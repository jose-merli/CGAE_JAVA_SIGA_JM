package org.itcgae.siga.db.services.age.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.CenInfluenciaMapper;
import org.itcgae.siga.db.services.age.providers.CenInfluenciaSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenInfluenciaExtendsMapper extends CenInfluenciaMapper {
		
		
	@SelectProvider(type = CenInfluenciaSqlExtendsProvider.class, method = "getJudicialDistrict")
	@Results({
		@Result(column = "IDPARTIDO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getJudicialDistrict(String idInstitucion);
	
			
}
