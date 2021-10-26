package org.itcgae.siga.db.services.fac.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.SerieFacturacionItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.FacSeriefacturacionMapper;
import org.itcgae.siga.db.services.fac.providers.FacSeriefacturacionExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface FacSeriefacturacionExtendsMapper extends FacSeriefacturacionMapper {

	@SelectProvider(type = FacSeriefacturacionExtendsSqlProvider.class, method = "getSeriesFacturacion")
	@Results({
		@Result(column = "idseriefacturacion", property = "idSerieFacturacion", jdbcType = JdbcType.INTEGER),
		@Result(column = "bancos_codigo", property = "idCuentaBancaria", jdbcType = JdbcType.VARCHAR),
		@Result(column = "iban", property = "cuentaBancaria", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombreabreviado", property = "abreviatura", jdbcType = JdbcType.VARCHAR),
		@Result(column = "descripcion", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "observaciones", property = "observaciones", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechabaja", property = "fechaBaja", jdbcType = JdbcType.DATE),
		@Result(column = "idsufijo", property = "idSufijo", jdbcType = JdbcType.INTEGER),
		@Result(column = "sufijo", property = "sufijo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idformapago", property = "idFormaPago", jdbcType = JdbcType.VARCHAR),
		@Result(column = "formapago", property = "formaPago", jdbcType = JdbcType.VARCHAR),
		@Result(column = "generarpdf", property = "generarPDF", jdbcType = JdbcType.BOOLEAN),
		@Result(column = "enviofacturas", property = "envioFacturas", jdbcType = JdbcType.BOOLEAN),
		@Result(column = "traspasofacturas", property = "traspasoFacturas", jdbcType = JdbcType.BOOLEAN),
		@Result(column = "idSerieFacturacionPrevia", property = "idSerieFacturacionPrevia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "serieGenerica", property = "serieGenerica", jdbcType = JdbcType.BOOLEAN)
	})
	List<SerieFacturacionItem> getSeriesFacturacion(SerieFacturacionItem serieFacturacionItem, Short idInstitucion, String idioma);

	@SelectProvider(type = FacSeriefacturacionExtendsSqlProvider.class, method = "getUsoSufijo")
	int getUsoSufijo(int idInstitucion, String codigoBanco);

}
