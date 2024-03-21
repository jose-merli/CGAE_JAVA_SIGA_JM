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
		sql.LEFT_OUTER_JOIN(
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
		sql.LEFT_OUTER_JOIN("gen_recursos_catalogos cat on cat.IDRECURSO = tipoejg.descripcion and cat.idlenguaje = '"
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
		sql.LEFT_OUTER_JOIN("gen_recursos_catalogos cat on cat.IDRECURSO = estado.descripcion and cat.idlenguaje = '"
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
		boolean sinDictamen = false;
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
		SQL sql = new SQL();
		SQL sqlUF = new SQL();
		SQL sqlContrarios = new SQL();
		SQL sqlUFRep = new SQL();

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
		sql.SELECT("ejg.numejg numejg");
		
		// from
		sql.FROM("scs_ejg ejg");
		// joins

		if((ejgItem.getNif() != null && ejgItem.getNif() != "") || (ejgItem.getRol() != null && ejgItem.getRol() != "") 
				|| (ejgItem.getApellidos() != null && ejgItem.getApellidos() != "") || (ejgItem.getNombre() != null && ejgItem.getNombre() != "")) {
			sql.LEFT_OUTER_JOIN("scs_personajg perjg on perjg.idpersona = ejg.idpersonajg AND perjg.IDINSTITUCION = EJG.IDINSTITUCION");
		}

				String joinEstado = "SCS_ESTADOEJG ESTADO ON ESTADO.IDINSTITUCION = EJG.IDINSTITUCION AND ESTADO.IDTIPOEJG = EJG.IDTIPOEJG AND ESTADO.ANIO = EJG.ANIO AND ESTADO.NUMERO = EJG.NUMERO ";
        	sql.JOIN(joinEstado);

		sql.INNER_JOIN(
				"SCS_MAESTROESTADOSEJG MAESTROESTADO ON ESTADO.IDESTADOEJG = MAESTROESTADO.IDESTADOEJG AND MAESTROESTADO.VISIBLECOMISION = 1");
		if ((ejgItem.getAnnioActa() != null && ejgItem.getAnnioActa() != "") || (ejgItem.getNumActa() != null && ejgItem.getNumActa() != "")) {
			sql.JOIN("scs_ejg_acta ejgacta ON ejgacta.idinstitucionejg = ejg.idinstitucion AND ejgacta.anioejg = ejg.anio AND ejgacta.idtipoejg = ejg.idtipoejg AND ejgacta.numeroejg = ejg.numero");
			sql.JOIN("scs_actacomision ac ON ejgacta.idinstitucionacta = ac.idinstitucion AND ejgacta.idacta = ac.idacta AND ejgacta.anioacta = ac.anioacta");
		}

		// where
		sql.WHERE("ejg.idinstitucion = " + idInstitucion);
		sql.WHERE("ESTADO.FECHABAJA IS NULL");
		//Arreglo último estado
		if (!(ejgItem.getEstadoEJG() != null && ejgItem.getEstadoEJG() != "")) {
        	sql.WHERE("ESTADO.IDESTADOEJG = F_SIGA_GET_IDULTIMOESTADOEJG (ESTADO.IDINSTITUCION, ESTADO.IDTIPOEJG, ESTADO.ANIO,ESTADO.NUMERO )");
		}
		if (ejgItem.getAnnio() != null && ejgItem.getAnnio() != "")
			sql.WHERE("ejg.anio =" + ejgItem.getAnnio());
		if (ejgItem.getNumero() != null && !ejgItem.getNumero().isEmpty()) {

			String[] parts;

			if (ejgItem.getNumero().trim().contains("-")) {
				parts = ejgItem.getNumero().trim().split("-");

				sql.WHERE("EJG.NUMEJG BETWEEN " + parts[0].trim() + " AND " + parts[1].trim());

			} else {
				sql.WHERE(" EJG.NUMEJG = " + ejgItem.getNumero().trim());
			}
		}
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
			sql.WHERE("TO_DATE(EJG.FECHALIMITEPRESENTACION,'DD/MM/RRRR') >= TO_DATE( '" + fechaLimiteDesd
					+ "','DD/MM/RRRR')");
		}
		if (ejgItem.getFechaLimiteHast() != null) {
			fechaLimiteHast = dateFormat.format(ejgItem.getFechaLimiteHast());
			sql.WHERE("TO_DATE(EJG.FECHALIMITEPRESENTACION,'DD/MM/RRRR') <= TO_DATE( '" + fechaLimiteHast
					+ "','DD/MM/RRRR')");
		}
		if (ejgItem.getDictamen() != null && !ejgItem.getDictamen().isEmpty()) {
			String[] selectedDict = ejgItem.getDictamen().split(",");
			for (String dictamen : selectedDict) {
				if (!dictamen.equals("-1") && !dictamen.equals("0")) {
					dictamenCad += dictamen + ",";
				} else if (dictamen.equals("0")) {
					sinDictamen = true;
				} else {
					indiferente = true;
				}
			}

			if (!indiferente) {
				if (dictamenCad.length() > 0) {
					dictamenCad = dictamenCad.substring(0, (dictamenCad.length() - 1));
					if (sinDictamen) {
						sql.WHERE("(EJG.IDTIPODICTAMENEJG IN (" + dictamenCad + ") OR EJG.IDTIPODICTAMENEJG IS NULL)");
					} else {
						sql.WHERE("EJG.IDTIPODICTAMENEJG IN (" + dictamenCad + ")");
					}
				} else if (sinDictamen) {
					sql.WHERE("EJG.IDTIPODICTAMENEJG IS NULL");
				}
			}

		}
		if (ejgItem.getFundamentoCalif() != null)
			sql.WHERE("EJG.IDFUNDAMENTOCALIF = " + ejgItem.getFundamentoCalif());
		if (ejgItem.getFechaDictamenDesd() != null) {
			fechaDictamenDesd = dateFormat.format(ejgItem.getFechaDictamenDesd());
			sql.WHERE("TO_DATE(EJG.FECHADICTAMEN,'DD/MM/RRRR') >= TO_DATE( '" + fechaDictamenDesd + "','DD/MM/RRRR')");
		}
		if (ejgItem.getFechaDictamenHast() != null) {
			fechaDictamenHast = dateFormat.format(ejgItem.getFechaDictamenHast());
			sql.WHERE("TO_DATE(EJG.FECHADICTAMEN,'DD/MM/RRRR') <= TO_DATE( '" + fechaDictamenHast + "','DD/MM/RRRR')");
		}
		if (ejgItem.getResolucion() != null) {
			if(ejgItem.getResolucion().equals("12")) {
				sql.WHERE("EJG.IDTIPORATIFICACIONEJG IS NULL");
			}else {
				sql.WHERE("EJG.IDTIPORATIFICACIONEJG = " + ejgItem.getResolucion());
			}
		}
		if (ejgItem.getFundamentoJuridico() != null && ejgItem.getFundamentoJuridico() != "")
			sql.WHERE("EJG.IDFUNDAMENTOJURIDICO = " + ejgItem.getFundamentoJuridico());
		if (ejgItem.getFechaResolucionDesd() != null) {
			fechaResolucionDesd = dateFormat.format(ejgItem.getFechaResolucionDesd());
			sql.WHERE("TO_DATE(EJG.FECHARESOLUCIONCAJG,'DD/MM/RRRR') >= TO_DATE( '" + fechaResolucionDesd
					+ "','DD/MM/RRRR')");
		}
		if (ejgItem.getFechaResolucionHast() != null) {
			fechaResolucionHast = dateFormat.format(ejgItem.getFechaResolucionHast());
			sql.WHERE("TO_DATE(EJG.FECHARESOLUCIONCAJG,'DD/MM/RRRR') <= TO_DATE( '" + fechaResolucionHast
					+ "','DD/MM/RRRR')");

		}
		if (ejgItem.getImpugnacion() != null && ejgItem.getImpugnacion() != "")
			sql.WHERE("EJG.IDTIPORESOLAUTO = " + ejgItem.getImpugnacion());
		if (ejgItem.getFundamentoImpuganacion() != null && ejgItem.getFundamentoImpuganacion() != "")
			sql.WHERE("EJG.IDTIPOSENTIDOAUTO = " + ejgItem.getFundamentoImpuganacion());
		if (ejgItem.getFechaImpugnacionDesd() != null) {
			fechaImpugnacionDesd = dateFormat.format(ejgItem.getFechaImpugnacionDesd());
			sql.WHERE("TO_DATE(EJG.FECHAAUTO,'DD/MM/RRRR') >= TO_DATE( '" + fechaImpugnacionDesd + "','DD/MM/RRRR')");
		}
		if (ejgItem.getFechaImpugnacionHast() != null) {
			fechaImpugnacionHast = dateFormat.format(ejgItem.getFechaImpugnacionHast());
			sql.WHERE("TO_DATE(EJG.FECHAAUTO,'DD/MM/RRRR') <= TO_DATE( '" + fechaImpugnacionHast + "','DD/MM/RRRR')");

		}
		if (ejgItem.getJuzgado() != null && ejgItem.getJuzgado() != "")
			sql.WHERE("EJG.JUZGADO = " + ejgItem.getJuzgado());
		if (ejgItem.getNumAnnioProcedimiento() != null && ejgItem.getNumAnnioProcedimiento() != "") {
			sql.WHERE("EJG.NUMEROPROCEDIMIENTO LIKE '%" + ejgItem.getNumAnnioProcedimiento() + "%'");
		}
		if (ejgItem.getAsunto() != null && ejgItem.getAsunto() != "")
			sql.WHERE("EJG.OBSERVACIONES LIKE '%" + ejgItem.getAsunto() + "%'");
		if (ejgItem.getNig() != null && ejgItem.getNig() != "")
			sql.WHERE("EJG.NIG like '%" + ejgItem.getNig() + "%'");
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
			sql.WHERE("TO_DATE(EJG.FECHAPRESENTACIONPONENTE,'DD/MM/RRRR') >= TO_DATE( '" + fechaPonenteDesd
					+ "','DD/MM/RRRR')");
		}
		if (ejgItem.getFechaPonenteHast() != null) {
			fechaPonenteHast = dateFormat.format(ejgItem.getFechaPonenteHast());
			sql.WHERE("TO_DATE(EJG.FECHAPRESENTACIONPONENTE,'DD/MM/RRRR') <= TO_DATE( '" + fechaPonenteHast
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
			// Controlar que Rol es el 1 para añadir el parentesís necesario para la QUERY.
			boolean banderaRolprimero = false;
			// Controlar si ya esta activo el 1 Rol.
			boolean checkRolone = false;
			// Obtener el último Rol.
			int rolUltimo = Integer.parseInt(ejgItem.getRol().substring(ejgItem.getRol().length() - 1));
			// Contador del Rol Actual
			int contadorRolActual = 0;
			// Verificar que contengan Solicitantes.
			if (ejgItem.getRol().contains("1")) {
				/*
				 * Comprobar que el Rol 1 si viene True = Entonces marcarmos TRUE la
				 * banderaRolprimero Compronar que el Rol 1 si viene False = Entonces marcamos
				 * False la banderaRolprimero
				 */
				checkRolone = (checkRolone == true) ? true : false;
				/*
				 * Comprobar que el Rol 1 si viene False = Entonces marcarmos TRUE porque será
				 * el 1 Rol. Compronar que el Rol 1 si viene True = Entonces ya tenemos el 1 Rol
				 * marcado.
				 */
				banderaRolprimero = (checkRolone == true) ? false : true;

				if (ejgItem.getNif() != null && ejgItem.getNif() != "") {
					// Comprobar que solo existe este Rol activado.
					if (ejgItem.getRol().equals("1")) {
						sql.WHERE("perjg.NIF = '" + ejgItem.getNif() + "'");
					} else {
						// Verificar si el 1 Rol que tiene sino no añadir parentesis.
						if (banderaRolprimero == true) {
							sql.WHERE("(( perjg.NIF = '" + ejgItem.getNif() + "'");
							checkRolone = true;
							banderaRolprimero = false;
						} else {
							sql.WHERE("perjg.NIF = '" + ejgItem.getNif() + "'");
						}
					}
				}
				if (ejgItem.getApellidos() != null && ejgItem.getApellidos() != "") {
					String columna = "REPLACE(CONCAT(perjg.apellido1,perjg.apellido2), ' ', '')";
					String cadena = ejgItem.getApellidos().replaceAll("\\s+", "");
					if (ejgItem.getRol().equals("1")) {
						sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
					} else {
						if (banderaRolprimero == true) {
							sql.WHERE(" ( " + UtilidadesString.filtroTextoBusquedas(columna, cadena).substring(0,
									UtilidadesString.filtroTextoBusquedas(columna, cadena).length() - 2));
							checkRolone = true;
							banderaRolprimero = false;
						} else {
							sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena).substring(0,
									UtilidadesString.filtroTextoBusquedas(columna, cadena).length() - 2));
						}
					}
				}
				if (ejgItem.getNombre() != null && ejgItem.getNombre() != "") {
					String columna = "perjg.NOMBRE";
					String cadena = ejgItem.getNombre();
					if (ejgItem.getRol().equals("1")) {
						sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
					} else {
						if (banderaRolprimero == true) {
							sql.WHERE(" ( " + UtilidadesString.filtroTextoBusquedas(columna, cadena).substring(0,
									UtilidadesString.filtroTextoBusquedas(columna, cadena).length() - 2));
							checkRolone = true;
							banderaRolprimero = false;
						} else {
							sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena).substring(0,
									UtilidadesString.filtroTextoBusquedas(columna, cadena).length() - 2));
						}
					}
				}
			}
			// Verificar que contengan Contrarios.
			if (ejgItem.getRol().contains("2")) {
				contadorRolActual = 2;
				// Existe un Rol 1 ya seleccionado.
				checkRolone = (checkRolone == true) ? true : false;
				banderaRolprimero = (checkRolone == true) ? false : true;
				if (ejgItem.getRol().equals("2")) {
					sql.JOIN(
							"scs_contrariosejg contrario ON contrario.idinstitucion = ejg.idinstitucion and contrario.idtipoejg = ejg.idtipoejg "
									+ "and contrario.anio = ejg.anio  and contrario.numero = ejg.numero");
					sql.JOIN(
							"scs_personajg perjgcontrario on perjgcontrario.idpersona = contrario.idpersona AND perjgcontrario.IDINSTITUCION = contrario.IDINSTITUCION");
					if (ejgItem.getNif() != null && ejgItem.getNif() != "")
						sql.WHERE("perjgcontrario.NIF = '" + ejgItem.getNif() + "'");
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
				} else {
					sqlContrarios.SELECT("1");
					sqlContrarios.FROM("scs_contrariosejg contrario  ");
					sqlContrarios.JOIN(
							"scs_personajg perjgcontrario on perjgcontrario.idpersona = contrario.idpersona AND perjgcontrario.IDINSTITUCION = contrario.IDINSTITUCION");
					sqlContrarios.WHERE(
							"contrario.anio = ejg.anio and contrario.numero = ejg.numero and contrario.idinstitucion = ejg.idinstitucion and contrario.idtipoejg = ejg.idtipoejg");
					if (ejgItem.getNif() != null && ejgItem.getNif() != "") {
						sqlContrarios.WHERE("perjgcontrario.NIF = '" + ejgItem.getNif() + "'");
					}
					if (ejgItem.getApellidos() != null && ejgItem.getApellidos() != "") {
						String columna = "REPLACE(CONCAT(perjgcontrario.apellido1,perjgcontrario.apellido2), ' ', '')";
						String cadena = ejgItem.getApellidos().replaceAll("\\s+", "");
						sqlContrarios.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
					}
					if (ejgItem.getNombre() != null && ejgItem.getNombre() != "") {
						String columna = "perjgcontrario.NOMBRE";
						String cadena = ejgItem.getNombre();
						sqlContrarios.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));

					}

					/*
					 * Comprobar si el contrario es el 1 Rol, en caso de que no continuamos con el
					 * procedimiento
					 */
					if (banderaRolprimero == true) {
						sql.WHERE(" " + "((EXISTS(" + sqlContrarios.toString() + ")");
						banderaRolprimero = false;
						checkRolone = true;
					} else {
						// Informados Nombre, Apellido y Dni.
						if ((ejgItem.getNombre() != null && ejgItem.getNombre() != "" && ejgItem.getApellidos() != null
								&& ejgItem.getApellidos() != "") && ejgItem.getNif() != null
								&& ejgItem.getNif() != "") {
							if (contadorRolActual == rolUltimo) {
								// Contiene el 1 Rol entonces insertar los parentesís.
								if (ejgItem.getRol().contains("1")) {
									sql.OR();
									sql.WHERE("EXISTS(" + sqlContrarios.toString() + ")))))");
									// 2 parentesís menos.
								} else {
									sql.OR();
									sql.WHERE("EXISTS(" + sqlContrarios.toString() + ")))");
								}
							} else {
								sql.OR();
								sql.WHERE("EXISTS(" + sqlContrarios.toString() + ")");
							}
							// Informados el Nombre y Apellidos
						} else if ((ejgItem.getNombre() != null && ejgItem.getNombre() != ""
								&& ejgItem.getApellidos() != null && ejgItem.getApellidos() != "")) {
							if (contadorRolActual == rolUltimo) {
								if (ejgItem.getRol().contains("1")) {
									sql.OR();
									sql.WHERE("EXISTS(" + sqlContrarios.toString() + "))))");
								} else {
									sql.OR();
									sql.WHERE("EXISTS(" + sqlContrarios.toString() + ")))");
								}
							} else {
								sql.OR();
								sql.WHERE("EXISTS(" + sqlContrarios.toString() + ")");
							}
							// Informados NIF
						} else if ((ejgItem.getNif() != null && ejgItem.getNif() != "")) {
							// Informado Nombre o Apellido
							if ((ejgItem.getApellidos() != null && ejgItem.getApellidos() != "")
									|| (ejgItem.getNombre() != null && ejgItem.getNombre() != "")) {
								if (contadorRolActual == rolUltimo) {
									if (ejgItem.getRol().contains("1")) {
										sql.OR();
										sql.WHERE("EXISTS(" + sqlContrarios.toString() + "))))");
									} else {
										sql.OR();
										sql.WHERE("EXISTS(" + sqlContrarios.toString() + ")))");
									}
								} else {
									sql.OR();
									sql.WHERE("EXISTS(" + sqlContrarios.toString() + ")");
								}
							} else {
								if (contadorRolActual == rolUltimo) {
									sql.OR();
									sql.WHERE("EXISTS(" + sqlContrarios.toString() + ")))");
								} else {
									sql.OR();
									sql.WHERE("EXISTS(" + sqlContrarios.toString() + ")");
								}
							}
							// Otros casos
						} else {
							if (contadorRolActual == rolUltimo) {
								sql.OR();
								sql.WHERE("EXISTS(" + sqlContrarios.toString() + ")))");

							} else {
								sql.OR();
								sql.WHERE("EXISTS(" + sqlContrarios.toString() + ")");
							}
						}
					}
				}
			}
			// Verificar que contengan Representantes.
			if (ejgItem.getRol().contains("3")) {
				contadorRolActual = 3;
				checkRolone = (checkRolone == true) ? true : false;
				banderaRolprimero = (checkRolone == true) ? false : true;
				if (ejgItem.getRol().equals("3")) {
					sql.JOIN(
							"scs_unidadfamiliarejg solicitante on solicitante.idinstitucion = ejg.idinstitucion and solicitante.idtipoejg = ejg.idtipoejg "
									+ "and solicitante.anio = ejg.anio  and solicitante.numero = ejg.numero and  solicitante.solicitante = 1");
					sql.JOIN(
							"scs_personajg perjgsolicitante ON perjgsolicitante.idpersona = solicitante.idpersona and perjgsolicitante.idinstitucion = solicitante.idinstitucion ");
					if (ejgItem.getNif() != null && ejgItem.getNif() != "")
						sql.WHERE("perjgsolicitante.NIF = '" + ejgItem.getNif() + "'");
					if (ejgItem.getApellidos() != null && ejgItem.getApellidos() != "") {
						String columna = "REPLACE(CONCAT(perjgsolicitante.apellido1,perjgsolicitante.apellido2), ' ', '')";
						String cadena = ejgItem.getApellidos().replaceAll("\\s+", "");
						sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
					}
					if (ejgItem.getNombre() != null && ejgItem.getNombre() != "") {
						String columna = "perjgsolicitante.NOMBRE";
						String cadena = ejgItem.getNombre();
						sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
					}
				} else {
					sqlUFRep.SELECT("1");
					sqlUFRep.FROM("scs_unidadfamiliarejg solicitante");
					sqlUFRep.JOIN(
							"scs_personajg perjgsolicitante on perjgsolicitante.idrepresentantejg = solicitante.idpersona AND perjgsolicitante.IDINSTITUCION = solicitante.IDINSTITUCION");
					sqlUFRep.WHERE(
							"solicitante.anio = ejg.anio and solicitante.numero = ejg.numero AND solicitante.solicitante = 1 "
									+ "and solicitante.idinstitucion = ejg.idinstitucion and solicitante.idtipoejg = ejg.idtipoejg");
					if (ejgItem.getNif() != null && ejgItem.getNif() != "") {
						sqlUFRep.WHERE("perjgsolicitante.NIF = '" + ejgItem.getNif() + "'");
					}
					if (ejgItem.getApellidos() != null && ejgItem.getApellidos() != "") {
						String columna = "REPLACE(CONCAT(perjgsolicitante.apellido1,perjgsolicitante.apellido2), ' ', '')";
						String cadena = ejgItem.getApellidos().replaceAll("\\s+", "");
						sqlUFRep.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
					}
					if (ejgItem.getNombre() != null && ejgItem.getNombre() != "") {
						String columna = "perjgsolicitante.NOMBRE";
						String cadena = ejgItem.getNombre();
						sqlUFRep.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
					}
					if (banderaRolprimero == true) {
						sql.WHERE(" " + "((EXISTS(" + sqlUFRep.toString() + ")");
						banderaRolprimero = false;
						checkRolone = true;
					} else {
						// Informados Nombre, Apellido y Dni.
						if ((ejgItem.getNombre() != null && ejgItem.getNombre() != "" && ejgItem.getApellidos() != null
								&& ejgItem.getApellidos() != "") && ejgItem.getNif() != null
								&& ejgItem.getNif() != "") {
							if (contadorRolActual == rolUltimo) {
								if (ejgItem.getRol().contains("1")) {
									sql.OR();
									sql.WHERE("EXISTS(" + sqlUFRep.toString() + ")))))");
								} else {
									sql.OR();
									sql.WHERE("EXISTS(" + sqlUFRep.toString() + ")))");
								}
							} else {
								sql.OR();
								sql.WHERE("EXISTS(" + sqlUFRep.toString() + ")");
							}
							// Informados el Nombre y Apellidos
						} else if ((ejgItem.getNombre() != null && ejgItem.getNombre() != ""
								&& ejgItem.getApellidos() != null && ejgItem.getApellidos() != "")) {

							if (contadorRolActual == rolUltimo) {
								if (ejgItem.getRol().contains("1")) {
									sql.OR();
									sql.WHERE("EXISTS(" + sqlUFRep.toString() + "))))");
								} else {
									sql.OR();
									sql.WHERE("EXISTS(" + sqlUFRep.toString() + ")))");
								}
							} else {
								sql.OR();
								sql.WHERE("EXISTS(" + sqlUFRep.toString() + ")");
							}
							// Informados NIF
						} else if ((ejgItem.getNif() != null && ejgItem.getNif() != "")) {

							// Informado Nombre o Apellido
							if ((ejgItem.getApellidos() != null && ejgItem.getApellidos() != "")
									|| (ejgItem.getNombre() != null && ejgItem.getNombre() != "")) {
								if (contadorRolActual == rolUltimo) {
									if (ejgItem.getRol().contains("1")) {
										sql.OR();
										sql.WHERE("EXISTS(" + sqlUFRep.toString() + "))))");
									} else {
										sql.OR();
										sql.WHERE("EXISTS(" + sqlUFRep.toString() + ")))");
									}
								} else {
									sql.OR();
									sql.WHERE("EXISTS(" + sqlUFRep.toString() + ")");
								}
							} else {
								if (contadorRolActual == rolUltimo) {
									sql.OR();
									sql.WHERE("EXISTS(" + sqlUFRep.toString() + ")))");
								} else {
									sql.OR();
									sql.WHERE("EXISTS(" + sqlUFRep.toString() + ")");
								}
							}
							// Otros casos
						} else {
							if (contadorRolActual == rolUltimo) {
								sql.OR();
								sql.WHERE("EXISTS(" + sqlUFRep.toString() + ")))");

							} else {
								sql.OR();
								sql.WHERE("EXISTS(" + sqlUFRep.toString() + ")");
							}
						}
					}

				}
			}
			// Verificar que contengan Unidad Familiar.
			if (ejgItem.getRol().contains("4")) {
				contadorRolActual = 4;
				checkRolone = (checkRolone == true) ? true : false;
				banderaRolprimero = (checkRolone == true) ? false : true;
				// Verificar si solo existe el Rol Unidad Familiar seleccionado.
				if (ejgItem.getRol().equals("4")) {
					sql.JOIN(
							"scs_unidadfamiliarejg unidadFamiliar on unidadFamiliar.idinstitucion = ejg.idinstitucion and unidadFamiliar.idtipoejg = ejg.idtipoejg "
									+ "and unidadFamiliar.anio = ejg.anio  and unidadFamiliar.numero = ejg.numero");
					sql.JOIN(
							"scs_personajg perjgunidadfamiliar on perjgunidadfamiliar.idpersona = unidadFamiliar.idpersona AND perjgunidadfamiliar.IDINSTITUCION = unidadFamiliar.IDINSTITUCION");

					if (ejgItem.getNif() != null && ejgItem.getNif() != "")
						sql.WHERE("perjgunidadfamiliar.NIF = '" + ejgItem.getNif() + "'");
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
					// Verificar que puede venir mas de un ROl.
				} else {
					sqlUF.SELECT("1");
					sqlUF.FROM("scs_unidadfamiliarejg unidadFamiliar");
					sqlUF.JOIN(
							"scs_personajg perjgunidadfamiliar on perjgunidadfamiliar.idpersona = unidadFamiliar.idpersona AND perjgunidadfamiliar.IDINSTITUCION = unidadFamiliar.IDINSTITUCION");
					sqlUF.WHERE(
							"unidadFamiliar.idinstitucion = ejg.idinstitucion and unidadFamiliar.idtipoejg = ejg.idtipoejg"
									+ "	and unidadFamiliar.anio = ejg.anio  and unidadFamiliar.numero = ejg.numero ");

					if (ejgItem.getNif() != null && ejgItem.getNif() != "") {

						sqlUF.WHERE("perjgunidadfamiliar.NIF = '" + ejgItem.getNif() + "'");

					}
					if (ejgItem.getApellidos() != null && ejgItem.getApellidos() != "") {
						String columna = "REPLACE(CONCAT(perjgunidadfamiliar.apellido1,perjgunidadfamiliar.apellido2), ' ', '')";
						String cadena = ejgItem.getApellidos().replaceAll("\\s+", "");

						sqlUF.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));

					}
					if (ejgItem.getNombre() != null && ejgItem.getNombre() != "") {
						String columna = "perjgunidadfamiliar.NOMBRE";
						String cadena = ejgItem.getNombre();
						sqlUF.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
					}
					if (banderaRolprimero == true) {
						sql.WHERE(" " + "((EXISTS(" + sqlUF.toString() + ")");
						banderaRolprimero = false;
						checkRolone = true;
					} else {
						// Informados Nombre, Apellido y Dni.
						if ((ejgItem.getNombre() != null && ejgItem.getNombre() != "" && ejgItem.getApellidos() != null
								&& ejgItem.getApellidos() != "") && ejgItem.getNif() != null
								&& ejgItem.getNif() != "") {

							if (contadorRolActual == rolUltimo) {
								if (ejgItem.getRol().contains("1")) {
									sql.OR();
									sql.WHERE("EXISTS(" + sqlUF.toString() + ")))))");
								} else {
									sql.OR();
									sql.WHERE("EXISTS(" + sqlUF.toString() + ")))");
								}
							} else {
								sql.OR();
								sql.WHERE("EXISTS(" + sqlUF.toString() + ")");
							}
							// Informados el Nombre y Apellidos
						} else if ((ejgItem.getNombre() != null && ejgItem.getNombre() != ""
								&& ejgItem.getApellidos() != null && ejgItem.getApellidos() != "")) {

							if (contadorRolActual == rolUltimo) {
								if (ejgItem.getRol().contains("1")) {
									sql.OR();
									sql.WHERE("EXISTS(" + sqlUF.toString() + "))))");
								} else {
									sql.OR();
									sql.WHERE("EXISTS(" + sqlUF.toString() + ")))");
								}
							} else {
								sql.OR();
								sql.WHERE("EXISTS(" + sqlUF.toString() + ")");
							}
							// Informados NIF
						} else if ((ejgItem.getNif() != null && ejgItem.getNif() != "")) {

							// Informado Nombre o Apellido
							if ((ejgItem.getApellidos() != null && ejgItem.getApellidos() != "")
									|| (ejgItem.getNombre() != null && ejgItem.getNombre() != "")) {
								if (contadorRolActual == rolUltimo) {
									if (ejgItem.getRol().contains("1")) {
										sql.OR();
										sql.WHERE("EXISTS(" + sqlUF.toString() + "))))");
									} else {
										sql.OR();
										sql.WHERE("EXISTS(" + sqlUF.toString() + ")))");
									}

								} else {
									sql.OR();
									sql.WHERE("EXISTS(" + sqlUF.toString() + ")");
								}
							} else {
								if (contadorRolActual == rolUltimo) {
									sql.OR();
									sql.WHERE("EXISTS(" + sqlUF.toString() + ")))");
								} else {
									sql.OR();
									sql.WHERE("EXISTS(" + sqlUF.toString() + ")");
								}
							}
							// Otros casos
						} else {
							if (contadorRolActual == rolUltimo) {
								sql.OR();
								sql.WHERE("EXISTS(" + sqlUF.toString() + ")))");

							} else {
								sql.OR();
								sql.WHERE("EXISTS(" + sqlUF.toString() + ")");
							}
						}
					}
				}
			}
			// Verificar que no contiene Roles.
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

				sql.WHERE("col.ncolegiado = " + ejgItem.getNumColegiado());
				if (ejgItem.getTipoLetrado().equals("E")) {
					sql.JOIN(
							"cen_colegiado col on ejg.idpersona = col.idpersona and ejg.idinstitucion = col.idinstitucion");
					// letrado tramitador
//					if (ejgItem.getIdTurno() != null && ejgItem.getIdTurno() != "")
						sql.WHERE("EJG.GUARDIATURNO_IDTURNO = " + ejgItem.getIdTurno());
					if (ejgItem.getIdGuardia() != null && ejgItem.getIdGuardia() != "")
						sql.WHERE("EJG.GUARDIATURNO_IDGUARDIA " + ejgItem.getIdGuardia());
				} else if (ejgItem.getTipoLetrado().equals("D")) {

					// letrado designas
					sql.JOIN(
							"SCS_EJGDESIGNA EJGDESIGNA ON EJGDESIGNA.IDINSTITUCION = EJG.idinstitucion and EJGDESIGNA.idtipoejg = ejg.idtipoejg and EJGDESIGNA.ANIOEJG = ejg.anio and EJGDESIGNA.numeroejg = ejg.numero");
					sql.JOIN(
							"SCS_DESIGNA DESIGNA ON EJGDESIGNA.IDINSTITUCION = DESIGNA.idinstitucion and EJGDESIGNA.IDTURNO = DESIGNA.IDTURNO and EJGDESIGNA.ANIODESIGNA = DESIGNA.ANIO and EJGDESIGNA.numerodesigna = DESIGNA.NUMERO");
					sql.JOIN(
							"SCS_DESIGNASLETRADO DESIGNALETRADO ON DESIGNALETRADO.idinstitucion = DESIGNA.idinstitucion  and DESIGNALETRADO.idturno = DESIGNA.idturno and DESIGNALETRADO.anio = DESIGNA.anio and DESIGNALETRADO.numero = DESIGNA.numero");
					sql.JOIN(
							"cen_colegiado col on DESIGNALETRADO.idpersona = col.idpersona and DESIGNALETRADO.idinstitucion = col.idinstitucion");
					SQL sql2 = new SQL();

					sql2.SELECT("MAX(LET2.Fechadesigna)");
					sql2.FROM("SCS_DESIGNASLETRADO LET2");
					sql2.WHERE("DESIGNALETRADO.IDINSTITUCION = LET2.IDINSTITUCION");
					sql2.WHERE("DESIGNALETRADO.ANIO = LET2.ANIO");
					sql2.WHERE("DESIGNALETRADO.NUMERO = LET2.NUMERO");
					sql2.WHERE("TRUNC(LET2.Fechadesigna) <= TRUNC(SYSDATE)");
					sql.WHERE("DESIGNALETRADO.Fechadesigna = (" + sql2 + ")");

					if (ejgItem.getIdPersona() != null && ejgItem.getIdPersona() != "")
						sql.WHERE("DESIGNALETRADO.IDPERSONA = " + ejgItem.getIdPersona());
					if (ejgItem.getIdTurno() != null && ejgItem.getIdTurno() != "")
						sql.WHERE("DESIGNA.IDTURNO = " + ejgItem.getIdTurno());
				} else if (ejgItem.getTipoLetrado().equals("A")) {
					// letrado asistencias
					sql.JOIN(
							"SCS_ASISTENCIA ASISTENCIA ON ASISTENCIA.IDINSTITUCION = EJG.idinstitucion and ASISTENCIA.EJGIDTIPOEJG = ejg.idtipoejg and ASISTENCIA.EJGANIO = ejg.anio and ASISTENCIA.ejgnumero = ejg.numero");
					sql.JOIN(
							"cen_colegiado col on ASISTENCIA.IDPERSONACOLEGIADO = col.idpersona and ASISTENCIA.idinstitucion = col.idinstitucion");
					if (ejgItem.getIdPersona() != null && ejgItem.getIdPersona() != "")
						sql.WHERE("ASISTENCIA.IDPERSONACOLEGIADO = " + ejgItem.getIdPersona());
					if (ejgItem.getIdTurno() != null && ejgItem.getIdTurno() != "")
						sql.WHERE("ASISTENCIA.IDTURNO = " + ejgItem.getIdTurno());
					if (ejgItem.getIdGuardia() != null && ejgItem.getIdGuardia() != "")
						sql.WHERE("ASISTENCIA.IDGUARDIA = " + ejgItem.getIdGuardia());
				}
			} else if(ejgItem.getIdPersona() != null){
				sql.LEFT_OUTER_JOIN(
						"cen_colegiado col on ejg.idpersona = col.idpersona and ejg.idinstitucion = col.idinstitucion");
				if (ejgItem.getTipoLetrado().equals("E")) {
					// letrado tramitador
					if (ejgItem.getIdTurno() != null && ejgItem.getIdTurno() != "")
						sql.WHERE("EJG.GUARDIATURNO_IDTURNO = " + ejgItem.getIdTurno());
					if (ejgItem.getIdGuardia() != null && ejgItem.getIdGuardia() != "")
						sql.WHERE("EJG.GUARDIATURNO_IDGUARDIA = " + ejgItem.getIdGuardia());
				} else if (ejgItem.getTipoLetrado().equals("D")) {
					// letrado designas
					sql.JOIN(
							"SCS_EJGDESIGNA EJGDESIGNA ON EJGDESIGNA.IDINSTITUCION = EJG.idinstitucion and EJGDESIGNA.idtipoejg = ejg.idtipoejg and EJGDESIGNA.ANIOEJG = ejg.anio and EJGDESIGNA.numeroejg = ejg.numero");
					if (ejgItem.getIdTurno() != null && ejgItem.getIdTurno() != "")
						sql.WHERE("EJGDESIGNA.IDTURNO = " + ejgItem.getIdTurno());
				} else if (ejgItem.getTipoLetrado().equals("A")) {
					// letrado asistencias
					sql.JOIN(
							"SCS_ASISTENCIA ASISTENCIA ON ASISTENCIA.IDINSTITUCION = EJG.idinstitucion and ASISTENCIA.EJGIDTIPOEJG = ejg.idtipoejg and ASISTENCIA.EJGANIO = ejg.anio and ASISTENCIA.ejgnumero = ejg.numero");
					if (ejgItem.getIdTurno() != null && ejgItem.getIdTurno() != "")
						sql.WHERE("ASISTENCIA.IDTURNO = " + ejgItem.getIdTurno());
					if (ejgItem.getIdGuardia() != null && ejgItem.getIdGuardia() != "")
						sql.WHERE("ASISTENCIA.IDGUARDIA = " + ejgItem.getIdGuardia());
				}
			}
		} else if(ejgItem.getNumColegiado() != null && ejgItem.getNumColegiado() != "" && ejgItem.getIdPersona() != null && ejgItem.getIdPersona() != "" &&
				ejgItem.getIdTurno() != null && ejgItem.getIdTurno() != "" && ejgItem.getIdGuardia() != null && ejgItem.getIdGuardia() != ""){
			sql.LEFT_OUTER_JOIN(
					"cen_colegiado col on ejg.idpersona = col.idpersona and ejg.idinstitucion = col.idinstitucion");
			if (ejgItem.getNumColegiado() != null && ejgItem.getNumColegiado() != "") {
				sql.WHERE("col.ncolegiado = " + ejgItem.getNumColegiado());
			}
			if (ejgItem.getIdTurno() != null && ejgItem.getIdTurno() != "")
				sql.WHERE("EJG.GUARDIATURNO_IDTURNO = " + ejgItem.getIdTurno());
			if (ejgItem.getIdGuardia() != null && ejgItem.getIdGuardia() != "")
				sql.WHERE("EJG.GUARDIATURNO_IDGUARDIA = " + ejgItem.getIdGuardia());
		}

		if (ejgItem.getEstadosSolicitudExpEco() != null && ejgItem.getEstadosSolicitudExpEco().length > 0) {
			sql.JOIN("SCS_EEJG_PETICIONES PET " + "ON PET.ANIO = ejg.ANIO " + "AND PET.NUMERO = ejg.NUMERO "
					+ "AND PET.IDINSTITUCION = ejg.IDINSTITUCION " + "AND PET.IDTIPOEJG = ejg.IDTIPOEJG");
			sql.WHERE("PET.ESTADO IN (" + String.join(",", ejgItem.getEstadosSolicitudExpEco()) + ")");
		}

		if (ejgItem.isInformacionEconomica()) {
			if (ejgItem.getEstadosSolicitudExpEco() == null || ejgItem.getEstadosSolicitudExpEco().length == 0) {
				sql.INNER_JOIN("SCS_EEJG_PETICIONES PET " + "ON PET.ANIO = ejg.ANIO " + "AND PET.NUMERO = ejg.NUMERO "
						+ "AND PET.IDINSTITUCION = ejg.IDINSTITUCION " + "AND PET.IDTIPOEJG = ejg.IDTIPOEJG");
			}
			sql.INNER_JOIN("SCS_EEJG_XML EXML " + "ON PET.IDPETICION = EXML.IDPETICION ");
			sql.WHERE("EXML.IDXML IS NOT NULL");
		}

		sql.ORDER_BY("EJG.ANIO DESC, EJG.NUMERO DESC");
		
		if (tamMaximo != null) { 
			Integer tamMaxNumber = tamMaximo;
			sql.FETCH_FIRST_ROWS_ONLY(tamMaxNumber);
		}

		return sql.toString();
	}

	
	public String busquedaEJGComisionFinal(EjgItem ejgItem, String idInstitucion, Integer tamMaximo,
			String idLenguaje, String stringListaEjgs) {
		String resolucion;
		SQL sql = new SQL();
		SQL sqlTurno = new SQL();
		SQL sqlTurnoGuardia = new SQL();

		resolucion = "(SELECT F_SIGA_GETRECURSO(tipores.DESCRIPCION, 1) from SCS_TIPORESOLUCION tipores where tipores.codigo = ejg.idtiporatificacionejg) RESOLUCION";

		SQL letrado = new SQL();
	        letrado.SELECT("cen_persona.apellidos2 || ' ' || cen_persona.apellidos1 || ',' || cen_persona.nombre");
	        letrado.FROM("scs_ejgdesigna ejgdesigna");
	        letrado.JOIN("cen_persona ON cen_persona.idpersona = F_SIGA_GETIDLETRADO_DESIGNA(ejgdesigna.IDINSTITUCION, ejgdesigna.IDTURNO , ejgdesigna.ANIODESIGNA , ejgdesigna.NUMERODESIGNA)");
	        letrado.WHERE("ejgdesigna.idinstitucion = ejg.idinstitucion");
	        letrado.WHERE("ejgdesigna.ANIOEJG = ejg.anio");
	        letrado.WHERE("ejgdesigna.NUMEROEJG = ejg.numero");
	        letrado.WHERE("ejgdesigna.IDTIPOEJG = ejg.IDTIPOEJG");
	        letrado.ORDER_BY("ejgdesigna.aniodesigna DESC, ejgdesigna.numerodesigna DESC");
		
		//sql turno
	        sqlTurno.SELECT("TUR.ABREVIATURA");
	        sqlTurno.FROM("SCS_TURNO TUR");
	        sqlTurno.JOIN("SCS_EJGDESIGNA ejgdes ON ejgdes.idinstitucion = TUR.IDINSTITUCION AND ejgdes.idturno = TUR.IDTURNO");
	        sqlTurno.WHERE("ejgdes.idinstitucion = ejg.idinstitucion "); 
	        sqlTurno.WHERE("ejgdes.ANIOEJG = ejg.anio ");
	        sqlTurno.WHERE("ejgdes.NUMEROEJG = ejg.numero "); 
	        sqlTurno.WHERE("ejgdes.IDTIPOEJG = ejg.IDTIPOEJG");
		sqlTurno.WHERE("rownum < 2");
		
		//sqlTurnoGuardia
		sqlTurnoGuardia.SELECT("st.ABREVIATURA || ' / ' || gt.nombre");
		sqlTurnoGuardia.FROM("SCS_ASISTENCIA sa");
		sqlTurnoGuardia.JOIN("SCS_TURNO st ON st.idturno = sa.idturno AND st.idinstitucion = sa.idinstitucion");
		sqlTurnoGuardia.JOIN("SCS_GUARDIASTURNO gt ON gt.idturno = sa.idturno AND gt.idinstitucion = sa.idinstitucion AND gt.IDGUARDIA = sa.IDGUARDIA");
		sqlTurnoGuardia.WHERE("ejganio = ejg.ANIO");
		sqlTurnoGuardia.WHERE("ejgnumero = ejg.NUMERO");
		sqlTurnoGuardia.WHERE("ejgidtipoejg = ejg.IDTIPOEJG");
		sqlTurnoGuardia.WHERE("sa.idinstitucion = ejg.IDINSTITUCION");
		sqlTurnoGuardia.WHERE("idestadoasistencia != '2'");
		sqlTurnoGuardia.WHERE("rownum < 2");
                

		// select
		sql.SELECT("ejg.anio");
		sql.SELECT("ejg.idinstitucion");
		sql.SELECT("ejg.idtipoejg");
		sql.SELECT("ejg.numero");
		sql.SELECT("ejg.numejg numejg");
		sql.SELECT("'E' || EJG.ANIO || '/' || EJG.NUMEJG AS NUMANIO");
		sql.SELECT(
				"(CASE\r\n" + 
				"		WHEN (" + sqlTurno.toString() + ") IS NOT NULL THEN (" + sqlTurno.toString() + ") \r\n" + 
				"		ELSE (" + sqlTurnoGuardia.toString() + ")" + 
				"	END) AS TURNOGUARDIA");
		sql.SELECT("(CASE WHEN TURNO.ABREVIATURA IS NULL THEN '' " + 
				"	ELSE TURNO.ABREVIATURA || ' / ' || GUARDIA.NOMBRE " + 
				"	END) AS TURNO");
		sql.SELECT("EJG.GUARDIATURNO_IDTURNO as IDTURNO");
		sql.SELECT("ejg.fechaapertura");
		sql.SELECT("ejg.fechamodificacion");
		sql.SELECT("(SELECT * FROM (" + letrado.toString() + ") WHERE ROWNUM =1) as nombreletrado");
					
		SQL sqlEstado = new SQL();
		sqlEstado.SELECT("f_siga_getrecurso(MAESTROESTADO.DESCRIPCION, " + idLenguaje + ")");
		sqlEstado.FROM("SCS_MAESTROESTADOSEJG MAESTROESTADO");
		sqlEstado.WHERE("ESTADO.IDESTADOEJG = MAESTROESTADO.IDESTADOEJG");
		sqlEstado.WHERE("MAESTROESTADO.VISIBLECOMISION = 1 AND ROWNUM = 1");
		
		sql.SELECT("(" + sqlEstado.toString() + ") AS ESTADOEJG");
		
		sql.SELECT("(CASE WHEN perjg.nombre is  NULL THEN '' ELSE perjg.apellido1 || ' ' || perjg.apellido2 || ', ' || perjg.nombre END) as NOMBRESOLICITANTE");
		sql.SELECT("EJG.NUMEROPROCEDIMIENTO");
		sql.SELECT("ejg.idpersonajg");
		sql.SELECT("perjg.NIF");
		sql.SELECT(resolucion);
		
		SQL sqlEstado2 = new SQL();
		sqlEstado2.SELECT("maestroestado.editablecomision");
		sqlEstado2.FROM("SCS_MAESTROESTADOSEJG MAESTROESTADO");
		sqlEstado2.WHERE("ESTADO.IDESTADOEJG = MAESTROESTADO.IDESTADOEJG");
		sqlEstado2.WHERE("MAESTROESTADO.VISIBLECOMISION = 1 AND ROWNUM = 1");
		
		sql.SELECT("(" + sqlEstado2.toString() + ") AS EDITABLECOMISION");

		// from
		sql.FROM("scs_ejg ejg");
		// joins
		sql.LEFT_OUTER_JOIN("scs_personajg perjg on perjg.idpersona = ejg.idpersonajg AND perjg.IDINSTITUCION = EJG.IDINSTITUCION");
        	sql.LEFT_OUTER_JOIN("SCS_TURNO  TURNO ON TURNO.IDINSTITUCION = ejg.IDINSTITUCION AND TURNO.IDTURNO = EJG.GUARDIATURNO_IDTURNO");
        	sql.LEFT_OUTER_JOIN("SCS_GUARDIASTURNO GUARDIA  ON GUARDIA.IDINSTITUCION =EJG.IDINSTITUCION AND GUARDIA.IDTURNO =EJG.GUARDIATURNO_IDTURNO  AND GUARDIA.IDGUARDIA =EJG.GUARDIATURNO_IDGUARDIA");
		String joinEstado = "SCS_ESTADOEJG ESTADO ON ESTADO.IDINSTITUCION = EJG.IDINSTITUCION AND ESTADO.IDTIPOEJG = EJG.IDTIPOEJG AND ESTADO.ANIO = EJG.ANIO AND ESTADO.NUMERO = EJG.NUMERO ";
        	sql.JOIN(joinEstado);

		// where
		sql.WHERE("ejg.idinstitucion = " + idInstitucion);
		sql.WHERE("ESTADO.FECHABAJA IS NULL");
        	sql.WHERE("ESTADO.IDESTADOEJG = F_SIGA_GET_IDULTIMOESTADOEJG (ESTADO.IDINSTITUCION, ESTADO.IDTIPOEJG, ESTADO.ANIO,ESTADO.NUMERO )");
		sql.WHERE("(ejg.anio, ejg.numejg) IN (" + stringListaEjgs + ")");

		sql.ORDER_BY("EJG.ANIO DESC, EJG.NUMERO DESC");
		
		if (tamMaximo != null) { 
			Integer tamMaxNumber = tamMaximo;
			sql.FETCH_FIRST_ROWS_ONLY(tamMaxNumber);
		}

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
