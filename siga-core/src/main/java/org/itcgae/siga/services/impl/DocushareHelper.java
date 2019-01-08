package org.itcgae.siga.services.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.itcgae.siga.DTOs.cen.DocuShareObjectVO;
import org.itcgae.siga.commons.utils.ReadProperties;
import org.itcgae.siga.commons.utils.SIGAReferences;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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

public class DocushareHelper {

	private static Logger log = LoggerFactory.getLogger(DocushareHelper.class);

	@Autowired
	private GenParametrosMapper genParametrosMapper;

	private static String DOCUSHARE_HOST = "DOCUSHARE_HOST";
	private static String DOCUSHARE_PORT = "DOCUSHARE_PORT";
	private static String DOCUSHARE_DOMAIN = "DOCUSHARE_DOMAIN";

	private static String DOCUSHARE_USER = "DOCUSHARE_USER";
	private static String DOCUSHARE_PASSWORD = "DOCUSHARE_PASSWORD";

	private static String PATH_DOCUSHARE_CENSO = "PATH_DOCUSHARE_CENSO";
	private static String PATH_DOCUSHARE_NOCOLEGIADO = "PATH_DOCUSHARE_NOCOLEGIADO";

	private static String ID_DOCUSHARE_CENSO = "ID_DOCUSHARE_CENSO";
	private static String ID_DOCUSHARE_NOCOLEGIADO = "ID_DOCUSHARE_NOCOLEGIADO";

	private short idInstitucion;
	private DSServer server;
	private DSSession dssession;

	// Para que coja el idioma español !!! Debería cogerlo desde el bundle de
	// xmlbeans pero no lo coge

	private void createSession() throws Exception {

		String datosConexion = null;
		try {
			List<String> listaParametros= new ArrayList<>();
			listaParametros.add(DOCUSHARE_HOST);
			listaParametros.add(DOCUSHARE_PORT);
			listaParametros.add(DOCUSHARE_DOMAIN);
			listaParametros.add(DOCUSHARE_USER);
			listaParametros.add(DOCUSHARE_PASSWORD);
			GenParametrosExample example = new GenParametrosExample();
			example.createCriteria().andParametroIn(listaParametros); 
			List<GenParametros> config = genParametrosMapper.selectByExample(example);
			
			if(config != null && config.size() > 0){
				 
				String host = config.get(0).getValor();
				String port = config.get(0).getValor();
				String domain = config.get(0).getValor();
				
				//parámetros particulares del colegio
				String user = config.get(0).getValor();
				String password = config.get(0).getValor();
				datosConexion = String.format("Parámetros de conexión docushare del colegio %s: HOST:'%s', PORT:'%s', DOMAIN:'%s', USER:'%s', PASSWORD:'%s'", this.idInstitucion, host, port, domain, user, password); 
				
				log.debug(datosConexion);			
			
				int iPort = 0;
				try {
					iPort = Integer.parseInt(port);
				} catch (Exception e) {
					String error = String.format("El parámetro %s no está configurado correctamente para el colegio %s. Comprobar que es un número válido.", DOCUSHARE_PORT, this.idInstitucion);
					log.error(error);
				}
				
				server = DSFactory.createServer(host, iPort);
				log.debug("Creado server con Docushare correctamente para el colegio " + this.idInstitucion);
				dssession = server.createSession(domain, user, password);
				log.debug("Creada session con Docushare correctamente para el colegio " + this.idInstitucion);
				
			}
			
//			GenParametrosService genParametrosService = (GenParametrosService) BusinessManager.getInstance().getService(GenParametrosService.class);
//	
//			String host = getValor(genParametrosService, DOCUSHARE_HOST, true);
//			String port = getValor(genParametrosService, DOCUSHARE_PORT, true);
//			String domain = getValor(genParametrosService, DOCUSHARE_DOMAIN, true);
//			
//			//parámetros particulares del colegio
//			String user = getValor(genParametrosService, DOCUSHARE_USER, false);
//			String password = getValor(genParametrosService, DOCUSHARE_PASSWORD, false);
			
//			datosConexion = String.format("Parámetros de conexión docushare del colegio %s: HOST:'%s', PORT:'%s', DOMAIN:'%s', USER:'%s', PASSWORD:'%s'", this.idInstitucion, host, port, domain, user, password); 
//			
//			log.debug(datosConexion);			
//		
//			int iPort = 0;
//			try {
//				iPort = Integer.parseInt(port);
//			} catch (Exception e) {
//				String error = String.format("El parámetro %s no está configurado correctamente para el colegio %s. Comprobar que es un número válido.", DOCUSHARE_PORT, this.idInstitucion);
//				log.error(error);
//			}
//			
//			server = DSFactory.createServer(host, iPort);
//			log.debug("Creado server con Docushare correctamente para el colegio " + this.idInstitucion);
//			dssession = server.createSession(domain, user, password);
//			log.debug("Creada session con Docushare correctamente para el colegio " + this.idInstitucion);
//			
		
		} catch (Exception e) {
			//Se ha producido un error. No se ha podido conectar con el servidor DocuShare.
			String mensaje = "Se ha producido un error. No se ha podido conectar con el servidor DocuShare. " + datosConexion;
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

		String idColl = null;

		createSession();

		try {
//			GenParametrosService genParametrosService = (GenParametrosService) BusinessManager.getInstance()
//					.getService(GenParametrosService.class);
			List<String> listaParametros= new ArrayList<>();
			listaParametros.add(pathRecibido);
		
			GenParametrosExample example = new GenParametrosExample();
			example.createCriteria().andParametroIn(listaParametros); 
			List<GenParametros> config = genParametrosMapper.selectByExample(example);
			
			
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

	public String buscaCollectionCenso(String title) throws Exception {
		return buscaCollection(PATH_DOCUSHARE_CENSO, title);
	}

	public String buscaCollectionNoColegiado(String title) throws Exception {
		return buscaCollection(PATH_DOCUSHARE_NOCOLEGIADO, title);
	}

	/**
	 * Cerramos la session y la conexion con el servidor DocuShare
	 * 
	 * @throws DSException
	 */
	private void close() throws Exception {
		log.debug("Cerrando conexión con Docushare para el colegio " + this.idInstitucion + "...");
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
	public List<DocuShareObjectVO> getContenidoCollection(String collection) throws Exception {

		if (collection == null || collection.trim().equals("")) {
			throw new IllegalArgumentException("El nombre de la colección no puede ser nula o vacía");
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
							DocuShareObjectVO dsObj = new DocuShareObjectVO(DocuShareObjectVO.DocuShareTipo.COLLECTION);
							dsObj.setId(dsObject.getHandle().toString());
							dsObj.setTitle(dsObject.getTitle());
							dsObj.setFechaModificacion(dsObject.getModifiedDate());
							dsObj.setDescription("");
							dsObj.setSummary("");
							dsObj.setOriginalFilename("");
							listDir.add(dsObj);
						} else if (dsObject instanceof DSDocument) {
							DocuShareObjectVO dsObj = new DocuShareObjectVO(DocuShareObjectVO.DocuShareTipo.DOCUMENT);
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
							listArch.add(dsObj);
						}
					}

					Collections.sort(listDir);
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

		ReadProperties rp = new ReadProperties(SIGAReferences.RESOURCE_FILES.SIGA);
		String rutaDirectorioTemp = rp.returnProperty("sjcs.directorioFisicoTemporalSJCSJava");
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

}
