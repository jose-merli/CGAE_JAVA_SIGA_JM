package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsTipodictamenejgSqlProvider;

public class ScsTipodictamenejgSqlExtendsProvider extends ScsTipodictamenejgSqlProvider{

	public String comboDictamen(String idLenguaje, String idInstitucion) {
		SQL sql = new SQL();
		SQL sqlfinal = new SQL();
		
		sql.SELECT("tipodictamen.IDTIPODICTAMENEJG");
		sql.SELECT("f_siga_getrecurso(tipodictamen.descripcion,"+idLenguaje+") DESCRIPCION");
		sql.SELECT("tipodictamen.BLOQUEADO");
		sql.SELECT("tipodictamen.FECHA_BAJA");

		sql.FROM("SCS_TIPODICTAMENEJG tipodictamen");
		//sql.WHERE("tipodictamen.fecha_baja is null");
		//sql.WHERE("tipodictamen.bloqueado = 'N'");
		sql.WHERE("tipodictamen.idinstitucion = "+idInstitucion);
		
		sqlfinal.SELECT("*");
		sqlfinal.FROM("(" + sql.toString() + ") AS consulta");
		sqlfinal.ORDER_BY("consulta.DESCRIPCION");
		return sqlfinal.toString();
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

