package org.itcgae.siga.db.services.cen.providers;

import java.awt.List;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CenDireccionTipodireccionSqlProvider;

public class CenDireccionTipodireccionSqlExtendsProvider extends CenDireccionTipodireccionSqlProvider {

	public String select(String idPersona, String idInstitucion, String[] idTipoDireccion, String idDireccion) {
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("tipoDir.IDINSTITUCION");
		sql.SELECT("tipoDir.IDPERSONA");
		sql.SELECT("tipoDir.IDDIRECCION");
		sql.SELECT("tipoDir.IDTIPODIRECCION");
		sql.SELECT("tipoDir.FECHAMODIFICACION");
		sql.SELECT("tipoDir.USUMODIFICACION");
		sql.FROM("CEN_DIRECCION_TIPODIRECCION tipoDir");
		sql.INNER_JOIN("CEN_DIRECCIONES dir on dir.idPersona = tipoDir.idPersona and dir.idDireccion = tipoDir.idDireccion and dir.idInstitucion = tipoDir.idInstitucion");
		
		if(idDireccion != "") {
			sql.WHERE("tipoDir.idPersona = "+ idPersona +" and tipoDir.idInstitucion =" +idInstitucion + " and tipoDir.idTipoDireccion IN ( " + String.join(",", idTipoDireccion) + " ) and dir.fechaBaja is null and tipoDir.idDireccion != "+ idDireccion);
		}else {
			sql.WHERE("tipoDir.idPersona = "+ idPersona +" and tipoDir.idInstitucion =" +idInstitucion + " and tipoDir.idTipoDireccion IN ( " + String.join(",", idTipoDireccion) + " ) and dir.fechaBaja is null");
		}
		
		return sql.toString();
	}
}
