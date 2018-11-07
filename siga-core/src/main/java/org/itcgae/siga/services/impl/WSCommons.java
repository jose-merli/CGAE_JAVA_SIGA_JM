package org.itcgae.siga.services.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.xmlbeans.XmlObject;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesSearchDTO;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesWS;
import org.itcgae.siga.DTOs.cen.SociedadesBajaDTO;
import org.itcgae.siga.DTOs.cen.SociedadesEditadasDTO;
import org.itcgae.siga.commons.constants.SigaConstants.ERROR_SERVER;
import org.itcgae.siga.commons.constants.SigaConstants.ESTADO_CARGAS;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.CargasWs;
import org.itcgae.siga.db.entities.CargasWsPagina;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenInstitucionExample;
import org.itcgae.siga.db.entities.CfgParamColegios;
import org.itcgae.siga.db.entities.CfgParamColegiosExample;
import org.itcgae.siga.db.entities.CmnDatosXml;
import org.itcgae.siga.db.mappers.CargasWsMapper;
import org.itcgae.siga.db.mappers.CargasWsPaginaMapper;
import org.itcgae.siga.db.mappers.CenInstitucionMapper;
import org.itcgae.siga.db.mappers.CfgParamColegiosMapper;
import org.itcgae.siga.db.services.cen.mappers.CenComponentesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenNocolegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.GuardarXmlDaoImpl;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.exception.ValidationException;
import org.itcgae.sspp.ws.registroSociedades.ColegioDocument.Colegio;
import org.itcgae.sspp.ws.registroSociedades.ContactoDocument.Contacto;
import org.itcgae.sspp.ws.registroSociedades.ContactoDocument.Contacto.Fax;
import org.itcgae.sspp.ws.registroSociedades.ContactoDocument.Contacto.Telefono;
import org.itcgae.sspp.ws.registroSociedades.ContactoDocument.Contacto.TelefonoMovil;
import org.itcgae.sspp.ws.registroSociedades.DatosCargoDocument.DatosCargo;
import org.itcgae.sspp.ws.registroSociedades.DatosEntidad;
import org.itcgae.sspp.ws.registroSociedades.DatosEntidad.FormaSocial;
import org.itcgae.sspp.ws.registroSociedades.DatosPersona;
import org.itcgae.sspp.ws.registroSociedades.DatosProfesionalDocument.DatosProfesional;
import org.itcgae.sspp.ws.registroSociedades.DatosRegistroDocument.DatosRegistro;
import org.itcgae.sspp.ws.registroSociedades.DireccionDocument.Direccion;
import org.itcgae.sspp.ws.registroSociedades.DireccionDocument.Direccion.CorreoElectronico;
import org.itcgae.sspp.ws.registroSociedades.DireccionDocument.Direccion.Poblacion;
import org.itcgae.sspp.ws.registroSociedades.DireccionDocument.Direccion.Provincia;
import org.itcgae.sspp.ws.registroSociedades.ErrorType;
import org.itcgae.sspp.ws.registroSociedades.GetListaSociedadesRequestDocument.GetListaSociedadesRequest;
import org.itcgae.sspp.ws.registroSociedades.GetListaSociedadesResponseDocument.GetListaSociedadesResponse;
import org.itcgae.sspp.ws.registroSociedades.IdentificacionDocument.Identificacion;
import org.itcgae.sspp.ws.registroSociedades.IntegranteSociedadDocument.IntegranteSociedad;
import org.itcgae.sspp.ws.registroSociedades.IntegranteSociedadDocument.IntegranteSociedad.IntegranteFisico;
import org.itcgae.sspp.ws.registroSociedades.IntegranteSociedadDocument.IntegranteSociedad.IntegranteJuridico;
import org.itcgae.sspp.ws.registroSociedades.ProfesionalAbogadoDocument.ProfesionalAbogado;
import org.itcgae.sspp.ws.registroSociedades.ProfesionalDocument.Profesional;
import org.itcgae.sspp.ws.registroSociedades.RegistroSociedadDocument.RegistroSociedad;
import org.itcgae.sspp.ws.registroSociedades.SociedadActualizacionDocument.SociedadActualizacion;
import org.itcgae.sspp.ws.registroSociedades.SociedadActualizacionDocument.SociedadActualizacion.Resena;
import org.itcgae.sspp.ws.registroSociedades.SociedadBajaDocument.SociedadBaja;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WSCommons {

	private static final Logger LOGGER = LoggerFactory.getLogger(WSCommons.class);

	@Autowired
	CfgParamColegiosMapper cfgParamColegiosMapper;
	@Autowired
	CenInstitucionMapper cenInstitucionMapper;
	
	@Autowired
	CargasWsPaginaMapper cargasWsPaginaMapper;
	@Autowired
	CargasWsMapper cargasWsMapper;

	@Autowired
	GuardarXmlDaoImpl guardarXmlDaoImpl;
	
	@Autowired
	CenNocolegiadoExtendsMapper cenNoColegiado;
	
	@Autowired
	private CenComponentesExtendsMapper cenComponentesExtendsMapper;

	
	private int tamanoPaginacion;



	public List<String> validaPeticion(XmlObject xmlObjectRequest, XmlObject xmlObjectResponse, boolean lanzarError)
			throws ValidationException {
		List<String> errores = ServicesHelper.validate(xmlObjectRequest, true);

		if (errores != null && errores.size() > 0) {

			String message = "El xml recibido no es válido.";
			for (String error : errores) {
				LOGGER.debug("Error: " + error);
				message += "\n" + error;
			}
			if (lanzarError) {
				lanzaError(ERROR_SERVER.XML_NO_VALIDO.name(), message, xmlObjectRequest, xmlObjectResponse);
			} else {
				throw new ValidationException(message);
			}
		}
		return errores;
	}

	public List<String> validaRespuesta(XmlObject xmlObject) {
		List<String> errores = ServicesHelper.validate(xmlObject, true);

		if (errores != null && errores.size() > 0) {
			LOGGER.info("El mensaje de respuesta no es válido.");
			for (String error : errores) {
				LOGGER.debug("Error: " + error);
			}

		}
		return errores;
	}

	public void lanzaError(String codigo, String message, XmlObject xmlObjectResponse) throws ValidationException {
		lanzaError(codigo, message, null, xmlObjectResponse);
	}

	private void lanzaError(String codigo, String message, XmlObject xmlObjectRequest, XmlObject xmlObjectResponse)
			throws ValidationException {

		LOGGER.error(message);

		if (xmlObjectResponse instanceof GetListaSociedadesResponse) {

			ErrorType errorType = ((GetListaSociedadesResponse) xmlObjectResponse).addNewErrorServidor();
			org.itcgae.sspp.ws.registroSociedades.ErrorDocument.Error error = errorType.addNewError();

			error.setCodRetorno(codigo);
			error.setDescRetorno(message);

			if (xmlObjectRequest != null) {
				errorType.setXmlError(xmlObjectRequest.xmlText());
			} else {
				errorType.setXmlError("Sin error xml");
			}
		}

		throw new ValidationException(message);
	}

	public void construirErrorGeneral(String codigo, String message, XmlObject xmlObjectResponse) {

		LOGGER.error(message);

		if (xmlObjectResponse instanceof GetListaSociedadesResponse) {

			ErrorType errorType = ((GetListaSociedadesResponse) xmlObjectResponse).addNewErrorServidor();
			org.itcgae.sspp.ws.registroSociedades.ErrorDocument.Error error = errorType.addNewError();

			error.setCodRetorno(codigo);
			error.setDescRetorno(message);

			errorType.setXmlError("Sin error xml");

		}
	}

	private String obtenerValorParametroColegio(Short idInstitucion, String nombreParametro) {
		String valor = null;
		CfgParamColegiosExample cfgParamColegiosExample = new CfgParamColegiosExample();
		cfgParamColegiosExample.createCriteria().andIdInstitucionEqualTo(Long.valueOf(idInstitucion))
				.andNombreEqualTo(nombreParametro);

		List<CfgParamColegios> listaParam = cfgParamColegiosMapper.selectByExample(cfgParamColegiosExample);
		if (listaParam != null && listaParam.size() == 1) {
			valor = listaParam.get(0).getValor();
		}
		return valor;
	}

	public void comprobarIP(XmlObject xmlObjectRequest, String endpointReference, Short idInstitucion,
			String nombreParametroIP, ERROR_SERVER error) throws ValidationException {

		// Método genérico que recibe una peticion de un cliente de ws y comprueba si la
		// IP del colegio se corresponde con la correcta
		String valor = obtenerValorParametroColegio(idInstitucion, nombreParametroIP);
		if (valor == null || !endpointReference.equals(valor)) {
			// Si no hemos encontrado el parámetro, o hemos encontrado más de uno hay que
			// construir error si es solicitado y devolver excepcion
			if (error != null) {
				lanzaError(error.name(), error.getMensajeError(), xmlObjectRequest);
			} else {
				throw new ValidationException(
						"Parametro no encontrado, o la IPRecibida no coincide " + endpointReference
								+ " configurada para el colegio " + idInstitucion + " con la obtenida de BD: " + valor);
			}
		}
		LOGGER.info("Se ha encontrado correctamente la IP " + endpointReference + " configurada para el colegio "
				+ idInstitucion);
	}

	public void colegioActivo(XmlObject xmlObjectRequest, Short idInstitucion, String nombreParametro,
			ERROR_SERVER error) throws ValidationException {

		String valor = obtenerValorParametroColegio(idInstitucion, nombreParametro);

		if (valor == null || valor.equals("0")) {
			// Si no hemos encontrado el parámetro, o hemos encontrado más de uno hay que
			// construir error si es solicitado y devolver excepcion
			if (error != null) {
				lanzaError(error.name(), error.getMensajeError(), xmlObjectRequest);
			} else {
				throw new ValidationException(
						"Parametro no encontrado, o no esta activo para el coelgio " + idInstitucion);
			}
		}
		LOGGER.info("Colegio activo? " + valor);

	}

	
	public void actualizaEstadoPagina(CargasWsPagina paginaWs) throws BusinessException {
		if (cargasWsPaginaMapper.updateByPrimaryKey(paginaWs) != 1) {
			throw new BusinessException("Error al actualizar la página con id_pagina: " + paginaWs.getIdWsPagina());
		}

	}

	public void actualizaEstadoCarga(CargasWs carga) throws BusinessException {

		carga.setFechaModificacion(new Date());
		if (cargasWsMapper.updateByPrimaryKey(carga) != 1) {
			throw new BusinessException("Error al actualizar la carga con id_carga: " + carga.getIdCarga());
		}

	}

	

	
	public CenInstitucion buscarColegio(String codColegio) {

		// Buscamos la institucion cuyo código se corresponda con el solicitado
		CenInstitucion colegio = null;
		if (codColegio != null && !codColegio.trim().equals("")) {
			LOGGER.info("Buscamos la Institucion con codigocolegio = " + codColegio);
			CenInstitucionExample example = new CenInstitucionExample();
			example.createCriteria().andCodigoextEqualTo(codColegio);
			List<CenInstitucion> listaInstitucion = cenInstitucionMapper.selectByExample(example);
			if (listaInstitucion != null && listaInstitucion.size() == 1) {
				colegio = listaInstitucion.get(0);
				LOGGER.info("El idInstitucion encontrado es " + colegio.getIdinstitucion());
			}
		}
		return colegio;
	}

	

	public boolean comprobarString(String nombre, int maxLength) throws ValidationException {
		boolean valida = false;

		if (nombre != null && nombre.length() <= maxLength) {
			valida = true;
		}

		return valida;
	}

	public CargasWs insertarCarga(Short idInstitucion, Short tipo, String numeroPeticion, short numTotalPaginas) {

		CargasWs cargasWs = new CargasWs();

		cargasWs.setFechaCreacion(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
		cargasWs.setFechaModificacion(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
		cargasWs.setIdEstadoCarga(ESTADO_CARGAS.ESTADO_PENDIENTE.getCodigo());
		cargasWs.setIdInstitucion(Long.valueOf(idInstitucion));
		cargasWs.setIdTipoCarga(tipo);
		cargasWs.setNumeroPeticion(numeroPeticion);
		cargasWs.setTotalPaginas(numTotalPaginas);
		cargasWs.setUsuModificacion(1);
		cargasWs.setConErrores((short) 0);

		if (cargasWsMapper.insert(cargasWs) != 1) {
			throw new BusinessException("No se ha insertado correctamente el xml " + numeroPeticion);
		}
		return cargasWs;
	}

	public CargasWsPagina insertarPagina(short numPagina, Long idCarga, Long idXmlReq) throws BusinessException {

		CargasWsPagina pagina = new CargasWsPagina();

		pagina.setIdCarga(idCarga);
		pagina.setIdDatosXml(idXmlReq);
		pagina.setFechaCreacion(new Date());
		pagina.setFechaPeticion(new Date());
		pagina.setNumPagina(numPagina);
		pagina.setCodError(null);
		if (cargasWsPaginaMapper.insert(pagina) != 1) {
			throw new BusinessException(
					"Error al insertar la página con idPagina:" + numPagina + "para la carga: " + idCarga);
		}
		LOGGER.debug("Insertada una pagina de carga");
		return pagina;
	}

	public Long guardarXML(String xml, Long idIn, Short tipo, Short modulo) {
		// IDiN : identificador de la peticion, en este caso a nulo
		// tipo: enumerado tipo operacion
		// modulo: enumerado modulos
		CmnDatosXml datos = new CmnDatosXml();

		datos.setXml(xml);
		datos.setModulo(modulo);
		datos.setTipoXml(tipo);
		datos.setFecha(Calendar.getInstance().getTime());

		if (idIn != null && idIn > 0) {
			datos.setIdDatosXmlRel(idIn);
		}
		LOGGER.info("preparándonos para insertar");
		return guardarXmlDaoImpl.insertaXML(datos);

	}

	public static byte[] zipBytes(String filename1, byte[] input1, String filename2, byte[] input2) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);
		if (input1 != null) {
			ZipEntry entry1 = new ZipEntry(filename1);
			entry1.setSize(input1.length);
			zos.putNextEntry(entry1);
			zos.write(input1);
			zos.closeEntry();
		}
		if (input2 != null) {
			ZipEntry entry2 = new ZipEntry(filename2);
			entry2.setSize(input2.length);
			zos.putNextEntry(entry2);
			zos.write(input2);
			zos.closeEntry();
		}
		zos.close();
		return baos.toByteArray();
	}

	
	
	
	/**
	 * Crea un boolean sacando el valor de un numero (si es 1 true, sino false)
	 * @param busqueda
	 * @return
	 */
	public static boolean createBooleanFromNumber(Number busqueda) {
		if (busqueda != null && busqueda.intValue() == 1){
			return true;
		}
		return false;
	}

	/**
	 * Devuelve un entero (1 o 0) dependiendo del valor de un boolean (true o false)
	 * @param busqueda
	 * @return
	 */
	public static short createShortFromBoolean(boolean bool) {
		return (short) (bool ? 1 : 0);
	}
	
	
	public RegistroSociedad[] cargarSociedades(GetListaSociedadesRequest peticion, GetListaSociedadesResponse respuesta, Long idWsPagina, Short idInstitucion) {

		//Nos disponemos a consultar los datos de las sociedades que han sufrido modificacion
		try {
			List<RegistroSociedad> registrosList = new ArrayList<RegistroSociedad>();
			//Primero consultamos las sociedades que han sido dadas de baja
			//List<NewIdDTO> idPersonas = cenNoColegiado.selectSociedadesEditadas(idInstitucion,peticion.getFechaDesde(),peticion.getFechaHasta());
			
			tamanoPaginacion = peticion.getNumElementosPagina();
			List<SociedadesBajaDTO> sociedadesEnBaja = cenNoColegiado.selectSociedadesEliminadas(idInstitucion,peticion.getFechaDesde().getTime(),peticion.getFechaHasta().getTime());
			
			List<SociedadesBajaDTO> sociedadesResult = new ArrayList<>();
			
			short totalPaginas = 0;
			if (sociedadesEnBaja.size() == 0) {
				LOGGER.info("No se ha encontrado ninguna sociedad con los filtros seleccionados");
			} else {
				if (peticion.getConPaginacion()) {
					if (sociedadesEnBaja.size() >= peticion.getNumPagina() * tamanoPaginacion) {
						sociedadesResult = sociedadesEnBaja.subList((peticion.getNumPagina() - 1) * tamanoPaginacion,
								(peticion.getNumPagina() * tamanoPaginacion));
					} else {
						sociedadesResult = sociedadesEnBaja.subList((peticion.getNumPagina() - 1) * tamanoPaginacion,
								sociedadesEnBaja.size());
					}
					
					// Se calcula el numero total de paginas
					totalPaginas = (short) (sociedadesResult.size() / tamanoPaginacion);
					if (sociedadesResult.size() % tamanoPaginacion > 0) {
						totalPaginas++;
					}
					respuesta.setNumTotalPaginas(totalPaginas);
					
				}else{
					sociedadesResult.addAll(sociedadesEnBaja);
				}
			}
			if (null != sociedadesResult && sociedadesResult.size()>0) {
				for (SociedadesBajaDTO sociedadBajaDTO : sociedadesResult) {
					
					RegistroSociedad registro = RegistroSociedad.Factory.newInstance();
					SociedadBaja sociedadBaja = SociedadBaja.Factory.newInstance();
					sociedadBaja.setCIFNIF(sociedadBajaDTO.getNif());
					sociedadBaja.setFechaBajaColegio(UtilidadesString.toCalendar(sociedadBajaDTO.getFechaBaja()));
					registro.setSociedadBaja(sociedadBaja );
					registrosList.add(registro);
				}
			}
				List<SociedadesEditadasDTO> sociedadesEditadas = cenNoColegiado.selectSociedadesEditar(idInstitucion,peticion.getFechaDesde().getTime(),peticion.getFechaHasta().getTime());
				if (null != sociedadesEditadas && sociedadesEditadas.size()>0) {
					List<SociedadActualizacion> sociedadesEditadasFinal = new ArrayList<SociedadActualizacion>();
					for (SociedadesEditadasDTO regSociedad : sociedadesEditadas) {
						SociedadActualizacion sociedadActualizacion = SociedadActualizacion.Factory.newInstance();
						
						boolean argPublicar = Boolean.FALSE;

						sociedadActualizacion.setPublicar(argPublicar);
						Resena argResena = Resena.Factory.newInstance();
						if(regSociedad.getResena()!=null){
							if(regSociedad.getResena().length()>100){
								argResena.setStringValue(regSociedad.getResena().substring(0, 99));
							}else{
								argResena.setStringValue(regSociedad.getResena());
							}
						}
						sociedadActualizacion.setResena(argResena);
						if (null != regSociedad.getObjetoSocial()) {
							if(regSociedad.getObjetoSocial().length()>=20){
								sociedadActualizacion.setObjetoSocial(regSociedad.getObjetoSocial().substring(0, 20));
							}else{
								sociedadActualizacion.setObjetoSocial(regSociedad.getObjetoSocial());
							}
						}
						
						//Insertamos los datos del registro
						DatosRegistro argRegistro = DatosRegistro.Factory.newInstance();
						argRegistro.setFechaCancelacion(UtilidadesString.toCalendar(regSociedad.getFechaCancelacion()));
						argRegistro.setFechaInscripcion(UtilidadesString.toCalendar(regSociedad.getFechaInscripcion()));
						argRegistro.setIdentificacionRegistro(regSociedad.getIdentificacionRegistro());
						argRegistro.setNumeroRegistro(regSociedad.getNumeroRegistro());
						sociedadActualizacion.setDatosRegistro(argRegistro);
						
						DatosPersona argNotario = DatosPersona.Factory.newInstance();
						//Insertamos el Notario
						Identificacion identificacion = Identificacion.Factory.newInstance();
						if(regSociedad.getIdentificacionNotario()!=null){
							identificacion.setNIF(regSociedad.getIdentificacionNotario());
							argNotario.setApellido1(regSociedad.getApellido1Notario());
							argNotario.setApellido2(regSociedad.getApellido2Notario());
							argNotario.setNombre(regSociedad.getNombreNotario());
						}
						argNotario.setIdentificacion(identificacion);
						sociedadActualizacion.setDatosNotario(argNotario);
						
						
						//Insertamos los datos de la sociedad
						DatosEntidad argSociedad = DatosEntidad.Factory.newInstance();
						argSociedad.setCIFNIF(regSociedad.getSociedadNif());
						argSociedad.setDenominacion(regSociedad.getSociedadDenominacion());
						FormaSocial formaSocial = FormaSocial.Factory.newInstance();
						if(regSociedad.getSociedadFormaSocial() != null){
							if(regSociedad.getSociedadFormaSocial().length()>=20){
								formaSocial.setStringValue(regSociedad.getSociedadFormaSocial().substring(0, 20));
							}else{
								formaSocial.setStringValue(regSociedad.getSociedadFormaSocial());
							}
						}
						argSociedad.setFormaSocial(formaSocial);
						sociedadActualizacion.setDatosSociedad(argSociedad);
						sociedadActualizacion.setFechaAlta(UtilidadesString.toCalendar(regSociedad.getSociedadFechaAlta()));
						sociedadActualizacion.setFechaConstitucion(UtilidadesString.toCalendar(regSociedad.getFechaConstitucion()));
						sociedadActualizacion.setFechaFin(UtilidadesString.toCalendar(regSociedad.getFechaFin()));
						//sociedadActualizacion.setObjetoSocial(regSociedad.getObjetoSocial());
						//Insertamos los datos de la direccion
						Direccion argDireccion = Direccion.Factory.newInstance();
						argDireccion.setDomicilio(regSociedad.getDomicilio());
						argDireccion.setCodigoPostal(regSociedad.getCodigoPostal());
						//Provincia
						Provincia provincia = Provincia.Factory.newInstance();
						provincia.setDescripcionProvincia(regSociedad.getProvincia());
						provincia.setCodigoProvincia(regSociedad.getCodigoProvincia());
						argDireccion.setProvincia(provincia);
						//Poblacion
						Poblacion poblacion = Poblacion.Factory.newInstance();
						poblacion.setDescripcionPoblacion(regSociedad.getPoblacion());
						poblacion.setCodigoPoblacion(regSociedad.getCodigoPoblacion());
						argDireccion.setPoblacion(poblacion);
						//Correo electronico
						CorreoElectronico cElectronico = CorreoElectronico.Factory.newInstance();
						cElectronico.setStringValue(regSociedad.getCorreoElectronico());
						cElectronico.setPublicar(Boolean.FALSE);
						argDireccion.setCorreoElectronico(cElectronico);
						int contador =0;
						int contadorContacto = 0;
						boolean telefono = false;
						boolean movil = false;
						boolean faxB = false;
						//contactos
						if(regSociedad.getTelefono1()!=null){
							contador++;
							telefono = true;
						}
						if(regSociedad.getMovil()!=null){
							contador++;
							movil = true;
						}
						if(regSociedad.getFax1()!=null){
							contador++;
							faxB = true;
						}
						Contacto[] contactosArray = null;
						if(contador>0){
							contactosArray = new Contacto[contador];
								if(telefono){
									Contacto contacto1 = Contacto.Factory.newInstance();
									Telefono telefono1 = Telefono.Factory.newInstance();
									telefono1.setPublicar(Boolean.FALSE);
									telefono1.setStringValue(regSociedad.getTelefono1());
									contacto1.setTelefono(telefono1);
									contactosArray[contadorContacto++] = contacto1;
								}if(movil){
									Contacto contacto1 = Contacto.Factory.newInstance();
									TelefonoMovil movil1 = TelefonoMovil.Factory.newInstance();
									movil1.setPublicar(Boolean.FALSE);
									movil1.setStringValue(regSociedad.getMovil());
									contacto1.setTelefonoMovil(movil1);
									contactosArray[contadorContacto++] = contacto1;
								}if(faxB){
									Contacto contacto1 = Contacto.Factory.newInstance();
									Fax fax = Fax.Factory.newInstance();
									fax.setPublicar(Boolean.FALSE);
									fax.setStringValue(regSociedad.getFax1());
									contacto1.setFax(fax);
									contactosArray[contadorContacto++] = contacto1;
								}
						}
						
						argDireccion.setPaginaWeb(regSociedad.getPaginaWeb());
						argDireccion.setContactoArray(contactosArray);
						argDireccion.setPublicar(Boolean.FALSE);
						sociedadActualizacion.setDireccion(argDireccion);
						
						//INSERTAMOS LOS DATOS DE LOS INTEGRANTES
						DatosIntegrantesSearchDTO datosIntegrantesSearchDTO = new DatosIntegrantesSearchDTO();
						datosIntegrantesSearchDTO.setIdPersona(regSociedad.getIdPersona());
						List<DatosIntegrantesWS> datosIntegrantesWS = cenComponentesExtendsMapper.selectIntegrantesWS(datosIntegrantesSearchDTO,idInstitucion.toString());
						if (null != datosIntegrantesWS && datosIntegrantesWS.size()>0) {
							IntegranteSociedad[] integrantesSociedad = new IntegranteSociedad[datosIntegrantesWS.size()];
							int i = 0;
							for (DatosIntegrantesWS integrante : datosIntegrantesWS) {
								IntegranteSociedad integranteUnitario = IntegranteSociedad.Factory.newInstance();
								SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
								if (integrante.getPersonaJuridica().equals("0")) {
									
									IntegranteFisico integranteFisico = IntegranteFisico.Factory.newInstance();
									DatosCargo cargo = DatosCargo.Factory.newInstance();
									if(integrante.getCargo() != null){
										cargo.setCargo(integrante.getCargo());
										cargo.setDescCargo(integrante.getDescripcionCargo());
										if (!UtilidadesString.esCadenaVacia(integrante.getFechaCargo())) {
											Date fechaCargo = dateFormat.parse(integrante.getFechaCargo());
											cargo.setFechaCargo(UtilidadesString.toCalendar(fechaCargo));
										}
									}
									if(integrante.getSocio()=="1"){
										integranteFisico.setSocio(Boolean.TRUE);
									}else{
										integranteFisico.setSocio(Boolean.FALSE);
									}
									integranteFisico.setDatosCargo(cargo);
									DatosPersona datosPersona = DatosPersona.Factory.newInstance();
									datosPersona.setApellido1(integrante.getApellidos1());
									datosPersona.setApellido2(integrante.getApellidos2());
									datosPersona.setNombre(integrante.getNombre());
									identificacion.setNIF(integrante.getNifCif());
									datosPersona.setIdentificacion(identificacion);
									integranteFisico.setDatosPersona(datosPersona);
									DatosProfesional datosProfesional = DatosProfesional.Factory.newInstance();
									Colegio colegio = Colegio.Factory.newInstance();
									colegio.setCodigoColegio(integrante.getCodigocolegio());
									colegio.setDescripcionColegio(integrante.getDescripcionColegio());
									if(integrante.getProfesionalAbogado().equals("1")){
										ProfesionalAbogado profesionalAbogado =  ProfesionalAbogado.Factory.newInstance();
										profesionalAbogado.setColegio(colegio);
										profesionalAbogado.setNumColegiado(integrante.getNumColegiado());
										datosProfesional.setProfesionalAbogado(profesionalAbogado);
									}else{
										Profesional profesional = Profesional.Factory.newInstance();
										if(integrante.getCodigocolegio()!=null){
											profesional.setColegio(colegio);
										}else{
											profesional.setNombreColegio(integrante.getDescripcionColegio());
										}
										profesional.setNumColegiado(integrante.getNumColegiado());
										if(integrante.getProfesion()!= null){
											if(integrante.getProfesion().length()>20){
												profesional.setProfesion(integrante.getProfesion().substring(0, 19));
											}else{
												profesional.setProfesion(integrante.getProfesion());
											}
										}
										datosProfesional.setProfesional(profesional);
									}
									integranteFisico.setDatosProfesional(datosProfesional);
									integranteUnitario.setIntegranteFisico(integranteFisico);
									integranteUnitario.setFechaModificacion(UtilidadesString.toCalendar(integrante.getFechaModificacion()));
									integranteUnitario.setPublicar(Boolean.FALSE);
								}else{
									IntegranteJuridico integranteJuridico = IntegranteJuridico.Factory.newInstance();
									DatosCargo cargoJuridico = DatosCargo.Factory.newInstance();
									cargoJuridico.setCargo(integrante.getCargo());
									
									cargoJuridico.setDescCargo(integrante.getDescripcionCargo());
									if(integrante.getFechaCargo()!=null){
										if (!UtilidadesString.esCadenaVacia(integrante.getFechaCargo().toString())) {
											Date fechaCargoJuridico = dateFormat.parse(integrante.getFechaCargo().toString());
											cargoJuridico.setFechaCargo(UtilidadesString.toCalendar(fechaCargoJuridico));
										}
									}
									integranteJuridico.setDatosCargo(cargoJuridico);
									integranteUnitario.setFechaModificacion(UtilidadesString.toCalendar(integrante.getFechaModificacion()));
									integranteUnitario.setPublicar(Boolean.FALSE);
									DatosEntidad datosEntidad = DatosEntidad.Factory.newInstance();
									datosEntidad.setCIFNIF(integrante.getNifCif());
									datosEntidad.setDenominacion(integrante.getNombre());
									integranteJuridico.setDatosEntidad(datosEntidad);
									integranteUnitario.setIntegranteJuridico(integranteJuridico);
								}
								integrantesSociedad[i] = integranteUnitario;
								i++;
							}
							
							sociedadActualizacion.setIntegranteSociedadArray(integrantesSociedad);
						}
						
						
						
						
						sociedadesEditadasFinal.add(sociedadActualizacion);
		
					}
					List<SociedadActualizacion> sociedadesEditadasResult = new ArrayList<>();
					
					if (sociedadesEditadasFinal.size() == 0) {
						LOGGER.info("No se ha encontrado ninguna sociedad con los filtros seleccionados");
						respuesta.setNumTotalPaginas(totalPaginas);
					} else {
						if (peticion.getConPaginacion()) {
							if (sociedadesEditadasFinal.size() >= peticion.getNumPagina() * tamanoPaginacion) {
								sociedadesEditadasResult = sociedadesEditadasFinal.subList((peticion.getNumPagina() - 1) * tamanoPaginacion,
										(peticion.getNumPagina() * tamanoPaginacion));
							} else {
								sociedadesEditadasResult = sociedadesEditadasFinal.subList((peticion.getNumPagina() - 1) * tamanoPaginacion,
										sociedadesEditadasFinal.size());
							}
							
							// Se calcula el numero total de paginas
							totalPaginas = (short) (sociedadesEditadas.size() / tamanoPaginacion);
							if (sociedadesEditadas.size() % tamanoPaginacion > 0) {
								totalPaginas++;
							}
							respuesta.setNumTotalPaginas(totalPaginas);
							
						}else{
							sociedadesEditadasResult.addAll(sociedadesEditadasFinal);
						}
					}
					try{
						if (null != sociedadesEditadasResult && sociedadesEditadasResult.size()>0) {
							
							for (SociedadActualizacion sociedadActualizacion : sociedadesEditadasResult) {

								RegistroSociedad registro = RegistroSociedad.Factory.newInstance();
								registro.setSociedadActualizacion(sociedadActualizacion);

								registrosList.add(registro);
								

							}
						}
					}catch(AssertionError e){
						LOGGER.info("Excepcion añadiendo los registros en el nodo RegistroSociedad: " + e.getMessage());
					}
					
				}
				
			respuesta.setNumTotalPaginas(totalPaginas);
			if (null != registrosList && registrosList.size()>0) {
				RegistroSociedad[] registrosReturn = new RegistroSociedad[registrosList.size()];
				int i = 0;
				for (RegistroSociedad registros : registrosList) {
					registrosReturn[i] = registros;
					
					i++;
				}
				return registrosReturn;
			}else {
				return new RegistroSociedad[0];
			}

		} catch (Exception e) {
			throw new BusinessException("Error al almacenar los datos de la sociedad", e);
		}
		
		
	
	}
	
	
	public List<CenInstitucion> getidInstitucionByCodExterno(String codExterno) {
		CenInstitucionExample example = new CenInstitucionExample();
		example.createCriteria().andCodigoextEqualTo(codExterno);
		
		return cenInstitucionMapper.selectByExample(example);
	}
	
	public List<CenInstitucion> getCodExternoByidInstitucion(String idInstitucion) {
		CenInstitucionExample example = new CenInstitucionExample();
		example.createCriteria().andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

		return cenInstitucionMapper.selectByExample(example);
	}

}
