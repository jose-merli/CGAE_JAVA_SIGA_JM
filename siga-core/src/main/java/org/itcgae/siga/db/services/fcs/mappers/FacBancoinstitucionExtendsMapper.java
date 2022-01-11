package org.itcgae.siga.db.services.fcs.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.FacBancoinstitucionMapper;
import org.itcgae.siga.db.services.fcs.providers.FacBancoinstitucionSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface FacBancoinstitucionExtendsMapper extends FacBancoinstitucionMapper {

    @SelectProvider(type = FacBancoinstitucionSqlExtendsProvider.class, method = "comboPropTranferencia")
    @Results({@Result(column = "IBAN", property = "label", jdbcType = JdbcType.VARCHAR),
            @Result(column = "BANCOS_CODIGO", property = "value", jdbcType = JdbcType.VARCHAR)})
    List<ComboItem> comboCuentasBanc(Short idInstitucion);


}
