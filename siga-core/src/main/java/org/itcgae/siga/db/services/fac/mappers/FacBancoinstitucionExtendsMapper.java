package org.itcgae.siga.db.services.fac.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.CuentasBancariasItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.FacBancoinstitucionMapper;
import org.itcgae.siga.db.services.fac.providers.FacBancoinstitucionSqlExtendsProvider;
import org.itcgae.siga.db.services.fac.providers.FacFacturaExtendsSqlProvider;
import org.itcgae.siga.db.services.fac.providers.FacSeriefacturacionExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface FacBancoinstitucionExtendsMapper extends FacBancoinstitucionMapper {
	
	@SelectProvider(type = FacBancoinstitucionSqlExtendsProvider.class, method = "getCuentasBancarias")
	@Results({
		@Result(column = "bancos_codigo", property = "bancosCodigo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "cod_banco", property = "codBanco", jdbcType = JdbcType.VARCHAR),
		@Result(column = "cod_sucursal", property = "codSucursal", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechabaja", property = "fechaBaja", jdbcType = JdbcType.DATE),
		@Result(column = "iban", property = "IBAN", jdbcType = JdbcType.VARCHAR),
		@Result(column = "descripcion", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombre", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "asientocontable", property = "asientoContable", jdbcType = JdbcType.VARCHAR),
		@Result(column = "cuentacontabletarjeta", property = "cuentaContableTarjeta", jdbcType = JdbcType.VARCHAR),
		@Result(column = "bic", property = "BIC", jdbcType = JdbcType.VARCHAR),
		@Result(column = "num_usos", property = "numUsos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "num_ficheros", property = "numFicheros", jdbcType = JdbcType.VARCHAR),
		@Result(column = "comisionimporte", property = "comisionImporte", jdbcType = JdbcType.VARCHAR),
		@Result(column = "comisiondescripcion", property = "comisionDescripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idtipoiva", property = "idTipoIVA", jdbcType = JdbcType.VARCHAR),
		@Result(column = "tipoiva", property = "tipoIVA", jdbcType = JdbcType.VARCHAR),
		@Result(column = "comisioncuentacontable", property = "comisionCuentaContable", jdbcType = JdbcType.VARCHAR),
		@Result(column = "configficherossecuencia", property = "configFicherosSecuencia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "configficherosesquema", property = "configFicherosEsquema", jdbcType = JdbcType.VARCHAR),
		@Result(column = "configlugaresquemasecuencia", property = "configLugaresQueMasSecuencia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "configconceptoampliado", property = "configConceptoAmpliado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idsufijosjcs", property = "idSufijoSjcs", jdbcType = JdbcType.VARCHAR),
		@Result(column = "sufijosjcs", property = "sufijoSjcs", jdbcType = JdbcType.VARCHAR),
		@Result(column = "concepto", property = "concepto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "sjcs", property = "sjcs", jdbcType = JdbcType.VARCHAR)})
	List<CuentasBancariasItem> getCuentasBancarias(String idCuenta, Short idInstitucion);

	@SelectProvider(type = FacBancoinstitucionSqlExtendsProvider.class, method = "getNextIdCuentaBancaria")
	@Results({ @Result(column = "bancos_codigo", property = "newId", jdbcType = JdbcType.VARCHAR) })
	NewIdDTO getNextIdCuentaBancaria(Short idInstitucion);

	@SelectProvider(type = FacBancoinstitucionSqlExtendsProvider.class, method = "getBancosCodigo")
	@Results({
			@Result(column = "BANCOS_CODIGO", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getBancosCodigo(String idInstitucion, String idFactura);

	@SelectProvider(type = FacBancoinstitucionSqlExtendsProvider.class, method = "getPorcentajeIva")
	@Results({
			@Result(column = "VALOR", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getPorcentajeIva(String idInstitucion, String bancoCodigo);
	
	@SelectProvider(type = FacBancoinstitucionSqlExtendsProvider.class, method = "comboPropTranferencia")
    @Results({@Result(column = "IBAN", property = "label", jdbcType = JdbcType.VARCHAR),
            @Result(column = "BANCOS_CODIGO", property = "value", jdbcType = JdbcType.VARCHAR)})
    List<ComboItem> comboCuentasBanc(Short idInstitucion);
	
}
