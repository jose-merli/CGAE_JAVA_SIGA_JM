package org.itcgae.siga.db.services.scs.providers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.CalendariosProgDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.DatosCalendarioItem;
import org.itcgae.siga.DTOs.scs.GuardiasItem;
import org.itcgae.siga.DTOs.scs.TarjetaAsistenciaResponseItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.ScsCabeceraguardias;
import org.itcgae.siga.db.mappers.ScsCabeceraguardiasSqlProvider;

public class ScsCabeceraguardiasSqlExtendsProvider extends ScsCabeceraguardiasSqlProvider{

	public String getCabeceraGuardiasDeVariasPersonas(String colegio, ArrayList<String> listaIdPersonas) {
		SQL sql = new SQL();
		// preparar grupos en sentencia IN
		String gruposIN = "";
		for(int i=0;i< listaIdPersonas.size(); i++) {
			
			gruposIN = gruposIN.concat("'");
			if(i != listaIdPersonas.size() -1) {
				gruposIN = gruposIN.concat(listaIdPersonas.get(i));
				gruposIN = gruposIN.concat("'");
				gruposIN = gruposIN.concat(",");
			}else {
				gruposIN = gruposIN.concat(listaIdPersonas.get(i));
				gruposIN = gruposIN.concat("'");
			}	
		}
		sql.SELECT_DISTINCT("IDINSTITUCION");
		sql.SELECT("IDTURNO");
		sql.SELECT("IDGUARDIA");
		sql.SELECT("FECHAINICIO");
		sql.FROM("SCS_CABECERAGUARDIAS");
		sql.WHERE("IDINSTITUCION = " + colegio +  "");
		sql.WHERE("IDPERSONA IN (" + gruposIN +  ")");
		sql.GROUP_BY("IDINSTITUCION,IDTURNO,IDGUARDIA, FECHAINICIO ");
		if (null != listaIdPersonas && listaIdPersonas.size()>0) {
			sql.HAVING("COUNT(1) = " + listaIdPersonas.size() +  "");
		}else {
			sql.HAVING("COUNT(1) = 0");
		}
		
		return sql.toString();
	}
	
	public String busquedaGuardiasColegiado(GuardiasItem guardiaItem, String idInstitucion, Integer tamMax) {
		SQL sql = new SQL();
		SQL SQL_PADRE = new SQL();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String fechahasta;
		String fechadesde;
		sql.SELECT("guard.validado,"
				+ "    guard.fechavalidacion,"
				+ "    guard.idturno,"
				+ "    guard.idguardia,"
				+ "    guard.idcalendarioguardias,"
				+ "    guard.idpersona,"
				+ "    guard.facturado,"
				+ "    guard.idfacturacion,"
				+ "    guard.numerogrupo,"
				+ "    guard.observacionesanulacion,"
				+ "    ("
				+ "        SELECT"
				+ "            t.nombre"
				+ "        FROM"
				+ "            scs_turno t"
				+ "        WHERE"
				+ "                t.idinstitucion = guard.idinstitucion"
				+ "            AND"
				+ "                t.idturno = guard.idturno"
				+ "    ) AS nomturno,"
				+ "    ("
				+ "        SELECT"
				+ "            g.nombre"
				+ "        FROM"
				+ "            scs_guardiasturno g"
				+ "        WHERE"
				+ "                g.idinstitucion = guard.idinstitucion"
				+ "            AND"
				+ "                g.idturno = guard.idturno"
				+ "            AND"
				+ "                g.idguardia = guard.idguardia"
				+ "    ) AS nomguardia,"
				+ "    guard.fechainicio,"
				+ "    guard.fecha_fin,"
				+ "    perso.apellidos1"
				+ "     || ' '"
				+ "     || perso.apellidos2"
				+ "     || ','"
				+ "     || perso.nombre colegiado,"
				+ "coleg.ncolegiado numeroColegiado");
		
				sql.FROM(" scs_cabeceraguardias guard,"
						+ "    cen_persona perso,"
						+ "    cen_colegiado coleg");
				
			sql.WHERE("coleg.idpersona = guard.idpersona"
					+ "    AND"
					+ "        coleg.idinstitucion = guard.idinstitucion"
					+ "    AND"
					+ "        perso.idpersona = guard.idpersona"
					+ "    AND"
					+ "        perso.idpersona = coleg.idpersona"
					+ " AND "
					+ "guard.idinstitucion = " + idInstitucion);
			
			
			if(guardiaItem.getIdTurno() != null && guardiaItem.getIdTurno() != "") {
				sql.WHERE("guard.idturno IN (" + guardiaItem.getIdTurno()+")");
			}
			
			if(guardiaItem.getIdGuardia() != null && guardiaItem.getIdGuardia() != "") {
				sql.WHERE("guard.idguardia IN ( " + guardiaItem.getIdGuardia() +")");
			}
			
			if(guardiaItem.getNumColegiado() != null && guardiaItem.getNumColegiado() != "") {
				sql.WHERE("coleg.NCOLEGIADO = " + guardiaItem.getNumColegiado());
			}
			
			
			if(guardiaItem.getValidada() != null && guardiaItem.getValidada() != "") {
				sql.WHERE("guard.validado= " + guardiaItem.getValidada());
			}
			
			
		
			
			if(guardiaItem.getFechadesde() != null) {
				fechadesde = dateFormat.format(guardiaItem.getFechadesde());
                sql.WHERE("( guard.fechainicio >= TO_DATE('" + fechadesde + "', 'DD/MM/RRRR')"
                        + " AND "
                        + "guard.fecha_fin >= TO_DATE('" + fechadesde + "', 'DD/MM/RRRR') )");
			}
			if(guardiaItem.getFechahasta() != null) {
				fechahasta = dateFormat.format(guardiaItem.getFechahasta());
                sql.WHERE("( guard.fechainicio <= TO_DATE('" + fechahasta + "', 'DD/MM/RRRR')"
                        + " AND "
                        + "guard.fecha_fin <= TO_DATE('" + fechahasta + "', 'DD/MM/RRRR') )");
			}

			SQL_PADRE.SELECT(" *");
			SQL_PADRE.FROM("( " + sql.toString() + " )");
			if(tamMax != null && tamMax > 0) {
				tamMax += 1;
				SQL_PADRE.WHERE(" ROWNUM <= " + tamMax);
			}


			return SQL_PADRE.toString();
	}
	
	public String validarSolicitudGuardia(ScsCabeceraguardias record) {
		SQL sql = new SQL();
		String fechavalidacion;
		String fechaInicio;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		fechavalidacion = dateFormat.format(record.getFechavalidacion());
		fechaInicio = dateFormat.format(record.getFechainicio());
		sql.UPDATE("SCS_CABECERAGUARDIAS");
		
		if (record.getFechavalidacion() != null) {
			sql.SET("FECHAVALIDACION =  TO_DATE('" + fechavalidacion + "', 'DD/MM/RRRR')");
		}
		
		sql.WHERE("IDINSTITUCION = '" + record.getIdinstitucion() + "'");
		sql.WHERE("IDTURNO = " + record.getIdturno());
		sql.WHERE("IDGUARDIA = " + record.getIdguardia());
		sql.WHERE("IDPERSONA = " + record.getIdpersona());
		sql.WHERE("FECHAINICIO = TO_DATE('" + fechaInicio + "', 'DD/MM/RRRR')");
		return sql.toString();
	}
	
	public String desvalidarGuardiaColegiado(ScsCabeceraguardias record) {
		SQL sql = new SQL();
		String fechaInicio;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		fechaInicio = dateFormat.format(record.getFechainicio());
		sql.UPDATE("SCS_CABECERAGUARDIAS");
		
		sql.SET("FECHAVALIDACION =  null");
		
		sql.WHERE("IDINSTITUCION = '" + record.getIdinstitucion() + "'");
		sql.WHERE("IDTURNO = " + record.getIdturno());
		sql.WHERE("IDGUARDIA = " + record.getIdguardia());
		sql.WHERE("IDPERSONA = " + record.getIdpersona());
		sql.WHERE("FECHAINICIO = TO_DATE('" + fechaInicio + "', 'DD/MM/RRRR')");
		return sql.toString();
	}
	
	public String getPermutaGuardiaColegiado(GuardiasItem guardiaItem) {
		SQL sql = new SQL();
		
		
		
		return sql.toString();
	}
	
	
	
	public String getCalendarioGuardiaColegiado(String institucion,String idTurno,String idGuardia,String idcalendarioguardias) {
		SQL sql = new SQL();
		
		sql.SELECT( "            t.nombre nombreturno,"
				+ "            gt.nombre nombreguardia,"
				+ "            cg.fechainicio,"
				+ "            cg.fechafin,"
				+ "            cg.observaciones,"
				+ "            DECODE("
				+ "                gt.numeroletradosguardia,"
				+ "                0,"
				+ "                DECODE("
				+ "                    ("
				+ "                        SELECT"
				+ "                            COUNT(1) total"
				+ "                        FROM"
				+ "                            scs_calendarioguardias calg"
				+ "                        WHERE"
				+ "                                calg.idinstitucion = cg.idinstitucion"
				+ "                            AND"
				+ "                                calg.idturno = cg.idturno"
				+ "                            AND"
				+ "                                calg.idguardia = cg.idguardia),0, 2, 3),"
				+ "                DECODE("
				+ "                    ("
				+ "                        SELECT"
				+ "                            COUNT(1) total"
				+ "                        FROM"
				+ "                            scs_cabeceraguardias cag"
				+ "                        WHERE"
				+ "                                cag.idinstitucion = cg.idinstitucion"
				+ "                            AND"
				+ "                                cag.idturno = cg.idturno"
				+ "                            AND"
				+ "                                cag.idguardia = cg.idguardia"
				+ "                            AND"
				+ "                                cag.idcalendarioguardias = cg.idcalendarioguardias ), 0, 2,3 )) estado,"
				+ "            cg.idturno,"
				+ "            cg.idguardia,"
				+ "            cg.idcalendarioguardias,"
				+ "            cg.idinstitucion");
		sql.FROM("scs_calendarioguardias cg,"
				+ "            scs_guardiasturno gt,"
				+ "            scs_turno t");
		sql.WHERE(""
				+ "				cg.idinstitucion = " + institucion
				+ "            AND"
				+ "                cg.idturno = " + idTurno
				+ "            AND"
				+ "                cg.idguardia = " +idGuardia
				+ "            AND"
				+ "                gt.idinstitucion = "+ institucion
				+ "            AND"
				+ "                gt.idturno = " + idTurno
				+ "             AND"
				+ "                gt.idguardia = "+idGuardia
				+ "                AND"
				+ "                t.idinstitucion = " + institucion
				+ "                AND"
				+ "                t.idturno = " + idTurno
				+ "            AND "
				+ "                cg.idcalendarioguardias = " + idcalendarioguardias);
		sql.ORDER_BY("nombreturno,"
				+ "    nombreguardia,"
				+ "    fechainicio,"
				+ "    fechafin");
	
		 
		
		
		return sql.toString();
	}
	
	public String tieneGuardia(String institucion,Long idPersona) {
		SQL sql = new SQL();
		
		sql.SELECT ("COUNT(*) NUMGUARDIAS");
		sql.FROM("SCS_CABECERAGUARDIAS");
		sql.WHERE("IDINSTITUCION= " + institucion);
		sql.WHERE("IDPERSONA= " + idPersona);
		sql.WHERE("SYSDATE BETWEEN TRUNC(FECHAINICIO) AND TRUNC(FECHA_FIN)");
	
		return sql.toString();
	}

	public String deleteCabecerasGuardiasCalendario(Integer idInstitucion, Integer idCalendarioGuardias, Integer idTurno, Integer idGuardia, String fechaInicio, String fechaFin) {

		SQL sql = new SQL();

		sql.DELETE_FROM("SCS_CABECERAGUARDIAS");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		sql.WHERE("IDCALENDARIOGUARDIAS = " + idCalendarioGuardias);
		sql.WHERE("IDTURNO = " + idTurno);
		sql.WHERE("IDGUARDIA = " + idGuardia);
		sql.WHERE("FECHAINICIO >= TO_DATE('" + fechaInicio + "', 'dd/mm/yyyy')");
		sql.WHERE("FECHA_FIN <= TO_DATE('" + fechaFin + "', 'dd/mm/yyyy')");
		return sql.toString();

	}

	public String getCabeceraGuardia(String idInstitucion, String idTurno, String idGuardia, String fechaInicio, String fechaFin) {

		SQL sql = new SQL();

		sql.SELECT("*");
		sql.FROM("SCS_CABECERAGUARDIAS");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		sql.WHERE("IDTURNO = " + idTurno);
		sql.WHERE("IDGUARDIA = " + idGuardia);
		sql.WHERE("FECHAINICIO >= TO_DATE('" + fechaInicio + "', 'dd/mm/yyyy')");
		sql.WHERE("FECHA_FIN <= TO_DATE('" + fechaFin + "', 'dd/mm/yyyy')");

		return sql.toString();

	}
	
}

