package org.itcgae.siga.db.services.fcs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.FcsPagosjgMapper;
import org.itcgae.siga.db.services.fcs.providers.FcsPagosjgSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface FcsPagosjgExtendsMapper extends FcsPagosjgMapper {

	@SelectProvider(type = FcsPagosjgSqlExtendsProvider.class, method = "comboPagosColegio")
	@Results({ 
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ID", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> comboPagosColegio(String idLenguaje, Short idInstitucion);
	
	
	
}
