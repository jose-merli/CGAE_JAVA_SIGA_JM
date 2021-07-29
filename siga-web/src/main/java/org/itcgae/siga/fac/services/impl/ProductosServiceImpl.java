package org.itcgae.siga.fac.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.FiltroProductoItem;
import org.itcgae.siga.DTO.fac.IdPeticionDTO;
import org.itcgae.siga.DTO.fac.ListaProductosDTO;
import org.itcgae.siga.DTO.fac.ListaProductosItem;
import org.itcgae.siga.DTO.fac.ProductoDetalleDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.PysFormapagoproducto;
import org.itcgae.siga.db.entities.PysFormapagoproductoKey;
import org.itcgae.siga.db.entities.PysProductosinstitucion;
import org.itcgae.siga.db.mappers.PysFormapagoproductoMapper;
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
	
	@Autowired
	private PysFormapagoproductoMapper pysFormaPagoProducto;
	
	
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
	public ComboDTO comboIvaNoDerogados(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();
		Error error = new Error();

		LOGGER.info("comboIvaNoDerogados() -> Entrada al servicio para recuperar el combo de ivas no derogados");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"comboIvaNoDerogados() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"comboIvaNoDerogados() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"comboIvaNoDerogados() / pysTipoIvaExtendsMapper.comboIva() -> Entrada a pysTipoIvaExtendsMapper para recuperar el combo de ivas no derogados");

					String idioma = usuarios.get(0).getIdlenguaje();
					List<ComboItem> listaComboIvaNoDerogados = pysTipoIvaExtendsMapper
							.comboIvaNoDerogados(idioma);

					LOGGER.info(
							"comboIvaNoDerogados() / pysTipoIvaExtendsMapper.comboIva() -> Salida de pysTipoIvaExtendsMapper para recuperar el combo de ivas no derogados");

					if (listaComboIvaNoDerogados != null && listaComboIvaNoDerogados.size() > 0) {
						comboDTO.setCombooItems(listaComboIvaNoDerogados);
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"ProductosServiceImpl.comboIvaNoDerogados() -> Se ha producido un error al recuperar el combo de ivas no derogados",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info("comboIvaNoDerogados() -> Salida del servicio para recuperar el combo de ivas no derogados");

		return comboDTO;
	}
	
	@Override
	public InsertResponseDTO crearEditarFormaPago(ProductoDetalleDTO producto, HttpServletRequest request) {
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		Error error = new Error();
		int statusInsertFormaPagoInternet = 0;
		int statusInsertFormaPagoSecretaria = 0;
		int statusEdicionFinalProducto = 0;
		

		LOGGER.info("crearEditarFormaPago() -> Entrada al servicio para crear/editar formas de pago y editar los campos restantes del producto");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"crearEditarFormaPago() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"crearEditarFormaPago() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"crearEditarFormaPago() / pysProductosInstitucionMapper -> Entrada a pysProductosInstitucionMapper para crear/editar formas de pago");
				
				//Si estamos creando un producto a partir de cero, es decir le hemos dado a nuevo en filtros-productos (front) recorremos las listas de pago de internet y secretaria insertando cada una de las formas
				if(!producto.isEditar()) {
					if(producto.getFormasdepagointernet() != null) {					
						for (int formasdepagointernet : producto.getFormasdepagointernet()) {
							PysFormapagoproducto formaPagoProducto = new PysFormapagoproducto();
							
							formaPagoProducto.setIdinstitucion(idInstitucion);
							formaPagoProducto.setIdtipoproducto((short) producto.getIdtipoproducto());
							formaPagoProducto.setIdproducto((long) producto.getIdproducto());
							formaPagoProducto.setIdproductoinstitucion((long) producto.getIdproductoinstitucion());
							formaPagoProducto.setIdformapago((short) formasdepagointernet);
							formaPagoProducto.setInternet("A");
							formaPagoProducto.setFechamodificacion(new Date());
							formaPagoProducto.setUsumodificacion(usuarios.get(0).getIdusuario());
							
							statusInsertFormaPagoInternet = pysFormaPagoProducto.insert(formaPagoProducto);
						}
					}
					if(producto.getFormasdepagosecretaria() != null)
						for (int formasdepagosecretaria : producto.getFormasdepagosecretaria()) {
							PysFormapagoproducto formaPagoProducto = new PysFormapagoproducto();
							
							formaPagoProducto.setIdinstitucion(idInstitucion);
							formaPagoProducto.setIdtipoproducto((short) producto.getIdtipoproducto());
							formaPagoProducto.setIdproducto((long) producto.getIdproducto());
							formaPagoProducto.setIdproductoinstitucion((long) producto.getIdproductoinstitucion());
							formaPagoProducto.setIdformapago((short) formasdepagosecretaria);
							formaPagoProducto.setInternet("S");
							formaPagoProducto.setFechamodificacion(new Date());
							formaPagoProducto.setUsumodificacion(usuarios.get(0).getIdusuario());
							
							statusInsertFormaPagoSecretaria = pysFormaPagoProducto.insert(formaPagoProducto);
						}
				//Si no estamos creando un producto a partir de cero si no que estamos editandolo habiendo accedido a traves del enlace en la columna 
				//producto del listado de productos (gestion-productos (front)) tenemos que comprobar las formas de pago originales del producto para
				//eliminar las correspondientes en caso de haber deseleccionado e insertar las nuevas ademas de no hacer inserts de las ya existentes
				}else if (producto.isEditar()) {
					PysFormapagoproductoKey pysFormaPagoProductoPrimaryKey = new PysFormapagoproductoKey();
					//PRIMERO RECORREMOS LAS FORMAS DE PAGO DE INTERNET
					//Comprobamos si en el producto editado no aparecen alguna/s formas de pago que se encontraban en el original para eliminarlas
					for (int formasdepagointernetoriginales : producto.getFormasdepagointernetoriginales()) {
						if(Arrays.asList(producto.getFormasdepagointernet()).contains(formasdepagointernetoriginales) == false) {
							pysFormaPagoProductoPrimaryKey.setIdinstitucion(idInstitucion);
							pysFormaPagoProductoPrimaryKey.setIdproducto((long) producto.getIdproducto());
							pysFormaPagoProductoPrimaryKey.setIdformapago((short) formasdepagointernetoriginales);
							pysFormaPagoProductoPrimaryKey.setIdproductoinstitucion((long) producto.getIdproductoinstitucion());
							pysFormaPagoProductoPrimaryKey.setIdtipoproducto((short) producto.getIdtipoproducto());
							
							pysFormaPagoProducto.deleteByPrimaryKey(pysFormaPagoProductoPrimaryKey);
						}
					}
					
					if(producto.getFormasdepagointernet() != null) {	
						//Comprobamos las nuevas formas de pago en el producto editado comparandolo con el producto original para insertarlas
						for(int formasdepagointernet : producto.getFormasdepagointernet()) {
							if(Arrays.asList(producto.getFormasdepagointernetoriginales()).contains(formasdepagointernet) == false) {
								PysFormapagoproducto formaPagoProducto = new PysFormapagoproducto();
								
								formaPagoProducto.setIdinstitucion(idInstitucion);
								formaPagoProducto.setIdtipoproducto((short) producto.getIdtipoproducto());
								formaPagoProducto.setIdproducto((long) producto.getIdproducto());
								formaPagoProducto.setIdproductoinstitucion((long) producto.getIdproductoinstitucion());
								formaPagoProducto.setIdformapago((short) formasdepagointernet);
								formaPagoProducto.setInternet("A");
								formaPagoProducto.setFechamodificacion(new Date());
								formaPagoProducto.setUsumodificacion(usuarios.get(0).getIdusuario());
								
								statusInsertFormaPagoInternet = pysFormaPagoProducto.insert(formaPagoProducto);
							}
						}
					}
					
					//POR ULTIMO RECORREMOS HACIENDO EXACTAMENTE LO MISMO LAS FORMAS DE PAGO DE SECRETARIA
					//Comprobamos si en el producto editado no aparecen alguna/s formas de pago que se encontraban en el original para eliminarlas
					for (int formasdepagosecretariasoriginales : producto.getFormasdepagosecretariaoriginales()) {
						if(Arrays.asList(producto.getFormasdepagosecretaria()).contains(formasdepagosecretariasoriginales) == false) {
							pysFormaPagoProductoPrimaryKey.setIdinstitucion(idInstitucion);
							pysFormaPagoProductoPrimaryKey.setIdproducto((long) producto.getIdproducto());
							pysFormaPagoProductoPrimaryKey.setIdformapago((short) formasdepagosecretariasoriginales);
							pysFormaPagoProductoPrimaryKey.setIdproductoinstitucion((long) producto.getIdproductoinstitucion());
							pysFormaPagoProductoPrimaryKey.setIdtipoproducto((short) producto.getIdtipoproducto());
							
							pysFormaPagoProducto.deleteByPrimaryKey(pysFormaPagoProductoPrimaryKey);
						}
					}
					
					if(producto.getFormasdepagosecretaria() != null) {	
						//Comprobamos las nuevas formas de pago en el producto editado comparandolo con el producto original para insertarlas
						for(int formasdepagosecretaria : producto.getFormasdepagosecretaria()) {
							if(Arrays.asList(producto.getFormasdepagosecretariaoriginales()).contains(formasdepagosecretaria) == false) {
								PysFormapagoproducto formaPagoProducto = new PysFormapagoproducto();
								
								formaPagoProducto.setIdinstitucion(idInstitucion);
								formaPagoProducto.setIdtipoproducto((short) producto.getIdtipoproducto());
								formaPagoProducto.setIdproducto((long) producto.getIdproducto());
								formaPagoProducto.setIdproductoinstitucion((long) producto.getIdproductoinstitucion());
								formaPagoProducto.setIdformapago((short) formasdepagosecretaria);
								formaPagoProducto.setInternet("S");
								formaPagoProducto.setFechamodificacion(new Date());
								formaPagoProducto.setUsumodificacion(usuarios.get(0).getIdusuario());
								
								statusInsertFormaPagoInternet = pysFormaPagoProducto.insert(formaPagoProducto);
							}
						}
					}
					
				}
				
				//Actualizamos la informacion restante la cual no fue introducida al crear el producto en la tarjeta datos generales
				PysProductosinstitucion productoInstitucion = new PysProductosinstitucion();
				
				productoInstitucion.setIdinstitucion(idInstitucion);
				productoInstitucion.setIdtipoproducto((short) producto.getIdtipoproducto());
				productoInstitucion.setIdproducto((long) producto.getIdproducto());
				productoInstitucion.setIdproductoinstitucion((long) producto.getIdproducto());
				
				productoInstitucion.setDescripcion(producto.getDescripcion());
				productoInstitucion.setValor(new BigDecimal(producto.getValor()));
				productoInstitucion.setMomentocargo(producto.getMomentocargo());
				productoInstitucion.setSolicitarbaja(producto.getSolicitarbaja());
				productoInstitucion.setSolicitaralta(producto.getSolicitaralta());
				productoInstitucion.setFechamodificacion(new Date());
				productoInstitucion.setUsumodificacion(usuarios.get(0).getIdusuario());
				productoInstitucion.setCuentacontable(producto.getCuentacontable());
				productoInstitucion.setIdimpresora((long) producto.getIdimpresora());
				productoInstitucion.setIdplantilla((long) producto.getIdplantilla());
				productoInstitucion.setTipocertificado(producto.getTipocertificado());
				productoInstitucion.setFechabaja(producto.getFechabaja());
				productoInstitucion.setIdcontador(producto.getIdcontador());
				productoInstitucion.setNofacturable(producto.getNofacturable());
				productoInstitucion.setIdtipoiva(producto.getIdtipoiva());
				productoInstitucion.setCodigoext(producto.getCodigoext());
				productoInstitucion.setCodigoTraspasonav(producto.getCodigo_traspasonav());
				productoInstitucion.setOrden((long) producto.getOrden());

				statusEdicionFinalProducto = pysProductosInstitucionMapper.updateByPrimaryKey(productoInstitucion);
				
					
					if(statusInsertFormaPagoInternet == 0 || statusInsertFormaPagoSecretaria == 0 || statusEdicionFinalProducto == 0) {
						insertResponseDTO.setStatus(SigaConstants.KO);
					}else if(statusInsertFormaPagoInternet == 1 && statusInsertFormaPagoSecretaria == 1 && statusEdicionFinalProducto == 0) {
						insertResponseDTO.setStatus(SigaConstants.OK);
					}
					
					LOGGER.info(
							"crearEditarFormaPago() / pysProductosInstitucionMapper-> Salida de pysProductosInstitucionMapper para crear/editar formas de pago y editar los campos restantes del producto");
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"ProductosServiceImpl.crearEditarFormaPago() -> Se ha producido un error al crear/editar formas de pago y editar los campos restantes del producto",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		insertResponseDTO.setError(error);

		LOGGER.info("crearEditarFormaPago() -> Salida del servicio para crear/editar formas de pago y editar los campos restantes del producto");

		return insertResponseDTO;
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
	public ComboDTO comboTipoFormaPagoInternet(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();
		Error error = new Error();

		LOGGER.info("comboTipoFormaPagoInternet() -> Entrada al servicio para recuperar el combo de formas de pago de internet");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"comboTipoFormaPagoInternet() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"comboTipoFormaPagoInternet() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"comboTipoFormaPagoInternet() / pysTipoFormaPagoExtendsMapper.comboTipoFormaPagoInternet() -> Entrada a pysTipoFormaPagoExtendsMapper para recuperar el combo de formas de pago de internet");

					String idioma = usuarios.get(0).getIdlenguaje();
					List<ComboItem> listaComboFormaPagoInternet = pysTipoFormaPagoExtendsMapper
							.comboTipoFormaPagoInternet(idioma);

					LOGGER.info(
							"comboTipoFormaPagoInternet() / pysTipoFormaPagoExtendsMapper.comboTipoFormaPagoInternet() -> Salida de pysTipoFormaPagoExtendsMapper para recuperar el combo de formas de pago de internet");

					if (listaComboFormaPagoInternet != null && listaComboFormaPagoInternet.size() > 0) {
						comboDTO.setCombooItems(listaComboFormaPagoInternet);
				}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"ProductosServiceImpl.comboTipoFormaPagoInternet() -> Se ha producido un error al recuperar el combo de formas de pago de internet",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info("comboTipoFormaPagoInternet() -> Salida del servicio para recuperar el combo de formas de pago de internet");

		return comboDTO;
	}

	@Override
	public ComboDTO comboTipoFormaPagoSecretaria(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();
		Error error = new Error();

		LOGGER.info("comboTipoFormaPagoSecretaria() -> Entrada al servicio para recuperar el combo de formas de pago de secretaria");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"comboTipoFormaPagoSecretaria() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"comboTipoFormaPagoSecretaria() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"comboTipoFormaPagoSecretaria() / pysTipoFormaPagoExtendsMapper.comboTipoFormaPagoSecretaria() -> Entrada a pysTipoFormaPagoExtendsMapper para recuperar el combo de formas de pago de secretaria");

					String idioma = usuarios.get(0).getIdlenguaje();
					List<ComboItem> listaComboFormaPagoSecretaria = pysTipoFormaPagoExtendsMapper
							.comboTipoFormaPagoSecretaria(idioma);

					LOGGER.info(
							"comboTipoFormaPagoSecretaria() / pysTipoFormaPagoExtendsMapper.comboTipoFormaPagoSecretaria() -> Salida de pysTipoFormaPagoExtendsMapper para recuperar el combo de formas de pago de secretaria");

					if (listaComboFormaPagoSecretaria != null && listaComboFormaPagoSecretaria.size() > 0) {
						comboDTO.setCombooItems(listaComboFormaPagoSecretaria);
				}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"ProductosServiceImpl.comboTipoFormaPagoSecretaria() -> Se ha producido un error al recuperar el combo de formas de pago de secretaria",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.info("comboTipoFormaPagoSecretaria() -> Salida del servicio para recuperar el combo de formas de pago de secretaria");

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
						}else if((listaProductos.get(i).getIdtipoproducto() == listaProductos.get(i - 1).getIdtipoproducto()) && (listaProductos.get(i).getIdproducto() == listaProductos.get(i - 1).getIdproducto()) && (listaProductos.get(i).getIdproductoinstitucion() == listaProductos.get(i - 1).getIdproductoinstitucion())) //Comprueba que el producto actual es distinto al anterior no el mismo con distinta forma de pago 
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
					productoInstitucion.setMomentocargo("P");//Se informa por defecto a P
					productoInstitucion.setSolicitarbaja(producto.getSolicitarbaja());
					productoInstitucion.setSolicitaralta(producto.getSolicitaralta());
					productoInstitucion.setIdimpresora(null);//No aplica
					productoInstitucion.setIdplantilla(null);//No aplica
					productoInstitucion.setIdcontador("PYS_" + producto.getIdtipoproducto() + "_" + producto.getIdproducto() + "_" + productoInstitucion.getIdproductoinstitucion());
					productoInstitucion.setTipocertificado(producto.getTipocertificado());
					productoInstitucion.setFechabaja(null);
					productoInstitucion.setFechamodificacion(new Date());
					productoInstitucion.setCodigoext(producto.getCodigoext());
					productoInstitucion.setCodigoTraspasonav(null);//No aplica
					productoInstitucion.setOrden(null);//No aplica
					productoInstitucion.setUsumodificacion(usuarios.get(0).getIdusuario());
					
					//Campos a informar despues en tarjeta formas de pago
					productoInstitucion.setValor(null);
					productoInstitucion.setIdtipoiva(null);
					productoInstitucion.setNofacturable("0");
					

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
	public DeleteResponseDTO editarProducto(ProductoDetalleDTO producto, HttpServletRequest request) {
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		int status = 0;
		

		LOGGER.info("editarProducto() -> Entrada al servicio para modificar un producto");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"editarProducto() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"editarProducto() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"editarProducto() / pysProductosMapper.editarProducto() -> Entrada a pysProductosMapper para modificar un producto");
					
					//AL EDITAR PRODUCTOS HAY QUE USAR LA CONSULTA QUE TE TRAE EL IDORDENACION YA QUE AL CAMBIAR TIPO O CATEGORIA SE PUEDE REPETIR LA PK DE 4 CAMPOS AL HABER IDPRODUCTOSINSTITUCION REPETIDOS
					//NewIdDTO idOrdenacion = pysProductosInstitucionExtendsMapper.getIndiceMaxProducto(producto, idInstitucion);
					//productoInstitucion.setIdproductoinstitucion(Long.parseLong(idOrdenacion.getNewId()));
					if(producto.getProductooriginal().getIdtipoproducto() == producto.getIdtipoproducto() && producto.getProductooriginal().getIdproducto() == producto.getIdproducto()) {
						PysProductosinstitucion productoInstitucion = new PysProductosinstitucion();
							
						productoInstitucion.setIdinstitucion(idInstitucion);
						productoInstitucion.setIdtipoproducto((short) producto.getIdtipoproducto());
						productoInstitucion.setIdproducto((long) producto.getIdproducto());
						productoInstitucion.setIdproductoinstitucion((long) producto.getIdproductoinstitucion());
						
						
						productoInstitucion.setDescripcion(producto.getDescripcion());
						productoInstitucion.setValor(new BigDecimal(producto.getValor()));
						productoInstitucion.setMomentocargo(producto.getMomentocargo());
						productoInstitucion.setSolicitarbaja(producto.getSolicitarbaja());
						productoInstitucion.setSolicitaralta(producto.getSolicitaralta());
						productoInstitucion.setFechamodificacion(new Date());
						productoInstitucion.setUsumodificacion(usuarios.get(0).getIdusuario());
						productoInstitucion.setCuentacontable(producto.getCuentacontable());
						productoInstitucion.setIdimpresora((long) producto.getIdimpresora());
						productoInstitucion.setIdplantilla((long) producto.getIdplantilla());
						productoInstitucion.setTipocertificado(producto.getTipocertificado());
						productoInstitucion.setFechabaja(producto.getFechabaja());
						productoInstitucion.setIdcontador(producto.getIdcontador());
						productoInstitucion.setNofacturable(producto.getNofacturable());
						productoInstitucion.setIdtipoiva(producto.getIdtipoiva());
						productoInstitucion.setCodigoext(producto.getCodigoext());
						productoInstitucion.setCodigoTraspasonav(producto.getCodigo_traspasonav());
						productoInstitucion.setOrden((long) producto.getOrden());
						
						status = pysProductosInstitucionMapper.updateByPrimaryKey(productoInstitucion);
					}else {
						System.out.println("FSDKFLSDKFSDLKFLSDKSDF");
					}
					
					if(status == 0) {
						deleteResponseDTO.setStatus(SigaConstants.KO);
					}else if(status == 1) {
						deleteResponseDTO.setStatus(SigaConstants.OK);
					}
					
					LOGGER.info(
							"editarProducto() / pysProductosMapper.editarProducto() -> Salida de pysProductosMapper para modificar un producto");
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"ProductosServiceImpl.modificarProducto() -> Se ha producido un error al modificar un producto",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		deleteResponseDTO.setError(error);

		LOGGER.info("modificarProducto() -> Salida del servicio para modificar un producto");

		return deleteResponseDTO;
	}
	
	@Override
	public DeleteResponseDTO reactivarBorradoFisicoLogicoProductos(ListaProductosDTO listadoProductos, HttpServletRequest request) {
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		Error error = new Error();
		int status = 0;
		int statusBorradoIdentificador = 0;
		IdPeticionDTO idPeticionDTO = new IdPeticionDTO();
		

		LOGGER.info("reactivarBorradoFisicoLogicoProductos() -> Entrada al servicio para borrar fisicamente o logicamente/reactivar un producto");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"reactivarBorradoFisicoLogicoProductos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"reactivarBorradoFisicoLogicoProductos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"reactivarBorradoFisicoLogicoProductos() / pysTiposProductosExtendsMapper.reactivarBorradoFisicoLogicoProductos() -> Entrada a pysTiposProductosExtendsMapper para borrar fisicamente o logicamente/reactivar un producto");
					
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
							if(status == 0) {
								deleteResponseDTO.setStatus(SigaConstants.KO);
							}else if(status == 1) {
								deleteResponseDTO.setStatus(SigaConstants.OK);
							}
						}else{ //Borrado fisico al no tener ninguna compra o solicitud de compra ya que el idpetidcion es 0, es decir comprobarUsoProducto no devolvio nada.
							//Borramos el registro
							status = pysTiposProductosExtendsMapper.borradoFisicoProductosRegistro(producto, idInstitucion);
							//Borramos el identificador
							statusBorradoIdentificador = pysTiposProductosExtendsMapper.borradoFisicoProductosIdentificador(producto, idInstitucion);
							
							if(status == 0 || statusBorradoIdentificador == 0) {
								deleteResponseDTO.setStatus(SigaConstants.KO);
							}else if(status == 1 || statusBorradoIdentificador == 1) {
								deleteResponseDTO.setStatus(SigaConstants.OK);
							}
						}
					}
					
					LOGGER.info(
							"reactivarBorradoFisicoLogicoProductos() / pysTiposProductosExtendsMapper.reactivarBorradoFisicoLogicoProductos() -> Salida de pysTiposProductosExtendsMapper para borrar fisicamente o logicamente/reactivar un producto");
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"ProductosServiceImpl.reactivarBorradoFisicoLogicoProductos() -> Se ha producido un error al borrar fisicamente o logicamente/reactivar un producto",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		
		deleteResponseDTO.setError(error);

		LOGGER.info("reactivarBorradoFisicoLogicoProductos() -> Salida del servicio para borrar fisicamente o logicamente/reactivar un producto");

		return deleteResponseDTO;
	}
}
