package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsTipodictamenejgSqlProvider;

public class ScsTipodictamenejgSqlExtendsProvider extends ScsTipodictamenejgSqlProvider{

	public String comboDictamen(String idLenguaje, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("tipodictamen.IDTIPODICTAMENEJG");
		sql.SELECT("catalogoDictamen.DESCRIPCION");

		sql.FROM("SCS_TIPODICTAMENEJG tipodictamen");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS catalogoDictamen on catalogoDictamen.idrecurso = tipodictamen.DESCRIPCION and catalogoDictamen.idlenguaje ="+idLenguaje);
		sql.WHERE("tipodictamen.fecha_baja is  null and tipodictamen.idinstitucion = "+idInstitucion);
		
		
		return sql.toString();
	}
	
	/**
	 * 
	 * @param idLenguaje
	 * @param idInstitucion
	 * @return
	 */
	public String estadosDictamen(String idLenguaje, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("T.IDTIPODICTAMENEJG, F_SIGA_GETRECURSO(T.DESCRIPCION,"+idLenguaje+") DESCRIPCION");
		sql.FROM("SCS_TIPODICTAMENEJG T"); 
		sql.WHERE("T.IDINSTITUCION = "+idInstitucion+" AND T.FECHA_BAJA IS NULL AND T.BLOQUEADO = 'N'");  
		
		return sql.toString();
	}
}

