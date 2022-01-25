package org.itcgae.siga.db.services.fac.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.FacturasIncluidasItem;
import org.itcgae.siga.DTO.fac.FicherosDevolucionesItem;
import org.itcgae.siga.db.entities.FacFactura;
import org.itcgae.siga.db.entities.FacLineadevoludisqbanco;
import org.itcgae.siga.db.mappers.FacDisquetedevolucionesMapper;
import org.itcgae.siga.db.services.fac.providers.FacDisquetedevolucionesExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface FacDisquetedevolucionesExtendsMapper extends FacDisquetedevolucionesMapper {

	@SelectProvider(type = FacDisquetedevolucionesExtendsSqlProvider.class, method = "getFicherosDevoluciones")
	@Results({
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDDISQUETEDEVOLUCIONES", property = "idDisqueteDevoluciones", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CUENTA_ENTIDAD", property = "cuentaEntidad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAGENERACION", property = "fechaCreacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTETOTAL", property = "facturacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMEROFACTURAS", property = "numRecibos", jdbcType = JdbcType.INTEGER),
		@Result(column = "BANCOS_CODIGO", property = "bancosCodigo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SUFIJO", property = "sufijo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREFICHERO", property = "nombreFichero", jdbcType = JdbcType.VARCHAR)
	})
	List<FicherosDevolucionesItem> getFicherosDevoluciones(FicherosDevolucionesItem item, String idInstitucion);

	@SelectProvider(type = FacDisquetedevolucionesExtendsSqlProvider.class, method = "getFacturasIncluidas")
	@Results({
			@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FORMAPAGO", property = "formaPago", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROFACTURAS", property = "numeroFacturas", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPORTETOTAL", property = "importeTotal", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PENDIENTETOTAL", property = "pendienteTotal", jdbcType = JdbcType.VARCHAR)
	})
	List<FacturasIncluidasItem> getFacturasIncluidas(String idFichero, String idInstitucion, String idIdioma);

}
