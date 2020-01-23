package org.itcgae.siga.scs.services.impl.oficio;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.BajasTemporalesDTO;
import org.itcgae.siga.DTOs.scs.BajasTemporalesItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.mappers.ScsGuardiasturnoMapper;
import org.itcgae.siga.db.mappers.ScsInscripcionguardiaMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsBajasTemporalesExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsGuardiasturnoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsInscripcionesTurnoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsOrdenacionColasExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsTurnosExtendsMapper;
import org.itcgae.siga.scs.services.impl.maestros.FichaPartidasJudicialesServiceImpl;
import org.itcgae.siga.scs.services.oficio.IGestionBajasTemporalesService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionBajasTemporalesServiceImpl implements IGestionBajasTemporalesService {

	private Logger LOGGER = Logger.getLogger(FichaPartidasJudicialesServiceImpl.class);

	@Autowired
	private ScsInscripcionesTurnoExtendsMapper scsInscripcionturnoExtendsMapper;
	
	@Autowired
	private ScsBajasTemporalesExtendsMapper scsBajasTemporalesExtendsMapper;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;


	@Override
	public BajasTemporalesDTO busquedaBajasTemporales(BajasTemporalesItem bajasTemporalesItem, HttpServletRequest request) {
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		BajasTemporalesDTO bajasTemporalesDTO = new BajasTemporalesDTO();
		List<BajasTemporalesItem> bajasTemporalesItems = null;
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"searchCostesFijos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"searchCostesFijos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				
				LOGGER.info(
						"searchCostesFijos() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Entrada a scsSubzonaExtendsMapper para obtener las subzonas");
				String fechahasta = "";
				String fechadesde = "";
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				LOGGER.info(
						"searchCostesFijos() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Entrada a scsSubzonaExtendsMapper para obtener las subzonas");
				if (bajasTemporalesItem.getFechadesde() != null) {
					Date fechad = bajasTemporalesItem.getFechadesde();

					fechadesde = dateFormat.format(fechad);
					if (bajasTemporalesItem.getFechahasta() != null) {
						Date fechah = bajasTemporalesItem.getFechahasta();
						fechahasta = dateFormat.format(fechah);

					}
				}
				

				bajasTemporalesItems = scsBajasTemporalesExtendsMapper.busquedaBajasTemporales(bajasTemporalesItem, idInstitucion, fechadesde, fechahasta);

				LOGGER.info(
						"searchCostesFijos() / scsSubzonaExtendsMapper.selectTipoSolicitud() -> Salida a scsSubzonaExtendsMapper para obtener las subzonas");

				if (bajasTemporalesItems != null) {
					bajasTemporalesDTO.setBajasTemporalesItems(bajasTemporalesItems);
				}
			}

		}
		LOGGER.info("searchCostesFijos() -> Salida del servicio para obtener los costes fijos");
		return bajasTemporalesDTO;
	}
	
}
