package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.AsuntosEjgItem;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableItem;
import org.itcgae.siga.DTOs.scs.ColegiadosSJCSItem;
import org.itcgae.siga.db.mappers.ScsEjgMapper;
import org.itcgae.siga.db.services.scs.providers.ScsEjgSqlExtendsProvider;

public interface ScsEjgExtendsMapper extends ScsEjgMapper {

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "getAsuntoTipoEjg")
	@Results({ @Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ASUNTO", property = "asunto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHA", property = "fecha", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TURNOGUARDIA", property = "turnoGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONALETRADO", property = "idPersonaLetrado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONAJG", property = "idPersonajg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "EXPEDIENTEINSOSTENIBILIDAD", property = "expedienteInsostenibilidad", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DICTAMEN", property = "dictamen", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FUNDAMENTOCALIFICACION", property = "fundamentoCalificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TIPORESOLUCION", property = "tipoResolucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TIPOFUNDAMENTO", property = "tipoFundamento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TIPORESOLUCIONAUTO", property = "tipoResolucionAuto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TIPOSENTIDOAUTO", property = "tipoSentidoAuto", jdbcType = JdbcType.VARCHAR), })
	AsuntosEjgItem getAsuntoTipoEjg(AsuntosClaveJusticiableItem asuntoClave, String idLenguaje);

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "searchClaveAsuntosEJG")
	@Results({ @Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CLAVE", property = "clave", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ROL", property = "rol", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR) })
	List<AsuntosClaveJusticiableItem> searchClaveAsuntosEJG(AsuntosJusticiableItem asuntosJusticiableItem,
			Integer tamMaximo);

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "comboCreadoDesde")
	@Results({ @Result(column = "ORIGENAPERTURA", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> comboCreadoDesde(String idlenguaje, String string);

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "busquedaColegiadoEJG")
	@Results({ @Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS", property = "apellidos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NCOLEGIADO", property = "nColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NCOMUNITARIO", property = "nComunitario", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDESTADO", property = "idEstado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ESTADO", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "RESIDENTE", property = "residente", jdbcType = JdbcType.BOOLEAN),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ABREVIATURA", property = "abreviatura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idPersona", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "tieneguardias", property = "tieneGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "guardiaspendientes", property = "guardiasPendientes", jdbcType = JdbcType.VARCHAR),
			@Result(column = "tieneturno", property = "tieneTurno", jdbcType = JdbcType.VARCHAR)})
	List<ColegiadosSJCSItem> busquedaColegiadoEJG(ColegiadosSJCSItem item, String idLenguaje, Integer tamMaximo);
	
	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "tieneGuardias")
	@Results({ @Result(column = "idGuardia", property = "tieneGuardia", jdbcType = JdbcType.VARCHAR),})
	List<String> tieneGuardias(String idInstitucion, ColegiadosSJCSItem colegiadosSJCSItem);
	
	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "tieneTurnos")
	@Results({ @Result(column = "idTurno", property = "tieneTurno", jdbcType = JdbcType.VARCHAR),
	@Result(column = "idPersona", property = "idPersona", jdbcType = JdbcType.VARCHAR),})
	List<ColegiadosSJCSItem> tieneTurnos(String idInstitucion, String idPersona);
	
	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "tieneGuardiasPendientes")
	@Results({ @Result(column = "idGuardia", property = "tieneGuardia", jdbcType = JdbcType.VARCHAR),})
	String tieneGuardiasPendientes(String idInstitucion, ColegiadosSJCSItem colegiadosSJCSItem, String idGuardia);
}
