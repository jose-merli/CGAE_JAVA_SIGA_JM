package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.MateriasItem;
import org.itcgae.siga.db.mappers.ScsMateriaMapper;
import org.itcgae.siga.db.services.scs.providers.ScsMateriaSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsMateriaExtendsMapper extends ScsMateriaMapper{
//
	@SelectProvider(type = ScsMateriaSqlExtendsProvider.class, method = "searchMateria")
	@Results({ @Result(column = "IDAREA", property = "idArea", jdbcType = JdbcType.NUMERIC),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NOMBREMATERIA", property = "nombreMateria", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CONTENIDO", property = "contenido", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDMATERIA", property = "idMateria", jdbcType = JdbcType.VARCHAR), 
			@Result(column = "JURISDICCION", property = "jurisdiccion", jdbcType = JdbcType.VARCHAR) 
	})
	List<MateriasItem> searchMateria(MateriasItem materia);
//	
//	
	@SelectProvider(type = ScsMateriaSqlExtendsProvider.class, method = "getIdMateria")
	@Results({ @Result(column = "IDMATERIA", property = "newId", jdbcType = JdbcType.VARCHAR)
	})
	NewIdDTO getIdMateria(Short idInstitucion);
	
	@SelectProvider(type = ScsMateriaSqlExtendsProvider.class, method = "comboMaterias")
	@Results({ @Result(column = "IDMATERIA", property = "value", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
			 })
	List<ComboItem> comboMaterias(Short idInstitucion,String idArea, String filtro);
	
}
	

