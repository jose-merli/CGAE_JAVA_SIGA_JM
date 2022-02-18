package org.itcgae.siga.db.services.fac.mappers;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.EstadosPagosItem;
import org.itcgae.siga.db.entities.FacFactura;
import org.itcgae.siga.db.entities.FacHistoricofactura;
import org.itcgae.siga.db.mappers.FacHistoricofacturaMapper;
import org.itcgae.siga.db.services.fac.providers.FacHistoricofacturaExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface FacHistoricofacturaExtendsMapper extends FacHistoricofacturaMapper {

	@SelectProvider(type = FacHistoricofacturaExtendsSqlProvider.class, method = "getEstadosPagos")
	@Results({
			@Result(column = "FECHAMODIFICACION", property = "fechaModificaion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPOACCION", property = "idAccion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ACCION", property = "accion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDESTADO", property = "idEstado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IBAN", property = "iban", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTALPAGADO", property = "impTotalPagado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTALPORPAGAR", property = "impTotalPorPagar", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDSJCS", property = "IDSJCS", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ENLACEFACTURA", property = "enlaceFactura", jdbcType = JdbcType.BOOLEAN),
			@Result(column = "NUMEROFACTURA", property = "numeroFactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDFACTURA", property = "idFactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDCARGOS", property = "idCargos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDDEVOLUCIONES", property = "idDevoluciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ENLACEABONO", property = "enlaceAbono", jdbcType = JdbcType.BOOLEAN),
			@Result(column = "NUMEROABONO", property = "numeroAbono", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDABONO", property = "idAbono", jdbcType = JdbcType.VARCHAR),
			@Result(column = "COMENTARIO", property = "comentario", jdbcType = JdbcType.VARCHAR),
			@Result(column = "COMISIONIDFACTURA", property = "comisionIdFactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "COMISIONFACTURA", property = "comisionFactura", jdbcType = JdbcType.VARCHAR)
	})
	List<EstadosPagosItem> getEstadosPagos(String idFactura, String idInstitucion, String idLenguaje);

	@SelectProvider(type = FacHistoricofacturaExtendsSqlProvider.class, method = "getFacturacionLog")
	@Results({
			@Result(column = "FECHA", property = "fechaModificaion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ACCION", property = "accion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR)
	})
	List<EstadosPagosItem> getFacturacionLog(String idFactura, String idInstitucion, String idLenguaje);

		@InsertProvider(type = FacHistoricofacturaExtendsSqlProvider.class, method = "insertarHistoricoFacParametros")
    int insertarHistoricoFacParametros(String idInstitucion, String idFactura, Integer idTipoAccion,
                                       Integer idPagoPorCaja, Integer idDisqueteCargos, Integer idFacturaIncluidaEnDisquete,
                                       Integer idDisqueteDevoluciones, String idRecibo, Integer idRenegociacion, Integer idAbono, String comisionIdFactura);

    @DeleteProvider(type = FacHistoricofacturaExtendsSqlProvider.class, method = "deleteDeshacerCierre")
    int deleteDeshacerCierre(Short idInstitucion, List<Integer> idPagos);

	@SelectProvider(type = FacHistoricofacturaExtendsSqlProvider.class, method = "facturasDevueltasEnDisquete")
	@Results({
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDFACTURA", property = "idfactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAEMISION", property = "fechaemision", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CONTABILIZADA", property = "contabilizada", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDSERIEFACTURACION", property = "idseriefacturacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPROGRAMACION", property = "idprogramacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDFORMAPAGO", property = "idformapago", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROFACTURA", property = "numerofactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDCUENTA", property = "idcuenta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONADEUDOR", property = "idpersonadeudor", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDCUENTADEUDOR", property = "idcuentadeudor", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CTACLIENTE", property = "ctacliente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OBSERVINFORME", property = "observinforme", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTAL", property = "imptotal", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTALIVA", property = "imptotaliva", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTALNETO", property = "imptotalneto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTALANTICIPADO", property = "imptotalanticipado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTALPAGADOPORCAJA", property = "imptotalpagadoporcaja", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTALPAGADOSOLOCAJA", property = "imptotalpagadosolocaja", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTALPAGADOSOLOTARJETA", property = "imptotalpagadosolotarjeta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTALPAGADOPORBANCO", property = "imptotalpagadoporbanco", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTALPAGADO", property = "imptotalpagado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTALPORPAGAR", property = "imptotalporpagar", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTALCOMPENSADO", property = "imptotalcompensado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "REFMANDATOSEPA", property = "refmandatosepa", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDMANDATO", property = "idmandato", jdbcType = JdbcType.VARCHAR),
			@Result(column = "COMISIONIDFACTURA", property = "comisionidfactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TRASPASADA", property = "traspasada", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ERRORTRASPASO", property = "errortraspaso", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ENVIOTRASPASO", property = "enviotraspaso", jdbcType = JdbcType.VARCHAR)
	})
	List<FacFactura> facturasDevueltasEnDisquete(String idDisquetecargos, String idInstitucion);

	@SelectProvider(type = FacHistoricofacturaExtendsSqlProvider.class, method = "getNextIdHstorico")
	Short getNextIdHstorico(Short idInstitucion, String idFactura);

	@UpdateProvider(type = FacHistoricofacturaExtendsSqlProvider.class, method = "updateImportesHistoricoEmisionFactura")
	int updateImportesHistoricoEmisionFactura(String idFactura, Short idHistorico, Short idInstitucion, Integer idUsuario);

}
