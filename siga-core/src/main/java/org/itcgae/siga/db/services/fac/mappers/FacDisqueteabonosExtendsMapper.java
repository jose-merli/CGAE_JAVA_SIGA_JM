package org.itcgae.siga.db.services.fac.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.FacturasIncluidasItem;
import org.itcgae.siga.DTO.fac.FicherosAbonosItem;
import org.itcgae.siga.db.mappers.FacDisqueteabonosMapper;
import org.itcgae.siga.db.services.fac.providers.FacDisqueteabonosExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface FacDisqueteabonosExtendsMapper extends FacDisqueteabonosMapper {

	@SelectProvider(type = FacDisqueteabonosExtendsSqlProvider.class, method = "getFicherosTransferencias")
	@Results({
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDDISQUETEABONO", property = "idDisqueteAbono", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREFICHERO", property = "nombreFichero", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CUENTA_ENTIDAD", property = "cuentaEntidad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "BANCOS_CODIGO", property = "bancosCodigo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHA", property = "fechaCreacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "numfacturas", property = "numRecibos", jdbcType = JdbcType.INTEGER),
		@Result(column = "IDSUFIJO", property = "idSufijo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SUFIJO", property = "sufijo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "totalimporte", property = "importeTotal", jdbcType = JdbcType.VARCHAR),
		@Result(column = "propSEPA", property = "propSEPA", jdbcType = JdbcType.VARCHAR),
		@Result(column = "propOtros", property = "propOtros", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fcs", property = "fcs", jdbcType = JdbcType.BOOLEAN)
	})
	List<FicherosAbonosItem> getFicherosTransferencias(FicherosAbonosItem item, String idInstitucion, String idioma, Integer tamMaximo);

	@SelectProvider(type = FacDisqueteabonosExtendsSqlProvider.class, method = "getFacturasIncluidas")
	@Results({
			@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FORMAPAGO", property = "formaPago", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROFACTURAS", property = "numeroFacturas", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPORTETOTAL", property = "importeTotal", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PENDIENTETOTAL", property = "pendienteTotal", jdbcType = JdbcType.VARCHAR)
	})
	List<FacturasIncluidasItem> getFacturasIncluidas(String idFichero, String idInstitucion, String idIdioma);

	@SelectProvider(type = FacDisqueteabonosExtendsSqlProvider.class, method = "getNextIdDisqueteAbono")
	Long getNextIdDisqueteAbono(Short idInstitucion);
}
