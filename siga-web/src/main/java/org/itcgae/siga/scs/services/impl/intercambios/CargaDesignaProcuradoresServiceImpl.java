package org.itcgae.siga.scs.services.impl.intercambios;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.EcomOperacionTipoaccionDTO;
import org.itcgae.siga.DTOs.scs.RemesaResolucionDTO;
import org.itcgae.siga.DTOs.scs.RemesasResolucionItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.GestorContadores;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.EcomCola;
import org.itcgae.siga.db.entities.EcomOperacionTipoaccion;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsCargaDesignaProcuradoresExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsRemesasResolucionesExtendsMapper;
import org.itcgae.siga.scs.services.impl.ejg.BusquedaRemesasServiceImpl;
import org.itcgae.siga.scs.services.intercambios.ICargaDesignaProcuradores;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CargaDesignaProcuradoresServiceImpl implements ICargaDesignaProcuradores{
	
	private Logger LOGGER = Logger.getLogger(this.getClass());
	
	@Autowired
	private ScsCargaDesignaProcuradoresExtendsMapper scsCarcaDesignaProcuradoresExtendsMapper;

	@Autowired
	private BusquedaRemesasServiceImpl busquedaRemesasServiceImpl;
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private ScsRemesasResolucionesExtendsMapper scsRemesasResolucionesExtendsMapper;
	
    @Autowired
    private GestorContadores gestorContadores;
    
	@Override
	public RemesaResolucionDTO buscarDesignaProcuradores(RemesasResolucionItem remesasResolucionItem,
			HttpServletRequest request) {
	LOGGER.info("buscarDesignaProcuradores() -> Entrada al servicio para obtener las remesas de resoluciones");
		
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		RemesaResolucionDTO remesaResultadoDTO = new RemesaResolucionDTO();
		List<RemesasResolucionItem> remesasResolucionesItem = null;
		
		if(idInstitucion != null) {
			remesasResolucionesItem = scsCarcaDesignaProcuradoresExtendsMapper.buscarDesignaProcuradores(remesasResolucionItem, idInstitucion);
			if(remesasResolucionesItem != null ) {
				remesaResultadoDTO.setRemesasResolucionItem(remesasResolucionesItem);
			}
		}
		return remesaResultadoDTO;
	}

	@Override
	public String obtenerTipoPCAJG(HttpServletRequest request) {
		String response = "";
		GenParametros tipoPCAJG = busquedaRemesasServiceImpl.getTipoPCAJG(request);
		
		if(tipoPCAJG != null) {
			response = tipoPCAJG.getValor();
		}
		
		return response;
	}
	
    @Override
    public AdmContador recuperarDatosContador(HttpServletRequest request){
    	
    	String token = request.getHeader("Authorization");
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

        String nombreContador = scsCarcaDesignaProcuradoresExtendsMapper.nombreContador(idInstitucion.toString());
        
		AdmContador adm = gestorContadores.getContador(idInstitucion,nombreContador);

		return adm;
    }
	
	@Override
	public EcomOperacionTipoaccionDTO obtenerDesignaProcuradores(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		GenParametros parametro = new GenParametros();
		EcomOperacionTipoaccionDTO ecomOperacionTipoaccionDTO = new EcomOperacionTipoaccionDTO();
		int insertado = 0;
		Error error = new Error();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"obtenerDesignaProcuradores() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info("Lenguaje del usuario: " + usuarios.get(0).getIdlenguaje());

			LOGGER.info(
					"obtenerDesignaProcuradores() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			parametro = busquedaRemesasServiceImpl.getTipoPCAJG(request);

			String tipoCajg = parametro.getValor();
			
			List<EcomOperacionTipoaccion>  ecomOperacionTipoAccion = scsRemesasResolucionesExtendsMapper.ecomOperacionTipoAccion(tipoCajg);

			EcomCola ecomCola = new EcomCola();
			if(ecomOperacionTipoAccion != null && ecomOperacionTipoAccion.size() > 0) {
				ecomCola.setIdinstitucion(idInstitucion);
				ecomCola.setIdoperacion(ecomOperacionTipoAccion.get(0).getIdoperacion());
			}
				try {
					insertado = busquedaRemesasServiceImpl.insert(ecomCola);
				} catch (Exception e) {
					insertado = 0;
					error.setCode(400);
					error.setDescription("Se ha producido un error en la inserción en la tabla ECOM_COLA");
					ecomOperacionTipoaccionDTO.setStatus(SigaConstants.KO);
					ecomOperacionTipoaccionDTO.setError(error);
					LOGGER.error("Se ha producido un error al insertar en ECOM_COLA " + e.getStackTrace());
					throw e;
				}
				if(insertado != 0) {
					error.setCode(200);
					error.setDescription("Obtener Designa Procuradores Correctamente.");
					ecomOperacionTipoaccionDTO.setStatus(SigaConstants.OK);
					ecomOperacionTipoaccionDTO.setError(error);	
				}		
			}
		
		return ecomOperacionTipoaccionDTO;
	}
	

}
