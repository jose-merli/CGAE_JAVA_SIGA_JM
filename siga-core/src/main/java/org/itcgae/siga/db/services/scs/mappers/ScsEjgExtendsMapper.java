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

		@Result(column = "ejg.anio", property = "anio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ejg.idtipoejg", property = "tipoEJG", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ejg.idinstitucion", property = "idInstitucion", jdbcType = JdbcType.DECIMAL),
		@Result(column = "ejg.numero", property = "numero", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ejg.numejg", property = "numEJG", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMANIO", property = "numAnnioProcedimiento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TURNO", property = "turno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TURNODES", property = "turnoDes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ejg.fechaapertura", property = "fechaApertura", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ejg.fechamodificacion", property = "fechaModificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREletrado", property = "apellidosYNombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ESTADOEJG", property = "estadoEJG", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRESOLICITANTE", property = "nombreApeSolicitante", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ejg.numeroprocedimiento", property = "procedimiento", jdbcType = JdbcType.VARCHAR),

	})
	List<EjgItem> busquedaEJG(EjgItem ejgItem, String string);

}
