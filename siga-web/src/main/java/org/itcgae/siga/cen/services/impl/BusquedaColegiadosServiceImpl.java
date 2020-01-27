package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.ComboInstitucionDTO;
import org.itcgae.siga.DTOs.cen.ComboInstitucionItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.IBusquedaColegiadosService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenClienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenEstadocivilExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenEstadocolegialExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposcvExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.itcgae.siga.DTOs.gen.Error;


@Service
public class BusquedaColegiadosServiceImpl implements IBusquedaColegiadosService{
	
	private Logger LOGGER = Logger.getLogger(BusquedaColegiadosServiceImpl.class);
	
	@Autowired
	private CenEstadocivilExtendsMapper cenEstadocivilExtendsMapper;
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenEstadocolegialExtendsMapper cenEstadocolegialExtendsMapper;
	
	@Autowired
	private CenClienteExtendsMapper cenClienteExtendsMapper;
	
	@Autowired
	private CenTiposcvExtendsMapper cenTiposcvExtendsMapper;
	
	@Autowired
	private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;
	
	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;
	
	@Override
	public ComboDTO getCivilStatus(HttpServletRequest request) {
		
		LOGGER.info("getCivilStatus() -> Entrada al servicio para obtener los tipos de estado civil");
		
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getCivilStatus() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getCivilStatus() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getCivilStatus() / cenEstadocivilExtendsMapper.distinctCivilStatus() -> Entrada a cenEstadocivilExtendsMapper para obtener los diferentes tipos de estados civiles");
				comboItems = cenEstadocivilExtendsMapper.distinctCivilStatus(usuario.getIdlenguaje());
				LOGGER.info(
						"getCivilStatus() / cenEstadocivilExtendsMapper.distinctCivilStatus() -> Salida de cenEstadocivilExtendsMapper para obtener los diferentes tipos de estados civiles");
			}
		}
		
		comboDTO.setCombooItems(comboItems);
		
		LOGGER.info("getCivilStatus() -> Salida del servicio para obtener los tipos de estado civil");
		
		return comboDTO;
	}

	@Override
	public ComboDTO getSituacion(HttpServletRequest request) {
		
		LOGGER.info("getSituacion() -> Entrada al servicio para obtener los tipos situacion de un colegiado");
		
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getSituacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getSituacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getSituacion() / cenEstadocolegialExtendsMapper.distinctCivilStatus() -> Entrada a cenEstadocolegialExtendsMapper para obtener los diferentes tipos de situacion de un colegiado");
				comboItems = cenEstadocolegialExtendsMapper.distinctSituacionColegial(usuario.getIdlenguaje());
				LOGGER.info(
						"getSituacion() / cenEstadocolegialExtendsMapper.distinctCivilStatus() -> Salida de cenEstadocolegialExtendsMapper para obtener los diferentes tipos de situacion de un colegiado");
				
//				ComboItem comboItem = new ComboItem();
//				comboItem.setLabel("");
//				comboItem.setValue("");
//				comboItems.add(0, comboItem);
				
			}
		}
		
		comboDTO.setCombooItems(comboItems);
		
		LOGGER.info("getSituacion() -> Salida del servicio para obtener los tipos situacion de un colegiado");
		
		return comboDTO;
	}

	@Override
	public ComboDTO getCVCategory(HttpServletRequest request) {
		
		LOGGER.info("getCVCategory() -> Entrada al servicio para obtener los tipos de categorías curriculares");
		
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getCVCategory() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getCVCategory() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getCVCategory() / cenTiposcvExtendsMapper.selectCategoriaCV() -> Entrada a cenTiposcvExtendsMapper para obtener los diferentes tipos de categorías curriculares");
				comboItems = cenTiposcvExtendsMapper.selectCategoriaCV(usuario.getIdlenguaje());
				LOGGER.info(
						"getCVCategory() / cenTiposcvExtendsMapper.selectCategoriaCV() -> Salida de cenTiposcvExtendsMapper para obtener los diferentes tipos de categorías curriculares");				
			}
		}
		
		comboDTO.setCombooItems(comboItems);
		
		LOGGER.info("getCVCategory() -> Salida del servicio para obtener los tipos de categorías curriculares");
		
		return comboDTO;
	}
	
	@Override
	public ComboInstitucionDTO getLabel(HttpServletRequest request) {
		LOGGER.info("getLabel() -> Entrada al servicio para obtener los de grupos de clientes");
		List<ComboInstitucionItem> comboItem = new ArrayList<ComboInstitucionItem>();
		ComboInstitucionDTO comboDTO = new ComboInstitucionDTO();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getLabel() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getLabel() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if(null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				// la consulta necesita idinstitucion de token y idlenguaje del usuario logeado
				usuario.setIdinstitucion(idInstitucion);
				LOGGER.info("getLabel() / cenGruposclienteExtendsMapper.getLabel() -> Entrada a cenGruposclienteExtendsMapper para obtener lista de tipos de colegios");
				comboItem = cenColegiadoExtendsMapper.getLabel(usuario);
				LOGGER.info("getLabel() / cenGruposclienteExtendsMapper.getLabel() -> Salida de cenGruposclienteExtendsMapper para obtener lista de tipos de colegios");
				comboDTO.setComboInstitucionItem(comboItem);
			}
			else {
				LOGGER.warn("getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		}
		else {
			LOGGER.warn("getLabel() -> idInstitucion del token nula");
		}
		
		LOGGER.info("getLabel() -> Salida del servicio para obtener los de grupos de clientes");
		return comboDTO;
	}
	
	@Override
	public ColegiadoDTO searchColegiado(ColegiadoItem colegiadoItem, HttpServletRequest request) {

		LOGGER.info("searchColegiado() -> Entrada al servicio para obtener colegiados");
		Error error = new Error();
		ColegiadoDTO colegiadosDTO = new ColegiadoDTO();
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		
		List<ColegiadoItem> colegiadoItemList = new ArrayList<ColegiadoItem>();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			if(null !=colegiadoItem.getSearchLoggedUser() && colegiadoItem.getSearchLoggedUser()) {
				colegiadoItem.setNif(dni);
			}
		GenParametrosExample genParametrosExample = new GenParametrosExample();
	    genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG").andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idInstitucion));
	    genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
	    LOGGER.info("searchColegiado() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
	    tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
	    LOGGER.info("searchColegiado() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
        if (tamMax != null) {
            tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
        } else {
            tamMaximo = null;
        }
	    
		colegiadoItemList = cenColegiadoExtendsMapper.selectColegiados(idInstitucion, colegiadoItem, tamMaximo);
		colegiadosDTO.setColegiadoItem(colegiadoItemList);
		if((colegiadoItemList != null) && tamMaximo != null && (colegiadoItemList.size()) > tamMaximo) {
			error.setCode(200);
			error.setDescription("La consulta devuelve más de " + tamMaximo + " resultados, pero se muestran sólo los " + tamMaximo + " más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
			colegiadosDTO.setError(error);
			}
			if (colegiadoItemList == null || colegiadoItemList.size() == 0) {

				LOGGER.warn(
						"searchColegiado() / cenColegiadoExtendsMapper.searchColegiado() -> No existen colegiados con las condiciones recibidas en la Institucion = "
								+ idInstitucion);
			}

		} else {
			LOGGER.warn("searchColegiado() -> idInstitucion del token nula");
		}

		return colegiadosDTO;
	}
	@Override
	public ColegiadoDTO searchColegiadoFicha(ColegiadoItem colegiadoItem, HttpServletRequest request) {

		LOGGER.info("searchColegiado() -> Entrada al servicio para obtener colegiados");

		ColegiadoDTO colegiadosDTO = new ColegiadoDTO();
		
		
		List<ColegiadoItem> colegiadoItemList = new ArrayList<ColegiadoItem>();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			if(null !=colegiadoItem.getSearchLoggedUser() && colegiadoItem.getSearchLoggedUser()) {
				colegiadoItem.setNif(dni);


			}
			colegiadoItemList = cenColegiadoExtendsMapper.selectColegiado(idInstitucion, colegiadoItem);
			colegiadosDTO.setColegiadoItem(colegiadoItemList);

			if (colegiadoItemList == null || colegiadoItemList.size() == 0) {

				LOGGER.warn(
						"searchColegiado() / cenColegiadoExtendsMapper.searchColegiado() -> No existen colegiados con las condiciones recibidas en la Institucion = "
								+ idInstitucion);
			}

		} else {
			LOGGER.warn("searchColegiado() -> idInstitucion del token nula");
		}

		return colegiadosDTO;
	}

}
