package org.itcgae.siga.db.services.scs.providers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.InscripcionesItem;
import org.itcgae.siga.db.mappers.ScsInscripcionturnoSqlProvider;

public class ScsInscripcionesTurnoSqlExtendsProvider extends ScsInscripcionturnoSqlProvider {

	public String comboTurnos(Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("consultaActiva.* FROM\r\n" + 
				"(\r\n" + 
				"SELECT\r\n" + 
				"    idturno,\r\n" + 
				"    nombre as nom\r\n" + 
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
				"    DECODE(fechabaja,NULL,NULL, '(Baja) ' || nombre || ' ') nom");
		sql.FROM("SCS_TURNO");
		sql.WHERE("idinstitucion = '"+idInstitucion+"'" + 
				"    AND   fechabaja IS NOT NULL\r\n)" + 
				"ORDER BY nom");
		
		return sql.toString() +"consultaBaja";
	}
	
	public String busquedaTrabajosGuardias(InscripcionesItem inscripcionesItem,Short idInstitucion,String fechaActual) {

		SQL sql = new SQL();
		
		sql.SELECT("TO_CHAR(gc.fechainicio,'dd/mm/yyyy')\r\n" + 
				"    || ' - '\r\n" + 
				"    || TO_CHAR(gc.fecha_fin,'dd/mm/yyyy') fecha,\r\n" + 
				"    gt.nombre incidencia,\r\n" + 
				"    'Guardia' descripcion");
		sql.FROM("scs_cabeceraguardias gc,\r\n" + 
				"    scs_guardiasturno gt");
		sql.WHERE("gc.idinstitucion = gt.idinstitucion\r\n" + 
				"    AND   gc.idturno = gt.idturno\r\n" + 
				"    AND   gc.idguardia = gt.idguardia\r\n" + 
				"    AND   gc.idinstitucion = '"+idInstitucion+"'" + 
				"    AND   gc.idpersona = '"+inscripcionesItem.getIdpersona()+"'"+ 
				"    AND   trunc(gc.fecha_fin) > '"+fechaActual+"'" + 
				"    AND   gc.idturno = '"+inscripcionesItem.getIdturno()+"'");
		sql.ORDER_BY("gc.fechainicio DESC");
		
		
		return sql.toString();
	}
	
	public String busquedaTrabajosPendientes(InscripcionesItem inscripcionesItem,Short idInstitucion,String fechaActual) {

		SQL sql = new SQL();
		
		sql.SELECT("des.anio\r\n" + 
				"    || '/'\r\n" + 
				"    || des.codigo incidencia,\r\n" + 
				"    TO_CHAR(des.fechaentrada,'dd/mm/yyyy') fecha,\r\n" + 
				"    'Designación' descripcion");
		sql.FROM("scs_designa des,\r\n" + 
				"    scs_designasletrado deslet");
		sql.WHERE("des.idinstitucion = '"+idInstitucion+"'" + 
				"    AND   des.estado <> ' f '    AND   deslet.idpersona = '"+inscripcionesItem.getIdpersona() +"'" + 
				"    AND   deslet.fecharenuncia IS NULL\r\n" + 
				"    AND   deslet.idturno = '"+inscripcionesItem.getIdturno() +"'" + 
				"    AND   des.idinstitucion = deslet.idinstitucion" + 
				"    AND   des.idturno = deslet.idturno" + 
				"    AND   des.anio = deslet.anio" + 
				"    AND   des.numero = deslet.numero" + 
				"    AND   trunc(des.fechaentrada) > '"+fechaActual+"'");		
		
		return sql.toString();
	}
	
	public String busquedaInscripciones(InscripcionesItem inscripcionesItem, Short idInstitucion,String fechadesde,String fechahasta, String afechade,Integer tamMax) {

		SQL sql = new SQL();
		
		sql.SELECT(  
				"       ( CASE\r\n" + 
				"            WHEN ins.fechadenegacion IS NOT NULL THEN '4'\r\n" + 
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
				"    tur.guardias,"
				+ 	"tur.idzona,\r\n" + 
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
				"    DECODE(col.comunitario,'1',col.ncomunitario,col.ncolegiado) ncolegiado," +
				"    DECODE(tur.GUARDIAS, 0, 'Obligatorias', DECODE(tur.GUARDIAS, 2, 'A elegir', 'Todas o ninguna'))as tipoguardias\r\n" +
				"FROM\r\n" + 
				"    scs_inscripcionturno ins\r\n" + 
				"    JOIN cen_colegiado col ON col.idpersona = ins.idpersona\r\n" + 
				"                                    AND col.idinstitucion = ins.idinstitucion\r\n" + 
				"     JOIN cen_persona per ON per.idpersona = col.idpersona\r\n" + 
				"    LEFT JOIN scs_turno tur ON tur.idturno = ins.idturno\r\n" + 
				"                                AND tur.idinstitucion = ins.idinstitucion");
		sql.WHERE("ins.idinstitucion ='"+idInstitucion+"'");
		if(inscripcionesItem.getIdturno() != null) {
			String condturnos ="(";
			for(int i = 0; i< inscripcionesItem.getIdturno().split(",").length; i++) {
				if(i>0) condturnos+=" or ";
				condturnos+="ins.idturno ='"+inscripcionesItem.getIdturno().split(",")[i]+"'";
			}
			condturnos+=")";
			sql.WHERE(condturnos);
			
		}
		if(inscripcionesItem.getNcolegiado() != null) {
			sql.WHERE("(col.ncolegiado ='"+inscripcionesItem.getNcolegiado()+
					"' OR col.ncomunitario = '" + inscripcionesItem.getNcolegiado()+ "')");
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
			String condestados = "(";
			String[] estados = inscripcionesItem.getEstado().split(",");
			for(int i = 0; i< estados.length; i++) {
				if(i>0) condestados+=" or ";
				// Pendiente de alta
				if(estados[i].equals("0")) {
					condestados+="(ins.fechavalidacion is null and ins.fechadenegacion is null)" ;
				}
				// Alta
				else if(estados[i].equals("1")) {
					condestados+="(ins.fechadenegacion IS NULL AND ins.fechabaja IS NULL" + 
							" AND ins.fechasolicitudbaja IS NULL AND ins.fechavalidacion IS NOT NULL)" ;
				}
				// Pendiente de Baja
				else if(estados[i].equals("2")) {
					condestados+="(ins.fechadenegacion IS NULL AND ins.fechabaja IS NULL" + 
							" AND ins.fechasolicitudbaja IS NOT NULL AND ins.fechavalidacion IS NOT NULL)" ;
				}
				// Baja
				else if(estados[i].equals("3")) {
					condestados+="(ins.fechadenegacion IS NULL AND ins.fechabaja IS NOT NULL"
							+ " AND ins.fechasolicitudbaja IS NOT NULL AND ins.fechavalidacion IS NOT NULL )" ;
				}
				// Denegada
				else if(estados[i].equals("4")) {
					condestados+="(ins.fechadenegacion is not null)" ;
				}
			}
			condestados+=")";
			sql.WHERE(condestados);
		}
		if(inscripcionesItem.getAfechade() != null) {
			sql.WHERE("ins.fechadenegacion is null AND ins.fechavalidacion is not null AND (ins.fechavalidacion <= TO_DATE('"+afechade+"','DD/MM/RRRR') OR (" + 
					" ins.fechasolicitudbaja is not null and ins.fechasolicitudbaja <= TO_DATE('"+afechade+" 00:00:00','DD/MM/YYYY HH24:MI:SS')))"
							+ "AND ( ins.fechabaja is null or ins.fechabaja >= TO_DATE('"+afechade+" 23:59:59','DD/MM/YYYY HH24:MI:SS'))") ;
		}
		if(inscripcionesItem.getFechadesde() != null) {
			sql.WHERE("ins.fechasolicitud >= TO_DATE('"+fechadesde+" 00:00:00','DD/MM/YYYY HH24:MI:SS')");
			if(inscripcionesItem.getFechahasta() != null)
			sql.WHERE("ins.fechasolicitud <= TO_DATE('"+fechahasta+" 23:59:59','DD/MM/YYYY HH24:MI:SS')");
		}
		sql.ORDER_BY("fechasolicitud DESC");
		if (tamMax != null) {
			Integer tamMaxNumber = tamMax + 1;
			sql.WHERE("rownum <= " + tamMaxNumber);
		}
			
		return sql.toString();
	}
	
	public String busquedaInscripcionesTurno(InscripcionesItem inscripcionesItem, Short idInstitucion,String fechadesde,String fechahasta, String afechade,Integer tamMax) {

		SQL sql = new SQL();
		
		sql.SELECT(  
				"       ( CASE\r\n" + 
				"            WHEN ins.fechadenegacion IS NOT NULL THEN '4'\r\n" + 
				"            WHEN ins.fechabaja IS NOT NULL\r\n" + 
				"                 AND ins.fechasolicitudbaja IS NOT NULL\r\n" + 
				"                 AND ins.fechavalidacion IS NULL THEN '4' /*Denegacion*/\r\n" + 
				"            WHEN ins.fechadenegacion IS NULL\r\n" + 
				"                 AND ins.fechabaja IS NOT NULL\r\n" + 
				"                 AND ins.fechavalidacion IS NOT NULL THEN '3' /*Baja*/\r\n" + 
				"            WHEN ins.fechadenegacion IS NULL\r\n" + 
				"                 AND ins.fechabaja IS NULL\r\n" + 
				"                 AND ins.fechasolicitudbaja IS NOT NULL\r\n" + 
				"                 AND ins.fechavalidacion IS NOT NULL THEN '2' /*Pendiente de Baja*/\r\n" + 
				"            WHEN ins.fechadenegacion IS NULL\r\n" + 
				"                 AND ins.fechabaja IS NULL\r\n" + 
				"                 AND ins.fechasolicitudbaja IS NULL\r\n" + 
				"                 AND ins.fechavalidacion IS NOT NULL THEN '1' /*Alta*/\r\n" + 
				"            WHEN ins.fechadenegacion IS NULL\r\n" + 
				"                 AND ins.fechabaja IS NULL\r\n" + 
				"                 AND ins.fechasolicitudbaja IS NULL\r\n" + 
				"                 AND ins.fechavalidacion IS NULL THEN '0' /*Pendiente de Alta*/\r\n" + 
				"            ELSE ''\r\n" + 
				"        END\r\n" + 
				"    ) estado,\r\n" + 
				"    tur.nombre nombreturno,\r\n" + 
				"    tur.abreviatura abreviatura,\r\n" + 
				"    tur.validarinscripciones,\r\n" + 
				"    tur.guardias,"
				+ 	"tur.idzona,\r\n" + 
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
				"    DECODE(col.comunitario,'1',col.ncomunitario,col.ncolegiado) ncolegiado," +
				"    DECODE(tur.GUARDIAS, 0, 'Obligatorias', DECODE(tur.GUARDIAS, 2, 'A elegir', 'Todas o ninguna'))as tipoguardias\r\n" +
				"FROM\r\n" + 
				"    scs_inscripcionturno ins\r\n" + 
				"    JOIN cen_colegiado col ON col.idpersona = ins.idpersona\r\n" + 
				"                                    AND col.idinstitucion = ins.idinstitucion\r\n" + 
				"     JOIN cen_persona per ON per.idpersona = col.idpersona\r\n" + 
				"    LEFT JOIN scs_turno tur ON tur.idturno = ins.idturno\r\n" + 
				"                                AND tur.idinstitucion = ins.idinstitucion");
		sql.WHERE("ins.idinstitucion ='"+idInstitucion+"'");
		if(inscripcionesItem.getIdturno() != null) {
			String condturnos ="(";
			for(int i = 0; i< inscripcionesItem.getIdturno().split(",").length; i++) {
				if(i>0) condturnos+=" or ";
				condturnos+="ins.idturno ='"+inscripcionesItem.getIdturno().split(",")[i]+"'";
			}
			condturnos+=")";
			sql.WHERE(condturnos);
			
		}
		if(inscripcionesItem.getNcolegiado() != null) {
			sql.WHERE("(col.ncolegiado ='"+inscripcionesItem.getNcolegiado()+
					"' OR col.ncomunitario = '" + inscripcionesItem.getNcolegiado()+ "')");
		}
		if(inscripcionesItem.getEstado() != null) {
			String condestados = "(";
			String[] estados = inscripcionesItem.getEstado().split(",");
			for(int i = 0; i< estados.length; i++) {
				if(i>0) condestados+=" or ";
				// Pendiente de alta
				if(estados[i].equals("0")) {
					condestados+="(ins.fechadenegacion IS NULL AND ins.fechabaja IS NULL" + 
							" AND ins.fechasolicitudbaja IS NULL AND ins.fechavalidacion IS NULL)" ;
				}
				// Alta
				else if(estados[i].equals("1")) {
					condestados+="(ins.fechadenegacion IS NULL AND ins.fechabaja IS NULL" + 
							" AND ins.fechasolicitudbaja IS NULL AND ins.fechavalidacion IS NOT NULL)" ;
				}
				// Pendiente de Baja
				else if(estados[i].equals("2")) {
					condestados+="(ins.fechadenegacion IS NULL AND ins.fechabaja IS NULL" + 
							" AND ins.fechasolicitudbaja IS NOT NULL AND ins.fechavalidacion IS NOT NULL)" ;
				}
				// Baja
				else if(estados[i].equals("3")) {
					condestados+="(ins.fechadenegacion IS NULL AND ins.fechabaja IS NOT NULL"
							+ " AND ins.fechavalidacion IS NOT NULL )" ;
				}
				// Denegada
				else if(estados[i].equals("4")) {
					condestados+="((ins.fechadenegacion is not null) or"
							+ "(ins.fechabaja IS NOT NULL"
							+ " AND ins.fechasolicitudbaja IS NOT NULL AND ins.fechavalidacion IS NULL )"
							+" )" ;
				}
			}
			condestados+=")";
			sql.WHERE(condestados);
		}
		if(inscripcionesItem.getAfechade() != null) {
			sql.WHERE("ins.fechadenegacion is null AND ins.fechavalidacion is not null AND (ins.fechavalidacion <= TO_DATE('"+afechade+"','DD/MM/RRRR') OR (" + 
					" ins.fechasolicitudbaja is not null and ins.fechasolicitudbaja <= TO_DATE('"+afechade+" 00:00:00','DD/MM/YYYY HH24:MI:SS')))"
							+ "AND ( ins.fechabaja is null or ins.fechabaja >= TO_DATE('"+afechade+" 23:59:59','DD/MM/YYYY HH24:MI:SS'))") ;
		}
		if(inscripcionesItem.getFechadesde() != null) {
			sql.WHERE("ins.fechasolicitud >= TO_DATE('"+fechadesde+" 00:00:00','DD/MM/YYYY HH24:MI:SS')");
			if(inscripcionesItem.getFechahasta() != null)
			sql.WHERE("ins.fechasolicitud <= TO_DATE('"+fechahasta+" 23:59:59','DD/MM/YYYY HH24:MI:SS')");
		}
		sql.ORDER_BY("fechasolicitud DESC");
		if (tamMax != null) {
			Integer tamMaxNumber = tamMax + 1;
			sql.WHERE("rownum <= " + tamMaxNumber);
		}
			
		return sql.toString();
	}
	
	public String busquedaTarjetaInscripciones(InscripcionesItem inscripcionesItem, Short idInstitucion, String idLenguaje) {

		SQL sql = new SQL();
		String where ="";
		String select="";
		//Inscripciones de turnos a secas, sin asignacion a ninguna guardia				
		if(inscripcionesItem.getIdguardia() == null && inscripcionesItem.getIdturno() != null) {
			select+=
					"          SCS_TURNO.NOMBRE AS NOMBRE_TURNO,\r\n" + 
					"          SCS_TURNO.IDZONA,\r\n" + 
					"          SCS_ZONA.NOMBRE AS NOMBRE_ZONA,\r\n" + 
					"          SCS_TURNO.IDSUBZONA,\r\n" + 
					"          SCS_SUBZONA.NOMBRE AS NOMBRE_SUBZONA,\r\n" + 
					"          SCS_TURNO.IDAREA,\r\n" + 
					"          SCS_AREA.NOMBRE AS NOMBRE_AREA,\r\n" + 
					"          SCS_TURNO.IDMATERIA,\r\n" + 
					"          SCS_MATERIA.NOMBRE AS NOMBRE_MATERIA,\r\n" + 
                    "          SCS_TURNO.IDTURNO,\r\n" + 
                    "          SCS_TURNO.GUARDIAS AS OBLIGATORIEDAD_INSCRIPCION, --La inscripcion en el turno obliga a inscribirse en guardias: 2- A elegir; 1-Todas o ninguna; 0-Obligatorias\r\n" + 
                    "          DECODE(SCS_TURNO.GUARDIAS, 0, 'Obligatorias', DECODE(SCS_TURNO.GUARDIAS, 2, 'A elegir', 'Todas o ninguna'))as tipoguardias\r\n" +
					"FROM\r\n" + 
					"          SCS_INSCRIPCIONTURNO\r\n" + 
					"          JOIN SCS_TURNO ON SCS_TURNO.IDINSTITUCION = SCS_INSCRIPCIONTURNO.IDINSTITUCION AND SCS_TURNO.IDTURNO = SCS_INSCRIPCIONTURNO.IDTURNO\r\n" + 
					"          JOIN SCS_ZONA ON SCS_ZONA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_ZONA.IDZONA = SCS_TURNO.IDZONA\r\n" + 
					"          JOIN SCS_SUBZONA ON SCS_SUBZONA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_SUBZONA.IDZONA = SCS_TURNO.IDZONA AND SCS_SUBZONA.IDSUBZONA = SCS_TURNO.IDSUBZONA\r\n" + 
					"          JOIN SCS_AREA ON SCS_AREA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_AREA.IDAREA = SCS_TURNO.IDAREA\r\n" + 
					"          JOIN SCS_MATERIA ON SCS_MATERIA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_MATERIA.IDMATERIA = SCS_TURNO.IDMATERIA AND SCS_MATERIA.IDAREA = SCS_TURNO.IDAREA \r\n";
			where +="          SCS_TURNO.IDINSTITUCION ='"+idInstitucion+"'\r\n" ;
		}
		else {
			select+=
					"*\r\n" + 
					"  FROM (SELECT          SCS_TURNO.NOMBRE AS NOMBRE_TURNO,\r\n" + 
					"          SCS_TURNO.IDZONA,\r\n" + 
					"          SCS_ZONA.NOMBRE AS NOMBRE_ZONA,\r\n" + 
					"          SCS_TURNO.IDSUBZONA,\r\n" + 
					"          SCS_SUBZONA.NOMBRE AS NOMBRE_SUBZONA,\r\n" + 
					"          SCS_TURNO.IDAREA,\r\n" + 
					"          SCS_AREA.NOMBRE AS NOMBRE_AREA,\r\n" + 
					"          SCS_TURNO.IDMATERIA,\r\n" + 
					"          SCS_MATERIA.NOMBRE AS NOMBRE_MATERIA,\r\n" + 
					"          SCS_TURNO.IDTURNO,\r\n"+
                    "          SCS_TURNO.GUARDIAS AS OBLIGATORIEDAD_INSCRIPCION, --La inscripcion en el turno obliga a inscribirse en guardias: 2- A elegir; 1-Todas o ninguna; 0-Obligatorias\r\n" + 
                    "          DECODE(SCS_TURNO.GUARDIAS, 0, 'Obligatorias', DECODE(SCS_TURNO.GUARDIAS, 2, 'A elegir', 'Todas o ninguna'))as tipoguardias\r\n" + 
					"FROM\r\n" + 
					"          SCS_INSCRIPCIONTURNO\r\n" + 
					"          JOIN SCS_GUARDIASTURNO ON SCS_INSCRIPCIONTURNO.IDINSTITUCION = SCS_GUARDIASTURNO.IDINSTITUCION AND SCS_INSCRIPCIONTURNO.IDTURNO = SCS_GUARDIASTURNO.IDTURNO\r\n" + 
					"          JOIN SCS_TURNO ON SCS_TURNO.IDINSTITUCION = SCS_INSCRIPCIONTURNO.IDINSTITUCION AND SCS_TURNO.IDTURNO = SCS_INSCRIPCIONTURNO.IDTURNO\r\n" + 
					"          JOIN SCS_ZONA ON SCS_ZONA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_ZONA.IDZONA = SCS_TURNO.IDZONA\r\n" + 
					"          JOIN SCS_SUBZONA ON SCS_SUBZONA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_SUBZONA.IDZONA = SCS_TURNO.IDZONA AND SCS_SUBZONA.IDSUBZONA = SCS_TURNO.IDSUBZONA\r\n" + 
					"          JOIN SCS_AREA ON SCS_AREA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_AREA.IDAREA = SCS_TURNO.IDAREA\r\n" + 
					"          JOIN SCS_MATERIA ON SCS_MATERIA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_MATERIA.IDMATERIA = SCS_TURNO.IDMATERIA AND SCS_MATERIA.IDAREA = SCS_TURNO.IDAREA\r\n" + 
					"          JOIN SCS_TIPOSGUARDIAS ON SCS_GUARDIASTURNO.IDTIPOGUARDIA = SCS_TIPOSGUARDIAS.IDTIPOGUARDIA\r\n" + 
					"          LEFT JOIN GEN_RECURSOS_CATALOGOS ON SCS_TIPOSGUARDIAS.DESCRIPCION = GEN_RECURSOS_CATALOGOS.IDRECURSO \r\n";
			where +="          SCS_TURNO.IDINSTITUCION ='"+idInstitucion+"'"+
					"          AND GEN_RECURSOS_CATALOGOS.IDLENGUAJE = '"+idLenguaje+"'\r\n" ;
		}
		if(inscripcionesItem.getIdturno() == null) {
				where +=
				"          AND ( \r\n" + 
				"          --Esté de baja\r\n" + 
				"          (SCS_INSCRIPCIONTURNO.IDPERSONA = '"+inscripcionesItem.getIdpersona()+"' AND SCS_INSCRIPCIONTURNO.FECHABAJA IS NOT NULL)\r\n"+ 
				"          --está inscrito pero que tengan alguna guardia a la que no estén inscritos\r\n" + 
				 ")"	;
		}
		else {
			where+=
			"          AND SCS_INSCRIPCIONTURNO.IDTURNO = '"+inscripcionesItem.getIdturno()+"'" + 
			"          AND SCS_INSCRIPCIONTURNO.IDPERSONA = '"+inscripcionesItem.getIdpersona()+"'";
		}
		
		sql.SELECT_DISTINCT(select);
		sql.WHERE(where);
		if(inscripcionesItem.getIdturno() == null) {
			sql.ORDER_BY("NOMBRE_TURNO)");
		} else {
			sql.ORDER_BY("NOMBRE_TURNO");
		}
		
		String result1=sql.toString();
		
		SQL sql2 = new SQL();
		String prevariables = "TURNOS_ASIGNADOS (IDTURNO, IDINSTITUCION) AS (SELECT DISTINCT SCS_INSCRIPCIONTURNO.IDTURNO, SCS_INSCRIPCIONTURNO.IDINSTITUCION\r\n"
				+ "	FROM  SCS_INSCRIPCIONTURNO\r\n"
				+ "    JOIN SCS_TURNO ON SCS_TURNO.IDINSTITUCION = SCS_INSCRIPCIONTURNO.IDINSTITUCION AND SCS_TURNO.IDTURNO=SCS_INSCRIPCIONTURNO.IDTURNO\r\n"
				+ "     WHERE SCS_TURNO.IDINSTITUCION = '"+idInstitucion+"' AND SCS_INSCRIPCIONTURNO.IDPERSONA='"+inscripcionesItem.getIdpersona()+"' ),\r\n"
				+ "    TURNOS_NO_ASIGNADOS (IDTURNO, IDINSTITUCION) AS(SELECT DISTINCT SCS_TURNO.IDTURNO, SCS_TURNO.IDINSTITUCION\r\n"
				+ "	FROM  SCS_TURNO\r\n"
				+ "    WHERE SCS_TURNO.IDINSTITUCION = '"+idInstitucion+"' AND IDTURNO NOT IN (\r\n"
				+ "    SELECT IDTURNO FROM TURNOS_ASIGNADOS))";
		
		sql2.SELECT("* \r\n" + 
				"FROM(\r\n" +
				"SELECT          SCS_TURNO.NOMBRE AS NOMBRE_TURNO,\r\n" + 
				"          SCS_TURNO.IDZONA,\r\n" + 
				"          SCS_ZONA.NOMBRE AS NOMBRE_ZONA,\r\n" + 
				"          SCS_TURNO.IDSUBZONA,\r\n" + 
				"          SCS_SUBZONA.NOMBRE AS NOMBRE_SUBZONA,\r\n" + 
				"          SCS_TURNO.IDAREA,\r\n" + 
				"          SCS_AREA.NOMBRE AS NOMBRE_AREA,\r\n" + 
				"          SCS_TURNO.IDMATERIA,\r\n" + 
				"          SCS_MATERIA.NOMBRE AS NOMBRE_MATERIA,\r\n" + 
				"          SCS_TURNO.IDTURNO,\r\n" +
		        "          SCS_TURNO.GUARDIAS AS OBLIGATORIEDAD_INSCRIPCION, --La inscripcion en el turno obliga a inscribirse en guardias: 2- A elegir; 1-Todas o ninguna; 0-Obligatorias\r\n" + 
		        "          DECODE(SCS_TURNO.GUARDIAS, 0, 'Obligatorias', DECODE(SCS_TURNO.GUARDIAS, 2, 'A elegir', 'Todas o ninguna'))as tipoguardias\r\n"); 

				sql2.FROM( 
				"SCS_TURNO\r\n"+
				"          JOIN SCS_ZONA ON SCS_ZONA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_ZONA.IDZONA = SCS_TURNO.IDZONA\r\n" + 
				"          JOIN SCS_SUBZONA ON SCS_SUBZONA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_SUBZONA.IDZONA = SCS_TURNO.IDZONA AND SCS_SUBZONA.IDSUBZONA = SCS_TURNO.IDSUBZONA\r\n" + 
				"          JOIN SCS_AREA ON SCS_AREA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_AREA.IDAREA = SCS_TURNO.IDAREA\r\n" + 
				"          JOIN SCS_MATERIA ON SCS_MATERIA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_MATERIA.IDMATERIA = SCS_TURNO.IDMATERIA AND SCS_MATERIA.IDAREA = SCS_TURNO.IDAREA\r\n");
		sql2.WHERE("          SCS_TURNO.IDINSTITUCION ='"+idInstitucion+"'"+
				"          AND SCS_TURNO.IDTURNO IN (\r\n"
						+ "    SELECT IDTURNO FROM TURNOS_NO_ASIGNADOS)\r\n");
		sql2.ORDER_BY("NOMBRE_TURNO)");
		
		String sql3 = "WITH " + prevariables+ "\r\n";
			
		//sql 1 Elementos de baja, sql2 turnos a los que no se ha solicitado y sql 3 turnos en los que se ha suscrito a parte de las guardias.
		String final_result;
		if(inscripcionesItem.getIdturno() == null) {
			final_result = sql3 + " \r\n " + sql2.toString() + " \r\n UNION \r\n " + result1;
		} else { 
			final_result=result1;
		}
		//SIGARNV-2009@DTT.JAMARTIN@05/08/2021@FIN	
		return final_result;
	}
	
	//SIGARNV-2009@DTT.JAMARTIN@06/08/2021@INICIO
	public String buscaTurnoInscrito(List<InscripcionesItem> inscripcionesItems, Short idInstitucion, String idPersona) {
		String listadoIdTurnos = "";
		for(int i=0; i<inscripcionesItems.size(); i++) {
			if(i==0) {
				listadoIdTurnos += inscripcionesItems.get(i).getIdturno();
			} else {
				listadoIdTurnos += ", "+inscripcionesItems.get(i).getIdturno();
			}
		}
		
		String sql = "SELECT * FROM SCS_INSCRIPCIONTURNO WHERE IDTURNO IN (" + listadoIdTurnos
				+ ") AND IDINSTITUCION = " + idInstitucion + " AND IDPERSONA = "
				+ idPersona;
		return sql;
	}
	
//	public String buscarGuardiasTurnosNoInscritos(List<InscripcionesItem> inscripcionesItems, Short idInstitucion, String idPersona) {
//		String listadoIdTurnos = "";
//		for(int i=0; i<inscripcionesItems.size(); i++) {
//			if(i==0) {
//				listadoIdTurnos += inscripcionesItems.get(i).getIdturno();
//			} else {
//				listadoIdTurnos += ", "+inscripcionesItems.get(i).getIdturno();
//			}
//		}
//		
//		String sql = "SELECT * FROM SCS_GUARDIASTURNO sg2 WHERE sg2.IDINSTITUCION = " + idInstitucion
//				+ " AND sg2.IDTURNO IN (" + listadoIdTurnos + ") AND sg2.IDGUARDIA NOT IN (SELECT DISTINCT sg.IDGUARDIA FROM " 
//				+ "SCS_INSCRIPCIONGUARDIA sg INNER JOIN SCS_TURNO st ON sg.IDINSTITUCION = st.IDINSTITUCION AND sg.IDTURNO = st.IDTURNO "
//				+ "INNER JOIN SCS_GUARDIASTURNO si ON sg.IDINSTITUCION = si.IDINSTITUCION AND sg.IDTURNO = si.IDTURNO AND sg.IDGUARDIA = si.IDGUARDIA WHERE sg.IDINSTITUCION = "
//				+ idInstitucion + " AND sg.IDTURNO IN (" + listadoIdTurnos + ") AND sg.IDPERSONA =  " + idPersona
//				+ ")";
//		
//		return sql;
//	}
	public String buscarGuardiasTurnosNoInscritos(List<InscripcionesItem> inscripcionesItems, Short idInstitucion, String idPersona) {
	    String listadoIdTurnos = "";
	    for(int i=0; i<inscripcionesItems.size(); i++) {
	        if(i==0) {
	            listadoIdTurnos += inscripcionesItems.get(i).getIdturno();
	        } else {
	            listadoIdTurnos += ", "+inscripcionesItems.get(i).getIdturno();
	        }
	    }

	    String sql = "SELECT sg2.IDGUARDIA, F_SIGA_GETRECURSO(stp.descripcion, 1) AS DESCRIPCION, sg2.IDINSTITUCION , sg2.IDTURNO, " +
	            "sg2.FECHAMODIFICACION,  sg2.USUMODIFICACION,  sg2.FECHABAJA,  sg2.NOMBRE " +
	            "FROM SCS_GUARDIASTURNO sg2 " +
	            "INNER JOIN SCS_TIPOSGUARDIAS stp ON stp.IDTIPOGUARDIA = sg2.IDTIPOGUARDIA "
	            + " WHERE sg2.IDINSTITUCION = " + idInstitucion
	            + " AND sg2.IDTURNO IN (" + listadoIdTurnos + ") AND sg2.IDGUARDIA NOT IN (SELECT DISTINCT sg.IDGUARDIA FROM "
	            + "SCS_INSCRIPCIONGUARDIA sg INNER JOIN SCS_TURNO st ON sg.IDINSTITUCION = st.IDINSTITUCION AND sg.IDTURNO = st.IDTURNO "
	            + "INNER JOIN SCS_GUARDIASTURNO si ON sg.IDINSTITUCION = si.IDINSTITUCION AND sg.IDTURNO = si.IDTURNO AND sg.IDGUARDIA = si.IDGUARDIA WHERE sg.IDINSTITUCION = "
	            + idInstitucion + " AND sg.IDTURNO IN (" + listadoIdTurnos + ") AND sg.IDPERSONA =  " + idPersona
	            + ")";

	    return sql;
	} 
	//SIGARNV-2009@DTT.JAMARTIN@06/08/2021@FIN
	
	
	
	public String buscarGuardiasTurnosInscritos(List<InscripcionesItem> inscripcionesItems, Short idInstitucion, String idPersona) {
	    String listadoIdTurnos = "";
	    for(int i=0; i<inscripcionesItems.size(); i++) {
	        if(i==0) {
	            listadoIdTurnos += inscripcionesItems.get(i).getIdturno();
	        } else {
	            listadoIdTurnos += ", "+inscripcionesItems.get(i).getIdturno();
	        }
	    }
	    
	    //En la consulta se incluyen aquellas inscripciones a guardias que hayan sido dadas de baja.
	    //Si se quisieran excluir, se deberia añadir el condicional más abajo.
	    String sql = "SELECT sg2.IDGUARDIA, F_SIGA_GETRECURSO(stp.descripcion, 1) AS DESCRIPCION, sg2.IDINSTITUCION , sg2.IDTURNO, " +
	            "sg2.FECHAMODIFICACION,  sg2.USUMODIFICACION,  sg2.FECHABAJA,  sg2.NOMBRE " +
	            "FROM SCS_GUARDIASTURNO sg2 " +
	            "INNER JOIN SCS_TIPOSGUARDIAS stp ON stp.IDTIPOGUARDIA = sg2.IDTIPOGUARDIA "
	            + " WHERE sg2.IDINSTITUCION = " + idInstitucion
	            + " AND sg2.IDTURNO IN (" + listadoIdTurnos + ") AND sg2.IDGUARDIA IN (SELECT DISTINCT sg.IDGUARDIA FROM "
	            + "SCS_INSCRIPCIONGUARDIA sg INNER JOIN SCS_TURNO st ON sg.IDINSTITUCION = st.IDINSTITUCION AND sg.IDTURNO = st.IDTURNO "
	            + "INNER JOIN SCS_GUARDIASTURNO si ON sg.IDINSTITUCION = si.IDINSTITUCION AND sg.IDTURNO = si.IDTURNO AND sg.IDGUARDIA = si.IDGUARDIA WHERE sg.IDINSTITUCION = "
	            + idInstitucion  
	            //+" AND asg.FECHABAJA IS NULL"
	            + " AND sg.IDTURNO IN (" + listadoIdTurnos + ") AND sg.IDPERSONA =  " + idPersona
	            + ")";

	    return sql;
	} 
	
	public String busquedaColaOficio(InscripcionesItem inscripcionesItem,String strDate,String busquedaOrden, Short idInstitucion) {
		SQL sql = new SQL();
		
		if(busquedaOrden == null || busquedaOrden.length() == 0) {
			busquedaOrden = "ANTIGUEDADCOLA";
		}
		sql.SELECT("ROWNUM AS  orden_cola,consulta_total.* from(WITH tabla_nueva AS (SELECT consulta2.*\r\n" + 
				"FROM (SELECT ROWNUM AS orden,consulta.* \r\n" + 
				"FROM (SELECT (CASE\r\n" + 
				"				WHEN Ins.Fechavalidacion IS NOT NULL\r\n" + 
				"				AND TRUNC(Ins.Fechavalidacion) <= NVL(TO_DATE('"+strDate+"', 'DD/MM/YYYY'), Ins.Fechavalidacion)\r\n" + 
				"				AND (Ins.Fechabaja IS NULL\r\n" + 
				"				OR TRUNC(Ins.Fechabaja) > NVL(TO_DATE('"+strDate+"', 'DD/MM/YYYY'), TO_DATE('01/01/1900', 'DD/MM/YYYY'))) THEN '1'\r\n" + 
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
		sql.WHERE("(ins.fechabaja is null AND Ins.Fechavalidacion IS NOT NULL AND tur.Idinstitucion = '"+idInstitucion+"'AND tur.Idturno = '"+inscripcionesItem.getIdturno()+"')");
		sql.ORDER_BY("/*aqui debemos de consultar primero el orden que vamos a ordenar.*/\r\n" +busquedaOrden+
				"          ) consulta ) consulta2)\r\n" + 
				"SELECT * from(\r\n" + 
				"SELECT  tabla_nueva.* FROM tabla_nueva)\r\n" + 
				"\r\n" + 
				") consulta_total       \r\n" + 
				"");
		
		return sql.toString();
		
	}
	
	//SIGARNV-2471@DTT.JAMARTIN@11/07/2022@INICIO
	public String busquedaColaOficio2(InscripcionesItem inscripcionesItem,String strDate,String busquedaOrden, Short idInstitucion) {

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
		sql4.WHERE("ins.fechabaja is null");
		sql4.WHERE("Ins.Fechavalidacion IS NOT NULL "+
				"AND tur.Idinstitucion = '"+idInstitucion+"'" +
				"AND tur.Idturno = '"+inscripcionesItem.getIdturno()+"'");
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
				"AND tur.Idturno = '"+inscripcionesItem.getIdturno()+"'");

		sqls3.SELECT(" * from(\r\n" +
				"SELECT tabla_nueva.* FROM tabla_nueva, tabla_nueva2\r\n" +
				"WHERE tabla_nueva.orden>tabla_nueva2.orden ORDER BY tabla_nueva.orden asc)\r\n" +
				"UNION\r\n" +
				"SELECT * FROM (SELECT tabla_nueva.* FROM tabla_nueva, tabla_nueva2\r\n" +
				"WHERE tabla_nueva.orden<=tabla_nueva2.orden ORDER BY tabla_nueva.orden asc)\r\n" +
				") consulta_total where activo = '1' ");

		sqls5.ORDER_BY(busquedaOrden+") consulta3)consulta4 WHERE idpersona = \r\n" +
				"( SELECT IDPERSONA_ULTIMO FROM SCS_TURNO\r\n" +
				"        where Idinstitucion = '"+idInstitucion+"'"+
				"		AND Idturno = '"+inscripcionesItem.getIdturno()+"'" +
				"	) )"+sqls3.toString());

		sql3.SELECT("ROWNUM AS orden,consulta.* ");
		sql3.FROM("("+sql4.toString()+") consulta WHERE activo = 1) consulta2),tabla_nueva2 AS ("+sqls5.toString());


		sql2.SELECT("consulta2.*");
		sql2.FROM("("+sql3.toString());

		sql.SELECT("ROWNUM AS  orden_cola,consulta_total.* from(WITH tabla_nueva AS ("+sql2.toString());

		return sql.toString();

	}
	//SIGARNV-2471@DTT.JAMARTIN@11/07/2022@FIN
	
	public String checkSaltos(InscripcionesItem inscripcionesItem,String strDate,String busquedaOrden, Short idInstitucion) {
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
		sql.WHERE("(ins.fechabaja is null AND Ins.Fechavalidacion IS NOT NULL AND tur.Idinstitucion = '"+idInstitucion+"'AND tur.Idturno = '"+inscripcionesItem.getIdturno()+"')");
		sql.ORDER_BY("/*aqui debemos de consultar primero el orden que vamos a ordenar.*/\r\n" +busquedaOrden+
				"          ) consulta ) consulta2)\r\n" + 
				"SELECT * from(\r\n" + 
				"SELECT  tabla_nueva.* FROM tabla_nueva)\r\n" + 
				"\r\n" + 
				") consulta_total       \r\n" + 
				"");
		
		return sql.toString()+" WHERE (saltos >0 OR compensaciones >0)";
		
	}

	public String borrarSaltos(InscripcionesItem inscripcionesItem, Short idInstitucion, int usumodificacion) {

		SQL sql = new SQL();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		sql.UPDATE("SCS_SALTOSCOMPENSACIONES");
		
		sql.SET("FECHAMODIFICACION = TO_DATE('"+dateFormat.format(new Date())+"','DD/MM/RRRR')");
		sql.SET("MOTIVOS = 'Baja de colegiado en el turno'");
		sql.SET("FECHACUMPLIMIENTO= TO_DATE('"+dateFormat.format(inscripcionesItem.getFechaActual())+"','DD/MM/RRRR')");
		sql.SET("USUMODIFICACION= '"+String.valueOf(usumodificacion) +"'");
		
		sql.WHERE("idturno ='"+inscripcionesItem.getIdturno()+"'");
		sql.WHERE("idinstitucion ='"+idInstitucion+"'");
		sql.WHERE("idpersona ='"+inscripcionesItem.getIdpersona()+"'");
			
		return sql.toString();
	}
	
	public String busquedaTarjeta(InscripcionesItem inscripcionesItem, Short idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("SCS_INSCRIPCIONTURNO");
		sql.WHERE("idturno ='"+inscripcionesItem.getIdturno()+"'");
		sql.WHERE("idinstitucion ='"+idInstitucion+"'");
		sql.WHERE("idpersona ='"+inscripcionesItem.getIdpersona()+"'");
			
		return sql.toString();
	}
	
	public String busquedaInscripcionesCMO(String turnos, Short idInstitucion) {
		
		SQL sql1 = new SQL();
		
		sql1.SELECT_DISTINCT("CEN_COLEGIADO.ncolegiado, SCS_INSCRIPCIONTURNO.idturno, SCS_INSCRIPCIONTURNO.fechavalidacion, SCS_INSCRIPCIONTURNO.fechabaja");
		sql1.FROM("SCS_INSCRIPCIONTURNO JOIN CEN_COLEGIADO ON CEN_COLEGIADO.IDPERSONA=SCS_INSCRIPCIONTURNO.IDPERSONA");
		if(!turnos.contains(","))sql1.WHERE("SCS_INSCRIPCIONTURNO.IDTURNO = '"+turnos+"'");
		else sql1.WHERE("SCS_INSCRIPCIONTURNO.IDTURNO IN ("+turnos+")");
		sql1.WHERE("SCS_INSCRIPCIONTURNO.idinstitucion ='"+idInstitucion.toString()+"'");
		sql1.WHERE("SCS_INSCRIPCIONTURNO.fechavalidacion is not null");
		
		return sql1.toString();
	}
	
	
	public String obtenerColegiadoInscritoTurno(Short idInstitucion, String idTurno, String idPersona) {
		
		SQL sql1 = new SQL();
		
		sql1.SELECT_DISTINCT("*");
		sql1.FROM("SCS_INSCRIPCIONTURNO");
	

		sql1.WHERE("SCS_INSCRIPCIONTURNO.idinstitucion ='"+idInstitucion.toString()+"'");
		sql1.WHERE("SCS_INSCRIPCIONTURNO.idturno ='"+idTurno+"'");
		sql1.WHERE("SCS_INSCRIPCIONTURNO.idpersona ='"+idPersona+"'");
		sql1.WHERE("SCS_INSCRIPCIONTURNO.fechabaja is null");
		
		return sql1.toString();
	}
	
	public String comboTurnosInscritoLetrado(Short idInstitucion, String idPersona) {
		SQL sql = new SQL();
		sql.SELECT("t.IDTURNO", "t.NOMBRE");
		sql.FROM("SCS_TURNO t");
		sql.INNER_JOIN("scs_inscripcionturno IT ON t.idturno =  IT.IDTURNO AND t.idinstitucion = it.idinstitucion");
		sql.WHERE("t.idinstitucion = "+idInstitucion,
				"it.fechabaja is null",
				"it.fechavalidacion is not null",
				"it.idpersona = '"+idPersona+"'");
		
		return sql.toString();
	}
}
