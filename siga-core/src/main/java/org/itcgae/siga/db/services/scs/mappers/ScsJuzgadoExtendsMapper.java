package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.JuzgadoItem;
import org.itcgae.siga.db.mappers.ScsJuzgadoMapper;
import org.itcgae.siga.db.services.scs.providers.ScsJuzgadoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsJuzgadoExtendsMapper extends ScsJuzgadoMapper{

	@SelectProvider(type = ScsJuzgadoSqlExtendsProvider.class, method = "searchCourt")
	@Results({
		@Result(column = "IDJUZGADO", property = "idJuzgado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DOMICILIO", property = "domicilio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOPOSTAL", property = "codigoPostal", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPOBLACION", property = "idPoblacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPROVINCIA", property = "idProvincia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TELEFONO1", property = "telefono1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TELEFONO2", property = "telefono2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FAX1", property = "fax", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOEXT", property = "codigoExt", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOPROCURADOR", property = "codigoProcurador", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VISIBLE", property = "visible", jdbcType = JdbcType.VARCHAR),
		@Result(column = "MOVIL", property = "movil", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ESDECANO", property = "esDecano", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOEXT2", property = "codigoExt2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "EMAIL", property = "email", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ISCODIGOEJIS", property = "isCodigoEjis", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VISIBLEMOVIL", property = "visibleMovil", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHACODIGOEJIS", property = "fechaCodigoEjis", jdbcType = JdbcType.VARCHAR),
		@Result(column = "MOVIL", property = "movil", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREPOBLACION", property = "nombrePoblacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREPROVINCIA", property = "nombreProvincia", jdbcType = JdbcType.VARCHAR),

	})
	List<JuzgadoItem> searchCourt(JuzgadoItem juzgadoItem, Short idInstitucion);

	@SelectProvider(type = ScsJuzgadoSqlExtendsProvider.class, method = "getIdJuzgado")
	@Results({ @Result(column = "IDJUZGADO", property = "newId", jdbcType = JdbcType.VARCHAR)
	
	})
	NewIdDTO getIdJuzgado(Short idInstitucion);
	
	
	@SelectProvider(type = ScsJuzgadoSqlExtendsProvider.class, method = "comboJuzgado")
	@Results({
		@Result(column = "IDJUZGADO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> comboJuzgado(Short idLenguaje, Short idInstitucion);
	
	@SelectProvider(type = ScsJuzgadoSqlExtendsProvider.class, method = "comboJuzgadoCdgoExt")
	@Results({
		@Result(column = "CODIGOEXT", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> comboJuzgadoCdgoExt(Short idLenguaje, Short idInstitucion);

	@SelectProvider(type = ScsJuzgadoSqlExtendsProvider.class, method = "comboJuzgados")
	@Results({
		@Result(column = "IDJUZGADO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> comboJuzgados(Short idInstitucion);
	@SelectProvider(type = ScsJuzgadoSqlExtendsProvider.class, method = "comboJuzgadoDesignaciones")
	@Results({
		@Result(column = "CODIGOEXT2", property = "codigoExt2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREPOBLACION", property = "nombrePoblacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDJUZGADO", property = "idJuzgado", jdbcType = JdbcType.VARCHAR)
	})
	List<JuzgadoItem> comboJuzgadoDesignaciones(Short idLenguaje, Short idInstitucion,String idJuzgado);
	
	@SelectProvider(type = ScsJuzgadoSqlExtendsProvider.class, method = "getJuzgadosByIdTurno")
	@Results({
		@Result(column = "idjuzgado", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombre", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> getJuzgadosByIdTurno(Short idInstitucion, String idTurnoo);

}
