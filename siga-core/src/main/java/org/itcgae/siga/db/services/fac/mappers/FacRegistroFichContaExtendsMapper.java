package org.itcgae.siga.db.services.fac.mappers;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.AbonoContabilidadItem;
import org.itcgae.siga.DTO.fac.AltaAnticipoItem;
import org.itcgae.siga.DTO.fac.AnticiposPySItem;
import org.itcgae.siga.DTO.fac.DevolucionesItem;
import org.itcgae.siga.DTO.fac.FacRegistroFichConta;
import org.itcgae.siga.DTO.fac.FacturasContabilidadItem;
import org.itcgae.siga.DTO.fac.LiquidacionAnticipoColegioItem;
import org.itcgae.siga.DTO.fac.PagoPorBancoAbonoItem;
import org.itcgae.siga.DTO.fac.PagoPorBancoItem;
import org.itcgae.siga.DTO.fac.PagoPorCajaItem;
import org.itcgae.siga.DTO.fac.PagoPorTarjetaItem;
import org.itcgae.siga.db.entities.FacRegistrofichconta;
import org.itcgae.siga.db.mappers.FacRegistrofichcontaMapper;
import org.itcgae.siga.db.services.fac.providers.FacRegistroFichContaExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface FacRegistroFichContaExtendsMapper extends FacRegistrofichcontaMapper {
	
	@SelectProvider(type = FacRegistroFichContaExtendsProvider.class, method = "search")
	@Results({
		@Result(column = "IDCONTABILIDAD", property = "idContabilidad", jdbcType = JdbcType.NUMERIC),
		@Result(column = "FECHACREACION", property = "fechaCreacion", jdbcType = JdbcType.DATE),
		@Result(column = "FECHADESDE", property = "fechaExportacionDesde", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAHASTA", property = "fechaExportacionHasta", jdbcType = JdbcType.DATE),
		@Result(column = "NOMBREFICHERO", property = "nombreFichero", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMEROASIENTOS", property = "numAsientos", jdbcType = JdbcType.NUMERIC),
		@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NOMBREESTADO", property = "nombreEstado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE)
	})
	List<FacRegistroFichConta> search(FacRegistroFichConta facRegistroFichConta, Short idInstitucion, Integer tamMaximo,String idLenguaje);
	
	@SelectProvider(type = FacRegistroFichContaExtendsProvider.class, method = "getMaxIdFacRegistroFichConta")
	@Results({
		@Result(column = "IDCONTABILIDAD", property = "idContabilidad", jdbcType = JdbcType.VARCHAR)
	})
	FacRegistroFichConta getMaxIdFacRegistroFichConta(Short idInstitucion);
	
	@SelectProvider(type = FacRegistroFichContaExtendsProvider.class, method = "obtenerFacturas")
	@Results({
		@Result(column = "IDFACTURA", property = "idfactura", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMEROFACTURA", property = "numerofactura", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPNETO", property = "impneto", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IMPIVA", property = "impiva", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IVA", property = "iva", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.NUMERIC),
		@Result(column = "FECHAEMISION", property = "fechaemision", jdbcType = JdbcType.DATE),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CTAPRODUCTOSERVICIO", property = "ctaproductosservicio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CTAIVA", property = "ctaiva", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CONFDEUDOR", property = "confdeudor", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CONFINGRESOS", property = "confingresos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CTAINGRESOS", property = "ctaingresos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CTACLIENTES", property = "ctaclientes", jdbcType = JdbcType.VARCHAR)
	})
	List<FacturasContabilidadItem> obtenerFacturas(FacRegistrofichconta facRegistroFichConta);
	
	@SelectProvider(type = FacRegistroFichContaExtendsProvider.class, method = "obtenerAbonos")
	@Results({
		@Result(column = "idabono", property = "idabono", jdbcType = JdbcType.NUMERIC),
		@Result(column = "numeroabono", property = "numeroabono", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idpersona", property = "idpersona", jdbcType = JdbcType.NUMERIC),
		@Result(column = "fecha", property = "fecha", jdbcType = JdbcType.DATE),
		@Result(column = "impneto", property = "impneto", jdbcType = JdbcType.NUMERIC),
		@Result(column = "impiva", property = "impiva", jdbcType = JdbcType.NUMERIC),
		@Result(column = "iva", property = "iva", jdbcType = JdbcType.NUMERIC),
		@Result(column = "descripcion", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ctaproductoservicio", property = "ctaproductoservicio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ctaiva", property = "ctaiva", jdbcType = JdbcType.VARCHAR),
		@Result(column = "numerofactura", property = "numerofactura", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idpersona_1", property = "idpersona_1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "devuelta", property = "devuelta", jdbcType = JdbcType.VARCHAR),
		@Result(column = "confdeudor", property = "confdeudor", jdbcType = JdbcType.VARCHAR),
		@Result(column = "confingresos", property = "confingresos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ctaingresos", property = "ctaingresos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ctaclientes", property = "ctaclientes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "imptotalpagadoporbanco", property = "imptotalpagadoporbanco", jdbcType = JdbcType.NUMERIC),
		@Result(column = "imptotalpagadoporcaja", property = "imptotalpagadoporcaja", jdbcType = JdbcType.NUMERIC),
		@Result(column = "bancos_codigo", property = "bancos_codigo", jdbcType = JdbcType.VARCHAR)
	})
	List<AbonoContabilidadItem> obtenerAbonos(FacRegistrofichconta facRegistroFichConta);
	
	@SelectProvider(type = FacRegistroFichContaExtendsProvider.class, method = "obtenerPagosPorCaja")
	@Results({
		@Result(column = "anticipo", property = "anticipo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idfactura", property = "idfactura", jdbcType = JdbcType.NUMERIC),
		@Result(column = "numerofactura", property = "numerofactura", jdbcType = JdbcType.VARCHAR),
		@Result(column = "confdeufor", property = "confdeufor", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ctaclientes", property = "ctaclientes", jdbcType = JdbcType.NUMERIC),
		@Result(column = "importe", property = "importe", jdbcType = JdbcType.NUMERIC),
		@Result(column = "tipoapunte", property = "tipoapunte", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idpersona", property = "idpersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fecha", property = "fecha", jdbcType = JdbcType.DATE)
	})
	List<PagoPorCajaItem> obtenerPagosPorCaja(FacRegistrofichconta facRegistroFichConta);
	
	@SelectProvider(type = FacRegistroFichContaExtendsProvider.class, method = "obtenerPagosPorBanco")
	@Results({
		@Result(column = "idfactura", property = "idfactura", jdbcType = JdbcType.NUMERIC),
		@Result(column = "numerofactura", property = "numerofactura", jdbcType = JdbcType.VARCHAR),
		@Result(column = "importe", property = "importe", jdbcType = JdbcType.NUMERIC),
		@Result(column = "bancos_codigo", property = "bancos_codigo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idpersona", property = "idpersona", jdbcType = JdbcType.NUMERIC),
		@Result(column = "fechacreacion", property = "fechacreacion", jdbcType = JdbcType.DATE),
		@Result(column = "confdeudor", property = "confdeudor", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ctaclientes", property = "ctaclientes", jdbcType = JdbcType.VARCHAR)
	})
	List<PagoPorBancoItem> obtenerPagosPorBanco(FacRegistrofichconta facRegistroFichConta);
	
	@SelectProvider(type = FacRegistroFichContaExtendsProvider.class, method = "obtenerPagosPorTarjeta")
	@Results({
		@Result(column = "idfactura", property = "idfactura", jdbcType = JdbcType.NUMERIC),
		@Result(column = "numerofactura", property = "numerofactura", jdbcType = JdbcType.VARCHAR),
		@Result(column = "tarjeta", property = "tarjeta", jdbcType = JdbcType.VARCHAR),
		@Result(column = "confdeudor", property = "confdeudor", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ctaclientes", property = "ctaclientes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "importe", property = "importe", jdbcType = JdbcType.NUMERIC),
		@Result(column = "idpersona", property = "idpersona", jdbcType = JdbcType.NUMERIC),
		@Result(column = "fecha", property = "fecha", jdbcType = JdbcType.DATE)
	})
	List<PagoPorTarjetaItem> obtenerPagosPorTarjeta(FacRegistrofichconta facRegistroFichConta);
	
	@SelectProvider(type = FacRegistroFichContaExtendsProvider.class, method = "obtenerDevoluciones")
	@Results({
		@Result(column = "idfactura", property = "idfactura", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idpersona", property = "idpersona", jdbcType = JdbcType.NUMERIC),
		@Result(column = "importe", property = "importe", jdbcType = JdbcType.NUMERIC),
		@Result(column = "iddisquetedevoluciones", property = "iddisquetedevoluciones", jdbcType = JdbcType.NUMERIC),
		@Result(column = "gastosdevolucion", property = "gastosdevolucion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "cargarcliente", property = "cargarcliente", jdbcType = JdbcType.VARCHAR),
		@Result(column = "bancos_codigo", property = "bancos_codigo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idpersona_1", property = "idpersona_1", jdbcType = JdbcType.NUMERIC),
		@Result(column = "fechageneracion", property = "fechageneracion", jdbcType = JdbcType.DATE),
		@Result(column = "numerofactura", property = "numerofactura", jdbcType = JdbcType.VARCHAR),
		@Result(column = "confdeudor", property = "confdeudor", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ctaclientes", property = "ctaclientes", jdbcType = JdbcType.NUMERIC)
	})
	List<DevolucionesItem> obtenerDevoluciones(FacRegistrofichconta facRegistroFichConta);
	
	@SelectProvider(type = FacRegistroFichContaExtendsProvider.class, method = "obtenerAltasAnticipos")
	@Results({
		@Result(column = "idanticipo", property = "idanticipo", jdbcType = JdbcType.NUMERIC),
		@Result(column = "descripcion", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "importeinicial", property = "importeinicial", jdbcType = JdbcType.NUMERIC),
		@Result(column = "idpersona", property = "idpersona", jdbcType = JdbcType.NUMERIC),
		@Result(column = "fecha", property = "fecha", jdbcType = JdbcType.DATE)
	})
	List<AltaAnticipoItem> obtenerAltasAnticipos(FacRegistrofichconta facRegistroFichConta);
	
	@SelectProvider(type = FacRegistroFichContaExtendsProvider.class, method = "obtenerLiquidacionesAnticiposColegios")
	@Results({
		@Result(column = "idanticipo", property = "idanticipo", jdbcType = JdbcType.NUMERIC),
		@Result(column = "importeanticipado", property = "importeanticipado", jdbcType = JdbcType.NUMERIC),
		@Result(column = "idpersona", property = "idpersona", jdbcType = JdbcType.NUMERIC),
		@Result(column = "fechaefectiva", property = "fechaefectiva", jdbcType = JdbcType.DATE)
	})
	List<LiquidacionAnticipoColegioItem> obtenerLiquidacionesAnticiposColegios(FacRegistrofichconta facRegistroFichConta);
	
	@SelectProvider(type = FacRegistroFichContaExtendsProvider.class, method = "obtenerAnticiposPyS")
	@Results({
		@Result(column = "anticipo", property = "anticipo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idfactura", property = "idfactura", jdbcType = JdbcType.NUMERIC),
		@Result(column = "numerofactura", property = "numerofactura", jdbcType = JdbcType.VARCHAR),
		@Result(column = "tarjeta", property = "tarjeta", jdbcType = JdbcType.VARCHAR),
		@Result(column = "confdeudor", property = "confdeudor", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ctaclientes", property = "ctaclientes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "importe", property = "importe", jdbcType = JdbcType.NUMERIC),
		@Result(column = "idpersona", property = "idpersona", jdbcType = JdbcType.NUMERIC),
		@Result(column = "fecha", property = "fecha", jdbcType = JdbcType.DATE),
		@Result(column = "numerolinea", property = "numerolinea", jdbcType = JdbcType.NUMERIC)
	})
	List<AnticiposPySItem> obtenerAnticiposPyS(FacRegistrofichconta facRegistroFichConta);
	
	@SelectProvider(type = FacRegistroFichContaExtendsProvider.class, method = "obtenerPagosPorBancoAbono")
	@Results({
		@Result(column = "idabono", property = "idabono", jdbcType = JdbcType.NUMERIC),
		@Result(column = "numeroabono", property = "numeroabono", jdbcType = JdbcType.VARCHAR),
		@Result(column = "iddisqueteabono", property = "iddisqueteabono", jdbcType = JdbcType.NUMERIC),
		@Result(column = "importe", property = "importe", jdbcType = JdbcType.NUMERIC),
		@Result(column = "bancos_codigo", property = "bancos_codigo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idpersona", property = "idpersona", jdbcType = JdbcType.NUMERIC),
		@Result(column = "fecha", property = "fecha", jdbcType = JdbcType.DATE),
		@Result(column = "estado", property = "estado", jdbcType = JdbcType.NUMERIC),
		@Result(column = "numerofactura", property = "numerofactura", jdbcType = JdbcType.VARCHAR),
		@Result(column = "confingresos", property = "confingresos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ctaingresos", property = "ctaingresos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "confdeudor", property = "confdeudor", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ctaclientes", property = "ctaclientes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ctaproductoservicio", property = "ctaproductoservicio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idfactura", property = "idfactura", jdbcType = JdbcType.VARCHAR)
	})
	List<PagoPorBancoAbonoItem> obtenerPagosPorBancoAbono(FacRegistrofichconta facRegistroFichConta);
	
}
