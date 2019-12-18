package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.ZonasItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.ScsSubzonaMapper;
import org.itcgae.siga.db.services.scs.providers.ScsSubzonaSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsSubzonaExtendsMapper extends ScsSubzonaMapper{

	@SelectProvider(type = ScsSubzonaSqlExtendsProvider.class, method = "getIdSubzona")
	@Results({ @Result(column = "IDSUBZONA", property = "newId", jdbcType = JdbcType.VARCHAR)
	})
	NewIdDTO getIdSubzona(Short idInstitucion, short idZona);
	
	
	@SelectProvider(type = ScsSubzonaSqlExtendsProvider.class, method = "searchSubzonas")
	@Results({
		@Result(column = "IDZONA", property = "idzona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDSUBZONA", property = "idsubzona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCIONSUBZONA", property = "descripcionsubzona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "JURISDICCION", property = "idpartido", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREPARTIDOSJUDICIALES", property = "nombrePartidosJudiciales", jdbcType = JdbcType.VARCHAR),
	})
	List<ZonasItem> searchSubzonas(String idZona,String idSubZona, Short idInstitucion);
	
}
