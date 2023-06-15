package org.itcgae.siga.db.services.exp.providers;

import org.apache.ibatis.jdbc.SQL;

public class ExpProcedimientosExeaSqlExtendsProvider {

   public String comboProcedimientosEXEA(String idlenguaje, String idInstitucion){
       SQL SQL = new SQL();

       SQL.SELECT("ID_PROC_EXEA",
               "f_siga_getrecurso(nombre," + idlenguaje + ") NOMBRE");
       SQL.FROM("exp_procedimientos_exea");
       SQL.WHERE("idinstitucion = '" + idInstitucion + "'",
               "PERMITE_INICIO_MANUAL != 0");
       SQL.ORDER_BY("ORDEN ASC");

       return SQL.toString();
   }
}
