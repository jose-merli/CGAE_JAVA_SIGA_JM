package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.TiposActuacionItem;
import org.itcgae.siga.db.mappers.ScsTipoactuacionSqlProvider;

public class ScsTipoactuacionAsistenciaSqlExtendsProvider extends ScsTipoactuacionSqlProvider {

	
	public String selectTiposActuacionasistencia(TiposActuacionItem tiposActuacionItem, String idtiposasistencia) {
		SQL sql = new SQL();
		
		sql.SELECT("idinstitucion");
		sql.SELECT("idtipoactuacion");
		sql.SELECT("idtipoasistencia");
		
		sql.WHERE("idinstitucion = '" + tiposActuacionItem.getIdinstitucion() + "'");
		sql.WHERE("idtipoactuacion = '" + tiposActuacionItem.getIdtipoactuacion() + "'");
		sql.WHERE("idtipoasistencia = '" + idtiposasistencia + "'");
		sql.WHERE("rownum = 1");
		
		sql.FROM("scs_actuacionasistencia");
		return sql.toString();
	}

}
