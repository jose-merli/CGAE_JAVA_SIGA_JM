package org.itcgae.siga.db.services.fac.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem2;
import org.itcgae.siga.db.mappers.FacEstadofacturaMapper;
import org.itcgae.siga.db.services.fac.providers.FactEstadosfacturaExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface FactEstadosfacturaExtendsMapper extends FacEstadofacturaMapper {

    @SelectProvider(type = FactEstadosfacturaExtendsSqlProvider.class, method = "comboEstadosFacturas")
    @Results({
            @Result(column = "idestado", property = "value", jdbcType = JdbcType.NUMERIC),
            @Result(column = "descripcion", property = "label1", jdbcType = JdbcType.VARCHAR),
            @Result(column = "tipo", property = "label2", jdbcType = JdbcType.VARCHAR)
    })
    List<ComboItem2> comboEstadosFacturas(String idioma);

}
