package org.itcgae.siga.db.services.fac.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.EstadosPagosItem;
import org.itcgae.siga.db.services.fac.providers.FacHistoricofacturaExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface FacHistoricofacturaExtendsMapper extends FacHistoricofacturaMapper {

	@SelectProvider(type = FacHistoricofacturaExtendsSqlProvider.class, method = "getEstadosPagos")
	@Results({
			@Result(column = "IDABONO", property = "idFactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROLINEA", property = "numeroLinea", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCIONLINEA", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PRECIOUNITARIO", property = "precioUnitario", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CANTIDAD", property = "cantidad", jdbcType = JdbcType.VARCHAR),
			@Result(column = "importeNeto", property = "importeNeto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "importeIVA", property = "importeIVA", jdbcType = JdbcType.VARCHAR),
			@Result(column = "importeTotal", property = "importeTotal", jdbcType = JdbcType.VARCHAR),
	})
	List<EstadosPagosItem> getEstadosPagos(String idFactura, String idInstitucion, String idLenguaje);
}
