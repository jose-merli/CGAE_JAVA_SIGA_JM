package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaItem;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaSearchDTO;
import org.itcgae.siga.DTOs.cen.EtiquetaUpdateDTO;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaItem;
import org.itcgae.siga.DTOs.cen.PersonaJuridicaSearchDTO;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.CenNocolegiadoMapper;
import org.itcgae.siga.db.services.cen.providers.CenNocolegiadoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenNocolegiadoExtendsMapper extends CenNocolegiadoMapper{
	
	
	@SelectProvider(type = CenNocolegiadoSqlExtendsProvider.class, method = "searchLegalPersons")
	@Results({
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DENOMINACION", property = "denominacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ABREVIATURA", property = "abreviatura", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHACONSTITUCION", property = "fechaConstitucion", jdbcType = JdbcType.DATE),
		@Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMEROINTEGRANTES", property = "numeroIntegrantes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRESINTEGRANTES", property = "nombresIntegrantes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHA_BAJA", property = "fechaBaja", jdbcType = JdbcType.DATE), 
		@Result(column = "SOCIEDADPROFESIONAL", property = "sociedadProfesional", jdbcType = JdbcType.VARCHAR)
	})
	List<BusquedaJuridicaItem> searchLegalPersons(BusquedaJuridicaSearchDTO busquedaJuridicaSearchDTO, String idLenguaje, String idInstitucion);
	
	
	
	@SelectProvider(type = CenNocolegiadoSqlExtendsProvider.class, method = "searchHistoricLegalPersons")
	@Results({
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DENOMINACION", property = "denominacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ABREVIATURA", property = "abreviatura", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHACONSTITUCION", property = "fechaConstitucion", jdbcType = JdbcType.DATE),
		@Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMEROINTEGRANTES", property = "numeroIntegrantes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRESINTEGRANTES", property = "nombresIntegrantes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHA_BAJA", property = "fechaBaja", jdbcType = JdbcType.DATE), 
		@Result(column = "SOCIEDADPROFESIONAL", property = "sociedadProfesional", jdbcType = JdbcType.VARCHAR)
	})
	List<BusquedaJuridicaItem> searchHistoricLegalPersons(BusquedaJuridicaSearchDTO busquedaJuridicaSearchDTO, String idLenguaje, String idInstitucion);
	
	
	@SelectProvider(type = CenNocolegiadoSqlExtendsProvider.class, method = "searchGeneralData")
	@Results({
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DENOMINACION", property = "denominacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ABREVIATURA", property = "abreviatura", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHACONSTITUCION", property = "fechaConstitucion", jdbcType = JdbcType.DATE),
		@Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMEROINTEGRANTES", property = "numeroIntegrantes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRESINTEGRANTES", property = "nombresIntegrantes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHA_BAJA", property = "fechaBaja", jdbcType = JdbcType.DATE), 
		@Result(column = "ANOTACIONES", property = "anotaciones", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGRUPO", property = "IDGrupos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SOCIEDADPROFESIONAL", property = "sociedadProfesional", jdbcType = JdbcType.VARCHAR)
	})
	List<PersonaJuridicaItem> searchGeneralData(PersonaJuridicaSearchDTO personaJuridicaSearchDTO);
	
	
	@InsertProvider(type = CenNocolegiadoSqlExtendsProvider.class, method = "insertSelectiveForCreateLegalPerson")
	int insertSelectiveForCreateLegalPerson(String idInstitucion, AdmUsuarios usuario,EtiquetaUpdateDTO etiquetaUpdateDTO);
}
