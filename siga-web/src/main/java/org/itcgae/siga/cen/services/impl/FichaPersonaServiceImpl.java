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
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteKey;
import org.itcgae.siga.db.entities.CenNocolegiado;
import org.itcgae.siga.db.entities.CenNocolegiadoExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.mappers.CenClienteMapper;
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
	
	@Autowired
	private CenClienteMapper cenClienteMapper;

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
				if(response == 1) {
					CenClienteKey key = new CenClienteKey();
					key.setIdinstitucion(idInstitucion);
					key.setIdpersona(Long.valueOf(desasociarPersona.getIdPersona()));
					
					CenCliente cliente = cenClienteMapper.selectByPrimaryKey(key);
					
					cliente.setFechaactualizacion(new Date());
					
					cenClienteMapper.updateByPrimaryKey(cliente);
					updateResponse.setStatus(SigaConstants.OK);
				} else {
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
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		
		AdmUsuarios usuario = new AdmUsuarios();
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		
		
		
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
			if(null != asociarPersona.getIdPersonaAsociar() && colegiado.getIdpersonanotario().equals(Long.valueOf(asociarPersona.getIdPersonaAsociar()))) {
				updateResponse.getError().setDescription("La persona seleccionada ya es el notario de la sociedad.");
			}else {
				usuario = usuarios.get(0);
				//Validamos si existe el cliente para la institucion de la sociedad, en caso de que no exista, se inserta.
				CenClienteKey key = new CenClienteKey();
				key.setIdinstitucion(idInstitucion);
				key.setIdpersona(Long.valueOf(asociarPersona.getIdPersonaAsociar()));
				
				CenCliente cliente = cenClienteMapper.selectByPrimaryKey(key );
				int responseCenCliente = 0;
				if (!(null != cliente)) {
					CenCliente recordCliente = new CenCliente();
					recordCliente = rellenarInsertCenCliente(usuario,asociarPersona.getIdPersonaAsociar(),idInstitucion);
					responseCenCliente = cenClienteMapper.insertSelective(recordCliente);
				}

				// Insertamos en NoColegiado
				if(responseCenCliente == 1) {
					
					LOGGER.info(
							"savePerson() / cenNocolegiadoExtendsMapper.insertSelective() -> Entrada a cenNocolegiadoExtendsMapper para crear un nuevo no colegiado");

					CenNocolegiado cenNocolegiado = rellenarInsertCenNoColegiado(usuario, Long.valueOf(asociarPersona.getIdPersonaAsociar()), idInstitucion);
					cenNocolegiadoExtendsMapper
							.insertSelective(cenNocolegiado);
					
					LOGGER.info(
							"savePerson() / cenNocolegiadoExtendsMapper.insertSelective() -> Salida de cenNocolegiadoExtendsMapper para crear un nuevo no colegiado");
				}
				
				CenNocolegiadoExample cenNoColegiadoExample = new CenNocolegiadoExample();
				cenNoColegiadoExample.createCriteria().andIdpersonaEqualTo(Long.valueOf(asociarPersona.getIdPersona())).andIdinstitucionEqualTo(idInstitucion);
				colegiado.setIdpersonanotario(Long.valueOf(asociarPersona.getIdPersonaAsociar()));
				colegiado.setFechamodificacion(new Date());
				colegiado.setUsumodificacion(usuario.getIdusuario());
				cenNocolegiadoExtendsMapper.updateByExampleSelective(colegiado, cenNoColegiadoExample);
				updateResponse.setStatus("OK");
				
			}
			
		}else {
			usuario = usuarios.get(0);
			//Validamos si existe el cliente para la institucion de la sociedad, en caso de que no exista, se inserta.
			CenClienteKey key = new CenClienteKey();
			key.setIdinstitucion(idInstitucion);
			key.setIdpersona(Long.valueOf(asociarPersona.getIdPersonaAsociar()));
			
			CenCliente cliente = cenClienteMapper.selectByPrimaryKey(key );
			if (!(null != cliente)) {
				CenCliente recordCliente = new CenCliente();
				recordCliente = rellenarInsertCenCliente(usuario,asociarPersona.getIdPersonaAsociar(),idInstitucion);
				int responseCenCliente = cenClienteMapper.insertSelective(recordCliente);
				if(responseCenCliente>0) {
					LOGGER.debug("Se ha insertado en CenCliente correctamente");
				}
			}
			
			CenNocolegiadoExample cenNoColegiadoExample = new CenNocolegiadoExample();
			cenNoColegiadoExample.createCriteria().andIdpersonaEqualTo(Long.valueOf(asociarPersona.getIdPersona())).andIdinstitucionEqualTo(idInstitucion);
			colegiado.setIdpersonanotario(Long.valueOf(asociarPersona.getIdPersonaAsociar()));
			colegiado.setFechamodificacion(new Date());
			colegiado.setUsumodificacion(usuario.getIdusuario());
			cenNocolegiadoExtendsMapper.updateByExampleSelective(colegiado, cenNoColegiadoExample);
			updateResponse.setStatus("OK");
			
		}
		
		CenClienteKey key = new CenClienteKey();
		key.setIdinstitucion(idInstitucion);
		key.setIdpersona(Long.valueOf(asociarPersona.getIdPersona()));
		CenCliente cliente = cenClienteMapper.selectByPrimaryKey(key);
		cliente.setFechaactualizacion(new Date());
		cenClienteMapper.updateByPrimaryKey(cliente);
		
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
			
			
			comboItems = cenPersonaExtendsMapper.selectMaxIdPersona(usuario.getIdinstitucion().toString());
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
		//
			cenPersonaExtendsMapper.insert(record);
			
			// obtenemos su idpersona
			comboItems = cenPersonaExtendsMapper.selectMaxIdPersona(usuario.getIdinstitucion().toString());

			//comboItems = cenPersonaExtendsMapper.selectMaxIdPersona();
			String maxIdPersona = comboItems.get(0).getValue();
			

			CenCliente recordCliente = new CenCliente();
			recordCliente = rellenarInsertCenCliente(usuario,maxIdPersona,idInstitucion);
			int responseCenCliente = cenClienteMapper.insertSelective(recordCliente);
			
			// Insertamos en NoColegiado
			if(responseCenCliente == 1) {
				LOGGER.info(
						"createPersonFile() / cenNocolegiadoExtendsMapper.insertSelective() -> Entrada a cenNocolegiadoExtendsMapper para crear un nuevo no colegiado");

				
				CenNocolegiado cenNocolegiado = rellenarInsertCenNoColegiado(usuario, nuevoIdPersona, idInstitucion);
				cenNocolegiadoExtendsMapper
						.insertSelective(cenNocolegiado);
				
				LOGGER.info(
						"createPersonFile() / cenNocolegiadoExtendsMapper.insertSelective() -> Salida de cenNocolegiadoExtendsMapper para crear un nuevo no colegiado");
			}
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

//			ComboItem comboItem = new ComboItem();
//			comboItem.setLabel("");
//			comboItem.setValue("");
//
//			comboItems.add(0, comboItem);
		}
		comboDTO.setCombooItems(comboItems);
		
		return comboDTO;
	}
	
	protected CenCliente rellenarInsertCenCliente(AdmUsuarios usuario, String maxIdPersona, Short idInstitucion) {
		CenCliente record = new CenCliente();
		
		record.setIdpersona(Long.valueOf(maxIdPersona));
		record.setIdinstitucion(Short.valueOf(idInstitucion));
		record.setFechaalta(new Date());
		record.setCaracter("P");
		record.setPublicidad(SigaConstants.DB_FALSE);
		record.setGuiajudicial(SigaConstants.DB_FALSE);
		record.setComisiones(SigaConstants.DB_FALSE);
		record.setIdtratamiento(Short.valueOf(SigaConstants.DB_TRUE)); // 1
		record.setFechamodificacion(new Date());
		record.setUsumodificacion(usuario.getIdusuario());
		record.setIdlenguaje(usuario.getIdlenguaje());
		record.setExportarfoto(SigaConstants.DB_FALSE);
		
		return record;
	}
	
	protected CenNocolegiado rellenarInsertCenNoColegiado(AdmUsuarios usuario, Long idPersona, Short idInstitucion) {
		CenNocolegiado cenNocolegiado = new CenNocolegiado();
		cenNocolegiado.setFechamodificacion(new Date());
		cenNocolegiado.setIdpersona(idPersona);
		cenNocolegiado.setUsumodificacion(usuario.getIdusuario());
		cenNocolegiado.setIdinstitucion(idInstitucion);
		cenNocolegiado.setTipo("1");
		cenNocolegiado.setSociedadsj("0");
		return cenNocolegiado;
	}
	
	@Override
	public FichaPersonaItem searchPersona(Long idPersona, HttpServletRequest request) {
		
		FichaPersonaItem fichaPersona = new FichaPersonaItem();
		
		LOGGER.info(
				"searchPersona() -> Entrada al servicio para la recuperar la ficha de persona");

		CenPersona fichaCenPersonaItem = cenPersonaExtendsMapper.selectByPrimaryKey(idPersona);
		if(fichaCenPersonaItem != null){
			fichaPersona.setNif(fichaCenPersonaItem.getNifcif());
			fichaPersona.setNombre(fichaCenPersonaItem.getNombre());
			fichaPersona.setApellido1(fichaCenPersonaItem.getApellidos1());
			fichaPersona.setApellido2(fichaCenPersonaItem.getApellidos2());
			fichaPersona.setTipoIdentificacion(String.valueOf(fichaCenPersonaItem.getIdtipoidentificacion()));
		}
		return fichaPersona;
	}
}
