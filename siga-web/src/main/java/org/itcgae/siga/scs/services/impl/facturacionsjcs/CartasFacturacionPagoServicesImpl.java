package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.CartasFacturacionPagosDTO;
import org.itcgae.siga.DTOs.scs.CartasFacturacionPagosItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsFacturacionJGExtendsMapper;
import org.itcgae.siga.scs.services.facturacionsjcs.ICartasFacturacionPagoServices;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartasFacturacionPagoServicesImpl implements ICartasFacturacionPagoServices {

	private Logger LOGGER = Logger.getLogger(CartasFacturacionPagoServicesImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private FcsFacturacionJGExtendsMapper fcsFacturacionJGExtendsMapper;

	@Override
	public CartasFacturacionPagosDTO buscarCartasfacturacion(CartasFacturacionPagosItem cartasFacturacionPagosItem,
			HttpServletRequest request) {
		
	    LOGGER.info("buscarCartasfacturacion() -> Entrada del servicio para obtener las cartas de facturacion");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		CartasFacturacionPagosDTO cartasFacturacionPagosDTO = new CartasFacturacionPagosDTO();
		
		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
	        exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
	      
	        LOGGER.info("buscarCartasfacturacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	        
	        List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	        
	        LOGGER.info("buscarCartasfacturacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
	            
	        if(null != usuarios && usuarios.size() > 0) {
	        	AdmUsuarios usuario = usuarios.get(0);
	            usuario.setIdinstitucion(idInstitucion);
	                
	            LOGGER.info("buscarCartasfacturacion() / fcsFacturacionJGExtendsMapper.buscarCartasfacturacion() -> Entrada a fcsFacturacionJGExtendsMapper para obtener las cartas de facturaciones");
	            
	            List<CartasFacturacionPagosItem> cartasFacturacionPagosItems = fcsFacturacionJGExtendsMapper.buscarCartasfacturacion(cartasFacturacionPagosItem, idInstitucion);
	            cartasFacturacionPagosDTO.setCartasFacturacionPagosItems(cartasFacturacionPagosItems);
	            
	            LOGGER.info("buscarCartasfacturacion() / fcsFacturacionJGExtendsMapper.buscarCartasfacturacion() -> Salida a fcsFacturacionJGExtendsMapper para obtener las cartas de facturaciones");
	      
	        }else {
	        	LOGGER.warn("buscarCartasfacturacion() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
	        }
	    }else {
	    	LOGGER.warn("buscarCartasfacturacion() -> idInstitucion del token nula");
	    }
	        
	    LOGGER.info("buscarCartasfacturacion() -> Salida del servicio para obtener las cartas de facturacion");
	    
	    return cartasFacturacionPagosDTO;	
	}

	@Override
	public CartasFacturacionPagosDTO buscarCartaspago(CartasFacturacionPagosItem cartasFacturacionPagosItem,
			HttpServletRequest request) {
		
	    LOGGER.info("buscarCartaspago() -> Entrada del servicio para obtener las cartas de pago");

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		CartasFacturacionPagosDTO cartasFacturacionPagosDTO = new CartasFacturacionPagosDTO();
		
		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
	        exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
	      
	        LOGGER.info("buscarCartaspago() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	        
	        List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	        
	        LOGGER.info("buscarCartaspago() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
	            
	        if(null != usuarios && usuarios.size() > 0) {
	        	AdmUsuarios usuario = usuarios.get(0);
	            usuario.setIdinstitucion(idInstitucion);
	                
	            LOGGER.info("buscarCartaspago() / fcsFacturacionJGExtendsMapper.buscarCartasfacturacion() -> Entrada a fcsFacturacionJGExtendsMapper para obtener las cartas de facturaciones");
	            
	            List<CartasFacturacionPagosItem> cartasFacturacionPagosItems = fcsFacturacionJGExtendsMapper.buscarCartaspago(cartasFacturacionPagosItem, idInstitucion, usuario.getIdlenguaje());
	            cartasFacturacionPagosDTO.setCartasFacturacionPagosItems(cartasFacturacionPagosItems);
	            
	            LOGGER.info("buscarCartaspago() / fcsFacturacionJGExtendsMapper.buscarCartasfacturacion() -> Salida a fcsFacturacionJGExtendsMapper para obtener las cartas de facturaciones");
	      
	        }else {
	        	LOGGER.warn("buscarCartaspago() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
	        }
	    }else {
	    	LOGGER.warn("buscarCartaspago() -> idInstitucion del token nula");
	    }
	        
	    LOGGER.info("buscarCartaspago() -> Salida del servicio para obtener las cartas de pago");
	    
	    return cartasFacturacionPagosDTO;	
	}
	
	
}