package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.EtiquetaRetencionesDTO;
import org.itcgae.siga.DTOs.cen.MaestroRetencionDTO;
import org.itcgae.siga.DTOs.cen.MaestroRetencionItem;
import org.itcgae.siga.DTOs.cen.PersonaSearchDTO;
import org.itcgae.siga.DTOs.cen.RetencionesDTO;
import org.itcgae.siga.DTOs.cen.RetencionesItem;
import org.itcgae.siga.cen.services.ITarjetaDatosRetencionesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosExample;
import org.itcgae.siga.db.entities.ScsMaestroretenciones;
import org.itcgae.siga.db.entities.ScsMaestroretencionesExample;
import org.itcgae.siga.db.entities.ScsRetencionesirpf;
import org.itcgae.siga.db.entities.ScsRetencionesirpfExample;
import org.itcgae.siga.db.mappers.GenRecursosCatalogosMapper;
import org.itcgae.siga.db.mappers.ScsMaestroretencionesMapper;
import org.itcgae.siga.db.mappers.ScsRetencionesirpfMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenNocolegiadoExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class TarjetaDatosRetencionesServiceImpl implements ITarjetaDatosRetencionesService {

	@Autowired
	private ScsMaestroretencionesMapper scsMaestroretencionesMapper;
	@Autowired
	private GenRecursosCatalogosMapper genRecursosCatalogosMapper;
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	@Autowired
	private CenNocolegiadoExtendsMapper cenNocolegiadoExtendsMapper;
	@Autowired
	private ScsRetencionesirpfMapper scsRetencionesirpfMapper;

	@Override
	public MaestroRetencionDTO getRetentionTypes(HttpServletRequest request) {
		MaestroRetencionDTO response = new MaestroRetencionDTO();
		List<MaestroRetencionItem> retencionesItemList = new ArrayList<MaestroRetencionItem>();
		org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
		response.setError(error);

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		// Obtenemos el usuario que modifica
		AdmUsuarios usuario = new AdmUsuarios();
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		if (null != usuarios && usuarios.size() > 0) {
			usuario = usuarios.get(0);
		}

		ScsMaestroretencionesExample exampleMaestro = new ScsMaestroretencionesExample();
		exampleMaestro.setDistinct(true);
		List<ScsMaestroretenciones> retencionesLista = scsMaestroretencionesMapper.selectByExample(exampleMaestro);

		if (null != retencionesLista && !retencionesLista.isEmpty()) {
			GenRecursosCatalogosExample exampleRecursos = new GenRecursosCatalogosExample();
			for (ScsMaestroretenciones scsMaestroretenciones : retencionesLista) {
				exampleRecursos.createCriteria().andIdrecursoEqualTo(scsMaestroretenciones.getDescripcion())
						.andIdlenguajeEqualTo(usuario.getIdlenguaje());
				List<GenRecursosCatalogos> recursos = genRecursosCatalogosMapper.selectByExample(exampleRecursos);
				if (null != recursos && !recursos.isEmpty()) {
					MaestroRetencionItem maestroRetencionItem = new MaestroRetencionItem();
					GenRecursosCatalogos recurso = recursos.get(0);
					maestroRetencionItem.setLabel(recurso.getDescripcion());
					maestroRetencionItem.setValue(String.valueOf(scsMaestroretenciones.getIdretencion()));
					maestroRetencionItem.setPorcentajeRetencion(String.valueOf(scsMaestroretenciones.getRetencion()));
					retencionesItemList.add(maestroRetencionItem);
				}
			}
			response.setMaestroRetencionItem(retencionesItemList);
		}else {
			response.getError().setDescription("No se han encontrado datos en ScsMaestroRetenciones.");
		}

		return response;
	}

	@Override
	public RetencionesDTO getRetenciones(PersonaSearchDTO personaSearchDTO, HttpServletRequest request) {
		RetencionesDTO response = new RetencionesDTO();
		org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
		response.setError(error);
		
		// Conseguimos información del usuario logeado
				String token = request.getHeader("Authorization");
				Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
				String dni = UserTokenUtils.getDniFromJWTToken(token);
				// Obtenemos el usuario que modifica
				AdmUsuarios usuario = new AdmUsuarios();
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				if (null != usuarios && usuarios.size() > 0) {
					usuario = usuarios.get(0);
				}
		
		
		List<RetencionesItem> listaRetenciones = cenNocolegiadoExtendsMapper.selectRetenciones(personaSearchDTO, usuario.getIdlenguaje(), String.valueOf(idInstitucion));
		if(null != listaRetenciones && !listaRetenciones.isEmpty()) {
			response.setRetencionesItemList(listaRetenciones);
		}else {
			response.getError().setDescription("No se han encontrado retenciones para el idPersona: " + personaSearchDTO.getIdPersona());
		}
		
		return response;
	}

	@Override
	public UpdateResponseDTO updateRetenciones(List<EtiquetaRetencionesDTO> etiquetaRetencionesDTO, HttpServletRequest request) {
		UpdateResponseDTO response = new UpdateResponseDTO();
		org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
		response.setError(error);
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		// Obtenemos el usuario que modifica
		AdmUsuarios usuario = new AdmUsuarios();
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		if (null != usuarios && usuarios.size() > 0) {
			usuario = usuarios.get(0);
		}
		
		if(null != etiquetaRetencionesDTO && !etiquetaRetencionesDTO.isEmpty()) {
			
			
			for (EtiquetaRetencionesDTO etiquetaUpdateDTO : etiquetaRetencionesDTO) {
				ScsRetencionesirpfExample exampleRetenciones = new ScsRetencionesirpfExample();
				exampleRetenciones.createCriteria().andIdinstitucionEqualTo(Short.valueOf(etiquetaUpdateDTO.getIdInstitucion())).andIdpersonaEqualTo(Long.valueOf(etiquetaUpdateDTO.getIdPersona()));
				scsRetencionesirpfMapper.deleteByExample(exampleRetenciones);
				
				ScsRetencionesirpf retenciones = new ScsRetencionesirpf();
				retenciones.setIdinstitucion(idInstitucion);
				retenciones.setUsumodificacion(usuario.getUsumodificacion());
				retenciones.setFechamodificacion(new Date());
				retenciones.setIdretencion(Integer.getInteger(etiquetaUpdateDTO.getIdRetencion()));
				retenciones.setFechafin(etiquetaUpdateDTO.getFechaFin());
				retenciones.setFechainicio(etiquetaUpdateDTO.getFechaInicio());
				retenciones.setIdpersona(Long.valueOf(etiquetaUpdateDTO.getIdPersona()));
				retenciones.setIdretencion(Integer.valueOf(etiquetaUpdateDTO.getIdRetencion()));
				try {
					scsRetencionesirpfMapper.updateByPrimaryKey(retenciones);
				}catch(Exception e) {
					response.setStatus(SigaConstants.KO);
					response.getError().setDescription("La actualización de ScsRetencionesIrpf para el idPersona: " + etiquetaUpdateDTO.getIdPersona() 
					+ " no se ha realizado correctamente.");
				}
				
			}
			response.setStatus(SigaConstants.OK);
		}else {
			response.getError().setDescription("No se han enviado datos para la actualización.");
		}
		return response;
	}
	
	

}
