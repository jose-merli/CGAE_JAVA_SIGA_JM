package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.GuardiasItem;
import org.itcgae.siga.DTOs.scs.IncompatibilidadesDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.IncompatibilidadesItem;
import org.itcgae.siga.db.mappers.ScsIncompatibilidadguardiasMapper;
import org.itcgae.siga.db.services.scs.providers.ScsIncompatibilidadguardiasSqlExtendsProvider;

public interface ScsIncompatibilidadguardiasExtendsMapper extends ScsIncompatibilidadguardiasMapper{
	

	@SelectProvider(type = ScsIncompatibilidadguardiasSqlExtendsProvider.class, method = "tarjetaIncompatibilidades")
	@Results({ 
		@Result(column = "TURNO", property = "turno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "GUARDIA", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SELECCIONLABORABLES", property = "seleccionLaborables", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SELECCIONFESTIVOS", property = "seleccionFestivos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "MOTIVOS", property = "descripcion", jdbcType = JdbcType.VARCHAR)
	})
	List<GuardiasItem> tarjetaIncompatibilidades(String idGuardia, String idTurno, String idInstitucion);
	
	@SelectProvider(type = ScsIncompatibilidadguardiasSqlExtendsProvider.class, method = "resumenIncompatibilidades")
	@Results({ 
		@Result(column = "TOTAL_INCOMPATIBILIDADES", property = "incompatibilidades", jdbcType = JdbcType.VARCHAR),
	})
	List<GuardiasItem> resumenIncompatibilidades(GuardiasItem guardia, String idInstitucion);
	
	@SelectProvider(type = ScsIncompatibilidadguardiasSqlExtendsProvider.class, method = "resumenIncompatibilidades2")
	@Results({ 
		@Result(column = "TURNO", property = "turno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "GUARDIA", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SELECCIONLABORABLES", property = "seleccionLaborables", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SELECCIONFESTIVOS", property = "seleccionFestivos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "MOTIVOS", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DIASSEPARACIONGUARDIAS", property = "diasSeparacionGuardias", jdbcType = JdbcType.DECIMAL)
		
	})
	List<GuardiasItem> resumenIncompatibilidades2(GuardiasItem guardia, String idInstitucion);
	
	
	@SelectProvider(type = ScsIncompatibilidadguardiasSqlExtendsProvider.class, method = "listadoIncompatibilidades")
	@Results({ 
		@Result(column = "EXISTE", property = "existe", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TURNO", property = "nombreTurno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "GUARDIA", property = "nombreGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TURNO_INCOMPATIBLE", property = "nombreTurnoIncompatible", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTURNO_INCOMPATIBLE", property = "idTurnoIncompatible", jdbcType = JdbcType.VARCHAR),
		@Result(column = "GUARDIA_INCOMPATIBLE", property = "nombreGuardiaIncompatible", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGUARDIA_INCOMPATIBLE", property = "idGuardiaIncompatible", jdbcType = JdbcType.VARCHAR),
		@Result(column = "MOTIVOS", property = "motivos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DIASSEPARACIONGUARDIAS", property = "diasSeparacionGuardias", jdbcType = JdbcType.VARCHAR)

	})
	List<IncompatibilidadesItem> getListadoIncompatibilidades(IncompatibilidadesDatosEntradaItem incompatibilidades, String idInstitucion, Integer tamMaximo);
	
	@SelectProvider(type = ScsIncompatibilidadguardiasSqlExtendsProvider.class, method = "deleteIncompatibilidades")
	void deleteIncompatibilidades(String idTurno, String idInstitucion, String idGuardia, String idTurnoIncompatible, String idGuardiaIncompatible);
		
	
	@SelectProvider(type = ScsIncompatibilidadguardiasSqlExtendsProvider.class, method = "deleteCalendarioProgramado1")
	void deleteCalendarioProgramado1(String idTurno, String idInstitucion, String idGuardia, String idCalendarioProgramado);
	
	@SelectProvider(type = ScsIncompatibilidadguardiasSqlExtendsProvider.class, method = "deleteCalendarioProgramado2")
	void deleteCalendarioProgramado2(String idTurno, String idInstitucion, String idGuardia, String idCalendarioProgramado);
	
	@SelectProvider(type = ScsIncompatibilidadguardiasSqlExtendsProvider.class, method = "saveIncompatibilidades")

	void saveListadoIncompatibilidades(int idTurno, int idInstitucion, int idGuardia, int idTurnoIncompatible, int idGuardiaIncompatible, int usuario, String motivos, int diasSeparacionGuardias, String fechaModificacion);
		
	@SelectProvider(type = ScsIncompatibilidadguardiasSqlExtendsProvider.class, method = "updateIfExists")
	void updateIfExists(String idTurno, String idInstitucion, String idGuardia, String idTurnoIncompatible, String idGuardiaIncompatible, String motivos, String diasSeparacionGuardia, String fecha);
		
	@SelectProvider(type = ScsIncompatibilidadguardiasSqlExtendsProvider.class, method = "checkIncompatibilidadesExists")
	int checkIncompatibilidadesExists(String idTurno, String idInstitucion, String idGuardia, String idTurnoIncompatible, String idGuardiaIncompatible);
		
	@SelectProvider(type = ScsIncompatibilidadguardiasSqlExtendsProvider.class, method = "getIdTurnoFromNombreTurno")
	@Results({ 
		@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
		
	})
	String getIdTurnoFromNombreTurno(String nombre);
	
	@SelectProvider(type = ScsIncompatibilidadguardiasSqlExtendsProvider.class, method = "getIdGuardiaFromNombreGuardia")
	@Results({ 
		@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
		
	})
	String getIdGuardiaFromNombreGuardia(String nombre);
	
	
	@SelectProvider(type = ScsIncompatibilidadguardiasSqlExtendsProvider.class, method = "getIdTurnoIncompatibleFromNombreTurno")
	@Results({ 
		@Result(column = "IDTURNO_INCOMPATIBLE", property = "idTurnoIncompatible", jdbcType = JdbcType.VARCHAR),
		
	})
	String getIdTurnoIncompatibleFromNombreTurno(String nombre);
	
	@SelectProvider(type = ScsIncompatibilidadguardiasSqlExtendsProvider.class, method = "getIdGuardiaIncompatibleFromNombreGuardia")

	String getIdGuardiaIncompatibleFromNombreGuardia(String nombre);
	
	
	@SelectProvider(type = ScsIncompatibilidadguardiasSqlExtendsProvider.class, method = "getIdTurnoIncByIdGuardiaInc")

	List<String> getIdTurnoIncByIdGuardiaInc(String idGuardiaInc, String idInstitucion);
	
	@SelectProvider(type = ScsIncompatibilidadguardiasSqlExtendsProvider.class, method = "getListaValueGuardiasInc")
	List<String> getListaValueGuardiasInc(String idInstitucion, String idTipoGuardia, String idTurno, Integer usu, String idPartidaPresupuestaria);
	
	@SelectProvider(type = ScsIncompatibilidadguardiasSqlExtendsProvider.class, method = "getListaLabelsGuardiasInc")
	List<String> getListaLabelsGuardiasInc(String idInstitucion, String idTipoGuardia, String idTurno, Integer usu, String idPartidaPresupuestaria);
	
}	

