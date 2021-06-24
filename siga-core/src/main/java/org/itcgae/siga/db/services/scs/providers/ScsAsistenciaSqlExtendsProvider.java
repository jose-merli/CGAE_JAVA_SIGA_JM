package org.itcgae.siga.db.services.scs.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableItem;
import org.itcgae.siga.DTOs.scs.FiltroAsistenciaItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.ScsActuacionasistencia;
import org.itcgae.siga.db.mappers.ScsAsistenciaSqlProvider;

public class ScsAsistenciaSqlExtendsProvider extends ScsAsistenciaSqlProvider {

	public String searchClaveAsistencia(AsuntosJusticiableItem asuntosJusticiableItem, Integer tamMax) {
		SQL sql = new SQL();
		SQL sqlOrder = new SQL();

		sqlOrder.SELECT("*");
		sql.SELECT("ASISTENCIA.idinstitucion, ASISTENCIA.anio,ASISTENCIA.numero,'' as clave, '' as rol, 'A' as tipo");
		sql.FROM("SCS_ASISTENCIA ASISTENCIA");
		// El Join con la tabla de scs_personaJG, solo realizará si nos viene informado
		// alguno de los datos del solicitante(Nif, nombre o apellidos).
		if (asuntosJusticiableItem.getNif() != null || asuntosJusticiableItem.getNombre() != null
				|| asuntosJusticiableItem.getApellidos() != null) {
			sql.INNER_JOIN(
					"SCS_PERSONAJG PERSONA ON (ASISTENCIA.IDPERSONAJG = PERSONA.IDPERSONA AND PERSONA.IDINSTITUCION = ASISTENCIA.IDINSTITUCION)");
		}
		sql.WHERE("ASISTENCIA.idinstitucion =" + asuntosJusticiableItem.getIdInstitucion());

		if (asuntosJusticiableItem.getAnio() != null && asuntosJusticiableItem.getAnio() != "") {
			sql.WHERE("ASISTENCIA.ANIO = " + asuntosJusticiableItem.getAnio());
		}
		if (asuntosJusticiableItem.getNumero() != null) {
			sql.WHERE("ASISTENCIA.NUMERO = " + asuntosJusticiableItem.getNumero());
		}
		if (asuntosJusticiableItem.getIdTurno() != null) {
			sql.WHERE("ASISTENCIA.IDTURNO = " + asuntosJusticiableItem.getIdTurno());
		}
		if (asuntosJusticiableItem.getIdGuardia() != null) {
			sql.WHERE("ASISTENCIA.IDGUARDIA = " + asuntosJusticiableItem.getIdGuardia());
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

		if (asuntosJusticiableItem.getNif() != null && asuntosJusticiableItem.getNif() != "") {
			if (asuntosJusticiableItem.getApellidos() != null)
				sql.WHERE(UtilidadesString.filtroTextoBusquedas("REPLACE(CONCAT(apellido1,apellido2), ' ', '')",
						asuntosJusticiableItem.getApellidos().replaceAll("\\s+", "")));
			if (asuntosJusticiableItem.getNombre() != null)
				sql.WHERE(UtilidadesString.filtroTextoBusquedas("NOMBRE", asuntosJusticiableItem.getNombre()));

		}
		if (asuntosJusticiableItem.getIdPersonaColegiado() != null) {
			sql.WHERE("ASISTENCIA.IDPERSONACOLEGIADO = " + asuntosJusticiableItem.getIdPersonaColegiado());
		}
		if (asuntosJusticiableItem.getNif() != null  && asuntosJusticiableItem.getNif() != "") {
            sql.WHERE("upper(PERSONA.NIF) like upper('%"+asuntosJusticiableItem.getNif()+"%')");
		}
		if (asuntosJusticiableItem.getIdTipoAsistencia() != null) {
			sql.WHERE("ASISTENCIA.IDTIPOASISTENCIA  = " + asuntosJusticiableItem.getIdTipoAsistencia());
		}
		if (asuntosJusticiableItem.getNumProcedimiento() != null && asuntosJusticiableItem.getNumProcedimiento() != "") {
			sql.WHERE("ASISTENCIA.NUMEROPROCEDIMIENTO   = '" + asuntosJusticiableItem.getNumProcedimiento()+"'");
		}
		if (asuntosJusticiableItem.getNumeroDiligencia() != null && asuntosJusticiableItem.getNumeroDiligencia() != "") {
			sql.WHERE("ASISTENCIA.NUMERODILIGENCIA   = '" + asuntosJusticiableItem.getNumeroDiligencia()+"'");
		}
		if (asuntosJusticiableItem.getComisaria() != null) {
			sql.WHERE("ASISTENCIA.COMISARIA   = " + asuntosJusticiableItem.getComisaria());
		}
		if (asuntosJusticiableItem.getIdJuzgado() != null) {
			sql.WHERE("ASISTENCIA.JUZGADO  = " + asuntosJusticiableItem.getIdJuzgado());
		}
		sqlOrder.FROM("(" + sql + " )");
		if (tamMax != null) {
			Integer tamMaxNumber = tamMax + 1;
			sqlOrder.WHERE("rownum <= " + tamMaxNumber);
		}

		return sqlOrder.toString();
		// return sql.toString();
	}

	public String getAsuntoTipoAsistencia(AsuntosClaveJusticiableItem asuntoClave, String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("ASISTENCIA.IDINSTITUCION");
		sql.SELECT("concat('A' || ASISTENCIA.anio || '/',lpad(ASISTENCIA.NUMERO,5,'0') ) as asunto");
		sql.SELECT("ASISTENCIA.FECHAHORA as fecha");
		sql.SELECT("ASISTENCIA.ANIO");
		sql.SELECT("ASISTENCIA.NUMERO");
		sql.SELECT("ASISTENCIA.codigo");
		sql.SELECT("GUARDIA.nombre as turnoguardia");
		sql.SELECT(
				"('<b>Centro de Detención</b>: ' || COMISARIA.NOMBRE || '<br/> <b>Comisaría</b>: ' || COMISARIA.NOMBRE) as datosinteres");
		sql.SELECT("ASISTENCIA.IDTURNO");
		sql.SELECT("ASISTENCIA.idpersonajg idpersonaasistido");
		sql.SELECT("ASISTENCIA.IDPERSONACOLEGIADO");

		sql.FROM("SCS_ASISTENCIA ASISTENCIA");

		sql.LEFT_OUTER_JOIN(
				"SCS_GUARDIASTURNO GUARDIA ON ASISTENCIA.IDGUARDIA = GUARDIA.IDGUARDIA AND ASISTENCIA.IDTURNO = GUARDIA.IDTURNO");
		sql.LEFT_OUTER_JOIN(
				"SCS_COMISARIA COMISARIA ON COMISARIA.IDCOMISARIA = ASISTENCIA.COMISARIA AND COMISARIA.IDINSTITUCION = ASISTENCIA.IDINSTITUCION");

		sql.WHERE("ASISTENCIA.IDINSTITUCION = '" + asuntoClave.getIdInstitucion() + "'");
		sql.WHERE("ASISTENCIA.ANIO = '" + asuntoClave.getAnio() + "'");
		sql.WHERE("ASISTENCIA.NUMERO = '" + asuntoClave.getNumero() + "'");

		if (asuntoClave.getClave() != null) {
			sql.WHERE("ASISTENCIA.idturno = '" + asuntoClave.getClave() + "'");
		}

		return sql.toString();
	}
	
	public String searchAsistencias(FiltroAsistenciaItem filtroAsistenciaItem, Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("scs_asistencia.anio");
		sql.SELECT("scs_asistencia.numero");
		sql.SELECT("scs_asistencia.fechahora");
		sql.SELECT("scs_asistencia.observaciones");
		sql.SELECT("scs_asistencia.incidencias");
		sql.SELECT("scs_asistencia.ejganio");
		sql.SELECT("scs_asistencia.ejgnumero");
		sql.SELECT("scs_asistencia.fechaanulacion");
		sql.SELECT("scs_asistencia.motivosanulacion");
		sql.SELECT("scs_asistencia.delitosimputados");
		sql.SELECT("scs_asistencia.contrarios");
		sql.SELECT("scs_asistencia.datosdefensajuridica");
		sql.SELECT("scs_asistencia.fechacierre");
		sql.SELECT("scs_asistencia.idtipoasistencia");
		sql.SELECT("scs_asistencia.idtipoasistenciacolegio");
		sql.SELECT("scs_asistencia.idturno");
		sql.SELECT("scs_asistencia.idguardia");
		sql.SELECT("scs_asistencia.idpersonacolegiado");
		sql.SELECT("scs_asistencia.idpersonajg");
		sql.SELECT("scs_asistencia.fechamodificacion");
		sql.SELECT("scs_asistencia.usumodificacion");
		sql.SELECT("scs_asistencia.designa_anio");
		sql.SELECT("scs_asistencia.designa_numero");
		sql.SELECT("scs_asistencia.designa_turno");
		sql.SELECT("scs_asistencia.facturado");
		sql.SELECT("scs_asistencia.pagado");
		sql.SELECT("scs_asistencia.idfacturacion");
		sql.SELECT("scs_asistencia.numerodiligencia");
		sql.SELECT("scs_asistencia.comisaria");
		sql.SELECT("scs_asistencia.comisariaidinstitucion");
		sql.SELECT("scs_asistencia.numeroprocedimiento");
		sql.SELECT("scs_asistencia.juzgado");
		sql.SELECT("scs_asistencia.juzgadoidinstitucion");
		sql.SELECT("scs_asistencia.idestadoasistencia");
		sql.SELECT("scs_asistencia.ejgidtipoejg");
		sql.SELECT("scs_asistencia.nig");
		sql.SELECT("scs_asistencia.idpretension");
		sql.SELECT("scs_asistencia.fechaestadoasistencia");
		sql.SELECT("scs_asistencia.fechasolicitud");
		sql.SELECT("scs_asistencia.idorigenasistencia");
		sql.SELECT("scs_asistencia.idmovimiento");
		sql.SELECT("to_char(fechahora, 'hh24')      hora");
		sql.SELECT("to_char(fechahora, 'mi')        minuto");
		sql.SELECT("nombre");
		sql.SELECT("apellido1");
		sql.SELECT("apellido2");
		sql.SELECT("nif");
		sql.SELECT("sexo");
		sql.SELECT("aa.fechajustificacion");
		sql.SELECT("aa.fecha fechaActuacion");
		sql.SELECT("aa.numeroasunto");
		sql.SELECT("case"
				+ "    when aa.idcomisaria is not null"
				+ "    then aa.idcomisaria"
				+ "    when aa.idjuzgado is not null"
				+ "    then aa.idjuzgado"
				+ "    else null end lugar");
		sql.SELECT("case"
				+ "    when aa.idcomisaria is not null"
				+ "    then 'C'"
				+ "    when aa.idjuzgado is not null"
				+ "    then 'J'"
				+ "    else '' end comisariaJuzgado");
		sql.FROM("scs_asistencia");
		sql.INNER_JOIN("scs_personajg p on p.idpersona = scs_asistencia.idpersonajg AND p.idinstitucion = scs_asistencia.idinstitucion");
		sql.INNER_JOIN("scs_actuacionasistencia aa on aa.idinstitucion = scs_asistencia.idinstitucion AND aa.anio = scs_asistencia.anio AND aa.numero = scs_asistencia.numero");
		sql.WHERE("scs_asistencia.idinstitucion = " + idInstitucion);
		sql.AND();
		sql.WHERE("scs_asistencia.idturno = '" + filtroAsistenciaItem.getIdTurno() + "'");
		sql.AND();
		sql.WHERE("scs_asistencia.idguardia = '" + filtroAsistenciaItem.getIdGuardia() + "'");
		sql.AND();
		sql.WHERE("scs_asistencia.idpersonacolegiado = '" + filtroAsistenciaItem.getIdLetradoGuardia() + "'");
		sql.AND();
		sql.WHERE("trunc(scs_asistencia.fechahora) = '"+filtroAsistenciaItem.getDiaGuardia() + "'");
		sql.AND();
		sql.WHERE("EXISTS ("
				+ "        SELECT"
				+ "            1"
				+ "        FROM"
				+ "            scs_actuacionasistencia aa"
				+ "        WHERE"
				+ "                aa.idinstitucion = scs_asistencia.idinstitucion"
				+ "            AND aa.anio = scs_asistencia.anio"
				+ "            AND aa.numero = scs_asistencia.numero"
				+ "    )");
		sql.AND();
		sql.WHERE("scs_asistencia.idtipoasistencia = '" + filtroAsistenciaItem.getIdTipoAsistencia() + "'");
		if(!"".equals(filtroAsistenciaItem.getIdTipoAsistenciaColegiado()) 
				&& filtroAsistenciaItem.getIdTipoAsistenciaColegiado() != null) {
			sql.AND();
			sql.WHERE("scs_asistencia.idtipoasistenciacolegio = '" + filtroAsistenciaItem.getIdTipoAsistenciaColegiado() + "'");
		}
		sql.ORDER_BY("scs_asistencia.anio","scs_asistencia.numero","aa.idactuacion");
		
		return sql.toString();
	}
	
	public String getNextNumeroAsistencia(String anio, Short idInstitucion) {
		 SQL sql = new SQL();
		 //SELECT nvl(MAX(NUMERO)+1,1) NUMERO FROM SCS_ASISTENCIA WHERE IDINSTITUCION = 2005 AND ANIO = 2018
		 
		 sql.SELECT("nvl(MAX(NUMERO)+1,1) NUMERO");
		 sql.FROM("SCS_ASISTENCIA");
		 sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'",
				 "ANIO = '" + anio + "'");
		 
		 return sql.toString();
	}
	
	public String updateAsistenciaExpress(ScsActuacionasistencia record) {
		SQL sql = new SQL();
        sql.UPDATE("SCS_ACTUACIONASISTENCIA");
        
        if (record.getFecha() != null) {
            sql.SET("FECHA = #{fecha,jdbcType=TIMESTAMP}");
        }

        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getFechajustificacion() != null) {
            sql.SET("FECHAJUSTIFICACION = #{fechajustificacion,jdbcType=TIMESTAMP}");
        }
             
        if (record.getNumeroasunto() != null) {
            sql.SET("NUMEROASUNTO = #{numeroasunto,jdbcType=VARCHAR}");
        }
        
        if (record.getIdcomisaria() != null) {
            sql.SET("IDCOMISARIA = #{idcomisaria,jdbcType=DECIMAL}");
            sql.SET("IDINSTITUCION_COMIS = #{idinstitucionComis,jdbcType=DECIMAL}");
            sql.SET("IDINSTITUCION_JUZG = NULL");
            sql.SET("IDJUZGADO = NULL");
        }else if (record.getIdjuzgado() != null) {
            sql.SET("IDINSTITUCION_JUZG = #{idinstitucionJuzg,jdbcType=DECIMAL}");
            sql.SET("IDJUZGADO = #{idjuzgado,jdbcType=DECIMAL}");
            sql.SET("IDCOMISARIA = NULL");
            sql.SET("IDINSTITUCION_COMIS = NULL");
        }else {
        	sql.SET("IDINSTITUCION_JUZG = NULL");
            sql.SET("IDJUZGADO = NULL");
            sql.SET("IDCOMISARIA = NULL");
            sql.SET("IDINSTITUCION_COMIS = NULL");
        }
        
        if (record.getIdtipoasistencia() != null) {
            sql.SET("IDTIPOASISTENCIA = #{idtipoasistencia,jdbcType=DECIMAL}");
        }
        
        
        sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
        sql.WHERE("ANIO = #{anio,jdbcType=DECIMAL}");
        sql.WHERE("NUMERO = #{numero,jdbcType=DECIMAL}");
        sql.WHERE("IDACTUACION = #{idactuacion,jdbcType=DECIMAL}");
        
        return sql.toString();
	}

}
