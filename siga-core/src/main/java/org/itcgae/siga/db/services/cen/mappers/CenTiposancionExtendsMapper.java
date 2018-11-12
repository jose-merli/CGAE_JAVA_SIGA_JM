package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.CenTiposancionMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenTiposancionExtendsMapper extends CenTiposancionMapper{

	@SelectProvider(type = CenTiposancionSqlExtendsProvider.class, method = "getComboTipoSancion")
	@Results({ @Result(column = "IDTIPOSANCION", property = "value", jdbcType = JdbcType.VARCHAR),
		 		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
			})
	List<ComboItem> getComboTipoSancion(); 
}
