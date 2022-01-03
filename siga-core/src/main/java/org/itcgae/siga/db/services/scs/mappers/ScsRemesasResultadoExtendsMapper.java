package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.RemesasResultadoFicheroItem;
import org.itcgae.siga.DTOs.scs.RemesasResultadoItem;
import org.itcgae.siga.db.entities.CajgRemesa;
import org.itcgae.siga.db.services.scs.providers.ScsRemesasResultadosExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsRemesasResultadoExtendsMapper {

	@SelectProvider(type = ScsRemesasResultadosExtendsProvider.class, method = "buscarRemesasResultado")
	@Results({
		@Result(column = "IDREMESARESOLUCION", property = "idRemesaResultado", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NUMREMESAPREFIJO", property = "numRemesaPrefijo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMREMESASUFIJO", property = "numRemesaSufijo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMREMESANUMERO", property = "numRemesaNumero", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREFICHERO", property = "nombreFichero", jdbcType = JdbcType.VARCHAR),
		@Result(column = "OBSERVACIONESREMESARESULTADO", property = "observacionesRemesaResultado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHACARGAREMESARESULTADO", property = "fechaCargaRemesaResultado", jdbcType = JdbcType.DATE),
		@Result(column = "FECHARESOLUCIONREMESARESULTADO", property = "fechaResolucionRemesaResultado", jdbcType = JdbcType.DATE),
		@Result(column = "IDREMESA", property = "idRemesa", jdbcType = JdbcType.NUMERIC),
		@Result(column = "PREFIJOREMESA", property = "prefijoRemesa", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SUFIJOREMESA", property = "sufijoRemesa", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMEROREMESA", property = "numeroRemesa", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCIONREMESA", property = "descripcionRemesa", jdbcType = JdbcType.VARCHAR)
	})
	List<RemesasResultadoItem> buscarRemesasResultado(RemesasResultadoItem remesasBusquedaItem, Short idInstitucion, int tamMaximo);
	
	@SelectProvider(type = ScsRemesasResultadosExtendsProvider.class, method = "buscarLineasFichero")
	@Results({
		@Result(column = "IDREMESARESOLUCIONFICHERO", property = "idRemesaResolucionFichero", jdbcType = JdbcType.NUMERIC),
		@Result(column = "LINEA", property = "linea", jdbcType = JdbcType.VARCHAR)
	})
	List<RemesasResultadoFicheroItem> buscarLineasFichero(int idRemesaResultado, Short idInstitucion);
	
	
	@InsertProvider(type = ScsRemesasResultadosExtendsProvider.class, method = "insertSelective")
	int insertSelective(RemesasResultadoItem remesasResultadoItem, Short idInstitucion, Integer idUsuario);

	
}
