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
        @Result(column = "estadopdf", property = "estadoPDF", jdbcType = JdbcType.VARCHAR),
        @Result(column = "idestadoconfirmacion", property = "idEstadoConfirmacion", jdbcType = JdbcType.VARCHAR),
        @Result(column = "estadoconfirmacion", property = "estadoConfirmacion", jdbcType = JdbcType.VARCHAR),
        @Result(column = "idestadoenvio", property = "idEstadoEnvio", jdbcType = JdbcType.VARCHAR),
        @Result(column = "estadoenvio", property = "estadoEnvio", jdbcType = JdbcType.VARCHAR),
        @Result(column = "idestadotraspaso", property = "idEstadoTraspaso", jdbcType = JdbcType.VARCHAR),
        @Result(column = "estadotraspaso", property = "estadoTraspaso", jdbcType = JdbcType.VARCHAR),
        @Result(column = "archivarfact", property = "archivarFact", jdbcType = JdbcType.BOOLEAN),
        @Result(column = "usumodificacion", property = "usuModificacion", jdbcType = JdbcType.VARCHAR),
        @Result(column = "nombrefichero", property = "nombreFichero", jdbcType = JdbcType.VARCHAR),
        @Result(column = "logerror", property = "logError", jdbcType = JdbcType.VARCHAR),
        @Result(column = "logtraspaso", property = "logTraspaso", jdbcType = JdbcType.VARCHAR),
        @Result(column = "traspasofacturas", property = "traspasoFacturas", jdbcType = JdbcType.BOOLEAN),
        @Result(column = "generapdf", property = "generaPDF", jdbcType = JdbcType.BOOLEAN),
        @Result(column = "envio", property = "envio", jdbcType = JdbcType.BOOLEAN),
        @Result(column = "idtipoplantillamail", property = "idTipoPlantillaMail", jdbcType = JdbcType.VARCHAR),
        @Result(column = "traspaso_platilla", property = "traspasoPlatilla", jdbcType = JdbcType.VARCHAR),
        @Result(column = "traspaso_codauditoria_def", property = "traspasoCodAuditoriaDef", jdbcType = JdbcType.VARCHAR),
        @Result(column = "importe", property = "importe", jdbcType = JdbcType.VARCHAR),
        @Result(column = "fechapresentacion", property = "fechaPresentacion", jdbcType = JdbcType.DATE),
        @Result(column = "fecharecibosprimeros", property = "fechaRecibosPrimeros", jdbcType = JdbcType.DATE),
        @Result(column = "fecharecibosrecurrentes", property = "fechaRecibosRecurrentes", jdbcType = JdbcType.DATE),
        @Result(column = "fechareciboscor1", property = "fechaRecibosCOR1", jdbcType = JdbcType.DATE),
        @Result(column = "fecharecibosb2b", property = "fechaRecibosB2B", jdbcType = JdbcType.DATE),
        @Result(column = "fechamodificacion", property = "fechaModificacion", jdbcType = JdbcType.DATE),
        @Result(column = "idmodelofactura", property = "idModeloFactura", jdbcType = JdbcType.VARCHAR),
        @Result(column = "idmodelorectificativa", property = "idModeloRectificativa", jdbcType = JdbcType.VARCHAR),
        @Result(column = "modelofactura", property = "modeloFactura", jdbcType = JdbcType.VARCHAR),
        @Result(column = "modelorectificativa", property = "modeloRectificativa", jdbcType = JdbcType.VARCHAR)
    })
    List<FacFacturacionprogramadaItem> getFacturacionesProgramadas(FacFacturacionprogramadaItem facturacionProgramada, Short idInstitucion, String idioma, Integer rownum);

    @SelectProvider(type = FacFacturacionprogramadaExtendsSqlProvider.class, method = "comboFacturaciones")
    @Results({
            @Result(column = "id", property = "value", jdbcType = JdbcType.NUMERIC),
            @Result(column = "descripcion", property = "label", jdbcType = JdbcType.VARCHAR)
    })
    List<ComboItem> comboFacturaciones(Short idInstitucion);
}
