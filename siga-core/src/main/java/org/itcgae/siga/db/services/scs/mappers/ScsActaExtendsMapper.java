package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.itcgae.siga.DTOs.com.EnviosMasivosItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.ActasItem;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.AsuntosEjgItem;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableItem;
import org.itcgae.siga.DTOs.scs.ColegiadosSJCSItem;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.EstadoEjgItem;
import org.itcgae.siga.DTOs.scs.ProcuradorItem;
import org.itcgae.siga.DTOs.scs.RelacionesItem;
import org.itcgae.siga.DTOs.scs.ResolucionEJGItem;
import org.itcgae.siga.db.mappers.ScsEjgMapper;
import org.itcgae.siga.db.services.scs.providers.ScsActaSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsEjgSqlExtendsProvider;

public interface ScsActaExtendsMapper extends ScsEjgMapper {

	@SelectProvider(type = ScsActaSqlExtendsProvider.class, method = "busquedaActas")
	@Results({

			@Result(column = "IDACTA", property = "idActa", jdbcType = JdbcType.NUMERIC),
			// @Result(column = "IDINSTITUCION", property = "institucion", jdbcType =
			// JdbcType.VARCHAR),
			@Result(column = "NUMEROACTA", property = "numeroActa", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHARESOLUCION", property = "fechaResolucion", jdbcType = JdbcType.DATE),
			@Result(column = "FECHAREUNION", property = "fechaReunion", jdbcType = JdbcType.DATE),
			@Result(column = "IDPRESIDENTE", property = "idPresidente", jdbcType = JdbcType.NUMERIC),
			@Result(column = "IDSECRETARIO", property = "idSecretario", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NOMBREPRESIDENTE", property = "nombrePresidente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRESECRETARIO", property = "nombreSecretario", jdbcType = JdbcType.VARCHAR)

	})
	List<ActasItem> busquedaActas(ActasItem actasItem, Short idInstitucion);
//
//	@SelectProvider(type = ScsActaSqlExtendsProvider.class, method = "comprobarBorrarActas")
//	@Results({ @Result(column = "ID", property = "idActa", jdbcType = JdbcType.VARCHAR) })
//	List<ActasItem> comprobarBorrarActa(ActasItem actasItem, Short idInstitucion);
//
	@SelectProvider(type = ScsActaSqlExtendsProvider.class, method = "comprobarGuardarActaPonente")
	@Results({ @Result(column = "contar", property = "contar", jdbcType = JdbcType.NUMERIC) })
	String comprobarGuardarActaPonente(ActasItem actasItem, Short idInstitucion);

	@SelectProvider(type = ScsActaSqlExtendsProvider.class, method = "comprobarGuardarActaSufijo")
	@Results({ @Result(column = "contar", property = "contar", jdbcType = JdbcType.VARCHAR) })
	String comprobarGuardarActaSufijo(ActasItem actasItem, Short idInstitucion);

	@SelectProvider(type = ScsActaSqlExtendsProvider.class, method = "comboSufijoActa")
	@Results({ @Result(column = "valor", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "valor", property = "label", jdbcType = JdbcType.DATE) })
	List<ComboItem> comboSufijoActa(Short idInstitucion);
//
//	@SelectProvider(type = ScsActaSqlExtendsProvider.class, method = "guardarActa")
//	int guardarActa(ActasItem actasItem);
//
//	@SelectProvider(type = ScsActaSqlExtendsProvider.class, method = "anadirEJGPendientesCAJG")
//	@Results({ @Result(column = "contar", property = "contar", jdbcType = JdbcType.VARCHAR) })
//	String anadirEJGPendientesCAJG(ActasItem actasItem, Short valueOf);
//
//	@SelectProvider(type = ScsActaSqlExtendsProvider.class, method = "abrirActa")
//	int abrirActa(ActasItem actasItem, Short valueOf);
//
//	@SelectProvider(type = ScsActaSqlExtendsProvider.class, method = "guardarActa")
//	@Results({ @Result(column = "ID", property = "idActa", jdbcType = JdbcType.VARCHAR),
//			@Result(column = "ANIOACTA", property = "anio", jdbcType = JdbcType.DATE) })
//	List<EjgItem> detectarEjgAsociadoActa(ActasItem actasItem);
//
//	@SelectProvider(type = ScsActaSqlExtendsProvider.class, method = "actualizarEjg")
//	int actualizarEjg(EjgItem ejgitem);
//
//	@SelectProvider(type = ScsActaSqlExtendsProvider.class, method = "actualizarEstadoEjg")
//	int actualizarEstadoEjg(EjgItem ejgitem, EstadoEjgItem estadoEjgItem);
//
//	@SelectProvider(type = ScsActaSqlExtendsProvider.class, method = "expedientesRetirados")
//	List<EjgItem> expedientesRetirados(ActasItem actasItem, Short idInstitucion);
//
//	@SelectProvider(type = ScsActaSqlExtendsProvider.class, method = "updatePendientes")
//	int updatePendientes(ActasItem actasItem, String pendientes);
//
//	@SelectProvider(type = ScsActaSqlExtendsProvider.class, method = "desvincularActa")
//	void desvincularActa(EjgItem ejgItem, ActasItem actasItem, Short idInstitucion);
//
//	@SelectProvider(type = ScsActaSqlExtendsProvider.class, method = "getUltimoEstadoEjg")
//	List<EstadoEjgItem> getUltimoEstadoEjg(EjgItem ejgItem, Short idInstitucion, String string);
//
//	@SelectProvider(type = ScsActaSqlExtendsProvider.class, method = "cambiarEstadoEjg")
//	int cambiarEstadoEjg(EstadoEjgItem estadoEjgItem, String string, String crearPendientes);
//
	@SelectProvider(type = ScsActaSqlExtendsProvider.class, method = "getActa")
	@Results({ @Result(column = "IDACTA", property = "idActa", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "anio", jdbcType = JdbcType.DATE),
			@Result(column = "ANIOACTA", property = "anio", jdbcType = JdbcType.DATE),
			@Result(column = "FECHAREUNION", property = "anio", jdbcType = JdbcType.DATE),
			@Result(column = "FECHARESOLUCION", property = "anio", jdbcType = JdbcType.DATE),
			@Result(column = "HORAINICIOREUNION", property = "anio", jdbcType = JdbcType.DATE),
			@Result(column = "HORAFINREUNION", property = "anio", jdbcType = JdbcType.DATE),
			@Result(column = "IDPRESIDENTE", property = "anio", jdbcType = JdbcType.DATE),
			@Result(column = "IDSECRETARIO", property = "anio", jdbcType = JdbcType.DATE),
			@Result(column = "MIEMBROSCOMISION", property = "anio", jdbcType = JdbcType.DATE),
			@Result(column = "FECHAMODIFICACION", property = "anio", jdbcType = JdbcType.DATE),
			@Result(column = "USUMODIFICACION", property = "anio", jdbcType = JdbcType.DATE),
			@Result(column = "PENDIENTES", property = "anio", jdbcType = JdbcType.DATE),
			@Result(column = "IDINTERCAMBIO", property = "anio", jdbcType = JdbcType.DATE),
			@Result(column = "FECHAINTERCAMBIO", property = "anio", jdbcType = JdbcType.DATE),
			@Result(column = "NUMEROACTA", property = "anio", jdbcType = JdbcType.DATE),
			@Result(column = "OBSERVACIONES", property = "anio", jdbcType = JdbcType.DATE) })
	ActasItem getActa(ActasItem actasItem, Short idInstitucion);
//
	@SelectProvider(type = ScsActaSqlExtendsProvider.class, method = "getEstadosEjg")
	@Results({ @Result(column = "IDESTADOPOREJG", property = "IDESTADOPOREJG", jdbcType = JdbcType.VARCHAR) })
	String getEstadosEjg(Short idinstitucion, Short idtipoejg, Short anio, Long numero);

}