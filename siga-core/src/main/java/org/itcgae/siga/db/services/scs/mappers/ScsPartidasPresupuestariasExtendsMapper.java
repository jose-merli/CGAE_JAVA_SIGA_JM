package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.ModulosItem;
import org.itcgae.siga.DTOs.scs.PartidasItem;
import org.itcgae.siga.DTOs.scs.ProcedimientoItem;
import org.itcgae.siga.db.mappers.ScsPartidapresupuestariaMapper;
import org.itcgae.siga.db.mappers.ScsPartidapresupuestariaSqlProvider;
import org.itcgae.siga.db.mappers.ScsProcedimientosMapper;
import org.itcgae.siga.db.services.scs.providers.ScsAreasMateriasSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsPartidasPresupuestariasSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsProcedimientosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsPartidasPresupuestariasExtendsMapper extends ScsPartidapresupuestariaMapper{

	@SelectProvider(type = ScsPartidasPresupuestariasSqlExtendsProvider.class, method = "searchProcess")
	@Results({
		@Result(column = "IDPROCEDIMIENTO", property = "idProcedimiento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTE", property = "importe", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGO", property = "codigo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "JURISDICCION", property = "jurisdiccion", jdbcType = JdbcType.VARCHAR),

	})
	List<ProcedimientoItem> searchProcess(String idLenguaje, Short idInstitucion);

	@SelectProvider(type=ScsPartidasPresupuestariasSqlExtendsProvider.class, method="searchPartida")
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDPARTIDAPRESUPUESTARIA", property="idpartidapresupuestaria", jdbcType=JdbcType.DECIMAL),
        @Result(column="NOMBREPARTIDA", property="nombrepartida", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="IMPORTEPARTIDA", property="importepartida", jdbcType=JdbcType.VARCHAR),
        @Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.DATE) 
	})
	List<PartidasItem> searchPartida(PartidasItem partidasItems);
	
	@SelectProvider(type=ScsPartidasPresupuestariasSqlExtendsProvider.class, method="searchPartidaPres")
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL),
        @Result(column="IDPARTIDAPRESUPUESTARIA", property="idpartidapresupuestaria", jdbcType=JdbcType.DECIMAL),
        @Result(column="NOMBREPARTIDA", property="nombrepartida", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="DESCRIPCION", property="descripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="IMPORTEPARTIDA", property="importepartida", jdbcType=JdbcType.VARCHAR),
        @Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.DATE) 
	})
	List<PartidasItem> searchPartidaPres(PartidasItem partidasItems);

	@SelectProvider(type = ScsPartidasPresupuestariasSqlExtendsProvider.class, method = "getIdPartidaPres")
	@Results({ @Result(column = "IDPARTIDAPRES", property = "newId", jdbcType = JdbcType.VARCHAR)
	})
	NewIdDTO getIdPartidaPres(Short idInstitucion);
	
}
