package org.itcgae.siga.db.services.fcs.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.RetencionesAplicadasItem;
import org.itcgae.siga.DTOs.scs.RetencionesItem;
import org.itcgae.siga.DTOs.scs.RetencionesRequestDTO;
import org.itcgae.siga.db.mappers.FcsRetencionesJudicialesMapper;
import org.itcgae.siga.db.services.fcs.providers.FcsRetencionesJudicialesSqlExtendsProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FcsRetencionesJudicialesExtendsMapper extends FcsRetencionesJudicialesMapper {

    @SelectProvider(type = FcsRetencionesJudicialesSqlExtendsProvider.class, method = "searchRetenciones")
    @Results({
            @Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDRETENCION", property = "idRetencion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NCOLEGIADO", property = "nColegiado", jdbcType = JdbcType.VARCHAR),
            @Result(column = "TIPORETENCION", property = "tipoRetencion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDDESTINATARIO", property = "idDestinatario", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NOMBREDESTINATARIO", property = "nombreDestinatario", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHAINICIO", property = "fechaInicio", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAFIN", property = "fechaFin", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "IMPORTE", property = "importe", jdbcType = JdbcType.VARCHAR),
            @Result(column = "RETENCIONAPLICADA", property = "retencionAplicada", jdbcType = JdbcType.VARCHAR),
            @Result(column = "RESTANTE", property = "restante", jdbcType = JdbcType.VARCHAR)
    })
    List<RetencionesItem> searchRetenciones(Short idInstitucion, RetencionesRequestDTO retencionesRequestDTO, String idLenguaje, Integer tamMaximo);

    @SelectProvider(type = FcsRetencionesJudicialesSqlExtendsProvider.class, method = "searchRetencionesAplicadas")
    @Results({
            @Result(column = "TIPORETENCION", property = "tipoRetencion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NCOLEGIADO", property = "numColegiado", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
            @Result(column = "APELLIDOS1", property = "apellidos1", jdbcType = JdbcType.VARCHAR),
            @Result(column = "APELLIDOS2", property = "apellidos2", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDDESTINATARIO", property = "idDestinatario", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NOMBREDESTINATARIO", property = "nombreDestinatario", jdbcType = JdbcType.VARCHAR),
            @Result(column = "DESCDESTINATARIO", property = "desDestinatario", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHAINICIO", property = "fechaInicio", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAFIN", property = "fechaFin", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHARETENCION", property = "fechaRetencion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IMPORTERETENIDO", property = "importeRetenido", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDPAGOSJG", property = "idPagosjg", jdbcType = JdbcType.VARCHAR),
            @Result(column = "MES", property = "mes", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
            @Result(column = "PAGORELACIONADO", property = "pagoRelacionado", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ABONORELACIONADO", property = "abonoRelacionado", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDCOBRO", property = "idCobro", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDRETENCION", property = "idRetencion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHADESDE", property = "fechaDesde", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHAHASTA", property = "fechaHasta", jdbcType = JdbcType.VARCHAR)
    })
    List<RetencionesAplicadasItem> searchRetencionesAplicadas(Short idInstitucion, RetencionesRequestDTO retencionesRequestDTO, Integer tamMaximo);
}
