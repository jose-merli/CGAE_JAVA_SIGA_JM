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
		@Result(column = "RESENA", property = "resena", jdbcType = JdbcType.VARCHAR),
		@Result(column = "OBJETOSOCIAL", property = "objetoSocial", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOPOLIZA", property = "numeroPoliza", jdbcType = JdbcType.VARCHAR),
		@Result(column = "COMPANIASEG", property = "companiaAseg", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAFIN", property = "fechaFin", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHANACIMIENTO", property = "fechaConstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SOCIEDADPROFESIONAL", property = "sociedadProfesional", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PREFIJO_NUMSSPP", property = "prefijoNumsspp", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CONTADOR_NUMSSPP", property = "contadorNumsspp", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SUFIJO_NUMSSPP", property = "sufijoNumsspp", jdbcType = JdbcType.VARCHAR)		
	})
	List<DatosRegistralesItem> searchRegistryDataLegalPerson(PersonaJuridicaSearchDTO personaJuridicaSearchDTO);
	
	
}
