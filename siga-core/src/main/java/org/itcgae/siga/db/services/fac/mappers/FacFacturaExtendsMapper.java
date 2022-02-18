package org.itcgae.siga.db.services.fac.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.DatoImpresionInformeFacturaDTO;
import org.itcgae.siga.DTO.fac.FacturaFacturacionProgramadaDTO;
import org.itcgae.siga.DTO.fac.FacturaItem;
import org.itcgae.siga.DTO.fac.FacturasFacturacionRapidaDTO;
import org.itcgae.siga.DTO.fac.InformeFacturacionItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.FacFacturaMapper;
import org.itcgae.siga.db.services.fac.providers.FacFacturaExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface FacFacturaExtendsMapper extends FacFacturaMapper {

	@SelectProvider(type = FacFacturaExtendsSqlProvider.class, method = "getFacturas")
	@Results({
			@Result(column = "idfactura", property = "idFactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numerofactura", property = "numeroFactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fecha", property = "fechaEmision", jdbcType = JdbcType.VARCHAR),
			@Result(column = "facturacion", property = "facturacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ncolident", property = "numeroColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nifcif", property = "numeroIdentificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombreCompleto", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idpersona", property = "idCliente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombreinst", property = "nombreInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "imptotal", property = "importefacturado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "imptotalporpagar", property = "importeAdeudadoPendiente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "estado", property = "estado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numcomunicaciones", property = "comunicacionesFacturas", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ultcomunicacion", property = "ultimaComunicacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "tipo", property = "tipo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDFORMAPAGO", property = "idFormaPago", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREFORMAPAGO", property = "nombreFormaPago", jdbcType = JdbcType.VARCHAR),
			@Result(column = "estado_max_historico", property = "estadoUlt", jdbcType = JdbcType.VARCHAR),
			@Result(column = "imptotalporpagar_max", property = "importePorPagarUlt", jdbcType = JdbcType.VARCHAR),
			@Result(column = "imptotalpagado_max", property = "importePagadoUlt", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idaccionult", property = "idAccionUlt", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechamodificacionult", property = "fechaModificacionUlt", jdbcType = JdbcType.VARCHAR),
			
	})
	List<FacturaItem> getFacturas(FacturaItem item, String idInstitucion, String idLenguaje);


	@SelectProvider(type = FacFacturaExtendsSqlProvider.class, method = "getFactura")
	@Results({
			@Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDFACTURA", property = "idFactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROFACTURA", property = "numeroFactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAEMISION", property = "fechaEmision", jdbcType = JdbcType.VARCHAR),
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
			@Result(column = "OBSERVACIONES", property = "observacionesFactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OBSERVINFORME", property = "observacionesFicheroFactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDSERIEFACTURACION", property = "idSerieFacturacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPROGRAMACION", property = "idFacturacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREABREVIADO", property = "serie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FACTURACION", property = "facturacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechaEmisionFacturacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idCliente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NIFCIF", property = "numeroIdentificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS", property = "apellidos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NCOLIDENT", property = "numeroColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONADEUDOR", property = "idDeudor", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ACREEDOR_ID", property = "identificacionDeudor", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ACREEDOR_NOMBRE", property = "descripcionDeudor", jdbcType = JdbcType.VARCHAR),
			@Result(column = "BANCOS_CODIGO", property = "bancosCodigo", jdbcType = JdbcType.VARCHAR)
	})
	List<FacturaItem> getFactura(String idFactura, String idInstitucion);


	@SelectProvider(type = FacFacturaExtendsSqlProvider.class, method = "getInformeFacturacionActual")
	@Results({
			@Result(column = "momento", property = "momento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "formaPago", property = "formaPago", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numeroFacturas", property = "numeroFacturas", jdbcType = JdbcType.VARCHAR),
			@Result(column = "total", property = "total", jdbcType = JdbcType.VARCHAR),
			@Result(column = "totalPendiente", property = "totalPendiente", jdbcType = JdbcType.VARCHAR)
	})
	List<InformeFacturacionItem> getInformeFacturacionActual(String idSerieFacturacion, String idProgramacion, String idInstitucion, String idLenguaje);

	@SelectProvider(type = FacFacturaExtendsSqlProvider.class, method = "getInformeFacturacionOriginal")
	@Results({
			@Result(column = "momento", property = "momento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "formaPago", property = "formaPago", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numeroFacturas", property = "numeroFacturas", jdbcType = JdbcType.VARCHAR),
			@Result(column = "total", property = "total", jdbcType = JdbcType.VARCHAR),
			@Result(column = "totalPendiente", property = "totalPendiente", jdbcType = JdbcType.VARCHAR)
	})
	List<InformeFacturacionItem> getInformeFacturacionOriginal(String idSerieFacturacion, String idProgramacion, String idInstitucion, String idLenguaje);

	@SelectProvider(type = FacFacturaExtendsSqlProvider.class, method = "getNewFacturaID")
	@Results({
			@Result(column = "ID", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getNewFacturaID(String idInstitucion);

	@SelectProvider(type = FacFacturaExtendsSqlProvider.class, method = "getNuevoNumeroFactura")
	@Results({
			@Result(column = "NUEVOCONTADOR", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getNuevoNumeroFactura(String idInstitucion, String idSerieFacturacion);

	@SelectProvider(type = FacFacturaExtendsSqlProvider.class, method = "getFacturasDeFacturacionProgramada")
	@Results({
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDFACTURA", property = "idFactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROFACTURA", property = "numeroFactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDESTADOPDF", property = "idEstadoPdf", jdbcType = JdbcType.DECIMAL)
	})
	List<FacturaFacturacionProgramadaDTO> getFacturasDeFacturacionProgramada(String institucion, String seriefacturacion, String idProgramacion);

	@SelectProvider(type = FacFacturaExtendsSqlProvider.class, method = "obtenerFacturasFacturacionRapida")
	@Results({
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDFACTURA", property = "idFactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROFACTURA", property = "numeroFactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDSERIEFACTURACION", property = "idSerieFacturacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPROGRAMACION", property = "idProgramacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDPETICION", property = "idPeticion", jdbcType = JdbcType.DECIMAL)
	})
	List<FacturasFacturacionRapidaDTO> obtenerFacturasFacturacionRapida(String idInstitucion, String idPeticion, String idSolicitudCertificado);

	@SelectProvider(type = FacFacturaExtendsSqlProvider.class, method = "getDatosImpresionInformeFactura")
	DatoImpresionInformeFacturaDTO getDatosImpresionInformeFactura(String idInstitucion, String idFactura);

	@UpdateProvider(type = FacFacturaExtendsSqlProvider.class, method = "updateImportesFactura")
	int updateImportesFactura(String idFactura, Short idInstitucion, Integer idUsuario);
}