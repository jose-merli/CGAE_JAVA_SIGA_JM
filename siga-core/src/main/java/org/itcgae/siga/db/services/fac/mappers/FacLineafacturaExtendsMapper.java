package org.itcgae.siga.db.services.fac.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.FacturaLineaItem;
import org.itcgae.siga.DTO.fac.LineaImpresionInformeDTO;
import org.itcgae.siga.db.mappers.FacLineafacturaMapper;
import org.itcgae.siga.db.services.fac.providers.FacLineafacturaExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface FacLineafacturaExtendsMapper extends FacLineafacturaMapper {

	@SelectProvider(type = FacLineafacturaExtendsSqlProvider.class, method = "getLineasFactura")
	@Results({
			@Result(column = "IDFACTURA", property = "idFactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROLINEA", property = "numeroLinea", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PRECIOUNITARIO", property = "precioUnitario", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CANTIDAD", property = "cantidad", jdbcType = JdbcType.VARCHAR),
			@Result(column = "importeNeto", property = "importeNeto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPOIVA", property = "idTipoIVA", jdbcType = JdbcType.VARCHAR),
			@Result(column = "tipoIVA", property = "tipoIVA", jdbcType = JdbcType.VARCHAR),
			@Result(column = "importeIVA", property = "importeIVA", jdbcType = JdbcType.VARCHAR),
			@Result(column = "importeTotal", property = "importeTotal", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPORTEANTICIPADO", property = "importeAnticipado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idCodigoBanco", property = "idCodigoBanco", jdbcType = JdbcType.VARCHAR)
	})
	List<FacturaLineaItem> getLineasFactura(String idFactura, String idInstitucion, Integer tamMaximo);

	@SelectProvider(type = FacLineafacturaExtendsSqlProvider.class, method = "getLineasImpresionInforme")
	List<LineaImpresionInformeDTO> getLineasImpresionInforme(String idInstitucion, String idFactura);

}
