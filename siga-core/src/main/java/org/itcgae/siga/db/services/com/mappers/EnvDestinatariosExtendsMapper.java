package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.com.DestinatarioItem;
import org.itcgae.siga.db.services.com.providers.EnvDestinatariosExtendsSqlProvider;

public interface EnvDestinatariosExtendsMapper {

	
	@SelectProvider(type = EnvDestinatariosExtendsSqlProvider.class, method = "selectDestinatarios")
	@Results({@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIFCIF", property = "NIFCIF", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS1", property = "apellidos1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS2", property = "apellidos2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DOMICILIO", property = "domicilio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "MOVIL", property = "movil", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRECOMPLETO", property = "nombreCompleto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CORREOELECTRONICO", property = "correoElectronico", jdbcType = JdbcType.VARCHAR)
	})
	List<DestinatarioItem> selectDestinatarios(Short idInstitucion, String idEnvio);
	
	@SelectProvider(type = EnvDestinatariosExtendsSqlProvider.class, method = "selectDestinatariosComunicaciones")
	@Results({@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIFCIF", property = "NIFCIF", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS1", property = "apellidos1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS2", property = "apellidos2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DOMICILIO", property = "domicilio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "MOVIL", property = "movil", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRECOMPLETO", property = "nombreCompleto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CORREOELECTRONICO", property = "correoElectronico", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DIRECCION", property = "direccion", jdbcType = JdbcType.VARCHAR)
	})
	List<DestinatarioItem> selectDestinatariosComunicaciones(Short idInstitucion, String idEnvio);
}
