package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ScsEstadoasistenciaMapper;
import org.itcgae.siga.db.services.scs.providers.ScsEstadoAsistenciaSqlExtendsProvider;

public interface ScsEstadoasistenciaExtendsMapper extends ScsEstadoasistenciaMapper {
	
	@SelectProvider(type=ScsEstadoAsistenciaSqlExtendsProvider.class, method="comboEstadosAsistencia")
    @Results({
        @Result(column="IDESTADOASISTENCIA", property="value", jdbcType=JdbcType.VARCHAR),
        @Result(column="DESCRIPCION", property="label", jdbcType=JdbcType.VARCHAR),
    })
    List<ComboItem> comboEstadosAsistencia(String idLenguaje);

}
