package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.FacAbono;
import org.itcgae.siga.db.mappers.FacAbonoSqlProvider;

import java.util.Date;
import java.util.List;

public class FacAbonoSqlExtendsProvider extends FacAbonoSqlProvider {

    public String getNuevoID(String idInstitucion) {

        SQL sql = new SQL();
        sql.SELECT("(NVL(MAX(IDABONO) + 1, 1)) AS IDABONO");
        sql.FROM("FAC_ABONO");
        sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

        return sql.toString();
    }

    public String getIdAbonosPorPago(Short idInstitucion, Integer idPagosjg) {

        SQL sql = new SQL();
        sql.SELECT("IDABONO");
        sql.FROM("FAC_ABONO");
        sql.WHERE("IDINSTITUCION = " + idInstitucion);
        sql.WHERE("IDPAGOSJG = " + idPagosjg);

        return sql.toString();
    }

    public String restableceValoresAbono(Short idInstitucion, Long idDisqueteAbono) {

        SQL sql2 = new SQL();
        sql2.SELECT("ABODIS.IMPORTEABONADO");
        sql2.FROM("FAC_ABONOINCLUIDOENDISQUETE ABODIS");
        sql2.WHERE("ABODIS.IDINSTITUCION = " + idInstitucion);
        sql2.WHERE("ABODIS.IDDISQUETEABONO = " + idDisqueteAbono);
        sql2.WHERE("ABODIS.IDABONO = FAC_ABONO.IDABONO");

        SQL sql3 = new SQL();
        sql3.SELECT("IDABONO");
        sql3.FROM("FAC_ABONOINCLUIDOENDISQUETE");
        sql2.WHERE("IDINSTITUCION = " + idInstitucion);
        sql2.WHERE("IDDISQUETEABONO = " + idDisqueteAbono);

        SQL sql = new SQL();
        sql.UPDATE("FAC_ABONO");
        sql.SET("IMPTOTALABONADOPORBANCO = IMPTOTALABONADOPORBANCO - (" + sql2.toString() + ")");
        sql.SET("IMPTOTALABONADO = IMPTOTALABONADO - (" + sql2.toString() + ")");
        sql.SET("IMPPENDIENTEPORABONAR = IMPPENDIENTEPORABONAR + (" + sql2.toString() + ")");
        sql.SET("ESTADO = 5");
        sql.WHERE("IDINSTITUCION = " + idInstitucion);
        sql.WHERE("IDABONO IN (" + sql3.toString() + ")");

        return sql.toString();
    }

    public String hayAbonoPosterior(Short idInstitucion, Integer idPago) {

        SQL subQuery3 = new SQL();
        subQuery3.SELECT("MAX(FECHA)");
        subQuery3.FROM("FAC_ABONO");
        subQuery3.WHERE("IDINSTITUCION = " + idInstitucion);
        subQuery3.WHERE("IDPAGOSJG = " + idPago);

        SQL subQuery2 = new SQL();
        subQuery2.SELECT("IDABONO");
        subQuery2.FROM("FAC_ABONO");
        subQuery2.WHERE("IDINSTITUCION = " + idInstitucion);
        subQuery2.WHERE("IDPAGOSJG = " + idPago);
        subQuery2.WHERE("FECHA = ( " + subQuery3.toString() + " )");

        SQL subQuery = new SQL();
        subQuery.SELECT("MAX(IDABONO)");
        subQuery.FROM("( " + subQuery2.toString() + " )");

        SQL query = new SQL();
        query.SELECT("*");
        query.FROM("FAC_ABONO");
        query.WHERE("IDINSTITUCION = " + idInstitucion);
        query.WHERE("IDABONO > ( " + subQuery.toString() + " )");
        query.WHERE("ROWNUM = 1");

        return query.toString();
    }

    public String getAbonoAnterior(Short idInstitucion, Date fecha) {

        SQL subQuery = new SQL();
        subQuery.SELECT("IDABONO");
        subQuery.FROM("FAC_ABONO");
        subQuery.WHERE("IDINSTITUCION = " + idInstitucion);
        subQuery.WHERE("FECHA < TO_DATE(" + fecha + ", 'YYYY-MM-DD HH:MI:SS')");
        subQuery.ORDER_BY("IDABONO DESC");

        SQL query = new SQL();
        query.SELECT("IDABONO");
        query.FROM("( " + subQuery.toString() + " )");
        query.WHERE("ROWNUM = 1");

        return query.toString();
    }
    
    public String getPagosCerrados(Short idInstitucion, String anio) {
    	
    	SQL sql = new SQL();
    	
    	sql.SELECT_DISTINCT("IDPAGOSJG");
    	sql.FROM("FAC_ABONO");
    	sql.WHERE("IDINSTITUCION = " + idInstitucion);
    	sql.WHERE("to_char(FECHA, 'YYYY') = "+ anio);
    	sql.WHERE("IDPAGOSJG is not null");
    	
    	return sql.toString();
    }

}
