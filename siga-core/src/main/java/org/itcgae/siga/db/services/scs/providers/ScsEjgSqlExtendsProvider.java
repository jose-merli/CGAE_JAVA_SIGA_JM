package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.scs.EjgItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.ScsEjgSqlProvider;

public class ScsEjgSqlExtendsProvider extends ScsEjgSqlProvider {
	
	
	public String busquedaEJG(EjgItem ejgItem, String idInstitucion) {
		String dictamenCad = "";
		boolean indiferente = false;
		SQL sql = new SQL();
		
		String condicionAnnioNumActas = " (EXISTS (SELECT 1 FROM scs_ejg_acta ejgacta, scs_actacomision ac"
				+ " WHERE ejgacta.idinstitucionacta = ac.idinstitucion"
				+ " AND ejgacta.idacta = ac.idacta"
				+ " AND   ejgacta.anioacta = ac.anioacta"
				+ " AND   ejgacta.idinstitucionejg = ejg.idinstitucion"
				+ " AND   ejgacta.anioejg = ejg.anio"
				+ " AND   ejgacta.idtipoejg = ejg.idtipoejg"
				+ " AND   ejgacta.numeroejg = ejg.numero"
				+ " AND   ac.idinstitucion = " +  idInstitucion;
//				+ " AND   ac.anioacta = " + ejgItem.getAnnioActa()
//				+ " AND   ac.numeroacta = " + ejgItem.getNumActa() + ")";
		
		if(ejgItem.getAnnioActa() != null && ejgItem.getAnnioActa() != "")
			condicionAnnioNumActas = condicionAnnioNumActas + " AND   ac.anioacta = " + ejgItem.getAnnioActa();
		if(ejgItem.getNumActa() != null && ejgItem.getNumActa() != "")
			condicionAnnioNumActas = condicionAnnioNumActas + " AND   ac.numeroacta = " + ejgItem.getNumActa();
		condicionAnnioNumActas = condicionAnnioNumActas + "))";
		
		String condicionNumRegRemesa = " (EXISTS (SELECT 1 FROM cajg_ejgremesa ejgremesa, cajg_remesa remesa"
				+ " WHERE ejgremesa.idinstitucionremesa = remesa.idinstitucion"
				+ " AND ejgremesa.idinstitucion = ejg.idinstitucion"
				+ " AND   ejgremesa.anio = ejg.anio"
				+ " AND   ejgremesa.idtipoejg = ejg.idtipoejg"
				+ " AND   ejgremesa.numero = ejg.numero"
				+ " AND   remesa.idinstitucion = " + idInstitucion;
//				+ " AND   remesa.prefijo = " + ejgItem.getNumRegRemesa1()
//				+ " AND   remesa.numero = " + ejgItem.getNumRegRemesa2()
//				+ " AND   remesa.sufijo = " + ejgItem.getNumRegRemesa3() + ")";
		if(ejgItem.getNumRegRemesa1() != null && ejgItem.getNumRegRemesa1() != "")
			condicionNumRegRemesa = condicionNumRegRemesa + " AND   remesa.prefijo = '" + ejgItem.getNumRegRemesa1() + "'";
		if(ejgItem.getNumRegRemesa2() != null && ejgItem.getNumRegRemesa2() != "")
			condicionNumRegRemesa = condicionNumRegRemesa + " AND   remesa.numero = '" + ejgItem.getNumRegRemesa2() + "'"; 
		if(ejgItem.getNumRegRemesa3() != null && ejgItem.getNumRegRemesa3() != "")
			condicionNumRegRemesa = condicionNumRegRemesa + " AND   remesa.sufijo = '" + ejgItem.getNumRegRemesa3() + "'";
		condicionNumRegRemesa = condicionNumRegRemesa + "))";
		
		//select
		sql.SELECT("ejg.anio");
		sql.SELECT("ejg.idinstitucion");
		sql.SELECT("ejg.idtipoejg");
		sql.SELECT("ejg.numero");
		sql.SELECT("ejg.numejg numejg"); 
		sql.SELECT("'E' || EJG.ANIO || '/' || EJG.NUMEJG AS NUMANIO");
		sql.SELECT("TURNO.NOMBRE || '/' || GUARDIA.NOMBRE AS TURNO");
		sql.SELECT("TURNO.ABREVIATURA AS TURNODES");
		sql.SELECT("ejg.fechaapertura");
		sql.SELECT("ejg.fechamodificacion");
		sql.SELECT("per.apellidos1 || ' ' || per.apellidos2 || ', ' || per.nombre as NOMBREletrado");
		sql.SELECT("REC.DESCRIPCION AS ESTADOEJG");
		sql.SELECT("perjg.apellido1 || ' ' || perjg.apellido2 || ', ' || perjg.nombre as NOMBRESOLICITANTE");
		sql.SELECT("EJG.NUMEROPROCEDIMIENTO");
//		sql.SELECT("perjg.NIF");
//		sql.SELECT("perjg.correoelectronico");
//		sql.SELECT("perjg.fechanacimiento");


		
		//from
		sql.FROM("scs_ejg ejg");
		
		//joins
		sql.LEFT_OUTER_JOIN("cen_persona per on per.idpersona = ejg.idpersona");
		sql.LEFT_OUTER_JOIN("scs_personajg perjg on perjg.idpersona = ejg.idpersonajg AND perjg.IDINSTITUCION = EJG.IDINSTITUCION");
		sql.LEFT_OUTER_JOIN("SCS_TURNO  TURNO ON TURNO.IDINSTITUCION =EJG.IDINSTITUCION AND TURNO.IDTURNO =EJG.GUARDIATURNO_IDTURNO");
		sql.LEFT_OUTER_JOIN("SCS_GUARDIASTURNO GUARDIA  ON GUARDIA.IDINSTITUCION =EJG.IDINSTITUCION AND GUARDIA.IDTURNO =EJG.GUARDIATURNO_IDTURNO  AND GUARDIA.IDGUARDIA =EJG.GUARDIATURNO_IDGUARDIA");
		sql.INNER_JOIN("SCS_ESTADOEJG ESTADO ON (ESTADO.IDINSTITUCION = EJG.IDINSTITUCION "
				+ "AND ESTADO.IDTIPOEJG = EJG.IDTIPOEJG AND ESTADO.IDTIPOEJG = EJG.IDTIPOEJG "
				+ "AND ESTADO.ANIO = EJG.ANIO "
				+ "AND ESTADO.NUMERO = EJG.NUMERO "
				+ "AND ESTADO.FECHABAJA IS NULL "
				+ "AND ESTADO.idestadoporejg = (SELECT MAX(idestadoporejg) FROM SCS_ESTADOEJG ESTADO2 WHERE (ESTADO.IDINSTITUCION = ESTADO2.IDINSTITUCION "
				+ "AND ESTADO.IDTIPOEJG = ESTADO2.IDTIPOEJG "
				+ "AND ESTADO.ANIO = ESTADO2.ANIO "
				+ "AND ESTADO.NUMERO = ESTADO2.NUMERO "
				+ "AND ESTADO2.FECHABAJA IS NULL) "
				+ "AND ESTADO2.FECHAINICIO = (SELECT MAX(FECHAINICIO) FROM SCS_ESTADOEJG ESTADO3 WHERE (ESTADO3.IDINSTITUCION = ESTADO2.IDINSTITUCION "
				+ "AND ESTADO.IDTIPOEJG = ESTADO3.IDTIPOEJG "
				+ "AND ESTADO.ANIO = ESTADO3.ANIO "
				+ "AND ESTADO.NUMERO = ESTADO3.NUMERO "
				+ "AND ESTADO3.FECHABAJA IS NULL))))");
		sql.INNER_JOIN("SCS_MAESTROESTADOSEJG MAESTROESTADO ON ESTADO.IDESTADOEJG = MAESTROESTADO.IDESTADOEJG");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS REC ON REC.IDRECURSO = MAESTROESTADO.DESCRIPCION AND REC.IDLENGUAJE = 1");
		
		//where
		sql.WHERE("ejg.idinstitucion = " + idInstitucion);
		if(ejgItem.getAnnio() != null && ejgItem.getAnnio() != "")
                 sql.WHERE("ejg.anio =" + ejgItem.getAnnio());
		if(ejgItem.getNumero() != null && ejgItem.getNumero() != "")
                 sql.WHERE ("EJG.NUMEJG ="+ ejgItem.getNumero());
		if(ejgItem.getTipoEJG() != null && ejgItem.getTipoEJG() != "")
                 sql.WHERE ("ejg.IDTIPOEJG = " + ejgItem.getTipoEJG());
		if(ejgItem.getTipoEJGColegio() != null && ejgItem.getTipoEJGColegio() != "")
                 sql.WHERE ("ejg.idtipoejgcolegio = " + ejgItem.getTipoEJGColegio());
		if(ejgItem.getCreadoDesde() != null && ejgItem.getCreadoDesde() != "")
                 sql.WHERE ("ejg.origenapertura = '" + ejgItem.getCreadoDesde() +"'");
		if(ejgItem.getProcedimiento() != null && ejgItem.getProcedimiento() != "")
			     sql.WHERE ("regexp_like(EJG.NUMEROPROCEDIMIENTO,'" +  ejgItem.getProcedimiento()+"')");
        if(ejgItem.getEstadoEJG() != null && ejgItem.getEstadoEJG() != "")
                 sql.WHERE ("estado.idestadoejg =" + ejgItem.getEstadoEJG());
        if(ejgItem.getFechaAperturaDesd() != null)      	
                 sql.WHERE ("EJG.FECHAAPERTURA >= TO_DATE( '" + ejgItem.getFechaAperturaDesd() + "','DD/MM/RRRR')");
        if(ejgItem.getFechaAperturaHast() != null)   
        		 sql.WHERE ("EJG.FECHAAPERTURA <= TO_DATE( '" + ejgItem.getFechaAperturaHast() +"','DD/MM/RRRR')");
        if(ejgItem.getFechaEstadoDesd() != null)   
        		 sql.WHERE ("TO_CHAR(ESTADO.FECHAINICIO,'DD/MM/RRRR') >= TO_DATE( '" + ejgItem.getFechaEstadoDesd() + "','DD/MM/RRRR')");
        if(ejgItem.getFechaEstadoHast() != null)     
        		 sql.WHERE  ("TO_CHAR(ESTADO.FECHAINICIO,'DD/MM/RRRR') <= TO_DATE( '" + ejgItem.getFechaEstadoHast() + "','DD/MM/RRRR')");
        if(ejgItem.getFechaLimiteDesd() != null)   
        		 sql.WHERE  ("TO_CHAR(EJG.FECHALIMITEPRESENTACION,'DD/MM/RRRR') >= TO_DATE( '" + ejgItem.getFechaLimiteDesd() + "','DD/MM/RRRR')");
        if(ejgItem.getFechaLimiteHast() != null)   
        		 sql.WHERE  ("TO_CHAR(EJG.FECHALIMITEPRESENTACION,'DD/MM/RRRR') <= TO_DATE( '" + ejgItem.getFechaLimiteHast() + "','DD/MM/RRRR')");
        if(ejgItem.getDictamen() != null) {
        	for (String dictamen : ejgItem.getDictamen()) {
        		if(!dictamen.equals("-1")) {
            		dictamenCad += dictamen+",";
        		}else {indiferente = true;}
    		}
        	if(!indiferente) {
        		dictamenCad = dictamenCad.substring(0, (dictamenCad.length() -1));
                sql.WHERE ("EJG.IDTIPODICTAMENEJG IN (" + dictamenCad + ")");
        	}

        }
        if(ejgItem.getFundamentoCalif() != null && ejgItem.getFundamentoCalif() != "")
                 sql.WHERE ("EJG.IDFUNDAMENTOCALIF = " + ejgItem.getFundamentoCalif());
        if(ejgItem.getFechaDictamenDesd() != null)   
                 sql.WHERE  ("TO_CHAR(EJG.FECHADICTAMEN,'DD/MM/RRRR') >= TO_DATE( '"+ ejgItem.getFechaDictamenDesd() + "','DD/MM/RRRR')");
        if(ejgItem.getFechaDictamenHast() != null)   
        		 sql.WHERE  ("TO_CHAR(EJG.FECHADICTAMEN,'DD/MM/RRRR') <= TO_DATE( '"+ ejgItem.getFechaDictamenHast() + "','DD/MM/RRRR')");
        if(ejgItem.getResolucion() != null)   
                 sql.WHERE ("EJG.IDTIPORATIFICACIONEJG = " + ejgItem.getResolucion());
        if(ejgItem.getFundamentoJuridico() != null && ejgItem.getFundamentoJuridico() != "")
                 sql.WHERE ("EJG.IDFUNDAMENTOJURIDICO = " + ejgItem.getFundamentoJuridico());
        if(ejgItem.getFechaResolucionDesd() != null)   
                 sql.WHERE  ("TO_CHAR(EJG.FECHARESOLUCIONCAJG,'DD/MM/RRRR') >= TO_DATE( '"+ ejgItem.getFechaResolucionDesd() + "','DD/MM/RRRR')");
        if(ejgItem.getFechaResolucionHast() != null)       
        	 	 sql.WHERE  ("TO_CHAR(EJG.FECHARESOLUCIONCAJG,'DD/MM/RRRR') <= TO_DATE( '"+ ejgItem.getFechaResolucionHast() + "','DD/MM/RRRR')");
        if(ejgItem.getImpugnacion() != null && ejgItem.getImpugnacion() != "")
                 sql.WHERE ("EJG.IDTIPORESOLAUTO = " + ejgItem.getImpugnacion());
        if(ejgItem.getFundamentoImpuganacion() != null && ejgItem.getFundamentoImpuganacion() != "")
                 sql.WHERE ("EJG.IDTIPOSENTIDOAUTO = " + ejgItem.getFundamentoImpuganacion());
        if(ejgItem.getFechaImpugnacionDesd() != null)       
                 sql.WHERE  ("TO_CHAR(EJG.FECHAAUTO,'DD/MM/RRRR') >= TO_DATE( '"+ ejgItem.getFechaImpugnacionDesd() + "','DD/MM/RRRR')");
        if(ejgItem.getFechaImpugnacionHast() != null)         
        	 	 sql.WHERE  ("TO_CHAR(EJG.FECHAAUTO,'DD/MM/RRRR') <= TO_DATE( '"+ ejgItem.getFechaImpugnacionHast() + "','DD/MM/RRRR')");
        if(ejgItem.getJuzgado() != null && ejgItem.getJuzgado() != "")
                 sql.WHERE ("EJG.JUZGADO = " + ejgItem.getJuzgado());
        if(ejgItem.getNumAnnioProcedimiento() != null && ejgItem.getNumAnnioProcedimiento() != "")
                 sql.WHERE ("regexp_like(EJG.NUMEROPROCEDIMIENTO || EJG.ANIOPROCEDIMIENTO, '" + ejgItem.getNumAnnioProcedimiento() +"')");
        if(ejgItem.getAsunto() != null && ejgItem.getAsunto() != "")
        	 	 sql.WHERE ("regexp_like(EJG.OBSERVACIONES,'" + ejgItem.getAsunto() +"')");
        if(ejgItem.getNig() != null && ejgItem.getNig() != "")
                 sql.WHERE ("regexp_like(EJG.NIG,'" + ejgItem.getNig() +"'))");
        if(ejgItem.getPerceptivo() != null && ejgItem.getPerceptivo() != "")
                 sql.WHERE ("EJG.IDPRECEPTIVO = " + ejgItem.getPerceptivo());
        if(ejgItem.getCalidad() != null && ejgItem.getCalidad() != "")
                 sql.WHERE ("EJG.CALIDAD = '" + ejgItem.getCalidad() + "'");
        if(ejgItem.getRenuncia() != null && ejgItem.getRenuncia() != "")
                 sql.WHERE ("EJG.IDRENUNCIA = " + ejgItem.getRenuncia());
        if(ejgItem.getPonente() != null && ejgItem.getPonente() != "")
                 sql.WHERE ("EJG.IDPONENTE = " + ejgItem.getPonente());
        if(ejgItem.getFechaPonenteDesd() != null)   
                 sql.WHERE  ("TO_CHAR(EJG.FECHAPRESENTACIONPONENTE,'DD/MM/RRRR') >= TO_DATE( '"+ ejgItem.getFechaPonenteDesd() + "','DD/MM/RRRR')");
        if(ejgItem.getFechaPonenteHast() != null)     
        		 sql.WHERE  ("TO_CHAR(EJG.FECHAPRESENTACIONPONENTE,'DD/MM/RRRR') <= TO_DATE( '"+ ejgItem.getFechaPonenteHast() + "','DD/MM/RRRR')");
        if(ejgItem.getNumCAJG() != null && ejgItem.getNumCAJG() != "")
        		 sql.WHERE ("regexp_like(EJG.NUMERO_CAJG || EJG.ANIOCAJG,'" + ejgItem.getNumCAJG() +"')");
		sql.WHERE(condicionAnnioNumActas); 
		sql.WHERE(condicionNumRegRemesa);
        if(ejgItem.getAnnioCAJG() != null && ejgItem.getAnnioCAJG() != "")
   		 sql.WHERE ("EJG.aniocajg = " + ejgItem.getAnnioCAJG());
		//logica rol
		if(ejgItem.getRol() != null && ejgItem.getRol() != "") {
			if(ejgItem.getRol().equals("1")) {
				if(ejgItem.getNif() != null && ejgItem.getNif() != "")
					sql.WHERE("perjg.NIF = '" + ejgItem.getNif() + "'");
				if(ejgItem.getApellidos() != null && ejgItem.getApellidos() != "") {
					String columna = "REPLACE(CONCAT(perjg.apellido1,perjg.apellido2), ' ', '')";
					String cadena = ejgItem.getApellidos().replaceAll("\\s+","");
					sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
				}
//					sql.WHERE("TRANSLATE(LOWER( REPLACE(CONCAT(apellido1,apellido2), ' ', '')),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('"+ ejgItem.getApellidos() +"'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')");
				if(ejgItem.getNombre() != null && ejgItem.getNombre() != "") {
					String columna = "perjg.NOMBRE";
					String cadena = ejgItem.getNombre();
					sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
//					sql.WHERE("TRANSLATE(LOWER(perjg.NOMBRE),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('"+ ejgItem.getNombre() +"'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')");
				}
				}
			else if(ejgItem.getRol().equals("4")) {
                sql.INNER_JOIN("scs_unidadfamiliarejg unidadFamiliar on unidadFamiliar.idinstitucion = ejg.idinstitucion and unidadFamiliar.idtipoejg = ejg.idtipoejg"); 
                sql.WHERE("unidadFamiliar.anio = ejg.anio and unidadFamiliar.numero = ejg.numero");
                sql.INNER_JOIN("scs_personajg perjgunidadfamiliar on perjgunidadfamiliar.idpersona = unidadFamiliar.idpersona AND perjgunidadfamiliar.IDINSTITUCION = unidadFamiliar.IDINSTITUCION");
                if(ejgItem.getNif() != null && ejgItem.getNif() != "")
                	sql.WHERE("perjgunidadfamiliar.NIF = " + ejgItem.getNif());
				if(ejgItem.getApellidos() != null && ejgItem.getApellidos() != "") {
					String columna = "REPLACE(CONCAT(perjgunidadfamiliar.apellido1,perjgunidadfamiliar.apellido2), ' ', '')";
					String cadena = ejgItem.getApellidos().replaceAll("\\s+","");
					sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
				}
//					sql.WHERE("TRANSLATE(LOWER( REPLACE(CONCAT(perjgunidadfamiliar.apellido1,perjgunidadfamiliar.apellido2), ' ', '')),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('"+ ejgItem.getApellidos() +"'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN'))");
				if(ejgItem.getNombre() != null && ejgItem.getNombre() != "") {
					String columna = "perjgunidadfamiliar.NOMBRE";
					String cadena = ejgItem.getNombre();
					sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
				}
//					sql.WHERE("TRANSLATE(LOWER(perjgunidadfamiliar.NOMBRE),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('"+ ejgItem.getNombre() +"'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN'))");
			}
			else if(ejgItem.getRol().equals("2")) {
				sql.INNER_JOIN("scs_contrariosejg contrario on contrario.idinstitucion = ejg.idinstitucion and contrario.idtipoejg = ejg.idtipoejg"); 
				sql.WHERE("contrario.anio = ejg.anio and contrario.numero = ejg.numero");
				sql.INNER_JOIN("scs_personajg perjgcontrario on perjgcontrario.idpersona = contrario.idpersona AND perjgcontrario.IDINSTITUCION = contrario.IDINSTITUCION");
				if(ejgItem.getNif() != null && ejgItem.getNif() != "")
					sql.WHERE("PERJCONTRARIO.NIF = '" + ejgItem.getNif() + "'");
				if(ejgItem.getApellidos() != null && ejgItem.getApellidos() != "") {
					String columna = "REPLACE(CONCAT(perjgcontrario.apellido1,perjgcontrario.apellido2), ' ', '')";
					String cadena = ejgItem.getApellidos().replaceAll("\\s+","");
					sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
				}
//					sql.WHERE("TRANSLATE(LOWER( REPLACE(CONCAT(perjgcontrario.apellido1,perjgcontrario.apellido2), ' ', '')),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('"+ ejgItem.getApellidos() +"'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN'))");
				if(ejgItem.getNombre() != null && ejgItem.getNombre() != "") {
					String columna = "perjgcontrario.NOMBRE";
					String cadena = ejgItem.getNombre();
					sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
				}
//					sql.WHERE("TRANSLATE(LOWER(perjgcontrario.NOMBRE),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('"+ ejgItem.getNombre() +"'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN'))");
			}
			else if(ejgItem.getRol().equals("3")) {
				sql.INNER_JOIN("scs_unidadfamiliarejg solicitante on solicitante.idinstitucion = ejg.idinstitucion and solicitante.idtipoejg = ejg.idtipoejg"); 
				sql.WHERE("solicitante.anio = ejg.anio and solicitante.numero = ejg.numero AND solicitante.solicitante = 1");
				sql.INNER_JOIN("scs_personajg perjgsolicitante on perjgsolicitante.idrepresentanteejg = solicitante.idpersona AND perjgsolicitante.IDINSTITUCION = unidadFamiliar.IDINSTITUCION");
				if(ejgItem.getNif() != null && ejgItem.getNif() != "")
					sql.WHERE("PERJUNIDADFAMILIAR.NIF = '" + ejgItem.getNif() + "'");
				if(ejgItem.getApellidos() != null && ejgItem.getApellidos() != "") {
					String columna = "REPLACE(CONCAT(perjgunidadfamiliar.apellido1,perjgunidadfamiliar.apellido2), ' ', '')";
					String cadena = ejgItem.getApellidos().replaceAll("\\s+","");
					sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
				}
//					sql.WHERE("(TRANSLATE(LOWER( REPLACE(CONCAT(perjgunidadfamiliar.apellido1,perjgunidadfamiliar.apellido2), ' ', '')),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('"+ ejgItem.getApellidos() +"'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN'))");
				if(ejgItem.getNombre() != null && ejgItem.getNombre() != "") {
					String columna = "perjgunidadfamiliar.NOMBRE";
					String cadena = ejgItem.getNombre();
					sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
				}
//					sql.WHERE("(TRANSLATE(LOWER(perjgunidadfamiliar.NOMBRE),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('"+ ejgItem.getNombre() +"'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN'))");
			}	    
		}else {
			if(ejgItem.getNif() != null && ejgItem.getNif() != "")
				sql.WHERE("perjg.NIF = '" + ejgItem.getNif() + "'");
			if(ejgItem.getApellidos() != null && ejgItem.getApellidos() != "") {
				String columna = "REPLACE(CONCAT(perjg.apellido1,perjg.apellido2), ' ', '')";
				String cadena = ejgItem.getApellidos().replaceAll("\\s+","");
				sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
			}
//				sql.WHERE("TRANSLATE(LOWER( REPLACE(CONCAT(apellido1,apellido2), ' ', '')),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('"+ ejgItem.getApellidos() +"'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')");
			if(ejgItem.getNombre() != null && ejgItem.getNombre() != "") {
				String columna = "perjg.NOMBRE";
				String cadena = ejgItem.getNombre();
				sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
			}
//				sql.WHERE("TRANSLATE(LOWER(perjg.NOMBRE),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('"+ ejgItem.getNombre() +"'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')");
		}
		if(ejgItem.getTipoLetrado() != null && ejgItem.getTipoLetrado() != "") {
			if(ejgItem.getNumColegiado() != null && ejgItem.getNumColegiado() != "") {
				if(ejgItem.getTipoLetrado().equals("E")) {
					//letrado tramitador
					if(ejgItem.getIdPersona() != null && ejgItem.getIdPersona() != "")
						sql.WHERE("PER.IDPERSONA = " + ejgItem.getIdPersona());
					if(ejgItem.getTurno() != null && ejgItem.getTurno() != "")
						sql.WHERE("EJG.GUARDIATURNO_IDTURNO = " + ejgItem.getTurno());   
					if(ejgItem.getGuardia() != null && ejgItem.getGuardia() != "")
						sql.WHERE("EJG.GUARDIATURNO_IDGUARDIA " + ejgItem.getGuardia());
				}else if(ejgItem.getTipoLetrado().equals("D")){
					//letrado designas
					sql.INNER_JOIN("SCS_EJGDESIGNA EJGDESIGNA ON EJGDESIGNA.IDINSTITUCION = EJG.idinstitucion and EJGDESIGNA.idtipoejg = ejg.idtipoejg and EJGDESIGNA.ANIOEJG = ejg.anio and EJGDESIGNA.numeroejg = ejg.numero"); 
					sql.INNER_JOIN("SCS_DESIGNA DESIGNA ON EJGDESIGNA.IDINSTITUCION = DESIGNA.idinstitucion and EJGDESIGNA.IDTURNO = DESIGNA.IDTURNO and EJGDESIGNA.ANIO = DESIGNA.ANIO and EJGDESIGNA.numero = DESIGNA.NUMERO");
					sql.INNER_JOIN("SCS_DESIGNASLETRADO DESIGNALETRADO ON DESIGNALETRADO.idinstitucion = DESIGNA.idinstitucion  and DESIGNALETRADO.idturno = DESIGNA.idturno and DESIGNALETRADO.anio = DESIGNA.anio and DESIGNALETRADO.numero = DESIGNA.numero");
					if(ejgItem.getIdPersona() != null && ejgItem.getIdPersona() != "")
						sql.WHERE("DESIGNALETRADO.IDPERSONA = " + ejgItem.getIdPersona());
					if(ejgItem.getTurno() != null && ejgItem.getTurno() != "")
						sql.WHERE("DESIGNA.IDTURNO = " + ejgItem.getTurno());
				}else if(ejgItem.getTipoLetrado().equals("A")){
					//letrado asistencias
					sql.INNER_JOIN("SCS_ASISTENCIA ASISTENCIA ON ASISTENCIA.IDINSTITUCION = EJG.idinstitucion and ASISTENCIA.EJGIDTIPOEJG = ejg.idtipoejg and ASISTENCIA.EJGANIO = ejg.anio and ASISTENCIA.ejgnumero = ejg.numero");
					if(ejgItem.getIdPersona() != null && ejgItem.getIdPersona() != "")
						sql.WHERE("ASISTENCIA.IDPERSONACOLEGIADO = " + ejgItem.getIdPersona());
					if(ejgItem.getTurno() != null && ejgItem.getTurno() != "")
						sql.WHERE("ASISTENCIA.IDTURNO = " + ejgItem.getTurno());
					if(ejgItem.getGuardia() != null && ejgItem.getGuardia() != "")
						sql.WHERE("ASISTENCIA.IDGUARDIA = " + ejgItem.getGuardia());
				}
			}else {
				if(ejgItem.getTipoLetrado().equals("E")) {
					//letrado tramitador
					if(ejgItem.getTurno() != null && ejgItem.getTurno() != "")
						sql.WHERE("EJG.GUARDIATURNO_IDTURNO = " + ejgItem.getTurno());
					if(ejgItem.getGuardia() != null && ejgItem.getGuardia() != "")
						sql.WHERE("EJG.GUARDIATURNO_IDGUARDIA = " + ejgItem.getGuardia());                  
				}else if(ejgItem.getTipoLetrado().equals("D")){
					//letrado designas
					sql.INNER_JOIN("SCS_EJGDESIGNA EJGDESIGNA ON EJGDESIGNA.IDINSTITUCION = EJG.idinstitucion and EJGDESIGNA.idtipoejg = ejg.idtipoejg and EJGDESIGNA.ANIOEJG = ejg.anio and EJGDESIGNA.numeroejg = ejg.numero"); 
					if(ejgItem.getTurno() != null && ejgItem.getTurno() != "")
						sql.WHERE("EJGDESIGNA.IDTURNO = " + ejgItem.getTurno());           
				}else if(ejgItem.getTipoLetrado().equals("A")){
					//letrado asistencias
					sql.INNER_JOIN("SCS_ASISTENCIA ASISTENCIA ON ASISTENCIA.IDINSTITUCION = EJG.idinstitucion and ASISTENCIA.EJGIDTIPOEJG = ejg.idtipoejg and ASISTENCIA.EJGANIO = ejg.anio and ASISTENCIA.ejgnumero = ejg.numero");
					if(ejgItem.getTurno() != null && ejgItem.getTurno() != "")
						sql.WHERE("ASISTENCIA.IDTURNO = " + ejgItem.getTurno());
					if(ejgItem.getGuardia() != null && ejgItem.getGuardia() != "")
						sql.WHERE("ASISTENCIA.IDGUARDIA = " + ejgItem.getGuardia());   
				}
			}
		}else {
			if(ejgItem.getIdPersona() != null && ejgItem.getIdPersona() != "")
				sql.WHERE("PER.IDPERSONA = " + ejgItem.getIdPersona());
			if(ejgItem.getTurno() != null && ejgItem.getTurno() != "")
				sql.WHERE("EJG.GUARDIATURNO_IDTURNO = " + ejgItem.getTurno());
			if(ejgItem.getGuardia() != null && ejgItem.getGuardia() != "")
				sql.WHERE("EJG.GUARDIATURNO_IDGUARDIA = " + ejgItem.getGuardia());
		}

		sql.WHERE("rownum < 1000");
		//order
		sql.ORDER_BY("anio DESC, to_number(numejg) DESC");
		return sql.toString();	
	}
	public String datosEJG(EjgItem ejgItem, String idInstitucion) {
		SQL sql = new SQL();
		String joinDesignaLetrado = "(select * from SCS_DESIGNASLETRADO designaletrado where designaletrado.fecharenuncia is null or"
				+ " designaletrado.Fechadesigna = (SELECT MAX(LET2.Fechadesigna)"
				+ " FROM SCS_DESIGNASLETRADO LET2"
				+ " WHERE designaletrado.IDINSTITUCION = LET2.IDINSTITUCION"
				+ " AND designaletrado.IDTURNO = LET2.IDTURNO"
				+ " AND designaletrado.ANIO = LET2.ANIO"
				+ " AND designaletrado.NUMERO = LET2.NUMERO"
				+ " AND TRUNC(LET2.Fechadesigna) <= TRUNC(SYSDATE))"
				+ ") designaletrado2 on (designaletrado2.idinstitucion = ejgd.idinstitucion and designaletrado2.idturno = ejgd.idturno  and designaletrado2.anio = ejgd.anioejg and designaletrado2.numero = EJGD.NUMERODESIGNA)";

		//select
		sql.SELECT("ejg.anio");
		sql.SELECT("ejg.idinstitucion");
		sql.SELECT("ejg.idtipoejg");
		sql.SELECT("ejg.numero");
		sql.SELECT("ejg.numejg numejg"); 
		sql.SELECT("'E' || EJG.ANIO || '/' || EJG.NUMEJG AS NUMANIO");
		sql.SELECT("TURNO.NOMBRE || '/' || GUARDIA.NOMBRE AS TURNO");
		sql.SELECT("TURNO.ABREVIATURA AS TURNODES");
		sql.SELECT("ejg.fechaapertura");
		sql.SELECT("ejg.fechapresentacion");
		sql.SELECT("ejg.idtipoejgcolegio");
		sql.SELECT("ejg.fechalimitepresentacion");
		sql.SELECT("ejg.fechamodificacion");
		sql.SELECT("per.apellidos1 || ' ' || per.apellidos2 || ', ' || per.nombre as nombreletrado");
		sql.SELECT("REC.DESCRIPCION AS ESTADOEJG");
		sql.SELECT("perjg.apellido1 || ' ' || perjg.apellido2 || ', ' || perjg.nombre as NOMBRESOLICITANTE");
		sql.SELECT("EJG.NUMEROPROCEDIMIENTO");
		sql.SELECT("rectipodictamen.descripcion AS dictamen");
		sql.SELECT("rectiporesolucion.descripcion AS resolucion");
		sql.SELECT("rectiporesolauto.descripcion AS resolauto");
		sql.SELECT("personadesigna.apellidos1 || ' ' || personadesigna.apellidos2 || ', ' || personadesigna.nombre AS nombreletradodesigna");
		sql.SELECT("EXPEDIENTE.anioexpediente");
		sql.SELECT("EXPEDIENTE.numeroexpediente");
		sql.SELECT("EXPEDIENTE.IDTIPOEXPEDIENTE");
		//from
		sql.FROM("scs_ejg ejg");
		//joins
		sql.LEFT_OUTER_JOIN("cen_persona per on per.idpersona = ejg.idpersona");
		sql.LEFT_OUTER_JOIN("EXP_EXPEDIENTE EXPEDIENTE ON EJG.IDINSTITUCION = EXPEDIENTE.IDINSTITUCION AND EJG.ANIO = EXPEDIENTE.ANIOEJG  \r\n" + 
				"			            AND EJG.NUMERO = EXPEDIENTE.NUMEROEJG AND EJG.IDTIPOEJG = EXPEDIENTE.IDTIPOEJG");
		sql.LEFT_OUTER_JOIN("scs_personajg perjg on perjg.idpersona = ejg.idpersonajg AND perjg.IDINSTITUCION = EJG.IDINSTITUCION");
		sql.LEFT_OUTER_JOIN("SCS_TURNO  TURNO ON TURNO.IDINSTITUCION =EJG.IDINSTITUCION AND TURNO.IDTURNO =EJG.GUARDIATURNO_IDTURNO");
		sql.LEFT_OUTER_JOIN("SCS_GUARDIASTURNO GUARDIA  ON GUARDIA.IDINSTITUCION =EJG.IDINSTITUCION AND GUARDIA.IDTURNO =EJG.GUARDIATURNO_IDTURNO  AND GUARDIA.IDGUARDIA =EJG.GUARDIATURNO_IDGUARDIA");
		sql.INNER_JOIN("SCS_ESTADOEJG ESTADO ON (ESTADO.IDINSTITUCION = EJG.IDINSTITUCION "
				+ "AND ESTADO.IDTIPOEJG = EJG.IDTIPOEJG AND ESTADO.IDTIPOEJG = EJG.IDTIPOEJG "
				+ "AND ESTADO.ANIO = EJG.ANIO "
				+ "AND ESTADO.NUMERO = EJG.NUMERO "
				+ "AND ESTADO.FECHABAJA IS NULL "
				+ "AND ESTADO.idestadoporejg = (SELECT MAX(idestadoporejg) FROM SCS_ESTADOEJG ESTADO2 WHERE (ESTADO.IDINSTITUCION = ESTADO2.IDINSTITUCION "
				+ "AND ESTADO.IDTIPOEJG = ESTADO2.IDTIPOEJG "
				+ "AND ESTADO.ANIO = ESTADO2.ANIO "
				+ "AND ESTADO.NUMERO = ESTADO2.NUMERO "
				+ "AND ESTADO2.FECHABAJA IS NULL) "
				+ "AND ESTADO2.FECHAINICIO = (SELECT MAX(FECHAINICIO) FROM SCS_ESTADOEJG ESTADO3 WHERE (ESTADO3.IDINSTITUCION = ESTADO2.IDINSTITUCION "
				+ "AND ESTADO.IDTIPOEJG = ESTADO3.IDTIPOEJG "
				+ "AND ESTADO.ANIO = ESTADO3.ANIO "
				+ "AND ESTADO.NUMERO = ESTADO3.NUMERO "
				+ "AND ESTADO3.FECHABAJA IS NULL))))");
				sql.INNER_JOIN("SCS_MAESTROESTADOSEJG MAESTROESTADO ON ESTADO.IDESTADOEJG = MAESTROESTADO.IDESTADOEJG");
				sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS REC ON REC.IDRECURSO = MAESTROESTADO.DESCRIPCION AND REC.IDLENGUAJE = 1");
				sql.LEFT_OUTER_JOIN("scs_tipodictamenejg tipodictamen ON tipodictamen.idtipodictamenejg = ejg.idtipodictamenejg AND ejg.idinstitucion = tipodictamen.idinstitucion");
				sql.LEFT_OUTER_JOIN("gen_recursos_catalogos rectipodictamen ON rectipodictamen.idrecurso = tipodictamen.descripcion AND rectipodictamen.idlenguaje = 1");
				sql.LEFT_OUTER_JOIN("scs_tiporesolucion tiporesolucion ON tiporesolucion.idtiporesolucion = ejg.idtiporatificacionejg");
				sql.LEFT_OUTER_JOIN("gen_recursos_catalogos rectiporesolucion ON rectiporesolucion.idrecurso = tiporesolucion.descripcion AND rectiporesolucion.idlenguaje = 1");
				sql.LEFT_OUTER_JOIN("scs_tiporesolauto tiporesolauto ON tiporesolauto.idtiporesolauto = ejg.idtiporesolauto");
				sql.LEFT_OUTER_JOIN("gen_recursos_catalogos rectiporesolauto ON rectiporesolauto.idrecurso = tiporesolauto.descripcion AND rectiporesolauto.idlenguaje = 1");
				sql.LEFT_OUTER_JOIN("SCS_EJGDESIGNA EJGD ON   ejgd.anioejg = ejg.anio   AND   ejgd.numeroejg = ejg.numero AND   ejgd.idtipoejg = ejg.idtipoejg  AND   ejgd.idinstitucion = ejg.idinstitucion");
				sql.LEFT_OUTER_JOIN(joinDesignaLetrado);
				sql.LEFT_OUTER_JOIN("CEN_CLIENTE clientedesigna on clientedesigna.idpersona = designaletrado2.idpersona  and clientedesigna.idinstitucion = designaletrado2.idinstitucion");
				sql.LEFT_OUTER_JOIN("cen_persona personadesigna on clientedesigna.idpersona = personadesigna.idpersona");
				//where
				sql.WHERE("ejg.idinstitucion = " + idInstitucion);
				if(ejgItem.getAnnio() != null && ejgItem.getAnnio() != "")
		                 sql.WHERE("ejg.anio =" + ejgItem.getAnnio());
				if(ejgItem.getNumero() != null && ejgItem.getNumero() != "")
		                 sql.WHERE ("EJG.NUMEJG ="+ ejgItem.getNumero());
				if(ejgItem.getTipoEJG() != null && ejgItem.getTipoEJG() != "")
		                 sql.WHERE ("ejg.IDTIPOEJG = " + ejgItem.getTipoEJG());
				
				sql.ORDER_BY("anio DESC, to_number(numejg) DESC");
				return sql.toString();
	}
	public String comboCreadoDesde(String idLenguaje, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("ejg.ORIGENAPERTURA");
		sql.FROM("scs_ejg ejg");
		sql.WHERE("ejg.idinstitucion =" + idInstitucion);
		sql.ORDER_BY("ORIGENAPERTURA");
		return sql.toString();
	}
}
