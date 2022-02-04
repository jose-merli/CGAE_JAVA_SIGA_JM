package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CenClienteSqlProvider;

public class CenClienteSqlExtendsProvider extends CenClienteSqlProvider {

    public String getIdPersonaWithNif(String personaNif, Short idInstitucion) {
        SQL sql = new SQL();

        sql.SELECT("p.idpersona");
        sql.FROM("cen_cliente cli ");
        sql.INNER_JOIN("cen_persona p on P.IDPERSONA = CLI.IDPERSONA");
        sql.WHERE("CLI.IDINSTITUCION = '" + idInstitucion.shortValue() + "'");
        sql.WHERE("p.nifcif = '" + personaNif + "'");

        return sql.toString();
    }

    public String getIdPersona(String colegiadoNumero, String personaNif, Short idInstitucion) {
        SQL sql = new SQL();

        sql.SELECT("p.idpersona");
        sql.FROM("cen_persona p");
        sql.INNER_JOIN("CEN_COLEGIADO col on P.IDPERSONA = COL.IDPERSONA");
        sql.WHERE("COL.IDINSTITUCION = '" + idInstitucion.shortValue() + "'");
        sql.WHERE("NVL(col.NCOLEGIADO, col.NCOMUNITARIO) = '" + colegiadoNumero + "'");

        if (personaNif != null && personaNif != "") {
            sql.WHERE("p.nifcif = '" + personaNif + "'");
        }


        return sql.toString();
    }

    public String getEsLetrado(String idPersona, String idInstitucion) {
        SQL sql = new SQL();

        sql.SELECT("nvl(cen_cliente.letrado,0)AS LETRADO");
        sql.FROM("cen_cliente");
        sql.WHERE("IDPERSONA = '" + idPersona + "'");
        sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");


        return sql.toString();
    }

    public String getInstitucionesEjerciente(String idPersona, String idInstitucion) {
        SQL sql = new SQL();

        sql.SELECT("IDINSTITUCION");
        sql.FROM("CEN_DATOSCOLEGIALESESTADO");
        sql.WHERE("IDPERSONA = '" + idPersona + "'");
        sql.WHERE("IDESTADO = '20'");
        sql.WHERE("FECHAMODIFICACION = (SELECT max(fechamodificacion) " +
                "       	 FROM CEN_DATOSCOLEGIALESESTADO " +
                "       	   WHERE IDPERSONA = '" + idPersona + "' " +
                "       	   and idinstitucion in  " +
                "       	 (SELECT idinstitucion  " +
                "       	 FROM CEN_INSTITUCION  " +

                "       	  START WITH IDINSTITUCION = '" + idPersona + "'" +
                "       	 CONNECT BY PRIOR IDINSTITUCION = CEN_INST_IDINSTITUCION) )");


        return sql.toString();
    }

    public String getTratamiento(String idInstitucion, String idPersona, int idIdioma) {
        SQL sql = new SQL();

        sql.SELECT("f_siga_getrecurso (t.DESCRIPCION, " + idIdioma + ") as TRATAMIENTO");
        sql.FROM("cen_cliente c, cen_persona p, cen_tratamiento t");
        sql.WHERE("c.idpersona = '" + idPersona + "'");
        sql.WHERE("c.idinstitucion = " + idInstitucion + " AND c.idpersona = p.idpersona AND c.idtratamiento = t.idtratamiento");

        return sql.toString();
    }

}
