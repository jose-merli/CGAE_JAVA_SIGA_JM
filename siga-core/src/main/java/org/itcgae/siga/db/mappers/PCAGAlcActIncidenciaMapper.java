package org.itcgae.siga.db.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.db.entities.PCAJGAlcActIncidencia;

public interface PCAGAlcActIncidenciaMapper {
   
    @Select({
        "select",
        "*",
        "from PCAJG_ALC_ACT_INCIDENCIA"
    })
    @Results({
        @Result(column="CAMPO", property="campo", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="MENSAJE", property="mensaje", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="NIVEL", property="nivel", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="IDTIPOINC", property="idTipoIncidencia", jdbcType=JdbcType.DECIMAL, id=true)
    })
    List<PCAJGAlcActIncidencia> selectAll();

}