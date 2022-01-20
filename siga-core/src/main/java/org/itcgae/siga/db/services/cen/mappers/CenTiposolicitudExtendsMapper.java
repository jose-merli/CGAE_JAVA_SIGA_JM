package org.itcgae.siga.db.services.cen.mappers;


import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.services.cen.providers.CenTiposolicitudSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
@Service
@Primary
public interface CenTiposolicitudExtendsMapper {


	@SelectProvider(type = CenTiposolicitudSqlExtendsProvider.class, method = "getTipoSolicitud")
	@Results({
		@Result(column = "LABEL", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VALUE", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> selectTipoSolicitud(String idLenguage, String isEXEA);

}