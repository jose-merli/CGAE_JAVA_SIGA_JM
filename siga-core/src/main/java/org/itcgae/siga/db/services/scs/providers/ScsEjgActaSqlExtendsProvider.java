package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.ActasItem;
import org.itcgae.siga.db.mappers.ScsEjgActaSqlProvider;

public class ScsEjgActaSqlExtendsProvider extends ScsEjgActaSqlProvider{

	private Logger LOGGER = Logger.getLogger(ScsEjgActaSqlExtendsProvider.class);
	
	public String updateResolucion(ActasItem actasItem, Short idInstitucion) {

		SQL sql = new SQL();
		SQL update = new SQL();
		
		update.SELECT("EJG.IDTIPORATIFICACIONEJG, EJG.IDFUNDAMENTOJURIDICO");
		update.FROM("SCS_EJG EJG");
		update.WHERE("EJGACTA.IDINSTITUCIONEJG = EJG.IDINSTITUCION");
		update.WHERE("EJGACTA.IDTIPOEJG = EJG.IDTIPOEJG");
		update.WHERE("EJGACTA.ANIOEJG = EJG.ANIO");
		update.WHERE("EJGACTA.NUMEROEJG = EJG.NUMERO");
		
		sql.UPDATE("SCS_EJG_ACTA EJGACTA");
		sql.SET("(EJGACTA.IDTIPORATIFICACIONEJG, EJGACTA.IDFUNDAMENTOJURIDICO) = (" + update.toString() + ")");
		sql.WHERE("EJGACTA.ANIOACTA = " + actasItem.getAnioacta());
		sql.WHERE("EJGACTA.IDINSTITUCIONACTA = " + idInstitucion);
		sql.WHERE("EJGACTA.IDACTA = "+ actasItem.getIdacta());
		LOGGER.info("*******************updateResolucion********************" + sql.toString());
		return sql.toString();
	}
	
}
