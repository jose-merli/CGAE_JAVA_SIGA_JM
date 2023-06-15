package org.itcgae.siga.db.services.fcs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.FcsEstadosfacturacionMapper;
import org.itcgae.siga.db.services.fcs.providers.FcsEstadoFacturacionSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface FcsEstadosFacturacionExtendsMapper extends FcsEstadosfacturacionMapper {
	
	@SelectProvider(type = FcsEstadoFacturacionSqlExtendsProvider.class, method = "estadosFacturacion")
	@Results({ 
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDESTADOFACTURACION", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> estadosFacturacion(String idLenguaje);	
	
	@SelectProvider(type = FcsEstadoFacturacionSqlExtendsProvider.class, method = "estadosPagos")
	@Results({ 
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDESTADOPAGOSJG", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> estadosPagos(String idLenguaje);		
}
