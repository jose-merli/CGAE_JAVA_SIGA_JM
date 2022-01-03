package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;


import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.ActasItem;

import org.itcgae.siga.db.mappers.ScsEjgMapper;
import org.itcgae.siga.db.services.scs.providers.ScsActaSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsEjgComisionSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsActaExtendsMapper extends ScsEjgMapper {

	@SelectProvider(type = ScsActaSqlExtendsProvider.class, method = "busquedaActas")
	@Results({

			@Result(column = "IDACTA", property = "idacta", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NUMEROACTA", property = "numeroacta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHARESOLUCION", property = "fecharesolucion", jdbcType = JdbcType.DATE),
			@Result(column = "FECHAREUNION", property = "fechareunion", jdbcType = JdbcType.DATE),
			@Result(column = "IDPRESIDENTE", property = "idpresidente", jdbcType = JdbcType.NUMERIC),
			@Result(column = "IDSECRETARIO", property = "idsecretario", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NOMBREPRESIDENTE", property = "nombrepresidente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRESECRETARIO", property = "nombresecretario", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ANIOACTA", property = "anioacta", jdbcType = JdbcType.NUMERIC)

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
	@Results({ @Result(column = "valor", property = "valor", jdbcType = JdbcType.VARCHAR)})
	String comboSufijoActa(Short idInstitucion);

	@SelectProvider(type = ScsActaSqlExtendsProvider.class, method = "getNuevoNumActaComp")
	@Results({ @Result(column = "NUMEROACTA", property = "NUMEROACTA", jdbcType = JdbcType.VARCHAR)})
	String getNuevoNumActaComp(String idInstitucion, String anio, String sufijo);
	
	@SelectProvider(type = ScsActaSqlExtendsProvider.class, method = "getNuevoNumActaAuxComp")
	@Results({ @Result(column = "NUMEROACTA", property = "NUMEROACTA", jdbcType = JdbcType.VARCHAR)})
	String getNuevoNumActaAuxComp(String idInstitucion, String anio, String sufijo);

	@SelectProvider(type = ScsActaSqlExtendsProvider.class, method = "getNuevoNumActaSimple")
	@Results({ @Result(column = "NUMEROACTA", property = "NUMEROACTA", jdbcType = JdbcType.VARCHAR)})
	String getNuevoNumActaSimple(String idInstitucion, String anio) ;
	
	@SelectProvider(type = ScsActaSqlExtendsProvider.class, method = "getNuevoNumActaAuxSimple")
	@Results({ @Result(column = "NUMEROACTA", property = "NUMEROACTA", jdbcType = JdbcType.VARCHAR)})
	String getNuevoNumActaAuxSimple(String idInstitucion, String anio);
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

	@SelectProvider(type = ScsActaSqlExtendsProvider.class, method = "obtenerIdActa")
	@Results({ @Result(column = "IDACTA", property = "IDACTA", jdbcType = JdbcType.VARCHAR) })
	String obtenerIdActa(ActasItem actasItem, Short idInstitucion);

	@SelectProvider(type = ScsActaSqlExtendsProvider.class, method = "comprobarNumeroActa")
	@Results({ @Result(column = "NUMEROACTA", property = "NUMEROACTA", jdbcType = JdbcType.VARCHAR) })
	String comprobarNumeroActa(String anioHoy, String numeroActa, Short idInstitucion);


}