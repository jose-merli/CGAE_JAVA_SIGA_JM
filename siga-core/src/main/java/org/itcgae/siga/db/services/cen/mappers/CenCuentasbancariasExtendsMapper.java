package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.DatosBancariosItem;
import org.itcgae.siga.DTOs.cen.DatosBancariosSearchDTO;
import org.itcgae.siga.DTOs.cen.MandatosItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.CenCuentasbancariasMapper;
import org.itcgae.siga.db.mappers.CenGruposclienteClienteMapper;
import org.itcgae.siga.db.services.cen.providers.CenCuentasbancariasSqlExtendsProvider;
import org.itcgae.siga.db.services.cen.providers.CenGruposclienteClienteSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenCuentasbancariasExtendsMapper extends CenCuentasbancariasMapper{
	
	
	
	@SelectProvider(type = CenCuentasbancariasSqlExtendsProvider.class, method = "selectCuentasBancarias")
	@Results({
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCUENTA", property = "idCuenta", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TITULAR", property = "titular", jdbcType = JdbcType.VARCHAR),
		@Result(column = "USO", property = "uso", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IBAN", property = "iban", jdbcType = JdbcType.VARCHAR),
		@Result(column = "BIC", property = "bic", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAFIRMASERVICIOS", property = "fechaFirmaServicios", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAFIRMAPRODUCTOS", property = "fechaFirmaProductos", jdbcType = JdbcType.DATE)
	})
	List<DatosBancariosItem> selectCuentasBancarias(DatosBancariosSearchDTO datosBancarios, String idInstitucion);
	
	
	
	@SelectProvider(type = CenCuentasbancariasSqlExtendsProvider.class, method = "selectGeneralCuentasBancarias")
	@Results({
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCUENTA", property = "idCuenta", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TITULAR", property = "titular", jdbcType = JdbcType.VARCHAR),
		@Result(column = "USO", property = "uso", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IBAN", property = "iban", jdbcType = JdbcType.VARCHAR),
		@Result(column = "BIC", property = "bic", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CUENTACONTABLE", property = "cuentaContable", jdbcType = JdbcType.VARCHAR),
		@Result(column = "BANCO", property = "banco", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIFTITULAR", property = "nifTitular", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE)

	})
	List<DatosBancariosItem> selectGeneralCuentasBancarias(DatosBancariosSearchDTO datosBancarios, String idInstitucion);
	
	
	
	@SelectProvider(type = CenCuentasbancariasSqlExtendsProvider.class, method = "selectMandatos")
	@Results({
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCUENTA", property = "idCuenta", jdbcType = JdbcType.VARCHAR),
		@Result(column = "REFERENCIASERVICIO", property = "referenciaServicio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ESQUEMASERVICIO", property = "esquemaServicio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOPAGOSERVICIO", property = "tipoPagoServicio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "REFERENCIAPRODUCTO", property = "referenciaProducto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ESQUEMAPRODUCTO", property = "esquemaProducto", jdbcType = JdbcType.DATE),
		@Result(column = "TIPOPAGOPRODUCTO", property = "tipoPagoProducto", jdbcType = JdbcType.DATE),
	})
	List<MandatosItem> selectMandatos(DatosBancariosSearchDTO datosBancarios, String idInstitucion);
	
}