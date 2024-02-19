package org.itcgae.siga.db.services.fac.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.FacturaItem;
import org.itcgae.siga.DTO.fac.FicherosAbonosItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.FacAbono;
import org.itcgae.siga.db.mappers.FacAbonoMapper;
import org.itcgae.siga.db.services.fac.providers.FacAbonoExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Primary
public interface FacAbonoExtendsMapper extends FacAbonoMapper {

	@SelectProvider(type = FacAbonoExtendsSqlProvider.class, method = "getAbonos")
	@Results({
			@Result(column = "idabono", property = "idAbono", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numeroabono", property = "numeroFactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fecha", property = "fechaEmision", jdbcType = JdbcType.VARCHAR),
			@Result(column = "facturacion", property = "facturacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ncolident", property = "numeroColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nifcif", property = "numeroIdentificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombreCompleto", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idpersona", property = "idCliente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombreinst", property = "nombreInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "imptotal", property = "importefacturado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "imptotalporpagar", property = "importeAdeudadoPendiente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idEstado", property = "idEstado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "estado", property = "estado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numcomunicaciones", property = "comunicacionesFacturas", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ultcomunicacion", property = "ultimaComunicacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "tipo", property = "tipo", jdbcType = JdbcType.VARCHAR)
	})
	List<FacturaItem> getAbonos(FacturaItem item, String idInstitucion, String idLenguaje, Integer maxRows);

	@SelectProvider(type = FacAbonoExtendsSqlProvider.class, method = "getAbono")
	@Results({
			@Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDABONO", property = "idAbono", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDFACTURA", property = "idFactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROABONO", property = "numeroFactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDESTADO", property = "idEstado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHA", property = "fechaEmision", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTAL", property = "importefacturado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTALNETO", property = "importeNeto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTALIVA", property = "importeIVA", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTALANTICIPADO", property = "importeAnticipado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTALCOMPENSADO", property = "importeCompensado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTALPAGADOPORCAJA", property = "importeCaja", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTALPAGADOPORBANCO", property = "importeBanco", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTALPAGADO", property = "importePagado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTALPORPAGAR", property = "importeAdeudadoPendiente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPANULADO", property = "importeAnuladoAb", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTALABONADOEFECTIVO", property = "importeCajaAb", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTALABONADOPORBANCO", property = "importeBancoAb", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTALABONADO", property = "importePagadoAb", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPPENDIENTEPORABONAR", property = "importeAdeudadoPendienteAb", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OBSERVACIONES", property = "observacionesAbono", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MOTIVOS", property = "motivosAbono", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idCliente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NIFCIF", property = "numeroIdentificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS", property = "apellidos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NCOLIDENT", property = "numeroColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONADEUDOR", property = "idDeudor", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ACREEDOR_ID", property = "identificacionDeudor", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ACREEDOR_NOMBRE", property = "descripcionDeudor", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR)
	})
	List<FacturaItem> getAbono(String idAbono, String idInstitucion,String idLenguaje);

	@SelectProvider(type = FacAbonoExtendsSqlProvider.class, method = "getNewAbonoID")
	@Results({
			@Result(column = "ID", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getNewAbonoID(String idInstitucion);
	
	@SelectProvider(type = FacAbonoExtendsSqlProvider.class, method = "getNuevoID")
    Long getNuevoID(String idInstitucion);

    @SelectProvider(type = FacAbonoExtendsSqlProvider.class, method = "getIdAbonosPorPago")
    List<Long> getIdAbonosPorPago(Short idInstitucion, Integer idPagosjg);

    @UpdateProvider(type = FacAbonoExtendsSqlProvider.class, method = "restableceValoresAbono")
    int restableceValoresAbono(Short idInstitucion, Long idDisqueteAbono);

    @SelectProvider(type = FacAbonoExtendsSqlProvider.class, method = "hayAbonoPosterior")
    List<FacAbono> hayAbonoPosterior(Short idInstitucion, Integer idPago);

	@SelectProvider(type = FacAbonoExtendsSqlProvider.class, method = "getUltimoNumeroAbono")
    Long getUltimoNumeroAbono(String prefijo, String sufijo, Integer logitud, Short idInstitucion);

    @SelectProvider(type = FacAbonoExtendsSqlProvider.class, method = "getAbonoAnterior")
    List<Long> getAbonoAnterior(Short idInstitucion, String fecha);
    
    @SelectProvider(type = FacAbonoExtendsSqlProvider.class, method = "getPagosCerrados")
    List<FacAbono> getPagosCerrados(Short idInstitucion, String anio);

	@SelectProvider(type = FacAbonoExtendsSqlProvider.class, method = "getNuevoNumeroAbono")
	String getNuevoNumeroAbono(String idInstitucion, String idContador);

	@SelectProvider(type = FacAbonoExtendsSqlProvider.class, method = "getAbonosBanco")
	List<FacAbono> getAbonosBanco(Short idInstitucion, String bancosCodigo, Short idSufijo, List<String> idAbonos);

	@SelectProvider(type = FacAbonoExtendsSqlProvider.class, method = "getAbonosBancoSjcs")
	List<FacAbono> getAbonosBancoSjcs(Short idInstitucion, String bancosCodigo, Short idSufijo, List<String> idAbonos);

	@SelectProvider(type = FacAbonoExtendsSqlProvider.class, method = "getBancosSufijosSjcs")
	@Results({
		@Result(column = "BANCOS_CODIGO", property = "bancosCodigo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDSUFIJO", property = "idSufijo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPROPSEPA", property = "propSEPA", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPROPOTROS", property = "propOtros", jdbcType = JdbcType.VARCHAR)
	})
	List<FicherosAbonosItem> getBancosSufijosSjcs(Short idInstitucion);

}
