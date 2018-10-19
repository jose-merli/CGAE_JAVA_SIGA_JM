package org.itcgae.siga.db.services.age.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.AgeCalendarioMapper;
import org.itcgae.siga.db.services.age.providers.AgeCalendarioSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface AgeCalendarioExtendsMapper extends AgeCalendarioMapper{
		
		
	@SelectProvider(type = AgeCalendarioSqlExtendsProvider.class, method = "selectMaxCalendar")
	@Results({
		@Result(column = "IDCALENDARIO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> selectMaxCalendar();
	
}
