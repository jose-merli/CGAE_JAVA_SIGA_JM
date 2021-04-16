package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.CalendariosProgDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.CalendariosProgDatosSalidaItem;
import org.itcgae.siga.DTOs.scs.DatosCalendarioItem;
import org.itcgae.siga.DTOs.scs.DatosCalendarioProgramadoItem;
import org.itcgae.siga.DTOs.scs.GuardiasItem;
import org.itcgae.siga.DTOs.scs.LetradoGuardiaItem;
import org.itcgae.siga.db.mappers.ScsGuardiasturnoMapper;
import org.itcgae.siga.db.services.scs.providers.ScsGuardiasturnoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsGuardiasturnoExtendsMapper extends ScsGuardiasturnoMapper {

	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "searchGuardias")
	@Results({ @Result(column = "turno", property = "turno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombre", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "tipodeguardia", property = "idTipoGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "obligatoriedad", property = "obligatoriedad", jdbcType = JdbcType.VARCHAR),
			@Result(column = "duracion", property = "duracion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROLETRADOSGUARDIA", property = "letradosGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numeroletradosinscritos", property = "letradosIns", jdbcType = JdbcType.VARCHAR),
			@Result(column = "diaslaborables", property = "seleccionLaborables", jdbcType = JdbcType.VARCHAR),
			@Result(column = "diasfestivos", property = "seleccionFestivos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "VALIDARJUSTIFICACIONES", property = "validaJustificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.VARCHAR), })
	List<GuardiasItem> searchGuardias(GuardiasItem guardiaItem, String idInstitucion, String idLenguaje,
			Integer tamMax);

	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "comboGuardias")
	@Results({ @Result(column = "IDGUARDIA", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR), })
	List<ComboItem> comboGuardias(String idTurno, String idInstitucion);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "comboListasGuardias")
	@Results({ @Result(column = "IDLISTA", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR), })
	List<ComboItem> comboListasGuardias(String idInstitucion);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "comboConjuntoGuardias")
	@Results({ @Result(column = "IDCONJUNTOGUARDIA", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR), })
	List<ComboItem> comboConjuntoGuardias(String idInstitucion);

	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getIdGuardia")
	@Results({ @Result(column = "IDGUARDIA", property = "newId", jdbcType = JdbcType.VARCHAR) })
	NewIdDTO getIdGuardia();

	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getResumen")
	@Results({ @Result(column = "nombreguardia", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombreturno", property = "turno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "tipoguardia", property = "idTipoGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NLETRADOSINSCRITOS", property = "letradosGuardia", jdbcType = JdbcType.VARCHAR) })
	List<GuardiasItem> getResumen(String idGuardia, String idTurno, String idInstitucion, String idLenguaje);

	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getCalendario")
	@Results({ @Result(column = "FECHAINICIO", property = "fechaDesde", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAFIN", property = "fechaHasta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "GENERADO", property = "generado", jdbcType = JdbcType.VARCHAR) })
	List<DatosCalendarioItem> getCalendario(String idGuardia);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getCalendarioProgramado")
	@Results({ @Result(column = "turno", property = "turno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "guardia", property = "guardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idTurno", property = "idTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idGuardia", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechaDesde", property = "fechaDesde", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechaHasta", property = "fechaHasta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "generado", property = "generado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "observaciones", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechaProgramacion", property = "fechaProgramacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "estado", property = "estado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "listaGuardias", property = "listaGuardias", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idCalG", property = "idCalG", jdbcType = JdbcType.INTEGER),
			@Result(column = "numGuardias", property = "numGuardias", jdbcType = JdbcType.INTEGER),
			@Result(column = "idCalendarioProgramado", property = "idCalendarioProgramado", jdbcType = JdbcType.VARCHAR)})
	
	List<DatosCalendarioProgramadoItem> getCalendarioProgramado(CalendariosProgDatosEntradaItem obj, String idIns);
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getGuardiaCalProg")
	@Results({
			@Result(column = "guardia", property = "guardia", jdbcType = JdbcType.VARCHAR)})
	
	String getGuardiaCalProg( String idTurno, String idGuardia, String idCalG, String idInstitucion);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getObservacionesCalendario")
	@Results({
			@Result(column = "OBSERVACIONES", property = "OBSERVACIONES", jdbcType = JdbcType.VARCHAR)})
	List<String> getObservacionesCalendario(String idGuardia, String idTurno, String idInstitucion, String fechaIni, String fechaFin);
		
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getTurnoCalProg")
	@Results({ @Result(column = "turno", property = "turno", jdbcType = JdbcType.VARCHAR)})
	
	String getTurnoCalProg( String idTurno, String idCalG, String idInstitucion);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getNumGuardiasCalProg")
	@Results({ @Result(column = "numGuardias", property = "numGuardias", jdbcType = JdbcType.INTEGER)})
	String getNumGuardiasCalProg(String idCalG);

	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "resumenConfCola")
	@Results({
			// Se están usando atributos de GuardiasItem de forma temporal para no tener que
			// crear
			// un objeto de solo un uso.
			@Result(column = "ordenacion", property = "idOrdenacionColas", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROLETRADOSGUARDIA", property = "letradosIns", jdbcType = JdbcType.VARCHAR), })
	List<GuardiasItem> resumenConfCola(String idGuardia, String idTurno, String idInstitucion);

	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getGuardiasVinculadas")
	@Results({
			// Se están usando atributos de GuardiasItem de forma temporal para no tener que
			// crear
			// un objeto de solo un uso.
			@Result(column = "guardiavinculada", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "turnovinculado", property = "turno", jdbcType = JdbcType.VARCHAR), })
	List<GuardiasItem> getGuardiasVinculadas(String idGuardia, String idTurno, String idInstitucion);

	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getGuardiaPrincipal")
	@Results({
			// Se están usando atributos de GuardiasItem de forma temporal para no tener que
			// crear
			// un objeto de solo un uso.
			@Result(column = "guardiaprincipal", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "turnoprincipal", property = "turno", jdbcType = JdbcType.VARCHAR), })
	List<GuardiasItem> getGuardiaPrincipal(String idGuardiaPrincipal, String idTurnoPrincipal, String idInstitucion);

	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "searchLetradosGuardia")
	@Results({ @Result(column = "ACTIVO", property = "activo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS1", property = "apellidos1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS2", property = "apellidos2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ALFABETICOAPELLIDOS", property = "alfabeticoApellidos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROCOLEGIADO", property = "numeroColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDGRUPOGUARDIACOLEGIADO", property = "idGrupoGuardiaColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "GRUPO", property = "grupo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROGRUPO", property = "numeroGrupo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ORDENGRUPO", property = "ordenGrupo", jdbcType = JdbcType.VARCHAR) })
	List<LetradoGuardiaItem> searchLetradosGuardia(String idInstitucion, String idTurno, String idGuardia);

	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "searchCalendarios")
	@Results({ @Result(column = "turno", property = "turno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombre", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "lugar", property = "lugar", jdbcType = JdbcType.VARCHAR),
			@Result(column = "observaciones", property = "observaciones", jdbcType = JdbcType.VARCHAR) })
	List<CalendariosProgDatosSalidaItem> searchCalendarios(CalendariosProgDatosEntradaItem calendarioItem, String idInstitucion);
//	
//	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "separarGuardias")
//	@Results({
//		@Result(column = "DIASAPLICABLES", property = "value", jdbcType = JdbcType.VARCHAR)
//	})
//	List<String> separarGuardias(String idGuardia, String idTurno, String idInstitucion);
//	

}
