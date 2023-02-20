package org.itcgae.siga.scs.services.impl.maestros;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.AcreditacionDTO;
import org.itcgae.siga.DTOs.scs.AcreditacionItem;
import org.itcgae.siga.DTOs.scs.ModulosDTO;
import org.itcgae.siga.DTOs.scs.ModulosItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ExpExpediente;
import org.itcgae.siga.db.entities.ExpExpedienteExample;
import org.itcgae.siga.db.entities.PcajgProcedScsproced;
import org.itcgae.siga.db.entities.PcajgProcedScsprocedExample;
import org.itcgae.siga.db.entities.ScsAcreditacionprocedimiento;
import org.itcgae.siga.db.entities.ScsAcreditacionprocedimientoExample;
import org.itcgae.siga.db.entities.ScsAcreditacionprocedimientoKey;
import org.itcgae.siga.db.entities.ScsActuaciondesigna;
import org.itcgae.siga.db.entities.ScsActuaciondesignaExample;
import org.itcgae.siga.db.entities.ScsActuaciondesignaKey;
import org.itcgae.siga.db.entities.ScsDesigna;
import org.itcgae.siga.db.entities.ScsDesignaExample;
import org.itcgae.siga.db.entities.ScsJuzgadoprocedimiento;
import org.itcgae.siga.db.entities.ScsJuzgadoprocedimientoExample;
import org.itcgae.siga.db.entities.ScsJuzgadoprocedimientoKey;
import org.itcgae.siga.db.entities.ScsPretensionesproced;
import org.itcgae.siga.db.entities.ScsPretensionesprocedExample;
import org.itcgae.siga.db.entities.ScsPretensionesprocedKey;
import org.itcgae.siga.db.entities.ScsProcedimientos;
import org.itcgae.siga.db.entities.ScsProcedimientosExample;
import org.itcgae.siga.db.mappers.ExpExpedienteMapper;
import org.itcgae.siga.db.mappers.PcajgProcedScsprocedMapper;
import org.itcgae.siga.db.mappers.ScsAcreditacionprocedimientoMapper;
import org.itcgae.siga.db.mappers.ScsPretensionesprocedMapper;
import org.itcgae.siga.db.mappers.ScsActuaciondesignaMapper;
import org.itcgae.siga.db.mappers.ScsDesignaMapper;
import org.itcgae.siga.db.mappers.ScsJuzgadoprocedimientoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsAcreditacionExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsPretensionesProcedExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsProcedimientosExtendsMapper;
import org.itcgae.siga.scs.services.maestros.IModulosYBasesService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FichaModulosYBasesServiceImpl implements IModulosYBasesService {

	private Logger LOGGER = Logger.getLogger(FichaModulosYBasesServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsProcedimientosExtendsMapper scsProcedimientosExtendsMapper;

	@Autowired 
	private ScsAcreditacionExtendsMapper scsAcreditacionExtendsMapper;

	@Autowired 
	private ScsPretensionesProcedExtendsMapper scsPretensionesprocedMapper;
	
	@Autowired 
	private ScsPretensionesprocedMapper scsPretensionesprocediMapper;
	
	@Autowired 
	private ScsAcreditacionprocedimientoMapper scsAcreditacionProcedimientoMapper;

	@Autowired 
	private ScsActuaciondesignaMapper scsActuaciondesignaMapper;
	
	@Autowired 
	private ScsJuzgadoprocedimientoMapper scsJuzgadoprocedimientoMapper;
	
	@Autowired 
	private ScsDesignaMapper scsDesignaMapper;
	
	@Autowired 
	private ExpExpedienteMapper expExpedienteMapper;
	
	@Autowired 
	private PcajgProcedScsprocedMapper pcajgProcedScsprocedMapper;
	
	@Override
	public ComboDTO getProcedimientos(HttpServletRequest request, String idProcedimiento) {
		LOGGER.info("getProcedimientos() -> Entrada al servicio para obtener combo jurisdicciones");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getProcedimientos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getProcedimientos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				
				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"getProcedimientos() / cenScsJurisdiccionExtendsMapper.getJurisdicciones() -> Entrada a cenJurisdiccionesExtendsMapper para obtener los jurisdicciones");
	
				List<ComboItem> comboItems = scsProcedimientosExtendsMapper.getProcedimientos(idInstitucion.toString(), idProcedimiento, usuario.getIdlenguaje());

				// Eliminamos los valores nulos de la lista
				while (comboItems.remove(null));
				
				LOGGER.info(
						"getProcedimientos() / cenScsJurisdiccionExtendsMapper.getJurisdicciones() -> Salida a cenJurisdiccionesExtendsMapper para obtener los jurisdicciones");

				combo.setCombooItems(comboItems);
			}

		}
		LOGGER.info("getProcedimientos() -> Salida del servicio para obtener combo jurisdicciones");
		return combo;
	}
	
	@Override
	public ModulosDTO searchModules(ModulosItem modulosItem, HttpServletRequest request) {
		LOGGER.info("searchSubzonas() -> Entrada al servicio para obtener modulos");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ModulosDTO modulosDTO = new ModulosDTO();
		List<ModulosItem> modulosItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchModules() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchModules() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchModules() / scsProcedimientosMapper.selectByExample() -> Entrada a scsProcedimientosMapper para obtener los modulos");
				modulosItem.setidInstitucion(idInstitucion.toString());
				
				modulosItem.setNombre(UtilidadesString.tratamientoApostrofes(modulosItem.getNombre()));
				modulosItems = scsProcedimientosExtendsMapper.searchModulo(modulosItem, usuarios.get(0).getIdlenguaje());

				LOGGER.info(
						"searchModules() / scsProcedimientosMapper.selectByExample() -> Salida a scsProcedimientosMapper para obtener los modulos");


				if (modulosItems != null) {
					modulosDTO.setModulosItem(modulosItems);
				}
			}

		}
		LOGGER.info("searchSubzonas() -> Salida del servicio para obtener modulos");
		return modulosDTO;
	}
	
	
	@Override
	public UpdateResponseDTO updateModules(ModulosItem modulosItem, HttpServletRequest request) {
		LOGGER.info("updateModules() ->  Entrada al servicio para actualizar modulos");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateModules() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateModules() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				List<ScsProcedimientos> codigoExistente = null;
				try {
					
					boolean continua = true;
					
					ScsProcedimientosExample ejemplo = new ScsProcedimientosExample();
					ejemplo.createCriteria().andIdinstitucionEqualTo(idInstitucion).andNombreEqualTo(modulosItem.getNombre())
					.andIdprocedimientoNotEqualTo(modulosItem.getIdProcedimiento());

					List<ScsProcedimientos> modulosExistentes = scsProcedimientosExtendsMapper.selectByExample(ejemplo);

					if(modulosItem.getCodigo() != null && modulosItem.getCodigo() != "") {
					ScsProcedimientosExample ejemplo2 = new ScsProcedimientosExample();
					ejemplo2.createCriteria().andIdinstitucionEqualTo(idInstitucion).andCodigoEqualTo(modulosItem.getCodigo())
					.andIdprocedimientoNotEqualTo(modulosItem.getIdProcedimiento());

					codigoExistente = scsProcedimientosExtendsMapper.selectByExample(ejemplo2);
					}
					
					if(((modulosExistentes != null && modulosExistentes.size() > 0 ))) {
						for(int i = 0; i < modulosExistentes.size() ; i++) {
							if(!(modulosExistentes.get(i).getIdprocedimiento().toString().equals(modulosItem.getIdProcedimiento().toString()))) {
								response = 0;
								error.setCode(400);
								error.setDescription("menu.justiciaGratuita.maestros.nombreCodigoExistente");
								continua = false;
							}
						}
					}
					
					if(codigoExistente != null && codigoExistente.size() > 0) {
						for(int i = 0; i < codigoExistente.size() ; i++) {
							if(!(codigoExistente.get(i).getIdprocedimiento().toString().equals(modulosItem.getIdProcedimiento().toString()))) {
								response = 0;
								error.setCode(400);
								error.setDescription("menu.justiciaGratuita.maestros.nombreCodigoExistente");
								continua = false;
							}
						}
						
					}
						
					if(continua) {
						ScsProcedimientos modulo = new ScsProcedimientos();
						modulo.setIdprocedimiento(modulosItem.getIdProcedimiento());
						modulo.setIdinstitucion(idInstitucion);

						modulo = scsProcedimientosExtendsMapper.selectByPrimaryKey(modulo);
						BigDecimal precio = new BigDecimal(modulosItem.getImporte());
						modulo.setNombre(modulosItem.getNombre());
						modulo.setCodigo(modulosItem.getCodigo());
						modulo.setCodigoext(modulosItem.getCodigoext());
						modulo.setPrecio(precio);
						modulo.setComplemento(modulosItem.getComplemento());
						modulo.setFechadesdevigor(modulosItem.getFechadesdevigor());
						modulo.setFechahastavigor(modulosItem.getFechahastavigor());
						modulo.setIdjurisdiccion(Short.parseShort(modulosItem.getIdjurisdiccion()));
						modulo.setPermitiraniadirletrado(modulosItem.getPermitiraniadirletrado());
						modulo.setObservaciones(modulosItem.getObservaciones());
						modulo.setFechamodificacion(new Date());
						modulo.setUsumodificacion(usuarios.get(0).getIdusuario());
						
						LOGGER.info(
								"updateModules() / scsProcedimientosExtendsMapper.updateByExample() -> Entrada a scsProcedimientosExtendsMapper para actualizar el modulo");

						response = scsProcedimientosExtendsMapper.updateByPrimaryKey(modulo);

						LOGGER.info(
								"updateModules() / scsProcedimientosExtendsMapper.updateByExample() -> Salida de scsProcedimientosExtendsMapper para actualizar el modulo");
						
//						Combo multiselect de Procedimientos
						if(modulosItem.getProcedimientos() != null && modulosItem.getProcedimientos().length()>0) {
							ScsPretensionesprocedExample pretenprocedExample = new ScsPretensionesprocedExample();
							pretenprocedExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdprocedimientoEqualTo(modulosItem.getIdProcedimiento());
							List<ScsPretensionesproced> pretensionesAnteriores = scsPretensionesprocedMapper.selectByExample(pretenprocedExample);
							
							for(int i = 0; i < pretensionesAnteriores.size() ; i++) {
								scsPretensionesprocedMapper.deleteByPrimaryKey(pretensionesAnteriores.get(i));
							}
							
							String[] lista = modulosItem.getProcedimientos().split(",");
							
							for(int i = 0; i < lista.length ; i++) {
								ScsPretensionesproced pretension = new ScsPretensionesproced();
								pretension.setIdpretension(Short.parseShort(lista[i]));
								pretension.setFechamodificacion(new Date());
								pretension.setUsumodificacion(usuarios.get(0).getIdusuario());
								pretension.setIdprocedimiento(modulosItem.getIdProcedimiento());
								pretension.setIdinstitucion(idInstitucion);
								LOGGER.info(
										"updateModules() / scsProcedimientosExtendsMapper.updateByExample() -> Entrada a scsProcedimientosExtendsMapper para actualizar los procedimientos");
								scsPretensionesprocedMapper.insert(pretension);
								LOGGER.info(
										"updateModules() / scsProcedimientosExtendsMapper.updateByExample() -> Salida de scsProcedimientosExtendsMapper para actualizar los procedimientos");
							}
						
							
						}else {
//							ELIMINAR TODOS
							ScsPretensionesprocedExample pretenprocedExample = new ScsPretensionesprocedExample();
							pretenprocedExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdprocedimientoEqualTo(modulosItem.getIdProcedimiento());
							List<ScsPretensionesproced> pretensionesAnteriores = scsPretensionesprocedMapper.selectByExample(pretenprocedExample);
							
							for(int i = 0; i < pretensionesAnteriores.size() ; i++) {
								scsPretensionesprocedMapper.deleteByPrimaryKey(pretensionesAnteriores.get(i));
							}
						}
						
					}
					
				} catch (Exception e) {
					LOGGER.error(e);
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}
		}

		if (response == 0) {
			error.setCode(400);
			if(error.getDescription() == null) {
							error.setDescription("areasmaterias.materias.ficha.actualizarerror");
			}
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("updateModules() -> Salida del servicio para actualizar modulos");

		return updateResponseDTO;
	}
	

	@Override
	public InsertResponseDTO createModules(ModulosItem modulosItem, HttpServletRequest request) {
		LOGGER.info("createModules() ->  Entrada al servicio para insertar modulos");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;
		Integer idProcedimientoNuevo = 0;
		
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"createModules() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"createModules() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
		

			if (null != usuarios && usuarios.size() > 0) {
				List<ScsProcedimientos> codigoExistente = null;
				try {
						ScsProcedimientosExample ejemplo = new ScsProcedimientosExample();
						ejemplo.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andNombreEqualTo(modulosItem.getNombre());
						List<ScsProcedimientos> modulosExistentes = scsProcedimientosExtendsMapper.selectByExample(ejemplo);

						if(modulosItem.getCodigo() != null && modulosItem.getCodigo() != "") {
							ScsProcedimientosExample ejemplo2 = new ScsProcedimientosExample();
							ejemplo2.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andCodigoEqualTo(modulosItem.getCodigo());
							codigoExistente = scsProcedimientosExtendsMapper.selectByExample(ejemplo2);
						}
						
						
						if((modulosExistentes != null && modulosExistentes.size() > 0)|| (codigoExistente != null && codigoExistente.size() > 0 ) ) {
							response = 0;
							error.setCode(400);
							error.setDescription("menu.justiciaGratuita.maestros.nombreCodigoExistente");
						}else {
							ScsProcedimientos modulo = new ScsProcedimientos();

							NewIdDTO idProcedimiento = scsProcedimientosExtendsMapper.getIdProcedimiento(usuarios.get(0).getIdinstitucion());
							if (idProcedimiento == null) {
								idProcedimientoNuevo = 1;
								modulo.setIdprocedimiento("1");
							} else {
								idProcedimientoNuevo = (Integer.parseInt(idProcedimiento.getNewId()) + 1);
								modulo.setIdprocedimiento(idProcedimientoNuevo.toString());
							}

							
							modulo.setIdinstitucion(idInstitucion);

							BigDecimal precio = new BigDecimal(modulosItem.getImporte());
							modulo.setNombre(modulosItem.getNombre());
							modulo.setCodigo(modulosItem.getCodigo());
							modulo.setCodigoext(modulosItem.getCodigoext());
							modulo.setPrecio(precio);
							modulo.setVigente("1");
							modulo.setOrden(0);
							modulo.setComplemento(modulosItem.getComplemento());
							modulo.setFechadesdevigor(modulosItem.getFechadesdevigor());
							modulo.setFechahastavigor(modulosItem.getFechahastavigor());
							modulo.setIdjurisdiccion(Short.parseShort(modulosItem.getIdjurisdiccion()));
							modulo.setPermitiraniadirletrado(modulosItem.getPermitiraniadirletrado());
							modulo.setObservaciones(modulosItem.getObservaciones());
							modulo.setFechamodificacion(new Date());
							modulo.setUsumodificacion(usuarios.get(0).getIdusuario());
							
							LOGGER.info(
									"createModules() / scsProcedimientosExtendsMapper.updateByExample() -> Entrada a scsProcedimientosExtendsMapper para insertar los modulos seleccionados");

							response = scsProcedimientosExtendsMapper.insert(modulo);

							LOGGER.info(
									"createModules() / scsProcedimientosExtendsMapper.updateByExample() -> Salida de scsProcedimientosExtendsMapper para insertar los modulos seleccionados");

							
//							Combo multiselect de Procedimientos
							if(response == 1)
							if(modulosItem.getProcedimientos() != null && modulosItem.getProcedimientos().length()>0) {
								
								String[] lista = modulosItem.getProcedimientos().split(",");
								
								for(int i = 0; i < lista.length ; i++) {
									ScsPretensionesproced pretension = new ScsPretensionesproced();
									pretension.setIdpretension(Short.parseShort(lista[i]));
									pretension.setFechamodificacion(new Date());
									pretension.setUsumodificacion(usuarios.get(0).getIdusuario());
									pretension.setIdprocedimiento(modulo.getIdprocedimiento());
									pretension.setIdinstitucion(idInstitucion);
									LOGGER.info(
											"updateModules() / scsProcedimientosExtendsMapper.updateByExample() -> Entrada a scsProcedimientosExtendsMapper para actualizar los procedimientos");
									scsPretensionesprocedMapper.insert(pretension);
									LOGGER.info(
											"updateModules() / scsProcedimientosExtendsMapper.updateByExample() -> Salida de scsProcedimientosExtendsMapper para actualizar los procedimientos");
								}
							
								
							}else {
//								ELIMINAR TODOS
								ScsPretensionesprocedExample pretenprocedExample = new ScsPretensionesprocedExample();
								pretenprocedExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdprocedimientoEqualTo(modulo.getIdprocedimiento());
								List<ScsPretensionesproced> pretensionesAnteriores = scsPretensionesprocedMapper.selectByExample(pretenprocedExample);
								
								for(int i = 0; i < pretensionesAnteriores.size() ; i++) {
									scsPretensionesprocedMapper.deleteByPrimaryKey(pretensionesAnteriores.get(i));
								}
							}
							
						}
						
					
				} catch (Exception e) {
					LOGGER.error(e);
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					insertResponseDTO.setStatus(SigaConstants.KO);
				}
			}
		}

		if (response == 0) {
			error.setCode(400);
			if(error.getDescription() == null) {
							error.setDescription("areasmaterias.materias.ficha.insertarerror");
			}
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else {
			insertResponseDTO.setId(String.valueOf(idProcedimientoNuevo));
			error.setCode(200);
			error.setDescription("general.message.registro.insertado");
		}
		insertResponseDTO.setError(error);

		LOGGER.info("updateModules() -> Salida del servicio para insertar modulos");

		return insertResponseDTO;
	}
	
	

	@Override
	public UpdateResponseDTO updateAcreditacion(AcreditacionDTO acreditacionDTO, HttpServletRequest request) {
		LOGGER.info("updateAcreditacion() ->  Entrada al servicio para crear Acreditacion");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"updateAcreditacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateAcreditacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					for (AcreditacionItem acreditacionItem : acreditacionDTO.getAcreditacionItem()) {

						ScsAcreditacionprocedimiento acreditacionProc = new ScsAcreditacionprocedimiento();
						acreditacionProc.setIdacreditacion(Short.parseShort(acreditacionItem.getIdAcreditacion()));
						acreditacionProc.setIdinstitucion(idInstitucion);
						acreditacionProc.setIdprocedimiento(acreditacionItem.getIdprocedimiento());
						ScsAcreditacionprocedimiento registro = scsAcreditacionProcedimientoMapper.selectByPrimaryKey(acreditacionProc);

						
						if (!UtilidadesString.esCadenaVacia(acreditacionItem.getPorcentaje()) ) {
							BigDecimal porcentaje = new BigDecimal(Double.valueOf(acreditacionItem.getPorcentaje()));
							registro.setPorcentaje(porcentaje);
						}else{
							BigDecimal porcentaje = new BigDecimal(Double.valueOf(0));
							registro.setPorcentaje(porcentaje);
						}
						
//						registro.setNigNumprocedimiento(Short.parseShort(acreditacionItem.getNig_numprocedimiento()));
						if(acreditacionItem.isNigProcedimiento()) {
							registro.setNigNumprocedimiento(Short.parseShort("1"));
						}else {
							registro.setNigNumprocedimiento(Short.parseShort("0"));
						}
						registro.setCodigoext(acreditacionItem.getCodigoext());
						registro.setCodsubtarifa(acreditacionItem.getCodSubTarifa());	
						registro.setUsumodificacion(usuarios.get(0).getIdusuario());
						registro.setFechamodificacion(new Date());
						LOGGER.info(
								"updateAcreditacion() / scsProcedimientosExtendsMapper.deleteByExample() -> Entrada a scsProcedimientosExtendsMapper para crear las acreditaciones seleccionadas");

						response = scsAcreditacionProcedimientoMapper.updateByPrimaryKey(registro);


						LOGGER.info(
								"updateAcreditacion() / scsProcedimientosExtendsMapper.deleteByExample() -> Salida de scsProcedimientosExtendsMapper para crear las acreditaciones seleccionadas");

					}
					
				} catch (Exception e) {
					LOGGER.error(e);
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("areasmaterias.materias.ficha.actualizarerror");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("areasmaterias.materias.ficha.actualizarerror");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("updateAcreditacion() -> Salida del servicio para crear acreditaciones");

		return updateResponseDTO;
	}

	
	@Override
	public UpdateResponseDTO deleteAcreditacion(AcreditacionDTO acreditacionDTO, HttpServletRequest request) {
		LOGGER.info("deleteAcreditacion() ->  Entrada al servicio para eliminar Acreditacion");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"deleteAcreditacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteAcreditacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {
					boolean existe = false;
					for (AcreditacionItem acreditacionItem : acreditacionDTO.getAcreditacionItem()) {

						ScsActuaciondesignaExample ejemploActuacion = new ScsActuaciondesignaExample();
						ejemploActuacion.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdacreditacionEqualTo(Short.parseShort(acreditacionItem.getIdAcreditacion())).andIdprocedimientoEqualTo(acreditacionItem.getIdprocedimiento());
						List<ScsActuaciondesigna> actuacion = scsActuaciondesignaMapper.selectByExample(ejemploActuacion);

						if(!(actuacion == null || actuacion.size() == 0)) {
							existe = true; 
						}
					}
					
					if(!existe) {

					for (AcreditacionItem acreditacionItem : acreditacionDTO.getAcreditacionItem()) {
							ScsAcreditacionprocedimiento acreditacionProc = new ScsAcreditacionprocedimiento();
							acreditacionProc.setIdacreditacion(Short.parseShort(acreditacionItem.getIdAcreditacion()));
							acreditacionProc.setIdinstitucion(idInstitucion);
							acreditacionProc.setIdprocedimiento(acreditacionItem.getIdprocedimiento());
							LOGGER.info(
									"deleteAcreditacion() / scsProcedimientosExtendsMapper.deleteByExample() -> Entrada a scsProcedimientosExtendsMapper para eliminar las acreditaciones seleccionadas");

							response = scsAcreditacionProcedimientoMapper.deleteByPrimaryKey(acreditacionProc);

							LOGGER.info(
									"deleteAcreditacion() / scsProcedimientosExtendsMapper.deleteByExample() -> Salida de scsProcedimientosExtendsMapper para eliminar las acreditaciones seleccionadas");

					}
					}else {
						response = 2;
					}
					
				} catch (Exception e) {
					LOGGER.error(e);
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("areasmaterias.materias.ficha.eliminarError");
			updateResponseDTO.setStatus(SigaConstants.KO);
			
		} else if(response == 2) {
			error.setCode(400);
			error.setDescription("menu.justiciaGratuita.maestros.acreditacionEnUso");
			updateResponseDTO.setStatus(SigaConstants.KO);	
	
		}else{
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}
		updateResponseDTO.setError(error);

		LOGGER.info("deleteAcreditacion() -> Salida del servicio para eliminar acreditaciones");

		return updateResponseDTO;
	}

	
	@Override
	public UpdateResponseDTO deleteModules(ModulosDTO modulosDTO, HttpServletRequest request) {
		LOGGER.info("deleteModules() ->  Entrada al servicio para eliminar modulos");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"deleteModules() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteModules() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {

					for (ModulosItem modulosItem : modulosDTO.getModulosItem()) {

						
						ScsProcedimientos modulo = new ScsProcedimientos();
						modulo.setIdprocedimiento(modulosItem.getIdProcedimiento());
						modulo.setIdinstitucion(idInstitucion);

						modulo = scsProcedimientosExtendsMapper.selectByPrimaryKey(modulo);
						
						modulo.setFechamodificacion(new Date());
						modulo.setUsumodificacion(usuarios.get(0).getIdusuario());
						
						if(modulosDTO.getBaja().equals("bajalogica") || modulosItem.isUsado()) {
							modulo.setFechahastavigor(modulosItem.getFechahastavigor());
							modulo.setFechabaja(new Date());
							response = scsProcedimientosExtendsMapper.updateByPrimaryKey(modulo);
						}else if (modulosDTO.getBaja().equals("bajafisica")) {
							eliminaRelacionesProcedimiento(idInstitucion, modulosItem);
							response = scsProcedimientosExtendsMapper.deleteByPrimaryKey(modulo);
						}else if(modulosDTO.getBaja().equals("reactivar")) {
							modulo.setFechahastavigor(null);
							response = scsProcedimientosExtendsMapper.updateByPrimaryKey(modulo);
						}
						
						
						
						LOGGER.info(
								"deleteModules() / scsProcedimientosExtendsMapper.deleteByExample() -> Entrada a scsProcedimientosExtendsMapper para eliminar los modulos seleccionados");

						

						LOGGER.info(
								"deleteModules() / scsProcedimientosExtendsMapper.deleteByExample() -> Salida de scsProcedimientosExtendsMapper para eliminar los modulos seleccionados");

					}
					
				} catch (Exception e) {
					LOGGER.error(e);
					response = 0;
					error.setCode(400);
					if(!e.getCause().getMessage().contains("ORA-02292")){
						error.setDescription("general.mensaje.error.bbdd");
					}else {
						error.setDescription("sjcs.maestros.modulos.eliminacionfisicaerrorenuso");
					}
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("general.message.registro.actualizado");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteModules() -> Salida del servicio para eliminar modulos");

		return updateResponseDTO;
	}
	
	private void eliminaRelacionesProcedimiento(Short idInstitucion, ModulosItem modulosItem) {
		
		// Eliminamos las relaciones del procedimiento con las pretensiones
		ScsPretensionesprocedExample pretension = new ScsPretensionesprocedExample();
		pretension.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								   .andIdprocedimientoEqualTo(modulosItem.getIdProcedimiento());
		
		List<ScsPretensionesproced> pretensionesEliminar = scsPretensionesprocediMapper.selectByExample(pretension);
		
		for (ScsPretensionesproced pretensionEliminar : pretensionesEliminar) {
			
			ScsPretensionesprocedKey pretensionProcedKey = new ScsPretensionesprocedKey();
			pretensionProcedKey.setIdinstitucion(pretensionEliminar.getIdinstitucion());
			pretensionProcedKey.setIdpretension(pretensionEliminar.getIdpretension());
			pretensionProcedKey.setIdprocedimiento(pretensionEliminar.getIdprocedimiento());
			
			scsPretensionesprocediMapper.deleteByPrimaryKey(pretensionProcedKey);
		}
		
		// Eliminamos las relaciones del procedimiento con las acreditaciones
		ScsAcreditacionprocedimientoExample acreditacion = new ScsAcreditacionprocedimientoExample();
		acreditacion.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								   .andIdprocedimientoEqualTo(modulosItem.getIdProcedimiento());
		
		List<ScsAcreditacionprocedimiento> acreditacionesEliminar = scsAcreditacionProcedimientoMapper.selectByExample(acreditacion);
		
		for (ScsAcreditacionprocedimiento acreditacionEliminar : acreditacionesEliminar) {
			
			ScsAcreditacionprocedimientoKey acreditacionProcedKey = new ScsAcreditacionprocedimientoKey();
			acreditacionProcedKey.setIdinstitucion(acreditacionEliminar.getIdinstitucion());
			acreditacionProcedKey.setIdacreditacion(acreditacionEliminar.getIdacreditacion());
			acreditacionProcedKey.setIdprocedimiento(acreditacionEliminar.getIdprocedimiento());
			
			scsAcreditacionProcedimientoMapper.deleteByPrimaryKey(acreditacionProcedKey);
		}
		
		// Eliminamos las relaciones del procedimiento con los juzgados
		ScsJuzgadoprocedimientoExample juzgado = new ScsJuzgadoprocedimientoExample();
		juzgado.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								   .andIdprocedimientoEqualTo(modulosItem.getIdProcedimiento());
		
		List<ScsJuzgadoprocedimiento> juzgadosEliminar = scsJuzgadoprocedimientoMapper.selectByExample(juzgado);
		
		for (ScsJuzgadoprocedimiento juzgadoEliminar : juzgadosEliminar) {
			
			ScsJuzgadoprocedimientoKey juzgadoProcedKey = new ScsJuzgadoprocedimientoKey();
			juzgadoProcedKey.setIdinstitucion(juzgadoEliminar.getIdinstitucion());
			juzgadoProcedKey.setIdjuzgado(juzgadoEliminar.getIdjuzgado());
			juzgadoProcedKey.setIdprocedimiento(juzgadoEliminar.getIdprocedimiento());
			
			scsJuzgadoprocedimientoMapper.deleteByPrimaryKey(juzgadoProcedKey);
		}
	}
	
	@Override
	public UpdateResponseDTO checkModules(ModulosDTO modulosDTO, HttpServletRequest request) {
		LOGGER.info("checkModules() ->  Entrada al servicio para comprobar si los modulos han sido usados");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		boolean hayAlgunModuloUsado = false;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"checkModules() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"checkModules() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				try {
					for (ModulosItem modulosItem : modulosDTO.getModulosItem()) {
						
						if (comprobarModuloUsado(idInstitucion, modulosItem.getIdProcedimiento())) {
							hayAlgunModuloUsado = true;
							modulosItem.setUsado(true);
						}
					}
					
					response = 1;
				} catch (Exception e) {
					LOGGER.error(e);
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
		}

		updateResponseDTO.setModuloUsado(hayAlgunModuloUsado);
		updateResponseDTO.setModulosItem(modulosDTO.getModulosItem());
		updateResponseDTO.setError(error);

		LOGGER.info("checkModules() -> Salida del servicio para comprobar si los modulos han sido usados");

		return updateResponseDTO;
	}

	private boolean comprobarModuloUsado(Short idInstitucion, String idProcedimiento) {
		boolean usado = false;
		
		ScsActuaciondesignaExample scsActuaciondesignaExample = new ScsActuaciondesignaExample();
        scsActuaciondesignaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								   .andIdprocedimientoEqualTo(idProcedimiento);
		
		List<ScsActuaciondesigna> scsActuaciondesigna = scsActuaciondesignaMapper.selectByExample(scsActuaciondesignaExample);
		
		ScsDesignaExample scsDesignaExample = new ScsDesignaExample();
		scsDesignaExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								   .andIdprocedimientoEqualTo(idProcedimiento);
		
		List<ScsDesigna> scsDesigna = scsDesignaMapper.selectByExample(scsDesignaExample);
		
		ExpExpedienteExample expExpedienteExample = new ExpExpedienteExample();
		expExpedienteExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								   .andProcedimientoEqualTo(idProcedimiento);
		
		List<ExpExpediente> expExpediente = expExpedienteMapper.selectByExample(expExpedienteExample);
		
		PcajgProcedScsprocedExample pcajgProcedScsprocedExample = new PcajgProcedScsprocedExample();
		pcajgProcedScsprocedExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								   .andIdprocedimientoEqualTo(idProcedimiento);
		
		List<PcajgProcedScsproced> pcajgProcedScsproced = pcajgProcedScsprocedMapper.selectByExample(pcajgProcedScsprocedExample);
		
		if (scsActuaciondesigna.size() > 0 || 
				scsDesigna.size() > 0 || 
				expExpediente.size() > 0 || 
				pcajgProcedScsproced.size() > 0) {
			usado = true;
		}
		
		return usado;
	}
	
	@Override
	public ComboDTO getAcreditaciones(String idProcedimiento, HttpServletRequest request) {
		LOGGER.info("getJurisdicciones() -> Entrada al servicio para obtener combo jurisdicciones");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getAcreditaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getAcreditaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"getAcreditaciones() / scsAcreditacionExtendsMapper.getJurisdicciones() -> Entrada a scsAcreditacionExtendsMapper para obtener los acreditaciones");
	
				List<ComboItem> comboItems = scsAcreditacionExtendsMapper.getAcreditaciones(idInstitucion.toString(), idProcedimiento);

				LOGGER.info(
						"getAcreditaciones() / scsAcreditacionExtendsMapper.getAcreditaciones() -> Salida a scsAcreditacionExtendsMapper para obtener los acreditaciones");

				combo.setCombooItems(comboItems);
			}

		}
		LOGGER.info("getAcreditaciones() -> Salida del servicio para obtener combo acreditaciones");
		return combo;
	}
	
	
	@Override
	public AcreditacionDTO searchAcreditaciones(String idProcedimiento, HttpServletRequest request) {
		LOGGER.info("searchSubzonas() -> Entrada al servicio para obtener las zonas");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		AcreditacionDTO acreditacionDTO = new AcreditacionDTO();
		List<AcreditacionItem> acreditaciones = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchSubzonas() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchSubzonas() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchSubzonas() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Entrada a scsSubzonaExtendsMapper para obtener las subzonas");
				ModulosItem moduloItem = new ModulosItem();
				moduloItem.setIdProcedimiento(idProcedimiento);
				moduloItem.setidInstitucion(idInstitucion.toString());
				
				acreditaciones = scsAcreditacionExtendsMapper.searchAcreditaciones(moduloItem);

				LOGGER.info(
						"searchSubzonas() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Salida a scsSubzonaExtendsMapper para obtener las subzonas");


				if (acreditaciones != null) {
					acreditacionDTO.setAcreditacionItem(acreditaciones);
				}
			}

		}
		LOGGER.info("searchSubzonas() -> Salida del servicio para obtener las zonas");
		return acreditacionDTO;
	}


	@Override
	@Transactional
	public InsertResponseDTO createAcreditacion(AcreditacionItem acreditacionItem, HttpServletRequest request) {
		LOGGER.info("createZone() ->  Entrada al servicio para crear una nueva zona");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"createZone() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"createZone() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					ScsAcreditacionprocedimiento acreditacionProc = new ScsAcreditacionprocedimiento();
					acreditacionProc.setIdacreditacion(Short.parseShort(acreditacionItem.getIdAcreditacion()));
					acreditacionProc.setIdinstitucion(idInstitucion);
					acreditacionProc.setIdprocedimiento(acreditacionItem.getIdprocedimiento());
					ScsAcreditacionprocedimiento Existente = scsAcreditacionProcedimientoMapper.selectByPrimaryKey(acreditacionProc);
					

					LOGGER.info(
							"updateGroupZone() / scsZonasExtendsMapper.selectByExample(ageEventoExample) -> Salida a ageEventoExtendsMapper para buscar la sesión");

					if(Existente == null) {
						ScsAcreditacionprocedimiento registro = new ScsAcreditacionprocedimiento();

						BigDecimal porcentaje = new BigDecimal(Double.valueOf(acreditacionItem.getPorcentaje()));
						registro.setIdacreditacion(Short.parseShort(acreditacionItem.getIdAcreditacion()));
						registro.setIdprocedimiento(acreditacionItem.getIdprocedimiento());
						registro.setPorcentaje(porcentaje);
						if(acreditacionItem.isNigProcedimiento()) {
							registro.setNigNumprocedimiento(Short.parseShort("1"));
						}else {
							registro.setNigNumprocedimiento(Short.parseShort("0"));
						}
						registro.setCodigoext(acreditacionItem.getCodigoext());
						registro.setCodsubtarifa(acreditacionItem.getCodSubTarifa());
						registro.setUsumodificacion(usuario.getIdusuario());
						registro.setFechamodificacion(new Date());
						registro.setIdinstitucion(idInstitucion);

						LOGGER.info(
								"updateAcreditacion() / scsProcedimientosExtendsMapper.deleteByExample() -> Entrada a scsProcedimientosExtendsMapper para crear las acreditaciones seleccionadas");

						response = scsAcreditacionProcedimientoMapper.insert(registro);

						LOGGER.info(
								"updateAcreditacion() / scsProcedimientosExtendsMapper.deleteByExample() -> Salida de scsProcedimientosExtendsMapper para crear las acreditaciones seleccionadas");

					}

				} catch (Exception e) {
					LOGGER.error(e);
					response = 0;
					error.setCode(400); 
					error.setDescription("general.mensaje.error.bbdd");
					insertResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("messages.inserted.success");
		}

		insertResponseDTO.setError(error);

		LOGGER.info("createZone() -> Salida del servicio para crear un nuevo grupo zona");

		return insertResponseDTO;
	}
//	
//	@Override
//	public MateriasDTO searchMaterias(String idArea, HttpServletRequest request) {
//		LOGGER.info("searchMaterias() -> Entrada al servicio para obtener las Materias");
//
//		// Conseguimos información del usuario logeado
//		String token = request.getHeader("Authorization");
//		String dni = UserTokenUtils.getDniFromJWTToken(token);
//		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
//		MateriasDTO materiasDTO = new MateriasDTO();
//		List<MateriasItem> materiasItems = null;
//		MateriasItem materia = new MateriasItem();
//		if (idInstitucion != null) {
//			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
//			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
//
//			LOGGER.info(
//					"searchMaterias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
//
//			LOGGER.info(
//					"searchMaterias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			if (usuarios != null && usuarios.size() > 0) {
//
//				LOGGER.info(
//						"searchMaterias() / scsMateriaExtendsMapper.searchMateria() -> Entrada a scsMateriaExtendsMapper para obtener las Materias");
//				materia.setIdArea(idArea);
//				materia.setidInstitucion(idInstitucion);
//				materiasItems = scsMateriaExtendsMapper.searchMateria(materia);
//
//				LOGGER.info(
//						"searchMaterias() / scsMateriaExtendsMapper.searchMateria() -> Salida a scsMateriaExtendsMapper para obtener las Materias");
//
//				for (MateriasItem materias : materiasItems) {
//
//					if (materias.getJurisdiccion() != null) {
//
//						String[] materiasEnvio = materias.getJurisdiccion().split(";");
//						materias.setJurisdicciones(materiasEnvio);
//
//					}
//				}
//				materiasDTO.setmateriasItems(materiasItems);
//			}
//
//		}
//		LOGGER.info("searchMateria() -> Salida del servicio para obtener las Materias");
//		return materiasDTO;
//	}
//	
//
//	@Override
//	public UpdateResponseDTO updateMaterias(AreasDTO areasDTO, HttpServletRequest request) {
//		LOGGER.info("deleteZones() ->  Entrada al servicio para actualizar Materias");
//// PENDIENTE DE ACABAR
//		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
//		Error error = new Error();
//		int response = 0;
//
//		String token = request.getHeader("Authorization");
//		String dni = UserTokenUtils.getDniFromJWTToken(token);
//		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
//
//		if (null != idInstitucion) {
//
//			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
//			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
//
//			LOGGER.info(
//					"deleteZones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
//
//			LOGGER.info(
//					"deleteZones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			if (null != usuarios && usuarios.size() > 0) {
//
//				try {
//					
//					for (AreasItem areasItem : areasDTO.getAreasItems()) {
//					
//						ScsMateria materia = new ScsMateria();
//						materia.setIdmateria(Short.parseShort(areasItem.getIdMateria()));
//						materia.setFechamodificacion(new Date());
//						materia.setIdarea(Short.parseShort(areasItem.getIdArea()));
//						materia.setIdinstitucion(areasItem.getidInstitucion());
//						materia.setNombre(areasItem.getNombreMateria());
//						materia.setContenido(areasItem.getContenido());
//						materia.setUsumodificacion(usuarios.get(0).getIdusuario());
//						response = scsMateriaExtendsMapper.updateByPrimaryKeySelective(materia);
//						
//						String[] jurisdiccionesCombo  = areasItem.getJurisdiccion().split(";");
//						
//						ScsMateriajurisdiccionExample ejemploJurisdiccion = new ScsMateriajurisdiccionExample();
//						ejemploJurisdiccion.createCriteria().andIdinstitucionEqualTo(areasItem.getidInstitucion()).andIdareaEqualTo(Short.parseShort(areasItem.getIdArea())).andIdmateriaEqualTo(Short.parseShort(areasItem.getIdMateria()));
//						List<ScsMateriajurisdiccion> jurisdiccionesAnteriores = scsMateriaJurisdiccionExtendsMapper.selectByExample(ejemploJurisdiccion);
//						
//							for (ScsMateriajurisdiccion jurisdicciones : jurisdiccionesAnteriores) {
//								scsMateriaJurisdiccionExtendsMapper.deleteByPrimaryKey(jurisdicciones);
//							}
//							
//							if(jurisdiccionesCombo[0] != "")
//							for (String jurisdicciones : jurisdiccionesCombo) {
//								ScsMateriajurisdiccion materiaJurisdiccion = new ScsMateriajurisdiccion();
//								materiaJurisdiccion.setFechamodificacion(new Date());
//								materiaJurisdiccion.setIdarea(Short.parseShort(areasItem.getIdArea()));
//								materiaJurisdiccion.setIdmateria(Short.parseShort(areasItem.getIdMateria()));
//								materiaJurisdiccion.setIdinstitucion(areasItem.getidInstitucion());
//								materiaJurisdiccion.setIdjurisdiccion(Short.parseShort(jurisdicciones));
//								materiaJurisdiccion.setUsumodificacion(usuarios.get(0).getIdusuario());
//								scsMateriaJurisdiccionExtendsMapper.insert(materiaJurisdiccion);
//							}
//						}
//						
//
//				} catch (Exception e) {
//					response = 0;
//					error.setCode(400);
//					error.setDescription("general.mensaje.error.bbdd");
//					updateResponseDTO.setStatus(SigaConstants.KO);
//				}
//			}
//
//		}
//
//		if (response == 0) {
//			error.setCode(400);
//			error.setDescription("areasmaterias.materias.ficha.actualizarerror");
//			updateResponseDTO.setStatus(SigaConstants.KO);
//		} else {
//			error.setCode(200);
//			error.setDescription("general.message.registro.actualizado");
//		}
//
//		updateResponseDTO.setError(error);
//
//		LOGGER.info("deleteZones() -> Salida del servicio para eliminar zonas de un grupo zona");
//
//		return updateResponseDTO;
//	}
//
//	@Override
//	public UpdateResponseDTO deleteMaterias(AreasDTO areasDTO, HttpServletRequest request) {
//		LOGGER.info("deleteZones() ->  Entrada al servicio para actualizar Materias");
//		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
//		Error error = new Error();
//		int response = 0;
//
//		String token = request.getHeader("Authorization");
//		String dni = UserTokenUtils.getDniFromJWTToken(token);
//		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
//
//		if (null != idInstitucion) {
//
//			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
//			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
//
//			LOGGER.info(
//					"deleteZones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
//
//			LOGGER.info(
//					"deleteZones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			if (null != usuarios && usuarios.size() > 0) {
//
//				try {
//					boolean existe = false;
//					
//					for (AreasItem areasItem : areasDTO.getAreasItems()) {
//						
//						ScsTurnoExample ejemploTurno = new ScsTurnoExample();
//						ejemploTurno.createCriteria().andIdmateriaEqualTo(Short.parseShort(areasItem.getIdMateria())).andIdareaEqualTo(Short.parseShort(areasItem.getIdArea())).andIdinstitucionEqualTo(idInstitucion);
//						List<ScsTurno> turnos = scsTurnoMapper.selectByExample(ejemploTurno);
//
//						ExpExpedienteExample ejemploExpediente = new ExpExpedienteExample();
//						ejemploExpediente.createCriteria().andIdmateriaEqualTo(Short.parseShort(areasItem.getIdMateria())).andIdareaEqualTo(Short.parseShort(areasItem.getIdArea())).andIdinstitucionEqualTo(idInstitucion);
//						List<ExpExpediente> expedientes = expExpedienteMapper.selectByExample(ejemploExpediente);
//
//						if(!(turnos == null || turnos.size() == 0) || !(expedientes == null || expedientes.size() == 0)) {
//							existe = true; 
//						}
//					}
//					
//					if(!existe) {
//						for (AreasItem areasItem : areasDTO.getAreasItems()) {
//							
//							ScsMateriajurisdiccionExample ejemploJurisdiccion = new ScsMateriajurisdiccionExample();
//							ejemploJurisdiccion.createCriteria().andIdinstitucionEqualTo(areasItem.getidInstitucion()).andIdareaEqualTo(Short.parseShort(areasItem.getIdArea())).andIdmateriaEqualTo(Short.parseShort(areasItem.getIdMateria()));
//							List<ScsMateriajurisdiccion> jurisdiccionesAnteriores = scsMateriaJurisdiccionExtendsMapper.selectByExample(ejemploJurisdiccion);
//							
//								for (ScsMateriajurisdiccion jurisdicciones : jurisdiccionesAnteriores) {
//									scsMateriaJurisdiccionExtendsMapper.deleteByPrimaryKey(jurisdicciones);
//								}
//								
//							ScsMateriaExample example = new ScsMateriaExample();
//							example.createCriteria().andIdmateriaEqualTo(Short.parseShort(areasItem.getIdMateria())).andIdinstitucionEqualTo(idInstitucion).andIdareaEqualTo(Short.parseShort(areasItem.getIdArea()));
//							response = scsMateriaExtendsMapper.deleteByExample(example);
//							
//					}
//					}else {
//						response = 2;
//					}
//					
//					
//				} catch (Exception e) {
//					response = 0;
//					error.setCode(400);
//					error.setDescription("general.mensaje.error.bbdd");
//					updateResponseDTO.setStatus(SigaConstants.KO);
//				}
//			}
//
//		}
//
//		if (response == 0) {
//			error.setCode(400);
//			error.setDescription("areasmaterias.materias.ficha.erroreliminadomaterias");
//			updateResponseDTO.setStatus(SigaConstants.KO);
//		
//		} else if(response == 2) {
//			error.setCode(400);
//			error.setDescription("areasmaterias.materias.ficha.materiaEnUso");
//			updateResponseDTO.setStatus(SigaConstants.KO);	
//	
//		}else{
//			error.setCode(200);
//			error.setDescription("general.message.registro.actualizado");
//		}
//
//		updateResponseDTO.setError(error);
//
//		LOGGER.info("deleteZones() -> Salida del servicio para eliminar materias de un area");
//
//		return updateResponseDTO;
//	}
//	
//	@Override
//	@Transactional
//	public InsertResponseDTO createMaterias(AreasItem areasItem, HttpServletRequest request) {
//		LOGGER.info("createZone() ->  Entrada al servicio para crear una nueva zona");
//
//		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
//		Error error = new Error();
//		int response = 0;
//
//		String token = request.getHeader("Authorization");
//		String dni = UserTokenUtils.getDniFromJWTToken(token);
//		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
//		Short idMateriaNueva = 0;
//		if (null != idInstitucion) {
//
//			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
//			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
//
//			LOGGER.info(
//					"createMaterias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
//
//			LOGGER.info(
//					"createMaterias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			if (null != usuarios && usuarios.size() > 0) {
//				AdmUsuarios usuario = usuarios.get(0);
//
//				try {
//
////					MateriasItem ejemplo = new MateriasItem();
////					ejemplo.setNombreMateria(areasItem.getNombreMateria());
////					ejemplo.setIdArea(areasItem.getIdArea());
////					ejemplo.setidInstitucion(idInstitucion);
////					
////					List<MateriasItem> materiasDuplicadas = scsMateriaExtendsMapper.searchMateria(ejemplo);
//
//					LOGGER.info(
//							"createMaterias() / scsAreasMateriasExtendsMapper.selectByExample(ageEventoExample) -> Salida a ageEventoExtendsMapper para buscar la sesión");
//
////					if (materiasDuplicadas != null && materiasDuplicadas.size() > 0) {
////						
////						error.setCode(400);
////						error.setDescription("messages.censo.nombreExiste");
////						
////					} else {
//						
//							ScsMateria materia = new ScsMateria();
//							materia.setContenido(areasItem.getContenido());
//							materia.setFechamodificacion(new Date());
//							materia.setIdinstitucion(idInstitucion);
//							materia.setNombre(areasItem.getNombreMateria());
//							materia.setUsumodificacion(usuarios.get(0).getIdusuario());
//							materia.setIdarea(Short.parseShort(areasItem.getIdArea()));
//							
//							String[] jurisdiccionesCombo = null;
//							
//							NewIdDTO idMateria = scsMateriaExtendsMapper.getIdMateria(usuario.getIdinstitucion());
//							if(areasItem.getJurisdiccion() != "" && areasItem.getJurisdiccion() != null) {
//								jurisdiccionesCombo  = areasItem.getJurisdiccion().split(";");
//							}
//
//							if (idMateria == null) {
//								idMateriaNueva = 1;
//								materia.setIdmateria((short) 1);
//							} else {
//								idMateriaNueva = (short) (Integer.parseInt(idMateria.getNewId()) + 1);
//								materia.setIdmateria(idMateriaNueva);
//							}
//
//							LOGGER.info(
//									"createMaterias() / scsAreasMateriasExtendsMapper.insert() -> Entrada a scsAreasMateriasExtendsMapper para insertar una nueva materia");
//
//							response = scsMateriaExtendsMapper.insert(materia);
//							if(jurisdiccionesCombo != null) {
//								for (String jurisdicciones : jurisdiccionesCombo) {
//									ScsMateriajurisdiccion materiaJurisdiccion = new ScsMateriajurisdiccion();
//									materiaJurisdiccion.setFechamodificacion(new Date());
//									materiaJurisdiccion.setIdarea(Short.parseShort(areasItem.getIdArea()));
//									materiaJurisdiccion.setIdmateria(idMateriaNueva);
//									materiaJurisdiccion.setIdinstitucion(idInstitucion);
//									materiaJurisdiccion.setIdjurisdiccion(Short.parseShort(jurisdicciones));
//									materiaJurisdiccion.setUsumodificacion(usuarios.get(0).getIdusuario());
//									scsMateriaJurisdiccionExtendsMapper.insert(materiaJurisdiccion);
//								}
//							}
//							
//							LOGGER.info(
//									"createGroupZona() / scsAreasMateriasExtendsMapper.insert() -> Salida de scsAreasMateriasExtendsMapper para insertar una nueva materia");
////						}
//
//				} catch (Exception e) {
//					response = 0;
//					error.setCode(400);
//					error.setDescription("general.mensaje.error.bbdd");
//					insertResponseDTO.setStatus(SigaConstants.KO);
//				}
//			}
//
//		}
//
//		if (response == 0) {
//			error.setCode(400);
//			insertResponseDTO.setStatus(SigaConstants.KO);
//		} else {
//			error.setCode(200);
//			insertResponseDTO.setId(String.valueOf(Short.valueOf(idMateriaNueva)));
//			error.setDescription("messages.inserted.success");
//		}
//
//		insertResponseDTO.setError(error);
//
//		LOGGER.info("createZone() -> Salida del servicio para crear una nueva materia");
//
//		return insertResponseDTO;
//	}
//	

	@Override
	public StringDTO getComplementoProcedimiento(ModulosItem modulosItem, HttpServletRequest request) {
		LOGGER.info("getComplementoProcedimiento() -> Entrada al servicio para obtener el complemento del modulo");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		StringDTO stringDTO = new StringDTO();
		List<String> complemento = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {

				complemento = scsProcedimientosExtendsMapper.getComplementoProcedimiento(idInstitucion.toString(), modulosItem.getIdProcedimiento());

				if (complemento != null && complemento.size() > 0) {
					stringDTO.setValor(complemento.get(0));
				}
			}

		}
		LOGGER.info("getComplementoProcedimiento() -> Salida del servicio para obtener el complemento del modulo");
		return stringDTO;
	}
}
