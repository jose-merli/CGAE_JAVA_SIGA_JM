package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.db.mappers.ScsAsistenciaSqlProvider;

public class ScsAsistenciaSqlExtendsProvider extends ScsAsistenciaSqlProvider {

	public String getAsuntoTipoAsistencia(AsuntosClaveJusticiableItem asuntoClave, String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("ASISTENCIA.IDINSTITUCION");
		sql.SELECT("concat('A' || ASISTENCIA.anio || '/',lpad(ASISTENCIA.NUMERO,5,'0') ) as asunto");
		sql.SELECT("ASISTENCIA.FECHAHORA as fecha");
		sql.SELECT("ASISTENCIA.ANIO");
		sql.SELECT("ASISTENCIA.NUMERO");
		sql.SELECT("ASISTENCIA.codigo");
		sql.SELECT("GUARDIA.nombre as turnoguardia");
		sql.SELECT("('<b>Centro de Detención</b>: ' || COMISARIA.NOMBRE || '<br/> <b>Comisaría</b>: ' || COMISARIA.NOMBRE) as datosinteres");
		sql.SELECT("ASISTENCIA.IDTURNO");
		sql.SELECT("ASISTENCIA.idpersonajg idpersonaasistido");
		sql.SELECT("ASISTENCIA.IDPERSONACOLEGIADO");

		sql.FROM("SCS_ASISTENCIA ASISTENCIA");

		sql.LEFT_OUTER_JOIN("SCS_GUARDIASTURNO GUARDIA ON ASISTENCIA.IDGUARDIA = GUARDIA.IDGUARDIA AND ASISTENCIA.IDTURNO = GUARDIA.IDTURNO");
		sql.LEFT_OUTER_JOIN("SCS_COMISARIA COMISARIA ON COMISARIA.IDCOMISARIA = ASISTENCIA.COMISARIA AND COMISARIA.IDINSTITUCION = ASISTENCIA.IDINSTITUCION");
		
		sql.WHERE("ASISTENCIA.IDINSTITUCION = '" + asuntoClave.getIdInstitucion() + "'");
		sql.WHERE("ASISTENCIA.ANIO = '" + asuntoClave.getAnio() + "'");
		sql.WHERE("ASISTENCIA.NUMERO = '" + asuntoClave.getNumero() + "'");
		
		if(asuntoClave.getClave() != null) {
			sql.WHERE("ASISTENCIA.idturno = '" + asuntoClave.getClave() + "'");
		}
		
		return sql.toString();
	}

}
