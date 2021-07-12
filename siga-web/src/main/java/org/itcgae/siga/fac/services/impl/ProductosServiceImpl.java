package org.itcgae.siga.fac.services.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.FiltroProductoItem;
import org.itcgae.siga.DTO.fac.IdPeticionDTO;
import org.itcgae.siga.DTO.fac.ListaProductosDTO;
import org.itcgae.siga.DTO.fac.ListaProductosItem;
import org.itcgae.siga.DTO.fac.ListadoTipoProductoDTO;
import org.itcgae.siga.DTO.fac.TiposProductosItem;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.PysProductos;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTipoFormaPagoExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTipoIvaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTiposProductosExtendsMapper;
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

					if (listaProductos != null && listaProductos.size() > 0) {
						listaProductosDTO.setListaProductosItems(listaProductos);
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
