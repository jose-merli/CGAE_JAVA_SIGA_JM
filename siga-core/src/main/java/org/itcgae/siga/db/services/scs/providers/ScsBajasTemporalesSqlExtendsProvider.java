package org.itcgae.siga.db.services.scs.providers;

import java.util.Date;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.scs.BajasTemporalesItem;
import org.itcgae.siga.DTOs.scs.InscripcionesItem;
import org.itcgae.siga.db.entities.CenBajastemporales;
import org.itcgae.siga.db.mappers.CenBajastemporalesSqlProvider;
import org.itcgae.siga.db.mappers.ScsInscripcionturnoSqlProvider;
import java.text.SimpleDateFormat;

public class ScsBajasTemporalesSqlExtendsProvider extends CenBajastemporalesSqlProvider {


	public String busquedaBajasTemporales(BajasTemporalesItem bajasTemporalesItem, Short idInstitucion,String fechadesde,String fechahasta) {

		SQL sql = new SQL();
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
		sql.WHERE("TO_CHAR(bt.fechabt,'YYYY') >= TO_CHAR(bt.fechabt,'YYYY') - 2");
		sql.WHERE("bt.idinstitucion = '"+idInstitucion+"'");
		
		if(bajasTemporalesItem.getValidado() != null && !bajasTemporalesItem.getValidado().equals("2")) {
			sql.WHERE("bt.validado = '"+bajasTemporalesItem.getValidado()+"'");
		}
		if("2".equals(bajasTemporalesItem.getValidado())) {
			sql.WHERE("(bt.validado IS NULL OR bt.validado = 2)");
		}
		if(bajasTemporalesItem.getFechadesde() != null) {
			sql.WHERE("bt.fechadesde >='"+fechadesde+"'");
		}
		if(bajasTemporalesItem.getFechahasta() != null) {
			sql.WHERE("bt.fechahasta <='"+fechahasta+"'");
		}
		if(bajasTemporalesItem.getFechaalta() != null) {
			sql.WHERE("bt.fechaalta >='"+bajasTemporalesItem.getFechaalta()+"'");
		}
		if(bajasTemporalesItem.getFechabt() != null) {
			sql.WHERE("bt.fechabt <='"+bajasTemporalesItem.getFechabt()+"'");
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
}