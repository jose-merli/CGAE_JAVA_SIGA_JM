package org.itcgae.siga.db.services.cen.providers;

import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.commons.utils.SolModifSQLUtils;

public class CenSolModifFacturacionServicioSqlExtendsProvider {

	public String searchSolModifDatosFacturacion(SolicitudModificacionSearchDTO solicitudModificacionSearchDTO,
			String idLenguaje, String idInstitucion) {

		String rdo = "SELECT * FROM (" + SolModifSQLUtils.getGeneralRequest(solicitudModificacionSearchDTO, idLenguaje, idInstitucion)
				+ " ) UNION ( " + SolModifSQLUtils.getDataInvoicingRequest(solicitudModificacionSearchDTO, idLenguaje, idInstitucion)
				+ " ) ORDER BY 6 DESC";
		return rdo;
	}
}