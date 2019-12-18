package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.InscripcionesItem;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.db.mappers.ScsInscripcionturnoSqlProvider;
import org.itcgae.siga.db.mappers.ScsTipoactuacionSqlProvider;
import org.itcgae.siga.db.mappers.ScsTurnoSqlProvider;

public class ScsInscripcionesTurnoSqlExtendsProvider extends ScsInscripcionturnoSqlProvider {

	public String comboTurnos(Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("consultaActiva.* FROM\r\n" + 
				"(\r\n" + 
				"SELECT\r\n" + 
				"    idturno,\r\n" + 
				"    abreviatura as nom\r\n" + 
				"FROM\r\n" + 
				"    scs_turno\r\n" + 
				"WHERE\r\n" + 
				"    idinstitucion = '"+idInstitucion+"'" + 
				"    AND   fechabaja IS NULL\r\n" + 
				"    ORDER BY nom\r\n" + 
				") consultaActiva     \r\n" + 
				"UNION all\r\n" + 
				"select consultaBaja.* from (\r\n" + 
				"SELECT\r\n" + 
				"    idturno,\r\n" + 
				"    DECODE(fechabaja,NULL,NULL, '(Baja) ' || abreviatura || ' ') nom");
		sql.FROM("SCS_TURNO");
		sql.WHERE("idinstitucion = '"+idInstitucion+"'" + 
				"    AND   fechabaja IS NOT NULL\r\n)" + 
				"ORDER BY nom");
		
		return sql.toString() +"consultaBaja";
	}
	
	public String busquedaInscripciones(InscripcionesItem inscripcionesItem, Short idInstitucion,String fechadesde,String fechahasta, String afechade) {

		SQL sql = new SQL();
		sql.SELECT(  
				"       ( CASE\r\n" + 
				"            WHEN ins.fechadenegacion IS NOT NULL\r\n" + 
				"                 AND ins.fechabaja IS NOT NULL\r\n" + 
				"                 AND ins.fechasolicitudbaja IS NOT NULL\r\n" + 
				"                 AND ins.fechavalidacion IS NOT NULL THEN '4' /*Denegada*/\r\n" + 
				"            WHEN ins.fechadenegacion IS NULL\r\n" + 
				"                 AND ins.fechabaja IS NOT NULL\r\n" + 
				"                 AND ins.fechasolicitudbaja IS NOT NULL\r\n" + 
				"                 AND ins.fechavalidacion IS NOT NULL THEN '3' /*Baja*/\r\n" + 
				"            WHEN ins.fechadenegacion IS NULL\r\n" + 
				"                 AND ins.fechabaja IS NULL\r\n" + 
				"                 AND ins.fechasolicitudbaja IS NOT NULL\r\n" + 
				"                 AND ins.fechavalidacion IS NOT NULL THEN '2' /*Pendiente de Baja*/\r\n" + 
				"            WHEN ins.fechadenegacion IS NULL\r\n" + 
				"                 AND ins.fechabaja IS NULL\r\n" + 
				"                 AND ins.fechasolicitudbaja IS NULL\r\n" + 
				"                 AND ins.fechavalidacion IS NOT NULL THEN '1' /*Alta*/\r\n" + 
				"            ELSE '0' /*Pendiente de Alta*/\r\n" + 
				"        END\r\n" + 
				"    ) estado,\r\n" + 
				"    tur.nombre nombreturno,\r\n" + 
				"    tur.abreviatura abreviatura,\r\n" + 
				"    tur.validarinscripciones,\r\n" + 
				"    tur.guardias,\r\n" + 
				"    per.apellidos1\r\n" + 
				"    || DECODE(per.apellidos2,NULL,'',' '\r\n" + 
				"    || per.apellidos2)\r\n" + 
				"    || ', '\r\n" + 
				"    || per.nombre apellidosnombre,\r\n" + 
				"    DECODE(col.comunitario,'1',col.ncomunitario,col.ncolegiado) ncolegiado,\r\n" + 
				"    per.nombre,\r\n" + 
				"    per.apellidos1,\r\n" + 
				"    per.apellidos2,\r\n" + 
				"    ins.idinstitucion,\r\n" + 
				"    ins.idpersona,\r\n" + 
				"    ins.idturno,\r\n" + 
				"    ins.fechasolicitud," + 
				"    ins.observacionessolicitud,\r\n" + 
				"    ins.fechavalidacion,\r\n" + 
				"    ins.observacionesvalidacion,\r\n" + 
				"    ins.fechasolicitudbaja,\r\n" + 
				"    ins.observacionesbaja,\r\n" + 
				"    ins.fechabaja,\r\n" + 
				"    ins.observacionesvalbaja,\r\n" + 
				"    ins.fechadenegacion,\r\n" + 
				"    ins.observacionesdenegacion,\r\n" + 
				"    DECODE(col.comunitario,'1',col.ncomunitario,col.ncolegiado) ncolegiado,\r\n" + 
				"    TO_CHAR(nvl(ins.fechadenegacion,ins.fechavalidacion),'dd/mm/yyyy') fechavalidacion,\r\n" + 
				"    TO_CHAR(nvl(ins.fechadenegacion,ins.fechabaja),'dd/mm/yyyy') fechabaja\r\n" + 
				"FROM\r\n" + 
				"    scs_inscripcionturno ins\r\n" + 
				"    INNER JOIN cen_colegiado col ON col.idpersona = ins.idpersona\r\n" + 
				"                                    AND col.idinstitucion = ins.idinstitucion\r\n" + 
				"    INNER JOIN cen_persona per ON per.idpersona = col.idpersona\r\n" + 
				"    INNER JOIN scs_turno tur ON tur.idturno = ins.idturno\r\n" + 
				"                                AND tur.idinstitucion = ins.idinstitucion");
		sql.WHERE("ins.idinstitucion ='"+idInstitucion+"'");
		if(inscripcionesItem.getIdturno() != null) {
			sql.WHERE("ins.idturno ='"+inscripcionesItem.getIdturno()+"'");
		}
		if(inscripcionesItem.getIdpersona() != null) {
			sql.WHERE("ins.idpersona ='"+inscripcionesItem.getIdpersona()+"'");
		}
		
		sql.WHERE("ins.fechasolicitud = (\r\n" + 
				"        SELECT\r\n" + 
				"            MAX(it2.fechasolicitud)\r\n" + 
				"        FROM\r\n" + 
				"            scs_inscripcionturno it2\r\n" + 
				"        WHERE\r\n" + 
				"            it2.idinstitucion = ins.idinstitucion\r\n" + 
				"            AND   it2.idturno = ins.idturno\r\n" + 
				"            AND   it2.idpersona = ins.idpersona\r\n" + 
				"    )");
		if(inscripcionesItem.getEstado() != null) {
			if(inscripcionesItem.getEstado().equals("0")) {
				sql.WHERE("ins.fechavalidacion is null and ins.fechasolicitudbaja is null and ins.fechabaja "
						+ "is null and ins.fechadenegacion is null OR ins.fechavalidacion is not null and ins.fechasolicitudbaja is not null and ins.fechabaja"
						+ " is null and ins.fechadenegacion is null") ;
			}
			
			if(inscripcionesItem.getEstado().equals("1")) {
				sql.WHERE("ins.fechavalidacion is not null and ins.fechasolicitudbaja is null and ins.fechabaja "
						+ "is null and ins.fechadenegacion is null OR ins.fechavalidacion is not null and ins.fechasolicitudbaja is null and ins.fechabaja"
						+ " is not null and ins.fechadenegacion is null") ;
			}
			
			if(inscripcionesItem.getEstado().equals("2")) {
				sql.WHERE("ins.fechavalidacion is not null and ins.fechasolicitudbaja is not null and ins.fechabaja "
						+ "is not null and ins.fechadenegacion is not null") ;
			}
		}
		if(inscripcionesItem.getAfechade() != null) {
			sql.WHERE("((ins.fechavalidacion is not null and ins.fechavalidacion <= '"+afechade+"') and ins.fechasolicitudbaja is null and ins.fechabaja " + 
					"is null and ins.fechadenegacion is null) OR (ins.fechavalidacion is not null and (ins.fechasolicitudbaja is not null and ins.fechavalidacion <= '"+afechade+"')and ins.fechabaja"
					+ " is null and ins.fechadenegacion is null)") ;
		}
		if(inscripcionesItem.getFechadesde() != null) {
			sql.WHERE("ins.fechasolicitud >= '"+fechadesde+"'");
			if(inscripcionesItem.getFechahasta() != null)
			sql.WHERE("ins.fechasolicitud <= '"+fechahasta+"'");
		}
		sql.ORDER_BY("nombreturno");
			
		return sql.toString();
	}

}
