package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.SerieFacturacionItem;
import org.itcgae.siga.db.mappers.FacGrupcritincluidosenserieSqlProvider;
import org.itcgae.siga.db.mappers.FacSeriefacturacionSqlProvider;

import java.util.stream.Collectors;

public class FacGrupcritincluidosenserieExtendsSqlProvider extends FacGrupcritincluidosenserieSqlProvider {
	
	public String getConsultasSerie(String idSerieFacturacion, Short idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("con.idinstitucion");
		sql.SELECT("gc.idconsulta idconsulta");
		sql.SELECT("gc.idgruposcriterios idconsultaanterior");
		sql.SELECT("con.general");
		sql.SELECT("con.descripcion");
		sql.SELECT("con.observaciones");
		sql.SELECT("con.tipoconsulta");
		sql.SELECT("con.idmodulo");
		sql.SELECT("con.idclasecomunicacion");
		sql.SELECT("con.idobjetivo");
		sql.SELECT("con.sentencia");

		sql.FROM("fac_grupcritincluidosenserie gcs");
		sql.INNER_JOIN("cen_gruposcriterios gc ON (gcs.idinstitucion_grup = gc.idinstitucion AND gcs.idgruposcriterios = gc.idgruposcriterios)");
		sql.INNER_JOIN("con_consulta con ON (gc.idinstitucion = con.idinstitucion AND gc.idconsulta = con.idconsulta)");

		sql.WHERE("gcs.idinstitucion = " + idInstitucion);
		sql.WHERE("gcs.idseriefacturacion = " + idSerieFacturacion);

		sql.ORDER_BY("con.descripcion");
		
		return sql.toString();
	}

	public String comboDestinatarios(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT_DISTINCT("(cg.IDINSTITUCION || '-' || cg.IDGRUPOSCRITERIOS) id");
		sql.SELECT_DISTINCT("cg.NOMBRE");

		sql.FROM("FAC_GRUPCRITINCLUIDOSENSERIE fg");
		sql.INNER_JOIN("CEN_GRUPOSCRITERIOS cg ON (fg.IDINSTITUCION_GRUP=cg.IDINSTITUCION AND fg.IDGRUPOSCRITERIOS=cg.IDGRUPOSCRITERIOS)");

		sql.WHERE("fg.idinstitucion = " + idInstitucion);

		sql.ORDER_BY("cg.NOMBRE");

		return sql.toString();
	}

}
