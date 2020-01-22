package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.AsuntosEjgItem;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableItem;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.ResolucionEJGItem;
import org.itcgae.siga.db.mappers.ScsEjgMapper;
import org.itcgae.siga.db.services.scs.providers.ScsEjgSqlExtendsProvider;

public interface ScsEjgExtendsMapper extends ScsEjgMapper {
	
    @SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "getAsuntoTipoEjg")
    @Results({ 
              @Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
              @Result(column = "ASUNTO", property = "asunto", jdbcType = JdbcType.VARCHAR),
              @Result(column = "FECHA", property = "fecha", jdbcType = JdbcType.VARCHAR),
              @Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
              @Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),         
              @Result(column = "TURNOGUARDIA", property = "turnoGuardia", jdbcType = JdbcType.VARCHAR),
              @Result(column = "IDPERSONALETRADO", property = "idPersonaLetrado", jdbcType = JdbcType.VARCHAR),
              @Result(column = "IDPERSONAJG", property = "idPersonajg", jdbcType = JdbcType.VARCHAR),
              @Result(column = "EXPEDIENTEINSOSTENIBILIDAD", property = "expedienteInsostenibilidad", jdbcType = JdbcType.VARCHAR),
              @Result(column = "DICTAMEN", property = "dictamen", jdbcType = JdbcType.VARCHAR),
              @Result(column = "FUNDAMENTOCALIFICACION", property = "fundamentoCalificacion", jdbcType = JdbcType.VARCHAR),
              @Result(column = "TIPORESOLUCION", property = "tipoResolucion", jdbcType = JdbcType.VARCHAR),
              @Result(column = "TIPOFUNDAMENTO", property = "tipoFundamento", jdbcType = JdbcType.VARCHAR),
              @Result(column = "TIPORESOLUCIONAUTO", property = "tipoResolucionAuto", jdbcType = JdbcType.VARCHAR),
              @Result(column = "TIPOSENTIDOAUTO", property = "tipoSentidoAuto", jdbcType = JdbcType.VARCHAR),
    })
    AsuntosEjgItem getAsuntoTipoEjg(AsuntosClaveJusticiableItem asuntoClave, String idLenguaje);
    
@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "searchClaveAsuntosEJG")
@Results({ @Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
      @Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
      @Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
      @Result(column = "CLAVE", property = "clave", jdbcType = JdbcType.VARCHAR),
      @Result(column = "ROL", property = "rol", jdbcType = JdbcType.VARCHAR), 
      @Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR) 
})
List<AsuntosClaveJusticiableItem> searchClaveAsuntosEJG(AsuntosJusticiableItem asuntosJusticiableItem, Integer tamMaximo);


	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "comboCreadoDesde")
	@Results({ 
		@Result(column = "ORIGENAPERTURA", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> comboCreadoDesde(String idlenguaje, String string);

	
	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "busquedaEJG")
	@Results({ 

		@Result(column = "anio", property = "annio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idtipoejg", property = "tipoEJG", jdbcType = JdbcType.VARCHAR),
		@Result(column = "numero", property = "numero", jdbcType = JdbcType.VARCHAR),
		@Result(column = "numejg", property = "numEjg", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMANIO", property = "numAnnioProcedimiento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TURNO", property = "turno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TURNOGUARDIA", property = "turnoDes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechaapertura", property = "fechaApertura", jdbcType = JdbcType.DATE),
		@Result(column = "fechamodificacion", property = "fechaModificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRESOLICITANTE", property = "nombreApeSolicitante", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREletrado", property = "apellidosYNombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ESTADOEJG", property = "estadoEJG", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ejg.numeroprocedimiento", property = "procedimiento", jdbcType = JdbcType.VARCHAR),
	})
	List<EjgItem> busquedaEJG(EjgItem ejgItem, String string, Integer tamMaximo, String idLenguaje);

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "datosEJG")
	@Results({ 


//		sql.SELECT("ejg.fechapresentacion");
//		sql.SELECT("ejg.idtipocolegio");
//		sql.SELECT("ejg.fechalimitepresentacion");		
//		sql.SELECT("rectipodictamen.descripcion AS dictamen");
//		sql.SELECT("rectiporesolucion.descripcion AS resolucion");
//		sql.SELECT("rectiporesolauto.descripcion AS resolauto");
//		sql.SELECT("personadesigna.apellidos1 || ' ' || personadesigna.apellidos2 || ', ' || personadesigna.nombre AS nombreletradodesigna");
//		sql.SELECT("EXPEDIENTE.anioexpediente");
//		sql.SELECT("EXPEDIENTE.numeroexpediente");
//		sql.SELECT("EXPEDIENTE.IDTIPOEXPEDIENTE");
		@Result(column = "anio", property = "annio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idtipoejg", property = "tipoEJG", jdbcType = JdbcType.VARCHAR),
		@Result(column = "numero", property = "numero", jdbcType = JdbcType.VARCHAR),
		@Result(column = "numejg", property = "numEjg", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMANIO", property = "numAnnioProcedimiento", jdbcType = JdbcType.VARCHAR),
		
		@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TURNO", property = "turno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TURNOGUARDIA", property = "turnoDes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "GUARDIA", property = "guardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechaapertura", property = "fechaApertura", jdbcType = JdbcType.DATE),
		@Result(column = "fechamodificacion", property = "fechaModificacion", jdbcType = JdbcType.DATE),
		@Result(column = "NOMBRESOLICITANTE", property = "nombreApeSolicitante", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APESOLICITANTE", property = "apellidos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SOLONOMBRESOLIC", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombreletrado", property = "apellidosYNombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ESTADOEJG", property = "estadoEJG", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ejg.numeroprocedimiento", property = "procedimiento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "dictamen", property = "dictamenSing", jdbcType = JdbcType.VARCHAR),
		@Result(column = "resolucion", property = "resolucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "resolauto", property = "impugnacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechapresentacion", property = "fechapresentacion", jdbcType = JdbcType.DATE),
		@Result(column = "fechalimitepresentacion", property = "fechalimitepresentacion", jdbcType = JdbcType.DATE),
		@Result(column = "anioexpediente", property = "anioexpediente", jdbcType = JdbcType.VARCHAR),
		@Result(column = "numeroexpediente", property = "numeroexpediente", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechaauto", property = "fechaAuto", jdbcType = JdbcType.DATE),
		@Result(column = "idtiporesolauto", property = "autoResolutorio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idtiposentidoauto", property = "sentidoAuto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "observacionimpugnacion", property = "observacionesImpugnacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "numeroresolucion", property = "nImpugnacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechapublicacion", property = "fechaPublicacion", jdbcType = JdbcType.DATE),
		@Result(column = "bisresolucion", property = "bis", jdbcType = JdbcType.VARCHAR),
		@Result(column = "turnadoratificacion", property = "requiereTurn", jdbcType = JdbcType.VARCHAR),
	})
	List<EjgItem> datosEJG(EjgItem ejgItem, String string, String idLenguaje);
	
	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "getDictamen")
	@Results({ @Result(column = "fechadictamen", property = "fechaDictamen", jdbcType = JdbcType.DATE),
	      @Result(column = "observaciones", property = "observacionesDictamen", jdbcType = JdbcType.VARCHAR),
	      @Result(column = "dictamen", property = "dictamenSing", jdbcType = JdbcType.VARCHAR),
	      @Result(column = "iddictamen", property = "iddictamen", jdbcType = JdbcType.VARCHAR),
	      @Result(column = "fundamento", property = "fundamentoCalifDes", jdbcType = JdbcType.VARCHAR), 
	      @Result(column = "idfundamento", property = "fundamentoCalif", jdbcType = JdbcType.VARCHAR) 
	})
	EjgItem getDictamen(EjgItem ejgItem, String idInstitucion, String idLenguaje);
	
	
	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "getResolucion")
	@Results({ 
		 @Result(column = "anioacta", property = "annioActa", jdbcType = JdbcType.VARCHAR),
	     @Result(column = "idacta", property = "idActa", jdbcType = JdbcType.VARCHAR),
	     @Result(column = "idtiporatificacionejg", property = "idTiporatificacionEJG", jdbcType = JdbcType.VARCHAR),
	     @Result(column = "idfundamentojuridico", property = "idFundamentoJuridico", jdbcType = JdbcType.VARCHAR),
	     @Result(column = "ratificaciondictamen", property = "ratificacionDictamen", jdbcType = JdbcType.VARCHAR),
	     @Result(column = "idorigencajg", property = "idOrigencajg", jdbcType = JdbcType.VARCHAR),
	     @Result(column = "aniocajg", property = "anioCAJG", jdbcType = JdbcType.VARCHAR),
	     @Result(column = "numero_cajg", property = "numeroCAJG", jdbcType = JdbcType.VARCHAR),
	     @Result(column = "idponente", property = "idPonente", jdbcType = JdbcType.VARCHAR),
	     @Result(column = "fechapresentacionponente", property = "fechaPresentacionPonente", jdbcType = JdbcType.DATE),
	     @Result(column = "fecharesolucioncajg", property = "fechaResolucionCAJG", jdbcType = JdbcType.DATE),
	     @Result(column = "fecharatificacion", property = "fechaRatificacion", jdbcType = JdbcType.DATE),
	     @Result(column = "fechanotificacion", property = "fechaNotificacion", jdbcType = JdbcType.DATE),
	     @Result(column = "refauto", property = "refAuto", jdbcType = JdbcType.VARCHAR),
	     @Result(column = "idannioacta", property = "idAnnioActa", jdbcType = JdbcType.VARCHAR),
	     @Result(column = "turnadoratificacion", property = "turnadoRatificacion", jdbcType = JdbcType.VARCHAR),
	     @Result(column = "requierenotificarproc", property = "requiereNotificarProc", jdbcType = JdbcType.VARCHAR),
	     @Result(column = "notascajg", property = "notasCAJG", jdbcType = JdbcType.VARCHAR),
	})
	ResolucionEJGItem getResolucion(EjgItem ejgItem, String idInstitucion, String idLenguaje);

}

