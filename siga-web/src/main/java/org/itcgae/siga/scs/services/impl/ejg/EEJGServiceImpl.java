package org.itcgae.siga.scs.services.impl.ejg;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.com.DatosDocumentoItem;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.com.services.IPFDService;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.ScsEejgPeticiones;
import org.itcgae.siga.db.entities.ScsEjg;
import org.itcgae.siga.db.entities.ScsEjgKey;
import org.itcgae.siga.db.mappers.ScsEjgMapper;
import org.itcgae.siga.scs.services.ejg.IEEJGServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EEJGServiceImpl implements IEEJGServices {

	private static Logger LOGGER = Logger.getLogger(EEJGServiceImpl.class);

	@Autowired
	private ScsEjgMapper scsEjgMapper;

	@Autowired
	private IPFDService pfdService;

	@Override
	public DatosDocumentoItem getInformeEejg(Map<Integer, Map<String, String>> mapInformes, String idInstitucion) throws Exception {
		File file = null;
		DatosDocumentoItem doc = new DatosDocumentoItem();
		String csv = "";

		try {
			// obtenemos los datos del fichero desde bd

			for (Map.Entry<Integer, Map<String, String>> entrada : mapInformes.entrySet()) {
				Map<String, String> mapParameters = entrada.getValue();

				csv = mapParameters.get("csv");
				if (csv != null && !csv.equals("")) {
					String contenidoPDF = null;

					// LLamamos al servico de EEJG para obtener el PDF a traves de la PFD
					contenidoPDF = pfdService.obtenerDocumentoEEJGFirmado(csv);

					// generamos el informe
					byte[] content = Base64.getDecoder().decode(contenidoPDF);
					
					file = generarInformeEejg(content, mapParameters, idInstitucion);
					
					doc.setDatos(content);
					doc.setDocumentoFile(file);
					doc.setFileName(file.getName());
					doc.setPathDocumento(file.getPath());
				} else {
					continue;
				}
			}
		} catch (Exception e) {
			LOGGER.error("EEJGServiceImpl.getInformeEejg(). ERROR: ", e);
		}

		return doc;
	}

	/**
	 * 
	 * @param contenidoPDF
	 * @param mapParameters
	 * @return
	 * @throws Exception
	 */
	private File generarInformeEejg(byte[] contenidoPDF, Map<String, String> mapParameters, String idInstitucion)
			throws Exception {
		File fileFirmado = null;

		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String fecha = format.format(new Date());

			fecha = fecha.replaceAll("/", "");
			fecha = fecha.replaceAll(":", "");
			fecha = fecha.replaceAll(" ", "_");

			String numEjg = mapParameters.get("numEjg");
			String numEjgListado = UtilidadesString.replaceAllIgnoreCase(numEjg, "-", "/");
			mapParameters.put("numEjg", numEjgListado);

			String pdfNombre = "eejg" + "_" + idInstitucion + "_" + numEjg + "_" + mapParameters.get("nif") + "_"
					+ fecha + ".pdf";
			
			fileFirmado = pfdService.createTempFile(contenidoPDF, pdfNombre);

		} catch (Exception e) {
			throw new Exception("Error al generar el informe", e);
		}

		return fileFirmado;
	}


	@Override
	public Map<Integer, Map<String, String>> getDatosInformeEejg(EjgItem item, ScsEejgPeticiones peticion)
			throws Exception {

		Map<Integer, Map<String, String>> mapInformes = new HashMap<Integer, Map<String, String>>();
		Map<String, String> mapParameters = new HashMap<String, String>();

		ScsEjg scsEjg = new ScsEjg();
		ScsEjgKey key = new ScsEjgKey();

		try {
			// obtenemos los datos del ejg
			key.setAnio(Short.parseShort(item.getAnnio()));
			key.setIdinstitucion(Short.parseShort(item.getidInstitucion()));
			key.setIdtipoejg(Short.parseShort(item.getTipoEJG()));
			key.setNumero(Long.parseLong(item.getNumEjg()));

			scsEjg = scsEjgMapper.selectByPrimaryKey(key);

			// cargamos los datos para el informe
			mapParameters.put("numEjg", scsEjg.getAnio() + "-" + scsEjg.getNumejg());
			mapParameters.put("nif", peticion.getNif());
			mapParameters.put("idInstitucion", scsEjg.getIdinstitucion().toString());
			mapParameters.put("idioma", peticion.getIdioma());
			mapParameters.put("csv", peticion.getCsv());
			mapInformes.put(Integer.parseInt(String.valueOf(peticion.getIdxml())), mapParameters);

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return mapInformes;
	}

}