package org.itcgae.siga.scs.services.impl.guardia;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaDTO;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsSaltoscompensacionesExtendsMapper;
import org.itcgae.siga.scs.services.guardia.SaltosCompGuardiasService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaltosCompGuardiasServiceImpl implements SaltosCompGuardiasService {

	private Logger LOGGER = Logger.getLogger(SaltosCompGuardiasServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsSaltoscompensacionesExtendsMapper saltoscompensacionesMapper;

	@Override
	public SaltoCompGuardiaDTO searchSaltosYCompensaciones(SaltoCompGuardiaItem saltoItem, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		SaltoCompGuardiaDTO saltoCompDTO = new SaltoCompGuardiaDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchGuardias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("searchGuardias() -> Entrada para obtener los saltos y compensaciones");

				if (!UtilidadesString.esCadenaVacia(saltoItem.getFechaDesde()))
					saltoItem.setFechaDesde("AND TRUNC(FECHA) >= TRUNC(TO_DATE(" + saltoItem.getFechaDesde()
							+ ", 'YYYY/MM/DD HH24:MI:SS'))");
				if (!UtilidadesString.esCadenaVacia(saltoItem.getFechaHasta()))
					saltoItem.setFechaDesde("AND TRUNC(FECHA) <= TRUNC(TO_DATE(" + saltoItem.getFechaHasta()
							+ ", 'YYYY/MM/DD HH24:MI:SS'))");
				if (!UtilidadesString.esCadenaVacia(saltoItem.getSaltoCompensacion()))
					saltoItem.setFechaDesde("AND SALTOOCOMPENSACION = '" + saltoItem.getSaltoCompensacion() + "'");

				List<SaltoCompGuardiaItem> saltosComp = saltoscompensacionesMapper.searchSaltosCompensaciones(saltoItem,
						idInstitucion.toString());

				saltoCompDTO.setSaltosCompItems(saltosComp);

				LOGGER.info("searchGuardias() -> Salida ya con los datos recogidos");
			}
		}
		return saltoCompDTO;
	}

}
