package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.BusquedaPerFisicaDTO;
import org.itcgae.siga.DTOs.cen.BusquedaPerFisicaItem;
import org.itcgae.siga.DTOs.cen.BusquedaPerFisicaSearchDTO;
import org.itcgae.siga.DTOs.cen.BusquedaPerJuridicaDTO;
import org.itcgae.siga.DTOs.cen.BusquedaPerJuridicaItem;
import org.itcgae.siga.DTOs.cen.BusquedaPerJuridicaSearchDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoGeneralDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.NoColegiadoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.cen.services.IBusquedaPerService;
import org.itcgae.siga.cen.services.IInstitucionesService;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmConfig;
import org.itcgae.siga.db.entities.AdmConfigExample;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.mappers.AdmConfigMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenNocolegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.itcgae.siga.ws.client.ClientCENSO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegiados.info.redabogacia.BusquedaColegiadoRequestDocument;
import com.colegiados.info.redabogacia.BusquedaColegiadoRequestDocument.BusquedaColegiadoRequest;
import com.colegiados.info.redabogacia.BusquedaColegiadoResponseDocument;
import com.colegiados.info.redabogacia.BusquedaColegiadoResponseDocument.BusquedaColegiadoResponse;
import com.colegiados.info.redabogacia.ColegiadoRequestDocument;
import com.colegiados.info.redabogacia.ColegiadoRequestDocument.ColegiadoRequest;
import com.colegiados.info.redabogacia.ColegiadoResponseDocument;
import com.colegiados.info.redabogacia.ColegiadoResponseDocument.ColegiadoResponse;
import com.colegiados.info.redabogacia.ColegiadoResponseDocument.ColegiadoResponse.Colegiado;
import com.colegiados.info.redabogacia.ColegioType;
import com.colegiados.info.redabogacia.IdentificacionType;

@Service
public class BusquedaPerServiceImpl implements IBusquedaPerService {

	private Logger LOGGER = Logger.getLogger(BusquedaPerServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private CenInstitucionExtendsMapper cenInstitucionExtendsMapper;

	@Autowired
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;
	
	@Autowired
	private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;
	
	@Autowired
	private CenNocolegiadoExtendsMapper cenNocolegiadoExtendsMapper;
	
	@Autowired
	private AdmConfigMapper admConfigMapper;

	@Autowired
	private ClientCENSO clientCENSO;

	@Autowired
	private IInstitucionesService institucionesService;
	
	@Override
	public ComboDTO getLabelColegios(HttpServletRequest request) {
		LOGGER.info("getLabelColegios() -> Entrada al servicio para la búsqueda de todos los colegios");
	
		ComboDTO combo = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		LOGGER.info(
				"getLabelColegios() / cenInstitucionExtendsMapper.getComboInstituciones() -> Entrada a cenInstitucionExtendsMapper para busqueda de personas no colegiadas por filtro");
		comboItems = cenInstitucionExtendsMapper.getComboInstituciones();
		LOGGER.info(
				"getLabelColegios() / cenInstitucionExtendsMapper.getComboInstituciones() -> Salida de cenInstitucionExtendsMapper para busqueda de personas no colegiadas por filtro");
	
//		if (!comboItems.equals(null) && comboItems.size() > 0) {
//			// añade elemento vacio al principio para el dropdown de parte front
//			ComboItem comboItem = new ComboItem();
//			comboItem.setLabel("");
//			comboItem.setValue("");
//			comboItems.add(0, comboItem);
//			combo.setCombooItems(comboItems);
//		}
		
		combo.setCombooItems(comboItems);
		
		LOGGER.info("getLabelColegios() -> Salida del servicio para la búsqueda de todos los colegios");
		return combo;

	}

	@Override
	public BusquedaPerJuridicaDTO searchPerJuridica(int numPagina, BusquedaPerJuridicaSearchDTO busquedaPerJuridicaSearchDTO, HttpServletRequest request) {
		LOGGER.info(
				"searchJuridica() -> Entrada al servicio para la búsqueda por filtros de personas jurídicas");

		List<BusquedaPerJuridicaItem> busquedaJuridicaItems = new ArrayList<BusquedaPerJuridicaItem>();
		BusquedaPerJuridicaDTO busquedaPerJuridicaDTO = new BusquedaPerJuridicaDTO();
		String idLenguaje = null;

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchJuridica() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchJuridica() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				idLenguaje = usuario.getIdlenguaje();
				
				LOGGER.info(
						"searchJuridica() / cenPersonaExtendsMapper.searchPerJuridica() -> Entrada a cenNocolegiadoExtendsMapper para búsqueda de personas juridicas por filtro");
				busquedaJuridicaItems = cenPersonaExtendsMapper.searchPerJuridica(numPagina, busquedaPerJuridicaSearchDTO, idLenguaje);
				LOGGER.info(
						"searchJuridica() / cenNocolegiadoExtendsMapper.searchLegalPersons() -> Salida de cenNocolegiadoExtendsMapper para búsqueda de personas juridicas por filtro");

				//Llamamos al WS de Sociedades para buscar personas jurídicas.
//				if (null == busquedaJuridicaItems || busquedaJuridicaItems.size()==0) {
//					AdmConfigExample example = new AdmConfigExample();
//					example.createCriteria().andClaveEqualTo("url.ws.sociedades");
//					List<AdmConfig> config = admConfigMapper.selectByExample(example );
//					
//					if (null != config && config.size()>0) {
//						GetSociedadesPublicadorRequestDocument requestSociedades = GetSociedadesPublicadorRequestDocument.Factory.newInstance();
//						
//						GetSociedadesPublicadorRequest requestBody = GetSociedadesPublicadorRequest.Factory.newInstance();
//						if (null != busquedaPerJuridicaSearchDTO && null != busquedaPerJuridicaSearchDTO.getNif()) {
//							requestBody.setNIF(busquedaPerJuridicaSearchDTO.getNif());						
//						}
//						if (null != busquedaPerJuridicaSearchDTO && null != busquedaPerJuridicaSearchDTO.getDenominacion()) {
//							
//							requestBody.setDenominacion(busquedaPerJuridicaSearchDTO.getDenominacion());						
//						}
//						if (null != busquedaPerJuridicaSearchDTO && null != busquedaPerJuridicaSearchDTO.getTipo()) {
//							requestBody.setTipoSociedad(busquedaPerJuridicaSearchDTO.getTipo());						
//						}					
//						if (null != busquedaPerJuridicaSearchDTO.getIdInstitucion() && busquedaPerJuridicaSearchDTO.getIdInstitucion().length>0) {
//							Colegio[] colegios = new Colegio[busquedaPerJuridicaSearchDTO.getIdInstitucion().length];
//							for (int i = 0; i < busquedaPerJuridicaSearchDTO.getIdInstitucion().length; i++) {
//								List<CenInstitucion> instituciones = institucionesService.getCodExternoByidInstitucion(busquedaPerJuridicaSearchDTO.getIdInstitucion()[i]);
//								
//								if (null != instituciones && instituciones.size()>0) {
//									Colegio colegio = Colegio.Factory.newInstance();
//									colegio.setCodigoColegio(instituciones.get(0).getCodigoext());
//									colegios[i] = colegio;
//								}
//								
//								
//							}
//							requestBody.setColegioArray(colegios);
//							
//						}
//						requestBody.setEntidadOrigen("SIGA");
//						requestBody.setNumPagina(Short.valueOf("1"));
//						requestBody.setNumeroPeticion("1");
//						requestBody.setVersionEsquema("1");
//						Calendar fechaDesde = new GregorianCalendar(2000, Calendar.JANUARY, 01, 12, 00, 00);
//						requestBody.setFechaDesde(fechaDesde);
//						
//						Calendar fechaHasta = new GregorianCalendar(2050, Calendar.JANUARY, 01, 12, 00, 00);
//						requestBody.setFechaHasta(fechaHasta);
//						requestSociedades.setGetSociedadesPublicadorRequest(requestBody);
//						
//						GetSociedadesPublicadorResponseDocument registroSociedades = clienteRegistroSociedades.getListaSociedades(requestSociedades,config.get(0).getValor());
//						GetSociedadesPublicadorResponse responseSociedades = GetSociedadesPublicadorResponse.Factory.newInstance();
//						responseSociedades = registroSociedades.getGetSociedadesPublicadorResponse();
//						SociedadesColegio[] sociedadesList = responseSociedades.getSociedadesColegioArray();
//						if (null != sociedadesList && sociedadesList.length>0) {
//							for (int i = 0; i < sociedadesList.length; i++) {
//								BusquedaPerJuridicaItem busquedaJuridica = new BusquedaPerJuridicaItem();
//								if (null != sociedadesList[i].getRegistroSociedadArray() && sociedadesList[i].getRegistroSociedadArray().length>0) {
//									for (int j = 0; j < sociedadesList[i].getRegistroSociedadArray().length; j++) {
//										if (null != sociedadesList[i].getRegistroSociedadArray()[0].getSociedadActualizacion()) {
//											busquedaJuridica = new BusquedaPerJuridicaItem();
//											busquedaJuridica.setDenominacion(sociedadesList[i].getRegistroSociedadArray()[0].getSociedadActualizacion().getDatosSociedad().getDenominacion());
//											busquedaJuridica.setNif(sociedadesList[i].getRegistroSociedadArray()[0].getSociedadActualizacion().getDatosSociedad().getCIFNIF());
//											busquedaJuridica.setFechaConstitucion(sociedadesList[i].getRegistroSociedadArray()[0].getSociedadActualizacion().getFechaConstitucion().getTime());
//											busquedaJuridica.setSociedadProfesional("1");
//											if (null != sociedadesList[i].getColegio()) {
//												List<CenInstitucion> instituciones = institucionesService.getidInstitucionByCodExterno(sociedadesList[i].getColegio().getCodigoColegio());
//												if (null != instituciones && instituciones.size()>0) {
//													busquedaJuridica.setIdInstitucion(instituciones.get(0).getIdinstitucion().toString());
//												}
//											}
//											if (null != sociedadesList[i].getRegistroSociedadArray()[0].getSociedadActualizacion().getIntegranteSociedadArray() 
//													&& sociedadesList[i].getRegistroSociedadArray()[0].getSociedadActualizacion().getIntegranteSociedadArray().length>0) {
//												busquedaJuridica.setNumeroIntegrantes(String.valueOf(sociedadesList[i].getRegistroSociedadArray()[0].getSociedadActualizacion().getIntegranteSociedadArray().length));
//											}
//											busquedaJuridicaItems.add(busquedaJuridica);
//										}
//									}
//								}
//							//	busquedaJuridica.setNif(sociedad.getRegistroSociedadArray()[0].getSociedadActualizacion().get);
//							//	sociedad.getRegistroSociedadArray()[1
//							}
//						}
//						//registroSociedades.getGetSociedadesPublicadorResponse()
//					}
//				}
				
				busquedaPerJuridicaDTO.setBusquedaPerJuridicaItems(busquedaJuridicaItems);
				
			} 
			else {
				LOGGER.warn(
						"searchJuridica() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} 
		else {
			LOGGER.warn("searchJuridica() -> idInstitucion del token nula");
		}

		LOGGER.info("searchJuridica() -> Salida del servicio para la búsqueda por filtros de personas jurídicas");
		
		return busquedaPerJuridicaDTO;
	}

	@Override
	public BusquedaPerFisicaDTO searchPerFisica(int numPagina, BusquedaPerFisicaSearchDTO busquedaPerFisicaSearchDTO,
			HttpServletRequest request) {
		LOGGER.info(
				"searchPerFisica() -> Entrada al servicio para la búsqueda de personas físicas");

		List<BusquedaPerFisicaItem> busquedaPerFisicaItems = new ArrayList<BusquedaPerFisicaItem>();
		BusquedaPerFisicaDTO busquedaPerFisicaDTO = new BusquedaPerFisicaDTO();
		String idLenguaje = null;

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchPerFisica() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchPerFisica() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				idLenguaje = usuario.getIdlenguaje();

				
				// 1. Buscamos colegiados dentro de nuestro colegio
				LOGGER.info(
						"searchPerFisica() / cenPersonaExtendsMapper.searchPerFisica() -> Entrada a cenPersonaExtendsMapper para obtener lista de personas físicas");
				busquedaPerFisicaItems = cenPersonaExtendsMapper.searchPerFisica(busquedaPerFisicaSearchDTO, idLenguaje, String.valueOf(idInstitucion));
				
				if(busquedaPerFisicaItems.size() == 0 || null == busquedaPerFisicaItems) {
					BusquedaPerFisicaSearchDTO segundaBusqueda = new BusquedaPerFisicaSearchDTO();
					segundaBusqueda.setNif(busquedaPerFisicaSearchDTO.getNif());
					busquedaPerFisicaItems = cenPersonaExtendsMapper.searchPerFisica(segundaBusqueda, idLenguaje, String.valueOf(idInstitucion));
					if(busquedaPerFisicaItems.size() > 0) {
						busquedaPerFisicaDTO.setOnlyNif(true);
					}else {
						busquedaPerFisicaDTO.setOnlyNif(false);
					}
				}
				
				LOGGER.info(
						"searchPerFisica() / cenPersonaExtendsMapper.searchPerFisica() -> Salida de cenPersonaExtendsMapper para obtener lista de personas físicas");

				busquedaPerFisicaDTO.setBusquedaFisicaItems(busquedaPerFisicaItems); 
								
				// 2. Si no encontramos registros en BD local, buscamos en la aplicación de Censo
				if (null == busquedaPerFisicaItems || busquedaPerFisicaItems.size() == 0) {
					if (null != busquedaPerFisicaSearchDTO.getNif()  && !busquedaPerFisicaSearchDTO.getNif().equals("")) {
						Colegiado colegiado = buscarColegiado(busquedaPerFisicaSearchDTO.getNif());
						if (null != colegiado) {
							BusquedaPerFisicaItem busquedaPerFisica = new BusquedaPerFisicaItem();
							if (null != colegiado.getDatosPersonales().getApellido1()) {
								busquedaPerFisica.setPrimerApellido(colegiado.getDatosPersonales().getApellido1());
							}else{
								busquedaPerFisica.setPrimerApellido("");
							}
							if (null != colegiado.getDatosPersonales().getApellido2()) {
								busquedaPerFisica.setSegundoApellido(colegiado.getDatosPersonales().getApellido2());
							}else{
								busquedaPerFisica.setSegundoApellido("");
							}

							busquedaPerFisica.setApellidos(busquedaPerFisica.getPrimerApellido().concat(busquedaPerFisica.getSegundoApellido()));
							busquedaPerFisica.setNif(colegiado.getDatosPersonales().getIdentificacion().getNIF());
							busquedaPerFisica.setNombre(colegiado.getDatosPersonales().getNombre());
							
							if (null != colegiado.getColegiacionArray() && colegiado.getColegiacionArray().length>0) {
								if (null != colegiado.getColegiacionArray()[0].getResidente()) {
									if (colegiado.getColegiacionArray()[0].getResidente().toString().equals("1")) {
										busquedaPerFisica.setResidente("SI");
									}else{
										busquedaPerFisica.setResidente("NO");
									}
									
								}
								busquedaPerFisica.setNumeroColegiado(colegiado.getColegiacionArray()[0].getNumColegiado());
								busquedaPerFisica.setSituacion(colegiado.getColegiacionArray()[0].getSituacion().getSituacionEjerProfesional().toString());
								if (null != colegiado.getColegiacionArray()[0].getColegio()) {
									List<CenInstitucion> instituciones = institucionesService.getidInstitucionByCodExterno(colegiado.getColegiacionArray()[0].getColegio().getCodigoColegio());
									if (null != instituciones && instituciones.size()>0) {
										busquedaPerFisica.setColegio(instituciones.get(0).getNombre());
										busquedaPerFisica.setNumeroInstitucion(instituciones.get(0).getIdinstitucion().toString());
									}
								}
							}
							busquedaPerFisicaItems.add(busquedaPerFisica);
						}
					}		
					else {
						com.colegiados.info.redabogacia.BusquedaColegiadoResponseDocument.BusquedaColegiadoResponse.Colegiado[] colegiado = buscarColegiadoSinDocumentacion(busquedaPerFisicaSearchDTO);
						if (null != colegiado && colegiado.length>0) {
							//validamos si nos viene filtro por institucion
							if (null != busquedaPerFisicaSearchDTO.getIdInstitucion() && busquedaPerFisicaSearchDTO.getIdInstitucion().length>0) {
								List<String> idInstituciones = new ArrayList<String>();
								for (int i = 0; i < busquedaPerFisicaSearchDTO.getIdInstitucion().length; i++) {
									List<CenInstitucion> instituciones = institucionesService.getCodExternoByidInstitucion(busquedaPerFisicaSearchDTO.getIdInstitucion()[i]);
									
									if (null != instituciones && instituciones.size()>0) {
										idInstituciones.add(instituciones.get(0).getCodigoext());
									}
								}
								
							}
							for (int i = 0; i < colegiado.length; i++) {

								BusquedaPerFisicaItem busquedaPerFisica = new BusquedaPerFisicaItem();
								if (null != colegiado[i].getDatosPersonales().getApellido1()) {
									busquedaPerFisica.setPrimerApellido(colegiado[i].getDatosPersonales().getApellido1());
								}else{
									busquedaPerFisica.setPrimerApellido("");
								}
								if (null != colegiado[i].getDatosPersonales().getApellido2()) {
									busquedaPerFisica.setSegundoApellido(colegiado[i].getDatosPersonales().getApellido2());
								}else{
									busquedaPerFisica.setSegundoApellido("");
								}

								busquedaPerFisica.setApellidos(busquedaPerFisica.getPrimerApellido().concat(busquedaPerFisica.getSegundoApellido()));
								busquedaPerFisica.setNif(colegiado[i].getDatosPersonales().getIdentificacion().getNIF());
								busquedaPerFisica.setNombre(colegiado[i].getDatosPersonales().getNombre());
								
								if (null != colegiado[i].getColegiacionArray() && colegiado[i].getColegiacionArray().length>0) {
									if (null != colegiado[i].getColegiacionArray()[0].getResidente()) {
										if (colegiado[i].getColegiacionArray()[0].getResidente().toString().equals("1")) {
											busquedaPerFisica.setResidente("SI");
										}else{
											busquedaPerFisica.setResidente("NO");
										}
										
									}
									busquedaPerFisica.setNumeroColegiado(colegiado[i].getColegiacionArray()[0].getNumColegiado());
									busquedaPerFisica.setSituacion(colegiado[i].getColegiacionArray()[0].getSituacion().getSituacionEjerProfesional().toString());
									if (null != colegiado[i].getColegiacionArray()[0].getColegio()) {
										List<CenInstitucion> instituciones = institucionesService.getidInstitucionByCodExterno(colegiado[i].getColegiacionArray()[0].getColegio().getCodigoColegio());
										if (null != instituciones && instituciones.size()>0) {
											busquedaPerFisica.setColegio(instituciones.get(0).getNombre());
											busquedaPerFisica.setNumeroInstitucion(instituciones.get(0).getIdinstitucion().toString());
										}
									}
								}
								busquedaPerFisicaItems.add(busquedaPerFisica);
									
								
							}
						}
					}
					
				
				}				
			} else {
				LOGGER.warn(
						"searchPerFisica() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("searchPerFisica() -> idInstitucion del token nula");
		}

		LOGGER.info(
				"searchPerFisica() -> Salida del servicio para la búsqueda de personas físicas");
		return busquedaPerFisicaDTO;
	}

	private com.colegiados.info.redabogacia.BusquedaColegiadoResponseDocument.BusquedaColegiadoResponse.Colegiado[] buscarColegiadoSinDocumentacion(BusquedaPerFisicaSearchDTO busquedaPerFisicaSearchDTO) {
		

		com.colegiados.info.redabogacia.BusquedaColegiadoResponseDocument.BusquedaColegiadoResponse.Colegiado[] colegiado = null;
		
		try {
			AdmConfigExample example = new AdmConfigExample();
			example.createCriteria().andClaveEqualTo("url.ws.censo");
			List<AdmConfig> config = admConfigMapper.selectByExample(example );
			
			if (null != config && config.size()>0) {
				BusquedaColegiadoRequest colegiadoRequest = BusquedaColegiadoRequest.Factory.newInstance();
				colegiadoRequest.setNombre(busquedaPerFisicaSearchDTO.getNombre());
				colegiadoRequest.setApellido1(busquedaPerFisicaSearchDTO.getPrimerApellido());
				colegiadoRequest.setApellido2(busquedaPerFisicaSearchDTO.getSegundoApellido());
				
				com.colegiados.info.redabogacia.BusquedaColegiadoRequestDocument.BusquedaColegiadoRequest.Colegiado colegiadoSearch =com.colegiados.info.redabogacia.BusquedaColegiadoRequestDocument.BusquedaColegiadoRequest.Colegiado.Factory.newInstance();
				if (null != busquedaPerFisicaSearchDTO.getNumeroColegiado()  && null != busquedaPerFisicaSearchDTO.getIdInstitucion()) {
					colegiadoSearch.setNumColegiado(busquedaPerFisicaSearchDTO.getNumeroColegiado());
					ColegioType colegio = ColegioType.Factory.newInstance();
					List<CenInstitucion> instituciones = institucionesService.getCodExternoByidInstitucion(busquedaPerFisicaSearchDTO.getIdInstitucion()[0]);
					colegio.setCodigoColegio(instituciones.get(0).getCodigoext());
					colegiadoSearch.setColegio(colegio );
					if(!UtilidadesString.esCadenaVacia(busquedaPerFisicaSearchDTO.getNumeroColegiado())) {
						colegiadoSearch.setNumColegiado(busquedaPerFisicaSearchDTO.getNumeroColegiado());
					}
					colegiadoRequest.setColegiado(colegiadoSearch);
				}

				BusquedaColegiadoRequestDocument colegiadoRequestDocument = BusquedaColegiadoRequestDocument.Factory.newInstance();
				colegiadoRequestDocument.setBusquedaColegiadoRequest(colegiadoRequest);
				BusquedaColegiadoResponseDocument busquedaColegiadoResponseDocument = null;

				busquedaColegiadoResponseDocument = clientCENSO.busquedaColegiadoSinIdentificacion(colegiadoRequestDocument,config.get(0).getValor());
				BusquedaColegiadoResponse colegiadoResponse = busquedaColegiadoResponseDocument.getBusquedaColegiadoResponse();
				colegiado = colegiadoResponse.getColegiadoArray();
			}
			
		} catch (Exception e){
			LOGGER.error("Error en la llamada a busqueda de colegiados.", e);
		}
		return colegiado;
	}

	private Colegiado buscarColegiado(String documento) {
		

		Colegiado colegiado = null;
		
		try {
			AdmConfigExample example = new AdmConfigExample();
			example.createCriteria().andClaveEqualTo("url.ws.censo");
			List<AdmConfig> config = admConfigMapper.selectByExample(example );
			
			if (null != config && config.size()>0) {
				ColegiadoRequest colegiadoRequest = ColegiadoRequest.Factory.newInstance();
				String tipo = isNifNie(documento);
				// Rellenamos la peticion
				IdentificacionType identificacion = IdentificacionType.Factory.newInstance();
				if(tipo.equals("NIF")){
					identificacion.setNIF(documento);
				}else if(tipo.equals("NIE")){
					identificacion.setNIE(documento);
				}
				colegiadoRequest.setIdentificacion(identificacion);
				
				ColegiadoRequestDocument colegiadoRequestDocument = ColegiadoRequestDocument.Factory.newInstance();
				colegiadoRequestDocument.setColegiadoRequest(colegiadoRequest);
				ColegiadoResponseDocument colegiadoResponseDocument = null;
			
				colegiadoResponseDocument = clientCENSO.busquedaColegiadoConIdentificacion(colegiadoRequestDocument,config.get(0).getValor());
				ColegiadoResponse colegiadoResponse = colegiadoResponseDocument.getColegiadoResponse();
				colegiado = colegiadoResponse.getColegiado();
			}
		} catch (Exception e){
			LOGGER.error("Error en la llamada a busqueda de colegiados.", e);
		}
		return colegiado;
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

	@Override
	public ColegiadoGeneralDTO searchPerByIdPersona(String idPersona, HttpServletRequest request) {
		
		LOGGER.info("searchPerByIdPersona() -> Entrada del servicio para la búsqueda de persona por idPersona");
		
		ColegiadoGeneralDTO personaEncontrada = new ColegiadoGeneralDTO();
		Error error = new Error();
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				// Buscamos a la persona como colegiado
				List<ColegiadoItem> colegiado = cenColegiadoExtendsMapper.selectColegiadosByIdPersona(idInstitucion, idPersona);
				
				if(colegiado != null && colegiado.size() > 0) {
					// Persona encontrada
					personaEncontrada.setColegiadoItem(colegiado);
				}else {
					List<NoColegiadoItem> noColegiado = cenNocolegiadoExtendsMapper.selectNoColegiadosByIdPersona(idInstitucion, idPersona);
					if(noColegiado != null && noColegiado.size() > 0) {
						personaEncontrada.setNoColegiadoItem(noColegiado);
					}else {
						error.setCode(404);
						error.setDescription("Persona no encontrada");
						error.setMessage("Persona no encontrada");
						personaEncontrada.error(error);
					}
				}
			}
		}
		
		LOGGER.info("searchPerByIdPersona() -> Salida del servicio para la búsqueda de persona por idPersona");
		
		return personaEncontrada;
	}
	
	@Override
	public ColegiadoGeneralDTO searchPerByIdPersonaIdInstitucion(String idPersona, String idInstitucionPersona, HttpServletRequest request) {
		
		LOGGER.info("searchPerByIdPersonaIdInstitucion() -> Entrada del servicio para la búsqueda de persona por idPersona e idInstitucion");
		
		ColegiadoGeneralDTO personaEncontrada = new ColegiadoGeneralDTO();
		Error error = new Error();
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			
			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				// Buscamos a la persona como colegiado
				List<ColegiadoItem> colegiado = cenColegiadoExtendsMapper.selectColegiadosByIdPersona(Short.parseShort(idInstitucionPersona), idPersona);
				
				if(colegiado != null && colegiado.size() > 0) {
					// Persona encontrada
					personaEncontrada.setColegiadoItem(colegiado);
				}else {
					List<NoColegiadoItem> noColegiado = cenNocolegiadoExtendsMapper.selectNoColegiadosByIdPersona(Short.parseShort(idInstitucionPersona), idPersona);
					if(noColegiado != null && noColegiado.size() > 0) {
						personaEncontrada.setNoColegiadoItem(noColegiado);
					}else {
						error.setCode(404);
						error.setDescription("Persona no encontrada");
						error.setMessage("Persona no encontrada");
						personaEncontrada.error(error);
					}
				}
			}
		}
		
		LOGGER.info("searchPerByIdPersonaIdInstitucion() -> Salida del servicio para la búsqueda de persona por idPersona e idInstitucion");
		
		return personaEncontrada;
	}

}
