package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.ListaContrarioEJGJusticiableItem;
import org.itcgae.siga.DTOs.scs.ListaContrarioJusticiableItem;
import org.itcgae.siga.db.mappers.ScsContrariosejgMapper;
import org.itcgae.siga.db.services.scs.providers.ScsContrariosejgSqlExtendsProvider;

public interface ScsContrariosejgExtendsMapper extends ScsContrariosejgMapper{

	@SelectProvider(type = ScsContrariosejgSqlExtendsProvider.class, method = "busquedaListaContrariosEJG")
	@Results({ @Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idtipoejg", property = "idtipoejg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ABOGADO", property = "abogado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idrepresentantelegal", property = "representante", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PROCURADOR", property = "procurador", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOSNOMBRE", property = "apellidosnombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idabogadocontrarioejg", property = "idabogadocontrario", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idprocurador", property = "idprocurador", jdbcType = JdbcType.VARCHAR),
			@Result(column = "direccion", property = "direccion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
			@Result(column = "IDINSTITUCION_PROCU", property = "idInstitucionProc", jdbcType = JdbcType.VARCHAR)})
	List<ListaContrarioEJGJusticiableItem> busquedaListaContrariosEJG(EjgItem item, Short idInstitucion,
			Boolean historico);
}
