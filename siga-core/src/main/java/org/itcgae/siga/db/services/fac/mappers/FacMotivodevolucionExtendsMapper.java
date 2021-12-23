package org.itcgae.siga.db.services.fac.mappers;


import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.FacMotivodevolucionMapper;
import org.itcgae.siga.db.services.fac.providers.FacMotivodevolucionExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface FacMotivodevolucionExtendsMapper extends FacMotivodevolucionMapper {

    @SelectProvider(type = FacMotivodevolucionExtendsSqlProvider.class, method = "comboMotivosDevolucion")
    @Results({
            @Result(column = "id", property = "value", jdbcType = JdbcType.NUMERIC),
            @Result(column = "descripcion", property = "label", jdbcType = JdbcType.VARCHAR)
    })
    List<ComboItem> comboMotivosDevolucion(String idLenguaje);
}
