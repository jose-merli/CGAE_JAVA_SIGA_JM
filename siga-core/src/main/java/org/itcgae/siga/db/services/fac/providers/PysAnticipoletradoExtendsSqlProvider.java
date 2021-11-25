package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;

public class PysAnticipoletradoExtendsSqlProvider {
	
	 public String selectMaxIdAnticipo(Short idInstitucion, Long idPersona) {
	    	SQL query = new SQL();
	    	
	    	query.SELECT("nvl(MAX(idAnticipo), 0) as id");
	    	
	    	query.FROM("PYS_ANTICIPOLETRADO anticipos");
	    	
	    	query.WHERE("idpersona = "+idPersona);
	    	query.WHERE("idInstitucion = "+idInstitucion);
	    	
	    	return query.toString();
	    	
	    }

}
