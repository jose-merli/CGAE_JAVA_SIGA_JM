package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.scs.BaremosGuardiaItem;

public class ScsBaremosGuardiaSqlProvider {

	public String searchBaremosGuardia(BaremosGuardiaItem baremosGuardiaItem, Short idinstitucion, Integer tamMaximo) {

		String guardias = "";
		String facturaciones = "";
		String turnos = "";

		StringBuilder filtroDia = new StringBuilder(1000);

		if (baremosGuardiaItem.getIdGuardias() != null) {
			for (int i = 0; i < baremosGuardiaItem.getIdGuardias().length; i++) {
				guardias += baremosGuardiaItem.getIdGuardias()[i].toString();
				if (i < baremosGuardiaItem.getIdGuardias().length - 1)
					guardias += ",";
			}
		}

		if (baremosGuardiaItem.getIdTurnos() != null) {
			for (int i = 0; i < baremosGuardiaItem.getIdTurnos().length; i++) {
				turnos += baremosGuardiaItem.getIdTurnos()[i].toString();
				if (i < baremosGuardiaItem.getIdTurnos().length - 1)
					turnos += ",";
			}
		}
		if (baremosGuardiaItem.getIdFacturaciones() != null) {
			for (int i = 0; i < baremosGuardiaItem.getIdFacturaciones().length; i++) {
				facturaciones += baremosGuardiaItem.getIdFacturaciones()[i].toString();
				if (i < baremosGuardiaItem.getIdFacturaciones().length - 1)
					facturaciones += ",";
			}
		}

		SQL sql = new SQL();
		SQL sqlDisponibilidad = new SQL();
		SQL sqlNum_min_simple = new SQL();
		SQL sqlSimpleImptIndividual = new SQL();
		SQL sqlNapartir = new SQL();
		SQL sqlMaximo = new SQL();
		SQL sqlExisteHito = new SQL();
		
		// Subconsulta para obtener si existe hito
		sqlExisteHito.SELECT("1");
		sqlExisteHito.FROM("SCS_HITOFACTURABLEGUARDIA hit2");
		sqlExisteHito.WHERE("hit2.IDINSTITUCION = hit.IDINSTITUCION");
		sqlExisteHito.WHERE("hit2.IDTURNO = hit.IDTURNO");
		sqlExisteHito.WHERE("hit2.IDGUARDIA = hit.IDGUARDIA");
		sqlExisteHito.WHERE("nvl(hit2.DIASAPLICABLES, 'LMXJVSD') = nvl(hit.DIASAPLICABLES, 'LMXJVSD')");
		sqlExisteHito.WHERE("nvl(hit2.AGRUPAR, '0') = nvl(hit.AGRUPAR, '0')");
		sqlExisteHito.WHERE("hit2.IDHITO in ( 25, 22, 20)))");

		// Subconsulta para obtener la disponibilidad
		sqlDisponibilidad.SELECT(" hit.preciohito");
		if (!facturaciones.equals("0")) {
			sqlDisponibilidad.FROM("fcs_historico_hitofact hit2");
		} else {
			sqlDisponibilidad.FROM("scs_hitofacturableguardia hit2");
		}
		sqlDisponibilidad.WHERE(" hit2.idinstitucion = hit.idinstitucion");
		sqlDisponibilidad.WHERE(" hit2.idturno = hit.idturno");
		sqlDisponibilidad.WHERE(" hit2.idinstitucion = hit.idinstitucion");
		sqlDisponibilidad.WHERE(" hit2.idturno = hit.idturno");
		sqlDisponibilidad.WHERE(" hit2.idguardia = hit.idguardia");
		sqlDisponibilidad.WHERE(" hit2.diasaplicables = hit.diasaplicables");
		sqlDisponibilidad.WHERE(" hit2.agrupar = hit.agrupar");
		if (!facturaciones.equals("0")) {
			sqlDisponibilidad.WHERE("hit2.idfacturacion = hit.idfacturacion");
		}
		sqlDisponibilidad.WHERE(
				" hit2.idhito = CASE hit.idhito" + " WHEN 7    THEN 19 " + " WHEN 22   THEN 19 " + " WHEN 5    THEN 10 "
						+ " WHEN 20   THEN 10 " + " WHEN 44   THEN 54 " + " WHEN 1    THEN 53 " + " END");
		sqlDisponibilidad.WHERE("rownum < 2");

		// Subconsulta para obtener Numeros minimos Simples.
		sqlNum_min_simple.SELECT("hit2.preciohito");
		if (!facturaciones.equals("0")) {
			sqlNum_min_simple.FROM("fcs_historico_hitofact hit2");
		} else {
			sqlNum_min_simple.FROM("scs_hitofacturableguardia hit2");
		}
		sqlNum_min_simple.WHERE("hit2.idinstitucion = hit.idinstitucion");
		sqlNum_min_simple.WHERE("hit2.idturno = hit.idturno");
		sqlNum_min_simple.WHERE("hit2.idguardia = hit.idguardia");
		sqlNum_min_simple.WHERE("hit2.diasaplicables = hit.diasaplicables");
		sqlNum_min_simple.WHERE("hit2.agrupar = hit.agrupar");
		sqlNum_min_simple.WHERE("hit2.idhito = CASE hit.idhito " + " WHEN 44 THEN 56" + " WHEN 1  THEN 55 END");
		sqlNum_min_simple.WHERE("rownum < 2");

		// Subconsulta para obtener Numeros Importe Individual.
		sqlSimpleImptIndividual.SELECT("hit2.preciohito");
		if (!facturaciones.equals("0")) {
			sqlSimpleImptIndividual.FROM("fcs_historico_hitofact hit2");
		} else {
			sqlSimpleImptIndividual.FROM("scs_hitofacturableguardia hit2");
		}
		sqlSimpleImptIndividual.WHERE("hit2.idinstitucion = hit.idinstitucion");
		sqlSimpleImptIndividual.WHERE("hit2.idturno = hit.idturno");
		sqlSimpleImptIndividual.WHERE("hit2.idguardia = hit.idguardia");
		sqlSimpleImptIndividual.WHERE("hit2.agrupar = hit.agrupar");
		if (!facturaciones.equals("0")) {
			sqlSimpleImptIndividual.WHERE("hit2.idfacturacion = hit.idfacturacion");
		}
		sqlSimpleImptIndividual.WHERE("hit2.idhito = CASE hit.idhito" + " WHEN 7  THEN 7" + " WHEN 9  THEN 9"
				+ " WHEN 22 THEN 22" + " WHEN 5  THEN 5" + " WHEN 20 THEN 20 " + " WHEN 44 THEN 44 "
				+ " WHEN 1  THEN 1 " + " WHEN 12 THEN 12 " + " WHEN 13 THEN 13 " + " END ");
		sqlSimpleImptIndividual.WHERE("rownum < 2");

		// Subconsulta para obtener el NAPARTIR.
		sqlNapartir.SELECT("hit2.preciohito");
		if (!facturaciones.equals("0")) {
			sqlNapartir.FROM("fcs_historico_hitofact hit2");
		} else {
			sqlNapartir.FROM("scs_hitofacturableguardia hit2");
		}
		sqlNapartir.WHERE("hit2.idinstitucion = hit.idinstitucion");
		sqlNapartir.WHERE("hit2.idturno = hit.idturno");
		sqlNapartir.WHERE("hit2.idguardia = hit.idguardia");
		sqlNapartir.WHERE("hit2.diasaplicables = hit.diasaplicables");
		sqlNapartir.WHERE("hit2.agrupar = hit.agrupar ");
		if (!facturaciones.equals("0")) {
			sqlSimpleImptIndividual.WHERE("hit2.idfacturacion = hit.idfacturacion");
		}
		sqlNapartir.WHERE("hit2.idhito = CASE hit.idhito " + " WHEN 44 THEN 46" + " WHEN 1  THEN 45" + " END");
		sqlNapartir.WHERE("rownum < 2");

		// Subconsulta para obtener el Maximo.
		sqlMaximo.SELECT("hit2.preciohito");
		if (!facturaciones.equals("0")) {
			sqlMaximo.FROM("fcs_historico_hitofact hit2");
		} else {
			sqlMaximo.FROM("scs_hitofacturableguardia hit2");
		}
		sqlMaximo.WHERE("hit2.idinstitucion = hit.idinstitucion");
		sqlMaximo.WHERE("hit2.idturno = hit.idturno");
		sqlMaximo.WHERE("hit2.idguardia = hit.idguardia");
		sqlMaximo.WHERE("hit2.diasaplicables = hit.diasaplicables");
		sqlMaximo.WHERE("hit2.agrupar = hit.agrupar");
		if (!facturaciones.equals("0")) {
			sqlMaximo.WHERE("hit2.idfacturacion = hit.idfacturacion");
		}
		sqlMaximo.WHERE("hit2.idhito = CASE hit.idhito " + " WHEN 7    THEN 8" + " WHEN 22   THEN 8"
				+ " WHEN 5    THEN 3" + "	WHEN 20   THEN 3" + "	WHEN 9    THEN 6" + "	WHEN 24   THEN 6"
				+ "	WHEN 44   THEN 4" + "	WHEN 1    THEN 2" + "END");
		sqlMaximo.WHERE("rownum < 2");

		// Filtro para obtener los días.
		filtroDia.append("'L: ' "
				+ " || CASE WHEN regexp_count(gua.seleccionlaborables|| hit.diasaplicables,'L') = 2 THEN 'L' " + " END "
				+ " || CASE WHEN regexp_count(gua.seleccionlaborables|| hit.diasaplicables,'M') = 2 THEN 'M' " + " END "
				+ " || CASE WHEN regexp_count(gua.seleccionlaborables|| hit.diasaplicables,'X') = 2 THEN 'X' " + " END "
				+ " || CASE WHEN regexp_count(gua.seleccionlaborables|| hit.diasaplicables,'J') = 2 THEN 'J' " + " END "
				+ " || CASE WHEN regexp_count(gua.seleccionlaborables|| hit.diasaplicables,'V') = 2 THEN 'V' " + " END "
				+ " || CASE WHEN regexp_count(gua.seleccionlaborables|| hit.diasaplicables,'S') = 2 THEN 'S' " + " END "
				+ " || CASE WHEN regexp_count(gua.seleccionlaborables|| hit.diasaplicables,'D') = 2 THEN 'D' "
				+ " END ");

		filtroDia.append(" || ' ' || CHR(10) || 'F: '");
		filtroDia.append(" || CASE WHEN regexp_count(gua.seleccionfestivos|| hit.diasaplicables,'L') = 2 THEN 'L' "
				+ " END " + " || CASE WHEN regexp_count(gua.seleccionfestivos|| hit.diasaplicables,'M') = 2 THEN 'M' "
				+ " END " + " || CASE WHEN regexp_count(gua.seleccionfestivos|| hit.diasaplicables,'X') = 2 THEN 'X' "
				+ " END " + " || CASE WHEN regexp_count(gua.seleccionfestivos|| hit.diasaplicables,'J') = 2 THEN 'J' "
				+ " END " + " || CASE WHEN regexp_count(gua.seleccionfestivos|| hit.diasaplicables,'V') = 2 THEN 'V' "
				+ " END " + " || CASE WHEN regexp_count(gua.seleccionfestivos|| hit.diasaplicables,'S') = 2 THEN 'S' "
				+ " END " + " || CASE WHEN regexp_count(gua.seleccionfestivos|| hit.diasaplicables,'D') = 2 THEN 'D' "
				+ " END ");

		// Consulta para buscar BaremosGuardia
		sql.SELECT("  gua.nombre guardias");
		sql.SELECT("  gua.diasguardia " + "     || ' ' " + "     || gua.tipodiasguardia n_dias");
		sql.SELECT("tip.idhitoconfiguracion");
		sql.SELECT("hit.idturno");
		sql.SELECT("hit.idguardia");
		sql.SELECT("hit.IDHITO");
		sql.SELECT("gua.fechabaja");
		sql.SELECT("hit.IDHITO");
		sql.SELECT("tur.nombre as NOMBRETURNO");
		sql.SELECT("gua.fechabaja");
		sql.SELECT("f_siga_getrecurso( tip.descripcion,1) baremo");
		sql.SELECT(filtroDia.toString() + " dias ");
		sql.SELECT("(" + sqlDisponibilidad.toString() + ") disponibilidad");

		sql.SELECT("(" + sqlNum_min_simple.toString() + ") num_minimo_simple");

		sql.SELECT("(" + sqlSimpleImptIndividual.toString() + ") simple_o_importe_individual");

		sql.SELECT("(" + sqlNapartir.toString() + ") NAPARTIR");

		sql.SELECT("(" + sqlMaximo.toString() + ") maximo");

		sql.SELECT("(" + sqlMaximo.toString() + ") maximo");

		sql.SELECT(" CASE " + " WHEN nvl(hit.agrupar,0) = '1' THEN 'No' ELSE 'Si' END" + " por_dia");

		if (!facturaciones.equals("0")) {
			sql.FROM("FCS_HISTORICO_HITOFACT  hit");
		} else {
			sql.FROM("scs_hitofacturableguardia hit");
		}
		if (!facturaciones.equals("0")) {
			sql.FROM(" scs_hitofacturable tip," + "    scs_guardiasturno gua," + "		scs_turno tur");
		} else {
			sql.FROM(" scs_hitofacturable tip," + "    scs_guardiasturno gua");
		}

		sql.WHERE(" hit.idhito = tip.idhito");
		sql.WHERE(" hit.idinstitucion = gua.idinstitucion");
		sql.WHERE("(hit.idhito IN ( 25, 22, 20, 44, 1 )"
				+ " or ( hit.idhito in ( 7, 9, 5 ) and not exists (" + sqlExisteHito.toString() + ""
				+ " or ( hit.idhito in ( 12, 13 ) and hit.PRECIOHITO > 0 ) )");
		
		sql.WHERE("hit.idinstitucion = " + idinstitucion);
		if (guardias != "") {
			sql.WHERE("gua.idguardia IN( " + guardias + ")");
		}
		if (turnos != "") {
			sql.WHERE("hit.idturno IN( " + turnos + ")");
		}
		if (!facturaciones.equals("0")) {
			sql.WHERE("hit.idfacturacion IN( " + facturaciones + ")");
		}

		if (!baremosGuardiaItem.isHistorico()) {
			sql.WHERE("gua.fechabaja is null");
		}

		if (tamMaximo != null) {
			Integer tamMaxNumber = tamMaximo + 1;
			sql.WHERE("ROWNUM <= " + tamMaxNumber);
		}

		sql.WHERE("hit.idturno = tur.idturno");
		sql.WHERE("hit.idinstitucion = tur.idinstitucion");

		sql.ORDER_BY("hit.idhito, hit.idguardia");

		return sql.toString();
	}

	public String searchBaremosFichaGuardia(String idGuardia, Short idinstitucion, Integer tamMaximo) {

		SQL sql = new SQL();
		SQL sqlFinal = new SQL();

		sql.SELECT("	LISTAGG( " + "        gua.nombre, " + "        ',' "
				+ "    ) WITHIN GROUP(ORDER BY gua.nombre) guardias, " + "    gua.diasguardia " + "     || ' ' "
				+ "     || gua.tipodiasguardia n_dias, " + "	tip.idhitoconfiguracion," + "		hit.idturno,"
				+ "		hit.idguardia," + "     hit.IDHITO,"
				+ "(SELECT NOMBRE FROM SCS_TURNO WHERE IDINSTITUCION = hit.IDINSTITUCION AND IDTURNO = hit.idturno) NOMBRETURNO,"
				+ "		gua.fechabaja," + "    f_siga_getrecurso( " + "        tip.descripcion, " + "        1 "
				+ "    ) baremo, " + "    'L: ' " + "     || CASE " + "            WHEN regexp_count( "
				+ "                gua.seleccionlaborables " + "                 || hit.diasaplicables, "
				+ "                'L' " + "            ) = 2 THEN 'L' " + "        END " + "     || CASE "
				+ "            WHEN regexp_count( " + "                gua.seleccionlaborables "
				+ "                 || hit.diasaplicables, " + "                'M' " + "            ) = 2 THEN 'M' "
				+ "        END " + "     || CASE " + "            WHEN regexp_count( "
				+ "                gua.seleccionlaborables " + "                 || hit.diasaplicables, "
				+ "                'X' " + "            ) = 2 THEN 'X' " + "        END " + "     || CASE "
				+ "            WHEN regexp_count( " + "                gua.seleccionlaborables "
				+ "                 || hit.diasaplicables, " + "                'J' " + "            ) = 2 THEN 'J' "
				+ "        END " + "     || CASE " + "            WHEN regexp_count( "
				+ "                gua.seleccionlaborables " + "                 || hit.diasaplicables, "
				+ "                'V' " + "            ) = 2 THEN 'V' " + "        END " + "     || CASE "
				+ "            WHEN regexp_count( " + "                gua.seleccionlaborables "
				+ "                 || hit.diasaplicables, " + "                'S' " + "            ) = 2 THEN 'S' "
				+ "        END " + "     || CASE " + "            WHEN regexp_count( "
				+ "                gua.seleccionlaborables " + "                 || hit.diasaplicables, "
				+ "                'D' " + "            ) = 2 THEN 'D' " + "        END " + "     || ' ' "
				+ "     || CHR(10) " + "     || 'F: ' " + "     || CASE " + "            WHEN regexp_count( "
				+ "                gua.seleccionfestivos " + "                 || hit.diasaplicables, "
				+ "                'L' " + "            ) = 2 THEN 'L' " + "        END " + "     || CASE "
				+ "            WHEN regexp_count( " + "                gua.seleccionfestivos "
				+ "                 || hit.diasaplicables, " + "                'M' " + "            ) = 2 THEN 'M' "
				+ "        END " + "     || CASE " + "            WHEN regexp_count( "
				+ "                gua.seleccionfestivos " + "                 || hit.diasaplicables, "
				+ "                'X' " + "            ) = 2 THEN 'X' " + "        END " + "     || CASE "
				+ "            WHEN regexp_count( " + "                gua.seleccionfestivos "
				+ "                 || hit.diasaplicables, " + "                'J' " + "            ) = 2 THEN 'J' "
				+ "        END " + "     || CASE " + "            WHEN regexp_count( "
				+ "                gua.seleccionfestivos " + "                 || hit.diasaplicables, "
				+ "                'V' " + "            ) = 2 THEN 'V' " + "        END " + "     || CASE "
				+ "            WHEN regexp_count( " + "                gua.seleccionfestivos "
				+ "                 || hit.diasaplicables, " + "                'S' " + "            ) = 2 THEN 'S' "
				+ "        END " + "     || CASE " + "            WHEN regexp_count( "
				+ "                gua.seleccionfestivos " + "                 || hit.diasaplicables, "
				+ "                'D' " + "            ) = 2 THEN 'D' " + "        END " + "    dias, " + "    ( "
				+ "        SELECT " + "            hit2.preciohito " + "        FROM "
				+ "            scs_hitofacturableguardia hit2 " + "        WHERE "
				+ "                hit2.idinstitucion = hit.idinstitucion " + "            AND "
				+ "                hit2.idturno = hit.idturno " + "            AND "
				+ "                hit2.idguardia = hit.idguardia " + "            AND "
				+ "                hit2.diasaplicables = hit.diasaplicables " + "            AND "
				+ "                hit2.agrupar = hit.agrupar " + "            AND " + "                hit2.idhito = "
				+ "                    CASE hit.idhito " + "                        WHEN 7    THEN 19 "
				+ "                        WHEN 22   THEN 19 " + "                        WHEN 5    THEN 10 "
				+ "                        WHEN 20   THEN 10 " + "                        WHEN 44   THEN 54 "
				+ "                        WHEN 1    THEN 53 " + "                    END " + "    ) disponibilidad, "
				+ "    ( " + "        SELECT " + "            hit2.preciohito " + "        FROM "
				+ "            scs_hitofacturableguardia hit2 " + "        WHERE "
				+ "                hit2.idinstitucion = hit.idinstitucion " + "            AND "
				+ "                hit2.idturno = hit.idturno " + "            AND "
				+ "                hit2.idguardia = hit.idguardia " + "            AND "
				+ "                hit2.diasaplicables = hit.diasaplicables " + "            AND "
				+ "                hit2.agrupar = hit.agrupar " + "            AND " + "                hit2.idhito = "
				+ "                    CASE hit.idhito " + "                        WHEN 44   THEN 56 "
				+ "                        WHEN 1    THEN 55 " + "                    END "
				+ "    ) num_minimo_simple, " + "    ( " + "        SELECT " + "            hit2.preciohito "
				+ "        FROM " + "            scs_hitofacturableguardia hit2 " + "        WHERE "
				+ "                hit2.idinstitucion = hit.idinstitucion " + "            AND "
				+ "                hit2.idturno = hit.idturno " + "            AND "
				+ "                hit2.idguardia = hit.idguardia " + "             AND hit2.agrupar = hit.agrupar "
				+ "            AND " + "                hit2.idhito = " + "                    CASE hit.idhito "
				+ "                        WHEN 7    THEN 7 " + "                        WHEN 9    THEN 9 "
				+ "                        WHEN 22   THEN 22 " + "                        WHEN 5    THEN 5 "
				+ "                        WHEN 20   THEN 20 " + "                        WHEN 44   THEN 44 "
				+ "                        WHEN 1    THEN 1 " + "                        WHEN 12   THEN 12 "
				+ "                        WHEN 13   THEN 13 " + "                    END "
				+ "    ) simple_o_importe_individual, " + "    ( " + "        SELECT " + "            hit2.preciohito "
				+ "        FROM " + "            scs_hitofacturableguardia hit2 " + "        WHERE "
				+ "                hit2.idinstitucion = hit.idinstitucion " + "            AND "
				+ "                hit2.idturno = hit.idturno " + "            AND "
				+ "                hit2.idguardia = hit.idguardia " + "            AND "
				+ "                hit2.diasaplicables = hit.diasaplicables " + "            AND "
				+ "                hit2.agrupar = hit.agrupar " + "            AND " + "                hit2.idhito = "
				+ "                    CASE hit.idhito " + "                        WHEN 44   THEN 46 "
				+ "                        WHEN 1    THEN 45 " + "                    END " + "    ) NAPARTIR, "
				+ "    ( " + "        SELECT " + "            hit2.preciohito " + "        FROM "
				+ "            scs_hitofacturableguardia hit2 " + "        WHERE "
				+ "                hit2.idinstitucion = hit.idinstitucion " + "            AND "
				+ "                hit2.idturno = hit.idturno " + "            AND "
				+ "                hit2.idguardia = hit.idguardia " + "            AND "
				+ "                hit2.diasaplicables = hit.diasaplicables " + "            AND "
				+ "                hit2.agrupar = hit.agrupar " + "            AND " + "                hit2.idhito = "
				+ "                    CASE hit.idhito " + "                        WHEN 7    THEN 8"
				+ "                        WHEN 22   THEN 8" + "                        WHEN 5    THEN 3"
				+ "                        WHEN 20   THEN 3" + "                        WHEN 9    THEN 6"
				+ "                        WHEN 24   THEN 6" + "                        WHEN 44   THEN 4"
				+ "                        WHEN 1    THEN 2" + "                    END" + "    ) maximo,"
				+ "        CASE" + "            WHEN nvl(" + "                hit.agrupar," + "                0"
				+ "            ) = '1' THEN 'No'" + "            ELSE 'Si'" + "        END" + "    por_dia");

		sql.FROM("scs_hitofacturableguardia hit," + "    scs_hitofacturable tip," + "    scs_guardiasturno gua");

		sql.WHERE(" hit.idhito = tip.idhito" + "    AND" + "        hit.idinstitucion = gua.idinstitucion" + "    AND"
				+ "        hit.idturno = gua.idturno" + "    AND" + "        hit.idguardia = gua.idguardia" + "    AND"
				+ "        hit.idhito IN (" + "            7,9,25,22,5,20,44,1,12,13" + "        )");
		sql.WHERE("hit.idinstitucion = " + idinstitucion);

		sql.WHERE("gua.idguardia IN( " + idGuardia + ")");

		sql.GROUP_BY("hit.idinstitucion," + "    hit.idturno," + "    hit.idguardia," + "    gua.diasguardia,"
				+ "    gua.tipodiasguardia," + "    tip.descripcion," + "    gua.seleccionlaborables,"
				+ "    gua.seleccionfestivos," + "    hit.preciohito," + "    hit.diasaplicables," + "    hit.agrupar,"
				+ "    hit.idhito," + "   gua.fechabaja," + "tip.idhitoconfiguracion");
		sql.ORDER_BY("hit.idhito, hit.idguardia");

		sqlFinal.SELECT("*");
		sqlFinal.FROM("(" + sql + ")");
		if (tamMaximo != null) {
			sqlFinal.WHERE("ROWNUM <= " + tamMaximo);
		}

		return sqlFinal.toString();
	}
}
