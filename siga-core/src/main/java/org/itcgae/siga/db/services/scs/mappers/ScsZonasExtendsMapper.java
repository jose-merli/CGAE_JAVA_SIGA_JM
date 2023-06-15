package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.ZonasItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.ScsZonaMapper;
import org.itcgae.siga.db.services.scs.providers.ScsZonasSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsZonasExtendsMapper extends ScsZonaMapper{

	@SelectProvider(type = ScsZonasSqlExtendsProvider.class, method = "searchZonas")
	@Results({
		@Result(column = "IDZONA", property = "idzona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "descripcionzona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRESUBZONAS", property = "descripcionsubzona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "partidosjudiciales", property = "descripcionpartido", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idpartidosjudiciales", property = "idPartidosJudiciales", jdbcType = JdbcType.VARCHAR),
		@Result(column = "partidosjudiciales", property = "descripcionpartido", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idsConjuntoSubzonas", property = "idsConjuntoSubzonas", jdbcType = JdbcType.VARCHAR),
	})
	List<ZonasItem> searchZonas(ZonasItem zonasItem, Short idInstitucion);

	@SelectProvider(type = ScsZonasSqlExtendsProvider.class, method = "getIdZona")
	@Results({ @Result(column = "IDZONA", property = "newId", jdbcType = JdbcType.VARCHAR)
	
	})
	NewIdDTO getIdZona(Short idInstitucion);
	
	@SelectProvider(type = ScsZonasSqlExtendsProvider.class, method = "searchGroupZoneByName")
	@Results({
		@Result(column = "IDZONA", property = "idzona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "descripcionzona", jdbcType = JdbcType.VARCHAR),
	})
	List<ZonasItem> searchGroupZoneByName(String idZona, String nombre, Short idInstitucion);
	
	@SelectProvider(type = ScsZonasSqlExtendsProvider.class, method = "searchGroupZoneOnlyByName")
	@Results({
		@Result(column = "IDZONA", property = "idzona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "descripcionzona", jdbcType = JdbcType.VARCHAR),
	})
	List<ZonasItem> searchGroupZoneOnlyByName(String nombre, Short idInstitucion);
}
