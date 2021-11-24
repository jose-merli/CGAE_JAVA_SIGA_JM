package org.itcgae.siga.db.services.fac.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
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
		@Result(column = "IDDISQUETEABONO", property = "idDisqueteAbonos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREFICHERO", property = "nombreFichero", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CUENTA_ENTIDAD", property = "cuentaEntidad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "cod_banco", property = "bancosCodigo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHA", property = "fechaCreacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "totalimporte", property = "facturacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "numfacturas", property = "numRecibos", jdbcType = JdbcType.INTEGER),
		@Result(column = "FECHAMODIFICACION", property = "fechaUltimaModificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDSUFIJO", property = "idSufijo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SUFIJO", property = "sufijo", jdbcType = JdbcType.VARCHAR)
	})
	List<FicherosAbonosItem> getFicherosTransferencias(FicherosAbonosItem item, String idInstitucion);
}
