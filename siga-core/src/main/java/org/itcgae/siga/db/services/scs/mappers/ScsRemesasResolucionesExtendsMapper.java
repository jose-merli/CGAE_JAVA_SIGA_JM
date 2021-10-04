package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.RemesasResolucionItem;
import org.itcgae.siga.db.services.scs.providers.ScsRemesasResolucionesExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsRemesasResolucionesExtendsMapper {

	@SelectProvider(type = ScsRemesasResolucionesExtendsProvider.class ,method = "buscarRemesasResoluciones")
	@Results({
		@Result(column = "IDREMESARESOLUCION", property = "idRemesaResultado", jdbcType = JdbcType.NUMERIC),
		@Result(column = "PREFIJO", property = "numRemesaPrefijo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SUFIJO", property = "numRemesaSufijo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREFICHERO", property = "nombreFichero", jdbcType = JdbcType.VARCHAR),
		@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHACARGA", property = "fechaCarga", jdbcType = JdbcType.DATE),
		@Result(column = "FECHARESOLUCION", property = "fechaResolucion", jdbcType = JdbcType.DATE),
		@Result(column = "IDREMESA", property = "idRemesa", jdbcType = JdbcType.NUMERIC)
	})
	List<RemesasResolucionItem> buscarRemesasResoluciones(RemesasResolucionItem remesasResolucionItem, int idInstitucion);

}
