package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.scs.TurnosItem;
import org.itcgae.siga.db.mappers.ScsTipoactuacionSqlProvider;
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

	public String busquedaTurnos(TurnosItem turnosItem, Short idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT(
				"turnos.idturno idturno, turnos.nombre nombre, turnos.abreviatura abreviatura, area.nombre area, materi.nombre materia, zona.nombre zona, subzon.nombre subzona, cat.descripcion as grupofacturacion,(SELECT COUNT(*)from scs_inscripcionturno ins where ins.idinstitucion = turnos.idinstitucion and ins.idturno = turnos.idturno and (ins.fechabaja is null or trunc(ins.fechabaja) > trunc(sysdate) ) and (ins.fechavalidacion is not null and trunc(ins.fechavalidacion) <= trunc(sysdate) ) ) as nletrados, turnos.fechabaja as fechabaja,  LISTAGG(parjud.nombre, ';') WITHIN GROUP (ORDER BY parjud.idpartido) AS nombrePartidosJudiciales from scs_turno turnos ");

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
		if (turnosItem.getAbreviatura() != null && turnosItem.getAbreviatura().toString() != "") {
			sql.WHERE("UPPER(turnos.abreviatura) like UPPER('%" + turnosItem.getAbreviatura() + "%')");
		}
		if (turnosItem.getNombre() != null && turnosItem.getNombre().toString() != "") {
			sql.WHERE("UPPER(turnos.nombre) like UPPER('%" + turnosItem.getNombre() + "%')");
		}
		if (turnosItem.getJurisdiccion() != null && turnosItem.getJurisdiccion() != "") {
			sql.WHERE("turnos.idjurisdiccion ='" + turnosItem.getJurisdiccion() + "'");
		}
		if (turnosItem.getIdtipoturno() != null && turnosItem.getIdtipoturno() != "") {
			sql.WHERE("turnos.idtipoturno = '" + turnosItem.getIdtipoturno() + "'");
		}
		if (turnosItem.getIdarea() != null && turnosItem.getIdarea() != "") {
			sql.WHERE("area.idarea = '" + turnosItem.getIdarea() + "'");
		}
		if (turnosItem.getIdmateria() != null && turnosItem.getIdmateria() != "") {
			sql.WHERE("materi.idmateria = '" + turnosItem.getIdmateria() + "'");
		}
		if (turnosItem.getIdzona() != null && turnosItem.getIdzona() != "") {
			sql.WHERE("zona.idzona = '" + turnosItem.getIdzona() + "'");
		}
		if (turnosItem.getIdzubzona() != null && turnosItem.getIdzubzona() != "") {
			sql.WHERE("subzon.idsubzona = '" + turnosItem.getIdzubzona() + "'");
		}
		if (turnosItem.getIdpartidapresupuestaria() != null && turnosItem.getIdpartidapresupuestaria() != "") {
			sql.WHERE("partid.IDPARTIDAPRESUPUESTARIA = '" + turnosItem.getIdpartidapresupuestaria() + "'");
		}
		if (turnosItem.getGrupofacturacion() != null && turnosItem.getGrupofacturacion() != "") {
			sql.WHERE("grupof.idgrupofacturacion  = '" + turnosItem.getGrupofacturacion() + "'");
		}
		if (!turnosItem.isHistorico()) {
			sql.WHERE("turnos.fechabaja is null");
		}
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

public String busquedaColaOficio(TurnosItem turnosItem,String strDate,String busquedaOrden, Short idInstitucion) {

		SQL sql = new SQL();
		SQL sql2 = new SQL();
		SQL sql3 = new SQL();
		SQL sql4 = new SQL();
		SQL sqls3 = new SQL();
		SQL sqls4 = new SQL();
		SQL sqls5 = new SQL();
		

		
		sql4.SELECT("(CASE\r\n" + 
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
				"		AND TRUNC(Ins.Fechavalidacion) <= NVL('"+strDate+"', Ins.Fechavalidacion)\r\n" + 
				"		AND (Ins.Fechabaja IS NULL\r\n" + 
				"		OR TRUNC(Ins.Fechabaja) > NVL('"+strDate+"', '01/01/1900')) THEN '1'\r\n" + 
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
				"UNION ALL\r\n" + 
				"SELECT * FROM (SELECT tabla_nueva.* FROM tabla_nueva, tabla_nueva2\r\n" + 
				"WHERE tabla_nueva.orden<=tabla_nueva2.orden ORDER BY tabla_nueva.orden asc)\r\n" + 
				") consulta_total");
		
		
		if(turnosItem.getIdpersonaUltimo() != null) {
			sqls5.ORDER_BY(busquedaOrden+") consulta3 WHERE activo = 1)consulta4 WHERE idpersona = \r\n" + 
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
				"		AND TRUNC(Ins.Fechavalidacion) <= NVL('"+strDate+"', Ins.Fechavalidacion)\r\n" + 
				"		AND (Ins.Fechabaja IS NULL\r\n" + 
				"		OR TRUNC(Ins.Fechabaja) > NVL('"+strDate+"', '01/01/1900')) THEN '1'\r\n" + 
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
		sql4.WHERE("ins.fechabaja is null");
		sql4.WHERE("Ins.Fechavalidacion IS NOT NULL "+
				"AND Gua.Idinstitucion = '"+idInstitucion+"'" + 
				"AND Gua.Idturno = '"+turnosItem.getIdturno()+"'"+
				"AND Gua.idguardia = '"+turnosItem.getIdcomboguardias()+"'");
		sql4.ORDER_BY(""+busquedaOrden+",numeroGrupo,\r\n" + 
				"	ordengrupo, \r\n" + 
				"	Ins.FECHASUSCRIPCION,\r\n" + 
				"	Ins.Idpersona");
		

		sqls5.SELECT("consulta4.* from(SELECT ROWNUM AS orden,consulta3.* FROM (SELECT \r\n" + 
				"	(CASE\r\n" + 
				"		WHEN Ins.Fechavalidacion IS NOT NULL\r\n" + 
				"		AND TRUNC(Ins.Fechavalidacion) <= NVL('"+strDate+"', Ins.Fechavalidacion)\r\n" + 
				"		AND (Ins.Fechabaja IS NULL\r\n" + 
				"		OR TRUNC(Ins.Fechabaja) > NVL('"+strDate+"', '01/01/1900')) THEN '1'\r\n" + 
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
		
		sqls3.SELECT(" * from(\r\n" + 
				"SELECT tabla_nueva.* FROM tabla_nueva, tabla_nueva2\r\n" + 
				"WHERE tabla_nueva.orden>tabla_nueva2.orden ORDER BY tabla_nueva.orden asc)\r\n" + 
				"UNION ALL\r\n" + 
				"SELECT * FROM (SELECT tabla_nueva.* FROM tabla_nueva, tabla_nueva2\r\n" + 
				"WHERE tabla_nueva.orden<=tabla_nueva2.orden ORDER BY tabla_nueva.orden asc)\r\n" + 
				") consulta_total");
		
		
		if(turnosItem.getIdpersonaUltimo() != null) {
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
			sqls5.ORDER_BY(""+busquedaOrden+",numeroGrupo,\r\n" + 
					"	ordengrupo, \r\n" + 
					"	Ins.FECHASUSCRIPCION,\r\n" + 
					"	Ins.Idpersona )consulta3 WHERE activo = 1)consulta4)"+sqls3.toString());
		}

		sql3.SELECT("ROWNUM AS orden,consulta.* ");
		sql3.FROM("("+sql4.toString()+") consulta WHERE activo = 1) consulta2),tabla_nueva2 AS ("+sqls5.toString());
			
		
		sql2.SELECT("consulta2.*");
		sql2.FROM("("+sql3.toString());	
		
		sql.SELECT("ROWNUM AS  orden_cola,consulta_total.* from(WITH tabla_nueva AS ("+sql2.toString());
		return sql.toString();

	}
}
