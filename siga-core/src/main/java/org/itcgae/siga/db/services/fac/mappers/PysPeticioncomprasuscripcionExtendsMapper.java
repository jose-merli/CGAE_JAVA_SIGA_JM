package org.itcgae.siga.db.services.fac.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.FichaCompraSuscripcionItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.services.fac.providers.PysPeticioncomprasuscripcionSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface PysPeticioncomprasuscripcionExtendsMapper {

	@SelectProvider(type = PysPeticioncomprasuscripcionSqlExtendsProvider.class, method = "getFichaCompraSuscripcion")
	@Results({ 
			// TARJETA CLIENTE
			@Result(column = "idInstitucion", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idPersona", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombre", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "apellidos", property = "apellidos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idtipoidentificacion", property = "idtipoidentificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nif", property = "nif", jdbcType = JdbcType.VARCHAR), 

			// TARJETA SOLICITUD
			@Result(column = "nSolicitud", property = "nSolicitud", jdbcType = JdbcType.VARCHAR),
			@Result(column = "usuModificacion", property = "usuModificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechaSolicitud", property = "fechaSolicitud", jdbcType = JdbcType.DATE),
			@Result(column = "fechaAprobacion", property = "fechaAprobacion", jdbcType = JdbcType.DATE),
			@Result(column = "fechaDenegacion", property = "fechaDenegacion", jdbcType = JdbcType.DATE),
			@Result(column = "fechaAnulacion", property = "fechaAnulacion", jdbcType = JdbcType.DATE), })
	FichaCompraSuscripcionItem getFichaCompraSuscripcion(Short idInstitucion, FichaCompraSuscripcionItem ficha);

}
