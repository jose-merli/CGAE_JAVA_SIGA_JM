package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.scs.*;
import org.itcgae.siga.db.entities.ScsActuacionasistencia;
import org.itcgae.siga.db.mappers.ScsActuacionasistenciaSqlProvider;
import org.itcgae.siga.db.mappers.ScsAsistenciaMapper;
import org.itcgae.siga.db.mappers.ScsAsistenciaSqlProvider;
import org.itcgae.siga.db.services.scs.providers.ScsAsistenciaSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsDesignacionesSqlExtendsProvider;
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

	 @UpdateProvider(type=ScsAsistenciaSqlExtendsProvider.class, method="eliminarRelacionAsistenciaDes")
	    int eliminarRelacionAsistenciaDes(String idinstitucion, String anio, String numero);


	List<TarjetaAsistenciaItem> searchAsistencias(FiltroAsistenciaItem filtro, Short idInstitucion);

	@SelectProvider(type = ScsAsistenciaSqlExtendsProvider.class, method = "getNextNumeroAsistencia")
	@Results({
		@Result(column = "numero", property = "numero", jdbcType = JdbcType.VARCHAR),

	})
	String getNextNumeroAsistencia(String anio, Short idInstitucion);

    @UpdateProvider(type=ScsAsistenciaSqlExtendsProvider.class, method="updateAsistenciaExpress")
    int updateAsistenciaExpress(ScsActuacionasistencia record);

	@SelectProvider(type = ScsAsistenciaSqlExtendsProvider.class, method = "searchListaContrarios")
	@Results({ @Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOSNOMBRE", property = "apellidosnombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE)})
	List<ListaContrarioJusticiableItem> searchListaContrarios(String anioNumero, Short idInstitucion, boolean mostrarHistorico);

	@SelectProvider(type = ScsAsistenciaSqlExtendsProvider.class, method = "getNumContrarios")
	@Results({ @Result(column = "contrarios", property = "valor", jdbcType = JdbcType.VARCHAR)})
	StringDTO getNumContrarios(String anioNumero, Short idInstitucion);

	@SelectProvider(type = ScsAsistenciaSqlExtendsProvider.class, method = "searchRelaciones")
	@Results({ @Result(column = "SJCS", property = "sjcs", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDLETRADO", property = "idletrado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LETRADO", property = "letrado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNODESIGNA", property = "idturnodesigna", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPO", property = "idtipo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGO", property = "codigo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESC_TURNO", property = "desturno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DES_TIPO", property = "destipo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "INTERESADO", property = "interesado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DICTAMEN", property = "dictamen", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHADICTAMEN", property = "fechadictamen", jdbcType = JdbcType.VARCHAR),
			@Result(column = "RESOLUCION", property = "resolucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHARESOLUCION", property = "fecharesolucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAASUNTO", property = "fechaasunto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DILNIGPROC", property = "dilnigproc", jdbcType = JdbcType.VARCHAR)})
	List<RelacionesItem> searchRelaciones(String anio, String num, Short idInstitucion, int idLenguaje, Integer tamMax);

}
