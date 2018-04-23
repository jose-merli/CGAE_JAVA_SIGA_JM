package org.itcgae.siga.adm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.ParametroDTO;
import org.itcgae.siga.DTOs.adm.ParametroDeleteDTO;
import org.itcgae.siga.DTOs.adm.ParametroItem;
import org.itcgae.siga.DTOs.adm.ParametroRequestDTO;
import org.itcgae.siga.DTOs.adm.ParametroUpdateDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.adm.service.IParametrosService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.mappers.AdmUsuariosMapper;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.security.UserAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParametrosServiceImpl implements IParametrosService{

	@Autowired
	private GenParametrosMapper genParametrosMapper;
	
	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;
	
	@Autowired
	private AdmUsuariosMapper admUsuariosMapper;
	
	@Override
	public ComboDTO getParameterModules() {
		ComboDTO combo = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		comboItems = genParametrosExtendsMapper.getModules();
		
		if(!comboItems.equals(null) && comboItems.size() > 0) {
			// añade elemento vacio al princpio para el dropdown de parte front
			ComboItem comboItem = new ComboItem();
			comboItem.setLabel("");
			comboItem.setValue("");
			comboItems.add(0, comboItem); 
			combo.setCombooItems(comboItems);
		}

		return combo;
	}

	@Override
	public ParametroDTO getParametersSearch(int numPagina, ParametroRequestDTO parametroRequestDTO, HttpServletRequest request) {	
		
		
		List<GenParametros> genparametros = new ArrayList<GenParametros>();
		ParametroDTO parametroDTO = new ParametroDTO();
		List<ParametroItem> parametroItems = new ArrayList<ParametroItem>();
		
		// Obtener idInstitucion del certificado
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		parametroRequestDTO.setIdInstitucion(nifInstitucion.substring(nifInstitucion.length()-4,nifInstitucion.length()));
		
		if (!parametroRequestDTO.getParametrosGenerales().equalsIgnoreCase("") && !parametroRequestDTO.getModulo().equalsIgnoreCase("") && parametroRequestDTO.getIdInstitucion() != null) {
			if (parametroRequestDTO.getParametrosGenerales().equals("N")) {
				// Buscar en gen_parametros por modulo e institucion
				GenParametrosExample genParametrosExample = new GenParametrosExample();
				genParametrosExample.createCriteria().andIdinstitucionEqualTo(Short.valueOf(parametroRequestDTO.getIdInstitucion())).andModuloEqualTo(parametroRequestDTO.getModulo()).andFechaBajaIsNull();
				genparametros = genParametrosMapper.selectByExample(genParametrosExample);

				if (genparametros != null && genparametros.size() > 0) {
					for (int i = 0; i < genparametros.size(); i++) {
						ParametroItem parametroItem = new ParametroItem();
						parametroItem.setIdInstitucion(String.valueOf(genparametros.get(i).getIdinstitucion()));
						parametroItem.setModulo(genparametros.get(i).getModulo());
						parametroItem.setParametro(genparametros.get(i).getParametro());
						parametroItem.setValor(genparametros.get(i).getValor());
						parametroItem.setIdRecurso(genparametros.get(i).getIdrecurso());
						parametroItems.add(parametroItem);
					}
					parametroDTO.setParametrosItems(parametroItems);
				}
			} else if (parametroRequestDTO.getParametrosGenerales().equals("S")) {

				parametroItems = genParametrosExtendsMapper.getParametersSearch(numPagina, parametroRequestDTO);

				if (parametroItems != null && parametroItems.size() > 0)
					parametroDTO.setParametrosItems(parametroItems);

			}
		}
		
		return parametroDTO;
		
	}
	
	
	@Override
	public ParametroDTO getParametersRecord(int numPagina, ParametroRequestDTO parametroRequestDTO, HttpServletRequest request) {
		List<GenParametros> genparametros = new ArrayList<GenParametros>();
		ParametroDTO parametroDTO = new ParametroDTO();
		List<ParametroItem> parametroItems = new ArrayList<ParametroItem>();
		
		// Obtener idInstitucion del certificado
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		parametroRequestDTO.setIdInstitucion(nifInstitucion.substring(nifInstitucion.length() - 4, nifInstitucion.length()));
		
		
		if (!parametroRequestDTO.getParametrosGenerales().equalsIgnoreCase("") && !parametroRequestDTO.getModulo().equalsIgnoreCase("") && parametroRequestDTO.getIdInstitucion() != null) {
			//if (parametroRequestDTO.getParametrosGenerales().equals("N")) {
				// Buscar en gen_parametros por modulo e institucion
				GenParametrosExample genParametrosExample = new GenParametrosExample();
				genParametrosExample.createCriteria().andIdinstitucionEqualTo(Short.valueOf(parametroRequestDTO.getIdInstitucion())).andModuloEqualTo(parametroRequestDTO.getModulo());

				genparametros = genParametrosMapper.selectByExample(genParametrosExample);

				if (genparametros != null && genparametros.size() > 0) {
					for (int i = 0; i < genparametros.size(); i++) {
						ParametroItem parametroItem = new ParametroItem();
						parametroItem.setIdInstitucion(String.valueOf(genparametros.get(i).getIdinstitucion()));
						parametroItem.setModulo(genparametros.get(i).getModulo());
						parametroItem.setParametro(genparametros.get(i).getParametro());
						parametroItem.setValor(genparametros.get(i).getValor());
						parametroItem.setFechaBaja(genparametros.get(i).getFechaBaja());
						parametroItems.add(parametroItem);
					}
					parametroDTO.setParametrosItems(parametroItems);
				}
//			} else if (parametroRequestDTO.getParametrosGenerales().equals("S")) {
//
//				parametroItems = genParametrosExtendsMapper.getParametersRecord(numPagina, parametroRequestDTO);
//
//				if (parametroItems != null && parametroItems.size() > 0)
//					parametroDTO.setParametrosItems(parametroItems);
//
//			}
		}
		return parametroDTO;
	}
	

	@Override
	public UpdateResponseDTO updateParameter(ParametroUpdateDTO parametroUpdateDTO, HttpServletRequest request) {
		int response = 0;
		GenParametros genParametros = new GenParametros();
		GenParametros genParametrosSelect = new GenParametros();
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		GenParametrosKey genParametrosKey = new GenParametrosKey();
		
		// Obtener idInstitucion del certificado y idUsuario del certificado
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		String dni = nifInstitucion.substring(0,9);
		Short idInstitucion = Short.valueOf(nifInstitucion.substring(nifInstitucion.length()-4,nifInstitucion.length()));
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
		List<AdmUsuarios> usuarios = admUsuariosMapper.selectByExample(exampleUsuarios);
		AdmUsuarios usuario = usuarios.get(0);
		
		if(!parametroUpdateDTO.getIdInstitucion().equalsIgnoreCase("") && !parametroUpdateDTO.getModulo().equalsIgnoreCase("") &&
			!parametroUpdateDTO.getParametro().equalsIgnoreCase("") && !parametroUpdateDTO.getValor().equalsIgnoreCase("")){
			
			// crear registro igual al existente en tabla gen_parametros, pero con el valor recibido y idInstitucion del usuario logeado
			if(parametroUpdateDTO.getIdInstitucion().equals("0")){
				
				// seteo de parametros a GenParametros. idInstitucion de certificado
				genParametros.setIdinstitucion(idInstitucion);
				genParametros.setModulo(parametroUpdateDTO.getModulo());
				genParametros.setParametro(parametroUpdateDTO.getParametro());
				genParametros.setValor(parametroUpdateDTO.getValor());
				genParametros.setFechamodificacion(new Date());
				genParametros.setUsumodificacion(Integer.valueOf(usuario.getIdusuario()));
				
				if(!genParametros.getIdinstitucion().equals(null)){
						
					genParametrosKey.setIdinstitucion(genParametros.getIdinstitucion());
					genParametrosKey.setModulo(genParametros.getModulo());
					genParametrosKey.setParametro(genParametros.getParametro());
					
					// comprobamos si realmente existe el registro {idinstitucion,modulo, parametro}, ya que fecha de baja puede ser distinta de null y no se muestra al buscar en pantalla
					genParametrosSelect = genParametrosMapper.selectByPrimaryKey(genParametrosKey);
					
					// si no es nulo, entonces hay que actualizar en vez de crear
					if(genParametrosSelect != null){
						// actualizamos fecha de baja a null, para que vuelva a estar disponible
						genParametros.setFechaBaja(null);
						response = genParametrosMapper.updateByPrimaryKeySelective(genParametros);
					}
					// si es nulo, se crea un nuevo registro
					else {
						genParametros.setIdrecurso(parametroUpdateDTO.getIdRecurso());
						response = genParametrosMapper.insertSelective(genParametros);
					}
					
				}
				
			}
			// actualiza registro en tabla gen_parametros con idInstitucion recibido y valor recibido 
			else {
				
				// seteo de parametros a GenParametros. idInstitucion es el recibido
				genParametros.setIdinstitucion(Short.valueOf(parametroUpdateDTO.getIdInstitucion()));
				genParametros.setModulo(parametroUpdateDTO.getModulo());
				genParametros.setParametro(parametroUpdateDTO.getParametro());
				genParametros.setValor(parametroUpdateDTO.getValor());
				genParametros.setFechamodificacion(new Date());
				genParametros.setUsumodificacion(Integer.valueOf(usuario.getIdusuario()));
				
				response = genParametrosMapper.updateByPrimaryKeySelective(genParametros);
			}
				
		}
		
		
		if(response == 1)
			updateResponseDTO.setStatus("OK");
		else
			updateResponseDTO.setStatus("ERROR");
		
		return updateResponseDTO;
	}

	@Override
	public UpdateResponseDTO deleteParameter(ParametroDeleteDTO parametroDeleteDTO, HttpServletRequest request) {
		int response = 0;
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		
		// Obtener idInstitucion del certificado y idUsuario del certificado
		String nifInstitucion = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		String dni = nifInstitucion.substring(0, 9);
		Short idInstitucion = Short
				.valueOf(nifInstitucion.substring(nifInstitucion.length() - 4, nifInstitucion.length()));
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);
		List<AdmUsuarios> usuarios = admUsuariosMapper.selectByExample(exampleUsuarios);
		AdmUsuarios usuario = usuarios.get(0);
		
		
		if(!parametroDeleteDTO.getIdInstitucion().equalsIgnoreCase("") && !parametroDeleteDTO.getModulo().equalsIgnoreCase("") &&
				!parametroDeleteDTO.getParametro().equalsIgnoreCase("")) {
			
			GenParametros genParametros = new GenParametros();
			genParametros.setModulo(parametroDeleteDTO.getModulo());
			genParametros.setParametro(parametroDeleteDTO.getParametro());
			genParametros.setIdinstitucion(Short.valueOf(parametroDeleteDTO.getIdInstitucion()));
			genParametros.setFechaBaja(new Date());
			genParametros.setUsumodificacion(Integer.valueOf(usuario.getIdusuario()));
			
			response  = genParametrosMapper.updateByPrimaryKeySelective(genParametros);
		}
		
		if(response == 1)
			updateResponseDTO.setStatus("OK");
		else
			updateResponseDTO.setStatus("ERROR");
		
		return updateResponseDTO;
	}

	
	
	

	
}
