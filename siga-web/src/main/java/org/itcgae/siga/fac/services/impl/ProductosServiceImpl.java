package org.itcgae.siga.fac.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.FiltroProductoItem;
import org.itcgae.siga.DTO.fac.IdPeticionDTO;
import org.itcgae.siga.DTO.fac.ListaProductosDTO;
import org.itcgae.siga.DTO.fac.ListaProductosItem;
import org.itcgae.siga.DTO.fac.ListadoTipoProductoDTO;
import org.itcgae.siga.DTO.fac.ProductoDetalleDTO;
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
import org.itcgae.siga.db.entities.PysProductosinstitucion;
import org.itcgae.siga.db.mappers.PysProductosMapper;
import org.itcgae.siga.db.mappers.PysProductosinstitucionMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTipoFormaPagoExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTipoIvaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTiposProductosExtendsMapper;
import org.itcgae.siga.db.services.form.mappers.PysProductosinstitucionExtendsMapper;
import org.itcgae.siga.fac.services.IProductosService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductosServiceImpl implements IProductosService{
	private Logger LOGGER = Logger.getLogger(ProductosServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private PySTipoIvaExtendsMapper pysTipoIvaExtendsMapper;
	
	@Autowired
	private PySTipoFormaPagoExtendsMapper pysTipoFormaPagoExtendsMapper;
	
	@Autowired
	private PySTiposProductosExtendsMapper pysTiposProductosExtendsMapper;
	
	@Autowired
	private PysProductosinstitucionExtendsMapper pysProductosInstitucionExtendsMapper;
	
	@Autowired
	private PysProductosinstitucionMapper pysProductosInstitucionMapper;
	
	
	@Override
	public ComboDTO comboIva(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();
		Error error = new Error();

		LOGGER.info("comboIva() -> Entrada al servicio para recuperar el combo de ivas");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"comboIva() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"comboIva() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"comboIva() / pysTipoIvaExtendsMapper.comboIva() -> Entrada a pysTipoIvaExtendsMapper para recuperar el combo de ivas");

					String idioma = usuarios.get(0).getIdlenguaje();
					List<ComboItem> listaComboIva = pysTipoIvaExtendsMapper
							.comboIva(idioma);

					LOGGER.info(
							"comboIva() / pysTipoIvaExtendsMapper.comboIva() -> Salida de pysTipoIvaExtendsMapper para recuperar el combo de ivas");

					if (listaComboIva != null && listaComboIva.size() > 0) {
						comboDTO.setCombooItems(listaComboIva);
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"ProductosServiceImpl.comboIva() -> Se ha producido un error al recuperar el combo de ivas",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info("comboIva() -> Salida del servicio para recuperar el combo de ivas");

		return comboDTO;
	}
	
	@Override
	public ComboDTO comboTipoFormaPago(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();
		Error error = new Error();

		LOGGER.info("comboTipoFormaPago() -> Entrada al servicio para recuperar el combo de formas de pago");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"comboTipoFormaPago() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"comboTipoFormaPago() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"comboTipoFormaPago() / pysTipoFormaPagoExtendsMapper.comboTipoFormaPago() -> Entrada a pysTipoFormaPagoExtendsMapper para recuperar el combo de formas de pago");

					String idioma = usuarios.get(0).getIdlenguaje();
					List<ComboItem> listaComboFormaPago = pysTipoFormaPagoExtendsMapper
							.comboTipoFormaPago(idioma);

					LOGGER.info(
							"comboTipoFormaPago() / pysTipoFormaPagoExtendsMapper.comboTipoFormaPago() -> Salida de pysTipoFormaPagoExtendsMapper para recuperar el combo de formas de pago");

					if (listaComboFormaPago != null && listaComboFormaPago.size() > 0) {
						comboDTO.setCombooItems(listaComboFormaPago);
				}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"ProductosServiceImpl.comboTipoFormaPago() -> Se ha producido un error al recuperar el combo de formas de pago",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info("comboTipoFormaPago() -> Salida del servicio para recuperar el combo de formas de pago");

		return comboDTO;
	}

	@Override
	public ListaProductosDTO searchListadoProductos(HttpServletRequest request, FiltroProductoItem filtroProductoItem) {
		ListaProductosDTO listaProductosDTO = new ListaProductosDTO();
		Error error = new Error();

		LOGGER.info("searchListadoProductos() -> Entrada al servicio para recuperar el listado de productos segun la busqueda");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"searchListadoProductos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"searchListadoProductos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"searchListadoProductos() / pysTiposProductosExtendsMapper.searchListadoProductos() -> Entrada a pysTiposProductosExtendsMapper para obtener el listado de productos segun la busqueda");

					String idioma = usuarios.get(0).getIdlenguaje();
					List<ListaProductosItem> listaProductos = pysTiposProductosExtendsMapper
							.searchListadoProductosBuscador(idioma, idInstitucion, filtroProductoItem);

					LOGGER.info(
							"searchListadoProductos() / pysTiposProductosExtendsMapper.searchListadoProductos() -> Salida de pysTiposProductosExtendsMapper para obtener el listado de productos segun la busqueda");

					//Necesario para agrupar los productos, ya que la sql devuelve los productos repetidos con distintas formas de pago, con esto se pretende agrupar las formas de pago del producto en una sola linea (info mas detallada en el funcional)
					List<ListaProductosItem> listaProductosProcesada = new ArrayList<ListaProductosItem>();
					int numFormasDePago = 1; //Numero de productos que añades cuando realmente son el mismo pero con diferentes formas de pago
					for (int i = 0; i < listaProductos.size(); i++) {
						
						if(i == 0) {
							listaProductosProcesada.add(listaProductos.get(i));
						}else if((listaProductos.get(i).getIdtipoproducto() == listaProductos.get(i - 1).getIdtipoproducto()) && (listaProductos.get(i).getIdproducto() == listaProductos.get(i - 1).getIdproducto())) //Comprueba que el producto actual es distinto al anterior no el mismo con distinta forma de pago 
						{
							//Este if comprueba si es el 3 producto identico excepto por la forma de pago al primero que añadiste (es decir este seria el 4 por lo que al tener mas de 3 formas de pago se ha de mostrar el numero)
							if(numFormasDePago > 2) {
								listaProductosProcesada.get(listaProductosProcesada.size() - 1).setFormapago("4");
							}else {
								listaProductosProcesada.get(listaProductosProcesada.size() - 1).setFormapago(listaProductosProcesada.get(listaProductosProcesada.size() - 1).getFormapago() + ", " + listaProductos.get(i).getFormapago());						
							}
							numFormasDePago++;
					
						}else{
							numFormasDePago = 0;
							listaProductosProcesada.add(listaProductos.get(i));
						}
					}
					
					if (listaProductosProcesada != null && listaProductosProcesada.size() > 0) {
						listaProductosDTO.setListaProductosItems(listaProductosProcesada);
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"ProductosServiceImpl.searchListadoProductos() -> Se ha producido un error al obtener el listado de productos segun la busqueda",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

	listaProductosDTO.setError(error);

		LOGGER.info("searchListadoProductos() -> Salida del servicio para obtener el listado de productos segun la busqueda");

		return listaProductosDTO;
	}
	
	@Override
	public ProductoDetalleDTO detalleProducto(HttpServletRequest request, int idTipoProducto, int idProducto, int idProductoInstitucion) {
		ProductoDetalleDTO productoDetalleDTO = new ProductoDetalleDTO();
		Error error = new Error();

		LOGGER.info("detalleProducto() -> Entrada al servicio para recuperar los detalles del producto");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"detalleProducto() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"detalleProducto() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"detalleProducto() / PysProductosinstitucionExtendsMapper.detalleProducto() -> Entrada a PysProductosinstitucionExtendsMapper para obtener los detalles del producto");

					String idioma = usuarios.get(0).getIdlenguaje();
					productoDetalleDTO = pysProductosInstitucionExtendsMapper
							.detalleProducto(idTipoProducto, idProducto, idProductoInstitucion, idInstitucion);

					LOGGER.info(
							"detalleProducto() / PysProductosinstitucionExtendsMapper.detalleProducto() -> Salida de PysProductosinstitucionExtendsMapper para obtener los detalles del producto");
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"ProductosServiceImpl.detalleProducto() -> Se ha producido un error al obtener el los detalles del producto",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		productoDetalleDTO.setError(error);

		LOGGER.info("detalleProducto() -> Salida del servicio para obtener los detalles del producto");

		return productoDetalleDTO;
	}

	@Override
	public InsertResponseDTO nuevoProducto(ProductoDetalleDTO producto, HttpServletRequest request) {
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int status = 0;
		

		LOGGER.info("nuevoProducto() -> Entrada al servicio para crear un producto");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"nuevoProducto() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"nuevoProducto() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"nuevoProducto() / pysProductosInstitucionMapper.nuevoProducto() -> Entrada a pysProductosInstitucionMapper para crear un producto");

					NewIdDTO idOrdenacion = pysProductosInstitucionExtendsMapper.getIndiceMaxProducto(producto, idInstitucion);
					PysProductosinstitucion productoInstitucion = new PysProductosinstitucion();
			
					productoInstitucion.setIdinstitucion(idInstitucion);
					productoInstitucion.setIdtipoproducto((short) producto.getIdtipoproducto());
					productoInstitucion.setIdproducto((long) producto.getIdproducto());
					productoInstitucion.setIdproductoinstitucion(Long.parseLong(idOrdenacion.getNewId()));
					productoInstitucion.setDescripcion(producto.getDescripcion());
					productoInstitucion.setCuentacontable(producto.getCuentacontable());
					productoInstitucion.setValor(new BigDecimal(0));//PROVISIONAL
					productoInstitucion.setMomentocargo("P");
					productoInstitucion.setSolicitarbaja(producto.getSolicitarbaja());
					productoInstitucion.setSolicitaralta(producto.getSolicitaralta());
					productoInstitucion.setIdcontador("PYS_" + producto.getIdtipoproducto() + "_" + producto.getIdproducto() + "_" + producto.getIdproductoinstitucion());
					productoInstitucion.setTipocertificado(producto.getTipocertificado());
					productoInstitucion.setFechamodificacion(new Date());//SYSTIMESTAMP?
					productoInstitucion.setNofacturable("0");
					productoInstitucion.setUsumodificacion(usuarios.get(0).getIdusuario());

					status = pysProductosInstitucionMapper.insert(productoInstitucion);
					
					if(status == 0) {
						insertResponseDTO.setStatus(SigaConstants.KO);
					}else if(status == 1) {
						insertResponseDTO.setStatus(SigaConstants.OK);
					}
					

					LOGGER.info(
							"nuevoProducto() / pysProductosInstitucionMapper.crearProducto() -> Salida de pysProductosInstitucionMapper para crear un producto");
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"ProductosServiceImpl.nuevoProducto() -> Se ha producido un error al crear un producto",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		
		insertResponseDTO.setError(error);

		LOGGER.info("nuevoProducto() -> Salida del servicio para crear un producto");

		return insertResponseDTO;
	}
	
	@Override
	public DeleteResponseDTO ReactivarBorradoFisicoLogicoProductos(ListaProductosDTO listadoProductos, HttpServletRequest request) {
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		int status = 0;
		int statusBorradoIdentificador = 0;
		IdPeticionDTO idPeticionDTO = new IdPeticionDTO();
		

		LOGGER.info("ReactivarBorradoFisicoLogicoProductos() -> Entrada al servicio para borrar fisicamente o logicamente/reactivar un producto");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"ReactivarBorradoFisicoLogicoProductos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"ReactivarBorradoFisicoLogicoProductos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"ReactivarBorradoFisicoLogicoProductos() / pysTiposProductosExtendsMapper.ReactivarBorradoFisicoLogicoProductos() -> Entrada a pysTiposProductosExtendsMapper para borrar fisicamente o logicamente/reactivar un producto");
					
					for (ListaProductosItem producto : listadoProductos.getListaProductosItems()) {
						
						//Comprueba que haya alguna compra realizada
						if(pysTiposProductosExtendsMapper.comprobarUsoProducto(producto, idInstitucion) != null)
							idPeticionDTO.setIdpeticionUso(pysTiposProductosExtendsMapper.comprobarUsoProducto(producto, idInstitucion)); 
						
						//Comprueba que haya solicitud de compra
						if(pysTiposProductosExtendsMapper.comprobarSolicitudProducto(producto, idInstitucion) != null)
							idPeticionDTO.setIdpeticionSolicitud(pysTiposProductosExtendsMapper.comprobarSolicitudProducto(producto, idInstitucion));
							
						
						
						//Borrado logico --> Actualizamos la fechabaja del producto a la actual (sysdate)
						//Borrado fisico --> Eliminamos el registro del producto y posteriormente el identificador
						if(idPeticionDTO.getIdpeticionUso().size() > 0 || idPeticionDTO.getIdpeticionSolicitud().size() > 0 ) { //Borrado logico ya que comprobarUsoProducto devolvio resultado por lo que el producto tiene alguna compra o solicitud de compra
							status = pysTiposProductosExtendsMapper.borradoLogicoProductos(usuarios.get(0), producto, idInstitucion);
						}else{ //Borrado fisico al no tener ninguna compra o solicitud de compra ya que el idpetidcion es 0, es decir comprobarUsoProducto no devolvio nada.
							//Borramos el registro
							status = pysTiposProductosExtendsMapper.borradoFisicoProductosRegistro(producto, idInstitucion);
							//Borramos el identificador
							statusBorradoIdentificador = pysTiposProductosExtendsMapper.borradoFisicoProductosIdentificador(producto, idInstitucion);
						}
					}
					
					if(status == 0 || statusBorradoIdentificador == 0) {
						deleteResponseDTO.setStatus(SigaConstants.KO);
					}else if(status == 1 || statusBorradoIdentificador == 1) {
						deleteResponseDTO.setStatus(SigaConstants.OK);
					}
					
					LOGGER.info(
							"ReactivarBorradoFisicoLogicoProductos() / pysTiposProductosExtendsMapper.ReactivarBorradoFisicoLogicoProductos() -> Salida de pysTiposProductosExtendsMapper para borrar fisicamente o logicamente/reactivar un producto");
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"ProductosServiceImpl.ReactivarBorradoFisicoLogicoProductos() -> Se ha producido un error al borrar fisicamente o logicamente/reactivar un producto",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		
		deleteResponseDTO.setError(error);

		LOGGER.info("ReactivarBorradoFisicoLogicoProductos() -> Salida del servicio para borrar fisicamente o logicamente/reactivar un producto");

		return deleteResponseDTO;
	}
}
