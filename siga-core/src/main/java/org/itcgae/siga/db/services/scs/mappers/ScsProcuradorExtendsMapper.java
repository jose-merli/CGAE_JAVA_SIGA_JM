package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.ProcuradorItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.ScsProcuradorMapper;
import org.itcgae.siga.db.services.scs.providers.ScsProcuradorSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsProcuradorExtendsMapper extends ScsProcuradorMapper{

	@SelectProvider(type = ScsProcuradorSqlExtendsProvider.class, method = "searchProcuradores")
	@Results({
		@Result(column = "IDPROCURADOR", property = "idProcurador", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS1", property = "apellido1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS2", property = "apellido2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DOMICILIO", property = "domicilio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOPOSTAL", property = "codigoPostal", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPOBLACION", property = "idPoblacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPROVINCIA", property = "idProvincia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TELEFONO1", property = "telefono1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TELEFONO2", property = "telefono2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FAX1", property = "fax1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NCOLEGIADO", property = "nColegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCOLPROCURADOR", property = "idColProcurador", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGO", property = "codigoExt", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREPOBLACION", property = "nombrePoblacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREPROVINCIA", property = "nombreProvincia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "EMAIL", property = "email", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREAPE", property = "nombreApe", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombreColProcurador", property = "nombreColProcurador", jdbcType = JdbcType.VARCHAR),
	})
	List<ProcuradorItem> searchProcuradores(ProcuradorItem procuradorItem, Short idInstitucion);

	@SelectProvider(type = ScsProcuradorSqlExtendsProvider.class, method = "getIdProcurador")
	@Results({ @Result(column = "IDPROCURADOR", property = "newId", jdbcType = JdbcType.VARCHAR)
	
	})
	NewIdDTO getIdProcurador(Short idInstitucion);
	
	
	@SelectProvider(type= ScsProcuradorSqlExtendsProvider.class, method ="buscadorProcurador")
	@Results({
		@Result(column = "IDPROCURADOR", property = "idProcurador", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "abreviatura", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS1", property = "apellido1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS2", property = "apellido2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DOMICILIO", property = "domicilio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOPOSTAL", property = "codigoPostal", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPOBLACION", property = "idPoblacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPROVINCIA", property = "idProvincia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TELEFONO1", property = "telefono1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TELEFONO2", property = "telefono2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FAX1", property = "fax1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NCOLEGIADO", property = "nColegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREAPE", property = "nombreApe", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCOLPROCURADOR", property = "idColProcurador", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGO", property = "codigoExt", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREPOBLACION", property = "nombrePoblacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREPROVINCIA", property = "nombreProvincia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "EMAIL", property = "email", jdbcType = JdbcType.VARCHAR),
		})
	List<ProcuradorItem> buscadorProcurador(String idInstitucion, ProcuradorItem procuradorItem);
}

