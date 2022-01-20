package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.LogRemesaResolucionItem;
import org.itcgae.siga.DTOs.scs.RemesasItem;
import org.itcgae.siga.DTOs.scs.RemesasResolucionItem;
import org.itcgae.siga.db.entities.CajgRemesaresolucionfichero;
import org.itcgae.siga.db.entities.EcomOperacionTipoaccion;
import org.itcgae.siga.db.services.scs.providers.ScsCargaDesignaProcuradoresExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsRemesasExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsRemesasResolucionesExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsRemesasResolucionesExtendsMapper {

	@SelectProvider(type = ScsRemesasResolucionesExtendsProvider.class ,method = "buscarRemesasResoluciones")
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
	List<RemesasResolucionItem> buscarRemesasResoluciones(RemesasResolucionItem remesasResolucionItem, int idInstitucion, int tamMaximo);
	
	@SelectProvider(type = ScsRemesasResolucionesExtendsProvider.class, method = "getMaxIdRemesaResolucion")
	@Results({
		@Result(column = "IDREMESARESOLUCION", property = "idRemesaResolucion", jdbcType = JdbcType.VARCHAR)
	})
	RemesasResolucionItem getMaxIdRemesaResolucion(Short idInstitucion);

	@SelectProvider(type = ScsRemesasResolucionesExtendsProvider.class, method = "getMaxIdRemesaResolucionFichero")
	@Results({
		@Result(column = "IDREMESARESOLUCIONFICHERO", property = "idremesaresolucionfichero", jdbcType = JdbcType.INTEGER)
	})
	CajgRemesaresolucionfichero getMaxIdRemesaResolucionFichero();

	@SelectProvider(type = ScsRemesasResolucionesExtendsProvider.class, method = "ecomOperacionTipoAccion")
	@Results({
		@Result(column = "IDOPERACION", property = "idoperacion", jdbcType = JdbcType.INTEGER)
	})
	List<EcomOperacionTipoaccion> ecomOperacionTipoAccion(String tipoCAJG);
	
	@SelectProvider(type = ScsRemesasResolucionesExtendsProvider.class, method = "logRemesaResoluciones")
	@Results({
		@Result(column = "CODIGO", property = "codigo", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PARAMETROSERROR", property = "parametrosError", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMEROLINEA", property = "numeroLinea", jdbcType = JdbcType.VARCHAR),
	})
	List<LogRemesaResolucionItem> logRemesaResoluciones(String idInstitucion, String idRemesaResolucion) ;
	
	@SelectProvider(type = ScsRemesasResolucionesExtendsProvider.class, method = "contador")
	@Results({
		@Result(column = "IDCONTADOR", property = "idcontador", jdbcType = JdbcType.VARCHAR)
	})
	String contador(String idInstitucion, String idTipoRemesa);
	
}
