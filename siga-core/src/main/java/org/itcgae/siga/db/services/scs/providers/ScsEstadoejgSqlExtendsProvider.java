package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.db.entities.ScsEstadoejg;
import org.itcgae.siga.db.entities.ScsEstadoejgKey;
import org.itcgae.siga.db.mappers.ScsEstadoejgSqlProvider;

public class ScsEstadoejgSqlExtendsProvider extends ScsEstadoejgSqlProvider {

          public String getEstadoEjg(AsuntosClaveJusticiableItem asuntoClave, String idLenguaje) {

                    SQL sql = new SQL();
                    SQL sql2 = new SQL();
                    
                    sql2.SELECT("E.IDESTADOEJG");
                    sql2.SELECT("REC.DESCRIPCION");
                    sql2.SELECT("E.FECHAINICIO");

                    sql2.FROM("SCS_ESTADOEJG E");
                    sql2.INNER_JOIN("SCS_MAESTROESTADOSEJG MAESTROESTADO ON MAESTROESTADO.IDESTADOEJG = E.IDESTADOEJG");
                    sql2.INNER_JOIN("GEN_RECURSOS_CATALOGOS REC ON REC.IDRECURSO = MAESTROESTADO.DESCRIPCION AND IDLENGUAJE = '" + idLenguaje + "'");
          
                    sql2.WHERE("E.IDINSTITUCION = '" + asuntoClave.getIdInstitucion() + "'");
                    sql2.WHERE("E.IDTIPOEJG = '" + asuntoClave.getClave() + "'");
                    sql2.WHERE("E.ANIO  = '" + asuntoClave.getAnio() + "'");
                    sql2.WHERE("E.NUMERO  = '" + asuntoClave.getNumero() + "'");
                    sql2.WHERE("E.FECHABAJA IS NULL");

                    sql2.ORDER_BY("TRUNC(E.FECHAINICIO) DESC, E.IDESTADOPOREJG DESC");
                    
                    sql.SELECT("*");
                    
                    sql.FROM("(" + sql2 + ")");
                    
                    sql.WHERE("ROWNUM = 1");
                    
                    return sql.toString();
          }

	public String comboEstadoEjg(Short idLenguaje, String filtroEstadoEjg) {
                    SQL sql = new SQL();

        sql.SELECT("IDESTADOEJG, F_SIGA_GETRECURSO(DESCRIPCION, 1) AS DESCRIPCION, CODIGOEXT, BLOQUEADO, ORDEN, VISIBLECOMISION, EDITABLECOMISION");
        sql.FROM("SCS_MAESTROESTADOSEJG");
        switch(filtroEstadoEjg) {
        
        	case "1" :
        		sql.WHERE("IDESTADOEJG NOT IN (25,26,13,0,9,10,12,15,16,20)");
        		
        		break;
        		
        	case "2" :
        			
        		break;
        		
        	default:
        		sql.WHERE("IDESTADOEJG NOT IN (25,26,13,0,9,10,12,15,16,20)");
        		
        		break;
        	
        }
       
        sql.ORDER_BY("ORDEN");

        return sql.toString();
    }
	
	public String bajaEstadoEjg(ScsEstadoejg estado) {
		SQL sql = new SQL();
		sql.UPDATE("SCS_ESTADOEJG");
		sql.SET("FECHABAJA = SYSDATE");
		sql.WHERE("IDINSTITUCION = "+estado.getIdinstitucion());
		sql.WHERE("IDTIPOEJG = "+estado.getIdtipoejg());
		sql.WHERE("ANIO = "+estado.getAnio());
		sql.WHERE("NUMERO = "+estado.getNumero());
		sql.WHERE("IDESTADOPOREJG = "+estado.getIdestadoporejg());		
		return sql.toString();
	}
    
    public String getEstados(EjgItem ejgItem, String idInstitucion, String idLenguaje) {
		SQL sql = new SQL();
		
        sql.SELECT("estado.fechainicio," + 
        		" estado.fechamodificacion," + 
        		" estado.idestadoejg," + 
        		" recursos.descripcion," + 
        		" estado.observaciones," + 
        		" estado.automatico," +
        		" estado.anio,"+
        		" estado.IDINSTITUCION,"+
        		" estado.IDTIPOEJG,"+
        		" estado.numero,"+
        		" estado.FECHABAJA,"+
        		"estado.IDESTADOPOREJG,"+
        		" maestro.visiblecomision," + 
        		" NULL nombre," + 
        		" NULL apellidos1," + 
        		" NULL apellidos2," +
        		" usuario.descripcion AS usuariomod");
		//sql.SELECT("persona.apellidos1 || ' ' || persona.apellidos2 || ', ' || persona.nombre AS usuariomod");

        sql.FROM("scs_estadoejg estado");
        sql.INNER_JOIN("scs_maestroestadosejg maestro on (estado.idestadoejg=maestro.idestadoejg)");
        sql.INNER_JOIN("gen_recursos_catalogos recursos on (maestro.descripcion=recursos.idrecurso)");
        sql.LEFT_OUTER_JOIN("cen_persona persona on (estado.usumodificacion=persona.idpersona)");
        sql.LEFT_OUTER_JOIN("adm_usuarios usuario ON (estado.usumodificacion = usuario.idusuario and estado.idinstitucion = usuario.idinstitucion)");

        if(ejgItem.getAnnio() != null && ejgItem.getAnnio() != "") {
            sql.WHERE("estado.anio = '" + ejgItem.getAnnio() + "'");
        }
        if(ejgItem.getNumero() != null && ejgItem.getNumero() != "") {
            sql.WHERE ("estado.numero = '"+ ejgItem.getNumero() + "'");
        }
        if(ejgItem.getTipoEJG() != null && ejgItem.getTipoEJG() != "") {
            sql.WHERE ("estado.idtipoejg = '" + ejgItem.getTipoEJG() + "'");
        }
        sql.WHERE ("recursos.idlenguaje = '" + idLenguaje + "'");
        sql.WHERE ("estado.idinstitucion = '" + idInstitucion + "'");
        if(!ejgItem.isHistorico())
        	sql.WHERE("estado.fechabaja is null");
        
        sql.ORDER_BY("trunc(estado.fechainicio) desc, estado.IDESTADOPOREJG DESC");
        
        return sql.toString();
    }
    
    public String getUltEstadoEjg(EjgItem ejgItem, String idInstitucion) {
    	
    	SQL sql = new SQL();
		
    	//FALTARIA AÑADIR LA VISIBILIDAD DE LA COMISION PARA ESE ESTADO
    	sql.SELECT("estado.fechainicio," + 
        		" estado.fechamodificacion," + 
        		" estado.idestadoejg," + 
        		" recursos.descripcion," + 
        		" estado.observaciones," + 
        		" estado.automatico," +
        		" estado.anio,"+
        		" estado.IDINSTITUCION,"+
        		" estado.IDTIPOEJG,"+
        		" estado.numero,"+
        		" estado.FECHABAJA,"+
        		"estado.IDESTADOPOREJG,"+
        		" maestro.visiblecomision," + 
        		" NULL nombre," + 
        		" NULL apellidos1," + 
        		" NULL apellidos2," +
        		" usuario.descripcion AS usuariomod");
    	
    	sql.FROM("scs_estadoejg estado");
        sql.INNER_JOIN("scs_maestroestadosejg maestro on (estado.idestadoejg=maestro.idestadoejg)");
        sql.INNER_JOIN("gen_recursos_catalogos recursos on (maestro.descripcion=recursos.idrecurso)");
        sql.LEFT_OUTER_JOIN("cen_persona persona on (estado.usumodificacion=persona.idpersona)");
        sql.LEFT_OUTER_JOIN("adm_usuarios usuario ON (estado.usumodificacion = usuario.idusuario and estado.idinstitucion = usuario.idinstitucion)");

        sql.WHERE("estado.idestadoporejg = F_SIGA_GET_ULTIMOESTADOPOREJG("+ idInstitucion +",\r\n"
        		+ ejgItem.getTipoEJG()+",\r\n"
        		+ ejgItem.getAnnio()+",\r\n"
        		+ ejgItem.getNumero()+") ");
        sql.WHERE("estado.IDINSTITUCION = "+idInstitucion+" and estado.IDTIPOEJG= "+ ejgItem.getTipoEJG() 
        +"  and estado.ANIO = "+ ejgItem.getAnnio()+ " and estado.NUMERO = "+ ejgItem.getNumero());
        
        return sql.toString();
    
    }
    
    public String getEditResolEjg(EjgItem ejgItem, String idInstitucion) {
    	
    	SQL sql = new SQL();
		
    	sql.SELECT("maestro.EDITABLECOMISION");
    	
    	sql.FROM("scs_estadoejg estado");
        sql.INNER_JOIN("scs_maestroestadosejg maestro on (estado.idestadoejg=maestro.idestadoejg)");

        sql.WHERE("estado.idestadoporejg = F_SIGA_GET_ULTIMOESTADOPOREJG("+ idInstitucion +",\r\n"
        		+ ejgItem.getTipoEJG()+",\r\n"
        		+ ejgItem.getAnnio()+",\r\n"
        		+ ejgItem.getNumero()+") ");
        sql.WHERE("estado.IDINSTITUCION = "+idInstitucion+" and estado.IDTIPOEJG= "+ ejgItem.getTipoEJG() 
        +"  and estado.ANIO = "+ ejgItem.getAnnio()+ " and estado.NUMERO = "+ ejgItem.getNumero());
        
        return sql.toString();
    
    }

}
