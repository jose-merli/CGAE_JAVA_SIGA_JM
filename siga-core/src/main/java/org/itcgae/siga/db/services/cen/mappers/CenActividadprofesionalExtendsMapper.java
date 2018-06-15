package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.DatosRegistralesItem;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.CenActividadprofesionalMapper;
import org.itcgae.siga.db.services.cen.providers.CenActividadprofesionalSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenActividadprofesionalExtendsMapper extends CenActividadprofesionalMapper{
	
	@SelectProvider(type = CenActividadprofesionalSqlExtendsProvider.class, method = "selectProfesionalActivities")
	@Results({
		@Result(column = "IDACTIVIDADPROFESIONAL", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> selectProfesionalActivities(String idLenguaje);
	
	
	
	@SelectProvider(type = CenActividadprofesionalSqlExtendsProvider.class, method = "searchRegistryDataLegalPerson")
	@Results({
		@Result(column = "RESENA", property = "resenia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "OBJETOSOCIAL", property = "objetoSocial", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOPOLIZA", property = "numeroPoliza", jdbcType = JdbcType.VARCHAR),
		@Result(column = "COMPANIASEG", property = "companiaSeg", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAFIN", property = "fechaFin", jdbcType = JdbcType.DATE),
		@Result(column = "FECHANACIMIENTO", property = "fechaConstitucion", jdbcType = JdbcType.DATE),
		@Result(column = "SOCIEDADPROFESIONAL", property = "sociedadProfesional", jdbcType = JdbcType.VARCHAR)
	})
	List<DatosRegistralesItem> searchRegistryDataLegalPerson(PersonaJuridicaSearchDTO personaJuridicaSearchDTO);
	
	
}
