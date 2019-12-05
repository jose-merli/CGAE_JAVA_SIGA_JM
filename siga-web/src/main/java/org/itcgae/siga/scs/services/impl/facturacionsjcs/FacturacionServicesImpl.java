package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.FacturacionDTO;
import org.itcgae.siga.DTOs.scs.FacturacionDeleteDTO;
import org.itcgae.siga.DTOs.scs.FacturacionItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsFacturacionJGExtendsMapper;
import org.itcgae.siga.scs.services.facturacionsjcs.IFacturacionServices;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacturacionServicesImpl implements IFacturacionServices {

	private Logger LOGGER = Logger.getLogger(FacturacionServicesImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private FcsFacturacionJGExtendsMapper fcsFacturacionJGExtendsMapper;
	
	public FacturacionDTO buscarFacturaciones(FacturacionItem facturacionItem, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		FacturacionDTO facturaciones = new FacturacionDTO();
		
		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
	        exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
	        LOGGER.info("getLabel() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	        List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	        LOGGER.info("getLabel() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
	            
	        if(null != usuarios && usuarios.size() > 0) {
	        	AdmUsuarios usuario = usuarios.get(0);
	            usuario.setIdinstitucion(idInstitucion);
	                
	            LOGGER.info("buscarFacturaciones() / fcsFacturacionJGExtendsMapper.buscarFacturaciones() -> Entrada a fcsFacturacionJGExtendsMapper para obtener las facturaciones");
	            List<FacturacionItem> facturacionItems = fcsFacturacionJGExtendsMapper.buscarFacturaciones(facturacionItem, idInstitucion.toString());
	            facturaciones.setFacturacionItem(facturacionItems);
	            LOGGER.info("buscarFacturaciones() / fcsFacturacionJGExtendsMapper.buscarFacturaciones() -> Salida a fcsFacturacionJGExtendsMapper para obtener las facturaciones");
	        }else {
	        	LOGGER.warn("getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
	        }
	    }else {
	    	LOGGER.warn("getLabel() -> idInstitucion del token nula");
	    }
	        
	    LOGGER.info("getLabel() -> Salida del servicio para obtener las facturaciones");
	    
	    return facturaciones;	
	}
	
	public FacturacionDeleteDTO eliminarFacturaciones(int idFactura, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		FacturacionDeleteDTO facturacionesDelete = new FacturacionDeleteDTO();
		int response = 0;
		
		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
	        exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
	        LOGGER.info("getLabel() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	        List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	        LOGGER.info("getLabel() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
	            
	        if(null != usuarios && usuarios.size() > 0) {
	        	AdmUsuarios usuario = usuarios.get(0);
	            usuario.setIdinstitucion(idInstitucion);
	                
	            LOGGER.info("buscarFacturaciones() / fcsFacturacionJGExtendsMapper.buscarFacturaciones() -> Entrada a fcsFacturacionJGExtendsMapper para obtener las facturaciones");
	            //response = fcsFacturacionJGExtendsMapper.eliminarFacturaciones(idFactura, idInstitucion.toString()); 	            
	            
	            if (response > 0){
	            	facturacionesDelete.setStatus(SigaConstants.OK);
	            }else{
	            	facturacionesDelete.setStatus(SigaConstants.KO);
	            }
	           
	            LOGGER.info("buscarFacturaciones() / fcsFacturacionJGExtendsMapper.buscarFacturaciones() -> Salida a fcsFacturacionJGExtendsMapper para obtener las facturaciones");
	        }else {
	        	LOGGER.warn("getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
	        }
	    }else {
	    	LOGGER.warn("getLabel() -> idInstitucion del token nula");
	    }
	        
	    LOGGER.info("getLabel() -> Salida del servicio para obtener las facturaciones");
	    
	    return facturacionesDelete;	
	}
}
