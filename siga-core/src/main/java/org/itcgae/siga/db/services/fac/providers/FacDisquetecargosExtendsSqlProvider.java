package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.FicherosAdeudosItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.FacDisquetecargosSqlProvider;

import java.text.SimpleDateFormat;


public class FacDisquetecargosExtendsSqlProvider extends FacDisquetecargosSqlProvider {
	
	public String getFicherosAdeudos(FicherosAdeudosItem item, String idInstitucion) {
		SQL principal = new SQL();
		SQL totalRemesa = new SQL();
		SQL numRecibos = new SQL();
		SQL sql = new SQL();
		
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fecha;
		
		//sumatorio de remesas
		totalRemesa.SELECT("SUM (fi.importe)");
		totalRemesa.FROM("fac_facturaincluidaendisquete fi");
		totalRemesa.WHERE("fi.idinstitucion = c.idinstitucion AND fi.iddisquetecargos = c.iddisquetecargos");
		
		//sumatorio numero de recibos
		numRecibos.SELECT("COUNT (1)");
		numRecibos.FROM("fac_facturaincluidaendisquete fi");
		numRecibos.WHERE("fi.idinstitucion = c.idinstitucion AND fi.iddisquetecargos = c.iddisquetecargos");
		
		//query principal
		principal.SELECT("c.idinstitucion, c.iddisquetecargos, c.nombrefichero, c.bancos_codigo, b.DESCRIPCION CUENTA_ENTIDAD, b.iban, "
				+ "c.fechacreacion, c.idseriefacturacion, nvl(sf.nombreabreviado,'<FACTURAS SUELTAS>') nombreabreviado,c .idprogramacion, nvl(p.descripcion,'<FACTURAS SUELTAS>') descripcion, "
				+ "c.fechacargo, c.numerolineas, c.idsufijo,( sufijo || ' - ' || concepto ) sufijo, ("+totalRemesa.toString()+") total_remesa, ("+numRecibos.toString()+") AS NUMRECIBOS, "
				+ "c.fechapresentacion, c. fecharecibosprimeros, c.fecharecibosrecurrentes, c.fechareciboscor1, c.fecharecibosb2b, c.fechamodificacion");
		
		principal.FROM("fac_disquetecargos c");
		principal.INNER_JOIN("fac_bancoinstitucion b ON (c.idinstitucion=b.idinstitucion AND c.bancos_codigo=b.bancos_codigo)");
		principal.LEFT_OUTER_JOIN("fac_seriefacturacion sf ON (sf.idinstitucion=c.idinstitucion AND sf.idseriefacturacion=c.idseriefacturacion)");
		principal.LEFT_OUTER_JOIN("fac_facturacionprogramada p ON (p.idinstitucion=c.idinstitucion AND p.idseriefacturacion=c.idseriefacturacion "
				+ "AND p.idprogramacion=c.idprogramacion)");
		principal.LEFT_OUTER_JOIN("fac_sufijo s ON (s.idinstitucion=c.idinstitucion AND s.idsufijo=c.idsufijo)");
		
		principal.WHERE("c.idinstitucion="+idInstitucion);
		
		//CUENTA BANCARIA
		if(item.getBancosCodigo()!=null) {
			principal.WHERE("b.bancos_codigo="+item.getBancosCodigo());
		}
		
		//sufijo
		if(item.getIdSufijo()!=null) {
			principal.WHERE("c.idSufijo="+item.getIdSufijo());
		}
		
		//fecha creacion desde 
		if(item.getFechaCreacionDesde()!=null) {
			fecha = dateFormat.format(item.getFechaCreacionDesde());
			principal.WHERE("c.fechacreacion >= TO_DATE('"+fecha+"', 'DD/MM/YYYY')");
		}
		
		//fecha creacion hasta 
		if(item.getFechaCreacionHasta()!=null) {
			fecha = dateFormat.format(item.getFechaCreacionHasta());
			principal.WHERE("c.fechacreacion <= TO_DATE('"+fecha+"', 'DD/MM/YYYY')");
		}
		
		//serie
		if(item.getIdseriefacturacion()!=null) {
			principal.WHERE("c.idseriefacturacion = "+item.getIdseriefacturacion());
		}

		//programacion
		if(!UtilidadesString.esCadenaVacia(item.getIdprogramacion())) {
			principal.WHERE("c.idprogramacion = "+item.getIdprogramacion());
		}
		
		//facturacion
		if(item.getFacturacion()!=null && !item.getFacturacion().trim().isEmpty()) {
			principal.WHERE("UPPER(p.descripcion) LIKE UPPER('%"+item.getFacturacion().trim()+"%')");
		}

		// IdDisqueteCargos
		if (!UtilidadesString.esCadenaVacia(item.getIdDisqueteCargos())) {
			principal.WHERE("c.iddisquetecargos = " + item.getIdDisqueteCargos());
		}

		// Origen --> 0: Facturación de serie; 1: Facturas sueltas
		if (!UtilidadesString.esCadenaVacia(item.getOrigen())) {
			if (item.getOrigen().equals("0")) {
				principal.WHERE("c.idseriefacturacion IS NOT NULL");
			} else if (item.getOrigen().equals("1")) {
				principal.WHERE("c.idseriefacturacion IS NULL");
			}
		}
	
		principal.ORDER_BY("c.iddisquetecargos DESC");
		
		//query completa
		sql.SELECT("*");
		sql.FROM("("+principal.toString()+")");
		sql.WHERE("ROWNUM < 201");
		
		//importe total desde
		if(item.getImporteTotalDesde()!=null && !item.getImporteTotalDesde().isEmpty()) {
			sql.WHERE("total_remesa>=to_number("+item.getImporteTotalDesde()+",'99999999999999999.99')");
		}
		
		//importe total hasta
		if(item.getImporteTotalHasta()!=null && !item.getImporteTotalHasta().isEmpty()) {
			sql.WHERE("total_remesa<=to_number("+item.getImporteTotalHasta()+",'99999999999999999.99')");
		}
		
		//numrecibos desde
		if(item.getNumRecibosDesde()!=null && !item.getNumRecibosDesde().isEmpty()) {
			sql.WHERE("numRecibos >= "+item.getNumRecibosDesde());
		}
		
		//numrecibos hasta
		if(item.getNumRecibosHasta()!=null && !item.getNumRecibosHasta().isEmpty()) {
			sql.WHERE("numRecibos <= "+item.getNumRecibosHasta());
		}
				
		return sql.toString();
	}

	public String getFacturasIncluidas(String idFichero, String idInstitucion, String idIdioma) {

		SQL sql = new SQL();

		sql.SELECT("gr.DESCRIPCION ESTADO, F_SIGA_GETRECURSO(pf.DESCRIPCION,"+idIdioma+") FORMAPAGO, "
				+ "COUNT(*) NUMEROFACTURAS, SUM(ff.IMPTOTAL) IMPORTETOTAL, SUM(ff.IMPTOTALPORPAGAR) PENDIENTETOTAL");
		sql.FROM("FAC_FACTURA ff");
		sql.INNER_JOIN("FAC_FACTURAINCLUIDAENDISQUETE ff2 ON (ff.IDINSTITUCION = ff2.IDINSTITUCION AND ff.IDFACTURA = ff2.IDFACTURA)");
		sql.LEFT_OUTER_JOIN("FAC_ESTADOFACTURA fe ON (ff.ESTADO = fe.IDESTADO)");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS gr ON (gr.IDLENGUAJE = "+idIdioma+" AND fe.DESCRIPCION = gr.IDRECURSO)");
		sql.LEFT_OUTER_JOIN("PYS_FORMAPAGO pf ON (ff.IDFORMAPAGO = pf.IDFORMAPAGO)");
		sql.WHERE("ff.IDINSTITUCION = "+idInstitucion);
		sql.WHERE("ff2.IDDISQUETECARGOS ="+idFichero);
		sql.GROUP_BY("gr.DESCRIPCION, pf.DESCRIPCION");

		return sql.toString();
	}
	
	public String getRenegociacionFactura(String institucion, String factura) {

        SQL sql = new SQL();
        sql.SELECT("FAC_FACTURAINCLUIDAENDISQUETE.IDRENEGOCIACION");
        sql.FROM("FAC_DISQUETECARGOS");
        sql.JOIN("FAC_FACTURAINCLUIDAENDISQUETE ON FAC_FACTURAINCLUIDAENDISQUETE.IDINSTITUCION = FAC_DISQUETECARGOS.IDINSTITUCION" +
                " AND FAC_FACTURAINCLUIDAENDISQUETE.IDDISQUETECARGOS = FAC_DISQUETECARGOS.IDDISQUETECARGOS");
        sql.WHERE("FAC_FACTURAINCLUIDAENDISQUETE.IDINSTITUCION  = " + institucion);
        sql.WHERE("FAC_FACTURAINCLUIDAENDISQUETE.IDFACTURA = " + factura);
        SQL subQuery = new SQL();
        subQuery.SELECT("MAX(DISQ.FECHACREACION)");
        subQuery.FROM("FAC_DISQUETECARGOS DISQ");
        subQuery.WHERE("FAC_FACTURAINCLUIDAENDISQUETE.IDINSTITUCION = DISQ.IDINSTITUCION");
        subQuery.WHERE("FAC_FACTURAINCLUIDAENDISQUETE.IDDISQUETECARGOS = DISQ.IDDISQUETECARGOS");
        subQuery.WHERE("FAC_FACTURAINCLUIDAENDISQUETE.IDINSTITUCION = " + institucion);
        subQuery.WHERE("FAC_FACTURAINCLUIDAENDISQUETE.IDFACTURA = " + factura);
        sql.WHERE("FAC_DISQUETECARGOS.FECHACREACION IN (" + subQuery.toString() + ")");

        return sql.toString();
    }
}