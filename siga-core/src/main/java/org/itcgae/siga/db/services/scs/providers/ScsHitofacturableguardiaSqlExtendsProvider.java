package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.scs.BaremosGuardiaItem;
import org.itcgae.siga.db.mappers.ScsHitofacturableguardiaSqlProvider;

public class ScsHitofacturableguardiaSqlExtendsProvider extends ScsHitofacturableguardiaSqlProvider {

	public String getBaremos(String idGuardia, String idLenguaje) {
		SQL sql = new SQL();

//		sql.SELECT("GEN_RECURSOS_CATALOGOS.DESCRIPCION AS DESCRIPCION");
		sql.SELECT("SCS_HITOFACTURABLE.NOMBRE AS NOMBRE");
		sql.SELECT("SCS_HITOFACTURABLEGUARDIA.PRECIOHITO AS PRECIO");

		sql.FROM("SCS_HITOFACTURABLEGUARDIA");

		sql.JOIN(" SCS_HITOFACTURABLE ON SCS_HITOFACTURABLEGUARDIA.IDHITO = SCS_HITOFACTURABLE.IDHITO");
		sql.LEFT_OUTER_JOIN(
				"GEN_RECURSOS_CATALOGOS ON SCS_HITOFACTURABLE.DESCRIPCION = GEN_RECURSOS_CATALOGOS.IDRECURSO");

		sql.WHERE("SCS_HITOFACTURABLEGUARDIA.IDGUARDIA='" + idGuardia + "'");
		sql.WHERE("GEN_RECURSOS_CATALOGOS.IDLENGUAJE = '" + idLenguaje + "'");

		return sql.toString();
	}

	public String getCheckSeparacionGuardias(String idGuardia, String idTurno, String idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("DIASAPLICABLES");

		sql.FROM("SCS_HITOFACTURABLEGUARDIA");

		sql.WHERE("IDINSTITUCION ='" + idInstitucion + "'");
		sql.WHERE("IDTURNO = '" + idTurno + "'");
		sql.WHERE("IDGUARDIA  = '" + idGuardia + "'");
		sql.WHERE("NVL(AGRUPAR , 0) = 0");
		sql.WHERE("DIASAPLICABLES IS NOT NULL");

		sql.GROUP_BY("DIASAPLICABLES");
		return sql.toString();
	}

	public String getGuardiasByConf(String idInstitucion) {


		SQL sql = new SQL();

		sql.SELECT("f_siga_getrecurso( " 
				+ "        hito.descripcion, " 
				+ "        1 " 
				+ "    ) baremo, "
				+ "    hitoFac.preciohito, " 
				+ "    hitoFac.diasaplicables, " 
				+ "     CASE" + "			 WHEN nvl("
				+ "			hitoFac.agrupar," 
				+ "			0" 
				+ "			) = '1' THEN 'No'" 
				+ "			 ELSE 'Si'"
				+ "			END" 
				+ "			por_dia, " 
				+ "    hitoFac.idinstitucion, " 
				+ "    hitoFac.idturno, "
				+ "( select nombre from scs_turno where idturno = hitoFac.idturno and idinstitucion = hitoFac.idinstitucion) nomturno,"
				+ "    hitoFac.idguardia, "
				+ "( select nombre from SCS_GUARDIASTURNO where idguardia = hitoFac.idguardia and idinstitucion = hitoFac.idinstitucion) nomguardia," 
				+ "   hitoFac.idhito");

		sql.FROM("SCS_HITOFACTURABLEGUARDIA hitoFac, " + "    SCS_HITOFACTURABLE hito");

		sql.WHERE("hitoFac.IDINSTITUCION =" + idInstitucion);
//		sql.WHERE("hitoFac.IDTURNO IN( " + turnos + ")");
//		sql.WHERE("hitoFac.IDGUARDIA  IN( " + guardias + ")");
		sql.WHERE("hito.idhitoconfiguracion = hito.idhito");
		sql.WHERE("hito.idhito = hitoFac.idhito");

		return sql.toString();
	}

	public String getTurnoForGuardia(String idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("DISTINCT(t.IDTURNO) ," + "t.NOMBRE");

		sql.FROM("scs_guardiasturno g");
		sql.JOIN("scs_turno t ON t.idinstitucion = g.IDINSTITUCION AND g.idturno = t.idturno");

		sql.WHERE("g.IDINSTITUCION =" + idInstitucion);

		return sql.toString();
	}

	public String getTurnoGuarConf(String idInstitucion){
		SQL sql = new SQL();


		sql.SELECT("hitofac.idturno," +
				"    hitofac.idguardia," +
				"    t.nombre nomturno," +
				"    g.nombre nomguardia," +
				"    LISTAGG(" +
				"        hitofac.idhito," +
				"        '/'" +
				"    ) WITHIN GROUP(ORDER BY" +
				"        hitofac.idturno," +
				"        hitofac.idguardia," +
				"        t.nombre," +
				"        g.nombre" +
				"    ) \"hitos\"");

		sql.FROM("scs_hitofacturableguardia hitofac");


		sql.LEFT_OUTER_JOIN("scs_guardiasturno g ON " +
				"        g.idguardia = hitofac.idguardia" +
				"    AND " +
				"        g.idinstitucion = hitofac.idinstitucion");

		sql.LEFT_OUTER_JOIN(" scs_turno t ON " +
				"        t.idturno = hitofac.idturno" +
				"    AND " +
				"        t.idinstitucion = hitofac.idinstitucion");

		sql.WHERE("hitofac.IDINSTITUCION =" + idInstitucion);
		sql.GROUP_BY(" hitofac.idturno," +
				"    hitofac.idguardia," +
				"    t.nombre," +
				"    g.nombre");


		return sql.toString();
	}
	
	public String getAgruparGuardia(String idInstitucion, String idTurno, String idGuardia) {
		SQL sql = new SQL();
		
		sql.SELECT("MAX(AGRUPAR)");
		sql.FROM("SCS_HITOFACTURABLEGUARDIA sh");
		sql.WHERE("sh.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("sh.IDTURNO = '" + idTurno + "'");
		sql.WHERE("sh.IDGUARDIA = '" + idGuardia + "'");
		sql.WHERE("sh.AGRUPAR IS NOT NULL");
		
		return sql.toString();
	}

}
