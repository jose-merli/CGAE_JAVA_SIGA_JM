package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.db.mappers.ScsTurnoMapper;
import org.itcgae.siga.db.mappers.ScsTurnoSqlProvider;
import org.itcgae.siga.DTOs.scs.InscripcionTurnoItem;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.ScsTurnoMapper;
import org.itcgae.siga.db.services.scs.providers.ScsDesignacionesSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTurnosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsTurnosExtendsMapper extends ScsTurnoMapper {

	@SelectProvider(type = ScsTurnosSqlExtendsProvider.class, method = "comboTurnos")
	@Results({ @Result(column = "IDTURNO", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR), })
	List<ComboItem> comboTurnos(Short idInstitucion);
	
	@SelectProvider(type = ScsTurnosSqlExtendsProvider.class, method = "comboTurnosAsuntos")
	@Results({ @Result(column = "IDTURNO", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR), })
	List<ComboItem> comboTurnosAsuntos(Short idInstitucion, String idTipo);
	
	@SelectProvider(type = ScsTurnosSqlExtendsProvider.class, method = "comboTurnosNoBajaNoExistentesEnListaGuardias")
	@Results({ @Result(column = "IDTURNO", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR), })
	List<ComboItem> comboTurnosNoBajaNoExistentesEnListaGuardias(String idInstitucion, String idListaGuardias);
	
	@SelectProvider(type = ScsTurnosSqlExtendsProvider.class, method = "comboTurnosDesignacion")
	@Results({ @Result(column = "IDTURNO", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR), })
	List<ComboItem> comboTurnosDesignacion(Short idInstitucion, Short idTipoTurno);

	@SelectProvider(type = ScsTurnosSqlExtendsProvider.class, method = "comboTurnosTipo")
	@Results({ @Result(column = "IDTURNO", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR), })
	List<ComboItem> comboTurnosTipo(Short idInstitucion, String tipoturno);

	@SelectProvider(type = ScsTurnosSqlExtendsProvider.class, method = "busquedaTurnos")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ABREVIATURA", property = "abreviatura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "GUARDIAS", property = "guardias", jdbcType = JdbcType.DECIMAL),
			@Result(column = "VALIDARJUSTIFICACIONES", property = "validarjustificaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESIGNADIRECTA", property = "designadirecta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "VALIDARINSCRIPCIONES", property = "validarinscripciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDAREA", property = "idarea", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDMATERIA", property = "idmateria", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDZONA", property = "idzona", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDORDENACIONCOLAS", property = "idordenacioncolas", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDGRUPOFACTURACION", property = "idgrupofacturacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDSUBZONA", property = "idsubzona", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPARTIDAPRESUPUESTARIA", property = "idpartidapresupuestaria", jdbcType = JdbcType.DECIMAL),
			@Result(column = "REQUISITOS", property = "requisitos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA_ULTIMO", property = "idpersona_ultimo", jdbcType = JdbcType.DECIMAL),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ACTIVARRETRICCIONACREDIT", property = "activarretriccionacredit", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LETRADOASISTENCIAS", property = "letradoasistencias", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LETRADOACTUACIONES", property = "letradoactuaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHASOLICITUD_ULTIMO", property = "fechasolicitudUltimo", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "VISIBILIDAD", property = "visibilidad", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPOTURNO", property = "idtipoturno", jdbcType = JdbcType.DECIMAL),
			@Result(column = "VISIBLEMOVIL", property = "visiblemovil", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDJURISDICCION", property = "idjurisdiccion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "NLETRADOS", property = "nletrados", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombrepartidosjudiciales", property = "nombrepartidosjudiciales", jdbcType = JdbcType.VARCHAR),})
	List<TurnosItem> busquedaTurnos(TurnosItem turnosItem, Short idInstitucion, String idLenguaje);

	@SelectProvider(type = ScsTurnosSqlExtendsProvider.class, method = "getObligatoriedadByTurno")
	int getObligatoriedadByTurno(Short idInstitucion, String idTurno);
	
	@SelectProvider(type = ScsTurnosSqlExtendsProvider.class, method = "busquedaFichaTurnos")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ABREVIATURA", property = "abreviatura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "GUARDIAS", property = "guardias", jdbcType = JdbcType.DECIMAL),
			@Result(column = "VALIDARJUSTIFICACIONES", property = "validarjustificaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESIGNADIRECTA", property = "designadirecta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "VALIDARINSCRIPCIONES", property = "validarinscripciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDAREA", property = "idarea", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDMATERIA", property = "idmateria", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDZONA", property = "idzona", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDORDENACIONCOLAS", property = "idordenacioncolas", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDGRUPOFACTURACION", property = "idgrupofacturacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDSUBZONA", property = "idsubzona", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPARTIDAPRESUPUESTARIA", property = "idpartidapresupuestaria", jdbcType = JdbcType.DECIMAL),
			@Result(column = "REQUISITOS", property = "requisitos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA_ULTIMO", property = "idpersona_ultimo", jdbcType = JdbcType.DECIMAL),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ACTIVARRETRICCIONACREDIT", property = "activarretriccionacredit", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LETRADOASISTENCIAS", property = "letradoasistencias", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LETRADOACTUACIONES", property = "letradoactuaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHASOLICITUD_ULTIMO", property = "fechasolicitudUltimo", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "VISIBILIDAD", property = "visibilidad", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPOTURNO", property = "idtipoturno", jdbcType = JdbcType.DECIMAL),
			@Result(column = "VISIBLEMOVIL", property = "visiblemovil", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDJURISDICCION", property = "idjurisdiccion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP) })
	List<TurnosItem> busquedaFichaTurnos(TurnosItem turnosItem, Short idInstitucion, String idLenguaje);

	@SelectProvider(type = ScsTurnosSqlExtendsProvider.class, method = "getIdTurno")
	@Results({ @Result(column = "IDTURNO", property = "newId", jdbcType = JdbcType.VARCHAR) })
	NewIdDTO getIdTurno(Short idInstitucion);

	@SelectProvider(type = ScsTurnosSqlExtendsProvider.class, method = "getIdOrdenacion")
	@Results({ @Result(column = "IDORDENACIONCOLAS", property = "newId", jdbcType = JdbcType.VARCHAR) })
	NewIdDTO getIdOrdenacion(Short idInstitucion);

	@SelectProvider(type = ScsTurnosSqlExtendsProvider.class, method = "resumenTurnoColaGuardia")
	@Results({ @Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDSUBZONA", property = "idzubzona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDZONA", property = "idzona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE_MATERIA", property = "materia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE_ZONA", property = "zona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROINSCRITOSTURNO", property = "numeroInscritos", jdbcType = JdbcType.VARCHAR), })
	List<TurnosItem> resumenTurnoColaGuardia(String idTurno, String idInstitucion);

	@SelectProvider(type = ScsTurnosSqlExtendsProvider.class, method = "comboTurnosBusqueda")
	@Results({ @Result(column = "IDTURNO", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> comboTurnosBusqueda(Short idInstitucion, String pantalla, String idTurno);

	@SelectProvider(type = ScsTurnosSqlExtendsProvider.class, method = "comboEstados")
	@Results({ @Result(column = "VALOR", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "VALOR", property = "label", jdbcType = JdbcType.VARCHAR), })
	List<ComboItem> comboEstados(Short idInstitucion);
	
	/**
	 * updateUltimoGuardias
	 *
	 * @param turnosItem
	 * @param idInstitucion
	 * @return
	 */
	@UpdateProvider(type = ScsTurnosSqlExtendsProvider.class, method = "updateUltimoGuardias")
	int updateUltimoGuardias(TurnosItem turnosItem,Short idInstitucion);

	@SelectProvider(type=ScsTurnosSqlExtendsProvider.class, method="updateUltimo")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ABREVIATURA", property = "abreviatura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "GUARDIAS", property = "guardias", jdbcType = JdbcType.DECIMAL),
			@Result(column = "VALIDARJUSTIFICACIONES", property = "validarjustificaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESIGNADIRECTA", property = "designadirecta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "VALIDARINSCRIPCIONES", property = "validarinscripciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDAREA", property = "idarea", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDMATERIA", property = "idmateria", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDZONA", property = "idzona", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDORDENACIONCOLAS", property = "idordenacioncolas", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDGRUPOFACTURACION", property = "idgrupofacturacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDSUBZONA", property = "idsubzona", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPARTIDAPRESUPUESTARIA", property = "idpartidapresupuestaria", jdbcType = JdbcType.DECIMAL),
			@Result(column = "REQUISITOS", property = "requisitos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA_ULTIMO", property = "idpersona_ultimo", jdbcType = JdbcType.DECIMAL),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ACTIVARRETRICCIONACREDIT", property = "activarretriccionacredit", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LETRADOASISTENCIAS", property = "letradoasistencias", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LETRADOACTUACIONES", property = "letradoactuaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHASOLICITUD_ULTIMO", property = "fechasolicitudUltimo", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "VISIBILIDAD", property = "visibilidad", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPOTURNO", property = "idtipoturno", jdbcType = JdbcType.DECIMAL),
			@Result(column = "VISIBLEMOVIL", property = "visiblemovil", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDJURISDICCION", property = "idjurisdiccion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP) })
	List<TurnosItem> updateUltimo(TurnosItem turnosItem,Short idInstitucion);

	@SelectProvider(type=ScsTurnosSqlExtendsProvider.class, method="busquedaUltimoLetrado")
	@Results({
			@Result(column = "NOMBREPERSONA", property = "nombrepersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS1", property = "apellido1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS2", property = "apellido2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROCOLEGIADO", property = "numerocolegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRETURNO", property = "nombre", jdbcType = JdbcType.VARCHAR)
	})
	List<TurnosItem> busquedaUltimoLetrado(String idTurno,Short idInstitucion);

	@SelectProvider(type=ScsTurnosSqlExtendsProvider.class, method="busquedaColaOficio")
	@Results({
			@Result(column = "AUX", property = "idmateria", jdbcType = JdbcType.DECIMAL),
			@Result(column = "ORDEN_COLA", property = "orden", jdbcType = JdbcType.DECIMAL),
			@Result(column = "ORDEN", property = "idzona", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL) ,
			@Result(column = "FECHAVALIDACION", property = "fechavalidacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHABAJAPERSONA", property = "fechabajapersona", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHASOLICITUD", property = "fechasolicitud", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "NIFCIF", property = "nifcif", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREPERSONA", property = "nombrepersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS1", property = "apellido1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS2", property = "apellido2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ALFABETICOAPELLIDOS", property = "alfabeticoapellidos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROCOLEGIADO", property = "numerocolegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHANACIMIENTO", property = "fechanacimiento", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "ANTIGUEDADCOLA", property = "antiguedadcola", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "SALTOS", property = "saltos", jdbcType = JdbcType.DECIMAL),
			@Result(column = "COMPENSACIONES", property = "compensaciones", jdbcType = JdbcType.DECIMAL),
	})
	List<TurnosItem> busquedaColaOficio(TurnosItem turnosItem,String strDate,String busquedaOrden,Short idInstitucion);

	@SelectProvider(type=ScsTurnosSqlExtendsProvider.class, method="busquedaColaOficio2")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ABREVIATURA", property = "abreviatura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "GUARDIAS", property = "guardias", jdbcType = JdbcType.DECIMAL),
			@Result(column = "VALIDARJUSTIFICACIONES", property = "validarjustificaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESIGNADIRECTA", property = "designadirecta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "VALIDARINSCRIPCIONES", property = "validarinscripciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDAREA", property = "idarea", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDMATERIA", property = "idmateria", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDZONA", property = "idzona", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDORDENACIONCOLAS", property = "idordenacioncolas", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDGRUPOFACTURACION", property = "idgrupofacturacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDSUBZONA", property = "idsubzona", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPARTIDAPRESUPUESTARIA", property = "idpartidapresupuestaria", jdbcType = JdbcType.DECIMAL),
			@Result(column = "REQUISITOS", property = "requisitos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA_ULTIMO", property = "idpersona_ultimo", jdbcType = JdbcType.DECIMAL),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ACTIVARRETRICCIONACREDIT", property = "activarretriccionacredit", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LETRADOASISTENCIAS", property = "letradoasistencias", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LETRADOACTUACIONES", property = "letradoactuaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHASOLICITUD_ULTIMO", property = "fechasolicitudUltimo", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "VISIBILIDAD", property = "visibilidad", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPOTURNO", property = "idtipoturno", jdbcType = JdbcType.DECIMAL),
			@Result(column = "VISIBLEMOVIL", property = "visiblemovil", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDJURISDICCION", property = "idjurisdiccion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "ORDEN_COLA", property = "idcomboguardias", jdbcType = JdbcType.DECIMAL) })
	List<TurnosItem> busquedaColaOficio2(TurnosItem turnosItem,String busquedaOrden,String strDate,Short idInstitucion);

	@SelectProvider(type=ScsTurnosSqlExtendsProvider.class, method="busquedaColaOficioPrimerLetrado")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ABREVIATURA", property = "abreviatura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "GUARDIAS", property = "guardias", jdbcType = JdbcType.DECIMAL),
			@Result(column = "VALIDARJUSTIFICACIONES", property = "validarjustificaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESIGNADIRECTA", property = "designadirecta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "VALIDARINSCRIPCIONES", property = "validarinscripciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDAREA", property = "idarea", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDMATERIA", property = "idmateria", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDZONA", property = "idzona", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDORDENACIONCOLAS", property = "idordenacioncolas", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDGRUPOFACTURACION", property = "idgrupofacturacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDSUBZONA", property = "idsubzona", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPARTIDAPRESUPUESTARIA", property = "idpartidapresupuestaria", jdbcType = JdbcType.DECIMAL),
			@Result(column = "REQUISITOS", property = "requisitos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA_ULTIMO", property = "idpersona_ultimo", jdbcType = JdbcType.DECIMAL),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ACTIVARRETRICCIONACREDIT", property = "activarretriccionacredit", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LETRADOASISTENCIAS", property = "letradoasistencias", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LETRADOACTUACIONES", property = "letradoactuaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGOEXT", property = "codigoext", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHASOLICITUD_ULTIMO", property = "fechasolicitudUltimo", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "VISIBILIDAD", property = "visibilidad", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPOTURNO", property = "idtipoturno", jdbcType = JdbcType.DECIMAL),
			@Result(column = "VISIBLEMOVIL", property = "visiblemovil", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDJURISDICCION", property = "idjurisdiccion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "ORDEN_COLA", property = "idcomboguardias", jdbcType = JdbcType.DECIMAL) })
	List<TurnosItem> busquedaColaOficioPrimerLetrado(TurnosItem turnosItem,String busquedaOrden,String strDate,Short idInstitucion);
	
	@SelectProvider(type=ScsTurnosSqlExtendsProvider.class, method="busquedaColaGuardia")
	@Results({
			@Result(column = "ORDEN_COLA", property = "orden", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDGUARDIA", property = "idguardias", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREGUARDIA", property = "nombreguardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS1", property = "apellido1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS2", property = "apellido2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROCOLEGIADO", property = "numerocolegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAVALIDACION", property = "fechavalidacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.DATE) })
	List<TurnosItem> busquedaColaGuardia(TurnosItem turnosItem,String strDate,String busquedaOrden,Short idInstitucion);


	@SelectProvider(type=ScsTurnosSqlExtendsProvider.class, method="selectInscripcionTurnoByTurno")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHASOLICITUD", property = "fechasolicitud", jdbcType = JdbcType.TIMESTAMP) })
	List<InscripcionTurnoItem> selectInscripcionTurnoByTurno(Short idInstitucion, String idTurno);
	
	@SelectProvider(type=ScsTurnosSqlExtendsProvider.class, method="selectInscripcionTurnoBajasByTurnoFechaBaja")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHASOLICITUD", property = "fechasolicitud", jdbcType = JdbcType.TIMESTAMP) })
	List<InscripcionTurnoItem> selectInscripcionTurnoBajasByTurnoFechaBaja(Short idInstitucion, String idTurno,String fechaBaja);
	
	@SelectProvider(type=ScsTurnosSqlExtendsProvider.class, method="selectInscripcionAltasTurnoByTurno")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHASOLICITUD", property = "fechasolicitud", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHASOLICITUDBAJA", property = "fechasolicitudbaja", jdbcType = JdbcType.TIMESTAMP) })
	List<InscripcionTurnoItem> selectInscripcionAltasTurnoByTurno(Short idInstitucion, String idTurno);

}