package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.AsuntosDesignaItem;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableItem;
import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.JustificacionExpressItem;
import org.itcgae.siga.db.mappers.ScsDesignaMapper;
import org.itcgae.siga.db.services.com.providers.ConModulosExtendsSqlProvider;
import org.itcgae.siga.db.services.scs.providers.ScsDesignacionesSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsDesignacionesExtendsMapper extends ScsDesignaMapper{

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "searchClaveDesignaciones")
	@Results({ @Result(column = "ANIO", property = "anio", jdbcType = JdbcType.DATE),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CLAVE", property = "clave", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ROL", property = "rol", jdbcType = JdbcType.VARCHAR), 
			@Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR) 
	})
	List<AsuntosClaveJusticiableItem> searchClaveDesignaciones(AsuntosJusticiableItem asuntosJusticiableItem, Integer tamMaximo);
	
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "busquedaDesignaciones")
	@Results({ 
		@Result(column = "ANIO", property = "ano", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.NUMERIC),
			@Result(column = "ESTADO", property = "art27", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAESTADO", property = "fechaEstado", jdbcType = JdbcType.DATE),
			@Result(column = "NOMBRE", property = "nombreTurno", jdbcType = JdbcType.VARCHAR), 
			@Result(column = "NCOLEGIADO", property = "numColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPODESIGNACOLEGIO", property = "observaciones", jdbcType = JdbcType.VARCHAR)
	})
	List<DesignaItem> busquedaDesignaciones(DesignaItem designaItem, Short idInstitucion, Integer tamMaximo);
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "getAsuntoTipoDesigna")
	@Results({ 
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ASUNTO", property = "asunto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHA", property = "fecha", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),	
		@Result(column = "TURNOGUARDIA", property = "turnoGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGO", property = "codigo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DATOSINTERES", property = "datosInteres", jdbcType = JdbcType.VARCHAR),
	})
	AsuntosDesignaItem getAsuntoTipoDesigna(AsuntosClaveJusticiableItem asuntoClave, String idLenguaje);
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "busquedaJustificacionExpres")
	@Results({ 
		@Result(column = "CODIGODESIGNA", property = "codigo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAENTRADA", property = "fechaEntrada", jdbcType = JdbcType.DATE),
		@Result(column = "EXPEDIENTES", property = "expedientes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CLIENTE", property = "cliente", jdbcType = JdbcType.VARCHAR),	
//		@Result(column = "ESTADO", property = "estados", jdbcType = JdbcType.VARCHAR),
//		@Result(column = "IDJUZGADO", property = "idJuzgado", jdbcType = JdbcType.VARCHAR),
//		@Result(column = "IDINSTITUCION_JUZG", property = "idInstitucion_juzg", jdbcType = JdbcType.VARCHAR),	
//		@Result(column = "NIG", property = "nig", jdbcType = JdbcType.VARCHAR),
//		@Result(column = "NUMPROCEDIMIENTNO", property = "numProcedimiento", jdbcType = JdbcType.VARCHAR),
//		@Result(column = "IDPROCEDIMIENTO", property = "idProcedimiento", jdbcType = JdbcType.VARCHAR),	
	})
	List<DesignaItem> busquedaJustificacionExpres(JustificacionExpressItem item, String idInstitucion, String longitudCodEJG, String idPersona);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "comboModulos")
	@Results({@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPROCEDIMIENTO", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> comboModulos(Short idInstitucion);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "comboProcedimientos")
	@Results({@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPROCEDIMIENTO", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> comboProcedimientos(Short idInstitucion);
}