package org.itcgae.siga.scs.guardia.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.scs.guardia.GuardiasDTO;
import org.itcgae.siga.DTO.scs.guardia.GuardiasItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.guardia.ScsGuardiasturnoExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.stereotype.Service;

@Service
public class GuardiasServiceImpl {
	
	private Logger LOGGER = Logger.getLogger(GuardiasServiceImpl.class);

	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	private ScsGuardiasturnoExtendsMapper scsGuardiasturnoExtendsMapper;
	
	public GuardiasDTO searchGuardias(HttpServletRequest request, GuardiasItem guardiasItem) {
		
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		GuardiasDTO guardiaDTO = new GuardiasDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchGuardias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("searchGuardias() -> Entrada para obtener los guardias");

				List<GuardiasItem> guardias = scsGuardiasturnoExtendsMapper.searchGuardias(guardiasItem, idInstitucion.toString(), usuarios.get(0).getIdlenguaje());

				guardiaDTO.setGuardiaItems(guardias);
				
				LOGGER.info("searchGuardias() -> Salida ya con los datos recogidos");
			}
		}
		return guardiaDTO;
	}
	
}
