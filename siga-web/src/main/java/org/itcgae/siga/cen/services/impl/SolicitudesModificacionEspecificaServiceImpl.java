package org.itcgae.siga.cen.services.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.SolicitModifDatosBasicosDTO;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.cen.services.ISolicitudesModificacionEspecificaService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenSolicitmodifdatosbasicos;
import org.itcgae.siga.db.entities.CenSolicitmodifdatosbasicosExample;
import org.itcgae.siga.db.mappers.CenSolicitmodifdatosbasicosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolicitudesModificacionEspecificaServiceImpl implements ISolicitudesModificacionEspecificaService{

	private Logger LOGGER = Logger.getLogger(SolicitudesModificacionEspecificaServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenSolicitmodifdatosbasicosMapper cenSolicitmodifdatosbasicosMapper;
	
	@Override
	public SolicitModifDatosBasicosDTO searchModificacionDatosBasicos(int numPagina,
			SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, HttpServletRequest request) {
		LOGGER.info(
				"searchModificacionDatosBasicos() -> Entrada al servicio para recuperar las solicitudes de modificación de datos básicos");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		SolicitModifDatosBasicosDTO solicitModifDatosBasicosDTO = new SolicitModifDatosBasicosDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchModificacionDatosBasicos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchModificacionDatosBasicos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"searchModificacionDatosBasicos() / cenSolicitmodifdatosbasicosMapper.selectByExample() -> Entrada a cenSolicitmodifdatosbasicosMapper para obtener los datos básicos de modificación");				
				
				// NO ME VALE HAY QUE USAR EL EXTENDIDO
				
				CenSolicitmodifdatosbasicosExample cenSolicitmodifdatosbasicosExample = new CenSolicitmodifdatosbasicosExample();
				cenSolicitmodifdatosbasicosExample.createCriteria().andIdinstitucionEqualTo(Short.valueOf(idInstitucion)).andIdlenguajeEqualTo(usuario.getIdlenguaje());
				List<CenSolicitmodifdatosbasicos> solicitModifDatosBasicosItems = cenSolicitmodifdatosbasicosMapper.selectByExample(cenSolicitmodifdatosbasicosExample);
				solicitModifDatosBasicosDTO.setSolModificacionItems(solicitModifDatosBasicosItems);

			}
		}

		LOGGER.info(
				"searchModificacionDatosBasicos() -> Salida del servicio para recuperar las solicitudes de modificación de datos básicos");

		return solicitModifDatosBasicosDTO;
	}
}
