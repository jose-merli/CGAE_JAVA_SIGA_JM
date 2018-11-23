package org.itcgae.siga.db.services.cen.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.EtiquetaUpdateDTO;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.CenDatoscv;
import org.itcgae.siga.db.entities.CenSolicitudmodificacioncv;
import org.itcgae.siga.db.mappers.CenDatoscvSqlProvider;
import org.itcgae.siga.db.mappers.CenSolicitmodifdatosbasicosSqlProvider;
import org.itcgae.siga.db.mappers.CenSolicitudmodificacioncvSqlProvider;

public class CenSolicitmodifdatosbasicosSqlExtendsProvider extends CenSolicitmodifdatosbasicosSqlProvider{
	
	public String getMaxIdSolicitud(String idInstitucion, String idPersona) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDSOLICITUD) AS IDSOLICITUD");
		sql.FROM("CEN_SOLICITMODIFDATOSBASICOS");
		sql.WHERE("IDINSTITUCION = '"+idInstitucion+"'");
		sql.WHERE("IDPERSONA = '"+ idPersona +"'");
		
		return sql.toString();
	}
	

}
