package org.itcgae.siga.db.services.fcs.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.BusquedaRetencionesRequestDTO;
import org.itcgae.siga.DTOs.scs.CertificacionesItem;
import org.itcgae.siga.db.mappers.FcsCertificacionesMapper;
import org.itcgae.siga.db.services.fcs.providers.FcsCertificacionesSqlExtendsProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FcsCertificacionesExtendsMapper extends FcsCertificacionesMapper {

    @SelectProvider(type = FcsCertificacionesSqlExtendsProvider.class, method = "buscarCertificaciones")
    @Results({@Result(column = "IDCERTIFICACION", property = "idCertificacion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHADESDE", property = "fechaDesde", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAHASTA", property = "fechaHasta", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "PERIODO", property = "periodo", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
            @Result(column = "TURNO", property = "turno", jdbcType = JdbcType.VARCHAR),
            @Result(column = "GUARDIA", property = "guardia", jdbcType = JdbcType.VARCHAR),
            @Result(column = "EJG", property = "ejg", jdbcType = JdbcType.VARCHAR),
            @Result(column = "SOJ", property = "soj", jdbcType = JdbcType.VARCHAR),
            @Result(column = "TOTAL", property = "total", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDESTADOCERTIFICACION", property = "idEstadoCertificacion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDPARTIDAPRESUPUESTARIA", property = "idPartidaPresupuestaria", jdbcType = JdbcType.VARCHAR),
            @Result(column = "NOMBREPARTIDAPRESUPUESTARIA", property = "nombrePartidaPresupuestaria", jdbcType = JdbcType.VARCHAR)})
    List<CertificacionesItem> buscarCertificaciones(BusquedaRetencionesRequestDTO busquedaRetencionesRequestDTO, Integer tamMax, String idLenguaje);

    @SelectProvider(type = FcsCertificacionesSqlExtendsProvider.class, method = "getComboEstadosCertificaciones")
    @Results({@Result(column = "LABEL", property = "label", jdbcType = JdbcType.VARCHAR),
            @Result(column = "VALUE", property = "value", jdbcType = JdbcType.VARCHAR)})
    List<ComboItem> getComboEstadosCertificaciones(String idLenguaje);
}
