package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.commons.utils.SolModifSQLUtils;

public class CenSolicitudesmodificacionSqlExtendsProvider {

	public String getTipoModificacion(String idLenguage) {
		SQL sql = new SQL();
		sql.SELECT("E.IDTIPOSOLICITUD AS VALUE");
		sql.SELECT("F_SIGA_GETRECURSO(DESCRIPCION," + idLenguage +") AS LABEL");
		sql.FROM("CEN_TIPOSOLICITUD E");
		return sql.toString();
	}
	
//	public String getMaxIdSolicitud(String idInstitucion, String idPersona) {
//		SQL sql = new SQL();
//		sql.SELECT("MAX(IDSOLICITUD) AS IDSOLICITUD");
//		sql.FROM("CEN_SOLICMODICUENTAS");
//		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
//		sql.WHERE("IDPERSONA = '" + idPersona + "'");
//
//		return sql.toString();
//	}
	
	public String searchSolModif(SolicitudModificacionSearchDTO solicitudModificacionSearchDTO,
			String idLenguaje, String idInstitucion) {

		String rdo = "SELECT * FROM (" + SolModifSQLUtils.getGeneralRequest(solicitudModificacionSearchDTO, idLenguaje, idInstitucion)
				+ " ) ORDER BY 6 DESC";
		return rdo;
	}
	
	public String getMaxIdSolicitud(String idInstitucion, String idPersona) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDSOLICITUD) AS IDSOLICITUD");
		sql.FROM("CEN_SOLICITUDESMODIFICACION");
		sql.WHERE("IDINSTITUCION = '"+idInstitucion+"'");
		sql.WHERE("IDPERSONA = '"+ idPersona +"'");
		
		return sql.toString();
	}
	

	public String searchTipoCambio(String descripcion, String idLenguaje) {
		SQL sql = new SQL();

		sql.SELECT("T.IDTIPOCAMBIO AS IDTIPOCAMBIO");
		sql.FROM("GEN_RECURSOS_CATALOGOS R");
		sql.INNER_JOIN("CEN_TIPOCAMBIO T ON T.DESCRIPCION = R.IDRECURSO");
		sql.WHERE("IDLENGUAJE = '" + idLenguaje + "'");
		sql.WHERE("R.DESCRIPCION = '" + descripcion + "'");
		
		return sql.toString();
	}
}
