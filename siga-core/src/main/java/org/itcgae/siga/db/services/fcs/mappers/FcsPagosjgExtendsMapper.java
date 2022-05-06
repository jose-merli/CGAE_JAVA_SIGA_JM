package org.itcgae.siga.db.services.fcs.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.*;
import org.itcgae.siga.db.entities.FcsPagoColegiado;
import org.itcgae.siga.db.entities.FcsPagosEstadospagos;
import org.itcgae.siga.db.entities.FcsPagosjg;
import org.itcgae.siga.db.mappers.FcsPagosjgMapper;
import org.itcgae.siga.db.services.fcs.providers.FcsPagosjgSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Primary
public interface FcsPagosjgExtendsMapper extends FcsPagosjgMapper {

    @SelectProvider(type = FcsPagosjgSqlExtendsProvider.class, method = "comboPagosColegio")
    @Results({@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ID", property = "value", jdbcType = JdbcType.VARCHAR)})
    List<ComboItem> comboPagosColegio(String idLenguaje, Short idInstitucion);

    @SelectProvider(type = FcsPagosjgSqlExtendsProvider.class, method = "comboPagosColegioPorEstados")
    @Results({@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ID", property = "value", jdbcType = JdbcType.VARCHAR)})
    List<ComboItem> comboPagosColegioPorEstados(String idLenguaje, Short idInstitucion, List<String> idEstados);

    @SelectProvider(type = FcsPagosjgSqlExtendsProvider.class, method = "comboAplicadoEnPago")
    @Results({@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ID", property = "value", jdbcType = JdbcType.VARCHAR)})
    List<ComboItem> comboAplicadoEnPago(String idInstitucion);
    

    @SelectProvider(type = FcsPagosjgSqlExtendsProvider.class, method = "buscarPagos")
    @Results({@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ABREVIATURA", property = "abreviatura", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDFACTURACION", property = "idFacturacion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDPAGOSJG", property = "idPagosjg", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHADESDE", property = "fechaDesde", jdbcType = JdbcType.DATE),
            @Result(column = "FECHAHASTA", property = "fechaHasta", jdbcType = JdbcType.DATE),
            @Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
            @Result(column = "DESESTADO", property = "desEstado", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDESTADO", property = "idEstado", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHAESTADO", property = "fechaEstado", jdbcType = JdbcType.DATE),
            @Result(column = "CANTIDAD", property = "cantidad", jdbcType = JdbcType.VARCHAR),
            @Result(column = "PORCENTAJE", property = "porcentaje", jdbcType = JdbcType.VARCHAR)})
    List<PagosjgItem> buscarPagos(PagosjgItem pagosItem, String idInstitucion, String idLenguaje, Integer tamMax);

    @SelectProvider(type = FcsPagosjgSqlExtendsProvider.class, method = "datosGeneralesPagos")
    @Results({@Result(column = "IDFACTURACION", property = "idFacturacion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NOMBREFAC", property = "nombreFac", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDPAGOSJG", property = "idPagosjg", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ABREVIATURA", property = "abreviatura", jdbcType = JdbcType.VARCHAR)})
    List<PagosjgItem> datosGeneralesPagos(String idPago, String idInstitucion);

    @SelectProvider(type = FcsPagosjgSqlExtendsProvider.class, method = "historicoPagos")
    @Results({@Result(column = "IDESTADOPAGOSJG", property = "idEstado", jdbcType = JdbcType.VARCHAR),
            @Result(column = "DESCRIPCION", property = "desEstado", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHAESTADO", property = "fechaEstado", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "USUARIO", property = "nombreUsuModificacion", jdbcType = JdbcType.VARCHAR)})
    List<PagosjgItem> historicoPagos(String idPago, String lenguaje, Short idInstitucion);

    @SelectProvider(type = FcsPagosjgSqlExtendsProvider.class, method = "getNewIdPago")
    @Results({@Result(column = "IDPAGOSJG", property = "newId", jdbcType = JdbcType.VARCHAR)})
    NewIdDTO getNewIdPago(Short idInstitucion);

    @SelectProvider(type = FcsPagosjgSqlExtendsProvider.class, method = "getConceptosPago")
    @Results({@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDPAGOSJG", property = "idPagosjg", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDFACTURACION", property = "idFacturacion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDHITOGENERAL", property = "idConcepto", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDGRUPOFACTURACION", property = "idGrupoFacturacion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "DESCRIPCION", property = "desConcepto", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IMPORTEFACTURADO", property = "importeFacturado", jdbcType = JdbcType.NUMERIC),
            @Result(column = "IMPORTEPENDIENTE", property = "importePendiente", jdbcType = JdbcType.NUMERIC),
            @Result(column = "PORCENTAJEPENDIENTE", property = "porcentajePendiente", jdbcType = JdbcType.NUMERIC),
            @Result(column = "IMPORTEPAGADO", property = "cantidadApagar", jdbcType = JdbcType.NUMERIC),
            @Result(column = "PORCENTAJEPAGADO", property = "porcentajeApagar", jdbcType = JdbcType.NUMERIC)})
    List<ConceptoPagoItem> getConceptosPago(String idPago, Short idInstitucion, String lenguaje);

    @SelectProvider(type = FcsPagosjgSqlExtendsProvider.class, method = "comboConceptosPago")
    @Results({@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDFACTURACION", property = "idFacturacion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDPAGOSJG", property = "idPagosjg", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDHITOGENERAL", property = "idConcepto", jdbcType = JdbcType.VARCHAR),
            @Result(column = "DESCRIPCION", property = "desConcepto", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDGRUPOFACTURACION", property = "idGrupoFacturacion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IMPORTEFACTURADO", property = "importeFacturado", jdbcType = JdbcType.NUMERIC),
            @Result(column = "IMPORTEPENDIENTE", property = "importePendiente", jdbcType = JdbcType.NUMERIC),
            @Result(column = "PORCENTAJEPENDIENTE", property = "porcentajePendiente", jdbcType = JdbcType.NUMERIC)})
    List<ConceptoPagoItem> comboConceptosPago(Short idInstitucion, String idFacturacion, String idPago, String idLenguaje);

    @SelectProvider(type = FcsPagosjgSqlExtendsProvider.class, method = "hayMovimientosVariosPositivosAaplicar")
    int hayMovimientosVariosPositivosAaplicar(Short idInstitucion, String idFacturacion);

    @SelectProvider(type = FcsPagosjgSqlExtendsProvider.class, method = "getConfigFichAbonos")
    @Results({@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDPAGOSJG", property = "idPagosjg", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDFACTURACION", property = "idFacturacion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "BANCOS_CODIGO", property = "codBanco", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDSUFIJO", property = "idSufijo", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDPROPSEPA", property = "idPropSepa", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDPROPOTROS", property = "idPropOtros", jdbcType = JdbcType.VARCHAR)})
    List<PagosjgItem> getConfigFichAbonos(String idPago, Short idInstitucion);

    @SelectProvider(type = FcsPagosjgSqlExtendsProvider.class, method = "getNumApuntesPago")
    @Results({@Result(column = "NUMAPUNTES", property = "valor", jdbcType = JdbcType.VARCHAR)})
    StringDTO getNumApuntesPago(String idPago, Short idInstitucion, String idLenguaje);

    @SelectProvider(type = FcsPagosjgSqlExtendsProvider.class, method = "getEstadoPago")
    String getEstadoPago(String idPago, Short idInstitucion);

    @SelectProvider(type = FcsPagosjgSqlExtendsProvider.class, method = "getPagosSJCSBloqueadosEnEjecucion")
    List<FcsPagosjg> getPagosSJCSBloqueadosEnEjecucion(Short idInstitucion, Long tiempoMaximoMinutos);

    @SelectProvider(type = FcsPagosjgSqlExtendsProvider.class, method = "getCompensacionFacturas")
    @Results({@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NCOLEGIADO", property = "numColegiado", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDFACTURA", property = "idFactura", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NUMEROFACTURA", property = "numeroFactura", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHAFACTURA", property = "fechaFactura", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IMPORTETOTALFACTURA", property = "importeTotalFactura", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IMPORTEPENDIENTEFACTURA", property = "importePendienteFactura", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IMPORTECOMPENSADO", property = "importeCompensado", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IMPORTEPAGO", property = "importePagado", jdbcType = JdbcType.VARCHAR)})
    List<CompensacionFacItem> getCompensacionFacturas(String idPago, Short idInstitucion);

    @SelectProvider(type = FcsPagosjgSqlExtendsProvider.class, method = "getColegiadosApagar")
    @Results({
            @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDPAGOSJG", property = "idpagosjg", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDPERSONA_SJCS", property = "idperorigen", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDPERDESTINO", property = "idperdestino", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IMPOFICIO", property = "impoficio", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IMPASISTENCIA", property = "impasistencia", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IMPSOJ", property = "impsoj", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IMPEJG", property = "impejg", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IMPMOVVAR", property = "impmovvar", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IMPIRPF", property = "impirpf", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IMPRET", property = "impret", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDCUENTA", property = "idcuenta", jdbcType = JdbcType.DECIMAL),
            @Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "PORCENTAJEIRPF", property = "porcentajeirpf", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDRETENCION", property = "idretencion", jdbcType = JdbcType.DECIMAL)
    })
    List<FcsPagoColegiado> getColegiadosApagar(Short idInstitucion, String idPago, int caseMorosos);

    @SelectProvider(type = FcsPagosjgSqlExtendsProvider.class, method = "perteneceAunaSociedad")
    @Results({
            @Result(column = "IDINSTITUCION", property = "idCuenta", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDCUENTA", property = "idCuenta", jdbcType = JdbcType.DECIMAL)
    })
    List<PerteneceAunaSociedadDTO> perteneceAunaSociedad(Short idInstitucion, String idPersona);

    @SelectProvider(type = FcsPagosjgSqlExtendsProvider.class, method = "tieneCuentaAbonoAsociada")
    String tieneCuentaAbonoAsociada(Short idInstitucion, String idPersonaSociedad, String idCuenta);

    @SelectProvider(type = FcsPagosjgSqlExtendsProvider.class, method = "getCuentaBancariaActiva")
    List<String> getCuentaBancariaActiva(Short idInstitucion, String idPersona);

    @SelectProvider(type = FcsPagosjgSqlExtendsProvider.class, method = "getMovimientosRW")
    @Results({
            @Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDMOVIMIENTO", property = "idMovimiento", jdbcType = JdbcType.DECIMAL),
            @Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "MOTIVO", property = "motivo", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHAALTA", property = "fechaAlta", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "CANTIDAD", property = "cantidad", jdbcType = JdbcType.DECIMAL),
            @Result(column = "FECHAMODIFICACION", property = "fechaModificacion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "USUMODIFICACION", property = "usuModificacion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDFACTURACION", property = "idFacturacion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDGRUPOFACTURACION", property = "idGrupoFacturacion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IMPORTEAPLICADO", property = "importeAplicado", jdbcType = JdbcType.DECIMAL)
    })
    List<MovimientoVarioDTO> getMovimientosRW(Short idInstitucion, String idPersona, String idFacturacion, String fDesde, String idGrupoFacturacion, int caso);

    @SelectProvider(type = FcsPagosjgSqlExtendsProvider.class, method = "getFacturacionesGruposPagos")
    @Results({
            @Result(column = "IDFACTURACION", property = "idFacturacion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDGRUPOFACTURACION", property = "idGrupoFacturacion", jdbcType = JdbcType.DECIMAL)
    })
    List<FacturacionGrupoPagoDTO> getFacturacionesGruposPagos(String idPago, String idInstitucion);

    @SelectProvider(type = FcsPagosjgSqlExtendsProvider.class, method = "getSumaMovimientosAplicados")
    double getSumaMovimientosAplicados(String idInstitucion, String idMovimiento, String idPersona);

    @SelectProvider(type = FcsPagosjgSqlExtendsProvider.class, method = "getSumaRetenciones")
    double getSumaRetenciones(String idInstitucion, String idPago, String idPersona);

    @SelectProvider(type = FcsPagosjgSqlExtendsProvider.class, method = "getNuevoIdAplicaMovimientosVarios")
    Long getNuevoIdAplicaMovimientosVarios();

    @SelectProvider(type = FcsPagosjgSqlExtendsProvider.class, method = "getDetallePagoColegiado")
    @Results({
            @Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDPAGOS", property = "idPagos", jdbcType = JdbcType.DECIMAL),
            @Result(column = "TOTALIMPORTESJCS", property = "totalImporteSjcs", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IMPORTETOTALRETENCIONES", property = "importeTotalRetenciones", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IMPORTETOTALMOVIMIENTOS", property = "importeTotalMovimientos", jdbcType = JdbcType.DECIMAL),
            @Result(column = "TOTALIMPORTEIRPF", property = "totalImporteIrpf", jdbcType = JdbcType.DECIMAL),
            @Result(column = "TOTALFINAL", property = "totalFinal", jdbcType = JdbcType.DECIMAL),
            @Result(column = "TOTALIMPORTEIVA", property = "totalImporteIva", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDPERSONASJCS", property = "idPersonaSjcs", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDPERDESTINO", property = "idPerDestino", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDCUENTA", property = "idCuenta", jdbcType = JdbcType.DECIMAL),
            @Result(column = "TIPOIRPF", property = "tipoIrpf", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IMPORTETOTALOFICIO", property = "importeTotalOficio", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IMPORTETOTALASISTENCIA", property = "importeTotalAsistencia", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IMPORTETOTALEJG", property = "importeTotalEjg", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IMPORTETOTALSOJ", property = "importeTotalSoj", jdbcType = JdbcType.DECIMAL),
            @Result(column = "DESTINATARIO", property = "destinatario", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FORMADEPAGO", property = "formaDePago", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NOMBREBANCO", property = "nombreBanco", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NUMEROCUENTA", property = "numeroCuenta", jdbcType = JdbcType.VARCHAR)
    })
    DetallePagoColegiadoDTO getDetallePagoColegiado(String idInstitucion, String idPagosJg, String idPersona, boolean irpf, String idioma);

    @SelectProvider(type = FcsPagosjgSqlExtendsProvider.class, method = "getPago")
    @Results({
            @Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDPAGOSJG", property = "idPagosjg", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDESTADO", property = "idEstado", jdbcType = JdbcType.VARCHAR),
            @Result(column = "DESESTADO", property = "desEstado", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDFACTURACION", property = "idFacturacion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NOMBREFAC", property = "nombreFac", jdbcType = JdbcType.VARCHAR),
    })
    PagosjgItem getPago(String idPago, Short idInstitucion, String idLenguaje);

    @SelectProvider(type = FcsPagosjgSqlExtendsProvider.class, method = "getFechaEstadoPago")
    Date getFechaEstadoPago(Short idInstitucion, Integer idPago);

}
