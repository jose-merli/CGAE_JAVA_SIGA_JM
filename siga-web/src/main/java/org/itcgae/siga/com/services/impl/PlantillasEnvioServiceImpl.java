package org.itcgae.siga.com.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.PlantillaEnvioSearchItem;
import org.itcgae.siga.DTOs.com.PlantillasEnvioDTO;
import org.itcgae.siga.DTOs.com.RemitenteDTO;
import org.itcgae.siga.DTOs.com.TarjetaConfiguracionDto;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.cen.services.impl.TarjetaDatosDireccionesServiceImpl;
import org.itcgae.siga.com.services.IPlantillasEnvioService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlantillasEnvioServiceImpl implements IPlantillasEnvioService{

	private Logger LOGGER = Logger.getLogger(PlantillasEnvioServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Override
	public ComboDTO getComboTipoEnvio(HttpServletRequest request) {
		
		LOGGER.info("getComboTipoEnvio() -> Entrada al servicio para obtener los tipos de envio");
		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("getComboTipoEnvio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("getComboTipoEnvio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if (null != usuarios && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				//comboItems = cenTipoDireccionExtendsMapper.selectTipoDireccion(usuario.getIdlenguaje());
				
				if(null != comboItems && comboItems.size() > 0) {
					ComboItem element = new ComboItem();
					element.setLabel("");
					element.setValue("");
					comboItems.add(0, element);
				}		
				
				comboDTO.setCombooItems(comboItems);
				
			}
			
		}
		LOGGER.info("getComboTipoEnvio() -> Salida del servicio para obtener los tipos de envio");
		
		return comboDTO;
	}

	@Override
	public ComboDTO getComboConsultas(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlantillasEnvioDTO PlantillasEnvioSearch(HttpServletRequest request, PlantillaEnvioSearchItem filtros) {
		
		LOGGER.info("getComboTipoEnvio() -> Entrada al servicio para obtener los tipos de envio");
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info("getComboTipoEnvio() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info("getComboTipoEnvio() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
			
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);

			}
			
		}
		LOGGER.info("getComboTipoEnvio() -> Salida del servicio para obtener los tipos de envio");
		return null;
	}

	@Override
	public Error borrarPlantillasEnvio(HttpServletRequest request, PlantillasEnvioDTO plantillasEnvio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Error guardarDatosGenerales(HttpServletRequest request, TarjetaConfiguracionDto datosTarjeta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConsultasDTO detalleConsultas(HttpServletRequest request, ConsultasDTO consultas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Error asociarConsulta(HttpServletRequest request, ConsultaItem consulta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Error borrarConsulta(HttpServletRequest request, ConsultaItem consulta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RemitenteDTO detalleRemitente(HttpServletRequest request, String idConsulta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Error guardarREmitente(HttpServletRequest request, RemitenteDTO remitente) {
		// TODO Auto-generated method stub
		return null;
	}

}
