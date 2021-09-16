package org.itcgae.siga.scs.services.impl.ejg;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.RemesaResultadoDTO;
import org.itcgae.siga.DTOs.scs.RemesasResultadoItem;
import org.itcgae.siga.db.services.scs.mappers.ScsRemesasResultadoExtendsMapper;
import org.itcgae.siga.scs.services.ejg.IRemesasResultados;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemesasResultadoServiceImpl implements IRemesasResultados{
	
	private Logger LOGGER = Logger.getLogger(RemesasResultadoServiceImpl.class);
	
	@Autowired
	private ScsRemesasResultadoExtendsMapper scsRemesasResultadoExtendsMapper;
	
	@Override
	public RemesaResultadoDTO buscarRemesasResultado(RemesasResultadoItem remesasResultadoItem, HttpServletRequest request) {
		LOGGER.info("getLabel() -> Entrada del servicio para obtener las remesas de resultados");
		
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		RemesaResultadoDTO remesaResultadoDTO = new RemesaResultadoDTO();
		List<RemesasResultadoItem> remesasResultadoItems = null;
		
		if (idInstitucion != null) {
			remesasResultadoItems = scsRemesasResultadoExtendsMapper.buscarRemesasResultado(remesasResultadoItem, idInstitucion);
			
			if (remesasResultadoItems != null) {
				remesaResultadoDTO.setRemesasResultadosItem(remesasResultadoItems);
			}
		}
		
		
		LOGGER.info("getLabel() -> Salida del servicio para obtener las remesas de resultados");
		return remesaResultadoDTO;
	}

}
