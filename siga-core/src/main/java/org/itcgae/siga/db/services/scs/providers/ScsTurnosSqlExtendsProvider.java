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
		return sql.toString();
	}

	public String busquedaTurnos(TurnosItem turnosItem, Short idInstitucion) {
		
		SQL sql = new SQL();
		sql.SELECT("turnos.idturno idturno, turnos.nombre nombre, turnos.abreviatura abreviatura, area.nombre area, materi.nombre materia, zona.nombre zona, subzon.nombre subzona, cat.descripcion as grupofacturacion,(SELECT COUNT(*)from scs_inscripcionturno ins where ins.idinstitucion = turnos.idinstitucion and ins.idturno = turnos.idturno and (ins.fechabaja is null or trunc(ins.fechabaja) > trunc(sysdate) ) and (ins.fechavalidacion is not null and trunc(ins.fechavalidacion) <= trunc(sysdate) ) ) as nletrados, turnos.fechabaja as fechabaja from scs_turno turnos ");
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
		sql.ORDER_BY("nombre");
			
		return sql.toString();
		
	}

}
