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
		sql.WHERE("IDINSTITUCION = '"+idInstitucion +"'");
		sql.WHERE("FECHABAJA IS NULL");
		sql.ORDER_BY("NOMBRE");
		return sql.toString();
	}

	public String busquedaTurnos(TurnosItem turnosItem, Short idInstitucion) {
		
		SQL sql = new SQL();
		sql.SELECT("turnos.idturno idturno, turnos.nombre nombre, turnos.abreviatura abreviatura, area.nombre area, materi.nombre materia, zona.nombre zona, subzon.nombre subzona, cat.descripcion as grupofacturacion,(SELECT COUNT(*)from scs_inscripcionturno ins where ins.idinstitucion = turnos.idinstitucion and ins.idturno = turnos.idturno and (ins.fechabaja is null or trunc(ins.fechabaja) > trunc(sysdate) ) and (ins.fechavalidacion is not null and trunc(ins.fechavalidacion) <= trunc(sysdate) ) ) as nletrados, turnos.fechabaja as fechabaja,  LISTAGG(parjud.nombre, ';') WITHIN GROUP (ORDER BY parjud.idpartido) AS nombrePartidosJudiciales from scs_turno turnos ");
		
		sql.INNER_JOIN("scs_materia materi ON materi.idinstitucion = turnos.idinstitucion and materi.idmateria = turnos.idmateria and materi.idarea = turnos.idarea");
		sql.INNER_JOIN("scs_area area ON area.idinstitucion = materi.idinstitucion and area.idarea = materi.idarea");
		sql.INNER_JOIN("scs_ordenacioncolas on scs_ordenacioncolas.idordenacioncolas = turnos.idordenacioncolas");
		sql.LEFT_OUTER_JOIN("scs_subzona subzon ON subzon.idinstitucion = turnos.idinstitucion and subzon.idzona = turnos.idzona and subzon.idsubzona = turnos.idsubzona");
		sql.INNER_JOIN("scs_zona zona ON  zona.idinstitucion = turnos.idinstitucion and  zona.idzona = turnos.idzona");
		sql.INNER_JOIN("scs_grupofacturacion grupof ON grupof.idinstitucion = turnos.idinstitucion and grupof.idgrupofacturacion = turnos.idgrupofacturacion");
		sql.LEFT_OUTER_JOIN("scs_subzonapartido subpar ON subpar.idsubzona = turnos.idsubzona and subpar.idinstitucion = turnos.idinstitucion and turnos.idzona = subpar.idzona");
		sql.LEFT_OUTER_JOIN("cen_partidojudicial parjud ON  parjud.idpartido = subpar.idpartido");
		sql.LEFT_OUTER_JOIN("scs_partidapresupuestaria partid ON partid.IDPARTIDAPRESUPUESTARIA = turnos.IDPARTIDAPRESUPUESTARIA and partid.idinstitucion = turnos.idinstitucion");
		sql.INNER_JOIN("gen_recursos_catalogos cat ON  cat.IDRECURSO = grupof.nombre and cat.IDLENGUAJE= 1");
		sql.WHERE("turnos.idinstitucion = '"+idInstitucion+"'");
		if(turnosItem.getAbreviatura() != null && turnosItem.getAbreviatura().toString() != "") {
			sql.WHERE("UPPER(turnos.abreviatura) like UPPER('%" + turnosItem.getAbreviatura() + "%')");
		}
		if(turnosItem.getNombre() != null && turnosItem.getNombre().toString() != "") {
			sql.WHERE("UPPER(turnos.nombre) like UPPER('%" + turnosItem.getNombre() + "%')");
		}
		if(turnosItem.getJurisdiccion() != null && turnosItem.getJurisdiccion() != "") {
			sql.WHERE("turnos.idjurisdiccion ='"+turnosItem.getJurisdiccion()+"'");
		}
		if(turnosItem.getIdtipoturno() != null && turnosItem.getIdtipoturno() != "") {
			sql.WHERE("turnos.idtipoturno = '"+turnosItem.getIdtipoturno()+"'");
		}
		if(turnosItem.getIdarea() != null && turnosItem.getIdarea() != "") {
			sql.WHERE("area.idarea = '"+turnosItem.getIdarea()+"'");
		}
		if(turnosItem.getIdmateria() != null && turnosItem.getIdmateria() != "") {
			sql.WHERE("materi.idmateria = '"+turnosItem.getIdmateria()+"'");
		}
		if(turnosItem.getIdzona() != null && turnosItem.getIdzona() != "") {
			sql.WHERE("zona.idzona = '"+turnosItem.getIdzona()+"'");
		}
		if(turnosItem.getIdzubzona() != null && turnosItem.getIdzubzona() != "") {
			sql.WHERE("subzon.idsubzona = '"+turnosItem.getIdzubzona()+"'");
		}
		if(turnosItem.getIdpartidapresupuestaria() != null && turnosItem.getIdpartidapresupuestaria() != "") {
			sql.WHERE("partid.IDPARTIDAPRESUPUESTARIA = '"+turnosItem.getIdpartidapresupuestaria()+"'");
		}
		if(turnosItem.getGrupofacturacion() != null && turnosItem.getGrupofacturacion() != "") {
			sql.WHERE("grupof.idgrupofacturacion  = '"+turnosItem.getGrupofacturacion()+"'");
		}
		if(!turnosItem.isHistorico()) {
			sql.WHERE("turnos.fechabaja is null");
		}
		sql.GROUP_BY("turnos.idturno ,\r\n" + 
				"    turnos.nombre ,\r\n" + 
				"    turnos.abreviatura ,\r\n" + 
				"    area.nombre ,\r\n" + 
				"    materi.nombre ,\r\n" + 
				"    zona.nombre ,\r\n" + 
				"    subzon.nombre ,  \r\n" + 
				"    grupof.idgrupofacturacion ,\r\n" + 
				"    cat.DESCRIPCION , turnos.fechabaja,\r\n" + 
				"     turnos.idinstitucion");
		sql.ORDER_BY("nombre");
			
		return sql.toString();
		
	}
	
public String busquedaFichaTurnos(TurnosItem turnosItem, Short idInstitucion) {
		
		SQL sql = new SQL();
		sql.SELECT("turnos.idturno idturno, turnos.guardias idguardias,turnos.nombre nombre, turnos.idordenacioncolas idordenacioncolas,turnos.idjurisdiccion,turnos.abreviatura abreviatura,turnos.validarjustificaciones validarjustificaciones,turnos.idtipoturno idtipoturno,turnos.validarinscripciones validarinscripciones,turnos.designadirecta designadirecta,turnos.requisitos requisitos,turnos.idarea idarea,turnos.idmateria idmateria,turnos.idzona idzona,turnos.idpartidapresupuestaria idpartidapresupuestaria,turnos.idsubzona idsubzona,turnos.idpersona_ultimo idpersona_ultimo, turnos.descripcion descripcion,turnos.activarretriccionacredit activarretriccionacredit, turnos.letradoasistencias letradoasistencias,turnos.letradoactuaciones letradoactuaciones,turnos.codigoext codigoext, turnos.fechasolicitud_ultimo fechasolicitud_ultimo,turnos.visiblemovil visiblemovil,area.nombre area, materi.nombre materia, zona.nombre zona, subzon.nombre subzona,grupof.idgrupofacturacion idgrupofacturacion, cat.descripcion as grupofacturacion,(SELECT COUNT(*)from scs_inscripcionturno ins where ins.idinstitucion = turnos.idinstitucion and ins.idturno = turnos.idturno and (ins.fechabaja is null or trunc(ins.fechabaja) > trunc(sysdate) ) and (ins.fechavalidacion is not null and trunc(ins.fechavalidacion) <= trunc(sysdate) ) ) as nletrados, turnos.fechabaja as fechabaja from scs_turno turnos ");
		sql.INNER_JOIN("scs_ordenacioncolas on scs_ordenacioncolas.idordenacioncolas = turnos.idordenacioncolas");
		sql.INNER_JOIN("scs_materia materi ON materi.idinstitucion = turnos.idinstitucion and materi.idmateria = turnos.idmateria and materi.idarea = turnos.idarea");
		sql.INNER_JOIN("scs_area area ON area.idinstitucion = materi.idinstitucion and area.idarea = materi.idarea");
		sql.LEFT_OUTER_JOIN("scs_subzona subzon ON subzon.idinstitucion = turnos.idinstitucion and subzon.idzona = turnos.idzona and subzon.idsubzona = turnos.idsubzona");
		sql.INNER_JOIN("scs_zona zona ON  zona.idinstitucion = turnos.idinstitucion and  zona.idzona = turnos.idzona");
		sql.INNER_JOIN("scs_grupofacturacion grupof ON grupof.idinstitucion = turnos.idinstitucion and grupof.idgrupofacturacion = turnos.idgrupofacturacion");
		sql.LEFT_OUTER_JOIN("cen_partidojudicial parjud ON  parjud.idpartido = subzon.idpartido");
		sql.LEFT_OUTER_JOIN("scs_partidapresupuestaria partid ON partid.IDPARTIDAPRESUPUESTARIA = turnos.IDPARTIDAPRESUPUESTARIA and partid.idinstitucion = turnos.idinstitucion");
		sql.INNER_JOIN("gen_recursos_catalogos cat ON  cat.IDRECURSO = grupof.nombre and cat.IDLENGUAJE= 1");
		sql.WHERE("turnos.idinstitucion = '"+idInstitucion+"'");
		sql.WHERE("turnos.idturno ='"+turnosItem.getIdturno()+"'");
		sql.ORDER_BY("nombre");
			
		return sql.toString();
		
	}

	public String getIdTurno(Short idInstitucion) {
	SQL sql = new SQL();

	sql.SELECT("MAX(IDTURNO) AS IDTURNO");
	sql.FROM("SCS_TURNO");
	sql.WHERE("IDINSTITUCION = '"+ idInstitucion +"'");

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
		sql.SELECT("ROW_NUMBER()OVER(ORDER BY '"+busquedaOrden+"')as orden,consulta.*from(SELECT (CASE WHEN ins.fechavalidacion IS NOT NULL AND TRUNC(ins.fechavalidacion)<=nvl('"+strDate+"',ins.fechavalidacion)AND(ins.fechabaja is null or trunc(ins.fechabaja) > nvl('"+strDate+"','01/01/1900')) then '1' else '0' end)activo, ins.idinstitucion,ins.idturno, TRUNC(ins.fechavalidacion) as fechavalidacion, TO_CHAR(TRUNC(ins.fechabaja),'DD/MM/YYYY') as fechabajapersona,"
				+ "ins.fechasolicitud as fechasolicitud, per.nifcif,per.idpersona,per.nombre as nombrepersona,per.apellidos1, DECODE(per.apellidos2, NULL,'',' ' || per.apellidos2) apellidos2,\r\n" + 
				"    per.apellidos1 \r\n" + 
				"    || DECODE(per.apellidos2,NULL,'',' '\r\n" + 
				"    || per.apellidos2) alfabeticoapellidos,\r\n" + 
				"    DECODE(col.comunitario,'1',col.ncomunitario,col.ncolegiado) numerocolegiado,\r\n" + 
				"    per.fechanacimiento fechanacimiento,\r\n" + 
				"    ins.fechavalidacion antiguedadcola,\r\n" + 
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
				"    )  as compensaciones\r\n" + 
				sql.FROM("scs_inscripcionturno ins" + 
						" INNER JOIN cen_persona per ON per.IDPERSONA = ins.IDPERSONA" + 
						" INNER JOIN cen_colegiado col ON col.idpersona = per.IDPERSONA and col.IDINSTITUCION = ins.IDINSTITUCION and col.IDPERSONA = ins.IDPERSONA" + 
						" INNER JOIN scs_turno tur ON tur.IDTURNO = ins.IDTURNO and tur.IDINSTITUCION = ins.IDINSTITUCION"));
		sql.WHERE("ins.fechavalidacion is not null and tur.idinstitucion= '"+idInstitucion+"'"+"and tur.idturno ='"+turnosItem.getIdturno()+"'");
		if(!turnosItem.isHistorico()) {
			sql.WHERE("ins.fechabaja is null");
		}
		return sql.toString()+")"+"consulta";
		
	}

public String busquedaColaGuardia(TurnosItem turnosItem,String strDate,String busquedaOrden, Short idInstitucion) {
	
	SQL sql = new SQL();
	sql.SELECT("ROW_NUMBER() OVER(ORDER BY '"+busquedaOrden+"') as orden, consulta.* from (\r\n" + 
			"\r\n" + 
			"SELECT\r\n" + 
			"    \r\n" + 
			"    (\r\n" + 
			"        CASE\r\n" + 
			"            WHEN ins.fechavalidacion IS NOT NULL\r\n" + 
			"                 AND trunc(ins.fechavalidacion) <= nvl('"+strDate+"',ins.fechavalidacion)\r\n" + 
			"                 AND (\r\n" + 
			"                ins.fechabaja IS NULL\r\n" + 
			"                OR trunc(ins.fechabaja) > nvl('"+strDate+"','01/01/1900')\r\n" + 
			"            ) THEN '1'\r\n" + 
			"            ELSE '0'\r\n" + 
			"        END\r\n" + 
			"    ) activo,\r\n" + 
			"    ins.idinstitucion,\r\n" + 
			"    ins.idturno,\r\n" + 
			"    trunc(ins.fechavalidacion) AS fechavalidacion,\r\n" + 
			"    TO_CHAR(trunc(ins.fechabaja),'DD/MM/YYYY') AS fechabajaguardia,\r\n" + 
			"    ins.FECHASUSCRIPCION AS fechasolicitud,\r\n" + 
			"    per.nifcif,\r\n" + 
			"    per.idpersona,\r\n" + 
			"    per.nombre as nombreguardia," + 
			"    per.apellidos1,\r\n" + 
			"    DECODE(per.apellidos2,NULL,'',' '\r\n" + 
			"    || per.apellidos2) apellidos2,\r\n" + 
			"    per.apellidos1 \r\n" + 
			"    || DECODE(per.apellidos2,NULL,'',' '\r\n" + 
			"    || per.apellidos2) alfabeticoapellidos,\r\n" + 
			"    DECODE(col.comunitario,'1',col.ncomunitario,col.ncolegiado) numerocolegiado,\r\n" + 
			"    per.fechanacimiento fechanacimiento,\r\n" + 
			"    ins.fechavalidacion antiguedadcola,\r\n" + 
			"     (\r\n" + 
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
			"    )  as compensaciones\r\n" + 
			"FROM\r\n" + 
			"    scs_inscripcionguardia ins\r\n" + 
			"    INNER JOIN cen_persona per ON per.IDPERSONA = ins.IDPERSONA\r\n" + 
			"    INNER JOIN scs_guardiasturno gua ON gua.idturno = ins.idturno and gua.idguardia = ins.idguardia and gua.IDINSTITUCION = ins.IDINSTITUCION\r\n" + 
			"    LEFT JOIN  scs_grupoguardiacolegiado gru ON gru.IDINSTITUCION = ins.IDINSTITUCION and gru.IDTURNO = ins.IDTURNO and gru.IDGUARDIA = ins.IDGUARDIA and gru.IDPERSONA = per.IDPERSONA and gru.FECHASUSCRIPCION = ins.FECHASUSCRIPCION\r\n" + 
			"    LEFT JOIN scs_grupoguardia grg ON grg.IDGRUPOGUARDIA = gru.IDGRUPOGUARDIA\r\n" + 
			"    INNER JOIN cen_colegiado col ON col.idpersona = per.IDPERSONA and col.IDINSTITUCION = ins.IDINSTITUCION and col.IDPERSONA = ins.IDPERSONA \r\n");
	sql.WHERE("ins.fechavalidacion is not null and gua.idinstitucion= '"+idInstitucion+"'"+"and gua.idturno ='"+turnosItem.getIdturno()+"'and gua.idguardia ='"+turnosItem.getIdcomboguardias()+"'");
	if(!turnosItem.isHistorico()) {
		sql.WHERE("ins.fechabaja is null");
	}
	return sql.toString()+")"+"consulta";
	
}
}
