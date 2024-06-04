package org.itcgae.siga.db.services.scs.providers;

import java.text.SimpleDateFormat;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.ModulosItem;
import org.itcgae.siga.db.mappers.ScsProcedimientosSqlProvider;

public class ScsProcedimientosSqlExtendsProvider extends ScsProcedimientosSqlProvider {

	public String searchProcess(String idLenguaje, Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("proc.idinstitucion");
		sql.SELECT("proc.idprocedimiento");
		sql.SELECT("proc.nombre");
		sql.SELECT("proc.codigo");
		sql.SELECT("proc.precio as importe");
		sql.SELECT("juris.descripcion as jurisdiccion");

		sql.FROM("SCS_PROCEDIMIENTOS proc");
		sql.INNER_JOIN("SCS_JURISDICCION jurisdiccion on jurisdiccion.IDJURISDICCION =  proc.IDJURISDICCION");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS juris on (juris.idrecurso = jurisdiccion.DESCRIPCION and idlenguaje = '"
				+ idLenguaje + "')");
		;

		sql.WHERE("proc.idinstitucion = '" + idInstitucion + "'");

		sql.ORDER_BY("proc.nombre");

		return sql.toString();
	}

	public String getProcedimientos(String idInstitucion, String idProcedimiento, String idioma) {

		String sql = "";
		SQL sql1 = new SQL();
		SQL sql2 = new SQL();

		sql1.SELECT("pretension.idinstitucion");
		sql1.SELECT("pretension.idpretension");
		sql1.SELECT("f_siga_getrecurso(pretension.descripcion, " + idioma + ") nombre");
		sql1.FROM("SCS_PROCEDIMIENTOS procedimiento");
		sql1.LEFT_OUTER_JOIN(
				"SCS_PRETENSIONESPROCED prepro on (prepro.idprocedimiento = procedimiento.idprocedimiento AND PREPRO.IDINSTITUCION = PROCEDIMIENTO.IDINSTITUCION)");
		sql1.LEFT_OUTER_JOIN(
				"SCS_PRETENSION pretension on (prepro.idpretension = pretension.idpretension AND pretension.IDINSTITUCION = PROCEDIMIENTO.IDINSTITUCION)");
		sql1.WHERE("procedimiento.idinstitucion = '" + idInstitucion + "'");
		if (!idProcedimiento.equals("")) {
			sql1.WHERE("procedimiento.idprocedimiento = '" + idProcedimiento + "'");
		}
		sql2.SELECT("pretension.idinstitucion");
		sql2.SELECT("pretension.idpretension");
		sql2.SELECT("f_siga_getrecurso(pretension.descripcion, " + idioma + ") nombre");
		sql2.FROM("SCS_PRETENSION pretension");
		sql2.WHERE("pretension.idinstitucion = '" + idInstitucion + "'");
		sql2.WHERE("pretension.fechabaja is null");

		sql = "SELECT * FROM (" + sql1.toString() + " UNION " + sql2.toString() + ") ORDER BY NOMBRE";

		return sql;
	}

//
//SELECT procedimiento.idprocedimiento, LISTAGG(prepro.idpretension, ',') WITHIN GROUP (ORDER BY prepro.idprocedimiento) AS procedimientos
//, procedimiento.nombre, procedimiento.codigo, procedimiento.precio as importe, procedimiento.complemento, procedimiento.vigente, procedimiento.idjurisdiccion, procedimiento.orden, procedimiento.codigoext, procedimiento.permitiraniadirletrado, procedimiento.fechadesdevigor, procedimiento.fechahastavigor, procedimiento.fechabaja, procedimiento.observaciones
//FROM SCS_PROCEDIMIENTOS procedimiento
//Left JOIN SCS_PRETENSIONESPROCED prepro on (prepro.idprocedimiento = procedimiento.idprocedimiento AND PREPRO.IDINSTITUCION = PROCEDIMIENTO.IDINSTITUCION)
//
//WHERE (procedimiento.idinstitucion = '2003'  AND UPPER(procedimiento.nombre) like UPPER('%%') AND procedimiento.fechabaja is null)
//GROUP BY procedimiento.idprocedimiento,  procedimiento.nombre, procedimiento.codigo, procedimiento.precio , procedimiento.complemento, procedimiento.vigente, procedimiento.idjurisdiccion, procedimiento.orden, procedimiento.codigoext, procedimiento.permitiraniadirletrado, procedimiento.fechadesdevigor, procedimiento.fechahastavigor, procedimiento.fechabaja, procedimiento.observaciones
//ORDER BY nombre;

	public String searchModulo(ModulosItem moduloItem, String idioma, String idJuzgado) {
	    SQL sql = new SQL();

	    sql.SELECT("DISTINCT procedimiento.idprocedimiento");
	    sql.SELECT("procedimiento.nombre");
	    sql.SELECT("procedimiento.codigo");
	    sql.SELECT("procedimiento.precio as importe");
	    sql.SELECT("procedimiento.complemento");
	    sql.SELECT("procedimiento.vigente");
	    sql.SELECT("procedimiento.idjurisdiccion");
	    sql.SELECT("procedimiento.orden");
	    sql.SELECT("procedimiento.codigoext");
	    sql.SELECT("procedimiento.permitiraniadirletrado");
	    sql.SELECT("procedimiento.fechadesdevigor");
	    sql.SELECT("procedimiento.fechahastavigor");
	    sql.SELECT("procedimiento.fechabaja");
	    sql.SELECT("procedimiento.observaciones");
	    sql.SELECT("juris.descripcion AS jurisdicciondes");
	    sql.SELECT("(SELECT LISTAGG(pretension.idpretension, ',') WITHIN GROUP (ORDER BY pretension.idpretension) " +
	                "FROM SCS_PRETENSION pretension " +
	                "INNER JOIN SCS_PRETENSIONESPROCED prepro ON prepro.idprocedimiento = procedimiento.idprocedimiento AND prepro.IDINSTITUCION = procedimiento.IDINSTITUCION " +
	                "WHERE prepro.idpretension = pretension.idpretension AND pretension.IDINSTITUCION = procedimiento.IDINSTITUCION AND pretension.FECHABAJA IS NULL) AS PRETENSIONES");
	    sql.SELECT("(SELECT LISTAGG(juzgado.nombre, ', ') WITHIN GROUP (ORDER BY juzgado.nombre) " +
	                "FROM SCS_JUZGADO juzgado " +
	                "INNER JOIN SCS_JUZGADOPROCEDIMIENTO juzgado_procedimiento ON juzgado.idjuzgado = juzgado_procedimiento.idjuzgado AND juzgado_procedimiento.IDINSTITUCION_JUZG = juzgado.IDINSTITUCION " +
	                "WHERE juzgado_procedimiento.idprocedimiento = procedimiento.idprocedimiento AND juzgado_procedimiento.IDINSTITUCION = procedimiento.IDINSTITUCION AND rownum <= 50) AS juzgados");
	    sql.SELECT("(SELECT LISTAGG(acreditacion.descripcion, ', ') WITHIN GROUP (ORDER BY acreditacion.descripcion) " +
	                "FROM SCS_ACREDITACION acreditacion " +
	                "INNER JOIN SCS_ACREDITACIONPROCEDIMIENTO acre ON acre.idacreditacion = acreditacion.idacreditacion " +
	                "WHERE acre.idprocedimiento = procedimiento.idprocedimiento AND acre.IDINSTITUCION = procedimiento.IDINSTITUCION AND rownum <= 50) AS acreditaciones");
	    sql.SELECT("(SELECT count(*) FROM SCS_JUZGADO juzgado INNER JOIN SCS_JUZGADOPROCEDIMIENTO juzgado_procedimiento ON"
	    		+ " juzgado.idjuzgado = juzgado_procedimiento.idjuzgado AND juzgado_procedimiento.IDINSTITUCION_JUZG = juzgado.IDINSTITUCION"
	    		+ "	WHERE juzgado_procedimiento.idprocedimiento = procedimiento.idprocedimiento AND juzgado_procedimiento.IDINSTITUCION = procedimiento.IDINSTITUCION) AS NUMJUZGADOS");
	    sql.SELECT("(SELECT COUNT(*) FROM SCS_ACREDITACION acreditacion INNER JOIN SCS_ACREDITACIONPROCEDIMIENTO acre ON acre.idacreditacion = acreditacion.idacreditacion"
	    		+ "	WHERE acre.idprocedimiento = procedimiento.idprocedimiento AND acre.IDINSTITUCION = procedimiento.IDINSTITUCION) AS NUMACREDITACIONES");
	    sql.FROM("SCS_PROCEDIMIENTOS procedimiento");
	    sql.INNER_JOIN("scs_jurisdiccion jurisdiccion ON jurisdiccion.idjurisdiccion = procedimiento.idjurisdiccion");
	    sql.LEFT_OUTER_JOIN("gen_recursos_catalogos juris ON (juris.idrecurso = jurisdiccion.descripcion AND idlenguaje = '" + idioma + "')");
	    sql.LEFT_OUTER_JOIN("SCS_PRETENSIONESPROCED sp ON sp.IDPROCEDIMIENTO = procedimiento.IDPROCEDIMIENTO AND sp.IDINSTITUCION = procedimiento.IDINSTITUCION");
	    sql.LEFT_OUTER_JOIN("SCS_JUZGADOPROCEDIMIENTO sj ON sj.IDPROCEDIMIENTO = procedimiento.IDPROCEDIMIENTO AND sj.IDINSTITUCION = procedimiento.IDINSTITUCION");
	    
	    sql.WHERE("procedimiento.idinstitucion = '" + moduloItem.getIdInstitucion() + "'");

	    if (!moduloItem.isHistorico()) {
	        sql.WHERE("procedimiento.fechabaja IS NULL");
	        
	        if (moduloItem.getFechadesdevigor() != null && moduloItem.getFechahastavigor() != null) {
	        	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	        	String fechaDesdeFormateada = formato.format(moduloItem.getFechadesdevigor());
	        	String fechaHastaFormateada = formato.format(moduloItem.getFechahastavigor());
	        	
	        	sql.WHERE("procedimiento.fechadesdevigor >= TO_DATE('" + fechaDesdeFormateada + "', 'dd/MM/yyyy')"
	        			+ " AND procedimiento.fechadesdevigor <= TO_DATE('" + fechaHastaFormateada + "', 'dd/MM/yyyy')"
	        			+ " AND ((procedimiento.fechahastavigor >= TO_DATE('" + fechaDesdeFormateada + "', 'dd/MM/yyyy')"
	        			+ " AND procedimiento.fechahastavigor <= TO_DATE('" + fechaHastaFormateada + "', 'dd/MM/yyyy')) OR procedimiento.fechahastavigor IS NULL)");
	        } else {
	        	if (moduloItem.getFechadesdevigor() != null) {
					SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
					String fechaFormateada = formato.format(moduloItem.getFechadesdevigor());
					
					sql.WHERE("(procedimiento.fechahastavigor >= TO_DATE('" + fechaFormateada + "', 'dd/MM/yyyy') OR procedimiento.fechahastavigor IS NULL)");
				}
		        
		        if (moduloItem.getFechahastavigor() != null) {
		        	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
					String fechaFormateada = formato.format(moduloItem.getFechahastavigor());
					
					sql.WHERE("procedimiento.fechadesdevigor <= TO_DATE('" + fechaFormateada + "', 'dd/MM/yyyy')");
		        }
	        }
	    }
	    
		if (moduloItem.getIdjurisdiccion() != null && !moduloItem.getIdjurisdiccion().equals("")) {
			String[] listaId = moduloItem.getIdjurisdiccion().split(",");

			String listaFormateada = "'" + listaId[0] + "'";

			if (listaId.length > 1) {
				for (int i = 1; i < listaId.length; i++) {
					listaFormateada += ",'" + listaId[i] + "'";
				}
			}

			sql.WHERE("procedimiento.IDJURISDICCION in (" + listaFormateada + ")");
		}
		
		if (moduloItem.getIdProcedimiento() != null && !moduloItem.getIdProcedimiento().isEmpty()) {
			sql.WHERE("sp.IDPRETENSION IN (" + moduloItem.getIdProcedimiento() + ")");
		}
		
		if (moduloItem.getJuzgados() != null && !moduloItem.getJuzgados().isEmpty()) {
			sql.WHERE("sj.IDJUZGADO IN (" + moduloItem.getJuzgados() + ")");
		}
		
		if(moduloItem.getComplemento() != null && !moduloItem.getComplemento().isEmpty()) {
			sql.WHERE("procedimiento.complemento = " + moduloItem.getComplemento());
		}

	    if (moduloItem.getNombre() != null && !moduloItem.getNombre().isEmpty()) {
	        sql.WHERE("UPPER(procedimiento.nombre) LIKE UPPER('%" + moduloItem.getNombre() + "%')");
	    }
	    if (moduloItem.getCodigo() != null && !moduloItem.getCodigo().isEmpty()) {
	        sql.WHERE("UPPER(procedimiento.codigo) LIKE UPPER('%" + moduloItem.getCodigo() + "%')");
	    }
	    if (moduloItem.getPrecio() != null) {
	        if (moduloItem.getNombre() != null && !moduloItem.getNombre().isEmpty()) {
	            sql.WHERE("UPPER(procedimiento.nombre) = UPPER('" + moduloItem.getNombre() + "')");
	        }
	        if (moduloItem.getIdProcedimiento() != null && !moduloItem.getIdProcedimiento().isEmpty()) {
	            sql.WHERE("procedimiento.idprocedimiento = '" + moduloItem.getIdProcedimiento() + "'");
	        }
	        if (moduloItem.getCodigo() != null && !moduloItem.getCodigo().isEmpty()) {
	            sql.WHERE("UPPER(procedimiento.codigo) = UPPER('" + moduloItem.getCodigo() + "')");
	        }
	    }

	    sql.ORDER_BY("procedimiento.nombre, procedimiento.codigo");

	    return sql.toString();
	}

	/*
	 * public String searchModulo(ModulosItem moduloItem, String idioma) {
	 * 
	 * SQL sql = new SQL();
	 * 
	 * sql.SELECT("procedimiento.idprocedimiento");
	 * sql.SELECT("procedimiento.nombre"); sql.SELECT("procedimiento.codigo"); sql.
	 * SELECT("LISTAGG(pretension.idpretension, ',') WITHIN GROUP (ORDER BY procedimiento.idprocedimiento) AS procedimientos"
	 * ); sql.SELECT("procedimiento.precio as importe");
	 * sql.SELECT("procedimiento.complemento"); sql.SELECT("procedimiento.vigente");
	 * sql.SELECT("procedimiento.idjurisdiccion");
	 * sql.SELECT("procedimiento.orden"); sql.SELECT("procedimiento.codigoext");
	 * sql.SELECT("procedimiento.permitiraniadirletrado");
	 * sql.SELECT("procedimiento.fechadesdevigor");
	 * sql.SELECT("procedimiento.fechahastavigor");
	 * sql.SELECT("procedimiento.fechabaja");
	 * sql.SELECT("procedimiento.observaciones");
	 * sql.SELECT("juris.descripcion AS jurisdicciondes");
	 * 
	 * sql.FROM("SCS_PROCEDIMIENTOS PROCEDIMIENTO");
	 * 
	 * sql.WHERE("procedimiento.idinstitucion = '" + moduloItem.getidInstitucion() +
	 * "'");
	 * 
	 * sql.
	 * LEFT_OUTER_JOIN("SCS_PRETENSIONESPROCED prepro on (prepro.idprocedimiento = procedimiento.idprocedimiento AND PREPRO.IDINSTITUCION = PROCEDIMIENTO.IDINSTITUCION)"
	 * ); sql.
	 * LEFT_OUTER_JOIN("SCS_PRETENSION pretension on (prepro.idpretension = pretension.idpretension AND pretension.IDINSTITUCION = PROCEDIMIENTO.IDINSTITUCION AND PRETENSION.FECHABAJA IS NULL)"
	 * ); sql.
	 * INNER_JOIN("scs_jurisdiccion jurisdiccion ON jurisdiccion.idjurisdiccion = procedimiento.idjurisdiccion"
	 * ); sql.LEFT_OUTER_JOIN("gen_recursos_catalogos juris ON (" +
	 * "            juris.idrecurso = jurisdiccion.descripcion" +
	 * "        AND" + "            idlenguaje = '"+ idioma +"' " +
	 * "    )");
	 * 
	 * if(moduloItem.getPrecio() == null) { if(moduloItem.getNombre() != null &&
	 * moduloItem.getNombre() != "") {
	 * sql.WHERE("UPPER(procedimiento.nombre) like UPPER('%" +
	 * moduloItem.getNombre() + "%')"); } if(moduloItem.getIdProcedimiento() != null
	 * && moduloItem.getIdProcedimiento() != "") {
	 * sql.WHERE("procedimiento.idprocedimiento = '" +
	 * moduloItem.getIdProcedimiento() + "'"); } if(moduloItem.getCodigo() != null
	 * && moduloItem.getCodigo() != "") {
	 * sql.WHERE("UPPER(procedimiento.codigo) like UPPER('%" +
	 * moduloItem.getCodigo() + "%')"); } }else { if(moduloItem.getNombre() != null
	 * && moduloItem.getNombre() != "") {
	 * sql.WHERE("UPPER(procedimiento.nombre) = UPPER('" + moduloItem.getNombre() +
	 * "')"); } if(moduloItem.getIdProcedimiento() != null &&
	 * moduloItem.getIdProcedimiento() != "") {
	 * sql.WHERE("procedimiento.idprocedimiento = '" +
	 * moduloItem.getIdProcedimiento() + "'"); } if(moduloItem.getCodigo() != null
	 * && moduloItem.getCodigo() != "") {
	 * sql.WHERE("UPPER(procedimiento.codigo) = UPPER('" + moduloItem.getCodigo() +
	 * "')"); } }
	 * 
	 * if(!moduloItem.isHistorico()) { if(moduloItem.isVerSoloAlta()) { sql.
	 * WHERE("(procedimiento.fechadesdevigor <= sysdate AND procedimiento.fechahastavigor is null)"
	 * ); } else { sql.
	 * WHERE("(procedimiento.fechadesdevigor <= sysdate AND (procedimiento.FECHAHASTAVIGOR > sysdate OR procedimiento.fechahastavigor is null))"
	 * ); }
	 * 
	 * sql.WHERE("(procedimiento.fechabaja is null)"); }
	 * 
	 * sql.
	 * GROUP_BY("procedimiento.idprocedimiento,  procedimiento.nombre, procedimiento.codigo, procedimiento.precio , procedimiento.complemento, procedimiento.vigente, procedimiento.idjurisdiccion, procedimiento.orden, procedimiento.codigoext, procedimiento.permitiraniadirletrado, procedimiento.fechadesdevigor, procedimiento.fechahastavigor, procedimiento.fechabaja, procedimiento.observaciones, juris.descripcion"
	 * ); sql.ORDER_BY("nombre");
	 * 
	 * return sql.toString(); }
	 */

	public String getIdProcedimiento(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(to_number(IDPROCEDIMIENTO)) AS IDPROCEDIMIENTO");
		sql.FROM("SCS_PROCEDIMIENTOS");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}

	public String getComplementoProcedimiento(String idInstitucion, String idProcedimiento) {
		SQL sql = new SQL();

		sql.SELECT("COMPLEMENTO");
		sql.FROM("SCS_PROCEDIMIENTOS");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("IDPROCEDIMIENTO = '" + idProcedimiento + "'");

		return sql.toString();
	}

}
