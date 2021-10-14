package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.SerieFacturacionItem;
import org.itcgae.siga.db.mappers.FacSeriefacturacionSqlProvider;

public class FacSeriefacturacionExtendsSqlProvider extends FacSeriefacturacionSqlProvider {
	
	public String getSeriesFacturacion(SerieFacturacionItem serieFacturacionItem, Short idInstitucion) {
		SQL sql = new SQL();
		
		// Select
		sql.SELECT("sf.idinstitucion");
		sql.SELECT("sf.idseriefacturacion");
		sql.SELECT("bi.iban");
		sql.SELECT("sf.nombreabreviado");
		sql.SELECT("sf.descripcion");
		sql.SELECT("sf.observaciones");
		sql.SELECT("sf.fechabaja");
		sql.SELECT("s.idsufijo");
		sql.SELECT("(s.sufijo || ' - ' || s.concepto ) sufijo");
		sql.SELECT("s.idsufijo");
		sql.SELECT("fp.idformapago");
		sql.SELECT("( SELECT f_siga_getrecurso(descripcion,1)\r\n"
				+ "		FROM PYS_FORMAPAGO \r\n"
				+ "		WHERE idformapago=fP.idformapago\r\n"
				+ "	) formapago");
		sql.SELECT("sf.generarpdf");
		sql.SELECT("sf.enviofacturas");
		sql.SELECT("sf.traspasofacturas");
		
		// From
		sql.FROM("fac_seriefacturacion sf");
		
		// Joins
		sql.INNER_JOIN("fac_seriefacturacion_banco sfb ON (sfb.idseriefacturacion = sf.idseriefacturacion AND sfb.idinstitucion = sf.idinstitucion)");
		sql.INNER_JOIN("fac_bancoinstitucion bi ON (bi.bancos_codigo = sfb.bancos_codigo AND bi.idinstitucion = sf.idinstitucion)");
		sql.LEFT_OUTER_JOIN("fac_formapagoserie fp ON (fp.idinstitucion=sf.idinstitucion AND fp.idseriefacturacion=sf.idseriefacturacion)");
		sql.LEFT_OUTER_JOIN("fac_sufijo s ON (s.idsufijo = sfb.idsufijo AND s.idinstitucion = sf.idinstitucion)");
		
		// Where obligatorio
		sql.WHERE("sf.idinstitucion = " + idInstitucion);
		
		// Where opcionales
		if (serieFacturacionItem.getAbreviatura() != null && serieFacturacionItem.getAbreviatura() != "")
			sql.WHERE("upper(sf.nombreabreviado) LIKE upper('%" + serieFacturacionItem.getAbreviatura() + "%')");
		if (serieFacturacionItem.getDescripcion() != null && serieFacturacionItem.getDescripcion() != "")
			sql.WHERE("upper(sf.descripcion) LIKE upper('%" + serieFacturacionItem.getDescripcion() + "%')");
		if (serieFacturacionItem.getCuentaBancaria() != null && serieFacturacionItem.getCuentaBancaria() != "")
			sql.WHERE("bi.bancos_codigo = " + serieFacturacionItem.getCuentaBancaria());
		
		if (serieFacturacionItem.getSufijo() != null && serieFacturacionItem.getSufijo() != "")
			sql.WHERE("s.idsufijo = " + serieFacturacionItem.getSufijo());
		
		if (serieFacturacionItem.getTiposProductos() != null && !serieFacturacionItem.getTiposProductos().isEmpty())
			sql.WHERE("sf.idseriefacturacion IN ( "
					+ "SELECT DISTINCT f.idseriefacturacion "
						+ "FROM fac_factura f "
						+ "INNER JOIN pys_compra c ON ( "
							+ "c.idfactura = f.idfactura "
							+ "AND c.idinstitucion = f.idinstitucion "
							+ "AND c.idtipoproducto IN (" + String.join(", ", serieFacturacionItem.getTiposProductos()) + ")))");
		
		if (serieFacturacionItem.getTiposServicios() != null && !serieFacturacionItem.getTiposServicios().isEmpty())
			sql.WHERE("sf.idseriefacturacion IN ( "
					+ "SELECT DISTINCT f.idseriefacturacion "
						+ "FROM fac_factura f "
						+ "INNER JOIN fac_facturacionsuscripcion fs ON ( "
							+ "fs.idfactura = f.idfactura "
							+ "AND fs.idinstitucion = f.idinstitucion "
							+ "AND fs.idtiposervicios IN (" + String.join(", ", serieFacturacionItem.getTiposServicios()) + ")))");
		
		if (serieFacturacionItem.getEtiquetas() != null && !serieFacturacionItem.getEtiquetas().isEmpty())
			sql.WHERE("sf.idseriefacturacion IN ( "
					+ "SELECT DISTINCT ti.idseriefacturacion "
						+ "FROM fac_tipocliincluidoenseriefac ti "
						+ "WHERE ti.idinstitucion = sf.idinstitucion "
							+ "AND ti.idgrupo IN (" + String.join(", ", serieFacturacionItem.getEtiquetas()) + "))");
		
		if (serieFacturacionItem.getConsultasDestinatarios() != null && !serieFacturacionItem.getConsultasDestinatarios().isEmpty())
			sql.WHERE("sf.idseriefacturacion IN ( "
						+ "SELECT ti.idseriefacturacion "
							+ "FROM fac_tipocliincluidoenseriefac ti "
							+ "WHERE ti.idinstitucion = sf.idinstitucion "
								+ "AND ti.idgrupo IN ( "
									+ "SELECT gcc.idgrupo "
										+ "FROM cen_gruposcliente_cliente gcc "
										+ "WHERE gcc.idinstitucion = sf.idinstitucion "
											+ "AND idpersona IN (" + String.join(", ", serieFacturacionItem.getConsultasDestinatarios()) + ")))");
		
		if (serieFacturacionItem.getContadorFacturas() != null && serieFacturacionItem.getContadorFacturas() != "")
			sql.WHERE("sf.idcontador = " + serieFacturacionItem.getContadorFacturas());
		if (serieFacturacionItem.getContadorFacturasRectificativas() != null && serieFacturacionItem.getContadorFacturasRectificativas() != "")
			sql.WHERE("sf.idcontador = " + serieFacturacionItem.getContadorFacturasRectificativas());
		
		// Order by
		sql.ORDER_BY("sf.idseriefacturacion");
		
		return sql.toString();
	}
}
