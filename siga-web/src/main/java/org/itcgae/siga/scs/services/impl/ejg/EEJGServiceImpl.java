package org.itcgae.siga.scs.services.impl.ejg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.com.services.IPFDService;
import org.itcgae.siga.commons.utils.ReadProperties;
import org.itcgae.siga.commons.utils.SIGAReferences;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.itcgae.siga.db.entities.ScsEejgPeticiones;
import org.itcgae.siga.db.entities.ScsEjg;
import org.itcgae.siga.db.entities.ScsEjgKey;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.mappers.ScsEjgMapper;
import org.itcgae.siga.scs.services.ejg.IEEJGServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.sis.firma.core.B64.Base64CODEC;


@Service
public class EEJGServiceImpl implements IEEJGServices {
	
	private static Logger LOGGER = Logger.getLogger(EEJGServiceImpl.class);

	@Autowired
	private ScsEjgMapper scsEjgMapper;

	@Autowired
	private IPFDService pfdService;
	
	@Autowired
	private GenPropertiesMapper propertiesMapper;
	
	//@Autowired 
//	//private Base64CODEC base64;

	@Override
	public File getInformeEejg(Map<Integer, Map<String, String>> mapInformes, String idInstitucion) throws Exception {
		File file = null;
		List<File> file2zip = null;
		String nombreZip = null;
		List<String> lNumEjg = null; 
		String numEjg = null;
		String csv = "";
		String fecha = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		try {
			//obtenemos los datos del fichero desde bd
			
			for (Map.Entry<Integer, Map<String, String>> entrada: mapInformes.entrySet()){
				Map<String, String> mapParameters = entrada.getValue();
				numEjg = mapParameters.get("numEjg") ;
				
				csv = mapParameters.get("csv");
				if(csv != null && !csv.equals("")){
					String contenidoPDF = null;
					
					//LLamamos al servico de EEJG para obtener el PDF a traves de la PFD
					contenidoPDF = pfdService.obtenerDocumentoFirmado(csv);
				
					//generamos el informe
					file = generarInformeEejg(contenidoPDF, mapParameters, idInstitucion);
				} else {
					continue; 
				}
				
				if(mapInformes.entrySet().size()>1){
					
					if(file2zip==null){
						
						
						fecha = format.format(new Date());
						fecha = fecha.replaceAll("/","");
						fecha = fecha.replaceAll(":","");
						fecha = fecha.replaceAll(" ","_");
						
						lNumEjg = new ArrayList<String>();
						file2zip = new ArrayList<File>();
						
						numEjg = UtilidadesString.replaceAllIgnoreCase(numEjg, "/", "-");
						nombreZip = "eejg"+ "_" + idInstitucion + "_" +numEjg + "_" + fecha;
						
						if(!lNumEjg.contains(numEjg)){
							lNumEjg.add(numEjg);
						}
					}

					file2zip.add(file);
				}
				
			}
			
			if(file2zip!=null){
				ReadProperties rp= new ReadProperties(SIGAReferences.RESOURCE_FILES.SIGA);
				String directorioEspecificoInforme = rp.returnProperty("sjcs.directorioPlantillaInformeEejg");
				String directorioSalida = rp.returnProperty("sjcs.directorioFisicoSalidaInformeEejg");
				
				if(lNumEjg.size()>1)
					nombreZip = "eejg"+ "_" + idInstitucion + "_"+ fecha; 
				
				String rutaZip = directorioSalida + directorioEspecificoInforme + System.getProperty("file.separator") + idInstitucion
						+ System.getProperty("file.separator") + nombreZip;
				
				File zip = doZip(file2zip, rutaZip);
				
				return zip;
			}else{
				return file;
				
			}
		}catch(Exception e) {
			LOGGER.error("EEJGServiceImpl.getInformeEejg(). ERROR: ", e);
		}
		
		return file;
	}

	/**
	 * 
	 * @param array
	 * @param rutaFinal
	 * @return
	 */
	private static File doZip(List<File> array, String rutaFinal){
		File ficZip=null;
		byte[] buffer = new byte[8192];
		int leidos;
		ZipOutputStream outTemp = null;
		
		try {
			if ((array!=null) && (array.size()>0)) {
				
				ficZip = new File(rutaFinal+".zip");
				outTemp = new ZipOutputStream(new FileOutputStream(ficZip));
				
				for (int i=0; i<array.size(); i++)
				{
					File auxFile = (File)array.get(i);
					if (auxFile.exists()) {
						ZipEntry ze = new ZipEntry(auxFile.getName());
						outTemp.putNextEntry(ze);
						FileInputStream fis=new FileInputStream(auxFile);
						
						buffer = new byte[8192];
						
						while ((leidos = fis.read(buffer, 0, buffer.length)) > 0)
						{
							outTemp.write(buffer, 0, leidos);
						}
						
						outTemp.flush();
						fis.close();
						outTemp.closeEntry();
						auxFile.delete();
					}
				}
				
				outTemp.close();
			}
		 
		} catch (Exception e) {
			LOGGER.error("Error al crear fichero zip. ", e);
		} finally {
		    try {
		        outTemp.close();
		    } catch (Exception eee) {}
		}
		
		return ficZip;
	}
	
	/**
	 * 
	 * @param contenidoPDF
	 * @param mapParameters
	 * @return
	 * @throws Exception
	 */
	private File generarInformeEejg(String contenidoPDF, Map<String, String> mapParameters, String idInstitucion) throws Exception {
		File fileFirmado = null;

//		File rutaTmp = null;
		try {
//			String idioma = mapParameters.get("idioma");

//			String idiomaExt = idioma.substring(0, 2).toUpperCase();
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String fecha = format.format(new Date());
			
			fecha = fecha.replaceAll("/", "");
			fecha = fecha.replaceAll(":", "");
			fecha = fecha.replaceAll(" ", "_");
			
//			String directorioPlantillas = "";
			String directorioEspecificoInforme= "";
			String directorioSalida = "";
			
			for(int i=0; i<2; i++) {
				GenPropertiesKey key = new GenPropertiesKey();
				String parametro = "";
				
				if(i==0) {
					parametro="sjcs.directorioPlantillaInformeEejg";
				}else if(i==1){
					parametro="sjcs.directorioFisicoSalidaInformeEejg";
				}
				
				key.setParametro(parametro);
				key.setFichero("SIGA");
				
				GenProperties properties = propertiesMapper.selectByPrimaryKey(key);
				
				if(i==0) {
					directorioEspecificoInforme =  properties.getValor();
				}else if(i==1){
					directorioSalida =  properties.getValor();
				}
			}
			
//			 Directorios y nombres de trabajo
//			String plantillaNombre = "InformeEejg_" + idiomaExt + ".xsl";
//			String plantillaRuta = directorioPlantillas + directorioEspecificoInforme + System.getProperty("file.separator")
//					+ idInstitucion;
			
			String numEjg = mapParameters.get("numEjg");
			String numEjgListado = UtilidadesString.replaceAllIgnoreCase(numEjg, "-", "/");
			mapParameters.put("numEjg", numEjgListado);

			String pdfNombre = "eejg_2005_2018-01200_45837302G_20210525_131611.pdf";
//			String pdfNombre = "eejg" + "_" + idInstitucion + "_" + numEjg + "_" + mapParameters.get("nif") + "_"
//					+ fecha + ".pdf";
			String pdfRuta = directorioSalida + directorioEspecificoInforme + System.getProperty("file.separator") + idInstitucion;

			File rutaPDF = new File(pdfRuta);
			rutaPDF.mkdirs();
			
			if (!rutaPDF.canWrite()) {
				throw new Exception("Error plantilla");
			}

			// Nos creamos el fichero PDF que se va a mostrar al usuario
			pdfRuta += System.getProperty("file.separator");
			fileFirmado = new File(pdfRuta + pdfNombre);

			// Realizamos la decodificacion para su correcta visualización
			//base64.decodeToFile(contenidoPDF, pdfRuta + pdfNombre);

		}catch (Exception e) {
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