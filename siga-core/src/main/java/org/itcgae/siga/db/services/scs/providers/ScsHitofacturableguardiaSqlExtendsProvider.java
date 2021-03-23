package org.itcgae.siga.db.services.scs.providers;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsHitofacturableguardiaSqlProvider;

public class ScsHitofacturableguardiaSqlExtendsProvider extends ScsHitofacturableguardiaSqlProvider {
	
	public String getBaremos(String idGuardia, String idLenguaje) {
		SQL sql = new SQL();
		
//		sql.SELECT("GEN_RECURSOS_CATALOGOS.DESCRIPCION AS DESCRIPCION");
		sql.SELECT("SCS_HITOFACTURABLE.NOMBRE AS NOMBRE");
		sql.SELECT("SCS_HITOFACTURABLEGUARDIA.PRECIOHITO AS PRECIO");
		
		
		sql.FROM("SCS_HITOFACTURABLEGUARDIA");

		sql.JOIN(" SCS_HITOFACTURABLE ON SCS_HITOFACTURABLEGUARDIA.IDHITO = SCS_HITOFACTURABLE.IDHITO");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS ON SCS_HITOFACTURABLE.DESCRIPCION = GEN_RECURSOS_CATALOGOS.IDRECURSO");
		
		sql.WHERE("SCS_HITOFACTURABLEGUARDIA.IDGUARDIA='"+idGuardia+"'");
		sql.WHERE("GEN_RECURSOS_CATALOGOS.IDLENGUAJE = '"+idLenguaje+"'");
		
		return sql.toString();
	}
	
	public String getCheckSeparacionGuardias(String idGuardia, String idTurno, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("DIASAPLICABLES");
		
		sql.FROM("SCS_HITOFACTURABLEGUARDIA");

		sql.WHERE("IDINSTITUCION ='"+idInstitucion+"'");
		sql.WHERE("IDTURNO = '"+idTurno+"'");
		sql.WHERE("IDGUARDIA  = '"+idGuardia+"'");
		sql.WHERE("NVL(AGRUPAR , 0) = 0");
		sql.WHERE("DIASAPLICABLES IS NOT NULL");
		
		sql.GROUP_BY("DIASAPLICABLES");
		return sql.toString();
	}

}
