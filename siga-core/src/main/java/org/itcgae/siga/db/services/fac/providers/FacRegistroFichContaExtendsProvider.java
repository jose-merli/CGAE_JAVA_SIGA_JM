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
		sql.SELECT(" P.CONFDEUDOR");
		
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
}
