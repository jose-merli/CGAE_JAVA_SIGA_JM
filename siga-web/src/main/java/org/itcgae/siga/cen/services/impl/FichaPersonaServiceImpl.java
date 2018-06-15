package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.AsociarPersonaDTO;
import org.itcgae.siga.DTOs.cen.DesasociarPersonaDTO;
import org.itcgae.siga.DTOs.cen.FichaPerSearchDTO;
import org.itcgae.siga.DTOs.cen.FichaPersonaDTO;
import org.itcgae.siga.DTOs.cen.FichaPersonaItem;
import org.itcgae.siga.cen.services.IFichaPersonaService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenNocolegiado;
import org.itcgae.siga.db.entities.CenNocolegiadoExample;
import org.itcgae.siga.db.mappers.CenNocolegiadoMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FichaPersonaServiceImpl implements IFichaPersonaService{

	private Logger LOGGER = Logger.getLogger(FichaPersonaServiceImpl.class);
	
	@Autowired
	private CenNocolegiadoMapper cenNoColegiadoMapper;
	@Autowired
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Override
	public FichaPersonaDTO searchPersonFile(int numPagina, FichaPerSearchDTO fichaPerSearch,
			HttpServletRequest request) {
		
		List<CenNocolegiado> colegiadoList = new ArrayList<CenNocolegiado>();
		CenNocolegiado colegiado = new CenNocolegiado();
		FichaPersonaDTO fichaPersona = new FichaPersonaDTO();
		
		LOGGER.info(
				"searchPersonFile() -> Entrada al servicio para la recuperar la ficha de persona");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		fichaPerSearch.setIdInstitucion(String.valueOf(idInstitucion));
		
		if(null != fichaPerSearch.getTipoPersona() && fichaPerSearch.getTipoPersona().equals(SigaConstants.TIPO_PERSONA_NOTARIO)) {
			CenNocolegiadoExample example = new CenNocolegiadoExample();
			example.createCriteria().andIdpersonaEqualTo(Long.valueOf(fichaPerSearch.getIdPersona()));
			colegiadoList = cenNoColegiadoMapper.selectByExample(example);
		}
		
		if(null != colegiadoList && !colegiadoList.isEmpty()) {
			colegiado = colegiadoList.get(0);
		}
		
		if(null != colegiado.getIdpersonanotario()) {
			List<FichaPersonaItem> fichaPersonaItem = cenPersonaExtendsMapper.searchPersonFile(idInstitucion, colegiado.getIdpersonanotario());
			if(null != fichaPersonaItem) {
				fichaPersona.setFichaPersonaItem(fichaPersonaItem);
			}
		}
			
		return fichaPersona;
	}

	@Override
	public UpdateResponseDTO disassociatePerson(DesasociarPersonaDTO desasociarPersona, HttpServletRequest request) {
		List<CenNocolegiado> colegiadoList = new ArrayList<CenNocolegiado>();
		CenNocolegiado colegiado = new CenNocolegiado();
		UpdateResponseDTO updateResponse = new UpdateResponseDTO();
		org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
		updateResponse.setError(error);
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		
		//Obtenemos el usuario que modifica 
		AdmUsuarios usuario = new AdmUsuarios();
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		if (null != usuarios && usuarios.size() > 0) {
		usuario = usuarios.get(0);
		}
		
		
		if(null != desasociarPersona.getTipoPersona() && desasociarPersona.getTipoPersona().equals(SigaConstants.TIPO_PERSONA_NOTARIO)) {
			CenNocolegiadoExample example = new CenNocolegiadoExample();
			example.createCriteria().andIdpersonaEqualTo(Long.valueOf(desasociarPersona.getIdPersona())).andIdinstitucionEqualTo(idInstitucion);
			colegiadoList = cenNoColegiadoMapper.selectByExample(example);
		}
		
		if(null != colegiadoList && !colegiadoList.isEmpty()) {
			colegiado = colegiadoList.get(0);
		}else {
			updateResponse.getError().setDescription("La persona seleccionada no es el notario de la sociedad.");
			return updateResponse;
		}
		
		
		if(null != colegiado.getIdpersonanotario()) {
			if(colegiado.getIdpersonanotario().equals(desasociarPersona.getIdPersonaDesasociar())) {
				CenNocolegiadoExample cenNoColegiadoExample = new CenNocolegiadoExample();
				cenNoColegiadoExample.createCriteria().andIdpersonaEqualTo(Long.valueOf(desasociarPersona.getIdPersona())).andIdinstitucionEqualTo(idInstitucion);
				colegiado.setIdpersonanotario(null);
				colegiado.setFechamodificacion(new Date());
				colegiado.setUsumodificacion(usuario.getIdusuario());
				cenNoColegiadoMapper.updateByExampleSelective(colegiado, cenNoColegiadoExample);
				updateResponse.setStatus("OK");
			}else {
				updateResponse.getError().setDescription("La persona seleccionada no es el notario de la sociedad.");
			}
			
		}
		return updateResponse;
	}


	@Override
	public UpdateResponseDTO savePerson(AsociarPersonaDTO asociarPersona, HttpServletRequest request) {
		List<CenNocolegiado> colegiadoList = new ArrayList<CenNocolegiado>();
		CenNocolegiado colegiado = new CenNocolegiado();
		UpdateResponseDTO updateResponse = new UpdateResponseDTO();
		org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
		updateResponse.setError(error);
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if(null != asociarPersona.getTipoPersona() && asociarPersona.getTipoPersona().equals(SigaConstants.TIPO_PERSONA_NOTARIO)) {
			CenNocolegiadoExample example = new CenNocolegiadoExample();
			example.createCriteria().andIdpersonaEqualTo(Long.valueOf(asociarPersona.getIdPersona())).andIdinstitucionEqualTo(idInstitucion);
			colegiadoList = cenNoColegiadoMapper.selectByExample(example);
		}
		
		if(null != colegiadoList && !colegiadoList.isEmpty()) {
			colegiado = colegiadoList.get(0);
		}else {
			updateResponse.setStatus("KO");
			updateResponse.getError().setDescription("No existen registros con idPersona: " + asociarPersona.getIdPersona());
			return updateResponse;
		}
		
		
		if(null != colegiado.getIdpersonanotario()) {
			if(colegiado.getIdpersonanotario().equals(asociarPersona.getIdPersonaAsociar())) {
				updateResponse.getError().setDescription("La persona seleccionada ya es el notario de la sociedad.");
			}else {
				CenNocolegiadoExample cenNoColegiadoExample = new CenNocolegiadoExample();
				cenNoColegiadoExample.createCriteria().andIdpersonaEqualTo(Long.valueOf(asociarPersona.getIdPersona())).andIdinstitucionEqualTo(idInstitucion);
				colegiado.setIdpersonanotario(Long.valueOf(asociarPersona.getIdPersonaAsociar()));
				cenNoColegiadoMapper.updateByExampleSelective(colegiado, cenNoColegiadoExample);
				updateResponse.setStatus("OK");
				
			}
			
		}
		return updateResponse;
	}
	

}
