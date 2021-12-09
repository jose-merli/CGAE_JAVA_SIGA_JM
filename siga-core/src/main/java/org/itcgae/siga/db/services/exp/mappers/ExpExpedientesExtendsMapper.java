package org.itcgae.siga.db.services.exp.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.exea.ExpedienteItem;
import org.itcgae.siga.db.mappers.ExpExpedienteMapper;
import org.itcgae.siga.db.services.exp.providers.ExpExpedienteSqlExtendsProvider;

import java.util.List;

public interface ExpExpedientesExtendsMapper extends ExpExpedienteMapper {

    @SelectProvider(type = ExpExpedienteSqlExtendsProvider.class, method = "getExpedientesSigaColegiado")
    @Results({
            @Result(column = "TIPOEXPEDIENTE", property = "tipoExpediente", jdbcType = JdbcType.VARCHAR),
            @Result(column = "RELACION", property = "relacion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ESTADOEXPEDIENTE", property = "estadoExpediente", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHAAPERTURA", property = "fechaApertura", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NUMEROEXPEDIENTE", property = "numExpediente", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ANIOEXPEDIENTE", property = "anioExpediente", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDTIPOEXPEDIENTE", property = "idTipoExpediente", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDINSTITUCIONTIPOEXPEDIENTE", property = "idInstitucionTipoExpediente", jdbcType = JdbcType.VARCHAR),

    })
    List<ExpedienteItem> getExpedientesSigaColegiado(Short idInstitucion, String idPersona);
}
