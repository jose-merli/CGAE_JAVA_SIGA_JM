package org.itcgae.siga.db.services.scs.providers;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsHitofacturableguardiaSqlProvider;

public class ScsHitofacturableguardiaSqlExtendsProvider extends ScsHitofacturableguardiaSqlProvider {
	
	public String getBaremos(String idGuardia, String idLenguaje) {
		SQL sql = new SQL();
		
		sql.SELECT("GEN_RECURSOS_CATALOGOS.DESCRIPCION AS DESCRIPCION");
		sql.SELECT("SCS_HITOFACTURABLEGUARDIA.PRECIOHITO AS PRECIO");
		
		sql.FROM("SCS_HITOFACTURABLEGUARDIA");

		sql.JOIN(" SCS_HITOFACTURABLE ON SCS_HITOFACTURABLEGUARDIA.IDHITO = SCS_HITOFACTURABLE.IDHITO");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS ON SCS_HITOFACTURABLE.DESCRIPCION = GEN_RECURSOS_CATALOGOS.IDRECURSO");
		
		sql.WHERE("SCS_HITOFACTURABLEGUARDIA.IDGUARDIA='"+idGuardia+"'");
		sql.WHERE("GEN_RECURSOS_CATALOGOS.IDLENGUAJE = '"+idLenguaje+"'");
		
		return sql.toString();
	}

}
