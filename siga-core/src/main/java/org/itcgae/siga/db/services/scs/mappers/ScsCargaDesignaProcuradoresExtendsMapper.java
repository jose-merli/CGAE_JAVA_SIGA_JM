package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.RemesasResolucionItem;
import org.itcgae.siga.db.entities.EcomOperacionTipoaccion;
import org.itcgae.siga.db.services.scs.providers.ScsCargaDesignaProcuradoresExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsRemesasResolucionesExtendsProvider;


public interface ScsCargaDesignaProcuradoresExtendsMapper {
	
	@SelectProvider(type = ScsCargaDesignaProcuradoresExtendsProvider.class ,method = "buscarDesignaProcuradores")
	@Results({
		@Result(column = "IDREMESARESOLUCION", property = "idRemesaResolucion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "PREFIJO", property = "numRemesaPrefijo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SUFIJO", property = "numRemesaSufijo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMERO", property= "numRemesaNumero", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREFICHERO", property = "nombreFichero", jdbcType = JdbcType.VARCHAR),
		@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHACARGA", property = "fechaCarga", jdbcType = JdbcType.DATE),
		@Result(column = "FECHARESOLUCION", property = "fechaResolucion", jdbcType = JdbcType.DATE),
		@Result(column = "IDREMESA", property = "idRemesa", jdbcType = JdbcType.NUMERIC),
		@Result(column = "LOGGENERADO", property ="log", jdbcType = JdbcType.NUMERIC)
	})
	List<RemesasResolucionItem> buscarDesignaProcuradores(RemesasResolucionItem remesasResolucionItem, int idInstitucion);
	
	@SelectProvider(type = ScsCargaDesignaProcuradoresExtendsProvider.class, method = "nombreContador")
	@Results({
		@Result(column = "IDCONTADOR", property = "idcontador", jdbcType = JdbcType.VARCHAR)
	})
	String nombreContador (String idInstitucion);
	
	@SelectProvider(type = ScsCargaDesignaProcuradoresExtendsProvider.class, method = "ecomOperacionTipoAccion")
	@Results({
		@Result(column = "IDOPERACION", property = "idoperacion", jdbcType = JdbcType.INTEGER)
	})
	List<EcomOperacionTipoaccion> ecomOperacionTipoAccion(String tipoCAJG);
	
}
