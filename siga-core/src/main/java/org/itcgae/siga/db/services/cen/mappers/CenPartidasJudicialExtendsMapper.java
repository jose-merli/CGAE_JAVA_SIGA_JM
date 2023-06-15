package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.PartidasJudicialesItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.ProcedimientoItem;
import org.itcgae.siga.db.entities.CenInfluencia;
import org.itcgae.siga.db.mappers.CenPartidojudicialMapper;
import org.itcgae.siga.db.services.cen.providers.CenPartidojudicialSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.CenPartidasJudicialSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenPartidasJudicialExtendsMapper extends CenPartidojudicialMapper{

	@SelectProvider(type = CenPartidasJudicialSqlExtendsProvider.class, method = "searchProcess")
	@Results({
		@Result(column = "IDPROCEDIMIENTO", property = "idProcedimiento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTE", property = "importe", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGO", property = "codigo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "JURISDICCION", property = "jurisdiccion", jdbcType = JdbcType.VARCHAR),

	})
	List<ProcedimientoItem> searchProcess(String idLenguaje, Short idInstitucion);

	@SelectProvider(type=CenPartidasJudicialSqlExtendsProvider.class, method="searchPartida")
	@Results({ @Result(column = "IDPARTIDO", property = "idpartido", jdbcType = JdbcType.DECIMAL, id = true),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
		@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
		@Result(column = "IDINSTITUCIONPROPIETARIO", property = "idinstitucionpropietario", jdbcType = JdbcType.DECIMAL),
		@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR) })
	List<PartidasJudicialesItem> searchPartida(PartidasJudicialesItem partidasJudicialesItems);
	
	@SelectProvider(type = CenPartidojudicialSqlExtendsProvider.class, method = "getPartidosJudiciales")
	@Results({
		@Result(column = "IDPARTIDO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> getPartidosJudiciales(Short idInstitucion);
	
	@SelectProvider(type=CenPartidasJudicialSqlExtendsProvider.class, method="searchInfluencia")
	@Results({ @Result(column = "IDPARTIDO", property = "idpartido", jdbcType = JdbcType.DECIMAL, id = true),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
		@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
		@Result(column = "IDINSTITUCIONPROPIETARIO", property = "idinstitucionpropietario", jdbcType = JdbcType.DECIMAL),
		@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR) })
	
	List<PartidasJudicialesItem> searchInfluencia(CenInfluencia ejemplo2);

	
}
