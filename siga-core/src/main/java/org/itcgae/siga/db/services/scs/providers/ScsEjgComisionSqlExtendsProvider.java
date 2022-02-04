package org.itcgae.siga.db.services.scs.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.ActasItem;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.ScsEjgSqlProvider;

public class ScsEjgComisionSqlExtendsProvider extends ScsEjgSqlProvider {
	private Logger LOGGER = Logger.getLogger(ScsEjgComisionSqlExtendsProvider.class);

	public String obligatoriedadResolucion(Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("IDINSTITUCION as VALUE");
		sql.FROM("GEN_PARAMETROS");
		sql.WHERE("PARAMETRO like '%VALIDAR_OBL%'");
		sql.WHERE("IDINSTITUCION =" + idInstitucion);
		LOGGER.info(sql.toString());
		return sql.toString();
	}

	public String comboPresidente(String idLenguaje, Short idInstitucion) {

		SQL sqlPresidente = new SQL();
		sqlPresidente.SELECT("pon.IDPONENTE as value, f_siga_getrecurso(pon.nombre," + idLenguaje + ") as nombre");
		sqlPresidente.FROM("scs_ponente pon");
		sqlPresidente.WHERE("pon.idinstitucion = " + idInstitucion);
		sqlPresidente.ORDER_BY("nombre");
		LOGGER.info("*******************comboPresidente********************" + sqlPresidente.toString());
		return sqlPresidente.toString();
	}

	public String getEtiquetasPonente(Short idLenguaje) {

		SQL sqlPresidente = new SQL();
		sqlPresidente.SELECT("f_Siga_Getrecurso_Etiqueta('gratuita.ejg.estado.literal.automatico'," + idLenguaje
				+ ") || ' ' ||f_Siga_Getrecurso_Etiqueta('gratuita.operarRatificacion.literal.ponente', " + idLenguaje
				+ ") as nombre");
		sqlPresidente.FROM("dual");
		LOGGER.info("*******************getEtiquetasPonente********************" + sqlPresidente.toString());
		return sqlPresidente.toString();
	}

	public String comboAnioActa(Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("to_clob(IDINSTITUCION ||',' || ANIOACTA || ',' || IDACTA) as value");
		sql.SELECT("anioacta||'/'||numeroacta as DESCRIPCION");
		sql.FROM("SCS_ACTACOMISION");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("FECHARESOLUCION IS NULL");
		sql.ORDER_BY("IDINSTITUCION,ANIOACTA,IDACTA");

		LOGGER.info("*******************comboAnioActa********************" + sql.toString());

		return sql.toString();
	}

	public String comboResolucion(Short idLenguaje, String idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("tiporesolucion.idtiporesolucion as VALUE");
		sql.SELECT("catalogoResolucion.descripcion AS DESCRIPCION");
		sql.FROM("SCS_TIPORESOLUCION tiporesolucion");
		sql.INNER_JOIN(
				"GEN_RECURSOS_CATALOGOS catalogoResolucion on catalogoResolucion.idrecurso = tiporesolucion.DESCRIPCION and catalogoResolucion.idlenguaje = "
						+ idLenguaje);

		sql.WHERE("tiporesolucion.fechabaja is null");
		sql.WHERE("tiporesolucion.fecha_baja is null");

		sql.ORDER_BY("catalogoResolucion.descripcion");
		LOGGER.info("*******************comboResolucion********************" + sql.toString());

		return sql.toString();
	}

	public String comboTipoEjgColegioComision(Short idLenguaje, String idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("tipoejg.IDTIPOEJGCOLEGIO");
		sql.SELECT("cat.descripcion");
		sql.FROM("SCS_TIPOEJGCOLEGIO tipoejg");
		sql.INNER_JOIN("gen_recursos_catalogos cat on cat.IDRECURSO = tipoejg.descripcion and cat.idlenguaje = '"
				+ idLenguaje + "'");
		sql.WHERE("tipoejg.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("tipoejg.fecha_baja is null");
		sql.ORDER_BY("cat.descripcion");

		return sql.toString();
	}

	public String comboGuardiasComision(String idTurno, String idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("NOMBRE");
		sql.SELECT("IDGUARDIA");

		sql.FROM("SCS_GUARDIASTURNO");

		sql.WHERE("IDTURNO IN (" + idTurno + ")");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.ORDER_BY("nombre");
		LOGGER.info("*******************comboGuardiasComision********************" + sql.toString());
		return sql.toString();
	}

	public String comboDictamenComision(Short idLenguaje, String idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("tipodictamen.IDTIPODICTAMENEJG");
		sql.SELECT("catalogoDictamen.DESCRIPCION");

		sql.FROM("SCS_TIPODICTAMENEJG tipodictamen");
		sql.LEFT_OUTER_JOIN(
				"GEN_RECURSOS_CATALOGOS catalogoDictamen on catalogoDictamen.idrecurso = tipodictamen.DESCRIPCION and catalogoDictamen.idlenguaje ="
						+ idLenguaje);
		sql.WHERE("tipodictamen.fecha_baja is  null and tipodictamen.idinstitucion = " + idInstitucion);

		LOGGER.info("*******************comboDictamenComision********************" + sql.toString());

		return sql.toString();
	}

	public String comboEstadoEjg(Short idLenguaje, String idInstitucion, String resolucion) {
		SQL sql = new SQL();

		sql.SELECT("estado.IDESTADOEJG");
		sql.SELECT("cat.descripcion");
		sql.FROM("SCS_MAESTROESTADOSEJG estado");
		sql.INNER_JOIN("gen_recursos_catalogos cat on cat.IDRECURSO = estado.descripcion and cat.idlenguaje = '"
				+ idLenguaje + "'");
		sql.WHERE("estado.fecha_baja is null");
		sql.WHERE("VISIBLECOMISION = 1");
		sql.ORDER_BY("cat.descripcion");
		LOGGER.info("*******************comboEstadoEjgComision********************" + sql.toString());

		return sql.toString();
	}

	public String comboTurnosTipoComision(String idInstitucion, String tipoturno) {

		SQL sql = new SQL();

		sql.SELECT("IDTURNO, NOMBRE");
		sql.FROM("SCS_TURNO");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("FECHABAJA IS NULL");
		if (tipoturno.equals("1") || tipoturno.equals("2"))
			sql.WHERE("IDTIPOTURNO= '" + tipoturno + "'");
		sql.ORDER_BY("NOMBRE");
		LOGGER.info("*******************comboTurnosTipoComision********************" + sql.toString());
		return sql.toString();
	}

	public String comboJuzgadosComision(String idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("IDJUZGADO");
		sql.SELECT("DECODE(CODIGOEXT2,NULL,NOMBRE, CODIGOEXT2 || '-' || NOMBRE) AS DESCRIPCION");
		sql.FROM("SCS_JUZGADO");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		LOGGER.info("*******************comboJuzgadosComision********************" + sql.toString());
		return sql.toString();
	}

	public String comboFundamentoCalificacionComision(Short idLenguaje, String idInstitucion, String[] list_dictamen) {
		SQL sql = new SQL();
		String dictamenCad = "";
		boolean indiferente = false;

		sql.SELECT("fundamento.idfundamentocalif");
		sql.SELECT("catalogoFundamento.descripcion");

		sql.FROM("SCS_TIPOFUNDAMENTOCALIF fundamento");

		if (list_dictamen != null) {
			for (String dictamen : list_dictamen) {
				if (!dictamen.equals("-1")) {
					dictamenCad += dictamen + ",";
				} else {
					indiferente = true;
				}
			}
			if (!indiferente) {
				dictamenCad = dictamenCad.substring(0, (dictamenCad.length() - 1));
				sql.WHERE("fundamento.IDTIPODICTAMENEJG IN (" + dictamenCad + ")");
			}

		}

		sql.LEFT_OUTER_JOIN(
				"GEN_RECURSOS_CATALOGOS catalogoFundamento on catalogoFundamento.idrecurso = fundamento.DESCRIPCION and catalogoFundamento.idlenguaje ="
						+ idLenguaje);
		sql.WHERE("fundamento.fecha_baja is null and fundamento.idinstitucion  = '" + idInstitucion + "'");
		LOGGER.info("*******************comboFundamentoCalificacionComision********************" + sql.toString());
		return sql.toString();
	}

	public String comboPonenteComision(Short idLenguaje, String idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("SCS_PONENTE.IDPONENTE as CLAVE");
		sql.SELECT("GEN_RECURSOS_CATALOGOS.DESCRIPCION as DESCRIPCION");
		sql.FROM("SCS_PONENTE");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS ON SCS_PONENTE.NOMBRE = GEN_RECURSOS_CATALOGOS.IDRECURSO");
		sql.WHERE("SCS_PONENTE.FECHA_BAJA IS NULL AND SCS_PONENTE.IDINSTITUCION = '" + idInstitucion
				+ "' AND GEN_RECURSOS_CATALOGOS.IDLENGUAJE ='" + idLenguaje + "'");
		sql.ORDER_BY("GEN_RECURSOS_CATALOGOS.DESCRIPCION");
		LOGGER.info("*******************comboPonenteComision********************" + sql.toString());
		return sql.toString();
	}

	public String comboFundamentoJuridComision(Short idLenguaje, String idInstitucion, String resolucion) {
		SQL sql = new SQL();

		sql.SELECT("idfundamento as ID");
		sql.SELECT("F_SIGA_GETRECURSO(DESCRIPCION, 1) as DESCRIPCION");
		sql.FROM("SCS_TIPOFUNDAMENTOS");
		if(resolucion != null) {
		sql.WHERE("fecha_baja is null AND IDINSTITUCION =" + idInstitucion + "  AND IDTIPORESOLUCION =" + resolucion
				+ " OR IDTIPORESOLUCION = NULL");
		}else{
		sql.WHERE("fecha_baja is null AND IDINSTITUCION =" + idInstitucion);
		sql.ORDER_BY("DESCRIPCION");
		}
		LOGGER.info("*******************comboFundamentoJuridComision********************" + sql.toString());
		return sql.toString();
	}

	public String comboColegioEjgComision(String idInstitucion) {
		SQL sql = new SQL();
		sql.SELECT("INT.NOMBRE");
		sql.SELECT("INT.IDINSTITUCION");
		sql.FROM("CEN_INSTITUCION INT");
		sql.WHERE(
				"INT.IDCOMISION IN (SELECT INTCOM.IDCOMISION FROM CEN_INSTITUCION INTCOM WHERE INTCOM.IDINSTITUCION = IDINSTITUCION AND INTCOM.IDINSTITUCION = '"
						+ idInstitucion + "') AND INT.IDTIPOINSTITUCION =3");
		LOGGER.info("*******************comboColegioEjgComision********************" + sql.toString());
		return sql.toString();
	}

	public String idUltimoEstado(EjgItem ejgItem, String idInstitucion) {

		SQL sqlIdEstadoEjg = new SQL();
		String F_SIGA_GET_IDULTIMOESTADOEJG = "F_SIGA_GET_IDULTIMOESTADOEJG(" + idInstitucion + ","
				+ ejgItem.getTipoEJG() + "," + ejgItem.getAnnio() + "," + ejgItem.getNumero() + ") AS ID";
		sqlIdEstadoEjg.SELECT(F_SIGA_GET_IDULTIMOESTADOEJG);
		sqlIdEstadoEjg.FROM("dual");
		LOGGER.info("*******************idUltimoEstado********************" + sqlIdEstadoEjg.toString());
		return sqlIdEstadoEjg.toString();
	}

	public String busquedaEJGComision(EjgItem ejgItem, String idInstitucion, Integer tamMaximo,
			String idLenguaje) {
		String dictamenCad = "";
		boolean indiferente = false;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String fechaAperturaDesd;
		String fechaAperturaHast;
		String fechaEstadoDesd;
		String fechaEstadoHast;
		String fechaLimiteDesd;
		String fechaLimiteHast;
		String fechaDictamenDesd;
		String fechaDictamenHast;
		String fechaImpugnacionDesd;
		String fechaImpugnacionHast;
		String fechaPonenteDesd;
		String fechaPonenteHast;
		String fechaResolucionDesd;
		String fechaResolucionHast;
		String resolucion;
		SQL sql = new SQL();

		resolucion = "(SELECT F_SIGA_GETRECURSO(tipores.DESCRIPCION, 1) from SCS_TIPORESOLUCION tipores where tipores.codigo = ejg.idtiporatificacionejg) RESOLUCION";
		
		String condicionAnnioNumActas = " (EXISTS (SELECT 1 FROM scs_ejg_acta ejgacta, scs_actacomision ac"
				+ " WHERE ejgacta.idinstitucionacta = ac.idinstitucion" + " AND ejgacta.idacta = ac.idacta"
				+ " AND   ejgacta.anioacta = ac.anioacta" + " AND   ejgacta.idinstitucionejg = ejg.idinstitucion"
				+ " AND   ejgacta.anioejg = ejg.anio" + " AND   ejgacta.idtipoejg = ejg.idtipoejg"
				+ " AND   ejgacta.numeroejg = ejg.numero" + " AND   ac.idinstitucion = " + idInstitucion;

		if (ejgItem.getAnnioActa() != null && ejgItem.getAnnioActa() != "")
			condicionAnnioNumActas = condicionAnnioNumActas + " AND   ac.anioacta = " + ejgItem.getAnnioActa();
		if (ejgItem.getNumActa() != null && ejgItem.getNumActa() != "")
			condicionAnnioNumActas = condicionAnnioNumActas + " AND   ac.numeroacta = '" + ejgItem.getNumActa() + "'";
		condicionAnnioNumActas = condicionAnnioNumActas + "))";

		String condicionNumRegRemesa = " (EXISTS (SELECT 1 FROM cajg_ejgremesa ejgremesa, cajg_remesa remesa"
				+ " WHERE ejgremesa.idinstitucionremesa = remesa.idinstitucion"
				+ " AND ejgremesa.idinstitucion = ejg.idinstitucion" + " AND   ejgremesa.anio = ejg.anio"
				+ " AND   ejgremesa.idtipoejg = ejg.idtipoejg" + " AND   ejgremesa.numero = ejg.numero"
				+ " AND   remesa.idinstitucion = " + idInstitucion;
		if (ejgItem.getNumRegRemesa1() != null && ejgItem.getNumRegRemesa1() != "")
			condicionNumRegRemesa = condicionNumRegRemesa + " AND   remesa.prefijo = '" + ejgItem.getNumRegRemesa1()
					+ "'";
		if (ejgItem.getNumRegRemesa2() != null && ejgItem.getNumRegRemesa2() != "")
			condicionNumRegRemesa = condicionNumRegRemesa + " AND   remesa.numero = '" + ejgItem.getNumRegRemesa2()
					+ "'";
		if (ejgItem.getNumRegRemesa3() != null && ejgItem.getNumRegRemesa3() != "")
			condicionNumRegRemesa = condicionNumRegRemesa + " AND   remesa.sufijo = '" + ejgItem.getNumRegRemesa3()
					+ "'";
		condicionNumRegRemesa = condicionNumRegRemesa + "))";

		// select
		sql.SELECT("ejg.anio");
		sql.SELECT("ejg.idinstitucion");
		sql.SELECT("ejg.idtipoejg");
		sql.SELECT("ejg.numero");
		sql.SELECT("ejg.numejg numejg");
		sql.SELECT("'E' || EJG.ANIO || '/' || EJG.NUMEJG AS NUMANIO");
		sql.SELECT(
				"(CASE WHEN TURNO.NOMBRE is  NULL THEN '' ELSE TURNO.NOMBRE || '/' || GUARDIA.NOMBRE END) AS TURNOGUARDIA");
		sql.SELECT("TURNO.ABREVIATURA AS TURNO");
		sql.SELECT("EJG.GUARDIATURNO_IDTURNO as IDTURNO");
		sql.SELECT("ejg.fechaapertura");
		sql.SELECT("ejg.fechamodificacion");
		sql.SELECT(
				"(CASE WHEN per.nombre is  NULL THEN '' ELSE per.apellidos1 || ' ' || per.apellidos2 || ', ' || per.nombre END) as NOMBREletrado");
		sql.SELECT("REC.DESCRIPCION AS ESTADOEJG");
		sql.SELECT(
				"(CASE WHEN perjg.nombre is  NULL THEN '' ELSE perjg.apellido1 || ' ' || perjg.apellido2 || ', ' || perjg.nombre END) as NOMBRESOLICITANTE");
		sql.SELECT("EJG.NUMEROPROCEDIMIENTO");
		sql.SELECT("ejg.idpersonajg");
		sql.SELECT("perjg.NIF");
		sql.SELECT(resolucion);
		sql.SELECT("maestroestado.editablecomision as EDITABLECOMISION");

		// from
		sql.FROM("scs_ejg ejg");
		// joins
		sql.LEFT_OUTER_JOIN("cen_persona per on per.idpersona = ejg.idpersona");
		sql.LEFT_OUTER_JOIN(
				"scs_personajg perjg on perjg.idpersona = ejg.idpersonajg AND perjg.IDINSTITUCION = EJG.IDINSTITUCION");
		sql.LEFT_OUTER_JOIN(
				"SCS_TURNO  TURNO ON TURNO.IDINSTITUCION =EJG.IDINSTITUCION AND TURNO.IDTURNO =EJG.GUARDIATURNO_IDTURNO");
		sql.LEFT_OUTER_JOIN(
				"SCS_GUARDIASTURNO GUARDIA  ON GUARDIA.IDINSTITUCION =EJG.IDINSTITUCION AND GUARDIA.IDTURNO =EJG.GUARDIATURNO_IDTURNO  AND GUARDIA.IDGUARDIA =EJG.GUARDIATURNO_IDGUARDIA");
		sql.INNER_JOIN("SCS_ESTADOEJG ESTADO ON (ESTADO.IDINSTITUCION = EJG.IDINSTITUCION "
				+ "AND ESTADO.IDTIPOEJG = EJG.IDTIPOEJG AND ESTADO.IDTIPOEJG = EJG.IDTIPOEJG "
				+ "AND ESTADO.ANIO = EJG.ANIO " + "AND ESTADO.NUMERO = EJG.NUMERO " + "AND ESTADO.FECHABAJA IS NULL "
				+ "AND ESTADO.idestadoporejg = (SELECT MAX(idestadoporejg) FROM SCS_ESTADOEJG ESTADO2 WHERE (ESTADO.IDINSTITUCION = ESTADO2.IDINSTITUCION "
				+ "AND ESTADO.IDTIPOEJG = ESTADO2.IDTIPOEJG " + "AND ESTADO.ANIO = ESTADO2.ANIO "
				+ "AND ESTADO.NUMERO = ESTADO2.NUMERO " + "AND ESTADO2.FECHABAJA IS NULL) "
				+ "AND ESTADO2.FECHAINICIO = (SELECT MAX(FECHAINICIO) FROM SCS_ESTADOEJG ESTADO3 WHERE (ESTADO3.IDINSTITUCION = ESTADO2.IDINSTITUCION "
				+ "AND ESTADO.IDTIPOEJG = ESTADO3.IDTIPOEJG " + "AND ESTADO.ANIO = ESTADO3.ANIO "
				+ "AND ESTADO.NUMERO = ESTADO3.NUMERO " + "AND ESTADO3.FECHABAJA IS NULL))))"); // AND ESTADO.IDTIPOEJG
																								// = "+idUltimoEstado+"
		sql.INNER_JOIN(
				"SCS_MAESTROESTADOSEJG MAESTROESTADO ON ESTADO.IDESTADOEJG = MAESTROESTADO.IDESTADOEJG AND MAESTROESTADO.VISIBLECOMISION = 1"); // AND
																																				// MAESTROESTADO.VISIBLECOMISION
																																				// =
																																				// 1
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS REC ON REC.IDRECURSO = MAESTROESTADO.DESCRIPCION AND REC.IDLENGUAJE = '"
				+ idLenguaje + "'");

		// where
		sql.WHERE("ejg.idinstitucion = " + idInstitucion);
		if (ejgItem.getAnnio() != null && ejgItem.getAnnio() != "")
			sql.WHERE("ejg.anio =" + ejgItem.getAnnio());
		if (ejgItem.getNumero() != null && ejgItem.getNumero() != "")
			sql.WHERE("EJG.NUMEJG =" + ejgItem.getNumero());
		if (ejgItem.getTipoEJG() != null && ejgItem.getTipoEJG() != "")
			sql.WHERE("ejg.IDTIPOEJG IN (" + ejgItem.getTipoEJG() + ")");
		if (ejgItem.getTipoEJGColegio() != null && ejgItem.getTipoEJGColegio() != "")
			sql.WHERE("ejg.idtipoejgcolegio IN (" + ejgItem.getTipoEJGColegio() + ")");
		if (ejgItem.getCreadoDesde() != null && ejgItem.getCreadoDesde() != "")
			sql.WHERE("ejg.origenapertura IN (" + ejgItem.getCreadoDesde() + ")");
		if (ejgItem.getProcedimiento() != null && ejgItem.getProcedimiento() != "")
			sql.WHERE("regexp_like(EJG.NUMEROPROCEDIMIENTO,'" + ejgItem.getProcedimiento() + "')");
		if (ejgItem.getEstadoEJG() != null && ejgItem.getEstadoEJG() != "")
			sql.WHERE("estado.idestadoejg IN (" + ejgItem.getEstadoEJG() + ")");
		if (ejgItem.getFechaAperturaDesd() != null) {
			fechaAperturaDesd = dateFormat.format(ejgItem.getFechaAperturaDesd());
			sql.WHERE("EJG.FECHAAPERTURA >= TO_DATE( '" + fechaAperturaDesd + "','DD/MM/RRRR')");
		}
		if (ejgItem.getFechaAperturaHast() != null) {
			fechaAperturaHast = dateFormat.format(ejgItem.getFechaAperturaHast());
			sql.WHERE("EJG.FECHAAPERTURA <= TO_DATE( '" + fechaAperturaHast + "','DD/MM/RRRR')");
		}
		if (ejgItem.getFechaEstadoDesd() != null) {
			fechaEstadoDesd = dateFormat.format(ejgItem.getFechaEstadoDesd());
			sql.WHERE("TO_CHAR(ESTADO.FECHAINICIO,'DD/MM/RRRR') >= TO_DATE( '" + fechaEstadoDesd + "','DD/MM/RRRR')");
		}
		if (ejgItem.getFechaEstadoHast() != null) {
			fechaEstadoHast = dateFormat.format(ejgItem.getFechaEstadoHast());
			sql.WHERE("TO_CHAR(ESTADO.FECHAINICIO,'DD/MM/RRRR') <= TO_DATE( '" + fechaEstadoHast + "','DD/MM/RRRR')");

		}
		if (ejgItem.getFechaLimiteDesd() != null) {
			fechaLimiteDesd = dateFormat.format(ejgItem.getFechaLimiteDesd());
			sql.WHERE("TO_CHAR(EJG.FECHALIMITEPRESENTACION,'DD/MM/RRRR') >= TO_DATE( '" + fechaLimiteDesd
					+ "','DD/MM/RRRR')");
		}
		if (ejgItem.getFechaLimiteHast() != null) {
			fechaLimiteHast = dateFormat.format(ejgItem.getFechaLimiteHast());
			sql.WHERE("TO_CHAR(EJG.FECHALIMITEPRESENTACION,'DD/MM/RRRR') <= TO_DATE( '" + fechaLimiteHast
					+ "','DD/MM/RRRR')");
		}
		if (ejgItem.getDictamen() != null && !ejgItem.getDictamen().isEmpty()) {
			String[] selectedDict = ejgItem.getDictamen().split(",");
			for (String dictamen : selectedDict) {
				if (!dictamen.equals("-1")) {
					dictamenCad += dictamen + ",";
				} else {
					indiferente = true;
				}
			}

			if (!indiferente) {
				dictamenCad = dictamenCad.substring(0, (dictamenCad.length() - 1));
				sql.WHERE("EJG.IDTIPODICTAMENEJG IN (" + dictamenCad + ")");
			}

		}
		if (ejgItem.getFundamentoCalif() != null)
			sql.WHERE("EJG.IDFUNDAMENTOCALIF = " + ejgItem.getFundamentoCalif());
		if (ejgItem.getFechaDictamenDesd() != null) {
			fechaDictamenDesd = dateFormat.format(ejgItem.getFechaDictamenDesd());
			sql.WHERE("TO_CHAR(EJG.FECHADICTAMEN,'DD/MM/RRRR') >= TO_DATE( '" + fechaDictamenDesd + "','DD/MM/RRRR')");
		}
		if (ejgItem.getFechaDictamenHast() != null) {
			fechaDictamenHast = dateFormat.format(ejgItem.getFechaDictamenHast());
			sql.WHERE("TO_CHAR(EJG.FECHADICTAMEN,'DD/MM/RRRR') <= TO_DATE( '" + fechaDictamenHast + "','DD/MM/RRRR')");
		}
		if (ejgItem.getResolucion() != null)
			sql.WHERE("EJG.IDTIPORATIFICACIONEJG = " + ejgItem.getResolucion());
		if (ejgItem.getFundamentoJuridico() != null && ejgItem.getFundamentoJuridico() != "")
			sql.WHERE("EJG.IDFUNDAMENTOJURIDICO = " + ejgItem.getFundamentoJuridico());
		if (ejgItem.getFechaResolucionDesd() != null) {
			fechaResolucionDesd = dateFormat.format(ejgItem.getFechaResolucionDesd());
			sql.WHERE("TO_CHAR(EJG.FECHARESOLUCIONCAJG,'DD/MM/RRRR') >= TO_DATE( '" + fechaResolucionDesd
					+ "','DD/MM/RRRR')");
		}
		if (ejgItem.getFechaResolucionHast() != null) {
			fechaResolucionHast = dateFormat.format(ejgItem.getFechaResolucionHast());
			sql.WHERE("TO_CHAR(EJG.FECHARESOLUCIONCAJG,'DD/MM/RRRR') <= TO_DATE( '" + fechaResolucionHast
					+ "','DD/MM/RRRR')");

		}
		if (ejgItem.getImpugnacion() != null && ejgItem.getImpugnacion() != "")
			sql.WHERE("EJG.IDTIPORESOLAUTO = " + ejgItem.getImpugnacion());
		if (ejgItem.getFundamentoImpuganacion() != null && ejgItem.getFundamentoImpuganacion() != "")
			sql.WHERE("EJG.IDTIPOSENTIDOAUTO = " + ejgItem.getFundamentoImpuganacion());
		if (ejgItem.getFechaImpugnacionDesd() != null) {
			fechaImpugnacionDesd = dateFormat.format(ejgItem.getFechaImpugnacionDesd());
			sql.WHERE("TO_CHAR(EJG.FECHAAUTO,'DD/MM/RRRR') >= TO_DATE( '" + fechaImpugnacionDesd + "','DD/MM/RRRR')");
		}
		if (ejgItem.getFechaImpugnacionHast() != null) {
			fechaImpugnacionHast = dateFormat.format(ejgItem.getFechaImpugnacionHast());
			sql.WHERE("TO_CHAR(EJG.FECHAAUTO,'DD/MM/RRRR') <= TO_DATE( '" + fechaImpugnacionHast + "','DD/MM/RRRR')");

		}
		if (ejgItem.getJuzgado() != null && ejgItem.getJuzgado() != "")
			sql.WHERE("EJG.JUZGADO = " + ejgItem.getJuzgado());
		if (ejgItem.getNumAnnioProcedimiento() != null && ejgItem.getNumAnnioProcedimiento() != "")
			sql.WHERE("regexp_like(EJG.NUMEROPROCEDIMIENTO || EJG.ANIOPROCEDIMIENTO, '"
					+ ejgItem.getNumAnnioProcedimiento() + "')");
		if (ejgItem.getAsunto() != null && ejgItem.getAsunto() != "")
			sql.WHERE("regexp_like(EJG.OBSERVACIONES,'" + ejgItem.getAsunto() + "')");
		if (ejgItem.getNig() != null && ejgItem.getNig() != "")
			sql.WHERE("regexp_like(EJG.NIG,'" + ejgItem.getNig() + "'))");
		if (ejgItem.getPerceptivo() != null && ejgItem.getPerceptivo() != "")
			sql.WHERE("EJG.IDPRECEPTIVO = " + ejgItem.getPerceptivo());
		if (ejgItem.getCalidad() != null && ejgItem.getCalidad() != "")
			sql.WHERE("EJG.CALIDAD = '" + ejgItem.getCalidad() + "'");
		if (ejgItem.getRenuncia() != null && ejgItem.getRenuncia() != "")
			sql.WHERE("EJG.IDRENUNCIA = " + ejgItem.getRenuncia());
		if (ejgItem.getPonente() != null && ejgItem.getPonente() != "")
			sql.WHERE("EJG.IDPONENTE = " + ejgItem.getPonente());
		if (ejgItem.getFechaPonenteDesd() != null) {
			fechaPonenteDesd = dateFormat.format(ejgItem.getFechaPonenteDesd());
			sql.WHERE("TO_CHAR(EJG.FECHAPRESENTACIONPONENTE,'DD/MM/RRRR') >= TO_DATE( '" + fechaPonenteDesd
					+ "','DD/MM/RRRR')");
		}
		if (ejgItem.getFechaPonenteHast() != null) {
			fechaPonenteHast = dateFormat.format(ejgItem.getFechaPonenteHast());
			sql.WHERE("TO_CHAR(EJG.FECHAPRESENTACIONPONENTE,'DD/MM/RRRR') <= TO_DATE( '" + fechaPonenteHast
					+ "','DD/MM/RRRR')");
		}
		if (ejgItem.getNumCAJG() != null && ejgItem.getNumCAJG() != "")
			sql.WHERE("regexp_like(EJG.NUMERO_CAJG || EJG.ANIOCAJG,'" + ejgItem.getNumCAJG() + "')");
		if (ejgItem.getAnnioActa() != null && ejgItem.getAnnioActa() != "")
			sql.WHERE(condicionAnnioNumActas);
		if (ejgItem.getNumRegRemesa1() != null && ejgItem.getNumRegRemesa1() != ""
				|| ejgItem.getNumRegRemesa2() != null && ejgItem.getNumRegRemesa2() != ""
				|| ejgItem.getNumRegRemesa3() != null && ejgItem.getNumRegRemesa3() != "")
			sql.WHERE(condicionNumRegRemesa);

		if (ejgItem.getAnnioCAJG() != null && ejgItem.getAnnioCAJG() != "")
			sql.WHERE("EJG.aniocajg = " + ejgItem.getAnnioCAJG());
		// logica rol
		if (ejgItem.getRol() != null && ejgItem.getRol() != "") {
			if (ejgItem.getRol().equals("1")) {
				if (ejgItem.getNif() != null && ejgItem.getNif() != "")
					sql.WHERE("perjg.NIF = '" + ejgItem.getNif() + "'");
				if (ejgItem.getApellidos() != null && ejgItem.getApellidos() != "") {
					String columna = "REPLACE(CONCAT(perjg.apellido1,perjg.apellido2), ' ', '')";
					String cadena = ejgItem.getApellidos().replaceAll("\\s+", "");
					sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
				}
				if (ejgItem.getNombre() != null && ejgItem.getNombre() != "") {
					String columna = "perjg.NOMBRE";
					String cadena = ejgItem.getNombre();
					sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
				}
			} else if (ejgItem.getRol().equals("4")) {
				sql.INNER_JOIN(
						"scs_unidadfamiliarejg unidadFamiliar on unidadFamiliar.idinstitucion = ejg.idinstitucion and unidadFamiliar.idtipoejg = ejg.idtipoejg");
				sql.WHERE("unidadFamiliar.anio = ejg.anio and unidadFamiliar.numero = ejg.numero");
				sql.INNER_JOIN(
						"scs_personajg perjgunidadfamiliar on perjgunidadfamiliar.idpersona = unidadFamiliar.idpersona AND perjgunidadfamiliar.IDINSTITUCION = unidadFamiliar.IDINSTITUCION");
				if (ejgItem.getNif() != null && ejgItem.getNif() != "")
					sql.WHERE("perjgunidadfamiliar.NIF = " + ejgItem.getNif());
				if (ejgItem.getApellidos() != null && ejgItem.getApellidos() != "") {
					String columna = "REPLACE(CONCAT(perjgunidadfamiliar.apellido1,perjgunidadfamiliar.apellido2), ' ', '')";
					String cadena = ejgItem.getApellidos().replaceAll("\\s+", "");
					sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
				}
				if (ejgItem.getNombre() != null && ejgItem.getNombre() != "") {
					String columna = "perjgunidadfamiliar.NOMBRE";
					String cadena = ejgItem.getNombre();
					sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
				}
			} else if (ejgItem.getRol().equals("2")) {
				sql.INNER_JOIN(
						"scs_contrariosejg contrario on contrario.idinstitucion = ejg.idinstitucion and contrario.idtipoejg = ejg.idtipoejg");
				sql.WHERE("contrario.anio = ejg.anio and contrario.numero = ejg.numero");
				sql.INNER_JOIN(
						"scs_personajg perjgcontrario on perjgcontrario.idpersona = contrario.idpersona AND perjgcontrario.IDINSTITUCION = contrario.IDINSTITUCION");
				if (ejgItem.getNif() != null && ejgItem.getNif() != "")
					sql.WHERE("PERJCONTRARIO.NIF = '" + ejgItem.getNif() + "'");
				if (ejgItem.getApellidos() != null && ejgItem.getApellidos() != "") {
					String columna = "REPLACE(CONCAT(perjgcontrario.apellido1,perjgcontrario.apellido2), ' ', '')";
					String cadena = ejgItem.getApellidos().replaceAll("\\s+", "");
					sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
				}
				if (ejgItem.getNombre() != null && ejgItem.getNombre() != "") {
					String columna = "perjgcontrario.NOMBRE";
					String cadena = ejgItem.getNombre();
					sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
				}
			} else if (ejgItem.getRol().equals("3")) {
				sql.INNER_JOIN(
						"scs_unidadfamiliarejg solicitante on solicitante.idinstitucion = ejg.idinstitucion and solicitante.idtipoejg = ejg.idtipoejg");
				sql.WHERE(
						"solicitante.anio = ejg.anio and solicitante.numero = ejg.numero AND solicitante.solicitante = 1");
				sql.INNER_JOIN(
						"scs_personajg perjgsolicitante on perjgsolicitante.idrepresentanteejg = solicitante.idpersona AND perjgsolicitante.IDINSTITUCION = unidadFamiliar.IDINSTITUCION");
				if (ejgItem.getNif() != null && ejgItem.getNif() != "")
					sql.WHERE("PERJUNIDADFAMILIAR.NIF = '" + ejgItem.getNif() + "'");
				if (ejgItem.getApellidos() != null && ejgItem.getApellidos() != "") {
					String columna = "REPLACE(CONCAT(perjgunidadfamiliar.apellido1,perjgunidadfamiliar.apellido2), ' ', '')";
					String cadena = ejgItem.getApellidos().replaceAll("\\s+", "");
					sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
				}
				if (ejgItem.getNombre() != null && ejgItem.getNombre() != "") {
					String columna = "perjgunidadfamiliar.NOMBRE";
					String cadena = ejgItem.getNombre();
					sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
				}
			}
		} else {
			if (ejgItem.getNif() != null && ejgItem.getNif() != "")
				sql.WHERE("perjg.NIF = '" + ejgItem.getNif() + "'");
			if (ejgItem.getApellidos() != null && ejgItem.getApellidos() != "") {
				String columna = "REPLACE(CONCAT(perjg.apellido1,perjg.apellido2), ' ', '')";
				String cadena = ejgItem.getApellidos().replaceAll("\\s+", "");
				sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
			}
			if (ejgItem.getNombre() != null && ejgItem.getNombre() != "") {
				String columna = "perjg.NOMBRE";
				String cadena = ejgItem.getNombre();
				sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
			}
		}
		if (ejgItem.getTipoLetrado() != null && ejgItem.getTipoLetrado() != "") {
			if (ejgItem.getNumColegiado() != null && ejgItem.getNumColegiado() != "") {
				if (ejgItem.getTipoLetrado().equals("E")) {
					// letrado tramitador
					if (ejgItem.getIdPersona() != null && ejgItem.getIdPersona() != "")
						sql.WHERE("PER.IDPERSONA = " + ejgItem.getIdPersona());
					if (ejgItem.getTurno() != null && ejgItem.getTurno() != "")
						sql.WHERE("EJG.GUARDIATURNO_IDTURNO = " + ejgItem.getTurno());
					if (ejgItem.getGuardia() != null && ejgItem.getGuardia() != "")
						sql.WHERE("EJG.GUARDIATURNO_IDGUARDIA " + ejgItem.getGuardia());
				} else if (ejgItem.getTipoLetrado().equals("D")) {
					// letrado designas
					sql.INNER_JOIN(
							"SCS_EJGDESIGNA EJGDESIGNA ON EJGDESIGNA.IDINSTITUCION = EJG.idinstitucion and EJGDESIGNA.idtipoejg = ejg.idtipoejg and EJGDESIGNA.ANIOEJG = ejg.anio and EJGDESIGNA.numeroejg = ejg.numero");
					sql.INNER_JOIN(
							"SCS_DESIGNA DESIGNA ON EJGDESIGNA.IDINSTITUCION = DESIGNA.idinstitucion and EJGDESIGNA.IDTURNO = DESIGNA.IDTURNO and EJGDESIGNA.ANIO = DESIGNA.ANIO and EJGDESIGNA.numero = DESIGNA.NUMERO");
					sql.INNER_JOIN(
							"SCS_DESIGNASLETRADO DESIGNALETRADO ON DESIGNALETRADO.idinstitucion = DESIGNA.idinstitucion  and DESIGNALETRADO.idturno = DESIGNA.idturno and DESIGNALETRADO.anio = DESIGNA.anio and DESIGNALETRADO.numero = DESIGNA.numero");
					if (ejgItem.getIdPersona() != null && ejgItem.getIdPersona() != "")
						sql.WHERE("DESIGNALETRADO.IDPERSONA = " + ejgItem.getIdPersona());
					if (ejgItem.getTurno() != null && ejgItem.getTurno() != "")
						sql.WHERE("DESIGNA.IDTURNO = " + ejgItem.getTurno());
				} else if (ejgItem.getTipoLetrado().equals("A")) {
					// letrado asistencias
					sql.INNER_JOIN(
							"SCS_ASISTENCIA ASISTENCIA ON ASISTENCIA.IDINSTITUCION = EJG.idinstitucion and ASISTENCIA.EJGIDTIPOEJG = ejg.idtipoejg and ASISTENCIA.EJGANIO = ejg.anio and ASISTENCIA.ejgnumero = ejg.numero");
					if (ejgItem.getIdPersona() != null && ejgItem.getIdPersona() != "")
						sql.WHERE("ASISTENCIA.IDPERSONACOLEGIADO = " + ejgItem.getIdPersona());
					if (ejgItem.getTurno() != null && ejgItem.getTurno() != "")
						sql.WHERE("ASISTENCIA.IDTURNO = " + ejgItem.getTurno());
					if (ejgItem.getGuardia() != null && ejgItem.getGuardia() != "")
						sql.WHERE("ASISTENCIA.IDGUARDIA = " + ejgItem.getGuardia());
				}
			} else {
				if (ejgItem.getTipoLetrado().equals("E")) {
					// letrado tramitador
					if (ejgItem.getTurno() != null && ejgItem.getTurno() != "")
						sql.WHERE("EJG.GUARDIATURNO_IDTURNO = " + ejgItem.getTurno());
					if (ejgItem.getGuardia() != null && ejgItem.getGuardia() != "")
						sql.WHERE("EJG.GUARDIATURNO_IDGUARDIA = " + ejgItem.getGuardia());
				} else if (ejgItem.getTipoLetrado().equals("D")) {
					// letrado designas
					sql.INNER_JOIN(
							"SCS_EJGDESIGNA EJGDESIGNA ON EJGDESIGNA.IDINSTITUCION = EJG.idinstitucion and EJGDESIGNA.idtipoejg = ejg.idtipoejg and EJGDESIGNA.ANIOEJG = ejg.anio and EJGDESIGNA.numeroejg = ejg.numero");
					if (ejgItem.getTurno() != null && ejgItem.getTurno() != "")
						sql.WHERE("EJGDESIGNA.IDTURNO = " + ejgItem.getTurno());
				} else if (ejgItem.getTipoLetrado().equals("A")) {
					// letrado asistencias
					sql.INNER_JOIN(
							"SCS_ASISTENCIA ASISTENCIA ON ASISTENCIA.IDINSTITUCION = EJG.idinstitucion and ASISTENCIA.EJGIDTIPOEJG = ejg.idtipoejg and ASISTENCIA.EJGANIO = ejg.anio and ASISTENCIA.ejgnumero = ejg.numero");
					if (ejgItem.getTurno() != null && ejgItem.getTurno() != "")
						sql.WHERE("ASISTENCIA.IDTURNO = " + ejgItem.getTurno());
					if (ejgItem.getGuardia() != null && ejgItem.getGuardia() != "")
						sql.WHERE("ASISTENCIA.IDGUARDIA = " + ejgItem.getGuardia());
				}
			}
		} else {
			if (ejgItem.getIdPersona() != null && ejgItem.getIdPersona() != "")
				sql.WHERE("PER.IDPERSONA = " + ejgItem.getIdPersona());
			if (ejgItem.getTurno() != null && ejgItem.getTurno() != "")
				sql.WHERE("EJG.GUARDIATURNO_IDTURNO = " + ejgItem.getTurno());
			if (ejgItem.getGuardia() != null && ejgItem.getGuardia() != "")
				sql.WHERE("EJG.GUARDIATURNO_IDGUARDIA = " + ejgItem.getGuardia());
		}
		if (tamMaximo != null) {
			Integer tamMaxNumber = tamMaximo + 1;
			sql.WHERE("rownum < " + tamMaxNumber);
			
		}
		sql.ORDER_BY("TURNO ASC, GUARDIA.NOMBRE ASC");
		LOGGER.info("*******************busquedaejg********************" + sql.toString());
		return sql.toString();
	}
	
	public String busquedaEJGActaComision(EjgItem ejgItem, String idInstitucion, Integer tamMaximo,
			String idLenguaje) {
		String dictamenCad = "";
		boolean indiferente = false;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String fechaAperturaDesd;
		String fechaAperturaHast;
		String fechaEstadoDesd;
		String fechaEstadoHast;
		String fechaLimiteDesd;
		String fechaLimiteHast;
		String fechaDictamenDesd;
		String fechaDictamenHast;
		String fechaImpugnacionDesd;
		String fechaImpugnacionHast;
		String fechaPonenteDesd;
		String fechaPonenteHast;
		String fechaResolucionDesd;
		String fechaResolucionHast;
		String resolucion;
		SQL sql = new SQL();

		resolucion = "(SELECT F_SIGA_GETRECURSO(tipores.DESCRIPCION, 1) from SCS_TIPORESOLUCION tipores where tipores.IDTIPORESOLUCION = ejg.idtiporatificacionejg) RESOLUCION";
		
		String condicionAnnioNumActas = " (EXISTS (SELECT 1 FROM scs_ejg_acta ejgacta, scs_actacomision ac"
				+ " WHERE ejgacta.idinstitucionacta = ac.idinstitucion" + " AND ejgacta.idacta = ac.idacta"
				+ " AND   ejgacta.anioacta = ac.anioacta" + " AND   ejgacta.idinstitucionejg = ejg.idinstitucion"
				+ " AND   ejgacta.anioejg = ejg.anio" + " AND   ejgacta.idtipoejg = ejg.idtipoejg"
				+ " AND   ejgacta.numeroejg = ejg.numero" + " AND   ac.idinstitucion = " + idInstitucion;

		if (ejgItem.getAnnioActa() != null && ejgItem.getAnnioActa() != "")
			condicionAnnioNumActas = condicionAnnioNumActas + " AND   ac.anioacta = " + ejgItem.getAnnioActa();
		if (ejgItem.getNumActa() != null && ejgItem.getNumActa() != "")
			condicionAnnioNumActas = condicionAnnioNumActas + " AND   ac.numeroacta = '" + ejgItem.getNumActa() + "'";
		condicionAnnioNumActas = condicionAnnioNumActas + "))";

		String condicionNumRegRemesa = " (EXISTS (SELECT 1 FROM cajg_ejgremesa ejgremesa, cajg_remesa remesa"
				+ " WHERE ejgremesa.idinstitucionremesa = remesa.idinstitucion"
				+ " AND ejgremesa.idinstitucion = ejg.idinstitucion" + " AND   ejgremesa.anio = ejg.anio"
				+ " AND   ejgremesa.idtipoejg = ejg.idtipoejg" + " AND   ejgremesa.numero = ejg.numero"
				+ " AND   remesa.idinstitucion = " + idInstitucion;
		if (ejgItem.getNumRegRemesa1() != null && ejgItem.getNumRegRemesa1() != "")
			condicionNumRegRemesa = condicionNumRegRemesa + " AND   remesa.prefijo = '" + ejgItem.getNumRegRemesa1()
					+ "'";
		if (ejgItem.getNumRegRemesa2() != null && ejgItem.getNumRegRemesa2() != "")
			condicionNumRegRemesa = condicionNumRegRemesa + " AND   remesa.numero = '" + ejgItem.getNumRegRemesa2()
					+ "'";
		if (ejgItem.getNumRegRemesa3() != null && ejgItem.getNumRegRemesa3() != "")
			condicionNumRegRemesa = condicionNumRegRemesa + " AND   remesa.sufijo = '" + ejgItem.getNumRegRemesa3()
					+ "'";
		condicionNumRegRemesa = condicionNumRegRemesa + "))";

		// select
		sql.SELECT("ejg.anio");
		sql.SELECT("ejg.idinstitucion");
		sql.SELECT("ejg.idtipoejg");
		sql.SELECT("ejg.numero");
		sql.SELECT("ejg.numejg numejg");
		sql.SELECT("'E' || EJG.ANIO || '/' || EJG.NUMEJG AS NUMANIO");
		sql.SELECT(
				"(CASE WHEN TURNO.NOMBRE is  NULL THEN '' ELSE TURNO.NOMBRE || '/' || GUARDIA.NOMBRE END) AS TURNOGUARDIA");
		sql.SELECT("TURNO.ABREVIATURA AS TURNO");
		sql.SELECT("EJG.GUARDIATURNO_IDTURNO as IDTURNO");
		sql.SELECT("ejg.fechaapertura");
		sql.SELECT("ejg.fechamodificacion");
		sql.SELECT(
				"(CASE WHEN per.nombre is  NULL THEN '' ELSE per.apellidos1 || ' ' || per.apellidos2 || ', ' || per.nombre END) as NOMBREletrado");
		sql.SELECT("REC.DESCRIPCION AS ESTADOEJG");
		sql.SELECT(
				"(CASE WHEN perjg.nombre is  NULL THEN '' ELSE perjg.apellido1 || ' ' || perjg.apellido2 || ', ' || perjg.nombre END) as NOMBRESOLICITANTE");
		sql.SELECT("EJG.NUMEROPROCEDIMIENTO");
		sql.SELECT("ejg.idpersonajg");
		sql.SELECT("perjg.NIF");
		sql.SELECT(resolucion);

		// from
		sql.FROM("scs_ejg ejg");
		// joins
		sql.LEFT_OUTER_JOIN("cen_persona per on per.idpersona = ejg.idpersona");
		sql.LEFT_OUTER_JOIN(
				"scs_personajg perjg on perjg.idpersona = ejg.idpersonajg AND perjg.IDINSTITUCION = EJG.IDINSTITUCION");
		sql.LEFT_OUTER_JOIN(
				"SCS_TURNO  TURNO ON TURNO.IDINSTITUCION =EJG.IDINSTITUCION AND TURNO.IDTURNO =EJG.GUARDIATURNO_IDTURNO");
		sql.LEFT_OUTER_JOIN(
				"SCS_GUARDIASTURNO GUARDIA  ON GUARDIA.IDINSTITUCION =EJG.IDINSTITUCION AND GUARDIA.IDTURNO =EJG.GUARDIATURNO_IDTURNO  AND GUARDIA.IDGUARDIA =EJG.GUARDIATURNO_IDGUARDIA");
		sql.LEFT_OUTER_JOIN("SCS_ESTADOEJG ESTADO ON (ESTADO.IDINSTITUCION = EJG.IDINSTITUCION "
				+ "AND ESTADO.IDTIPOEJG = EJG.IDTIPOEJG AND ESTADO.IDTIPOEJG = EJG.IDTIPOEJG "
				+ "AND ESTADO.ANIO = EJG.ANIO " + "AND ESTADO.NUMERO = EJG.NUMERO " + "AND ESTADO.FECHABAJA IS NULL "
//				+ "AND ESTADO.idestadoporejg = (SELECT MAX(idestadoporejg) FROM SCS_ESTADOEJG ESTADO2 WHERE (ESTADO.IDINSTITUCION = ESTADO2.IDINSTITUCION "
//				+ "AND ESTADO.IDTIPOEJG = ESTADO2.IDTIPOEJG " + "AND ESTADO.ANIO = ESTADO2.ANIO "
//				+ "AND ESTADO.NUMERO = ESTADO2.NUMERO " + "AND ESTADO2.FECHABAJA IS NULL) "
//				+ "AND ESTADO2.FECHAINICIO = (SELECT MAX(FECHAINICIO) FROM SCS_ESTADOEJG ESTADO3 WHERE (ESTADO3.IDINSTITUCION = ESTADO2.IDINSTITUCION "
//				+ "AND ESTADO.IDTIPOEJG = ESTADO3.IDTIPOEJG " + "AND ESTADO.ANIO = ESTADO3.ANIO "
//				+ "AND ESTADO.NUMERO = ESTADO3.NUMERO " + "AND ESTADO3.FECHABAJA IS NULL)))"
						+ ")"); // AND ESTADO.IDTIPOEJG
																		// = "+idUltimoEstado+"
		sql.LEFT_OUTER_JOIN("SCS_ESTADOEJG ESTADOBIS ON (ESTADOBIS.IDINSTITUCION = EJG.IDINSTITUCION AND ESTADOBIS.IDTIPOEJG = EJG.IDTIPOEJG AND ESTADOBIS.IDTIPOEJG = EJG.IDTIPOEJG AND ESTADOBIS.ANIO = EJG.ANIO AND ESTADOBIS.NUMERO = EJG.NUMERO AND ESTADOBIS.FECHABAJA IS NULL and (ESTADOBIS.IDESTADOPOREJG > ESTADO.IDESTADOPOREJG))\r\n"
				+ "");
		sql.LEFT_OUTER_JOIN(
				"SCS_MAESTROESTADOSEJG MAESTROESTADO ON ESTADO.IDESTADOEJG = MAESTROESTADO.IDESTADOEJG"); // AND
																																				// MAESTROESTADO.VISIBLECOMISION
																																				// =
																																				// 1
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS REC ON REC.IDRECURSO = MAESTROESTADO.DESCRIPCION AND REC.IDLENGUAJE = '"
				+ idLenguaje + "'");

		// where
		sql.WHERE("ejg.idinstitucion = " + idInstitucion);
		sql.WHERE("estadobis.idestadoejg is null");
		if (ejgItem.getAnnio() != null && ejgItem.getAnnio() != "")
			sql.WHERE("ejg.anio =" + ejgItem.getAnnio());
		if (ejgItem.getNumero() != null && ejgItem.getNumero() != "")
			sql.WHERE("EJG.NUMEJG =" + ejgItem.getNumero());
		if (ejgItem.getTipoEJG() != null && ejgItem.getTipoEJG() != "")
			sql.WHERE("ejg.IDTIPOEJG IN (" + ejgItem.getTipoEJG() + ")");
		if (ejgItem.getTipoEJGColegio() != null && ejgItem.getTipoEJGColegio() != "")
			sql.WHERE("ejg.idtipoejgcolegio IN (" + ejgItem.getTipoEJGColegio() + ")");
		if (ejgItem.getCreadoDesde() != null && ejgItem.getCreadoDesde() != "")
			sql.WHERE("ejg.origenapertura IN (" + ejgItem.getCreadoDesde() + ")");
		if (ejgItem.getProcedimiento() != null && ejgItem.getProcedimiento() != "")
			sql.WHERE("regexp_like(EJG.NUMEROPROCEDIMIENTO,'" + ejgItem.getProcedimiento() + "')");
		if (ejgItem.getEstadoEJG() != null && ejgItem.getEstadoEJG() != "")
			sql.WHERE("estado.idestadoejg IN (" + ejgItem.getEstadoEJG() + ")");
		if (ejgItem.getFechaAperturaDesd() != null) {
			fechaAperturaDesd = dateFormat.format(ejgItem.getFechaAperturaDesd());
			sql.WHERE("EJG.FECHAAPERTURA >= TO_DATE( '" + fechaAperturaDesd + "','DD/MM/RRRR')");
		}
		if (ejgItem.getFechaAperturaHast() != null) {
			fechaAperturaHast = dateFormat.format(ejgItem.getFechaAperturaHast());
			sql.WHERE("EJG.FECHAAPERTURA <= TO_DATE( '" + fechaAperturaHast + "','DD/MM/RRRR')");
		}
		if (ejgItem.getFechaEstadoDesd() != null) {
			fechaEstadoDesd = dateFormat.format(ejgItem.getFechaEstadoDesd());
			sql.WHERE("TO_CHAR(ESTADO.FECHAINICIO,'DD/MM/RRRR') >= TO_DATE( '" + fechaEstadoDesd + "','DD/MM/RRRR')");
		}
		if (ejgItem.getFechaEstadoHast() != null) {
			fechaEstadoHast = dateFormat.format(ejgItem.getFechaEstadoHast());
			sql.WHERE("TO_CHAR(ESTADO.FECHAINICIO,'DD/MM/RRRR') <= TO_DATE( '" + fechaEstadoHast + "','DD/MM/RRRR')");

		}
		if (ejgItem.getFechaLimiteDesd() != null) {
			fechaLimiteDesd = dateFormat.format(ejgItem.getFechaLimiteDesd());
			sql.WHERE("TO_CHAR(EJG.FECHALIMITEPRESENTACION,'DD/MM/RRRR') >= TO_DATE( '" + fechaLimiteDesd
					+ "','DD/MM/RRRR')");
		}
		if (ejgItem.getFechaLimiteHast() != null) {
			fechaLimiteHast = dateFormat.format(ejgItem.getFechaLimiteHast());
			sql.WHERE("TO_CHAR(EJG.FECHALIMITEPRESENTACION,'DD/MM/RRRR') <= TO_DATE( '" + fechaLimiteHast
					+ "','DD/MM/RRRR')");
		}
		if (ejgItem.getDictamen() != null && !ejgItem.getDictamen().isEmpty()) {
			String[] selectedDict = ejgItem.getDictamen().split(",");
			for (String dictamen : selectedDict) {
				if (!dictamen.equals("-1")) {
					dictamenCad += dictamen + ",";
				} else {
					indiferente = true;
				}
			}

			if (!indiferente) {
				dictamenCad = dictamenCad.substring(0, (dictamenCad.length() - 1));
				sql.WHERE("EJG.IDTIPODICTAMENEJG IN (" + dictamenCad + ")");
			}

		}
		if (ejgItem.getFundamentoCalif() != null)
			sql.WHERE("EJG.IDFUNDAMENTOCALIF = " + ejgItem.getFundamentoCalif());
		if (ejgItem.getFechaDictamenDesd() != null) {
			fechaDictamenDesd = dateFormat.format(ejgItem.getFechaDictamenDesd());
			sql.WHERE("TO_CHAR(EJG.FECHADICTAMEN,'DD/MM/RRRR') >= TO_DATE( '" + fechaDictamenDesd + "','DD/MM/RRRR')");
		}
		if (ejgItem.getFechaDictamenHast() != null) {
			fechaDictamenHast = dateFormat.format(ejgItem.getFechaDictamenHast());
			sql.WHERE("TO_CHAR(EJG.FECHADICTAMEN,'DD/MM/RRRR') <= TO_DATE( '" + fechaDictamenHast + "','DD/MM/RRRR')");
		}
		if (ejgItem.getResolucion() != null)
			sql.WHERE("EJG.IDTIPORATIFICACIONEJG = " + ejgItem.getResolucion());
		if (ejgItem.getFundamentoJuridico() != null && ejgItem.getFundamentoJuridico() != "")
			sql.WHERE("EJG.IDFUNDAMENTOJURIDICO = " + ejgItem.getFundamentoJuridico());
		if (ejgItem.getFechaResolucionDesd() != null) {
			fechaResolucionDesd = dateFormat.format(ejgItem.getFechaResolucionDesd());
			sql.WHERE("TO_CHAR(EJG.FECHARESOLUCIONCAJG,'DD/MM/RRRR') >= TO_DATE( '" + fechaResolucionDesd
					+ "','DD/MM/RRRR')");
		}
		if (ejgItem.getFechaResolucionHast() != null) {
			fechaResolucionHast = dateFormat.format(ejgItem.getFechaResolucionHast());
			sql.WHERE("TO_CHAR(EJG.FECHARESOLUCIONCAJG,'DD/MM/RRRR') <= TO_DATE( '" + fechaResolucionHast
					+ "','DD/MM/RRRR')");

		}
		if (ejgItem.getImpugnacion() != null && ejgItem.getImpugnacion() != "")
			sql.WHERE("EJG.IDTIPORESOLAUTO = " + ejgItem.getImpugnacion());
		if (ejgItem.getFundamentoImpuganacion() != null && ejgItem.getFundamentoImpuganacion() != "")
			sql.WHERE("EJG.IDTIPOSENTIDOAUTO = " + ejgItem.getFundamentoImpuganacion());
		if (ejgItem.getFechaImpugnacionDesd() != null) {
			fechaImpugnacionDesd = dateFormat.format(ejgItem.getFechaImpugnacionDesd());
			sql.WHERE("TO_CHAR(EJG.FECHAAUTO,'DD/MM/RRRR') >= TO_DATE( '" + fechaImpugnacionDesd + "','DD/MM/RRRR')");
		}
		if (ejgItem.getFechaImpugnacionHast() != null) {
			fechaImpugnacionHast = dateFormat.format(ejgItem.getFechaImpugnacionHast());
			sql.WHERE("TO_CHAR(EJG.FECHAAUTO,'DD/MM/RRRR') <= TO_DATE( '" + fechaImpugnacionHast + "','DD/MM/RRRR')");

		}
		if (ejgItem.getJuzgado() != null && ejgItem.getJuzgado() != "")
			sql.WHERE("EJG.JUZGADO = " + ejgItem.getJuzgado());
		if (ejgItem.getNumAnnioProcedimiento() != null && ejgItem.getNumAnnioProcedimiento() != "")
			sql.WHERE("regexp_like(EJG.NUMEROPROCEDIMIENTO || EJG.ANIOPROCEDIMIENTO, '"
					+ ejgItem.getNumAnnioProcedimiento() + "')");
		if (ejgItem.getAsunto() != null && ejgItem.getAsunto() != "")
			sql.WHERE("regexp_like(EJG.OBSERVACIONES,'" + ejgItem.getAsunto() + "')");
		if (ejgItem.getNig() != null && ejgItem.getNig() != "")
			sql.WHERE("regexp_like(EJG.NIG,'" + ejgItem.getNig() + "'))");
		if (ejgItem.getPerceptivo() != null && ejgItem.getPerceptivo() != "")
			sql.WHERE("EJG.IDPRECEPTIVO = " + ejgItem.getPerceptivo());
		if (ejgItem.getCalidad() != null && ejgItem.getCalidad() != "")
			sql.WHERE("EJG.CALIDAD = '" + ejgItem.getCalidad() + "'");
		if (ejgItem.getRenuncia() != null && ejgItem.getRenuncia() != "")
			sql.WHERE("EJG.IDRENUNCIA = " + ejgItem.getRenuncia());
		if (ejgItem.getPonente() != null && ejgItem.getPonente() != "")
			sql.WHERE("EJG.IDPONENTE = " + ejgItem.getPonente());
		if (ejgItem.getFechaPonenteDesd() != null) {
			fechaPonenteDesd = dateFormat.format(ejgItem.getFechaPonenteDesd());
			sql.WHERE("TO_CHAR(EJG.FECHAPRESENTACIONPONENTE,'DD/MM/RRRR') >= TO_DATE( '" + fechaPonenteDesd
					+ "','DD/MM/RRRR')");
		}
		if (ejgItem.getFechaPonenteHast() != null) {
			fechaPonenteHast = dateFormat.format(ejgItem.getFechaPonenteHast());
			sql.WHERE("TO_CHAR(EJG.FECHAPRESENTACIONPONENTE,'DD/MM/RRRR') <= TO_DATE( '" + fechaPonenteHast
					+ "','DD/MM/RRRR')");
		}
		if (ejgItem.getNumCAJG() != null && ejgItem.getNumCAJG() != "")
			sql.WHERE("regexp_like(EJG.NUMERO_CAJG || EJG.ANIOCAJG,'" + ejgItem.getNumCAJG() + "')");
		if (ejgItem.getAnnioActa() != null && ejgItem.getAnnioActa() != "")
			sql.WHERE(condicionAnnioNumActas);
		if (ejgItem.getNumRegRemesa1() != null && ejgItem.getNumRegRemesa1() != ""
				|| ejgItem.getNumRegRemesa2() != null && ejgItem.getNumRegRemesa2() != ""
				|| ejgItem.getNumRegRemesa3() != null && ejgItem.getNumRegRemesa3() != "")
			sql.WHERE(condicionNumRegRemesa);

		if (ejgItem.getAnnioCAJG() != null && ejgItem.getAnnioCAJG() != "")
			sql.WHERE("EJG.aniocajg = " + ejgItem.getAnnioCAJG());
		// logica rol
		if (ejgItem.getRol() != null && ejgItem.getRol() != "") {
			if (ejgItem.getRol().equals("1")) {
				if (ejgItem.getNif() != null && ejgItem.getNif() != "")
					sql.WHERE("perjg.NIF = '" + ejgItem.getNif() + "'");
				if (ejgItem.getApellidos() != null && ejgItem.getApellidos() != "") {
					String columna = "REPLACE(CONCAT(perjg.apellido1,perjg.apellido2), ' ', '')";
					String cadena = ejgItem.getApellidos().replaceAll("\\s+", "");
					sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
				}
				if (ejgItem.getNombre() != null && ejgItem.getNombre() != "") {
					String columna = "perjg.NOMBRE";
					String cadena = ejgItem.getNombre();
					sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
				}
			} else if (ejgItem.getRol().equals("4")) {
				sql.INNER_JOIN(
						"scs_unidadfamiliarejg unidadFamiliar on unidadFamiliar.idinstitucion = ejg.idinstitucion and unidadFamiliar.idtipoejg = ejg.idtipoejg");
				sql.WHERE("unidadFamiliar.anio = ejg.anio and unidadFamiliar.numero = ejg.numero");
				sql.INNER_JOIN(
						"scs_personajg perjgunidadfamiliar on perjgunidadfamiliar.idpersona = unidadFamiliar.idpersona AND perjgunidadfamiliar.IDINSTITUCION = unidadFamiliar.IDINSTITUCION");
				if (ejgItem.getNif() != null && ejgItem.getNif() != "")
					sql.WHERE("perjgunidadfamiliar.NIF = " + ejgItem.getNif());
				if (ejgItem.getApellidos() != null && ejgItem.getApellidos() != "") {
					String columna = "REPLACE(CONCAT(perjgunidadfamiliar.apellido1,perjgunidadfamiliar.apellido2), ' ', '')";
					String cadena = ejgItem.getApellidos().replaceAll("\\s+", "");
					sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
				}
				if (ejgItem.getNombre() != null && ejgItem.getNombre() != "") {
					String columna = "perjgunidadfamiliar.NOMBRE";
					String cadena = ejgItem.getNombre();
					sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
				}
			} else if (ejgItem.getRol().equals("2")) {
				sql.INNER_JOIN(
						"scs_contrariosejg contrario on contrario.idinstitucion = ejg.idinstitucion and contrario.idtipoejg = ejg.idtipoejg");
				sql.WHERE("contrario.anio = ejg.anio and contrario.numero = ejg.numero");
				sql.INNER_JOIN(
						"scs_personajg perjgcontrario on perjgcontrario.idpersona = contrario.idpersona AND perjgcontrario.IDINSTITUCION = contrario.IDINSTITUCION");
				if (ejgItem.getNif() != null && ejgItem.getNif() != "")
					sql.WHERE("PERJCONTRARIO.NIF = '" + ejgItem.getNif() + "'");
				if (ejgItem.getApellidos() != null && ejgItem.getApellidos() != "") {
					String columna = "REPLACE(CONCAT(perjgcontrario.apellido1,perjgcontrario.apellido2), ' ', '')";
					String cadena = ejgItem.getApellidos().replaceAll("\\s+", "");
					sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
				}
				if (ejgItem.getNombre() != null && ejgItem.getNombre() != "") {
					String columna = "perjgcontrario.NOMBRE";
					String cadena = ejgItem.getNombre();
					sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
				}
			} else if (ejgItem.getRol().equals("3")) {
				sql.INNER_JOIN(
						"scs_unidadfamiliarejg solicitante on solicitante.idinstitucion = ejg.idinstitucion and solicitante.idtipoejg = ejg.idtipoejg");
				sql.WHERE(
						"solicitante.anio = ejg.anio and solicitante.numero = ejg.numero AND solicitante.solicitante = 1");
				sql.INNER_JOIN(
						"scs_personajg perjgsolicitante on perjgsolicitante.idrepresentanteejg = solicitante.idpersona AND perjgsolicitante.IDINSTITUCION = unidadFamiliar.IDINSTITUCION");
				if (ejgItem.getNif() != null && ejgItem.getNif() != "")
					sql.WHERE("PERJUNIDADFAMILIAR.NIF = '" + ejgItem.getNif() + "'");
				if (ejgItem.getApellidos() != null && ejgItem.getApellidos() != "") {
					String columna = "REPLACE(CONCAT(perjgunidadfamiliar.apellido1,perjgunidadfamiliar.apellido2), ' ', '')";
					String cadena = ejgItem.getApellidos().replaceAll("\\s+", "");
					sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
				}
				if (ejgItem.getNombre() != null && ejgItem.getNombre() != "") {
					String columna = "perjgunidadfamiliar.NOMBRE";
					String cadena = ejgItem.getNombre();
					sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
				}
			}
		} else {
			if (ejgItem.getNif() != null && ejgItem.getNif() != "")
				sql.WHERE("perjg.NIF = '" + ejgItem.getNif() + "'");
			if (ejgItem.getApellidos() != null && ejgItem.getApellidos() != "") {
				String columna = "REPLACE(CONCAT(perjg.apellido1,perjg.apellido2), ' ', '')";
				String cadena = ejgItem.getApellidos().replaceAll("\\s+", "");
				sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
			}
			if (ejgItem.getNombre() != null && ejgItem.getNombre() != "") {
				String columna = "perjg.NOMBRE";
				String cadena = ejgItem.getNombre();
				sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
			}
		}
		if (ejgItem.getTipoLetrado() != null && ejgItem.getTipoLetrado() != "") {
			if (ejgItem.getNumColegiado() != null && ejgItem.getNumColegiado() != "") {
				if (ejgItem.getTipoLetrado().equals("E")) {
					// letrado tramitador
					if (ejgItem.getIdPersona() != null && ejgItem.getIdPersona() != "")
						sql.WHERE("PER.IDPERSONA = " + ejgItem.getIdPersona());
					if (ejgItem.getTurno() != null && ejgItem.getTurno() != "")
						sql.WHERE("EJG.GUARDIATURNO_IDTURNO = " + ejgItem.getTurno());
					if (ejgItem.getGuardia() != null && ejgItem.getGuardia() != "")
						sql.WHERE("EJG.GUARDIATURNO_IDGUARDIA " + ejgItem.getGuardia());
				} else if (ejgItem.getTipoLetrado().equals("D")) {
					// letrado designas
					sql.INNER_JOIN(
							"SCS_EJGDESIGNA EJGDESIGNA ON EJGDESIGNA.IDINSTITUCION = EJG.idinstitucion and EJGDESIGNA.idtipoejg = ejg.idtipoejg and EJGDESIGNA.ANIOEJG = ejg.anio and EJGDESIGNA.numeroejg = ejg.numero");
					sql.INNER_JOIN(
							"SCS_DESIGNA DESIGNA ON EJGDESIGNA.IDINSTITUCION = DESIGNA.idinstitucion and EJGDESIGNA.IDTURNO = DESIGNA.IDTURNO and EJGDESIGNA.ANIO = DESIGNA.ANIO and EJGDESIGNA.numero = DESIGNA.NUMERO");
					sql.INNER_JOIN(
							"SCS_DESIGNASLETRADO DESIGNALETRADO ON DESIGNALETRADO.idinstitucion = DESIGNA.idinstitucion  and DESIGNALETRADO.idturno = DESIGNA.idturno and DESIGNALETRADO.anio = DESIGNA.anio and DESIGNALETRADO.numero = DESIGNA.numero");
					if (ejgItem.getIdPersona() != null && ejgItem.getIdPersona() != "")
						sql.WHERE("DESIGNALETRADO.IDPERSONA = " + ejgItem.getIdPersona());
					if (ejgItem.getTurno() != null && ejgItem.getTurno() != "")
						sql.WHERE("DESIGNA.IDTURNO = " + ejgItem.getTurno());
				} else if (ejgItem.getTipoLetrado().equals("A")) {
					// letrado asistencias
					sql.INNER_JOIN(
							"SCS_ASISTENCIA ASISTENCIA ON ASISTENCIA.IDINSTITUCION = EJG.idinstitucion and ASISTENCIA.EJGIDTIPOEJG = ejg.idtipoejg and ASISTENCIA.EJGANIO = ejg.anio and ASISTENCIA.ejgnumero = ejg.numero");
					if (ejgItem.getIdPersona() != null && ejgItem.getIdPersona() != "")
						sql.WHERE("ASISTENCIA.IDPERSONACOLEGIADO = " + ejgItem.getIdPersona());
					if (ejgItem.getTurno() != null && ejgItem.getTurno() != "")
						sql.WHERE("ASISTENCIA.IDTURNO = " + ejgItem.getTurno());
					if (ejgItem.getGuardia() != null && ejgItem.getGuardia() != "")
						sql.WHERE("ASISTENCIA.IDGUARDIA = " + ejgItem.getGuardia());
				}
			} else {
				if (ejgItem.getTipoLetrado().equals("E")) {
					// letrado tramitador
					if (ejgItem.getTurno() != null && ejgItem.getTurno() != "")
						sql.WHERE("EJG.GUARDIATURNO_IDTURNO = " + ejgItem.getTurno());
					if (ejgItem.getGuardia() != null && ejgItem.getGuardia() != "")
						sql.WHERE("EJG.GUARDIATURNO_IDGUARDIA = " + ejgItem.getGuardia());
				} else if (ejgItem.getTipoLetrado().equals("D")) {
					// letrado designas
					sql.INNER_JOIN(
							"SCS_EJGDESIGNA EJGDESIGNA ON EJGDESIGNA.IDINSTITUCION = EJG.idinstitucion and EJGDESIGNA.idtipoejg = ejg.idtipoejg and EJGDESIGNA.ANIOEJG = ejg.anio and EJGDESIGNA.numeroejg = ejg.numero");
					if (ejgItem.getTurno() != null && ejgItem.getTurno() != "")
						sql.WHERE("EJGDESIGNA.IDTURNO = " + ejgItem.getTurno());
				} else if (ejgItem.getTipoLetrado().equals("A")) {
					// letrado asistencias
					sql.INNER_JOIN(
							"SCS_ASISTENCIA ASISTENCIA ON ASISTENCIA.IDINSTITUCION = EJG.idinstitucion and ASISTENCIA.EJGIDTIPOEJG = ejg.idtipoejg and ASISTENCIA.EJGANIO = ejg.anio and ASISTENCIA.ejgnumero = ejg.numero");
					if (ejgItem.getTurno() != null && ejgItem.getTurno() != "")
						sql.WHERE("ASISTENCIA.IDTURNO = " + ejgItem.getTurno());
					if (ejgItem.getGuardia() != null && ejgItem.getGuardia() != "")
						sql.WHERE("ASISTENCIA.IDGUARDIA = " + ejgItem.getGuardia());
				}
			}
		} else {
			if (ejgItem.getIdPersona() != null && ejgItem.getIdPersona() != "")
				sql.WHERE("PER.IDPERSONA = " + ejgItem.getIdPersona());
			if (ejgItem.getTurno() != null && ejgItem.getTurno() != "")
				sql.WHERE("EJG.GUARDIATURNO_IDTURNO = " + ejgItem.getTurno());
			if (ejgItem.getGuardia() != null && ejgItem.getGuardia() != "")
				sql.WHERE("EJG.GUARDIATURNO_IDGUARDIA = " + ejgItem.getGuardia());
		}
		
		sql.ORDER_BY("TURNO ASC, GUARDIA.NOMBRE ASC");
		LOGGER.info("*******************busquedaejgacta********************" + sql.toString());
		return sql.toString();
	}

	public String busquedaActas(ActasItem actasItem) {
		
		String fechaReunion = new SimpleDateFormat("dd/MM/yy").format(actasItem.getFechareunion());
		String fechaResolucion = new SimpleDateFormat("dd/MM/yy").format(actasItem.getFecharesolucion());

		
		
		SQL sql = new SQL();
		sql.SELECT("NUMEROACTA,IDPRESIDENTE,IDSECRETARIO,FECHAREUNION,FECHARESOLUCION ");
		sql.FROM("scs_ACTACOMISION");
		sql.WHERE("NUMEROACTA like '"+actasItem.getActa()+"'");
		sql.WHERE("IDPRESIDENTE like '"+actasItem.getIdpresidente()+"'");
		sql.WHERE("IDSECRETARIO like '"+actasItem.getIdsecretario()+"'");
		sql.WHERE("FECHAREUNION = '"+fechaReunion+"'");
		sql.WHERE("FECHARESOLUCION = '"+fechaResolucion+"'");

		LOGGER.info("*******************busquedaActas********************" + sql.toString());
		return sql.toString();
	}
	
}
