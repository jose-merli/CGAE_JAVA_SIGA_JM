package org.itcgae.siga.com.services.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.cen.FicheroVo;
import org.itcgae.siga.DTOs.com.DatosDocumentoItem;
import org.itcgae.siga.DTOs.com.DestinatarioItem;
import org.itcgae.siga.DTOs.com.DestinatariosDTO;
import org.itcgae.siga.DTOs.com.EnviosMasivosDTO;
import org.itcgae.siga.DTOs.com.EnviosMasivosItem;
import org.itcgae.siga.DTOs.com.EnviosMasivosSearch;
import org.itcgae.siga.DTOs.com.NuevaComunicacionItem;
import org.itcgae.siga.DTOs.com.ResponseDocumentoDTO;
import org.itcgae.siga.DTOs.com.ResponseFileDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.cen.services.impl.FicherosServiceImpl;
import org.itcgae.siga.com.services.IComunicacionesService;
import org.itcgae.siga.com.services.IDialogoComunicacionService;
import org.itcgae.siga.com.services.IEnviosMasivosService;
import org.itcgae.siga.com.services.IPFDService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SIGAServicesHelper;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.EnvDocumentos;
import org.itcgae.siga.db.entities.EnvEnvios;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.entities.ScsDocumentacionejg;
import org.itcgae.siga.db.entities.ScsEjg;
import org.itcgae.siga.db.entities.ScsUnidadfamiliarejg;
import org.itcgae.siga.db.entities.ScsUnidadfamiliarejgExample;
import org.itcgae.siga.db.mappers.EnvDocumentosMapper;
import org.itcgae.siga.db.mappers.EnvEnviosMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ConClaseComunicacionExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvDestinatariosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvEnviosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModModeloComunicacionExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
@Transactional(timeout=2400)
public class ComunicacionesServiceImpl implements IComunicacionesService {

	private Logger LOGGER = Logger.getLogger(ComunicacionesServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private EnvEnviosExtendsMapper _envEnviosExtendsMapper;
	
	@Autowired
	private ConClaseComunicacionExtendsMapper _conClaseComunicacionExtendsMapper;
	
	@Autowired
	private EnvDestinatariosExtendsMapper _envDestinatariosExtendsMapper;
	
	@Autowired
	private ModModeloComunicacionExtendsMapper _modModeloComunicacionExtendsMapper;
	
	@Autowired
	private IDialogoComunicacionService _dialogoComunicacionService;
	
	@Autowired
	private IPFDService pfdService;	
	
	@Autowired
	private IEnviosMasivosService _enviosMasivosService;

	@Autowired
	private GenPropertiesMapper genPropertiesMapper;
	
	@Autowired
	private EnvEnviosExtendsMapper envEnviosExtendsMapper;

	@Autowired
	private EnvEnviosMapper envEnviosMapper;
	
	@Autowired
	private EnvDocumentosMapper envDocumentosMapper;
	
	@Autowired
	private FicherosServiceImpl ficherosServiceImpl;
	

	/**Realiza la busqueda de comunicaciones **/
	@Override
 	public EnviosMasivosDTO comunicacionesSearch(HttpServletRequest request, EnviosMasivosSearch filtros) {
		LOGGER.info("estadoEnvios() -> Entrada al servicio para obtener combo estado envios");

		EnviosMasivosDTO enviosMasivos = new EnviosMasivosDTO();
		List<EnviosMasivosItem> enviosItemList = new ArrayList<EnviosMasivosItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				try {
					enviosItemList = _envEnviosExtendsMapper.selectEnviosComunicacionSearch(idInstitucion, usuario.getIdlenguaje(), filtros);
					if (enviosItemList.size() > 0) {
						enviosMasivos.setEnviosMasivosItem(enviosItemList);
					}
				} catch (Exception e) {
					Error error = new Error();
					error.setCode(500);
					error.setMessage(e.getMessage());
					e.printStackTrace();
					enviosMasivos.setError(error);
				}

			}
		}

		LOGGER.info("estadoEnvios() -> Salida del servicio para obtener combo estado envios");
		return enviosMasivos;
	}

	

	/**Duplica el envío pero mandandolo a Envios masivos**/
	@Override
	public Error reenviar(HttpServletRequest request, EnviosMasivosItem[] envio) {
		LOGGER.info("reenviar() -> Entrada al servicio para reenviar");
		
		// TODO Auto-generated method stub
		LOGGER.info("reenviar() -> Salida del servicio para reenviar");
		return null;
	}



	/**Obtiene los destinatarios del envío, para cargar los destinatarios en la ficha**/
	@Override
	public DestinatariosDTO detalleDestinatarios(HttpServletRequest request, String idEnvio) {
		LOGGER.info("detalleDestinatarios() -> Entrada al servicio para obtener el detalle de los destinatarios del envio");
		
		DestinatariosDTO respuesta = new DestinatariosDTO();
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		try{
			
			if (null != idInstitucion) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				
				if (null != usuarios && usuarios.size() > 0) {
					
					List<DestinatarioItem> destinatarios = _envDestinatariosExtendsMapper.selectDestinatariosComunicaciones(idInstitucion, idEnvio);
					if(destinatarios.size()>0){
						respuesta.setDestinatarios(destinatarios);
					}
					
				}
				
			}
		
		}catch(Exception e){
			Error error = new Error();
			error.setCode(500);
			error.setDescription("Internal Server error");
			error.setMessage(e.getMessage());
			respuesta.setError(error);
			e.printStackTrace();
		}
		
		LOGGER.info("detalleDestinatarios() -> Salida del servicio para obtener el detalle de los destinatarios de envio");
		return respuesta;
	}



	@Override
	public ComboDTO claseComunicacion(HttpServletRequest request) {
		LOGGER.info("claseComunicacion() -> Entrada al servicio para obtener combo clases comunicacion");
		
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			AdmUsuarios usuario = usuarios.get(0);
			
			if (null != usuarios && usuarios.size() > 0) {

				comboItems = _conClaseComunicacionExtendsMapper.selectTipoClaseComunicacion(usuario.getIdlenguaje());
				
				
				comboDTO.setCombooItems(comboItems);
				
			}
		}
		
		
		LOGGER.info("claseComunicacion() -> Salida del servicio para obtener combo clases comunicacion");
		
		
		return comboDTO;
	}

	@Override
	public ComboDTO modelosClasesComunicacion(HttpServletRequest request, String idClaseComunicacion) {

		
		LOGGER.info("modelosClasesComunicacion() -> Entrada al servicio para obtener combo modelos de comunicacion de las clases comunicacion");
		
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			if (null != usuarios && usuarios.size() > 0) {

				comboItems = _modModeloComunicacionExtendsMapper.selectModelosClasesComunicacion(idClaseComunicacion);
					
				
				comboDTO.setCombooItems(comboItems);
				
			}
		}
		
		LOGGER.info("modelosClasesComunicacion() -> Salida del servicio para obtener combo modelos de comunicacion de las clases comunicacion");
		return comboDTO;
	}
	
	
	@Override
	public ResponseFileDTO descargarDocumento(HttpServletRequest request, ResponseDocumentoDTO documentoDTO) {		
		LOGGER.info("descargarDocumento() -> Entrada al servicio para descargar un documento");
		
		Error error = new Error();
		ResponseFileDTO respuesta = new ResponseFileDTO();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			if (null != usuarios && usuarios.size() > 0) {
				try{
					String filePath = _enviosMasivosService.getPathFicheroEnvioMasivo(idInstitucion, Long.valueOf(documentoDTO.getIdEnvio()));
					String nombreFichero = documentoDTO.getNombreDocumento();
					String idEnvio = documentoDTO.getIdEnvio();
					
					File file = new File(filePath, nombreFichero);
					
					if(!file.exists()) {
						file = null;
						
						List<DatosDocumentoItem> listaDocumentos = _dialogoComunicacionService.generarDocumentosEnvio(String.valueOf(idInstitucion), idEnvio);
						
						for(DatosDocumentoItem documento: listaDocumentos) {
							LOGGER.debug("nombreFichero de pantalla : " + nombreFichero);
							LOGGER.debug("nombreFichero generado en este método: " + documento.getFileName());
							//if( nombreFichero != null && nombreFichero.equalsIgnoreCase(documento.getFileName())){
								documento.setFileName(nombreFichero);
								file = new File(documento.getPathDocumento());
								if(file != null && !file.exists()) {
									file = null;
								}
							//}
							
						}
					}
					respuesta.setFile(file);

				}catch(Exception e){
					error.setCode(500);
					error.setDescription(e.getMessage());
					error.setMessage("Error al borrar el documento");
					respuesta.setError(error);
					LOGGER.error("borrarDocumento() -> Error al borrar el documento " + e.getMessage());
				}
				
				
			}
		}
		LOGGER.info("descargarDocumento() -> Salida del servicio para descargar un documento");
		return respuesta;
	}



	@Override
	public String descargarCertificado(HttpServletRequest request, String csv) {
		LOGGER.info("descargarCertificado() -> Entrada al servicio para descargar el certificado del buroSMS del envio");
		
		Error error = new Error();
		ResponseFileDTO respuesta = new ResponseFileDTO();
		String fileBase64 = "";
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			if (null != usuarios && usuarios.size() > 0) {
				try{
					fileBase64 = pfdService.obtenerDocumentoFirmado(csv);

				}catch(Exception e){
					error.setCode(500);
					error.setDescription(e.getMessage());
					error.setMessage("Error al borrar el documento");
					respuesta.setError(error);
					LOGGER.error("borrarDocumento() -> Error al borrar el documento " + e.getMessage());
				}
				
				
			}
		}
		LOGGER.info("descargarCertificado() -> Salida del servicio para descargar el certificado del buroSMS del envio");
		return fileBase64;
	}
	
	@Transactional
	@Override
    public InsertResponseDTO saveNuevaComm (HttpServletRequest request, NuevaComunicacionItem nuevaComm) throws SigaExceptions, NumberFormatException, IOException {
		LOGGER.info("saveNuevaComm() -> Entrada al servicio para insertar una nueva comunicación");
        InsertResponseDTO responsedto = new InsertResponseDTO();
        int response = 0;

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

        if (idInstitucion != null) {
            LOGGER.debug(
                    "ComunicacionesServiceImpl.saveNuevaComm() -> Entrada para obtener información del usuario logeado");

            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

            LOGGER.debug(
                    "ComunicacionesServiceImpl.saveNuevaComm() -> Salida de obtener información del usuario logeado");

            if (usuarios != null && usuarios.size() > 0) {
                LOGGER.debug(
                        "ComunicacionesServiceImpl.saveNuevaComm() -> Entrada para insertar insertar una nueva comunicación");
                	
                	EnvEnvios nuevoEnvio = new EnvEnvios();

                	nuevoEnvio.setUsumodificacion(usuarios.get(0).getIdusuario());
                	nuevoEnvio.setFechamodificacion(new Date());
                	nuevoEnvio.setIdinstitucion(idInstitucion);
                	
                	Long newIdEnvio = Long.valueOf(envEnviosExtendsMapper.selectMaxIDEnvio().getNewId());
                	nuevoEnvio.setIdenvio(newIdEnvio);

                	response = envEnviosMapper.insert(nuevoEnvio);
                	
                	if(response == 0) {
                		throw new SigaExceptions("Error al insertar una nueva comunicación");
                	}
                	
                	this.subirDocumentosNewComm(request, nuevaComm, newIdEnvio);
                	
                    LOGGER.debug(
                            "ComunicacionesServiceImpl.saveNuevaComm() -> Salida del servicio para insertar una nueva comunicación");
            }
        }

        return responsedto;
	}
	
	
    public InsertResponseDTO subirDocumentosNewComm(HttpServletRequest request, NuevaComunicacionItem nuevaComm, Long idEnvio) throws SigaExceptions, NumberFormatException, IOException {

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
        Error error = new Error();
        int response = 0;

            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
            LOGGER.info(
                    "ComunicacionesServiceImpl.subirDocumentosNewComm() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

            LOGGER.info(
                    "ComunicacionesServiceImpl.subirDocumentosNewComm() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

            if (usuarios != null && !usuarios.isEmpty()) {

        		EnvDocumentos newDocComm = new EnvDocumentos();
        		newDocComm.setFechamodificacion(new Date());
        		newDocComm.setUsumodificacion(usuarios.get(0).getIdusuario());
        		newDocComm.setIdenvio(idEnvio);
        		newDocComm.setIdinstitucion(idInstitucion);
        		newDocComm.setPathdocumento(getDirectorioDocumentoCom(idInstitucion));

            	for(File doc : nuevaComm.getDocs()) {
            		
            		newDocComm.setIddocumento(Short.valueOf(uploadFileCom(Files.readAllBytes(doc.toPath()), usuarios.get(0).getIdusuario(), idInstitucion, doc.getName(), FilenameUtils.getExtension(doc.getName())).toString()));
            		newDocComm.setDescripcion(doc.getName());
            		
            		envDocumentosMapper.insert(newDocComm);
            		
            		if(response == 0) {
                		throw new SigaExceptions("Error al insertar la documentación asociada a una nueva comunicación");
                	}
            	}
                

            }

        return insertResponseDTO;
    }
    
    private Long uploadFileCom(byte[] bytes, Integer idUsuario, Short idInstitucion, String nombreFichero,
            String extension) {

		FicheroVo ficheroVo = new FicheroVo();
		
		String directorioFichero = getDirectorioDocumentoCom(idInstitucion);
		ficheroVo.setDirectorio(directorioFichero);
		ficheroVo.setNombre(nombreFichero);
		ficheroVo.setDescripcion("Fichero asociado al envio " + ficheroVo.getNombre());
		
		ficheroVo.setIdinstitucion(idInstitucion);
		ficheroVo.setFichero(bytes);
		ficheroVo.setExtension(extension.toLowerCase());
		
		ficheroVo.setUsumodificacion(idUsuario);
		ficheroVo.setFechamodificacion(new Date());
		ficherosServiceImpl.insert(ficheroVo);
		
		SIGAServicesHelper.uploadFichero(ficheroVo.getDirectorio(), ficheroVo.getNombre(), ficheroVo.getFichero());
		
		return ficheroVo.getIdfichero();
	}
    
    private String getDirectorioDocumentoCom(Short idInstitucion) {

        // Extraemos el path para los ficheros
        GenPropertiesExample genPropertiesExampleP = new GenPropertiesExample();
        genPropertiesExampleP.createCriteria().andParametroEqualTo("gen.ficheros.path");
        List<GenProperties> genPropertiesPath = genPropertiesMapper.selectByExample(genPropertiesExampleP);
        String path = genPropertiesPath.get(0).getValor();

        StringBuffer directorioFichero = new StringBuffer(path);
        directorioFichero.append(idInstitucion);
        directorioFichero.append(File.separator);

        // Extraemos el path concreto para los documentos de los nuevos comunicar
        //Falta por determinar el nombre del parametro
        
        //GenPropertiesExample genPropertiesExampleD = new GenPropertiesExample();
        //genPropertiesExampleD.createCriteria().andParametroEqualTo("scs.ficheros.expedientesJG");
//        List<GenProperties> genPropertiesDirectorio = genPropertiesMapper.selectByExample(genPropertiesExampleD);
//        directorioFichero.append(genPropertiesDirectorio.get(0).getValor());

        return directorioFichero.toString();
    }
}

