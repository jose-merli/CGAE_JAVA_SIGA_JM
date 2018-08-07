package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.CenEstadocivilMapper;
import org.itcgae.siga.db.services.cen.providers.CenEstadocivilSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenEstadocivilExtendsMapper extends CenEstadocivilMapper{

	@SelectProvider(type = CenEstadocivilSqlExtendsProvider.class, method = "distinctCivilStatus")
	@Results({
		@Result(column = "IDESTADOCIVIL", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> distinctCivilStatus(String idLenguaje);
}
