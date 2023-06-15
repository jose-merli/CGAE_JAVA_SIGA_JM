package org.itcgae.siga.db.services.scs.providers;

import java.util.Arrays;
import java.util.List;

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
				"SA.IDGUARDIA",
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
				"(SELECT MAX(IDTURNO) FROM SCS_GUARDIASTURNO WHERE IDINSTITUCION = SA.IDINSTITUCION AND IDGUARDIA = SA.IDGUARDIA ) AS IDTURNO",
				"P.nombre || ' ' || P.apellidos1 || ' ' || p.apellidos2    AS nombreColegiado");
		sql2.FROM("SCS_SOLICITUD_ACEPTADA SA");
		sql2.LEFT_OUTER_JOIN("CV_LLAMADA LL ON SA.IDLLAMADA = LL.IDLLAMADA AND SA.IDINSTITUCION = LL.IDINSTITUCION");
		sql2.INNER_JOIN(
				"CEN_COLEGIADO C ON C.IDINSTITUCION = SA.IDINSTITUCION AND NVL(C.NCOLEGIADO, C.NCOMUNITARIO) = SA.NUMEROCOLEGIADO",
				"CEN_PERSONA P ON C.IDPERSONA = P.IDPERSONA");
		sql2.WHERE("SA.IDINSTITUCION ="+idInstitucion);
		if(!UtilidadesString.esCadenaVacia(preAsistencia.getnAvisoCentralita())) {
			sql2.WHERE("LL.NUMAVISOCV = '"+preAsistencia.getnAvisoCentralita()+"'");
		}
		if(!UtilidadesString.esCadenaVacia(preAsistencia.getEstado())) {
			sql2.WHERE("SA.ESTADO IN ("+preAsistencia.getEstado()+")");
		}
		if(!UtilidadesString.esCadenaVacia(preAsistencia.getFechaLlamadaDsd())) {
			sql2.WHERE("SA.FECHALLAMADA >= TO_DATE('"+preAsistencia.getFechaLlamadaDsd()+"','DD/MM/YYYY HH24:MI')");
		}
		if(!UtilidadesString.esCadenaVacia(preAsistencia.getFechaLlamadaHasta())) {
			sql2.WHERE("SA.FECHALLAMADA <= TO_DATE('"+preAsistencia.getFechaLlamadaHasta()+"','DD/MM/YYYY HH24:MI')");
		}
		if(UtilidadesString.esCadenaVacia(preAsistencia.getIdGuardia())
				&& !UtilidadesString.esCadenaVacia(preAsistencia.getIdTurno())) {
			sql2.WHERE("SA.IDGUARDIA  IN (select G.IDGUARDIA FROM SCS_GUARDIASTURNO G WHERE  G.IDINSTITUCION = '"+idInstitucion+"' AND G.IDTURNO IN( "+preAsistencia.getIdTurno()+"))");
		}
		if(!UtilidadesString.esCadenaVacia(preAsistencia.getIdGuardia())) {
			sql2.WHERE("SA.IDGUARDIA IN ("+preAsistencia.getIdGuardia()+")");
		}
		if(!UtilidadesString.esCadenaVacia(preAsistencia.getIdTipoCentroDetencion())) {
			if("10".equals(preAsistencia.getIdTipoCentroDetencion())) {
				String[] arrayComisariaString =  preAsistencia.getIdComisaria().split(",");
				List<String> listaComisariasString = Arrays.asList(arrayComisariaString);
				String str ="";
				for(int i=0; i< listaComisariasString.size() ; i++) {
					if(i == 0) {
						str += "'"+listaComisariasString.get(i) +"' ";
					}else {
						str += ",'" + listaComisariasString.get(i) +"'";
					}
				}
				sql2.WHERE("SA.IDCENTRODETENCION IN ("+str+")");
			}else if("20".equals(preAsistencia.getIdTipoCentroDetencion())) {
				String[] arrayJuzgadoString =  preAsistencia.getIdJuzgado().split(",");
				List<String> listaJuzgadoString = Arrays.asList(arrayJuzgadoString);
				String str = "";
				for(int i=0; i< listaJuzgadoString.size() ; i++) {
					if(i == 0) {
						str += "'"+listaJuzgadoString.get(i) +"' ";
					}else {
						str += ",'" + listaJuzgadoString.get(i) +"'";
					}
				}
				sql2.WHERE("SA.IDCENTRODETENCION IN ("+str+")");
			}
			sql2.WHERE("SA.IDTIPOCENTRODETENCION IN ("+preAsistencia.getIdTipoCentroDetencion()+")");
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
