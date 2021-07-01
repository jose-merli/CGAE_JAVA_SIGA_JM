package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.PreAsistenciaItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.ScsSolicitudAceptadaSqlProvider;

public class ScsSolicitudAceptadaSqlExtendsProvider extends ScsSolicitudAceptadaSqlProvider {
	
	public String searchPreasistencias(PreAsistenciaItem preAsistencia, Integer tamMax, Short idInstitucion){
		
		SQL sql = new SQL();
		SQL sql2 = new SQL();
		sql2.SELECT("SA.IDSOLICITUD",
				"SA.IDLLAMADA",
				"TO_CHAR(SA.FECHALLAMADA,'DD/MM/YYYY HH24:MI') as fechaLlamada",
				"SA.IDINSTITUCION",
				"SA.NUMEROCOLEGIADO",
				"SA.IDGUARDIA || ' - ' || SA.NOMBREGUARDIA AS descripcionGuardia",
				"SA.FECHAGUARDIA",
				"SA.ESTADO",
				"CASE SA.ESTADO "
				+ "				       WHEN  0 THEN 'PENDIENTE'"
				+ "				       WHEN  1 THEN 'CONFIRMADA'"
				+ "				       ELSE 'DENEGADA'"
				+ "				       END"
				+ "				       DESCRIPCIONESTADO",
				"SA.IDCENTRODETENCION ||' - '||SA.NOMBRECENTRODETENCION AS centroDetencion",
				"SA.IDTIPOCENTRODETENCION",
				"SA.FECHAMODIFICACION",
				"SA.USUMODIFICACION",
				"TO_CHAR(SA.FECHARECEPCION,'DD/MM/YYYY HH24:MI') as fechaRecepcion",
				"LL.NUMAVISOCV",
				"P.nombre || ' ' || P.apellidos1 || ' ' || p.apellidos2    AS nombreColegiado");
		sql2.FROM("SCS_SOLICITUD_ACEPTADA SA");
		sql2.INNER_JOIN("CV_LLAMADA LL ON SA.IDLLAMADA = LL.IDLLAMADA AND SA.IDINSTITUCION = LL.IDINSTITUCION",
				"CEN_COLEGIADO C ON C.IDINSTITUCION = SA.IDINSTITUCION AND NVL(C.NCOLEGIADO, C.NCOMUNITARIO) = SA.NUMEROCOLEGIADO",
				"CEN_PERSONA P ON C.IDPERSONA = P.IDPERSONA");
		sql2.WHERE("SA.IDINSTITUCION ="+idInstitucion);
		if(!UtilidadesString.esCadenaVacia(preAsistencia.getnAvisoCentralita())) {
			sql2.WHERE("LL.NUMAVISOCV = '"+preAsistencia.getnAvisoCentralita()+"'");
		}
		if(!UtilidadesString.esCadenaVacia(preAsistencia.getEstado())) {
			sql2.WHERE("SA.ESTADO = '"+preAsistencia.getEstado()+"'");
		}
		if(!UtilidadesString.esCadenaVacia(preAsistencia.getFechaLlamadaDsd())) {
			sql2.WHERE("SA.FECHALLAMADA >= TO_DATE('"+preAsistencia.getFechaLlamadaDsd()+"','DD/MM/YYYY HH24:MI')");
		}
		if(!UtilidadesString.esCadenaVacia(preAsistencia.getFechaLlamadaHasta())) {
			sql2.WHERE("SA.FECHALLAMADA <= TO_DATE('"+preAsistencia.getFechaLlamadaHasta()+"','DD/MM/YYYY HH24:MI')");
		}
		if(!UtilidadesString.esCadenaVacia(preAsistencia.getIdGuardia())
				&& !UtilidadesString.esCadenaVacia(preAsistencia.getIdTurno())) {
			sql2.WHERE("SA.IDGUARDIA  IN (select G.IDGUARDIA FROM SCS_GUARDIASTURNO G WHERE  G.IDINSTITUCION = '"+idInstitucion+"' AND G.IDTURNO ='"+preAsistencia.getIdTurno()+"')");
		}
		if(!UtilidadesString.esCadenaVacia(preAsistencia.getIdGuardia())) {
			sql2.WHERE("SA.IDGUARDIA = '"+preAsistencia.getIdGuardia()+"'");
		}
		if(!UtilidadesString.esCadenaVacia(preAsistencia.getIdTipoCentroDetencion())) {
			if("10".equals(preAsistencia.getIdTipoCentroDetencion())) {
				sql2.WHERE("SA.IDCENTRODETENCION = '"+preAsistencia.getIdComisaria()+"'");
			}else if("20".equals(preAsistencia.getIdTipoCentroDetencion())) {
				sql2.WHERE("SA.IDCENTRODETENCION = '"+preAsistencia.getIdJuzgado()+"'");
			}
			sql2.WHERE("SA.IDTIPOCENTRODETENCION = '"+preAsistencia.getIdTipoCentroDetencion()+"'");
		}
		if(!UtilidadesString.esCadenaVacia(preAsistencia.getNumeroColegiado())){
			sql2.WHERE("SA.NUMEROCOLEGIADO = '"+preAsistencia.getNumeroColegiado()+"'");
		}
		sql2.ORDER_BY("IDLLAMADA");
		
		
		sql.SELECT("*");
		sql.FROM("( " + sql2.toString() + " )");

		if (tamMax != null) {
			Integer tamMaxNumber = tamMax + 1;
			sql.WHERE("ROWNUM <= " + tamMaxNumber);
		}
		return sql.toString();
		
	}

}
