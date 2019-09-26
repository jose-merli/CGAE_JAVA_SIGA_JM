package org.itcgae.siga.db.services.scs.providers;

import java.util.Date;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.scs.ModulosItem;
import org.itcgae.siga.db.mappers.ScsProcedimientosSqlProvider;

public class ScsProcedimientosSqlExtendsProvider extends ScsProcedimientosSqlProvider{

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
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS juris on (juris.idrecurso = jurisdiccion.DESCRIPCION and idlenguaje = '" + idLenguaje + "')");;
		
		sql.WHERE("proc.idinstitucion = '" + idInstitucion + "'");
		
		sql.ORDER_BY("proc.nombre");
	
		return sql.toString();
	}

	public String searchModulo(ModulosItem moduloItem) {
		
		SQL sql = new SQL();
		
		sql.SELECT("idprocedimiento");
		sql.SELECT("nombre");
		sql.SELECT("codigo");
		sql.SELECT("rpad(precio,length(precio)+1,'€') precio");
		sql.SELECT("precio as importe");
		sql.SELECT("complemento");
		sql.SELECT("vigente");
		sql.SELECT("idjurisdiccion");
		sql.SELECT("orden");
		sql.SELECT("codigoext");
		sql.SELECT("permitiraniadirletrado");
		sql.SELECT("fechadesdevigor");
		sql.SELECT("fechahastavigor");
		sql.SELECT("fechabaja");
		sql.SELECT("observaciones");

		sql.FROM("SCS_PROCEDIMIENTOS");
		
		sql.WHERE("idinstitucion = '" + moduloItem.getidInstitucion() + "'");
		
		if(moduloItem.getPrecio() == null) {
			if(moduloItem.getNombre() != null && moduloItem.getNombre() != "") {
				sql.WHERE("UPPER(nombre) like UPPER('%" + moduloItem.getNombre() + "%')");
			}
			if(moduloItem.getIdProcedimiento() != null && moduloItem.getIdProcedimiento() != "") {
				sql.WHERE("idprocedimiento = '" + moduloItem.getIdProcedimiento() + "'");
			}
			if(moduloItem.getCodigo() != null && moduloItem.getCodigo() != "") {
				sql.WHERE("UPPER(codigo) like UPPER('%" + moduloItem.getCodigo() + "%')");
			}
		}else {
			if(moduloItem.getNombre() != null && moduloItem.getNombre() != "") {
				sql.WHERE("UPPER(nombre) = UPPER('" + moduloItem.getNombre() + "')");
			}
			if(moduloItem.getIdProcedimiento() != null && moduloItem.getIdProcedimiento() != "") {
				sql.WHERE("idprocedimiento = '" + moduloItem.getIdProcedimiento() + "'");
			}
			if(moduloItem.getCodigo() != null && moduloItem.getCodigo() != "") {
				sql.WHERE("UPPER(codigo) = UPPER('" + moduloItem.getCodigo() + "')");
			}
		}
		
		if(!moduloItem.isHistorico()) {
			sql.WHERE("fechabaja is null");
		}
		sql.ORDER_BY("nombre"); 
	
		return sql.toString();
	}
	
	public String getIdProcedimiento(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDPROCEDIMIENTO) AS IDPROCEDIMIENTO");
		sql.FROM("SCS_PROCEDIMIENTOS");
		sql.WHERE("IDINSTITUCION = '"+ idInstitucion +"'");

		return sql.toString();
	}
	
	
}
