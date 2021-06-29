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
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TiposServiciosServiceImpl implements ITiposServiciosService {
	private Logger LOGGER = Logger.getLogger(TiposProductosServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private PysServiciosMapper pysServiciosMapper;
	
//	@Autowired
//	private PySTiposServiciosExtendsMapper pysTiposServiciosExtendsMapper;
	
	@Autowired
	PysServiciosExtendsMapper pysServiciosExtendsMapper;
	
	@Override
	public ListadoTipoServicioDTO searchTiposServicios(HttpServletRequest request) {
		ListadoTipoServicioDTO listadoTipoServicioDTO = new ListadoTipoServicioDTO();
		Error error = new Error();

		LOGGER.info("searchTiposServicios() -> Entrada al servicio para recuperar el listado de servicios");

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
							"searchTiposServicios() / pysServiciosExtendsMapper.searchTiposServicios() -> Entrada a pysServiciosExtendsMapper para obtener el listado de servicios");

					String idioma = usuarios.get(0).getIdlenguaje();
					List<TiposServiciosItem> listaServicios = pysServiciosExtendsMapper
							.searchTiposServicios(idioma, idInstitucion);

					LOGGER.info(
							"searchTiposServicios() / pysServiciosExtendsMapper.searchTiposServicios() -> Salida de pysServiciosExtendsMapper para obtener el listado de servicios");

					if (listaServicios != null && listaServicios.size() > 0) {
						listadoTipoServicioDTO.setTiposServiciosItems(listaServicios);
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"TiposServiciosServiceImpl.searchTiposServicios() -> Se ha producido un error al obtener el listado de servicios",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		listadoTipoServicioDTO.setError(error);

		LOGGER.info("searchTiposServicios() -> Salida del servicio para obtener el listado de servicios");

		return listadoTipoServicioDTO;
	}

	@Override
	public ListadoTipoServicioDTO searchTiposServiciosHistorico(HttpServletRequest request) {
		ListadoTipoServicioDTO listadoTipoServicioDTO = new ListadoTipoServicioDTO();
		Error error = new Error();

		LOGGER.info("searchTiposServiciosHistorico() -> Entrada al servicio para recuperar el listado de servicios historico");

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
							"searchTiposServiciosHistorico() / pysServiciosExtendsMapper.searchTiposServiciosHistorico() -> Entrada a pysServiciosExtendsMapper para obtener el listado de servicios historico");

					String idioma = usuarios.get(0).getIdlenguaje();
					List<TiposServiciosItem> listaServicios = pysServiciosExtendsMapper
							.searchTiposServiciosHistorico(idioma, idInstitucion);

					LOGGER.info(
							"searchTiposServiciosHistorico() / pysServiciosExtendsMapper.searchTiposServiciosHistorico() -> Salida de pysServiciosExtendsMapper para obtener el listado de servicios historico");

					if (listaServicios != null && listaServicios.size() > 0) {
						listadoTipoServicioDTO.setTiposServiciosItems(listaServicios);
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"TiposServiciosServiceImpl.searchTiposServiciosHistorico() -> Se ha producido un error al obtener el listado de servicios historico",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		listadoTipoServicioDTO.setError(error);

		LOGGER.info("searchTiposServiciosHistorico() -> Salida del servicio para obtener el listado de servicios historico");

		return listadoTipoServicioDTO;
	}

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

	@Override
	public InsertResponseDTO crearServicio(ListadoTipoServicioDTO listadoServicios, HttpServletRequest request) {
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int status = 0;
		

		LOGGER.info("crearServicio() -> Entrada al servicio para crear un servicio");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"crearServicio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"crearServicio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"crearServicio() / pysServiciosExtendsMapper.crearProducto() -> Entrada a pysServiciosExtendsMapper para crear un servicio");

					NewIdDTO idOrdenacion = pysServiciosExtendsMapper.getIndiceMaxServicio(listadoServicios.getTiposServiciosItems(), idInstitucion);
					PysServicios servicio = new PysServicios();
			
					
					servicio.setIdinstitucion(idInstitucion);
					servicio.setIdtiposervicios(Short.parseShort(String.valueOf(listadoServicios.getTiposServiciosItems().get(0).getIdtiposervicios())));
					servicio.setIdservicio(Long.parseLong(idOrdenacion.getNewId().toString()));
					servicio.setDescripcion(listadoServicios.getTiposServiciosItems().get(0).getDescripcion());
					servicio.setFechamodificacion(new Date());
					servicio.setUsumodificacion(usuarios.get(0).getIdusuario());
					servicio.setFechabaja(null);
					
					status = pysServiciosMapper.insert(servicio);
					
					if(status == 0) {
						insertResponseDTO.setStatus(SigaConstants.KO);
					}else if(status == 1) {
						insertResponseDTO.setStatus(SigaConstants.OK);
					}
					
					LOGGER.info(
							"crearServicio() / pysServiciosExtendsMapper.crearServicio() -> Salida de pysServiciosExtendsMapper para crear un servicio");
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"TiposServiciosServiceImpl.crearServicio() -> Se ha producido un error al crear un servicio",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}
		
		insertResponseDTO.setError(error);

		LOGGER.info("crearServicio() -> Salida del servicio para crear un servicio");

		return insertResponseDTO;
	}

	@Override
	public DeleteResponseDTO modificarServicio(ListadoTipoServicioDTO listadoServicios, HttpServletRequest request) {
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		int status = 0;
		

		LOGGER.info("modificarServicio() -> Entrada al servicio para modificar un servicio");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"modificarServicio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"modificarServicio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"modificarServicio() / pysServiciosExtendsMapper.modificarServicio() -> Entrada a pysServiciosExtendsMapper para modificar un servicio");
					
					for (TiposServiciosItem servicio : listadoServicios.getTiposServiciosItems()) {
						PysServicios servicioMyBatis = new PysServicios();
						
						
						servicioMyBatis.setIdinstitucion(idInstitucion);
						servicioMyBatis.setIdtiposervicios(Short.parseShort(String.valueOf(servicio.getIdtiposervicios())));
						servicioMyBatis.setIdservicio(new Long(servicio.getIdservicio()));
						servicioMyBatis.setDescripcion(servicio.getDescripcion());
						servicioMyBatis.setFechamodificacion(new Date());
						servicioMyBatis.setUsumodificacion(usuarios.get(0).getIdusuario());
						servicioMyBatis.setFechabaja(null);
						
						status = pysServiciosMapper.updateByPrimaryKey(servicioMyBatis);
					}
					
					
					if(status == 0) {
						deleteResponseDTO.setStatus(SigaConstants.KO);
					}else if(status == 1) {
						deleteResponseDTO.setStatus(SigaConstants.OK);
					}
					

					LOGGER.info(
							"modificarServicio() / pysServiciosExtendsMapper.modificarServicio() -> Salida de pysServiciosExtendsMapper para modificar un servicio");
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"TiposServiciosServiceImpl.modificarServicio() -> Se ha producido un error al modificar un servicio",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		
		deleteResponseDTO.setError(error);

		LOGGER.info("modificarServicio() -> Salida del servicio para modificar un servicio");

		return deleteResponseDTO;
	}
	
	@Override
	public ServicioDTO activarDesactivarServicio(ListadoTipoServicioDTO listadoServicios, HttpServletRequest request) {
		ServicioDTO servicioDTO = new ServicioDTO();
		Error error = new Error();
		int status = 0;
		

		LOGGER.info("activarDesactivarServicio() -> Entrada al servicio para activar/desactivar servicios");

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
							"activarDesactivarServicio() / pysServiciosExtendsMapper.activarDesactivarServicio() -> Entrada a pysServiciosExtendsMapper para activar/desactivar servicios");

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
							"activarDesactivarServicio() / pysServiciosExtendsMapper.activarDesactivarServicio() -> Salida de pysServiciosExtendsMapper para activar/desactivar servicios");
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"TiposServiciosServiceImpl.activarDesactivarServicio() -> Se ha producido un error al activar/desactivar servicios",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		
		servicioDTO.setError(error);

		LOGGER.info("activarDesactivarServicio() -> Salida del servicio para activar/desactivar servicios");

		return servicioDTO;
	}

}


