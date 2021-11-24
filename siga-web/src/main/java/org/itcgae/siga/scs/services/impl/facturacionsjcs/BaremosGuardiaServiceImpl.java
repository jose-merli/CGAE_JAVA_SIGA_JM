package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.scs.BaremosGuardiaDTO;
import org.itcgae.siga.DTO.scs.BaremosGuardiaItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.db.entities.ScsBaremosGuardiaKey;
import org.itcgae.siga.db.services.scs.mappers.ScsBaremosGuardiaMapper;
import org.itcgae.siga.scs.services.facturacionsjcs.IBaremosGuardiaServices;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaremosGuardiaServiceImpl implements IBaremosGuardiaServices {

    private Logger LOGGER = Logger.getLogger(BaremosGuardiaServiceImpl.class);

    @Autowired
    private ScsBaremosGuardiaMapper baremosGuardiaMapper;

    @Override
    public BaremosGuardiaDTO searchBaremosGuardia(Integer idTurno, Integer idGuardia,
                                                      HttpServletRequest request) {
    	BaremosGuardiaDTO baremosGuardiaDTO = new BaremosGuardiaDTO();
    	Error error = new Error();

        LOGGER.info("searchBaremosGuardia() -> Entrada del servicio para obtener baremos guardia ");

        String token = request.getHeader("Authorization");
//        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
       
		try {
			if (null != idInstitucion) {
				ScsBaremosGuardiaKey key = new ScsBaremosGuardiaKey();
				key.setIdInstitucion(idInstitucion);
				key.setIdGuardia(idGuardia);
				key.setIdTurno(idTurno);

				List<BaremosGuardiaItem> lBaremos = baremosGuardiaMapper.searchBaremosGuardia(key);
				baremosGuardiaDTO.setBaremosGuardiaItems(lBaremos);

			} else {
				LOGGER.warn("searchBaremosGuardia() -> idInstitucion del token nula");
			}
		} catch (Exception e) {
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}
        
        baremosGuardiaDTO.setError(error);

        LOGGER.info("searchBaremosGuardia() -> Salida del servicio para obtener baremos guardia");

        return baremosGuardiaDTO;
    }


}