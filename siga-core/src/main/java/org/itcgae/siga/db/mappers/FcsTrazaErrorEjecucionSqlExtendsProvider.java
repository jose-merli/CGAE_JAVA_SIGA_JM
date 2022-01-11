package org.itcgae.siga.db.mappers;

import org.apache.ibatis.jdbc.SQL;

public class FcsTrazaErrorEjecucionSqlExtendsProvider {

    public String selectSQ() {
    	
        SQL sql = new SQL();
        sql.SELECT("SEQ_IDERROR.currval");
        sql.FROM("dual");
        
        return sql.toString();
    }
}
