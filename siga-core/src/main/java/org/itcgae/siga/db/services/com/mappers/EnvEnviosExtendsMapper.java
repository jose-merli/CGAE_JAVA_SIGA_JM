package org.itcgae.siga.db.services.com.mappers;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.com.EnviosMasivosItem;
import org.itcgae.siga.DTOs.com.EnviosMasivosSearch;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.entities.EnvEnvios;
import org.itcgae.siga.db.mappers.EnvEnviosMapper;
import org.itcgae.siga.db.services.com.providers.EnvEnviosExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface EnvEnviosExtendsMapper extends EnvEnviosMapper {
          
      @SelectProvider(type = EnvEnviosExtendsSqlProvider.class, method = "selectEnvios")
      @Results({@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
                @Result(column = "IDENVIO", property = "idEnvio", jdbcType = JdbcType.NUMERIC),
                @Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
                @Result(column = "FECHACREACION", property = "fechaCreacion", jdbcType = JdbcType.DATE),
                @Result(column = "IDPLANTILLAENVIOS", property = "idPlantillaEnvios", jdbcType = JdbcType.VARCHAR),
                @Result(column = "IDESTADO", property = "idEstado", jdbcType = JdbcType.NUMERIC),
                @Result(column = "IDTIPOENVIOS", property = "idTipoEnvios", jdbcType = JdbcType.NUMERIC),
                @Result(column = "NOMBREPLANTILLA", property = "nombrePlantilla", jdbcType = JdbcType.VARCHAR),
                @Result(column = "IDPLANTILLA", property = "idPlantilla", jdbcType = JdbcType.VARCHAR),
                @Result(column = "FECHAPROGRAMADA", property = "fechaProgramada", jdbcType = JdbcType.DATE),
                @Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
                @Result(column = "ASUNTO", property = "asunto", jdbcType = JdbcType.VARCHAR),
                @Result(column = "CUERPO", property = "cuerpo", jdbcType = JdbcType.VARCHAR),
                @Result(column = "TIPOENVIO", property = "tipoEnvio", jdbcType = JdbcType.VARCHAR),
                @Result(column = "ESTADOENVIO", property = "estadoEnvio", jdbcType = JdbcType.VARCHAR),
                @Result(column = "NUMDEST", property = "numDestinatarios", jdbcType = JdbcType.NUMERIC)
      })
      List<EnviosMasivosItem> selectEnviosMasivosSearch(Short idInstitucion, String idLenguaje, EnviosMasivosSearch filtros);
      
      @SelectProvider(type = EnvEnviosExtendsSqlProvider.class, method = "selectMaxIDEnvio")
      @Results({
                @Result(column = "IDMAX", property = "newId", jdbcType = JdbcType.VARCHAR)
      })
      NewIdDTO selectMaxIDEnvio(Short idInstitucion);
      
      @SelectProvider(type = EnvEnviosExtendsSqlProvider.class, method = "selectEnviosComunicacion")
      @Results({@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
                @Result(column = "IDENVIO", property = "idEnvio", jdbcType = JdbcType.NUMERIC),
                @Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
                @Result(column = "FECHACREACION", property = "fechaCreacion", jdbcType = JdbcType.TIMESTAMP),
                @Result(column = "IDPLANTILLAENVIOS", property = "idPlantillaEnvios", jdbcType = JdbcType.VARCHAR),
                @Result(column = "IDESTADO", property = "idEstado", jdbcType = JdbcType.NUMERIC),
                @Result(column = "IDTIPOENVIOS", property = "idTipoEnvios", jdbcType = JdbcType.NUMERIC),
                @Result(column = "IDMODELOCOMUNICACION", property = "idModeloComunicacion", jdbcType = JdbcType.NUMERIC),
                @Result(column = "IDCLASECOMUNICACION", property = "idClaseComunicacion", jdbcType = JdbcType.VARCHAR),
                @Result(column = "NOMBRECLASE", property = "claseComunicacion", jdbcType = JdbcType.VARCHAR),
                @Result(column = "NOMBREMODELO", property = "modeloComunicacion", jdbcType = JdbcType.VARCHAR),
                @Result(column = "NOMBREPLANTILLA", property = "nombrePlantilla", jdbcType = JdbcType.VARCHAR),
                @Result(column = "IDPLANTILLA", property = "idPlantilla", jdbcType = JdbcType.VARCHAR),
                @Result(column = "FECHAPROGRAMADA", property = "fechaProgramada", jdbcType = JdbcType.TIMESTAMP),
                @Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
                @Result(column = "ASUNTO", property = "asunto", jdbcType = JdbcType.VARCHAR),
                @Result(column = "CUERPO", property = "cuerpo", jdbcType = JdbcType.VARCHAR),
                @Result(column = "IDGRUPO", property = "idGrupo", jdbcType = JdbcType.VARCHAR),
                @Result(column = "TIPOENVIO", property = "tipoEnvio", jdbcType = JdbcType.VARCHAR),
                @Result(column = "ESTADOENVIO", property = "estadoEnvio", jdbcType = JdbcType.VARCHAR),
                @Result(column = "CSV", property = "csv", jdbcType = JdbcType.VARCHAR),
                @Result(column = "ESTADOENVIO", property = "estadoEnvio", jdbcType = JdbcType.VARCHAR),
                @Result(column = "DESTINATARIO", property = "destinatario", jdbcType = JdbcType.VARCHAR)
      })
      List<EnviosMasivosItem> selectEnviosComunicacionSearch(Short idInstitucion, String idLenguaje, EnviosMasivosSearch filtros);
      
      
      @SelectProvider(type = EnvEnviosExtendsSqlProvider.class, method = "selectEnviosProgramados")
      @Results({@Result(column = "idinstitucion", property = "idinstitucion", jdbcType = JdbcType.NUMERIC),
                @Result(column = "idenvio", property = "idenvio", jdbcType = JdbcType.NUMERIC),
                @Result(column = "fecha", property = "fecha", jdbcType = JdbcType.DATE),
                @Result(column = "generardocumento", property = "generardocumento", jdbcType = JdbcType.VARCHAR),
                @Result(column = "imprimiretiquetas", property = "imprimiretiquetas", jdbcType = JdbcType.VARCHAR),
                @Result(column = "idplantillaenvios", property = "idplantillaenvios", jdbcType = JdbcType.NUMERIC),
                @Result(column = "idestado", property = "idestado", jdbcType = JdbcType.NUMERIC),
                @Result(column = "idtipoenvios", property = "idtipoenvios", jdbcType = JdbcType.NUMERIC),
                @Result(column = "fechamodificacion", property = "fechamodificacion", jdbcType = JdbcType.DATE),
                @Result(column = "usumodificacion", property = "usumodificacion", jdbcType = JdbcType.NUMERIC),
                @Result(column = "idplantilla", property = "idplantilla", jdbcType = JdbcType.NUMERIC),
                @Result(column = "idimpresora", property = "idimpresora", jdbcType = JdbcType.NUMERIC),
                @Result(column = "fechaprogramada", property = "fechaprogramada", jdbcType = JdbcType.DATE),
                @Result(column = "consulta", property = "consulta", jdbcType = JdbcType.VARCHAR),
                @Result(column = "acuserecibo", property = "acuserecibo", jdbcType = JdbcType.VARCHAR),
                @Result(column = "idtipointercambiotelematico", property = "idtipointercambiotelematico", jdbcType = JdbcType.VARCHAR),
                @Result(column = "comisionajg", property = "comisionajg", jdbcType = JdbcType.NUMERIC),
                @Result(column = "fechabaja", property = "fechabaja", jdbcType = JdbcType.DATE),
                @Result(column = "csv", property = "csv", jdbcType = JdbcType.VARCHAR),
                @Result(column = "idsolicitudecos", property = "idsolicitudecos", jdbcType = JdbcType.VARCHAR),
                @Result(column = "envio", property = "envio", jdbcType = JdbcType.VARCHAR),
                @Result(column = "idmodelocomunicacion", property = "idmodelocomunicacion", jdbcType = JdbcType.NUMERIC)
                
      })
      List<EnvEnvios> obtenerEnviosProgramados();
      
      @SelectProvider(type = EnvEnviosExtendsSqlProvider.class, method = "selectEnvioById")
      @Results({@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
                @Result(column = "IDENVIO", property = "idEnvio", jdbcType = JdbcType.NUMERIC),
                @Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
                @Result(column = "FECHACREACION", property = "fechaCreacion", jdbcType = JdbcType.DATE),
                @Result(column = "IDPLANTILLAENVIOS", property = "idPlantillaEnvios", jdbcType = JdbcType.VARCHAR),
                @Result(column = "IDESTADO", property = "idEstado", jdbcType = JdbcType.NUMERIC),
                @Result(column = "IDTIPOENVIOS", property = "idTipoEnvios", jdbcType = JdbcType.NUMERIC),
                @Result(column = "NOMBREPLANTILLA", property = "nombrePlantilla", jdbcType = JdbcType.VARCHAR),
                @Result(column = "IDPLANTILLA", property = "idPlantilla", jdbcType = JdbcType.VARCHAR),
                @Result(column = "FECHAPROGRAMADA", property = "fechaProgramada", jdbcType = JdbcType.DATE),
                @Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
                @Result(column = "ASUNTO", property = "asunto", jdbcType = JdbcType.VARCHAR),
                @Result(column = "CUERPO", property = "cuerpo", jdbcType = JdbcType.VARCHAR),
                @Result(column = "TIPOENVIO", property = "tipoEnvio", jdbcType = JdbcType.VARCHAR),
                @Result(column = "ESTADOENVIO", property = "estadoEnvio", jdbcType = JdbcType.VARCHAR)
      })
      List<EnviosMasivosItem> selectEnviosMasivosById(Short idInstitucion, String idLenguaje, String idEnvio);
      
      @SelectProvider(type = EnvEnviosExtendsSqlProvider.class, method = "selectEnviosByIdPlantilla")
      @Results({@Result(column = "IDENVIO", property = "idEnvio", jdbcType = JdbcType.NUMERIC)
      })
      List<EnviosMasivosItem> selectEnviosByIdPlantilla(Short idInstitucion, String idPlantillaEnvio);
      
      @SelectProvider(type = EnvEnviosExtendsSqlProvider.class, method = "obtenerDestinatarios")
      @Results({@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
                @Result(column = "IDENVIO", property = "idEnvio", jdbcType = JdbcType.NUMERIC),
                @Result(column = "NUMDEST", property = "numDestinatarios", jdbcType = JdbcType.NUMERIC)
      })
      List<EnviosMasivosItem> obtenerDestinatarios(Short idInstitucion, String idEnvios);

      
      @SelectProvider(type = EnvEnviosExtendsSqlProvider.class, method = "obtenerEnviosMalCreados")
      @Results({@Result(column = "idinstitucion", property = "idinstitucion", jdbcType = JdbcType.NUMERIC),
                @Result(column = "idenvio", property = "idenvio", jdbcType = JdbcType.NUMERIC),
                @Result(column = "fecha", property = "fecha", jdbcType = JdbcType.DATE)
                
      })
      List<EnvEnvios> obtenerEnviosMalCreados();
	
    @SelectProvider(type = EnvEnviosExtendsSqlProvider.class, method = "busquedaSelectEnvios")
    @Results({@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
            @Result(column = "IDENVIO", property = "idEnvio", jdbcType = JdbcType.NUMERIC),
            @Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHACREACION", property = "fechaCreacion", jdbcType = JdbcType.DATE),
            @Result(column = "IDESTADO", property = "idEstado", jdbcType = JdbcType.NUMERIC),
            @Result(column = "IDTIPOENVIOS", property = "idTipoEnvios", jdbcType = JdbcType.NUMERIC),
            @Result(column = "FECHAPROGRAMADA", property = "fechaProgramada", jdbcType = JdbcType.DATE),
            @Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
            @Result(column = "TIPOENVIO", property = "tipoEnvio", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ESTADOENVIO", property = "estadoEnvio", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDPLANTILLAENVIOS", property = "idPlantillaEnvios", jdbcType = JdbcType.VARCHAR)
    })
    List<EnviosMasivosItem> busquedaSelectEnviosMasivosSearch(Short idInstitucion, String idLenguaje, EnviosMasivosSearch filtros);

    @DeleteProvider(type = EnvEnviosExtendsSqlProvider.class, method = "eliminarEnviosPago")
    int eliminarEnviosPago(Short idInstitucion, List<String> idPagos);

    @SelectProvider(type = EnvEnviosExtendsSqlProvider.class, method = "getNewIdEnvio")
    Integer getNewIdEnvio();
	
	@SelectProvider(type = EnvEnviosExtendsSqlProvider.class, method = "obtenerEnviosIrrecuperables")
      @Results({@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
    	  		@Result(column = "IDESTADO", property = "idEstado", jdbcType = JdbcType.NUMERIC),
                @Result(column = "IDENVIO", property = "idEnvio", jdbcType = JdbcType.NUMERIC)
                
      })
      List<EnviosMasivosItem> obtenerEnviosIrrecuperables(Short horas);
}
