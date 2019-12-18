package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.FacturacionDTO;
import org.itcgae.siga.DTOs.scs.FacturacionDeleteDTO;
import org.itcgae.siga.DTOs.scs.FacturacionItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.FcsFactEstadosfacturacion;
import org.itcgae.siga.db.entities.FcsFacturacionjg;
import org.itcgae.siga.db.mappers.FcsFactEstadosfacturacionMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsFacturacionJGExtendsMapper;
import org.itcgae.siga.scs.services.facturacionsjcs.IFacturacionServices;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FacturacionServicesImpl implements IFacturacionServices {

	private Logger LOGGER = Logger.getLogger(FacturacionServicesImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private FcsFacturacionJGExtendsMapper fcsFacturacionJGExtendsMapper;
	
	@Autowired
	private FcsFactEstadosfacturacionMapper fcsFactEstadosfacturacionMapper;
	
	@Override
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
	
	@Override
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
	                
	            //LOGGER.info("buscarFacturaciones() / fcsFacturacionJGExtendsMapper.buscarFacturaciones() -> Entrada a fcsFacturacionJGExtendsMapper para obtener las facturaciones");
	            //response = fcsFacturacionJGExtendsMapper.eliminarFacturaciones(idFactura, idInstitucion.toString()); 	            
	            
//	            if (response > 0){
//	            	facturacionesDelete.setStatus(SigaConstants.OK);
//	            }else{
//	            	facturacionesDelete.setStatus(SigaConstants.KO);
//	            }
	           
	            //LOGGER.info("eliminarFacturaciones() / fcsFacturacionJGExtendsMapper.buscarFacturaciones() -> Salida a fcsFacturacionJGExtendsMapper para obtener las facturaciones");
	        }else {
	        	LOGGER.warn("getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
	        }
	    }else {
	    	LOGGER.warn("getLabel() -> idInstitucion del token nula");
	    }
	        
	    LOGGER.info("getLabel() -> Salida del servicio para eliminar las facturaciones");
	    
	    return facturacionesDelete;	
	}
	
	@Override
	public FacturacionDTO datosFacturacion(String idFacturacion, HttpServletRequest request) {
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
	                
	            LOGGER.info("datosFacturacion() / fcsFacturacionJGExtendsMapper.datosFacturacion() -> Entrada a fcsFacturacionJGExtendsMapper para obtener los datos de la facturacón");
	            List<FacturacionItem> facturacionItem = fcsFacturacionJGExtendsMapper.datosFacturacion(idFacturacion, idInstitucion.toString());
	            facturaciones.setFacturacionItem(facturacionItem);
	            LOGGER.info("datosFacturacion() / fcsFacturacionJGExtendsMapper.datosFacturacion() -> Salida a fcsFacturacionJGExtendsMapper para obtener los datos de la facturación");
	        }else {
	        	LOGGER.warn("getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
	        }
	    }else {
	    	LOGGER.warn("getLabel() -> idInstitucion del token nula");
	    }
	        
	    LOGGER.info("getLabel() -> Salida del servicio para obtener los datos de las facturaciones");
	    
	    return facturaciones;	
	}
	
	@Override
	public FacturacionDTO historicoFacturacion(String idFacturacion, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		FacturacionDTO facturaciones = new FacturacionDTO();
		String idLenguaje="";
		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
	        exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
	        LOGGER.info("getLabel() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	        List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	        LOGGER.info("getLabel() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
	            
	        if(null != usuarios && usuarios.size() > 0) {
	        	AdmUsuarios usuario = usuarios.get(0);
	            usuario.setIdinstitucion(idInstitucion);
	            idLenguaje=usuario.getIdlenguaje();    
	            LOGGER.info("historicoFacturacion() / fcsFacturacionJGExtendsMapper.historicoFacturacion() -> Entrada a fcsFacturacionJGExtendsMapper para obtener los historicos de estados de la facturacón");
	            List<FacturacionItem> facturacionItem = fcsFacturacionJGExtendsMapper.historicoFacturacion(idFacturacion, idLenguaje, idInstitucion.toString());
	            facturaciones.setFacturacionItem(facturacionItem);
	            LOGGER.info("historicoFacturacion() / fcsFacturacionJGExtendsMapper.historicoFacturacion() -> Salida a fcsFacturacionJGExtendsMapper para obtener los historicos de estados de la facturación");
	        }else {
	        	LOGGER.warn("getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
	        }
	    }else {
	    	LOGGER.warn("getLabel() -> idInstitucion del token nula");
	    }
	        
	    LOGGER.info("getLabel() -> Salida del servicio para obtener el historico de estados de las facturaciones");
	    
	    return facturaciones;	
	}
	
	@Override
	@Transactional
	public InsertResponseDTO saveFacturacion(FacturacionItem facturacionItem, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponse = new InsertResponseDTO();
		org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
		insertResponse.setError(error);
		int response = 0;
		int idFacturacion = 0;
		
		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
	        exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
	        LOGGER.info("getLabel() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	        List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	        LOGGER.info("getLabel() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
	            
	        if(null != usuarios && usuarios.size() > 0) {
	        	AdmUsuarios usuario = usuarios.get(0);
	            usuario.setIdinstitucion(idInstitucion);
	            FcsFacturacionjg record = new FcsFacturacionjg();
	            FcsFactEstadosfacturacion record2 = new FcsFactEstadosfacturacion();
	           
	            LOGGER.info("saveFacturacion() / fcsFacturacionJGExtendsMapper.saveFacturacion() -> Entrada a fcsFacturacionJGExtendsMapper para obtener los datos de la facturacón");
	            
	            //GUARDAR DATOAS DE LA FACTURACION
	            try {
	            	//OBTENEMOS EL ID DE LA FACTURA
	            	LOGGER.info("saveFacturacion() / fcsFacturacionJGExtendsMapper.saveFacturacion() -> Guardar datos en fcsFacturacionjg");
	            	NewIdDTO idP = fcsFacturacionJGExtendsMapper.getIdFacturacion(idInstitucion);	
	            	idFacturacion = Integer.parseInt(idP.getNewId()) + 1;
	            	int idPartida = Integer.parseInt(facturacionItem.getIdPartidaPresupuestaria());
	            	Short idEstado = 10;
	            	
	            	//SETEAMOS LOS DATOS Y GUARDAMOS LA FACTURA
	            	record.setIdfacturacion(idFacturacion);
		            record.setIdinstitucion(idInstitucion);
		            record.setFechadesde(facturacionItem.getFechaDesde());
		            record.setFechahasta(facturacionItem.getFechaHasta());
		            record.setNombre(facturacionItem.getNombre());
		            record.setRegularizacion(facturacionItem.getRegularizacion());
		            record.setPrevision(facturacionItem.getPrevision());
		            record.setVisible(facturacionItem.getVisible());
		            record.setFechamodificacion(new Date());
		            record.setUsumodificacion(usuario.getIdusuario());
		            record.setIdpartidapresupuestaria(idPartida);
		           		            
		            response = fcsFacturacionJGExtendsMapper.insert(record);
		            
		            LOGGER.info("saveFacturacion() / fcsFacturacionJGExtendsMapper.saveFacturacion() -> Salida guardar datos en fcsFacturacionjg");
		            
		            LOGGER.info("saveFacturacion() / fcsFacturacionJGExtendsMapper.saveFacturacion() -> Entrada para guardar los estados de la facturacion en fcsFactEstadosFacturacion");
		            
		            //GUARDAMOS EL ESTADO DE LA FACTURA
		            Short idOrdenEstado = 1;
		            
		            record2.setIdfacturacion(record.getIdfacturacion());
		            record2.setIdinstitucion(idInstitucion);
		            record2.setIdestadofacturacion(idEstado);
		            record2.setFechaestado(new Date());
		            record2.setFechamodificacion(new Date());
		            record2.setUsumodificacion(usuario.getIdusuario());
		            record2.setIdordenestado(idOrdenEstado);
		            
		            response = fcsFactEstadosfacturacionMapper.insert(record2);
		            
		            LOGGER.info("saveFacturacion() / fcsFacturacionJGExtendsMapper.saveFacturacion() -> Salida guardar los estados de la facturacion en fcsFactEstadosFacturacion");
	            }catch(Exception e){
	            	LOGGER.error("ERROR: FacturacionServicesImpl.saveFacturacion() > al guardar los datos de la facturacion.", e);
	            	error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					insertResponse.setStatus(SigaConstants.KO);
	            }            
	            
	            LOGGER.info("saveFacturacion() / fcsFacturacionJGExtendsMapper.datosFacturacion() -> Salida a fcsFacturacionJGExtendsMapper para obtener los datos de la facturación");
	        }else {
	        	LOGGER.warn("getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
	        }
	    }else {
	    	LOGGER.warn("getLabel() -> idInstitucion del token nula");
	    }
	        
	    LOGGER.info("getLabel() -> Salida del servicio para guardar las facturaciones");
	    
	    if (response == 0 && error.getDescription() == null) {
			error.setCode(400);
			insertResponse.setStatus(SigaConstants.KO);
		} else if (error.getCode() == null) {
			error.setCode(200);
			insertResponse.setId(Integer.toString(idFacturacion));
			insertResponse.setStatus(SigaConstants.OK);
		}

	    insertResponse.setError(error);
		
		return insertResponse;
	}
	
	@Override
	@Transactional
	public UpdateResponseDTO updateFacturacion(FacturacionItem facturacionItem, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		UpdateResponseDTO updateResponse = new UpdateResponseDTO();
		org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
		updateResponse.setError(error);
		
		int response = 0;
		int idFacturacion = 0;
		
		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
	        exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
	        
	        LOGGER.info("getLabel() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	        List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	        LOGGER.info("getLabel() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
	            
	        if(null != usuarios && usuarios.size() > 0) {
	        	AdmUsuarios usuario = usuarios.get(0);
	            usuario.setIdinstitucion(idInstitucion);
	            FcsFacturacionjg record = new FcsFacturacionjg();
	            
	            try {
	               	LOGGER.info("updateFacturacion() -> Actualizar datos en fcsFacturacionjg");
	               	idFacturacion = Integer.parseInt(facturacionItem.getIdFacturacion());
	               	int idPartida = Integer.parseInt(facturacionItem.getIdPartidaPresupuestaria());
	               	
	            	//SETEAMOS LOS DATOS Y GUARDAMOS LA FACTURA
	            	record.setIdfacturacion(idFacturacion);
		            record.setIdinstitucion(idInstitucion);
		            record.setFechadesde(facturacionItem.getFechaDesde());
		            record.setFechahasta(facturacionItem.getFechaHasta());
		            record.setNombre(facturacionItem.getNombre());
		            record.setRegularizacion(facturacionItem.getRegularizacion());
		            record.setVisible(facturacionItem.getVisible());
		            record.setFechamodificacion(new Date());
		            record.setUsumodificacion(usuario.getIdusuario());
		            record.setIdpartidapresupuestaria(idPartida);
		           		            
		            response = fcsFacturacionJGExtendsMapper.updateByPrimaryKeySelective(record);
		            
		            LOGGER.info("updateFacturacion() -> Salida actualizar datos en fcsFacturacionjg");
		            
	            }catch(Exception e){
	            	LOGGER.error("ERROR: FacturacionServicesImpl.updateFacturacion() > al actualizar los datos de la facturacion.", e);
	            	error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					updateResponse.setStatus(SigaConstants.KO);
	            }            
	        }else {
	        	LOGGER.warn("getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
	        }
	    }else {
	    	LOGGER.warn("getLabel() -> idInstitucion del token nula");
	    }
	        
		LOGGER.info("getLabel() -> Salida del servicio para actualizar las facturaciones");
	    
	    if (response == 0 && error.getDescription() == null) {
			error.setCode(400);
			updateResponse.setStatus(SigaConstants.KO);
		} else if (error.getCode() == null) {
			error.setCode(200);
			updateResponse.setStatus(SigaConstants.OK);
		}

	    updateResponse.setError(error);
	    
	    return updateResponse;	
	}
	
	@Override
	@Transactional
	public InsertResponseDTO ejecutarFacturacion(String idFacturacion, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponse = new InsertResponseDTO();
		org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
		insertResponse.setError(error);
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
	            FcsFactEstadosfacturacion record = new FcsFactEstadosfacturacion();
	            FcsFacturacionjg record2 = new FcsFacturacionjg();
	           
	            LOGGER.info("ejecutarFacturacion() -> Entrada para poner la facturacion como programada");
	            
	            //GUARDAR DATOAS DE LA FACTURACION
	            try {
	            	//ACTUALIZAMOS LA PREVISION DE LA TABLA FCS_FACTURACIONJG
	            	LOGGER.info("ejecutarFacturacion() -> Actualizar la prevision");
	            	
		            record2.setPrevision("0");
		            record2.setIdfacturacion(Integer.parseInt(idFacturacion));
		            record2.setIdinstitucion(idInstitucion);
		           		            
		            response = fcsFacturacionJGExtendsMapper.updateByPrimaryKeySelective(record2);
		            
		            LOGGER.info("ejecutarFacturacion() -> Salida actualizar la prevision");
		            
	            	//SETEAMOS LOS DATOS Y HACEMOS INSERT DEL ESTADO
	            	LOGGER.info("ejecutarFacturacion() -> Guardar datos en fcsFactEstadosfacturacion");
	            	NewIdDTO idP = fcsFacturacionJGExtendsMapper.getIdOrdenEstado(idInstitucion, idFacturacion);	
	            	Short idOrdenEstado = (short) (Integer.parseInt(idP.getNewId())+1);
	            	Short idEstado = 50;
	            	
		            record.setIdinstitucion(idInstitucion);
		            record.setIdfacturacion(Integer.parseInt(idFacturacion));
		            record.setIdestadofacturacion(idEstado);
		            record.setFechaestado(new Date());
		            record.setFechamodificacion(new Date());
		            record.setUsumodificacion(usuario.getIdusuario());
		            record.setIdordenestado(idOrdenEstado);
		           		            
		            response = fcsFactEstadosfacturacionMapper.insert(record);
		            
		            LOGGER.info("ejecutarFacturacion() -> Salida guardar datos en fcsFactEstadosfacturacion");
	            }catch(Exception e){
	            	LOGGER.error("ERROR: FacturacionServicesImpl.ejecutarFacturacion() >  Al poner la facturacion como programada.", e);
	            	error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					insertResponse.setStatus(SigaConstants.KO);
	            }            
	            
	            LOGGER.info("ejecutarFacturacion() -> Salida poner la facturacion como programada");
	        }else {
	        	LOGGER.warn("getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
	        }
	    }else {
	    	LOGGER.warn("getLabel() -> idInstitucion del token nula");
	    }
	        
	    LOGGER.info("getLabel() -> Salida del servicio para poner la facturacion como programada");
	    
	    if (response == 0 && error.getDescription() == null) {
			error.setCode(400);
			insertResponse.setStatus(SigaConstants.KO);
		} else if (error.getCode() == null) {
			error.setCode(200);
			insertResponse.setStatus(SigaConstants.OK);
		}

	    insertResponse.setError(error);
		
		return insertResponse;
	}
	
	@Override
	public InsertResponseDTO reabrirFacturacion(String idFacturacion, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponse = new InsertResponseDTO();
		org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
		insertResponse.setError(error);
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
	            FcsFactEstadosfacturacion record = new FcsFactEstadosfacturacion();
	           
	            LOGGER.info("reabrirFacturacion() -> Entrada para reabrir la facturacion");
	            
	            //GUARDAR DATOAS DE LA FACTURACION
	            try {
	            	//SETEAMOS LOS DATOS Y HACEMOS INSERT DEL ESTADO
	            	LOGGER.info("reabrirFacturacion() -> Guardar datos para reabrir en fcsFactEstadosfacturacion");
	            	NewIdDTO idP = fcsFacturacionJGExtendsMapper.getIdOrdenEstado(idInstitucion, idFacturacion);	
	            	Short idOrdenEstado = (short) (Integer.parseInt(idP.getNewId())+1);
	            	Short idEstado = 10;
	            	
		            record.setIdinstitucion(idInstitucion);
		            record.setIdestadofacturacion(idEstado);
		            record.setIdfacturacion(Integer.parseInt(idFacturacion));
		            record.setFechaestado(new Date());
		            record.setFechamodificacion(new Date());
		            record.setUsumodificacion(usuario.getIdusuario());
		            record.setIdordenestado(idOrdenEstado);
		           		            
		            response = fcsFactEstadosfacturacionMapper.insert(record);
		            
		            LOGGER.info("reabrirFacturacion() -> Salida guardar datos para reabrir en fcsFactEstadosfacturacion");
	            }catch(Exception e){
	            	LOGGER.error("ERROR: FacturacionServicesImpl.reabrirFacturacion() >  Al reabrir la facturacion.", e);
	            	error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					insertResponse.setStatus(SigaConstants.KO);
	            }            
	            
	            LOGGER.info("reabrirFacturacion() -> Salida para reabrir la facturacion");
	        }else {
	        	LOGGER.warn("getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
	        }
	    }else {
	    	LOGGER.warn("getLabel() -> idInstitucion del token nula");
	    }
	        
	    LOGGER.info("getLabel() -> Salida del servicio para rabrir las facturaciones");
	    
	    if (response == 0 && error.getDescription() == null) {
			error.setCode(400);
			insertResponse.setStatus(SigaConstants.KO);
		} else if (error.getCode() == null) {
			error.setCode(200);
			insertResponse.setStatus(SigaConstants.OK);
		}

	    insertResponse.setError(error);
		
		return insertResponse;
	}
	
	@Override
	@Transactional
	public InsertResponseDTO simularFacturacion(String idFacturacion, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		InsertResponseDTO insertResponse = new InsertResponseDTO();
		org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
		insertResponse.setError(error);
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
	            FcsFactEstadosfacturacion record = new FcsFactEstadosfacturacion();
	            FcsFacturacionjg record2 = new FcsFacturacionjg();
	           
	            LOGGER.info("simularFacturacion() -> Entrada para simular la facturacion");
	            
	            //GUARDAR DATOAS DE LA FACTURACION
	            try {
	            	//PRIMERO ACTUALIZAMOS FCS_FACTURACIONJG EL CAMPO PREVISION A 1
	            	//SETEAMOS LOS DATOS Y HACEMOS UPDATE
	            	LOGGER.info("simularFacturacion() -> Actualizar prevision para simular facturacion");
	            	
		            record2.setPrevision("1");
		            record2.setIdfacturacion(Integer.parseInt(idFacturacion));
		            record2.setIdinstitucion(idInstitucion);
		           		            
		            response = fcsFacturacionJGExtendsMapper.updateByPrimaryKeySelective(record2);
		            
		            LOGGER.info("simularFacturacion() -> Salida actualizar prevision para simular facturacion");
	            	
	            	//SETEAMOS LOS DATOS Y HACEMOS INSERT DEL ESTADO
	            	LOGGER.info("simularFacturacion() -> Guardar datos para simular facturacion");
	            	NewIdDTO idP = fcsFacturacionJGExtendsMapper.getIdOrdenEstado(idInstitucion, idFacturacion);	
	            	Short idOrdenEstado = (short) (Integer.parseInt(idP.getNewId())+1);
	            	Short idEstado = 50;
	            	
		            record.setIdinstitucion(idInstitucion);
		            record.setIdestadofacturacion(idEstado);
		            record.setIdfacturacion(Integer.parseInt(idFacturacion));
		            record.setFechaestado(new Date());
		            record.setFechamodificacion(new Date());
		            record.setUsumodificacion(usuario.getIdusuario());
		            record.setIdordenestado(idOrdenEstado);
		           		            
		            response = fcsFactEstadosfacturacionMapper.insert(record);
		            
		            LOGGER.info("simularFacturacion() -> Salida guardar datos para simular facturacion");
	            }catch(Exception e){
	            	LOGGER.error("ERROR: FacturacionServicesImpl.simularFacturacion() >  Al simular la facturacion.", e);
	            	error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					insertResponse.setStatus(SigaConstants.KO);
	            }            
	        }else {
	        	LOGGER.warn("getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
	        }
	    }else {
	    	LOGGER.warn("getLabel() -> idInstitucion del token nula");
	    }
	        
	    LOGGER.info("getLabel() -> Salida del servicio para simular la facturacion");
	    
	    if (response == 0 && error.getDescription() == null) {
			error.setCode(400);
			insertResponse.setStatus(SigaConstants.KO);
		} else if (error.getCode() == null) {
			error.setCode(200);
			insertResponse.setStatus(SigaConstants.OK);
		}

	    insertResponse.setError(error);
		
		return insertResponse;
	}
}