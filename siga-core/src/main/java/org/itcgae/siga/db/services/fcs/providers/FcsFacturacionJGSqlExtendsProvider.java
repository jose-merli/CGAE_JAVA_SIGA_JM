package org.itcgae.siga.db.services.fcs.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
//import org.itcgae.siga.DTOs.scs.CartasFacturacionPagosItem;
import org.itcgae.siga.DTOs.scs.FacturacionItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.FcsFacturacionjgSqlProvider;

public class FcsFacturacionJGSqlExtendsProvider extends FcsFacturacionjgSqlProvider {

	public String buscarFacturaciones(FacturacionItem facturacionItem, String idInstitucion) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();

		sql.SELECT("IDINSTITUCION");
		sql.SELECT("ABREVIATURA");
		sql.SELECT("IDFACTURACION");
		sql.SELECT("FECHADESDE");
		sql.SELECT("FECHAHASTA");
		sql.SELECT("NOMBRE");
		sql.SELECT("REGULARIZACION");
		sql.SELECT("DESESTADO");
		sql.SELECT("IDESTADO");
		sql.SELECT("FECHAESTADO");
		sql.SELECT("IMPORTETOTAL");
		sql.SELECT("IMPORTEPAGADO");
		sql.SELECT("IMPORTETOTAL-IMPORTEPAGADO AS IMPORTEPENDIENTE");

		sql2.SELECT("FAC.IDINSTITUCION");
		sql2.SELECT("INS.ABREVIATURA");
		sql2.SELECT("FAC.IDFACTURACION");
		sql2.SELECT("FAC.FECHADESDE");
		sql2.SELECT("FAC.FECHAHASTA");
		sql2.SELECT("FAC.NOMBRE");
		sql2.SELECT("DECODE(FAC.REGULARIZACION, '1', 'Si', 'No') AS REGULARIZACION");
		sql2.SELECT("(SELECT F_SIGA_GETRECURSO(ESTADOS.DESCRIPCION, 1) DESCRIPCION "
				+ "FROM FCS_ESTADOSFACTURACION ESTADOS "
				+ "WHERE EST.IDESTADOFACTURACION = ESTADOS.IDESTADOFACTURACION) AS DESESTADO");
		sql2.SELECT("EST.IDESTADOFACTURACION AS IDESTADO");
		sql2.SELECT("EST.FECHAESTADO AS FECHAESTADO");
		sql2.SELECT("NVL(FAC.IMPORTETOTAL, 0) AS IMPORTETOTAL");
		sql2.SELECT("NVL(DECODE(EST.IDESTADOFACTURACION, 20, 0, " + "(SELECT SUM(P.IMPORTEPAGADO) "
				+ "FROM FCS_PAGOSJG P "
				+ "WHERE P.IDFACTURACION = FAC.IDFACTURACION AND P.IDINSTITUCION = FAC.IDINSTITUCION)),0) AS IMPORTEPAGADO");
		sql2.FROM("FCS_FACTURACIONJG FAC");
		sql2.INNER_JOIN("CEN_INSTITUCION INS ON (FAC.IDINSTITUCION = INS.IDINSTITUCION)");
		sql2.INNER_JOIN(
				"FCS_FACT_ESTADOSFACTURACION EST ON (FAC.IDINSTITUCION = EST.IDINSTITUCION AND FAC.IDFACTURACION = EST.IDFACTURACION)");
		sql2.WHERE("FAC.PREVISION = '0'");
		sql2.WHERE("FAC.IDINSTITUCION = '" + idInstitucion + "'");

		// FILTRO ESTADOS FACTURACIÓN
		if (!UtilidadesString.esCadenaVacia(facturacionItem.getIdEstado())) {
			sql2.WHERE("EST.IDORDENESTADO =(SELECT MAX(EST2.IDORDENESTADO) " + "FROM FCS_FACT_ESTADOSFACTURACION EST2 "
					+ "WHERE EST2.IDINSTITUCION = EST.IDINSTITUCION AND EST2.IDFACTURACION = EST.IDFACTURACION)");
			sql2.WHERE("EST.IDESTADOFACTURACION = " + facturacionItem.getIdEstado());
		}

		//FILTRO NOMBRE
		if(!UtilidadesString.esCadenaVacia(facturacionItem.getNombre())) {
			sql2.WHERE(UtilidadesString.filtroTextoBusquedas("FAC.NOMBRE", facturacionItem.getNombre()));
		}

		// FILTRO POR CONCEPTOS DE FACTURACIÓN Y POR GRUPOS DE FACTURACIÓN Y PARTIDA
		// PRESUPUESTARIA
		if (!UtilidadesString.esCadenaVacia(facturacionItem.getIdConcepto())
				|| !UtilidadesString.esCadenaVacia(facturacionItem.getIdFacturacion())
				|| !UtilidadesString.esCadenaVacia(facturacionItem.getIdPartidaPresupuestaria())) {
			StringBuilder queryAux = new StringBuilder();
			queryAux.append(
					"EXISTS (SELECT 1 FROM FCS_FACT_GRUPOFACT_HITO HIT WHERE HIT.IDFACTURACION = FAC.IDFACTURACION AND HIT.IDINSTITUCION = FAC.IDINSTITUCION");

			// FILTRO POR CONCEPTOS DE FACTURACION
			if (!UtilidadesString.esCadenaVacia(facturacionItem.getIdConcepto())) {
				queryAux.append(" AND HIT.IDHITOGENERAL = " + facturacionItem.getIdConcepto());
			}
			// FILTRO POR GRUPO FACTURACION
			if (!UtilidadesString.esCadenaVacia(facturacionItem.getIdFacturacion())) {
				queryAux.append(" AND HIT.IDGRUPOFACTURACION = " + facturacionItem.getIdFacturacion());
			}
			// FILTRO POR PARTIDA PRESUPUESTARIA
			if (!UtilidadesString.esCadenaVacia(facturacionItem.getIdPartidaPresupuestaria())) {
				queryAux.append(" AND HIT.IDPARTIDAPRESUPUESTARIA = " + facturacionItem.getIdPartidaPresupuestaria());
			}

			queryAux.append(")");
			sql2.WHERE(queryAux.toString());
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		// FILTRO FECHADESDE
		if (null != facturacionItem.getFechaDesde()) {
			String fechaF = dateFormat.format(facturacionItem.getFechaDesde());
			sql2.WHERE("FAC.FECHADESDE >=TO_DATE('" + fechaF + "', 'DD/MM/YYYY hh24:mi:ss')");
		}

		// FILTRO FECHAHASTA
		if (null != facturacionItem.getFechaHasta()) {
			String fechaF = dateFormat.format(facturacionItem.getFechaHasta());
			sql2.WHERE("FAC.FECHAHASTA <=TO_DATE('" + fechaF + "', 'DD/MM/YYYY hh24:mi:ss')");
		}

		sql.FROM("(" + sql2.toString() + ") busqueda");
		sql.ORDER_BY("busqueda.NOMBRE");
		sql.ORDER_BY("busqueda.FECHADESDE DESC");

		return sql.toString();
	}

	public String datosFacturacion(String idFacturacion, String idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("fac.idinstitucion");
		sql.SELECT("fac.idfacturacion");
		sql.SELECT("fac.fechadesde");
		sql.SELECT("fac.fechahasta");
		sql.SELECT("fac.nombre");
		sql.SELECT("fac.idpartidapresupuestaria");
		sql.SELECT("fac.regularizacion");
		sql.SELECT("fac.importeejg");
		sql.SELECT("fac.prevision");
		sql.SELECT("fac.visible");
		sql.FROM("fcs_facturacionjg fac");
		sql.WHERE("fac.idinstitucion = '" + idInstitucion + "'");
		sql.WHERE("fac.idfacturacion =  '" + idFacturacion + "'");
		sql.ORDER_BY("fac.idinstitucion");
		sql.ORDER_BY("fac.idfacturacion");

		return sql.toString();
	}

	public String historicoFacturacion(String idFacturacion, String lenguaje, String idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("estados.idestadofacturacion");
		sql.SELECT("rec.descripcion descripcion");
		sql.SELECT("est.fechaestado fechaestado");
		sql.FROM("fcs_fact_estadosfacturacion est");
		sql.JOIN("fcs_estadosfacturacion estados on (est.idestadofacturacion = estados.idestadofacturacion)");
		sql.JOIN("gen_recursos_catalogos rec on (estados.descripcion = rec.idrecurso)");
		sql.WHERE("est.idinstitucion = '" + idInstitucion + "'");
		sql.WHERE("est.idfacturacion = '" + idFacturacion + "'");
		sql.WHERE("rec.idlenguaje = '" + lenguaje + "'");
		sql.ORDER_BY("fechaestado DESC");

		return sql.toString();
	}

	public String getIdFacturacion(Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("NVL(MAX(IDFACTURACION),0) AS IDFACTURACION");
		sql.FROM("FCS_FACTURACIONJG");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}

	public String getIdOrdenEstado(Short idInstitucion, String idFacturacion) {

		SQL sql = new SQL();

		sql.SELECT("NVL(MAX(IDORDENESTADO),1) AS IDORDENESTADO");
		sql.FROM("FCS_FACT_ESTADOSFACTURACION");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("IDFACTURACION = '" + idFacturacion + "'");

		return sql.toString();
	}

	
	public String conceptosFacturacion(String idFacturacion, String idInstitucion, String idLenguaje) {
		SQL sql = new SQL();
		SQL sqlDescGrupo = new SQL();
		SQL sqlDescConcepto = new SQL();
		
		sqlDescGrupo.SELECT("rec.descripcion");
		sqlDescGrupo.FROM("gen_recursos_catalogos rec");
		sqlDescGrupo.WHERE("rec.idrecurso = grupo.nombre");
		sqlDescGrupo.WHERE("rec.idlenguaje = '"+idLenguaje+"'");
		
		sqlDescConcepto.SELECT("rec.descripcion");
		sqlDescConcepto.FROM("gen_recursos_catalogos rec");
		sqlDescConcepto.WHERE("rec.idrecurso = concept.descripcion");
		sqlDescConcepto.WHERE("rec.idlenguaje = '"+idLenguaje+"'");
		
		sql.SELECT("fac.idinstitucion");
		sql.SELECT("fac.idfacturacion");
		sql.SELECT("("+sqlDescGrupo.toString()+") descGrupo");
		sql.SELECT("("+sqlDescConcepto.toString()+") descConcepto");
		sql.SELECT("grupo.idgrupofacturacion idgrupo");
		sql.SELECT("concept.idhitogeneral idconcepto");
		sql.SELECT("CASE concept.idhitogeneral\r\n" + 
				"        WHEN 10   THEN\r\n" + 
				"            fac.importeoficio\r\n" + 
				"        WHEN 20   THEN\r\n" + 
				"            fac.importeguardia\r\n" + 
				"        WHEN 30   THEN\r\n" + 
				"            fac.importesoj\r\n" + 
				"        ELSE\r\n" + 
				"            fac.importeejg\r\n" + 
				"    END AS importetotal"); 
		sql.SELECT("CASE concept.idhitogeneral\r\n" + 
				"        WHEN 10   THEN\r\n" + 
				"            fac.importeoficio - pago.importeoficio\r\n" + 
				"        WHEN 20   THEN\r\n" + 
				"            fac.importeguardia - pago.importeguardia\r\n" + 
				"        WHEN 30   THEN\r\n" + 
				"            fac.importesoj - pago.importesoj\r\n" + 
				"        ELSE\r\n" + 
				"            fac.importeejg - pago.importeejg\r\n" + 
				"    END AS importependiente");
		sql.FROM("fcs_fact_grupofact_hito   factgrupo");  
		sql.LEFT_OUTER_JOIN("scs_grupofacturacion grupo ON (factgrupo.idgrupofacturacion = grupo.idgrupofacturacion AND factgrupo.idinstitucion = grupo.idinstitucion)");
		sql.INNER_JOIN("fcs_hitogeneral concept ON (factgrupo.idhitogeneral = concept.idhitogeneral)");
		sql.INNER_JOIN("fcs_facturacionjg fac ON (factgrupo.idfacturacion = fac.idfacturacion AND factgrupo.idinstitucion = fac.idinstitucion)");
		sql.INNER_JOIN("fcs_pagosjg pago ON (fac.idfacturacion = pago.idfacturacion AND fac.idinstitucion = pago.idinstitucion)");
		sql.WHERE("factgrupo.idinstitucion = '"+idInstitucion+"'");
		sql.WHERE(" factgrupo.idfacturacion = '"+idFacturacion+"'");
		sql.ORDER_BY("factgrupo.idhitogeneral");
		
		return sql.toString();
	}



	public String getComboFactColegio(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("fac.idfacturacion");
		sql.SELECT(
				"to_char(fac.fechadesde, 'dd/mm/yyyy') || '-' || to_char(fac.fechahasta, 'dd/mm/yyyy') || ' - ' || fac.nombre as LABEL");
		sql.FROM("fcs_facturacionjg fac");
		sql.INNER_JOIN(
				"fcs_fact_estadosfacturacion est ON fac.idfacturacion = est.idfacturacion AND fac.idinstitucion = est.idinstitucion");
		sql.WHERE("fac.idinstitucion = '" + idInstitucion + "'");
		sql.WHERE("(est.idestadofacturacion = 30 OR est.idestadofacturacion = 20)");
		sql.WHERE("(fac.prevision IS NULL OR fac.prevision = '0')");

		SQL sql1 = new SQL();
		sql1.SELECT("MAX(est2.idordenestado)");
		sql1.FROM("fcs_fact_estadosfacturacion est2");
		sql1.WHERE("est2.idinstitucion = est.idinstitucion");
		sql1.WHERE("est2.idfacturacion = est.idfacturacion");

		sql.WHERE("est.idordenestado = (" + sql1 + ")");
		sql.ORDER_BY("fac.fechadesde DESC");
		return sql.toString();
	}

//	public String buscarCartasfacturacion(CartasFacturacionPagosItem cartasFacturacionPagosItem, Short idInstitucion) {
//
//		SQL sql = new SQL();
//
//		sql.SELECT("per.apellidos1 || ' ' || per.apellidos2 || ', ' || per.nombre nombrecol");
//		sql.SELECT("fac.nombre nombrefac");
//		sql.SELECT("fac.idfacturacion");
//		sql.SELECT("fac.fechadesde");
//		sql.SELECT("fac.fechahasta");
//		sql.SELECT("decode(col.comunitario, 1, col.ncomunitario, col.ncolegiado) ncolegiado");
//		sql.SELECT(" SUM(impguardia) + SUM(impoficio) + SUM(impejg) + SUM(impsoj) importetotal");
//		sql.SELECT("SUM(impguardia) importeguardia");
//		sql.SELECT("SUM(impoficio) importeoficio");
//		sql.SELECT("SUM(impejg) importeejg");
//		sql.SELECT("SUM(impsoj) importesoj");
//
//		SQL sql2 = new SQL();
//		sql2.SELECT("idpersona");
//		sql2.SELECT("idinstitucion");
//		sql2.SELECT("idfacturacion");
//		sql2.SELECT("precioaplicado + preciocostesfijos impguardia");
//		sql2.SELECT("0 impoficio");
//		sql2.SELECT("0 impejg");
//		sql2.SELECT("0 impsoj");
//
//		sql2.FROM("fcs_fact_apunte");
//
//		SQL sql3 = new SQL();
//		sql3.SELECT("idpersona");
//		sql3.SELECT("idinstitucion");
//		sql3.SELECT("idfacturacion");
//		sql3.SELECT("0");
//		sql3.SELECT("importefacturado impoficio");
//		sql3.SELECT("0");
//		sql3.SELECT("0");
//
//		sql3.FROM("fcs_fact_actuaciondesigna");
//
//		SQL sql4 = new SQL();
//		sql4.SELECT("idpersona");
//		sql4.SELECT("idinstitucion");
//		sql4.SELECT("idfacturacion");
//		sql4.SELECT("0");
//		sql4.SELECT("0 impoficio");
//		sql4.SELECT("precioaplicado impejg");
//		sql4.SELECT("0 impsoj");
//
//		sql4.FROM("fcs_fact_ejg");
//
//		SQL sql5 = new SQL();
//		sql5.SELECT("idpersona");
//		sql5.SELECT("idinstitucion");
//		sql5.SELECT("idfacturacion");
//		sql5.SELECT("0");
//		sql5.SELECT("0 impoficio");
//		sql5.SELECT("0");
//		sql5.SELECT("precioaplicado impsoj");
//
//		sql5.FROM("fcs_fact_soj");
//
//		sql.FROM("((" + sql2 + ") UNION ALL (" + sql3 + ") UNION ALL (" + sql4 + ") UNION ALL (" + sql5 + ")) importes");
//		sql.FROM("cen_persona per");
//		sql.FROM("cen_colegiado col");
//		sql.FROM("fcs_facturacionjg fac");
//		sql.FROM("fcs_fact_grupofact_hito grupo");
//
//		sql.WHERE("col.idpersona = importes.idpersona");
//		sql.WHERE("col.idinstitucion = importes.idinstitucion");
//		sql.WHERE("col.idpersona = per.idpersona");
//		sql.WHERE("fac.idfacturacion = importes.idfacturacion");
//		sql.WHERE("fac.idinstitucion = importes.idinstitucion");
//		sql.WHERE("fac.idinstitucion = grupo.idinstitucion");
//		sql.WHERE("fac.idfacturacion = grupo.idfacturacion");
//		sql.WHERE("fac.idinstitucion = " + idInstitucion);
//
//		if (!UtilidadesString.esCadenaVacia(cartasFacturacionPagosItem.getIdFacturacion())) {
//			sql.WHERE("fac.idfacturacion > " + cartasFacturacionPagosItem.getIdFacturacion());
//		}
//
//		if (!UtilidadesString.esCadenaVacia(cartasFacturacionPagosItem.getIdPersona())) {
//			sql.WHERE("col.idpersona = " + cartasFacturacionPagosItem.getIdPersona());
//		}
//
//		if (!UtilidadesString.esCadenaVacia(cartasFacturacionPagosItem.getIdTurno())) {
//			sql.WHERE("grupo.IDGRUPOFACTURACION =" + cartasFacturacionPagosItem.getIdTurno());
//		}
//
//		if (!UtilidadesString.esCadenaVacia(cartasFacturacionPagosItem.getIdConcepto())) {
//			sql.WHERE("grupo.IDHITOGENERAL =" + cartasFacturacionPagosItem.getIdConcepto());
//		}
//
//		if (!UtilidadesString.esCadenaVacia(cartasFacturacionPagosItem.getIdPartidaPresupuestaria())) {
//			sql.WHERE("fac.IDPARTIDAPRESUPUESTARIA =" + cartasFacturacionPagosItem.getIdPartidaPresupuestaria());
//		}
//
//		sql.GROUP_BY(
//				"importes.idpersona, col.ncolegiado,col.comunitario,col.ncomunitario, fac.nombre, fac.fechadesde, fac.fechahasta,fac.idfacturacion,per.nombre,per.apellidos1,per.apellidos2");
//		sql.ORDER_BY("fac.fechadesde desc,per.apellidos1,per.apellidos2,per.nombre");
//
//		return sql.toString();
//	}

}
