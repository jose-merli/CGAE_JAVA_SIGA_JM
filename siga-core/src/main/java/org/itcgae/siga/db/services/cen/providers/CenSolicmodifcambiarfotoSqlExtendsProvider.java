package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.CenSolicmodifexportarfotoDTO;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.commons.utils.SolModifSQLUtils;
import org.itcgae.siga.db.mappers.CenSolicmodifcambiarfotoSqlProvider;

public class CenSolicmodifcambiarfotoSqlExtendsProvider extends CenSolicmodifcambiarfotoSqlProvider {

	public String searchSolModifDatoscambiarFoto(SolicitudModificacionSearchDTO solicitudModificacionSearchDTO,
			String idLenguaje, String idInstitucion) {

		String id = "";
		if(solicitudModificacionSearchDTO.getTipoModificacion() != null) {
			id = solicitudModificacionSearchDTO.getTipoModificacion();
		}
		
		String rdo = "SELECT * FROM ("
				+ SolModifSQLUtils.getGeneralRequest(solicitudModificacionSearchDTO, idLenguaje, idInstitucion)
				+ " ) UNION ( "
				+ SolModifSQLUtils.getDataChangeFotoRequest(id, solicitudModificacionSearchDTO, idLenguaje, idInstitucion)
				+ " ) ORDER BY 6 DESC";
		return rdo;
	}

	public String getMaxIdSolicitud(String idInstitucion, String idPersona) {
		SQL sql = new SQL();
		sql.SELECT("MAX(IDSOLICITUD) AS IDSOLICITUD");
		sql.FROM("CEN_SOLICMODIFCAMBIARFOTO");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("IDPERSONA = '" + idPersona + "'");

		return sql.toString();
	}

	
	public String updateByPrimaryKeySelectiveDTO(CenSolicmodifexportarfotoDTO record) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_SOLICMODIFCAMBIARFOTO");
		if (record.getMotivo() != null) {
			sql.SET("MOTIVO = #{motivo,jdbcType=VARCHAR}");
		}
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdpersona() != null) {
			sql.SET("IDPERSONA = #{idpersona,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdestadosolic() != null) {
			sql.SET("IDESTADOSOLIC = #{idestadosolic,jdbcType=DECIMAL}");
		}
		if (record.getExportarfoto() != null) {
			sql.SET("EXPORTARFOTO = #{exportarfoto,jdbcType=VARCHAR}");
		}
		if (record.getFechaalta() != null) {
			sql.SET("FECHAALTA = #{fechaalta,jdbcType=TIMESTAMP}");
		}
		sql.WHERE("IDSOLICITUD = #{idsolicitud,jdbcType=DECIMAL}");
		return sql.toString();
	}
}
