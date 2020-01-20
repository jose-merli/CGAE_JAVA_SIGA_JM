package org.itcgae.siga.db.services.fcs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.FcsHitogeneralMapper;
import org.itcgae.siga.db.services.fcs.providers.FcsHitoGeneralSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface FcsHitoGeneralExtendsMapper extends FcsHitogeneralMapper {
	
	@SelectProvider(type = FcsHitoGeneralSqlExtendsProvider.class, method = "factConceptos")
	@Results({ 
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDHITOGENERAL", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> factConceptos(String idLenguaje);	
	
}
