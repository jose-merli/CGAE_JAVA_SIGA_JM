package org.itcgae.siga.db.services.fac.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.FacFacturacionsuscripcionMapper;
import org.itcgae.siga.db.services.fac.providers.FacFacturacionsuscripcionExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface FacFacturacionsuscripcionExtendsMapper extends FacFacturacionsuscripcionMapper {

	@SelectProvider(type = FacFacturacionsuscripcionExtendsSqlProvider.class, method = "getTiposServicios")
	@Results({
		@Result(column = "idtiposervicios", property = "value", jdbcType = JdbcType.NUMERIC),
		@Result(column = "descripcion", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getTiposServicios(String idSerieFacturacion, Short idInstitucion, String idioma);
	
}
