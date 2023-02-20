package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.ModulosItem;
import org.itcgae.siga.DTOs.scs.ProcedimientoItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.ScsProcedimientosMapper;
import org.itcgae.siga.db.services.cen.providers.ScsJurisdiccionSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsAreasMateriasSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsProcedimientosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsProcedimientosExtendsMapper extends ScsProcedimientosMapper{

	@SelectProvider(type = ScsProcedimientosSqlExtendsProvider.class, method = "searchProcess")
	@Results({
		@Result(column = "IDPROCEDIMIENTO", property = "idProcedimiento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTE", property = "importe", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGO", property = "codigo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "JURISDICCION", property = "jurisdiccion", jdbcType = JdbcType.VARCHAR),
	})
	List<ProcedimientoItem> searchProcess(String idLenguaje, Short idInstitucion);

	@SelectProvider(type = ScsProcedimientosSqlExtendsProvider.class, method = "searchModulo")
	@Results({
		@Result(column = "IDPROCEDIMIENTO", property = "idProcedimiento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAHASTAVIGOR", property = "fechahastavigor", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHADESDEVIGOR", property = "fechadesdevigor", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTE", property = "importe", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGO", property = "codigo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "JURISDICCION", property = "jurisdiccion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "JURISDICCIONDES", property = "jurisdiccionDes", jdbcType = JdbcType.VARCHAR),
	})
	List<ModulosItem> searchModulo(ModulosItem moduloItem, String idioma);
	
	@SelectProvider(type = ScsProcedimientosSqlExtendsProvider.class, method = "getIdProcedimiento")
	@Results({ @Result(column = "IDPROCEDIMIENTO", property = "newId", jdbcType = JdbcType.VARCHAR)
	})
	NewIdDTO getIdProcedimiento(Short idInstitucion);
	
	@SelectProvider(type = ScsProcedimientosSqlExtendsProvider.class, method = "getProcedimientos")
	@Results({
		@Result(column = "IDPRETENSION", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> getProcedimientos(String idInstitucion, String idProcedimiento, String idioma);
	
	@SelectProvider(type = ScsProcedimientosSqlExtendsProvider.class, method = "getComplementoProcedimiento")
	List<String> getComplementoProcedimiento(String idInstitucion, String idProcedimiento);
	
	
}
