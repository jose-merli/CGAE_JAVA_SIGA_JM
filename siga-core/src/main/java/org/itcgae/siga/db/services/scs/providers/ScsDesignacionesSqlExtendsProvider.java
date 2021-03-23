package org.itcgae.siga.db.services.scs.providers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableItem;
import org.itcgae.siga.DTOs.scs.DesignaItem;
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
		int totalRegistros=0;

		//aalg. INC_06694_SIGA. Se modifica la query para hacerla más eficiente
		try {
			sql=" select distinct des.estado estado, des.anio anio, des.numero numero, des.fechaentrada fechaentrada,des.idturno idturno, des.codigo||'' codigo, des.sufijo sufijo,des.idinstitucion idinstitucion ";
			sql+=" from scs_designa des ";

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
			boolean tiene_origen=designaItem.getOrigen() != null && !designaItem.getOrigen().equalsIgnoreCase("");

			if (tiene_juzg||tiene_asunto||tiene_acreditacion||tiene_modulo||tiene_fechaJustificacionDesde||tiene_fechaJustificacionHasta || tiene_origen){
				sql+=	", scs_actuaciondesigna act ";
			}

			boolean tiene_interesado=false;
			if((designaItem.getNif() != null && !designaItem.getNif().equalsIgnoreCase(""))
					|| (designaItem.getNombreInteresado() != null && !designaItem.getNombreInteresado().equalsIgnoreCase(""))
					|| (designaItem.getApellidosInteresado() != null && !designaItem.getApellidosInteresado().equalsIgnoreCase(""))){
				tiene_interesado = true;
			}

			if (tiene_interesado){
				sql += ", SCS_DEFENDIDOSDESIGNA DED, SCS_PERSONAJG PER ";
			}

			contador++;
			codigosBind.put(new Integer(contador),idInstitucion);
			sql+=" where des.idinstitucion ="+idInstitucion;

			if(String.valueOf(designaItem.getNumColegiado()) !=null && !(String.valueOf(designaItem.getNumColegiado())).equals("") ){
				sql += " and l.idinstitucion =des.idinstitucion ";
				sql += " and l.idturno =des.idturno ";
				sql += " and l.anio =des.anio "; 
				sql += " and l.numero =des.numero ";
				sql += " and (l.Fechadesigna is null or";
				sql += " l.Fechadesigna = (SELECT MAX(LET2.Fechadesigna) FROM SCS_DESIGNASLETRADO LET2";
				sql += " WHERE l.IDINSTITUCION = LET2.IDINSTITUCION AND l.IDTURNO = LET2.IDTURNO";
				sql += " AND l.ANIO = LET2.ANIO AND l.NUMERO = LET2.NUMERO";
				sql += " AND TRUNC(LET2.Fechadesigna) <= TRUNC(SYSDATE)))";

				contador++;
				codigosBind.put(new Integer(contador),String.valueOf(designaItem.getNumColegiado()));
				sql += " and l.idPersona = " + String.valueOf(designaItem.getNumColegiado()) + " ";
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

			if (String.valueOf(designaItem.getCodigo()) != null && !String.valueOf(designaItem.getCodigo()).equalsIgnoreCase("")) {


				if (hasComodin(String.valueOf(designaItem.getCodigo())))	{
					contador++;
					sql += " AND " + prepararSentenciaCompletaBind(String.valueOf(designaItem.getCodigo()),"des.codigo",contador,codigosBind ); 

				}else {
					contador++;
					codigosBind.put(new Integer(contador),String.valueOf(designaItem.getCodigo()).trim());
					sql += " AND ltrim(des.codigo,'0') = ltrim(" + String.valueOf(designaItem.getCodigo())+",'0')" ; 
				}
			}
			if (designaItem.getIdJuzgado() != null && !String.valueOf(designaItem.getIdJuzgado()).equalsIgnoreCase("")) {
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
					sql += " AND des.idjuzgado IN (" + juzgadoIN +" )";
				}
			}
			if (designaItem.getAsunto() != null && !designaItem.getAsunto().equalsIgnoreCase("")) {
				contador++;
				codigosBind.put(new Integer(contador),designaItem.getAsunto().trim());
				sql += " AND des.resumenasunto = " + designaItem.getAsunto() ;
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
				sql += " AND des.nig = " + designaItem.getNig();
			}			
			// ACTUACIONES PENDIENTES
//			String[] actuacionesPendientes = designaItem.getIdActuacionesV();
//			if (actuacionesPendientes!= null && actuacionesPendientes.length > 0) {
//				if(actuacionesPendientes.equalsIgnoreCase("SINACTUACIONES")){
//					actuacionesPendientes="";
//					sql += " and upper(F_SIGA_ACTUACIONESDESIG(des.idinstitucion,des.idturno,des.anio,des.numero)) is null";
//				}else{
//					contador++;
//					codigosBind.put(new Integer(contador),actuacionesPendientes.trim());
//					sql += " and upper(F_SIGA_ACTUACIONESDESIG(des.idinstitucion,des.idturno,des.anio,des.numero))=upper(" + contador + ")";
//				}
//			}

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
							" and def.idtipoencalidad= '" + designaItem.getIdCalidad()[0]+ "' ";
				}else {
					String calidadIN = "";
					for(int i = 0; i<designaItem.getIdCalidad().length; i++) {
						String calidad = designaItem.getIdCalidad()[i];
						if(i == designaItem.getIdCalidad().length-1) {
							calidadIN = calidadIN + "'"+calidad+"'";
						}else {
							calidadIN = calidadIN + "'"+calidad+"'" +" ,";
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
					sql+=" and ";
					if (hasComodin(designaItem.getNif())){	
						contador++;
						sql+=prepararSentenciaCompletaBind(designaItem.getNif().trim(),"PER.NIF",contador,codigosBind);
					}else{
						contador++;
						sql +=prepararSentenciaNIFBind(designaItem.getNif(),"PER.NIF",contador, codigosBind);
					}
				}
				if(designaItem.getNombreInteresado() != null && !designaItem.getNombreInteresado() .equalsIgnoreCase("")){
					sql+=" and ";
					contador++;
					sql+=prepararSentenciaCompletaBind(designaItem.getNombreInteresado() .trim(),"PER.NOMBRE",contador, codigosBind);
				}
//				if(designaItem.getApellidosInteresado() != null && !designaItem.getApellidosInteresado().equalsIgnoreCase("")){
//					sql+=" and ";
//					contador++;
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
					contador++;
					codigosBind.put(new Integer(contador),designaItem.getAsunto().trim());
					sql += " AND act.numeroasunto = " + designaItem.getAsunto().trim();
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
				if (tiene_modulo) {
					if(designaItem.getIdProcedimiento().length == 1) {
						sql += " AND act.idprocedimiento = " + designaItem.getIdProcedimiento()[0];
					}else {
						String turnoIN = "";
						for(int i = 0; i<designaItem.getIdProcedimiento().length; i++) {
							String turno = designaItem.getIdProcedimiento()[i];
							if(i == designaItem.getIdProcedimiento().length-1) {
								turnoIN = turnoIN + turno;
							}else {
								turnoIN = turnoIN + turno +" ,";
							}
						}
						sql += " AND act.idprocedimiento IN (" + turnoIN +" )";
					}
				}
				if (tiene_origen) {
					if(designaItem.getOrigen().equalsIgnoreCase("ICA")){
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
			sql+=" order by des.anio desc, codigo desc;";
			// No utilizamos la clase Paginador para la busqueda de letrados porque al filtrar por residencia la sql no devolvia bien los 
			//  datos que eran de tipo varchar (devolvía n veces el mismo resultado), utilizamos el paginador PaginadorCaseSensitive
			// y hacemos a parte el tratamiento de mayusculas y signos de acentuación

		} 
		catch (Exception e) { 	
			throw e;
		}

		

		return sql;
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
	    
}
