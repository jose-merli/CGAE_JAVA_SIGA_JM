/**
 * 
 */
package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsEstadoasistenciaSqlProvider;

/**
 * @author Dani
 *
 */
public class ScsEstadoAsistenciaSqlExtendsProvider extends ScsEstadoasistenciaSqlProvider {
	
	public String comboEstadosAsistencia(String idLenguaje) {
		
		SQL sql = new SQL();
		
		sql.SELECT("ea.idestadoasistencia",
				"F_SIGA_GETRECURSO(ea.descripcion,'"+idLenguaje+"') as descripcion");
		sql.FROM("scs_estadoasistencia ea");
		sql.WHERE("ea.fecha_baja is null");
		
		return sql.toString();
		
	}

}
