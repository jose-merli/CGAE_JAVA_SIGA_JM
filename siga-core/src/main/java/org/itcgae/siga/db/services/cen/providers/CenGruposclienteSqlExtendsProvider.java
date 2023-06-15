package org.itcgae.siga.db.services.cen.providers;

import java.util.List;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.CenGruposclienteSqlProvider;

public class CenGruposclienteSqlExtendsProvider extends CenGruposclienteSqlProvider{

	public String getLabel(AdmUsuarios usuario) {
		SQL sql = new SQL();
		
		sql.SELECT("distinct MAX(GRUCLI.IDGRUPO) AS IDGRUPO");
		sql.SELECT("INITCAP(GENR.DESCRIPCION) as DESCRIPCION");
		sql.SELECT("GRUCLI.IDINSTITUCION");
		sql.FROM("cen_gruposcliente GRUCLI");
		sql.INNER_JOIN("  GEN_RECURSOS_CATALOGOS GENR on GRUCLI.NOMBRE = GENR.IDRECURSO");
		sql.WHERE(" GRUCLI.IDINSTITUCION in ('2000', '"+ usuario.getIdinstitucion()  + "')");
		sql.WHERE(" GENR.IDLENGUAJE = '"+ usuario.getIdlenguaje()+ "'");
		sql.GROUP_BY("GENR.DESCRIPCION, GRUCLI.IDINSTITUCION");
		sql.ORDER_BY("DESCRIPCION");
		return sql.toString();
	}
	
	public String selectDistinctGruposClientes(String idInstitucion,String idLenguaje, String grupo) {
		SQL sql = new SQL();
		
		sql.SELECT("DISTINCT CLIENTE.*");
		sql.FROM("CEN_GRUPOSCLIENTE CLIENTE");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CATALOGO ON CLIENTE.NOMBRE = CATALOGO.IDRECURSO AND IDLENGUAJE = '" + idLenguaje + "' ");
		sql.WHERE("CLIENTE.IDINSTITUCION = '"+ idInstitucion +"'");
		sql.WHERE("CLIENTE.IDGRUPO = '" + grupo +"'");
			
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
	
	public String selectDescripcionGrupos(List<String> grupos, AdmUsuarios usuario, String idInstitucion) {
		
		// preparar grupos en sentencia IN
		String gruposIN = "";
		for(int i=0;i< grupos.size(); i++) {
			
			gruposIN = gruposIN.concat("'");
			if(i != grupos.size() -1) {
				gruposIN = gruposIN.concat(grupos.get(i));
				gruposIN = gruposIN.concat("'");
				gruposIN = gruposIN.concat(",");
			}else {
				gruposIN = gruposIN.concat(grupos.get(i));
				gruposIN = gruposIN.concat("'");
			}	
		}
		
		SQL sql = new SQL();
		
		sql.SELECT("CAT.DESCRIPCION");
		sql.FROM("CEN_GRUPOSCLIENTE GRU");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON GRU.NOMBRE = CAT.IDRECURSO");
		sql.WHERE("CAT.IDLENGUAJE = '" + usuario.getIdlenguaje() + "'");
		sql.WHERE("CAT.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("GRU.IDGRUPO IN (" + gruposIN +  ")");
		
		return sql.toString();
	}
	
	public String selectDescripcionGruposColegiados(List<String[]> grupos, AdmUsuarios usuario, String idInstitucion) {
		
		// preparar grupos en sentencia IN
		String gruposIN = "";
		for(int i=0;i< grupos.size(); i++) {
			
			if (i == grupos.size() - 1) {
				gruposIN += "( grucli.IDGRUPO ='" + grupos.get(i)[0] + "' and grucli.IDINSTITUCION = '" + grupos.get(i)[1] + "')";
			} else {
				gruposIN += "( grucli.IDGRUPO ='" + grupos.get(i)[0] + "' and grucli.IDINSTITUCION = '" + grupos.get(i)[1] + "') or";

			}

		}
		
		SQL sql = new SQL();
		
		sql.SELECT("CAT.DESCRIPCION");
		sql.FROM("CEN_GRUPOSCLIENTE grucli");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON grucli.NOMBRE = CAT.IDRECURSO");
		sql.WHERE("CAT.IDLENGUAJE = '" + usuario.getIdlenguaje() + "'");
		sql.WHERE("(" + gruposIN +  ")");
		
		return sql.toString();
	}
	
	public String getMaxIdGrupo() {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDGRUPO)");
		sql.FROM("CEN_GRUPOSCLIENTE");
		
		return sql.toString();
	}
	
	public String comboEtiquetas(String idioma, Short idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("gc.idgrupo as id");
		sql.SELECT("f_siga_getrecurso(gc.nombre, " + idioma + ") as descripcion");
		sql.FROM("CEN_GRUPOSCLIENTE gc");
		sql.WHERE("gc.idinstitucion="+ idInstitucion);
		sql.ORDER_BY("descripcion");
			
		return sql.toString();
	}
}
