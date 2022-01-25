package org.itcgae.siga.db.services.fcs.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.FacSufijoMapper;
import org.itcgae.siga.db.services.fcs.providers.FacSufijoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface FacSufijoExtendsMapper extends FacSufijoMapper {

    @SelectProvider(type = FacSufijoSqlExtendsProvider.class, method = "comboSufijos")
    @Results({@Result(column = "CONCEPTO", property = "label", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDSUFIJO", property = "value", jdbcType = JdbcType.VARCHAR)})
    List<ComboItem> comboSufijos(Short idInstitucion);

}
