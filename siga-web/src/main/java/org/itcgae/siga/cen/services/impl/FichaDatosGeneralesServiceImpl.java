package org.itcgae.siga.cen.services.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.ParametroRequestDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.ComboEtiquetasDTO;
import org.itcgae.siga.DTOs.cen.ComboEtiquetasItem;
import org.itcgae.siga.DTOs.cen.DatosDireccionesDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesItem;
import org.itcgae.siga.DTOs.cen.EtiquetaUpdateDTO;
import org.itcgae.siga.DTOs.cen.NoColegiadoItem;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.cen.services.IFichaDatosGeneralesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmConfig;
import org.itcgae.siga.db.entities.AdmConfigExample;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteExample;
import org.itcgae.siga.db.entities.CenClienteKey;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoExample;
import org.itcgae.siga.db.entities.CenColegiadoKey;
import org.itcgae.siga.db.entities.CenDireccionTipodireccion;
import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.entities.CenGruposcliente;
import org.itcgae.siga.db.entities.CenGruposclienteCliente;
import org.itcgae.siga.db.entities.CenGruposclienteClienteExample;
import org.itcgae.siga.db.entities.CenNocolegiado;
import org.itcgae.siga.db.entities.CenNocolegiadoExample;
import org.itcgae.siga.db.entities.CenNocolegiadoKey;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.CenSolicitmodifdatosbasicos;
import org.itcgae.siga.db.entities.CenSolicmodifcambiarfoto;
import org.itcgae.siga.db.entities.ForTemacursoPersona;
import org.itcgae.siga.db.entities.ForTemacursoPersonaExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.mappers.AdmConfigMapper;
import org.itcgae.siga.db.mappers.CenClienteMapper;
import org.itcgae.siga.db.mappers.CenDireccionTipodireccionMapper;
import org.itcgae.siga.db.mappers.CenSolicmodifcambiarfotoMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenRecursosCatalogosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenClienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDireccionesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenEstadocivilExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenGruposclienteClienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenGruposclienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenNocolegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicitmodifdatosbasicosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTratamientoExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.ForTemacursoPersonaExtendsMapper;
import org.itcgae.siga.gen.services.IAuditoriaCenHistoricoService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
@Transactional(timeout=2400)
public class FichaDatosGeneralesServiceImpl implements IFichaDatosGeneralesService {

	private Logger LOGGER = Logger.getLogger(FichaDatosGeneralesServiceImpl.class);

	@Autowired
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;

	@Autowired
	private GenParametrosExtendsMapper genParametrosMapper;

	@Autowired
	private GenPropertiesMapper genPropertiesMapper;
	
	@Autowired
	private CenDireccionTipodireccionMapper cenDireccionTipoDireccionMapper;
	
	
	@Autowired
	private CenDireccionesExtendsMapper _cenDireccionesMapper;

	@Autowired
	private CenNocolegiadoExtendsMapper cenNocolegiadoMapper;

	@Autowired
	private CenColegiadoExtendsMapper cenColegiadoMapper;

	@Autowired
	private CenSolicmodifcambiarfotoMapper cenSolicmodifcambiarfoto;

	@Autowired
	private CenDireccionesExtendsMapper cenDireccionesExtendsMapper;

	@Autowired
	private CenGruposclienteClienteExtendsMapper cenGruposclienteClienteExtendsMapper;

	@Autowired
	private CenSolicitmodifdatosbasicosExtendsMapper cenSolicitmodifdatosbasicosMapper;

	// @Autowired
	// private CenDatoscolegialesestadoExtendsMapper cenDatoscolegialesestadoMapper;

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

	@Autowired
	private ForTemacursoPersonaExtendsMapper forTemaCursoPersonaExtendsMapper;

	@Autowired
	private CenClienteExtendsMapper cenClienteExtendsMapper;

	@Autowired
	private IAuditoriaCenHistoricoService auditoriaCenHistoricoService;

	@Autowired
	private AdmConfigMapper admConfigMapper;
	// @Override
	// public ComboDTO getSocietyTypes(HttpServletRequest request) {
	// // TODO Auto-generated method stub
	// return null;
	// }

	@Override
	public UpdateResponseDTO updateColegiado(ColegiadoItem colegiadoItem, HttpServletRequest request)
			throws ParseException {
		LOGGER.info("updateColegiado() -> Entrada al servicio para actualizar información general de un colegiado");
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		// List<CenDatoscolegialesestado> cenDatoscolegialesestado = new
		// ArrayList<CenDatoscolegialesestado>();
		List<ComboEtiquetasItem> gruposPersona = new ArrayList<ComboEtiquetasItem>();
		EtiquetaUpdateDTO etiquetaUpdateDTO = new EtiquetaUpdateDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		boolean cambioEtiquetas = false;

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

				try {
					// 1. obtener colegiado

					CenColegiado cenColegiadoAnterior = new CenColegiado();
					CenColegiadoKey cencolegiadoKey = new CenColegiadoKey();
					cencolegiadoKey.setIdpersona(Long.valueOf(colegiadoItem.getIdPersona()));
					cencolegiadoKey.setIdinstitucion(idInstitucion);
					cenColegiadoAnterior = cenColegiadoMapper.selectByPrimaryKey(cencolegiadoKey);

					CenPersona cenPersona = cenPersonaExtendsMapper
							.selectByPrimaryKey(Long.valueOf(colegiadoItem.getIdPersona()));
					
					CenPersona cenPersonaAnterior = mapCenPersona(cenPersona);

					etiquetaUpdateDTO.setIdPersona(colegiadoItem.getIdPersona());

					// 1. Etiquetas asociadas a la sociedad
					gruposPersona = cenGruposclienteClienteExtendsMapper.selectGruposPersonaJuridicaLenguaje(
							etiquetaUpdateDTO.getIdPersona(), String.valueOf(usuario.getIdinstitucion()), usuario.getIdlenguaje());

					List<String[]> gruposPerJuridicaAntiguos = new  ArrayList<String[]>();
					List<String[]> gruposPerJuridicaAnterior = new ArrayList<String[]>();
					List<String> gruposPerJuridicaPosterior = new ArrayList<String>();
					List<String[]> gruposPerJuridicaPosteriorAudit = new ArrayList<String[]>();


					for (int i = 0; i < gruposPersona.size(); i++) {
						String[] a = {
								gruposPersona.get(i).getIdGrupo(),
								gruposPersona.get(i).getIdInstitucion()
						};
						
						gruposPerJuridicaAntiguos.add(a);
						gruposPerJuridicaAnterior.add(a);
					}
					// 2. Recorremos las etiquetas
					//En colegiadoItem.getEtiquetas() no vienen las idInstitucion 2000 y las intenta borrar
					for (ComboEtiquetasItem etiqueta : colegiadoItem.getEtiquetas()) {
						String[] a = {
								etiqueta.getIdGrupo(),
								etiqueta.getIdInstitucion()
						};
						gruposPerJuridicaPosteriorAudit.add(a);
						
						// 2.1. Es una etiqueta de nueva y no existe registro en ninguna tabla
						if (etiqueta.getIdGrupo() == "") {

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

							genRecursosCatalogos.setIdrecursoalias(
									"cen_gruposcliente.nombre." + usuario.getIdinstitucion() + "." + idGrupo);
							genRecursosCatalogos.setUsumodificacion(usuario.getIdusuario());
							genRecursosCatalogos.setFechamodificacion(new Date());
							genRecursosCatalogos.setIdinstitucion(usuario.getIdinstitucion());
							genRecursosCatalogos.setNombretabla("CEN_GRUPOSCLIENTE");

							int resultGenRecursosCatalogos = genRecursosCatalogosExtendsMapper
									.insert(genRecursosCatalogos);

							if (resultGenRecursosCatalogos == 1) {

								LOGGER.warn(
										"updateLegalPerson() / genRecursosCatalogosExtendsMapper.insert() -> Insertada la descripción en genRecursosCatálogos correctamente");

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
									cenGruposclienteCliente.setFechaInicio(fechaInicio);
									if (etiqueta.getFechaBaja() != null) {
										Date fechaBaja = df.parse(etiqueta.getFechaBaja());
										cenGruposclienteCliente.setFechaBaja(fechaBaja);
									} else {
										cenGruposclienteCliente.setFechaBaja(null);
									}
									cenGruposclienteCliente.setFechamodificacion(new Date());
									cenGruposclienteCliente.setIdgrupo(idGrupo);
									cenGruposclienteCliente.setIdinstitucion(usuario.getIdinstitucion());
									cenGruposclienteCliente.setIdinstitucionGrupo(usuario.getIdinstitucion());
									cenGruposclienteCliente
											.setIdpersona(Long.valueOf(etiquetaUpdateDTO.getIdPersona()));
									cenGruposclienteCliente.setUsumodificacion(usuario.getIdusuario());

									int resultCenGruposClienteClientes = cenGruposclienteClienteExtendsMapper
											.insert(cenGruposclienteCliente);

									if (resultCenGruposClienteClientes == 1) {
										updateResponseDTO.setStatus(SigaConstants.OK);
										gruposPerJuridicaPosterior.add(String.valueOf(cenGruposcli.getIdgrupo()));
										
										LOGGER.warn(
												"updateLegalPerson() / cenGruposclienteClienteExtendsMapper.insert() -> Insertado correctamente en la tabla CenGruposClienteClientes");
									} else {
										updateResponseDTO.setStatus(SigaConstants.KO);
										LOGGER.warn(
												"updateLegalPerson() / cenGruposclienteClienteExtendsMapper.insert() -> No se ha podido insertar en la tabla CenGruposClienteClientes");
									}

								} else {
									updateResponseDTO.setStatus(SigaConstants.KO);
									LOGGER.warn(
											"updateLegalPerson() / cenGruposclienteMapper.insert() -> No insertado el id correctamente en la tabla CenGruposCliente");
								}

							} else {
								updateResponseDTO.setStatus(SigaConstants.KO);
								LOGGER.warn(
										"updateLegalPerson() / genRecursosCatalogosExtendsMapper.insert() -> No insertada la descripción en genRecursosCatálogos correctamente");
							}
						} else {
							
							boolean encontrado = false;
							int i = 0;
							
							for (String[] array: gruposPerJuridicaAntiguos) {
								
								if(array[0].equals(etiqueta.getIdGrupo()) && array[1].equals(etiqueta.getIdInstitucion())) {
									encontrado = true;
									break;
								}
								i++;
							}
							
							// Etiqueta para nueva asociación
							if (!encontrado) {
								// Buscamos si esa etiqueta ya existe en la tabla de relaciones
								List<CenGruposclienteCliente> listarelacionGrupoPersona = new ArrayList<CenGruposclienteCliente>();
								CenGruposclienteClienteExample relacionGrupoPersona = new CenGruposclienteClienteExample();
								relacionGrupoPersona.createCriteria()
										.andIdpersonaEqualTo(Long.valueOf(etiquetaUpdateDTO.getIdPersona()))
										.andIdgrupoEqualTo(Short.valueOf(etiqueta.getIdGrupo()))
										.andIdinstitucionEqualTo(idInstitucion)
										.andIdinstitucionGrupoEqualTo(Short.valueOf(etiqueta.getIdInstitucion()));
								listarelacionGrupoPersona = cenGruposclienteClienteExtendsMapper
										.selectByExample(relacionGrupoPersona);

								// Si no existe --> creamos un nuevo registro
								if (listarelacionGrupoPersona.isEmpty()) {
									cambioEtiquetas = true;

									LOGGER.info(
											"updateLegalPerson() / cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> Entrada a cenGruposclienteClienteExtendsMapper para crear relacion grupo-persona jurídica");
									SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy",
											Locale.getDefault());
									Date date = new Date();
									String fecha = dateFormat.format(date);
									etiqueta.setFechaInicio(fecha);
									int response = 0;

									response = cenGruposclienteClienteExtendsMapper.insertSelectiveForUpdateLegalPerson(
											etiqueta, etiquetaUpdateDTO.getIdPersona(), etiqueta.getIdInstitucion(),
											String.valueOf(idInstitucion), String.valueOf(usuario.getIdusuario()));
								
									LOGGER.info(
											"updateLegalPerson() / cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> Salida a cenGruposclienteClienteExtendsMapper para crear relacion grupo-persona jurídica");

									if (response == 0) {
										updateResponseDTO.setStatus(SigaConstants.KO);
										LOGGER.warn(
												"updateLegalPerson() / cenGruposclienteExtendsMapper.createLegalPerson() -> No se pudo insertar el grupo = "
														+ etiqueta.getIdGrupo()
														+ " en tabla CEN_GRUPOSCLIENTE_CLIENTE");
									} else {
										gruposPerJuridicaPosterior.add(etiqueta.getIdGrupo());
									}

								}

								// El grupo ya existe
							} else {
								// eliminamos grupo de los antiguos que tenia la persona juridica para que solo
								// queden los que hay que eliminar
								
								gruposPerJuridicaAntiguos.remove(i);

								// Pero insertamos el resgistro para auditoría
								gruposPerJuridicaPosterior.add(etiqueta.getIdGrupo());
							}
						}
					}
					
					//Eliminamos etiquetas
					 for (String [] etiquetaEliminar : gruposPerJuridicaAntiguos) {
						 cambioEtiquetas = true;
							CenGruposclienteClienteExample cenGruposclienteClienteExample = new CenGruposclienteClienteExample();
							cenGruposclienteClienteExample.createCriteria()
							.andIdinstitucionEqualTo(idInstitucion)
									.andIdpersonaEqualTo(Long.valueOf(etiquetaUpdateDTO.getIdPersona()))
									.andIdgrupoEqualTo(Short.valueOf(etiquetaEliminar[0]))
									.andIdinstitucionGrupoEqualTo(Short.valueOf(etiquetaEliminar[1]));

							LOGGER.info(
									"updateLegalPerson() / cenGruposclienteClienteExtendsMapper.updateByExampleSelective() -> Entrada a cenGruposclienteClienteExtendsMapper para eliminar un grupo relacionado con persona juridica en tabla CEN_GRUPOSCLIENTE_CLIENTE");

							int eliminadoGrupo = cenGruposclienteClienteExtendsMapper
									.deleteByExample(cenGruposclienteClienteExample);

							// .updateByExample(cenGruposclienteCliente, cenGruposclienteClienteExample);
							LOGGER.info(
									"updateLegalPerson() / cenGruposclienteClienteExtendsMapper.updateByExampleSelective() -> Salida de cenGruposclienteClienteExtendsMapper para eliminar un grupo relacionado con persona juridica en tabla CEN_GRUPOSCLIENTE_CLIENTE");

							if (eliminadoGrupo == 0) {
								updateResponseDTO.setStatus(SigaConstants.KO);
								LOGGER.warn(
										"updateLegalPerson() / cenGruposclienteClienteExtendsMapper.updateByExampleSelective() -> No se pudo eliminar el grupo ="
								+ etiquetaEliminar[0] + "en tabla CEN_GRUPOSCLIENTE_CLIENTE");
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
						if (colegiadoItem.getIdEstadoCivil() != null) {
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
						dniRepetido.createCriteria().andNifcifEqualTo(colegiadoItem.getNif())
								.andIdpersonaNotEqualTo(Long.valueOf(colegiadoItem.getIdPersona()));
						List<CenPersona> busqueda = cenPersonaExtendsMapper.selectByExample(dniRepetido);
						if (busqueda.isEmpty()) {
							cenPersonaExtendsMapper.updateByExample(cenPersona, cenPersonaExample1);
						} else {
							updateResponseDTO.setStatus(SigaConstants.KO);
							Error error = new Error();
							// Ya existe un usuario con este número de identificación
							if (colegiadoItem.getColegiado() == false || colegiadoItem.getColegiado() == null) {
								error.setMessage(
										"censo.fichaColegial.datosGenerales.literal.numDocumentoExistente.noColegiado");
							} else {
								error.setMessage(
										"censo.fichaColegial.datosGenerales.literal.numDocumentoExistente.colegiado");
							}
							updateResponseDTO.setError(error);
						}
						LOGGER.info(
								"updateColegiado() / cenPersonaExtendsMapper.updateByExampleSelective() -> Salida de cenPersonaExtendsMapper para actualizar información de colegiado en CEN_PERSONA");

						// 3. Actualiza tabla CEN_CLIENTE

						CenClienteKey key = new CenClienteKey();
						key.setIdpersona(Long.valueOf(colegiadoItem.getIdPersona()));
						key.setIdinstitucion(idInstitucion);

						CenCliente cenCliente = new CenCliente();
						cenCliente = cenClienteMapper.selectByPrimaryKey(key);
						
						CenCliente cenClienteAnterior = cenClienteMapper.selectByPrimaryKey(key);
						
						// Por ahora no tenemos cuenta contable, se queda pendiente
						// cenCliente.setAsientocontable(colegiadoItem.getCuentaContable());
						cenCliente.setIdlenguaje(colegiadoItem.getIdLenguaje());
						if (colegiadoItem.getIncorporacionDate() != null) {
							cenCliente.setFechaalta(colegiadoItem.getIncorporacionDate());
						}
						cenCliente.setFechamodificacion(new Date());
						cenCliente.setUsumodificacion(usuario.getIdusuario());
						cenCliente.setComisiones(colegiadoItem.getComisiones());
						cenCliente.setIdtratamiento(Short.parseShort(colegiadoItem.getIdTratamiento()));
						cenCliente.setAsientocontable(colegiadoItem.getAsientoContable());
						cenCliente.setCaracter("P");
						cenCliente.setNoaparecerredabogacia(colegiadoItem.getNoAparecerRedAbogacia());

						CenClienteExample cenClienteExample = new CenClienteExample();
						cenClienteExample.createCriteria()
								.andIdpersonaEqualTo(Long.valueOf(colegiadoItem.getIdPersona()))
								.andIdinstitucionEqualTo(idInstitucion);
						LOGGER.info(
								"updateColegiado() / cenClienteMapper.updateByExampleSelective() -> Entrada a cenClienteMapper para actualizar información de colegiado en CEN_CLIENTE");
						cenClienteMapper.updateByExampleSelective(cenCliente, cenClienteExample);
						LOGGER.info(
								"updateColegiado() / cenClienteMapper.updateByExampleSelective() -> Salida de cenClienteMapper para actualizar información de colegiado en CEN_CLIENTE");

						// 3.5 Actualiza CEN_COLEGIADO O CEN_NOCOLEGIADO
						if (colegiadoItem.getColegiado() == false || colegiadoItem.getColegiado() == null) {
							CenNocolegiadoExample cenNocolegiadoExample = new CenNocolegiadoExample();
							cenNocolegiadoExample.createCriteria()
									.andIdpersonaEqualTo(Long.valueOf(colegiadoItem.getIdPersona()))
									.andIdinstitucionEqualTo(idInstitucion);

							CenNocolegiado cenNocolegiado = new CenNocolegiado();
							cenNocolegiado.setUsumodificacion(usuario.getIdusuario());
							cenNocolegiado.setFechamodificacion(new Date());

							cenNocolegiadoMapper.updateByExampleSelective(cenNocolegiado, cenNocolegiadoExample);
						} else {
							CenColegiadoExample cenColegiadoExample = new CenColegiadoExample();
							cenColegiadoExample.createCriteria()
									.andIdpersonaEqualTo(Long.valueOf(colegiadoItem.getIdPersona()))
									.andIdinstitucionEqualTo(idInstitucion);
							CenColegiado cenColegiado = new CenColegiado();
							cenColegiado.setUsumodificacion(usuario.getIdusuario());
							cenColegiado.setFechaincorporacion(colegiadoItem.getIncorporacionDate());
							cenColegiado.setFechamodificacion(new Date());

							cenColegiadoMapper.updateByExampleSelective(cenColegiado, cenColegiadoExample);
						}

						// // 4. Actualiza la tabla CEN_DATOSCOLEGIALESESTADO
						//
						// CenDatoscolegialesestadoExample cenDatoscolegialesestadoExample = new
						// CenDatoscolegialesestadoExample();
						// cenDatoscolegialesestadoExample.createCriteria().andIdpersonaEqualTo(Long.valueOf(colegiadoItem.getIdPersona())).andIdinstitucionEqualTo(idInstitucion);
						// cenDatoscolegialesestadoExample.setOrderByClause("FECHAESTADO DESC");
						// // Buscamos por idPersona para ver si el estado es diferente
						//
						// cenDatoscolegialesestado =
						// cenDatoscolegialesestadoMapper.selectByExample(cenDatoscolegialesestadoExample);
						//
						// if(cenDatoscolegialesestado != null && cenDatoscolegialesestado.size()>0) {
						// if
						// (!cenDatoscolegialesestado.get(0).getIdestado().equals(Short.valueOf(colegiadoItem.getSituacion())))
						// {
						// CenDatoscolegialesestado cenEstadoColegial = new CenDatoscolegialesestado();
						// cenEstadoColegial.setIdestado(Short.parseShort(colegiadoItem.getSituacion()));
						// cenEstadoColegial.setIdpersona(Long.parseLong(colegiadoItem.getIdPersona()));
						// cenEstadoColegial.setIdinstitucion(Short.parseShort(colegiadoItem.getIdInstitucion()));
						// cenEstadoColegial.setFechamodificacion(new Date());
						// cenEstadoColegial.setUsumodificacion(usuario.getIdusuario());
						// cenEstadoColegial.setFechaestado(new Date());
						// cenDatoscolegialesestadoMapper.insertSelective(cenEstadoColegial);
						// }
						// }

						// Comenzamos el proceso de guardar los temas en la tabla for_temacurso_persona
						if (colegiadoItem.getTemasCombo() != null && colegiadoItem.getTemasCombo().size() > 0) {

							// Eliminamos el tema que no se encuentre en la lista actual
							ForTemacursoPersonaExample forTemacursoPersonaExample = new ForTemacursoPersonaExample();
							forTemacursoPersonaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andIdpersonaEqualTo(Long.parseLong(colegiadoItem.getIdPersona()))
									.andFechabajaIsNull();

							LOGGER.info(
									"updateColegiado() / forTemaCursoPersonaExtendsMapper.selectByExample(forTemacursoPersonaExample) -> Entrada a forTemacursoCursoMapper para buscar los temas registrados de una persona");

							List<ForTemacursoPersona> forTemacursoCursoAntiguosList = forTemaCursoPersonaExtendsMapper
									.selectByExample(forTemacursoPersonaExample);

							LOGGER.info(
									"updateColegiado() / forTemaCursoPersonaExtendsMapper.selectByExample(forTemacursoPersonaExample) -> Salida a forTemacursoCursoMapper para buscar los temas registrados de una persona");

							List<ForTemacursoPersona> forTemacursoCursoDarBaja = new ArrayList<ForTemacursoPersona>();

							// Si hay temas que estan dados de alta, comprobamos que se encuentra en la
							// modificacion actual
							if (!forTemacursoCursoAntiguosList.isEmpty()) {

								for (ForTemacursoPersona temasAsignadosAntiguos : forTemacursoCursoAntiguosList) {
									boolean flag = false;
									for (int i = 0; colegiadoItem.getTemasCombo().size() > i; i++) {

										if (temasAsignadosAntiguos.getIdtemacurso() == Long
												.valueOf(colegiadoItem.getTemasCombo().get(i).getValue())) {
											flag = true;
											i = colegiadoItem.getTemasCombo().size();
										}
									}

									// Si no se encuentra en la lista actual la borramos
									if (!flag) {
										forTemacursoCursoDarBaja.add(temasAsignadosAntiguos);
									}
								}

								for (ForTemacursoPersona temaCursoBaja : forTemacursoCursoDarBaja) {

									temaCursoBaja.setUsumodificacion(usuario.getIdusuario().longValue());
									temaCursoBaja.setFechabaja(new Date());
									temaCursoBaja.setFechamodificacion(new Date());

									LOGGER.info(
											"updateColegiado() / forTemaCursoPersonaExtendsMapper.updateByPrimaryKey(temaCursoBaja) -> Entrada a forTemaCursoPersonaExtendsMapper para dar de baja a un tema de una persona");

									forTemaCursoPersonaExtendsMapper.updateByPrimaryKey(temaCursoBaja);

									LOGGER.info(
											"updateColegiado() / forTemaCursoPersonaExtendsMapper.updateByPrimaryKey(temaCursoBaja) -> Salida a forTemaCursoPersonaExtendsMapper para dar de baja a un tema de una persona");
								}
							}

							// Añadimos temas
							for (ComboItem tema : colegiadoItem.getTemasCombo()) {

								// Para cada temas comprobamos si ya existe la relacion
								ForTemacursoPersonaExample forTemacursoExample = new ForTemacursoPersonaExample();
								forTemacursoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
										.andIdpersonaEqualTo(Long.parseLong(colegiadoItem.getIdPersona()))
										.andIdtemacursoEqualTo(Long.valueOf(tema.getValue()));

								LOGGER.info(
										"updateColegiado() / forTemaCursoPersonaExtendsMapper.selectByExample(forTemacursoCursoExample) -> Entrada a forTemaCursoPersonaExtendsMapper para buscar los temas registrados de una persona");

								List<ForTemacursoPersona> forTemacursoCursoList = forTemaCursoPersonaExtendsMapper
										.selectByExample(forTemacursoExample);

								LOGGER.info(
										"updateColegiado() / forTemaCursoPersonaExtendsMapper.selectByExample(forTemacursoCursoExample) -> Salida a forTemaCursoPersonaExtendsMapper para buscar los temas registrados de una persona");

								// Si no existe la creamos
								if (forTemacursoCursoList.isEmpty()) {

									ForTemacursoPersona forTemacursoCurso = new ForTemacursoPersona();
									forTemacursoCurso.setFechabaja(null);
									forTemacursoCurso.setFechamodificacion(new Date());
									forTemacursoCurso.setIdinstitucion(idInstitucion);
									forTemacursoCurso.setIdpersona(Long.parseLong(colegiadoItem.getIdPersona()));
									forTemacursoCurso.setUsumodificacion(usuario.getIdusuario().longValue());
									forTemacursoCurso.setIdtemacurso(Long.valueOf(tema.getValue()));

									LOGGER.info(
											"updateColegiado() / forTemaCursoPersonaExtendsMapper.insert(forTipoServicioCurso) -> Entrada a forTemaCursoPersonaExtendsMapper para insertar un tema de una persona");

									forTemaCursoPersonaExtendsMapper.insert(forTemacursoCurso);

									LOGGER.info(
											"updateColegiado() / forTemaCursoPersonaExtendsMapper.insert(forTipoServicioCurso) -> Salida a forTemaCursoPersonaExtendsMapper para insertar un tema de una persona");

									// Si existe
								} else {
									// Comprobamos que si fecha de baja esta a null, si no esta la modificamos
									if (forTemacursoCursoList.get(0).getFechabaja() != null) {
										ForTemacursoPersona forTemaCurso = forTemacursoCursoList.get(0);
										forTemaCurso.setFechabaja(null);
										forTemaCurso.setUsumodificacion(usuario.getIdusuario().longValue());
										forTemaCurso.setFechamodificacion(new Date());

										LOGGER.info(
												"updateColegiado() / forTemaCursoPersonaExtendsMapper.updateByPrimaryKey(forTemaCurso) -> Entrada a forTemaCursoPersonaExtendsMapper para dar de alta a un tema de una persona");

										forTemaCursoPersonaExtendsMapper.updateByPrimaryKey(forTemaCurso);

										LOGGER.info(
												"updateColegiado() / forTemaCursoPersonaExtendsMapper.updateByPrimaryKey(forTemaCurso) -> Salida a forTemaCursoPersonaExtendsMapper para dar de alta a un tema de una persona");
									}
								}
							}

							// Comprobamos si existe algun tema para el curso y les damos de baja
						} else {
							// Eliminamos Tema
							ForTemacursoPersonaExample forTemacursoCursoExample = new ForTemacursoPersonaExample();
							forTemacursoCursoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
									.andIdpersonaEqualTo(Long.parseLong(colegiadoItem.getIdPersona()))
									.andFechabajaIsNull();

							LOGGER.info(
									"updateColegiado() / forTemaCursoPersonaExtendsMapper.selectByExample(forTemacursoCursoExample) -> Entrada a forTemaCursoPersonaExtendsMapper para buscar los temas registrados de una persona");

							List<ForTemacursoPersona> forTemacursoCursoAntiguosList = forTemaCursoPersonaExtendsMapper
									.selectByExample(forTemacursoCursoExample);

							LOGGER.info(
									"updateColegiado() / forTemaCursoPersonaExtendsMapper.selectByExample(forTemacursoCursoExample) -> Salida a forTemaCursoPersonaExtendsMapper para buscar los temas registrados de una persona");

							for (ForTemacursoPersona temasAsignadosAntiguos : forTemacursoCursoAntiguosList) {

								temasAsignadosAntiguos.setUsumodificacion(usuario.getIdusuario().longValue());
								temasAsignadosAntiguos.setFechabaja(new Date());
								temasAsignadosAntiguos.setFechamodificacion(new Date());

								LOGGER.info(
										"updateColegiado() / forTemaCursoPersonaExtendsMapper.updateByPrimaryKey(temaCursoBaja) -> Entrada a forTemaCursoPersonaExtendsMapper para dar de baja a un tema de una persona");

								forTemaCursoPersonaExtendsMapper.updateByPrimaryKey(temasAsignadosAntiguos);

								LOGGER.info(
										"updateColegiado() / forTemaCursoPersonaExtendsMapper.updateByPrimaryKey(temaCursoBaja) -> Salida a forTemaCursoPersonaExtendsMapper para dar de baja a un tema de una persona");

							}
						}

						if (!updateResponseDTO.getStatus().equals(SigaConstants.KO)) {

							if (!UtilidadesString.esCadenaVacia(colegiadoItem.getMotivo())) {
//								if (null != colegiadoItem.getColegiado() && colegiadoItem.getColegiado()) {
									Long idPersonaCreada = Long.valueOf(colegiadoItem.getIdPersona());
									// obtenemos registro cen_persona creado
									CenPersona cenPersonaPosterior = new CenPersona();
									cenPersonaPosterior = cenPersonaExtendsMapper.selectByPrimaryKey(idPersonaCreada);
									// obtenemos registro cen_nocolegiado creado
									CenColegiado cenColegiadoPosterior = new CenColegiado();
									cencolegiadoKey = new CenColegiadoKey();
									cencolegiadoKey.setIdpersona(idPersonaCreada);
									cencolegiadoKey.setIdinstitucion(idInstitucion);
									cenColegiadoPosterior = cenColegiadoMapper.selectByPrimaryKey(cencolegiadoKey);
									// obtenemos registro cen_cliente creado
									CenCliente cenClientePosterior = new CenCliente();
									CenClienteKey cenClienteKey = new CenClienteKey();
									cenClienteKey.setIdpersona(idPersonaCreada);
									cenClienteKey.setIdinstitucion(idInstitucion);
									cenClientePosterior = cenClienteMapper.selectByPrimaryKey(cenClienteKey);
									// obtenemos las etiquetas creadas
									// List<String> gruposPerJuridicaNuevos =
									// Arrays.asList(sociedadCreateDTO.getGrupos());
									// llamada a auditoria
									auditoriaCenHistoricoService.manageAuditoriaDatosGeneralesColegiado(
											gruposPerJuridicaPosteriorAudit, gruposPerJuridicaAnterior, cenPersonaAnterior,
											cenPersonaPosterior, cenColegiadoAnterior, cenColegiadoPosterior,
											cenClienteAnterior, cenClientePosterior, "UPDATE", request,
											colegiadoItem.getMotivo(), cambioEtiquetas);

//								}
							}

							if (!updateResponseDTO.getStatus().equals(SigaConstants.KO)) {
								
								// Se comprueba si se deben revisar las cuentas y se ejecutan los scripts que se
								// encargan de ello
								LOGGER.info(
										"updateColegiado() / forTemaCursoPersonaExtendsMapper.updateByPrimaryKey(temaCursoBaja) -> Entrada a ejecutarPL_RevisionSuscripcionesLetrado");
								// Lanzamos el proceso de revision de suscripciones del letrado
								String resultado[] = ejecutarPL_RevisionSuscripcionesLetrado("" + idInstitucion.toString(),
										"" + colegiadoItem.getIdPersona(), "", "" + usuario.getIdusuario().toString());
								if ((resultado == null) || (!resultado[0].equals("0"))) {
									LOGGER.info(
											"updateColegiado() / forTemaCursoPersonaExtendsMapper.updateByPrimaryKey(temaCursoBaja) -> Entrada a error al ejecutarPL_RevisionSuscripcionesLetrado");
									Error error = new Error();
									updateResponseDTO.setStatus(SigaConstants.KO);
									error.setMessage("Error al ejecutar el PL PKG_SERVICIOS_AUTOMATICOS.PROCESO_REVISION_LETRADO"
											+ resultado[1]);
									updateResponseDTO.setError(error);
									return updateResponseDTO;
								}
								LOGGER.info(
										"updateColegiado() / forTemaCursoPersonaExtendsMapper.updateByPrimaryKey(temaCursoBaja) -> Salida a ejecutarPL_RevisionSuscripcionesLetrado");
							}
							updateResponseDTO.setStatus(SigaConstants.OK);
						}

						
						
					} else {
						updateResponseDTO.setStatus(SigaConstants.KO);
						LOGGER.warn(
								"updateColegiado() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
										+ dni + " e idInstitucion = " + idInstitucion);
					}

				} catch (Exception e) {
					updateResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.warn(
							"updateColegiado() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
									+ dni + " e idInstitucion = " + idInstitucion);
				}
			} else {
				updateResponseDTO.setStatus(SigaConstants.KO);
				LOGGER.warn("updateColegiado() -> idInstitucion del token nula");
			}

			LOGGER.info("updateColegiado() -> Salida del servicio para actualizar información general de un colegiado");
		}
		return updateResponseDTO;
	}

	@Override
	public ComboEtiquetasDTO getLabelPerson(ColegiadoItem colegiadoItem, HttpServletRequest request)
			throws ParseException {
		LOGGER.info("getLabelPerson() -> Entrada al servicio para obtener etiquetas de una persona jurídica");
		ComboEtiquetasDTO comboEtiquetasDTO = new ComboEtiquetasDTO();
		List<ComboEtiquetasItem> comboEtiquetasItems = new ArrayList<ComboEtiquetasItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getLabel() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getLabel() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getLabelPerson() / cenGruposclienteClienteExtendsMapper.selectGruposPersonaJuridica() -> Entrada a cenGruposclienteClienteExtendsMapper para obtener grupos de una persona jurídica");
				if(colegiadoItem.getIdPersona() != null) {
					comboEtiquetasItems = cenGruposclienteClienteExtendsMapper.selectGruposPersonaJuridicaLenguaje(colegiadoItem.getIdPersona(), String.valueOf(idInstitucion), usuario.getIdlenguaje());
				}
				
				LOGGER.info(
						"getLabelPerson() / cenGruposclienteClienteExtendsMapper.selectGruposPersonaJuridica() -> Entrada a cenGruposclienteClienteExtendsMapper para obtener grupos de una persona jurídica");
			}
			else {
				LOGGER.warn("getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		}

		Date date = new Date();
		Date fechaBaja;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String fechaHoy = simpleDateFormat.format(date);

		for (ComboEtiquetasItem comboEtiquetasItem : comboEtiquetasItems) {
			Date fechaInicio = simpleDateFormat.parse(comboEtiquetasItem.getFechaInicio());

			if (comboEtiquetasItem.getFechaBaja() != null) {
				fechaBaja = simpleDateFormat.parse(comboEtiquetasItem.getFechaBaja());
			} else {
				fechaBaja = null;
			}

			Date fechaActual = simpleDateFormat.parse(fechaHoy);

			if (fechaBaja == null) {
				comboEtiquetasItem.setColor("#024eff");
			} else {
				if ((fechaInicio.before(fechaActual) && fechaBaja.after(fechaActual))) {
					comboEtiquetasItem.setColor("#024eff");
				} else if (fechaActual.compareTo(fechaBaja) == 0 && fechaActual.compareTo(fechaInicio) == 0) {
					comboEtiquetasItem.setColor("#024eff");
				} else if (fechaActual.before(fechaInicio) && fechaBaja.after(fechaInicio)) {
					comboEtiquetasItem.setColor("#024eff");
				} else if (fechaInicio.before(fechaBaja) && fechaActual.after(fechaBaja)) {
					comboEtiquetasItem.setColor("#f70000");
				} else {
					comboEtiquetasItem.setColor("#f70000");
				}
			}
			
			if(comboEtiquetasItem.getLabel().contains("#")) {
				comboEtiquetasItem.setLabel(comboEtiquetasItem.getLabel().split("#")[0]);
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
		// ColegiadoDTO colegiado = new ColegiadoDTO();

		List<DatosDireccionesItem> datosDireccionesItem = new ArrayList<DatosDireccionesItem>();
		// DatosDireccionesDTO datosDireccionesDTO = new DatosDireccionesDTO();

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
				if(colegiadoItem.getIdPersona() != null) {
					datosDireccionesItem = cenDireccionesExtendsMapper.selectPartidoJudicial(colegiadoItem.getIdPersona(), idInstitucion.toString());
				}
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
		Error error = new Error();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String dni = UserTokenUtils.getDniFromJWTToken(token);

		// obtener path para almacenar las fotografias
		LOGGER.debug("uploadPhotography() -> Obtener path para almacenar las fotografias");
		GenPropertiesExample genPropertiesExample = new GenPropertiesExample();
		genPropertiesExample.createCriteria().andParametroEqualTo("directorios.carpeta.fotos");
		LOGGER.info(
				"loadPhotography() / genPropertiesMapper.selectByExample() -> Entrada a genPropertiesMapper para obtener directorio de la fotografía");
		List<GenProperties> properties = genPropertiesMapper.selectByExample(genPropertiesExample);
		LOGGER.info(
				"loadPhotography() / genPropertiesMapper.selectByExample() -> Salida de genPropertiesMapper para obtener directorio de la fotografía");

		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		LOGGER.info(
				"datosColegialesUpdate() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		LOGGER.info(
				"datosColegialesUpdate() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

		if (null != usuarios && usuarios.size() > 0) {
			AdmUsuarios usuario = usuarios.get(0);

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
						|| (!extension.trim().toUpperCase().equals(".JPG")
								&& !extension.trim().toUpperCase().equals(".GIF")
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
						LOGGER.debug(
								"uploadPhotography() -> Cierre del stream de la fotografía de la persona jurídica");
						stream.close();
					}

					// actualizar nombre de la fotografia en base de datos
					LOGGER.debug("uploadPhotography() -> actualizar nombre de la fotografia en base de datos");

					CenSolicmodifcambiarfoto solicitud = new CenSolicmodifcambiarfoto();
					solicitud.setFotografia(fileName);
					solicitud.setFechaalta(new Date());
					solicitud.setFechamodificacion(new Date());
					solicitud.setIdinstitucion(idInstitucion);
					solicitud.setIdpersona(Long.valueOf(idPersona));

//					List<ComboItem> autoAceptar = cenSolicitmodifdatosbasicosMapper
//							.getAutoAceptar(String.valueOf(idInstitucion));
//					

//					GenParametrosExample example = new GenParametrosExample();
//					example.createCriteria().andIdinstitucionEqualTo(idInstitucion).andParametroEqualTo("SOLICITUDES_MODIF_CENSO");
					List<GenParametros> autoAceptar = new ArrayList<GenParametros>();
					
					GenParametros param = new GenParametros();
					ParametroRequestDTO parametroRequestDTO = new ParametroRequestDTO();
					parametroRequestDTO.setIdInstitucion(String.valueOf(idInstitucion));
					parametroRequestDTO.setModulo("CEN");
					parametroRequestDTO.setParametrosGenerales("SOLICITUDES_MODIF_CENSO");
					StringDTO paramRequest = genParametrosMapper.getParameterFunction(0, parametroRequestDTO );
					param.setParametro("SOLICITUDES_MODIF_CENSO");
					param.setValor(paramRequest.getValor());
					autoAceptar.add(param);
					
//					if (config.size() > 0) {
//						result = config.get(0).getValor();
//					} else {
//						result = "0";
//					}
//					return result;
					
					if(autoAceptar.size() == 0) {
						GenParametros combo = new GenParametros();
						combo.setValor("N");
						autoAceptar.add(combo);
					}
					
					if (autoAceptar.size() > 0) {
						if (autoAceptar.get(0).getValor().equals("S")) {
							solicitud.setIdestadosolic(Short.parseShort("20"));
						} else {
							solicitud.setIdestadosolic(Short.parseShort("10"));
						}
					} else {
						solicitud.setIdestadosolic(Short.parseShort("10"));
					}
					solicitud.setMotivo(motivo);
					solicitud.setUsumodificacion(usuario.getIdusuario());
					LOGGER.info(
							"loadPhotography() / cenClienteMapper.updateByExample() -> Entrada a cenSolicmodifcambiarfoto actualizar el nombre de la fotografía de una persona jurídica");
					response = cenSolicmodifcambiarfoto.insert(solicitud);
					LOGGER.info(
							"loadPhotography() / cenClienteMapper.updateByExample() -> Salida de cenSolicmodifcambiarfoto actualizar el nombre de la fotografía de una persona jurídica");
					if (autoAceptar.size() > 0) {
						if (autoAceptar.get(0).getValor().equals("S")) {
							CenClienteExample cenClienteExample = new CenClienteExample();
							cenClienteExample.createCriteria().andIdpersonaEqualTo(Long.valueOf(idPersona))
									.andIdinstitucionEqualTo(idInstitucion);
							CenCliente cenCliente = new CenCliente();
							cenCliente.setFotografia(fileName);
							LOGGER.info(
									"loadPhotography() / cenClienteMapper.updateByExample() -> Entrada a cenClienteMapper actualizar el nombre de la fotografía de una persona jurídica");
							response = cenClienteMapper.updateByExampleSelective(cenCliente, cenClienteExample);

							error.setCode(200);
							error.setDescription(
									"Su petición ha sido aceptada automáticamente. Puede ver ya los datos actualizados");
						} else {
							GenParametrosExample ejemploParam = new GenParametrosExample();
							List<GenParametros> xDias = new ArrayList<GenParametros>();
							ejemploParam.createCriteria()
									.andParametroEqualTo("PLAZO_EN_DIAS_APROBACION_SOLICITUD_MODIFICACION")
									.andIdinstitucionEqualTo(idInstitucion);
							xDias = genParametrosMapper.selectByExample(ejemploParam);
							error.setCode(200);
							if (xDias.size() == 0) {
								GenParametrosExample ejemploParam2 = new GenParametrosExample();
								ejemploParam2.createCriteria()
										.andParametroEqualTo("PLAZO_EN_DIAS_APROBACION_SOLICITUD_MODIFICACION")
										.andIdinstitucionEqualTo((short) 2000);
								xDias = genParametrosMapper.selectByExample(ejemploParam2);
							}
							error.setDescription("Su petición ha sido registrada y será revisada en los próximos "
									+ xDias.get(0).getValor()
									+ " días. Puede comprobar el estado de su petición en el menú Solicitudes de modificación");
						}
					} else {
						GenParametrosExample ejemploParam = new GenParametrosExample();
						List<GenParametros> xDias = new ArrayList<GenParametros>();
						ejemploParam.createCriteria()
								.andParametroEqualTo("PLAZO_EN_DIAS_APROBACION_SOLICITUD_MODIFICACION")
								.andIdinstitucionEqualTo(idInstitucion);
						xDias = genParametrosMapper.selectByExample(ejemploParam);
						error.setCode(200);
						if (xDias.size() == 0) {
							GenParametrosExample ejemploParam2 = new GenParametrosExample();
							ejemploParam2.createCriteria()
									.andParametroEqualTo("PLAZO_EN_DIAS_APROBACION_SOLICITUD_MODIFICACION")
									.andIdinstitucionEqualTo((short) 2000);
							xDias = genParametrosMapper.selectByExample(ejemploParam2);
						}
						error.setDescription("Su petición ha sido registrada y será revisada en los próximos "
								+ xDias.get(0).getValor()
								+ " días. Puede comprobar el estado de su petición en el menú Solicitudes de modificación");

					}

					updateResponseDTO.setError(error);
					if (response == 1) {
						updateResponseDTO.setStatus(SigaConstants.OK);
						LOGGER.warn("loadPhotography() / cenClienteMapper.updateByExample() -> "
								+ updateResponseDTO.getStatus()
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
							+ updateResponseDTO.getStatus() + ".No existen ninguna persona con en idPersona:"
							+ idPersona + " indicado");
				}

			} else {
				updateResponseDTO.setStatus(SigaConstants.KO);
				LOGGER.warn("loadPhotography() / genPropertiesMapper.selectByExample() -> "
						+ updateResponseDTO.getStatus() + ".No se pudo obtener el directorio de la fotografía");
			}

			LOGGER.info(
					"uploadPhotography() -> Salida del servicio para guardar una fotografía de una persona jurídica");

		}

		return updateResponseDTO;

	}

	// @Override
	// public ComboDTO autoAceptar(NoColegiadoItem noColegiadoItem,
	// HttpServletRequest request) {
	// LOGGER.info(
	// "createColegiado() -> Entrada al servicio para actualizar información general
	// de una persona jurídica");
	// InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
	//
	// // Conseguimos información del usuario logeado
	// String token = request.getHeader("Authorization");
	// String dni = UserTokenUtils.getDniFromJWTToken(token);
	// Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
	// ComboItem autoAceptar =
	// cenSolicitmodifdatosbasicosMapper.getAutoAceptar(String.valueOf(idInstitucion),
	// noColegiadoItem.getIdPersona());
	// ComboDTO response = new ComboDTO();
	// List<ComboItem> combo = new ArrayList<ComboItem>();
	//
	// response.setCombooItems(combo);
	// return autoAceptar;
	// }

	@Override
	public ComboDTO autoAceptar(HttpServletRequest request) {

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

			LOGGER.info(
					"getTratamiento() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				List<GenParametros> autoAceptar = new ArrayList<GenParametros>();
				
				GenParametros param = new GenParametros();
				ParametroRequestDTO parametroRequestDTO = new ParametroRequestDTO();
				parametroRequestDTO.setIdInstitucion(String.valueOf(idInstitucion));
				parametroRequestDTO.setModulo("CEN");
				parametroRequestDTO.setParametrosGenerales("SOLICITUDES_MODIF_CENSO");
				StringDTO paramRequest = genParametrosMapper.getParameterFunction(0, parametroRequestDTO );
				param.setParametro("SOLICITUDES_MODIF_CENSO");
				param.setValor(paramRequest.getValor());
				autoAceptar.add(param);
				
				if(autoAceptar.size() == 0) {
					GenParametros combo = new GenParametros();
					combo.setValor("N");
					autoAceptar.add(combo);
					ComboItem result = new ComboItem();
					result.setValue(combo.getValor());
					comboItems.add(result);
					}else {
						ComboItem result = new ComboItem();
						result.setValue(param.getValor());
						comboItems.add(result);
				}
				
				comboItems = cenSolicitmodifdatosbasicosMapper.getAutoAceptar(String.valueOf(idInstitucion));

				comboDTO.setCombooItems(comboItems);

			}

		}
		LOGGER.info("getPais() -> Salida del servicio para obtener los tipos de tratamiento disponibles");

		return comboDTO;
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
		Error error = new Error();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"createColegiado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"createColegiado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				if (!insertResponseDTO.getStatus().equals(SigaConstants.KO)) {

					// Comprobamos si se debe generar la solicitud de modificacion de datos basicos
					// del lenguaje

					CenClienteKey key = new CenClienteKey();
					key.setIdinstitucion(idInstitucion);
					key.setIdpersona(Long.parseLong(noColegiadoItem.getIdPersona()));
					CenCliente cliente = cenClienteExtendsMapper.selectByPrimaryKey(key);
					if (!noColegiadoItem.getIdLenguaje().equals(cliente.getIdlenguaje())) {

						// 1 crear registro en tabla CEN_SOLICITMODIFDATOSBASICOS
						LOGGER.info(
								"createColegiado() / cenPersonaExtendsMapper.insertSelectiveForNewSociety() -> Entrada a cenPersonaExtendsMapper para crear una nueva persona");

						CenSolicitmodifdatosbasicos solicitud = new CenSolicitmodifdatosbasicos();

						NewIdDTO idBD = cenSolicitmodifdatosbasicosMapper
								.getMaxIdSolicitud(String.valueOf(idInstitucion), noColegiadoItem.getIdPersona());
						// if (idBD == null) {
						// solicitud.setIdsolicitud(Short.parseShort("1"));
						// } else {
						// int idCv = Integer.parseInt(idBD.getNewId());
						// solicitud.setIdsolicitud(Short.parseShort(""+ (1 + idCv)));
						// }

//						List<ComboItem> autoAceptar = cenSolicitmodifdatosbasicosMapper
//								.getAutoAceptar(String.valueOf(idInstitucion));

//						GenParametrosExample example = new GenParametrosExample();
//						example.createCriteria().andIdinstitucionEqualTo(idInstitucion).andParametroEqualTo("SOLICITUDES_MODIF_CENSO");
						List<GenParametros> autoAceptar = new ArrayList<GenParametros>();
						
						GenParametros param = new GenParametros();
						ParametroRequestDTO parametroRequestDTO = new ParametroRequestDTO();
						parametroRequestDTO.setIdInstitucion(String.valueOf(idInstitucion));
						parametroRequestDTO.setModulo("CEN");
						parametroRequestDTO.setParametrosGenerales("SOLICITUDES_MODIF_CENSO");
						StringDTO paramRequest = genParametrosMapper.getParameterFunction(0, parametroRequestDTO );
						param.setParametro("REGTEL");
						param.setValor(paramRequest.getValor());
						autoAceptar.add(param);
						
//						if (config.size() > 0) {
//							result = config.get(0).getValor();
//						} else {
//							result = "0";
//						}
//						return result;
						
						if(autoAceptar.size() == 0) {
							GenParametros combo = new GenParametros();
							combo.setValor("N");
							autoAceptar.add(combo);
						}
						
						int idCv = Integer.parseInt(idBD.getNewId());
						solicitud.setIdsolicitud(Short.parseShort("" + (1 + idCv)));
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
						solicitud.setFechaalta(new Date());
						if (autoAceptar.size() > 0) {
							if (autoAceptar.get(0).getValor().equals("S")) {
								solicitud.setIdestadosolic(Short.parseShort("20"));
							} else {
								solicitud.setIdestadosolic(Short.parseShort("10"));
							}
						} else {
							solicitud.setIdestadosolic(Short.parseShort("10"));
						}
						int responseInsertPersona = cenSolicitmodifdatosbasicosMapper.insert(solicitud);
						int responseUpdate = 0;

						if (autoAceptar.get(0).getValor().equals("S")) {
							CenCliente modificacion = new CenCliente();
							modificacion.setIdinstitucion(idInstitucion);
							modificacion.setIdlenguaje(solicitud.getIdlenguaje());
							modificacion.setPublicidad(solicitud.getPublicidad());
							modificacion.setGuiajudicial(solicitud.getGuiajudicial());
							modificacion.setIdpersona(solicitud.getIdpersona());
							modificacion.setFechaactualizacion(new Date());
							modificacion.setFechamodificacion(new Date());
							modificacion.setUsumodificacion(usuario.getIdusuario());

							responseUpdate = cenClienteExtendsMapper.updateByPrimaryKeySelective(modificacion);
							error.setCode(200);
							error.setDescription(
									"Su petición ha sido aceptada automáticamente. Puede ver ya los datos actualizados");
						} else {
							GenParametrosExample ejemploParam = new GenParametrosExample();
							List<GenParametros> xDias = new ArrayList<GenParametros>();
							ejemploParam.createCriteria()
									.andParametroEqualTo("PLAZO_EN_DIAS_APROBACION_SOLICITUD_MODIFICACION")
									.andIdinstitucionEqualTo(idInstitucion);
							xDias = genParametrosMapper.selectByExample(ejemploParam);
							error.setCode(200);
							if (xDias.size() == 0) {
								GenParametrosExample ejemploParam2 = new GenParametrosExample();
								ejemploParam2.createCriteria()
										.andParametroEqualTo("PLAZO_EN_DIAS_APROBACION_SOLICITUD_MODIFICACION")
										.andIdinstitucionEqualTo((short) 2000);
								xDias = genParametrosMapper.selectByExample(ejemploParam2);
							}
							error.setDescription("Su petición ha sido registrada y será revisada en los próximos "
									+ xDias.get(0).getValor()
									+ " días. Puede comprobar el estado de su petición en el menú Solicitudes de modificación");
						}

						insertResponseDTO.setError(error);
						LOGGER.info(
								"createColegiado() / cenSolicitmodifdatosbasicosMapper.insert() -> Salida de cenSolicitmodifdatosbasicosMapper para crear una nueva solicitud");
						if (responseInsertPersona == 1) {
							insertResponseDTO.setStatus(SigaConstants.OK);
							LOGGER.info("createColegiado() Solicitud creada correctamente");
							if (responseUpdate == 1) {
								LOGGER.info("createColegiado() Solicitud procesada correctamente");
							}

						} else {
							insertResponseDTO.setStatus(SigaConstants.KO);
							LOGGER.info("createColegiado() La solicitud no ha podido ser creada correctamente");
						}
					} else {
						insertResponseDTO.setStatus(SigaConstants.OK);
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
	public InsertResponseDTO createNoColegiado(NoColegiadoItem noColegiadoItem, HttpServletRequest request)
			throws ParseException {
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
		// List<CenGruposcliente> cenGruposcliente = new ArrayList<CenGruposcliente>();

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
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
					crearPersonaDTO
							.setIdtipoidentificacion(Short.parseShort(noColegiadoItem.getIdTipoIdentificacion()));
					crearPersonaDTO.setFechanacimiento(noColegiadoItem.getFechaNacimientoDate());
					if (noColegiadoItem.getSexo() != null) {
						crearPersonaDTO.setSexo(noColegiadoItem.getSexo());
					}
					if (noColegiadoItem.getNaturalDe() != null) {
						crearPersonaDTO.setNaturalde(noColegiadoItem.getNaturalDe());

					}
					if (noColegiadoItem.getidEstadoCivil() != null) {

						crearPersonaDTO.setIdestadocivil(Short.parseShort(noColegiadoItem.getidEstadoCivil()));
					}

					ultimoInsert = cenPersonaExtendsMapper.insertSelectiveForPerson(crearPersonaDTO, usuario);
					LOGGER.info(
							"createColegiado() / cenPersonaExtendsMapper.insertSelectiveForCreateLegalPerson() -> Salida de cenPersonaExtendsMapper para crear una nueva persona");
				} else {
					CenPersona persona = personas.get(0);
					persona.setApellidos1(noColegiadoItem.getApellidos1());
					persona.setApellidos2(noColegiadoItem.getApellidos2());
					persona.setNombre(noColegiadoItem.getNombre());
					persona.setIdtipoidentificacion(Short.parseShort(noColegiadoItem.getIdTipoIdentificacion()));
					persona.setFechanacimiento(noColegiadoItem.getFechaNacimientoDate());
					if (noColegiadoItem.getSexo() != null) {
						persona.setSexo(noColegiadoItem.getSexo());
					}
					if (noColegiadoItem.getNaturalDe() != null) {
						persona.setNaturalde(noColegiadoItem.getNaturalDe());

					}
					if (noColegiadoItem.getidEstadoCivil() != null) {

						persona.setIdestadocivil(Short.parseShort(noColegiadoItem.getidEstadoCivil()));
					}
					ultimoInsert = cenPersonaExtendsMapper.updateByPrimaryKey(persona);
					LOGGER.info(
							"createColegiado() / cenPersonaExtendsMapper.insertSelectiveForCreateLegalPerson() -> Salida de cenPersonaExtendsMapper para crear una nueva persona");

				}
				if (ultimoInsert == 1 || (null != personas && personas.size() > 0)) {
					List<ComboItem> comboItems = new ArrayList<ComboItem>();
					Long idPersona = Long.valueOf(0);
					if (null != personas && personas.size() > 0) {
						idPersona = personas.get(0).getIdpersona();
					} else {
						comboItems = cenPersonaExtendsMapper.selectMaxIdPersona(usuario.getIdinstitucion().toString());
						idPersona = Long.valueOf(comboItems.get(0).getValue());
					}
					noColegiadoItem.setIdPersona(String.valueOf(idPersona));
					CenClienteKey key = new CenClienteKey();
					key.setIdinstitucion(usuario.getIdinstitucion());
					key.setIdpersona(idPersona);
					// 2 crear registro en tabla CEN_CLIENTE
					CenCliente cliente = cenClienteMapper.selectByPrimaryKey(key);
					if (null == cliente) {

						CenCliente cenCliente = new CenCliente();

						// if (!comboItems.isEmpty()) {

						cenCliente.setIdpersona(idPersona);
						cenCliente.setIdinstitucion(usuario.getIdinstitucion());
						cenCliente.setFechaalta(new Date());
						cenCliente.setCaracter("P");
						cenCliente.setComisiones(noColegiadoItem.getComisiones());
						cenCliente.setFechamodificacion(new Date());
						cenCliente.setUsumodificacion(usuario.getIdusuario());
						cenCliente.setIdlenguaje(noColegiadoItem.getIdLenguaje());
						cenCliente.setExportarfoto(SigaConstants.DB_FALSE);
						cenCliente.setPublicidad(SigaConstants.DB_FALSE);
						cenCliente.setLetrado(SigaConstants.DB_FALSE);
						cenCliente.setNoenviarrevista(SigaConstants.DB_FALSE);
						cenCliente.setGuiajudicial(SigaConstants.DB_FALSE);
						cenCliente.setIdtratamiento(Short.parseShort(noColegiadoItem.getidTratamiento()));
						cenCliente.setAsientocontable(noColegiadoItem.getAsientoContable());
						cenCliente.setNoaparecerredabogacia(noColegiadoItem.getNoAparecerRedAbogacia());

						// }

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
						CenNocolegiado nocol = cenNocolegiadoMapper.selectByPrimaryKey(noColegiadokey);
						// 3 crear registro en tabla CEN_NOCOLEGIADO
						if (null == nocol) {

							LOGGER.info(
									"createColegiado() / cenColegiadoExtendsMapper.insertSelectiveForCreateNewSociety() -> Entrada a cenColegiadoExtendsMapper para crear un nuevo colegiado");

							EtiquetaUpdateDTO cenNocolegiado = new EtiquetaUpdateDTO();

							// Se pone a cero ya que al ser una persona física no tiene tipo ni sociedadsj
							cenNocolegiado.setTipo("1");
							cenNocolegiado.setIdPersona(idPersona.toString());
							cenNocolegiado.setAnotaciones(noColegiadoItem.getAnotaciones());
							int insertNoColegiado = cenNocolegiadoMapper.insertSelectiveForCreateLegalPerson(
									String.valueOf(idInstitucion), usuario, cenNocolegiado);

							if (insertNoColegiado == 1) {
								insertResponseDTO.setStatus(SigaConstants.OK);
								insertResponseDTO.setId(idPersona.toString());
								LOGGER.warn(
										"createColegiado() / cenNocolegiadoExtendsMapper.insertSelectiveForCreateLegalPerson() -> Se ha insertado correctamente la persona en CEN_NOCOLEGIADO");
							}
							LOGGER.info(
									"createColegiado() / cenColegiadoExtendsMapper.insertSelectiveForCreateLegalPerson() -> Salida de cenColegiadoExtendsMapper para crear un nuevo colegiado");

							// 2. crear/actualizar relaciones entre tablas para todos los grupos
							if (!insertResponseDTO.getStatus().equals(SigaConstants.KO)) {

								if (null != noColegiadoItem.getEtiquetas()) {
									for (ComboEtiquetasItem etiqueta : noColegiadoItem.getEtiquetas()) {

										if (etiqueta.getIdGrupo() != "") {
											LOGGER.info(
													"createLegalPerson() / cenGruposclienteExtendsMapper.createLegalPerson() -> Entrada a cenGruposclienteExtendsMapper para obtener grupos de clientes");
											cenGruposcliente = cenGruposclienteExtendsMapper
													.selectDistinctGruposClientes(String.valueOf(idInstitucion),
															usuario.getIdlenguaje(), etiqueta.getIdGrupo());
											LOGGER.info(
													"createLegalPerson() / cenGruposclienteExtendsMapper.createLegalPerson() -> Salida de cenGruposclienteExtendsMapper para obtener grupos de clientes");

											// 2.1 existen registros => solo insertar/actualizar fecha_baja en tabla
											// CEN_GRUPOSCLIENTE_CLIENTE
											if (null != cenGruposcliente && cenGruposcliente.size() > 0) {

												List<CenGruposclienteCliente> listarelacionGrupoPersona = new ArrayList<CenGruposclienteCliente>();
												CenGruposclienteClienteExample relacionGrupoPersona = new CenGruposclienteClienteExample();
												relacionGrupoPersona.createCriteria()
														.andIdpersonaEqualTo(
																Long.valueOf(noColegiadoItem.getIdPersona()))
														.andIdgrupoEqualTo(Short.valueOf(etiqueta.getIdGrupo()))
														.andIdinstitucionEqualTo(idInstitucion)
														.andIdinstitucionGrupoEqualTo(
																Short.valueOf(etiqueta.getIdInstitucion()));
												listarelacionGrupoPersona = cenGruposclienteClienteExtendsMapper
														.selectByExample(relacionGrupoPersona);

												// 2.1.1. Si no existe registro en CEN_GRUPOSCLIENTE_CLIENTE se inserta
												if (listarelacionGrupoPersona.isEmpty()) {

													int response = 0;
													SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy",
															Locale.getDefault());
													Date date = new Date();
													String fecha = dateFormat.format(date);
													etiqueta.setFechaInicio(fecha);
													LOGGER.info(
															"createLegalPerson() / cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> Entrada a cenGruposclienteClienteExtendsMapper para crear relacion grupo-persona jurídica");
													// if (cenGruposcliente != null && cenGruposcliente.size() > 0) {
													response = cenGruposclienteClienteExtendsMapper
															.insertSelectiveForUpdateLegalPerson(etiqueta,
																	noColegiadoItem.getIdPersona(),
																	etiqueta.getIdInstitucion(),
																	String.valueOf(idInstitucion),
																	String.valueOf(usuario.getIdusuario()));
													// } else {
													// response = cenGruposclienteClienteExtendsMapper
													// .insertSelectiveForUpdateLegalPerson(etiqueta,
													// noColegiadoItem.getIdPersona(),
													// String.valueOf(SigaConstants.IDINSTITUCION_2000),
													// String.valueOf(idInstitucion),
													// String.valueOf(usuario.getIdusuario()));
													// }

													LOGGER.info(
															"createLegalPerson() / cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> Salida a cenGruposclienteClienteExtendsMapper para crear relacion grupo-persona jurídica");

													if (response == 0) {
														insertResponseDTO.setStatus(SigaConstants.KO);
														LOGGER.warn(
																"createLegalPerson() / cenGruposclienteClienteExtendsMapper.insertSelectiveForCreateLegalPerson() -> No se pudo insertar el grupo = "
																		+ etiqueta.getIdGrupo()
																		+ " en tabla CEN_GRUPOSCLIENTE_CLIENTE");
													} else {
														gruposPerJuridicaNuevos.add(etiqueta.getIdGrupo());
													}
												}

											}

										}
									}
								}

								// Temas

								if (noColegiadoItem.getTemasCombo() != null
										&& noColegiadoItem.getTemasCombo().size() > 0) {

									// Añadimos temas
									for (ComboItem tema : noColegiadoItem.getTemasCombo()) {

										// Para cada temas comprobamos si ya existe la relacion
										ForTemacursoPersonaExample forTemacursoExample = new ForTemacursoPersonaExample();
										forTemacursoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
												.andIdpersonaEqualTo(Long.parseLong(noColegiadoItem.getIdPersona()))
												.andIdtemacursoEqualTo(Long.valueOf(tema.getValue()));

										LOGGER.info(
												"updateColegiado() / forTemaCursoPersonaExtendsMapper.selectByExample(forTemacursoCursoExample) -> Entrada a forTemaCursoPersonaExtendsMapper para buscar los temas registrados de una persona");

										List<ForTemacursoPersona> forTemacursoCursoList = forTemaCursoPersonaExtendsMapper
												.selectByExample(forTemacursoExample);

										LOGGER.info(
												"updateColegiado() / forTemaCursoPersonaExtendsMapper.selectByExample(forTemacursoCursoExample) -> Salida a forTemaCursoPersonaExtendsMapper para buscar los temas registrados de una persona");

										// Si no existe la creamos
										if (forTemacursoCursoList.isEmpty()) {

											ForTemacursoPersona forTemacursoCurso = new ForTemacursoPersona();
											forTemacursoCurso.setFechabaja(null);
											forTemacursoCurso.setFechamodificacion(new Date());
											forTemacursoCurso.setIdinstitucion(idInstitucion);
											forTemacursoCurso
													.setIdpersona(Long.parseLong(noColegiadoItem.getIdPersona()));
											forTemacursoCurso.setUsumodificacion(usuario.getIdusuario().longValue());
											forTemacursoCurso.setIdtemacurso(Long.valueOf(tema.getValue()));

											LOGGER.info(
													"updateColegiado() / forTemaCursoPersonaExtendsMapper.insert(forTipoServicioCurso) -> Entrada a forTemaCursoPersonaExtendsMapper para insertar un tema de una persona");

											forTemaCursoPersonaExtendsMapper.insert(forTemacursoCurso);

											LOGGER.info(
													"updateColegiado() / forTemaCursoPersonaExtendsMapper.insert(forTipoServicioCurso) -> Salida a forTemaCursoPersonaExtendsMapper para insertar un tema de una persona");

											// Si existe
										} else {
											// Comprobamos que si fecha de baja esta a null, si no esta la modificamos
											if (forTemacursoCursoList.get(0).getFechabaja() != null) {
												ForTemacursoPersona forTemaCurso = forTemacursoCursoList.get(0);
												forTemaCurso.setFechabaja(null);
												forTemaCurso.setUsumodificacion(usuario.getIdusuario().longValue());
												forTemaCurso.setFechamodificacion(new Date());

												LOGGER.info(
														"updateColegiado() / forTemaCursoPersonaExtendsMapper.updateByPrimaryKey(forTemaCurso) -> Entrada a forTemaCursoPersonaExtendsMapper para dar de alta a un tema de una persona");

												forTemaCursoPersonaExtendsMapper.updateByPrimaryKey(forTemaCurso);

												LOGGER.info(
														"updateColegiado() / forTemaCursoPersonaExtendsMapper.updateByPrimaryKey(forTemaCurso) -> Salida a forTemaCursoPersonaExtendsMapper para dar de alta a un tema de una persona");
											}
										}
									}

									// Comprobamos si existe algun tema para el curso y les damos de baja
								}

								//Creamos la dirección si ya la tuviera.
								if (null != noColegiadoItem.getIdPoblacion() || null != noColegiadoItem.getIdProvincia()) {
									Long idDireccion = insertarDatosDireccion(noColegiadoItem, usuario, idPersona);
								} 
							} else {
								insertResponseDTO.setStatus(SigaConstants.KO);
								LOGGER.warn(
										"createColegiado() / cenNocolegiadoExtendsMapper.insertSelectiveForCreateLegalPerson() -> No se ha podido crear la persona no colegiada en tabla CEN_NOCOLEGIADO");
							}
						} else {
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

	private Long insertarDatosDireccion(NoColegiadoItem noColegiadoItem, AdmUsuarios usuario, Long idPersona) {
		
		CenDirecciones direccion = new CenDirecciones();
		
		//Creamos la direccion con los tipos de direccion oblogatorios
		 List<DatosDireccionesItem> direccionID = _cenDireccionesMapper.selectNewIdDireccion(idPersona.toString(),usuario.getIdinstitucion().toString());
		
		direccion.setIddireccion(Long.valueOf(direccionID.get(0).getIdDireccion()));
		direccion.setCodigopostal(noColegiadoItem.getCodigoPostal());
		direccion.setCorreoelectronico(noColegiadoItem.getCorreoElectronico());
		direccion.setDomicilio(noColegiadoItem.getDomicilio());
		if(noColegiadoItem.getFax1() != null)direccion.setFax1(noColegiadoItem.getFax1());
		if(noColegiadoItem.getFax2() != null)direccion.setFax1(noColegiadoItem.getFax2());
		direccion.setFechamodificacion(new Date());
		direccion.setIdinstitucion(usuario.getIdinstitucion());
		direccion.setIdpersona(idPersona);
		if(noColegiadoItem.getIdPoblacion()!= null)direccion.setIdpoblacion(noColegiadoItem.getIdPoblacion());
		if(noColegiadoItem.getIdProvincia()!= null)direccion.setIdprovincia(noColegiadoItem.getIdProvincia());
		if(noColegiadoItem.getTelefono1()!= null)direccion.setTelefono1(noColegiadoItem.getTelefono1());
		if(noColegiadoItem.getTelefono2()!= null)direccion.setTelefono1(noColegiadoItem.getTelefono2());
		direccion.setMovil(noColegiadoItem.getMovil());
		direccion.setUsumodificacion(usuario.getIdusuario());
		
		if(noColegiadoItem.getIdPais()!= null){
			direccion.setIdpais(noColegiadoItem.getIdPais());
		}else{
			//Ponemos por defecto España
			direccion.setIdpais("191");
		}
			
			
		//Añadimos las preferencias de direccion obligatorias
		//direccion.setPreferente("ECS");
		
		_cenDireccionesMapper.insert(direccion);
		
		CenDireccionTipodireccion tipoDireccion =  new CenDireccionTipodireccion();
		tipoDireccion.setFechamodificacion(new Date());
		tipoDireccion.setIddireccion(direccion.getIddireccion());
		tipoDireccion.setIdinstitucion(usuario.getIdinstitucion());
		tipoDireccion.setIdpersona(idPersona);
		tipoDireccion.setUsumodificacion(usuario.getIdusuario());
		
		//Añadimos los tipo de direcciones obligatorios
//		tipoDireccion.setIdtipodireccion(Short.valueOf(SigaConstants.TIPO_DIR_TRASPASO));
//		cenDireccionTipoDireccionMapper.insert(tipoDireccion );
//		tipoDireccion.setIdtipodireccion(Short.valueOf(SigaConstants.TIPO_DIR_FACTURACION));
//		cenDireccionTipoDireccionMapper.insert(tipoDireccion );
		tipoDireccion.setIdtipodireccion(Short.valueOf(SigaConstants.TIPO_DIR_CENSOWEB));
		cenDireccionTipoDireccionMapper.insert(tipoDireccion );
//		tipoDireccion.setIdtipodireccion(Short.valueOf(SigaConstants.TIPO_DIR_GUIAJUDICIAL));
//		cenDireccionTipoDireccionMapper.insert(tipoDireccion );
//		tipoDireccion.setIdtipodireccion(Short.valueOf(SigaConstants.TIPO_DIR_GUARDIA));
//		cenDireccionTipoDireccionMapper.insert(tipoDireccion );
//		tipoDireccion.setIdtipodireccion(Short.valueOf(SigaConstants.TIPO_DIR_DESPACHO));
//		cenDireccionTipoDireccionMapper.insert(tipoDireccion );
//		
//		tipoDireccion.setIdtipodireccion(Short.valueOf(SigaConstants.TIPO_DIR_PREFERENTE_EMAIL));
//		cenDireccionTipoDireccionMapper.insert(tipoDireccion );
//		tipoDireccion.setIdtipodireccion(Short.valueOf(SigaConstants.TIPO_DIR_PREFERENTE_CORREO));
//		cenDireccionTipoDireccionMapper.insert(tipoDireccion );
//		tipoDireccion.setIdtipodireccion(Short.valueOf(SigaConstants.TIPO_DIR_PREFERENTE_SMS));
//		cenDireccionTipoDireccionMapper.insert(tipoDireccion );
		
		return direccion.getIddireccion();
		
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

	// @Override
	// public ComboDTO getLabel(HttpServletRequest request) {
	// // TODO Auto-generated method stub
	// return null;
	// }

	@Override
	public ComboDTO getTopicsSpecificPerson(HttpServletRequest request, String idPersona) {
		LOGGER.info("getTopicsSpecificPerson() -> Entrada al servicio para obtener los temas según la persona");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String dni = UserTokenUtils.getDniFromJWTToken(token);

		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

		if (null != usuarios && usuarios.size() > 0) {
			AdmUsuarios usuario = usuarios.get(0);
			if (null != idInstitucion) {

				LOGGER.info(
						"getTopicsSpecificPerson() / forTemaCursoPersonaExtendsMapper.getTopicsSpecificPerson -> Entrada a forTemaCursoPersonaExtendsMapper para obtener los temas según la persona");
				if(idPersona != null && !idPersona.equals("undefined")) {
					comboItems = forTemaCursoPersonaExtendsMapper.getTopicsSpecificPerson(idInstitucion.toString(), idPersona, usuario.getIdlenguaje());
				}
				
				LOGGER.info(
						"getTopicsSpecificPerson() / forTemaCursoPersonaExtendsMapper.getTopicsSpecificPerson -> Salida de forTemaCursoPersonaExtendsMapper para obtener los temas según la persona");

			}
		}
		comboDTO.setCombooItems(comboItems);

		LOGGER.info("getTopicsSpecificPerson() -> Salida del servicio para obtener los temas según la persona");

		return comboDTO;
	}
	
	/**
	 * PL que realiza una revision de letrado
	 * 
	 * @param idInstitucion
	 * @param idPersona
	 * @param usuario
	 * @return
	 * @throws ClsExceptions
	 */
	private String[] ejecutarPL_RevisionSuscripcionesLetrado(String idInstitucion, String idPersona, String fecha,
			String usuario) throws Exception {
		LOGGER.info("Entrada Ejecución PL Revision SuscripcionesLetrado");
		Object[] paramIn = new Object[4]; // Parametros de entrada del PL
		String resultado[] = new String[2]; // Parametros de salida del PL

		try {
			// Parametros de entrada del PL
			paramIn[0] = idInstitucion;
			paramIn[1] = idPersona;
			paramIn[2] = fecha;
			paramIn[3] = usuario;

			// Ejecucion del PL
			resultado = callPLProcedure("{call PKG_SERVICIOS_AUTOMATICOS.PROCESO_REVISION_LETRADO (?,?,?,?,?,?)}", 2,
					paramIn);

		} catch (Exception e) {
			LOGGER.info("Error Ejecución PL Revision SuscripcionesLetrado: " + e.getMessage());
			resultado[0] = "1"; // P_NUMREGISTRO
			resultado[1] = "ERROR"; // ERROR P_DATOSERROR
		}
		LOGGER.info("Salida OK Ejecución PL Revision SuscripcionesLetrado");
		return resultado;
	}
	
	/**
	 * Calls a PL Funtion
	 * 
	 * @author CSD
	 * @param functionDefinition
	 *            string that defines the function
	 * @param inParameters
	 *            input parameters
	 * @param outParameters
	 *            number of output parameters
	 * @return error code, '0' if ok
	 * @throws NamingException
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClsExceptions
	 *             type Exception
	 */
	private String[] callPLProcedure(String functionDefinition, int outParameters, Object[] inParameters)
			throws IOException, NamingException, SQLException {
		String result[] = null;

		if (outParameters > 0)
			result = new String[outParameters];
		DataSource ds = getOracleDataSource();
		Connection con = ds.getConnection();
		try {
			CallableStatement cs = con.prepareCall(functionDefinition);
			int size = inParameters.length;

			// input Parameters
			for (int i = 0; i < size; i++) {

				cs.setString(i + 1, (String) inParameters[i]);
			}
			// output Parameters
			for (int i = 0; i < outParameters; i++) {
				cs.registerOutParameter(i + size + 1, Types.VARCHAR);
			}

			for (int intento = 1; intento <= 2; intento++) {
				try {
					cs.execute();
					break;

				} catch (SQLTimeoutException tex) {
					throw tex;

				} catch (SQLException ex) {
					if (ex.getErrorCode() != 4068 || intento == 2) { // JPT: 4068 es un error de descompilado (la
																		// segunda vez deberia funcionar)
						throw ex;
					}
				}

			}

			for (int i = 0; i < outParameters; i++) {
				result[i] = cs.getString(i + size + 1);
			}
			cs.close();
			return result;

		} catch (SQLTimeoutException ex) {
			return null;
		} catch (SQLException ex) {
			return null;
		} catch (Exception e) {
			return null;
		} finally {
			con.close();
			con = null;
		}
	}

	/**
	 * Recupera el datasource con los datos de conexión sacados del fichero de
	 * configuracion
	 * 
	 * @return
	 * @throws IOException
	 * @throws NamingException
	 */
	private DataSource getOracleDataSource() throws IOException, NamingException {
		try {

			LOGGER.debug("Recuperando datasource {} provisto por el servidor (JNDI)");

			AdmConfigExample example = new AdmConfigExample();
			example.createCriteria().andClaveEqualTo("spring.datasource.jndi-name");
			List<AdmConfig> config = admConfigMapper.selectByExample(example);
			Context ctx = new InitialContext();
			return (DataSource) ctx.lookup(config.get(0).getValor());
		} catch (NamingException e) {
			throw e;
		}
	}

	@Override
	public StringDTO getTipoIdentificacion(StringDTO nifcif,HttpServletRequest request) {
		LOGGER.info(
				"verifyPerson() -> Entrada al servicio para verificar si la persona logueada está en la tabla cen_colegiado");

		StringDTO stringDTO = new StringDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		
		String dni = "";
		
		if(nifcif.getValor() == "") {
			dni = UserTokenUtils.getDniFromJWTToken(token);
		}else {
			dni = nifcif.getValor();
		}
		
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			CenPersonaExample cenPersonaExample = new CenPersonaExample();
			cenPersonaExample.createCriteria().andNifcifEqualTo(dni);
			List<CenPersona> cenPersona = cenPersonaExtendsMapper.selectByExample(cenPersonaExample);

			if (!cenPersona.isEmpty()) {
					if (null != cenPersona.get(0).getIdtipoidentificacion()) {
						stringDTO.setValor(cenPersona.get(0).getIdtipoidentificacion().toString());
					}
			}
		}

		LOGGER.info(
				"verifyPerson() -> Salida al servicio para verificar si la persona logueada está en la tabla cen_colegiado");
		return stringDTO;
	}
	
	private CenPersona mapCenPersona(CenPersona persona) {
		CenPersona nuevaPersona = new CenPersona();
		
		if (persona.getApellidos1() != null) {
			nuevaPersona.setApellidos1(persona.getApellidos1());
		}
		
		if (persona.getApellidos2() != null) {
			nuevaPersona.setApellidos2(persona.getApellidos2());
		}
		
		if (persona.getFallecido() != null) {
			nuevaPersona.setFallecido(persona.getFallecido());
		}
		
		if (persona.getFechamodificacion() != null) {
			nuevaPersona.setFechamodificacion(persona.getFechamodificacion());
		}
		
		if (persona.getFechanacimiento() != null) {
			nuevaPersona.setFechanacimiento(persona.getFechanacimiento());
		}
		
		if (persona.getIdestadocivil() != null) {
			nuevaPersona.setIdestadocivil(persona.getIdestadocivil());
		}
		
		if (persona.getIdpersona() != null) {
			nuevaPersona.setIdpersona(persona.getIdpersona());
		}
		
		if (persona.getIdtipoidentificacion() != null) {
			nuevaPersona.setIdtipoidentificacion(persona.getIdtipoidentificacion());
		}
		
		if (persona.getNaturalde() != null) {
			nuevaPersona.setNaturalde(persona.getNaturalde());
		}
		
		if (persona.getNifcif() != null) {
			nuevaPersona.setNifcif(persona.getNifcif());
		}
		
		if (persona.getNombre() != null) {
			nuevaPersona.setNombre(persona.getNombre());
		}
		
		if (persona.getSexo() != null) {
			nuevaPersona.setSexo(persona.getSexo());
		}
		
		if (persona.getUsumodificacion() != null) {
			nuevaPersona.setUsumodificacion(persona.getUsumodificacion());
		}
		
		return nuevaPersona;
	}
}
