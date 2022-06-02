package org.itcgae.siga.db.services.fac.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.FacturasIncluidasItem;
import org.itcgae.siga.DTO.fac.FicherosAdeudosItem;
import org.itcgae.siga.db.mappers.FacDisquetecargosMapper;
import org.itcgae.siga.db.services.fac.providers.FacDisquetecargosExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface FacDisquetecargosExtendsMapper extends FacDisquetecargosMapper {

	@SelectProvider(type = FacDisquetecargosExtendsSqlProvider.class, method = "getFicherosAdeudos")
	@Results({
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDDISQUETECARGOS", property = "idDisqueteCargos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREFICHERO", property = "nombreFichero", jdbcType = JdbcType.VARCHAR),
		@Result(column = "BANCOS_CODIGO", property = "bancosCodigo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CUENTA_ENTIDAD", property = "cuentaEntidad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IBAN", property = "iban", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHACREACION", property = "fechaCreacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDSERIEFACTURACION", property = "idseriefacturacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREABREVIADO", property = "nombreabreviado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPROGRAMACION", property = "idprogramacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHACARGO", property = "fechacargo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMEROLINEAS", property = "numerolineas", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDSUFIJO", property = "idSufijo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SUFIJO", property = "sufijo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TOTAL_REMESA", property = "totalRemesa", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMRECIBOS", property = "numRecibos", jdbcType = JdbcType.INTEGER),
		@Result(column = "FECHAPRESENTACION", property = "fechaPresentacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHARECIBOSPRIMEROS", property = "fechaRecibosPrimeros", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHARECIBOSRECURRENTES", property = "fechaRecibosRecurrentes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHARECIBOSCOR1", property = "fechaRecibosCOR", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHARECIBOSB2B", property = "fechaRecibosB2B", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAMODIFICACION", property = "fechaUltimaModificacion", jdbcType = JdbcType.VARCHAR)
	})
	List<FicherosAdeudosItem> getFicherosAdeudos(FicherosAdeudosItem item, String idInstitucion, Integer tamMaximo);

	@SelectProvider(type = FacDisquetecargosExtendsSqlProvider.class, method = "getFacturasIncluidas")
	@Results({
			@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FORMAPAGO", property = "formaPago", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROFACTURAS", property = "numeroFacturas", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPORTETOTAL", property = "importeTotal", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PENDIENTETOTAL", property = "pendienteTotal", jdbcType = JdbcType.VARCHAR)
	})
	List<FacturasIncluidasItem> getFacturasIncluidas(String idFichero, String idInstitucion, String idIdioma);
	
	@SelectProvider(type = FacDisquetecargosExtendsSqlProvider.class, method = "getRenegociacionFactura")
    String getRenegociacionFactura(String institucion, String factura);
}
