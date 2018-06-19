package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.CenGruposclienteSqlProvider;

public class CenGruposclienteSqlExtendsProvider extends CenGruposclienteSqlProvider{

	public String getLabel(AdmUsuarios usuario) {
		SQL sql = new SQL();
		
		sql.SELECT("distinct GRUCLI.IDGRUPO");
		sql.SELECT("GENR.DESCRIPCION");
		sql.FROM(" cen_gruposcliente GRUCLI");
		sql.INNER_JOIN("  GEN_RECURSOS_CATALOGOS GENR on GRUCLI.NOMBRE = GENR.IDRECURSO");
		sql.WHERE(" GRUCLI.IDINSTITUCION = '"+ usuario.getIdinstitucion()  + "'");
		sql.WHERE(" GENR.IDLENGUAJE = '"+ usuario.getIdlenguaje()+ "'");
		sql.ORDER_BY("GENR.DESCRIPCION");
		return sql.toString();
	}
	
	public String selectDistinctGruposClientes(String idInstitucion,String idLenguaje, String descripcion) {
		SQL sql = new SQL();
		
		sql.SELECT("DISTINCT CLIENTE.*");
		sql.FROM("CEN_GRUPOSCLIENTE CLIENTE");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CATALOGO ON CLIENTE.NOMBRE = CATALOGO.IDRECURSO AND IDLENGUAJE = '" + idLenguaje + "' ");
		sql.WHERE("CLIENTE.IDINSTITUCION = '"+ idInstitucion +"'");
		sql.WHERE("CATALOGO.DESCRIPCION = '" + descripcion +"'");
			
		return sql.toString();
	}
	
	public String insertSelectiveForCreateLegalPerson(String idInstitucion, AdmUsuarios usuario) {
		SQL sql = new SQL();
		sql.INSERT_INTO("CEN_GRUPOSCLIENTE");
		sql.VALUES("FECHAMODIFICACION", "SYSDATE");
		sql.VALUES("IDGRUPO", "(SELECT MAX(IDGRUPO) + 1 FROM CEN_GRUPOSCLIENTE)");
		sql.VALUES("IDINSTITUCION", idInstitucion);
		sql.VALUES("NOMBRE", "(SELECT MAX(IDRECURSO) FROM GEN_RECURSOS_CATALOGOS WHERE NOMBRETABLA = 'CEN_GRUPOSCLIENTE')");
		sql.VALUES("USUMODIFICACION", String.valueOf(usuario.getIdusuario()));
		return sql.toString();
	}
}
