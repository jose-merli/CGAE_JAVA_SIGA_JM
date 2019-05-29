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
import org.itcgae.siga.commons.utils.ReadProperties;
import org.itcgae.siga.commons.utils.SIGAReferences;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
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

	private static String ID_DOCUSHARE_CENSO = "ID_DOCUSHARE_CENSO";
	private static String ID_DOCUSHARE_NOCOLEGIADO = "ID_DOCUSHARE_NOCOLEGIADO";
	private static String PATH_DOCUSHARE_DEBUG = "/ds";
	private short idInstitucion;
	
	private DSServer server;
	private DSSession dssession;
	private String antPath;
	
	public DocushareHelper(short idInstitucion) {
		super();
		this.idInstitucion = idInstitucion;
	}

	// Para que coja el idioma español !!! Debería cogerlo desde el bundle de
	// xmlbeans pero no lo coge
	public void setIdInstitucion(short idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	private void createSession() throws Exception {

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
				parametroRequestDTO.setIdInstitucion(String.valueOf(this.idInstitucion));
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
						this.idInstitucion, host, port, domain, user, password);

				log.info(datosConexion);

				int iPort = 0;
				try {
					iPort = Integer.parseInt(port);
				} catch (Exception e) {
					String error = String.format(
							"El parámetro %s no está configurado correctamente para el colegio %s. Comprobar que es un número válido.",
							DOCUSHARE_PORT, this.idInstitucion);
					log.error(error);
				}
				String msg = "HOST: " + host + "PORT: " + iPort;

				log.info(msg);
				server = DSFactory.createServer(host, iPort);
				log.info("Creado server con Docushare correctamente para el colegio " + this.idInstitucion);
				dssession = server.createSession(domain, user, password);
				log.info("Creada session con Docushare correctamente para el colegio " + this.idInstitucion);

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
	private String buscaCollection(String pathRecibido, String title) throws Exception {
		if (MODO_DEBUG_LOCAL) {
			return buscaCollectionMODO_DEBUG_LOCAL(PATH_DOCUSHARE_DEBUG, title);
		}
		String idColl = null;

		createSession();

		try {
			// GenParametrosService genParametrosService = (GenParametrosService)
			// BusinessManager.getInstance()
			// .getService(GenParametrosService.class);
			
			
			List<String> listaParametros = new ArrayList<>();
			listaParametros.add(pathRecibido);
			
			List<GenParametros> config = new ArrayList<GenParametros>();
			for (Iterator iterator = listaParametros.iterator(); iterator.hasNext();) {
				String parametro = (String) iterator.next();
				GenParametros param = new GenParametros();
				ParametroRequestDTO parametroRequestDTO = new ParametroRequestDTO();
				parametroRequestDTO.setIdInstitucion(String.valueOf(this.idInstitucion));
				parametroRequestDTO.setModulo("GEN");
				parametroRequestDTO.setParametrosGenerales(parametro);
				StringDTO paramRequest = genParametrosMapper.getParameterFunction(0, parametroRequestDTO );
				param.setParametro(parametro);
				param.setValor(paramRequest.getValor());
				config.add(param);
			}

			
			String path = config.get(0).getValor();

			if (path != null) {
				if (!path.trim().endsWith(";")) {
					path += ";";
				}
				path += title;

				String[] titles = path.split(";");
				DSObject dsObject = dssession.getResolvedObject(DSCollection.title, titles, DSSelectSet.NO_PROPERTIES);
				if (dsObject != null) {
					idColl = dsObject.getHandle().toString();
				}
			}
		} catch (Exception e) {
			String mensaje = String.format(
					"Se ha producido un error al buscar la collection para el colegio %s, pathRecibido = '%s' y title = '%s'",
					this.idInstitucion, pathRecibido, title);
			log.error(mensaje, e);

		} finally {
			close();
		}

		return idColl;
	}

	public String buscaCollectionCenso(String title, short idInstitucion) throws Exception {
		this.idInstitucion = idInstitucion;
		return buscaCollection(PATH_DOCUSHARE_CENSO, title);
	}

	public String buscaCollectionNoColegiado(String title) throws Exception {
		return buscaCollection(PATH_DOCUSHARE_NOCOLEGIADO, title);
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
	private List<DocuShareObjectVO> getContenidoCollectionMODO_DEBUG_LOCAL(String identificadorDS) throws Exception {
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
					identificadorDS, this.idInstitucion);
			log.error(mensaje, e);

		}
		return datos;

	}

	/**
	 * Cerramos la session y la conexion con el servidor DocuShare
	 * 
	 * @throws DSException
	 */
	private void close() throws Exception {
		log.info("Cerrando conexión con Docushare para el colegio " + this.idInstitucion + "...");
		try {
			if (server != null) {
				server.close();
				log.debug("Conexión cerrada con Docushare para el colegio " + this.idInstitucion);
			}
		} catch (Exception e) {
			String mensaje = String.format("Se ha producido un error al cerrar la conexión para el colegio %s",
					this.idInstitucion);
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
	public List<DocuShareObjectVO> getContenidoCollection(String collection, String antPath) throws Exception {

		if (collection == null || collection.trim().equals("")) {
			throw new IllegalArgumentException("El nombre de la colección no puede ser nula o vacía");
		}
		if (MODO_DEBUG_LOCAL) {
			return getContenidoCollectionMODO_DEBUG_LOCAL(collection);
		}

		List<DocuShareObjectVO> list = new ArrayList<DocuShareObjectVO>();

		try {
			createSession();
			log.debug("Recuperando contenido collection " + collection + " del colegio " + this.idInstitucion);

			DSHandle dsHandle = new DSHandle(collection);
			DSCollection dsCollectionParent = (DSCollection) dssession.getObject(dsHandle);

			if (dsCollectionParent != null) {
				log.debug("Se ha encontrado la collection " + dsCollectionParent.getHandle() + " para el colegio "
						+ this.idInstitucion);
				DSObjectIterator it = dsCollectionParent.children(null);
				if (it != null) {
					log.debug("Número de hijos de la collection " + collection + ": "
							+ dsCollectionParent.getChildCount() + " del colegio " + this.idInstitucion);
					List<DocuShareObjectVO> listDir = new ArrayList<DocuShareObjectVO>();
					List<DocuShareObjectVO> listArch = new ArrayList<DocuShareObjectVO>();

					DSObject dsObject = null;

					while (it.hasNext()) {
						dsObject = it.nextObject();
						log.debug("Encontrado hijo \"" + dsObject.getHandle() + "\" dentro de la collection "
								+ collection + " del colegio " + this.idInstitucion);

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

							dsObj.setTitle(dsObject.getTitle());
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

					// Collections.sort(listDir);
					// Collections.sort(listArch, new Comparator<DocuShareObjectVO>() {
					// public int compare(DocuShareObjectVO o1, DocuShareObjectVO o2) {
					// return (-1) * o1.getFechaModificacion().compareTo(o2.getFechaModificacion());
					// }
					// });

					list.addAll(listDir);
					list.addAll(listArch);
				}
			}

		} catch (Exception e) {
			String mensaje = String.format(
					"Se ha producido un error al obtener el contenido de la coleccion con identificadorDS = '%s' para el colegio %s",
					collection, this.idInstitucion);
			log.error(mensaje, e);
			throw new Exception(mensaje, e);
		} finally {
			close();
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
	public File getDocument(String title) throws Exception {

		if (MODO_DEBUG_LOCAL) {
			return getDocumentMODO_DEBUG_LOCAL(title);
		}

		// Extraer propiedad
		GenPropertiesExample genPropertiesExampleP = new GenPropertiesExample();
		genPropertiesExampleP.createCriteria().andParametroEqualTo("sjcs.directorioFisicoTemporalSJCSJava");
		List<GenProperties> genPropertiesPath = genPropertiesMapper.selectByExample(genPropertiesExampleP);

		String rutaDirectorioTemp = genPropertiesPath.get(0).getValor();

		File fileParent = new File(rutaDirectorioTemp);

		File file = null;

		try {
			createSession();

			DSDocument dsDocument = (DSDocument) dssession.getObject(new DSHandle(title));

			DSContentElement[] dsContentElements = dsDocument.getContentElements();
			for (int j = 0; j < dsContentElements.length; j++) {
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
					title, this.idInstitucion);
			log.error(mensaje, e);

		} finally {
			close();
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
	public String createCollectionNoColegiado(String collectionTitle, String collectionSummary) throws Exception {
		return createCollection(ID_DOCUSHARE_NOCOLEGIADO, collectionTitle, collectionSummary);
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
	public String createCollectionCenso(String collectionTitle, String collectionSummary) throws Exception {
		return createCollection(ID_DOCUSHARE_CENSO, collectionTitle, collectionSummary);
	}
	


	
	/**
	 * 
	 * @param collectionTitle Nombre de la carpeta o collecion que se quiere crear
	 * @param collectionSummary Descripcion de la carpeta o collecion que se quiere crear
	 * @return
	 * @throws SIGAException
	 * @throws ClsExceptions
	 */
	private String createCollection(String ID_DOCUSHARE, String collectionTitle, String collectionSummary) throws Exception{

	
		String identificadorDS = null;				
		createSession();
		
		try {
			

			List<GenParametros> config = new ArrayList<GenParametros>();
				GenParametros param = new GenParametros();
				ParametroRequestDTO parametroRequestDTO = new ParametroRequestDTO();
				parametroRequestDTO.setIdInstitucion(String.valueOf(this.idInstitucion));
				parametroRequestDTO.setModulo("GEN");
				parametroRequestDTO.setParametrosGenerales(ID_DOCUSHARE);
				StringDTO paramRequest = genParametrosMapper.getParameterFunction(0, parametroRequestDTO );
				param.setParametro(ID_DOCUSHARE);
				param.setValor(paramRequest.getValor());
				config.add(param);

			
			String idExpedientes = config.get(0).getValor();
			log.debug("ID_DOCUSHARE=" + idExpedientes + " para el colegio " + this.idInstitucion);
			
			DSCollection dsCollectionParent = (DSCollection) dssession.getObject(new DSHandle(idExpedientes));			

			DSClass collectionClass = dssession.getDSClass(DSCollection.classname);
			DSProperties collectionPrototype = collectionClass.createPrototype();
			collectionPrototype.setPropValue(DSCollection.title, collectionTitle);
			collectionPrototype.setPropValue(DSCollection.summary, collectionSummary);

			DSHandle dsHandle = dssession.createObject(collectionPrototype, DSLinkDesc.containment, dsCollectionParent, null, null);
			identificadorDS = dsHandle.toString();
		
		} catch (Exception e) {
			//"expedientes.docushare.error.crearColeccion"
			String mensaje = String.format("Se ha producido un error al crear la collection para el colegio %s e ID_DOCUSHARE = '%s'", this.idInstitucion, ID_DOCUSHARE);
			log.error(mensaje, e);
			
		} finally {
			close();
		}

		return identificadorDS;
	}

}
