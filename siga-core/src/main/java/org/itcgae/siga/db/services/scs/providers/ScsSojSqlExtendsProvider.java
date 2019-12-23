package org.itcgae.siga.db.services.scs.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.ScsSojSqlProvider;

public class ScsSojSqlExtendsProvider extends ScsSojSqlProvider {

	public String searchClaveSoj(AsuntosJusticiableItem asuntosJusticiableItem, Integer tamMax) {
		SQL sql = new SQL();
		SQL sqlOrder = new SQL();

		sqlOrder.SELECT("*");
		sql.SELECT("SOJ.idinstitucion, SOJ.anio,SOJ.numero,to_char(SOJ.IDTIPOSOJ) AS clave, '' as rol, 'S' as tipo");
		sql.FROM("SCS_SOJ SOJ");
		// El Join con la tabla de scs_personaJG, solo realizará si nos viene informado
		// alguno de los datos del solicitante(Nif, nombre o apellidos).
		if (asuntosJusticiableItem.getNombre() != null || asuntosJusticiableItem.getApellidos() != null
				|| asuntosJusticiableItem.getNif() != null) {
			sql.INNER_JOIN(
					"SCS_PERSONAJG PERSONA ON (SOJ.IDPERSONAJG = PERSONA.IDPERSONA AND PERSONA.IDINSTITUCION = SOJ.IDINSTITUCION)");
		}
		sql.WHERE("SOJ.idinstitucion =" + asuntosJusticiableItem.getIdInstitucion());

		if (asuntosJusticiableItem.getAnio() != null && asuntosJusticiableItem.getAnio() != "") {
			sql.WHERE("SOJ.ANIO = " + asuntosJusticiableItem.getAnio());
		}
		if (asuntosJusticiableItem.getNumero() != null) {
			sql.WHERE("SOJ.NUMERO = " + asuntosJusticiableItem.getNumero());
		}
		if (asuntosJusticiableItem.getIdTurno() != null) {
			sql.WHERE("SOJ.IDTURNO = " + asuntosJusticiableItem.getIdTurno());
		}
		if (asuntosJusticiableItem.getIdGuardia() != null) {
			sql.WHERE("SOJ.IDGUARDIA = " + asuntosJusticiableItem.getIdGuardia());
		}

		if (asuntosJusticiableItem.getIdPersonaColegiado() != null) {
			sql.WHERE("SOJ.IDPERSONA = " + asuntosJusticiableItem.getIdPersonaColegiado());
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

		if (asuntosJusticiableItem.getNif() != null  && asuntosJusticiableItem.getNif() != "") {
            sql.WHERE("upper(PERSONA.NIF) like upper('%"+asuntosJusticiableItem.getNif()+"%')");
		}

		if ((asuntosJusticiableItem.getNombre() != null  && asuntosJusticiableItem.getNombre() != "")|| (asuntosJusticiableItem.getApellidos() != null && asuntosJusticiableItem.getApellidos() != "")
				|| (asuntosJusticiableItem.getNif() != null  && asuntosJusticiableItem.getNif() != "")) {
			if (asuntosJusticiableItem.getApellidos() != null)
				sql.WHERE(UtilidadesString.filtroTextoBusquedas("REPLACE(CONCAT(apellido1,apellido2), ' ', '')",
						asuntosJusticiableItem.getApellidos().replaceAll("\\s+", "")));
			if (asuntosJusticiableItem.getNombre() != null)
				sql.WHERE(UtilidadesString.filtroTextoBusquedas("NOMBRE", asuntosJusticiableItem.getNombre()));
		}

		if (asuntosJusticiableItem.getIdTipoSoj() != null) {
			sql.WHERE("SOJ.IDTIPOSOJ = " + asuntosJusticiableItem.getIdTipoSoj());
		}

		sqlOrder.FROM("(" + sql + " )");
		if (tamMax != null) {
			Integer tamMaxNumber = tamMax + 1;
			sqlOrder.WHERE("rownum <= " + tamMaxNumber);
		}
		return sqlOrder.toString();
		// return sql.toString();
	}

	public String getAsuntoTipoSoj(AsuntosClaveJusticiableItem asuntoClave, String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("SOJ.IDINSTITUCION");
		sql.SELECT("concat('S' || SOJ.anio || '/',lpad(SOJ.NUMSOJ,5,'0') ) as asunto");
		sql.SELECT("SOJ.FECHAAPERTURA as fecha");
		sql.SELECT("SOJ.ANIO");
		sql.SELECT("SOJ.NUMERO");
		sql.SELECT("' ' AS turnoguardia");
		sql.SELECT(
				"('<b>Tipo Consulta</b>: ' || RECTIPOCONSULTA.DESCRIPCION || '<br/> <b>Tipo Respuesta</b>: ' || RECTIPORESPUESTA.DESCRIPCION) as datosInteres");
		sql.SELECT("SOJ.idpersonajg IDPERSONASOJ");
		sql.SELECT("SOJ.IDPERSONA IDPERSONACOLEGIADO");

		sql.FROM("SCS_SOJ SOJ");
		sql.LEFT_OUTER_JOIN(
				"scs_tiporespuesta TIPORESPUESTA ON SOJ.IDTIPORESPUESTA = TIPORESPUESTA.IDTIPORESPUESTA AND SOJ.IDINSTITUCION = TIPORESPUESTA.IDINSTITUCION");
		sql.LEFT_OUTER_JOIN(
				"GEN_RECURSOS_CATALOGOS RECTIPORESPUESTA ON RECTIPORESPUESTA.IDRECURSO = TIPORESPUESTA.DESCRIPCION AND RECTIPORESPUESTA.IDLENGUAJE = '"
						+ idLenguaje + "'");
		sql.LEFT_OUTER_JOIN(
				"scs_tipoconsulta TIPOCONSULTA ON SOJ.IDTIPOCONSULTA = TIPOCONSULTA.IDTIPOCONSULTA AND SOJ.IDINSTITUCION = TIPOCONSULTA.IDINSTITUCION");
		sql.LEFT_OUTER_JOIN(
				"GEN_RECURSOS_CATALOGOS RECTIPOCONSULTA ON RECTIPOCONSULTA.IDRECURSO = TIPOCONSULTA.DESCRIPCION AND RECTIPOCONSULTA.IDLENGUAJE = '"
						+ idLenguaje + "'");
		sql.WHERE("SOJ.IDINSTITUCION = '" + asuntoClave.getIdInstitucion() + "'");
		sql.WHERE("SOJ.ANIO = '" + asuntoClave.getAnio() + "'");
		sql.WHERE("SOJ.NUMERO = '" + asuntoClave.getNumero() + "'");
		sql.WHERE("SOJ.IDTIPOSOJ = '" + asuntoClave.getClave() + "'");

		return sql.toString();
	}

}
