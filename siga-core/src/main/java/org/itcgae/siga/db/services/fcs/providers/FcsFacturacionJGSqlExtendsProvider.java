package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.CartasFacturacionPagosItem;
import org.itcgae.siga.DTOs.scs.FacturacionItem;
import org.itcgae.siga.commons.constants.SigaConstants.ESTADO_FACTURACION;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.*;
import org.itcgae.siga.db.mappers.FcsFacturacionjgSqlProvider;

import java.text.SimpleDateFormat;

public class FcsFacturacionJGSqlExtendsProvider extends FcsFacturacionjgSqlProvider {

	private Logger LOGGER = Logger.getLogger(FcsFacturacionJGSqlExtendsProvider.class);

	
	public String buscarFacturaciones(FacturacionItem facturacionItem, String idInstitucion, Integer tamMax) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();

		sql.SELECT("IDINSTITUCION");
		sql.SELECT("ABREVIATURA");
		sql.SELECT("IDFACTURACION");
		sql.SELECT("FECHADESDE");
		sql.SELECT("FECHAHASTA");
		sql.SELECT("NOMBRE");
		//SIGARNV-2815@DTT.JAMARTIN@06/07/2022@INICIO
		sql.SELECT("LISTAGG(IDGRUPOFACTURACION, ', ') WITHIN GROUP (ORDER BY IDGRUPOFACTURACION) AS IDGRUPOFACTURACION");
		//SIGARNV-2815@DTT.JAMARTIN@06/07/2022@FIN 
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
		sql2.SELECT("F_SIGA_GETRECURSO(sg.NOMBRE,1) IDGRUPOFACTURACION");
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
		sql2.FROM(
				"FCS_FACTURACIONJG FAC LEFT JOIN FCS_FACT_GRUPOFACT_HITO ffgh ON (ffgh.IDFACTURACION = FAC.IDFACTURACION AND ffgh.IDINSTITUCION = FAC.IDINSTITUCION) LEFT JOIN SCS_GRUPOFACTURACION sg ON (sg.IDGRUPOFACTURACION = ffgh.IDGRUPOFACTURACION AND sg.IDINSTITUCION = ffgh.IDINSTITUCION) ");
		sql2.INNER_JOIN("CEN_INSTITUCION INS ON (FAC.IDINSTITUCION = INS.IDINSTITUCION)");
		sql2.INNER_JOIN(
				"FCS_FACT_ESTADOSFACTURACION EST ON (FAC.IDINSTITUCION = EST.IDINSTITUCION AND FAC.IDFACTURACION = EST.IDFACTURACION)");
		// sql2.INNER_JOIN("FCS_FACT_GRUPOFACT_HITO ffgh ON (ffgh.IDFACTURACION =
		// FAC.IDFACTURACION AND ffgh.IDINSTITUCION = FAC.IDINSTITUCION)");
		// sql2.INNER_JOIN("SCS_GRUPOFACTURACION sg ON (sg.IDGRUPOFACTURACION =
		// ffgh.IDGRUPOFACTURACION AND sg.IDINSTITUCION = ffgh.IDINSTITUCION)");
		sql2.WHERE("FAC.IDINSTITUCION = '" + idInstitucion + "'");
		sql2.WHERE("EST.IDORDENESTADO =(SELECT MAX(EST2.IDORDENESTADO) FROM FCS_FACT_ESTADOSFACTURACION EST2 "
				+ "WHERE EST2.IDINSTITUCION = EST.IDINSTITUCION AND EST2.IDFACTURACION = EST.IDFACTURACION)");
		// FILTRO ESTADOS FACTURACIÓN
		if (!UtilidadesString.esCadenaVacia(facturacionItem.getIdEstado())) {
			sql2.WHERE("EST.IDESTADOFACTURACION IN ( " + facturacionItem.getIdEstado() + " )");
		}

		// FILTRO NOMBRE
		if (!UtilidadesString.esCadenaVacia(facturacionItem.getNombre())) {
			sql2.WHERE(UtilidadesString.filtroTextoBusquedas("FAC.NOMBRE", facturacionItem.getNombre().trim()));
		}

		// FILTRO POR PARTIDA PRESUPUESTARIA
		if (!UtilidadesString.esCadenaVacia(facturacionItem.getIdPartidaPresupuestaria())) {
			sql2.WHERE("FAC.IDPARTIDAPRESUPUESTARIA IN ( " + facturacionItem.getIdPartidaPresupuestaria() + " )");
		}

		// FILTRO POR CONCEPTOS DE FACTURACIÓN Y POR GRUPOS DE FACTURACIÓN Y PARTIDA
		// PRESUPUESTARIA
		if (!UtilidadesString.esCadenaVacia(facturacionItem.getIdConcepto())
				|| !UtilidadesString.esCadenaVacia(facturacionItem.getIdFacturacion())
				|| !UtilidadesString.esCadenaVacia(facturacionItem.getIdPartidaPresupuestaria())) {
			SQL sql3 = new SQL();

			sql3.SELECT("1");
			sql3.FROM("FCS_FACT_GRUPOFACT_HITO HIT");
			sql3.WHERE("HIT.IDFACTURACION = FAC.IDFACTURACION");
			sql3.WHERE("HIT.IDINSTITUCION = FAC.IDINSTITUCION");

			// FILTRO POR CONCEPTOS DE FACTURACION
			if (!UtilidadesString.esCadenaVacia(facturacionItem.getIdConcepto())) {
				sql3.WHERE("HIT.IDHITOGENERAL IN ( " + facturacionItem.getIdConcepto() + " )");
			}
			// FILTRO POR GRUPO FACTURACION
			if (!UtilidadesString.esCadenaVacia(facturacionItem.getIdFacturacion())) {
				sql3.WHERE("HIT.IDGRUPOFACTURACION IN ( " + facturacionItem.getIdFacturacion() + " )");
			}

			sql2.WHERE("EXISTS (" + sql3.toString() + ")");
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
		//SIGARNV-2815@DTT.JAMARTIN@06/07/2022@INICIO
		sql.GROUP_BY("IDINSTITUCION, ABREVIATURA, IDFACTURACION, FECHADESDE, FECHAHASTA, NOMBRE, REGULARIZACION, DESESTADO, IDESTADO, FECHAESTADO, IMPORTETOTAL, IMPORTEPAGADO");
		//SIGARNV-2815@DTT.JAMARTIN@06/07/2022@FIN
		sql.ORDER_BY("busqueda.FECHADESDE DESC");
		sql.ORDER_BY("busqueda.FECHAHASTA DESC");
		// sql.ORDER_BY("busqueda.FECHAESTADO DESC");

		SQL query = new SQL();
		query.SELECT("*");
		query.FROM("( " + sql.toString() + " )");

		if (tamMax != null) {
			Integer tamMaxNumber = tamMax + 1;
			query.WHERE("ROWNUM <= " + tamMaxNumber);
		}

		return query.toString();
	}

	public String getNumeroFacturacionesNoCerradas(FacturacionItem facturacionItem, Short idInstitucion) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String fecha = dateFormat.format(facturacionItem.getFechaEstado());

		SQL sql = new SQL();

		sql.SELECT(" Decode((SELECT Count(*)  "
				+ "                            FROM Fcs_Facturacionjg Facpos, "
				+ "                            Fcs_Fact_Grupofact_Hito Grupos, "
				+ "                            Fcs_Fact_Grupofact_Hito Gru  "
				+ "                            WHERE Fac.Idinstitucion = Gru.Idinstitucion "
				+ "                            AND Fac.Idfacturacion = Gru.Idfacturacion "
				+ "                            AND Facpos.Idinstitucion = Grupos.Idinstitucion "
				+ "                            AND Facpos.Idfacturacion = Grupos.Idfacturacion "
				+ "                            AND Facpos.Idinstitucion = Fac.Idinstitucion "
				+ "                            AND Facpos.prevision = Fac.prevision "
				+ "                            AND Facpos.Fechadesde > Fac.Fechadesde "
				+ "                            AND Grupos.Idgrupofacturacion = Gru.Idgrupofacturacion "
				+ "                            AND Grupos.Idhitogeneral = Gru.Idhitogeneral), 0, '1', '0') BORRAPORGRUPO,"
				+ "	("
				+ "	SELECT"
				+ "		Decode(Est.Idestadofacturacion, 10, '1', 20, '1', 60, '1', '0')"
				+ "	FROM"
				+ "		Fcs_Fact_Estadosfacturacion Est"
				+ "	WHERE"
				+ "		Fac.Idinstitucion = Est.Idinstitucion"
				+ "		AND Fac.Idfacturacion = Est.Idfacturacion"
				+ "		AND Est.Idordenestado =   "
				+ "                               ("
				+ "		SELECT"
				+ "			Max(Est2.Idordenestado)"
				+ "		FROM"
				+ "			Fcs_Fact_Estadosfacturacion Est2"
				+ "		WHERE"
				+ "			Est2.Idinstitucion = Est.Idinstitucion"
				+ "			AND Est2.Idfacturacion = Est.Idfacturacion)"
				+ "		AND Rownum = 1) BORRARPORESTADO"
				+ " FROM"
				+ "	FCS_FACTURACIONJG FAC,"
				+ "	FCS_FACT_ESTADOSFACTURACION EST,"
				+ "	CEN_INSTITUCION INS"
				+ " WHERE"
				+ "	fac.IDINSTITUCION = EST.IDINSTITUCION"
				+ "	AND fac.IDFACTURACION = EST.IDFACTURACION"
				+ "	AND est.IDORDENESTADO ="
				+ "                         ("
				+ "	SELECT"
				+ "		Max(est2.IDORDENESTADO)"
				+ "	FROM"
				+ "		FCS_FACT_ESTADOSFACTURACION EST2"
				+ "	WHERE"
				+ "		est2.IDINSTITUCION = est.IDINSTITUCION"
				+ "		AND est2.IDFACTURACION = est.IDFACTURACION  "
				+ "                       )"
				+ "	AND fac.IDINSTITUCION = ins.IDINSTITUCION"
				+ "	AND fac.IDFACTURACION = '" + facturacionItem.getIdFacturacion() + "'"
				+ "	AND fac.IDINSTITUCION = " + idInstitucion);

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

	public String historicoFacturacion(String idFacturacion, String idLenguaje, String idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("EST.IDESTADOFACTURACION");
		sql.SELECT("REC.DESCRIPCION DESCRIPCION");
		sql.SELECT("EST.FECHAESTADO FECHAESTADO");
		sql.SELECT("EST.OBSERVACIONES");
		sql.SELECT("EST.PREVISION");
		/*
		 * sql.SELECT(
		 * "(CASE WHEN PER.NOMBRE IS NOT NULL THEN PER.APELLIDOS2 || ' ' || PER.APELLIDOS1 || ', ' || PER.NOMBRE ELSE NULL END) AS USUARIO"
		 * );
		 */
		sql.SELECT("(CASE WHEN AU.DESCRIPCION IS NOT NULL THEN AU.DESCRIPCION ELSE NULL END) AS USUARIO");

		sql.FROM("FCS_FACT_ESTADOSFACTURACION EST "
				+ "JOIN FCS_FACTURACIONJG FAC ON (FAC.IDINSTITUCION = EST.IDINSTITUCION AND FAC.IDFACTURACION = EST.IDFACTURACION) "
				+ "LEFT JOIN ADM_USUARIOS AU ON (AU.IDINSTITUCION = EST.IDINSTITUCION AND AU.IDUSUARIO = EST.USUMODIFICACION) "
				// + "LEFT JOIN CEN_PERSONA PER ON (AU.NIF = PER.NIFCIF) "
				+ "JOIN FCS_ESTADOSFACTURACION ESTADOS ON (EST.IDESTADOFACTURACION = ESTADOS.IDESTADOFACTURACION) "
				+ "JOIN GEN_RECURSOS_CATALOGOS REC ON (ESTADOS.DESCRIPCION = REC.IDRECURSO)");

		sql.WHERE("EST.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("EST.IDFACTURACION = '" + idFacturacion + "'");
		sql.WHERE("REC.IDLENGUAJE = '" + idLenguaje + "'");
		sql.ORDER_BY("FECHAESTADO DESC");

		return sql.toString();
	}

	public String numApuntes(String idFacturacion, String idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("per.apellidos1 || ' ' || per.apellidos2 || ', ' || per.nombre nombrecol");
		sql.SELECT("fac.nombre nombrefac");
		sql.SELECT("fac.idfacturacion");
		sql.SELECT("fac.fechadesde");
		sql.SELECT("fac.fechahasta");
		sql.SELECT("decode(col.comunitario, 1, col.ncomunitario, col.ncolegiado) ncolegiado");
		sql.SELECT(" SUM(impguardia) + SUM(impoficio) + SUM(impejg) + SUM(impsoj) importetotal");
		sql.SELECT("SUM(impguardia) importeguardia");
		sql.SELECT("SUM(impoficio) importeoficio");
		sql.SELECT("SUM(impejg) importeejg");
		sql.SELECT("SUM(impsoj) importesoj");

		SQL sql2 = new SQL();
		sql2.SELECT("idpersona");
		sql2.SELECT("idinstitucion");
		sql2.SELECT("idfacturacion");
		sql2.SELECT("precioaplicado + preciocostesfijos impguardia");
		sql2.SELECT("0 impoficio");
		sql2.SELECT("0 impejg");
		sql2.SELECT("0 impsoj");

		sql2.FROM("fcs_fact_apunte");

		SQL sql3 = new SQL();
		sql3.SELECT("idpersona");
		sql3.SELECT("idinstitucion");
		sql3.SELECT("idfacturacion");
		sql3.SELECT("0");
		sql3.SELECT("importefacturado impoficio");
		sql3.SELECT("0");
		sql3.SELECT("0");

		sql3.FROM("fcs_fact_actuaciondesigna");

		SQL sql4 = new SQL();
		sql4.SELECT("idpersona");
		sql4.SELECT("idinstitucion");
		sql4.SELECT("idfacturacion");
		sql4.SELECT("0");
		sql4.SELECT("0 impoficio");
		sql4.SELECT("precioaplicado impejg");
		sql4.SELECT("0 impsoj");

		sql4.FROM("fcs_fact_ejg");

		SQL sql5 = new SQL();
		sql5.SELECT("idpersona");
		sql5.SELECT("idinstitucion");
		sql5.SELECT("idfacturacion");
		sql5.SELECT("0");
		sql5.SELECT("0 impoficio");
		sql5.SELECT("0");
		sql5.SELECT("precioaplicado impsoj");

		sql5.FROM("fcs_fact_soj");

		sql.FROM(
				"((" + sql2 + ") UNION ALL (" + sql3 + ") UNION ALL (" + sql4 + ") UNION ALL (" + sql5 + ")) importes");
		sql.FROM("cen_persona per");
		sql.FROM("cen_colegiado col");
		sql.FROM("fcs_facturacionjg fac");
		sql.FROM("fcs_fact_grupofact_hito grupo");

		sql.WHERE("col.idpersona = importes.idpersona");
		sql.WHERE("col.idinstitucion = importes.idinstitucion");
		sql.WHERE("col.idpersona = per.idpersona");
		sql.WHERE("fac.idfacturacion = importes.idfacturacion");
		sql.WHERE("fac.idinstitucion = importes.idinstitucion");
		sql.WHERE("fac.idinstitucion = grupo.idinstitucion");
		sql.WHERE("fac.idfacturacion = grupo.idfacturacion");
		sql.WHERE("fac.visible = '1'");
		sql.WHERE("fac.idinstitucion = " + idInstitucion);
		sql.WHERE("fac.idfacturacion = " + idFacturacion);

		sql.GROUP_BY("col.idpersona");
		sql.GROUP_BY("col.ncolegiado");
		sql.GROUP_BY("col.comunitario");
		sql.GROUP_BY("col.ncomunitario");
		sql.GROUP_BY("fac.nombre");
		sql.GROUP_BY("fac.fechadesde");
		sql.GROUP_BY("fac.fechahasta");
		sql.GROUP_BY("fac.idfacturacion");
		sql.GROUP_BY("per.nombre");
		sql.GROUP_BY("per.apellidos1");
		sql.GROUP_BY("per.apellidos2");

		SQL sql6 = new SQL();
		sql6.SELECT("COUNT(1) AS NUMAPUNTES");
		sql6.FROM("(" + sql + ") cartas");

		return sql6.toString();
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

		sqlDescGrupo.SELECT("REC.DESCRIPCION");
		sqlDescGrupo.FROM("GEN_RECURSOS_CATALOGOS REC");
		sqlDescGrupo.WHERE("REC.IDRECURSO = GRUPO.NOMBRE");
		sqlDescGrupo.WHERE("REC.IDLENGUAJE = '" + idLenguaje + "'");

		sqlDescConcepto.SELECT("REC.DESCRIPCION");
		sqlDescConcepto.FROM("GEN_RECURSOS_CATALOGOS REC");
		sqlDescConcepto.WHERE("REC.IDRECURSO = CONCEPT.DESCRIPCION");
		sqlDescConcepto.WHERE("REC.IDLENGUAJE = '" + idLenguaje + "'");

		sql.SELECT("FAC.IDINSTITUCION");
		sql.SELECT("FAC.IDFACTURACION");
		sql.SELECT("(" + sqlDescGrupo.toString() + ") DESCGRUPO");
		sql.SELECT("(" + sqlDescConcepto.toString() + ") DESCCONCEPTO");
		sql.SELECT("GRUPO.IDGRUPOFACTURACION IDGRUPO");
		sql.SELECT("CONCEPT.IDHITOGENERAL IDCONCEPTO");
		sql.SELECT("CASE CONCEPT.IDHITOGENERAL\r\n" + "        WHEN 10 THEN\r\n"
				+ "            	NVL(FAC.IMPORTEOFICIO,0)\r\n" + "        WHEN 20 THEN\r\n"
				+ "             NVL(FAC.IMPORTEGUARDIA,0)\r\n" + "        WHEN 30 THEN\r\n"
				+ "             NVL(FAC.IMPORTESOJ,0)\r\n" + "        ELSE\r\n"
				+ "             NVL(FAC.IMPORTEEJG,0)\r\n" + "END AS IMPORTETOTAL");
		sql.SELECT("CASE CONCEPT.IDHITOGENERAL\r\n" + "        WHEN 10 THEN\r\n"
				+ "             NVL(PAGO.IMPORTEOFICIO,0)\r\n" + "        WHEN 20 THEN\r\n"
				+ "              NVL(PAGO.IMPORTEGUARDIA,0)\r\n" + "        WHEN 30 THEN\r\n" + "             "
				+ "				 NVL(PAGO.IMPORTESOJ,0)\r\n" + "        ELSE\r\n"
				+ "             NVL(PAGO.IMPORTEEJG,0)\r\n" + "END AS IMPORTEPENDIENTE,"
				+ "             FACTGRUPO.IDPARTIDAPRESUPUESTARIA AS IDPARTIDAPRESUPUESTARIA");
		sql.FROM("FCS_FACT_GRUPOFACT_HITO FACTGRUPO");
		sql.LEFT_OUTER_JOIN(
				"SCS_GRUPOFACTURACION GRUPO ON (FACTGRUPO.IDGRUPOFACTURACION = GRUPO.IDGRUPOFACTURACION AND FACTGRUPO.IDINSTITUCION = GRUPO.IDINSTITUCION)");
		sql.INNER_JOIN("FCS_HITOGENERAL CONCEPT ON (FACTGRUPO.IDHITOGENERAL = CONCEPT.IDHITOGENERAL)");
		sql.INNER_JOIN(
				"FCS_FACTURACIONJG FAC ON (FACTGRUPO.IDFACTURACION = FAC.IDFACTURACION AND FACTGRUPO.IDINSTITUCION = FAC.IDINSTITUCION)");
		sql.LEFT_OUTER_JOIN(
				"FCS_PAGOSJG PAGO ON (FAC.IDFACTURACION = PAGO.IDFACTURACION AND FAC.IDINSTITUCION = PAGO.IDINSTITUCION)");
		sql.WHERE("FACTGRUPO.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("FACTGRUPO.IDFACTURACION = '" + idFacturacion + "'");
		sql.ORDER_BY("FACTGRUPO.IDHITOGENERAL");

		SQL query = new SQL();
		query.SELECT("IDINSTITUCION");
		query.SELECT("IDFACTURACION");
		query.SELECT("DESCGRUPO");
		query.SELECT("DESCCONCEPTO");
		query.SELECT("IDGRUPO");
		query.SELECT("IDCONCEPTO");
		query.SELECT("IMPORTETOTAL");
		query.SELECT("IMPORTETOTAL - SUM(IMPORTEPENDIENTE) AS IMPORTEPENDIENTE");
		query.SELECT("IDPARTIDAPRESUPUESTARIA");
		query.FROM("( " + sql.toString() + " )");
		query.GROUP_BY("IDINSTITUCION, IDFACTURACION, DESCGRUPO, DESCCONCEPTO, IDGRUPO, IDCONCEPTO, IMPORTETOTAL, IDPARTIDAPRESUPUESTARIA");
		
		//LOGGER.info("++++ [SIGA TEST] Query conceptosFacturacion() -> " + query.toString());
		return query.toString();
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

	public String buscarCartasfacturacion(CartasFacturacionPagosItem cartasFacturacionPagosItem, Short idInstitucion,
			Integer tamMax, boolean letrado) {

		SQL sql = new SQL();
		SQL sqlOrder = new SQL();

		sqlOrder.SELECT("*");

		sql.SELECT("per.apellidos1 || ' ' || per.apellidos2 || ', ' || per.nombre nombrecol");
		sql.SELECT("fac.nombre nombrefac");
		sql.SELECT("fac.idfacturacion");
		sql.SELECT("fac.fechadesde");
		sql.SELECT("fac.fechahasta");
		sql.SELECT("decode(col.comunitario, 1, col.ncomunitario, col.ncolegiado) ncolegiado");
		sql.SELECT(" SUM(impguardia) + SUM(impoficio) + SUM(impejg) + SUM(impsoj) importetotal");
		sql.SELECT("SUM(impguardia) importeguardia");
		sql.SELECT("SUM(impoficio) importeoficio");
		sql.SELECT("SUM(impejg) importeejg");
		sql.SELECT("SUM(impsoj) importesoj");

		SQL sql2 = new SQL();
		sql2.SELECT("idpersona");
		sql2.SELECT("idinstitucion");
		sql2.SELECT("idfacturacion");
		sql2.SELECT("precioaplicado + preciocostesfijos impguardia");
		sql2.SELECT("0 impoficio");
		sql2.SELECT("0 impejg");
		sql2.SELECT("0 impsoj");

		sql2.FROM("fcs_fact_apunte");

		SQL sql3 = new SQL();
		sql3.SELECT("idpersona");
		sql3.SELECT("idinstitucion");
		sql3.SELECT("idfacturacion");
		sql3.SELECT("0");
		sql3.SELECT("importefacturado impoficio");
		sql3.SELECT("0");
		sql3.SELECT("0");

		sql3.FROM("fcs_fact_actuaciondesigna");

		SQL sql4 = new SQL();
		sql4.SELECT("idpersona");
		sql4.SELECT("idinstitucion");
		sql4.SELECT("idfacturacion");
		sql4.SELECT("0");
		sql4.SELECT("0 impoficio");
		sql4.SELECT("precioaplicado impejg");
		sql4.SELECT("0 impsoj");

		sql4.FROM("fcs_fact_ejg");

		SQL sql5 = new SQL();
		sql5.SELECT("idpersona");
		sql5.SELECT("idinstitucion");
		sql5.SELECT("idfacturacion");
		sql5.SELECT("0");
		sql5.SELECT("0 impoficio");
		sql5.SELECT("0");
		sql5.SELECT("precioaplicado impsoj");

		sql5.FROM("fcs_fact_soj");

		sql.FROM(
				"((" + sql2 + ") UNION ALL (" + sql3 + ") UNION ALL (" + sql4 + ") UNION ALL (" + sql5 + ")) importes");
		sql.FROM("cen_persona per");
		sql.FROM("cen_colegiado col");
		sql.FROM("fcs_facturacionjg fac");
		sql.FROM("fcs_fact_grupofact_hito grupo");

		sql.WHERE("col.idpersona = importes.idpersona");
		sql.WHERE("col.idinstitucion = importes.idinstitucion");
		sql.WHERE("col.idpersona = per.idpersona");
		sql.WHERE("fac.idfacturacion = importes.idfacturacion");
		sql.WHERE("fac.idinstitucion = importes.idinstitucion");
		sql.WHERE("fac.idinstitucion = grupo.idinstitucion");
		sql.WHERE("fac.idfacturacion = grupo.idfacturacion");
		if (letrado) {
			sql.WHERE("fac.visible = '1'");
		}
		sql.WHERE("fac.idinstitucion = " + idInstitucion);

		if (!UtilidadesString.esCadenaVacia(cartasFacturacionPagosItem.getIdFacturacion())) {
			sql.WHERE("fac.idfacturacion IN ( " + cartasFacturacionPagosItem.getIdFacturacion() + " )");
		}

		if (!UtilidadesString.esCadenaVacia(cartasFacturacionPagosItem.getIdPersona())) {
			sql.WHERE("col.idpersona = " + cartasFacturacionPagosItem.getIdPersona());
		}

		if (!UtilidadesString.esCadenaVacia(cartasFacturacionPagosItem.getIdTurno())) {
			sql.WHERE("grupo.IDGRUPOFACTURACION IN ( " + cartasFacturacionPagosItem.getIdTurno() + " )");
		}

		if (!UtilidadesString.esCadenaVacia(cartasFacturacionPagosItem.getIdConcepto())) {
			sql.WHERE("grupo.IDHITOGENERAL IN ( " + cartasFacturacionPagosItem.getIdConcepto() + " )");
		}

		if (!UtilidadesString.esCadenaVacia(cartasFacturacionPagosItem.getIdPartidaPresupuestaria())) {
			sql.WHERE("fac.IDPARTIDAPRESUPUESTARIA IN ( " + cartasFacturacionPagosItem.getIdPartidaPresupuestaria()
					+ " )");
		}

		sql.GROUP_BY(
				"importes.idpersona, col.ncolegiado,col.comunitario,col.ncomunitario, fac.nombre, fac.fechadesde, fac.fechahasta,fac.idfacturacion,per.nombre,per.apellidos1,per.apellidos2");
		sql.ORDER_BY("fac.fechadesde desc,per.apellidos1,per.apellidos2,per.nombre");

		sqlOrder.FROM("(" + sql + " )");
		if (tamMax != null) {
			Integer tamMaxNumber = tamMax + 1;
			sqlOrder.WHERE("rownum <= " + tamMaxNumber);
		}

		return sqlOrder.toString();
	}

	public String datosPagos(String idFacturacion, String idInstitucion, String idLenguaje) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();
		SQL sql3 = new SQL();

		sql3.SELECT("rec.descripcion");
		sql3.FROM("gen_recursos_catalogos rec");
		sql3.WHERE("rec.idrecurso = concept.descripcion");
		sql3.WHERE("rec.idlenguaje = '" + idLenguaje + "'");

		sql2.SELECT("MAX(est2.fechaestado)");
		sql2.FROM("fcs_pagos_estadospagos est2");
		sql2.WHERE("est2.idinstitucion = est.idinstitucion");
		sql2.WHERE("est2.idpagosjg = est.idpagosjg");

		sql.SELECT("pjg.idinstitucion");
		sql.SELECT("pjg.idpagosjg");
		sql.SELECT("pjg.idfacturacion");
		sql.SELECT("pjg.nombre");
		sql.SELECT("(" + sql3.toString() + ") desconcepto");
		sql.SELECT("concept.idhitogeneral");
		sql.SELECT("pjg.importeejg");
		sql.SELECT("pjg.importeguardia");
		sql.SELECT("pjg.importeoficio");
		sql.SELECT("pjg.importesoj");
		sql.SELECT("pjg.importerepartir");
		sql.SELECT("pjg.importepagado");
		sql.SELECT(
				"(CASE WHEN factgrupo.IDHITOGENERAL = 10 THEN ROUND((PJG.IMPORTEOFICIO / FAC.IMPORTEOFICIO) * 100 , 2)"
						+ "WHEN factgrupo.IDHITOGENERAL = 20 THEN ROUND((PJG.IMPORTEGUARDIA / FAC.IMPORTEGUARDIA) * 100 , 2)"
						+ "WHEN factgrupo.IDHITOGENERAL = 30 THEN ROUND((PJG.IMPORTESOJ / FAC.IMPORTESOJ) * 100 , 2)"
						+ "WHEN factgrupo.IDHITOGENERAL = 40 THEN ROUND((PJG.IMPORTEEJG / FAC.IMPORTEEJG) * 100 , 2)"
						+ "END )  porcentaje");
		sql.SELECT("est.fechaestado");
		sql.SELECT("rec.descripcion desestado");
		sql.FROM("fcs_pagosjg pjg");
		sql.INNER_JOIN(
				"fcs_pagos_estadospagos est ON (pjg.idinstitucion = est.idinstitucion AND pjg.idpagosjg = est.idpagosjg)");
		sql.INNER_JOIN("fcs_estadospagos estpagos ON (est.idestadopagosjg = estpagos.idestadopagosjg)");
		sql.INNER_JOIN(
				"fcs_fact_grupofact_hito factgrupo ON (pjg.idfacturacion = factgrupo.idfacturacion AND pjg.idinstitucion = factgrupo.idinstitucion)");
		sql.INNER_JOIN("fcs_hitogeneral concept ON (factgrupo.idhitogeneral = concept.idhitogeneral)");
		sql.INNER_JOIN("gen_recursos_catalogos rec ON (estpagos.descripcion = rec.idrecurso)");
		sql.INNER_JOIN("FCS_FACTURACIONJG fac ON (fac.IDFACTURACION = factgrupo.IDFACTURACION)");
		sql.WHERE("est.fechaestado = (" + sql2.toString() + ")");
		sql.WHERE("fac.idinstitucion = '" + idInstitucion + "'");
		sql.WHERE("fac.idfacturacion =  '" + idFacturacion + "'");
		sql.WHERE("rec.idlenguaje = '" + idLenguaje + "'");
		sql.WHERE("pjg.idinstitucion = '" + idInstitucion + "'");
		sql.ORDER_BY("pjg.IDPAGOSJG DESC");
		sql.ORDER_BY("desconcepto");

		return sql.toString();
	}

	public String buscarCartaspago(CartasFacturacionPagosItem cartasFacturacionPagosItem, Short idInstitucion,
			String idLenguaje, Integer tamMax) {

		SQL sql = new SQL();
		SQL sqlOrder = new SQL();

		sqlOrder.SELECT("*");

		sql.SELECT("idpersonasjcs");
		sql.SELECT("idpagos");
		sql.SELECT("nombrepago");
		sql.SELECT("totalimportesjcs");
		sql.SELECT("importetotalretenciones");
		sql.SELECT("importetotalmovimientos");
		sql.SELECT("totalimportebruto");
		sql.SELECT("totalimporteirpf");
		sql.SELECT("importetotal");
		sql.SELECT("idinstitucion");

		SQL sqlFormadepago = new SQL();
		sqlFormadepago.SELECT("descripcion");
		sqlFormadepago.FROM("gen_recursos_catalogos");
		sqlFormadepago.WHERE("idrecurso = formadepago");
		sqlFormadepago.WHERE("idlenguaje = '" + idLenguaje + "'");

		sql.SELECT("(" + sqlFormadepago + ") as formadepago");
		sql.SELECT("ncolegiado");
		sql.SELECT("nombrecol");
		sql.SELECT("nombredest");

		SQL sql2 = new SQL();
		sql2.SELECT("pagoacolegiados.*");
		sql2.SELECT("decode(col.comunitario, 1, col.ncomunitario, col.ncolegiado) ncolegiado");

		SQL sqlNombreCol = new SQL();
		sqlNombreCol.SELECT("p.apellidos1 || p.apellidos2 || ', ' || p.nombre");
		sqlNombreCol.FROM("cen_persona p");
		sqlNombreCol.FROM("cen_cliente c");
		sqlNombreCol.WHERE("p.idpersona = c.idpersona");
		sqlNombreCol.WHERE("c.idinstitucion = pagoacolegiados.idinstitucion");
		sqlNombreCol.WHERE("c.idpersona = pagoacolegiados.idpersonasjcs");

		sql2.SELECT("(" + sqlNombreCol + ") AS nombrecol");

		SQL sqlNombreDest = new SQL();
		sqlNombreDest.SELECT("p.apellidos1 || p.apellidos2 || ', ' || p.nombre");
		sqlNombreDest.FROM("cen_persona p");
		sqlNombreDest.FROM("cen_cliente c");
		sqlNombreDest.WHERE("p.idpersona = c.idpersona");
		sqlNombreDest.WHERE("c.idinstitucion = pagoacolegiados.idinstitucion");
		sqlNombreDest.WHERE("c.idpersona = pagoacolegiados.idpersonasjcs");

		sql2.SELECT("(" + sqlNombreDest + ")  AS nombredest ");

		SQL sqlPagoacolegiados = new SQL();

		sqlPagoacolegiados.SELECT("pc.idperorigen AS idpersonasjcs");
		sqlPagoacolegiados.SELECT("pc.idperdestino AS idpersonadest");
		sqlPagoacolegiados.SELECT("pc.idpagosjg AS idpagos");
		sqlPagoacolegiados.SELECT("pj.nombre AS nombrepago");
		sqlPagoacolegiados.SELECT("SUM(pc.impoficio + pc.impasistencia + pc.impejg + pc.impsoj) AS totalimportesjcs");
		sqlPagoacolegiados.SELECT("SUM(pc.impret) AS importetotalretenciones");
		sqlPagoacolegiados.SELECT("SUM(pc.impmovvar) AS importetotalmovimientos");
		sqlPagoacolegiados.SELECT(
				"SUM(pc.impoficio + pc.impasistencia + pc.impejg + pc.impsoj + pc.impmovvar) AS totalimportebruto");
		sqlPagoacolegiados.SELECT("SUM(pc.impirpf) AS totalimporteirpf");
		sqlPagoacolegiados.SELECT(
				"( SUM(pc.impoficio + pc.impasistencia + pc.impejg + pc.impsoj + pc.impmovvar) + ( SUM(pc.impirpf) ) + ( SUM(pc.impret) ) ) AS importetotal");
		sqlPagoacolegiados.SELECT("pc.idinstitucion");

		SQL sqlFormadepagoDecode = new SQL();
		sqlFormadepagoDecode.SELECT("a.idcuenta");
		sqlFormadepagoDecode.FROM("fac_abono a");
		sqlFormadepagoDecode.WHERE("idperdestino = a.idpersona");
		sqlFormadepagoDecode.WHERE("pc.idinstitucion = a.idinstitucion");
		sqlFormadepagoDecode.WHERE("pc.idpagosjg = a.idpagosjg");
		sqlFormadepagoDecode.WHERE("ROWNUM = 1");

		sqlPagoacolegiados.SELECT("decode(( " + sqlFormadepagoDecode
				+ " ), NULL, 'gratuita.pagos.porCaja', 'gratuita.pagos.porBanco') AS formadepago");
		sqlPagoacolegiados.FROM("fcs_pago_colegiado pc");
		sqlPagoacolegiados.FROM("cen_persona cen");
		sqlPagoacolegiados.FROM("fcs_pagosjg pj");
		sqlPagoacolegiados.FROM("fcs_facturacionjg fac");

		sqlPagoacolegiados.WHERE("pc.idinstitucion = " + idInstitucion);

		if (!UtilidadesString.esCadenaVacia(cartasFacturacionPagosItem.getIdPago())) {
			sqlPagoacolegiados.WHERE("pc.idpagosjg IN ( " + cartasFacturacionPagosItem.getIdPago() + " )");
		}

		if (!UtilidadesString.esCadenaVacia(cartasFacturacionPagosItem.getIdTurno())
				|| !UtilidadesString.esCadenaVacia(cartasFacturacionPagosItem.getIdConcepto())) {
			SQL sql3 = new SQL();
			sql3.SELECT("1");
			sql3.FROM("fcs_fact_grupofact_hito grupo");
			sql3.WHERE("fac.idinstitucion = grupo.idinstitucion AND fac.idfacturacion = grupo.idfacturacion");

			if (!UtilidadesString.esCadenaVacia(cartasFacturacionPagosItem.getIdTurno())) {
				sql3.WHERE("grupo.IDGRUPOFACTURACION IN ( " + cartasFacturacionPagosItem.getIdTurno() + " )");
			}

			if (!UtilidadesString.esCadenaVacia(cartasFacturacionPagosItem.getIdConcepto())) {
				sql3.WHERE("grupo.IDHITOGENERAL IN ( " + cartasFacturacionPagosItem.getIdConcepto() + " )");
			}

			sqlPagoacolegiados.WHERE("EXISTS (" + sql3.toString() + ")");
		}

		if (!UtilidadesString.esCadenaVacia(cartasFacturacionPagosItem.getIdPartidaPresupuestaria())) {
			sqlPagoacolegiados.WHERE("fac.IDPARTIDAPRESUPUESTARIA IN ( "
					+ cartasFacturacionPagosItem.getIdPartidaPresupuestaria() + " )");
		}

		sqlPagoacolegiados.WHERE("pc.idperorigen = nvl(NULL, pc.idperorigen)");
		sqlPagoacolegiados.WHERE("cen.idpersona = nvl(NULL, pc.idperorigen)");
		sqlPagoacolegiados.WHERE("pj.idinstitucion = pc.idinstitucion");
		sqlPagoacolegiados.WHERE("pj.idpagosjg = pc.idpagosjg");
		sqlPagoacolegiados.WHERE("pj.idinstitucion = fac.idinstitucion");
		sqlPagoacolegiados.WHERE("pj.idfacturacion = fac.idfacturacion");

		sqlPagoacolegiados.GROUP_BY("cen.apellidos1");
		sqlPagoacolegiados.GROUP_BY("cen.apellidos2");
		sqlPagoacolegiados.GROUP_BY("pc.idperorigen");
		sqlPagoacolegiados.GROUP_BY("pc.idperdestino");
		sqlPagoacolegiados.GROUP_BY("pc.idpagosjg");
		sqlPagoacolegiados.GROUP_BY("pc.idinstitucion");
		sqlPagoacolegiados.GROUP_BY("pj.nombre");
		sqlPagoacolegiados.GROUP_BY("pj.fechadesde");

		sqlPagoacolegiados.ORDER_BY("cen.apellidos1");
		sqlPagoacolegiados.ORDER_BY("cen.apellidos2");
		sqlPagoacolegiados.ORDER_BY("pj.fechadesde");

		sql2.FROM("(" + sqlPagoacolegiados + ") pagoacolegiados");
		sql2.FROM("cen_colegiado col");
		sql2.WHERE("pagoacolegiados.idinstitucion = col.idinstitucion");
		sql2.WHERE("pagoacolegiados.idpersonasjcs = col.idpersona");

		sql.FROM("(" + sql2 + ")");
		sql.WHERE("totalimportesjcs > 0");

		if (!UtilidadesString.esCadenaVacia(cartasFacturacionPagosItem.getIdPersona())) {

			SQL sqlIdpersonasjcs = new SQL();
			sqlIdpersonasjcs.SELECT("pers.idpersona");
			sqlIdpersonasjcs.FROM("cen_persona pers");
			sqlIdpersonasjcs.WHERE("pers.idpersona =" + cartasFacturacionPagosItem.getIdPersona());
			sql.WHERE("idpersonasjcs IN (" + sqlIdpersonasjcs + ")");
		}

		sql.ORDER_BY("nombrecol");

		sqlOrder.FROM("(" + sql + " )");
		if (tamMax != null) {
			Integer tamMaxNumber = tamMax + 1;
			sqlOrder.WHERE("rownum <= " + tamMaxNumber);
		}

		return sqlOrder.toString();
	}

	public String facturacionesPorEstadoEjecucion(String idInstitucion) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();

		sql.SELECT("FAC.IDFACTURACION");
		sql.SELECT("FAC.IDINSTITUCION");
		sql.SELECT("FAC.FECHADESDE");
		sql.SELECT("FAC.FECHAHASTA");
		sql.SELECT("FAC.NOMBRE");
		sql.SELECT("FAC.IMPORTETOTAL");
		sql.SELECT("FAC.IMPORTEOFICIO");
		sql.SELECT("FAC.IMPORTEGUARDIA");
		sql.SELECT("FAC.IMPORTESOJ");
		sql.SELECT("FAC.IMPORTEEJG");
		sql.SELECT("FAC.PREVISION");
		sql.SELECT("FAC.REGULARIZACION");
		sql.SELECT("FAC.FECHAMODIFICACION");
		sql.SELECT("FAC.USUMODIFICACION");
		sql.SELECT("FAC.IDFACTURACION_REGULARIZA");
		sql.SELECT("FAC.NOMBREFISICO");
		sql.SELECT("FAC.IDECOMCOLA");
		sql.SELECT("FAC.VISIBLE");
		sql.SELECT("FAC.IDPARTIDAPRESUPUESTARIA");
		sql.FROM("FCS_FACTURACIONJG FAC");
		sql.JOIN(
				"FCS_FACT_ESTADOSFACTURACION E ON (FAC.IDINSTITUCION = E.IDINSTITUCION AND FAC.IDFACTURACION = E.IDFACTURACION)");
		sql.WHERE("E.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("E.IDESTADOFACTURACION = " + ESTADO_FACTURACION.ESTADO_FACTURACION_EN_EJECUCION.getCodigo());
		sql2.SELECT("MAX(EST2.IDORDENESTADO)");
		sql2.FROM("FCS_FACT_ESTADOSFACTURACION EST2 ");
		sql2.WHERE("EST2.IDINSTITUCION = E.IDINSTITUCION");
		sql2.WHERE("EST2.IDFACTURACION = E.IDFACTURACION");
		sql.WHERE("E.IDORDENESTADO = (" + sql2.toString() + ")");

		return sql.toString();
	}

	public String facturacionesPorEstadoProgramadas(String idInstitucion) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();

		sql.SELECT("FAC.IDFACTURACION");
		sql.SELECT("FAC.IDINSTITUCION");
		sql.SELECT("FAC.FECHADESDE");
		sql.SELECT("FAC.FECHAHASTA");
		sql.SELECT("FAC.NOMBRE");
		sql.SELECT("FAC.IMPORTETOTAL");
		sql.SELECT("FAC.IMPORTEOFICIO");
		sql.SELECT("FAC.IMPORTEGUARDIA");
		sql.SELECT("FAC.IMPORTESOJ");
		sql.SELECT("FAC.IMPORTEEJG");
		sql.SELECT("FAC.PREVISION");
		sql.SELECT("FAC.REGULARIZACION");
		sql.SELECT("FAC.FECHAMODIFICACION");
		sql.SELECT("FAC.USUMODIFICACION");
		sql.SELECT("FAC.IDFACTURACION_REGULARIZA");
		sql.SELECT("FAC.NOMBREFISICO");
		sql.SELECT("FAC.IDECOMCOLA");
		sql.SELECT("FAC.VISIBLE");
		sql.SELECT("FAC.IDPARTIDAPRESUPUESTARIA");
		sql.FROM("FCS_FACTURACIONJG FAC");
		sql.JOIN(
				"FCS_FACT_ESTADOSFACTURACION E ON (FAC.IDINSTITUCION = E.IDINSTITUCION AND FAC.IDFACTURACION = E.IDFACTURACION)");
		sql.WHERE("E.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("E.IDESTADOFACTURACION = " + ESTADO_FACTURACION.ESTADO_FACTURACION_PROGRAMADA.getCodigo());
		sql2.SELECT("MAX(EST2.IDORDENESTADO)");
		sql2.FROM("FCS_FACT_ESTADOSFACTURACION EST2 ");
		sql2.WHERE("EST2.IDINSTITUCION = E.IDINSTITUCION");
		sql2.WHERE("EST2.IDFACTURACION = E.IDFACTURACION");
		sql.WHERE("E.IDORDENESTADO = (" + sql2.toString() + ")");

		return sql.toString();
	}

	public String ultimoEstadoFacturacion(String idFacturacion, String idInstitucion) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();

		sql.SELECT("est.idestadofacturacion idestadofacturacion");
		sql.SELECT("est.idinstitucion idinstitucion");
		sql.SELECT("est.idfacturacion idfacturacion");
		sql.SELECT("est.fechaestado fechaestado");
		sql.FROM("fcs_fact_estadosfacturacion est");
		sql.JOIN("fcs_estadosfacturacion estados on (est.idestadofacturacion = estados.idestadofacturacion)");
		sql.WHERE("est.idinstitucion = '" + idInstitucion + "'");
		sql.WHERE("est.idfacturacion = '" + idFacturacion + "'");
		sql2.SELECT("MAX(EST2.IDORDENESTADO)");
		sql2.FROM("FCS_FACT_ESTADOSFACTURACION EST2 ");
		sql2.WHERE("EST2.IDINSTITUCION = EST.IDINSTITUCION");
		sql2.WHERE("EST2.IDFACTURACION = EST.IDFACTURACION");
		sql.WHERE("EST.IDORDENESTADO = (" + sql2.toString() + ")");
		sql.ORDER_BY("fechaestado DESC");

		return sql.toString();
	}

	public String getParametroInstitucion(String idInstitucion, String parametro) {
		SQL sql = new SQL();

		sql.SELECT("VALOR");
		sql.FROM("gen_parametros");
		sql.WHERE("PARAMETRO = " + parametro);
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		return sql.toString();
	}

	public String comboFacturaciones(String idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT("F.IDFACTURACION");
		sql.SELECT(
				"TO_CHAR(F.FECHADESDE, 'DD/MM/YYYY') || '-' || TO_CHAR(F.FECHAHASTA, 'DD/MM/YYYY') || ' - ' || F.NOMBRE AS DESCRIPCION");
		sql.FROM("FCS_FACTURACIONJG F");
		sql.JOIN(
				"FCS_FACT_ESTADOSFACTURACION E ON F.IDINSTITUCION = E.IDINSTITUCION AND F.IDFACTURACION = E.IDFACTURACION");
		sql.WHERE("F.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("E.IDESTADOFACTURACION = 30");
		sql.WHERE("PKG_SIGA_PAGOS_SJCS.FUN_FCS_FACTURACION_POR_PAGAR(F.IDINSTITUCION, F.IDFACTURACION) = '1'");
		sql.ORDER_BY("F.FECHADESDE DESC");

		return sql.toString();

	}

	public String comboFactMovimientos(String idInstitucion) {

		SQL subquery2 = new SQL();
		subquery2.SELECT("MAX(EST2.IDORDENESTADO)");
		subquery2.FROM("FCS_FACT_ESTADOSFACTURACION EST2");
		subquery2.WHERE("EST2.IDINSTITUCION = EST.IDINSTITUCION");
		subquery2.WHERE("EST2.IDFACTURACION = EST.IDFACTURACION");

		SQL sql = new SQL();
		sql.SELECT("F.IDFACTURACION AS id");
		sql.SELECT(
				"TO_CHAR(F.FECHADESDE, 'DD/MM/YYYY') || '-' || TO_CHAR(F.FECHAHASTA, 'DD/MM/YYYY') || ' - ' || F.NOMBRE AS DESCRIPCION");
		sql.FROM("FCS_FACTURACIONJG F");
		sql.FROM("FCS_FACT_ESTADOSFACTURACION EST");
		sql.WHERE("F.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("(F.PREVISION IS NULL OR F.PREVISION='0')");
		sql.WHERE("F.IDINSTITUCION = EST.IDINSTITUCION");
		sql.WHERE("F.IDFACTURACION = EST.IDFACTURACION");
		sql.WHERE("EST.IDORDENESTADO = (" + subquery2 + ")");
		sql.WHERE("EST.IDESTADOFACTURACION IN (20,30)");
		sql.ORDER_BY("F.FECHADESDE DESC");

		return sql.toString();
	}

	public String comboAgrupacionEnTurnos(String idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT("IDGRUPOFACTURACION as ID");
		sql.SELECT("substr(f_siga_getrecurso (NOMBRE,1),0,25) as DESCRIPCION");
		sql.FROM("SCS_GRUPOFACTURACION");
		sql.WHERE("IDINSTITUCION ='" + idInstitucion + "'");
		sql.ORDER_BY("DESCRIPCION");

		return sql.toString();
	}

	public String getFacturacionesPorActuacionDesigna(Short idInstitucion, ScsActuaciondesigna scsActuaciondesigna,
			String literal) {

		SQL sql = new SQL();
		sql.SELECT_DISTINCT("FAC.IDFACTURACION");
		sql.SELECT("FAC.NOMBRE");
		sql.SELECT("'" + literal + "' TIPO");
		sql.SELECT("F.IMPORTEFACTURADO AS IMPORTE");
		sql.SELECT("FAC.IDPARTIDAPRESUPUESTARIA");
		sql.SELECT("FAC.IMPORTEOFICIO");
		sql.FROM("FCS_FACT_ACTUACIONDESIGNA F");
		sql.INNER_JOIN(
				"FCS_FACTURACIONJG FAC ON FAC.IDFACTURACION = F.IDFACTURACION AND  FAC.IDINSTITUCION = F.IDINSTITUCION");
		sql.WHERE("FAC.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("F.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("F.NUMEROASUNTO = " + scsActuaciondesigna.getNumeroasunto());
		sql.WHERE("F.NUMERO = " + scsActuaciondesigna.getNumero());
		sql.WHERE("F.ANIO = " + scsActuaciondesigna.getAnio());
		sql.WHERE("F.IDTURNO = " + scsActuaciondesigna.getIdturno());

		return sql.toString();
	}

	public String getFacturacionesPorAsistencia(Short idInstitucion, ScsAsistencia scsAsistencia, String literal) {

		SQL sql = new SQL();
		sql.SELECT("FAC.IDFACTURACION");
		sql.SELECT("FAC.NOMBRE");
		sql.SELECT("'" + literal + "' TIPO");
		sql.SELECT("F.PRECIOAPLICADO IMPORTE");
		sql.SELECT("FAC.IDPARTIDAPRESUPUESTARIA");
		sql.SELECT("FAC.IMPORTEGUARDIA AS IMPORTEGUARDIA");
		sql.FROM("FCS_FACT_ASISTENCIA F");
		sql.INNER_JOIN(
				"FCS_FACTURACIONJG FAC ON FAC.IDFACTURACION = F.IDFACTURACION AND F.IDINSTITUCION = FAC.IDINSTITUCION");
		sql.WHERE("F.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("F.ANIO = " + scsAsistencia.getAnio());
		sql.WHERE("F.NUMERO = " + scsAsistencia.getNumero());
		sql.ORDER_BY("F.IDINSTITUCION, F.IDFACTURACION");

		SQL subQuery = new SQL();
		subQuery.SELECT("IDFACTURACION");
		subQuery.SELECT("NOMBRE");
		subQuery.SELECT("TIPO");
		subQuery.SELECT("SUM(IMPORTE) AS IMPORTE");
		subQuery.SELECT("IDPARTIDAPRESUPUESTARIA");
		subQuery.SELECT("IMPORTEGUARDIA");
		subQuery.FROM("(" + sql.toString() + ")");
		subQuery.GROUP_BY("IDFACTURACION,NOMBRE,TIPO,IDPARTIDAPRESUPUESTARIA,IMPORTEGUARDIA");

		return subQuery.toString();
	}

	public String getFacturacionesPorActuacionAsistencia(Short idInstitucion,
			ScsActuacionasistencia scsActuacionasistencia, String literal) {

		SQL sql = new SQL();
		sql.SELECT("FAC.IDFACTURACION");
		sql.SELECT("FAC.NOMBRE");
		sql.SELECT("'" + literal + "' TIPO");
		sql.SELECT("(F.PRECIOAPLICADO + F.PRECIOCOSTESFIJOS)  IMPORTE");
		sql.SELECT("FAC.IDPARTIDAPRESUPUESTARIA");
		sql.SELECT("FAC.IMPORTEGUARDIA AS IMPORTEGUARDIA");
		sql.FROM("FCS_FACT_ACTUACIONASISTENCIA F");
		sql.INNER_JOIN(
				"FCS_FACTURACIONJG FAC ON FAC.IDFACTURACION = F.IDFACTURACION AND F.IDINSTITUCION = FAC.IDINSTITUCION");
		sql.WHERE("F.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("F.ANIO = " + scsActuacionasistencia.getAnio());
		sql.WHERE("F.NUMERO = " + scsActuacionasistencia.getNumero());
		sql.WHERE("F.IDACTUACION = " + scsActuacionasistencia.getIdactuacion());
		sql.ORDER_BY("F.IDINSTITUCION, F.IDFACTURACION");

		SQL subQuery = new SQL();
		subQuery.SELECT("IDFACTURACION");
		subQuery.SELECT("NOMBRE");
		subQuery.SELECT("TIPO");
		subQuery.SELECT("SUM(IMPORTE) AS IMPORTE");
		subQuery.SELECT("IDPARTIDAPRESUPUESTARIA");
		subQuery.SELECT("IMPORTEGUARDIA");
		subQuery.FROM("(" + sql.toString() + ")");
		subQuery.GROUP_BY("IDFACTURACION,NOMBRE,TIPO,IDPARTIDAPRESUPUESTARIA,IMPORTEGUARDIA");

		return subQuery.toString();
	}

	public String getFacturacionesPorGuardia(Short idInstitucion, ScsCabeceraguardias scsCabeceraguardias,
			String literal) {
		SQL sql = new SQL();
		sql.SELECT("FAC.IDFACTURACION");
		sql.SELECT("FAC.NOMBRE");
		sql.SELECT("'" + literal + "' TIPO");
		sql.SELECT("(F.PRECIOAPLICADO + F.PRECIOCOSTESFIJOS ) IMPORTE");
		sql.SELECT("FAC.IDPARTIDAPRESUPUESTARIA");
		sql.SELECT("FAC.IMPORTEGUARDIA AS IMPORTEGUARDIA");
		sql.FROM("FCS_FACT_APUNTE F");
		sql.INNER_JOIN(
				"FCS_FACTURACIONJG FAC ON FAC.IDFACTURACION = F.IDFACTURACION AND F.IDINSTITUCION = FAC.IDINSTITUCION");
		sql.WHERE("F.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("F.IDTURNO = " + scsCabeceraguardias.getIdturno());
		sql.WHERE("F.IDGUARDIA = " + scsCabeceraguardias.getIdguardia());
		sql.WHERE("F.IDPERSONA = " + scsCabeceraguardias.getIdpersona());
		if (null != scsCabeceraguardias.getFechainicio()) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String fechaF = dateFormat.format(scsCabeceraguardias.getFechainicio());
			sql.WHERE("F.FECHAINICIO = TO_DATE('" + fechaF + "', 'DD/MM/YYYY hh24:mi:ss')");
		}
		sql.ORDER_BY("F.IDINSTITUCION, F.IDFACTURACION");

		SQL subQuery = new SQL();
		subQuery.SELECT("IDFACTURACION");
		subQuery.SELECT("NOMBRE");
		subQuery.SELECT("TIPO");
		subQuery.SELECT("SUM(IMPORTE) AS IMPORTE");
		subQuery.SELECT("IDPARTIDAPRESUPUESTARIA");
		subQuery.SELECT("IMPORTEGUARDIA");
		subQuery.FROM("(" + sql.toString() + ")");
		subQuery.GROUP_BY("IDFACTURACION,NOMBRE,TIPO,IDPARTIDAPRESUPUESTARIA,IMPORTEGUARDIA");

		return subQuery.toString();
	}

	public String getFacturacionesPorEJG(Short idInstitucion, ScsEjg scsEjg, String literal) {

		SQL sql = new SQL();
		sql.SELECT("FAC.IDFACTURACION");
		sql.SELECT("FAC.NOMBRE");
		sql.SELECT("'" + literal + "' TIPO");
		sql.SELECT("F.PRECIOAPLICADO IMPORTE");
		sql.SELECT("FAC.IDPARTIDAPRESUPUESTARIA");
		sql.SELECT("FAC.IMPORTEEJG AS IMPORTEEJG");
		sql.FROM("FCS_FACT_EJG F");
		sql.INNER_JOIN(
				"FCS_FACTURACIONJG FAC ON FAC.IDFACTURACION = F.IDFACTURACION AND F.IDINSTITUCION = FAC.IDINSTITUCION");
		sql.WHERE("F.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("F.IDTIPOEJG = " + scsEjg.getIdtipoejg());
		sql.WHERE("F.ANIO = " + scsEjg.getAnio());
		sql.WHERE("F.NUMERO = " + scsEjg.getNumero());
		sql.ORDER_BY("F.IDINSTITUCION, F.IDFACTURACION");

		SQL subQuery = new SQL();
		subQuery.SELECT("IDFACTURACION");
		subQuery.SELECT("NOMBRE");
		subQuery.SELECT("TIPO");
		subQuery.SELECT("SUM(IMPORTE) AS IMPORTE");
		subQuery.SELECT("IDPARTIDAPRESUPUESTARIA");
		subQuery.SELECT("IMPORTEEJG");
		subQuery.FROM("(" + sql.toString() + ")");
		subQuery.GROUP_BY("IDFACTURACION,NOMBRE,TIPO,IDPARTIDAPRESUPUESTARIA,IMPORTEEJG");

		return subQuery.toString();
	}

	public String facturacionesPorEstadoEjecucionTiempoLimite(String idInstitucion, Integer tiempoMaximo) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();

		sql.SELECT("FAC.IDFACTURACION");
		sql.SELECT("FAC.IDINSTITUCION");
		sql.SELECT("FAC.FECHADESDE");
		sql.SELECT("FAC.FECHAHASTA");
		sql.SELECT("FAC.NOMBRE");
		sql.SELECT("FAC.IMPORTETOTAL");
		sql.SELECT("FAC.IMPORTEOFICIO");
		sql.SELECT("FAC.IMPORTEGUARDIA");
		sql.SELECT("FAC.IMPORTESOJ");
		sql.SELECT("FAC.IMPORTEEJG");
		sql.SELECT("FAC.PREVISION");
		sql.SELECT("FAC.REGULARIZACION");
		sql.SELECT("FAC.FECHAMODIFICACION");
		sql.SELECT("FAC.USUMODIFICACION");
		sql.SELECT("FAC.IDFACTURACION_REGULARIZA");
		sql.SELECT("FAC.NOMBREFISICO");
		sql.SELECT("FAC.IDECOMCOLA");
		sql.SELECT("FAC.VISIBLE");
		sql.SELECT("FAC.IDPARTIDAPRESUPUESTARIA");
		sql.FROM("FCS_FACTURACIONJG FAC");
		sql.JOIN(
				"FCS_FACT_ESTADOSFACTURACION E ON (FAC.IDINSTITUCION = E.IDINSTITUCION AND FAC.IDFACTURACION = E.IDFACTURACION)");
		sql.WHERE("E.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("E.IDESTADOFACTURACION = " + ESTADO_FACTURACION.ESTADO_FACTURACION_EN_EJECUCION.getCodigo());
		sql.WHERE("FAC.FECHAMODIFICACION = " + "SYSDATE - " + tiempoMaximo + "/1440");
		sql2.SELECT("MAX(EST2.IDORDENESTADO)");
		sql2.FROM("FCS_FACT_ESTADOSFACTURACION EST2 ");
		sql2.WHERE("EST2.IDINSTITUCION = E.IDINSTITUCION");
		sql2.WHERE("EST2.IDFACTURACION = E.IDFACTURACION");
		sql.WHERE("E.IDORDENESTADO = (" + sql2.toString() + ")");

		return sql.toString();
	}
	
	public String getDatosPagoAsuntoPorFacturacionActuacionDesignas(Short idInstitucion, String idFacturacion,
			String literal) {

		SQL sql = new SQL();
		sql.SELECT("IDPAGOSJG");
		sql.SELECT("NOMBRE");
		sql.SELECT("'" + literal + "' TIPO");
		sql.SELECT("IMPORTEOFICIO IMPORTE");
		sql.FROM("FCS_PAGOSJG");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		sql.WHERE("IDFACTURACION = " + idFacturacion);
		sql.WHERE("IMPORTEOFICIO > 0");

		return sql.toString();
	}

	public String getDatosPagoAsuntoPorFacturacionAsistencia(Short idInstitucion, String idFacturacion,
			String literal) {

		SQL sql = new SQL();
		sql.SELECT("IDPAGOSJG");
		sql.SELECT("NOMBRE");
		sql.SELECT("'" + literal + "' TIPO");
		sql.SELECT("IMPORTEGUARDIA IMPORTE");
		sql.FROM("FCS_PAGOSJG");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		sql.WHERE("IDFACTURACION = " + idFacturacion);
		sql.WHERE("IMPORTEGUARDIA > 0");

		return sql.toString();
	}
	
	public String getDatosPagoAsuntoPorFacturacionActuacionesAsistencia(Short idInstitucion, String idFacturacion,
			String literal) {

		SQL sql = new SQL();
		sql.SELECT("IDPAGOSJG");
		sql.SELECT("NOMBRE");
		sql.SELECT("'" + literal + "' TIPO");
		sql.SELECT("IMPORTEGUARDIA IMPORTE");
		sql.FROM("FCS_PAGOSJG");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		sql.WHERE("IDFACTURACION = " + idFacturacion);
		sql.WHERE("IMPORTEGUARDIA > 0");

		return sql.toString();
	}
	
	public String getDatosPagoAsuntoPorFacturacionEjgs(Short idInstitucion, String idFacturacion,
			String literal) {

		SQL sql = new SQL();
		sql.SELECT("IDPAGOSJG");
		sql.SELECT("NOMBRE");
		sql.SELECT("'" + literal + "' TIPO");
		sql.SELECT("IMPORTEEJG IMPORTE");
		sql.FROM("FCS_PAGOSJG");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		sql.WHERE("IDFACTURACION = " + idFacturacion);
		sql.WHERE("IMPORTEEJG > 0");

		return sql.toString();
	}
	
	public String getDatosPagoAsuntoPorFacturacionGuardiasColegiado(Short idInstitucion, String idFacturacion,
			String literal) {

		SQL sql = new SQL();
		sql.SELECT("IDPAGOSJG");
		sql.SELECT("NOMBRE");
		sql.SELECT("'" + literal + "' TIPO");
		sql.SELECT("IMPORTEGUARDIA IMPORTE");
		sql.FROM("FCS_PAGOSJG");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		sql.WHERE("IDFACTURACION = " + idFacturacion);
		sql.WHERE("IMPORTEGUARDIA > 0");

		return sql.toString();
	}

	public String getDatosPagoAsuntoPorMovimientoVario(Short idInstitucion, String idMovimiento, String literal) {

		SQL sql = new SQL();
		sql.SELECT("P.IDPAGOSJG");
		sql.SELECT("P.NOMBRE");
		sql.SELECT("'" + literal + "' TIPO");
		sql.SELECT("NVL(P.IMPORTEPAGADO, 0) IMPORTE");
		sql.FROM("FCS_APLICA_MOVIMIENTOSVARIOS AMV");
		sql.JOIN("FCS_PAGOSJG P ON P.IDINSTITUCION = AMV.IDINSTITUCION AND P.IDPAGOSJG = AMV.IDPAGOSJG");
		sql.WHERE("AMV.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("AMV.IDMOVIMIENTO = " + idMovimiento);

		SQL sqlGroup = new SQL();
		sqlGroup.SELECT("*");
		sqlGroup.FROM("(" + sql.toString() + ")");
		sqlGroup.GROUP_BY("IDPAGOSJG");
		sqlGroup.GROUP_BY("NOMBRE");
		sqlGroup.GROUP_BY("TIPO");
		sqlGroup.GROUP_BY("IMPORTE");

		return sqlGroup.toString();
	}

	public String getAgrupacionDeTurnosPorTurno(Short idInstitucion, String idTurno) {

		SQL sql = new SQL();
		sql.SELECT("IDGRUPOFACTURACION");
		sql.FROM("SCS_TURNO");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		sql.WHERE("IDTURNO = " + idTurno);

		return sql.toString();
	}

	public String getFacturacionesCerradasPorInstitucion(Short idInstitucion) {

		SQL subQuery = new SQL();
		subQuery.SELECT("MAX(EST2.IDORDENESTADO)");
		subQuery.FROM("FCS_FACT_ESTADOSFACTURACION EST2");
		subQuery.WHERE("EST2.IDINSTITUCION = EST.IDINSTITUCION");
		subQuery.WHERE("EST2.IDFACTURACION = EST.IDFACTURACION");

		SQL sql = new SQL();
		sql.SELECT("FAC.IDFACTURACION");
		sql.FROM("FCS_FACTURACIONJG FAC");
		sql.JOIN(
				"FCS_FACT_ESTADOSFACTURACION EST ON FAC.IDINSTITUCION = EST.IDINSTITUCION AND FAC.IDFACTURACION = EST.IDFACTURACION");
		sql.WHERE("FAC.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("EST.IDESTADOFACTURACION = 30");
		sql.WHERE("EST.IDORDENESTADO = (" + subQuery.toString() + ")");

		return sql.toString();
	}

	public String comboFactBaremos(String idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT("F.IDFACTURACION");
		sql.SELECT(
				"TO_CHAR(F.FECHADESDE, 'DD/MM/YYYY') || '-' || TO_CHAR(F.FECHAHASTA, 'DD/MM/YYYY') || ' - ' || F.NOMBRE AS DESCRIPCION");
		sql.FROM("FCS_FACTURACIONJG F");
		sql.WHERE("F.IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();

	}
}
