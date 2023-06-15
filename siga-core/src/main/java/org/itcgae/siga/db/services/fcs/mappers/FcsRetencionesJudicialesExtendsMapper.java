package org.itcgae.siga.db.services.fcs.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.itcgae.siga.DTOs.scs.*;
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
            @Result(column = "FECHAINICIO", property = "fechainicio", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAFIN", property = "fechaFin", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "IMPORTE", property = "importe", jdbcType = JdbcType.VARCHAR),
            @Result(column = "RETENCIONAPLICADA", property = "retencionAplicada", jdbcType = JdbcType.VARCHAR),
            @Result(column = "RESTANTE", property = "restante", jdbcType = JdbcType.VARCHAR)
    })
    List<RetencionesItem> searchRetenciones(Short idInstitucion, RetencionesRequestDTO retencionesRequestDTO, String idLenguaje, Integer tamMaximo);

    @SelectProvider(type = FcsRetencionesJudicialesSqlExtendsProvider.class, method = "searchRetencionConRestante")
    @Results({
            @Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDRETENCION", property = "idRetencion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDDESTINATARIO", property = "idDestinatario", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHAALTA", property = "fechaAlta", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAINICIO", property = "fechainicio", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAFIN", property = "fechaFin", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "TIPORETENCION", property = "tipoRetencion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IMPORTE", property = "importe", jdbcType = JdbcType.VARCHAR),
            @Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHAMODIFICACION", property = "fechaModificacion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "USUMODIFICACION", property = "usuModificacion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "CONTABILIZADO", property = "contabilizado", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ESDETURNO", property = "esDeTurno", jdbcType = JdbcType.VARCHAR),
            @Result(column = "DESCDESTINATARIO", property = "descDestinatario", jdbcType = JdbcType.VARCHAR),
            @Result(column = "RESTANTE", property = "restante", jdbcType = JdbcType.VARCHAR)
    })
    RetencionJudicialItem searchRetencionConRestante(Short idInstitucion, String idRetencion);

    @SelectProvider(type = FcsRetencionesJudicialesSqlExtendsProvider.class, method = "searchRetencionesAplicadas")
    @Results({
            @Result(column = "TIPORETENCION", property = "tipoRetencion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NCOLEGIADO", property = "numColegiado", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDDESTINATARIO", property = "idDestinatario", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NOMBREDESTINATARIO", property = "nombreDestinatario", jdbcType = JdbcType.VARCHAR),
            @Result(column = "DESCDESTINATARIO", property = "desDestinatario", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHAINICIO", property = "fechaInicio", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAFIN", property = "fechaFin", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHARETENCION", property = "fechaRetencion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IMPORTERETENIDO", property = "importeRetenido", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDPAGOSJG", property = "idPagosjg", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ANIOMES", property = "anioMes", jdbcType = JdbcType.VARCHAR),
            @Result(column = "PAGORELACIONADO", property = "pagoRelacionado", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ABONORELACIONADO", property = "abonoRelacionado", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDCOBRO", property = "idCobro", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDRETENCION", property = "idRetencion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHADESDE", property = "fechaDesde", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHAHASTA", property = "fechaHasta", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IMPORTEPAGO", property = "importePago", jdbcType = JdbcType.VARCHAR)
    })
    List<RetencionesAplicadasItem> searchRetencionesAplicadas(Short idInstitucion, RetencionesRequestDTO retencionesRequestDTO, Integer tamMaximo);

    @SelectProvider(type = FcsRetencionesJudicialesSqlExtendsProvider.class, method = "getAplicacionesRetenciones")
    @Results({
            @Result(column = "ANIOMES", property = "anioMes", jdbcType = JdbcType.VARCHAR),
            @Result(column = "TIPORETENCION", property = "tipoRetencion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IMPORTEANTAPLICARETENCION", property = "importeAntAplicaRetencion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IMPORTEANTRETENIDO", property = "importeAntRetenido", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IMPORTEAPLICARETENCION", property = "importeAplicaRetencion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IMPORTERETENIDO", property = "importeRetenido", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IMPORTETOTAPLICARETENCION", property = "importeTotAplicaRetencion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IMPORTETOTRETENIDO", property = "importeTotRetenido", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IMPORTESMI", property = "importeSmi", jdbcType = JdbcType.VARCHAR),
            @Result(column = "COLEGIADO", property = "colegiado", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NOMBREPAGO", property = "nombrePago", jdbcType = JdbcType.VARCHAR)
    })
    List<AplicacionRetencionItem> getAplicacionesRetenciones(Short idInstitucion, AplicacionRetencionRequestDTO aplicacionRetencionRequestDTO);

    @SelectProvider(type = FcsRetencionesJudicialesSqlExtendsProvider.class, method = "getNewId")
    @Results({
            @Result(column = "IDRETENCION", property = "idMax", jdbcType = JdbcType.VARCHAR),
    })
    MaxIdDto getNewId(Short idinstitucion);
}
