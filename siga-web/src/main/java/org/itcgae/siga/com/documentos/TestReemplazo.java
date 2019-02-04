package org.itcgae.siga.com.documentos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.itcgae.siga.DTOs.com.CampoDinamicoItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.services.com.mappers.ConConsultasExtendsMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class TestReemplazo {

	@Autowired
	private ConConsultasExtendsMapper _conConsultasExtendsMapper;
	
	public static void main(String[] args) throws Exception {
		
		String idInstitucion = "2005";
		
		String sentencia = "<SELECT>select f_siga_calculoncolegiado( cv.idinstitucion, cv.idpersona) as \"N_COLEGIADO\",per.nombre as \"NOMBRE\",per.apellidos1 || ' ' || per.apellidos2 as \"APELLIDOS\",f_siga_getdireccioncliente( cv.idinstitucion, cv.idpersona, 2, 16) as \"EMAIL DESPACHO\" </SELECT><FROM>from cen_datoscv cv, cen_persona per</FROM><WHERE>where cv.idpersona = per.idpersona and cv.idinstitucion = %%IDINSTITUCION%% and cv.idtipocv = 1 and cv.fechainicio as \"FECHA INICIO\" %%=%% %%fecha%% and cv.idtipocvsubtipo1 as \"CURSO\" %%=%% %%MULTIVALOR@select idtipocvsubtipo1 as \"ID\", f_siga_getrecurso(descripcion, 1) as \"DESCRIPCION\" from cen_tiposcvsubtipo1 where idinstitucion = %%idinstitucion%% and idtipocv = 1 order by f_siga_getrecurso(descripcion, 1) asc%%</WHERE><GROUPBY></GROUPBY><ORDERBY></ORDERBY>";
		
		String selectExperta=sentencia.toUpperCase().replaceAll("\r\n"," ");
		String selectOriginal = sentencia.replaceAll("\r\n"," ");
		
		if ((selectExperta.indexOf(SigaConstants.ETIQUETATIPONUMERO))>=0 || (selectExperta.indexOf(SigaConstants.ETIQUETATIPOTEXTO))>=0 || (selectExperta.indexOf(SigaConstants.ETIQUETATIPOFECHA)>=0 ||(selectExperta.indexOf(SigaConstants.ETIQUETATIPOMULTIVALOR))>=0 ) ){
	  		
	  		if (selectExperta.toUpperCase().indexOf("%%IDINSTITUCION%%")>=0){
	  			selectExperta=selectExperta.toUpperCase().replaceAll("%%IDINSTITUCION%%",idInstitucion);
	  			selectOriginal=selectOriginal.replaceAll("%%IDINSTITUCION%%",idInstitucion);
	  			
			}
	  		
	  		AdmUsuarios usuario = new AdmUsuarios();
	  		usuario.setIdlenguaje("1");
	  		
	  		List<CampoDinamicoItem> campos = obtenerCriteriosCamposSalida(usuario, selectExperta, selectOriginal);
	  		System.out.println("Parametros encontrados");
	  		for(CampoDinamicoItem campo:campos){
	  			System.out.println("campo ::" + campo.getAlias() + " " + campo.getCampo() + " " + campo.getOperacion() + " " + campo.getTipoDato());
	  		}

	  	}
	}
	
	protected static List<CampoDinamicoItem> obtenerCriteriosCamposSalida(AdmUsuarios usuario, String selectExperta,String selectOriginal) throws Exception{
		
		List<CampoDinamicoItem> listaCamposDinamicos = new ArrayList<CampoDinamicoItem>();
		CampoDinamicoItem campoDinamico = null;
		
		List<String> tipoDatos = new ArrayList<String>();
		String campo="";
		String alias="";
		
		// Cargamos el vector de tipo de los tipos de datos de los criterios dinamicos
		tipoDatos.add(SigaConstants.ETIQUETATIPONUMERO);
		tipoDatos.add(SigaConstants.ETIQUETATIPOTEXTO);
		tipoDatos.add(SigaConstants.ETIQUETATIPOFECHA);
		tipoDatos.add(SigaConstants.ETIQUETATIPOMULTIVALOR);
		
		String sentencia_aux="";
		String sentenciaOriginalAux="";
		
		boolean continuar=true;
		List<String> ayuda = new ArrayList<String>();
		
		List<String> operadoresList = new ArrayList<String>();
		operadoresList.add("=");
		operadoresList.add("!=");
		operadoresList.add(">");
		operadoresList.add(">=");
		operadoresList.add("<");
		operadoresList.add("<=");
		operadoresList.add("IS NULL");
		operadoresList.add("LIKE");
		operadoresList.add("OPERADOR");
		
		try{	
			//Buscamos los criterios dinamicos que pueda haber en la sentencia select construida	 	
		 	String critCampoSalida=selectExperta;
		 	sentencia_aux=critCampoSalida;
		 	sentenciaOriginalAux = selectOriginal;
		 	continuar=true;
		 	
		 	for (int i=0;i<tipoDatos.size();i++){// Para cada tipo de datos
		 		continuar=true;
		 		sentencia_aux=critCampoSalida;
		 		sentenciaOriginalAux = selectOriginal;
		 		String operadorEncontrado = "";
		 		while ((continuar)&& (sentencia_aux.length()>0)){
		 			operadorEncontrado = "";
		 			//int pos_ini2 =sentencia_aux.lastIndexOf(v_tipoDatos.get(i).toString());
		 			int pos_ini=sentencia_aux.indexOf(tipoDatos.get(i).toString());
		 			if (pos_ini>=0){
		 				campoDinamico = new CampoDinamicoItem();
		 				
		 				String sentenciaA=sentencia_aux.substring(0,pos_ini);
		 				
		 				String operadores[] = sentenciaA.split("%%");
		 				for (int j = operadores.length-1; j >= 0 ; j--) {
							String operador = operadores[j];
							if(operadoresList.contains(operador)){
								operadorEncontrado = operador;
								break;
								
							}
								
						}
		 				 
		 				//--int pos_fin = sentencia_aux.indexOf("AND");
		 				String sentenciaAyuda=sentencia_aux.substring(pos_ini);
		 				int pos_fin = sentenciaAyuda.indexOf("AND");
		 				String sentenciaAyudaOriginal = null;
		 				if(pos_fin==-1)
		 					sentenciaAyudaOriginal = sentenciaOriginalAux.substring(pos_ini);
		 				else
		 					sentenciaAyudaOriginal = sentenciaOriginalAux.substring(pos_ini,pos_ini+pos_fin);
		 				
		 				campo=getAliasMostrar(sentenciaA);
		 				campoDinamico.setCampo(campo);		 				
		 				//listaCampos.add(campo);
		 				
		 				alias=getAliasCompleto(sentenciaA);
		 				campoDinamico.setAlias(alias);
		 				//listaAlias.add(alias);
		 				
		 				int posicionValue = sentenciaAyudaOriginal.toUpperCase().indexOf(" DEFECTO ");
				 		String valorDefecto = null;
				 		if (posicionValue>=0){
				 			String valueDefecto = sentenciaAyudaOriginal.substring(posicionValue);
				 			int inicio=valueDefecto.indexOf("\"");
				 			String auxiliar =valueDefecto.substring(inicio+1) ;
							int fin=auxiliar.indexOf("\"");
							String retorno=null;
							if(inicio!=-1 && fin!=-1){
								valorDefecto=auxiliar.substring(0,fin);
							}
					    }
				 		//listaValorDefecto.add(valorDefecto==null?"":valorDefecto);
				 		campoDinamico.setValorDefecto(valorDefecto==null?"":valorDefecto);
				 		
				 		int posicionNulo = sentenciaAyudaOriginal.toUpperCase().indexOf(" NULO ");
				 		String valorNulo = null;
				 		if (posicionNulo>=0){
				 			String valueNulo = sentenciaAyudaOriginal.substring(posicionNulo);
				 			int inicio=valueNulo.indexOf("\"");
				 			String auxiliar =valueNulo.substring(inicio+1) ;
							int fin=auxiliar.indexOf("\"");
							String retorno=null;
							if(inicio!=-1 && fin!=-1){
								valorNulo=auxiliar.substring(0,fin);
							}
					    }
				 		//listaValorNulo.add(valorNulo==null?Boolean.FALSE:valorNulo.toLowerCase().equals("si")?Boolean.TRUE:Boolean.FALSE);
				 		campoDinamico.setValorNulo(valorNulo==null?Boolean.FALSE:valorNulo.toLowerCase().equals("si")?Boolean.TRUE:Boolean.FALSE);
				 		
		 			 if (tipoDatos.get(i).toString().equals(SigaConstants.ETIQUETATIPONUMERO)){	
		 					 				
		 				//listaOperaciones.add(operadorEncontrado);
		 				 campoDinamico.setOperacion(operadorEncontrado);
		 				 
		  				//listaValores.add(null);
		 				campoDinamico.setValores(null);
		 				
		  				//tipoCampo.add("N");
		 				campoDinamico.setTipoDato("N");
		 				
		  				//ayuda.add("-1");
		 				campoDinamico.setAyuda("-1");
		 				
		 			 }else if(tipoDatos.get(i).toString().equals(SigaConstants.ETIQUETATIPOTEXTO)){		 				
		 				//listaOperaciones.add(operadorEncontrado);
		 				 campoDinamico.setOperacion(operadorEncontrado);
		 				 
		  				//listaValores.add(null);
		 				campoDinamico.setValores(null);
		 				
		  				//tipoCampo.add("A");
		 				campoDinamico.setTipoDato("A");
		 				
		  				//ayuda.add("-1");
		 				campoDinamico.setAyuda("-1");
		 			 }else if (tipoDatos.get(i).toString().equals(SigaConstants.ETIQUETATIPOFECHA)){
		 				
		 				//listaOperaciones.add(operadorEncontrado);
		 				 campoDinamico.setOperacion(operadorEncontrado);
		 				 
		  				//listaValores.add(null);
		 				campoDinamico.setValores(null);
		 				
		  				if(operadorEncontrado.equalsIgnoreCase("IS NULL")){
		  					//tipoCampo.add("X");
			 				campoDinamico.setTipoDato("X");
		  				}
		  				else{
		  					//tipoCampo.add("D");
			 				campoDinamico.setTipoDato("D");
		  				}
		  				
		  				//ayuda.add("-1");
		 				campoDinamico.setAyuda("-1");
		 				
		  				if(valorDefecto!=null && valorDefecto.equalsIgnoreCase("sysdate")){
		  					//listaValorDefecto.add(new Date().toString());
		  					campoDinamico.setValorDefecto(new Date().toString());
		  				}
		  				
		 			 }else if (tipoDatos.get(i).toString().equals(SigaConstants.ETIQUETATIPOMULTIVALOR)){
		 				
		 				//listaOperaciones.add(operadorEncontrado);
		 				campoDinamico.setOperacion(operadorEncontrado);
		 				
		 				//Obtenemos los resultados de la query multivalor

		 				String selectAyuda = obtenerSelectAyuda(sentenciaAyuda,usuario);
		 				//ayuda.add(selectAyuda+"%%");
		 				campoDinamico.setAyuda(selectAyuda+"%%");
		 				
		 				if (selectAyuda!=null && !selectAyuda.equals("")){
		 					/*RowsContainer rc2 = null;
		 					rc2 = new RowsContainer();
		 					rc2.query(selectAyuda);
		 					listaValores.add(rc2.getAll());
		 					tipoCampo.add("MV");*/
		 				}	 				
		 			
		 			 }
		 				
		 			 	listaCamposDinamicos.add(campoDinamico);
		 			}else{
		 				continuar=false;
		 			}
		 			
		 			sentencia_aux=sentencia_aux.substring(pos_ini+tipoDatos.get(i).toString().length());
		 			sentenciaOriginalAux=sentenciaOriginalAux.substring(pos_ini+tipoDatos.get(i).toString().length());
		 		}
		 		
		 		
		 	}
		 	
	
		 	return listaCamposDinamicos;
	
		} catch (Exception e) {
			throw e;
		}
	}
		
	
	protected static String getAliasMostrar(String sentencia) {
		String operador="";
		sentencia=sentencia.toUpperCase();
		int pos_AND=sentencia.lastIndexOf(" AND ");
		int pos_OR=sentencia.lastIndexOf(" OR ");
		int pos_WHERE=sentencia.lastIndexOf("WHERE");
		int posicion=-1;
		if (pos_AND <0 && pos_OR<0){
			posicion=sentencia.toUpperCase().lastIndexOf("WHERE");
			sentencia=sentencia.substring(posicion+"WHERE".length());
			if (sentencia.toUpperCase().lastIndexOf(" AS ")>=0){// Existe Alias
				int posAs=sentencia.toUpperCase().lastIndexOf(" AS ");
				int posEtiquetaOperador=sentencia.toUpperCase().indexOf(SigaConstants.ETIQUETAOPERADOR);
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%=%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%!=%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%>%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%>=%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%<%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%<=%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%IS NULL%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%LIKE%%");
				}

				sentencia=sentencia.substring(posAs+" AS ".length(),posEtiquetaOperador).replaceAll("\"","");



			}else{// no hay alias
				int posEtiquetaOperador=sentencia.toUpperCase().indexOf(SigaConstants.ETIQUETAOPERADOR);
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%=%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%!=%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%>%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%>=%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%<%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%<=%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%IS NULL%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%LIKE%%");
				}
				sentencia=sentencia.substring(0,posEtiquetaOperador);

				if (sentencia.indexOf(".")>=0){
					sentencia=sentencia.substring(sentencia.indexOf(".")+1);
				}


			}

		}else{ 
			if ((pos_AND>pos_OR)&& (pos_AND>pos_WHERE)){
				operador=" AND ";
				posicion=pos_AND;
			}else if ((pos_OR>pos_AND)&& (pos_OR>pos_WHERE)){
				operador=" OR ";
				posicion=pos_OR;
			}else if ((pos_WHERE>pos_AND)&& (pos_WHERE>pos_OR)){
				operador="WHERE";
				posicion=pos_WHERE;
			}
			sentencia=sentencia.substring(posicion+operador.length());

			if (sentencia.toUpperCase().lastIndexOf(" AS ")>=0){// Existe Alias
				int posAs=sentencia.toUpperCase().lastIndexOf(" AS ");
				int posEtiquetaOperador=sentencia.toUpperCase().indexOf(SigaConstants.ETIQUETAOPERADOR);
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%=%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%!=%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%>%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%>=%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%<%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%<=%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%IS NULL%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%LIKE%%");
				}


				sentencia=sentencia.substring(posAs+" AS ".length(),posEtiquetaOperador).replaceAll("\"","");



			}else{// no hay alias
				int posEtiquetaOperador=sentencia.toUpperCase().indexOf(SigaConstants.ETIQUETAOPERADOR);
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%=%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%!=%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%>%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%>=%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%<%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%<=%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%IS NULL%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%LIKE%%");
				}

				sentencia=sentencia.substring(0,posEtiquetaOperador);

				if (sentencia.indexOf(".")>=0){
					sentencia=sentencia.substring(sentencia.indexOf(".")+1);
				}

			}
		}	

		return sentencia;
	}
	
	protected static String getAliasCompleto(String sentencia){
		String operador="";
		int pos_AND=sentencia.lastIndexOf(" AND ");
		int pos_OR=sentencia.lastIndexOf(" OR ");
		int pos_WHERE=sentencia.lastIndexOf(" WHERE ");
		int posicion=-1;
		
		if (pos_AND <0 && pos_OR<0){
			posicion=sentencia.toUpperCase().lastIndexOf("WHERE");
			sentencia=sentencia.substring(posicion+"WHERE".length());
			if (sentencia.toUpperCase().lastIndexOf(" AS ")>=0){// Existe Alias
	            int posAs=sentencia.toUpperCase().lastIndexOf(" AS ");
	            int posEtiquetaOperador=sentencia.toUpperCase().indexOf(SigaConstants.ETIQUETAOPERADOR);
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%=%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%!=%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%>%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%>=%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%<%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%<=%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%IS NULL%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%LIKE%%");
				}

              
       
            sentencia=sentencia.substring(posAs,posEtiquetaOperador);
       
        
		  	
		    }else{// no hay alias
		  
		     /*sentencia=sentencia.substring(0,sentencia.toUpperCase().indexOf(ClsConstants.ETIQUETAOPERADOR));
			
		     if (sentencia.indexOf(".")>=0){
		  	  sentencia=sentencia.substring(sentencia.indexOf(".")+1);
		     }*/
		    	sentencia="-1";
		  
			
		   }
		}else{ 
			if ((pos_AND>pos_OR)&& (pos_AND>pos_WHERE)){
				operador=" AND ";
				posicion=pos_AND;
			  }else if ((pos_OR>pos_AND)&& (pos_OR>pos_WHERE)){
			  	operador=" OR ";
			  	posicion=pos_OR;
			  }else if ((pos_WHERE>pos_AND)&& (pos_WHERE>pos_OR)){
			  	operador="WHERE";
			  	posicion=pos_WHERE;
			  }
		    sentencia=sentencia.substring(posicion+operador.length());
			
			if (sentencia.toUpperCase().lastIndexOf(" AS ")>=0){// Existe Alias
	            int posAs=sentencia.toUpperCase().lastIndexOf(" AS ");
	            int posEtiquetaOperador=sentencia.toUpperCase().indexOf(SigaConstants.ETIQUETAOPERADOR);
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%=%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%!=%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%>%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%>=%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%<%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%<=%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%IS NULL%%");
				}
				if(posEtiquetaOperador==-1){
					posEtiquetaOperador=sentencia.toUpperCase().indexOf("%%LIKE%%");
				}

              
       
             sentencia=sentencia.substring(posAs,posEtiquetaOperador);
       
        
		  	
		    }else{
		    	sentencia="-1";
		    }
		  }	
		
	 return sentencia;
	}
	
	protected static String obtenerSelectAyuda(String select, AdmUsuarios usuario) throws SigaExceptions {
		select=select.toUpperCase().replaceAll(SigaConstants.ETIQUETAOPERADOR,"");
		String select1="";
		int posCritMulti;
		String selectCritMulti="";
		if(select.toUpperCase().indexOf(SigaConstants.ETIQUETATIPOMULTIVALOR)>=0){
			select1=select.substring(select.toUpperCase().indexOf(SigaConstants.ETIQUETATIPOMULTIVALOR));
			posCritMulti=SigaConstants.ETIQUETATIPOMULTIVALOR.length();
			selectCritMulti=select1.substring(posCritMulti);
		}else{		  
			selectCritMulti=select;
		}		
			
			if (selectCritMulti.toUpperCase().indexOf("%%")>=0){
				String selectCritMulti1="";
				selectCritMulti1=selectCritMulti.toUpperCase();
			    selectCritMulti1=selectCritMulti1.replaceAll("%%IDIOMA%%",usuario.getIdlenguaje());
			    selectCritMulti1=selectCritMulti1.replaceAll("@IDIOMA@",usuario.getIdlenguaje());
			    selectCritMulti1=selectCritMulti1.substring(0,selectCritMulti1.toUpperCase().indexOf("%%"));
			    
			    return selectCritMulti1;
			}else{
				return selectCritMulti;
				//eSTO ES QUE YA ESTAN RESUELTOS quitamos la excepcion
				//throw new SigaExceptions("Error al obtener los valores de l");
			}    
			    
	}

}
