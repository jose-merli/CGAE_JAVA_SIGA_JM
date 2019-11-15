package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.scs.ProcuradorItem;
import org.itcgae.siga.db.mappers.ScsProcuradorSqlProvider;

public class ScsProcuradorSqlExtendsProvider extends ScsProcuradorSqlProvider {

	public String searchProcuradores(ProcuradorItem procuradorItem, Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("procurador.idinstitucion");
		sql.SELECT("procurador.idprocurador");
		sql.SELECT("procurador.nombre");
		sql.SELECT("procurador.apellidos1");
		sql.SELECT("procurador.apellidos2");
		sql.SELECT("procurador.domicilio");
		sql.SELECT("procurador.codigopostal");
		sql.SELECT("procurador.idpoblacion");
		sql.SELECT("procurador.idprovincia");
		sql.SELECT("procurador.telefono1");
		sql.SELECT("procurador.fax1");
		sql.SELECT_DISTINCT("concat(concat(procurador.apellidos1 || ' ', concat(procurador.apellidos2 , ', ')), procurador.nombre || ' ') AS nombreape");
		sql.SELECT("procurador.telefono2");
		sql.SELECT("procurador.fechabaja");
		sql.SELECT("procurador.ncolegiado");
		sql.SELECT("procurador.idcolprocurador");
		sql.SELECT("procurador.codigo");
		sql.SELECT("procurador.email");
		sql.SELECT("POBLACION.NOMBRE AS NOMBREPOBLACION");
		sql.SELECT("PROVINCIAS.NOMBRE AS NOMBREPROVINCIA");

		sql.FROM("SCS_PROCURADOR procurador");
		sql.LEFT_OUTER_JOIN("CEN_PROVINCIAS PROVINCIAS ON PROVINCIAS.IDPROVINCIA = procurador.IDPROVINCIA");
		sql.LEFT_OUTER_JOIN("CEN_POBLACIONES POBLACION ON POBLACION.IDPOBLACION = procurador.IDPOBLACION");
		if(idInstitucion != 2000) {
			sql.WHERE("idinstitucion = '" + idInstitucion + "'");
		}

		if (procuradorItem.getNombre() != null && procuradorItem.getNombre() != "") {
			sql.AND();
			sql.WHERE("(TRANSLATE(LOWER( PROCURADOR.NOMBRE),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('%"
					+ procuradorItem.getNombre().trim() + "%'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN'))");
			
		}

		if (procuradorItem.getApellido1() != null && procuradorItem.getApellido1() != "") {
			sql.AND();
			sql.WHERE("((TRANSLATE(LOWER( PROCURADOR.APELLIDOS1),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('%"
					+ procuradorItem.getApellido1().trim() + "%'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN'))");
			sql.OR();
			sql.WHERE("(TRANSLATE(LOWER( PROCURADOR.APELLIDOS2),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('%"
					+ procuradorItem.getApellido1().trim() + "%'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')))");
		}
		if (procuradorItem.getCodigoExt() != null && procuradorItem.getCodigoExt() != "") {
			sql.AND();
			sql.WHERE("UPPER(procurador.codigo) = UPPER('" + procuradorItem.getCodigoExt() + "')");
		}

		if (!procuradorItem.getHistorico()) {
			sql.AND();

			sql.WHERE("fechabaja is null");
		}

		sql.ORDER_BY("procurador.nombre");

		return sql.toString();
	}

	public String getIdProcurador(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDPROCURADOR) AS IDPROCURADOR");
		sql.FROM("SCS_PROCURADOR");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}

}
