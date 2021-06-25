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
		sql.WHERE("numero = "+ejg.getNumEjg());
		sql.WHERE("idtipoejg = "+ejg.getTipoEJG());
		
		//se coge el csv de las observaciones, se pone en este campo porque no cuadraba el csv en el item del ejg
		if(ejg.getObservaciones()!=null && !ejg.getObservaciones().trim().isEmpty()) {
			sql.WHERE("csv = '"+ejg.getObservaciones()+"'");
		
		}else if(ejg.getNif()!=null && !ejg.getNif().trim().isEmpty()) {
			sql.WHERE("nif = '"+ejg.getNif()+"'");
			sql.WHERE("fechaconsulta=(SELECT MAX(p2.FECHACONSULTA) from scs_eejg_peticiones p2 where p.nif=p2.nif)");
		
		}else {
			sql.WHERE("fechaconsulta=(SELECT MAX(p2.FECHACONSULTA) from scs_eejg_peticiones p2 where p.nif=p2.nif)");
		}
		
		return sql.toString();
	}
	
}
