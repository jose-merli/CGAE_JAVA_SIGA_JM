package org.itcgae.siga.cen.services.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.ComboColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ComboColegiadoItem;

import org.itcgae.siga.cen.services.IFichaColegialOtrasColegiacionesService;
import org.itcgae.siga.cen.services.IInstitucionesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmConfig;
import org.itcgae.siga.db.entities.AdmConfigExample;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenInstitucionExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.GenRecursos;
import org.itcgae.siga.db.entities.GenRecursosExample;
import org.itcgae.siga.db.mappers.AdmConfigMapper;
import org.itcgae.siga.db.mappers.CenInstitucionMapper;
import org.itcgae.siga.db.mappers.GenRecursosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.itcgae.siga.ws.client.ClientCENSO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegiados.info.redabogacia.ColegiacionType;
import com.colegiados.info.redabogacia.ColegiadoRequestDocument;
import com.colegiados.info.redabogacia.ColegiadoRequestDocument.ColegiadoRequest;
import com.colegiados.info.redabogacia.ColegiadoResponseDocument;
import com.colegiados.info.redabogacia.ColegiadoResponseDocument.ColegiadoResponse;
import com.colegiados.info.redabogacia.ColegiadoResponseDocument.ColegiadoResponse.Colegiado;
import com.colegiados.info.redabogacia.IdentificacionType;

@Service
public class FichaColegialOtrasColegiacionesServiceImpl implements IFichaColegialOtrasColegiacionesService {

	private Logger LOGGER = Logger.getLogger(FichaColegialOtrasColegiacionesServiceImpl.class);
	
	@Autowired
	private AdmConfigMapper admConfigMapper;

	@Autowired
	private CenInstitucionMapper cenInstitucionMapper;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private IInstitucionesService institucionesService;
	
	@Autowired
	private ClientCENSO clientCENSO;

	@Autowired
	private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;
	
	@Autowired
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;
	
	@Autowired
	private GenRecursosMapper genRecursosMapper;
	private static final String ESTADO_EJERCIENTE = "Ejerciente";
	private static final String ESTADO_NOEJERCIENTE = "No Ejerciente";
	
	@Override
	public ColegiadoDTO searchOtherCollegues(int numPagina, String nif,
			HttpServletRequest request) {
		ColegiadoDTO colegiadoDTO = new ColegiadoDTO();
			Colegiado colegiado = null;
			// Conseguimos información del usuario logeado
			String token = request.getHeader("Authorization");
			String dni = UserTokenUtils.getDniFromJWTToken(token);
			Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
			String idLenguaje = null;
			List<ColegiadoItem> colegiadoItems = new ArrayList<ColegiadoItem>();
		try {
			AdmConfigExample example = new AdmConfigExample();
			example.createCriteria().andClaveEqualTo("url.ws.censo");
			List<AdmConfig> config = admConfigMapper.selectByExample(example );
			
			if (null != config && config.size()>0) {
					//Busqueda por nif
					ColegiadoRequest colegiadoRequest = ColegiadoRequest.Factory.newInstance();
					String tipo = isNifNie(nif);
					// Rellenamos la peticion
					IdentificacionType identificacion = IdentificacionType.Factory.newInstance();
					if(tipo.equals("NIF")){
						identificacion.setNIF(nif);
					}else if(tipo.equals("NIE")){
						identificacion.setNIE(nif);
					}
					colegiadoRequest.setIdentificacion(identificacion);
					
					ColegiadoRequestDocument colegiadoRequestDocument = ColegiadoRequestDocument.Factory.newInstance();
					colegiadoRequestDocument.setColegiadoRequest(colegiadoRequest);
					ColegiadoResponseDocument colegiadoResponseDocument = null;
				
					colegiadoResponseDocument = clientCENSO.busquedaColegiadoConIdentificacion(colegiadoRequestDocument,config.get(0).getValor());
					ColegiadoResponse colegiadoResponse = colegiadoResponseDocument.getColegiadoResponse();
					colegiado = colegiadoResponse.getColegiado();
				
					
					if (null != colegiado) {
						AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
						exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
						LOGGER.info(
								"searchOtherCollegues() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
						List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
						LOGGER.info(
								"searchOtherCollegues() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");


						AdmUsuarios usuario = usuarios.get(0);
						idLenguaje = usuario.getIdlenguaje();
						if (null != colegiado.getColegiacionArray() && colegiado.getColegiacionArray().length>0) {
							for (ColegiacionType colegiadoColegiacion : colegiado.getColegiacionArray()) {
									
								ColegiadoItem colegiadoItem = new ColegiadoItem();
									if (null != colegiado.getDatosPersonales().getApellido1()) {
										colegiadoItem.setApellidos1(colegiado.getDatosPersonales().getApellido1());
									}else{
										colegiadoItem.setApellidos1("");
									}
									if (null != colegiado.getDatosPersonales().getApellido2()) {
										colegiadoItem.setApellidos2(colegiado.getDatosPersonales().getApellido2());
									}else{
										colegiadoItem.setApellidos2("");
									}
					
									colegiadoItem.setApellidos(colegiadoItem.getApellidos1().concat(colegiadoItem.getApellidos2()));
									colegiadoItem.setNif(colegiado.getDatosPersonales().getIdentificacion().getNIF());
									colegiadoItem.setNombre(colegiado.getDatosPersonales().getNombre());
									if (null != colegiadoColegiacion.getResidente()) {
										if (colegiadoColegiacion.getResidente().toString().equals("1")) {
											colegiadoItem.setResidenteInscrito("SI");
										}else{
											colegiadoItem.setResidenteInscrito("NO");
										}
										
									}
									colegiadoItem.numColegiado(colegiadoColegiacion.getNumColegiado());
									
									if (null != colegiadoColegiacion.getSituacion().getSituacionEjerProfesional().toString()) {
										GenRecursosExample recursosExample = new GenRecursosExample();
										switch ( colegiadoColegiacion.getSituacion().getSituacionEjerProfesional().toString()) {
										case SigaConstants.CENSO_WS_SITUACION_BAJACOLEGIO:
											recursosExample.createCriteria().andIdrecursoEqualTo("censo.ws.situacionejerciente.BAJA_COLEGIAL").andIdlenguajeEqualTo(idLenguaje);
											List<GenRecursos> recurso = genRecursosMapper.selectByExample(recursosExample );
											colegiadoItem.setEstadoColegial(recurso.get(0).getDescripcion());
											break;
										case SigaConstants.CENSO_WS_SITUACION_EJERCIENTE:
											
											recursosExample.createCriteria().andIdrecursoEqualTo("censo.ws.situacionejerciente.EJERCIENTE").andIdlenguajeEqualTo(idLenguaje);
											recurso = genRecursosMapper.selectByExample(recursosExample );
											colegiadoItem.setEstadoColegial(recurso.get(0).getDescripcion());
											break;
										case SigaConstants.CENSO_WS_SITUACION_INSCRITO:
											
											recursosExample.createCriteria().andIdrecursoEqualTo("censo.ws.situacionejerciente.INSCRITO").andIdlenguajeEqualTo(idLenguaje);
											recurso = genRecursosMapper.selectByExample(recursosExample );
											colegiadoItem.setEstadoColegial(recurso.get(0).getDescripcion());
											break;
										case SigaConstants.CENSO_WS_SITUACION_NOEJERCIENTE:
											
											recursosExample.createCriteria().andIdrecursoEqualTo("censo.ws.situacionejerciente.NO_EJERCIENTE").andIdlenguajeEqualTo(idLenguaje);
											recurso = genRecursosMapper.selectByExample(recursosExample );
											colegiadoItem.setEstadoColegial(recurso.get(0).getDescripcion());
											break;
										}
									}
									
									
//									colegiadoItem.setResidencia(colegiadoColegiacion.getResidente().toString());
									SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
									String fechaEstado = format1.format(colegiadoColegiacion.getSituacion().getFechaSituacion().getTime());     
									colegiadoItem.setFechaEstado(colegiadoColegiacion.getSituacion().getFechaSituacion().getTime());
									colegiadoItem.setFechaEstadoStr(fechaEstado);
									if (null != colegiado.getColegiacionArray()[0].getColegio()) {
										List<CenInstitucion> instituciones = institucionesService.getidInstitucionByCodExterno(colegiadoColegiacion.getColegio().getCodigoColegio());
										if (null != instituciones && instituciones.size()>0) {
											colegiadoItem.setInstitucion(instituciones.get(0).getNombre());
											colegiadoItem.idInstitucion(instituciones.get(0).getIdinstitucion().toString());
										}
									}
									if (null != colegiado.getLocalizacion()) {
										colegiadoItem.setDomicilio(colegiado.getLocalizacion().getDomicilio());
										
									}
									
									// Extraemos el idPersona
									CenPersonaExample cenPersonaExample = new CenPersonaExample();
									cenPersonaExample.createCriteria().andNifcifEqualTo(nif);
									List<CenPersona> cenPersona = cenPersonaExtendsMapper.selectByExample(cenPersonaExample);
									
									
									if(!cenPersona.isEmpty()) {
										colegiadoItem.setIdPersona(String.valueOf(cenPersona.get(0).getIdpersona()));
									}
									CenInstitucionExample institucionExample = new CenInstitucionExample();
									institucionExample.createCriteria().andCodigoextEqualTo(colegiadoColegiacion.getColegio().getCodigoColegio());
									List <CenInstitucion> inst = cenInstitucionMapper.selectByExample(institucionExample);
									if(!(inst.get(0).getIdinstitucion().equals(idInstitucion))) {
										colegiadoItems.add(colegiadoItem);
									}
							}
							List<ColegiadoItem> otrasColegiacionesEjercientes = new ArrayList<>();
							List<ColegiadoItem> otrasColegiacionesNoEjercientes = new ArrayList<>();
							List<ColegiadoItem> restoColegiaciones = new ArrayList<>();
							for(ColegiadoItem otraColegiacion: colegiadoItems) {
								if(ESTADO_EJERCIENTE.equals(otraColegiacion.getEstadoColegial())) {
									otrasColegiacionesEjercientes.add(otraColegiacion);
								}else if(ESTADO_NOEJERCIENTE.equals(otraColegiacion.getEstadoColegial())) {
									otrasColegiacionesNoEjercientes.add(otraColegiacion);
								}else {
									restoColegiaciones.add(otraColegiacion);
								}
							}
							colegiadoItems.clear();
							Collections.sort(otrasColegiacionesEjercientes);
							for(ColegiadoItem e: otrasColegiacionesEjercientes) {
								colegiadoItems.add(e);
							}
							Collections.sort(otrasColegiacionesNoEjercientes);
							for(ColegiadoItem e: otrasColegiacionesNoEjercientes) {
								colegiadoItems.add(e);
							}
							Collections.sort(restoColegiaciones);
							for(ColegiadoItem e: restoColegiaciones) {
								colegiadoItems.add(e);
							}
							
							colegiadoDTO.setColegiadoItem(colegiadoItems);
							
						}
					}
			}
		} catch (Exception e){
			LOGGER.error("Error en la llamada a busqueda de colegiados.", e);
		}
		
		return colegiadoDTO;
	}

	
//	@Override
//	public ColegiadoDTO searchOtherCollegues(int numPagina, String idPersona, HttpServletRequest request) {
//	LOGGER.info("searchOtherCollegues() -> Entrada al servicio para la búsqueda de un abogado en distintos colegios");
//		
//		List<ColegiadoItem> colegiadoItems = new ArrayList<ColegiadoItem>();
//		ColegiadoDTO colegiadoDTO = new ColegiadoDTO();
//		String idLenguaje = null;
//		
//		// Conseguimos información del usuario logeado
//		String token = request.getHeader("Authorization");
//		String dni = UserTokenUtils.getDniFromJWTToken(token);
//		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
//		
//		if (null != idInstitucion) {
//			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
//			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
//			LOGGER.info(
//					"searchOtherCollegues() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
//			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
//			LOGGER.info(
//					"searchOtherCollegues() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//			if (null != usuarios && usuarios.size() > 0) {
//				AdmUsuarios usuario = usuarios.get(0);
//				idLenguaje = usuario.getIdlenguaje();
//				
//			LOGGER.info(
//						"searchOtherCollegues() / cenComponentesExtendsMapper.searchSocieties() -> Entrada a cenComponentesExtendsMapper para obtener sociedades");
//			    colegiadoItems = cenColegiadoExtendsMapper.searchOtherCollegues(idPersona, idLenguaje);
//				LOGGER.info("searchOtherCollegues() / cenComponentesExtendsMapper.searchSocieties() -> Salida de cenComponentesExtendsMapper para obtener sociedades");
//				colegiadoDTO.setColegiadoItem(colegiadoItems);
//			} 
//			else {
//				LOGGER.warn("searchOtherCollegues() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
//			}
//		} 
//		else {
//			LOGGER.warn("searchOtherCollegues() -> idInstitucion del token nula");
//		}
//		
//		LOGGER.info("searchOtherCollegues() -> Salida del servicio para la búsqueda de un abogado en distintos colegios");
//		return colegiadoDTO;
//	}


	@Override
	public ComboColegiadoDTO getLabelColegios(String idPersona, HttpServletRequest request) {
		LOGGER.info("getLabelColegios() -> Entrada al servicio para la búsqueda de todos los colegios");
		
		ComboColegiadoDTO combo = new ComboColegiadoDTO();
		List<ComboColegiadoItem> comboItems = new ArrayList<ComboColegiadoItem>();

		LOGGER.info(
				"getLabelColegios() / cenInstitucionExtendsMapper.getComboInstituciones() -> Entrada a cenInstitucionExtendsMapper para busqueda de personas no colegiadas por filtro");
		comboItems = cenColegiadoExtendsMapper.getLabelColegios(idPersona);
		LOGGER.info(
				"getLabelColegios() / cenInstitucionExtendsMapper.getComboInstituciones() -> Salida de cenInstitucionExtendsMapper para busqueda de personas no colegiadas por filtro");
	
//		if (!comboItems.equals(null) && comboItems.size() > 0) {
//			// añade elemento vacio al principio para el dropdown de parte front
//			ComboColegiadoItem comboItem = new ComboColegiadoItem();
//			comboItem.setLabel("");
//			comboItem.setValue("");
//			comboItems.add(0, comboItem);
//			combo.setCombooItems(comboItems);
//		}
//		
		combo.setCombooItems(comboItems);
		LOGGER.info("getLabelColegios() -> Salida del servicio para la búsqueda de todos los colegios");
		return combo;
	}
	
	public static String isNifNie(String nif) {
		String tipo;
		if(nif.length() != 9){
			return null;
		}else{
			// si es NIE, eliminar la x,y,z inicial para tratarlo como nif
			if (nif.toUpperCase().startsWith("X") || nif.toUpperCase().startsWith("Y") || nif.toUpperCase().startsWith("Z")){
				nif = nif.substring(1);
				tipo = "NIE";
			}else{
				tipo = "NIF";
			}
		}
	
		Pattern nifPattern = Pattern.compile("(\\d{1,8})([TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke])");
		Matcher m = nifPattern.matcher(nif);
		if (m.matches()) {
			String letra = m.group(2);
			// Extraer letra del NIF
			String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
			int dni = Integer.parseInt(m.group(1));
			dni = dni % 23;
			String reference = letras.substring(dni, dni + 1);

			if (reference.equalsIgnoreCase(letra)) {
				return tipo;
			} else {
				return tipo;
			}
		} else
			return null;
	}

}

