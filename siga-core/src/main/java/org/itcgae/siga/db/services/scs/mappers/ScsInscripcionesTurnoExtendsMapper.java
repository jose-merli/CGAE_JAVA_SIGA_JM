package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.InscripcionesItem;
import org.itcgae.siga.DTOs.scs.TiposActuacionItem;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.db.mappers.ScsInscripcionturnoMapper;
import org.itcgae.siga.db.mappers.ScsTurnoMapper;
import org.itcgae.siga.db.services.scs.providers.ScsInscripcionesTurnoSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsProcedimientosSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTurnosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsInscripcionesTurnoExtendsMapper extends ScsInscripcionturnoMapper{

	@SelectProvider(type = ScsInscripcionesTurnoSqlExtendsProvider.class, method = "comboTurnos")
	@Results({
		@Result(column = "IDTURNO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOM", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> comboTurnos(Short idInstitucion);
	
	 @SelectProvider(type=ScsInscripcionesTurnoSqlExtendsProvider.class, method="busquedaInscripciones")
	 @Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
		 @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
	        @Result(column="IDPERSONA", property="idpersona", jdbcType=JdbcType.DECIMAL, id=true),
	        @Result(column="IDTURNO", property="idturno", jdbcType=JdbcType.DECIMAL, id=true),
	        @Result(column="FECHASOLICITUD", property="fechasolicitud", jdbcType=JdbcType.DATE),
	        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
	        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
	        @Result(column="FECHAVALIDACION", property="fechavalidacion", jdbcType=JdbcType.TIMESTAMP),
	        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP),
	        @Result(column="OBSERVACIONESSOLICITUD", property="observacionessolicitud", jdbcType=JdbcType.VARCHAR),
	        @Result(column="OBSERVACIONESVALIDACION", property="observacionesvalidacion", jdbcType=JdbcType.VARCHAR),
	        @Result(column="OBSERVACIONESBAJA", property="observacionesbaja", jdbcType=JdbcType.VARCHAR),
	        @Result(column="FECHASOLICITUDBAJA", property="fechasolicitudbaja", jdbcType=JdbcType.TIMESTAMP),
	        @Result(column="FECHADENEGACION", property="fechadenegacion", jdbcType=JdbcType.TIMESTAMP),
	        @Result(column="OBSERVACIONESDENEGACION", property="observacionesdenegacion", jdbcType=JdbcType.VARCHAR),
	        @Result(column="OBSERVACIONESVALBAJA", property="observacionesvalbaja", jdbcType=JdbcType.VARCHAR) })
	    List<InscripcionesItem> busquedaInscripciones(InscripcionesItem inscripcionesItem,Short idInstitucion,String fechadesde,String fechahasta,String afechade,Integer tamMaximo);
	 
	 @SelectProvider(type=ScsInscripcionesTurnoSqlExtendsProvider.class, method="busquedaTarjetaInscripciones")
	 @Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
		 @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
	        @Result(column="IDPERSONA", property="idpersona", jdbcType=JdbcType.DECIMAL, id=true),
	        @Result(column="IDTURNO", property="idturno", jdbcType=JdbcType.DECIMAL, id=true),
	        @Result(column="FECHASOLICITUD", property="fechasolicitud", jdbcType=JdbcType.DATE),
	        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
	        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
	        @Result(column="FECHAVALIDACION", property="fechavalidacion", jdbcType=JdbcType.TIMESTAMP),
	        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP),
	        @Result(column="OBSERVACIONESSOLICITUD", property="observacionessolicitud", jdbcType=JdbcType.VARCHAR),
	        @Result(column="OBSERVACIONESVALIDACION", property="observacionesvalidacion", jdbcType=JdbcType.VARCHAR),
	        @Result(column="OBSERVACIONESBAJA", property="observacionesbaja", jdbcType=JdbcType.VARCHAR),
	        @Result(column="FECHASOLICITUDBAJA", property="fechasolicitudbaja", jdbcType=JdbcType.TIMESTAMP),
	        @Result(column="FECHADENEGACION", property="fechadenegacion", jdbcType=JdbcType.TIMESTAMP),
	        @Result(column="OBSERVACIONESDENEGACION", property="observacionesdenegacion", jdbcType=JdbcType.VARCHAR),
	        @Result(column="OBSERVACIONESVALBAJA", property="observacionesvalbaja", jdbcType=JdbcType.VARCHAR) })
	    List<InscripcionesItem> busquedaTarjetaInscripciones(InscripcionesItem inscripcionesItem,Short idInstitucion, String idlenguaje);
	 
	 @SelectProvider(type=ScsInscripcionesTurnoSqlExtendsProvider.class, method="busquedaColaOficio")
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
			@Result(column = "IDPERSONA_ULTIMO", property = "idpersonaUltimo", jdbcType = JdbcType.DECIMAL),
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
	    List<InscripcionesItem> busquedaColaOficio(InscripcionesItem inscripcionesItem,String busquedaOrden,String strDate,Short idInstitucion);
	 
	 @SelectProvider(type=ScsInscripcionesTurnoSqlExtendsProvider.class, method="busquedaTarjeta")
	 @Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
		 @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
	        @Result(column="IDPERSONA", property="idpersona", jdbcType=JdbcType.DECIMAL, id=true),
	        @Result(column="IDTURNO", property="idturno", jdbcType=JdbcType.DECIMAL, id=true),
	        @Result(column="FECHASOLICITUD", property="fechasolicitud", jdbcType=JdbcType.DATE),
	        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
	        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
	        @Result(column="FECHAVALIDACION", property="fechavalidacion", jdbcType=JdbcType.TIMESTAMP),
	        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP),
	        @Result(column="OBSERVACIONESSOLICITUD", property="observacionessolicitud", jdbcType=JdbcType.VARCHAR),
	        @Result(column="OBSERVACIONESVALIDACION", property="observacionesvalidacion", jdbcType=JdbcType.VARCHAR),
	        @Result(column="OBSERVACIONESBAJA", property="observacionesbaja", jdbcType=JdbcType.VARCHAR),
	        @Result(column="FECHASOLICITUDBAJA", property="fechasolicitudbaja", jdbcType=JdbcType.TIMESTAMP),
	        @Result(column="FECHADENEGACION", property="fechadenegacion", jdbcType=JdbcType.TIMESTAMP),
	        @Result(column="OBSERVACIONESDENEGACION", property="observacionesdenegacion", jdbcType=JdbcType.VARCHAR),
	        @Result(column="OBSERVACIONESVALBAJA", property="observacionesvalbaja", jdbcType=JdbcType.VARCHAR) })
	    List<InscripcionesItem> busquedaTarjeta(InscripcionesItem inscripcionesItem,Short idInstitucion);
	 
	 @SelectProvider(type=ScsInscripcionesTurnoSqlExtendsProvider.class, method="busquedaTrabajosGuardias")
	 @Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
		 @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
	        @Result(column="IDPERSONA", property="idpersona", jdbcType=JdbcType.DECIMAL, id=true),
	        @Result(column="IDTURNO", property="idturno", jdbcType=JdbcType.DECIMAL, id=true),
	        @Result(column="FECHASOLICITUD", property="fechasolicitud", jdbcType=JdbcType.DATE),
	        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
	        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
	        @Result(column="FECHAVALIDACION", property="fechavalidacion", jdbcType=JdbcType.TIMESTAMP),
	        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP),
	        @Result(column="OBSERVACIONESSOLICITUD", property="observacionessolicitud", jdbcType=JdbcType.VARCHAR),
	        @Result(column="OBSERVACIONESVALIDACION", property="observacionesvalidacion", jdbcType=JdbcType.VARCHAR),
	        @Result(column="OBSERVACIONESBAJA", property="observacionesbaja", jdbcType=JdbcType.VARCHAR),
	        @Result(column="FECHASOLICITUDBAJA", property="fechasolicitudbaja", jdbcType=JdbcType.TIMESTAMP),
	        @Result(column="FECHADENEGACION", property="fechadenegacion", jdbcType=JdbcType.TIMESTAMP),
	        @Result(column="OBSERVACIONESDENEGACION", property="observacionesdenegacion", jdbcType=JdbcType.VARCHAR),
	        @Result(column="OBSERVACIONESVALBAJA", property="observacionesvalbaja", jdbcType=JdbcType.VARCHAR) })
	    List<InscripcionesItem> busquedaTrabajosGuardias(InscripcionesItem inscripcionesItem,Short idInstitucion, String fechaActual);
	 
	 @SelectProvider(type=ScsInscripcionesTurnoSqlExtendsProvider.class, method="busquedaTrabajosPendientes")
	 @Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
		 @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
	        @Result(column="IDPERSONA", property="idpersona", jdbcType=JdbcType.DECIMAL, id=true),
	        @Result(column="IDTURNO", property="idturno", jdbcType=JdbcType.DECIMAL, id=true),
	        @Result(column="FECHASOLICITUD", property="fechasolicitud", jdbcType=JdbcType.DATE),
	        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
	        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
	        @Result(column="FECHAVALIDACION", property="fechavalidacion", jdbcType=JdbcType.TIMESTAMP),
	        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP),
	        @Result(column="OBSERVACIONESSOLICITUD", property="observacionessolicitud", jdbcType=JdbcType.VARCHAR),
	        @Result(column="OBSERVACIONESVALIDACION", property="observacionesvalidacion", jdbcType=JdbcType.VARCHAR),
	        @Result(column="OBSERVACIONESBAJA", property="observacionesbaja", jdbcType=JdbcType.VARCHAR),
	        @Result(column="FECHASOLICITUDBAJA", property="fechasolicitudbaja", jdbcType=JdbcType.TIMESTAMP),
	        @Result(column="FECHADENEGACION", property="fechadenegacion", jdbcType=JdbcType.TIMESTAMP),
	        @Result(column="OBSERVACIONESDENEGACION", property="observacionesdenegacion", jdbcType=JdbcType.VARCHAR),
	        @Result(column="OBSERVACIONESVALBAJA", property="observacionesvalbaja", jdbcType=JdbcType.VARCHAR) })
	    List<InscripcionesItem> busquedaTrabajosPendientes(InscripcionesItem inscripcionesItem,Short idInstitucion, String fechaActual);
}
