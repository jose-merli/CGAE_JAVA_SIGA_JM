package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacPlantillafacturacionSqlProvider;

public class FacPlantillafacturacionSqlExtendsProvider extends FacPlantillafacturacionSqlProvider {

    public String getPlantillaSerieFacturacion(String institucion, String serieFacturacion) {

        SQL sql = new SQL();
        sql.SELECT("PF.IDINSTITUCION");
        sql.SELECT("PF.IDPLANTILLA");
        sql.SELECT("PF.FECHAMODIFICACION");
        sql.SELECT("PF.USUMODIFICACION");
        sql.SELECT("PF.DESCRIPCION");
        sql.SELECT("PF.PLANTILLAPDF");
        sql.FROM("FAC_PLANTILLAFACTURACION PF");
        sql.INNER_JOIN("FAC_SERIEFACTURACION SF ON PF.IDINSTITUCION = SF.IDINSTITUCION AND PF.IDPLANTILLA = SF.IDPLANTILLA");
        sql.WHERE("SF.IDSERIEFACTURACION = " + serieFacturacion);
        sql.WHERE("SF.IDINSTITUCION = " + institucion);

        return sql.toString();
    }

}
