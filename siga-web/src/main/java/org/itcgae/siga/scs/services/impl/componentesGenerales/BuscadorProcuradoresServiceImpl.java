package org.itcgae.siga.scs.services.impl.componentesGenerales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.ProcuradorDTO;
import org.itcgae.siga.DTOs.scs.ProcuradorItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsProcuradorExtendsMapper;
import org.itcgae.siga.scs.services.componentesGenerales.BuscadorProcuradoresService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuscadorProcuradoresServiceImpl implements BuscadorProcuradoresService {

	private Logger LOGGER = Logger.getLogger(BuscadorProcuradoresServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private ScsProcuradorExtendsMapper scsProcuradorExtendsMapper;

	@Override
	public ProcuradorDTO searchProcuradores(HttpServletRequest request, ProcuradorItem procuradorItem) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ProcuradorDTO procuradorDTO = new ProcuradorDTO();
		List<ProcuradorItem> procuradoresItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchProcuradores() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchProcuradores() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchProcuradores() / cenTiposolicitudSqlExtendsMapper.selectTipoSolicitud() -> Entrada a cenTiposolicitudSqlExtendsMapper para obtener los tipos de solicitud");

				procuradoresItems = scsProcuradorExtendsMapper.buscadorProcurador(idInstitucion.toString(),
						procuradorItem);

				LOGGER.info(
						"searchProcuradores() / cenTiposolicitudSqlExtendsMapper.selectTipoSolicitud() -> Salida a cenTiposolicitudSqlExtendsMapper para obtener los tipos de solicitud");

				if (procuradoresItems != null) {
					procuradorDTO.setProcuradorItems(procuradoresItems);
				}
			}

		}
		LOGGER.info("searchZones() -> Salida del servicio para obtener las zonas");
		return procuradorDTO;

	}

}
