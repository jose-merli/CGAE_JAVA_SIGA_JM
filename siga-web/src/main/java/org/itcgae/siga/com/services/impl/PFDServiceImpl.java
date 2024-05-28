package org.itcgae.siga.com.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.itcgae.siga.com.services.IPFDService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.ws.client.ClientPFD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pfd.ws.validacionFirma.ResultSolicitudDocumentoTO;
import com.pfd.ws.validacionFirma.SolicitudDocumentoTO;
import com.sis.firma.core.TO.DatosSelloFirmaPDFTO;
import com.sis.firma.core.TO.FirmaResultadoTO;
import com.sis.firma.core.tipos.TipoResultadoFirma;
import com.sis.firma.core.tipos.TipoResultadoObtencionDoc;

@Service
public class PFDServiceImpl implements IPFDService {

	Logger LOGGER = Logger.getLogger(PFDServiceImpl.class);

	@Autowired
	ClientPFD clientPFD;

	@Autowired
	GenPropertiesMapper _genPropertiesMapper;

	@Autowired
	GenParametrosMapper _genParametrosMapper;

	@Override
	public String firmarPDF(File fichero) throws Exception {
		GenParametrosKey keyParam = new GenParametrosKey();

		keyParam.setIdinstitucion(Short.parseShort(SigaConstants.IDINSTITUCION_0));
		keyParam.setModulo(SigaConstants.MODULO_GEN);
		keyParam.setParametro(SigaConstants.PFD_FIRMA_LOCATION);

		GenParametros param = _genParametrosMapper.selectByPrimaryKey(keyParam);

		String firmaLocation = param.getValor();

		keyParam = new GenParametrosKey();

		keyParam.setIdinstitucion(Short.parseShort(SigaConstants.IDINSTITUCION_0));
		keyParam.setModulo(SigaConstants.MODULO_GEN);
		keyParam.setParametro(SigaConstants.PFD_FIRMA_VISIBLE);

		param = _genParametrosMapper.selectByPrimaryKey(keyParam);

		String firmaVisible = param.getValor();

		keyParam = new GenParametrosKey();

		keyParam.setIdinstitucion(Short.parseShort(SigaConstants.IDINSTITUCION_0));
		keyParam.setModulo(SigaConstants.MODULO_GEN);
		keyParam.setParametro(SigaConstants.PFD_IDCLIENTE);

		param = _genParametrosMapper.selectByPrimaryKey(keyParam);
		String idClientePFD = param.getValor();

		keyParam = new GenParametrosKey();

		keyParam.setIdinstitucion(Short.parseShort(SigaConstants.IDINSTITUCION_0));
		keyParam.setModulo(SigaConstants.MODULO_GEN);
		keyParam.setParametro(SigaConstants.PFD_FIRMA_RAZON);

		param = _genParametrosMapper.selectByPrimaryKey(keyParam);
		String razon = param.getValor();

		String base64File = "";
		//String csv = "";

		if (fichero != null) {
			try (FileInputStream inFile = new FileInputStream(fichero)) {
				byte bytesData[] = new byte[(int) fichero.length()];
				inFile.read(bytesData);
				base64File = Base64.getEncoder().encodeToString(bytesData);
			} catch (FileNotFoundException e) {
				LOGGER.error("Fichero a firmar no encontrado", e);
				throw new Exception("Fichero a firmar no encontrado");
			} catch (IOException e) {
				LOGGER.error("Error al leer el fichero a firmar ", e);
				throw new Exception("Error al leer el fichero a firmar");
			}
		}

		if (base64File != null && !"".equals(base64File)) {

			DatosSelloFirmaPDFTO datosSelloFirma =new DatosSelloFirmaPDFTO();
			datosSelloFirma.setVisible(Integer.parseInt(firmaVisible));
			datosSelloFirma.setRazon(razon);
			datosSelloFirma.setLocation(firmaLocation);
			Calendar cal = Calendar.getInstance();
			datosSelloFirma.setFechaFirma(cal, "DD/MM/YYYY");

			FirmaResultadoTO response = null;
			try {

				keyParam = new GenParametrosKey();

				keyParam.setIdinstitucion(Short.parseShort(SigaConstants.IDINSTITUCION_0));
				keyParam.setModulo(SigaConstants.MODULO_GEN);
				keyParam.setParametro(SigaConstants.PFD_URLWS);

				param = _genParametrosMapper.selectByPrimaryKey(keyParam);
				String uriService = param.getValor();

				response = clientPFD.firmarPDF(idClientePFD, uriService, datosSelloFirma, base64File);
				if (response != null) {				    
			    	
					//tratamos la firma
			    	String resultado = response.getB64();

					if (response.getResultado().toUpperCase().equals(TipoResultadoFirma.FIRMA_OK)) {
						LOGGER.debug("PFDServiceImpl.firmarPDF :: Documento firmado correctamente");
						base64File = resultado;
					} else {
						LOGGER.error("PFDServiceImpl.firmarPDF :: Error al firmar el documento :: " + resultado);
						throw new Exception("PFDServiceImpl.firmarPDF :: Error al firmar el documento :: " + resultado);
					}
				} else {
					LOGGER.error("PFDServiceImpl.firmarPDF :: Error al firmar el documento :: Respuesta nula");
					throw new Exception("PFDServiceImpl.firmarPDF :: Error al enviar a firmar un documento");
				}
			} catch (Exception e) {
				LOGGER.error("PFDServiceImpl.firmarPDF :: Error al enviar a firmar un documento ", e);
				throw new Exception("PFDServiceImpl.firmarPDF :: Error al enviar a firmar un documento");
			}
		} else {
			throw new Exception("PFDServiceImpl.firmarPDF :: Error al obtener el fichero a firmar");
		}

		return base64File;
	}

	@Override
	public String obtenerDocumentoFirmado(String csv) throws Exception {
		return obtenerDocumentoFirmadoModulo(csv, SigaConstants.ID_INSTITUCION_0, SigaConstants.MODULO_GEN, SigaConstants.PFD_IDCLIENTE, SigaConstants.PFD_URLWS);
	}

	@Override
	public String obtenerDocumentoEEJGFirmado(String csv) throws Exception {
		return obtenerDocumentoFirmadoModulo(csv, SigaConstants.IDINSTITUCION_2000, SigaConstants.MODULO_SCS, SigaConstants.EEJG_IDSISTEMA, SigaConstants.PFD_URLWS);
	}

	private String obtenerDocumentoFirmadoModulo(String csv, Short idInstitucion, String modulo, String parametroIdCliente, String paramentroUrl) throws Exception {
		SolicitudDocumentoTO solicitud = new SolicitudDocumentoTO();
		ResultSolicitudDocumentoTO response = null;
		String documentoBase64 = "";

		GenParametrosKey keyParam = new GenParametrosKey();

		keyParam.setIdinstitucion(idInstitucion);
		keyParam.setModulo(modulo);
		keyParam.setParametro(parametroIdCliente);
		GenParametros param = _genParametrosMapper.selectByPrimaryKey(keyParam);

		String idClientePFD = param.getValor();

		solicitud.setIdAppCliente(idClientePFD);
		solicitud.setIdValidacion(csv);
		
		// cambiar esto por el csv del fichero, para pruebas en local
		//solicitud.setIdValidacion("DEM-JKTYO-NERK0-LNGBQ-CLB1Z");

		try {
			keyParam = new GenParametrosKey();

			keyParam.setIdinstitucion(idInstitucion);
			keyParam.setModulo(modulo);
			keyParam.setParametro(paramentroUrl);

			param = _genParametrosMapper.selectByPrimaryKey(keyParam);
			String uriService = param.getValor();

			response = clientPFD.obtenerDocumento(uriService, solicitud);
			if (response != null) {
				String resultado = response.getDocumento().getResultado();
				LOGGER.debug(resultado);
				
				if (response.getResultado().toUpperCase().equals (TipoResultadoObtencionDoc.SOLICITUD_DOCUMENTO_OK))
					{
				String idValidacion=(response.getDocumento().getIdValidacion());
				String idCliente=(response.getDocumento().getCliente());
				String procesoFirma=(response.getDocumento().getProcesoFirma());
				String fecha=(response.getDocumento().getFecha());
				String cn=(response.getDocumento().getCn());
				String certificadoB64=(response.getDocumento().getCertificadoB64().toString());
				documentoBase64=(response.getDocumento().getFirmab64().toString());
			
				} else {
					LOGGER.error("PFDServiceImpl.obtenerDocumentoFirmado :: Error al obtener el documento firmado :: "
							+ resultado);
					throw new Exception(
							"PFDServiceImpl.firmarPDF :: Error al obtener el documento firmado :: " + resultado);
				}
			} else {
				LOGGER.error(
						"PFDServiceImpl.obtenerDocumentoFirmado :: Error al obtener el documento firmado :: Respuesta nula");
				throw new Exception(
						"PFDServiceImpl.obtenerDocumentoFirmado :: Error al obtener el documento firmado :: Respuesta nula");
			}

		} catch (Exception e) {
			LOGGER.error("Error al enviar a firmar un documento ", e);
			throw new Exception("Error al enviar a firmar un documento");
		}

		return documentoBase64;
	}

	@Override
	public byte[] getBytes(String file) throws Exception {
		FileInputStream inputStream = new FileInputStream(file);
		byte[] bytes = new byte[inputStream.available()];
		inputStream.read(bytes);
		inputStream.close();

		return bytes;
	}

	@Override
	public File createTempFile(byte[] bytes, String fileName) throws Exception {
		File returnFile = null;

		try {
			returnFile = new File(fileName);

			FileOutputStream fileOutputStream = new FileOutputStream(returnFile);

			fileOutputStream.write(bytes);
			fileOutputStream.flush();
			fileOutputStream.close();

			returnFile.deleteOnExit();

		} catch (Exception e) {
			LOGGER.debug("Se ha producido un error al createFile" + e.toString());
			throw new Exception("Se ha producido un error al createFile");
		}

		return returnFile;
	}

}
