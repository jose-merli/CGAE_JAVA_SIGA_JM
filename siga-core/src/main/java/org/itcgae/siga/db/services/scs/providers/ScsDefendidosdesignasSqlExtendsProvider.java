package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.db.mappers.ScsDefendidosdesignaSqlProvider;

public class ScsDefendidosdesignasSqlExtendsProvider extends ScsDefendidosdesignaSqlProvider {

	public String getInteresadosDesigna(AsuntosClaveJusticiableItem asuntoClave, String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("LISTAGG((PERSONA.APELLIDO1  || ' ' || PERSONA.APELLIDO2 || ', ' || PERSONA.NOMBRE), '; ') WITHIN GROUP (ORDER BY DEFENDIDOSDESIGNA.idinstitucion, DEFENDIDOSDESIGNA.anio, DEFENDIDOSDESIGNA.numero, DEFENDIDOSDESIGNA.idturno desc) AS INTERESADOS");
		sql.FROM("scs_defendidosdesigna DEFENDIDOSDESIGNA");
		sql.LEFT_OUTER_JOIN("SCS_PERSONAJG PERSONA ON DEFENDIDOSDESIGNA.IDPERSONA = PERSONA.IDPERSONA  AND DEFENDIDOSDESIGNA.IDINSTITUCION = PERSONA.IDINSTITUCION");
		sql.WHERE("DEFENDIDOSDESIGNA.idinstitucion = '" + asuntoClave.getIdInstitucion() + "'");
		sql.WHERE("DEFENDIDOSDESIGNA.idturno  = '" + asuntoClave.getClave() + "'");
		sql.WHERE("DEFENDIDOSDESIGNA.anio   = '" + asuntoClave.getAnio() + "'");
		sql.WHERE("DEFENDIDOSDESIGNA.numero   = '" + asuntoClave.getNumero() + "'");
		
		return sql.toString();
	}

}
