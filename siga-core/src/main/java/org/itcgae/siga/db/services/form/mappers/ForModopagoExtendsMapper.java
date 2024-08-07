package org.itcgae.siga.db.services.form.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ForModopagoMapper;
import org.itcgae.siga.db.services.form.providers.ForModopagoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ForModopagoExtendsMapper extends ForModopagoMapper{

	@SelectProvider(type = ForModopagoSqlExtendsProvider.class, method = "getPaymentMode")
	@Results({
		@Result(column = "IDMODOPAGO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> getPaymentMode(String idLenguaje);
}
