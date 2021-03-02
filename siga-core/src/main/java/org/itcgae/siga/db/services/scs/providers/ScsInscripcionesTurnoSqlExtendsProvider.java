package org.itcgae.siga.db.services.scs.providers;

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
			String condturnos ="(";
			for(int i = 0; i< inscripcionesItem.getIdturno().split(",").length; i++) {
				if(i>0) condturnos+=" or ";
				condturnos+="ins.idturno ='"+inscripcionesItem.getIdturno().split(",")[i]+"'";
			}
			condturnos+=")";
			sql.WHERE(condturnos);
			
		}
		if(inscripcionesItem.getNcolegiado() != null) {
			sql.WHERE("(col.ncolegiado ='"+inscripcionesItem.getNcolegiado()+"')");
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
			sql.WHERE("((ins.fechavalidacion is not null and ins.fechavalidacion <= '"+afechade+"' and ins.fechasolicitudbaja is null)" + 
					" OR (ins.fechasolicitudbaja is not null and ins.fechavalidacion <= '"+afechade+"'and ins.fechabaja"
					+ " is null))") ;
		}
		if(inscripcionesItem.getFechadesde() != null) {
			sql.WHERE("ins.fechasolicitud >= '"+fechadesde+"'");
			if(inscripcionesItem.getFechahasta() != null)
			sql.WHERE("ins.fechasolicitud <= '"+fechahasta+"'");
		}
		sql.ORDER_BY("fechasolicitud DESC");
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
	
	public String busquedaColaOficio(InscripcionesItem inscripcionesItem,String strDate,String busquedaOrden, Short idInstitucion) {
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
}
