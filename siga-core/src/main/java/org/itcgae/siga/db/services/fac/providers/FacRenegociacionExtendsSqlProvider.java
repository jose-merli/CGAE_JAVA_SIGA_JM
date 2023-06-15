package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.FacturaItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.FacFacturaSqlProvider;
import org.itcgae.siga.db.mappers.FacRenegociacionSqlProvider;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;


public class FacRenegociacionExtendsSqlProvider extends FacRenegociacionSqlProvider {
    
    public String getNuevoID(Short idInstitucion, String idFactura) {

        SQL sql = new SQL();
        sql.SELECT("(NVL(MAX(IDRENEGOCIACION) + 1, 1)) AS IDRENEGOCIACION");
        sql.FROM("FAC_RENEGOCIACION");
        sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
        sql.WHERE("IDFACTURA = '" + idFactura + "'");

        return sql.toString();
    }

}