package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsEstadosFacturacionExtendsMapper;
import org.itcgae.siga.scs.services.facturacionsjcs.ICombosServices;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CombosServicesImpl implements ICombosServices {

	private Logger LOGGER = Logger.getLogger(CombosServicesImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private FcsEstadosFacturacionExtendsMapper fcsEstadosFacturacionExtendsMapper;

	public ComboDTO comboFactEstados(HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String idLenguaje;
		ComboDTO comboEstadosFact = new ComboDTO();

		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
	        exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
	        LOGGER.info("getLabel() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	        List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	        LOGGER.info("getLabel() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
	            
	        if(null != usuarios && usuarios.size() > 0) {
	        	AdmUsuarios usuario = usuarios.get(0);
	            usuario.setIdinstitucion(idInstitucion);
	            idLenguaje = usuario.getIdlenguaje();
	                
	            LOGGER.info("comboFactEstados() / FcsEstadosFacturacionExtendsMapper.estadosFacturacion() -> Entrada a FcsEstadosFacturacionExtendsMapper para obtener los estados");
	            List<ComboItem> comboItems = fcsEstadosFacturacionExtendsMapper.estadosFacturacion(idLenguaje);
	            comboEstadosFact.setCombooItems(comboItems);
	            LOGGER.info("comboFactEstados() / FcsEstadosFacturacionExtendsMapper.estadosFacturacion() -> Salida a FcsEstadosFacturacionExtendsMapper para obtener los estados");
	        }else {
	        	LOGGER.warn("getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
	        }
	    }else {
	    	LOGGER.warn("getLabel() -> idInstitucion del token nula");
	    }
	        
	    LOGGER.info("getLabel() -> Salida del servicio para obtener los estados de las facturas");
	    return comboEstadosFact;
	}
}