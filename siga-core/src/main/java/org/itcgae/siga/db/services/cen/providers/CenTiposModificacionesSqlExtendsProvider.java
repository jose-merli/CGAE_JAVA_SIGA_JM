package org.itcgae.siga.db.services.cen.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.commons.utils.UtilidadesString;

public class CenTiposModificacionesSqlExtendsProvider {
	
	public String getTipoModificacion(String idLenguage) {
		SQL sql = new SQL();
		sql.SELECT("tip.IDTIPOMODIFICACION AS VALUE");
		sql.SELECT("cat.DESCRIPCION AS LABEL");
		sql.FROM("CEN_TIPOSMODIFICACIONES tip");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS cat on cat.IDRECURSO = tip.DESCRIPCION");
		sql.WHERE("cat.IDLENGUAJE ='"+ idLenguage + "'");
		return sql.toString();
	}

	public String searchModificationRequest(SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, String idLenguage) {
		
		SQL sql = new SQL();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
		
		sql.SELECT_DISTINCT("cat1.DESCRIPCION as ESTADO");
		sql.SELECT("sol.IDSOLICITUD as IDSOLICITUD");
		sql.SELECT("cat2.DESCRIPCION as TIPOMODIFICACION");
		sql.SELECT("col.NCOLEGIADO as NUMCOLEGIADO");
		sql.SELECT("per.NOMBRE as NOMBRE");
		sql.SELECT("sol.FECHAALTA as FECHAALTA");
		sql.FROM("CEN_SOLICITUDESMODIFICACION sol");
		sql.INNER_JOIN("CEN_ESTADOSOLICITUDMODIF est on est.IDESTADOSOLIC = sol.IDESTADOSOLIC");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS cat1 on cat1.IDRECURSO = est.DESCRIPCION");
		sql.INNER_JOIN("CEN_TIPOSMODIFICACIONES modif on modif.IDTIPOMODIFICACION = sol.IDTIPOMODIFICACION");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS cat2 on cat2.IDRECURSO = modif.DESCRIPCION");
		sql.INNER_JOIN("CEN_PERSONA per on per.IDPERSONA = sol.IDPERSONA");
		sql.INNER_JOIN("CEN_COLEGIADO col on col.IDPERSONA = per.IDPERSONA and col.IDINSTITUCION = sol.IDINSTITUCION");
		sql.WHERE("cat1.IDLENGUAJE ='"+ idLenguage + "'");
		sql.WHERE("cat2.IDLENGUAJE ='"+ idLenguage + "'");
//		sql.WHERE("per.idpersona ='"+ solicitudModificacionSearchDTO.getIdPersona() + "'");
		sql.WHERE("sol.idinstitucion ='"+ solicitudModificacionSearchDTO.getIdInstitucion() + "'");
	
		if (!UtilidadesString.esCadenaVacia(solicitudModificacionSearchDTO.getTipoModificacion())) {
			sql.WHERE(UtilidadesString.filtroTextoBusquedas("sol.IDTIPOMODIFICACION",
					solicitudModificacionSearchDTO.getTipoModificacion()));
		}

		if (!UtilidadesString.esCadenaVacia(solicitudModificacionSearchDTO.getEstado())) {
			sql.WHERE(UtilidadesString.filtroTextoBusquedas("sol.IDESTADOSOLIC",
					solicitudModificacionSearchDTO.getEstado()));
		}

		if (null != solicitudModificacionSearchDTO.getFechaDesde()) {
			String fechaDesde = dateFormat.format(solicitudModificacionSearchDTO.getFechaDesde());
			sql.WHERE(" TO_DATE(sol.FECHAALTA,'DD/MM/YYYY') >= TO_DATE('" +fechaDesde + "', 'DD/MM/YYYY') ");
		}
		
		if (null != solicitudModificacionSearchDTO.getFechaHasta()) {
			String fechaHasta = dateFormat.format(solicitudModificacionSearchDTO.getFechaHasta());
			sql.WHERE(" TO_DATE(sol.FECHAALTA,'DD/MM/YYYY') <= TO_DATE('" +fechaHasta + "', 'DD/MM/YYYY') ");
		}

		return sql.toString();
	}
}
