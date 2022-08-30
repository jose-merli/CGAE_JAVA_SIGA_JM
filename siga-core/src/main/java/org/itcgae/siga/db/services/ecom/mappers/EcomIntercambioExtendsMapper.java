package org.itcgae.siga.db.services.ecom.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.EjgListaIntercambiosItem;
import org.itcgae.siga.db.mappers.EcomIntercambioMapper;
import org.itcgae.siga.db.services.ecom.providers.EcomIntercambioExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface EcomIntercambioExtendsMapper extends EcomIntercambioMapper {

    @SelectProvider(type = EcomIntercambioExtendsSqlProvider.class, method = "getNewId")
    Long getNewId();

    @SelectProvider(type = EcomIntercambioExtendsSqlProvider.class, method = "getListadoIntercambiosAltaEJG")
    @Results({
            @Result(column = "idinstitucion", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
            @Result(column = "idecomintercambio", property = "idEcomIntercambio", jdbcType = JdbcType.NUMERIC),
            @Result(column = "idecomcola", property = "idEcomCola", jdbcType = JdbcType.NUMERIC),
            @Result(column = "descripcion", property = "descripcion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "estadorespuesta", property = "estadoRespuesta", jdbcType = JdbcType.VARCHAR),
            @Result(column = "idestadorespuesta", property = "idEstadoRespuesta", jdbcType = JdbcType.NUMERIC),
            @Result(column = "respuesta", property = "respuesta", jdbcType = JdbcType.VARCHAR),
            @Result(column = "fechaenvio", property = "fechaEnvio", jdbcType = JdbcType.DATE),
            @Result(column = "fecharespuesta", property = "fechaRespuesta", jdbcType = JdbcType.DATE)
    })
    List<EjgListaIntercambiosItem> getListadoIntercambiosAltaEJG(String idInstitucion, String anio, String idTipoEJG, String numero);

    @SelectProvider(type = EcomIntercambioExtendsSqlProvider.class, method = "getListadoIntercambiosDocumentacionEJG")
    @Results({
            @Result(column = "idinstitucion", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
            @Result(column = "idecomintercambio", property = "idEcomIntercambio", jdbcType = JdbcType.NUMERIC),
            @Result(column = "idecomcola", property = "idEcomCola", jdbcType = JdbcType.NUMERIC),
            @Result(column = "descripcion", property = "descripcion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "estadorespuesta", property = "estadoRespuesta", jdbcType = JdbcType.VARCHAR),
            @Result(column = "idestadorespuesta", property = "idEstadoRespuesta", jdbcType = JdbcType.NUMERIC),
            @Result(column = "respuesta", property = "respuesta", jdbcType = JdbcType.VARCHAR),
            @Result(column = "fechaenvio", property = "fechaEnvio", jdbcType = JdbcType.DATE),
            @Result(column = "fecharespuesta", property = "fechaRespuesta", jdbcType = JdbcType.DATE)
    })
    List<EjgListaIntercambiosItem> getListadoIntercambiosDocumentacionEJG(String idInstitucion, String anio, String idTipoEJG, String numero);

}
