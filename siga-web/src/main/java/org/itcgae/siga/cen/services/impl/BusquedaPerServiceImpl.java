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
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.IBusquedaPerService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.itcgae.siga.ws.client.ClientCENSO;
import org.itcgae.siga.ws.client.ClientRegistroSociedades;
import org.itcgae.sspp.ws.publicadorSociedades.ColegioDocument.Colegio;
import org.itcgae.sspp.ws.publicadorSociedades.GetSociedadesPublicadorRequestDocument;
import org.itcgae.sspp.ws.publicadorSociedades.GetSociedadesPublicadorRequestDocument.GetSociedadesPublicadorRequest;
import org.itcgae.sspp.ws.publicadorSociedades.GetSociedadesPublicadorResponseDocument;
import org.itcgae.sspp.ws.publicadorSociedades.GetSociedadesPublicadorResponseDocument.GetSociedadesPublicadorResponse;
import org.itcgae.sspp.ws.publicadorSociedades.SociedadesColegio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegiados.info.redabogacia.BusquedaColegiadoRequestDocument;
import com.colegiados.info.redabogacia.BusquedaColegiadoRequestDocument.BusquedaColegiadoRequest;
import com.colegiados.info.redabogacia.BusquedaColegiadoResponseDocument;
import com.colegiados.info.redabogacia.BusquedaColegiadoResponseDocument.BusquedaColegiadoResponse;
import com.colegiados.info.redabogacia.ColegiadoRequestDocument;
import com.colegiados.info.redabogacia.ColegiadoResponseDocument;
import com.colegiados.info.redabogacia.IdentificacionType;
import com.colegiados.info.redabogacia.ColegiadoRequestDocument.ColegiadoRequest;
import com.colegiados.info.redabogacia.ColegiadoResponseDocument.ColegiadoResponse;
import com.colegiados.info.redabogacia.ColegiadoResponseDocument.ColegiadoResponse.Colegiado;

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
	private ClientRegistroSociedades clienteRegistroSociedades;

	@Autowired
	private ClientCENSO clientCENSO;

	
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
	
		if (!comboItems.equals(null) && comboItems.size() > 0) {
			// añade elemento vacio al principio para el dropdown de parte front
			ComboItem comboItem = new ComboItem();
			comboItem.setLabel("");
			comboItem.setValue("");
			comboItems.add(0, comboItem);
			combo.setCombooItems(comboItems);
		}
		
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
				if (null == busquedaJuridicaItems || busquedaJuridicaItems.size()==0) {
					GetSociedadesPublicadorRequestDocument requestSociedades = GetSociedadesPublicadorRequestDocument.Factory.newInstance();
					
					GetSociedadesPublicadorRequest requestBody = GetSociedadesPublicadorRequest.Factory.newInstance();
					if (null != busquedaPerJuridicaSearchDTO && null != busquedaPerJuridicaSearchDTO.getNif()) {
						requestBody.setNIF(busquedaPerJuridicaSearchDTO.getNif());						
					}
					if (null != busquedaPerJuridicaSearchDTO && null != busquedaPerJuridicaSearchDTO.getDenominacion()) {
						
						requestBody.setDenominacion(busquedaPerJuridicaSearchDTO.getDenominacion());						
					}
					if (null != busquedaPerJuridicaSearchDTO && null != busquedaPerJuridicaSearchDTO.getTipo()) {
						requestBody.setTipoSociedad(busquedaPerJuridicaSearchDTO.getTipo());						
					}					
					if (null != busquedaPerJuridicaSearchDTO.getIdInstitucion() && busquedaPerJuridicaSearchDTO.getIdInstitucion().length>0) {
						Colegio[] colegios = new Colegio[busquedaPerJuridicaSearchDTO.getIdInstitucion().length];
						for (int i = 0; i < busquedaPerJuridicaSearchDTO.getIdInstitucion().length; i++) {
							Colegio colegio = Colegio.Factory.newInstance();
							colegio.setCodigoColegio(busquedaPerJuridicaSearchDTO.getIdInstitucion()[i]);
							
						}
						requestBody.setColegioArray(colegios);
						
					}
					
					requestSociedades.setGetSociedadesPublicadorRequest(requestBody);
					GetSociedadesPublicadorResponseDocument registroSociedades = clienteRegistroSociedades.getListaSociedades(requestSociedades);
					GetSociedadesPublicadorResponse responseSociedades = GetSociedadesPublicadorResponse.Factory.newInstance();
					responseSociedades = registroSociedades.getGetSociedadesPublicadorResponse();
					SociedadesColegio[] sociedadesList = responseSociedades.getSociedadesColegioArray();
					if (null != sociedadesList && sociedadesList.length>0) {
						for (int i = 0; i < sociedadesList.length; i++) {
							SociedadesColegio sociedad = SociedadesColegio.Factory.newInstance();
							BusquedaPerJuridicaItem busquedaJuridica = new BusquedaPerJuridicaItem();
						//	busquedaJuridica.setNif(sociedad.getRegistroSociedadArray()[0].getSociedadActualizacion().get);
						//	sociedad.getRegistroSociedadArray()[1
						}
					}
					//registroSociedades.getGetSociedadesPublicadorResponse()
				}
				
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

				LOGGER.info(
						"searchPerFisica() / cenPersonaExtendsMapper.searchPerFisica() -> Entrada a cenPersonaExtendsMapper para obtener lista de personas físicas");
				busquedaPerFisicaItems = cenPersonaExtendsMapper.searchPerFisica(busquedaPerFisicaSearchDTO, idLenguaje);
				LOGGER.info(
						"searchPerFisica() / cenPersonaExtendsMapper.searchPerFisica() -> Salida de cenPersonaExtendsMapper para obtener lista de personas físicas");

				busquedaPerFisicaDTO.setBusquedaFisicaItems(busquedaPerFisicaItems); 
				
				//Si no encontramos registros, buscamos en la aplicación de Censo
				if (null == busquedaPerFisicaItems || busquedaPerFisicaItems.size() == 0) {
					if (null != busquedaPerFisicaSearchDTO.getNif()) {
						Colegiado colegiado = buscarColegiado(busquedaPerFisicaSearchDTO.getNif());
					}		
					else {
						com.colegiados.info.redabogacia.BusquedaColegiadoResponseDocument.BusquedaColegiadoResponse.Colegiado[] colegiado = buscarColegiadoSinDocumentacion(busquedaPerFisicaSearchDTO);
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
			
			BusquedaColegiadoRequest colegiadoRequest = BusquedaColegiadoRequest.Factory.newInstance();
			colegiadoRequest.setNombre(busquedaPerFisicaSearchDTO.getNombre());
			colegiadoRequest.setApellido1(busquedaPerFisicaSearchDTO.getPrimerApellido());
			colegiadoRequest.setApellido2(busquedaPerFisicaSearchDTO.getSegundoApellido());
			if (null != busquedaPerFisicaSearchDTO.getNumColegiado()  ) {
				com.colegiados.info.redabogacia.BusquedaColegiadoRequestDocument.BusquedaColegiadoRequest.Colegiado colegiadoSearch =com.colegiados.info.redabogacia.BusquedaColegiadoRequestDocument.BusquedaColegiadoRequest.Colegiado.Factory.newInstance();
				colegiadoSearch.setNumColegiado(busquedaPerFisicaSearchDTO.getNumColegiado());
				colegiadoRequest.setColegiado(colegiadoSearch);
			}
			BusquedaColegiadoRequestDocument colegiadoRequestDocument = BusquedaColegiadoRequestDocument.Factory.newInstance();
			colegiadoRequestDocument.setBusquedaColegiadoRequest(colegiadoRequest);
			BusquedaColegiadoResponseDocument busquedaColegiadoResponseDocument = null;
		
			busquedaColegiadoResponseDocument = clientCENSO.busquedaColegiadoSinIdentificacion(colegiadoRequestDocument);
			BusquedaColegiadoResponse colegiadoResponse = busquedaColegiadoResponseDocument.getBusquedaColegiadoResponse();
			colegiado = colegiadoResponse.getColegiadoArray();
			
		} catch (Exception e){
			LOGGER.error("Error en la llamada a busqueda de colegiados.", e);
		}
		return colegiado;
	}

	private Colegiado buscarColegiado(String documento) {
		

		Colegiado colegiado = null;
		
		try {
			
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
		
			colegiadoResponseDocument = clientCENSO.busquedaColegiadoConIdentificacion(colegiadoRequestDocument);
			ColegiadoResponse colegiadoResponse = colegiadoResponseDocument.getColegiadoResponse();
			colegiado = colegiadoResponse.getColegiado();
			
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

}
