package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.db.mappers.ScsEejgPeticionesSqlProvider;

public class ScsEejgPeticionesSqlExtendsProvider extends ScsEejgPeticionesSqlProvider {

	public String getPeticionesPorEJG(EjgItem ejg){
		SQL sql = new SQL();
		
		sql.SELECT("IDPETICION");
		sql.SELECT("IDUSUARIOPETICION");
		sql.SELECT("FECHAPETICION");
		sql.SELECT("ESTADO");
		sql.SELECT("IDSOLICITUD");
		sql.SELECT("IDINSTITUCION");
		sql.SELECT("IDTIPOEJG");
		sql.SELECT("ANIO");
		sql.SELECT("NUMERO");
		sql.SELECT("IDPERSONA");
		sql.SELECT("NUMEROINTENTOSSOLICITUD");
		sql.SELECT("NUMEROINTENTOSCONSULTA");
		sql.SELECT("IDXML");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("FECHASOLICITUD");
		sql.SELECT("FECHACONSULTA");
		sql.SELECT("IDIOMA");
		sql.SELECT("NUMEROINTENTOSPENDIENTEINFO");
		sql.SELECT("NIF");
		sql.SELECT("NOMBRE");
		sql.SELECT("APELLIDO1");
		sql.SELECT("APELLIDO2");
		sql.SELECT("OBSERVACIONES");
		sql.SELECT("RUTA_PDF");
		sql.SELECT("IDECOMCOLA");
		sql.SELECT("MSGERROR");
		sql.SELECT("CSV");
		
		sql.FROM("SCS_EEJG_PETICIONES p");
		
		sql.WHERE("idinstitucion = "+ejg.getidInstitucion());
		sql.WHERE("anio = "+ejg.getAnnio());
		sql.WHERE("numero = "+ejg.getNumero());
		sql.WHERE("idtipoejg = "+ejg.getTipoEJG());
		
		//se coge el csv de las observaciones, se pone en este campo porque no cuadraba el csv en el item del ejg
		if(ejg.getObservaciones()!=null && !ejg.getObservaciones().trim().isEmpty()) {
			sql.WHERE("csv = '"+ejg.getObservaciones()+"'");
		
		}else if(ejg.getNif()!=null && !ejg.getNif().trim().isEmpty()) {
			sql.WHERE("nif = '"+ejg.getNif()+"'");
			sql.WHERE("fechaconsulta=(SELECT MAX(p2.FECHACONSULTA) from scs_eejg_peticiones p2 where p.nif=p2.nif)");
		
		}else {
			sql.WHERE("fechaconsulta=(SELECT MAX(p2.FECHACONSULTA) from scs_eejg_peticiones p2 where p2.idinstitucion = p.idinstitucion AND p2.anio = p.anio AND p2.numero = p.numero AND p2.idtipoejg = p.idtipoejg)");
		}
		
		return sql.toString();
	}
	
	public String getMaxIdpeticion() {
		SQL sql = new SQL();
		sql.SELECT("max(IDPETICION) +1 ");
		sql.FROM("SCS_EEJG_PETICIONES");
		return sql.toString();
	}
	
	public String getUltimoIdPeticion() {
		SQL sql = new SQL();
		sql.SELECT("max(IDPETICION)");
		sql.FROM("SCS_EEJG_PETICIONES");
		return sql.toString();
	}

	public String getExpedientesEconomicos(EjgItem ejgItem, String idInstitucion, Integer tamMaximo, String idLenguaje) {
		SQL sql = new SQL();
		sql.SELECT("eejg.idpeticion," +
				" eejg.fechasolicitud," +
				" eejg.fechapeticion," +
				" u.descripcion as solicitadopor," +
				" eejg.estado as idEstado," +
				" eejg.csv");
		sql.SELECT("sp.apellido1 || ' ' || sp.apellido2 || ', ' || sp.nombre || ' ' || sp.nif as justiciable");

		sql.FROM("scs_eejg_peticiones eejg");

		sql.INNER_JOIN("SCS_PERSONAJG sp on (eejg.IDINSTITUCION = sp.IDINSTITUCION and eejg.IDPERSONA = sp.IDPERSONA)");
		sql.INNER_JOIN("CEN_ESTADOSOLICITUD cestado on (cestado.IDESTADO = eejg.estado)");
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

		if (tamMaximo != null) {
			Integer tamMaxNumber = tamMaximo + 1;
			sql.WHERE("rownum <= " + tamMaxNumber);
		}
		sql.ORDER_BY("eejg.fechapeticion DESC, eejg.fechasolicitud DESC");
		return sql.toString();
	}
	
}
