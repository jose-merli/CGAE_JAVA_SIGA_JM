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
		sql.SELECT("bi.descripcion iban");
		sql.SELECT("sf.nombreabreviado");
		sql.SELECT("sf.descripcion");
		sql.SELECT("sf.observaciones");
		sql.SELECT("sf.fechabaja");
		sql.SELECT("s.idsufijo");
		sql.SELECT("(s.sufijo || ' - ' || s.concepto ) sufijo");
		sql.SELECT("s.idsufijo");
		//sql.SELECT("fp.idformapago");
		sql.SELECT("( SELECT COUNT(*) "
				+ "		FROM fac_formapagoserie fp "
				+ "		WHERE (fp.idinstitucion=sf.idinstitucion AND fp.idseriefacturacion=sf.idseriefacturacion) "
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
		// sql.LEFT_OUTER_JOIN("fac_formapagoserie fp ON (fp.idinstitucion=sf.idinstitucion AND fp.idseriefacturacion=sf.idseriefacturacion)");
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
						+ "WHERE tpf.idinstitucion = sf.idinstitucion "
							+ "AND (tpf.idtipoproducto || '-' || tpf.idproducto) IN (" + String.join(", ", serieFacturacionItem.getIdTiposProductos().stream().map(t -> "'" + t + "'").collect(Collectors.toList())) + "))");
		
		if (serieFacturacionItem.getIdTiposServicios() != null && !serieFacturacionItem.getIdTiposServicios().isEmpty())
			sql.WHERE("sf.idseriefacturacion IN ( "
					+ "SELECT DISTINCT tsf.idseriefacturacion "
						+ "FROM fac_tiposservinclsenfact tsf "
						+ "WHERE tsf.idinstitucion = sf.idinstitucion "
							+ "AND (tsf.idtiposervicios || '-' || tsf.idservicio) IN (" + String.join(", ", serieFacturacionItem.getIdTiposServicios().stream().map(t -> "'" + t + "'").collect(Collectors.toList())) + "))");
		
		if (serieFacturacionItem.getIdEtiquetas() != null && !serieFacturacionItem.getIdEtiquetas().isEmpty())
			sql.WHERE("sf.idseriefacturacion IN ( "
					+ "SELECT DISTINCT ti.idseriefacturacion "
						+ "FROM fac_tipocliincluidoenseriefac ti "
						+ "WHERE ti.idinstitucion = sf.idinstitucion "
							+ "AND ti.idgrupo IN (" + String.join(", ", serieFacturacionItem.getIdEtiquetas()) + "))");
		
		if (serieFacturacionItem.getIdConsultasDestinatarios() != null && !serieFacturacionItem.getIdConsultasDestinatarios().isEmpty())
			sql.WHERE("sf.idseriefacturacion IN ( "
						+ "SELECT DISTINCT csf.idseriefacturacion "
							+ "FROM fac_grupcritincluidosenserie csf "
							+ "WHERE csf.idinstitucion = sf.idinstitucion "
								+ "AND csf.idinstitucion_grup || '-' || csf.idgruposcriterios IN (" + String.join(", ", serieFacturacionItem.getIdConsultasDestinatarios().stream().map(t -> "'" + t + "'").collect(Collectors.toList())) + "))");
		
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

		SQL principal = new SQL();
		SQL pagos = new SQL();
		SQL series = new SQL();

		// Consulta de los pagos
		pagos.SELECT("'PAGOS SJCS' tipo");
		pagos.SELECT("p.IDPAGOSJG idseriefacturacion");
		pagos.SELECT("p.abreviatura");
		pagos.SELECT("p.NOMBRE descripcion");
		pagos.SELECT("s.idsufijo");
		pagos.SELECT("s.sufijo || ' - ' || s.concepto sufijo ");

		// Subsonsulta 1 de los pagos
		pagos.SELECT("(SELECT DISTINCT  COUNT(a.idpagosjg) FROM fac_abono a " +
				"WHERE a.IDPAGOSJG = p.IDPAGOSJG AND a.idinstitucion = p.idinstitucion " +
					"AND a.estado NOT IN (SELECT idestado FROM fac_estadoabono " +
						"WHERE UPPER(descripcion) LIKE '%PAGADO%')) num_pendientes");

		pagos.FROM("fcs_pagosjg p");
		pagos.LEFT_OUTER_JOIN("fac_sufijo s ON (p.idinstitucion = s.idinstitucion " +
				"AND p.idsufijo = s.idsufijo)");
		pagos.WHERE("p.idinstitucion = " + idInstitucion + " AND p.bancos_codigo = " + codigoBanco);

		// Consulta de los select
		series.SELECT("'SERIE' tipo");
		series.SELECT("sf.idseriefacturacion");
		series.SELECT("sf.nombreabreviado abreviatura");
		series.SELECT("sf.descripcion");
		series.SELECT("s.idsufijo");
		series.SELECT("s.sufijo || ' - ' || s.concepto sufijo");

		// Subsonsulta 1 de las series
		series.SELECT("(SELECT DISTINCT COUNT(f.idfactura) FROM fac_factura f, fac_estadofactura ef " +
				"WHERE f.idinstitucion = sf.idinstitucion " +
					"AND f.idseriefacturacion = sf.idseriefacturacion " +
					"AND f.estado NOT IN ( SELECT idestado FROM fac_estadofactura " +
						"WHERE upper(descripcion) LIKE '%PAGADO%')) num_pendientes"
		);

		series.FROM("fac_seriefacturacion sf");
		series.INNER_JOIN("fac_seriefacturacion_banco sfb ON (sf.idinstitucion = sfb.idinstitucion " +
							"AND sf.idseriefacturacion = sfb.idseriefacturacion)");
		series.LEFT_OUTER_JOIN("fac_sufijo s ON (sfb.idinstitucion = s.idinstitucion " +
								"AND sfb.idsufijo = s.idsufijo)");
		series.WHERE("sf.idinstitucion = " + idInstitucion + " AND sfb.bancos_codigo = " + codigoBanco);

		principal.SELECT("*");
		principal.FROM("(" + pagos + " UNION " + series + ")");
		principal.ORDER_BY("tipo desc");
		principal.ORDER_BY("abreviatura");

		return principal.toString();

	}

	public String getBancosSufijos(Short idInstitucion) {
		SQL sql = new SQL();

		// Consulta de los select
		sql.SELECT_DISTINCT("sf.bancos_codigo, sfb.idsufijo");

		sql.FROM("fac_seriefacturacion sf");
		sql.INNER_JOIN("fac_seriefacturacion_banco sfb ON (sf.idinstitucion = sfb.idinstitucion " +
				"AND sf.idseriefacturacion = sfb.idseriefacturacion)");
		sql.WHERE("sf.idinstitucion = " + idInstitucion);
		sql.WHERE("sf.fechabaja is null");

		return sql.toString();

	}

	public String obtenerSeriesAdecuadas(String idInstitucion, int numeroTipos, String listadoProductos) {
		SQL sql = new SQL();
		sql.SELECT("S.IDINSTITUCION");
		sql.SELECT("S.IDSERIEFACTURACION");
		sql.SELECT("S.IDPLANTILLA");
		sql.SELECT("S.ID_NOMBRE_DESCARGA_FAC");
		sql.SELECT("S.DESCRIPCION");
		sql.SELECT("S.NOMBREABREVIADO");
		sql.SELECT("S.ENVIOFACTURAS");
		sql.SELECT("S.GENERARPDF");
		sql.SELECT("S.TRASPASO_PLANTILLA");
		sql.SELECT("S.TRASPASO_CODAUDITORIA_DEF");
		sql.SELECT("S.IDCONTADOR");
		sql.SELECT("S.IDCONTADOR_ABONOS");
		sql.SELECT("S.CONFDEUDOR");
		sql.SELECT("S.CONFINGRESOS");
		sql.SELECT("S.CTACLIENTES");
		sql.SELECT("S.CTAINGRESOS");
		sql.SELECT("S.OBSERVACIONES");
		sql.SELECT("S.TIPOSERIE");
		sql.SELECT("S.IDTIPOPLANTILLAMAIL");
		sql.SELECT("S.IDTIPOENVIOS");
		sql.SELECT("S.IDSERIEFACTURACIONPREVIA");
		sql.SELECT("S.FECHABAJA");
		sql.SELECT("S.FECHAMODIFICACION");
		sql.SELECT("S.USUMODIFICACION");
		sql.FROM("FAC_SERIEFACTURACION S");
		sql.WHERE("S.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("S.TIPOSERIE = 'G'");
		sql.WHERE("FECHABAJA IS NULL");
		sql.WHERE(numeroTipos + " = (SELECT COUNT(*) FROM FAC_TIPOSPRODUINCLUENFACTU T WHERE (T.IDPRODUCTO, T.IDTIPOPRODUCTO) IN (" + listadoProductos + ") AND T.IDINSTITUCION = S.IDINSTITUCION AND T.IDSERIEFACTURACION = S.IDSERIEFACTURACION)");
		sql.ORDER_BY("S.DESCRIPCION");
		return sql.toString();
	}

	public String obtenerSeriesAdecuadas2(String idInstitucion, int numeroTipos, String listadoProductos, String idPersona) {
		SQL sql = new SQL();
		sql.SELECT("S.IDINSTITUCION");
		sql.SELECT("S.IDSERIEFACTURACION");
		sql.SELECT("S.IDPLANTILLA");
		sql.SELECT("S.ID_NOMBRE_DESCARGA_FAC");
		sql.SELECT("S.DESCRIPCION");
		sql.SELECT("S.NOMBREABREVIADO");
		sql.SELECT("S.ENVIOFACTURAS");
		sql.SELECT("S.GENERARPDF");
		sql.SELECT("S.TRASPASO_PLANTILLA");
		sql.SELECT("S.TRASPASO_CODAUDITORIA_DEF");
		sql.SELECT("S.IDCONTADOR");
		sql.SELECT("S.IDCONTADOR_ABONOS");
		sql.SELECT("S.CONFDEUDOR");
		sql.SELECT("S.CONFINGRESOS");
		sql.SELECT("S.CTACLIENTES");
		sql.SELECT("S.CTAINGRESOS");
		sql.SELECT("S.OBSERVACIONES");
		sql.SELECT("S.TIPOSERIE");
		sql.SELECT("S.IDTIPOPLANTILLAMAIL");
		sql.SELECT("S.IDTIPOENVIOS");
		sql.SELECT("S.IDSERIEFACTURACIONPREVIA");
		sql.SELECT("S.FECHABAJA");
		sql.SELECT("S.FECHAMODIFICACION");
		sql.SELECT("S.USUMODIFICACION");
		sql.FROM("FAC_SERIEFACTURACION S");
		sql.WHERE("S.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("FECHABAJA IS NULL");
		sql.WHERE(numeroTipos + " = (SELECT COUNT(*) FROM FAC_TIPOSPRODUINCLUENFACTU T WHERE (T.IDPRODUCTO, T.IDTIPOPRODUCTO) IN (" + listadoProductos + ") AND T.IDINSTITUCION = S.IDINSTITUCION AND T.IDSERIEFACTURACION = S.IDSERIEFACTURACION\n" +
				"\tAND EXISTS (SELECT 1 FROM TABLE(PKG_SIGA_FACTURACION.OBTENCIONPOBLACIONCLIENTES(T.IDINSTITUCION, T.IDSERIEFACTURACION)) PoblSF WHERE PoblSF.IDPERSONA = " + idPersona + "))");
		return sql.toString();
	}

}
