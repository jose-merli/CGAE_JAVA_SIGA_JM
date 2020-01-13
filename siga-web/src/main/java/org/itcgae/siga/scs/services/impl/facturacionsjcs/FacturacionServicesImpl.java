package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.FacturacionDTO;
import org.itcgae.siga.DTOs.scs.FacturacionDeleteDTO;
import org.itcgae.siga.DTOs.scs.FacturacionItem;
import org.itcgae.siga.DTOs.scs.PagosjgDTO;
import org.itcgae.siga.DTOs.scs.PagosjgItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.FcsFactActuaciondesignaExample;
import org.itcgae.siga.db.entities.FcsFactApunteExample;
import org.itcgae.siga.db.entities.FcsFactEjgExample;
import org.itcgae.siga.db.entities.FcsFactEstadosfacturacion;
import org.itcgae.siga.db.entities.FcsFactEstadosfacturacionExample;
import org.itcgae.siga.db.entities.FcsFactGrupofactHito;
import org.itcgae.siga.db.entities.FcsFactGrupofactHitoExample;
import org.itcgae.siga.db.entities.FcsFactGuardiascolegiadoExample;
import org.itcgae.siga.db.entities.FcsFactSojExample;
import org.itcgae.siga.db.entities.FcsFacturacionEstadoEnvioExample;
import org.itcgae.siga.db.entities.FcsFacturacionjg;
import org.itcgae.siga.db.entities.FcsFacturacionjgExample;
import org.itcgae.siga.db.entities.FcsHistoAcreditacionprocExample;
import org.itcgae.siga.db.entities.FcsHistoTipoactcostefijoExample;
import org.itcgae.siga.db.entities.FcsHistoricoAcreditacionExample;
import org.itcgae.siga.db.entities.FcsHistoricoHitofactExample;
import org.itcgae.siga.db.entities.FcsHistoricoProcedimientosExample;
import org.itcgae.siga.db.entities.FcsHistoricoTipoactuacionExample;
import org.itcgae.siga.db.entities.FcsHistoricoTipoasistcolegioExample;
import org.itcgae.siga.db.entities.FcsMovimientosvarios;
import org.itcgae.siga.db.entities.FcsMovimientosvariosExample;
import org.itcgae.siga.db.entities.ScsActuacionasistencia;
import org.itcgae.siga.db.entities.ScsActuacionasistenciaExample;
import org.itcgae.siga.db.entities.ScsAsistencia;
import org.itcgae.siga.db.entities.ScsAsistenciaExample;
import org.itcgae.siga.db.entities.ScsEjg;
import org.itcgae.siga.db.entities.ScsEjgExample;
import org.itcgae.siga.db.entities.ScsGuardiascolegiado;
import org.itcgae.siga.db.entities.ScsGuardiascolegiadoExample;
import org.itcgae.siga.db.entities.ScsSoj;
import org.itcgae.siga.db.entities.ScsSojExample;
import org.itcgae.siga.db.mappers.FcsFactActuaciondesignaMapper;
import org.itcgae.siga.db.mappers.FcsFactApunteMapper;
import org.itcgae.siga.db.mappers.FcsFactEjgMapper;
import org.itcgae.siga.db.mappers.FcsFactEstadosfacturacionMapper;
import org.itcgae.siga.db.mappers.FcsFactGrupofactHitoMapper;
import org.itcgae.siga.db.mappers.FcsFactGuardiascolegiadoMapper;
import org.itcgae.siga.db.mappers.FcsFactSojMapper;
import org.itcgae.siga.db.mappers.FcsFacturacionEstadoEnvioMapper;
import org.itcgae.siga.db.mappers.FcsFacturacionjgMapper;
import org.itcgae.siga.db.mappers.FcsHistoAcreditacionprocMapper;
import org.itcgae.siga.db.mappers.FcsHistoTipoactcostefijoMapper;
import org.itcgae.siga.db.mappers.FcsHistoricoAcreditacionMapper;
import org.itcgae.siga.db.mappers.FcsHistoricoHitofactMapper;
import org.itcgae.siga.db.mappers.FcsHistoricoProcedimientosMapper;
import org.itcgae.siga.db.mappers.FcsHistoricoTipoactuacionMapper;
import org.itcgae.siga.db.mappers.FcsHistoricoTipoasistcolegioMapper;
import org.itcgae.siga.db.mappers.FcsMovimientosvariosMapper;
import org.itcgae.siga.db.mappers.FcsPagosjgMapper;
import org.itcgae.siga.db.mappers.ScsActuacionasistenciaMapper;
import org.itcgae.siga.db.mappers.ScsAsistenciaMapper;
import org.itcgae.siga.db.mappers.ScsEjgMapper;
import org.itcgae.siga.db.mappers.ScsGuardiascolegiadoMapper;
import org.itcgae.siga.db.mappers.ScsSojMapper;
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
	
	@Autowired
	private FcsFactGrupofactHitoMapper fcsFactGrupofactHitoMapper;
	
	@Autowired
	private FcsHistoricoTipoactuacionMapper fcsHistoricoTipoactuacionMapper;
	
	@Autowired
	FcsHistoricoTipoasistcolegioMapper fcsHistoricoTipoasistcolegioMapper;
	
	@Autowired
	FcsHistoTipoactcostefijoMapper fcsHistoTipoactcostefijoMapper;
	
	@Autowired
	FcsHistoricoHitofactMapper fcsHistoricoHitofactMapper;
	
	@Autowired
	FcsFactGuardiascolegiadoMapper fcsFactGuardiascolegiadoMapper;
	
	@Autowired
	FcsFactApunteMapper fcsFactApunteMapper;
		
	@Autowired
	FcsFacturacionEstadoEnvioMapper fcsFacturacionEstadoEnvioMapper;
	
	@Autowired
	FcsFactActuaciondesignaMapper fcsFactActuaciondesignaMapper;
	
	@Autowired
	FcsFactEjgMapper fcsFactEjgMapper;
	
	@Autowired
	FcsFactSojMapper fcsFactSojMapper;
	
	@Autowired
	FcsHistoAcreditacionprocMapper fcsHistoAcreditacionprocMapper;
	
	@Autowired
	FcsHistoricoAcreditacionMapper fcsHistoricoAcreditacionMapper;
	
	@Autowired
	FcsHistoricoProcedimientosMapper fcsHistoricoProcedimientosMapper;
	
	@Autowired
	ScsGuardiascolegiadoMapper scsGuardiascolegiadoMapper;
	
	@Autowired
	ScsActuacionasistenciaMapper scsActuacionasistenciaMapper;
	
	@Autowired
	FcsMovimientosvariosMapper fcsMovimientosvariosMapper;
	
	@Autowired
	ScsAsistenciaMapper scsAsistenciaMapper;
	
	@Autowired
	ScsEjgMapper scsEjgMapper;
	
	@Autowired
	ScsSojMapper scsSojMapper;
	
	@Autowired
	FcsPagosjgMapper fcsPagosjgMapper;
	
	@Autowired
	FcsFacturacionjgMapper fcsFacturacionjgMapper;

	
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
	@Transactional
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
	                
	            LOGGER.info("eliminaFacturaciones() / FacturacionServicesImpl.eliminarFacturaciones() -> Entrada a eliminafacturacion para eliminar la facturacion de las tablas relacionadas");
	            response = eliminaTablasFacturacion(idFactura, idInstitucion.toString());
	            
	            if(response > 0)
	            	response = updateTablasFacturacion(idFactura,idInstitucion.toString());
	            
	            if(response > 0)
	            	response = eliminaFacturacionjg(idFactura,idInstitucion.toString());
	            
	            if (response > 0){
	            	facturacionesDelete.setStatus(SigaConstants.OK);
	            }else{
	            	facturacionesDelete.setStatus(SigaConstants.KO);
	            }
	           
	            LOGGER.info("eliminaFacturaciones() / FacturacionServicesImpl.eliminarFacturaciones() -> Salida a eliminafacturacion para eliminar la facturacion de las tablas relacionadas");
	        }else {
	        	LOGGER.warn("getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
	        }
	    }else {
	    	LOGGER.warn("getLabel() -> idInstitucion del token nula");
	    }
	        
	    LOGGER.info("getLabel() -> Salida del servicio para eliminar las facturaciones");
	    
	    return facturacionesDelete;	
	}

	private int eliminaTablasFacturacion(int idFactura, String idInstitucion) {
		try {
			//ELIMINAMOS EL REGISTRO DE TODAS LAS TABLAS RELACIONADAS CON LA FACTURACION
			
			FcsHistoricoTipoactuacionExample fcsHistoricoTipoactuacionExample = new FcsHistoricoTipoactuacionExample();
			fcsHistoricoTipoactuacionExample.createCriteria().andIdfacturacionEqualTo(idFactura).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			fcsHistoricoTipoactuacionMapper.deleteByExample(fcsHistoricoTipoactuacionExample);
			
			FcsHistoricoTipoasistcolegioExample fcsHistoricoTipoasistcolegioExample = new FcsHistoricoTipoasistcolegioExample();
			fcsHistoricoTipoasistcolegioExample.createCriteria().andIdfacturacionEqualTo(idFactura).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			fcsHistoricoTipoasistcolegioMapper.deleteByExample(fcsHistoricoTipoasistcolegioExample);
	
			FcsHistoTipoactcostefijoExample fcsHistoTipoactcostefijoExample = new FcsHistoTipoactcostefijoExample();
			fcsHistoTipoactcostefijoExample.createCriteria().andIdfacturacionEqualTo(idFactura).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			fcsHistoTipoactcostefijoMapper.deleteByExample(fcsHistoTipoactcostefijoExample);
	
			FcsHistoricoHitofactExample fcsHistoricoHitofactExample = new FcsHistoricoHitofactExample();
			fcsHistoricoHitofactExample.createCriteria().andIdfacturacionEqualTo(idFactura).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			fcsHistoricoHitofactMapper.deleteByExample(fcsHistoricoHitofactExample);
	
			FcsFactGrupofactHitoExample fcsFactGrupofactHitoExample = new FcsFactGrupofactHitoExample();
			fcsFactGrupofactHitoExample.createCriteria().andIdfacturacionEqualTo(idFactura).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			fcsFactGrupofactHitoMapper.deleteByExample(fcsFactGrupofactHitoExample);
			
			FcsFactGuardiascolegiadoExample fcsFactGuardiascolegiadoExample = new FcsFactGuardiascolegiadoExample();
			fcsFactGuardiascolegiadoExample.createCriteria().andIdfacturacionEqualTo(idFactura).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			fcsFactGuardiascolegiadoMapper.deleteByExample(fcsFactGuardiascolegiadoExample);
			
			FcsFactApunteExample fcsFactApunteExample = new FcsFactApunteExample();
			fcsFactApunteExample.createCriteria().andIdfacturacionEqualTo(idFactura).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			fcsFactApunteMapper.deleteByExample(fcsFactApunteExample);
			
			FcsFactEstadosfacturacionExample fcsFactEstadosfacturacionExample = new FcsFactEstadosfacturacionExample();
			fcsFactEstadosfacturacionExample.createCriteria().andIdfacturacionEqualTo(idFactura).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			fcsFactEstadosfacturacionMapper.deleteByExample(fcsFactEstadosfacturacionExample);
			
			FcsFacturacionEstadoEnvioExample fcsFacturacionEstadoEnvioExample = new FcsFacturacionEstadoEnvioExample();
			fcsFacturacionEstadoEnvioExample.createCriteria().andIdfacturacionEqualTo(idFactura).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			fcsFacturacionEstadoEnvioMapper.deleteByExample(fcsFacturacionEstadoEnvioExample);
			
			FcsFactActuaciondesignaExample fcsFactActuaciondesignaExample = new FcsFactActuaciondesignaExample();
			fcsFactActuaciondesignaExample.createCriteria().andIdfacturacionEqualTo(idFactura).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			fcsFactActuaciondesignaMapper.deleteByExample(fcsFactActuaciondesignaExample);
			
			FcsFactEjgExample fcsFactEjgExample = new FcsFactEjgExample();
			fcsFactEjgExample.createCriteria().andIdfacturacionEqualTo(idFactura).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			fcsFactEjgMapper.deleteByExample(fcsFactEjgExample);
	
			FcsFactSojExample fcsFactSojExample = new FcsFactSojExample();
			fcsFactSojExample.createCriteria().andIdfacturacionEqualTo(idFactura).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			fcsFactSojMapper.deleteByExample(fcsFactSojExample);
	
			FcsHistoAcreditacionprocExample fcsHistoAcreditacionprocExample = new FcsHistoAcreditacionprocExample();
			fcsHistoTipoactcostefijoExample.createCriteria().andIdfacturacionEqualTo(idFactura).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			fcsHistoAcreditacionprocMapper.deleteByExample(fcsHistoAcreditacionprocExample);
			
			FcsHistoricoAcreditacionExample fcsHistoricoAcreditacionExample = new FcsHistoricoAcreditacionExample();
			fcsHistoricoAcreditacionExample.createCriteria().andIdfacturacionEqualTo(idFactura).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			fcsHistoricoAcreditacionMapper.deleteByExample(fcsHistoricoAcreditacionExample);
	
			FcsHistoricoProcedimientosExample fcsHistoricoProcedimientosExample = new FcsHistoricoProcedimientosExample();
			fcsHistoricoProcedimientosExample.createCriteria().andIdfacturacionEqualTo(idFactura).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			fcsHistoricoProcedimientosMapper.deleteByExample(fcsHistoricoProcedimientosExample);
						
			return 1;
		}catch(Exception e) {
			LOGGER.error("Se ha producido un error al eliminar los registros relacionados con la facturación: "+idFactura+" del colegio "+idInstitucion,e);
			return 0;
		}
	}

	private int updateTablasFacturacion(int idFactura, String idInstitucion) {
		try 
		{
			//ACTUALIZO A NULL EL ID DE FACTURACIÓN EN LAS TABLAS DONDE ESTÁ REFERENCIADA LA FACTURACIÓN QUE ESTAMOS ELIMINANDO
			ScsGuardiascolegiadoExample scsGuardiasColegiadoExample = new ScsGuardiascolegiadoExample();
			scsGuardiasColegiadoExample.createCriteria().andIdfacturacionEqualTo(idFactura).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<ScsGuardiascolegiado> listaScsGuardiascolegiado = scsGuardiascolegiadoMapper.selectByExample(scsGuardiasColegiadoExample);
			for(ScsGuardiascolegiado item : listaScsGuardiascolegiado) {
				item.setIdfacturacion(null);
				scsGuardiascolegiadoMapper.updateByExample(item, scsGuardiasColegiadoExample);
			}
			
			ScsActuacionasistenciaExample scsActuacionasistenciaExample = new ScsActuacionasistenciaExample();
			scsActuacionasistenciaExample.createCriteria().andIdfacturacionEqualTo(idFactura).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<ScsActuacionasistencia> listaScsActuacionasistencia = scsActuacionasistenciaMapper.selectByExample(scsActuacionasistenciaExample);
			for(ScsActuacionasistencia item : listaScsActuacionasistencia) {
				item.setIdfacturacion(null);
				scsActuacionasistenciaMapper.updateByExample(item, scsActuacionasistenciaExample);
			}
			
			FcsMovimientosvariosExample fcsMovimientosvariosExample = new FcsMovimientosvariosExample();
			fcsMovimientosvariosExample.createCriteria().andIdfacturacionEqualTo(idFactura).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<FcsMovimientosvarios> listaFcsMovimientosvarios = fcsMovimientosvariosMapper.selectByExample(fcsMovimientosvariosExample);
			for(FcsMovimientosvarios item : listaFcsMovimientosvarios) {
				item.setIdfacturacion(null);
				fcsMovimientosvariosMapper.updateByExample(item, fcsMovimientosvariosExample);
			}
			
			ScsAsistenciaExample scsAsistenciaExample = new ScsAsistenciaExample();
			scsAsistenciaExample.createCriteria().andIdfacturacionEqualTo(idFactura).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<ScsAsistencia> listaScsAsistencia = scsAsistenciaMapper.selectByExample(scsAsistenciaExample);
			for(ScsAsistencia item : listaScsAsistencia) {
				item.setIdfacturacion(null);
				scsAsistenciaMapper.updateByExample(item, scsAsistenciaExample);
			}
			
			ScsEjgExample scsEjgExample = new ScsEjgExample();
			scsEjgExample.createCriteria().andIdfacturacionEqualTo(idFactura).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<ScsEjg> listaScsEjg = scsEjgMapper.selectByExample(scsEjgExample);
			for(ScsEjg item : listaScsEjg) {
				item.setIdfacturacion(null);
				scsEjgMapper.updateByExample(item, scsEjgExample);
			}
			
			ScsSojExample scsSojExample = new ScsSojExample();
			scsSojExample.createCriteria().andIdfacturacionEqualTo(idFactura).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<ScsSoj> listaScsSoj = scsSojMapper.selectByExample(scsSojExample);
			for(ScsSoj item : listaScsSoj) {
				item.setIdfacturacion(null);
				scsSojMapper.updateByExample(item, scsSojExample);
			}
	
			//Parece que no hay que hacer update en la tabla FCS_PAGOSJG porque no debería haber pagos para una facturación que se puede eliminar

			return 1;
		}catch(Exception e) {
			LOGGER.error("Se ha producido un error al actualizar a null los registros asociados a la facturación: "+idFactura+" del colegio "+idInstitucion,e);
			return 0;
		}
		
	}

	private int eliminaFacturacionjg(int idFactura, String idInstitucion) {
		try 
		{
			//ELIMINO LA FACTURACION
			FcsFacturacionjgExample fcsFacturacionjgExample = new FcsFacturacionjgExample();
			fcsFacturacionjgExample.createCriteria().andIdfacturacionEqualTo(idFactura).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			fcsFacturacionjgMapper.deleteByExample(fcsFacturacionjgExample);
			return 1;
		}catch(Exception e) {
			LOGGER.error("Se ha producido un error al eliminar la facturación: "+idFactura+" del colegio "+idInstitucion,e);
			return 0;
		}
		
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
	public StringDTO numApuntes(String idFacturacion, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		StringDTO apuntes = new StringDTO();
		
		if(null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
	        exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
	        LOGGER.info("getLabel() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	        List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	        LOGGER.info("getLabel() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
	            
	        if(null != usuarios && usuarios.size() > 0) {
	        	AdmUsuarios usuario = usuarios.get(0);
	            usuario.setIdinstitucion(idInstitucion);
	           
	            LOGGER.info("numApuntes() / fcsFacturacionJGExtendsMapper.numApuntes() -> Entrada a fcsFacturacionJGExtendsMapper para obtener los números de apuntes");
	            String numeroApuntes = fcsFacturacionJGExtendsMapper.numApuntes(idFacturacion, idInstitucion.toString());
	            apuntes.setValor(numeroApuntes);
	            LOGGER.info("historicoFacturacion() / fcsFacturacionJGExtendsMapper.historicoFacturacion() -> Salida a fcsFacturacionJGExtendsMapper para obtener los números de apuntes");
	        }else {
	        	LOGGER.warn("getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
	        }
	    }else {
	    	LOGGER.warn("getLabel() -> idInstitucion del token nula");
	    }
	        
	    LOGGER.info("getLabel() -> Salida del servicio para obtener los números de apuntes");
	    
	    return apuntes;	
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
	           
	            LOGGER.info("saveFacturacion() / fcsFacturacionJGExtendsMapper.saveFacturacion() -> Entrada a guardar los datos de la facturacón");
	            
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
	
	@Override
	public FacturacionDTO conceptosFacturacion(String idFacturacion, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		FacturacionDTO facturaciones = new FacturacionDTO();
		String idLenguaje;
		
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
	                
	            LOGGER.info("conceptosFacturacion() / fcsFacturacionJGExtendsMapper.conceptosFacturacion() -> Entrada a fcsFacturacionJGExtendsMapper para obtener los conceptos de facturacón");
	            List<FacturacionItem> facturacionItem = fcsFacturacionJGExtendsMapper.conceptosFacturacion(idFacturacion, idInstitucion.toString(), idLenguaje);
	            facturaciones.setFacturacionItem(facturacionItem);
	            LOGGER.info("conceptosFacturacion() / fcsFacturacionJGExtendsMapper.conceptosFacturacion() -> Salida de fcsFacturacionJGExtendsMapper para obtener los conceptos de facturación");
	        }else {
	        	LOGGER.warn("getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
	        }
	    }else {
	    	LOGGER.warn("getLabel() -> idInstitucion del token nula");
	    }
	        
	    LOGGER.info("getLabel() -> Salida del servicio para obtener los conceptos de facturaciones");
	    
	    return facturaciones;	
	}
	
	@Override
	@Transactional
	public InsertResponseDTO saveConceptosFac(FacturacionItem facturacionItem, HttpServletRequest request) {
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
	            FcsFactGrupofactHito record = new FcsFactGrupofactHito();
	           
	            LOGGER.info("saveConceptosFac() / fcsFacturacionJGExtendsMapper.saveConceptosFac() -> Entrada a guardar los conceptos de la facturacón");
	            
	            //GUARDAR DATOAS DE LA FACTURACION
	            try {
	            	//OBTENEMOS EL ID DE LA FACTURA
	            	LOGGER.info("saveConceptosFac() / fcsFacturacionJGExtendsMapper.saveConceptosFac() -> Guardar conceptos en fcsFacturacionjg");
	            	Short idGrupoFacturacion=(short) Integer.parseInt(facturacionItem.getIdGrupo());
	            	Short idHitoGeneral = (short) Integer.parseInt(facturacionItem.getIdConcepto());
	            	
	            	//COMPROBAMOS QUE LOS DATOS A INSERTAR NO EXISTAN EN LA TABLA	            	
	            	FcsFactGrupofactHitoExample example = new FcsFactGrupofactHitoExample();
	            	example.createCriteria().andIdfacturacionEqualTo(Integer.parseInt(facturacionItem.getIdFacturacion())).andIdinstitucionEqualTo(idInstitucion).andIdgrupofacturacionEqualTo(idGrupoFacturacion).andIdhitogeneralEqualTo(idHitoGeneral);	            	
	            		            	
	            	List<FcsFactGrupofactHito> conceptos = fcsFactGrupofactHitoMapper.selectByExample(example);
	            	
		            if(conceptos.size()==0) {		            	
		            	//SETEAMOS LOS DATOS Y GUARDAMOS LA FACTURA	      
			            record.setIdinstitucion(idInstitucion);
			            record.setIdfacturacion(Integer.parseInt(facturacionItem.getIdFacturacion()));
			            record.setIdgrupofacturacion(idGrupoFacturacion);
			            record.setIdhitogeneral(idHitoGeneral);
			            record.setFechamodificacion(new Date());
			            record.setUsumodificacion(usuario.getIdusuario());
			           		            
			            response = fcsFactGrupofactHitoMapper.insert(record);
			            
			            LOGGER.info("saveConceptosFac() / fcsFacturacionJGExtendsMapper.saveFacturacion() -> Salida guardar datos en fcsFacturacionjg");
		            }else {
		            	error.setCode(400);
						error.setDescription("facturacionSJCS.facturacionesYPagos.mensaje.conceptoExistente");
		            }
	            }catch(Exception e){
	            	LOGGER.error("ERROR: FacturacionServicesImpl.saveConceptosFac() > al guardar los conceptos de la facturacion.", e);
	            	error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					insertResponse.setStatus(SigaConstants.KO);
	            }            
	            
	            LOGGER.info("saveConceptosFac() / fcsFacturacionJGExtendsMapper.saveConceptosFac() -> Salida de guardar los conceptos de la facturación");
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
			insertResponse.setStatus(SigaConstants.OK);
		}

	    insertResponse.setError(error);
		
		return insertResponse;
	}
	
	@Override
	@Transactional
	public UpdateResponseDTO updateConceptosFac(FacturacionItem facturacionItem, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		UpdateResponseDTO updateResponse = new UpdateResponseDTO();
		org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
		updateResponse.setError(error);
		
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
	            FcsFactGrupofactHito record = new FcsFactGrupofactHito();
	            
	            try {
	               	LOGGER.info("updateConceptosFac() -> Actualizar datos de los conceptos de facturacion");
            
		            Short idGrupoFacturacion=(short) Integer.parseInt(facturacionItem.getIdGrupo());
		            Short idGrupoOld=(short) Integer.parseInt(facturacionItem.getIdGrupoOld());
		            
	            	Short idHitoGeneral = (short) Integer.parseInt(facturacionItem.getIdConcepto());
	            	Short idHitoOld = (short) Integer.parseInt(facturacionItem.getIdConceptoOld());
	            	
	            	//COMPROBAMOS QUE LOS DATOS A INSERTAR NO EXISTAN EN LA TABLA
	            	LOGGER.info("saveConceptosFac() / fcsFacturacionJGExtendsMapper.saveConceptosFac() -> Entrada: comprobamos que los conceptos no existan previamente.");
	            	
	            	FcsFactGrupofactHitoExample example = new FcsFactGrupofactHitoExample();
	            	example.createCriteria().andIdfacturacionEqualTo(Integer.parseInt(facturacionItem.getIdFacturacion())).andIdinstitucionEqualTo(idInstitucion).andIdgrupofacturacionEqualTo(idGrupoFacturacion).andIdhitogeneralEqualTo(idHitoGeneral);	            	
	            		            	
	            	List<FcsFactGrupofactHito> conceptos = fcsFactGrupofactHitoMapper.selectByExample(example);
	            	
		            if(conceptos.size()==0) {		            	
		            	FcsFactGrupofactHitoExample exampleUpdate = new FcsFactGrupofactHitoExample();
		            	
		            	exampleUpdate.createCriteria().andIdfacturacionEqualTo(Integer.parseInt(facturacionItem.getIdFacturacion())).andIdinstitucionEqualTo(idInstitucion).andIdgrupofacturacionEqualTo(idGrupoOld).andIdhitogeneralEqualTo(idHitoOld);	            	
		            	//SETEAMOS LOS DATOS Y GUARDAMOS LA FACTURA	      
			            record.setIdinstitucion(idInstitucion);
			            record.setIdfacturacion(Integer.parseInt(facturacionItem.getIdFacturacion()));
			            record.setIdgrupofacturacion(idGrupoFacturacion);
			            record.setIdhitogeneral(idHitoGeneral);
			            record.setFechamodificacion(new Date());
			            record.setUsumodificacion(usuario.getIdusuario());
			           		            
			            response = fcsFactGrupofactHitoMapper.updateByExampleSelective(record, exampleUpdate);
			            
			            LOGGER.info("updateConceptosFac() -> Salida actualizar datos de los conceptos de facturacion");
	            	}else{
		            	error.setCode(400);
						error.setDescription("facturacionSJCS.facturacionesYPagos.mensaje.conceptoExistente");
						LOGGER.info("saveConceptosFac() -> Salida: comprobamos que los conceptos no existan previamente.");
		            }
	            }catch(Exception e){
	            	LOGGER.error("ERROR: FacturacionServicesImpl.updateConceptosFac() > al actualizar los conceptos de la facturacion.", e);
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
	        
		LOGGER.info("getLabel() -> Salida del servicio para actualizar los conceptos de las facturaciones");
	    
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
	
	public DeleteResponseDTO deleteConceptosFac(List<FacturacionItem> facturacionDTO, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		DeleteResponseDTO deleteResponse = new DeleteResponseDTO();
		org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
		deleteResponse.setError(error);
		
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
	            FcsFactGrupofactHito record = new FcsFactGrupofactHito();
	            
	            try {		
	            	LOGGER.info("deleteConceptosFac() -> Eliminar conceptos de facturacion");
	                
	            	for (FacturacionItem factItem : facturacionDTO) {
	            		Short idGrupoFacturacion=(short) Integer.parseInt(factItem.getIdGrupo());
	 	            	Short idHitoGeneral = (short) Integer.parseInt(factItem.getIdConcepto());
	 	            	
	 	            	//SETEAMOS LOS DATOS Y GUARDAMOS LA FACTURA	      
	 		            record.setIdinstitucion(idInstitucion);
	 		            record.setIdfacturacion(Integer.parseInt(factItem.getIdFacturacion()));
	 		            record.setIdgrupofacturacion(idGrupoFacturacion);
	 		            record.setIdhitogeneral(idHitoGeneral);
	 		            
	 		           response = fcsFactGrupofactHitoMapper.deleteByPrimaryKey(record);
	            	}
		            
		            LOGGER.info("deleteConceptosFac() SALIDA -> Eliminar conceptos de facturacion");
	            }catch(Exception e){
	            	LOGGER.error("ERROR: FacturacionServicesImpl.deleteConceptosFac() > al eliminar los conceptos de la facturacion.", e);
	            	error.setCode(400);
					error.setDescription("general.mensaje.error.bbdd");
					deleteResponse.setStatus(SigaConstants.KO);
	            }            
	        }else {
	        	LOGGER.warn("getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
	        }
	    }else {
	    	LOGGER.warn("getLabel() -> idInstitucion del token nula");
	    }
	        
		LOGGER.info("getLabel() -> Salida del servicio para actualizar los conceptos de las facturaciones");
	    
	    if (response == 0 && error.getDescription() == null) {
			error.setCode(400);
			deleteResponse.setStatus(SigaConstants.KO);
		} else if (error.getCode() == null) {
			error.setCode(200);
			deleteResponse.setStatus(SigaConstants.OK);
		}

	    deleteResponse.setError(error);
	    
		return deleteResponse;
	}
	
	@Override
	public PagosjgDTO datosPagos(String idFacturacion, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		PagosjgDTO pagos = new PagosjgDTO();
		String idLenguaje = "";
		
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
	                
	            LOGGER.info("datosPagos() / fcsFacturacionJGExtendsMapper.datosPagos() -> Entrada a fcsFacturacionJGExtendsMapper para obtener los datos de los pagos");
	            List<PagosjgItem> pagosItem = fcsFacturacionJGExtendsMapper.datosPagos(idFacturacion, idInstitucion.toString(), idLenguaje);
	            pagos.setPagosjgItem(pagosItem);
	            LOGGER.info("datosPagos() / fcsFacturacionJGExtendsMapper.datosPagos() -> Salida fcsFacturacionJGExtendsMapper para obtener los datos de los pagos");
	        }else {
	        	LOGGER.warn("getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
	        }
	    }else {
	    	LOGGER.warn("getLabel() -> idInstitucion del token nula");
	    }
	        
	    LOGGER.info("getLabel() -> Salida del servicio para obtener los datos de las facturaciones");
	    
	    return pagos;	
	}
}