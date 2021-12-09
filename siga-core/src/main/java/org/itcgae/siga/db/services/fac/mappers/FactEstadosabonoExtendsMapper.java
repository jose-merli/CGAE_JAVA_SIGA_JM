package org.itcgae.siga.db.services.fac.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem2;
import org.itcgae.siga.db.services.fac.providers.FactEstadosabonoExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface FactEstadosabonoExtendsMapper extends FactEstadosabonoMapper {

    @SelectProvider(type = FactEstadosabonoExtendsSqlProvider.class, method = "comboEstadosAbonos")
    @Results({
            @Result(column = "idestado", property = "value", jdbcType = JdbcType.NUMERIC),
            @Result(column = "descripcion", property = "label1", jdbcType = JdbcType.VARCHAR),
            @Result(column = "tipo", property = "label2", jdbcType = JdbcType.VARCHAR)
    })
    List<ComboItem2> comboEstadosAbonos(String idioma);

}
