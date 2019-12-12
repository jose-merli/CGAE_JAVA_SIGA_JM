package org.itcgae.siga.db.services.fcs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.FacturacionItem;
import org.itcgae.siga.db.mappers.FcsFacturacionjgMapper;
import org.itcgae.siga.db.services.fcs.providers.FcsFacturacionJGSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsPersonajgSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface FcsFacturacionJGExtendsMapper extends FcsFacturacionjgMapper {

	@SelectProvider(type = FcsFacturacionJGSqlExtendsProvider.class, method = "buscarFacturaciones")
	@Results({ 
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ABREVIATURA", property = "abreviatura", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDFACTURACION", property = "idFacturacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHADESDE", property = "fechaDesde", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAHASTA", property = "fechaHasta", jdbcType = JdbcType.DATE),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "REGULARIZACION", property = "regularizacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESESTADO", property = "desEstado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDESTADO", property = "idEstado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAESTADO", property = "fechaEstado", jdbcType = JdbcType.DATE),
		@Result(column = "IMPORTETOTAL", property = "importeTotal", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTEPAGADO", property = "importePagado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPARTIDAPRESUPUESTARIA", property = "idPartidaPresupuestaria", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTEPENDIENTE", property = "importePendiente", jdbcType = JdbcType.VARCHAR),
	})
	List<FacturacionItem> buscarFacturaciones(FacturacionItem facturacionItem, String idInstitucion);
	
	@SelectProvider(type = FcsFacturacionJGSqlExtendsProvider.class, method = "datosFacturacion")
	@Results({ 
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDFACTURACION", property = "idFacturacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHADESDE", property = "fechaDesde", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAHASTA", property = "fechaHasta", jdbcType = JdbcType.DATE),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "REGULARIZACION", property = "regularizacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PREVISION", property = "prevision", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VISIBLE", property = "visible", jdbcType = JdbcType.VARCHAR),
	})
	List<FacturacionItem> datosFacturacion(String idFacturacion, String idInstitucion);
	
	@SelectProvider(type = FcsFacturacionJGSqlExtendsProvider.class, method = "historicoFacturacion")
	@Results({ 
		@Result(column = "idestadofacturacion", property = "idEstado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "descripcion", property = "desEstado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechaestado", property = "fechaEstado", jdbcType = JdbcType.DATE),
	})
	List<FacturacionItem> historicoFacturacion(String idFacturacion, String idLenguaje, String idInstitucion);
	
	@SelectProvider(type = FcsFacturacionJGSqlExtendsProvider.class, method = "getIdFacturacion")
	@Results({ @Result(column = "IDFACTURACION", property = "newId", jdbcType = JdbcType.VARCHAR)
	})
	NewIdDTO getIdFacturacion(Short idInstitucion);
}
