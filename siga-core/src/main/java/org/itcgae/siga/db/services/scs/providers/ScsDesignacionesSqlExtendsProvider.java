package org.itcgae.siga.db.services.scs.providers;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaItem;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaRequestDTO;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableItem;
import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.DocumentoActDesignaItem;
import org.itcgae.siga.DTOs.scs.DocumentoDesignaItem;
import org.itcgae.siga.DTOs.scs.JustificacionExpressItem;
import org.itcgae.siga.DTOs.scs.ProcuradorItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.ScsDesigna;
import org.itcgae.siga.db.entities.ScsSaltoscompensaciones;
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
					+ "           (SELECT MAX(LET2.Fechadesigna)\r\n"
					+ "               FROM SCS_DESIGNASLETRADO LET2\r\n"
					+ "              WHERE DESIGNALETRADO.IDINSTITUCION = LET2.IDINSTITUCION\r\n"
					+ "                AND DESIGNALETRADO.IDTURNO = LET2.IDTURNO\r\n"
					+ "                AND DESIGNALETRADO.ANIO = LET2.ANIO\r\n"
					+ "                AND DESIGNALETRADO.NUMERO = LET2.NUMERO\r\n"
					+ "                AND TRUNC(LET2.Fechadesigna) <= TRUNC(SYSDATE)))\r\n" + "");
		}
		if (asuntosJusticiableItem.getNif() != null && asuntosJusticiableItem.getNif() != "") {
			sql.WHERE("upper(PERSONA.NIF) like upper('%" + asuntosJusticiableItem.getNif() + "%')");
		}
		if (asuntosJusticiableItem.getIdTipoDesigna() != null) {
			sql.WHERE("DESIGNA.IDTIPODESIGNACOLEGIO = " + asuntosJusticiableItem.getIdTipoDesigna());
		}
		if (asuntosJusticiableItem.getIdEstadoDesigna() != null) {
			sql.WHERE("DESIGNA.ESTADO = '" + asuntosJusticiableItem.getIdEstadoDesigna() + "'");
		}
		if (asuntosJusticiableItem.getNumProcedimiento() != null
				&& asuntosJusticiableItem.getNumProcedimiento() != "") {
			sql.WHERE("DESIGNA.NUMPROCEDIMIENTO   = '" + asuntosJusticiableItem.getNumProcedimiento() + "'");
		}
		// if(asuntosJusticiableItem.getNumeroDiligencia() != null) {
		// sql.WHERE("ASISTENCIA.NUMERODILIGENCIA =
		// "+asuntosJusticiableItem.getNumeroDiligencia());
		// }
		if (asuntosJusticiableItem.getNig() != null && asuntosJusticiableItem.getNig() != "") {
			sql.WHERE("DESIGNA.NIG   = '" + asuntosJusticiableItem.getNig() + "'");
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
		int contador = 0;
		// Acceso a BBDD

		// aalg. INC_06694_SIGA. Se modifica la query para hacerla más eficiente
		try {
			sql = "select des.art27, persona.idpersona, des.idpretension, des.idjuzgado, des.FECHAOFICIOJUZGADO, des.DELITOS, des.FECHARECEPCIONCOLEGIO, des.OBSERVACIONES, des.FECHAJUICIO, des.DEFENSAJURIDICA,"
					+ " des.nig, des.numprocedimiento,des.idprocedimiento, des.estado estado, des.anio anio, des.numero numero, des.IDTIPODESIGNACOLEGIO, des.fechaalta fechaalta,"
					+ " des.fechaentrada fechaentrada,des.idturno idturno, des.codigo codigo, des.sufijo sufijo, des.fechafin, des.idinstitucion idinstitucion,"
					+ "  des.fechaestado fechaestado,colegiado.ncolegiado,juzgado.nombre as nombrejuzgado, "
					+ " turno.nombre,"
					+ " persona.nombre as nombrepersona, persona.APELLIDOS1 as apellido1persona, persona.APELLIDOS2 as apellido2persona ,"
					+ " PER.NOMBRE AS NOMBREINTERESADO," + " PER.APELLIDO1, PER.APELLIDO2  ";
			sql += " from scs_designa des, cen_persona persona ," + " SCS_DESIGNASLETRADO l ,"
					+ "  CEN_COLEGIADO colegiado," + " scs_turno turno,"
					+ " scs_juzgado juzgado, SCS_DEFENDIDOSDESIGNA DED," + " SCS_PERSONAJG PER   ";

//			if (String.valueOf(designaItem.getNumColegiado()) != null
//					&& !String.valueOf(designaItem.getNumColegiado()).equals("")) {
//				sql += ", SCS_DESIGNASLETRADO l ";
//			}

//			if (designaItem.getIdCalidad() != null && designaItem.getIdCalidad().length > 0) {
//				sql += ", SCS_DEFENDIDOSDESIGNA def ";
//			}

			boolean tiene_juzg = designaItem.getNombreJuzgadoActu() != null
					&& !designaItem.getNombreJuzgadoActu().equalsIgnoreCase("");
			boolean tiene_asunto = designaItem.getAsunto() != null && !designaItem.getAsunto().equalsIgnoreCase("");
			boolean tiene_acreditacion = designaItem.getIdAcreditacion() != null
					&& !designaItem.getAcreditacion().equalsIgnoreCase("");
			boolean tiene_modulo = designaItem.getModulo() != null && !designaItem.getModulo().equalsIgnoreCase("");
			boolean tiene_fechaJustificacionDesde = designaItem.getFechaJustificacionDesde() != null
					&& designaItem.getFechaJustificacionDesde().toString() != null
					&& !designaItem.getFechaJustificacionDesde().toString().equalsIgnoreCase("");
			boolean tiene_fechaJustificacionHasta = designaItem.getFechaJustificacionHasta() != null
					&& designaItem.getFechaJustificacionHasta().toString() != null
					&& !designaItem.getFechaJustificacionHasta().toString().equalsIgnoreCase("");
			boolean tiene_origen = designaItem.getIdOrigen() != null && !designaItem.getIdOrigen().equalsIgnoreCase("");
			boolean tiene_actuacionesV = designaItem.getIdActuacionesV() != null
					&& !designaItem.getIdActuacionesV().equalsIgnoreCase("");
			boolean tiene_moduloDesignacion = (designaItem.getIdModulo() != null
					&& designaItem.getIdModulos().length > 0);
			boolean tienePretensionesDesignacion = (designaItem.getIdProcedimiento() != null
					&& designaItem.getIdProcedimientos().length > 0);

			if (tiene_juzg || tiene_asunto || tiene_acreditacion || tiene_modulo || tiene_fechaJustificacionDesde
					|| tiene_fechaJustificacionHasta || tiene_origen || tiene_actuacionesV) {
				sql += ", scs_actuaciondesigna act ";
			}

			boolean tiene_interesado = false;
			if ((designaItem.getNif() != null && !designaItem.getNif().equalsIgnoreCase(""))
					|| (designaItem.getNombreInteresado() != null
							&& !designaItem.getNombreInteresado().equalsIgnoreCase(""))
					|| (designaItem.getApellidosInteresado() != null
							&& !designaItem.getApellidosInteresado().equalsIgnoreCase(""))) {
				tiene_interesado = true;
			}

//			sql += ", scs_turno turno,  scs_juzgado juzgado, scs_pretensionesproced pret, scs_procedimientos procd, scs_pretension pretension";

//			if (tiene_interesado) {
//				sql += ", SCS_DEFENDIDOSDESIGNA DED, SCS_PERSONAJG PER ";
//			}

			if (tienePretensionesDesignacion) {
				sql += ", SCS_PRETENSION pret ";
			}

			if (idInstitucion != null) {
				sql += " where l.anio=des.anio and l.numero=des.numero and l.idinstitucion = des.idinstitucion and l.idturno=des.idturno"
						+ " and persona.idpersona=l.idpersona"
						+ " and colegiado.IDINSTITUCION = des.IDINSTITUCION and colegiado.IDPERSONA =persona.idpersona"
						+ " and des.idturno=turno.idturno and des.IDINSTITUCION=turno.IDINSTITUCION"
						+ " and des.idjuzgado = juzgado.idjuzgado and des.IDINSTITUCION = juzgado.IDINSTITUCION"
						+ " AND PER.IDINSTITUCION=des.IDINSTITUCION"
						+ "  and  DED.anio=des.anio and DED.numero=des.numero and DED.idinstitucion = des.idinstitucion and DED.idturno=des.idturno"
						+ "  AND DED.IDINSTITUCION = PER.IDINSTITUCION    AND DED.IDPERSONA = PER.IDPERSONA  ";
			}

			if (String.valueOf(designaItem.getNumColegiado()) != null
					&& !(String.valueOf(designaItem.getNumColegiado())).equals("")) {
//				sql += " and l.idinstitucion =des.idinstitucion and persona.idpersona = colegiado.idpersona ";
//				sql += " and des.idinstitucion = juzgado.idinstitucion and des.idjuzgado = juzgado.idjuzgado";
//				sql += "  and procd.idinstitucion = des.idinstitucion and procd.idprocedimiento = des.idprocedimiento and pret.idinstitucion = procd.idinstitucion and procd.idprocedimiento = pret.idprocedimiento ";
//				sql += " and pretension.idpretension = des.idpretension and pretension.idinstitucion = des.idinstitucion ";
//				sql += " and l.idturno =des.idturno ";
//				sql += " and l.anio =des.anio ";
//				sql += " and l.numero =des.numero ";
//				sql += " and l.idpersona =colegiado.idpersona ";
//				sql += " and l.idinstitucion =colegiado.idinstitucion ";
//				sql += " and l.idinstitucion =des.idinstitucion ";
				sql += " and (l.Fechadesigna is null or";
				sql += " l.Fechadesigna = (SELECT MAX(LET2.Fechadesigna) FROM SCS_DESIGNASLETRADO LET2";
				sql += " WHERE l.IDINSTITUCION = LET2.IDINSTITUCION AND l.IDTURNO = LET2.IDTURNO";
				sql += " AND l.ANIO = LET2.ANIO AND l.NUMERO = LET2.NUMERO";
				sql += " AND TRUNC(LET2.Fechadesigna) <= TRUNC(SYSDATE)))";
				sql += " AND des.IDINSTITUCION = " + idInstitucion;

//				sql += " and l.idpersona = " + String.valueOf(designaItem.getNumColegiado()) + " ";
			}
			if (designaItem.getNumColegiado() != null && !(String.valueOf(designaItem.getNumColegiado())).equals("")) {
				sql += " and colegiado.ncolegiado = " + String.valueOf(designaItem.getNumColegiado()) + " ";
			}

			if (designaItem.getIdTurnos() != null
					&& (String.valueOf(designaItem.getIdTurnos()) != "-1" && designaItem.getIdTurnos().length != 0)
					&& !String.valueOf(designaItem.getIdTurnos()).equals("")) {
				if (designaItem.getIdTurnos().length == 1) {
					sql += " AND des.idTurno = " + designaItem.getIdTurnos()[0];
				} else {
					String turnoIN = "";
					for (int i = 0; i < designaItem.getIdTurnos().length; i++) {
						String turno = designaItem.getIdTurnos()[i];
						if (i == designaItem.getIdTurnos().length - 1) {
							turnoIN = turnoIN + turno;
						} else {
							turnoIN = turnoIN + turno + " ,";
						}
					}
					sql += " AND des.idTurno IN (" + turnoIN + " )";
				}
			}

			if (designaItem.getAno() != 0 && String.valueOf(designaItem.getAno()) != null
					&& !String.valueOf(designaItem.getAno()).equalsIgnoreCase("")) {

				if (String.valueOf(designaItem.getAno()).indexOf('*') >= 0) {

					contador++;
					sql += " AND " + prepararSentenciaCompletaBind(String.valueOf(designaItem.getAno()).trim(),
							"des.anio", contador, codigosBind);

				} else if (designaItem.getAno() != 0) {
					contador++;
					codigosBind.put(new Integer(contador), String.valueOf(designaItem.getAno()).trim());
					sql += " AND des.anio = " + String.valueOf(designaItem.getAno());
				}
			}

			if (designaItem.getCodigo() != null && !designaItem.getCodigo().equalsIgnoreCase("")) {

				if ((designaItem.getCodigo().indexOf(',') != -1) && (designaItem.getCodigo().indexOf('-') == -1)) {
					String[] parts = designaItem.getCodigo().split(",");
					sql += " AND (des.codigo = ";
					for (int i = 0; i < parts.length; i++) {
						if (i == parts.length - 1) {
							sql += parts[i].trim() + ")";
						}else {
							sql += parts[i].trim() + " OR des.codigo = ";
						}
					}
				} else if ((designaItem.getCodigo().indexOf('-') != -1)
						&& (designaItem.getCodigo().indexOf(',') == -1)) {
					String[] parts = designaItem.getCodigo().split("-");
					if (parts.length == 2) {
						sql += " and des.codigo IN (" + parts[0] + "," + parts[1] + ")";
					}
				} else if ((designaItem.getCodigo().indexOf('-') == -1)
						&& (designaItem.getCodigo().indexOf(',') == -1)) {
//					sql += " AND des.codigo = " + String.valueOf(designaItem.getCodigo()).trim();
					sql += "AND ltrim(des.codigo,'0') = ltrim(" + designaItem.getCodigo() + ",'0')";
				}
			}
			if (designaItem.getIdJuzgados() != null && designaItem.getIdJuzgados().length > 0) {
				if (designaItem.getIdJuzgados().length == 1) {
					sql += " AND des.idjuzgado = " + designaItem.getIdJuzgados()[0];
				} else {
					String juzgadoIN = "";
					for (int i = 0; i < designaItem.getIdJuzgados().length; i++) {
						String juzgado = designaItem.getIdJuzgados()[i];
						if (i == designaItem.getIdJuzgados().length - 1) {
							juzgadoIN = juzgadoIN + juzgado;
						} else {
							juzgadoIN = juzgadoIN + juzgado + " ,";
						}
					}
					sql += " AND act.idjuzgado IN (" + juzgadoIN + " )";
				}
			}
			if (designaItem.getAsunto() != null && !designaItem.getAsunto().equalsIgnoreCase("")) {
				contador++;
				codigosBind.put(new Integer(contador), designaItem.getAsunto().trim());
//				sql += " AND des.resumenasunto = '" + designaItem.getAsunto() + "'";
				sql += " AND regexp_like(des.OBSERVACIONES,'" + designaItem.getAsunto() + "')";
			}

			if (designaItem.getIdModuloActuaciones() != null && designaItem.getIdModuloActuaciones().length > 0) {
				if (designaItem.getIdModuloActuaciones().length == 1) {
					sql += " AND act.IDPROCEDIMIENTO = '" + designaItem.getIdModuloActuaciones()[0] + "'";
				} else {
					String estadoIN = "";
					for (int i = 0; i < designaItem.getIdModuloActuaciones().length; i++) {
						String estado = designaItem.getIdModuloActuaciones()[i];
						if (i == designaItem.getIdModuloActuaciones().length - 1) {
							estadoIN = estadoIN + "'" + estado + "'";
						} else {
							estadoIN = estadoIN + "'" + estado + "'" + " ,";
						}
					}
					sql += " AND act.IDPROCEDIMIENTO IN (" + estadoIN + " )";
				}

			}

			if (designaItem.getEstados() != null && designaItem.getEstados().length > 0) {
				if (designaItem.getEstados().length == 1) {
					sql += " AND des.estado = '" + designaItem.getEstados()[0] + "'";
				} else {
					String estadoIN = "";
					for (int i = 0; i < designaItem.getEstados().length; i++) {
						String estado = designaItem.getEstados()[i];
						if (i == designaItem.getEstados().length - 1) {
							estadoIN = estadoIN + "'" + estado + "'";
						} else {
							estadoIN = estadoIN + "'" + estado + "'" + " ,";
						}
					}
					sql += " AND des.estado IN (" + estadoIN + " )";
				}

			}
			if (designaItem.getNumProcedimiento() != null && !designaItem.getNumProcedimiento().equalsIgnoreCase("")) {
				contador++;
				codigosBind.put(new Integer(contador), designaItem.getNumProcedimiento().trim());
//				sql += " AND des.numprocedimiento = " + designaItem.getNumProcedimiento();
				sql += " AND regexp_like(des.numprocedimiento," + designaItem.getNumProcedimiento() + ") ";
			}
			if (designaItem.getNig() != null && !designaItem.getNig().equalsIgnoreCase("")) {
				contador++;
				codigosBind.put(new Integer(contador), designaItem.getNig().trim());
				sql += " AND des.nig = '" + designaItem.getNig() + "'";
			}
			if (tienePretensionesDesignacion) {
				if (designaItem.getIdProcedimientos() != null && designaItem.getIdProcedimientos().length > 0) {
					if (designaItem.getIdProcedimientos().length == 1) {
						sql += " AND pret.IDPRETENSION = '" + designaItem.getIdProcedimientos()[0] + "'";
					} else {
						String estadoIN = "";
						for (int i = 0; i < designaItem.getIdProcedimientos().length; i++) {
							String estado = designaItem.getIdProcedimientos()[i];
							if (i == designaItem.getIdProcedimientos().length - 1) {
								estadoIN = estadoIN + "'" + estado + "'";
							} else {
								estadoIN = estadoIN + "'" + estado + "'" + " ,";
							}
						}
						sql += " AND pret.IDPRETENSION IN (" + estadoIN + " )";
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

			if (designaItem.getIdActuacionesV() != null && !designaItem.getIdActuacionesV().trim().isEmpty()) {
				if ("SINACTUACIONES".equalsIgnoreCase(designaItem.getIdActuacionesV().trim())) {
					sql += (" AND F_SIGA_ACTUACIONESDESIG(des.IDINSTITUCION,des.IDTURNO,des.ANIO,des.NUMERO) IS NULL ");
				} else {
					sql += (" AND UPPER(F_SIGA_ACTUACIONESDESIG(des.IDINSTITUCION,des.IDTURNO,des.ANIO,des.NUMERO))=UPPER('"
							+ designaItem.getIdActuacionesV() + "')");
				}
			}

			if (designaItem.getDocumentacionActuacion() != null
					&& !designaItem.getDocumentacionActuacion().equalsIgnoreCase("")) {
				if (designaItem.getDocumentacionActuacion().equalsIgnoreCase("TODAS")) {
					sql += " and act.DOCJUSTIFICACION IS NOT NULL ";
				} else if (designaItem.getDocumentacionActuacion().equalsIgnoreCase("ALGUNAS")) {
					sql += " and act.DOCJUSTIFICACION = '1'"; // -----FALTA
				} else if (designaItem.getDocumentacionActuacion().equalsIgnoreCase("NINGUNA")) {
					sql += " and act.DOCJUSTIFICACION IS NULL";
				}
			}

			// Mostrar ART 27
			String mostarArt27 = designaItem.getIdArt27();
			if (mostarArt27 != null && !mostarArt27.equalsIgnoreCase("") && !mostarArt27.equalsIgnoreCase("T")) {
				if (mostarArt27.equalsIgnoreCase("S")) {
					sql += " AND des.art27 = 1";
				} else if (mostarArt27.equalsIgnoreCase("N")) {
					sql += " AND des.art27 = 0";
				}
			}

			if (designaItem.getIdCalidad() != null && designaItem.getIdCalidad().length > 0) {
				if (designaItem.getIdCalidad().length == 1) {
					sql += " and DED.idtipoencalidad= " + designaItem.getIdCalidad()[0];
				} else {
					String calidadIN = "";
					for (int i = 0; i < designaItem.getIdCalidad().length; i++) {
						String calidad = designaItem.getIdCalidad()[i];
						if (i == designaItem.getIdCalidad().length - 1) {
							calidadIN = calidadIN + "'" + calidad + "'";
						} else {
							calidadIN = calidadIN + calidad + " ,";
						}
					}
					sql += " and def.ANIO = des.anio" + " and def.NUMERO = des.numero"
							+ " and def.IDINSTITUCION = des.idinstitucion" + " and def.IDTURNO = des.idturno"
							+ " and def.idtipoencalidad IN (" + calidadIN + " )";
				}

			}

			if ((designaItem.getFechaEntradaInicio() != null
					&& !designaItem.getFechaEntradaInicio().toString().equalsIgnoreCase(""))
					|| (designaItem.getFechaEntradaFin() != null
							&& !designaItem.getFechaEntradaFin().toString().equalsIgnoreCase(""))) {

				DateFormat formatter1 = new SimpleDateFormat("dd/MM/yy");
				String fechaEntradaInicio = formatter1.format(designaItem.getFechaEntradaInicio());
				String fechaEntradaFin = formatter1.format(designaItem.getFechaEntradaFin());

				sql += " and des.fechaentrada between '" + fechaEntradaInicio + "' and '" + fechaEntradaFin + "' ";

			}
			if ((designaItem.getFechaJustificacionDesde() != null
					&& designaItem.getFechaJustificacionDesde().toString() != null
					&& !designaItem.getFechaJustificacionDesde().toString().equalsIgnoreCase(""))
					|| (designaItem.getFechaJustificacionHasta() != null
							&& designaItem.getFechaJustificacionHasta().toString() != null
							&& !designaItem.getFechaJustificacionHasta().toString().equalsIgnoreCase(""))) {
				DateFormat formatter1 = new SimpleDateFormat("dd/MM/yy");
				String fechaEntradaInicio = formatter1.format(designaItem.getFechaJustificacionDesde());
				String fechaEntradaFin = formatter1.format(designaItem.getFechaJustificacionHasta());

				sql += " and act.fechaJustificacion between '" + fechaEntradaInicio + "' and '" + fechaEntradaFin
						+ "' ";

			}
			if (designaItem.getIdTipoDesignaColegios() != null
					&& (!String.valueOf(designaItem.getIdTipoDesignaColegios()).equalsIgnoreCase(""))
					&& designaItem.getIdTipoDesignaColegios().length > 0) {
				if (designaItem.getIdTipoDesignaColegios().length == 1) {
					sql += " AND des.IDTIPODESIGNACOLEGIO = " + designaItem.getIdTipoDesignaColegios()[0];
				} else {
					String turnoIN = "";
					for (int i = 0; i < designaItem.getIdTipoDesignaColegios().length; i++) {
						String turno = designaItem.getIdTipoDesignaColegios()[i];
						if (i == designaItem.getIdTipoDesignaColegios().length - 1) {
							turnoIN = turnoIN + turno;
						} else {
							turnoIN = turnoIN + turno + " ,";
						}
					}
					sql += " AND des.IDTIPODESIGNACOLEGIO IN (" + turnoIN + " )";
				}
			}

			if (tiene_interesado) {
//				sql += " AND DED.IDINSTITUCION = PER.IDINSTITUCION";
//				sql += "    AND DED.IDPERSONA = PER.IDPERSONA";
//				sql += "    AND DED.IDINSTITUCION = des.idInstitucion";
//				sql += "    AND DED.ANIO = des.ANIO";
//				sql += "    AND DED.IDTURNO = des.idTURNO";
//				sql += "    AND DED.NUMERO = des.NUMERO";

				if (designaItem.getNif() != null && !designaItem.getNif().equalsIgnoreCase("")) {
//					sql += " and PER.NIF = " + "'" + designaItem.getNif().trim() + "'";
					sql += " and LTRIM(UPPER(PER.NIF),'0') LIKE LTRIM(UPPER('" + designaItem.getNif().trim()
							+ "%'),'0')";
				}
				if (designaItem.getNombreInteresado() != null
						&& !designaItem.getNombreInteresado().equalsIgnoreCase("")) {
//					sql += " and PER.NOMBRE = " + "'" + designaItem.getNombreInteresado().trim() + "'";
					sql += " AND regexp_like(PER.NOMBRE,'" + designaItem.getNombreInteresado().trim() + "')";
				}
				if (designaItem.getApellidosInteresado() != null
						&& !designaItem.getApellidosInteresado().equalsIgnoreCase("")) {
					sql += " and regexp_like(PER.APELLIDO1,'" + designaItem.getApellidosInteresado().trim() + "')";
					sql += " or regexp_like(PER.APELLIDO2,'" + designaItem.getApellidosInteresado().trim() + "')";
				}
			}

			if (tiene_juzg || tiene_asunto || tiene_acreditacion || tiene_modulo || tiene_fechaJustificacionDesde
					|| tiene_fechaJustificacionHasta || tiene_origen) {
				sql += " and des.idinstitucion = act.idinstitucion" + " and des.idturno = act.idturno"
						+ " and des.anio = act.anio" + " and des.numero = act.numero ";
				if (tiene_juzg) {
					String a[] = (String.valueOf(designaItem.getIdJuzgadoActu())).split(",");
					if (designaItem.getIdJuzgadoActu().length == 1) {
						sql += " AND act.idjuzgado = " + designaItem.getIdJuzgadoActu()[0];
					} else {
						String turnoIN = "";
						for (int i = 0; i < designaItem.getIdJuzgadoActu().length; i++) {
							String turno = designaItem.getIdJuzgadoActu()[i];
							if (i == designaItem.getIdJuzgadoActu().length - 1) {
								turnoIN = turnoIN + turno;
							} else {
								turnoIN = turnoIN + turno + " ,";
							}
						}
						sql += " AND act.idjuzgado IN (" + turnoIN + " )";
					}
				}
				if (tiene_asunto) {
					sql += " AND des.RESUMENASUNTO = '" + designaItem.getAsunto().trim() + "' ";
				}
				if (tiene_acreditacion) {
					if (designaItem.getIdAcreditacion().toString().indexOf(',') != -1) {
						sql += " AND act.idacreditacion = " + designaItem.getIdAcreditacion()[0];
					} else {
						String turnoIN = "";
						for (int i = 0; i < designaItem.getIdAcreditacion().length; i++) {
							String turno = designaItem.getIdAcreditacion()[i];

							if (i == designaItem.getIdTurnos().length - 1) {
								turnoIN = turnoIN + turno;
							} else {
								turnoIN = turnoIN + turno + " ,";
							}
						}
						sql += " AND act.idacreditacion IN (" + turnoIN + " )";
					}
				}

				if (designaItem.getIdProcedimientoActuaciones() != null
						&& designaItem.getIdProcedimientoActuaciones().length > 0) {
					if (designaItem.getIdProcedimientoActuaciones().length == 1) {
						sql += " AND act.IDPROCEDIMIENTO = '" + designaItem.getIdProcedimientoActuaciones()[0] + "'";
					} else {
						String estadoIN = "";
						for (int i = 0; i < designaItem.getIdProcedimientoActuaciones().length; i++) {
							String estado = designaItem.getIdProcedimientoActuaciones()[i];
							if (i == designaItem.getIdProcedimientoActuaciones().length - 1) {
								estadoIN = estadoIN + "'" + estado + "'";
							} else {
								estadoIN = estadoIN + "'" + estado + "'" + " ,";
							}
						}
						sql += " AND act.IDPROCEDIMIENTO IN (" + estadoIN + " )";
					}

				}
				if (tiene_origen) {
					if (designaItem.getIdOrigen().equalsIgnoreCase("COLEGIO")) {
						sql += " AND act.USUCREACION <> ";
					} else {
						sql += " AND act.USUCREACION = ";
					}
					sql += "  (SELECT U.IDUSUARIO " + "    FROM CEN_PERSONA P,  ADM_USUARIOS U " + "    WHERE      "
							+ "       U.NIF = P.NIFCIF " + "       AND U.IDINSTITUCION = act.IDINSTITUCION "
							+ "       AND P.IDPERSONA = act.IDPERSONACOLEGIADO) ";

				}
			}
//			if (tamMax != null) {
//				Integer tamMaxNumber = tamMax + 1;
//				sql += ("AND rownum <= " + tamMaxNumber);
//			}
			// jbd // inc7744 // Cambiamos el order by porque parece que afecta a la query
			// cuando se busca por colegiado
			// sql+=" order by des.idturno, des.anio desc, des.numero desc";
			sql += "  and rownum <= 200 order by des.anio desc, codigo desc ";
			// No utilizamos la clase Paginador para la busqueda de letrados porque al
			// filtrar por residencia la sql no devolvia bien los
			// datos que eran de tipo varchar (devolvía n veces el mismo resultado),
			// utilizamos el paginador PaginadorCaseSensitive
			// y hacemos a parte el tratamiento de mayusculas y signos de acentuación

		} catch (Exception e) {
			throw e;
		}

		return sql;
	}
	
	public String busquedaNuevaDesigna(DesignaItem designaItem, Short idInstitucion, Integer tamMax) throws Exception {
		String sql = "";

		Hashtable codigosBind = new Hashtable();
		int contador = 0;
		// Acceso a BBDD

		// aalg. INC_06694_SIGA. Se modifica la query para hacerla más eficiente
		try {
			sql = "select des.art27, persona.idpersona, des.idpretension, des.idjuzgado, des.FECHAOFICIOJUZGADO, des.DELITOS, des.FECHARECEPCIONCOLEGIO, des.OBSERVACIONES, des.FECHAJUICIO, des.DEFENSAJURIDICA,"
					+ " des.nig, des.numprocedimiento,des.idprocedimiento, des.estado estado, des.anio anio, des.numero numero, des.IDTIPODESIGNACOLEGIO, des.fechaalta fechaalta,"
					+ " des.fechaentrada fechaentrada,des.idturno idturno, des.codigo codigo, des.sufijo sufijo, des.fechafin, des.idinstitucion idinstitucion,"
					+ "  des.fechaestado fechaestado,colegiado.ncolegiado,"
					+ " turno.nombre,"
					+ " persona.nombre as nombrepersona, persona.APELLIDOS1 as apellido1persona, persona.APELLIDOS2 as apellido2persona ";
			sql += " from scs_designa des, cen_persona persona ," + " SCS_DESIGNASLETRADO l ,"
					+ "  CEN_COLEGIADO colegiado," + " scs_turno turno";

//			if (String.valueOf(designaItem.getNumColegiado()) != null
//					&& !String.valueOf(designaItem.getNumColegiado()).equals("")) {
//				sql += ", SCS_DESIGNASLETRADO l ";
//			}

//			if (designaItem.getIdCalidad() != null && designaItem.getIdCalidad().length > 0) {
//				sql += ", SCS_DEFENDIDOSDESIGNA def ";
//			}

			boolean tiene_juzg = designaItem.getNombreJuzgadoActu() != null
					&& !designaItem.getNombreJuzgadoActu().equalsIgnoreCase("");
			boolean tiene_asunto = designaItem.getAsunto() != null && !designaItem.getAsunto().equalsIgnoreCase("");
			boolean tiene_acreditacion = designaItem.getIdAcreditacion() != null
					&& !designaItem.getAcreditacion().equalsIgnoreCase("");
			boolean tiene_modulo = designaItem.getModulo() != null && !designaItem.getModulo().equalsIgnoreCase("");
			boolean tiene_fechaJustificacionDesde = designaItem.getFechaJustificacionDesde() != null
					&& designaItem.getFechaJustificacionDesde().toString() != null
					&& !designaItem.getFechaJustificacionDesde().toString().equalsIgnoreCase("");
			boolean tiene_fechaJustificacionHasta = designaItem.getFechaJustificacionHasta() != null
					&& designaItem.getFechaJustificacionHasta().toString() != null
					&& !designaItem.getFechaJustificacionHasta().toString().equalsIgnoreCase("");
			boolean tiene_origen = designaItem.getIdOrigen() != null && !designaItem.getIdOrigen().equalsIgnoreCase("");
			boolean tiene_actuacionesV = designaItem.getIdActuacionesV() != null
					&& !designaItem.getIdActuacionesV().equalsIgnoreCase("");
			boolean tiene_moduloDesignacion = (designaItem.getIdModulo() != null
					&& designaItem.getIdModulos().length > 0);
			boolean tienePretensionesDesignacion = (designaItem.getIdProcedimiento() != null
					&& designaItem.getIdProcedimientos().length > 0);

			if (tiene_juzg || tiene_asunto || tiene_acreditacion || tiene_modulo || tiene_fechaJustificacionDesde
					|| tiene_fechaJustificacionHasta || tiene_origen || tiene_actuacionesV) {
				sql += ", scs_actuaciondesigna act ";
			}

			boolean tiene_interesado = false;
			if ((designaItem.getNif() != null && !designaItem.getNif().equalsIgnoreCase(""))
					|| (designaItem.getNombreInteresado() != null
							&& !designaItem.getNombreInteresado().equalsIgnoreCase(""))
					|| (designaItem.getApellidosInteresado() != null
							&& !designaItem.getApellidosInteresado().equalsIgnoreCase(""))) {
				tiene_interesado = true;
			}

//			sql += ", scs_turno turno,  scs_juzgado juzgado, scs_pretensionesproced pret, scs_procedimientos procd, scs_pretension pretension";

//			if (tiene_interesado) {
//				sql += ", SCS_DEFENDIDOSDESIGNA DED, SCS_PERSONAJG PER ";
//			}

			if (tienePretensionesDesignacion) {
				sql += ", SCS_PRETENSION pret ";
			}

			if (idInstitucion != null) {
				sql += " where l.anio=des.anio and l.numero=des.numero and l.idinstitucion = des.idinstitucion and l.idturno=des.idturno"
						+ " and persona.idpersona=l.idpersona"
						+ " and colegiado.IDINSTITUCION = des.IDINSTITUCION and colegiado.IDPERSONA =persona.idpersona"
						+ " and des.idturno=turno.idturno and des.IDINSTITUCION=turno.IDINSTITUCION ";
			}

			if (String.valueOf(designaItem.getNumColegiado()) != null
					&& !(String.valueOf(designaItem.getNumColegiado())).equals("")) {
//				sql += " and l.idinstitucion =des.idinstitucion and persona.idpersona = colegiado.idpersona ";
//				sql += " and des.idinstitucion = juzgado.idinstitucion and des.idjuzgado = juzgado.idjuzgado";
//				sql += "  and procd.idinstitucion = des.idinstitucion and procd.idprocedimiento = des.idprocedimiento and pret.idinstitucion = procd.idinstitucion and procd.idprocedimiento = pret.idprocedimiento ";
//				sql += " and pretension.idpretension = des.idpretension and pretension.idinstitucion = des.idinstitucion ";
//				sql += " and l.idturno =des.idturno ";
//				sql += " and l.anio =des.anio ";
//				sql += " and l.numero =des.numero ";
//				sql += " and l.idpersona =colegiado.idpersona ";
//				sql += " and l.idinstitucion =colegiado.idinstitucion ";
//				sql += " and l.idinstitucion =des.idinstitucion ";
				sql += " and (l.Fechadesigna is null or";
				sql += " l.Fechadesigna = (SELECT MAX(LET2.Fechadesigna) FROM SCS_DESIGNASLETRADO LET2";
				sql += " WHERE l.IDINSTITUCION = LET2.IDINSTITUCION AND l.IDTURNO = LET2.IDTURNO";
				sql += " AND l.ANIO = LET2.ANIO AND l.NUMERO = LET2.NUMERO";
				sql += " AND TRUNC(LET2.Fechadesigna) <= TRUNC(SYSDATE)))";
				sql += " AND des.IDINSTITUCION = " + idInstitucion;

//				sql += " and l.idpersona = " + String.valueOf(designaItem.getNumColegiado()) + " ";
			}
			if (designaItem.getNumColegiado() != null && !(String.valueOf(designaItem.getNumColegiado())).equals("")) {
				sql += " and colegiado.ncolegiado = " + String.valueOf(designaItem.getNumColegiado()) + " ";
			}

			if (designaItem.getIdTurnos() != null
					&& (String.valueOf(designaItem.getIdTurnos()) != "-1" && designaItem.getIdTurnos().length != 0)
					&& !String.valueOf(designaItem.getIdTurnos()).equals("")) {
				if (designaItem.getIdTurnos().length == 1) {
					sql += " AND des.idTurno = " + designaItem.getIdTurnos()[0];
				} else {
					String turnoIN = "";
					for (int i = 0; i < designaItem.getIdTurnos().length; i++) {
						String turno = designaItem.getIdTurnos()[i];
						if (i == designaItem.getIdTurnos().length - 1) {
							turnoIN = turnoIN + turno;
						} else {
							turnoIN = turnoIN + turno + " ,";
						}
					}
					sql += " AND des.idTurno IN (" + turnoIN + " )";
				}
			}

			if (designaItem.getAno() != 0 && String.valueOf(designaItem.getAno()) != null
					&& !String.valueOf(designaItem.getAno()).equalsIgnoreCase("")) {

				if (String.valueOf(designaItem.getAno()).indexOf('*') >= 0) {

					contador++;
					sql += " AND " + prepararSentenciaCompletaBind(String.valueOf(designaItem.getAno()).trim(),
							"des.anio", contador, codigosBind);

				} else if (designaItem.getAno() != 0) {
					contador++;
					codigosBind.put(new Integer(contador), String.valueOf(designaItem.getAno()).trim());
					sql += " AND des.anio = " + String.valueOf(designaItem.getAno());
				}
			}

			if (designaItem.getCodigo() != null && !designaItem.getCodigo().equalsIgnoreCase("")) {

				if ((designaItem.getCodigo().indexOf(',') != -1) && (designaItem.getCodigo().indexOf('-') == -1)) {
					String[] parts = designaItem.getCodigo().split(",");
					sql += " AND (des.codigo = ";
					for (int i = 0; i < parts.length; i++) {
						if (i == parts.length - 1) {
							sql += parts[i].trim() + ")";
						}else {
							sql += parts[i].trim() + " OR des.codigo = ";
						}
					}
				} else if ((designaItem.getCodigo().indexOf('-') != -1)
						&& (designaItem.getCodigo().indexOf(',') == -1)) {
					String[] parts = designaItem.getCodigo().split("-");
					if (parts.length == 2) {
						sql += " and des.codigo IN (" + parts[0] + "," + parts[1] + ")";
					}
				} else if ((designaItem.getCodigo().indexOf('-') == -1)
						&& (designaItem.getCodigo().indexOf(',') == -1)) {
//					sql += " AND des.codigo = " + String.valueOf(designaItem.getCodigo()).trim();
					sql += "AND ltrim(des.codigo,'0') = ltrim(" + designaItem.getCodigo() + ",'0')";
				}
			}
			if (designaItem.getIdJuzgados() != null && designaItem.getIdJuzgados().length > 0) {
				if (designaItem.getIdJuzgados().length == 1) {
					sql += " AND des.idjuzgado = " + designaItem.getIdJuzgados()[0];
				} else {
					String juzgadoIN = "";
					for (int i = 0; i < designaItem.getIdJuzgados().length; i++) {
						String juzgado = designaItem.getIdJuzgados()[i];
						if (i == designaItem.getIdJuzgados().length - 1) {
							juzgadoIN = juzgadoIN + juzgado;
						} else {
							juzgadoIN = juzgadoIN + juzgado + " ,";
						}
					}
					sql += " AND act.idjuzgado IN (" + juzgadoIN + " )";
				}
			}
			if (designaItem.getAsunto() != null && !designaItem.getAsunto().equalsIgnoreCase("")) {
				contador++;
				codigosBind.put(new Integer(contador), designaItem.getAsunto().trim());
//				sql += " AND des.resumenasunto = '" + designaItem.getAsunto() + "'";
				sql += " AND regexp_like(des.OBSERVACIONES,'" + designaItem.getAsunto() + "')";
			}

			if (designaItem.getIdModuloActuaciones() != null && designaItem.getIdModuloActuaciones().length > 0) {
				if (designaItem.getIdModuloActuaciones().length == 1) {
					sql += " AND act.IDPROCEDIMIENTO = '" + designaItem.getIdModuloActuaciones()[0] + "'";
				} else {
					String estadoIN = "";
					for (int i = 0; i < designaItem.getIdModuloActuaciones().length; i++) {
						String estado = designaItem.getIdModuloActuaciones()[i];
						if (i == designaItem.getIdModuloActuaciones().length - 1) {
							estadoIN = estadoIN + "'" + estado + "'";
						} else {
							estadoIN = estadoIN + "'" + estado + "'" + " ,";
						}
					}
					sql += " AND act.IDPROCEDIMIENTO IN (" + estadoIN + " )";
				}

			}

			if (designaItem.getEstados() != null && designaItem.getEstados().length > 0) {
				if (designaItem.getEstados().length == 1) {
					sql += " AND des.estado = '" + designaItem.getEstados()[0] + "'";
				} else {
					String estadoIN = "";
					for (int i = 0; i < designaItem.getEstados().length; i++) {
						String estado = designaItem.getEstados()[i];
						if (i == designaItem.getEstados().length - 1) {
							estadoIN = estadoIN + "'" + estado + "'";
						} else {
							estadoIN = estadoIN + "'" + estado + "'" + " ,";
						}
					}
					sql += " AND des.estado IN (" + estadoIN + " )";
				}

			}
			if (designaItem.getNumProcedimiento() != null && !designaItem.getNumProcedimiento().equalsIgnoreCase("")) {
				contador++;
				codigosBind.put(new Integer(contador), designaItem.getNumProcedimiento().trim());
//				sql += " AND des.numprocedimiento = " + designaItem.getNumProcedimiento();
				sql += " AND regexp_like(des.numprocedimiento," + designaItem.getNumProcedimiento() + ") ";
			}
			if (designaItem.getNig() != null && !designaItem.getNig().equalsIgnoreCase("")) {
				contador++;
				codigosBind.put(new Integer(contador), designaItem.getNig().trim());
				sql += " AND des.nig = '" + designaItem.getNig() + "'";
			}
			if (tienePretensionesDesignacion) {
				if (designaItem.getIdProcedimientos() != null && designaItem.getIdProcedimientos().length > 0) {
					if (designaItem.getIdProcedimientos().length == 1) {
						sql += " AND pret.IDPRETENSION = '" + designaItem.getIdProcedimientos()[0] + "'";
					} else {
						String estadoIN = "";
						for (int i = 0; i < designaItem.getIdProcedimientos().length; i++) {
							String estado = designaItem.getIdProcedimientos()[i];
							if (i == designaItem.getIdProcedimientos().length - 1) {
								estadoIN = estadoIN + "'" + estado + "'";
							} else {
								estadoIN = estadoIN + "'" + estado + "'" + " ,";
							}
						}
						sql += " AND pret.IDPRETENSION IN (" + estadoIN + " )";
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

			if (designaItem.getIdActuacionesV() != null && !designaItem.getIdActuacionesV().trim().isEmpty()) {
				if ("SINACTUACIONES".equalsIgnoreCase(designaItem.getIdActuacionesV().trim())) {
					sql += (" AND F_SIGA_ACTUACIONESDESIG(des.IDINSTITUCION,des.IDTURNO,des.ANIO,des.NUMERO) IS NULL ");
				} else {
					sql += (" AND UPPER(F_SIGA_ACTUACIONESDESIG(des.IDINSTITUCION,des.IDTURNO,des.ANIO,des.NUMERO))=UPPER('"
							+ designaItem.getIdActuacionesV() + "')");
				}
			}

			if (designaItem.getDocumentacionActuacion() != null
					&& !designaItem.getDocumentacionActuacion().equalsIgnoreCase("")) {
				if (designaItem.getDocumentacionActuacion().equalsIgnoreCase("TODAS")) {
					sql += " and act.DOCJUSTIFICACION IS NOT NULL ";
				} else if (designaItem.getDocumentacionActuacion().equalsIgnoreCase("ALGUNAS")) {
					sql += " and act.DOCJUSTIFICACION = '1'"; // -----FALTA
				} else if (designaItem.getDocumentacionActuacion().equalsIgnoreCase("NINGUNA")) {
					sql += " and act.DOCJUSTIFICACION IS NULL";
				}
			}

			// Mostrar ART 27
			String mostarArt27 = designaItem.getIdArt27();
			if (mostarArt27 != null && !mostarArt27.equalsIgnoreCase("") && !mostarArt27.equalsIgnoreCase("T")) {
				if (mostarArt27.equalsIgnoreCase("S")) {
					sql += " AND des.art27 = 1";
				} else if (mostarArt27.equalsIgnoreCase("N")) {
					sql += " AND des.art27 = 0";
				}
			}

			if (designaItem.getIdCalidad() != null && designaItem.getIdCalidad().length > 0) {
				if (designaItem.getIdCalidad().length == 1) {
					sql += " and DED.idtipoencalidad= " + designaItem.getIdCalidad()[0];
				} else {
					String calidadIN = "";
					for (int i = 0; i < designaItem.getIdCalidad().length; i++) {
						String calidad = designaItem.getIdCalidad()[i];
						if (i == designaItem.getIdCalidad().length - 1) {
							calidadIN = calidadIN + "'" + calidad + "'";
						} else {
							calidadIN = calidadIN + calidad + " ,";
						}
					}
					sql += " and def.ANIO = des.anio" + " and def.NUMERO = des.numero"
							+ " and def.IDINSTITUCION = des.idinstitucion" + " and def.IDTURNO = des.idturno"
							+ " and def.idtipoencalidad IN (" + calidadIN + " )";
				}

			}

			if ((designaItem.getFechaEntradaInicio() != null
					&& !designaItem.getFechaEntradaInicio().toString().equalsIgnoreCase(""))
					|| (designaItem.getFechaEntradaFin() != null
							&& !designaItem.getFechaEntradaFin().toString().equalsIgnoreCase(""))) {

				DateFormat formatter1 = new SimpleDateFormat("dd/MM/yy");
				String fechaEntradaInicio = formatter1.format(designaItem.getFechaEntradaInicio());
				String fechaEntradaFin = formatter1.format(designaItem.getFechaEntradaFin());

				sql += " and des.fechaentrada between '" + fechaEntradaInicio + "' and '" + fechaEntradaFin + "' ";

			}
			if ((designaItem.getFechaJustificacionDesde() != null
					&& designaItem.getFechaJustificacionDesde().toString() != null
					&& !designaItem.getFechaJustificacionDesde().toString().equalsIgnoreCase(""))
					|| (designaItem.getFechaJustificacionHasta() != null
							&& designaItem.getFechaJustificacionHasta().toString() != null
							&& !designaItem.getFechaJustificacionHasta().toString().equalsIgnoreCase(""))) {
				DateFormat formatter1 = new SimpleDateFormat("dd/MM/yy");
				String fechaEntradaInicio = formatter1.format(designaItem.getFechaJustificacionDesde());
				String fechaEntradaFin = formatter1.format(designaItem.getFechaJustificacionHasta());

				sql += " and act.fechaJustificacion between '" + fechaEntradaInicio + "' and '" + fechaEntradaFin
						+ "' ";

			}
			if (designaItem.getIdTipoDesignaColegios() != null
					&& (!String.valueOf(designaItem.getIdTipoDesignaColegios()).equalsIgnoreCase(""))
					&& designaItem.getIdTipoDesignaColegios().length > 0) {
				if (designaItem.getIdTipoDesignaColegios().length == 1) {
					sql += " AND des.IDTIPODESIGNACOLEGIO = " + designaItem.getIdTipoDesignaColegios()[0];
				} else {
					String turnoIN = "";
					for (int i = 0; i < designaItem.getIdTipoDesignaColegios().length; i++) {
						String turno = designaItem.getIdTipoDesignaColegios()[i];
						if (i == designaItem.getIdTipoDesignaColegios().length - 1) {
							turnoIN = turnoIN + turno;
						} else {
							turnoIN = turnoIN + turno + " ,";
						}
					}
					sql += " AND des.IDTIPODESIGNACOLEGIO IN (" + turnoIN + " )";
				}
			}

			if (tiene_interesado) {
//				sql += " AND DED.IDINSTITUCION = PER.IDINSTITUCION";
//				sql += "    AND DED.IDPERSONA = PER.IDPERSONA";
//				sql += "    AND DED.IDINSTITUCION = des.idInstitucion";
//				sql += "    AND DED.ANIO = des.ANIO";
//				sql += "    AND DED.IDTURNO = des.idTURNO";
//				sql += "    AND DED.NUMERO = des.NUMERO";

				if (designaItem.getNif() != null && !designaItem.getNif().equalsIgnoreCase("")) {
//					sql += " and PER.NIF = " + "'" + designaItem.getNif().trim() + "'";
					sql += " and LTRIM(UPPER(PER.NIF),'0') LIKE LTRIM(UPPER('" + designaItem.getNif().trim()
							+ "%'),'0')";
				}
				if (designaItem.getNombreInteresado() != null
						&& !designaItem.getNombreInteresado().equalsIgnoreCase("")) {
//					sql += " and PER.NOMBRE = " + "'" + designaItem.getNombreInteresado().trim() + "'";
					sql += " AND regexp_like(PER.NOMBRE,'" + designaItem.getNombreInteresado().trim() + "')";
				}
				if (designaItem.getApellidosInteresado() != null
						&& !designaItem.getApellidosInteresado().equalsIgnoreCase("")) {
					sql += " and regexp_like(PER.APELLIDO1,'" + designaItem.getApellidosInteresado().trim() + "')";
					sql += " or regexp_like(PER.APELLIDO2,'" + designaItem.getApellidosInteresado().trim() + "')";
				}
			}

			if (tiene_juzg || tiene_asunto || tiene_acreditacion || tiene_modulo || tiene_fechaJustificacionDesde
					|| tiene_fechaJustificacionHasta || tiene_origen) {
				sql += " and des.idinstitucion = act.idinstitucion" + " and des.idturno = act.idturno"
						+ " and des.anio = act.anio" + " and des.numero = act.numero ";
				if (tiene_juzg) {
					String a[] = (String.valueOf(designaItem.getIdJuzgadoActu())).split(",");
					if (designaItem.getIdJuzgadoActu().length == 1) {
						sql += " AND act.idjuzgado = " + designaItem.getIdJuzgadoActu()[0];
					} else {
						String turnoIN = "";
						for (int i = 0; i < designaItem.getIdJuzgadoActu().length; i++) {
							String turno = designaItem.getIdJuzgadoActu()[i];
							if (i == designaItem.getIdJuzgadoActu().length - 1) {
								turnoIN = turnoIN + turno;
							} else {
								turnoIN = turnoIN + turno + " ,";
							}
						}
						sql += " AND act.idjuzgado IN (" + turnoIN + " )";
					}
				}
				if (tiene_asunto) {
					sql += " AND des.RESUMENASUNTO = '" + designaItem.getAsunto().trim() + "' ";
				}
				if (tiene_acreditacion) {
					if (designaItem.getIdAcreditacion().toString().indexOf(',') != -1) {
						sql += " AND act.idacreditacion = " + designaItem.getIdAcreditacion()[0];
					} else {
						String turnoIN = "";
						for (int i = 0; i < designaItem.getIdAcreditacion().length; i++) {
							String turno = designaItem.getIdAcreditacion()[i];

							if (i == designaItem.getIdTurnos().length - 1) {
								turnoIN = turnoIN + turno;
							} else {
								turnoIN = turnoIN + turno + " ,";
							}
						}
						sql += " AND act.idacreditacion IN (" + turnoIN + " )";
					}
				}

				if (designaItem.getIdProcedimientoActuaciones() != null
						&& designaItem.getIdProcedimientoActuaciones().length > 0) {
					if (designaItem.getIdProcedimientoActuaciones().length == 1) {
						sql += " AND act.IDPROCEDIMIENTO = '" + designaItem.getIdProcedimientoActuaciones()[0] + "'";
					} else {
						String estadoIN = "";
						for (int i = 0; i < designaItem.getIdProcedimientoActuaciones().length; i++) {
							String estado = designaItem.getIdProcedimientoActuaciones()[i];
							if (i == designaItem.getIdProcedimientoActuaciones().length - 1) {
								estadoIN = estadoIN + "'" + estado + "'";
							} else {
								estadoIN = estadoIN + "'" + estado + "'" + " ,";
							}
						}
						sql += " AND act.IDPROCEDIMIENTO IN (" + estadoIN + " )";
					}

				}
				if (tiene_origen) {
					if (designaItem.getIdOrigen().equalsIgnoreCase("COLEGIO")) {
						sql += " AND act.USUCREACION <> ";
					} else {
						sql += " AND act.USUCREACION = ";
					}
					sql += "  (SELECT U.IDUSUARIO " + "    FROM CEN_PERSONA P,  ADM_USUARIOS U " + "    WHERE      "
							+ "       U.NIF = P.NIFCIF " + "       AND U.IDINSTITUCION = act.IDINSTITUCION "
							+ "       AND P.IDPERSONA = act.IDPERSONACOLEGIADO) ";

				}
			}
//			if (tamMax != null) {
//				Integer tamMaxNumber = tamMax + 1;
//				sql += ("AND rownum <= " + tamMaxNumber);
//			}
			// jbd // inc7744 // Cambiamos el order by porque parece que afecta a la query
			// cuando se busca por colegiado
			// sql+=" order by des.idturno, des.anio desc, des.numero desc";
			sql += "  and rownum <= 200 order by des.anio desc, codigo desc ";
			// No utilizamos la clase Paginador para la busqueda de letrados porque al
			// filtrar por residencia la sql no devolvia bien los
			// datos que eran de tipo varchar (devolvía n veces el mismo resultado),
			// utilizamos el paginador PaginadorCaseSensitive
			// y hacemos a parte el tratamiento de mayusculas y signos de acentuación

		} catch (Exception e) {
			throw e;
		}

		return sql;
	}


	public String busquedaProcedimientoDesignas(DesignaItem designaItem, Short idInstitucion, Integer tamMax)
			throws Exception {
		SQL sql = new SQL();

		sql.SELECT("DISTINCT F_SIGA_GETRECURSO(P.DESCRIPCION, 1) AS procedimiento");

		sql.FROM("SCS_PRETENSION P");

		sql.INNER_JOIN("SCS_DESIGNA D ON D.IDINSTITUCION = P.IDINSTITUCION AND D.IDPRETENSION = P.IDPRETENSION");

		sql.WHERE("P.IDPRETENSION = '" + designaItem.getIdPretension() + "'");
		sql.WHERE("D.IDTURNO = '" + designaItem.getIdTurno() + "'");
		sql.WHERE(" D.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("D.ANIO = '" + designaItem.getAno() + "'");
		sql.WHERE("D.NUMERO = '" + designaItem.getNumero() + "'");

		return sql.toString();

	}

	public String busquedaModuloDesignas(DesignaItem designaItem, Short idInstitucion, Integer tamMax)
			throws Exception {
		SQL sql = new SQL();

		sql.SELECT("procd.nombre as modulo,procd.IDPROCEDIMIENTO as IDMODULO");

		sql.FROM("SCS_PROCEDIMIENTOS procd");

		sql.INNER_JOIN(
				" SCS_DESIGNA DES ON procd.IDINSTITUCION = DES.IDINSTITUCION AND procd.IDPROCEDIMIENTO = DES.IDPROCEDIMIENTO");

		sql.WHERE("procd.IDPROCEDIMIENTO = '" + designaItem.getIdProcedimiento() + "'");
		sql.WHERE("DES.IDTURNO = '" + designaItem.getIdTurno() + "'");
		sql.WHERE(" DES.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("DES.ANIO = '" + designaItem.getAno() + "'");
		sql.WHERE("DES.NUMERO = '" + designaItem.getNumero() + "'");

		return sql.toString();

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

		sql.LEFT_OUTER_JOIN(
				"SCS_TURNO TURNO ON designa.idturno = turno.idturno and designa.idinstitucion = turno.idinstitucion");
		sql.LEFT_OUTER_JOIN(
				"SCS_Juzgado juzgado ON juzgado.idjuzgado = DESIGNA.IDJUZGADO and DESIGNA.IDINSTITUCION = juzgado.IDINSTITUCION");

		sql.WHERE("DESIGNA.IDINSTITUCION = '" + asuntoClave.getIdInstitucion() + "'");
		sql.WHERE("DESIGNA.ANIO = '" + asuntoClave.getAnio() + "'");
		sql.WHERE("DESIGNA.NUMERO = '" + asuntoClave.getNumero() + "'");
		sql.WHERE("DESIGNA.idturno = '" + asuntoClave.getClave() + "'");

		return sql.toString();
	}

	/**
	 * 
	 * @param idInstitucion
	 * @param idTurno
	 * @param anio
	 * @param numero
	 * @return
	 */
	public String busquedaActuacionesJustificacionExpres(String idInstitucion, String idTurno, String anio,
			String numero, JustificacionExpressItem item) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT act.numero, ac.idacreditacion, ac.descripcion acreditacion, ac.idtipoacreditacion, ");
		sql.append( " decode(to_char(acp.porcentaje), to_char(trunc(acp.porcentaje)), to_char(acp.porcentaje), ");
		sql.append( " f_siga_formatonumero(to_char(acp.porcentaje), 2)) porcentaje, tac.descripcion tipo, ");
		sql.append( " pro.nombre procedimiento, pro.codigo categoria, pro.idjurisdiccion, pro.complemento, pro.permitiraniadirletrado, ");
		sql.append( " act.numeroasunto, act.idprocedimiento, act.idjuzgado, j.nombre nombreJuzgado, ");
		sql.append( " to_char(act.fechajustificacion, 'dd/mm/yyyy') fechajustificacion, act.validada, act.idfacturacion, ");
		sql.append( " act.numeroprocedimiento, act.anioprocedimiento, act.anio, act.idTurno, act.idInstitucion, ");
		sql.append( " ( ");
		sql.append( " SELECT nombre || ' (' || fechadesde || '-' || fechahasta || ')' ");
		sql.append( " FROM fcs_facturacionjg fjg ");
		sql.append( " WHERE fjg.idinstitucion = act.idinstitucion AND fjg.idfacturacion = act.idfacturacion");
		sql.append( " ) AS descripcionfacturacion, ");
		sql.append( " act.docjustificacion, act.anulacion, acp.nig_numprocedimiento, act.nig, act.fecha, 0 permitireditarletrado ");
		sql.append( " FROM scs_actuaciondesigna act, scs_procedimientos pro, scs_acreditacionprocedimiento acp, scs_acreditacion ac, ");
		sql.append( " scs_tipoacreditacion tac, scs_juzgado j "); 
		sql.append( " WHERE ac.idtipoacreditacion = tac.idtipoacreditacion AND act.idinstitucion = j.idinstitucion AND act.idjuzgado=j.idjuzgado ");
		sql.append( " AND act.idacreditacion = ac.idacreditacion AND act.idacreditacion = acp.idacreditacion AND act.idinstitucion_proc = acp.idinstitucion ");
		sql.append( " AND act.idprocedimiento = acp.idprocedimiento AND act.idinstitucion_proc = pro.idinstitucion AND act.idprocedimiento = pro.idprocedimiento ");
		sql.append( " AND act.idinstitucion = "+ idInstitucion + " AND act.idturno = " + idTurno + " AND act.anio = " + anio + " AND act.numero = " + numero + " "); 
		if (item.getJustificacionDesde() != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String fecha = dateFormat.format(item.getJustificacionDesde());

			sql.append(" AND TRUNC(ACT.FECHAJUSTIFICACION) >= TO_DATE('" + fecha + "','DD/MM/YYYY')");
		}

		if (item.getJustificacionHasta() != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String fecha = dateFormat.format(item.getJustificacionHasta());

			sql.append(" AND TRUNC(ACT.FECHAJUSTIFICACION) <= TO_DATE('" + fecha + "','DD/MM/YYYY')");
		}
		sql.append(  " ORDER BY act.fechajustificacion, act.numeroasunto");

		return sql.toString();
	}

	/**
	 * 
	 * @param item
	 * @param idInstitucion
	 * @param longitudCodEJG
	 * @param idPersona
	 * @return
	 */
	public String busquedaJustificacionExpres(JustificacionExpressItem item, String idInstitucion,
			String longitudCodEJG, String idPersona, String idFavorable, String idDesfavorable) {

		StringBuilder sql = new StringBuilder();

		sql.append(
				"SELECT * FROM ( SELECT DECODE(ALLDESIGNAS.NUM_TIPO_RESOLUCION_DESIGNA,1,'FAVORABLE', 2,'NO_FAVORABLE', "
						+ "3,'PTE_CAJG', 4, 'SIN_RESOLUCION','SIN_EJG') AS TIPO_RESOLUCION_DESIGNA, ");
		sql.append(" ALLDESIGNAS.* ");
		sql.append(" FROM ( ");

		sql.append(" SELECT TO_CHAR(D.FECHAENTRADA, 'dd/mm/yyyy') AS FECHADESIGNA, ");
		sql.append(" TO_CHAR(D.FECHAENTRADA, 'yyyy_mm_dd') AS FECHAORDEN, ");
		sql.append(" D.ART27 AS ART27, ");
		sql.append(" D.ANIO || '/' || D.CODIGO AS CODIGODESIGNA,");

		sql.append(" F_SIGA_GETEJG_DESIGNA(" + idInstitucion + ",d.idturno,d.anio,d.numero," + longitudCodEJG
				+ ") AS EXPEDIENTES, ");

		sql.append(
				" DECODE(D.ANIOPROCEDIMIENTO,NULL,D.NUMPROCEDIMIENTO,D.NUMPROCEDIMIENTO||'/'||D.ANIOPROCEDIMIENTO) AS ASUNTO, ");

		sql.append(" f_siga_getdefendidosdesigna(" + idInstitucion + ",d.anio,d.idturno,d.numero,0) AS CLIENTE, ");

		sql.append(" D.RESUMENASUNTO AS RESUMENASUNTO, ");
		sql.append(" DL.FECHARENUNCIA, ");
		sql.append(" D.IDINSTITUCION, ");
		sql.append(" D.IDTURNO, ");
		sql.append(" D.ANIO, ");
		sql.append(" D.NUMERO, ");
		sql.append(" D.CODIGO, ");
		sql.append(" D.IDJUZGADO, J.NOMBRE NOMBREJUZGADO, ");
		sql.append(" D.IDINSTITUCION_JUZG, ");
		sql.append(" D.ESTADO, ");
		sql.append(" D.SUFIJO, ");
		sql.append(" D.FECHAENTRADA, ");
		sql.append(" DL.IDPERSONA, ");
		sql.append(" D.IDPROCEDIMIENTO, ");
		sql.append(" D.NUMPROCEDIMIENTO, ");
		sql.append(" D.ANIOPROCEDIMIENTO, P.NOMBRE PROCEDIMIENTO,");
		sql.append(" D.NIG, ");

		sql.append(
				" (SELECT COUNT(*) FROM SCS_DESIGNASLETRADO SDL WHERE D.IDINSTITUCION = SDL.IDINSTITUCION AND D.ANIO = SDL.ANIO AND "
						+ "D.NUMERO = SDL.NUMERO AND D.IDTURNO = SDL.IDTURNO) AS CAMBIOLETRADO, ");

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

		sql.append(" FROM SCS_DESIGNA D join scs_designasletrado   dl ON d.idinstitucion = dl.idinstitucion "
				+ "AND d.anio = dl.anio AND d.numero = dl.numero AND d.idturno = dl.idturno ");
		sql.append("join scs_juzgado j ON  d.idjuzgado = j.idjuzgado\r\n" + 
				"                AND d.idinstitucion_juzg = j.idinstitucion ");
		sql.append("join scs_procedimientos p ON  p.idprocedimiento = d.idprocedimiento\r\n" + 
				"                AND p.idinstitucion = d.idinstitucion ");
		if((item.getSinEJG()!= null && !item.getSinEJG().isEmpty())
				|| (item.getConEJGNoFavorables()!= null && !item.getConEJGNoFavorables().isEmpty())
				|| (item.getEjgSinResolucion()!= null && !item.getEjgSinResolucion().isEmpty())
				|| (item.getResolucionPTECAJG() != null && !item.getResolucionPTECAJG().isEmpty())) {
			sql.append("LEFT OUTER join scs_ejgdesigna ejd ON  d.anio = ejd.aniodesigna\r\n" + 
					"                AND d.numero = ejd.numerodesigna\r\n" + 
					"                AND d.idturno = ejd.idturno\r\n" + 
					"                LEFT OUTER join scs_ejg ejg ON ejd.idinstitucion = ejg.idinstitucion\r\n" + 
					"                AND ejd.idtipoejg = ejg.idtipoejg\r\n" + 
					"                AND ejd.anioejg = ejg.anio\r\n" + 
					"                AND ejd.numeroejg = ejg.numero ");
		}
		sql.append(" WHERE D.IDINSTITUCION = " + idInstitucion);
		        
		if(item.getSinEJG()!= null && !item.getSinEJG().isEmpty()) {
			if(item.getSinEJG().equals("0")){
				sql.append(" AND ejg.anio is not null ");
			}else {
				sql.append(" AND ejg.anio is null ");
			}
		}
		if((item.getConEJGNoFavorables()!= null && !item.getConEJGNoFavorables().isEmpty())) {
			if(item.getEjgSinResolucion().equals("0")) {
				sql.append(" AND ejg.IDTIPODICTAMENEJG <> " + idDesfavorable);
			}//else {
			//	sql.append(" AND ejg.IDTIPODICTAMENEJG = " + idFavorable);
			//}
		}
		if((item.getEjgSinResolucion()!= null && !item.getEjgSinResolucion().isEmpty())) {
			if(item.getConEJGNoFavorables().equals("0")) {
				sql.append(" AND ejg.anioresolucion is not null\r\n" + 
						" and ejg.numeroresolucion is not null ");
			}else {
				sql.append(" AND ejg.anioresolucion IS NULL\r\n" + 
						" AND ejg.numeroresolucion IS NULL ");
			}
		}
		
		if((item.getResolucionPTECAJG() != null && !item.getResolucionPTECAJG().isEmpty())) {
			if(item.getResolucionPTECAJG().equals("0")) {
				sql.append(" AND ejg.EJG.FECHARESOLUCIONCAJG IS NOT NULL");
			}//else {
			//	sql.append(" AND ejg.EJG.FECHARESOLUCIONCAJG IS NULL");
			//}
		}

		if (item.getAnioDesignacion() != null && !item.getAnioDesignacion().trim().isEmpty()) {
			sql.append(" AND D.ANIO = " + item.getAnioDesignacion().trim());
		}

		if (item.getNumDesignacion() != null && !item.getNumDesignacion().isEmpty()) {
			// si viene - hay que buscar de uno a otro (1-5 => numDesignacion 1,2,3,4,5)
			// si viene , hay que buscar uno u otro (1,6 => numDesignacion 1 ó 6)

			sql.append(" AND");

			String[] parts;
			boolean primero = true;

			// contiene ,
			if (item.getNumDesignacion().trim().contains(",")) {
				parts = item.getNumDesignacion().trim().split(",");

				sql.append("(");

				for (String str : parts) {
					if (primero) {
						sql.append(" D.CODIGO = " + str.trim());
						primero = false;
					} else {
						sql.append(" OR D.CODIGO =" + str.trim());
					}
				}

				sql.append(" )");

				// contiene -
			} else if (item.getNumDesignacion().trim().contains("-")) {
				parts = item.getNumDesignacion().trim().split("-");

				sql.append("( TO_NUMBER(D.CODIGO, 999999999999) BETWEEN ");

				for (String str : parts) {
					if (primero) {
						sql.append("TO_NUMBER(" + str.trim() + ", 999999999999)");
						primero = false;
					} else {
						sql.append(" AND TO_NUMBER(" + str.trim() + ", 999999999999)");
					}
				}

				sql.append(" )");
			} else {
				sql.append(" D.CODIGO = " + item.getNumDesignacion().trim());
			}
		}

		if ((item.getAnioEJG() != null && !item.getAnioEJG().trim().isEmpty())
				|| (item.getNumEJG() != null && !item.getNumEJG().trim().isEmpty())) {
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

			if (item.getAnioEJG() != null && !item.getAnioEJG().trim().isEmpty()) {
				sql.append(" AND EJG.ANIO = " + item.getAnioEJG().trim());
			}

			if (item.getNumEJG() != null && !item.getNumEJG().trim().isEmpty()) {
				// si viene - hay que buscar de uno a otro (1-5 => numDesignacion 1,2,3,4,5)
				// si viene , hay que buscar uno u otro (1,6 => numDesignacion 1 ó 6)

				sql.append(" AND");

				String[] parts;
				boolean primero = true;

				// contiene ,
				if (item.getNumEJG().trim().contains(",")) {
					parts = item.getNumEJG().trim().split(",");

					sql.append("(");

					for (String str : parts) {
						if (primero) {
							sql.append(" EJG.NUMEJG = " + str.trim());
							primero = false;
						} else {
							sql.append(" OR EJG.NUMEJG =" + str.trim());
						}
					}

					sql.append(" )");

					// contiene -
				} else if (item.getNumEJG().trim().contains("-")) {
					parts = item.getNumEJG().trim().split("-");

					sql.append("( TO_NUMBER(EJG.NUMEJG, 999999999999) BETWEEN ");

					for (String str : parts) {
						if (primero) {
							sql.append("TO_NUMBER(" + str.trim() + ", 999999999999)");
							primero = false;
						} else {
							sql.append(" AND TO_NUMBER(" + str.trim() + ", 999999999999)");
						}
					}

					sql.append(" )");
				} else {
					sql.append(" EJG.NUMEJG = " + item.getNumEJG().trim());
				}
			}

			sql.append(")");
		}

		if (item.getActuacionesValidadas() != null && !item.getActuacionesValidadas().trim().isEmpty()) {
			if ("SINACTUACIONES".equalsIgnoreCase(item.getActuacionesValidadas().trim())) {
				sql.append(" AND F_SIGA_ACTUACIONESDESIG(D.IDINSTITUCION,D.IDTURNO,D.ANIO,D.NUMERO) IS NULL ");
			} else {
				sql.append(" AND UPPER(F_SIGA_ACTUACIONESDESIG(D.IDINSTITUCION,D.IDTURNO,D.ANIO,D.NUMERO))=UPPER('"
						+ item.getActuacionesValidadas() + "')");
			}
		}

		if (item.getJustificacionDesde() != null || item.getJustificacionHasta() != null) {
			sql.append(" AND (SELECT COUNT(*) FROM SCS_ACTUACIONDESIGNA ACT");
			sql.append(" WHERE ACT.IDINSTITUCION = D.IDINSTITUCION AND ACT.ANIO = D.ANIO ");
			sql.append(" AND ACT.IDTURNO = D.IDTURNO AND ACT.NUMERO = D.NUMERO");

			if (item.getJustificacionDesde() != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String fecha = dateFormat.format(item.getJustificacionDesde());

				sql.append(" AND TRUNC(ACT.FECHAJUSTIFICACION) >= TO_DATE('" + fecha + "','DD/MM/YYYY')");
			}

			if (item.getJustificacionHasta() != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String fecha = dateFormat.format(item.getJustificacionHasta());

				sql.append(" AND TRUNC(ACT.FECHAJUSTIFICACION) <= TO_DATE('" + fecha + "','DD/MM/YYYY')");
			}

			sql.append(" )>0");
		}

		// fechas designacion
		if (item.getDesignacionDesde() != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String fecha = dateFormat.format(item.getDesignacionDesde());

			sql.append("AND TRUNC(D.FECHAENTRADA) >= '" + fecha + "'");
		} else {
			sql.append(" AND TRUNC(D.FECHAENTRADA) > TO_DATE('01/01/1950', 'DD/MM/RRRR')");
		}

		if (item.getDesignacionHasta() != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String fecha = dateFormat.format(item.getDesignacionHasta());

			sql.append("AND D.FECHAENTRADA <= '" + fecha + "'");
		}

		// nombre y apellidos
		if (item.getApellidos() != null && !item.getApellidos().trim().isEmpty() && item.getNombre() != null
				&& !item.getNombre().trim().isEmpty()) {

			sql.append(" AND UPPER(f_siga_getdefendidosdesigna(" + idInstitucion
					+ ",D.anio,D.idturno,D.numero,1) ) like ");
			sql.append("'%" + item.getNombre().trim().toUpperCase() + "%");
			sql.append(item.getApellidos().trim().toUpperCase() + "%'");

		} else if (item.getApellidos() != null && !item.getApellidos().trim().isEmpty()
				&& (item.getNombre() == null || item.getNombre().trim().isEmpty())) {

			sql.append(" and UPPER(f_siga_getdefendidosdesigna(" + idInstitucion
					+ ",d.anio,d.idturno,d.numero,1) ) like ");
			sql.append("'%" + item.getApellidos().trim().toUpperCase() + "%'");

		} else if ((item.getApellidos() == null || item.getApellidos().isEmpty()) && item.getNombre() != null
				&& !item.getNombre().trim().isEmpty()) {

			sql.append(" AND UPPER(f_siga_getdefendidosdesigna(" + idInstitucion
					+ ",D.anio,D.idturno,D.numero,1) ) like ");
			sql.append("'%" + item.getNombre().trim().toUpperCase() + "%'");
		}

		// NCOLEGIADO
		if (idPersona != null && !idPersona.isEmpty()) {
			sql.append(" AND DL.IDPERSONA = " + idPersona);
		}

		if (item.isMuestraPendiente()) {
			sql.append(" AND D.ESTADO NOT IN ('A','F') ");
		}

		if (item.getEstado() != null) {
			sql.append(" AND D.ESTADO ='" + item.getEstado() + "'");
		}

		sql.append(" ) ALLDESIGNAS ");

		StringBuilder tiposResolucionBuilder = new StringBuilder();

		tiposResolucionBuilder.append(" WHERE (ALLDESIGNAS.NUM_TIPO_RESOLUCION_DESIGNA IN (1");

		if (!item.isRestriccionesVisualizacion()
				|| (item.getResolucionPTECAJG() != null && !"0".equals(item.getResolucionPTECAJG()))) {
			tiposResolucionBuilder.append(",3");
		}

		if (!item.isRestriccionesVisualizacion()
				|| (item.getConEJGNoFavorables() != null && !"0".equals(item.getConEJGNoFavorables()))) {
			tiposResolucionBuilder.append(",2");
		}

		if (!item.isRestriccionesVisualizacion()
				|| (item.getEjgSinResolucion() != null && !"0".equals(item.getEjgSinResolucion()))) {
			tiposResolucionBuilder.append(",4");
		}

		tiposResolucionBuilder.append(")");

		if (!item.isRestriccionesVisualizacion()
				|| (item.getEjgSinResolucion() != null && !"0".equals(item.getEjgSinResolucion()))) {
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
			sql.append(" ) ORDER BY FECHAENTRADA DESC, IDINSTITUCION, ANIO, CODIGO DESC, SUFIJO, CODIGODESIGNA DESC");
		}

		sql.append(") query WHERE rownum<=200");

		return sql.toString();
	}

	public String comboModulos(Short idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT("MODULO.IDPROCEDIMIENTO, MODULO.NOMBRE ");
		sql.FROM("SCS_PROCEDIMIENTOS MODULO");
		sql.WHERE("MODULO.IDINSTITUCION = " + idInstitucion);

		return sql.toString();
	}
	
	public String comboDelitos(DesignaItem designaItem, Short idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT("D.IDDELITO, F_SIGA_GETRECURSO(D.DESCRIPCION, 1) as DESCRIPCION ");
		sql.FROM("SCS_DELITO D ");
		sql.INNER_JOIN("SCS_DELITOSDESIGNA DD ON D.IDINSTITUCION = DD.IDINSTITUCION AND D.IDDELITO=DD.IDDELITO ");
		sql.WHERE("DD.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("DD.IDTURNO = " + designaItem.getIdTurno());

		return sql.toString();
	}

	public String comboProcedimientos(Short idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT(" DISTINCT IDPRETENSION, F_SIGA_GETRECURSO(DESCRIPCION, 1) AS NOMBRE");
		sql.FROM("SCS_PRETENSION");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);

		return sql.toString();
	}

	public String getProcedimientosJuzgados(Short idInstitucion, String idJuzgado) {

		SQL sql = new SQL();
		sql.SELECT("DISTINCT JUZGADO.IDPROCEDIMIENTO");
		sql.FROM("SCS_JUZGADOPROCEDIMIENTO JUZGADO");
		sql.WHERE("JUZGADO.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("JUZGADO.IDJUZGADO = " + idJuzgado);

		return sql.toString();
	}

	public String getProcedimientosPretension(Short idInstitucion, List<String> idProcedimientos) {

		String inSQL = "(";

		for (int i = 0; i < idProcedimientos.size(); i++) {
			if (i != idProcedimientos.size() - 1) {
				inSQL += idProcedimientos.get(i) + " ,";
			} else {
				inSQL += idProcedimientos.get(i) + ")";
			}
		}

		SQL sql = new SQL();
		sql.SELECT("DISTINCT IDPRETENSION, IDPROCEDIMIENTO");
		sql.FROM("SCS_PRETENSIONESPROCED PETRENSION");
		sql.WHERE("PETRENSION.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("PETRENSION.IDPROCEDIMIENTO IN " + inSQL);

		return sql.toString();
	}

	public String comboProcedimientosConJuzgado(Short idInstitucion, List<String> idPretensiones) {

		String inSQL = "(";

		for (int i = 0; i < idPretensiones.size(); i++) {
			if (i != idPretensiones.size() - 1) {
				inSQL += idPretensiones.get(i) + " ,";
			} else {
				inSQL += idPretensiones.get(i) + ")";
			}
		}

		SQL sql = new SQL();
		sql.SELECT("DISTINCT IDPRETENSION, F_SIGA_GETRECURSO(DESCRIPCION, 1) AS DESCRIPCION ");
		sql.FROM("SCS_PRETENSION ");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		sql.WHERE("IDPRETENSION IN " + inSQL);

		return sql.toString();
	}

	public String comboModulosConJuzgado(Short idInstitucion, List<String> procedimientosJuzgados) {

		String inSQL = "(";

		for (int i = 0; i < procedimientosJuzgados.size(); i++) {
			if (i != procedimientosJuzgados.size() - 1) {
				inSQL += procedimientosJuzgados.get(i) + " ,";
			} else {
				inSQL += procedimientosJuzgados.get(i) + ")";
			}
		}

		SQL sql = new SQL();
		sql.SELECT("DISTINCT MODULO.IDPROCEDIMIENTO, MODULO.NOMBRE ");
		sql.FROM("SCS_PROCEDIMIENTOS MODULO ");
		sql.WHERE("MODULO.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("MODULO.IDPROCEDIMIENTO IN " + inSQL);

		return sql.toString();
	}

	public String getProcedimientoPretension(Short idInstitucion, String idPretension) {

		SQL sql = new SQL();
		sql.SELECT("DISTINCT IDPROCEDIMIENTO");
		sql.FROM("SCS_PRETENSIONESPROCED PETRENSION");
		sql.WHERE("PETRENSION.IDINSTITUCION = " + idInstitucion);
		sql.WHERE(" PETRENSION.IDPRETENSION = " + idPretension);

		return sql.toString();
	}

	public String comboModulosConProcedimientos(Short idInstitucion, List<String> idPretensiones) {

		String inSQL = "(";

		for (int i = 0; i < idPretensiones.size(); i++) {
			if (i != idPretensiones.size() - 1) {
				inSQL += idPretensiones.get(i) + " ,";
			} else {
				inSQL += idPretensiones.get(i) + ")";
			}
		}

		SQL sql = new SQL();
		sql.SELECT("DISTINCT MODULO.IDPROCEDIMIENTO, MODULO.NOMBRE ");
		sql.FROM("SCS_PROCEDIMIENTOS MODULO ");
		sql.WHERE("MODULO.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("MODULO.IDPROCEDIMIENTO IN " + inSQL);

		return sql.toString();
	}

	public String getPretensionModulo(Short idInstitucion, String idModulo) {

		SQL sql = new SQL();
		sql.SELECT("DISTINCT PETRENSION.IDPRETENSION ");
		sql.FROM("SCS_PRETENSIONESPROCED PETRENSION");
		sql.WHERE("PETRENSION.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("PETRENSION.IDPROCEDIMIENTO = " + idModulo);

		return sql.toString();
	}

	public String comboProcedimientosConModulos(Short idInstitucion, List<String> idPretensiones) {

		String inSQL = "(";

		for (int i = 0; i < idPretensiones.size(); i++) {
			if (i != idPretensiones.size() - 1) {
				inSQL += idPretensiones.get(i) + " ,";
			} else {
				inSQL += idPretensiones.get(i) + ")";
			}
		}

		SQL sql = new SQL();
		sql.SELECT("DISTINCT IDPRETENSION, F_SIGA_GETRECURSO(DESCRIPCION, 1) AS DESCRIPCION ");
		sql.FROM("SCS_PRETENSION ");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		sql.WHERE("IDPROCEDIMIENTO IN " + inSQL);

		return sql.toString();
	}

	public static String prepararSentenciaCompletaBind(String cadena, String Campo, int contador, Hashtable codigos) {
		String temp = "";
		String sentenciaCompleta = "";
		/*
		 * La cadena introducida se pasa a mayusculas y se eliminan los blancos por la
		 * derecha y por la izquierda
		 */
		// cadena = cadena.trim();
		cadena = validateChars(cadena);

		if (hasComodin(cadena)) {

			codigos.put(new Integer(contador), convertir(cadena));
			temp = " LIKE :" + contador + " ";
			sentenciaCompleta = Campo + temp;
		} else {

			codigos.put(new Integer(contador), cadena);
			sentenciaCompleta = "regexp_like(" + Campo + ",:" + contador + ")";

		}

		return sentenciaCompleta;
	}

	public static String validateChars(Object cad) {
		cad = replacePattern(replaceBlanks(cad.toString()), "'", "''");
		cad = replacePattern(replaceBlanks(cad.toString()), "(", "\\(");
		cad = replacePattern(replaceBlanks(cad.toString()), ")", "\\)");
		return cad.toString();
	}

	private static String replaceBlanks(String str) {
		byte[] aux = str.trim().getBytes();
		int j = 0;
		for (int i = 0; i < aux.length;) {
			aux[j++] = aux[i];
			if (aux[i++] == ' ')
				while (aux[i] == ' ')
					i++;
		}
		return new String(aux, 0, j);
	}

	private static String replacePattern(String str, String pattern, String replace) {
		int s = 0;
		int e = 0;
		StringBuffer result = new StringBuffer();

		while ((e = str.indexOf(pattern, s)) >= 0) {
			result.append(str.substring(s, e));
			result.append(replace);
			s = e + pattern.length();
		}
		result.append(str.substring(s));
		return result.toString();
	}

	public static boolean hasComodin(String cadena) {
		if (cadena == null)
			return false;
		int posicion = 0; // posición del carácter en la cadena
		posicion = cadena.indexOf('*') + 1; // -1 + 1 si no está
		posicion = posicion + cadena.indexOf('?') + 1; // -1 + 1 si no está
		return (posicion != 0); // si cero, falso
	}

	public static boolean hasComa(String cadena) {
		if (cadena == null)
			return false;
		return cadena.indexOf(',') >= 0;
	}

	public static boolean hasGuion(String cadena) {
		if (cadena == null)
			return false;
		return cadena.indexOf('-') >= 0; // si cero, falso
	}

	public static String convertir(String cadena) {
		String temp = "";
		temp = cadena.replace('*', '%');
		temp = temp.replace('?', '_');
		temp = temp.toUpperCase();
		return temp;
	}

	public static String prepararSentenciaNIFBind(String cadena, String campo, int contador, Hashtable codigos) {
		String cadenaTemp = "";
		String campoNIF = campo;
		cadena = cadena.trim();
		/*
		 * if (cadena.length()<9){ for (int i=0; i<9-cadena.length();i++){
		 * cerosAux=cerosAux+ceros; if (i==0){
		 * cadenaTemp=" ("+campoNIF+" LIKE UPPER('"+cadena+"%')"; }
		 * cadenaTemp+=" OR "+campoNIF+" LIKE UPPER('"+cerosAux+cadena+"%')"; } } else
		 * cadenaTemp=" ("+campoNIF+" LIKE UPPER('"+cadena+"%')"; cadenaTemp+=")";
		 */
		cadena = validateChars(cadena);

		codigos.put(new Integer(contador), cadena + "%");
		cadenaTemp += " LTRIM(UPPER(" + campoNIF + "),'0') LIKE LTRIM(UPPER(" + contador + "),'0') ";
		// cadenaTemp="regexp_like ("+campoNIF+",'^[0]{0,8}:"+contador+"\\w*$')";

		return cadenaTemp;
	}

	public static Vector dateBetweenDesdeAndHastaBind(String nombreColumna, String fechaDesde, String fechaHasta,
			int contador, Hashtable codigos) {
		Vector resultV = new Vector();
		String result = "";
		// SimpleDateFormat sdf = new SimpleDateFormat(ClsConstants.DATE_FORMAT_JAVA);

		try {
			String fechaIni = fechaDesde;
			String fechaFin = fechaHasta;

			/*
			 * JPT: - No entiendo para que le suma un dia y luego hace un trunc - Si le suma
			 * un dia y hace menor o igual por fecha y hora todavia puede tener sentido
			 * 
			 * if (fechaFin != null && !fechaFin.trim().equals("")) { Date d =
			 * sdf.parse(fechaFin); d.setTime(d.getTime() + ClsConstants.DATE_MORE);
			 * fechaFin = (sdf.format(d)); }
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

	public String busquedaProcurador(String num, String idinstitucion, String idturno) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();

		sql2.SELECT(
				"p.ncolegiado, p.nombre, p.apellidos1, p.apellidos2, dp.numerodesignacion, dp.fechadesigna, dp.observaciones, dp.motivosrenuncia, dp.fecharenunciasolicita");

		sql2.FROM("SCS_DESIGNAPROCURADOR dp, SCS_PROCURADOR p");
		sql2.WHERE("dp.idinstitucion = " + idinstitucion);
		//sql2.WHERE("dp.idturno = " + idturno);
		//sql2.WHERE("dp.numero =" + num);
		sql2.WHERE("dp.idprocurador = p.idprocurador");
		sql2.WHERE("dp.idinstitucion = p.idinstitucion");
		sql2.ORDER_BY("dp.FECHADESIGNA DESC");

		sql.SELECT("*");
		sql.FROM("( " + sql2.toString() + " )");
		sql.WHERE("ROWNUM <= 200");

		return sql.toString();
	}

	public String comboTipoMotivo(Short institucion, String idLenguaje) {
		SQL sql = new SQL();

		sql.SELECT("IDTIPOMOTIVO, F_SIGA_GETRECURSO(E.DESCRIPCION, "+idLenguaje+") as Descripcion");
		sql.FROM("SCS_TIPOMOTIVO E");
		sql.ORDER_BY("Descripcion");

		return sql.toString();
	}

	public String nuevoProcurador(ProcuradorItem procuradorItem, Integer usuario) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		SQL sql = new SQL();
		sql.INSERT_INTO("SCS_DESIGNAPROCURADOR");

		if (procuradorItem.getNombre() != null) {
			sql.VALUES("nombre", "'" + procuradorItem.getNombre() + "'");
		}
		if (procuradorItem.getApellido1() != null) {
			sql.VALUES("apellidos1", "'" + procuradorItem.getApellido1() + "'");
		}
		if (procuradorItem.getApellido2() != null) {
			sql.VALUES("apellidos2", "'" + procuradorItem.getApellido2() + "'");
		}
		if (procuradorItem.getnColegiado() != null) {
			sql.VALUES("ncolegiado", "'" + procuradorItem.getnColegiado() + "'");
		}
		if (procuradorItem.getNumerodesignacion() != null) {
			sql.VALUES("numerodesignacion", "'" + procuradorItem.getNumerodesignacion() + "'");
		}
		if (procuradorItem.getFechaDesigna() != null) {
			sql.VALUES("fechadesigna", "'" + procuradorItem.getFechaDesigna() + "'");
		}
		if (procuradorItem.getMotivosRenuncia() != null) {
			sql.VALUES("motivorenuncia", "'" + procuradorItem.getMotivosRenuncia() + "'");
		}
		if (procuradorItem.getObservaciones() != null) {
			sql.VALUES("observaciones", "'" + procuradorItem.getObservaciones() + "'");
		}

		sql.VALUES("usumodificacion", "'" + usuario + "'");
		sql.VALUES("fechamodificacion", "'" + procuradorItem.getFechaModificacion() + "'");

		return sql.toString();

	}

	public String busquedaActDesigna(ActuacionDesignaRequestDTO actuacionDesignaRequestDTO, String idInstitucion) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();

		sql2.SELECT("ACT.FECHA AS FECHAACTUACION");
		sql2.SELECT("ACT.NUMEROASUNTO");
		sql2.SELECT("ACT.NUMERO");
		sql2.SELECT("ACT.IDTURNO");
		sql2.SELECT("ACT.ANIO");
		sql2.SELECT("ACT.FACTURADO");
		sql2.SELECT("PRO.NOMBRE AS MODULO");
		sql2.SELECT("ACR.DESCRIPCION AS ACREDITACION");
		sql2.SELECT("ACT.FECHAJUSTIFICACION AS JUSTIFICACION");
		sql2.SELECT("ACT.VALIDADA");
		sql2.SELECT("ACT.ANULACION AS ANULADA");
		sql2.SELECT("FAC.NOMBRE AS FACTURACION");
		sql2.SELECT("PER.APELLIDOS2 || ' ' || PER.APELLIDOS1 || ', ' || PER.NOMBRE AS LETRADO");
		sql2.SELECT("ACT.NIG");
		sql2.SELECT("ACT.NUMEROPROCEDIMIENTO");
		sql2.SELECT("ACT.IDJUZGADO");
		sql2.SELECT("JUZ.NOMBRE AS NOMBREJUZGADO");
		sql2.SELECT("ACT.IDPROCEDIMIENTO");
		sql2.SELECT("ACT.IDPRETENSION");
		sql2.SELECT("ACT.IDACREDITACION");
		sql2.SELECT("ACT.IDPRISION");
		sql2.SELECT("ACT.OBSERVACIONES");
		sql2.SELECT("ACT.OBSERVACIONESJUSTIFICACION");
		sql2.SELECT("ACT.TALONARIO");
		sql2.SELECT("ACT.TALON");
		sql2.SELECT("DECODE(COL.COMUNITARIO, '1', COL.NCOMUNITARIO, COL.NCOLEGIADO) AS NUMCOLEGIADO");
		sql2.SELECT("ACT.IDPERSONACOLEGIADO");
		sql2.SELECT("ACT.ID_MOTIVO_CAMBIO");
		sql2.SELECT("ACT.IDFACTURACION");
		sql2.SELECT("ACT.USUCREACION");
		sql2.SELECT("ACT.FECHACREACION");
		sql2.SELECT("ACT.USUMODIFICACION");
		sql2.SELECT("ACT.FECHAMODIFICACION");
		sql2.SELECT("ACT.USUJUSTIFICACION");
		sql2.SELECT("ACT.FECHAUSUJUSTIFICACION");
		sql2.SELECT("ACT.USUVALIDACION");
		sql2.SELECT("ACT.FECHAVALIDACION");

		sql2.FROM("SCS_ACTUACIONDESIGNA ACT");

		sql2.JOIN("SCS_ACREDITACION ACR ON ACR.IDACREDITACION = ACT.IDACREDITACION "
				+ "LEFT JOIN FCS_FACTURACIONJG FAC ON FAC.IDFACTURACION = ACT.IDFACTURACION "
				+ "LEFT JOIN SCS_JUZGADO JUZ ON JUZ.IDJUZGADO = ACT.IDJUZGADO AND JUZ.IDINSTITUCION = ACT.IDINSTITUCION "
				+ "LEFT JOIN SCS_PROCEDIMIENTOS PRO ON PRO.IDPROCEDIMIENTO = ACT.IDPROCEDIMIENTO AND PRO.IDINSTITUCION = ACT.IDINSTITUCION AND PRO.IDINSTITUCION = ACT.IDINSTITUCION "
				+ "LEFT JOIN CEN_COLEGIADO COL ON COL.IDPERSONA = ACT.IDPERSONACOLEGIADO AND COL.IDINSTITUCION = ACT.IDINSTITUCION "
				+ "LEFT JOIN CEN_PERSONA PER ON PER.IDPERSONA = COL.IDPERSONA");

		if (!actuacionDesignaRequestDTO.isHistorico()) {
			sql2.WHERE("ACT.ANULACION = 0");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaRequestDTO.getIdPersonaColegiado())) {
			sql2.WHERE("ACT.IDPERSONACOLEGIADO = '" + actuacionDesignaRequestDTO.getIdPersonaColegiado() + "'");
		}
		
		if (!UtilidadesString.esCadenaVacia(actuacionDesignaRequestDTO.getNumeroAsunto())) {
			sql2.WHERE("ACT.NUMEROASUNTO = '" + actuacionDesignaRequestDTO.getNumeroAsunto() + "'");
		}

		sql2.WHERE("ACT.IDINSTITUCION = '" + idInstitucion + "'");
		sql2.WHERE("ACT.IDTURNO = '" + actuacionDesignaRequestDTO.getIdTurno() + "'");
		sql2.WHERE("ACT.ANIO = '" + actuacionDesignaRequestDTO.getAnio() + "'");
		sql2.WHERE("ACT.NUMERO = '" + actuacionDesignaRequestDTO.getNumero() + "'");

		sql2.ORDER_BY("FECHAACTUACION DESC, NUMEROASUNTO DESC");

		sql.SELECT("*");
		sql.FROM("( " + sql2.toString() + " )");
		sql.WHERE("ROWNUM <= 201");

		return sql.toString();
	}

	public String getNewIdActuDesigna(ActuacionDesignaItem actuacionDesignaItem, Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("NVL(MAX(ACT.NUMEROASUNTO),0) +1 AS ID");

		sql.FROM("SCS_ACTUACIONDESIGNA ACT");

		sql.WHERE("ACT.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("ACT.IDTURNO = '" + actuacionDesignaItem.getIdTurno() + "'");
		sql.WHERE("ACT.ANIO = '" + actuacionDesignaItem.getAnio() + "'");
		sql.WHERE("ACT.NUMERO = '" + actuacionDesignaItem.getNumero() + "'");

		return sql.toString();
	}
	
	    public String busquedaListaContrarios(DesignaItem item, Short idInstitucion, Boolean historico) {
	    	
	    	String consulta ="SELECT\r\n" + 
	    			"    t1.numero,\r\n" + 
	    			"    t1.idinstitucion,\r\n" + 
	    			"    t1.idturno,\r\n" + 
	    			"    t1.anio,\r\n" + 
	    			"    t1.nif,\r\n" + 
	    			"    t1.idabogadocontrario,\r\n" + 
	    			"    t1.idprocurador,\r\n" + 
	    			"    t1.fechabaja,\r\n" +
	    			"    t1.idpersona,\r\n" + 
	    			"    t1.idrepresentantelegal,\r\n" + 
	    			"    CASE\r\n" + 
	    			"        WHEN t1.idabogadocontrario IS NOT NULL THEN\r\n" + 
	    			"            (\r\n" + 
	    			"                SELECT\r\n" + 
	    			"                    col.ncolegiado\r\n" + 
	    			"                    || ', '\r\n" + 
	    			"                    || t1.NOMBREABOGADOCONTRARIO\r\n" + 
	    			"--                    || per.apellidos1\r\n" + 
	    			"--                    || ' '\r\n" + 
	    			"--                    || per.apellidos2\r\n" + 
	    			"--                    || ', '\r\n" + 
	    			"--                    || per.nombre \r\n" + 
	    			"                    AS abogado\r\n" + 
	    			"                FROM\r\n" + 
	    			"                    cen_persona     per,\r\n" + 
	    			"                    cen_colegiado   col\r\n" + 
	    			"                WHERE\r\n" + 
	    			"                    ( per.idpersona = col.idpersona\r\n" + 
	    			"                      AND per.idpersona = t1.idabogadocontrario\r\n" + 
	    			"                      AND col.idinstitucion = t1.idinstitucion )\r\n" + 
	    			"            )\r\n" + 
	    			"        ELSE\r\n" + 
	    			"            ''\r\n" + 
	    			"    END AS abogado,\r\n" + 
	    			"    CASE\r\n" + 
	    			"        WHEN t1.idprocurador IS NOT NULL THEN\r\n" + 
	    			"            (\r\n" + 
	    			"                SELECT\r\n" + 
	    			"                    procu.ncolegiado\r\n" + 
	    			"                    || ', '\r\n" + 
	    			"                    || procu.apellidos1\r\n" + 
	    			"                    || ' '\r\n" + 
	    			"                    || procu.apellidos2\r\n" + 
	    			"                    || ', '\r\n" + 
	    			"                    || procu.nombre AS procurador\r\n" + 
	    			"                FROM\r\n" + 
	    			"                    scs_procurador procu\r\n" + 
	    			"                WHERE\r\n" + 
	    			"                    ( idprocurador = t1.idprocurador\r\n" + 
	    			"                      AND idinstitucion = t1.IDINSTITUCION_PROCU )\r\n" + 
	    			"            )\r\n" + 
	    			"        ELSE\r\n" + 
	    			"            ''\r\n" + 
	    			"    END AS procurador,\r\n" + 
	    			"    t1.apellidosnombre\r\n" + 
	    			"FROM\r\n" + 
	    			"    (\r\n" + 
	    			"        SELECT\r\n" + 
	    			"            scs_contrariosdesigna.numero,\r\n" + 
	    			"            scs_contrariosdesigna.idinstitucion,\r\n" + 
	    			"            scs_contrariosdesigna.idturno,\r\n" + 
	    			"            scs_contrariosdesigna.anio,\r\n" + 
	    			"            scs_contrariosdesigna.idpersona,\r\n" + 
	    			"            scs_contrariosdesigna.idrepresentantelegal,\r\n" + 
	    			"            scs_contrariosdesigna.NOMBREABOGADOCONTRARIO,\r\n" + 
	    			"            scs_contrariosdesigna.fechabaja,\r\n" + 
	    			"            persona.nif,\r\n" + 
	    			"            persona.apellido1\r\n" + 
	    			"            || decode(persona.apellido2, NULL, '', ' ' || persona.apellido2)\r\n" + 
	    			"            || ', '\r\n" + 
	    			"            || persona.nombre AS apellidosnombre,\r\n" + 
	    			"            scs_contrariosdesigna.idabogadocontrario,\r\n" + 
	    			"            scs_contrariosdesigna.idprocurador,\r\n" + 
	    			"            scs_contrariosdesigna.IDINSTITUCION_PROCU\r\n" + 
	    			"        FROM\r\n" + 
	    			"            scs_contrariosdesigna\r\n" + 
	    			"            JOIN scs_personajg persona ON persona.idpersona = scs_contrariosdesigna.idpersona\r\n" + 
	    			"                                          AND persona.idinstitucion = scs_contrariosdesigna.idinstitucion\r\n" + 
	    			"        WHERE\r\n" + 
	    			"            ( scs_contrariosdesigna.anio = "+item.getAno()+"\r\n" + 
	    			"              AND scs_contrariosdesigna.numero = "+item.getNumero()+"\r\n" + 
	    			"              AND scs_contrariosdesigna.idinstitucion = "+idInstitucion+"\r\n";
	    			if(!historico) {
	    				consulta+=" AND scs_contrariosdesigna.fechabaja is null \r\n";
	    			}
	    			consulta+="              AND scs_contrariosdesigna.idturno = "+item.getIdTurno()+" )\r\n" + 
	    			"    ) t1\r\n" + 
	    			"\r\n" + 
	    			"";
	    	
	    	return consulta;
	    }
	    
		public String busquedaListaInteresados(DesignaItem item, Short idInstitucion) {
			    	
			SQL sql = new SQL();
			
			sql.SELECT("SCS_DEFENDIDOSDESIGNA.numero");
			sql.SELECT("SCS_DEFENDIDOSDESIGNA.idinstitucion");
			sql.SELECT("SCS_DEFENDIDOSDESIGNA.idturno");
			sql.SELECT("SCS_DEFENDIDOSDESIGNA.anio");
			sql.SELECT("SCS_DEFENDIDOSDESIGNA.idpersona");
			sql.SELECT("SCS_DEFENDIDOSDESIGNA.numero");
			sql.SELECT("persona.nif");
			sql.SELECT("persona.direccion");
			sql.SELECT("    CASE\r\n" + 
			    			"        WHEN nombrerepresentante IS NOT NULL THEN\r\n" + 
			    			"            nombrerepresentante\r\n" + 
			    			"        ELSE\r\n" + 
			    			"            ''\r\n" + 
			    			"    END AS representante\r\n");
			sql.SELECT("            persona.apellido1\r\n" + 
	    			"            || decode(persona.apellido2, NULL, '', ' ' || persona.apellido2)\r\n" + 
	    			"            || ', '\r\n" + 
	    			"            || persona.nombre AS apellidosnombre");
			sql.FROM("SCS_DEFENDIDOSDESIGNA");
			sql.JOIN("scs_personajg persona ON persona.idpersona = scs_DEFENDIDOSDESIGNA.idpersona AND persona.idinstitucion = scs_DEFENDIDOSDESIGNA.idinstitucion");
			sql.WHERE("            ( scs_DEFENDIDOSDESIGNA.anio = "+item.getAno()+"\r\n" + 
			"              AND scs_DEFENDIDOSDESIGNA.numero = "+item.getNumero()+"\r\n" + 
			"              AND scs_DEFENDIDOSDESIGNA.idinstitucion = "+idInstitucion+"\r\n" + 
			"              AND scs_DEFENDIDOSDESIGNA.idturno = "+item.getIdTurno()+" )\r\n");
	
	    	
	    	return sql.toString();
	    }

	public String anularReactivarActDesigna(ActuacionDesignaItem actuacionDesignaItem, Short idInstitucion, AdmUsuarios usuario, boolean anular) {
		SQL sql = new SQL();
		String anulReact = anular ? "1" : "0";

		sql.UPDATE("SCS_ACTUACIONDESIGNA");

		sql.SET("ANULACION = '" + anulReact + "'");
		sql.SET("USUMODIFICACION = '" + usuario.getIdusuario() + "'");
		sql.SET("FECHAMODIFICACION = SYSTIMESTAMP");

		sql.WHERE("NUMERO = '" + actuacionDesignaItem.getNumero() + "'");
		sql.WHERE("IDTURNO = '" + actuacionDesignaItem.getIdTurno() + "'");
		sql.WHERE("ANIO = '" + actuacionDesignaItem.getAnio() + "'");
		sql.WHERE("NUMEROASUNTO = '" + actuacionDesignaItem.getNumeroAsunto() + "'");
		sql.WHERE("IDINSTITUCION = '" +idInstitucion + "'");

		return sql.toString();
	}

	public String validarDesvalidarActDesigna(ActuacionDesignaItem actuacionDesignaItem, String idInstitucion,
			AdmUsuarios usuario, boolean validar) {
		SQL sql = new SQL();
		String validarReact = validar ? "1" : "0";

		sql.UPDATE("SCS_ACTUACIONDESIGNA");

		sql.SET("VALIDADA = '" + validarReact + "'");
		sql.SET("USUMODIFICACION = '" + usuario.getIdusuario() + "'");
		sql.SET("FECHAMODIFICACION = SYSTIMESTAMP");
		
		if(validar && !UtilidadesString.esCadenaVacia(actuacionDesignaItem.getFechaJustificacion())) {
			sql.SET("FECHAJUSTIFICACION = '" + actuacionDesignaItem.getFechaJustificacion() + "'");
		}
		
		if(validar) {
			sql.SET("FECHAVALIDACION = SYSDATE");
			sql.SET("USUVALIDACION = '" + usuario.getIdusuario() + "'");
		}

		sql.WHERE("NUMERO = '" + actuacionDesignaItem.getNumero() + "'");
		sql.WHERE("IDTURNO = '" + actuacionDesignaItem.getIdTurno() + "'");
		sql.WHERE("ANIO = '" + actuacionDesignaItem.getAnio() + "'");
		sql.WHERE("NUMEROASUNTO = '" + actuacionDesignaItem.getNumeroAsunto() + "'");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}
	
	public String compruebaProcurador(String num, String anio) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();

		sql2.SELECT("*");

		sql2.FROM("SCS_EJGDESIGNA");
		sql2.WHERE("NUMERODESIGNA = "+num);
		sql2.WHERE("ANIODESIGNA ="+anio);

		sql.SELECT("*");
		sql.FROM("( " + sql2.toString() + " )");
		sql.WHERE("ROWNUM <= 200");

		return sql.toString();
	}
	
	public String compruebaFechaProcurador(String fecha, String numAnio) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();

		sql2.SELECT(
				"p.ncolegiado, p.nombre, p.apellidos1, p.apellidos2, dp.numerodesignacion, dp.fechadesigna, dp.observaciones, dp.motivosrenuncia, dp.fecharenunciasolicita");

		sql2.FROM("SCS_DESIGNAPROCURADOR dp, SCS_PROCURADOR p");
		//sql2.WHERE("dp.numerodesigna = " + numAnio);
		sql2.WHERE("dp.fechadesigna =" + fecha);
		sql2.WHERE("dp.idprocurador = p.idprocurador");
		sql2.WHERE("dp.idinstitucion = p.idinstitucion");
		sql2.ORDER_BY("dp.FECHADESIGNA DESC");

		sql.SELECT("*");
		sql.FROM("( " + sql2.toString() + " )");
		sql.WHERE("ROWNUM <= 200");

		return sql.toString();
	}
	
	public String guardarProcurador(ProcuradorItem procuradorItem) {
		
		SQL sql = new SQL();
		sql.UPDATE("scs_designaprocurador");
		
		sql.SET("fechadesigna ='"+procuradorItem.getFechaDesigna()+"'");
		sql.SET("numerodesignacion ='"+procuradorItem.getNumerodesignacion()+"'");
		sql.SET("motivosrenuncia ='"+procuradorItem.getMotivosRenuncia()+"'");

		sql.WHERE("numero= "+procuradorItem.getNumero());
		sql.WHERE("idinstitucion="+procuradorItem.getIdInstitucion());
		sql.WHERE("idturno="+procuradorItem.getIdTurno());
		
		return sql.toString();
	}
	
	public String actualizarProcurador(ProcuradorItem procuradorItem) {
		
		SQL sql = new SQL();
		sql.UPDATE("scs_designaprocurador");
		
		sql.SET("fechadesigna ='"+procuradorItem.getFechaDesigna()+"'");
		sql.SET("numerodesignacion ='"+procuradorItem.getNumerodesignacion()+"'");
		sql.SET("motivosrenuncia ='"+procuradorItem.getMotivosRenuncia()+"'");

		sql.WHERE("numero= "+procuradorItem.getNumero());
		sql.WHERE("idinstitucion="+procuradorItem.getIdInstitucion());
		sql.WHERE("idturno="+procuradorItem.getIdTurno());
		
		return sql.toString();
	}

	public String eliminarActDesigna(ActuacionDesignaItem actuacionDesignaItem, Short idInstitucion, AdmUsuarios usuario) {
		SQL sql = new SQL();

		sql.DELETE_FROM("SCS_ACTUACIONDESIGNA");

		sql.WHERE("NUMERO = '" + actuacionDesignaItem.getNumero() + "'");
		sql.WHERE("IDTURNO = '" + actuacionDesignaItem.getIdTurno() + "'");
		sql.WHERE("ANIO = '" + actuacionDesignaItem.getAnio() + "'");
		sql.WHERE("NUMEROASUNTO = '" + actuacionDesignaItem.getNumeroAsunto() + "'");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}

	public String guardarActDesigna(ActuacionDesignaItem actuacionDesignaItem, String idInstitucion,
			AdmUsuarios usuario) {

		SQL sql = new SQL();

		sql.INSERT_INTO("SCS_ACTUACIONDESIGNA");

		sql.VALUES("IDINSTITUCION", "'" + idInstitucion + "'");
		sql.VALUES("IDTURNO", "'" + actuacionDesignaItem.getIdTurno() + "'");
		sql.VALUES("ANIO", "'" + actuacionDesignaItem.getAnio() + "'");
		sql.VALUES("NUMERO", "'" + actuacionDesignaItem.getNumero() + "'");
		sql.VALUES("FECHA", "'" + actuacionDesignaItem.getFechaActuacion() + "'");
		sql.VALUES("NUMEROASUNTO", "'" + actuacionDesignaItem.getNumeroAsunto() + "'");
		sql.VALUES("ACUERDOEXTRAJUDICIAL", "'0'");
		sql.VALUES("ANULACION", "'0'");
		sql.VALUES("FECHAMODIFICACION", "SYSDATE");
		sql.VALUES("USUMODIFICACION", "'" + usuario.getIdusuario() + "'");
		sql.VALUES("IDPROCEDIMIENTO", "'" + actuacionDesignaItem.getIdProcedimiento() + "'");
		sql.VALUES("IDINSTITUCION_PROC", "'" + idInstitucion + "'");
		sql.VALUES("USUCREACION", "'" + usuario.getIdusuario() + "'");
		sql.VALUES("FECHACREACION", "SYSDATE");

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdPersonaColegiado())) {
			sql.VALUES("IDPERSONACOLEGIADO", "'" + actuacionDesignaItem.getIdPersonaColegiado() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getObservaciones())) {
			sql.VALUES("OBSERVACIONES", "'" + actuacionDesignaItem.getObservaciones() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getTalonario())) {
			sql.VALUES("TALONARIO", "'" + actuacionDesignaItem.getTalonario() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getTalon())) {
			sql.VALUES("TALON", "'" + actuacionDesignaItem.getTalon() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getNig())) {
			sql.VALUES("NIG", "'" + actuacionDesignaItem.getNig() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getNumProcedimiento())) {
			sql.VALUES("NUMEROPROCEDIMIENTO", "'" + actuacionDesignaItem.getNumProcedimiento() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdJuzgado())) {
			sql.VALUES("IDJUZGADO", "'" + actuacionDesignaItem.getIdJuzgado() + "'");
		}
		
		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdMotivoCambio())) {
			sql.VALUES("ID_MOTIVO_CAMBIO", "'" + actuacionDesignaItem.getIdMotivoCambio() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdPretension())) {
			sql.VALUES("IDPRETENSION", "'" + actuacionDesignaItem.getIdPretension() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdAcreditacion())) {
			sql.VALUES("IDACREDITACION", "'" + actuacionDesignaItem.getIdAcreditacion() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdPrision())) {
			sql.VALUES("IDPRISION", "'" + actuacionDesignaItem.getIdPrision() + "'");
		}

		return sql.toString();

	}
	
	public String actualizarActDesigna(ActuacionDesignaItem actuacionDesignaItem, String idInstitucion,
			AdmUsuarios usuario) {

		SQL sql = new SQL();

		sql.UPDATE("SCS_ACTUACIONDESIGNA");
		
		if (!UtilidadesString.esCadenaVacia(idInstitucion)) {
			sql.SET("IDINSTITUCION = '" + idInstitucion + "'");
		}
		
		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getFechaActuacion())) {
			sql.SET("FECHA = '" + actuacionDesignaItem.getFechaActuacion() + "'");
		}
		
		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdPersonaColegiado())) {
			sql.SET("IDPERSONACOLEGIADO = '" + actuacionDesignaItem.getIdPersonaColegiado() + "'");
		}
		
		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getObservaciones())) {
			sql.SET("OBSERVACIONES = '" + actuacionDesignaItem.getObservaciones() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getTalonario())) {
			sql.SET("TALONARIO = '" + actuacionDesignaItem.getTalonario() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getTalon())) {
			sql.SET("TALON = '" + actuacionDesignaItem.getTalon() + "'");
		}
		

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getNig())) {
			sql.SET("NIG = '" + actuacionDesignaItem.getNig() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getNumProcedimiento())) {
			sql.SET("NUMEROPROCEDIMIENTO = '" + actuacionDesignaItem.getNumProcedimiento() + "'");
		}
		
		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdJuzgado())) {
			sql.SET("IDJUZGADO = '" + actuacionDesignaItem.getIdJuzgado() + "'");
		}
		
		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdMotivoCambio())) {
			sql.SET("ID_MOTIVO_CAMBIO = '" + actuacionDesignaItem.getIdMotivoCambio() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdProcedimiento())) {
			sql.SET("IDPROCEDIMIENTO = '" + actuacionDesignaItem.getIdProcedimiento() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdPretension())) {
			sql.SET("IDPRETENSION = '" + actuacionDesignaItem.getIdPretension() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdAcreditacion())) {
			sql.SET("IDACREDITACION = '" + actuacionDesignaItem.getIdAcreditacion() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getIdPrision())) {
			sql.SET("IDPRISION = '" + actuacionDesignaItem.getIdPrision() + "'");
		}

		sql.SET("FECHAMODIFICACION = SYSDATE");
		sql.SET("USUMODIFICACION = '" + usuario.getIdusuario() + "'");
		
		sql.WHERE("NUMERO = '" + actuacionDesignaItem.getNumero() + "'");
		sql.WHERE("IDTURNO = '" + actuacionDesignaItem.getIdTurno() + "'");
		sql.WHERE("ANIO = '" + actuacionDesignaItem.getAnio() + "'");
		sql.WHERE("NUMEROASUNTO = '" + actuacionDesignaItem.getNumeroAsunto() + "'");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();

	}
	
	public String existeDesginaJuzgadoProcedimiento(Short idInstitucion, DesignaItem designa) {

		SQL sql = new SQL();
		sql.SELECT("count(*) as num");
		sql.FROM("SCS_DESIGNA");
		sql.WHERE("IDJUZGADO = '" + designa.getIdJuzgado() + "'");
		sql.WHERE("NUMPROCEDIMIENTO = '" + designa.getNumProcedimiento() + "'");

		return sql.toString();
	}

	public String getDatosAdicionales(Short idInstitucion, Integer tamMax, DesignaItem designa) {

		SQL sql = new SQL();
		sql.SELECT("FECHAOFICIOJUZGADO, DELITOS, FECHARECEPCIONCOLEGIO, OBSERVACIONES, FECHAJUICIO, DEFENSAJURIDICA");
		sql.FROM("SCS_DESIGNA");
		sql.WHERE("NUMERO = '" + designa.getNumero() + "'");
		sql.WHERE("IDTURNO = '" + designa.getIdTurno() + "'");
		sql.WHERE("ANIO = '" + designa.getAno() + "'");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}
	
	public String  comboAcreditacionesPorModulo(Short idInstitucion, String idModulo) {
		SQL sql = new SQL();
		
		sql.SELECT("a.idacreditacion || ',' || a.nig_numprocedimiento AS id");
		sql.SELECT("acred.descripcion || ' (' || DECODE(TO_CHAR(a.porcentaje), TO_CHAR(trunc(a.porcentaje) ), "
				+ "TO_CHAR(a.porcentaje), f_siga_formatonumero(TO_CHAR(a.porcentaje), 2)) || '%)' AS descripcion");
	    sql.SELECT("IDTIPOACREDITACION");
	
	    sql.FROM("scs_acreditacionprocedimiento a, scs_acreditacion acred");
	    sql.WHERE("a.idprocedimiento = "+idModulo+" and a.idinstitucion = "+idInstitucion);
	    sql.WHERE("a.idacreditacion = acred.idacreditacion");
	    sql.ORDER_BY("descripcion");
		
		return sql.toString();
	}


	public String busquedaRelaciones(String anio, String num ,String idTurno, String idinstitucion) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();

		sql2.SELECT("TRIM('ASISTENCIA') sjcs,\r\n"
				+ "	            idinstitucion        idinstitucion,\r\n"
				+ "	            anio                 anio,\r\n"
				+ "	            numero               numero,\r\n"
				+ "	            idpersonacolegiado   idletrado,\r\n"
				+ "	            to_char(idturno) idturno,\r\n"
				+ "	            to_char(designa_turno) idturnodesigna,\r\n"
				+ "	            to_char(idtipoasistencia) idtipo,\r\n"
				+ "	            to_char(numero) codigo, (\r\n"
				+ "	                SELECT\r\n"
				+ "	                    abreviatura\r\n"
				+ "	                FROM\r\n"
				+ "	                    scs_turno\r\n"
				+ "	                WHERE\r\n"
				+ "	                    idturno = scs_asistencia.idturno\r\n"
				+ "	                    AND idinstitucion = scs_asistencia.idinstitucion\r\n"
				+ "	            ) des_turno,\r\n"
				+ "	            (\r\n"
				+ "	                SELECT\r\n"
				+ "	                    f_siga_getrecurso(descripcion, 1)\r\n"
				+ "	                FROM\r\n"
				+ "	                    scs_tipoasistencia\r\n"
				+ "	                WHERE\r\n"
				+ "	                    scs_tipoasistencia.idtipoasistencia = scs_asistencia.idtipoasistencia\r\n"
				+ "	            ) des_tipo\r\n"
				+ "	        FROM\r\n"
				+ "	            scs_asistencia\r\n"
				+ "	        WHERE\r\n"
				+ "	            designa_anio = 2020\r\n"
				+ "	            AND designa_numero = 1\r\n"
				+ "	            AND designa_turno = 4024\r\n"
				+ "	            AND idinstitucion = 2005\r\n"
				+ "	        UNION\r\n"
				+ "	        SELECT\r\n"
				+ "	            TRIM('EJG') sjcs,\r\n"
				+ "	            e.idinstitucion   idinstitucion,\r\n"
				+ "	            e.anio            anio,\r\n"
				+ "	            e.numero          numero,\r\n"
				+ "	            e.idpersona       idletrado,\r\n"
				+ "	            to_char(e.guardiaturno_idturno) idturno,\r\n"
				+ "	            to_char(ed.idturno) idturnodesigna,\r\n"
				+ "	            to_char(e.idtipoejg) idtipo,\r\n"
				+ "	            lpad(e.numejg, 5, 0) codigo,\r\n"
				+ "	            (\r\n"
				+ "	                SELECT\r\n"
				+ "	                    abreviatura\r\n"
				+ "	                FROM\r\n"
				+ "	                    scs_turno\r\n"
				+ "	                WHERE\r\n"
				+ "	                    idturno = e.guardiaturno_idturno\r\n"
				+ "	                    AND idinstitucion = e.idinstitucion\r\n"
				+ "	            ) des_turno,\r\n"
				+ "	            (\r\n"
				+ "	                SELECT\r\n"
				+ "	                    f_siga_getrecurso(descripcion, 1)\r\n"
				+ "	                FROM\r\n"
				+ "	                    scs_tipoejg\r\n"
				+ "	                WHERE\r\n"
				+ "	                    scs_tipoejg.idtipoejg = e.idtipoejg\r\n"
				+ "	            ) des_tipo\r\n"
				+ "	        FROM\r\n"
				+ "	            scs_ejg          e,\r\n"
				+ "	            scs_ejgdesigna   ed\r\n"
				+ "	        WHERE\r\n"
				+ "	            ed.aniodesigna = "+anio+"\r\n"
				+ "	            AND ed.numerodesigna = "+num+"\r\n"
				+ "	            AND ed.idturno = "+idTurno+"\r\n"
				+ "	            AND ed.idinstitucion = "+idinstitucion+"\r\n"
				+ "	            AND ed.idinstitucion = e.idinstitucion\r\n"
				+ "	            AND ed.anioejg = e.anio\r\n"
				+ "	            AND ed.numeroejg = e.numero\r\n"
				+ "	            AND ed.idtipoejg = e.idtipoejg\r\n"
				+ "	            AND ed.idinstitucion = e.idinstitucion\r\n");

		sql.SELECT("*");
		sql.FROM("( " + sql2.toString() + " )");
		sql.WHERE("ROWNUM <= 200");
		sql.ORDER_BY("sjcs,\r\n"
				+ "	    idinstitucion,\r\n"
				+ "	    anio DESC,\r\n"
				+ "	    codigo DESC ");
		
		return sql.toString();
	}
	
	public String eliminarRelacion(String anio, String num, String idTurno, String idinstitucion) {
		SQL sql = new SQL();

		sql.DELETE_FROM("SCS_EJGDESIGNA");

		sql.WHERE("NUMEROEJG = " + num);
		sql.WHERE("IDTURNO = " + idTurno);
		sql.WHERE("ANIOEJG = " + anio);
		sql.WHERE("IDINSTITUCION = " + idinstitucion);

		return sql.toString();
	}
		
	public String getPartidaPresupuestariaDesigna(Short idInstitucion, DesignaItem designaItem) {
		SQL sql = new SQL();

		sql.SELECT("P.idpartidapresupuestaria, nombrepartida ");
		sql.INNER_JOIN("SCS_TURNO T ON T.IDINSTITUCION = D.IDINSTITUCION AND T.IDTURNO = D.IDTURNO");
		sql.INNER_JOIN("SCS_PARTIDAPRESUPUESTARIA P ON T.IDINSTITUCION = P.IDINSTITUCION AND T.idpartidapresupuestaria = P.idpartidapresupuestaria");
		sql.FROM("SCS_DESIGNA  D");
		sql.WHERE("D.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("D.ANIO = '" + designaItem.getAno() + "'");
		sql.WHERE("D.IDTURNO = '" + designaItem.getIdTurno() + "'");
		sql.WHERE("D.NUMERO = '" + designaItem.getNumero() + "'");
		
		return sql.toString();
		
	}
	public String comboMotivosCambioActDesigna(Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("IDACTDESMOTCAMBIO");
		sql.SELECT("NOMBRE");

		sql.FROM("SCS_ACTUADESIG_MOTCAMBIO");

		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}

	public String updateJustiActDesigna(ActuacionDesignaItem actuacionDesignaItem, String idInstitucion, AdmUsuarios usuario) {

		SQL sql = new SQL();
		
		sql.UPDATE("SCS_ACTUACIONDESIGNA");

		if(!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getFechaJustificacion())) {
			sql.SET("FECHAJUSTIFICACION = '" + actuacionDesignaItem.getFechaJustificacion() + "'");
			sql.SET("USUJUSTIFICACION = '" + usuario.getIdusuario() + "'");
			sql.SET("FECHAUSUJUSTIFICACION = SYSDATE");
		}
		
		if(!UtilidadesString.esCadenaVacia(actuacionDesignaItem.getObservacionesJusti())) {
			sql.SET("OBSERVACIONESJUSTIFICACION = '" + actuacionDesignaItem.getObservacionesJusti() + "'");
		}
		
		sql.SET("USUMODIFICACION = '" + usuario.getIdusuario() + "'");
		sql.SET("FECHAMODIFICACION = SYSTIMESTAMP");
		
		sql.WHERE("NUMERO = '" + actuacionDesignaItem.getNumero() + "'");
		sql.WHERE("IDTURNO = '" + actuacionDesignaItem.getIdTurno() + "'");
		sql.WHERE("ANIO = '" + actuacionDesignaItem.getAnio() + "'");
		sql.WHERE("NUMEROASUNTO = '" + actuacionDesignaItem.getNumeroAsunto() + "'");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		
		return sql.toString();
	}
		
	public String getListaLetradosDesigna(ScsDesigna designa, Short idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT("SCS_DESIGNASLETRADO.FECHADESIGNA, \r\n" + 
				"SCS_DESIGNASLETRADO.FECHARENUNCIA, \r\n" + 
				"SCS_DESIGNASLETRADO.MOTIVOSRENUNCIA, \r\n" + 
				"SCS_DESIGNASLETRADO.FECHARENUNCIASOLICITA, \r\n"+
				"SCS_DESIGNASLETRADO.LETRADODELTURNO, \r\n"+
				"CEN_COLEGIADO.NCOLEGIADO \r\n");
		sql.SELECT("            persona.apellidos1\r\n" + 
    			"            || decode(persona.apellidos2, NULL, '', ' ' || persona.apellidos2)\r\n" + 
    			"            || ', '\r\n" + 
    			"            || persona.nombre AS apellidosnombre");
		sql.SELECT("SCS_DESIGNASLETRADO.IDPERSONA");
		sql.SELECT("SCS_DESIGNASLETRADO.observaciones");
		sql.FROM("SCS_DESIGNASLETRADO");
		sql.JOIN("CEN_COLEGIADO ON CEN_COLEGIADO.IDPERSONA=SCS_DESIGNASLETRADO.IDPERSONA AND CEN_COLEGIADO.IDINSTITUCION=SCS_DESIGNASLETRADO.IDINSTITUCION");
		sql.JOIN("CEN_PERSONA PERSONA ON PERSONA.IDPERSONA=SCS_DESIGNASLETRADO.IDPERSONA");
		sql.WHERE("SCS_DESIGNASLETRADO.NUMERO = '" + designa.getNumero() + "'");
		sql.WHERE("SCS_DESIGNASLETRADO.IDTURNO = '" + designa.getIdturno() + "'");
		sql.WHERE("SCS_DESIGNASLETRADO.ANIO = '" + designa.getAnio() + "'");
		sql.WHERE("SCS_DESIGNASLETRADO.IDINSTITUCION = '" + idInstitucion + "'");
		sql.ORDER_BY("SCS_DESIGNASLETRADO.LETRADODELTURNO DESC");
		
		
		return sql.toString();
	}

	
	
	public String  obtenerCodigoDesigna(String idInstitucion, String anio){
		SQL sql = new SQL();
		
		sql.SELECT("(MAX(to_number(CODIGO)) + 1) AS CODIGO");
		sql.FROM("SCS_DESIGNA");
		sql.WHERE(" IDINSTITUCION ='"+idInstitucion+"'");
		sql.WHERE(" ANIO ='"+anio+"'");

    	return sql.toString();
    }
	
	
	
	public String  obtenerNumeroDesigna(String idInstitucion, String anio){
		SQL sql = new SQL();
		
		sql.SELECT("(MAX(NUMERO) + 1) AS NUMERO");
		sql.FROM("SCS_DESIGNA");
		sql.WHERE(" IDINSTITUCION ='"+idInstitucion+"'");
		sql.WHERE(" ANIO ='"+anio+"'");

    	return sql.toString();
    }
	
	public String getLetradosDiasBajaTemporalTurno(String idInstitucion,String idTurno,String fecha) {
		
        SQL sql = new SQL();
		
		sql.SELECT("BAJAS.*");
		sql.FROM(" CEN_BAJASTEMPORALES BAJAS, SCS_INSCRIPCIONTURNO INS ");
		sql.WHERE(" BAJAS.IDINSTITUCION = INS.IDINSTITUCION ");
		sql.WHERE(" BAJAS.IDPERSONA = INS.IDPERSONA ");
		sql.WHERE(" INS.IDINSTITUCION ='"+idInstitucion+"'");
		sql.WHERE(" INS.IDTURNO ='"+idTurno+"'");
		sql.WHERE(" TO_DATE(BAJAS.FECHABT, 'YYYY-MM-DD') BETWEEN  TO_DATE('"+ fecha+"' , 'YYYY-MM-DD') AND  TO_DATE( '"+ fecha+"' , 'YYYY-MM-DD')  " );
		
		
    	return sql.toString();
    	
	}
	
	public String getSaltos(String idInstitucion, String idTurno, String idGuardia) {
		SQL sql = new SQL();
		sql.SELECT(" * ");
		sql.FROM(" SCS_SALTOSCOMPENSACIONES ");
		sql.WHERE(" IDINSTITUCION ='" + idInstitucion + "'");
		if (idTurno != null && !idTurno.equals("")) {
			sql.WHERE(" IDTURNO ='" + idTurno + "'");
		}
		if (idGuardia != null && !idGuardia.equals("")) {
			sql.WHERE(" IDGUARDIA ='" + idGuardia + "'");
		} else {
			sql.WHERE(" IDGUARDIA IS NULL ");
		}
		sql.WHERE(" SALTOOCOMPENSACION = 'S'  ");
		sql.ORDER_BY("FECHA, IDSALTOSTURNO");
		
		return sql.toString();
	}
	
	
	public String getCompensaciones(String idInstitucion, String idTurno, String fecha) {

		SQL sql = new SQL();
		sql.SELECT(" * ");
		sql.FROM(" SCS_SALTOSCOMPENSACIONES ");
		sql.WHERE(" IDINSTITUCION ='" + idInstitucion + "'");
		if (idTurno != null && !idTurno.equals("")) {
			sql.WHERE(" IDTURNO ='" + idTurno + "'");
		}
		sql.WHERE(" IDGUARDIA IS NULL ");
		sql.WHERE(" SALTOOCOMPENSACION = 'C' ");
		sql.WHERE(" FECHACUMPLIMIENTO is NULL ");
		sql.ORDER_BY("FECHA, IDSALTOSTURNO");
		
		return sql.toString();
	}
    
    
	
	public String getInscripcionTurnoActiva(String idInstitucion, String idTurno, String idPersona, String fecha) {
		
		SQL sql = new SQL();
		
		sql.SELECT(" Ins.Idinstitucion,Ins.Idturno, Per.Idpersona,Ins.fechasolicitud,TO_CHAR(TRUNC(Ins.fechavalidacion),'DD/MM/YYYY') As Fechavalidacion,"
				+ "TO_CHAR(trunc(Ins.fechabaja),'DD/MM/YYYY') As Fechabaja,Per.Nombre,Per.Apellidos1,Decode(Per.Apellidos2, Null, '', ' ' || Per.Apellidos2) apellidos2,"
				+ " Per.Apellidos1 || Decode(Per.Apellidos2, Null, '', ' ' || Per.Apellidos2) ALFABETICOAPELLIDOS,  Decode(Col.Comunitario, '1', Col.Ncomunitario, Col.Ncolegiado) NUMEROCOLEGIADO, Per.Fechanacimiento FECHANACIMIENTO,"
				+ "  Ins.Fechavalidacion AS ANTIGUEDADCOLA ");
		
		sql.FROM(" Scs_Turno              Tur, Cen_Colegiado          Col, Cen_Persona            Per,Scs_Inscripcionturno   Ins");
		sql.WHERE(" Ins.Fechavalidacion Is Not Null ");
		sql.WHERE(" Tur.Idinstitucion ='" + idInstitucion + "'");
		sql.WHERE(" Tur.Idturno ='" + idTurno + "'");
		sql.WHERE(" Ins.Fechavalidacion Is Not Null ");
		sql.WHERE( " Trunc(Ins.Fechavalidacion) <= nvl('"+fecha+"',  Ins.Fechavalidacion)" );
		sql.WHERE( "(Ins.Fechabaja Is Null Or    Trunc(Ins.Fechabaja) > nvl('"+fecha+"', '01/01/1900')) ");
		sql.WHERE(" Ins.idpersona ='" + idPersona + "' and rownum <= 1");
		
		return sql.toString();
		
	}
	
	
	public String cambiarUltimoCola(String idInstitucion, String idTurno, String idPersonaUltimo, Date fechaSolicitudUltimo, AdmUsuarios usuario) throws ParseException {
		
		String sIdpersona = (idPersonaUltimo == null) ? "null" : idPersonaUltimo.toString();
		String sFechaSolicitudUltimo = (fechaSolicitudUltimo == null || fechaSolicitudUltimo.equals("")) ? "null" : fechaSolicitudUltimo.toString();
		
		 Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String fechaBBDD2 = formatter.format(fechaSolicitudUltimo);
		 
		SQL sql = new SQL();

		sql.UPDATE("SCS_TURNO");

		sql.SET("IDPERSONA_ULTIMO = '" + sIdpersona + "'");
		sql.SET("FECHASOLICITUD_ULTIMO = TO_DATE('" + fechaBBDD2 + "' , 'YYYY/MM/DD HH24:MI:SS') ");
		sql.SET("USUMODIFICACION = '" + usuario.getIdusuario() + "'");
		sql.SET("FECHAMODIFICACION = SYSTIMESTAMP");
		

		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("IDTURNO = '" + idTurno + "'");
		

		return sql.toString();
	}
	
	public String marcarSaltoCompensacion(ScsSaltoscompensaciones saltoCompensacion, AdmUsuarios usuario)
			throws Exception {
		SQL sql = new SQL();

		try {
			String s_idinstitucion = saltoCompensacion.getIdinstitucion().toString();
			String s_idturno = null;
			if (saltoCompensacion.getIdturno() != null) {
				s_idturno = saltoCompensacion.getIdturno().toString();
			}
			String s_idguardia = null;
			if (saltoCompensacion.getIdguardia() != null) {
				s_idguardia = saltoCompensacion.getIdguardia().toString();
			}
			String s_idpersona = null;
			if (saltoCompensacion.getIdpersona() != null) {
				s_idpersona = saltoCompensacion.getIdpersona().toString();
			}
			String s_saltocompensacion = null;
			if (saltoCompensacion.getSaltoocompensacion() != null) {
				s_saltocompensacion = saltoCompensacion.getSaltoocompensacion();
				if (s_saltocompensacion.equals(""))
					s_saltocompensacion = " ";
			}

			Format formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String fechaBBDD2 = formatter.format(saltoCompensacion.getFechacumplimiento());

			sql.UPDATE("SCS_SALTOSCOMPENSACIONES");

			sql.SET("FECHACUMPLIMIENTO = TO_DATE('" + fechaBBDD2 + "' , 'YYYY/MM/DD HH24:MI:SS') ");
			sql.SET("USUMODIFICACION = '" + usuario.getIdusuario() + "'");
			sql.SET("FECHAMODIFICACION = SYSTIMESTAMP");
			if (saltoCompensacion.getIdguardia() != null) {
				sql.SET("IDCALENDARIOGUARDIAS = '" + saltoCompensacion.getIdcalendarioguardias() + "'");
			}
			if (saltoCompensacion.getMotivos() != null && !saltoCompensacion.getMotivos().equals("")) {

				sql.SET("MOTIVOS = '" + saltoCompensacion.getMotivos() + "'");
			}

			sql.WHERE("IDINSTITUCION = '" + s_idinstitucion + "'");

			if (s_idturno != null && !s_idturno.equals("")) {
				sql.WHERE("IDTURNO = '" + s_idturno + "'");

			}
			if (s_idguardia != null && !s_idguardia.equals("")) {
				sql.WHERE("IDGUARDIA = '" + s_idguardia + "'");
			} else {
				sql.WHERE("IDGUARDIA IS NULL");
			}

			if (s_idpersona != null && !s_idpersona.equals("")) {
				sql.WHERE("IDPERSONA = '" + s_idpersona + "'");
			}

			if (s_saltocompensacion != " ") {
				sql.WHERE("SALTOOCOMPENSACION = '" + s_saltocompensacion + "'");
			}

			sql.WHERE("FECHACUMPLIMIENTO IS NULL");

			if (saltoCompensacion.getIdsaltosturno() != null) {

				sql.WHERE("IDSALTOSTURNO = '" + saltoCompensacion.getIdsaltosturno() + "'");
			}

			// sql.append(" AND rownum=1");

		} catch (Exception e) {
			throw new Exception("Excepcion en marcarSaltoCompensacionBBDD.", e);
		}

		return sql.toString();
	}
	
	
	/**
	 * Obtiene de BD las inscripciones ordenadas para formar la cola de un turno dada una fecha
	 * 
	 * @param idinstitucion
	 * @param idturno
	 * @param fecha
	 * @param order
	 * @return
	 * @throws Exception 
	 */
 	public String getColaTurnoBBDD(String idinstitucion, String idturno, String fecha, String order) throws Exception   {
 		try {
			if (idinstitucion == null || idinstitucion.equals(""))
				return null;
			if (idturno == null || idturno.equals(""))
				return null;
			if (fecha == null || fecha.equals(""))
				fecha = "null";
			else if(!fecha.trim().equalsIgnoreCase("sysdate"))
				fecha = "'"+fecha.trim()+"'";
			
			String consulta = 
			"Select "+
				"       (case when Ins.Fechavalidacion Is Not Null "+
				"              And Trunc(Ins.Fechavalidacion) <= nvl("+fecha+",  Ins.Fechavalidacion) "+
				"              And (Ins.Fechabaja Is Null Or "+
				"                   Trunc(Ins.Fechabaja) > nvl("+fecha+", '01/01/1900')) "+
				"             then '1' "+
				"             else '0' "+
				"        end) Activo, "+
				" Ins.Idinstitucion,"+
				"       Ins.Idturno, " +
				" TO_CHAR(TRUNC(Ins.fechavalidacion),'DD/MM/YYYY') AS fechavalidacion, "+
			    "   TO_CHAR(trunc(Ins.fechabaja),'DD/MM/YYYY') AS fechabaja, "+
			    "    Ins.fechasolicitud AS fechaSolicitud, "+
			    "       Per.Nifcif,"+
				"       Per.Idpersona,"+
				"       Per.Nombre, " +
				"       Per.Apellidos1, " +
				"       Decode(Per.Apellidos2, Null, '', ' ' || Per.Apellidos2) apellidos2, " +
				"       Decode(Col.Comunitario, '1', Col.Ncomunitario, Col.Ncolegiado) Ncolegiado, " +
				
				"       Per.Apellidos1 || " +
				"       Decode(Per.Apellidos2, Null, '', ' ' || Per.Apellidos2) ALFABETICOAPELLIDOS, " +
				"       Decode(Col.Comunitario, '1', Col.Ncomunitario, Col.Ncolegiado) NUMEROCOLEGIADO, " +
				"       Per.Fechanacimiento FECHANACIMIENTO, " +
				"       Ins.Fechavalidacion ANTIGUEDADCOLA " +
				"  From Scs_Turno              Tur, " +
				"       Cen_Colegiado          Col, " +
				"       Cen_Persona            Per, " +
				"       Scs_Inscripcionturno   Ins " +
				" Where Col.Idpersona = Per.Idpersona " +
				"   And Ins.Idinstitucion = Tur.Idinstitucion " +
				"   And Ins.Idturno = Tur.Idturno " +
				"   And Ins.Idinstitucion = Col.Idinstitucion " +
				"   And Ins.Idpersona = Col.Idpersona " +
				
				//cuando no se pasa fecha, se sacan todas las validadas (en cualquier fecha)
				"   And Ins.Fechavalidacion Is Not Null " +
	/*
				"   And Trunc(Ins.Fechavalidacion) <= nvl("+fecha+",  Ins.Fechavalidacion) " +
					//cuando no se pasa fecha, se sacan aunque esten de baja
				"   And (Ins.Fechabaja Is Null Or   Trunc(Ins.Fechabaja) > nvl("+fecha+", '01/01/1900')) " +*/
				"   And Tur.Idinstitucion = "+idinstitucion+" " +
				"   And Tur.Idturno = "+idturno+" ";
			
			consulta += " order by " + order;
			// Para el caso de que coincida el orden establecido, a�adimos un orden que siempre deberia ser diferente: la fecha de suscripcion
			consulta += ", Ins.fechasolicitud, Ins.Idpersona ";
			
			
	
			return consulta;
			
		} catch (Exception e) {
			throw new Exception ( "Error al ejecutar getColaTurno()",e);
		}			
	} 
 	
	public String busquedaComunicaciones(String anio, String num, String idturno, String idPersona) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();
		
		
		sql2.SELECT("S.DESCRIPCION, S.FECHA, S.FECHAPROGRAMADA, F_SIGA_GETRECURSO(T.NOMBRE, 1) as TIPOENVIO, N.NOMBRE, F_SIGA_GETRECURSO(A.DESCRIPCION, 1) as IDESTADO , N.APELLIDOS1 , N.APELLIDOS2");

		sql2.FROM("SCS_COMUNICACIONES C");
		sql2.INNER_JOIN("ENV_ENVIOS S on (s.idenvio = C.IDENVIOSALIDA)");
		sql2.INNER_JOIN("ENV_TIPOENVIOS T on (T.IDTIPOENVIOS = S.IDTIPOENVIOS)");
		sql2.INNER_JOIN("CEN_ESTADOSOLICITUD A on (A.IDESTADO = S.IDESTADO)");
		sql2.INNER_JOIN("ENV_DESTINATARIOS D on (D.IDENVIO = S.IDENVIO)");
		sql2.INNER_JOIN("CEN_PERSONA N on (N.IDPERSONA = D.IDPERSONA)");
		sql2.WHERE("C.DESIGNAANIO = " + anio);
		sql2.WHERE("C.DESIGNAIDTURNO =" + idturno);
		
		if(idPersona == null) {
			sql2.WHERE("C.DESIGNANUMERO =" + num);
		}else {
			sql2.WHERE("C.DESIGNANUMERO =" + num + "OR D.IDPERSONA ="+idPersona);
		}
		

		sql.SELECT("*");
		sql.FROM("( " + sql2.toString() + " )");
		sql.WHERE("ROWNUM <= 200");

		return sql.toString();
	}
	
	public String  obtenerIdPersonaByNumCol(String idInstitucion, String numColegiado) {
		SQL sql = new SQL();
		
		sql.SELECT("IDPERSONA ");
		sql.FROM("CEN_COLEGIADO");
		sql.WHERE(" IDINSTITUCION ='"+idInstitucion+"'");
		sql.WHERE(" NCOLEGIADO ='"+numColegiado+"'");

    	return sql.toString();
    }
	
	public String comboAcreditacionesPorTipo() {
		
		SQL sql = new SQL();
		SQL sql2 = new SQL();
		
		sql2.SELECT("DESCRIPCION");
		sql2.SELECT("IDTIPOACREDITACION");
		
		sql2.FROM("SCS_TIPOACREDITACION");
		
		sql.SELECT("*");
		sql.FROM("( " + sql2.toString() + " )");
		sql.WHERE("ROWNUM <= 3");
		
		return sql.toString();
	}
	
	public String getNewIdDocumentacionAsi(Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("NVL(MAX(DOC.IDDOCUMENTACIONASI),0) +1 AS ID");

		sql.FROM("SCS_DOCUMENTACIONASI DOC");

		sql.WHERE("DOC.IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}
	
	public String getDocumentosPorActDesigna(DocumentoActDesignaItem documentoActDesignaItem, Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("DOC.IDDOCUMENTACIONASI");
		sql.SELECT("DOC.IDTIPODOCUMENTO");
		sql.SELECT("TIPODOC.NOMBRE AS NOMBRETIPODOCUMENTO");
		sql.SELECT("DOC.IDFICHERO");
		sql.SELECT("DOC.IDINSTITUCION");
		sql.SELECT("DOC.ANIO");
		sql.SELECT("DOC.NUMERO");
		sql.SELECT("DOC.IDACTUACION");
		sql.SELECT("DOC.USUMODIFICACION");
		sql.SELECT("DOC.FECHAMODIFICACION");
		sql.SELECT("DOC.FECHAENTRADA");
		sql.SELECT("DOC.OBSERVACIONES");
		sql.SELECT("DOC.NOMBREFICHERO");
		sql.SELECT("COL.IDPERSONA");
		sql.SELECT(
				"NVL(DECODE(NVL(COL.COMUNITARIO,0),0, COL.NCOLEGIADO, COL.NCOMUNITARIO), COL.NCOLEGIADO) AS NUMCOLEGIADO");

		sql.FROM("SCS_DOCUMENTACIONASI DOC");

		sql.JOIN("SCS_TIPODOCUMENTOASI TIPODOC ON TIPODOC.IDTIPODOCUMENTOASI = DOC.IDTIPODOCUMENTO");
		sql.JOIN("ADM_USUARIOS ADM ON ADM.IDUSUARIO = DOC.USUMODIFICACION AND ADM.IDINSTITUCION = DOC.IDINSTITUCION");
		sql.JOIN("CEN_PERSONA P ON ADM.NIF = P.NIFCIF");
		sql.JOIN("CEN_COLEGIADO COL ON P.IDPERSONA = COL.IDPERSONA AND COL.IDINSTITUCION = DOC.IDINSTITUCION");

		sql.WHERE("DOC.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("DOC.NUMERO ='" + documentoActDesignaItem.getNumero() + "'");
		sql.WHERE("DOC.ANIO ='" + documentoActDesignaItem.getAnio() + "'");
		sql.WHERE("DOC.IDACTUACION ='" + documentoActDesignaItem.getIdActuacion() + "'");

		sql.ORDER_BY("DOC.FECHAENTRADA DESC");

		return sql.toString();
	}
	
	public String getDocumentosPorDesigna(DocumentoDesignaItem documentoDesignaItem, Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("DOCD.IDDOCUMENTACIONDES");
		sql.SELECT("DOCD.IDTIPODOCUMENTO");
		sql.SELECT("DOCD.IDFICHERO");
		sql.SELECT("DOCD.IDINSTITUCION");
		sql.SELECT("DOCD.IDTURNO");
		sql.SELECT("DOCD.ANIO");
		sql.SELECT("DOCD.NUMERO");
		sql.SELECT("DOCD.IDACTUACION");
		sql.SELECT("DOCD.USUMODIFICACION");
		sql.SELECT("DOCD.FECHAMODIFICACION");
		sql.SELECT("DOCD.FECHAENTRADA");
		sql.SELECT("DOCD.OBSERVACIONES");
		sql.SELECT("DOCD.NOMBREFICHERO");
		sql.SELECT("COL.IDPERSONA");
		sql.SELECT(
				"NVL(DECODE(NVL(COL.COMUNITARIO,0),0, COL.NCOLEGIADO, COL.NCOMUNITARIO), COL.NCOLEGIADO) AS NUMCOLEGIADO");

		sql.FROM("SCS_DOCUMENTACIONDESIGNA DOCD");

		sql.JOIN("ADM_USUARIOS ADM ON ADM.IDUSUARIO = DOCD.USUMODIFICACION AND ADM.IDINSTITUCION = DOCD.IDINSTITUCION");
		sql.JOIN("CEN_PERSONA P ON ADM.NIF = P.NIFCIF");
		sql.JOIN("CEN_COLEGIADO COL ON P.IDPERSONA = COL.IDPERSONA AND COL.IDINSTITUCION = DOCD.IDINSTITUCION");

		sql.WHERE("DOCD.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("DOCD.NUMERO ='" + documentoDesignaItem.getNumero() + "'");
		sql.WHERE("DOCD.ANIO ='" + documentoDesignaItem.getAnio() + "'");
		sql.WHERE("DOCD.IDTURNO ='" + documentoDesignaItem.getIdTurno() + "'");

		return sql.toString();
	}
	
	public String comboTipoDocumentacionDesigna() {
		SQL sql = new SQL();
		
		sql.SELECT("E.IDTIPODOCUMENTODES");
		sql.SELECT("F_SIGA_GETRECURSO(E.NOMBRE, 1) AS NOMBRE");
		
		sql.FROM("SCS_TIPODOCUMENTODES E");
		
		return sql.toString();
	}
	
	public String getNewIdDocumentacionDes(Short idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("NVL(MAX(DOC.IDDOCUMENTACIONDES),0) +1 AS ID");
		
		sql.FROM("SCS_DOCUMENTACIONDESIGNA DOC");
		
		sql.WHERE("DOC.IDINSTITUCION = '" + idInstitucion + "'");
		
		return sql.toString();
	}

	
}
