package org.itcgae.siga.db.services.form.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.form.PreciosCursoItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.PysPreciosserviciosMapper;
import org.itcgae.siga.db.services.form.providers.PysPreciosserviciosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface PysPreciosserviciosExtendsMapper extends PysPreciosserviciosMapper{

	@SelectProvider(type = PysPreciosserviciosSqlExtendsProvider.class, method = "selectMaxIdPrecioServicio")
	@Results({
		@Result(column = "IDPRECIOSERVICIO", property = "newId", jdbcType = JdbcType.VARCHAR),
	})
	NewIdDTO selectMaxIdPrecioServicio(Short idInstitucion, Long idServicio, Long idServicioInstitucion, Short idPeriocidad);
	
	@SelectProvider(type = PysPreciosserviciosSqlExtendsProvider.class, method = "selectPricesCourse")
	@Results({
		@Result(column = "PERIOCIDAD", property = "periocidad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VALOR", property = "importe", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PORDEFECTO", property = "porDefecto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR)
	})
	List<PreciosCursoItem> selectPricesCourse(Short idInstitucion, Long idServicio, String idLenguaje, String codigoCurso);


}
