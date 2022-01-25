package org.itcgae.siga.db.services.fac.mappers;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.FacturaLineaItem;
import org.itcgae.siga.db.mappers.FacLineaabonoMapper;
import org.itcgae.siga.db.services.fac.providers.FacLineaabonoExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface FacLineaabonoExtendsMapper extends FacLineaabonoMapper {

	@SelectProvider(type = FacLineaabonoExtendsSqlProvider.class, method = "getLineasAbono")
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
	List<FacturaLineaItem> getLineasAbono(String idFactura, String idInstitucion);
	
	@SelectProvider(type = FacLineaabonoExtendsSqlProvider.class, method = "getNuevoID")
    Long getNuevoID(String idInstitucion, String idAbono);

    @DeleteProvider(type = FacLineaabonoExtendsSqlProvider.class, method = "deleteDeshacerCierre")
    int deleteDeshacerCierre(Short idInstitucion, List<Integer> idPagos);
}
