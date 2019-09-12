package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.scs.AreasItem;
import org.itcgae.siga.db.mappers.ScsAreaMapper;
import org.itcgae.siga.db.services.cen.providers.ScsAreasMateriasSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsAreasMateriasExtendsMapper extends ScsAreaMapper{

	@SelectProvider(type = ScsAreasMateriasSqlExtendsProvider.class, method = "searchAreas")
	@Results({ @Result(column = "IDAREA", property = "idArea", jdbcType = JdbcType.NUMERIC),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NOMBRE", property = "nombreArea", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CONTENIDO", property = "contenido", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREMATERIAGRUP", property = "nombreMateria", jdbcType = JdbcType.VARCHAR),
			@Result(column = "JURISDICCIONES", property = "jurisdicciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.DATE)			

	})
	List<AreasItem> searchAreas(AreasItem areasItem);
	
	
	
}
