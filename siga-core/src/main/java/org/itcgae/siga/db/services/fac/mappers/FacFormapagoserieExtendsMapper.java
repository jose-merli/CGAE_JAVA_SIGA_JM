package org.itcgae.siga.db.services.fac.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.FacFormapagoserieMapper;
import org.itcgae.siga.db.services.fac.providers.FacFormapagoserieExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface FacFormapagoserieExtendsMapper extends FacFormapagoserieMapper {

    @SelectProvider(type = FacFormapagoserieExtendsSqlProvider.class, method = "getFormasPagosSerie")
    @Results({
            @Result(column = "idformapago", property = "value", jdbcType = JdbcType.NUMERIC),
            @Result(column = "descripcion", property = "label", jdbcType = JdbcType.VARCHAR)
    })
    List<ComboItem> getFormasPagosSerie(String idSerieFacturacion, Short idInstitucion, String idioma);

}
