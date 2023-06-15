package org.itcgae.siga.fac.services.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.FichaCompraSuscripcionItem;
import org.itcgae.siga.DTO.fac.FiltrosCompraProductosItem;
import org.itcgae.siga.DTO.fac.ListaCompraProductosDTO;
import org.itcgae.siga.DTO.fac.ListaCompraProductosItem;
import org.itcgae.siga.DTO.fac.ListaProductosCompraDTO;
import org.itcgae.siga.DTO.fac.ListaProductosCompraItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesNumeros;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PysPeticioncomprasuscripcionExtendsMapper;
import org.itcgae.siga.fac.services.ICompraProductosService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompraProductosServiceImpl implements ICompraProductosService{

	private Logger LOGGER = Logger.getLogger(CompraProductosServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;
	
	@Autowired
	private GestionFichaCompraSuscripcionServiceImpl gestionFichaCompraSuscripcionServiceImpl;
	
	@Autowired
	private PysPeticioncomprasuscripcionExtendsMapper pysPeticioncomprasuscripcionExtendsMapper;
	
	@Override
	public ComboDTO comboEstadoFactura(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();
		Error error = new Error();

		LOGGER.debug("comboEstadoFactura() -> Entrada al servicio para recuperar el combo de estados de factura");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"comboEstadoFactura() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"comboEstadoFactura() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {
					LOGGER.info(
							"comboEstadoFactura() / pysPeticioncomprasuscripcionExtendsMapper.comboEstadoFactura() -> Entrada a pysPeticioncomprasuscripcionExtendsMapper para recuperar el combo de estados de factura");

					String idioma = usuarios.get(0).getIdlenguaje();
					List<ComboItem> listaComboEstadosFactura = pysPeticioncomprasuscripcionExtendsMapper.comboEstadoFactura(idioma);

					LOGGER.info(
							"comboEstadoFactura() / pysPeticioncomprasuscripcionExtendsMapper.comboEstadoFactura() -> Salida de pysPeticioncomprasuscripcionExtendsMapper para recuperar el combo de estados de factura");

					if (listaComboEstadosFactura != null && listaComboEstadosFactura.size() > 0) {
						comboDTO.setCombooItems(listaComboEstadosFactura);
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"ProductosServiceImpl.comboEstadoFactura() -> Se ha producido un error al recuperar el combo de estados de factura",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		comboDTO.setError(error);

		LOGGER.debug("comboEstadoFactura() -> Salida del servicio para recuperar el combo de estados de factura");

		return comboDTO;
	}
	
	@Override
	public ListaCompraProductosDTO getListaCompraProductos(HttpServletRequest request, FiltrosCompraProductosItem peticion) {
		ListaCompraProductosDTO listaCompraProductos = new ListaCompraProductosDTO();
		Error error = new Error();

		LOGGER.debug(
				"getListaCompraProductos() -> Entrada al servicio para recuperar la lista de compras de productos");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				LOGGER.info(
						"getListaCompraProductos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"getListaCompraProductos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (usuarios != null && !usuarios.isEmpty()) {

					//Para obtener toda la informacion de una compra/suscripcion ya creada
						LOGGER.info(
								"getListaCompraProductos() / pysPeticioncomprasuscripcionExtendsMapper.getListaCompras() -> Entrada a PysPeticioncomprasuscripcionExtendsMapper para obtener las peticiones de compra que cumplan las condiciones");

						// Se parametriza el número de resultados en función del parámetro TAM_MAX_CONSULTA_JG
						Integer tamMax = UtilidadesNumeros.tryParseInt(getParametro(SigaConstants.MODULO_SCS, SigaConstants.TAM_MAX_CONSULTA_JG, idInstitucion), null);
						listaCompraProductos.setListaCompraProductosItems(pysPeticioncomprasuscripcionExtendsMapper.getListaCompras(peticion, idInstitucion, usuarios.get(0).getIdlenguaje(), tamMax));
						
						//Revisamos las fechas obtenidas para determinar el idestado que se devuelve
						for(ListaCompraProductosItem compraProductos : listaCompraProductos.getListaCompraProductosItems()) {
							if(compraProductos.getFechaAnulada() != null) compraProductos.setIdEstadoSolicitud("5");
							else if(compraProductos.getFechaSolicitadaAnulacion() != null) compraProductos.setIdEstadoSolicitud("4");
							else if(compraProductos.getFechaEfectiva() != null) compraProductos.setIdEstadoSolicitud("3");
							else if(compraProductos.getFechaDenegada() != null) compraProductos.setIdEstadoSolicitud("2");
							else compraProductos.setIdEstadoSolicitud("1");
							
							//REVISAR
//							List<ListaProductosCompraItem> productosCompra = gestionFichaCompraSuscripcionServiceImpl.getListaProductosCompra(request, compraProductos.getnSolicitud()).getListaProductosCompraItems();
//							
//							Float totalCompra = (float) 0;
//							for(ListaProductosCompraItem productoCompra : productosCompra) {
//								//(prodSol.VALOR*prodSol.cantidad)*(1+TIVA.VALOR/100)
//								totalCompra =  ((Float.parseFloat(productoCompra.getPrecioUnitario())*Float.parseFloat(productoCompra.getCantidad()))*(1+(Float.parseFloat(productoCompra.getValorIva())/100)));
//							}
//							
//							compraProductos.setImporte(totalCompra.toString());
						
						}

						// Muestra un mensaje si la lista de resultados ha alcanzado el tamaño máximo de la consulta
						if (tamMax != null && tamMax < listaCompraProductos.getListaCompraProductosItems().size()
								|| null == tamMax && 200 < listaCompraProductos.getListaCompraProductosItems().size()) {
							listaCompraProductos.getListaCompraProductosItems().remove(listaCompraProductos.getListaCompraProductosItems().size() - 1);
							error.setMessage("general.message.consulta.resultados");
						}

						
						
						LOGGER.info(
								"getListaCompraProductos() / pysPeticioncomprasuscripcionExtendsMapper.getListaCompras() -> Salida de PysPeticioncomprasuscripcionExtendsMapper para obtener las peticiones de compra que cumplan las condiciones");
					
						error.setCode(200);
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"getListaCompraProductos() -> Se ha producido un error al obtener las peticiones de compra que cumplan las condiciones",
					e);
			error.setCode(500);
		}

		LOGGER.debug(
				"getListaCompraProductos() -> Salida del servicio para obtener las peticiones de compra que cumplan las condiciones");

		listaCompraProductos.setError(error);
		
		return listaCompraProductos;
		
	}

	private String getParametro(String modulo, String parametro, Short idInstitucion) {
		GenParametrosKey keyParametros = new GenParametrosKey();
		keyParametros.setModulo(modulo);
		keyParametros.setParametro(parametro);
		keyParametros.setIdinstitucion(idInstitucion);
		GenParametros property = genParametrosExtendsMapper.selectByPrimaryKey(keyParametros);

		if (property == null) {
			keyParametros.setIdinstitucion(SigaConstants.ID_INSTITUCION_0);
			property = genParametrosExtendsMapper.selectByPrimaryKey(keyParametros);
		}

		return property != null ? property.getValor() : "";
	}


}
