package org.itcgae.siga.scs.services.impl.guardia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaDTO;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaItem;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaLetradoGrupoDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsSaltoscompensacionesExtendsMapper;
import org.itcgae.siga.scs.services.guardia.SaltosCompGuardiasService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaltosCompGuardiasServiceImpl implements SaltosCompGuardiasService {

	private Logger LOGGER = Logger.getLogger(SaltosCompGuardiasServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsSaltoscompensacionesExtendsMapper saltoscompensacionesMapper;

	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;

	@Autowired
	private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;

	@Override
	public SaltoCompGuardiaDTO searchSaltosYCompensaciones(SaltoCompGuardiaItem saltoItem, HttpServletRequest request) {

		LOGGER.info("searchSaltosYCompensaciones() -> Entrada para obtener los saltos y compensaciones");

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
				exampleUsuarios.createCriteria().andNifEqualTo(dni)
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"searchSaltosYCompensaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"searchSaltosYCompensaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {

					GenParametrosExample genParametrosExample = new GenParametrosExample();

					genParametrosExample.createCriteria().andModuloEqualTo("SCS")
							.andParametroEqualTo("TAM_MAX_CONSULTA_JG")
							.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));

					genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

					LOGGER.info(
							"searchJusticiables() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

					tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

					LOGGER.info(
							"searchJusticiables() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

					if (tamMax != null) {
						tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
					} else {
						tamMaximo = null;
					}

					List<SaltoCompGuardiaItem> saltosComp = saltoscompensacionesMapper
							.searchSaltosCompensaciones(saltoItem, idInstitucion.toString(), tamMaximo);

					if ((saltosComp != null) && tamMaximo != null && (saltosComp.size()) > tamMaximo) {
						error.setCode(200);
						error.setDescription("La consulta devuelve más de " + tamMaximo
								+ " resultados, pero se muestran sólo los " + tamMaximo
								+ " más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
						saltoCompDTO.setError(error);
						saltosComp.remove(saltosComp.size() - 1);
					}

					if (saltosComp != null && !saltosComp.isEmpty()) {

						LOGGER.info("searchGuardias() -> Si es por grupo, se obtienen los diferentes letrados");

						for (SaltoCompGuardiaItem saltoComp : saltosComp) {

							if (!UtilidadesString.esCadenaVacia(saltoComp.getGrupo())) {

								List<SaltoCompGuardiaLetradoGrupoDTO> saltoCompLetradosGrupo = saltoscompensacionesMapper
										.searchLetrados(saltoComp, idInstitucion.toString());

								if (saltoCompLetradosGrupo != null && !saltoCompLetradosGrupo.isEmpty()) {

									List<String> letrados = new ArrayList<>();

									for (SaltoCompGuardiaLetradoGrupoDTO letrado : saltoCompLetradosGrupo) {
										letrados.add(letrado.getLetrado());
									}

									saltoComp.setLetradosGrupo(letrados);
								}

							}

						}
					}

					saltoCompDTO.setSaltosCompItems(saltosComp);

					LOGGER.info("searchGuardias() -> Salida ya con los datos recogidos");
				}
			}

		} catch (Exception e) {
			LOGGER.error(
					"SaltosCompGuardiasServiceImpl.searchSaltosYCompensaciones() -> Se ha producido un error en la búsqueda del saltos y compensaciones.",
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

		LOGGER.info("guardarSaltosCompensaciones() -> Entrada para guardar los saltos y compensaciones");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();

		try {

			if (idInstitucion != null) {

				LOGGER.info(
						"guardarSaltosCompensaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"guardarSaltosCompensaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty() && listaSaltoItem != null && !listaSaltoItem.isEmpty()) {

					for (SaltoCompGuardiaItem saltoCompGuardiaItem : listaSaltoItem) {

						// Buscamos si el salto o compensación existe
						List<SaltoCompGuardiaItem> listaSaltosComp = new ArrayList<>();

						if (!UtilidadesString.esCadenaVacia(saltoCompGuardiaItem.getIdSaltosTurno())) {
							listaSaltosComp = saltoscompensacionesMapper.searchSaltosCompensaciones(
									saltoCompGuardiaItem, idInstitucion.toString(), Integer.valueOf(2));
						}

						if (listaSaltosComp != null) {
							// En caso de que no exista procedemos a realizar la insercción
							if (listaSaltosComp.isEmpty()) {

								// Comprobamos si tenemos que insertar en la tabla de grupos o no
								if (!UtilidadesString.esCadenaVacia(saltoCompGuardiaItem.getGrupo())) {

									MaxIdDto nuevoIdGrupo = saltoscompensacionesMapper
											.selectNuevoIdSaltosCompensacionesGrupo();

									int insertado = saltoscompensacionesMapper.guardarSaltosCompensacionesGrupo(
											saltoCompGuardiaItem, idInstitucion.toString(),
											nuevoIdGrupo.getIdMax().toString(), usuarios.get(0));

									if (insertado == 1) {
										deleteResponseDTO.setStatus("OK");
									} else {
										deleteResponseDTO.setStatus("KO");
										LOGGER.error(
												"SaltosCompGuardiasServiceImpl.guardarSaltosCompensaciones() -> Se ha producido un error al tratar de guardar el salto u compensacion.");

										error.setCode(500);
										error.setDescription("Error al guardar el salto u compensación.");
										deleteResponseDTO.setError(error);
									}

								} else {

									if (!UtilidadesString.esCadenaVacia(saltoCompGuardiaItem.getColegiadoGrupo())) {

										CenColegiadoExample cenColegiadoExample = new CenColegiadoExample();
										cenColegiadoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
												.andNcolegiadoEqualTo(saltoCompGuardiaItem.getColegiadoGrupo());

										List<CenColegiado> colegiado = cenColegiadoExtendsMapper
												.selectByExample(cenColegiadoExample);

										saltoCompGuardiaItem.setIdPersona(colegiado.get(0).getIdpersona().toString());

										MaxIdDto nuevoId = saltoscompensacionesMapper.selectNuevoIdSaltosCompensaciones(
												saltoCompGuardiaItem, Short.toString(idInstitucion));

										int insertado = saltoscompensacionesMapper.guardarSaltosCompensaciones(
												saltoCompGuardiaItem, Short.toString(idInstitucion),
												Long.toString(nuevoId.getIdMax()), usuarios.get(0));

										if (insertado == 1) {
											deleteResponseDTO.setStatus("OK");
										} else {
											deleteResponseDTO.setStatus("KO");
											LOGGER.error(
													"SaltosCompGuardiasServiceImpl.guardarSaltosCompensaciones() -> Se ha producido un error al tratar de guardar el salto u compensacion.");

											error.setCode(500);
											error.setDescription("Error al guardar el salto u compensación.");
											deleteResponseDTO.setError(error);
										}

									}

								}
							}
							// En caso de que exista procedemos a actualizar el registro
							else {

								// Comprobamos si tenemos que actualizar en la tabla de grupos o no
								if (!UtilidadesString.esCadenaVacia(saltoCompGuardiaItem.getGrupo())) {
									int actualizado = saltoscompensacionesMapper.actualizarSaltosCompensacionesGrupo(
											saltoCompGuardiaItem, Short.toString(idInstitucion), usuarios.get(0));

									if (actualizado == 1) {
										deleteResponseDTO.setStatus("OK");
									} else {
										deleteResponseDTO.setStatus("KO");
										LOGGER.error(
												"SaltosCompGuardiasServiceImpl.guardarSaltosCompensaciones() -> Se ha producido un error al tratar de actualizar el salto u compensacion.");

										error.setCode(500);
										error.setDescription("Error al actualizar el salto u compensación.");
										deleteResponseDTO.setError(error);
									}
								} else {

									CenColegiadoExample cenColegiadoExample = new CenColegiadoExample();
									cenColegiadoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
											.andNcolegiadoEqualTo(saltoCompGuardiaItem.getColegiadoGrupo());

									List<CenColegiado> colegiado = cenColegiadoExtendsMapper
											.selectByExample(cenColegiadoExample);

									saltoCompGuardiaItem.setIdPersona(colegiado.get(0).getIdpersona().toString());

									int actualizado = saltoscompensacionesMapper.actualizarSaltosCompensaciones(
											saltoCompGuardiaItem, Short.toString(idInstitucion), usuarios.get(0));

									if (actualizado == 1) {
										deleteResponseDTO.setStatus("OK");
									} else {
										deleteResponseDTO.setStatus("KO");
										LOGGER.error(
												"SaltosCompGuardiasServiceImpl.guardarSaltosCompensaciones() -> Se ha producido un error al tratar de actualizar el salto u compensacion.");

										error.setCode(500);
										error.setDescription("Error al actualizar el salto u compensación.");
										deleteResponseDTO.setError(error);
									}
								}

							}

						}

					}

				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"SaltosCompGuardiasServiceImpl.guardarSaltosCompensaciones() -> Se ha producido un error al tratar de guardar y/o actualizar el salto u compensacion.",
					e);

			error.setCode(500);
			error.setDescription("Error al guardar y/o actualizar el salto u compensación.");
			error.setMessage(e.getMessage());

			deleteResponseDTO.setError(error);
		}

		return deleteResponseDTO;
	}

}
