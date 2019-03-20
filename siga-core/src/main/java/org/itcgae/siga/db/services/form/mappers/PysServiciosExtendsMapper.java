package org.itcgae.siga.db.services.form.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.PysServiciosMapper;
import org.itcgae.siga.db.services.form.providers.ForTiposervicioSqlExtendsProvider;
import org.itcgae.siga.db.services.form.providers.PysServiciosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface PysServiciosExtendsMapper extends PysServiciosMapper{

	@SelectProvider(type = PysServiciosSqlExtendsProvider.class, method = "selectMaxIdServicio")
	@Results({
		@Result(column = "IDSERVICIO", property = "newId", jdbcType = JdbcType.VARCHAR),
	})
	NewIdDTO selectMaxIdServicio(Short idInstitucion);
	
	@SelectProvider(type = PysServiciosSqlExtendsProvider.class, method = "selectIdServicioByIdCurso")
	@Results({
		@Result(column = "IDSERVICIO", property = "newId", jdbcType = JdbcType.VARCHAR),
	})
	NewIdDTO selectIdServicioByIdCurso(Short idInstitucion, Long idCurso);

	@SelectProvider(type = PysServiciosSqlExtendsProvider.class, method = "getServicesCourse")
	@Results({
		@Result(column = "IDTIPOSERVICIO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> getServicesCourse(String idInstitucion, String idLenguaje);
	
}
