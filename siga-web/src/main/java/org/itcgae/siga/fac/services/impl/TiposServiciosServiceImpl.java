package org.itcgae.siga.fac.services.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.ListadoTipoProductoDTO;
import org.itcgae.siga.DTO.fac.ListadoTipoServicioDTO;
import org.itcgae.siga.DTO.fac.ServicioDTO;
import org.itcgae.siga.DTO.fac.TiposProductosItem;
import org.itcgae.siga.DTO.fac.TiposServiciosItem;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.PysProductos;
import org.itcgae.siga.db.entities.PysServicios;
import org.itcgae.siga.db.mappers.PysServiciosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysServiciosExtendsMapper;
import org.itcgae.siga.fac.services.ITiposServiciosService;
import org.itcgae.siga.security.CgaeAuthenticationProvider;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TiposServiciosServiceImpl implements ITiposServiciosService {
	private Logger LOGGER = Logger.getLogger(TiposServiciosServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private PysServiciosMapper pysServiciosMapper;
	
	@Autowired
	PysServiciosExtendsMapper pysServiciosExtendsMapper;
	
    @Autowired
    private CgaeAuthenticationProvider authenticationProvider;
	
	//Datos tabla pantalla Maestros --> Tipos Servicios
	@Override
	public ListadoTipoServicioDTO searchTiposServicios(HttpServletRequest request) {
		ListadoTipoServicioDTO listadoTipoServicioDTO = new ListadoTipoServicioDTO();
		Error error = new Error();

		LOGGER.info("searchTiposServicios() -> Entrada al servicio para recuperar el listado de tipos de servicios");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"searchTiposServicios() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"searchTiposServicios() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"searchTiposServicios() / pysServiciosExtendsMapper.searchTiposServicios() -> Entrada a pysServiciosExtendsMapper para obtener el listado de tipos de servicios");

					String idioma = usuarios.get(0).getIdlenguaje();
					List<TiposServiciosItem> listaServicios = pysServiciosExtendsMapper
							.searchTiposServicios(idioma, idInstitucion);

					LOGGER.info(
							"searchTiposServicios() / pysServiciosExtendsMapper.searchTiposServicios() -> Salida de pysServiciosExtendsMapper para obtener el listado de tipos de servicios");

					if (listaServicios != null && listaServicios.size() > 0) {
						listadoTipoServicioDTO.setTiposServiciosItems(listaServicios);
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"TiposServiciosServiceImpl.searchTiposServicios() -> Se ha producido un error al obtener el listado de tipos de servicios",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		listadoTipoServicioDTO.setError(error);

		LOGGER.info("searchTiposServicios() -> Salida del servicio para obtener el listado de tipos de servicios");

		return listadoTipoServicioDTO;
	}

	//Datos con historico (incluidos registros con fechabaja != null) tabla pantalla Maestros --> Tipos Servicios
	@Override
	public ListadoTipoServicioDTO searchTiposServiciosHistorico(HttpServletRequest request) {
		ListadoTipoServicioDTO listadoTipoServicioDTO = new ListadoTipoServicioDTO();
		Error error = new Error();

		LOGGER.info("searchTiposServiciosHistorico() -> Entrada al servicio para recuperar el listado de tipos de servicios historico");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"searchTiposServiciosHistorico() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"searchTiposServiciosHistorico() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"searchTiposServiciosHistorico() / pysServiciosExtendsMapper.searchTiposServiciosHistorico() -> Entrada a pysServiciosExtendsMapper para obtener el listado de tipos de servicios historico");

					String idioma = usuarios.get(0).getIdlenguaje();
					List<TiposServiciosItem> listaServicios = pysServiciosExtendsMapper
							.searchTiposServiciosHistorico(idioma, idInstitucion);

					LOGGER.info(
							"searchTiposServiciosHistorico() / pysServiciosExtendsMapper.searchTiposServiciosHistorico() -> Salida de pysServiciosExtendsMapper para obtener el listado de tipos de servicios historico");

					if (listaServicios != null && listaServicios.size() > 0) {
						listadoTipoServicioDTO.setTiposServiciosItems(listaServicios);
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"TiposServiciosServiceImpl.searchTiposServiciosHistorico() -> Se ha producido un error al obtener el listado de tipos de servicios historico",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		listadoTipoServicioDTO.setError(error);

		LOGGER.info("searchTiposServiciosHistorico() -> Salida del servicio para obtener el listado de tipos de servicios historico");

		return listadoTipoServicioDTO;
	}

	//Obtiene los datos del combo categoria de servicios (PYS_TIPOSERVICIOS)
	@Override
	public ComboDTO comboTiposServicios(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();
		Error error = new Error();

		LOGGER.info("comboTiposServicios() -> Entrada al servicio para recuperar el combo de tipos de servicios");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"comboTiposServicios() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"comboTiposServicios() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"comboTiposServicios() / pysServiciosExtendsMapper.comboTiposServicios() -> Entrada a pysServiciosExtendsMapper para recuperar el combo de tipos de servicios");

					String idioma = usuarios.get(0).getIdlenguaje();
					List<ComboItem> listaComboTiposServicios = pysServiciosExtendsMapper
							.comboTiposServicios(idioma);

					LOGGER.info(
							"comboTiposServicios() / pysServiciosExtendsMapper.comboTiposServicios() -> Salida de pysServiciosExtendsMapper para recuperar el combo de tipos de servicios");

					if (listaComboTiposServicios != null && listaComboTiposServicios.size() > 0) {
						comboDTO.setCombooItems(listaComboTiposServicios);
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"TiposServiciosServiceImpl.comboTiposServicios() -> Se ha producido un error al recuperar el combo de tipos de servicios",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info("comboTiposServicios() -> Salida del servicio para recuperar el combo de tipos de servicios");

		return comboDTO;
	}
	
    //Metodo que crea y edita tipos de servicios (PYS_SERVICIOS)
    @Override
    public DeleteResponseDTO crearEditarServicio(ListadoTipoServicioDTO listadoServicios, HttpServletRequest request) throws Exception {
        
        DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
        //Obtenemos la informacion del usuario logeado
        AdmUsuarios usuario = new AdmUsuarios();
        int status = 0;
        

        LOGGER.info("crearEditarServicio() -> Entrada al servicio para crear/modificar un tipo de servicio (PYS_SERVICIOS)");

        usuario = authenticationProvider.checkAuthentication(request);
        
        if (usuario != null) {

            LOGGER.info(
                "crearEditarServicio() / pysServiciosMapper.crearEditarServicio() -> Entrada a pysServiciosMapper para crear/modificar un tipo de servicio (PYS_SERVICIOS)");
                    
            for (TiposServiciosItem tipoServicio : listadoServicios.getTiposServiciosItems()) {
                
                PysServicios tipoServicioMyBattis = new PysServicios();
                        
                tipoServicioMyBattis.setIdinstitucion(usuario.getIdinstitucion());
                tipoServicioMyBattis.setIdtiposervicios(Short.parseShort(String.valueOf(tipoServicio.getIdtiposervicios())));
                
                if(tipoServicio.isNuevo()) {
                    NewIdDTO idOrdenacion = pysServiciosExtendsMapper.getIndiceMaxTipoServicio(tipoServicio.getIdtiposervicios(), usuario.getIdinstitucion());
                    tipoServicioMyBattis.setIdservicio(Long.parseLong(idOrdenacion.getNewId().toString()));
                }else {
                	tipoServicioMyBattis.setIdservicio(new Long(tipoServicio.getIdservicio()));
                }
                tipoServicioMyBattis.setDescripcion(tipoServicio.getDescripcion());
                tipoServicioMyBattis.setFechamodificacion(new Date());
                tipoServicioMyBattis.setUsumodificacion(usuario.getIdusuario());
                
                if(tipoServicio.isNuevo()) {        
                    status = pysServiciosMapper.insertSelective(tipoServicioMyBattis);
                }else {
                    status = pysServiciosMapper.updateByPrimaryKeySelective(tipoServicioMyBattis);
                }
                
                if(status == 0) {                
                    deleteResponseDTO.setStatus(SigaConstants.KO);
                    LOGGER.info(
                            "Actualizacion/insercion fallida del tipo de servicio con id: " + tipoServicio.getIdservicio() + ", descripcion: " + tipoServicio.getDescripcion());
                }else if(status == 1) {
                    LOGGER.info(
                        "crearEditarServicio() / pysServiciosMapper.crearEditarServicio() -> Actualizacion/insercion exitosa del tipo de servicio con id: " + tipoServicio.getIdservicio() + ", descripcion: " + tipoServicio.getDescripcion());
                    deleteResponseDTO.setStatus(SigaConstants.OK);
                }
                
            }    
                    
            LOGGER.info(
                    "crearEditarServicio() / pysServiciosMapper.crearEditarServicio() -> Salida de pysServiciosMapper para crear/modificar un tipo de servicio (PYS_SERVICIOS)");
        }
        
        LOGGER.info("crearEditarServicio() -> Salida del servicio para crear/modificar un tipo de servicio (PYS_SERVICIOS)");
        
        return deleteResponseDTO;
    }
    
    //Realiza un borrado logico (establecer fechabaja = new Date()) o lo reactiva en caso de que esta inhabilitado.
	@Override
	public ServicioDTO activarDesactivarServicio(ListadoTipoServicioDTO listadoServicios, HttpServletRequest request) {
		ServicioDTO servicioDTO = new ServicioDTO();
		Error error = new Error();
		int status = 0;
		

		LOGGER.info("activarDesactivarServicio() -> Entrada al servicio para activar/desactivar tipos de servicios");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"activarDesactivarServicio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"activarDesactivarServicio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"activarDesactivarServicio() / pysServiciosExtendsMapper.activarDesactivarServicio() -> Entrada a pysServiciosExtendsMapper para activar/desactivar tipos de servicios");

					for (TiposServiciosItem servicio : listadoServicios.getTiposServiciosItems()) {
						status = pysServiciosExtendsMapper
								.activarDesactivarServicio(usuarios.get(0), idInstitucion, servicio);
					}
					
					if(status == 0) {
						servicioDTO.setStatus(SigaConstants.KO);
					}else if(status == 1) {
						servicioDTO.setStatus(SigaConstants.OK);
					}
					

					LOGGER.info(
							"activarDesactivarServicio() / pysServiciosExtendsMapper.activarDesactivarServicio() -> Salida de pysServiciosExtendsMapper para activar/desactivar tipos de servicios");
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"TiposServiciosServiceImpl.activarDesactivarServicio() -> Se ha producido un error al activar/desactivar tipos de servicios",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		
		servicioDTO.setError(error);

		LOGGER.info("activarDesactivarServicio() -> Salida del servicio para activar/desactivar tipos de servicios");

		return servicioDTO;
	}
	
	@Override
	public ComboDTO searchTiposServiciosByIdCategoria(HttpServletRequest request, String idCategoria) {
		ComboDTO comboDTO = new ComboDTO();
		Error error = new Error();

		LOGGER.info("searchTiposServiciosByIdCategoria() -> Entrada al servicio para recuperar el combo de servicios segun categoria");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"searchTiposServiciosByIdCategoria() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"searchTiposServiciosByIdCategoria() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"searchTiposProductosByIdCategoria() / pysServiciosExtendsMapper.searchTiposServiciosByIdCategoria() -> Entrada a pysServiciosExtendsMapper para obtener el combo de servicios segun categoria");

					String idioma = usuarios.get(0).getIdlenguaje();
					List<ComboItem> listaComboTiposServicios = pysServiciosExtendsMapper
							.searchTiposServiciosByIdCategoria(idioma, idInstitucion, idCategoria);

					LOGGER.info(
							"searchTiposServiciosByIdCategoria() / pysServiciosExtendsMapper.searchTiposServiciosByIdCategoria() -> Salida de pysServiciosExtendsMapper para obtener el combo de servicios segun categoria");

					if (listaComboTiposServicios != null && listaComboTiposServicios.size() > 0) {
						comboDTO.setCombooItems(listaComboTiposServicios);
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"TiposServiciosServiceImpl.searchTiposServiciosByIdCategoria() -> Se ha producido un error al obtener el combo de servicios segun categoria",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info("searchTiposServiciosByIdCategoria() -> Salida del servicio para obtener el combo de servicios segun categoria");

		return comboDTO;
	}
	
	@Override
	public ComboDTO searchTiposServiciosByIdCategoriaMultiple(HttpServletRequest request, String idCategoria) {
		ComboDTO comboDTO = new ComboDTO();
		Error error = new Error();

		LOGGER.info("searchTiposServiciosByIdCategoriaMultiple() -> Entrada al servicio para recuperar el combo de servicios segun multiples categorias");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"searchTiposServiciosByIdCategoriaMultiple() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"searchTiposServiciosByIdCategoriaMultiple() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"searchTiposProductosByIdCategoria() / pysServiciosExtendsMapper.searchTiposServiciosByIdCategoria() -> Entrada a pysServiciosExtendsMapper para obtener el combo de servicios segun multiples categorias");

					String idioma = usuarios.get(0).getIdlenguaje();
					List<ComboItem> listaComboTiposServicios = pysServiciosExtendsMapper
							.searchTiposServiciosByIdCategoriaMultiple(idioma, idInstitucion, idCategoria);

					LOGGER.info(
							"searchTiposServiciosByIdCategoriaMultiple() / pysServiciosExtendsMapper.searchTiposServiciosByIdCategoria() -> Salida de pysServiciosExtendsMapper para obtener el combo de servicios segun multiples categorias");

					if (listaComboTiposServicios != null && listaComboTiposServicios.size() > 0) {
						comboDTO.setCombooItems(listaComboTiposServicios);
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"TiposServiciosServiceImpl.searchTiposServiciosByIdCategoriaMultiple() -> Se ha producido un error al obtener el combo de servicios de multiples categorias",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info("searchTiposServiciosByIdCategoriaMultiple() -> Salida del servicio para obtener el combo de servicios de multiples categorias");

		return comboDTO;
	}
	

}


