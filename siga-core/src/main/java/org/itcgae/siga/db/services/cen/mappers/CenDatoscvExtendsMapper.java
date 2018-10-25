package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.FichaDatosCurricularesItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.CenDatoscvMapper;
import org.itcgae.siga.db.services.adm.providers.GenRecursosCatalogosSqlExtendsProvider;
import org.itcgae.siga.db.services.cen.providers.CenDatoscvSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


@Service
@Primary
public interface CenDatoscvExtendsMapper extends CenDatoscvMapper {

	
	@SelectProvider(type = CenDatoscvSqlExtendsProvider.class, method = "searchDatosCurriculares")
	@Results({ @Result(column = "FECHAINICIO", property = "fechaInicio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAFIN", property = "fechaFin", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CATEGORIACURRICULAR", property = "categoriaCurricular", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOSUBTIPO", property = "tipoSubtipo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOCV", property = "idTipoCv", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOCVSUBTIPO1", property = "idTipoCvSubtipo1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOCVSUBTIPO2", property = "idTipoCvSubtipo2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CREDITOS", property = "creditos", jdbcType = JdbcType.VARCHAR)

	})
	List<FichaDatosCurricularesItem> searchDatosCurriculares(String idPersona, String idInstitucion);
	
	
	@SelectProvider(type = CenDatoscvSqlExtendsProvider.class, method = "getMaxIdCv")
	@Results({ @Result(column = "IDCV", property = "newId", jdbcType = JdbcType.VARCHAR)
	
	})
	NewIdDTO getMaxIdCv(String idInstitucion, String idPersona);
}
