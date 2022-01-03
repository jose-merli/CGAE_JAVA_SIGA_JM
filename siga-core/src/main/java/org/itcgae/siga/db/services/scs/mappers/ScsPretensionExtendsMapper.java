package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.PretensionItem;
import org.itcgae.siga.db.mappers.ScsPretensionMapper;
import org.itcgae.siga.db.services.scs.providers.ScsPretensionSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsPretensionExtendsMapper extends ScsPretensionMapper{

	@SelectProvider(type = ScsPretensionSqlExtendsProvider.class, method = "searchPretensiones")
	@Results({
		@Result(column = "IDPRETENSION", property = "idPretension", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCIONPRETENSION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOEXT", property = "codigoExt", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDJURISDICCION", property = "idJurisdiccion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCIONJURISDICCION", property = "descripcionJurisdiccion", jdbcType = JdbcType.VARCHAR),

	})
	List<PretensionItem> searchPretensiones(String idLenguaje, PretensionItem pretensionItem);

	@SelectProvider(type = ScsPretensionSqlExtendsProvider.class, method = "getIdPretension")
	@Results({ @Result(column = "IDPRETENSION", property = "newId", jdbcType = JdbcType.VARCHAR)
	
	})
	NewIdDTO getIdPretension(Short idInstitucion);
}
