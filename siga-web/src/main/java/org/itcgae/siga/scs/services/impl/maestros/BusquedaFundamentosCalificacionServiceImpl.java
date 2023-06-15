package org.itcgae.siga.scs.services.impl.maestros;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboDictamenDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.ComboItemDictamen;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.FundamentosCalificacionDTO;
import org.itcgae.siga.DTOs.scs.FundamentosCalificacionItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosExample;
import org.itcgae.siga.db.entities.ScsTipofundamentocalif;
import org.itcgae.siga.db.entities.ScsTipofundamentocalifExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenRecursosCatalogosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsFundamentoscalificacionExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTipodictamenejgExtendsMapper;
import org.itcgae.siga.scs.services.maestros.IBusquedaFundamentosCalificacionService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class BusquedaFundamentosCalificacionServiceImpl implements IBusquedaFundamentosCalificacionService {

	private Logger LOGGER = Logger.getLogger(BusquedaFundamentosCalificacionServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsTipodictamenejgExtendsMapper sqScsTipodictamenejgExtendsMapper;

	@Autowired
	private ScsFundamentoscalificacionExtendsMapper scsFundamentoscalificacionExtendsMapper;
	
	@Autowired
	private GenRecursosCatalogosExtendsMapper genRecursosCatalogosExtendsMapper;
	
	


	
	@Override
	public ComboDictamenDTO comboDictamen(HttpServletRequest request) {
		LOGGER.info("comboDictamen() -> Entrada al servicio para obtener los dictamenes");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ComboDictamenDTO comboDTO = new ComboDictamenDTO();
		List<ComboItemDictamen> comboItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"comboDictamen() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"comboDictamen() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"comboDictamen() / sqScsTipodictamenejgExtendsMapper.comboDic() -> Entrada a sqScsTipodictamenejgExtendsMapper para obtener los dictamenes");

				comboItems = sqScsTipodictamenejgExtendsMapper.comboDictamen(usuarios.get(0).getIdlenguaje(), idInstitucion.toString());

				LOGGER.info(
						"comboDictamen() / cenTiposolicitudSqlExtendsMapper.selectTipoSolicitud() -> Salida a sqScsTipodictamenejgExtendsMapper para obtener los dictamenes");

				if (comboItems != null) {
					comboDTO.setCombooItems(comboItems);
				}
			}

		}
		LOGGER.info("comboDictamen() -> Salida del servicio para obtener los dictamenes");
		return comboDTO;
	}


	@Override
	public FundamentosCalificacionDTO searchFundamentos(FundamentosCalificacionItem fundamentosCalificacionItem,
			HttpServletRequest request) {
		LOGGER.info("searchFundamentos() -> Entrada al servicio para obtener los fundamentos");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		FundamentosCalificacionDTO fundamentosDTO = new FundamentosCalificacionDTO();
		List<FundamentosCalificacionItem> fundamentosItems = new ArrayList<FundamentosCalificacionItem>();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchFundamentos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchFundamentos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchFundamentos() / scsFundamentoscalificacionExtendsMapper.searchFundamentos() -> Entrada a scsFundamentoscalificacionExtendsMapper para realizar la query");

				fundamentosItems = scsFundamentoscalificacionExtendsMapper.searchFundamentos(usuarios.get(0).getIdlenguaje(), idInstitucion.toString(),
						fundamentosCalificacionItem);

				LOGGER.info(
						"searchFundamentos() / scsFundamentoscalificacionExtendsMapper.searchFundamentos() -> Salida a scsFundamentoscalificacionExtendsMapper para realizar la query");

				if (fundamentosItems != null) {
					fundamentosDTO.setFundamentosCalificacionesItems(fundamentosItems);
				}
			}

		}
		LOGGER.info("searchFundamentos() -> Salida del servicio para obtener los fundamentos");
		return fundamentosDTO;
	}


	@Override
	public UpdateResponseDTO deleteFundamentos(FundamentosCalificacionDTO fundamentosCalificacionDTO, HttpServletRequest request) {
		LOGGER.info("deleteFundamentos() ->  Entrada al servicio para eliminar fundamentos");

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
					"deleteFundamentos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"deleteFundamentos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (FundamentosCalificacionItem funamentoCalificacionItem : fundamentosCalificacionDTO.getFundamentosCalificacionesItems()) {

						// Damos baja juzgado

						ScsTipofundamentocalifExample scsTipoFundamentoCalifExample = new ScsTipofundamentocalifExample();
						scsTipoFundamentoCalifExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdfundamentocalifEqualTo(Short.valueOf(funamentoCalificacionItem.getIdFundamento()));

						LOGGER.info(
								"deleteFundamentos() / scsFundamentoscalificacionExtendsMapper.selectByExample() -> Entrada a scsFundamentoscalificacionExtendsMapper para buscar el fundamento");

						List<ScsTipofundamentocalif> fundamentosList = scsFundamentoscalificacionExtendsMapper.selectByExample(scsTipoFundamentoCalifExample);

						LOGGER.info(
								"deleteFundamentos() / scsFundamentoscalificacionExtendsMapper.selectByExample() -> Salida de scsFundamentoscalificacionExtendsMapper para buscar el fundamento");

						if (null != fundamentosList && fundamentosList.size() > 0) {

							ScsTipofundamentocalif fundamentoCalificacion= fundamentosList.get(0);

							fundamentoCalificacion.setFechabaja(new Date());
							fundamentoCalificacion.setFechamodificacion(new Date());
							fundamentoCalificacion.setUsumodificacion(usuario.getIdusuario());

							LOGGER.info(
									"deleteFundamentos() / scsFundamentoscalificacionExtendsMapper.updateByPrimaryKey() -> Entrada a scsFundamentoscalificacionExtendsMapper para dar de baja a un fundamento");

							response = scsFundamentoscalificacionExtendsMapper.updateByPrimaryKey(fundamentoCalificacion);

							LOGGER.info(
									"deleteFundamentos() / scsFundamentoscalificacionExtendsMapper.updateByPrimaryKey() -> Salida de scsFundamentoscalificacionExtendsMapper para dar de baja a un fundamento");
						}
					}

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("No se han eliminado los fundamentos");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("Se han eliminado los fundamentos correctamente");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("deleteFundamentos() -> Salida del servicio para eliminar fundamentos");
		return updateResponseDTO;
		
	}


	@Override
	public UpdateResponseDTO activateFundamentos(FundamentosCalificacionDTO fundamentosCalificacionDTO, HttpServletRequest request) {

		LOGGER.info("activateFundamentos() ->  Entrada al servicio para dar de alta a grupos zona");

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
					"activateFundamentos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"activateFundamentos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					for (FundamentosCalificacionItem fundamentoItem : fundamentosCalificacionDTO.getFundamentosCalificacionesItems()) {

						// Damos alta juzgado

						ScsTipofundamentocalifExample scsTipoFundamentoCalifExample = new ScsTipofundamentocalifExample();
						scsTipoFundamentoCalifExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
						.andIdfundamentocalifEqualTo(Short.valueOf(fundamentoItem.getIdFundamento()));

						LOGGER.info(
								"activateFundamentos() / scsFundamentoscalificacionExtendsMapper.selectByExample() -> Entrada a scsFundamentoscalificacionExtendsMapper para buscar el fundamento");

						List<ScsTipofundamentocalif> fundamentosList = scsFundamentoscalificacionExtendsMapper.selectByExample(scsTipoFundamentoCalifExample);

						LOGGER.info(
								"activateFundamentos() / scsFundamentoscalificacionExtendsMapper.selectByExample() -> Salida de scsFundamentoscalificacionExtendsMapper para buscar el fundamento");

						if (null != fundamentosList && fundamentosList.size() > 0) {

							ScsTipofundamentocalif fundamento= fundamentosList.get(0);

							fundamento.setFechabaja(null);
							fundamento.setFechamodificacion(new Date());
							fundamento.setUsumodificacion(usuario.getIdusuario());

							LOGGER.info(
									"activateFundamentos() / scsFundamentoscalificacionExtendsMapper.updateByPrimaryKey() -> Entrada a scsFundamentoscalificacionExtendsMapper para dar de alta a un fundamento");

							response = scsFundamentoscalificacionExtendsMapper.updateByPrimaryKey(fundamento);

							LOGGER.info(
									"activateFundamentos() / scsJuzgadoExtendsMapper.updateByPrimaryKey() -> Salida de scsFundamentoscalificacionExtendsMapper para dar de alta a un fundamento");

							
						}

					}

				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					updateResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			error.setDescription("No se han activado los fundamentos");
			updateResponseDTO.setStatus(SigaConstants.KO);
		} else {
			error.setCode(200);
			error.setDescription("Se han actualizado los fundamentos correctamente");
		}

		updateResponseDTO.setError(error);

		LOGGER.info("activateFundamentos() -> Salida del servicio para dar de alta a los fundamentos");
		return updateResponseDTO;
	}


	@Override
	public InsertResponseDTO insertFundamentos(FundamentosCalificacionItem fundamentosCalificacionItem, HttpServletRequest request) {
		
		LOGGER.info("insertFundamentos() ->  Entrada al servicio para crear un nuevo fundamento");

		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int response = 0;

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		short idFundamento = 0;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"insertFundamentos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"insertFundamentos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					LOGGER.info(
							"insertFundamentos() / scsFundamentoscalificacionExtendsMapper.insert() -> Entrada a scsFundamentoscalificacionExtendsMapper para insertar el nuevo juzgado");
					GenRecursosCatalogosExample genRecursosCatalogosExample = new GenRecursosCatalogosExample();
					genRecursosCatalogosExample.createCriteria().andIdinstitucionEqualTo(Short.valueOf(idInstitucion)).
						andDescripcionEqualTo(fundamentosCalificacionItem.getDescripcionFundamento());
					List<GenRecursosCatalogos> l = genRecursosCatalogosExtendsMapper.selectByExample(genRecursosCatalogosExample);
					
					
					if (l != null && l.size() > 0) {
						error.setCode(400);
						error.setDescription("general.existeDato");

					} else {
					
						ScsTipofundamentocalif fundamento = new ScsTipofundamentocalif();

						
						fundamento.setFechabaja(null);
						fundamento.setFechamodificacion(new Date());
						fundamento.setUsumodificacion(usuario.getIdusuario().intValue());
						fundamento.setIdinstitucion(idInstitucion);
						fundamento.setTextoplantilla(fundamentosCalificacionItem.getTextoEnPlantilla());
						fundamento.setCodigo(fundamentosCalificacionItem.getCodigo());
						fundamento.setCodigoejis(fundamentosCalificacionItem.getCodigoEjis());
						
						fundamento.setBloqueado("N");
						fundamento.setIdtipodictamenejg(Short.valueOf(fundamentosCalificacionItem.getIdTipoDictamenEjg()));
						
												
						GenRecursosCatalogos genRecursosCatalogos = new GenRecursosCatalogos();
						genRecursosCatalogos.setDescripcion(fundamentosCalificacionItem.getDescripcionFundamento());
						genRecursosCatalogos.setFechamodificacion(new Date());
						genRecursosCatalogos.setUsumodificacion(usuario.getIdusuario());
						genRecursosCatalogos.setIdinstitucion(idInstitucion);
						genRecursosCatalogos.setIdlenguaje(usuario.getIdlenguaje());
						genRecursosCatalogos.setIdrecursoalias("scs_tipofundamentos.descripcion." + idInstitucion
								+ "." + genRecursosCatalogos.getIdrecurso());
						genRecursosCatalogos.setNombretabla("SCS_TIPOFUNDAMENTOCALIF");
						genRecursosCatalogos.setCampotabla("DESCRIPCION");
						NewIdDTO idJD = genRecursosCatalogosExtendsMapper.getMaxIdRecursoCatalogo(idInstitucion.toString(), usuario.getIdlenguaje());
						NewIdDTO idJ = scsFundamentoscalificacionExtendsMapper.getIdFundamento(usuario.getIdinstitucion(), usuario.getIdlenguaje());

						
						
						
						if (idJ == null) {
							fundamento.setIdfundamentocalif((short)1);
						} else {
							idFundamento =(short) (Short.valueOf(idJ.getNewId())+1);
							fundamento.setIdfundamentocalif(idFundamento);
						}
						if (idJD == null) {
							genRecursosCatalogos.setIdrecurso("1");
							
						} else {
							genRecursosCatalogos.setIdrecurso((Long.parseLong(idJD.getNewId())+1)+"");
							fundamento.setDescripcion(genRecursosCatalogos.getIdrecurso());
						}

						
						
							response = scsFundamentoscalificacionExtendsMapper.insert(fundamento);
							insertResponseDTO.setId(fundamento.getIdfundamentocalif().toString());
	
							LOGGER.info(
									"insertFundamentos() / scsFundamentoscalificacionExtendsMapper.insert() -> Salida de scsFundamentoscalificacionExtendsMapper para insertar el nuevo fundamento");
	
	
							LOGGER.info(
									"insertFundamentos() / scsFundamentoscalificacionExtendsMapper.insert() -> Entrada a scsFundamentoscalificacionExtendsMapper para insertar el nuevo fundamento");
	
							genRecursosCatalogosExtendsMapper.insert(genRecursosCatalogos);
							insertarRestoIdiomas(genRecursosCatalogos);
						
						LOGGER.info(
								"insertFundamentos() / scsFundamentoscalificacionExtendsMapper.insert() -> Salida de scsFundamentoscalificacionExtendsMapper para insertar el nuevo fundamento");

					}
				} catch (Exception e) {
					response = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en BBDD contacte con su administrador");
					insertResponseDTO.setStatus(SigaConstants.KO);
				}
			}

		}

		if (response == 0) {
			error.setCode(400);
			insertResponseDTO.setStatus(SigaConstants.KO);
		} else if (error.getCode() == null) {
			error.setCode(200);
			insertResponseDTO.setId(String.valueOf(idFundamento));
			error.setDescription("Se ha creado el fundamento correctamente");
		}

		insertResponseDTO.setError(error);

		LOGGER.info("insertFundamentos() -> Salida del servicio para crear un nuevo grupo zona");
		
		return insertResponseDTO;

	}
	
	@Override
	public UpdateResponseDTO updateFundamentosCalificacion(FundamentosCalificacionItem fundamentosCalificacionItem, HttpServletRequest request) {
		LOGGER.info("updateFundamentosCalificacion() ->  Entrada al servicio para editar fundamento");

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
					"updateFundamentosCalificacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"updateFundamentosCalificacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

				try {

					// Obtenemos el fundamento de resolucion que queremos modificar
					ScsTipofundamentocalifExample example = new ScsTipofundamentocalifExample();
					example.createCriteria().andIdinstitucionEqualTo(idInstitucion).
							andIdfundamentocalifEqualTo(Short.valueOf(fundamentosCalificacionItem.getIdFundamento()));
//							.andFechabajaIsNull();

					LOGGER.info(
							"updateFundamentoResolucion() / scsTipofundamentosExtendsMapper.selectByExample(example) -> Entrada a scsTipofundamentosExtendsMapper para buscar un fundamento resolucion");

					List<ScsTipofundamentocalif> scsTipofundamentoscalifList = scsFundamentoscalificacionExtendsMapper
							.selectByExample(example);

					LOGGER.info(
							"updateFundamentoResolucion() / scsTipofundamentosExtendsMapper.selectByExample(example) -> Salida a scsTipofundamentosExtendsMapper para buscar  un fundamento resolucion");

					if (scsTipofundamentoscalifList != null && scsTipofundamentoscalifList.size() > 0) {

						ScsTipofundamentocalif scsTipofundamentocalif = scsTipofundamentoscalifList.get(0);

						// Buscamos si existe una descripcion que sea igual en fundamentos q no sea el
						// propio

						GenRecursosCatalogosExample exampleRecursosRepetidos = new GenRecursosCatalogosExample();
						exampleRecursosRepetidos.createCriteria()
								.andDescripcionEqualTo(fundamentosCalificacionItem.getDescripcionFundamento())
								.andCampotablaEqualTo("DESCRIPCION").andNombretablaEqualTo("SCS_TIPOFUNDAMENTOCALIF")
								.andIdinstitucionEqualTo(idInstitucion)
								.andIdrecursoNotEqualTo(scsTipofundamentocalif.getDescripcion());

						List<GenRecursosCatalogos> recursosRepetidos = genRecursosCatalogosExtendsMapper
								.selectByExample(exampleRecursosRepetidos);

						// Si la descripcion se repite
						if (recursosRepetidos != null && recursosRepetidos.size() > 0) {
							error.setCode(400);
							error.setDescription("messages.jgr.maestros.gestionFundamentosResolucion.existeFundamentosResolucionMismaDescripcion");
						} else {
						ScsTipofundamentocalifExample scsTipofundamentocalifExample = new ScsTipofundamentocalifExample();
						scsTipofundamentocalifExample.createCriteria().andIdfundamentocalifEqualTo(Short.valueOf(fundamentosCalificacionItem.getIdFundamento()))
								.andIdinstitucionEqualTo(idInstitucion);

						LOGGER.info(
								"updateFundamentosCalificacion() / scsFundamentoscalificacionExtendsMapper.selectByExample() -> Entrada a scsFundamentoscalificacionExtendsMapper para buscar el fundamento");

						List<ScsTipofundamentocalif> scsFundamentosList = scsFundamentoscalificacionExtendsMapper.selectByExample(scsTipofundamentocalifExample);

						LOGGER.info(
								"updateFundamentosCalificacion() / scsFundamentoscalificacionExtendsMapper.selectByExample() -> Salida a scsFundamentoscalificacionExtendsMapper para buscar el fundamento");


						ScsTipofundamentocalif fundamento = scsFundamentosList.get(0);

						fundamento.setCodigo(fundamentosCalificacionItem.getCodigo());
						fundamento.setFechamodificacion(new Date());
						fundamento.setUsumodificacion(usuario.getIdusuario().intValue());
						
						fundamento.setTextoplantilla(fundamentosCalificacionItem.getTextoEnPlantilla());
						fundamento.setIdtipodictamenejg(Short.valueOf(fundamentosCalificacionItem.getIdTipoDictamenEjg()));

						GenRecursosCatalogos genRecursosCatalogos = new GenRecursosCatalogos();
						genRecursosCatalogos.setIdrecurso(fundamento.getDescripcion());
						genRecursosCatalogos.setIdinstitucion(idInstitucion);
						genRecursosCatalogos.setIdlenguaje(usuarios.get(0).getIdlenguaje());
						genRecursosCatalogos = genRecursosCatalogosExtendsMapper
								.selectByPrimaryKey(genRecursosCatalogos);
						genRecursosCatalogos.setFechamodificacion(new Date());
						genRecursosCatalogos.setUsumodificacion(usuario.getIdusuario().intValue());
						genRecursosCatalogos.setDescripcion(fundamentosCalificacionItem.getDescripcionFundamento());
						response = scsFundamentoscalificacionExtendsMapper.updateByPrimaryKey(fundamento);

						genRecursosCatalogosExtendsMapper.updateByPrimaryKey(genRecursosCatalogos);

						
						updateRestoIdiomas(genRecursosCatalogos);
							
						updateResponseDTO.setId(fundamento.getIdfundamentocalif().toString());
				
							
						
						}}}
				catch (Exception e) {
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
		} else if(response == 1){
			error.setCode(200);
			
		}

		updateResponseDTO.setError(error);

		LOGGER.info("updateFundamentosCalificacion() -> Salida del servicio para editar fundamento");
		return updateResponseDTO;
	}


	//INSERTAR GEN RECURSOS CATALOGOS
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

	
  //MODIFICAR GEN RECURSOS CATALOGOS
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
