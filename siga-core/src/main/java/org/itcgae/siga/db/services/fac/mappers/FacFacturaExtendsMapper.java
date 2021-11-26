package org.itcgae.siga.db.services.fac.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.FacturaItem;
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
			@Result(column = "fechaemision", property = "fechaEmision", jdbcType = JdbcType.VARCHAR),
			@Result(column = "facturacion", property = "facturacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ncolegiado", property = "numeroColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nifcif", property = "numeroIdentificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "apellidos1", property = "apellidos1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "apellidos2", property = "apellidos2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombre", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombreinst", property = "nombreInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "imptotal", property = "importefacturado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "imptotalporpagar", property = "importeAdeudadoPendiente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "estado", property = "estado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numcomunicaciones", property = "comunicacionesFacturas", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ultcomunicacion", property = "ultimaComunicacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "tipo", property = "tipo", jdbcType = JdbcType.VARCHAR)
	})
	List<FacturaItem> getFacturas(FacturaItem item, String idInstitucion, String idLenguaje);

	@SelectProvider(type = FacFacturaExtendsSqlProvider.class, method = "getAbonos")
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
}
