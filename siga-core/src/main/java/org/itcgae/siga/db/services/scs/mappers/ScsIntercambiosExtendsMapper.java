package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.CargaMasivaProcuradorBusquedaItem;
import org.itcgae.siga.DTOs.scs.CargaMasivaProcuradorItem;
import org.itcgae.siga.db.entities.ScsEstadoejg;
import org.itcgae.siga.db.services.scs.providers.ScsIntercambiosExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsIntercambiosExtendsMapper {
	
	@SelectProvider(type = ScsIntercambiosExtendsProvider.class, method = "listadoCargaMasivaProcuradores")
	@Results({
		@Result(column = "IDCARGAMASIVA", property = "idCargaMasiva", jdbcType = JdbcType.NUMERIC),
		@Result(column = "TIPOCARGA", property = "tipoCarga", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "FECHACARGA", property = "fechaCarga", jdbcType = JdbcType.DATE),
		@Result(column = "IDFICHERO", property = "idFichero", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDFICHEROLOG", property = "idFicheroLog", jdbcType = JdbcType.NUMERIC),
		@Result(column = "FECHAMODIFICACION", property = "fechaModificacion", jdbcType = JdbcType.DATE),
		@Result(column = "NUMREGISTROS", property = "numRegistros", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NOMBREFICHERO", property = "nombreFichero", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMREGISTROSERRONEOS", property = "numRegistrosErroneos", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "usuario", jdbcType = JdbcType.VARCHAR)
	})
	List<CargaMasivaProcuradorItem> listadoCargaMasivaProcuradores(CargaMasivaProcuradorBusquedaItem cargaMasivaItem, Short idInstitucion, Integer tamMaximo);
	
	@SelectProvider(type = ScsIntercambiosExtendsProvider.class, method = "getMaxIdEstadoPorEJG")
	@Results({
		@Result(column = "IDESTADOPOREJG", property = "idestadoporejg", jdbcType = JdbcType.NUMERIC)
	})
	ScsEstadoejg getMaxIdEstadoPorEJG(String anio, Short idInstitucion, String numero, String idTipoEJG);

}
