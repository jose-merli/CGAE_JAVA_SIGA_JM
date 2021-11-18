package org.itcgae.siga.services.impl;

import com.exea.sincronizacion.redabogacia.*;
import org.itcgae.siga.DTOs.cen.FichaPersonaItem;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.ColegiadoJGItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.*;
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
            TipoBoolean.Enum isResidente = request.getColegiado().getColegiacion().getResidente();
            Long idPersona, idDireccion = null;

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

                    //Insertamos/updateamos los datos personales en CEN_PERSONA
                    idPersona = insertarDatosPersonales(request.getColegiado().getDatosPersonales(), idInstitucion);
                    affectedRows += insertarDatosCliente(idPersona, idInstitucion);
                    idDireccion = insertarDatosDireccion(request.getColegiado().getLocalizacion(), idInstitucion , request.getColegiado().getColegiacion().getTipoSolicitud());
                    //TODO: insertarDatosColegiado
                    if(request.getColegiado().getDatosBancarios() != null
                        && !UtilidadesString.esCadenaVacia(request.getColegiado().getDatosBancarios().getIBAN())){
                        //TODO: insertarDatosBancarios
                    }else{
                        //TODO: ejecutarPL_RevisionSuscripcionesLetrado ?
                    }

                //Si viene informado el numColegiado puede que se trate de una reincorporacion, si no, lanzamos error
                } else {
                    List<ColegiadoJGItem> colegiadoJGItems = cenPersonaExtendsMapper.busquedaColegiadoExpress(numColegiado,idInstitucion.toString());

                    if(colegiadoJGItems != null
                        && !colegiadoJGItems.isEmpty()){

                        ColegiadoJGItem colegiado = colegiadoJGItems.get(0);

                        if(isReincorporacion
                                && situacionPrevia != 1 //No puede ser una reincorporacion y que sea su primera colegiacion (situacionPrevia = 1)
                                && ((situacionPrevia == 2 && TipoColegiacionType.E.equals(tipoColegiacionType))
                                    || (situacionPrevia == 3 && TipoColegiacionType.N.equals(tipoColegiacionType)))){
                            //Debe coincidir la situacion previa con el tipo de solicitud, es decir si era ejerciente, que el tipo de solicitud sea E (Ejerciente)
                            // y si era no ejerciente, que el tipo de solicitud sea N (No ejerciente)

                                reincorporarColegiado(colegiado,request,response,idInstitucion);

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
    @Transactional
    private int reincorporarColegiado(ColegiadoJGItem colegiado,
                                      AltaColegiadoRequestDocument.AltaColegiadoRequest request,
                                      AltaColegiadoResponseDocument.AltaColegiadoResponse response, Short idInstitucion){
        int affectedRows = 0;
        TipoColegiacionType.Enum tipoColegiacionType = request.getColegiado().getColegiacion().getTipoSolicitud();
        TipoBoolean.Enum isResidente = request.getColegiado().getColegiacion().getResidente();

        //Comprobaremos si realmente está de baja
        CenDatoscolegialesestadoExample cenDatoscolegialesestadoExample = new CenDatoscolegialesestadoExample();
        cenDatoscolegialesestadoExample.createCriteria()
                .andIdpersonaEqualTo(Long.valueOf(colegiado.getIdPersona()))
                .andIdinstitucionEqualTo(idInstitucion);
        cenDatoscolegialesestadoExample.setOrderByClause("FECHAESTADO DESC");
        List<CenDatoscolegialesestado> estados = cenDatoscolegialesestadoExtendsMapper.selectByExample(cenDatoscolegialesestadoExample);

        if(estados != null
                && !estados.isEmpty()) {
            CenDatoscolegialesestado lastEstado = estados.get(0);
            //Si su estado es baja colegial, cambiamos estado y ponemos la marca inscrito
            if (SigaConstants.ESTADO_COLEGIAL_BAJACOLEGIAL == lastEstado.getIdestado()) {
                lastEstado.setFechaestado(request.getColegiado().getColegiacion().getFechaIncorporacion().getTime());
                if (TipoColegiacionType.N.equals(tipoColegiacionType)) {
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

                CenColegiado cenColegiado = new CenColegiado();
                cenColegiado.setComunitario("0");
                cenColegiado.setIdinstitucion(idInstitucion);
                cenColegiado.setIdpersona(Long.valueOf(colegiado.getIdPersona()));
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
                    //TODO: Asociar expediente a colegiado
                    //TODO: Si el expediente esta asociado a una solicitud de incorporación, asociarlo
                    response.setNumeroExpediente(request.getNumeroExpediente());
                    log.info("SincronizacionEXEAServiceImpl.aprobarAltaColegiado() / Reincorporacion finalizada, se informa response y se devuelve");
                }
            } else {
                ErrorType errorType = response.addNewError();
                errorType.setCodigo(SigaConstants.ERROR_SINCRONIZACION_EXEA.OTRO_ERROR.name());
                errorType.setDescripcion("El colegiado no tiene el estado BAJA COLEGIAL");
                errorType.setXmlRequest("Sin error XML");
                log.error("SincronizacionEXEAServiceImpl.aprobarAltaColegiado() / El colegiado " + colegiado.getnColegiado() + " no tiene el estado BAJA COLEGIAL");
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
                    ejemploCliente.createCriteria().andIdpersonaEqualTo(busqueda.get(0).getIdpersona()).andIdinstitucionEqualTo(Short.valueOf("2000"));
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
    private Long insertarDatosDireccion (LocalizacionType localizacionType, Short idInstitucion, TipoColegiacionType.Enum tipoColegiacion){

        log.info("SincronizacionEXEAServiceImpl.insertarDatosDireccion() - INICIO");
        Long idDireccion = null;
        log.info("SincronizacionEXEAServiceImpl.insertarDatosDireccion() - FIN");
        return idDireccion;
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
}
