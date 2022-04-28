package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.mappers.ScsTurnoSqlProvider;

public class ScsTurnosSqlExtendsProvider extends ScsTurnoSqlProvider {

	public String comboTurnos(Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("IDTURNO, NOMBRE");
		sql.FROM("SCS_TURNO");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("FECHABAJA IS NULL");
		sql.ORDER_BY("NOMBRE");
		return sql.toString();
	}
	
	public String comboTurnosDesignacion(Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("IDTURNO, NOMBRE");
		sql.FROM("SCS_TURNO");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("FECHABAJA IS NULL");
		sql.WHERE("IDTIPOTURNO = '2' OR IDTIPOTURNO IS NULL");

		sql.ORDER_BY("NOMBRE");
		return sql.toString();
	}
	
	

	public String comboTurnosTipo(Short idInstitucion, String tipoturno) {

		SQL sql = new SQL();

		sql.SELECT("IDTURNO, NOMBRE");
		sql.FROM("SCS_TURNO");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("FECHABAJA IS NULL");
		if (tipoturno.equals("1")) {
			sql.WHERE("nvl(IDTIPOTURNO, '1') = '" + tipoturno + "'");
		}
		if (tipoturno.equals("2")) {
			sql.WHERE("nvl(IDTIPOTURNO, '2') = '" + tipoturno + "'");
		}
		sql.ORDER_BY("NOMBRE");
		return sql.toString();
	}

	public String busquedaTurnos(TurnosItem turnosItem, Short idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT(
				"turnos.idinstitucion idinstitucion, turnos.idturno idturno, turnos.nombre nombre, turnos.abreviatura abreviatura, area.nombre area, materi.nombre materia, zona.nombre zona, subzon.nombre subzona, cat.descripcion as grupofacturacion,(SELECT COUNT(*)from scs_inscripcionturno ins where ins.idinstitucion = turnos.idinstitucion and ins.idturno = turnos.idturno and (ins.fechabaja is null or trunc(ins.fechabaja) > trunc(sysdate) ) and (ins.fechavalidacion is not null and trunc(ins.fechavalidacion) <= trunc(sysdate) ) ) as nletrados, turnos.fechabaja as fechabaja,  LISTAGG(parjud.nombre, ';') WITHIN GROUP (ORDER BY parjud.idpartido) AS nombrePartidosJudiciales from scs_turno turnos ");

		sql.INNER_JOIN(
				"scs_materia materi ON materi.idinstitucion = turnos.idinstitucion and materi.idmateria = turnos.idmateria and materi.idarea = turnos.idarea");
		sql.INNER_JOIN("scs_area area ON area.idinstitucion = materi.idinstitucion and area.idarea = materi.idarea");
		sql.INNER_JOIN("scs_ordenacioncolas on scs_ordenacioncolas.idordenacioncolas = turnos.idordenacioncolas");
		sql.LEFT_OUTER_JOIN(
				"scs_subzona subzon ON subzon.idinstitucion = turnos.idinstitucion and subzon.idzona = turnos.idzona and subzon.idsubzona = turnos.idsubzona");
		sql.INNER_JOIN("scs_zona zona ON  zona.idinstitucion = turnos.idinstitucion and  zona.idzona = turnos.idzona");
		sql.INNER_JOIN(
				"scs_grupofacturacion grupof ON grupof.idinstitucion = turnos.idinstitucion and grupof.idgrupofacturacion = turnos.idgrupofacturacion");
		sql.LEFT_OUTER_JOIN(
				"scs_subzonapartido subpar ON subpar.idsubzona = turnos.idsubzona and subpar.idinstitucion = turnos.idinstitucion and turnos.idzona = subpar.idzona");
		sql.LEFT_OUTER_JOIN("cen_partidojudicial parjud ON  parjud.idpartido = subpar.idpartido");
		sql.LEFT_OUTER_JOIN(
				"scs_partidapresupuestaria partid ON partid.IDPARTIDAPRESUPUESTARIA = turnos.IDPARTIDAPRESUPUESTARIA and partid.idinstitucion = turnos.idinstitucion");
		sql.INNER_JOIN("gen_recursos_catalogos cat ON  cat.IDRECURSO = grupof.nombre and cat.IDLENGUAJE= 1");
		sql.WHERE("turnos.idinstitucion = '" + idInstitucion + "'");
		
		if (turnosItem.getAbreviatura() != null && turnosItem.getAbreviatura().toString() != "" && turnosItem.getNombre() != null && turnosItem.getNombre().toString() != "") {
			sql.WHERE("UPPER(turnos.abreviatura) like UPPER('%" + turnosItem.getAbreviatura() + "%') OR UPPER(turnos.nombre) like UPPER('%" + turnosItem.getNombre() + "%')");
			
		}else {
			if (turnosItem.getAbreviatura() != null && turnosItem.getAbreviatura().toString() != "") {
				sql.WHERE("UPPER(turnos.abreviatura) like UPPER('%" + turnosItem.getAbreviatura() + "%')");
			}
			if (turnosItem.getNombre() != null && turnosItem.getNombre().toString() != "") {
				sql.WHERE("UPPER(turnos.nombre) like UPPER('%" + turnosItem.getNombre() + "%')");
			}
		}
		if (turnosItem.getJurisdiccion() != null && turnosItem.getJurisdiccion() != "") {
			String condturnos ="(";
			for(int i = 0; i< turnosItem.getJurisdiccion().split(",").length; i++) {
				if(i>0) condturnos+=" or ";
				condturnos+="turnos.idjurisdiccion ='"+turnosItem.getJurisdiccion().split(",")[i]+"'";
			}
			condturnos+=")";
			sql.WHERE(condturnos);
		}
		if (turnosItem.getIdtipoturno() != null && turnosItem.getIdtipoturno() != "") {
			String condturnos ="(";
			for(int i = 0; i< turnosItem.getIdtipoturno().split(",").length; i++) {
				if(i>0) condturnos+=" or ";
				condturnos+="turnos.idtipoturno ='"+turnosItem.getIdtipoturno().split(",")[i]+"'";
			}
			condturnos+=")";
			sql.WHERE(condturnos);
		}
		if (turnosItem.getIdarea() != null && turnosItem.getIdarea() != "") {
			String condturnos ="(";
			for(int i = 0; i< turnosItem.getIdarea().split(",").length; i++) {
				if(i>0) condturnos+=" or ";
				condturnos+="area.idarea ='"+turnosItem.getIdarea().split(",")[i]+"'";
			}
			condturnos+=")";
			sql.WHERE(condturnos);
		}
		if (turnosItem.getIdturno() != null && turnosItem.getIdturno() != "") {
			String condturnos ="(";
			for(int i = 0; i< turnosItem.getIdturno().split(",").length; i++) {
				if(i>0) condturnos+=" or ";
				condturnos+="turnos.idturno ='"+turnosItem.getIdturno().split(",")[i]+"'";
			}
			condturnos+=")";
			sql.WHERE(condturnos);
		}
		if (turnosItem.getIdmateria() != null && turnosItem.getIdmateria() != "") {
			String condturnos ="(";
			for(int i = 0; i< turnosItem.getIdmateria().split(",").length; i++) {
				if(i>0) condturnos+=" or ";
				condturnos+="materi.idmateria ='"+turnosItem.getIdmateria().split(",")[i]+"'";
			}
			condturnos+=")";
			sql.WHERE(condturnos);
		}
		if (turnosItem.getIdzona() != null && turnosItem.getIdzona() != "") {
			String condturnos ="(";
			for(int i = 0; i< turnosItem.getIdzona().split(",").length; i++) {
				if(i>0) condturnos+=" or ";
				condturnos+="zona.idzona ='"+turnosItem.getIdzona().split(",")[i]+"'";
			}
			condturnos+=")";
			sql.WHERE(condturnos);
		}
		if (turnosItem.getIdzubzona() != null && turnosItem.getIdzubzona() != "") {
			String condturnos ="(";
			for(int i = 0; i< turnosItem.getIdzubzona().split(",").length; i++) {
				if(i>0) condturnos+=" or ";
				condturnos+="subzon.idsubzona ='"+turnosItem.getIdzubzona().split(",")[i]+"'";
			}
			condturnos+=")";
			sql.WHERE(condturnos);
		}
		if (turnosItem.getIdpartidapresupuestaria() != null && turnosItem.getIdpartidapresupuestaria() != "") {
			String condturnos ="(";
			for(int i = 0; i< turnosItem.getIdpartidapresupuestaria().split(",").length; i++) {
				if(i>0) condturnos+=" or ";
				condturnos+="partid.IDPARTIDAPRESUPUESTARIA ='"+turnosItem.getIdpartidapresupuestaria().split(",")[i]+"'";
			}
			condturnos+=")";
			sql.WHERE(condturnos);
		}
		if (turnosItem.getGrupofacturacion() != null && turnosItem.getGrupofacturacion() != "") {
			String condturnos ="(";
			for(int i = 0; i< turnosItem.getGrupofacturacion().split(",").length; i++) {
				if(i>0) condturnos+=" or ";
				condturnos+="grupof.idgrupofacturacion ='"+turnosItem.getGrupofacturacion().split(",")[i]+"'";
			}
			condturnos+=")";
			sql.WHERE(condturnos);
		}
		if (!turnosItem.isHistorico()) {
			sql.WHERE("turnos.fechabaja is null");
		}
		sql.WHERE(" rownum <= 200");
		sql.GROUP_BY("turnos.idturno ,\r\n" + "    turnos.nombre ,\r\n" + "    turnos.abreviatura ,\r\n"
				+ "    area.nombre ,\r\n" + "    materi.nombre ,\r\n" + "    zona.nombre ,\r\n"
				+ "    subzon.nombre ,  \r\n" + "    grupof.idgrupofacturacion ,\r\n"
				+ "    cat.DESCRIPCION , turnos.fechabaja,\r\n" + "     turnos.idinstitucion");
		sql.ORDER_BY("nombre");

		return sql.toString();

	}

	public String busquedaFichaTurnos(TurnosItem turnosItem, Short idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT(
				"turnos.idturno idturno, turnos.guardias idguardias,turnos.nombre nombre, turnos.idordenacioncolas idordenacioncolas,turnos.idjurisdiccion,turnos.abreviatura abreviatura,turnos.validarjustificaciones validarjustificaciones,turnos.idtipoturno idtipoturno,turnos.validarinscripciones validarinscripciones,turnos.designadirecta designadirecta,turnos.requisitos requisitos,turnos.idarea idarea,turnos.idmateria idmateria,turnos.idzona idzona,turnos.idpartidapresupuestaria idpartidapresupuestaria,turnos.idsubzona idsubzona,turnos.idpersona_ultimo idpersona_ultimo, turnos.descripcion descripcion,turnos.activarretriccionacredit activarretriccionacredit, turnos.letradoasistencias letradoasistencias,turnos.letradoactuaciones letradoactuaciones,turnos.codigoext codigoext, turnos.fechasolicitud_ultimo fechasolicitud_ultimo,turnos.visiblemovil visiblemovil,area.nombre area, materi.nombre materia, zona.nombre zona, subzon.nombre subzona,grupof.idgrupofacturacion idgrupofacturacion, cat.descripcion as grupofacturacion,(SELECT COUNT(*)from scs_inscripcionturno ins where ins.idinstitucion = turnos.idinstitucion and ins.idturno = turnos.idturno and (ins.fechabaja is null or trunc(ins.fechabaja) > trunc(sysdate) ) and (ins.fechavalidacion is not null and trunc(ins.fechavalidacion) <= trunc(sysdate) ) ) as nletrados, turnos.fechabaja as fechabaja from scs_turno turnos ");
		sql.INNER_JOIN("scs_ordenacioncolas on scs_ordenacioncolas.idordenacioncolas = turnos.idordenacioncolas");
		sql.INNER_JOIN(
				"scs_materia materi ON materi.idinstitucion = turnos.idinstitucion and materi.idmateria = turnos.idmateria and materi.idarea = turnos.idarea");
		sql.INNER_JOIN("scs_area area ON area.idinstitucion = materi.idinstitucion and area.idarea = materi.idarea");
		sql.LEFT_OUTER_JOIN(
				"scs_subzona subzon ON subzon.idinstitucion = turnos.idinstitucion and subzon.idzona = turnos.idzona and subzon.idsubzona = turnos.idsubzona");
		sql.INNER_JOIN("scs_zona zona ON  zona.idinstitucion = turnos.idinstitucion and  zona.idzona = turnos.idzona");
		sql.INNER_JOIN(
				"scs_grupofacturacion grupof ON grupof.idinstitucion = turnos.idinstitucion and grupof.idgrupofacturacion = turnos.idgrupofacturacion");
		sql.LEFT_OUTER_JOIN("cen_partidojudicial parjud ON  parjud.idpartido = subzon.idpartido");
		sql.LEFT_OUTER_JOIN(
				"scs_partidapresupuestaria partid ON partid.IDPARTIDAPRESUPUESTARIA = turnos.IDPARTIDAPRESUPUESTARIA and partid.idinstitucion = turnos.idinstitucion");
		sql.INNER_JOIN("gen_recursos_catalogos cat ON  cat.IDRECURSO = grupof.nombre and cat.IDLENGUAJE= 1");
		sql.WHERE("turnos.idinstitucion = '" + idInstitucion + "'");
		sql.WHERE("turnos.idturno ='" + turnosItem.getIdturno() + "'");
		sql.ORDER_BY("nombre");

		return sql.toString();

	}

	public String getIdTurno(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDTURNO) AS IDTURNO");
		sql.FROM("SCS_TURNO");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}

	public String getIdOrdenacion(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDORDENACIONCOLAS) AS IDORDENACIONCOLAS");
		sql.FROM("scs_ordenacioncolas");
		return sql.toString();
	}

	public String busquedaUltimoLetrado(String idTurno,Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("col.ncolegiado NUMEROCOLEGIADO");
		sql.SELECT("per.nombre NOMBREPERSONA");
		sql.SELECT("per.apellidos1 APELLIDOS1");
		sql.SELECT("per.apellidos2 APELLIDOS2");
		sql.SELECT("tur.nombre NOMBRETURNO");
		sql.FROM("scs_turno tur");
		sql.JOIN("cen_colegiado col on tur.idinstitucion = col.idinstitucion and tur.idpersona_ultimo = col.idpersona");
		sql.JOIN("cen_persona per on tur.idpersona_ultimo = per.idpersona");
		sql.WHERE("tur.idinstitucion = "+idInstitucion);
		sql.WHERE("tur.idturno = "+idTurno);

		return sql.toString();
	}

	public String resumenTurnoColaGuardia(String idTurno, String idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("SCS_TURNO.nombre");
		sql.SELECT("SCS_ZONA.IDZONA");
		sql.SELECT("SCS_TURNO.IDSUBZONA");
		sql.SELECT("SCS_MATERIA.NOMBRE AS NOMBRE_MATERIA");
		sql.SELECT("SCS_ZONA.NOMBRE AS NOMBRE_ZONA");
		sql.SELECT("(\r\n" + "		SELECT\r\n" + "			COUNT(*)\r\n" + "		FROM\r\n"
				+ "			SCS_INSCRIPCIONTURNO\r\n" + "		WHERE\r\n"
				+ "			(SCS_INSCRIPCIONTURNO.FECHABAJA IS NULL\r\n"
				+ "			OR TRUNC(SCS_INSCRIPCIONTURNO.FECHABAJA)>TRUNC(SYSDATE))\r\n"
				+ "			AND (SCS_INSCRIPCIONTURNO.FECHAVALIDACION IS NOT NULL\r\n"
				+ "			AND TRUNC(SCS_INSCRIPCIONTURNO.FECHAVALIDACION)<= TRUNC(SYSDATE))\r\n"
				+ "			AND SCS_INSCRIPCIONTURNO.IDINSTITUCION = SCS_TURNO.IDINSTITUCION\r\n"
				+ "			AND SCS_INSCRIPCIONTURNO.IDTURNO = SCS_TURNO.IDTURNO ) AS NUMEROINSCRITOSTURNO");
		sql.FROM("SCS_TURNO");
		sql.JOIN("SCS_MATERIA ON\r\n" + "		SCS_TURNO.IDINSTITUCION = SCS_MATERIA.IDINSTITUCION\r\n"
				+ "		AND SCS_TURNO.IDAREA = SCS_MATERIA.IDAREA\r\n"
				+ "		AND SCS_TURNO.IDMATERIA = SCS_MATERIA.IDMATERIA");
		sql.JOIN("SCS_ZONA ON\r\n" + "		SCS_TURNO.IDINSTITUCION = SCS_ZONA.IDINSTITUCION\r\n"
				+ "		AND SCS_TURNO.IDZONA = SCS_ZONA.IDZONA");
		sql.WHERE("IDTURNO=" + idTurno);
		sql.WHERE("SCS_TURNO.IDINSTITUCION=" + idInstitucion);

		return sql.toString();
	}

	public String busquedaColaOficio(TurnosItem turnosItem,String strDate,String busquedaOrden, Short idInstitucion) {

		SQL sql = new SQL();
		SQL sql2 = new SQL();
		SQL sql3 = new SQL();
		SQL sql4 = new SQL();
		SQL sqls3 = new SQL();
		SQL sqls4 = new SQL();
		SQL sqls5 = new SQL();

		if(busquedaOrden == null || busquedaOrden.length() == 0) {
			busquedaOrden = "ANTIGUEDADCOLA";
		}

		sql4.SELECT("(CASE\r\n" +
				"				WHEN Ins.Fechavalidacion IS NOT NULL\r\n" +
				"				AND TRUNC(Ins.Fechavalidacion) <= NVL(TO_DATE('"+strDate+"','DD/MM/RRRR'), Ins.Fechavalidacion)\r\n" +
				"				AND (Ins.Fechabaja IS NULL\r\n" +
				"				OR TRUNC(Ins.Fechabaja) > TO_DATE(NVL('"+strDate+"', '01/01/1900'),'DD/MM/RRRR')) THEN '1'\r\n" +
				"				ELSE '0'\r\n" +
				"				END) Activo,\r\n" +
				"				Ins.Idinstitucion,\r\n" +
				"				Ins.Idturno,\r\n" +
				"				Per.Idpersona,\r\n" +
				"				TRUNC(Ins.fechavalidacion) AS Fechavalidacion,\r\n" +
				"				TRUNC(Ins.fechabaja) AS fechabajapersona,\r\n" +
				"				ins.fechasolicitud AS fechasolicitud,\r\n" +
				"				Per.Nifcif,\r\n" +
				"				Per.Nombre as nombrepersona,\r\n" +
				"				Per.Apellidos1,\r\n" +
				"				DECODE(Per.Apellidos2, NULL, '', ' ' || Per.Apellidos2) apellidos2,\r\n" +
				"				Per.Apellidos1 || DECODE(Per.Apellidos2, NULL, '', ' ' || Per.Apellidos2) ALFABETICOAPELLIDOS,\r\n" +
				"				DECODE(Col.Comunitario, '1', Col.Ncomunitario, Col.Ncolegiado) NUMEROCOLEGIADO,\r\n" +
				"				Per.Fechanacimiento FECHANACIMIENTO,\r\n" +
				"				Ins.Fechavalidacion AS ANTIGUEDADCOLA,(\r\n" +
				"					SELECT\r\n" +
				"						COUNT(1) numero\r\n" +
				"						FROM scs_saltoscompensaciones salto\r\n" +
				"						WHERE salto.idinstitucion = tur.idinstitucion\r\n" +
				"							AND   salto.idturno = tur.IDTURNO\r\n" +
				"							AND   salto.idguardia IS NULL\r\n" +
				"							AND   salto.saltoocompensacion = 'S'\r\n" +
				"							AND   salto.fechacumplimiento IS NULL\r\n" +
				"							and   salto.idpersona = ins.IDPERSONA\r\n" +
				"					)  as saltos,(\r\n" +
				"						SELECT\r\n" +
				"							COUNT(1) numero FROM scs_saltoscompensaciones salto\r\n" +
				"						WHERE\r\n" +
				"							salto.idinstitucion = tur.idinstitucion\r\n" +
				"							AND   salto.idturno = tur.IDTURNO\r\n" +
				"							AND   salto.idguardia IS NULL\r\n" +
				"							AND   salto.saltoocompensacion = 'C'\r\n" +
				"							AND   salto.fechacumplimiento IS NULL\r\n" +
				"							and   salto.idpersona = ins.IDPERSONA\r\n" +
				"					)  as compensaciones");

		sql4.FROM("scs_inscripcionturno ins");
		sql4.INNER_JOIN("cen_persona per ON per.IDPERSONA = ins.IDPERSONA");
		sql4.INNER_JOIN("cen_colegiado col ON col.idpersona = per.IDPERSONA and col.IDINSTITUCION = ins.IDINSTITUCION and col.IDPERSONA = ins.IDPERSONA");
		sql4.INNER_JOIN("scs_turno tur ON tur.IDTURNO = ins.IDTURNO and tur.IDINSTITUCION = col.IDINSTITUCION");
		if(!turnosItem.isHistorico()) {
			sql4.WHERE("ins.fechabaja is null");
		}
		sql4.WHERE("Ins.Fechavalidacion IS NOT NULL "+
				"AND tur.Idinstitucion = '"+idInstitucion+"'" +
				"AND tur.Idturno = '"+turnosItem.getIdturno()+"'");
		sql4.ORDER_BY(""+busquedaOrden+"");


		sqls5.SELECT("consulta4.* from(SELECT ROWNUM AS orden,consulta3.* FROM (SELECT \r\n" +
				"	(CASE\r\n" +
				"		WHEN Ins.Fechavalidacion IS NOT NULL\r\n" +
				"		AND TRUNC(Ins.Fechavalidacion) <= NVL(TO_DATE('"+strDate+"','DD/MM/RRRR'), Ins.Fechavalidacion)\r\n" +
				"		AND (Ins.Fechabaja IS NULL\r\n" +
				"		OR TRUNC(Ins.Fechabaja) > TO_DATE(NVL('"+strDate+"', '01/01/1900'),'DD/MM/RRRR')) THEN '1'\r\n" +
				"		ELSE '0'\r\n" +
				"	END) Activo,\r\n" +
				"	Ins.Idinstitucion,\r\n" +
				"	Ins.Idturno,\r\n" +
				"	Per.Idpersona,\r\n" +
				"	TRUNC(Ins.fechavalidacion) AS Fechavalidacion,\r\n" +
				"	TRUNC(Ins.fechabaja) AS fechabajapersona,\r\n" +
				"    ins.fechasolicitud AS fechasolicitud,\r\n" +
				"	Per.Nifcif,\r\n" +
				"	Per.Nombre as nombrepersona,\r\n" +
				"	Per.Apellidos1,\r\n" +
				"	DECODE(Per.Apellidos2, NULL, '', ' ' || Per.Apellidos2) apellidos2,\r\n" +
				"	Per.Apellidos1 || DECODE(Per.Apellidos2, NULL, '', ' ' || Per.Apellidos2) ALFABETICOAPELLIDOS,\r\n" +
				"	DECODE(Col.Comunitario, '1', Col.Ncomunitario, Col.Ncolegiado) NUMEROCOLEGIADO,\r\n" +
				"	Per.Fechanacimiento FECHANACIMIENTO,\r\n" +
				"	Ins.Fechavalidacion AS ANTIGUEDADCOLA,\r\n" +
				"     (\r\n" +
				"        SELECT\r\n" +
				"            COUNT(1) numero\r\n" +
				"        FROM\r\n" +
				"            scs_saltoscompensaciones salto\r\n" +
				"        WHERE\r\n" +
				"            salto.idinstitucion = tur.idinstitucion\r\n" +
				"            AND   salto.idturno = tur.IDTURNO\r\n" +
				"            AND   salto.idguardia IS NULL\r\n" +
				"            AND   salto.saltoocompensacion = 'S'\r\n" +
				"            AND   salto.fechacumplimiento IS NULL\r\n" +
				"            and   salto.idpersona = ins.IDPERSONA\r\n" +
				"    )  as saltos,\r\n" +
				"     (\r\n" +
				"        SELECT\r\n" +
				"            COUNT(1) numero\r\n" +
				"        FROM\r\n" +
				"            scs_saltoscompensaciones salto\r\n" +
				"        WHERE\r\n" +
				"            salto.idinstitucion = tur.idinstitucion\r\n" +
				"            AND   salto.idturno = tur.IDTURNO\r\n" +
				"            AND   salto.idguardia IS NULL\r\n" +
				"            AND   salto.saltoocompensacion = 'C'\r\n" +
				"            AND   salto.fechacumplimiento IS NULL\r\n" +
				"            and   salto.idpersona = ins.IDPERSONA\r\n" +
				"    )  as compensaciones");

		sqls5.FROM("scs_inscripcionturno ins");
		sqls5.INNER_JOIN("cen_persona per ON per.IDPERSONA = ins.IDPERSONA");
		sqls5.INNER_JOIN("cen_colegiado col ON col.idpersona = per.IDPERSONA and col.IDINSTITUCION = ins.IDINSTITUCION and col.IDPERSONA = ins.IDPERSONA ");
		sqls5.INNER_JOIN("scs_turno tur ON tur.IDTURNO = ins.IDTURNO and tur.IDINSTITUCION = col.IDINSTITUCION");

		sqls5.WHERE("Ins.Fechavalidacion IS NOT NULL "+
				"AND tur.Idinstitucion ='"+idInstitucion+"'" +
				"AND tur.Idturno = '"+turnosItem.getIdturno()+"'");

		sqls3.SELECT(" * from(\r\n" +
				"SELECT tabla_nueva.* FROM tabla_nueva, tabla_nueva2\r\n" +
				"WHERE tabla_nueva.orden>tabla_nueva2.orden ORDER BY tabla_nueva.orden asc)\r\n" +
				"UNION\r\n" +
				"SELECT * FROM (SELECT tabla_nueva.* FROM tabla_nueva, tabla_nueva2\r\n" +
				"WHERE tabla_nueva.orden<=tabla_nueva2.orden ORDER BY tabla_nueva.orden asc)\r\n" +
				") consulta_total where activo = '1' ");


		if(turnosItem.getIdpersona_ultimo() != null) {
			sqls5.ORDER_BY(busquedaOrden+") consulta3)consulta4 WHERE idpersona = \r\n" +
					"( SELECT IDPERSONA_ULTIMO FROM SCS_TURNO\r\n" +
					"        where Idinstitucion = '"+idInstitucion+"'"+
					"		AND Idturno = '"+turnosItem.getIdturno()+"'" +
					"	) )"+sqls3.toString());

		}else {
			sqls5.ORDER_BY(busquedaOrden+") consulta3 WHERE activo = 1)consulta4)"+sqls3.toString());
		}

		sql3.SELECT("ROWNUM AS orden,consulta.* ");
		sql3.FROM("("+sql4.toString()+") consulta WHERE activo = 1) consulta2),tabla_nueva2 AS ("+sqls5.toString());


		sql2.SELECT("consulta2.*");
		sql2.FROM("("+sql3.toString());

		sql.SELECT("ROWNUM AS  orden_cola,consulta_total.* from(WITH tabla_nueva AS ("+sql2.toString());

//		try {
//			sqlSession.getConnection();
//			List<TurnosItem> turno =  (List<TurnosItem>) sqlSession.getConnection().prepareStatement(sql.toString()).executeQuery();
//
//		} catch (SQLException e) {
//			System.out.println(e.getMessage());
//		}

		return sql.toString();

	}

	public String busquedaColaOficio2(TurnosItem turnosItem,String strDate,String busquedaOrden, Short idInstitucion) {
		SQL sql = new SQL();

		if(busquedaOrden == null || busquedaOrden.length() == 0) {
			busquedaOrden = "ANTIGUEDADCOLA";
		}
		sql.SELECT("ROWNUM AS  orden_cola,consulta_total.* from(WITH tabla_nueva AS (SELECT consulta2.*\r\n" +
				"FROM (SELECT ROWNUM AS orden,consulta.* \r\n" +
				"FROM (SELECT (CASE\r\n" +
				"				WHEN Ins.Fechavalidacion IS NOT NULL\r\n" +
				"				AND TRUNC(Ins.Fechavalidacion) <= NVL('"+strDate+"', Ins.Fechavalidacion)\r\n" +
				"				AND (Ins.Fechabaja IS NULL\r\n" +
				"				OR TRUNC(Ins.Fechabaja) > NVL('"+strDate+"', '01/01/1900')) THEN '1'\r\n" +
				"				ELSE '0'\r\n" +
				"				END) Activo,\r\n" +
				"				Ins.Idinstitucion,\r\n" +
				"				Ins.Idturno,\r\n" +
				"				Per.Idpersona,\r\n" +
				"				TRUNC(Ins.fechavalidacion) AS Fechavalidacion,\r\n" +
				"				TRUNC(Ins.fechabaja) AS fechabajapersona,\r\n" +
				"				ins.fechasolicitud AS fechasolicitud,\r\n" +
				"				Per.Nifcif,\r\n" +
				"				Per.Nombre as nombrepersona,\r\n" +
				"				Per.Apellidos1,\r\n" +
				"				DECODE(Per.Apellidos2, NULL, '', ' ' || Per.Apellidos2) apellidos2,\r\n" +
				"				Per.Apellidos1 || DECODE(Per.Apellidos2, NULL, '', ' ' || Per.Apellidos2) ALFABETICOAPELLIDOS,\r\n" +
				"				DECODE(Col.Comunitario, '1', Col.Ncomunitario, Col.Ncolegiado) NUMEROCOLEGIADO,\r\n" +
				"				Per.Fechanacimiento FECHANACIMIENTO,\r\n" +
				"				Ins.Fechavalidacion AS ANTIGUEDADCOLA,(\r\n" +
				"					SELECT\r\n" +
				"						COUNT(1) numero\r\n" +
				"						FROM scs_saltoscompensaciones salto\r\n" +
				"						WHERE salto.idinstitucion = tur.idinstitucion\r\n" +
				"							AND   salto.idturno = tur.IDTURNO\r\n" +
				"							AND   salto.idguardia IS NULL\r\n" +
				"							AND   salto.saltoocompensacion = 'S'\r\n" +
				"							AND   salto.fechacumplimiento IS NULL\r\n" +
				"							and   salto.idpersona = ins.IDPERSONA\r\n" +
				"					)  as saltos,(\r\n" +
				"						SELECT\r\n" +
				"							COUNT(1) numero FROM scs_saltoscompensaciones salto\r\n" +
				"						WHERE\r\n" +
				"							salto.idinstitucion = tur.idinstitucion\r\n" +
				"							AND   salto.idturno = tur.IDTURNO\r\n" +
				"							AND   salto.idguardia IS NULL\r\n" +
				"							AND   salto.saltoocompensacion = 'C'\r\n" +
				"							AND   salto.fechacumplimiento IS NULL\r\n" +
				"							and   salto.idpersona = ins.IDPERSONA\r\n" +
				"					)  as compensaciones");
		sql.FROM(" scs_inscripcionturno ins");
		sql.INNER_JOIN("cen_persona per ON per.IDPERSONA = ins.IDPERSONA");
		sql.INNER_JOIN("cen_colegiado col ON col.idpersona = per.IDPERSONA and col.IDINSTITUCION = ins.IDINSTITUCION and col.IDPERSONA = ins.IDPERSONA");
		sql.INNER_JOIN("scs_turno tur ON tur.IDTURNO = ins.IDTURNO and tur.IDINSTITUCION = col.IDINSTITUCION");
		sql.WHERE("(Ins.Fechavalidacion IS NOT NULL AND Ins.Fechavalidacion <= '"+strDate+"' AND tur.Idinstitucion = '"+idInstitucion+"'AND tur.Idturno = '"+turnosItem.getIdturno()+"')");
		sql.ORDER_BY("/*aqui debemos de consultar primero el orden que vamos a ordenar.*/\r\n" +busquedaOrden+
				"          ) consulta ) consulta2)\r\n" +
				"SELECT * from(\r\n" +
				"SELECT  tabla_nueva.* FROM tabla_nueva)\r\n" +
				"\r\n" +
				") consulta_total where activo = '1'\r\n" +
				"");

		return sql.toString();

	}

	/**
	 * updateUltimoGuardias
	 *
	 * @param turnosItem
	 * @param idInstitucion
	 * @return
	 */
	public String updateUltimoGuardias(TurnosItem turnosItem,Short idInstitucion) {
		SQL sql2 = new SQL();
		sql2.SELECT("FECHASUSCRIPCION");
		sql2.FROM("SCS_INSCRIPCIONGUARDIA");
		sql2.WHERE("IDINSTITUCION = '"+idInstitucion+"'");
		sql2.WHERE("IDPERSONA = '"+turnosItem.getIdpersona()+"'");
		sql2.WHERE("IDTURNO = '"+turnosItem.getIdturno()+"'");
		sql2.WHERE("FECHABAJA IS NULL");
		sql2.WHERE("IDGUARDIA = '"+turnosItem.getIdguardias()+"'");

		SQL sql = new SQL();

		sql.UPDATE("SCS_GUARDIASTURNO");
		sql.SET("IDPERSONA_ULTIMO ='"+turnosItem.getIdpersona()+"',FECHASUSCRIPCION_ULTIMO = ("+sql2+")");
		sql.WHERE("IDINSTITUCION ='"+idInstitucion+"'");
		sql.WHERE("IDTURNO = '"+turnosItem.getIdturno()+"'");
		sql.WHERE("IDGUARDIA = '"+turnosItem.getIdguardias()+"'");

		return sql.toString();
	}

	public String updateUltimo(TurnosItem turnosItem,Short idInstitucion) {
		SQL sql = new SQL();

		sql.UPDATE("scs_turno set idpersona_ultimo ='"+turnosItem.getIdpersona()+"',fechasolicitud_ultimo = (select SCS_INSCRIPCIONTURNO.FECHASOLICITUD from SCS_INSCRIPCIONTURNO "
				+ "where idinstitucion = '"+idInstitucion+"'and idpersona='"+turnosItem.getIdpersona()+"' and idturno ='"+turnosItem.getIdturno()+"'and fechabaja is null) where idinstitucion ='"+idInstitucion+"'and idturno = '"+turnosItem.getIdturno()+"'");
		return sql.toString();
	}


	public String busquedaColaGuardia(TurnosItem turnosItem, String strDate, String busquedaOrden,
									  Short idInstitucion) {

		SQL sql = new SQL();
		SQL sql2 = new SQL();
		SQL sql3 = new SQL();
		SQL sql4 = new SQL();
		SQL sqls3 = new SQL();
		SQL sqls4 = new SQL();
		SQL sqls5 = new SQL();

		sql4.SELECT("(CASE\r\n" +
				"		WHEN Ins.Fechavalidacion IS NOT NULL\r\n" +
				"		AND TRUNC(Ins.Fechavalidacion) <= NVL(TO_DATE('"+strDate+"','DD/MM/RRRR'), Ins.Fechavalidacion)\r\n" +
				"		THEN '1'\r\n" +
				"		ELSE '0'\r\n" +
				"	END) Activo,\r\n" +
				"	Ins.Idinstitucion,\r\n" +
				"	Ins.Idturno,\r\n" +
				"	Ins.Idguardia,\r\n" +
				"	Per.Idpersona,\r\n" +
				"	Ins.fechasuscripcion AS Fechasuscripcion,\r\n" +
				"	TRUNC(Ins.fechavalidacion) AS Fechavalidacion,\r\n" +
				"	TRUNC(Ins.fechabaja) AS fechabajaguardia,\r\n" +
				"	Per.Nifcif,\r\n" +
				"	Per.Nombre as nombreguardia,\r\n" +
				"	Per.Apellidos1,\r\n" +
				"	DECODE(Per.Apellidos2, NULL, '', ' ' || Per.Apellidos2) apellidos2,\r\n" +
				"	Per.Apellidos1 || DECODE(Per.Apellidos2, NULL, '', ' ' || Per.Apellidos2) ALFABETICOAPELLIDOS,\r\n" +
				"	DECODE(Col.Comunitario, '1', Col.Ncomunitario, Col.Ncolegiado) NUMEROCOLEGIADO,\r\n" +
				"	Per.Fechanacimiento FECHANACIMIENTO,\r\n" +
				"	Ins.Fechavalidacion AS ANTIGUEDADCOLA,\r\n" +
				"	DECODE(Gua.Porgrupos, '1', Gru.IDGRUPOGUARDIACOLEGIADO, NULL) AS Idgrupoguardiacolegiado,\r\n" +
				"	DECODE(Gua.Porgrupos, '1', Gru.IDGRUPOGUARDIA, NULL) AS Grupo,\r\n" +
				"	DECODE(Gua.Porgrupos, '1', Grg.NUMEROGRUPO, NULL) AS numeroGrupo,\r\n" +
				"	DECODE(Gua.Porgrupos, '1', Gru.ORDEN, NULL) AS Ordengrupo,\r\n" +
				"         (\r\n" +
				"        SELECT\r\n" +
				"            COUNT(1) numero\r\n" +
				"        FROM\r\n" +
				"            scs_saltoscompensaciones salto\r\n" +
				"        WHERE\r\n" +
				"            salto.idinstitucion = gua.idinstitucion\r\n" +
				"            AND   salto.idturno = gua.IDTURNO\r\n" +
				"            AND   salto.idguardia =gua.idguardia\r\n" +
				"            AND   salto.saltoocompensacion = 'S'\r\n" +
				"            AND   salto.fechacumplimiento IS NULL\r\n" +
				"            and   salto.idpersona = ins.IDPERSONA\r\n" +
				"    )  as saltos,\r\n" +
				"     (\r\n" +
				"        SELECT\r\n" +
				"            COUNT(1) numero\r\n" +
				"        FROM\r\n" +
				"            scs_saltoscompensaciones salto\r\n" +
				"        WHERE\r\n" +
				"            salto.idinstitucion = gua.idinstitucion\r\n" +
				"            AND   salto.idturno = gua.IDTURNO\r\n" +
				"            AND   salto.idguardia = gua.idguardia\r\n" +
				"            AND   salto.saltoocompensacion = 'C'\r\n" +
				"            AND   salto.fechacumplimiento IS NULL\r\n" +
				"            and   salto.idpersona = ins.IDPERSONA\r\n" +
				"    )  as compensaciones");

		sql4.FROM("scs_inscripcionguardia  ins");
		sql4.INNER_JOIN("cen_persona per ON per.IDPERSONA = ins.IDPERSONA");
		sql4.INNER_JOIN("cen_colegiado col ON col.idpersona = per.IDPERSONA and col.IDINSTITUCION = ins.IDINSTITUCION and col.IDPERSONA = ins.IDPERSONA");
		sql4.INNER_JOIN("scs_guardiasturno gua ON gua.idturno = ins.idturno and gua.idguardia = ins.idguardia and gua.IDINSTITUCION = ins.IDINSTITUCION");
		sql4.LEFT_OUTER_JOIN("scs_grupoguardiacolegiado gru ON gru.IDINSTITUCION = ins.IDINSTITUCION and gru.IDTURNO = ins.IDTURNO and gru.IDGUARDIA = ins.IDGUARDIA and gru.IDPERSONA = per.IDPERSONA and gru.FECHASUSCRIPCION = ins.FECHASUSCRIPCION");
		sql4.LEFT_OUTER_JOIN("scs_grupoguardia grg ON grg.IDGRUPOGUARDIA = gru.IDGRUPOGUARDIA");
		sql4.WHERE("Ins.Fechavalidacion IS NOT NULL "+
				"AND Gua.Idinstitucion = '"+idInstitucion+"'" +
				"AND Gua.Idturno = '"+turnosItem.getIdturno()+"'"+
				"AND Gua.idguardia = '"+turnosItem.getIdcomboguardias()+"'");
		if(busquedaOrden != null && busquedaOrden.length() > 0) {
			sql4.ORDER_BY(""+busquedaOrden+",numeroGrupo,\r\n" +
					"	ordengrupo, \r\n" +
					"	Ins.FECHASUSCRIPCION,\r\n" +
					"	Ins.Idpersona");
		}else {
			sql4.ORDER_BY("numeroGrupo,\r\n" +
					"	ordengrupo, \r\n" +
					"	Ins.FECHASUSCRIPCION,\r\n" +
					"	Ins.Idpersona");
		}



		sqls5.SELECT("consulta4.* from(SELECT ROWNUM AS orden,consulta3.* FROM (SELECT \r\n" +
				"	(CASE\r\n" +
				"		WHEN Ins.Fechavalidacion IS NOT NULL\r\n" +
				"		AND TRUNC(Ins.Fechavalidacion) <= NVL(TO_DATE('"+strDate+"','DD/MM/RRRR'), Ins.Fechavalidacion)\r\n" +
				"		THEN '1'\r\n" +
				"		ELSE '0'\r\n" +
				"	END) Activo,\r\n" +
				"	Ins.Idinstitucion,\r\n" +
				"	Ins.Idturno,\r\n" +
				"	Ins.Idguardia,\r\n" +
				"	Per.Idpersona,\r\n" +
				"	Ins.fechasuscripcion AS Fechasuscripcion,\r\n" +
				"	TRUNC(Ins.fechavalidacion) AS Fechavalidacion,\r\n" +
				"	TRUNC(Ins.fechabaja) AS fechabajaguardia,\r\n" +
				"	Per.Nifcif,\r\n" +
				"	Per.Nombre,\r\n" +
				"	Per.Apellidos1,\r\n" +
				"	DECODE(Per.Apellidos2, NULL, '', ' ' || Per.Apellidos2) apellidos2,\r\n" +
				"	Per.Apellidos1 || DECODE(Per.Apellidos2, NULL, '', ' ' || Per.Apellidos2) ALFABETICOAPELLIDOS,\r\n" +
				"	DECODE(Col.Comunitario, '1', Col.Ncomunitario, Col.Ncolegiado) NUMEROCOLEGIADO,\r\n" +
				"	Per.Fechanacimiento FECHANACIMIENTO,\r\n" +
				"	Ins.Fechavalidacion AS ANTIGUEDADCOLA,\r\n" +
				"	DECODE(Gua.Porgrupos, '1', Gru.IDGRUPOGUARDIACOLEGIADO, NULL) AS Idgrupoguardiacolegiado,\r\n" +
				"	DECODE(Gua.Porgrupos, '1', Gru.IDGRUPOGUARDIA, NULL) AS Grupo,\r\n" +
				"	DECODE(Gua.Porgrupos, '1', Grg.NUMEROGRUPO, NULL) AS numeroGrupo,\r\n" +
				"	DECODE(Gua.Porgrupos, '1', Gru.ORDEN, NULL) AS Ordengrupo,\r\n" +
				"         (\r\n" +
				"        SELECT\r\n" +
				"            COUNT(1) numero\r\n" +
				"        FROM\r\n" +
				"            scs_saltoscompensaciones salto\r\n" +
				"        WHERE\r\n" +
				"            salto.idinstitucion = gua.idinstitucion\r\n" +
				"            AND   salto.idturno = gua.IDTURNO\r\n" +
				"            AND   salto.idguardia =gua.idguardia\r\n" +
				"            AND   salto.saltoocompensacion = 'S'\r\n" +
				"            AND   salto.fechacumplimiento IS NULL\r\n" +
				"            and   salto.idpersona = ins.IDPERSONA\r\n" +
				"    )  as saltos,\r\n" +
				"     (\r\n" +
				"        SELECT\r\n" +
				"            COUNT(1) numero\r\n" +
				"        FROM\r\n" +
				"            scs_saltoscompensaciones salto\r\n" +
				"        WHERE\r\n" +
				"            salto.idinstitucion = gua.idinstitucion\r\n" +
				"            AND   salto.idturno = gua.IDTURNO\r\n" +
				"            AND   salto.idguardia = gua.idguardia\r\n" +
				"            AND   salto.saltoocompensacion = 'C'\r\n" +
				"            AND   salto.fechacumplimiento IS NULL\r\n" +
				"            and   salto.idpersona = ins.IDPERSONA\r\n" +
				"    )  as compensaciones");

		sqls5.FROM("scs_inscripcionguardia  ins");
		sqls5.INNER_JOIN("cen_persona per ON per.IDPERSONA = ins.IDPERSONA");
		sqls5.INNER_JOIN("cen_colegiado col ON col.idpersona = per.IDPERSONA and col.IDINSTITUCION = ins.IDINSTITUCION and col.IDPERSONA = ins.IDPERSONA");
		sqls5.INNER_JOIN("scs_guardiasturno gua ON gua.idturno = ins.idturno and gua.idguardia = ins.idguardia and gua.IDINSTITUCION = ins.IDINSTITUCION");
		sqls5.LEFT_OUTER_JOIN("scs_grupoguardiacolegiado gru ON gru.IDINSTITUCION = ins.IDINSTITUCION and gru.IDTURNO = ins.IDTURNO and gru.IDGUARDIA = ins.IDGUARDIA and gru.IDPERSONA = per.IDPERSONA and gru.FECHASUSCRIPCION = ins.FECHASUSCRIPCION");
		sqls5.LEFT_OUTER_JOIN("scs_grupoguardia grg ON grg.IDGRUPOGUARDIA = gru.IDGRUPOGUARDIA");
		sqls5.WHERE("Ins.Fechavalidacion IS NOT NULL "+
				"AND Gua.Idinstitucion = '"+idInstitucion+"'" +
				"AND Gua.Idturno = '"+turnosItem.getIdturno()+"'"+
				"AND Gua.idguardia = '"+turnosItem.getIdcomboguardias()+"'");

		sqls3.SELECT(" *\r\n"
				+ "        FROM\r\n"
				+ "            (\r\n"
				+ "                SELECT\r\n"
				+ "                    tabla_nueva.*\r\n"
				+ "                FROM\r\n"
				+ "                    tabla_nueva,\r\n"
				+ "                    tabla_nueva2\r\n"
				+ "                WHERE\r\n"
				+ "                   tabla_nueva.orden > tabla_nueva2.orden\r\n"
				+ "                ORDER BY\r\n"
				+ "                    tabla_nueva.orden ASC\r\n"
				+ "            )\r\n"
				+ "        UNION ALL\r\n"
				+ "        SELECT\r\n"
				+ "            *\r\n"
				+ "        FROM\r\n"
				+ "            (\r\n"
				+ "                SELECT\r\n"
				+ "                    tabla_nueva.*\r\n"
				+ "                FROM\r\n"
				+ "                    tabla_nueva,\r\n"
				+ "                    tabla_nueva2\r\n"
				+ "                WHERE\r\n"
				+ "                   tabla_nueva.orden <= tabla_nueva2.orden\r\n"
				+ "                ORDER BY\r\n"
				+ "                    tabla_nueva.orden ASC\r\n"
				+ "            )\r\n"
				+ "    ) consulta_total\r\n"
				+ "");


		if(turnosItem.getIdpersona_ultimo() != null) {
			if(busquedaOrden != null && busquedaOrden.length() > 0 ) {
				sqls5.ORDER_BY(""+busquedaOrden+",numeroGrupo,\r\n" +
						"	ordengrupo, \r\n" +
						"	Ins.FECHASUSCRIPCION,\r\n" +
						"	Ins.Idpersona )consulta3 WHERE activo = 1)consulta4 WHERE idpersona = \r\n" +
						"( SELECT IDPERSONA_ULTIMO FROM SCS_GUARDIASTURNO\r\n" +
						"        where Idinstitucion = '"+idInstitucion+"'"+
						"		AND Idturno = '"+turnosItem.getIdturno()+"'"
						+ "and idguardia ='"+turnosItem.getIdcomboguardias()+"'" +
						"	) )"+sqls3.toString());
			}else {
				sqls5.ORDER_BY("numeroGrupo,\r\n" +
						"	ordengrupo, \r\n" +
						"	Ins.FECHASUSCRIPCION,\r\n" +
						"	Ins.Idpersona )consulta3 WHERE activo = 1)consulta4 WHERE idpersona = \r\n" +
						"( SELECT IDPERSONA_ULTIMO FROM SCS_GUARDIASTURNO\r\n" +
						"        where Idinstitucion = '"+idInstitucion+"'"+
						"		AND Idturno = '"+turnosItem.getIdturno()+"'"
						+ "and idguardia ='"+turnosItem.getIdcomboguardias()+"'" +
						"	) )"+sqls3.toString());
			}

		}else {
			if(busquedaOrden != null && busquedaOrden.length() > 0 ) {
				sqls5.ORDER_BY(""+busquedaOrden+",numeroGrupo,\r\n" +
						"	ordengrupo, \r\n" +
						"	Ins.FECHASUSCRIPCION,\r\n" +
						"	Ins.Idpersona )consulta3 WHERE activo = 1)consulta4)"+sqls3.toString());
			}else {
				sqls5.ORDER_BY("numeroGrupo,\r\n" +
						"	ordengrupo, \r\n" +
						"	Ins.FECHASUSCRIPCION,\r\n" +
						"	Ins.Idpersona )consulta3 WHERE activo = 1)consulta4)"+sqls3.toString());
			}

		}

		sql3.SELECT("ROWNUM AS orden,consulta.* ");
		sql3.FROM("("+sql4.toString()+") consulta WHERE activo = 1) consulta2),tabla_nueva2 AS ("+sqls5.toString());


		sql2.SELECT("consulta2.*");
		sql2.FROM("("+sql3.toString());

		sql.SELECT("ROWNUM AS  orden_cola,consulta_total.* from(WITH tabla_nueva AS ("+sql2.toString());
		return sql.toString();

	}

	public String comboTurnosBusqueda(Short idInstitucion, String pantalla) {

		SQL sql = new SQL();

		sql.SELECT("IDTURNO, NOMBRE");
		sql.FROM("SCS_TURNO");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("FECHABAJA IS NULL");

		if (SigaConstants.EJG.equalsIgnoreCase(pantalla)) {
			sql.WHERE("IDTIPOTURNO <> 2 AND IDTIPOTURNO IS NOT NULL");
		}

		if (SigaConstants.GUARDIA.equalsIgnoreCase(pantalla) || SigaConstants.OFICIO.equalsIgnoreCase(pantalla)) {
			sql.WHERE("IDTIPOTURNO <> 1 AND IDTIPOTURNO IS NOT NULL");
		}

		sql.ORDER_BY("NOMBRE");

		return sql.toString();
	}
	
	public String comboEstados(Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("VALOR");
		sql.FROM("GEN_CATALOGOS_WS");
		
		return sql.toString();

	}
	
	public String selectInscripcionTurnoByTurno(Short idInstitucion, String idTurno) {

		SQL sql = new SQL();

		sql.SELECT("IDINSTITUCION,IDPERSONA, IDTURNO,FECHASOLICITUD");
		sql.FROM("SCS_INSCRIPCIONTURNO");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("IDTURNO = '" + idTurno + "'");
		sql.WHERE("FECHABAJA IS NULL");

		return sql.toString();
	}
	
	
	public String getObligatoriedadByTurno(Short idInstitucion, String idTurno) {

		SQL sql = new SQL();

		sql.SELECT("T.GUARDIAS");
		sql.FROM("SCS_TURNO T");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("IDTURNO = '" + idTurno + "'");
//DECODE(SCS_TURNO.GUARDIAS, 0, 'Obligatorias', DECODE(SCS_TURNO.GUARDIAS, 2, 'A elegir', 'Todas o ninguna')
		return sql.toString();
	}

}