package org.itcgae.siga.db.services.fcs.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.BusquedaRetencionesRequestDTO;
import org.itcgae.siga.DTOs.scs.CertificacionesItem;
import org.itcgae.siga.DTOs.scs.EstadoCertificacionItem;
import org.itcgae.siga.DTOs.scs.FacturacionItem;
import org.itcgae.siga.db.mappers.FcsCertificacionesMapper;
import org.itcgae.siga.db.services.fcs.providers.FcsCertificacionesSqlExtendsProvider;
import org.itcgae.siga.db.services.fcs.providers.FcsFacturacionJGSqlExtendsProvider;
import org.itcgae.siga.db.services.fcs.providers.FcsMovimientosvariosSqlExtendsProvider;
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

    @SelectProvider(type = FcsCertificacionesSqlExtendsProvider.class, method = "getEstadosCertificacion")
    @Results({@Result(column = "IDHISTORICO", property = "idHistorico", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDCERTIFICACION", property = "idCertificacion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "PROCESO", property = "proceso", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHAESTADO", property = "fechaEstado", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "IDESTADO", property = "idEstado", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR)})
    List<EstadoCertificacionItem> getEstadosCertificacion(String idCertificacion, Short idInstitucion, String idLenguaje);

    @SelectProvider(type = FcsCertificacionesSqlExtendsProvider.class, method = "getFactCertificaciones")
    @Results({@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ABREVIATURA", property = "abreviatura", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDFACTURACION", property = "idFacturacion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHADESDE", property = "fechaDesde", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAHASTA", property = "fechaHasta", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
            @Result(column = "REGULARIZACION", property = "regularizacion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "DESESTADO", property = "desEstado", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDESTADO", property = "idEstado", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHAESTADO", property = "fechaEstado", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "IMPORTETOTAL", property = "importeTotal", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IMPORTEPAGADO", property = "importePagado", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IMPORTEPENDIENTE", property = "importePendiente", jdbcType = JdbcType.VARCHAR),
            @Result(column = "PARTIDAPRESUPUESTARIA", property = "idPartidaPresupuestaria", jdbcType = JdbcType.VARCHAR),
            @Result(column = "GRUPOFACTURACION", property = "idGrupo", jdbcType = JdbcType.VARCHAR),
            @Result(column = "TURNO", property = "importeOficio", jdbcType = JdbcType.VARCHAR),
            @Result(column = "GUARDIA", property = "importeGuardia", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IMPORTESOJ", property = "importeSoj", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IMPORTEEJG", property = "importeEjg", jdbcType = JdbcType.VARCHAR),
    })
    List<FacturacionItem> getFactCertificaciones(String idCertificacion, String idInstitucion, Integer tamMax);

    @SelectProvider(type = FcsCertificacionesSqlExtendsProvider.class, method = "comboFactByPartidaPresu")
    @Results({@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDFACTURACION", property = "value", jdbcType = JdbcType.VARCHAR)})
    List<ComboItem> comboFactByPartidaPresu(String idpartidapresupuestaria, String idinstitucion);

    @SelectProvider(type = FcsCertificacionesSqlExtendsProvider.class, method = "comboFactNull")
    @Results({@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDFACTURACION", property = "value", jdbcType = JdbcType.VARCHAR)})
    List<ComboItem> comboFactNull(String idinstitucion);


}
