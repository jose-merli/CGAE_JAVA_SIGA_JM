package org.itcgae.siga.cen.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.EtiquetaRetencionesDTO;
import org.itcgae.siga.DTOs.cen.MaestroRetencionDTO;
import org.itcgae.siga.DTOs.cen.MaestroRetencionItem;
import org.itcgae.siga.DTOs.cen.PersonaSearchDTO;
import org.itcgae.siga.DTOs.cen.RetencionesDTO;
import org.itcgae.siga.DTOs.cen.RetencionesItem;
import org.itcgae.siga.cen.services.ITarjetaDatosRetencionesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteKey;
import org.itcgae.siga.db.entities.GenRecursosCatalogos;
import org.itcgae.siga.db.entities.GenRecursosCatalogosExample;
import org.itcgae.siga.db.entities.ScsMaestroretenciones;
import org.itcgae.siga.db.entities.ScsMaestroretencionesExample;
import org.itcgae.siga.db.entities.ScsRetencionesirpf;
import org.itcgae.siga.db.entities.ScsRetencionesirpfExample;
import org.itcgae.siga.db.mappers.CenClienteMapper;
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

	private Logger LOGGER = Logger.getLogger(TarjetaDatosRetencionesServiceImpl.class);

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
	@Autowired
	private CenClienteMapper cenClienteMapper;

	@Override
	public MaestroRetencionDTO getRetentionTypes(HttpServletRequest request) {
		LOGGER.info("getRetentionTypes() -> Entrada al servicio para buscar los tipos de retenciones");
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
		LOGGER.info(
				"getRetentionTypes() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		LOGGER.info(
				"getRetentionTypes() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

		if (null != usuarios && usuarios.size() > 0) {
			usuario = usuarios.get(0);

			ScsMaestroretencionesExample exampleMaestro = new ScsMaestroretencionesExample();
			exampleMaestro.setDistinct(true);
			LOGGER.info(
					"getRetentionTypes() / scsMaestroretencionesMapper.selectByExample() -> Entrada a scsMaestroretencionesMapper para obtener las retenciones");
			List<ScsMaestroretenciones> retencionesLista = scsMaestroretencionesMapper.selectByExample(exampleMaestro);
			LOGGER.info(
					"getRetentionTypes() / scsMaestroretencionesMapper.selectByExample() -> Salida de scsMaestroretencionesMapper para obtener las retenciones");

			if (null != retencionesLista && !retencionesLista.isEmpty()) {
				for (ScsMaestroretenciones scsMaestroretenciones : retencionesLista) {
					GenRecursosCatalogosExample exampleRecursos = new GenRecursosCatalogosExample();
					exampleRecursos.createCriteria().andIdrecursoEqualTo(scsMaestroretenciones.getDescripcion())
							.andIdlenguajeEqualTo(usuario.getIdlenguaje());
					
					LOGGER.info(
							"getRetentionTypes() / genRecursosCatalogosMapper.selectByExample() -> Entrada a genRecursosCatalogosMapper para obtener obtener la descripción de las retenciones");
					List<GenRecursosCatalogos> recursos = genRecursosCatalogosMapper.selectByExample(exampleRecursos);
					LOGGER.info(
							"getRetentionTypes() / genRecursosCatalogosMapper.selectByExample() -> Salida de genRecursosCatalogosMapper para obtener obtener la descripción de las retenciones");

					if (null != recursos && !recursos.isEmpty()) {
						MaestroRetencionItem maestroRetencionItem = new MaestroRetencionItem();
						GenRecursosCatalogos recurso = recursos.get(0);
						maestroRetencionItem.setLabel(recurso.getDescripcion());
						maestroRetencionItem.setValue(String.valueOf(scsMaestroretenciones.getIdretencion()));
						maestroRetencionItem
								.setPorcentajeRetencion(String.valueOf(scsMaestroretenciones.getRetencion()));
						retencionesItemList.add(maestroRetencionItem);
					}
				}
				MaestroRetencionItem maestroRetencionItem = new MaestroRetencionItem();
				maestroRetencionItem.setLabel("");
				maestroRetencionItem.setPorcentajeRetencion("");
				maestroRetencionItem.setValue("");
				retencionesItemList.add(0, maestroRetencionItem);
				
				 Collections.sort(retencionesItemList, new Comparator<Object>() {
			            @Override
			            public int compare(Object item1, Object item2) {
			                //use instanceof to verify the references are indeed of the type in question
			                return ((MaestroRetencionItem)item1).getLabel()
			                        .compareTo(((MaestroRetencionItem)item2).getLabel());
			            }
			        }); 
				response.setMaestroRetencionItem(retencionesItemList);
				
				
			} else {
				response.getError().setDescription("No se han encontrado datos en ScsMaestroRetenciones.");
			}

		}
		LOGGER.info("getRetentionTypes() -> Salida del servicio para buscar los tipos de retenciones");

		return response;
	}

	@Override
	public RetencionesDTO getRetenciones(int numPagina, PersonaSearchDTO personaSearchDTO, HttpServletRequest request) {
		LOGGER.info("getRetenciones() -> Entrada al servicio para buscar las retenciones de una persona jurídica");
		RetencionesDTO retencionesDTO = new RetencionesDTO();
		org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
		retencionesDTO.setError(error);

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		// Obtenemos el usuario que modifica
		AdmUsuarios usuario = new AdmUsuarios();
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		LOGGER.info(
				"getRetenciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		LOGGER.info(
				"getRetenciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
		if (null != usuarios && usuarios.size() > 0) {
			usuario = usuarios.get(0);

			LOGGER.info(
					"getRetenciones() / cenNocolegiadoExtendsMapper.selectRetenciones() -> Entrada a cenNocolegiadoExtendsMapper para obtener las retenciones de la persona jurídica indicada");
			List<RetencionesItem> listaRetenciones = cenNocolegiadoExtendsMapper.selectRetenciones(personaSearchDTO,
					usuario.getIdlenguaje(), String.valueOf(idInstitucion));
			LOGGER.info(
					"getRetenciones() / cenNocolegiadoExtendsMapper.selectRetenciones() -> Salida de cenNocolegiadoExtendsMapper para obtener las retenciones de la persona jurídica indicada");

			if (null != listaRetenciones && !listaRetenciones.isEmpty()) {
				retencionesDTO.setRetencionesItemList(listaRetenciones);
			} else {
				retencionesDTO.getError().setDescription(
						"No se han encontrado retenciones para el idPersona: " + personaSearchDTO.getIdPersona());
			}
		}

		LOGGER.info("getRetenciones() -> Salida del servicio para buscar las retenciones de una persona jurídica");

		return retencionesDTO;
	}

	@Override
	public UpdateResponseDTO updateRetenciones(List<EtiquetaRetencionesDTO> etiquetaRetencionesDTO, String idPersona,
			HttpServletRequest request) {
		LOGGER.info(
				"updateRetenciones() -> Entrada al servicio para actualizar las retenciones asociadas a una persona jurídica");
		UpdateResponseDTO response = new UpdateResponseDTO();
		org.itcgae.siga.DTOs.gen.Error error = new org.itcgae.siga.DTOs.gen.Error();
		response.setError(error);
		boolean cerrojoBorrado = true;
		boolean cerrojoInsercion = true;

		int responseInsercion = 0;

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		// Obtenemos el usuario que modifica
		AdmUsuarios usuario = new AdmUsuarios();
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		LOGGER.info(
				"updateRetenciones() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		LOGGER.info(
				"updateRetenciones() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
		if (null != usuarios && usuarios.size() > 0) {
			usuario = usuarios.get(0);
			// 1. Borrar las retenciones antiguas
			ScsRetencionesirpfExample exampleRetenciones = new ScsRetencionesirpfExample();

			exampleRetenciones.createCriteria().andIdinstitucionEqualTo(Short.valueOf(idInstitucion))
					.andIdpersonaEqualTo(Long.valueOf(idPersona));
			LOGGER.info(
					"updateRetenciones() / scsRetencionesirpfMapper.deleteByExample() -> Entrada a scsRetencionesirpfMapper para obtener borrar retenciones asociadas a personas jurídicas");
			scsRetencionesirpfMapper.deleteByExample(exampleRetenciones);
			LOGGER.info(
					"updateRetenciones() / scsRetencionesirpfMapper.deleteByExample() -> Salida de scsRetencionesirpfMapper para obtener borrar retenciones asociadas a personas jurídicas");

			if (null != etiquetaRetencionesDTO && !etiquetaRetencionesDTO.isEmpty()) {
				for (EtiquetaRetencionesDTO etiquetaUpdateDTO : etiquetaRetencionesDTO) {

					if (cerrojoBorrado) {
						response.setStatus(SigaConstants.OK);
						cerrojoBorrado = false;
					}
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					ScsRetencionesirpf retenciones = new ScsRetencionesirpf();
					retenciones.setIdinstitucion(idInstitucion);
					retenciones.setUsumodificacion(usuario.getUsumodificacion());
					retenciones.setFechamodificacion(new Date());
					try {
						if (!UtilidadesString.esCadenaVacia(etiquetaUpdateDTO.getFechaFin())) {
							retenciones.setFechafin(format.parse(etiquetaUpdateDTO.getFechaFin()));
						}
						if (!UtilidadesString.esCadenaVacia(etiquetaUpdateDTO.getFechaInicio())) {
							retenciones.setFechainicio(format.parse(etiquetaUpdateDTO.getFechaInicio()));
						}
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					retenciones.setIdpersona(Long.valueOf(etiquetaUpdateDTO.getIdPersona()));
					retenciones.setIdretencion(Integer.valueOf(etiquetaUpdateDTO.getIdRetencion()));
					try {
						LOGGER.info(
								"updateRetenciones() / scsRetencionesirpfMapper.insertSelective() -> Entrada a scsRetencionesirpfMapper para añadir retenciones asociadas a personas jurídicas");
						responseInsercion = scsRetencionesirpfMapper.insertSelective(retenciones);
						LOGGER.info(
								"updateRetenciones() / scsRetencionesirpfMapper.insertSelective() -> Salida de scsRetencionesirpfMapper para añadir retenciones asociadas a personas jurídicas");

						if (responseInsercion == 1 && cerrojoInsercion) {
							response.setStatus(SigaConstants.OK);
							cerrojoInsercion = false;
							
							CenClienteKey keyCliente = new CenClienteKey();
							keyCliente.setIdinstitucion(idInstitucion);
							keyCliente.setIdpersona(Long.valueOf(etiquetaUpdateDTO.getIdPersona()));
							CenCliente cliente = cenClienteMapper.selectByPrimaryKey(keyCliente);
							cliente.setFechaactualizacion(new Date());
							cenClienteMapper.updateByPrimaryKey(cliente);
						}

						if (responseInsercion == 0) {
							response.setStatus(SigaConstants.KO);
						}

					} catch (Exception e) {
						response.setStatus(SigaConstants.KO);
						response.getError().setDescription("La actualización de ScsRetencionesIrpf para el idPersona: "
								+ etiquetaUpdateDTO.getIdPersona() + " no se ha realizado correctamente.");
					}

					response.setStatus(SigaConstants.OK);
				}
			}
		}

		LOGGER.info(
				"updateRetenciones() -> Salida del servicio para actualizar las retenciones asociadas a una persona jurídica");
		return response;
	}

}
