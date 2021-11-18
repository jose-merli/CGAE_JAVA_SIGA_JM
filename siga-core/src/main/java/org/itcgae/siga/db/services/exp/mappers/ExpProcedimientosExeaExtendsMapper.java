package org.itcgae.siga.db.services.exp.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ExpProcedimientosExeaMapper;
import org.itcgae.siga.db.services.exp.providers.ExpProcedimientosExeaSqlExtendsProvider;

import java.util.List;

public interface ExpProcedimientosExeaExtendsMapper extends ExpProcedimientosExeaMapper {

    @SelectProvider(type = ExpProcedimientosExeaSqlExtendsProvider.class, method = "comboProcedimientosEXEA")
    @Results({
            @Result(column = "ID_PROC_EXEA", property = "value", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR)
    })
    List<ComboItem> comboProcedimientosEXEA(String idlenguaje, String idInstitucion);
}
