package org.itcgae.siga.db.services.scs.providers;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.GuardiasItem;
import org.itcgae.siga.DTOs.scs.PermutaDTO;
import org.itcgae.siga.DTOs.scs.PermutaItem;
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

    public String getTurnoInscrito(Long idPersona, Short idinstitucion) {
        SQL sql = new SQL();

        sql.SELECT("it.IDTURNO,"
                + "t.NOMBRE");
        sql.FROM("SCS_INSCRIPCIONTURNO it");
        sql.JOIN("scs_turno t on it.IDTURNO = t.IDTURNO and it.IDINSTITUCION = t.IDINSTITUCION");
        sql.WHERE("it.IDINSTITUCION = " + idinstitucion);
        sql.WHERE("it.IDPERSONA =" + idPersona);

        return sql.toString();
    }

    public String getGuardiaDestinoInscrito(GuardiasItem guardiaItem, String idinstitucion) {
        String fechainicio;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        fechainicio = dateFormat.format(guardiaItem.getFechadesde());

        SQL sql = new SQL();
        sql.SELECT("g.NOMBRE || ' | ' || col.ncolegiado || ' ' || (CASE WHEN per.nombre is  NULL THEN '' ELSE per.apellidos1 || ' ' || per.apellidos2 || ', ' || per.nombre END) || ' | ' || gc.FECHAINICIO AS GUARDIA,"
                + "gc.idguardia  || ' | ' || gc.idturno || ' | ' || gc.idpersona || ' | ' ||  gc.IDCALENDARIOGUARDIAS DATOS,"
                + "gc.fechainicio");
        sql.FROM("SCS_CABECERAGUARDIAS  gc");
        sql.JOIN("SCS_INSCRIPCIONGUARDIA si ON gc.IDINSTITUCION = si.IDINSTITUCION and gc.IDPERSONA = si.IDPERSONA and si.IDGUARDIA = gc.IDGUARDIA AND si.IDTURNO = gc.IDTURNO");
        sql.JOIN("scs_guardiasturno g ON g.idinstitucion = si.idinstitucion AND g.idguardia = si.IDGUARDIA");
        sql.JOIN("cen_persona per ON per.idpersona = gc.idpersona");
        sql.JOIN("cen_colegiado col ON col.idpersona = gc.idpersona");

        sql.WHERE("gc.idinstitucion = " + idinstitucion);
        sql.WHERE("gc.FECHAINICIO >= TO_DATE('" + fechainicio + "', 'DD/MM/RRRR')");
        sql.WHERE("TO_DATE('" + fechainicio + "', 'DD/MM/RRRR') between si.FECHAVALIDACION and nvl(si.FECHABAJA, '31/12/2999')");
        sql.WHERE("gc.IDTURNO = " + guardiaItem.getIdTurno());
        sql.WHERE("gc.IDGUARDIA = " + guardiaItem.getIdGuardia());
        sql.WHERE("gc.IDCALENDARIOGUARDIAS = " + guardiaItem.getIdCalendarioGuardias());


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

}
