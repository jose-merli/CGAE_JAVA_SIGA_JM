package org.itcgae.siga.cen.services.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.ComboEtiquetasDTO;
import org.itcgae.siga.DTOs.cen.ComboEtiquetasItem;
import org.itcgae.siga.DTOs.cen.DatosDireccionesDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesItem;
import org.itcgae.siga.DTOs.cen.EtiquetaUpdateDTO;
import org.itcgae.siga.DTOs.cen.NoColegiadoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.cen.services.IFichaDatosGeneralesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SigaExceptions;
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
import org.itcgae.siga.db.entities.CenNocolegiadoKey;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.CenSolicitmodifdatosbasicos;
import org.itcgae.siga.db.entities.CenSolicmodifexportarfoto;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.mappers.CenClienteMapper;
import org.itcgae.siga.db.mappers.CenSolicmodifexportarfotoMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
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
import org.itcgae.siga.db.services.cen.mappers.CenSolicitmodifdatosbasicosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicmodifexportarfotoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTratamientoExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
public class FichaDatosGeneralesServiceImpl implements IFichaDatosGeneralesService{

	private Logger LOGGER = Logger.getLogger(FichaDatosGeneralesServiceImpl.class);
	
	@Autowired
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;

	@Autowired
	private GenPropertiesMapper genPropertiesMapper;

	@Autowired
	private CenNocolegiadoExtendsMapper cenNocolegiadoMapper;

	@Autowired
	private CenColegiadoExtendsMapper cenColegiadoMapper;
	
	@Autowired
	private CenSolicmodifexportarfotoExtendsMapper cenSolicmodifexportarfoto;

	@Autowired
	private CenDireccionesExtendsMapper cenDireccionesExtendsMapper;
	
	@Autowired
	private CenGruposclienteClienteExtendsMapper cenGruposclienteClienteExtendsMapper;

	@Autowired
	private  CenSolicitmodifdatosbasicosExtendsMapper  cenSolicitmodifdatosbasicosMapper;

//	@Autowired
//	private CenDatoscolegialesestadoExtendsMapper cenDatoscolegialesestadoMapper;

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


//	@Override
//	public ComboDTO getSocietyTypes(HttpServletRequest request) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public  UpdateResponseDTO updateColegiado(ColegiadoItem colegiadoItem, HttpServletRequest request) throws ParseException {
		LOGGER.info(
				"updateColegiado() -> Entrada al servicio para actualizar información general de un colegiado");
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
//		List<CenDatoscolegialesestado> cenDatoscolegialesestado = new ArrayList<CenDatoscolegialesestado>();
		List<ComboEtiquetasItem> gruposPersona = new ArrayList<ComboEtiquetasItem>();
		EtiquetaUpdateDTO etiquetaUpdateDTO = new EtiquetaUpdateDTO();
		
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

				// 1. Etiquetas asociadas a la sociedad 
				gruposPersona = cenGruposclienteClienteExtendsMapper.selectGruposPersonaJuridica(
						etiquetaUpdateDTO.getIdPersona(), String.valueOf(usuario.getIdinstitucion()));

				List<String> gruposPerJuridicaAntiguos = new ArrayList<String>();
				List<String> gruposPerJuridicaAnterior = new ArrayList<String>();
				List<String> gruposPerJuridicaPosterior = new ArrayList<String>();
				
				for (int i = 0; i < gruposPersona.size(); i++) {
					gruposPerJuridicaAntiguos.add(gruposPersona.get(i).getIdGrupo());
					gruposPerJuridicaAnterior.add(gruposPersona.get(i).getIdGrupo());
				}
				  
				// 2. Recorremos las etiquetas 
				for (ComboEtiquetasItem etiqueta : colegiadoItem.getEtiquetas()) {
//
					// 2.1. Es una etiqueta de nueva y no existe registro en ninguna tabla
	if(etiqueta.getIdGrupo() == "") {
						
						GenRecursosCatalogos genRecursosCatalogos = new GenRecursosCatalogos();
						
						genRecursosCatalogos.setCampotabla("NOMBRE");
						genRecursosCatalogos.setDescripcion(etiqueta.getLabel());
						genRecursosCatalogos.setIdlenguaje(usuario.getIdlenguaje());
					
						String idRecursoBD = genRecursosCatalogosExtendsMapper.getMaxIdRecurso();
						String idRecurso = String.valueOf(Integer.valueOf(idRecursoBD) + 1);
						genRecursosCatalogos.setIdrecurso(idRecurso);
						
						// Obtenemos el idGrupo de CenGrupoCliente
						Short idGrupoBD = cenGruposclienteExtendsMapper.getMaxIdGrupo();
						Short idGrupo = (short) (idGrupoBD + (short) 1);
					
						genRecursosCatalogos.setIdrecursoalias("cen_gruposcliente.nombre." + usuario.getIdinstitucion() + "." + idGrupo);
						genRecursosCatalogos.setUsumodificacion(usuario.getIdusuario());
						genRecursosCatalogos.setFechamodificacion(new Date());
						genRecursosCatalogos.setIdinstitucion(usuario.getIdinstitucion());
						genRecursosCatalogos.setNombretabla("CEN_GRUPOSCLIENTE");
					
						int resultGenRecursosCatalogos = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogos);

						if (resultGenRecursosCatalogos == 1) {
										
							LOGGER.warn("updateLegalPerson() / genRecursosCatalogosExtendsMapper.insert() -> Insertada la descripción en genRecursosCatálogos correctamente");

							// Insertamos en CenGruposCliente

							CenGruposcliente cenGruposcli = new CenGruposcliente();

							cenGruposcli.setIdgrupo(idGrupo);
							cenGruposcli.setFechamodificacion(new Date());
							cenGruposcli.setIdinstitucion(usuario.getIdinstitucion());
							cenGruposcli.setNombre(idRecurso);
							cenGruposcli.setUsumodificacion(usuario.getIdusuario());

							int resultCenGruposCliente = cenGruposclienteExtendsMapper.insert(cenGruposcli);
							

							if (resultCenGruposCliente == 1) {
									
								LOGGER.warn(
												"updateLegalPerson() / cenGruposclienteMapper.insert() -> Insertado el id correctamente en la tabla CenGruposCliente");

								CenGruposclienteCliente cenGruposclienteCliente = new CenGruposclienteCliente();
								
								DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

						  
								Date fechaInicio = df.parse(etiqueta.getFechaInicio());
							    Date fechaBaja = df.parse(etiqueta.getFechaBaja());
							    
								cenGruposclienteCliente.setFechaBaja(fechaBaja);
								cenGruposclienteCliente.setFechaInicio(fechaInicio);
								cenGruposclienteCliente.setFechamodificacion(new Date());
								cenGruposclienteCliente.setIdgrupo(idGrupo);
								cenGruposclienteCliente.setIdinstitucion(usuario.getIdinstitucion());							
								cenGruposclienteCliente.setIdinstitucionGrupo(usuario.getIdinstitucion());
								cenGruposclienteCliente.setIdpersona(Long.valueOf(etiquetaUpdateDTO.getIdPersona()));
								cenGruposclienteCliente.setUsumodificacion(usuario.getIdusuario());
					
								int resultCenGruposClienteClientes = cenGruposclienteClienteExtendsMapper
													.insert(cenGruposclienteCliente);
					
								if (resultCenGruposClienteClientes == 1) {
									updateResponseDTO.setStatus(SigaConstants.OK);
									gruposPerJuridicaPosterior.add(String.valueOf(cenGruposcli.getIdgrupo()));
									LOGGER.warn("updateLegalPerson() / cenGruposclienteClienteExtendsMapper.insert() -> Insertado correctamente en la tabla CenGruposClienteClientes");
								} else {
									updateResponseDTO.setStatus(SigaConstants.KO);
									LOGGER.warn("updateLegalPerson() / cenGruposclienteClienteExtendsMapper.insert() -> No se ha podido insertar en la tabla CenGruposClienteClientes");
								}

							} else {
								updateResponseDTO.setStatus(SigaConstants.KO);
								LOGGER.warn(
										"updateLegalPerson() / cenGruposclienteMapper.insert() -> No insertado el id correctamente en la tabla CenGruposCliente");
						     }

						} else {
							updateResponseDTO.setStatus(SigaConstants.KO);
							LOGGER.warn("updateLegalPerson() / genRecursosCatalogosExtendsMapper.insert() -> No insertada la descripción en genRecursosCatálogos correctamente");
						}
	}else {	
						// Etiqueta para nueva asociación
						if(!gruposPerJuridicaAntiguos.contains(etiqueta.getIdGrupo())) {
							
							// Buscamos si esa etiqueta ya existe en la tabla de relaciones
							List<CenGruposclienteCliente> listarelacionGrupoPersona = new ArrayList<CenGruposclienteCliente>();
							CenGruposclienteClienteExample relacionGrupoPersona = new CenGruposclienteClienteExample();
							relacionGrupoPersona.createCriteria()
									.andIdpersonaEqualTo(Long.valueOf(etiquetaUpdateDTO.getIdPersona()))
									.andIdgrupoEqualTo(Short.valueOf(etiqueta.getIdGrupo())).andIdinstitucionEqualTo(idInstitucion);
							listarelacionGrupoPersona = cenGruposclienteClienteExtendsMapper
									.selectByExample(relacionGrupoPersona);

							// Si no existe --> creamos un nuevo registro
							if (listarelacionGrupoPersona.isEmpty()) {

								LOGGER.info(
										"updateLegalPerson() / cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> Entrada a cenGruposclienteClienteExtendsMapper para crear relacion grupo-persona jurídica");

								int response = cenGruposclienteClienteExtendsMapper.insertSelectiveForUpdateLegalPerson(
									etiqueta, etiquetaUpdateDTO.getIdPersona(), String.valueOf(idInstitucion), 
										String.valueOf(usuario.getIdusuario()));
								LOGGER.info(
										"updateLegalPerson() / cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> Salida a cenGruposclienteClienteExtendsMapper para crear relacion grupo-persona jurídica");

								if (response == 0) {
									updateResponseDTO.setStatus(SigaConstants.KO);
									LOGGER.warn(
											"updateLegalPerson() / cenGruposclienteExtendsMapper.createLegalPerson() -> No se pudo insertar el grupo = "
													+ etiqueta.getIdGrupo() + " en tabla CEN_GRUPOSCLIENTE_CLIENTE");
								}else {
									gruposPerJuridicaPosterior.add(etiqueta.getIdGrupo());
								}
							
							}else {
								// Modificar la etiqueta existente
								
								if(String.valueOf(listarelacionGrupoPersona.get(0).getFechaInicio()) != etiqueta.getFechaInicio()) {
									
									DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

									  
									Date fechaInicio = df.parse(etiqueta.getFechaInicio());
								    Date fechaBaja = df.parse(etiqueta.getFechaBaja());
								    
									listarelacionGrupoPersona.get(0).setFechaInicio(fechaInicio);
									listarelacionGrupoPersona.get(0).setFechaBaja(fechaBaja);
									LOGGER.info(
											"createLegalPerson() / cenGruposclienteClienteExtendsMapper.updateByExample() -> Entrada a cenGruposclienteClienteExtendsMapper actualizar la relacion grupo-persona jurídica");

									int response = cenGruposclienteClienteExtendsMapper.updateByExample(
											listarelacionGrupoPersona.get(0), relacionGrupoPersona);
									LOGGER.info(
											"createLegalPerson() / cenGruposclienteClienteExtendsMapper.updateByExample() -> Salida de cenGruposclienteClienteExtendsMapper actualizar la relacion grupo-persona jurídica");

									if (response == 0) {
										updateResponseDTO.setStatus(SigaConstants.KO);
										LOGGER.warn(
												"updateLegalPerson() / cenGruposclienteExtendsMapper.updateByExample() -> No se pudo actualizar la fecha de baja del grupo = "
														+ etiqueta + " en tabla CEN_GRUPOSCLIENTE_CLIENTE");
									}else {
										gruposPerJuridicaPosterior.add(etiqueta.getIdGrupo());
									}
										
								}
							}
							
						// El grupo ya existe		
						}else {
							// eliminamos grupo de los antiguos que tenia la persona juridica para que solo
							// queden los que hay que eliminar
							gruposPerJuridicaAntiguos.remove(etiqueta.getIdGrupo());
							
							// Pero insertamos el resgistro para auditoría
							gruposPerJuridicaPosterior.add(etiqueta.getIdGrupo());
						}
					}
				}
					
					// 3. Actualiza los campos de la persona jurídica
     				if (!updateResponseDTO.getStatus().equals(SigaConstants.KO)) {
	
					// 3.1.actualiza tabla CEN_PERSONA
	
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
				
				CenPersonaExample cenPersonaExample1 = new CenPersonaExample();
				cenPersonaExample1.createCriteria()
						.andIdpersonaEqualTo(Long.valueOf(colegiadoItem.getIdPersona()));
				LOGGER.info(
						"updateColegiado() / cenPersonaExtendsMapper.updateByExampleSelective() -> Entrada a cenPersonaExtendsMapper para actualizar información de colegiado en CEN_PERSONA");
				
				CenPersonaExample dniRepetido = new CenPersonaExample();
				dniRepetido.createCriteria().andIdpersonaEqualTo(Long.valueOf(colegiadoItem.getIdPersona())).andNifcifNotEqualTo(colegiadoItem.getNif());
				List<CenPersona> busqueda = cenPersonaExtendsMapper.selectByExample(dniRepetido);
				if(busqueda.isEmpty()) {
					cenPersonaExtendsMapper.updateByExampleSelective(cenPersona, cenPersonaExample1);
				}else {
					updateResponseDTO.setStatus(SigaConstants.KO);
					Error error = new Error();
//					Ya existe un usuario con este número de identificación
					error.setMessage("gratuita.personaJG.literal.numDocumentoExistente");
					updateResponseDTO.setError(error);
				}
				LOGGER.info(
						"updateColegiado() / cenPersonaExtendsMapper.updateByExampleSelective() -> Salida de cenPersonaExtendsMapper para actualizar información de colegiado en CEN_PERSONA");
				
				  for (int i = 0; i < gruposPerJuridicaAntiguos.size(); i++) {
						CenGruposclienteCliente cenGruposclienteCliente = new CenGruposclienteCliente();
						cenGruposclienteCliente.setFechamodificacion(new Date());
						cenGruposclienteCliente.setUsumodificacion(usuario.getIdusuario());
						cenGruposclienteCliente.setFechaBaja(null); // Ponemos la fecha de baja a null
						CenGruposclienteClienteExample cenGruposclienteClienteExample = new CenGruposclienteClienteExample();
						cenGruposclienteClienteExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdpersonaEqualTo(Long.valueOf(etiquetaUpdateDTO.getIdPersona()))
								.andIdinstitucionGrupoEqualTo(idInstitucion) 
								.andIdgrupoEqualTo(Short.valueOf(gruposPerJuridicaAntiguos.get(i)));
						LOGGER.info(
								"updateLegalPerson() / cenGruposclienteClienteExtendsMapper.updateByExampleSelective() -> Entrada a cenGruposclienteClienteExtendsMapper para eliminar un grupo relacionado con persona juridica en tabla CEN_GRUPOSCLIENTE_CLIENTE");
						int eliminadoGrupo = cenGruposclienteClienteExtendsMapper
								.updateByExample(cenGruposclienteCliente, cenGruposclienteClienteExample);
						LOGGER.info(
							"updateLegalPerson() / cenGruposclienteClienteExtendsMapper.updateByExampleSelective() -> Salida de cenGruposclienteClienteExtendsMapper para eliminar un grupo relacionado con persona juridica en tabla CEN_GRUPOSCLIENTE_CLIENTE");

						if (eliminadoGrupo == 0) {
							updateResponseDTO.setStatus(SigaConstants.KO);
							LOGGER.warn(
									"updateLegalPerson() / cenGruposclienteClienteExtendsMapper.updateByExampleSelective() -> No se pudo eliminar el grupo = "
											+ gruposPerJuridicaAntiguos.get(i) + " en tabla CEN_GRUPOSCLIENTE_CLIENTE");
						}
					}

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
					cenCliente.setNoaparecerredabogacia(colegiadoItem.getNoAparecerRedAbogacia());
	
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
					
//						//	4. Actualiza la tabla CEN_DATOSCOLEGIALESESTADO
//					
//					CenDatoscolegialesestadoExample cenDatoscolegialesestadoExample = new CenDatoscolegialesestadoExample();
//					cenDatoscolegialesestadoExample.createCriteria().andIdpersonaEqualTo(Long.valueOf(colegiadoItem.getIdPersona())).andIdinstitucionEqualTo(idInstitucion);
//					cenDatoscolegialesestadoExample.setOrderByClause("FECHAESTADO DESC");
//							// Buscamos por idPersona para ver si el estado es diferente 
//					
//					cenDatoscolegialesestado = cenDatoscolegialesestadoMapper.selectByExample(cenDatoscolegialesestadoExample);
//					
//					if(cenDatoscolegialesestado != null && cenDatoscolegialesestado.size()>0) {
//						if (!cenDatoscolegialesestado.get(0).getIdestado().equals(Short.valueOf(colegiadoItem.getSituacion()))) {
//							CenDatoscolegialesestado cenEstadoColegial = new CenDatoscolegialesestado();
//							cenEstadoColegial.setIdestado(Short.parseShort(colegiadoItem.getSituacion()));
//							cenEstadoColegial.setIdpersona(Long.parseLong(colegiadoItem.getIdPersona()));
//							cenEstadoColegial.setIdinstitucion(Short.parseShort(colegiadoItem.getIdInstitucion()));
//							cenEstadoColegial.setFechamodificacion(new Date());
//							cenEstadoColegial.setUsumodificacion(usuario.getIdusuario());
//							cenEstadoColegial.setFechaestado(new Date());
//							cenDatoscolegialesestadoMapper.insertSelective(cenEstadoColegial);
//						}
//					}
					if(!updateResponseDTO.getStatus().equals(SigaConstants.KO)) {
						updateResponseDTO.setStatus(SigaConstants.OK);
					}

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
		}
		return updateResponseDTO;
	}
	
	
	

	@Override
	public ComboEtiquetasDTO getLabelPerson(ColegiadoItem colegiadoItem, HttpServletRequest request) throws ParseException {
		LOGGER.info("getLabelPerson() -> Entrada al servicio para obtener etiquetas de una persona jurídica");
		ComboEtiquetasDTO comboEtiquetasDTO = new ComboEtiquetasDTO();
		List<ComboEtiquetasItem>comboEtiquetasItems = new ArrayList<ComboEtiquetasItem>();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		LOGGER.info(
				"getLabelPerson() / cenGruposclienteClienteExtendsMapper.selectGruposPersonaJuridica() -> Entrada a cenGruposclienteClienteExtendsMapper para obtener grupos de una persona jurídica");
		comboEtiquetasItems = cenGruposclienteClienteExtendsMapper.selectGruposPersonaJuridica(colegiadoItem.getIdPersona(), String.valueOf(idInstitucion));
		LOGGER.info(
				"getLabelPerson() / cenGruposclienteClienteExtendsMapper.selectGruposPersonaJuridica() -> Entrada a cenGruposclienteClienteExtendsMapper para obtener grupos de una persona jurídica");
		
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yy");
		String fechaHoy = simpleDateFormat.format(date);
		
		for (ComboEtiquetasItem comboEtiquetasItem : comboEtiquetasItems) {
			Date fechaInicio = simpleDateFormat.parse(comboEtiquetasItem.getFechaInicio());
		    Date fechaBaja = simpleDateFormat.parse( comboEtiquetasItem.getFechaBaja());
		    Date fechaActual = simpleDateFormat.parse(fechaHoy);
		    
			if(fechaInicio.before(fechaActual) && fechaBaja.after(fechaActual)) {
				comboEtiquetasItem.setColor("#87CEFA"); 
			}else if(fechaActual.before(fechaInicio) && fechaBaja.after(fechaInicio)) {
				comboEtiquetasItem.setColor("#40E0D0");
			}else if(fechaInicio.before(fechaBaja) && fechaActual.after(fechaBaja)) {
				comboEtiquetasItem.setColor("#F08080"); 
			}
		}
		
		comboEtiquetasDTO.setComboEtiquetasItems(comboEtiquetasItems);
		LOGGER.info("getLabelPerson() -> Salida del servicio para obtener etiquetas de una persona");
		return comboEtiquetasDTO;
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
	public InsertResponseDTO solicitudUploadPhotography(MultipartHttpServletRequest request)
			throws IllegalStateException, IOException {

		LOGGER.info("uploadPhotography() -> Entrada al servicio para guardar una fotografía de una persona jurídica");
		InsertResponseDTO updateResponseDTO = new InsertResponseDTO();
		String idPersona = request.getParameter("idPersona");
		String motivo = request.getParameter("motivo");
		int response = 0;

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		// obtener path para almacenar las fotografias
		LOGGER.debug("uploadPhotography() -> Obtener path para almacenar las fotografias");
		GenPropertiesExample genPropertiesExample = new GenPropertiesExample();
		genPropertiesExample.createCriteria().andParametroEqualTo("directorios.carpeta.fotos");
		LOGGER.info(
				"loadPhotography() / genPropertiesMapper.selectByExample() -> Entrada a genPropertiesMapper para obtener directorio de la fotografía");
		List<GenProperties> properties = genPropertiesMapper.selectByExample(genPropertiesExample);
		LOGGER.info(
				"loadPhotography() / genPropertiesMapper.selectByExample() -> Salida de genPropertiesMapper para obtener directorio de la fotografía");

		if (null != properties && properties.size() > 0) {
			String pathImagenes = properties.get(0).getValor() + "/" + String.valueOf(idInstitucion) + "/";

			// Coger archivo del request
			LOGGER.debug("uploadPhotography() -> Coger fotografía del request");
			Iterator<String> itr = request.getFileNames();
			MultipartFile file = request.getFile(itr.next());
			String fileName = file.getOriginalFilename();
			String extension = fileName.substring(fileName.lastIndexOf("."), fileName.length());

			// comprobar extension de la fotografia
			LOGGER.debug("uploadPhotography() -> Comprobar extension de la fotografia");
			if (extension == null || extension.trim().equals("")
					|| (!extension.trim().toUpperCase().equals(".JPG") && !extension.trim().toUpperCase().equals(".GIF")
							&& !extension.trim().toUpperCase().equals(".PNG")
							&& !extension.trim().toUpperCase().equals(".JPEG"))) {

				try {
					throw new SigaExceptions("messages.error.imagen.tipoNoCorrecto");
				} catch (SigaExceptions e) {
					e.printStackTrace();
				}
			}

			// Crear nombre del archivo a guardar
			LOGGER.debug("uploadPhotography() -> Crear nombre de la fotografía a guardar");
			CenPersonaExample cenPersonaExample = new CenPersonaExample();
			cenPersonaExample.createCriteria().andIdpersonaEqualTo(Long.valueOf(idPersona));
			LOGGER.info(
					"loadPhotography() / cenPersonaExtendsMapper.selectByExample() -> Entrada a cenPersonaExtendsMapper para nifcif de una persona");
			List<CenPersona> cenPersonas = cenPersonaExtendsMapper.selectByExample(cenPersonaExample);
			LOGGER.info(
					"loadPhotography() / cenPersonaExtendsMapper.selectByExample() -> Salida de cenPersonaExtendsMapper para nifcif de una persona");

			if (null != cenPersonas && cenPersonas.size() > 0) {
				String nifCif = cenPersonas.get(0).getNifcif();

				fileName = nifCif + "_" + String.valueOf(idInstitucion) + "_" + fileName;

				BufferedOutputStream stream = null;
				// Guardar el archivo
				LOGGER.debug("uploadPhotography() -> Guardar la fotografía");
				try {
					File aux = new File(pathImagenes);
					// creo directorio si no existe
					aux.mkdirs();
					File serverFile = new File(pathImagenes, fileName);
					stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(file.getBytes());

				} catch (FileNotFoundException e) {
					LOGGER.error(
							"uploadPhotography() -> Error al buscar la fotografía de la persona jurídica en el directorio indicado",
							e);
				} catch (IOException ioe) {
					LOGGER.error(
							"uploadPhotography() -> Error al guardar la fotografía de la persona jurídica en el directorio indicado",
							ioe);
				} finally {
					// close the stream
					LOGGER.debug("uploadPhotography() -> Cierre del stream de la fotografía de la persona jurídica");
					stream.close();
				}

				// actualizar nombre de la fotografia en base de datos
				LOGGER.debug("uploadPhotography() -> actualizar nombre de la fotografia en base de datos");
//				CenClienteExample cenClienteExample = new CenClienteExample();
//				cenClienteExample.createCriteria().andIdpersonaEqualTo(Long.valueOf(idPersona))
//						.andIdinstitucionEqualTo(idInstitucion);
//				CenCliente cenCliente = new CenCliente();
//				cenCliente.setFotografia(fileName);
				
				CenSolicmodifexportarfoto solicitud = new CenSolicmodifexportarfoto();
				solicitud.setExportarfoto(fileName);
				solicitud.setFechaalta(new Date());
				solicitud.setFechamodificacion(new Date());
				solicitud.setIdestadosolic(Short.parseShort("10"));
				solicitud.setIdinstitucion(idInstitucion);
				solicitud.setIdpersona(Long.valueOf(idPersona));
				
				NewIdDTO idSolicitudBD = cenSolicmodifexportarfoto.getMaxIdSolicitud(String.valueOf(idInstitucion),
						idPersona);
				if (idSolicitudBD == null) {
					solicitud.setIdsolicitud(Short.parseShort("1"));
				} else {
					int id = Integer.parseInt(idSolicitudBD.getNewId()) + 1;
					solicitud.setIdsolicitud(Short.parseShort("" + id));
				}
				
				solicitud.setMotivo(motivo);
				solicitud.setUsumodificacion(Integer.parseInt(idPersona));
				LOGGER.info(
						"loadPhotography() / cenClienteMapper.updateByExample() -> Entrada a cenClienteMapper actualizar el nombre de la fotografía de una persona jurídica");
				
				response = cenSolicmodifexportarfoto.insert(solicitud);
				
				LOGGER.info(
						"loadPhotography() / cenClienteMapper.updateByExample() -> Salida de cenClienteMapper actualizar el nombre de la fotografía de una persona jurídica");
				if (response == 1) {
					updateResponseDTO.setStatus(SigaConstants.OK);
					LOGGER.warn(
							"loadPhotography() / cenClienteMapper.updateByExample() -> " + updateResponseDTO.getStatus()
									+ " .Nombre de la fotografía de una persona jurídica actualizado correctamente");
				} else {
					updateResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.warn("loadPhotography() / cenClienteMapper.updateByExample() -> "
							+ updateResponseDTO.getStatus()
							+ " .No se ha podido actualizar el nombre de la fotografía de una persona jurídica");
				}

			} else {
				updateResponseDTO.setStatus(SigaConstants.KO);
				LOGGER.warn("loadPhotography() / cenPersonaExtendsMapper.selectByExample() -> "
						+ updateResponseDTO.getStatus() + ".No existen ninguna persona con en idPersona:" + idPersona
						+ " indicado");
			}

		} else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("loadPhotography() / genPropertiesMapper.selectByExample() -> " + updateResponseDTO.getStatus()
					+ ".No se pudo obtener el directorio de la fotografía");
		}

		LOGGER.info("uploadPhotography() -> Salida del servicio para guardar una fotografía de una persona jurídica");

		return updateResponseDTO;
	}
	
	
	

	@Override
	public InsertResponseDTO solicitudModificacion(NoColegiadoItem noColegiadoItem, HttpServletRequest request) {
		LOGGER.info(
				"createColegiado() -> Entrada al servicio para actualizar información general de una persona jurídica");
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

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

					if (!insertResponseDTO.getStatus().equals(SigaConstants.KO)) {

						// 1 crear registro en tabla CEN_SOLICITMODIFDATOSBASICOS
						LOGGER.info(
								"createColegiado() / cenPersonaExtendsMapper.insertSelectiveForNewSociety() -> Entrada a cenPersonaExtendsMapper para crear una nueva persona");

						CenSolicitmodifdatosbasicos solicitud = new CenSolicitmodifdatosbasicos();

						NewIdDTO idBD = cenSolicitmodifdatosbasicosMapper.getMaxIdSolicitud(String.valueOf(idInstitucion),
								noColegiadoItem.getIdPersona());
//						if (idBD == null) {
//							solicitud.setIdsolicitud(Short.parseShort("1"));
//						} else {
//							int idCv = Integer.parseInt(idBD.getNewId());
//							solicitud.setIdsolicitud(Short.parseShort(""+ (1 + idCv)));
//						}
						
						int idCv = Integer.parseInt(idBD.getNewId());
						solicitud.setIdsolicitud(Short.parseShort(""+ (1 + idCv)));
						solicitud.setPublicidad(SigaConstants.DB_FALSE);
						solicitud.setAbonos("B");
						solicitud.setCargos("B");
						solicitud.setGuiajudicial(SigaConstants.DB_FALSE);
						solicitud.setIdlenguaje(noColegiadoItem.getIdLenguaje());
						solicitud.setMotivo(noColegiadoItem.getMotivo());
						solicitud.setIdinstitucion(idInstitucion);
						solicitud.setIdpersona(Long.parseLong(noColegiadoItem.getIdPersona()));
						solicitud.setFechamodificacion(new Date());
						solicitud.setUsumodificacion(usuario.getIdusuario());
						solicitud.setIdestadosolic(Short.parseShort("10"));
						solicitud.setFechaalta(new Date());
//					AGREGAR EL RESTO DE CAMPOS PARA LA INSERCIÓN, PREGUNTAR QUE HACER CON LOS CAMPOS QUE NO SON LA FECHA Y TAMPOCO SON NULLABLES.
						
						int responseInsertPersona =  cenSolicitmodifdatosbasicosMapper.insert(solicitud);
						LOGGER.info(
								"createColegiado() / cenSolicitmodifdatosbasicosMapper.insert() -> Salida de cenSolicitmodifdatosbasicosMapper para crear una nueva solicitud");
						if(responseInsertPersona == 1) {
							insertResponseDTO.setStatus(SigaConstants.OK);
							LOGGER.info("createColegiado() Solicitud creada correctamente");

						}else {
							insertResponseDTO.setStatus(SigaConstants.KO);
							LOGGER.info("createColegiado() La solicitud no ha podido ser creada correctamente");
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
				LOGGER.warn("createColegiado() -> idInstitucion del token nula");
			}
		
		
		return insertResponseDTO;
	}

	
	
	
	@Override
	public InsertResponseDTO createNoColegiado(NoColegiadoItem noColegiadoItem, HttpServletRequest request) throws ParseException {
		LOGGER.info(
				"createColegiado() -> Entrada al servicio para actualizar información general de una persona jurídica");
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		List<CenGruposcliente> cenGruposcliente = new ArrayList<CenGruposcliente>();
		List<String> gruposPerJuridicaNuevos = new ArrayList<String>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		int ultimoInsert = 0;
//		List<CenGruposcliente> cenGruposcliente = new ArrayList<CenGruposcliente>();


			
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
					CenPersonaExample example = new CenPersonaExample();
					example.createCriteria().andNifcifEqualTo(noColegiadoItem.getNif());
					List<CenPersona> personas = cenPersonaExtendsMapper.selectByExample(example);
					
					if (null != personas && !(personas.size() > 0)) {
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
						
						 ultimoInsert = cenPersonaExtendsMapper
								.insertSelectiveForPerson(crearPersonaDTO, usuario);
						LOGGER.info(
								"createColegiado() / cenPersonaExtendsMapper.insertSelectiveForCreateLegalPerson() -> Salida de cenPersonaExtendsMapper para crear una nueva persona");
					}
						if (ultimoInsert == 1 || (null != personas && personas.size() > 0)) {
							List<ComboItem> comboItems = new ArrayList<ComboItem>();
							Long idPersona = Long.valueOf(0);
							if (null != personas && personas.size() > 0 ) {
								idPersona = personas.get(0).getIdpersona();
							}else {
								comboItems = cenPersonaExtendsMapper.selectMaxIdPersona();
								idPersona = Long.valueOf(comboItems.get(0).getValue());
							}
							CenClienteKey key = new CenClienteKey();
							key.setIdinstitucion(usuario.getIdinstitucion());
							key.setIdpersona(idPersona);
							// 2 crear registro en tabla CEN_CLIENTE
							CenCliente cliente = cenClienteMapper.selectByPrimaryKey(key );
							if (null == cliente) {
								
							
							CenCliente cenCliente = new CenCliente();
							

							//if (!comboItems.isEmpty()) {
								
								cenCliente.setIdpersona(idPersona);						
								// cenCliente.setIdpersona(Long.parseLong(colegiadoItem.getIdPersona()));
								cenCliente.setIdinstitucion(usuario.getIdinstitucion());
								cenCliente.setFechaalta(new Date());
								cenCliente.setCaracter("P");
								cenCliente.setComisiones(noColegiadoItem.getComisiones());
								cenCliente.setIdtratamiento(Short.valueOf(SigaConstants.DB_TRUE)); // 1
								cenCliente.setFechamodificacion(new Date());
								cenCliente.setUsumodificacion(usuario.getIdusuario());
								cenCliente.setIdlenguaje(usuario.getIdlenguaje());
								cenCliente.setExportarfoto(SigaConstants.DB_FALSE);
								cenCliente.setNoaparecerredabogacia(noColegiadoItem.getNoAparecerRedAbogacia());
							//}
							
							LOGGER.info(
									"createColegiado() / cenClienteMapper.insert() -> Entrada a cenClienteMapper para crear un nuevo colegiado");

							ultimoInsert = cenClienteMapper.insert(cenCliente);
							
							LOGGER.info(
									"createColegiado() / cenClienteMapper.insert() -> Salida de cenClienteMapper para crear un nuevo colegiado");

						
							}
							if (ultimoInsert == 1 || null != cliente) {
								CenNocolegiadoKey noColegiadokey = new CenNocolegiadoKey();
								noColegiadokey.setIdinstitucion(usuario.getIdinstitucion());
								noColegiadokey.setIdpersona(idPersona);
								CenNocolegiado nocol = cenNocolegiadoMapper.selectByPrimaryKey(noColegiadokey );
								// 3 crear registro en tabla CEN_NOCOLEGIADO
								if (null == nocol) {
									
								
								LOGGER.info(
										"createColegiado() / cenColegiadoExtendsMapper.insertSelectiveForCreateNewSociety() -> Entrada a cenColegiadoExtendsMapper para crear un nuevo colegiado");

										EtiquetaUpdateDTO cenNocolegiado = new EtiquetaUpdateDTO();
										
										
//										Se pone a cero ya que al ser una persona física no tiene tipo ni sociedadsj
										cenNocolegiado.setTipo("0");
										cenNocolegiado.setIdPersona(idPersona.toString());
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
								
								// 2. crear/actualizar relaciones entre tablas para todos los grupos
								if (!insertResponseDTO.getStatus().equals(SigaConstants.KO)) {

									if (null != noColegiadoItem.getEtiquetas()) {
										for (ComboEtiquetasItem etiqueta : noColegiadoItem.getEtiquetas()) {
											
											if(etiqueta.getIdGrupo() != "") {
												LOGGER.info(
														"createLegalPerson() / cenGruposclienteExtendsMapper.createLegalPerson() -> Entrada a cenGruposclienteExtendsMapper para obtener grupos de clientes");
												cenGruposcliente = cenGruposclienteExtendsMapper.selectDistinctGruposClientes(
														String.valueOf(idInstitucion), usuario.getIdlenguaje(), etiqueta.getIdGrupo());
												LOGGER.info(
														"createLegalPerson() / cenGruposclienteExtendsMapper.createLegalPerson() -> Salida de cenGruposclienteExtendsMapper para obtener grupos de clientes");
				
												// 2.1 existen registros => solo insertar/actualizar fecha_baja en tabla
												// CEN_GRUPOSCLIENTE_CLIENTE
												if (null != cenGruposcliente && cenGruposcliente.size() > 0) {
				
													List<CenGruposclienteCliente> listarelacionGrupoPersona = new ArrayList<CenGruposclienteCliente>();
													CenGruposclienteClienteExample relacionGrupoPersona = new CenGruposclienteClienteExample();
													relacionGrupoPersona.createCriteria()
															.andIdpersonaEqualTo(Long.valueOf(insertResponseDTO.getId()))
															.andIdgrupoEqualTo(Short.valueOf(etiqueta.getIdGrupo()))
															.andIdinstitucionEqualTo(idInstitucion);
													listarelacionGrupoPersona = cenGruposclienteClienteExtendsMapper
															.selectByExample(relacionGrupoPersona);
				
													// 2.1.1. Si no existe registro en CEN_GRUPOSCLIENTE_CLIENTE se inserta
													if (listarelacionGrupoPersona.isEmpty()) {
														LOGGER.info(
																"createLegalPerson() / cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> Entrada a cenGruposclienteClienteExtendsMapper para crear relacion grupo-persona jurídica");
				
														int response = cenGruposclienteClienteExtendsMapper
																.insertSelectiveForCreateLegalPerson(etiqueta, String.valueOf(idInstitucion),
																		etiqueta.getIdGrupo(), String.valueOf(usuario.getIdusuario()));
														LOGGER.info(
																"createLegalPerson() / cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> Salida a cenGruposclienteClienteExtendsMapper para crear relacion grupo-persona jurídica");
				
														if (response == 0) {
															insertResponseDTO.setStatus(SigaConstants.KO);
															LOGGER.warn(
																	"createLegalPerson() / cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> No se pudo insertar el grupo = "
																			+ etiqueta.getIdGrupo() + " en tabla CEN_GRUPOSCLIENTE_CLIENTE");
														}else {
															gruposPerJuridicaNuevos.add(etiqueta.getIdGrupo());
														}
													}
													// 2.1.2. Si existe registro en CEN_GRUPOSCLIENTE_CLIENTE se actualiza su fecha
													// de baja
													else {
				
														listarelacionGrupoPersona.get(0).setFechaBaja(null);
														LOGGER.info(
																"createLegalPerson() / cenGruposclienteClienteExtendsMapper.updateByExample() -> Entrada a cenGruposclienteClienteExtendsMapper actualizar la relacion grupo-persona jurídica");
				
														int response = cenGruposclienteClienteExtendsMapper.updateByExample(
																listarelacionGrupoPersona.get(0), relacionGrupoPersona);
														LOGGER.info(
																"createLegalPerson() / cenGruposclienteClienteExtendsMapper.updateByExample() -> Salida de cenGruposclienteClienteExtendsMapper actualizar la relacion grupo-persona jurídica");
				
														if (response == 0) {
															insertResponseDTO.setStatus(SigaConstants.KO);
															LOGGER.warn(
																	"createLegalPerson() / cenGruposclienteClienteExtendsMapper.updateByExample() -> No se pudo actualizar la fecha de baja del grupo = "
																			+ etiqueta.getIdGrupo() + " en tabla CEN_GRUPOSCLIENTE_CLIENTE");
														}else {
															gruposPerJuridicaNuevos.add(etiqueta.getIdGrupo());
														}
				
													}
				
												}
												// 2.2 no existen registros => solo insertar en: tabla CEN_GRUPOSCLIENTE ,
												// GEN_RECURSOS_CATALOGOS y CEN_GRUPOSCLIENTE_CLIENTE
												else {
													int response1 = 0;
													int response2 = 0;
													int response3 = 0;
													// 1.2.1 insertar en GEN_RECURSOS_CATALOGOS para generar recurso
													String nombreTabla = "CEN_GRUPOSCLIENTE";
													String campoTabla = "NOMBRE";
													LOGGER.info(
															"createLegalPerson() / genRecursosCatalogosExtendsMapper.insertSelectiveForCreateLegalPerson() -> Entrada a genRecursosCatalogosExtendsMapper para crear recurso de una persona juridica");
													response1 = genRecursosCatalogosExtendsMapper.insertSelectiveForCreateLegalPerson(
															String.valueOf(idInstitucion), usuario, etiqueta.getIdGrupo(), nombreTabla, campoTabla);
													LOGGER.info(
															"createLegalPerson() / genRecursosCatalogosExtendsMapper.insertSelectiveForCreateLegalPerson() -> Salida de genRecursosCatalogosExtendsMapper para crear recurso de una persona juridica");
				
													if (response1 == 1) {
				
														// 2.2.2 insertar en CEN_GRUPOSCLIENTE para generar el grupo
														LOGGER.info(
																"createLegalPerson() / cenGruposclienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> Entrada a cenGruposclienteExtendsMapper para crear grupo de una persona jurídica");
				
														response2 = cenGruposclienteExtendsMapper.insertSelectiveForCreateLegalPerson(
																String.valueOf(idInstitucion), usuario);
														LOGGER.info(
																"createLegalPerson() / cenGruposclienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> Salida de cenGruposclienteExtendsMapper para crear grupo de una persona jurídica");
				
														if (response2 == 1) {
															// 2.2.3 insertar en CEN_GRUPOSCLIENTE_CLIENTE para relacionar
															// grupos-usuarios
															LOGGER.info(
																	"createLegalPerson() / cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> Entrada a cenGruposclienteClienteExtendsMapper para crear relacion grupo-persona jurídica");
				
															response3 = cenGruposclienteClienteExtendsMapper
																	.insertSelectiveForCreateLegalPerson(etiqueta, String.valueOf(idInstitucion),
																			"", String.valueOf(usuario.getIdusuario()));
															LOGGER.info(
																	"createLegalPerson() / cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> Salida de cenGruposclienteClienteExtendsMapper para crear relacion grupo-persona jurídica");
				
															if (response3 == 0) {
																insertResponseDTO.setStatus(SigaConstants.KO);
																LOGGER.warn(
																		"createLegalPerson() / cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> No se pudo insertar el grupo = "
																				+ etiqueta.getIdGrupo() + " en tabla CEN_GRUPOSCLIENTE_CLIENTE");
				
															}else {
																gruposPerJuridicaNuevos.add(etiqueta.getIdGrupo());
															}
														} else {
															insertResponseDTO.setStatus(SigaConstants.KO);
															LOGGER.warn(
																	"createLegalPerson() / cenGruposclienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> No se pudo insertar el grupo = "
																			+ etiqueta.getIdGrupo() + " en tabla CEN_GRUPOSCLIENTE");
														}
													} else {
														insertResponseDTO.setStatus(SigaConstants.KO);
														LOGGER.warn(
																"createLegalPerson() / genRecursosCatalogosExtendsMapper.insertSelectiveForCreateLegalPerson() -> No se pudo insertar el grupo = "
																		+ etiqueta.getIdGrupo() + " en tabla GEN_RECURSOS_CATALOGOS");
													}
												}
											}
										}
									}
								
							
							}
							else {
								insertResponseDTO.setStatus(SigaConstants.KO);
								LOGGER.warn(
										"createColegiado() / cenNocolegiadoExtendsMapper.insertSelectiveForCreateLegalPerson() -> No se ha podido crear la persona no colegiada en tabla CEN_NOCOLEGIADO");
							}
							}else {
									insertResponseDTO.setStatus(SigaConstants.KO);
									org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
									error.setMessage("messages.censo.nifcifExiste2");
									insertResponseDTO.setError(error);
								}
						} else {
							insertResponseDTO.setStatus(SigaConstants.KO);
							LOGGER.warn(
									"createColegiado() / cenPersonaExtendsMapper.insertSelectiveForCreateLegalPerson() -> No se ha podido crear la persona no colegiada en tabla CEN_NOCOLEGIADO");
						}

					
					
				} else {
					insertResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.warn(
							"createColegiado() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
									+ dni + " e idInstitucion = " + idInstitucion);
				}
				
				
			} 
				
				
			} else {
				insertResponseDTO.setStatus(SigaConstants.KO);
				LOGGER.warn("createLegalPerson() -> idInstitucion del token nula");
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

//	@Override
//	public ComboDTO getLabel(HttpServletRequest request) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	
}
