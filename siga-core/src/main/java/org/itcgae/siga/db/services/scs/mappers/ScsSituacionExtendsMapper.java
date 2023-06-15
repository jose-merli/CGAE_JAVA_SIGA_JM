package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ScsSituacionMapper;
import org.itcgae.siga.db.services.scs.providers.ScsSituacionSqlExtendsProvider;

public interface ScsSituacionExtendsMapper extends ScsSituacionMapper{
	
	@SelectProvider(type= ScsSituacionSqlExtendsProvider.class, method= "comboSituaciones")
	@Results({ 
		@Result(column = "IDSITUACION", property = "value", jdbcType= JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> comboSituaciones(String idLenguaje);

}
