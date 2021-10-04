package org.itcgae.siga.scs.services.impl.ejg;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.RemesaResolucionDTO;
import org.itcgae.siga.DTOs.scs.RemesasResolucionItem;
import org.itcgae.siga.db.services.scs.mappers.ScsRemesasResolucionesExtendsMapper;
import org.itcgae.siga.scs.services.ejg.IRemesasResoluciones;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RemesasResolucionesServiceImpl implements IRemesasResoluciones{
	
	private Logger LOGGER = Logger.getLogger(this.getClass());
	
	@Autowired
	private ScsRemesasResolucionesExtendsMapper scsRemesasResultadoExtendsMapper;

	@Override
	public RemesaResolucionDTO buscarRemesasResoluciones(RemesasResolucionItem remesasResolucionItem,
			HttpServletRequest request) {
		LOGGER.info("getLabel() -> Entrada al servicio para obtener las remesas de resoluciones");
		
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		RemesaResolucionDTO remesaResultadoDTO = new RemesaResolucionDTO();
		List<RemesasResolucionItem> remesasResolucionesItem = null;
		
		if(idInstitucion != null) {
			remesasResolucionesItem = scsRemesasResultadoExtendsMapper.buscarRemesasResoluciones(remesasResolucionItem, idInstitucion);
			if(remesasResolucionesItem != null ) {
				remesaResultadoDTO.setRemesasResolucionItem(remesasResolucionesItem);
			}
		}
		return remesaResultadoDTO;
	}
	
	

}
