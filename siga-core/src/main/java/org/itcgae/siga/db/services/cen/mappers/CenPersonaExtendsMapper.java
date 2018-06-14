package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.BusquedaPerFisicaItem;
import org.itcgae.siga.DTOs.cen.BusquedaPerFisicaSearchDTO;
import org.itcgae.siga.DTOs.cen.BusquedaPerJuridicaItem;
import org.itcgae.siga.DTOs.cen.BusquedaPerJuridicaSearchDTO;
import org.itcgae.siga.DTOs.cen.EtiquetaUpdateDTO;
import org.itcgae.siga.DTOs.cen.FichaPerSearchDTO;
import org.itcgae.siga.DTOs.cen.FichaPersonaItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.services.cen.providers.CenPersonaSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenPersonaExtendsMapper extends CenPersonaMapper{

	@SelectProvider(type = CenPersonaSqlExtendsProvider.class, method = "loadPhotography")
	@Results({
		@Result(column = "NIFCIF", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FOTOGRAFIA", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	ComboItem loadPhotography(String idPersona);
	

	
	@SelectProvider(type = CenPersonaSqlExtendsProvider.class, method = "searchPerFisica")
	@Results({
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "COLEGIO", property = "colegio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR), 
		@Result(column = "APELLIDOS", property = "primerApellido", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NCOLEGIADO", property = "numeroColegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "RESIDENTE", property = "residente", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ESTADOCOLEGIAL", property = "situacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHANACIMIENTO", property = "fechaNacimiento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DENOMINACION", property = "nombre", jdbcType = JdbcType.VARCHAR)	
	})
	List<BusquedaPerFisicaItem> searchPerFisica(BusquedaPerFisicaSearchDTO busquedaPerFisicaSearchDTO, String idLenguaje);
	
	
	@SelectProvider(type = CenPersonaSqlExtendsProvider.class, method = "searchPerJuridica")
	@Results({
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOIDENTIFICACION", property = "idTipoIdentificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DENOMINACION", property = "denominacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ABREVIATURA", property = "abreviatura", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHACONSTITUCION", property = "fechaConstitucion", jdbcType = JdbcType.DATE),
		@Result(column = "SOCIEDADPROFESIONAL", property = "sociedadProfesional", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR), 
		@Result(column = "FECHA_BAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
		@Result(column = "NUMEROINTEGRANTES", property = "numeroIntegrantes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRESINTEGRANTES", property = "nombresIntegrantes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR)
	})
	List<BusquedaPerJuridicaItem> searchPerJuridica(int numpagina, BusquedaPerJuridicaSearchDTO busquedaPerJuridicaSearchDTO, String idLenguaje);
	
		
	@InsertProvider(type = CenPersonaSqlExtendsProvider.class, method = "insertSelectiveForCreateLegalPerson")
	int insertSelectiveForCreateLegalPerson(EtiquetaUpdateDTO etiquetaUpdateDTO, AdmUsuarios usuario);
	
	@SelectProvider(type = CenPersonaSqlExtendsProvider.class, method = "searchPersonFile")
	@Results({
		@Result(column = "COLEGIO", property = "colegio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NCOLEGIADO", property = "numeroColegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "RESIDENTE", property = "residente", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ESTADOCOLEGIAL", property = "situacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAALTA", property = "fechaAlta", jdbcType = JdbcType.DATE)
	})
	List<FichaPersonaItem> searchPersonFile(FichaPerSearchDTO fichaPersona);
	
}
