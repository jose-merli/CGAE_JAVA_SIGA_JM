package org.itcgae.siga.fac.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.FiltroProductoItem;
import org.itcgae.siga.DTO.fac.IdPeticionDTO;
import org.itcgae.siga.DTO.fac.ListaCodigosPorColegioDTO;
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
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.PysFormapagoproducto;
import org.itcgae.siga.db.entities.PysFormapagoproductoKey;
import org.itcgae.siga.db.entities.PysProductosinstitucion;
import org.itcgae.siga.db.entities.PysTipoiva;
import org.itcgae.siga.db.mappers.AdmContadorMapper;
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
import org.springframework.transaction.annotation.Transactional;

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
	
	@Autowired
	private AdmContadorMapper admContadorMapper;
	
	//Obtiene el combo de tipos de iva dados de alta (fechabaja is null)
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
	
	//Obtiene todos los tipos de iva para el combo esten o no dados de alta (indiferentemente de la fechabaja)
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
	
	//Inserta o edita en base de datos las formas de pago asociadas a X producto
	@Override
	@Transactional(rollbackFor = Exception.class)
	public InsertResponseDTO crearEditarFormaPago(ProductoDetalleDTO producto, HttpServletRequest request) throws Exception{
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		NewIdDTO idProductoInstitucion = new NewIdDTO();
		int statusInsertFormaPagoInternet = 0;
		int statusInsertFormaPagoSecretaria = 0;
		int statusEdicionFinalProducto = 0;
		int statusDeleteFormasDePagoInternet = 0;
		int statusDeleteFormasDePagoSecretaria = 0;
		

		LOGGER.info("crearEditarFormaPago() -> Entrada al servicio para crear/editar formas de pago y editar los campos restantes del producto");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

	
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
						"crearEditarFormaPago() / pysFormasDePagoProductoMapper -> Entrada a pysFormasDePagoProductoMapper para crear/editar formas de pago");
					
				idProductoInstitucion = (pysProductosInstitucionExtendsMapper.getIdProductoInstitucion(producto, idInstitucion));	
				//Si estamos creando un producto a partir de cero, es decir le hemos dado a nuevo en filtros-productos (front) recorremos las listas de pago de internet y secretaria insertando cada una de las formas
				if(!producto.isEditar()) {
					if(producto.getFormasdepagointernet() != null) {					
						for (int formasdepagointernet : producto.getFormasdepagointernet()) {
							PysFormapagoproducto formaPagoProducto = new PysFormapagoproducto();
								
							formaPagoProducto.setIdinstitucion(idInstitucion);
							formaPagoProducto.setIdtipoproducto((short) producto.getIdtipoproducto());
							formaPagoProducto.setIdproducto((long) producto.getIdproducto());
							formaPagoProducto.setIdproductoinstitucion(Long.valueOf(idProductoInstitucion.getNewId()));
							formaPagoProducto.setIdformapago((short) formasdepagointernet);
							formaPagoProducto.setInternet("A");
							formaPagoProducto.setFechamodificacion(new Date());
							formaPagoProducto.setUsumodificacion(usuarios.get(0).getIdusuario());
								
							statusInsertFormaPagoInternet = pysFormaPagoProducto.insert(formaPagoProducto);
								
							if(statusInsertFormaPagoInternet == 0) {
									throw new Exception("Error al insertar una forma de pago de internet del producto");
							}else if (statusInsertFormaPagoInternet == 1){
								LOGGER.info(
										"crearEditarFormaPago() / pysFormasDePagoProductoMapper -> Forma de pago de internet insertada con exito");
							}
						}
					}
					
					if(producto.getFormasdepagosecretaria() != null)
						for (int formasdepagosecretaria : producto.getFormasdepagosecretaria()) {
							PysFormapagoproducto formaPagoProducto = new PysFormapagoproducto();
								
							formaPagoProducto.setIdinstitucion(idInstitucion);
							formaPagoProducto.setIdtipoproducto((short) producto.getIdtipoproducto());
							formaPagoProducto.setIdproducto((long) producto.getIdproducto());
							formaPagoProducto.setIdproductoinstitucion(Long.valueOf(idProductoInstitucion.getNewId()));
							formaPagoProducto.setIdformapago((short) formasdepagosecretaria);
							formaPagoProducto.setInternet("S");
							formaPagoProducto.setFechamodificacion(new Date());
							formaPagoProducto.setUsumodificacion(usuarios.get(0).getIdusuario());
								
							statusInsertFormaPagoSecretaria = pysFormaPagoProducto.insert(formaPagoProducto);
								
							if(statusInsertFormaPagoSecretaria == 0) {
								throw new Exception("Error al insertar una forma de pago de secretaria del producto");
							}else if (statusInsertFormaPagoSecretaria == 1){
								LOGGER.info(
										"crearEditarFormaPago() / pysFormasDePagoProductoMapper -> Forma de pago de secretaria insertada con exito");
							}
						}
				}else if (producto.isEditar()) {
					PysFormapagoproductoKey pysFormaPagoProductoPrimaryKey = new PysFormapagoproductoKey();
						
					//PRIMERO ELIMINAMOS LAS FORMAS DE PAGO ORIGINALES DE INTERNET Y SECRETARIA
					//INTERNET
					if(producto.getFormasdepagointernetoriginales() != null) {
						for (int formasdepagointernetoriginales : producto.getFormasdepagointernetoriginales()) {
								
								pysFormaPagoProductoPrimaryKey.setIdinstitucion(idInstitucion);
								pysFormaPagoProductoPrimaryKey.setIdproducto((long) producto.getIdproducto());
								pysFormaPagoProductoPrimaryKey.setIdformapago((short) formasdepagointernetoriginales);
								pysFormaPagoProductoPrimaryKey.setIdproductoinstitucion((long) producto.getIdproductoinstitucion());
								pysFormaPagoProductoPrimaryKey.setIdtipoproducto((short) producto.getIdtipoproducto());
									
								statusDeleteFormasDePagoInternet = pysFormaPagoProducto.deleteByPrimaryKey(pysFormaPagoProductoPrimaryKey);
									
								if(statusDeleteFormasDePagoInternet == 0) {
									throw new Exception("Error al eliminar una forma de pago de internet del producto");
								}		
						}
					}
						
					//SECRETARIA
					if(producto.getFormasdepagosecretariaoriginales() != null) {
						for (int formasdepagosecretariasoriginales : producto.getFormasdepagosecretariaoriginales()) {
								
								pysFormaPagoProductoPrimaryKey.setIdinstitucion(idInstitucion);
								pysFormaPagoProductoPrimaryKey.setIdproducto((long) producto.getIdproducto());
								pysFormaPagoProductoPrimaryKey.setIdformapago((short) formasdepagosecretariasoriginales);
								pysFormaPagoProductoPrimaryKey.setIdproductoinstitucion(Long.valueOf(idProductoInstitucion.getNewId()));
								pysFormaPagoProductoPrimaryKey.setIdtipoproducto((short) producto.getIdtipoproducto());
									
								statusDeleteFormasDePagoSecretaria = pysFormaPagoProducto.deleteByPrimaryKey(pysFormaPagoProductoPrimaryKey);
									
								if(statusDeleteFormasDePagoSecretaria == 0) {
									throw new Exception("Error al eliminar una forma de pago de secretaria del producto");
								}							
							}
					}
						
						
					//SEGUNDO INSERTAMOS LAS FORMAS DE PAGO DE INTERNET Y SECRETARIA
					//INTERNET
					if(producto.getFormasdepagointernet() != null) {	
						for(int formasdepagointernet : producto.getFormasdepagointernet()) {
				
								PysFormapagoproducto formaPagoProducto = new PysFormapagoproducto();
									
								formaPagoProducto.setIdinstitucion(idInstitucion);
								formaPagoProducto.setIdtipoproducto((short) producto.getIdtipoproducto());
								formaPagoProducto.setIdproducto((long) producto.getIdproducto());
								formaPagoProducto.setIdproductoinstitucion(Long.valueOf(idProductoInstitucion.getNewId()));
								formaPagoProducto.setIdformapago((short) formasdepagointernet);
								formaPagoProducto.setInternet("A");
								formaPagoProducto.setFechamodificacion(new Date());
								formaPagoProducto.setUsumodificacion(usuarios.get(0).getIdusuario());
									
								statusInsertFormaPagoInternet = pysFormaPagoProducto.insert(formaPagoProducto);
									
								if(statusInsertFormaPagoInternet == 0) {
									throw new Exception("Error al insertar una forma de pago de internet del producto");
								}else if (statusInsertFormaPagoInternet == 1){
									LOGGER.info(
											"crearEditarFormaPago() / pysFormasDePagoProductoMapper -> Forma de pago de internet insertada con exito");
								}
						}
					}
						
					//SECRETARIA
					if(producto.getFormasdepagosecretaria() != null) {	
						for(int formasdepagosecretaria : producto.getFormasdepagosecretaria()) {
					
								PysFormapagoproducto formaPagoProducto = new PysFormapagoproducto();
									
								formaPagoProducto.setIdinstitucion(idInstitucion);
								formaPagoProducto.setIdtipoproducto((short) producto.getIdtipoproducto());
								formaPagoProducto.setIdproducto((long) producto.getIdproducto());
								formaPagoProducto.setIdproductoinstitucion(Long.valueOf(idProductoInstitucion.getNewId()));
								formaPagoProducto.setIdformapago((short) formasdepagosecretaria);
								formaPagoProducto.setInternet("S");
								formaPagoProducto.setFechamodificacion(new Date());
								formaPagoProducto.setUsumodificacion(usuarios.get(0).getIdusuario());
									
								statusInsertFormaPagoSecretaria = pysFormaPagoProducto.insert(formaPagoProducto);
									
								if(statusInsertFormaPagoSecretaria == 0) {
									throw new Exception("Error al insertar una forma de pago de secretaria del producto");
								}else if (statusInsertFormaPagoSecretaria == 1){
									LOGGER.info(
											"crearEditarFormaPago() / pysFormasDePagoProductoMapper -> Forma de pago de secretaria insertada con exito");
								}					
						}
					}
						
				}
				LOGGER.info(
						"crearEditarFormaPago() / pysFormasDePagoProductoMapper -> Salida de pysFormasDePagoProductoMapper para crear/editar formas de pago");
				
				LOGGER.info(
						"crearEditarFormaPago() / pysProductosInstitucionMapper-> Entrada a pysProductosInstitucionMapper para actualizar la informacion restante del producto");
				
					
				//Actualizamos la informacion restante la cual no fue introducida al crear el producto en la tarjeta datos generales
				PysProductosinstitucion productoInstitucion = new PysProductosinstitucion();
					
				productoInstitucion.setIdinstitucion(idInstitucion);
				productoInstitucion.setIdtipoproducto((short) producto.getIdtipoproducto());
				productoInstitucion.setIdproducto((long) producto.getIdproducto());
				productoInstitucion.setIdproductoinstitucion(Long.valueOf(idProductoInstitucion.getNewId()));
								
				productoInstitucion.setValor(new BigDecimal(producto.getValor()));
				productoInstitucion.setNofacturable(producto.getNofacturable());
				productoInstitucion.setIdtipoiva(producto.getIdtipoiva());
					
				productoInstitucion.setFechamodificacion(new Date());
				productoInstitucion.setUsumodificacion(usuarios.get(0).getIdusuario());
					
				statusEdicionFinalProducto = pysProductosInstitucionMapper.updateByPrimaryKeySelective(productoInstitucion);
					
				if(statusEdicionFinalProducto == 0) {
					throw new Exception("Error al insertar la informacion restante de la tarjeta formas de pago en el producto");
				}else if(statusEdicionFinalProducto == 1) {
					LOGGER.info(
							"crearEditarFormaPago() / pysProductosInstitucionMapper-> Informacion restante del producto actualizada correctamente");
				}
					
				LOGGER.info(
							"crearEditarFormaPago() / pysProductosInstitucionMapper-> Salida de pysProductosInstitucionMapper para actualizar la informacion restante del producto");
			}

		}

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

	//Obtiene la informacion de los productos al darle a buscar en Facturacion --> Productos para rellenar la tabla.
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
								listaProductosProcesada.get(listaProductosProcesada.size() - 1).setFormapago(String.valueOf(numFormasDePago));
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
	
	//Obtiene la informacion detallada del producto para su uso al entrar por editar desde la pantalla busqueda de productos por ejemplo
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
					
					LOGGER.info(
							"detalleProducto() / PysProductosinstitucionExtendsMapper.obtenerFormasDePagoInternetByProducto() -> Entrada a PysProductosinstitucionExtendsMapper para obtener las formas de pago de internet");
					List<Integer> listaFormasDePagoInternet = pysProductosInstitucionExtendsMapper.obtenerFormasDePagoInternetByProducto(idTipoProducto, idProducto, idProductoInstitucion, idInstitucion);
					
					if(listaFormasDePagoInternet != null && !listaFormasDePagoInternet.isEmpty()) {
						productoDetalleDTO.setFormasdepagointernet(listaFormasDePagoInternet);
					}
					
					LOGGER.info(
							"detalleProducto() / PysProductosinstitucionExtendsMapper.obtenerFormasDePagoInternetByProducto() -> Salida a PysProductosinstitucionExtendsMapper para obtener las formas de pago de internet");
					
					LOGGER.info(
							"detalleProducto() / PysProductosinstitucionExtendsMapper.obtenerFormasDePagoSecretariaByProducto() -> Entrada a PysProductosinstitucionExtendsMapper para obtener las formas de pago de secretaria");
					List<Integer> listaFormasDePagoSecretaria = pysProductosInstitucionExtendsMapper.obtenerFormasDePagoSecretariaByProducto(idTipoProducto, idProducto, idProductoInstitucion, idInstitucion);
					
					if(listaFormasDePagoSecretaria != null && !listaFormasDePagoSecretaria.isEmpty()) {
						productoDetalleDTO.setFormasdepagosecretaria(listaFormasDePagoSecretaria);
					}
					
					LOGGER.info(
							"detalleProducto() / PysProductosinstitucionExtendsMapper.obtenerFormasDePagoSecretariaByProducto() -> Salida a PysProductosinstitucionExtendsMapper para obtener las formas de pago de secretaria");
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

	//Inserta en base de datos un nuevo producto
	@SuppressWarnings("deprecation")
	@Override
	@Transactional(rollbackFor = Exception.class)
	public InsertResponseDTO nuevoProducto(ProductoDetalleDTO producto, HttpServletRequest request) throws Exception {
		InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
		int status = 0;
		int statusInsertAdmContador = 0;
		

		LOGGER.info("nuevoProducto() -> Entrada al servicio para crear un producto");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

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
					
				if(producto.getTipocertificado() == null) {
					productoInstitucion.setIdcontador(null);
				}else {
					LOGGER.info(
							"nuevoProducto() / admContadorMapper.nuevoProducto() -> Entrada a admContadorMapper para crear un contador necesario para el producto ya que se ha definido un tipo de certificado");
					
					productoInstitucion.setIdcontador("PYS_" + producto.getIdtipoproducto() + "_" + producto.getIdproducto() + "_" + productoInstitucion.getIdproductoinstitucion());
						
					AdmContador admContador = new AdmContador();
						
					admContador.setContador(0L);
					admContador.setDescripcion("fdsf");
					admContador.setFechareconfiguracion(new Date("2022/01/01 00:00:00")); //FECHA RECONFIGURACION
					admContador.setIdcampocontador("CONTADOR_CER");
					admContador.setIdcampoprefijo("PREFIJO_CER");
					admContador.setIdcamposufijo("SUFIJO_CER");
					admContador.setIdcontador(productoInstitucion.getIdcontador());
					admContador.setIdinstitucion(idInstitucion);
					admContador.setIdtabla("CER_SOLICITUDCERTIFICADOS");
					admContador.setLongitudcontador(5);
					admContador.setModificablecontador("0");
					admContador.setModo((short) 0);
					admContador.setNombre("fdsf");
					admContador.setPrefijo(null);
					admContador.setReconfiguracioncontador("0");
					admContador.setReconfiguracionprefijo(null);
					admContador.setReconfiguracionsufijo("/2022");
					admContador.setSufijo("/2021");
					admContador.setGeneral("0");
					admContador.setIdmodulo((short) 9);
					admContador.setFechamodificacion(new Date());
					admContador.setUsumodificacion(usuarios.get(0).getIdusuario());
					admContador.setFechacreacion(new Date());
					admContador.setUsucreacion(usuarios.get(0).getIdusuario());
						
					statusInsertAdmContador = admContadorMapper.insert(admContador);
						
					if(statusInsertAdmContador == 0) {
						throw new Exception("No se ha podido crear el registro en adm_contador");
					}else if(statusInsertAdmContador == 1) {
						LOGGER.info(
								"nuevoProducto() / admContadorMapper.nuevoProducto() -> Registro en adm_contador creado con exito");
					}
					
					LOGGER.info(
							"nuevoProducto() / admContadorMapper.nuevoProducto() -> Salida de admContadorMapper para crear un contador necesario para el producto ya que se ha definido un tipo de certificado");
		
				}
					
				productoInstitucion.setTipocertificado(producto.getTipocertificado());
				productoInstitucion.setFechabaja(null);
				productoInstitucion.setFechamodificacion(new Date());
					
				if(producto.getCodigoext() != null) {
					productoInstitucion.setCodigoext(producto.getCodigoext());
				}else {
					productoInstitucion.setCodigoext(producto.getIdtipoproducto() + "|" + producto.getIdproducto() + "|" + Long.parseLong(idOrdenacion.getNewId()));
				}
					
				productoInstitucion.setCodigoTraspasonav(null);//No aplica
				productoInstitucion.setOrden(null);//No aplica
				productoInstitucion.setUsumodificacion(usuarios.get(0).getIdusuario());
					
				//Campos a informar despues en tarjeta formas de pago
				productoInstitucion.setValor(new BigDecimal(0));//Fijado a 0 hasta que se rellene en formas de pago
				productoInstitucion.setIdtipoiva(null);
				productoInstitucion.setNofacturable("0");
					

				status = pysProductosInstitucionMapper.insert(productoInstitucion);
					
					
				if(status == 0) {	
					throw new Exception("No se ha podido crear el registro en PYS_PRODUCTOSINSTITUCION");
				}else if(status == 1) {
					LOGGER.info(
							"nuevoProducto() / pysProductosInstitucionMapper.nuevoProducto() -> Se ha podido crear el registro en PYS_PRODUCTOSINSTITUCION");
				}
					

				LOGGER.info(
					"nuevoProducto() / pysProductosInstitucionMapper.nuevoProducto() -> Salida de pysProductosInstitucionMapper para crear un producto");
			}

		}
		
		LOGGER.info("nuevoProducto() -> Salida del servicio para crear un producto");

		return insertResponseDTO;
	}
	
	//Edita un producto existente en base de datos
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
							"editarProducto() / pysProductosInstitucionMapper.editarProducto() -> Entrada a pysProductosInstitucionMapper para modificar un producto");				
	
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
						if(producto.getCodigoext() != null) {
							productoInstitucion.setCodigoext(producto.getCodigoext());
						}else {
							productoInstitucion.setCodigoext(producto.getIdtipoproducto() + "|" + producto.getIdproducto() + "|" + producto.getIdproductoinstitucion());
						}
						productoInstitucion.setCodigoTraspasonav(producto.getCodigo_traspasonav());
						productoInstitucion.setOrden((long) producto.getOrden());
						
						status = pysProductosInstitucionMapper.updateByPrimaryKey(productoInstitucion);				
					
					if(status == 0) {
						deleteResponseDTO.setStatus(SigaConstants.KO);
					}else if(status == 1) {
						deleteResponseDTO.setStatus(SigaConstants.OK);
					}
					
					LOGGER.info(
							"editarProducto() / pysProductosInstitucionMapper.editarProducto() -> Salida de pysProductosInstitucionMapper para modificar un producto");
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"ProductosServiceImpl.editarProducto() -> Se ha producido un error al modificar un producto",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		deleteResponseDTO.setError(error);

		LOGGER.info("editarProducto() -> Salida del servicio para modificar un producto");

		return deleteResponseDTO;
	}
	
	//Reactiva o elimina fisicamente (si no tiene ninguna solicitud de compra o compra existente) o logicamente (en caso de tener solicitud de compra o compra existente, le establece la fechabaja a hoy) un producto
	@Override
	@Transactional(rollbackFor = Exception.class)
	public DeleteResponseDTO reactivarBorradoFisicoLogicoProductos(ListaProductosDTO listadoProductos, HttpServletRequest request) throws Exception {
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		int status = 0;
		int statusBorradoIdentificador = 0;
		int statusBorradoFormasDePagoProducto = 0;
		IdPeticionDTO idPeticionDTO = new IdPeticionDTO();
		

		LOGGER.info("reactivarBorradoFisicoLogicoProductos() -> Entrada al servicio para borrar fisicamente o logicamente o reactivar un producto");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);


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
						"reactivarBorradoFisicoLogicoProductos() / pysTiposProductosExtendsMapper.reactivarBorradoFisicoLogicoProductos() -> Entrada a pysTiposProductosExtendsMapper para borrar fisicamente o logicamente o reactivar un producto");
				
				for (ListaProductosItem producto : listadoProductos.getListaProductosItems()) {
						
					//Comprueba que haya solicitud de compra
					if(pysTiposProductosExtendsMapper.comprobarSolicitudProducto(producto, idInstitucion) != null)
						idPeticionDTO.setIdpeticionSolicitud(pysTiposProductosExtendsMapper.comprobarSolicitudProducto(producto, idInstitucion)); //Tener en cuenta que comprobarSolicitudProducto no devuelve solo un id si no uno por cada solicitud, por eso se usa una lista.
											
					//Borrado logico --> Actualizamos la fechabaja del producto a la actual (sysdate)
						//Borrado fisico --> Eliminamos las formas de pago del producto, registro del producto y posteriormente el identificador
					if(idPeticionDTO.getIdpeticionSolicitud().size() > 0 ) { //Borrado logico ya que comprobarSolicitudProducto devolvio resultado por lo que el producto tiene alguna compra o solicitud de compra
						status = pysTiposProductosExtendsMapper.borradoLogicoProductos(usuarios.get(0), producto, idInstitucion);
						if(status == 0) {
							throw new Exception("No se pudo realizar la baja logica del producto");
						}else if(status == 1) {
							LOGGER.info(
									"reactivarBorradoFisicoLogicoProductos() / pysTiposProductosExtendsMapper.reactivarBorradoFisicoLogicoProductos() -> Baja logica del producto realizada con exito");
						}
					}else{ //Borrado fisico al no tener ninguna compra o solicitud de compra, es decir comprobarSolicitudProducto no devolvio nada.
						//Borramos las formas de pago del producto (si es que las tiene)
						
						if(producto.getNoFacturable().equals("0")) {
							statusBorradoFormasDePagoProducto = pysTipoFormaPagoExtendsMapper.borradoFisicoFormasPagoByProducto(producto, idInstitucion);
							
							if(statusBorradoFormasDePagoProducto == 0) {
								throw new Exception("No se pudo realizar el borrado de las formas de pago del producto");
							}else if(statusBorradoFormasDePagoProducto == 1) {
								LOGGER.info(
										"reactivarBorradoFisicoLogicoProductos() / pysTipoFormaPagoExtendsMapper.borradoFisicoFormasPagoByProducto() -> Borrado de las formas de pago del producto realizado con exito");
							}
						}
						
						//Borramos el registro del producto
						status = pysTiposProductosExtendsMapper.borradoFisicoProductosRegistro(producto, idInstitucion);
						
						if(status == 0) {
							throw new Exception("No se pudo realizar el borrado fisico del producto");
						}else if(status == 1) {
							LOGGER.info(
									"reactivarBorradoFisicoLogicoProductos() / pysTiposProductosExtendsMapper.reactivarBorradoFisicoLogicoProductos() -> Borrado fisico del producto realizado con exito");
						}
						//Borramos el identificador en caso de que tuviera (si no se selecciono tipo de certificado a la hora de crearlo, no tendra)
						
						if(producto.getIdcontador() != null && !producto.getIdcontador().equals("")) {
							statusBorradoIdentificador = pysTiposProductosExtendsMapper.borradoFisicoProductosIdentificador(producto, idInstitucion);
								
							if(statusBorradoIdentificador == 0) {
								throw new Exception("No se pudo realizar el borrado del identificador");
							}else if(statusBorradoIdentificador == 1) {
								LOGGER.info(
										"reactivarBorradoFisicoLogicoProductos() / pysTiposProductosExtendsMapper.reactivarBorradoFisicoLogicoProductos() -> Borrado del identificador realizado con exito");
							}
						}
					}
				}
					
				LOGGER.info(
							"reactivarBorradoFisicoLogicoProductos() / pysTiposProductosExtendsMapper.reactivarBorradoFisicoLogicoProductos() -> Salida de pysTiposProductosExtendsMapper para borrar fisicamente o logicamente o reactivar un producto");
			}

		}

		LOGGER.info("reactivarBorradoFisicoLogicoProductos() -> Salida del servicio para borrar fisicamente o logicamente o reactivar un producto");

		return deleteResponseDTO;
	}

	//Obtiene los codigos de productos existentes en un colegio para su uso por ejemplo en validar que en ficha producto a la hora de crear/editar no se introduzca un codigo ya existente (para saber como esta formado el codigo revisar la documentacion)
	@Override
	public ListaCodigosPorColegioDTO obtenerCodigosPorColegio(HttpServletRequest request) {
		ListaCodigosPorColegioDTO listaCodigosPorColegioDTO = new ListaCodigosPorColegioDTO();
		Error error = new Error();

		LOGGER.info("obtenerCodigosPorColegio() -> Entrada al servicio para recuperar el listado de codigos en una institucion concreta");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"obtenerCodigosPorColegio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"obtenerCodigosPorColegio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"obtenerCodigosPorColegio() / pysTiposProductosExtendsMapper.obtenerCodigosPorColegio() -> Entrada a pysTiposProductosExtendsMapper para recuperar el listado de codigos en una institucion concreta");

					String idioma = usuarios.get(0).getIdlenguaje();
					List<String> listaCodigosPorColegio = pysTiposProductosExtendsMapper
							.obtenerCodigosPorColegio(idInstitucion);

					LOGGER.info(
							"obtenerCodigosPorColegio() / pysTiposProductosExtendsMapper.obtenerCodigosPorColegio() -> Salida de pysTiposProductosExtendsMapper para recuperar el listado de codigos en una institucion concreta");

					if (listaCodigosPorColegio != null && listaCodigosPorColegio.size() > 0) {
						listaCodigosPorColegioDTO.setListaCodigosPorColegio(listaCodigosPorColegio);
				}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"ProductosServiceImpl.obtenerCodigosPorColegio() -> Se ha producido un error al recuperar el listado de codigos en una institucion concreta",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		listaCodigosPorColegioDTO.setError(error);

		LOGGER.info("obtenerCodigosPorColegio() -> Salida del servicio para recuperar el listado de codigos en una institucion concreta");

		return listaCodigosPorColegioDTO;
	}
	
	public PysTipoiva getIvaDetail(HttpServletRequest request, String idTipoIva){
		PysTipoiva resultado = null;
		Error error = new Error();

		LOGGER.info("getIvaDetail() -> Entrada al servicio para recuperar la información de un tipo iva");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"getIvaDetail() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"getIvaDetail() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"getIvaDetail() / pysTipoIvaExtendsMapper.selectByPrimaryKey() -> Entrada a pysTipoIvaExtendsMapper para recuperar la información de un tipo iva");

					resultado = pysTipoIvaExtendsMapper.selectByPrimaryKey(Integer.valueOf(idTipoIva));
					
					LOGGER.info(
							"getIvaDetail() / pysTipoIvaExtendsMapper.selectByPrimaryKey() -> Salida de pysTipoIvaExtendsMapper para recuperar la información de un tipo iva");

					
				
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"ProductosServiceImpl.getIvaDetail() -> Se ha producido un error al recuperar la información de un tipo iva",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		LOGGER.info("getIvaDetail() -> Salida del servicio para recuperar la información de un tipo iva");

		return resultado;
	}
}
