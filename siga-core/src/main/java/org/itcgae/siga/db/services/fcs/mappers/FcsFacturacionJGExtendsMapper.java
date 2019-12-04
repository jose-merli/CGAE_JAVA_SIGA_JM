package org.itcgae.siga.db.services.fcs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.scs.FacturacionItem;
import org.itcgae.siga.db.services.adm.providers.AdmUsuariosSqlExtendsProvider;
import org.itcgae.siga.db.services.fcs.providers.FcsFacturacionJGSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.itcgae.siga.db.mappers.FcsFacturacionjgMapper;

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
}
