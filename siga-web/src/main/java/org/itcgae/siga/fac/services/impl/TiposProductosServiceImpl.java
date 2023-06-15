package org.itcgae.siga.fac.services.impl;

import java.util.Date;
import java.util.List;


import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.ListadoTipoProductoDTO;
import org.itcgae.siga.DTO.fac.ProductoDTO;
import org.itcgae.siga.DTO.fac.TiposProductosItem;
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
import org.itcgae.siga.db.mappers.PysProductosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTiposProductosExtendsMapper;
import org.itcgae.siga.fac.services.ITiposProductosService;
import org.itcgae.siga.security.CgaeAuthenticationProvider;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TiposProductosServiceImpl implements ITiposProductosService {

	private Logger LOGGER = Logger.getLogger(TiposProductosServiceImpl.class);

	@Autowired
	private PySTiposProductosExtendsMapper pysTiposProductosExtendsMapper;
	
	@Autowired
	private PysProductosMapper pysProductosMapper;
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CgaeAuthenticationProvider authenticationProvider;

	
	//Datos tabla pantalla Maestros --> Tipos Productos
	@Override
	public ListadoTipoProductoDTO searchTiposProductos(HttpServletRequest request) {
		ListadoTipoProductoDTO listadoTipoProductoDTO = new ListadoTipoProductoDTO();
		Error error = new Error();

		LOGGER.info("searchTiposProductos() -> Entrada al servicio para recuperar el listado de tipos de productos");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"searchTiposProductos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"searchTiposProductos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"searchTiposProductos() / pysTiposProductosExtendsMapper.searchTiposProductos() -> Entrada a pysTiposProductosExtendsMapper para obtener el listado de tipos de productos");

					String idioma = usuarios.get(0).getIdlenguaje();
					List<TiposProductosItem> listaProductos = pysTiposProductosExtendsMapper
							.searchTiposProductos(idioma, idInstitucion);

					LOGGER.info(
							"searchTiposProductos() / pysTiposProductosExtendsMapper.searchTiposProductos() -> Salida de pysTiposProductosExtendsMapper para obtener el listado de tipos de productos");

					if (listaProductos != null && listaProductos.size() > 0) {
						listadoTipoProductoDTO.setTiposProductosItems(listaProductos);
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"TiposProductosServiceImpl.searchTiposProductos() -> Se ha producido un error al obtener el listado de tipos de productos",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		listadoTipoProductoDTO.setError(error);

		LOGGER.info("searchTiposProductos() -> Salida del servicio para obtener el listado de tipos de productos");

		return listadoTipoProductoDTO;
	}
	
	//Datos con historico (incluidos registros con fechabaja != null) tabla pantalla Maestros --> Tipos Productos
	@Override
	public ListadoTipoProductoDTO searchTiposProductosHistorico(HttpServletRequest request) {
		ListadoTipoProductoDTO listadoTipoProductoDTO = new ListadoTipoProductoDTO();
		Error error = new Error();

		LOGGER.info("searchTiposProductosHistorico() -> Entrada al servicio para recuperar el listado de tipos de productos historico");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"searchTiposProductosHistorico() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"searchTiposProductosHistorico() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"searchTiposProductosHistorico() / pysTiposProductosExtendsMapper.searchTiposProductosHistorico() -> Entrada a pysTiposProductosExtendsMapper para obtener el listado de tipos de productos historico");

					String idioma = usuarios.get(0).getIdlenguaje();
					List<TiposProductosItem> listaProductos = pysTiposProductosExtendsMapper
							.searchTiposProductosHistorico(idioma, idInstitucion);

					LOGGER.info(
							"searchTiposProductosHistorico() / pysTiposProductosExtendsMapper.searchTiposProductosHistorico() -> Salida de pysTiposProductosExtendsMapper para obtener el listado de tipos de productos historico");

					if (listaProductos != null && listaProductos.size() > 0) {
						listadoTipoProductoDTO.setTiposProductosItems(listaProductos);
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"TiposProductosServiceImpl.searchTiposProductosHistorico() -> Se ha producido un error al obtener el listado de tipos de productos historico",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		listadoTipoProductoDTO.setError(error);

		LOGGER.info("searchTiposProductosHistorico() -> Salida del servicio para obtener el listado de tipos de productos historico");

		return listadoTipoProductoDTO;
	}
	
	//Obtiene los datos del combo categoria de productos (PYS_TIPOSPRODUCTOS)
	@Override
	public ComboDTO comboTiposProductos(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();
		Error error = new Error();

		LOGGER.info("comboTiposProductos() -> Entrada al servicio para recuperar el combo de tipos de productos");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"comboTiposProductos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"searchTiposProductos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"comboTiposProductos() / pysTiposProductosExtendsMapper.comboTiposProductos() -> Entrada a pysTiposProductosExtendsMapper para recuperar el combo de tipos de productos");

					String idioma = usuarios.get(0).getIdlenguaje();
					List<ComboItem> listaComboTiposProductos = pysTiposProductosExtendsMapper
							.comboTiposProductos(idInstitucion,idioma);

					LOGGER.info(
							"searchTiposProductos() / pysTiposProductosExtendsMapper.comboTiposProductos() -> Salida de pysTiposProductosExtendsMapper para recuperar el combo de tipos de productos");

					if (listaComboTiposProductos != null && listaComboTiposProductos.size() > 0) {
						comboDTO.setCombooItems(listaComboTiposProductos);
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"TiposProductosServiceImpl.comboTiposProductos() -> Se ha producido un error al recuperar el combo de tipos de productos",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info("comboTiposProductos() -> Salida del servicio para recuperar el combo de tipos de productos");

		return comboDTO;
	}
	
	//Metodo que crea y edita tipos de productos (PYS_PRODUCTOS)
	@Override
	public DeleteResponseDTO crearEditarProducto(ListadoTipoProductoDTO listadoProductos, HttpServletRequest request) throws Exception {
		
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		//Obtenemos la informacion del usuario logeado
		AdmUsuarios usuario = new AdmUsuarios();
		int status = 0;
		

		LOGGER.info("crearEditarProducto() -> Entrada al servicio para crear/modificar un tipo de producto (PYS_PRODUCTOS)");

		usuario = authenticationProvider.checkAuthentication(request);
		
		if (usuario != null) {

			LOGGER.info(
				"crearEditarProducto() / pysProductosMapper.crearEditarProducto() -> Entrada a pysProductosMapper para crear/modificar un tipo de producto (PYS_PRODUCTOS)");
					
			for (TiposProductosItem tipoProducto : listadoProductos.getTiposProductosItems()) {
				
				PysProductos tipoProductoMyBattis = new PysProductos();
						
				tipoProductoMyBattis.setIdinstitucion(usuario.getIdinstitucion());
				tipoProductoMyBattis.setIdtipoproducto(Short.parseShort(String.valueOf(tipoProducto.getIdtipoproducto())));
				
				if(tipoProducto.isNuevo()) {
					NewIdDTO idOrdenacion = pysTiposProductosExtendsMapper.getIndiceMaxTipoProducto(tipoProducto.getIdtipoproducto(), usuario.getIdinstitucion());
					tipoProductoMyBattis.setIdproducto(Long.parseLong(idOrdenacion.getNewId().toString()));
				}else {
					tipoProductoMyBattis.setIdproducto(new Long(tipoProducto.getIdproducto()));
				}
				tipoProductoMyBattis.setDescripcion(tipoProducto.getDescripcion());
				tipoProductoMyBattis.setFechamodificacion(new Date());
				tipoProductoMyBattis.setUsumodificacion(usuario.getIdusuario());
				
				if(tipoProducto.isNuevo()) {		
					status = pysProductosMapper.insertSelective(tipoProductoMyBattis);
				}else {
					status = pysProductosMapper.updateByPrimaryKeySelective(tipoProductoMyBattis);
				}
				
				if(status == 0) {				
					deleteResponseDTO.setStatus(SigaConstants.KO);
					LOGGER.info(
							"Actualizacion/insercion fallida del tipo de producto con id: " + tipoProducto.getIdproducto() + ", descripcion: " + tipoProducto.getDescripcion());
				}else if(status == 1) {
					LOGGER.info(
						"crearEditarProducto() / pysProductosMapper.crearEditarProducto() -> Actualizacion/insercion exitosa del tipo de producto con id: " + tipoProducto.getIdproducto() + ", descripcion: " + tipoProducto.getDescripcion());
					deleteResponseDTO.setStatus(SigaConstants.OK);
				}
				
			}	
					
			LOGGER.info(
					"crearEditarProducto() / pysProductosMapper.crearEditarProducto() -> Salida de pysProductosMapper para crear/modificar un tipo de producto (PYS_PRODUCTOS)");
		}
		
		LOGGER.info("crearEditarProducto() -> Salida del servicio para crear/modificar un tipo de producto (PYS_PRODUCTOS)");
		
		return deleteResponseDTO;
	}
	
	//Realiza un borrado logico (establecer fechabaja = new Date()) o lo reactiva en caso de que esta inhabilitado.
	@Override
	public ProductoDTO activarDesactivarProducto(ListadoTipoProductoDTO listadoProductos, HttpServletRequest request) {
		ProductoDTO productoDTO = new ProductoDTO();
		Error error = new Error();
		int status = 0;
		

		LOGGER.info("activarDesactivarProducto() -> Entrada al servicio para activar/desactivar tipos de productos");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"activarDesactivarProducto() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"activarDesactivarProducto() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"activarDesactivarProducto() / pysTiposProductosExtendsMapper.activarDesactivarProducto() -> Entrada a pysTiposProductosExtendsMapper para activar/desactivar tipos de productos");

					for (TiposProductosItem producto : listadoProductos.getTiposProductosItems()) {
						status = pysTiposProductosExtendsMapper
								.activarDesactivarProducto(usuarios.get(0), idInstitucion, producto);
					}
					
					if(status == 0) {
						productoDTO.setStatus(SigaConstants.KO);
					}else if(status == 1) {
						productoDTO.setStatus(SigaConstants.OK);
					}
					

					LOGGER.info(
							"activarDesactivarProducto() / pysTiposProductosExtendsMapper.activarDesactivarProducto() -> Salida de pysTiposProductosExtendsMapper para activar/desactivar tipos de productos");
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"TiposProductosServiceImpl.activarDesactivarProducto() -> Se ha producido un error al activar/desactivar tipos de productos",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		
		productoDTO.setError(error);

		LOGGER.info("activarDesactivarProducto() -> Salida del servicio para activar/desactivar tipos de productos");

		return productoDTO;
	}

	@Override
	public ComboDTO searchTiposProductosByIdCategoria(HttpServletRequest request, String idCategoria) {
		ComboDTO comboDTO = new ComboDTO();
		Error error = new Error();

		LOGGER.info("searchTiposProductosByIdCategoria() -> Entrada al servicio para recuperar el combo de productos segun categoria");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"searchTiposProductosByIdCategoria() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"searchTiposProductosByIdCategoria() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"searchTiposProductosByIdCategoria() / pysTiposProductosExtendsMapper.searchTiposProductosByIdCategoria() -> Entrada a pysTiposProductosExtendsMapper para obtener el combo de productos segun categoria");

					String idioma = usuarios.get(0).getIdlenguaje();
					List<ComboItem> listaComboTiposProductos = pysTiposProductosExtendsMapper
							.searchTiposProductosByIdCategoria(idioma, idInstitucion, idCategoria);

					LOGGER.info(
							"searchTiposProductosByIdCategoria() / pysTiposProductosExtendsMapper.searchTiposProductosByIdCategoria() -> Salida de pysTiposProductosExtendsMapper para obtener el combo de productos segun categoria");

					if (listaComboTiposProductos != null && listaComboTiposProductos.size() > 0) {
						comboDTO.setCombooItems(listaComboTiposProductos);
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"TiposProductosServiceImpl.searchTiposProductosByIdCategoria() -> Se ha producido un error al obtener el combo de productos segun categoria",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info("searchTiposProductosByIdCategoria() -> Salida del servicio para obtener el combo de productos segun categoria");

		return comboDTO;
	}

	@Override
	public ComboDTO searchTiposProductosByIdCategoriaMultiple(HttpServletRequest request, String idCategoria) {
		ComboDTO comboDTO = new ComboDTO();
		Error error = new Error();

		LOGGER.debug("searchTiposProductosByIdCategoriaMultiple() -> Entrada al servicio para recuperar el combo de productos segun varias categorias");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"searchTiposProductosByIdCategoriaMultiple() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"searchTiposProductosByIdCategoriaMultiple() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"searchTiposProductosByIdCategoriaMultiple() / pysTiposProductosExtendsMapper.searchTiposProductosByIdCategoriaMultiple() -> Entrada a pysTiposProductosExtendsMapper para obtener el combo de productos segun varias categorias");

					String idioma = usuarios.get(0).getIdlenguaje();
					List<ComboItem> listaComboTiposProductos = pysTiposProductosExtendsMapper
							.searchTiposProductosByIdCategoriaMultiple(idioma, idInstitucion, idCategoria);

					LOGGER.info(
							"searchTiposProductosByIdCategoriaMultiple() / pysTiposProductosExtendsMapper.searchTiposProductosByIdCategoriaMultiple() -> Salida de pysTiposProductosExtendsMapper para obtener el combo de productos segun varias categorias");

					if (listaComboTiposProductos != null && listaComboTiposProductos.size() > 0) {
						comboDTO.setCombooItems(listaComboTiposProductos);
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"TiposProductosServiceImpl.searchTiposProductosByIdCategoriaaMultiple() -> Se ha producido un error al obtener el combo de productos segun varias categorias",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.debug("searchTiposProductosByIdCategoriaMultiple() -> Salida del servicio para obtener el combo de productos segun varias categorias");

		return comboDTO;
	}

}
