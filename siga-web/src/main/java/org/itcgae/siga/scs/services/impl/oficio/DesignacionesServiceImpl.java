package org.itcgae.siga.scs.services.impl.oficio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.JustificacionExpressItem;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsDesignacionesExtendsMapper;
import org.itcgae.siga.scs.services.oficio.IDesignacionesService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Service
public class DesignacionesServiceImpl implements IDesignacionesService {

	private Logger LOGGER = Logger.getLogger(DesignacionesServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private ScsDesignacionesExtendsMapper scsDesignacionesExtendsMapper;
	
	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;
	
	@Override
	public JustificacionExpressItem busquedaJustificacionExpres(JustificacionExpressItem item, HttpServletRequest request) {
		JustificacionExpressItem result = new JustificacionExpressItem();
		
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info("DesignacionesServiceImpl.busquedaJustificacionExpres() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("DesignacionesServiceImpl.busquedaJustificacionExpres -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("DesignacionesServiceImpl.busquedaJustificacionExpres -> Entrada a servicio para la busqueda de justifiacion express");

				LOGGER.info("DesignacionesServiceImpl.busquedaJustificacionExpres -> Salida del servicio");
			}
		}
		
		return result;
	}	
	
	@Override
	public DesignaItem busquedaDesignas(DesignaItem designaItem, HttpServletRequest request) {
		DesignaItem result = new DesignaItem();
		
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		
		if (idInstitucion != null) {
			
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info("DesignacionesServiceImpl.busquedaDesignas() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("DesignacionesServiceImpl.busquedaDesignas -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			GenParametrosExample genParametrosExample = new GenParametrosExample();
		    genParametrosExample.createCriteria().andModuloEqualTo("CEN").andParametroEqualTo("TAM_MAX_BUSQUEDA_COLEGIADO").andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idInstitucion));
		    genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
		    LOGGER.info("searchColegiado() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
		    tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
		    LOGGER.info("searchColegiado() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
	        if (tamMax != null) {
	            tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
	        } else {
	            tamMaximo = null;
	        }
			
			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("DesignacionesServiceImpl.busquedaDesignas -> Entrada a servicio para la busqueda de justifiacion express");
				List<DesignaItem> designas = null;
				try {
				designas = scsDesignacionesExtendsMapper.busquedaDesignaciones(designaItem, idInstitucion, tamMaximo);
				}catch(Exception e) {
					LOGGER.info("DesignacionesServiceImpl.busquedaDesignas -> Salida del servicio");
				}
				LOGGER.info("DesignacionesServiceImpl.busquedaDesignas -> Salida del servicio");
			}
		}
		
		return result;
	}	
	
	@Override
	public ComboDTO modulo(HttpServletRequest request) {
		LOGGER.info("modulo() -> Entrada al servicio para obtener combo modulos");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {

				comboItems = scsDesignacionesExtendsMapper.comboModulos(idInstitucion);

				comboDTO.setCombooItems(comboItems);

			}
		}

		LOGGER.info("objetivo() -> Salida del servicio para obtener combo modulo");

		return comboDTO;
	}
	
	@Override
	public ComboDTO comboProcedimientos(HttpServletRequest request) {
		LOGGER.info("comboProcedimientos() -> Entrada al servicio para obtener comboProcedimientos");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (null != usuarios && usuarios.size() > 0) {

				comboItems = scsDesignacionesExtendsMapper.comboProcedimientos(idInstitucion);

				comboDTO.setCombooItems(comboItems);

			}
		}

		LOGGER.info("objetivo() -> Salida del servicio para obtener comboProcedimientos");

		return comboDTO;
	}

}