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
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteKey;
import org.itcgae.siga.db.entities.CenNocolegiado;
import org.itcgae.siga.db.entities.ForCertificadoscurso;
import org.itcgae.siga.db.entities.ForCertificadoscursoExample;
import org.itcgae.siga.db.entities.ForCurso;
import org.itcgae.siga.db.entities.ForInscripcion;
import org.itcgae.siga.db.entities.ForInscripcionExample;
import org.itcgae.siga.db.entities.PysPeticioncomprasuscripcion;
import org.itcgae.siga.db.entities.PysProductossolicitados;
import org.itcgae.siga.db.entities.PysServiciossolicitados;
import org.itcgae.siga.db.entities.PysSuscripcion;
import org.itcgae.siga.db.mappers.PysServiciossolicitadosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenClienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenNocolegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForCertificadoscursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForCursoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForInscripcionExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysPeticioncomprasuscripcionExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysProductossolicitadosExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysServiciosExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysServiciosinstitucionExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysSuscripcionExtendsMapper;
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
	private ForCursoExtendsMapper forCursoExtendsMapper;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenNocolegiadoExtendsMapper cenNocolegiadoExtendsMapper;
	
	@Autowired
	private PysProductossolicitadosExtendsMapper pysProductosSolicitadosExtendsMapper;
	
	@Autowired
	private ForCertificadoscursoExtendsMapper forCertificadosCursoExtendsMapper;
	
	@Autowired
	private CenClienteExtendsMapper cenClienteExtendsMapper;
	
	@Autowired
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;
	
	@Autowired
	private PysServiciosExtendsMapper pysServiciosExtendsMapper;

	@Autowired
	private PysServiciosinstitucionExtendsMapper pysServiciosinstitucionExtendsMapper;


	@Autowired
	private PysPeticioncomprasuscripcionExtendsMapper pysPeticioncomprasuscripcionExtendsMapper;

	@Autowired
	private PysSuscripcionExtendsMapper pysSuscripcionExtendsMapper;

	@Autowired
	private PysServiciossolicitadosMapper pysServiciossolicitadosMapper;

	
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
					ForCurso curso = forCursoExtendsMapper.selectByPrimaryKey(Long.parseLong(inscripcionItem.getIdCurso()));
					// Insertamos inscripcion en la tabla FOR_INSCRIPCION
					forInscripcionInsert.setIdinstitucion(curso.getIdinstitucion());
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
					
					if (null != forInscripcionInsert.getIdpersona()) {
						if (null != curso && (null != curso.getAutovalidacioninscripcion() && curso.getAutovalidacioninscripcion() > 0)) {
							//Comprobamos si hay inscripciones disponibles para validarla.  
							if(this.cursosConPlazasDisponibles(curso.getIdcurso().toString())){
								inscripcionItem.setIdInscripcion(Long.valueOf(comboItems.get(0).getValue()));
								aprobarAutomaticamente(inscripcionItem, usuario,curso.getIdinstitucion());
							}
							
						}
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
					ForInscripcion record = new ForInscripcion();
					record.setFechamodificacion(new Date());
					record.setUsumodificacion(usuario.getIdusuario().longValue());
					
					if(inscripcionItem.getIdPersona() != null)
						record.setIdpersona(inscripcionItem.getIdPersona());
					
					if(inscripcionItem.getEmitirCertificado() != null)
						record.setEmitircertificado(inscripcionItem.getEmitirCertificado().shortValue());
					
					ForInscripcionExample example = new ForInscripcionExample();
					example.createCriteria().andIdinscripcionEqualTo(inscripcionItem.getIdInscripcion());

					// Si el curso al que nos hemos inscrito es de otra institucion,
					// se creara un usuario no colegiado en la institucion del curso.
					// Comprobamos si el curso es de distinta institucion que el usuario al que
					// queremos asignar la inscripcion
					if (inscripcionItem.getIdPersona() != null) {
						CenClienteKey key = new CenClienteKey();
						key.setIdpersona(inscripcionItem.getIdPersona());
						key.setIdinstitucion(Short.valueOf(inscripcionItem.getIdInstitucion()));
						CenCliente cenCliente = cenClienteExtendsMapper.selectByPrimaryKey(key);
						
						// Si la persona no se encuentra en la misma institucion del curso/inscripcion
						// tendremos que insertar el no-colegiado con esa institucion
						if(cenCliente == null) {
							
							cenCliente = new CenCliente();
							cenCliente.setIdpersona(inscripcionItem.getIdPersona());
							cenCliente.setIdinstitucion(Short.valueOf(inscripcionItem.getIdInstitucion()));
							cenCliente.setFechaalta(new Date());
							cenCliente.setCaracter("P");
							cenCliente.setPublicidad(SigaConstants.DB_FALSE);
							cenCliente.setGuiajudicial(SigaConstants.DB_FALSE);
							// Para crear un cliente debemos rellenar comisiones, con que dato?
							cenCliente.setComisiones("0");
							cenCliente.setIdtratamiento(Short.valueOf(SigaConstants.DB_TRUE)); // 1
							cenCliente.setFechamodificacion(new Date());
							cenCliente.setUsumodificacion(usuario.getIdusuario());
							cenCliente.setIdlenguaje(usuario.getIdlenguaje());
							cenCliente.setExportarfoto(SigaConstants.DB_FALSE);

							LOGGER.info(
									"generateExcelInscriptions() / cenClienteMapper.insert() -> Entrada a cenClienteMapper para crear un nuevo colegiado");

							int responseCenCliente = 0;
							responseCenCliente = cenClienteExtendsMapper.insert(cenCliente);

							if (responseCenCliente > 0) {
								CenNocolegiado noColegiadoRecord = new CenNocolegiado();

								noColegiadoRecord.setTipo("0");
								noColegiadoRecord.setIdpersona(inscripcionItem.getIdPersona());
								noColegiadoRecord.setIdinstitucion(Short.valueOf(inscripcionItem.getIdInstitucion()));
								noColegiadoRecord.setFechamodificacion(new Date());
								noColegiadoRecord.setUsumodificacion(usuario.getIdusuario());
								// Para crear un nocoleagiado debemos rellenar campo sociedadsj
								noColegiadoRecord.setSociedadsj("0");

								LOGGER.info(
										"updateInscripcion() / cenNocolegiadoExtendsMapper.insert() -> Entrada a cenNocolegiadoExtendsMapper para insertar un no-colegiado");
								cenNocolegiadoExtendsMapper.insert(noColegiadoRecord);
								LOGGER.info(
										"updateInscripcion() / cenNocolegiadoExtendsMapper.insert() -> Salida a cenNocolegiadoExtendsMapper para insertar un no-colegiado");
							}
						}
						
					/*}else{
						Long idPersona = null;
						// Lo añadimos a la tabla cen_persona
						try {
							
							CenPersona nuevaPersona = new CenPersona();
							List<ComboItem> comboItems = new ArrayList<ComboItem>();
							comboItems = cenPersonaExtendsMapper.selectMaxIdPersona();
							idPersona = Long.valueOf(comboItems.get(0).getValue()) + 1;

							//nuevaPersona.setApellidos1(inscripcionItem.getApellidos());
							nuevaPersona.setApellidos2(null);
							nuevaPersona.setFallecido("0");
							nuevaPersona.setFechamodificacion(new Date());
							nuevaPersona.setFechanacimiento(null);
							nuevaPersona.setIdestadocivil(null);
							nuevaPersona.setIdpersona(idPersona);
							nuevaPersona.setIdtipoidentificacion(Short.valueOf("10"));
							nuevaPersona.setNaturalde(null);
							nuevaPersona.setNifcif(inscripcionItem.getNombrePersona());
							nuevaPersona.setNombre(inscripcionItem.getNifPersona());
							nuevaPersona.setSexo(null);
							nuevaPersona.setUsumodificacion(usuario.getIdusuario());

							LOGGER.info(
									"saveTrainersCourse() / cenPersonaExtendsMapper.insert(nuevaPersona) -> Entrada a cenPersonaExtendsMapper para insertar en la tabla cen_persona al nuevo formador");
							int responseCenPersona = 0;
							responseCenPersona = cenPersonaExtendsMapper.insert(nuevaPersona);

							LOGGER.info(
									"saveTrainersCourse() / cenPersonaExtendsMapper.insert(nuevaPersona) -> Salida a cenPersonaExtendsMapper para insertar en la tabla cen_persona al nuevo formador");
						} catch (Exception e) {
							error.setMessage("Error al insertar al nuevo formador en la tabla cen_persona");
						}

						// Si no existe error lo añadimos a la tabla cen_cliente

						inscripcionItem.setIdPersona(idPersona);
						CenCliente cenCliente = new CenCliente();
						cenCliente.setIdpersona(inscripcionItem.getIdPersona());
						cenCliente.setIdinstitucion(Short.valueOf(inscripcionItem.getIdInstitucion()));
						cenCliente.setFechaalta(new Date());
						cenCliente.setCaracter("P");
						cenCliente.setPublicidad(SigaConstants.DB_FALSE);
						cenCliente.setGuiajudicial(SigaConstants.DB_FALSE);
						// Para crear un cliente debemos rellenar comisiones, con que dato?
						cenCliente.setComisiones("0");
						cenCliente.setIdtratamiento(Short.valueOf(SigaConstants.DB_TRUE)); // 1
						cenCliente.setFechamodificacion(new Date());
						cenCliente.setUsumodificacion(usuario.getIdusuario());
						cenCliente.setIdlenguaje(usuario.getIdlenguaje());
						cenCliente.setExportarfoto(SigaConstants.DB_FALSE);

						LOGGER.info(
								"generateExcelInscriptions() / cenClienteMapper.insert() -> Entrada a cenClienteMapper para crear un nuevo colegiado");

						int responseCenCliente = 0;
						responseCenCliente = cenClienteExtendsMapper.insert(cenCliente);

						if (responseCenCliente > 0) {
							CenNocolegiado noColegiadoRecord = new CenNocolegiado();

							noColegiadoRecord.setTipo("0");
							noColegiadoRecord.setIdpersona(inscripcionItem.getIdPersona());
							noColegiadoRecord.setIdinstitucion(Short.valueOf(inscripcionItem.getIdInstitucion()));
							noColegiadoRecord.setFechamodificacion(new Date());
							noColegiadoRecord.setUsumodificacion(usuario.getIdusuario());
							// Para crear un nocoleagiado debemos rellenar campo sociedadsj
							noColegiadoRecord.setSociedadsj("0");

							LOGGER.info(
									"updateInscripcion() / cenNocolegiadoExtendsMapper.insert() -> Entrada a cenNocolegiadoExtendsMapper para insertar un no-colegiado");
							cenNocolegiadoExtendsMapper.insert(noColegiadoRecord);
							LOGGER.info(
									"updateInscripcion() / cenNocolegiadoExtendsMapper.insert() -> Salida a cenNocolegiadoExtendsMapper para insertar un no-colegiado");
						}
					

						// Si esta registrado en la tabla cen_persona obtenemos su idPersona
						if(inscripcionItem.getIdPersona() != null)
							record.setIdpersona(inscripcionItem.getIdPersona());
					}
*/
					}
					LOGGER.info(
							"updateInscripcion() / forInscripcionExtendsMapper.updateByExampleSelective() -> Entrada a forInscripcionExtendsMapper para modificar una inscripcion");

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

	@Override
	public String compruebaMinimaAsistencia(InscripcionItem inscripcionItem, HttpServletRequest request)
	{
		LOGGER.info("compruebaMinimaAsistencia() -> Entrada del servicio comprobar la minimaAsistencia");
		
		String minimaAsistencia = "";

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		Long idInscripcion = inscripcionItem.getIdInscripcion();

		try {
			NewIdDTO checkMinimaAsistencia = forInscripcionExtendsMapper.checkMinimaAsistencia(idInstitucion,
					idInscripcion);

			minimaAsistencia = checkMinimaAsistencia.getNewId();
		} catch (Exception e) {
			minimaAsistencia = "0";
		}
		
		LOGGER.info("compruebaMinimaAsistencia() -> Salida del servicio comprobar la minimaAsistencia");
		
		return minimaAsistencia;
	}
	
	public List<Long> aprobarAutomaticamente(InscripcionItem inscripcion, AdmUsuarios usuario, Short idInstitucion) {
		// Aprobar inscripción: aprobará la inscripción o inscripciones seleccionadas
		// que estén en estado Pendiente.  
		List<Long> arrayIds = new ArrayList<>();
	// Añadimos a la lista de ids únicamente los ids de las inscripciones que puedan ser aceptadas
		int response = 0;
		PysPeticioncomprasuscripcion pysPeticioncomprasuscripcion = new PysPeticioncomprasuscripcion();
		pysPeticioncomprasuscripcion.setFechamodificacion(new Date());
		pysPeticioncomprasuscripcion.setIdinstitucion(idInstitucion);
		pysPeticioncomprasuscripcion.setUsumodificacion(usuario.getIdusuario());
		pysPeticioncomprasuscripcion.setTipopeticion("A");
		pysPeticioncomprasuscripcion.setIdestadopeticion(Short.valueOf("20"));
		NewIdDTO idPeticion = pysPeticioncomprasuscripcionExtendsMapper
				.selectMaxIdPeticion(idInstitucion);
		pysPeticioncomprasuscripcion.setIdpeticion(Long.valueOf(idPeticion.getNewId()));
		pysPeticioncomprasuscripcion.setIdpersona(inscripcion.getIdPersona());
		pysPeticioncomprasuscripcion.setFecha(new Date());
		pysPeticioncomprasuscripcion.setNumOperacion("1");

		LOGGER.info(
				"autovalidateInscriptionsCourse() / pysPeticioncomprasuscripcionExtendsMapper.insert() -> Entrada a pysPeticioncomprasuscripcionExtendsMapper para insertar un precio servicio");

		response = pysPeticioncomprasuscripcionExtendsMapper
				.insert(pysPeticioncomprasuscripcion);

		LOGGER.info(
				"autovalidateInscriptionsCourse() / pysPeticioncomprasuscripcionExtendsMapper.insert() -> Salida a pysPeticioncomprasuscripcionExtendsMapper para insertar un precio servicio");

		NewIdDTO idservicio = pysServiciosExtendsMapper
				.selectIdServicioByIdCurso(idInstitucion, Long.valueOf(inscripcion.getIdCurso()));
		NewIdDTO idserviciosinstitucion = pysServiciosinstitucionExtendsMapper
				.selectIdServicioinstitucionByIdServicio(idInstitucion,
						Long.valueOf(idservicio.getNewId()));

		PysServiciossolicitados pysServiciossolicitados = new PysServiciossolicitados();
		pysServiciossolicitados.setFechamodificacion(new Date());
		pysServiciossolicitados.setIdinstitucion(idInstitucion);
		pysServiciossolicitados.setUsumodificacion(usuario.getIdusuario());
		pysServiciossolicitados.setAceptado("A");
		pysServiciossolicitados
				.setIdtiposervicios(SigaConstants.ID_TIPO_SERVICIOS_FORMACION);
		pysServiciossolicitados.setIdservicio(Long.valueOf(idservicio.getNewId()));
		pysServiciossolicitados
				.setIdserviciosinstitucion(Long.valueOf(idserviciosinstitucion.getNewId()));
		pysServiciossolicitados
				.setIdpeticion(Long.valueOf(pysPeticioncomprasuscripcion.getIdpeticion()));
		pysServiciossolicitados.setIdpersona(inscripcion.getIdPersona());
		pysServiciossolicitados.setCantidad(1);
		pysServiciossolicitados.setIdformapago(Short.valueOf("10"));

		LOGGER.info(
				"autovalidateInscriptionsCourse() / pysServiciossolicitadosMapper.insert() -> Entrada a pysServiciossolicitadosMapper para insertar el servicio solicitado");

		response = pysServiciossolicitadosMapper.insert(pysServiciossolicitados);

		LOGGER.info(
				"autovalidateInscriptionsCourse() / pysServiciossolicitadosMapper.insert() -> Salida a pysServiciossolicitadosMapper para insertar el servicio solicitado");

		PysSuscripcion pysSuscripcion = new PysSuscripcion();
		pysSuscripcion.setFechamodificacion(new Date());
		pysSuscripcion.setIdinstitucion(idInstitucion);
		pysSuscripcion.setUsumodificacion(usuario.getIdusuario());
		pysSuscripcion.setIdtiposervicios(SigaConstants.ID_TIPO_SERVICIOS_FORMACION);
		pysSuscripcion.setIdservicio(Long.valueOf(idservicio.getNewId()));
		pysSuscripcion
				.setIdserviciosinstitucion(Long.valueOf(idserviciosinstitucion.getNewId()));
		pysSuscripcion
				.setIdpeticion(Long.valueOf(pysPeticioncomprasuscripcion.getIdpeticion()));
		pysSuscripcion.setIdpersona(inscripcion.getIdPersona());
		pysSuscripcion.setCantidad(1);
		pysSuscripcion.setIdformapago(Short.valueOf("10"));
		pysSuscripcion.setFechasuscripcion(new Date());

		CursoItem curso = forCursoExtendsMapper.searchCourseByIdcurso(
				inscripcion.getIdCurso(), idInstitucion,
				usuario.getIdlenguaje());

		pysSuscripcion.setDescripcion(curso.getNombreCurso());
		NewIdDTO idSuscripcion = pysSuscripcionExtendsMapper.selectMaxIdSuscripcion(
				idInstitucion, Long.valueOf(idservicio.getNewId()),
				Long.valueOf(idserviciosinstitucion.getNewId()));
		pysSuscripcion.setIdsuscripcion(Long.valueOf(idSuscripcion.getNewId()));

		LOGGER.info(
				"autovalidateInscriptionsCourse() / pysSuscripcionExtendsMapper.insert() -> Entrada a pysSuscripcionExtendsMapper para insertar la suscripcion a la inscripcion");

		response = pysSuscripcionExtendsMapper.insert(pysSuscripcion);

		LOGGER.info(
				"autovalidateInscriptionsCourse() / pysSuscripcionExtendsMapper.insert() -> Salida a pysSuscripcionExtendsMapper para insertar la suscripcion a la inscripcion");

		
		//Entidad que se va a rellenar con los valores a actualizar
		ForInscripcion record = new ForInscripcion();
		record.setIdestadoinscripcion(3L);
		record.setIdpeticionsuscripcion(Long.valueOf(idPeticion.getNewId()));
		record.setUsumodificacion(usuario.getIdusuario().longValue()); // seteamos el usuario de modificacion
		record.setFechamodificacion(new Date()); // seteamos la fecha de modificación
		
		ForInscripcionExample example = new ForInscripcionExample();
		example.createCriteria().andIdinscripcionEqualTo(inscripcion.getIdInscripcion());
		
		LOGGER.info(
				"updateEstado() / forInscripcionExtendsMapper.updateByExampleSelective() -> Entrada a forInscripcionExtendsMapper para invocar a updateByExampleSelective para actualizar inscripciones según los criterios establecidos");
		response = forInscripcionExtendsMapper.updateByExampleSelective(record, example);

		arrayIds.add(inscripcion.getIdInscripcion());
	


		return arrayIds;
	}
	
	private Boolean cursosConPlazasDisponibles(String idCurso ) {
		Boolean response = Boolean.FALSE;
		LOGGER.info("compruebaPlazas() -> Entrada al servicio comprobar si quedan plazas del curso especificado");
		//Primero comprobamos el numero de plazas que se intenta aprobar de cada curso
			//Primero comprobamos las plazas disponibles del curso
			CursoItem inscripcionesAprobadas = forInscripcionExtendsMapper.compruebaPlazasAprobadas(idCurso);
			if (null == inscripcionesAprobadas) {
				inscripcionesAprobadas = new CursoItem();
				inscripcionesAprobadas.setInscripciones("0");
			}
			ForCurso cursoEntidad = forCursoExtendsMapper.selectByPrimaryKey(Long.parseLong(idCurso));
			Long plazasdisponibles =0L;
			if (null != cursoEntidad) {
				if (null != cursoEntidad.getNumeroplazas()) {
					plazasdisponibles = cursoEntidad.getNumeroplazas() - Long.parseLong(inscripcionesAprobadas.getInscripciones());
				}
			}
			if (plazasdisponibles > 0) {
				response = Boolean.TRUE;
			}
		

		return response;
		
	}
	
	
}
