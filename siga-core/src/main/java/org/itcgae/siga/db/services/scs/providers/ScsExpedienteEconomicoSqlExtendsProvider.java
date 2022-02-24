package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.db.mappers.ScsEejgPeticionesSqlProvider;

public class ScsExpedienteEconomicoSqlExtendsProvider extends ScsEejgPeticionesSqlProvider {

	public String getExpedientesEconomicos(EjgItem ejgItem, String idInstitucion, Integer tamMaximo, String idLenguaje) {
		SQL sql = new SQL();
		sql.SELECT("eejg.idpeticion," + 
					" eejg.fechasolicitud," + 
					" eejg.fechapeticion," + 
					" u.descripcion as solicitadopor," + 
					" grcatalogos.descripcion as estado," + 
					" eejg.csv");
		sql.SELECT("sp.apellido1 || ' ' || sp.apellido2 || ', ' || sp.nombre || ' ' || sp.nif as justiciable");
		
		sql.FROM("scs_eejg_peticiones eejg");

		sql.INNER_JOIN("SCS_PERSONAJG sp on (eejg.IDINSTITUCION = sp.IDINSTITUCION and eejg.IDPERSONA = sp.IDPERSONA)");
		sql.INNER_JOIN("CEN_ESTADOSOLICITUD cestado on (cestado.IDESTADO = eejg.estado)");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS grcatalogos on (grcatalogos.idrecurso=cestado.descripcion)");
		sql.LEFT_OUTER_JOIN("adm_usuarios u on (eejg.idusuariopeticion=u.idusuario and eejg.idinstitucion=u.idinstitucion)");
		
		if(ejgItem.getAnnio() != null && ejgItem.getAnnio() != "")
			sql.WHERE("eejg.anio = '" + ejgItem.getAnnio() + "'");
		if(ejgItem.getNumero() != null && ejgItem.getNumero() != "")
			sql.WHERE("eejg.numero = '" + ejgItem.getNumero() + "'");
//		sql.WHERE("eejg.numero='4556'");
		if(ejgItem.getTipoEJG() != null && ejgItem.getTipoEJG() != "")
			sql.WHERE("eejg.idtipoejg = '" + ejgItem.getTipoEJG() + "'");
		if(idInstitucion != null && idInstitucion != "")
			sql.WHERE("eejg.idinstitucion = '" + idInstitucion + "'");
		sql.WHERE("grcatalogos.idlenguaje = '" + idLenguaje +"'");

		if (tamMaximo != null) {
			Integer tamMaxNumber = tamMaximo + 1;
			sql.WHERE("rownum <= " + tamMaxNumber);
		}
		sql.ORDER_BY("eejg.fechapeticion DESC, eejg.fechasolicitud DESC");
		return sql.toString();
	}
}
