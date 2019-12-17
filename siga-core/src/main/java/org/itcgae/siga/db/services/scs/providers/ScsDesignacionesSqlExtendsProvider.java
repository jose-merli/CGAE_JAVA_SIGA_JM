package org.itcgae.siga.db.services.scs.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.ScsDesignaSqlProvider;

public class ScsDesignacionesSqlExtendsProvider extends ScsDesignaSqlProvider {

	public String searchClaveDesignaciones(AsuntosJusticiableItem asuntosJusticiableItem, Integer tamMax) {
		SQL sql = new SQL();
		SQL sqlOrder = new SQL();

		sqlOrder.SELECT("*");
		sql.SELECT(
				"DESIGNA.idinstitucion, DESIGNA.anio,DESIGNA.numero,to_char(DESIGNA.idturno)  as clave, '' as rol, 'D' as tipo");
		sql.FROM("SCS_DESIGNA DESIGNA");
		// El Join con la tabla de scs_personaJG, solo realizará si nos viene informado
		// alguno de los datos del solicitante(Nif, nombre o apellidos).
		if (asuntosJusticiableItem.getIdPersonaColegiado() != null) {
			sql.INNER_JOIN(
					"SCS_DESIGNASLETRADO DESIGNALETRADO ON DESIGNALETRADO.idinstitucion = DESIGNA.idinstitucion  and DESIGNALETRADO.idturno = DESIGNA.idturno\r\n"
							+ "  and DESIGNALETRADO.anio = DESIGNA.anio and DESIGNALETRADO.numero = DESIGNA.numero");
		}
		sql.INNER_JOIN(
				"scs_defendidosdesigna DEFENDIDOSDESIGNA ON DEFENDIDOSDESIGNA.idinstitucion = DESIGNA.idinstitucion  and DEFENDIDOSDESIGNA.idturno = DESIGNA.idturno\r\n"
						+ "                            and DEFENDIDOSDESIGNA.anio = DESIGNA.anio and DEFENDIDOSDESIGNA.numero = DESIGNA.numero");
		sql.LEFT_OUTER_JOIN(
				"SCS_PERSONAJG PERSONA ON  DEFENDIDOSDESIGNA.IDPERSONA = PERSONA.IDPERSONA  AND DEFENDIDOSDESIGNA.IDINSTITUCION = PERSONA.IDINSTITUCION");
		sql.WHERE("DESIGNA.idinstitucion =" + asuntosJusticiableItem.getIdInstitucion());

		if (asuntosJusticiableItem.getAnio() != null && asuntosJusticiableItem.getAnio() != "") {
			sql.WHERE("DESIGNA.ANIO = " + asuntosJusticiableItem.getAnio());
		}
		if (asuntosJusticiableItem.getNumero() != null) {
			sql.WHERE("DESIGNA.NUMERO = " + asuntosJusticiableItem.getNumero());
		}
		if (asuntosJusticiableItem.getIdTurno() != null) {
			sql.WHERE("DESIGNA.IDTURNO = " + asuntosJusticiableItem.getIdTurno());
		}
		if (asuntosJusticiableItem.getIdGuardia() != null) {
			sql.WHERE("DESIGNA.IDGUARDIA = " + asuntosJusticiableItem.getIdGuardia());
		}
		if (asuntosJusticiableItem.getIdPersonaColegiado() != null) {
			sql.WHERE("DESIGNALETRADO.IDPERSONA = " + asuntosJusticiableItem.getIdPersonaColegiado());
		}
		if (asuntosJusticiableItem.getFechaAperturaDesde() != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String fecha = dateFormat.format(asuntosJusticiableItem.getFechaAperturaDesde());
			sql.WHERE("TO_CHAR(EJG.FECHAAPERTURA,'DD/MM/RRRR') >= TO_DATE('" + fecha + "','DD/MM/RRRR')");
		}
		if (asuntosJusticiableItem.getFechaAperturaHasta() != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String fecha = dateFormat.format(asuntosJusticiableItem.getFechaAperturaHasta());
			sql.WHERE("TO_CHAR(EJG.FECHAAPERTURA,'DD/MM/RRRR') <= TO_DATE('" + fecha + "','DD/MM/RRRR')");
		}

		if (asuntosJusticiableItem.getIdPersonaColegiado() != null) {
			if (asuntosJusticiableItem.getApellidos() != null)
				sql.WHERE(UtilidadesString.filtroTextoBusquedas("REPLACE(CONCAT(apellido1,apellido2), ' ', '')",
						asuntosJusticiableItem.getApellidos().replaceAll("\\s+", "")));
			if (asuntosJusticiableItem.getNombre() != null)
				sql.WHERE(UtilidadesString.filtroTextoBusquedas("NOMBRE", asuntosJusticiableItem.getNombre()));

		}
		if (asuntosJusticiableItem.getIdPersonaColegiado() != null) {

		sql.WHERE("( DESIGNALETRADO.fecharenuncia is null or\r\n" + "           DESIGNALETRADO.Fechadesigna =\r\n"
				+ "           (SELECT MAX(LET2.Fechadesigna)\r\n" + "               FROM SCS_DESIGNASLETRADO LET2\r\n"
				+ "              WHERE DESIGNALETRADO.IDINSTITUCION = LET2.IDINSTITUCION\r\n"
				+ "                AND DESIGNALETRADO.IDTURNO = LET2.IDTURNO\r\n"
				+ "                AND DESIGNALETRADO.ANIO = LET2.ANIO\r\n"
				+ "                AND DESIGNALETRADO.NUMERO = LET2.NUMERO\r\n"
				+ "                AND TRUNC(LET2.Fechadesigna) <= TRUNC(SYSDATE)))\r\n" + "");
		}
		if (asuntosJusticiableItem.getNif() != null && asuntosJusticiableItem.getNif() != "") {
            sql.WHERE("upper(PERSONA.NIF) like upper('%"+asuntosJusticiableItem.getNif()+"%')");
		}
		if (asuntosJusticiableItem.getIdTipoDesigna() != null) {
			sql.WHERE("DESIGNA.IDTIPODESIGNACOLEGIO = " + asuntosJusticiableItem.getIdTipoDesigna());
		}
		if (asuntosJusticiableItem.getIdEstadoDesigna() != null) {
			sql.WHERE("DESIGNA.ESTADO = '" + asuntosJusticiableItem.getIdEstadoDesigna()+"'");
		}
		if (asuntosJusticiableItem.getNumProcedimiento() != null && asuntosJusticiableItem.getNumProcedimiento() != "") {
			sql.WHERE("DESIGNA.NUMPROCEDIMIENTO   = '" + asuntosJusticiableItem.getNumProcedimiento()+"'");
		}
		// if(asuntosJusticiableItem.getNumeroDiligencia() != null) {
		// sql.WHERE("ASISTENCIA.NUMERODILIGENCIA =
		// "+asuntosJusticiableItem.getNumeroDiligencia());
		// }
		if (asuntosJusticiableItem.getNig() != null && asuntosJusticiableItem.getNig() != "") {
			sql.WHERE("DESIGNA.NIG   = '" + asuntosJusticiableItem.getNig()+"'");
		}
		if (asuntosJusticiableItem.getIdJuzgado() != null) {
			sql.WHERE("DESIGNA.IDJUZGADO  = " + asuntosJusticiableItem.getIdJuzgado());
		}

		sqlOrder.FROM("(" + sql + " )");
		if (tamMax != null) {
			Integer tamMaxNumber = tamMax + 1;
			sqlOrder.WHERE("rownum <= " + tamMaxNumber);
		}

		return sqlOrder.toString();
		// return sql.toString();
	}

}
