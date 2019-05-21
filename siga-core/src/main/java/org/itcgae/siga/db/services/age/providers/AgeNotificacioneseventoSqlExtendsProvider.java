package org.itcgae.siga.db.services.age.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.commons.utils.SolModifSQLUtils;
import org.itcgae.siga.commons.utils.UtilidadesString;
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
		sql.SELECT("rec4.DESCRIPCION as DESCRIPCIONMEDIDA");
		sql.SELECT("rec2.DESCRIPCION as DESCRIPCIONANTES");
		sql.FROM("AGE_NOTIFICACIONESEVENTO noti");
		sql.LEFT_OUTER_JOIN(
				"ENV_PLANTILLASENVIOS plantilla on (noti.idplantilla = plantilla.idplantillaenvios and noti.idinstitucion = plantilla.idinstitucion and noti.IDTIPOENVIOS = plantilla.IDTIPOENVIOS)");
		sql.LEFT_OUTER_JOIN(
				"ENV_TIPOENVIOS tipo on (plantilla.IDTIPOENVIOS = tipo.IDTIPOENVIOS)");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS rec ON (rec.IDRECURSO = tipo.NOMBRE AND rec.IDLENGUAJE = '"+idLenguaje+"') ");
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
		sql.ORDER_BY("noti.IDTIPONOTIFICACIONEVENTO");


		return sql.toString();
	}
	
	public String getHistoricCalendarNotifications(String idCalendario, String idInstitucion, String idLenguaje) {
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
		sql.SELECT("noti.IDNOTIFICACIONEVENTO");
		sql.SELECT("noti.fechabaja");
		sql.SELECT("rec3.DESCRIPCION as nombretiponotificacion");
		sql.SELECT("rec4.DESCRIPCION as DESCRIPCIONMEDIDA");
		sql.SELECT("rec2.DESCRIPCION as DESCRIPCIONANTES");
		sql.FROM("AGE_NOTIFICACIONESEVENTO noti");
		sql.LEFT_OUTER_JOIN(
				"ENV_PLANTILLASENVIOS plantilla on (noti.idplantilla = plantilla.idplantillaenvios and noti.idinstitucion = plantilla.idinstitucion and noti.IDTIPOENVIOS = plantilla.IDTIPOENVIOS)");
		sql.LEFT_OUTER_JOIN(
				"ENV_TIPOENVIOS tipo on (plantilla.IDTIPOENVIOS = tipo.IDTIPOENVIOS)");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS rec ON (rec.IDRECURSO = tipo.NOMBRE AND rec.IDLENGUAJE = '" +idLenguaje + "') ");
		sql.INNER_JOIN("AGE_UNIDADMEDIDA uni on (uni.IDUNIDADMEDIDA = noti.IDUNIDADMEDIDA)");
		sql.INNER_JOIN("AGE_TIPOCUANDO tipocuando on (noti.IDTIPOCUANDO = tipocuando.IDTIPOCUANDO)");
		sql.INNER_JOIN(
				"AGE_TIPONOTIFICACIONEVENTO  tiponotificacion ON (tiponotificacion.IDTIPONOTIFICACIONEVENTO = noti.IDTIPONOTIFICACIONEVENTO)");
		sql.INNER_JOIN(
				"GEN_RECURSOS_CATALOGOS rec2 ON (rec2.IDRECURSO = tipocuando.DESCRIPCION AND rec2.IDLENGUAJE = '" +idLenguaje + "')");
		sql.INNER_JOIN(
				"GEN_RECURSOS_CATALOGOS rec3 ON (rec3.IDRECURSO = tiponotificacion.DESCRIPCION AND rec3.IDLENGUAJE = '"+idLenguaje+"')");
		sql.INNER_JOIN(
				"GEN_RECURSOS_CATALOGOS rec4 ON (rec4.IDRECURSO = uni.DESCRIPCION AND rec4.IDLENGUAJE = '"+idLenguaje+"')");
	
		sql.WHERE("noti.idinstitucion = '" + idInstitucion + "'");
		sql.WHERE("noti.idcalendario = '" + idCalendario + "'");
		sql.ORDER_BY("noti.IDTIPONOTIFICACIONEVENTO");

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
		sql.SELECT("rec4.DESCRIPCION as DESCRIPCIONMEDIDA");
		sql.SELECT("rec2.DESCRIPCION as DESCRIPCIONANTES");
		sql.FROM("AGE_NOTIFICACIONESEVENTO noti");
		sql.LEFT_OUTER_JOIN(
				"ENV_PLANTILLASENVIOS plantilla on (noti.idplantilla = plantilla.idplantillaenvios and noti.idinstitucion = plantilla.idinstitucion and noti.IDTIPOENVIOS = plantilla.IDTIPOENVIOS)");
		sql.LEFT_OUTER_JOIN(
				"ENV_TIPOENVIOS tipo on (plantilla.IDTIPOENVIOS = tipo.IDTIPOENVIOS)");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS rec ON (rec.IDRECURSO = tipo.NOMBRE AND rec.IDLENGUAJE = '"+idLenguaje+"') ");
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
		sql.ORDER_BY("noti.IDTIPONOTIFICACIONEVENTO");

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
		sql.SELECT("rec4.DESCRIPCION as DESCRIPCIONMEDIDA");
		sql.SELECT("rec2.DESCRIPCION as DESCRIPCIONANTES");
		sql.FROM("AGE_NOTIFICACIONESEVENTO noti");
		sql.LEFT_OUTER_JOIN(
				"ENV_PLANTILLASENVIOS plantilla on (noti.idplantilla = plantilla.idplantillaenvios and noti.idinstitucion = plantilla.idinstitucion and noti.IDTIPOENVIOS = plantilla.IDTIPOENVIOS)");
		sql.LEFT_OUTER_JOIN(
				"ENV_TIPOENVIOS tipo on (plantilla.IDTIPOENVIOS = tipo.IDTIPOENVIOS)");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS rec ON (rec.IDRECURSO = tipo.NOMBRE AND rec.IDLENGUAJE = '"+idLenguaje+"') ");
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
		sql.ORDER_BY("noti.IDTIPONOTIFICACIONEVENTO");

		return sql.toString();
	}
	
	
	public String selectDestinatariosSesion(Long idEvento,Long idCurso,Short idInstitucion, String idEnvio, String usumodificacion) {
		String rdo = "SELECT * FROM ("
				+ getInscritosCurso(idCurso,idInstitucion, idEnvio, usumodificacion)
				+ " ) UNION ( "
				+ getFormadoresSesion(idEvento,idInstitucion, idEnvio, usumodificacion)
				+ " ) ";
		return rdo;
	}
	
	public String selectDestinatariosCurso(Long idCurso,Short idInstitucion, String idEnvio, String usumodificacion) {
		String rdo = "SELECT * FROM ("
				+ getInscritosCurso(idCurso,idInstitucion, idEnvio, usumodificacion)
				+ " ) UNION ( "
				+ getFormadoresCurso(idCurso,idInstitucion, idEnvio, usumodificacion)
				+ " ) ";
		return rdo;
	}
	
	public String selectDestinatariosInscripcion(Long idCurso,Short idInstitucion, String idEnvio, String usumodificacion) {

		String rdo = "SELECT * FROM ("
				+ getInteresadosTemasCurso(idCurso,idInstitucion, idEnvio, usumodificacion)
				+ " ) UNION ( "
				+ getFormadoresCurso(idCurso,idInstitucion, idEnvio, usumodificacion)
				+ " ) ";
		return rdo;
		
	}
	
	private String getFormadoresCurso(Long idCurso, Short idInstitucion, String idEnvio, String usumodificacion) {

		SQL consultaDatosInscripcion = new SQL();

		
		
		consultaDatosInscripcion.SELECT( idEnvio + " AS IDENVIO");
		consultaDatosInscripcion.SELECT("FORMADOR.IDPERSONA");
		consultaDatosInscripcion.SELECT("CURSO.IDINSTITUCION");
		consultaDatosInscripcion.SELECT("SYSDATE AS FECHAMODIFICACION");
		consultaDatosInscripcion.SELECT("'1' AS USUMODIFICACION");
		consultaDatosInscripcion.SELECT("DIRECCIONES.DOMICILIO");
		consultaDatosInscripcion.SELECT("DIRECCIONES.CODIGOPOSTAL");
		consultaDatosInscripcion.SELECT("DIRECCIONES.FAX1");
		consultaDatosInscripcion.SELECT("DIRECCIONES.FAX2");
		consultaDatosInscripcion.SELECT("DIRECCIONES.CORREOELECTRONICO");
		consultaDatosInscripcion.SELECT("DIRECCIONES.IDPAIS");
		consultaDatosInscripcion.SELECT("DIRECCIONES.IDPROVINCIA");
		consultaDatosInscripcion.SELECT("DIRECCIONES.IDPOBLACION");
		consultaDatosInscripcion.SELECT("PERSONA.NOMBRE");
		consultaDatosInscripcion.SELECT("PERSONA.APELLIDOS1");
		consultaDatosInscripcion.SELECT("PERSONA.APELLIDOS2");
		consultaDatosInscripcion.SELECT("PERSONA.NIFCIF");
		consultaDatosInscripcion.SELECT("DIRECCIONES.POBLACIONEXTRANJERA");
		consultaDatosInscripcion.SELECT("DIRECCIONES.MOVIL");
		consultaDatosInscripcion.SELECT("'CEN_PERSONA' AS TIPODESTINATARIO");
		consultaDatosInscripcion.SELECT("'0' AS ORIGENDESTINATARIO");
		consultaDatosInscripcion.SELECT("NULL AS IDESTADO");
		
		
		consultaDatosInscripcion.FROM("FOR_CURSO CURSO");
		
		consultaDatosInscripcion.INNER_JOIN(
				"FOR_PERSONA_CURSO FORMADOR ON FORMADOR.IDCURSO = CURSO.IDCURSO AND FORMADOR.FECHABAJA IS NULL");
		 
		consultaDatosInscripcion.INNER_JOIN( "CEN_PERSONA PERSONA ON PERSONA.IDPERSONA = FORMADOR.IDPERSONA");
				
		
		consultaDatosInscripcion.INNER_JOIN("CEN_DIRECCIONES DIRECCIONES ON DIRECCIONES.IDPERSONA  = PERSONA.IDPERSONA "
										 + "	AND DIRECCIONES.IDINSTITUCION = CURSO.IDINSTITUCION");
			
				
		consultaDatosInscripcion.INNER_JOIN("CEN_DIRECCION_TIPODIRECCION TIPODIRECCION ON DIRECCIONES.IDPERSONA  = TIPODIRECCION.IDPERSONA AND "
				+ "DIRECCIONES.IDINSTITUCION = TIPODIRECCION.IDINSTITUCION AND DIRECCIONES.IDDIRECCION = TIPODIRECCION.IDDIRECCION AND TIPODIRECCION.IDTIPODIRECCION = 14");				

		consultaDatosInscripcion.WHERE("CURSO.IDCURSO = '" + idCurso + "'");

		return consultaDatosInscripcion.toString();
	}

	private String getFormadoresSesion(Long idEvento, Short idInstitucion, String idEnvio, String usumodificacion) {

		SQL consultaDatosInscripcion = new SQL();

		
		
		consultaDatosInscripcion.SELECT( idEnvio + " AS IDENVIO");
		consultaDatosInscripcion.SELECT("PERSONAEVENTO.IDPERSONA");
		consultaDatosInscripcion.SELECT("EVENTO.IDINSTITUCION");
		consultaDatosInscripcion.SELECT("SYSDATE AS FECHAMODIFICACION");
		consultaDatosInscripcion.SELECT("'1' AS USUMODIFICACION");
		consultaDatosInscripcion.SELECT("DIRECCIONES.DOMICILIO");
		consultaDatosInscripcion.SELECT("DIRECCIONES.CODIGOPOSTAL");
		consultaDatosInscripcion.SELECT("DIRECCIONES.FAX1");
		consultaDatosInscripcion.SELECT("DIRECCIONES.FAX2");
		consultaDatosInscripcion.SELECT("DIRECCIONES.CORREOELECTRONICO");
		consultaDatosInscripcion.SELECT("DIRECCIONES.IDPAIS");
		consultaDatosInscripcion.SELECT("DIRECCIONES.IDPROVINCIA");
		consultaDatosInscripcion.SELECT("DIRECCIONES.IDPOBLACION");
		consultaDatosInscripcion.SELECT("PERSONA.NOMBRE");
		consultaDatosInscripcion.SELECT("PERSONA.APELLIDOS1");
		consultaDatosInscripcion.SELECT("PERSONA.APELLIDOS2");
		consultaDatosInscripcion.SELECT("PERSONA.NIFCIF");
		consultaDatosInscripcion.SELECT("DIRECCIONES.POBLACIONEXTRANJERA");
		consultaDatosInscripcion.SELECT("DIRECCIONES.MOVIL");
		consultaDatosInscripcion.SELECT("'CEN_PERSONA' AS TIPODESTINATARIO");
		consultaDatosInscripcion.SELECT("'0' AS ORIGENDESTINATARIO");
		consultaDatosInscripcion.SELECT("NULL AS IDESTADO");
		
		
		consultaDatosInscripcion.FROM("AGE_EVENTO EVENTO");
		
		consultaDatosInscripcion.INNER_JOIN(
				"AGE_PERSONA_EVENTO PERSONAEVENTO ON EVENTO.IDEVENTO = PERSONAEVENTO.IDEVENTO AND PERSONAEVENTO.FECHABAJA IS NULL");
	
		consultaDatosInscripcion.INNER_JOIN( "CEN_PERSONA PERSONA ON PERSONA.IDPERSONA = PERSONAEVENTO.IDPERSONA");
				
		
		consultaDatosInscripcion.INNER_JOIN("CEN_DIRECCIONES DIRECCIONES ON DIRECCIONES.IDPERSONA  = PERSONA.IDPERSONA "
										 + "	AND DIRECCIONES.IDINSTITUCION = EVENTO.IDINSTITUCION");
				
		consultaDatosInscripcion.INNER_JOIN("CEN_DIRECCION_TIPODIRECCION TIPODIRECCION ON DIRECCIONES.IDPERSONA  = TIPODIRECCION.IDPERSONA AND "
				+ "DIRECCIONES.IDINSTITUCION = TIPODIRECCION.IDINSTITUCION AND DIRECCIONES.IDDIRECCION = TIPODIRECCION.IDDIRECCION AND TIPODIRECCION.IDTIPODIRECCION = 14");				

		consultaDatosInscripcion.WHERE("EVENTO.IDEVENTO = '" + idEvento + "'");

		return consultaDatosInscripcion.toString();
	}

	private String getInscritosCurso(Long idCurso, Short idInstitucion, String idEnvio, String usumodificacion) {

		SQL consultaDatosInscripcion = new SQL();

		
		
		consultaDatosInscripcion.SELECT( idEnvio + " AS IDENVIO");
		consultaDatosInscripcion.SELECT("INSCRIPCION.IDPERSONA");
		consultaDatosInscripcion.SELECT("CURSO.IDINSTITUCION");
		consultaDatosInscripcion.SELECT("SYSDATE AS FECHAMODIFICACION");
		consultaDatosInscripcion.SELECT("'1' AS USUMODIFICACION");
		consultaDatosInscripcion.SELECT("DIRECCIONES.DOMICILIO");
		consultaDatosInscripcion.SELECT("DIRECCIONES.CODIGOPOSTAL");
		consultaDatosInscripcion.SELECT("DIRECCIONES.FAX1");
		consultaDatosInscripcion.SELECT("DIRECCIONES.FAX2");
		consultaDatosInscripcion.SELECT("DIRECCIONES.CORREOELECTRONICO");
		consultaDatosInscripcion.SELECT("DIRECCIONES.IDPAIS");
		consultaDatosInscripcion.SELECT("DIRECCIONES.IDPROVINCIA");
		consultaDatosInscripcion.SELECT("DIRECCIONES.IDPOBLACION");
		consultaDatosInscripcion.SELECT("PERSONA.NOMBRE");
		consultaDatosInscripcion.SELECT("PERSONA.APELLIDOS1");
		consultaDatosInscripcion.SELECT("PERSONA.APELLIDOS2");
		consultaDatosInscripcion.SELECT("PERSONA.NIFCIF");
		consultaDatosInscripcion.SELECT("DIRECCIONES.POBLACIONEXTRANJERA");
		consultaDatosInscripcion.SELECT("DIRECCIONES.MOVIL");
		consultaDatosInscripcion.SELECT("'CEN_PERSONA' AS TIPODESTINATARIO");
		consultaDatosInscripcion.SELECT("'0' AS ORIGENDESTINATARIO");
		consultaDatosInscripcion.SELECT("NULL AS IDESTADO");
		
		
		consultaDatosInscripcion.FROM("FOR_CURSO CURSO");
		
		consultaDatosInscripcion.INNER_JOIN(
				"FOR_INSCRIPCION INSCRIPCION ON CURSO.IDCURSO = INSCRIPCION.IDCURSO AND INSCRIPCION.IDESTADOINSCRIPCION = 3");
	
		consultaDatosInscripcion.INNER_JOIN( "CEN_PERSONA PERSONA ON PERSONA.IDPERSONA = INSCRIPCION.IDPERSONA");
				
		
		consultaDatosInscripcion.INNER_JOIN("CEN_DIRECCIONES DIRECCIONES ON DIRECCIONES.IDPERSONA  = PERSONA.IDPERSONA "
										 + "	AND DIRECCIONES.IDINSTITUCION = CURSO.IDINSTITUCION");

				
		consultaDatosInscripcion.INNER_JOIN("CEN_DIRECCION_TIPODIRECCION TIPODIRECCION ON DIRECCIONES.IDPERSONA  = TIPODIRECCION.IDPERSONA AND "
				+ "DIRECCIONES.IDINSTITUCION = TIPODIRECCION.IDINSTITUCION AND DIRECCIONES.IDDIRECCION = TIPODIRECCION.IDDIRECCION AND TIPODIRECCION.IDTIPODIRECCION = 14");				

		consultaDatosInscripcion.WHERE("CURSO.IDCURSO = '" + idCurso + "'");

		return consultaDatosInscripcion.toString();
	}

	

	private String getInteresadosTemasCurso(Long idCurso, Short idInstitucion, String idEnvio,
			String usumodificacion) {

		SQL consultaDatosInscripcion = new SQL();

		
		
		consultaDatosInscripcion.SELECT( idEnvio + " AS IDENVIO");
		consultaDatosInscripcion.SELECT("TEMA.IDPERSONA");
		consultaDatosInscripcion.SELECT("CURSO.IDINSTITUCION");
		consultaDatosInscripcion.SELECT("SYSDATE AS FECHAMODIFICACION");
		consultaDatosInscripcion.SELECT("'1' AS USUMODIFICACION");
		consultaDatosInscripcion.SELECT("DIRECCIONES.DOMICILIO");
		consultaDatosInscripcion.SELECT("DIRECCIONES.CODIGOPOSTAL");
		consultaDatosInscripcion.SELECT("DIRECCIONES.FAX1");
		consultaDatosInscripcion.SELECT("DIRECCIONES.FAX2");
		consultaDatosInscripcion.SELECT("DIRECCIONES.CORREOELECTRONICO");
		consultaDatosInscripcion.SELECT("DIRECCIONES.IDPAIS");
		consultaDatosInscripcion.SELECT("DIRECCIONES.IDPROVINCIA");
		consultaDatosInscripcion.SELECT("DIRECCIONES.IDPOBLACION");
		consultaDatosInscripcion.SELECT("PERSONA.NOMBRE");
		consultaDatosInscripcion.SELECT("PERSONA.APELLIDOS1");
		consultaDatosInscripcion.SELECT("PERSONA.APELLIDOS2");
		consultaDatosInscripcion.SELECT("PERSONA.NIFCIF");
		consultaDatosInscripcion.SELECT("DIRECCIONES.POBLACIONEXTRANJERA");
		consultaDatosInscripcion.SELECT("DIRECCIONES.MOVIL");
		consultaDatosInscripcion.SELECT("'CEN_PERSONA' AS TIPODESTINATARIO");
		consultaDatosInscripcion.SELECT("'0' AS ORIGENDESTINATARIO");
		consultaDatosInscripcion.SELECT("NULL AS IDESTADO");
		
		
		consultaDatosInscripcion.FROM("FOR_CURSO CURSO");
		
		consultaDatosInscripcion.INNER_JOIN(
				"FOR_TEMACURSO_CURSO TEMACURSO ON TEMACURSO.IDCURSO = CURSO.IDCURSO");
		consultaDatosInscripcion.INNER_JOIN(
				"FOR_TEMACURSO_PERSONA TEMA ON TEMA.IDTEMACURSO = TEMACURSO.IDTEMACURSO AND TEMA.IDINSTITUCION = CURSO.IDINSTITUCION");
		consultaDatosInscripcion.INNER_JOIN( "CEN_PERSONA PERSONA ON PERSONA.IDPERSONA = TEMA.IDPERSONA");
				
		
		consultaDatosInscripcion.INNER_JOIN("CEN_DIRECCIONES DIRECCIONES ON DIRECCIONES.IDPERSONA  = PERSONA.IDPERSONA "
										 + "	AND DIRECCIONES.IDINSTITUCION = CURSO.IDINSTITUCION");
			
				
		consultaDatosInscripcion.INNER_JOIN("CEN_DIRECCION_TIPODIRECCION TIPODIRECCION ON DIRECCIONES.IDPERSONA  = TIPODIRECCION.IDPERSONA AND "
				+ "DIRECCIONES.IDINSTITUCION = TIPODIRECCION.IDINSTITUCION AND DIRECCIONES.IDDIRECCION = TIPODIRECCION.IDDIRECCION AND TIPODIRECCION.IDTIPODIRECCION = 14");				

		consultaDatosInscripcion.WHERE("CURSO.IDCURSO = '" + idCurso + "'");

		return consultaDatosInscripcion.toString();
	}

	
	
	
}
