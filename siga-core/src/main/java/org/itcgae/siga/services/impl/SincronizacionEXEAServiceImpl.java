package org.itcgae.siga.services.impl;

import com.exea.sincronizacion.redabogacia.*;
import org.itcgae.siga.DTOs.cen.DatosDireccionesItem;
import org.itcgae.siga.DTOs.cen.FichaPersonaItem;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.ColegiadoJGItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.*;
import org.itcgae.siga.db.services.adm.mappers.CenHistoricoExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.*;
import org.itcgae.siga.exception.ValidationException;
import org.itcgae.siga.services.ISincronizacionEXEAService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.rmi.CORBA.Util;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class SincronizacionEXEAServiceImpl implements ISincronizacionEXEAService {

    private Logger log = LoggerFactory.getLogger(SincronizacionEXEAServiceImpl.class);

    @Autowired
    private WSCommons wsCommons;

    @Autowired
    private CenPersonaExtendsMapper cenPersonaExtendsMapper;

    @Autowired
    private CenSolicitudincorporacionExtendsMapper cenSolicitudincorporacionExtendsMapper;

    @Autowired
    private CenDatoscolegialesestadoExtendsMapper cenDatoscolegialesestadoExtendsMapper;

    @Autowired
    private GenParametrosExtendsMapper genParametrosExtendsMapper;

    @Autowired
    private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;

    @Autowired
    private CenEstadocivilExtendsMapper cenEstadocivilExtendsMapper;

    @Autowired
    private CenClienteExtendsMapper cenClienteExtendsMapper;

    @Autowired
    private  CenInstitucionExtendsMapper cenInstitucionExtendsMapper;

    @Autowired
    private  CenNocolegiadoExtendsMapper cenNocolegiadoExtendsMapper;

    @Autowired
    private CenDireccionesExtendsMapper cenDireccionesExtendsMapper;

    @Autowired
    private  CenDireccionTipodireccionExtendsMapper cenDireccionTipodireccionExtendsMapper;

    @Autowired
    private CenPoblacionesExtendsMapper cenPoblacionesExtendsMapper;

    @Autowired
    private CenPaisExtendsMapper cenPaisExtendsMapper;

    @Autowired
    private CenHistoricoExtendsMapper cenHistoricoExtendsMapper;

    @Autowired
    private CenCuentasbancariasExtendsMapper cenCuentasbancariasExtendsMapper;

    @Autowired
    private CenBancosExtendsMapper cenBancosExtendsMapper;

    @Autowired
    private CenSancionExtendsMapper cenSancionExtendsMapper;

    @Autowired
    private CenTiposancionExtendsMapper cenTiposancionExtendsMapper;

    /**
     * Metodo que recibe una peticion {@link ObtenerNumColegiacionResponseDocument} y, tras varias validaciones, devuelve o no el proximo numero de colegiado
     *
     * tipoColegiacion (1, 2, 3)
     * tipoSolicitud(E, N, I)
     *
     * @param requestDocument
     * @return
     */
    @Override
    public ObtenerNumColegiacionResponseDocument getNumColegiacion(ObtenerNumColegiacionRequestDocument requestDocument, String ipCliente) {

        log.info("SincronizacionEXEAServiceImpl.getNumColegiacion() - INICIO");
        log.info("SincronizacionEXEAServiceImpl.getNumColegiacion() / REQUEST : " + requestDocument.xmlText());

        ObtenerNumColegiacionResponseDocument responseDocument = ObtenerNumColegiacionResponseDocument.Factory.newInstance();
        ObtenerNumColegiacionResponseDocument.ObtenerNumColegiacionResponse response = responseDocument.addNewObtenerNumColegiacionResponse();
        ObtenerNumColegiacionRequestDocument.ObtenerNumColegiacionRequest request = requestDocument.getObtenerNumColegiacionRequest();
        boolean yaDadoAlta = false;
        try {
            wsCommons.validaPeticion(request, response, true);
            Short idInstitucion = null;
            //El codigo de colegio es el codigo externo de la institucion, buscamos el idinstitucion real
            CenInstitucion institucion = wsCommons.buscarColegio(request.getColegio().getCodigoColegio());
            if(institucion != null){
                idInstitucion = institucion.getIdinstitucion();
                log.info("SincronizacionEXEAServiceImpl.getNumColegiacion() / Colegio encontrado: " + request.getColegio().getCodigoColegio() + " - " + idInstitucion);
            }else{
                ErrorType errorType = response.addNewError();
                errorType.setCodigo(SigaConstants.ERROR_SINCRONIZACION_EXEA.COLEGIO_NOVALIDO.name());
                errorType.setDescripcion(SigaConstants.ERROR_SINCRONIZACION_EXEA.COLEGIO_NOVALIDO.getMensajeError());
                errorType.setXmlRequest("Sin error XML");
                log.error("SincronizacionEXEAServiceImpl.getNumColegiacion() / ERROR CONTROLADO: Colegio inexsistente en BBDD - " + request.getColegio().getCodigoColegio());
            }

            if(idInstitucion != null) {
                String identificacion = "";
                Long idPersona = null;
                FichaPersonaItem fichaPersonaItem = null;
                String nextNumColegiado = null;

                if (!UtilidadesString.esCadenaVacia(request.getIdentificacion().getNIF())) {
                    identificacion = request.getIdentificacion().getNIF();
                    log.info("SincronizacionEXEAServiceImpl.getNumColegiacion() / Informado NIF: " + identificacion);
                } else if (!UtilidadesString.esCadenaVacia(request.getIdentificacion().getNIE())) {
                    identificacion = request.getIdentificacion().getNIE();
                    log.info("SincronizacionEXEAServiceImpl.getNumColegiacion() / Informado NIE: " + identificacion);
                } else {
                    identificacion = request.getIdentificacion().getPasaporte();
                    log.info("SincronizacionEXEAServiceImpl.getNumColegiacion() / Informado Pasaporte: " + identificacion);
                }

                idPersona = cenPersonaExtendsMapper.getIdPersonaWithNif(identificacion);

                //Puede estar registrado en censo como persona, pero no ser colegiado. Lo siguiente es comprobar si ya es un colegiado
                if (idPersona != null) {

                    log.info("SincronizacionEXEAServiceImpl.getNumColegiacion() / Registrado en CEN_PERSONA: " + idPersona);
                    fichaPersonaItem = cenPersonaExtendsMapper.getColegiadoByIdPersona(idPersona.toString(), idInstitucion);

                    if (fichaPersonaItem != null) {
                        ColegioType colegioType = response.addNewColegio();
                        colegioType.setCodigoColegio(request.getColegio().getCodigoColegio());
                        response.setNumeroExpediente(request.getNumeroExpediente());
                        response.setNumColegiacion(fichaPersonaItem.getNumeroColegiado());
                        response.setTipoColegiacion(request.getTipoSolicitud());
                        yaDadoAlta = true;
                        log.info("SincronizacionEXEAServiceImpl.getNumColegiacion() / Colegiado ya dado de alta, se devuelve su numero de colegiado - " + fichaPersonaItem.getNumeroColegiado());
                    }

                }else{
                    log.info("SincronizacionEXEAServiceImpl.getNumColegiacion() / No registrado en CEN_PERSONA, se devolvera numero de colegiado nuevo");
                }

                //Si no está dado de alta como colegiado procedemos a buscar, dependiendo del tipo de colegiacion, el proximo numero de colegiado
                if (!yaDadoAlta) {
                    log.info("SincronizacionEXEAServiceImpl.getNumColegiacion() / No dado de alta, se procede a buscar nuevo numero de colegiado");
                    // Comprobamos si para la institucion tiene un contador unico
                    GenParametrosKey key = new GenParametrosKey();
                    key.setIdinstitucion(idInstitucion);
                    key.setModulo(SigaConstants.MODULO_CENSO);
                    key.setParametro(SigaConstants.PARAMETRO_CONTADOR_UNICO);
                    GenParametros genParametro = genParametrosExtendsMapper.selectByPrimaryKey(key);

                    String contadorUnico = genParametro == null || genParametro.getValor() == null ? "0" : genParametro.getValor();
                    //Si la institucion tiene un contador unico
                    if (SigaConstants.DB_TRUE.equals(contadorUnico)) {
                        nextNumColegiado = cenSolicitudincorporacionExtendsMapper.getMaxNColegiadoComunitario(idInstitucion.toString()).getValor();
                        log.info("SincronizacionEXEAServiceImpl.getNumColegiacion() / La institucion tiene contador unico, devolvemos nColegiado o nComunitario - " + nextNumColegiado);
                        //Ejerciente || No Ejerciente
                    } else if (TipoColegiacionType.E.equals(request.getTipoSolicitud())
                            || TipoColegiacionType.N.equals(request.getTipoSolicitud())) {
                        nextNumColegiado = cenSolicitudincorporacionExtendsMapper.getMaxNColegiado(idInstitucion.toString()).getValor();
                        log.info("SincronizacionEXEAServiceImpl.getNumColegiacion() / El tipo de solicitud es Ejerciente/No Ejerciente, devolvemos siguiente numero de colegiado - "+ nextNumColegiado);
                        //Inscrito
                    } else if (TipoColegiacionType.I.equals(request.getTipoSolicitud())) {
                        nextNumColegiado = cenSolicitudincorporacionExtendsMapper.getMaxNComunitario(idInstitucion.toString()).getValor();
                        log.info("SincronizacionEXEAServiceImpl.getNumColegiacion() / El tipo de solicitud es de Inscrito, devolvemos siguiente numero de comunitario");
                    }

                    ColegioType colegioType = response.addNewColegio();
                    colegioType.setCodigoColegio(request.getColegio().getCodigoColegio());
                    response.setNumeroExpediente(request.getNumeroExpediente());
                    response.setNumColegiacion(nextNumColegiado);
                    response.setTipoColegiacion(request.getTipoSolicitud());

                }
            }

        } catch (ValidationException validationException) {
            log.error("SincronizacionEXEAServiceImpl.getNumColegiacion() / Error: XML NO VALIDO", validationException);
        }

        log.info("SincronizacionEXEAServiceImpl.getNumColegiacion() / RESPONSE: " + responseDocument.xmlText());
        log.info("SincronizacionEXEAServiceImpl.getNumColegiacion() - FIN");
        return responseDocument;
    }

    /**
     * Metodo que recibe un {@link AltaColegiadoRequestDocument} para dar de alta o reincorporar a un colegiado
     *
     * @param requestDocument
     * @param ipCliente
     * @return
     */
    @Transactional(rollbackFor = SigaExceptions.class)
    @Override
    public AltaColegiadoResponseDocument aprobarAltaColegiado(AltaColegiadoRequestDocument requestDocument, String ipCliente) throws SigaExceptions {

        log.info("SincronizacionEXEAServiceImpl.aprobarAltaColegiado() - INICIO");
        log.info("SincronizacionEXEAServiceImpl.aprobarAltaColegiado() / REQUEST : " + requestDocument.xmlText());

        AltaColegiadoResponseDocument responseDocument = AltaColegiadoResponseDocument.Factory.newInstance();
        AltaColegiadoResponseDocument.AltaColegiadoResponse response = responseDocument.addNewAltaColegiadoResponse();
        AltaColegiadoRequestDocument.AltaColegiadoRequest request = requestDocument.getAltaColegiadoRequest();

        try {
            wsCommons.validaPeticion(request,response,true);
            String numColegiado = request.getColegiado().getColegiacion().getNumColegiado();
            //El codigo de colegio es el codigo externo de la institucion, buscamos el idinstitucion real
            CenInstitucion institucion = wsCommons.buscarColegio(request.getColegiado().getColegiacion().getColegio().getCodigoColegio());
            Short idInstitucion = null;
            boolean isReincorporacion = TipoBoolean.X_1.equals(request.getColegiado().getColegiacion().getReincorporacion());
            int situacionPrevia = request.getColegiado().getColegiacion().getSituacionPrevia().intValue();
            TipoColegiacionType.Enum tipoColegiacionType = request.getColegiado().getColegiacion().getTipoSolicitud();
            Long idPersona, idDireccion;
            Short idCuenta = null;

            int affectedRows = 0;
            if(institucion != null){
                idInstitucion = institucion.getIdinstitucion();
                log.info("SincronizacionEXEAServiceImpl.aprobarAltaColegiado() / Colegio encontrado: " + request.getColegiado().getColegiacion().getColegio().getCodigoColegio() + " - " + idInstitucion);
            }else{
                ErrorType errorType = response.addNewError();
                errorType.setCodigo(SigaConstants.ERROR_SINCRONIZACION_EXEA.COLEGIO_NOVALIDO.name());
                errorType.setDescripcion(SigaConstants.ERROR_SINCRONIZACION_EXEA.COLEGIO_NOVALIDO.getMensajeError());
                errorType.setXmlRequest("Sin error XML");
                log.error("SincronizacionEXEAServiceImpl.aprobarAltaColegiado() / ERROR CONTROLADO: Colegio inexsistente en BBDD - " + request.getColegiado().getColegiacion().getColegio().getCodigoColegio());
            }
            if(idInstitucion != null) {
                if (UtilidadesString.esCadenaVacia(numColegiado)) {

                    if(isReincorporacion){
                        ErrorType errorType = response.addNewError();
                        errorType.setCodigo(SigaConstants.ERROR_SINCRONIZACION_EXEA.COLEGIO_NOVALIDO.name());
                        errorType.setDescripcion("No puede llevarse a cabo una reincorporacion si no viene informado el numero de colegiado");
                        errorType.setXmlRequest("Sin error XML");
                        log.error("SincronizacionEXEAServiceImpl.aprobarAltaColegiado() / No se puede llevar a cabo una reincorporacion sin el numero de colegiado");
                    }else{
                        log.info("SincronizacionEXEAServiceImpl.aprobarAltaColegiado() / No viene informado el numColegiado, insertamos datos y creamos el colegiado");

                        //Insertamos/updateamos los datos personales en CEN_PERSONA
                        idPersona = insertarDatosPersonales(request.getColegiado().getDatosPersonales(), idInstitucion);

                        affectedRows += insertarDatosCliente(idPersona, idInstitucion);

                        idDireccion = insertarDatosDireccion(request.getColegiado().getLocalizacion(), idInstitucion , request.getColegiado().getColegiacion().getTipoSolicitud(), idPersona);

                        affectedRows += insertarDatosColegiado(request.getColegiado().getColegiacion(), idInstitucion, idPersona, numColegiado, request.getNumeroExpediente());

                        if(request.getColegiado().getDatosBancarios() != null
                                && !UtilidadesString.esCadenaVacia(request.getColegiado().getDatosBancarios().getIBAN())){
                            idCuenta = insertarDatosBancarios(request.getColegiado().getDatosBancarios(), idPersona, idInstitucion, request.getColegiado().getDatosPersonales());
                        }else{
                            //TODO: ejecutarPL_RevisionSuscripcionesLetrado ?
                        }

                        if(idPersona != null && idDireccion != null && affectedRows > 0){
                            response.setNumeroExpediente(request.getNumeroExpediente());
                        }else{
                            ErrorType errorType = response.addNewError();
                            errorType.setCodigo(SigaConstants.ERROR_SINCRONIZACION_EXEA.OTRO_ERROR.name());
                            errorType.setDescripcion("Ocurrio un error al crear el nuevo colegiado");
                            errorType.setXmlRequest("Sin error XML");
                            log.error("SincronizacionEXEAServiceImpl.aprobarAltaColegiado() / Error al crear el nuevo colegiado - idPersona: " + idPersona + " - idDireccion: " + idDireccion + " - affectedRows: " + affectedRows);
                        }
                    }
                //Si viene informado el numColegiado puede que se trate de una reincorporacion, si no, lanzamos error
                } else {
                    CenColegiadoExample cenColegiadoExample = new CenColegiadoExample();
                    List<CenColegiado> cenColegiados;
                    if (TipoColegiacionType.E.equals(request.getColegiado().getColegiacion().getTipoSolicitud())
                            || TipoColegiacionType.N.equals(request.getColegiado().getColegiacion().getTipoSolicitud())) {
                        cenColegiadoExample.createCriteria()
                                .andNcolegiadoEqualTo(numColegiado)
                                .andIdinstitucionEqualTo(idInstitucion)
                                .andComunitarioEqualTo("0");
                        //Inscrito
                    } else if (TipoColegiacionType.I.equals(request.getColegiado().getColegiacion().getTipoSolicitud())) {
                        cenColegiadoExample.createCriteria()
                                .andNcomunitarioEqualTo(numColegiado)
                                .andIdinstitucionEqualTo(idInstitucion)
                                .andComunitarioEqualTo("1");
                    }

                    cenColegiados = cenColegiadoExtendsMapper.selectByExample(cenColegiadoExample);

                    if(cenColegiados != null
                        && !cenColegiados.isEmpty()){

                        CenColegiado colegiado = cenColegiados.get(0);

                        if(isReincorporacion
                                && situacionPrevia != 1 //No puede ser una reincorporacion y que sea su primera colegiacion (situacionPrevia = 1)
                                && ((situacionPrevia == 2 && (TipoColegiacionType.E.equals(tipoColegiacionType) || TipoColegiacionType.I.equals(tipoColegiacionType)))
                                    || (situacionPrevia == 3 && TipoColegiacionType.N.equals(tipoColegiacionType)))){
                            //Debe coincidir la situacion previa con el tipo de solicitud, es decir si era ejerciente, que el tipo de solicitud sea E (Ejerciente)
                            // y si era no ejerciente, que el tipo de solicitud sea N (No ejerciente)

                                reincorporarColegiado(colegiado, request, response, idInstitucion);

                        //Si se ha encontrado el colegiado y no es una reincorporacion devolvemos error
                        }else if(!isReincorporacion){
                            ErrorType errorType = response.addNewError();
                            errorType.setCodigo(SigaConstants.ERROR_SINCRONIZACION_EXEA.COLEGIADO_ENCONTRADO.name());
                            errorType.setDescripcion(SigaConstants.ERROR_SINCRONIZACION_EXEA.COLEGIADO_ENCONTRADO.getMensajeError());
                            errorType.setXmlRequest("Sin error XML");
                            log.error("SincronizacionEXEAServiceImpl.aprobarAltaColegiado() / El colegiado " + numColegiado + " ya esta dado de alta en el sistema");

                            //Si es una reincorporacion pero la situacion previa es Primera Colegiacion, lanzamos error
                        }else if(isReincorporacion && situacionPrevia == 1){
                            ErrorType errorType = response.addNewError();
                            errorType.setCodigo(SigaConstants.ERROR_SINCRONIZACION_EXEA.OTRO_ERROR.name());
                            errorType.setDescripcion("No es posible el alta del colegiado, no puede tratarse de una primera colegiacion y tratarse de una reincorporacion");
                            errorType.setXmlRequest("Sin error XML");
                            log.error("SincronizacionEXEAServiceImpl.aprobarAltaColegiado() / No puede ser una reincorporacion y tratarse de la primera colegiacion");

                            //Si era Ejerciente, el tipo de solicitud debe ser Ejerciente y si era No Ejerciente, el tipo de solicitud debe ser No Ejerciente
                        }else if((situacionPrevia == 2 && !TipoColegiacionType.E.equals(tipoColegiacionType))
                                || (situacionPrevia == 3 && !TipoColegiacionType.N.equals(tipoColegiacionType))){
                            ErrorType errorType = response.addNewError();
                            errorType.setCodigo(SigaConstants.ERROR_SINCRONIZACION_EXEA.OTRO_ERROR.name());
                            errorType.setDescripcion("No es posible el alta del colegiado debido a que la situacion previa y el tipo de solicitud deben coincidir");
                            errorType.setXmlRequest("Sin error XML");
                            log.error("SincronizacionEXEAServiceImpl.aprobarAltaColegiado() / Error de validacion: Para una reincorporacion, la situacion previa y el tipo de solicitud deben coincidir");
                        }
                    //Si no existe en el sistema y no es reincorporacion le damos de alta
                    }else if (!isReincorporacion){

                        //Insertamos/updateamos los datos personales en CEN_PERSONA
                        idPersona = insertarDatosPersonales(request.getColegiado().getDatosPersonales(), idInstitucion);

                        affectedRows += insertarDatosCliente(idPersona, idInstitucion);

                        idDireccion = insertarDatosDireccion(request.getColegiado().getLocalizacion(), idInstitucion , request.getColegiado().getColegiacion().getTipoSolicitud(), idPersona);

                        affectedRows += insertarDatosColegiado(request.getColegiado().getColegiacion(), idInstitucion, idPersona, numColegiado, request.getNumeroExpediente());

                        if(request.getColegiado().getDatosBancarios() != null
                                && !UtilidadesString.esCadenaVacia(request.getColegiado().getDatosBancarios().getIBAN())){
                            idCuenta = insertarDatosBancarios(request.getColegiado().getDatosBancarios(), idPersona, idInstitucion, request.getColegiado().getDatosPersonales());
                        }else{
                            //TODO: ejecutarPL_RevisionSuscripcionesLetrado ?
                        }

                        if(idPersona != null && idDireccion != null && affectedRows > 0){
                            response.setNumeroExpediente(request.getNumeroExpediente());
                        }else{
                            ErrorType errorType = response.addNewError();
                            errorType.setCodigo(SigaConstants.ERROR_SINCRONIZACION_EXEA.OTRO_ERROR.name());
                            errorType.setDescripcion("Ocurrio un error al crear el nuevo colegiado");
                            errorType.setXmlRequest("Sin error XML");
                        }

                    //Si no existe y es reincorporacion lanzamos error
                    }else{
                        ErrorType errorType = response.addNewError();
                        errorType.setCodigo(SigaConstants.ERROR_SINCRONIZACION_EXEA.COLEGIADO_NOENCONTRADO.name());
                        errorType.setDescripcion(SigaConstants.ERROR_SINCRONIZACION_EXEA.COLEGIADO_NOENCONTRADO.getMensajeError());
                        errorType.setXmlRequest("Sin error XML");
                        log.error("SincronizacionEXEAServiceImpl.aprobarAltaColegiado() / El colegiado " + numColegiado + " no existe y por lo tanto no se puede llevar a cabo la reincorporacion");
                    }
                }
            }

        } catch (ValidationException validationException) {
            log.error("SincronizacionEXEAServiceImpl.aprobarAltaColegiado() / Error: XML NO VALIDO", validationException);
        } catch (Exception e){
            log.error("SincronizacionEXEAServiceImpl.aprobarAltaColegiado() / ERROR NO CONTROLADO", e);
            throw new SigaExceptions(e,e.getMessage());
        }

        log.info("SincronizacionEXEAServiceImpl.aprobarAltaColegiado() / RESPONSE: " + responseDocument.xmlText());
        log.info("SincronizacionEXEAServiceImpl.aprobarAltaColegiado() - FIN");
        return responseDocument;
    }

    /**
     * Servicio encargado de dar de alta una sancion
     *
     * @param requestDocument
     * @param ipCliente
     * @return
     * @throws SigaExceptions
     */
    @Override
    @Transactional(rollbackFor = SigaExceptions.class)
    public AltaSancionResponseDocument altaSancion(AltaSancionRequestDocument requestDocument, String ipCliente) throws SigaExceptions {

        log.info("SincronizacionEXEAServiceImpl.altaSancion() - INICIO");
        log.info("SincronizacionEXEAServiceImpl.altaSancion() / REQUEST : " + requestDocument.xmlText());

        AltaSancionResponseDocument responseDocument =  AltaSancionResponseDocument.Factory.newInstance();
        AltaSancionResponseDocument.AltaSancionResponse response = responseDocument.addNewAltaSancionResponse();
        AltaSancionRequestDocument.AltaSancionRequest request = requestDocument.getAltaSancionRequest();
        int affectedRows = 0;

        try{
            wsCommons.validaPeticion(request,response,true);
            //El codigo de colegio es el codigo externo de la institucion, buscamos el idinstitucion real
            CenInstitucion institucion = wsCommons.buscarColegio(request.getSancion().getDatosSancion().getColegioConsejo().getCodigoColegio());
            Short idInstitucion = null;
            if(institucion != null){
                idInstitucion = institucion.getIdinstitucion();
                log.info("SincronizacionEXEAServiceImpl.altaSancion() / Colegio encontrado: " + request.getSancion().getDatosSancion().getColegioConsejo().getCodigoColegio() + " - " + idInstitucion);
            }else{
                ErrorType errorType = response.addNewError();
                errorType.setCodigo(SigaConstants.ERROR_SINCRONIZACION_EXEA.COLEGIO_NOVALIDO.name());
                errorType.setDescripcion(SigaConstants.ERROR_SINCRONIZACION_EXEA.COLEGIO_NOVALIDO.getMensajeError());
                errorType.setXmlRequest("Sin error XML");
                log.error("SincronizacionEXEAServiceImpl.altaSancion() / ERROR CONTROLADO: Colegio inexsistente en BBDD - " + request.getSancion().getDatosSancion().getColegioConsejo().getCodigoColegio());
            }
            if(idInstitucion != null) {

                String identificacion;
                Long idPersona;

                if (!UtilidadesString.esCadenaVacia(request.getSancion().getIdentificacionColegiado().getNIF())) {
                    identificacion = request.getSancion().getIdentificacionColegiado().getNIF();
                    log.info("SincronizacionEXEAServiceImpl.altaSancion() / Informado NIF: " + identificacion);
                } else if (!UtilidadesString.esCadenaVacia(request.getSancion().getIdentificacionColegiado().getNIE())) {
                    identificacion = request.getSancion().getIdentificacionColegiado().getNIE();
                    log.info("SincronizacionEXEAServiceImpl.altaSancion() / Informado NIE: " + identificacion);
                } else {
                    identificacion = request.getSancion().getIdentificacionColegiado().getPasaporte();
                    log.info("SincronizacionEXEAServiceImpl.altaSancion() / Informado Pasaporte: " + identificacion);
                }

                idPersona = cenPersonaExtendsMapper.getIdPersonaWithNif(identificacion);

                if(idPersona != null){

                    affectedRows += insertarDatosSancion(request, response, idInstitucion, idPersona);

                    if(affectedRows > 0){

                        response.setNumeroExpediente(request.getNumeroExpediente());
                        log.info("SincronizacionEXEAServiceImpl.altaSancion() / Sancion creada satisfactoriamente, informamos numero de expediente en la respuesta");

                    }else if(response.getError() == null){

                        ErrorType errorType = response.addNewError();
                        errorType.setCodigo(SigaConstants.ERROR_SINCRONIZACION_EXEA.OTRO_ERROR.name());
                        errorType.setDescripcion(SigaConstants.ERROR_SINCRONIZACION_EXEA.OTRO_ERROR.getMensajeError());
                        log.error("SincronizacionEXEAServiceImpl.altaSancion() / Hubo un error creando la sancion");

                    }

                }else{
                    ErrorType errorType = response.addNewError();
                    errorType.setCodigo(SigaConstants.ERROR_SINCRONIZACION_EXEA.COLEGIADO_NOENCONTRADO.name());
                    errorType.setDescripcion(SigaConstants.ERROR_SINCRONIZACION_EXEA.COLEGIADO_NOENCONTRADO.getMensajeError());
                    errorType.setXmlRequest("Sin error XML");
                    log.error("SincronizacionEXEAServiceImpl.altaSancion() / ERROR: No se ha encontrado al colegiado con identificacion " + identificacion);
                }

            }

        }catch (ValidationException validationException) {
            log.error("SincronizacionEXEAServiceImpl.altaSancion() / Error: XML NO VALIDO", validationException);
        } catch (Exception e){
            log.error("SincronizacionEXEAServiceImpl.altaSancion() / ERROR NO CONTROLADO", e);
            throw new SigaExceptions(e,e.getMessage());
        }

        log.info("SincronizacionEXEAServiceImpl.altaSancion() / RESPONSE : " + responseDocument.xmlText());
        log.info("SincronizacionEXEAServiceImpl.altaSancion() - FIN");
        return responseDocument;
    }

    /**
     *
     * Metodo encargado de reincorporar un colegiado e insertar el estado nuevo
     *
     * @param colegiado
     * @param request
     * @param response
     * @param idInstitucion
     * @return
     */
    @Transactional
    private int reincorporarColegiado(CenColegiado colegiado,
                                      AltaColegiadoRequestDocument.AltaColegiadoRequest request,
                                      AltaColegiadoResponseDocument.AltaColegiadoResponse response, Short idInstitucion){
        int affectedRows = 0;
        TipoColegiacionType.Enum tipoColegiacionType = request.getColegiado().getColegiacion().getTipoSolicitud();
        TipoBoolean.Enum isResidente = request.getColegiado().getColegiacion().getResidente();

        //Comprobaremos si realmente está de baja
        CenDatoscolegialesestadoExample cenDatoscolegialesestadoExample = new CenDatoscolegialesestadoExample();
        cenDatoscolegialesestadoExample.createCriteria()
                .andIdpersonaEqualTo(colegiado.getIdpersona())
                .andIdinstitucionEqualTo(idInstitucion);
        cenDatoscolegialesestadoExample.setOrderByClause("FECHAESTADO DESC");
        List<CenDatoscolegialesestado> estados = cenDatoscolegialesestadoExtendsMapper.selectByExample(cenDatoscolegialesestadoExample);

        if(estados != null
                && !estados.isEmpty()) {
            CenDatoscolegialesestado lastEstado = estados.get(0);
            //Si su estado es baja colegial, cambiamos estado y ponemos la marca inscrito
            if (SigaConstants.ESTADO_COLEGIAL_BAJACOLEGIAL == lastEstado.getIdestado()) {
                lastEstado.setFechaestado(request.getColegiado().getColegiacion().getFechaIncorporacion().getTime());
                if (TipoColegiacionType.N.equals(tipoColegiacionType)
                        || TipoColegiacionType.I.equals(tipoColegiacionType)) {
                    lastEstado.setIdestado((short) SigaConstants.ESTADO_COLEGIAL_SINEJERCER);
                } else if (TipoColegiacionType.E.equals(tipoColegiacionType)) {
                    lastEstado.setIdestado((short) SigaConstants.ESTADO_COLEGIAL_EJERCIENTE);
                }
                lastEstado.setFechamodificacion(new Date());
                if (TipoBoolean.X_1.equals(isResidente)) {
                    lastEstado.setSituacionresidente(SigaConstants.DB_TRUE);
                } else {
                    lastEstado.setSituacionresidente(SigaConstants.DB_FALSE);
                }
                lastEstado.setObservaciones("Tramitada reincorporación por EXEA");
                lastEstado.setNumExpediente(request.getNumeroExpediente());

                CenColegiado cenColegiado = new CenColegiado();
                if(TipoColegiacionType.I.equals(tipoColegiacionType)){
                    cenColegiado.setComunitario("1");
                }else {
                    cenColegiado.setComunitario("0");
                }
                cenColegiado.setIdinstitucion(idInstitucion);
                cenColegiado.setIdpersona(colegiado.getIdpersona());
                //Ponemos la marca inscrito a 0 en CEN_COLEGIADO
                affectedRows += cenColegiadoExtendsMapper.updateByPrimaryKeySelective(cenColegiado);
                //Updateamos el estado en CEN_DATOSCOLEGIALESESTADO
                affectedRows += cenDatoscolegialesestadoExtendsMapper.insert(lastEstado);
                if (affectedRows <= 0) {
                    ErrorType errorType = response.addNewError();
                    errorType.setCodigo(SigaConstants.ERROR_SINCRONIZACION_EXEA.OTRO_ERROR.name());
                    errorType.setDescripcion("Error BBDD, no se actualizo ninguna fila en la reincorporacion");
                    errorType.setXmlRequest("Sin error XML");
                    log.error("SincronizacionEXEAServiceImpl.aprobarAltaColegiado() / Error en BBDD, no se actualizo ninguna fila en la reincorporacion");
                } else {
                    response.setNumeroExpediente(request.getNumeroExpediente());
                    log.info("SincronizacionEXEAServiceImpl.aprobarAltaColegiado() / Reincorporacion finalizada, se informa response y se devuelve");
                }
            } else {
                ErrorType errorType = response.addNewError();
                errorType.setCodigo(SigaConstants.ERROR_SINCRONIZACION_EXEA.OTRO_ERROR.name());
                errorType.setDescripcion("El colegiado no tiene el estado BAJA COLEGIAL");
                errorType.setXmlRequest("Sin error XML");
                log.error("SincronizacionEXEAServiceImpl.aprobarAltaColegiado() / El colegiado " + colegiado.getNcolegiado() + " no tiene el estado BAJA COLEGIAL");
            }
        }
        return affectedRows;
    }


    /**
     * Inserta o actualiza los datos personales del nuevo colegiado en CEN_PERSONA
     *
     * @param datosPersonalesType
     * @param idInstitucion
     * @return
     */
    @Transactional
    private Long insertarDatosPersonales(DatosPersonalesType datosPersonalesType, Short idInstitucion){

        log.info("SincronizacionEXEAServiceImpl.insertarDatosPersonales() - INICIO");
        Long idPersona = null;
        CenPersona datosPersonales = new CenPersona();
        String identificacion = "";
        Short idTipoIdentificacion = null;
        if (!UtilidadesString.esCadenaVacia(datosPersonalesType.getIdentificacion().getNIF())) {
            identificacion = datosPersonalesType.getIdentificacion().getNIF();
            idTipoIdentificacion = 10;
            log.info("SincronizacionEXEAServiceImpl.insertarDatosPersonales() / Informado NIF: " + identificacion);
        } else if (!UtilidadesString.esCadenaVacia(datosPersonalesType.getIdentificacion().getNIE())) {
            identificacion = datosPersonalesType.getIdentificacion().getNIE();
            idTipoIdentificacion = 40;
            log.info("SincronizacionEXEAServiceImpl.insertarDatosPersonales() / Informado NIE: " + identificacion);
        } else {
            identificacion = datosPersonalesType.getIdentificacion().getPasaporte();
            idTipoIdentificacion = 30;
            log.info("SincronizacionEXEAServiceImpl.insertarDatosPersonales() / Informado Pasaporte: " + identificacion);
        }

        CenPersonaExample cenPersonaExample = new CenPersonaExample();
        cenPersonaExample.createCriteria().andNifcifEqualTo(identificacion);
        List <CenPersona> busqueda = cenPersonaExtendsMapper.selectByExample(cenPersonaExample);
        //Si no se ha encontrado a nadie con su identificacion insertamos una persona nueva
        if(busqueda == null
            || busqueda.isEmpty()) {

            log.info("SincronizacionEXEAServiceImpl.insertarDatosPersonales() / No existe el NIF/NIE/Pasaporte en BBDD, se crea un registro nuevo en CEN_PERSONA");
            MaxIdDto personaID = cenPersonaExtendsMapper.selectMaxIdPersona2(idInstitucion.toString());

            datosPersonales.setIdpersona(personaID.getIdMax());
            datosPersonales.setNombre(datosPersonalesType.getNombre());
            datosPersonales.setApellidos1(datosPersonalesType.getApellido1());
            datosPersonales.setApellidos2(datosPersonalesType.getApellido2());
            datosPersonales.setFechamodificacion(new Date());
            if(datosPersonalesType.getFechaNacimiento() != null) {
                datosPersonales.setFechanacimiento(datosPersonalesType.getFechaNacimiento().getTime());
            }
            if(datosPersonalesType.getEstadoCivil() != null) {
                datosPersonales.setIdestadocivil(getIdEstadoCivilSIGAFromEnum(datosPersonalesType.getEstadoCivil()));
            }
            datosPersonales.setNifcif(identificacion);
            datosPersonales.setIdtipoidentificacion(idTipoIdentificacion);
            datosPersonales.setSexo(datosPersonalesType.getSexo().toString());
            datosPersonales.setFallecido("0");
            datosPersonales.setFechamodificacion(new Date());
            datosPersonales.setUsumodificacion(0);

            cenPersonaExtendsMapper.insert(datosPersonales);
            idPersona = datosPersonales.getIdpersona();

        }else{
            log.info("SincronizacionEXEAServiceImpl.insertarDatosPersonales() / NIF/NIE/Pasaporte encontrado en CEN_PERSONA, comprobamos registros en CEN_CLIENTE y actualizamos datos");
            //Si ya estaba registrado como persona en SIGA, comprobamos sus apariciones por todas las instituciones
            CenClienteExample ejemploCliente = new CenClienteExample();
            ejemploCliente.createCriteria().andIdpersonaEqualTo(busqueda.get(0).getIdpersona()).andIdinstitucionBetween(Short.valueOf("2001"), Short.valueOf("2100"));


            List<CenCliente> clienteExistente = cenClienteExtendsMapper.selectByExample(ejemploCliente);

            if(clienteExistente != null && !clienteExistente.isEmpty()) {
                log.info("SincronizacionEXEAServiceImpl.insertarDatosPersonales() / Encontrado en CEN_CLIENTE " + clienteExistente.size() + " veces");
                int numeroAparicionesSiga = 0;
                CenInstitucionExample exampleInstitucion = new CenInstitucionExample();
                exampleInstitucion.createCriteria().andFechaenproduccionIsNotNull();
                //Colegios de SIGA
                List<CenInstitucion> instituciones =cenInstitucionExtendsMapper.selectByExample(exampleInstitucion);
                List<Short> idInstituciones = new ArrayList<Short>();
                for (Iterator<CenInstitucion> iterator = instituciones.iterator(); iterator.hasNext();) {
                    CenInstitucion string = (CenInstitucion) iterator.next();
                    idInstituciones.add(string.getIdinstitucion());
                }
                for (Iterator<CenCliente> iterator = clienteExistente.iterator(); iterator.hasNext();) {
                    CenCliente cenCliente = (CenCliente) iterator.next();
                    if (idInstituciones.contains(cenCliente.getIdinstitucion()) && !cenCliente.getIdinstitucion().equals(idInstitucion)) {
                        numeroAparicionesSiga++;
                    }
                }
                log.info("SincronizacionEXEAServiceImpl.insertarDatosPersonales() / Ha aparecido en otras instituciones " + numeroAparicionesSiga + " veces");
                //Si no ha aparecido como cliente en otras instituciones, updateamos sus datos con los de la peticion
                if (numeroAparicionesSiga < 1) {
                    log.info("SincronizacionEXEAServiceImpl.insertarDatosPersonales() / Al no haber aparecido ninguna vez, actualizamos sus datos");
                    CenPersona personaUpdate = busqueda.get(0);
                    personaUpdate.setNombre(datosPersonalesType.getNombre());
                    personaUpdate.setApellidos1(datosPersonalesType.getApellido1());
                    personaUpdate.setApellidos2(datosPersonalesType.getApellido2());
                    personaUpdate.setFechamodificacion(new Date());
                    if(datosPersonalesType.getFechaNacimiento() != null) {
                        personaUpdate.setFechanacimiento(datosPersonalesType.getFechaNacimiento().getTime());
                    }
                    if(datosPersonalesType.getEstadoCivil() != null) {
                        personaUpdate.setIdestadocivil(getIdEstadoCivilSIGAFromEnum(datosPersonalesType.getEstadoCivil()));
                    }
                    personaUpdate.setIdtipoidentificacion(idTipoIdentificacion);
                    personaUpdate.setNifcif(identificacion);
                    personaUpdate.setSexo(datosPersonalesType.getSexo().toString());
                    personaUpdate.setUsumodificacion(0);
                    cenPersonaExtendsMapper.updateByPrimaryKey(personaUpdate);
                }
            }else{
                if (clienteExistente.size() == 1) {

                    //Si solo ha aparecido una vez y en la misma institucion que la de la peticion, updateamos sus datos
                    if (clienteExistente.get(0).getIdinstitucion().equals(idInstitucion)) {
                        log.info("SincronizacionEXEAServiceImpl.insertarDatosPersonales() / Ha aparecido una vez y ha sido en la misma institucion de la peticion, actualizamos datos");
                        CenPersona personaUpdate = busqueda.get(0);
                        personaUpdate.setNombre(datosPersonalesType.getNombre());
                        personaUpdate.setApellidos1(datosPersonalesType.getApellido1());
                        personaUpdate.setApellidos2(datosPersonalesType.getApellido2());
                        personaUpdate.setFechamodificacion(new Date());
                        if(datosPersonalesType.getFechaNacimiento() != null) {
                            personaUpdate.setFechanacimiento(datosPersonalesType.getFechaNacimiento().getTime());
                        }
                        if(datosPersonalesType.getEstadoCivil() != null) {
                            personaUpdate.setIdestadocivil(getIdEstadoCivilSIGAFromEnum(datosPersonalesType.getEstadoCivil()));
                        }
                        personaUpdate.setIdtipoidentificacion(idTipoIdentificacion);
                        personaUpdate.setNifcif(identificacion);
                        personaUpdate.setSexo(datosPersonalesType.getSexo().toString());
                        personaUpdate.setUsumodificacion(0);
                        cenPersonaExtendsMapper.updateByPrimaryKey(personaUpdate);
                    }else{
                        //Si ha aparecido una vez pero no en la misma institucion, la buscamos y si existe updateamos los datos de la persona
                        CenInstitucionExample exampleInstitucion = new CenInstitucionExample();
                        exampleInstitucion.createCriteria().andFechaenproduccionIsNotNull().andIdinstitucionEqualTo(clienteExistente.get(0).getIdinstitucion());
                        //Colegios de SIGA
                        List<CenInstitucion> instituciones = cenInstitucionExtendsMapper.selectByExample(exampleInstitucion);
                        log.info("SincronizacionEXEAServiceImpl.insertarDatosPersonales() / Ha aparecido una vez pero en distinta institucion, si no existe entonces actualizamos si no, no");
                        if (instituciones.isEmpty()) {
                            CenPersona personaUpdate = busqueda.get(0);
                            personaUpdate.setNombre(datosPersonalesType.getNombre());
                            personaUpdate.setApellidos1(datosPersonalesType.getApellido1());
                            personaUpdate.setApellidos2(datosPersonalesType.getApellido2());
                            personaUpdate.setFechamodificacion(new Date());
                            if(datosPersonalesType.getFechaNacimiento() != null) {
                                personaUpdate.setFechanacimiento(datosPersonalesType.getFechaNacimiento().getTime());
                            }
                            if(datosPersonalesType.getEstadoCivil() != null) {
                                personaUpdate.setIdestadocivil(getIdEstadoCivilSIGAFromEnum(datosPersonalesType.getEstadoCivil()));
                            }
                            personaUpdate.setIdtipoidentificacion(idTipoIdentificacion);
                            personaUpdate.setNifcif(identificacion);
                            personaUpdate.setSexo(datosPersonalesType.getSexo().toString());
                            personaUpdate.setUsumodificacion(0);
                            cenPersonaExtendsMapper.updateByPrimaryKey(personaUpdate);
                        }
                    }

                }else{
                    //Si no ha aparecido como cliente en ninguna institucion, buscamos en la institucion general (2000)
                    log.info("SincronizacionEXEAServiceImpl.insertarDatosPersonales() / No ha aparecido como cliente en ninguna institucion, buscamos en la general 2000");
                    ejemploCliente = new CenClienteExample();
                    ejemploCliente.createCriteria().andIdpersonaEqualTo(busqueda.get(0).getIdpersona()).andIdinstitucionEqualTo(Short.valueOf(SigaConstants.InstitucionGeneral));
                    List<CenCliente> clienteExistente2 = cenClienteExtendsMapper.selectByExample(ejemploCliente);
                    if (!clienteExistente2.isEmpty()){
                        log.info("SincronizacionEXEAServiceImpl.insertarDatosPersonales() / Se ha encontrado en la institucion 2000 en CEN_CLIENTE, actualizamos");
                        CenPersona personaUpdate = busqueda.get(0);
                        personaUpdate.setNombre(datosPersonalesType.getNombre());
                        personaUpdate.setApellidos1(datosPersonalesType.getApellido1());
                        personaUpdate.setApellidos2(datosPersonalesType.getApellido2());
                        personaUpdate.setFechamodificacion(new Date());
                        if(datosPersonalesType.getFechaNacimiento() != null) {
                            personaUpdate.setFechanacimiento(datosPersonalesType.getFechaNacimiento().getTime());
                        }
                        if(datosPersonalesType.getEstadoCivil() != null) {
                            personaUpdate.setIdestadocivil(getIdEstadoCivilSIGAFromEnum(datosPersonalesType.getEstadoCivil()));
                        }
                        personaUpdate.setIdtipoidentificacion(idTipoIdentificacion);
                        personaUpdate.setNifcif(identificacion);
                        personaUpdate.setSexo(datosPersonalesType.getSexo().toString());
                        personaUpdate.setUsumodificacion(0);
                        cenPersonaExtendsMapper.updateByPrimaryKey(personaUpdate);

                    }

                }
            }
            idPersona = busqueda.get(0).getIdpersona();
        }

        log.info("SincronizacionEXEAServiceImpl.insertarDatosPersonales() / idPersona: " + idPersona);
        log.info("SincronizacionEXEAServiceImpl.insertarDatosPersonales() / FIN");
        return idPersona;
    }

    /**
     * Metodo para insertar/actualizar los datos en CEN_CLIENTE
     *
     * @param idPersona
     * @param idInstitucion
     * @return
     */
    @Transactional
    private int insertarDatosCliente(Long idPersona, Short idInstitucion){

        log.info("SincronizacionEXEAServiceImpl.insertarDatosCliente() - INICIO");
        int affectedRows = 0;
        CenCliente cliente = new CenCliente();
        CenClienteKey ejemploCliente = new CenClienteKey();
        ejemploCliente.setIdpersona(idPersona);
        ejemploCliente.setIdinstitucion(idInstitucion);

        CenCliente clienteExistente = cenClienteExtendsMapper.selectByPrimaryKey(ejemploCliente);

        if(null != clienteExistente) {
            log.info("SincronizacionEXEAServiceImpl.insertarDatosCliente() / Tiene registro en CEN_CLIENTE, actualizamos datos para ponerlo como letrado");
            CenNocolegiadoKey key = new CenNocolegiadoKey();
            key.setIdinstitucion(idInstitucion);
            key.setIdpersona(idPersona);
            CenNocolegiado noColegiado = cenNocolegiadoExtendsMapper.selectByPrimaryKey(key);

            if(noColegiado != null) {
                log.info("SincronizacionEXEAServiceImpl.insertarDatosCliente() / Tiene registro en CEN_NOCOLEGIADO, lo damos de baja en la tabla");
                noColegiado.setFechaBaja(new Date());
                noColegiado.setFechamodificacion(new Date());
                noColegiado.setUsumodificacion(0);
                cenNocolegiadoExtendsMapper.updateByPrimaryKey(noColegiado);
            }

            CenCliente clienteupdate = clienteExistente;
            clienteupdate.setLetrado("1");
            clienteupdate.setIdtratamiento(Short.valueOf(SigaConstants.DB_TRUE)); // 1
            clienteupdate.setFechamodificacion(new Date());
            clienteupdate.setUsumodificacion(0);
            affectedRows += cenClienteExtendsMapper.updateByPrimaryKey(clienteupdate);
        }else {
            log.info("SincronizacionEXEAServiceImpl.insertarDatosCliente() / No tiene registro en CEN_CLIENTE, insertamos como letrado");
            cliente.setIdpersona(idPersona);
            cliente.setIdinstitucion(idInstitucion);
            cliente.setFechaalta(new Date());
            cliente.setCaracter("P");
            cliente.setPublicidad(SigaConstants.DB_FALSE);
            cliente.setGuiajudicial(SigaConstants.DB_FALSE);
            cliente.setComisiones(SigaConstants.DB_FALSE);
            cliente.setIdtratamiento(Short.valueOf(SigaConstants.DB_TRUE)); // 1
            cliente.setFechamodificacion(new Date());
            cliente.setUsumodificacion(0);
            cliente.setIdlenguaje("1");
            cliente.setExportarfoto(SigaConstants.DB_FALSE);
            cliente.setLetrado("1");
            cliente.setNoenviarrevista("0");
            cliente.setNoaparecerredabogacia("0");
            cliente.setFechacarga(new Date());
            affectedRows += cenClienteExtendsMapper.insert(cliente);
        }
        log.info("SincronizacionEXEAServiceImpl.insertarDatosCliente() - FIN");
        return affectedRows;
    }

    /**
     * Método para insertar todos los datos relacionados con la direccion del nuevo colegiado
     *
     * @param localizacionType
     * @param idInstitucion
     * @param tipoColegiacion
     * @return
     */
    @Transactional
    private Long insertarDatosDireccion (LocalizacionType localizacionType, Short idInstitucion, TipoColegiacionType.Enum tipoColegiacion, Long idPersona){

        log.info("SincronizacionEXEAServiceImpl.insertarDatosDireccion() - INICIO");
        Long idDireccion = null;

        CenDirecciones direccion = new CenDirecciones();

        //Creamos la direccion con los tipos de direccion oblogatorios
        List<DatosDireccionesItem> direccionID = cenDireccionesExtendsMapper.selectNewIdDireccion(idPersona.toString(),idInstitucion.toString());
        idDireccion = Long.valueOf(direccionID.get(0).getIdDireccion());
        log.info("SincronizacionEXEAServiceImpl.insertarDatosDireccion() / Siguiente ID direccion obtenido: " + idDireccion);
        direccion.setIddireccion(idDireccion);
        if(localizacionType.getNacional() != null) {

            log.info("SincronizacionEXEAServiceImpl.insertarDatosDireccion() / Es nacional");
            direccion.setCodigopostal(localizacionType.getNacional().getCodigoPostal());
            direccion.setIdprovincia(localizacionType.getNacional().getProvincia().getCodigoProvincia());

            if(!UtilidadesString.esCadenaVacia(localizacionType.getNacional().getPoblacion().getCodigoPoblacion())){

                direccion.setIdpoblacion(localizacionType.getNacional().getPoblacion().getCodigoPoblacion());

            } else if(UtilidadesString.esCadenaVacia(localizacionType.getNacional().getPoblacion().getCodigoPoblacion())
                && !UtilidadesString.esCadenaVacia(localizacionType.getNacional().getPoblacion().getDescripcionPoblacion())){

                direccion.setIdpoblacion(getIdPoblacionFromDescripcion(localizacionType.getNacional().getPoblacion().getDescripcionPoblacion()));

            }

        } else if(localizacionType.getExtranjero() != null){

            log.info("SincronizacionEXEAServiceImpl.insertarDatosDireccion() / Es extranjero");
            direccion.setCodigopostal(localizacionType.getExtranjero().getCodigoPostal());
            direccion.setPoblacionextranjera(localizacionType.getExtranjero().getPoblacion());
            direccion.setIdpais(getIdPaisFromCodISO(localizacionType.getExtranjero().getPais().getCodigoPais()));

        }
        direccion.setCorreoelectronico(localizacionType.getEmail());
        direccion.setDomicilio(localizacionType.getDomicilio());

        if(localizacionType.getContacto() != null
                && localizacionType.getContacto().getFax1() != null
                && !UtilidadesString.esCadenaVacia(localizacionType.getContacto().getFax1().getStringValue())) {
            direccion.setFax1(localizacionType.getContacto().getFax1().getStringValue());
        }
        if(localizacionType.getContacto() != null
                && localizacionType.getContacto().getFax2() != null
                && !UtilidadesString.esCadenaVacia(localizacionType.getContacto().getFax2().getStringValue())){
            direccion.setFax2(localizacionType.getContacto().getFax2().getStringValue());
        }

        direccion.setFechamodificacion(new Date());
        direccion.setIdinstitucion(idInstitucion);
        direccion.setIdpersona(idPersona);

        if(localizacionType.getContacto() != null
                && localizacionType.getContacto().getTelefono1() != null
                && !UtilidadesString.esCadenaVacia(localizacionType.getContacto().getTelefono1().getStringValue())) {
            direccion.setTelefono1(localizacionType.getContacto().getTelefono1().getStringValue());
        }

        if(localizacionType.getContacto() != null
                && localizacionType.getContacto().getTelefono2() != null
                && !UtilidadesString.esCadenaVacia(localizacionType.getContacto().getTelefono2().getStringValue())) {
            direccion.setTelefono2(localizacionType.getContacto().getTelefono2().getStringValue());
        }

        if(localizacionType.getContacto() != null
                && localizacionType.getContacto().getMovil() != null
                && !UtilidadesString.esCadenaVacia(localizacionType.getContacto().getMovil().getStringValue())) {
            direccion.setMovil(localizacionType.getContacto().getMovil().getStringValue());
        }

        direccion.setUsumodificacion(0);
        direccion.setPreferente("ECS");

        cenDireccionesExtendsMapper.insert(direccion);

        log.info("SincronizacionEXEAServiceImpl.insertarDatosDireccion() / Una vez insertados los datos en CEN_DIRECCIONES, insertamos en CEN_DIRECCION_TIPODIRECCION");
        CenDireccionTipodireccion tipoDireccion =  new CenDireccionTipodireccion();
        tipoDireccion.setFechamodificacion(new Date());
        tipoDireccion.setIddireccion(direccion.getIddireccion());
        tipoDireccion.setIdinstitucion(idInstitucion);
        tipoDireccion.setIdpersona(idPersona);
        tipoDireccion.setUsumodificacion(0);

        //Añadimos los tipo de direcciones obligatorios
        tipoDireccion.setIdtipodireccion(Short.valueOf(SigaConstants.TIPO_DIR_TRASPASO));
        cenDireccionTipodireccionExtendsMapper.insert(tipoDireccion );
        tipoDireccion.setIdtipodireccion(Short.valueOf(SigaConstants.TIPO_DIR_FACTURACION));
        cenDireccionTipodireccionExtendsMapper.insert(tipoDireccion );
        tipoDireccion.setIdtipodireccion(Short.valueOf(SigaConstants.TIPO_DIR_CENSOWEB));
        cenDireccionTipodireccionExtendsMapper.insert(tipoDireccion );
        tipoDireccion.setIdtipodireccion(Short.valueOf(SigaConstants.TIPO_DIR_GUIAJUDICIAL));
        cenDireccionTipodireccionExtendsMapper.insert(tipoDireccion );
        tipoDireccion.setIdtipodireccion(Short.valueOf(SigaConstants.TIPO_DIR_GUARDIA));
        cenDireccionTipodireccionExtendsMapper.insert(tipoDireccion );

        tipoDireccion.setIdtipodireccion(Short.valueOf(SigaConstants.TIPO_DIR_PREFERENTE_EMAIL));
        cenDireccionTipodireccionExtendsMapper.insert(tipoDireccion );
        tipoDireccion.setIdtipodireccion(Short.valueOf(SigaConstants.TIPO_DIR_PREFERENTE_CORREO));
        cenDireccionTipodireccionExtendsMapper.insert(tipoDireccion );
        tipoDireccion.setIdtipodireccion(Short.valueOf(SigaConstants.TIPO_DIR_PREFERENTE_SMS));
        cenDireccionTipodireccionExtendsMapper.insert(tipoDireccion );

        //Si el tipo de solicitud es Ejerciente o No Ejerciente, insertamos el tipo direccion Despacho
        if(TipoColegiacionType.E.equals(tipoColegiacion)
            || TipoColegiacionType.N.equals(tipoColegiacion)){

            log.info("SincronizacionEXEAServiceImpl.insertarDatosDireccion() / Es ejerciente o no ejerciente, insertamos tipo direccion Despacho");
            tipoDireccion.setIdtipodireccion(Short.valueOf(SigaConstants.TIPO_DIR_DESPACHO));
            cenDireccionTipodireccionExtendsMapper.insert(tipoDireccion );

        }

        log.info("SincronizacionEXEAServiceImpl.insertarDatosDireccion() - FIN");
        return idDireccion;
    }

    /**
     * Metodo encargado de insertar los datos relacionados con el colegiado
     *
     * @param colegiacionType
     * @param idInstitucion
     * @param idPersona
     * @return
     */
    @Transactional
    private int insertarDatosColegiado (ColegiacionType colegiacionType, Short idInstitucion, Long idPersona, String numColegiado, String numExpediente){

        log.info("SincronizacionEXEAServiceImpl.insertarDatosColegiado() - INICIO");
        int affectedRows = 0;
        String nColegiado = null;
        CenColegiado colegiado = new CenColegiado();

        colegiado.setIdpersona(idPersona);
        colegiado.setFechaincorporacion(colegiacionType.getFechaIncorporacion().getTime());
        colegiado.setFechamodificacion(new Date());
        colegiado.setIdinstitucion(idInstitucion);

        if(UtilidadesString.esCadenaVacia(numColegiado)){
            log.info("SincronizacionEXEAServiceImpl.insertarDatosColegiado() / No viene informado el numero de colegiado, buscamos el siguiente");
            // Comprobamos si para la institucion tiene un contador unico
            GenParametrosKey key = new GenParametrosKey();
            key.setIdinstitucion(idInstitucion);
            key.setModulo(SigaConstants.MODULO_CENSO);
            key.setParametro(SigaConstants.PARAMETRO_CONTADOR_UNICO);
            GenParametros genParametro = genParametrosExtendsMapper.selectByPrimaryKey(key);

            String contadorUnico = genParametro == null || genParametro.getValor() == null ? "0" : genParametro.getValor();

            StringDTO stringDTO = new StringDTO();

            if (SigaConstants.DB_TRUE.equals(contadorUnico)) {

                stringDTO = cenSolicitudincorporacionExtendsMapper.getMaxNColegiadoComunitario(idInstitucion.toString());

            } else {

                if(TipoColegiacionType.E.equals(colegiacionType.getTipoSolicitud())
                    || TipoColegiacionType.N.equals(colegiacionType.getTipoSolicitud())) {

                    stringDTO = cenSolicitudincorporacionExtendsMapper.getMaxNColegiado(idInstitucion.toString());

                }else if(TipoColegiacionType.I.equals(colegiacionType.getTipoSolicitud())) {

                    stringDTO = cenSolicitudincorporacionExtendsMapper.getMaxNComunitario(idInstitucion.toString());

                }
            }
            colegiacionType.setNumColegiado(nColegiado);
            nColegiado = stringDTO.getValor();
            log.info("SincronizacionEXEAServiceImpl.insertarDatosColegiado() / Numero de colegiado obtenido: " + nColegiado);
        }else{
            nColegiado = numColegiado;
        }

        colegiado.setUsumodificacion(0);
        colegiado.setFechapresentacion(new Date());

        if(TipoColegiacionType.E.equals(colegiacionType.getTipoSolicitud())
                || TipoColegiacionType.N.equals(colegiacionType.getTipoSolicitud())) {

            log.info("SincronizacionEXEAServiceImpl.insertarDatosColegiado() / Es ejerciente o no ejerciente, ponemos marca comunitario a 0 y rellenamos numColegiado");
            colegiado.setComunitario(SigaConstants.DB_FALSE);
            colegiado.setNcolegiado(nColegiado);

        }else if(TipoColegiacionType.I.equals(colegiacionType.getTipoSolicitud())) {

            log.info("SincronizacionEXEAServiceImpl.insertarDatosColegiado() / Es inscrito, ponemos marca comunitario a 1 y rellenamos numComunitario");
            colegiado.setComunitario(SigaConstants.DB_TRUE);
            colegiado.setNcomunitario(nColegiado);

        }
        colegiado.setJubilacioncuota("0");
        colegiado.setIndtitulacion("1");
        colegiado.setSituacionejercicio("0");
        colegiado.setSituacionempresa("0");
        if(TipoBoolean.X_1.equals(colegiacionType.getResidente())){
            colegiado.setSituacionresidente(SigaConstants.DB_TRUE);
        }else{
            colegiado.setSituacionresidente(SigaConstants.DB_FALSE);
        }

        CenColegiadoExample ejemploColegiado = new CenColegiadoExample();
        ejemploColegiado.createCriteria().andIdpersonaEqualTo(idPersona).andIdinstitucionEqualTo(idInstitucion);

        List <CenColegiado> listaColegiados = cenColegiadoExtendsMapper.selectByExample(ejemploColegiado);
        //Si no esta ya dado de alto la persona como colegiado en la institucion, insertamos
        if(listaColegiados.isEmpty()) {

            affectedRows += cenColegiadoExtendsMapper.insert(colegiado);

            if(affectedRows > 0){
                log.info("SincronizacionEXEAServiceImpl.insertarDatosColegiado() / Una vez insertamos en CEN_COLEGIADO, insertamos en CEN_DATOSCOLEGIALESESTADO y por consecuencia en CEN_HISTORICO");
                CenDatoscolegialesestado datosColegiales = new CenDatoscolegialesestado();
                datosColegiales.setFechaestado(colegiacionType.getFechaIncorporacion().getTime());
                datosColegiales.setFechamodificacion(new Date());

                if (TipoColegiacionType.E.equals(colegiacionType.getTipoSolicitud())
                        || TipoColegiacionType.N.equals(colegiacionType.getTipoSolicitud())) {

                    datosColegiales.setIdestado(Short.valueOf("20"));

                }else{

                    datosColegiales.setIdestado(Short.valueOf("10"));

                }

                if(TipoBoolean.X_1.equals(colegiacionType.getResidente())) {
                    datosColegiales.setSituacionresidente("1");
                }else {
                    datosColegiales.setSituacionresidente("0");
                }
                datosColegiales.setIdinstitucion(idInstitucion);
                datosColegiales.setIdpersona(idPersona);
                datosColegiales.setUsumodificacion(0);
                datosColegiales.setObservaciones("Tramitada alta por expediente de colegiacion EXEA");
                //Asociamos el expediente de EXEA al colegiado
                datosColegiales.setNumExpediente(numExpediente);
                affectedRows += cenDatoscolegialesestadoExtendsMapper.insert(datosColegiales );

                if(affectedRows == 2){ //Una vez hecho insert en CEN_COLEGIADO y CEN_DATOSCOLEGIALESESTADO dejamos registro en CEN_HISTORICO

                    log.info("SincronizacionEXEAServiceImpl.insertarDatosColegiado() / Insertamos en CEN_HISTORICO");
                    CenHistorico cenHistorico = new CenHistorico();
                    NewIdDTO idHistorico = cenHistoricoExtendsMapper.selectMaxIDHistoricoByPerson(idPersona.toString(), idInstitucion.toString());

                    cenHistorico.setIdhistorico(Short.valueOf(idHistorico.getNewId()));
                    cenHistorico.setIdtipocambio((short)SigaConstants.TIPO_CAMBIO_HISTORICO_ESTADO_ALTA_COLEGIAL);
                    cenHistorico.setIdpersona(idPersona);
                    cenHistorico.setFechaefectiva(colegiacionType.getFechaIncorporacion().getTime());
                    cenHistorico.setFechamodificacion(new Date());
                    cenHistorico.setFechaentrada(colegiacionType.getFechaIncorporacion().getTime());
                    cenHistorico.setIdinstitucion(idInstitucion);
                    cenHistorico.setDescripcion("Alta de colegiacion por Expediente Colegiacion EXEA");
                    cenHistorico.setUsumodificacion(0);

                    affectedRows += cenHistoricoExtendsMapper.insert(cenHistorico);
                }
            }
        }
        log.info("SincronizacionEXEAServiceImpl.insertarDatosColegiado() - FIN");
        return affectedRows;
    }

    /**
     * Metodo que se encarga de insertar los datos bancarios del colegiado si los tuviera
     *
     * @param datosBancarios
     * @param idPersona
     * @param idInstitucion
     * @return
     */
    @Transactional
    private Short insertarDatosBancarios(BancoType datosBancarios, Long idPersona, Short idInstitucion, DatosPersonalesType datosPersonalesType){

        log.info("SincronizacionEXEAServiceImpl.insertarDatosBancarios() - INICIO");
        String titularCuenta = datosPersonalesType.getNombre().toUpperCase() + " " + datosPersonalesType.getApellido1().toUpperCase();
        if(!UtilidadesString.esCadenaVacia(datosPersonalesType.getApellido2())){
            titularCuenta += " " + datosPersonalesType.getApellido2().toUpperCase();
        }
        CenCuentasbancarias cuenta = new CenCuentasbancarias();
        Short idCuenta = Short.valueOf(cenCuentasbancariasExtendsMapper.selectMaxID(idPersona, idInstitucion).getIdMax().toString());
        cuenta.setIdcuenta(idCuenta);
        cuenta.setAbonosjcs("0");
        cuenta.setFechamodificacion(new Date());
        cuenta.setIdinstitucion(idInstitucion);
        cuenta.setIdpersona(idPersona);
        //cuenta.setNumerocuenta(datosBancarios.getBanco()); No hay numero de cuenta en la peticion
        cuenta.setTitular(titularCuenta);
        cuenta.setIban(datosBancarios.getIBAN());
        cuenta.setUsumodificacion(0);

        //Si es IBAN espaniol, rellenamos datos adicionales
        if (cuenta.getIban().startsWith("ES")) {
            log.info("SincronizacionEXEAServiceImpl.insertarDatosBancarios() / Cuenta bancaria espaniola");
            cuenta.setCboCodigo(datosBancarios.getIBAN().substring(4, 8));
            cuenta.setCodigosucursal(datosBancarios.getIBAN().substring(8, 12));
            cuenta.setDigitocontrol(datosBancarios.getIBAN().substring(12, 14));
            cuenta.setNumerocuenta(datosBancarios.getIBAN().substring(14, 24));
        }

        CenBancosExample cenBancosExample = new CenBancosExample();
        cenBancosExample.createCriteria().andBicEqualTo(datosBancarios.getBIC())
                .andNombreEqualTo(datosBancarios.getBanco());
        List<CenBancos> cenBancos = cenBancosExtendsMapper.selectByExample(cenBancosExample);
        //Buscamos el banco cen CEN_BANCOS, y si no esta registrado, lo insertamos
        if (null != cenBancos && !cenBancos.isEmpty()) {
            log.info("SincronizacionEXEAServiceImpl.insertarDatosBancarios() / Banco existente en BBDD");
            cuenta.setCboCodigo(cenBancos.get(0).getCodigo()); // Tanto si es ext o esp tiene cod en cenBancos
        } else {

            log.info("SincronizacionEXEAServiceImpl.insertarDatosBancarios() / Banco no registrado en BBDD, insertamos en CEN_BANCOS");
            NewIdDTO newIdDTO = cenBancosExtendsMapper.getMaxCode();

            CenBancos record = new CenBancos();

            if (!datosBancarios.getIBAN().startsWith("ES")) {
                log.info("SincronizacionEXEAServiceImpl.insertarDatosBancarios() / Banco Extranjero");
                String rdo = UtilidadesString.fillCadena(newIdDTO.getNewId(), 5, "0");
                record.setCodigo(rdo);
                record.setNombre("BANCO EXTRANJERO");
            } else {
                log.info("SincronizacionEXEAServiceImpl.insertarDatosBancarios() / Banco Espaniol");
                record.setCodigo(datosBancarios.getIBAN().substring(4, 8));
                record.setNombre(datosBancarios.getBanco());

            }

            record.setBic(datosBancarios.getBIC());
            record.setFechamodificacion(new Date());

            CenPaisExample cenPaisExample = new CenPaisExample();
            cenPaisExample.createCriteria().andCodIsoEqualTo(datosBancarios.getIBAN().substring(0, 2));
            List<CenPais> cenPais = cenPaisExtendsMapper.selectByExample(cenPaisExample);

            if (null != cenPais && !cenPais.isEmpty()) {
                record.setIdpais(cenPais.get(0).getIdpais());
            }

            record.setUsumodificacion(0);
            log.info("SincronizacionEXEAServiceImpl.insertarDatosBancarios() / Insertamos el banco si no existe con el codigo formado");
            if(cenBancosExtendsMapper.selectByPrimaryKey(record.getCodigo()) == null) {
                cenBancosExtendsMapper.insert(record);
            }
            cuenta.setCboCodigo(record.getCodigo());
        }
        log.info("SincronizacionEXEAServiceImpl.insertarDatosBancarios() / Insertamos cuenta bancaria");
        cenCuentasbancariasExtendsMapper.insertSelective(cuenta);

        log.info("SincronizacionEXEAServiceImpl.insertarDatosBancarios() - FIN");
        return idCuenta;
    }

    /**
     * Metodo encargado de insertar los datos de la sancion
     *
     * @param request
     * @param idInstitucion
     * @param idPersona
     * @return
     */
    @Transactional
    private int insertarDatosSancion(AltaSancionRequestDocument.AltaSancionRequest request, AltaSancionResponseDocument.AltaSancionResponse response, Short idInstitucion, Long idPersona){
        log.info("SincronizacionEXEAServiceImpl.insertarDatosSancion() - INICIO");
        int affectedRows = 0;
        CenSancion cenSancion = new CenSancion();
        NewIdDTO idSancion = cenSancionExtendsMapper
                .getMaxIdSancion(String.valueOf(idInstitucion), String.valueOf(idPersona));
        Short idTipoSancion = getIdSancionFromCdgoExt(request.getSancion().getDatosSancion().getTipoSancion(), response);

        if(idTipoSancion != null) {

            if (idSancion == null) {
                cenSancion.setIdsancion((long) 1);
            } else {
                int idS = Integer.parseInt(idSancion.getNewId()) + 1;
                cenSancion.setIdsancion((long) idS);
            }
            log.info("SincronizacionEXEAServiceImpl.insertarDatosSancion() / ID generado para la sancion: " + cenSancion.getIdsancion());
            cenSancion.setIdpersona(idPersona);
            cenSancion.setIdinstitucion(idInstitucion);
            cenSancion.setIdinstitucionsancion(idInstitucion);
            cenSancion.setIdtiposancion(idTipoSancion);
            cenSancion.setRefcolegio(request.getSancion().getDatosSancion().getRefSancion());
            cenSancion.setFechaacuerdo(request.getSancion().getDatosSancion().getFechaAcuerdo().getTime());
            cenSancion.setFechainicio(request.getSancion().getDatosSancion().getFechaInicio().getTime());
            cenSancion.setNumExpediente(request.getNumeroExpediente());

            if(request.getSancion().getDatosSancion().getFechaFin() != null){
                cenSancion.setFechafin(request.getSancion().getDatosSancion().getFechaFin().getTime());
            }

            if(request.getSancion().getDatosSancion().getDatosFirmeza() != null
                && request.getSancion().getDatosSancion().getDatosFirmeza().getFecha() != null){

                cenSancion.setFechafirmeza(request.getSancion().getDatosSancion().getDatosFirmeza().getFecha().getTime());

            }

            if(request.getSancion().getDatosSancion().getDatosFirmeza() != null
                    && request.getSancion().getDatosSancion().getDatosFirmeza().getCheck() != null){

                cenSancion.setChkfirmeza(request.getSancion().getDatosSancion().getDatosFirmeza().getCheck().toString());

            }
            cenSancion.setChkarchivada(SigaConstants.DB_FALSE);
            cenSancion.setChkrehabilitado(SigaConstants.DB_FALSE);
            cenSancion.setUsumodificacion(0);
            cenSancion.setFechamodificacion(new Date());
            cenSancion.setTexto(request.getSancion().getDatosSancion().getTexto());
            cenSancion.setObservaciones(request.getSancion().getDatosSancion().getObservaciones());

            log.info("SincronizacionEXEAServiceImpl.insertarDatosSancion() / Insertamos en la institucion " + idInstitucion + " y para el colegiado con idpersona " + idPersona);
            affectedRows += cenSancionExtendsMapper.insert(cenSancion);

            //Si no es firme, se crea una para la institucion general
            //FIXME: REVISAR ESTO, YA QUE EN EL XSD SOLO DEJA INFORMAR O FECHA FIRMEZA O EL CHECK FIRMEZA
            if(request.getSancion().getDatosSancion().getDatosFirmeza() == null
                    || (request.getSancion().getDatosSancion().getDatosFirmeza().getFecha() != null
                    && TipoBoolean.X_0.equals(request.getSancion().getDatosSancion().getDatosFirmeza().getCheck())
                    && !SigaConstants.InstitucionGeneral.equals(idInstitucion.toString()))){

                CenSancion sancionInstitucionGeneral = cenSancion;

                sancionInstitucionGeneral.setIdsancionorigen(cenSancion.getIdsancion());
                sancionInstitucionGeneral.setIdinstitucion(Short.valueOf(SigaConstants.InstitucionGeneral));
                sancionInstitucionGeneral.setIdinstitucionsancion(idInstitucion);
                sancionInstitucionGeneral.setIdinstitucionorigen(idInstitucion);

                log.info("SincronizacionEXEAServiceImpl.insertarDatosSancion() / Insertamos en la institucion 2000");

                affectedRows += cenSancionExtendsMapper.insertSelective(sancionInstitucionGeneral);

            }

        }else{
            ErrorType errorType = response.addNewError();
            errorType.setCodigo(SigaConstants.ERROR_SINCRONIZACION_EXEA.OTRO_ERROR.name());
            errorType.setDescripcion("Tipo de sancion no encontrada en el sistema");
            errorType.setXmlRequest("Sin error XML");
            log.error("SincronizacionEXEAServiceImpl.insertarDatosSancion() / Tipo de sancion no encontrada en el sistema, devolvemos error");
        }
        return  affectedRows;
    }

    private Short getIdSancionFromCdgoExt(AltaSancionRequestDocument.AltaSancionRequest.Sancion.DatosSancion.TipoSancion tipoSancion, AltaSancionResponseDocument.AltaSancionResponse response){
        Short idSancion = null;
        List<CenTiposancion> tipoSanciones;
        if(!UtilidadesString.esCadenaVacia(tipoSancion.getCodigo())){

            CenTiposancionExample cenTiposancionExample = new CenTiposancionExample();
            cenTiposancionExample.createCriteria().andCodigoextEqualTo(tipoSancion.getCodigo());

            tipoSanciones = cenTiposancionExtendsMapper.selectByExample(cenTiposancionExample);

            if(tipoSanciones != null
                && !tipoSanciones.isEmpty()){

                idSancion = tipoSanciones.get(0).getIdtiposancion();

            }else{
                ErrorType errorType = response.addNewError();
                errorType.setCodigo(SigaConstants.ERROR_SINCRONIZACION_EXEA.SANCION_NOENCONTRADA.name());
                errorType.setDescripcion(SigaConstants.ERROR_SINCRONIZACION_EXEA.SANCION_NOENCONTRADA.getMensajeError());
                errorType.setXmlRequest("Sin error XML");
                log.error("SincronizacionEXEAServiceImpl.getIdSancionFromCdgoExt() - TIPO DE SANCION NO ENCONTRADA");
            }
        }

        return idSancion;
    }

    /**
     * Metodo para obtener el ID estado civil de SIGA, ya que desde EXEA nos llegara el CODIGO EJIS
     *
     * @param estadoCivil
     * @return
     */
    private Short getIdEstadoCivilSIGAFromEnum(TipoEstadoCivil.Enum estadoCivil){
        Short idEstadoCivil = null;

        CenEstadocivilExample cenEstadocivilExample = new CenEstadocivilExample();
        cenEstadocivilExample.createCriteria()
                .andCodigoejisEqualTo(estadoCivil.toString());
        List<CenEstadocivil> cenEstadosciviles = cenEstadocivilExtendsMapper.selectByExample(cenEstadocivilExample);

        if(cenEstadosciviles != null
            && !cenEstadosciviles.isEmpty()){
            idEstadoCivil = cenEstadosciviles.get(0).getIdestadocivil();
        }

        return idEstadoCivil;
    }


    /**
     * Metodo para buscar el id de poblacion a traves de la descripcion, ya
     * que puede venir informada la descripcion y no el id
     *
     * @param descripcionPoblacion
     * @return
     */
    private String getIdPoblacionFromDescripcion(String descripcionPoblacion){
        String idPoblacion = "";
        List<CenPoblaciones> poblaciones = cenPoblacionesExtendsMapper.selectByFilter(descripcionPoblacion, null);
        if(poblaciones != null
            && !poblaciones.isEmpty()){
            idPoblacion = poblaciones.get(0).getIdpoblacion();
        }
        return idPoblacion;
    }

    /**
     * Metodo para buscar el ID Pais desde su codigo ISO, que es el que recibiremos en la peticion
     *
     * @param ISOPais
     * @
     */
    private String getIdPaisFromCodISO(String ISOPais){
        String idPais = "";
        CenPaisExample cenPaisExample = new CenPaisExample();
        cenPaisExample.createCriteria().andCodIsoEqualTo(ISOPais);

        List<CenPais> paises =  cenPaisExtendsMapper.selectByExample(cenPaisExample);

        if(paises != null
            && !paises.isEmpty()){
            idPais = paises.get(0).getIdpais();
        }

        return idPais;
    }
}
