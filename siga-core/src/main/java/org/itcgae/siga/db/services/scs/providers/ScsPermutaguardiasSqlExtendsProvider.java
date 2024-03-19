package org.itcgae.siga.db.services.scs.providers;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.GuardiasItem;
import org.itcgae.siga.DTOs.scs.PermutaDTO;
import org.itcgae.siga.DTOs.scs.PermutaItem;
import org.itcgae.siga.DTOs.scs.PermutaItem2;
import org.itcgae.siga.db.mappers.ScsPermutaguardiasSqlProvider;

public class ScsPermutaguardiasSqlExtendsProvider extends ScsPermutaguardiasSqlProvider {

    public String getPermutaColeg(PermutaItem permutaItem, Short idInstitucion) {


        SQL sql = new SQL();


        sql.SELECT("  pg.fechasolicitud,"
                + "    pg.fechaconfirmacion,"
                + "    turno.nombre turnopermuta,"
                + "    guardia.nombre guardiadestino,"
                + "    pg.motivossolicitante motivos,"
                + "    pg.idinstitucion,"
                + "    pg.numero,"
                + "    pg.idpersona_solicitante,"
                + "    pg.idpersona_confirmador,"
                + "    pg.idturno_solicitante,"
                + "    pg.idcalendarioguardias_solicitan,"
                + "    pg.idguardia_solicitante,"
                + "    pg.idturno_confirmador,"
                + "    pg.idcalendarioguardias_confirmad,"
                + "    pg.idguardia_confirmador,"
                + "    pg.fechainicio_solicitante,"
                + "    pg.fechainicio_confirmador,"
                + "    pg.motivosconfirmador,"
                + "    pg.motivossolicitante,"
                + "    pg.id_per_cab_solicitante,"
                + "    pg.id_per_cab_confirmador,"
                + "    pc.idcalendarioguardias,"
                + "    pc.fecha,"
                + "    pc.idturno,"
                + "    pc.idguardia,"
                + "    pc.idpersona");

        sql.FROM("scs_permutaguardias pg");
        sql.JOIN("scs_permuta_cabecera pc ON"
                + "        pg.idinstitucion = pc.idinstitucion"
                + "    AND ("
                + "            pg.id_per_cab_solicitante = pc.id_permuta_cabecera"
                + "        OR"
                + "            pg.id_per_cab_confirmador = pc.id_permuta_cabecera"
                + "    )"
        );

        sql.JOIN("scs_turno turno ON"
                + "        turno.idinstitucion = pc.idinstitucion"
                + "    AND"
                + "        turno.idturno = pc.idturno");
        sql.JOIN("scs_guardiasturno guardia ON"
                + "        guardia.idinstitucion = pg.idinstitucion"
                + "    AND"
                + "        guardia.idguardia = pc.idguardia");
        sql.WHERE(" pc.idinstitucion = " + idInstitucion);
        sql.WHERE(" pc.idguardia = " + permutaItem.getIdguardia());
        sql.WHERE(" pc.idturno = " + permutaItem.getIdturno());
        sql.WHERE("ROWNUM <= 200");


        return sql.toString();
    }
    
    public String getPermutasGuardiaColeg(PermutaItem2 permutaItem, Short idInstitucion) {
    	String fechainicio;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        fechainicio = dateFormat.format(permutaItem.getFechainicioSolicitante());

        SQL sql = new SQL();
        SQL sql2 = new SQL();    
        
        sql2.SELECT("1");
        sql2.FROM("SCS_PERMUTAGUARDIAS sp2");
        sql2.WHERE("sp2.idinstitucion = " + idInstitucion);
        sql2.WHERE(permutaItem.getIdpersona() + " IN (sp2.IDPERSONA_SOLICITANTE, sp2.IDPERSONA_CONFIRMADOR)");
        sql2.WHERE("((" + permutaItem.getIdturno() + " = sp2.IDTURNO_SOLICITANTE "
        		+ "and " + permutaItem.getIdguardia() + " = sp2.IDGUARDIA_SOLICITANTE "
        		+ "and TO_DATE('" + fechainicio + "', 'DD/MM/YYYY') = TRUNC(sp2.FECHAINICIO_SOLICITANTE) "
        		+ ")"
        		+ "or (" + permutaItem.getIdturno() + " = sp2.IDTURNO_CONFIRMADOR "
        		+ "and " + permutaItem.getIdguardia() + " = sp2.IDGUARDIA_CONFIRMADOR "
        		+ "and TO_DATE('" + fechainicio + "', 'DD/MM/YYYY') = TRUNC(sp2.FECHAINICIO_CONFIRMADOR) "
        				+ "))");
        
        sql.SELECT("  sp.fechasolicitud,"
                + "    sp.fechaconfirmacion,"
                + "    turno.nombre turnopermuta,"
                + "    guardia.nombre guardiadestino,"
                + "    sp.motivossolicitante motivos,"
                + "    sp.idinstitucion,"
                + "    sp.numero,"
                + "    sp.idpersona_solicitante,"
                + "    sp.idpersona_confirmador,"
                + "    sp.idturno_solicitante,"
                + "    sp.idcalendarioguardias_solicitan,"
                + "    sp.idguardia_solicitante,"
                + "    sp.idturno_confirmador,"
                + "    sp.idcalendarioguardias_confirmad,"
                + "    sp.idguardia_confirmador,"
                + "    sp.fechainicio_solicitante,"
                + "    sp.fechainicio_confirmador,"
                + "    sp.motivosconfirmador,"
                + "    sp.motivossolicitante,"
                + "    sp.id_per_cab_solicitante,"
                + "    sp.id_per_cab_confirmador,"
                //SIGARNV-2885@DTT.JAMARTIN@07/02/2023@INICIO
        		+ "	(PERSONA1.APELLIDOS1 || ' ' || PERSONA1.APELLIDOS2 || ', ' || PERSONA1.NOMBRE) AS NOMBRE_PERSONA_SOLICITANTE,"
        		+ "	(PERSONA2.APELLIDOS1 || ' ' || PERSONA2.APELLIDOS2 || ', ' || PERSONA2.NOMBRE) AS NOMBRE_PERSONA_CONFIRMADOR");
        		//SIGARNV-2885@DTT.JAMARTIN@07/02/2023@FIN
        
        sql.FROM("SCS_PERMUTAGUARDIAS sp");

//        sql.JOIN("scs_turno turno ON\r\n"
//        		+ "	turno.idinstitucion = sp.idinstitucion\r\n"
//        		+ "	AND (turno.idturno = sp.IDTURNO_SOLICITANTE OR turno.idturno = sp.IDTURNO_CONFIRMADOR)");
        sql.JOIN("scs_turno turno ON\r\n"
                + "	turno.idinstitucion = sp.idinstitucion\r\n"
                + "	AND turno.idturno = sp.IDTURNO_CONFIRMADOR");
        sql.JOIN("scs_guardiasturno guardia ON\r\n"
        		+ "	guardia.idinstitucion = sp.idinstitucion\r\n"
        		+ "	AND (guardia.idguardia = sp.IDGUARDIA_SOLICITANTE OR guardia.idguardia = sp.IDGUARDIA_SOLICITANTE)");
        //SIGARNV-2885@DTT.JAMARTIN@07/02/2023@INICIO
        sql.JOIN("CEN_PERSONA PERSONA1 ON sp.IDPERSONA_SOLICITANTE = PERSONA1.IDPERSONA");
        sql.JOIN("CEN_PERSONA PERSONA2 ON sp.IDPERSONA_CONFIRMADOR = PERSONA2.IDPERSONA");
        //SIGARNV-2885@DTT.JAMARTIN@07/02/2023@FIN
        sql.WHERE("sp.idinstitucion = " + idInstitucion);
        
        sql.WHERE("((" + permutaItem.getIdturno() + " = sp.IDTURNO_SOLICITANTE "
        		+ "and " + permutaItem.getIdguardia() + " = sp.IDGUARDIA_SOLICITANTE "
        		+ "and TO_DATE('" + fechainicio + "', 'DD/MM/YYYY') = TRUNC(sp.FECHAINICIO_SOLICITANTE) "
        		+ ")"
        		+ "or (" + permutaItem.getIdturno() + " = sp.IDTURNO_CONFIRMADOR "
        		+ "and " + permutaItem.getIdguardia() + " = sp.IDGUARDIA_CONFIRMADOR)"
        		+ "and TO_DATE('" + fechainicio + "', 'DD/MM/YYYY') = TRUNC(sp.FECHAINICIO_CONFIRMADOR) "
        		+ ")");
        
        sql.WHERE("EXISTS (" + sql2 + ")");
        //SIGARNV-2885@DTT.JAMARTIN@07/02/2023@INICIO 
        sql.WHERE("(sp.IDPERSONA_SOLICITANTE <> " + permutaItem.getIdpersona() + " OR sp.IDPERSONA_CONFIRMADOR <> " + permutaItem.getIdpersona() + ")");
        //SIGARNV-2885@DTT.JAMARTIN@07/02/2023@FIN 
        sql.WHERE("ROWNUM <= 200");
        
        sql.ORDER_BY("FECHACONFIRMACION desc");

        return sql.toString();
    }
    
    public String deletePermutasGuardia(GuardiasItem permutaItem, Short idInstitucion) {
    	String fechainicio;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        fechainicio = dateFormat.format(permutaItem.getFechadesde());
    	SQL sql = new SQL();
    	
    	
    	sql.DELETE_FROM("SCS_PERMUTAGUARDIAS sp");
    	 sql.WHERE("sp.idinstitucion = " + idInstitucion);
    	sql.WHERE("((" + permutaItem.getIdTurno() + " = sp.IDTURNO_SOLICITANTE "
        		+ "and " + permutaItem.getIdGuardia() + " = sp.IDGUARDIA_SOLICITANTE "
        		+ "and TO_DATE('" + fechainicio + "', 'DD/MM/YYYY') = TRUNC(sp.FECHAINICIO_SOLICITANTE) "
        		+ ")"
        		+ "or (" + permutaItem.getIdTurno() + " = sp.IDTURNO_CONFIRMADOR "
        		+ "and " + permutaItem.getIdGuardia() + " = sp.IDGUARDIA_CONFIRMADOR)"
        		+ "and TO_DATE('" +  fechainicio + "', 'DD/MM/YYYY') = TRUNC(sp.FECHAINICIO_CONFIRMADOR) "
        		+ ")");
    	sql.WHERE("(sp.IDPERSONA_SOLICITANTE <> " + permutaItem.getIdPersona() + " OR sp.IDPERSONA_CONFIRMADOR <> " + permutaItem.getIdPersona() + ")");
    	return sql.toString();
    }

    public String getTurnoInscrito(Long idPersona, Short idinstitucion) {
    	
        SQL sql = new SQL();
        SQL sql2 = new SQL();

        /*sql2.SELECT("cabguar.IDTURNO");
        sql2.FROM("SCS_CABECERAGUARDIAS cabguar");
        sql2.WHERE("cabguar.FECHAINICIO >= SYSDATE");
        sql2.WHERE("cabguar.IDINSTITUCION = " + idinstitucion);
        
        sql.SELECT("UNIQUE turno.IDTURNO,"
                + "turno.NOMBRE");
        sql.FROM("SCS_TURNO turno");
        sql.WHERE("turno.IDTURNO IN (" + sql2 + ")");
        sql.WHERE("turno.IDINSTITUCION = " + idinstitucion);*/
        
        sql2.SELECT("cabguar.IDTURNO");
        sql2.FROM("SCS_CABECERAGUARDIAS cabguar");
        sql2.WHERE("(cabguar.FECHAINICIO >= SYSDATE\r\n"
        		+ "			AND cabguar.IDINSTITUCION = " + idinstitucion +")"
        		+ "	AND turno.IDINSTITUCION = " + idinstitucion );
        
    	sql.SELECT("UNIQUE turno.IDTURNO,"
                + "CONCAT(CONCAT(turno.NOMBRE,' / '),sg.nombre) AS NOMBRE");
    	sql.FROM("SCS_TURNO turno");
    	sql.JOIN("SCS_GUARDIASTURNO sg ON\r\n"
        		+ "	sg.IDTURNO = turno.IDTURNO");
    	sql.WHERE("turno.IDTURNO IN (" + sql2 + ")" );
    	
    	sql.ORDER_BY("NOMBRE");
        return sql.toString();
    }

    public String getGuardiaDestinoInscrito(GuardiasItem guardiaItem, String idinstitucion) {
        String fechainicio;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        fechainicio = dateFormat.format(guardiaItem.getFechadesde());

        SQL sql = new SQL();
        sql.SELECT("UNIQUE col.ncolegiado || ' ' || (CASE WHEN per.nombre is  NULL THEN '' ELSE per.apellidos1 || ' ' || per.apellidos2 || ', ' || per.nombre END) || ' | ' || gc.FECHAINICIO AS GUARDIA,"
                + "gc.idguardia  || ' | ' || gc.idturno || ' | ' || gc.idpersona || ' | ' ||  gc.IDCALENDARIOGUARDIAS DATOS,"
                + "gc.fechainicio");
        sql.FROM("SCS_CABECERAGUARDIAS  gc");
        sql.JOIN("SCS_INSCRIPCIONGUARDIA si ON gc.IDINSTITUCION = si.IDINSTITUCION and gc.IDPERSONA = si.IDPERSONA and si.IDGUARDIA = gc.IDGUARDIA AND si.IDTURNO = gc.IDTURNO");
        sql.JOIN("scs_guardiasturno g ON g.idinstitucion = si.idinstitucion AND g.idguardia = si.IDGUARDIA");
        sql.JOIN("cen_persona per ON per.idpersona = gc.idpersona");
        sql.JOIN("cen_colegiado col ON col.idpersona = gc.idpersona");
        
        sql.WHERE("col.IDINSTITUCION = " + idinstitucion);
        sql.WHERE("gc.idinstitucion = " + idinstitucion);
        sql.WHERE("gc.FECHAINICIO > SYSDATE");
        if (guardiaItem.getIdPersona() != null && !guardiaItem.getIdPersona().isEmpty()) {
        	sql.WHERE("gc.idpersona <> " + guardiaItem.getIdPersona());
        }
        //sql.WHERE("SYSDATE between si.FECHAVALIDACION and nvl(si.FECHABAJA, '31/12/2999')");
        sql.WHERE("gc.IDTURNO = " + guardiaItem.getIdTurno());
        sql.ORDER_BY("gc.fechainicio desc");
        //sql.WHERE("gc.IDGUARDIA = " + guardiaItem.getIdGuardia());
        //sql.WHERE("gc.IDCALENDARIOGUARDIAS = " + guardiaItem.getIdCalendarioGuardias());
        //SIGARNV-2885@DTT.JAMARTIN@06/02/2023@INICIO
        //sql.WHERE("gc.IDPERSONA <> " + guardiaItem.getIdPersona());
        //SIGARNV-2885@DTT.JAMARTIN@06/02/2023@FIN 
        
     // Agregar la cláusula ORDER BY para ordenar por el campo idguardia de forma ascendente
        
      
        return sql.toString();
    }

    public String maxIdPermutaGuardia(String idinstitucion) {

        SQL sql = new SQL();

        sql.SELECT("max(numero) + 1");
        sql.FROM("SCS_PERMUTAGUARDIAS");
        sql.WHERE("idinstitucion = " + idinstitucion);

        return sql.toString();

    }

    public String deletePermutasCalendarioSolicitante(Integer idInstitucion, Integer idCalendarioGuardias, Integer idTurno, Integer idGuardia) {

        SQL sql = new SQL();

        // Como solicitante
        sql.DELETE_FROM("SCS_PERMUTAGUARDIAS");
        sql.WHERE("IDINSTITUCION = " + idInstitucion);
        sql.WHERE("IDCALENDARIOGUARDIAS_SOLICITAN = " + idCalendarioGuardias);
        sql.WHERE("IDTURNO_SOLICITANTE = " + idTurno);
        sql.WHERE("IDGUARDIA_SOLICITANTE = " + idGuardia);

        return sql.toString();

    }

    public String deletePermutasCalendarioConfirmador(Integer idInstitucion, Integer idCalendarioGuardias, Integer idTurno, Integer idGuardia) {

        SQL sql = new SQL();

        // Como confirmador
        sql.DELETE_FROM("SCS_PERMUTAGUARDIAS");
        sql.WHERE("IDINSTITUCION = " + idInstitucion);
        sql.WHERE("IDCALENDARIOGUARDIAS_CONFIRMAD = " + idCalendarioGuardias);
        sql.WHERE("IDTURNO_CONFIRMADOR = " + idTurno);
        sql.WHERE("IDGUARDIA_CONFIRMADOR = " + idGuardia);

        return sql.toString();

    }
    
    //SIGARNV-2885@DTT.JAMARTIN@06/02/2023@INICIO
    public String getFechaSolicitanteInicio(String idPersona, Short idCalendarioGuardias, Short idGuardia, Short idInstitucion) {
    	 SQL sql = new SQL();
    	 
    	 sql.SELECT("FECHAINICIO_SOLICITANTE");
    	 sql.FROM("SCS_PERMUTAGUARDIAS");
    	 sql.WHERE("IDPERSONA_SOLICITANTE = " + idPersona); 
    	 sql.WHERE("IDCALENDARIOGUARDIAS_SOLICITAN = " + idCalendarioGuardias); 
    	 sql.WHERE("IDGUARDIA_SOLICITANTE = " + idGuardia); 
    	 sql.WHERE("IDINSTITUCION = " + idInstitucion); 
    	 sql.ORDER_BY("FECHASOLICITUD desc");
    	 
    	 return sql.toString();
    }
	//SIGARNV-2885@DTT.JAMARTIN@06/02/2023@FIN 

}
