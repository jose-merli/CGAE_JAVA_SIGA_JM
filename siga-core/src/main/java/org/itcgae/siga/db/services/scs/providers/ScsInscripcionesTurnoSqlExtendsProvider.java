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
				"    DECODE(col.comunitario,'1',col.ncomunitario,col.ncolegiado) ncolegiado,"
				+ "DECODE(tur.GUARDIAS, 0, 'Obligatorias', DECODE(tur.GUARDIAS, 2, 'A elegir', 'Todas o ninguna'))as tipoguardias\r\n" + 
				"FROM\r\n" + 
				"    scs_inscripcionturno ins\r\n" + 
				"    JOIN cen_colegiado col ON col.idpersona = ins.idpersona\r\n" + 
				"                                    AND col.idinstitucion = ins.idinstitucion\r\n" + 
				"     JOIN cen_persona per ON per.idpersona = col.idpersona\r\n" + 
				"    LEFT JOIN scs_turno tur ON tur.idturno = ins.idturno\r\n" + 
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
				sql.WHERE("((ins.fechavalidacion is null and ins.fechasolicitudbaja is null and ins.fechabaja "
						+ "is null and ins.fechadenegacion is null) OR (ins.fechavalidacion is not null and ins.fechasolicitudbaja is not null and ins.fechabaja"
						+ " is null and ins.fechadenegacion is null))") ;
			}
			
			if(inscripcionesItem.getEstado().equals("1")) {
				sql.WHERE("((ins.fechavalidacion is not null and ins.fechasolicitudbaja is null and ins.fechabaja "
						+ "is null and ins.fechadenegacion is null) OR (ins.fechavalidacion is not null and ins.fechasolicitudbaja is not null and ins.fechabaja"
						+ " is not null and ins.fechadenegacion is null))") ;
			}
			
			if(inscripcionesItem.getEstado().equals("2")) {
				sql.WHERE("(ins.fechavalidacion is not null and ins.fechasolicitudbaja is not null and ins.fechabaja "
						+ "is not null and ins.fechadenegacion is not null)") ;
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
		if (tamMax != null) {
			Integer tamMaxNumber = tamMax + 1;
			sql.WHERE("rownum <= " + tamMaxNumber);
		}
			
		return sql.toString();
	}
	
	public String busquedaTarjetaInscripciones(InscripcionesItem inscripcionesItem, Short idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT(
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
				"          SCS_GUARDIASTURNO.IDGUARDIA,\r\n" + 
				"          SCS_GUARDIASTURNO.NOMBRE AS NOMBRE_GUARDIA,\r\n" + 
				"          GEN_RECURSOS_CATALOGOS.DESCRIPCION AS DESCRIPCION_TIPO_GUARDIA,\r\n" + 
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
				"          LEFT JOIN GEN_RECURSOS_CATALOGOS ON SCS_TIPOSGUARDIAS.DESCRIPCION = GEN_RECURSOS_CATALOGOS.IDRECURSO \r\n" + 
				"WHERE\r\n" + 
				"          SCS_INSCRIPCIONTURNO.IDINSTITUCION ='"+idInstitucion+"'" + 
				"          AND SCS_INSCRIPCIONTURNO.IDTURNO = '"+inscripcionesItem.getIdturno()+"'" + 
				"          AND SCS_INSCRIPCIONTURNO.IDPERSONA = '"+inscripcionesItem.getIdpersona()+"'" + 
				"          AND GEN_RECURSOS_CATALOGOS.IDLENGUAJE = 1\r\n" + 
				"          AND\r\n" + 
				"          --Nunca ha solicitado inscripcion\r\n" + 
				"((SCS_INSCRIPCIONTURNO.FECHASOLICITUD IS NULL\r\n" + 
				"          AND SCS_INSCRIPCIONTURNO.FECHABAJA IS NULL)\r\n" + 
				"          --Esté de baja\r\n" + 
				"          OR (SCS_INSCRIPCIONTURNO.FECHABAJA IS NOT NULL)\r\n" + 
				"          --está inscrito pero que tengan alguna guardia a la que no estén inscritos\r\n" + 
				"          OR ((SCS_INSCRIPCIONTURNO.FECHASOLICITUD IS NULL\r\n" + 
				"          AND SCS_INSCRIPCIONTURNO.FECHABAJA IS NULL\r\n" + 
				"          AND SCS_INSCRIPCIONTURNO.FECHADENEGACION IS NULL)\r\n" + 
				"          --pero que tengan alguna guardia a la que no estén inscritos\r\n" + 
				"          AND (\r\n" + 
				"          SELECT\r\n" + 
				"                    (GUARDIAS_EN_TURNO_COUNT - COUNT(GUARDIAS)) AS ESTA_INSCRITO_EN_TODAS\r\n" + 
				"                    --0 SI ESTÁ INSCRITO EN TODAS\r\n" + 
				"\r\n" + 
				"                    FROM ( WITH GUARDIAS_EN_TURNO AS (\r\n" + 
				"                    SELECT\r\n" + 
				"                              IDGUARDIA\r\n" + 
				"                    FROM\r\n" + 
				"                              SCS_GUARDIASTURNO\r\n" + 
				"                    WHERE\r\n" + 
				"                              IDINSTITUCION = '"+idInstitucion+"'" + 
				"                              AND IDTURNO = '"+inscripcionesItem.getIdturno()+"'" + 
				"                              AND FECHABAJA IS NULL)\r\n" + 
				"                    SELECT\r\n" + 
				"                              (\r\n" + 
				"                              SELECT\r\n" + 
				"                                       COUNT (*)\r\n" + 
				"                              FROM\r\n" + 
				"                                       GUARDIAS_EN_TURNO) GUARDIAS_EN_TURNO_COUNT,\r\n" + 
				"                              (\r\n" + 
				"                              SELECT\r\n" + 
				"                                       IDGUARDIA\r\n" + 
				"                              FROM\r\n" + 
				"                                       SCS_INSCRIPCIONGUARDIA\r\n" + 
				"                              WHERE\r\n" + 
				"                                       IDINSTITUCION = '"+idInstitucion+"'" + 
				"                                       AND IDTURNO = '"+inscripcionesItem.getIdturno()+"'" + 
				"                                       AND IDPERSONA = '"+inscripcionesItem.getIdpersona()+"'" + 
				"                                       AND IDGUARDIA IN (GUARDIAS_EN_TURNO.IDGUARDIA)) GUARDIAS\r\n" + 
				"                    FROM\r\n" + 
				"                              GUARDIAS_EN_TURNO)");
		sql.GROUP_BY("GUARDIAS_EN_TURNO_COUNT) > 0))");
		
			
		return sql.toString();
	}

}
