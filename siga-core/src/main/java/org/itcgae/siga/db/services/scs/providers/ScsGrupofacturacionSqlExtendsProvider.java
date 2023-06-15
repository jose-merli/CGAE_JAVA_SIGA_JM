package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsGrupofacturacionSqlProvider;

import java.util.List;

public class ScsGrupofacturacionSqlExtendsProvider extends ScsGrupofacturacionSqlProvider{
	
	public String getComboGrupoFacturacion(String idInstitucion, String idLenguaje) {
		SQL sql = new SQL();
		
		sql.SELECT("SCS_GRUPOFACTURACION.IDGRUPOFACTURACION");
		sql.SELECT("GEN_RECURSOS_CATALOGOS.DESCRIPCION");
		sql.FROM("SCS_GRUPOFACTURACION");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS ON SCS_GRUPOFACTURACION.NOMBRE= GEN_RECURSOS_CATALOGOS.IDRECURSO");
		
		sql.WHERE("SCS_GRUPOFACTURACION.FECHA_BAJA IS NULL");
		sql.WHERE("SCS_GRUPOFACTURACION.IDINSTITUCION = '"+idInstitucion+"'");
		sql.WHERE("GEN_RECURSOS_CATALOGOS.IDLENGUAJE = '"+idLenguaje+"'");
		
		sql.ORDER_BY("GEN_RECURSOS_CATALOGOS.DESCRIPCION");
		return sql.toString();
	}

	public String grupoFacturacionByColegios(List<String> idColegios, String idLenguaje) {
		SQL sql = new SQL();

		sql.SELECT("SCS_GRUPOFACTURACION.IDGRUPOFACTURACION");
		sql.SELECT("GEN_RECURSOS_CATALOGOS.DESCRIPCION");
		sql.FROM("SCS_GRUPOFACTURACION");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS ON SCS_GRUPOFACTURACION.NOMBRE= GEN_RECURSOS_CATALOGOS.IDRECURSO");

		sql.WHERE("SCS_GRUPOFACTURACION.FECHA_BAJA IS NULL");
		sql.WHERE("SCS_GRUPOFACTURACION.IDINSTITUCION IN " + idColegios.toString().replace("[", "(").replace("]", ")"));
		sql.WHERE("GEN_RECURSOS_CATALOGOS.IDLENGUAJE = '" + idLenguaje + "'");

		sql.ORDER_BY("GEN_RECURSOS_CATALOGOS.DESCRIPCION");
		return sql.toString();
	}
}
