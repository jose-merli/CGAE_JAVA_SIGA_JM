package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.BusquedaInscripcionItem;
import org.itcgae.siga.DTOs.scs.InscripcionDatosEntradaDTO;
import org.itcgae.siga.DTOs.scs.InscripcionGuardiaItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.CargaMasivaDatosITItem;
import org.itcgae.siga.DTOs.scs.ModulosItem;
import org.itcgae.siga.db.mappers.ScsInscripcionguardiaMapper;
import org.itcgae.siga.db.services.scs.providers.ScsInscripcionesTurnoSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsInscripcionguardiaSqlExtendsProvider;

public interface ScsInscripcionguardiaExtendsMapper extends ScsInscripcionguardiaMapper{


	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "getColaGuardias")
	@Results({
		@Result(column = "NUMEROGRUPO", property="numeroGrupo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGRUPOGUARDIACOLEGIADO", property = "idGrupoGuardiaColegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ORDENGRUPO", property = "orden", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMEROCOLEGIADO", property = "nColegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAVALIDACION", property = "fechaValidacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "COMPENSACIONES", property = "compensaciones", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SALTOS", property = "saltos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS1", property = "apellido1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS2", property = "apellido2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombre", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "orden_cola", property = "ordenCola", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "GRUPO", property = "idGrupoGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "Fechasuscripcion", property = "fechaSuscripcion", jdbcType = JdbcType.TIMESTAMP),

	})
	List<InscripcionGuardiaItem> getColaGuardias(String idGuardia, String idTurno, String fecha,String ultimo,String ordenaciones, String idInstitucion, String idgrupoguardiacolegiado);


	
	
	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "searchGrupo")
	@Results({
		@Result(column = "NUMEROGRUPO", property = "grupo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ORDEN", property = "orden", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR)
	})
	List<CargaMasivaDatosITItem> searchGrupo(String grupo, Short idInstitucion);
	
	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "searchGrupo")
	@Results({
		@Result(column = "NUMEROGRUPO", property = "grupo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ORDEN", property = "orden", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR)
	})
	List<CargaMasivaDatosITItem> searchGrupoGuardia(Short idInstitucion, String idGuardia);
	
	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "comboGuardiasInscritoLetrado")
	@Results({
		@Result(column = "IDGUARDIA", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> comboGuardiasInscritoLetrado(Short idInstitucion, String idPersona, String idTurno);


	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "listadoInscripciones")
	@Results({ 
		@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL),
		@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
		@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.DECIMAL),
		@Result(column = "NOMBREGUARDIA", property = "nombreGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGUARDIA", property = "idguardia", jdbcType = JdbcType.DECIMAL),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.DATE),
		@Result(column = "OBSERVACIONESBAJA", property = "observacionesbaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHASOLICITUDBAJA", property = "fechasolicitudbaja", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAVALIDACION", property = "fechavalidacion", jdbcType = JdbcType.DATE),
		@Result(column = "OBSERVACIONESVALIDACION", property = "observacionesvalidacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHADENEGACION", property = "fechadenegacion", jdbcType = JdbcType.DATE),
		@Result(column = "OBSERVACIONESDENEGACION", property = "observacionesdenegacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "OBSERVACIONESVALBAJA", property = "observacionesvalbaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHASUSCRIPCION", property = "fechasolicitud", jdbcType = JdbcType.DATE),
	})
	List<BusquedaInscripcionItem> getListadoInscripciones(InscripcionDatosEntradaDTO inscripciones, String idInstitucion);
	
	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "validarInscripciones")
	@Results({		
	})
	String getValidarInscripciones(BusquedaInscripcionItem inscripciones, String idInstitucion);
	
	
	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "denegarInscripciones")
	@Results({		
	})
	String getDenegarInscripciones(BusquedaInscripcionItem inscripciones, String idInstitucion);

}
