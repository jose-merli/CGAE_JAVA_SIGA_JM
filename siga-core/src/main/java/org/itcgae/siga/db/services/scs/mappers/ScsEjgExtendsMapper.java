package org.itcgae.siga.db.services.scs.mappers;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.scs.DocumentacionEjgItem;
import org.itcgae.siga.DTO.scs.EjgItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ScsEjgMapper;
import org.itcgae.siga.db.services.scs.providers.ScsEjgSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTipoejgSqlExtendsProvider;

public interface ScsEjgExtendsMapper extends ScsEjgMapper {

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "comboCreadoDesde")
	@Results({ 
		@Result(column = "ORIGENAPERTURA", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> comboCreadoDesde(String idlenguaje, String string);

	
	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "busquedaEJG")
	@Results({ 

		@Result(column = "anio", property = "annio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idtipoejg", property = "tipoEJG", jdbcType = JdbcType.VARCHAR),
		@Result(column = "numero", property = "numero", jdbcType = JdbcType.VARCHAR),
		@Result(column = "numejg", property = "numColegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMANIO", property = "numAnnioProcedimiento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TURNO", property = "turno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TURNODES", property = "turnoDes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechaapertura", property = "fechaApertura", jdbcType = JdbcType.DATE),
		@Result(column = "fechamodificacion", property = "fechaModificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRESOLICITANTE", property = "nombreApeSolicitante", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREletrado", property = "apellidosYNombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ESTADOEJG", property = "estadoEJG", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ejg.numeroprocedimiento", property = "procedimiento", jdbcType = JdbcType.VARCHAR),
	})
	List<EjgItem> busquedaEJG(EjgItem ejgItem, String string);

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "datosEJG")
	@Results({ 


//		sql.SELECT("ejg.fechapresentacion");
//		sql.SELECT("ejg.idtipocolegio");
//		sql.SELECT("ejg.fechalimitepresentacion");		
//		sql.SELECT("rectipodictamen.descripcion AS dictamen");
//		sql.SELECT("rectiporesolucion.descripcion AS resolucion");
//		sql.SELECT("rectiporesolauto.descripcion AS resolauto");
//		sql.SELECT("personadesigna.apellidos1 || ' ' || personadesigna.apellidos2 || ', ' || personadesigna.nombre AS nombreletradodesigna");
//		sql.SELECT("EXPEDIENTE.anioexpediente");
//		sql.SELECT("EXPEDIENTE.numeroexpediente");
//		sql.SELECT("EXPEDIENTE.IDTIPOEXPEDIENTE");
		@Result(column = "anio", property = "annio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idtipoejg", property = "tipoEJG", jdbcType = JdbcType.VARCHAR),
		@Result(column = "numero", property = "numero", jdbcType = JdbcType.VARCHAR),
		@Result(column = "numejg", property = "numColegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMANIO", property = "numAnnioProcedimiento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TURNO", property = "turno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TURNODES", property = "turnoDes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechaapertura", property = "fechaApertura", jdbcType = JdbcType.DATE),
		@Result(column = "fechamodificacion", property = "fechaModificacion", jdbcType = JdbcType.DATE),
		@Result(column = "NOMBRESOLICITANTE", property = "nombreApeSolicitante", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombreletrado", property = "apellidosYNombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ESTADOEJG", property = "estadoEJG", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ejg.numeroprocedimiento", property = "procedimiento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "dictamen", property = "dictamenSing", jdbcType = JdbcType.VARCHAR),
		@Result(column = "resolucion", property = "resolucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "resolauto", property = "impugnacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechapresentacion", property = "fechapresentacion", jdbcType = JdbcType.DATE),
		@Result(column = "fechalimitepresentacion", property = "fechalimitepresentacion", jdbcType = JdbcType.DATE),
		@Result(column = "anioexpediente", property = "anioexpediente", jdbcType = JdbcType.VARCHAR),
		@Result(column = "numeroexpediente", property = "numeroexpediente", jdbcType = JdbcType.VARCHAR),


	})
	List<EjgItem> datosEJG(EjgItem ejgItem, String string);

}
