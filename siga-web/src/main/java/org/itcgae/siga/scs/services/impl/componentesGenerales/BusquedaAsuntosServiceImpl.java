package org.itcgae.siga.scs.services.impl.componentesGenerales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableDTO;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsAsistenciaExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsDesignacionesExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsEjgExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsSojExtendsMapper;
import org.itcgae.siga.scs.services.componentesGenerales.BusquedaAsuntosService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusquedaAsuntosServiceImpl implements BusquedaAsuntosService {

	private Logger LOGGER = Logger.getLogger(BusquedaAsuntosServiceImpl.class);
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private ScsEjgExtendsMapper scsEjgExtendsMapper;
	
	@Autowired
	private ScsAsistenciaExtendsMapper scsAsistenciaExtendsMapper;
	
	@Autowired
	private ScsDesignacionesExtendsMapper scsDesignacionesExtendsMapper;
	
	@Autowired
	private ScsSojExtendsMapper scsSojExtendsMapper;
	
	@Override
	public AsuntosJusticiableDTO searchClaveAsuntosEJG(HttpServletRequest request, AsuntosJusticiableItem asuntosJusticiableItem) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		AsuntosJusticiableDTO asuntosJusticiableDTO = new AsuntosJusticiableDTO();
		List<AsuntosJusticiableItem> asuntosJusticiableItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchClaveAsuntosEJG() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchClaveAsuntosEJG() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchClaveAsuntosEJG() / scsEjgExtendsMapper.searchClaveAsuntosEJG() -> Entrada a scsEjgExtendsMapper para obtener los AsuntosEJG");

				asuntosJusticiableItems = scsEjgExtendsMapper.searchClaveAsuntosEJG(asuntosJusticiableItem);

				LOGGER.info(
						"searchClaveAsuntosEJG() / scsEjgExtendsMapper.searchClaveAsuntosEJG() -> Salida a scsEjgExtendsMapper para obtener los AsuntosEJG");

				if (asuntosJusticiableItems != null) {
					asuntosJusticiableDTO.setAsuntosJusticiableItems(asuntosJusticiableItems);
				}
			}

		}
		LOGGER.info("searchClaveAsuntosEJG() -> Salida del servicio para obtener los Asistencias");
		return asuntosJusticiableDTO;

	}

	@Override
	public AsuntosJusticiableDTO searchClaveAsuntosAsistencias(HttpServletRequest request, AsuntosJusticiableItem asuntosJusticiableItem) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		AsuntosJusticiableDTO asuntosJusticiableDTO = new AsuntosJusticiableDTO();
		List<AsuntosJusticiableItem> asuntosJusticiableItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchClaveAsuntosAsistencias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchClaveAsuntosAsistencias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchClaveAsuntosAsistencias() / scsAsistenciaExtendsMapper.searchClaveAsuntosAsistencias() -> Entrada a cenTiposolicitudSqlExtendsMapper para obtener los Asistencias");

				asuntosJusticiableItems = scsAsistenciaExtendsMapper.searchClaveAsistencia(asuntosJusticiableItem);

				LOGGER.info(
						"searchClaveAsuntosAsistencias() / scsAsistenciaExtendsMapper.searchClaveAsuntosAsistencias() -> Salida a cenTiposolicitudSqlExtendsMapper para obtener los Asistencias");

				if (asuntosJusticiableItems != null) {
					asuntosJusticiableDTO.setAsuntosJusticiableItems(asuntosJusticiableItems);
				}
			}

		}
		LOGGER.info("searchClaveAsuntosAsistencias() -> Salida del servicio para obtener los Asistencias");
		return asuntosJusticiableDTO;

	}
	

	@Override
	public AsuntosJusticiableDTO searchClaveAsuntosDesignaciones(HttpServletRequest request, AsuntosJusticiableItem asuntosJusticiableItem) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		AsuntosJusticiableDTO asuntosJusticiableDTO = new AsuntosJusticiableDTO();
		List<AsuntosJusticiableItem> asuntosJusticiableItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchClaveAsuntosDesignaciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchClaveAsuntosDesignaciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchClaveAsuntosDesignaciones() / scsEjgExtendsMapper.searchClaveAsuntosDesignaciones() -> Entrada a cenTiposolicitudSqlExtendsMapper para obtener los AsuntosEJG");

				asuntosJusticiableItems = scsDesignacionesExtendsMapper.searchClaveDesignaciones(asuntosJusticiableItem);

				LOGGER.info(
						"searchClaveAsuntosDesignaciones() / scsEjgExtendsMapper.searchClaveAsuntosDesignaciones() -> Salida a cenTiposolicitudSqlExtendsMapper para obtener los AsuntosEJG");

				if (asuntosJusticiableItems != null) {
					asuntosJusticiableDTO.setAsuntosJusticiableItems(asuntosJusticiableItems);
				}
			}

		}
		LOGGER.info("searchClaveAsuntosDesignaciones() -> Salida del servicio para obtener los AsuntosEJG");
		return asuntosJusticiableDTO;

	}
	
	
	@Override
	public AsuntosJusticiableDTO searchClaveAsuntosSOJ(HttpServletRequest request, AsuntosJusticiableItem asuntosJusticiableItem) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		AsuntosJusticiableDTO asuntosJusticiableDTO = new AsuntosJusticiableDTO();
		List<AsuntosJusticiableItem> asuntosJusticiableItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchClaveAsuntosSOJ() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchClaveAsuntosSOJ() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchClaveAsuntosSOJ() / scsEjgExtendsMapper.searchClaveAsuntosSOJ() -> Entrada a cenTiposolicitudSqlExtendsMapper para obtener los AsuntosEJG");

				asuntosJusticiableItems = scsSojExtendsMapper.searchClaveSoj(asuntosJusticiableItem);

				LOGGER.info(
						"searchClaveAsuntosSOJ() / scsEjgExtendsMapper.searchClaveAsuntosSOJ() -> Salida a cenTiposolicitudSqlExtendsMapper para obtener los AsuntosEJG");

				if (asuntosJusticiableItems != null) {
					asuntosJusticiableDTO.setAsuntosJusticiableItems(asuntosJusticiableItems);
				}
			}

		}
		LOGGER.info("searchClaveAsuntosSOJ() -> Salida del servicio para obtener los AsuntosEJG");
		return asuntosJusticiableDTO;

	}
	
	
	
}
