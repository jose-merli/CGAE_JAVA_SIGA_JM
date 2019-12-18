package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.TiposAsistenciaItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.ScsTipoasistenciaMapper;
import org.itcgae.siga.db.mappers.ScsTipoasistenciaSqlProvider;
import org.itcgae.siga.db.mappers.ScsTipoasistenciacolegioMapper;
import org.itcgae.siga.db.mappers.ScsTipoasistenciacolegioSqlProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTipoasistenciaColegioSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTipoasistenciaSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTipofundamentosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsTipoAsistenciaColegioExtendsMapper extends ScsTipoasistenciacolegioMapper{
	
	@SelectProvider(type = ScsTipoasistenciaColegioSqlExtendsProvider.class, method = "getIdTipoasistenciacolegio")
	@Results({ @Result(column = "IDTIPOASISTENCIACOLEGIO", property = "newId", jdbcType = JdbcType.VARCHAR)
	
	})
	NewIdDTO getIdTipoasistenciacolegio(Short idInstitucion);
}
