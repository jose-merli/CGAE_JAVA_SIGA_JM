package org.itcgae.siga.cen.services.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.BusquedaPerFisicaDTO;
import org.itcgae.siga.DTOs.cen.BusquedaPerFisicaItem;
import org.itcgae.siga.DTOs.cen.BusquedaPerFisicaSearchDTO;
import org.itcgae.siga.cen.services.IBusquedaCensoGeneralService;
import org.itcgae.siga.cen.services.IInstitucionesService;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmConfig;
import org.itcgae.siga.db.entities.AdmConfigExample;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.mappers.AdmConfigMapper;
import org.itcgae.siga.ws.client.ClientCENSO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegiados.info.redabogacia.BusquedaColegiadoRequestDocument;
import com.colegiados.info.redabogacia.BusquedaColegiadoRequestDocument.BusquedaColegiadoRequest;
import com.colegiados.info.redabogacia.BusquedaColegiadoResponseDocument;
import com.colegiados.info.redabogacia.BusquedaColegiadoResponseDocument.BusquedaColegiadoResponse;
import com.colegiados.info.redabogacia.ColegiacionType;
import com.colegiados.info.redabogacia.ColegiadoRequestDocument;
import com.colegiados.info.redabogacia.ColegiadoRequestDocument.ColegiadoRequest;
import com.colegiados.info.redabogacia.ColegiadoResponseDocument;
import com.colegiados.info.redabogacia.ColegiadoResponseDocument.ColegiadoResponse;
import com.colegiados.info.redabogacia.ColegiadoResponseDocument.ColegiadoResponse.Colegiado;
import com.colegiados.info.redabogacia.ColegioType;
import com.colegiados.info.redabogacia.IdentificacionType;

@Service
public class BusquedaCensoGeneralServiceImpl implements IBusquedaCensoGeneralService {

	private Logger LOGGER = Logger.getLogger(BusquedaCensoGeneralServiceImpl.class);

	
	@Autowired
	private AdmConfigMapper admConfigMapper;

	@Autowired
	private ClientCENSO clientCENSO;

	@Autowired
	private IInstitucionesService institucionesService;
	
	

	@Override
	public BusquedaPerFisicaDTO search(int numPagina, BusquedaPerFisicaSearchDTO busquedaPerFisicaSearchDTO,
			HttpServletRequest request) {

			Colegiado colegiado = null;
			BusquedaPerFisicaDTO busquedaPerFisicaDTO = new BusquedaPerFisicaDTO();
			List<BusquedaPerFisicaItem> busquedaPerFisicaItems = new ArrayList<BusquedaPerFisicaItem>();
		try {
			AdmConfigExample example = new AdmConfigExample();
			example.createCriteria().andClaveEqualTo("url.ws.censo");
			List<AdmConfig> config = admConfigMapper.selectByExample(example );
			
			if (null != config && config.size()>0) {
				if (!UtilidadesString.esCadenaVacia(busquedaPerFisicaSearchDTO.getNif())) {
					
					//Busqueda por nif
					ColegiadoRequest colegiadoRequest = ColegiadoRequest.Factory.newInstance();
					String tipo = isNifNie(busquedaPerFisicaSearchDTO.getNif());
					// Rellenamos la peticion
					IdentificacionType identificacion = IdentificacionType.Factory.newInstance();
					if(tipo.equals("NIF")){
						identificacion.setNIF(busquedaPerFisicaSearchDTO.getNif());
					}else if(tipo.equals("NIE")){
						identificacion.setNIE(busquedaPerFisicaSearchDTO.getNif());
					}
					colegiadoRequest.setIdentificacion(identificacion);
					
					ColegiadoRequestDocument colegiadoRequestDocument = ColegiadoRequestDocument.Factory.newInstance();
					colegiadoRequestDocument.setColegiadoRequest(colegiadoRequest);
					ColegiadoResponseDocument colegiadoResponseDocument = null;
				
					colegiadoResponseDocument = clientCENSO.busquedaColegiadoConIdentificacion(colegiadoRequestDocument,config.get(0).getValor());
					ColegiadoResponse colegiadoResponse = colegiadoResponseDocument.getColegiadoResponse();
					colegiado = colegiadoResponse.getColegiado();
				
					
					if (null != colegiado) {
						if (null != colegiado.getColegiacionArray() && colegiado.getColegiacionArray().length>0) {
							for (ColegiacionType colegiadoColegiacion : colegiado.getColegiacionArray()) {
								Boolean encontrado = Boolean.TRUE;
								if (!UtilidadesString.esCadenaVacia(busquedaPerFisicaSearchDTO.getNombre())) {
									if (!busquedaPerFisicaSearchDTO.getNombre().equals(colegiado.getDatosPersonales().getNombre())) {
										encontrado = Boolean.FALSE;
									}
								}
								if (!UtilidadesString.esCadenaVacia(busquedaPerFisicaSearchDTO.getPrimerApellido())) {
									if (!busquedaPerFisicaSearchDTO.getPrimerApellido().equals(colegiado.getDatosPersonales().getApellido1())) {
										encontrado = Boolean.FALSE;
									}
								}
								if (!UtilidadesString.esCadenaVacia(busquedaPerFisicaSearchDTO.getSegundoApellido())) {
									if (!busquedaPerFisicaSearchDTO.getSegundoApellido().equals(colegiado.getDatosPersonales().getApellido2())) {
										encontrado = Boolean.FALSE;
									}
								}
								if (!UtilidadesString.esCadenaVacia(busquedaPerFisicaSearchDTO.getNumeroColegiado())) {
									if (null != colegiado.getColegiacionArray() && colegiado.getColegiacionArray().length>0) {
										encontrado = Boolean.FALSE;
											if (busquedaPerFisicaSearchDTO.getNumeroColegiado().equals(colegiadoColegiacion.getNumColegiado())) {
												encontrado = Boolean.TRUE;
											}
									}
		
								}
								if (null !=busquedaPerFisicaSearchDTO.getIdInstitucion() && busquedaPerFisicaSearchDTO.getIdInstitucion().length>0) {
										encontrado = Boolean.FALSE;
											for (int j = 0; j <  busquedaPerFisicaSearchDTO.getIdInstitucion().length; j++) {
												List<CenInstitucion> instituciones = institucionesService.getidInstitucionByCodExterno(colegiadoColegiacion.getColegio().getCodigoColegio());
												if (busquedaPerFisicaSearchDTO.getIdInstitucion()[j].equals(String.valueOf(instituciones.get(0).getIdinstitucion()))) {
													encontrado = Boolean.TRUE;
												}
											}
									}
		
								if (encontrado) {
									
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
									if (null != colegiadoColegiacion.getResidente()) {
										if (colegiadoColegiacion.getResidente().toString().equals("1")) {
											busquedaPerFisica.setResidente("SI");
										}else{
											busquedaPerFisica.setResidente("NO");
										}
										
									}
									busquedaPerFisica.setNumeroColegiado(colegiadoColegiacion.getNumColegiado());
									busquedaPerFisica.setSituacion(colegiadoColegiacion.getSituacion().getSituacionEjerProfesional().toString());
									
									SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
									String fechaEstado = format1.format(colegiadoColegiacion.getSituacion().getFechaSituacion().getTime());     
									busquedaPerFisica.setFechaEstado(fechaEstado);
									if (null != colegiado.getColegiacionArray()[0].getColegio()) {
										List<CenInstitucion> instituciones = institucionesService.getidInstitucionByCodExterno(colegiadoColegiacion.getColegio().getCodigoColegio());
										if (null != instituciones && instituciones.size()>0) {
											busquedaPerFisica.setColegio(instituciones.get(0).getNombre());
											busquedaPerFisica.setNumeroInstitucion(instituciones.get(0).getIdinstitucion().toString());
										}
									}
									if (null != colegiado.getLocalizacion()) {
										busquedaPerFisica.setDomicilio(colegiado.getLocalizacion().getDomicilio());
										
									}
									busquedaPerFisicaItems.add(busquedaPerFisica);
								}
							}
						
							
							busquedaPerFisicaDTO.setBusquedaFisicaItems(busquedaPerFisicaItems);
						}
					}
				}else{
					com.colegiados.info.redabogacia.BusquedaColegiadoResponseDocument.BusquedaColegiadoResponse.Colegiado[] colegiadoName = null;
					BusquedaColegiadoRequest colegiadoRequest = BusquedaColegiadoRequest.Factory.newInstance();
					if (null != busquedaPerFisicaSearchDTO.getNombre()){
						colegiadoRequest.setNombre(busquedaPerFisicaSearchDTO.getNombre());
					}
					if (null != busquedaPerFisicaSearchDTO.getPrimerApellido()){
						colegiadoRequest.setApellido1(busquedaPerFisicaSearchDTO.getPrimerApellido());
					}
					if (null != busquedaPerFisicaSearchDTO.getSegundoApellido()){
						colegiadoRequest.setApellido2(busquedaPerFisicaSearchDTO.getSegundoApellido());
					}
					
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
					colegiadoName = colegiadoResponse.getColegiadoArray();
					
					if (null != colegiadoName && colegiadoName.length>0) {
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
						for (int i = 0; i < colegiadoName.length; i++) {

							
							if (null != colegiadoName[i].getColegiacionArray() && colegiadoName[i].getColegiacionArray().length>0) {
								for (ColegiacionType colegiadoName2 : colegiadoName[i].getColegiacionArray()) {
									BusquedaPerFisicaItem busquedaPerFisica = new BusquedaPerFisicaItem();
									if (null != colegiadoName[i].getDatosPersonales().getApellido1()) {
										busquedaPerFisica.setPrimerApellido(colegiadoName[i].getDatosPersonales().getApellido1());
									}else{
										busquedaPerFisica.setPrimerApellido("");
									}
									if (null != colegiadoName[i].getDatosPersonales().getApellido2()) {
										busquedaPerFisica.setSegundoApellido(colegiadoName[i].getDatosPersonales().getApellido2());
									}else{
										busquedaPerFisica.setSegundoApellido("");
									}
	
									busquedaPerFisica.setApellidos(busquedaPerFisica.getPrimerApellido().concat(busquedaPerFisica.getSegundoApellido()));
									busquedaPerFisica.setNif(colegiadoName[i].getDatosPersonales().getIdentificacion().getNIF());
									busquedaPerFisica.setNombre(colegiadoName[i].getDatosPersonales().getNombre());
									
									if (null != colegiadoName2.getResidente()) {
										if (colegiadoName2.getResidente().toString().equals("1")) {
											busquedaPerFisica.setResidente("SI");
										}else{
											busquedaPerFisica.setResidente("NO");
										}
										
									}
									busquedaPerFisica.setNumeroColegiado(colegiadoName2.getNumColegiado());
									if (null != colegiadoName2.getSituacion()) {
										if (null != colegiadoName2.getSituacion().getSituacionEjerProfesional()) {
											busquedaPerFisica.setSituacion(colegiadoName[i].getColegiacionArray()[0].getSituacion().getSituacionEjerProfesional().toString());
											SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
											String fechaEstado = format1.format(colegiadoName2.getSituacion().getFechaSituacion().getTime());     
											busquedaPerFisica.setFechaEstado(fechaEstado);
										}
									}
									
									if (null != colegiadoName2.getColegio()) {
										List<CenInstitucion> instituciones = institucionesService.getidInstitucionByCodExterno(colegiadoName2.getColegio().getCodigoColegio());
										if (null != instituciones && instituciones.size()>0) {
											busquedaPerFisica.setColegio(instituciones.get(0).getNombre());
											busquedaPerFisica.setNumeroInstitucion(instituciones.get(0).getIdinstitucion().toString());
										}
									}
									if (null != colegiadoName[i].getLocalizacion()) {
										busquedaPerFisica.setDomicilio(colegiadoName[i].getLocalizacion().getDomicilio());
										
									}
									busquedaPerFisicaItems.add(busquedaPerFisica);
								}
							}

								
							
						}
						busquedaPerFisicaDTO.setBusquedaFisicaItems(busquedaPerFisicaItems); 
					}
				
				}
			}
		} catch (Exception e){
			LOGGER.error("Error en la llamada a busqueda de colegiados.", e);
		}
		
		
		if (null != busquedaPerFisicaDTO) {
			for (BusquedaPerFisicaItem busquedaPerFisicaItem : busquedaPerFisicaDTO.getBusquedaFisicaItems()) {
				
			}
		}
		return busquedaPerFisicaDTO;
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
