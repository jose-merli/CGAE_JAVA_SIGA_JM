package org.itcgae.siga.db.services.scs.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableItem;
import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.JustificacionExpressItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.ScsDesignaSqlProvider;

public class ScsDesignacionesSqlExtendsProvider extends ScsDesignaSqlProvider {

	public String searchClaveDesignaciones(AsuntosJusticiableItem asuntosJusticiableItem, Integer tamMax) {
		SQL sql = new SQL();
		SQL sqlOrder = new SQL();

		sqlOrder.SELECT("*");
		sql.SELECT(
				"DESIGNA.idinstitucion, DESIGNA.anio,DESIGNA.numero,to_char(DESIGNA.idturno)  as clave, '' as rol, 'D' as tipo");
		sql.FROM("SCS_DESIGNA DESIGNA");
		// El Join con la tabla de scs_personaJG, solo realizará si nos viene informado
		// alguno de los datos del solicitante(Nif, nombre o apellidos).
		if (asuntosJusticiableItem.getIdPersonaColegiado() != null) {
			sql.INNER_JOIN(
					"SCS_DESIGNASLETRADO DESIGNALETRADO ON DESIGNALETRADO.idinstitucion = DESIGNA.idinstitucion  and DESIGNALETRADO.idturno = DESIGNA.idturno\r\n"
							+ "  and DESIGNALETRADO.anio = DESIGNA.anio and DESIGNALETRADO.numero = DESIGNA.numero");
		}
		sql.INNER_JOIN(
				"scs_defendidosdesigna DEFENDIDOSDESIGNA ON DEFENDIDOSDESIGNA.idinstitucion = DESIGNA.idinstitucion  and DEFENDIDOSDESIGNA.idturno = DESIGNA.idturno\r\n"
						+ "                            and DEFENDIDOSDESIGNA.anio = DESIGNA.anio and DEFENDIDOSDESIGNA.numero = DESIGNA.numero");
		sql.LEFT_OUTER_JOIN(
				"SCS_PERSONAJG PERSONA ON  DEFENDIDOSDESIGNA.IDPERSONA = PERSONA.IDPERSONA  AND DEFENDIDOSDESIGNA.IDINSTITUCION = PERSONA.IDINSTITUCION");
		sql.WHERE("DESIGNA.idinstitucion =" + asuntosJusticiableItem.getIdInstitucion());

		if (asuntosJusticiableItem.getAnio() != null && asuntosJusticiableItem.getAnio() != "") {
			sql.WHERE("DESIGNA.ANIO = " + asuntosJusticiableItem.getAnio());
		}
		if (asuntosJusticiableItem.getNumero() != null) {
			sql.WHERE("DESIGNA.NUMERO = " + asuntosJusticiableItem.getNumero());
		}
		if (asuntosJusticiableItem.getIdTurno() != null) {
			sql.WHERE("DESIGNA.IDTURNO = " + asuntosJusticiableItem.getIdTurno());
		}
		if (asuntosJusticiableItem.getIdGuardia() != null) {
			sql.WHERE("DESIGNA.IDGUARDIA = " + asuntosJusticiableItem.getIdGuardia());
		}
		if (asuntosJusticiableItem.getIdPersonaColegiado() != null) {
			sql.WHERE("DESIGNALETRADO.IDPERSONA = " + asuntosJusticiableItem.getIdPersonaColegiado());
		}
		if (asuntosJusticiableItem.getFechaAperturaDesde() != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String fecha = dateFormat.format(asuntosJusticiableItem.getFechaAperturaDesde());
			sql.WHERE("TO_CHAR(EJG.FECHAAPERTURA,'DD/MM/RRRR') >= TO_DATE('" + fecha + "','DD/MM/RRRR')");
		}
		if (asuntosJusticiableItem.getFechaAperturaHasta() != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String fecha = dateFormat.format(asuntosJusticiableItem.getFechaAperturaHasta());
			sql.WHERE("TO_CHAR(EJG.FECHAAPERTURA,'DD/MM/RRRR') <= TO_DATE('" + fecha + "','DD/MM/RRRR')");
		}

		if (asuntosJusticiableItem.getIdPersonaColegiado() != null) {
			if (asuntosJusticiableItem.getApellidos() != null)
				sql.WHERE(UtilidadesString.filtroTextoBusquedas("REPLACE(CONCAT(apellido1,apellido2), ' ', '')",
						asuntosJusticiableItem.getApellidos().replaceAll("\\s+", "")));
			if (asuntosJusticiableItem.getNombre() != null)
				sql.WHERE(UtilidadesString.filtroTextoBusquedas("NOMBRE", asuntosJusticiableItem.getNombre()));

		}
		if (asuntosJusticiableItem.getIdPersonaColegiado() != null) {

		sql.WHERE("( DESIGNALETRADO.fecharenuncia is null or\r\n" + "           DESIGNALETRADO.Fechadesigna =\r\n"
				+ "           (SELECT MAX(LET2.Fechadesigna)\r\n" + "               FROM SCS_DESIGNASLETRADO LET2\r\n"
				+ "              WHERE DESIGNALETRADO.IDINSTITUCION = LET2.IDINSTITUCION\r\n"
				+ "                AND DESIGNALETRADO.IDTURNO = LET2.IDTURNO\r\n"
				+ "                AND DESIGNALETRADO.ANIO = LET2.ANIO\r\n"
				+ "                AND DESIGNALETRADO.NUMERO = LET2.NUMERO\r\n"
				+ "                AND TRUNC(LET2.Fechadesigna) <= TRUNC(SYSDATE)))\r\n" + "");
		}
		if (asuntosJusticiableItem.getNif() != null && asuntosJusticiableItem.getNif() != "") {
            sql.WHERE("upper(PERSONA.NIF) like upper('%"+asuntosJusticiableItem.getNif()+"%')");
		}
		if (asuntosJusticiableItem.getIdTipoDesigna() != null) {
			sql.WHERE("DESIGNA.IDTIPODESIGNACOLEGIO = " + asuntosJusticiableItem.getIdTipoDesigna());
		}
		if (asuntosJusticiableItem.getIdEstadoDesigna() != null) {
			sql.WHERE("DESIGNA.ESTADO = '" + asuntosJusticiableItem.getIdEstadoDesigna()+"'");
		}
		if (asuntosJusticiableItem.getNumProcedimiento() != null && asuntosJusticiableItem.getNumProcedimiento() != "") {
			sql.WHERE("DESIGNA.NUMPROCEDIMIENTO   = '" + asuntosJusticiableItem.getNumProcedimiento()+"'");
		}
		// if(asuntosJusticiableItem.getNumeroDiligencia() != null) {
		// sql.WHERE("ASISTENCIA.NUMERODILIGENCIA =
		// "+asuntosJusticiableItem.getNumeroDiligencia());
		// }
		if (asuntosJusticiableItem.getNig() != null && asuntosJusticiableItem.getNig() != "") {
			sql.WHERE("DESIGNA.NIG   = '" + asuntosJusticiableItem.getNig()+"'");
		}
		if (asuntosJusticiableItem.getIdJuzgado() != null) {
			sql.WHERE("DESIGNA.IDJUZGADO  = " + asuntosJusticiableItem.getIdJuzgado());
		}

		sqlOrder.FROM("(" + sql + " )");
		if (tamMax != null) {
			Integer tamMaxNumber = tamMax + 1;
			sqlOrder.WHERE("rownum <= " + tamMaxNumber);
		}

		return sqlOrder.toString();
		// return sql.toString();
	}
	
	public String busquedaDesignaciones(DesignaItem designaItem, Integer tamMax) {
		SQL sql = new SQL();
		SQL sqlOrder = new SQL();

//		sqlOrder.SELECT("*");
//		sql.SELECT(
//				"DESIGNA.idinstitucion, DESIGNA.anio,DESIGNA.numero,to_char(DESIGNA.idturno)  as clave, '' as rol, 'D' as tipo");
//		sql.FROM("SCS_DESIGNA DESIGNA");
//		// El Join con la tabla de scs_personaJG, solo realizará si nos viene informado
//		// alguno de los datos del solicitante(Nif, nombre o apellidos).
//		if (asuntosJusticiableItem.getIdPersonaColegiado() != null) {
//			sql.INNER_JOIN(
//					"SCS_DESIGNASLETRADO DESIGNALETRADO ON DESIGNALETRADO.idinstitucion = DESIGNA.idinstitucion  and DESIGNALETRADO.idturno = DESIGNA.idturno\r\n"
//							+ "  and DESIGNALETRADO.anio = DESIGNA.anio and DESIGNALETRADO.numero = DESIGNA.numero");
//		}
//		sql.INNER_JOIN(
//				"scs_defendidosdesigna DEFENDIDOSDESIGNA ON DEFENDIDOSDESIGNA.idinstitucion = DESIGNA.idinstitucion  and DEFENDIDOSDESIGNA.idturno = DESIGNA.idturno\r\n"
//						+ "                            and DEFENDIDOSDESIGNA.anio = DESIGNA.anio and DEFENDIDOSDESIGNA.numero = DESIGNA.numero");
//		sql.LEFT_OUTER_JOIN(
//				"SCS_PERSONAJG PERSONA ON  DEFENDIDOSDESIGNA.IDPERSONA = PERSONA.IDPERSONA  AND DEFENDIDOSDESIGNA.IDINSTITUCION = PERSONA.IDINSTITUCION");
//		sql.WHERE("DESIGNA.idinstitucion =" + asuntosJusticiableItem.getIdInstitucion());

		sqlOrder.FROM("(" + sql + " )");
		if (tamMax != null) {
			Integer tamMaxNumber = tamMax + 1;
			sqlOrder.WHERE("rownum <= " + tamMaxNumber);
		}

		return sqlOrder.toString();
		// return sql.toString();
	}

	public String getAsuntoTipoDesigna(AsuntosClaveJusticiableItem asuntoClave, String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("DESIGNA.IDINSTITUCION");
		sql.SELECT("concat('D' || DESIGNA.anio || '/',lpad(DESIGNA.codigo,5,'0') ) as asunto");
		sql.SELECT("DESIGNA.FECHAENTRADA as fecha");
		sql.SELECT("DESIGNA.ANIO");
		sql.SELECT("DESIGNA.NUMERO");
		sql.SELECT("DESIGNA.codigo");
		sql.SELECT("turno.nombre as turnoguardia");
		sql.SELECT("'<b>Juzgado</b>: ' || juzgado.nombre as datosinteres");

		sql.FROM("SCS_DESIGNA DESIGNA");

		sql.LEFT_OUTER_JOIN("SCS_TURNO TURNO ON designa.idturno = turno.idturno and designa.idinstitucion = turno.idinstitucion");
		sql.LEFT_OUTER_JOIN("SCS_Juzgado juzgado ON juzgado.idjuzgado = DESIGNA.IDJUZGADO and DESIGNA.IDINSTITUCION = juzgado.IDINSTITUCION");
		
		sql.WHERE("DESIGNA.IDINSTITUCION = '" + asuntoClave.getIdInstitucion() + "'");
		sql.WHERE("DESIGNA.ANIO = '" + asuntoClave.getAnio() + "'");
		sql.WHERE("DESIGNA.NUMERO = '" + asuntoClave.getNumero() + "'");
		sql.WHERE("DESIGNA.idturno = '" + asuntoClave.getClave() + "'");
		
		return sql.toString();
	}

	public String busquedaJustificacionExpres(JustificacionExpressItem item, String idInstitucion, String longitudCodEJG, String idPersona) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" ");
		sql.append(" SELECT DECODE(ALLDESIGNAS.NUM_TIPO_RESOLUCION_DESIGNA,1,'FAVORABLE', 2,'NO_FAVORABLE', 3,'PTE_CAJG', 4, 'SIN_RESOLUCION','SIN_EJG') AS TIPO_RESOLUCION_DESIGNA, ");
		sql.append(" ALLDESIGNAS.* ");
		sql.append(" FROM ( ");
		
		sql.append(" SELECT TO_CHAR(D.FECHAENTRADA, 'dd/mm/yyyy') AS FECHADESIGNA, ");
		sql.append(" TO_CHAR(D.FECHAENTRADA, 'yyyy_mm_dd') AS FECHAORDEN, ");
		sql.append(" D.ART27 AS ART27, ");
		sql.append(" D.ANIO || '/' || D.CODIGO AS CODIGODESIGNA,");
		
		sql.append(" F_SIGA_GETEJG_DESIGNA("+idInstitucion+",d.idturno,d.anio,d.numero,"+longitudCodEJG+") AS EXPEDIENTES, ");
		
		sql.append(" DECODE(D.ANIOPROCEDIMIENTO,NULL,D.NUMPROCEDIMIENTO,D.NUMPROCEDIMIENTO||'/'||D.ANIOPROCEDIMIENTO) AS ASUNTO, ");
		
		sql.append(" f_siga_getdefendidosdesigna("+idInstitucion+",d.anio,d.idturno,d.numero,0) AS CLIENTE, ");
		
		sql.append(" D.RESUMENASUNTO AS RESUMENASUNTO, ");
		sql.append(" DL.FECHARENUNCIA, ");
		sql.append(" D.IDINSTITUCION, ");
		sql.append(" D.IDTURNO, ");
		sql.append(" D.ANIO, ");
		sql.append(" D.NUMERO, ");
		sql.append(" D.CODIGO, ");
		sql.append(" D.IDJUZGADO, ");
		sql.append(" D.IDINSTITUCION_JUZG, ");
		sql.append(" D.ESTADO, ");
		sql.append(" D.SUFIJO, ");
		sql.append(" D.FECHAENTRADA, ");
		sql.append(" DL.IDPERSONA, ");
		sql.append(" D.IDPROCEDIMIENTO, ");
		sql.append(" D.NUMPROCEDIMIENTO, ");
		sql.append(" D.ANIOPROCEDIMIENTO, ");
		sql.append(" D.NIG, ");
		
		sql.append(" (SELECT COUNT(*) FROM SCS_DESIGNASLETRADO SDL WHERE D.IDINSTITUCION = SDL.IDINSTITUCION AND D.ANIO = SDL.ANIO AND D.NUMERO = SDL.NUMERO AND D.IDTURNO = SDL.IDTURNO) AS CAMBIOLETRADO, ");
	               
		sql.append(" (SELECT MIN(CASE WHEN (EJG.FECHARESOLUCIONCAJG IS NOT NULL ");
		sql.append(" AND ((EJG.IDTIPORATIFICACIONEJG IN (3,5,6,7) ");
		sql.append(" AND EJG.IDTIPORESOLAUTO IS NOT NULL ");
		sql.append(" AND EJG.IDTIPORESOLAUTO IN (1)) ");
		sql.append(" OR (EJG.IDTIPORATIFICACIONEJG IN (1,2,8,9,10,11) ");
		sql.append(" AND (EJG.IDTIPORESOLAUTO IS NULL ");
		sql.append(" OR EJG.IDTIPORESOLAUTO NOT IN (3))))) THEN 1 "); 
	                           
		sql.append(" WHEN (EJG.FECHARESOLUCIONCAJG IS NOT NULL ");
		sql.append(" AND ((EJG.IDTIPORATIFICACIONEJG IN (1,2,8,9,10,11,0) ");
		sql.append(" AND EJG.IDTIPORESOLAUTO IS NOT NULL ");
		sql.append(" AND EJG.IDTIPORESOLAUTO IN (3)) ");
		sql.append(" OR (EJG.IDTIPORATIFICACIONEJG IN (3,5,6,7) ");
		sql.append(" AND (EJG.IDTIPORESOLAUTO IS NULL ");
		sql.append(" OR EJG.IDTIPORESOLAUTO NOT IN (1))))) THEN 2 "); 
	                           
		sql.append(" WHEN (EJG.FECHARESOLUCIONCAJG IS NOT NULL ");
		sql.append(" AND EJG.IDTIPORATIFICACIONEJG IN (4)) THEN  3 "); 
	                           
		sql.append(" ELSE 4 END) ");
	                
		sql.append(" FROM SCS_EJG EJG, ");
		sql.append(" SCS_EJGDESIGNA EJGDES ");
		
		sql.append(" WHERE EJGDES.IDINSTITUCION = EJG.IDINSTITUCION ");
		sql.append(" AND EJGDES.IDTIPOEJG = EJG.IDTIPOEJG ");
		sql.append(" AND EJGDES.ANIOEJG = EJG.ANIO ");
		sql.append(" AND EJGDES.NUMEROEJG = EJG.NUMERO ");
		sql.append(" AND D.IDINSTITUCION = EJGDES.IDINSTITUCION ");
		sql.append(" AND D.IDTURNO = EJGDES.IDTURNO ");
		sql.append(" AND D.ANIO = EJGDES.ANIODESIGNA ");
		sql.append(" AND D.NUMERO = EJGDES.NUMERODESIGNA) AS NUM_TIPO_RESOLUCION_DESIGNA ");
	        
		sql.append(" FROM SCS_DESIGNA D, ");
		sql.append(" SCS_DESIGNASLETRADO DL ");
	        
		sql.append(" WHERE D.IDINSTITUCION = DL.IDINSTITUCION ");
		sql.append(" AND D.ANIO = DL.ANIO ");
		sql.append(" AND D.NUMERO = DL.NUMERO ");
		sql.append(" AND D.IDTURNO = DL.IDTURNO ");
		
		sql.append(" AND D.IDINSTITUCION = "+idInstitucion);
		
		if (item.getAnioDesignacion()!=null && !item.getAnioDesignacion().trim().isEmpty()) {
			sql.append(" AND D.ANIO = "+item.getAnioDesignacion());
		}
		
		if (item.getNumDesignacion()!=null && !item.getNumDesignacion().isEmpty()) {
			//hay que distinguir entre - y , para la busqueda
			
			sql.append(" AND ");
			
//				if (ComodinBusquedas.hasComodin(sCodigoDesigna)) {
//					sql.append(ComodinBusquedas.prepararSentenciaCompletaBind(sCodigoDesigna, "D.CODIGO", contador, codigos));
//				
//				} else if (ComodinBusquedas.hasComa(sCodigoDesigna) || ComodinBusquedas.hasGuion(sCodigoDesigna)) {
//					ComodinBusquedas comodinBusquedas = new ComodinBusquedas();
//					sql.append(comodinBusquedas.prepararSentenciaCompletaEJGBind(sCodigoDesigna, "D.CODIGO", contador, codigos));
//					contador =  codigos.size();
//					
//				} else {
			sql.append(" LTRIM(D.CODIGO, '0')  = LTRIM('"+item.getNumDesignacion().trim()+"', '0') ");
		}	
		
		if ((item.getAnioEJG()!=null && !item.getAnioEJG().trim().isEmpty()) || (item.getNumEJG()!=null && !item.getNumEJG().trim().isEmpty())) {
			sql.append(" AND EXISTS ( ");
			sql.append(" SELECT 1 ");
			sql.append(" FROM SCS_EJG EJG, ");
			sql.append(" SCS_EJGDESIGNA EJGDES ");
			sql.append(" WHERE EJGDES.IDINSTITUCION = EJG.IDINSTITUCION ");
			sql.append(" AND EJGDES.IDTIPOEJG = EJG.IDTIPOEJG ");
			sql.append(" AND EJGDES.ANIOEJG = EJG.ANIO ");
			sql.append(" AND EJGDES.NUMEROEJG = EJG.NUMERO ");
			sql.append(" AND D.IDINSTITUCION = EJGDES.IDINSTITUCION ");
			sql.append(" AND D.IDTURNO = EJGDES.IDTURNO ");
			sql.append(" AND D.ANIO = EJGDES.ANIODESIGNA ");
			sql.append(" AND D.NUMERO = EJGDES.NUMERODESIGNA ");
			
			if (item.getAnioEJG()!=null && !item.getAnioEJG().trim().isEmpty()) {
				sql.append(" AND EJG.ANIO = "+item.getAnioEJG().trim());
			}
			
			if (item.getNumEJG()!=null && !item.getNumEJG().trim().isEmpty()){
				sql.append(" AND ");
				
				//hacer distincion entre - y ,
				
//				if (ComodinBusquedas.hasComodin(sCodigoEJG)) {
//					sql.append(ComodinBusquedas.prepararSentenciaCompletaBind(sCodigoEJG, "EJG.NUMEJG", contador, codigos));
//				
//				} else if (ComodinBusquedas.hasComa(sCodigoEJG) || ComodinBusquedas.hasGuion(sCodigoEJG)) {
//					ComodinBusquedas comodinBusquedas = new ComodinBusquedas();
//					sql.append(comodinBusquedas.prepararSentenciaCompletaEJGBind(sCodigoEJG, "EJG.NUMEJG", contador, codigos));
//					contador =  codigos.size();
//					
//				} else {
				sql.append(" LTRIM(EJG.NUMEJG, '0')  = LTRIM('"+item.getNumEJG()+"', '0') ");
			}			
		}

		if(item.getActuacionesValidadas()!=null && !item.getActuacionesValidadas().trim().isEmpty()){
			if("SINACTUACIONES".equalsIgnoreCase(item.getActuacionesValidadas().trim())){
				sql.append(" AND F_SIGA_ACTUACIONESDESIG(D.IDINSTITUCION,D.IDTURNO,D.ANIO,D.NUMERO) IS NULL ");
			}else{
			    sql.append(" AND UPPER(F_SIGA_ACTUACIONESDESIG(D.IDINSTITUCION,D.IDTURNO,D.ANIO,D.NUMERO))=UPPER('"+item.getActuacionesValidadas()+"')");
			}
		}
		
		if (item.getJustificacionDesde() != null || item.getJustificacionHasta() != null) {
			sql.append(" AND (SELECT COUNT(*) FROM SCS_ACTUACIONDESIGNA ACT");
			sql.append(" WHERE ACT.IDINSTITUCION = D.IDINSTITUCION AND ACT.ANIO = D.ANIO ");
			sql.append(" AND ACT.IDTURNO = D.IDTURNO AND ACT.NUMERO = D.NUMERO" );
			
			if (item.getJustificacionDesde() != null) {
				sql.append(" AND ACT.FECHAJUSTIFICACION >= '"+item.getJustificacionDesde()+"'");
			}

			if (item.getJustificacionHasta() != null) {
				sql.append(" AND ACT.FECHAJUSTIFICACION<= '"+item.getJustificacionHasta()+"'");
			}

			sql.append(" )>0");
		}
		
		//fechas designacion
		if (item.getDesignacionDesde() != null) {
			sql.append(" AND TRUNC(D.FECHAENTRADA) >= '"+item.getDesignacionDesde()+"'");
		}else{
			sql.append(" AND TRUNC(D.FECHAENTRADA) > '01/01/1950'");
		}
		
		if (item.getDesignacionHasta() != null){
			sql.append(" AND TRUNC(D.FECHAENTRADA) <='"+item.getDesignacionHasta()+"'");
		}
		
		//nombre y apellidos
		if (item.getApellidos() != null && !item.getApellidos().trim().isEmpty() && item.getNombre() != null && !item.getNombre().trim().isEmpty()){
			sql.append(" AND UPPER(f_siga_getdefendidosdesigna("+idInstitucion+",D.anio,D.idturno,D.numero,1) ) like ");
			sql.append("'%"+item.getNombre().trim().toUpperCase()+"%");
			sql.append(item.getApellidos().trim().toUpperCase()+"%'");

		}else if (item.getApellidos() != null && !item.getApellidos().trim().isEmpty() && (item.getNombre()==null || item.getNombre().trim().isEmpty())){
			sql.append(" and UPPER(f_siga_getdefendidosdesigna("+idInstitucion+",d.anio,d.idturno,d.numero,1) ) like ");
		    sql.append("'%"+item.getApellidos().trim().toUpperCase()+"%'");
		
		}else if ((item.getApellidos() == null || item.getApellidos().isEmpty()) && item.getNombre()!=null && !item.getNombre().trim().isEmpty()){
			sql.append(" AND UPPER(f_siga_getdefendidosdesigna("+idInstitucion+",D.anio,D.idturno,D.numero,1) ) like ");
		    sql.append("'%"+item.getNombre().trim().toUpperCase()+"%'");
		}
		
		//NCOLEGIADO
		if(idPersona!=null && !idPersona.isEmpty()){
			sql.append(" AND DL.IDPERSONA = "+idPersona);
		}

		if (item.getAnioDesignacion() != null && !item.getAnioDesignacion().trim().isEmpty()) {
			sql.append(" AND D.ANIO = "+item.getAnioDesignacion());	
		}

		if(item.isMuestraPendiente()) {
			sql.append(" AND D.ESTADO NOT IN ('A','F') ");
		}
		
		if(item.getEstado()!=null){
			sql.append(" AND D.ESTADO ='"+item.getEstado()+"'");
		}
		
		sql.append(" ) ALLDESIGNAS ");

		StringBuilder tiposResolucionBuilder = new StringBuilder();

		tiposResolucionBuilder.append(" WHERE (ALLDESIGNAS.NUM_TIPO_RESOLUCION_DESIGNA IN (1");
		
		if (!item.isRestriccionesVisualizacion() || (item.getResolucionPTECAJG() !=null && !"0".equals(item.getResolucionPTECAJG()))) {
			tiposResolucionBuilder.append(",3");
		}		
		
		if (!item.isRestriccionesVisualizacion() || (item.getConEJGNoFavorables() !=null && !"0".equals(item.getConEJGNoFavorables()))) {
			tiposResolucionBuilder.append(",2");
		}
		
		if (!item.isRestriccionesVisualizacion() || (item.getEjgSinResolucion() !=null && !"0".equals(item.getEjgSinResolucion()))){
			tiposResolucionBuilder.append(",4");
		}
		
		tiposResolucionBuilder.append(")");
		
		if (!item.isRestriccionesVisualizacion() || (item.getEjgSinResolucion() !=null && !"0".equals(item.getEjgSinResolucion()))){
			tiposResolucionBuilder.append(" OR ALLDESIGNAS.NUM_TIPO_RESOLUCION_DESIGNA is null ");
		}
		
		tiposResolucionBuilder.append(")");
		
		sql.append(tiposResolucionBuilder);
		
		if (item.isMuestraPendiente()) {
			sql.append(" AND (NOT EXISTS ");
			sql.append(" (SELECT * ");
			sql.append(" FROM SCS_ACTUACIONDESIGNA ACT ");
			sql.append(" WHERE ACT.IDINSTITUCION = ALLDESIGNAS.IDINSTITUCION ");
			sql.append(" AND ACT.IDTURNO = ALLDESIGNAS.IDTURNO ");
			sql.append(" AND ACT.ANIO = ALLDESIGNAS.ANIO ");
			sql.append(" AND ACT.NUMERO = ALLDESIGNAS.NUMERO) OR ");
			sql.append(" (SELECT COUNT(*) ");
			sql.append(" FROM SCS_ACREDITACIONPROCEDIMIENTO ACP ");
			sql.append(" WHERE EXISTS (SELECT * ");
			sql.append(" FROM SCS_ACTUACIONDESIGNA ACT ");
			sql.append(" WHERE ACT.IDINSTITUCION = ALLDESIGNAS.IDINSTITUCION ");
			sql.append(" AND ACT.IDTURNO = ALLDESIGNAS.IDTURNO ");
			sql.append(" AND ACT.ANIO = ALLDESIGNAS.ANIO ");
			sql.append(" AND ACT.NUMERO = ALLDESIGNAS.NUMERO ");
			sql.append(" AND ACT.IDINSTITUCION_PROC = ACP.IDINSTITUCION ");
			sql.append(" AND ACT.IDPROCEDIMIENTO = ACP.IDPROCEDIMIENTO) ");
			sql.append(" AND NOT EXISTS (SELECT * ");
			sql.append(" FROM SCS_ACTUACIONDESIGNA ACT ");
			sql.append(" WHERE ACT.IDINSTITUCION = ALLDESIGNAS.IDINSTITUCION ");
			sql.append(" AND ACT.IDTURNO = ALLDESIGNAS.IDTURNO ");
			sql.append(" AND ACT.ANIO = ALLDESIGNAS.ANIO ");
			sql.append(" AND ACT.NUMERO = ALLDESIGNAS.NUMERO ");
			sql.append(" AND ACT.IDINSTITUCION_PROC = ACP.IDINSTITUCION ");
			sql.append(" AND ACT.IDPROCEDIMIENTO = ACP.IDPROCEDIMIENTO ");
			sql.append(" AND ACT.IDACREDITACION = ACP.IDACREDITACION ");
			sql.append(" AND ACT.VALIDADA = '1'))>0 ");
			sql.append(" ) ");
		}
		
		return sql.toString();
	}
	public String comboModulos(Short idInstitucion){

		SQL sql = new SQL();
		sql.SELECT("MODULO.IDPROCEDIMIENTO, MODULO.NOMBRE ");
		sql.FROM("SCS_PROCEDIMIENTOS MODULO");
		sql.WHERE("MODULO.IDINSTITUCION = " + idInstitucion);

		return sql.toString();
	}
	
	public String comboProcedimientos(Short idInstitucion){

		SQL sql = new SQL();
		sql.SELECT("DISTINCT B.IDPROCEDIMIENTO, B.NOMBRE ");
		sql.FROM("SCS_PRETENSIONESPROCED A ");
		sql.INNER_JOIN("SCS_PROCEDIMIENTOS B ON A.IDPROCEDIMIENTO = B.IDPROCEDIMIENTO AND A.IDINSTITUCION = B.IDINSTITUCION ");
		sql.WHERE("A.IDINSTITUCION = " + idInstitucion);

		return sql.toString();
	}

}
