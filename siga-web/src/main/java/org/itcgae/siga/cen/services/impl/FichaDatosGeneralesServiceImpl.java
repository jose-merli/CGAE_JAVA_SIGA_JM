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
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.CrearPersonaDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesItem;
import org.itcgae.siga.DTOs.cen.DatosDireccionesSearchDTO;
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
import org.itcgae.siga.db.entities.CenGruposcliente;
import org.itcgae.siga.db.entities.CenGruposclienteCliente;
import org.itcgae.siga.db.entities.CenGruposclienteClienteExample;
import org.itcgae.siga.db.entities.CenNocolegiado;
import org.itcgae.siga.db.entities.CenNocolegiadoExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.mappers.CenClienteMapper;
import org.itcgae.siga.db.mappers.CenDatoscolegialesestadoMapper;
import org.itcgae.siga.db.mappers.CenNocolegiadoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenRecursosCatalogosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDatoscolegialesestadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDireccionesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenEstadocivilExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenGruposclienteClienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenGruposclienteExtendsMapper;
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
	private CenDireccionesExtendsMapper cenDireccionesExtendsMapper;
	
	@Autowired
	private CenGruposclienteClienteExtendsMapper cenGruposclienteClienteExtendsMapper;

	@Autowired
	private CenDatoscolegialesestadoExtendsMapper cenDatoscolegialesestadoMapper;

	@Autowired
	private CenGruposclienteExtendsMapper cenGruposclienteExtendsMapper;

	@Autowired
	private GenRecursosCatalogosExtendsMapper genRecursosCatalogosExtendsMapper;

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
		List<ComboItem> gruposPersona = new ArrayList<ComboItem>();
		EtiquetaUpdateDTO etiquetaUpdateDTO = new EtiquetaUpdateDTO();
		List<String> gruposNuevosNoAniadidos = new ArrayList<String>();
		List<CenGruposcliente> cenGruposcliente = new ArrayList<CenGruposcliente>();

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

				etiquetaUpdateDTO.setIdPersona(colegiadoItem.getIdPersona());

				// 1. obtener grupos de la persona juridica antes de actualizar
				gruposPersona = cenGruposclienteClienteExtendsMapper.selectGruposPersonaJuridica(
						colegiadoItem.getIdPersona(), String.valueOf(usuario.getIdinstitucion()));

				List<String> gruposPerJuridicaAntiguos = new ArrayList<String>();
				List<String> gruposPerJuridicaAnterior = new ArrayList<String>();

				for (int i = 0; i < gruposPersona.size(); i++) {
					gruposPerJuridicaAntiguos.add(gruposPersona.get(i).getValue());
					gruposPerJuridicaAnterior.add(gruposPersona.get(i).getValue());
				}

				// 2. actualizar relaciones entre tablas para todos los grupos (eliminado y
				// creación)
				for (String grupo : colegiadoItem.getGrupos()) {

					// 2.1 si el nuevo grupo no está, hay que crearlo
					if (!gruposPerJuridicaAntiguos.contains(grupo)) {

						// guardamos para AUDITORIA los grupos nuevos
						gruposNuevosNoAniadidos.add(grupo);

						LOGGER.info(
								"updateLegalPerson() / cenGruposclienteExtendsMapper.createLegalPerson() -> Entrada a cenGruposclienteExtendsMapper para obtener grupos de clientes");
						cenGruposcliente = cenGruposclienteExtendsMapper.selectDistinctGruposClientes(
								String.valueOf(idInstitucion), usuario.getIdlenguaje(), grupo);
						LOGGER.info(
								"updateLegalPerson() / cenGruposclienteExtendsMapper.createLegalPerson() -> Salida de cenGruposclienteExtendsMapper para obtener grupos de clientes");

						// 2.1.1 si existen ya registros para ese grupo => solo actualizar/insertar en
						// tabla CEN_GRUPOSCLIENTE_CLIENTE (relacion persona-grupo)
						if (null != cenGruposcliente && cenGruposcliente.size() > 0) {

							List<CenGruposclienteCliente> listarelacionGrupoPersona = new ArrayList<CenGruposclienteCliente>();
							CenGruposclienteClienteExample relacionGrupoPersona = new CenGruposclienteClienteExample();
							relacionGrupoPersona.createCriteria()
									.andIdpersonaEqualTo(Long.valueOf(etiquetaUpdateDTO.getIdPersona()))
									.andIdgrupoEqualTo(Short.valueOf(grupo)).andIdinstitucionEqualTo(idInstitucion);
							listarelacionGrupoPersona = cenGruposclienteClienteExtendsMapper
									.selectByExample(relacionGrupoPersona);

							// 2.1.1.1 si no existe registro que relaciona un grupo-persona juridica => se
							// crea registro en CEN_GRUPOSCLIENTE_CLIENTE
							if (listarelacionGrupoPersona.isEmpty()) {
								LOGGER.info(
										"updateLegalPerson() / cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> Entrada a cenGruposclienteClienteExtendsMapper para crear relacion grupo-persona jurídica");

								int response = cenGruposclienteClienteExtendsMapper.insertSelectiveForUpdateLegalPerson(
										etiquetaUpdateDTO, String.valueOf(idInstitucion), grupo,
										String.valueOf(usuario.getIdusuario()));
								LOGGER.info(
										"updateLegalPerson() / cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> Salida a cenGruposclienteClienteExtendsMapper para crear relacion grupo-persona jurídica");

								if (response == 0) {
									updateResponseDTO.setStatus(SigaConstants.KO);
									LOGGER.warn(
											"updateLegalPerson() / cenGruposclienteExtendsMapper.createLegalPerson() -> No se pudo insertar el grupo = "
													+ grupo + " en tabla CEN_GRUPOSCLIENTE_CLIENTE");
								}
							}
							// 2.1.1.2 existe registro que relaciona un grupo-persona juridica => se
							// actualiza la fecha de baja del registro en CEN_GRUPOSCLIENTE_CLIENTE
							else {

								listarelacionGrupoPersona.get(0).setFechaBaja(null);
								LOGGER.info(
										"updateLegalPerson() / cenGruposclienteClienteExtendsMapper.updateByExample() -> Entrada a cenGruposclienteClienteExtendsMapper para actualizar relacion grupo-persona jurídica");

								int response = cenGruposclienteClienteExtendsMapper
										.updateByExample(listarelacionGrupoPersona.get(0), relacionGrupoPersona);
								LOGGER.info(
										"updateLegalPerson() / cenGruposclienteClienteExtendsMapper.updateByExample() -> Salida de cenGruposclienteClienteExtendsMapper para actualizar relacion grupo-persona jurídica");

								if (response == 0) {
									updateResponseDTO.setStatus(SigaConstants.KO);
									LOGGER.warn(
											"updateLegalPerson() / cenGruposclienteExtendsMapper.updateByExample() -> No se pudo actualizar la fecha de baja del grupo = "
													+ grupo + " en tabla CEN_GRUPOSCLIENTE_CLIENTE");
								}
							}

						}
						// 2.1.2 no existen registros para ese grupo => insertar en: tabla
						// CEN_GRUPOSCLIENTE , GEN_RECURSOS_CATALOGOS y CEN_GRUPOSCLIENTE_CLIENTE
						else {
//							int response1 = 0;
//							int response2 = 0;
//							int response3 = 0;
//							// insertar en GEN_RECURSOS_CATALOGOS para generar recurso
//							String nombreTabla = "CEN_GRUPOSCLIENTE";
//							String campoTabla = "NOMBRE";
//							LOGGER.info(
//									"updateLegalPerson() / genRecursosCatalogosExtendsMapper.insertSelectiveForCreateLegalPerson() -> Entrada a genRecursosCatalogosExtendsMapper para crear recurso de una persona juridica");
//							response1 = genRecursosCatalogosExtendsMapper.insertSelectiveForCreateLegalPerson(
//									String.valueOf(idInstitucion), usuario, grupo, nombreTabla, campoTabla);
//							LOGGER.info(
//									"updateLegalPerson() / genRecursosCatalogosExtendsMapper.insertSelectiveForCreateLegalPerson() -> Salida de genRecursosCatalogosExtendsMapper para crear recurso de una persona juridica");
//
//							if (response1 == 1) {
//
//								// insertar en CEN_GRUPOSCLIENTE para generar el grupo
//								LOGGER.info(
//										"updateLegalPerson() / cenGruposclienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> Entrada a cenGruposclienteExtendsMapper para crear grupo de una persona jurídica");
//
//								response2 = cenGruposclienteExtendsMapper
//										.insertSelectiveForCreateLegalPerson(String.valueOf(idInstitucion), usuario);
//								LOGGER.info(
//										"updateLegalPerson() / cenGruposclienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> Salida de cenGruposclienteExtendsMapper para crear grupo de una persona jurídica");
//
//								if (response2 == 1) {
//									// insertar en CEN_GRUPOSCLIENTE_CLIENTE para relacionar grupos-usuarios
//									LOGGER.info(
//											"updateLegalPerson() / cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> Entrada a cenGruposclienteClienteExtendsMapper para crear relacion grupo-persona jurídica");
//
//									response3 = cenGruposclienteClienteExtendsMapper
//											.insertSelectiveForUpdateLegalPerson(etiquetaUpdateDTO,
//													String.valueOf(idInstitucion), "",
//													String.valueOf(usuario.getIdusuario()));
//									LOGGER.info(
//											"updateLegalPerson() / cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> Salida de cenGruposclienteClienteExtendsMapper para crear relacion grupo-persona jurídica");
//
//									if (response3 == 0) {
//										updateResponseDTO.setStatus(SigaConstants.KO);
//										LOGGER.warn(
//												"updateLegalPerson() / cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> No se pudo insertar el grupo = "
//														+ grupo + " en tabla CEN_GRUPOSCLIENTE_CLIENTE");
//
//									}
//								} else {
//									updateResponseDTO.setStatus(SigaConstants.KO);
//									LOGGER.warn(
//											"updateLegalPerson() / cenGruposclienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> No se pudo insertar el grupo = "
//													+ grupo + " en tabla CEN_GRUPOSCLIENTE");
//								}
//							} else {
//								updateResponseDTO.setStatus(SigaConstants.KO);
//								LOGGER.warn(
//										"updateLegalPerson() / genRecursosCatalogosExtendsMapper.insertSelectiveForCreateLegalPerson() -> No se pudo insertar el grupo = "
//												+ grupo + " en tabla GEN_RECURSOS_CATALOGOS");
//							}
						}
					}
					// 2.2 el grupo ya existe para la persona juridica => eliminar de los grupos
					// antiguos
					else {
						// eliminamos grupo de los antiguos que tenia la persona juridica para que solo
						// queden los que hay que eliminar
						gruposPerJuridicaAntiguos.remove(grupo);
					}
				}
				
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
	public DatosDireccionesDTO partidoJudicialSearch(ColegiadoItem colegiadoItem, HttpServletRequest request) {
		LOGGER.info("searchBanksData() -> Entrada al servicio para la búsqueda por filtros de direcciones");
	
		DatosDireccionesDTO datosDireccionesDTO = new DatosDireccionesDTO();
//		ColegiadoDTO colegiado = new ColegiadoDTO();

		List<DatosDireccionesItem> datosDireccionesItem = new ArrayList<DatosDireccionesItem>();
//		DatosDireccionesDTO datosDireccionesDTO = new DatosDireccionesDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"datosDireccionesSearch() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"datosDireccionesSearch() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				LOGGER.info(
						"datosDireccionesSearch() / cenDireccionesExtendsMapper.selectDirecciones() -> Entrada a cenCuentasbancariasExtendsMapper para busqueda de direcciones");
				datosDireccionesItem = cenDireccionesExtendsMapper.selectPartidoJudicial(colegiadoItem.getIdPersona(), idInstitucion.toString());
				LOGGER.info(
						"datosDireccionesSearch() / cenDireccionesExtendsMapper.selectDirecciones() -> Salida de cenCuentasbancariasExtendsMapper para busqueda de direcciones");

			} else {
				LOGGER.warn(
						"datosDireccionesSearch() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("datosDireccionesSearch() -> idInstitucion del token nula");
		}

		LOGGER.info("datosDireccionesSearch() -> Salida del servicio para la búsqueda por filtros de direcciones");
		datosDireccionesDTO.setDatosDireccionesItem(datosDireccionesItem);
		return datosDireccionesDTO;
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
								cenCliente.setComisiones(noColegiadoItem.getComisiones());
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
												List<ComboItem> idper = cenPersonaExtendsMapper.selectMaxIdPersona();
												insertResponseDTO.setId(idper.get(0).getLabel());
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
