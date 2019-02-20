package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.commons.utils.SolModifSQLUtils;
import org.itcgae.siga.db.mappers.CenSolicitmodifdatosbasicosSqlProvider;

public class CenSolicitmodifdatosbasicosSqlExtendsProvider extends CenSolicitmodifdatosbasicosSqlProvider{
	
	public String searchSolModifDatosGenerales(SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, String idLenguaje, String idInstitucion, Long idPersona) {
		if (null != idPersona) {
			String rdo = "SELECT * FROM ((" + SolModifSQLUtils.getGeneralRequest(solicitudModificacionSearchDTO, idLenguaje, idInstitucion) + " ) UNION ( "
					+ SolModifSQLUtils.getBasicDataRequest(solicitudModificacionSearchDTO, idLenguaje, idInstitucion) + " )) WHERE IDPERSONA = "+ idPersona +"  ORDER BY 6 DESC";
			return rdo;
		}else{
			String rdo = "SELECT * FROM (" + SolModifSQLUtils.getGeneralRequest(solicitudModificacionSearchDTO, idLenguaje, idInstitucion) + " ) UNION ( "
					+ SolModifSQLUtils.getBasicDataRequest(solicitudModificacionSearchDTO, idLenguaje, idInstitucion) + " ) ORDER BY 6 DESC";
			return rdo;
		}

	}
	
	public String getMaxIdSolicitud(String idInstitucion, String idPersona) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDSOLICITUD) AS IDSOLICITUD");
		sql.FROM("CEN_SOLICITMODIFDATOSBASICOS");
//		sql.WHERE("IDINSTITUCION = '"+idInstitucion+"'");
//		sql.WHERE("IDPERSONA = '"+ idPersona +"'");
		
		return sql.toString();
	}
	
	public String getAutoAceptar(String idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("VALOR");
		sql.FROM("gen_parametros");
		sql.WHERE("PARAMETRO = 'SOLICITUDES_MODIF_CENSO'");
		sql.WHERE("IDINSTITUCION = '"+idInstitucion +"'");
		return sql.toString();
	}
	

}
