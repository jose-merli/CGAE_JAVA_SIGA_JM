package org.itcgae.siga.db.services.age.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.AgeNotificacioneseventoSqlProvider;

public class AgeNotificacioneseventoSqlExtendsProvider extends AgeNotificacioneseventoSqlProvider {

	public String getCalendarNotifications(String idCalendario, String idInstitucion, String idLenguaje) {
		SQL sql = new SQL();

		sql.SELECT_DISTINCT("noti.idevento");
		sql.SELECT("plantilla.nombre as nombreplantilla");
		sql.SELECT("plantilla.idplantillaenvios as idplantilla");
		sql.SELECT("noti.idcalendario");
		sql.SELECT("rec.descripcion as tipoenvio");
		sql.SELECT("tipo.IDTIPOENVIOS as idtipoenvio");
		sql.SELECT("noti.IDTIPONOTIFICACIONEVENTO");
		sql.SELECT("uni.IDUNIDADMEDIDA");
		sql.SELECT("noti.cuando || ' ' || rec4.DESCRIPCION || ' ' || rec2.DESCRIPCION as descripcioncuando");
		sql.SELECT("noti.cuando");
		sql.SELECT("noti.IDTIPOCUANDO");
		sql.SELECT("rec3.DESCRIPCION as nombretiponotificacion");
		sql.SELECT("noti.IDNOTIFICACIONEVENTO");
		sql.FROM("AGE_NOTIFICACIONESEVENTO noti");
		sql.INNER_JOIN(
				"ENV_PLANTILLASENVIOS plantilla on (noti.idplantilla = plantilla.idplantillaenvios and noti.idinstitucion = plantilla.idinstitucion)");
		sql.INNER_JOIN(
				"ENV_TIPOENVIOS tipo on (plantilla.IDTIPOENVIOS = tipo.IDTIPOENVIOS) and noti.IDTIPOENVIOS = plantilla.IDTIPOENVIOS");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS rec ON (rec.IDRECURSO = tipo.NOMBRE AND rec.IDLENGUAJE = '"+idLenguaje+"') ");
		sql.INNER_JOIN("AGE_UNIDADMEDIDA uni on (uni.IDUNIDADMEDIDA = noti.IDUNIDADMEDIDA)");
		sql.INNER_JOIN("AGE_TIPOCUANDO tipocuando on (noti.IDTIPOCUANDO = tipocuando.IDTIPOCUANDO)");
		sql.INNER_JOIN(
				"AGE_TIPONOTIFICACIONEVENTO  tiponotificacion ON (tiponotificacion.IDTIPONOTIFICACIONEVENTO = noti.IDTIPONOTIFICACIONEVENTO)");
		sql.INNER_JOIN(
				"GEN_RECURSOS_CATALOGOS rec2 ON (rec2.IDRECURSO = tipocuando.DESCRIPCION AND rec2.IDLENGUAJE = '"+idLenguaje+"')");
		sql.INNER_JOIN(
				"GEN_RECURSOS_CATALOGOS rec3 ON (rec3.IDRECURSO = tiponotificacion.DESCRIPCION AND rec3.IDLENGUAJE = '"+idLenguaje+"')");
		sql.INNER_JOIN(
				"GEN_RECURSOS_CATALOGOS rec4 ON (rec4.IDRECURSO = uni.DESCRIPCION AND rec4.IDLENGUAJE = '"+idLenguaje+"')");
		sql.WHERE("noti.FECHABAJA is NULL");
		sql.WHERE("noti.idinstitucion = '" + idInstitucion + "'");
		sql.WHERE("noti.idcalendario = '" + idCalendario + "'");

		return sql.toString();
	}
	
	public String getHistoricCalendarNotifications(String idCalendario, String idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT_DISTINCT("noti.idevento");
		sql.SELECT("plantilla.nombre as nombreplantilla");
		sql.SELECT("plantilla.idplantillaenvios as idplantilla");
		sql.SELECT("noti.idcalendario");
		sql.SELECT("rec.descripcion as tipoenvio");
		sql.SELECT("tipo.IDTIPOENVIOS as idtipoenvio");
		sql.SELECT("noti.IDTIPONOTIFICACIONEVENTO");
		sql.SELECT("uni.IDUNIDADMEDIDA");
		sql.SELECT("noti.cuando || ' ' || uni.DESCRIPCION || ' ' || rec2.DESCRIPCION as descripcioncuando");
		sql.SELECT("noti.cuando");
		sql.SELECT("noti.IDTIPOCUANDO");
		sql.SELECT("tiponotificacion.DESCRIPCION as nombretiponotificacion");
		sql.SELECT("noti.IDNOTIFICACIONEVENTO");
		sql.SELECT("noti.fechabaja");
		sql.FROM("AGE_NOTIFICACIONESEVENTO noti");
		sql.INNER_JOIN(
				"ENV_PLANTILLASENVIOS plantilla on (noti.idplantilla = plantilla.idplantillaenvios and noti.idinstitucion = plantilla.idinstitucion)");
		sql.INNER_JOIN(
				"ENV_TIPOENVIOS tipo on (plantilla.IDTIPOENVIOS = tipo.IDTIPOENVIOS) and noti.IDTIPOENVIOS = plantilla.IDTIPOENVIOS");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS rec ON (rec.IDRECURSO = tipo.NOMBRE AND rec.IDLENGUAJE = '1') ");
		sql.INNER_JOIN("AGE_UNIDADMEDIDA uni on (uni.IDUNIDADMEDIDA = noti.IDUNIDADMEDIDA)");
		sql.INNER_JOIN("AGE_TIPOCUANDO tipocuando on (noti.IDTIPOCUANDO = tipocuando.IDTIPOCUANDO)");
		sql.INNER_JOIN(
				"AGE_TIPONOTIFICACIONEVENTO  tiponotificacion ON (tiponotificacion.IDTIPONOTIFICACIONEVENTO = noti.IDTIPONOTIFICACIONEVENTO)");
		sql.INNER_JOIN(
				"GEN_RECURSOS_CATALOGOS rec2 ON (rec2.IDRECURSO = tipocuando.DESCRIPCION AND rec2.IDLENGUAJE = '1')");
		sql.WHERE("noti.idinstitucion = '" + idInstitucion + "'");
		sql.WHERE("noti.idcalendario = '" + idCalendario + "'");
		sql.ORDER_BY("noti.fechabaja");

		return sql.toString();
	}
	
	public String getEventNotifications(String idEvento, String idInstitucion, String idLenguaje) {
		SQL sql = new SQL();

		sql.SELECT_DISTINCT("noti.idevento");
		sql.SELECT("plantilla.nombre as nombreplantilla");
		sql.SELECT("plantilla.idplantillaenvios as idplantilla");
		sql.SELECT("noti.idcalendario");
		sql.SELECT("rec.descripcion as tipoenvio");
		sql.SELECT("tipo.IDTIPOENVIOS as idtipoenvio");
		sql.SELECT("noti.IDTIPONOTIFICACIONEVENTO");
		sql.SELECT("uni.IDUNIDADMEDIDA");
		sql.SELECT("noti.cuando || ' ' || rec4.DESCRIPCION || ' ' || rec2.DESCRIPCION as descripcioncuando");
		sql.SELECT("noti.cuando");
		sql.SELECT("noti.IDTIPOCUANDO");
		sql.SELECT("rec3.DESCRIPCION as nombretiponotificacion");
		sql.SELECT("noti.IDNOTIFICACIONEVENTO");
		sql.FROM("AGE_NOTIFICACIONESEVENTO noti");
		sql.INNER_JOIN(
				"ENV_PLANTILLASENVIOS plantilla on (noti.idplantilla = plantilla.idplantillaenvios and noti.idinstitucion = plantilla.idinstitucion)");
		sql.INNER_JOIN(
				"ENV_TIPOENVIOS tipo on (plantilla.IDTIPOENVIOS = tipo.IDTIPOENVIOS) and noti.IDTIPOENVIOS = plantilla.IDTIPOENVIOS");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS rec ON (rec.IDRECURSO = tipo.NOMBRE AND rec.IDLENGUAJE = '"+idLenguaje+"') ");
		sql.INNER_JOIN("AGE_UNIDADMEDIDA uni on (uni.IDUNIDADMEDIDA = noti.IDUNIDADMEDIDA)");
		sql.INNER_JOIN("AGE_TIPOCUANDO tipocuando on (noti.IDTIPOCUANDO = tipocuando.IDTIPOCUANDO)");
		sql.INNER_JOIN(
				"AGE_TIPONOTIFICACIONEVENTO  tiponotificacion ON (tiponotificacion.IDTIPONOTIFICACIONEVENTO = noti.IDTIPONOTIFICACIONEVENTO)");
		sql.INNER_JOIN(
				"GEN_RECURSOS_CATALOGOS rec2 ON (rec2.IDRECURSO = tipocuando.DESCRIPCION AND rec2.IDLENGUAJE = '"+idLenguaje+"')");
		sql.INNER_JOIN(
				"GEN_RECURSOS_CATALOGOS rec3 ON (rec3.IDRECURSO = tiponotificacion.DESCRIPCION AND rec3.IDLENGUAJE = '"+idLenguaje+"')");
		sql.INNER_JOIN(
				"GEN_RECURSOS_CATALOGOS rec4 ON (rec4.IDRECURSO = uni.DESCRIPCION AND rec4.IDLENGUAJE = '"+idLenguaje+"')");
		sql.WHERE("noti.FECHABAJA is NULL");
		sql.WHERE("noti.idinstitucion = '" + idInstitucion + "'");
		sql.WHERE("noti.idevento = '" + idEvento + "'");
		sql.WHERE("noti.idcalendario is NULL");

		return sql.toString();
	}
	
	public String getHistoricEventNotifications(String idEvento, String idInstitucion, String idLenguaje) {
		SQL sql = new SQL();

		sql.SELECT_DISTINCT("noti.idevento");
		sql.SELECT("plantilla.nombre as nombreplantilla");
		sql.SELECT("plantilla.idplantillaenvios as idplantilla");
		sql.SELECT("noti.idcalendario");
		sql.SELECT("rec.descripcion as tipoenvio");
		sql.SELECT("tipo.IDTIPOENVIOS as idtipoenvio");
		sql.SELECT("noti.IDTIPONOTIFICACIONEVENTO");
		sql.SELECT("uni.IDUNIDADMEDIDA");
		sql.SELECT("noti.cuando || ' ' || rec4.DESCRIPCION || ' ' || rec2.DESCRIPCION as descripcioncuando");
		sql.SELECT("noti.cuando");
		sql.SELECT("noti.IDTIPOCUANDO");
		sql.SELECT("rec3.DESCRIPCION as nombretiponotificacion");
		sql.SELECT("noti.IDNOTIFICACIONEVENTO");
		sql.SELECT("noti.fechabaja");
		sql.FROM("AGE_NOTIFICACIONESEVENTO noti");
		sql.INNER_JOIN(
				"ENV_PLANTILLASENVIOS plantilla on (noti.idplantilla = plantilla.idplantillaenvios and noti.idinstitucion = plantilla.idinstitucion)");
		sql.INNER_JOIN(
				"ENV_TIPOENVIOS tipo on (plantilla.IDTIPOENVIOS = tipo.IDTIPOENVIOS) and noti.IDTIPOENVIOS = plantilla.IDTIPOENVIOS");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS rec ON (rec.IDRECURSO = tipo.NOMBRE AND rec.IDLENGUAJE = '"+idLenguaje+"') ");
		sql.INNER_JOIN("AGE_UNIDADMEDIDA uni on (uni.IDUNIDADMEDIDA = noti.IDUNIDADMEDIDA)");
		sql.INNER_JOIN("AGE_TIPOCUANDO tipocuando on (noti.IDTIPOCUANDO = tipocuando.IDTIPOCUANDO)");
		sql.INNER_JOIN(
				"AGE_TIPONOTIFICACIONEVENTO  tiponotificacion ON (tiponotificacion.IDTIPONOTIFICACIONEVENTO = noti.IDTIPONOTIFICACIONEVENTO)");
		sql.INNER_JOIN(
				"GEN_RECURSOS_CATALOGOS rec2 ON (rec2.IDRECURSO = tipocuando.DESCRIPCION AND rec2.IDLENGUAJE = '"+idLenguaje+"')");
		sql.INNER_JOIN(
				"GEN_RECURSOS_CATALOGOS rec3 ON (rec3.IDRECURSO = tiponotificacion.DESCRIPCION AND rec3.IDLENGUAJE = '"+idLenguaje+"')");
		sql.INNER_JOIN(
				"GEN_RECURSOS_CATALOGOS rec4 ON (rec4.IDRECURSO = uni.DESCRIPCION AND rec4.IDLENGUAJE = '"+idLenguaje+"')");
		sql.WHERE("noti.idinstitucion = '" + idInstitucion + "'");
		sql.WHERE("noti.idevento = '" + idEvento + "'");
		sql.WHERE("noti.idcalendario is NULL");

		return sql.toString();
	}
	
}
