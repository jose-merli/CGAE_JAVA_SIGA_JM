package org.itcgae.siga.services.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.itcgae.siga.DTOs.adm.ParametroRequestDTO;
import org.itcgae.siga.DTOs.cen.DocuShareObjectVO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xerox.docushare.DSClass;
import com.xerox.docushare.DSContentElement;
import com.xerox.docushare.DSException;
import com.xerox.docushare.DSFactory;
import com.xerox.docushare.DSHandle;
import com.xerox.docushare.DSObject;
import com.xerox.docushare.DSObjectIterator;
import com.xerox.docushare.DSSelectSet;
import com.xerox.docushare.DSServer;
import com.xerox.docushare.DSSession;
import com.xerox.docushare.FileContentElement;
import com.xerox.docushare.object.DSCollection;
import com.xerox.docushare.object.DSDocument;
import com.xerox.docushare.property.DSLinkDesc;
import com.xerox.docushare.property.DSProperties;

@Service
public class DocushareHelper {

	private static Logger log = LoggerFactory.getLogger(DocushareHelper.class);

	@Autowired
	private GenParametrosExtendsMapper genParametrosMapper;
	@Autowired
	private GenPropertiesMapper genPropertiesMapper;
	
	private static boolean MODO_DEBUG_LOCAL = false;
	private static String DOCUSHARE_HOST = "DOCUSHARE_HOST";
	private static String DOCUSHARE_PORT = "DOCUSHARE_PORT";
	private static String DOCUSHARE_DOMAIN = "DOCUSHARE_DOMAIN";

	private static String DOCUSHARE_USER = "DOCUSHARE_USER";
	private static String DOCUSHARE_PASSWORD = "DOCUSHARE_PASSWORD";

	private static String PATH_DOCUSHARE_CENSO = "PATH_DOCUSHARE_CENSO";
	private static String PATH_DOCUSHARE_NOCOLEGIADO = "PATH_DOCUSHARE_NOCOLEGIADO";
	private static String PATH_DOCUSHARE_EJG = "PATH_DOCUSHARE_EJG";

	private static String ID_DOCUSHARE_CENSO = "ID_DOCUSHARE_CENSO";
	private static String ID_DOCUSHARE_NOCOLEGIADO = "ID_DOCUSHARE_NOCOLEGIADO";
	private static String ID_DOCUSHARE_EJG = "ID_DOCUSHARE_EJG";
	
	private static String PATH_DOCUSHARE_DEBUG = "/ds";
	
	private DSServer server;
	private DSSession dssession;
	private String antPath;
	
	private void createSession(short idinstitucion) throws Exception {

		String datosConexion = null;
		try {
			String host = null;
			String port = null;
			String domain = null;

			// parámetros particulares del colegio
			String user = null;
			String password = null;
			List<String> listaParametros = new ArrayList<>();
			listaParametros.add(DOCUSHARE_HOST);
			listaParametros.add(DOCUSHARE_PORT);
			listaParametros.add(DOCUSHARE_DOMAIN);
			listaParametros.add(DOCUSHARE_USER);
			listaParametros.add(DOCUSHARE_PASSWORD);

			List<GenParametros> config = new ArrayList<GenParametros>();
			for (Iterator iterator = listaParametros.iterator(); iterator.hasNext();) {
				String parametro = (String) iterator.next();
				GenParametros param = new GenParametros();
				ParametroRequestDTO parametroRequestDTO = new ParametroRequestDTO();
				parametroRequestDTO.setIdInstitucion(String.valueOf(idinstitucion));
				parametroRequestDTO.setModulo("GEN");
				parametroRequestDTO.setParametrosGenerales(parametro);
				StringDTO paramRequest = genParametrosMapper.getParameterFunction(0, parametroRequestDTO );
				param.setParametro(parametro);
				param.setValor(paramRequest.getValor());
				config.add(param);
			}

			if (config != null && config.size() > 0) {
				for (int i = 0; i < config.size(); i++) {
					switch (config.get(i).getParametro()) {
					case "DOCUSHARE_HOST":
						host = config.get(i).getValor();
						break;
					case "DOCUSHARE_PORT":
						port = config.get(i).getValor();
						break;
					case "DOCUSHARE_DOMAIN":
						domain = config.get(i).getValor();
						break;
					case "DOCUSHARE_USER":
						user = config.get(i).getValor();
						break;
					case "DOCUSHARE_PASSWORD":
						password = config.get(i).getValor();
						break;
					default:
						break;
					}
				}

				datosConexion = String.format(
						"Parámetros de conexión docushare del colegio %s: HOST:'%s', PORT:'%s', DOMAIN:'%s', USER:'%s', PASSWORD:'%s'",
						idinstitucion, host, port, domain, user, password);

				log.info(datosConexion);

				int iPort = 0;
				try {
					iPort = Integer.parseInt(port);
				} catch (Exception e) {
					String error = String.format(
							"El parámetro %s no está configurado correctamente para el colegio %s. Comprobar que es un número válido.",
							DOCUSHARE_PORT, idinstitucion);
					log.error(error);
				}
				String msg = "HOST: " + host + "PORT: " + iPort;

				log.info(msg);
				server = DSFactory.createServer(host, iPort);
				log.info("Creado server con Docushare correctamente para el colegio " + idinstitucion);
				dssession = server.createSession(domain, user, password);
				log.info("Creada session con Docushare correctamente para el colegio " + idinstitucion);

			}

			 

		} catch (Exception e) {
			// Se ha producido un error. No se ha podido conectar con el servidor DocuShare.
			String mensaje = "Se ha producido un error. No se ha podido conectar con el servidor DocuShare. "
					+ datosConexion;
			log.error(mensaje, e);
			throw new Exception(mensaje, e);
		}

	}

	 
	/**
	 * Busca una collection a partir de una path y un nombre de collection
	 * 
	 * @param path
	 * @param title
	 * @return
	 * @throws Exception
	 */
	private String buscaCollection(short idinstitucion, String pathRecibido, String title) throws Exception {
		if (MODO_DEBUG_LOCAL) {
			return buscaCollectionMODO_DEBUG_LOCAL(PATH_DOCUSHARE_DEBUG, title);
		}
		String idColl = null;

		createSession(idinstitucion);

		try {
			// GenParametrosService genParametrosService = (GenParametrosService)
			// BusinessManager.getInstance()
			// .getService(GenParametrosService.class);
			
			log.debug("Entramos a buscar la colección de DocuShare");//de Censo
			List<String> listaParametros = new ArrayList<>();
			listaParametros.add(pathRecibido);
			
			List<GenParametros> config = new ArrayList<GenParametros>();
			for (Iterator iterator = listaParametros.iterator(); iterator.hasNext();) {
				String parametro = (String) iterator.next();
				GenParametros param = new GenParametros();
				ParametroRequestDTO parametroRequestDTO = new ParametroRequestDTO();
				parametroRequestDTO.setIdInstitucion(String.valueOf(idinstitucion));
				parametroRequestDTO.setModulo("GEN");
				parametroRequestDTO.setParametrosGenerales(parametro);
				StringDTO paramRequest = genParametrosMapper.getParameterFunction(0, parametroRequestDTO );
				param.setParametro(parametro);
				param.setValor(paramRequest.getValor());
				config.add(param);
			}

			
			String path = config.get(0).getValor();
			log.debug("path: " +path);
			if (path != null) {
				if (!path.trim().endsWith(";")) {
					path += ";";
				}
				path += title;
				log.debug("new path: " +path);
				String[] titles = path.split(";");
				log.debug("DSCollection.title: " +DSCollection.title);
				log.debug("titles: " +titles.toString());
				log.debug("DSSelectSet.NO_PROPERTIES: " +DSSelectSet.NO_PROPERTIES);
				DSObject dsObject = dssession.getResolvedObject(DSCollection.title, titles, DSSelectSet.NO_PROPERTIES);
				log.debug("dsObject: " +dsObject);
				if (dsObject != null) {
					idColl = dsObject.getHandle().toString();
					log.debug("idColl: " +idColl);
				}
			}
		} catch (Exception e) {
			String mensaje = String.format(
					"Se ha producido un error al buscar la collection para el colegio %s, pathRecibido = '%s' y title = '%s'",
					idinstitucion, pathRecibido, title);
			log.error(mensaje, e);

		} finally {
			close(idinstitucion);
		}

		return idColl;
	}

	public String buscaCollectionCenso(String title, short idInstitucion) throws Exception {
		return buscaCollection(idInstitucion, PATH_DOCUSHARE_CENSO, title);
	}

	public String buscaCollectionNoColegiado(String title, short idinstitucion) throws Exception {
		return buscaCollection(idinstitucion, PATH_DOCUSHARE_NOCOLEGIADO, title);
	}
	
	public String buscaCollectionEjg(String title, short idinstitucion) throws Exception {
		return buscaCollection(idinstitucion, PATH_DOCUSHARE_EJG, title);
	}

	private String buscaCollectionMODO_DEBUG_LOCAL(String parent, String title) {
		File file = new File(parent, title);
		if (file != null && file.exists()) {
			return file.getAbsolutePath();
		} else {
			return null;
		}
	}

	/**
	 * Método para pruebas en local
	 * 
	 * @param title
	 * @return
	 * @throws Exception
	 */
	public File getDocumentMODO_DEBUG_LOCAL(String title) {
		File file = new File(title);
		return file;
	}

	/**
	 * Método para pruebas en local
	 * 
	 * @param identificadorDS
	 * @return
	 * @throws SIGAServiceException
	 */
	private List<DocuShareObjectVO> getContenidoCollectionMODO_DEBUG_LOCAL(short idinstitucion, String identificadorDS) throws Exception {
		List<DocuShareObjectVO> datos = new ArrayList<DocuShareObjectVO>();
		try {
			File file = new File(identificadorDS);

			if (file != null) {
				List<DocuShareObjectVO> listDir = new ArrayList<DocuShareObjectVO>();
				List<DocuShareObjectVO> listArch = new ArrayList<DocuShareObjectVO>();
				File[] filesHijos = file.listFiles();
				if (filesHijos != null) {
					for (File fileHijo : filesHijos) {
						DocuShareObjectVO dsObj = null;
						if (fileHijo.isDirectory()) {
							dsObj = new DocuShareObjectVO();
							dsObj.setTipo("0");
							dsObj.setId(fileHijo.getAbsolutePath());
							dsObj.setTitle(fileHijo.getName());
							dsObj.setDescription("");
							dsObj.setSummary("");
							dsObj.setOriginalFilename("");
							dsObj.setFechaModificacion(new Date(fileHijo.lastModified()));
							listDir.add(dsObj);
						} else {
							dsObj = new DocuShareObjectVO();
							dsObj.setTipo("1");
							dsObj.setId(fileHijo.getAbsolutePath());
							dsObj.setTitle(fileHijo.getName());
							dsObj.setDescription("Descripcion - " + fileHijo.getName());
							dsObj.setFechaModificacion(new Date(fileHijo.lastModified()));
							dsObj.setOriginalFilename("Filename Original - " + fileHijo.getName());
							dsObj.setSummary("Resumen - " + fileHijo.getName());
							dsObj.setSizeKB(fileHijo.length() / 1024);
							if (dsObj.getSizeKB() == 0) {
								dsObj.setSizeKB(1);
							}
							listArch.add(dsObj);
						}
					}
				}

				// Collections.sort(listDir);
				// Collections.sort(listArch);

				datos.addAll(listDir);
				datos.addAll(listArch);

			}

		} catch (Exception e) {
			String mensaje = String.format(
					"Se ha producido un error al obtener el contenido de la coleccion con identificadorDS = '%s' para el colegio %s",
					identificadorDS, idinstitucion);
			log.error(mensaje, e);

		}
		return datos;

	}

	/**
	 * Cerramos la session y la conexion con el servidor DocuShare
	 * 
	 * @throws DSException
	 */
	private void close(short idinstitucion) throws Exception {
		log.info("Cerrando conexión con Docushare para el colegio " + idinstitucion + "...");
		try {
			if (server != null) {
				server.close();
				log.debug("Conexión cerrada con Docushare para el colegio " + idinstitucion);
			}
		} catch (Exception e) {
			String mensaje = String.format("Se ha producido un error al cerrar la conexión para el colegio %s",
					idinstitucion);
			log.error(mensaje, e);

		}
	}

	/**
	 * Recupera lista de colleccion y de documents de una collection
	 * 
	 * @param collection
	 * @return
	 * @throws SIGAServiceException
	 * @throws Exception
	 */
	public List<DocuShareObjectVO> getContenidoCollection(short idinstitucion, String collection, String antPath) throws Exception {

		if (collection == null || collection.trim().equals("")) {
			throw new IllegalArgumentException("El nombre de la colección no puede ser nula o vacía");
		}
		if (MODO_DEBUG_LOCAL) {
			return getContenidoCollectionMODO_DEBUG_LOCAL(idinstitucion, collection);
		}

		List<DocuShareObjectVO> list = new ArrayList<DocuShareObjectVO>();

		try {
			createSession(idinstitucion);
			log.debug("Recuperando contenido collection " + collection + " del colegio " + idinstitucion);

			DSHandle dsHandle = new DSHandle(collection);
			DSCollection dsCollectionParent = (DSCollection) dssession.getObject(dsHandle);

			if (dsCollectionParent != null) {
				log.debug("Se ha encontrado la collection " + dsCollectionParent.getHandle() + " para el colegio "
						+ idinstitucion);
				DSObjectIterator it = dsCollectionParent.children(null);
				if (it != null) {
					log.debug("Número de hijos de la collection " + collection + ": "
							+ dsCollectionParent.getChildCount() + " del colegio " + idinstitucion);
					List<DocuShareObjectVO> listDir = new ArrayList<DocuShareObjectVO>();
					List<DocuShareObjectVO> listArch = new ArrayList<DocuShareObjectVO>();

					DSObject dsObject = null;

					while (it.hasNext()) {
						dsObject = it.nextObject();
						log.debug("Encontrado hijo \"" + dsObject.getHandle() + "\" dentro de la collection "
								+ collection + " del colegio " + idinstitucion);

						if (dsObject instanceof DSCollection) {
							DocuShareObjectVO dsObj = new DocuShareObjectVO();
							dsObj.setTipo("0");
							dsObj.setId(dsObject.getHandle().toString());
							dsObj.setTitle(dsObject.getTitle());
							dsObj.setFechaModificacion(dsObject.getModifiedDate());
							dsObj.setDescription("");
							dsObj.setSummary("");
							dsObj.setOriginalFilename("");
							dsObj.setParent(antPath);
							listDir.add(dsObj);
						} else if (dsObject instanceof DSDocument) {
							DocuShareObjectVO dsObj = new DocuShareObjectVO();
							dsObj.setTipo("1");
							dsObj.setId(dsObject.getHandle().toString());

							dsObj.setTitle(dsObject.getTitle() + " " + ((DSDocument) dsObject).getOriginalFileName());
							dsObj.setDescription(dsObject.getDescription());
							dsObj.setFechaModificacion(dsObject.getModifiedDate());
							dsObj.setOriginalFilename(((DSDocument) dsObject).getOriginalFileName());
							dsObj.setSummary(dsObject.getSummary());
							long size = ((DSDocument) dsObject).getSize();
							if (size > 0) {
								dsObj.setSizeKB(size / 1024);
							}
							dsObj.setParent(antPath);
							listArch.add(dsObj);
						}
					}

					 Collections.sort(listDir);
					 
					 Collections.sort(listDir, new Comparator<DocuShareObjectVO>() {
					 public int compare(DocuShareObjectVO o3, DocuShareObjectVO o4) {
					 return (-1) * o3.getFechaModificacion().compareTo(o4.getFechaModificacion());
					 }
					 });
					 
					 Collections.sort(listArch, new Comparator<DocuShareObjectVO>() {
					 public int compare(DocuShareObjectVO o1, DocuShareObjectVO o2) {
					 return (-1) * o1.getFechaModificacion().compareTo(o2.getFechaModificacion());
					 }
					 });

					list.addAll(listDir);
					list.addAll(listArch);
				}
			}

		} catch (Exception e) {
			String mensaje = String.format(
					"Se ha producido un error al obtener el contenido de la coleccion con identificadorDS = '%s' para el colegio %s",
					collection, idinstitucion);
			log.error(mensaje, e);
			throw new Exception(mensaje, e);
		} finally {
			close(idinstitucion);
		}

		return list;
	}

	/**
	 * Recupera un documento copiandolo en un fichero local
	 * 
	 * @param title
	 * @return
	 * @throws Exception
	 */
	public File getDocument(short idinstitucion, String title) throws Exception {

		if (MODO_DEBUG_LOCAL) {
			return getDocumentMODO_DEBUG_LOCAL(title);
		}
		log.debug("titulo del documento a descargar Regtel: " + title);
		log.debug("institucion del documento a descargar Regtel: " + idinstitucion);
		// Extraer propiedad
		GenPropertiesExample genPropertiesExampleP = new GenPropertiesExample();
		genPropertiesExampleP.createCriteria().andParametroEqualTo("sjcs.directorioFisicoTemporalSJCSJava");
		List<GenProperties> genPropertiesPath = genPropertiesMapper.selectByExample(genPropertiesExampleP);

		String rutaDirectorioTemp = genPropertiesPath.get(0).getValor();

		File fileParent = new File(rutaDirectorioTemp);

		File file = null;

		try {
			createSession(idinstitucion);

			DSDocument dsDocument = (DSDocument) dssession.getObject(new DSHandle(title));
			log.debug("DsDocument: " + dsDocument);
			DSContentElement[] dsContentElements = dsDocument.getContentElements();
			for (int j = 0; j < dsContentElements.length; j++) {
				log.debug("DsDocument número: " + j);
				if (dsContentElements[j] instanceof FileContentElement) {
					FileContentElement dsContentElement = (FileContentElement) dsContentElements[j];
					fileParent.mkdirs();
					file = new File(fileParent, dsDocument.getOriginalFileName());

					dsContentElement.open();
					int max = dsContentElement.available();
					ByteArrayOutputStream baos = new ByteArrayOutputStream(max);

					baos.write(dsContentElement.read(max));
					baos.flush();

					OutputStream outputStream = new FileOutputStream(file);
					baos.writeTo(outputStream);

					baos.close();
					outputStream.close();
					dsContentElement.close();
				}
			}

			return file;
		} catch (Exception e) {
			// "message.docushare.error.obtenerDocumento"
			String mensaje = String.format(
					"Se ha producido un error al obtener el documento con identificador = '%s' para el colegio %s",
					title, idinstitucion);
			log.error(mensaje, e);

		} finally {
			close(idinstitucion);
		}
		return file;

	}
	/**
	 * 
	 * @param collectionTitle Nombre de la carpeta o collecion que se quiere crear
	 * @return
	 * @throws SIGAServiceException 
	 * @throws SIGAException
	 * @throws ClsExceptions
	 */
	public String createCollectionNoColegiado(short idinstitucion, String collectionTitle, String collectionSummary) throws Exception {
		return createCollection(idinstitucion, ID_DOCUSHARE_NOCOLEGIADO, collectionTitle, collectionSummary);
	}	

	/**
	 * 
	 * @param collectionTitle Nombre de la carpeta o collecion que se quiere crear
	 * @param collectionSummary Descripcion de la carpeta o collecion que se quiere crear
	 * @return
	 * @throws SIGAServiceException 
	 * @throws SIGAException
	 * @throws ClsExceptions
	 */
	public String createCollectionCenso(short idinstitucion, String collectionTitle, String collectionSummary) throws Exception {
		return createCollection(idinstitucion, ID_DOCUSHARE_CENSO, collectionTitle, collectionSummary);
	}
	
	/**
	 * 
	 * @param collectionTitle Nombre de la carpeta o collecion que se quiere crear
	 * @param collectionSummary Descripcion de la carpeta o collecion que se quiere crear
	 * @return
	 * @throws SIGAServiceException 
	 * @throws SIGAException
	 * @throws ClsExceptions
	 */
	public String createCollectionEjg(short idinstitucion, String collectionTitle, String collectionSummary) throws Exception {
		return createCollection(idinstitucion, ID_DOCUSHARE_EJG, collectionTitle, collectionSummary);
	}
	


	
	/**
	 * 
	 * @param collectionTitle Nombre de la carpeta o collecion que se quiere crear
	 * @param collectionSummary Descripcion de la carpeta o collecion que se quiere crear
	 * @return
	 * @throws SIGAException
	 * @throws ClsExceptions
	 */
	private String createCollection(short idinstitucion, String ID_DOCUSHARE, String collectionTitle, String collectionSummary) throws Exception{

	
		String identificadorDS = null;				
		createSession(idinstitucion);
		
		try {
			

			List<GenParametros> config = new ArrayList<GenParametros>();
				GenParametros param = new GenParametros();
				ParametroRequestDTO parametroRequestDTO = new ParametroRequestDTO();
				parametroRequestDTO.setIdInstitucion(String.valueOf(idinstitucion));
				parametroRequestDTO.setModulo("GEN");
				parametroRequestDTO.setParametrosGenerales(ID_DOCUSHARE);
				StringDTO paramRequest = genParametrosMapper.getParameterFunction(0, parametroRequestDTO );
				param.setParametro(ID_DOCUSHARE);
				param.setValor(paramRequest.getValor());
				config.add(param);

			
			String idExpedientes = config.get(0).getValor();
			log.debug("ID_DOCUSHARE=" + idExpedientes + " para el colegio " + idinstitucion);
			
			log.info("Obtencion de dsCollectionParent");
			DSCollection dsCollectionParent = (DSCollection) dssession.getObject(new DSHandle(idExpedientes));			
			log.info("Parent obtenido");
			
			log.info("Manipulacion de coleccion");
			DSClass collectionClass = dssession.getDSClass(DSCollection.classname);
			DSProperties collectionPrototype = collectionClass.createPrototype();
			collectionPrototype.setPropValue(DSCollection.title, collectionTitle);
			collectionPrototype.setPropValue(DSCollection.summary, collectionSummary);
			log.info("Salida de manipulacion de obtencion");

			log.info("Obtencion de identificadorDS");
			DSHandle dsHandle = dssession.createObject(collectionPrototype, DSLinkDesc.containment, dsCollectionParent, null, null);
			log.info("Valor de identificadorDS "+dsHandle.toString());
			identificadorDS = dsHandle.toString();
		
		} catch (Exception e) {
			//"expedientes.docushare.error.crearColeccion"
			String mensaje = String.format("Se ha producido un error al crear la collection para el colegio %s e ID_DOCUSHARE = '%s'", idinstitucion, ID_DOCUSHARE);
			log.error(mensaje, e);
			throw (new Exception("Error al crear la colección en Regtel para el EJG"));
		} finally {
			close(idinstitucion);
		}

		return identificadorDS;
	}

}
