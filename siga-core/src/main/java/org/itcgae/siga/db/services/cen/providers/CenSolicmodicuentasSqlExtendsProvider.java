package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.commons.utils.SolModifSQLUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class CenSolicmodicuentasSqlExtendsProvider {

	public String searchSolModifDatosBancarios(SolicitudModificacionSearchDTO solicitudModificacionSearchDTO,
			String idLenguaje, String idInstitucion) {

		String rdo = "SELECT * FROM ("
				+ SolModifSQLUtils.getGeneralRequest(solicitudModificacionSearchDTO, idLenguaje, idInstitucion)
				+ " ) UNION ( "
				+ SolModifSQLUtils.getBancDataRequest(solicitudModificacionSearchDTO, idLenguaje, idInstitucion)
				+ " ) ORDER BY 6 DESC";
		return rdo;
	}

	public String getMaxIdSolicitud(String idInstitucion, String idPersona) {
		SQL sql = new SQL();
		sql.SELECT("MAX(IDSOLICITUD) AS IDSOLICITUD");
		sql.FROM("CEN_SOLICMODICUENTAS");

		return sql.toString();
	}

}
