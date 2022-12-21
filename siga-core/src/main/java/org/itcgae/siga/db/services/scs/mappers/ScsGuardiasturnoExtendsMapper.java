package org.itcgae.siga.db.services.scs.mappers;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.scs.GuardiasItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.BajasTemporalesItem;
import org.itcgae.siga.DTOs.scs.CabeceraGuardiasCalendarioItem;
import org.itcgae.siga.DTOs.scs.CalendariosProgDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.CalendariosProgDatosSalidaItem;
import org.itcgae.siga.DTOs.scs.CargaMasivaDatosITItem;
import org.itcgae.siga.DTOs.scs.CenPersonaItem;
import org.itcgae.siga.DTOs.scs.DatosCalendarioItem;
import org.itcgae.siga.DTOs.scs.DatosCalendarioProgramadoItem;
import org.itcgae.siga.DTOs.scs.FechaSeparacionItem;
import org.itcgae.siga.DTOs.scs.GrupoGuardiaRowItem;
import org.itcgae.siga.DTOs.scs.GuardiaCalendarioItem;
import org.itcgae.siga.DTOs.scs.GuardiasCalendarioItem;
import org.itcgae.siga.DTOs.scs.GuardiasTurnoItem;
import org.itcgae.siga.DTOs.scs.HcoConfProgCalendariosItem;
import org.itcgae.siga.DTOs.scs.InscripcionGuardiaItem;
import org.itcgae.siga.DTOs.scs.InscripcionTurnoItem;
import org.itcgae.siga.DTOs.scs.LetradoGuardiaItem;
import org.itcgae.siga.DTOs.scs.RangoFechasItem;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaGrupoItem;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaItem;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.CenBajastemporales;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.ScsGrupoguardiacolegiado;
import org.itcgae.siga.db.entities.ScsGuardiasturno;
import org.itcgae.siga.db.entities.ScsOrdenacioncolas;
import org.itcgae.siga.db.entities.ScsSaltoscompensaciones;
import org.itcgae.siga.db.mappers.ScsGuardiasturnoMapper;
import org.itcgae.siga.db.services.scs.providers.ScsEjgComisionSqlExtendsProvider;
import org.itcgae.siga.db.services.form.providers.PysServiciosinstitucionSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsDesignacionesSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsGuardiasturnoSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsInscripcionguardiaSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsSaltoscompensacionesSqlExtendsProvider;
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
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "searchNombreTurnoGuardiaNoAbrev")
	@Results({
		@Result(column = "NOMBRETURNO", property = "nombreTurno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREGUARDIA", property = "nombreGuardia", jdbcType = JdbcType.VARCHAR)
		})
	List<CargaMasivaDatosITItem> searchNombreTurnoGuardiaNoAbrev(String idInstitucion, String nombreGuardia);


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
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "searchGuardias2")
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
	List<org.itcgae.siga.DTOs.scs.GuardiasItem> searchGuardias2(org.itcgae.siga.DTOs.scs.GuardiasItem guardiaItem, String idInstitucion, String idLenguaje,
			Integer tamMax);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getGuardiaColeg")
	@Results({ @Result(column = "turno", property = "turno", jdbcType = JdbcType.VARCHAR),
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
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.VARCHAR), })
	List<org.itcgae.siga.DTOs.scs.GuardiasItem> getGuardiaColeg(org.itcgae.siga.DTOs.scs.GuardiasItem guardiaItem, String idInstitucion, String idLenguaje);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "comboGuardiasNoBaja")
	@Results({ @Result(column = "IDGUARDIA", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR), })
	List<ComboItem> comboGuardiasNoBaja(String idTurno, String idInstitucion);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "comboGuardiasNoBajaNoExistentesEnListaGuardias")
	@Results({ @Result(column = "IDGUARDIA", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR), })
	List<ComboItem> comboGuardiasNoBajaNoExistentesEnListaGuardias(String idTurno, String idListaGuardias, String idInstitucion);

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
	List<org.itcgae.siga.DTOs.scs.GuardiasItem> getResumen(String idGuardia, String idTurno, String idInstitucion, String idLenguaje);

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
	
	
	
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getAllCalendariosProgramadosSigaClassiquePendiente")
	
	@Results({ @Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "turno", property = "turno", jdbcType = JdbcType.VARCHAR),
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
			@Result(column = "idCalendarioProgramado", property = "idCalendarioProgramado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SOLOGENERARVACIO", property = "soloGenerarVacio", jdbcType = JdbcType.CHAR),
			@Result(column = "observaciones", property = "observaciones", jdbcType = JdbcType.VARCHAR)})
	
	List<DatosCalendarioProgramadoItem> getAllCalendariosProgramadosSigaClassiquePendiente();
	
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getCalendariosProgramadosSigaClassique")
	@Results({ @Result(column = "institucion", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "turno", property = "turno", jdbcType = JdbcType.VARCHAR),
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
			@Result(column = "idCalendarioProgramado", property = "idCalendarioProgramado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "observaciones", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "facturado", property = "facturado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "asistenciasAsociadas", property = "asistenciasAsociadas", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CONTADORGENERADOS", property = "contadorGenerados", jdbcType = JdbcType.NUMERIC),
			@Result(column = "SOLOGENERARVACIO", property = "soloGenerarVacio", jdbcType = JdbcType.CHAR),
			@Result(column = "estadoProgramacion", property = "estadoProgramacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDCALENDARIOGUARDIAS", property = "idCalendarioGuardia", jdbcType = JdbcType.VARCHAR)})
	
	List<DatosCalendarioProgramadoItem> getCalendariosProgramadosSigaClassique(CalendariosProgDatosEntradaItem obj, String idIns);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getLastCalendariosProgramadosSigaClassique")
	@Results({ @Result(column = "institucion", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "turno", property = "turno", jdbcType = JdbcType.VARCHAR),
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
			@Result(column = "idCalendarioProgramado", property = "idCalendarioProgramado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "observaciones", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "facturado", property = "facturado", jdbcType = JdbcType.CHAR),
			@Result(column = "asistenciasAsociadas", property = "asistenciasAsociadas", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechaModificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDCALENDARIOGUARDIAS", property = "idCalendarioGuardia", jdbcType = JdbcType.VARCHAR)})
	
	List<DatosCalendarioProgramadoItem> getLastCalendariosProgramadosSigaClassique(CalendariosProgDatosEntradaItem calendarioItem, String idInstitucion);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getFechasProgramacionFromIdConjuntoGuardia")
	@Results({ 
			@Result(column = "fechaDesde", property = "fechaDesde", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechaHasta", property = "fechaHasta", jdbcType = JdbcType.VARCHAR)})
	
	List<RangoFechasItem> getFechasProgramacionFromIdConjuntoGuardia(String idConjunto, String idIns);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getConjuntoGuardiasIdFromGuardiaId")
	@Results({ 
			@Result(column = "IDCONJUNTOGUARDIA", property = "IDCONJUNTOGUARDIA", jdbcType = JdbcType.VARCHAR)})
	
	List<String> getConjuntoGuardiasIdFromGuardiaId(String idGuardia, String idIns);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getGuardiasFromCalendarProg")
	@Results({ @Result(column = "TURNO", property = "turno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "GUARDIA", property = "guardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "GENERADO", property = "generado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCALENDARIOGUARDIAS", property = "idCalendarioGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR),
		})
	List<GuardiaCalendarioItem>getGuardiasFromCalendar(String idCalendar, String idInstitucion, String fechaDesde, String fechaHasta);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getIdCalendarioGuardiasFromTurnosGuardiasList")
	@Results({ 
		@Result(column = "IDCALENDARIOGUARDIAS", property = "idCalendarioGuardia", jdbcType = JdbcType.VARCHAR)
		})
	List<String> getIdCalendarioGuardiasFromTurnosGuardiasList(String turnos, String guardias, String idInstitucion, String fechaDesde, String fechaHasta);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getNumGuardiasFromCalendarProg")
	@Results({ @Result(column = "NUMGUARDIA", property = "NUMGUARDIA", jdbcType = JdbcType.VARCHAR)
			})
	String getNumGuardiasFromCalendarProg(String idCalendar, String idInstitucion);
	
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getGuardiaCalProg")
	@Results({
			@Result(column = "guardia", property = "guardia", jdbcType = JdbcType.VARCHAR)})
	
	String getGuardiaCalProg( String idTurno, String idGuardia, String idCalG, String idInstitucion);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getObservacionesCalendario")
	@Results(
			)
	List<String> getObservacionesCalendario(String idGuardia, String idTurno, String idInstitucion, String fechaIni, String fechaFin);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "setObservaciones")
	@Results(
			)
	String setObservaciones(String idInstitucion, String idCG, String observaciones, String idGuardia, String idTurno, String fechaIni, String fechaFin);
		
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "setObservaciones2")
	@Results(
			)
	String setObservaciones2(String idInstitucion, String idCG, String observaciones, String fechaIni, String fechaFin);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getTurnoCalProg")
	@Results({ @Result(column = "turno", property = "turno", jdbcType = JdbcType.VARCHAR)})
	
	String getTurnoCalProg( String idTurno, String idCalG, String idInstitucion);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getNumGuardiasCalProg")
	@Results({ @Result(column = "numGuardias", property = "numGuardias", jdbcType = JdbcType.INTEGER)})
	String getNumGuardiasCalProg(String idCalG, String idCalendario, String idInstitucion);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getGuardiasAsociadasCalProg")
	@Results()
	List<String> getGuardiasAsociadasCalProg(String idCalG, String idCalendario, String idInstitucion);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getFacturada")
	@Results()
	List<String> getFacturada(String idGuardia);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getAsistencias")
	@Results({ @Result(column = "numasistencias", property = "numasistencias", jdbcType = JdbcType.INTEGER)})
	Integer getAsistencias(String idGuardia);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getNumGuardiasCalProg2")
	@Results({ @Result(column = "numGuardias", property = "numGuardias", jdbcType = JdbcType.INTEGER)})
	String getNumGuardiasCalProg2(String idInstitucion, String idTurno, String idGuardia, String idCalendarioGuardias);

	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "resumenConfCola")
	@Results({
		
			// Se están usando atributos de GuardiasItem de forma temporal para no tener que
			// crear
			// un objeto de solo un uso.
			@Result(column = "ordenacion", property = "idOrdenacionColas", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROLETRADOSGUARDIA", property = "letradosIns", jdbcType = JdbcType.VARCHAR), })
	List<org.itcgae.siga.DTOs.scs.GuardiasItem> resumenConfCola(String idGuardia, String idTurno, String idInstitucion);

	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getGuardiasVinculadas")
	@Results({
			// Se están usando atributos de GuardiasItem de forma temporal para no tener que
			// crear
			// un objeto de solo un uso.
			@Result(column = "guardiavinculada", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "turnovinculado", property = "turno", jdbcType = JdbcType.VARCHAR), })
	List<org.itcgae.siga.DTOs.scs.GuardiasItem> getGuardiasVinculadas(String idGuardia, String idTurno, String idInstitucion);

	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getGuardiaPrincipal")
	@Results({
			// Se están usando atributos de GuardiasItem de forma temporal para no tener que
			// crear
			// un objeto de solo un uso.
			@Result(column = "guardiaprincipal", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "turnoprincipal", property = "turno", jdbcType = JdbcType.VARCHAR), })
	List<org.itcgae.siga.DTOs.scs.GuardiasItem> getGuardiaPrincipal(String idGuardiaPrincipal, String idTurnoPrincipal, String idInstitucion);

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
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "searchLetradosInscripcion")
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
	List<LetradoGuardiaItem> searchLetradosInscripcion(String idInstitucion, String idGuardia);

	
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

	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "comboGuardiasNoGrupo")
	@Results({
		@Result(column = "IDGUARDIA", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> comboGuardiasNoGrupo(String idTurno, String idInstitucion);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "comboGuardiasGrupo")
	@Results({
		@Result(column = "IDGUARDIA", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> comboGuardiasGrupo(String idTurno, String idInstitucion);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "comboTurnosGuardiasGrupo")
	@Results({
		@Result(column = "IDTURNO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> comboTurnosGuardiasGrupo(String idInstitucion);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "comboGuardiasUpdate")
	@Results({
		@Result(column = "IDGUARDIA", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> comboGuardiasUpdate(String idTurno, String idInstitucion);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getguardiasFromConjuntoGuardiasId")
	@Results({ @Result(column = "TURNO", property = "turno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "GUARDIA", property = "guardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "GENERADO", property = "generado", jdbcType = JdbcType.VARCHAR)
			})
	List<GuardiaCalendarioItem> getguardiasFromConjuntoGuardiasId(String idConjuntoGuardia, String idInstitucion);
		
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
	 
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="setguardiaInConjuntoGuardias")
	 @Results({})
	 String setguardiaInConjuntoGuardias(String idConjuntoGuardia, String idInstitucion, String today, GuardiaCalendarioItem item,String  usuarioModificacion);
	 
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="setGuardiaInCalendario")
	 @Results({})
	 String setGuardiaInCalendario(String idCalendar, String idConjuntoGuardia, String idInstitucion, String today, GuardiaCalendarioItem item);
	 
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="updateGuardiaInCalendario")
	 @Results({})
	 String updateGuardiaInCalendario(String idCalendar, String idConjuntoGuardia, String idInstitucion, String today, GuardiaCalendarioItem item);
	 
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="deleteguardiaFromConjuntoGuardias")
	 @Results({})
	 String deleteguardiaFromConjuntoGuardias(String idConjuntoGuardia, String idInstitucion, String today, GuardiaCalendarioItem item);
	 
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="deleteguardiaFromLog")
	 @Results({})
	 String deleteguardiaFromLog(String idConjuntoGuardia, String idInstitucion, String today, GuardiaCalendarioItem item);
	 
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="deleteGuardiaFromCalendario")
	 @Results({})
	 String deleteGuardiaFromCalendario(String idCalendar, String idConjuntoGuardia, String idInstitucion, String today, GuardiaCalendarioItem item);
	
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="getConjuntoFromCalendarId")
	 @Results({ @Result(column = "IDCONJUNTOGUARDIA", property = "IDCONJUNTOGUARDIA", jdbcType = JdbcType.VARCHAR)})
	 String getConjuntoFromCalendarId(String idCalendar, String idInstitucion);
	 
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="getConjuntoGuardiaFromGuardiaTurno")
	 @Results({ @Result(column = "IDCONJUNTOGUARDIA", property = "IDCONJUNTOGUARDIA", jdbcType = JdbcType.VARCHAR)})
	 String getConjuntoGuardiaFromGuardiaTurno(String idGuardia, String idTurno, String idInstitucion) ;

	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="getGuardiasToProg")
	 @Results()
	 int getGuardiasToProg(DatosCalendarioProgramadoItem programacion, String idInstitucion);
	 
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="getGuardiasToProgByDates")
	 @Results()
	 int getGuardiasToProgByDates(String fechaDesde, String fechaHasta, String idInstitucion);

	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="updateCalendarioProgramado1")
	 @Results({})
	 
	 String updateCalendarioProgramado1(DatosCalendarioProgramadoItem calendarioItem, String idInstitucion);
	 
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="updateCalendarioProgramado2")
	 @Results({})
	 
	 int updateCalendarioProgramado2(DatosCalendarioProgramadoItem calendarioItem, String idInstitucion);
	 
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="updateCalendarioProgramado3")
	 @Results({})
	 
	 String updateCalendarioProgramado3(DatosCalendarioProgramadoItem calendarioItem, String idInstitucion);
	 
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="updateConfCalendarioProgramado2")
	 @Results({})
	 
	 String updateConfCalendarioProgramado2(DatosCalendarioProgramadoItem calendarioItem, String idInstitucion);
	 
	 @InsertProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="generateCalendarioProgramado")
	 @Results({})
	 int generateCalendarioProgramado(String idCalculado, DatosCalendarioProgramadoItem calendarioItem, String idInstitucion, String today, String usuModif);
	 
	 
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "compruebaSolapamientoProgramamcionesA")
	long compruebaSolapamientoProgramamcionesA(String idTurno, String idGuardia, String fechaINI, String fechaFIN, Short idInstitucion);
	 
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="insertarHistorico")
	 @Results({ @Result(column = "IDPROGCALENDARIO", property = "IDPROGCALENDARIO", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDCONJUNTOGUARDIA", property = "IDCONJUNTOGUARDIA", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDINSTITUCION", property = "IDINSTITUCION", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDTURNO", property = "IDTURNO", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDGUARDIA", property = "IDGUARDIA", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "ORDEN", property = "ORDEN", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "FECHAMODIFICACION", property = "FECHAMODIFICACION", jdbcType = JdbcType.TIMESTAMP),
		 @Result(column = "USUMODIFICACION", property = "USUMODIFICACION", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "ESTADO", property = "ESTADO", jdbcType = JdbcType.DECIMAL)})
	 String insertarHistorico(DatosCalendarioProgramadoItem calendarioItem, String idInstitucion);
	 
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="updateEstado")
	 @Results({})
	 String updateEstado(DatosCalendarioProgramadoItem calendarioItem, String idInstitucion);
	 
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="getNextGuardiaProgramadaNoGenerada")
	 @Results({ @Result(column = "IDPROGCALENDARIO", property = "idprogcalendario", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDCONJUNTOGUARDIA", property = "idconjuntoguardia", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDGUARDIA", property = "idguardia", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "ORDEN", property = "orden", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "NUMEROLETRADOSGUARDIA", property = "numeroletradosguardia", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "NUMEROSUSTITUTOSGUARDIA", property = "numerosustitutosguardia", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DIASGUARDIA", property = "diasguardia", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DIASPAGADOS", property = "diaspagados", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "VALIDARJUSTIFICACIONES", property = "validarjustificaciones", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DIASSEPARACIONGUARDIAS", property = "diasseparacionguardias", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDORDENACIONCOLAS", property = "idordenacioncolas", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "NUMEROASISTENCIAS", property = "numeroasistencias", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DESCRIPCIONFACTURACION", property = "descripcionfacturacion", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DESCRIPCIONPAGO", property = "descripcionpago", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDPARTIDAPRESUPUESTARIA", property = "idpartidapresupuestaria", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "NUMEROACTUACIONES", property = "numeroactuaciones", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDPERSONA_ULTIMO", property = "idpersona_ultimo", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "TIPODIASGUARDIA", property = "tipodiasguardia", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DIASPERIODO", property = "diasperiodo", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "TIPODIASPERIODO", property = "tipodiasperiodo", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "FESTIVOS", property = "festivos", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "SELECCIONLABORABLES", property = "seleccionlaborables", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "SELECCIONFESTIVOS", property = "seleccionfestivos", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDTURNOSUSTITUCION", property = "idturnosustitucion", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDGUARDIASUSTITUCION", property = "idguardiasustitucion", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDTIPOGUARDIA", property = "idtipoguardia", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "PORGRUPOS", property = "porgrupos", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "ROTARCOMPONENTES", property = "rotarcomponentes", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDINSTITUCIONPRINCIPAL", property = "idinstitucionprincipal", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDTURNOPRINCIPAL", property = "idturnoprincipal", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDGUARDIAPRINCIPAL", property = "idguardiaprincipal", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "FECHASUSCRIPCION_ULTIMO", property = "fechasuscripcion_ultimo", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDGRUPOGUARDIA_ULTIMO", property = "idgrupoguardia_ultimo", jdbcType = JdbcType.VARCHAR)})
	 List<HcoConfProgCalendariosItem> getNextGuardiaProgramadaNoGenerada(DatosCalendarioProgramadoItem calendarioItem, String idInstitucion);
	 
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="getCalGuardiavVector")
	 @Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDGUARDIA", property = "idguardia", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDCALENDARIOGUARDIAS", property = "idcalendarioguardias", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "FECHAFIN", property = "fechafin", jdbcType = JdbcType.DATE),
		 @Result(column = "FECHAINICIO", property = "fechainicio", jdbcType = JdbcType.DATE),
		 @Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.DATE),
		 @Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDPERSONA_ULTIMOANTERIOR", property = "idpersona_ultimoanterior", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDGRUPOGUARDIA_ULTIMOANTERIOR", property = "idgrupoguardia_ultimoanterior", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "FECHASUSC_ULTIMOANTERIOR", property = "fechasusc_ultimoanterior", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDTURNOPRINCIPAL", property = "idturnoprincipal", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDGUARDIAPRINCIPAL", property = "idguardiaprincipal", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDCALENDARIOGUARDIASPRINCIPAL", property = "idcalendarioguardiasprincipal", jdbcType = JdbcType.DECIMAL)})
	 List<GuardiasCalendarioItem> getCalGuardiavVector(String idTurno, String idGuardia, String fechaDesde, String fechaHasta, String idInstitucion);

	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="getOneCalGuardia")
	 @Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDGUARDIA", property = "idguardia", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDCALENDARIOGUARDIAS", property = "idcalendarioguardias", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "FECHAFIN", property = "fechafin", jdbcType = JdbcType.DATE),
		 @Result(column = "FECHAINICIO", property = "fechainicio", jdbcType = JdbcType.DATE),
		 @Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.DATE),
		 @Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDPERSONA_ULTIMOANTERIOR", property = "idpersona_ultimoanterior", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDGRUPOGUARDIA_ULTIMOANTERIOR", property = "idgrupoguardia_ultimoanterior", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "FECHASUSC_ULTIMOANTERIOR", property = "fechasusc_ultimoanterior", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDTURNOPRINCIPAL", property = "idturnoprincipal", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDGUARDIAPRINCIPAL", property = "idguardiaprincipal", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDCALENDARIOGUARDIASPRINCIPAL", property = "idcalendarioguardiasprincipal", jdbcType = JdbcType.DECIMAL)})
	 List<GuardiasCalendarioItem> getOneCalGuardia(String idTurno, String idGuardia, String fechaDesde, String fechaHasta, String idInstitucion);

	 
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="cabGuardiavVector")
	 @Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDGUARDIA", property = "idguardia", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDCALENDARIOGUARDIAS", property = "idcalendarioguardias", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "FECHA_FIN", property = "fecha_fin", jdbcType = JdbcType.TIMESTAMP),
		 @Result(column = "FECHAINICIO", property = "fechainicio", jdbcType = JdbcType.TIMESTAMP),
		 @Result(column = "SUSTITUTO", property = "sustituto", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
		 @Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "FACTURADO", property = "facturado", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "PAGADO", property = "pagado", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "VALIDADO", property = "validado", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "LETRADOSUSTITUIDO", property = "letradosustituido", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "FECHASUSTITUCION", property = "fechasustitucion", jdbcType = JdbcType.TIMESTAMP),
		 @Result(column = "COMENSUSTITUCION", property = "comensustitucion", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "FECHAVALIDACION", property = "fechavalidacion", jdbcType = JdbcType.TIMESTAMP),
		 @Result(column = "IDFACTURACION", property = "idfacturacion", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "FECHAALTA", property = "fechaalta", jdbcType = JdbcType.TIMESTAMP),
		 @Result(column = "POSICION", property = "posicion", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "USUALTA", property = "usualta", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "NUMEROGRUPO", property = "numerogrupo", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "OBSERVACIONESANULACION", property = "observacionesanulacion", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDMOVIMIENTO", property = "idmovimiento", jdbcType = JdbcType.DECIMAL)})
	 CabeceraGuardiasCalendarioItem cabGuardiavVector(GuardiasCalendarioItem calendarioItem, String idInstitucion);
	 
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="getIdPersonaUltimoAnterior")
	 @Results({
		 @Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "NUMEROLETRADOSGUARDIA", property = "numeroLetradosGuardia", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "NUMEROSUSTITUTOSGUARDIA", property = "numeroSustitutosGuardia", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DIASGUARDIA", property = "diasGuardia", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DIASPAGADOS", property = "diasPagados", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "VALIDARJUSTIFICACIONES", property = "validarJustificaciones", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DIASSEPARACIONGUARDIAS", property = "diasSeparacionGuardia", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "NUMEROASISTENCIAS", property = "numeroAsistencias", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "NUMEROACTUACIONES", property = "numeroActuaciones", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DESCRIPCIONFACTURACION", property = "descripcionFacturacion", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DESCRIPCIONPAGO", property = "descripcionPago", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDORDENACIONCOLAS", property = "idOrdenacionColas", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDPARTIDAPRESUPUESTARIA", property = "idPartidaPresupuestaria", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDPERSONA_ULTIMO", property = "idPersona_Ultimo", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDGRUPOGUARDIA_ULTIMO", property = "idGrupoGuardiaColegiado_Ultimo", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "FECHASUSCRIPCION_ULTIMO", property = "fechaSuscripcion_Ultimo", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DIASPERIODO", property = "diasPeriodo", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "TIPODIASPERIODO", property = "tipoDiasPeriodo", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "SELECCIONLABORABLES", property = "seleccionLaborables", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "SELECCIONFESTIVOS", property = "seleccionFestivos", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDTURNOSUSTITUCION", property = "idTurnoSustitucion", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDGUARDIASUSTITUCION", property = "idGuardiaSustitucion", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "PORGRUPOS", property = "porGrupos", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "ROTARCOMPONENTES", property = "rotarComponentes", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDINSTITUCIONPRINCIPAL", property = "idInstitucionPrincipal", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDTURNOPRINCIPAL", property = "idTurnoPrincipal", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDGUARDIAPRINCIPAL", property = "idGuardiaPrincipal", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "TIPODIASGUARDIA", property = "tipodiasGuardia", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDTIPOGUARDIA", property = "idTipoGuardiaSeleccionado", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "ENVIOCENTRALITA", property = "envioCentralita", jdbcType = JdbcType.VARCHAR),
	 	})
	 List<GuardiasTurnoItem> getIdPersonaUltimoAnterior(String idTurno, String idGuardia, String idInstitucion);
	 
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="getIdCalendarioGuardias")
	 @Results({@Result(column = "IDCALENDARIOGUARDIAS", property = "IDCALENDARIOGUARDIAS", jdbcType = JdbcType.DECIMAL)})
	 String getIdCalendarioGuardias(String idTurno, String idGuardia, String idInstitucion);
	 
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="getIdCalendarioGuardiasFecha")
	 @Results({@Result(column = "IDCALENDARIOGUARDIAS", property = "IDCALENDARIOGUARDIAS", jdbcType = JdbcType.DECIMAL)})
	 String getIdCalendarioGuardiasFecha(String idTurno, String idGuardia, String idInstitucion, String diaGuardia);
	 
	 @InsertProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="insertarRegistroCalendarioGuardias")
	 @Results({})
	 int insertarRegistroCalendarioGuardias(Integer idTurnoPrincipal, Integer idGuardiaPrincipal, Integer idCalendarioPrincipal, String observaciones, String idTurno, String idGuardia, String fechaHasta, String fechaDesde, String idCalendarioProgramado, String idInstitucion, String idPersonaUltimoAnterior, String today, String fechaSuscUltimoAnterior, String idGrupoUltimoAnterior, String usuModif);

	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="getGuardia")
	 @Results({@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "NUMEROLETRADOSGUARDIA", property = "numeroLetradosGuardia", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "NUMEROSUSTITUTOSGUARDIA", property = "numeroSustitutosGuardia", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DIASGUARDIA", property = "diasGuardia", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DIASPAGADOS", property = "diasPagados", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "VALIDARJUSTIFICACIONES", property = "validarJustificaciones", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DIASSEPARACIONGUARDIAS", property = "diasSeparacionGuardia", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "NUMEROASISTENCIAS", property = "numeroAsistencias", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "NUMEROACTUACIONES", property = "numeroActuaciones", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DESCRIPCIONFACTURACION", property = "descripcionFacturacion", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DESCRIPCIONPAGO", property = "descripcionPago", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDORDENACIONCOLAS", property = "idOrdenacionColas", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDPARTIDAPRESUPUESTARIA", property = "idPartidaPresupuestaria", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDPERSONA_ULTIMO", property = "idPersona_Ultimo", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDGRUPOGUARDIA_ULTIMO", property = "idGrupoGuardiaColegiado_Ultimo", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "FECHASUSCRIPCION_ULTIMO", property = "fechaSuscripcion_Ultimo", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DIASPERIODO", property = "diasPeriodo", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "TIPODIASPERIODO", property = "tipoDiasPeriodo", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "SELECCIONLABORABLES", property = "seleccionLaborables", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "SELECCIONFESTIVOS", property = "seleccionFestivos", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDTURNOSUSTITUCION", property = "idTurnoSustitucion", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDGUARDIASUSTITUCION", property = "idGuardiaSustitucion", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "PORGRUPOS", property = "porGrupos", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "ROTARCOMPONENTES", property = "rotarComponentes", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDINSTITUCIONPRINCIPAL", property = "idInstitucionPrincipal", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDTURNOPRINCIPAL", property = "idTurnoPrincipal", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDGUARDIAPRINCIPAL", property = "idGuardiaPrincipal", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "TIPODIASGUARDIA", property = "tipodiasGuardia", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDTIPOGUARDIA", property = "idTipoGuardiaSeleccionado", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "ENVIOCENTRALITA", property = "envioCentralita", jdbcType = JdbcType.VARCHAR),
	 	})
	 
	 GuardiasTurnoItem getGuardia(String idGuardia, String idTurno, String idInstitucion);
	 
	 
	 
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="getTotalLetrados")
	 @Results({@Result(column = "TOTAL", property = "TOTAL", jdbcType = JdbcType.DECIMAL)})
	 int getTotalLetrados(String idInstitucion, String idCalendarioGuardias, String idTurno, String idGuardia, String today);

	@SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="getTotalGuardias")
	@Results({@Result(column = "TOTAL", property = "TOTAL", jdbcType = JdbcType.DECIMAL)})
	int getTotalGuardias(String idInstitucion, String idCalendarioGuardias, String idTurno, String idGuardia);

	@SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="getTotalColegiadosFacturados")
	@Results({@Result(column = "TOTAL", property = "TOTAL", jdbcType = JdbcType.DECIMAL)})
	int getTotalColegiadosFacturados(String idInstitucion, String idCalendarioGuardias, String idTurno, String idGuardia,String fechaIni, String fechaFin);

	@SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="getTotalColegiadosConAsistencias")
	@Results({@Result(column = "TOTAL", property = "TOTAL", jdbcType = JdbcType.DECIMAL)})
	int getTotalColegiadosConAsistencias(String idInstitucion, String idTurno, String idGuardia,String fechaIni, String fechaFin);

	
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="getGuardiasVnculadas")
	 @Results({@Result(column = "IDINSTITUCION", property = "IDINSTITUCION", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDTURNO", property = "IDTURNO", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDGUARDIA", property = "IDGUARDIA", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "NOMBRE", property = "NOMBRE", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "NUMEROLETRADOSGUARDIA", property = "NUMEROLETRADOSGUARDIA", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "NUMEROSUSTITUTOSGUARDIA", property = "NUMEROSUSTITUTOSGUARDIA", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DIASGUARDIA", property = "DIASGUARDIA", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DIASPAGADOS", property = "DIASPAGADOS", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "VALIDARJUSTIFICACIONES", property = "VALIDARJUSTIFICACIONES", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DIASSEPARACIONGUARDIAS", property = "DIASSEPARACIONGUARDIAS", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "NUMEROASISTENCIAS", property = "NUMEROASISTENCIAS", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "NUMEROACTUACIONES", property = "NUMEROACTUACIONES", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DESCRIPCION", property = "DESCRIPCION", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DESCRIPCIONFACTURACION", property = "DESCRIPCIONFACTURACION", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DESCRIPCIONPAGO", property = "DESCRIPCIONPAGO", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDORDENACIONCOLAS", property = "IDORDENACIONCOLAS", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDPARTIDAPRESUPUESTARIA", property = "IDPARTIDAPRESUPUESTARIA", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDPERSONA_ULTIMO", property = "IDPERSONA_ULTIMO", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDGRUPOGUARDIA_ULTIMO", property = "IDGRUPOGUARDIA_ULTIMO", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "FECHASUSCRIPCION_ULTIMO", property = "FECHASUSCRIPCION_ULTIMO", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DIASPERIODO", property = "DIASPERIODO", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "TIPODIASPERIODO", property = "TIPODIASPERIODO", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "FECHAMODIFICACION", property = "FECHAMODIFICACION", jdbcType = JdbcType.TIMESTAMP),
		 @Result(column = "USUMODIFICACION", property = "USUMODIFICACION", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "SELECCIONLABORABLES", property = "SELECCIONLABORABLES", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "SELECCIONFESTIVOS", property = "SELECCIONFESTIVOS", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDTURNOSUSTITUCION", property = "IDTURNOSUSTITUCION", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDGUARDIASUSTITUCION", property = "IDGUARDIASUSTITUCION", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "PORGRUPOS", property = "PORGRUPOS", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "ROTARCOMPONENTES", property = "ROTARCOMPONENTES", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDINSTITUCIONPRINCIPAL", property = "IDINSTITUCIONPRINCIPAL", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDTURNOPRINCIPAL", property = "IDTURNOPRINCIPAL", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDGUARDIAPRINCIPAL", property = "IDGUARDIAPRINCIPAL", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "TIPODIASGUARDIA", property = "TIPODIASGUARDIA", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDTIPOGUARDIA", property = "IDTIPOGUARDIA", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "ENVIOCENTRALITA", property = "ENVIOCENTRALITA", jdbcType = JdbcType.VARCHAR),
	 	})
	 List<GuardiasTurnoItem> getGuardiasVnculadas(String idInstitucion, String idTurno, String idGuardia);
	 
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="getAllDatosGuardia")
	 @Results({@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "NUMEROLETRADOSGUARDIA", property = "numeroLetradosGuardia", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "NUMEROSUSTITUTOSGUARDIA", property = "numeroSustitutosGuardia", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DIASGUARDIA", property = "diasGuardia", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DIASPAGADOS", property = "diasPagados", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "VALIDARJUSTIFICACIONES", property = "validarJustificaciones", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DIASSEPARACIONGUARDIAS", property = "diasSeparacionGuardia", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "NUMEROASISTENCIAS", property = "numeroAsistencias", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "NUMEROACTUACIONES", property = "numeroActuaciones", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DESCRIPCIONFACTURACION", property = "descripcionFacturacion", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DESCRIPCIONPAGO", property = "descripcionPago", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDORDENACIONCOLAS", property = "idOrdenacionColas", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDPARTIDAPRESUPUESTARIA", property = "idPartidaPresupuestaria", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDPERSONA_ULTIMO", property = "idPersona_Ultimo", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDGRUPOGUARDIA_ULTIMO", property = "idGrupoGuardiaColegiado_Ultimo", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "FECHASUSCRIPCION_ULTIMO", property = "fechaSuscripcion_Ultimo", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DIASPERIODO", property = "diasPeriodo", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "TIPODIASPERIODO", property = "tipoDiasPeriodo", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "SELECCIONLABORABLES", property = "seleccionLaborables", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "SELECCIONFESTIVOS", property = "seleccionFestivos", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDTURNOSUSTITUCION", property = "idTurnoSustitucion", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDGUARDIASUSTITUCION", property = "idGuardiaSustitucion", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "PORGRUPOS", property = "porGrupos", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "ROTARCOMPONENTES", property = "rotarComponentes", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDINSTITUCIONPRINCIPAL", property = "idInstitucionPrincipal", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDTURNOPRINCIPAL", property = "idTurnoPrincipal", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDGUARDIAPRINCIPAL", property = "idGuardiaPrincipal", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "TIPODIASGUARDIA", property = "tipodiasGuardia", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDTIPOGUARDIA", property = "idTipoGuardiaSeleccionado", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "ENVIOCENTRALITA", property = "envioCentralita", jdbcType = JdbcType.VARCHAR),
	 	})
	 List<GuardiasTurnoItem> getAllDatosGuardia(String idInstitucion, String idTurno, String idGuardia);
	 
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="getCalGuardias")
	 @Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDGUARDIA", property = "idguardia", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDCALENDARIOGUARDIAS", property = "idcalendarioguardias", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "FECHAFIN", property = "fechafin", jdbcType = JdbcType.TIMESTAMP),
		 @Result(column = "FECHAINICIO", property = "fechainicio", jdbcType = JdbcType.TIMESTAMP),
		 @Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
		 @Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDPERSONA_ULTIMOANTERIOR", property = "idpersona_ultimoanterior", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDGRUPOGUARDIA_ULTIMOANTERIOR", property = "idgrupoguardia_ultimoanterior", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "FECHASUSC_ULTIMOANTERIOR", property = "fechasusc_ultimoanterior", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDTURNOPRINCIPAL", property = "idturnoprincipal", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDGUARDIAPRINCIPAL", property = "idguardiaprincipal", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDCALENDARIOGUARDIASPRINCIPAL", property = "idcalendarioguardiasprincipal", jdbcType = JdbcType.DECIMAL)})
	 List<GuardiasCalendarioItem> getCalGuardias(String idInstitucion, String idTurno, String idGuardia, String idCalendarioGuardias);
	 
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="getFestivosTurno")
	 @Results({@Result(column = "FECHA", property = "FECHA", jdbcType = JdbcType.TIMESTAMP)})
	 List<String> getFestivosTurno(String fechaInicio, String fechaFin, String idInstitucion1, String idInstitucion2, String idTurno);

	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="getFestivosAgenda")
	 @Results({@Result(column = "FECHAINICIO", property = "fechaDesde", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "FECHAFIN", property = "fechaHasta", jdbcType = JdbcType.VARCHAR),
		 })
	 List<RangoFechasItem> getFestivosAgenda(String fechaInicio, String fechaFin, String idInstitucion);
	 
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="getDiasASeparar")
	 @Results({@Result(column = "DIASAPLICABLES", property = "DIASAPLICABLES", jdbcType = JdbcType.VARCHAR)})
	 List<String> getDiasASeparar(String idGuardia, String idTurno, String idInstitucion, String agrupar);
	 
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="getNextGuardiaConfigurada")
	 @Results({ @Result(column = "IDPROGCALENDARIO", property = "idprogcalendario", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDCONJUNTOGUARDIA", property = "idconjuntoguardia", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDGUARDIA", property = "idguardia", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "ORDEN", property = "orden", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "NUMEROLETRADOSGUARDIA", property = "numeroletradosguardia", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "NUMEROSUSTITUTOSGUARDIA", property = "numerosustitutosguardia", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DIASGUARDIA", property = "diasguardia", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DIASPAGADOS", property = "diaspagados", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "VALIDARJUSTIFICACIONES", property = "validarjustificaciones", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DIASSEPARACIONGUARDIAS", property = "diasseparacionguardias", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDORDENACIONCOLAS", property = "idordenacioncolas", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "NUMEROASISTENCIAS", property = "numeroasistencias", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DESCRIPCIONFACTURACION", property = "descripcionfacturacion", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DESCRIPCIONPAGO", property = "descripcionpago", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDPARTIDAPRESUPUESTARIA", property = "idpartidapresupuestaria", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "NUMEROACTUACIONES", property = "numeroactuaciones", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDPERSONA_ULTIMO", property = "idpersona_ultimo", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "TIPODIASGUARDIA", property = "tipodiasguardia", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "DIASPERIODO", property = "diasperiodo", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "TIPODIASPERIODO", property = "tipodiasperiodo", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "FESTIVOS", property = "festivos", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "SELECCIONLABORABLES", property = "seleccionlaborables", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "SELECCIONFESTIVOS", property = "seleccionfestivos", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDTURNOSUSTITUCION", property = "idturnosustitucion", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDGUARDIASUSTITUCION", property = "idguardiasustitucion", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDTIPOGUARDIA", property = "idtipoguardia", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "PORGRUPOS", property = "porgrupos", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "ROTARCOMPONENTES", property = "rotarcomponentes", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDINSTITUCIONPRINCIPAL", property = "idinstitucionprincipal", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDTURNOPRINCIPAL", property = "idturnoprincipal", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDGUARDIAPRINCIPAL", property = "idguardiaprincipal", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "FECHASUSCRIPCION_ULTIMO", property = "fechasuscripcion_ultimo", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDGRUPOGUARDIA_ULTIMO", property = "idgrupoguardia_ultimo", jdbcType = JdbcType.VARCHAR)})
	 
	 List<HcoConfProgCalendariosItem> getNextGuardiaConfigurada(String idInstitucion, String idProgCalendario);
	 
	 
	 
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="getCalGuardiavVector2")
	 @Results({ @Result(column = "IDINSTITUCION", property = "IDINSTITUCION", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDTURNO", property = "IDTURNO", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDGUARDIA", property = "IDGUARDIA", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDCALENDARIOGUARDIAS", property = "IDCALENDARIOGUARDIAS", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "FECHAFIN", property = "FECHAFIN", jdbcType = JdbcType.TIMESTAMP),
		 @Result(column = "FECHAINICIO", property = "FECHAINICIO", jdbcType = JdbcType.TIMESTAMP),
		 @Result(column = "OBSERVACIONES", property = "OBSERVACIONES", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "FECHAMODIFICACION", property = "FECHAMODIFICACION", jdbcType = JdbcType.TIMESTAMP),
		 @Result(column = "USUMODIFICACION", property = "USUMODIFICACION", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDPERSONA_ULTIMOANTERIOR", property = "IDPERSONA_ULTIMOANTERIOR", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDGRUPOGUARDIA_ULTIMOANTERIOR", property = "IDGRUPOGUARDIA_ULTIMOANTERIOR", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "FECHASUSC_ULTIMOANTERIOR", property = "FECHASUSC_ULTIMOANTERIOR", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDTURNOPRINCIPAL", property = "IDTURNOPRINCIPAL", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDGUARDIAPRINCIPAL", property = "IDGUARDIAPRINCIPAL", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDCALENDARIOGUARDIASPRINCIPAL", property = "IDCALENDARIOGUARDIASPRINCIPAL", jdbcType = JdbcType.DECIMAL)})
	 List<GuardiasCalendarioItem> getCalGuardiavVector2(String idTurno, String idGuardia, String fechaDesde, String fechaHasta, String idInstitucion);
	 
	 
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="cabGuardiavVector")
	 @Results({ @Result(column = "IDINSTITUCION", property = "IDINSTITUCION", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDTURNO", property = "IDTURNO", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDGUARDIA", property = "IDGUARDIA", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDCALENDARIOGUARDIAS", property = "IDCALENDARIOGUARDIAS", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "IDPERSONA", property = "IDPERSONA", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "FECHA_FIN", property = "FECHA_FIN", jdbcType = JdbcType.TIMESTAMP),
		 @Result(column = "FECHAINICIO", property = "FECHAINICIO", jdbcType = JdbcType.TIMESTAMP),
		 @Result(column = "SUSTITUTO", property = "SUSTITUTO", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "FECHAMODIFICACION", property = "FECHAMODIFICACION", jdbcType = JdbcType.TIMESTAMP),
		 @Result(column = "USUMODIFICACION", property = "USUMODIFICACION", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "FACTURADO", property = "FACTURADO", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "PAGADO", property = "PAGADO", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "VALIDADO", property = "VALIDADO", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "LETRADOSUSTITUIDO", property = "LETRADOSUSTITUIDO", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "FECHASUSTITUCION", property = "FECHASUSTITUCION", jdbcType = JdbcType.TIMESTAMP),
		 @Result(column = "COMENSUSTITUCION", property = "COMENSUSTITUCION", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "FECHAVALIDACION", property = "FECHAVALIDACION", jdbcType = JdbcType.TIMESTAMP),
		 @Result(column = "IDFACTURACION", property = "IDFACTURACION", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "FECHAALTA", property = "FECHAALTA", jdbcType = JdbcType.TIMESTAMP),
		 @Result(column = "POSICION", property = "POSICION", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "USUALTA", property = "USUALTA", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "NUMEROGRUPO", property = "NUMEROGRUPO", jdbcType = JdbcType.DECIMAL),
		 @Result(column = "OBSERVACIONESANULACION", property = "OBSERVACIONESANULACION", jdbcType = JdbcType.VARCHAR),
		 @Result(column = "IDMOVIMIENTO", property = "IDMOVIMIENTO", jdbcType = JdbcType.DECIMAL)})
	 List<CabeceraGuardiasCalendarioItem> cabGuardiavVector2(GuardiasCalendarioItem calendarioItem, String idInstitucion);
	 
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="getBajasTemporalesGuardias")
		@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "FECHABT", property = "fechabt", jdbcType = JdbcType.TIMESTAMP, id = true),
			@Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHADESDE", property = "fechadesde", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAHASTA", property = "fechahasta", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAALTA", property = "fechaalta", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.DATE),
			@Result(column = "VALIDADO", property = "validado", jdbcType = JdbcType.CHAR),
			@Result(column = "FECHAESTADO", property = "fechaestado", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "ELIMINADO", property = "eliminado", jdbcType = JdbcType.DECIMAL)})
		List<BajasTemporalesItem> getBajasTemporalesGuardias(String idInstitucion, String idTurno, String idGuardia, String fechaDesde, String fechaHasta);
		
	 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="insertGrupoGuardiaColegiadoCalendario")
	 @Results({})
	String insertGrupoGuardiaColegiadoCalendario(String idCalendarioGuardias, String idTurno, String idInstitucion, String idGuardia);
	 
	 
	 
		@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getSaltosCompensacionesGrupo")
		@Results({ @Result(column = "IDSALTOCOMPENSACIONGRUPO", property = "idSaltoCompensacionGrupo", jdbcType = JdbcType.VARCHAR),
				@Result(column = "IDGRUPOGUARDIA", property = "idGrupoGuardia", jdbcType = JdbcType.VARCHAR),
				@Result(column = "SALTOOCOMPENSACION", property = "saltoCompensacion", jdbcType = JdbcType.VARCHAR),
				@Result(column = "FECHA", property = "fecha", jdbcType = JdbcType.VARCHAR),
				@Result(column = "FECHACUMPLIMIENTO", property = "fechaCumplimiento", jdbcType = JdbcType.VARCHAR),
				@Result(column = "MOTIVO", property = "motivo", jdbcType = JdbcType.VARCHAR),
				@Result(column = "MOTIVOCUMPLIMIENTO", property = "motivoCumplimiento", jdbcType = JdbcType.VARCHAR),
				@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
				@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
				@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
				@Result(column = "IDCALENDARIOGUARDIAS", property = "idCalendarioGuardias", jdbcType = JdbcType.VARCHAR),
				@Result(column = "IDINSTITUCION_CUMPLI", property = "idInstitucion_Cumpli", jdbcType = JdbcType.VARCHAR),
				@Result(column = "IDTURNO_CUMPLI", property = "idTurno_Cumpli", jdbcType = JdbcType.VARCHAR),
				@Result(column = "IDGUARDIA_CUMPLI", property = "idGuardia_Cumpli", jdbcType = JdbcType.VARCHAR),
				@Result(column = "IDCALENDARIOGUARDIAS_CUMPLI", property = "idCalendarioGuardias_Cumpli", jdbcType = JdbcType.VARCHAR),
				@Result(column = "FECHACREACION", property = "fechaCreacion", jdbcType = JdbcType.VARCHAR),
				@Result(column = "USUCREACION", property = "usuCreacion", jdbcType = JdbcType.VARCHAR),
				@Result(column = "FECHAMODIFICACION", property = "fechaModificacion", jdbcType = JdbcType.VARCHAR),
				@Result(column = "USUMODIFICACION", property = "usuModificacion", jdbcType = JdbcType.VARCHAR),
				@Result(column = "IDCALENDARIOGUARDIASCREACION", property = "idGrupoGuardiaCreacion", jdbcType = JdbcType.VARCHAR),
				@Result(column = "TIPOMANUAL", property = "tipoManual", jdbcType = JdbcType.VARCHAR),
				@Result(column = "FECHA_ANULACION", property = "fechaAnulacion", jdbcType = JdbcType.VARCHAR)})
		List<SaltoCompGuardiaGrupoItem>  getSaltosCompensacionesGrupo(String saltoOcompensacion, String idTurno, String idInstitucion, String idGuardia);
		
		@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getLetradosGrupos")
		@Results({
			@Result(column = "IDINSTITUCION", property="idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.DECIMAL),
			@Result(column = "APELLIDOS1", property = "apellido1", jdbcType = JdbcType.DECIMAL),
			@Result(column = "APELLIDOS2", property = "apellido2", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHASUSCRIPCION", property = "fechaSuscripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAVALIDACION", property = "fechaValidacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHASOLICITUDBAJA", property = "fechasolicitudbaja", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHADENEGACION", property = "fechadenegacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OBSERVACIONESSUSCRIPCION", property = "observacionessuscripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OBSERVACIONESVALIDACION", property = "observacionesvalidacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OBSERVACIONESBAJA", property = "observacionesbaja", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OBSERVACIONESDENEGACION", property = "observacionesdenegacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OBSERVACIONESVALBAJA", property = "observacionesvalbaja", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDGRUPOGUARDIACOLEGIADO", property = "idGrupoGuardiaColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROGRUPO", property = "numeroGrupo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ORDENGRUPO", property = "orden", jdbcType = JdbcType.VARCHAR),
		})
		List<InscripcionGuardiaItem> getLetradosGrupos(String fechaGuardia, String idTurno, String idInstitucion, String idGuardia, String idGrupoGuardia);
	 
		
		 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="getUltimoColegiadoGrupo")
			@Results({ 
			@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHASUSCRIPCION", property = "fechaSuscripcion", jdbcType = JdbcType.DATE),
			@Result(column = "IDGRUPOGUARDIACOLEGIADO", property = "idGrupoGuardiaColegiado", jdbcType = JdbcType.NUMERIC)})
		 GrupoGuardiaRowItem getUltimoColegiadoGrupo(String idTurno, String idInstitucion, String idGuardia);

		 @SelectProvider(type=ScsGuardiasturnoSqlExtendsProvider.class, method="updateGuardiasTurno")
		 @Results({})
		 String updateGuardiasTurno(GuardiasTurnoItem item);
		 
		 
			@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getColaGuardia")
			@Results({
				@Result(column = "Activo", property="estado", jdbcType = JdbcType.DECIMAL),
				@Result(column = "IDINSTITUCION", property="idInstitucion", jdbcType = JdbcType.DECIMAL),
				@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.DECIMAL),
				@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.DECIMAL),
				@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.DECIMAL),
				@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.DECIMAL),
				@Result(column = "APELLIDOS1", property = "apellido1", jdbcType = JdbcType.DECIMAL),
				@Result(column = "APELLIDOS2", property = "apellido2", jdbcType = JdbcType.DECIMAL),
				@Result(column = "FECHASUSCRIPCION", property = "fechaSuscripcion", jdbcType = JdbcType.TIMESTAMP),
				@Result(column = "FECHAVALIDACION", property = "fechaValidacion", jdbcType = JdbcType.DATE),
				@Result(column = "FECHASOLICITUDBAJA", property = "fechaSolicitudBaja", jdbcType = JdbcType.DATE),
				@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.DATE),
				@Result(column = "FECHADENEGACION", property = "fechaDenegacion", jdbcType = JdbcType.DATE),
				@Result(column = "OBSERVACIONESSUSCRIPCION", property = "observacionessuscripcion", jdbcType = JdbcType.VARCHAR),
				@Result(column = "OBSERVACIONESVALIDACION", property = "observacionesvalidacion", jdbcType = JdbcType.VARCHAR),
				@Result(column = "OBSERVACIONESBAJA", property = "observacionesbaja", jdbcType = JdbcType.VARCHAR),
				@Result(column = "OBSERVACIONESDENEGACION", property = "observacionesdenegacion", jdbcType = JdbcType.VARCHAR),
				@Result(column = "OBSERVACIONESVALBAJA", property = "observacionesvalbaja", jdbcType = JdbcType.VARCHAR),
				@Result(column = "IDGRUPOGUARDIACOLEGIADO", property = "idGrupoGuardiaColegiado", jdbcType = JdbcType.DECIMAL),
				@Result(column = "GRUPO", property = "idGrupoGuardia", jdbcType = JdbcType.DECIMAL),
				@Result(column = "NUMEROGRUPO", property = "numeroGrupo", jdbcType = JdbcType.DECIMAL),
				@Result(column = "ORDENGRUPO", property = "orden", jdbcType = JdbcType.DECIMAL),
				@Result(column = "NUMEROCOLEGIADO", property = "nColegiado", jdbcType = JdbcType.VARCHAR)
			})
		 List<InscripcionGuardiaItem> getColaGuardia(String fechaInicio, String fechaFin, String idinstitucion, String idturno, String idguardia, String order);
			
			@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getOrdenacionColas")
			@Results({
				@Result(column = "ALFABETICOAPELLIDOS", property="alfabeticoapellidos", jdbcType = JdbcType.VARCHAR),
				@Result(column = "FECHANACIMIENTO", property = "fechanacimiento", jdbcType = JdbcType.VARCHAR),
				@Result(column = "NUMEROCOLEGIADO", property = "numerocolegiado", jdbcType = JdbcType.VARCHAR),
				@Result(column = "ANTIGUEDADCOLA", property = "antiguedadcola", jdbcType = JdbcType.VARCHAR),
				@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.VARCHAR),
				@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.VARCHAR),
				@Result(column = "ORDENACIONMANUAL", property = "ordenacionmanual", jdbcType = JdbcType.VARCHAR),
				@Result(column = "IDORDENACIONCOLAS", property = "idordenacioncolas", jdbcType = JdbcType.VARCHAR)
			})
			List<ScsOrdenacioncolas> getOrdenacionColas(String idOrdenacionColas);
			
			@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getNuevoId")
			@Results({
				@Result(column = "IDSALTOSTURNO", property="IDSALTOSTURNO", jdbcType = JdbcType.VARCHAR)})
			String getNuevoId(String idinstitucion, String idturno);
			
			@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "guardarSaltosCompensacionesGrupo")
			@Results({})
			String guardarSaltosCompensacionesGrupo(SaltoCompGuardiaGrupoItem saltoItem, String idInstitucion, Integer usuario);
			
			@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "nextIdCalprog")
			@Results({
				@Result(column = "ID", property="ID", jdbcType = JdbcType.VARCHAR)})
			String nextIdCalprog();
			
			@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "nextIdSaltoOComp")
			@Results({
				@Result(column = "ID", property="ID", jdbcType = JdbcType.VARCHAR)})
			String nextIdSaltoOComp();
			
			@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "diasSeparacionEntreGuardias")
			@Results({
				@Result(column = "TOTAL", property="TOTAL", jdbcType = JdbcType.VARCHAR)})
			String diasSeparacionEntreGuardias(String idpersona,String idinstitucion, String idturno, String idguardia, Boolean esPermuta, String fechaPeriodoPrimerDiaOriginal, String fechaPeriodoPrimerDia, String fechaPeriodoUltimoDia);
			
			@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "maxFechaInicioPeriodoCabGuardia")
			@Results({
				@Result(column = "MAXIMA", property="MAXIMA", jdbcType = JdbcType.VARCHAR)})
			String maxFechaInicioPeriodoCabGuardia(String idpersona,String idinstitucion, String idturno, String idguardia, Boolean esPermuta, String fechaPeriodoPrimerDiaOriginal, String fechaPeriodoPrimerDia);
				

			@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "minFechaInicioPeriodoCabGuardia")
			@Results({
				@Result(column = "MINIMA", property="MINIMA", jdbcType = JdbcType.VARCHAR)})
			String minFechaInicioPeriodoCabGuardia(String idpersona,String idinstitucion, String idturno, String idguardia, Boolean esPermuta, String fechaPeriodoPrimerDiaOriginal, String fechaPeriodoUltimoDia);

			
			@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "checkIncompatibilidadesCalendario")
			@Results({
				@Result(column = "FECHAFIN", property="FECHAFIN", jdbcType = JdbcType.VARCHAR)})
			String checkIncompatibilidadesCalendario(String idPersona,String idInstitucion, String idTurno, String idGuardia, String fechaGuardia);
			
			@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "checkIncompatibilidadesCalendarioSinBucle")
			@Results({
				@Result(column = "FECHAFIN", property="fechaFin", jdbcType = JdbcType.VARCHAR),
				@Result(column = "DIASSEPARACIONGUARDIAS", property = "diasSeparacion", jdbcType = JdbcType.VARCHAR)})
			List<FechaSeparacionItem> checkIncompatibilidadesCalendarioSinBucle(String idPersona,String idInstitucion, String idTurno, String idGuardia);
			
			@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "updateSaltosCompensacionesGrupo")
			@Results({})
			String updateSaltosCompensacionesGrupo(SaltoCompGuardiaGrupoItem saltoItem, String idInstitucion, Integer usuario);
			
			@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "marcarSaltoCompensacion")
			@Results({})
			String marcarSaltoCompensacion(Integer usuario, String idturno, ScsSaltoscompensaciones saltoCompensacion,String s_idpersona, String s_idinstitucion, String s_idturno, String s_idguardia,String s_saltocompensacion, String fechaCumplimiento);
		
			@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getGrupoData")
			@Results({ @Result(column = "idgrupoguardiacolegiado", property = "idGrupoGuardiaColegiado", jdbcType = JdbcType.NUMERIC),
				@Result(column = "IDGRUPOGUARDIA", property = "idGrupoGuardia", jdbcType = JdbcType.NUMERIC),
				@Result(column = "orden", property = "orden", jdbcType = JdbcType.NUMERIC),}
			)
			List<GrupoGuardiaRowItem> getGrupoData(String idGrupo);
			
			@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getSaltoCompensacionesActivo")
			@Results({
				@Result(column = "IDPERSONA", property = "IDPERSONA", jdbcType = JdbcType.VARCHAR)})
			List<String> getSaltoCompensacionesActivo( String s_idinstitucion, String s_idturno, String s_idguardia, String s_idpersona, String s_saltocompensacion);
			
			@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getPersonaById")
			@Results({ @Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL, id = true),
				@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
				@Result(column = "APELLIDOS1", property = "apellidos1", jdbcType = JdbcType.VARCHAR),
				@Result(column = "APELLIDOS2", property = "apellidos2", jdbcType = JdbcType.VARCHAR),
				@Result(column = "NIFCIF", property = "nifcif", jdbcType = JdbcType.VARCHAR),
				@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
				@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
				@Result(column = "IDTIPOIDENTIFICACION", property = "idtipoidentificacion", jdbcType = JdbcType.DECIMAL),
				@Result(column = "FECHANACIMIENTO", property = "fechanacimiento", jdbcType = JdbcType.TIMESTAMP),
				@Result(column = "IDESTADOCIVIL", property = "idestadocivil", jdbcType = JdbcType.DECIMAL),
				@Result(column = "NATURALDE", property = "naturalde", jdbcType = JdbcType.VARCHAR),
				@Result(column = "FALLECIDO", property = "fallecido", jdbcType = JdbcType.VARCHAR),
				@Result(column = "SEXO", property = "sexo", jdbcType = JdbcType.VARCHAR) })
			List<CenPersonaItem> getPersonaById (String idPersona);
			
			@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getInscripcionesTurnoActiva")
			@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.NUMERIC),
				    @Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.NUMERIC),
				    @Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.NUMERIC),
					@Result(column = "FECHAVALIDACION", property = "fechavalidacion", jdbcType = JdbcType.VARCHAR),
					@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.VARCHAR),
					@Result(column = "FECHASOLICITUD", property = "fechasolicitud", jdbcType = JdbcType.TIMESTAMP),
					@Result(column = "NIFCIF", property = "nifcif", jdbcType = JdbcType.VARCHAR),
					@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
					@Result(column = "APELLIDOS1", property = "apellidos1", jdbcType = JdbcType.VARCHAR),
					@Result(column = "APELLIDOS2", property = "apellidos2", jdbcType = JdbcType.VARCHAR),
					@Result(column = "ALFABETICOAPELLIDOS", property = "alfabeticoapellidos", jdbcType = JdbcType.VARCHAR),
					@Result(column = "NUMEROCOLEGIADO", property = "numerocolegiado", jdbcType = JdbcType.VARCHAR),
				
					@Result(column = "ACTIVO", property = "estado", jdbcType = JdbcType.VARCHAR)
					})
		 	 List<InscripcionTurnoItem> getInscripcionesTurnoActiva(String idPersona,String idInstitucion, String idTurno, String fecha); 
			
			@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getInscripcionesGuardiaActiva")
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
			List<InscripcionGuardiaItem> getInscripcionesGuardiaActiva(String idPersona,String idInstitucion, String idGuardia, String fecha, String idTurno);
			@UpdateProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "cambiarUltimoCola4")
			int cambiarUltimoCola4(String sIdinstitucion, String sIdTurno, String sIdGuardia, String sIdpersona, String sIdGrupoGuardiaColegiado_Ultimo, 
					String sFechaSusc, String usu);
			@UpdateProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "cambiarUltimoCola3")
			int cambiarUltimoCola3(String sIdinstitucion, String sIdTurno, String sIdGuardia, String sIdpersona, String sIdGrupoGuardiaColegiado_Ultimo, 
					String sFechaSusc, String usu);
			@UpdateProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "cambiarUltimoCola2")
			int cambiarUltimoCola2(String sIdinstitucion, String sIdTurno, String sIdGuardia, String sIdpersona, String sIdGrupoGuardiaColegiado_Ultimo, 
					String sFechaSusc, String usu);
			@UpdateProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "cambiarUltimoCola1")
			int cambiarUltimoCola1(String sIdinstitucion, String sIdTurno, String sIdGuardia, String sIdpersona, String sIdGrupoGuardiaColegiado_Ultimo, 
					String sFechaSusc, String usu);
			
			@UpdateProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "actualizarUltimoColegiado")
			int actualizarUltimoColegiado(ScsGuardiasturno guardia);
			
			@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "validaGuardiaLetradoPeriodo")
			@Results({
				@Result(column = "EXISTEGUARDIA", property="EXISTEGUARDIA", jdbcType = JdbcType.DECIMAL)})
			int validaGuardiaLetradoPeriodo(String idPersona, String idInstitucion, String idTurno, String idGuardia, String fechaInicio, String fechaFin);
			
			@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getInstitucionParam")
			@Results({
				@Result(column = "IDINSTITUCION", property="IDINSTITUCION", jdbcType = JdbcType.VARCHAR)})
			List<String> getInstitucionParam(String idModulo, String idParametro);
			
			@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "reordenarRestoGrupoLetrados")
			@Results({
				@Result(column = "idgrupoguardia", property="idGrupoGuardia", jdbcType = JdbcType.NUMERIC),
				@Result(column = "idgrupoguardiacolegiado", property = "idGrupoGuardiaColegiado", jdbcType = JdbcType.NUMERIC),
				@Result(column = "orden", property = "orden", jdbcType = JdbcType.NUMERIC)})
			List<GrupoGuardiaRowItem>  reordenarRestoGrupoLetrados(String idGrupo);
				
			@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "updateByPrimaryKeySelective2")
			@Results({})
			String updateByPrimaryKeySelective2(ScsGrupoguardiacolegiado record);
			
			@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "checkHistorico")
			@Results({})
			 String checkHistorico(DatosCalendarioProgramadoItem calendarioItem, String idInstitucion);
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getTipoDiaGuardia")
	@Results({
		@Result(
			column = "diaslaborables",
			property = "seleccionLaborables",
			jdbcType = JdbcType.VARCHAR
	), @Result(
			column = "diasfestivos",
			property = "seleccionFestivos",
			jdbcType = JdbcType.VARCHAR
	)})
	GuardiasItem getTipoDiaGuardia(String idTurno, String idGuardia, Short idInstitucion);

			@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getIdGuardiaByName")
			@Results({})

			List<String> getIdGuardiaByName( String name, String idInstitucion);
			
			@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getIdTurnoByName")
			@Results({})
			List<String> getIdTurnoByName( String name, String idInstitucion);

			@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "insertHistoricoCalendario")
			@Results({})
			String insertHistoricoCalendario(String idCalendar, String idConjuntoGuardia, String idInstitucion, String today, GuardiaCalendarioItem item, String usuModif);

			@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getLastProgramacion")
			@Results({})
			String getLastProgramacion( String idInstitucion);

			@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getNextProgramacion")
			@Results({})
			String getNextProgramacion( String idInstitucion);
			
			@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getLastCalendar")
			@Results({})
			String getLastCalendar( String idInstitucion);

			@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getIdUltimaGuardiaTurno")
			@Results({})
			String getIdUltimaGuardiaTurno();

			@UpdateProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "updateSaltosCompensacionesCumplidos")
			public boolean updateSaltosCompensacionesCumplidos(Integer idInstitucion, Integer idCalendarioGuardias, Integer idTurno, Integer idGuardia, Integer usuario);

			@DeleteProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "deleteSaltosCompensacionesCreadosEnCalendario")
			public boolean deleteSaltosCompensacionesCreadosEnCalendario(Integer idInstitucion, Integer idCalendarioGuardias, Integer idTurno, Integer idGuardia);

			@DeleteProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "deleteSaltosCompensacionesCalendariosInexistentes")
			public boolean deleteSaltosCompensacionesCalendariosInexistentes(Integer idInstitucion, Integer idTurno, Integer idGuardia);

}
