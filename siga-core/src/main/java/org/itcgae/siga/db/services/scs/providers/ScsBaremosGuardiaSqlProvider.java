package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.scs.BaremosGuardiaItem;

public class ScsBaremosGuardiaSqlProvider {

	public String searchBaremosGuardia(BaremosGuardiaItem baremosGuardiaItem, Short idinstitucion) {
		
		String guardias = "";
		String facturaciones = "";
		String turnos = "";
		
			if(baremosGuardiaItem.getIdGuardias() != null) {
				for (int i = 0; i < baremosGuardiaItem.getIdGuardias().length; i++) {
					guardias += baremosGuardiaItem.getIdGuardias()[i].toString();
					if (i < baremosGuardiaItem.getIdGuardias().length - 1)
						guardias += ",";
				}
			}
			
		
			if(baremosGuardiaItem.getIdTurnos() != null) {
			for (int i = 0; i < baremosGuardiaItem.getIdTurnos().length; i++) {
				turnos += baremosGuardiaItem.getIdTurnos()[i].toString();
				if (i < baremosGuardiaItem.getIdTurnos().length - 1)
					turnos += ",";
			}
			}
			if(baremosGuardiaItem.getIdFacturaciones() != null) {
			for (int i = 0; i < baremosGuardiaItem.getIdFacturaciones().length; i++) {
				facturaciones += baremosGuardiaItem.getIdFacturaciones()[i].toString();
				if (i < baremosGuardiaItem.getIdFacturaciones().length - 1)
					facturaciones += ",";
			}
			}
		
		SQL sql = new SQL();
		sql.SELECT(
				"	LISTAGG( "
				+ "        gua.nombre, "
				+ "        ',' "
				+ "    ) WITHIN GROUP(ORDER BY gua.nombre) guardias, "
				+ "    gua.diasguardia "
				+ "     || ' ' "
				+ "     || gua.tipodiasguardia n_dias, "+
				"	tip.idhitoconfiguracion,"
						+ "		hit.idturno,"
						+ "		hit.idguardia,"
						+ "     hit.IDHITO,"
						+ "(SELECT NOMBRE FROM SCS_TURNO WHERE IDINSTITUCION = hit.IDINSTITUCION AND IDTURNO = hit.idturno) NOMBRE,"
						+ "		gua.fechabaja,"
				+ "    f_siga_getrecurso( "
				+ "        tip.descripcion, "
				+ "        1 "
				+ "    ) baremo, "
				+ "    'L: ' "
				+ "     || CASE "
				+ "            WHEN regexp_count( "
				+ "                gua.seleccionlaborables "
				+ "                 || hit.diasaplicables, "
				+ "                'L' "
				+ "            ) = 2 THEN 'L' "
				+ "        END "
				+ "     || CASE "
				+ "            WHEN regexp_count( "
				+ "                gua.seleccionlaborables "
				+ "                 || hit.diasaplicables, "
				+ "                'M' "
				+ "            ) = 2 THEN 'M' "
				+ "        END "
				+ "     || CASE "
				+ "            WHEN regexp_count( "
				+ "                gua.seleccionlaborables "
				+ "                 || hit.diasaplicables, "
				+ "                'X' "
				+ "            ) = 2 THEN 'X' "
				+ "        END "
				+ "     || CASE "
				+ "            WHEN regexp_count( "
				+ "                gua.seleccionlaborables "
				+ "                 || hit.diasaplicables, "
				+ "                'J' "
				+ "            ) = 2 THEN 'J' "
				+ "        END "
				+ "     || CASE "
				+ "            WHEN regexp_count( "
				+ "                gua.seleccionlaborables "
				+ "                 || hit.diasaplicables, "
				+ "                'V' "
				+ "            ) = 2 THEN 'V' "
				+ "        END "
				+ "     || CASE "
				+ "            WHEN regexp_count( "
				+ "                gua.seleccionlaborables "
				+ "                 || hit.diasaplicables, "
				+ "                'S' "
				+ "            ) = 2 THEN 'S' "
				+ "        END "
				+ "     || CASE "
				+ "            WHEN regexp_count( "
				+ "                gua.seleccionlaborables "
				+ "                 || hit.diasaplicables, "
				+ "                'D' "
				+ "            ) = 2 THEN 'D' "
				+ "        END "
				+ "     || ' ' "
				+ "     || CHR(10) "
				+ "     || 'F: ' "
				+ "     || CASE "
				+ "            WHEN regexp_count( "
				+ "                gua.seleccionfestivos "
				+ "                 || hit.diasaplicables, "
				+ "                'L' "
				+ "            ) = 2 THEN 'L' "
				+ "        END "
				+ "     || CASE "
				+ "            WHEN regexp_count( "
				+ "                gua.seleccionfestivos "
				+ "                 || hit.diasaplicables, "
				+ "                'M' "
				+ "            ) = 2 THEN 'M' "
				+ "        END "
				+ "     || CASE "
				+ "            WHEN regexp_count( "
				+ "                gua.seleccionfestivos "
				+ "                 || hit.diasaplicables, "
				+ "                'X' "
				+ "            ) = 2 THEN 'X' "
				+ "        END "
				+ "     || CASE "
				+ "            WHEN regexp_count( "
				+ "                gua.seleccionfestivos "
				+ "                 || hit.diasaplicables, "
				+ "                'J' "
				+ "            ) = 2 THEN 'J' "
				+ "        END "
				+ "     || CASE "
				+ "            WHEN regexp_count( "
				+ "                gua.seleccionfestivos "
				+ "                 || hit.diasaplicables, "
				+ "                'V' "
				+ "            ) = 2 THEN 'V' "
				+ "        END "
				+ "     || CASE "
				+ "            WHEN regexp_count( "
				+ "                gua.seleccionfestivos "
				+ "                 || hit.diasaplicables, "
				+ "                'S' "
				+ "            ) = 2 THEN 'S' "
				+ "        END "
				+ "     || CASE "
				+ "            WHEN regexp_count( "
				+ "                gua.seleccionfestivos "
				+ "                 || hit.diasaplicables, "
				+ "                'D' "
				+ "            ) = 2 THEN 'D' "
				+ "        END "
				+ "    dias, "
				+ "    ( "
				+ "        SELECT "
				+ "            hit2.preciohito "
				+ "        FROM "
				+ "            scs_hitofacturableguardia hit2 "
				+ "        WHERE "
				+ "                hit2.idinstitucion = hit.idinstitucion "
				+ "            AND "
				+ "                hit2.idturno = hit.idturno "
				+ "            AND "
				+ "                hit2.idguardia = hit.idguardia "
				+ "            AND "
				+ "                hit2.diasaplicables = hit.diasaplicables "
				+ "            AND "
				+ "                hit2.agrupar = hit.agrupar "
				+ "            AND "
				+ "                hit2.idhito = "
				+ "                    CASE hit.idhito "
				+ "                        WHEN 7    THEN 19 "
				+ "                        WHEN 22   THEN 19 "
				+ "                        WHEN 5    THEN 10 "
				+ "                        WHEN 20   THEN 10 "
				+ "                        WHEN 44   THEN 54 "
				+ "                        WHEN 1    THEN 53 "
				+ "                    END "
				+ "    ) disponibilidad, "
				+ "    ( "
				+ "        SELECT "
				+ "            hit2.preciohito "
				+ "        FROM "
				+ "            scs_hitofacturableguardia hit2 "
				+ "        WHERE "
				+ "                hit2.idinstitucion = hit.idinstitucion "
				+ "            AND "
				+ "                hit2.idturno = hit.idturno "
				+ "            AND "
				+ "                hit2.idguardia = hit.idguardia "
				+ "            AND "
				+ "                hit2.diasaplicables = hit.diasaplicables "
				+ "            AND "
				+ "                hit2.agrupar = hit.agrupar "
				+ "            AND "
				+ "                hit2.idhito = "
				+ "                    CASE hit.idhito "
				+ "                        WHEN 44   THEN 56 "
				+ "                        WHEN 1    THEN 55 "
				+ "                    END "
				+ "    ) num_minimo_simple, "
				+ "    ( "
				+ "        SELECT "
				+ "            hit2.preciohito "
				+ "        FROM "
				+ "            scs_hitofacturableguardia hit2 "
				+ "        WHERE "
				+ "                hit2.idinstitucion = hit.idinstitucion "
				+ "            AND "
				+ "                hit2.idturno = hit.idturno "
				+ "            AND "
				+ "                hit2.idguardia = hit.idguardia "
				+ "             AND hit2.agrupar = hit.agrupar "
				+ "            AND "
				+ "                hit2.idhito = "
				+ "                    CASE hit.idhito "
				+ "                        WHEN 7    THEN 7 "
				+ "                        WHEN 9    THEN 9 "
				+ "                        WHEN 22   THEN 22 "
				+ "                        WHEN 5    THEN 5 "
				+ "                        WHEN 20   THEN 20 "
				+ "                        WHEN 44   THEN 44 "
				+ "                        WHEN 1    THEN 1 "
				+ "                        WHEN 12   THEN 12 "
				+ "                        WHEN 13   THEN 13 "
				+ "                    END "
				+ "    ) simple_o_importe_individual, "
				+ "    ( "
				+ "        SELECT "
				+ "            hit2.preciohito "
				+ "        FROM "
				+ "            scs_hitofacturableguardia hit2 "
				+ "        WHERE "
				+ "                hit2.idinstitucion = hit.idinstitucion "
				+ "            AND "
				+ "                hit2.idturno = hit.idturno "
				+ "            AND "
				+ "                hit2.idguardia = hit.idguardia "
				+ "            AND "
				+ "                hit2.diasaplicables = hit.diasaplicables "
				+ "            AND "
				+ "                hit2.agrupar = hit.agrupar "
				+ "            AND "
				+ "                hit2.idhito = "
				+ "                    CASE hit.idhito "
				+ "                        WHEN 44   THEN 46 "
				+ "                        WHEN 1    THEN 45 "
				+ "                    END "
				+ "    ) NAPARTIR, "
				+ "    ( "
				+ "        SELECT "
				+ "            hit2.preciohito "
				+ "        FROM "
				+ "            scs_hitofacturableguardia hit2 "
				+ "        WHERE "
				+ "                hit2.idinstitucion = hit.idinstitucion "
				+ "            AND "
				+ "                hit2.idturno = hit.idturno "
				+ "            AND "
				+ "                hit2.idguardia = hit.idguardia "
				+ "            AND "
				+ "                hit2.diasaplicables = hit.diasaplicables "
				+ "            AND "
				+ "                hit2.agrupar = hit.agrupar "
				+ "            AND "
				+ "                hit2.idhito = "
				+ "                    CASE hit.idhito "
				+ "                        WHEN 7    THEN 8"
				+ "                        WHEN 22   THEN 8"
				+ "                        WHEN 5    THEN 3"
				+ "                        WHEN 20   THEN 3"
				+ "                        WHEN 9    THEN 6"
				+ "                        WHEN 24   THEN 6"
				+ "                        WHEN 44   THEN 4"
				+ "                        WHEN 1    THEN 2"
				+ "                    END"
				+ "    ) maximo,"
				+ "        CASE"
				+ "            WHEN nvl("
				+ "                hit.agrupar,"
				+ "                0"
				+ "            ) = '1' THEN 'No'"
				+ "            ELSE 'Si'"
				+ "        END"
				+ "    por_dia");
				
				if(!facturaciones.equals("0")) {
					sql.FROM("FCS_HISTORICO_HITOFACT  hit,"
							+ "    scs_hitofacturable tip,"
							+ "    scs_guardiasturno gua,"
							+ "		scs_turno tur");
				}else {
					sql.FROM("scs_hitofacturableguardia hit,"
							+ "    scs_hitofacturable tip,"
							+ "    scs_guardiasturno gua");
				}
				
				sql.WHERE(" hit.idhito = tip.idhito"
						+ "    AND"
						+ "        hit.idinstitucion = gua.idinstitucion"
						+ "    AND"
						+ "        hit.idturno = gua.idturno"
						+ "    AND"
						+ "        hit.idguardia = gua.idguardia"
						+ "    AND"
						+ "        hit.idhito IN ("
						+ "            7,9,25,22,5,20,44,1,12,13"
						+ "        )"
						);
				sql.WHERE("hit.idinstitucion = " + idinstitucion);
				if(guardias != "") {
					sql.WHERE("gua.idguardia IN( " + guardias +")");
				}
				if(turnos != "") {
					sql.WHERE("hit.idturno IN( " + turnos +")");
				}
				if(!facturaciones.equals("0")) {
					sql.WHERE("hit.idfacturacion IN( " + facturaciones +")");
				}
				if(baremosGuardiaItem.isHistorico() == false) {
					sql.WHERE("gua.fechabaja is null");
				}else {
					sql.WHERE("gua.fechabaja is not null");
				}
				sql.WHERE("rownum <= 200");
				
				sql.GROUP_BY("hit.idinstitucion,"
						+ "    hit.idturno,"
						+ "    hit.idguardia,"
						+ "    gua.diasguardia,"
						+ "    gua.tipodiasguardia,"
						+ "    tip.descripcion,"
						+ "    gua.seleccionlaborables,"
						+ "    gua.seleccionfestivos,"
						+ "    hit.preciohito,"
						+ "    hit.diasaplicables,"
						+ "    hit.agrupar,"
						+ "    hit.idhito,"
						+ "   gua.fechabaja,"
						+ "tip.idhitoconfiguracion");
				sql.ORDER_BY("hit.idhito, hit.idguardia");
		return sql.toString();
	}
}
