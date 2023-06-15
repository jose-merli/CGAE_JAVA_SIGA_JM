package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.ProcedimientoItem;
import org.itcgae.siga.db.mappers.ScsJuzgadoprocedimientoMapper;
import org.itcgae.siga.db.services.scs.providers.ScsJuzgadoprocedimientoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsJuzgadoProcedimientoExtendsMapper extends ScsJuzgadoprocedimientoMapper{

	@SelectProvider(type = ScsJuzgadoprocedimientoSqlExtendsProvider.class, method = "searchProcJudged")
	@Results({
		@Result(column = "IDPROCEDIMIENTO", property = "idProcedimiento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTE", property = "importe", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGO", property = "codigo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "JURISDICCION", property = "jurisdiccion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechadesdevigor", property = "fechadesdevigor", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechahastavigor", property = "fechahastavigor", jdbcType = JdbcType.VARCHAR)

	})
	List<ProcedimientoItem> searchProcJudged(String idLenguaje, Short idInstitucion, String idJuzgado, String historico);
	

}
