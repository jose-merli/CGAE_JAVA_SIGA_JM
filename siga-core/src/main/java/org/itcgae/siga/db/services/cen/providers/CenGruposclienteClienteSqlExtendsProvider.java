package org.itcgae.siga.db.services.cen.providers;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.ComboEtiquetasItem;
import org.itcgae.siga.DTOs.cen.EtiquetaUpdateDTO;
import org.itcgae.siga.db.entities.CenGruposclienteCliente;
import org.itcgae.siga.db.entities.CenGruposclienteClienteExample;
import org.itcgae.siga.db.mappers.CenGruposclienteClienteSqlProvider;

public class CenGruposclienteClienteSqlExtendsProvider extends CenGruposclienteClienteSqlProvider{

		
	public String insertSelectiveForCreateLegalPerson(ComboEtiquetasItem etiqueta, String idInstitucion, String grupo, String idUsuario) {
		
		SQL sql = new SQL();
		
		sql.INSERT_INTO("CEN_GRUPOSCLIENTE_CLIENTE");
		sql.VALUES("IDPERSONA", "(Select max(idpersona)  from cen_persona)");
		sql.VALUES("IDINSTITUCION", idInstitucion);
		if(!grupo.equals("")) {
			sql.VALUES("IDGRUPO", "'" +grupo + "'");
		}
		else {
			sql.VALUES("IDGRUPO", "(SELECT MAX(IDGRUPO) FROM CEN_GRUPOSCLIENTE)");
		}
		
		sql.VALUES("FECHAMODIFICACION", "SYSDATE");
		sql.VALUES("USUMODIFICACION", "'" +idUsuario + "'");
		sql.VALUES("IDINSTITUCION_GRUPO", "'" +idInstitucion+ "'");
		sql.VALUES("FECHA_BAJA", "'" + etiqueta.getFechaBaja() + "'");
		sql.VALUES("FECHA_INICIO", "'" + etiqueta.getFechaInicio() + "'");
		return sql.toString();
	}
	
	public String selectGruposPersonaJuridica(String idPersona, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("cli.idgrupo");
		sql.SELECT("GENR.descripcion");
		sql.SELECT("TO_CHAR(cli.fecha_inicio, 'dd/MM/yyyy') as FECHA_INICIO");
		sql.SELECT("TO_CHAR(cli.fecha_baja, 'dd/MM/yyyy') as FECHA_BAJA");
		sql.FROM("CEN_GRUPOSCLIENTE_CLIENTE cli");
		sql.INNER_JOIN("cen_persona per on cli.idpersona = per.idpersona");
		sql.INNER_JOIN("CEN_GRUPOSCLIENTE GRUCLI on cli.idGrupo = GRUCLI.idGrupo");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS GENR on GRUCLI.NOMBRE = GENR.IDRECURSO AND GENR.idLenguaje = '1'");
		sql.WHERE("per.idpersona = '"+idPersona+"'");
		sql.WHERE("cli.FECHA_BAJA is NOT null");
		sql.WHERE("GRUCLI.idinstitucion = '"+idInstitucion+"'");
		
		return sql.toString();
	}
	
	public String insertSelectiveForUpdateLegalPerson(ComboEtiquetasItem etiqueta, String idPersona, String idInstitucion, String idUsuario) {
		SQL sql = new SQL();
		
		sql.INSERT_INTO("CEN_GRUPOSCLIENTE_CLIENTE");
		sql.VALUES("IDPERSONA", "'" + idPersona + "'");
		sql.VALUES("IDINSTITUCION", "'" + idInstitucion + "'");
		sql.VALUES("IDGRUPO", "'" + etiqueta.getIdGrupo() + "'");
		sql.VALUES("FECHAMODIFICACION", "SYSDATE");
		sql.VALUES("USUMODIFICACION", "'" +idUsuario + "'");
		sql.VALUES("IDINSTITUCION_GRUPO", "'" +idInstitucion+ "'");
		sql.VALUES("FECHA_BAJA", "'" + etiqueta.getFechaBaja() + "'");
		sql.VALUES("FECHA_INICIO", "'" + etiqueta.getFechaInicio() + "'");
		return sql.toString();
	}
	
	public String updateByExample(Map<String, Object> parameter) {
		CenGruposclienteCliente record = (CenGruposclienteCliente) parameter.get("record");
		CenGruposclienteClienteExample example = (CenGruposclienteClienteExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("CEN_GRUPOSCLIENTE_CLIENTE");
		if (record.getIdpersona() != null) {
			sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
		}
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdgrupo() != null) {
			sql.SET("IDGRUPO = #{record.idgrupo,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdinstitucionGrupo() != null) {
			sql.SET("IDINSTITUCION_GRUPO = #{record.idinstitucionGrupo,jdbcType=DECIMAL}");
		}
		sql.SET("FECHA_BAJA = #{record.fechaBaja,jdbcType=TIMESTAMP}");
		
		if(record.getFechaInicio() != null) {
			sql.SET("FECHA_INICIO = #{record.fechaInicio,jdbcType=TIMESTAMP}");
		}
		
		applyWhere(sql, example, true);
		return sql.toString();
	}
}