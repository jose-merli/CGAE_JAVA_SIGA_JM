package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.PysPreciosserviciosSqlProvider;

public class PysPreciosserviciosSqlExtendsProvider extends PysPreciosserviciosSqlProvider {

	public String selectMaxIdPrecioServicio(Short idInstitucion, Long idServicio, Long idServicioInstitucion, Short idPeriocidad) {

		SQL sql = new SQL();

		sql.SELECT("NVL(max(IDPRECIOSSERVICIOS) +1, 1) AS IDPRECIOSERVICIO");
		sql.FROM("PYS_PRECIOSSERVICIOS");
		sql.WHERE("idInstitucion =" + idInstitucion);
		sql.WHERE("idTipoServicios = 5");
		sql.WHERE("idServicio = " + idServicio);
		sql.WHERE("idserviciosinstitucion = " + idServicioInstitucion);
		sql.WHERE("IDPERIODICIDAD = " + idPeriocidad );
		  
		return sql.toString();
	}
	
	public String selectPricesCourse(Short idInstitucion, Long idServicio, String idLenguaje, String codigoCurso) {

		SQL sql = new SQL();

		sql.SELECT("pys.DESCRIPCION");
		sql.SELECT("pys.VALOR");
		sql.SELECT("DECODE(pys.PORDEFECTO,1,'SI','NO') AS PORDEFECTO");
		sql.SELECT("cat.DESCRIPCION as PERIOCIDAD");
		sql.FROM("PYS_SERVICIOS SERVICIO");
		sql.INNER_JOIN("PYS_SERVICIOSINSTITUCION SERVICIOINST ON SERVICIOINST.IDSERVICIO = SERVICIO.IDSERVICIO "
				+ "AND SERVICIO.IDTIPOSERVICIOS = SERVICIOINST.IDTIPOSERVICIOS  AND SERVICIO.IDINSTITUCION = SERVICIOINST.IDINSTITUCION ");
		sql.INNER_JOIN("PYS_PRECIOSSERVICIOS pys ON PYS.IDSERVICIO = SERVICIO.IDSERVICIO AND SERVICIO.IDTIPOSERVICIOS = PYS.IDTIPOSERVICIOS"
												+ "	 AND PYS.IDSERVICIOSINSTITUCION = SERVICIOINST.IDSERVICIOSINSTITUCION");
		sql.INNER_JOIN("PYS_PERIODICIDAD per on per.IDPERIODICIDAD = pys.IDPERIODICIDAD");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS cat ON per.DESCRIPCION = cat.IDRECURSO and idLenguaje = '" + idLenguaje + "'");
		sql.WHERE("pys.idInstitucion =" + idInstitucion);
		sql.WHERE("SERVICIOINST.descripcion like '%" + codigoCurso + "%'");
		sql.WHERE("pys.idServicio = " + idServicio);
		
		return sql.toString();
	}

}
