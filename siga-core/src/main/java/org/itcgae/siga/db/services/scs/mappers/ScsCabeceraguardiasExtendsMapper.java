package org.itcgae.siga.db.services.scs.mappers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.CalendariosProgDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.DatosCalendarioProgramadoItem;
import org.itcgae.siga.DTOs.scs.GuardiasItem;
import org.itcgae.siga.DTOs.scs.TarjetaAsistenciaResponseItem;
import org.itcgae.siga.db.entities.ScsCabeceraguardias;
import org.itcgae.siga.db.entities.ScsGuardiascolegiado;
import org.itcgae.siga.db.mappers.ScsCabeceraguardiasMapper;
import org.itcgae.siga.db.mappers.ScsCabeceraguardiasSqlProvider;
import org.itcgae.siga.db.services.scs.providers.ScsCabeceraguardiasSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsGuardiascolegiadoSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsGuardiasturnoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsCabeceraguardiasExtendsMapper extends ScsCabeceraguardiasMapper {

	@SelectProvider(type = ScsCabeceraguardiasSqlExtendsProvider.class, method = "getCabeceraGuardiasDeVariasPersonas")
	@Results({ 
		@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPAGOSJG", property = "idpagosjg", jdbcType = JdbcType.VARCHAR)
	})
	List<ScsCabeceraguardias> getCabeceraGuardiasDeVariasPersonas(String colegio, ArrayList<String> listaIdPersonas);
	
	@SelectProvider(type = ScsCabeceraguardiasSqlExtendsProvider.class, method = "busquedaGuardiasColegiado")
	@Results({ @Result(column = "idinstitucion", property = "idinstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechainicio", property = "fechadesde", jdbcType = JdbcType.DATE),
			@Result(column = "fecha_fin", property = "fechahasta", jdbcType = JdbcType.DATE),
			@Result(column = "validado", property = "validada", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idturno", property = "idTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idguardia", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nomturno", property = "tipoTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nomguardia", property = "tipoGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "colegiado", property = "letradosGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numerocolegiado", property = "numColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "tipodias", property = "tipoDiasGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numerogrupo", property = "grupoGuardiaColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idpersona", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechavalidacion", property = "fechaValidacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idcalendarioguardias", property = "idCalendarioGuardias", jdbcType = JdbcType.VARCHAR),
			@Result(column = "facturado", property = "facturado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idfacturacion", property = "idFacturacion", jdbcType = JdbcType.INTEGER),
			@Result(column = "comensustitucion", property = "comensustitucion", jdbcType = JdbcType.VARCHAR),})	
	List<org.itcgae.siga.DTOs.scs.GuardiasItem> busquedaGuardiasColegiado(GuardiasItem guardiaItem, String idInstitucion, Integer tamMax);
	
	@UpdateProvider(type = ScsCabeceraguardiasSqlExtendsProvider.class, method = "validarSolicitudGuardia")
	int validarSolicitudGuardia(ScsCabeceraguardias record);
	
	@UpdateProvider(type = ScsCabeceraguardiasSqlExtendsProvider.class, method = "desvalidarGuardiaColegiado")
	int desvalidarGuardiaColegiado(ScsCabeceraguardias record);
	
	@SelectProvider(type = ScsCabeceraguardiasSqlExtendsProvider.class, method = "getPermutaGuardiaColegiado")
	@Results({ 
		@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPAGOSJG", property = "idpagosjg", jdbcType = JdbcType.VARCHAR)
	})
	List<ScsCabeceraguardias> getPermutaGuardiaColegiado(GuardiasItem guardiaItem);
	
	
	@SelectProvider(type = ScsCabeceraguardiasSqlExtendsProvider.class, method = "getCalendarioGuardiaColegiado")
	@Results({ @Result(column = "institucion", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombreturno", property = "turno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombreguardia", property = "guardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idTurno", property = "idTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idGuardia", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechainicio", property = "fechaDesde", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechafin", property = "fechaHasta", jdbcType = JdbcType.VARCHAR),
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
			@Result(column = "asistenciasAsociadas", property = "asistenciasAsociadas", jdbcType = JdbcType.VARCHAR)})
	
	List<DatosCalendarioProgramadoItem> getCalendarioGuardiaColegiado(String institucion,String idTurno,String idGuardia,String idcalendarioguardias);
	
	@SelectProvider(type = ScsCabeceraguardiasSqlExtendsProvider.class, method = "tieneGuardia")
	int tieneGuardia(String institucion,Long idPersona);

	@DeleteProvider(type = ScsCabeceraguardiasSqlExtendsProvider.class, method = "deleteCabecerasGuardiasCalendario")
	public boolean deleteCabecerasGuardiasCalendario(Integer idInstitucion, Integer idCalendarioGuardias, Integer idTurno, Integer idGuardia, String fechaInicio, String fechaFin);

	@SelectProvider(type = ScsCabeceraguardiasSqlExtendsProvider.class, method = "getCabeceraGuardia")
	List<ScsCabeceraguardias> getCabeceraGuardia(String idInstitucion, String idTurno, String idGuardia, String fechaInicio, String fechaFin);

}
