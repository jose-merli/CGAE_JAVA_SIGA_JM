package org.itcgae.siga.db.services.cen.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.BancoBicItem;
import org.itcgae.siga.DTOs.cen.DatosBancariosAnexoItem;
import org.itcgae.siga.DTOs.cen.DatosBancariosItem;
import org.itcgae.siga.DTOs.cen.DatosBancariosSearchAnexosDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosSearchBancoDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosSearchDTO;
import org.itcgae.siga.DTOs.cen.MandatosItem;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.entities.CenCuentasbancarias;
import org.itcgae.siga.db.mappers.CenCuentasbancariasMapper;
import org.itcgae.siga.db.services.cen.providers.CenCuentasbancariasSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

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
		@Result(column = "IBANFORMATEADO", property = "ibanFormateado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "BIC", property = "bic", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAFIRMASERVICIOS", property = "fechaFirmaServicios", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAMODIFICACION", property = "fechaModificacion", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAFIRMAPRODUCTOS", property = "fechaFirmaProductos", jdbcType = JdbcType.DATE)
	})
	List<DatosBancariosItem> selectCuentasBancarias(DatosBancariosSearchDTO datosBancarios, Short idInstitucion);
	
	
	
	@SelectProvider(type = CenCuentasbancariasSqlExtendsProvider.class, method = "selectGeneralCuentasBancarias")
	@Results({
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCUENTA", property = "idCuenta", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TITULAR", property = "titular", jdbcType = JdbcType.VARCHAR),
		@Result(column = "USO", property = "uso", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IBAN", property = "iban", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IBANFORMATEADO", property = "ibanFormateado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "BIC", property = "bic", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CUENTACONTABLE", property = "cuentaContable", jdbcType = JdbcType.VARCHAR),
		@Result(column = "BANCO", property = "banco", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAMODIFICACION", property = "fechaModificacion", jdbcType = JdbcType.DATE),
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
		@Result(column = "IDMANDATOSERVICIO", property = "idMandatoServicio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "REFERENCIAPRODUCTO", property = "referenciaProducto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOIDPRODUCTO", property = "tipoIdProducto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDENTIFSERVICIO", property = "identifServicio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOIDSERVICIO", property = "tipoIdServicio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDENTIFPRODUCTO", property = "identifProducto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ESQUEMAPRODUCTO", property = "esquemaProducto", jdbcType = JdbcType.DATE),
		@Result(column = "IDMANDATOPRODUCTO", property = "idMandatoProducto", jdbcType = JdbcType.DATE),
	})
	List<MandatosItem> selectMandatos(DatosBancariosSearchDTO datosBancarios, String idInstitucion);
	
	@SelectProvider(type = CenCuentasbancariasSqlExtendsProvider.class, method = "selectNewIdCuenta")
	@Results({
		@Result(column = "IDCUENTA", property = "idCuenta", jdbcType = JdbcType.VARCHAR),
	})
	List<DatosBancariosItem> selectNewIdCuenta(String idPersona);

	@SelectProvider(type = CenCuentasbancariasSqlExtendsProvider.class, method = "getComboEsquemas")
	@Results({ 	@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
				@Result(column = "VALUE", property = "value", jdbcType = JdbcType.VARCHAR)
			})
	List<ComboItem> getComboEsquemas(String idlenguaje);


	@SelectProvider(type = CenCuentasbancariasSqlExtendsProvider.class, method = "selectBanks")
	@Results({
		@Result(column = "BANCO", property = "banco", jdbcType = JdbcType.VARCHAR),
		@Result(column = "BIC", property = "bic", jdbcType = JdbcType.VARCHAR)
	})
	List<BancoBicItem> selectBanks(DatosBancariosSearchBancoDTO datosBancariosSearchBancoDTO); 


	@SelectProvider(type = CenCuentasbancariasSqlExtendsProvider.class, method = "selectAnexos")
	@Results({
		@Result(column = "IDMANDATO", property = "idMandato", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDANEXO", property = "idAnexo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCUENTA", property = "idCuenta", jdbcType = JdbcType.VARCHAR),
        @Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOMANDATO", property = "tipoMandato", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAUSO", property = "fechaUso", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FIRMAFECHA", property = "firmaFecha", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FIRMALUGAR", property = "firmaLugar", jdbcType = JdbcType.DATE),
		@Result(column = "IDFICHEROFIRMA", property = "idFicheroFirma", jdbcType = JdbcType.VARCHAR),

	})
	List<DatosBancariosAnexoItem> selectAnexos(DatosBancariosSearchAnexosDTO datosBancariosAnexos);
	
	@SelectProvider(type = CenCuentasbancariasSqlExtendsProvider.class, method = "selectNewIdAnexo")
	@Results({
		@Result(column = "IDANEXO", property = "newId", jdbcType = JdbcType.VARCHAR),
	})
	List<NewIdDTO> selectNewIdAnexo(String idPersona,String idCuenta,String idMandato, String idInstitucion);
	
	@SelectProvider(type = CenCuentasbancariasSqlExtendsProvider.class, method = "selectMaxId")
	@Results({
		@Result(column = "IDCUENTA", property = "idMax", jdbcType = JdbcType.VARCHAR),
	})
	MaxIdDto selectMaxID(Long idPersona, Short idinstitucion);

	@SelectProvider(type = CenCuentasbancariasSqlExtendsProvider.class, method = "getComboCuentas")
	@Results({
			@Result(column = "NUMEROCUENTA", property = "label", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDCUENTA", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getComboCuentas(String idPersona, String idInstitucion);

	@SelectProvider(type = CenCuentasbancariasSqlExtendsProvider.class, method = "getCuentaActivaServiciosActivos")
	CenCuentasbancarias getCuentaActivaServiciosActivos(Short idInstitucion, Long idPersona);
}