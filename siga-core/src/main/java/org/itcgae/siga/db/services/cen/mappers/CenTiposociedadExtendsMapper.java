package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.CenTiposociedadMapper;
import org.itcgae.siga.db.services.cen.providers.CenTiposociedadSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenTiposociedadExtendsMapper extends CenTiposociedadMapper{


	@SelectProvider(type = CenTiposociedadSqlExtendsProvider.class, method = "getSocietyTypes")
	@Results({
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "LETRACIF", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getSocietyTypes(String idLenguaje);
	
}
