package org.itcgae.siga.fac.services.impl;

import java.util.List;


import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.ListadoTipoProductoDTO;
import org.itcgae.siga.DTO.fac.TiposProductosItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.PySTiposProductosExtendsMapper;
import org.itcgae.siga.fac.services.ITiposProductosService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TiposProductosServiceImpl implements ITiposProductosService {

	private Logger LOGGER = Logger.getLogger(TiposProductosServiceImpl.class);

	@Autowired
	private PySTiposProductosExtendsMapper pysTiposProductosExtendsMapper;
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Override
	public ListadoTipoProductoDTO searchTiposProductos(HttpServletRequest request) {
		ListadoTipoProductoDTO listadoTipoProductoDTO = new ListadoTipoProductoDTO();
		Error error = new Error();

		LOGGER.info("searchTiposProductos() -> Entrada al servicio para recuperar el listado de productos");

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
							"searchTiposProductos() / pysTiposProductosExtendsMapper.searchTiposProductos() -> Entrada a pysTiposProductosExtendsMapper para obtener el listado de productos");

					String idioma = usuarios.get(0).getIdlenguaje();
					List<TiposProductosItem> listaProductos = pysTiposProductosExtendsMapper
							.searchTiposProductos(idioma, idInstitucion);

					LOGGER.info(
							"searchTiposProductos() / pysTiposProductosExtendsMapper.searchTiposProductos() -> Salida de pysTiposProductosExtendsMapper para obtener el listado de productos");

					if (listaProductos != null && listaProductos.size() > 0) {
						listadoTipoProductoDTO.setTiposProductosItems(listaProductos);
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error(
					"TiposProductosServiceImpl.searchTiposProductos() -> Se ha producido un error al obtener el listado de productos",
					e);
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		listadoTipoProductoDTO.setError(error);

		LOGGER.info("searchTiposProductos() -> Salida del servicio para obtener el listado de productos");

		return listadoTipoProductoDTO;
	}

}
