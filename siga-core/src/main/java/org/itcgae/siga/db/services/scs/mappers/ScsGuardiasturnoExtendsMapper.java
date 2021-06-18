package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.scs.GuardiasItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.CargaMasivaDatosITItem;
import org.itcgae.siga.DTOs.scs.InscripcionGuardiaItem;
import org.itcgae.siga.DTOs.scs.InscripcionTurnoItem;
import org.itcgae.siga.DTOs.scs.InscripcionesItem;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.db.mappers.ScsGuardiasturnoMapper;
import org.itcgae.siga.db.services.scs.providers.ScsEjgComisionSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsGuardiasturnoSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsPrisionSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTurnosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsGuardiasturnoExtendsMapper extends ScsGuardiasturnoMapper{

	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "searchNombreTurnoGuardia")
	@Results({ 
		@Result(column = "ABREVIATURATURNO", property = "nombreTurno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREGUARDIA", property = "nombreGuardia", jdbcType = JdbcType.VARCHAR)
		})
	List<CargaMasivaDatosITItem> searchNombreTurnoGuardia(String idInstitucion, String nombreGuardia);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "comboGuardias")
	@Results({ @Result(column = "IDGUARDIA", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR), })
	List<ComboItem> comboGuardias(String idTurno, String idInstitucion);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "searchGuardias")
	@Results({ 
		@Result(column = "turno", property = "turno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombre", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "tipodeguardia", property = "tipoGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "obligatoriedad", property = "obligatoriedad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "duracion", property = "duracion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMEROLETRADOSGUARDIA", property = "letradosGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "numeroletradosinscritos", property = "letradosIns", jdbcType = JdbcType.VARCHAR),
		@Result(column = "diaslaborables", property = "seleccionLaborables", jdbcType = JdbcType.VARCHAR),
		@Result(column = "diasfestivos", property = "seleccionFestivos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VALIDARJUSTIFICACIONES", property = "validaJustificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.VARCHAR),
	})
	List<GuardiasItem> searchGuardias(TurnosItem turnosItem , String idInstitucion, String idLenguaje);
	

	
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "comboGuardiasNoGrupo")
	@Results({
		@Result(column = "IDGUARDIA", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> comboGuardiasNoGrupo(String idTurno, String idInstitucion);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "comboGuardiasUpdate")
	@Results({
		@Result(column = "IDGUARDIA", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> comboGuardiasUpdate(String idTurno, String idInstitucion);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getIdGuardia")
	@Results({ @Result(column = "IDGUARDIA", property = "newId", jdbcType = JdbcType.VARCHAR)
	})
	NewIdDTO getIdGuardia();
	
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "busquedaGuardiasCMO")
	@Results({
		@Result(column = "NOMBRE_TURNO", property = "turno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE_GUARDIA", property = "guardia", jdbcType = JdbcType.VARCHAR),
//		@Result(column = "NCOLEGIADO", property = "ncolegiado", jdbcType = JdbcType.VARCHAR),
//		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.VARCHAR),
//		@Result(column = "FECHAVALIDACION", property = "fechavalidacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PORGRUPOS", property = "grupo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDORDENCOLAS", property = "orden", jdbcType = JdbcType.VARCHAR)
		
	})
	List<CargaMasivaDatosITItem> busquedaGuardiasCMO(String turnos, String guardias, Short idInstitucion);
	
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="selectGuardiaTurnoByTurno")
	 @Results({ @Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDPERSONA", property = "idPersonaUltimo", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDINSTITUCION", property = "jurisdiccion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHASUSCRIPCION", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP)})
	    List<GuardiasItem> selectGuardiaTurnoByTurno(Short idInstitucion, String idTurno);
	 
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="selectGuardiaConfiguradasTurno")
	 @Results({ @Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDINSTITUCION", property = "jurisdiccion", jdbcType = JdbcType.DECIMAL)})
	    List<GuardiasItem> selectGuardiaConfiguradasTurno(Short idInstitucion, String idTurno);
}
