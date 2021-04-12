package org.itcgae.siga.db.services.scs.providers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Vector;

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
	
	public String busquedaDesignaciones(DesignaItem designaItem, Short idInstitucion, Integer tamMax) throws Exception {
		String sql = "";
		
		Hashtable codigosBind = new Hashtable();
		int contador=0;
		// Acceso a BBDD

		//aalg. INC_06694_SIGA. Se modifica la query para hacerla más eficiente
		try {
			sql=" select distinct procd.nombre as nombreprocedimiento, juzgado.nombre as nombrejuzgado,"
					+ " des.nig, des.numprocedimiento, des.estado estado, des.anio anio, des.numero numero,"
					+ " des.IDTIPODESIGNACOLEGIO, des.fechaalta fechaalta, des.fechaentrada fechaentrada,"
					+ "des.idturno idturno, des.codigo codigo, des.sufijo sufijo,des.idinstitucion idinstitucion,"
					+ " turno.nombre, des.fechaestado fechaestado, colegiado.ncolegiado, persona.nombre as nombrepersona,"
					+ " persona.APELLIDOS1 as apellido1persona,  persona.APELLIDOS2 as apellido2persona ";
			sql+=" from scs_designa des, CEN_COLEGIADO colegiado, cen_persona persona ";

			if(String.valueOf(designaItem.getNumColegiado()) !=null && !String.valueOf(designaItem.getNumColegiado()).equals("") ){
				sql += ", SCS_DESIGNASLETRADO l ";
			}

			if (designaItem.getIdCalidad() != null && designaItem.getIdCalidad().length > 0) {
				sql += ", SCS_DEFENDIDOSDESIGNA def ";
			}

			boolean tiene_juzg=designaItem.getNombreJuzgadoActu() != null && !designaItem.getNombreJuzgadoActu().equalsIgnoreCase("");
			boolean tiene_asunto=designaItem.getAsunto() != null && !designaItem.getAsunto().equalsIgnoreCase("");
			boolean tiene_acreditacion=designaItem.getAcreditacion() != null && !designaItem.getAcreditacion().equalsIgnoreCase("");
			boolean tiene_modulo=designaItem.getModulo() != null && !designaItem.getModulo().equalsIgnoreCase("");
			boolean tiene_fechaJustificacionDesde=designaItem.getFechaJustificacionDesde() != null && designaItem.getFechaJustificacionDesde().toString() != null && !designaItem.getFechaJustificacionDesde().toString().equalsIgnoreCase("");
			boolean tiene_fechaJustificacionHasta=designaItem.getFechaJustificacionHasta() != null && designaItem.getFechaJustificacionHasta().toString() != null && !designaItem.getFechaJustificacionHasta().toString().equalsIgnoreCase("");
			boolean tiene_origen=designaItem.getIdOrigen() != null && !designaItem.getIdOrigen().equalsIgnoreCase("");
			boolean tiene_actuacionesV = designaItem.getIdActuacionesV() != null && !designaItem.getIdActuacionesV().equalsIgnoreCase("");
			boolean tiene_moduloDesignacion =  (designaItem.getIdModulo() != null && designaItem.getIdModulo().length > 0);
			boolean tienePretensionesDesignacion = (designaItem.getIdProcedimiento() != null && designaItem.getIdProcedimiento().length > 0);
			
			if (tiene_juzg||tiene_asunto||tiene_acreditacion||tiene_modulo||tiene_fechaJustificacionDesde||tiene_fechaJustificacionHasta || tiene_origen || tiene_actuacionesV){
				sql+=	", scs_actuaciondesigna act ";
			}

			boolean tiene_interesado=false;
			if((designaItem.getNif() != null && !designaItem.getNif().equalsIgnoreCase(""))
					|| (designaItem.getNombreInteresado() != null && !designaItem.getNombreInteresado().equalsIgnoreCase(""))
					|| (designaItem.getApellidosInteresado() != null && !designaItem.getApellidosInteresado().equalsIgnoreCase(""))){
				tiene_interesado = true;
			}

			sql += ", scs_turno turno,  scs_juzgado juzgado, scs_pretensionesproced pret, scs_procedimientos procd";
			
			if (tiene_interesado){
				sql += ", SCS_DEFENDIDOSDESIGNA DED, SCS_PERSONAJG PER ";
			}
			
			if(tienePretensionesDesignacion) {
				sql += ", SCS_PRETENSION pret ";
			}

			if(idInstitucion != null) {
				sql+=" where des.idinstitucion = colegiado.idinstitucion and des.idinstitucion ="+idInstitucion + " and des.idturno = turno.idturno and des.idinstitucion = turno.idinstitucion ";
			}

			if(String.valueOf(designaItem.getNumColegiado()) !=null && !(String.valueOf(designaItem.getNumColegiado())).equals("") ){
				sql += " and l.idinstitucion =des.idinstitucion and persona.idpersona = colegiado.idpersona ";
				sql += " and des.idinstitucion = juzgado.idinstitucion and des.idjuzgado = juzgado.idjuzgado";
				sql += "  and procd.idinstitucion = des.idinstitucion and procd.idprocedimiento = des.idprocedimiento and pret.idinstitucion = procd.idinstitucion and procd.idprocedimiento = pret.idprocedimiento ";
				sql += " and l.idturno =des.idturno ";
				sql += " and l.anio =des.anio "; 
				sql += " and l.numero =des.numero ";
				sql += " and l.idpersona =colegiado.idpersona ";
				sql += " and l.idinstitucion =colegiado.idinstitucion ";
				sql += " and l.idinstitucion =des.idinstitucion ";
				sql += " and (l.Fechadesigna is null or";
				sql += " l.Fechadesigna = (SELECT MAX(LET2.Fechadesigna) FROM SCS_DESIGNASLETRADO LET2";
				sql += " WHERE l.IDINSTITUCION = LET2.IDINSTITUCION AND l.IDTURNO = LET2.IDTURNO";
				sql += " AND l.ANIO = LET2.ANIO AND l.NUMERO = LET2.NUMERO";
				sql += " AND TRUNC(LET2.Fechadesigna) <= TRUNC(SYSDATE)))";

				sql += " and colegiado.ncolegiado = " + String.valueOf(designaItem.getNumColegiado()) + " ";
			}

			if ( designaItem.getIdTurno() != null && (String.valueOf(designaItem.getIdTurno()) != "-1")&&!String.valueOf(designaItem.getIdTurno()).equals("")){
				if(designaItem.getIdTurno().length == 1) {
					sql += " AND des.idTurno = " + designaItem.getIdTurno()[0];
				}else {
					String turnoIN = "";
					for(int i = 0; i<designaItem.getIdTurno().length; i++) {
						String turno = designaItem.getIdTurno()[i];
						if(i == designaItem.getIdTurno().length-1) {
							turnoIN = turnoIN + turno;
						}else {
							turnoIN = turnoIN + turno +" ,";
						}
					}
					sql += " AND des.idTurno IN (" + turnoIN +" )";
				}
			}

			if (designaItem.getAno() != 0 && String.valueOf(designaItem.getAno()) != null && !String.valueOf(designaItem.getAno()).equalsIgnoreCase("")) {

				if (String.valueOf(designaItem.getAno()).indexOf('*') >= 0){

					contador++;
					sql += " AND " + prepararSentenciaCompletaBind(String.valueOf(designaItem.getAno()).trim(),"des.anio",contador,codigosBind );

				}    
				else if(designaItem.getAno() != 0) {
					contador++;
					codigosBind.put(new Integer(contador),String.valueOf(designaItem.getAno()).trim());
					sql += " AND des.anio = " + String.valueOf(designaItem.getAno());
				}	
			}

			if (designaItem.getCodigo() != null && !designaItem.getCodigo().equalsIgnoreCase("")) {

				if((designaItem.getCodigo().indexOf(',') != -1) && (designaItem.getCodigo().indexOf('-') == 1)) {
					String[] parts = designaItem.getCodigo().split(",");
					sql += " AND (des.codigo = ";
					for(int i = 0; i< parts.length; i++) {
						if(i == parts.length - 1) {
							sql += parts[i].trim() + ")"; 
						}
						sql += parts[i].trim() + " OR des.codigo = "; 
					}
				}else if((designaItem.getCodigo().indexOf('-') != -1) && (designaItem.getCodigo().indexOf(',') == -1)) {
					String[] parts = designaItem.getCodigo().split("-");
					if(parts.length == 2) {
						sql += " des.codigo IN ("+parts[0]+","+parts[1]+")";
					}
				}else if((designaItem.getCodigo().indexOf('-') == -1) && (designaItem.getCodigo().indexOf(',') == -1)){
					sql += " AND des.codigo = " + String.valueOf(designaItem.getCodigo()).trim() ; 
				}
			}
			if (designaItem.getIdJuzgado() != null && designaItem.getIdJuzgado().length > 0) {
				if(designaItem.getIdJuzgado().length == 1) {
					sql += " AND des.idjuzgado = " + designaItem.getIdJuzgado()[0];
				}else {
					String juzgadoIN = "";
					for(int i = 0; i<designaItem.getIdJuzgado().length; i++) {
						String juzgado = designaItem.getIdJuzgado()[i];
						if(i == designaItem.getIdJuzgado().length-1) {
							juzgadoIN = juzgadoIN + juzgado;
						}else {
							juzgadoIN = juzgadoIN + juzgado +" ,";
						}
					}
					sql += " AND act.idjuzgado IN (" + juzgadoIN +" )";
				}
			}
			if (designaItem.getAsunto() != null && !designaItem.getAsunto().equalsIgnoreCase("")) {
				contador++;
				codigosBind.put(new Integer(contador),designaItem.getAsunto().trim());
				sql += " AND des.resumenasunto = '" + designaItem.getAsunto()+"'" ;
			}
			
			if (designaItem.getIdModulo() != null && designaItem.getIdModulo().length > 0) {
				if(designaItem.getIdModulo().length == 1) {
					sql += " AND des.IDPROCEDIMIENTO = '" + designaItem.getIdModulo()[0]+"'";
				}else {
					String estadoIN = "";
					for(int i = 0; i<designaItem.getIdModulo().length; i++) {
						String estado = designaItem.getIdModulo()[i];
						if(i == designaItem.getIdModulo().length-1) {
							estadoIN = estadoIN + "'"+estado+"'";
						}else {
							estadoIN = estadoIN + "'"+estado+"'" +" ,";
						}
					}
					sql += " AND des.IDPROCEDIMIENTO IN (" + estadoIN +" )";
				}
				
			}
			
			if (designaItem.getIdModuloActuaciones() != null && designaItem.getIdModuloActuaciones().length > 0) {
				if(designaItem.getIdModuloActuaciones().length == 1) {
					sql += " AND act.IDPROCEDIMIENTO = '" + designaItem.getIdModuloActuaciones()[0]+"'";
				}else {
					String estadoIN = "";
					for(int i = 0; i<designaItem.getIdModuloActuaciones().length; i++) {
						String estado = designaItem.getIdModuloActuaciones()[i];
						if(i == designaItem.getIdModuloActuaciones().length-1) {
							estadoIN = estadoIN + "'"+estado+"'";
						}else {
							estadoIN = estadoIN + "'"+estado+"'" +" ,";
						}
					}
					sql += " AND act.IDPROCEDIMIENTO IN (" + estadoIN +" )";
				}
				
			}
			
			if (designaItem.getEstados() != null && designaItem.getEstados().length > 0) {
				if(designaItem.getEstados().length == 1) {
					sql += " AND des.estado = '" + designaItem.getEstados()[0]+"'";
				}else {
					String estadoIN = "";
					for(int i = 0; i<designaItem.getEstados().length; i++) {
						String estado = designaItem.getEstados()[i];
						if(i == designaItem.getEstados().length-1) {
							estadoIN = estadoIN + "'"+estado+"'";
						}else {
							estadoIN = estadoIN + "'"+estado+"'" +" ,";
						}
					}
					sql += " AND des.estado IN (" + estadoIN +" )";
				}
				
			}
			if (designaItem.getNumProcedimiento() != null && !designaItem.getNumProcedimiento().equalsIgnoreCase("")) {
				contador++;
				codigosBind.put(new Integer(contador),designaItem.getNumProcedimiento().trim());
				sql += " AND des.numprocedimiento = " + designaItem.getNumProcedimiento();
			}
			if (designaItem.getNig() != null && !designaItem.getNig().equalsIgnoreCase("")) {
				contador++;
				codigosBind.put(new Integer(contador),designaItem.getNig().trim());
				sql += " AND des.nig = '" + designaItem.getNig()+"'";
			}	
			if(tienePretensionesDesignacion) {
				if (designaItem.getIdProcedimiento() != null && designaItem.getIdProcedimiento().length > 0) {
					if(designaItem.getIdProcedimiento().length == 1) {
						sql += " AND pret.IDPRETENSION = '" + designaItem.getIdProcedimiento()[0]+"'";
					}else {
						String estadoIN = "";
						for(int i = 0; i<designaItem.getIdProcedimiento().length; i++) {
							String estado = designaItem.getIdProcedimiento()[i];
							if(i == designaItem.getIdProcedimiento().length-1) {
								estadoIN = estadoIN + "'"+estado+"'";
							}else {
								estadoIN = estadoIN + "'"+estado+"'" +" ,";
							}
						}
						sql += " AND pret.IDPRETENSION IN (" + estadoIN +" )";
					}

				}
			}
			
			// ACTUACIONES PENDIENTES
//			if (designaItem.getIdActuacionesV() != null && !designaItem.getIdActuacionesV().equalsIgnoreCase("")) {
//				if(designaItem.getIdActuacionesV().equalsIgnoreCase("SINACTUACIONES")){
//					sql += " and upper(SCS_ACTUACIONDESIGNA(des.idinstitucion,des.idturno,des.anio,des.numero)) is null";  // -----FALTA
//				}else if(designaItem.getIdActuacionesV().equalsIgnoreCase("SI")){
//					sql += " and act.VALIDADA = '1' ";
//				}else if(designaItem.getIdActuacionesV().equalsIgnoreCase("NO")){
//					sql += " and act.VALIDADA = '0' ";
//				}
//			}
			
			if(designaItem.getIdActuacionesV()!=null && !designaItem.getIdActuacionesV().trim().isEmpty()){
				if("SINACTUACIONES".equalsIgnoreCase(designaItem.getIdActuacionesV().trim())){
					sql += (" AND F_SIGA_ACTUACIONESDESIG(des.IDINSTITUCION,des.IDTURNO,des.ANIO,des.NUMERO) IS NULL ");
				}else{
				    sql += (" AND UPPER(F_SIGA_ACTUACIONESDESIG(des.IDINSTITUCION,des.IDTURNO,des.ANIO,des.NUMERO))=UPPER('"+designaItem.getIdActuacionesV()+"')");
				}
			}
			
			
			if (designaItem.getDocumentacionActuacion() != null && !designaItem.getDocumentacionActuacion().equalsIgnoreCase("")) {
				if(designaItem.getDocumentacionActuacion().equalsIgnoreCase("TODAS")){
					sql += " and act.DOCJUSTIFICACION IS NOT NULL ";
				}else if(designaItem.getDocumentacionActuacion().equalsIgnoreCase("ALGUNAS")){
					sql += " and act.DOCJUSTIFICACION = '1'";  // -----FALTA
				}else if(designaItem.getDocumentacionActuacion().equalsIgnoreCase("NINGUNA")){
					sql += " and act.DOCJUSTIFICACION IS NULL";
				}
			}

			//Mostrar ART 27
			String mostarArt27 = designaItem.getArt27() ;
			if (mostarArt27!= null && !mostarArt27.equalsIgnoreCase("") && !mostarArt27.equalsIgnoreCase("T")) {
				if(mostarArt27.equalsIgnoreCase("S")){
					sql += " AND des.art27 = 1";
				}else if(mostarArt27.equalsIgnoreCase("N")){
					sql += " AND des.art27 = 0";
				}
			}

			if (designaItem.getIdCalidad() != null && designaItem.getIdCalidad().length > 0) {
				if(designaItem.getIdCalidad().length == 1) {
					sql += " and def.ANIO = des.anio"+
							" and def.NUMERO = des.numero"+
							" and def.IDINSTITUCION = des.idinstitucion"+
							" and def.IDTURNO = des.idturno"+
							" and def.idtipoencalidad= " + designaItem.getIdCalidad()[0];
				}else {
					String calidadIN = "";
					for(int i = 0; i<designaItem.getIdCalidad().length; i++) {
						String calidad = designaItem.getIdCalidad()[i];
						if(i == designaItem.getIdCalidad().length-1) {
							calidadIN = calidadIN + "'"+calidad+"'";
						}else {
							calidadIN = calidadIN +calidad+" ,";
						}
					}
					sql +=" and def.ANIO = des.anio"+
							" and def.NUMERO = des.numero"+
							" and def.IDINSTITUCION = des.idinstitucion"+
							" and def.IDTURNO = des.idturno"+
							" and def.idtipoencalidad IN (" + calidadIN +" )";
				}
				
			}

			if ((designaItem.getFechaEntradaInicio() != null && !designaItem.getFechaEntradaInicio().toString().equalsIgnoreCase(""))
					||
					(designaItem.getFechaEntradaFin() != null && !designaItem.getFechaEntradaFin().toString().equalsIgnoreCase(""))
					){

				DateFormat formatter1 = new SimpleDateFormat("dd/MM/yy");
				String fechaEntradaInicio = formatter1.format(designaItem.getFechaEntradaInicio());
				String fechaEntradaFin = formatter1.format(designaItem.getFechaEntradaFin());
				
				sql += " and des.fechaentrada between '" + fechaEntradaInicio + "' and '" + fechaEntradaFin + "' ";

			}
			if ((designaItem.getFechaJustificacionDesde() != null && designaItem.getFechaJustificacionDesde().toString() != null && !designaItem.getFechaJustificacionDesde().toString().equalsIgnoreCase(""))
					||
					(designaItem.getFechaJustificacionHasta() != null && designaItem.getFechaJustificacionHasta().toString() != null && !designaItem.getFechaJustificacionHasta().toString().equalsIgnoreCase(""))
					){
				DateFormat formatter1 = new SimpleDateFormat("dd/MM/yy");
				String fechaEntradaInicio = formatter1.format(designaItem.getFechaJustificacionDesde());
				String fechaEntradaFin = formatter1.format(designaItem.getFechaJustificacionHasta());

				sql += " and act.fechaJustificacion between '" + fechaEntradaInicio + "' and '" + fechaEntradaFin + "' ";

			}
			if(designaItem.getIdTipoDesignaColegio() != null && (!String.valueOf(designaItem.getIdTipoDesignaColegio()).equalsIgnoreCase(""))){
				if(designaItem.getIdTipoDesignaColegio().length == 1) {
					sql += " AND des.IDTIPODESIGNACOLEGIO = " + designaItem.getIdTipoDesignaColegio()[0];
				}else {
					String turnoIN = "";
					for(int i = 0; i<designaItem.getIdTipoDesignaColegio().length; i++) {
						String turno = designaItem.getIdTipoDesignaColegio()[i];
						if(i == designaItem.getIdTipoDesignaColegio().length-1) {
							turnoIN = turnoIN + turno;
						}else {
							turnoIN = turnoIN + turno +" ,";
						}
					}
					sql += " AND des.IDTIPODESIGNACOLEGIO IN (" + turnoIN +" )";
				}
			}

			if (tiene_interesado){
				sql+=" AND DED.IDINSTITUCION = PER.IDINSTITUCION";
				sql+="    AND DED.IDPERSONA = PER.IDPERSONA";
				sql+="    AND DED.IDINSTITUCION = des.idInstitucion";
				sql+="    AND DED.ANIO = des.ANIO";
				sql+="    AND DED.IDTURNO = des.idTURNO";
				sql+="    AND DED.NUMERO = des.NUMERO";

				if(designaItem.getNif() != null && !designaItem.getNif().equalsIgnoreCase("")){
					sql+=" and PER.NIF = " + "'"+designaItem.getNif().trim()+"'";
				}
				if(designaItem.getNombreInteresado() != null && !designaItem.getNombreInteresado().equalsIgnoreCase("")){
					sql+=" and PER.NOMBRE = "+"'"+designaItem.getNombreInteresado().trim()+"'";
				}
//				if(designaItem.getApellidosInteresado() != null && !designaItem.getApellidosInteresado().equalsIgnoreCase("")){
//					sql+=" and ";
//					contador++;
//					sql+="regexp_like("+designaItem.getApellidosInteresado()+",:"+contador+")";
//					sql+=ComodinBusquedas.prepararSentenciaCompletaBind(designaItem.getApellidosInteresado().trim(),"PER.APELLIDO1",contador,codigosBind);
//				}
//				if(UtilidadesHash.getString(miHash,"APELLIDO2") != null && !UtilidadesHash.getString(miHash,"APELLIDO2").equalsIgnoreCase("")){
//					sql+=" and ";
//					contador++;
//					sql+=ComodinBusquedas.prepararSentenciaCompletaBind((String)UtilidadesHash.getString(miHash,"APELLIDO2").trim(),"PER.APELLIDO2",contador,codigosBind);
//				}
			}

			if (tiene_juzg||tiene_asunto||tiene_acreditacion||tiene_modulo||tiene_fechaJustificacionDesde||tiene_fechaJustificacionHasta || tiene_origen){
				sql+=	" and des.idinstitucion = act.idinstitucion"+
						" and des.idturno = act.idturno"+
						" and des.anio = act.anio"+
						" and des.numero = act.numero ";
				if (tiene_juzg) {
					String a[]=(String.valueOf(designaItem.getIdJuzgadoActu())).split(",");
					if(designaItem.getIdJuzgadoActu().length == 1) {
						sql += " AND act.idjuzgado = " + designaItem.getIdJuzgadoActu()[0];
					}else {
						String turnoIN = "";
						for(int i = 0; i<designaItem.getIdJuzgadoActu().length; i++) {
							String turno = designaItem.getIdJuzgadoActu()[i];
							if(i == designaItem.getIdJuzgadoActu().length-1) {
								turnoIN = turnoIN + turno;
							}else {
								turnoIN = turnoIN + turno +" ,";
							}
						}
						sql += " AND act.idjuzgado IN (" + turnoIN +" )";
					}
				}
				if (tiene_asunto) {
					sql += " AND des.RESUMENASUNTO = '" + designaItem.getAsunto().trim()+"' ";
				}
				if (tiene_acreditacion) {
					if(designaItem.getIdAcreditacion().toString().indexOf(',') != -1) {
						sql += " AND act.idacreditacion = " + designaItem.getIdAcreditacion()[0];
					}else {
						String turnoIN = "";
						for(int i = 0; i<designaItem.getIdAcreditacion().length; i++) {
							String turno = designaItem.getIdAcreditacion()[i];
							if(i == designaItem.getIdTurno().length-1) {
								turnoIN = turnoIN + turno;
							}else {
								turnoIN = turnoIN + turno +" ,";
							}
						}
						sql += " AND act.idacreditacion IN (" + turnoIN +" )";
					}
				}
				if (tiene_origen) {
					if(designaItem.getIdOrigen().equalsIgnoreCase("COLEGIO")){
						sql +=" AND act.USUCREACION <> ";
					}else{
						sql +=" AND act.USUCREACION = ";
					}
					sql += "  (SELECT U.IDUSUARIO "+
							"    FROM CEN_PERSONA P,  ADM_USUARIOS U "+
							"    WHERE      "+
							"       U.NIF = P.NIFCIF "+
							"       AND U.IDINSTITUCION = act.IDINSTITUCION "+
							"       AND P.IDPERSONA = act.IDPERSONACOLEGIADO) ";

				}
			}
//			if (tamMax != null) {
//				Integer tamMaxNumber = tamMax + 1;
//				sql += ("AND rownum <= " + tamMaxNumber);
//			}
			// jbd // inc7744 // Cambiamos el order by porque parece que afecta a la query cuando se busca por colegiado
			// sql+=" order by des.idturno, des.anio desc, des.numero desc";
			sql+="  and rownum <= 200 order by des.anio desc, codigo desc ";
			// No utilizamos la clase Paginador para la busqueda de letrados porque al filtrar por residencia la sql no devolvia bien los 
			//  datos que eran de tipo varchar (devolvía n veces el mismo resultado), utilizamos el paginador PaginadorCaseSensitive
			// y hacemos a parte el tratamiento de mayusculas y signos de acentuación

		} 
		catch (Exception e) { 	
			throw e;
		}

		

		return sql;
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
		sql.append(" SELECT * FROM (");
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
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String fecha = dateFormat.format(item.getJustificacionDesde());
				sql.append(" AND TRUNC(ACT.FECHAJUSTIFICACION) >= '" + fecha + "'");
			}

			if (item.getJustificacionHasta() != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String fecha = dateFormat.format(item.getJustificacionHasta());
				
				sql.append(" AND TRUNC(ACT.FECHAJUSTIFICACION) <= '" + fecha + "'");
			}

			sql.append(" )>0");
		}
		
		//fechas designacion
		if (item.getDesignacionDesde() != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String fecha = dateFormat.format(item.getDesignacionDesde());
			
			sql.append("AND TRUNC(D.FECHAENTRADA) >= '" + fecha + "'");
		}else{
			sql.append(" AND TRUNC(D.FECHAENTRADA) > '01/01/1950'");
		}
		
		if (item.getDesignacionHasta() != null){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String fecha = dateFormat.format(item.getDesignacionHasta());
			
			sql.append("AND D.FECHAENTRADA <= '" + fecha + "'");
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
		
		sql.append(" ) query WHERE ROWNUM <= 200 ORDER BY CODIGODESIGNA DESC");
		
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

	public static String prepararSentenciaCompletaBind( String cadena, String Campo, int contador, Hashtable codigos ) {
        String temp = "";
        String sentenciaCompleta="";
        /* La cadena introducida se pasa a mayusculas y se eliminan los blancos
         * por la derecha y por la izquierda
         */
        //cadena = cadena.trim();
        cadena=validateChars(cadena);
       
        if ( hasComodin( cadena ) ){
        	
            codigos.put(new Integer(contador),convertir(cadena));
            temp = " LIKE :"+contador + " ";
            sentenciaCompleta=Campo+temp;
        } else {
        	
            codigos.put(new Integer(contador),cadena);
            sentenciaCompleta="regexp_like("+Campo+",:"+contador+")";
            
        }
       
        
        return sentenciaCompleta;
    }
	
	  public static String validateChars(Object cad) {
		  cad = replacePattern(replaceBlanks(cad.toString()),"'","''");
		  cad = replacePattern(replaceBlanks(cad.toString()),"(","\\(");
		  cad = replacePattern(replaceBlanks(cad.toString()),")","\\)");
	    return cad.toString();
	  }
	  
	  private static String replaceBlanks(String str) {
		    byte[] aux = str.trim().getBytes();
		    int j=0;
		    for(int i=0; i<aux.length;){
		      aux[j++]=aux[i];
		      if(aux[i++]==' ')
		      	while(aux[i]==' ')i++;
		    }
		    return new String(aux,0,j);
		}  

	  private static String replacePattern(String str, String pattern, String replace) {
		    int s = 0;
		    int e = 0;
		    StringBuffer result = new StringBuffer();
		
		    while ((e = str.indexOf(pattern, s)) >= 0) {
		      result.append(str.substring(s, e));
		      result.append(replace);
		      s = e+pattern.length();
		    }
		    result.append(str.substring(s));
		    return result.toString();
		  }
	
	  public static boolean hasComodin(String cadena ){
	    	if (cadena == null)
	    		return false;
	        int posicion = 0;                                // posición del carácter en la cadena
	        posicion = cadena.indexOf('*') + 1;              //  -1 + 1 si no está
	        posicion = posicion + cadena.indexOf('?') + 1;   //  -1 + 1 si no está
	        return (posicion != 0);                          //  si cero, falso
	    }
	    
	    public static boolean hasComa(String cadena ){
	    	if (cadena == null)
	    		return false;
	        return cadena.indexOf(',')>=0;
	    }
	    public static boolean hasGuion(String cadena ){
	    	if (cadena == null)
	    		return false;
	        return cadena.indexOf('-')>=0;                     //  si cero, falso
	    }
	    
	    public static String convertir( String cadena ) {
	        String temp = "";
	        temp = cadena.replace('*', '%');
	        temp =   temp.replace('?', '_');
	        temp = temp.toUpperCase();
	        return temp;
	    }
	    
	    public static String prepararSentenciaNIFBind( String cadena, String campo, int contador, Hashtable codigos ) {
	        String cadenaTemp = "";
	        String campoNIF=campo;
	        cadena=cadena.trim();
	        /*if (cadena.length()<9){
	        	for (int i=0; i<9-cadena.length();i++){
	        		cerosAux=cerosAux+ceros;        		
	        		if (i==0){
	        			cadenaTemp=" ("+campoNIF+" LIKE UPPER('"+cadena+"%')";        	
	        		}
	        		cadenaTemp+=" OR "+campoNIF+" LIKE UPPER('"+cerosAux+cadena+"%')";	
	        	}        		
	        } else
	      		 cadenaTemp=" ("+campoNIF+" LIKE UPPER('"+cadena+"%')";
	        cadenaTemp+=")";*/
	        cadena=validateChars(cadena);
	        
	        codigos.put(new Integer(contador),cadena+"%");
	        cadenaTemp +=" LTRIM(UPPER("+campoNIF+"),'0') LIKE LTRIM(UPPER("+contador+"),'0') ";
	        //cadenaTemp="regexp_like ("+campoNIF+",'^[0]{0,8}:"+contador+"\\w*$')";
	        
	        return cadenaTemp;
	    }
	    
	    public static Vector dateBetweenDesdeAndHastaBind(String nombreColumna, String fechaDesde, String fechaHasta, int contador, Hashtable codigos) {
			Vector resultV = new Vector();
			String result = "";
			//SimpleDateFormat sdf = new SimpleDateFormat(ClsConstants.DATE_FORMAT_JAVA);

			try {
				String fechaIni = fechaDesde;
				String fechaFin = fechaHasta;
				
				/* JPT: 
				 * - No entiendo para que le suma un dia y luego hace un trunc
				 * - Si le suma un dia y hace menor o igual por fecha y hora todavia puede tener sentido 
				 * 
					 if (fechaFin != null && !fechaFin.trim().equals("")) {
						Date d = sdf.parse(fechaFin);
						d.setTime(d.getTime() + ClsConstants.DATE_MORE);
						fechaFin = (sdf.format(d));
					}
				*/
				boolean existedesde = false;

				if (fechaIni != null && !fechaIni.trim().equals("")) {
					contador++;
					codigos.put(new Integer(contador), fechaIni);
					result = " TRUNC(" + nombreColumna + ") >= TRUNC(TO_DATE(:" + contador + ", '" + "dd/mm/yy" + "')) ";
					existedesde = true;
				}

				if (fechaFin != null && !fechaFin.trim().equals("")) {
					if (existedesde) {
						result += " AND ";
					}
					contador++;
					codigos.put(new Integer(contador), fechaFin);
					result += " TRUNC(" + nombreColumna + ") <= TRUNC(TO_DATE(:" + contador + ", '" + "dd/mm/yy" + "')) ";
				}

			} catch (Exception e) {
				throw e;
			}

			resultV.add(new Integer(contador));
			resultV.add(result);

			return resultV;
		}
	    
	    public String busquedaListaContrarios(DesignaItem designaItem, Short idInstitucion) {
	    	
	    	SQL sql1 = new SQL();
	    	
	    	//Extraemos el identificador y apellidos y nombre de la persona asociada al contrario
	    	sql1.SELECT("SCS_CONTRARIODESIGNA.NUMERO");
	    	sql1.SELECT("SCS_CONTRARIODESIGNA.IDINSTITUCION");
	    	sql1.SELECT("SCS_CONTRARIODESIGNA.IDTURNO");
	    	sql1.SELECT("SCS_CONTRARIODESIGNA.ANIO");
	    	sql1.SELECT("SCS_CONTRARIODESIGNA.IDPERSONA");
	    	sql1.SELECT("CEN_PERSONA.NIFCIF");
	    	sql1.SELECT("CONCAT( CEN_PERSONA.APELLIDOS1, ' ', CEN_PERSONA.APELLIDOS2, ', ', CEN_PERSONA.NOMBRE ) as APELLIDOSNOMBRE");
	    	sql1.FROM("SCS_CONTRARIODESIGNA ON SCS_CONTRARIODESIGNA.NUMERO=SCS_DESIGNA.NUMERO");
	    	sql1.JOIN("CEN_PERSONA ON CEN.PERSONA.IDPERSONA=SCS_CONTRARIODESIGNA.IDPERSONA");
	    	sql1.WHERE("SCS_CONTRARIODESIGNA.NUMERO = '"+designaItem.getNumero()+"'");
	    	sql1.WHERE("SCS_CONTRARIODESIGNA.IDINSTITUCION = '"+idInstitucion+"");

	    	
	    	SQL sql2 = new SQL();
	    	
	    	//Extraemos el nº colegiado y los apellidos y el nombre del abogado asociado al contrario
	    	sql2.SELECT("SCS_CONTRARIODESIGNA.NUMERO");
	    	sql2.SELECT("SCS_CONTRARIODESIGNA.IDINSTITUCION");
	    	sql2.SELECT("SCS_CONTRARIODESIGNA.IDTURNO");
	    	sql2.SELECT("SCS_CONTRARIODESIGNA.ANIO");
	    	sql2.SELECT("SCS_CONTRARIODESIGNA.IDPERSONA");
	    	sql2.SELECT("CONCAT(CEN_COLEGIADO.NCOLEGIADO, ' : ', CEN_PERSONA.APELLIDOS1, ' ', CEN_PERSONA.APELLIDOS2, ', ', CEN_PERSONA.NOMBRE ) as ABOGADO");
	    	sql2.FROM("SCS_CONTRARIODESIGNA");
	    	sql2.JOIN("CEN_PERSONA ON CEN.PERSONA.IDPERSONA=SCS_CONTRARIODESIGNA.IDABOGADOCONTRARIO");
	    	sql2.JOIN("CEN_COLEGIADO ON CEN.COLEGIADO.IDPERSONA=SCS_CONTRARIODESIGNA.IDABOGADOCONTRARIO");
	    	sql2.WHERE("SCS_CONTRARIODESIGNA.NUMERO = '"+designaItem.getNumero()+"");
	    	sql2.WHERE("SCS_CONTRARIODESIGNA.IDINSTITUCION = '"+idInstitucion+"");

	    	
	    	SQL sql3 = new SQL();
	    	
	    	//Extraemos el nº colegiado y los apellidos y el nombre del procurador asociado al contrario
	    	sql3.SELECT("SCS_CONTRARIODESIGNA.NUMERO");
	    	sql3.SELECT("SCS_CONTRARIODESIGNA.IDINSTITUCION");
	    	sql3.SELECT("SCS_CONTRARIODESIGNA.IDTURNO");
	    	sql3.SELECT("SCS_CONTRARIODESIGNA.ANIO");
	    	sql3.SELECT("SCS_CONTRARIODESIGNA.IDPERSONA");
	    	sql3.SELECT("CONCAT(CEN_COLEGIADO.NCOLEGIADO, ' : ', CEN_PERSONA.APELLIDOS1, ' ', CEN_PERSONA.APELLIDOS2, ', ', CEN_PERSONA.NOMBRE ) as PROCURADOR");
	    	sql3.FROM("SCS_CONTRARIODESIGNA");
	    	sql3.JOIN("CEN_PERSONA ON CEN.PERSONA.IDPERSONA=SCS_CONTRARIODESIGNA.IDPROCURADOR");
	    	sql3.JOIN("CEN_COLEGIADO ON CEN.COLEGIADO.IDPERSONA=SCS_CONTRARIODESIGNA.IDPROCURADOR");
	    	sql3.WHERE("SCS_CONTRARIODESIGNA.NUMERO = '"+designaItem.getNumero()+"");
	    	sql3.WHERE("SCS_CONTRARIODESIGNA.IDINSTITUCION = '"+idInstitucion+"");
	    	
//	    	SELECT t1.ks, t1.[# Tasks], COALESCE(t2.[# Late], 0) AS [# Late]
//			FROM 
//			    (SELECT ks, COUNT(*) AS 'Procurador' FROM Table GROUP BY ks) t1
//			LEFT JOIN
//			    (SELECT ks, COUNT(*) AS '# Late' FROM Table WHERE Age > Palt GROUP BY ks) t2
//			ON (t1.SCS_CONTRARIODESIGNA.NUMERO = t2.SCS_CONTRARIODESIGNA.NUMERO &&

	    	
	    	SQL sql4 = new SQL();
	    	
	    	sql4.SELECT("t1.SCS_CONTRARIODESIGNA.NUMERO, t1.SCS_CONTRARIODESIGNA.IDINSTITUCION"
	    			+ "t1.SCS_CONTRARIODESIGNA.IDTURNO, t1.SCS_CONTRARIODESIGNA.NUMEROt1.ANIO,"
	    			+ "t1.SCS_CONTRARIODESIGNA.IDPERSONA, COALESCE(t2.Abogado, 0) AS Abogado,"
	    			+ "COALESCE(t3.Procurador, 0) AS Procurador ");
	    	sql4.FROM("("+sql1.toString()+") t1");
	    	sql4.LEFT_OUTER_JOIN("("+sql2.toString()+") t2");
	    	sql4.LEFT_OUTER_JOIN("("+sql3.toString()+") t3");
	    	
	    	sql4.FROM("ON t1.SCS_CONTRARIODESIGNA.IDINSTITUCION = t2.SCS_CONTRARIODESIGNA.IDINSTITUCION &&"
	    			+ "t1.SCS_CONTRARIODESIGNA.IDTURNO = t2.SCS_CONTRARIODESIGNA.IDTURNO &&"
	    			+ "t1.SCS_CONTRARIODESIGNA.ANIO = t2.SCS_CONTRARIODESIGNA.ANIO &&"
	    			+ "t1.SCS_CONTRARIODESIGNA.IDPERSONA = t1.SCS_CONTRARIODESIGNA.IDPERSONA"+
	    			"t1.SCS_CONTRARIODESIGNA.IDINSTITUCION = t3.SCS_CONTRARIODESIGNA.IDINSTITUCION &&"
	    	    			+ "t1.SCS_CONTRARIODESIGNA.IDTURNO = t3.SCS_CONTRARIODESIGNA.IDTURNO &&"
	    	    			+ "t1.SCS_CONTRARIODESIGNA.ANIO = t3.SCS_CONTRARIODESIGNA.ANIO &&"
	    	    			+ "t1.SCS_CONTRARIODESIGNA.IDPERSONA = t3.SCS_CONTRARIODESIGNA.IDPERSONA");
	    	
	    	
	    	return sql4.toString();
	    }
	    
}
