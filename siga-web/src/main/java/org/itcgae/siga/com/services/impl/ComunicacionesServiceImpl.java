package org.itcgae.siga.com.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
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
import org.itcgae.siga.com.services.IComunicacionesService;
import org.itcgae.siga.com.services.IDialogoComunicacionService;
import org.itcgae.siga.com.services.IEnviosMasivosService;
import org.itcgae.siga.com.services.IPFDService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.constants.SigaConstants.GEN_PARAMETROS;
import org.itcgae.siga.commons.utils.SIGAHelper;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.EcomCola;
import org.itcgae.siga.db.entities.EcomColaParametros;
import org.itcgae.siga.db.entities.EnvCamposenvios;
import org.itcgae.siga.db.entities.EnvDestinatarios;
import org.itcgae.siga.db.entities.EnvDocumentos;
import org.itcgae.siga.db.entities.EnvEnvios;
import org.itcgae.siga.db.entities.EnvEnviosKey;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.entities.ScsJuzgado;
import org.itcgae.siga.db.entities.ScsJuzgadoKey;
import org.itcgae.siga.db.mappers.EcomColaMapper;
import org.itcgae.siga.db.mappers.EcomColaParametrosMapper;
import org.itcgae.siga.db.mappers.EnvCamposenviosMapper;
import org.itcgae.siga.db.mappers.EnvDestinatariosMapper;
import org.itcgae.siga.db.mappers.EnvDocumentosMapper;
import org.itcgae.siga.db.mappers.EnvEnviosMapper;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.mappers.ScsJuzgadoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ConClaseComunicacionExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EcomColaExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvDestinatariosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvEnviosExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ModModeloComunicacionExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

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
	private EnvEnviosExtendsMapper envEnviosExtendsMapper;

	@Autowired
	private EnvEnviosMapper envEnviosMapper;
	
	@Autowired
	private EnvDocumentosMapper envDocumentosMapper;
	
	@Autowired
	private EcomColaParametrosMapper ecomColaParametrosMapper;
	
	@Autowired
	private EcomColaMapper ecomColaMapper;
	
	@Autowired
	private GenParametrosMapper genParametrosMapper;
	
	@Autowired
	private EnvCamposenviosMapper envCamposenviosMapper;
	
	@Autowired
	private EnvDestinatariosMapper envDestinatariosMapper;
	
	@Autowired
	private EcomColaExtendsMapper ecomColaExtendsMapper;
	
	@Autowired
	private ScsJuzgadoMapper scsJuzgadoMapper;
	
	@Autowired
	private IEnviosMasivosService _enviosMasivosService;
	

	/**Realiza la busqueda de comunicaciones **/
	@Override
 	public EnviosMasivosDTO comunicacionesSearch(HttpServletRequest request, EnviosMasivosSearch filtros) {
		LOGGER.info("estadoEnvios() -> Entrada al servicio para obtener combo estado envios");

		EnviosMasivosDTO enviosMasivos = new EnviosMasivosDTO();
		List<EnviosMasivosItem> enviosItemList = new ArrayList<EnviosMasivosItem>();
		
		boolean control = false;

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
					String filePath = _enviosMasivosService.getPathFicheroEnvioMasivo(idInstitucion, Long.parseLong(documentoDTO.getIdEnvio()),null);
					
					String nombreFichero = documentoDTO.getNombreDocumento();
					String idEnvio = documentoDTO.getIdEnvio();
					
					File file = null;
					
					//Para diferenciar entre los dos sistemas de guardado (Como en envios masivos o no)
					if(filePath.contains("\\")) {
						String[] partsPath = filePath.split("\\\\");
						filePath = String.join("\\",Arrays.copyOf(partsPath, partsPath.length-1));
						file = new File(filePath, partsPath[partsPath.length-1]);
					}
					else {
	                    file = new File(filePath, nombreFichero);
					}
					
					
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
					error.setMessage(e.getMessage());
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
    public InsertResponseDTO saveNuevaComm (MultipartHttpServletRequest request) throws SigaExceptions, NumberFormatException, IOException, Exception {
		LOGGER.info("saveNuevaComm() -> Entrada al servicio para insertar una nueva comunicación");
        InsertResponseDTO responsedto = new InsertResponseDTO();
        int response = 0;

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

        if (idInstitucion != null) {
            LOGGER.info(
                    "ComunicacionesServiceImpl.saveNuevaComm() -> Entrada para obtener información del usuario logeado");

            AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

            LOGGER.info(
                    "ComunicacionesServiceImpl.saveNuevaComm() -> Salida de obtener información del usuario logeado");

            if (usuarios != null && usuarios.size() > 0) {
            	
            	//Se comprueba si la institución desde la que se llama al servicio tiene 
            	//el parametro "INTEGRACIONCONPNJ" activado
            	//REVISAR SI HAY IMPLEMENTACIÓN MÁS OPTIMA EN LA BBDD
            	GenParametrosExample parametroExample = new GenParametrosExample();
            	
            	parametroExample.setOrderByClause("IDINSTITUCION DESC");
            	parametroExample.createCriteria().andIdinstitucionIn(Arrays.asList(idInstitucion, (short) 0))
            	.andParametroEqualTo("INTEGRACIONCONPNJ").andModuloEqualTo(SigaConstants.MODULO_COM);
            	
            	List<GenParametros> parametrosInt = genParametrosMapper.selectByExample(parametroExample);
            	
            	//Esto indica que la institución actual no deberia tener acceso a este servicio
            	if(parametrosInt.get(0).getValor().equals("0")) {
            		throw new SigaExceptions("La institucion actual no tiene acceso a servicios del modulo PNJ");
            	}

                String nuevaCommJson = request.getParameter("nuevaComunicacion");

        		ObjectMapper objectMapper = new ObjectMapper();
        		
                NuevaComunicacionItem nuevaComm = objectMapper.readValue(nuevaCommJson,
						new TypeReference<NuevaComunicacionItem>() {
						});

                Iterator<String> itr = request.getFileNames();
            	
                List<MultipartFile> docs = new ArrayList<MultipartFile>();
                
                while (itr.hasNext()) {
                	MultipartFile file = request.getFile(itr.next());
                	docs = nuevaComm.getDocs();
                	docs.add(file);
                	nuevaComm.setDocs(docs);
                }

                LOGGER.info(
                        "ComunicacionesServiceImpl.saveNuevaComm() -> Entrada para insertar insertar una nueva comunicación");
                
                

            	EnvEnvios nuevoEnvio = new EnvEnvios();

            	nuevoEnvio.setUsumodificacion(usuarios.get(0).getIdusuario());
            	nuevoEnvio.setFechamodificacion(new Date());
            	if(nuevaComm.getIdModeloComunicacion() != null) {
            		nuevoEnvio.setIdmodelocomunicacion(Long.valueOf(nuevaComm.getIdModeloComunicacion()));
            	}
            	else {
            		nuevoEnvio.setIdmodelocomunicacion(null);
            	}
            	
            	
            	nuevoEnvio.setIdenvio(null); //Se deja a null ya que dentro del propio insert se recupera el nuevo ID del envio
            	nuevoEnvio.setEnvio("A"); //Determina en que busqueda se muestra. "A" Comunicaiones, "M" envios masivos, ....
            	nuevoEnvio.setFecha(new Date());
            	nuevoEnvio.setDescripcion(nuevaComm.getAsunto());
            	nuevoEnvio.setIdinstitucion(idInstitucion);
            	nuevoEnvio.setGenerardocumento("N");
            	nuevoEnvio.setImprimiretiquetas("N");
            	nuevoEnvio.setIdplantillaenvios(Integer.valueOf(nuevaComm.getIdPlantillaEnvios()));
            	nuevoEnvio.setIdestado((short) 1);//Estado "Pendiente Manual"
            	nuevoEnvio.setIdtipoenvios((short) 6); //Tipo envio "Envío telemático"
            	nuevoEnvio.setFechaprogramada(nuevaComm.getFechaEfecto());
            	
            	LOGGER.info(
                        "ComunicacionesServiceImpl.saveNuevaComm() / envEnviosMapper.insert() -> Entrada a envEnviosMapper para insertar un nuevo envio asociado a la nueva comunicación");

            	
            	response = envEnviosMapper.insert(nuevoEnvio);
            	
            	LOGGER.info(
                        "ComunicacionesServiceImpl.saveNuevaComm() / envEnviosMapper.insert() -> Salida de envEnviosMapper para insertar un nuevo envio asociado a la nueva comunicación");

            	if(response == 0) {
            		throw new SigaExceptions("Error al insertar el envio de una nueva comunicación");
            	}
            	
            	//Se recupera el id del nuevo envio introducido
            	Long newIdEnvio = Long.valueOf(envEnviosExtendsMapper.selectMaxIDEnvio(idInstitucion).getNewId());
            	

            	LOGGER.info(
                        "ComunicacionesServiceImpl.saveNuevaComm() / envCamposEnviosMapper.insert() -> Entrada a envCamposEnviosMapper para insertar los valores de los campos de un nuevo envio asociado a la nueva comunicación. Id Envío: " + newIdEnvio);

            	//Vamos a utilizar los campos de valores genericos definidos en la tabla ENV_CAMPOS e introduciremos valores para ellos asociados
            	//Con nuestro envio además del asunto y el cuerpo en la tabla ENV_CAMPOSENVIOS
            	
            	EnvCamposenvios campo = new EnvCamposenvios();
            	
            	campo.setIdinstitucion(idInstitucion);
            	campo.setIdenvio(newIdEnvio);
            	campo.setFechamodificacion(new Date());
            	campo.setUsumodificacion(usuarios.get(0).getIdusuario());
            	
            	
            	//ASUNTO
            	campo.setIdcampo((short) 1);
            	campo.setTipocampo("E");
            	campo.setValor(nuevaComm.getAsunto());
            	
            	response = envCamposenviosMapper.insert(campo);
            	
            	if(response == 0) {
            		throw new SigaExceptions("Error al insertar el campo \"Asunto\" como campo del envio para una nueva comunicación");
            	}

            	//Introducimos el juzgado como destinatario
            	ScsJuzgadoKey juzgadoKey = new ScsJuzgadoKey();
            	
            	juzgadoKey.setIdinstitucion(idInstitucion);
            	juzgadoKey.setIdjuzgado(Long.valueOf(nuevaComm.getJuzgado()));
            	
            	ScsJuzgado juzgadoSelecc = scsJuzgadoMapper.selectByPrimaryKey(juzgadoKey);
            	
            	EnvDestinatarios destinatario = new EnvDestinatarios();
            	
            	destinatario.setIdinstitucion(idInstitucion);
            	destinatario.setIdenvio(newIdEnvio);
            	destinatario.setIdpersona(-juzgadoSelecc.getIdjuzgado()); //REVISAR: Se introduce el id del juzgado NEGATIVO como el idpersona para diferenciar
            	destinatario.setTipodestinatario("SCS_JUZGADO"); //Se introduce la tabla SCS_JUZGADO al realizar un envio a una organismo judicial
            	destinatario.setNombre(juzgadoSelecc.getNombre());
            	destinatario.setDomicilio(juzgadoSelecc.getDomicilio());
            	destinatario.setNombre(juzgadoSelecc.getNombre());
            	destinatario.setCodigopostal(juzgadoSelecc.getCodigopostal());
            	destinatario.setFax1(juzgadoSelecc.getFax1());
            	destinatario.setIdprovincia(juzgadoSelecc.getIdprovincia());
            	destinatario.setIdpoblacion(juzgadoSelecc.getIdpoblacion());
            	destinatario.setMovil(juzgadoSelecc.getMovil());
            	destinatario.setCorreoelectronico(juzgadoSelecc.getEmail());
            	
            	destinatario.setFechamodificacion(new Date());
            	destinatario.setUsumodificacion(usuarios.get(0).getIdusuario());

                	
                response = envDestinatariosMapper.insert(destinatario);
                	
                if(response == 0) {
                	throw new SigaExceptions("Error al insertar el campo el juzgado como el destinatario del envio para una nueva comunicación");
                }

            	//MENSAJE (CUERPO)
            	campo.setIdcampo((short) 2);
            	campo.setTipocampo("E");
            	campo.setValor(nuevaComm.getMensaje());
            	
            	response = envCamposenviosMapper.insert(campo);
            	
            	if(response == 0) {
            		throw new SigaExceptions("Error al insertar el campo \"Cuerpo\" como campo del envio para una nueva comunicación");
            	}
            	
            	//FECHA EFECTO
            	campo.setIdcampo((short) 6);
            	campo.setTipocampo("A");
            	campo.setValor(nuevaComm.getFechaEfecto().toString());
            	
            	response = envCamposenviosMapper.insert(campo);
            	
            	if(response == 0) {
            		throw new SigaExceptions("Error al insertar el campo \"Fecha Efecto\" como campo del envio para una nueva comunicación");
            	}
            	
            	//NIG
            	if(nuevaComm.getNig() != null && nuevaComm.getNig().trim() != "") {
	            	campo.setIdcampo((short) 7);
	            	campo.setTipocampo("A");
	            	campo.setValor(nuevaComm.getNig());
	            	
	            	response = envCamposenviosMapper.insert(campo);
	            	
	            	if(response == 0) {
	            		throw new SigaExceptions("Error al insertar el campo \"Nig\" como campo del envio para una nueva comunicación");
	            	}
            	}
            	
            	
            	//N PROCEDIMIENTO
            	if(nuevaComm.getNumProcedimiento() != null && nuevaComm.getNumProcedimiento().trim() != "") {
	            	campo.setIdcampo((short) 8);
	            	campo.setTipocampo("A");
	            	campo.setValor(nuevaComm.getNumProcedimiento());
	            	
	            	response = envCamposenviosMapper.insert(campo);
	            	
	            	if(response == 0) {
	            		throw new SigaExceptions("Error al insertar el campo \"N Procedimiento\" como campo del envio para una nueva comunicación");
	            	}
            	}
            	
            	LOGGER.info(
                        "ComunicacionesServiceImpl.saveNuevaComm() / envCamposEnviosMapper.insert() -> Salida de envCamposEnviosMapper para insertar los valores de los campos de un nuevo envio asociado a la nueva comunicación");

                	
                	EcomCola elementoCola = new EcomCola();

                	//No se introduce esta nueva id ya que el valor del id se asigna automaticamente dentro del propio método de insert
                	String newIdEcomCola = ecomColaExtendsMapper.getNewId().getNewId();
                	newIdEcomCola = Long.toString((Long.valueOf(newIdEcomCola)+1));
                	
//                    elementoCola.setIdecomcola(Long.valueOf(newIdEcomCola));
                	
                	elementoCola.setFechacreacion(new Date());
                	elementoCola.setIdinstitucion(idInstitucion);
                	elementoCola.setIdestadocola((short) 1);//Todos los elementos nuevos de la cola eCOM se inicializan con estado uno para que eCOM los procese
                	elementoCola.setIdoperacion(80); //Referencia al servicio creado por Jesus Roman (ECOM_OPERACION)
                	elementoCola.setReintento(0);//Numero de reintentos realizados
                	elementoCola.setFechamodificacion(new Date());
                	elementoCola.setUsumodificacion(usuarios.get(0).getIdusuario());
                	
                	LOGGER.info(
                            "ComunicacionesServiceImpl.saveNuevaComm() / ecomColaMapper.insert() -> Entrada a ecomColaMapper para insertar una fila en la cola eCOM");

                	response = ecomColaMapper.insert(elementoCola);
                	
                	LOGGER.info(
                            "ComunicacionesServiceImpl.saveNuevaComm() / ecomColaMapper.insert() -> Salida de ecomColaMapper para insertar una fila en la cola eCOM");

                	if(response == 0) {
                		throw new SigaExceptions("Error al insertar el envio de una nueva comunicación en la cola de eCOM");
                	}
                	
                	
                	LOGGER.info(
                            "ComunicacionesServiceImpl.saveNuevaComm() / insertParametrosNewComm() -> Entrada insertParametrosNewComm() para insertar los parametros para la nueva fila de eCOM");

                	
                	//Insertamos los distintos parametros de eCOM a partir de los campos rellenados 
                	//del formulario de la nueva comunicación
                	insertParametrosNewComm(nuevaComm.getIdTipoMensaje(), newIdEcomCola, idInstitucion, newIdEnvio);
                	
                	LOGGER.info(
                            "ComunicacionesServiceImpl.saveNuevaComm() / insertParametrosNewComm() -> Salida de insertParametrosNewComm() para insertar los parametros para la nueva fila de eCOM");

                	
                	
                	
                	if(!nuevaComm.getDocs().isEmpty()) {
                		LOGGER.info(
                                "ComunicacionesServiceImpl.saveNuevaComm() / subirDocumentosNewComm() -> Entrada a subirDocumentosNewComm para insertar los documentos asociados a la comunicación");

                		
                		this.subirDocumentosNewComm(request, nuevaComm, newIdEnvio);
                		
                		LOGGER.info(
                                "ComunicacionesServiceImpl.saveNuevaComm() / subirDocumentosNewComm() -> Salida de subirDocumentosNewComm para insertar los documentos asociados a la comunicación");

                	}
                	
                	
                	
                    LOGGER.debug(
                            "ComunicacionesServiceImpl.saveNuevaComm() -> Salida del servicio para insertar una nueva comunicación");
            }
        }

        responsedto.setStatus(SigaConstants.OK);
        
        
        return responsedto;
	}
	
	
    public InsertResponseDTO subirDocumentosNewComm(HttpServletRequest request, NuevaComunicacionItem nuevaComm, Long idEnvio) throws SigaExceptions, NumberFormatException, IOException {

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
        AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
            exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
            LOGGER.info(
                    "ComunicacionesServiceImpl.subirDocumentosNewComm() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

            List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

            LOGGER.info(
                    "ComunicacionesServiceImpl.subirDocumentosNewComm() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

            if (usuarios != null && !usuarios.isEmpty()) {

            	uploadFileCom(idEnvio, idInstitucion, nuevaComm.getDocs(), usuarios.get(0).getIdusuario());
        		
                

            }

        return insertResponseDTO;
    }
    
//    private Long uploadFileCom(byte[] bytes, Integer idUsuario, Short idInstitucion, String nombreFichero,
//            String extension, Long idEnvio) {
//
//		FicheroVo ficheroVo = new FicheroVo();
//		
//		String directorioFichero = getPathFicheroNuevaCom(idInstitucion, idEnvio);
//		ficheroVo.setDirectorio(directorioFichero);
//		ficheroVo.setNombre(nombreFichero);
//		ficheroVo.setDescripcion("Fichero asociado al envio " + ficheroVo.getNombre());
//		
//		ficheroVo.setIdinstitucion(idInstitucion);
//		ficheroVo.setFichero(bytes);
//		ficheroVo.setExtension(extension.toLowerCase());
//		
//		ficheroVo.setUsumodificacion(idUsuario);
//		ficheroVo.setFechamodificacion(new Date());
//		ficherosServiceImpl.insert(ficheroVo);
//		
//		SIGAServicesHelper.uploadFichero(directorioFichero, ficheroVo.getNombre(), ficheroVo.getFichero());
//		
//		return ficheroVo.getIdfichero();
//	}
    
    private void insertParametrosNewComm(String idTipoMensaje, String newIdEcomCola, Short idInstitucion, Long idEnvio) throws SigaExceptions {
    	int response = 0;
    	
    	EcomColaParametros parametro = new EcomColaParametros();
    	
    	parametro.setIdecomcola(Long.valueOf(newIdEcomCola));
    	
    	
    	 parametro.setClave("TIPOMENSAJE");
    	 //VALORES: 30 (ESTANDAR), 31 (TURNO OFICIO), 32 (HONORARIOS PROFESIONALES) Y 33 (JUSTICIA GRATUITA)
         if(idTipoMensaje != null) {
        	 parametro.setValor(idTipoMensaje);
         }
         else {
        	 throw new SigaExceptions("Se debe introducir un valor de \"idTipoMensaje\" para poder crear un nuevo envío telemático");
         }
         	
         response = ecomColaParametrosMapper.insert(parametro);
         	
         if(response == 0) {
         	throw new SigaExceptions("Error al insertar el campo \"TIPOMENSAJE\" como parametro en la cola eCOM para una nueva comunicación");
         }
    	
    	
        parametro.setClave("IDDENVIO");
        parametro.setValor(idEnvio.toString());
        	
        response = ecomColaParametrosMapper.insert(parametro);
        	
        if(response == 0) {
        	throw new SigaExceptions("Error al insertar el campo \"IDDENVIO\" como parametro en la cola eCOM para una nueva comunicación");
        }
    	
        parametro.setClave("IDINSTITUCION");
        parametro.setValor(idInstitucion.toString());
        	
        response = ecomColaParametrosMapper.insert(parametro);
        	
        if(response == 0) {
        	throw new SigaExceptions("Error al insertar el campo \"IDINSTITUCION\" como parametro en la cola eCOM para una nueva comunicación");
        }
    }
    

    private ResponseDocumentoDTO uploadFileCom(Long idEnvio, Short idInstitucion, List<MultipartFile> docs, Integer idUsuario) throws IOException, SigaExceptions {
		LOGGER.info("uploadFile() -> Entrada al servicio para subir un documento de envio");

		ResponseDocumentoDTO response = new ResponseDocumentoDTO();
		
		int result = 0;

				String pathFichero = getPathFicheroNuevaCom(idInstitucion, idEnvio);
				
				EnvDocumentos newDocComm = new EnvDocumentos();
        		newDocComm.setFechamodificacion(new Date());
        		newDocComm.setUsumodificacion(idUsuario);
        		newDocComm.setIdenvio(idEnvio);
        		newDocComm.setIdinstitucion(idInstitucion);

				// 1. Coger archivo del request
				LOGGER.debug("uploadFile() -> Coger documento de cuenta bancaria del request");
				for(MultipartFile file : docs) {
	
					String fileNameOriginal = file.getOriginalFilename().split(";")[0];
					Long.toString(System.currentTimeMillis());
	
					try {
	
						File serverFile = new File(pathFichero);
						serverFile.mkdirs();
	
						SIGAHelper.addPerm777(serverFile);
	
						serverFile = new File(serverFile, fileNameOriginal);
						LOGGER.info("uploadFile() -> Ruta del fichero subido: " + serverFile.getAbsolutePath());
						if (serverFile.exists()) {
							LOGGER.error("Ya existe el fichero: " + serverFile.getAbsolutePath());
							throw new FileAlreadyExistsException("El fichero ya existe");
						}
						// stream = new BufferedOutputStream(new FileOutputStream(serverFile));
						// stream.write(file.getBytes());
						FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
						response.setNombreDocumento(fileNameOriginal);
						response.setRutaDocumento(serverFile.getAbsolutePath());
					} catch (FileNotFoundException e) {
						Error error = new Error();
						error.setCode(500);
						error.setDescription(e.getMessage());
						response.setError(error);
						throw new SigaExceptions("uploadFile() -> Error al buscar el documento de envio en el directorio indicado"+e);
					} catch (FileAlreadyExistsException ex) {
						Error error = new Error();
						error.setCode(400);
						error.setDescription(ex.getMessage());
						response.setError(error);
						throw new SigaExceptions("uploadFile() -> El fichero ya existe en el filesystem"+ ex);
					} catch (IOException ioe) {
						Error error = new Error();
						error.setCode(500);
						error.setDescription(ioe.getMessage());
						response.setError(error);
						throw new SigaExceptions("uploadFile() -> Error al guardar el documento de envio en el directorio indicado"+
								ioe);
					} catch (Exception ioe) {
						Error error = new Error();
						error.setCode(500);
						error.setDescription(ioe.getMessage());
						response.setError(error);
						throw new SigaExceptions("uploadFile() -> Error al guardar el documento de envio en el directorio indicado" +
								ioe);
					} finally {
						// close the stream
						LOGGER.info("uploadFile() -> Cierre del stream del documento");
						// stream.close();
					}
					
					newDocComm.setDescripcion(file.getOriginalFilename().split(";")[0]);
            		
            		newDocComm.setIddocumento(null);//El id del documento se recupera automaticamente en el servicio de insert del mapper envDocumentosMapper
            		newDocComm.setPathdocumento(response.getRutaDocumento());
            		
            		LOGGER.info(
                            "ComunicacionesServiceImpl.subirDocumentosNewComm() / envDocumentosMapper.insert() -> Entrada a envDocumentosMapper para insertar la referencia al documento insertado");

            		
            		result = envDocumentosMapper.insert(newDocComm);
            		
            		LOGGER.info(
                            "ComunicacionesServiceImpl.subirDocumentosNewComm() / envDocumentosMapper.insert() -> Salida de envDocumentosMapper para insertar la referencia al documento insertado");

            		if(result == 0) {
                		throw new SigaExceptions("Error al insertar la documentación asociada a una nueva comunicación");
                	}
				}
			
		

		LOGGER.info("uploadFile() -> Salida del servicio para subir un documento de envio");
		return response;
	}
	
	private String getPathFicheroNuevaCom(Short idInstitucion, Long idEnvio) {
		String pathFichero = null;

		GenParametrosKey genParametrosKey = new GenParametrosKey();
		genParametrosKey.setIdinstitucion(SigaConstants.IDINSTITUCION_0_SHORT);
		genParametrosKey.setModulo(SigaConstants.MODULO_ENV);
		genParametrosKey.setParametro(GEN_PARAMETROS.PATH_DOCUMENTOSADJUNTOS.name());

		GenParametros genParametros = genParametrosMapper.selectByPrimaryKey(genParametrosKey);

		if (genParametros == null || genParametros.getValor() == null || genParametros.getValor().trim().equals("")) {
			String error = "La ruta de ficheros de plantilla no está configurada correctamente";
			LOGGER.error(error);
			throw new BusinessException(error);
		}

		// Recuperamos el idenvio para construir la ruta a partir de la fecha de
		// creación
		EnvEnviosKey envEnviosKey = new EnvEnviosKey();
		envEnviosKey.setIdinstitucion(idInstitucion);
		envEnviosKey.setIdenvio(idEnvio);
		EnvEnvios envEnvios = envEnviosMapper.selectByPrimaryKey(envEnviosKey);

		if (envEnvios == null) {
			String error = "No se ha encontrado el envío con idEnvio = " + idEnvio + " para la institución "
					+ idInstitucion;
			LOGGER.error(error);
			throw new BusinessException(error);
		}

		Calendar cal = Calendar.getInstance();
		// seteamos la fecha de creación del envío
		cal.setTime(envEnvios.getFecha());

		pathFichero = genParametros.getValor().trim() + SigaConstants.pathSeparator + String.valueOf(idInstitucion)
				+ SigaConstants.pathSeparator + cal.get(Calendar.YEAR) + SigaConstants.pathSeparator
				+ (cal.get(Calendar.MONTH) + 1) + SigaConstants.pathSeparator + idEnvio;

		return pathFichero;
	}
}

