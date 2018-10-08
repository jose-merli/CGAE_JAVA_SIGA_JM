package org.itcgae.siga.cen.services.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.EtiquetaUpdateDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.CrearPersonaDTO;
import org.itcgae.siga.DTOs.cen.NoColegiadoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.IFichaDatosGeneralesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteExample;
import org.itcgae.siga.db.entities.CenClienteKey;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoExample;
import org.itcgae.siga.db.entities.CenDatoscolegialesestado;
import org.itcgae.siga.db.entities.CenDatoscolegialesestadoExample;
import org.itcgae.siga.db.entities.CenNocolegiado;
import org.itcgae.siga.db.entities.CenNocolegiadoExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.mappers.CenClienteMapper;
import org.itcgae.siga.db.mappers.CenDatoscolegialesestadoMapper;
import org.itcgae.siga.db.mappers.CenNocolegiadoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDatoscolegialesestadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenEstadocivilExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenNocolegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTratamientoExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FichaDatosGeneralesServiceImpl implements IFichaDatosGeneralesService{

	private Logger LOGGER = Logger.getLogger(FichaDatosGeneralesServiceImpl.class);
	
	@Autowired
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;

	@Autowired
	private CenNocolegiadoExtendsMapper cenNocolegiadoMapper;

	@Autowired
	private CenColegiadoExtendsMapper cenColegiadoMapper;

	@Autowired
	private CenDatoscolegialesestadoExtendsMapper cenDatoscolegialesestadoMapper;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenClienteMapper cenClienteMapper;

	@Autowired
	private CenTratamientoExtendsMapper cenFichaDatosGeneralesExtendsMapper;

	@Autowired
	private CenEstadocivilExtendsMapper cenEstadocivilExtendsMapper;

	@Override
	public ComboDTO getSocietyTypes(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public  UpdateResponseDTO updateColegiado(ColegiadoItem colegiadoItem, HttpServletRequest request) throws ParseException {
//		UpdateResponseDTO response = new UpdateResponseDTO();
		LOGGER.info(
				"updateColegiado() -> Entrada al servicio para actualizar información general de un colegiado");
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		List<CenDatoscolegialesestado> cenDatoscolegialesestado = new ArrayList<CenDatoscolegialesestado>();
	
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"updateColegiado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"updateColegiado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				// 1. obtener colegiado
				
				CenPersona cenPersona = cenPersonaExtendsMapper.selectByPrimaryKey(Long.valueOf(colegiadoItem.getIdPersona()));

				// 2. Actualizamos la tabla Cen_Persona
				
				cenPersona.setIdpersona(Long.parseLong(colegiadoItem.getIdPersona()));
				cenPersona.setNombre(colegiadoItem.getNombre());
				cenPersona.setApellidos1(colegiadoItem.getApellidos1());
				cenPersona.setApellidos2(colegiadoItem.getApellidos2());
				cenPersona.setNifcif(colegiadoItem.getNif());
				cenPersona.setIdtipoidentificacion(Short.parseShort(colegiadoItem.getIdTipoIdentificacion()));
				cenPersona.setSexo(colegiadoItem.getSexo());
				cenPersona.setFechanacimiento(colegiadoItem.getFechaNacimientoDate());
				cenPersona.setNaturalde(colegiadoItem.getNaturalDe());
				if(colegiadoItem.getIdEstadoCivil() != null) {
				cenPersona.setIdestadocivil(Short.parseShort(colegiadoItem.getIdEstadoCivil()));
				}
				cenPersona.setFechamodificacion(new Date());
				cenPersona.setUsumodificacion(usuario.getIdusuario());
				
				CenPersonaExample cenPersonaExample = new CenPersonaExample();
				cenPersonaExample.createCriteria()
						.andIdpersonaEqualTo(Long.valueOf(colegiadoItem.getIdPersona()));
				LOGGER.info(
						"updateColegiado() / cenPersonaExtendsMapper.updateByExampleSelective() -> Entrada a cenPersonaExtendsMapper para actualizar información de colegiado en CEN_PERSONA");

				cenPersonaExtendsMapper.updateByExampleSelective(cenPersona, cenPersonaExample);
				LOGGER.info(
						"updateColegiado() / cenPersonaExtendsMapper.updateByExampleSelective() -> Salida de cenPersonaExtendsMapper para actualizar información de colegiado en CEN_PERSONA");
				


					// 3. Actualiza tabla CEN_CLIENTE

					CenClienteKey key = new CenClienteKey();
					key.setIdpersona(Long.valueOf(colegiadoItem.getIdPersona()));
					key.setIdinstitucion(idInstitucion);

					CenCliente cenCliente = new CenCliente();
					cenCliente = cenClienteMapper.selectByPrimaryKey(key);
						// Por ahora no tenemos cuenta contable, se queda pendiente
						//cenCliente.setAsientocontable(colegiadoItem.getCuentaContable());
					cenCliente.setIdlenguaje(colegiadoItem.getIdLenguaje());
					if(colegiadoItem.getIncorporacionDate()!=null) { 
					cenCliente.setFechaalta(colegiadoItem.getIncorporacionDate());
					}
					cenCliente.setFechamodificacion(new Date());
					cenCliente.setUsumodificacion(usuario.getIdusuario());
					cenCliente.setComisiones(colegiadoItem.getComisiones());
					cenCliente.setIdtratamiento(Short.parseShort(colegiadoItem.getIdtratamiento()));
					cenCliente.setAsientocontable(colegiadoItem.getAsientoContable());
					cenCliente.setCaracter("P");
					cenCliente.setPublicidad(SigaConstants.DB_FALSE);
					cenCliente.setGuiajudicial(SigaConstants.DB_FALSE); 
					
					CenClienteExample cenClienteExample = new CenClienteExample();
					cenClienteExample.createCriteria()
							.andIdpersonaEqualTo(Long.valueOf(colegiadoItem.getIdPersona())).andIdinstitucionEqualTo(idInstitucion);
					LOGGER.info(
							"updateColegiado() / cenClienteMapper.updateByExampleSelective() -> Entrada a cenClienteMapper para actualizar información de colegiado en CEN_CLIENTE");

					cenClienteMapper.updateByExampleSelective(cenCliente, cenClienteExample);
					LOGGER.info(
							"updateColegiado() / cenClienteMapper.updateByExampleSelective() -> Salida de cenClienteMapper para actualizar información de colegiado en CEN_CLIENTE");
					
								//	3.5 Actualiza CEN_COLEGIADO O CEN_NOCOLEGIADO
								if(colegiadoItem.getColegiado() == false || colegiadoItem.getColegiado() == null) {
									CenNocolegiadoExample cenNocolegiadoExample = new CenNocolegiadoExample();
									cenNocolegiadoExample.createCriteria()
											.andIdpersonaEqualTo(Long.valueOf(colegiadoItem.getIdPersona())).andIdinstitucionEqualTo(idInstitucion);
									
									CenNocolegiado cenNocolegiado = new CenNocolegiado();
									cenNocolegiado.setUsumodificacion(usuario.getIdusuario());
									cenNocolegiado.setFechamodificacion(new Date());
									
									cenNocolegiadoMapper.updateByExampleSelective(cenNocolegiado, cenNocolegiadoExample);
								}else {
									CenColegiadoExample cenColegiadoExample = new CenColegiadoExample();
									cenColegiadoExample.createCriteria()
											.andIdpersonaEqualTo(Long.valueOf(colegiadoItem.getIdPersona())).andIdinstitucionEqualTo(idInstitucion);
									CenColegiado cenColegiado = new CenColegiado();
									cenColegiado.setUsumodificacion(usuario.getIdusuario());
									cenColegiado.setFechaincorporacion(colegiadoItem.getIncorporacionDate());
									cenColegiado.setFechamodificacion(new Date());
									
									cenColegiadoMapper.updateByExampleSelective(cenColegiado, cenColegiadoExample);
								}
				
					
					
						//	4. Actualiza la tabla CEN_DATOSCOLEGIALESESTADO
					
					CenDatoscolegialesestadoExample cenDatoscolegialesestadoExample = new CenDatoscolegialesestadoExample();
					cenDatoscolegialesestadoExample.createCriteria().andIdpersonaEqualTo(Long.valueOf(colegiadoItem.getIdPersona())).andIdinstitucionEqualTo(idInstitucion);
					cenDatoscolegialesestadoExample.setOrderByClause("FECHAESTADO DESC");
							// Buscamos por idPersona para ver si el estado es diferente 
					
					cenDatoscolegialesestado = cenDatoscolegialesestadoMapper.selectByExample(cenDatoscolegialesestadoExample);
					
					if(cenDatoscolegialesestado != null && cenDatoscolegialesestado.size()>0) {
						if (!cenDatoscolegialesestado.get(0).getIdestado().equals(Short.valueOf(colegiadoItem.getSituacion()))) {
							CenDatoscolegialesestado cenEstadoColegial = new CenDatoscolegialesestado();
							cenEstadoColegial.setIdestado(Short.parseShort(colegiadoItem.getSituacion()));
							cenEstadoColegial.setIdpersona(Long.parseLong(colegiadoItem.getIdPersona()));
							cenEstadoColegial.setIdinstitucion(Short.parseShort(colegiadoItem.getIdInstitucion()));
							cenEstadoColegial.setFechamodificacion(new Date());
							cenEstadoColegial.setUsumodificacion(usuario.getIdusuario());
							cenEstadoColegial.setFechaestado(new Date());
							cenDatoscolegialesestadoMapper.insertSelective(cenEstadoColegial);
//							cenDatoscolegialesestadoMapper.updateByExample(cenEstadoColegial, cenDatoscolegialesestadoExample);
						}
					}
					
					


					updateResponseDTO.setStatus(SigaConstants.OK);

//				}

			} else {
				updateResponseDTO.setStatus(SigaConstants.KO);
				LOGGER.warn(
						"updateColegiado() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("updateColegiado() -> idInstitucion del token nula");
		}

		LOGGER.info(
				"updateColegiado() -> Salida del servicio para actualizar información general de un colegiado");

		return updateResponseDTO;
		
	}
	
	
	
	@Override
	public InsertResponseDTO createNoColegiado(NoColegiadoItem noColegiadoItem, HttpServletRequest request) throws ParseException {
		LOGGER.info(
				"createColegiado() -> Entrada al servicio para actualizar información general de una persona jurídica");
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		List<CenDatoscolegialesestado> cenDatoscolegialesestado = new ArrayList<CenDatoscolegialesestado>();


		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

//		List<CenGruposcliente> cenGruposcliente = new ArrayList<CenGruposcliente>();

		CenPersonaExample example = new CenPersonaExample();
		example.createCriteria().andNifcifEqualTo(noColegiadoItem.getNif());
		List<CenPersona> personas = cenPersonaExtendsMapper.selectByExample(example);
		
		if (null != personas && !(personas.size() > 0)) {
			
			if (null != idInstitucion) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				LOGGER.info(
						"createColegiado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"createColegiado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
				
				if (null != usuarios && usuarios.size() > 0) {
					AdmUsuarios usuario = usuarios.get(0);

					// 1. crear tablas CEN_PERSONA, CEN_COLEGIADO y CEN_CLIENTE
					if (!insertResponseDTO.getStatus().equals(SigaConstants.KO)) {

						// 1 crear registro en tabla CEN_PERSONA
						LOGGER.info(
								"createColegiado() / cenPersonaExtendsMapper.insertSelectiveForNewSociety() -> Entrada a cenPersonaExtendsMapper para crear una nueva persona");

						CenPersona crearPersonaDTO = new CenPersona();
						
						crearPersonaDTO.setNifcif(noColegiadoItem.getNif());
						crearPersonaDTO.setApellidos1(noColegiadoItem.getApellidos1());
						crearPersonaDTO.setApellidos2(noColegiadoItem.getApellidos2());
						crearPersonaDTO.setNombre(noColegiadoItem.getNombre());
						crearPersonaDTO.setIdtipoidentificacion(Short.parseShort(noColegiadoItem.getIdTipoIdentificacion()));
						crearPersonaDTO.setFechanacimiento(noColegiadoItem.getFechaNacimientoDate());
						if(noColegiadoItem.getSexo()!=null) {
						crearPersonaDTO.setSexo(noColegiadoItem.getSexo());
						}
						if(noColegiadoItem.getNaturalDe()!=null) {
							crearPersonaDTO.setNaturalde(noColegiadoItem.getNaturalDe());

						}
						if(noColegiadoItem.getidEstadoCivil() != null) {
							
						crearPersonaDTO.setIdestadocivil(Short.parseShort(noColegiadoItem.getidEstadoCivil()));
						}
						
						int responseInsertPersona = cenPersonaExtendsMapper
								.insertSelectiveForPerson(crearPersonaDTO, usuario);
						LOGGER.info(
								"createColegiado() / cenPersonaExtendsMapper.insertSelectiveForCreateLegalPerson() -> Salida de cenPersonaExtendsMapper para crear una nueva persona");

						if (responseInsertPersona == 1) {
							// 2 crear registro en tabla CEN_CLIENTE

							CenCliente cenCliente = new CenCliente();
							List<ComboItem> comboItems = new ArrayList<ComboItem>();

							comboItems = cenPersonaExtendsMapper.selectMaxIdPersona();

							if (!comboItems.isEmpty()) {
								
								cenCliente.setIdpersona(Long.valueOf(comboItems.get(0).getValue()));						
								// cenCliente.setIdpersona(Long.parseLong(colegiadoItem.getIdPersona()));
								cenCliente.setIdinstitucion(usuario.getIdinstitucion());
								cenCliente.setFechaalta(new Date());
								cenCliente.setCaracter("P");
								cenCliente.setPublicidad(SigaConstants.DB_FALSE);
								cenCliente.setGuiajudicial(SigaConstants.DB_FALSE);
								cenCliente.setComisiones(SigaConstants.DB_FALSE);
								cenCliente.setIdtratamiento(Short.valueOf(SigaConstants.DB_TRUE)); // 1
								cenCliente.setFechamodificacion(new Date());
								cenCliente.setUsumodificacion(usuario.getIdusuario());
								cenCliente.setIdlenguaje(usuario.getIdlenguaje());
								cenCliente.setExportarfoto(SigaConstants.DB_FALSE);
							}
							
							LOGGER.info(
									"createColegiado() / cenClienteMapper.insert() -> Entrada a cenClienteMapper para crear un nuevo colegiado");

							int responseCenCliente = 0;
							responseCenCliente = cenClienteMapper.insert(cenCliente);
							
							LOGGER.info(
									"createColegiado() / cenClienteMapper.insert() -> Salida de cenClienteMapper para crear un nuevo colegiado");

							
//							int responseCenCliente = 0;
//							if (null != record.getIdpersona()) {
//								responseCenCliente = cenClienteMapper.insertSelective(record);
//							}

							if (responseCenCliente == 1) {
								// 3 crear registro en tabla CEN_NOCOLEGIADO
								LOGGER.info(
										"createColegiado() / cenColegiadoExtendsMapper.insertSelectiveForCreateNewSociety() -> Entrada a cenColegiadoExtendsMapper para crear un nuevo colegiado");

										EtiquetaUpdateDTO cenNocolegiado = new EtiquetaUpdateDTO();
										
										
//										Se pone a cero ya que al ser una persona física no tiene tipo ni sociedadsj
										cenNocolegiado.setTipo("0");
//										cenNocolegiado.set
										cenNocolegiado.setAnotaciones(noColegiadoItem.getAnotaciones());
										int insertNoColegiado = cenNocolegiadoMapper.insertSelectiveForCreateLegalPerson(String.valueOf(idInstitucion), usuario, cenNocolegiado);
												
										
											if(insertNoColegiado == 1) {
												insertResponseDTO.setStatus(SigaConstants.OK);
												LOGGER.warn(
														"createColegiado() / cenNocolegiadoExtendsMapper.insertSelectiveForCreateLegalPerson() -> Se ha insertado correctamente la persona en CEN_NOCOLEGIADO");
											}
								LOGGER.info(
										"createColegiado() / cenColegiadoExtendsMapper.insertSelectiveForCreateLegalPerson() -> Salida de cenColegiadoExtendsMapper para crear un nuevo colegiado");
							}

							else {
								insertResponseDTO.setStatus(SigaConstants.KO);
								LOGGER.warn(
										"createColegiado() / cenNocolegiadoExtendsMapper.insertSelectiveForCreateLegalPerson() -> No se ha podido crear la persona no colegiada en tabla CEN_NOCOLEGIADO");
							}
						} else {
							insertResponseDTO.setStatus(SigaConstants.KO);
							LOGGER.warn(
									"createColegiado() / cenPersonaExtendsMapper.insertSelectiveForCreateLegalPerson() -> No se ha podido crear la persona no colegiada en tabla CEN_NOCOLEGIADO");
						}

					}
					
				} else {
					insertResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.warn(
							"createColegiado() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
									+ dni + " e idInstitucion = " + idInstitucion);
				}
				
				
				
				
				
			} else {
				insertResponseDTO.setStatus(SigaConstants.KO);
				LOGGER.warn("createLegalPerson() -> idInstitucion del token nula");
			}
			
		} else {
			insertResponseDTO.setStatus(SigaConstants.KO);
			org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
			error.setMessage("messages.censo.nifcifExiste2");
			insertResponseDTO.setError(error);
		}
		
		return insertResponseDTO;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public ComboDTO getEstadoCivil(HttpServletRequest request) {

		LOGGER.info("getEstadoCivil() -> Entrada al servicio para obtener los tipos de tratamiento disponibles");
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getEstadoCivil() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			AdmUsuarios usuario = usuarios.get(0);

			LOGGER.info(
					"getEstadoCivil() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				comboItems = cenEstadocivilExtendsMapper.distinctCivilStatus(usuario.getIdlenguaje());				

				comboDTO.setCombooItems(comboItems);

			}

		}
		LOGGER.info("getPais() -> Salida del servicio para obtener los tipos de tratamiento disponibles");

		return comboDTO;
	}
	
	@Override
	public ComboDTO getTratamiento(HttpServletRequest request) {

		LOGGER.info("getTratamiento() -> Entrada al servicio para obtener los tipos de tratamiento disponibles");
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getTratamiento() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			AdmUsuarios usuario = usuarios.get(0);

			LOGGER.info(
					"getTratamiento() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				comboItems = cenFichaDatosGeneralesExtendsMapper.selectTratamiento(usuario.getIdlenguaje());				

				comboDTO.setCombooItems(comboItems);

			}

		}
		LOGGER.info("getPais() -> Salida del servicio para obtener los tipos de tratamiento disponibles");

		return comboDTO;
	}

	@Override
	public ComboDTO getLabel(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
