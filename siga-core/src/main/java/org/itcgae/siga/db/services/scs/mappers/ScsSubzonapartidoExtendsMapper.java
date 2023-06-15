package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.db.services.scs.providers.ScsSubzonapartidoSqlExtendsProvider;

public interface ScsSubzonapartidoExtendsMapper extends ScsSubzonaExtendsMapper{

	@SelectProvider(type = ScsSubzonapartidoSqlExtendsProvider.class, method = "getPartidoJudicialTurno")
	@Results({ 
		@Result(column = "NOMBRE", property = "partidoJudicial", jdbcType = JdbcType.VARCHAR),
	})
	List<TurnosItem> getPartidoJudicialTurno(String idZona, String idSubzona, String idInstitucion);
}
