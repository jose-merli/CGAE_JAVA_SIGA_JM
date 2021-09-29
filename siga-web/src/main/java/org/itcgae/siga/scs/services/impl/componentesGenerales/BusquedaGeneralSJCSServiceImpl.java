package org.itcgae.siga.scs.services.impl.componentesGenerales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.ColegiadosSJCSDTO;
import org.itcgae.siga.DTOs.scs.ColegiadosSJCSItem;
import org.itcgae.siga.DTOs.scs.ProcuradorDTO;
import org.itcgae.siga.DTOs.scs.ProcuradorItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.scs.services.componentesGenerales.BusquedaGeneralSJCSService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusquedaGeneralSJCSServiceImpl implements BusquedaGeneralSJCSService {

	private Logger LOGGER = Logger.getLogger(BusquedaGeneralSJCSServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;

	@Override
	public ColegiadosSJCSDTO searchColegiadosSJCS(HttpServletRequest request, ColegiadosSJCSItem colegiadosSJCSItem) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ColegiadosSJCSDTO colegiadosSJCSDTO = new ColegiadosSJCSDTO();
		List<ColegiadosSJCSItem> colegiadosSJCSItems = null;

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchColegiadosSJCS() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchColegiadosSJCS() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				LOGGER.info(
						"searchColegiadosSJCS() / cenColegiadoExtendsMapper.selectTipoSolicitud() -> Entrada a cenTiposolicitudSqlExtendsMapper para obtener los colegiados");

				colegiadosSJCSItems = cenColegiadoExtendsMapper.busquedaColegiadosSJCS(idInstitucion.toString(),
						colegiadosSJCSItem);

				LOGGER.info(
						"searchColegiadosSJCS() / cenColegiadoExtendsMapper.selectTipoSolicitud() -> Salida a cenTiposolicitudSqlExtendsMapper para obtener los colegiados");

				if (colegiadosSJCSItems != null) {
					colegiadosSJCSDTO.setColegiadosSJCSItem(colegiadosSJCSItems);
				}
			}

		}
		LOGGER.info("searchColegiadosSJCS() -> Salida del servicio para obtener los colegiados");
		return colegiadosSJCSDTO;

	}

}