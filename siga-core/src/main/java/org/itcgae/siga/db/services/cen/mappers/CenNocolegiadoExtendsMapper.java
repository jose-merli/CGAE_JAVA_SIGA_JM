package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaItem;
import org.itcgae.siga.db.mappers.CenNocolegiadoMapper;
import org.itcgae.siga.db.services.cen.providers.CenNocolegiadoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenNocolegiadoExtendsMapper extends CenNocolegiadoMapper{
	
	
	@SelectProvider(type = CenNocolegiadoSqlExtendsProvider.class, method = "searchLegalPersons")
	@Results({
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "LETRACIF", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<BusquedaJuridicaItem> searchLegalPersons();
}
