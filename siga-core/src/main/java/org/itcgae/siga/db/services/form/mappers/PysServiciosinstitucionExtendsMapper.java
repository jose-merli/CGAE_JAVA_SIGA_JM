package org.itcgae.siga.db.services.form.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.ProductoDetalleDTO;
import org.itcgae.siga.DTO.fac.ServicioDetalleDTO;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.PysServiciosinstitucionMapper;
import org.itcgae.siga.db.services.form.providers.PysProductosinstitucionSqlExtendsProvider;
import org.itcgae.siga.db.services.form.providers.PysServiciosinstitucionSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface PysServiciosinstitucionExtendsMapper extends PysServiciosinstitucionMapper{

	@SelectProvider(type = PysServiciosinstitucionSqlExtendsProvider.class, method = "selectMaxIdServicioinstitucion")
	@Results({
		@Result(column = "IDSERVICIOINSTITUCION", property = "newId", jdbcType = JdbcType.VARCHAR),
	})
	NewIdDTO selectMaxIdServicioinstitucion(Short idInstitucion, Long idServicio);
	
	@SelectProvider(type = PysServiciosinstitucionSqlExtendsProvider.class, method = "selectIdServicioinstitucionByIdServicio")
	@Results({
		@Result(column = "IDSERVICIOSINSTITUCION", property = "newId", jdbcType = JdbcType.VARCHAR),
	})
	NewIdDTO selectIdServicioinstitucionByIdServicio(Short idInstitucion, Long idServicio);
	
	@SelectProvider(type = PysServiciosinstitucionSqlExtendsProvider.class, method = "getIndiceMaxServicio")
	@Results({ 
		@Result(column = "IDSERVICIOSINSTITUCION", property = "newId", jdbcType = JdbcType.NUMERIC)
		}) 
	NewIdDTO getIndiceMaxProducto(ServicioDetalleDTO servicio, Short idInstitucion);

}
