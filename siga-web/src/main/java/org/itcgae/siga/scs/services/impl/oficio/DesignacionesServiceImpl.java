package org.itcgae.siga.scs.services.impl.oficio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.scs.ColegiadosSJCSItem;
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
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
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
	
	@Autowired
	private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;
	
	@Override
	public List<JustificacionExpressItem> busquedaJustificacionExpres(JustificacionExpressItem item, HttpServletRequest request) {
		List <JustificacionExpressItem> result = null;
		
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		StringDTO parametros = new StringDTO();
		String idPersona = null;
		
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info("DesignacionesServiceImpl.busquedaJustificacionExpres() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("DesignacionesServiceImpl.busquedaJustificacionExpres -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("DesignacionesServiceImpl.busquedaJustificacionExpres -> Entrada a servicio para la busqueda de justifiacion express");
				
				try {
					//cargamos los parámetros necesarios	
					String longitudCodEJG;
					
					//LONGITUD_CODEJG
					parametros = genParametrosExtendsMapper.selectParametroPorInstitucion("LONGITUD_CODEJG", idInstitucion.toString());
					
					//si el ncolegiado, viene relleno, debemos obtener la idpersona
					if(item.getnColegiado()!=null && !item.getnColegiado().trim().isEmpty()) {
						//obtenemos la idpersona
						ColegiadosSJCSItem colegiadosSJCSItem = new ColegiadosSJCSItem();
						colegiadosSJCSItem.setnColegiado(item.getnColegiado());
						
						List<ColegiadosSJCSItem> colegiadosSJCSItems = cenColegiadoExtendsMapper.busquedaColegiadosSJCS(idInstitucion.toString(), colegiadosSJCSItem);
						
						if(colegiadosSJCSItems.size()>0) {
							idPersona = colegiadosSJCSItems.get(0).getIdPersona();
						}
					}
					
					//comprobamos la longitud para la institucion, si no tiene nada, cogemos el de la institucion 0
					if(parametros != null && parametros.getValor()!=null) {
						longitudCodEJG = parametros.getValor();
					}else {
						parametros = genParametrosExtendsMapper.selectParametroPorInstitucion("LONGITUD_CODEJG", "0");
						longitudCodEJG = parametros.getValor();
					}
					
					result = scsDesignacionesExtendsMapper.busquedaJustificacionExpres(item, idInstitucion.toString(), longitudCodEJG, idPersona);
					
					//cogemos los expedientes devueltos de la consulta y los tratamos para el front
					for(int i=0; i<result.size(); i++){						
						List<String> expedientes = new ArrayList<String>();
						
						if(result.get(i).getEjgs()!=null && !result.get(i).getEjgs().isEmpty()) {
							String[] parts = result.get(i).getEjgs().split(",");
														
							for(String str : parts) {
								if(str.indexOf("##")!=-1) {
									expedientes.add(str.substring(0, str.indexOf("##")).trim());
								}
							}
						}
						
						if(expedientes.size()>0) {
							result.get(i).setExpedientes(expedientes);
						}
					}
					
					LOGGER.info("DesignacionesServiceImpl.busquedaJustificacionExpres -> Salida del servicio");
				}catch (Exception e){
					LOGGER.error("DesignacionesServiceImpl.busquedaJustificacionExpres -> ERROR: al consultar datos de la bd. ", e);
				}
			}
		}
		
		return result;
	}	
	
	@Override
	public List<DesignaItem> busquedaDesignas(DesignaItem designaItem, HttpServletRequest request) {
		DesignaItem result = new DesignaItem();
		List<DesignaItem> designas = null;
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
				
				try {
				designas = scsDesignacionesExtendsMapper.busquedaDesignaciones(designaItem, idInstitucion, tamMaximo);
				}catch(Exception e) {
					LOGGER.error(e.getMessage());
					LOGGER.info("DesignacionesServiceImpl.busquedaDesignas -> Salida del servicio");
				}
				LOGGER.info("DesignacionesServiceImpl.busquedaDesignas -> Salida del servicio");
			}
		}
		
		return designas;
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