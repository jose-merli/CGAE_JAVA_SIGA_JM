package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.RemesasBusquedaItem;
import org.itcgae.siga.DTOs.scs.RemesasItem;
import org.itcgae.siga.DTOs.scs.RemesasItem2;
import org.itcgae.siga.db.services.scs.providers.ScsRemesasExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsRemesasExtendsMapper{

	@SelectProvider(type = ScsRemesasExtendsProvider.class, method = "comboEstado")
	@Results({ 
		@Result(column = "IDESTADO", property = "value", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> comboEstado(String string);
	
	@SelectProvider(type = ScsRemesasExtendsProvider.class, method = "buscarRemesas")
	@Results({
		@Result(column = "IDREMESA", property = "idRemesa", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "PREFIJO", property = "prefijo", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.NUMERIC),
		@Result(column = "SUFIJO", property = "sufijo", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHA_GENERACION", property = "fechaGeneracion", jdbcType = JdbcType.DATE),
		@Result(column = "FECHA_ENVIO", property = "fechaEnvio", jdbcType = JdbcType.DATE),
		@Result(column = "FECHA_RECEPCION", property = "fechaRecepcion", jdbcType = JdbcType.DATE),
		@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "INCIDENCIAS_EJG", property = "incidenciasEJG", jdbcType = JdbcType.NUMERIC),
		@Result(column = "TOTAL_EJG", property = "totalEJG", jdbcType = JdbcType.NUMERIC)
	})
	List<RemesasItem> buscarRemesas(RemesasBusquedaItem remesasBusquedaItem, Short idInstitucion, String idLenguaje);
	
	@SelectProvider(type = ScsRemesasExtendsProvider.class, method = "isEstadoRemesaIniciada")
	@Results({
		@Result(column = "IDREMESA", property = "idRemesa", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDESTADO", property = "idEstado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAREMESA", property = "fechaRemesa", jdbcType = JdbcType.VARCHAR)
	})
	List<RemesasItem2> isEstadoRemesaIniciada(RemesasBusquedaItem remesasBusquedaItem, Short idInstitucion);
}
