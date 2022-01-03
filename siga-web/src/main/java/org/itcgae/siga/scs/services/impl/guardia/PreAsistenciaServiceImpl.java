/**
 * 
 */
package org.itcgae.siga.scs.services.impl.guardia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.PreAsistenciaDTO;
import org.itcgae.siga.DTOs.scs.PreAsistenciaItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoExample;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.ScsSolicitudAceptada;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsSolicitudAceptadaExtendsMapper;
import org.itcgae.siga.scs.services.guardia.PreAsistenciaService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pjarana
 *
 */
@Service
public class PreAsistenciaServiceImpl implements PreAsistenciaService {
	
	private Logger LOGGER = Logger.getLogger(PreAsistenciaServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;
	
	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;
	
	@Autowired
	private ScsSolicitudAceptadaExtendsMapper scsSolicitudAceptadaExtendsMapper;
	
	@Override
	public PreAsistenciaDTO searchPreasistencias(HttpServletRequest request, PreAsistenciaItem filtro) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		PreAsistenciaDTO preAsistenciaDTO = new PreAsistenciaDTO();
		Error error = new Error();
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		List<PreAsistenciaItem> preasistencias = new ArrayList<PreAsistenciaItem>();
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
	
				LOGGER.info(
						"searchPreasistencias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	
				LOGGER.info(
						"searchPreasistencias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				if (usuarios != null && usuarios.size() > 0) {
					
					
					GenParametrosExample genParametrosExample = new GenParametrosExample();

					genParametrosExample.createCriteria().andModuloEqualTo("SCS")
							.andParametroEqualTo("TAM_MAX_CONSULTA_JG")
							.andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));

					genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

					LOGGER.info(
							"PreAsistenciaServiceImpl.searchPreasistencias() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

					tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);

					LOGGER.info(
							"PreAsistenciaServiceImpl.searchPreasistencias() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

					if (tamMax != null) {
						tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
					} else {
						tamMaximo = null;
					}
					
					preasistencias = scsSolicitudAceptadaExtendsMapper.searchPreAsistencias(filtro, tamMaximo, idInstitucion);
					
					if (preasistencias != null && !preasistencias.isEmpty()) {

						if (tamMaximo != null && preasistencias.size() > tamMaximo) {
							error.setCode(200);
							error.setDescription("La consulta devuelve más de " + tamMaximo
									+ " resultados, pero se muestran sólo los " + tamMaximo
									+ " más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
							preAsistenciaDTO.setError(error);
							preasistencias.remove(preasistencias.size() - 1);
						}

						preAsistenciaDTO.setPreasistenciaItems(preasistencias);

						LOGGER.info(
								"PreAsistenciaServiceImpl.searchPreasistencias() -> Salida de la búsqueda de saltos y compensaciones");

					}
					
					
				}
			}
		}catch(Exception e) {
			LOGGER.error("searchPreasistencias() / ERROR: "+ e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al buscar las preasistencias: " + e);
			error.description("Error al buscar las preasistencias: " + e);
			preAsistenciaDTO.setError(error);
		}
		return preAsistenciaDTO;
	}
	
	private String getIdPersona(String nColegiado, Short idInstitucion) {

		String idPersona = "";
		
		CenColegiadoExample colegiadoExample = new CenColegiadoExample();
		colegiadoExample.createCriteria().andNcolegiadoEqualTo(nColegiado).andIdinstitucionEqualTo(idInstitucion);
		
		List<CenColegiado> colegiados = cenColegiadoExtendsMapper.selectByExample(colegiadoExample);
		
		if(colegiados != null
				&& !colegiados.isEmpty()) {
			
			idPersona = String.valueOf(colegiados.get(0).getIdpersona());
			
		}
		
		return idPersona;
	}

	@Override
	public UpdateResponseDTO denegarPreasistencias(HttpServletRequest request, List<PreAsistenciaItem> preasistencias) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponse = new UpdateResponseDTO();
		Error error = new Error();
		int recordsUpdated = 0;
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
	
				LOGGER.info(
						"denegarPreasistencias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	
				LOGGER.info(
						"denegarPreasistencias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				if (usuarios != null && usuarios.size() > 0
						&& preasistencias != null && !preasistencias.isEmpty()) {
					
					for(int i = 0; i < preasistencias.size(); i++) {
						
						PreAsistenciaItem preasistencia = preasistencias.get(i);
						
						ScsSolicitudAceptada scsSolicitudAceptada = new ScsSolicitudAceptada();
						scsSolicitudAceptada.setIdsolicitud(Integer.valueOf(preasistencia.getIdSolicitud()));
						scsSolicitudAceptada.setEstado(Short.valueOf("2")); //Estado denegada
						scsSolicitudAceptada.setUsumodificacion(usuarios.get(0).getIdusuario());
						scsSolicitudAceptada.setFechamodificacion(new Date());
						
						recordsUpdated += scsSolicitudAceptadaExtendsMapper.updateByPrimaryKeySelective(scsSolicitudAceptada);
					}
					
					if(recordsUpdated <= 0) {
						error.setCode(200);
						error.setMessage("No se actualizo ningun registro");
						error.description("No se actualizo ningun registro");
						updateResponse.setError(error);
					}
					
				}
			}
		}catch(Exception e) {
			LOGGER.error("denegarPreasistencias() / ERROR: "+ e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al denegar las preasistencias: " + e);
			error.description("Error al denegar las preasistencias: " + e);
			updateResponse.setError(error);
		}
		return updateResponse;
	
	}
	
	@Override
	public UpdateResponseDTO activarPreAsistenciasDenegadas(HttpServletRequest request, List<PreAsistenciaItem> preasistencias) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		UpdateResponseDTO updateResponse = new UpdateResponseDTO();
		Error error = new Error();
		int recordsUpdated = 0;
		try {
			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
	
				LOGGER.info(
						"denegarPreasistencias() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	
				LOGGER.info(
						"denegarPreasistencias() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
	
				if (usuarios != null && usuarios.size() > 0
						&& preasistencias != null && !preasistencias.isEmpty()) {
					
					for(int i = 0; i < preasistencias.size(); i++) {
						
						PreAsistenciaItem preasistencia = preasistencias.get(i);
						
						ScsSolicitudAceptada scsSolicitudAceptada = new ScsSolicitudAceptada();
						scsSolicitudAceptada.setIdsolicitud(Integer.valueOf(preasistencia.getIdSolicitud()));
						scsSolicitudAceptada.setEstado(Short.valueOf("0")); //Estado pendiente
						scsSolicitudAceptada.setUsumodificacion(usuarios.get(0).getIdusuario());
						scsSolicitudAceptada.setFechamodificacion(new Date());
						
						recordsUpdated += scsSolicitudAceptadaExtendsMapper.updateByPrimaryKeySelective(scsSolicitudAceptada);
					}
					
					if(recordsUpdated <= 0) {
						error.setCode(200);
						error.setMessage("No se actualizo ningun registro");
						error.description("No se actualizo ningun registro");
						updateResponse.setError(error);
					}
					
				}
			}
		}catch(Exception e) {
			LOGGER.error("denegarPreasistencias() / ERROR: "+ e.getMessage(), e);
			error.setCode(500);
			error.setMessage("Error al denegar las preasistencias: " + e);
			error.description("Error al denegar las preasistencias: " + e);
			updateResponse.setError(error);
		}
		return updateResponse;
	
	}

}
