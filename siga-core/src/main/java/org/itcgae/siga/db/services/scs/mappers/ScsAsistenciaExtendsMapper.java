package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableItem;
import org.itcgae.siga.db.mappers.ScsAsistenciaMapper;
import org.itcgae.siga.db.mappers.ScsAsistenciaSqlProvider;
import org.itcgae.siga.db.services.scs.providers.ScsAsistenciaSqlExtendsProvider;
import org.itcgae.siga.DTOs.scs.AsuntosAsistenciaItem;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsAsistenciaExtendsMapper extends ScsAsistenciaMapper{

	@SelectProvider(type = ScsAsistenciaSqlExtendsProvider.class, method = "searchClaveAsistencia")
	@Results({ @Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "ASUNTO", property = "asunto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TURNOGUARDIA", property = "turnoGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "INTERESADO", property = "interesado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "JUZGADO", property = "juzgado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "LETRADO", property = "letrado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAHORA", property = "fecha", jdbcType = JdbcType.DATE),
		@Result(column = "NUMEROPROCEDIMIENTO", property = "numeroProcedimiento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOASISTENCIA", property = "idTipoAsistencia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOASISTENCIA", property = "tipo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMERODILIGENCIA", property = "numeroDiligencia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "dilnigproc", property = "dilnigproc", jdbcType = JdbcType.VARCHAR)
	})
	List<AsuntosJusticiableItem> searchClaveAsistencia(AsuntosJusticiableItem asuntosJusticiableItem, Integer tamMaximo, String idLenguaje);
	

	@SelectProvider(type = ScsAsistenciaSqlExtendsProvider.class, method = "getAsuntoTipoAsistencia")
	@Results({ 
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ASUNTO", property = "asunto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHA", property = "fecha", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),	
		@Result(column = "TURNOGUARDIA", property = "turnoGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGO", property = "codigo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DATOSINTERES", property = "datosInteres", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONAASISTIDO", property = "idPersonaAsistido", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONACOLEGIADO", property = "idPersonaColegiado", jdbcType = JdbcType.VARCHAR)
	})
	AsuntosAsistenciaItem getAsuntoTipoAsistencia(AsuntosClaveJusticiableItem asuntoClave, String idLenguaje);
	
	 @UpdateProvider(type=ScsAsistenciaSqlExtendsProvider.class, method="eliminarRelacionAsistencia")
	    int eliminarRelacionAsistencia(String idinstitucion, String anio, String numero);

}
