package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.db.mappers.ScsSojSqlProvider;

public class ScsSojSqlExtendsProvider extends ScsSojSqlProvider {

	public String getAsuntoTipoSoj(AsuntosClaveJusticiableItem asuntoClave, String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("SOJ.IDINSTITUCION");
		sql.SELECT("concat('S' || SOJ.anio || '/',lpad(SOJ.NUMSOJ,5,'0') ) as asunto");
		sql.SELECT("SOJ.FECHAAPERTURA as fecha");
		sql.SELECT("SOJ.ANIO");
		sql.SELECT("SOJ.NUMERO");
		sql.SELECT("' ' AS turnoguardia");
		sql.SELECT("('<b>Tipo Consulta</b>: ' || RECTIPOCONSULTA.DESCRIPCION || '<br/> <b>Tipo Respuesta</b>: ' || RECTIPORESPUESTA.DESCRIPCION) as datosInteres");
		sql.SELECT("SOJ.idpersonajg IDPERSONASOJ");
		sql.SELECT("SOJ.IDPERSONA IDPERSONACOLEGIADO");

		sql.FROM("SCS_SOJ SOJ");
		sql.LEFT_OUTER_JOIN("scs_tiporespuesta TIPORESPUESTA ON SOJ.IDTIPORESPUESTA = TIPORESPUESTA.IDTIPORESPUESTA AND SOJ.IDINSTITUCION = TIPORESPUESTA.IDINSTITUCION");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS RECTIPORESPUESTA ON RECTIPORESPUESTA.IDRECURSO = TIPORESPUESTA.DESCRIPCION AND RECTIPORESPUESTA.IDLENGUAJE = '" + idLenguaje + "'");
		sql.LEFT_OUTER_JOIN("scs_tipoconsulta TIPOCONSULTA ON SOJ.IDTIPOCONSULTA = TIPOCONSULTA.IDTIPOCONSULTA AND SOJ.IDINSTITUCION = TIPOCONSULTA.IDINSTITUCION");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS RECTIPOCONSULTA ON RECTIPOCONSULTA.IDRECURSO = TIPOCONSULTA.DESCRIPCION AND RECTIPOCONSULTA.IDLENGUAJE = '" + idLenguaje + "'");
		sql.WHERE("SOJ.IDINSTITUCION = '" + asuntoClave.getIdInstitucion() + "'");
		sql.WHERE("SOJ.ANIO = '" + asuntoClave.getAnio() + "'");
		sql.WHERE("SOJ.NUMERO = '" + asuntoClave.getNumero() + "'");
		sql.WHERE("SOJ.IDTIPOSOJ = '" + asuntoClave.getClave() + "'");
		
		return sql.toString();
	}

}
