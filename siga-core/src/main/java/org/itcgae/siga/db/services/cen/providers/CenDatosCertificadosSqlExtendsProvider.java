package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CerSolicitudcertificadosSqlProvider;

public class CenDatosCertificadosSqlExtendsProvider extends CerSolicitudcertificadosSqlProvider{
	
	public String datosCertificadosSearch(String idPersona,Short idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("CERT.IDSOLICITUD");
		sql.SELECT("CERT.IDPERSONA_DES AS IDPERSONA");
		sql.SELECT("PROD.DESCRIPCION AS DESCRIPCION");
		sql.SELECT("TO_CHAR(CERT.FECHAEMISIONCERTIFICADO,'DD/MM/YYYY') AS FECHAEMISION");
//		sql2.SELECT("TO_CHAR(PER.FECHANACIMIENTO,'DD/MM/YYYY')  AS FECHACONSTITUCION");
		sql.FROM("CER_SOLICITUDCERTIFICADOS CERT");
		sql.INNER_JOIN("PYS_PRODUCTOSINSTITUCION PROD ON Cert.PPN_IDTIPOPRODUCTO = PROD.IDTIPOPRODUCTO");
		sql.WHERE("CERT.PPN_IDPRODUCTO = PROD.IDPRODUCTO");
		sql.WHERE("CERT.PPN_IDPRODUCTOINSTITUCION = PROD.IDPRODUCTOINSTITUCION");
		sql.WHERE("CERT.IDINSTITUCION = PROD.IDINSTITUCION");
		sql.WHERE("PROD.IDINSTITUCION = '"+idInstitucion+"'");
		sql.WHERE("PROD.TIPOCERTIFICADO = 'C'");
		sql.WHERE("IDPERSONA_DES = '"+idPersona+"'");
		sql.ORDER_BY("FECHAEMISION DESC");
		return sql.toString();
	}

}
