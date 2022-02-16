package org.itcgae.siga.db.services.fac.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.FacFacturacionprogramadaExtendsDTO;
import org.itcgae.siga.DTO.fac.FacFacturacionprogramadaItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.entities.FacFacturacionprogramada;
import org.itcgae.siga.db.mappers.FacFacturacionprogramadaMapper;
import org.itcgae.siga.db.services.fac.providers.FacFacturacionprogramadaExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

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
        @Result(column = "tipoplantillamail", property = "tipoPlantillaMail", jdbcType = JdbcType.VARCHAR),
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

    @SelectProvider(type = FacFacturacionprogramadaExtendsSqlProvider.class, method = "getNextIdFacturacionProgramada")
    @Results( { @Result(column = "idprogramacion", property = "newId", jdbcType = JdbcType.VARCHAR) })
    NewIdDTO getNextIdFacturacionProgramada(Short idInstitucion, Long idSerieFacturacion);

    @SelectProvider(type = FacFacturacionprogramadaExtendsSqlProvider.class, method = "comboFacturaciones")
    @Results({
            @Result(column = "id", property = "value", jdbcType = JdbcType.NUMERIC),
            @Result(column = "descripcion", property = "label", jdbcType = JdbcType.VARCHAR)
    })
    List<ComboItem> comboFacturaciones(Short idInstitucion);

    @SelectProvider(type = FacFacturacionprogramadaExtendsSqlProvider.class, method = "isSerieFacturacionActiva")
	boolean isSerieFacturacionActiva(Short idInstitucion, Long idSerieFacturacion, Long idProgramacion);

    @SelectProvider(type = FacFacturacionprogramadaExtendsSqlProvider.class, method = "getFacturacionesProTratarFacturacion")
    @Results({
            @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDSERIEFACTURACION", property = "idseriefacturacion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDPROGRAMACION", property = "idprogramacion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "FECHAINICIOPRODUCTOS", property = "fechainicioproductos", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAFINPRODUCTOS", property = "fechafinproductos", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAINICIOSERVICIOS", property = "fechainicioservicios", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAFINSERVICIOS", property = "fechafinservicios", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAPROGRAMACION", property = "fechaprogramacion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAPREVISTAGENERACION", property = "fechaprevistageneracion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDPREVISION", property = "idprevision", jdbcType = JdbcType.DECIMAL),
            @Result(column = "FECHAREALGENERACION", property = "fecharealgeneracion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHACONFIRMACION", property = "fechaconfirmacion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "IDESTADOCONFIRMACION", property = "idestadoconfirmacion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDESTADOPDF", property = "idestadopdf", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDESTADOENVIO", property = "idestadoenvio", jdbcType = JdbcType.DECIMAL),
            @Result(column = "FECHAPREVISTACONFIRM", property = "fechaprevistaconfirm", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "GENERAPDF", property = "generapdf", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ENVIO", property = "envio", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ARCHIVARFACT", property = "archivarfact", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHACARGO", property = "fechacargo", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "CONFDEUDOR", property = "confdeudor", jdbcType = JdbcType.VARCHAR),
            @Result(column = "CONFINGRESOS", property = "confingresos", jdbcType = JdbcType.VARCHAR),
            @Result(column = "CTAINGRESOS", property = "ctaingresos", jdbcType = JdbcType.VARCHAR),
            @Result(column = "CTACLIENTES", property = "ctaclientes", jdbcType = JdbcType.VARCHAR),
            @Result(column = "VISIBLE", property = "visible", jdbcType = JdbcType.VARCHAR),
            @Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDTIPOPLANTILLAMAIL", property = "idtipoplantillamail", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDTIPOENVIOS", property = "idtipoenvios", jdbcType = JdbcType.DECIMAL),
            @Result(column = "FECHAPRESENTACION", property = "fechapresentacion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHARECIBOSPRIMEROS", property = "fecharecibosprimeros", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHARECIBOSRECURRENTES", property = "fecharecibosrecurrentes", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHARECIBOSCOR1", property = "fechareciboscor1", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHARECIBOSB2B", property = "fecharecibosb2b", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "NOMBREFICHERO", property = "nombrefichero", jdbcType = JdbcType.VARCHAR),
            @Result(column = "LOGERROR", property = "logerror", jdbcType = JdbcType.VARCHAR),
            @Result(column = "TRASPASO_PLANTILLA", property = "traspasoPlantilla", jdbcType = JdbcType.VARCHAR),
            @Result(column = "TRASPASO_CODAUDITORIA_DEF", property = "traspasoCodauditoriaDef", jdbcType = JdbcType.VARCHAR),
            @Result(column = "TRASPASOFACTURAS", property = "traspasofacturas", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDESTADOTRASPASO", property = "idestadotraspaso", jdbcType = JdbcType.DECIMAL),
            @Result(column = "LOGTRASPASO", property = "logtraspaso", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDMODELOFACTURA", property = "idmodelofactura", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDMODELORECTIFICATIVA", property = "idmodelorectificativa", jdbcType = JdbcType.DECIMAL),
            @Result(column = "NOMBREABREVIADO", property = "nombreabreviado", jdbcType = JdbcType.VARCHAR)
    })
    List<FacFacturacionprogramadaExtendsDTO> getFacturacionesProTratarFacturacion(Short idInstitucion, Double tiempoMaximoEjecucionBloqueada);

    @SelectProvider(type = FacFacturacionprogramadaExtendsSqlProvider.class, method = "getFacturacionesProTratarConfirmacion")
    @Results({
            @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDSERIEFACTURACION", property = "idseriefacturacion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDPROGRAMACION", property = "idprogramacion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "FECHAINICIOPRODUCTOS", property = "fechainicioproductos", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAFINPRODUCTOS", property = "fechafinproductos", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAINICIOSERVICIOS", property = "fechainicioservicios", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAFINSERVICIOS", property = "fechafinservicios", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAPROGRAMACION", property = "fechaprogramacion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAPREVISTAGENERACION", property = "fechaprevistageneracion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDPREVISION", property = "idprevision", jdbcType = JdbcType.DECIMAL),
            @Result(column = "FECHAREALGENERACION", property = "fecharealgeneracion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHACONFIRMACION", property = "fechaconfirmacion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "IDESTADOCONFIRMACION", property = "idestadoconfirmacion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDESTADOPDF", property = "idestadopdf", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDESTADOENVIO", property = "idestadoenvio", jdbcType = JdbcType.DECIMAL),
            @Result(column = "FECHAPREVISTACONFIRM", property = "fechaprevistaconfirm", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "GENERAPDF", property = "generapdf", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ENVIO", property = "envio", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ARCHIVARFACT", property = "archivarfact", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHACARGO", property = "fechacargo", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "CONFDEUDOR", property = "confdeudor", jdbcType = JdbcType.VARCHAR),
            @Result(column = "CONFINGRESOS", property = "confingresos", jdbcType = JdbcType.VARCHAR),
            @Result(column = "CTAINGRESOS", property = "ctaingresos", jdbcType = JdbcType.VARCHAR),
            @Result(column = "CTACLIENTES", property = "ctaclientes", jdbcType = JdbcType.VARCHAR),
            @Result(column = "VISIBLE", property = "visible", jdbcType = JdbcType.VARCHAR),
            @Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDTIPOPLANTILLAMAIL", property = "idtipoplantillamail", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDTIPOENVIOS", property = "idtipoenvios", jdbcType = JdbcType.DECIMAL),
            @Result(column = "FECHAPRESENTACION", property = "fechapresentacion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHARECIBOSPRIMEROS", property = "fecharecibosprimeros", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHARECIBOSRECURRENTES", property = "fecharecibosrecurrentes", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHARECIBOSCOR1", property = "fechareciboscor1", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHARECIBOSB2B", property = "fecharecibosb2b", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "NOMBREFICHERO", property = "nombrefichero", jdbcType = JdbcType.VARCHAR),
            @Result(column = "LOGERROR", property = "logerror", jdbcType = JdbcType.VARCHAR),
            @Result(column = "TRASPASO_PLANTILLA", property = "traspasoPlantilla", jdbcType = JdbcType.VARCHAR),
            @Result(column = "TRASPASO_CODAUDITORIA_DEF", property = "traspasoCodauditoriaDef", jdbcType = JdbcType.VARCHAR),
            @Result(column = "TRASPASOFACTURAS", property = "traspasofacturas", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDESTADOTRASPASO", property = "idestadotraspaso", jdbcType = JdbcType.DECIMAL),
            @Result(column = "LOGTRASPASO", property = "logtraspaso", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDMODELOFACTURA", property = "idmodelofactura", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDMODELORECTIFICATIVA", property = "idmodelorectificativa", jdbcType = JdbcType.DECIMAL),
            @Result(column = "NOMBREABREVIADO", property = "nombreabreviado", jdbcType = JdbcType.VARCHAR)
    })
    List<FacFacturacionprogramadaExtendsDTO> getFacturacionesProTratarConfirmacion(Short idInstitucion);

    @SelectProvider(type = FacFacturacionprogramadaExtendsSqlProvider.class, method = "getFacturacionesProGenerarPDFsYenviarFacturasProgramacion")
    @Results({
            @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDSERIEFACTURACION", property = "idseriefacturacion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDPROGRAMACION", property = "idprogramacion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "FECHAINICIOPRODUCTOS", property = "fechainicioproductos", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAFINPRODUCTOS", property = "fechafinproductos", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAINICIOSERVICIOS", property = "fechainicioservicios", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAFINSERVICIOS", property = "fechafinservicios", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAPROGRAMACION", property = "fechaprogramacion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAPREVISTAGENERACION", property = "fechaprevistageneracion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDPREVISION", property = "idprevision", jdbcType = JdbcType.DECIMAL),
            @Result(column = "FECHAREALGENERACION", property = "fecharealgeneracion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHACONFIRMACION", property = "fechaconfirmacion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "IDESTADOCONFIRMACION", property = "idestadoconfirmacion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDESTADOPDF", property = "idestadopdf", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDESTADOENVIO", property = "idestadoenvio", jdbcType = JdbcType.DECIMAL),
            @Result(column = "FECHAPREVISTACONFIRM", property = "fechaprevistaconfirm", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "GENERAPDF", property = "generapdf", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ENVIO", property = "envio", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ARCHIVARFACT", property = "archivarfact", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHACARGO", property = "fechacargo", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "CONFDEUDOR", property = "confdeudor", jdbcType = JdbcType.VARCHAR),
            @Result(column = "CONFINGRESOS", property = "confingresos", jdbcType = JdbcType.VARCHAR),
            @Result(column = "CTAINGRESOS", property = "ctaingresos", jdbcType = JdbcType.VARCHAR),
            @Result(column = "CTACLIENTES", property = "ctaclientes", jdbcType = JdbcType.VARCHAR),
            @Result(column = "VISIBLE", property = "visible", jdbcType = JdbcType.VARCHAR),
            @Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDTIPOPLANTILLAMAIL", property = "idtipoplantillamail", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDTIPOENVIOS", property = "idtipoenvios", jdbcType = JdbcType.DECIMAL),
            @Result(column = "FECHAPRESENTACION", property = "fechapresentacion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHARECIBOSPRIMEROS", property = "fecharecibosprimeros", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHARECIBOSRECURRENTES", property = "fecharecibosrecurrentes", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHARECIBOSCOR1", property = "fechareciboscor1", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHARECIBOSB2B", property = "fecharecibosb2b", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "NOMBREFICHERO", property = "nombrefichero", jdbcType = JdbcType.VARCHAR),
            @Result(column = "LOGERROR", property = "logerror", jdbcType = JdbcType.VARCHAR),
            @Result(column = "TRASPASO_PLANTILLA", property = "traspasoPlantilla", jdbcType = JdbcType.VARCHAR),
            @Result(column = "TRASPASO_CODAUDITORIA_DEF", property = "traspasoCodauditoriaDef", jdbcType = JdbcType.VARCHAR),
            @Result(column = "TRASPASOFACTURAS", property = "traspasofacturas", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDESTADOTRASPASO", property = "idestadotraspaso", jdbcType = JdbcType.DECIMAL),
            @Result(column = "LOGTRASPASO", property = "logtraspaso", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDMODELOFACTURA", property = "idmodelofactura", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDMODELORECTIFICATIVA", property = "idmodelorectificativa", jdbcType = JdbcType.DECIMAL),
            @Result(column = "NOMBREABREVIADO", property = "nombreabreviado", jdbcType = JdbcType.VARCHAR)
    })
    List<FacFacturacionprogramadaExtendsDTO> getFacturacionesProGenerarPDFsYenviarFacturasProgramacion(Short idInstitucion, Double tiempoMaximoEjecucionBloqueada);

    @SelectProvider(type = FacFacturacionprogramadaExtendsSqlProvider.class, method = "getFacturacionesProGenerarEnviosFacturasPendientes")
    @Results({
            @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDSERIEFACTURACION", property = "idseriefacturacion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDPROGRAMACION", property = "idprogramacion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "FECHAINICIOPRODUCTOS", property = "fechainicioproductos", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAFINPRODUCTOS", property = "fechafinproductos", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAINICIOSERVICIOS", property = "fechainicioservicios", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAFINSERVICIOS", property = "fechafinservicios", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAPROGRAMACION", property = "fechaprogramacion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAPREVISTAGENERACION", property = "fechaprevistageneracion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDPREVISION", property = "idprevision", jdbcType = JdbcType.DECIMAL),
            @Result(column = "FECHAREALGENERACION", property = "fecharealgeneracion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHACONFIRMACION", property = "fechaconfirmacion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "IDESTADOCONFIRMACION", property = "idestadoconfirmacion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDESTADOPDF", property = "idestadopdf", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDESTADOENVIO", property = "idestadoenvio", jdbcType = JdbcType.DECIMAL),
            @Result(column = "FECHAPREVISTACONFIRM", property = "fechaprevistaconfirm", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "GENERAPDF", property = "generapdf", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ENVIO", property = "envio", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ARCHIVARFACT", property = "archivarfact", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHACARGO", property = "fechacargo", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "CONFDEUDOR", property = "confdeudor", jdbcType = JdbcType.VARCHAR),
            @Result(column = "CONFINGRESOS", property = "confingresos", jdbcType = JdbcType.VARCHAR),
            @Result(column = "CTAINGRESOS", property = "ctaingresos", jdbcType = JdbcType.VARCHAR),
            @Result(column = "CTACLIENTES", property = "ctaclientes", jdbcType = JdbcType.VARCHAR),
            @Result(column = "VISIBLE", property = "visible", jdbcType = JdbcType.VARCHAR),
            @Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDTIPOPLANTILLAMAIL", property = "idtipoplantillamail", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDTIPOENVIOS", property = "idtipoenvios", jdbcType = JdbcType.DECIMAL),
            @Result(column = "FECHAPRESENTACION", property = "fechapresentacion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHARECIBOSPRIMEROS", property = "fecharecibosprimeros", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHARECIBOSRECURRENTES", property = "fecharecibosrecurrentes", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHARECIBOSCOR1", property = "fechareciboscor1", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHARECIBOSB2B", property = "fecharecibosb2b", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "NOMBREFICHERO", property = "nombrefichero", jdbcType = JdbcType.VARCHAR),
            @Result(column = "LOGERROR", property = "logerror", jdbcType = JdbcType.VARCHAR),
            @Result(column = "TRASPASO_PLANTILLA", property = "traspasoPlantilla", jdbcType = JdbcType.VARCHAR),
            @Result(column = "TRASPASO_CODAUDITORIA_DEF", property = "traspasoCodauditoriaDef", jdbcType = JdbcType.VARCHAR),
            @Result(column = "TRASPASOFACTURAS", property = "traspasofacturas", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDESTADOTRASPASO", property = "idestadotraspaso", jdbcType = JdbcType.DECIMAL),
            @Result(column = "LOGTRASPASO", property = "logtraspaso", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDMODELOFACTURA", property = "idmodelofactura", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDMODELORECTIFICATIVA", property = "idmodelorectificativa", jdbcType = JdbcType.DECIMAL),
            @Result(column = "NOMBREABREVIADO", property = "nombreabreviado", jdbcType = JdbcType.VARCHAR)
    })
    List<FacFacturacionprogramadaExtendsDTO> getFacturacionesProGenerarEnviosFacturasPendientes(Short idInstitucion, Double tiempoMaximoEjecucionBloqueada);

    @SelectProvider(type = FacFacturacionprogramadaExtendsSqlProvider.class, method = "getFacturacionesProComprobacionTraspasoFacturas")
    @Results({
            @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDSERIEFACTURACION", property = "idseriefacturacion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDPROGRAMACION", property = "idprogramacion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "FECHAINICIOPRODUCTOS", property = "fechainicioproductos", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAFINPRODUCTOS", property = "fechafinproductos", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAINICIOSERVICIOS", property = "fechainicioservicios", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAFINSERVICIOS", property = "fechafinservicios", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAPROGRAMACION", property = "fechaprogramacion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAPREVISTAGENERACION", property = "fechaprevistageneracion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDPREVISION", property = "idprevision", jdbcType = JdbcType.DECIMAL),
            @Result(column = "FECHAREALGENERACION", property = "fecharealgeneracion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHACONFIRMACION", property = "fechaconfirmacion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "IDESTADOCONFIRMACION", property = "idestadoconfirmacion", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDESTADOPDF", property = "idestadopdf", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDESTADOENVIO", property = "idestadoenvio", jdbcType = JdbcType.DECIMAL),
            @Result(column = "FECHAPREVISTACONFIRM", property = "fechaprevistaconfirm", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "GENERAPDF", property = "generapdf", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ENVIO", property = "envio", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ARCHIVARFACT", property = "archivarfact", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FECHACARGO", property = "fechacargo", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "CONFDEUDOR", property = "confdeudor", jdbcType = JdbcType.VARCHAR),
            @Result(column = "CONFINGRESOS", property = "confingresos", jdbcType = JdbcType.VARCHAR),
            @Result(column = "CTAINGRESOS", property = "ctaingresos", jdbcType = JdbcType.VARCHAR),
            @Result(column = "CTACLIENTES", property = "ctaclientes", jdbcType = JdbcType.VARCHAR),
            @Result(column = "VISIBLE", property = "visible", jdbcType = JdbcType.VARCHAR),
            @Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDTIPOPLANTILLAMAIL", property = "idtipoplantillamail", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDTIPOENVIOS", property = "idtipoenvios", jdbcType = JdbcType.DECIMAL),
            @Result(column = "FECHAPRESENTACION", property = "fechapresentacion", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHARECIBOSPRIMEROS", property = "fecharecibosprimeros", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHARECIBOSRECURRENTES", property = "fecharecibosrecurrentes", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHARECIBOSCOR1", property = "fechareciboscor1", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "FECHARECIBOSB2B", property = "fecharecibosb2b", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "NOMBREFICHERO", property = "nombrefichero", jdbcType = JdbcType.VARCHAR),
            @Result(column = "LOGERROR", property = "logerror", jdbcType = JdbcType.VARCHAR),
            @Result(column = "TRASPASO_PLANTILLA", property = "traspasoPlantilla", jdbcType = JdbcType.VARCHAR),
            @Result(column = "TRASPASO_CODAUDITORIA_DEF", property = "traspasoCodauditoriaDef", jdbcType = JdbcType.VARCHAR),
            @Result(column = "TRASPASOFACTURAS", property = "traspasofacturas", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDESTADOTRASPASO", property = "idestadotraspaso", jdbcType = JdbcType.DECIMAL),
            @Result(column = "LOGTRASPASO", property = "logtraspaso", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IDMODELOFACTURA", property = "idmodelofactura", jdbcType = JdbcType.DECIMAL),
            @Result(column = "IDMODELORECTIFICATIVA", property = "idmodelorectificativa", jdbcType = JdbcType.DECIMAL),
            @Result(column = "NOMBREABREVIADO", property = "nombreabreviado", jdbcType = JdbcType.VARCHAR)
    })
    List<FacFacturacionprogramadaExtendsDTO> getFacturacionesProComprobacionTraspasoFacturas(Short idInstitucion, Double tiempoMaximoEjecucionBloqueada);

    @SelectProvider(type = FacFacturacionprogramadaExtendsSqlProvider.class, method = "getNumTotalFacturacionesProTratarFacturacion")
    Integer getNumTotalFacturacionesProTratarFacturacion(Short idInstitucion, Double tiempoMaximoEjecucionBloqueada);

    @SelectProvider(type = FacFacturacionprogramadaExtendsSqlProvider.class, method = "getPosicionFacturacionProTratarFacturacion")
    Integer getPosicionFacturacionProTratarFacturacion(Short idInstitucion, Double tiempoMaximoEjecucionBloqueada, String idSerieFacturacion, String idProgramacion);

    @SelectProvider(type = FacFacturacionprogramadaExtendsSqlProvider.class, method = "getNumTotalFacturacionesProTratarConfirmacion")
    Integer getNumTotalFacturacionesProTratarConfirmacion(Short idInstitucion);

    @SelectProvider(type = FacFacturacionprogramadaExtendsSqlProvider.class, method = "getPosicionFacturacionProTratarConfirmacion")
    Integer getPosicionFacturacionProTratarConfirmacion(Short idInstitucion, String idSerieFacturacion, String idProgramacion);

    @SelectProvider(type = FacFacturacionprogramadaExtendsSqlProvider.class, method = "getNumTotalFacturacionesProGenerarPDFsYenviarFacturasProgramacion")
    Integer getNumTotalFacturacionesProGenerarPDFsYenviarFacturasProgramacion(Short idInstitucion, Double tiempoMaximoEjecucionBloqueada);

    @SelectProvider(type = FacFacturacionprogramadaExtendsSqlProvider.class, method = "getPosicionFacturacionProGenerarPDFsYenviarFacturasProgramacion")
    Integer getPosicionFacturacionProGenerarPDFsYenviarFacturasProgramacion(Short idInstitucion, String idSerieFacturacion, String idProgramacion, Double tiempoMaximoEjecucionBloqueada);

    @SelectProvider(type = FacFacturacionprogramadaExtendsSqlProvider.class, method = "getNumTotalFacturacionesProGenerarEnviosFacturasPendientes")
    Integer getNumTotalFacturacionesProGenerarEnviosFacturasPendientes(Short idInstitucion, Double tiempoMaximoEjecucionBloqueada);

    @SelectProvider(type = FacFacturacionprogramadaExtendsSqlProvider.class, method = "getPosicionFacturacionProGenerarEnviosFacturasPendientes")
    Integer getPosicionFacturacionProGenerarEnviosFacturasPendientes(Short idInstitucion, String idSerieFacturacion, String idProgramacion, Double tiempoMaximoEjecucionBloqueada);

    @SelectProvider(type = FacFacturacionprogramadaExtendsSqlProvider.class, method = "getNumTotalFacturacionesProComprobacionTraspasoFacturas")
    Integer getNumTotalFacturacionesProComprobacionTraspasoFacturas(Short idInstitucion, Double tiempoMaximoEjecucionBloqueada);

    @SelectProvider(type = FacFacturacionprogramadaExtendsSqlProvider.class, method = "getPosicionFacturacionProComprobacionTraspasoFacturas")
    Integer getPosicionFacturacionProComprobacionTraspasoFacturas(Short idInstitucion, String idSerieFacturacion, String idProgramacion, Double tiempoMaximoEjecucionBloqueada);

}
