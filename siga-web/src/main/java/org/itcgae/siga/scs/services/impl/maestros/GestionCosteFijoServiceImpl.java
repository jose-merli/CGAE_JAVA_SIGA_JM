package org.itcgae.siga.scs.services.impl.maestros;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.CosteFijoDTO;
import org.itcgae.siga.DTOs.scs.CosteFijoItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ScsTipoactuacioncostefijo;
import org.itcgae.siga.db.entities.ScsTipoactuacioncostefijoExample;
import org.itcgae.siga.db.mappers.ScsTipoactuacioncostefijoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsCostefijoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTipoactuacionExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTipoasistenciaExtendsMapper;
import org.itcgae.siga.scs.services.maestros.IGestionCosteFijoService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionCosteFijoServiceImpl implements IGestionCosteFijoService {

	private Logger LOGGER = Logger.getLogger(GestionCosteFijoServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsCostefijoExtendsMapper scsCostefijoExtendsMapper;

	@Autowired
	private ScsTipoasistenciaExtendsMapper scsTipoasistenciaExtendsMapper;

	@Autowired
	private ScsTipoactuacionExtendsMapper scsTipoactuacionExtendsMapper;

	@Autowired
	private ScsTipoactuacioncostefijoMapper scsTipoactuacioncostefijoMapper;

	@Override
	public CosteFijoDTO searchCostesFijos(boolean historico, HttpServletRequest request) {
		LOGGER.info("searchCostesFijos() -> Entrada al servicio para obtener los costes fijos");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		CosteFijoDTO costeFijoDTO = new CosteFijoDTO();
		List<CosteFijoItem> costeFijoItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchCostesFijos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchCostesFijos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"searchCostesFijos() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Entrada a scsSubzonaExtendsMapper para obtener las subzonas");

				costeFijoItems = scsCostefijoExtendsMapper.searchCosteFijos(historico, usuario.getIdlenguaje(),
						idInstitucion);

				LOGGER.info(
						"searchCostesFijos() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Salida a scsSubzonaExtendsMapper para obtener las subzonas");

				if (costeFijoItems != null) {
					costeFijoDTO.setCosteFijoItems(costeFijoItems);
				}
			}

		}
		LOGGER.info("searchCostesFijos() -> Salida del servicio para obtener los costes fijos");
		return costeFijoDTO;
	}

	@Override
	public ComboDTO getComboCosteFijos(HttpServletRequest request) {
		LOGGER.info("getComboCosteFijos() -> Entrada al servicio para obtener combo costes fijos");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getComboCosteFijos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getComboCosteFijos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"getComboCosteFijos() / scsCostefijoExtendsMapper.getComboCosteFijos() -> Entrada a scsCostefijoExtendsMapper para obtener los costes fijos");

				List<ComboItem> comboItems = scsCostefijoExtendsMapper.getComboCosteFijos(usuario.getIdlenguaje(),
						idInstitucion);

				LOGGER.info(
						"getComboCosteFijos() / scsCostefijoExtendsMapper.getComboCosteFijos() -> Salida a scsCostefijoExtendsMapper para obtener los costes fijos");

				combo.setCombooItems(comboItems);
			}

		}
		LOGGER.info("getCosteFijos() -> Salida del servicio para obtener combo costes fijos");
		return combo;
	}

	@Override
	public ComboDTO getComboActuacion(String idTipoAsistencia, HttpServletRequest request) {

		LOGGER.info("getComboActuacion() -> Entrada al servicio para obtener combo de las actuaciones");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getComboActuacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getComboActuacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"getComboActuacion() / scsTipoactuacionExtendsMapper.getComboActuacion() -> Entrada a scsTipoactuacionExtendsMapper para obtener las actuaciones");

				List<ComboItem> comboItems = scsTipoactuacionExtendsMapper.getComboActuacion(idTipoAsistencia,
						usuario.getIdlenguaje(), idInstitucion);

				LOGGER.info(
						"getComboActuacion() / scsTipoactuacionExtendsMapper.getComboActuacion() -> Salida a scsTipoactuacionExtendsMapper para obtener las actuaciones");

				combo.setCombooItems(comboItems);
			}

		}
		LOGGER.info("getComboActuacion() -> Salida del servicio para obtener combo actuaciones");
		return combo;

	}

	@Override
	public ComboDTO getComboAsistencia(HttpServletRequest request) {

		LOGGER.info("getComboAsistencia() -> Entrada al servicio para obtener combo de las asistencias");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getComboAsistencia() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getComboAsistencia() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"getComboAsistencia() / scsTipoasistenciaExtendsMapper.getComboActuacion() -> Entrada a scsTipoasistenciaExtendsMapper para obtener las asistencias");

				List<ComboItem> comboItems = scsTipoasistenciaExtendsMapper.getComboAsistencia(usuario.getIdlenguaje(),
						idInstitucion);

				LOGGER.info(
						"getComboAsistencia() / scsTipoasistenciaExtendsMapper.getComboActuacion() -> Salida a scsTipoasistenciaExtendsMapper para obtener las asistencias");

				combo.setCombooItems(comboItems);
			}

		}
		LOGGER.info("getComboAsistencia() -> Salida del servicio para obtener combo asistencias");
		return combo;

	}

	@Override
	public InsertResponseDTO createCosteFijo(CosteFijoItem costeFijoItem, HttpServletRequest request) {
		LOGGER.info("createCosteFijo() ->  Entrada al servicio para crear un nuevo coste fijo");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		short idZona = 0;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"createCosteFijo() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"createCosteFijo() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					LOGGER.info(
							"createCosteFijo() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Entrada a scsTipoactuacioncostefijoMapper para buscar los costes fijos propios");

					ScsTipoactuacioncostefijoExample example = new ScsTipoactuacioncostefijoExample();
					example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdcostefijoEqualTo(Short.valueOf(costeFijoItem.getIdCosteFijo()))
							.andIdtipoasistenciaEqualTo(Short.valueOf(costeFijoItem.getIdTipoAsistencia()))
							.andIdtipoactuacionEqualTo(Short.valueOf(costeFijoItem.getIdTipoActuacion()));

					List<ScsTipoactuacioncostefijo> scsCostefijoList = scsTipoactuacioncostefijoMapper
							.selectByExample(example);

					LOGGER.info(
							"createCosteFijo() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Salida a scsTipoactuacioncostefijoMapper para buscar los costes fijos propios");

					if (scsCostefijoList != null && scsCostefijoList.size() > 0) {

						ScsTipoactuacioncostefijo costeFijo = scsCostefijoList.get(0);
						
						if(costeFijo.getFechabaja() != null) {
							error.setCode(400);
							error.setDescription("messages.jgr.maestros.gestionCostesFijos.existeCosteFijoMismasCaracteristicasDadoBaja");
						}else {
							error.setCode(400);
							error.setDescription("messages.jgr.maestros.gestionCostesFijos.existeCosteFijoMismasCaracteristicas");
						}
						

					} else {

						ScsTipoactuacioncostefijo scsTipoactuacioncostefijo = new ScsTipoactuacioncostefijo();

						scsTipoactuacioncostefijo.setFechabaja(null);
						scsTipoactuacioncostefijo.setFechamodificacion(new Date());
						scsTipoactuacioncostefijo.setUsumodificacion(usuario.getIdusuario().intValue());
						scsTipoactuacioncostefijo.setIdinstitucion(idInstitucion);
						
						if (costeFijoItem.getIdCosteFijo() != null) {
							scsTipoactuacioncostefijo.setIdcostefijo(Short.decode(costeFijoItem.getIdCosteFijo()));
						}
						
						if (costeFijoItem.getIdTipoActuacion() != null) {
							scsTipoactuacioncostefijo.setIdtipoactuacion(Short.decode(costeFijoItem.getIdTipoActuacion()));

						}

						if (costeFijoItem.getIdTipoAsistencia() != null) {
							scsTipoactuacioncostefijo
							.setIdtipoasistencia(Short.decode(costeFijoItem.getIdTipoAsistencia()));
						}
					
						if (costeFijoItem.getImporte() != null) {
							scsTipoactuacioncostefijo
							.setImporte(BigDecimal.valueOf(Float.valueOf(costeFijoItem.getImporte())));
						}

						LOGGER.info(
								"createCosteFijo() / scsTipoactuacioncostefijoMapper.insert() -> Entrada a scsTipoactuacioncostefijoMapper para insertar el nuevo coste fijo");

						response = scsTipoactuacioncostefijoMapper.insert(scsTipoactuacioncostefijo);

						LOGGER.info(
								"createCosteFijo() / scsTipoactuacioncostefijoMapper.insert() -> Salida de scsTipoactuacioncostefijoMapper para insertar el nuevo coste fijo");
					}

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					insertResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0 && error.getDescription() == null) {
			error.setCode(400);
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else if (error.getCode() == null) {
			error.setCode(200);
			insertResponseDTO.setId(String.valueOf(Short.valueOf(idZona)));
			insertResponseDTO.setStatus(SigaConstants.OK);

		}

		insertResponseDTO.setError(error);

		LOGGER.info("createCosteFijo() -> Salida del servicio para crear un nuevo coste fijo");

		return insertResponseDTO;
	}

	@Override
	public UpdateResponseDTO updateCosteFijo(CosteFijoDTO costeFijoDTO, HttpServletRequest request) {
		LOGGER.info("updateCosteFijo() ->  Entrada al servicio para modificar un coste fijo");

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
					"updateCosteFijo() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateCosteFijo() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (CosteFijoItem costeFijoItem : costeFijoDTO.getCosteFijoItems()) {

						LOGGER.info(
								"updateCosteFijo() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Entrada a scsTipoactuacioncostefijoMapper para buscar los costes fijos propios");

						ScsTipoactuacioncostefijoExample example = new ScsTipoactuacioncostefijoExample();
						example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdcostefijoEqualTo(Short.decode(costeFijoItem.getIdCosteFijoOld()))
								.andIdtipoasistenciaEqualTo(Short.decode(costeFijoItem.getIdTipoAsistenciaOld()))
								.andIdtipoactuacionEqualTo(Short.decode(costeFijoItem.getIdTipoActuacionOld()))
								.andFechabajaIsNull();

						List<ScsTipoactuacioncostefijo> scsCostefijoList = scsTipoactuacioncostefijoMapper
								.selectByExample(example);

						LOGGER.info(
								"updateCosteFijo() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Salida a scsTipoactuacioncostefijoMapper para buscar los costes fijos propios");

						if (scsCostefijoList != null && scsCostefijoList.size() > 0) {

							ScsTipoactuacioncostefijo scsTipoactuacioncostefijo = scsCostefijoList.get(0);

							scsTipoactuacioncostefijo.setFechamodificacion(new Date());
							scsTipoactuacioncostefijo.setUsumodificacion(usuario.getIdusuario().intValue());
							scsTipoactuacioncostefijo.setIdinstitucion(idInstitucion);
							
							if (costeFijoItem.getImporte() != null) {
								BigDecimal importe = new BigDecimal(costeFijoItem.getImporte());
								scsTipoactuacioncostefijo
										.setImporte(importe);
							}

							LOGGER.info(
									"updateCosteFijo() / scsTipoactuacioncostefijoMapper.updateByExample() -> Entrada a scsTipoactuacioncostefijoMapper para modificar coste fijo");

							response = scsTipoactuacioncostefijoMapper.updateByExample(scsTipoactuacioncostefijo, example);

							LOGGER.info(
									"updateCosteFijo() / scsTipoactuacioncostefijoMapper.updateByExample() -> Salida de scsTipoactuacioncostefijoMapper para modificar coste fijo");
						}
					}
				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0 && error.getDescription() == null){
			error.setCode(400);
			updateResponseDTO.setStatus(SigaConstants.KO);
		}else if (error.getCode() == null) {
			error.setCode(200);
			updateResponseDTO.setStatus(SigaConstants.OK);

		}

		updateResponseDTO.setError(error);

		LOGGER.info("updateCosteFijo() -> Salida del servicio para modificar coste fijo");

		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO deleteCosteFijo(CosteFijoDTO costeFijoDTO, HttpServletRequest request) {
		LOGGER.info("deleteCosteFijo() ->  Entrada al servicio para eliminar costes fijos");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 2;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"deleteCosteFijo() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteCosteFijo() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (CosteFijoItem costeFijoItem : costeFijoDTO.getCosteFijoItems()) {

						LOGGER.info(
								"deleteCosteFijo() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Entrada a scsTipoactuacioncostefijoMapper para buscar los costes fijos propios");

						ScsTipoactuacioncostefijoExample example = new ScsTipoactuacioncostefijoExample();
						example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdcostefijoEqualTo(Short.decode(costeFijoItem.getIdCosteFijo()))
								.andIdtipoasistenciaEqualTo(Short.decode(costeFijoItem.getIdTipoAsistencia()))
								.andIdtipoactuacionEqualTo(Short.decode(costeFijoItem.getIdTipoActuacion()));

						List<ScsTipoactuacioncostefijo> scsCostefijoList = scsTipoactuacioncostefijoMapper
								.selectByExample(example);

						LOGGER.info(
								"deleteCosteFijo() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Salida a scsTipoactuacioncostefijoMapper para buscar los costes fijos propios");

						if (scsCostefijoList != null && scsCostefijoList.size() > 0) {

							ScsTipoactuacioncostefijo costefijo = scsCostefijoList.get(0);

							costefijo.setFechabaja(new Date());
							costefijo.setFechamodificacion(new Date());
							costefijo.setUsumodificacion(usuario.getIdusuario());

							LOGGER.info(
									"deleteCosteFijo() / scsTipoactuacioncostefijoMapper.updateByPrimaryKey() -> Entrada a scsTipoactuacioncostefijoMapper para dar de baja a un coste fijo");

							response = scsTipoactuacioncostefijoMapper.updateByPrimaryKey(costefijo);

							LOGGER.info(
									"deleteCosteFijo() / scsTipoactuacioncostefijoMapper.updateByPrimaryKey() -> Salida de scsTipoactuacioncostefijoMapper para dar de baja a un coste fijo");
						}
					}

				} catch (Exception e) {
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
			updateResponseDTO.setStatus(SigaConstants.OK);

		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteCosteFijo() -> Salida del servicio para eliminar costes fijos");

		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO activateCosteFijo(CosteFijoDTO costeFijoDTO, HttpServletRequest request) {
		LOGGER.info("activateCosteFijo() ->  Entrada al servicio para activar costes fijos");

		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		int response = 2;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"activateCosteFijo() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"activateCosteFijo() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (CosteFijoItem costeFijoItem : costeFijoDTO.getCosteFijoItems()) {

						LOGGER.info(
								"activateCosteFijo() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Entrada a scsTipoactuacioncostefijoMapper para buscar los costes fijos propios");

						ScsTipoactuacioncostefijoExample example = new ScsTipoactuacioncostefijoExample();
						example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdcostefijoEqualTo(Short.decode(costeFijoItem.getIdCosteFijo()))
								.andIdtipoasistenciaEqualTo(Short.decode(costeFijoItem.getIdTipoAsistencia()))
								.andIdtipoactuacionEqualTo(Short.decode(costeFijoItem.getIdTipoActuacion()));

						List<ScsTipoactuacioncostefijo> scsCostefijoList = scsTipoactuacioncostefijoMapper
								.selectByExample(example);

						LOGGER.info(
								"activateCosteFijo() / scsTipoactuacioncostefijoMapper.selectByExample(example) -> Salida a scsTipoactuacioncostefijoMapper para buscar los costes fijos propios");

						if (scsCostefijoList != null && scsCostefijoList.size() > 0) {

							ScsTipoactuacioncostefijo costefijo = scsCostefijoList.get(0);

							costefijo.setFechabaja(null);
							costefijo.setFechamodificacion(new Date());
							costefijo.setUsumodificacion(usuario.getIdusuario());

							LOGGER.info(
									"activateCosteFijo() / scsTipoactuacioncostefijoMapper.updateByPrimaryKey() -> Entrada a scsTipoactuacioncostefijoMapper para dar de alta a un coste fijo");

							response = scsTipoactuacioncostefijoMapper.updateByPrimaryKey(costefijo);

							LOGGER.info(
									"activateCosteFijo() / scsTipoactuacioncostefijoMapper.updateByPrimaryKey() -> Salida de scsTipoactuacioncostefijoMapper para dar de alta a un coste fijo");
						}
					}

				} catch (Exception e) {
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
			updateResponseDTO.setStatus(SigaConstants.OK);
		}

		updateResponseDTO.setError(error);

		LOGGER.info("activateCosteFijo() -> Salida del servicio para activar costes fijos");

		return updateResponseDTO;
	}

}