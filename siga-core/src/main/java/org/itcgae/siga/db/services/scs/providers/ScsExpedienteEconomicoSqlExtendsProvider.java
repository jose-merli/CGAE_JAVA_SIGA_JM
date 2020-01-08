package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.db.mappers.ScsEejgPeticionesSqlProvider;

public class ScsExpedienteEconomicoSqlExtendsProvider extends ScsEejgPeticionesSqlProvider {

	public String getExpedientesEconomicos(EjgItem ejgItem, String idInstitucion, Integer tamMaximo, String idLenguaje) {
		SQL sql = new SQL();
//		String idLenguaje = "1";
		sql.SELECT("eejg.idpeticion," + 
					" eejg.fechasolicitud," + 
					" eejg.fechapeticion," + 
					" eejg.estado," + 
					" grcatalogos.descripcion," + 
					" eejg.csv");
		
		sql.FROM("scs_eejg_peticiones eejg");
		
		sql.INNER_JOIN("CEN_ESTADOSOLICITUD cestado on (cestado.IDESTADO = eejg.estado)");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS grcatalogos on (grcatalogos.DESCRIPCION=cestado.descripcion)");

		
		if(ejgItem.getAnnio() != null && ejgItem.getAnnio() != "")
			sql.WHERE("eejg.anio = '" + ejgItem.getAnnio() + "'");
		if(ejgItem.getNumero() != null && ejgItem.getNumero() != "")
			sql.WHERE("eejg.numero = '" + ejgItem.getNumero() + "'");
		if(ejgItem.getTipoEJG() != null && ejgItem.getTipoEJG() != "")
			sql.WHERE("eejg.idtipoejg = '" + ejgItem.getTipoEJG() + "'");
		if(idInstitucion != null && idInstitucion != "")
			sql.WHERE("eejg.idinstitucion = '" + idInstitucion + "'");
		sql.WHERE("grcatalogos.idlenguaje = '" + idLenguaje +"'");

		if (tamMaximo != null) {
			Integer tamMaxNumber = tamMaximo + 1;
			sql.WHERE("rownum <= " + tamMaxNumber);
		}
		return sql.toString();
	}
}
