package org.itcgae.siga.db.services.scs.providers;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaItem;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaRequestDTO;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableItem;
import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.DocumentoActDesignaItem;
import org.itcgae.siga.DTOs.scs.DocumentoDesignaItem;
import org.itcgae.siga.DTOs.scs.JustificacionExpressItem;
import org.itcgae.siga.DTOs.scs.ProcuradorItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.ScsDesigna;
import org.itcgae.siga.db.entities.ScsDesignaKey;
import org.itcgae.siga.db.entities.ScsSaltoscompensaciones;
import org.itcgae.siga.db.mappers.ScsDesignaSqlProvider;

public class ScsDesignacionesSqlExtendsProvider extends ScsDesignaSqlProvider {

	private Logger LOGGER = Logger.getLogger(ScsDesignacionesSqlExtendsProvider.class);
	
	public String searchClaveDesignaciones(AsuntosJusticiableItem asuntosJusticiableItem, Integer tamMaximo, String idLenguaje) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String fecha;
		
		SQL sql = new SQL();
		
		sql.SELECT("designaciones.idinstitucion,"
				+ "    designaciones.anio,"
//				+ "    designaciones.numero,"
				+ "    designaciones.codigo as numero,"
				+ "    ( 'D' || designaciones.anio || '/' || designaciones.numero) asunto,"
				+ "    ( nvl( turno.abreviatura, '' ) || '/') turnoguardia,"
				+ "    ( nvl( persona.nombre, '' ) || ' ' || nvl( persona.apellidos1, '' ) || ' ' || nvl( persona.apellidos2, '' ) ) letrado,"
//				+ "    ( nvl( per.nombre, '' ) || ' ' || nvl( per.apellido1, '' ) || ' ' || nvl( per.apellido2, '' ) ) interesado,"
				+ "    NVL(designaciones.nig, '') nig,"
				+ "    NVL(designaciones.numprocedimiento, '') numeroprocedimiento,"
				+ "    NVL(designaciones.estado, '') estado,"
				+ "    designaciones.fechaentrada,"
				+ "designaciones.IDTIPODESIGNACOLEGIO,"
				+ "    f_siga_getrecurso(juzgado.nombre," + idLenguaje + ") juzgado,"
				+ "f_siga_getrecurso(nvl(td.descripcion,'')," + idLenguaje + ") tipodesigna,"
				+ "nvl( designaciones.nig, 'Sin número' ) || ' / ' || nvl( designaciones.numprocedimiento,'Sin número' ) dilnigproc");
		
		
		sql.FROM("scs_designa designaciones");
		sql.JOIN("scs_turno turno ON (designaciones.idturno = turno.idturno AND designaciones.idinstitucion = turno.idinstitucion)");
		sql.LEFT_OUTER_JOIN("scs_tipodesignacolegio td ON (td.IDTIPODESIGNACOLEGIO = designaciones.IDTIPODESIGNACOLEGIO AND td.IDINSTITUCION = designaciones.IDINSTITUCION)");
		sql.LEFT_OUTER_JOIN("scs_designasletrado designaLetrado ON" 
				+"        (designaLetrado.anio = designaciones.anio" 
				+"            AND designaLetrado.numero = designaciones.numero" 
				+"            AND designaLetrado.idinstitucion = designaciones.idinstitucion" 
				+"            AND designaLetrado.idturno = designaciones.idturno)");
		sql.LEFT_OUTER_JOIN("cen_colegiado colegiado ON"
				+"        (colegiado.idinstitucion = designaciones.idinstitucion"
				+"            AND colegiado.idpersona = designaLetrado.idpersona)");
		sql.LEFT_OUTER_JOIN("cen_persona persona ON (persona.idpersona = colegiado.idpersona)");
		sql.LEFT_OUTER_JOIN("scs_juzgado juzgado ON" 
				+"        (designaciones.idjuzgado = juzgado.idjuzgado"  
				+"            AND designaciones.idinstitucion = juzgado.idinstitucion)");
		
		/*sql.JOIN("scs_tipodesignacolegio td ON (td.IDTIPODESIGNACOLEGIO = designaciones.IDTIPODESIGNACOLEGIO AND td.IDINSTITUCION = designaciones.IDINSTITUCION)");
		sql.JOIN("scs_designasletrado designaLetrado ON"
				+ "    (designaLetrado.anio = designaciones.anio"
				+ "        AND designaLetrado.numero = designaciones.numero"
				+ "        AND designaLetrado.idinstitucion = designaciones.idinstitucion"
				+ "        AND designaLetrado.idturno = designaciones.idturno)");
		sql.JOIN("cen_persona persona ON"
				+ "    (persona.idpersona = designaLetrado.idpersona)");
		
		sql.JOIN("cen_colegiado colegiado ON"
				+ "    (colegiado.idinstitucion = designaciones.idinstitucion"
				+ "        AND colegiado.idpersona = persona.idpersona)");
		sql.JOIN("scs_turno turno ON"
				+ "    (designaciones.idturno = turno.idturno"
				+ "        AND designaciones.idinstitucion = turno.idinstitucion)");
		sql.JOIN("scs_juzgado juzgado ON"
				+ "    (designaciones.idjuzgado = juzgado.idjuzgado"
				+ "        AND designaciones.idinstitucion = juzgado.idinstitucion)");*/
//		sql.JOIN("scs_defendidosdesigna defendidoDesigna ON"
//				+ "    (defendidoDesigna.anio = designaciones.anio"
//				+ "        AND defendidoDesigna.numero = designaciones.numero"
//				+ "        AND defendidoDesigna.idinstitucion = designaciones.idinstitucion"
//				+ "        AND defendidoDesigna.idturno = designaciones.idturno)");
//		sql.JOIN("scs_personajg per ON"
//				+ "    (defendidoDesigna.idinstitucion = per.idinstitucion\r\n"
//				+ "        AND defendidoDesigna.idpersona = per.idpersona)");
		
		sql.WHERE("designaciones.idinstitucion = "+asuntosJusticiableItem.getIdInstitucion());
		sql.WHERE("( designaLetrado.fechadesigna IS NULL"
				+ "        OR designaLetrado.fechadesigna = ("
				+ "        SELECT"
				+ "            MAX(let2.fechadesigna)"
				+ "        FROM\r\n"
				+ "            scs_designasletrado let2"
				+ "        WHERE"
				+ "            designaLetrado.idinstitucion = let2.idinstitucion"
				+ "            AND designaLetrado.idturno = let2.idturno"
				+ "            AND designaLetrado.anio = let2.anio"
				+ "            AND designaLetrado.numero = let2.numero"
				+ "            AND trunc(let2.fechadesigna) <= trunc(SYSDATE) ) )");
		if (asuntosJusticiableItem.getAnio() != null && !asuntosJusticiableItem.getAnio().trim().isEmpty()) {
			sql.WHERE("designaciones.anio = " + asuntosJusticiableItem.getAnio().trim());
		}


		if (asuntosJusticiableItem.getNumero() != null && !asuntosJusticiableItem.getNumero().trim().isEmpty()) {
			sql.WHERE("designaciones.codigo = " + asuntosJusticiableItem.getNumero().trim());
		}
		
		if (asuntosJusticiableItem.getIdEstadoDesigna() != null) {
			sql.WHERE("designaciones.estado = '" + asuntosJusticiableItem.getIdEstadoDesigna().trim()+"'");
		}
		if(asuntosJusticiableItem.getIdTipoDesigna() != null && !asuntosJusticiableItem.getIdTipoDesigna().trim().isEmpty()) {
	    	sql.WHERE("designaciones.IDTIPODESIGNACOLEGIO = "+asuntosJusticiableItem.getIdTipoDesigna());
	    }
		if (asuntosJusticiableItem.getFechaAperturaDesde() != null) {
			fecha = dateFormat.format(asuntosJusticiableItem.getFechaAperturaDesde());
			sql.WHERE("designaciones.fechaentrada >= TO_DATE('" + fecha + "','DD/MM/RRRR')");
		}

		if (asuntosJusticiableItem.getFechaAperturaHasta() != null) {
			fecha = dateFormat.format(asuntosJusticiableItem.getFechaAperturaHasta());
			sql.WHERE("designaciones.fechaentrada <= TO_DATE('" + fecha + "','DD/MM/RRRR')");
		}

		if (asuntosJusticiableItem.getNif() != null && !asuntosJusticiableItem.getNif().trim().isEmpty()) {
			sql.WHERE("per.nif = '%" + asuntosJusticiableItem.getNif().trim() + "%'");
		}

		if (asuntosJusticiableItem.getNombre() != null && !asuntosJusticiableItem.getNombre().trim().isEmpty()) {
			sql.WHERE("per.nombre LIKE upper('%" + asuntosJusticiableItem.getNombre().trim() + "%')");
		}

		if (asuntosJusticiableItem.getApellidos() != null && !asuntosJusticiableItem.getApellidos().trim().isEmpty()) {
			sql.WHERE("(per.apellido1 || ' ' || per.apellido2) LIKE upper('%"
					+ asuntosJusticiableItem.getApellidos().trim() + "%')");
		}
		if (asuntosJusticiableItem.getNig() != null && !asuntosJusticiableItem.getNig().trim().isEmpty()) {
			sql.WHERE("designaciones.nig = '"+asuntosJusticiableItem.getNig()+"'");
		}
		if (asuntosJusticiableItem.getIdJuzgado() != null) {
			sql.WHERE("designaciones.idjuzgado = " + asuntosJusticiableItem.getIdJuzgado());
		}
		if (asuntosJusticiableItem.getIdPersonaColegiado() != null) {
			sql.WHERE("persona.idpersona = " + asuntosJusticiableItem.getIdPersonaColegiado());
		}

		if (asuntosJusticiableItem.getNumeroProcedimiento() != null && !asuntosJusticiableItem.getNumeroProcedimiento().trim().isEmpty()) {
			sql.WHERE("designaciones.numprocedimiento   = '" + asuntosJusticiableItem.getNumeroProcedimiento().trim() + "'");
		}
		
		if(asuntosJusticiableItem.getIdTurno() != null && !asuntosJusticiableItem.getIdTurno().isEmpty()) {
			sql.WHERE("turno.idturno   = '" + asuntosJusticiableItem.getIdTurno() + "'");
		}
		
		if(asuntosJusticiableItem.getnColegiado() != null && !asuntosJusticiableItem.getnColegiado().isEmpty()) {
			sql.WHERE("colegiado.ncolegiado = '" + asuntosJusticiableItem.getnColegiado() + "'");
		}
		
		sql.ORDER_BY("designaciones.anio desc, designaciones.codigo DESC");
		
		SQL sqlPpal = new SQL();
		sqlPpal.SELECT("*");
		sqlPpal.FROM("("+sql.toString()+") consulta");
		sqlPpal.WHERE("ROWNUM <= " + tamMaximo);
		
		return sqlPpal.toString();
	}
	
	public String busquedaDesignaActual(ScsDesignaKey key){
		String sql = "";
		
		try {
			sql =   "select distinct F_SIGA_ACTUACIONESDESIG(des.IDINSTITUCION,des.IDTURNO,des.ANIO,des.NUMERO) AS validada , des.art27, des.idpretension,"
						+ " F_SIGA_GETRECURSO(SP.DESCRIPCION, 1) AS procedimiento, SP2.nombre as modulo, SP2.IDPROCEDIMIENTO as IDMODULO, des.idjuzgado, des.FECHAOFICIOJUZGADO, des.DELITOS, des.FECHARECEPCIONCOLEGIO, des.OBSERVACIONES, des.FECHAJUICIO, des.DEFENSAJURIDICA,"
						+ " des.nig, des.resumenasunto, des.numprocedimiento,des.idprocedimiento, des.estado estado, des.anio anio, des.numero numero, des.IDTIPODESIGNACOLEGIO, des.fechaalta fechaalta,"
						+ " des.fechaentrada fechaentrada,des.idturno idturno, des.codigo codigo, des.sufijo sufijo, des.fechafin, des.idinstitucion idinstitucion,"
						+ "  des.fechaestado fechaestado,juzgado.nombre as nombrejuzgado,  turno.nombre,";
			sql +=  " colegiado.ncolegiado, persona.idpersona, persona.nombre as nombrepersona, persona.APELLIDOS1 as apellido1persona, persona.APELLIDOS2 as apellido2persona";
			
			sql +=  " FROM scs_designa             des\r\n"; 
			sql +=  "   LEFT OUTER JOIN scs_designasletrado     l ON l.anio = des.anio\r\n" + 
					"                                  AND l.numero = des.numero\r\n" + 
					"                                  AND l.idinstitucion = des.idinstitucion\r\n" + 
					"                                  AND l.idturno = des.idturno\r\n" + 
					"    LEFT OUTER JOIN cen_colegiado           colegiado ON colegiado.idinstitucion = l.idinstitucion\r\n" + 
					"                                    AND colegiado.idpersona = l.idpersona\r\n" + 
					"    LEFT OUTER JOIN cen_persona             persona ON l.idpersona = persona.idpersona\r\n";
			sql += 	"    JOIN scs_turno               turno ON des.idturno = turno.idturno\r\n" + 
					"                            AND des.idinstitucion = turno.idinstitucion\r\n" + 
					"    LEFT OUTER JOIN scs_juzgado             juzgado ON des.idjuzgado = juzgado.idjuzgado\r\n" + 
					"                                           AND des.idinstitucion = juzgado.idinstitucion\r\n" +
					"    LEFT JOIN SCS_PRETENSION sp ON SP.IDINSTITUCION = DES.IDINSTITUCION AND SP.IDPRETENSION\r\n" +
					" = DES.IDPRETENSION" +
					"    LEFT JOIN SCS_PROCEDIMIENTOS sp2 ON sp2.IDINSTITUCION = DES.IDINSTITUCION AND sp2.IDPROCEDIMIENTO\r\n" +
					"= DES.IDPROCEDIMIENTO";
			sql += 	" where des.IDINSTITUCION = " + key.getIdinstitucion() ;
			sql += 	" AND des.idTurno = " + key.getIdturno();
			sql += 	" AND des.anio = " + key.getAnio();
			sql += 	" and des.numero = " + key.getNumero();
			sql += 	" and (l.Fechadesigna is null or";
			sql += 	" l.Fechadesigna = (SELECT MAX(LET2.Fechadesigna) FROM SCS_DESIGNASLETRADO LET2";
			sql += 	" WHERE l.IDINSTITUCION = LET2.IDINSTITUCION AND l.IDTURNO = LET2.IDTURNO";
			sql += 	" AND l.ANIO = LET2.ANIO AND l.NUMERO = LET2.NUMERO";
			sql += 	" AND TRUNC(LET2.Fechadesigna) <= TRUNC(SYSDATE)))";
			
		} catch (Exception e) {
			throw e;
		}

		return sql;
	}

	public String busquedaDesignaciones(DesignaItem designaItem, Short idInstitucion, Integer tamMaximo) throws Exception {
		String sql = "";
		
		SQL sqlDesigna = new SQL();
		SQL sqlDefendidos = new SQL();

		Hashtable codigosBind = new Hashtable();
		int contador = 0;
		// Acceso a BBDD
		
		sqlDesigna.SELECT("*");
		sqlDesigna.FROM("scs_designa");
		sqlDesigna.WHERE("IDINSTITUCION = " + idInstitucion);
		
		sqlDefendidos.SELECT("NVL(PER.APELLIDO1, '') || ' ' || NVL(PER.APELLIDO2, '') || ', ' || NVL(PER.NOMBRE, '')");
		sqlDefendidos.FROM("scs_defendidosdesigna ded");
		sqlDefendidos.JOIN("scs_personajg per ON ded.idinstitucion = per.idinstitucion	AND ded.idpersona = per.idpersona");
		sqlDefendidos.WHERE("calidad IS NOT NULL");
		sqlDefendidos.WHERE("ded.anio = des.anio");
		sqlDefendidos.WHERE("ded.numero = des.numero");
		sqlDefendidos.WHERE("ded.idinstitucion = des.idinstitucion");
		sqlDefendidos.WHERE("ded.idturno = des.idturno");
		sqlDefendidos.WHERE("rownum < 2");

		// aalg. INC_06694_SIGA. Se modifica la query para hacerla más eficiente
		try {
			sql = "select distinct F_SIGA_ACTUACIONESDESIG(des.IDINSTITUCION,des.IDTURNO,des.ANIO,des.NUMERO) AS validada , des.art27, des.idpretension, des.idjuzgado, des.FECHAOFICIOJUZGADO, des.DELITOS, des.FECHARECEPCIONCOLEGIO, des.OBSERVACIONES, des.FECHAJUICIO, des.DEFENSAJURIDICA,"
					+ " des.nig, des.numprocedimiento,des.idprocedimiento, des.estado estado, des.anio anio, des.numero numero, des.IDTIPODESIGNACOLEGIO, des.fechaalta fechaalta,"
					+ " des.fechaentrada fechaentrada,des.resumenasunto, des.idturno idturno, des.codigo codigo, des.sufijo sufijo, des.fechafin, des.idinstitucion idinstitucion,"
					+ "  des.fechaestado fechaestado,juzgado.nombre as nombrejuzgado,  turno.nombre,";
			sql +=  " colegiado.ncolegiado, persona.idpersona, NVL(persona.nombre,'') as nombrepersona, NVL(persona.APELLIDOS1,'') as apellido1persona, NVL(persona.APELLIDOS2,'') as apellido2persona ,";
			sql += " (" + sqlDefendidos.toString() +")  AS NOMBREINTERESADO";
			
			sql += " FROM ( " + sqlDesigna + " ) des\r\n"; 
				sql += "   LEFT OUTER JOIN scs_designasletrado     l ON l.anio = des.anio\r\n" + 
					"                                  AND l.numero = des.numero\r\n" + 
					"                                  AND l.idinstitucion = des.idinstitucion\r\n" + 
					"                                  AND l.idturno = des.idturno\r\n" + 
					"    LEFT OUTER JOIN cen_colegiado           colegiado ON colegiado.idinstitucion = l.idinstitucion\r\n" + 
					"                                    AND colegiado.idpersona = l.idpersona\r\n" + 
					"    LEFT OUTER JOIN cen_persona             persona ON l.idpersona = persona.idpersona\r\n";
			sql += 	"    JOIN scs_turno               turno ON des.idturno = turno.idturno\r\n" + 
					"                            AND des.idinstitucion = turno.idinstitucion\r\n" + 
					"    LEFT OUTER JOIN scs_juzgado             juzgado ON des.idjuzgado = juzgado.idjuzgado\r\n" + 
					"                                           AND des.idinstitucion = juzgado.idinstitucion\r\n";

			boolean tiene_juzg = designaItem.getIdJuzgadoActu() != null
					&& designaItem.getIdJuzgadoActu().length >0;
			boolean tiene_asunto = designaItem.getAsunto() != null && !designaItem.getAsunto().equalsIgnoreCase("");
			boolean tiene_acreditacion = designaItem.getIdAcreditacion() != null
					&& (designaItem.getIdAcreditacion().length != 0);
			boolean tiene_modulo = designaItem.getIdModuloActuaciones() != null && designaItem.getIdModuloActuaciones().length >0;
			boolean tiene_fechaJustificacionDesde = designaItem.getFechaJustificacionDesde() != null
					&& designaItem.getFechaJustificacionDesde().toString() != null
					&& !designaItem.getFechaJustificacionDesde().toString().equalsIgnoreCase("");
			boolean tiene_fechaJustificacionHasta = designaItem.getFechaJustificacionHasta() != null
					&& designaItem.getFechaJustificacionHasta().toString() != null
					&& !designaItem.getFechaJustificacionHasta().toString().equalsIgnoreCase("");
			boolean tiene_origen = designaItem.getIdOrigen() != null && !designaItem.getIdOrigen().equalsIgnoreCase("");
			boolean tiene_actuacionesV = designaItem.getIdActuacionesV() != null
					&& !designaItem.getIdActuacionesV().equalsIgnoreCase("");
			boolean tiene_moduloDesignacion = (designaItem.getIdModulos() != null
					&& designaItem.getIdModulos().length > 0);
			boolean tienePretensionesDesignacion = (designaItem.getIdProcedimientos() != null
					&& designaItem.getIdProcedimientos().length > 0);

			if (tiene_juzg||tiene_asunto||tiene_acreditacion||tiene_modulo||tiene_fechaJustificacionDesde||tiene_fechaJustificacionHasta || tiene_origen){
				sql+=	", scs_actuaciondesigna act ";
			}
			
			boolean tiene_interesado = false;
			if ((designaItem.getNif() != null && !designaItem.getNif().equalsIgnoreCase(""))
					|| (designaItem.getNombreInteresado() != null
							&& !designaItem.getNombreInteresado().equalsIgnoreCase(""))
					|| (designaItem.getApellidosInteresado() != null
							&& !designaItem.getApellidosInteresado().equalsIgnoreCase(""))) {
				tiene_interesado = true;
			}


			if (tienePretensionesDesignacion) {
				sql += ", SCS_PRETENSION pret ";
			}

			if (idInstitucion != null) {
				sql += " where des.IDINSTITUCION = " + idInstitucion ;
			}

			

			//if (designaItem.getNumColegiado() != null && !(String.valueOf(designaItem.getNumColegiado())).equals("")) {
				sql += " and (l.Fechadesigna is null or";
				sql += " l.Fechadesigna = (SELECT MAX(LET2.Fechadesigna) FROM SCS_DESIGNASLETRADO LET2";
				sql += " WHERE l.IDINSTITUCION = LET2.IDINSTITUCION AND l.IDTURNO = LET2.IDTURNO";
				sql += " AND l.ANIO = LET2.ANIO AND l.NUMERO = LET2.NUMERO";
				sql += " AND TRUNC(LET2.Fechadesigna) <= TRUNC(SYSDATE)))";

//				sql += " and l.idpersona = " + String.valueOf(designaItem.getNumColegiado()) + " ";
			//}
			if (designaItem.getNumColegiado() != null && !(String.valueOf(designaItem.getNumColegiado())).equals("")) {
				sql += " and colegiado.ncolegiado = " + String.valueOf(designaItem.getNumColegiado()) + " ";
			}

			if (designaItem.getIdTurnos() != null
					&& (String.valueOf(designaItem.getIdTurnos()) != "-1" && designaItem.getIdTurnos().length != 0)
					&& !String.valueOf(designaItem.getIdTurnos()).equals("")) {
				if (designaItem.getIdTurnos().length == 1) {
					sql += " AND des.idTurno = " + designaItem.getIdTurnos()[0];
				} else {
					String turnoIN = "";
					for (int i = 0; i < designaItem.getIdTurnos().length; i++) {
						String turno = designaItem.getIdTurnos()[i];
						if (i == designaItem.getIdTurnos().length - 1) {
							turnoIN = turnoIN + turno;
						} else {
							turnoIN = turnoIN + turno + " ,";
						}
					}
					sql += " AND des.idTurno IN (" + turnoIN + " )";
				}
			}

			if (designaItem.getAno() != 0 && String.valueOf(designaItem.getAno()) != null
					&& !String.valueOf(designaItem.getAno()).equalsIgnoreCase("")) {

				if (String.valueOf(designaItem.getAno()).indexOf('*') >= 0) {

					contador++;
					sql += " AND " + prepararSentenciaCompletaBind(String.valueOf(designaItem.getAno()).trim(),
							"des.anio", contador, codigosBind);

				} else if (designaItem.getAno() != 0) {
					contador++;
					sql += " AND des.anio = " + String.valueOf(designaItem.getAno());
				}
			}

			if (designaItem.getCodigo() != null && !designaItem.getCodigo().equalsIgnoreCase("")) {

				if ((designaItem.getCodigo().indexOf(',') != -1) && (designaItem.getCodigo().indexOf('-') == -1)) {
					String[] parts = designaItem.getCodigo().split(",");
					sql += " AND (des.codigo = ";
					for (int i = 0; i < parts.length; i++) {
						if (i == parts.length - 1) {
							sql += parts[i].trim() + ")";
						} else {
							sql += parts[i].trim() + " OR des.codigo = ";
						}
					}
				} else if ((designaItem.getCodigo().indexOf('-') != -1)
						&& (designaItem.getCodigo().indexOf(',') == -1)) {
					String[] parts = designaItem.getCodigo().split("-");
					if (parts.length == 2) {
						//SIGARNV-2253@DTT.JAMARTIN@08/07/2022@INICIO
//						sql += " and des.codigo IN (" + parts[0] + "," + parts[1] + ")";
						sql += " and des.codigo BETWEEN " + parts[0] + " AND " + parts[1];
						//SIGARNV-2253@DTT.JAMARTIN@08/07/2022@FIN 
					}
				} else if ((designaItem.getCodigo().indexOf('-') == -1)
						&& (designaItem.getCodigo().indexOf(',') == -1)) {
//					sql += " AND des.codigo = " + String.valueOf(designaItem.getCodigo()).trim();
					sql += " AND ltrim(des.codigo,'0') = ltrim(" + designaItem.getCodigo() + ",'0')";
				}
			}
			if (designaItem.getIdJuzgados() != null && designaItem.getIdJuzgados().length > 0) {
				if (designaItem.getIdJuzgados().length == 1) {
					sql += " AND des.idjuzgado = " + designaItem.getIdJuzgados()[0];
				} else {
					String juzgadoIN = "";
					for (int i = 0; i < designaItem.getIdJuzgados().length; i++) {
						String juzgado = designaItem.getIdJuzgados()[i];
						if (i == designaItem.getIdJuzgados().length - 1) {
							juzgadoIN = juzgadoIN + juzgado;
						} else {
							juzgadoIN = juzgadoIN + juzgado + " ,";
						}
					}
					sql += " AND des.idjuzgado IN (" + juzgadoIN + " )";
				}
			}
			
			if (designaItem.getIdModulos() != null && designaItem.getIdModulos().length > 0) {
				if (designaItem.getIdModulos().length == 1) {
					sql += " AND des.IDPROCEDIMIENTO = '" + designaItem.getIdModulos()[0] + "'";
				} else {
					String estadoIN = "";
					for (int i = 0; i < designaItem.getIdModulos().length; i++) {
						String estado = designaItem.getIdModulos()[i];
						if (i == designaItem.getIdModulos().length - 1) {
							estadoIN = estadoIN + "'" + estado + "'";
						} else {
							estadoIN = estadoIN + "'" + estado + "'" + " ,";
						}
					}
					sql += " AND des.IDPROCEDIMIENTO IN (" + estadoIN + " )";
				}

			}
			
			if (tiene_asunto) {
				contador++;
				codigosBind.put(new Integer(contador), designaItem.getAsunto().trim());
//				sql += " AND des.resumenasunto = '" + designaItem.getAsunto() + "'";
				sql += " AND regexp_like(des.resumenasunto,'" + designaItem.getAsunto() + "')";
			}

			if (designaItem.getIdModuloActuaciones() != null && designaItem.getIdModuloActuaciones().length > 0) {
				if (designaItem.getIdModuloActuaciones().length == 1) {
					sql += " AND act.IDPROCEDIMIENTO = '" + designaItem.getIdModuloActuaciones()[0] + "'";
				} else {
					String estadoIN = "";
					for (int i = 0; i < designaItem.getIdModuloActuaciones().length; i++) {
						String estado = designaItem.getIdModuloActuaciones()[i];
						if (i == designaItem.getIdModuloActuaciones().length - 1) {
							estadoIN = estadoIN + "'" + estado + "'";
						} else {
							estadoIN = estadoIN + "'" + estado + "'" + " ,";
						}
					}
					sql += " AND act.IDPROCEDIMIENTO IN (" + estadoIN + " )";
				}

			}

			if (designaItem.getEstados() != null && designaItem.getEstados().length > 0) {
				if (designaItem.getEstados().length == 1) {
					sql += " AND des.estado = '" + designaItem.getEstados()[0] + "'";
				} else {
					String estadoIN = "";
					for (int i = 0; i < designaItem.getEstados().length; i++) {
						String estado = designaItem.getEstados()[i];
						if (i == designaItem.getEstados().length - 1) {
							estadoIN = estadoIN + "'" + estado + "'";
						} else {
							estadoIN = estadoIN + "'" + estado + "'" + " ,";
						}
					}
					sql += " AND des.estado IN (" + estadoIN + " )";
				}

			}
			if (designaItem.getNumProcedimiento() != null && !designaItem.getNumProcedimiento().equalsIgnoreCase("")) {
				contador++;
				codigosBind.put(new Integer(contador), designaItem.getNumProcedimiento().trim());
//				sql += " AND des.numprocedimiento = " + designaItem.getNumProcedimiento();
				sql += " AND regexp_like(des.numprocedimiento,'" + designaItem.getNumProcedimiento() + "') ";
			}
			if (designaItem.getNig() != null && !designaItem.getNig().equalsIgnoreCase("")) {
				contador++;
				codigosBind.put(new Integer(contador), designaItem.getNig().trim());
				sql += " AND des.nig = '" + designaItem.getNig() + "'";
			}
			if (tienePretensionesDesignacion) {
				if (designaItem.getIdProcedimientos() != null && designaItem.getIdProcedimientos().length > 0) {
					if (designaItem.getIdProcedimientos().length == 1) {
						sql += " AND des.IDPRETENSION = '" + designaItem.getIdProcedimientos()[0] + "'";
					} else {
						String estadoIN = "";
						for (int i = 0; i < designaItem.getIdProcedimientos().length; i++) {
							String estado = designaItem.getIdProcedimientos()[i];
							if (i == designaItem.getIdProcedimientos().length - 1) {
								estadoIN = estadoIN + "'" + estado + "'";
							} else {
								estadoIN = estadoIN + "'" + estado + "'" + " ,";
							}
						}
						sql += " AND des.IDPRETENSION IN (" + estadoIN + " )";
					}

				}
			}
			
			if(designaItem.getIdActuacionesV()!=null && !designaItem.getIdActuacionesV().trim().isEmpty()){
				if("SINACTUACIONES".equalsIgnoreCase(designaItem.getIdActuacionesV().trim())){
					sql += (" AND F_SIGA_ACTUACIONESDESIG(des.IDINSTITUCION,des.IDTURNO,des.ANIO,des.NUMERO) IS NULL ");
				}else{
				    sql += (" AND UPPER(F_SIGA_ACTUACIONESDESIG(des.IDINSTITUCION,des.IDTURNO,des.ANIO,des.NUMERO))=UPPER('"+designaItem.getIdActuacionesV()+"')");
				}
			}

			if (designaItem.getDocumentacionActuacion() != null
					&& !designaItem.getDocumentacionActuacion().equalsIgnoreCase("")) {
				if (designaItem.getDocumentacionActuacion().equalsIgnoreCase("TODAS")) {
					sql += " and not exists (select 1 from scs_actuaciondesigna act "
							+ "where act.idturno = des.idturno "
							+ "and act.idinstitucion = des.idinstitucion "
							+ "and act.anio = des.anio "
							+ "and act.numero = des.numero "
							+ "and act.docjustificacion IS NULL or act.docjustificacion = 0) ";
				} else if (designaItem.getDocumentacionActuacion().equalsIgnoreCase("ALGUNAS")) {
					sql += " and exists (select 1 from scs_actuaciondesigna act "
							+ "where act.idturno = des.idturno "
							+ "and act.idinstitucion = des.idinstitucion "
							+ "and act.anio = des.anio "
							+ "and act.numero = des.numero "
							+ "and act.docjustificacion = 1) ";
				} else if (designaItem.getDocumentacionActuacion().equalsIgnoreCase("NINGUNA")) {
					sql += " and not exists (select 1 from scs_actuaciondesigna act "
							+ "where act.idturno = des.idturno "
							+ "and act.idinstitucion = des.idinstitucion "
							+ "and act.anio = des.anio "
							+ "and act.numero = des.numero "
							+ "and act.docjustificacion = 1) ";
				}
			}

			// Mostrar ART 27
			String mostarArt27 = designaItem.getIdArt27();
			if (mostarArt27 != null && !mostarArt27.equalsIgnoreCase("") && !mostarArt27.equalsIgnoreCase("T")) {
				if (mostarArt27.equalsIgnoreCase("S")) {
					sql += " AND des.art27 = 1";
				} else if (mostarArt27.equalsIgnoreCase("N")) {
					sql += " AND des.art27 = 0";
				}
			}

			if (designaItem.getIdCalidad() != null && designaItem.getIdCalidad().length > 0) {
				if (designaItem.getIdCalidad().length == 1) {
					sql += " and DED.idtipoencalidad= " + designaItem.getIdCalidad()[0];
				} else {
					String calidadIN = "";
					for (int i = 0; i < designaItem.getIdCalidad().length; i++) {
						String calidad = designaItem.getIdCalidad()[i];
						if (i == designaItem.getIdCalidad().length - 1) {
							calidadIN = calidadIN + "'" + calidad + "'";
						} else {
							calidadIN = calidadIN + calidad + " ,";
						}
					}
					sql += " and def.ANIO = des.anio" + " and def.NUMERO = des.numero"
							+ " and def.IDINSTITUCION = des.idinstitucion" + " and def.IDTURNO = des.idturno"
							+ " and def.idtipoencalidad IN (" + calidadIN + " )";
				}

			}

			if ((designaItem.getFechaEntradaInicio() != null
					&& !designaItem.getFechaEntradaInicio().toString().equalsIgnoreCase(""))
					|| (designaItem.getFechaEntradaFin() != null
							&& !designaItem.getFechaEntradaFin().toString().equalsIgnoreCase(""))) {

				DateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
				String fechaEntradaInicio ="";
				String fechaEntradaFin =""; 
				if(designaItem.getFechaEntradaInicio()!= null)
					fechaEntradaInicio = formatter1.format(designaItem.getFechaEntradaInicio());
				if(designaItem.getFechaEntradaFin()!= null)
					fechaEntradaFin = formatter1.format(designaItem.getFechaEntradaFin());
				if(designaItem.getFechaEntradaInicio()!= null && designaItem.getFechaEntradaFin()!= null) {
					sql += " and des.fechaentrada between TO_DATE('" + fechaEntradaInicio + "','DD/MM/RRRR') and TO_DATE('" + fechaEntradaFin +"','DD/MM/RRRR') ";
				}else if(designaItem.getFechaEntradaInicio()!= null) {
					sql += " and des.fechaentrada >=  TO_DATE('" + fechaEntradaInicio +"','DD/MM/RRRR') ";
				}else {
					sql += " and des.fechaentrada <=  TO_DATE('" + fechaEntradaFin +"','DD/MM/RRRR') ";
				}
				

			}
			if ((designaItem.getFechaJustificacionDesde() != null
					&& designaItem.getFechaJustificacionDesde().toString() != null
					&& !designaItem.getFechaJustificacionDesde().toString().equalsIgnoreCase(""))
					|| (designaItem.getFechaJustificacionHasta() != null
							&& designaItem.getFechaJustificacionHasta().toString() != null
							&& !designaItem.getFechaJustificacionHasta().toString().equalsIgnoreCase(""))) {
				DateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
				String fechaJustificacionDesde ="";
				String fechaJustificacionHasta =""; 
				if(designaItem.getFechaJustificacionDesde()!= null)
					fechaJustificacionDesde = formatter1.format(designaItem.getFechaJustificacionDesde());
				if(designaItem.getFechaJustificacionHasta()!= null)
					fechaJustificacionHasta = formatter1.format(designaItem.getFechaJustificacionHasta());
				if(designaItem.getFechaJustificacionDesde()!= null && designaItem.getFechaJustificacionHasta()!= null) {
					sql += " and act.fechaJustificacion between TO_DATE('" + fechaJustificacionDesde + "','DD/MM/RRRR') and TO_DATE('" + fechaJustificacionHasta +"','DD/MM/RRRR') ";
				}else if(designaItem.getFechaJustificacionDesde()!= null) {
					sql += " and act.fechaJustificacion >=  TO_DATE('" + fechaJustificacionDesde +"','DD/MM/RRRR') ";
				}else {
					sql += " and act.fechaJustificacion <=  TO_DATE('" + fechaJustificacionHasta +"','DD/MM/RRRR') ";
				}

			}
			if (designaItem.getIdTipoDesignaColegios() != null
					&& (!String.valueOf(designaItem.getIdTipoDesignaColegios()).equalsIgnoreCase(""))
					&& designaItem.getIdTipoDesignaColegios().length > 0) {
				if (designaItem.getIdTipoDesignaColegios().length == 1) {
					sql += " AND des.IDTIPODESIGNACOLEGIO = " + designaItem.getIdTipoDesignaColegios()[0];
				} else {
					String turnoIN = "";
					for (int i = 0; i < designaItem.getIdTipoDesignaColegios().length; i++) {
						String turno = designaItem.getIdTipoDesignaColegios()[i];
						if (i == designaItem.getIdTipoDesignaColegios().length - 1) {
							turnoIN = turnoIN + turno;
						} else {
							turnoIN = turnoIN + turno + " ,";
						}
					}
					sql += " AND des.IDTIPODESIGNACOLEGIO IN (" + turnoIN + " )";
				}
			}

			if (tiene_interesado) {

				if (designaItem.getNif() != null && !designaItem.getNif().equalsIgnoreCase("")) {
//					sql += " and PER.NIF = " + "'" + designaItem.getNif().trim() + "'";
					sql += " AND des.NUMERO IN (SELECT numero FROM SCS_DEFENDIDOSDESIGNA sd WHERE sd.IDPERSONA IN (SELECT IDPERSONA FROM SCS_PERSONAJG sp WHERE sp.NIF like '"+designaItem.getNif()+"%' AND IDINSTITUCION = "+idInstitucion+"))";
				}
				if (designaItem.getNombreInteresado() != null
						&& !designaItem.getNombreInteresado().equalsIgnoreCase("")) {
//					sql += " and PER.NOMBRE = " + "'" + designaItem.getNombreInteresado().trim() + "'";
					sql += " AND des.NUMERO IN (SELECT numero FROM SCS_DEFENDIDOSDESIGNA sd WHERE sd.IDPERSONA IN (SELECT IDPERSONA FROM SCS_PERSONAJG sp WHERE UPPER(sp.NOMBRE) like UPPER('%"+designaItem.getNombreInteresado()+"%') AND IDINSTITUCION = "+idInstitucion+"))";
				}
				if (designaItem.getApellidosInteresado() != null
						&& !designaItem.getApellidosInteresado().equalsIgnoreCase("")) {
					sql += " AND des.NUMERO IN (SELECT numero FROM SCS_DEFENDIDOSDESIGNA sd WHERE sd.IDPERSONA IN (SELECT IDPERSONA FROM SCS_PERSONAJG sp WHERE UPPER(sp.APELLIDO1 || ' ' ||sp.APELLIDO2) LIKE UPPER('%"+designaItem.getApellidosInteresado()+"%') AND sp.IDINSTITUCION = "+idInstitucion+"))";
				}
			}

			if (tiene_juzg || tiene_asunto || tiene_acreditacion || tiene_modulo || tiene_fechaJustificacionDesde
					|| tiene_fechaJustificacionHasta || tiene_origen) {
				sql += " and des.idinstitucion = act.idinstitucion" + " and des.idturno = act.idturno"
						+ " and des.anio = act.anio" + " and des.numero = act.numero ";
				if (tiene_juzg) {
					String a[] = (String.valueOf(designaItem.getIdJuzgadoActu())).split(",");
					if (designaItem.getIdJuzgadoActu().length == 1) {
						sql += " AND act.idjuzgado = " + designaItem.getIdJuzgadoActu()[0];
					} else {
						String turnoIN = "";
						for (int i = 0; i < designaItem.getIdJuzgadoActu().length; i++) {
							String turno = designaItem.getIdJuzgadoActu()[i];
							if (i == designaItem.getIdJuzgadoActu().length - 1) {
								turnoIN = turnoIN + turno;
							} else {
								turnoIN = turnoIN + turno + " ,";
							}
						}
						sql += " AND act.idjuzgado IN (" + turnoIN + " )";
					}
				}
				/*if (tiene_asunto) {
					sql += " AND des.RESUMENASUNTO = '" + designaItem.getAsunto().trim() + "' ";
				}*/
				if (tiene_acreditacion) {
					if (designaItem.getIdAcreditacion().toString().indexOf(',') != -1) {
						sql += " AND act.idacreditacion = " + designaItem.getIdAcreditacion()[0];
					} else {
						String turnoIN = "";
						for (int i = 0; i < designaItem.getIdAcreditacion().length; i++) {
							String turno = designaItem.getIdAcreditacion()[i];

							if (i == designaItem.getIdAcreditacion().length - 1) {
								turnoIN = turnoIN + turno;
							} else {
								turnoIN = turnoIN + turno + " ,";
							}
						}
						sql += " AND act.idacreditacion IN (" + turnoIN + " )";
					}
				}

				if (designaItem.getIdProcedimientoActuaciones() != null
						&& designaItem.getIdProcedimientoActuaciones().length > 0) {
					if (designaItem.getIdProcedimientoActuaciones().length == 1) {
						sql += " AND act.IDPRETENSION = '" + designaItem.getIdProcedimientoActuaciones()[0] + "'";
					} else {
						String estadoIN = "";
						for (int i = 0; i < designaItem.getIdProcedimientoActuaciones().length; i++) {
							String estado = designaItem.getIdProcedimientoActuaciones()[i];
							if (i == designaItem.getIdProcedimientoActuaciones().length - 1) {
								estadoIN = estadoIN + "'" + estado + "'";
							} else {
								estadoIN = estadoIN + "'" + estado + "'" + " ,";
							}
						}
						sql += " AND act.IDPRETENSION IN (" + estadoIN + " )";
					}

				}
				if (tiene_origen) {
					if (designaItem.getIdOrigen().equalsIgnoreCase("COLEGIO")) {
						sql += " AND act.USUCREACION <> ";
					} else {
						sql += " AND act.USUCREACION = ";
					}
					sql += "  (SELECT U.IDUSUARIO " + "    FROM CEN_PERSONA P,  ADM_USUARIOS U " + "    WHERE      "
							+ "       U.NIF = P.NIFCIF " + "       AND U.IDINSTITUCION = act.IDINSTITUCION "
							+ "       AND P.IDPERSONA = act.IDPERSONACOLEGIADO) ";

				}
			}
			// jbd // inc7744 // Cambiamos el order by porque parece que afecta a la query
			// cuando se busca por colegiado
			//sql += " order by des.anio desc, des.NUMERO desc";
			
			sql += "  order by des.idturno, des.anio desc, des.codigo desc ";
			
			// No utilizamos la clase Paginador para la busqueda de letrados porque al
			// filtrar por residencia la sql no devolvia bien los
			// datos que eran de tipo varchar (devolvía n veces el mismo resultado),
			// utilizamos el paginador PaginadorCaseSensitive
			// y hacemos a parte el tratamiento de mayusculas y signos de acentuación
			
			
			if (tamMaximo != null) { 
				Integer tamMaxNumber = tamMaximo;
				sql += "FETCH FIRST " + tamMaxNumber + " ROWS ONLY";
			}
		} catch (Exception e) {
			throw e;
		}

		//LOGGER.info("+++++ [SIGA TEST] - query busquedaDesignaciones() --> " + sql.toString());
		return sql;
	}
	
	public String busquedaDesignaciones2(DesignaItem designaItem, Short idInstitucion, Integer tamMaximo) throws Exception {
		SQL sql = new SQL();
		
		SQL sqlDefendidos = new SQL();

		Hashtable codigosBind = new Hashtable();
		int contador = 0;
		// Acceso a BBDD
		
		sqlDefendidos.SELECT("NVL(PER.APELLIDO1, '') || ' ' || NVL(PER.APELLIDO2, '') || ', ' || NVL(PER.NOMBRE, '')");
		sqlDefendidos.FROM("scs_defendidosdesigna ded");
		sqlDefendidos.JOIN("scs_personajg per ON ded.idinstitucion = per.idinstitucion	AND ded.idpersona = per.idpersona");
		sqlDefendidos.WHERE("calidad IS NOT NULL");
		sqlDefendidos.WHERE("ded.anio = des.anio");
		sqlDefendidos.WHERE("ded.numero = des.numero");
		sqlDefendidos.WHERE("ded.idinstitucion = des.idinstitucion");
		sqlDefendidos.WHERE("ded.idturno = des.idturno");
		sqlDefendidos.WHERE("rownum < 2");

		// aalg. INC_06694_SIGA. Se modifica la query para hacerla más eficiente
		try {
			sql.SELECT_DISTINCT("des.anio anio, des.numero numero, des.idturno idturno, des.codigo codigo, des.idinstitucion idinstitucion");
			
			sql.FROM("scs_designa des"); 

			boolean tiene_juzg = designaItem.getIdJuzgadoActu() != null
					&& designaItem.getIdJuzgadoActu().length >0;
			boolean tiene_asunto = designaItem.getAsunto() != null && !designaItem.getAsunto().equalsIgnoreCase("");
			boolean tiene_acreditacion = designaItem.getIdAcreditacion() != null
					&& (designaItem.getIdAcreditacion().length != 0);
			boolean tiene_modulo = designaItem.getIdModuloActuaciones() != null && designaItem.getIdModuloActuaciones().length >0;
			boolean tiene_documentacion = designaItem.getDocumentacionActuacion()!= null && designaItem.getDocumentacionActuacion().length() >0;
			boolean tiene_procActuacion = designaItem.getIdProcedimientoActuaciones()!=null && designaItem.getIdProcedimientoActuaciones().length>0;
			boolean tiene_fechaJustificacionDesde = designaItem.getFechaJustificacionDesde() != null
					&& designaItem.getFechaJustificacionDesde().toString() != null
					&& !designaItem.getFechaJustificacionDesde().toString().equalsIgnoreCase("");
			boolean tiene_fechaJustificacionHasta = designaItem.getFechaJustificacionHasta() != null
					&& designaItem.getFechaJustificacionHasta().toString() != null
					&& !designaItem.getFechaJustificacionHasta().toString().equalsIgnoreCase("");
			boolean tiene_origen = designaItem.getIdOrigen() != null && !designaItem.getIdOrigen().equalsIgnoreCase("");
			boolean tiene_actuacionesV = designaItem.getIdActuacionesV() != null
					&& !designaItem.getIdActuacionesV().equalsIgnoreCase("");
			boolean tiene_moduloDesignacion = (designaItem.getIdModulos() != null
					&& designaItem.getIdModulos().length > 0);
			boolean tienePretensionesDesignacion = (designaItem.getIdProcedimientos() != null
					&& designaItem.getIdProcedimientos().length > 0);

			if(tiene_modulo||tiene_documentacion||tiene_fechaJustificacionDesde||tiene_fechaJustificacionHasta||tiene_juzg||tiene_acreditacion||tiene_origen) {
				sql.JOIN("scs_actuaciondesigna act ON act.idturno = des.idturno" +
												" and act.idinstitucion = des.idinstitucion " +
												" and act.anio = des.anio " +
												" and act.numero = des.numero ");
			}
			
			boolean tiene_interesado = false;
			if ((designaItem.getNif() != null && !designaItem.getNif().equalsIgnoreCase(""))
					|| (designaItem.getNombreInteresado() != null
							&& !designaItem.getNombreInteresado().equalsIgnoreCase(""))
					|| (designaItem.getApellidosInteresado() != null
							&& !designaItem.getApellidosInteresado().equalsIgnoreCase(""))) {
				tiene_interesado = true;
			}


//			if (tienePretensionesDesignacion) {
//				sql.JOIN("SCS_PRETENSION pret ON des.idinstitucion = pret.idinstitucion AND des.idpretension = pret.idpretension");
//			}

			if (idInstitucion != null) {
				sql.WHERE("des.IDINSTITUCION = " + idInstitucion );
			}

			

			//if (designaItem.getNumColegiado() != null && !(String.valueOf(designaItem.getNumColegiado())).equals("")) {
				

//				sql += " and l.idpersona = " + String.valueOf(designaItem.getNumColegiado()) + " ";
			//}
			if (designaItem.getNumColegiado() != null && !(String.valueOf(designaItem.getNumColegiado())).equals("")) {
				sql.LEFT_OUTER_JOIN("scs_designasletrado l ON l.anio = des.anio" + 
														" AND l.numero = des.numero" + 
														" AND l.idinstitucion = des.idinstitucion" + 
														" AND l.idturno = des.idturno"); 
				sql.LEFT_OUTER_JOIN("cen_colegiado colegiado ON colegiado.idinstitucion = l.idinstitucion" + 
														" AND colegiado.idpersona = l.idpersona"); 
				sql.LEFT_OUTER_JOIN("cen_persona persona ON l.idpersona = persona.idpersona");
				sql.WHERE(" (l.Fechadesigna is null or" +
							" l.Fechadesigna = (SELECT MAX(LET2.Fechadesigna) FROM SCS_DESIGNASLETRADO LET2" +
												" WHERE l.IDINSTITUCION = LET2.IDINSTITUCION AND l.IDTURNO = LET2.IDTURNO" +
												" AND l.ANIO = LET2.ANIO AND l.NUMERO = LET2.NUMERO" +
												" AND TRUNC(LET2.Fechadesigna) <= TRUNC(SYSDATE)))");
				sql.WHERE(" colegiado.ncolegiado = " + String.valueOf(designaItem.getNumColegiado()));
			}

			if (designaItem.getIdTurnos() != null
					&& (String.valueOf(designaItem.getIdTurnos()) != "-1" && designaItem.getIdTurnos().length != 0)
					&& !String.valueOf(designaItem.getIdTurnos()).equals("")) {
				
				if (designaItem.getIdTurnos().length == 1) {
					sql.WHERE("des.idTurno = " + designaItem.getIdTurnos()[0]);
				} else {
					String turnoIN = "";
					for (int i = 0; i < designaItem.getIdTurnos().length; i++) {
						String turno = designaItem.getIdTurnos()[i];
						if (i == designaItem.getIdTurnos().length - 1) {
							turnoIN = turnoIN + turno;
						} else {
							turnoIN = turnoIN + turno + " ,";
						}
					}
					sql.WHERE("des.idTurno IN (" + turnoIN + " )");
				}
			}

			if (designaItem.getAno() != 0 && String.valueOf(designaItem.getAno()) != null
					&& !String.valueOf(designaItem.getAno()).equalsIgnoreCase("")) {

				if (String.valueOf(designaItem.getAno()).indexOf('*') >= 0) {

					contador++;
					sql.WHERE(prepararSentenciaCompletaBind(String.valueOf(designaItem.getAno()).trim(),
							"des.anio", contador, codigosBind));

				} else if (designaItem.getAno() != 0) {
					contador++;
					sql.WHERE("des.anio = " + String.valueOf(designaItem.getAno()));
				}
			}

			if (designaItem.getCodigo() != null && !designaItem.getCodigo().equalsIgnoreCase("")) {

				if ((designaItem.getCodigo().indexOf(',') != -1) && (designaItem.getCodigo().indexOf('-') == -1)) {
					String[] parts = designaItem.getCodigo().split(",");
					String whereCodigo = " (des.codigo = ";
					for (int i = 0; i < parts.length; i++) {
						if (i == parts.length - 1) {
							whereCodigo += parts[i].trim() + ")";
						} else {
							whereCodigo += parts[i].trim() + " OR des.codigo = ";
						}
					}
					sql.WHERE(whereCodigo);
				} else if ((designaItem.getCodigo().indexOf('-') != -1)
						&& (designaItem.getCodigo().indexOf(',') == -1)) {
					String[] parts = designaItem.getCodigo().split("-");
					if (parts.length == 2) {
						sql.WHERE(" des.codigo BETWEEN " + parts[0] + " AND " + parts[1]);
					}
				} else if ((designaItem.getCodigo().indexOf('-') == -1)
						&& (designaItem.getCodigo().indexOf(',') == -1)) {
					sql.WHERE(" ltrim(des.codigo,'0') = ltrim(" + designaItem.getCodigo() + ",'0')");
				}
			}
			if (designaItem.getIdJuzgados() != null && designaItem.getIdJuzgados().length > 0) {
				if (designaItem.getIdJuzgados().length == 1) {
					sql.WHERE(" des.idjuzgado = " + designaItem.getIdJuzgados()[0]);
				} else {
					String juzgadoIN = "";
					for (int i = 0; i < designaItem.getIdJuzgados().length; i++) {
						String juzgado = designaItem.getIdJuzgados()[i];
						if (i == designaItem.getIdJuzgados().length - 1) {
							juzgadoIN = juzgadoIN + juzgado;
						} else {
							juzgadoIN = juzgadoIN + juzgado + " ,";
						}
					}
					sql.WHERE(" des.idjuzgado IN (" + juzgadoIN + " )");
				}
			}
			
			if (designaItem.getIdModulos() != null && designaItem.getIdModulos().length > 0) {
				if (designaItem.getIdModulos().length == 1) {
					sql.WHERE(" des.IDPROCEDIMIENTO = '" + designaItem.getIdModulos()[0] + "'");
				} else {
					String estadoIN = "";
					for (int i = 0; i < designaItem.getIdModulos().length; i++) {
						String estado = designaItem.getIdModulos()[i];
						if (i == designaItem.getIdModulos().length - 1) {
							estadoIN = estadoIN + "'" + estado + "'";
						} else {
							estadoIN = estadoIN + "'" + estado + "'" + " ,";
						}
					}
					sql.WHERE(" des.IDPROCEDIMIENTO IN (" + estadoIN + " )");
				}

			}
			
			if (tiene_asunto) {
				contador++;
				codigosBind.put(new Integer(contador), designaItem.getAsunto().trim());
				sql.WHERE(" regexp_like(des.resumenasunto,'" + designaItem.getAsunto() + "')");
			}

			if (designaItem.getIdModuloActuaciones() != null && designaItem.getIdModuloActuaciones().length > 0) {
				if (designaItem.getIdModuloActuaciones().length == 1) {
					sql.WHERE(" act.IDPROCEDIMIENTO = '" + designaItem.getIdModuloActuaciones()[0] + "'");
				} else {
					String estadoIN = "";
					for (int i = 0; i < designaItem.getIdModuloActuaciones().length; i++) {
						String estado = designaItem.getIdModuloActuaciones()[i];
						if (i == designaItem.getIdModuloActuaciones().length - 1) {
							estadoIN = estadoIN + "'" + estado + "'";
						} else {
							estadoIN = estadoIN + "'" + estado + "'" + " ,";
						}
					}
					sql.WHERE(" act.IDPROCEDIMIENTO IN (" + estadoIN + " )");
				}

			}

			if (designaItem.getEstados() != null && designaItem.getEstados().length > 0) {
				if (designaItem.getEstados().length == 1) {
					sql.WHERE("des.estado = '" + designaItem.getEstados()[0] + "'");
				} else {
					String estadoIN = "";
					for (int i = 0; i < designaItem.getEstados().length; i++) {
						String estado = designaItem.getEstados()[i];
						if (i == designaItem.getEstados().length - 1) {
							estadoIN = estadoIN + "'" + estado + "'";
						} else {
							estadoIN = estadoIN + "'" + estado + "'" + " ,";
						}
					}
					sql.WHERE(" des.estado IN (" + estadoIN + " )");
				}

			}
			if (designaItem.getNumProcedimiento() != null && !designaItem.getNumProcedimiento().equalsIgnoreCase("")) {
				contador++;
				codigosBind.put(new Integer(contador), designaItem.getNumProcedimiento().trim());
				sql.WHERE(" regexp_like(des.numprocedimiento,'" + designaItem.getNumProcedimiento() + "') ");
			}
			if (designaItem.getNig() != null && !designaItem.getNig().equalsIgnoreCase("")) {
				contador++;
				codigosBind.put(new Integer(contador), designaItem.getNig().trim());
				sql.WHERE(" des.nig = '" + designaItem.getNig() + "'");
			}
			if (tienePretensionesDesignacion) {
				if (designaItem.getIdProcedimientos() != null && designaItem.getIdProcedimientos().length > 0) {
					if (designaItem.getIdProcedimientos().length == 1) {
						sql.WHERE("des.IDPRETENSION = '" + designaItem.getIdProcedimientos()[0] + "'");
					} else {
						String estadoIN = "";
						for (int i = 0; i < designaItem.getIdProcedimientos().length; i++) {
							String estado = designaItem.getIdProcedimientos()[i];
							if (i == designaItem.getIdProcedimientos().length - 1) {
								estadoIN = estadoIN + "'" + estado + "'";
							} else {
								estadoIN = estadoIN + "'" + estado + "'" + " ,";
							}
						}
						sql.WHERE("des.IDPRETENSION IN (" + estadoIN + " )");
					}

				}
			}
			
			if(designaItem.getIdActuacionesV()!=null && !designaItem.getIdActuacionesV().trim().isEmpty()){
				if("SINACTUACIONES".equalsIgnoreCase(designaItem.getIdActuacionesV().trim())){
					sql.WHERE(" F_SIGA_ACTUACIONESDESIG(des.IDINSTITUCION,des.IDTURNO,des.ANIO,des.NUMERO) IS NULL ");
				}else{
				    sql.WHERE(" UPPER(F_SIGA_ACTUACIONESDESIG(des.IDINSTITUCION,des.IDTURNO,des.ANIO,des.NUMERO))=UPPER('"+designaItem.getIdActuacionesV()+"')");
				}
			}

			if (designaItem.getDocumentacionActuacion() != null
					&& !designaItem.getDocumentacionActuacion().equalsIgnoreCase("")) {
				if (designaItem.getDocumentacionActuacion().equalsIgnoreCase("TODAS")) {
					sql.WHERE("not exists (select 1 from scs_actuaciondesigna act "
							+ "and act.docjustificacion IS NULL or act.docjustificacion = 0) ");
				} else if (designaItem.getDocumentacionActuacion().equalsIgnoreCase("ALGUNAS")) {
					sql.WHERE(" exists (select 1 from scs_actuaciondesigna act "
							+ "and act.docjustificacion = 1) ");
				} else if (designaItem.getDocumentacionActuacion().equalsIgnoreCase("NINGUNA")) {
					sql.WHERE("not exists (select 1 from scs_actuaciondesigna act "
							+ "and act.docjustificacion = 1) ");
				}
			}

			// Mostrar ART 27
			String mostarArt27 = designaItem.getIdArt27();
			if (mostarArt27 != null && !mostarArt27.equalsIgnoreCase("") && !mostarArt27.equalsIgnoreCase("T")) {
				if (mostarArt27.equalsIgnoreCase("S")) {
					sql.WHERE(" des.art27 = 1");
				} else if (mostarArt27.equalsIgnoreCase("N")) {
					sql.WHERE(" des.art27 = 0");
				}
			}

			if (designaItem.getIdCalidad() != null && designaItem.getIdCalidad().length > 0) {
				sql.JOIN("scs_defendidosdesigna defen on defen.anio = des.anio" +
													" AND defen.numero = des.numero" +
													" AND defen.idinstitucion = des.idinstitucion" +
													" AND defen.idturno = des.idturno");
				if (designaItem.getIdCalidad().length == 1) {
					sql.WHERE(" defen.idtipoencalidad= " + designaItem.getIdCalidad()[0]);
				} else {
					String calidadIN = "";
					for (int i = 0; i < designaItem.getIdCalidad().length; i++) {
						String calidad = designaItem.getIdCalidad()[i];
						if (i == designaItem.getIdCalidad().length - 1) {
							calidadIN = calidadIN + "'" + calidad + "'";
						} else {
							calidadIN = calidadIN + calidad + " ,";
						}
					}
					sql.WHERE(" defen.idtipoencalidad IN (" + calidadIN + " )");
				}

			}

			if ((designaItem.getFechaEntradaInicio() != null
					&& !designaItem.getFechaEntradaInicio().toString().equalsIgnoreCase(""))
					|| (designaItem.getFechaEntradaFin() != null
							&& !designaItem.getFechaEntradaFin().toString().equalsIgnoreCase(""))) {

				DateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
				String fechaEntradaInicio ="";
				String fechaEntradaFin =""; 
				if(designaItem.getFechaEntradaInicio()!= null)
					fechaEntradaInicio = formatter1.format(designaItem.getFechaEntradaInicio());
				if(designaItem.getFechaEntradaFin()!= null)
					fechaEntradaFin = formatter1.format(designaItem.getFechaEntradaFin());
				if(designaItem.getFechaEntradaInicio()!= null && designaItem.getFechaEntradaFin()!= null) {
					sql.WHERE(" des.fechaentrada between TO_DATE('" + fechaEntradaInicio + "','DD/MM/RRRR') and TO_DATE('" + fechaEntradaFin +"','DD/MM/RRRR') ");
				}else if(designaItem.getFechaEntradaInicio()!= null) {
					sql.WHERE(" des.fechaentrada >=  TO_DATE('" + fechaEntradaInicio +"','DD/MM/RRRR') ");
				}else {
					sql.WHERE(" des.fechaentrada <=  TO_DATE('" + fechaEntradaFin +"','DD/MM/RRRR') ");
				}
				

			}
			if ((designaItem.getFechaJustificacionDesde() != null
					&& designaItem.getFechaJustificacionDesde().toString() != null
					&& !designaItem.getFechaJustificacionDesde().toString().equalsIgnoreCase(""))
					|| (designaItem.getFechaJustificacionHasta() != null
							&& designaItem.getFechaJustificacionHasta().toString() != null
							&& !designaItem.getFechaJustificacionHasta().toString().equalsIgnoreCase(""))) {
				DateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
				String fechaJustificacionDesde ="";
				String fechaJustificacionHasta =""; 
				if(designaItem.getFechaJustificacionDesde()!= null)
					fechaJustificacionDesde = formatter1.format(designaItem.getFechaJustificacionDesde());
				if(designaItem.getFechaJustificacionHasta()!= null)
					fechaJustificacionHasta = formatter1.format(designaItem.getFechaJustificacionHasta());
				if(designaItem.getFechaJustificacionDesde()!= null && designaItem.getFechaJustificacionHasta()!= null) {
					sql.WHERE(" act.fechaJustificacion between TO_DATE('" + fechaJustificacionDesde + "','DD/MM/RRRR') and TO_DATE('" + fechaJustificacionHasta +"','DD/MM/RRRR') ");
				}else if(designaItem.getFechaJustificacionDesde()!= null) {
					sql.WHERE(" act.fechaJustificacion >=  TO_DATE('" + fechaJustificacionDesde +"','DD/MM/RRRR') ");
				}else {
					sql.WHERE(" act.fechaJustificacion <=  TO_DATE('" + fechaJustificacionHasta +"','DD/MM/RRRR') ");
				}

			}
			if (designaItem.getIdTipoDesignaColegios() != null
					&& (!String.valueOf(designaItem.getIdTipoDesignaColegios()).equalsIgnoreCase(""))
					&& designaItem.getIdTipoDesignaColegios().length > 0) {
				if (designaItem.getIdTipoDesignaColegios().length == 1) {
					sql.WHERE(" des.IDTIPODESIGNACOLEGIO = " + designaItem.getIdTipoDesignaColegios()[0]);
				} else {
					String turnoIN = "";
					for (int i = 0; i < designaItem.getIdTipoDesignaColegios().length; i++) {
						String turno = designaItem.getIdTipoDesignaColegios()[i];
						if (i == designaItem.getIdTipoDesignaColegios().length - 1) {
							turnoIN = turnoIN + turno;
						} else {
							turnoIN = turnoIN + turno + " ,";
						}
					}
					sql.WHERE(" des.IDTIPODESIGNACOLEGIO IN (" + turnoIN + " )");
				}
			}

			if (tiene_interesado) {

				if (designaItem.getNif() != null && !designaItem.getNif().equalsIgnoreCase("")) {
					sql.WHERE(" des.NUMERO IN (SELECT numero FROM SCS_DEFENDIDOSDESIGNA sd WHERE sd.IDTURNO = des.IDTURNO AND sd.ANIO = des.ANIO AND sd.IDPERSONA IN (SELECT IDPERSONA FROM SCS_PERSONAJG sp WHERE sp.NIF like '"+designaItem.getNif()+"%' AND IDINSTITUCION = "+idInstitucion+"))");
				}
				if (designaItem.getNombreInteresado() != null
						&& !designaItem.getNombreInteresado().equalsIgnoreCase("")) {
					sql.WHERE(" des.NUMERO IN (SELECT numero FROM SCS_DEFENDIDOSDESIGNA sd WHERE sd.IDTURNO = des.IDTURNO AND sd.ANIO = des.ANIO AND sd.IDPERSONA IN (SELECT IDPERSONA FROM SCS_PERSONAJG sp WHERE UPPER(sp.NOMBRE) like UPPER('%"+designaItem.getNombreInteresado()+"%') AND IDINSTITUCION = "+idInstitucion+"))");
				}
				if (designaItem.getApellidosInteresado() != null
						&& !designaItem.getApellidosInteresado().equalsIgnoreCase("")) {
					sql.WHERE(" des.NUMERO IN (SELECT numero FROM SCS_DEFENDIDOSDESIGNA sd WHERE sd.IDTURNO = des.IDTURNO AND sd.ANIO = des.ANIO AND sd.IDPERSONA IN (SELECT IDPERSONA FROM SCS_PERSONAJG sp WHERE UPPER(sp.APELLIDO1 || ' ' ||sp.APELLIDO2) LIKE UPPER('%"+designaItem.getApellidosInteresado()+"%') AND sp.IDINSTITUCION = "+idInstitucion+"))");
				}
			}

			if (tiene_juzg || tiene_asunto || tiene_acreditacion || tiene_modulo || tiene_fechaJustificacionDesde
					|| tiene_fechaJustificacionHasta || tiene_origen) {
				
				if (tiene_juzg) {
					String a[] = (String.valueOf(designaItem.getIdJuzgadoActu())).split(",");
					if (designaItem.getIdJuzgadoActu().length == 1) {
						sql.WHERE(" act.idjuzgado = " + designaItem.getIdJuzgadoActu()[0]);
					} else {
						String turnoIN = "";
						for (int i = 0; i < designaItem.getIdJuzgadoActu().length; i++) {
							String turno = designaItem.getIdJuzgadoActu()[i];
							if (i == designaItem.getIdJuzgadoActu().length - 1) {
								turnoIN = turnoIN + turno;
							} else {
								turnoIN = turnoIN + turno + " ,";
							}
						}
						sql.WHERE(" act.idjuzgado IN (" + turnoIN + " )");
					}
				}
				/*if (tiene_asunto) {
					sql += " AND des.RESUMENASUNTO = '" + designaItem.getAsunto().trim() + "' ";
				}*/
				if (tiene_acreditacion) {
					if (designaItem.getIdAcreditacion().toString().indexOf(',') != -1) {
						sql.WHERE(" act.idacreditacion = " + designaItem.getIdAcreditacion()[0]);
					} else {
						String turnoIN = "";
						for (int i = 0; i < designaItem.getIdAcreditacion().length; i++) {
							String turno = designaItem.getIdAcreditacion()[i];

							if (i == designaItem.getIdAcreditacion().length - 1) {
								turnoIN = turnoIN + turno;
							} else {
								turnoIN = turnoIN + turno + " ,";
							}
						}
						sql.WHERE(" act.idacreditacion IN (" + turnoIN + " )");
					}
				}

				if (designaItem.getIdProcedimientoActuaciones() != null
						&& designaItem.getIdProcedimientoActuaciones().length > 0) {
					if (designaItem.getIdProcedimientoActuaciones().length == 1) {
						sql.WHERE(" act.IDPRETENSION = '" + designaItem.getIdProcedimientoActuaciones()[0] + "'");
					} else {
						String estadoIN = "";
						for (int i = 0; i < designaItem.getIdProcedimientoActuaciones().length; i++) {
							String estado = designaItem.getIdProcedimientoActuaciones()[i];
							if (i == designaItem.getIdProcedimientoActuaciones().length - 1) {
								estadoIN = estadoIN + "'" + estado + "'";
							} else {
								estadoIN = estadoIN + "'" + estado + "'" + " ,";
							}
						}
						sql.WHERE(" act.IDPRETENSION IN (" + estadoIN + " )");
					}

				}
				if (tiene_origen) {
					if (designaItem.getIdOrigen().equalsIgnoreCase("COLEGIO")) {
						sql.WHERE(" act.USUCREACION <> ");
					} else {
						sql.WHERE(" act.USUCREACION = ");
					}
					sql.WHERE("  (SELECT U.IDUSUARIO " + "    FROM CEN_PERSONA P,  ADM_USUARIOS U " + "    WHERE      "
							+ "       U.NIF = P.NIFCIF " + "       AND U.IDINSTITUCION = act.IDINSTITUCION "
							+ "       AND P.IDPERSONA = act.IDPERSONACOLEGIADO) ");

				}
			}
			// jbd // inc7744 // Cambiamos el order by porque parece que afecta a la query
			// cuando se busca por colegiado
			//sql += " order by des.anio desc, des.NUMERO desc";
			
			sql.ORDER_BY("des.anio desc, des.codigo desc ");
			
			// No utilizamos la clase Paginador para la busqueda de letrados porque al
			// filtrar por residencia la sql no devolvia bien los
			// datos que eran de tipo varchar (devolvía n veces el mismo resultado),
			// utilizamos el paginador PaginadorCaseSensitive
			// y hacemos a parte el tratamiento de mayusculas y signos de acentuación
			
			
			if (tamMaximo != null) { 
				Integer tamMaxNumber = tamMaximo;
				sql.FETCH_FIRST_ROWS_ONLY(tamMaxNumber);
			}
		} catch (Exception e) {
			throw e;
		}

		//LOGGER.info("+++++ [SIGA TEST] - query busquedaDesignaciones() --> " + sql.toString());
		return sql.toString();
	}
	
	public String busquedaTotalRegistrosDesignaciones(DesignaItem designaItem, Short idInstitucion) throws Exception {
		SQL sql = new SQL();
		
		SQL sqlDefendidos = new SQL();

		Hashtable codigosBind = new Hashtable();
		int contador = 0;
		// Acceso a BBDD
		
		sqlDefendidos.SELECT("NVL(PER.APELLIDO1, '') || ' ' || NVL(PER.APELLIDO2, '') || ', ' || NVL(PER.NOMBRE, '')");
		sqlDefendidos.FROM("scs_defendidosdesigna ded");
		sqlDefendidos.JOIN("scs_personajg per ON ded.idinstitucion = per.idinstitucion	AND ded.idpersona = per.idpersona");
		sqlDefendidos.WHERE("calidad IS NOT NULL");
		sqlDefendidos.WHERE("ded.anio = des.anio");
		sqlDefendidos.WHERE("ded.numero = des.numero");
		sqlDefendidos.WHERE("ded.idinstitucion = des.idinstitucion");
		sqlDefendidos.WHERE("ded.idturno = des.idturno");
		sqlDefendidos.WHERE("rownum < 2");

		// aalg. INC_06694_SIGA. Se modifica la query para hacerla más eficiente
		try {
			sql.SELECT_DISTINCT("COUNT(*) AS TOTAL");
			
			sql.FROM("scs_designa des"); 

			boolean tiene_juzg = designaItem.getIdJuzgadoActu() != null
					&& designaItem.getIdJuzgadoActu().length >0;
			boolean tiene_asunto = designaItem.getAsunto() != null && !designaItem.getAsunto().equalsIgnoreCase("");
			boolean tiene_acreditacion = designaItem.getIdAcreditacion() != null
					&& (designaItem.getIdAcreditacion().length != 0);
			boolean tiene_modulo = designaItem.getIdModuloActuaciones() != null && designaItem.getIdModuloActuaciones().length >0;
			boolean tiene_documentacion = designaItem.getDocumentacionActuacion()!= null && designaItem.getDocumentacionActuacion().length() >0;
			boolean tiene_procActuacion = designaItem.getIdProcedimientoActuaciones()!=null && designaItem.getIdProcedimientoActuaciones().length>0;
			boolean tiene_fechaJustificacionDesde = designaItem.getFechaJustificacionDesde() != null
					&& designaItem.getFechaJustificacionDesde().toString() != null
					&& !designaItem.getFechaJustificacionDesde().toString().equalsIgnoreCase("");
			boolean tiene_fechaJustificacionHasta = designaItem.getFechaJustificacionHasta() != null
					&& designaItem.getFechaJustificacionHasta().toString() != null
					&& !designaItem.getFechaJustificacionHasta().toString().equalsIgnoreCase("");
			boolean tiene_origen = designaItem.getIdOrigen() != null && !designaItem.getIdOrigen().equalsIgnoreCase("");
			boolean tiene_actuacionesV = designaItem.getIdActuacionesV() != null
					&& !designaItem.getIdActuacionesV().equalsIgnoreCase("");
			boolean tiene_moduloDesignacion = (designaItem.getIdModulos() != null
					&& designaItem.getIdModulos().length > 0);
			boolean tienePretensionesDesignacion = (designaItem.getIdProcedimientos() != null
					&& designaItem.getIdProcedimientos().length > 0);

			if(tiene_modulo||tiene_documentacion||tiene_fechaJustificacionDesde||tiene_fechaJustificacionHasta||tiene_juzg||tiene_acreditacion||tiene_origen) {
				sql.JOIN("scs_actuaciondesigna act ON act.idturno = des.idturno" +
												" and act.idinstitucion = des.idinstitucion " +
												" and act.anio = des.anio " +
												" and act.numero = des.numero ");
			}
			
			boolean tiene_interesado = false;
			if ((designaItem.getNif() != null && !designaItem.getNif().equalsIgnoreCase(""))
					|| (designaItem.getNombreInteresado() != null
							&& !designaItem.getNombreInteresado().equalsIgnoreCase(""))
					|| (designaItem.getApellidosInteresado() != null
							&& !designaItem.getApellidosInteresado().equalsIgnoreCase(""))) {
				tiene_interesado = true;
			}


//			if (tienePretensionesDesignacion) {
//				sql.JOIN("SCS_PRETENSION pret ON des.idinstitucion = pret.idinstitucion AND des.idpretension = pret.idpretension");
//			}

			if (idInstitucion != null) {
				sql.WHERE("des.IDINSTITUCION = " + idInstitucion );
			}

			

			//if (designaItem.getNumColegiado() != null && !(String.valueOf(designaItem.getNumColegiado())).equals("")) {
				

//				sql += " and l.idpersona = " + String.valueOf(designaItem.getNumColegiado()) + " ";
			//}
			if (designaItem.getNumColegiado() != null && !(String.valueOf(designaItem.getNumColegiado())).equals("")) {
				sql.LEFT_OUTER_JOIN("scs_designasletrado l ON l.anio = des.anio" + 
														" AND l.numero = des.numero" + 
														" AND l.idinstitucion = des.idinstitucion" + 
														" AND l.idturno = des.idturno"); 
				sql.LEFT_OUTER_JOIN("cen_colegiado colegiado ON colegiado.idinstitucion = l.idinstitucion" + 
														" AND colegiado.idpersona = l.idpersona"); 
				sql.LEFT_OUTER_JOIN("cen_persona persona ON l.idpersona = persona.idpersona");
				sql.WHERE(" (l.Fechadesigna is null or" +
							" l.Fechadesigna = (SELECT MAX(LET2.Fechadesigna) FROM SCS_DESIGNASLETRADO LET2" +
												" WHERE l.IDINSTITUCION = LET2.IDINSTITUCION AND l.IDTURNO = LET2.IDTURNO" +
												" AND l.ANIO = LET2.ANIO AND l.NUMERO = LET2.NUMERO" +
												" AND TRUNC(LET2.Fechadesigna) <= TRUNC(SYSDATE)))");
				sql.WHERE(" colegiado.ncolegiado = " + String.valueOf(designaItem.getNumColegiado()));
			}

			if (designaItem.getIdTurnos() != null
					&& (String.valueOf(designaItem.getIdTurnos()) != "-1" && designaItem.getIdTurnos().length != 0)
					&& !String.valueOf(designaItem.getIdTurnos()).equals("")) {
				
				if (designaItem.getIdTurnos().length == 1) {
					sql.WHERE("des.idTurno = " + designaItem.getIdTurnos()[0]);
				} else {
					String turnoIN = "";
					for (int i = 0; i < designaItem.getIdTurnos().length; i++) {
						String turno = designaItem.getIdTurnos()[i];
						if (i == designaItem.getIdTurnos().length - 1) {
							turnoIN = turnoIN + turno;
						} else {
							turnoIN = turnoIN + turno + " ,";
						}
					}
					sql.WHERE("des.idTurno IN (" + turnoIN + " )");
				}
			}

			if (designaItem.getAno() != 0 && String.valueOf(designaItem.getAno()) != null
					&& !String.valueOf(designaItem.getAno()).equalsIgnoreCase("")) {

				if (String.valueOf(designaItem.getAno()).indexOf('*') >= 0) {

					contador++;
					sql.WHERE(prepararSentenciaCompletaBind(String.valueOf(designaItem.getAno()).trim(),
							"des.anio", contador, codigosBind));

				} else if (designaItem.getAno() != 0) {
					contador++;
					sql.WHERE("des.anio = " + String.valueOf(designaItem.getAno()));
				}
			}

			if (designaItem.getCodigo() != null && !designaItem.getCodigo().equalsIgnoreCase("")) {

				if ((designaItem.getCodigo().indexOf(',') != -1) && (designaItem.getCodigo().indexOf('-') == -1)) {
					String[] parts = designaItem.getCodigo().split(",");
					String whereCodigo = " (des.codigo = ";
					for (int i = 0; i < parts.length; i++) {
						if (i == parts.length - 1) {
							whereCodigo += parts[i].trim() + ")";
						} else {
							whereCodigo += parts[i].trim() + " OR des.codigo = ";
						}
					}
					sql.WHERE(whereCodigo);
				} else if ((designaItem.getCodigo().indexOf('-') != -1)
						&& (designaItem.getCodigo().indexOf(',') == -1)) {
					String[] parts = designaItem.getCodigo().split("-");
					if (parts.length == 2) {
						sql.WHERE(" des.codigo BETWEEN " + parts[0] + " AND " + parts[1]);
					}
				} else if ((designaItem.getCodigo().indexOf('-') == -1)
						&& (designaItem.getCodigo().indexOf(',') == -1)) {
					sql.WHERE(" ltrim(des.codigo,'0') = ltrim(" + designaItem.getCodigo() + ",'0')");
				}
			}
			if (designaItem.getIdJuzgados() != null && designaItem.getIdJuzgados().length > 0) {
				if (designaItem.getIdJuzgados().length == 1) {
					sql.WHERE(" des.idjuzgado = " + designaItem.getIdJuzgados()[0]);
				} else {
					String juzgadoIN = "";
					for (int i = 0; i < designaItem.getIdJuzgados().length; i++) {
						String juzgado = designaItem.getIdJuzgados()[i];
						if (i == designaItem.getIdJuzgados().length - 1) {
							juzgadoIN = juzgadoIN + juzgado;
						} else {
							juzgadoIN = juzgadoIN + juzgado + " ,";
						}
					}
					sql.WHERE(" des.idjuzgado IN (" + juzgadoIN + " )");
				}
			}
			
			if (designaItem.getIdModulos() != null && designaItem.getIdModulos().length > 0) {
				if (designaItem.getIdModulos().length == 1) {
					sql.WHERE(" des.IDPROCEDIMIENTO = '" + designaItem.getIdModulos()[0] + "'");
				} else {
					String estadoIN = "";
					for (int i = 0; i < designaItem.getIdModulos().length; i++) {
						String estado = designaItem.getIdModulos()[i];
						if (i == designaItem.getIdModulos().length - 1) {
							estadoIN = estadoIN + "'" + estado + "'";
						} else {
							estadoIN = estadoIN + "'" + estado + "'" + " ,";
						}
					}
					sql.WHERE(" des.IDPROCEDIMIENTO IN (" + estadoIN + " )");
				}

			}
			
			if (tiene_asunto) {
				contador++;
				codigosBind.put(new Integer(contador), designaItem.getAsunto().trim());
				sql.WHERE(" regexp_like(des.resumenasunto,'" + designaItem.getAsunto() + "')");
			}

			if (designaItem.getIdModuloActuaciones() != null && designaItem.getIdModuloActuaciones().length > 0) {
				if (designaItem.getIdModuloActuaciones().length == 1) {
					sql.WHERE(" act.IDPROCEDIMIENTO = '" + designaItem.getIdModuloActuaciones()[0] + "'");
				} else {
					String estadoIN = "";
					for (int i = 0; i < designaItem.getIdModuloActuaciones().length; i++) {
						String estado = designaItem.getIdModuloActuaciones()[i];
						if (i == designaItem.getIdModuloActuaciones().length - 1) {
							estadoIN = estadoIN + "'" + estado + "'";
						} else {
							estadoIN = estadoIN + "'" + estado + "'" + " ,";
						}
					}
					sql.WHERE(" act.IDPROCEDIMIENTO IN (" + estadoIN + " )");
				}

			}

			if (designaItem.getEstados() != null && designaItem.getEstados().length > 0) {
				if (designaItem.getEstados().length == 1) {
					sql.WHERE("des.estado = '" + designaItem.getEstados()[0] + "'");
				} else {
					String estadoIN = "";
					for (int i = 0; i < designaItem.getEstados().length; i++) {
						String estado = designaItem.getEstados()[i];
						if (i == designaItem.getEstados().length - 1) {
							estadoIN = estadoIN + "'" + estado + "'";
						} else {
							estadoIN = estadoIN + "'" + estado + "'" + " ,";
						}
					}
					sql.WHERE(" des.estado IN (" + estadoIN + " )");
				}

			}
			if (designaItem.getNumProcedimiento() != null && !designaItem.getNumProcedimiento().equalsIgnoreCase("")) {
				contador++;
				codigosBind.put(new Integer(contador), designaItem.getNumProcedimiento().trim());
				sql.WHERE(" regexp_like(des.numprocedimiento,'" + designaItem.getNumProcedimiento() + "') ");
			}
			if (designaItem.getNig() != null && !designaItem.getNig().equalsIgnoreCase("")) {
				contador++;
				codigosBind.put(new Integer(contador), designaItem.getNig().trim());
				sql.WHERE(" des.nig = '" + designaItem.getNig() + "'");
			}
			if (tienePretensionesDesignacion) {
				if (designaItem.getIdProcedimientos() != null && designaItem.getIdProcedimientos().length > 0) {
					if (designaItem.getIdProcedimientos().length == 1) {
						sql.WHERE("des.IDPRETENSION = '" + designaItem.getIdProcedimientos()[0] + "'");
					} else {
						String estadoIN = "";
						for (int i = 0; i < designaItem.getIdProcedimientos().length; i++) {
							String estado = designaItem.getIdProcedimientos()[i];
							if (i == designaItem.getIdProcedimientos().length - 1) {
								estadoIN = estadoIN + "'" + estado + "'";
							} else {
								estadoIN = estadoIN + "'" + estado + "'" + " ,";
							}
						}
						sql.WHERE("des.IDPRETENSION IN (" + estadoIN + " )");
					}

				}
			}
			
			if(designaItem.getIdActuacionesV()!=null && !designaItem.getIdActuacionesV().trim().isEmpty()){
				if("SINACTUACIONES".equalsIgnoreCase(designaItem.getIdActuacionesV().trim())){
					sql.WHERE(" F_SIGA_ACTUACIONESDESIG(des.IDINSTITUCION,des.IDTURNO,des.ANIO,des.NUMERO) IS NULL ");
				}else{
				    sql.WHERE(" UPPER(F_SIGA_ACTUACIONESDESIG(des.IDINSTITUCION,des.IDTURNO,des.ANIO,des.NUMERO))=UPPER('"+designaItem.getIdActuacionesV()+"')");
				}
			}

			if (designaItem.getDocumentacionActuacion() != null
					&& !designaItem.getDocumentacionActuacion().equalsIgnoreCase("")) {
				if (designaItem.getDocumentacionActuacion().equalsIgnoreCase("TODAS")) {
					sql.WHERE("not exists (select 1 from scs_actuaciondesigna act "
							+ "and act.docjustificacion IS NULL or act.docjustificacion = 0) ");
				} else if (designaItem.getDocumentacionActuacion().equalsIgnoreCase("ALGUNAS")) {
					sql.WHERE(" exists (select 1 from scs_actuaciondesigna act "
							+ "and act.docjustificacion = 1) ");
				} else if (designaItem.getDocumentacionActuacion().equalsIgnoreCase("NINGUNA")) {
					sql.WHERE("not exists (select 1 from scs_actuaciondesigna act "
							+ "and act.docjustificacion = 1) ");
				}
			}

			// Mostrar ART 27
			String mostarArt27 = designaItem.getIdArt27();
			if (mostarArt27 != null && !mostarArt27.equalsIgnoreCase("") && !mostarArt27.equalsIgnoreCase("T")) {
				if (mostarArt27.equalsIgnoreCase("S")) {
					sql.WHERE(" des.art27 = 1");
				} else if (mostarArt27.equalsIgnoreCase("N")) {
					sql.WHERE(" des.art27 = 0");
				}
			}

			if (designaItem.getIdCalidad() != null && designaItem.getIdCalidad().length > 0) {
				sql.JOIN("scs_defendidosdesigna defen on defen.anio = des.anio" +
													" AND defen.numero = des.numero" +
													" AND defen.idinstitucion = des.idinstitucion" +
													" AND defen.idturno = des.idturno");
				if (designaItem.getIdCalidad().length == 1) {
					sql.WHERE(" defen.idtipoencalidad= " + designaItem.getIdCalidad()[0]);
				} else {
					String calidadIN = "";
					for (int i = 0; i < designaItem.getIdCalidad().length; i++) {
						String calidad = designaItem.getIdCalidad()[i];
						if (i == designaItem.getIdCalidad().length - 1) {
							calidadIN = calidadIN + "'" + calidad + "'";
						} else {
							calidadIN = calidadIN + calidad + " ,";
						}
					}
					sql.WHERE(" defen.idtipoencalidad IN (" + calidadIN + " )");
				}

			}

			if ((designaItem.getFechaEntradaInicio() != null
					&& !designaItem.getFechaEntradaInicio().toString().equalsIgnoreCase(""))
					|| (designaItem.getFechaEntradaFin() != null
							&& !designaItem.getFechaEntradaFin().toString().equalsIgnoreCase(""))) {

				DateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
				String fechaEntradaInicio ="";
				String fechaEntradaFin =""; 
				if(designaItem.getFechaEntradaInicio()!= null)
					fechaEntradaInicio = formatter1.format(designaItem.getFechaEntradaInicio());
				if(designaItem.getFechaEntradaFin()!= null)
					fechaEntradaFin = formatter1.format(designaItem.getFechaEntradaFin());
				if(designaItem.getFechaEntradaInicio()!= null && designaItem.getFechaEntradaFin()!= null) {
					sql.WHERE(" des.fechaentrada between TO_DATE('" + fechaEntradaInicio + "','DD/MM/RRRR') and TO_DATE('" + fechaEntradaFin +"','DD/MM/RRRR') ");
				}else if(designaItem.getFechaEntradaInicio()!= null) {
					sql.WHERE(" des.fechaentrada >=  TO_DATE('" + fechaEntradaInicio +"','DD/MM/RRRR') ");
				}else {
					sql.WHERE(" des.fechaentrada <=  TO_DATE('" + fechaEntradaFin +"','DD/MM/RRRR') ");
				}
				

			}
			if ((designaItem.getFechaJustificacionDesde() != null
					&& designaItem.getFechaJustificacionDesde().toString() != null
					&& !designaItem.getFechaJustificacionDesde().toString().equalsIgnoreCase(""))
					|| (designaItem.getFechaJustificacionHasta() != null
							&& designaItem.getFechaJustificacionHasta().toString() != null
							&& !designaItem.getFechaJustificacionHasta().toString().equalsIgnoreCase(""))) {
				DateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
				String fechaJustificacionDesde ="";
				String fechaJustificacionHasta =""; 
				if(designaItem.getFechaJustificacionDesde()!= null)
					fechaJustificacionDesde = formatter1.format(designaItem.getFechaJustificacionDesde());
				if(designaItem.getFechaJustificacionHasta()!= null)
					fechaJustificacionHasta = formatter1.format(designaItem.getFechaJustificacionHasta());
				if(designaItem.getFechaJustificacionDesde()!= null && designaItem.getFechaJustificacionHasta()!= null) {
					sql.WHERE(" act.fechaJustificacion between TO_DATE('" + fechaJustificacionDesde + "','DD/MM/RRRR') and TO_DATE('" + fechaJustificacionHasta +"','DD/MM/RRRR') ");
				}else if(designaItem.getFechaJustificacionDesde()!= null) {
					sql.WHERE(" act.fechaJustificacion >=  TO_DATE('" + fechaJustificacionDesde +"','DD/MM/RRRR') ");
				}else {
					sql.WHERE(" act.fechaJustificacion <=  TO_DATE('" + fechaJustificacionHasta +"','DD/MM/RRRR') ");
				}

			}
			if (designaItem.getIdTipoDesignaColegios() != null
					&& (!String.valueOf(designaItem.getIdTipoDesignaColegios()).equalsIgnoreCase(""))
					&& designaItem.getIdTipoDesignaColegios().length > 0) {
				if (designaItem.getIdTipoDesignaColegios().length == 1) {
					sql.WHERE(" des.IDTIPODESIGNACOLEGIO = " + designaItem.getIdTipoDesignaColegios()[0]);
				} else {
					String turnoIN = "";
					for (int i = 0; i < designaItem.getIdTipoDesignaColegios().length; i++) {
						String turno = designaItem.getIdTipoDesignaColegios()[i];
						if (i == designaItem.getIdTipoDesignaColegios().length - 1) {
							turnoIN = turnoIN + turno;
						} else {
							turnoIN = turnoIN + turno + " ,";
						}
					}
					sql.WHERE(" des.IDTIPODESIGNACOLEGIO IN (" + turnoIN + " )");
				}
			}

			if (tiene_interesado) {

				if (designaItem.getNif() != null && !designaItem.getNif().equalsIgnoreCase("")) {
					sql.WHERE(" des.NUMERO IN (SELECT numero FROM SCS_DEFENDIDOSDESIGNA sd WHERE sd.IDTURNO = des.IDTURNO AND sd.ANIO = des.ANIO AND sd.IDPERSONA IN (SELECT IDPERSONA FROM SCS_PERSONAJG sp WHERE sp.NIF like '"+designaItem.getNif()+"%' AND IDINSTITUCION = "+idInstitucion+"))");
				}
				if (designaItem.getNombreInteresado() != null
						&& !designaItem.getNombreInteresado().equalsIgnoreCase("")) {
					sql.WHERE(" des.NUMERO IN (SELECT numero FROM SCS_DEFENDIDOSDESIGNA sd WHERE sd.IDTURNO = des.IDTURNO AND sd.ANIO = des.ANIO AND sd.IDPERSONA IN (SELECT IDPERSONA FROM SCS_PERSONAJG sp WHERE UPPER(sp.NOMBRE) like UPPER('%"+designaItem.getNombreInteresado()+"%') AND IDINSTITUCION = "+idInstitucion+"))");
				}
				if (designaItem.getApellidosInteresado() != null
						&& !designaItem.getApellidosInteresado().equalsIgnoreCase("")) {
					sql.WHERE(" des.NUMERO IN (SELECT numero FROM SCS_DEFENDIDOSDESIGNA sd WHERE sd.IDTURNO = des.IDTURNO AND sd.ANIO = des.ANIO AND sd.IDPERSONA IN (SELECT IDPERSONA FROM SCS_PERSONAJG sp WHERE UPPER(sp.APELLIDO1 || ' ' ||sp.APELLIDO2) LIKE UPPER('%"+designaItem.getApellidosInteresado()+"%') AND sp.IDINSTITUCION = "+idInstitucion+"))");
				}
			}

			if (tiene_juzg || tiene_asunto || tiene_acreditacion || tiene_modulo || tiene_fechaJustificacionDesde
					|| tiene_fechaJustificacionHasta || tiene_origen) {
				
				if (tiene_juzg) {
					String a[] = (String.valueOf(designaItem.getIdJuzgadoActu())).split(",");
					if (designaItem.getIdJuzgadoActu().length == 1) {
						sql.WHERE(" act.idjuzgado = " + designaItem.getIdJuzgadoActu()[0]);
					} else {
						String turnoIN = "";
						for (int i = 0; i < designaItem.getIdJuzgadoActu().length; i++) {
							String turno = designaItem.getIdJuzgadoActu()[i];
							if (i == designaItem.getIdJuzgadoActu().length - 1) {
								turnoIN = turnoIN + turno;
							} else {
								turnoIN = turnoIN + turno + " ,";
							}
						}
						sql.WHERE(" act.idjuzgado IN (" + turnoIN + " )");
					}
				}
				/*if (tiene_asunto) {
					sql += " AND des.RESUMENASUNTO = '" + designaItem.getAsunto().trim() + "' ";
				}*/
				if (tiene_acreditacion) {
					if (designaItem.getIdAcreditacion().toString().indexOf(',') != -1) {
						sql.WHERE(" act.idacreditacion = " + designaItem.getIdAcreditacion()[0]);
					} else {
						String turnoIN = "";
						for (int i = 0; i < designaItem.getIdAcreditacion().length; i++) {
							String turno = designaItem.getIdAcreditacion()[i];

							if (i == designaItem.getIdAcreditacion().length - 1) {
								turnoIN = turnoIN + turno;
							} else {
								turnoIN = turnoIN + turno + " ,";
							}
						}
						sql.WHERE(" act.idacreditacion IN (" + turnoIN + " )");
					}
				}

				if (designaItem.getIdProcedimientoActuaciones() != null
						&& designaItem.getIdProcedimientoActuaciones().length > 0) {
					if (designaItem.getIdProcedimientoActuaciones().length == 1) {
						sql.WHERE(" act.IDPRETENSION = '" + designaItem.getIdProcedimientoActuaciones()[0] + "'");
					} else {
						String estadoIN = "";
						for (int i = 0; i < designaItem.getIdProcedimientoActuaciones().length; i++) {
							String estado = designaItem.getIdProcedimientoActuaciones()[i];
							if (i == designaItem.getIdProcedimientoActuaciones().length - 1) {
								estadoIN = estadoIN + "'" + estado + "'";
							} else {
								estadoIN = estadoIN + "'" + estado + "'" + " ,";
							}
						}
						sql.WHERE(" act.IDPRETENSION IN (" + estadoIN + " )");
					}

				}
				if (tiene_origen) {
					if (designaItem.getIdOrigen().equalsIgnoreCase("COLEGIO")) {
						sql.WHERE(" act.USUCREACION <> ");
					} else {
						sql.WHERE(" act.USUCREACION = ");
					}
					sql.WHERE("  (SELECT U.IDUSUARIO " + "    FROM CEN_PERSONA P,  ADM_USUARIOS U " + "    WHERE      "
							+ "       U.NIF = P.NIFCIF " + "       AND U.IDINSTITUCION = act.IDINSTITUCION "
							+ "       AND P.IDPERSONA = act.IDPERSONACOLEGIADO) ");

				}
			}
			// jbd // inc7744 // Cambiamos el order by porque parece que afecta a la query
			// cuando se busca por colegiado
			//sql += " order by des.anio desc, des.NUMERO desc";
			
			sql.ORDER_BY("des.anio desc, des.codigo desc ");
			
			// No utilizamos la clase Paginador para la busqueda de letrados porque al
			// filtrar por residencia la sql no devolvia bien los
			// datos que eran de tipo varchar (devolvía n veces el mismo resultado),
			// utilizamos el paginador PaginadorCaseSensitive
			// y hacemos a parte el tratamiento de mayusculas y signos de acentuación
		} catch (Exception e) {
			throw e;
		}

		//LOGGER.info("+++++ [SIGA TEST] - query busquedaDesignaciones() --> " + sql.toString());
		return sql.toString();
	}
	
	public String busquedaDesignacionesFinal(List<String> ids, Short idInstitucion, Integer tamMaximo) throws Exception {
		SQL sql = new SQL();
		
		SQL sqlDefendidos = new SQL();

		// Acceso a BBDD
		
		sqlDefendidos.SELECT("NVL(PER.APELLIDO1, '') || ' ' || NVL(PER.APELLIDO2, '') || ', ' || NVL(PER.NOMBRE, '')");
		sqlDefendidos.FROM("scs_defendidosdesigna ded");
		sqlDefendidos.JOIN("scs_personajg per ON ded.idinstitucion = per.idinstitucion	AND ded.idpersona = per.idpersona");
		//sqlDefendidos.WHERE("calidad IS NOT NULL");
		sqlDefendidos.WHERE("ded.anio = des.anio");
		sqlDefendidos.WHERE("ded.numero = des.numero");
		sqlDefendidos.WHERE("ded.idinstitucion = des.idinstitucion");
		sqlDefendidos.WHERE("ded.idturno = des.idturno");
		sqlDefendidos.WHERE("rownum < 2");

		// aalg. INC_06694_SIGA. Se modifica la query para hacerla más eficiente
		try {
			sql.SELECT_DISTINCT("F_SIGA_ACTUACIONESDESIG(des.IDINSTITUCION,des.IDTURNO,des.ANIO,des.NUMERO) AS validada , des.art27, des.idpretension, des.idjuzgado, des.FECHAOFICIOJUZGADO, des.DELITOS, des.FECHARECEPCIONCOLEGIO, des.OBSERVACIONES, des.FECHAJUICIO, des.DEFENSAJURIDICA,"
					+ " des.nig, des.numprocedimiento,des.idprocedimiento, des.estado estado, des.anio anio, des.numero numero, des.IDTIPODESIGNACOLEGIO, des.fechaalta fechaalta,"
					+ " des.fechaentrada fechaentrada,des.resumenasunto, des.idturno idturno, des.codigo codigo, des.sufijo sufijo, des.fechafin, des.idinstitucion idinstitucion,"
					+ "  des.fechaestado fechaestado");
			sql.SELECT("juzgado.nombre as nombrejuzgado,  turno.nombre");
			sql.SELECT(" colegiado.ncolegiado, persona.idpersona, NVL(persona.nombre,'') as nombrepersona, NVL(persona.APELLIDOS1,'') as apellido1persona, NVL(persona.APELLIDOS2,'') as apellido2persona ");
			sql.SELECT(" (" + sqlDefendidos.toString() +")  AS NOMBREINTERESADO");
			
			sql.FROM("scs_designa des"); 
			sql.JOIN("scs_turno turno ON des.idturno = turno.idturno AND des.idinstitucion = turno.idinstitucion");
			sql.LEFT_OUTER_JOIN("scs_juzgado juzgado ON des.idjuzgado = juzgado.idjuzgado" + 
					" AND des.idinstitucion = juzgado.idinstitucion");
			sql.LEFT_OUTER_JOIN("scs_designasletrado l ON l.anio = des.anio" + 
					" AND l.numero = des.numero" + 
					" AND l.idinstitucion = des.idinstitucion" + 
					" AND l.idturno = des.idturno"); 
			sql.LEFT_OUTER_JOIN("cen_colegiado colegiado ON colegiado.idinstitucion = l.idinstitucion" + 
								" AND colegiado.idpersona = l.idpersona"); 
			sql.LEFT_OUTER_JOIN("cen_persona persona ON l.idpersona = persona.idpersona");

			String cadenaIds = "";
			
			for(String id: ids) {
				if(cadenaIds.isEmpty()) {
					cadenaIds += "(" + id + ")";
				}else {
					cadenaIds += ",(" + id + ")";
				}
			}
			sql.WHERE("(des.idinstitucion,des.idturno,des.anio,des.numero) IN (" + cadenaIds + ")");

			sql.WHERE(" (l.Fechadesigna is null or" +
					" l.Fechadesigna = (SELECT MAX(LET2.Fechadesigna) FROM SCS_DESIGNASLETRADO LET2" +
					" WHERE l.IDINSTITUCION = LET2.IDINSTITUCION AND l.IDTURNO = LET2.IDTURNO" +
					" AND l.ANIO = LET2.ANIO AND l.NUMERO = LET2.NUMERO" +
					" AND TRUNC(LET2.Fechadesigna) <= TRUNC(SYSDATE)))");
			
			sql.ORDER_BY("des.anio desc, des.codigo desc ");
			
			// No utilizamos la clase Paginador para la busqueda de letrados porque al
			// filtrar por residencia la sql no devolvia bien los
			// datos que eran de tipo varchar (devolvía n veces el mismo resultado),
			// utilizamos el paginador PaginadorCaseSensitive
			// y hacemos a parte el tratamiento de mayusculas y signos de acentuación
			
			
			if (tamMaximo != null) { 
				Integer tamMaxNumber = tamMaximo;
				sql.FETCH_FIRST_ROWS_ONLY(tamMaxNumber);
			}
		} catch (Exception e) {
			throw e;
		}

		//LOGGER.info("+++++ [SIGA TEST] - query busquedaDesignaciones() --> " + sql.toString());
		return sql.toString();
	}
	
	public String busquedaNuevaDesigna(DesignaItem designaItem, Short idInstitucion, Integer tamMaximo, boolean isNoColegiado) throws Exception {
		String sql = "";

		Hashtable codigosBind = new Hashtable();
		int contador = 0;
		// Acceso a BBDD

		// aalg. INC_06694_SIGA. Se modifica la query para hacerla más eficiente
		try {
			
			if(isNoColegiado) {
				sql = "select DISTINCT F_SIGA_ACTUACIONESDESIG(des.IDINSTITUCION,des.IDTURNO,des.ANIO,des.NUMERO) AS validada, des.art27, persona.idpersona, des.idpretension, des.idjuzgado, des.FECHAOFICIOJUZGADO, des.DELITOS, des.FECHARECEPCIONCOLEGIO, des.OBSERVACIONES, des.FECHAJUICIO, des.DEFENSAJURIDICA,"
						+ " des.nig, des.numprocedimiento,des.idprocedimiento, des.estado estado, des.anio anio, des.numero numero, des.IDTIPODESIGNACOLEGIO, des.fechaalta fechaalta,"
						+ " des.fechaentrada fechaentrada,des.idturno idturno, des.codigo codigo, des.sufijo sufijo, des.fechafin, des.idinstitucion idinstitucion,"
						+ "  des.fechaestado fechaestado, des.RESUMENASUNTO, " + " turno.nombre,  "
						   
						+ " persona.nombre as nombrepersona, persona.APELLIDOS1 as apellido1persona, persona.APELLIDOS2 as apellido2persona ";
			}else {
				sql = "select  DISTINCT F_SIGA_ACTUACIONESDESIG(des.IDINSTITUCION,des.IDTURNO,des.ANIO,des.NUMERO) AS validada , des.art27, persona.idpersona, des.idpretension, des.idjuzgado, des.FECHAOFICIOJUZGADO, des.DELITOS, des.FECHARECEPCIONCOLEGIO, des.OBSERVACIONES, des.FECHAJUICIO, des.DEFENSAJURIDICA,"
						+ " des.nig, des.numprocedimiento,des.idprocedimiento, des.estado estado, des.anio anio, des.numero numero, des.IDTIPODESIGNACOLEGIO, des.fechaalta fechaalta,"
						+ " des.fechaentrada fechaentrada,des.idturno idturno, des.codigo codigo, des.sufijo sufijo, des.fechafin, des.idinstitucion idinstitucion,"
						+ "  des.fechaestado fechaestado, des.RESUMENASUNTO, colegiado.ncolegiado," + " turno.nombre, "
						   
						+ " persona.nombre as nombrepersona, persona.APELLIDOS1 as apellido1persona, persona.APELLIDOS2 as apellido2persona ";
			}
																			  
			if(isNoColegiado) {
				sql += " from scs_designa des, cen_persona persona ," + " SCS_DESIGNASLETRADO l ,"
						+ " scs_turno turno ";
			}else {
				sql += " from scs_designa des, cen_persona persona ," + " SCS_DESIGNASLETRADO l ,"
						+ "  CEN_COLEGIADO colegiado," + " scs_turno turno ";
			}
																					

			boolean tiene_juzg = designaItem.getIdJuzgadoActu() != null
					&& designaItem.getIdJuzgadoActu().length >0;
			boolean tiene_asunto = designaItem.getAsunto() != null && !designaItem.getAsunto().equalsIgnoreCase("");
			boolean tiene_acreditacion = designaItem.getIdAcreditacion() != null
					&& (designaItem.getIdAcreditacion().length != 0);
			boolean tiene_modulo = designaItem.getIdModuloActuaciones() != null && designaItem.getIdModuloActuaciones().length >0;
			boolean tiene_fechaJustificacionDesde = designaItem.getFechaJustificacionDesde() != null
					&& designaItem.getFechaJustificacionDesde().toString() != null
					&& !designaItem.getFechaJustificacionDesde().toString().equalsIgnoreCase("");
			boolean tiene_fechaJustificacionHasta = designaItem.getFechaJustificacionHasta() != null
					&& designaItem.getFechaJustificacionHasta().toString() != null
					&& !designaItem.getFechaJustificacionHasta().toString().equalsIgnoreCase("");
			boolean tiene_origen = designaItem.getIdOrigen() != null && !designaItem.getIdOrigen().equalsIgnoreCase("");
			boolean tiene_actuacionesV = designaItem.getIdActuacionesV() != null
					&& !designaItem.getIdActuacionesV().equalsIgnoreCase("");
			boolean tiene_moduloDesignacion = (designaItem.getIdModulos() != null
					&& designaItem.getIdModulos().length > 0);
			boolean tienePretensionesDesignacion = (designaItem.getIdProcedimientos() != null
					&& designaItem.getIdProcedimientos().length > 0);

			if (tiene_juzg||tiene_asunto||tiene_acreditacion||tiene_modulo||tiene_fechaJustificacionDesde||tiene_fechaJustificacionHasta || tiene_origen){
				sql+=	", scs_actuaciondesigna act ";
			}

			boolean tiene_interesado = false;
			if ((designaItem.getNif() != null && !designaItem.getNif().equalsIgnoreCase(""))
					|| (designaItem.getNombreInteresado() != null
							&& !designaItem.getNombreInteresado().equalsIgnoreCase(""))
					|| (designaItem.getApellidosInteresado() != null
							&& !designaItem.getApellidosInteresado().equalsIgnoreCase(""))) {
				tiene_interesado = true;
			}

			if (tienePretensionesDesignacion) {
				sql += ", SCS_PRETENSION pret ";
			}

			if (idInstitucion != null) {
				if(isNoColegiado) {
					sql += " where l.anio=des.anio and l.numero=des.numero and l.idinstitucion = des.idinstitucion and l.idturno=des.idturno"
							+ " and persona.idpersona=l.idpersona"
							+ " and des.idturno=turno.idturno and des.IDINSTITUCION=turno.IDINSTITUCION ";
				}else {
					sql += " where l.anio=des.anio and l.numero=des.numero and l.idinstitucion = des.idinstitucion and l.idturno=des.idturno"
							+ " and persona.idpersona=l.idpersona"
							+ " and colegiado.IDINSTITUCION = des.IDINSTITUCION and colegiado.IDPERSONA =persona.idpersona"
							+ " and des.idturno=turno.idturno and des.IDINSTITUCION=turno.IDINSTITUCION ";
				}
				
			}
										
			if (String.valueOf(designaItem.getNumColegiado()) != null
					&& !(String.valueOf(designaItem.getNumColegiado())).equals("")) {
				sql += " and (l.Fechadesigna is null or";
				sql += " l.Fechadesigna = (SELECT MAX(LET2.Fechadesigna) FROM SCS_DESIGNASLETRADO LET2";
				sql += " WHERE l.IDINSTITUCION = LET2.IDINSTITUCION AND l.IDTURNO = LET2.IDTURNO";
				sql += " AND l.ANIO = LET2.ANIO AND l.NUMERO = LET2.NUMERO";
				sql += " AND TRUNC(LET2.Fechadesigna) <= TRUNC(SYSDATE)))";
				sql += " AND des.IDINSTITUCION = " + idInstitucion;

//				sql += " and l.idpersona = " + String.valueOf(designaItem.getNumColegiado()) + " ";
			}
			if (designaItem.getNumColegiado() != null && !(String.valueOf(designaItem.getNumColegiado())).equals("")
					&& !isNoColegiado) {
				sql += " and colegiado.ncolegiado = " + String.valueOf(designaItem.getNumColegiado()) + " ";
			}

			if (designaItem.getIdTurnos() != null
					&& (String.valueOf(designaItem.getIdTurnos()) != "-1" && designaItem.getIdTurnos().length != 0)
					&& !String.valueOf(designaItem.getIdTurnos()).equals("")) {
				if (designaItem.getIdTurnos().length == 1) {
					sql += " AND des.idTurno = " + designaItem.getIdTurnos()[0];
				} else {
					String turnoIN = "";
					for (int i = 0; i < designaItem.getIdTurnos().length; i++) {
						String turno = designaItem.getIdTurnos()[i];
						if (i == designaItem.getIdTurnos().length - 1) {
							turnoIN = turnoIN + turno;
						} else {
							turnoIN = turnoIN + turno + " ,";
						}
					}
					sql += " AND des.idTurno IN (" + turnoIN + " )";
				}
			}

			if (designaItem.getAno() != 0 && String.valueOf(designaItem.getAno()) != null
					&& !String.valueOf(designaItem.getAno()).equalsIgnoreCase("")) {

				if (String.valueOf(designaItem.getAno()).indexOf('*') >= 0) {

					contador++;
					sql += " AND " + prepararSentenciaCompletaBind(String.valueOf(designaItem.getAno()).trim(),
							"des.anio", contador, codigosBind);

				} else if (designaItem.getAno() != 0) {
					contador++;
					codigosBind.put(new Integer(contador), String.valueOf(designaItem.getAno()).trim());
					sql += " AND des.anio = " + String.valueOf(designaItem.getAno());
				}
			}

			if (designaItem.getCodigo() != null && !designaItem.getCodigo().equalsIgnoreCase("")) {

				if ((designaItem.getCodigo().indexOf(',') != -1) && (designaItem.getCodigo().indexOf('-') == -1)) {
					String[] parts = designaItem.getCodigo().split(",");
					sql += " AND (des.codigo = ";
					for (int i = 0; i < parts.length; i++) {
						if (i == parts.length - 1) {
							sql += parts[i].trim() + ")";
						} else {
							sql += parts[i].trim() + " OR des.codigo = ";
						}
					}
				} else if ((designaItem.getCodigo().indexOf('-') != -1)
						&& (designaItem.getCodigo().indexOf(',') == -1)) {
					String[] parts = designaItem.getCodigo().split("-");
					if (parts.length == 2) {
						sql += " and des.codigo IN (" + parts[0] + "," + parts[1] + ")";
					}
				} else if ((designaItem.getCodigo().indexOf('-') == -1)
						&& (designaItem.getCodigo().indexOf(',') == -1)) {
//					sql += " AND des.codigo = " + String.valueOf(designaItem.getCodigo()).trim();
					sql += "AND ltrim(des.codigo,'0') = ltrim(" + designaItem.getCodigo() + ",'0')";
				}
			}
			if (designaItem.getIdJuzgados() != null && designaItem.getIdJuzgados().length > 0) {
				if (designaItem.getIdJuzgados().length == 1) {
					sql += " AND des.idjuzgado = " + designaItem.getIdJuzgados()[0];
				} else {
					String juzgadoIN = "";
					for (int i = 0; i < designaItem.getIdJuzgados().length; i++) {
						String juzgado = designaItem.getIdJuzgados()[i];
						if (i == designaItem.getIdJuzgados().length - 1) {
							juzgadoIN = juzgadoIN + juzgado;
						} else {
							juzgadoIN = juzgadoIN + juzgado + " ,";
						}
					}
					sql += " AND act.idjuzgado IN (" + juzgadoIN + " )";
	 }
			}
			
			if (designaItem.getIdModulos() != null && designaItem.getIdModulos().length > 0) {
				if (designaItem.getIdModulos().length == 1) {
					sql += " AND des.IDPROCEDIMIENTO = '" + designaItem.getIdModulos()[0] + "'";
				} else {
					String estadoIN = "";
					for (int i = 0; i < designaItem.getIdModulos().length; i++) {
						String estado = designaItem.getIdModulos()[i];
						if (i == designaItem.getIdModulos().length - 1) {
							estadoIN = estadoIN + "'" + estado + "'";
						} else {
							estadoIN = estadoIN + "'" + estado + "'" + " ,";
						}
					}
					sql += " AND des.IDPROCEDIMIENTO IN (" + estadoIN + " )";											  
				}

			}
   
			if (designaItem.getAsunto() != null && !designaItem.getAsunto().equalsIgnoreCase("")) {
				contador++;
				codigosBind.put(new Integer(contador), designaItem.getAsunto().trim());
//				sql += " AND des.resumenasunto = '" + designaItem.getAsunto() + "'";
				sql += " AND regexp_like(des.OBSERVACIONES,'" + designaItem.getAsunto() + "')";
			}

			if (designaItem.getIdModuloActuaciones() != null && designaItem.getIdModuloActuaciones().length > 0) {
				if (designaItem.getIdModuloActuaciones().length == 1) {
					sql += " AND act.IDPROCEDIMIENTO = '" + designaItem.getIdModuloActuaciones()[0] + "'";
				} else {
					String estadoIN = "";
					for (int i = 0; i < designaItem.getIdModuloActuaciones().length; i++) {
						String estado = designaItem.getIdModuloActuaciones()[i];
						if (i == designaItem.getIdModuloActuaciones().length - 1) {
							estadoIN = estadoIN + "'" + estado + "'";
						} else {
							estadoIN = estadoIN + "'" + estado + "'" + " ,";
						}
					}
					sql += " AND act.IDPROCEDIMIENTO IN (" + estadoIN + " )";
				}

			}

			if (designaItem.getEstados() != null && designaItem.getEstados().length > 0) {
				if (designaItem.getEstados().length == 1) {
					sql += " AND des.estado = '" + designaItem.getEstados()[0] + "'";
				} else {
					String estadoIN = "";
					for (int i = 0; i < designaItem.getEstados().length; i++) {
						String estado = designaItem.getEstados()[i];
						if (i == designaItem.getEstados().length - 1) {
							estadoIN = estadoIN + "'" + estado + "'";
						} else {
							estadoIN = estadoIN + "'" + estado + "'" + " ,";
						}
					}
					sql += " AND des.estado IN (" + estadoIN + " )";
				}

			}
			if (designaItem.getNumProcedimiento() != null && !designaItem.getNumProcedimiento().equalsIgnoreCase("")) {
				contador++;
				codigosBind.put(new Integer(contador), designaItem.getNumProcedimiento().trim());
//				sql += " AND des.numprocedimiento = " + designaItem.getNumProcedimiento();
				sql += " AND regexp_like(des.numprocedimiento," + designaItem.getNumProcedimiento() + ") ";
			}
			if (designaItem.getNig() != null && !designaItem.getNig().equalsIgnoreCase("")) {
				contador++;
				codigosBind.put(new Integer(contador), designaItem.getNig().trim());
				sql += " AND des.nig = '" + designaItem.getNig() + "'";
			}
			if (tienePretensionesDesignacion) {
				if (designaItem.getIdProcedimientos() != null && designaItem.getIdProcedimientos().length > 0) {
					if (designaItem.getIdProcedimientos().length == 1) {
						sql += " AND des.IDPRETENSION = '" + designaItem.getIdProcedimientos()[0] + "'";
					} else {
						String estadoIN = "";
						for (int i = 0; i < designaItem.getIdProcedimientos().length; i++) {
							String estado = designaItem.getIdProcedimientos()[i];
							if (i == designaItem.getIdProcedimientos().length - 1) {
								estadoIN = estadoIN + "'" + estado + "'";
							} else {
								estadoIN = estadoIN + "'" + estado + "'" + " ,";
							}
						}
						sql += " AND des.IDPRETENSION IN (" + estadoIN + " )";
					}

				}
			}
			
			if(designaItem.getIdActuacionesV()!=null && !designaItem.getIdActuacionesV().trim().isEmpty()){
				if("SINACTUACIONES".equalsIgnoreCase(designaItem.getIdActuacionesV().trim())){
					sql += (" AND F_SIGA_ACTUACIONESDESIG(des.IDINSTITUCION,des.IDTURNO,des.ANIO,des.NUMERO) IS NULL ");
				}else{
				    sql += (" AND UPPER(F_SIGA_ACTUACIONESDESIG(des.IDINSTITUCION,des.IDTURNO,des.ANIO,des.NUMERO))=UPPER('"+designaItem.getIdActuacionesV()+"')");
				}
			}


			if (designaItem.getDocumentacionActuacion() != null
					&& !designaItem.getDocumentacionActuacion().equalsIgnoreCase("")) {
				if (designaItem.getDocumentacionActuacion().equalsIgnoreCase("TODAS")) {
					sql += " and act.DOCJUSTIFICACION IS NOT NULL ";
				} else if (designaItem.getDocumentacionActuacion().equalsIgnoreCase("ALGUNAS")) {
					sql += " and act.DOCJUSTIFICACION = '1'"; // -----FALTA
				} else if (designaItem.getDocumentacionActuacion().equalsIgnoreCase("NINGUNA")) {
					sql += " and act.DOCJUSTIFICACION IS NULL";
				}
			}

			// Mostrar ART 27
			String mostarArt27 = designaItem.getIdArt27();
			if (mostarArt27 != null && !mostarArt27.equalsIgnoreCase("") && !mostarArt27.equalsIgnoreCase("T")) {
				if (mostarArt27.equalsIgnoreCase("S")) {
					sql += " AND des.art27 = 1";
				} else if (mostarArt27.equalsIgnoreCase("N")) {
					sql += " AND des.art27 = 0";
				}
			}

			if (designaItem.getIdCalidad() != null && designaItem.getIdCalidad().length > 0) {
				if (designaItem.getIdCalidad().length == 1) {
					sql += " and DED.idtipoencalidad= " + designaItem.getIdCalidad()[0];
				} else {
					String calidadIN = "";
					for (int i = 0; i < designaItem.getIdCalidad().length; i++) {
						String calidad = designaItem.getIdCalidad()[i];
						if (i == designaItem.getIdCalidad().length - 1) {
							calidadIN = calidadIN + "'" + calidad + "'";
						} else {
							calidadIN = calidadIN + calidad + " ,";
						}
					}
					sql += " and def.ANIO = des.anio" + " and def.NUMERO = des.numero"
							+ " and def.IDINSTITUCION = des.idinstitucion" + " and def.IDTURNO = des.idturno"
							+ " and def.idtipoencalidad IN (" + calidadIN + " )";
				}

			}

			if ((designaItem.getFechaEntradaInicio() != null
					&& !designaItem.getFechaEntradaInicio().toString().equalsIgnoreCase(""))
					|| (designaItem.getFechaEntradaFin() != null
							&& !designaItem.getFechaEntradaFin().toString().equalsIgnoreCase(""))) {

				DateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
				String fechaEntradaInicio ="";
				String fechaEntradaFin =""; 
				if(designaItem.getFechaEntradaInicio()!= null)
					fechaEntradaInicio = formatter1.format(designaItem.getFechaEntradaInicio());
				if(designaItem.getFechaEntradaFin()!= null)
					fechaEntradaFin = formatter1.format(designaItem.getFechaEntradaFin());
				if(designaItem.getFechaEntradaInicio()!= null && designaItem.getFechaEntradaFin()!= null) {
					sql += " and des.fechaentrada between TO_DATE('" + fechaEntradaInicio + "','DD/MM/RRRR') and TO_DATE('" + fechaEntradaFin +"','DD/MM/RRRR') ";
				}else if(designaItem.getFechaEntradaInicio()!= null) {
					sql += " and des.fechaentrada >=  TO_DATE('" + fechaEntradaInicio +"','DD/MM/RRRR') ";
				}else {
					sql += " and des.fechaentrada <=  TO_DATE('" + fechaEntradaFin +"','DD/MM/RRRR') ";
				}
				

			}
			if ((designaItem.getFechaJustificacionDesde() != null
					&& designaItem.getFechaJustificacionDesde().toString() != null
					&& !designaItem.getFechaJustificacionDesde().toString().equalsIgnoreCase(""))
					|| (designaItem.getFechaJustificacionHasta() != null
							&& designaItem.getFechaJustificacionHasta().toString() != null
							&& !designaItem.getFechaJustificacionHasta().toString().equalsIgnoreCase(""))) {
				DateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
				String fechaJustificacionDesde ="";
				String fechaJustificacionHasta =""; 
				if(designaItem.getFechaJustificacionDesde()!= null)
					fechaJustificacionDesde = formatter1.format(designaItem.getFechaJustificacionDesde());
				if(designaItem.getFechaJustificacionHasta()!= null)
					fechaJustificacionHasta = formatter1.format(designaItem.getFechaJustificacionHasta());
				if(designaItem.getFechaJustificacionDesde()!= null && designaItem.getFechaJustificacionHasta()!= null) {
					sql += " and act.fechaJustificacion between TO_DATE('" + fechaJustificacionDesde + "','DD/MM/RRRR') and TO_DATE('" + fechaJustificacionHasta +"','DD/MM/RRRR') ";
				}else if(designaItem.getFechaJustificacionDesde()!= null) {
					sql += " and act.fechaJustificacion >=  TO_DATE('" + fechaJustificacionDesde +"','DD/MM/RRRR') ";
				}else {
					sql += " and act.fechaJustificacion <=  TO_DATE('" + fechaJustificacionHasta +"','DD/MM/RRRR') ";
				}

			}
			if (designaItem.getIdTipoDesignaColegios() != null
					&& (!String.valueOf(designaItem.getIdTipoDesignaColegios()).equalsIgnoreCase(""))
					&& designaItem.getIdTipoDesignaColegios().length > 0) {
				if (designaItem.getIdTipoDesignaColegios().length == 1) {
					sql += " AND des.IDTIPODESIGNACOLEGIO = " + designaItem.getIdTipoDesignaColegios()[0];
				} else {
					String turnoIN = "";
					for (int i = 0; i < designaItem.getIdTipoDesignaColegios().length; i++) {
						String turno = designaItem.getIdTipoDesignaColegios()[i];
						if (i == designaItem.getIdTipoDesignaColegios().length - 1) {
							turnoIN = turnoIN + turno;
						} else {
							turnoIN = turnoIN + turno + " ,";
						}
					}
					sql += " AND des.IDTIPODESIGNACOLEGIO IN (" + turnoIN + " )";
				}
			}

			if (tiene_interesado) {

				if (designaItem.getNif() != null && !designaItem.getNif().equalsIgnoreCase("")) {
//					sql += " and PER.NIF = " + "'" + designaItem.getNif().trim() + "'";
					sql += " and LTRIM(UPPER(PER.NIF),'0') LIKE LTRIM(UPPER('" + designaItem.getNif().trim()
							+ "%'),'0')";
				}
				if (designaItem.getNombreInteresado() != null
						&& !designaItem.getNombreInteresado().equalsIgnoreCase("")) {
//					sql += " and PER.NOMBRE = " + "'" + designaItem.getNombreInteresado().trim() + "'";
					sql += " AND persona.NOMBRE like UPPER('%" + designaItem.getNombreInteresado().trim() + "%')";
				}
				if (designaItem.getApellidosInteresado() != null
						&& !designaItem.getApellidosInteresado().equalsIgnoreCase("")) {
					sql += " and (upper(persona.APELLIDO1) like UPPER('%" + designaItem.getApellidosInteresado().trim() + "%')";
					sql += " or upper(persona.APELLIDO2) like UPPER('%" + designaItem.getApellidosInteresado().trim() + "%'))";
				}
			}

			if (tiene_juzg || tiene_asunto || tiene_acreditacion || tiene_modulo || tiene_fechaJustificacionDesde
					|| tiene_fechaJustificacionHasta || tiene_origen) {
				sql += " and des.idinstitucion = act.idinstitucion" + " and des.idturno = act.idturno"
						+ " and des.anio = act.anio" + " and des.numero = act.numero ";
				if (tiene_juzg) {
					String a[] = (String.valueOf(designaItem.getIdJuzgadoActu())).split(",");
					if (designaItem.getIdJuzgadoActu().length == 1) {
						sql += " AND act.idjuzgado = " + designaItem.getIdJuzgadoActu()[0];
					} else {
						String turnoIN = "";
						for (int i = 0; i < designaItem.getIdJuzgadoActu().length; i++) {
							String turno = designaItem.getIdJuzgadoActu()[i];
							if (i == designaItem.getIdJuzgadoActu().length - 1) {
								turnoIN = turnoIN + turno;
							} else {
								turnoIN = turnoIN + turno + " ,";
							}
						}
						sql += " AND act.idjuzgado IN (" + turnoIN + " )";
					}
				}
				if (tiene_asunto) {
					sql += " AND des.RESUMENASUNTO = '" + designaItem.getAsunto().trim() + "' ";
				}
				if (tiene_acreditacion) {
					if (designaItem.getIdAcreditacion().toString().indexOf(',') != -1) {
						sql += " AND act.idacreditacion = " + designaItem.getIdAcreditacion()[0];
					} else {
						String turnoIN = "";
						for (int i = 0; i < designaItem.getIdAcreditacion().length; i++) {
							String turno = designaItem.getIdAcreditacion()[i];

							if (i == designaItem.getIdAcreditacion().length - 1) {
								turnoIN = turnoIN + turno;
							} else {
								turnoIN = turnoIN + turno + " ,";
							}
						}
						sql += " AND act.idacreditacion IN (" + turnoIN + " )";
					}
				}

				if (designaItem.getIdProcedimientoActuaciones() != null
						&& designaItem.getIdProcedimientoActuaciones().length > 0) {
					if (designaItem.getIdProcedimientoActuaciones().length == 1) {
						sql += " AND act.IDPRETENSION = '" + designaItem.getIdProcedimientoActuaciones()[0] + "'";
					} else {
						String estadoIN = "";
						for (int i = 0; i < designaItem.getIdProcedimientoActuaciones().length; i++) {
							String estado = designaItem.getIdProcedimientoActuaciones()[i];
							if (i == designaItem.getIdProcedimientoActuaciones().length - 1) {
								estadoIN = estadoIN + "'" + estado + "'";
							} else {
								estadoIN = estadoIN + "'" + estado + "'" + " ,";
							}
						}
						sql += " AND act.IDPRETENSION IN (" + estadoIN + " )";
					}

				}
				if (tiene_origen) {
					if (designaItem.getIdOrigen().equalsIgnoreCase("COLEGIO")) {
						sql += " AND act.USUCREACION <> ";
					} else {
						sql += " AND act.USUCREACION = ";
					}
					sql += "  (SELECT U.IDUSUARIO " + "    FROM CEN_PERSONA P,  ADM_USUARIOS U " + "    WHERE      "
							+ "       U.NIF = P.NIFCIF " + "       AND U.IDINSTITUCION = act.IDINSTITUCION "
							+ "       AND P.IDPERSONA = act.IDPERSONACOLEGIADO) ";

				}
			}
//			if (tamMax != null) {
//				Integer tamMaxNumber = tamMax + 1;
//				sql += ("AND rownum <= " + tamMaxNumber);
//			}
			// jbd // inc7744 // Cambiamos el order by porque parece que afecta a la query
			// cuando se busca por colegiado
			// sql+=" order by des.idturno, des.anio desc, des.numero desc";
			sql += "  and rownum <= 200 order by des.anio desc, codigo desc ";
			// No utilizamos la clase Paginador para la busqueda de letrados porque al
			// filtrar por residencia la sql no devolvia bien los
			// datos que eran de tipo varchar (devolvía n veces el mismo resultado),
			// utilizamos el paginador PaginadorCaseSensitive
			// y hacemos a parte el tratamiento de mayusculas y signos de acentuación

		} catch (Exception e) {
			throw e;
		}

		return sql;
		
	}

	public String busquedaProcedimientoDesignas(DesignaItem designaItem, Short idInstitucion, Integer tamMaximo)
			throws Exception {
		SQL sql = new SQL();

		sql.SELECT("DISTINCT F_SIGA_GETRECURSO(P.DESCRIPCION, 1) AS procedimiento");

		sql.FROM("SCS_PRETENSION P");

		sql.INNER_JOIN("SCS_DESIGNA D ON D.IDINSTITUCION = P.IDINSTITUCION AND D.IDPRETENSION = P.IDPRETENSION");

		sql.WHERE("P.IDPRETENSION = '" + designaItem.getIdPretension() + "'");
		sql.WHERE("D.IDTURNO = '" + designaItem.getIdTurno() + "'");
		sql.WHERE(" D.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("D.ANIO = '" + designaItem.getAno() + "'");
		sql.WHERE("D.NUMERO = '" + designaItem.getNumero() + "'");
		/*sql.WHERE("P.FECHABAJA IS NULL");
		sql.WHERE("P.FECHA_BAJA IS NULL");*/

		return sql.toString();

	}

	public String busquedaModuloDesignas(DesignaItem designaItem, Short idInstitucion, Integer tamMaximo)
			throws Exception {
		SQL sql = new SQL();

		sql.SELECT("procd.nombre as modulo,procd.IDPROCEDIMIENTO as IDMODULO");

		sql.FROM("SCS_PROCEDIMIENTOS procd");

		sql.INNER_JOIN(
				" SCS_DESIGNA DES ON procd.IDINSTITUCION = DES.IDINSTITUCION AND procd.IDPROCEDIMIENTO = DES.IDPROCEDIMIENTO");

		sql.WHERE("procd.IDPROCEDIMIENTO = '" + designaItem.getIdProcedimiento() + "'");
		sql.WHERE("DES.IDTURNO = '" + designaItem.getIdTurno() + "'");
		sql.WHERE(" DES.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("DES.ANIO = '" + designaItem.getAno() + "'");
		sql.WHERE("DES.NUMERO = '" + designaItem.getNumero() + "'");
		//sql.WHERE("procd.FECHABAJA IS NULL");

		return sql.toString();

	}

	public String getAsuntoTipoDesigna(AsuntosClaveJusticiableItem asuntoClave, String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("DESIGNA.IDINSTITUCION");
		sql.SELECT("concat('D' || DESIGNA.anio || '/',lpad(DESIGNA.codigo,5,'0') ) as asunto");
		sql.SELECT("DESIGNA.FECHAENTRADA as fecha");
		sql.SELECT("DESIGNA.ANIO");
		sql.SELECT("DESIGNA.NUMERO");
		sql.SELECT("DESIGNA.codigo");
		sql.SELECT("turno.nombre as turnoguardia");
		sql.SELECT("'<b>Juzgado</b>: ' || juzgado.nombre as datosinteres");

		sql.FROM("SCS_DESIGNA DESIGNA");

		sql.LEFT_OUTER_JOIN(
				"SCS_TURNO TURNO ON designa.idturno = turno.idturno and designa.idinstitucion = turno.idinstitucion");
		sql.LEFT_OUTER_JOIN(
				"SCS_Juzgado juzgado ON juzgado.idjuzgado = DESIGNA.IDJUZGADO and DESIGNA.IDINSTITUCION = juzgado.IDINSTITUCION");

		sql.WHERE("DESIGNA.IDINSTITUCION = '" + asuntoClave.getIdInstitucion() + "'");
		sql.WHERE("DESIGNA.ANIO = '" + asuntoClave.getAnio() + "'");
		sql.WHERE("DESIGNA.NUMERO = '" + asuntoClave.getNumero() + "'");
		sql.WHERE("DESIGNA.idturno = '" + asuntoClave.getClave() + "'");

		return sql.toString();
	}

	/**
	 * 
	 * @param idInstitucion
	 * @param idTurno
	 * @param anio
	 * @param numero
	 * @return
	 */
	public String busquedaActuacionesJustificacionExpres(String idInstitucion, String idTurno, String anio,
			String numero, JustificacionExpressItem item) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT act.facturado, act.numero, ac.idacreditacion, ac.descripcion acreditacion, ac.idtipoacreditacion, ");
		sql.append(" decode(to_char(acp.porcentaje), to_char(trunc(acp.porcentaje)), to_char(acp.porcentaje), ");
		sql.append(" f_siga_formatonumero(to_char(acp.porcentaje), 2)) porcentaje, tac.descripcion tipo, ");
		sql.append(
				" pro.nombre procedimiento, pro.codigo categoria, pro.idjurisdiccion, pro.complemento, pro.permitiraniadirletrado, ");
		sql.append(" act.numeroasunto, act.idprocedimiento, act.idjuzgado, j.nombre nombreJuzgado, j.codigoext categoriaJuzgado, ");
		sql.append(
				" to_char(act.fechajustificacion, 'dd/mm/yyyy') fechajustificacion, act.validada, act.idfacturacion, ");
		sql.append(" act.numeroprocedimiento, act.anioprocedimiento, act.anio, act.idTurno, act.idInstitucion, ");
		sql.append(" ( ");
		sql.append(" SELECT nombre || ' (' || fechadesde || '-' || fechahasta || ')' ");
		sql.append(" FROM fcs_facturacionjg fjg ");
		sql.append(" WHERE fjg.idinstitucion = act.idinstitucion AND fjg.idfacturacion = act.idfacturacion");
		sql.append(" ) AS descripcionfacturacion, ");
		sql.append("(SELECT 1 FROM scs_documentaciondesigna WHERE (idactuacion = act.numeroasunto AND idinstitucion = " + idInstitucion
				+ " AND numero = act.numero AND anio = act.anio AND idturno = act.idturno ) FETCH FIRST 1 ROWS ONLY) AS TIENEDOCUMENTACION,");
		sql.append(
				" act.docjustificacion, act.anulacion, acp.nig_numprocedimiento, act.nig, act.fecha, 0 permitireditarletrado ");
		sql.append(
				" FROM scs_actuaciondesigna act, scs_procedimientos pro, scs_acreditacionprocedimiento acp, scs_acreditacion ac, ");
		sql.append(" scs_tipoacreditacion tac, scs_juzgado j ");

		// Filtro para evitar que se muestren las actuaciones de otro letrado
		if (!UtilidadesString.esCadenaVacia(item.getnColegiado())) {
			sql.append(" , scs_designasletrado designaletrado, cen_colegiado colegiado ");
		}

		sql.append(
				" WHERE ac.idtipoacreditacion = tac.idtipoacreditacion  (+) "
				+ "AND act.idinstitucion = j.idinstitucion (+) "
				+ "AND act.idjuzgado=j.idjuzgado  (+)");
		sql.append(
				" AND act.idacreditacion = ac.idacreditacion (+) "
				+ "AND act.idacreditacion = acp.idacreditacion (+) "
				+ "AND act.idinstitucion_proc = acp.idinstitucion (+) ");
		sql.append(
				" AND act.idprocedimiento = acp.idprocedimiento  (+)"
				+ "AND act.idinstitucion_proc = pro.idinstitucion (+) "
				+ "AND act.idprocedimiento = pro.idprocedimiento (+) ");

		// Filtro para evitar que se muestren las actuaciones de otro letrado
		if (!UtilidadesString.esCadenaVacia(item.getnColegiado())) {
			SQL subquery = new SQL();
			subquery.SELECT("MAX(LET2.FECHADESIGNA)");
			subquery.FROM("SCS_DESIGNASLETRADO LET2");
			subquery.WHERE("DESIGNALETRADO.IDINSTITUCION = LET2.IDINSTITUCION");
			subquery.WHERE("DESIGNALETRADO.ANIO = LET2.ANIO");
			subquery.WHERE("DESIGNALETRADO.NUMERO = LET2.NUMERO");
			subquery.WHERE("DESIGNALETRADO.IDPERSONA = LET2.IDPERSONA");
			subquery.WHERE("DESIGNALETRADO.IDTURNO = LET2.IDTURNO");
			//Se elimina esta condición para que se muestren las actuaciones con fecha anterior a la fecha de designa.			
			//subquery.WHERE("TRUNC(LET2.FECHADESIGNA) <= act.fecha");

			sql.append(
					" AND act.idinstitucion = designaletrado.idinstitucion (+)"
							+ "AND act.idturno = designaletrado.idturno (+) "
							+ "AND act.anio = designaletrado.anio (+) "
							+ "AND act.numero = designaletrado.numero (+) "
							//Se elimina esta condición para que se muestren las actuaciones con fecha anterior a la fecha de designa.										
							//+ "AND act.fecha >= TRUNC(designaletrado.fechadesigna) "
							+ "AND designaletrado.fechadesigna = ( " + subquery.toString() + " ) ");
			sql.append(
					" AND colegiado.idpersona = designaletrado.idpersona (+) "
							+ "AND colegiado.idinstitucion = designaletrado.idinstitucion (+) "
							+ "AND colegiado.ncolegiado = " + item.getnColegiado());
		}

		sql.append(" AND act.idinstitucion = " + idInstitucion + " AND act.idturno = " + idTurno + " AND act.anio = "
				+ anio + " AND act.numero = " + numero + " ");
		
		if (item.getJustificacionDesde() != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String fecha = dateFormat.format(item.getJustificacionDesde());

			sql.append(" AND TRUNC(ACT.FECHAJUSTIFICACION) >= TO_DATE('" + fecha + "','DD/MM/YYYY')");
		}

		if (item.getJustificacionHasta() != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String fecha = dateFormat.format(item.getJustificacionHasta());

			sql.append(" AND TRUNC(ACT.FECHAJUSTIFICACION) <= TO_DATE('" + fecha + "','DD/MM/YYYY')");
		}
		
		if(item.isMuestraPendiente()) {
			sql.append(" AND ACT.ANULACION = '0'");
		}
		
		sql.append(" ORDER BY act.fecha, act.numeroasunto, act.fechajustificacion");
				
		return sql.toString();
	}

	/**
	 * 
	 * @param item
	 * @param idInstitucion
	 * @param longitudCodEJG
	 * @param idPersona
	 * @return
	 */
	public String busquedaJustificacionExpres(JustificacionExpressItem item, String idInstitucion,
			String longitudCodEJG, String idPersona, String idFavorable, String idDesfavorable, String fechaDesde, String fechaHasta, boolean imprimir) {
//	public String busquedaJustificacionExpres(JustificacionExpressItem item, String idInstitucion,
//			String longitudCodEJG, String idPersona,  String fechaDesde, String fechaHasta) {

		StringBuilder sql = new StringBuilder();

		if(!imprimir) {
			sql.append(
					"SELECT DISTINCT * FROM ( SELECT DECODE(ALLDESIGNAS.NUM_TIPO_RESOLUCION_DESIGNA,1,'FAVORABLE', 2,'NO_FAVORABLE', "
							+ "3,'PTE_CAJG', 4, 'SIN_RESOLUCION','SIN_EJG') AS TIPO_RESOLUCION_DESIGNA, ");
			sql.append(" ALLDESIGNAS.* ");
			sql.append(" FROM ( ");

			sql.append(" SELECT TO_CHAR(D.FECHAENTRADA, 'dd/mm/yyyy') AS FECHADESIGNA, ");
			sql.append(" TO_CHAR(D.FECHAENTRADA, 'yyyy_mm_dd') AS FECHAORDEN, ");
			sql.append(" D.ART27 AS ART27, ");
			sql.append(" D.ANIO || '/' || D.CODIGO AS CODIGODESIGNA,");

			sql.append(" F_SIGA_GETEJG_DESIGNA(" + idInstitucion + ",d.idturno,d.anio,d.numero," + longitudCodEJG
					+ ") AS EXPEDIENTES, ");

			sql.append(
					" DECODE(D.ANIOPROCEDIMIENTO,NULL,D.NUMPROCEDIMIENTO,D.NUMPROCEDIMIENTO||'/'||D.ANIOPROCEDIMIENTO) AS ASUNTO, ");

			sql.append(" f_siga_getdefendidosdesigna(" + idInstitucion + ",d.anio,d.idturno,d.numero,0) AS CLIENTE, ");

			sql.append(" D.RESUMENASUNTO AS RESUMENASUNTO, ");
			sql.append(" DL.FECHARENUNCIA, ");
			sql.append(" D.IDINSTITUCION, ");
			sql.append(" D.IDTURNO, ");
			sql.append(" D.ANIO, ");
			sql.append(" D.NUMERO, ");
			sql.append(" D.CODIGO, ");
			sql.append(" D.IDJUZGADO, J.CODIGOEXT CATEGORIAJUZGADO, J.NOMBRE NOMBREJUZGADO, ");
			sql.append(" D.IDINSTITUCION_JUZG, ");
			sql.append(" D.ESTADO, ");
			sql.append(" D.SUFIJO, ");
			sql.append(" D.FECHAENTRADA, ");
			sql.append(" DL.IDPERSONA, ");
			sql.append(" D.IDPROCEDIMIENTO, ");
			sql.append(" D.NUMPROCEDIMIENTO, ");
			sql.append(" D.ANIOPROCEDIMIENTO, P.NOMBRE PROCEDIMIENTO,");
			sql.append(" P.COMPLEMENTO,");
			sql.append(" P.CODIGO CATEGORIA, ");
			sql.append(
					"(SELECT 1 FROM scs_documentaciondesigna WHERE ( idinstitucion = " + idInstitucion
					+ " AND numero = d.numero AND anio = d.anio AND idturno = d.idturno ) FETCH FIRST 1 ROWS ONLY) AS TIENEDOCUMENTACION,");
			sql.append(" D.NIG, ");
			sql.append(
					" (SELECT COUNT(*) FROM SCS_DESIGNASLETRADO SDL WHERE D.IDINSTITUCION = SDL.IDINSTITUCION" );
			if (fechaDesde != null) {
				sql.append(" AND TO_CHAR(SDL.FECHADESIGNA , 'dd-MM-yyyy') >=  '" + fechaDesde + "'" );
			}
			if (fechaHasta != null) {
				sql.append(" AND TO_CHAR(SDL.FECHADESIGNA , 'dd-MM-yyyy') <=  '" + fechaHasta + "'" );
			}
			sql.append(" AND D.ANIO = SDL.ANIO AND "
							+ "D.NUMERO = SDL.NUMERO AND D.IDTURNO = SDL.IDTURNO) AS CAMBIOLETRADO, ");
			sql.append(" (SELECT MIN(CASE WHEN (EJG.FECHARESOLUCIONCAJG IS NOT NULL ");
			sql.append(" AND ((EJG.IDTIPORATIFICACIONEJG IN (3,5,6,7) ");
			sql.append(" AND EJG.IDTIPORESOLAUTO IS NOT NULL ");
			sql.append(" AND EJG.IDTIPORESOLAUTO IN (1)) ");
			sql.append(" OR (EJG.IDTIPORATIFICACIONEJG IN (1,2,8,9,10,11) ");
			sql.append(" AND (EJG.IDTIPORESOLAUTO IS NULL ");
			sql.append(" OR EJG.IDTIPORESOLAUTO NOT IN (3))))) THEN 1 ");

			sql.append(" WHEN (EJG.FECHARESOLUCIONCAJG IS NOT NULL ");
			sql.append(" AND ((EJG.IDTIPORATIFICACIONEJG IN (1,2,8,9,10,11,0) ");
			sql.append(" AND EJG.IDTIPORESOLAUTO IS NOT NULL ");
			sql.append(" AND EJG.IDTIPORESOLAUTO IN (3)) ");
			sql.append(" OR (EJG.IDTIPORATIFICACIONEJG IN (3,5,6,7) ");
			sql.append(" AND (EJG.IDTIPORESOLAUTO IS NULL ");
			sql.append(" OR EJG.IDTIPORESOLAUTO NOT IN (1))))) THEN 2 ");

			sql.append(" WHEN (EJG.FECHARESOLUCIONCAJG IS NOT NULL ");
			sql.append(" AND EJG.IDTIPORATIFICACIONEJG IN (4)) THEN  3 ");

			sql.append(" ELSE 4 END) ");

			sql.append(" FROM SCS_EJG EJG, ");
			sql.append(" SCS_EJGDESIGNA EJGDES ");

			sql.append(" WHERE EJGDES.IDINSTITUCION = EJG.IDINSTITUCION ");
			sql.append(" AND EJGDES.IDTIPOEJG = EJG.IDTIPOEJG ");
			sql.append(" AND EJGDES.ANIOEJG = EJG.ANIO ");
			sql.append(" AND EJGDES.NUMEROEJG = EJG.NUMERO ");
			sql.append(" AND D.IDINSTITUCION = EJGDES.IDINSTITUCION ");
			sql.append(" AND D.IDTURNO = EJGDES.IDTURNO ");
			sql.append(" AND D.ANIO = EJGDES.ANIODESIGNA ");
			sql.append(" AND D.NUMERO = EJGDES.NUMERODESIGNA) AS NUM_TIPO_RESOLUCION_DESIGNA, ");
			sql.append(" DECODE(turno.VALIDARJUSTIFICACIONES,'S',1,'N',0) AS VALIDARJUSTIFICACIONES, ");
			sql.append(" DECODE(turno.LETRADOACTUACIONES,'S',1,'1',1,'N',0,'0',0) AS LETRADOACTUACIONES ");

		}else {
			sql.append(" SELECT DISTINCT * FROM ( select  ALLDESIGNAS.*  FROM (  SELECT    D.IDINSTITUCION,  D.IDTURNO,  D.ANIO,  D.NUMERO, DL.IDPERSONA ");
		
		}
		
		sql.append(" FROM SCS_DESIGNA D join scs_designasletrado   dl ON d.idinstitucion = dl.idinstitucion "
				+ "AND d.anio = dl.anio AND d.numero = dl.numero AND d.idturno = dl.idturno ");
		sql.append(" JOIN scs_turno   turno        ON d.idinstitucion = turno.idinstitucion "
                + " AND d.idturno = turno.idturno " );
		sql.append(" LEFT OUTER join scs_juzgado j ON  d.idjuzgado = j.idjuzgado\r\n"
				+ "                AND d.idinstitucion_juzg = j.idinstitucion ");
		sql.append(" LEFT OUTER join scs_procedimientos p ON  p.idprocedimiento = d.idprocedimiento\r\n"
				+ "                AND p.idinstitucion = d.idinstitucion ");
		sql.append("LEFT OUTER join scs_ejgdesigna ejd ON  d.anio = ejd.aniodesigna\r\n"
				+ "                AND d.numero = ejd.numerodesigna\r\n"
				+ "                AND d.idturno = ejd.idturno\r\n"
				+ "                LEFT OUTER join scs_ejg ejg ON ejd.idinstitucion = ejg.idinstitucion\r\n"
				+ "                AND ejd.idtipoejg = ejg.idtipoejg\r\n"
				+ "                AND ejd.anioejg = ejg.anio\r\n"
				+ "                AND ejd.numeroejg = ejg.numero ");
		sql.append(" WHERE D.IDINSTITUCION = " + idInstitucion);

		if(item.getNumEJG()!=null && !item.getNumEJG().isEmpty()) {
			sql.append(" AND ejg.numejg = "+item.getNumEJG());
		}
		if (imprimir) {
			if(item.isMuestraPendiente()) {
				sql.append(" AND D.ESTADO <> 'F' ");
				sql.append(" AND D.ESTADO <> 'A' ");
	
			}
		}
		
		if(item.getAnioEJG()!=null && !item.getAnioEJG().isEmpty()) {
			sql.append(" AND ejg.anio = "+item.getAnioEJG());
		}
		
		if (item.getSinEJG()!=null) {
			if (item.getSinEJG().equals("0")) {
				sql.append(" AND ejg.anio is not null ");
			}
		}

		if (item.getAnioDesignacion() != null && !item.getAnioDesignacion().trim().isEmpty()) {
			sql.append(" AND D.ANIO = " + item.getAnioDesignacion().trim());
		}

		if (item.getNumDesignacion() != null && !item.getNumDesignacion().isEmpty()) {
			// si viene - hay que buscar de uno a otro (1-5 => numDesignacion 1,2,3,4,5)
			// si viene , hay que buscar uno u otro (1,6 => numDesignacion 1 ó 6)

			sql.append(" AND");

			String[] parts;
			boolean primero = true;

			// contiene ,
			if (item.getNumDesignacion().trim().contains(",")) {
				parts = item.getNumDesignacion().trim().split(",");

				sql.append("(");

				for (String str : parts) {
					if (primero) {
						sql.append(" D.CODIGO = " + str.trim());
						primero = false;
					} else {
						sql.append(" OR D.CODIGO =" + str.trim());
					}
				}

				sql.append(" )");

				// contiene -
			} else if (item.getNumDesignacion().trim().contains("-")) {
				parts = item.getNumDesignacion().trim().split("-");

				sql.append("( TO_NUMBER(D.CODIGO, 999999999999) BETWEEN ");

				for (String str : parts) {
					if (primero) {
						sql.append("TO_NUMBER(" + str.trim() + ", 999999999999)");
						primero = false;
					} else {
						sql.append(" AND TO_NUMBER(" + str.trim() + ", 999999999999)");
					}
				}

				sql.append(" )");
			} else {
				sql.append(" D.CODIGO = " + item.getNumDesignacion().trim());
			}
		}

		if (item.getActuacionesValidadas() != null && !item.getActuacionesValidadas().trim().isEmpty()) {
			if ("SINACTUACIONES".equalsIgnoreCase(item.getActuacionesValidadas().trim())) {
				sql.append(" AND F_SIGA_ACTUACIONESDESIG(D.IDINSTITUCION,D.IDTURNO,D.ANIO,D.NUMERO) IS NULL ");
			} else {
				sql.append(" AND UPPER(F_SIGA_ACTUACIONESDESIG(D.IDINSTITUCION,D.IDTURNO,D.ANIO,D.NUMERO))=UPPER('"
						+ item.getActuacionesValidadas() + "')");
			}
		}

		if (item.getJustificacionDesde() != null || item.getJustificacionHasta() != null) {
			sql.append(" AND (SELECT COUNT(*) FROM SCS_ACTUACIONDESIGNA ACT");
			sql.append(" WHERE ACT.IDINSTITUCION = D.IDINSTITUCION AND ACT.ANIO = D.ANIO ");
			sql.append(" AND ACT.IDTURNO = D.IDTURNO AND ACT.NUMERO = D.NUMERO");

			if (item.getJustificacionDesde() != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String fecha = dateFormat.format(item.getJustificacionDesde());

				sql.append(" AND TRUNC(ACT.FECHAJUSTIFICACION) >= TO_DATE('" + fecha + "','DD/MM/YYYY')");
			}

			if (item.getJustificacionHasta() != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String fecha = dateFormat.format(item.getJustificacionHasta());

				sql.append(" AND TRUNC(ACT.FECHAJUSTIFICACION) <= TO_DATE('" + fecha + "','DD/MM/YYYY')");
			}

			sql.append(" )>0");
		}

		// fechas designacion
		if (item.getDesignacionDesde() != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String fecha = dateFormat.format(item.getDesignacionDesde());

			sql.append("AND TRUNC(D.FECHAENTRADA) >= TO_DATE('" + fecha + "', 'DD/MM/RRRR')");
		} else {
			sql.append(" AND TRUNC(D.FECHAENTRADA) > TO_DATE('01/01/1950', 'DD/MM/RRRR')");
		}

		if (item.getDesignacionHasta() != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String fecha = dateFormat.format(item.getDesignacionHasta());

			sql.append("AND D.FECHAENTRADA <= TO_DATE('" + fecha + "', 'DD/MM/RRRR')");
		}

		// nombre y apellidos
		if (item.getApellidos() != null && !item.getApellidos().trim().isEmpty() && item.getNombre() != null
				&& !item.getNombre().trim().isEmpty()) {

			sql.append(" AND UPPER(f_siga_getdefendidosdesigna(" + idInstitucion
					+ ",D.anio,D.idturno,D.numero,1) ) like ");
			sql.append("'%" + item.getNombre().trim().toUpperCase() + "%");
			sql.append(item.getApellidos().trim().toUpperCase() + "%'");

		} else if (item.getApellidos() != null && !item.getApellidos().trim().isEmpty()
				&& (item.getNombre() == null || item.getNombre().trim().isEmpty())) {

			sql.append(" and UPPER(f_siga_getdefendidosdesigna(" + idInstitucion
					+ ",d.anio,d.idturno,d.numero,1) ) like ");
			sql.append("'%" + item.getApellidos().trim().toUpperCase() + "%'");

		} else if ((item.getApellidos() == null || item.getApellidos().isEmpty()) && item.getNombre() != null
				&& !item.getNombre().trim().isEmpty()) {

			sql.append(" AND UPPER(f_siga_getdefendidosdesigna(" + idInstitucion
					+ ",D.anio,D.idturno,D.numero,1) ) like ");
			sql.append("'%" + item.getNombre().trim().toUpperCase() + "%'");
		}

		// NCOLEGIADO
		if (idPersona != null && !idPersona.isEmpty()) {
			sql.append(" AND DL.IDPERSONA = " + idPersona);
			//Esto evita que se repitan los registros al tener varios letrados designados por cada designa
			sql.append(" and dl.fechadesigna = (select MAX(dl2.fechadesigna) from scs_designasletrado dl2 ");
			sql.append(" where d.idinstitucion = dl2.idinstitucion");
			sql.append(" AND dl2.anio = d.anio");
			sql.append(" AND dl2.numero = d.numero");
			sql.append(" AND dl2.idturno = d.idturno");
			sql.append(" AND dl2.idpersona = "+ idPersona+")");
		}

		if (item.getEstado() != null) {
			sql.append(" AND D.ESTADO ='" + item.getEstado() + "'");
		}
		if (imprimir) {
			if(item.isMuestraPendiente()) {
				sql.append(" AND NOT EXISTS( "
						+ "				SELECT 1 "
						+ "FROM "
						+ "	scs_actuaciondesigna act, "
						+ "	scs_procedimientos pro, "
						+ "	scs_acreditacionprocedimiento acp, "
						+ "	scs_acreditacion ac, "
						+ "	scs_tipoacreditacion tac, "
						+ "	scs_juzgado j , "
						+ "	scs_designasletrado designaletrado, "
						+ "	cen_colegiado colegiado "
						+ "WHERE "
						+ "	ac.idtipoacreditacion = tac.idtipoacreditacion (+) "
						+ "	AND act.idinstitucion = j.idinstitucion (+) "
						+ "	AND act.idjuzgado = j.idjuzgado (+) "
						+ "	AND act.idacreditacion = ac.idacreditacion (+) "
						+ "	AND act.idacreditacion = acp.idacreditacion (+) "
						+ "	AND act.idinstitucion_proc = acp.idinstitucion (+) "
						+ "	AND act.idprocedimiento = acp.idprocedimiento (+) "
						+ "	AND act.idinstitucion_proc = pro.idinstitucion (+) "
						+ "	AND act.idprocedimiento = pro.idprocedimiento (+) "
						+ "	AND act.idinstitucion = designaletrado.idinstitucion (+) "
						+ "	AND act.idturno = designaletrado.idturno (+) "
						+ "	AND act.anio = designaletrado.anio (+) "
						+ "	AND act.numero = designaletrado.numero (+) "
						+ "	AND designaletrado.fechadesigna = ( "
						+ "	SELECT "
						+ "		MAX(LET2.FECHADESIGNA) "
						+ "	FROM "
						+ "		SCS_DESIGNASLETRADO LET2 "
						+ "	WHERE "
						+ "		(DESIGNALETRADO.IDINSTITUCION = LET2.IDINSTITUCION "
						+ "			AND DESIGNALETRADO.ANIO = LET2.ANIO "
						+ "			AND DESIGNALETRADO.NUMERO = LET2.NUMERO "
						+ "			AND DESIGNALETRADO.IDTURNO = LET2.IDTURNO) ) "
						+ "	AND colegiado.idpersona = designaletrado.idpersona (+) "
						+ "	AND colegiado.idinstitucion = designaletrado.idinstitucion (+) "
						+ "	AND act.idinstitucion = d.idinstitucion "
						+ "	AND act.idturno = d.idturno "
						+ "	AND act.anio = d.anio "
						+ "	AND act.numero = d.numero "
						+ "	AND ACT.ANULACION = '0' "
						+ "	AND acp.porcentaje >= 100 "
						+ "	AND "
						+ "						("
						+ "							SELECT"
						+ "								COUNT(*)"
						+ "								FROM"
						+ "									scs_actuaciondesigna act"
						+ "								WHERE"
						+ "									act.idinstitucion =  d.idinstitucion"
						+ "									AND act.idturno = d.idturno"
						+ "									AND act.anio = d.anio"
						+ "									AND IDPERSONACOLEGIADO = DL.IDPERSONA"
						+ "									AND NUMERO = d.numero"
						+ "						)"
						+ "						="
						+ "						("
						+ "							SELECT"
						+ "								COUNT(*)"
						+ "								FROM"
						+ "									scs_actuaciondesigna act"
						+ "								WHERE"
						+ "									act.idinstitucion = d.idinstitucion"
						+ "									AND act.idturno = d.idturno"
						+ "									AND act.anio = d.anio"
						+ "									AND IDPERSONACOLEGIADO = DL.IDPERSONA"
						+ "									AND NUMERO = d.numero"
						+ "									AND VALIDADA = 1"
						+ "						))");
			}
		}
		sql.append(" ) ALLDESIGNAS ");

		StringBuilder tiposResolucionBuilder = new StringBuilder();

		tiposResolucionBuilder.append(" WHERE (ALLDESIGNAS.NUM_TIPO_RESOLUCION_DESIGNA IN (1");

		if (!item.isRestriccionesVisualizacion()
				|| (item.getResolucionPTECAJG() != null && !"0".equals(item.getResolucionPTECAJG()))) {
			tiposResolucionBuilder.append(",3");
		}

		if (!item.isRestriccionesVisualizacion()
				|| (item.getConEJGNoFavorables() != null && !"0".equals(item.getConEJGNoFavorables()))) {
			tiposResolucionBuilder.append(",2");
		}

		if (!item.isRestriccionesVisualizacion()
				|| (item.getEjgSinResolucion() != null && !"0".equals(item.getEjgSinResolucion()))) {
			tiposResolucionBuilder.append(",4");
		}

		tiposResolucionBuilder.append(")");

		if (!item.isRestriccionesVisualizacion()
				|| (item.getEjgSinResolucion() != null && !"0".equals(item.getEjgSinResolucion()))) {
			tiposResolucionBuilder.append(" OR ALLDESIGNAS.NUM_TIPO_RESOLUCION_DESIGNA is null ");
		}

		tiposResolucionBuilder.append(")");

		if(!imprimir) {
			sql.append(tiposResolucionBuilder);

			sql.append(" ORDER BY FECHAORDEN DESC, IDINSTITUCION, ANIO, CODIGO DESC, SUFIJO, CODIGODESIGNA DESC");
			sql.append(") query WHERE rownum<=200");
		}else {
			sql.append(" ORDER BY IDINSTITUCION, ANIO DESC");
			sql.append(") query WHERE rownum<=200");
		}
	
		
		if (item.getConEJGNoFavorables() != null && item.getConEJGNoFavorables().equals("0")) {
			sql.append(" AND query.tipo_resolucion_designa != 'NO_FAVORABLE' ");
		}
	
		if (item.getEjgSinResolucion() != null && item.getEjgSinResolucion().equals("0")) {
			sql.append(" AND query.tipo_resolucion_designa != 'SIN_RESOLUCION' ");
		}
		
		if (item.getResolucionPTECAJG() != null && item.getResolucionPTECAJG().equals("0")) {
			sql.append(" AND query.tipo_resolucion_designa != 'PTE_CAJG' ");
		}

		return sql.toString();
	}

	public String comboModulos(Short idInstitucion, int filtro, String fecha) {
		//Filtros , (0-Fecha Actual, 1 - Fecha Designacion , 2- Fecha Actuacion
		SQL sql = new SQL();
		sql.SELECT("MODULO.IDPROCEDIMIENTO, MODULO.NOMBRE, MODULO.CODIGO ");
		sql.FROM("SCS_PROCEDIMIENTOS MODULO");
		sql.WHERE("MODULO.IDINSTITUCION = " + idInstitucion);
		if(filtro == 0) {
			sql.WHERE("TRUNC(MODULO.FECHADESDEVIGOR) <= SYSDATE");
			sql.WHERE("(TRUNC(MODULO.FECHAHASTAVIGOR) >= SYSDATE OR MODULO.FECHAHASTAVIGOR IS NULL)");
		}else {
			sql.WHERE("TRUNC(MODULO.FECHADESDEVIGOR) <= TO_DATE('" + fecha + "','DD/MM/YYYY')");
			sql.WHERE("(TRUNC(MODULO.FECHAHASTAVIGOR) >= TO_DATE('" + fecha + "','DD/MM/YYYY') OR MODULO.FECHAHASTAVIGOR IS NULL)");
		}
		sql.WHERE("FECHABAJA IS NULL");
		sql.ORDER_BY("MODULO.NOMBRE");

		return sql.toString();
	}

	public String comboDelitos(DesignaItem designaItem, Short idInstitucion) {

		SQL sql3 = new SQL();
		sql3.SELECT("*");
		sql3.FROM("SCS_DELITOSDESIGNA");
		sql3.WHERE("IDINSTITUCION = " + idInstitucion);
		sql3.WHERE("NUMERO = '" + designaItem.getNumero() + "'");
		sql3.WHERE("IDTURNO = '" + designaItem.getIdTurno() + "'");
		sql3.WHERE("ANIO = '" + designaItem.getAno() + "'");

		SQL sql2 = new SQL();
		sql2.SELECT("D.IDDELITO");
		sql2.SELECT("F_SIGA_GETRECURSO(D.DESCRIPCION, 1) AS DESCRIPCION");
		sql2.SELECT("DD.NUMERO");
		sql2.FROM("SCS_DELITO D");
		sql2.LEFT_OUTER_JOIN(
				"( " + sql3.toString() + ") DD ON D.IDINSTITUCION = DD.IDINSTITUCION AND D.IDDELITO = DD.IDDELITO");
		sql2.WHERE("D.IDINSTITUCION = " + idInstitucion);
		sql2.ORDER_BY("DD.NUMERO NULLS LAST");
		sql2.ORDER_BY("DESCRIPCION ASC");

		SQL sql = new SQL();
		sql.SELECT("IDDELITO");
		sql.SELECT("DESCRIPCION");
		sql.FROM("( " + sql2.toString() + ")");

		return sql.toString();
	}

	public String comboProcedimientos(Short idInstitucion,String idLenguaje) {

		SQL sql = new SQL();
		sql.SELECT(" DISTINCT IDPRETENSION, NVL('(' ||CODIGOEXT || ')', ' ') ||' - ' || F_SIGA_GETRECURSO(DESCRIPCION, "+idLenguaje+") AS NOMBRE");
		sql.SELECT("F_SIGA_GETRECURSO(DESCRIPCION, 1) AS NOMBRE_ORDENADO");
		sql.FROM("SCS_PRETENSION");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		sql.WHERE("FECHABAJA IS NULL");
		sql.WHERE("FECHA_BAJA IS NULL");
		sql.ORDER_BY("NOMBRE_ORDENADO ASC");

		return sql.toString();
	}

	public String getProcedimientosJuzgados(Short idInstitucion, String idJuzgado) {

		SQL sql = new SQL();
		sql.SELECT("DISTINCT JUZGADO.IDPROCEDIMIENTO");
		sql.FROM("SCS_JUZGADOPROCEDIMIENTO JUZGADO");
		sql.WHERE("JUZGADO.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("JUZGADO.IDJUZGADO = " + idJuzgado);

		return sql.toString();
	}
	
	public String getProcedimientosJuzgados2(Short idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT("DISTINCT JUZGADO.IDPROCEDIMIENTO");
		sql.FROM("SCS_JUZGADOPROCEDIMIENTO JUZGADO");
		sql.WHERE("JUZGADO.IDINSTITUCION = " + idInstitucion);

		return sql.toString();
	}

	public String getProcedimientosPretension(Short idInstitucion, List<String> idProcedimientos) {

		String inSQL = "(";

		for (int i = 0; i < idProcedimientos.size(); i++) {
			if (i != idProcedimientos.size() - 1) {
				inSQL += idProcedimientos.get(i) + " ,";
			} else {
				inSQL += idProcedimientos.get(i) + ")";
			}
		}

		SQL sql = new SQL();
		sql.SELECT("DISTINCT IDPRETENSION, IDPROCEDIMIENTO");
		sql.FROM("SCS_PRETENSIONESPROCED PETRENSION");
		sql.WHERE("PETRENSION.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("PETRENSION.IDPROCEDIMIENTO IN " + inSQL);

		return sql.toString();
	}

	public String comboProcedimientosConJuzgado(Short idInstitucion, List<String> idPretensiones,String idLenguaje) {

		String inSQL = "(";

		for (int i = 0; i < idPretensiones.size(); i++) {
			if (i != idPretensiones.size() - 1) {
				inSQL += idPretensiones.get(i) + " ,";
			} else {
				inSQL += idPretensiones.get(i) + ")";
			}
		}


		SQL sql = new SQL();
		
		
		sql.SELECT_DISTINCT("IDPRETENSION"); 
		sql.SELECT("NVL(sp.CODIGOEXT, ' ') || ' - ' || f_siga_getrecurso(sp.DESCRIPCION, " + idLenguaje + ") AS DESCRIPCION");
	    sql.FROM("SCS_PRETENSION sp");
	    sql.WHERE("IDINSTITUCION = " + idInstitucion);
	    sql.WHERE("IDPRETENSION IN " + inSQL);
	    sql.WHERE("FECHABAJA IS NULL");
	    sql.WHERE("FECHA_BAJA IS NULL");
		
		

		return sql.toString();
	}

	public String comboModulosConJuzgado(Short idInstitucion, List<String> procedimientosJuzgados, int filtro, String fecha) {

		String inSQL = "(";

		for (int i = 0; i < procedimientosJuzgados.size(); i++) {
			if (i != procedimientosJuzgados.size() - 1) {
				inSQL += procedimientosJuzgados.get(i) + " ,";
			} else {
				inSQL += procedimientosJuzgados.get(i) + ")";
			}
		}

		SQL sql = new SQL();
		sql.SELECT("DISTINCT MODULO.IDPROCEDIMIENTO, MODULO.NOMBRE ");
		sql.FROM("SCS_PROCEDIMIENTOS MODULO ");
		sql.WHERE("MODULO.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("MODULO.IDPROCEDIMIENTO IN " + inSQL);
		if(filtro != 0) {
//			sql.WHERE("TRUNC(MODULO.FECHADESDEVIGOR) <= "+ fecha);
//			sql.WHERE("(TRUNC(MODULO.FECHAHASTAVIGOR) >= " + fecha + " OR MODULO.FECHAHASTAVIGOR IS NULL)");
//		}else {
			sql.WHERE("TRUNC(MODULO.FECHADESDEVIGOR) <= TO_DATE('" + fecha + "','DD/MM/YYYY')");
			sql.WHERE("(TRUNC(MODULO.FECHAHASTAVIGOR) >= TO_DATE('" + fecha + "','DD/MM/YYYY') OR MODULO.FECHAHASTAVIGOR IS NULL)");
		}
		sql.WHERE("FECHABAJA IS NULL");
		sql.ORDER_BY("MODULO.NOMBRE");

		return sql.toString();
	}

	public String getProcedimientoPretension(Short idInstitucion, String idPretension) {

		SQL sql = new SQL();
		sql.SELECT("DISTINCT IDPROCEDIMIENTO");
		sql.FROM("SCS_PRETENSIONESPROCED PETRENSION");
		sql.WHERE("PETRENSION.IDINSTITUCION = " + idInstitucion);
		sql.WHERE(" PETRENSION.IDPRETENSION = " + idPretension);

		return sql.toString();
	}

	public String comboModulosConProcedimientos(Short idInstitucion, List<String> idPretensiones, int filtro, String fecha) {

		String inSQL = "(";

		for (int i = 0; i < idPretensiones.size(); i++) {
			if (i != idPretensiones.size() - 1) {
				inSQL += idPretensiones.get(i) + " ,";
			} else {
				inSQL += idPretensiones.get(i) + ")";
			}
		}

		SQL sql = new SQL();
		sql.SELECT("DISTINCT MODULO.IDPROCEDIMIENTO, MODULO.NOMBRE ");
		sql.FROM("SCS_PROCEDIMIENTOS MODULO ");
		sql.WHERE("MODULO.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("MODULO.IDPROCEDIMIENTO IN " + inSQL);
		if(filtro == 0) {
			if(fecha.contains("/")) {
				sql.WHERE("TRUNC(MODULO.FECHADESDEVIGOR) <= TO_DATE('"+ fecha + "','DD//MM//YYYY')");
				sql.WHERE("(TRUNC(MODULO.FECHAHASTAVIGOR) >= TO_DATE('" + fecha + "','DD//MM//YYYY') OR MODULO.FECHAHASTAVIGOR IS NULL)");
			} else {
				sql.WHERE("TRUNC(MODULO.FECHADESDEVIGOR) <= "+ fecha);
				sql.WHERE("(TRUNC(MODULO.FECHAHASTAVIGOR) >= " + fecha + " OR MODULO.FECHAHASTAVIGOR IS NULL)");
			}
		}else {
			sql.WHERE("TRUNC(MODULO.FECHADESDEVIGOR) <= TO_DATE('" + fecha + "','DD//MM//YYYY')");
			sql.WHERE("(TRUNC(MODULO.FECHAHASTAVIGOR) >= TO_DATE('" + fecha + "','DD//MM//YYYY') OR MODULO.FECHAHASTAVIGOR IS NULL)");
		}
		sql.WHERE("FECHABAJA IS NULL");
		sql.ORDER_BY("MODULO.NOMBRE");

		return sql.toString();
	}

	public String getPretensionModulo(Short idInstitucion, String idModulo) {

		SQL sql = new SQL();
		sql.SELECT("DISTINCT PETRENSION.IDPRETENSION ");
		sql.FROM("SCS_PRETENSIONESPROCED PETRENSION");
		sql.WHERE("PETRENSION.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("PETRENSION.IDPROCEDIMIENTO = " + idModulo);

		return sql.toString();
	}

	public String comboProcedimientosConModulos(Short idInstitucion, List<String> idPretensiones) {

		String inSQL = "(";

		for (int i = 0; i < idPretensiones.size(); i++) {
			if (i != idPretensiones.size() - 1) {
				inSQL += idPretensiones.get(i) + " ,";
			} else {
				inSQL += idPretensiones.get(i) + ")";
			}
		}

		SQL sql = new SQL();
		sql.SELECT("DISTINCT IDPRETENSION, F_SIGA_GETRECURSO(DESCRIPCION, 1) AS DESCRIPCION ");
		sql.FROM("SCS_PRETENSION ");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		sql.WHERE("IDPRETENSION IN " + inSQL);
		sql.WHERE("FECHABAJA IS NULL");
		sql.WHERE("FECHA_BAJA IS NULL");
		sql.ORDER_BY("DESCRIPCION");

		return sql.toString();
	}

	public static String prepararSentenciaCompletaBind(String cadena, String Campo, int contador, Hashtable codigos) {
		String temp = "";
		String sentenciaCompleta = "";
		/*
		 * La cadena introducida se pasa a mayusculas y se eliminan los blancos por la
		 * derecha y por la izquierda
		 */
		// cadena = cadena.trim();
		cadena = validateChars(cadena);

		if (hasComodin(cadena)) {

			codigos.put(new Integer(contador), convertir(cadena));
			temp = " LIKE :" + contador + " ";
			sentenciaCompleta = Campo + temp;
		} else {

			codigos.put(new Integer(contador), cadena);
			sentenciaCompleta = "regexp_like(" + Campo + ",:" + contador + ")";

		}

		return sentenciaCompleta;
	}

	public static String validateChars(Object cad) {
		cad = replacePattern(replaceBlanks(cad.toString()), "'", "''");
		cad = replacePattern(replaceBlanks(cad.toString()), "(", "\\(");
		cad = replacePattern(replaceBlanks(cad.toString()), ")", "\\)");
		return cad.toString();
	}

	private static String replaceBlanks(String str) {
		byte[] aux = str.trim().getBytes();
		int j = 0;
		for (int i = 0; i < aux.length;) {
			aux[j++] = aux[i];
			if (aux[i++] == ' ')
				while (aux[i] == ' ')
					i++;
		}
		return new String(aux, 0, j);
	}

	private static String replacePattern(String str, String pattern, String replace) {
		int s = 0;
		int e = 0;
		StringBuffer result = new StringBuffer();

		while ((e = str.indexOf(pattern, s)) >= 0) {
			result.append(str.substring(s, e));
			result.append(replace);
			s = e + pattern.length();
		}
		result.append(str.substring(s));
		return result.toString();
	}

	public static boolean hasComodin(String cadena) {
		if (cadena == null)
			return false;
		int posicion = 0; // posición del carácter en la cadena
		posicion = cadena.indexOf('*') + 1; // -1 + 1 si no está
		posicion = posicion + cadena.indexOf('?') + 1; // -1 + 1 si no está
		return (posicion != 0); // si cero, falso
	}

	public static boolean hasComa(String cadena) {
		if (cadena == null)
			return false;
		return cadena.indexOf(',') >= 0;
	}

	public static boolean hasGuion(String cadena) {
		if (cadena == null)
			return false;
		return cadena.indexOf('-') >= 0; // si cero, falso
	}

	public static String convertir(String cadena) {
		String temp = "";
		temp = cadena.replace('*', '%');
		temp = temp.replace('?', '_');
		temp = temp.toUpperCase();
		return temp;
	}

	public static String prepararSentenciaNIFBind(String cadena, String campo, int contador, Hashtable codigos) {
		String cadenaTemp = "";
		String campoNIF = campo;
		cadena = cadena.trim();
		/*
		 * if (cadena.length()<9){ for (int i=0; i<9-cadena.length();i++){
		 * cerosAux=cerosAux+ceros; if (i==0){
		 * cadenaTemp=" ("+campoNIF+" LIKE UPPER('"+cadena+"%')"; }
		 * cadenaTemp+=" OR "+campoNIF+" LIKE UPPER('"+cerosAux+cadena+"%')"; } } else
		 * cadenaTemp=" ("+campoNIF+" LIKE UPPER('"+cadena+"%')"; cadenaTemp+=")";
		 */
		cadena = validateChars(cadena);

		codigos.put(new Integer(contador), cadena + "%");
		cadenaTemp += " LTRIM(UPPER(" + campoNIF + "),'0') LIKE LTRIM(UPPER(" + contador + "),'0') ";
		// cadenaTemp="regexp_like ("+campoNIF+",'^[0]{0,8}:"+contador+"\\w*$')";

		return cadenaTemp;
	}

	public static Vector dateBetweenDesdeAndHastaBind(String nombreColumna, String fechaDesde, String fechaHasta,
			int contador, Hashtable codigos) {
		Vector resultV = new Vector();
		String result = "";
		// SimpleDateFormat sdf = new SimpleDateFormat(ClsConstants.DATE_FORMAT_JAVA);

		try {
			String fechaIni = fechaDesde;
			String fechaFin = fechaHasta;

			/*
			 * JPT: - No entiendo para que le suma un dia y luego hace un trunc - Si le suma
			 * un dia y hace menor o igual por fecha y hora todavia puede tener sentido
			 * 
			 * if (fechaFin != null && !fechaFin.trim().equals("")) { Date d =
			 * sdf.parse(fechaFin); d.setTime(d.getTime() + ClsConstants.DATE_MORE);
			 * fechaFin = (sdf.format(d)); }
			 */
			boolean existedesde = false;

			if (fechaIni != null && !fechaIni.trim().equals("")) {
				contador++;
				codigos.put(new Integer(contador), fechaIni);
				result = " TRUNC(" + nombreColumna + ") >= TRUNC(TO_DATE(:" + contador + ", '" + "dd/mm/yy" + "')) ";
				existedesde = true;
			}

			if (fechaFin != null && !fechaFin.trim().equals("")) {
				if (existedesde) {
					result += " AND ";
				}
				contador++;
				codigos.put(new Integer(contador), fechaFin);
				result += " TRUNC(" + nombreColumna + ") <= TRUNC(TO_DATE(:" + contador + ", '" + "dd/mm/yy" + "')) ";
			}

		} catch (Exception e) {
			throw e;
		}

		resultV.add(new Integer(contador));
		resultV.add(result);

		return resultV;
	}

	public String busquedaProcurador(String num, String idinstitucion, String idturno, String anio) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();

		sql2.SELECT(
				"p.ncolegiado, p.nombre, p.apellidos1, p.apellidos2, dp.anio, dp.numerodesignacion, dp.fechadesigna, dp.observaciones, dp.motivosrenuncia,dp.fecharenuncia, dp.fecharenunciasolicita");
		sql2.SELECT("dp.idprocurador");
		sql2.SELECT("dp.idinstitucion_proc");
		
		sql2.FROM("SCS_DESIGNAPROCURADOR dp");
		sql2.INNER_JOIN("SCS_PROCURADOR p on dp.idprocurador = p.idprocurador and dp.idinstitucion_proc = p.idinstitucion");
		sql2.WHERE("dp.idinstitucion = " + idinstitucion);
		sql2.WHERE("dp.idturno = " + idturno);
		sql2.WHERE("dp.numero =" + num);
		sql2.WHERE("dp.anio=" + anio);
		sql2.ORDER_BY("dp.FECHADESIGNA DESC");

		sql.SELECT("*");
		sql.FROM("( " + sql2.toString() + " )");
		sql.WHERE("ROWNUM <= 200");

		return sql.toString();
	}

	public String comboTipoMotivo(Short idInstitucion, String idLenguaje) {
		SQL sql = new SQL();

		sql.SELECT("IDTIPOMOTIVO, F_SIGA_GETRECURSO(E.DESCRIPCION, " + idLenguaje + ") as Descripcion");
		sql.FROM("SCS_TIPOMOTIVO E");
		sql.ORDER_BY("Descripcion");

		return sql.toString();
	}

	public String busquedaActDesigna(ActuacionDesignaRequestDTO actuacionDesignaRequestDTO, String idInstitucion) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();

		sql2.SELECT("ACT.FECHA AS FECHAACTUACION");
		sql2.SELECT("ACT.NUMEROASUNTO");
		sql2.SELECT("ACT.NUMERO");
		sql2.SELECT("ACT.IDTURNO");
		sql2.SELECT("ACT.ANIO");
		sql2.SELECT("ACT.FACTURADO");
		sql2.SELECT("PRO.NOMBRE AS MODULO");
		sql2.SELECT("ACR.DESCRIPCION AS ACREDITACION");
		sql2.SELECT("ACT.FECHAJUSTIFICACION AS JUSTIFICACION");
		sql2.SELECT("ACT.VALIDADA");
		sql2.SELECT("ACT.ANULACION AS ANULADA");
		sql2.SELECT("FAC.NOMBRE AS FACTURACION");
		sql2.SELECT("PER.APELLIDOS2 || ' ' || PER.APELLIDOS1 || ', ' || PER.NOMBRE AS LETRADO");
		sql2.SELECT("ACT.NIG");
		sql2.SELECT("ACT.NUMEROPROCEDIMIENTO");
		sql2.SELECT("ACT.IDJUZGADO");
		sql2.SELECT("JUZ.NOMBRE AS NOMBREJUZGADO");
		sql2.SELECT("ACT.IDPROCEDIMIENTO");
		sql2.SELECT("ACT.IDPRETENSION");
		sql2.SELECT("ACT.IDACREDITACION");
		sql2.SELECT("ACT.IDPRISION");
		sql2.SELECT("ACT.OBSERVACIONES");
		sql2.SELECT("ACT.OBSERVACIONESJUSTIFICACION");
		sql2.SELECT("ACT.TALONARIO");
		sql2.SELECT("ACT.TALON");
		sql2.SELECT("DECODE(COL.COMUNITARIO, '1', COL.NCOMUNITARIO, COL.NCOLEGIADO) AS NUMCOLEGIADO");
		sql2.SELECT("ACT.IDPERSONACOLEGIADO");
		sql2.SELECT("ACT.ID_MOTIVO_CAMBIO");
		sql2.SELECT("ACT.IDFACTURACION");
		sql2.SELECT("ACT.USUCREACION");
		sql2.SELECT("ACT.FECHACREACION");
		sql2.SELECT("ACT.USUMODIFICACION");
		sql2.SELECT("ACT.FECHAMODIFICACION");
		sql2.SELECT("ACT.USUJUSTIFICACION");
		sql2.SELECT("ACT.FECHAUSUJUSTIFICACION");
		sql2.SELECT("ACT.USUVALIDACION");
		sql2.SELECT("ACT.FECHAVALIDACION");
		sql2.SELECT("TUR.VALIDARJUSTIFICACIONES");
		sql2.SELECT("ACT.IDPARTIDAPRESUPUESTARIA");
		sql2.SELECT("PAR.NOMBREPARTIDA");
		sql2.SELECT("ACT.IDINSTITUCION");

		sql2.FROM("SCS_ACTUACIONDESIGNA ACT");

		sql2.JOIN("SCS_ACREDITACION ACR ON ACR.IDACREDITACION = ACT.IDACREDITACION "
				+ "LEFT JOIN FCS_FACTURACIONJG FAC ON FAC.IDFACTURACION = ACT.IDFACTURACION AND FAC.IDINSTITUCION = ACT.IDINSTITUCION "
				+ "LEFT JOIN SCS_JUZGADO JUZ ON JUZ.IDJUZGADO = ACT.IDJUZGADO AND JUZ.IDINSTITUCION = ACT.IDINSTITUCION "
				+ "LEFT JOIN SCS_PROCEDIMIENTOS PRO ON PRO.IDPROCEDIMIENTO = ACT.IDPROCEDIMIENTO AND PRO.IDINSTITUCION = ACT.IDINSTITUCION AND PRO.IDINSTITUCION = ACT.IDINSTITUCION "
				+ "LEFT JOIN CEN_COLEGIADO COL ON COL.IDPERSONA = ACT.IDPERSONACOLEGIADO AND COL.IDINSTITUCION = ACT.IDINSTITUCION "
				+ "INNER JOIN SCS_TURNO TUR ON TUR.IDINSTITUCION = ACT.IDINSTITUCION AND TUR.IDTURNO = ACT.IDTURNO "
				+ "LEFT JOIN CEN_PERSONA PER ON PER.IDPERSONA = COL.IDPERSONA "
				+ "LEFT JOIN SCS_PARTIDAPRESUPUESTARIA PAR ON PAR.IDINSTITUCION = ACT.IDINSTITUCION AND PAR.IDPARTIDAPRESUPUESTARIA = ACT.IDPARTIDAPRESUPUESTARIA");

		if (!actuacionDesignaRequestDTO.isHistorico()) {
			sql2.WHERE("ACT.ANULACION = 0");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaRequestDTO.getIdPersonaColegiado())) {
			sql2.WHERE("ACT.IDPERSONACOLEGIADO = '" + actuacionDesignaRequestDTO.getIdPersonaColegiado() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaRequestDTO.getNumeroAsunto())) {
			sql2.WHERE("ACT.NUMEROASUNTO = '" + actuacionDesignaRequestDTO.getNumeroAsunto() + "'");
		}

		sql2.WHERE("ACT.IDINSTITUCION = '" + idInstitucion + "'");
		sql2.WHERE("ACT.IDTURNO = '" + actuacionDesignaRequestDTO.getIdTurno() + "'");
		sql2.WHERE("ACT.ANIO = '" + actuacionDesignaRequestDTO.getAnio() + "'");
		sql2.WHERE("ACT.NUMERO = '" + actuacionDesignaRequestDTO.getNumero() + "'");

		sql2.ORDER_BY("FECHAACTUACION DESC, NUMEROASUNTO DESC");

		sql.SELECT("*");
		sql.FROM("( " + sql2.toString() + " )");
		sql.WHERE("ROWNUM <= 201");

		return sql.toString();
	}

	public String getNewIdActuDesigna(ActuacionDesignaItem actuacionDesignaItem, Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("NVL(MAX(ACT.NUMEROASUNTO),0) +1 AS ID");

		sql.FROM("SCS_ACTUACIONDESIGNA ACT");

		sql.WHERE("ACT.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("ACT.IDTURNO = '" + actuacionDesignaItem.getIdTurno() + "'");
		sql.WHERE("ACT.ANIO = '" + actuacionDesignaItem.getAnio() + "'");
		sql.WHERE("ACT.NUMERO = '" + actuacionDesignaItem.getNumero() + "'");

		return sql.toString();
	}

	public String busquedaListaContrarios(DesignaItem item, Short idInstitucion, Boolean historico) {

		String consulta = "SELECT\r\n" 
				+ "    t1.numero,\r\n" 
				+ "    t1.idinstitucion,\r\n" 
				+ "    t1.idturno,\r\n"
				+ "    t1.anio,\r\n" 
				+ "    t1.nif,\r\n" 
				+ "    t1.idabogadocontrario,\r\n"
				+ "    t1.IDINSTITUCION_PROCU,\r\n"
				+ "    t1.idprocurador,\r\n"
				+ "    t1.fechabaja,\r\n" 
				+ "    t1.idpersona,\r\n" 
				+ "    t1.idrepresentantelegal,\r\n"
				+ "    CASE\r\n" + "        WHEN t1.idabogadocontrario IS NOT NULL THEN\r\n" + "            (\r\n"
				+ "                SELECT\r\n" + "                    col.ncolegiado\r\n"
				+ "                    || ', '\r\n" + "                    || t1.NOMBREABOGADOCONTRARIO\r\n"
				+ "--                    || per.apellidos1\r\n" + "--                    || ' '\r\n"
				+ "--                    || per.apellidos2\r\n" + "--                    || ', '\r\n"
				+ "--                    || per.nombre \r\n" + "                    AS abogado\r\n"
				+ "                FROM\r\n" + "                    cen_persona     per,\r\n"
				+ "                    cen_colegiado   col\r\n" + "                WHERE\r\n"
				+ "                    ( per.idpersona = col.idpersona\r\n"
				+ "                      AND per.idpersona = t1.idabogadocontrario\r\n"
				+ "                      AND col.idinstitucion = t1.idinstitucion )\r\n" + "            )\r\n"
				+ "        ELSE\r\n" + "            ''\r\n" + "    END AS abogado,\r\n" + "    CASE\r\n"
				+ "        WHEN t1.idprocurador IS NOT NULL THEN\r\n" + "            (\r\n"
				+ "                SELECT\r\n" + "                    procu.ncolegiado\r\n"
				+ "                    || ', '\r\n" + "                    || procu.apellidos1\r\n"
				+ "                    || ' '\r\n" + "                    || procu.apellidos2\r\n"
				+ "                    || ', '\r\n" + "                    || procu.nombre AS procurador\r\n"
				+ "                FROM\r\n" + "                    scs_procurador procu\r\n"
				+ "                WHERE\r\n" + "                    ( idprocurador = t1.idprocurador\r\n"
				+ "                      AND idinstitucion = t1.IDINSTITUCION_PROCU )\r\n" + "            )\r\n"
				+ "        ELSE\r\n" + "            ''\r\n" + "    END AS procurador,\r\n"
				+ "    t1.apellidosnombre\r\n" + "FROM\r\n" + "    (\r\n" + "        SELECT\r\n"
				+ "            scs_contrariosdesigna.numero,\r\n"
				+ "            scs_contrariosdesigna.idinstitucion,\r\n"
				+ "            scs_contrariosdesigna.idturno,\r\n" + "            scs_contrariosdesigna.anio,\r\n"
				+ "            scs_contrariosdesigna.idpersona,\r\n"
				+ "            scs_contrariosdesigna.idrepresentantelegal,\r\n"
				+ "            scs_contrariosdesigna.NOMBREABOGADOCONTRARIO,\r\n"
				+ "            scs_contrariosdesigna.fechabaja,\r\n" + "            persona.nif,\r\n"
				+ "            persona.apellido1\r\n"
				+ "            || decode(persona.apellido2, NULL, '', ' ' || persona.apellido2)\r\n"
				+ "            || ', '\r\n" + "            || persona.nombre AS apellidosnombre,\r\n"
				+ "            scs_contrariosdesigna.idabogadocontrario,\r\n"
				+ "            scs_contrariosdesigna.idprocurador,\r\n"
				+ "            scs_contrariosdesigna.IDINSTITUCION_PROCU\r\n" + "        FROM\r\n"
				+ "            scs_contrariosdesigna\r\n"
				+ "            JOIN scs_personajg persona ON persona.idpersona = scs_contrariosdesigna.idpersona\r\n"
				+ "                                          AND persona.idinstitucion = scs_contrariosdesigna.idinstitucion\r\n"
				+ "        WHERE\r\n" + "            ( scs_contrariosdesigna.anio = " + item.getAno() + "\r\n"
				+ "              AND scs_contrariosdesigna.numero = " + item.getNumero() + "\r\n"
				+ "              AND scs_contrariosdesigna.idinstitucion = " + idInstitucion + "\r\n";
		if (!historico) {
			consulta += " AND scs_contrariosdesigna.fechabaja is null \r\n";
		}
		consulta += "              AND scs_contrariosdesigna.idturno = " + item.getIdTurno() + " )\r\n" + "    ) t1\r\n"
				+ "\r\n" + "";

		return consulta;
	}

	public String busquedaListaInteresados(DesignaItem item, Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("SCS_DEFENDIDOSDESIGNA.numero");
		sql.SELECT("SCS_DEFENDIDOSDESIGNA.idinstitucion");
		sql.SELECT("SCS_DEFENDIDOSDESIGNA.idturno");
		sql.SELECT("SCS_DEFENDIDOSDESIGNA.anio");
		sql.SELECT("SCS_DEFENDIDOSDESIGNA.idpersona");
		sql.SELECT("SCS_DEFENDIDOSDESIGNA.numero");
		sql.SELECT("persona.nif");
		sql.SELECT("F_SIGA_getRecurso(tv.descripcion,'1') ||' '|| persona.direccion || ' ' || persona.numerodir || ' ' || persona.pisodir || ' ' || persona.puertadir || ' ' || pol.nombre ||' ' || persona.codigopostal || ' ' || prov.nombre  as direccion");
		sql.SELECT("    CASE\r\n" + "        WHEN nombrerepresentante IS NOT NULL THEN\r\n"
				+ "            nombrerepresentante\r\n" + "        ELSE\r\n" + "            ''\r\n"
				+ "    END AS representante\r\n");
		sql.SELECT("            persona.apellido1\r\n"
				+ "            || decode(persona.apellido2, NULL, '', ' ' || persona.apellido2)\r\n"
				+ "            || ', '\r\n" + "            || persona.nombre AS apellidosnombre");
		sql.FROM("SCS_DEFENDIDOSDESIGNA");
		sql.JOIN(
				"scs_personajg persona ON persona.idpersona = scs_DEFENDIDOSDESIGNA.idpersona AND persona.idinstitucion = scs_DEFENDIDOSDESIGNA.idinstitucion");
		sql.LEFT_OUTER_JOIN("cen_tipovia tv on persona.idtipovia = tv.idtipovia and tv.idinstitucion = persona.idinstitucion"
				, "cen_provincias prov on persona.idprovincia = prov.idprovincia and prov.idpais = persona.idpais"
				, "cen_poblaciones pol on pol.idprovincia = prov.idprovincia and pol.idpoblacion = persona.idpoblacion");
		sql.WHERE("            ( scs_DEFENDIDOSDESIGNA.anio = " + item.getAno() + "\r\n"
				+ "              AND scs_DEFENDIDOSDESIGNA.numero = " + item.getNumero() + "\r\n"
				+ "              AND scs_DEFENDIDOSDESIGNA.idinstitucion = " + idInstitucion + "\r\n"
				+ "              AND scs_DEFENDIDOSDESIGNA.idturno = " + item.getIdTurno() + " )\r\n");

		//LOGGER.info("+++++ [SIGA TEST] - query busquedaListaInteresados() --> " + sql.toString());
		
		return sql.toString();
	}

	public String anularReactivarActDesigna(ActuacionDesignaItem actuacionDesignaItem, Short idInstitucion,
			AdmUsuarios usuario, boolean anular) {
		SQL sql = new SQL();
		String anulReact = anular ? "1" : "0";

		sql.UPDATE("SCS_ACTUACIONDESIGNA");

		sql.SET("ANULACION = '" + anulReact + "'");
		sql.SET("USUMODIFICACION = '" + usuario.getIdusuario() + "'");
		sql.SET("FECHAMODIFICACION = SYSTIMESTAMP");

		sql.WHERE("NUMERO = '" + actuacionDesignaItem.getNumero() + "'");
		sql.WHERE("IDTURNO = '" + actuacionDesignaItem.getIdTurno() + "'");
		sql.WHERE("ANIO = '" + actuacionDesignaItem.getAnio() + "'");
		sql.WHERE("NUMEROASUNTO = '" + actuacionDesignaItem.getNumeroAsunto() + "'");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}

	public String validarDesvalidarActDesigna(ActuacionDesignaItem actuacionDesignaItem, String idInstitucion,
			AdmUsuarios usuario, boolean validar) {
		SQL sql = new SQL();
		String validarReact = validar ? "1" : "0";

		sql.UPDATE("SCS_ACTUACIONDESIGNA");

		sql.SET("VALIDADA = '" + validarReact + "'");
		sql.SET("USUMODIFICACION = '" + usuario.getIdusuario() + "'");
		sql.SET("FECHAMODIFICACION = SYSTIMESTAMP");

		

		if (validar) {
			sql.SET("FECHAVALIDACION = SYSDATE");
			sql.SET("USUVALIDACION = '" + usuario.getIdusuario() + "'");
			
			if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getFechaJustificacion())) {
				sql.SET("FECHAJUSTIFICACION = TO_DATE('" +  actuacionDesignaItem.getFechaJustificacion() + "', 'DD/MM/RRRR')");
				sql.SET("USUJUSTIFICACION = '" + usuario.getIdusuario() + "'");
				sql.SET("FECHAUSUJUSTIFICACION = SYSDATE");
			} else {
				sql.SET("FECHAJUSTIFICACION = SYSDATE");
				sql.SET("USUJUSTIFICACION = '" + usuario.getIdusuario() + "'");
				sql.SET("FECHAUSUJUSTIFICACION = SYSDATE");
			}
			if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getObservacionesJusti())) {
				sql.SET("OBSERVACIONESJUSTIFICACION = '" + actuacionDesignaItem.getObservacionesJusti() + "'");
			} else {
				sql.SET("OBSERVACIONESJUSTIFICACION = NULL");
			}
		}else {
			sql.SET("FECHAVALIDACION = NULL");
			sql.SET("USUVALIDACION = NULL");
		}

		sql.WHERE("NUMERO = '" + actuacionDesignaItem.getNumero() + "'");
		sql.WHERE("IDTURNO = '" + actuacionDesignaItem.getIdTurno() + "'");
		sql.WHERE("ANIO = '" + actuacionDesignaItem.getAnio() + "'");
		sql.WHERE("NUMEROASUNTO = '" + actuacionDesignaItem.getNumeroAsunto() + "'");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}

	public String compruebaProcurador(String num, String anio) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();

		sql2.SELECT("*");

		sql2.FROM("SCS_EJGDESIGNA");
		sql2.WHERE("NUMERODESIGNA = " + num);
		sql2.WHERE("ANIODESIGNA =" + anio);

		sql.SELECT("*");
		sql.FROM("( " + sql2.toString() + " )");
		sql.WHERE("ROWNUM <= 200");

		return sql.toString();
	}

	public String compruebaFechaProcurador(ProcuradorItem procurador, Short idInstitucion) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();
		
		sql2.SELECT(
				"p.ncolegiado, p.nombre, p.apellidos1, p.apellidos2, dp.numerodesignacion, dp.fechadesigna, dp.observaciones, dp.motivosrenuncia,dp.fecharenuncia, dp.fecharenunciasolicita");

		sql2.FROM("SCS_DESIGNAPROCURADOR dp");
		sql2.INNER_JOIN("SCS_PROCURADOR p on dp.idprocurador = p.idprocurador and dp.idinstitucion = p.idinstitucion");
		sql2.WHERE("dp.numero = '" + procurador.getNumero() + "'");
		sql2.WHERE("dp.fechadesigna = TO_DATE('" + procurador.getFechaDesigna() + "','DD/MM/YYYY')");
		sql2.WHERE("dp.idprocurador = "+ procurador.getIdProcurador() +"");
		sql2.WHERE("dp.idinstitucion = "+ idInstitucion +"");
		sql2.WHERE("dp.idturno = "+ procurador.getIdTurno() +"");
		sql2.ORDER_BY("dp.FECHADESIGNA DESC");

		sql.SELECT("*");
		sql.FROM("( " + sql2.toString() + " )");
		sql.WHERE("ROWNUM <= 200");

		return sql.toString();
	}

	public String guardarProcurador(ProcuradorItem procuradorItem, String fecha) {

		SQL sql = new SQL();
		String [] part = procuradorItem.getNombre().split(",");
		String nombre =part[1].substring(1);
		
		sql.UPDATE("scs_designaprocurador");

		sql.SET("fechadesigna = TO_DATE('" + procuradorItem.getFechaDesigna() + "','DD/MM/YYYY')");
		sql.SET("numerodesignacion ='" + procuradorItem.getNumerodesignacion() + "'");
		sql.SET("motivosrenuncia ='" + procuradorItem.getMotivosRenuncia() + "'");
		sql.SET("fecharenunciasolicita = TO_DATE('" + procuradorItem.getFecharenunciasolicita() + "','DD/MM/YYYY')");
		sql.SET("observaciones='"+procuradorItem.getObservaciones()+"'");

		sql.WHERE("idinstitucion=" + procuradorItem.getIdInstitucion());
		sql.WHERE("idprocurador=(SELECT IDPROCURADOR FROM SCS_PROCURADOR WHERE NCOLEGIADO = '"+procuradorItem.getnColegiado()+"' AND NOMBRE = '"+nombre+"')");
		if(fecha != null) {
			sql.WHERE("TRUNC(fecharenuncia) =TO_DATE('"+fecha+"','DD/MM/YYYY')");
		}
		sql.WHERE("idturno="+procuradorItem.getIdTurno());
		sql.WHERE("numero="+procuradorItem.getNumero());

		return sql.toString();
	}
	
	public String guardarProcuradorEJG(ProcuradorItem procuradorItem) {

		SQL sql = new SQL();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String fechaDesigna = dateFormat.format(procuradorItem.getFechaDesigna());

		sql.UPDATE("SCS_EJGDESIGNA");

		sql.SET("fechadesigna =TO_DATE('" + fechaDesigna + "','DD/MM/RRRR')");
		sql.SET("numerodesignacion ='" + procuradorItem.getNumerodesignacion() + "'");
		sql.SET("motivosrenuncia ='" + procuradorItem.getMotivosRenuncia() + "'");

		sql.WHERE("idinstitucion=" + procuradorItem.getIdInstitucion());
		sql.WHERE("idprocurador=(SELECT IDPROCURADOR FROM SCS_PROCURADOR WHERE NCOLEGIADO = '"+procuradorItem.getnColegiado()+"')");

		return sql.toString();
	}

	public String actualizarProcurador(ProcuradorItem procuradorItem) {

		SQL sql2 = new SQL();

		sql2.DELETE_FROM("scs_designaprocurador");
		sql2.WHERE("fechadesigna = TO_DATE('" + procuradorItem.getFechaDesigna() + "','DD/MM/YYYY')");
		sql2.WHERE("numerodesignacion ='" + procuradorItem.getNumerodesignacion() + "'");

		return sql2.toString();
	}
	
	public String insertaProcurador(ProcuradorItem procuradorItem) {

		SQL sql = new SQL();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String fechaDesigna = dateFormat.format(procuradorItem.getFechaDesigna());
		sql.INSERT_INTO("scs_designaprocurador");

		sql.SET("idinstitucion ='" + procuradorItem.getIdInstitucion() + "'");
		sql.SET("numero ='" + procuradorItem.getNumero() + "'");
		sql.SET("anio ='" + 2021 + "'");
		sql.SET("idprocurador ='" + procuradorItem.getIdProcurador() + "'");
		sql.SET("idinstitucion_proc ='" + procuradorItem.getIdColProcurador() + "'");
		sql.SET("idturno ='" + procuradorItem.getIdTurno() + "'");
		sql.SET("fechadesigna = TO_DATE('" + fechaDesigna + "','DD/MM/RRRR')");
		sql.SET("numerodesignacion ='" + procuradorItem.getNumerodesignacion() + "'");
		sql.SET("motivosrenuncia ='" + procuradorItem.getMotivosRenuncia() + "'");
 

		return sql.toString();
	}

	public String eliminarActDesigna(ActuacionDesignaItem actuacionDesignaItem, Short idInstitucion,
			AdmUsuarios usuario) {
		SQL sql = new SQL();

		sql.DELETE_FROM("SCS_ACTUACIONDESIGNA");

		sql.WHERE("NUMERO = '" + actuacionDesignaItem.getNumero() + "'");
		sql.WHERE("IDTURNO = '" + actuacionDesignaItem.getIdTurno() + "'");
		sql.WHERE("ANIO = '" + actuacionDesignaItem.getAnio() + "'");
		sql.WHERE("NUMEROASUNTO = '" + actuacionDesignaItem.getNumeroAsunto() + "'");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}

	public String guardarActDesigna(ActuacionDesignaItem actuacionDesignaItem, String idInstitucion,
			AdmUsuarios usuario) {

		SQL sql = new SQL();

		sql.INSERT_INTO("SCS_ACTUACIONDESIGNA");

		sql.VALUES("IDINSTITUCION", "'" + idInstitucion + "'");
		sql.VALUES("IDTURNO", "'" + actuacionDesignaItem.getIdTurno() + "'");
		sql.VALUES("ANIO", "'" + actuacionDesignaItem.getAnio() + "'");
		sql.VALUES("NUMERO", "'" + actuacionDesignaItem.getNumero() + "'");
		sql.VALUES("FECHA", "TO_DATE('" +  actuacionDesignaItem.getFechaActuacion() + "', 'DD/MM/RRRR')");
		sql.VALUES("NUMEROASUNTO", "'" + actuacionDesignaItem.getNumeroAsunto() + "'");
		sql.VALUES("ACUERDOEXTRAJUDICIAL", "'0'");
		sql.VALUES("ANULACION", "'0'");
		sql.VALUES("FECHAMODIFICACION", "SYSDATE");
		sql.VALUES("USUMODIFICACION", "'" + usuario.getIdusuario() + "'");
		sql.VALUES("IDPROCEDIMIENTO", "'" + actuacionDesignaItem.getIdProcedimiento() + "'");
		sql.VALUES("IDINSTITUCION_PROC", "'" + idInstitucion + "'");
		sql.VALUES("USUCREACION", "'" + usuario.getIdusuario() + "'");
		sql.VALUES("FECHACREACION", "SYSDATE");

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdPersonaColegiado())) {
			sql.VALUES("IDPERSONACOLEGIADO", "'" + actuacionDesignaItem.getIdPersonaColegiado() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getObservaciones())) {
			sql.VALUES("OBSERVACIONES", "'" + actuacionDesignaItem.getObservaciones() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getTalonario())) {
			sql.VALUES("TALONARIO", "'" + actuacionDesignaItem.getTalonario() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getTalon())) {
			sql.VALUES("TALON", "'" + actuacionDesignaItem.getTalon() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getNig())) {
			sql.VALUES("NIG", "'" + actuacionDesignaItem.getNig() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getNumProcedimiento())) {
			sql.VALUES("NUMEROPROCEDIMIENTO", "'" + actuacionDesignaItem.getNumProcedimiento() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdJuzgado())) {
			sql.VALUES("IDJUZGADO", "'" + actuacionDesignaItem.getIdJuzgado() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdMotivoCambio())) {
			sql.VALUES("ID_MOTIVO_CAMBIO", "'" + actuacionDesignaItem.getIdMotivoCambio() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdPretension())) {
			sql.VALUES("IDPRETENSION", "'" + actuacionDesignaItem.getIdPretension() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdAcreditacion())) {
			sql.VALUES("IDACREDITACION", "'" + actuacionDesignaItem.getIdAcreditacion() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdPrision())) {
			sql.VALUES("IDPRISION", "'" + actuacionDesignaItem.getIdPrision() + "'");
		}
		
		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdPartidaPresupuestaria())) {
			sql.VALUES("IDPARTIDAPRESUPUESTARIA", "'" + actuacionDesignaItem.getIdPartidaPresupuestaria() + "'");
		}

		return sql.toString();

	}

	public String actualizarActDesigna(ActuacionDesignaItem actuacionDesignaItem, String idInstitucion,
			AdmUsuarios usuario) {

		SQL sql = new SQL();

		sql.UPDATE("SCS_ACTUACIONDESIGNA");

		if (!UtilidadesString.esCadenaVacia(idInstitucion)) {
			sql.SET("IDINSTITUCION = '" + idInstitucion + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getFechaActuacion())) {
			sql.SET("FECHA = TO_DATE('" +  actuacionDesignaItem.getFechaActuacion() + "', 'DD/MM/RRRR')");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdPersonaColegiado())) {
			sql.SET("IDPERSONACOLEGIADO = '" + actuacionDesignaItem.getIdPersonaColegiado() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getObservaciones())) {
			sql.SET("OBSERVACIONES = '" + actuacionDesignaItem.getObservaciones() + "'");
		} else {
			sql.SET("OBSERVACIONES = NULL");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getTalonario())) {
			sql.SET("TALONARIO = '" + actuacionDesignaItem.getTalonario() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getTalon())) {
			sql.SET("TALON = '" + actuacionDesignaItem.getTalon() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getNig())) {
			sql.SET("NIG = '" + actuacionDesignaItem.getNig() + "'");
		} else {
			sql.SET("NIG = NULL");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getNumProcedimiento())) {
			sql.SET("NUMEROPROCEDIMIENTO = '" + actuacionDesignaItem.getNumProcedimiento() + "'");
		} else {
			sql.SET("NUMEROPROCEDIMIENTO = NULL");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdJuzgado())) {
			sql.SET("IDJUZGADO = '" + actuacionDesignaItem.getIdJuzgado() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdMotivoCambio())) {
			sql.SET("ID_MOTIVO_CAMBIO = '" + actuacionDesignaItem.getIdMotivoCambio() + "'");
		} else {
			sql.SET("ID_MOTIVO_CAMBIO = NULL");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdProcedimiento())) {
			sql.SET("IDPROCEDIMIENTO = '" + actuacionDesignaItem.getIdProcedimiento() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdPretension())) {
			sql.SET("IDPRETENSION = '" + actuacionDesignaItem.getIdPretension() + "'");
		} else {
			sql.SET("IDPRETENSION = NULL");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdAcreditacion())) {
			sql.SET("IDACREDITACION = '" + actuacionDesignaItem.getIdAcreditacion() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdPrision())) {
			sql.SET("IDPRISION = '" + actuacionDesignaItem.getIdPrision() + "'");
		}else {
			sql.SET("IDPRISION = NULL");
		}

		sql.SET("FECHAMODIFICACION = SYSDATE");
		sql.SET("USUMODIFICACION = '" + usuario.getIdusuario() + "'");

		sql.WHERE("NUMERO = '" + actuacionDesignaItem.getNumero() + "'");
		sql.WHERE("IDTURNO = '" + actuacionDesignaItem.getIdTurno() + "'");
		sql.WHERE("ANIO = '" + actuacionDesignaItem.getAnio() + "'");
		sql.WHERE("NUMEROASUNTO = '" + actuacionDesignaItem.getNumeroAsunto() + "'");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();

	}

	public String existeDesginaJuzgadoProcedimiento(Short idInstitucion, DesignaItem designa, Integer longitudDesigna) {

		SQL sql = new SQL();			
		sql.SELECT("LISTAGG(DISTINCT 'D' || d.ANIO || '/' || substr(d.CODIGO,LENGTH(d.codigo)-" + longitudDesigna + "+1,LENGTH(d.codigo)), ', ') WITHIN GROUP (ORDER BY d.ANIO DESC, d.CODIGO ASC) AS LISTA");
		sql.FROM("SCS_DESIGNA d");
		
		if(designa.getIdJuzgado() != null) {
			sql.WHERE("IDJUZGADO = '" + designa.getIdJuzgado() + "'");
		}else{
			sql.WHERE("IDJUZGADO = ''");
		}
		sql.WHERE("NUMPROCEDIMIENTO = '" + designa.getNumProcedimiento() + "'");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		if(designa.getAno() != 0 && designa.getNumero() != 0) {
			sql.WHERE("( NUMERO != '" + designa.getNumero() + "' OR ANIO != '" + designa.getAno() + "')");
		}
		
		return sql.toString();
	}

	public String getDatosAdicionales(Short idInstitucion, DesignaItem designa) {

		SQL sql = new SQL();
		SQL sqlDelitos = new SQL();
		
		sqlDelitos.SELECT("LISTAGG(sd.IDDELITO, ', ') WITHIN GROUP (ORDER BY sd.IDDELITO)");
		sqlDelitos.FROM("SCS_DELITOSDESIGNA sd"); 
		sqlDelitos.WHERE("sd.NUMERO = '" + designa.getNumero() + "'");
		sqlDelitos.WHERE("sd.IDTURNO = '" + designa.getIdTurno() + "'");
		sqlDelitos.WHERE("sd.ANIO = '" + designa.getAno() + "'");
		sqlDelitos.WHERE("sd.IDINSTITUCION = '" + idInstitucion + "'");
		
		sql.SELECT("FECHAOFICIOJUZGADO, DELITOS, (" + sqlDelitos.toString() + ") AS IDDELITOS, FECHARECEPCIONCOLEGIO, OBSERVACIONES, FECHAJUICIO, DEFENSAJURIDICA");
		sql.FROM("SCS_DESIGNA");
		sql.WHERE("NUMERO = '" + designa.getNumero() + "'");
		sql.WHERE("IDTURNO = '" + designa.getIdTurno() + "'");
		sql.WHERE("ANIO = '" + designa.getAno() + "'");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}

	public String getDatosAdicionalesDesigna(Short idInstitucion, Integer tamMaximo, DesignaItem designa) {

		SQL sql = new SQL();
		SQL sqlDelitos = new SQL();
		
		sqlDelitos.SELECT("LISTAGG(sd.IDDELITO, ', ') WITHIN GROUP (ORDER BY sd.IDDELITO)");
		sqlDelitos.FROM("SCS_DELITOSDESIGNA sd"); 
		sqlDelitos.WHERE("sd.NUMERO = '" + designa.getNumero() + "'");
		sqlDelitos.WHERE("sd.IDTURNO = '" + designa.getIdTurno() + "'");
		sqlDelitos.WHERE("sd.ANIO = '" + designa.getAno() + "'");
		sqlDelitos.WHERE("sd.IDINSTITUCION = '" + idInstitucion + "'");
		
		sql.SELECT("FECHAOFICIOJUZGADO, (" + sqlDelitos.toString() + ") AS DELITOS, FECHARECEPCIONCOLEGIO, OBSERVACIONES, FECHAJUICIO, DEFENSAJURIDICA");
		sql.FROM("SCS_DESIGNA");
		sql.WHERE("NUMERO = '" + designa.getNumero() + "'");
		sql.WHERE("IDTURNO = '" + designa.getIdTurno() + "'");
		sql.WHERE("ANIO = '" + designa.getAno() + "'");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}
	public String comboAcreditacionesPorModulo(Short idInstitucion, String idModulo) {
		SQL sql = new SQL();

		sql.SELECT("a.idacreditacion || ',' || a.nig_numprocedimiento AS id");
		sql.SELECT("acred.descripcion || ' (' || DECODE(TO_CHAR(a.porcentaje), TO_CHAR(trunc(a.porcentaje) ), "
				+ "TO_CHAR(a.porcentaje), f_siga_formatonumero(TO_CHAR(a.porcentaje), 2)) || '%)' AS descripcion");
		sql.SELECT("IDTIPOACREDITACION");

		sql.FROM("scs_acreditacionprocedimiento a, scs_acreditacion acred");
		sql.WHERE("a.idprocedimiento = " + idModulo + " and a.idinstitucion = " + idInstitucion);
		sql.WHERE("a.idacreditacion = acred.idacreditacion");
		sql.ORDER_BY("descripcion");

		return sql.toString();
	}
	
	public String busquedaResolucionEjgAsistencia(String anio, String num, short idInstitucion, int idLenguaje) {
		SQL sql = new SQL();
		
		sql.SELECT("NVL(f_siga_getrecurso(res.descripcion,"+idLenguaje+"),'Sin resolución') resolucion");
		sql.FROM("scs_ejg e");
		sql.INNER_JOIN("scs_asistencia a on a.idinstitucion = e.idinstitucion AND a.ejganio = e.anio AND a.ejgnumero = e.numero AND a.ejgidtipoejg = e.idtipoejg");
		sql.LEFT_OUTER_JOIN("cen_persona on cen_persona.idpersona = e.idpersona",
				"cen_colegiado c on cen_persona.idpersona=c.idpersona and c.idinstitucion=e.idinstitucion",
				"scs_personajg perjg on perjg.idpersona = e.idpersonajg and e.idinstitucion = perjg.idinstitucion",
				"scs_tipodictamenejg dic on e.idtipodictamenejg = dic.idtipodictamenejg and e.idinstitucion = dic.idinstitucion",
				"scs_tiporesolucion res on e.IDTIPORATIFICACIONEJG = res.idtiporesolucion",
				"scs_tiporesolauto imp ON e.idtiporesolauto = imp.idtiporesolauto");
		sql.WHERE("a.anio = '"+anio+"' AND  a.numero = '"+num+"' AND  a.idinstitucion ="+idInstitucion);
		
		return sql.toString();
	}

	public String busquedaRelaciones(String designaAnio, String designaNumero, String designaTurno, String idInstitucion) {
		//Consulta padre que engloba al resto para usar un where general, etc.
		SQL sql = new SQL();
		
		//Primera consulta para relaciones asistencia
		SQL sql2 = new SQL();
		sql2.SELECT(" DISTINCT substr(\r\n"
				+ "                TRIM('ASISTENCIA'),\r\n"
				+ "                1,\r\n"
				+ "                1\r\n"
				+ "            )\r\n"
				+ "             || asi.anio\r\n"
				+ "             || '/'\r\n"
				+ "             || TO_CHAR(asi.numero) sjcs");
		sql2.SELECT(" asi.idinstitucion");
		sql2.SELECT(" asi.anio");
		sql2.SELECT(" asi.numero");
		sql2.SELECT(" idpersonacolegiado idletrado");
		sql2.SELECT(" cen_persona.apellidos2\r\n"
				+ "             || ' '\r\n"
				+ "             || cen_persona.apellidos1\r\n"
				+ "             || ','\r\n"
				+ "             || cen_persona.nombre letrado");
		sql2.SELECT("cen_colegiado.ncolegiado ncol");
		sql2.SELECT(" TO_CHAR(idturno) idturno");
		sql2.SELECT(" TO_CHAR(designa_turno) idturnodesigna");
		sql2.SELECT(" TO_CHAR(idtipoasistencia) idtipo");
		sql2.SELECT(" TO_CHAR(asi.numero) codigo");
		
		//Subconsulta sql2
		SQL sql3= new SQL();
		sql3.SELECT(" nombre");
		sql3.FROM(" SCS_GUARDIASTURNO");
		sql3.WHERE(" idturno = asi.idturno");
		sql3.WHERE(" idturno = asi.idturno\r\n"
				+ "                        AND\r\n"
				+ "                            idinstitucion = asi.idinstitucion\r\n"
				+ "                        AND idguardia = asi.IDGUARDIA");
		sql2.SELECT(" (" + sql3.toString() + ")" + "desc_turno");
		
		//Subconsulta sql2
		SQL sql4 = new SQL();
		sql4.SELECT(" f_siga_getrecurso(\r\n"
				+ "                        descripcion,\r\n"
				+ "                        1\r\n"
				+ "                    )");
		sql4.FROM(" scs_tipoasistencia");
		sql4.WHERE(" idturno = asi.idturno");
		sql4.WHERE(" idturno = asi.idturno\r\n"
				+ "                        AND\r\n"
				+ "                            scs_tipoasistencia.idtipoasistencia = asi.idtipoasistencia");
		sql2.SELECT("(" + sql4.toString() + ")" + "des_tipo");
		
		sql2.SELECT(" perjg.apellido1\r\n"
				+ "               ||  \r\n"
				+ "                CASE WHEN perjg.apellido2 is not null then \r\n"
				+ "                     ' ' || perjg.apellido2 || ', '\r\n"
				+ "                   ELSE\r\n"
				+ "                   ', '\r\n"
				+ "                END || perjg.nombre interesado, perjg.nif nif");
		sql2.SELECT( "null impugnacion");
        sql2.SELECT(" null fechaimpugnacion");
		sql2.SELECT(" null dictamen");
		sql2.SELECT(" null fechadictamen");
		sql2.SELECT(" 'Sin resolución' resolucion");
		sql2.SELECT(" null fecharesolucion");
		sql2.SELECT(" f_siga_getrecurso(com.nombre,1) centrodetencion");
		sql2.SELECT(" fechahora fechaasunto");
		sql2.SELECT(" NVL(asi.numerodiligencia, 'Sin número' ) || ' / ' || NVL(asi.nig,'Sin número') || ' / ' || NVL(asi.numeroprocedimiento,'Sin número') dilnigproc");
		sql2.FROM(" scs_asistencia asi");
		sql2.LEFT_OUTER_JOIN(" scs_ejg ej ON ej.idpersona = asi.idpersonacolegiado AND ej.IDINSTITUCION = asi.IDINSTITUCION");
		sql2.LEFT_OUTER_JOIN(" cen_colegiado ON cen_colegiado.idpersona = asi.idpersonacolegiado AND CEN_COLEGIADO.IDINSTITUCION = asi.IDINSTITUCION"); 
		sql2.LEFT_OUTER_JOIN(" cen_persona ON cen_persona.idpersona = cen_colegiado.idpersona");
		sql2.LEFT_OUTER_JOIN(" scs_personajg perjg on perjg.idpersona = asi.idpersonajg and asi.idinstitucion = perjg.idinstitucion");
		sql2.LEFT_OUTER_JOIN(" scs_comisaria com on asi.comisaria = com.idcomisaria and asi.comisariaidinstitucion = com.idinstitucion");
		sql2.WHERE(" designa_anio = " + designaAnio);
		sql2.WHERE(" designa_numero = " + designaNumero);
		sql2.WHERE(" designa_turno = " + designaTurno );
		sql2.WHERE(" asi.idinstitucion = " + idInstitucion);
		
		//Segunda consulta para relaciones EJB
		SQL sql5 = busquedaRelacionesEjgSinExpedienteExt(designaAnio, designaNumero, designaTurno, idInstitucion);
		
		sql.SELECT(" *");
		sql.FROM("( " + sql2.toString() + "UNION " + sql5.toString() +" )");
		sql.WHERE(" ROWNUM <= 200");
		sql.ORDER_BY(" sjcs");
		sql.ORDER_BY(" idinstitucion");
		sql.ORDER_BY(" anio DESC");
		sql.ORDER_BY(" codigo DESC");

		return sql.toString();
	}
	
	public String busquedaRelacionesConIdExpedienteExt(String designaAnio, String designaNumero, String designaTurno, String idInstitucion) {
		//Consulta padre que engloba al resto para usar un where general, etc.
		SQL sql = new SQL();
		
		//Primera consulta para relaciones asistencia
		SQL sql2 = new SQL();
		sql2.SELECT(" substr(\r\n"
				+ "                TRIM('ASISTENCIA'),\r\n"
				+ "                1,\r\n"
				+ "                1\r\n"
				+ "            )\r\n"
				+ "             || asi.anio\r\n"
				+ "             || '/'\r\n"
				+ "             || TO_CHAR(asi.numero) sjcs");
		sql2.SELECT(" asi.idinstitucion");
		sql2.SELECT(" asi.anio");
		sql2.SELECT(" asi.numero");
		sql2.SELECT(" idpersonacolegiado idletrado");
		sql2.SELECT(" cen_persona.apellidos2\r\n"
				+ "             || ' '\r\n"
				+ "             || cen_persona.apellidos1\r\n"
				+ "             || ','\r\n"
				+ "             || cen_persona.nombre letrado");
		sql2.SELECT("cen_colegiado.ncolegiado ncol");
		sql2.SELECT(" TO_CHAR(idturno) idturno");
		sql2.SELECT(" TO_CHAR(designa_turno) idturnodesigna");
		sql2.SELECT(" TO_CHAR(idtipoasistencia) idtipo");
		sql2.SELECT(" TO_CHAR(asi.numero) codigo");
		
		//Subconsulta sql2
		SQL sql3= new SQL();
		sql3.SELECT(" nombre");
		sql3.FROM(" SCS_GUARDIASTURNO");
		sql3.WHERE(" idturno = asi.idturno");
		sql3.WHERE(" idturno = asi.idturno\r\n"
				+ "                        AND\r\n"
				+ "                            idinstitucion = asi.idinstitucion\r\n"
				+ "                        AND idguardia = asi.IDGUARDIA");
		sql2.SELECT(" (" + sql3.toString() + ")" + "desc_turno");
		
		//Subconsulta sql2
		SQL sql4 = new SQL();
		sql4.SELECT(" f_siga_getrecurso(\r\n"
				+ "                        descripcion,\r\n"
				+ "                        1\r\n"
				+ "                    )");
		sql4.FROM(" scs_tipoasistencia");
		sql4.WHERE(" idturno = asi.idturno");
		sql4.WHERE(" idturno = asi.idturno\r\n"
				+ "                        AND\r\n"
				+ "                            scs_tipoasistencia.idtipoasistencia = asi.idtipoasistencia");
		sql2.SELECT("(" + sql4.toString() + ")" + "des_tipo");
		
		sql2.SELECT(" perjg.apellido1\r\n"
				+ "               ||  \r\n"
				+ "                CASE WHEN perjg.apellido2 is not null then \r\n"
				+ "                     ' ' || perjg.apellido2 || ', '\r\n"
				+ "                   ELSE\r\n"
				+ "                   ', '\r\n"
				+ "                END || perjg.nombre interesado, perjg.nif nif");
		sql2.SELECT( "null impugnacion");
        sql2.SELECT(" null fechaimpugnacion");
		sql2.SELECT(" null dictamen");
		sql2.SELECT(" null fechadictamen");
		sql2.SELECT(" 'Sin resolución' resolucion");
		sql2.SELECT(" null fecharesolucion");
		sql2.SELECT(" f_siga_getrecurso(com.nombre,1) centrodetencion");
		sql2.SELECT(" fechahora fechaasunto");
		sql2.SELECT(" NVL(asi.numerodiligencia, 'Sin número' ) || ' / ' || NVL(asi.nig,'Sin número') || ' / ' || NVL(asi.numeroprocedimiento,'Sin número') dilnigproc");
		sql2.SELECT(" ej.idexpedienteext");
		sql2.FROM(" scs_asistencia asi");
		sql2.LEFT_OUTER_JOIN(" scs_ejg ej ON ej.idpersona = asi.idpersonacolegiado AND ej.IDINSTITUCION = asi.IDINSTITUCION");
		sql2.LEFT_OUTER_JOIN(" cen_colegiado ON cen_colegiado.idpersona = asi.idpersonacolegiado AND CEN_COLEGIADO.IDINSTITUCION = asi.IDINSTITUCION"); 
		sql2.LEFT_OUTER_JOIN(" cen_persona ON cen_persona.idpersona = cen_colegiado.idpersona");
		sql2.LEFT_OUTER_JOIN(" scs_personajg perjg on perjg.idpersona = asi.idpersonajg and asi.idinstitucion = perjg.idinstitucion");
		sql2.LEFT_OUTER_JOIN(" scs_comisaria com on asi.comisaria = com.idcomisaria and asi.comisariaidinstitucion = com.idinstitucion");
		sql2.WHERE(" designa_anio = " + designaAnio);
		sql2.WHERE(" designa_numero = " + designaNumero);
		sql2.WHERE(" designa_turno = " + designaTurno );
		sql2.WHERE(" asi.idinstitucion = " + idInstitucion);
		
		//Segunda consulta para relaciones EJB
		SQL sql5 = busquedaRelacionesEjg(designaAnio, designaNumero, designaTurno, idInstitucion);
		
		sql.SELECT(" *");
		sql.FROM("( " + sql2.toString() + "UNION " + sql5.toString() +" )");
		sql.WHERE(" ROWNUM <= 200");
		sql.ORDER_BY(" sjcs");
		sql.ORDER_BY(" idinstitucion");
		sql.ORDER_BY(" anio DESC");
		sql.ORDER_BY(" codigo DESC");

		return sql.toString();
	}
	
	private SQL busquedaRelacionesEjg(String designaAnio, String designaNumero, String designaTurno, String idInstitucion) {
		SQL sql = new SQL();
		sql.SELECT(" substr(\r\n"
				+ "                TRIM('EJG'),\r\n"
				+ "                1,\r\n"
				+ "                1\r\n"
				+ "            )\r\n"
				+ "             || anio\r\n"
				+ "             || '/'\r\n"
				+ "             || TO_CHAR(numejg) sjcs");
		sql.SELECT(" e.idinstitucion");
		sql.SELECT(" e.anio");
		sql.SELECT(" e.numero");
		sql.SELECT(" e.idpersona idletrado");
		sql.SELECT(" cen_persona.apellidos2\r\n"
				+ "             || ' '\r\n"
				+ "             || cen_persona.apellidos1\r\n"
				+ "             || ','\r\n"
				+ "             || cen_persona.nombre letrado");
		sql.SELECT(" cen_colegiado.ncolegiado ncol");
		sql.SELECT(" TO_CHAR(e.guardiaturno_idturno) idturno");
		sql.SELECT(" TO_CHAR(ed.idturno) idturnodesigna");
		sql.SELECT(" TO_CHAR(e.idtipoejg) idtipo");
		sql.SELECT(" lpad(\r\n"
				+ "                e.numejg,\r\n"
				+ "                5,\r\n"
				+ "                0\r\n"
				+ "            ) codigo");
		
		//Subconsulta 
		SQL sqlSub1 = new SQL();
		sqlSub1.SELECT(" abreviatura");
		sqlSub1.FROM(" scs_turno");
		sqlSub1.WHERE(" idturno = e.guardiaturno_idturno");
		sqlSub1.WHERE(" idinstitucion = e.idinstitucion");
		sql.SELECT(" (" + sqlSub1.toString() + ")" + "des_turno");
		
		//Subconsulta 
		SQL sqlSub2 = new SQL();
		sqlSub2.SELECT(" f_siga_getrecurso(\r\n"
				+ "                        descripcion,\r\n"
				+ "                        1\r\n"
				+ "                    )");
		sqlSub2.FROM(" scs_tipoejg");
		sqlSub2.WHERE(" scs_tipoejg.idtipoejg = e.idtipoejg");
		sql.SELECT(" (" + sqlSub2.toString() + ")" + "des_tipo");
		
		sql.SELECT(" perjg.apellido1\r\n"
				+ "               ||  \r\n"
				+ "                CASE WHEN perjg.apellido2 is not null then \r\n"
				+ "                     ' ' || perjg.apellido2 || ', '\r\n"
				+ "                   ELSE\r\n"
				+ "                   ', '\r\n"
				+ "                END || perjg.nombre interesado, perjg.nif nif");
		sql.SELECT(" imp.descripcion impugnacion");
		sql.SELECT(" fechaauto fechaimpugnacion");
		sql.SELECT(" f_siga_getrecurso(dic.descripcion,1) dictamen");
		sql.SELECT(" fechadictamen");
		sql.SELECT(" f_siga_getrecurso(res.descripcion,1) resolucion");
		sql.SELECT(" e.FECHARESOLUCIONCAJG fecharesolucion");
		sql.SELECT(" null centrodetencion");
		sql.SELECT(" fechaapertura fechaasunto");
		sql.SELECT(" NVL(numerodiligencia, 'Sin número' ) || ' / ' || NVL(nig,'Sin número') || ' / ' || NVL(numeroprocedimiento,'Sin número') dilnigproc");
		sql.SELECT(" e.idexpedienteext");
		sql.FROM(" scs_ejg e");
		sql.JOIN(" scs_ejgdesigna ed on ed.idinstitucion = e.idinstitucion\r\n"
				+ "                AND\r\n"
				+ "                    ed.anioejg = e.anio\r\n"
				+ "                AND\r\n"
				+ "                    ed.numeroejg = e.numero\r\n"
				+ "                AND\r\n"
				+ "                    ed.idtipoejg = e.idtipoejg");
		sql.LEFT_OUTER_JOIN("cen_colegiado ON cen_colegiado.idpersona = e.idpersona AND cen_colegiado.idinstitucion = e.idinstitucion ");
		sql.LEFT_OUTER_JOIN("cen_persona on cen_persona.idpersona = cen_colegiado.idpersona ");
		sql.LEFT_OUTER_JOIN(" scs_personajg perjg on perjg.idpersona = e.idpersonajg and e.idinstitucion = perjg.idinstitucion");
		sql.LEFT_OUTER_JOIN(" scs_tipodictamenejg dic on e.idtipodictamenejg = dic.idtipodictamenejg and e.idinstitucion = dic.idinstitucion");
		sql.LEFT_OUTER_JOIN(" scs_tiporesolucion res on e.IDTIPORATIFICACIONEJG = res.idtiporesolucion");
		sql.LEFT_OUTER_JOIN(" scs_tiporesolauto imp ON e.idtiporesolauto = imp.idtiporesolauto");
		sql.WHERE(" ed.aniodesigna = " + designaAnio);
		sql.WHERE(" ed.numerodesigna = " + designaNumero);
		sql.WHERE(" ed.idturno = " + designaTurno);
		sql.WHERE(" ed.idinstitucion = " +  idInstitucion);
		
		return sql;
	}
	
	private SQL busquedaRelacionesEjgSinExpedienteExt(String designaAnio, String designaNumero, String designaTurno, String idInstitucion) {
		SQL sql = new SQL();
		sql.SELECT(" substr(\r\n"
				+ "                TRIM('EJG'),\r\n"
				+ "                1,\r\n"
				+ "                1\r\n"
				+ "            )\r\n"
				+ "             || anio\r\n"
				+ "             || '/'\r\n"
				+ "             || TO_CHAR(numejg) sjcs");
		sql.SELECT(" e.idinstitucion");
		sql.SELECT(" e.anio");
		sql.SELECT(" e.numero");
		sql.SELECT(" e.idpersona idletrado");
		sql.SELECT(" cen_persona.apellidos2\r\n"
				+ "             || ' '\r\n"
				+ "             || cen_persona.apellidos1\r\n"
				+ "             || ','\r\n"
				+ "             || cen_persona.nombre letrado");
		sql.SELECT(" cen_colegiado.ncolegiado ncol");
		sql.SELECT(" TO_CHAR(e.guardiaturno_idturno) idturno");
		sql.SELECT(" TO_CHAR(ed.idturno) idturnodesigna");
		sql.SELECT(" TO_CHAR(e.idtipoejg) idtipo");
		sql.SELECT(" lpad(\r\n"
				+ "                e.numejg,\r\n"
				+ "                5,\r\n"
				+ "                0\r\n"
				+ "            ) codigo");
		
		//Subconsulta 
		SQL sqlSub1 = new SQL();
		sqlSub1.SELECT(" abreviatura");
		sqlSub1.FROM(" scs_turno");
		sqlSub1.WHERE(" idturno = e.guardiaturno_idturno");
		sqlSub1.WHERE(" idinstitucion = e.idinstitucion");
		sql.SELECT(" (" + sqlSub1.toString() + ")" + "des_turno");
		
		//Subconsulta 
		SQL sqlSub2 = new SQL();
		sqlSub2.SELECT(" f_siga_getrecurso(\r\n"
				+ "                        descripcion,\r\n"
				+ "                        1\r\n"
				+ "                    )");
		sqlSub2.FROM(" scs_tipoejg");
		sqlSub2.WHERE(" scs_tipoejg.idtipoejg = e.idtipoejg");
		sql.SELECT(" (" + sqlSub2.toString() + ")" + "des_tipo");
		
		sql.SELECT(" perjg.apellido1\r\n"
				+ "               ||  \r\n"
				+ "                CASE WHEN perjg.apellido2 is not null then \r\n"
				+ "                     ' ' || perjg.apellido2 || ', '\r\n"
				+ "                   ELSE\r\n"
				+ "                   ', '\r\n"
				+ "                END || perjg.nombre interesado, perjg.nif nif");
		sql.SELECT(" imp.descripcion impugnacion");
		sql.SELECT(" fechaauto fechaimpugnacion");
		sql.SELECT(" f_siga_getrecurso(dic.descripcion,1) dictamen");
		sql.SELECT(" fechadictamen");
		sql.SELECT(" f_siga_getrecurso(res.descripcion,1) resolucion");
		sql.SELECT(" e.FECHARESOLUCIONCAJG fecharesolucion");
		sql.SELECT(" null centrodetencion");
		sql.SELECT(" fechaapertura fechaasunto");
		sql.SELECT(" NVL(numerodiligencia, 'Sin número' ) || ' / ' || NVL(nig,'Sin número') || ' / ' || NVL(numeroprocedimiento,'Sin número') dilnigproc");
		sql.FROM(" scs_ejg e");
		sql.JOIN(" scs_ejgdesigna ed on ed.idinstitucion = e.idinstitucion\r\n"
				+ "                AND\r\n"
				+ "                    ed.anioejg = e.anio\r\n"
				+ "                AND\r\n"
				+ "                    ed.numeroejg = e.numero\r\n"
				+ "                AND\r\n"
				+ "                    ed.idtipoejg = e.idtipoejg");
		sql.LEFT_OUTER_JOIN("cen_colegiado ON cen_colegiado.idpersona = e.idpersona AND cen_colegiado.idinstitucion = e.idinstitucion ");
		sql.LEFT_OUTER_JOIN("cen_persona on cen_persona.idpersona = cen_colegiado.idpersona ");
		sql.LEFT_OUTER_JOIN(" scs_personajg perjg on perjg.idpersona = e.idpersonajg and e.idinstitucion = perjg.idinstitucion");
		sql.LEFT_OUTER_JOIN(" scs_tipodictamenejg dic on e.idtipodictamenejg = dic.idtipodictamenejg and e.idinstitucion = dic.idinstitucion");
		sql.LEFT_OUTER_JOIN(" scs_tiporesolucion res on e.IDTIPORATIFICACIONEJG = res.idtiporesolucion");
		sql.LEFT_OUTER_JOIN(" scs_tiporesolauto imp ON e.idtiporesolauto = imp.idtiporesolauto");
		sql.WHERE(" ed.aniodesigna = " + designaAnio);
		sql.WHERE(" ed.numerodesigna = " + designaNumero);
		sql.WHERE(" ed.idturno = " + designaTurno);
		sql.WHERE(" ed.idinstitucion = " +  idInstitucion);
		
		return sql;
	}
	
	public String busquedaRelacionesEJG(String designaAnio, String designaNumero, String designaTurno, String idInstitucion) {
		SQL sql = new SQL();
		SQL sqlEjb = busquedaRelacionesEjg(designaAnio, designaNumero, designaTurno, idInstitucion);
		
		sql.SELECT(" * ");
		sql.FROM("( " +sqlEjb.toString() +" )");
		sql.WHERE(" ROWNUM <= 10");
		return sql.toString();
	}
	
	public String existeRelacionEJG(String designaAnio, String designaNumero, String designaTurno, String idInstitucion, String anioejg, String numeroejg, String idtipoejg) {
		SQL sql = new SQL();
		SQL sqlEjb = busquedaRelacionesEjg(designaAnio, designaNumero, designaTurno, idInstitucion);
		sqlEjb.WHERE("ed.anioejg = " + anioejg);
		sqlEjb.WHERE("ed.numeroejg = " + numeroejg);
		sqlEjb.WHERE("ed.idtipoejg = " + idtipoejg);
		
		sql.SELECT(" CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 end existe ");
		sql.FROM("( " +sqlEjb.toString() +" )");
		sql.WHERE(" ROWNUM <= 10");
		
		return sql.toString();
	}

	public String eliminarRelacion(String anioEjg, String numEjg, String idTurno, String idinstitucion, String anioDes, String numDes, String idTipoEjg) {
		SQL sql = new SQL();

		sql.DELETE_FROM("SCS_EJGDESIGNA");

		sql.WHERE("NUMEROEJG = " + numEjg);
		sql.WHERE("IDTURNO = " + idTurno);
		sql.WHERE("ANIOEJG = " + anioEjg);
		sql.WHERE("IDINSTITUCION = " + idinstitucion);
		sql.WHERE("ANIODESIGNA = " + anioDes);
		sql.WHERE("NUMERODESIGNA = " + numDes);
		sql.WHERE("IDTIPOEJG = " + idTipoEjg);
		

		return sql.toString();
	}

	public String getPartidaPresupuestariaDesigna(Short idInstitucion, DesignaItem designaItem) {
		SQL sql = new SQL();
 
		sql.SELECT("D.IDPARTIDAPRESUPUESTARIA");
		sql.SELECT("PAR.NOMBREPARTIDA");

		sql.FROM("SCS_DESIGNA  D "
				+ "LEFT JOIN SCS_PARTIDAPRESUPUESTARIA PAR ON PAR.IDINSTITUCION = D.IDINSTITUCION AND PAR.IDPARTIDAPRESUPUESTARIA = D.IDPARTIDAPRESUPUESTARIA");

		sql.WHERE("D.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("D.ANIO = '" + designaItem.getAno() + "'");
		sql.WHERE("D.IDTURNO = '" + designaItem.getIdTurno() + "'");
		sql.WHERE("D.NUMERO = '" + designaItem.getNumero() + "'");

		return sql.toString();

	}

	public String comboMotivosCambioActDesigna(Short idInstitucion,String idLenguaje) {

		SQL sql = new SQL(); 

		sql.SELECT("IDACTDESMOTCAMBIO");
		sql.SELECT("f_siga_getrecurso(NOMBRE,'"+idLenguaje+"') as NOMBRE");

		sql.FROM("SCS_ACTUADESIG_MOTCAMBIO");

		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("FECHABAJA IS  NULL");
		sql.WHERE("FECHA_BAJA IS  NULL"); 
		sql.ORDER_BY("NOMBRE ASC");
 
		return sql.toString();
	}

	public String updateJustiActDesigna(ActuacionDesignaItem actuacionDesignaItem, String idInstitucion,
			AdmUsuarios usuario) {

		SQL sql = new SQL();

		sql.UPDATE("SCS_ACTUACIONDESIGNA");

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getFechaJustificacion())) {
			sql.SET("FECHAJUSTIFICACION = TO_DATE('" +  actuacionDesignaItem.getFechaJustificacion() + "', 'DD/MM/RRRR')");
			sql.SET("USUJUSTIFICACION = '" + usuario.getIdusuario() + "'");
			sql.SET("FECHAUSUJUSTIFICACION = SYSDATE");
			
		} else {
			sql.SET("FECHAJUSTIFICACION = NULL");
			sql.SET("USUJUSTIFICACION = NULL");
			sql.SET("FECHAUSUJUSTIFICACION = NULL");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getObservacionesJusti())) {
			sql.SET("OBSERVACIONESJUSTIFICACION = '" + actuacionDesignaItem.getObservacionesJusti() + "'");
		} else {
			sql.SET("OBSERVACIONESJUSTIFICACION = NULL");
		}

		sql.SET("USUMODIFICACION = '" + usuario.getIdusuario() + "'");
		sql.SET("FECHAMODIFICACION = SYSTIMESTAMP");

		sql.WHERE("NUMERO = '" + actuacionDesignaItem.getNumero() + "'");
		sql.WHERE("IDTURNO = '" + actuacionDesignaItem.getIdTurno() + "'");
		sql.WHERE("ANIO = '" + actuacionDesignaItem.getAnio() + "'");
		sql.WHERE("NUMEROASUNTO = '" + actuacionDesignaItem.getNumeroAsunto() + "'");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}

	public String getListaLetradosDesigna(ScsDesigna designa, Short idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT("SCS_DESIGNASLETRADO.FECHADESIGNA, \r\n" + "SCS_DESIGNASLETRADO.FECHARENUNCIA, \r\n"
				+ "SCS_DESIGNASLETRADO.MOTIVOSRENUNCIA, \r\n" + "SCS_DESIGNASLETRADO.FECHARENUNCIASOLICITA, \r\n"
				+ "SCS_DESIGNASLETRADO.LETRADODELTURNO, \r\n" + "CEN_COLEGIADO.NCOLEGIADO \r\n");
		sql.SELECT("            persona.apellidos1\r\n"
				+ "            || decode(persona.apellidos2, NULL, '', ' ' || persona.apellidos2)\r\n"
				+ "            || ', '\r\n" + "            || persona.nombre AS apellidosnombre");
		sql.SELECT("SCS_DESIGNASLETRADO.IDPERSONA");
		sql.SELECT("PERSONA.NIFCIF");
		sql.SELECT("SCS_DESIGNASLETRADO.observaciones");
		sql.FROM("SCS_DESIGNASLETRADO");
		sql.LEFT_OUTER_JOIN(" CEN_COLEGIADO ON CEN_COLEGIADO.IDPERSONA=SCS_DESIGNASLETRADO.IDPERSONA AND CEN_COLEGIADO.IDINSTITUCION=SCS_DESIGNASLETRADO.IDINSTITUCION");
		sql.JOIN("CEN_PERSONA PERSONA ON PERSONA.IDPERSONA=SCS_DESIGNASLETRADO.IDPERSONA");
		sql.WHERE("SCS_DESIGNASLETRADO.NUMERO = '" + designa.getNumero() + "'");
		sql.WHERE("SCS_DESIGNASLETRADO.IDTURNO = '" + designa.getIdturno() + "'");
		sql.WHERE("SCS_DESIGNASLETRADO.ANIO = '" + designa.getAnio() + "'");
		sql.WHERE("SCS_DESIGNASLETRADO.IDINSTITUCION = '" + idInstitucion + "'");
		if(designa.getNumprocedimiento() != null) {
			sql.WHERE("SCS_DESIGNASLETRADO.IDPERSONA = '" + designa.getNumprocedimiento() + "'");
		}
		sql.ORDER_BY("scs_designasletrado.fechadesigna DESC, scs_designasletrado.fecharenuncia DESC NULLS FIRST, scs_designasletrado.fecharenunciasolicita DESC NULLS FIRST, SCS_DESIGNASLETRADO.LETRADODELTURNO DESC");

		return sql.toString();
	}

	public String obtenerCodigoDesigna(String idInstitucion, String anio) {
		SQL sql = new SQL();

		sql.SELECT("(MAX(to_number(CODIGO)) + 1) AS CODIGO");
		sql.FROM("SCS_DESIGNA");
		sql.WHERE(" IDINSTITUCION ='" + idInstitucion + "'");
		sql.WHERE(" ANIO ='" + anio + "'");

		return sql.toString();
	}

	public String obtenerNumeroDesigna(String idInstitucion, String anio) {
		SQL sql = new SQL();

		sql.SELECT("(MAX(NUMERO) + 1) AS NUMERO");
		sql.FROM("SCS_DESIGNA");
		sql.WHERE(" IDINSTITUCION ='" + idInstitucion + "'");
		sql.WHERE(" ANIO ='" + anio + "'");

		return sql.toString();
	}

	public String getLetradosDiasBajaTemporalTurno(String idInstitucion, String idTurno, String fecha) {

		SQL sql = new SQL();

		sql.SELECT("BAJAS.*");
		sql.FROM(" CEN_BAJASTEMPORALES BAJAS, SCS_INSCRIPCIONTURNO INS ");
		sql.WHERE(" BAJAS.IDINSTITUCION = INS.IDINSTITUCION ");
		sql.WHERE(" BAJAS.IDPERSONA = INS.IDPERSONA ");
		sql.WHERE(" BAJAS.VALIDADO = 1 ");
		sql.WHERE(" BAJAS.ELIMINADO = 0 ");
		sql.WHERE(" INS.IDINSTITUCION ='" + idInstitucion + "'");
		sql.WHERE(" INS.IDTURNO ='" + idTurno + "'");
		sql.WHERE(" BAJAS.FECHABT BETWEEN  TO_DATE('" + fecha
				+ "' , 'YYYY-MM-DD') AND  TO_DATE( '" + fecha + "' , 'YYYY-MM-DD')  ");

		return sql.toString();

	}

	public String getSaltos(String idInstitucion, String idTurno, String idGuardia) {
		SQL sql = new SQL();
		sql.SELECT(" * ");
		sql.FROM(" SCS_SALTOSCOMPENSACIONES ");
		sql.WHERE(" IDINSTITUCION ='" + idInstitucion + "'");
		if (idTurno != null && !idTurno.equals("")) {
			sql.WHERE(" IDTURNO ='" + idTurno + "'");
		}
		if (idGuardia != null && !idGuardia.equals("")) {
			sql.WHERE(" IDGUARDIA ='" + idGuardia + "'");
		} else {
			sql.WHERE(" IDGUARDIA IS NULL ");
		}
		sql.WHERE(" FECHACUMPLIMIENTO IS NULL ");
		sql.WHERE(" FECHA_ANULACION IS NULL ");
		sql.WHERE(" SALTOOCOMPENSACION = 'S'  ");
		sql.ORDER_BY("FECHA, IDSALTOSTURNO");

		return sql.toString();
	}

	public String getCompensaciones(String idInstitucion, String idTurno, String fecha) {

		SQL sql = new SQL();
		sql.SELECT(" IDINSTITUCION "); 
		sql.SELECT(" IDTURNO ");
		sql.SELECT(" IDSALTOSTURNO ");
		sql.SELECT(" IDPERSONA ");
		sql.SELECT(" SALTOOCOMPENSACION ");
		sql.SELECT(" FECHA ");
		sql.SELECT(" IDGUARDIA ");
		sql.SELECT(" MOTIVOS ");
		sql.SELECT(" FECHACUMPLIMIENTO ");
		sql.SELECT(" IDCALENDARIOGUARDIAS ");
		sql.SELECT(" IDCALENDARIOGUARDIASCREACION ");
		sql.SELECT(" TIPOMANUAL ");
		sql.SELECT(" FECHA_ANULACION ");
		sql.FROM(" SCS_SALTOSCOMPENSACIONES ");
		sql.WHERE(" IDINSTITUCION ='" + idInstitucion + "'");
		if (idTurno != null && !idTurno.equals("")) {
			sql.WHERE(" IDTURNO ='" + idTurno + "'");
		}
		sql.WHERE(" IDGUARDIA IS NULL ");
		sql.WHERE(" SALTOOCOMPENSACION = 'C' ");
		sql.WHERE(" FECHACUMPLIMIENTO is NULL ");
		sql.WHERE(" FECHA_ANULACION is NULL ");
		sql.WHERE(" FECHA <= SYSDATE ");
		sql.ORDER_BY("FECHA, IDSALTOSTURNO");

		return sql.toString();
	}

	public String getInscripcionTurnoActiva(String idInstitucion, String idTurno, String idPersona, String fecha) {

		SQL sql = new SQL();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
		//	String fechaFormat = dateFormat.format(fecha);
			

			sql.SELECT(
					" Ins.Idinstitucion,Ins.Idturno, Per.Idpersona,Ins.fechasolicitud,TO_CHAR(TRUNC(Ins.fechavalidacion),'DD/MM/YYYY') As Fechavalidacion,"
							+ "TO_CHAR(trunc(Ins.fechabaja),'DD/MM/YYYY') As Fechabaja,Per.Nombre,Per.Apellidos1,Decode(Per.Apellidos2, Null, '', ' ' || Per.Apellidos2) apellidos2,"
							+ " Per.Apellidos1 || Decode(Per.Apellidos2, Null, '', ' ' || Per.Apellidos2) ALFABETICOAPELLIDOS,  Decode(Col.Comunitario, '1', Col.Ncomunitario, Col.Ncolegiado) NUMEROCOLEGIADO, Per.Fechanacimiento FECHANACIMIENTO,"
							+ "  Ins.Fechavalidacion AS ANTIGUEDADCOLA ");

            sql.FROM("Scs_Inscripcionturno Ins");    
            sql.JOIN("Scs_Turno Tur ON ins.IDINSTITUCION = tur.IDINSTITUCION AND ins.IDTURNO = tur.IDTURNO"); 
            sql.JOIN("Cen_Colegiado Col ON ins.IDINSTITUCION = col.IDINSTITUCION AND ins.IDPERSONA = col.IDPERSONA"); 
            sql.JOIN("Cen_Persona Per ON col.IDPERSONA = per.IDPERSONA"); 
			sql.WHERE(" Ins.Fechavalidacion Is Not Null ");
			sql.WHERE(" Tur.Idinstitucion ='" + idInstitucion + "'");
			sql.WHERE(" Tur.Idturno ='" + idTurno + "'");
			sql.WHERE(" Ins.Fechavalidacion Is Not Null ");
			sql.WHERE(" Trunc(Ins.Fechavalidacion) <= nvl(TO_DATE('" + fecha + "','DD/MM/YYYY'),  Ins.Fechavalidacion)");
			sql.WHERE("(Ins.Fechabaja Is Null Or    Trunc(Ins.Fechabaja) > TO_DATE(nvl('" + fecha + "', '01/01/1900'),'DD/MM/YYYY')) ");
			sql.WHERE(" Ins.idpersona ='" + idPersona + "' and rownum <= 1");
			
		}catch (Exception e) {
			LOGGER.error(e);
		}
	

		return sql.toString();

	}

	public String cambiarUltimoCola(String idInstitucion, String idTurno, String idPersonaUltimo,
			Date fechaSolicitudUltimo, AdmUsuarios usuario) throws ParseException {

		String sIdpersona = (idPersonaUltimo == null) ? "null" : idPersonaUltimo.toString();
		String sFechaSolicitudUltimo = (fechaSolicitudUltimo == null) ? "null"
				: fechaSolicitudUltimo.toString();

		Format formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String fechaBBDD2 = formatter.format(fechaSolicitudUltimo);

		SQL sql = new SQL();

		sql.UPDATE("SCS_TURNO");

		sql.SET("IDPERSONA_ULTIMO = '" + sIdpersona + "'");
		sql.SET("FECHASOLICITUD_ULTIMO = TO_DATE('" + fechaBBDD2 + "' , 'YYYY/MM/DD HH24:MI:SS') ");
		sql.SET("USUMODIFICACION = '" + usuario.getIdusuario() + "'");
		sql.SET("FECHAMODIFICACION = SYSTIMESTAMP");

		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("IDTURNO = '" + idTurno + "'");

		return sql.toString();
	}

	/**
	 * Obtiene de BD las inscripciones ordenadas para formar la cola de un turno
	 * dada una fecha
	 * 
	 * @param idinstitucion
	 * @param idturno
	 * @param fecha
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public String getColaTurnoBBDD(String idinstitucion, String idturno, String fecha, String order) throws Exception {
		try {
			if (idinstitucion == null || idinstitucion.equals(""))
				return null;
			if (idturno == null || idturno.equals(""))
				return null;
			if (fecha == null || fecha.equals(""))
				fecha = "null";
			else if (!fecha.trim().equalsIgnoreCase("sysdate"))
				fecha = "'" + fecha.trim() + "'";

			String consulta = "Select " + "       (case when Ins.Fechavalidacion Is Not Null "
					+ "              And Trunc(Ins.Fechavalidacion) <= TO_DATE(nvl(" + fecha + ",  Ins.Fechavalidacion),'DD/MM/YYYY') "
					+ "              And (Ins.Fechabaja Is Null Or " + " Trunc(Ins.Fechabaja) > TO_DATE(nvl(" + fecha + ", '01/01/1900'),'DD/MM/YYYY')) "
					+ "             then '1' " + "             else '0' "
					+ "        end) Activo, " + " Ins.Idinstitucion," + "       Ins.Idturno, "
					+ " TO_CHAR(TRUNC(Ins.fechavalidacion),'DD/MM/YYYY') AS fechavalidacion, "
					+ "   TO_CHAR(trunc(Ins.fechabaja),'DD/MM/YYYY') AS fechabaja, "
					+ "    Ins.fechasolicitud AS fechaSolicitud, " + "       Per.Nifcif," + "       Per.Idpersona,"
					+ "       Per.Nombre, " + "       Per.Apellidos1, "
					+ "       Decode(Per.Apellidos2, Null, '', ' ' || Per.Apellidos2) apellidos2, "
					+ "       Decode(Col.Comunitario, '1', Col.Ncomunitario, Col.Ncolegiado) Ncolegiado, " +

					"       Per.Apellidos1 || "
					+ "       Decode(Per.Apellidos2, Null, '', ' ' || Per.Apellidos2) ALFABETICOAPELLIDOS, "
					+ "       Decode(Col.Comunitario, '1', Col.Ncomunitario, Col.Ncolegiado) NUMEROCOLEGIADO, "
					+ "       Per.Fechanacimiento FECHANACIMIENTO, " + "       Ins.Fechavalidacion ANTIGUEDADCOLA "
					+ "  From Scs_Turno              Tur, " + "       Cen_Colegiado          Col, "
					+ "       Cen_Persona            Per, " + "       Scs_Inscripcionturno   Ins "
					+ " Where Col.Idpersona = Per.Idpersona " + "   And Ins.Idinstitucion = Tur.Idinstitucion "
					+ "   And Ins.Idturno = Tur.Idturno " + "   And Ins.Idinstitucion = Col.Idinstitucion "
					+ "   And Ins.Idpersona = Col.Idpersona " +

					// cuando no se pasa fecha, se sacan todas las validadas (en cualquier fecha)
					"   And Ins.Fechavalidacion Is Not Null " +
					/*
					 * "   And Trunc(Ins.Fechavalidacion) <= nvl("+fecha+",  Ins.Fechavalidacion) "
					 * + //cuando no se pasa fecha, se sacan aunque esten de baja
					 * "   And (Ins.Fechabaja Is Null Or   Trunc(Ins.Fechabaja) > nvl("
					 * +fecha+", '01/01/1900')) " +
					 */
					"   And Tur.Idinstitucion = " + idinstitucion + " " + "   And Tur.Idturno = " + idturno + " ";

			consulta += " order by " + order;
			// Para el caso de que coincida el orden establecido, a�adimos un orden que
			// siempre deberia ser diferente: la fecha de suscripcion
			consulta += ", Ins.fechasolicitud, Ins.Idpersona ";

			return consulta;

		} catch (Exception e) {
			throw new Exception("Error al ejecutar getColaTurno()", e);
		}
	}

	public String busquedaComunicaciones(String num, String anio, String idturno, Short idInstitucion, String idLenguaje) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();
		SQL sql3 = new SQL();
		SQL sqlTipoEnvio = new SQL();
		SQL sqlEstadosEnvio = new SQL();
		
        //subquery tipo envio
        sqlTipoEnvio.SELECT("cat.descripcion");
        sqlTipoEnvio.FROM("env_tipoenvios");
        sqlTipoEnvio.LEFT_OUTER_JOIN("gen_recursos_catalogos cat ON (cat.idrecurso = env_tipoenvios.nombre)");
        sqlTipoEnvio.WHERE("env_tipoenvios.idtipoenvios = e.idtipoenvios");
        sqlTipoEnvio.WHERE("cat.idlenguaje = '"+idLenguaje+"'");
        
        //subquery estadosEnvios
        sqlEstadosEnvio.SELECT("cat.descripcion");
        sqlEstadosEnvio.FROM("env_estadoenvio estado");
        sqlEstadosEnvio.LEFT_OUTER_JOIN("gen_recursos_catalogos cat ON (cat.idrecurso = estado.nombre)");
        sqlEstadosEnvio.WHERE("estado.idestado = e.idestado");
        sqlEstadosEnvio.WHERE("cat.idlenguaje = '"+idLenguaje+"'");
		
		sql3.SELECT("c.idenviosalida");
        sql3.SELECT("c.idinstitucion");
        sql3.FROM("scs_comunicaciones c");
		sql3.WHERE("c.idinstitucion = '"+idInstitucion+"'");
		sql3.WHERE("c.designaanio = "+anio);
		sql3.WHERE("c.designaidturno = "+idturno);
		sql3.WHERE("c.designanumero = '"+num+"'");
        
		sql2.SELECT("e.*");
		sql2.SELECT("(dest.nombre || ' ' || dest.apellidos1 || ' ' || dest.apellidos2) AS destinatario");
		sql2.SELECT("("+sqlTipoEnvio.toString()+") as tipoenvio");
		sql2.SELECT("("+sqlEstadosEnvio.toString()+") as estadoenvio");
		sql2.SELECT("nvl(camposenviosasunto.valor, plantilla.asunto) AS asunto");
		sql2.SELECT("nvl(camposenvioscuerpo.valor, plantilla.cuerpo) AS cuerpo");
		sql2.FROM("env_envios e");
		sql2.LEFT_OUTER_JOIN("env_destinatarios dest on (dest.idenvio=e.idenvio and dest.idinstitucion =e.idinstitucion)");
		sql2.LEFT_OUTER_JOIN("env_plantillasenvios plantilla ON (plantilla.idinstitucion = '"+idInstitucion+"' AND plantilla.idplantillaenvios = e.idplantillaenvios"
				+ " AND plantilla.idtipoenvios = e.idtipoenvios)");
		sql2.LEFT_OUTER_JOIN("env_camposenvios camposenviosasunto ON (e.idenvio = camposenviosasunto.idenvio AND camposenviosasunto.idinstitucion = e.idinstitucion"
				+ " AND camposenviosasunto.idcampo = 1)");
		sql2.LEFT_OUTER_JOIN("env_camposenvios camposenvioscuerpo ON (e.idenvio = camposenvioscuerpo.idenvio AND camposenvioscuerpo.idinstitucion = e.idinstitucion"
				+ " AND camposenvioscuerpo.idcampo = 2)");
		
		sql2.WHERE("e.fechabaja IS NULL");
		sql2.WHERE("(e.idenvio,e.idinstitucion) IN ("+sql3.toString()+")");
		
		sql.SELECT("*");
		sql.FROM("("+sql2.toString()+")");

		return sql.toString();
	}

	public String obtenerIdPersonaByNumCol(String idInstitucion, String numColegiado) {
		SQL sql = new SQL();

		sql.SELECT("IDPERSONA ");
		sql.FROM("CEN_COLEGIADO");
		sql.WHERE(" IDINSTITUCION ='" + idInstitucion + "'");
		sql.WHERE(" NCOLEGIADO ='" + numColegiado + "'");

		return sql.toString();
	}
	public String obtenerIdPersonaByNumComunitario(String idInstitucion, String numColegiado) {
		SQL sql = new SQL();

		sql.SELECT("IDPERSONA ");
		sql.FROM("CEN_COLEGIADO");
		sql.WHERE(" IDINSTITUCION ='" + idInstitucion + "'");
		sql.WHERE(" NCOMUNITARIO ='" + numColegiado + "'");

		return sql.toString();
	}
	
	public String obtenerIdPersonaByNumColNColegiado(String nif) {
		SQL sql = new SQL();

		sql.SELECT("IDPERSONA ");
		sql.FROM("CEN_PERSONA");
		sql.WHERE(" NIFCIF ='" + nif + "'");

		return sql.toString();
	}
	
	public String obtenerNumNoColegiado(String idInstitucion, String idPersona) {
		SQL sql = new SQL();

		sql.SELECT("*");
		sql.FROM("CEN_NOCOLEGIADO");
		sql.WHERE(" IDINSTITUCION ='" + idInstitucion + "'");
		sql.WHERE(" IDPERSONA ='" + idPersona + "'");

		return sql.toString();
	}

	public String comboAcreditacionesPorTipo(Short idInstitucion, String idProcedimiento) {

		SQL sql = new SQL();
		SQL sql2 = new SQL();
		SQL sql3 = new SQL();

		// Query para obtener los tipos de acreditaciones
		sql3.SELECT("IDTIPOACREDITACION");
		sql3.FROM("SCS_TIPOACREDITACION");

		// Query para quedarnos con los tres primeros registros de los tipos de
		// acreditaciones
		sql2.SELECT("*");
		sql2.FROM("( " + sql3.toString() + " )");
		sql2.WHERE("ROWNUM <= 3");

		// Query principal
		sql.SELECT(
				"ACRE.IDACREDITACION || ',' || DECODE(TO_CHAR(ACREP.NIG_NUMPROCEDIMIENTO), TO_CHAR(TRUNC(ACREP.NIG_NUMPROCEDIMIENTO)), TO_CHAR(ACREP.NIG_NUMPROCEDIMIENTO), F_SIGA_FORMATONUMERO(TO_CHAR(ACREP.NIG_NUMPROCEDIMIENTO), 0)) AS ID");
		sql.SELECT(
				"ACRE.DESCRIPCION || ' (' || DECODE(TO_CHAR(ACREP.PORCENTAJE), TO_CHAR(TRUNC(ACREP.PORCENTAJE) ), TO_CHAR(ACREP.PORCENTAJE), F_SIGA_FORMATONUMERO(TO_CHAR(ACREP.PORCENTAJE), 2)) || '%)' AS DESCRIPCION");
		sql.FROM("SCS_ACREDITACIONPROCEDIMIENTO ACREP");
		sql.JOIN("SCS_ACREDITACION ACRE ON ACRE.IDACREDITACION = ACREP.IDACREDITACION");
		sql.WHERE("ACREP.IDPROCEDIMIENTO = '" + idProcedimiento + "'");
		sql.WHERE("ACREP.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("ACRE.IDTIPOACREDITACION IN ( " + sql2.toString() + " )");

		return sql.toString();
	}

	public String getNewIdDocumentacionAsi(Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("NVL(MAX(DOC.IDDOCUMENTACIONASI),0) +1 AS ID");

		sql.FROM("SCS_DOCUMENTACIONASI DOC");

		sql.WHERE("DOC.IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}

	public String getDocumentosPorActDesigna(DocumentoActDesignaItem documentoActDesignaItem, Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("DOC.IDDOCUMENTACIONASI");
		sql.SELECT("DOC.IDTIPODOCUMENTO");
		sql.SELECT("TIPODOC.NOMBRE AS NOMBRETIPODOCUMENTO");
		sql.SELECT("DOC.IDFICHERO");
		sql.SELECT("DOC.IDINSTITUCION");
		sql.SELECT("DOC.ANIO");
		sql.SELECT("DOC.NUMERO");
		sql.SELECT("DOC.IDACTUACION");
		sql.SELECT("DOC.USUMODIFICACION");
		sql.SELECT("DOC.FECHAMODIFICACION");
		sql.SELECT("DOC.FECHAENTRADA");
		sql.SELECT("DOC.OBSERVACIONES");
		sql.SELECT("DOC.NOMBREFICHERO");
		sql.SELECT("P.IDPERSONA");

		sql.FROM("SCS_DOCUMENTACIONASI DOC");

		sql.JOIN("SCS_TIPODOCUMENTOASI TIPODOC ON TIPODOC.IDTIPODOCUMENTOASI = DOC.IDTIPODOCUMENTO");
		sql.JOIN("ADM_USUARIOS ADM ON ADM.IDUSUARIO = DOC.USUMODIFICACION AND ADM.IDINSTITUCION = DOC.IDINSTITUCION");
		sql.JOIN("CEN_PERSONA P ON ADM.NIF = P.NIFCIF");

		sql.WHERE("DOC.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("DOC.NUMERO ='" + documentoActDesignaItem.getNumero() + "'");
		sql.WHERE("DOC.ANIO ='" + documentoActDesignaItem.getAnio() + "'");
		sql.WHERE("DOC.IDACTUACION ='" + documentoActDesignaItem.getIdActuacion() + "'");

		sql.ORDER_BY("DOC.FECHAENTRADA DESC");

		return sql.toString();
	}

	public String getDocumentosPorDesigna(DocumentoDesignaItem documentoDesignaItem, Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("DOCD.IDDOCUMENTACIONDES");
		sql.SELECT("DOCD.IDTIPODOCUMENTO");
		sql.SELECT(
				"(SELECT F_SIGA_GETRECURSO(E.NOMBRE, 1) FROM SCS_TIPODOCUMENTODES E WHERE E.IDTIPODOCUMENTODES = DOCD.IDTIPODOCUMENTO) AS NOMBRETIPODOCUMENTO");
		sql.SELECT("DOCD.IDFICHERO");
		sql.SELECT("DOCD.IDINSTITUCION");
		sql.SELECT("DOCD.IDTURNO");
		sql.SELECT("DOCD.ANIO");
		sql.SELECT("DOCD.NUMERO");
		sql.SELECT("DOCD.IDACTUACION");
		sql.SELECT("DOCD.USUMODIFICACION");
		sql.SELECT("DOCD.FECHAMODIFICACION");
		sql.SELECT("DOCD.FECHAENTRADA");
		sql.SELECT("DOCD.OBSERVACIONES");
		sql.SELECT("DOCD.NOMBREFICHERO");
		sql.SELECT("(SELECT IDPERSONA FROM CEN_PERSONA P WHERE P.NIFCIF = ADM.NIF) AS IDPERSONA");

		sql.FROM("SCS_DOCUMENTACIONDESIGNA DOCD");

		sql.JOIN("SCS_TIPODOCUMENTODES TIPODOC ON TIPODOC.IDTIPODOCUMENTODES = DOCD.IDTIPODOCUMENTO");
		sql.JOIN("ADM_USUARIOS ADM ON ADM.IDUSUARIO = DOCD.USUMODIFICACION AND ADM.IDINSTITUCION = DOCD.IDINSTITUCION");
		//sql.JOIN("CEN_PERSONA P ON ADM.NIF = P.NIFCIF");
		
		if(!UtilidadesString.esCadenaVacia(documentoDesignaItem.getIdActuacion())) {
			sql.WHERE("DOCD.IDACTUACION = '" + documentoDesignaItem.getIdActuacion() + "'");
		}

		sql.WHERE("DOCD.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("DOCD.NUMERO ='" + documentoDesignaItem.getNumero() + "'");
		sql.WHERE("DOCD.ANIO ='" + documentoDesignaItem.getAnio() + "'");
		sql.WHERE("DOCD.IDTURNO ='" + documentoDesignaItem.getIdTurno() + "'");

		return sql.toString();
	}

	public String comboTipoDocumentacionDesigna() {
		SQL sql = new SQL();

		sql.SELECT("E.IDTIPODOCUMENTODES");
		sql.SELECT("F_SIGA_GETRECURSO(E.NOMBRE, 1) AS NOMBRE");

		sql.FROM("SCS_TIPODOCUMENTODES E");

		return sql.toString();
	}

	public String getNewIdDocumentacionDes(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("NVL(MAX(DOC.IDDOCUMENTACIONDES),0) +1 AS ID");

		sql.FROM("SCS_DOCUMENTACIONDESIGNA DOC");

		sql.WHERE("DOC.IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}

	
	public String comprobarCodigoDesigna(String idInstitucion, String anio, String idTurno,  String codigo) {
		SQL sql = new SQL();

		sql.SELECT("CODIGO");
		sql.FROM("SCS_DESIGNA");
		sql.WHERE(" IDINSTITUCION ='" + idInstitucion + "'");
		sql.WHERE(" ANIO ='" + anio + "'");
		sql.WHERE(" CODIGO ='" + codigo + "'");

		return sql.toString();
	}
	
	public String busquedaJuzgadoDesignas(Integer idJuzgado, Short idInstitucion, Integer tamMax)
			throws Exception {
		SQL sql = new SQL();

		sql.SELECT("NOMBRE");

		sql.FROM("SCS_JUZGADO");

		sql.WHERE(" IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("IDJUZGADO = '" + idJuzgado + "'");
		//sql.WHERE("FECHABAJA IS NULL");

		return sql.toString();

	}
	
	public String actualizarPartidaPresupuestariaActDesigna(ActuacionDesignaItem actuacionDesignaItem,
			Short idInstitucion, AdmUsuarios usuario) {

		SQL sql = new SQL();

		sql.UPDATE("SCS_ACTUACIONDESIGNA");

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdPartidaPresupuestaria())) {
			sql.SET("IDPARTIDAPRESUPUESTARIA = '" + actuacionDesignaItem.getIdPartidaPresupuestaria() + "'");
		} else {
			sql.SET("IDPARTIDAPRESUPUESTARIA = NULL");
		}
		
		sql.SET("FECHAMODIFICACION = SYSDATE");
		sql.SET("USUMODIFICACION = '" + usuario.getIdusuario() + "'");

		sql.WHERE("NUMERO = '" + actuacionDesignaItem.getNumero() + "'");
		sql.WHERE("IDTURNO = '" + actuacionDesignaItem.getIdTurno() + "'");
		sql.WHERE("ANIO = '" + actuacionDesignaItem.getAnio() + "'");
		sql.WHERE("NUMEROASUNTO = '" + actuacionDesignaItem.getNumeroAsunto() + "'");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();

	}

	public String actualizarPartidaPresupuestariaDesigna(DesignaItem designaItem, Short idInstitucion,
			AdmUsuarios usuario) {

		SQL sql = new SQL();

		sql.UPDATE("SCS_DESIGNA");

		if (!UtilidadesString.esCadenaVacia(designaItem.getIdPartidaPresupuestaria())) {
			sql.SET("IDPARTIDAPRESUPUESTARIA = '" + designaItem.getIdPartidaPresupuestaria() + "'");
		} else {
			sql.SET("IDPARTIDAPRESUPUESTARIA = NULL");
		}
		
		sql.SET("FECHAMODIFICACION = SYSDATE");
		sql.SET("USUMODIFICACION = '" + usuario.getIdusuario() + "'");

		sql.WHERE("NUMERO = '" + designaItem.getNumero() + "'");
		sql.WHERE("IDTURNO = '" + designaItem.getIdTurno() + "'");
		sql.WHERE("ANIO = '" + designaItem.getAno() + "'");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();

	}
	
	public String getDelitos(Short idInstitucion, DesignaItem designaItem) {
		SQL sql = new SQL();

		sql.SELECT("IDDELITO");

		sql.FROM("SCS_DELITOSDESIGNA");

		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		sql.WHERE("IDTURNO = '" + designaItem.getIdTurno() + "'");
		sql.WHERE("ANIO = '" + designaItem.getAno() + "'");
		sql.WHERE("NUMERO = '" + designaItem.getNumero() + "'");

		return sql.toString();
	}
	
	public String getDefendidosDesigna (String idInstitucion, String numero, String idTurno, String anio, String idPersonaJG, String idPersona, String longitudNumEjg) {
		SQL sql = new SQL();
		
		sql.SELECT("def.idinstitucion,\r\n" + 
				"	    def.idturno,\r\n" + 
				"	    def.anio,\r\n" + 
				"	    def.numero,\r\n" + 
				"	    perjg.idpersona           idpersonainteresado,\r\n" + 
				"	    perjg.nombre\r\n" + 
				"	    || ' '\r\n" + 
				"	    || perjg.apellido1\r\n" + 
				"	    || ' '\r\n" + 
				"	    || perjg.apellido2 AS nombre_defendido,\r\n" + 
				"	    decode(perjg.direccion, NULL, NULL,((\r\n" + 
				"	        SELECT\r\n" + 
				"	            (upper(substr(f_siga_getrecurso(tv.descripcion, 1), 1, 1)))\r\n" + 
				"	            ||(lower(substr(f_siga_getrecurso(tv.descripcion, 1), 2)))\r\n" + 
				"	        FROM\r\n" + 
				"	            cen_tipovia tv\r\n" + 
				"	        WHERE\r\n" + 
				"	            tv.idtipovia = perjg.idtipovia\r\n" + 
				"	            AND tv.idinstitucion = perjg.idinstitucion\r\n" + 
				"	    )\r\n" + 
				"	                                        || ' '\r\n" + 
				"	                                        || perjg.direccion\r\n" + 
				"	                                        || ' '\r\n" + 
				"	                                        || perjg.numerodir\r\n" + 
				"	                                        || ' '\r\n" + 
				"	                                        || perjg.escaleradir\r\n" + 
				"	                                        || ' '\r\n" + 
				"	                                        || perjg.pisodir\r\n" + 
				"	                                        || ' '\r\n" + 
				"	                                        || perjg.puertadir)) AS domicilio_defendido,\r\n" + 
				"	    perjg.codigopostal        AS cp_defendido,\r\n" + 
				"	    pob.nombre                AS poblacion_defendido,\r\n" + 
				"	    prov.nombre               AS provincia_defendido,\r\n" + 
				"	    pais.nombre               AS nombre_pais,\r\n" + 
				"	    perjg.observaciones       AS obs_defendido,\r\n" + 
				"	    (\r\n" + 
				"	        SELECT\r\n" + 
				"	            tel2.numerotelefono\r\n" + 
				"	        FROM\r\n" + 
				"	            scs_telefonospersona tel2\r\n" + 
				"	        WHERE\r\n" + 
				"	            tel2.idinstitucion = perjg.idinstitucion\r\n" + 
				"	            AND tel2.idpersona = perjg.idpersona\r\n" + 
				"	            AND ROWNUM < 2\r\n" + 
				"	    ) AS telefono1_defendido,\r\n" + 
				"	    replace((\r\n" + 
				"	        SELECT\r\n" + 
				"	            wmsys.wm_concat(ltel.nombretelefono\r\n" + 
				"	                            || ':'\r\n" + 
				"	                            || ltel.numerotelefono)\r\n" + 
				"	        FROM\r\n" + 
				"	            scs_telefonospersona ltel\r\n" + 
				"	        WHERE\r\n" + 
				"	            ltel.idinstitucion = perjg.idinstitucion\r\n" + 
				"	            AND ltel.idpersona = perjg.idpersona\r\n" + 
				"	    ), ',', ', ') AS lista_telefonos_interesado,\r\n" + 
				"	    perjg.nif                 AS nif_defendido,\r\n" + 
				"	    decode(perjg.sexo, NULL, NULL, 'M', 'gratuita.personaEJG.sexo.mujer',\r\n" + 
				"	           'gratuita.personaEJG.sexo.hombre') AS sexo_defendido,\r\n" + 
				"	    decode(perjg.sexo, NULL, NULL, 'M', f_siga_getrecurso_etiqueta('gratuita.personaEJG.sexo.mujer', 1), f_siga_getrecurso_etiqueta\r\n" + 
				"	    ('gratuita.personaEJG.sexo.hombre', 1)) AS sexo_defendido_descripcion,\r\n" + 
				"	    decode(perjg.sexo, 'H', 'o', 'a') AS o_a_defendido,\r\n" + 
				"	    decode(perjg.sexo, 'H', 'el', 'la') AS el_la_defendido,\r\n" + 
				"	    perjg.idlenguaje          AS idlenguaje_defendido,\r\n" + 
				"	    (\r\n" + 
				"	        SELECT\r\n" + 
				"	            ejg.anio\r\n" + 
				"	        FROM\r\n" + 
				"	            scs_ejg                 ejg,\r\n" + 
				"	            scs_ejgdesigna          ejgdes,\r\n" + 
				"	            scs_unidadfamiliarejg   ufa\r\n" + 
				"	        WHERE\r\n" + 
				"	            def.idinstitucion = ejgdes.idinstitucion\r\n" + 
				"	            AND def.idturno = ejgdes.idturno\r\n" + 
				"	            AND def.anio = ejgdes.aniodesigna\r\n" + 
				"	            AND def.numero = ejgdes.numerodesigna\r\n" + 
				"	            AND ejgdes.idinstitucion = ejg.idinstitucion\r\n" + 
				"	            AND ejgdes.idtipoejg = ejg.idtipoejg\r\n" + 
				"	            AND ejgdes.anioejg = ejg.anio\r\n" + 
				"	            AND ejgdes.numeroejg = ejg.numero\r\n" + 
				"	            AND ejg.idinstitucion = ufa.idinstitucion\r\n" + 
				"	            AND ejg.idtipoejg = ufa.idtipoejg\r\n" + 
				"	            AND ejg.anio = ufa.anio\r\n" + 
				"	            AND ejg.numero = ufa.numero\r\n" + 
				"	            AND def.idinstitucion = ufa.idinstitucion\r\n" + 
				"	            AND def.idpersona = ufa.idpersona\r\n" + 
				"	            AND ROWNUM = 1\r\n" + 
				"	    ) AS anio_ejg,\r\n" + 
				"	    (\r\n" + 
				"	        SELECT\r\n" + 
				"	            ( ejg.anio\r\n" + 
				"	              || '/'\r\n" + 
				"	              || lpad(ejg.numejg, 5, 0) )\r\n" + 
				"	        FROM\r\n" + 
				"	            scs_ejg                 ejg,\r\n" + 
				"	            scs_ejgdesigna          ejgdes,\r\n" + 
				"	            scs_unidadfamiliarejg   ufa\r\n" + 
				"	        WHERE\r\n" + 
				"	            def.idinstitucion = ejgdes.idinstitucion\r\n" + 
				"	            AND def.idturno = ejgdes.idturno\r\n" + 
				"	            AND def.anio = ejgdes.aniodesigna\r\n" + 
				"	            AND def.numero = ejgdes.numerodesigna\r\n" + 
				"	            AND ejgdes.idinstitucion = ejg.idinstitucion\r\n" + 
				"	            AND ejgdes.idtipoejg = ejg.idtipoejg\r\n" + 
				"	            AND ejgdes.anioejg = ejg.anio\r\n" + 
				"	            AND ejgdes.numeroejg = ejg.numero\r\n" + 
				"	            AND ejg.idinstitucion = ufa.idinstitucion\r\n" + 
				"	            AND ejg.idtipoejg = ufa.idtipoejg\r\n" + 
				"	            AND ejg.anio = ufa.anio\r\n" + 
				"	            AND ejg.numero = ufa.numero\r\n" + 
				"	            AND def.idinstitucion = ufa.idinstitucion\r\n" + 
				"	            AND def.idpersona = ufa.idpersona\r\n" + 
				"	            AND ROWNUM = 1\r\n" + 
				"	    ) AS numero_ejg,\r\n" + 
				"	    (\r\n" + 
				"	        SELECT\r\n" + 
				"	            to_char(ejg.fecharesolucioncajg, 'dd/mm/yyyy')\r\n" + 
				"	        FROM\r\n" + 
				"	            scs_ejg                 ejg,\r\n" + 
				"	            scs_ejgdesigna          ejgdes,\r\n" + 
				"	            scs_unidadfamiliarejg   ufa\r\n" + 
				"	        WHERE\r\n" + 
				"	            def.idinstitucion = ejgdes.idinstitucion\r\n" + 
				"	            AND def.idturno = ejgdes.idturno\r\n" + 
				"	            AND def.anio = ejgdes.aniodesigna\r\n" + 
				"	            AND def.numero = ejgdes.numerodesigna\r\n" + 
				"	            AND ejgdes.idinstitucion = ejg.idinstitucion\r\n" + 
				"	            AND ejgdes.idtipoejg = ejg.idtipoejg\r\n" + 
				"	            AND ejgdes.anioejg = ejg.anio\r\n" + 
				"	            AND ejgdes.numeroejg = ejg.numero\r\n" + 
				"	            AND ejg.idinstitucion = ufa.idinstitucion\r\n" + 
				"	            AND ejg.idtipoejg = ufa.idtipoejg\r\n" + 
				"	            AND ejg.anio = ufa.anio\r\n" + 
				"	            AND ejg.numero = ufa.numero\r\n" + 
				"	            AND def.idinstitucion = ufa.idinstitucion\r\n" + 
				"	            AND def.idpersona = ufa.idpersona\r\n" + 
				"	            AND ROWNUM = 1\r\n" + 
				"	    ) AS fecharesolucioncajg,\r\n" + 
				"	    (\r\n" + 
				"	        SELECT\r\n" + 
				"	            COUNT(1)\r\n" + 
				"	        FROM\r\n" + 
				"	            scs_ejgdesigna ejgdes\r\n" + 
				"	        WHERE\r\n" + 
				"	            ejgdes.idinstitucion = " +  idInstitucion + "\r\n" + 
				"	            AND ejgdes.idturno = " + idTurno + "\r\n" + 
				"	            AND ejgdes.aniodesigna = " + anio + "\r\n" + 
				"	            AND ejgdes.numerodesigna = " + numero + "\r\n" + 
				"	    ) count_ejg,\r\n" + 
				"	    cal.descripcion           AS calidad_defendido,\r\n" + 
				"	    cal.idtipoencalidad,\r\n" + 
				"	    perjg.idrepresentantejg   idrepresentantejg");
	sql.FROM(" scs_defendidosdesigna   def,\r\n" + 
			"	    scs_personajg           perjg,\r\n" + 
			"	    scs_tipoencalidad       cal,\r\n" + 
			"	    cen_poblaciones         pob,\r\n" + 
			"	    cen_provincias          prov,\r\n" + 
			"	    cen_pais                pais");
	sql.WHERE("	    perjg.idinstitucion = def.idinstitucion\r\n" + 
			"	    AND perjg.idpersona = def.idpersona\r\n" + 
			"	    AND cal.idtipoencalidad (+) = def.idtipoencalidad\r\n" + 
			"	    AND cal.idinstitucion (+) = def.idinstitucion\r\n" + 
			"	    AND perjg.idpoblacion = pob.idpoblacion (+)\r\n" + 
			"	    AND perjg.idprovincia = prov.idprovincia (+)\r\n" + 
			"	    AND perjg.idpais = pais.idpais (+)\r\n" + 
			"	    AND def.idinstitucion = " + idInstitucion +"\r\n" + 
			"	    AND def.idturno = " + idTurno +" \r\n" + 
			"	    AND def.anio = " + anio + "\r\n" + 
			"	    AND def.numero = " + numero + "\r\n" + 
			"");
	if (idPersonaJG != null && !idPersonaJG.trim().equals("")) {
		sql.WHERE(" PERJG.IDPERSONA = " + idPersonaJG +" ");
	}
	sql.ORDER_BY("perjg.idpersona");
		
		return sql.toString();
	}
	
	public String getDatosEjgResolucionFavorable (String idInstitucion, String numero, String idTurno, String anio) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();
		
		sql.SELECT(" Per.Nombre");
		sql.SELECT(" Per.Apellido1");
		sql.SELECT(" Nvl(Per.Apellido2, '') Apellido2 ");
		sql2.FROM("  Scs_Ejgdesigna Ejgdes, Scs_Ejg Ejg, Scs_Personajg Per ");
		sql2.WHERE(" Ejgdes.Idinstitucion = Ejg.Idinstitucion ");
		sql2.WHERE(" Ejgdes.Idtipoejg = Ejg.Idtipoejg ");
		sql2.WHERE(" Ejgdes.Anioejg = Ejg.Anio ");
		sql2.WHERE(" Ejgdes.Numeroejg = Ejg.Numero ");
		sql2.WHERE(" Ejg.Idinstitucion = Per.Idinstitucion ");
		sql2.WHERE(" Ejg.Idpersonajg = Per.Idpersona ");
		sql2.WHERE(" Ejgdes.Idinstitucion = " + idInstitucion);
		sql2.WHERE(" Ejgdes.Idturno = " + idTurno);
		sql2.WHERE(" Ejgdes.Aniodesigna= " + anio);
		sql2.WHERE(" Ejgdes.Numerodesigna  = " + numero);
		sql2.WHERE(" ( (EJG.Fecharesolucioncajg is not null and EJG.Idtiporatificacionejg in (1, 2, 8, 10, 9, 11)) "+
				"  OR (EJG.Idtiporatificacionejg is null and EJG.Fecharesolucioncajg is null)) ");
		
		sql.SELECT(" Per.Nombre");
		sql.SELECT(" Per.Apellido1");
		sql.SELECT(" Nvl(Per.Apellido2, '') Apellido2 ");
		sql.FROM("  From Scs_Ejgdesigna        Ejgdes, " +
				" Scs_Unidadfamiliarejg Uniejg, "+
				" Scs_Personajg         Per, "+
				" Scs_Ejg Ejg ");
		sql.WHERE(" Ejgdes.Idinstitucion = Uniejg.Idinstitucion ");
		sql.WHERE(" Ejgdes.Idtipoejg = Uniejg.Idtipoejg ");
		sql.WHERE(" Ejgdes.Anioejg = Uniejg.Anio ");
		sql.WHERE(" Ejgdes.Numeroejg = Uniejg.Numero ");
		sql.WHERE(" Uniejg.Idinstitucion = Per.Idinstitucion ");
		sql.WHERE(" Uniejg.Idpersona = Per.Idpersona ");
		sql.WHERE(" Ejgdes.Idinstitucion = " + idInstitucion);
		sql.WHERE(" Ejgdes.Idturno = " + idTurno);
		sql.WHERE(" Ejgdes.Aniodesigna = " + anio);
		sql.WHERE(" Ejgdes.Numerodesigna = " + numero);
		sql.WHERE("  Ejgdes.Idinstitucion = Ejg.Idinstitucion ");
		sql.WHERE(" Ejgdes.Idtipoejg = Ejg.Idtipoejg ");
		sql.WHERE(" Ejgdes.Anioejg = Ejg.Anio ");
		sql.WHERE(" Ejgdes.Numeroejg = Ejg.Numero ");
		sql.WHERE(" ( (EJG.Fecharesolucioncajg is not null and EJG.Idtiporatificacionejg in (1, 2, 8, 10, 9, 11)) "+
				" OR (EJG.Idtiporatificacionejg is null and EJG.Fecharesolucioncajg is null))  Union " + sql2);
		sql.ORDER_BY(" Apellido1, Apellido2, Nombre ");
	
		return sql.toString();
	}
}
