package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.TiposAsistenciaItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ScsTipoasistenciaMapper;
import org.itcgae.siga.db.mappers.ScsTipoasistenciaSqlProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTipoasistenciaSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsTipoasistenciaExtendsMapper extends ScsTipoasistenciaMapper{

	@SelectProvider(type = ScsTipoasistenciaSqlExtendsProvider.class, method = "getComboAsistencia")
	@Results({
		@Result(column = "IDTIPOASISTENCIA", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> getComboAsistencia(String idLenguaje, Short idInstitucion);
	
	@SelectProvider(type=ScsTipoasistenciaSqlExtendsProvider.class, method="searchTiposAsistencia")
    @Results({
        @Result(column="IDTIPOASISTENCIA", property="idtipoasistencia", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="CODIGOEXT", property="codigoext", jdbcType=JdbcType.VARCHAR),
        @Result(column="BLOQUEADO", property="bloqueado", jdbcType=JdbcType.CHAR),
        @Result(column="FECHA_BAJA", property="fechaBaja", jdbcType=JdbcType.TIMESTAMP)
    })
 List<TiposAsistenciaItem> searchTiposAsistencia(boolean historico, String idLenguaje, Short idInstitucion);
	
	@SelectProvider(type=ScsTipoasistenciaSqlExtendsProvider.class, method="searchTiposAsistenciaPorDefecto")
    @Results({
        @Result(column="IDTIPOASISTENCIA", property="idtipoasistencia", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="CODIGOEXT", property="codigoext", jdbcType=JdbcType.VARCHAR),
        @Result(column="BLOQUEADO", property="bloqueado", jdbcType=JdbcType.CHAR),
        @Result(column="FECHA_BAJA", property="fechaBaja", jdbcType=JdbcType.TIMESTAMP)
    })
 List<TiposAsistenciaItem> searchTiposAsistenciaPorDefecto(boolean historico, String idLenguaje, Short idInstitucion);
	
	@SelectProvider(type=ScsTipoasistenciaSqlExtendsProvider.class, method="getTiposGuardia")
    @Results({
        @Result(column="IDTIPOASISTENCIA", property="value", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="DESCRIPCION", property="label", jdbcType=JdbcType.VARCHAR)
    })
	List<ComboItem> getTiposGuardia(String idLenguaje,Short idInstitucion);
	
	@SelectProvider(type=ScsTipoasistenciaSqlExtendsProvider.class, method="getTiposGuardia2")
    @Results({
        @Result(column="IDTIPOASISTENCIA", property="value", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="DESCRIPCION", property="label", jdbcType=JdbcType.VARCHAR)
    })
	List<ComboItem> getTiposGuardia2(String idLenguaje,Short idInstitucion);
	
	@SelectProvider(type=ScsTipoasistenciaSqlExtendsProvider.class, method="updateTiposAsistencia")
    @Results({
        @Result(column="IDTIPOASISTENCIA", property="idtipoasistencia", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="CODIGOEXT", property="codigoext", jdbcType=JdbcType.VARCHAR),
        @Result(column="BLOQUEADO", property="bloqueado", jdbcType=JdbcType.CHAR),
        @Result(column="FECHA_BAJA", property="fechaBaja", jdbcType=JdbcType.TIMESTAMP)
    })
 List<TiposAsistenciaItem> updateTiposAsistencia(TiposAsistenciaItem tiposAsistenciasItem, String idLenguaje, Short idInstitucion);
}
