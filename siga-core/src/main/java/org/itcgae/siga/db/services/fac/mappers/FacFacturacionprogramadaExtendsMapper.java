package org.itcgae.siga.db.services.fac.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.FacFacturacionprogramadaItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.FacFacturacionprogramadaMapper;
import org.itcgae.siga.db.services.fac.providers.FacFacturacionprogramadaExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface FacFacturacionprogramadaExtendsMapper extends FacFacturacionprogramadaMapper {

    @SelectProvider(type = FacFacturacionprogramadaExtendsSqlProvider.class, method = "getFacturacionesProgramadas")
    @Results({
        @Result(column = "idprogramacion", property = "idProgramacion", jdbcType = JdbcType.VARCHAR),
        @Result(column = "idseriefacturacion", property = "idSerieFacturacion", jdbcType = JdbcType.VARCHAR),
        @Result(column = "descripcion", property = "descripcion", jdbcType = JdbcType.VARCHAR),
        @Result(column = "nombreabreviado", property = "nombreAbreviado", jdbcType = JdbcType.VARCHAR),
        @Result(column = "fechainicioservicios", property = "fechaInicioServicios", jdbcType = JdbcType.DATE),
        @Result(column = "fechafinservicios", property = "fechaFinServicios", jdbcType = JdbcType.DATE),
        @Result(column = "fechainicioproductos", property = "fechaInicioProductos", jdbcType = JdbcType.DATE),
        @Result(column = "fechafinproductos", property = "fechaFinProductos", jdbcType = JdbcType.DATE),
        @Result(column = "fechaprogramacion", property = "fechaProgramacion", jdbcType = JdbcType.DATE),
        @Result(column = "fechaprevistageneracion", property = "fechaPrevistaGeneracion", jdbcType = JdbcType.DATE),
        @Result(column = "fechaprevistaconfirm", property = "fechaPrevistaConfirm", jdbcType = JdbcType.DATE),
        @Result(column = "fecharealgeneracion", property = "fechaRealGeneracion", jdbcType = JdbcType.DATE),
        @Result(column = "fechaconfirmacion", property = "fechaConfirmacion", jdbcType = JdbcType.DATE),
        @Result(column = "idestadopdf", property = "idEstadoPDF", jdbcType = JdbcType.VARCHAR),
        @Result(column = "idestadoconfirmacion", property = "idEstadoConfirmacion", jdbcType = JdbcType.VARCHAR),
        @Result(column = "idestadoenvio", property = "idEstadoEnvio", jdbcType = JdbcType.VARCHAR),
        @Result(column = "idestadotraspaso", property = "idEstadoTraspaso", jdbcType = JdbcType.VARCHAR),
        @Result(column = "archivarfact", property = "archivarFact", jdbcType = JdbcType.VARCHAR),
        @Result(column = "usumodificacion", property = "usuModificacion", jdbcType = JdbcType.VARCHAR),
        @Result(column = "nombrefichero", property = "nombreFichero", jdbcType = JdbcType.VARCHAR),
        @Result(column = "logerror", property = "logError", jdbcType = JdbcType.VARCHAR),
        @Result(column = "logtraspaso", property = "logTraspaso", jdbcType = JdbcType.VARCHAR),
        @Result(column = "traspasofacturas", property = "traspasoFacturas", jdbcType = JdbcType.VARCHAR),
        @Result(column = "traspaso_platilla", property = "traspasoPlatilla", jdbcType = JdbcType.VARCHAR),
        @Result(column = "traspaso_codauditoria_def", property = "traspasoCodAuditoriaDef", jdbcType = JdbcType.VARCHAR),
        @Result(column = "importe", property = "importe", jdbcType = JdbcType.VARCHAR)
    })
    List<FacFacturacionprogramadaItem> getFacturacionesProgramadas(FacFacturacionprogramadaItem facturacionProgramada, Short idInstitucion, String idioma, Integer rownum);

    @SelectProvider(type = FacFacturacionprogramadaExtendsSqlProvider.class, method = "comboFacturaciones")
    @Results({
            @Result(column = "id", property = "value", jdbcType = JdbcType.NUMERIC),
            @Result(column = "descripcion", property = "label", jdbcType = JdbcType.VARCHAR)
    })
    List<ComboItem> comboFacturaciones(Short idInstitucion);
}
