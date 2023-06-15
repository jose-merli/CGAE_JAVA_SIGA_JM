package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.TiposActuacionItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.entities.ScsTipoactuacion;
import org.itcgae.siga.db.entities.ScsTipoactuacionExample;
import org.itcgae.siga.db.mappers.ScsTipoactuacionMapper;
import org.itcgae.siga.db.mappers.ScsTipoactuacionSqlProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTipoactuacionSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTipoasistenciaColegioSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTipoasistenciaSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsTipoactuacionExtendsMapper extends ScsTipoactuacionMapper{

	@SelectProvider(type = ScsTipoactuacionSqlExtendsProvider.class, method = "getComboActuacion")
	@Results({
		@Result(column = "IDTIPOACTUACION", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> getComboActuacion(String idTipoAsistencia, String idLenguaje, Short idInstitucion);
	
	@SelectProvider(type = ScsTipoactuacionSqlExtendsProvider.class, method = "searchTiposActuacion")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTIPOASISTENCIA", property = "idtipoasistencia", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTIPOACTUACION", property = "idtipoactuacion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPORTE", property = "importe", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "IMPORTEMAXIMO", property = "importemaximo", jdbcType = JdbcType.DECIMAL),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP) })
	
	List<TiposActuacionItem> searchTiposActuacion(boolean historico, String idLenguaje, Short idInstitucion);
	
	@SelectProvider(type=ScsTipoactuacionSqlExtendsProvider.class, method="getTiposAsistencia")
    @Results({
        @Result(column="IDTIPOASISTENCIACOLEGIO", property="value", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="DESCRIPCION", property="label", jdbcType=JdbcType.VARCHAR)
    })
	List<ComboItem> getTiposAsistencia(String idLenguaje,Short idInstitucion);
	
	@SelectProvider(type = ScsTipoactuacionSqlExtendsProvider.class, method = "getIdTipoactuacion")
	@Results({ @Result(column = "IDTIPOACTUACION", property = "newId", jdbcType = JdbcType.VARCHAR)
	
	})
	NewIdDTO getIdTipoactuacion(Short idInstitucion);
}
