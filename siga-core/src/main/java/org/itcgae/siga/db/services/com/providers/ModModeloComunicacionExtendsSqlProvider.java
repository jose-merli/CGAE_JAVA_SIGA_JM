package org.itcgae.siga.db.services.com.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesSearch;

public class ModModeloComunicacionExtendsSqlProvider {
	
	public String selectModulosComunicacion(DatosModelosComunicacionesSearch filtros, boolean historico){
		
		SQL sql = new SQL();
		
		// Formateo de fecha para sentencia sql
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				
		sql.SELECT("modelo.IDMODELOCOMUNICACION");
		sql.SELECT("modelo.NOMBRE");
		sql.SELECT("modelo.VISIBLE");
		sql.SELECT("modelo.ORDEN");
		sql.SELECT("modelo.IDINSTITUCION");
		sql.SELECT("modelo.DESCRIPCION");
		sql.SELECT("modelo.PRESELECCIONAR");
		sql.SELECT("modelo.IDCLASECOMUNICACION");
		sql.SELECT("clase.NOMBRE AS NOMBRECLASE");
		sql.SELECT("inst.ABREVIATURA");
		
		sql.FROM("MOD_MODELOCOMUNICACION modelo");
		
		sql.INNER_JOIN("MOD_CLASECOMUNICACIONES clase ON modelo.IDCLASECOMUNICACION = clase.IDCLASECOMUNICACION");
		sql.INNER_JOIN("CEN_INSTITUCION inst ON inst.IDINSTITUCION = modelo.IDINSTITUCION");
		
		if(filtros.getIdInstitucion() != null && !filtros.getIdInstitucion().trim().equals("")){
			sql.WHERE("IDINSTITUCION = '"+filtros.getIdInstitucion()+"'");
		}
		if(filtros.getIdClaseComunicacion() != null && !filtros.getIdClaseComunicacion().trim().equals("")){
			sql.WHERE("IDCLASECOMUNICACION = '"+filtros.getIdClaseComunicacion()+"'");
		}
		
		if(filtros.getNombre() != null && !filtros.getNombre().trim().equals("")){
			sql.WHERE("NOMBRE = '"+filtros.getNombre()+"'");
		}
		if(filtros.getPreseleccionar() != null && !filtros.getPreseleccionar().trim().equals("")){
			sql.WHERE("PRESELECCIONAR = '"+filtros.getPreseleccionar()+"'");	
		}
		if(filtros.getVisible() != null && !filtros.getVisible().trim().equals("")){
			sql.WHERE("VISIBLE = '"+filtros.getIdInstitucion()+"'");	
		}
		
		if(historico){
			if(filtros.getFechaBaja() != null){
				String fechaBaja = dateFormat.format(filtros.getFechaBaja());
				String fechaBaja2 = dateFormat.format(filtros.getFechaBaja());
				fechaBaja += " 00:00:00";
				fechaBaja2 += " 23:59:59";
				sql.WHERE("(FECHABAJA >= TO_DATE('" +fechaBaja + "', 'DD/MM/YYYY HH24:MI:SS') AND FECHABAJA <= TO_DATE('" +fechaBaja2 + "', 'DD/MM/YYYY HH24:MI:SS'))");
			}
		}else{
			sql.WHERE("FECHABAJA is NULL");
		}
		
		
		return sql.toString();
	}

}
