package org.itcgae.siga.db.services.scs.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.BajasTemporalesItem;
import org.itcgae.siga.db.mappers.CenBajastemporalesSqlProvider;

public class ScsBajasTemporalesSqlExtendsProvider extends CenBajastemporalesSqlProvider {


	public String busquedaBajasTemporales(BajasTemporalesItem bajasTemporalesItem, Short idInstitucion) {
		SQL sql = new SQL();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		sql.SELECT("DISTINCT\r\n" + 
				"    bt.idinstitucion,\r\n" + 
				"    bt.idpersona,\r\n" + 
				"    bt.tipo,\r\n" + 
				"    bt.fechadesde,\r\n" + 
				"    bt.fechahasta,\r\n" + 
				"    bt.fechaalta,\r\n" + 
				"    bt.descripcion,\r\n" + 
				"    bt.validado,\r\n" +
				"    bt.fechabt,\r\n" + 
				"    bt.fechaestado,\r\n" + 
				"    trunc(bt.fechaestado),\r\n" + 
				"    DECODE(col.comunitario,'1',col.ncomunitario,col.ncolegiado) ncolegiado,\r\n" + 
				"    per.nombre,\r\n" + 
				"    per.apellidos1,\r\n" + 
				"    bt.eliminado,\r\n" + 
				"    per.apellidos2");
		sql.FROM("cen_bajastemporales bt");
		sql.INNER_JOIN("cen_colegiado col ON col.idpersona = bt.idpersona and col.idinstitucion = bt.idinstitucion\r\n" + 
				"    INNER JOIN cen_persona per ON per.idpersona = col.idpersona");
		sql.WHERE("bt.idinstitucion = '"+idInstitucion+"'");
		
		if(bajasTemporalesItem.getValidado() != null && !bajasTemporalesItem.getValidado().equals("2")) {
			sql.WHERE("bt.validado = '"+bajasTemporalesItem.getValidado()+"'");
		}
		if("2".equals(bajasTemporalesItem.getValidado())) {
			sql.WHERE("(bt.validado IS NULL OR bt.validado = 2)");
		}
		if(bajasTemporalesItem.getFechadesde() != null) {
			sql.WHERE("bt.fechadesde >=TO_DATE('"+dateFormat.format(bajasTemporalesItem.getFechadesde())+"','DD/MM/RRRR')");
		}
		if(bajasTemporalesItem.getFechahasta() != null) {
			sql.WHERE("bt.fechahasta <=TO_DATE('"+dateFormat.format(bajasTemporalesItem.getFechahasta())+"','DD/MM/RRRR')");
		}
		if(bajasTemporalesItem.getFechasolicituddesde() != null) {
			sql.WHERE("bt.fechaalta >=TO_DATE('"+dateFormat.format(bajasTemporalesItem.getFechasolicituddesde())+"','DD/MM/RRRR')");
		}
		if(bajasTemporalesItem.getFechasolicitudhasta() != null) {
			sql.WHERE("bt.fechaalta <=TO_DATE('"+dateFormat.format(bajasTemporalesItem.getFechasolicitudhasta())+"','DD/MM/RRRR')");
		}
		if(bajasTemporalesItem.getFechabt() != null) {
			sql.WHERE("bt.fechabt <=TO_DATE('"+dateFormat.format(bajasTemporalesItem.getFechabt())+"','DD/MM/RRRR')");
		}
		if(bajasTemporalesItem.getTipo() != null) {
			sql.WHERE("bt.tipo = '"+bajasTemporalesItem.getTipo()+"'");
		}
		if(bajasTemporalesItem.getIdpersona() != null) {
			sql.WHERE("bt.idpersona = '"+bajasTemporalesItem.getIdpersona()+"'");
		}
		if(bajasTemporalesItem.getNcolegiado() != null && !bajasTemporalesItem.getNcolegiado().equals("")) {
			sql.WHERE("(col.ncolegiado = '"+bajasTemporalesItem.getNcolegiado()+"' OR col.ncomunitario = '"+bajasTemporalesItem.getNcolegiado()+"')");
		}
		if(!bajasTemporalesItem.isHistorico()) {
			sql.WHERE("bt.eliminado = 0");
		}else {
			sql.WHERE("(bt.eliminado = 1 OR bt.eliminado = 0)");
		}
		sql.WHERE("ROWNUM <= 200");
		return sql.toString();
	}
	
	public String comboEstado() {

		SQL sql = new SQL();

		sql.SELECT("idestado, descripcion");
		
		sql.FROM("cen_estadosbajastemp");

		return sql.toString();
	}
	

	public String checkNifColegiado(String nif, Short idInstitucion ) {

		SQL sql = new SQL();

		sql.SELECT("cen_persona.idpersona");
		
		sql.FROM("cen_persona");
		sql.JOIN("cen_colegiado on cen.persona.idpersona=cen_colegiado.idpersona");
		
		sql.WHERE("cen_persona.nifcif= '"+nif+"'");
		sql.WHERE("cen_colegiado.idinstitucion= '"+idInstitucion+"'");

		return sql.toString();
	}
	
	public String deleteBajasTemporales(BajasTemporalesItem bajasTemporalesItem) {
		

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		SQL sql = new SQL();
		sql.UPDATE("cen_bajastemporales");
		sql.SET("ELIMINADO = 1");
		sql.WHERE("FECHABT = '"+ dateFormat.format(bajasTemporalesItem.getFechabt())+"'");
		sql.WHERE("IDPERSONA= "+ bajasTemporalesItem.getIdpersona());
		sql.WHERE("IDINSTITUCION="+bajasTemporalesItem.getIdinstitucion());
	
		return sql.toString();
	}
	
public String updateBajasTemporales(BajasTemporalesItem bajasTemporalesItem) {
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		SQL sql = new SQL();
		sql.UPDATE("cen_bajastemporales");
		sql.SET("VALIDADO = '"+bajasTemporalesItem.getValidado()+"'");
		sql.WHERE("FECHABT = '"+ dateFormat.format(bajasTemporalesItem.getFechabt())+"'");
		sql.WHERE("IDPERSONA= "+ bajasTemporalesItem.getIdpersona());
		sql.WHERE("IDINSTITUCION="+bajasTemporalesItem.getIdinstitucion());
	
		return sql.toString();
	
	}

public String saveBajaTemporal(BajasTemporalesItem bajasTemporalesItem) {
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	SQL sql = new SQL();
	sql.UPDATE("cen_bajastemporales");
	
	if(bajasTemporalesItem.getFechadesde() != null) {
		sql.SET("fechadesde = '"+dateFormat.format(bajasTemporalesItem.getFechadesde())+"'");
	}
	if(bajasTemporalesItem.getFechahasta() != null) {
		sql.SET("fechahasta = '"+dateFormat.format(bajasTemporalesItem.getFechahasta())+"'");
	}
	if(bajasTemporalesItem.getTipo() != null) {
		sql.SET("TIPO = '"+bajasTemporalesItem.getTipo()+"'");
	}
	if(bajasTemporalesItem.getDescripcion() != null) {
		sql.SET("DESCRIPCION = '"+bajasTemporalesItem.getDescripcion()+"'");
	}
	
	sql.WHERE("FECHABT = '"+ dateFormat.format(bajasTemporalesItem.getFechabt())+"'");
	sql.WHERE("IDPERSONA= "+ bajasTemporalesItem.getIdpersona());
	sql.WHERE("IDINSTITUCION="+bajasTemporalesItem.getIdinstitucion());

	return sql.toString();

}

public String nuevaBajaTemporal(BajasTemporalesItem bajasTemporalesItem, Integer usuario) {
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	SQL sql = new SQL();
	sql.INSERT_INTO("cen_bajastemporales");
	
	if(bajasTemporalesItem.getFechadesde() != null) {
		sql.VALUES("fechadesde","'"+dateFormat.format(bajasTemporalesItem.getFechadesde())+"'");
	}
	if(bajasTemporalesItem.getFechahasta() != null) {
		sql.VALUES("fechahasta","'"+dateFormat.format(bajasTemporalesItem.getFechahasta())+"'");
	}
	if(bajasTemporalesItem.getTipo() != null) {
		sql.VALUES("TIPO","'"+bajasTemporalesItem.getTipo()+"'");
	}
	if(bajasTemporalesItem.getDescripcion() != null) {
		sql.VALUES("DESCRIPCION","'"+bajasTemporalesItem.getDescripcion()+"'");
	}
	
	sql.VALUES("VALIDADO", "2");
	sql.VALUES("FECHAESTADO", "'"+ dateFormat.format(bajasTemporalesItem.getFechaalta())+"'");
	sql.VALUES("FECHAMODIFICACION", "'"+ dateFormat.format(bajasTemporalesItem.getFechaalta())+"'");
	sql.VALUES("FECHAALTA", "'"+ dateFormat.format(bajasTemporalesItem.getFechaalta())+"'");
	sql.VALUES("ELIMINADO", "0");
	sql.VALUES("FECHABT","'"+ dateFormat.format(bajasTemporalesItem.getFechabt())+"'");
	sql.VALUES("IDPERSONA", bajasTemporalesItem.getIdpersona());
	sql.VALUES("IDINSTITUCION",bajasTemporalesItem.getIdinstitucion());
	sql.VALUES("USUMODIFICACION","'"+usuario+"'");

	return sql.toString();

}


public String idPersona(BajasTemporalesItem bajasTemporalesItem) {
	SQL sql = new SQL();
	sql.SELECT("idpersona");
	
	sql.FROM("cen_colegiado");
	
	sql.WHERE("idinstitucion="+bajasTemporalesItem.getIdinstitucion());
	sql.WHERE("ncolegiado= "+bajasTemporalesItem.getNcolegiado());
	
	return sql.toString();
}




}