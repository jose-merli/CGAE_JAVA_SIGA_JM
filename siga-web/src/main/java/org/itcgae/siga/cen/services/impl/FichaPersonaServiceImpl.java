package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.AsociarPersonaDTO;
import org.itcgae.siga.DTOs.cen.CrearPersonaDTO;
import org.itcgae.siga.DTOs.cen.DesasociarPersonaDTO;
import org.itcgae.siga.DTOs.cen.FichaPerSearchDTO;
import org.itcgae.siga.DTOs.cen.FichaPersonaDTO;
import org.itcgae.siga.DTOs.cen.FichaPersonaItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.IFichaPersonaService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenNocolegiado;
import org.itcgae.siga.db.entities.CenNocolegiadoExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenNocolegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTipoidentificacionExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FichaPersonaServiceImpl implements IFichaPersonaService{

	private Logger LOGGER = Logger.getLogger(FichaPersonaServiceImpl.class);
	
	@Autowired
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenNocolegiadoExtendsMapper cenNocolegiadoExtendsMapper;
	
	@Autowired
	private CenTipoidentificacionExtendsMapper cenTipoidentificacionExtendsMapper;
	
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
			colegiadoList = cenNocolegiadoExtendsMapper.selectByExample(example);
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
			colegiadoList = cenNocolegiadoExtendsMapper.selectByExample(example);
		}
		
		if(null != colegiadoList && !colegiadoList.isEmpty()) {
			colegiado = colegiadoList.get(0);
		}else {
			updateResponse.getError().setDescription("La persona seleccionada no es el notario de la sociedad.");
			return updateResponse;
		}
		
		if(null != colegiado.getIdpersonanotario()) {
			if(String.valueOf(colegiado.getIdpersonanotario()).equals(desasociarPersona.getIdPersonaDesasociar())) {
				desasociarPersona.setIdInstitucion(String.valueOf(idInstitucion));
				int response =cenNocolegiadoExtendsMapper.disassociatePerson(usuario, desasociarPersona);
				if(response == 1)
					updateResponse.setStatus(SigaConstants.OK);
				else {
					updateResponse.setStatus(SigaConstants.KO);
				}
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
			colegiadoList = cenNocolegiadoExtendsMapper.selectByExample(example);
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
				cenNocolegiadoExtendsMapper.updateByExampleSelective(colegiado, cenNoColegiadoExample);
				updateResponse.setStatus("OK");
				
			}
			
		}
		return updateResponse;
	}

	@Override
	public ComboDTO createPersonFile(CrearPersonaDTO crearPersonaDTO, HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		
		AdmUsuarios usuario = new AdmUsuarios();
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		
		if (null != usuarios && usuarios.size() > 0) {
			usuario = usuarios.get(0);
			// creamos el nuevo notario
			//int response = cenPersonaExtendsMapper.insertSelectiveForPersonFile(crearPersonaDTO, usuario);
			
			
			comboItems = cenPersonaExtendsMapper.selectMaxIdPersona();
			Long nuevoIdPersona = Long.valueOf(comboItems.get(0).getValue()) + 1; 
			CenPersona record = new CenPersona();
			record.setApellidos1(crearPersonaDTO.getApellido1());
			record.setApellidos2(crearPersonaDTO.getApellido2());
			record.setFallecido("0");
			record.setFechamodificacion(new Date());
			record.setFechanacimiento(null);
			record.setIdestadocivil(null);
			record.setIdpersona(nuevoIdPersona);
			record.setIdtipoidentificacion(Short.valueOf(crearPersonaDTO.getTipoIdentificacion()));
			record.setNaturalde(null);
			record.setNifcif(crearPersonaDTO.getNif());
			record.setNombre(crearPersonaDTO.getNombre());
			record.setSexo(null);
			record.setUsumodificacion(usuario.getIdusuario());
		
			cenPersonaExtendsMapper.insert(record);
			
			// obtenemos su idpersona
			comboItems = cenPersonaExtendsMapper.selectMaxIdPersona();
		}
		
		if(comboItems.isEmpty()) {
			comboDTO.getError().description("no se encuentra el idpersona de notario");
		}
		
		comboDTO.setCombooItems(comboItems);
		
		return comboDTO;
	}

	@Override
	public ComboDTO getIdentificationTypes(HttpServletRequest request) {
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String dni = UserTokenUtils.getDniFromJWTToken(token);

		AdmUsuarios usuario = new AdmUsuarios();
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

		if (null != usuarios && usuarios.size() > 0) {
			usuario = usuarios.get(0);

			comboItems = cenTipoidentificacionExtendsMapper.getIdentificationTypes(usuario.getIdlenguaje());

			ComboItem comboItem = new ComboItem();
			comboItem.setLabel("");
			comboItem.setValue("");

			comboItems.add(0, comboItem);
		}
		comboDTO.setCombooItems(comboItems);
		
		return comboDTO;
	}
	

}
