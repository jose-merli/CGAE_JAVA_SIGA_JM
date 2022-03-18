package org.itcgae.siga.services.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.transaction.Transaction;

import com.exea.sincronizacion.redabogacia.*;
import org.apache.xmlbeans.XmlObject;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.DatosDireccionesSearchDTO;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesSearchDTO;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesWS;
import org.itcgae.siga.DTOs.cen.SociedadesBajaDTO;
import org.itcgae.siga.DTOs.cen.SociedadesEditadasDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.com.DatosDocumentoItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.constants.SigaConstants.ERROR_SERVER;
import org.itcgae.siga.commons.constants.SigaConstants.ESTADO_CARGAS;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.commons.utils.Validaciones;
import org.itcgae.siga.db.entities.AdmConfig;
import org.itcgae.siga.db.entities.AdmConfigExample;
import org.itcgae.siga.db.entities.CargasWs;
import org.itcgae.siga.db.entities.CargasWsPagina;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteKey;
import org.itcgae.siga.db.entities.CenColegiadoKey;
import org.itcgae.siga.db.entities.CenDatoscolegialesestado;
import org.itcgae.siga.db.entities.CenDatoscolegialesestadoExample;
import org.itcgae.siga.db.entities.CenDireccionTipodireccion;
import org.itcgae.siga.db.entities.CenDireccionTipodireccionExample;
import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.entities.CenDireccionesExample;
import org.itcgae.siga.db.entities.CenDireccionesKey;
import org.itcgae.siga.db.entities.CenHistorico;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenInstitucionExample;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CfgParamColegios;
import org.itcgae.siga.db.entities.CfgParamColegiosExample;
import org.itcgae.siga.db.entities.CmnDatosXml;
import org.itcgae.siga.db.entities.EcomCenDatos;
import org.itcgae.siga.db.entities.FcsPagoColegiado;
import org.itcgae.siga.db.entities.GenRecursosCatalogosKey;
import org.itcgae.siga.db.entities.GenRecursosKey;
import org.itcgae.siga.db.entities.ScsCabeceraguardias;
import org.itcgae.siga.db.mappers.AdmConfigMapper;
import org.itcgae.siga.db.mappers.CargasWsMapper;
import org.itcgae.siga.db.mappers.CargasWsPaginaMapper;
import org.itcgae.siga.db.mappers.CenDireccionTipodireccionMapper;
import org.itcgae.siga.db.mappers.CenInstitucionMapper;
import org.itcgae.siga.db.mappers.CenPaisMapper;
import org.itcgae.siga.db.mappers.CenPoblacionesMapper;
import org.itcgae.siga.db.mappers.CenProvinciasMapper;
import org.itcgae.siga.db.mappers.CfgParamColegiosMapper;
import org.itcgae.siga.db.mappers.GenRecursosCatalogosMapper;
import org.itcgae.siga.db.mappers.GenRecursosMapper;
import org.itcgae.siga.db.services.adm.mappers.CenHistoricoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenClienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenComponentesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDatoscolegialesestadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDireccionesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenInstitucionExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenNocolegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.GuardarXmlDaoImpl;
import org.itcgae.siga.db.services.ecom.mappers.EcomCenDatosExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsPagoColegiadoExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsCabeceraguardiasExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.exception.ValidationException;
import org.itcgae.siga.ws.fusionadorPersonas.GetFusionadorPersonasRequestDocument;
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
import org.itcgae.sspp.ws.registroSociedades.ProfesionalAbogadoPropioDocument.ProfesionalAbogadoPropio;
import org.itcgae.sspp.ws.registroSociedades.ProfesionalDocument.Profesional;
import org.itcgae.sspp.ws.registroSociedades.RegistroSociedadDocument.RegistroSociedad;
import org.itcgae.sspp.ws.registroSociedades.SociedadActualizacionDocument.SociedadActualizacion;
import org.itcgae.sspp.ws.registroSociedades.SociedadActualizacionDocument.SociedadActualizacion.Resena;
import org.itcgae.sspp.ws.registroSociedades.SociedadBajaDocument.SociedadBaja;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.wsdl.util.StringUtils;

@Service
@Transactional
public class WSCommons {

	private static final Logger LOGGER = LoggerFactory.getLogger(WSCommons.class);

	@Autowired
	CfgParamColegiosMapper cfgParamColegiosMapper;
	@Autowired
	CenInstitucionMapper cenInstitucionMapper;

	@Autowired
	AdmConfigMapper admConfigMapper;

	@Autowired
	CargasWsPaginaMapper cargasWsPaginaMapper;
	@Autowired
	CargasWsMapper cargasWsMapper;

	@Autowired
	GuardarXmlDaoImpl guardarXmlDaoImpl;

	@Autowired
	CenNocolegiadoExtendsMapper cenNoColegiado;

	@Autowired
	private CenDireccionesExtendsMapper cenDireccionesExtendsMapper;
	
	@Autowired
	private CenDatoscolegialesestadoExtendsMapper cenDatoscolegialesestadoExtendsMapper;

	@Autowired
	private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;

	@Autowired
	private CenDatoscolegialesestadoExtendsMapper cenDatosColegialesEstadoExtendsMapper;

	@Autowired
	private CenClienteExtendsMapper cenClienteExtendsMapper;

	@Autowired
	private CenPersonaExtendsMapper cenPersonaExtendsMapper;

	@Autowired
	private CenInstitucionExtendsMapper cenInstitucionExtendsMapper;

	@Autowired
	private CenComponentesExtendsMapper cenComponentesExtendsMapper;

	@Autowired
	private EcomCenDatosExtendsMapper ecomCenDatosExtendsMapper;

	@Autowired
	private CenDireccionTipodireccionMapper cenTipoDireccionMapper;

	@Autowired
	private GenRecursosCatalogosMapper genRecursosCatalogosMapper;

	@Autowired
	private GenRecursosMapper genRecursosMapper;

	@Autowired
	private CenHistoricoExtendsMapper cenHistoricoMapper;

	@Autowired
	private CenPaisMapper cenPaisMapper;

	@Autowired
	private CenPoblacionesMapper cenPoblacionesMapper;

	@Autowired
	private CenProvinciasMapper cenProvinciasMapper;

	@Autowired
	private FcsPagoColegiadoExtendsMapper fcsPagoColegiadoMapper;

	@Autowired
	private ScsCabeceraguardiasExtendsMapper scsCabeceraguardiasMapper;

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
		}else if(xmlObjectResponse instanceof ObtenerNumColegiacionResponseDocument.ObtenerNumColegiacionResponse){

			com.exea.sincronizacion.redabogacia.ErrorType errorType = ((ObtenerNumColegiacionResponseDocument.ObtenerNumColegiacionResponse) xmlObjectResponse).addNewError();

			if(ERROR_SERVER.XML_NO_VALIDO.name().equals(codigo)) {
				errorType.setCodigo(SigaConstants.ERROR_SINCRONIZACION_EXEA.FORMATO_NOVALIDO.name());
				errorType.setDescripcion(message);
			}else if(ERROR_SERVER.CLI_IP_NO_ENCONTRADA.name().equals(codigo)){
				errorType.setCodigo(SigaConstants.ERROR_SINCRONIZACION_EXEA.IP_NOVALIDA.name());
				errorType.setDescripcion(SigaConstants.ERROR_SINCRONIZACION_EXEA.IP_NOVALIDA.getMensajeError());
			}

			if(xmlObjectRequest != null){
				errorType.setXmlRequest(xmlObjectRequest.xmlText());
			}

		}else if(xmlObjectResponse instanceof AltaColegiadoResponseDocument.AltaColegiadoResponse){

			com.exea.sincronizacion.redabogacia.ErrorType errorType = ((AltaColegiadoResponseDocument.AltaColegiadoResponse) xmlObjectResponse).addNewError();

			if(ERROR_SERVER.XML_NO_VALIDO.name().equals(codigo)) {
				errorType.setCodigo(SigaConstants.ERROR_SINCRONIZACION_EXEA.FORMATO_NOVALIDO.name());
				errorType.setDescripcion(message);
			}else if(ERROR_SERVER.CLI_IP_NO_ENCONTRADA.name().equals(codigo)){
				errorType.setCodigo(SigaConstants.ERROR_SINCRONIZACION_EXEA.IP_NOVALIDA.name());
				errorType.setDescripcion(SigaConstants.ERROR_SINCRONIZACION_EXEA.IP_NOVALIDA.getMensajeError());
			}
			if(xmlObjectRequest != null){
				errorType.setXmlRequest(xmlObjectRequest.xmlText());
			}

		}else if(xmlObjectResponse instanceof AltaSancionResponseDocument.AltaSancionResponse){

			com.exea.sincronizacion.redabogacia.ErrorType errorType = ((AltaSancionResponseDocument.AltaSancionResponse) xmlObjectResponse).addNewError();

			if(ERROR_SERVER.XML_NO_VALIDO.name().equals(codigo)) {
				errorType.setCodigo(SigaConstants.ERROR_SINCRONIZACION_EXEA.FORMATO_NOVALIDO.name());
				errorType.setDescripcion(message);
			}else if(ERROR_SERVER.CLI_IP_NO_ENCONTRADA.name().equals(codigo)){
				errorType.setCodigo(SigaConstants.ERROR_SINCRONIZACION_EXEA.IP_NOVALIDA.name());
				errorType.setDescripcion(SigaConstants.ERROR_SINCRONIZACION_EXEA.IP_NOVALIDA.getMensajeError());
			}

			if(xmlObjectRequest != null){
				errorType.setXmlRequest(xmlObjectRequest.xmlText());
			}

		}else if(xmlObjectResponse instanceof UpdateEstadoExpedienteResponseDocument.UpdateEstadoExpedienteResponse){

			com.exea.sincronizacion.redabogacia.ErrorType errorType = ((UpdateEstadoExpedienteResponseDocument.UpdateEstadoExpedienteResponse) xmlObjectResponse).addNewError();
			if(ERROR_SERVER.XML_NO_VALIDO.name().equals(codigo)) {
				errorType.setCodigo(SigaConstants.ERROR_SINCRONIZACION_EXEA.FORMATO_NOVALIDO.name());
				errorType.setDescripcion(message);
			}else if(ERROR_SERVER.CLI_IP_NO_ENCONTRADA.name().equals(codigo)){
				errorType.setCodigo(SigaConstants.ERROR_SINCRONIZACION_EXEA.IP_NOVALIDA.name());
				errorType.setDescripcion(SigaConstants.ERROR_SINCRONIZACION_EXEA.IP_NOVALIDA.getMensajeError());
			}

			if(xmlObjectRequest != null){
				errorType.setXmlRequest(xmlObjectRequest.xmlText());
			}

		}else if(xmlObjectResponse instanceof ActualizacionSancionResponseDocument.ActualizacionSancionResponse){

			com.exea.sincronizacion.redabogacia.ErrorType errorType = ((ActualizacionSancionResponseDocument.ActualizacionSancionResponse) xmlObjectResponse).addNewError();
			if(ERROR_SERVER.XML_NO_VALIDO.name().equals(codigo)) {
				errorType.setCodigo(SigaConstants.ERROR_SINCRONIZACION_EXEA.FORMATO_NOVALIDO.name());
				errorType.setDescripcion(message);
			}else if(ERROR_SERVER.CLI_IP_NO_ENCONTRADA.name().equals(codigo)){
				errorType.setCodigo(SigaConstants.ERROR_SINCRONIZACION_EXEA.IP_NOVALIDA.name());
				errorType.setDescripcion(SigaConstants.ERROR_SINCRONIZACION_EXEA.IP_NOVALIDA.getMensajeError());
			}

			if(xmlObjectRequest != null){
				errorType.setXmlRequest(xmlObjectRequest.xmlText());
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
		LOGGER.info(
				"Se ha recibido una llamada desde la IP " + endpointReference + " para el colegio " + idInstitucion);
		String valor = obtenerValorParametroColegio(idInstitucion, nombreParametroIP);
		String[] valores = null;
		if (valor != null) {
			valores = valor.split(",");
		}
		// Para probar las sociedades
		// valor = "127.0.0.1";
		if (valores == null || !validarIPs(endpointReference, valores)) {
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
		// Solo para probar
		// valor = "1";
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

	public static File zipBytes(List<DatosDocumentoItem> ficheros, File fileZip) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);
		List<String> listaNombreFicherosZip = new ArrayList<String>();

		for (DatosDocumentoItem fichero : ficheros) {
			if (fichero != null && fichero.getFileName() != null) {
				String nombreFichero = fichero.getFileName();

				if (listaNombreFicherosZip.contains(nombreFichero)) {
					// Quitamos la extension
					int indice = nombreFichero.lastIndexOf(".");
					if (indice > -1) {
						String extension = nombreFichero.substring(indice, nombreFichero.length());
						String nombreSinExtension = nombreFichero.substring(0, nombreFichero.lastIndexOf("."));

						// Le añadimos un timestamp
						nombreFichero = nombreSinExtension + "_" + System.currentTimeMillis() + extension;
					} else {
						nombreFichero = nombreFichero + "_" + System.currentTimeMillis();
					}
				}

				ZipEntry entry = new ZipEntry(nombreFichero);
				listaNombreFicherosZip.add(nombreFichero);

				entry.setSize(fichero.getDatos().length);
				zos.putNextEntry(entry);
				zos.write(fichero.getDatos());
				zos.closeEntry();
			}
		}
		zos.close();

		FileOutputStream fos = new FileOutputStream(fileZip);
		fos.write(baos.toByteArray());
		fos.flush();
		fos.close();

		return fileZip;

	}
	
	public static File fileBytes(List<DatosDocumentoItem> ficheros, String path) throws IOException {
		File file = new File(path);
		FileOutputStream fos = new FileOutputStream(file);
		boolean closed = false;
		
		try {
			fos.write(ficheros.get(0).getDatos());
			fos.flush();
			
			fos.close();
			closed = true;
		} finally {
			if (!closed) {
				fos.close();
			}
		}
		
		return file;
	}
	
	public static File fileBytes(byte[] datos, String path) throws IOException {
		File file = new File(path);
		FileOutputStream fos = new FileOutputStream(file);
		boolean closed = false;
		
		try {
			fos.write(datos);
			fos.flush();
			
			fos.close();
			closed = true;
		} finally {
			if (!closed) {
				fos.close();
			}
		}
		
		return file;
	}
	
	/**
	 * Crea un boolean sacando el valor de un numero (si es 1 true, sino false)
	 * 
	 * @param busqueda
	 * @return
	 */
	public static boolean createBooleanFromNumber(Number busqueda) {
		if (busqueda != null && busqueda.intValue() == 1) {
			return true;
		}
		return false;
	}

	/**
	 * Devuelve un entero (1 o 0) dependiendo del valor de un boolean (true o false)
	 * 
	 * @param busqueda
	 * @return
	 */
	public static short createShortFromBoolean(boolean bool) {
		return (short) (bool ? 1 : 0);
	}

	public RegistroSociedad[] cargarSociedades(GetListaSociedadesRequest peticion, GetListaSociedadesResponse respuesta,
			Long idWsPagina, Short idInstitucion) {

		// Nos disponemos a consultar los datos de las sociedades que han sufrido
		// modificacion
		try {
			List<RegistroSociedad> registrosList = new ArrayList<RegistroSociedad>();
			// Primero consultamos las sociedades que han sido dadas de baja
			// List<NewIdDTO> idPersonas =
			// cenNoColegiado.selectSociedadesEditadas(idInstitucion,peticion.getFechaDesde(),peticion.getFechaHasta());

			tamanoPaginacion = peticion.getNumElementosPagina();
			List<SociedadesBajaDTO> sociedadesEnBaja = cenNoColegiado.selectSociedadesEliminadas(idInstitucion,
					peticion.getFechaDesde().getTime(), peticion.getFechaHasta().getTime());

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

				} else {
					sociedadesResult.addAll(sociedadesEnBaja);
					respuesta.setNumTotalPaginas((short) 1);
				}

				// Se calcula el numero total de paginas
				totalPaginas = (short) (sociedadesResult.size() / tamanoPaginacion);
				if (sociedadesResult.size() % tamanoPaginacion > 0) {
					totalPaginas++;
				}
			}

			if (null != sociedadesResult && sociedadesResult.size() > 0) {
				for (SociedadesBajaDTO sociedadBajaDTO : sociedadesResult) {

					RegistroSociedad registro = RegistroSociedad.Factory.newInstance();
					SociedadBaja sociedadBaja = SociedadBaja.Factory.newInstance();
					sociedadBaja.setCIFNIF(sociedadBajaDTO.getNif());
					sociedadBaja.setFechaBajaColegio(UtilidadesString.toCalendar(sociedadBajaDTO.getFechaBaja()));
					if (validaSociedadBaja(sociedadBaja)) {
						registro.setSociedadBaja(sociedadBaja );
						registrosList.add(registro);
					}
				}
			}
				List<SociedadesEditadasDTO> sociedadesEditadas = cenNoColegiado.selectSociedadesEditar(idInstitucion,peticion.getFechaDesde().getTime(),peticion.getFechaHasta().getTime());
				if (null != sociedadesEditadas && sociedadesEditadas.size()>0) {
					List<SociedadActualizacion> sociedadesEditadasFinal = new ArrayList<SociedadActualizacion>();
					for (SociedadesEditadasDTO regSociedad : sociedadesEditadas) {
						SociedadActualizacion sociedadActualizacion = SociedadActualizacion.Factory.newInstance();
						
						boolean argPublicar = Boolean.TRUE;

						sociedadActualizacion.setPublicar(argPublicar);
						Resena argResena = Resena.Factory.newInstance();
						if(regSociedad.getResena()!=null){
							if(regSociedad.getResena().length()>100){
								argResena.setStringValue(regSociedad.getResena().substring(0, 99));
							}else{
								argResena.setStringValue(regSociedad.getResena());
							}
						}
						//sociedadActualizacion.setResena(argResena);
						if(argResena.getStringValue().length() >= 3) {
							sociedadActualizacion.setResena(argResena.getStringValue());
						}
						if (null != regSociedad.getObjetoSocial()) {
							if(regSociedad.getObjetoSocial().length()>=500){
								sociedadActualizacion.setObjetoSocial(regSociedad.getObjetoSocial().substring(0, 500));
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
							if (Validaciones.validaNIE(regSociedad.getIdentificacionNotario())){
								identificacion.setNIE(regSociedad.getIdentificacionNotario());
							} else if (Validaciones.validaNIF(regSociedad.getIdentificacionNotario())) {
								identificacion.setNIF(regSociedad.getIdentificacionNotario());
							}
							argNotario.setApellido1(regSociedad.getApellido1Notario());
							argNotario.setApellido2(regSociedad.getApellido2Notario());
							argNotario.setNombre(regSociedad.getNombreNotario());
						}
						if(identificacion.getNIF() != null) {
							if(!identificacion.getNIF().isEmpty()) {
								argNotario.setIdentificacion(identificacion);
							}
						} else if (identificacion.getNIE() != null) {
							if(!identificacion.getNIE().isEmpty()) {
								argNotario.setIdentificacion(identificacion);
							}
						}
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
						if(validarCodPostal(regSociedad.getCodigoPostal())) {
							argDireccion.setCodigoPostal(regSociedad.getCodigoPostal());
						}
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
						cElectronico.setPublicar(Boolean.TRUE);
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
					}

					// Insertamos los datos del registro
					DatosRegistro argRegistro = DatosRegistro.Factory.newInstance();
					argRegistro.setFechaCancelacion(UtilidadesString.toCalendar(regSociedad.getFechaCancelacion()));
					argRegistro.setFechaInscripcion(UtilidadesString.toCalendar(regSociedad.getFechaInscripcion()));
					argRegistro.setIdentificacionRegistro(regSociedad.getIdentificacionRegistro());
					argRegistro.setNumeroRegistro(regSociedad.getNumeroRegistro());
					sociedadActualizacion.setDatosRegistro(argRegistro);

					DatosPersona argNotario = DatosPersona.Factory.newInstance();
					// Insertamos el Notario
					Identificacion identificacion = Identificacion.Factory.newInstance();
					if (regSociedad.getIdentificacionNotario() != null) {
						identificacion.setNIF(regSociedad.getIdentificacionNotario());
						argNotario.setApellido1(regSociedad.getApellido1Notario());
						argNotario.setApellido2(regSociedad.getApellido2Notario());
						argNotario.setNombre(regSociedad.getNombreNotario());
					}
					argNotario.setIdentificacion(identificacion);
					sociedadActualizacion.setDatosNotario(argNotario);

					// Insertamos los datos de la sociedad
					DatosEntidad argSociedad = DatosEntidad.Factory.newInstance();
					argSociedad.setCIFNIF(regSociedad.getSociedadNif());
					argSociedad.setDenominacion(regSociedad.getSociedadDenominacion());
					FormaSocial formaSocial = FormaSocial.Factory.newInstance();
					if (regSociedad.getSociedadFormaSocial() != null) {
						if (regSociedad.getSociedadFormaSocial().length() >= 20) {
							formaSocial.setStringValue(regSociedad.getSociedadFormaSocial().substring(0, 20));
						} else {
							formaSocial.setStringValue(regSociedad.getSociedadFormaSocial());
						}
					}
					argSociedad.setFormaSocial(formaSocial);
					sociedadActualizacion.setDatosSociedad(argSociedad);
					sociedadActualizacion.setFechaAlta(UtilidadesString.toCalendar(regSociedad.getSociedadFechaAlta()));
					sociedadActualizacion
							.setFechaConstitucion(UtilidadesString.toCalendar(regSociedad.getFechaConstitucion()));
					sociedadActualizacion.setFechaFin(UtilidadesString.toCalendar(regSociedad.getFechaFin()));
					// sociedadActualizacion.setObjetoSocial(regSociedad.getObjetoSocial());
					// Insertamos los datos de la direccion
					Direccion argDireccion = Direccion.Factory.newInstance();
					argDireccion.setDomicilio(regSociedad.getDomicilio());
					argDireccion.setCodigoPostal(regSociedad.getCodigoPostal());
					// Provincia
					Provincia provincia = Provincia.Factory.newInstance();
					provincia.setDescripcionProvincia(regSociedad.getProvincia());
					provincia.setCodigoProvincia(regSociedad.getCodigoProvincia());
					argDireccion.setProvincia(provincia);
					// Poblacion
					Poblacion poblacion = Poblacion.Factory.newInstance();
					poblacion.setDescripcionPoblacion(regSociedad.getPoblacion());
					poblacion.setCodigoPoblacion(regSociedad.getCodigoPoblacion());
					argDireccion.setPoblacion(poblacion);
					// Correo electronico
					CorreoElectronico cElectronico = CorreoElectronico.Factory.newInstance();
					cElectronico.setStringValue(regSociedad.getCorreoElectronico());
					cElectronico.setPublicar(Boolean.FALSE);
					argDireccion.setCorreoElectronico(cElectronico);
					int contador = 0;
					int contadorContacto = 0;
					boolean telefono = false;
					boolean movil = false;
					boolean faxB = false;
					// contactos
					if (regSociedad.getTelefono1() != null) {
						contador++;
						telefono = true;
					}
					if (regSociedad.getMovil() != null) {
						contador++;
						movil = true;
					}
					if (regSociedad.getFax1() != null) {
						contador++;
						faxB = true;
					}
					Contacto[] contactosArray = null;
					if (contador > 0) {
						contactosArray = new Contacto[contador];
						if (telefono) {
							Contacto contacto1 = Contacto.Factory.newInstance();
							Telefono telefono1 = Telefono.Factory.newInstance();
							telefono1.setPublicar(Boolean.FALSE);
							telefono1.setStringValue(regSociedad.getTelefono1());
							contacto1.setTelefono(telefono1);
							contactosArray[contadorContacto++] = contacto1;
						}
						Contacto[] contactosArray = null;
						if(contador>0){
							contactosArray = new Contacto[contador];
								if(telefono){
									Contacto contacto1 = Contacto.Factory.newInstance();
									Telefono telefono1 = Telefono.Factory.newInstance();
									telefono1.setPublicar(Boolean.TRUE);
									telefono1.setStringValue(regSociedad.getTelefono1());
									contacto1.setTelefono(telefono1);
									contactosArray[contadorContacto++] = contacto1;
								}if(movil){
									Contacto contacto1 = Contacto.Factory.newInstance();
									TelefonoMovil movil1 = TelefonoMovil.Factory.newInstance();
									movil1.setPublicar(Boolean.TRUE);
									movil1.setStringValue(regSociedad.getMovil());
									contacto1.setTelefonoMovil(movil1);
									contactosArray[contadorContacto++] = contacto1;
								}if(faxB){
									Contacto contacto1 = Contacto.Factory.newInstance();
									Fax fax = Fax.Factory.newInstance();
									fax.setPublicar(Boolean.TRUE);
									fax.setStringValue(regSociedad.getFax1());
									contacto1.setFax(fax);
									contactosArray[contadorContacto++] = contacto1;
								}
						}
						
						argDireccion.setPaginaWeb(regSociedad.getPaginaWeb());
						argDireccion.setContactoArray(contactosArray);
						argDireccion.setPublicar(Boolean.TRUE);
						if(validarDireccion(argDireccion)) {
							sociedadActualizacion.setDireccion(argDireccion);
						}
					}

					argDireccion.setPaginaWeb(regSociedad.getPaginaWeb());
					argDireccion.setContactoArray(contactosArray);
					argDireccion.setPublicar(Boolean.FALSE);
					if (validarDireccion(argDireccion)) {
						sociedadActualizacion.setDireccion(argDireccion);
					}

					// INSERTAMOS LOS DATOS DE LOS INTEGRANTES
					DatosIntegrantesSearchDTO datosIntegrantesSearchDTO = new DatosIntegrantesSearchDTO();
					datosIntegrantesSearchDTO.setIdPersona(regSociedad.getIdPersona());
					List<DatosIntegrantesWS> datosIntegrantesWS = cenComponentesExtendsMapper
							.selectIntegrantesWS(datosIntegrantesSearchDTO, idInstitucion.toString());
					if (null != datosIntegrantesWS && datosIntegrantesWS.size() > 0) {
						IntegranteSociedad[] integrantesSociedad = new IntegranteSociedad[datosIntegrantesWS.size()];
						int i = 0;
						for (DatosIntegrantesWS integrante : datosIntegrantesWS) {
							IntegranteSociedad integranteUnitario = IntegranteSociedad.Factory.newInstance();
							SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
							if (integrante.getPersonaJuridica().equals("0")) {

								IntegranteFisico integranteFisico = IntegranteFisico.Factory.newInstance();
								DatosCargo cargo = DatosCargo.Factory.newInstance();
								if (integrante.getCargo() != null) {
									cargo.setCargo(integrante.getCargo());
									cargo.setDescCargo(integrante.getDescripcionCargo());
									if (!UtilidadesString.esCadenaVacia(integrante.getFechaCargo())) {
										Date fechaCargo = dateFormat.parse(integrante.getFechaCargo());
										cargo.setFechaCargo(UtilidadesString.toCalendar(fechaCargo));
									}
								}
								if ("1".equals(integrante.getSocio())) {
									integranteFisico.setSocio(Boolean.TRUE);
								} else {
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
								if (integrante.getProfesionalAbogado().equals("1")) {
									ProfesionalAbogado profesionalAbogado = ProfesionalAbogado.Factory.newInstance();
									profesionalAbogado.setColegio(colegio);
									profesionalAbogado.setNumColegiado(integrante.getNumColegiado());
									datosProfesional.setProfesionalAbogado(profesionalAbogado);
								} else {
									Profesional profesional = Profesional.Factory.newInstance();
									if (integrante.getCodigocolegio() != null) {
										profesional.setColegio(colegio);
									} else {
										profesional.setNombreColegio(integrante.getDescripcionColegio());
									}
									integranteFisico.setDatosCargo(cargo);
									DatosPersona datosPersona = DatosPersona.Factory.newInstance();
									datosPersona.setApellido1(integrante.getApellidos1());
									datosPersona.setApellido2(integrante.getApellidos2());
									datosPersona.setNombre(integrante.getNombre());
									// Nos aseguramos que no se utilice la identificación del integrante anterior
									identificacion = Identificacion.Factory.newInstance();
									/* Si el integrante no tiene NIF ni NIE, no se le añade la identificación
									 * NOTA IMPORTANTE: Para un profesionalAbogadoPropio SI es necesaria la identificación
									 * independientemente de su tipo de documento, por tanto, si el integrante
									 * es profesionalAbogadoPropio y no tiene NIF ni NIE, se deberá devolver como
									 * profesionalAbogado
									 */
									if ("NIE".equals(integrante.getTipoIdentificacion())){
										identificacion.setNIE(integrante.getNifCif());
										datosPersona.setIdentificacion(identificacion);
									} else if ("NIF".equals(integrante.getTipoIdentificacion()) ||
											   "CIF".equals(integrante.getTipoIdentificacion())) {
										identificacion.setNIF(integrante.getNifCif());
										datosPersona.setIdentificacion(identificacion);
									}
									integranteFisico.setDatosPersona(datosPersona);
									DatosProfesional datosProfesional = DatosProfesional.Factory.newInstance();
									Colegio colegio = Colegio.Factory.newInstance();
									colegio.setCodigoColegio(integrante.getCodigoColegioCliente());
									colegio.setDescripcionColegio(integrante.getDescripcionColegioCliente());
									
									int estadoColegial = 0;

									// Obtenemos el estado colegial del integrante
									CenDatoscolegialesestadoExample cenDatoscolegialesestadoExample = new CenDatoscolegialesestadoExample();
									cenDatoscolegialesestadoExample.createCriteria()
											.andIdinstitucionEqualTo(Short.valueOf(integrante.getIdInstitucionCliente()))
											.andIdpersonaEqualTo(Long.valueOf(integrante.getIdPersona()));
									cenDatoscolegialesestadoExample.setOrderByClause("fechaestado desc");

									List<CenDatoscolegialesestado> cenDatoscolegialesestadosList = cenDatoscolegialesestadoExtendsMapper
											.selectByExample(cenDatoscolegialesestadoExample);
									
									if (!cenDatoscolegialesestadosList.isEmpty()) {
										estadoColegial = cenDatoscolegialesestadosList.get(0).getIdestado().intValue();
									}
									
									if (integrante.getProfesion() != null) {
										if(integrante.getProfesionalAbogado().equals("1")){
											if (integrante.getCodigocolegio().equals(integrante.getCodigoColegioCliente())) {
												ProfesionalAbogadoPropio profesionalAbogadoPropio =  ProfesionalAbogadoPropio.Factory.newInstance();
												profesionalAbogadoPropio.setColegio(colegio);
												profesionalAbogadoPropio.setNumColegiado(integrante.getNumColegiado());
												profesionalAbogadoPropio.setIdentificacion(identificacion);
												datosProfesional.setProfesionalAbogadoPropio(profesionalAbogadoPropio);
											} else {
												ProfesionalAbogado profesionalAbogado =  ProfesionalAbogado.Factory.newInstance();
												profesionalAbogado.setColegio(colegio);
												profesionalAbogado.setNumColegiado(integrante.getNumColegiado());
												datosProfesional.setProfesionalAbogado(profesionalAbogado);
											}
											
										}else if (estadoColegial == 0){
											Profesional profesional = Profesional.Factory.newInstance();
											if(integrante.getCodigoColegioCliente()!=null){
												profesional.setColegio(colegio);
											}else{
												profesional.setNombreColegio(integrante.getDescripcionColegioCliente());
											}
											profesional.setNumColegiado(integrante.getNumColegiado());
											if(integrante.getProfesion().length()>20){
												profesional.setProfesion(integrante.getProfesion().substring(0, 19));
											}else{
												profesional.setProfesion(integrante.getProfesion());
											}
											datosProfesional.setProfesional(profesional);
										}
										
										if (integrante.getNumColegiado() != null) {
											integranteFisico.setDatosProfesional(datosProfesional);
										}
									}
									
									integranteUnitario.setIntegranteFisico(integranteFisico);
									integranteUnitario.setFechaModificacion(UtilidadesString.toCalendar(integrante.getFechaModificacion()));
									integranteUnitario.setPublicar(Boolean.TRUE);
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
									integranteUnitario.setPublicar(Boolean.TRUE);
									DatosEntidad datosEntidad = DatosEntidad.Factory.newInstance();
									datosEntidad.setCIFNIF(integrante.getNifCif());
									datosEntidad.setDenominacion(integrante.getNombre());
									integranteJuridico.setDatosEntidad(datosEntidad);
									integranteUnitario.setIntegranteJuridico(integranteJuridico);
								}
								integranteJuridico.setDatosCargo(cargoJuridico);
								integranteUnitario.setFechaModificacion(
										UtilidadesString.toCalendar(integrante.getFechaModificacion()));
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
							sociedadesEditadasResult = sociedadesEditadasFinal.subList(
									(peticion.getNumPagina() - 1) * tamanoPaginacion,
									(peticion.getNumPagina() * tamanoPaginacion));
						} else {
							sociedadesEditadasResult = sociedadesEditadasFinal.subList(
									(peticion.getNumPagina() - 1) * tamanoPaginacion, sociedadesEditadasFinal.size());
						}

						// Se calcula el numero total de paginas
						totalPaginas = (short) (sociedadesEditadas.size() / tamanoPaginacion);
						if (sociedadesEditadas.size() % tamanoPaginacion > 0) {
							totalPaginas++;
						}
						respuesta.setNumTotalPaginas(totalPaginas);

					} else {
						sociedadesEditadasResult.addAll(sociedadesEditadasFinal);
						respuesta.setNumTotalPaginas((short) 1);
					}
					
					try{
						if (null != sociedadesEditadasResult && sociedadesEditadasResult.size()>0) {
							
							for (SociedadActualizacion sociedadActualizacion : sociedadesEditadasResult) {

								RegistroSociedad registro = RegistroSociedad.Factory.newInstance();
								
								if (validaSociedadActualizacion(sociedadActualizacion)) {
									registro.setSociedadActualizacion(sociedadActualizacion);
									registrosList.add(registro);
								}
							}
						}
					}
				} catch (AssertionError e) {
					LOGGER.info("Excepcion añadiendo los registros en el nodo RegistroSociedad: " + e.getMessage());
				}

			}

			if (null != registrosList && registrosList.size() > 0) {
				RegistroSociedad[] registrosReturn = new RegistroSociedad[registrosList.size()];
				int i = 0;
				for (RegistroSociedad registros : registrosList) {
					registrosReturn[i] = registros;

					i++;
				}
				return registrosReturn;
			} else {
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

	/**
	 * Combina a 2 personas en una unica teniendo en cuenta las preferencias del
	 * usuario
	 * 
	 * @param mapping
	 * @param formulario
	 * @param request
	 * @param response
	 * @return
	 */
	public String fusionarPersonas(GetFusionadorPersonasRequestDocument peticionEntrada) {

		// TODO
		// MantenimientoDuplicadosForm miForm = (MantenimientoDuplicadosForm)
		// formulario;
		// SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		ControlFusionador controlFusionador = null;
		String idPersonaDestino = peticionEntrada.getGetFusionadorPersonasRequest().getDatosFusionador()
				.getIdPersonaDestino();
		String idPersonaOrigen = peticionEntrada.getGetFusionadorPersonasRequest().getDatosFusionador()
				.getIdPersonaOrigen();
		CenPersona beanPersonaDestino, beanPersonaOrigen;
		HashSet<String> conjuntoColegiosIguales;
		List<CenDatoscolegialesestado> listaEstadosAinsertar;
		String idInstitucion;

		// UserTransaction tx = null;
		GenRecursosKey example = new GenRecursosKey();
		example.setIdlenguaje("1");

		try {
			beanPersonaDestino = cenPersonaExtendsMapper.selectByPrimaryKey(Long.valueOf(idPersonaDestino));
			beanPersonaOrigen = cenPersonaExtendsMapper.selectByPrimaryKey(Long.valueOf(idPersonaOrigen));

			// Control de fusion de colegiados en el mismo colegio

			List<StringDTO> listaColegiacionesPersonaOrigen = cenColegiadoExtendsMapper
					.selectColegiacionesIdPersona(Long.valueOf(idPersonaOrigen));
			listaColegiacionesPersonaOrigen
					.addAll(cenNoColegiado.selectColegiacionesIdPersona(Long.valueOf(idPersonaOrigen)));
			String stInstitucion;
			StringDTO nombreInstitucion;
			int intInstitucion;
			listaEstadosAinsertar = new ArrayList<CenDatoscolegialesestado>();
			for (int i = 0; i < listaColegiacionesPersonaOrigen.size(); i++) {
				// para cada colegiacion de la persona origen
				stInstitucion = listaColegiacionesPersonaOrigen.get(i).getValor();
				nombreInstitucion = cenInstitucionExtendsMapper.getAbreviaturaInstitucion(stInstitucion);
				intInstitucion = Integer.parseInt(stInstitucion);
				// Si se quiere fusionar un colegiado en el mismo colegio, solo lo permitimos al
				// personal de IT o bien si el colegio no esta en produccion
				CenColegiadoKey keyColegiado = new CenColegiadoKey();
				keyColegiado.setIdinstitucion(Short.valueOf(stInstitucion));
				keyColegiado.setIdpersona(Long.parseLong(idPersonaDestino));
				if (cenColegiadoExtendsMapper.selectByPrimaryKey(keyColegiado) != null) {
					// Hay que comprobar el ultimo estado colegial de ambas personas. Si el origen
					// tiene un estado colegial posterior al destino, algo va mal
					List<ColegiadoItem> estadosColegio;
					List<EcomCenDatos> actualizacionesColegio;
					String idUltimoEstadoOrigen = null, idUltimoEstadoDestino = null,
							idEstadoActualizacionOrigen = null, idEstadoActualizacionDestino = null;
					Date fechaUltimoEstadoOrigen = null, fechaUltimoEstadoDestino = null,
							fechaEstadoActualizacionOrigen = null, fechaEstadoActualizacionDestino = null;

					ColegiadoItem colegiadoItem = new ColegiadoItem();
					colegiadoItem.setIdPersona(idPersonaOrigen);
					estadosColegio = cenColegiadoExtendsMapper.selectColegiaciones(Short.valueOf(stInstitucion), "1",
							colegiadoItem);
					if (estadosColegio != null && estadosColegio.size() > 0) {
						ColegiadoItem colegiadoItemRecuperado = estadosColegio.get(0);

						idUltimoEstadoOrigen = colegiadoItemRecuperado.getIdEstado();
						fechaUltimoEstadoOrigen = colegiadoItemRecuperado.getFechaEstado();
					}
					colegiadoItem.setIdPersona(idPersonaDestino);
					estadosColegio = cenColegiadoExtendsMapper.selectColegiaciones(Short.valueOf(stInstitucion), "1",
							colegiadoItem);
					if (estadosColegio != null && estadosColegio.size() > 0) {
						ColegiadoItem colegiadoItemRecuperado = estadosColegio.get(0);
						idUltimoEstadoDestino = colegiadoItemRecuperado.getIdEstado();
						fechaUltimoEstadoDestino = colegiadoItemRecuperado.getFechaEstado();
						;
					}

					actualizacionesColegio = ecomCenDatosExtendsMapper.getInfoMantenimientoDuplicados(idPersonaOrigen,
							Integer.toString(intInstitucion));
					if (actualizacionesColegio != null & actualizacionesColegio.size() > 0) {
						EcomCenDatos ecomCenDatos = actualizacionesColegio.get(0);
						if (ecomCenDatos.getIdecomcensosituacionejer() != null) {
							idEstadoActualizacionOrigen = ecomCenDatos.getIdecomcensosituacionejer().toString();
							fechaEstadoActualizacionOrigen = UtilidadesString
									.removeTime(ecomCenDatos.getFechamodifrecibida());
						}
					}
					actualizacionesColegio = ecomCenDatosExtendsMapper.getInfoMantenimientoDuplicados(idPersonaDestino,
							Integer.toString(intInstitucion));
					if (actualizacionesColegio != null & actualizacionesColegio.size() > 0) {
						EcomCenDatos ecomCenDatos = actualizacionesColegio.get(0);
						if (ecomCenDatos.getIdecomcensosituacionejer() != null) {
							idEstadoActualizacionDestino = ecomCenDatos.getIdecomcensosituacionejer().toString();
							fechaEstadoActualizacionDestino = UtilidadesString
									.removeTime(ecomCenDatos.getFechamodifrecibida());
						}
					}

					// Para cada persona, la fecha final sera la mayor entre la de estado y la de
					// actualizacion por carga
					if (fechaEstadoActualizacionOrigen != null
							&& fechaEstadoActualizacionOrigen.after(fechaUltimoEstadoOrigen)) {
						if (idEstadoActualizacionOrigen.equalsIgnoreCase(idUltimoEstadoOrigen)) {
							fechaUltimoEstadoOrigen = fechaEstadoActualizacionOrigen;

							CenDatoscolegialesestado estado = new CenDatoscolegialesestado();
							estado.setFechaestado(fechaEstadoActualizacionOrigen);
							estado.setIdinstitucion(Short.valueOf(stInstitucion));
							estado.setIdpersona(Long.valueOf(idPersonaOrigen));
							estado.setIdestado(Short.valueOf(idEstadoActualizacionOrigen));
							estado.setFechamodificacion(new Date());
							estado.setUsumodificacion(-1);
							listaEstadosAinsertar.add(estado);
						}
					}
					if (fechaEstadoActualizacionDestino != null
							&& fechaEstadoActualizacionDestino.after(fechaUltimoEstadoDestino)) {
						if (idEstadoActualizacionDestino.equalsIgnoreCase(idUltimoEstadoDestino)) {
							fechaUltimoEstadoDestino = fechaEstadoActualizacionDestino;

							CenDatoscolegialesestado estado = new CenDatoscolegialesestado();
							estado.setFechaestado(fechaEstadoActualizacionDestino);
							estado.setIdinstitucion(Short.valueOf(stInstitucion));
							estado.setIdpersona(Long.valueOf(idPersonaOrigen));
							estado.setIdestado(Short.valueOf(idEstadoActualizacionDestino));
							estado.setFechamodificacion(new Date());
							estado.setUsumodificacion(-1);
							listaEstadosAinsertar.add(estado);
						} else {

							return "En el colegio de " + nombreInstitucion.getValor() + ", "
									+ beanPersonaDestino.getNombre() + " (" + beanPersonaDestino.getNifcif()
									+ ") tiene un estado colegial que no se corresponde con la última actuación por Carga de censo. Por favor, revise la carga y/o consulte al Administrador para más información.";
						}
					}
					// Si el destino tiene un estado mas antiguo que el origen, no podemos permitir
					// la fusion.
					if (idUltimoEstadoOrigen != null && idUltimoEstadoDestino != null
							&& !idUltimoEstadoOrigen.equalsIgnoreCase(idUltimoEstadoDestino)) {
						if (fechaUltimoEstadoOrigen.after(fechaUltimoEstadoDestino)) {
							return "En el colegio de " + nombreInstitucion.getValor() + ", "
									+ beanPersonaOrigen.getNombre() + " (" + beanPersonaOrigen.getNifcif()
									+ ") tiene una fecha de situación colegial posterior al de "
									+ beanPersonaDestino.getNombre() + " (" + beanPersonaDestino.getNifcif()
									+ "). Para evitar cambiar el estado colegial de esta última (destino de la combinación), se ha cancelado la operación. Por favor, modifique los estados colegiales para que el destino de la combinación tenga el último estado colegial.";
						}
					}

				}
			}

			// semaforo para evitar que se pida la fusion de la misma persona varias veces
			controlFusionador = ControlFusionador.getControlFusionador(idPersonaOrigen, idPersonaDestino);
			if (controlFusionador == null) {
				example.setIdrecurso("messages.error.censo.mantenimientoDuplicados.fusionEnCurso");
				String mensaje = genRecursosMapper.selectByPrimaryKey(example).getDescripcion();

				return mensaje;
			}

			// comprobando que existan las dos personas a fusionar, por si acaso ya no
			// existe alguna (por ejemplo, si se ha fusionado en otro hilo de ejecucion)
			if (cenPersonaExtendsMapper.selectByPrimaryKey(Long.parseLong(idPersonaOrigen)) == null
					|| cenPersonaExtendsMapper.selectByPrimaryKey(Long.parseLong(idPersonaDestino)) == null) {
				example.setIdrecurso("messages.error.censo.mantenimientoDuplicados.fusionEnCursoNuevaBusqueda");
				String mensaje = genRecursosMapper.selectByPrimaryKey(example).getDescripcion();

				return mensaje;
			}

			// tx = user.getTransactionPesada();
			// tx.begin();

			// insertando los estados de actualizacion de origen y destino
			for (CenDatoscolegialesestado hashEstado : listaEstadosAinsertar) {
				hashEstado.setObservaciones(
						"Mantenimiento de duplicados: inserción automática de estado colegial por última actualización desde Carga de Censo");

				cenDatosColegialesEstadoExtendsMapper.insert(hashEstado);
			}

			// Aunque el proceso de fusion (en PL) ya se encarga de combinar las
			// direcciones,
			// tenemos que comprobar las unicidades de direcciones. Para ello, es mejor
			// moverlas ahora y comprobar las unicidades
			conjuntoColegiosIguales = new HashSet<String>();
			if (null != peticionEntrada.getGetFusionadorPersonasRequest().getDatosFusionador().getListaDirecciones()) {

				String[] direcciones = peticionEntrada.getGetFusionadorPersonasRequest().getDatosFusionador()
						.getListaDirecciones().split(",");
				String[] direccion;
				if (null != direcciones && direcciones.length > 0) {
					// CenDirecciones beanDireccion = new CenDirecciones();

					String idInstitucionComun, idDireccionOrigen;

					// HttpServletRequest preguntarAlUsuario = null;
					for (int i = 0; i < direcciones.length; i++) {
						direccion = direcciones[i].split("&&");
						if (direccion.length > 1) {
							idInstitucionComun = direccion[0];
							idDireccionOrigen = direccion[2];

							// hay que asegurarse que existe el cliente de CGAE
							if (idInstitucionComun.equalsIgnoreCase(String.valueOf(SigaConstants.InstitucionGeneral))) {
								CenClienteKey clientekey = new CenClienteKey();
								clientekey.setIdinstitucion(Short.valueOf(SigaConstants.InstitucionGeneral));
								clientekey.setIdpersona(Long.valueOf(idPersonaDestino));
								if (cenClienteExtendsMapper.selectByPrimaryKey(clientekey) == null) {
									clientekey.setIdpersona(Long.valueOf(idPersonaOrigen));
									CenCliente cliente = cenClienteExtendsMapper.selectByPrimaryKey(clientekey);
									cliente.setIdpersona(Long.valueOf(idPersonaDestino));
									cenClienteExtendsMapper.insert(cliente);
								}
							}

							CenDireccionesKey direccioneskey = new CenDireccionesKey();
							direccioneskey.setIddireccion(Long.valueOf(idDireccionOrigen));
							direccioneskey.setIdinstitucion(Short.valueOf(idInstitucionComun));
							direccioneskey.setIdpersona(Long.valueOf(idPersonaOrigen));
							// recuperando el registro original que se va a copiar
							CenDirecciones direccionConsultada = cenDireccionesExtendsMapper
									.selectByPrimaryKey(direccioneskey);

							if (null != direccionConsultada) {

								// comprobando los tipos
								CenDireccionTipodireccionExample tipoDireccionexample = new CenDireccionTipodireccionExample();
								tipoDireccionexample.createCriteria()
										.andIddireccionEqualTo(Long.valueOf(idDireccionOrigen))
										.andIdinstitucionEqualTo(Short.valueOf(idInstitucionComun))
										.andIdpersonaEqualTo(Long.valueOf(idPersonaOrigen));
								List<CenDireccionTipodireccion> tiposDireccion = cenTipoDireccionMapper
										.selectByExample(tipoDireccionexample);
								List<String> tiposOriginales = new ArrayList<String>();
								for (CenDireccionTipodireccion cenDireccionTipodireccion : tiposDireccion) {
									tiposOriginales.add(cenDireccionTipodireccion.getIdtipodireccion().toString());
								}
								List<String> tiposAinsertar = revisarTiposEnDireccionesExistentes(idInstitucionComun,
										idPersonaDestino, tiposOriginales);
								CenDireccionTipodireccion vBeanTipoDir[];
								if (tiposAinsertar.size() == 0) {
									direccionConsultada.setFechabaja(new Date());
									// .put(CenDireccionesBean.C_FECHABAJA, "sysdate");
									// y dejamos los tipos originales
									vBeanTipoDir = establecerTipoDireccion(tiposOriginales.toArray(new String[0]));
								} else {
									vBeanTipoDir = establecerTipoDireccion(tiposAinsertar.toArray(new String[0]));
								}

								// comprobando las preferencias
								String preferencias = direccionConsultada.getPreferente();
								preferencias = revisarPreferenciasEnDireccionesExistentes(idInstitucionComun,
										idPersonaDestino, preferencias);
								direccionConsultada.setPreferente(preferencias);
								// hashDireccion.put(CenDireccionesBean.C_PREFERENTE, preferencias);

								// insertando en la persona destino
								direccionConsultada.setIdpersona(Long.valueOf(idPersonaDestino));
								insertarDireccionConHistorico(direccionConsultada, vBeanTipoDir,
										"Dirección movida a persona destino antes de fusión", null, "1");

								// borrando la direccion de origen
								direccionConsultada.setIdpersona(Long.valueOf(idPersonaOrigen));
								direccionConsultada.setFechabaja(new Date());
								cenDireccionesExtendsMapper.updateByPrimaryKey(direccionConsultada);

								// guardando la institucion en la que se estan tratando direcciones
								conjuntoColegiosIguales.add(idInstitucionComun);
							}
						}
					}
				}
			}
			if (null != peticionEntrada.getGetFusionadorPersonasRequest().getDatosFusionador()
					.getListaDireccionesNoSeleccionadas()) {

				// anyadiendo tambien el listado de colegios iguales de los que se ha
				// deseleccionado todas las direcciones
				String[] direcciones = peticionEntrada.getGetFusionadorPersonasRequest().getDatosFusionador()
						.getListaDireccionesNoSeleccionadas().split(",");
				if (null != direcciones && direcciones.length > 0) {
					for (int i = 0; i < direcciones.length; i++) {
						String[] direccion = direcciones[i].split("&&");
						if (direccion.length > 1) {
							idInstitucion = direccion[0];
							conjuntoColegiosIguales.add(idInstitucion);
						}
					}
				}
			}
			// borrando todas direcciones de las instituciones (ya se movieron las que
			// fueron seleccionadas en la interfaz)
			boolean validarTipos = false;
			List<CenDirecciones> direccionesInstitucion = new ArrayList<CenDirecciones>();
			for (String idInstitucionCol : conjuntoColegiosIguales) {
				CenDireccionesExample exampleDirecciones = new CenDireccionesExample();
				exampleDirecciones.createCriteria().andIdpersonaEqualTo(Long.valueOf(idPersonaOrigen))
						.andIdinstitucionEqualTo(Short.valueOf(idInstitucionCol));
				direccionesInstitucion = cenDireccionesExtendsMapper.selectByExample(exampleDirecciones);
				for (CenDirecciones direccionEnCGAE : direccionesInstitucion) {
					boolean error = deleteDireccionConHistorico(direccionEnCGAE,
							"Dirección movida a persona destino antes de fusión", validarTipos, null);
					if (!error) {
						example.setIdrecurso("messages.error.censo.mantenimientoDuplicados.guardiaMismoDia");
						String mensaje = genRecursosMapper.selectByPrimaryKey(example).getDescripcion();

						return mensaje;
					}
				}
			}

			// comprobando datos que no es posible fusionar y hay que arreglar a mano
			ArrayList<String> listaIdPersonas = new ArrayList<String>();
			listaIdPersonas.add(idPersonaOrigen);
			listaIdPersonas.add(idPersonaDestino);
			List<FcsPagoColegiado> pagos = new ArrayList<FcsPagoColegiado>();
			List<ScsCabeceraguardias> guardias = new ArrayList<ScsCabeceraguardias>();

			for (String colegio : conjuntoColegiosIguales) {
				pagos = fcsPagoColegiadoMapper.selectPagosColegiadoDeVariasPersonas(colegio, listaIdPersonas);
				if (pagos != null && pagos.size() > 0) {
					// tx.rollback();
					example.setIdrecurso("messages.error.censo.mantenimientoDuplicados.registroMismoPagoSJCS");
					String mensaje = genRecursosMapper.selectByPrimaryKey(example).getDescripcion();

					return mensaje;
				}

				guardias = scsCabeceraguardiasMapper.getCabeceraGuardiasDeVariasPersonas(colegio, listaIdPersonas);
				if (guardias != null && guardias.size() > 0) {
					// tx.rollback();
					example.setIdrecurso("messages.error.censo.mantenimientoDuplicados.guardiaMismoDia");
					String mensaje = genRecursosMapper.selectByPrimaryKey(example).getDescripcion();

					return mensaje;
				}
			}

			// ejecutando la fusion y controlando las posibles excepciones
			String[] resultadoFusion = ejecutarPL_fusion(idPersonaOrigen, idPersonaDestino);
			if (resultadoFusion[0].equalsIgnoreCase("-1")) { // error controlado: mostrando el error en pantalla
				// tx.rollback();

				return "Imposible completar la fusión de las personas con Num. ident. ´" + beanPersonaOrigen.getNifcif()
						+ "´ y ´" + beanPersonaDestino.getNifcif() + "´: " + resultadoFusion[1];
			} else if (!resultadoFusion[0].equalsIgnoreCase("-1") && !resultadoFusion[0].equalsIgnoreCase("0")) {
				return "Imposible completar la fusión de las personas con Num. ident. ´" + beanPersonaOrigen.getNifcif()
						+ "´ y ´" + beanPersonaDestino.getNifcif() + "´. Consulte con el Administrador";
			}

			// tx.commit();

		} catch (Exception e) {
			return "Error en la fusión de las personas. Consulte al administrador";
		} finally {
			// ABRIMOS EL SEMAFORO. SE DEBE EJECUTAR SIEMPRE
			if (controlFusionador != null) {
				controlFusionador.removeControlFusionador();
			}
		}
		String msgSalida = "Fusión completada: se encuentran todos los datos de ´" + beanPersonaDestino.getNombre()
				+ "´ en el registro con Num. ident. ´" + beanPersonaDestino.getNifcif() + "´"; // OJOOO: No se pueden
																								// poner comillas dobles
																								// ni simples porque
																								// fallará la JSP
		return msgSalida;

	}

	private boolean deleteDireccionConHistorico(CenDirecciones direccionEnCGAE, String motivoHis, boolean validarTipos,
			Object object) {
		CenHistorico beanHis = new CenHistorico();
		if (motivoHis != null) {
			// estableciendo los datos del Historico
			beanHis.setMotivo(motivoHis);
		}
		boolean error = false;
		try {
			// Eliminamos la direccion

			CenDireccionesKey key = new CenDireccionesKey();
			key.setIddireccion(direccionEnCGAE.getIddireccion());
			key.setIdpersona(direccionEnCGAE.getIdpersona());
			key.setIdinstitucion(direccionEnCGAE.getIdinstitucion());
			int deleteDireccion = cenDireccionesExtendsMapper.deleteByPrimaryKey(key);

			if (!(deleteDireccion > 0)) {
				error = true;
			}
			if (!error) {
				// Se comprueba si se quiere insertar con historico o no
				if (beanHis != null) {
					// Insertamos el historico
					// CenHistorico admHis = new CenHistorico();
					if (insertCompleto(beanHis, direccionEnCGAE, 2, "1")) {
						return true;
					}
				}
			}

		} catch (Exception e) {
			throw e;
		}
		return error;

	}

	private static class ControlFusionador {
		/**
		 * Conjunto de personas que se estan fusionando en este momento. Se van metiendo
		 * y sacando segun se termina la fusion
		 */
		private static HashSet<String> listaPersonasFusionando = new HashSet<String>();
		// public static final String CONTROL_INFORME = "CONTROL_INFORME_FUSIONADOR";

		/**
		 * Obtiene un control basado en dos personas a fusionar
		 * 
		 * @param idpersona1
		 * @param idpersona2
		 * @return
		 */
		public static ControlFusionador getControlFusionador(String idpersona1, String idpersona2) {
			synchronized (listaPersonasFusionando) {
				if (listaPersonasFusionando.contains(idpersona1) || listaPersonasFusionando.contains(idpersona2)) {
					return null;
				} else {
					return new ControlFusionador(idpersona1, idpersona2);
				}
			}
		}

		private String personaFusionando1, personaFusionando2;

		/**
		 * Constructor privado: solo se puede obtener un control llamando a
		 * getControlFusionador(), que busca si las personas ya estan en una fusion
		 * 
		 * @param idpersona1
		 * @param idpersona2
		 */
		private ControlFusionador(String idpersona1, String idpersona2) {
			if (idpersona1 != null) {
				listaPersonasFusionando.add(idpersona1);
				personaFusionando1 = idpersona1;
			}
			if (idpersona2 != null) {
				listaPersonasFusionando.add(idpersona2);
				personaFusionando2 = idpersona2;
			}
		}

		/**
		 * Da por terminada la fusion controlada por este objeto
		 */
		public void removeControlFusionador() {
			synchronized (listaPersonasFusionando) {
				listaPersonasFusionando.remove(this.personaFusionando1);
				listaPersonasFusionando.remove(this.personaFusionando2);
			}
		}
	}

	/**
	 * Este metodo comprueba que los tipos de una nueva direccion (pasada como
	 * parametro) cumplen con las unicidades de tipos de las direcciones existentes.
	 * 
	 * @param dirNueva
	 */
	private ArrayList<String> revisarTiposEnDireccionesExistentes(String idInstitucion, String idPersona,
			List<String> tiposAinsertar) {
		ArrayList<String> tiposValidos = new ArrayList<String>();

		List<Integer> alTiposDireccionUnicos = Arrays.asList(UtilidadesString.tiposDireccionUnicos);

		for (String tipo : tiposAinsertar) {
			DatosDireccionesSearchDTO datosDireccionesSearchDTO = new DatosDireccionesSearchDTO();
			datosDireccionesSearchDTO.setIdInstitucion(idInstitucion);
			datosDireccionesSearchDTO.setIdPersona(idPersona);
			datosDireccionesSearchDTO.setIdTipo(tipo);
			datosDireccionesSearchDTO.setHistorico(false);
			if (!alTiposDireccionUnicos.contains(Integer.valueOf(tipo)) || cenDireccionesExtendsMapper
					.selectDirecciones(datosDireccionesSearchDTO, idInstitucion) == null) {
				tiposValidos.add(tipo);
			}
		}

		return tiposValidos;
	}

	private CenDireccionTipodireccion[] establecerTipoDireccion(String[] tipos) {
		int numTipos = tipos.length;
		CenDireccionTipodireccion vBeanTipoDir[] = new CenDireccionTipodireccion[numTipos];
		for (int i = 0; i < numTipos; i++) {
			CenDireccionTipodireccion b = new CenDireccionTipodireccion();
			b.setIdtipodireccion(Short.valueOf(tipos[i]));
			vBeanTipoDir[i] = b;
		}

		return vBeanTipoDir;
	}

	/**
	 * Este metodo comprueba que los tipos de una nueva direccion (pasada como
	 * parametro) cumplen con las unicidades de tipos de las direcciones existentes.
	 * 
	 * @param dirNueva
	 * 
	 */
	private String revisarPreferenciasEnDireccionesExistentes(String idInstitucion, String idPersona,
			String preferenciasAinsertar) {
		StringBuilder preferenciasValidas = new StringBuilder();

		char preferencia;
		for (int i = 0; i < preferenciasAinsertar.length(); i++) {
			preferencia = preferenciasAinsertar.charAt(i);
			CenDireccionesExample direccionExample = new CenDireccionesExample();
			direccionExample.createCriteria().andIdpersonaEqualTo(Long.valueOf(idPersona)).andFechabajaIsNull()
					.andIdinstitucionaltaEqualTo(Short.valueOf(idInstitucion))
					.andPreferenteLike(String.valueOf(preferencia));
			List<CenDirecciones> direcciones = cenDireccionesExtendsMapper.selectByExample(direccionExample);
			if (direcciones == null || direcciones.size() < 1) {
				preferenciasValidas.append(preferencia);
			}
		}

		return preferenciasValidas.toString();
	}

	/**
	 * Inserta los datos de una direccion y rellena la tabla de historicos
	 * (CEN_HISTORICO)
	 * 
	 * @author daniel.campos 10-01-05
	 * @version 1
	 * @param beanDir     datos de la direccion.
	 * @param beanTipoDir datos el tipo de la direccion.
	 * @param BeanHis     con el motivo y el tipo, para almacenar en el Historico
	 */
	private String insertarDireccionConHistorico(CenDirecciones beanDir, CenDireccionTipodireccion beanTipoDir[],
			String motivoHis, List<Integer> idTipoDireccionAValidarIntegers, String idioma) {
		CenHistorico beanHis = new CenHistorico();
		if (motivoHis != null) {
			// estableciendo los datos del Historico
			beanHis.setMotivo(motivoHis);
		}

		try {
			// Insertamos la direccion
			beanDir.setIddireccion(Long.valueOf(cenDireccionesExtendsMapper
					.selectNewIdDireccion(beanDir.getIdpersona().toString(), beanDir.getIdinstitucion().toString())
					.get(0).getIdDireccion()));
			int insertDireccion = cenDireccionesExtendsMapper.insert(beanDir);
			if (insertDireccion > 0) {

				boolean error = false;

				for (int i = 0; i < beanTipoDir.length; i++) {
					beanTipoDir[i].setIddireccion(beanDir.getIddireccion());
					beanTipoDir[i].setIdinstitucion(beanDir.getIdinstitucion());
					beanTipoDir[i].setIdpersona(beanDir.getIdpersona());
					beanTipoDir[i].setFechamodificacion(new Date());
					beanTipoDir[i].setUsumodificacion(-1);

					// Insertamos el tipo de direccion
					int insertTipoDireccion = cenTipoDireccionMapper.insert(beanTipoDir[i]);
					if (!(insertTipoDireccion > 0)) {
						error = true;
					}
				}

				String validacion = validarRestricciones(beanDir, idTipoDireccionAValidarIntegers);
				if (!validacion.equals("ok")) {
					return validacion;
				}
				if (!error) {
					// Se comprueba si se quiere insertar con historico o no
					if (beanHis != null) {
						// Insertamos el historico
						// CenHistorico admHis = new CenHistorico();
						if (insertCompleto(beanHis, beanDir, 1, idioma)) {
							return "ok";
						}
					}
				}
			}
			return "ko";
		} catch (Exception e) {
			throw e;
		}

	}

	private boolean insertCompleto(CenHistorico beanHis, CenDirecciones beanAsociado, int accion, String idioma) {

		CenDirecciones beanDir = (CenDirecciones) beanAsociado;

		String descripcion = "";
		GenRecursosKey example = new GenRecursosKey();
		GenRecursosCatalogosKey exampleRecursosCatalogos = new GenRecursosCatalogosKey();
		if (accion == 1) {
			example.setIdrecurso("historico.literal.registroNuevo");
		} else if (accion == 2) {
			example.setIdrecurso("historico.literal.registroEliminado");
		}

		example.setIdlenguaje("1");
		exampleRecursosCatalogos.setIdlenguaje("1");

		descripcion = genRecursosMapper.selectByPrimaryKey(example).getDescripcion() + "\n";
		beanHis.setIdinstitucion(beanDir.getIdinstitucion());
		beanHis.setIdpersona(beanDir.getIdpersona());
		beanHis.setIdtipocambio(Short.valueOf(new Integer(SigaConstants.TIPO_CAMBIO_HISTORICO_DIRECCIONES).toString()));

		// Sustituimos id's por descripciones
		try {

			// UtilidadesHash.set(hBeanAsociado, CenDireccionesBean.C_IDPAIS,
			// UtilidadesString.getMensajeIdioma(idioma,
			// ((CenPaisBean)paisAdm.selectByPK(hBeanAsociado).get(0)).getNombre()));
			if (null != cenPaisMapper.selectByPrimaryKey(beanAsociado.getIdpais())) {
				exampleRecursosCatalogos
						.setIdrecurso(cenPaisMapper.selectByPrimaryKey(beanAsociado.getIdpais()).getNombre());

				descripcion += genRecursosCatalogosMapper.selectByPrimaryKey(exampleRecursosCatalogos).getDescripcion()
						+ "\n";
			}

		} catch (Exception e) {
		}
		try {

			// UtilidadesHash.set(hBeanAsociado, CenDireccionesBean.C_IDPOBLACION,
			// UtilidadesString.getMensajeIdioma(idioma,
			// ((CenPoblacionesBean)pobAdm.selectByPK(hBeanAsociado).get(0)).getNombre()));
			if (null != cenPoblacionesMapper.selectByPrimaryKey(beanAsociado.getIdpoblacion())) {
				descripcion += cenPoblacionesMapper.selectByPrimaryKey(beanAsociado.getIdpoblacion()).getNombre()
						+ "\n";
			}

		} catch (Exception e) {
		}
		try {

			// UtilidadesHash.set(hBeanAsociado, CenDireccionesBean.C_IDPROVINCIA,
			// UtilidadesString.getMensajeIdioma(idioma,
			// ((CenProvinciaBean)provAdm.selectByPK(hBeanAsociado).get(0)).getNombre()));
			if (null != cenProvinciasMapper.selectByPrimaryKey(beanAsociado.getIdprovincia())) {
				descripcion += cenProvinciasMapper.selectByPrimaryKey(beanAsociado.getIdprovincia()).getNombre() + "\n";
			}
		} catch (Exception e) {
		}
		try {
			String p = beanAsociado.getPreferente();
			if (p != null && !p.equals("")) {
				String fin = "";
				example.setIdrecurso("censo.preferente.correo");
				if (p.indexOf(SigaConstants.TIPO_PREFERENTE_CORREO) >= 0)
					fin += ", " + genRecursosMapper.selectByPrimaryKey(example).getDescripcion();// (idioma,
																									// "censo.preferente.correo");
				example.setIdrecurso("censo.preferente.mail");
				if (p.indexOf(SigaConstants.TIPO_PREFERENTE_CORREOELECTRONICO) >= 0)
					fin += ", " + genRecursosMapper.selectByPrimaryKey(example).getDescripcion();
				example.setIdrecurso("censo.preferente.fax");
				if (p.indexOf(SigaConstants.TIPO_PREFERENTE_FAX) >= 0)
					fin += ", " + genRecursosMapper.selectByPrimaryKey(example).getDescripcion();
				example.setIdrecurso("censo.preferente.sms");
				if (p.indexOf(SigaConstants.TIPO_PREFERENTE_SMS) >= 0)
					fin += ", " + genRecursosMapper.selectByPrimaryKey(example).getDescripcion();
				if (fin.length() > 2)
					fin = fin.substring(2);
				// UtilidadesHash.set(hBeanAsociado, CenDireccionesBean.C_PREFERENTE, fin);
				descripcion += fin + "\n";
			}

		} catch (Exception e) {
		}
		try {

			List<StringDTO> tipodireccion = cenDireccionesExtendsMapper.getTiposDireccion(beanDir.getIdinstitucion(),
					beanDir.getIdpersona(), beanDir.getIddireccion(), "1");

			String sDirecciones = "";
			for (StringDTO tipo : tipodireccion) {
				sDirecciones += ", " + tipo.getValor();
			}
			descripcion += (sDirecciones.length() > 2) ? sDirecciones.substring(2) : sDirecciones;
		} catch (Exception e) {
		}

		// Hay que comprobar la longitud de la descripción que vamos a insertar
		// porque con la petición R1411_0046 se amplió el campo de la descripción de los
		// datos del cv a 4000.
//		int numCaract=descripcion.length();
//		
//		//Si estamos insertando más de 4000 caracteres hay que truncar el valor de "DESCRIPCION"
//		if(numCaract > MAX_NUM_CARACTERES_DESCRIPCION)
//		{
//			descripcion="";
//			
//			descripcion=this.getDescripcionCorta(accion,hBeanAsociado,hBeanAsociadoAnterior,idioma,numCaract);
//			
//		}

		beanHis.setDescripcion(descripcion);

		if ((beanHis.getFechaefectiva() == null))
			beanHis.setFechaefectiva(new Date());
		if ((beanHis.getFechaentrada() == null))
			beanHis.setFechaentrada(new Date());
		beanHis.setFechamodificacion(new Date());
		beanHis.setUsumodificacion(-1);
		NewIdDTO newId = cenHistoricoMapper.selectMaxIDHistoricoByPerson(beanHis.getIdpersona().toString(),
				beanHis.getIdinstitucion().toString());
		beanHis.setIdhistorico(Short.valueOf(newId.getNewId()));
		int insertHistorico = cenHistoricoMapper.insert(beanHis);

		// Insertamos el historico
		if (insertHistorico > 0) {
			return true;
		}
		return false;

	}

	private String validarRestricciones(CenDirecciones beanDir, List<Integer> idTipoDireccionAValidarIntegers) {

		try {
			// QUE EXISTA UNA DIRECCION DE CORREO
			String error = "";

			if (idTipoDireccionAValidarIntegers != null
					&& idTipoDireccionAValidarIntegers.contains(SigaConstants.TIPO_DIRECCION_FACTURACION)
					&& cenDireccionesExtendsMapper.getNumDirecciones(beanDir, SigaConstants.TIPO_DIRECCION_FACTURACION)
							.size() < 1) {
				error = "messages.censo.direcciones.facturacion";
				return error;
			}

			StringDTO esLetrado = cenClienteExtendsMapper.getEsLetrado(beanDir.getIdpersona().toString(),
					beanDir.getIdinstitucion().toString());

			ColegiadoItem colegiadoItem = new ColegiadoItem();
			colegiadoItem.setIdPersona(beanDir.getIdpersona().toString());
			List<ColegiadoItem> colegiaciones = cenColegiadoExtendsMapper
					.selectColegiaciones(beanDir.getIdinstitucion(), "1", colegiadoItem);// (beanDir.getIdPersona(),
																							// beanDir.getIdInstitucion());
			if ((null == colegiaciones || colegiaciones.size() == 0) && esLetrado.getValor().equals("0")) {
				// no es colegiado o no tiene estado colegial y ademas no es letrado
				error = "messages.censo.direcciones.tipoCorreo";
				return error;
			}

			if (idTipoDireccionAValidarIntegers != null
					&& idTipoDireccionAValidarIntegers.contains(SigaConstants.TIPO_DIRECCION_CENSOWEB)
					&& cenDireccionesExtendsMapper.getNumDirecciones(beanDir, SigaConstants.TIPO_DIRECCION_CENSOWEB)
							.size() < 1) {
				error = "messages.censo.direcciones.tipoCorreo";
				return error;
			}
			if (idTipoDireccionAValidarIntegers != null
					&& idTipoDireccionAValidarIntegers.contains(SigaConstants.TIPO_DIRECCION_TRASPASO_OJ)
					&& cenDireccionesExtendsMapper.getNumDirecciones(beanDir, SigaConstants.TIPO_DIRECCION_TRASPASO_OJ)
							.size() < 1) {
				error = "messages.censo.direcciones.traspaso.required";
				return error;
			}

			Integer estado = Integer.valueOf(colegiaciones.get(0).getIdEstado());

			// obtener las colegiaciones del letrado en estado ejerciente
			List<StringDTO> vInstitucionesEjerciente = cenClienteExtendsMapper.getInstitucionesEjerciente(
					beanDir.getIdpersona().toString(), beanDir.getIdinstitucion().toString());
			boolean tieneColegiacionEjerciente = false;
			if (vInstitucionesEjerciente != null && vInstitucionesEjerciente.size() > 0) {
				tieneColegiacionEjerciente = true;
			}

			// si es letrado y no tiene alguna colegiación ejerciente,
			// no se comprueba ni la dirección de despacho ni la de guia judicial
			if (!(esLetrado.getValor().equals("1") && !tieneColegiacionEjerciente)) {
				// SI ES EJERCIENTE O LETRADO QUE EXISTA UNA DIRECCION DE DESPACHO
				if ((((estado != null && estado.intValue() == SigaConstants.ESTADO_COLEGIAL_EJERCIENTE)
						|| esLetrado.getValor().equals("1"))) && idTipoDireccionAValidarIntegers != null
						&& idTipoDireccionAValidarIntegers.contains(SigaConstants.TIPO_DIRECCION_DESPACHO)
						&& (cenDireccionesExtendsMapper
								.getNumDirecciones(beanDir, SigaConstants.TIPO_DIRECCION_DESPACHO).size() < 1)) {
					error = "messages.censo.direcciones.tipoDespacho";
					return error;
				}

				// SI ES EJERCIENTE O LETRADO QUE EXISTA UNA DIRECCION DE GUIA JUDICIAL
				if ((((estado != null && estado.intValue() == SigaConstants.ESTADO_COLEGIAL_EJERCIENTE))
						|| esLetrado.getValor().equals("1")) && idTipoDireccionAValidarIntegers != null
						&& idTipoDireccionAValidarIntegers.contains(SigaConstants.TIPO_DIRECCION_GUIA)
						&& (cenDireccionesExtendsMapper.getNumDirecciones(beanDir, SigaConstants.TIPO_DIRECCION_GUIA)
								.size() < 1)) {
					error = "messages.censo.direcciones.tipoGuia";
					return error;
				}
			}

		} catch (Exception e) {
			return "Error al validar las restricciones de direcciones";

		}
		return "ok";

	}

	/**
	 * @param idInstitucion
	 * @param idPersona
	 * @param idCuenta
	 * @param usuario
	 * @return Codigo y mensaje de error
	 * @throws ClsExceptions
	 */
	private String[] ejecutarPL_fusion(String idPersonaOrigen, String idPersonaDestino) throws Exception {
		Object[] param_in = new Object[2]; // Parametros de entrada del PL
		String resultado[] = null; // Parametros de salida del PL

		try {
			// resultado = new String[2];
			param_in = new Object[2];
			param_in[0] = idPersonaOrigen;
			param_in[1] = idPersonaDestino;

			// Ejecucion del PL
			resultado = callPLProcedure("{call PKG_SIGA_FUSION_PERSONAS.Fusiona_Personas(?,?,?,?)}", 2, param_in);

		} catch (Exception e) {
			resultado[0] = "1"; // P_CODRETORNO
			resultado[1] = "ERROR"; // ERROR P_DATOSERROR
		}

		return resultado;
	}

	/**
	 * Calls a PL Funtion
	 * 
	 * @author CSD
	 * @param functionDefinition string that defines the function
	 * @param inParameters       input parameters
	 * @param outParameters      number of output parameters
	 * @return error code, '0' if ok
	 * @throws NamingException
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClsExceptions   type Exception
	 */
	private String[] callPLProcedure(String functionDefinition, int outParameters, Object[] inParameters)
			throws IOException, NamingException, SQLException {
		String result[] = null;

		if (outParameters > 0)
			result = new String[outParameters];
		DataSource ds = getOracleDataSource();
		Connection con = ds.getConnection();
		try {
			CallableStatement cs = con.prepareCall(functionDefinition);
			int size = inParameters.length;

			// input Parameters
			for (int i = 0; i < size; i++) {

				cs.setString(i + 1, (String) inParameters[i]);
			}
			// output Parameters
			for (int i = 0; i < outParameters; i++) {
				cs.registerOutParameter(i + size + 1, Types.VARCHAR);
			}

			for (int intento = 1; intento <= 2; intento++) {
				try {
					cs.execute();
					break;

				} catch (SQLTimeoutException tex) {
					throw tex;

				} catch (SQLException ex) {
					if (ex.getErrorCode() != 4068 || intento == 2) { // JPT: 4068 es un error de descompilado (la
																		// segunda vez deberia funcionar)
						throw ex;
					}
				}

			}

			for (int i = 0; i < outParameters; i++) {
				result[i] = cs.getString(i + size + 1);
			}
			cs.close();
			return result;

		} catch (SQLTimeoutException ex) {
			return null;
		} catch (SQLException ex) {
			return null;
		} catch (Exception e) {
			return null;
		} finally {
			con.close();
			con = null;
		}
	}

	/**
	 * Recupera el datasource con los datos de conexión sacados del fichero de
	 * configuracion
	 * 
	 * @return
	 * @throws IOException
	 * @throws NamingException
	 */
	private DataSource getOracleDataSource() throws IOException, NamingException {
		try {

			LOGGER.debug("Recuperando datasource {} provisto por el servidor (JNDI)");

			AdmConfigExample example = new AdmConfigExample();
			example.createCriteria().andClaveEqualTo("spring.datasource.jndi-name");
			List<AdmConfig> config = admConfigMapper.selectByExample(example);
			Context ctx = new InitialContext();
			return (DataSource) ctx.lookup(config.get(0).getValor());
		} catch (NamingException e) {
			throw e;
		}
	}

	private boolean validarDireccion(Direccion argDireccion) {
		boolean valido = true;
		if (argDireccion.getDomicilio() == null && argDireccion.getCodigoPostal() == null
				&& argDireccion.getProvincia().getCodigoProvincia() == null
				&& argDireccion.getProvincia().getDescripcionProvincia() == null
				&& argDireccion.getPoblacion().getCodigoPoblacion() == null
				&& argDireccion.getPoblacion().getDescripcionPoblacion() == null) {
			valido = false;
		}
		return valido;
	}

	private boolean validarIPs(String endpointReference, String[] valores) {
		boolean valid = false;
		if (valores != null) {
			for (int i = 0; i < valores.length; i++) {
				if (valores[i].equals(endpointReference)) {
					valid = true;
				}
			}
		}
		
		private boolean validarCodPostal(String codPostal) {
			boolean valid = false;
			String regex = "\\d{5}";
			if(codPostal != null && codPostal.matches(regex)) {
				valid = true;
			}
			return valid;
		}
		
		/**
		 * Valida los dato de una sociedad dada de baja.
		 * @param sociedadBaja
		 * @return
		 */
		public boolean validaSociedadBaja(SociedadBaja sociedadBaja) {
			boolean valid = false;
			
			// Comprueba si se ha proporcionado un CIF o NIF válido
			if ( (Validaciones.validaNIF(sociedadBaja.getCIFNIF()) ||
				  Validaciones.validaCIF(sociedadBaja.getCIFNIF()) ) && 
			// Comprueba si existe la fecha de baja del colegio
				sociedadBaja.getFechaBajaColegio() != null) {
				valid = true;
			}
			
			return valid;
		}
		
		/**
		 * Valida los datos de una sociedad.
		 * @param sociedadActualizacion
		 * @return
		 */
		private boolean validaSociedadActualizacion(SociedadActualizacion sociedadActualizacion) {
			
			// Comprueba si se ha proporcionado un CIF o NIF válido
			if ( (Validaciones.validaNIF(sociedadActualizacion.getDatosSociedad().getCIFNIF()) ||
				  Validaciones.validaCIF(sociedadActualizacion.getDatosSociedad().getCIFNIF()) ) &&
			// Comprueba si existen los datos de la sociedad
				sociedadActualizacion.getDatosSociedad() != null &&
				sociedadActualizacion.getFechaAlta() != null &&
				sociedadActualizacion.getFechaConstitucion() != null &&
				sociedadActualizacion.getResena() != null &&
				sociedadActualizacion.getDireccion() != null) {
				
					// Si existe el campo objeto social, comprueba si el valor no supera los 500 caracteres
					if (sociedadActualizacion.getObjetoSocial() != null &&
						sociedadActualizacion.getObjetoSocial().length() > 500) {
						return false;
					}
					
					// Comprueba que exista al menos un integrante en la sociedad
					if (sociedadActualizacion.getIntegranteSociedadArray().length == 0) {
						return false;
					} else {
						for (IntegranteSociedad integrante: sociedadActualizacion.getIntegranteSociedadArray()) {
							
							// Comprueba que los datos de los integrantes de la sociedad sean válidos
							if (!Validaciones.validaIntegranteSociedad(integrante)) {
								return false;
							}
						}
							
					}
					
					// Comprueba que los datos de la dirección de la sociedad sean válidos
					if (sociedadActualizacion.getDireccion() == null ||
						!Validaciones.validaDireccion(sociedadActualizacion.getDireccion())) {
						return false;
					}
					
					// Comprueba que los datos del notario de la sociedad sean válidos
					if (sociedadActualizacion.getDatosNotario() != null &&
						!Validaciones.validaDatosPersona(sociedadActualizacion.getDatosNotario())) {
						return false;
					}
				} else {
					return false;
				}
				
			
			return true;
		}
}
