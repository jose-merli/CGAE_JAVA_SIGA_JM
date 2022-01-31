package org.itcgae.siga.db.services.fac.providers;


import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.FacRegistroFichConta;
import org.itcgae.siga.db.entities.FacRegistrofichconta;
import org.itcgae.siga.db.mappers.FacRegistrofichcontaSqlProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class FacRegistroFichContaExtendsProvider extends FacRegistrofichcontaSqlProvider{

	private Logger LOGGER = Logger.getLogger(this.getClass());
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public String search(FacRegistroFichConta facRegistroFichConta, Short idInstitucion, Integer tamMaximo,String idLenguaje ) {

		SQL sql = new SQL();
		
		sql.SELECT("F.IDCONTABILIDAD");
		sql.SELECT("F.FECHACREACION");
		sql.SELECT("F.FECHADESDE");
		sql.SELECT("F.FECHAHASTA");
		sql.SELECT("F.NUMEROASIENTOS");
		sql.SELECT("F.NOMBREFICHERO");
		sql.SELECT("F.ESTADO");
		sql.SELECT("GEN.DESCRIPCION as NOMBREESTADO");
		sql.SELECT("F.FECHABAJA");
		
		sql.FROM("FAC_REGISTROFICHCONTA F");
		sql.LEFT_OUTER_JOIN("FAC_ESTADOCONTAB E ON (F.ESTADO = E.IDESTADO)");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS GEN ON (E.DESCRIPCION = GEN.IDRECURSO  AND GEN.IDLENGUAJE = "+ idLenguaje +")");
		
		if(facRegistroFichConta.getIdContabilidadDesde() != 0 && String.valueOf(facRegistroFichConta.getIdContabilidadDesde()) != null && !String.valueOf(facRegistroFichConta.getIdContabilidadDesde()).isEmpty()) {
			sql.WHERE("F.IDCONTABILIDAD >= " + facRegistroFichConta.getIdContabilidadDesde());
		}
	
		if(facRegistroFichConta.getIdContabilidadHasta() != 0 && String.valueOf(facRegistroFichConta.getIdContabilidadHasta()) != null && !String.valueOf(facRegistroFichConta.getIdContabilidadHasta()).isEmpty()) {
			sql.WHERE("F.IDCONTABILIDAD <= " + facRegistroFichConta.getIdContabilidadHasta());
		}

		if(facRegistroFichConta.getFechaCreacionDesde() != null) {
			String fechaCreacionDesde = dateFormat.format(facRegistroFichConta.getFechaCreacionDesde());
			sql.WHERE("TRUNC(F.FECHACREACION) >= TO_DATE('" + fechaCreacionDesde + "', 'DD/MM/RRRR')");
		}
		
		if(facRegistroFichConta.getFechaCreacionHasta() != null) {
			String fechaCreacionHasta = dateFormat.format(facRegistroFichConta.getFechaCreacionHasta());
			sql.WHERE("TRUNC(F.FECHACREACION) <= TO_DATE('" + fechaCreacionHasta + "', 'DD/MM/RRRR')");
		}
		
		if(facRegistroFichConta.getNumAsientosDesde() != 0 && String.valueOf(facRegistroFichConta.getNumAsientosDesde()) != null && !String.valueOf(facRegistroFichConta.getNumAsientosDesde()).isEmpty()) {
			sql.WHERE("F.NUMEROASIENTOS >= " + facRegistroFichConta.getNumAsientosDesde());
		}
	
		if(facRegistroFichConta.getNumAsientosHasta() != 0 && String.valueOf(facRegistroFichConta.getNumAsientosHasta()) != null && !String.valueOf(facRegistroFichConta.getNumAsientosHasta()).isEmpty()) {
			sql.WHERE("F.NUMEROASIENTOS <= " + facRegistroFichConta.getNumAsientosHasta());
		}
		
		if(facRegistroFichConta.getFechaExportacionDesde() != null) {
			String fechaExportacionDesde = dateFormat.format(facRegistroFichConta.getFechaExportacionDesde());
			sql.WHERE("TRUNC(F.FECHADESDE) >= TO_DATE('" + fechaExportacionDesde + "', 'DD/MM/RRRR')");
		}
		
		if(facRegistroFichConta.getFechaExportacionHasta() != null) {
			String fechaExportacionHasta = dateFormat.format(facRegistroFichConta.getFechaExportacionHasta());
			sql.WHERE("TRUNC(F.FECHAHASTA) <= TO_DATE('" + fechaExportacionHasta + "', 'DD/MM/RRRR')");
		}
		
		sql.WHERE("F.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("ROWNUM <= " + tamMaximo);
		
		if(!facRegistroFichConta.isHistorico()) {
			sql.WHERE("F.FECHABAJA IS NULL");
		}
		
		sql.ORDER_BY("F.IDCONTABILIDAD DESC");
		
		LOGGER.info(sql.toString());
		return sql.toString();
	}
	
	public String getMaxIdFacRegistroFichConta(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("NVL((MAX(IDCONTABILIDAD)+1),1) as IDCONTABILIDAD");
		sql.FROM("FAC_REGISTROFICHCONTA");
		sql.WHERE("IDINSTITUCION = "+ idInstitucion);
		LOGGER.info(sql.toString());
		return sql.toString();
		
	}
	
	public String obtenerFacturas(FacRegistrofichconta facRegistroFichConta) {
		SQL sql = new SQL();
		
		sql.SELECT(" SELECT TO_NUMBER(F.IDFACTURA) IDFACTURA");
		sql.SELECT(" F.NUMEROFACTURA");
		sql.SELECT(" L.CANTIDAD * L.PRECIOUNITARIO AS IMPNETO");
		sql.SELECT(" ROUND(L.CANTIDAD * L.PRECIOUNITARIO * L.IVA / 100, 2) AS IMPIVA");
		sql.SELECT(" L.IVA");
		sql.SELECT(" DECODE(F.IDPERSONADEUDOR, NULL, F.IDPERSONA, F.IDPERSONADEUDOR) AS IDPERSONA");
		sql.SELECT(" F.FECHAEMISION");
		sql.SELECT(" L.DESCRIPCION");
		sql.SELECT(" L.CTAPRODUCTOSERVICIO");
		sql.SELECT(" L.CTAIVA");
		sql.SELECT(" P.CONFDEUDOR");
		sql.SELECT(" P.CONFINGRESOS");
		sql.SELECT(" P.CTAINGRESOS");
		sql.SELECT(" P.CTACLIENTES");
		
		sql.FROM(" FAC_FACTURA F");
		sql.FROM(" FAC_LINEAFACTURA L");
		sql.FROM(" FAC_FACTURACIONPROGRAMADA P");
		
		sql.WHERE(" F.IDINSTITUCION = L.IDINSTITUCION");
		sql.WHERE(" F.IDFACTURA = L.IDFACTURA");
		sql.WHERE(" F.IDINSTITUCION = P.IDINSTITUCION");
		sql.WHERE(" F.IDSERIEFACTURACION = P.IDSERIEFACTURACION");
		sql.WHERE(" F.IDPROGRAMACION = P.IDPROGRAMACION");
		sql.WHERE(" F.NUMEROFACTURA IS NOT NULL");
		sql.WHERE(" F.IDINSTITUCION = " + facRegistroFichConta.getIdinstitucion());
		
		if(facRegistroFichConta.getFechadesde() != null) {
			String fechaExportacionDesde = dateFormat.format(facRegistroFichConta.getFechadesde());
			sql.WHERE("TRUNC(F.FECHAEMISION) >= TO_DATE('" + fechaExportacionDesde + "', 'DD/MM/RRRR')");
		}
		
		if(facRegistroFichConta.getFechahasta() != null) {
			String fechaExportacionHasta = dateFormat.format(facRegistroFichConta.getFechahasta());
			sql.WHERE("TRUNC(F.FECHAEMISION) <= TO_DATE('" + fechaExportacionHasta + "', 'DD/MM/RRRR')");
		}
		
		sql.ORDER_BY("1");

		LOGGER.info(sql.toString());
		return sql.toString();
		
	}
	
	public String obtenerAbonos(FacRegistrofichconta facRegistroFichConta) {
		SQL sql = new SQL();
		
		sql.SELECT(" to_number(a.idabono) idabono");
		sql.SELECT(" a.numeroabono");
		sql.SELECT(" a.idpersona");
		sql.SELECT(" a.fecha");
		sql.SELECT(" -1 * la.cantidad * la.preciounitario AS impneto");
		sql.SELECT(" -1 * round(la.cantidad * la.preciounitario * la.iva / 100, 2) AS impiva");
		sql.SELECT(" la.iva");
		sql.SELECT(" la.descripcionlinea AS descripcion");
		sql.SELECT(" l.ctaproductoservicio");
		sql.SELECT(" l.ctaiva");
		sql.SELECT(" f.numerofactura");
		sql.SELECT(" DECODE(f.idpersonadeudor, NULL, f.idpersona, f.idpersonadeudor) AS idpersona");
		sql.SELECT(" d.devuelta");
		sql.SELECT(" p.confdeudor");
		sql.SELECT(" p.confingresos");
		sql.SELECT(" p.ctaingresos");
		sql.SELECT(" p.ctaclientes");
		sql.SELECT(" f.imptotalpagadoporbanco");
		sql.SELECT(" f.imptotalpagadoporcaja");
		sql.SELECT(" (SELECT bancos_codigo FROM fac_disquetecargos WHERE idinstitucion = d.idinstitucion AND iddisquetecargos = d.iddisquetecargos) AS bancos_codigo");
		
		sql.FROM(" fac_abono a");
		sql.FROM(" fac_factura f");
		sql.FROM(" fac_lineaabono la");
		sql.FROM(" fac_lineafactura l");
		sql.FROM(" fac_facturacionprogramada p");
		sql.FROM(" fac_facturaincluidaendisquete d");
		
		sql.WHERE(" a.idinstitucion = f.idinstitucion");
		sql.WHERE(" a.idfactura = f.idfactura");
		sql.WHERE(" a.idinstitucion = la.idinstitucion");
		sql.WHERE(" a.idabono = la.idabono");
		sql.WHERE(" la.idinstitucion = l.idinstitucion (+)");
		
		sql.WHERE(" la.idfactura = l.idfactura (+)");
		sql.WHERE(" la.lineafactura = l.numerolinea (+)");
		sql.WHERE(" f.idinstitucion = p.idinstitucion");
		sql.WHERE(" f.idseriefacturacion = p.idseriefacturacion");
		sql.WHERE(" f.idprogramacion = p.idprogramacion (+)");
		sql.WHERE(" f.idfactura = d.idfactura (+)");
		sql.WHERE(" f.idinstitucion = d.idinstitucion (+)");
		sql.WHERE(" a.idpagosjg IS NULL");
		sql.WHERE(" a.IDINSTITUCION = " + facRegistroFichConta.getIdinstitucion());
		
		if(facRegistroFichConta.getFechadesde() != null) {
			String fechaExportacionDesde = dateFormat.format(facRegistroFichConta.getFechadesde());
			sql.WHERE("TRUNC(a.FECHA) >= TO_DATE('" + fechaExportacionDesde + "', 'DD/MM/RRRR')");
		}
		
		if(facRegistroFichConta.getFechahasta() != null) {
			String fechaExportacionHasta = dateFormat.format(facRegistroFichConta.getFechahasta());
			sql.WHERE("TRUNC(a.FECHA) <= TO_DATE('" + fechaExportacionHasta + "', 'DD/MM/RRRR')");
		}
		
		sql.ORDER_BY("1");

		LOGGER.info(sql.toString());
		return sql.toString();
		
	}
	
	public String obtenerPagosPorCaja(FacRegistrofichconta facRegistroFichConta) {
		SQL sql = new SQL();
		
		sql.SELECT(" '0' AS anticipo");
		sql.SELECT(" to_number(a.idfactura) idfactura");
		sql.SELECT(" b.numerofactura");
		sql.SELECT(" a.tarjeta tarjeta");
		sql.SELECT(" p.confdeudor");
		sql.SELECT(" p.ctaclientes");
		sql.SELECT(" a.importe importe");
		sql.SELECT(" a.tipoapunte tipoapunte");
		sql.SELECT(" DECODE(b.idpersonadeudor, NULL,b.idpersona, b.idpersonadeudor) idpersona");
		sql.SELECT(" a.fecha fecha");
		
		sql.FROM(" fac_pagosporcaja a");
		sql.FROM(" fac_factura b");
		sql.FROM(" fac_facturacionprogramada p");    
		
		sql.WHERE(" b.idinstitucion = a.idinstitucion");
		sql.WHERE(" b.idfactura = a.idfactura");
		sql.WHERE(" b.idinstitucion = p.idinstitucion");
		sql.WHERE(" b.idseriefacturacion = p.idseriefacturacion");
		sql.WHERE(" b.idprogramacion = p.idprogramacion");
		sql.WHERE(" b.numerofactura IS NOT NULL");
		sql.WHERE(" a.idabono IS NULL");
		sql.WHERE(" a.idinstitucion = " + facRegistroFichConta.getIdinstitucion());
		sql.WHERE(" a.tarjeta = 'N'");
		
		if(facRegistroFichConta.getFechadesde() != null) {
			String fechaExportacionDesde = dateFormat.format(facRegistroFichConta.getFechadesde());
			sql.WHERE("TRUNC(a.FECHA) >= TO_DATE('" + fechaExportacionDesde + "', 'DD/MM/RRRR')");
		}
		
		if(facRegistroFichConta.getFechahasta() != null) {
			String fechaExportacionHasta = dateFormat.format(facRegistroFichConta.getFechahasta());
			sql.WHERE("TRUNC(a.FECHA) <= TO_DATE('" + fechaExportacionHasta + "', 'DD/MM/RRRR')");
		}
		
		sql.ORDER_BY("2");

		LOGGER.info(sql.toString());
		return sql.toString();
	}
	
	public String obtenerPagosPorBanco(FacRegistrofichconta facRegistroFichConta) {
		SQL sql = new SQL();
		
		sql.SELECT(" to_number(a.idfactura) idfactura");
		sql.SELECT(" c.numerofactura");
		sql.SELECT(" a.importe importe");
		sql.SELECT(" b.bancos_codigo bancos_codigo");
		sql.SELECT(" DECODE(c.idpersonadeudor, NULL, c.idpersona, c.idpersonadeudor) idpersona");
		sql.SELECT(" b.fechacreacion fechacreacion");
		sql.SELECT(" p.confdeudor");
		sql.SELECT(" p.ctaclientes");
		
		sql.FROM(" fac_facturaincluidaendisquete a");
		sql.FROM(" fac_disquetecargos b");
		sql.FROM(" fac_factura c");
		sql.FROM(" fac_facturacionprogramada p");   
		
		sql.WHERE(" c.idinstitucion = p.idinstitucion");
		sql.WHERE(" c.idseriefacturacion = p.idseriefacturacion");
		sql.WHERE(" c.idprogramacion = p.idprogramacion");
		sql.WHERE(" a.idinstitucion = b.idinstitucion");
		sql.WHERE(" a.iddisquetecargos = b.iddisquetecargos");
		sql.WHERE(" a.idfactura = c.idfactura");
		sql.WHERE(" a.idinstitucion = c.idinstitucion");
		sql.WHERE(" c.numerofactura IS NOT NULL");
		sql.WHERE(" a.idinstitucion = " + facRegistroFichConta.getIdinstitucion());
		
		if(facRegistroFichConta.getFechadesde() != null) {
			String fechaExportacionDesde = dateFormat.format(facRegistroFichConta.getFechadesde());
			sql.WHERE("TRUNC(b.FECHACREACION) >= TO_DATE('" + fechaExportacionDesde + "', 'DD/MM/RRRR')");
		}
		
		if(facRegistroFichConta.getFechahasta() != null) {
			String fechaExportacionHasta = dateFormat.format(facRegistroFichConta.getFechahasta());
			sql.WHERE("TRUNC(b.FECHACREACION) <= TO_DATE('" + fechaExportacionHasta + "', 'DD/MM/RRRR')");
		}
		
		sql.ORDER_BY("1");

		LOGGER.info(sql.toString());
		return sql.toString();
	}
	
	public String obtenerPagosPorTarjeta(FacRegistrofichconta facRegistroFichConta) {
		SQL sql = new SQL();
		
		sql.SELECT(" to_number(a.idfactura) idfactura");
		sql.SELECT(" b.numerofactura");
		sql.SELECT(" a.tarjeta tarjeta");
		sql.SELECT(" p.confdeudor");
		sql.SELECT(" p.ctaclientes");
		sql.SELECT(" a.importe importe");
		sql.SELECT(" DECODE(b.idpersonadeudor, NULL b.idpersona, b.idpersonadeudor) idpersona");
		sql.SELECT(" a.fecha fecha");
				
		sql.FROM(" fac_pagosporcaja a");
		sql.FROM(" fac_factura b");
		sql.FROM(" fac_facturacionprogramada p");
				
		sql.WHERE(" b.idinstitucion = a.idinstitucion");
		sql.WHERE(" b.idfactura = a.idfactura");
		sql.WHERE(" b.idinstitucion = p.idinstitucion");
		sql.WHERE(" b.idseriefacturacion = p.idseriefacturacion");
		sql.WHERE(" b.idprogramacion = p.idprogramacion");
		sql.WHERE(" b.numerofactura IS NOT NULL");
		sql.WHERE(" a.idinstitucion = " + facRegistroFichConta.getIdinstitucion());
		sql.WHERE(" a.tarjeta = 'S'");
	
		if(facRegistroFichConta.getFechadesde() != null) {
			String fechaExportacionDesde = dateFormat.format(facRegistroFichConta.getFechadesde());
			sql.WHERE("TRUNC(fecha) >= TO_DATE('" + fechaExportacionDesde + "', 'DD/MM/RRRR')");
		}
		
		if(facRegistroFichConta.getFechahasta() != null) {
			String fechaExportacionHasta = dateFormat.format(facRegistroFichConta.getFechahasta());
			sql.WHERE("TRUNC(fecha) <= TO_DATE('" + fechaExportacionHasta + "', 'DD/MM/RRRR')");
		}
		
		sql.ORDER_BY("1");

		LOGGER.info(sql.toString());
		return sql.toString();
	}
	
	public String obtenerDevoluciones(FacRegistrofichconta facRegistroFichConta) {
		SQL sql = new SQL();
		
		sql.SELECT(" a.idfactura idfactura");
		sql.SELECT(" DECODE(d.idpersonadeudor, NULL, d.idpersona, d.idpersonadeudor) idpersona");
		sql.SELECT(" a.importe importe");
		sql.SELECT(" b.iddisquetedevoluciones iddisquetedevoluciones");
		sql.SELECT(" b.gastosdevolucion gastosdevolucion");
		sql.SELECT(" b.cargarcliente cargarcliente");
		sql.SELECT(" c.bancos_codigo bancos_codigo");
		sql.SELECT(" d.idpersona idpersona");
		sql.SELECT(" c.fechageneracion fechageneracion");
		sql.SELECT(" d.numerofactura");
		sql.SELECT(" p.confdeudor");
		sql.SELECT(" p.ctaclientes");
				
		sql.FROM(" fac_facturaincluidaendisquete a");
		sql.FROM(" fac_lineadevoludisqbanco b");
		sql.FROM(" fac_disquetedevoluciones c");
		sql.FROM(" fac_factura d");
		sql.FROM(" fac_facturacionprogramada p");
			
		sql.WHERE(" d.idinstitucion = p.idinstitucion");
		sql.WHERE(" d.idseriefacturacion = p.idseriefacturacion");
		sql.WHERE(" d.idprogramacion = p.idprogramacion");
		sql.WHERE(" a.idinstitucion = b.idinstitucion");
		sql.WHERE(" a.iddisquetecargos = b.iddisquetecargos");
		sql.WHERE(" a.idfacturaincluidaendisquete = b.idfacturaincluidaendisquete");
		sql.WHERE(" a.idinstitucion = c.idinstitucion");
		sql.WHERE(" b.iddisquetedevoluciones = c.iddisquetedevoluciones");
		sql.WHERE(" a.idfactura = d.idfactura");
		sql.WHERE(" a.idinstitucion = d.idinstitucion");
		sql.WHERE(" d.numerofactura IS NOT NULL");
		sql.WHERE(" a.idinstitucion = " + facRegistroFichConta.getIdinstitucion());
	
		if(facRegistroFichConta.getFechadesde() != null) {
			String fechaExportacionDesde = dateFormat.format(facRegistroFichConta.getFechadesde());
			sql.WHERE("TRUNC(c.fechageneracion) >= TO_DATE('" + fechaExportacionDesde + "', 'DD/MM/RRRR')");
		}
		
		if(facRegistroFichConta.getFechahasta() != null) {
			String fechaExportacionHasta = dateFormat.format(facRegistroFichConta.getFechahasta());
			sql.WHERE("TRUNC(c.fechageneracion) <= TO_DATE('" + fechaExportacionHasta + "', 'DD/MM/RRRR')");
		}
		
		sql.ORDER_BY(" c.iddisquetedevoluciones");

		LOGGER.info(sql.toString());
		return sql.toString();
	}
	
	public String obtenerAltasAnticipos(FacRegistrofichconta facRegistroFichConta) {
		SQL sql = new SQL();
		
		sql.SELECT(" idanticipo");
		sql.SELECT(" descripcion");
		sql.SELECT(" importeinicial");
		sql.SELECT(" idpersona");
		sql.SELECT(" fecha");
		sql.SELECT(" ctacontable");
				
		sql.FROM(" pys_anticipoletrado");
			
		sql.WHERE(" idinstitucion = " + facRegistroFichConta.getIdinstitucion());
	
		if(facRegistroFichConta.getFechadesde() != null) {
			String fechaExportacionDesde = dateFormat.format(facRegistroFichConta.getFechadesde());
			sql.WHERE("TRUNC(fecha) >= TO_DATE('" + fechaExportacionDesde + "', 'DD/MM/RRRR')");
		}
		
		if(facRegistroFichConta.getFechahasta() != null) {
			String fechaExportacionHasta = dateFormat.format(facRegistroFichConta.getFechahasta());
			sql.WHERE("TRUNC(fecha) <= TO_DATE('" + fechaExportacionHasta + "', 'DD/MM/RRRR')");
		}
		
		sql.ORDER_BY(" idanticipo");

		LOGGER.info(sql.toString());
		return sql.toString();
	}
	
	public String obtenerLiquidacionesAnticiposColegios(FacRegistrofichconta facRegistroFichConta) {
		SQL sql = new SQL();
		
		sql.SELECT(" idanticipo");
		sql.SELECT(" importeanticipado");
		sql.SELECT(" idpersona");
		sql.SELECT(" fechaefectiva");

		sql.FROM(" pys_lineaanticipo");
			
		sql.WHERE(" liquidacion = '1'");
		sql.WHERE(" idinstitucion = " + facRegistroFichConta.getIdinstitucion());
	
		if(facRegistroFichConta.getFechadesde() != null) {
			String fechaExportacionDesde = dateFormat.format(facRegistroFichConta.getFechadesde());
			sql.WHERE("TRUNC(fechaefectiva) >= TO_DATE('" + fechaExportacionDesde + "', 'DD/MM/RRRR')");
		}
		
		if(facRegistroFichConta.getFechahasta() != null) {
			String fechaExportacionHasta = dateFormat.format(facRegistroFichConta.getFechahasta());
			sql.WHERE("TRUNC(fechaefectiva) <= TO_DATE('" + fechaExportacionHasta + "', 'DD/MM/RRRR')");
		}
		
		sql.ORDER_BY(" idanticipo");

		LOGGER.info(sql.toString());
		return sql.toString();
	}
	
	public String obtenerAnticiposPyS(FacRegistrofichconta facRegistroFichConta) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();
		String sqlFinal = "";
		
		//INICIO ANTICIPADO PRODUCTOS
		sql.SELECT(" '1' AS anticipo");
		sql.SELECT(" to_number(b.idfactura) idfactura");
		sql.SELECT(" b.numerofactura");
		sql.SELECT(" 'S' tarjeta");
		sql.SELECT(" p.confdeudor");
		sql.SELECT(" p.ctaclientes");
		sql.SELECT(" l.importeanticipado importe");
		sql.SELECT(" DECODE(b.idpersonadeudor, NULL, b.idpersona, b.idpersonadeudor) idpersona");
		sql.SELECT(" b.fechaemision fecha");
		sql.SELECT(" l.numerolinea numerolinea");

		sql.FROM(" fac_factura b");
		sql.FROM(" fac_facturacionprogramada p");
		sql.FROM(" fac_lineafactura l");
		sql.FROM(" pys_compra c");
			
		sql.WHERE(" b.idinstitucion = p.idinstitucion");
		sql.WHERE(" b.idseriefacturacion = p.idseriefacturacion");
		sql.WHERE(" b.idprogramacion = p.idprogramacion");
		sql.WHERE(" b.idinstitucion = l.idinstitucion");
		sql.WHERE(" b.idfactura = l.idfactura");
		sql.WHERE(" l.idinstitucion = c.idinstitucion");
		sql.WHERE(" l.idfactura = c.idfactura");
		sql.WHERE(" l.numerolinea = c.numerolinea");
		sql.WHERE(" b.idinstitucion = " + facRegistroFichConta.getIdinstitucion());
		sql.WHERE(" l.importeanticipado <> 0");
		sql.WHERE(" b.numerofactura IS NOT NULL");
		
		if(facRegistroFichConta.getFechadesde() != null) {
			String fechaExportacionDesde = dateFormat.format(facRegistroFichConta.getFechadesde());
			sql.WHERE("TRUNC(b.fechaemision) >= TO_DATE('" + fechaExportacionDesde + "', 'DD/MM/RRRR')");
		}
		
		if(facRegistroFichConta.getFechahasta() != null) {
			String fechaExportacionHasta = dateFormat.format(facRegistroFichConta.getFechahasta());
			sql.WHERE("TRUNC(b.fechaemision) <= TO_DATE('" + fechaExportacionHasta + "', 'DD/MM/RRRR')");
		}
		
		//FIN ANTICIPADO PRODUCTOS
		
		//INICIO ANTICIPADO SERVICIOS
		sql2.SELECT(" '1' AS anticipo");
		sql2.SELECT(" to_number(b.idfactura) idfactura");
		sql2.SELECT(" b.numerofactura");
		sql2.SELECT(" 'S' tarjeta");
		sql2.SELECT(" p.confdeudor");
		sql2.SELECT(" p.ctaclientes");
		sql2.SELECT(" l.importeanticipado importe");
		sql2.SELECT("  DECODE(b.idpersonadeudor, NULL, b.idpersona, b.idpersonadeudor) idpersona");
		sql2.SELECT(" b.fechaemision fecha");
		sql2.SELECT(" l.numerolinea numerolinea");
		
		sql2.FROM(" fac_factura b");
		sql2.FROM(" fac_facturacionprogramada p");
		sql2.FROM(" fac_lineafactura l");
		sql2.FROM(" fac_facturacionsuscripcion s");
		sql2.FROM(" pys_suscripcion sus");
		
		sql2.WHERE(" b.idinstitucion = p.idinstitucion");
		sql2.WHERE(" b.idseriefacturacion = p.idseriefacturacion");
		sql2.WHERE(" b.idprogramacion = p.idprogramacion");
		sql2.WHERE(" b.idinstitucion = l.idinstitucion");
		sql2.WHERE(" b.idfactura = l.idfactura");
		sql2.WHERE(" l.idinstitucion = s.idinstitucion");
		sql2.WHERE(" l.idfactura = s.idfactura");
		sql2.WHERE(" l.numerolinea = s.numerolinea");
		sql2.WHERE(" s.idinstitucion = sus.idinstitucion");
		sql2.WHERE(" s.idsuscripcion = sus.idsuscripcion");
		sql2.WHERE(" b.idinstitucion = " + facRegistroFichConta.getIdinstitucion());
		sql2.WHERE(" l.importeanticipado <> 0");
		sql2.WHERE(" b.numerofactura IS NOT NULL");
				        
		if(facRegistroFichConta.getFechadesde() != null) {
			String fechaExportacionDesde = dateFormat.format(facRegistroFichConta.getFechadesde());
			sql.WHERE("TRUNC(b.fechaemision) >= TO_DATE('" + fechaExportacionDesde + "', 'DD/MM/RRRR')");
		}
		
		if(facRegistroFichConta.getFechahasta() != null) {
			String fechaExportacionHasta = dateFormat.format(facRegistroFichConta.getFechahasta());
			sql.WHERE("TRUNC(b.fechaemision) <= TO_DATE('" + fechaExportacionHasta + "', 'DD/MM/RRRR')");
		}
		//FIN ANTICIPADO SERVICIOS
		
		sqlFinal = "SELECT * FROM ("+ sql.toString() + " UNION " + sql2.toString() + ") ORDER BY 2";

		LOGGER.info(sql.toString());
		return sqlFinal;
	}
}
