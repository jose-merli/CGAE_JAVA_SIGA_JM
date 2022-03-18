package org.itcgae.siga.db.services.cen.providers;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.ComboEtiquetasItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.CenGruposclienteCliente;
import org.itcgae.siga.db.entities.CenGruposclienteClienteExample;
import org.itcgae.siga.db.mappers.CenGruposclienteClienteSqlProvider;

public class CenGruposclienteClienteSqlExtendsProvider extends CenGruposclienteClienteSqlProvider{

		
	public String insertSelectiveForCreateLegalPerson(ComboEtiquetasItem etiqueta, String idInstitucion, String grupo, String idUsuario) {
		
		SQL sql = new SQL();
		
		sql.INSERT_INTO("CEN_GRUPOSCLIENTE_CLIENTE");
		sql.VALUES("IDPERSONA", "(Select max(idpersona)  from cen_persona  where idpersona like '" + idInstitucion + "' || '%' )");
		sql.VALUES("IDINSTITUCION", idInstitucion);
		if(!grupo.equals("")) {
			sql.VALUES("IDGRUPO", "'" +grupo + "'");
		}
		else {
			sql.VALUES("IDGRUPO", "(SELECT MAX(IDGRUPO) FROM CEN_GRUPOSCLIENTE)");
		}
		
		sql.VALUES("FECHAMODIFICACION", "SYSDATE");
		sql.VALUES("USUMODIFICACION", "'" +idUsuario + "'");
		sql.VALUES("IDINSTITUCION_GRUPO", "'" + etiqueta.getIdInstitucion() + "'");
		if(etiqueta.getFechaBaja() != null) {
			sql.VALUES("FECHA_BAJA", "TO_DATE('" + etiqueta.getFechaBaja() + "','DD/MM/YYYY')");
		}else {
			sql.VALUES("FECHA_BAJA", "null");
		} 		sql.VALUES("FECHA_INICIO", "TO_DATE('" + etiqueta.getFechaInicio() + "','DD/MM/YYYY')");
		
		return sql.toString();
	}
	
	public String selectGruposPersonaJuridica(String idPersona, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("cli.idgrupo");
		sql.SELECT("INITCAP(GENR.DESCRIPCION) as DESCRIPCION");
		sql.SELECT("NVL(TO_CHAR(cli.fecha_inicio, 'dd/MM/yyyy'),TO_CHAR(TO_DATE('01/01/1980','DD/MM/YYYY'),'DD/MM/YYYY')) as FECHA_INICIO");
		sql.SELECT("TO_CHAR(cli.fecha_baja, 'dd/MM/yyyy') as FECHA_BAJA");
		sql.SELECT("cli.IDINSTITUCION_GRUPO as IDINSTITUCION");
		sql.FROM("CEN_GRUPOSCLIENTE_CLIENTE cli");
		sql.INNER_JOIN("cen_persona per on cli.idpersona = per.idpersona ");
		sql.INNER_JOIN("CEN_GRUPOSCLIENTE GRUCLI on cli.idGrupo = GRUCLI.idGrupo and cli.idinstitucion_grupo = grucli.idinstitucion");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS GENR on GRUCLI.NOMBRE = GENR.IDRECURSO AND GENR.idLenguaje = '1'");
		sql.WHERE("per.idpersona = '"+idPersona+"'");
//		sql.WHERE("cli.FECHA_BAJA > sysdate");
		sql.WHERE(" cli.idinstitucion in ('2000', '"+ idInstitucion  + "')");
		//sql.WHERE("cli.idinstitucion = '"+idInstitucion+"'");
		sql.ORDER_BY("GENR.descripcion");
		return sql.toString();
	}
	
	public String selectGruposPersonaJuridicaLenguaje(String idPersona, String idInstitucion, String idLenguaje) {
		SQL sql = new SQL();
		
		sql.SELECT("cli.idgrupo");
		sql.SELECT("INITCAP(GENR.DESCRIPCION) as DESCRIPCION");
		sql.SELECT("NVL(TO_CHAR(cli.fecha_inicio, 'dd/MM/yyyy'),TO_CHAR(TO_DATE('01/01/1980','DD/MM/YYYY'),'DD/MM/YYYY')) as FECHA_INICIO");
		sql.SELECT("TO_CHAR(cli.fecha_baja, 'dd/MM/yyyy') as FECHA_BAJA");
		sql.SELECT("cli.IDINSTITUCION_GRUPO as IDINSTITUCION");
		sql.FROM("CEN_GRUPOSCLIENTE_CLIENTE cli");
		sql.INNER_JOIN("cen_persona per on cli.idpersona = per.idpersona ");
		sql.INNER_JOIN("CEN_GRUPOSCLIENTE GRUCLI on cli.idGrupo = GRUCLI.idGrupo and cli.idinstitucion_grupo = grucli.idinstitucion");
		if(idLenguaje != null) {
			sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS GENR on GRUCLI.NOMBRE = GENR.IDRECURSO AND GENR.idLenguaje = '"+ idLenguaje + "'");
		} else {
			sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS GENR on GRUCLI.NOMBRE = GENR.IDRECURSO AND GENR.idLenguaje = '1'");
		}
		sql.WHERE("per.idpersona = '"+idPersona+"'");
		sql.WHERE(" cli.idinstitucion = '"+ idInstitucion  + "'");
		sql.WHERE(" cli.idinstitucion_grupo in ('2000', '"+ idInstitucion  + "')");
		sql.ORDER_BY("GENR.descripcion");
		return sql.toString();
	}
	

	public String selectGrupos(String idlenguaje, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("GRUCLI.idgrupo");
		sql.SELECT("GENR.descripcion");
		sql.FROM("CEN_GRUPOSCLIENTE GRUCLI");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS GENR on GRUCLI.NOMBRE = GENR.IDRECURSO AND GENR.idLenguaje = '"+idlenguaje+"'");
		
		if(idInstitucion == SigaConstants.IDINSTITUCION_2000.toString()) {
			sql.WHERE("GRUCLI.idinstitucion = '" + idInstitucion + "'");
		}else {
			sql.WHERE("GRUCLI.idinstitucion in ('"+idInstitucion+"','" + SigaConstants.IDINSTITUCION_2000 + "')");
		}
		
		sql.ORDER_BY("GENR.descripcion");
		
		return sql.toString();
	}
	
	//public String insertSelectiveForUpdateLegalPerson(EtiquetaUpdateDTO etiquetaUpdateDTO, String idInstitucion, String grupo, String idUsuario) {

	public String insertSelectiveForUpdateLegalPerson(ComboEtiquetasItem etiqueta, String idPersona, String idInstitucionEtiqueta, String idInstitucion, String idUsuario) {

		SQL sql = new SQL();

		sql.INSERT_INTO("CEN_GRUPOSCLIENTE_CLIENTE");
		sql.VALUES("IDPERSONA", "'" + idPersona + "'");
		sql.VALUES("IDINSTITUCION", "'" + idInstitucion + "'");
		sql.VALUES("IDGRUPO", "'" + etiqueta.getIdGrupo() + "'");
		sql.VALUES("FECHAMODIFICACION", "SYSDATE");
		sql.VALUES("USUMODIFICACION", "'" +idUsuario + "'");
		sql.VALUES("IDINSTITUCION_GRUPO", "'" +idInstitucionEtiqueta+ "'");
//		sql.VALUES("FECHA_BAJA", "'" + etiqueta.getFechaBaja() + "'");
//		sql.VALUES("FECHA_INICIO", "'" + etiqueta.getFechaInicio() + "'");
		if(etiqueta.getFechaBaja() != null) {
			sql.VALUES("FECHA_BAJA", "TO_DATE('" + etiqueta.getFechaBaja() + "','DD/MM/YYYY')");
		}else {
			sql.VALUES("FECHA_BAJA", "null");
		} 
		sql.VALUES("FECHA_INICIO", "TO_DATE('" + etiqueta.getFechaInicio() + "','DD/MM/YYYY')");
		
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