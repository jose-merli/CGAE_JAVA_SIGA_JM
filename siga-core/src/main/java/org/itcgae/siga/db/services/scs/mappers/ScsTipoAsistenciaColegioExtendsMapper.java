package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.TiposAsistenciaItem;
import org.itcgae.siga.db.mappers.ScsTipoasistenciacolegioMapper;
import org.itcgae.siga.db.services.scs.providers.ScsTipoasistenciaColegioSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsTipoAsistenciaColegioExtendsMapper extends ScsTipoasistenciacolegioMapper{
	
	@SelectProvider(type = ScsTipoasistenciaColegioSqlExtendsProvider.class, method = "getIdTipoasistenciacolegio")
	@Results({ @Result(column = "IDTIPOASISTENCIACOLEGIO", property = "newId", jdbcType = JdbcType.VARCHAR)
	
	})
	NewIdDTO getIdTipoasistenciacolegio(Short idInstitucion);
	
	@SelectProvider(type=ScsTipoasistenciaColegioSqlExtendsProvider.class, method="selectTiposAsistenciaColegiado")
	 @Results({ @Result(column = "idtipoasistenciacolegio", property = "idtipoasistenciacolegio", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "descripcion", property = "tipoasistencia", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "pordefecto", property = "pordefecto", jdbcType = JdbcType.VARCHAR)})
    List<TiposAsistenciaItem> getTiposAsistenciaColegiado(Short idInstitucion, Integer idLenguaje, String idTipoGuardia);
}
