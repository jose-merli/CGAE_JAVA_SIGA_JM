package org.itcgae.siga.db.services.scs.providers;

import java.util.Date;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.scs.BajasTemporalesItem;
import org.itcgae.siga.DTOs.scs.InscripcionesItem;
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
				"    trunc(bt.fechaestado),\r\n" + 
				"    DECODE(col.comunitario,'1',col.ncomunitario,col.ncolegiado) ncolegiado,\r\n" + 
				"    per.nombre,\r\n" + 
				"    per.apellidos1,\r\n" + 
				"    per.apellidos2");
		sql.FROM("cen_bajastemporales bt");
		sql.INNER_JOIN("cen_colegiado col ON col.idpersona = bt.idpersona and col.idinstitucion = bt.idinstitucion\r\n" + 
				"    INNER JOIN cen_persona per ON per.idpersona = col.idpersona");
		sql.WHERE("TO_CHAR(bt.fechabt,'YYYY') >= TO_CHAR(bt.fechabt,'YYYY') - 2");
		sql.WHERE("bt.idinstitucion = '"+idInstitucion+"'");

		if(bajasTemporalesItem.getValidado() != null) {
			sql.WHERE("bt.validado = '"+bajasTemporalesItem.getValidado()+"'");
		}
		if(bajasTemporalesItem.getFechadesde() != null) {
			sql.WHERE("trunc(bt.fechabt) >='"+fechadesde+"'");
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
			
		return sql.toString();
	}
	
	public String nuevaBajaTemporal(ColegiadoItem colegiadoItem) {
		SQL sql = new SQL();
		sql.INSERT_INTO("cen_bajastemporales");
		sql.VALUES("idinstitucion", colegiadoItem.getInstitucion());
		sql.VALUES("idpersona", colegiadoItem.getIdPersona());
		sql.VALUES("valido", null);
		sql.VALUES("fechaestado", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		sql.VALUES("fechabt", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		
		return sql.toString();
	}
}