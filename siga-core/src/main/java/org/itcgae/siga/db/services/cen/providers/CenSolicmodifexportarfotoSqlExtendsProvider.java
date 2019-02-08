package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.CenSolicmodifexportarfotoDTO;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.commons.utils.SolModifSQLUtils;
import org.itcgae.siga.db.entities.CenSolicmodifexportarfoto;

public class CenSolicmodifexportarfotoSqlExtendsProvider {

	public String searchSolModifDatosUseFoto(SolicitudModificacionSearchDTO solicitudModificacionSearchDTO,
			String idLenguaje, String idInstitucion, Long idPersona) {

		String id = "";
		if(solicitudModificacionSearchDTO.getTipoModificacion() != null) {
			id = solicitudModificacionSearchDTO.getTipoModificacion();
		}
		if (null != idPersona) {
			String rdo = "SELECT * FROM (("
					+ SolModifSQLUtils.getGeneralRequest(solicitudModificacionSearchDTO, idLenguaje, idInstitucion)
					+ " ) UNION ( "
					+ SolModifSQLUtils.getDataPhotoRequest(id, solicitudModificacionSearchDTO, idLenguaje, idInstitucion)
					+ " )) WHERE IDPERSONA = "+ idPersona +"  ORDER BY 6 DESC";
			return rdo;
		}else{
			String rdo = "SELECT * FROM ("
					+ SolModifSQLUtils.getGeneralRequest(solicitudModificacionSearchDTO, idLenguaje, idInstitucion)
					+ " ) UNION ( "
					+ SolModifSQLUtils.getDataPhotoRequest(id, solicitudModificacionSearchDTO, idLenguaje, idInstitucion)
					+ " ) ORDER BY 6 DESC";
			return rdo;
		}
	}

	public String getMaxIdSolicitud(String idInstitucion, String idPersona) {
		SQL sql = new SQL();
		sql.SELECT("MAX(IDSOLICITUD) AS IDSOLICITUD");
		sql.FROM("CEN_SOLICMODICUENTAS");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("IDPERSONA = '" + idPersona + "'");

		return sql.toString();
	}

	
	public String updateByPrimaryKeySelectiveDTO(CenSolicmodifexportarfotoDTO record) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_SOLICMODIFEXPORTARFOTO");
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
