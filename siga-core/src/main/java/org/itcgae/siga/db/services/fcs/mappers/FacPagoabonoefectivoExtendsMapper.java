package org.itcgae.siga.db.services.fcs.mappers;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.EstadosAbonosItem;
import org.itcgae.siga.db.entities.FacPagoabonoefectivo;
import org.itcgae.siga.db.mappers.FacPagoabonoefectivoMapper;
import org.itcgae.siga.db.services.fcs.providers.FacPagoabonoefectivoSqlExtendsProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FacPagoabonoefectivoExtendsMapper extends FacPagoabonoefectivoMapper {

    @SelectProvider(type = FacPagoabonoefectivoSqlExtendsProvider.class, method = "getNuevoID")
    Long getNuevoID(String institucion, String abono);

    @DeleteProvider(type = FacPagoabonoefectivoSqlExtendsProvider.class, method = "deleteDeshacerCierre")
    int deleteDeshacerCierre(Short idInstitucion, List<Integer> idPagos);

    @SelectProvider(type = FacPagoabonoefectivoSqlExtendsProvider.class, method = "getEstadosAbonos")
    @Results({
        @Result(column = "FECHA", property = "fecha", jdbcType = JdbcType.DATE),
        @Result(column = "FECHAMODIFICACION", property = "fechaModificaion", jdbcType = JdbcType.DATE),
        @Result(column = "MODO", property = "accion", jdbcType = JdbcType.VARCHAR),
        @Result(column = "IDESTADO", property = "idEstado", jdbcType = JdbcType.VARCHAR),
        @Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR),
        @Result(column = "IMPORTE", property = "movimiento", jdbcType = JdbcType.VARCHAR),
        @Result(column = "IMPORTE_PENDIENTE", property = "importePendiente", jdbcType = JdbcType.VARCHAR),
        @Result(column = "IDCUENTA", property = "idCuenta", jdbcType = JdbcType.VARCHAR),
        @Result(column = "IDDISQUETEABONO", property = "idDisqueteAbono", jdbcType = JdbcType.VARCHAR),
        @Result(column = "BANCO", property = "cuentaBancaria", jdbcType = JdbcType.VARCHAR),
        @Result(column = "IDENTIFICADOR", property = "identificador", jdbcType = JdbcType.VARCHAR),
        @Result(column = "IDFACTURA", property = "idFactura", jdbcType = JdbcType.VARCHAR),
        @Result(column = "NUMEROFACTURA", property = "numeroFactura", jdbcType = JdbcType.VARCHAR),
        @Result(column = "IDABONO", property = "idAbono", jdbcType = JdbcType.VARCHAR),
        @Result(column = "NUMEROABONO", property = "numeroAbono", jdbcType = JdbcType.VARCHAR),
        @Result(column = "COMENTARIO", property = "comentario", jdbcType = JdbcType.VARCHAR),
    })
    List<EstadosAbonosItem> getEstadosAbonos(String idAbono, Short idInstitucion, String idioma);
}
