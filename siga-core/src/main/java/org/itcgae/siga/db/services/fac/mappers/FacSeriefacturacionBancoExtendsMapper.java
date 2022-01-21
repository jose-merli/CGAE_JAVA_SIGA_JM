package org.itcgae.siga.db.services.fac.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.db.entities.FacSeriefacturacionBanco;
import org.itcgae.siga.db.mappers.FacSeriefacturacionBancoMapper;
import org.itcgae.siga.db.services.fac.providers.FacSeriefacturacionBancoExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface FacSeriefacturacionBancoExtendsMapper extends FacSeriefacturacionBancoMapper {

	@SelectProvider(type = FacSeriefacturacionBancoExtendsSqlProvider.class, method = "getBancosSufijos")
	@Results({
		@Result(column = "bancos_codigo", property = "bancosCodigo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idsufijo", property = "idsufijo", jdbcType = JdbcType.INTEGER)
	})
	List<FacSeriefacturacionBanco> getBancosSufijos(Short idInstitucion);

}
