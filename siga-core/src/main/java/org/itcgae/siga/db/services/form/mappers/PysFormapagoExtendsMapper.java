package org.itcgae.siga.db.services.form.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.db.mappers.PysFormapagoMapper;
import org.itcgae.siga.db.services.form.providers.PysFormapagoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface PysFormapagoExtendsMapper extends PysFormapagoMapper{

	@SelectProvider(type = PysFormapagoSqlExtendsProvider.class, method = "getWayToPay")
	@Results({
		@Result(column = "DESCRIPCION", property = "valor", jdbcType = JdbcType.VARCHAR),
	})
	List<StringDTO> getWayToPay(String idLenguaje);
}
