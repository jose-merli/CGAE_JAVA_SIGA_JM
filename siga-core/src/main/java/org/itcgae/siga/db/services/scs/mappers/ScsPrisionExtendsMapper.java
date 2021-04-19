package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.PrisionItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.ScsPrisionMapper;
import org.itcgae.siga.db.services.scs.providers.ScsDesignacionesSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsPrisionSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsPrisionExtendsMapper extends ScsPrisionMapper{

	@SelectProvider(type = ScsPrisionSqlExtendsProvider.class, method = "searchPrisiones")
	@Results({
		@Result(column = "IDPRISION", property = "idPrision", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DOMICILIO", property = "domicilio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOPOSTAL", property = "codigoPostal", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPOBLACION", property = "idPoblacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPROVINCIA", property = "idProvincia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TELEFONO1", property = "telefono1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TELEFONO2", property = "telefono2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FAX1", property = "fax", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOEXT", property = "codigoExt", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREPOBLACION", property = "nombrePoblacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREPROVINCIA", property = "nombreProvincia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "EMAIL", property = "email", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VISIBLEMOVIL", property = "visibleMovil", jdbcType = JdbcType.NUMERIC)
	})
	List<PrisionItem> searchPrisiones(PrisionItem prisionItem, Short idInstitucion);

	@SelectProvider(type = ScsPrisionSqlExtendsProvider.class, method = "getIdPrision")
	@Results({ @Result(column = "IDPRISION", property = "newId", jdbcType = JdbcType.VARCHAR)
	
	})
	NewIdDTO getIdPrision(Short idInstitucion);
	
	@SelectProvider(type = ScsPrisionSqlExtendsProvider.class, method = "getComboPrisiones")
	@Results({ @Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPRISION", property = "value", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> getComboPrisiones(Short idInstitucion);
	
}
