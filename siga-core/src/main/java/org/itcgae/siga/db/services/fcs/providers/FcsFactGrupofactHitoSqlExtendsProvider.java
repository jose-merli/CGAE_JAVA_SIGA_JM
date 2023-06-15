package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FcsFactGrupofactHitoSqlProvider;

public class FcsFactGrupofactHitoSqlExtendsProvider extends FcsFactGrupofactHitoSqlProvider {

    public String comboConceptosPago(String idFacturacion, Short idInstitucion, String idioma) {

        SQL sql = new SQL();

        sql.SELECT("DISTINCT F_SIGA_GETRECURSO(HITO.DESCRIPCION , " + idioma + ") AS DESCRIPCION");
        sql.SELECT("FACHITO.IDHITOGENERAL");

        sql.FROM("FCS_FACT_GRUPOFACT_HITO FACHITO");
        sql.JOIN("FCS_HITOGENERAL HITO ON HITO.IDHITOGENERAL = FACHITO.IDHITOGENERAL");

        sql.WHERE("FACHITO.IDINSTITUCION = '" + idInstitucion + "'");
        sql.WHERE("FACHITO.IDFACTURACION = '" + idFacturacion + "'");

        sql.ORDER_BY("FACHITO.IDHITOGENERAL");

        return sql.toString();
    }

}
