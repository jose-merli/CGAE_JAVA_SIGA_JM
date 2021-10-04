package org.itcgae.siga.fac.services.impl;

import org.apache.log4j.Logger;
import org.itcgae.siga.fac.services.IFacturacionPySService;
import org.springframework.stereotype.Service;

@Service
public class FacturacionPySServiceImpl implements IFacturacionPySService {

	private Logger LOGGER = Logger.getLogger(FacturacionPySServiceImpl.class);

//	@Autowired
//	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

//	@Override
//	public ListadoTipoProductoDTO searchTiposProductos(HttpServletRequest request) {
//		ListadoTipoProductoDTO listadoTipoProductoDTO = new ListadoTipoProductoDTO();
//		Error error = new Error();
//
//		LOGGER.info("searchTiposProductos() -> Entrada al servicio para recuperar el listado de tipos de productos");
//
//		// Conseguimos información del usuario logeado
//		String token = request.getHeader("Authorization");
//		String dni = UserTokenUtils.getDniFromJWTToken(token);
//		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
//
//		try {
//			if (idInstitucion != null) {
//				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
//				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
//
//				LOGGER.info(
//						"searchTiposProductos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
//
//				LOGGER.info(
//						"searchTiposProductos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//				if (usuarios != null && !usuarios.isEmpty()) {
//					LOGGER.info(
//							"searchTiposProductos() / pysTiposProductosExtendsMapper.searchTiposProductos() -> Entrada a pysTiposProductosExtendsMapper para obtener el listado de tipos de productos");
//
//					String idioma = usuarios.get(0).getIdlenguaje();
//					List<TiposProductosItem> listaProductos = pysTiposProductosExtendsMapper
//							.searchTiposProductos(idioma, idInstitucion);
//
//					LOGGER.info(
//							"searchTiposProductos() / pysTiposProductosExtendsMapper.searchTiposProductos() -> Salida de pysTiposProductosExtendsMapper para obtener el listado de tipos de productos");
//
//					if (listaProductos != null && listaProductos.size() > 0) {
//						listadoTipoProductoDTO.setTiposProductosItems(listaProductos);
//					}
//				}
//
//			}
//		} catch (Exception e) {
//			LOGGER.error(
//					"TiposProductosServiceImpl.searchTiposProductos() -> Se ha producido un error al obtener el listado de tipos de productos",
//					e);
//			error.setCode(500);
//			error.setDescription("general.mensaje.error.bbdd");
//		}
//
//		listadoTipoProductoDTO.setError(error);
//
//		LOGGER.info("searchTiposProductos() -> Salida del servicio para obtener el listado de tipos de productos");
//
//		return listadoTipoProductoDTO;
//	}
	
}
