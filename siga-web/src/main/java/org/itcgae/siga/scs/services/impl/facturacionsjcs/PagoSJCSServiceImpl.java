package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.channels.FileChannel;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.ListIterator;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.ColegiadosPagoDTO;
import org.itcgae.siga.DTOs.scs.CompensacionFacDTO;
import org.itcgae.siga.DTOs.scs.CompensacionFacItem;
import org.itcgae.siga.DTOs.scs.ConceptoPagoDTO;
import org.itcgae.siga.DTOs.scs.ConceptoPagoItem;
import org.itcgae.siga.DTOs.scs.DetallePagoColegiadoDTO;
import org.itcgae.siga.DTOs.scs.PagosjgDTO;
import org.itcgae.siga.DTOs.scs.PagosjgItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SIGAHelper;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.entities.AdmContadorKey;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoKey;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.FacAbono;
import org.itcgae.siga.db.entities.FacAbonoKey;
import org.itcgae.siga.db.entities.FacEstadofactura;
import org.itcgae.siga.db.entities.FacEstadofacturaExample;
import org.itcgae.siga.db.entities.FacFactura;
import org.itcgae.siga.db.entities.FacFacturaExample;
import org.itcgae.siga.db.entities.FacFacturaKey;
import org.itcgae.siga.db.entities.FacLineaabono;
import org.itcgae.siga.db.entities.FacPagoabonoefectivo;
import org.itcgae.siga.db.entities.FacPagosporcaja;
import org.itcgae.siga.db.entities.FacPropositos;
import org.itcgae.siga.db.entities.FacPropositosExample;
import org.itcgae.siga.db.entities.FacSufijo;
import org.itcgae.siga.db.entities.FacSufijoExample;
import org.itcgae.siga.db.entities.FcsAplicaMovimientosvarios;
import org.itcgae.siga.db.entities.FcsAplicaMovimientosvariosExample;
import org.itcgae.siga.db.entities.FcsCobrosRetencionjudicial;
import org.itcgae.siga.db.entities.FcsCobrosRetencionjudicialExample;
import org.itcgae.siga.db.entities.FcsFactGrupofactHito;
import org.itcgae.siga.db.entities.FcsFactGrupofactHitoExample;
import org.itcgae.siga.db.entities.FcsPagoColegiado;
import org.itcgae.siga.db.entities.FcsPagoColegiadoExample;
import org.itcgae.siga.db.entities.FcsPagoGrupofactHito;
import org.itcgae.siga.db.entities.FcsPagoGrupofactHitoExample;
import org.itcgae.siga.db.entities.FcsPagosEstadospagos;
import org.itcgae.siga.db.entities.FcsPagosEstadospagosExample;
import org.itcgae.siga.db.entities.FcsPagosEstadospagosKey;
import org.itcgae.siga.db.entities.FcsPagosjg;
import org.itcgae.siga.db.entities.FcsPagosjgKey;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.itcgae.siga.db.entities.GenRecursosKey;
import org.itcgae.siga.db.mappers.CenColegiadoMapper;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.FacEstadofacturaMapper;
import org.itcgae.siga.db.mappers.FacFacturaMapper;
import org.itcgae.siga.db.mappers.FcsAplicaMovimientosvariosMapper;
import org.itcgae.siga.db.mappers.FcsCobrosRetencionjudicialMapper;
import org.itcgae.siga.db.mappers.FcsPagoGrupofactHitoMapper;
import org.itcgae.siga.db.mappers.FcsPagosEstadospagosMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.mappers.GenRecursosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmContadorExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacAbonoExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacBancoinstitucionExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacDisquetecargosExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacHistoricofacturaExtendsMapper;
import org.itcgae.siga.db.services.fac.mappers.FacLineaabonoExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FacPagoabonoefectivoExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FacPagosporcajaExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FacPropositosExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FacSufijoExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsFactGrupofactHitoExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsFacturacionJGExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsPagoColegiadoExtendsMapper;
import org.itcgae.siga.db.services.fcs.mappers.FcsPagosjgExtendsMapper;
import org.itcgae.siga.exception.FacturacionSJCSException;
import org.itcgae.siga.scs.services.facturacionsjcs.IPagoSJCSService;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfSignatureAppearance;
import com.lowagie.text.pdf.PdfStamper;

@Service
public class PagoSJCSServiceImpl implements IPagoSJCSService {

    private final Logger LOGGER = Logger.getLogger(PagoSJCSServiceImpl.class);

    @Autowired
    private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

    @Autowired
    private GenParametrosExtendsMapper genParametrosMapper;

    @Autowired
    private FcsPagosjgExtendsMapper fcsPagosjgExtendsMapper;

    @Autowired
    private FcsFacturacionJGExtendsMapper fcsFacturacionJGExtendsMapper;

    @Autowired
    private FcsPagosEstadospagosMapper fcsPagosEstadospagosMapper;

    @Autowired
    private FcsFactGrupofactHitoExtendsMapper fcsFactGrupofactHitoExtendsMapper;

    @Autowired
    private FcsPagoGrupofactHitoMapper fcsPagoGrupofactHitoMapper;

    @Autowired
    private FacPropositosExtendsMapper facPropositosExtendsMapper;

    @Autowired
    private FacSufijoExtendsMapper facSufijoExtendsMapper;

    @Autowired
    private FacBancoinstitucionExtendsMapper facBancoinstitucionExtendsMapper;

    @Autowired
    private FcsPagoColegiadoExtendsMapper fcsPagoColegiadoExtendsMapper;

    @Autowired
    private FcsAplicaMovimientosvariosMapper fcsAplicaMovimientosvariosMapper;

    @Autowired
    private UtilidadesFacturacionSJCS utilidadesFacturacionSJCS;

    @Autowired
    private GenRecursosMapper genRecursosMapper;

    @Autowired
    private CenPersonaMapper cenPersonaMapper;

    @Autowired
    private FacAbonoExtendsMapper facAbonoExtendsMapper;

    @Autowired
    private AdmContadorExtendsMapper admContadorExtendsMapper;

    @Autowired
    private FacLineaabonoExtendsMapper facLineaabonoExtendsMapper;

    @Autowired
    private FacFacturaMapper facFacturaMapper;

    @Autowired
    private FacPagoabonoefectivoExtendsMapper facPagoabonoefectivoExtendsMapper;

    @Autowired
    private FacPagosporcajaExtendsMapper facPagosporcajaExtendsMapper;

    @Autowired
    private FacDisquetecargosExtendsMapper facDisquetecargosExtendsMapper;

    @Autowired
    private CenColegiadoMapper cenColegiadoMapper;

    @Autowired
    private GenPropertiesMapper genPropertiesMapper;

    @Autowired
    private FacEstadofacturaMapper facEstadofacturaMapper;

    @Autowired
    private FacHistoricofacturaExtendsMapper facHistoricofacturaExtendsMapper;

    @Autowired
    private FcsCobrosRetencionjudicialMapper fcsCobrosRetencionjudicialMapper;

    @Autowired
    private UtilidadesPagoSJCS utilidadesPagoSJCS;

    @Override
    public PagosjgDTO buscarPagos(PagosjgItem pagosItem, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        PagosjgDTO pagos = new PagosjgDTO();
        String idLenguaje = "";
        List<GenParametros> tamMax = null;
        Integer tamMaximo = null;
        Error error = new Error();

        try {

            if (null != idInstitucion) {
                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
                LOGGER.info(
                        "PagoSJCSServiceImpl.buscarPagos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "PagoSJCSServiceImpl.buscarPagos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && usuarios.size() > 0) {
                    AdmUsuarios usuario = usuarios.get(0);
                    usuario.setIdinstitucion(idInstitucion);
                    idLenguaje = usuario.getIdlenguaje();

                    GenParametrosExample genParametrosExample = new GenParametrosExample();
                    genParametrosExample.createCriteria().andModuloEqualTo("SCS")
                            .andParametroEqualTo("TAM_MAX_CONSULTA_JG")
                            .andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));
                    genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

                    LOGGER.info(
                            "PagoSJCSServiceImpl.buscarPagos() / genParametrosMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");

                    tamMax = genParametrosMapper.selectByExample(genParametrosExample);

                    LOGGER.info(
                            "PagoSJCSServiceImpl.buscarPagos() / genParametrosMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");

                    if (tamMax != null) {
                        tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
                    } else {
                        tamMaximo = null;
                    }

                    LOGGER.info(
                            "PagoSJCSServiceImpl.buscarPagos() / fcsFacturacionJGExtendsMapper.buscarPagos() -> Entrada a fcsFacturacionJGExtendsMapper para obtener los pagos");
                    List<PagosjgItem> pagosItems = fcsPagosjgExtendsMapper.buscarPagos(pagosItem,
                            idInstitucion.toString(), idLenguaje, tamMaximo);

                    if (null != pagosItems && pagosItems.size() > tamMaximo) {
                        pagosItems.remove(pagosItems.size() - 1);
                        error.setCode(200);
                        error.setDescription("general.message.consulta.resultados");
                    }

                    pagos.setPagosjgItem(pagosItems);
                    LOGGER.info(
                            "PagoSJCSServiceImpl.buscarPagos() / fcsFacturacionJGExtendsMapper.buscarPagos() -> Salida a fcsFacturacionJGExtendsMapper para obtener los pagos");
                } else {
                    LOGGER.warn(
                            "PagoSJCSServiceImpl.buscarPagos() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
                                    + dni + " e idInstitucion = " + idInstitucion);
                }
            } else {
                LOGGER.warn("PagoSJCSServiceImpl.buscarPagos() -> idInstitucion del token nula");
            }

        } catch (Exception e) {
            LOGGER.error("PagoSJCSServiceImpl.buscarPagos() -> Se ha producido un error al buscar los pagos",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        pagos.setError(error);
        LOGGER.info("PagoSJCSServiceImpl.buscarPagos() -> Salida del servicio para obtener los pagos");

        return pagos;
    }

    @Override
    public PagosjgDTO getPago(String idPago, HttpServletRequest request) {

        LOGGER.info("PagoSJCSServiceImpl.getPago() -> Entrada al servicio para obtener los datos del pago: " + idPago);

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        PagosjgDTO pagosjgDTO = new PagosjgDTO();
        Error error = new Error();

        try {

            if (null != idInstitucion) {
                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
                LOGGER.info(
                        "PagoSJCSServiceImpl.getPago() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "PagoSJCSServiceImpl.getPago() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && usuarios.size() > 0) {

                    PagosjgItem pagosjgItem = fcsPagosjgExtendsMapper.getPago(idPago, idInstitucion, usuarios.get(0).getIdlenguaje());
                    pagosjgDTO.setPagosjgItem(Collections.singletonList(pagosjgItem));
                }

            }

        } catch (Exception e) {
            LOGGER.error(
                    "PagoSJCSServiceImpl.getPago() -> Se ha producido un error al buscar el pago: " + idPago, e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        pagosjgDTO.setError(error);

        LOGGER.info("PagoSJCSServiceImpl.getPago() -> Salida del servicio para obtener los datos del pago: " + idPago);

        return pagosjgDTO;
    }

    @Override
    public PagosjgDTO datosGeneralesPagos(String idPago, HttpServletRequest request) {

        LOGGER.info(
                "PagoSJCSServiceImpl.datosGeneralesPagos() -> Entrada para obtener los datos generales del pago: "
                        + idPago);

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        PagosjgDTO pagosjgDTO = new PagosjgDTO();
        Error error = new Error();

        if (null != idInstitucion) {

            try {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
                LOGGER.info(
                        "PagoSJCSServiceImpl.datosGeneralesPagos() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "PagoSJCSServiceImpl.datosGeneralesPagos() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    AdmUsuarios usuario = usuarios.get(0);
                    usuario.setIdinstitucion(idInstitucion);

                    LOGGER.info(
                            "PagoSJCSServiceImpl.datosGeneralesPagos() -> fcsFacturacionJGExtendsMapper.datosGeneralesPagos() -> Entrada para obtener los datos del pago");
                    List<PagosjgItem> pago = fcsPagosjgExtendsMapper.datosGeneralesPagos(idPago,
                            idInstitucion.toString());
                    pagosjgDTO.setPagosjgItem(pago);
                    LOGGER.info(
                            "PagoSJCSServiceImpl.datosGeneralesPagos() -> fcsFacturacionJGExtendsMapper.datosGeneralesPagos() -> Salida para obtener los datos del pago");
                } else {
                    LOGGER.warn(
                            "PagoSJCSServiceImpl.datosGeneralesPagos() -> admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
                                    + dni + " e idInstitucion = " + idInstitucion);
                }

            } catch (Exception e) {
                LOGGER.error(
                        "PagoSJCSServiceImpl.datosGeneralesPagos() -> Se ha producido un error al buscar los datos generales del pago: "
                                + idPago,
                        e);
                error.setCode(500);
                error.setDescription("general.mensaje.error.bbdd");
            }

        } else {
            LOGGER.warn("PagoSJCSServiceImpl.datosGeneralesPagos() -> idInstitucion del token nula");
        }

        pagosjgDTO.setError(error);

        LOGGER.info("PagoSJCSServiceImpl.datosGeneralesPagos() -> Salida con los datos generales del pago: "
                + idPago);

        return pagosjgDTO;
    }

    @Override
    public PagosjgDTO historicoPagos(String idPago, HttpServletRequest request) {

        LOGGER.info(
                "PagoSJCSServiceImpl.historicoPagos() -> Entrada al servicio para obtener el histórico de un pago");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        PagosjgDTO pagosjgDTO = new PagosjgDTO();
        Error error = new Error();

        try {

            if (null != idInstitucion) {
                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
                LOGGER.info(
                        "PagoSJCSServiceImpl.historicoPagos() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "PagoSJCSServiceImpl.historicoPagos() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    String idLenguaje = usuarios.get(0).getIdlenguaje();

                    LOGGER.info(
                            "PagoSJCSServiceImpl.historicoPagos() -> Entrada a fcsFacturacionJGExtendsMapper para obtener los historicos de estados del pago");
                    List<PagosjgItem> listaEstados = fcsPagosjgExtendsMapper.historicoPagos(idPago, idLenguaje,
                            idInstitucion);
                    LOGGER.info(
                            "PagoSJCSServiceImpl.historicoPagos() -> Salida a fcsFacturacionJGExtendsMapper para obtener los historicos de estados del pago");
                    pagosjgDTO.setPagosjgItem(listaEstados);
                }
            }

        } catch (Exception e) {
            LOGGER.error(
                    "PagoSJCSServiceImpl.historicoPagos() -> Se ha producido un error al buscar el histórico de estados del pago: "
                            + idPago,
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        pagosjgDTO.setError(error);

        LOGGER.info(
                "PagoSJCSServiceImpl.historicoPagos() -> Salida del servicio para obtener el histórico de un pago");

        return pagosjgDTO;
    }

    @Override
    public InsertResponseDTO savePago(PagosjgItem pagosjgItem, HttpServletRequest request) {

        LOGGER.info("PagoSJCSServiceImpl.savePago() -> Entrada al servicio para la creación de un pago");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
        Error error = new Error();
        int response = 0;

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "PagoSJCSServiceImpl.savePago() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "PagoSJCSServiceImpl.savePago() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    LOGGER.info(
                            "PagoSJCSServiceImpl.savePago() -> fcsPagosjgMapper.getNewIdPago() -> Entrada al método para obtener un nuevo identificador de pago");
                    NewIdDTO newId = fcsPagosjgExtendsMapper.getNewIdPago(idInstitucion);
                    LOGGER.info(
                            "PagoSJCSServiceImpl.savePago() -> fcsPagosjgMapper.getNewIdPago() -> Salida del método para obtener un nuevo identificador de pago: "
                                    + newId.getNewId());

                    FcsPagosjg record = new FcsPagosjg();
                    record.setIdinstitucion(idInstitucion);
                    record.setIdpagosjg(Integer.valueOf(newId.getNewId()));
                    record.setIdfacturacion(Integer.valueOf(pagosjgItem.getIdFacturacion()));
                    record.setNombre(pagosjgItem.getNombre());
                    record.setAbreviatura(pagosjgItem.getAbreviatura());
                    record.setFechadesde(pagosjgItem.getFechaDesde());
                    record.setFechahasta(pagosjgItem.getFechaHasta());
                    record.setCriteriopagoturno("F");
                    record.setImportepagado(BigDecimal.ZERO);
                    record.setImporterepartir(BigDecimal.ZERO);
                    record.setImporteoficio(BigDecimal.ZERO);
                    record.setImporteguardia(BigDecimal.ZERO);
                    record.setImportesoj(BigDecimal.ZERO);
                    record.setImporteejg(BigDecimal.ZERO);
                    record.setImporteminimo(BigDecimal.ZERO);
                    record.setUsumodificacion(usuarios.get(0).getIdusuario());
                    record.setFechamodificacion(new Date());

                    // Buscamos el propósito sepa por defecto de la institución y lo seteamos en el pago
                    GenParametrosExample genParametrosExample = new GenParametrosExample();
                    genParametrosExample.createCriteria().andParametroEqualTo(SigaConstants.PARAMETRO_PROPOSITO_TRANSFERENCIA_SEPA)
                            .andModuloEqualTo(SigaConstants.MODULO_FACTURACION)
                            .andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));

                    genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

                    LOGGER.info(
                            "PagoSJCSServiceImpl.savePago() -> genParametrosMapper.selectByExample() -> Obtenenos el proposito SEPA por defecto");
                    List<GenParametros> parametrosPropSepa = genParametrosMapper.selectByExample(genParametrosExample);

                    if (null != parametrosPropSepa && !parametrosPropSepa.isEmpty()) {

                        FacPropositosExample facPropositosExample = new FacPropositosExample();
                        facPropositosExample.createCriteria().andCodigoEqualTo(parametrosPropSepa.get(0).getValor());
                        List<FacPropositos> propositos = facPropositosExtendsMapper.selectByExample(facPropositosExample);

                        if (null != propositos && !propositos.isEmpty()) {
                            record.setIdpropsepa(propositos.get(0).getIdproposito());
                        }

                    }

                    // Buscamos el propósito para otras transferencias por defecto de la institución y lo seteamos en el pago
                    genParametrosExample = new GenParametrosExample();
                    genParametrosExample.createCriteria().andParametroEqualTo(SigaConstants.PARAMETRO_PROPOSITO_OTRA_TRANFERENCIA)
                            .andModuloEqualTo(SigaConstants.MODULO_FACTURACION)
                            .andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, idInstitucion));


                    LOGGER.info(
                            "PagoSJCSServiceImpl.savePago() -> genParametrosMapper.selectByExample() -> Obtenenos el proposito para otras transferencias por defecto");
                    List<GenParametros> parametrosPropOtraTrans = genParametrosMapper.selectByExample(genParametrosExample);

                    if (null != parametrosPropOtraTrans && !parametrosPropOtraTrans.isEmpty()) {

                        FacPropositosExample facPropositosExample = new FacPropositosExample();
                        facPropositosExample.createCriteria().andCodigoEqualTo(parametrosPropOtraTrans.get(0).getValor());
                        List<FacPropositos> propositos = facPropositosExtendsMapper.selectByExample(facPropositosExample);

                        if (null != propositos && !propositos.isEmpty()) {
                            record.setIdpropotros(propositos.get(0).getIdproposito());
                        }
                    }

                    // Si sólo encontramos un sufijo lo ponemos por defecto en el pago
                    FacSufijoExample facSufijoExample = new FacSufijoExample();
                    facSufijoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion);

                    LOGGER.info(
                            "PagoSJCSServiceImpl.savePago() -> facSufijoExtendsMapper.selectByExample() -> Obtenenos la lista de sufijos");
                    List<FacSufijo> listaSufijos = facSufijoExtendsMapper.selectByExample(facSufijoExample);
                    LOGGER.info(
                            "PagoSJCSServiceImpl.savePago() -> facSufijoExtendsMapper.selectByExample() -> Lista de sufijos obtenida");

                    if (listaSufijos.size() == 1) {
                        record.setIdsufijo(listaSufijos.get(0).getIdsufijo());
                    }

                    LOGGER.info(
                            "PagoSJCSServiceImpl.savePago() -> fcsPagosjgMapper.insertSelective() -> Entrada al método para insertar el nuevo pago: "
                                    + newId.getNewId());
                    response = fcsPagosjgExtendsMapper.insertSelective(record);
                    LOGGER.info(
                            "PagoSJCSServiceImpl.savePago() -> fcsPagosjgMapper.insertSelective() -> Salida del método para insertar el nuevo pago: "
                                    + newId.getNewId());

                    FcsPagosEstadospagos record2 = new FcsPagosEstadospagos();
                    record2.setIdinstitucion(idInstitucion);
                    record2.setIdpagosjg(Integer.valueOf(newId.getNewId()));
                    record2.setIdestadopagosjg(Short.valueOf("10"));
                    record2.setFechaestado(new Date());
                    record2.setFechamodificacion(new Date());
                    record2.setUsumodificacion(usuarios.get(0).getIdusuario());

                    LOGGER.info(
                            "PagoSJCSServiceImpl.savePago() -> fcsPagosEstadospagosMapper.insertSelective() -> Entrada al método para insertar el nuevo estado al pago: "
                                    + newId.getNewId());
                    fcsPagosEstadospagosMapper.insertSelective(record2);
                    LOGGER.info(
                            "PagoSJCSServiceImpl.savePago() -> fcsPagosEstadospagosMapper.insertSelective() -> Salida del método para insertar el nuevo estado al pago: "
                                    + newId.getNewId());

                    if (response == 0) {
                        insertResponseDTO.setStatus(SigaConstants.KO);
                        error.setCode(500);
                        error.setDescription("general.message.error.realiza.accion");
                    } else {
                        insertResponseDTO.setStatus(SigaConstants.OK);
                        insertResponseDTO.setId(newId.getNewId());
                    }

                }

            }

        } catch (Exception e) {
            LOGGER.error("PagoSJCSServiceImpl.savePago() -> Se ha producido un error en la creación del pago",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        insertResponseDTO.setError(error);

        LOGGER.info("PagoSJCSServiceImpl.savePago() -> Salida del servicio para la creación de un pago");

        return insertResponseDTO;
    }

    @Override
    public UpdateResponseDTO updatePago(PagosjgItem pagosjgItem, HttpServletRequest request) {

        LOGGER.info("PagoSJCSServiceImpl.updatePago() -> Entrada al servicio para la actualización de un pago");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
        Error error = new Error();
        int response = 0;

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "PagoSJCSServiceImpl.updatePago() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "PagoSJCSServiceImpl.updatePago() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    FcsPagosjg record = new FcsPagosjg();
                    record.setIdinstitucion(idInstitucion);
                    record.setIdpagosjg(Integer.valueOf(pagosjgItem.getIdPagosjg()));
                    record.setIdfacturacion(Integer.valueOf(pagosjgItem.getIdFacturacion()));
                    record.setNombre(pagosjgItem.getNombre());
                    record.setAbreviatura(pagosjgItem.getAbreviatura());
                    record.setUsumodificacion(usuarios.get(0).getIdusuario());
                    record.setFechamodificacion(new Date());

                    LOGGER.info(
                            "PagoSJCSServiceImpl.updatePago() -> fcsPagosjgMapper.updateByPrimaryKeySelective() -> Entrada al método para actualizar el pago: "
                                    + pagosjgItem.getIdPagosjg());
                    response = fcsPagosjgExtendsMapper.updateByPrimaryKeySelective(record);
                    LOGGER.info(
                            "PagoSJCSServiceImpl.updatePago() -> fcsPagosjgMapper.updateByPrimaryKeySelective() -> Salida del método para actualizar el pago: "
                                    + pagosjgItem.getIdPagosjg());

                    if (response == 0) {
                        updateResponseDTO.setStatus(SigaConstants.KO);
                        error.setCode(500);
                        error.setDescription("general.message.error.realiza.accion");
                    } else {
                        updateResponseDTO.setStatus(SigaConstants.OK);
                    }

                }

            }

        } catch (Exception e) {
            LOGGER.error(
                    "PagoSJCSServiceImpl.updatePago() -> Se ha producido un error en la actualización del pago",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        updateResponseDTO.setError(error);

        LOGGER.info("PagoSJCSServiceImpl.updatePago() -> Salida del servicio para la actualización de un pago");

        return updateResponseDTO;
    }

    @Override
    public DeleteResponseDTO deletePago(PagosjgItem pagosjgItem, HttpServletRequest request) {

        LOGGER.info(
                "PagoSJCSServiceImpl.deletePago() -> Entrada al servicio para el borrado fisico del pago: " + pagosjgItem.getIdPagosjg());

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
        Error error = new Error();

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "PagoSJCSServiceImpl.deletePago() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "PagoSJCSServiceImpl.deletePago() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    FcsPagosjgKey fcsPagosjgKey = new FcsPagosjgKey();
                    fcsPagosjgKey.setIdpagosjg(Integer.valueOf(pagosjgItem.getIdPagosjg()));
                    fcsPagosjgKey.setIdinstitucion(idInstitucion);

                    FcsPagosjg pago = fcsPagosjgExtendsMapper.selectByPrimaryKey(fcsPagosjgKey);
                    // Obtenemos el idFacturacion
                    Integer idFacturacion = pago.getIdfacturacion();

                    // Recupero el nombre de los ficheros asociados a la facturacion
                    Hashtable nombreFicheros = utilidadesFacturacionSJCS.getNombreFicherosPago(idInstitucion, idFacturacion, pago.getIdpagosjg(), null);

                    // deshaciendo los cobros de retenciones judiciales
                    FcsCobrosRetencionjudicialExample fcsCobrosRetencionjudicialExample = new FcsCobrosRetencionjudicialExample();
                    fcsCobrosRetencionjudicialExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
                            .andIdpagosjgEqualTo(pago.getIdpagosjg());

                    List<FcsCobrosRetencionjudicial> vAuxCobrosRetencionJudicial = fcsCobrosRetencionjudicialMapper.selectByExample(fcsCobrosRetencionjudicialExample);
                    if ((vAuxCobrosRetencionJudicial != null) && (!vAuxCobrosRetencionJudicial.isEmpty())) {
                        fcsCobrosRetencionjudicialMapper.deleteByExample(fcsCobrosRetencionjudicialExample);
                    }

                    FcsPagoGrupofactHitoExample fcsPagoGrupofactHitoExample = new FcsPagoGrupofactHitoExample();
                    fcsPagoGrupofactHitoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
                            .andIdpagosjgEqualTo(pago.getIdpagosjg());

                    List<FcsPagoGrupofactHito> vAuxPagoGrupoFactHito = fcsPagoGrupofactHitoMapper.selectByExample(fcsPagoGrupofactHitoExample);
                    if ((vAuxPagoGrupoFactHito != null) && (!vAuxPagoGrupoFactHito.isEmpty())) {
                        fcsPagoGrupofactHitoMapper.deleteByExample(fcsPagoGrupofactHitoExample);
                    }

                    FcsAplicaMovimientosvariosExample fcsAplicaMovimientosvariosExample = new FcsAplicaMovimientosvariosExample();
                    fcsAplicaMovimientosvariosExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
                            .andIdpagosjgEqualTo(pago.getIdpagosjg());

                    List<FcsAplicaMovimientosvarios> vAuxMovimientosAplicados = fcsAplicaMovimientosvariosMapper.selectByExample(fcsAplicaMovimientosvariosExample);
                    if ((vAuxMovimientosAplicados != null) && (!vAuxMovimientosAplicados.isEmpty())) {
                        fcsAplicaMovimientosvariosMapper.deleteByExample(fcsAplicaMovimientosvariosExample);
                    }

                    FcsPagoColegiadoExample fcsPagoColegiadoExample = new FcsPagoColegiadoExample();
                    fcsPagoColegiadoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
                            .andIdpagosjgEqualTo(pago.getIdpagosjg());

                    List<FcsPagoColegiado> vAuxPagoColegiado = fcsPagoColegiadoExtendsMapper.selectByExample(fcsPagoColegiadoExample);
                    if ((vAuxPagoColegiado != null) && (!vAuxPagoColegiado.isEmpty())) {
                        fcsPagoColegiadoExtendsMapper.deleteByExample(fcsPagoColegiadoExample);
                    }

                    FcsPagosEstadospagosExample fcsPagosEstadospagosExample = new FcsPagosEstadospagosExample();
                    fcsPagosEstadospagosExample.createCriteria().andIdpagosjgEqualTo(pago.getIdpagosjg())
                            .andIdinstitucionEqualTo(idInstitucion);

                    fcsPagosEstadospagosMapper.deleteByExample(fcsPagosEstadospagosExample);

                    //Borramos: se hace borrado en cascada de todos los apuntes, movimientos varios, estados, criterios pago y cobros de retenciones judiciales
                    if (fcsPagosjgExtendsMapper.deleteByPrimaryKey(fcsPagosjgKey) == 1) {
                        // borrado fisico de ficheros del servidor web
                        utilidadesFacturacionSJCS.borrarFicheros(idInstitucion, nombreFicheros);
                    }

                    deleteResponseDTO.setStatus(SigaConstants.OK);

                }

            }

        } catch (Exception e) {
            LOGGER.error(
                    "PagoSJCSServiceImpl.deletePago() -> Se ha producido un error en la eliminación del pago: " + pagosjgItem.getIdPagosjg(),
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
            deleteResponseDTO.setStatus(SigaConstants.KO);
        }

        deleteResponseDTO.setError(error);

        LOGGER.info(
                "PagoSJCSServiceImpl.deletePago() -> Salida del servicio para el borrado fisico del pago: " + pagosjgItem.getIdPagosjg());

        return deleteResponseDTO;
    }

    @Override
    public ConceptoPagoDTO comboConceptosPago(String idFacturacion, String idPago, HttpServletRequest request) {

        LOGGER.info(
                "PagoSJCSServiceImpl.comboConceptosPago() -> Entrada al servicio para obtener el combo de los conceptos del pago");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        ConceptoPagoDTO conceptoPagoDTO = new ConceptoPagoDTO();
        Error error = new Error();

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "PagoSJCSServiceImpl.comboConceptosPago() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "PagoSJCSServiceImpl.comboConceptosPago() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    LOGGER.info(
                            "PagoSJCSServiceImpl.comboConceptosPago() -> fcsPagosjgExtendsMapper.comboConceptosPago() -> Entrada para obtener el combo de los conceptos del pago según su facturación");

                    List<ConceptoPagoItem> listaConceptosFac = fcsPagosjgExtendsMapper.comboConceptosPago(idInstitucion, idFacturacion, idPago, usuarios.get(0).getIdlenguaje());
                    conceptoPagoDTO.setListaConceptos(listaConceptosFac);
                    LOGGER.info(
                            "PagoSJCSServiceImpl.comboConceptosPago() -> fcsPagosjgExtendsMapper.comboConceptosPago() -> Salida de obtener el combo de los conceptos del pago según su facturación");

                }

            }

        } catch (Exception e) {
            LOGGER.error(
                    "PagoSJCSServiceImpl.comboConceptosPago() -> Se ha producido un error en la obtención del combo de conceptos del pago",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        conceptoPagoDTO.setError(error);

        LOGGER.info(
                "PagoSJCSServiceImpl.comboConceptosPago() -> Salida del servicio para obtener el combo de los conceptos del pago");

        return conceptoPagoDTO;
    }

//	@Override
//	public ConceptoPagoDTO getConceptosPago(String idPago, String idFacturacion, HttpServletRequest request) {
//
//		LOGGER.info(
//				"PagoSJCSServiceImpl.getConceptosPago() -> Entrada al servicio para obtener los conceptos del pago: "
//						+ idPago);
//
//		String token = request.getHeader("Authorization");
//		String dni = UserTokenUtils.getDniFromJWTToken(token);
//		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
//		ConceptoPagoDTO conceptoPagoDTO = new ConceptoPagoDTO();
//		Error error = new Error();
//		List<Integer> responses = new ArrayList<Integer>();
//		List<FcsFactGrupofactHito> listaConceptosFac = new ArrayList<FcsFactGrupofactHito>();
//		List<ConceptoPagoItem> listaConceptosPago = new ArrayList<ConceptoPagoItem>();
//
//		try {
//
//			if (null != idInstitucion) {
//
//				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
//				exampleUsuarios.createCriteria().andNifEqualTo(dni)
//						.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
//
//				LOGGER.info(
//						"PagoSJCSServiceImpl.getConceptosPago() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
//				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
//				LOGGER.info(
//						"PagoSJCSServiceImpl.getConceptosPago() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");
//
//				if (null != usuarios && !usuarios.isEmpty()) {
//
//					FcsFactGrupofactHitoExample fcsFactGrupofactHitoExample = new FcsFactGrupofactHitoExample();
//					fcsFactGrupofactHitoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
//							.andIdfacturacionEqualTo(Integer.valueOf(idFacturacion));
//
//					LOGGER.info(
//							"PagoSJCSServiceImpl.getConceptosPago() -> fcsFactGrupofactHitoMapper.selectByExample() -> Entrada para la obtención de los conceptos asociados a la facturación: "
//									+ idFacturacion);
//					listaConceptosFac = fcsFactGrupofactHitoMapper.selectByExample(fcsFactGrupofactHitoExample);
//					LOGGER.info(
//							"PagoSJCSServiceImpl.getConceptosPago() -> fcsFactGrupofactHitoMapper.selectByExample() -> Salida de la obtención de los conceptos asociados a la facturación: "
//									+ idFacturacion);
//
//					FcsPagoGrupofactHitoExample fcsPagoGrupofactHitoExample = new FcsPagoGrupofactHitoExample();
//					fcsPagoGrupofactHitoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
//							.andIdpagosjgEqualTo(Integer.valueOf(idPago));
//
//					LOGGER.info(
//							"PagoSJCSServiceImpl.getConceptosPago() -> fcsPagoGrupofactHitoMapper.deleteByExample() -> Entrada para la eliminación de los conceptos del pago: "
//									+ idPago);
//					fcsPagoGrupofactHitoMapper.deleteByExample(fcsPagoGrupofactHitoExample);
//					LOGGER.info(
//							"PagoSJCSServiceImpl.getConceptosPago() -> fcsPagoGrupofactHitoMapper.deleteByExample() -> Salida de la eliminación de los conceptos del pago: "
//									+ idPago);
//
//					if (!listaConceptosFac.isEmpty()) {
//
//						for (FcsFactGrupofactHito fcsFactGrupofactHito : listaConceptosFac) {
//
//							FcsPagoGrupofactHito fcsPagoGrupofactHito = new FcsPagoGrupofactHito();
//							fcsPagoGrupofactHito.setIdinstitucion(idInstitucion);
//							fcsPagoGrupofactHito.setIdpagosjg(Integer.valueOf(idPago));
//							fcsPagoGrupofactHito.setIdhitogeneral(fcsFactGrupofactHito.getIdhitogeneral());
//							fcsPagoGrupofactHito.setIdgrupofacturacion(fcsFactGrupofactHito.getIdgrupofacturacion());
//							fcsPagoGrupofactHito.setFechamodificacion(new Date());
//							fcsPagoGrupofactHito.setUsumodificacion(usuarios.get(0).getIdusuario());
//
//							LOGGER.info(
//									"PagoSJCSServiceImpl.getConceptosPago() -> fcsPagoGrupofactHitoMapper.insertSelective() -> Entrada para la inserción de los conceptos del pago: "
//											+ idPago);
//							int response = fcsPagoGrupofactHitoMapper.insertSelective(fcsPagoGrupofactHito);
//							LOGGER.info(
//									"PagoSJCSServiceImpl.getConceptosPago() -> fcsPagoGrupofactHitoMapper.insertSelective() -> Salida de la inserción de los conceptos del pago: "
//											+ idPago);
//
//							responses.add(response);
//
//						}
//
//						LOGGER.info(
//								"PagoSJCSServiceImpl.getConceptosPago() ->fcsPagosjgExtendsMapper.getConceptosPago() -> Entrada para la obtención de los conceptos del pago: "
//										+ idPago);
//						listaConceptosPago = fcsPagosjgExtendsMapper.getConceptosPago(idPago, idInstitucion,
//								usuarios.get(0).getIdlenguaje());
//						LOGGER.info(
//								"PagoSJCSServiceImpl.getConceptosPago() ->fcsPagosjgExtendsMapper.getConceptosPago() -> Salida de la obtención de los conceptos del pago: "
//										+ idPago);
//
//						conceptoPagoDTO.setListaConceptos(listaConceptosPago);
//					}
//
//				}
//
//			}
//
//		} catch (Exception e) {
//			LOGGER.error(
//					"PagoSJCSServiceImpl.getConceptosPago() -> Se ha producido un error en la obtención de los conceptos del pago: "
//							+ idPago,
//					e);
//			error.setCode(500);
//			error.setDescription("general.mensaje.error.bbdd");
//		}
//
//		if (!listaConceptosFac.isEmpty() && !responses.isEmpty() && responses.contains(0)) {
//			LOGGER.error(
//					"PagoSJCSServiceImpl.getConceptosPago() -> Se ha producido un error en la obtención de los conceptos del pago: "
//							+ idPago);
//			error.setCode(400);
//			error.setDescription("general.message.error.realiza.accion");
//		}
//
//		conceptoPagoDTO.setError(error);
//
//		LOGGER.info(
//				"PagoSJCSServiceImpl.getConceptosPago() -> Salida del servicio para obtener los conceptos del pago: "
//						+ idPago);
//
//		return conceptoPagoDTO;
//	}

    @Override
    public ConceptoPagoDTO getConceptosPago(String idPago, String idFacturacion, HttpServletRequest request) {

        LOGGER.info(
                "PagoSJCSServiceImpl.getConceptosPago() -> Entrada al servicio para obtener los conceptos del pago: "
                        + idPago);

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        ConceptoPagoDTO conceptoPagoDTO = new ConceptoPagoDTO();
        Error error = new Error();
        List<ConceptoPagoItem> listaConceptosPago = new ArrayList<ConceptoPagoItem>();

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "PagoSJCSServiceImpl.getConceptosPago() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "PagoSJCSServiceImpl.getConceptosPago() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    LOGGER.info(
                            "PagoSJCSServiceImpl.getConceptosPago() ->fcsPagosjgExtendsMapper.getConceptosPago() -> Entrada para la obtención de los conceptos del pago: "
                                    + idPago);
                    listaConceptosPago = fcsPagosjgExtendsMapper.getConceptosPago(idPago, idInstitucion,
                            usuarios.get(0).getIdlenguaje());
                    LOGGER.info(
                            "PagoSJCSServiceImpl.getConceptosPago() ->fcsPagosjgExtendsMapper.getConceptosPago() -> Salida de la obtención de los conceptos del pago: "
                                    + idPago);

                    listaConceptosPago = listaConceptosPago.stream()
                            .collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(ConceptoPagoItem::getIdConcepto))),
                                    ArrayList::new));

                    conceptoPagoDTO.setListaConceptos(listaConceptosPago);

                }

            }

        } catch (Exception e) {
            LOGGER.error(
                    "PagoSJCSServiceImpl.getConceptosPago() -> Se ha producido un error en la obtención de los conceptos del pago: "
                            + idPago,
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        conceptoPagoDTO.setError(error);

        LOGGER.info(
                "PagoSJCSServiceImpl.getConceptosPago() -> Salida del servicio para obtener los conceptos del pago: "
                        + idPago);

        return conceptoPagoDTO;
    }

    @Override
    public UpdateResponseDTO saveConceptoPago(List<ConceptoPagoItem> listaConceptoPagoItem,
                                              HttpServletRequest request) {

        LOGGER.info(
                "PagoSJCSServiceImpl.saveConceptoPago() -> Entrada al servicio para actualizar conceptos asociados al pago");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
        Error error = new Error();
        List<Integer> responses = null;

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "PagoSJCSServiceImpl.saveConceptoPago() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "PagoSJCSServiceImpl.saveConceptoPago() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    if (null != listaConceptoPagoItem && !listaConceptoPagoItem.isEmpty()) {

                        responses = new ArrayList<Integer>();

                        // Obtenemos los datos del pago sobre el que actuaremos
                        FcsPagosjgKey fcsPagosjgKey = new FcsPagosjgKey();
                        fcsPagosjgKey.setIdinstitucion(idInstitucion);
                        fcsPagosjgKey.setIdpagosjg(Integer.valueOf(listaConceptoPagoItem.get(0).getIdPagosjg()));

                        LOGGER.info(
                                "PagoSJCSServiceImpl.saveConceptoPago() -> fcsPagosjgExtendsMapper.selectByPrimaryKey() -> Obtenemos el pago");
                        FcsPagosjg pago = fcsPagosjgExtendsMapper.selectByPrimaryKey(fcsPagosjgKey);

                        // Dependiendo de el tipo de concepto actualizaremos el importe correspondiente
                        // y el importe pagado
                        for (ConceptoPagoItem concepto : listaConceptoPagoItem) {

                            if (null != concepto.getCantidadApagar()) {

                                if (concepto.getCantidadApagar() == 0 && !hayMovimientosVariosPositivosAaplicar(idInstitucion, concepto.getIdFacturacion())) {
                                    error.setCode(400);
                                    error.setDescription("facturacionSJCS.facturacionesYPagos.errorImporteCriterio");
                                    throw new Exception("No se puede introducir un importe igual a 0 si no hay movimientos varios positivos a aplicar");
                                }

                                switch (concepto.getIdConcepto()) {
                                    case "10":
                                        pago.setImporteoficio(BigDecimal.valueOf(
                                                pago.getImporteoficio().doubleValue() + concepto.getCantidadApagar()));
                                        pago.setImportepagado(BigDecimal.valueOf(pago.getImportepagado().doubleValue()
                                                + pago.getImporteoficio().doubleValue()));
                                        break;
                                    case "20":
                                        pago.setImporteguardia(BigDecimal.valueOf(
                                                pago.getImporteguardia().doubleValue() + concepto.getCantidadApagar()));
                                        pago.setImportepagado(BigDecimal.valueOf(pago.getImportepagado().doubleValue()
                                                + pago.getImporteguardia().doubleValue()));
                                        break;
                                    case "30":
                                        pago.setImportesoj(BigDecimal.valueOf(
                                                pago.getImportesoj().doubleValue() + concepto.getCantidadApagar()));
                                        pago.setImportepagado(BigDecimal.valueOf(pago.getImportepagado().doubleValue()
                                                + pago.getImportesoj().doubleValue()));
                                        break;
                                    case "40":
                                        pago.setImporteejg(BigDecimal.valueOf(
                                                pago.getImporteejg().doubleValue() + concepto.getCantidadApagar()));
                                        pago.setImportepagado(BigDecimal.valueOf(pago.getImportepagado().doubleValue()
                                                + pago.getImporteejg().doubleValue()));
                                        break;
                                }

                                LOGGER.info("PagoSJCSServiceImpl.saveConceptoPago() -> fcsFactGrupofactHitoExtendsMapper.selectByExample() -> Buscamos los conceptos de facturación con IDHITOGENERAL = " + concepto.getIdConcepto());
                                FcsFactGrupofactHitoExample fcsFactGrupofactHitoExample = new FcsFactGrupofactHitoExample();
                                fcsFactGrupofactHitoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
                                        .andIdfacturacionEqualTo(Integer.valueOf(concepto.getIdFacturacion()))
                                        .andIdhitogeneralEqualTo(Short.valueOf(concepto.getIdConcepto()));

                                List<FcsFactGrupofactHito> listaFcsFactGrupofactHito = fcsFactGrupofactHitoExtendsMapper
                                        .selectByExample(fcsFactGrupofactHitoExample);

                                // Recorremos los conceptos de la facturación con el idHitogeneral buscado y por cada línea insertamos una en los conceptos del pago
                                for (FcsFactGrupofactHito fcsFactGrupofactHito : listaFcsFactGrupofactHito) {

                                    FcsPagoGrupofactHito fcsPagoGrupofactHito = new FcsPagoGrupofactHito();
                                    fcsPagoGrupofactHito.setIdinstitucion(idInstitucion);
                                    fcsPagoGrupofactHito.setIdpagosjg(fcsPagosjgKey.getIdpagosjg());
                                    fcsPagoGrupofactHito.setIdhitogeneral(Short.valueOf(concepto.getIdConcepto()));
                                    fcsPagoGrupofactHito
                                            .setIdgrupofacturacion(fcsFactGrupofactHito.getIdgrupofacturacion());
                                    fcsPagoGrupofactHito.setFechamodificacion(new Date());
                                    fcsPagoGrupofactHito.setUsumodificacion(usuarios.get(0).getIdusuario());

                                    LOGGER.info("PagoSJCSServiceImpl.saveConceptoPago() -> fcsPagoGrupofactHitoMapper.insertSelective() -> Insertamos el concepto del pago");
                                    int response = fcsPagoGrupofactHitoMapper.insertSelective(fcsPagoGrupofactHito);
                                    responses.add(response);
                                }

                            }

                        }

                        LOGGER.info(
                                "PagoSJCSServiceImpl.saveConceptoPago() -> fcsPagosjgExtendsMapper.updateByPrimaryKeySelective() -> Actualizamos los datos del pago");
                        pago.setImporterepartir(pago.getImportepagado());
                        pago.setFechamodificacion(new Date());
                        pago.setUsumodificacion(usuarios.get(0).getIdusuario());
                        fcsPagosjgExtendsMapper.updateByPrimaryKeySelective(pago);

                    }

                }

            }

        } catch (Exception e) {
            LOGGER.error(
                    "PagoSJCSServiceImpl.saveConceptoPago() -> Se ha producido un error al introducir los conceptos del pago",
                    e);
            if (null == error.getCode() || error.getCode() != 400) {
                error.setCode(500);
                error.setDescription("general.mensaje.error.bbdd");
            }
        }

        if (null != responses && responses.contains(0)) {
            LOGGER.error(
                    "PagoSJCSServiceImpl.saveConceptoPago() -> Se ha producido un error al introducir los conceptos del pago");
            error.setCode(400);
            error.setDescription("general.message.error.realiza.accion");
            updateResponseDTO.setStatus(SigaConstants.KO);
        } else {
            updateResponseDTO.setStatus(SigaConstants.OK);
        }

        updateResponseDTO.setError(error);

        LOGGER.info(
                "PagoSJCSServiceImpl.saveConceptoPago() -> Salida del servicio para actualizar conceptos asociados al pago");

        return updateResponseDTO;
    }

    private boolean hayMovimientosVariosPositivosAaplicar(Short idInstitucion, String idFacturacion) {
        int numero = fcsPagosjgExtendsMapper.hayMovimientosVariosPositivosAaplicar(idInstitucion, idFacturacion);
        return (numero > 0);
    }

    @Override
    public DeleteResponseDTO deleteConceptoPago(List<ConceptoPagoItem> listaConceptoPagoItem,
                                                HttpServletRequest request) {

        LOGGER.info(
                "PagoSJCSServiceImpl.deleteConceptoPago() -> Entrada al servicio para desasociar cenceptos del pago");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
        Error error = new Error();
        List<Integer> responses = null;

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "PagoSJCSServiceImpl.deleteConceptoPago() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "PagoSJCSServiceImpl.deleteConceptoPago() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    if (null != listaConceptoPagoItem && !listaConceptoPagoItem.isEmpty()) {

                        responses = new ArrayList<Integer>();

                        // Obtenemos el pago
                        FcsPagosjgKey fcsPagosjgKey = new FcsPagosjgKey();
                        fcsPagosjgKey.setIdinstitucion(idInstitucion);
                        fcsPagosjgKey.setIdpagosjg(Integer.valueOf(listaConceptoPagoItem.get(0).getIdPagosjg()));

                        LOGGER.info(
                                "PagoSJCSServiceImpl.deleteConceptoPago() -> fcsPagosjgExtendsMapper.selectByPrimaryKey() -> Obtenemos el pago");
                        FcsPagosjg pago = fcsPagosjgExtendsMapper.selectByPrimaryKey(fcsPagosjgKey);

                        // Dependiendo del concepto de pago, restaremos la cantidad al importe pagado y
                        // además actualizaremos a 0 el campo correspondiente
                        for (ConceptoPagoItem concepto : listaConceptoPagoItem) {

                            switch (concepto.getIdConcepto()) {
                                case "10":
                                    pago.setImportepagado(BigDecimal.valueOf(
                                            pago.getImportepagado().doubleValue() - pago.getImporteoficio().doubleValue()));
                                    pago.setImporteoficio(BigDecimal.ZERO);
                                    break;
                                case "20":
                                    pago.setImportepagado(BigDecimal.valueOf(pago.getImportepagado().doubleValue()
                                            - pago.getImporteguardia().doubleValue()));
                                    pago.setImporteguardia(BigDecimal.ZERO);
                                    break;
                                case "30":
                                    pago.setImportepagado(BigDecimal.valueOf(
                                            pago.getImportepagado().doubleValue() - pago.getImportesoj().doubleValue()));
                                    pago.setImportesoj(BigDecimal.ZERO);
                                    break;
                                case "40":
                                    pago.setImportepagado(BigDecimal.valueOf(
                                            pago.getImportepagado().doubleValue() - pago.getImporteejg().doubleValue()));
                                    pago.setImporteejg(BigDecimal.ZERO);
                                    break;
                            }

                            // Eliminamos el concepto del pago
                            FcsPagoGrupofactHitoExample fcsPagoGrupofactHitoExample = new FcsPagoGrupofactHitoExample();
                            fcsPagoGrupofactHitoExample.createCriteria().andIdinstitucionEqualTo(idInstitucion)
                                    .andIdhitogeneralEqualTo(Short.valueOf(concepto.getIdConcepto()))
                                    .andIdpagosjgEqualTo(Integer.valueOf(concepto.getIdPagosjg()));

                            LOGGER.info(
                                    "PagoSJCSServiceImpl.deleteConceptoPago() -> fcsPagoGrupofactHitoMapper.deleteByExample() -> Eliminamos el concepto del pago");
                            int response = fcsPagoGrupofactHitoMapper.deleteByExample(fcsPagoGrupofactHitoExample);
                            responses.add(response);

                        }

                        LOGGER.info(
                                "PagoSJCSServiceImpl.deleteConceptoPago() -> fcsPagosjgExtendsMapper.updateByPrimaryKeySelective() -> Actualizamos los datos del pago");
                        pago.setImporterepartir(pago.getImportepagado());
                        pago.setFechamodificacion(new Date());
                        pago.setUsumodificacion(usuarios.get(0).getIdusuario());
                        fcsPagosjgExtendsMapper.updateByPrimaryKeySelective(pago);

                    }

                }

            }

        } catch (Exception e) {
            LOGGER.error(
                    "PagoSJCSServiceImpl.deleteConceptoPago() -> Se ha producido un error al intentar desasociar cenceptos del pago",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        if (null != responses && responses.contains(0)) {
            LOGGER.error(
                    "PagoSJCSServiceImpl.deleteConceptoPago() -> Se ha producido un error al intentar desasociar cenceptos del pago");
            error.setCode(400);
            error.setDescription("general.message.error.realiza.accion");
            deleteResponseDTO.setStatus(SigaConstants.KO);
        } else {
            deleteResponseDTO.setStatus(SigaConstants.OK);
        }

        deleteResponseDTO.setError(error);

        LOGGER.info(
                "PagoSJCSServiceImpl.deleteConceptoPago() -> Salida del servicio para desasociar cenceptos del pago");

        return deleteResponseDTO;
    }

    @Override
    public ComboDTO comboPropTranferenciaSepa(HttpServletRequest request) {

        LOGGER.info(
                "PagoSJCSServiceImpl.comboPropTranferenciaSepa() -> Entrada al servicio para obtener el combo de propósitos para transferencias SEPA");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        ComboDTO comboDTO = new ComboDTO();
        Error error = new Error();

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "PagoSJCSServiceImpl.comboPropTranferenciaSepa() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "PagoSJCSServiceImpl.comboPropTranferenciaSepa() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    LOGGER.info(
                            "PagoSJCSServiceImpl.comboPropTranferenciaSepa() -> facPropositosExtendsMapper.comboPropTranferencia() -> LLamada al servicio para obtener el combo");
                    List<ComboItem> combo = facPropositosExtendsMapper.
                            comboPropTranferencia(usuarios.get(0).getIdlenguaje(), false);
                    LOGGER.info(
                            "PagoSJCSServiceImpl.comboPropTranferenciaSepa() -> facPropositosExtendsMapper.comboPropTranferencia() -> Salida del servicio con el combo");
                    comboDTO.setCombooItems(combo);

                }

            }

        } catch (Exception e) {
            LOGGER.error(
                    "PagoSJCSServiceImpl.comboPropTranferenciaSepa() -> Se ha producido un error al intentar obtener el combo de propósitos para transferencias SEPA",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        comboDTO.setError(error);

        LOGGER.info(
                "PagoSJCSServiceImpl.comboPropTranferenciaSepa() -> Salida del servicio para obtener el combo de propósitos para transferencias SEPA");

        return comboDTO;
    }

    @Override
    public ComboDTO comboPropOtrasTranferencias(HttpServletRequest request) {

        LOGGER.info(
                "PagoSJCSServiceImpl.comboPropOtrasTranferencias() -> Entrada al servicio para obtener el combo de propósitos para otras transferencias");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        ComboDTO comboDTO = new ComboDTO();
        Error error = new Error();

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "PagoSJCSServiceImpl.comboPropOtrasTranferencias() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "PagoSJCSServiceImpl.comboPropOtrasTranferencias() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    LOGGER.info(
                            "PagoSJCSServiceImpl.comboPropOtrasTranferencias() -> facPropositosExtendsMapper.comboPropTranferencia() -> LLamada al servicio para obtener el combo");
                    List<ComboItem> combo = facPropositosExtendsMapper.
                            comboPropTranferencia(usuarios.get(0).getIdlenguaje(), true);
                    LOGGER.info(
                            "PagoSJCSServiceImpl.comboPropOtrasTranferencias() -> facPropositosExtendsMapper.comboPropTranferencia() -> Salida del servicio con el combo");
                    comboDTO.setCombooItems(combo);

                }

            }

        } catch (Exception e) {
            LOGGER.error(
                    "PagoSJCSServiceImpl.comboPropOtrasTranferencias() -> Se ha producido un error al intentar obtener el combo de propósitos para otras transferencias",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        comboDTO.setError(error);

        LOGGER.info(
                "PagoSJCSServiceImpl.comboPropOtrasTranferencias() -> Salida del servicio para obtener el combo de propósitos para otras transferencias");

        return comboDTO;
    }

    @Override
    public ComboDTO comboSufijos(HttpServletRequest request) {

        LOGGER.info(
                "PagoSJCSServiceImpl.comboSufijos() -> Entrada al servicio para obtener el combo de sufijos que se pueden aplicar a una cuenta bancaria");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        ComboDTO comboDTO = new ComboDTO();
        Error error = new Error();

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "PagoSJCSServiceImpl.comboSufijos() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "PagoSJCSServiceImpl.comboSufijos() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    LOGGER.info(
                            "PagoSJCSServiceImpl.comboSufijos() -> facSufijoExtendsMapper.comboSufijos() -> Entrada al servicio para obtener el combo");
                    List<ComboItem> comboSufijos = facSufijoExtendsMapper.comboSufijos(idInstitucion);
                    LOGGER.info(
                            "PagoSJCSServiceImpl.comboSufijos() -> facSufijoExtendsMapper.comboSufijos() -> Salida del servicio con el combo obtenido");
                    comboDTO.setCombooItems(comboSufijos);

                }

            }

        } catch (Exception e) {
            LOGGER.error(
                    "PagoSJCSServiceImpl.comboSufijos() -> Se ha producido un error al intentar obtener el combo de sufijos que se pueden aplicar a una cuenta bancaria",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        comboDTO.setError(error);

        LOGGER.info(
                "PagoSJCSServiceImpl.comboSufijos() -> Salida del servicio para obtener el combo de sufijos que se pueden aplicar a una cuenta bancaria");

        return comboDTO;
    }

    @Override
    public ComboDTO comboCuentasBanc(HttpServletRequest request) {

        LOGGER.info(
                "PagoSJCSServiceImpl.comboCuentasBanc() -> Entrada al servicio para obtener el combo de cuentas bancarias");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        ComboDTO comboDTO = new ComboDTO();
        Error error = new Error();

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "PagoSJCSServiceImpl.comboCuentasBanc() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "PagoSJCSServiceImpl.comboCuentasBanc() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    LOGGER.info(
                            "PagoSJCSServiceImpl.comboCuentasBanc() -> facBancoinstitucionExtendsMapper.comboCuentasBanc() -> Entrada al servicio para obtener el combo");
                    List<ComboItem> comboCuentasBancarias = facBancoinstitucionExtendsMapper.comboCuentasBanc(idInstitucion);
                    LOGGER.info(
                            "PagoSJCSServiceImpl.comboCuentasBanc() -> facBancoinstitucionExtendsMapper.comboCuentasBanc() -> Salida del servicio con el combo obtenido");
                    comboDTO.setCombooItems(comboCuentasBancarias);

                }

            }

        } catch (Exception e) {
            LOGGER.error(
                    "PagoSJCSServiceImpl.comboCuentasBanc() -> Se ha producido un error al intentar obtener el combo de cuentas bancarias",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        comboDTO.setError(error);

        LOGGER.info(
                "PagoSJCSServiceImpl.comboCuentasBanc() -> Salida del servicio para obtener el combo de cuentas bancarias");

        return comboDTO;
    }

    @Override
    public UpdateResponseDTO saveConfigFichAbonos(PagosjgItem pagosjgItem, HttpServletRequest request) {

        LOGGER.info(
                "PagoSJCSServiceImpl.saveConfigFichAbonos() -> Entrada al servicio para guardar la configuración de ficheros de abonos");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
        Error error = new Error();
        int response = 0;

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "PagoSJCSServiceImpl.saveConfigFichAbonos() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "PagoSJCSServiceImpl.saveConfigFichAbonos() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    FcsPagosjg record = new FcsPagosjg();
                    record.setIdinstitucion(idInstitucion);
                    record.setIdpagosjg(Integer.valueOf(pagosjgItem.getIdPagosjg()));
                    record.setFechamodificacion(new Date());
                    record.setUsumodificacion(usuarios.get(0).getIdusuario());
                    record.setBancosCodigo(pagosjgItem.getCodBanco());
                    record.setIdsufijo(Short.valueOf(pagosjgItem.getIdSufijo()));
                    record.setIdpropsepa(Short.valueOf(pagosjgItem.getIdPropSepa()));
                    record.setIdpropotros(Short.valueOf(pagosjgItem.getIdPropOtros()));

                    LOGGER.info(
                            "PagoSJCSServiceImpl.saveConfigFichAbonos() -> fcsPagosjgExtendsMapper.updateByPrimaryKeySelective() -> Actualizamos los datos en el pago");
                    response = fcsPagosjgExtendsMapper.updateByPrimaryKeySelective(record);
                    LOGGER.info(
                            "PagoSJCSServiceImpl.saveConfigFichAbonos() -> fcsPagosjgExtendsMapper.updateByPrimaryKeySelective() -> Pago actualizado: " + response);
                }

            }

            if (response == 0) {
                updateResponseDTO.setStatus(SigaConstants.KO);
                error.setCode(500);
                error.setDescription("general.message.error.realiza.accion");

            } else {
                updateResponseDTO.setStatus(SigaConstants.OK);
                updateResponseDTO.setId(pagosjgItem.getIdPagosjg());
            }

        } catch (Exception e) {
            LOGGER.error(
                    "PagoSJCSServiceImpl.saveConfigFichAbonos() -> Se ha producido un error al intentar guardar la configuración de ficheros de abonos",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        updateResponseDTO.setError(error);

        LOGGER.info(
                "PagoSJCSServiceImpl.saveConfigFichAbonos() -> Salida del servicio para guardar la configuración de ficheros de abonos");

        return updateResponseDTO;
    }

    @Override
    public PagosjgDTO getConfigFichAbonos(String idPago, HttpServletRequest request) {

        LOGGER.info(
                "PagoSJCSServiceImpl.getConfigFichAbonos() -> Entrada al servicio para obtener la configuración de ficheros de abonos");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        PagosjgDTO pagosjgDTO = new PagosjgDTO();
        Error error = new Error();

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "PagoSJCSServiceImpl.getConfigFichAbonos() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "PagoSJCSServiceImpl.getConfigFichAbonos() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    LOGGER.info(
                            "PagoSJCSServiceImpl.getConfigFichAbonos() -> fcsPagosjgExtendsMapper.getConfigFichAbonos() -> Entrada a la obtención de los datos");
                    List<PagosjgItem> datosConfigFichAbonos = fcsPagosjgExtendsMapper.getConfigFichAbonos(idPago, idInstitucion);
                    LOGGER.info(
                            "PagoSJCSServiceImpl.getConfigFichAbonos() -> fcsPagosjgExtendsMapper.getConfigFichAbonos() -> Salida con los datos obtenidos");
                    pagosjgDTO.setPagosjgItem(datosConfigFichAbonos);

                }

            }

        } catch (Exception e) {
            LOGGER.error(
                    "PagoSJCSServiceImpl.getConfigFichAbonos() -> Se ha producido un error al intentar obtener la configuración de ficheros de abonos",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        pagosjgDTO.setError(error);

        LOGGER.info(
                "PagoSJCSServiceImpl.getConfigFichAbonos() -> Salida del servicio para obtener la configuración de ficheros de abonos");

        return pagosjgDTO;
    }

    @Override
    public StringDTO getNumApuntesPago(String idPago, HttpServletRequest request) {

        LOGGER.info(
                "PagoSJCSServiceImpl.getNumApuntesPago() -> Entrada al servicio para obtener el número de apuntes del pago: " + idPago);

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        StringDTO stringDTO = new StringDTO();

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "PagoSJCSServiceImpl.getNumApuntesPago() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "PagoSJCSServiceImpl.getNumApuntesPago() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    LOGGER.info(
                            "PagoSJCSServiceImpl.getNumApuntesPago() -> fcsPagosjgExtendsMapper.getNumApuntesPago() -> Entrada para obtener el número de apuntes");
                    stringDTO = fcsPagosjgExtendsMapper.getNumApuntesPago(idPago, idInstitucion, usuarios.get(0).getIdlenguaje());
                    LOGGER.info(
                            "PagoSJCSServiceImpl.getNumApuntesPago() -> fcsPagosjgExtendsMapper.getNumApuntesPago() -> Salida, número de apuntes: " + stringDTO.getValor());

                }

            }

        } catch (Exception e) {
            LOGGER.error(
                    "PagoSJCSServiceImpl.getNumApuntesPago() -> Se ha producido un error al intentar obtener el número de apuntes del pago:" + idPago,
                    e);
        }

        LOGGER.info(
                "PagoSJCSServiceImpl.getNumApuntesPago() -> Salida del servicio para obtener el número de apuntes del pago: " + idPago);

        return stringDTO;
    }

    @Override
    public CompensacionFacDTO getCompensacionFacturas(String idPago, HttpServletRequest request) {

        LOGGER.info(
                "PagoSJCSServiceImpl.getCompensacionFacturas() -> Entrada al servicio para obtener todas las facturas pendientes de colegiados que estén en el pago para compensar");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        CompensacionFacDTO compensacionFacDTO = new CompensacionFacDTO();
        Error error = new Error();

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "PagoSJCSServiceImpl.getCompensacionFacturas() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "PagoSJCSServiceImpl.getCompensacionFacturas() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    LOGGER.info(
                            "PagoSJCSServiceImpl.getCompensacionFacturas() -> fcsPagosjgExtendsMapper.getCompensacionFacturas() -> Entrada al servicio para obtener las compensaciones del pago: " + idPago);
                    List<CompensacionFacItem> compensaciones = fcsPagosjgExtendsMapper.getCompensacionFacturas(idPago, idInstitucion);
                    LOGGER.info(
                            "PagoSJCSServiceImpl.getCompensacionFacturas() -> fcsPagosjgExtendsMapper.getCompensacionFacturas() -> Salida del servicio para obtener las compensaciones del pago: " + idPago);
                    compensacionFacDTO.setCompensaciones(compensaciones);

                }

            }

        } catch (Exception e) {
            LOGGER.error(
                    "PagoSJCSServiceImpl.getCompensacionFacturas() -> Se ha producido un error al intentar obtener todas las facturas pendientes de colegiados que estén en el pago para compensar",
                    e);
            error.setCode(500);
            error.setDescription("general.mensaje.error.bbdd");
        }

        compensacionFacDTO.setError(error);

        LOGGER.info(
                "PagoSJCSServiceImpl.getCompensacionFacturas() -> Salida del servicio");

        return compensacionFacDTO;
    }

    @Override
    public InsertResponseDTO ejecutarPagoSJCS(String idPago, boolean simular, HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

        InsertResponseDTO insertResponseDTO = new InsertResponseDTO();
        Error error = new Error();
        List<AdmUsuarios> usuarios = null;
        FcsPagosjg pago = null;
        boolean ponerEnEstadoAbierto = true;

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "PagoSJCSServiceImpl.ejecutarPagoSJCS() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "PagoSJCSServiceImpl.ejecutarPagoSJCS() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {
                    if (FacturacionSJCSServicesImpl.isAlguienEjecutando()) {
                        LOGGER.info(
                                "YA SE ESTA EJECUTANDO LA FACTURACIÓN SJCS EN BACKGROUND. CUANDO TERMINE SE INICIARA OTRA VEZ EL PROCESO DE DESBLOQUEO.");
                        insertResponseDTO.setStatus(SigaConstants.KO);
                        error.setCode(400);
                        error.setDescription("facturacionSJCS.facturacionPagos.pagos.errorDeshacerCierreProceso");
                    }else{
                        FacturacionSJCSServicesImpl.setAlguienEjecutando();
                        try{
                        AdmUsuarios usuario = usuarios.get(0);

                        // Antes de ejecutar el pago comprobamos si tiene banco asociado
                        FcsPagosjgKey fcsPagosjgKey = new FcsPagosjgKey();
                        fcsPagosjgKey.setIdinstitucion(idInstitucion);
                        fcsPagosjgKey.setIdpagosjg(Integer.valueOf(idPago));

                        LOGGER.info(
                                "UtilidadesPagoSJCS.ejecutarPagoSJCS() -> fcsPagosjgMapper.selectByPrimaryKey() -> Entrada para obtener los datos del pago: "
                                        + idPago);
                        pago = fcsPagosjgExtendsMapper.selectByPrimaryKey(fcsPagosjgKey);
                        LOGGER.info(
                                "UtilidadesPagoSJCS.ejecutarPagoSJCS() -> fcsPagosjgMapper.selectByPrimaryKey() -> Salida de obtener los datos del pago: "
                                        + idPago);

                        if (UtilidadesString.esCadenaVacia(pago.getBancosCodigo())) {
                            throw new FacturacionSJCSException("Debe de seleccionar una cuenta bancaria", "factSJCS.abonos.configuracion.literal.cuentaObligatoria");
                        }

                        String estadoPago = fcsPagosjgExtendsMapper.getEstadoPago(idPago, idInstitucion);
                        String criterioTurno = pago.getCriteriopagoturno();

                        // Validacion de los datos antes de ejecutar el pago:
                        // 1. El estado del pago debe ser abierto:
                        if (!estadoPago.equals(SigaConstants.ESTADO_PAGO_ABIERTO)) {
                            ponerEnEstadoAbierto = false;
                            LOGGER.error("El pago no se encuentra en un estado correcto para realizar esta operación");
                            throw new FacturacionSJCSException("El pago no se encuentra en un estado correcto para realizar esta operación", "messages.factSJCS.error.estadoPagoNoCorrecto");
                        }
                        // 2. Criterios correctos del Turno:
                        if (!criterioTurno.equals(SigaConstants.CRITERIOS_PAGO_FACTURACION)) {
                            LOGGER.error("No ha sido configurado el criterio de pago.");
                            throw new FacturacionSJCSException("No ha sido configurado el criterio de pago.", "messages.factSJCS.error.criterioPago");
                        }

                        //3. Si no se ha introducido importe a pagar el importe a facturar será cero
                        if (pago.getImporterepartir().doubleValue() == 0.00) {
                            LOGGER.error("El importe a facturar será cero, introduzca importe a pagar distinto de cero.");
                            throw new FacturacionSJCSException("El importe a facturar será cero, introduzca importe a pagar distinto de cero.", "messages.facturacionSJCS.abono.sin.importe.pago");
                        }

                        utilidadesPagoSJCS.ejecutarPagoSJCS(pago, simular, idInstitucion, usuario);
                        }catch (Exception e){
                            LOGGER.error(e.getMessage());
                            LOGGER.error(e.getStackTrace());
                            throw e;
                        }finally {
                            FacturacionSJCSServicesImpl.setNadieEjecutando();
                        }
                    }
                }

            }

        } catch (FacturacionSJCSException fe) {
            LOGGER.error(fe.getMessage());
            LOGGER.error(fe.getStackTrace());
            if (ponerEnEstadoAbierto) {
                ponerPagoEstadoAbierto(pago, idInstitucion, usuarios.get(0));
            }
            error.setDescription(fe.getDescription());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            LOGGER.error(e.getStackTrace());
            if (simular && e.getMessage().equals("messages.facturacionSJCS.pago.simulado")) {
                error.setDescription(null);
            } else {
                LOGGER.error(
                        "PagoSJCSServiceImpl.ejecutarPagoSJCS() -> Se ha producido en la ejecución del pago: " + idPago, e);
                error.setCode(500);
                error.setDescription("general.mensaje.error.bbdd");
            }
        }

        insertResponseDTO.setError(error);

        if (error.getDescription() == null) {
            insertResponseDTO.setStatus(SigaConstants.OK);
        } else {
            insertResponseDTO.setStatus(SigaConstants.KO);
        }

        return insertResponseDTO;
    }

    private void ponerPagoEstadoAbierto(FcsPagosjg pago, Short idInstitucion, AdmUsuarios usuario) {

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

        List<FcsPagoColegiado> colegiados;

        try {

            colegiados = fcsPagosjgExtendsMapper.getColegiadosApagar(idInstitucion, idPago, caseMorosos);

        } catch (Exception e) {
            throw new FacturacionSJCSException("Error al obtener los colegiados a pagar", e, "messages.factSJCS.error.colegiadosApagar");
        }

        return colegiados;
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

    /**
     * Método que implementa la accion cerrarPago. Modifica el estado del pago a
     * cerrado.
     */
    @Override
    public UpdateResponseDTO cerrarPago(String idPago, HttpServletRequest request) {

        LOGGER.info("PagoSJCSServiceImpl.cerrarPago() -> Entrada al servicio para el cierre de un pago");

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        Error error = new Error();
        UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
        FcsPagosjg pago = null;

        try {

            updateResponseDTO.setStatus(SigaConstants.OK);

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "PagoSJCSServiceImpl.cerrarPago() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "PagoSJCSServiceImpl.cerrarPago() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    FcsPagosjgKey fcsPagosjgKey = new FcsPagosjgKey();
                    fcsPagosjgKey.setIdinstitucion(idInstitucion);
                    fcsPagosjgKey.setIdpagosjg(Integer.valueOf(idPago));

                    LOGGER.info(
                            "PagoSJCSServiceImpl.cerrarPago() -> fcsPagosjgMapper.selectByPrimaryKey() -> Entrada para obtener los datos del pago: "
                                    + idPago);
                    pago = fcsPagosjgExtendsMapper.selectByPrimaryKey(fcsPagosjgKey);
                    LOGGER.info(
                            "PagoSJCSServiceImpl.cerrarPago() -> fcsPagosjgMapper.selectByPrimaryKey() -> Salida de obtener los datos del pago: "
                                    + idPago);

                    FcsPagosEstadospagos record = new FcsPagosEstadospagos();
                    record.setIdinstitucion(idInstitucion);
                    record.setIdpagosjg(pago.getIdpagosjg());
                    record.setIdestadopagosjg(Short.valueOf(SigaConstants.ESTADO_PAGO_CERRADO));
                    record.setFechaestado(new Date());
                    record.setFechamodificacion(new Date());
                    record.setUsumodificacion(usuarios.get(0).getIdusuario());

                    LOGGER.info(
                            "PagoSJCSServiceImpl.cerrarPago() -> fcsPagosEstadospagosMapper.insertSelective() -> Insertamo el estado CERRADO para el pago: " + idPago);
                    // Insertamos el estado del pago
                    fcsPagosEstadospagosMapper.insertSelective(record);

                    // Pasamos automaticamente a deducir los pagos
                    generarAbonos(idInstitucion, idPago, usuarios.get(0), null);

                    // Exportacion de datos a EXCEL
                    utilidadesFacturacionSJCS.exportarDatosPagos(idInstitucion, pago.getIdfacturacion(), pago.getIdpagosjg(), null, usuarios.get(0));

                }

            }

        } catch (Exception e) {
            LOGGER.error(
                    "PagoSJCSServiceImpl.cerrarPago() -> Se ha producido un error en el cierre del pago: " + idPago, e);
            error.setCode(500);
            error.setDescription("formacion.mensaje.general.mensaje.error");
            updateResponseDTO.setStatus(SigaConstants.KO);
            FcsPagosEstadospagosKey fcsPagosEstadospagosKey = new FcsPagosEstadospagosKey();
            fcsPagosEstadospagosKey.setIdestadopagosjg(Short.valueOf(SigaConstants.ESTADO_PAGO_CERRADO));
            fcsPagosEstadospagosKey.setIdinstitucion(idInstitucion);
            fcsPagosEstadospagosKey.setIdpagosjg(Integer.valueOf(idPago));
            fcsPagosEstadospagosMapper.deleteByPrimaryKey(fcsPagosEstadospagosKey);
        }

        updateResponseDTO.setError(error);
        updateResponseDTO.setId(idPago);

        LOGGER.info("PagoSJCSServiceImpl.cerrarPago() -> Salida del servicio para el cierre de un pago");

        return updateResponseDTO;
    }

    /**
     * Método que implementa la accion cerrarPagoManual. Modifica el estado del pago a cerrado
     *
     * @param idPago
     * @param idsParaEnviar Son los idPersona
     * @param request
     */
    @Override
    public UpdateResponseDTO cerrarPagoManual(String idPago, List<String> idsParaEnviar, HttpServletRequest request) {

        LOGGER.info("PagoSJCSServiceImpl.cerrarPagoManual() -> Entrada al servicio para cerrar manualmente el pago: " + idPago);

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        Error error = new Error();
        UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
        FcsPagosjg pago;

        try {

            updateResponseDTO.setStatus(SigaConstants.OK);

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info(
                        "PagoSJCSServiceImpl.cerrarPagoManual() -> admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info(
                        "PagoSJCSServiceImpl.cerrarPagoManual() -> admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {

                    FcsPagosjgKey fcsPagosjgKey = new FcsPagosjgKey();
                    fcsPagosjgKey.setIdinstitucion(idInstitucion);
                    fcsPagosjgKey.setIdpagosjg(Integer.valueOf(idPago));

                    LOGGER.info(
                            "PagoSJCSServiceImpl.cerrarPagoManual() -> fcsPagosjgMapper.selectByPrimaryKey() -> Entrada para obtener los datos del pago: "
                                    + idPago);
                    pago = fcsPagosjgExtendsMapper.selectByPrimaryKey(fcsPagosjgKey);
                    LOGGER.info(
                            "PagoSJCSServiceImpl.cerrarPagoManual() -> fcsPagosjgMapper.selectByPrimaryKey() -> Salida de obtener los datos del pago: "
                                    + idPago);

                    // Insertamos el estado del pago
                    FcsPagosEstadospagos record = new FcsPagosEstadospagos();
                    record.setIdinstitucion(idInstitucion);
                    record.setIdpagosjg(pago.getIdpagosjg());
                    record.setIdestadopagosjg(Short.valueOf(SigaConstants.ESTADO_PAGO_CERRADO));
                    record.setFechaestado(new Date());
                    record.setFechamodificacion(new Date());
                    record.setUsumodificacion(usuarios.get(0).getIdusuario());

                    LOGGER.info(
                            "PagoSJCSServiceImpl.cerrarPagoManual() -> fcsPagosEstadospagosMapper.insertSelective() -> Insertamo el estado CERRADO para el pago: " + idPago);
                    // Insertamos el estado del pago
                    fcsPagosEstadospagosMapper.insertSelective(record);

                    // ahora pasamos a generar abonos
                    List<ColegiadosPagoDTO> letradosAPagar = new ArrayList<>();

                    idsParaEnviar.forEach(id -> {
                        ColegiadosPagoDTO b = new ColegiadosPagoDTO();
                        b.setIdInstitucion(idInstitucion.toString());
                        b.setIdPersona(id);
                        b.setMarcado("1");
                        letradosAPagar.add(b);
                    });

                    generarAbonos(idInstitucion, idPago, usuarios.get(0), letradosAPagar);
                }

            }

        } catch (Exception e) {
            LOGGER.error(
                    "PagoSJCSServiceImpl.cerrarPagoManual() -> Se ha producido un error en el cierre manual del pago: " + idPago, e);
            error.setCode(500);
            error.setDescription("formacion.mensaje.general.mensaje.error");
            updateResponseDTO.setStatus(SigaConstants.KO);
            FcsPagosEstadospagosKey fcsPagosEstadospagosKey = new FcsPagosEstadospagosKey();
            fcsPagosEstadospagosKey.setIdestadopagosjg(Short.valueOf(SigaConstants.ESTADO_PAGO_CERRADO));
            fcsPagosEstadospagosKey.setIdinstitucion(idInstitucion);
            fcsPagosEstadospagosKey.setIdpagosjg(Integer.valueOf(idPago));
            fcsPagosEstadospagosMapper.deleteByPrimaryKey(fcsPagosEstadospagosKey);
        }

        updateResponseDTO.setError(error);
        updateResponseDTO.setId(idPago);

        LOGGER.info("PagoSJCSServiceImpl.cerrarPagoManual() -> Salida del servicio para cerrar manualmente el pago: " + idPago);

        return updateResponseDTO;
    }

    /**
     * Por cada colegiado aplicamos el proceso de generar Abonos 1. Obtener el
     * total SJCS 2. Aplicar los movimientos varios 3. Obtener importe bruto
     * como la suma de los movimientos varios y el total SJCS 4. Obtener el
     * importe neto aplicando el IRPF 5. Aplicar retenciones judiciales y no
     * judiciales - �Aplicar tramos LEC? 6. Generar abono
     */
    protected void generarAbonos(Short idInstitucion, String idPago, AdmUsuarios usuario, List<ColegiadosPagoDTO> colegiadosMarcados) throws Exception {

        // Recuperamos los colegiados a los que tenemos que pagar
        // aquellos incluidos en el pago o con movimientos varios pendientes
        List<FcsPagoColegiado> colegiados = getColegiadosApagar(idInstitucion, idPago, SigaConstants.LISTA_PAGO_TODOS, usuario);

        for (FcsPagoColegiado colegiado : colegiados) {
            // recupera el colegiado
            String idPersona = colegiado.getIdperorigen().toString();
            DetallePagoColegiadoDTO detallePagoColegiadoDTO = fcsPagosjgExtendsMapper.getDetallePagoColegiado(idInstitucion.toString(), idPago, idPersona, false, usuario.getIdlenguaje());

            double importeTurnos = detallePagoColegiadoDTO.getImporteTotalOficio().doubleValue();
            double importeGuardias = detallePagoColegiadoDTO.getImporteTotalAsistencia().doubleValue();
            double importeSoj = detallePagoColegiadoDTO.getImporteTotalSoj().doubleValue();
            double importeEjg = detallePagoColegiadoDTO.getImporteTotalEjg().doubleValue();
            double importeMovimientos = detallePagoColegiadoDTO.getImporteTotalMovimientos().doubleValue();
            double importeIrpfTotal = detallePagoColegiadoDTO.getTotalImporteIrpf().doubleValue();
            double importeRetenciones = detallePagoColegiadoDTO.getImporteTotalRetenciones().doubleValue();
            double totalFinal = detallePagoColegiadoDTO.getTotalImporteIrpf().doubleValue();

            String idPersonaDestino = detallePagoColegiadoDTO.getIdPerDestino().toString();
            String idCuenta = detallePagoColegiadoDTO.getIdCuenta() == null ? "" : detallePagoColegiadoDTO.getIdCuenta().toString();

            // 6. Generar abono si corresponde
            if (totalFinal < 0) {
                throw new FacturacionSJCSException("PagoSJCSServiceImpl.generarAbonos() Importe final de abono negativo: hay que revisar el proceso.");
            }

            // Guardamos los importes:
            Hashtable importes = new Hashtable();
            importes.put("importeTurnos", String.valueOf(importeTurnos));
            importes.put("importeGuardias", String.valueOf(importeGuardias));
            importes.put("importeSoj", String.valueOf(importeSoj));
            importes.put("importeEjg", String.valueOf(importeEjg));
            importes.put("importeMovimientos", String.valueOf(importeMovimientos));
            importes.put("importeRetenciones", String.valueOf(importeRetenciones));

            // Creamos el Abono:
            this.crearAbonos(idPersonaDestino, idCuenta, colegiadosMarcados, idPersonaDestino, idPago, idInstitucion.toString(), importes, importeIrpfTotal, idPersona, usuario);
        }

    }

    /**
     * Crea los abonos a partir de los importes e irpfs calculados.
     *
     * @param idPersona
     * @param idCuenta
     * @param colegiadosMarcados
     * @param idPersonaSoc
     * @param idPago
     * @param idInstitucion
     * @param importes
     * @param irpfTotal
     * @param idPersonaSJCS
     */
    private void crearAbonos(String idPersona, String idCuenta, List<ColegiadosPagoDTO> colegiadosMarcados, String idPersonaSoc, String idPago, String idInstitucion, Hashtable importes, double irpfTotal, String idPersonaSJCS, AdmUsuarios usuario) throws FacturacionSJCSException {

        String importeTurnos = "", importeGuardias = "", importeSoj = "", importeEjg = "", importeMovimientos = "", importeRetenciones = "", importeRetencionesPersona = "";
        String personaPago = "";
        String sociedadPago = "";

        try {

            // Recuperamos los importes:
            importeTurnos = (String) importes.get("importeTurnos");
            importeGuardias = (String) importes.get("importeGuardias");
            importeSoj = (String) importes.get("importeSoj");
            importeEjg = (String) importes.get("importeEjg");
            importeMovimientos = (String) importes.get("importeMovimientos");
            importeRetenciones = (String) importes.get("importeRetenciones");

            // Perparamos el motivo del pago
            GenRecursosKey genRecursosKey = new GenRecursosKey();
            genRecursosKey.setIdrecurso("factSJCS.abonos.literal.motivo");
            genRecursosKey.setIdlenguaje(usuario.getIdlenguaje());
            String motivoPago = genRecursosMapper.selectByPrimaryKey(genRecursosKey).getDescripcion();

            CenPersona sociedadBean = cenPersonaMapper.selectByPrimaryKey(Long.valueOf(idPersonaSoc));
            sociedadPago = sociedadBean.getNombre();

            CenPersona personaBean = cenPersonaMapper.selectByPrimaryKey(Long.valueOf(idPersonaSJCS));
            personaPago = personaBean.getNombre() + " " + personaBean.getApellidos1() + " " + personaBean.getApellidos2();

            if (idPersonaSJCS.equalsIgnoreCase(idPersonaSoc)) {
                // Si coinciden idPersonaSJCS e idPersonaSoc el pago es a una persona
                motivoPago += " " + personaPago;
            } else {
                // Si no coinciden el ser� a una sociedad a traves de un letrado
                genRecursosKey.setIdrecurso("factSJCS.abonos.literal.motivo.letrado");
                motivoPago += " " + sociedadPago + " " + genRecursosMapper.selectByPrimaryKey(genRecursosKey).getDescripcion() + " " + personaPago;
            }

            // preparamos el abono
            Hashtable hash = new Hashtable();
            String idAbono = facAbonoExtendsMapper.getNuevoID(idInstitucion).toString();
            AdmContadorKey admContadorKey = new AdmContadorKey();
            admContadorKey.setIdcontador(SigaConstants.CONTADOR_ABONOS_PAGOSJG);
            admContadorKey.setIdinstitucion(Short.valueOf(idInstitucion));
            AdmContador admContador = admContadorExtendsMapper.selectByPrimaryKey(admContadorKey);
            String numeroAbono = getNuevoContadorConPrefijoSufijo(admContador);

            FacAbono facAbono = new FacAbono();
            facAbono.setIdinstitucion(Short.valueOf(idInstitucion));
            facAbono.setIdabono(Long.valueOf(idAbono));
            facAbono.setNumeroabono(numeroAbono);
            facAbono.setMotivos(motivoPago);
            facAbono.setFecha(new Date());
            facAbono.setFechamodificacion(new Date());
            facAbono.setContabilizada(SigaConstants.FACTURA_ABONO_NO_CONTABILIZADA);
            facAbono.setIdpersona(Long.valueOf(idPersona));
            facAbono.setIdcuenta(Short.valueOf(idCuenta));
            facAbono.setIdpagosjg(Integer.valueOf(idPago));
            facAbono.setIdperorigen(Long.valueOf(idPersonaSJCS));

            // Recuperamos el nombre del pago si esta disponible y lo metemos en
            // las observaciones
            FcsPagosjgKey fcsPagosjgKey = new FcsPagosjgKey();
            fcsPagosjgKey.setIdpagosjg(Integer.valueOf(idPago));
            fcsPagosjgKey.setIdinstitucion(Short.valueOf(idInstitucion));

            FcsPagosjg pagos = fcsPagosjgExtendsMapper.selectByPrimaryKey(fcsPagosjgKey);

            if (null != pagos) {
                facAbono.setObservaciones(pagos.getNombre());
            }

            // preparamos las listas de abono
            String idLineaAbono = facLineaabonoExtendsMapper.getNuevoID(idInstitucion, idAbono).toString();
            int idLinea = Integer.parseInt(idLineaAbono);

            // campos comunes
            FacLineaabono htLista = new FacLineaabono();
            htLista.setIdinstitucion(Short.valueOf(idInstitucion));
            htLista.setIdabono(Long.valueOf(idAbono));
            htLista.setIva(BigDecimal.ZERO);
            htLista.setCantidad(1);

            // insertamos el abono
            facAbonoExtendsMapper.insertSelective(facAbono);
            setContador(admContador, getNuevoContador(admContador));

            double importeNeto = 0;
            double importeIVA = 0;

            // Hay que llamar al procedimiento PROC_SIGA_ACTESTADOABONO para que
            // cambie el estado.
            // String salidaPL[] = new String[2];
            // salidaPL =
            // EjecucionPLs.ejecutarProcPROC_SIGA_ACTESTADOABONO(idInstitucion,
            // idAbono, usr.getUserName());
            // if (!salidaPL[0].equals("0"))
            // throw new ClsExceptions
            // ("Error en PL ejecutarProcPROC_SIGA_ACTESTADOABONO al egenrar el abono");

            if (Double.parseDouble(importeTurnos) != 0) {
                FacLineaabono htTurnos = this.prepararListaAbono(htLista, importeTurnos, idLinea, UtilidadesString.getMensajeIdioma(usuario.getIdlenguaje(), "factSJCS.abonos.literal.turnos"));
                idLinea++;

                htTurnos.setUsumodificacion(usuario.getIdusuario());
                htTurnos.setFechamodificacion(new Date());
                facLineaabonoExtendsMapper.insertSelective(htTurnos);

                importeNeto += new Double(importeTurnos).doubleValue() * new Double(htLista.getCantidad()).doubleValue();

                importeIVA += ((new Double(importeTurnos).doubleValue() * new Double(htLista.getCantidad()).doubleValue()) * htLista.getIva().doubleValue()) / 100;
            }
            if (Double.parseDouble(importeGuardias) != 0) {
                FacLineaabono htGuardias = this.prepararListaAbono(htLista, importeGuardias, idLinea, UtilidadesString.getMensajeIdioma(usuario.getIdlenguaje(), "factSJCS.abonos.literal.guardiasPresenciales"));
                idLinea++;

                htGuardias.setUsumodificacion(usuario.getIdusuario());
                htGuardias.setFechamodificacion(new Date());
                facLineaabonoExtendsMapper.insertSelective(htGuardias);

                importeNeto += new Double(importeGuardias).doubleValue() * new Double(htLista.getCantidad()).doubleValue();

                importeIVA += ((new Double(importeGuardias).doubleValue() * new Double(htLista.getCantidad()).doubleValue()) * htLista.getIva().doubleValue()) / 100;
            }
            if (Double.parseDouble(importeSoj) != 0) {
                FacLineaabono htSoj = this.prepararListaAbono(htLista, importeSoj, idLinea, UtilidadesString.getMensajeIdioma(usuario.getIdlenguaje(), "factSJCS.abonos.literal.SOJ"));
                idLinea++;

                htSoj.setUsumodificacion(usuario.getIdusuario());
                htSoj.setFechamodificacion(new Date());
                facLineaabonoExtendsMapper.insertSelective(htSoj);

                importeNeto += new Double(importeSoj).doubleValue() * new Double(htLista.getCantidad()).doubleValue();

                importeIVA += ((new Double(importeSoj).doubleValue() * new Double(htLista.getCantidad()).doubleValue()) * htLista.getIva().doubleValue()) / 100;
            }
            if (Double.parseDouble(importeEjg) != 0) {
                FacLineaabono htEjg = this.prepararListaAbono(htLista, importeEjg, idLinea, UtilidadesString.getMensajeIdioma(usuario.getIdlenguaje(), "factSJCS.abonos.literal.EJG"));
                idLinea++;

                htEjg.setUsumodificacion(usuario.getIdusuario());
                htEjg.setFechamodificacion(new Date());
                facLineaabonoExtendsMapper.insertSelective(htEjg);

                importeNeto += new Double(importeEjg).doubleValue() * new Double(htLista.getCantidad()).doubleValue();

                importeIVA += ((new Double(importeEjg).doubleValue() * new Double(htLista.getCantidad()).doubleValue()) * htLista.getIva().doubleValue()) / 100;
            }
            if (Double.parseDouble(importeMovimientos) != 0) {
                FacLineaabono htMovimientos = this.prepararListaAbono(htLista, importeMovimientos, idLinea, UtilidadesString.getMensajeIdioma(usuario.getIdlenguaje(), "factSJCS.abonos.literal.movimientos"));
                idLinea++;

                htMovimientos.setUsumodificacion(usuario.getIdusuario());
                htMovimientos.setFechamodificacion(new Date());
                facLineaabonoExtendsMapper.insertSelective(htMovimientos);

                importeNeto += new Double(importeMovimientos).doubleValue() * new Double(htLista.getCantidad()).doubleValue();

                importeIVA += ((new Double(importeMovimientos).doubleValue() * new Double(htLista.getCantidad()).doubleValue()) * htLista.getIva().doubleValue()) / 100;
            }
            if (irpfTotal != 0) {
                FacLineaabono hAux = this.prepararListaAbono(htLista, "" + irpfTotal, idLinea, UtilidadesString.getMensajeIdioma(usuario.getIdlenguaje(), "factSJCS.abonos.literal.IRPFTotal"));
                idLinea++;

                hAux.setUsumodificacion(usuario.getIdusuario());
                hAux.setFechamodificacion(new Date());
                facLineaabonoExtendsMapper.insertSelective(hAux);

                importeNeto += new Double(irpfTotal).doubleValue() * new Double(htLista.getCantidad()).doubleValue();

                importeIVA += ((new Double(irpfTotal).doubleValue() * new Double(htLista.getCantidad()).doubleValue()) * htLista.getIva().doubleValue()) / 100;
            }

            // Retenciones por persona:
            // NOTA: Si el importe de retenciones es 0 no generamos el abono:
            if (Double.parseDouble(importeRetenciones) != 0) {
                importeRetencionesPersona = String.valueOf(Double.parseDouble(importeRetenciones));
                FacLineaabono htRetencionPersona = this.prepararListaAbono(htLista, importeRetencionesPersona, idLinea, UtilidadesString.getMensajeIdioma(usuario.getIdlenguaje(), "factSJCS.abonos.literal.retenciones"));
                idLinea++;

                htRetencionPersona.setUsumodificacion(usuario.getIdusuario());
                htRetencionPersona.setFechamodificacion(new Date());
                facLineaabonoExtendsMapper.insertSelective(htRetencionPersona);

                importeNeto += new Double(importeRetencionesPersona).doubleValue() * new Double(htLista.getCantidad()).doubleValue();

                importeIVA += ((new Double(importeRetencionesPersona).doubleValue() * new Double(htLista.getCantidad()).doubleValue()) * htLista.getIva().doubleValue()) / 100;
            }

            // RGG
            // Obtengo el abono insertado
            FacAbonoKey facAbonoKey = new FacAbonoKey();
            facAbonoKey.setIdinstitucion(Short.valueOf(idInstitucion));
            facAbonoKey.setIdabono(Long.valueOf(idAbono));
            FacAbono bAbono = facAbonoExtendsMapper.selectByPrimaryKey(facAbonoKey);

            //Redondeo el importe porque se estaban quedando en estado pendientes por caja abonos con importe 0.00
            redondea(importeNeto, 2);
            redondea(importeIVA, 2);
            Double importeAbono = redondea((importeNeto + importeIVA), 2);

            // RGG 29/05/2009 Cambio de funciones de abono
            bAbono.setImptotal(BigDecimal.valueOf(importeAbono));
            bAbono.setImppendienteporabonar(BigDecimal.valueOf(importeAbono));
            bAbono.setImptotalabonado(BigDecimal.ZERO);
            bAbono.setImptotalabonadoefectivo(BigDecimal.ZERO);
            bAbono.setImptotalabonadoporbanco(BigDecimal.ZERO);
            bAbono.setImptotaliva(BigDecimal.valueOf(importeIVA));
            bAbono.setImptotalneto(BigDecimal.valueOf(importeNeto));

            if (importeAbono <= 0) {
                // pagado
                bAbono.setEstado(Short.valueOf("1"));
            } else {
                if (bAbono.getIdcuenta() != null) {
                    // pendiente pago banco
                    bAbono.setEstado(Short.valueOf("5"));
                } else {
                    // pendiente pago caja
                    bAbono.setEstado(Short.valueOf("6"));
                }
            }

            if (facAbonoExtendsMapper.updateByPrimaryKeySelective(bAbono) == 0) {
                throw new FacturacionSJCSException("Error al actualizar estado e importes del abono");
            }
            // Hay que deducir cobros si es true:
            if (deducirCobros(idInstitucion, idPersonaSoc, colegiadosMarcados)) {
                cantidadCompensada(idInstitucion, idAbono, usuario);
            }
        } catch (Exception e) {
            throw new FacturacionSJCSException("PagoSJCSServiceImpl.crearAbonos()", e);
        }
    }

    private void validarLongitudContador(int contadorSiguiente, AdmContador contador) throws FacturacionSJCSException {

        try {
            String contadorlongitud = Integer.toString(contadorSiguiente);
            Integer longitud = contador.getLongitudcontador();
            if (contadorlongitud.length() > longitud.intValue()) {
                if (contador.getModo().toString().equals("1")) {
                    throw new FacturacionSJCSException("Ya existe el número de registro y al sugerir un nuevo contador se ha violado la longitud máxima configurada para él.");
                } else {
                    throw new FacturacionSJCSException("Se ha superado la máxima longitud para el número contador");
                }
            }
        } catch (Exception e) {
            throw new FacturacionSJCSException("Error al modificar los datos del contador", e);
        }
    }

    /**
     * Metodo que devuelve el siguiente contador siguiente al insertado en la tabla ADM_CONTADOR sin
     * validar su unicidad en el sistema
     */
    private String getNuevoContador(AdmContador contador) throws FacturacionSJCSException {

        String contadorFinalSugerido = "";
        try {
            int contadorNuevo = Integer.parseInt(contador.getContador().toString()) + 1;
            Integer contadorSugerido = new Integer(contadorNuevo);

            // Comprobamos que el contador que se sugiere no supera la longitud definida
            validarLongitudContador(contadorNuevo, contador);

            Integer longitud = contador.getLongitudcontador();
            int longitudContador = longitud.intValue();

            contadorFinalSugerido = UtilidadesString.formatea(contadorSugerido, longitudContador, true);

        } catch (Exception e) {
            throw new FacturacionSJCSException("Error al obtener nuevo contador", e);
        }
        return contadorFinalSugerido;
    }

    private String getNuevoContadorConPrefijoSufijo(AdmContador contenidoAdmContador) throws FacturacionSJCSException {
        String contadorFinalSugerido = "";
        try {
            String numeroAbono = getNuevoContador(contenidoAdmContador);
            contadorFinalSugerido = contenidoAdmContador.getPrefijo() + numeroAbono + contenidoAdmContador.getSufijo();
        } catch (Exception e) {
            throw new FacturacionSJCSException("Error al obtener nuevo contador con prefijo y sufijo", e);
        }
        return contadorFinalSugerido;
    }

    private AdmContador getContador(Short idInstitucion, String idContador) {

        AdmContadorKey admContadorKey = new AdmContadorKey();
        admContadorKey.setIdinstitucion(idInstitucion);
        admContadorKey.setIdcontador(idContador);

        return admContadorExtendsMapper.selectByPrimaryKey(admContadorKey);
    }

    private void setContador(AdmContador datosContadorNuevo, String numRegNuevo) throws FacturacionSJCSException {
        // registro contador que se obtiene de la tabla ADM_CONTADORES con el
        // idinstitucion y el idcontador
        AdmContador gcOriginal = null;

        try {
            gcOriginal = getContador(datosContadorNuevo.getIdinstitucion(), datosContadorNuevo.getIdcontador());
            // Solo actualizamos la tabla Adm_Contador si el contador introducido por
            // pantalla es mayor que el almacenado en la tabla de contadores
            Long numReg = new Long(numRegNuevo);

            if (numReg.longValue() > gcOriginal.getContador()) {
                datosContadorNuevo.setContador(Long.valueOf(numRegNuevo));

                if ((gcOriginal.getPrefijo().equals(datosContadorNuevo.getPrefijo())) && (gcOriginal.getSufijo().equals(datosContadorNuevo.getSufijo()))) {
                    // Solo en el caso de que el usuario no ha modificado el prefijo y sufijo que se
                    // le propone cuando se inserta una nueva sociedad, entonces
                    // se actualiza el campo contador en la tabla ADM_CONTADORES con el ultimo utilizado
                    admContadorExtendsMapper.updateByPrimaryKeySelective(datosContadorNuevo);
                }
            }

        } catch (Exception e) {
            throw new FacturacionSJCSException("Error al modificar los datos del contador", e);
        }
    }

    private FacLineaabono prepararListaAbono(FacLineaabono entrada, String precioUnitario, int idLinea, String descripcion) {

        FacLineaabono salida = new FacLineaabono();
        salida.setIdinstitucion(entrada.getIdinstitucion());
        salida.setIdabono(entrada.getIdabono());
        salida.setIva(entrada.getIva());
        salida.setPreciounitario(new BigDecimal(precioUnitario));
        salida.setCantidad(entrada.getCantidad());
        salida.setNumerolinea((long) idLinea);
        salida.setDescripcionlinea(descripcion);

        return salida;
    }

    /**
     * Funcion que comprueba que el letrado identificado con esa IdInstitucion y
     * ese idPersona est� marcado.
     */
    private boolean colegiadosDeducibles(String idInstitucion, String idPersona, List<ColegiadosPagoDTO> vColegiados) {

        boolean resultado = false, continuar = true;

        // buscamos dentor del Vector algun letrado con ese idPersona y el
        // idInstitucion
        for (int contador = 0; contador < vColegiados.size() && continuar; contador++) {
            ColegiadosPagoDTO colegiado = vColegiados.get(contador);
            if (((String) colegiado.getIdPersona()).equals(idPersona)
                    && ((String) colegiado.getIdInstitucion())
                    .equals(idInstitucion)) {
                if (colegiado.getMarcado() == null)
                    resultado = false;
                else
                    resultado = true;
                continuar = false;
            }
        }
        return resultado;
    }


    /**
     * Funcion que comprueba si hay que deducir cobros para una persona
     *
     * @param idInstitucion
     * @param idPersona
     * @param vColegiados
     * @return
     */
    private boolean deducirCobros(String idInstitucion, String idPersona, List<ColegiadosPagoDTO> vColegiados) {

        boolean resultado = false;

        GenParametrosExample genParametrosExample = new GenParametrosExample();
        genParametrosExample.createCriteria().andModuloEqualTo(SigaConstants.MODULO_FACTURACION_SJCS)
                .andParametroEqualTo(SigaConstants.DEDUCIR_COBROS_AUTOMATICO)
                .andIdinstitucionIn(Arrays.asList(SigaConstants.ID_INSTITUCION_0, Short.valueOf(idInstitucion)));
        genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

        try {
            String cobroAutomatico = genParametrosMapper.selectByExample(genParametrosExample).get(0).getValor();
            boolean automatico = (cobroAutomatico.equals(SigaConstants.DB_TRUE));
            if (automatico)
                resultado = true;
            else {
                if (colegiadosDeducibles(idInstitucion, idPersona, vColegiados))
                    resultado = true;
                else
                    resultado = false;
            }
        } catch (Exception e) {
        }
        return resultado;
    }

    /**
     * Obtiene la cantidad compensada abono-facturas pendientes
     */
    private double cantidadCompensada(String institucion, String abono, AdmUsuarios usuario) throws FacturacionSJCSException {

        double resultado = 0;
        double cantidadPendiente = 0;
        double cantidadOriginal = 0;
        String idPersona = "";
        boolean correcto = true;

        try {

            // Obtengo datos generales del abono
            FacAbonoKey facAbonoKey = new FacAbonoKey();
            facAbonoKey.setIdabono(Long.valueOf(abono));
            facAbonoKey.setIdinstitucion(Short.valueOf(institucion));
            FacAbono datosAbono = facAbonoExtendsMapper.selectByPrimaryKey(facAbonoKey);

            idPersona = datosAbono.getIdpersona().toString();
            cantidadPendiente = datosAbono.getImppendienteporabonar().doubleValue();
            cantidadOriginal = cantidadPendiente;

            // Obtengo factura asociada del abono si procede
            FacAbono refAbono = facAbonoExtendsMapper.selectByPrimaryKey(facAbonoKey);
            String facturaAsociada = refAbono.getIdfactura();
            List<FacFactura> facturasPendientes = getFacturasPendientes(institucion, idPersona, facturaAsociada, cantidadPendiente);

            // Recorro la lista de facturas pendientes
            ListIterator listaFacturas = facturasPendientes.listIterator();
            double importeFactura = 0;
            while (correcto && (cantidadPendiente > 0) && (listaFacturas.hasNext())) {
                FacFactura temporal = (FacFactura) listaFacturas.next();
                // Mientras disponga de cantidad pendiente compenso/pago facturas
                importeFactura = temporal.getImptotalporpagar().doubleValue();

                // Restriccion impuesta por inconsistencia de la BBDD de pruebas
                if (importeFactura >= 0) {

                    // Cargo la tabla hash con los valores del formulario para insertar en la BBDD (pago abono efectivo)
                    FacPagoabonoefectivo datosPagoAbono = new FacPagoabonoefectivo();
                    datosPagoAbono.setIdinstitucion(Short.valueOf(institucion));
                    datosPagoAbono.setIdabono(Long.valueOf(abono));
                    datosPagoAbono.setIdpagoabono(facPagoabonoefectivoExtendsMapper.getNuevoID(institucion, abono));

                    if (importeFactura > cantidadPendiente) {
                        datosPagoAbono.setImporte(BigDecimal.valueOf(cantidadPendiente));
                    } else {
                        datosPagoAbono.setImporte(BigDecimal.valueOf(importeFactura));
                    }

                    datosPagoAbono.setFecha(new Date());
                    datosPagoAbono.setContabilizado(SigaConstants.FACTURA_ABONO_NO_CONTABILIZADA);

                    double importeCompensado = datosPagoAbono.getImporte().doubleValue();

                    correcto = (facPagoabonoefectivoExtendsMapper.insertSelective(datosPagoAbono) == 1);

                    // RGG 29/05/2009 Cambio de funciones de abono
                    if (correcto) {

                        // Obtengo el abono insertado
                        FacAbonoKey facAbonoKey1 = new FacAbonoKey();
                        facAbonoKey1.setIdinstitucion(Short.valueOf(institucion));
                        facAbonoKey1.setIdabono(Long.valueOf(abono));

                        FacAbono bAbono = facAbonoExtendsMapper.selectByPrimaryKey(facAbonoKey1);

                        Double impPendientePorAbonar = redondea(bAbono.getImppendienteporabonar().doubleValue() - importeCompensado, 2);
                        bAbono.setImppendienteporabonar(BigDecimal.valueOf(impPendientePorAbonar));
                        Double impTotalAbonado = redondea(bAbono.getImptotalabonado().doubleValue() + importeCompensado, 2);
                        bAbono.setImptotalabonado(BigDecimal.valueOf(impTotalAbonado));

                        if (bAbono.getImppendienteporabonar().doubleValue() <= 0) {
                            // pagado
                            bAbono.setEstado(Short.valueOf("1"));
                        } else {
                            if (bAbono.getIdcuenta() != null) {
                                // pendiente pago banco
                                bAbono.setEstado(Short.valueOf("5"));
                            } else {
                                // pendiente pago caja
                                bAbono.setEstado(Short.valueOf("6"));
                            }
                        }

                        if (facAbonoExtendsMapper.updateByPrimaryKeySelective(bAbono) != 1) {
                            throw new FacturacionSJCSException("Error al actualizar el pago compensado del abono");
                        }
                    } else {
                        throw new FacturacionSJCSException("Error al actualizar el pago compensado del abono");
                    }

                    // Cargo la tabla hash con los valores del formulario para insertar en la BBDD (pago por caja de la factura)
                    FacPagosporcaja datosPagoFactura = new FacPagosporcaja();
                    datosPagoFactura.setIdinstitucion(Short.valueOf(institucion));
                    datosPagoFactura.setIdfactura(temporal.getIdfactura());
                    datosPagoFactura.setIdpagoporcaja(facPagosporcajaExtendsMapper.getNuevoID(institucion, temporal.getIdfactura()));
                    datosPagoFactura.setFecha(new Date());
                    datosPagoFactura.setTarjeta("N");
                    datosPagoFactura.setTipoapunte(SigaConstants.TIPO_APUNTE_COMPENSADO);
                    datosPagoFactura.setObservaciones(UtilidadesString.getMensajeIdioma(usuario.getIdlenguaje(), "messages.abonos.literal.compensa") + " " + (String) refAbono.getNumeroabono());
                    datosPagoFactura.setContabilizado(SigaConstants.FACTURA_ABONO_NO_CONTABILIZADA);

                    if (importeFactura > cantidadPendiente) {
                        datosPagoFactura.setImporte(BigDecimal.valueOf(cantidadPendiente));
                        cantidadPendiente = 0;
                    } else {
                        datosPagoFactura.setImporte(BigDecimal.valueOf(importeFactura));
                        cantidadPendiente -= importeFactura;
                    }
                    datosPagoFactura.setIdabono(Long.valueOf(abono));
                    datosPagoFactura.setIdpagoabono(datosPagoAbono.getIdpagoabono());

                    correcto = (facPagosporcajaExtendsMapper.insertSelective(datosPagoFactura) == 1);

                    // Actualizo estado del abono
                    if (correcto) {

                        FacFacturaKey facFacturaKey = new FacFacturaKey();
                        facFacturaKey.setIdfactura(temporal.getIdfactura());
                        facFacturaKey.setIdinstitucion(Short.valueOf(institucion));

                        FacFactura facturaBean = facFacturaMapper.selectByPrimaryKey(facFacturaKey);

                        if (null != facturaBean) {

                            // AQUI VAMOS A MODIFICAR LOS VALORES DE IMPORTES
                            Double impTotalCompensadoFac = redondea(facturaBean.getImptotalcompensado().doubleValue() + datosPagoFactura.getImporte().doubleValue(), 2);
                            Double impTotalPagadoFac = redondea(facturaBean.getImptotalpagado().doubleValue() + datosPagoFactura.getImporte().doubleValue(), 2);
                            Double impTotalPorPagarFac = redondea(facturaBean.getImptotalporpagar().doubleValue() - datosPagoFactura.getImporte().doubleValue(), 2);

                            facturaBean.setImptotalcompensado(BigDecimal.valueOf(impTotalCompensadoFac));
                            facturaBean.setImptotalpagado(BigDecimal.valueOf(impTotalPagadoFac));
                            facturaBean.setImptotalporpagar(BigDecimal.valueOf(impTotalPorPagarFac));

                            if (facFacturaMapper.updateByPrimaryKeySelective(facturaBean) == 1) {
                                // AQUI VAMOS A MODIFICAR EL VALOR DE ESTADO
                                consultarActNuevoEstadoFactura(facturaBean, true);
                                //CGP - INICIO (07/11/2017) - R1709_0035 - A�adimos registro en el hist�rico de la facturaci�n.
                                boolean resultadoHistorico = Boolean.FALSE;
                                try {
                                    resultadoHistorico = (facHistoricofacturaExtendsMapper.insertarHistoricoFacParametros(institucion, temporal.getIdfactura(), 10,
                                            Integer.valueOf(datosPagoFactura.getIdpagoporcaja().toString()),
                                            null, null, null, null, null, Integer.valueOf(abono), null) == 1);
                                    if (!resultadoHistorico) {
                                        LOGGER.warn("### No se ha insertado en el historico de la facturacion ");
                                    }
                                } catch (Exception e) {
                                    LOGGER.error("@@@ ERROR: No se ha insertado el historico de la facturacion", e);
                                }
                                //CGP - FIN
                            } else {
                                throw new FacturacionSJCSException("Error al actualizar los importes de la factura");
                            }


                        } else {
                            throw new FacturacionSJCSException("No se ha encontrado la factura buscada: " + institucion);
                        }

//					    // actualizamos el estado del abono
//						ConsPLFacturacion plFacturacion=new ConsPLFacturacion();
//						plFacturacion.actualizarEstadoAbono(new Integer(institucion),new Long(abono), new Integer(userBean.getUserName()));
                    }
                }
            }
            // calculo cuanto dinero se ha compensado
            resultado = cantidadOriginal - cantidadPendiente;
            redondea(resultado, 2);

        } catch (Exception e) {
            throw new FacturacionSJCSException("Error al compensar abono-facturas", e);
        }
        return (resultado);
    }

    private List<FacFactura> getFacturasPendientes(String institucion, String idPersona, String idFactura, double pdtePagar) {

        List<FacFactura> facturasPendientes = new ArrayList<>();

        FacFacturaExample facFacturaExample = new FacFacturaExample();
        facFacturaExample.createCriteria().andIdinstitucionEqualTo(Short.valueOf(institucion))
                .andIdpersonaEqualTo(Long.valueOf(idPersona))
                .andEstadoNotIn(Arrays.asList(Short.valueOf("1"), Short.valueOf("7"), Short.valueOf("8"))).andImptotalporpagarGreaterThan(BigDecimal.ZERO);
        facFacturaExample.setOrderByClause("FECHAEMISION ASC");

        List<FacFactura> facturas = facFacturaMapper.selectByExample(facFacturaExample);

        if (!facturas.isEmpty()) {
            //Variable que nos sirve para ir acumulando para saber cuando ya tiene facturas suficientes
            //para compensar el a
            double totalAcumulado = 0;

            for (FacFactura factura : facturas) {
                totalAcumulado += factura.getImptotalporpagar().doubleValue();

                //Si encontramos la factura la ponemos la primera
                //�QUE SENTIDO TIENE COMPENSAR UN ABONO DE UNA FACTURA CON LA MISMA FACTURA?
                if (idFactura != null && !idFactura.trim().equals("") && factura.getIdfactura().equalsIgnoreCase(idFactura)) {
                    facturasPendientes.add(0, factura);
                } else {
                    facturasPendientes.add(factura);
                }

                //Si el total acumulado es mayor que lo pendiente por pagar nos salimos ya que tenemos las facturas
                //suficientes para compensar
                if (totalAcumulado >= pdtePagar) {
                    break;
                }
            }

        }

        return facturasPendientes;
    }

    /**
     * Este m�todo aunque se llama consulta, tambi�n MODIFICA la tabla FAC_FACTURAS dependiendo del valor del par�metro actualizar
     */
    public String consultarActNuevoEstadoFactura(FacFactura facturaBean, boolean actualizar) throws FacturacionSJCSException {

        String nuevoEstado = "";
        String descEstado = "";

        try {
            double cero = 0;
            if (facturaBean.getImptotalporpagar().doubleValue() <= cero) {
                // Est� pagada
                nuevoEstado = SigaConstants.ESTADO_FACTURA_PAGADA;
            } else {
                // Pendiente de pago
                // BNS
                String ultimoFicheroBancarioDeFactura = facDisquetecargosExtendsMapper.getRenegociacionFactura(facturaBean.getIdinstitucion().toString(), facturaBean.getIdfactura());
                if (ultimoFicheroBancarioDeFactura == null) {
                    if (facturaBean.getIdcuenta() == null && facturaBean.getIdcuentadeudor() == null) {
                        // pendiente pago por caja
                        nuevoEstado = SigaConstants.ESTADO_FACTURA_CAJA;
                    } else {
                        // La factura esta pendiente de enviar a banco
                        nuevoEstado = SigaConstants.ESTADO_FACTURA_BANCO;
                    }
                } else {
                    if (ultimoFicheroBancarioDeFactura.trim().equals("")) {
                        //La factura esta devuelta y pendiente de renegociacion
                        nuevoEstado = SigaConstants.ESTADO_FACTURA_DEVUELTA;
                    } else {
                        // La factura esta renegociada y pendiente de enviar a banco
                        if (facturaBean.getIdcuenta() == null && facturaBean.getIdcuentadeudor() == null) {
                            // pendiente pago por caja
                            nuevoEstado = SigaConstants.ESTADO_FACTURA_CAJA;
                        } else {
                            // La factura esta pendiente de enviar a banco
                            nuevoEstado = SigaConstants.ESTADO_FACTURA_BANCO;
                        }
                    }
                }
            }

            if (actualizar) {

                FacFacturaKey facFacturaKey = new FacFacturaKey();
                facFacturaKey.setIdinstitucion(facturaBean.getIdinstitucion());
                facFacturaKey.setIdfactura(facturaBean.getIdfactura());

                FacFactura facturaLocalBean = facFacturaMapper.selectByPrimaryKey(facFacturaKey);

                if (null != facturaLocalBean) {
                    facturaLocalBean.setEstado(Short.valueOf(nuevoEstado));
                    if (facFacturaMapper.updateByPrimaryKeySelective(facturaLocalBean) == 0) {
                        throw new FacturacionSJCSException("Error al actualizar el estado");
                    }
                    try {
                        //TODO: Si no se produce error regeneramos el pdf
                        generarPdfFacturaFirmada(facturaLocalBean, Boolean.TRUE);
                    } catch (Exception e) {

                    }
                }
            }

            FacEstadofacturaExample facEstadofacturaExample = new FacEstadofacturaExample();
            facEstadofacturaExample.createCriteria().andIdestadoEqualTo(Short.valueOf(nuevoEstado));

            List<FacEstadofactura> facEstadoFacBean = facEstadofacturaMapper.selectByExample(facEstadofacturaExample);

            if (facEstadoFacBean != null && facEstadoFacBean.size() > 0) {
                descEstado = facEstadoFacBean.get(0).getDescripcion();
            }

        } catch (Exception e) {
            throw new FacturacionSJCSException("Excepcion en la consulta/actualizacion del estado de la factura.", e);
        }

        return descEstado;
    }

    /**
     * Metodo que implementa la generacion de la factura en PDF firmada
     */
    public File generarPdfFacturaFirmada(FacFactura bFacFactura, boolean bRegenerar) throws FacturacionSJCSException {
        try {

            File filePDF = generarPdfFacturaSinFirmar(bFacFactura, bRegenerar);

            if (filePDF == null) {
                throw new FacturacionSJCSException("Error al generar la factura. Fichero devuelto es nulo");

            } else {
                LOGGER.info("DESPUES DE LA GENERACION DE LA FACTURA: " + filePDF.getAbsolutePath());
                LOGGER.info("Existe el fichero: " + (filePDF.exists() ? "SI" : "NO"));
            }

            // Genero una carpeta con las firmas
            String sRutaFirmas = filePDF.getParentFile().getPath() + SigaConstants.FILE_SEP + "firmas";
            File fRutaFirmas = new File(sRutaFirmas);
            mkdirs(sRutaFirmas);

            if (!fRutaFirmas.canWrite()) {
                throw new FacturacionSJCSException("Sistema: El path donde almacenar las facturas no tiene los permisos adecuados.");
            }

            // Genero una copia del pdf que se va a firmar
            File fFicheroFirmado = new File(sRutaFirmas + SigaConstants.FILE_SEP + filePDF.getName());
            copyFile(filePDF, fFicheroFirmado);

            // Firmo el pdf
            boolean isFirmadoOk = firmarPDF(fFicheroFirmado, bFacFactura.getIdinstitucion().toString());
            LOGGER.info("PDF FIRMADO:: " + isFirmadoOk);

            // Hay que borrar el pdf sin firma si no tiene numero de factura
            if (bFacFactura.getNumerofactura() == null || bFacFactura.getNumerofactura().equals("")) {
                filePDF.delete();
            }

            return fFicheroFirmado;

        } catch (Exception e) {
            throw new FacturacionSJCSException("Error al invocar generarPdfFacturaFirmada", e);
        }
    }

    /**
     * Metodo que implementa la generacion de la factura en PDF
     */
    private File generarPdfFacturaSinFirmar(FacFactura bFacturaBean, boolean bRegenerar) throws FacturacionSJCSException {

        try {

            String idFacturaParametro = "-1";

            idFacturaParametro = bFacturaBean.getIdfactura();
            // Obtenemos el numero de colegiado
            CenColegiadoKey cenColegiadoKey = new CenColegiadoKey();
            cenColegiadoKey.setIdinstitucion(bFacturaBean.getIdinstitucion());
            cenColegiadoKey.setIdpersona(bFacturaBean.getIdpersona());
            CenColegiado hCenColegiado = cenColegiadoMapper.selectByPrimaryKey(cenColegiadoKey);

            String nColegiado = "";
            if (null != hCenColegiado) {
                nColegiado = hCenColegiado.getNcolegiado();
            }

            // obtener ruta almacen
            String idSerieIdProgramacion = bFacturaBean.getIdseriefacturacion().toString() + "_" + bFacturaBean.getIdprogramacion().toString();

            GenPropertiesKey genPropertiesKey = new GenPropertiesKey();
            genPropertiesKey.setFichero(SigaConstants.FICHERO_SIGA);
            genPropertiesKey.setParametro(SigaConstants.PARAMETRO_DIRECTORIO_FISICO_FACTURA_PDF);

            String directorioFisicoFacturaPDFJava = genPropertiesMapper.selectByPrimaryKey(genPropertiesKey).getValor();
            genPropertiesKey.setParametro(SigaConstants.PARAMETRO_DIRECTORIO_FACTURA_PDF);
            String directorioFacturaPDFJava = genPropertiesMapper.selectByPrimaryKey(genPropertiesKey).getValor();

            String rutaAlmacen = directorioFisicoFacturaPDFJava + directorioFacturaPDFJava + SigaConstants.FILE_SEP + bFacturaBean.getIdinstitucion().toString() +
                    SigaConstants.FILE_SEP + idSerieIdProgramacion;
            File rutaPDF = new File(rutaAlmacen);

            mkdirs(rutaAlmacen);

            if (!rutaPDF.canWrite()) {
                throw new FacturacionSJCSException("Sistema: El path donde almacenar las facturas no tiene los permisos adecuados.");
            }

            //para las facturas que estan generadas pero no confirmadas por lo que no tienen numero se le pondra de nombre del idfactura y se borrara una vez descargada
            String nombrePDF = "";
            if (bFacturaBean.getNumerofactura() == null || bFacturaBean.getNumerofactura().equals("")) {
                nombrePDF = nColegiado + "-" + bFacturaBean.getIdfactura();
            } else { // Contiene numero de factura, por lo tanto, esta confirmada
                nombrePDF = nColegiado + "-" + UtilidadesString.validarNombreFichero(bFacturaBean.getNumerofactura());
            }

            // Buscamos si el fichero existe
            File fPdf = new File(rutaAlmacen + SigaConstants.FILE_SEP + nombrePDF + ".pdf");

            // TODO CÓDIGO QUE FALTA POR MIGRAR
            /*if (bRegenerar || !fPdf.exists()) { // Lo regeneramos en caso de que no exista

                Vector<String> vFacPlantillaFacturacion = admFacPlantillaFacturacion.getPlantillaSerieFacturacion(bFacturaBean.getIdInstitucion().toString(), bFacturaBean.getIdSerieFacturacion().toString());
                String modelo = "";
                if (vFacPlantillaFacturacion != null && vFacPlantillaFacturacion.size() > 0) {
                    modelo = (String) vFacPlantillaFacturacion.get(0);
                }

                // Obtenemos el lenguaje del cliente
                String idioma = admCliente.getLenguaje(bFacturaBean.getIdInstitucion().toString(), bFacturaBean.getIdPersona().toString());

                // RGG 26/02/2007 cambio en los codigos de lenguajes
                String idiomaExt = admLenguajes.getLenguajeExt(idioma);

                String rutaPlantilla = rp.returnProperty("facturacion.directorioFisicoPlantillaFacturaJava") +
                        rp.returnProperty("facturacion.directorioPlantillaFacturaJava") +
                        ClsConstants.FILE_SEP + bFacturaBean.getIdInstitucion().toString() +
                        ClsConstants.FILE_SEP + modelo;
                String nombrePlantilla = "factura_" + idiomaExt + ".fo";

                // Utilizamos la ruta de la plantilla para el temporal
                String rutaServidorTmp = rutaPlantilla + ClsConstants.FILE_SEP + "tmp_factura_" + System.currentTimeMillis();

                String contenidoPlantilla = this.obtenerContenidoPlantilla(rutaPlantilla, nombrePlantilla);

                ClsLogging.writeFileLog("ANTES DE GENERAR EL INFORME. ", 10);
                fPdf = this.generarInforme(request, rutaServidorTmp, contenidoPlantilla, rutaAlmacen, nombrePDF, idFacturaParametro);
                ClsLogging.writeFileLog("DESPUES DE GENERAR EL INFORME EN  " + rutaAlmacen, 10);
            }*/

            return fPdf;

        } catch (Exception e) {
            throw new FacturacionSJCSException("Error al invocar generarPdfFactura: " + e.getLocalizedMessage());
        }
    }

    private void mkdirs(String filePath) {
        boolean changeParentsToo;

        if (filePath == null || filePath.trim().equalsIgnoreCase(""))
            return;

        File fileDir = new File(filePath.toString());
        if (fileDir != null && !fileDir.exists()) {
            changeParentsToo = !fileDir.getParentFile().exists();
            fileDir.mkdirs();
            SIGAHelper.addPerm777(fileDir);
        }
    }

    private void copyFile(File s, File t) throws IOException {
        FileChannel in = (new FileInputStream(s)).getChannel();
        FileChannel out = (new FileOutputStream(t)).getChannel();
        in.transferTo(0, s.length(), out);
        in.close();
        out.close();
    }

    private boolean firmarPDF(File fIn, String idInstitucion) {
        try {
            LOGGER.info("VOY A FIRMAR EL PDF: " + fIn.getAbsolutePath());
            return firmarPDF(new Short(idInstitucion), fIn.getAbsolutePath());
        } catch (Exception e) {
            LOGGER.error("***************** ERROR DE FIRMA DIGITAL EN DOCUMENTO *************************");
            LOGGER.error("Error al FIRMAR el PDF de la institucion: " + idInstitucion);
            LOGGER.error("*******************************************************************************");
            e.printStackTrace();
            LOGGER.error("*******************************************************************************");
            return false;
        }
    }

    private KeyStore getKeyStore(String keyStoreType, String certificadoDigitalPath, String pwdCertificadoDigital) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        InputStream inputStream = new FileInputStream(certificadoDigitalPath);
        keyStore.load(inputStream, pwdCertificadoDigital.toCharArray());
        if (inputStream != null) {
            inputStream.close();
            inputStream = null;
        }
        return keyStore;

    }

    public boolean firmarPDF(Short idInstitucion, String nombreFicheroEntrada) throws Exception {
        FileInputStream fisID = null;
        FileOutputStream fos = null;
        try {
            GregorianCalendar gcFecha = new GregorianCalendar();

            GenParametrosExample genParametrosExample = new GenParametrosExample();
            genParametrosExample.createCriteria().andModuloEqualTo(SigaConstants.MODULO_CER)
                    .andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idInstitucion))
                    .andParametroEqualTo(SigaConstants.PARAMETRO_PATH_CERTIFICADOS_DIGITALES);
            genParametrosExample.setOrderByClause("IDINSTITUCION DESC");

            GenParametros certificadoDigitalDirectorio = genParametrosMapper.selectByExample(genParametrosExample).get(0);

            genParametrosExample.clear();
            genParametrosExample.createCriteria().andModuloEqualTo(SigaConstants.MODULO_CER)
                    .andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idInstitucion))
                    .andParametroEqualTo(SigaConstants.PARAMETRO_NOMBRE_CERTIFICADOS_DIGITALES);
            genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
            GenParametros certificadoDigitalNombre = genParametrosMapper.selectByExample(genParametrosExample).get(0);


            genParametrosExample.clear();
            genParametrosExample.createCriteria().andModuloEqualTo(SigaConstants.MODULO_CER)
                    .andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idInstitucion))
                    .andParametroEqualTo(SigaConstants.PARAMETRO_CLAVE_CERTIFICADOS_DIGITALES);
            genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
            GenParametros pwdCertificadoDigital = genParametrosMapper.selectByExample(genParametrosExample).get(0);

            StringBuilder certificadoDigitalPath = new StringBuilder();
            certificadoDigitalPath.append(certificadoDigitalDirectorio.getValor());
            certificadoDigitalPath.append(File.separator);
            certificadoDigitalPath.append(certificadoDigitalNombre.getValor());

            KeyStore keyStore = getKeyStore("PKCS12", certificadoDigitalPath.toString(), pwdCertificadoDigital.getValor());

            String sNombreFicheroSalida = nombreFicheroEntrada + ".tmp";
            File fOut = new File(sNombreFicheroSalida);
            File fIn = new File(nombreFicheroEntrada);

            String sAlias = (String) keyStore.aliases().nextElement();
            PrivateKey pKey = (PrivateKey) keyStore.getKey(sAlias, pwdCertificadoDigital.getValor().toCharArray());
            Certificate[] aCertificados = keyStore.getCertificateChain(sAlias);

            PdfReader reader = new PdfReader(nombreFicheroEntrada);

            fos = new FileOutputStream(sNombreFicheroSalida);

            PdfStamper stamper = PdfStamper.createSignature(reader, fos, '\0');
            PdfSignatureAppearance psa = stamper.getSignatureAppearance();

            psa.setCrypto(pKey, aCertificados, null, PdfSignatureAppearance.WINCER_SIGNED);
            psa.setSignDate(gcFecha);

            stamper.close();
            fos.close();
            fIn.delete();
            fOut.renameTo(fIn);

            return true;
        } catch (Exception e) {
            LOGGER.error("***************** ERROR DE FIRMA DIGITAL EN DOCUMENTO *************************");
            LOGGER.error("Error al FIRMAR el PDF: " + nombreFicheroEntrada + " IdInstitucion: " + idInstitucion);
            LOGGER.error("Error al FIRMAR el PDF: " + e.getMessage());
            LOGGER.error("*******************************************************************************");
//	        e.printStackTrace();
            LOGGER.error("*******************************************************************************");
            return false;
        } finally {
            try {
                if (fos != null) fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (fisID != null) fisID.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public UpdateResponseDTO deshacerCierre(String idPago, HttpServletRequest request) {

        LOGGER.info("PagoSJCSServiceImpl.deshacerCierre() -> Entrada al servicio para deshacer el cierre del pago: " + idPago);

        String token = request.getHeader("Authorization");
        String dni = UserTokenUtils.getDniFromJWTToken(token);
        Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
        UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
        Error error = new Error();

        try {

            if (null != idInstitucion) {

                AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
                exampleUsuarios.createCriteria().andNifEqualTo(dni)
                        .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

                LOGGER.info("PagoSJCSServiceImpl.deshacerCierre() -> admUsuariosExtendsMapper.selectByExample() ->" +
                        " Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
                List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
                LOGGER.info("PagoSJCSServiceImpl.deshacerCierre() -> admUsuariosExtendsMapper.selectByExample() ->" +
                        " Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

                if (null != usuarios && !usuarios.isEmpty()) {
                    if (FacturacionSJCSServicesImpl.isAlguienEjecutando()) {
                        LOGGER.info(
                                "YA SE ESTA EJECUTANDO LA FACTURACIÓN SJCS EN BACKGROUND. CUANDO TERMINE SE INICIARA OTRA VEZ EL PROCESO DE DESBLOQUEO.");
                        updateResponseDTO.setStatus(SigaConstants.KO);
                        error.setCode(400);
                        error.setDescription("facturacionSJCS.facturacionPagos.pagos.errorDeshacerCierreProceso");
                    }else {
                        FacturacionSJCSServicesImpl.setAlguienEjecutando();
                        try{
                            FcsPagosjgKey fcsPagosjgKey = new FcsPagosjgKey();
                            fcsPagosjgKey.setIdpagosjg(Integer.valueOf(idPago));
                            fcsPagosjgKey.setIdinstitucion(idInstitucion);

                            FcsPagosjg pago = fcsPagosjgExtendsMapper.selectByPrimaryKey(fcsPagosjgKey);

                            utilidadesPagoSJCS.deshacerCierre(pago, idInstitucion, usuarios.get(0));
                        }catch (Exception e){
                            LOGGER.error(e.getMessage());
                            LOGGER.error(e.getCause());
                            LOGGER.error(e);
                            throw e;
                        }finally {
                            FacturacionSJCSServicesImpl.setNadieEjecutando();
                        }
                    }
                }
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            LOGGER.error(e.getCause());
            LOGGER.error(e);
            updateResponseDTO.setStatus(SigaConstants.KO);
            error.setCode(500);
            error.setDescription("general.message.error.realiza.accion");
        }

        updateResponseDTO.setError(error);

        LOGGER.info("PagoSJCSServiceImpl.deshacerCierre() -> Salida del servicio para deshacer el cierre del pago: " + idPago);

        return updateResponseDTO;
    }


}
