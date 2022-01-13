package org.itcgae.siga.db.services.fcs.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.FcsDestinatariosRetencionesMapper;
import org.itcgae.siga.db.services.fcs.providers.FcsDestinatariosRetencionesSqlExtendsProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FcsDestinatariosRetencionesExtendsMapper extends FcsDestinatariosRetencionesMapper {

    @SelectProvider(type = FcsDestinatariosRetencionesSqlExtendsProvider.class, method = "getComboDestinatarios")
    @Results({
            @Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ID", property = "value", jdbcType = JdbcType.VARCHAR)
    })
    List<ComboItem> getComboDestinatarios(Short idInstitucion);

}
