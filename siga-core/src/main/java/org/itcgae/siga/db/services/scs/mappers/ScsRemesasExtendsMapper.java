package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.CheckAccionesRemesas;
import org.itcgae.siga.DTOs.scs.EJGRemesaItem;
import org.itcgae.siga.DTOs.scs.EstadoRemesaItem;
import org.itcgae.siga.DTOs.scs.RemesasBusquedaItem;
import org.itcgae.siga.DTOs.scs.RemesasItem;
import org.itcgae.siga.DTOs.scs.RemesasItem2;
import org.itcgae.siga.db.mappers.CajgRemesaMapper;
import org.itcgae.siga.db.services.scs.providers.ScsRemesasExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsRemesasExtendsMapper {

	@SelectProvider(type = ScsRemesasExtendsProvider.class, method = "comboEstado")
	@Results({ 
		@Result(column = "ID", property = "value", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> comboEstado(String string);
	
	@SelectProvider(type = ScsRemesasExtendsProvider.class, method = "buscarRemesas")
	@Results({
		@Result(column = "IDREMESA", property = "idRemesa", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "PREFIJO", property = "prefijo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SUFIJO", property = "sufijo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHA_GENERACION", property = "fechaGeneracion", jdbcType = JdbcType.DATE),
		@Result(column = "FECHA_ENVIO", property = "fechaEnvio", jdbcType = JdbcType.DATE),
		@Result(column = "FECHA_RECEPCION", property = "fechaRecepcion", jdbcType = JdbcType.DATE),
		@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "INCIDENCIAS_EJG", property = "incidenciasEJG", jdbcType = JdbcType.NUMERIC),
		@Result(column = "TOTAL_EJG", property = "totalEJG", jdbcType = JdbcType.NUMERIC),
		@Result(column = "INCIDENCIAS", property = "incidencias", jdbcType = JdbcType.NUMERIC)
	})
	List<RemesasItem> buscarRemesas(RemesasBusquedaItem remesasBusquedaItem, Short idInstitucion, Integer tamMaximo, String idLenguaje);
	
	@SelectProvider(type = ScsRemesasExtendsProvider.class, method = "isEstadoRemesaIniciada")
	@Results({
		@Result(column = "IDREMESA", property = "idRemesa", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDESTADO", property = "idEstado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAREMESA", property = "fechaRemesa", jdbcType = JdbcType.VARCHAR)
	})
	List<RemesasItem2> isEstadoRemesaIniciada(RemesasBusquedaItem remesasBusquedaItem, Short idInstitucion);
	
	@SelectProvider(type = ScsRemesasExtendsProvider.class, method = "listadoEstadoRemesa")
	@Results({
		@Result(column = "IDREMESA", property = "idRemesa", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAMODIFICACION", property = "fechaModificacion", jdbcType = JdbcType.VARCHAR)
	})
	List<EstadoRemesaItem> listadoEstadoRemesa(RemesasBusquedaItem remesasBusquedaItem, Short idInstitucion, String idLenguaje);
	
	@SelectProvider(type = ScsRemesasExtendsProvider.class, method = "getMaxIdRemesa")
	@Results({
		@Result(column = "IDREMESA", property = "idRemesa", jdbcType = JdbcType.VARCHAR)
	})
	RemesasItem getMaxIdRemesa(Short idInstitucion);
	
	@SelectProvider(type = ScsRemesasExtendsProvider.class, method = "getEJGRemesa")
	@Results({
		@Result(column = "IDEJGREMESA", property = "idEjgRemesa", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDENTIFICADOR", property = "identificadorEJG", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPOEJG", property = "idTipoEJG", jdbcType = JdbcType.NUMERIC),
		@Result(column = "TURNO_GUARDIA_EJG", property = "turnoGuardiaEJG", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ANIOEJG", property = "anioEJG", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NUMEROEJG", property = "numeroEJG", jdbcType = JdbcType.NUMERIC),
		@Result(column = "ESTADOEJG", property = "estadoEJG", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SOLICITANTE", property = "solicitante", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUEVAREMESA", property = "nuevaRemesa", jdbcType = JdbcType.NUMERIC),
		@Result(column = "ESTADOREMESA", property = "estadoRemesa", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMERO_INCIDENCIAS", property = "numIncidencias", jdbcType = JdbcType.NUMERIC),
		@Result(column = "INCIDENCIAS_ANTES_ENVIO", property = "incidenciasAntesEnvio", jdbcType = JdbcType.NUMERIC),
		@Result(column = "INCIDENCIAS_DESPUES_ENVIO", property = "incidenciasDespuesEnvio", jdbcType = JdbcType.NUMERIC)
	})
	List<EJGRemesaItem> getEJGRemesa(RemesasItem remesasItem, Short idInstitucion, String idLenguaje);
	
	@SelectProvider(type = ScsRemesasExtendsProvider.class, method = "getAcciones")
	@Results({
		@Result(column = "IDTIPOACCIONREMESA", property = "idTipoAccionRemesa", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR)
	})
	List<CheckAccionesRemesas> getAcciones(RemesasItem remesasItem, Short idInstitucion, String idlenguaje, String tipoPCAJG);
	
}
