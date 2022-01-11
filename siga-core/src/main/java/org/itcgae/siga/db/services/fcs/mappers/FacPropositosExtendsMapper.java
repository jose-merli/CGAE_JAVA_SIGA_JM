package org.itcgae.siga.db.services.fcs.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.FacPropositosMapper;
import org.itcgae.siga.db.services.fcs.providers.FacPropositosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface FacPropositosExtendsMapper extends FacPropositosMapper {

    @SelectProvider(type = FacPropositosSqlExtendsProvider.class, method = "comboPropTranferencia")
    @Results({@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDPROPOSITO", property = "value", jdbcType = JdbcType.VARCHAR)})
    List<ComboItem> comboPropTranferencia(String idLenguaje, boolean isOtrasTrans);

}
