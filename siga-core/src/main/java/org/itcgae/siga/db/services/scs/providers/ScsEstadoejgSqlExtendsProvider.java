package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.db.mappers.ScsEstadoejgSqlProvider;

public class ScsEstadoejgSqlExtendsProvider extends ScsEstadoejgSqlProvider {

	public String getEstadoEjg(AsuntosClaveJusticiableItem asuntoClave, String idLenguaje) {

		SQL sql = new SQL();
		SQL sql2 = new SQL();

		sql2.SELECT("E.IDESTADOEJG");
		sql2.SELECT("REC.DESCRIPCION");
		sql2.SELECT("E.FECHAINICIO");

		sql2.FROM("SCS_ESTADOEJG E");
		sql2.INNER_JOIN("SCS_MAESTROESTADOSEJG MAESTROESTADO ON MAESTROESTADO.IDESTADOEJG = E.IDESTADOEJG");
		sql2.INNER_JOIN("GEN_RECURSOS_CATALOGOS REC ON REC.IDRECURSO = MAESTROESTADO.DESCRIPCION AND IDLENGUAJE = '"
				+ idLenguaje + "'");

		sql2.WHERE("E.IDINSTITUCION = '" + asuntoClave.getIdInstitucion() + "'");
		sql2.WHERE("E.IDTIPOEJG = '" + asuntoClave.getClave() + "'");
		sql2.WHERE("E.ANIO  = '" + asuntoClave.getAnio() + "'");
		sql2.WHERE("E.NUMERO  = '" + asuntoClave.getNumero() + "'");
		sql2.WHERE("E.FECHABAJA IS NULL");

		sql2.ORDER_BY("TRUNC(E.FECHAINICIO) DESC, E.IDESTADOPOREJG DESC");

		sql.SELECT("*");

		sql.FROM("(" + sql2 + ")");

		sql.WHERE("ROWNUM = 1");

		return sql.toString();
	}

	public String comboEstadoEjg(Short idLenguaje) {
		SQL sql = new SQL();

		sql.SELECT("estado.IDESTADOEJG");
		sql.SELECT("cat.descripcion");
		sql.FROM("SCS_MAESTROESTADOSEJG estado");
		sql.INNER_JOIN("gen_recursos_catalogos cat on cat.IDRECURSO = estado.descripcion and cat.idlenguaje = '"
				+ idLenguaje + "'");
		sql.WHERE("estado.fecha_baja is null");
		sql.ORDER_BY("cat.descripcion");
		return sql.toString();
	}


	public String getEstados(EjgItem ejgItem, String idInstitucion, String idLenguaje) {
		SQL sql = new SQL();

		sql.SELECT("estado.fechainicio," + " estado.fechamodificacion," + " estado.idestadoejg,"
				+ " recursos.descripcion," + " estado.observaciones," + " estado.automatico,"
				+ " maestro.visiblecomision," + " persona.nombre," + " persona.apellidos1," + " persona.apellidos2");
		sql.SELECT("persona.apellidos1 || ' ' || persona.apellidos2 || ', ' || persona.nombre AS usuariomod");

		sql.FROM("scs_estadoejg estado");
		sql.INNER_JOIN("scs_maestroestadosejg maestro on (estado.idestadoejg=maestro.idestadoejg)");
		sql.INNER_JOIN("gen_recursos_catalogos recursos on (maestro.descripcion=recursos.idrecurso)");
		sql.INNER_JOIN("cen_persona persona on (estado.usumodificacion=persona.idpersona)");

		if (ejgItem.getAnnio() != null && ejgItem.getAnnio() != "")
			sql.WHERE("estado.anio = '" + ejgItem.getAnnio() + "'");
		if (ejgItem.getNumero() != null && ejgItem.getNumero() != "")
			sql.WHERE("estado.numero = '" + ejgItem.getNumero() + "'");
		if (ejgItem.getTipoEJG() != null && ejgItem.getTipoEJG() != "")
			sql.WHERE("estado.idtipoejg = '" + ejgItem.getTipoEJG() + "'");
		sql.WHERE("recursos.idlenguaje = '" + idLenguaje + "'");
		sql.WHERE("estado.idinstitucion = '" + idInstitucion + "'");
		if (!ejgItem.isHistorico())
			sql.WHERE("estado.fechabaja is null");

		sql.ORDER_BY("estado.fechainicio desc");
		return sql.toString();
	}

}
