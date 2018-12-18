package org.itcgae.siga.services.impl;

import java.util.List;

import org.apache.xmlbeans.XmlObject;
import org.itcgae.siga.DTOs.gen.FusionadorItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.constants.SigaConstants.ERROR_SERVER;
import org.itcgae.siga.commons.constants.SigaConstants.ID_TIPO_CARGA;
import org.itcgae.siga.commons.constants.SigaConstants.MODULO;
import org.itcgae.siga.commons.constants.SigaConstants.TIPO_XML;
import org.itcgae.siga.db.entities.CargasWs;
import org.itcgae.siga.db.entities.CargasWsExample;
import org.itcgae.siga.db.entities.CargasWsPagina;
import org.itcgae.siga.db.entities.CargasWsPaginaExample;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.mappers.CargasWsMapper;
import org.itcgae.siga.db.mappers.CargasWsPaginaMapper;
import org.itcgae.siga.db.mappers.CenInstitucionMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.exception.ValidationException;
import org.itcgae.siga.services.IFusionadorPersonasServerService;
import org.itcgae.siga.services.ISociedadesServerSevice;
import org.itcgae.siga.ws.fusionadorPersonas.GetFusionadorPersonasResponseDocument;
import org.itcgae.siga.ws.fusionadorPersonas.GetFusionadorPersonasResponseDocument.GetFusionadorPersonasResponse;
import org.itcgae.siga.ws.fusionadorPersonas.GetFusionadorPersonasRequestDocument;
import org.itcgae.siga.ws.fusionadorPersonas.GetFusionadorPersonasRequestDocument.GetFusionadorPersonasRequest;
import org.itcgae.sspp.ws.registroSociedades.ColegioDocument.Colegio;
import org.itcgae.sspp.ws.registroSociedades.GetListaSociedadesRequestDocument;
import org.itcgae.sspp.ws.registroSociedades.GetListaSociedadesRequestDocument.GetListaSociedadesRequest;
import org.itcgae.sspp.ws.registroSociedades.GetListaSociedadesResponseDocument;
import org.itcgae.sspp.ws.registroSociedades.GetListaSociedadesResponseDocument.GetListaSociedadesResponse;
import org.itcgae.sspp.ws.registroSociedades.RegistroSociedadDocument.RegistroSociedad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class FusionadorPersonasServerSeviceImpl implements IFusionadorPersonasServerService {

	private Logger log = LoggerFactory.getLogger(FusionadorPersonasServerSeviceImpl.class);


	@Autowired
	WSCommons WSCommons;

	@Autowired
	CenInstitucionMapper cenInstitucionMapper;

	@Autowired
	CargasWsMapper cargasWsMapper;

	@Autowired
	CargasWsPaginaMapper cargasWsPaginaMapper;

	@Override
	public GetFusionadorPersonasResponseDocument peticionServicio(String endpointReference,
			GetFusionadorPersonasRequestDocument peticionEntrada) {

		GetFusionadorPersonasResponseDocument respuestaDocument = GetFusionadorPersonasResponseDocument.Factory.newInstance();
		GetFusionadorPersonasResponse respuesta = respuestaDocument.addNewGetFusionadorPersonasResponse();
		
		GetFusionadorPersonasRequest peticion = peticionEntrada.getGetFusionadorPersonasRequest();
		CargasWs carga = new CargasWs();
		String mensaje = null;
		CargasWsPagina pagina = new CargasWsPagina();
		Long idDatosXml = null;
		List<String> errores = null;
		boolean erroresValidacion = false;
		boolean erroresGenerales = false;
		short estadoCarga = 1;
		String errorPagina = null;
		boolean paginaYaOK=false;
		Short idInstitucion=null;
		try {
			
			log.info("Guarda el XML");
			// Guardamos el xml de la peticion que hemos recibido
			idDatosXml = WSCommons.guardarXML(peticionEntrada.xmlText(), idDatosXml,
					TIPO_XML.PETICION_SERVICIO_FUSIONADOR.getCodigo(), MODULO.FUSIONADOR.getCodigo());
			

			// Validamos la peticion que nos ha llegado pidiendo que construya el xml de
			// respuesta de error con los errores encontrados si corresponde
			WSCommons.validaPeticion(peticion, respuesta, true);
			
			

			mensaje  =  WSCommons.fusionarPersonas(peticionEntrada);


		} catch (ValidationException validationException) {
			erroresValidacion = true;

		} catch (BusinessException bException) {
			erroresGenerales = true;
			log.error("Error en la carga: " + bException);
			// TODO Construir respuesta con eeror general
			WSCommons.construirErrorGeneral(ERROR_SERVER.CLI_OTROS_ERRORES_INTERNOS.name(),
					ERROR_SERVER.CLI_OTROS_ERRORES_INTERNOS.getMensajeError(), respuesta);
		}

		respuesta.setNumeroPeticion(peticion.getNumeroPeticion());
		respuesta.setColegio(peticion.getColegio());
		respuesta.setMensajeResponse(mensaje);
		
		try {

			WSCommons.guardarXML(respuestaDocument.xmlText(), idDatosXml,
					TIPO_XML.RESPUESTA_SERVICIO_FUSIONADOR.getCodigo(), MODULO.FUSIONADOR.getCodigo());
			errores = WSCommons.validaRespuesta(respuesta);
			if (!errores.isEmpty()) {
				throw new ValidationException(errores.toString());
			}
			// Fijamos la respuesta construida
			respuestaDocument.setGetFusionadorPersonasResponse(respuesta);

		} catch (ValidationException validationException) {
			erroresValidacion = true;
		} catch (BusinessException bException) {
			erroresGenerales = true;
		}
		if (erroresValidacion) {
			if (pagina!=null && pagina.getIdWsPagina()!=null){
				estadoCarga = SigaConstants.ESTADO_CARGAS.ESTADO_ERROR_FORMATO.getCodigo();
				errorPagina = "ERR_VALIDACION";
			}else{
				paginaYaOK=true;
			}
		}
		if (erroresGenerales) {
			estadoCarga = SigaConstants.ESTADO_CARGAS.ESTADO_ERROR_GENERAL.getCodigo();
			errorPagina = "ERR_GENERAL";
		}
		if (erroresValidacion || erroresGenerales) {
			// En el servicio no se debe de actualizar el estado de la carga a con error ya
			// que puede llegar luego una peticion que solucione el problema de dicha carga.
			
			 //Si se ha creado carga se tiene que actualizar el estado a con error 
			if (carga!=null && carga.getIdCarga()!=null && !paginaYaOK){ 
				carga.setConErrores((short)1);
			    carga.setIdEstadoCarga(estadoCarga); WSCommons.actualizaEstadoCarga(carga); 
			  }
			 
			// Si se ha creado pagina se tiene que actualizar el estado a con error
			if (pagina != null && pagina.getIdWsPagina() != null) {
				pagina.setCodError(errorPagina);
				WSCommons.actualizaEstadoPagina(pagina);
			}
		}
		return respuestaDocument;
	}

	private Short validaSeguridadPorColegio(GetListaSociedadesResponse setListaSociedadesResponse,
			String endpointReference, Colegio colegio) throws ValidationException {

		// Si el colegio que nos llega es null construimos error en la respuesta
		if (colegio == null) {
			WSCommons.lanzaError(SigaConstants.ERROR_SERVER.CLI_COLEGIO_NULO.name(),
					SigaConstants.ERROR_SERVER.CLI_COLEGIO_NULO.getMensajeError(), setListaSociedadesResponse);
		}
		String codigoColegio = colegio.getCodigoColegio();
		log.info("El colegio recibido es " + codigoColegio);

		// Obtenemos el identificador en nuestro sistema del colegio correspondiente a
		// ese código y no se encuentra ya el método interno contruye la respuesta de
		// error y lanza la excepcion
		Short idInstitucion = getIdinstitucion(setListaSociedadesResponse, codigoColegio);

		// comprobamos que el colegio tenga la IP configurada correctamente y que el
		// colegio esté activo
		WSCommons.comprobarIP(setListaSociedadesResponse, endpointReference, idInstitucion, SigaConstants.IP_ACCESO_SERVICIO_CARGAS, SigaConstants.ERROR_SERVER.CLI_IP_NO_ENCONTRADA);
		WSCommons.colegioActivo(setListaSociedadesResponse, idInstitucion, SigaConstants.ACTIVAR_CLIENTE_SERVICIO_CARGAS, SigaConstants.ERROR_SERVER.CLI_NOACTIVO);

		return idInstitucion;

	}

	/**
	 * Obtención de la institución a través del codigo del Colegio
	 * 
	 * @param xmlObjectResponse
	 * @param codigoColegio
	 * @return
	 * @throws ValidationException
	 */
	private Short getIdinstitucion(XmlObject xmlObjectResponse, String codigoColegio) throws ValidationException {

		// Si el codigo que nos llega es nulo construimos el mensaje de error de
		// respuesta ya que no podemos validarlo
		if (codigoColegio == null || codigoColegio.trim().equals("")) {
			WSCommons.lanzaError(SigaConstants.ERROR_SERVER.CLI_CODIGO_NO_EXISTE.name(),
					SigaConstants.ERROR_SERVER.CLI_CODIGO_NO_EXISTE.getMensajeError(), xmlObjectResponse);
		}

		// Obtener el id de institución x codColegio
		CenInstitucion colegio = WSCommons.buscarColegio(codigoColegio);

		if (colegio == null) {
			// No se encuentra el colegio con código "x"
			WSCommons.lanzaError(SigaConstants.ERROR_SERVER.CLI_CODIGO_NO_EXISTE.name(),
					SigaConstants.ERROR_SERVER.CLI_CODIGO_NO_EXISTE.getMensajeError(), xmlObjectResponse);
		}
		return colegio.getIdinstitucion();
	}

	/**
	 * Se comprueba si no hay insertada una carga con ese numero de peticion en la
	 * base de datos para ese colegio
	 * 
	 * @param peticion
	 * @param idInstitucion
	 * @throws ValidationException
	 */
	private CargasWs obtenerCarga(GetListaSociedadesRequest peticion, Short idInstitucion,
			GetListaSociedadesResponse respuesta) throws ValidationException {

		String numeroPeticion = peticion.getNumeroPeticion();
		CargasWs cargaWs = null;
		if (WSCommons.comprobarString(numeroPeticion, 20) == true) {
			// Comprobamos que no exista ya una carga creada con ese numero de peticion y
			// para esa institucion
			CargasWsExample cargasWsExample = new CargasWsExample();
			cargasWsExample.createCriteria().andNumeroPeticionEqualTo(numeroPeticion)
					.andIdInstitucionEqualTo(Long.valueOf(idInstitucion));
			List<CargasWs> listaCargas = cargasWsMapper.selectByExample(cargasWsExample);

			// si no está, se inserta
			if (listaCargas == null || listaCargas.size() == 0) {
				cargaWs = WSCommons.insertarCarga(idInstitucion, ID_TIPO_CARGA.SERV_SOC.getTipo(),
						peticion.getNumeroPeticion(), peticion.getNumPagina());
			} else {
				cargaWs = listaCargas.get(0);
				if (peticion.getNumPagina() > cargaWs.getTotalPaginas()) {
					WSCommons.lanzaError(SigaConstants.ERROR_SERVER.CLI_INCONCORDANCIA_EN_PAGINAS.name(),
							SigaConstants.ERROR_SERVER.CLI_INCONCORDANCIA_EN_PAGINAS.getMensajeError(), respuesta);
				}
				if (cargaWs.getConErrores()==1){
					WSCommons.lanzaError(SigaConstants.ERROR_SERVER.CLI_ERROR_DATOS_INTERNO.name(),
							SigaConstants.ERROR_SERVER.CLI_ERROR_DATOS_INTERNO.getMensajeError()
									+ " el número de petición es una carga ya encontrada con errores "
									+ peticion.getNumeroPeticion(),
							respuesta);
				}
			}
		} else {
			WSCommons.lanzaError(SigaConstants.ERROR_SERVER.CLI_CAMPO_NO_VALIDO.name(),
					SigaConstants.ERROR_SERVER.CLI_CAMPO_NO_VALIDO.getMensajeError()
							+ " sólo se admiten 20 caracteres para el numero de peticion "
							+ peticion.getNumeroPeticion(),
					respuesta);
		}

		return cargaWs;
	}

	private CargasWsPagina insertaPagina(Long idCarga, Long idDatosXml, GetListaSociedadesRequest peticion,
			GetListaSociedadesResponse respuesta) throws ValidationException {

		// Buscamos que no exista ya la pagina recibida en esa carga y sin errores
		CargasWsPaginaExample cargasWsPaginaExample = new CargasWsPaginaExample();
		CargasWsPagina cargasWsPagina = new CargasWsPagina();
		cargasWsPaginaExample.createCriteria().andIdCargaEqualTo(idCarga).andCodErrorIsNull()
				.andNumPaginaEqualTo(peticion.getNumPagina());
		List<CargasWsPagina> listaPagina = cargasWsPaginaMapper.selectByExample(cargasWsPaginaExample);

		// si no está, se inserta
		if (listaPagina.isEmpty()) {
			cargasWsPagina = WSCommons.insertarPagina(peticion.getNumPagina(), idCarga, idDatosXml);
		} else {
			WSCommons.lanzaError(SigaConstants.ERROR_SERVER.CLI_PAGINA_YA_INSERTADA.name(),
					SigaConstants.ERROR_SERVER.CLI_PAGINA_YA_INSERTADA.getMensajeError(), respuesta);
		}
		return cargasWsPagina;
	}



}