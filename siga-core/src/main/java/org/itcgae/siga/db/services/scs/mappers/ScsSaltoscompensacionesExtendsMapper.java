package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.itcgae.siga.DTOs.scs.BusquedaLetradosGuardiaDTO;
import org.itcgae.siga.DTOs.scs.LetradoGuardiaItem;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaItem;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaLetradoGrupoDTO;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.ScsSaltoscompensaciones;
import org.itcgae.siga.db.mappers.ScsSaltoscompensacionesMapper;
import org.itcgae.siga.db.services.scs.providers.ScsSaltoscompensacionesSqlExtendsProvider;

public interface ScsSaltoscompensacionesExtendsMapper extends ScsSaltoscompensacionesMapper {

	@SelectProvider(type = ScsSaltoscompensacionesSqlExtendsProvider.class, method = "searchSaltosCompensaciones")
	@Results({ @Result(column = "GRUPO", property = "grupo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NOMBRE_TURNO", property = "turno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREGUARDIA", property = "guardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDSALTOSTURNO", property = "idSaltosTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "COLEGIADO_GRUPO", property = "colegiadoGrupo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LETRADO", property = "letrado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SALTOOCOMPENSACION", property = "saltoCompensacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHA", property = "fecha", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MOTIVO", property = "motivo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAUSO", property = "fechaUso", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHA_ANULACION", property = "fechaAnulacion", jdbcType = JdbcType.VARCHAR) })
	List<SaltoCompGuardiaItem> searchSaltosCompensaciones(SaltoCompGuardiaItem saltoItem, String idInstitucion,
			Integer tamMax);

	@SelectProvider(type = ScsSaltoscompensacionesSqlExtendsProvider.class, method = "searchSaltosYCompensacionesOficio")
	@Results({ @Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NOMBRE_TURNO", property = "turno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDSALTOSTURNO", property = "idSaltosTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "COLEGIADO_GRUPO", property = "colegiadoGrupo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LETRADO", property = "letrado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SALTOOCOMPENSACION", property = "saltoCompensacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHA", property = "fecha", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MOTIVO", property = "motivo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAUSO", property = "fechaUso", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHA_ANULACION", property = "fechaAnulacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR) })
	List<SaltoCompGuardiaItem> searchSaltosYCompensacionesOficio(SaltoCompGuardiaItem salto, String idInstitucion,
			Integer tamMax);
	
	@SelectProvider(type = ScsSaltoscompensacionesSqlExtendsProvider.class, method = "searchSaltosOCompensacionesOficio")
	@Results({ @Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS1", property = "apellidos1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS2", property = "apellidos2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NCOLEGIADO", property = "numeroColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMERO", property = "numeroGrupo", jdbcType = JdbcType.VARCHAR) })
	List<LetradoGuardiaItem> searchSaltosOCompensacionesOficio(String idInstitucion, String idTurno, String idGuardia, String saltoocompensacion);

	@SelectProvider(type = ScsSaltoscompensacionesSqlExtendsProvider.class, method = "searchLetrados")
	@Results({ @Result(column = "NUMEROGRUPO", property = "numeroGrupo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "COLEGIADO", property = "colegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "GRUPO", property = "grupo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "COLEGIADOGRUPO", property = "colegiadoGrupo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LETRADO", property = "letrado", jdbcType = JdbcType.VARCHAR), })
	List<SaltoCompGuardiaLetradoGrupoDTO> searchLetrados(SaltoCompGuardiaItem saltoItem, String idInstitucion);

	@SelectProvider(type = ScsSaltoscompensacionesSqlExtendsProvider.class, method = "searchLetradosGuardia")
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
	List<LetradoGuardiaItem> searchLetradosGuardia(String idInstitucion, String idTurno, String idGuardia,
			boolean grupo);

	@SelectProvider(type = ScsSaltoscompensacionesSqlExtendsProvider.class, method = "searchLetradosTurno")
	@Results({ @Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS1", property = "apellidos1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS2", property = "apellidos2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ALFABETICOAPELLIDOS", property = "alfabeticoApellidos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROCOLEGIADO", property = "numeroColegiado", jdbcType = JdbcType.VARCHAR) })
	List<LetradoGuardiaItem> searchLetradosTurno(String idTurno, String idInstitucion);

	@SelectProvider(type = ScsSaltoscompensacionesSqlExtendsProvider.class, method = "selectNuevoIdSaltosCompensaciones")
	@Results({ @Result(column = "IDSALTOSTURNO", property = "idMax", jdbcType = JdbcType.VARCHAR), })
	MaxIdDto selectNuevoIdSaltosCompensaciones(SaltoCompGuardiaItem saltoItem, String idInstitucion);

	@SelectProvider(type = ScsSaltoscompensacionesSqlExtendsProvider.class, method = "selectNuevoIdSaltosCompensacionesGrupo")
	@Results({ @Result(column = "ID", property = "idMax", jdbcType = JdbcType.VARCHAR), })
	MaxIdDto selectNuevoIdSaltosCompensacionesGrupo();
	
	@SelectProvider(type = ScsSaltoscompensacionesSqlExtendsProvider.class, method = "selectIdSiguienteSaltosCompensacionesGrupo")
	@Results({ @Result(column = "ID", property = "idMax", jdbcType = JdbcType.NUMERIC), })
	MaxIdDto selectIdSiguienteSaltosCompensacionesGrupo();

	@InsertProvider(type = ScsSaltoscompensacionesSqlExtendsProvider.class, method = "guardarSaltosCompensaciones")
	int guardarSaltosCompensaciones(SaltoCompGuardiaItem saltoItem, String idInstitucion, String idSaltosTurno,
			AdmUsuarios usuario);

	@InsertProvider(type = ScsSaltoscompensacionesSqlExtendsProvider.class, method = "guardarSaltosCompensacionesGrupo")
	int guardarSaltosCompensacionesGrupo(SaltoCompGuardiaItem saltoItem, String idInstitucion, String idSalComGrupo,
			AdmUsuarios usuario);

	@UpdateProvider(type = ScsSaltoscompensacionesSqlExtendsProvider.class, method = "actualizarSaltosCompensaciones")
	int actualizarSaltosCompensaciones(SaltoCompGuardiaItem saltoItem, String idInstitucion, AdmUsuarios usuario);

	@UpdateProvider(type = ScsSaltoscompensacionesSqlExtendsProvider.class, method = "actualizarSaltosCompensacionesGrupo")
	int actualizarSaltosCompensacionesGrupo(SaltoCompGuardiaItem saltoItem, String idInstitucion, AdmUsuarios usuario);

	@UpdateProvider(type = ScsSaltoscompensacionesSqlExtendsProvider.class, method = "anularSaltosCompensaciones")
	int anularSaltosCompensaciones(SaltoCompGuardiaItem saltoItem, String idInstitucion, AdmUsuarios usuario);

	@UpdateProvider(type = ScsSaltoscompensacionesSqlExtendsProvider.class, method = "anularSaltosCompensacionesGrupo")
	int anularSaltosCompensacionesGrupo(SaltoCompGuardiaItem saltoItem, AdmUsuarios usuario);

	@DeleteProvider(type = ScsSaltoscompensacionesSqlExtendsProvider.class, method = "borrarSaltosCompensaciones")
	int borrarSaltosCompensaciones(SaltoCompGuardiaItem saltoItem, String idInstitucion);

	@DeleteProvider(type = ScsSaltoscompensacionesSqlExtendsProvider.class, method = "borrarSaltosCompensacionesGrupo")
	int borrarSaltosCompensacionesGrupo(SaltoCompGuardiaItem saltoItem);

	@SelectProvider(type = ScsSaltoscompensacionesSqlExtendsProvider.class, method = "isGrupo")
	String isGrupobyId(BusquedaLetradosGuardiaDTO idGuardia);
	
	@InsertProvider(type = ScsSaltoscompensacionesSqlExtendsProvider.class, method = "insertManual")
	int insertManual(ScsSaltoscompensaciones record, String fechaFormat);

	@DeleteProvider(type = ScsSaltoscompensacionesSqlExtendsProvider.class, method = "deleteSaltosCompensacionesCalendariosInexistentes")
	public boolean deleteSaltosCompensacionesCalendariosInexistentes(Integer idInstitucion, Integer idTurno, Integer idGuardia);

	@DeleteProvider(type = ScsSaltoscompensacionesSqlExtendsProvider.class, method = "deleteSaltosCompensacionesCreadosEnCalendario")
	public boolean deleteSaltosCompensacionesCreadosEnCalendario(Integer idInstitucion, Integer idCalendarioGuardias, Integer idTurno, Integer idGuardia);

	@DeleteProvider(type = ScsSaltoscompensacionesSqlExtendsProvider.class, method = "deleteSaltosCompensacionesGrupoCreadosEnCalendario")
	public boolean deleteSaltosCompensacionesGrupoCreadosEnCalendario(Integer idInstitucion, Integer idCalendarioGuardias, Integer idTurno, Integer idGuardia);

	@UpdateProvider(type = ScsSaltoscompensacionesSqlExtendsProvider.class, method = "updateSaltosCompensacionesCumplidos")
	public boolean updateSaltosCompensacionesCumplidos(Integer idInstitucion, Integer idCalendarioGuardias, Integer idTurno, Integer idGuardia, Integer usuario);

}
