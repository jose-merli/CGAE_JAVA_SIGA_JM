package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.SerieFacturacionItem;
import org.itcgae.siga.db.mappers.FacSeriefacturacionSqlProvider;

import java.util.stream.Collectors;

public class FacSeriefacturacionExtendsSqlProvider extends FacSeriefacturacionSqlProvider {
	
	public String getSeriesFacturacion(SerieFacturacionItem serieFacturacionItem, Short idInstitucion, String idioma) {
		SQL sql = new SQL();
		
		// Select
		sql.SELECT("sf.idinstitucion");
		sql.SELECT("sf.idseriefacturacion");
		sql.SELECT("bi.bancos_codigo");
		sql.SELECT("bi.iban");
		sql.SELECT("sf.nombreabreviado");
		sql.SELECT("sf.descripcion");
		sql.SELECT("sf.observaciones");
		sql.SELECT("sf.fechabaja");
		sql.SELECT("s.idsufijo");
		sql.SELECT("(s.sufijo || ' - ' || s.concepto ) sufijo");
		sql.SELECT("s.idsufijo");
		sql.SELECT("fp.idformapago");
		sql.SELECT("( SELECT f_siga_getrecurso(descripcion,'" + idioma + "') "
				+ "		FROM PYS_FORMAPAGO "
				+ "		WHERE idformapago=fP.idformapago "
				+ "	) formapago");
		sql.SELECT("sf.generarpdf");
		sql.SELECT("sf.idmodelofactura");
		sql.SELECT("sf.idmodelorectificativa");
		sql.SELECT("sf.enviofacturas");
		sql.SELECT("sf.idtipoplantillamail");
		sql.SELECT("sf.traspasofacturas");
		sql.SELECT("sf.traspaso_plantilla");
		sql.SELECT("sf.traspaso_codauditoria_def");
		sql.SELECT("sf.confdeudor");
		sql.SELECT("sf.ctaclientes");
		sql.SELECT("sf.confingresos");
		sql.SELECT("sf.ctaingresos");
		sql.SELECT("sf.idseriefacturacionprevia");
		sql.SELECT("(CASE WHEN sf.tiposerie = 'G' then 1 else 0 end) serieGenerica");
		sql.SELECT("sf.idcontador");
		sql.SELECT("sf.idcontador_abonos");

		// From
		sql.FROM("fac_seriefacturacion sf");
		
		// Joins
		sql.INNER_JOIN("fac_seriefacturacion_banco sfb ON (sfb.idseriefacturacion = sf.idseriefacturacion AND sfb.idinstitucion = sf.idinstitucion)");
		sql.INNER_JOIN("fac_bancoinstitucion bi ON (bi.bancos_codigo = sfb.bancos_codigo AND bi.idinstitucion = sf.idinstitucion)");
		sql.LEFT_OUTER_JOIN("fac_formapagoserie fp ON (fp.idinstitucion=sf.idinstitucion AND fp.idseriefacturacion=sf.idseriefacturacion)");
		sql.LEFT_OUTER_JOIN("fac_sufijo s ON (s.idsufijo = sfb.idsufijo AND s.idinstitucion = sf.idinstitucion)");
		
		// Where obligatorio
		sql.WHERE("sf.idinstitucion = '" + idInstitucion + "'");
		
		// Where opcionales
		if (serieFacturacionItem.getAbreviatura() != null && serieFacturacionItem.getAbreviatura().trim() != "")
			sql.WHERE("upper(sf.nombreabreviado) LIKE upper('%" + serieFacturacionItem.getAbreviatura().trim() + "%')");
		if (serieFacturacionItem.getDescripcion() != null && serieFacturacionItem.getDescripcion().trim() != "")
			sql.WHERE("upper(sf.descripcion) LIKE upper('%" + serieFacturacionItem.getDescripcion().trim() + "%')");
		if (serieFacturacionItem.getIdCuentaBancaria() != null && serieFacturacionItem.getIdCuentaBancaria() != "")
			sql.WHERE("bi.bancos_codigo = '" + serieFacturacionItem.getIdCuentaBancaria() + "'");
		
		if (serieFacturacionItem.getIdSufijo() != null && serieFacturacionItem.getSufijo() != "")
			sql.WHERE("s.idsufijo = '" + serieFacturacionItem.getIdSufijo() + "'");
		
		if (serieFacturacionItem.getIdTiposProductos() != null && !serieFacturacionItem.getIdTiposProductos().isEmpty())
			sql.WHERE("sf.idseriefacturacion IN ( "
					+ "SELECT DISTINCT tpf.idseriefacturacion "
						+ "FROM fac_tiposproduincluenfactu tpf "
						+ "INNER JOIN pys_tiposproductos tp ON ( tpf.idtipoproducto = tp.idtipoproducto ) "
						+ "WHERE tpf.idinstitucion = sf.idinstitucion "
							+ "AND tpf.idtipoproducto IN (" + String.join(", ", serieFacturacionItem.getIdTiposProductos()) + "))");
		
		if (serieFacturacionItem.getIdTiposServicios() != null && !serieFacturacionItem.getIdTiposServicios().isEmpty())
			sql.WHERE("sf.idseriefacturacion IN ( "
					+ "SELECT DISTINCT tsf.idseriefacturacion "
						+ "FROM fac_tiposservinclsenfact tsf "
						+ "INNER JOIN pys_tiposervicios ts ON ( tsf.idtiposervicios = ts.idtiposervicios ) "
						+ "WHERE tsf.idinstitucion = sf.idinstitucion "
							+ "AND ts.idtiposervicios IN (" + String.join(", ", serieFacturacionItem.getIdTiposServicios()) + "))");
		
		if (serieFacturacionItem.getIdEtiquetas() != null && !serieFacturacionItem.getIdEtiquetas().isEmpty())
			sql.WHERE("sf.idseriefacturacion IN ( "
					+ "SELECT DISTINCT ti.idseriefacturacion "
						+ "FROM fac_tipocliincluidoenseriefac ti "
						+ "WHERE ti.idinstitucion = sf.idinstitucion "
							+ "AND ti.idgrupo IN (" + String.join(", ", serieFacturacionItem.getIdEtiquetas()) + "))");
		
		if (serieFacturacionItem.getIdConsultasDestinatarios() != null && !serieFacturacionItem.getIdConsultasDestinatarios().isEmpty())
			sql.WHERE("sf.idseriefacturacion IN ( "
						+ "SELECT ti.idseriefacturacion "
							+ "FROM fac_tipocliincluidoenseriefac ti "
							+ "WHERE ti.idinstitucion = sf.idinstitucion "
								+ "AND ti.idgrupo IN ( "
									+ "SELECT gcc.idgrupo "
										+ "FROM cen_gruposcliente_cliente gcc "
										+ "WHERE gcc.idinstitucion = sf.idinstitucion "
											+ "AND idpersona IN (" + String.join(", ", serieFacturacionItem.getIdConsultasDestinatarios()) + ")))");
		
		if (serieFacturacionItem.getIdContadorFacturas() != null && serieFacturacionItem.getIdContadorFacturas() != "")
			sql.WHERE("sf.idcontador = '" + serieFacturacionItem.getIdContadorFacturas() + "'");
		if (serieFacturacionItem.getIdContadorFacturasRectificativas() != null && serieFacturacionItem.getIdContadorFacturasRectificativas() != "")
			sql.WHERE("sf.idcontador_abonos = '" + serieFacturacionItem.getIdContadorFacturasRectificativas() + "'");

		// Para la vista de detalles de una serie de facturación
		if (serieFacturacionItem.getIdSerieFacturacion() != null && serieFacturacionItem.getIdSerieFacturacion() != "")
			sql.WHERE("sf.idseriefacturacion = " + serieFacturacionItem.getIdSerieFacturacion());

		// Order by
		sql.ORDER_BY("sf.idseriefacturacion");
		
		return sql.toString();
	}

	public String getNextIdSerieFacturacion(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("(NVL(MAX(sf.idseriefacturacion),0) + 1) as idseriefacturacion");
		sql.FROM("fac_seriefacturacion sf");
		sql.WHERE("sf.idinstitucion = " + idInstitucion);

		return sql.toString();
	}

	public String getConsultaUsoSufijo(int idInstitucion, String codigoBanco) {

		String principal;
		SQL pagos = new SQL();
		SQL series = new SQL();

		// Consulta de los pagos
		pagos.SELECT("'PAGOS SJCS' tipo");
		pagos.SELECT("sf.idseriefacturacion");
		pagos.SELECT("sf.nombreabreviado abreviatura");
		pagos.SELECT("sf.descripcion");
		pagos.SELECT("s.idsufijo");
		pagos.SELECT("s.sufijo || ' - ' || s.concepto sufijo ");

		// Subsonsulta 1 de los pagos
		pagos.SELECT("( SELECT COUNT(1) " +
				"FROM fcs_pagosjg p " +
				"WHERE p.idinstitucion = sfb.idinstitucion " +
					"AND p.bancos_codigo = sfb.bancos_codigo " +
					"AND p.idpagosjg IN ( " +
						"SELECT DISTINCT a.idpagosjg " +
						"FROM fac_abono a " +
						"WHERE a.idinstitucion = sf.idinstitucion " +
							"AND a.estado NOT IN ( " +
								"SELECT idestado " +
								"FROM fac_estadoabono " +
								"WHERE UPPER(descripcion) LIKE '%PAGADO%') " +
				")) num_pendientes"
		);

		pagos.FROM("fac_seriefacturacion sf");
		pagos.INNER_JOIN("fac_seriefacturacion_banco sfb ON (sf.idinstitucion = sfb.idinstitucion " +
				"AND sf.idseriefacturacion = sfb.idseriefacturacion)");
		pagos.LEFT_OUTER_JOIN("fac_sufijo s ON (sfb.idinstitucion = s.idinstitucion " +
				"AND sfb.idsufijo = s.idsufijo)");
		pagos.WHERE("sf.idinstitucion = " + idInstitucion + " AND sfb.bancos_codigo = " + codigoBanco);

		// Consulta de los select
		series.SELECT("'SERIE' tipo");
		series.SELECT("sf.idseriefacturacion");
		series.SELECT("sf.nombreabreviado abreviatura");
		series.SELECT("sf.descripcion");
		series.SELECT("s.idsufijo");
		series.SELECT("s.sufijo || ' - ' || s.concepto sufijo");

		// Subsonsulta 1 de las series
		series.SELECT("( " +
				"SELECT COUNT(f.idfactura) " +
				"FROM fac_factura f, fac_estadofactura ef " +
				"WHERE f.idinstitucion = sf.idinstitucion " +
					"AND f.idseriefacturacion = sf.idseriefacturacion " +
					"AND f.estado NOT IN ( " +
						"SELECT idestado " +
						"FROM fac_estadofactura " +
						"WHERE upper(descripcion) LIKE '%PAGADO%' " +
                        ")) num_pendientes"
		);

		series.FROM("fac_seriefacturacion sf");
		series.INNER_JOIN("fac_seriefacturacion_banco sfb ON (sf.idinstitucion = sfb.idinstitucion " +
							"AND sf.idseriefacturacion = sfb.idseriefacturacion)");
		series.LEFT_OUTER_JOIN("fac_sufijo s ON (sfb.idinstitucion = s.idinstitucion " +
								"AND sfb.idsufijo = s.idsufijo)");
		series.WHERE("sf.idinstitucion = " + idInstitucion + " AND sfb.bancos_codigo = " + codigoBanco);

		principal = pagos + " UNION " + series;

		return principal;

	}

}
