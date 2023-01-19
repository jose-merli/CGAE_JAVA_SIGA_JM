package org.itcgae.siga.scs.services.impl.maestros;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.FundamentoResolucionItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosExample;
import org.itcgae.siga.db.entities.ScsTipofundamentos;
import org.itcgae.siga.db.entities.ScsTipofundamentosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenRecursosCatalogosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTipofundamentosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTiporesolucionExtendsMapper;
import org.itcgae.siga.scs.services.maestros.IGestionFundamentoResolucionService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestionFundamentoResolucionServiceImpl implements IGestionFundamentoResolucionService {

	private Logger LOGGER = Logger.getLogger(GestionFundamentoResolucionServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsTiporesolucionExtendsMapper scsTiporesolucionExtendsMapper;

	@Autowired
	private ScsTipofundamentosExtendsMapper scsTipofundamentosExtendsMapper;

	@Autowired
	private GenRecursosCatalogosExtendsMapper genRecursosCatalogosExtendsMapper;

	@Override
	public ComboDTO getResoluciones(HttpServletRequest request) {
		LOGGER.info("getResoluciones() -> Entrada al servicio para obtener combo resoluciones");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDTO combo = new ComboDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getResoluciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getResoluciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				LOGGER.info(
						"getResoluciones() / cenScsJurisdiccionExtendsMapper.getJurisdicciones() -> Entrada a cenPartidojudicialExtendsMapper para obtener los partidos judiciales");

				List<ComboItem> comboItems = scsTiporesolucionExtendsMapper.getResoluciones(usuario.getIdlenguaje(),"maestros");

				LOGGER.info(
						"getResoluciones() / cenScsJurisdiccionExtendsMapper.getJurisdicciones() -> Salida a cenPartidojudicialExtendsMapper para obtener los partidos judiciales");

				combo.setCombooItems(comboItems);
			}

		}
		LOGGER.info("getResoluciones() -> Salida del servicio para obtener combo resoluciones");
		return combo;
	}

	@Override
	@Transactional
	public UpdateResponseDTO updateFundamentoResolucion(FundamentoResolucionItem fundamentoResolucionItem,
			HttpServletRequest request) {
		LOGGER.info("updateFundamentoResolucion() ->  Entrada al servicio para modificar un fundamento de resolucion");

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
					"updateFundamentoResolucion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateFundamentoResolucion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					// Obtenermos fundamento de resolucion que queremos modificar
					// Obtenemos el fundamento de resolucion que queremos modificar
					ScsTipofundamentosExample example = new ScsTipofundamentosExample();
					example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdfundamentoEqualTo(Short.valueOf(fundamentoResolucionItem.getIdFundamento()))
							.andFechabajaIsNull();

					LOGGER.info(
							"updateFundamentoResolucion() / scsTipofundamentosExtendsMapper.selectByExample(example) -> Entrada a scsTipofundamentosExtendsMapper para buscar un fundamento resolucion");

					List<ScsTipofundamentos> scsTipofundamentosList = scsTipofundamentosExtendsMapper
							.selectByExample(example);

					LOGGER.info(
							"updateFundamentoResolucion() / scsTipofundamentosExtendsMapper.selectByExample(example) -> Salida a scsTipofundamentosExtendsMapper para buscar  un fundamento resolucion");

					if (scsTipofundamentosList != null && scsTipofundamentosList.size() > 0) {

						ScsTipofundamentos scsTipofundamento = scsTipofundamentosList.get(0);

						// Buscamos si existe una descripcion que sea igual en fundamentos q no sea el
						// propio

						GenRecursosCatalogosExample exampleRecursosRepetidos = new GenRecursosCatalogosExample();
						exampleRecursosRepetidos.createCriteria()
								.andDescripcionEqualTo(fundamentoResolucionItem.getDescripcionFundamento())
								.andCampotablaEqualTo("DESCRIPCION").andNombretablaEqualTo("SCS_TIPOFUNDAMENTOS")
								.andIdinstitucionEqualTo(idInstitucion)
								.andIdrecursoNotEqualTo(scsTipofundamento.getDescripcion());

						List<GenRecursosCatalogos> recursosRepetidos = genRecursosCatalogosExtendsMapper
								.selectByExample(exampleRecursosRepetidos);

						// Si la descripcion se repite
						if (recursosRepetidos != null && recursosRepetidos.size() > 0) {
							error.setCode(400);
							error.setDescription("messages.jgr.maestros.gestionFundamentosResolucion.existeFundamentosResolucionMismaDescripcion");
						} else {

							// Si no se repite se modifica tanto la el fundamento como la descripcion del
							// fundamento en recurso catalago

							GenRecursosCatalogosExample exampleRecursos = new GenRecursosCatalogosExample();
							exampleRecursos.createCriteria().andCampotablaEqualTo("DESCRIPCION")
									.andNombretablaEqualTo("SCS_TIPOFUNDAMENTOS").andIdinstitucionEqualTo(idInstitucion)
									.andIdrecursoEqualTo(scsTipofundamento.getDescripcion());

							LOGGER.info(
									"createFundamentosResolucion() / genRecursosCatalogosExtendsMapper.selectByExample(example) -> Entrada a genRecursosCatalogosExtendsMapper para buscar descripcion del fundamento resolucion");

							List<GenRecursosCatalogos> recursos = genRecursosCatalogosExtendsMapper
									.selectByExample(exampleRecursos);

							LOGGER.info(
									"createFundamentosResolucion() / genRecursosCatalogosExtendsMapper.selectByExample(example) -> Salida a genRecursosCatalogosExtendsMapper para buscar descripcion del fundamento resolucion");

							GenRecursosCatalogos recursoFundamento = recursos.get(0);

							if(!fundamentoResolucionItem.getDescripcionFundamento().equals(recursoFundamento.getDescripcion())) {
								recursoFundamento.setDescripcion(fundamentoResolucionItem.getDescripcionFundamento());
								recursoFundamento.setFechamodificacion(new Date());
								recursoFundamento.setUsumodificacion(usuario.getIdusuario());
								recursoFundamento.setIdinstitucion(idInstitucion);
								
								LOGGER.info(
										"updateFundamentoResolucion() / genRecursosCatalogosExtendsMapper.updateByExample() -> Entrada a genRecursosCatalogosExtendsMapper para modificar un fundamento de resolucion");

								response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(recursoFundamento);

								LOGGER.info(
										"updateFundamentoResolucion() / genRecursosCatalogosExtendsMapper.updateByExample() -> Salida de genRecursosCatalogosExtendsMapper para modificar un fundamento de resolucion");
								
								updateRestoIdiomas(recursoFundamento);
							}

							
							
							scsTipofundamento.setFechamodificacion(new Date());
							scsTipofundamento.setUsumodificacion(usuario.getIdusuario());
							scsTipofundamento.setIdinstitucion(idInstitucion);

							scsTipofundamento.setCodigo(fundamentoResolucionItem.getCodigoExt());
							scsTipofundamento.setTextoplantilla(fundamentoResolucionItem.getTextoPlantilla());
							scsTipofundamento.setTextoplantilla2(fundamentoResolucionItem.getTextoPlantilla2());
							scsTipofundamento.setTextoplantilla3(fundamentoResolucionItem.getTextoPlantilla3());
							scsTipofundamento.setTextoplantilla4(fundamentoResolucionItem.getTextoPlantilla4());

							if (fundamentoResolucionItem.getIdTipoResolucion() != null) {
								scsTipofundamento.setIdtiporesolucion(
										Short.valueOf(fundamentoResolucionItem.getIdTipoResolucion()));
							}

							LOGGER.info(
									"updateFundamentoResolucion() / scsTipofundamentosExtendsMapper.updateByExample() -> Entrada a scsTipofundamentosExtendsMapper para modificar un fundamento de resolucion");

							response = scsTipofundamentosExtendsMapper.updateByPrimaryKey(scsTipofundamento);

							LOGGER.info(
									"updateFundamentoResolucion() / scsTipofundamentosExtendsMapper.updateByExample() -> Salida de scsTipofundamentosExtendsMapper para modificar un fundamento de resolucion");
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

		if (response == 0 && error.getDescription() == null) {
			error.setCode(400);
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else if (error.getCode() == null) {
			error.setCode(200);
			updateResponseDTO.setStatus(SigaConstants.OK);
		}

		updateResponseDTO.setError(error);

		LOGGER.info("updateFundamentoResolucion() -> Salida del servicio para editar un fundamento de resolucion");

		return updateResponseDTO;
	}

	@Override
	@Transactional
	public InsertResponseDTO createFundamentoResolucion(FundamentoResolucionItem fundamentoResolucionItem,
			HttpServletRequest request) {
		LOGGER.info("createFundamentosResolucion() ->  Entrada al servicio para crear un fundamento resolucion");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		Integer idFundamento = null;

		ScsTipofundamentos scsTipofundamento = null;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"createFundamentosResolucion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"createFundamentosResolucion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					if (fundamentoResolucionItem != null) {

						// Buscamos si se encuentra la descripcion del nuevo fundamento
						GenRecursosCatalogosExample exampleRecursos = new GenRecursosCatalogosExample();
						exampleRecursos.createCriteria()
								.andDescripcionEqualTo(fundamentoResolucionItem.getDescripcionFundamento())
								.andCampotablaEqualTo("DESCRIPCION").andNombretablaEqualTo("SCS_TIPOFUNDAMENTOS")
								.andIdinstitucionEqualTo(idInstitucion);

						List<GenRecursosCatalogos> recursos = genRecursosCatalogosExtendsMapper
								.selectByExample(exampleRecursos);

						if (recursos != null && recursos.size() > 0) {
							error.setCode(400);
							error.setDescription("messages.jgr.maestros.gestionFundamentosResolucion.existeFundamentosResolucionMismaDescripcion");

						} else {

							scsTipofundamento = new ScsTipofundamentos();
							GenRecursosCatalogos genRecursosCatalogo = new GenRecursosCatalogos();

							genRecursosCatalogo.setCampotabla("DESCRIPCION");
							genRecursosCatalogo.setDescripcion(fundamentoResolucionItem.getDescripcionFundamento());
							genRecursosCatalogo.setFechamodificacion(new Date());
							genRecursosCatalogo.setIdinstitucion(idInstitucion);
							genRecursosCatalogo.setIdlenguaje(usuario.getIdlenguaje());

							NewIdDTO idRecursoBD = genRecursosCatalogosExtendsMapper
									.getMaxIdRecursoCatalogo(String.valueOf(idInstitucion), usuario.getIdlenguaje());

							if (idRecursoBD == null) {
								genRecursosCatalogo.setIdrecurso("1");
							} else {
								long idRecurso = Long.parseLong(idRecursoBD.getNewId()) + 1;
								genRecursosCatalogo.setIdrecurso(String.valueOf(idRecurso));
							}

							genRecursosCatalogo.setIdrecursoalias("scs_tipofundamentos.descripcion." + idInstitucion
									+ "." + genRecursosCatalogo.getIdrecurso());

							genRecursosCatalogo.setNombretabla("SCS_TIPOFUNDAMENTOS");
							genRecursosCatalogo.setUsumodificacion(usuario.getUsumodificacion());

							LOGGER.info(
									"createFundamentosResolucion() / genRecursosCatalogosExtendsMapper.selectByExample(example) -> Entrada a genRecursosCatalogosExtendsMapper para guardar descripcion");

							response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

							LOGGER.info(
									"createFundamentosResolucion() / genRecursosCatalogosExtendsMapper.selectByExample(example) -> Salida a genRecursosCatalogosExtendsMapper para guardar descripcion");

							scsTipofundamento.setDescripcion(genRecursosCatalogo.getIdrecurso());

							insertarRestoIdiomas(genRecursosCatalogo);

							LOGGER.info(
									"createFundamentosResolucion() / scsTipofundamentosExtendsMapper.getIdFundamentoResolucion(example) -> Entrada a scsTipofundamentosExtendsMapper para buscar un fundamento resolucion");

							NewIdDTO idF = scsTipofundamentosExtendsMapper.getIdFundamentoResolucion(idInstitucion);

							LOGGER.info(
									"createFundamentosResolucion() / scsTipofundamentosExtendsMapper.getIdFundamentoResolucion(example) -> Salida a scsTipofundamentosExtendsMapper para buscar  un fundamento resolucion");

							if (idF == null) {
								scsTipofundamento.setIdfundamento((short) 1);
							} else {
								idFundamento = (Integer.parseInt(idF.getNewId()) + 1);
								scsTipofundamento.setIdfundamento(Short.valueOf(idFundamento.toString()));
							}

							scsTipofundamento.setFechamodificacion(new Date());
							scsTipofundamento.setUsumodificacion(usuario.getIdusuario().intValue());
							scsTipofundamento.setIdinstitucion(idInstitucion);

							scsTipofundamento.setCodigo(fundamentoResolucionItem.getCodigoExt());
							scsTipofundamento.setTextoplantilla(fundamentoResolucionItem.getTextoPlantilla());
							scsTipofundamento.setTextoplantilla2(fundamentoResolucionItem.getTextoPlantilla2());
							scsTipofundamento.setTextoplantilla3(fundamentoResolucionItem.getTextoPlantilla3());
							scsTipofundamento.setTextoplantilla4(fundamentoResolucionItem.getTextoPlantilla4());

							scsTipofundamento.setBloqueado("N");

							if (fundamentoResolucionItem.getIdTipoResolucion() != null) {
								scsTipofundamento.setIdtiporesolucion(
										Short.valueOf(fundamentoResolucionItem.getIdTipoResolucion()));
							}

							LOGGER.info(
									"createFundamentosResolucion() / scsTipofundamentosExtendsMapper.updateByExample() -> Entrada a scsTipofundamentosExtendsMapper para modificar un fundamento de resolucion");

							response = scsTipofundamentosExtendsMapper.insert(scsTipofundamento);

							LOGGER.info(
									"createFundamentosResolucion() / scsTipofundamentosExtendsMapper.updateByExample() -> Salida de scsTipofundamentosExtendsMapper para modificar un fundamento de resolucion");
						}

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
			insertResponseDTO.setStatus(SigaConstants.OK);
			insertResponseDTO.setId(String.valueOf(scsTipofundamento.getIdfundamento()));

		}

		insertResponseDTO.setError(error);

		LOGGER.info("createFundamentosResolucion() -> Salida del servicio para crear un fundamento de resolucion");

		return insertResponseDTO;
	}

	private int insertarRestoIdiomas(GenRecursosCatalogos genRecursosCatalogo) {

		int response = 1;

		try {
			String idLenguaje = genRecursosCatalogo.getIdlenguaje();
			String descripcion = genRecursosCatalogo.getDescripcion();
			switch (idLenguaje) {
			case "1":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#CA"));
				genRecursosCatalogo.setIdlenguaje("2");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#EU"));
				genRecursosCatalogo.setIdlenguaje("3");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#GL"));
				genRecursosCatalogo.setIdlenguaje("4");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);
				break;
			case "2":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#ES"));
				genRecursosCatalogo.setIdlenguaje("1");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#EU"));
				genRecursosCatalogo.setIdlenguaje("3");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#GL"));
				genRecursosCatalogo.setIdlenguaje("4");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);
				break;
			case "3":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#CA"));
				genRecursosCatalogo.setIdlenguaje("2");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#ES"));
				genRecursosCatalogo.setIdlenguaje("1");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#GL"));
				genRecursosCatalogo.setIdlenguaje("4");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);
				break;
			case "4":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#CA"));
				genRecursosCatalogo.setIdlenguaje("2");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#EU"));
				genRecursosCatalogo.setIdlenguaje("3");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#ES"));
				genRecursosCatalogo.setIdlenguaje("1");
				response = genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogo);
				break;

			}
		} catch (Exception e) {
			response = 0;
		}

		return response;
	}
	
	private int updateRestoIdiomas(GenRecursosCatalogos genRecursosCatalogo) {

		int response = 1;

		try {
			String idLenguaje = genRecursosCatalogo.getIdlenguaje();
			String descripcion = genRecursosCatalogo.getDescripcion();
			switch (idLenguaje) {
			case "1":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#CA"));
				genRecursosCatalogo.setIdlenguaje("2");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#EU"));
				genRecursosCatalogo.setIdlenguaje("3");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#GL"));
				genRecursosCatalogo.setIdlenguaje("4");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);
				break;
			case "2":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#ES"));
				genRecursosCatalogo.setIdlenguaje("1");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#EU"));
				genRecursosCatalogo.setIdlenguaje("3");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#GL"));
				genRecursosCatalogo.setIdlenguaje("4");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);
				break;
			case "3":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#CA"));
				genRecursosCatalogo.setIdlenguaje("2");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#ES"));
				genRecursosCatalogo.setIdlenguaje("1");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#GL"));
				genRecursosCatalogo.setIdlenguaje("4");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);
				break;
			case "4":
				genRecursosCatalogo.setDescripcion(descripcion.concat("#CA"));
				genRecursosCatalogo.setIdlenguaje("2");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#EU"));
				genRecursosCatalogo.setIdlenguaje("3");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);

				genRecursosCatalogo.setDescripcion(descripcion.concat("#ES"));
				genRecursosCatalogo.setIdlenguaje("1");
				response = genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogo);
				break;

			}
		} catch (Exception e) {
			response = 0;
		}

		return response;
	}

}