package org.itcgae.siga.db.services.scs.providers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.GuardiasItem;
import org.itcgae.siga.DTOs.scs.IncompatibilidadesDatosEntradaItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.ScsIncompatibilidadguardiasSqlProvider;

public class ScsIncompatibilidadguardiasSqlExtendsProvider extends ScsIncompatibilidadguardiasSqlProvider{

	public String tarjetaIncompatibilidades(String idGuardia, String idTurno, String idInstitucion) {
		// SQL auxiliar para el UNION
		
		SQL sqlUnion = new SQL();
		sqlUnion.SELECT("turnos.NOMBRE AS TURNO");
		sqlUnion.SELECT("guardi.NOMBRE AS GUARDIA");
		sqlUnion.SELECT("guardi.SELECCIONLABORABLES");
		sqlUnion.SELECT("guardi.SELECCIONFESTIVOS");
		sqlUnion.SELECT("CASE \r\n" + 
				"			WHEN  'LMXJVSD' LIKE CONCAT('%',CONCAT(SELECCIONLABORABLES,'%')) AND LENGTH(SELECCIONLABORABLES)>1 \r\n" + 
				"				AND SUBSTR(SELECCIONLABORABLES,1,1) = 'L' AND SUBSTR(SELECCIONLABORABLES,LENGTH(SELECCIONLABORABLES),1) IN ('V','S','D') THEN \r\n" + 
				"				(CONCAT(CONCAT(SUBSTR(SELECCIONLABORABLES,1,1), '-'), SUBSTR(SELECCIONLABORABLES,LENGTH(SELECCIONLABORABLES),1)))\r\n" + 
				"			WHEN LENGTH(SELECCIONLABORABLES) = 1 THEN\r\n" + 
				"				SELECCIONLABORABLES\r\n" + 
				"			ELSE SELECCIONLABORABLES\r\n" + 
				"			END AS diaslaborables,\r\n" + 
				"		CASE \r\n" + 
				"			WHEN  'LMXJVSD' LIKE CONCAT('%',CONCAT(SELECCIONFESTIVOS,'%')) AND LENGTH(SELECCIONFESTIVOS)>1 \r\n" + 
				"				AND SUBSTR(SELECCIONFESTIVOS,1,1) = 'L' AND SUBSTR(SELECCIONFESTIVOS,LENGTH(SELECCIONFESTIVOS),1) IN ('V','S','D') THEN \r\n" + 
				"				(CONCAT(CONCAT(SUBSTR(SELECCIONFESTIVOS,1,1), '-'), SUBSTR(SELECCIONFESTIVOS,LENGTH(SELECCIONFESTIVOS),1)))\r\n" + 
				"			WHEN LENGTH(SELECCIONFESTIVOS) = 1 THEN\r\n" + 
				"				SELECCIONFESTIVOS\r\n" + 
				"			ELSE SELECCIONFESTIVOS\r\n" + 
				"			END AS diasfestivos");
		sqlUnion.SELECT("incomp.MOTIVOS");
		sqlUnion.SELECT("incomp.DIASSEPARACIONGUARDIAS");
		
		sqlUnion.FROM("SCS_INCOMPATIBILIDADGUARDIAS incomp");

		sqlUnion.JOIN("SCS_GUARDIASTURNO guardi ON incomp.idguardia = guardi.idguardia AND INCOMP.idturno = guardi.idturno AND INCOMP.idinstitucion = guardi.idinstitucion");
		sqlUnion.JOIN("SCS_TURNO turnos ON turnos.idturno = guardi.idturno AND turnos.idinstitucion = guardi.idinstitucion");
		
		sqlUnion.WHERE("(incomp.IDGUARDIA = "+idGuardia+" AND incomp.IDINSTITUCION = "+idInstitucion+" AND turnos.IDTURNO = "+idTurno);
		
		
		
		SQL sql = new SQL();

		sql.SELECT("turnos.NOMBRE AS TURNO");
		sql.SELECT("guardi.NOMBRE AS GUARDIA");
		sql.SELECT("guardi.SELECCIONLABORABLES");
		sql.SELECT("guardi.SELECCIONFESTIVOS");
		sql.SELECT("CASE \r\n" + 
				"			WHEN  'LMXJVSD' LIKE CONCAT('%',CONCAT(SELECCIONLABORABLES,'%')) AND LENGTH(SELECCIONLABORABLES)>1 \r\n" + 
				"				AND SUBSTR(SELECCIONLABORABLES,1,1) = 'L' AND SUBSTR(SELECCIONLABORABLES,LENGTH(SELECCIONLABORABLES),1) IN ('V','S','D') THEN \r\n" + 
				"				(CONCAT(CONCAT(SUBSTR(SELECCIONLABORABLES,1,1), '-'), SUBSTR(SELECCIONLABORABLES,LENGTH(SELECCIONLABORABLES),1)))\r\n" + 
				"			WHEN LENGTH(SELECCIONLABORABLES) = 1 THEN\r\n" + 
				"				SELECCIONLABORABLES\r\n" + 
				"			ELSE SELECCIONLABORABLES\r\n" + 
				"			END AS diaslaborables,\r\n" + 
				"		CASE \r\n" + 
				"			WHEN  'LMXJVSD' LIKE CONCAT('%',CONCAT(SELECCIONFESTIVOS,'%')) AND LENGTH(SELECCIONFESTIVOS)>1 \r\n" + 
				"				AND SUBSTR(SELECCIONFESTIVOS,1,1) = 'L' AND SUBSTR(SELECCIONFESTIVOS,LENGTH(SELECCIONFESTIVOS),1) IN ('V','S','D') THEN \r\n" + 
				"				(CONCAT(CONCAT(SUBSTR(SELECCIONFESTIVOS,1,1), '-'), SUBSTR(SELECCIONFESTIVOS,LENGTH(SELECCIONFESTIVOS),1)))\r\n" + 
				"			WHEN LENGTH(SELECCIONFESTIVOS) = 1 THEN\r\n" + 
				"				SELECCIONFESTIVOS\r\n" + 
				"			ELSE SELECCIONFESTIVOS\r\n" + 
				"			END AS diasfestivos");
		sql.SELECT("incomp.MOTIVOS");
		sql.SELECT("incomp.DIASSEPARACIONGUARDIAS");
		
		sql.FROM("SCS_INCOMPATIBILIDADGUARDIAS incomp");

		sql.JOIN("SCS_GUARDIASTURNO guardi ON incomp.idguardia = guardi.idguardia AND INCOMP.idturno = guardi.idturno AND INCOMP.idinstitucion = guardi.idinstitucion");
		sql.JOIN("SCS_TURNO turnos ON turnos.idturno = guardi.idturno AND turnos.idinstitucion = guardi.idinstitucion");
		
		sql.WHERE("incomp.IDGUARDIA_INCOMPATIBLE = "+idGuardia+" AND incomp.IDINSTITUCION = "+idInstitucion + " AND incomp.IDTURNO_INCOMPATIBLE = " + idTurno + ") UNION "+sqlUnion.toString());
		return sql.toString();
	}
	
	public String resumenIncompatibilidades(GuardiasItem guardia, String idInstitucion)  {
		SQL sql = new SQL();
		
		sql.SELECT("count (*) AS TOTAL_INCOMPATIBILIDADES");
		
		sql.FROM("(SELECT\r\n" + 
				"	turnos.NOMBRE AS TURNO,\r\n" + 
				"	guardi.NOMBRE AS GUARDIA,\r\n" + 
				"	guardi.SELECCIONLABORABLES,\r\n" + 
				"	guardi.SELECCIONFESTIVOS,\r\n" + 
				"	incomp.MOTIVOS,\r\n" + 
				"	incomp.DIASSEPARACIONGUARDIAS,\r\n" + 
				"	incomp.IDINSTITUCION\r\n" + 
				"FROM\r\n" + 
				"	SCS_INCOMPATIBILIDADGUARDIAS incomp\r\n" + 
				"	JOIN SCS_GUARDIASTURNO guardi ON incomp.idguardia = guardi.idguardia AND INCOMP.idturno = guardi.idturno AND INCOMP.idinstitucion = guardi.idinstitucion\r\n" + 
				"	JOIN SCS_TURNO turnos ON turnos.idturno = guardi.idturno AND turnos.idinstitucion = guardi.idinstitucion\r\n" + 
				"WHERE\r\n" + 
				"	incomp.IDINSTITUCION = "+idInstitucion+"\r\n" + 
				"	AND incomp.IDTURNO = "+guardia.getIdTurno()+"\r\n" + 
				"	AND incomp.IDGUARDIA = "+guardia.getIdGuardia()+"\r\n" + 
				"UNION\r\n" + 
				"SELECT\r\n" + 
				"	turnos.NOMBRE AS TURNO,\r\n" + 
				"	guardi.NOMBRE AS GUARDIA,\r\n" + 
				"	guardi.SELECCIONLABORABLES,\r\n" + 
				"	guardi.SELECCIONFESTIVOS,\r\n" + 
				"	incomp.MOTIVOS,\r\n" + 
				"	incomp.DIASSEPARACIONGUARDIAS,\r\n" + 
				"	incomp.IDINSTITUCION\r\n" + 
				"FROM\r\n" + 
				"	SCS_INCOMPATIBILIDADGUARDIAS incomp\r\n" + 
				"	JOIN SCS_GUARDIASTURNO guardi ON incomp.idguardia = guardi.idguardia AND INCOMP.idturno = guardi.idturno AND INCOMP.idinstitucion = guardi.idinstitucion\r\n" + 
				"	JOIN SCS_TURNO turnos ON turnos.idturno = guardi.idturno AND turnos.idinstitucion = guardi.idinstitucion\r\n" + 
				"WHERE\r\n" + 
				"	incomp.IDINSTITUCION = "+idInstitucion+"\r\n" + 
				"	AND incomp.IDTURNO_INCOMPATIBLE = "+guardia.getIdTurno()+"\r\n" + 
				"	AND incomp.IDGUARDIA_INCOMPATIBLE = "+guardia.getIdGuardia()+")");
		
		
		return sql.toString();
	}
	
	public String resumenIncompatibilidades2(GuardiasItem guardia, String idInstitucion)  {
		SQL sql = new SQL();
		
		sql.SELECT("turnos.NOMBRE AS TURNO, guardi.NOMBRE AS GUARDIA, guardi.SELECCIONLABORABLES, guardi.SELECCIONFESTIVOS, incomp.MOTIVOS, incomp.DIASSEPARACIONGUARDIAS");
		
		sql.FROM("	SCS_INCOMPATIBILIDADGUARDIAS incomp\r\n" + 
				"	JOIN SCS_GUARDIASTURNO guardi ON incomp.idguardia = guardi.idguardia AND INCOMP.idturno = guardi.idturno AND INCOMP.idinstitucion = guardi.idinstitucion\r\n" + 
				"	JOIN SCS_TURNO turnos ON turnos.idturno = guardi.idturno AND turnos.idinstitucion = guardi.idinstitucion\r\n" + 
				"WHERE\r\n" + 
				"	incomp.IDINSTITUCION = "+idInstitucion+"\r\n" + 
				"	AND incomp.IDTURNO = "+guardia.getIdTurno()+"\r\n" + 
				"	AND incomp.IDGUARDIA = "+guardia.getIdGuardia());
		
		return sql.toString();
	}
	
	
	
	public String listadoIncompatibilidades(IncompatibilidadesDatosEntradaItem incompatibilidades, String idInstitucion, Integer tamMax) {
	
		SQL listado_guardias = new SQL();
		SQL listado_guardias_idGuardia = new SQL();
		SQL sql = new SQL();
		SQL sqlNombreg1 = new SQL();
		SQL sqlNombreg2 = new SQL();
		SQL sqlSCSGuardiasTurnoIdGuardia = new SQL();
		SQL sqlMotivos = new SQL();
		SQL SQL_PADRE = new SQL();
		
		listado_guardias_idGuardia.SELECT("SCS_GUARDIASTURNO.IDGUARDIA");
		listado_guardias_idGuardia.FROM("SCS_GUARDIASTURNO");
		/*if (incompatibilidades.getNombreGuardia() != null && !incompatibilidades.getNombreGuardia().isEmpty()) {
			listado_guardias_idGuardia.WHERE("scs_guardiasturno.nombre = " + "'" + incompatibilidades.getNombreGuardia().toString() +  "'" );
		}*/
		if (idInstitucion != null && !idInstitucion.isEmpty()) {
			listado_guardias_idGuardia.WHERE("SCS_GUARDIASTURNO.IDINSTITUCION = " + idInstitucion );
		}
		if (incompatibilidades.getIdTurno() != null && !incompatibilidades.getIdTurno().isEmpty()) {
			listado_guardias_idGuardia.WHERE("SCS_GUARDIASTURNO.IDTURNO IN (" + incompatibilidades.getIdTurno() + " ) " );
		}
		if (!UtilidadesString.esCadenaVacia(incompatibilidades.getIdGuardia())) {
			listado_guardias_idGuardia.WHERE("SCS_GUARDIASTURNO.IDGUARDIA IN (" + incompatibilidades.getIdGuardia() + " ) " );
		}
		
		
		listado_guardias.SELECT("SCS_TURNO.NOMBRE AS turno");
		listado_guardias.SELECT("SCS_GUARDIASTURNO.NOMBRE AS nombre");
		listado_guardias.SELECT("SCS_TURNO.IDTURNO");
		listado_guardias.SELECT("SCS_GUARDIASTURNO.IDGUARDIA");
		listado_guardias.SELECT("SCS_GUARDIASTURNO.IDINSTITUCION");
		listado_guardias.SELECT("SCS_GUARDIASTURNO.IDTIPOGUARDIA");
		listado_guardias.FROM(" SCS_GUARDIASTURNO");
		listado_guardias.JOIN("SCS_TURNO ON SCS_TURNO.IDTURNO = SCS_GUARDIASTURNO.IDTURNO AND SCS_GUARDIASTURNO.IDINSTITUCION = SCS_TURNO.IDINSTITUCION"); 
		if(incompatibilidades.getIdTipoGuardia() != null && ! incompatibilidades.getIdTipoGuardia().isEmpty()) {
			listado_guardias.JOIN("SCS_TIPOSGUARDIAS ON SCS_TIPOSGUARDIAS.IDTIPOGUARDIA = SCS_GUARDIASTURNO.IDTIPOGUARDIA " ); 
		} 
		
		if(incompatibilidades.getIdTipoGuardia() != null && ! incompatibilidades.getIdTipoGuardia().isEmpty()) {
			listado_guardias.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS ON GEN_RECURSOS_CATALOGOS.IDRECURSO = SCS_TIPOSGUARDIAS.DESCRIPCION AND GEN_RECURSOS_CATALOGOS.IDLENGUAJE = 1 ");	
		}
			
		listado_guardias.JOIN("SCS_AREA ON SCS_AREA.IDAREA = SCS_TURNO.IDAREA AND SCS_AREA.IDINSTITUCION = SCS_GUARDIASTURNO.IDINSTITUCION " );
		listado_guardias.JOIN("SCS_MATERIA ON SCS_MATERIA.IDAREA = SCS_AREA.IDAREA AND SCS_MATERIA.IDINSTITUCION = SCS_GUARDIASTURNO.IDINSTITUCION " );
		listado_guardias.JOIN("SCS_ZONA ON SCS_ZONA.IDINSTITUCION = SCS_GUARDIASTURNO.IDINSTITUCION AND SCS_ZONA.IDZONA = SCS_TURNO.IDZONA " );
		listado_guardias.JOIN("SCS_SUBZONA ON SCS_ZONA.IDZONA = SCS_SUBZONA.IDZONA AND SCS_SUBZONA.IDINSTITUCION = SCS_GUARDIASTURNO.IDINSTITUCION " );
		if (idInstitucion != null && !idInstitucion.isEmpty()) {
			listado_guardias.WHERE("SCS_GUARDIASTURNO.IDINSTITUCION = " + idInstitucion );
		}
		listado_guardias.WHERE("SCS_GUARDIASTURNO.FECHABAJA IS NULL " );
		listado_guardias.WHERE("SCS_TURNO.FECHABAJA IS NULL " );
		//"  ORDER BY SCS_TURNO.NOMBRE, SCS_GUARDIASTURNO.NOMBRE " +
		if (incompatibilidades.getIdTurno() != null && !incompatibilidades.getIdTurno().isEmpty()) {
			listado_guardias.WHERE("SCS_GUARDIASTURNO.IDTURNO IN (" + incompatibilidades.getIdTurno() + ")" );
		}
		/*listado_guardias.WHERE("SCS_GUARDIASTURNO.IDGUARDIA IN (" + listado_guardias_idGuardia + ")" );*/
		if (incompatibilidades.getIdArea() != null && !incompatibilidades.getIdArea().isEmpty()) {
			listado_guardias.WHERE("SCS_AREA.IDAREA IN (" + incompatibilidades.getIdArea() + ")");
		}
//		if (incompatibilidades.getIdArea() != null && !incompatibilidades.getIdArea().isEmpty()) {
//			listado_guardias.WHERE("SCS_AREA.IDAREA = " + incompatibilidades.getIdArea() );
//		}
		if (incompatibilidades.getIdMateria() != null && !incompatibilidades.getIdMateria().isEmpty()) {
			listado_guardias.WHERE("SCS_MATERIA.IDMATERIA IN ("+ incompatibilidades.getIdMateria() + ")");
		}
		if (incompatibilidades.getIdZona() != null && !incompatibilidades.getIdZona().isEmpty()) {
			listado_guardias.WHERE("SCS_ZONA.IDZONA IN (" + incompatibilidades.getIdZona() + ")");
		}
		if (incompatibilidades.getIdSubZona() != null && !incompatibilidades.getIdSubZona().isEmpty()) {
			listado_guardias.WHERE("SCS_SUBZONA.IDSUBZONA IN ( "+ incompatibilidades.getIdSubZona()+ ")" );
		}
		if (incompatibilidades.getIdJurisdiccion() != null && !incompatibilidades.getIdJurisdiccion().isEmpty()) {
			listado_guardias.WHERE("SCS_TURNO.IDJURISDICCION IN ( " + incompatibilidades.getIdJurisdiccion() + ")" );
		}
		if (incompatibilidades.getIdGrupoFacturacion() != null && !incompatibilidades.getIdGrupoFacturacion().isEmpty()) {
			listado_guardias.WHERE("SCS_TURNO.IDGRUPOFACTURACION IN ( " + incompatibilidades.getIdGrupoFacturacion() + ")"  );
		}
		if (incompatibilidades.getIdPartidaPresupuestaria() != null && !incompatibilidades.getIdPartidaPresupuestaria().isEmpty()) {
			listado_guardias.WHERE("SCS_TURNO.IDPARTIDAPRESUPUESTARIA IN ( " + incompatibilidades.getIdPartidaPresupuestaria() + ")"  );
		}
		if (incompatibilidades.getIdTipoTurno() != null && !incompatibilidades.getIdTipoTurno().isEmpty()) {
			listado_guardias.WHERE("SCS_TURNO.IDTIPOTURNO IN ( " + incompatibilidades.getIdTipoTurno() + ")"  );
		}
		if (incompatibilidades.getIdTipoGuardia() != null && !incompatibilidades.getIdTipoGuardia().isEmpty()) {
			listado_guardias.WHERE("SCS_GUARDIASTURNO.IDTIPOGUARDIA IN ( " + incompatibilidades.getIdTipoGuardia() + ")" );
		}
		listado_guardias.ORDER_BY("SCS_TURNO.NOMBRE, SCS_GUARDIASTURNO.NOMBRE " );
		
		
		sqlNombreg1.SELECT("NOMBRE ");
		sqlNombreg1.FROM("SCS_TURNO ");
		sqlNombreg1.WHERE("IDTURNO = g1.IDTURNO ");
		sqlNombreg1.WHERE("IDINSTITUCION = g1.IDINSTITUCION");
		
		sqlNombreg2.SELECT("NOMBRE ");
		sqlNombreg2.FROM("SCS_TURNO ");
		sqlNombreg2.WHERE("IDTURNO = g2.IDTURNO ");
		sqlNombreg2.WHERE("IDINSTITUCION = g2.IDINSTITUCION");
		
		sqlSCSGuardiasTurnoIdGuardia.SELECT("SCS_GUARDIASTURNO.IDGUARDIA");
		sqlSCSGuardiasTurnoIdGuardia.FROM("SCS_GUARDIASTURNO");
		/*if (incompatibilidades.getNombreGuardia() != null && !incompatibilidades.getNombreGuardia().isEmpty()) {
		sqlSCSGuardiasTurnoIdGuardia.WHERE("scs_guardiasturno.nombre = " + "'" + incompatibilidades.getNombreGuardia().toString() + "'" );
		}*/

		if (idInstitucion != null && !idInstitucion.isEmpty()) {
		sqlSCSGuardiasTurnoIdGuardia.WHERE("SCS_GUARDIASTURNO.IDINSTITUCION = " + idInstitucion );
		}
		if (incompatibilidades.getIdTurno() != null && !incompatibilidades.getIdTurno().isEmpty()) {
		sqlSCSGuardiasTurnoIdGuardia.WHERE("SCS_GUARDIASTURNO.IDTURNO IN (" + incompatibilidades.getIdTurno() + " ) " );
		}
		if (incompatibilidades.getIdGuardia() != null && !incompatibilidades.getIdGuardia().isEmpty()) {
			sqlSCSGuardiasTurnoIdGuardia.WHERE("SCS_GUARDIASTURNO.IDGUARDIA IN (" + incompatibilidades.getIdGuardia() + " ) " );
		}
		
		sqlMotivos.SELECT("MOTIVOS");
		sqlMotivos.FROM("SCS_INCOMPATIBILIDADGUARDIAS");
		sqlMotivos.WHERE("IDINSTITUCION = g1.IDINSTITUCION");
		//sqlMotivos.WHERE("((IDTURNO = g1.IDTURNO AND IDGUARDIA = g1.IDGUARDIA AND IDTURNO_INCOMPATIBLE = g2.IDTURNO AND IDGUARDIA_INCOMPATIBLE = g2.IDGUARDIA) OR (IDTURNO = g2.IDTURNO AND IDGUARDIA = g2.IDGUARDIA AND IDTURNO_INCOMPATIBLE = g1.IDTURNO AND IDGUARDIA_INCOMPATIBLE = g1.IDGUARDIA))");
		sqlMotivos.WHERE("(IDTURNO = g1.IDTURNO AND IDGUARDIA = g1.IDGUARDIA AND IDTURNO_INCOMPATIBLE = g2.IDTURNO AND IDGUARDIA_INCOMPATIBLE = g2.IDGUARDIA)");
		
		sqlMotivos.WHERE("rownum = 1");
		
		sql.SELECT_DISTINCT(
			"NVL((SELECT 1 FROM SCS_INCOMPATIBILIDADGUARDIAS WHERE IDINSTITUCION = g1.IDINSTITUCION AND ((IDTURNO = g1.IDTURNO AND IDGUARDIA = g1.IDGUARDIA AND IDTURNO_INCOMPATIBLE = g2.IDTURNO AND IDGUARDIA_INCOMPATIBLE = g2.IDGUARDIA) OR (IDTURNO = g2.IDTURNO AND IDGUARDIA = g2.IDGUARDIA AND IDTURNO_INCOMPATIBLE = g1.IDTURNO AND IDGUARDIA_INCOMPATIBLE = g1.IDGUARDIA)) AND rownum = 1), '') AS EXISTE, "+
			"( " + sqlNombreg1 + " ) AS TURNO, "+
			"g1.IDTURNO AS IDTURNO, "+
			"g1.NOMBRE AS GUARDIA, "+
			"g1.IDGUARDIA AS IDGUARDIA, "+
			"( " + sqlNombreg2 + " ) AS TURNO_INCOMPATIBLE, "+
			"g2.IDTURNO AS IDTURNO_INCOMPATIBLE, "+
			"g2.NOMBRE AS GUARDIA_INCOMPATIBLE, "+
			"g2.IDGUARDIA AS IDGUARDIA_INCOMPATIBLE, "+
			"NVL(( " + sqlMotivos + " ), '') AS MOTIVOS, "+
			"NVL((SELECT DIASSEPARACIONGUARDIAS FROM SCS_INCOMPATIBILIDADGUARDIAS WHERE IDINSTITUCION = g1.IDINSTITUCION AND ((IDTURNO = g1.IDTURNO AND IDGUARDIA = g1.IDGUARDIA AND IDTURNO_INCOMPATIBLE = g2.IDTURNO AND IDGUARDIA_INCOMPATIBLE = g2.IDGUARDIA) OR (IDTURNO = g2.IDTURNO AND IDGUARDIA = g2.IDGUARDIA AND IDTURNO_INCOMPATIBLE = g1.IDTURNO AND IDGUARDIA_INCOMPATIBLE = g1.IDGUARDIA)) AND rownum = 1), '') AS DIASSEPARACIONGUARDIAS");

			sql.FROM( "( " + listado_guardias + " ) G1 ");
			sql.FROM(  "( SCS_GUARDIASTURNO ) G2 "  );
			sql.WHERE("g1.IDINSTITUCION = g2.IDINSTITUCION");
			sql.WHERE("  (g1.IDTURNO <> g2.IDTURNO OR g1.IDGUARDIA <> g2.IDGUARDIA)" );
			if (idInstitucion != null && !idInstitucion.isEmpty()) {
				sql.WHERE("g1.IDINSTITUCION = " + idInstitucion );
			}
			if (incompatibilidades.getIdTurno() != null && !incompatibilidades.getIdTurno().isEmpty()) {
				sql.WHERE("g1.IDTURNO IN ( " + incompatibilidades.getIdTurno() + " )" );
			}
			sql.WHERE("g1.IDGUARDIA IN (" +sqlSCSGuardiasTurnoIdGuardia + ")" );
				
			sql.ORDER_BY(" turno, guardia, turno_incompatible, guardia_incompatible");


			SQL_PADRE.SELECT(" *");
			SQL_PADRE.FROM("( " + sql.toString() + " )");
			SQL_PADRE.WHERE("EXISTE IS NOT NULL");
			if(tamMax != null && tamMax > 0) {
				tamMax += 1;
				SQL_PADRE.WHERE(" ROWNUM <= " + tamMax);
			}


			String fullquery = "WITH fullquery AS ("+ SQL_PADRE +") ";
			String resultado = " SELECT DISTINCT\r\n" + 
					"    existe,\r\n" + 
					"    turno,\r\n" + 
					"    idturno,\r\n" + 
					"    guardia,\r\n" + 
					"    idguardia,\r\n" + 
					"    diasseparacionguardias,\r\n" + 
					"    LISTAGG(idguardia_incompatible, ',') WITHIN GROUP(\r\n" + 
					"        ORDER BY\r\n" + 
					"            idturno, idguardia, idturno_incompatible, idguardia_incompatible\r\n" +
					"    ) AS idguardia_incompatible,\r\n" + 
					"    motivos,\r\n" + 
					"    LISTAGG(guardia_incompatible, ',') WITHIN GROUP(\r\n" + 
					"        ORDER BY\r\n" + 
					"            idturno, idguardia, idturno_incompatible, idguardia_incompatible\r\n" +
					"    ) AS guardia_incompatible,\r\n" + 
					"    LISTAGG(idturno_incompatible, ',') WITHIN GROUP(\r\n" + 
					"        ORDER BY\r\n" + 
					"            idturno, idguardia, idturno_incompatible, idguardia_incompatible\r\n" +
					"    ) AS idturno_incompatible,\r\n" + 
					"    LISTAGG(turno_incompatible, ',') WITHIN GROUP(\r\n" + 
					"        ORDER BY\r\n" + 
					"            idturno, idguardia, idturno_incompatible, idguardia_incompatible\r\n" +
					"    ) AS turno_incompatible\r\n" + 
					"FROM\r\n" + 
					"    fullquery\r\n" + 
					"WHERE\r\n" + 
					"    ( existe IS NOT NULL\r\n" + 
					"      AND ROWNUM <= 200 )\r\n" + 
					"GROUP BY\r\n" + 
					"    existe,\r\n" + 
					"    turno,\r\n" + 
					"    idturno,\r\n" + 
					"    guardia,\r\n" + 
					"    idguardia,\r\n" + 
					"    motivos,\r\n" + 
					"    diasseparacionguardias";

			return fullquery + resultado;
	}
	
	
	public String deleteIncompatibilidades(String idTurno, String idInstitucion, String idGuardia, String idTurnoIncompatible, String idGuardiaIncompatible) {
		SQL sql = new SQL();
		
		sql.DELETE_FROM("SCS_INCOMPATIBILIDADGUARDIAS" );
		sql.WHERE("IDINSTITUCION = " + idInstitucion);

		List<String> idTurnoIncompatibleList = Arrays.asList(idTurnoIncompatible.split(","));
		List<String> idGuardiaIncompatibleList = Arrays.asList(idGuardiaIncompatible.split(","));
		String tuplasIncompatibles = IntStream.range(0, idTurnoIncompatibleList.size()).boxed()
				.map(i -> "(" + idTurno + ", " + idGuardia + ", " + idTurnoIncompatibleList.get(i) + ", " + idGuardiaIncompatibleList.get(i) + "),"
					+ "(" + idTurnoIncompatibleList.get(i) + ", " + idGuardiaIncompatibleList.get(i) + ", " + idTurno + "," + idGuardia + ")")
				.collect(Collectors.joining(","));
		sql.WHERE("( IDTURNO, IDGUARDIA, IDTURNO_INCOMPATIBLE, IDGUARDIA_INCOMPATIBLE ) IN ( " + tuplasIncompatibles + " )");


		return sql.toString();
	}
	public String deleteCalendarioProgramado1(String idTurno, String idInstitucion, String idGuardia, String idCalendarioProgramado) {
		SQL sql = new SQL();
		sql.DELETE_FROM("SCS_HCO_CONF_PROG_CALENDARIOS" );
			sql.WHERE(
				"SCS_HCO_CONF_PROG_CALENDARIOS.IDPROGCALENDARIO = " + idCalendarioProgramado );
			sql.WHERE(
				"SCS_HCO_CONF_PROG_CALENDARIOS.IDINSTITUCION = " + idInstitucion );
			sql.WHERE(
					"SCS_HCO_CONF_PROG_CALENDARIOS.IDTURNO = " + idTurno );
			sql.WHERE(
					"SCS_HCO_CONF_PROG_CALENDARIOS.IDGUARDIA = " + idGuardia );

		return sql.toString();
	}
	
	public String deleteCalendarioProgramado2(String idCalendarioProgramado) {
		SQL sql = new SQL();
		sql.DELETE_FROM("SCS_PROG_CALENDARIOS" );
			sql.WHERE(
				"SCS_PROG_CALENDARIOS.IDPROGCALENDARIO = " + idCalendarioProgramado );

		return sql.toString();
	}
	
	
	public String saveIncompatibilidades(int idTurno, int idInstitucion, int idGuardia, int idTurnoIncompatible, int idGuardiaIncompatible, int usuario, String motivos, int diasSeparacionGuardias, String fechaModificacion) {
		SQL sql = new SQL();
		
		/*En el caso de que no existan las incompatibilidades, se insertarán ambas (no debería darse el caso de que sólo haya en un sentido, eso es una incompatibilidad mal creada)
		Será necesario hacer 2 INSERTs (craremos un método al que llamaremos 2 veces)*/
				sql.INSERT_INTO("SCS_INCOMPATIBILIDADGUARDIAS( IDINSTITUCION," + 
					" IDTURNO, " +
					" IDGUARDIA, " +
					" FECHAMODIFICACION, " +
					" USUMODIFICACION, " +
					" MOTIVOS, " +
					" IDTURNO_INCOMPATIBLE, " +
					" IDGUARDIA_INCOMPATIBLE, " +
					" DIASSEPARACIONGUARDIAS ) " +
				"VALUES ( " + idInstitucion + ", " +
				idTurno + ", " +
				idGuardia + ", " +
				"' " + fechaModificacion + " ' , " +
				usuario + ", " +
				"'" + motivos + "', " +
				idTurnoIncompatible + ", " +
				idGuardiaIncompatible + ", " +
				diasSeparacionGuardias + ") " );

			/*--Primera llamada al método-----------------------------------------------------------------
			---------DATOS DE EJEMPLO:
			---------------IDINSTITUCION = 2005
			---------------IDTURNO = 803
			---------------IDGUARDIA = 1331
			---------------IDTURNO_INCOMPAATIBLE = 802
			---------------IDGUARDIA_INCOMPATIBLE = 1441
			---------------USUARIO = 1928
			---------------MOTIVOS = 'PRUEBA'
			---------------DIASSEPARACIONGUARDIAS = 2
			--------------------------------------------------------------------------------------------

			--Segunda llamada al método-----------------------------------------------------------------
			---------DATOS DE EJEMPLO:
			---------------IDINSTITUCION = 2005
			---------------IDTURNO = 802
			---------------IDGUARDIA = 1441
			---------------IDTURNO_INCOMPAATIBLE = 803
			---------------IDGUARDIA_INCOMPATIBLE = 1331
			---------------USUARIO = 1928
			---------------MOTIVOS = 'PRUEBA'
			---------------DIASSEPARACIONGUARDIAS = 2
			--------------------------------------------------------------------------------------------*/
				
	

		return sql.toString();
	}
	
	public String checkIncompatibilidadesExists(String idTurno, String idInstitucion, String idGuardia, String idTurnoIncompatible, String idGuardiaIncompatible) {
		
		SQL subquery = new SQL();
		subquery.SELECT("IDTURNO");
		subquery.FROM("SCS_GUARDIASTURNO");
		subquery.WHERE("IDGUARDIA = " + idGuardiaIncompatible);
		SQL sql = new SQL();
		
		/*Será necesario comprobar si ya existe dicha incompatibilidad, para ello realizaremos la siguiente consulta, 
		que debrerá devolver como resultado 2 (esto significa que existe la incompatibilidad en ambas direcciones)*/
		//Campos obligatorios : Turno, Guardia, Guardias Incompatibles (al menos seleccionar una guardia) y Días de separación.
		sql.SELECT("COUNT(*)");
					
		sql.FROM("SCS_INCOMPATIBILIDADGUARDIAS");
					
		sql.WHERE("IDINSTITUCION = " + idInstitucion +
				" AND ((IDTURNO IN ( " + idTurno + " ) " +
				" AND IDGUARDIA in (" + idGuardia  + ") ");
				if (idTurnoIncompatible != null) {
					sql.WHERE(" IDTURNO_INCOMPATIBLE in " + "(" + idTurnoIncompatible + ")" );
					sql.WHERE("IDGUARDIA_INCOMPATIBLE in " + "(" + idGuardiaIncompatible + ")"  + ") " +
							" OR (IDTURNO IN ( " + idTurnoIncompatible + " ) " +
							" AND IDGUARDIA in " + "(" + idGuardiaIncompatible + ") " +
							" AND IDTURNO_INCOMPATIBLE in " + "(" + idTurno + ") " +
							" AND IDGUARDIA_INCOMPATIBLE in (" + idGuardia + "))) "
								);
				}else {
					sql.WHERE(" IDTURNO_INCOMPATIBLE IN " + "(" + subquery + ") " );
					sql.WHERE("IDGUARDIA_INCOMPATIBLE in " + "(" + idGuardiaIncompatible + ") "  + ") " +
							" OR (IDTURNO IN ( " + subquery + " ) " +
							" AND IDGUARDIA in " + "(" + idGuardiaIncompatible + ") " +
							" AND IDTURNO_INCOMPATIBLE in (" + idTurno + ") " +
							" AND IDGUARDIA_INCOMPATIBLE in (" + idGuardia + "))) "
								);
				}
				
		return sql.toString();
	}
	
	public String updateIfExists(String idTurno, String idInstitucion, String idGuardia, String idTurnoIncompatible, String idGuardiaIncompatible, String motivos, String diasSeparacionGuardia, String fechaModificacion) {
		SQL sql = new SQL();
		//Campos obligatorios : Turno, Guardia, Guardias Incompatibles (al menos seleccionar una guardia) y Días de separación.
		//Si ya existía la incompatibilidad, se actualizará (este update actualiza ambas incompatibilidades al la vez)
		sql.UPDATE("SCS_INCOMPATIBILIDADGUARDIAS");
		if(!UtilidadesString.esCadenaVacia(motivos)) {
			sql.SET("MOTIVOS = '" + motivos + "'," +
					" DIASSEPARACIONGUARDIAS = " + diasSeparacionGuardia + "," +
					" FECHAMODIFICACION = '" + fechaModificacion + "'");
		}else{
			sql.SET("MOTIVOS = null," +
					" DIASSEPARACIONGUARDIAS = " + diasSeparacionGuardia + "," +
					" FECHAMODIFICACION = '" + fechaModificacion + "'");
		}
		sql.WHERE(
			"IDINSTITUCION =" + idInstitucion +
			" AND ((IDTURNO IN ( " + idTurno + " )" +
			" AND IDGUARDIA = " + idGuardia +
			" AND IDTURNO_INCOMPATIBLE = " + idTurnoIncompatible + 
			" AND IDGUARDIA_INCOMPATIBLE = " + idGuardiaIncompatible + ")" +
			" OR (IDTURNO = " + idTurnoIncompatible +
			" AND IDGUARDIA = " + idGuardiaIncompatible + 
			" AND IDTURNO_INCOMPATIBLE IN ( " + idTurno + " )" +
			" AND IDGUARDIA_INCOMPATIBLE = " + idGuardia + "))");
		return sql.toString();
	}
	
	
	public String getIdTurnoFromNombreTurno(String nombreTurno) {
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("SCS_TURNO.IDTURNO");
					
		sql.FROM("SCS_TURNO");
					
		sql.WHERE("SCS_TURNO.NOMBRE = " + "'" + nombreTurno + "'" );
		
		return sql.toString();
	}
	
	public String getIdGuardiaFromNombreGuardia(String nombreGuardia) {
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("SCS_GUARDIASTURNO.IDGUARDIA");
					
		sql.FROM("SCS_GUARDIASTURNO");
					
		sql.WHERE("SCS_GUARDIASTURNO.NOMBRE = '" + nombreGuardia+ "'" );
		
		return sql.toString();
	}
	
	public String getIdTurnoIncompatibleFromNombreTurno(String nombreTurnoIncompatible) {
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("SCS_TURNO.IDTURNO");
					
		sql.FROM("SCS_TURNO");
					
		sql.WHERE("SCS_TURNO.NOMBRE = '" + nombreTurnoIncompatible + "'");
		
		return sql.toString();
	}
	
	public String getIdGuardiaIncompatibleFromNombreGuardia(String nombreGuardiaIncompatible) {
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("SCS_GUARDIASTURNO.IDGUARDIA");
		
		sql.FROM("SCS_GUARDIASTURNO");
					
		sql.WHERE("SCS_GUARDIASTURNO.NOMBRE = '" + nombreGuardiaIncompatible + "'");
		
		return sql.toString();
	}
	
	
	public String getIdTurnoIncByIdGuardiaInc(String idGuardiaInc, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("SCS_GUARDIASTURNO.IDTURNO");
					
		sql.FROM("SCS_GUARDIASTURNO");
					
		sql.WHERE("SCS_GUARDIASTURNO.IDGUARDIA IN (" + idGuardiaInc + ")");
		sql.WHERE("SCS_GUARDIASTURNO.IDINSTITUCION = " + idInstitucion);

		return sql.toString();
	}
	
	
	
	public String getListaLabelsGuardiasInc(String idInstitucion, String idTipoGuardia, String idTurno, Integer usu, String idPartidaPresupuestaria) {
		SQL sql = new SQL();
		
		sql.SELECT("SCS_GUARDIASTURNO.NOMBRE");
		
		sql.FROM("SCS_GUARDIASTURNO");
		
		if (idInstitucion != null && !idInstitucion.isEmpty()) {
			sql.WHERE("SCS_GUARDIASTURNO.IDINSTITUCION = " + idInstitucion );
		}
		if (idTipoGuardia != null && !idTipoGuardia.isEmpty()) {
			sql.WHERE("SCS_GUARDIASTURNO.IDTIPOGUARDIA IN ( " + idTipoGuardia + ")");
		}
		if (idTurno != null && !idTurno.isEmpty()) {
			sql.WHERE("SCS_GUARDIASTURNO.IDTURNO IN ( " + idTurno + ")");
		}
//		if (usu != null) {
//			sql.WHERE("SCS_GUARDIASTURNO.USUMODIFICACION = " + usu );
//		}
		if (idPartidaPresupuestaria != null && !idPartidaPresupuestaria.isEmpty()) {
			sql.WHERE("SCS_GUARDIASTURNO.IDPARTIDAPRESUPUESTARIA IN ( " + idPartidaPresupuestaria + ")"  );
		}
		sql.ORDER_BY("SCS_GUARDIASTURNO.NOMBRE");
		return sql.toString();
	}
	
	public String getListaValueGuardiasInc(String idInstitucion, String idTipoGuardia, String idTurno, Integer usu, String idPartidaPresupuestaria) {
		SQL sql = new SQL();
		
		sql.SELECT("SCS_GUARDIASTURNO.IDGUARDIA");
		
		sql.FROM("SCS_GUARDIASTURNO");
		
		if (idInstitucion != null && !idInstitucion.isEmpty()) {
			sql.WHERE("SCS_GUARDIASTURNO.IDINSTITUCION = " + idInstitucion );
		}
		if (idTipoGuardia != null && !idTipoGuardia.isEmpty()) {
			sql.WHERE("SCS_GUARDIASTURNO.IDTIPOGUARDIA IN ( " + idTipoGuardia + ")");
		}
		if (idTurno != null && !idTurno.isEmpty()) {
			sql.WHERE("SCS_GUARDIASTURNO.IDTURNO IN ( " + idTurno + ")");
		}
//		if (usu != null) {
//			sql.WHERE("SCS_GUARDIASTURNO.USUMODIFICACION = " + usu );
//		}
		if (idPartidaPresupuestaria != null && !idPartidaPresupuestaria.isEmpty()) {
			sql.WHERE("SCS_GUARDIASTURNO.IDPARTIDAPRESUPUESTARIA IN ( " + idPartidaPresupuestaria + ")"  );
		}
		
		sql.ORDER_BY("SCS_GUARDIASTURNO.NOMBRE");
		return sql.toString();
	}
	
	
}
