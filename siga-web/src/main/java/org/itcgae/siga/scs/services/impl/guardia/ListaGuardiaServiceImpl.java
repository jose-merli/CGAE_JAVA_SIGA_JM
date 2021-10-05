package org.itcgae.siga.scs.services.impl.guardia;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.*;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.ScsActuacionasistencia;
import org.itcgae.siga.db.entities.ScsActuacionasistenciaKey;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.scs.services.guardia.ListaGuardiaService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class ListaGuardiaServiceImpl implements ListaGuardiaService {

	private final Logger LOGGER = Logger.getLogger(ActuacionAsistenciaServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Override
	public ListaGuardiasDTO searchListaGuardias(HttpServletRequest request, ListaGuardiasItem filtro) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		ListaGuardiasDTO listaGuardiasDTO = new ListaGuardiasDTO();
		List<ListaGuardiasItem> listaGuardiasItems = new ArrayList<>();
		Error error = new Error();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

				LOGGER.info(
						"searchListaGuardias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

				LOGGER.info(
						"searchListaGuardias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if(usuarios != null
						&& !usuarios.isEmpty()) {



				}
			}
		}catch(Exception e) {
			LOGGER.error("searchListaGuardias() / ERROR: "+ e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al buscar las listas de guardias: " + e);
			error.description("Error al buscar las listas de guardias: " + e);
			listaGuardiasDTO.setError(error);
		}
		return listaGuardiasDTO;
	}
}
