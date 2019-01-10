package org.itcgae.siga.form.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.AsociarPersonaDTO;
import org.itcgae.siga.DTOs.form.CursoItem;
import org.itcgae.siga.DTOs.form.InscripcionItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenNocolegiado;
import org.itcgae.siga.db.entities.ForCertificadoscurso;
import org.itcgae.siga.db.entities.ForCertificadoscursoExample;
import org.itcgae.siga.db.entities.ForInscripcion;
import org.itcgae.siga.db.entities.ForInscripcionExample;
import org.itcgae.siga.db.entities.PysProductossolicitados;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenNocolegiadoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForCertificadoscursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForInscripcionExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysProductossolicitadosExtendsMapper;
import org.itcgae.siga.form.services.IFichaInscripcionService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FichaInscripcionServiceImpl implements IFichaInscripcionService {

	private Logger LOGGER = Logger.getLogger(FichaInscripcionServiceImpl.class);

	@Autowired
	private ForInscripcionExtendsMapper forInscripcionExtendsMapper;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenNocolegiadoExtendsMapper cenNocolegiadoExtendsMapper;
	
	@Autowired
	private PysProductossolicitadosExtendsMapper pysProductosSolicitadosExtendsMapper;
	
	@Autowired
	private ForCertificadoscursoExtendsMapper forCertificadosCursoExtendsMapper;
	
	@Override
	public CursoItem searchCourse(String idCurso, HttpServletRequest request) {
		LOGGER.info("searchCourse() -> Entrada al servicio para obtener un curso especifico");
		CursoItem cursoItem = new CursoItem();

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			LOGGER.info(
					"searchCourse() / forCursoExtendsMapper.selectByPrimaryKey() -> Entrada a forCursoExtendsMapper para obtener un curso especifico");
			cursoItem = forInscripcionExtendsMapper.searchCourseByIdcurso(idCurso, idInstitucion);
			LOGGER.info(
					"searchCourse() / forCursoExtendsMapper.selectByPrimaryKey() -> Salida de forCursoExtendsMapper para obtener un curso especifico");
		}

		LOGGER.info("searchCourse() -> Salida del servicio para obtener un curso especifico");

		return cursoItem;
	}
	
	@Override
	@Transactional
	public ComboDTO saveInscripcion(InscripcionItem inscripcionItem, HttpServletRequest request) {

		LOGGER.info("saveInscripcion() -> Entrada al servicio para insertar una inscripcion");
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		int response = 0;
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ForInscripcion forInscripcionInsert = new ForInscripcion();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"saveInscripcion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"saveInscripcion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {
					// Insertamos inscripcion en la tabla FOR_INSCRIPCION
					forInscripcionInsert.setIdinstitucion(idInstitucion);
					forInscripcionInsert.setUsumodificacion(usuario.getIdusuario().longValue());
					forInscripcionInsert.setFechamodificacion(new Date());
					forInscripcionInsert.setFechasolicitud(inscripcionItem.getFechaSolicitudDate());
					forInscripcionInsert.setIdcurso(Long.parseLong(inscripcionItem.getIdCurso()));
					forInscripcionInsert.setIdestadoinscripcion(Long.parseLong(inscripcionItem.getIdEstadoInscripcion()));	
					
					LOGGER.info(
							"saveInscripcion() / forInscripcionExtendsMapper.insertSelective(forInscripcionInsert) -> Entrada a forInscripcionExtendsMapper para insertar una inscripcion");
					response = forInscripcionExtendsMapper.insertSelective(forInscripcionInsert);
					LOGGER.info(
							"saveInscripcion() / forInscripcionExtendsMapper.insertSelective(forInscripcionInsert) -> Salida a forInscripcionExtendsMapper para insertar una inscripcion");

					comboItems = forInscripcionExtendsMapper.selectMaxIdInscripcion();
					
				} catch (Exception e) {
					response = 0;
				}
				if (response == 0) {
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					insertResponseDTO.setStatus(SigaConstants.KO);
				} else {
					error.setCode(200);

					insertResponseDTO.setId(Long.toString(forInscripcionInsert.getIdcurso()));
					insertResponseDTO.setError(error);
					insertResponseDTO.setStatus(SigaConstants.OK);
				}
			}
		}
		
		if(comboItems.isEmpty()) {
			comboDTO.getError().description("No se encuentra el idInscripcion");
		}
		
		LOGGER.info("saveInscripcion() -> Salida del servicio para insertar una inscripcion");
		comboDTO.setCombooItems(comboItems);
		
		return comboDTO;
	}
	
	@Override
	@Transactional
	public UpdateResponseDTO updateInscripcion(InscripcionItem inscripcionItem, HttpServletRequest request) {
		LOGGER.info("updateInscripcion() -> Entrada al servicio para modificar la inscripcion");

		int response = 0;
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"updateInscripcion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"updateInscripcion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {
					LOGGER.info(
							"updateInscripcion() / forInscripcionExtendsMapper.updateByExampleSelective() -> Entrada a forInscripcionExtendsMapper para modificar una inscripcion");

					ForInscripcion record = new ForInscripcion();
					record.setFechamodificacion(new Date());
					record.setUsumodificacion(usuario.getIdusuario().longValue());
					
					if(inscripcionItem.getIdPersona() != null)
						record.setIdpersona(inscripcionItem.getIdPersona());
					
					if(inscripcionItem.getEmitirCertificado() != null)
						record.setEmitircertificado(inscripcionItem.getEmitirCertificado().shortValue());
					
					ForInscripcionExample example = new ForInscripcionExample();
					example.createCriteria().andIdinscripcionEqualTo(inscripcionItem.getIdInscripcion());
					
					response = forInscripcionExtendsMapper.updateByExampleSelective(record, example);

					LOGGER.info(
							"updateInscripcion() / forInscripcionExtendsMapper.updateByExampleSelective() -> Salida a forInscripcionExtendsMapper para modificar una inscripcion");

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}

				if (response != 0) {
					error.setCode(200);
					updateResponseDTO.setId(inscripcionItem.getIdCurso().toString());
					updateResponseDTO.setStatus(SigaConstants.OK);
				}
			}
		}

		LOGGER.info("updateInscripcion() -> Salida del servicio para modificar la inscripcion");

		updateResponseDTO.setError(error);
		
		return updateResponseDTO;
	}

	@Override
	public InsertResponseDTO guardarPersona(AsociarPersonaDTO asociarPersona, HttpServletRequest request) {
		InsertResponseDTO insertResponse = new InsertResponseDTO();
		org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
		insertResponse.setError(error);
		int response = 0;
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String dni = UserTokenUtils.getDniFromJWTToken(token);

		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

		try {
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				CenNocolegiado record = new CenNocolegiado();
				// Se pone a cero ya que al ser una persona física no tiene tipo ni sociedad
				record.setTipo("0");
				record.setIdpersona(Long.parseLong(asociarPersona.getIdPersona()));
				record.setIdinstitucion(idInstitucion);
				record.setFechamodificacion(new Date());
				record.setUsumodificacion(usuario.getIdusuario());
				// Para crear un nocoleagiado debemos rellenar campo sociedadsj
				record.setSociedadsj("0");

				LOGGER.info(
						"guardarPersona() / cenNocolegiadoExtendsMapper.insert() -> Entrada a cenNocolegiadoExtendsMapper para insertar un no colegiado");
				response = cenNocolegiadoExtendsMapper.insert(record);
				LOGGER.info(
						"guardarPersona() / cenNocolegiadoExtendsMapper.insert() -> Salida a cenNocolegiadoExtendsMapper para insertar un no colegiado");
			}
		} catch (Exception e) {
			response = 0;
			error.setCode(400);
			error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
			insertResponse.setStatus(SigaConstants.KO);
		}

		if (response != 0) {
			error.setCode(200);
			insertResponse.setStatus(SigaConstants.OK);
		}
		return insertResponse;
	}
	
	@Override
	@Transactional
	public InsertResponseDTO generarSolicitudCertificados(InscripcionItem inscripcionItem, HttpServletRequest request) {

		LOGGER.info("generarSolicitudCertificados() -> Entrada al servicio para generar una solicitud de certificado");
		
		int response = 0;
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		List<ForCertificadoscurso> listCertificadosCurso;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		ForInscripcion forInscripcionInsert = new ForInscripcion();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"generarSolicitudCertificados() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"generarSolicitudCertificados() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {
					// Obtenemos los distintos certificados con los que generaremos las distintas solicitudes
					ForCertificadoscursoExample certificadosCursoExample = new ForCertificadoscursoExample();
					certificadosCursoExample.createCriteria().andIdcalificacionEqualTo(Long.parseLong(UtilidadesString.traduceNota(inscripcionItem.getCalificacion())));
					
					listCertificadosCurso = forCertificadosCursoExtendsMapper.selectByExample(certificadosCursoExample);
					
					for (ForCertificadoscurso forCertificadosCurso : listCertificadosCurso) {
						PysProductossolicitados pysRecord = new PysProductossolicitados();
											
						pysRecord.setIdinstitucion(idInstitucion);
						pysRecord.setIdpeticion(inscripcionItem.getIdPeticionSuscripcion());
						pysRecord.setIdtipoproducto(forCertificadosCurso.getIdtipoproducto()!=null ? forCertificadosCurso.getIdtipoproducto().shortValue() : null);
						pysRecord.setIdproducto(forCertificadosCurso.getIdproducto());
						pysRecord.setIdproductoinstitucion(forCertificadosCurso.getIdproductoinstitucion());
						pysRecord.setIdpersona(inscripcionItem.getIdPersona());
						pysRecord.setIdformapago(Short.valueOf("10"));
						pysRecord.setCantidad(1);
						pysRecord.setAceptado("A");
						pysRecord.setValor(forCertificadosCurso.getPrecio());
						pysRecord.setFechamodificacion(new Date());
						pysRecord.setUsumodificacion(usuario.getIdusuario());
//						pysRecord.setIddireccion(1L);
						pysRecord.setIdinstitucionorigen(idInstitucion);
						pysRecord.setNofacturable("1");
						pysRecord.setFecharecepcionsolicitud(new Date());
						
						LOGGER.info(
								"insertSelective() / pysProductosSolicitadosExtendsMapper.insertSelective(pysRecord) -> Entrada a forInscripcionExtendsMapper para insertar una inscripcion");
						response += pysProductosSolicitadosExtendsMapper.insertSelective(pysRecord);
						
						LOGGER.info(
								"insertSelective() / pysProductosSolicitadosExtendsMapper.insertSelective(pysRecord) -> Salida a forInscripcionExtendsMapper para insertar una inscripcion");
						
					}
					
					if(response == listCertificadosCurso.size()) {
						ForInscripcion recordInscripcion = new ForInscripcion();
						recordInscripcion.setIdinscripcion(inscripcionItem.getIdInscripcion());
						recordInscripcion.setCertificadoemitido(Short.valueOf("1"));
						recordInscripcion.setFechamodificacion(new Date());
						recordInscripcion.setUsumodificacion(usuario.getIdusuario().longValue());
						
						forInscripcionExtendsMapper.updateByPrimaryKeySelective(recordInscripcion);
					}else {
						response = 0;
					}
					
					
					
				} catch (Exception e) {
					response = 0;
				}
				
				if (response == 0) {
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					insertResponseDTO.setStatus(SigaConstants.KO);
				} else {
					error.setCode(200);

					insertResponseDTO.setId(inscripcionItem.getIdCurso());
					insertResponseDTO.setError(error);
					insertResponseDTO.setStatus(SigaConstants.OK);
				}
			}
		}
		
		LOGGER.info("generarSolicitudCertificados() -> Salida del servicio para insertar una inscripcion");
		
		return insertResponseDTO;
	}
	
}
