package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.itcgae.siga.db.mappers.PcajgAlcActErrorCamSqlProvider;

public class FcsPcajgAlcActErrorCamExtendsProvider extends PcajgAlcActErrorCamSqlProvider{
    private Logger LOGGER = Logger.getLogger(this.getClass());

	public String buscarErroresCAM(String listaIdFacturacion, Short idInstitucion, Integer tamMaximo) {

		SQL sql1 = new SQL();
		sql1.SELECT("CODIGO_ERROR,ERROR_DESCRIPCION,IDFACTURACION");
		sql1.FROM("V_PCAJG_ALC_ACT_ERROR_CAM");
		sql1.WHERE("IDFACTURACION IN ("+listaIdFacturacion+")");
		sql1.WHERE("IDINSTITUCION = " + idInstitucion);
		
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("( " + sql1.toString() + " )");

		if (tamMaximo != null) {
			Integer tamMaxNumber = tamMaximo + 1;
			sql.WHERE("ROWNUM <= " + tamMaxNumber);
		}
		
		LOGGER.info(sql.toString());
		return sql.toString();
	}
}
