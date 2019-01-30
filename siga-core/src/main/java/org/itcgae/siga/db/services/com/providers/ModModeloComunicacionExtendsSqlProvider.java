package org.itcgae.siga.db.services.com.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.com.DatosModelosComunicacionesSearch;

public class ModModeloComunicacionExtendsSqlProvider {
	
	public String selectModelosComunicacion(DatosModelosComunicacionesSearch filtros, boolean historico){
		
		SQL sql = new SQL();
		
		// Formateo de fecha para sentencia sql
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				
		sql.SELECT("modelo.IDMODELOCOMUNICACION");
		sql.SELECT("modelo.NOMBRE");
		sql.SELECT("modelo.VISIBLE");
		sql.SELECT("modelo.ORDEN");
		sql.SELECT("modelo.IDINSTITUCION");
		sql.SELECT("modelo.DESCRIPCION");
		sql.SELECT("modelo.FECHABAJA");
		sql.SELECT("modelo.PRESELECCIONAR");
		sql.SELECT("modelo.IDCLASECOMUNICACION");
		sql.SELECT("modelo.FECHABAJA");
		sql.SELECT("clase.NOMBRE AS NOMBRECLASE");
		sql.SELECT("inst.ABREVIATURA");
		
		sql.FROM("MOD_MODELOCOMUNICACION modelo");
		
		sql.INNER_JOIN("MOD_CLASECOMUNICACIONES clase ON modelo.IDCLASECOMUNICACION = clase.IDCLASECOMUNICACION");
		sql.INNER_JOIN("CEN_INSTITUCION inst ON inst.IDINSTITUCION = modelo.IDINSTITUCION");
		
		if(filtros.getIdInstitucion() != null && !filtros.getIdInstitucion().trim().equals("")){
			sql.WHERE("modelo.IDINSTITUCION = '"+filtros.getIdInstitucion()+"'");
		}
		if(filtros.getIdClaseComunicacion() != null && !filtros.getIdClaseComunicacion().trim().equals("")){
			sql.WHERE("modelo.IDCLASECOMUNICACION = '"+filtros.getIdClaseComunicacion()+"'");
		}
		
		if(filtros.getNombre() != null && !filtros.getNombre().trim().equals("")){
			sql.WHERE("modelo.NOMBRE LIKE '%"+filtros.getNombre()+"%'");
		}
		if(filtros.getPreseleccionar() != null && !filtros.getPreseleccionar().trim().equals("")){
			sql.WHERE("modelo.PRESELECCIONAR = '"+filtros.getPreseleccionar()+"'");	
		}
		if(filtros.getVisible() != null && !filtros.getVisible().trim().equals("")){
			sql.WHERE("modelo.VISIBLE = '"+filtros.getIdInstitucion()+"'");	
		}
		
		if(historico){
			if(filtros.getFechaBaja() != null){
				String fechaBaja = dateFormat.format(filtros.getFechaBaja());
				String fechaBaja2 = dateFormat.format(filtros.getFechaBaja());
				fechaBaja += " 00:00:00";
				fechaBaja2 += " 23:59:59";
				sql.WHERE("(modelo.FECHABAJA >= TO_DATE('" +fechaBaja + "', 'DD/MM/YYYY HH24:MI:SS') AND modelo.FECHABAJA <= TO_DATE('" +fechaBaja2 + "', 'DD/MM/YYYY HH24:MI:SS'))");
			}
		}else{
			sql.WHERE("modelo.FECHABAJA is NULL");
		}
		
		sql.ORDER_BY("IDMODELOCOMUNICACION ASC");
		
		return sql.toString();
	}
	
		
	public String selectModelosComunicacionDialg(String idClaseComunicacion){
	   
	   SQL sql = new SQL();
	   
	   sql.SELECT("MODELO.IDCLASECOMUNICACION, MODELO.IDMODELOCOMUNICACION, MODELO.NOMBRE");
	   sql.FROM("MOD_MODELOCOMUNICACION MODELO");
	   sql.WHERE("MODELO.IDCLASECOMUNICACION IN ("+idClaseComunicacion.toString()+")");
	   
	   return sql.toString();
	}
	
	public String selectPlantillaModelo(String idModeloComunicacion){
		
		SQL sql = new SQL();
		sql.SELECT("MPLANTILLA.IDPLANTILLAENVIOS AS VALUE, PLANTILLA.NOMBRE AS LABEL");
		sql.FROM("MOD_MODELO_PLANTILLAENVIO MPLANTILLA");
		sql.JOIN("ENV_PLANTILLASENVIOS PLANTILLA ON PLANTILLA.IDPLANTILLAENVIOS = MPLANTILLA.IDPLANTILLAENVIOS AND MPLANTILLA.IDINSTITUCION = PLANTILLA.IDINSTITUCION");
		sql.WHERE("MPLANTILLA.IDMODELOCOMUNICACION = '"+ idModeloComunicacion +"'");
		   
		return sql.toString();
	}
	
	
	public String selectTipoEnvioPlantilla(String idLenguaje, String idPlantilla){
		
		SQL sql = new SQL();
		sql.SELECT("TIPO.IDTIPOENVIOS AS VALUE, CAT.DESCRIPCION");
		sql.FROM("ENV_TIPOENVIOS TIPO");
		sql.JOIN("GEN_RECURSOS_CATALOGOS CAT ON CAT.IDRECURSO = TIPO.NOMBRE AND CAT.IDLENGUAJE = '" + idLenguaje + "'");
		sql.WHERE("TIPO.IDTIPOENVIOS = '" + idPlantilla + "'");
		   
		return sql.toString();
	}

}
