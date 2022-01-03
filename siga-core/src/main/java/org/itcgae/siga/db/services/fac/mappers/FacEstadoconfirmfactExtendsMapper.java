package org.itcgae.siga.db.services.fac.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.FacEstadoconfirmfactMapper;
import org.itcgae.siga.db.services.fac.providers.FacEstadoconfirmfactExtendsSqlProvider;
import org.itcgae.siga.db.services.fac.providers.FacFormapagoserieExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface FacEstadoconfirmfactExtendsMapper extends FacEstadoconfirmfactMapper {

    @SelectProvider(type = FacEstadoconfirmfactExtendsSqlProvider.class, method = "comboEstados")
    @Results({
            @Result(column = "idestado", property = "value", jdbcType = JdbcType.NUMERIC),
            @Result(column = "descripcion", property = "label", jdbcType = JdbcType.VARCHAR)
    })
    List<ComboItem> comboEstados(String tipo, String idioma);

}
