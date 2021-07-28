package org.itcgae.siga.db.services.fcs.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.ConceptoPagoItem;
import org.itcgae.siga.DTOs.scs.PagosjgItem;
import org.itcgae.siga.db.mappers.FcsPagosjgMapper;
import org.itcgae.siga.db.services.fcs.providers.FcsPagosjgSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface FcsPagosjgExtendsMapper extends FcsPagosjgMapper {

    @SelectProvider(type = FcsPagosjgSqlExtendsProvider.class, method = "comboPagosColegio")
    @Results({@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ID", property = "value", jdbcType = JdbcType.VARCHAR)})
    List<ComboItem> comboPagosColegio(String idLenguaje, Short idInstitucion);

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

}
