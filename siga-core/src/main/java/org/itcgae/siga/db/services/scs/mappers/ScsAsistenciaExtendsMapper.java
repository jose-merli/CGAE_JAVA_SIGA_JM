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
import org.itcgae.siga.db.services.scs.providers.ScsAsistenciaSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsDesignacionesSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsAsistenciaExtendsMapper extends ScsAsistenciaMapper{

	@SelectProvider(type = ScsAsistenciaSqlExtendsProvider.class, method = "searchClaveAsistencia")
	@Results({ @Result(column = "ANIO", property = "anio", jdbcType = JdbcType.DATE),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CLAVE", property = "clave", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ROL", property = "rol", jdbcType = JdbcType.VARCHAR), 
			@Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR) 
	})
	List<AsuntosClaveJusticiableItem> searchClaveAsistencia(AsuntosJusticiableItem asuntosJusticiableItem, Integer tamMaximo);
	

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
	
	@SelectProvider(type = ScsAsistenciaSqlExtendsProvider.class, method = "searchAsistencias")
	@Results({ 
		@Result(column = "anio", property = "anio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "numero", property = "numero", jdbcType = JdbcType.VARCHAR),	
		@Result(column = "numeroasunto", property = "numeroAsunto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechaActuacion", property = "fchaActuacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechajustificacion", property = "fchaJustificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "observaciones", property = "observaciones", jdbcType = JdbcType.VARCHAR),
		@Result(column = "iddelito", property = "idDelito", jdbcType = JdbcType.VARCHAR),
		@Result(column = "lugar", property = "lugar", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ejganio", property = "ejgAnio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ejgnumero", property = "ejgNumero", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombre", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "apellido1", property = "apellido1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "apellido2", property = "apellido2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nif", property = "nif", jdbcType = JdbcType.VARCHAR),
		@Result(column = "sexo", property = "sexo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "comisariaJuzgado", property = "comisariaJuzgado", jdbcType = JdbcType.VARCHAR)

	})
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
			@Result(column = "FECHADICTAMEN", property = "fechaDictamen", jdbcType = JdbcType.VARCHAR),
			@Result(column = "RESOLUCION", property = "resolucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHARESOLUCION", property = "fechaResolucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAASUNTO", property = "fechaAsunto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DILNIGPROC", property = "dilnigproc", jdbcType = JdbcType.VARCHAR)})
	List<RelacionesItem> searchRelaciones(String anio, String num, Short idInstitucion, int idLenguaje, Integer tamMax);

}
