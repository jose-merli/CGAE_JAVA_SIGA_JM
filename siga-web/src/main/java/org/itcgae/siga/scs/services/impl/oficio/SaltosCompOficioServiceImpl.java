package org.itcgae.siga.scs.services.impl.oficio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.LetradoGuardiaItem;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaDTO;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsSaltoscompensacionesExtendsMapper;
import org.itcgae.siga.scs.services.oficio.ISaltosCompOficioService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaltosCompOficioServiceImpl implements ISaltosCompOficioService {

	private Logger LOGGER = Logger.getLogger(SaltosCompOficioServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsSaltoscompensacionesExtendsMapper saltoscompensacionesMapper;

	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;

	@Override
	public SaltoCompGuardiaDTO searchSaltosYCompensaciones(SaltoCompGuardiaItem saltoItem, HttpServletRequest request) {

		LOGGER.info(
				"SaltosCompOficioServiceImpl.searchSaltosYCompensaciones() -> Entrada para obtener los saltos y compensaciones");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		SaltoCompGuardiaDTO saltoCompDTO = new SaltoCompGuardiaDTO();
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		Error error = new Error();

		try {

			if (idInstitucion != null) {

				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"SaltosCompOficioServiceImpl.searchSaltosYCompensaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"SaltosCompOficioServiceImpl.searchSaltosYCompensaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {

					GenParametrosExample genParametrosExample = new GenParametrosExample();

					genParametrosExample.createCriteria().andModuloEqualTo("SCS")
							.andParametroEqualTo("TAM_MAX_CONSULTA_JG")
							.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));

					genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

					LOGGER.info(
							"SaltosCompOficioServiceImpl.searchSaltosYCompensaciones() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

					tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

					LOGGER.info(
							"SaltosCompOficioServiceImpl.searchSaltosYCompensaciones() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

					if (tamMax != null) {
						tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
					} else {
						tamMaximo = null;
					}

					LOGGER.info(
							"SaltosCompOficioServiceImpl.searchSaltosYCompensaciones() -> Entrada a la búsqueda de saltos y compensaciones");

					List<SaltoCompGuardiaItem> listaSaltosCompensaciones = saltoscompensacionesMapper
							.searchSaltosYCompensacionesOficio(saltoItem, idInstitucion.toString(), tamMaximo);

					if (listaSaltosCompensaciones != null && !listaSaltosCompensaciones.isEmpty()) {

						if (tamMaximo != null && listaSaltosCompensaciones.size() > tamMaximo) {
							error.setCode(200);
							error.setDescription("La consulta devuelve más de " + tamMaximo
									+ " resultados, pero se muestran sólo los " + tamMaximo
									+ " más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
							saltoCompDTO.setError(error);
							listaSaltosCompensaciones.remove(listaSaltosCompensaciones.size() - 1);
						}

						saltoCompDTO.setSaltosCompItems(listaSaltosCompensaciones);

						LOGGER.info(
								"SaltosCompOficioServiceImpl.searchSaltosYCompensaciones() -> Salida de la búsqueda de saltos y compensaciones");

					}

				}

			}

		} catch (Exception e) {
			LOGGER.error(
					"SaltosCompOficioServiceImpl.searchSaltosYCompensaciones() -> Se ha producido un error en la búsqueda del saltos y compensaciones.",
					e);

			error.setCode(500);
			error.setDescription("Error en la búsqueda de saltos y compensaciones.");
			error.setMessage(e.getMessage());

			saltoCompDTO.setError(error);
		}

		return saltoCompDTO;
	}

	@Override
	public DeleteResponseDTO guardarSaltosCompensaciones(List<SaltoCompGuardiaItem> listaSaltoItem,
			HttpServletRequest request) {

		LOGGER.info(
				"SaltosCompOficioServiceImpl.guardarSaltosCompensaciones() -> Entrada para guardar los saltos y compensaciones");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		String errorStr = "Se ha producido un error al tratar de guardar el salto u compensacion.";
		String errorStrActu = "Se ha producido un error al tratar de actualizar el salto u compensacion.";

		try {

			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"SaltosCompOficioServiceImpl.guardarSaltosCompensaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"SaltosCompOficioServiceImpl.guardarSaltosCompensaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty() && listaSaltoItem != null && !listaSaltoItem.isEmpty()) {

					for (SaltoCompGuardiaItem saltoCompGuardiaItem : listaSaltoItem) {

						if (UtilidadesString.esCadenaVacia(saltoCompGuardiaItem.getIdSaltosTurno())) {

							MaxIdDto nuevoId = saltoscompensacionesMapper.selectNuevoIdSaltosCompensaciones(
									saltoCompGuardiaItem, Short.toString(idInstitucion));

							if (nuevoId == null) {
								nuevoId = new MaxIdDto();
								nuevoId.setIdMax(1L);
							}

							int insertado = saltoscompensacionesMapper.guardarSaltosCompensaciones(saltoCompGuardiaItem,
									Short.toString(idInstitucion), Long.toString(nuevoId.getIdMax()), usuarios.get(0));

							if (insertado == 1) {
								deleteResponseDTO.setStatus(SigaConstants.OK);
							} else {
								deleteResponseDTO.setStatus(SigaConstants.KO);
								LOGGER.error(
										"SaltosCompOficioServiceImpl.guardarSaltosCompensaciones() -> " + errorStr);

								error.setCode(500);
								error.setDescription(errorStr);
								deleteResponseDTO.setError(error);
							}

						} else {

							int actualizado = saltoscompensacionesMapper.actualizarSaltosCompensaciones(
									saltoCompGuardiaItem, Short.toString(idInstitucion), usuarios.get(0));

							if (actualizado == 1) {
								deleteResponseDTO.setStatus(SigaConstants.OK);
							} else {
								deleteResponseDTO.setStatus(SigaConstants.KO);
								LOGGER.error(
										"SaltosCompOficioServiceImpl.guardarSaltosCompensaciones() -> " + errorStrActu);

								error.setCode(500);
								error.setDescription(errorStrActu);
								deleteResponseDTO.setError(error);
							}

						}

					}

				}

			}

		} catch (Exception e) {
			LOGGER.error("SaltosCompOficioServiceImpl.guardarSaltosCompensaciones() -> " + errorStr, e);
			error.setCode(500);
			error.setDescription(errorStr);
			error.setMessage(e.getMessage());
			deleteResponseDTO.setError(error);
		}

		return deleteResponseDTO;
	}

	@Override
	public DeleteResponseDTO anularSaltosCompensaciones(List<SaltoCompGuardiaItem> listaSaltoItem,
			HttpServletRequest request) {
		LOGGER.info(
				"SaltosCompOficioServiceImpl.anularSaltosCompensaciones() -> Entrada para anular los saltos y compensaciones");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		String errorStr = "Se ha producido un error al tratar de anular el salto u compensacion.";

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"SaltosCompOficioServiceImpl.anularSaltosCompensaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"SaltosCompOficioServiceImpl.anularSaltosCompensaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty() && listaSaltoItem != null && !listaSaltoItem.isEmpty()) {

					for (SaltoCompGuardiaItem saltoCompGuardiaItem : listaSaltoItem) {

						if (UtilidadesString.esCadenaVacia(saltoCompGuardiaItem.getFechaUso())) {

							int anulado = saltoscompensacionesMapper.anularSaltosCompensaciones(saltoCompGuardiaItem,
									Short.toString(idInstitucion), usuarios.get(0));

							if (anulado == 1) {
								deleteResponseDTO.setStatus(SigaConstants.OK);
							} else {
								LOGGER.error("SaltosCompOficioServiceImpl.anularSaltosCompensaciones -> " + errorStr);
								error.setCode(500);
								error.setDescription(errorStr);
								deleteResponseDTO.setStatus(SigaConstants.KO);
								deleteResponseDTO.setError(error);
							}
						}

					}

				}
			}

		} catch (Exception e) {
			LOGGER.error("SaltosCompOficioServiceImpl.anularSaltosCompensaciones() -> " + errorStr, e);
			error.setCode(500);
			error.setDescription(errorStr);
			error.setMessage(e.getMessage());
			deleteResponseDTO.setError(error);
		}

		return deleteResponseDTO;
	}

	@Override
	public DeleteResponseDTO borrarSaltosCompensaciones(List<SaltoCompGuardiaItem> listaSaltoItem,
			HttpServletRequest request) {

		LOGGER.info(
				"SaltosCompOficioServiceImpl.borrarSaltosCompensaciones() -> Entrada para borrar los saltos y compensaciones");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		String errorStr = "Se ha producido un error al tratar de borrar el salto u compensacion.";

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"SaltosCompOficioServiceImpl.borrarSaltosCompensaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"SaltosCompOficioServiceImpl.borrarSaltosCompensaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty() && listaSaltoItem != null && !listaSaltoItem.isEmpty()) {

					for (SaltoCompGuardiaItem saltoCompGuardiaItem : listaSaltoItem) {

						if (UtilidadesString.esCadenaVacia(saltoCompGuardiaItem.getFechaUso())) {

							int borrado = saltoscompensacionesMapper.borrarSaltosCompensaciones(saltoCompGuardiaItem,
									Short.toString(idInstitucion));

							if (borrado == 1) {
								deleteResponseDTO.setStatus(SigaConstants.OK);
							} else {
								LOGGER.error("SaltosCompOficioServiceImpl.borrarSaltosCompensaciones() -> " + errorStr);
								error.setCode(500);
								error.setDescription(errorStr);
								deleteResponseDTO.setStatus(SigaConstants.KO);
								deleteResponseDTO.setError(error);
							}
						}
					}

				}
			}

		} catch (Exception e) {
			LOGGER.error("SaltosCompOficioServiceImpl.borrarSaltosCompensaciones() -> " + errorStr, e);
			error.setCode(500);
			error.setDescription(errorStr);
			error.setMessage(e.getMessage());
			deleteResponseDTO.setError(error);
		}

		return deleteResponseDTO;
	}

	@Override
	public ComboDTO searchLetradosTurno(SaltoCompGuardiaItem saltoItem, HttpServletRequest request) {

		LOGGER.info(
				"SaltosCompOficioServiceImpl.searchLetradosTurno() -> Entrada para buscar los letrados inscritos a un turno");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		String errorStr = "Se ha producido un error al tratar obtener los letrados inscritos a un turno.";
		ComboDTO comboDTO = new ComboDTO();

		try {

			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"SaltosCompOficioServiceImpl.searchLetradosTurno() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"SaltosCompOficioServiceImpl.searchLetradosTurno() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty() && saltoItem != null) {

					List<LetradoGuardiaItem> listLetradoGuardiaItem = saltoscompensacionesMapper
							.searchLetradosTurno(saltoItem.getIdTurno(), Short.toString(idInstitucion));

					comboDTO.setCombooItems(transformToListComboItemOficio(listLetradoGuardiaItem));

				}

			}

		} catch (Exception e) {
			LOGGER.error("SaltosCompOficioServiceImpl.searchLetradosTurno() -> " + errorStr, e);
			error.setCode(500);
			error.setDescription(errorStr);
			error.setMessage(e.getMessage());
			comboDTO.setError(error);
		}

		return comboDTO;
	}

	private List<ComboItem> transformToListComboItemOficio(List<LetradoGuardiaItem> listaLetradoGuardiaItem) {

		List<ComboItem> listaCombo = new ArrayList<>();

		for (LetradoGuardiaItem letradoGuardiaItem : listaLetradoGuardiaItem) {

			ComboItem comboItem = new ComboItem();

			comboItem.setLabel("(" + letradoGuardiaItem.getNumeroColegiado() + ") " + letradoGuardiaItem.getApellidos2()
					+ " " + letradoGuardiaItem.getApellidos1() + ", " + letradoGuardiaItem.getNombre());
			comboItem.setValue(letradoGuardiaItem.getIdPersona());
			listaCombo.add(comboItem);

		}

		return listaCombo;
	}
}
