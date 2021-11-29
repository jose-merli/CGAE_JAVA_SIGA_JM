package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.db.mappers.ScsContrariosejgSqlProvider;

public class ScsContrariosejgSqlExtendsProvider extends ScsContrariosejgSqlProvider{

	public String busquedaListaContrariosEJG(EjgItem item, Short idInstitucion, Boolean historico) {

		SQL sql = new SQL();

		sql.SELECT("t1.numero");
		sql.SELECT("t1.idinstitucion");
		sql.SELECT("t1.anio");
		sql.SELECT("t1.nif");
		sql.SELECT("t1.idabogadocontrarioejg");
		sql.SELECT("t1.idprocurador");
		sql.SELECT("t1.IDINSTITUCION_PROCU");
		sql.SELECT("t1.idpersona");
		sql.SELECT("t1.idtipoejg");
		sql.SELECT("t1.fechabaja");
		sql.SELECT("t1.direccion");
		sql.SELECT("CASE\r\n" + "        WHEN t1.idabogadocontrarioejg IS NOT NULL THEN\r\n" 
				+ "            (\r\n"
				+ "                SELECT\r\n" 
				+ "                    col.ncolegiado\r\n"
				+ "                    || ', '\r\n" 
				+ "                    || t1.NOMBREABOGADOCONTRARIOejg\r\n"
				+ "--                    || per.apellidos1\r\n" + "--                    || ' '\r\n"
				+ "--                    || per.apellidos2\r\n" + "--                    || ', '\r\n"
				+ "--                    || per.nombre \r\n" + "                    AS abogado\r\n"
				+ "                FROM\r\n" + "                    cen_persona     per,\r\n"
				+ "                    cen_colegiado   col\r\n" + "                WHERE\r\n"
				+ "                    ( per.idpersona = col.idpersona\r\n"
				+ "                      AND per.idpersona = t1.idabogadocontrarioejg\r\n"
				+ "                      AND col.idinstitucion = t1.idinstitucion )\r\n" 
				+ "            )\r\n"
				+ "        ELSE\r\n" + "            ''\r\n" + "    END AS abogado");
		sql.SELECT("CASE\r\n"
				+ "        WHEN t1.idprocurador IS NOT NULL THEN\r\n" + "            (\r\n"
				+ "                SELECT\r\n" + "                    procu.ncolegiado\r\n"
				+ "                    || ', '\r\n" + "                    || procu.apellidos1\r\n"
				+ "                    || ' '\r\n" + "                    || procu.apellidos2\r\n"
				+ "                    || ', '\r\n" + "                    || procu.nombre AS procurador\r\n"
				+ "                FROM\r\n" + "                    scs_procurador procu\r\n"
				+ "                WHERE\r\n" + "                    ( idprocurador = t1.idprocurador\r\n"
				+ "                      AND idinstitucion = t1.IDINSTITUCION_PROCU )\r\n" 
				+ "            )\r\n"
				+ "        ELSE\r\n" + "            ''\r\n" + "    END AS procurador");
		sql.SELECT("t1.apellidosnombre");
		String from = "        (SELECT\r\n"
				+ "            scs_contrariosejg.numero,\r\n"
				+ "            scs_contrariosejg.idinstitucion,\r\n"
				+ "            scs_contrariosejg.anio,\r\n"
				+ "            scs_contrariosejg.idpersona,\r\n"
				+ "            scs_contrariosejg.idtipoejg,\r\n"
				+ "            scs_contrariosejg.idrepresentanteejg,\r\n"
				+ "            scs_contrariosejg.NOMBREABOGADOCONTRARIOejg,\r\n"
				+ "            scs_contrariosejg.fechabaja,\r\n" 
				+ "            persona.nif,\r\n"
				+ "            persona.apellido1\r\n"
				+ "            || decode(persona.apellido2, NULL, '', ' ' || persona.apellido2)\r\n"
				+ "            || ', '\r\n" + "            || persona.nombre AS apellidosnombre,\r\n"
				+ "            scs_contrariosejg.idabogadocontrarioejg,\r\n"
				+ "            scs_contrariosejg.idprocurador,\r\n"
				+ "            scs_contrariosejg.IDINSTITUCION_PROCU,\r\n" 
				+ "            persona.direccion\r\n" 
				+ "        FROM\r\n"
				+ "            scs_contrariosejg\r\n"
				+ "            JOIN scs_personajg persona ON persona.idpersona = scs_contrariosejg.idpersona\r\n"
				+ "                                          AND persona.idinstitucion = scs_contrariosejg.idinstitucion\r\n"
				+ "        WHERE\r\n" + "            ( scs_contrariosejg.anio = " + item.getAnnio() + "\r\n"
				+ "              AND scs_contrariosejg.numero = " + item.getNumero() + "\r\n"
				+ "              AND scs_contrariosejg.idinstitucion = " + idInstitucion + "\r\n"
				+ "              AND scs_contrariosejg.idtipoejg = " + item.getTipoEJG() + "\r\n";
		if (!historico) {
			from += " AND scs_contrariosejg.fechabaja is null \r\n";
		}
		from += "   )) t1\r\n";

		sql.FROM(from);

		return sql.toString();
	}
}
