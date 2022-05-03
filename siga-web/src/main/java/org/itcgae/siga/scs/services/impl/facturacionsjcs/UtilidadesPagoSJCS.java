package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.FacturacionGrupoPagoDTO;
import org.itcgae.siga.DTOs.scs.MovimientoVarioDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.CenComponentes;
import org.itcgae.siga.db.entities.CenComponentesExample;
import org.itcgae.siga.db.entities.CenCuentasbancarias;
import org.itcgae.siga.db.entities.CenCuentasbancariasExample;
import org.itcgae.siga.db.entities.EnvProgrampagosExample;
import org.itcgae.siga.db.entities.FacAbono;
import org.itcgae.siga.db.entities.FacAbonoExample;
import org.itcgae.siga.db.entities.FacAbonoincluidoendisqueteExample;
import org.itcgae.siga.db.entities.FacDisqueteabonosExample;
import org.itcgae.siga.db.entities.FcsAplicaMovimientosvarios;
import org.itcgae.siga.db.entities.FcsFacturacionjg;
import org.itcgae.siga.db.entities.FcsFacturacionjgKey;
import org.itcgae.siga.db.entities.FcsMovimientosvarios;
import org.itcgae.siga.db.entities.FcsPagoColegiado;
import org.itcgae.siga.db.entities.FcsPagosEstadospagos;
import org.itcgae.siga.db.entities.FcsPagosEstadospagosExample;
import org.itcgae.siga.db.entities.FcsPagosEstadospagosKey;
import org.itcgae.siga.db.entities.FcsPagosjg;
import org.itcgae.siga.db.mappers.FacDisqueteabonosMapper;
import org.itcgae.siga.db.mappers.FcsAplicaMovimientosvariosMapper;
import org.itcgae.siga.db.mappers.FcsPagosEstadospagosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmContadorExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenComponentesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenCuentasbancariasExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvEnviosExtendsMapper;
import org.itcgae.siga.db.services.env.mappers.EnvEnvioprogramadoExtendsMapper;
import org.itcgae.siga.db.services.env.mappers.EnvProgrampagosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacAbonoExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacHistoricofacturaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacLineaabonoExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FacAbonoincluidoendisqueteExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FacPagoabonoefectivoExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FacPagosporcajaExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsFacturacionJGExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsPagoColegiadoExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsPagosjgExtendsMapper;
import org.itcgae.siga.exception.FacturacionSJCSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UtilidadesPagoSJCS {

    private final Logger LOGGER = Logger.getLogger(UtilidadesPagoSJCS.class);

    @Autowired
    private FcsPagosjgExtendsMapper fcsPagosjgExtendsMapper;

    @Autowired
    private FcsPagosEstadospagosMapper fcsPagosEstadospagosMapper;

    @Autowired
    private EjecucionPlsPago ejecucionPlsPago;

    @Autowired
    private FcsPagoColegiadoExtendsMapper fcsPagoColegiadoExtendsMapper;

    @Autowired
    private FcsAplicaMovimientosvariosMapper fcsAplicaMovimientosvariosMapper;

    @Autowired
    private FcsFacturacionJGExtendsMapper fcsFacturacionJGExtendsMapper;

    @Autowired
    private UtilidadesFacturacionSJCS utilidadesFacturacionSJCS;

    @Autowired
    private FacAbonoExtendsMapper facAbonoExtendsMapper;

    @Autowired
    private FacAbonoincluidoendisqueteExtendsMapper facAbonoincluidoendisqueteExtendsMapper;

    @Autowired
    private FacDisqueteabonosMapper facDisqueteabonosMapper;

    @Autowired
    private EnvEnvioprogramadoExtendsMapper envEnvioprogramadoExtendsMapper;

    @Autowired
    private EnvEnviosExtendsMapper envEnviosExtendsMapper;

    @Autowired
    private EnvProgrampagosExtendsMapper envProgrampagosExtendsMapper;

    @Autowired
    private FacLineaabonoExtendsMapper facLineaabonoExtendsMapper;

    @Autowired
    private FacHistoricofacturaExtendsMapper facHistoricofacturaExtendsMapper;

    @Autowired
    private FacPagosporcajaExtendsMapper facPagosporcajaExtendsMapper;

    @Autowired
    private FacPagoabonoefectivoExtendsMapper facPagoabonoefectivoExtendsMapper;

    @Autowired
    private AdmContadorExtendsMapper admContadorExtendsMapper;
    
    @Autowired
    private CenCuentasbancariasExtendsMapper cenCuentasbancariasExtendsMapper;
    
    @Autowired
    private CenComponentesExtendsMapper cenComponentesExtendsMapper;

    private static List<Integer> listaPagosDeshacerCierre = new ArrayList<>();

    @Async
    public void asyncEjecutarPagoSJCS(FcsPagosjg pago, boolean simular, Short idInstitucion, AdmUsuarios usuario) throws Exception {

        try {
            // Insertamos el estado del pago:
            insertEstadoPago(idInstitucion, pago.getIdpagosjg(), usuario.getIdusuario(), SigaConstants.ESTADO_PAGO_EJECUTANDO);

            // Iniciamos la ejecución del pago:
            ejecutarPagoSJCS(pago, simular, idInstitucion, usuario);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            LOGGER.error(e.getStackTrace());
            ponerPagoEstadoAbierto(pago, idInstitucion, usuario);
            throw e;
        } finally {
            FacturacionSJCSServicesImpl.setNadieEjecutando();
        }

    }

    @Transactional
    public void ejecutarPagoSJCS(FcsPagosjg pago, boolean simular, Short idInstitucion, AdmUsuarios usuario) throws Exception {

        ejecucionPlsPago.ejecutarPL_PagoTurnosOficio(Integer.valueOf(idInstitucion.toString()), pago.getIdpagosjg(), usuario);
        ejecucionPlsPago.ejecutarPL_PagoGuardias(Integer.valueOf(idInstitucion.toString()), pago.getIdpagosjg(), usuario);
        ejecucionPlsPago.ejecutarPL_PagoSOJ(Integer.valueOf(idInstitucion.toString()), pago.getIdpagosjg(), usuario);
        ejecucionPlsPago.ejecutarPL_PagoEJG(Integer.valueOf(idInstitucion.toString()), pago.getIdpagosjg(), usuario);

        // Calculo de todos los importes totales, importes de movimientos,
        // importes de irpf, importe bruto, importe neto ......
        // así como la forma de pago, si el pago es por banco, obtención del
        // nombre del banco y la cuenta corriente.
        this.obtencionImportes(idInstitucion, pago.getIdpagosjg().toString(), usuario);

        // Exportacion de datos a EXCEL
        utilidadesFacturacionSJCS.exportarDatosPagos(idInstitucion, pago.getIdfacturacion(), pago.getIdpagosjg(), null, usuario);

        if (simular) {
            throw new Exception("messages.facturacionSJCS.pago.simulado");
        }
    }

    @Transactional(rollbackFor = Exception.class,timeout = 900)
    private void obtencionImportes(Short idInstitucion, String idPago, AdmUsuarios usuario) throws Exception {

        // variables para hacer el calculo del importe final a pagar
        String idPersonaDestino;
        double importeSJCS = 0.0d;
        double importeTurnos = 0.0d, importeGuardias = 0.0d, importeSoj = 0.0d, importeEjg = 0.0d;
        double importeMovimientos = 0.0d, importeRetenciones = 0.0d;
        Double porcentajeIRPF;
        double importeIrpfTotal = 0.0d;
        String idCuenta;
        List<FcsPagoColegiado> colegiados = new ArrayList<FcsPagoColegiado>();
        FcsFacturacionjg facturacion = null;

        try {

            // Recuperamos los colegiados a los que tenemos que pagar
            // aquellos incluidos en el pago o con movimientos varios pendientes
             colegiados = getColegiadosApagar(idInstitucion, idPago, SigaConstants.LISTA_PAGO_TODOS, usuario);
             
            //Obtenemos la facturacion para los calculos posteriores
            //Obtiene la facturación del pago y sus grupos.
             List<FacturacionGrupoPagoDTO> facturacionesGruposPagosList = getFacturacionesGruposPagos(idPago, idInstitucion.toString(), usuario);

             Integer idFacturacion = null;

             if (facturacionesGruposPagosList != null && !facturacionesGruposPagosList.isEmpty()) {
                 idFacturacion = facturacionesGruposPagosList.get(0).getIdFacturacion();

                 //Recuperamos las fechas desde y hasta de la facturación
                 FcsFacturacionjgKey fcsFacturacionjgKey = new FcsFacturacionjgKey();
                 fcsFacturacionjgKey.setIdinstitucion(idInstitucion);
                 fcsFacturacionjgKey.setIdfacturacion(idFacturacion);
                 facturacion = fcsFacturacionJGExtendsMapper.selectByPrimaryKey(fcsFacturacionjgKey);
             }

            // Si no existe un pago para el colegiado debe existir al menos un
            // MV
            // por lo que pasa a tratar los movimientos varios
            if(!colegiados.isEmpty() || colegiados.size() != 0){
            	HashMap<String,Double> colegiadoMV = new HashMap<>();
                for (FcsPagoColegiado colegiado : colegiados) {

                    // Obtenemos el idcuenta con el fin de actualizar el registro de
                    // la persona de la tabla fcs_pago_colegiado

                    idPersonaDestino = colegiado.getIdperdestino().toString();
                    LOGGER.info("getCuentaAbonoSJCS -> Entramos");
                    ArrayList<String> cuenta = getCuentaAbonoSJCS(idInstitucion, idPersonaDestino, usuario);
                    LOGGER.info("getCuentaAbonoSJCS -> Salimos");
                    idCuenta = cuenta.get(2).equals("-1") ? null : cuenta.get(2);

                    if (idCuenta != null) {
                        colegiado.setIdcuenta(Short.valueOf(idCuenta));
                        colegiado.setFechamodificacion(new Date());
                        colegiado.setUsumodificacion(usuario.getIdusuario());
                    }
                    LOGGER.info("updateByPrimaryKeySelective -> Entramos");
                    fcsPagoColegiadoExtendsMapper.updateByPrimaryKeySelective(colegiado);
                    LOGGER.info("updateByPrimaryKeySelective -> Salimos");
                    importeTurnos = colegiado.getImpoficio().doubleValue();
                    importeGuardias = colegiado.getImpasistencia().doubleValue();
                    importeSoj = colegiado.getImpsoj().doubleValue();
                    importeEjg = colegiado.getImpejg().doubleValue();

                    // 1. Calcula el IMPORTE SJCS BRUTO
                    importeSJCS = importeTurnos + importeGuardias + importeSoj + importeEjg;

                    
                    // 2. Aplicar los movimientos varios
                    // Asocia todos los movimientos sin idpago al pago actual.
                    // Actualiza el porcentaje e importe IRPF para cada movimiento.
                    FcsMovimientosvarios fcsMovimientosvarios = new FcsMovimientosvarios();
                    fcsMovimientosvarios.setIdinstitucion(idInstitucion);
                    fcsMovimientosvarios.setIdpersona(colegiado.getIdperorigen());
                    LOGGER.info("aplicarMovimientosVarios -> Entramos");
                    importeMovimientos = aplicarMovimientosVarios(fcsMovimientosvarios, idPago, importeSJCS, usuario, facturacionesGruposPagosList, facturacion);
                    LOGGER.info("aplicarMovimientosVarios -> Salimos");
                    
                    colegiadoMV.put(idPersonaDestino, importeMovimientos);
                } // fin del primer for de colegiados
                
                for (FcsPagoColegiado colegiado : colegiados) { 
                	idPersonaDestino = colegiado.getIdperdestino().toString();
                	// obtiene el porcentajeIRPF del colegiado para utilizarlo al
                    // aplicar
                    // los movimientos varios y calcular el IRPF del importe bruto.
                    LOGGER.info("obtenerIrpf -> Entramos");
                    porcentajeIRPF = obtenerIrpf(idInstitucion.toString(), idPersonaDestino,
                            !idPersonaDestino.equals(colegiado.getIdperorigen().toString()), usuario);//obtenerla en la query ppal
                    LOGGER.info("obtenerIrpf -> Salimos");
                    
                    // 3. Obtener importe bruto como la suma de los movimientos varios y
                    // el total SJCS
                    
                    importeTurnos = colegiado.getImpoficio().doubleValue();
                    importeGuardias = colegiado.getImpasistencia().doubleValue();
                    importeSoj = colegiado.getImpsoj().doubleValue();
                    importeEjg = colegiado.getImpejg().doubleValue();

                    // Volvemos a calcular el IMPORTE SJCS BRUTO
                    importeSJCS = importeTurnos + importeGuardias + importeSoj + importeEjg;
                    double importeBruto = importeSJCS + importeMovimientos;

                    // 4. Obtener el importe neto aplicando el IRPF
                    // (hay que redondear el importeIrpf porque es un importe que se ha
                    // de presentar)
                    importeIrpfTotal = (-1) * redondea(importeBruto * porcentajeIRPF / 100, 2);

                    double importeNeto = importeBruto + importeIrpfTotal;

                    // 5. Aplicar retenciones judiciales y no judiciales
                    //aalg Incidencia del 28-sep-2011. Se modifica el usuario de modificacion que se estaba
                    // cogiendo el idPersona en vez del userName
                    LOGGER.info("aplicarRetencionesJudiciales -> Entramos");
                    aplicarRetencionesJudiciales(idInstitucion.toString(), idPago, colegiado.getIdperorigen().toString(),
                            Double.toString(importeNeto), usuario.getIdusuario().toString(), usuario.getIdlenguaje(), usuario);
                    LOGGER.info("aplicarRetencionesJudiciales -> Salimos");
                    // obtener el importe de las retenciones judiciales
                    importeRetenciones = getSumaRetenciones(idInstitucion.toString(), idPago, colegiado.getIdperorigen().toString(), usuario);
                    
                    //Obtenemos del mapa el importeMovimientos obtenido previamente
                    importeMovimientos = colegiadoMV.get(idPersonaDestino);
                    // Actualizar el irpf, movimientos varios y retenciones en
                    // fcs_pago_colegiado
                    FcsPagoColegiado fcsPagoColegiado = new FcsPagoColegiado();
                    fcsPagoColegiado.setIdinstitucion(idInstitucion);
                    fcsPagoColegiado.setIdpagosjg(Integer.valueOf(idPago));
                    fcsPagoColegiado.setIdperorigen(colegiado.getIdperorigen());
                    fcsPagoColegiado.setImpirpf(BigDecimal.valueOf(importeIrpfTotal));
                    fcsPagoColegiado.setPorcentajeirpf(BigDecimal.valueOf(porcentajeIRPF));
                    fcsPagoColegiado.setImpmovvar(BigDecimal.valueOf(importeMovimientos));
                    fcsPagoColegiado.setImpret(BigDecimal.valueOf(importeRetenciones));
                    LOGGER.info("fcsPagoColegiadoExtendsMapper.updateByPrimaryKeySelective -> Entramos");
                    fcsPagoColegiadoExtendsMapper.updateByPrimaryKeySelective(fcsPagoColegiado);
                    LOGGER.info("fcsPagoColegiadoExtendsMapper.updateByPrimaryKeySelective -> Salimos");
                } // fin del segundo for de colegiados
                
                // Insertamos el estado del pago:
                insertEstadoPago(idInstitucion, Integer.valueOf(idPago), usuario.getIdusuario(), SigaConstants.ESTADO_PAGO_EJECUTADO);
            }else{
                throw new FacturacionSJCSException("Para ejecutar un pago este debe estar asociado a un colegiado.");
            }


        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            LOGGER.error(e.getStackTrace());
            throw new FacturacionSJCSException("Error en la obtención de los importes", e, "messages.factSJCS.error.importes");
        }
    }

    public void insertEstadoPago(Short idInstitucion, Integer idPago, Integer idUsuario, String estado) {
        FcsPagosEstadospagos record = new FcsPagosEstadospagos();
        record.setIdinstitucion(idInstitucion);
        record.setIdpagosjg(idPago);
        record.setIdestadopagosjg(Short.valueOf(estado));
        record.setFechaestado(new Date());
        record.setFechamodificacion(new Date());
        record.setUsumodificacion(idUsuario);

        FcsPagosEstadospagosExample estadoExample = new FcsPagosEstadospagosExample();
        estadoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
                .andIdpagosjgEqualTo(idPago)
                .andIdestadopagosjgEqualTo(record.getIdestadopagosjg());

        if (fcsPagosEstadospagosMapper.countByExample(estadoExample) == 0) {
            fcsPagosEstadospagosMapper.insertSelective(record);
        } else {
            fcsPagosEstadospagosMapper.updateByPrimaryKeySelective(record);
        }
    }

    public void ponerPagoEstadoAbierto(FcsPagosjg pago, Short idInstitucion, AdmUsuarios usuario) {

        LOGGER.info(
                "PagoSJCSServiceImpl.ponerPagoEstadoAbierto() -> INICIO");

        // Eliminamos los estados del pago
        FcsPagosEstadospagosExample fcsPagosEstadospagosExample = new FcsPagosEstadospagosExample();
        fcsPagosEstadospagosExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
                .andIdpagosjgEqualTo(pago.getIdpagosjg());
        LOGGER.info(
                "PagoSJCSServiceImpl.ponerPagoEstadoAbierto() -> fcsPagosEstadospagosMapper.deleteByExample() -> Eliminamos los estados del pago");
        fcsPagosEstadospagosMapper.deleteByExample(fcsPagosEstadospagosExample);

        // Ponemos el pago en estado ABIERTO
        FcsPagosEstadospagos fcsPagosEstadospagos = new FcsPagosEstadospagos();
        fcsPagosEstadospagos.setIdinstitucion(idInstitucion);
        fcsPagosEstadospagos.setIdpagosjg(pago.getIdpagosjg());
        fcsPagosEstadospagos.setIdestadopagosjg(Short.valueOf(SigaConstants.ESTADO_PAGO_ABIERTO));
        fcsPagosEstadospagos.setFechaestado(new Date());
        fcsPagosEstadospagos.setFechamodificacion(new Date());
        fcsPagosEstadospagos.setUsumodificacion(usuario.getIdusuario());
        LOGGER.info(
                "PagoSJCSServiceImpl.ponerPagoEstadoAbierto() -> fcsPagosEstadospagosMapper.insertSelective() -> Ponemos el pago en estado abierto");
        fcsPagosEstadospagosMapper.insertSelective(fcsPagosEstadospagos);

        LOGGER.info(
                "PagoSJCSServiceImpl.ponerPagoEstadoAbierto() -> FIN");
    }

    /**
     * Funcion que devuelve los colegiados que interviene en un pago
     * en Actuaciones Designas, Asistencias, EJG's, Guardias y/o SOJ's y Movimientos.
     * <p>
     * listaPagoSoloIncluirMorosos = 0
     * listaPagoSoloIncluirNoMorosos = 1
     * listaPagoTodos = 2
     *
     * @param idInstitucion
     * @param idPago
     * @return List<FcsPagoColegiado>
     */
    private List<FcsPagoColegiado> getColegiadosApagar(Short idInstitucion, String idPago, int caseMorosos, AdmUsuarios usuario) throws Exception {

        List<FcsPagoColegiado> colegiados = new ArrayList<FcsPagoColegiado>();

        try {

            colegiados = fcsPagosjgExtendsMapper.getColegiadosApagar(idInstitucion, idPago, caseMorosos);

        } catch (Exception e) {
            throw new FacturacionSJCSException("Error al obtener los colegiados a pagar", e, "messages.factSJCS.error.colegiadosApagar");
        }

        return colegiados;
    }

    /**
     * Devuelve la cuenta de abono para una persona, comprobando si representa a una sociedad o a si mismo
     *
     * @param idInstitucion
     * @param idPersona
     * @return ArrayList (0=idInstitucion; 1=idPersona; 2=idCuentaAbonoSJCS)
     * <br> Si la cuenta es -1 es que pasa a pagar por caja
     * @throws Exception En cualquier caso de error
     */
    
    private ArrayList<String> getCuentaAbonoSJCS(Short idInstitucion, String idPersona, AdmUsuarios usuario) throws Exception {

        ArrayList<String> salida = new ArrayList<>();
        salida.add(idInstitucion.toString());
        try {

            // Buscamos si la persona pertenece a una sociedad y no tiene de baja la relacion
            //List<PerteneceAunaSociedadDTO> registros = fcsPagosjgExtendsMapper.perteneceAunaSociedad(idInstitucion, idPersona);
            
            CenComponentesExample exampleComponentes = new CenComponentesExample();
            exampleComponentes.createCriteria().andIdinstitucionEqualTo(idInstitucion)
            	.andCenClienteIdpersonaEqualTo(Long.valueOf(idPersona))
            	.andCenClienteIdinstitucionEqualTo(idInstitucion)
            	.andSociedadEqualTo("1")
            	.andFechabajaIsNull();
			List<CenComponentes> listaComponentes = cenComponentesExtendsMapper.selectByExample(exampleComponentes);
            
            if (!listaComponentes.isEmpty()) { // Tiene registros activos en componentes
                String sIdPersonaSociedad = listaComponentes.get(0).getIdpersona().toString();
                String oIdCuenta = listaComponentes.get(0).getIdcuenta().toString();
                salida.add(sIdPersonaSociedad);

                if (oIdCuenta != null) {// Tiene una cuenta de abono asociada

                    // Comprobamos que la cuenta siga activa y sea ABONOSJSCS=1
                	
                	String cuentaAbono = null;
                	
                	CenCuentasbancariasExample example = new CenCuentasbancariasExample();
                	example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
                		.andIdpersonaEqualTo(Long.valueOf(sIdPersonaSociedad))
                		.andAbonosjcsEqualTo("1")
                		.andFechabajaIsNull()
                		.andIdcuentaEqualTo(Short.valueOf(oIdCuenta));
        	        List<CenCuentasbancarias> cuentas = cenCuentasbancariasExtendsMapper.selectByExample(example);
                    if(cuentas!=null && cuentas.size()>0) {
                    	cuentaAbono = cuentas.get(0).getIdcuenta().toString(); //fcsPagosjgExtendsMapper.tieneCuentaAbonoAsociada(idInstitucion, sIdPersonaSociedad, oIdCuenta);
                	}
                			

                    if (cuentaAbono != null) { // La sociedad tiene una cuenta de abono activa y es AbonoSJCS=1
                        salida.add(oIdCuenta);
                    } else { // La sociedad tiene cuenta de abono de baja o es AbonoSJCS=0
                        salida.add("-1"); // paga por caja
                    }

                } else { // No tiene una cuenta la sociedad
                    salida.add("-1"); // paga por caja
                }

            } else { // No tiene registro en componentes, y por lo tanto, hay que buscar una cuenta bancaria activa de la persona con AbonoSJCS=1
                salida.add(idPersona);
                CenCuentasbancariasExample example = new CenCuentasbancariasExample();
            	example.createCriteria().andIdinstitucionEqualTo(idInstitucion)
            		.andIdpersonaEqualTo(Long.valueOf(idPersona))
            		.andAbonosjcsEqualTo("1")
            		.andFechabajaIsNull();
            	example.setOrderByClause("FECHAMODIFICACION DESC");
    	        List<CenCuentasbancarias> cuentas = cenCuentasbancariasExtendsMapper.selectByExample(example);
                //List<String> cuentas = fcsPagosjgExtendsMapper.getCuentaBancariaActiva(idInstitucion, idPersona);

                if (!cuentas.isEmpty()) { // La persona tiene alguna cuenta de AbonoSJCS=1 activa
                    salida.add(cuentas.get(0).getIdcuenta().toString());
                } else { // La persona no tiene ninguna cuenta de AbonoSJCS=1 activa
                    salida.add("-1"); // paga por caja
                }
            }

        } catch (Exception e) {
            throw new FacturacionSJCSException("Error al obtener la cuenta de abono SJCS", e, "messages.factSJCS.error.cuentaAbonoSJCS");
        }

        return salida;
    }

    /**
     * Devuelve el porcentaje de irpf a aplicar en un pago
     *
     * @param idInstitucion
     * @param idPersonaSociedad
     * @param esSociedad
     * @return
     * @throws Exception
     */
    
    private Double obtenerIrpf(String idInstitucion, String idPersonaSociedad,
                               boolean esSociedad, AdmUsuarios usuario) throws Exception {

        String[] resultado = ejecucionPlsPago.ejecutarPLCalcularIRPF_Pagos(idInstitucion, idPersonaSociedad, esSociedad);

        // comprueba si el pl se ha ejecutado correctamente
        if (!resultado[2].equals("0")) {
            if (resultado[2].equals("100"))
                throw new FacturacionSJCSException("Existen colegiados sin IRPF o varios IRPFs asignados. Puede revisar el listado de IRPFs asignados que encontrará en el módulo de Consultas",
                        "error.irpf.fileNotExist");
            else
                throw new FacturacionSJCSException("Error en la obtención de IRPF: " + resultado[3],
                        "messages.factSJCS.error.obtIrpf");
        }

        if (resultado[0] != null && !resultado[0].isEmpty() && resultado[0].contains(",")) {
            resultado[0] = resultado[0].replace(",", ".");
        }

        return new Double(resultado[0]);
    }

    /**
     * Devuelve el importe total de los movimientos varios. El algoritmos
     * utilizado es el siguiente: 1 Aplicar todos los MVs positivos 2 Ordenar
     * los MVs negativos por fecha 3 Intentar aplicar MV � Si la cantidad
     * resultante es igual a 0 entonces Terminar � Si la cantidad resultante es
     * menor que 0 entonces Dejar cantidad resultante en 0 Crear MV con la
     * diferencia Terminar 4 Seguir con otro MV en el paso 2.3
     *
     * @param fcsMovimientosvarios
     * @param idPago
     * @param importeSJCS
     * @param facturacionesGruposPagosList 
     * @param facturacion 
     * @return
     * @throws Exception
     */
    private double aplicarMovimientosVarios(FcsMovimientosvarios fcsMovimientosvarios, String idPago, double importeSJCS, AdmUsuarios usuario, List<FacturacionGrupoPagoDTO> facturacionesGruposPagosList, FcsFacturacionjg facturacion) throws Exception {

        // en esta variable se guarda el importe final de los movimientos varios
        double importeMovimientos = 0.0d;
        double importeAplicado = 0.0d;
        double importeTotalMovimiento = 0.0d;
        double importeAnteriorAplicado = 0.0d;
        Long auxIdMovimiento = 0L;
        Long auxIdMovimientoAnt = 0L;
        Date ausFechaModificacion;
        Integer auxUsuarioModificacion = 0;
        boolean noAplica = false;

        try {

            /*
             INC: R1411_0038 Movimientos a aplicar:
             1.-Movimientos del colegiado no asociados a ninguna facturación ni grupo de turnos.
             2.-Movimientos del colegiado asociados a una facturación y grupo de turnos que:
             a)Facturación asociada al movimiento <= Facturación del pago
             b)Grupo asociado al movimiento = Grupo del pago (facturación del movimiento puede no estar informada, si está informada tiene que cumplir condición a))
             */

            List<MovimientoVarioDTO> movimientos = new ArrayList<>();
            List<MovimientoVarioDTO> movimientos_aux = new ArrayList<>();

            //Se obtienen los movimientos del colegiado que no estén asociados ni a una facturación ni a un grupo. (Caso 1)
            movimientos_aux.addAll(getMovimientosRW(fcsMovimientosvarios.getIdinstitucion(), idPago, fcsMovimientosvarios.getIdpersona().toString(), null, null, null, SigaConstants.CASO_MVNOASOCIADO, usuario));

            if (facturacion != null) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String fechaDesde = sdf.format(facturacion.getFechadesde());

                //Se obtienen los movimientos de la facturación que no tienen grupo asociado
                // y que estén pendientes de aplicar de la facturación del pago y de facturaciones anteriores
                movimientos_aux.addAll(getMovimientosRW(fcsMovimientosvarios.getIdinstitucion(), idPago, fcsMovimientosvarios.getIdpersona().toString(), facturacion.getIdfacturacion().toString(), fechaDesde, null, SigaConstants.CASO_MVASOCIADOAFACTURACION, usuario));

                for (int i = 0; i < facturacionesGruposPagosList.size(); i++) {
                    Short idGrupo = facturacionesGruposPagosList.get(i).getIdGrupoFacturacion();
                    if (null != idGrupo) {
                        //Se obtienen los movimientos del colegiado que tienen idfacturacion <= que la del pago y el grupo = grupo del pago que estamos tratando (Caso 2)
                        movimientos_aux.addAll(getMovimientosRW(fcsMovimientosvarios.getIdinstitucion(), idPago, fcsMovimientosvarios.getIdpersona().toString(), facturacion.getIdfacturacion().toString(), fechaDesde, idGrupo.toString(), SigaConstants.CASO_MVASOCIADOAGRUPOFACT, usuario));
                    }
                }

            }

            List<MovimientoVarioDTO> movimientos_positivos = new ArrayList<>();
            List<MovimientoVarioDTO> movimientos_negativos = new ArrayList<>();

            for (int dm = 0; dm < movimientos_aux.size(); dm++) {
                if (movimientos_aux.get(dm).getCantidad().doubleValue() > 0) {
                    movimientos_positivos.add(movimientos_aux.get(dm));
                } else {
                    movimientos_negativos.add(movimientos_aux.get(dm));
                }
            }

            if (movimientos_positivos.size() > 1) {

                for (int dp = 0; dp < movimientos_positivos.size() - 1; dp++) {

                    for (int dsp = dp + 1; dsp < movimientos_positivos.size(); dsp++) {

                        Date fecha = movimientos_positivos.get(dp).getFechaAlta();

                        Date fechaSig = movimientos_positivos.get(dsp).getFechaAlta();

                        //Si la fecha del siguiente movimiento es mayor se intercambia el elemento
                        if (fecha.compareTo(fechaSig) > 0) {
                            //Intercambiamos valores
                            MovimientoVarioDTO mov_aux = movimientos_positivos.get(dp);
                            movimientos_positivos.set(dp, movimientos_positivos.get(dsp));
                            movimientos_positivos.set(dsp, mov_aux);
                        }

                    }

                }

                movimientos.addAll(movimientos_positivos);

            } else {
                movimientos.addAll(movimientos_positivos);
            }

            if (movimientos_negativos.size() > 1) {

                for (int dn = 0; dn < movimientos_negativos.size() - 1; dn++) {

                    for (int dsn = dn + 1; dsn < movimientos_negativos.size(); dsn++) {

                        Date fecha = movimientos_negativos.get(dn).getFechaAlta();

                        Date fechaSig = movimientos_negativos.get(dsn).getFechaAlta();

                        //Si la fecha del siguiente movimiento es mayor se intercambia el elemento
                        if (fecha.compareTo(fechaSig) > 0) {
                            //Intercambiamos valores
                            MovimientoVarioDTO mov_aux = movimientos_negativos.get(dn);
                            movimientos_negativos.set(dn, movimientos_negativos.get(dsn));
                            movimientos_negativos.set(dsn, mov_aux);

                        }

                    }

                }

                movimientos.addAll(movimientos_negativos);
            } else {
                movimientos.addAll(movimientos_negativos);
            }

            for (int contador = 0; contador < movimientos.size(); contador++) {

                if (!noAplica) {

                    auxIdMovimiento = movimientos.get(contador).getIdMovimiento();

                    if (auxIdMovimiento.intValue() != auxIdMovimientoAnt.intValue()) {

                        importeTotalMovimiento = movimientos.get(contador).getCantidad().doubleValue();
                        ausFechaModificacion = movimientos.get(contador).getFechaModificacion();
                        auxUsuarioModificacion = movimientos.get(contador).getUsuModificacion();

                        fcsMovimientosvarios.setIdmovimiento(auxIdMovimiento);
                        fcsMovimientosvarios.setFechamodificacion(ausFechaModificacion);
                        fcsMovimientosvarios.setUsumodificacion(auxUsuarioModificacion);
                        fcsMovimientosvarios.setCantidad(BigDecimal.valueOf(importeTotalMovimiento));

                        if (importeTotalMovimiento >= 0) {
                            // Si el importe del movimiento es positivo
                            if (importeTotalMovimiento > 0) {
                                importeMovimientos += importeTotalMovimiento;
                                insertarAplicacionMovimientos(fcsMovimientosvarios, idPago, importeTotalMovimiento, usuario);
                            }
                        } else {
                            // Si el importe del movimiento es negatio

                            //Primero comprobamos si se ha aplicado anteriormente algun importe en
                            // otro pago al movimiento
                            importeAnteriorAplicado = getSumaMovimientosAplicados(fcsMovimientosvarios.getIdinstitucion().toString(),
                                    auxIdMovimiento.toString(), fcsMovimientosvarios.getIdpersona().toString(), usuario);

                            importeTotalMovimiento = redondea(importeTotalMovimiento - importeAnteriorAplicado, 2);

                            importeMovimientos += importeTotalMovimiento;

                            importeAplicado = redondea(importeTotalMovimiento - (importeSJCS + importeMovimientos), 2);

                            if ((importeSJCS + importeMovimientos) <= 0) {

                                fcsMovimientosvarios.setCantidad(BigDecimal.valueOf(importeAplicado));

                                insertarAplicacionMovimientos(fcsMovimientosvarios, idPago, importeAplicado, usuario);

                                importeMovimientos = redondea((importeAplicado - (importeTotalMovimiento - (importeSJCS + importeMovimientos)) - importeSJCS), 2);

                                noAplica = true;

                            } else {

                                fcsMovimientosvarios.setCantidad(BigDecimal.valueOf(importeTotalMovimiento));

                                insertarAplicacionMovimientos(fcsMovimientosvarios, idPago, importeTotalMovimiento, usuario);

                            }
                        }

                        auxIdMovimientoAnt = auxIdMovimiento;

                    }

                }

            }


        } catch (Exception e) {
            throw new FacturacionSJCSException("No se ha podido realizar la petición. La incidencia ha sido remitida a RedAbogacia para su resolución.", e,
                    "messages.general.error");
        }

        return importeMovimientos;
    }

    /**
     * Devuelve el porcentaje de irpf a aplicar en un pago
     */
    
    private void aplicarRetencionesJudiciales(String idInstitucion,
                                              String idPagoJg, String idPersonaSociedad, String importeNeto,
                                              String usuMod, String idioma, AdmUsuarios usuario) throws Exception {

        // Sustituimos el carácter '.' por ',' para que el PL se ejecute correctamente
        if (importeNeto != null && importeNeto.contains(".")) {
            importeNeto = importeNeto.replace(".", ",");
        }
        LOGGER.debug("Ejecutamos PL aplicarRetencionesJudiciales: Entrada: " + "idInstitucion:" + idInstitucion +
                "idPagoJg:" + idPagoJg + "idPersonaSociedad:" + idPersonaSociedad +  "importeNeto:" + importeNeto
                + "idioma:" + idioma );
        // Aplicar las retenciones judiciales
         try{
            String resultado[] = ejecucionPlsPago.ejecutarPLAplicarRetencionesJudiciales(idInstitucion, idPagoJg, idPersonaSociedad, importeNeto, usuMod,
                    idioma);
            if(resultado != null){
                LOGGER.debug("PL ejecutado: "+ resultado.length + "," + resultado[0]);
            }else{
                LOGGER.debug("PL ejecutado: "+ resultado);
            }

            // comprueba si el pl se ha ejecutado correctamente
            if (resultado != null && !resultado[0].equals("0")) {
                if (resultado[0].equals("11"))
                    throw new FacturacionSJCSException("Se ha producido un error al calcular el importe de retención LEC. Seguramente no haya smi para el año o no esten configurados los tramos LEC",
                            "FactSJCS.mantRetencionesJ.plAplicarRetencionesJudiciales.error.tramosLEC");
                else
                    throw new FacturacionSJCSException("Error al aplicar las retenciones judiciales", "messages.factSJCS.error.retencionesJudi");
            }
        }catch(Exception e){
            LOGGER.error(e.getCause(), e);
             throw new FacturacionSJCSException("Error al aplicar las retenciones judiciales", "messages.factSJCS.error.retencionesJudi");
        }

    }

    
    private double getSumaRetenciones(String idInstitucion, String idPago, String idPersona, AdmUsuarios usuario) throws Exception {

        double importe;

        try {

            importe = fcsPagosjgExtendsMapper.getSumaRetenciones(idInstitucion, idPago, idPersona);

        } catch (Exception e) {
            throw new FacturacionSJCSException("Error al obtener la suma de retenciones", e, "messages.factSJCS.error.sumaRetenciones");
        }

        return importe;
    }

    /**
     * Devuelve los movimientos varios que hay que pagar para una persona
     * ordenados por fecha de alta
     *
     * @param idInstitucion
     * @param idPersona
     * @return
     */
    private List<MovimientoVarioDTO> getMovimientosRW(Short idInstitucion, String idPago, String idPersona, String idFacturacion, String fDesde,
                                                      String idGrupoFacturacion, int caso, AdmUsuarios usuario) throws Exception {

        List<MovimientoVarioDTO> listaMovimientos;

        try {

            listaMovimientos = fcsPagosjgExtendsMapper.getMovimientosRW(idInstitucion, idPersona, idFacturacion, fDesde, idGrupoFacturacion, caso);

        } catch (Exception e) {
            throw new FacturacionSJCSException("Error al obtener los movimientos varios que hay que pagar a una persona", e, "messages.factSJCS.error.movimientos");
        }

        return listaMovimientos;
    }

    private List<FacturacionGrupoPagoDTO> getFacturacionesGruposPagos(String idPago, String idInstitucion, AdmUsuarios usuario) throws Exception {

        List<FacturacionGrupoPagoDTO> listaFacturaciones;

        try {
            listaFacturaciones = fcsPagosjgExtendsMapper.getFacturacionesGruposPagos(idPago, idInstitucion);

        } catch (Exception e) {
            throw new FacturacionSJCSException("Error al obtener las facturaciones grupos pago", e, "messages.factSJCS.error.factGrupoPago");
        }

        return listaFacturaciones;
    }

    private void insertarAplicacionMovimientos(FcsMovimientosvarios movimientosvarios, String idPago, double importeAplicado, AdmUsuarios usuario) {

        Long nuevoId = fcsPagosjgExtendsMapper.getNuevoIdAplicaMovimientosVarios();

        FcsAplicaMovimientosvarios fcsAplicaMovimientosvarios = new FcsAplicaMovimientosvarios();
        fcsAplicaMovimientosvarios.setIdaplicacion(nuevoId);
        fcsAplicaMovimientosvarios.setIdinstitucion(movimientosvarios.getIdinstitucion());
        fcsAplicaMovimientosvarios.setIdmovimiento(movimientosvarios.getIdmovimiento());
        fcsAplicaMovimientosvarios.setIdpersona(movimientosvarios.getIdpersona());
        fcsAplicaMovimientosvarios.setImporteaplicado(BigDecimal.valueOf(importeAplicado));
        fcsAplicaMovimientosvarios.setFechamodificacion(movimientosvarios.getFechamodificacion());
        fcsAplicaMovimientosvarios.setUsumodificacion(Long.valueOf(usuario.getIdusuario()));
        fcsAplicaMovimientosvarios.setIdpagosjg(Integer.valueOf(idPago));

        fcsAplicaMovimientosvariosMapper.insertSelective(fcsAplicaMovimientosvarios);
    }

    private double getSumaMovimientosAplicados(String idInstitucion, String idMovimiento, String idPersona, AdmUsuarios usuario) throws Exception {

        double importe;

        try {

            importe = fcsPagosjgExtendsMapper.getSumaMovimientosAplicados(idInstitucion, idMovimiento, idPersona);

        } catch (Exception e) {
            throw new FacturacionSJCSException("Error al obtener la suma de los movimientos varios aplicados", e, "messages.factSJCS.error.sumaMovVariosAplica");
        }

        return importe;
    }

    private double redondea(double numero, int precision) {

        if (Double.isNaN(numero)) // Contolo NaN
            return 0.0;

        // Calcula el signo
        BigDecimal bdSigno = new BigDecimal("1");
        if (numero < 0) {
            bdSigno = new BigDecimal("-1");
        }

        // Calcula la precision
        BigDecimal bdPrecision = new BigDecimal("1");
        for (int i = 0; i < precision; i++) {
            bdPrecision = bdPrecision.multiply(new BigDecimal("10"));
        }

        BigDecimal bCalculo = BigDecimal.valueOf(numero); // Conversion double to BigDecimal

        bCalculo = bCalculo.multiply(bdSigno); // Control inicial del signo

        bCalculo = bCalculo.multiply(bdPrecision); // Pone la parte decimal dentro de la precision como entero

        bCalculo = bCalculo.add(new BigDecimal("0.5")); // Sumo 0.5

        RoundingMode RM = RoundingMode.DOWN;
        bCalculo = bCalculo.setScale(0, RM); // Obtengo la parte entera
        //bCalculo = BigDecimal.valueOf(bCalculo.intValue());

        bCalculo = bCalculo.divide(bdPrecision); // Vuelvo a poner la parte decimal

        bCalculo = bCalculo.multiply(bdSigno); // Control final del signo

        return bCalculo.doubleValue();
    }

    @Transactional(rollbackFor = Exception.class)
    public void deshacerCierre(FcsPagosjg pago, Short idInstitucion, AdmUsuarios usuario) throws Exception {

        LOGGER.info("UtilidadesPagoSJCS.deshacerCierre() -> Entrada al servicio para deshacer el cierre del pago");
        List<Integer> idPagos = new ArrayList<>();
        try{
            listaPagosDeshacerCierre = new ArrayList<>();
            recursivaDeshacerCierre(idInstitucion, pago.getIdpagosjg());

            // 3 - HACEMOS EL PASO 2 POR CADA PAGO ENCONTRADO


            idPagos.add(pago.getIdpagosjg());
            idPagos.addAll(listaPagosDeshacerCierre);

            if (null != idPagos && !idPagos.isEmpty()) {

                LOGGER.info("UtilidadesPagoSJCS.deshacerCierre() -> facLineaabonoExtendsMapper.deleteDeshacerCierre() -> Eliminamos las lineas de abono");
                facLineaabonoExtendsMapper.deleteDeshacerCierre(idInstitucion, idPagos);

                LOGGER.info("UtilidadesPagoSJCS.deshacerCierre() -> facHistoricofacturaExtendsMapper.deleteDeshacerCierre() -> Eliminamos el historico de factura");
                facHistoricofacturaExtendsMapper.deleteDeshacerCierre(idInstitucion, idPagos);

                LOGGER.info("UtilidadesPagoSJCS.deshacerCierre() -> facPagosporcajaExtendsMapper.deleteDeshacerCierre() -> Eliminamos los pagos por caja");
                facPagosporcajaExtendsMapper.deleteDeshacerCierre(idInstitucion, idPagos);

                LOGGER.info("UtilidadesPagoSJCS.deshacerCierre() -> facPagoabonoefectivoExtendsMapper.deleteDeshacerCierre() -> Eliminamos los pagos en efectivo");
                facPagoabonoefectivoExtendsMapper.deleteDeshacerCierre(idInstitucion, idPagos);

            }

            LOGGER.info("UtilidadesPagoSJCS.deshacerCierre() -> facAbonoExtendsMapper.hayAbonoPosterior() -> Comprobamos si hay abonos posteriores a los relacionados con nuestro pago");
            List<FacAbono> hayAbonoPosterior = facAbonoExtendsMapper.hayAbonoPosterior(idInstitucion, pago.getIdpagosjg());

            if (!hayAbonoPosterior.isEmpty()) {
                LOGGER.info("UtilidadesPagoSJCS.deshacerCierre() -> facAbonoExtendsMapper.getAbonoAnterior() -> Si no hay abono posterior buscamos el abono anterior al primero relacionado" +
                        "con nuestro pago");

                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String fecha = formato.format(hayAbonoPosterior.get(0).getFecha());

                List<Long> abonoAnterior = facAbonoExtendsMapper.getAbonoAnterior(idInstitucion, fecha);

                AdmContador admContador = new AdmContador();
                admContador.setIdcontador(SigaConstants.CONTADOR_ABONOS_PAGOSJG);
                admContador.setIdinstitucion(idInstitucion);

                if (abonoAnterior.isEmpty()) {
                    admContador.setContador(0L);
                } else {
                    admContador.setContador(abonoAnterior.get(0));
                }

                LOGGER.info("UtilidadesPagoSJCS.deshacerCierre() -> admContadorExtendsMapper.updateByPrimaryKeySelective() -> Actualizamos el contador");
                admContadorExtendsMapper.updateByPrimaryKeySelective(admContador);
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            LOGGER.error(e.getCause());
            LOGGER.error(e);
            throw new Exception("Error primera parte deshacer cierre", e);
         }


        LOGGER.info("UtilidadesPagoSJCS.deshacerCierre() -> acAbonoExtendsMapper.deleteByExample() -> Eliminamos los abonos relacionados con los pagos obtenidos");
        FacAbonoExample facAbonoExample = new FacAbonoExample();
        facAbonoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpagosjgIn(idPagos);
        facAbonoExtendsMapper.deleteByExample(facAbonoExample);

        for (Integer idpagoJG : idPagos) {

            try{
                LOGGER.info("UtilidadesPagoSJCS.deshacerCierre() -> fcsPagosjgExtendsMapper.getFechaEstadoPago() -> Obtenemos la fecha del estado de pago");
                Date fechaPago = fcsPagosjgExtendsMapper.getFechaEstadoPago(idInstitucion, idpagoJG);

                LOGGER.info("UtilidadesPagoSJCS.deshacerCierre() -> ejecucionPlsPago.ejecutarPLDeshacerCierre() -> Ejecutamos el procedimiento de base de datos");
                ejecucionPlsPago.ejecutarPLDeshacerCierre(idInstitucion, fechaPago);

                LOGGER.info("UtilidadesPagoSJCS.deshacerCierre() ->fcsPagosEstadospagosMapper.updateByPrimaryKeySelective() -> Actualizamos el estado de pago a ejecutado");
                FcsPagosEstadospagos record = new FcsPagosEstadospagos();
                record.setIdinstitucion(idInstitucion);
                record.setIdpagosjg(idpagoJG);
                record.setIdestadopagosjg(Short.valueOf(SigaConstants.ESTADO_PAGO_EJECUTADO));
                record.setFechaestado(new Date());
                record.setFechamodificacion(new Date());
                record.setUsumodificacion(usuario.getIdusuario());
                FcsPagosEstadospagos pagoEdit = fcsPagosEstadospagosMapper.selectByPrimaryKey(record);
                if(pagoEdit != null ){
                    fcsPagosEstadospagosMapper.updateByPrimaryKeySelective(record);
                }else{
                    fcsPagosEstadospagosMapper.insertSelective(record);
                }

            }catch (Exception e){
                LOGGER.error(e.getMessage());
                LOGGER.error(e.getCause());
                LOGGER.error(e);
                throw new Exception("Error al insertar el nuevo estado de la facturacion", e);
            }

        }

        if (null != idPagos && !idPagos.isEmpty()) {
            eliminarComunicacionesAbonosPagos(idInstitucion, idPagos);
        }

        LOGGER.info("UtilidadesPagoSJCS.deshacerCierre() -> Salida del servicio para deshacer el cierre del pago");
    }

    private void recursivaDeshacerCierre(Short idInstitucion, Integer idpagosJG) {

        LOGGER.info("UtilidadesPagoSJCS.recursivaDeshacerCierre() -> Entrada la función recursiva de deshacer cierre");

        List<Integer> listaPagosDiferentesAlBuscado = new ArrayList<>();

        LOGGER.info("UtilidadesPagoSJCS.recursivaDeshacerCierre() -> Obtenemos los abonos relacionados con nuestro pago");
        List<Long> listaIdAbonos = facAbonoExtendsMapper.getIdAbonosPorPago(idInstitucion, idpagosJG);

        LOGGER.info("UtilidadesPagoSJCS.recursivaDeshacerCierre() -> Obtenemos los disquetes en los que se encuentran dichos abonos");
        List<Long> listaIdDisquetes = new ArrayList<>();
        if (null != listaIdAbonos && !listaIdAbonos.isEmpty()) {
            listaIdDisquetes = facAbonoincluidoendisqueteExtendsMapper.getDisquetesPorAbonos(idInstitucion, listaIdAbonos);
        }

        listaIdDisquetes.forEach(disquete -> {

            LOGGER.info("UtilidadesPagoSJCS.recursivaDeshacerCierre() -> Comprobamos si hay más pagos diferentes al nuestro en los disquetes");
            listaPagosDiferentesAlBuscado.addAll(facAbonoincluidoendisqueteExtendsMapper.getRestoPagosDisquete(idInstitucion, disquete, idpagosJG));

            LOGGER.info("UtilidadesPagoSJCS.recursivaDeshacerCierre() -> Restablecemos los valores de los abonos y dejamos cada uno de ellos en estado pendiente de pagar por banco");
            int response = facAbonoExtendsMapper.restableceValoresAbono(idInstitucion, disquete);

            LOGGER.info("UtilidadesPagoSJCS.recursivaDeshacerCierre() -> Borramos los abonos incluidos en el disquete");
            FacAbonoincluidoendisqueteExample facAbonoincluidoendisqueteExample = new FacAbonoincluidoendisqueteExample();
            facAbonoincluidoendisqueteExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIddisqueteabonoEqualTo(disquete);
            facAbonoincluidoendisqueteExtendsMapper.deleteByExample(facAbonoincluidoendisqueteExample);

            LOGGER.info("UtilidadesPagoSJCS.recursivaDeshacerCierre() -> Borramos el disquete");
            FacDisqueteabonosExample facDisqueteabonosExample = new FacDisqueteabonosExample();
            facDisqueteabonosExample.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIddisqueteabonoEqualTo(disquete);
            facDisqueteabonosMapper.deleteByExample(facDisqueteabonosExample);
        });

        listaPagosDeshacerCierre.addAll(listaPagosDiferentesAlBuscado);

        for (Integer p : listaPagosDiferentesAlBuscado) {
            recursivaDeshacerCierre(idInstitucion, p);
        }

        LOGGER.info("UtilidadesPagoSJCS.recursivaDeshacerCierre() -> Salida de la función recursiva de deshacer cierre");
    }

    private void eliminarComunicacionesAbonosPagos(Short idInstitucion, List<Integer> idPagos) {

        LOGGER.info("UtilidadesPagoSJCS.eliminarComunicacionesAbonosPagos() -> Entrada al servicio de eliminar comunicaciones");

        List<String> idPagosString = idPagos.stream().map(p -> p.toString()).collect(Collectors.toList());

        envProgrampagosExtendsMapper.disableConstraint();

        envEnvioprogramadoExtendsMapper.eliminarEnviosPago(idInstitucion, idPagosString);

        envEnviosExtendsMapper.eliminarEnviosPago(idInstitucion, idPagosString);

        EnvProgrampagosExample envProgrampagosExample = new EnvProgrampagosExample();
        envProgrampagosExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
                .andIdpagoIn(idPagos);

        envProgrampagosExtendsMapper.deleteByExample(envProgrampagosExample);

        envProgrampagosExtendsMapper.enableConstraint();

        LOGGER.info("UtilidadesPagoSJCS.eliminarComunicacionesAbonosPagos() -> Salida del servicio de eliminar comunicaciones");

    }
}
