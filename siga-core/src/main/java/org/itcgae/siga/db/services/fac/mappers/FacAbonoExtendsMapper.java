package org.itcgae.siga.db.services.fac.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.FacturaItem;
import org.itcgae.siga.db.mappers.FacAbonoMapper;
import org.itcgae.siga.db.services.fac.providers.FacAbonoExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface FacAbonoExtendsMapper extends FacAbonoMapper {

	@SelectProvider(type = FacAbonoExtendsSqlProvider.class, method = "getAbonos")
	@Results({
			@Result(column = "idfactura", property = "idFactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fecha", property = "fechaEmision", jdbcType = JdbcType.VARCHAR),
			@Result(column = "facturacion", property = "facturacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ncolegiado", property = "numeroColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nifcif", property = "numeroIdentificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "apellidos", property = "apellidos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombre", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombreinst", property = "nombreInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "imptotal", property = "importefacturado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "imptotalporpagar", property = "importeAdeudadoPendiente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "estado", property = "estado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numcomunicaciones", property = "comunicacionesFacturas", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ultcomunicacion", property = "ultimaComunicacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "tipo", property = "tipo", jdbcType = JdbcType.VARCHAR)
	})
	List<FacturaItem> getAbonos(FacturaItem item, String idInstitucion, String idLenguaje);

	@SelectProvider(type = FacAbonoExtendsSqlProvider.class, method = "getAbono")
	@Results({
			@Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDABONO", property = "idFactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROABONO", property = "numeroFactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHA", property = "fechaEmision", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTAL", property = "importefacturado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTALNETO", property = "importeNeto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTALIVA", property = "importeIVA", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPANULADO", property = "importeAnulado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTALABONADOEFECTIVO", property = "importeCaja", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTALABONADOPORBANCO", property = "importeBanco", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPTOTALABONADO", property = "importePagado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPPENDIENTEPORABONAR", property = "importeAdeudadoPendiente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OBSERVACIONES", property = "observacionesAbono", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MOTIVOS", property = "MotivosAbono", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idCliente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NIFCIF", property = "numeroIdentificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS", property = "apellidos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NCOLIDENT", property = "numeroColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONADEUDOR", property = "idDeudor", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ACREEDOR_ID", property = "identificacionDeudor", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ACREEDOR_NOMBRE", property = "descripcionDeudor", jdbcType = JdbcType.VARCHAR)
	})
	List<FacturaItem> getAbono(String idFactura, String idInstitucion);
}
