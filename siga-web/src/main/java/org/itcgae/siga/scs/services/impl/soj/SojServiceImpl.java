package org.itcgae.siga.scs.services.impl.soj;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.DocumentacionAsistenciaItem;
import org.itcgae.siga.DTOs.scs.DocumentacionSojDTO;
import org.itcgae.siga.DTOs.scs.DocumentacionSojItem;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.FichaSojDTO;
import org.itcgae.siga.DTOs.scs.FichaSojItem;
import org.itcgae.siga.DTOs.scs.JusticiableItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ScsAsistencia;
import org.itcgae.siga.db.entities.ScsAsistenciaExample;
import org.itcgae.siga.db.entities.ScsDocumentacionasi;
import org.itcgae.siga.db.entities.ScsDocumentacionsoj;
import org.itcgae.siga.db.entities.ScsEjg;
import org.itcgae.siga.db.entities.ScsEjgExample;
import org.itcgae.siga.db.entities.ScsEjgKey;
import org.itcgae.siga.db.entities.ScsEjgWithBLOBs;
import org.itcgae.siga.db.entities.ScsPersonajg;
import org.itcgae.siga.db.entities.ScsPersonajgExample;
import org.itcgae.siga.db.entities.ScsSoj;
import org.itcgae.siga.db.entities.ScsSojExample;
import org.itcgae.siga.db.entities.ScsSojKey;
import org.itcgae.siga.db.entities.ScsUnidadfamiliarejg;
import org.itcgae.siga.db.entities.ScsUnidadfamiliarejgExample;
import org.itcgae.siga.db.mappers.ScsDocumentacionasiMapper;
import org.itcgae.siga.db.mappers.ScsDocumentacionsojMapper;
import org.itcgae.siga.db.mappers.ScsSojMapper;
import org.itcgae.siga.db.mappers.ScsUnidadfamiliarejgMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsEjgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPersonajgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsSojExtendsMapper;
import org.itcgae.siga.scs.services.soj.ISojService;
import org.itcgae.siga.security.CgaeAuthenticationProvider;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Service
public class SojServiceImpl implements ISojService {

	private Logger LOGGER = Logger.getLogger(SojServiceImpl.class);

	@Autowired
	private CgaeAuthenticationProvider authenticationProvider;

	@Autowired
	private ScsSojExtendsMapper scsSojExtendsMapper;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsPersonajgExtendsMapper scsPersonajgExtendsMapper;

	@Autowired
	private ScsSojExtendsMapper ScsSojExtendsMapper;
	
	@Autowired
	private ScsEjgExtendsMapper scsEjgExtendsMapper;
	
	@Autowired
	private ScsUnidadfamiliarejgMapper scsUnidadfamiliarejgMapper;
	
	@Autowired
	private ScsDocumentacionsojMapper scsDocumentacionsojMapper;

	@Override
	public FichaSojDTO getDetallesSoj(FichaSojItem fichaSojItem, HttpServletRequest request) throws Exception {
		LOGGER.info("getDetallesSoj() -> Entrando al servicio que obtiene los datos de un SOJ");
		FichaSojDTO fichaSojDTO = new FichaSojDTO();
		Error error = new Error();

		// Conseguimos información del usuario logeado
		LOGGER.info("getDetallesSoj() -> Entrando al servicio de autenticación");
		AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		LOGGER.info("getDetallesSoj() -> Saliendo del servicio de autenticación");
		
		if(fichaSojItem.getIdInstitucion() == null) {
			fichaSojItem.setIdInstitucion(String.valueOf(idInstitucion));
		}

		fichaSojDTO.setFichaSojItems(scsSojExtendsMapper.busquedaSoj(fichaSojItem));
		
		LOGGER.info("getDetallesSoj() -> Saliendo del servicio que obtiene los datos de un SOJ");
		return fichaSojDTO;
	}

	@Override
	public DocumentacionSojDTO getDocumentosSOJ(FichaSojItem fichaSojItem, HttpServletRequest request)
			throws Exception {
		LOGGER.info("getDocumentosSOJ() -> Entrando al servicio que obtiene los documentos de un SOJ");
		DocumentacionSojDTO documentacionSOJ = new DocumentacionSojDTO();
		Error error = new Error();

		// Conseguimos información del usuario logeado
		LOGGER.info("getDocumentosSOJ() -> Entrando al servicio de autenticación");
		AdmUsuarios usuario = authenticationProvider.checkAuthentication(request);
		LOGGER.info("getDocumentosSOJ() -> Saliendo del servicio de autenticación");

		LOGGER.info("getDocumentosSOJ() -> Entrando del servicio que obtiene los documentos de un SOJ");
		List<DocumentacionSojItem> documentos = scsSojExtendsMapper.busquedaDocumentosSOJ(fichaSojItem);
		
		documentacionSOJ.setDocumentacionEjgItems(scsSojExtendsMapper.busquedaDocumentosSOJ(fichaSojItem));
		LOGGER.info("getDocumentosSOJ() -> Saliendo del servicio que obtiene los documentos de un SOJ");
		return documentacionSOJ;
	}

	@Override
	public UpdateResponseDTO guardarDatosGenerales(FichaSojItem datos, HttpServletRequest request) throws Exception {

		LOGGER.info(
				"guardarDatosGenerales() -> Entrada al servicio para actualizar los datos asociados Datos Generales del SOJ");

		UpdateResponseDTO responsedto = new UpdateResponseDTO();
		String numero = null;
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		int response = 0;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"guardarDatosGenerales() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"guardarDatosGenerales() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				try {
					// Seleccionamos el SOJ que vamos a actualizar
					if(datos.getAnio() != null && datos.getIdTipoSoj() != null && datos.getNumero() != null) {
					ScsSojKey key = new ScsSojKey();
					key.setIdinstitucion(idInstitucion);
					key.setIdtiposoj(Short.parseShort(datos.getIdTipoSoj()));
					key.setAnio(Short.parseShort(datos.getAnio()));
					key.setNumero(Long.parseLong(datos.getNumero()));
					ScsSoj sojItem = scsSojExtendsMapper.selectByPrimaryKey(key);
					// Una vez tenemos el SOJ, introducimos la informacion seleccionada en la
					// tarjeta
					
					if (sojItem != null) {
						sojItem.setFechaapertura(datos.getFechaApertura());
						if(datos.getIdTipoSojColegio() != null) {
							sojItem.setIdtiposojcolegio(Short.parseShort(datos.getIdTipoSojColegio()));
						}
						if(datos.getIdTipoConsulta() != null) {
							sojItem.setIdtipoconsulta(Short.parseShort(datos.getIdTipoConsulta()));
						}
						if(datos.getIdTipoRespuesta() != null) {
							sojItem.setIdtiporespuesta(Short.parseShort(datos.getIdTipoRespuesta()));
						}
						if(datos.getDescripcionConsulta() != null) {
							sojItem.setDescripcionconsulta(datos.getDescripcionConsulta());
						}
						if(datos.getRespuestaLetrado() != null) {
							sojItem.setRespuestaletrado(datos.getRespuestaLetrado());
						}
						
						LOGGER.info(
								"guardarDatosGenerales() / scsSojExtendsMapper.updateByPrimaryKeySelective() -> Entrada a scsEjgMapper para actualizar el SOJ");

						response = scsSojExtendsMapper.updateByPrimaryKey(sojItem);

						LOGGER.info(
								"guardarDatosGenerales() / scsSojExtendsMapper.updateByPrimaryKeySelective() -> Salida a scsEjgMapper para actualizar el SOJ");
					}
					}else {
						//Si no encontramos el SOJ lo creamos
						ScsSoj sojItem = this.traducirFichaASOJ(datos, idInstitucion, usuarios);
						
						response = scsSojExtendsMapper.insert(sojItem);
						numero = String.valueOf(sojItem.getNumero()) + "*" + sojItem.getNumsoj();
					}

					if (response != 1) {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"guardarDatosGenerales() -> KO. No se ha actualizado los datos de Datos Generales SOJ.");
						throw new Exception("ERROR: No se ha actualizado Datos Generales de SOJ.");
					} else {
						responsedto.setStatus(SigaConstants.OK);
						responsedto.setId(numero);
					}

				} catch (Exception e) {
					responsedto.setStatus(SigaConstants.KO);
					LOGGER.debug(
							"guardarDatosGenerales() -> Se ha producido un error al actualizar Datos Generales de SOJ.",
							e);
				}
			}

		}
		LOGGER.info("guardarServiciosTramitacion() -> Salida del servicio para actualizar Datos Generales de SOJ.");
		return responsedto;
	}
	
	@Override
	@Transactional
	public UpdateResponseDTO asociarEJGaSOJ(List<String> datos, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();

		int response = 1;

		if (idInstitucion != null) {
			LOGGER.debug("GestionEJGServiceImpl.asociarSOJ() -> Entrada para obtener información del usuario logeado");

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.debug("GestionEJGServiceImpl.asociarSOJ() -> Salida de obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.debug("GestionEJGServiceImpl.asociarSOJ() -> Entrada para asociar un EJG a un SOJ");
				try {

					ScsSojExample exampleSOJ = new ScsSojExample();
					exampleSOJ.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdtiposojEqualTo(Short.parseShort(datos.get(3)))
							.andAnioEqualTo(Short.parseShort(datos.get(1))).andNumeroEqualTo(Long.valueOf(datos.get(2)));
					
					List<ScsSoj> soj = scsSojExtendsMapper.selectByExample(exampleSOJ);
					
					ScsEjgExample exampleEJG = new ScsEjgExample();
					exampleEJG.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andAnioEqualTo(Short.valueOf(datos.get(5)))
                            .andNumeroEqualTo(Long.valueOf(datos.get(6)))
							.andIdtipoejgEqualTo(Short.valueOf(datos.get(4)));
					List<ScsEjg> ejg = scsEjgExtendsMapper.selectByExample(exampleEJG);
					
					ScsUnidadfamiliarejgExample exampleUnidadFamiliar = new ScsUnidadfamiliarejgExample();
					exampleUnidadFamiliar.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andAnioEqualTo(ejg.get(0).getAnio())
							.andNumeroEqualTo(ejg.get(0).getNumero())
							.andIdtipoejgEqualTo(ejg.get(0).getIdtipoejg());
					List<ScsUnidadfamiliarejg> unidadFamiliar = scsUnidadfamiliarejgMapper.selectByExample(exampleUnidadFamiliar);
					//response = scsSojExtendsMapper.updateByExampleSelective(record, example);
					if(unidadFamiliar.size() >0) {//Si el ejg ya tiene justiciables y el nuestro no se encuentra entre ellos se inserta una unidad familiar
						boolean existe = false;
						for(ScsUnidadfamiliarejg unidad : unidadFamiliar) {
							if(unidad.getIdpersona().equals(soj.get(0).getIdpersonajg())) {
								existe = true;
								break;
							}
						}
						if(!existe) {
							ScsUnidadfamiliarejg unidadNueva = new ScsUnidadfamiliarejg();
							unidadNueva.setIdinstitucion(idInstitucion);
							unidadNueva.setIdtipoejg(ejg.get(0).getIdtipoejg());
							unidadNueva.setAnio(ejg.get(0).getAnio());
							unidadNueva.setNumero(ejg.get(0).getNumero());
							unidadNueva.setIdpersona(soj.get(0).getIdpersonajg());
							unidadNueva.setSolicitante(Short.valueOf("0"));
							unidadNueva.setFechamodificacion(new Date());
							unidadNueva.setUsumodificacion(usuarios.get(0).getIdusuario());
							int responseUnidad = scsUnidadfamiliarejgMapper.insert(unidadNueva);
						}
					}else {//si el ejg no tiene unidades familiares ponemos el justiciable del soj como solicitante
						//actualizamos el ejg con el idpersonajg del soj
						scsEjgExtendsMapper.updatePersonaJG(ejg.get(0), soj.get(0).getIdpersonajg());
						
						//e insertamos la nueva unidad familiar
						ScsUnidadfamiliarejg unidadNueva = new ScsUnidadfamiliarejg();
						unidadNueva.setIdinstitucion(idInstitucion);
						unidadNueva.setIdtipoejg(ejg.get(0).getIdtipoejg());
						unidadNueva.setAnio(ejg.get(0).getAnio());
						unidadNueva.setNumero(ejg.get(0).getNumero());
						unidadNueva.setIdpersona(soj.get(0).getIdpersonajg());
						unidadNueva.setSolicitante(Short.valueOf("1"));
						unidadNueva.setFechamodificacion(new Date());
						unidadNueva.setUsumodificacion(usuarios.get(0).getIdusuario());
						int responseUnidad = scsUnidadfamiliarejgMapper.insert(unidadNueva);
					}
					//Actualizamos el soj
					ScsSoj record = soj.get(0);
					record.setEjganio(ejg.get(0).getAnio());
					record.setEjgidtipoejg(ejg.get(0).getIdtipoejg());
					record.setEjgnumero(ejg.get(0).getNumero());
					int respuesta = scsSojExtendsMapper.updateByExample(soj.get(0), exampleSOJ);

				} catch (Exception e) {
					LOGGER.debug(
							"SojServiceImpl.asociarEJGaSOJ() -> Se ha producido un error al asociar el EJG al SOJ ",
							e);
					response = 0;
				} finally {
					// respuesta si se actualiza correctamente
					if (response != 1) {

						updateResponseDTO.setStatus(SigaConstants.KO);
						LOGGER.error(
								"GestionEJGServiceImpl.asociarSOJ() -> KO. No se ha asociado ningun elemento");

					} else {
						updateResponseDTO.setStatus(SigaConstants.OK);
					}
				}
			}
		}

		return updateResponseDTO;
	}
	
	private ScsSoj traducirFichaASOJ(FichaSojItem datos, Short idInstitucion, List<AdmUsuarios> usuarios) {
		ScsSoj sojItem = new ScsSoj();
		
		//Campos obligatorios
		sojItem.setAnio(Short.valueOf(datos.getAnio()));
		sojItem.setEstado("A");
		sojItem.setFechamodificacion(new Date());
		sojItem.setUsumodificacion(usuarios.get(0).getIdusuario());
		sojItem.setFechaapertura(datos.getFechaApertura());
		sojItem.setIdtiposoj(Short.valueOf(datos.getIdTipoSoj()));
		sojItem.setNumero(Long.valueOf(scsSojExtendsMapper.selectNuevoNumero(datos, idInstitucion)));
		sojItem.setNumsoj(scsSojExtendsMapper.selectNuevoNumeroSOJ(datos, idInstitucion));
		sojItem.setIdinstitucion(idInstitucion);
		
		//Campos opcionales
		if(datos.getIdTipoSojColegio() != null) {
			sojItem.setIdtiposojcolegio(Short.valueOf(datos.getIdTipoSojColegio()));
		}
		if(datos.getIdTipoConsulta() != null) {
			sojItem.setIdtipoconsulta(Short.valueOf(datos.getIdTipoConsulta()));
		}
		if(datos.getIdTipoRespuesta() != null) {
			sojItem.setIdtiporespuesta(Short.valueOf(datos.getIdTipoRespuesta()));
		}
		if(datos.getDescripcionConsulta() != null) {
			sojItem.setDescripcionconsulta(datos.getDescripcionConsulta());
		}	
		if(datos.getRespuestaLetrado() != null) {
			sojItem.setRespuestaletrado(datos.getRespuestaLetrado());
		}
		if(datos.getIdPersonaJG() != null) {
			sojItem.setIdpersonajg(Long.valueOf(datos.getIdPersonaJG()));
		}
		
		return sojItem;
	}

	@Override
	public UpdateResponseDTO asociarSOJ(HttpServletRequest request, FichaSojItem sojItem) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponse = new UpdateResponseDTO();
		Error error = new Error();
		int affectedRows = 0;
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"asociarSOJ() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"asociarSOJ() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					// Justiciable y SOJ.
					ScsPersonajg scsPersonajg = new ScsPersonajg();
					scsPersonajg.setIdinstitucion(idInstitucion);
					scsPersonajg.setIdpersona(Long.valueOf(sojItem.getJusticiable().getIdPersona()));
					scsPersonajg = scsPersonajgExtendsMapper.selectByPrimaryKey(scsPersonajg);
					ScsSojKey keySoj = new ScsSojKey();
					keySoj.setIdinstitucion(idInstitucion);
					keySoj.setIdtiposoj(Short.parseShort(sojItem.getIdTipoSoj()));
					keySoj.setAnio(Short.parseShort(sojItem.getAnio()));
					keySoj.setNumero(Long.parseLong(sojItem.getNumero()));
					keySoj = scsSojExtendsMapper.selectByPrimaryKey(keySoj);

					if (scsPersonajg != null && keySoj != null) {
						ScsSoj updateSoj = new ScsSoj();
						// Primary Keys
						updateSoj.setIdinstitucion(idInstitucion);
						updateSoj.setIdtiposoj(keySoj.getIdtiposoj());
						updateSoj.setAnio(keySoj.getAnio());
						updateSoj.setNumero(keySoj.getNumero());
						// IdPersona Campo a actualizar.
						updateSoj.setIdpersonajg(Long.valueOf(scsPersonajg.getIdpersona()));
						LOGGER.info(
								"asociarSOJ() / scsSojExtendsMapper.asociarSOJ() -> Entrada a scsEjgMapper para actualizar el SOJ");

						affectedRows = scsSojExtendsMapper.asociarSOJ(updateSoj);

						LOGGER.info(
								"asociarSOJ() / scsSojExtendsMapper.asociarSOJ() -> Salida a scsEjgMapper para actualizar el SOJ");
					}
					/*
					 * if (!UtilidadesString.esCadenaVacia(sojItem.getJusticiable().getIdPersona()))
					 * { scsPersonajg.setIdinstitucion(idInstitucion);
					 * scsPersonajg.setIdpersona(Long.valueOf(sojItem.getJusticiable().getIdPersona(
					 * ))); scsPersonajg =
					 * scsPersonajgExtendsMapper.selectByPrimaryKey(scsPersonajg);
					 * 
					 * scsPersonajg.setNombre(sojItem.getJusticiable().getNombre());
					 * scsPersonajg.setApellido1(sojItem.getJusticiable().getApellido1());
					 * scsPersonajg.setApellido2(sojItem.getJusticiable().getApellido2());
					 * scsPersonajg.setNif(sojItem.getJusticiable().getNif());
					 * scsPersonajg.setTipopersonajg(sojItem.getJusticiable().getTipoPersonajg());
					 * scsPersonajg.setFechamodificacion(new Date());
					 * 
					 * } else { ScsPersonajgExample scsPersonajgExample = new ScsPersonajgExample();
					 * scsPersonajgExample.createCriteria()
					 * .andNifEqualTo(sojItem.getJusticiable().getNif().toUpperCase())
					 * .andIdinstitucionEqualTo(idInstitucion);
					 * scsPersonajgExample.setOrderByClause("FECHAMODIFICACION DESC");
					 * List<ScsPersonajg> personajgs =
					 * scsPersonajgExtendsMapper.selectByExample(scsPersonajgExample);
					 * 
					 * if (personajgs != null && !personajgs.isEmpty()) { scsPersonajg =
					 * personajgs.get(0); scsPersonajg.setNif(sojItem.getJusticiable().getNif());
					 * scsPersonajg.setNombre(sojItem.getJusticiable().getNombre());
					 * scsPersonajg.setApellido1(sojItem.getJusticiable().getApellido1());
					 * scsPersonajg.setApellido2(sojItem.getJusticiable().getApellido2());
					 * scsPersonajg.setFechamodificacion(new Date());
					 * sojItem.getJusticiable().setIdPersona(String.valueOf(scsPersonajg.
					 * getIdpersona())); } }
					 */

					// Actualizamos datos del justiciable
					/*
					 * if (!UtilidadesString.esCadenaVacia(sojItem.getActualizaDatos()) &&
					 * "S".equals(sojItem.getActualizaDatos())) {
					 * 
					 * affectedRows +=
					 * scsPersonajgExtendsMapper.updateByPrimaryKeySelective(scsPersonajg); //
					 * Duplicamos el justiciable } else if ("N".equals(sojItem.getActualizaDatos()))
					 * { String newIdPersona =
					 * scsPersonajgExtendsMapper.getIdPersonajg(idInstitucion).getNewId();
					 * scsPersonajg.setIdpersona(Long.valueOf(newIdPersona) + 1); affectedRows +=
					 * scsPersonajgExtendsMapper.insertSelective(scsPersonajg);
					 * sojItem.getJusticiable().setIdPersona(String.valueOf(scsPersonajg.
					 * getIdpersona()));
					 * 
					 * }
					 */
					if (affectedRows <= 0) {
						updateResponse.setStatus(SigaConstants.KO);
						error.setCode(500);
						error.setDescription("No se ha actualizado ningún registro");
						updateResponse.setError(error);
					} else {
						updateResponse.setStatus(SigaConstants.OK);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("asociarSOJ() / ERROR: " + e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al asociar el SOJ: " + e);
			error.description("Error al asociar el SOJ: " + e);
			updateResponse.setError(error);
		}
		return updateResponse;
	}

	@Override
	public UpdateResponseDTO desasociarSOJ(HttpServletRequest request, FichaSojItem sojItem) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponse = new UpdateResponseDTO();
		Error error = new Error();
		int affectedRows = 0;
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"desasociarSOJ() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"desasociarSOJ() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {

					// Verificar SOJ que existe en BD
					ScsSoj itemSoj = new ScsSoj();
					itemSoj.setIdinstitucion(idInstitucion);
					itemSoj.setIdtiposoj(Short.parseShort(sojItem.getIdTipoSoj()));
					itemSoj.setAnio(Short.parseShort(sojItem.getAnio()));
					itemSoj.setNumero(Long.parseLong(sojItem.getNumero()));
					itemSoj = scsSojExtendsMapper.selectByPrimaryKey(itemSoj);

					// Desasociar el IDPERSONAJG del SOJ.
					if (itemSoj != null) {
						ScsSoj updateSoj = new ScsSoj();
						// Primary Keys
						updateSoj.setIdinstitucion(idInstitucion);
						updateSoj.setIdtiposoj(itemSoj.getIdtiposoj());
						updateSoj.setAnio(itemSoj.getAnio());
						updateSoj.setNumero(itemSoj.getNumero());
						// IdPersona Campo a actualizar.
						updateSoj.setIdpersonajg(null);
						LOGGER.info(
								"desasociarSOJ() / scsSojExtendsMapper.asociarSOJ() -> Entrada a scsSojExtendsMapper para actualizar el SOJ");

						affectedRows = scsSojExtendsMapper.asociarSOJ(updateSoj);

						LOGGER.info(
								"desasociarSOJ() / scsSojExtendsMapper.asociarSOJ() -> Salida a scsSojExtendsMapper para actualizar el SOJ");

					}
					if (affectedRows <= 0) {
						updateResponse.setStatus(SigaConstants.KO);
						error.setCode(500);
						error.setDescription("No se ha actualizado ningún registro");
						updateResponse.setError(error);
					} else {
						updateResponse.setStatus(SigaConstants.OK);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("desasociarSOJ() / ERROR: " + e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al desasociar el SOJ: " + e);
			error.description("Error al desasociar el SOJ: " + e);
			updateResponse.setError(error);
		}
		return updateResponse;
	}

	@Override
	public UpdateResponseDTO guardarServiciosTramitacion(HttpServletRequest request, FichaSojItem sojItem) {

		LOGGER.info(
				"guardarServiciosTramitacion() -> Entrada al servicio para actualizar los datos asociados a los servicios de tramitacion de SOJ");

		UpdateResponseDTO responsedto = new UpdateResponseDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		int response = 0;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"guardarServiciosTramitacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"guardarServiciosTramitacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				try {

					// Verificar SOJ que existe en BD
					ScsSoj itemSoj = new ScsSoj();
					itemSoj.setIdinstitucion(idInstitucion);
					itemSoj.setIdtiposoj(Short.parseShort(sojItem.getIdTipoSoj()));
					itemSoj.setAnio(Short.parseShort(sojItem.getAnio()));
					itemSoj.setNumero(Long.parseLong(sojItem.getNumero()));
					itemSoj = scsSojExtendsMapper.selectByPrimaryKey(itemSoj);

					// Una vez tenemos el EJG, introducimos la informacion seleccionada en la
					// tarjeta
					if (itemSoj != null) {
						ScsSoj updateSoj = new ScsSoj();
						updateSoj = itemSoj;
						updateSoj.setIdguardia(Integer.valueOf(sojItem.getIdGuardia()));
						updateSoj.setIdturno(Integer.valueOf(sojItem.getIdTurno()));
						// Persona de la tarjeta de Servicio de Tramitación (Opcional)
						if (!sojItem.getIdPersona().equals("")) {
							updateSoj.setIdpersona(Long.parseLong(sojItem.getIdPersona()));
						} else {
							updateSoj.setIdpersona(null);
						}
						LOGGER.info(
								"guardarServiciosTramitacion() / scsEjgMapper.updateByPrimaryKeySelective() -> Entrada a scsEjgMapper para actualizar el SOJ");

						response = scsSojExtendsMapper.updateByPrimaryKey(updateSoj);

						LOGGER.info(
								"guardarServiciosTramitacion() / scsEjgMapper.updateByPrimaryKeySelective() -> Salida a scsEjgMapper para actualizar el SOJ");
					}

					if (response != 1) {
						responsedto.setStatus(SigaConstants.KO);
						LOGGER.error(
								"guardarServiciosTramitacion() -> KO. No se ha actualizado turno, guardia y letrado asociados a un SOJ.");
						throw new Exception("ERROR: No se ha actualizado turno, guardia y letrado asociados a un SOJ.");
					} else {
						responsedto.setStatus(SigaConstants.OK);
					}

				} catch (Exception e) {
					responsedto.setStatus(SigaConstants.KO);
					LOGGER.debug(
							"guardarServiciosTramitacion() -> Se ha producido un error al actualizar turno, guardia y letrado asociados a un SOJ.",
							e);
				}
			}

		}
		return responsedto;

	}

	@Override
	public InsertResponseDTO subirDocumentoSOJ(List<DocumentacionSojItem> documentacionesSOJ,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		ObjectMapper objectMapper = new ObjectMapper();
		int affectedRows = 0;
		int idDocumentacion = 1;
		boolean actualizarDoc = false;
		try {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.subirDocumentoDesigna() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.subirDocumentoDesigna() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				// Comprobar las Documentaciones que son nuevas para crear.
				if (documentacionesSOJ != null) {
					for (DocumentacionSojItem documentacion : documentacionesSOJ) {
						// TRUE - Entonces Creamos el nuevo documento.
						if (documentacion.getNuevo()) {
							FichaSojItem sojItem = new FichaSojItem();
							List<DocumentacionSojItem> documentacionesExistentes = null;
							sojItem.setAnio(documentacion.getAnio());
							sojItem.setNumero(documentacion.getNumero());
							sojItem.setIdTipoSoj(documentacion.getIdtiposoj());
							sojItem.setIdInstitucion(String.valueOf(idInstitucion));
							// Verificar si existe la ultima Documentacion que vamos a crear
							LOGGER.info(
									"subirDocumentoSOJ() -> Entrando del servicio que obtiene los documentos del SOJ existente");
							documentacionesExistentes = scsSojExtendsMapper.busquedaDocumentosSOJ(sojItem);
							LOGGER.info(
									"subirDocumentoSOJ() -> Saliendo del servicio que obtiene los documentos del SOJ existente");
							// En caso que exista, sumamos el Identificador.
							if (documentacionesExistentes != null && documentacionesExistentes.size() > 0) {
								int indice = documentacionesExistentes.size() - 1;
								idDocumentacion = Integer
										.parseInt(documentacionesExistentes.get(indice).getIdDocumentacion()) + 1;
								documentacion.setIdDocumentacion(String.valueOf(idDocumentacion));
								// En caso que exista, no sumamos el Identificador.
							} else {
								documentacion.setIdDocumentacion(String.valueOf(1));
							}
							
							// Guardamos la documentacion.
							LOGGER.info(
									"subirDocumentoSOJ() -> Entrando del servicio que guarda los documentos del SOJ existente");
							
							ScsDocumentacionsoj documentacionSoj = new ScsDocumentacionsoj();
							documentacionSoj.setAnio(Short.valueOf(documentacion.getAnio()));
							documentacionSoj.setDocumentacion(documentacion.getDocumentacion());
							documentacionSoj.setFechaentrega(documentacion.getFechaPresentacion());
							documentacionSoj.setFechalimite(documentacion.getFechaLimite());
							documentacionSoj.setFechamodificacion(new Date());
							documentacionSoj.setIddocumentacion(Short.valueOf(documentacion.getIdDocumentacion()));
							documentacionSoj.setIdinstitucion(idInstitucion);
							documentacionSoj.setIdtiposoj(Short.valueOf(documentacion.getIdtiposoj()));
							documentacionSoj.setNumero(Long.valueOf(documentacion.getNumero()));
							documentacionSoj.setRegentrada(documentacion.getRegistroEntrada());
							documentacionSoj.setRegsalida(documentacion.getRegistroSalida());
							documentacionSoj.setUsumodificacion(Integer.valueOf(usuarios.get(0).getIdusuario()));
							
							int respuesta = scsDocumentacionsojMapper.insertSelective(documentacionSoj);
							
							LOGGER.info(
									"subirDocumentoSOJ() -> Saliendo del servicio  que guarda los documentos del SOJ existente");
							if (respuesta <= 0) {
								insertResponseDTO.setStatus(SigaConstants.KO);
								error.setCode(500);
								error.setDescription("No se ha insertado ningún registro");
								insertResponseDTO.setError(error);
							} else {
								insertResponseDTO.setStatus(SigaConstants.OK);
							}
						// Modificar documentos si tienen cambios.
						}else {
							ScsDocumentacionsoj documentExists = new ScsDocumentacionsoj();
							documentExists.setIdinstitucion(idInstitucion);
							documentExists.setIdtiposoj(Short.valueOf(documentacion.getIdtiposoj()));
							documentExists.setAnio(Short.valueOf(documentacion.getAnio()));
							documentExists.setNumero(Long.valueOf(documentacion.getNumero()));
							documentExists.setIddocumentacion(Short.valueOf(documentacion.getIdDocumentacion()));
							// Verificar si existe la ultima Documentacion que vamos a crear
							LOGGER.info(
									"subirDocumentoSOJ() -> Entrando del servicio que obtiene el documento del SOJ existente");
							documentExists = scsDocumentacionsojMapper.selectByPrimaryKey(documentExists);
							LOGGER.info(
									"subirDocumentoSOJ() -> Saliendo del servicio que obtiene el documento del SOJ existente");
							
							ScsDocumentacionsoj documentUpdate = new ScsDocumentacionsoj();
							documentUpdate.setIdinstitucion(idInstitucion);
							documentUpdate.setIdtiposoj(Short.valueOf(documentacion.getIdtiposoj()));
							documentUpdate.setAnio(Short.valueOf(documentacion.getAnio()));
							documentUpdate.setNumero(Long.valueOf(documentacion.getNumero()));
							documentUpdate.setIddocumentacion(Short.valueOf(documentacion.getIdDocumentacion()));
							
							// Fecha Limite
							if (!documentacion.getFechaLimite().equals(documentExists.getFechalimite())) {
								documentUpdate.setFechalimite(documentacion.getFechaLimite());
								actualizarDoc = true;
							}
							// Fecha Presentacion
							if (!documentacion.getFechaPresentacion().equals(documentExists.getFechaentrega())) {
								documentUpdate.setFechaentrega(documentacion.getFechaPresentacion());
								actualizarDoc = true;
							}
							// Registro Entrada
							if (!documentacion.getRegistroEntrada().equals(documentExists.getRegentrada())) {
								documentUpdate.setRegentrada(documentacion.getRegistroEntrada());
								actualizarDoc = true;
							}
							// Registro Salida
							if (!documentacion.getRegistroSalida().equals(documentExists.getRegsalida())) {
								documentUpdate.setRegsalida(documentacion.getRegistroSalida());
								actualizarDoc = true;
							}
							// Documentacion
							if (!documentacion.getDocumentacion().equals(documentExists.getDocumentacion())) {
								documentUpdate.setDocumentacion(documentacion.getDocumentacion());
								actualizarDoc = true;
							}
							
							// Actualizamos Documento.
							if (actualizarDoc) {
								LOGGER.info(
										"subirDocumentoSOJ() -> Entrando del servicio para actualizar el SOJ existente");
								int respuesta = scsDocumentacionsojMapper.updateByPrimaryKeySelective(documentUpdate);
								LOGGER.info(
										"subirDocumentoSOJ() -> Saliendo del servicio para actualizar el SOJ el SOJ existente");
								if (respuesta <= 0) {
									insertResponseDTO.setStatus(SigaConstants.KO);
									error.setCode(500);
									error.setDescription("No se ha actualizado ningún registro");
									insertResponseDTO.setError(error);
								} else {
									insertResponseDTO.setStatus(SigaConstants.OK);
								}
							}
						}
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"AsistenciaServiceImpl.subirDocumentoAsistencia() -> Se ha producido un error al subir un fichero perteneciente a la asistencia",
					e);
			error.setCode(500);
			error.setDescription("Error al subir el fichero perteneciente a la asistencia");
			error.setMessage(e.getMessage());
			insertResponseDTO.setError(error);
		}

		return insertResponseDTO;
	}
	
	@Override
	public DeleteResponseDTO eliminarDocumentoSOJ(HttpServletRequest request, List<DocumentacionSojItem> documentos) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		ObjectMapper objectMapper = new ObjectMapper();
		int affectedRows = 0;
		int idDocumentacion = 1;
		try {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
			LOGGER.info(
					"DesignacionesServiceImpl.eliminarDocumentoSOJ() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"DesignacionesServiceImpl.eliminarDocumentoSOJ() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && !usuarios.isEmpty()) {

				// Comprobar las Documentaciones que son nuevas para crear.
				if (documentos != null) {
					for (DocumentacionSojItem documentacion : documentos) {
						// Verificar que existe la documentacion y eliminarla en caso que exista.
							ScsDocumentacionsoj documentExists = new ScsDocumentacionsoj();
							documentExists.setIdinstitucion(idInstitucion);
							documentExists.setIdtiposoj(Short.valueOf(documentacion.getIdtiposoj()));
							documentExists.setAnio(Short.valueOf(documentacion.getAnio()));
							documentExists.setNumero(Long.valueOf(documentacion.getNumero()));
							documentExists.setIddocumentacion(Short.valueOf(documentacion.getIdDocumentacion()));
							// Verificar si existe la ultima Documentacion que vamos a crear
							LOGGER.info(
									"eliminarDocumentoSOJ() -> Entrando del servicio que obtiene el documento del SOJ existente");
							documentExists = scsDocumentacionsojMapper.selectByPrimaryKey(documentExists);
							LOGGER.info(
									"eliminarDocumentoSOJ() -> Saliendo del servicio que obtiene el documento del SOJ existente");
							
							if (documentExists != null) {
								LOGGER.info(
										"subirDocumentoSOJ() -> Entrando del servicio  que elimina el documento del Soj existente");
								int respuesta = scsDocumentacionsojMapper.deleteByPrimaryKey(documentExists);
								LOGGER.info(
										"subirDocumentoSOJ() -> Saliendo del servicio  que elimina el documento del Soj existente");
								if (respuesta <= 0) {
									deleteResponseDTO.setStatus(SigaConstants.KO);
									error.setCode(500);
									error.setDescription("No se ha insertado ningún registro");
									deleteResponseDTO.setError(error);
								} else {
									deleteResponseDTO.setStatus(SigaConstants.OK);
								}
							}
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"AsistenciaServiceImpl.subirDocumentoAsistencia() -> Se ha producido un error al subir un fichero perteneciente a la asistencia",
					e);
			error.setCode(500);
			error.setDescription("Error al subir el fichero perteneciente a la asistencia");
			error.setMessage(e.getMessage());
			deleteResponseDTO.setError(error);
		}

		return deleteResponseDTO;
	}
}
