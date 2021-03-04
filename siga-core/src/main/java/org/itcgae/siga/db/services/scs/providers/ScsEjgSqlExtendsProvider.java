package org.itcgae.siga.db.services.scs.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableItem;
import org.itcgae.siga.DTOs.scs.ColegiadosSJCSItem;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.ScsEjgSqlProvider;

public class ScsEjgSqlExtendsProvider extends ScsEjgSqlProvider {

	public String busquedaEJG(EjgItem ejgItem, String idInstitucion, Integer tamMaximo, String idLenguaje) {
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

		SQL sql = new SQL();

		String condicionAnnioNumActas = " (EXISTS (SELECT 1 FROM scs_ejg_acta ejgacta, scs_actacomision ac"
				+ " WHERE ejgacta.idinstitucionacta = ac.idinstitucion" + " AND ejgacta.idacta = ac.idacta"
				+ " AND   ejgacta.anioacta = ac.anioacta" + " AND   ejgacta.idinstitucionejg = ejg.idinstitucion"
				+ " AND   ejgacta.anioejg = ejg.anio" + " AND   ejgacta.idtipoejg = ejg.idtipoejg"
				+ " AND   ejgacta.numeroejg = ejg.numero" + " AND   ac.idinstitucion = " + idInstitucion;
//				+ " AND   ac.anioacta = " + ejgItem.getAnnioActa()
//				+ " AND   ac.numeroacta = " + ejgItem.getNumActa() + ")";

		if (ejgItem.getAnnioActa() != null && ejgItem.getAnnioActa() != "")
			condicionAnnioNumActas = condicionAnnioNumActas + " AND   ac.anioacta = " + ejgItem.getAnnioActa();
		if (ejgItem.getNumActa() != null && ejgItem.getNumActa() != "")
			condicionAnnioNumActas = condicionAnnioNumActas + " AND   ac.numeroacta = " + ejgItem.getNumActa();
		condicionAnnioNumActas = condicionAnnioNumActas + "))";

		String condicionNumRegRemesa = " (EXISTS (SELECT 1 FROM cajg_ejgremesa ejgremesa, cajg_remesa remesa"
				+ " WHERE ejgremesa.idinstitucionremesa = remesa.idinstitucion"
				+ " AND ejgremesa.idinstitucion = ejg.idinstitucion" + " AND   ejgremesa.anio = ejg.anio"
				+ " AND   ejgremesa.idtipoejg = ejg.idtipoejg" + " AND   ejgremesa.numero = ejg.numero"
				+ " AND   remesa.idinstitucion = " + idInstitucion;
//				+ " AND   remesa.prefijo = " + ejgItem.getNumRegRemesa1()
//				+ " AND   remesa.numero = " + ejgItem.getNumRegRemesa2()
//				+ " AND   remesa.sufijo = " + ejgItem.getNumRegRemesa3() + ")";
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
		sql.SELECT("TURNO.NOMBRE || '/' || GUARDIA.NOMBRE AS TURNOGUARDIA");
		sql.SELECT("TURNO.ABREVIATURA AS TURNO");
		sql.SELECT("EJG.GUARDIATURNO_IDTURNO as IDTURNO");
		sql.SELECT("ejg.fechaapertura");
		sql.SELECT("ejg.fechamodificacion");
		sql.SELECT("per.apellidos1 || ' ' || per.apellidos2 || ', ' || per.nombre as NOMBREletrado");
		sql.SELECT("REC.DESCRIPCION AS ESTADOEJG");
		sql.SELECT("perjg.apellido1 || ' ' || perjg.apellido2 || ', ' || perjg.nombre as NOMBRESOLICITANTE");
		sql.SELECT("EJG.NUMEROPROCEDIMIENTO");

//		sql.SELECT("perjg.NIF");
//		sql.SELECT("perjg.correoelectronico");
//		sql.SELECT("perjg.fechanacimiento");

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
				+ "AND ESTADO.NUMERO = ESTADO3.NUMERO " + "AND ESTADO3.FECHABAJA IS NULL))))");
		sql.INNER_JOIN("SCS_MAESTROESTADOSEJG MAESTROESTADO ON ESTADO.IDESTADOEJG = MAESTROESTADO.IDESTADOEJG");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS REC ON REC.IDRECURSO = MAESTROESTADO.DESCRIPCION AND REC.IDLENGUAJE = '"
				+ idLenguaje + "'");

		// where
		sql.WHERE("ejg.idinstitucion = " + idInstitucion);
		if (ejgItem.getAnnio() != null && ejgItem.getAnnio() != "")
			sql.WHERE("ejg.anio =" + ejgItem.getAnnio());
		if (ejgItem.getNumero() != null && ejgItem.getNumero() != "")
			sql.WHERE("EJG.NUMEJG =" + ejgItem.getNumero());
		if (ejgItem.getTipoEJG() != null && ejgItem.getTipoEJG() != "")
			sql.WHERE("ejg.IDTIPOEJG = " + ejgItem.getTipoEJG());
		if (ejgItem.getTipoEJGColegio() != null && ejgItem.getTipoEJGColegio() != "")
			sql.WHERE("ejg.idtipoejgcolegio = " + ejgItem.getTipoEJGColegio());
		if (ejgItem.getCreadoDesde() != null && ejgItem.getCreadoDesde() != "")
			sql.WHERE("ejg.origenapertura = '" + ejgItem.getCreadoDesde() + "'");
		if (ejgItem.getProcedimiento() != null && ejgItem.getProcedimiento() != "")
			sql.WHERE("regexp_like(EJG.NUMEROPROCEDIMIENTO,'" + ejgItem.getProcedimiento() + "')");
		if (ejgItem.getEstadoEJG() != null && ejgItem.getEstadoEJG() != "")
			sql.WHERE("estado.idestadoejg =" + ejgItem.getEstadoEJG());
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
		if (ejgItem.getDictamen() != null) {
			for (String dictamen : ejgItem.getDictamen()) {
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
		if (ejgItem.getFundamentoCalif() != null && ejgItem.getFundamentoCalif() != "")
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
//					sql.WHERE("TRANSLATE(LOWER( REPLACE(CONCAT(apellido1,apellido2), ' ', '')),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('"+ ejgItem.getApellidos() +"'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')");
				if (ejgItem.getNombre() != null && ejgItem.getNombre() != "") {
					String columna = "perjg.NOMBRE";
					String cadena = ejgItem.getNombre();
					sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
//					sql.WHERE("TRANSLATE(LOWER(perjg.NOMBRE),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('"+ ejgItem.getNombre() +"'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')");
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
//					sql.WHERE("TRANSLATE(LOWER( REPLACE(CONCAT(perjgunidadfamiliar.apellido1,perjgunidadfamiliar.apellido2), ' ', '')),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('"+ ejgItem.getApellidos() +"'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN'))");
				if (ejgItem.getNombre() != null && ejgItem.getNombre() != "") {
					String columna = "perjgunidadfamiliar.NOMBRE";
					String cadena = ejgItem.getNombre();
					sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
				}
//					sql.WHERE("TRANSLATE(LOWER(perjgunidadfamiliar.NOMBRE),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('"+ ejgItem.getNombre() +"'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN'))");
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
//					sql.WHERE("TRANSLATE(LOWER( REPLACE(CONCAT(perjgcontrario.apellido1,perjgcontrario.apellido2), ' ', '')),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('"+ ejgItem.getApellidos() +"'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN'))");
				if (ejgItem.getNombre() != null && ejgItem.getNombre() != "") {
					String columna = "perjgcontrario.NOMBRE";
					String cadena = ejgItem.getNombre();
					sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
				}
//					sql.WHERE("TRANSLATE(LOWER(perjgcontrario.NOMBRE),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('"+ ejgItem.getNombre() +"'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN'))");
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
//					sql.WHERE("(TRANSLATE(LOWER( REPLACE(CONCAT(perjgunidadfamiliar.apellido1,perjgunidadfamiliar.apellido2), ' ', '')),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('"+ ejgItem.getApellidos() +"'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN'))");
				if (ejgItem.getNombre() != null && ejgItem.getNombre() != "") {
					String columna = "perjgunidadfamiliar.NOMBRE";
					String cadena = ejgItem.getNombre();
					sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
				}
//					sql.WHERE("(TRANSLATE(LOWER(perjgunidadfamiliar.NOMBRE),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('"+ ejgItem.getNombre() +"'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN'))");
			}
		} else {
			if (ejgItem.getNif() != null && ejgItem.getNif() != "")
				sql.WHERE("perjg.NIF = '" + ejgItem.getNif() + "'");
			if (ejgItem.getApellidos() != null && ejgItem.getApellidos() != "") {
				String columna = "REPLACE(CONCAT(perjg.apellido1,perjg.apellido2), ' ', '')";
				String cadena = ejgItem.getApellidos().replaceAll("\\s+", "");
				sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
			}
//				sql.WHERE("TRANSLATE(LOWER( REPLACE(CONCAT(apellido1,apellido2), ' ', '')),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('"+ ejgItem.getApellidos() +"'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')");
			if (ejgItem.getNombre() != null && ejgItem.getNombre() != "") {
				String columna = "perjg.NOMBRE";
				String cadena = ejgItem.getNombre();
				sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
			}
//				sql.WHERE("TRANSLATE(LOWER(perjg.NOMBRE),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('"+ ejgItem.getNombre() +"'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')");
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
			sql.WHERE("rownum <= " + tamMaxNumber);

		}
		// order
//		sql.ORDER_BY("anio DESC, to_number(numejg) DESC");
		sql.ORDER_BY("TURNO ASC, GUARDIA.NOMBRE ASC");

		return sql.toString();
	}

	public String datosEJG(EjgItem ejgItem, String idInstitucion, String idLenguaje) {
		SQL sql = new SQL();

		String joinDesignaLetrado = "(select * from SCS_DESIGNASLETRADO designaletrado where designaletrado.fecharenuncia is null or"
				+ " designaletrado.Fechadesigna = (SELECT MAX(LET2.Fechadesigna)" + " FROM SCS_DESIGNASLETRADO LET2"
				+ " WHERE designaletrado.IDINSTITUCION = LET2.IDINSTITUCION"
				+ " AND designaletrado.IDTURNO = LET2.IDTURNO" + " AND designaletrado.ANIO = LET2.ANIO"
				+ " AND designaletrado.NUMERO = LET2.NUMERO" + " AND TRUNC(LET2.Fechadesigna) <= TRUNC(SYSDATE))"
				+ ") designaletrado2 on (designaletrado2.idinstitucion = ejgd.idinstitucion and designaletrado2.idturno = ejgd.idturno  and designaletrado2.anio = ejgd.anioejg and designaletrado2.numero = EJGD.NUMERODESIGNA)";

		// select
		sql.SELECT("EJG.IDPERSONA"); // IDpERSONA
		sql.SELECT("ejg.anio");
		sql.SELECT("ejg.idinstitucion");
		sql.SELECT("ejg.idtipoejg");
		sql.SELECT("ejg.numero");
		sql.SELECT("ejg.numejg numejg");
		sql.SELECT("'E' || EJG.ANIO || '/' || EJG.NUMEJG AS NUMANIO");
		sql.SELECT("TURNO.NOMBRE || '/' || GUARDIA.NOMBRE AS TURNOGUARDIA");
		sql.SELECT("TURNO.ABREVIATURA AS TURNO");
		sql.SELECT("GUARDIA.NOMBRE AS GUARDIA");
		sql.SELECT("EJG.GUARDIATURNO_IDTURNO as IDTURNO");
		sql.SELECT("EJG.GUARDIATURNO_IDGUARDIA as IDGUARDIA");
		sql.SELECT("ejg.fechaapertura");
		sql.SELECT("ejg.fechapresentacion");
		sql.SELECT("ejg.idtipoejgcolegio");
		sql.SELECT("ejg.fechalimitepresentacion");
		sql.SELECT("ejg.fechamodificacion");
		sql.SELECT("ejg.fechaauto");
		sql.SELECT("ejg.idtiporesolauto");
		sql.SELECT("ejg.idtiposentidoauto");
		sql.SELECT("ejg.observacionimpugnacion");
		sql.SELECT("ejg.numeroresolucion");
		sql.SELECT("ejg.fechapublicacion");
		sql.SELECT("ejg.bisresolucion");
		sql.SELECT("ejg.turnadoratificacion");
		sql.SELECT("per.apellidos1 || ' ' || per.apellidos2 || ', ' || per.nombre as nombreletrado");
		sql.SELECT("REC.DESCRIPCION AS ESTADOEJG");
		sql.SELECT("perjg.apellido1 || ' ' || perjg.apellido2 || ', ' || perjg.nombre as NOMBRESOLICITANTE");
		sql.SELECT("perjg.apellido1 || ' ' || perjg.apellido2 as APESOLICITANTE");
		sql.SELECT("perjg.nombre as SOLONOMBRESOLIC");
		sql.SELECT("perjg.NIF");
		sql.SELECT("EJG.NUMEROPROCEDIMIENTO");
		sql.SELECT("rectipodictamen.descripcion AS dictamen");
		sql.SELECT("rectiporesolucion.descripcion AS resolucion");
		sql.SELECT("rectiporesolauto.descripcion AS resolauto");
		sql.SELECT(
				"personadesigna.apellidos1 || ' ' || personadesigna.apellidos2 || ', ' || personadesigna.nombre AS nombreletradodesigna");
		sql.SELECT("EXPEDIENTE.anioexpediente");
		sql.SELECT("EXPEDIENTE.numeroexpediente");
		sql.SELECT("EXPEDIENTE.IDTIPOEXPEDIENTE");
		// from
		sql.FROM("scs_ejg ejg");
		// joins
		sql.LEFT_OUTER_JOIN("cen_persona per on per.idpersona = ejg.idpersona");
		sql.LEFT_OUTER_JOIN(
				"EXP_EXPEDIENTE EXPEDIENTE ON EJG.IDINSTITUCION = EXPEDIENTE.IDINSTITUCION AND EJG.ANIO = EXPEDIENTE.ANIOEJG  \r\n"
						+ "			            AND EJG.NUMERO = EXPEDIENTE.NUMEROEJG AND EJG.IDTIPOEJG = EXPEDIENTE.IDTIPOEJG");
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
				+ "AND ESTADO.NUMERO = ESTADO3.NUMERO " + "AND ESTADO3.FECHABAJA IS NULL))))");
		sql.INNER_JOIN("SCS_MAESTROESTADOSEJG MAESTROESTADO ON ESTADO.IDESTADOEJG = MAESTROESTADO.IDESTADOEJG");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS REC ON REC.IDRECURSO = MAESTROESTADO.DESCRIPCION AND REC.IDLENGUAJE = '"
				+ idLenguaje + "'");
		sql.LEFT_OUTER_JOIN(
				"scs_tipodictamenejg tipodictamen ON tipodictamen.idtipodictamenejg = ejg.idtipodictamenejg AND ejg.idinstitucion = tipodictamen.idinstitucion");
		sql.LEFT_OUTER_JOIN(
				"gen_recursos_catalogos rectipodictamen ON rectipodictamen.idrecurso = tipodictamen.descripcion AND rectipodictamen.idlenguaje = '"
						+ idLenguaje + "'");
		sql.LEFT_OUTER_JOIN(
				"scs_tiporesolucion tiporesolucion ON tiporesolucion.idtiporesolucion = ejg.idtiporatificacionejg");
		sql.LEFT_OUTER_JOIN(
				"gen_recursos_catalogos rectiporesolucion ON rectiporesolucion.idrecurso = tiporesolucion.descripcion AND rectiporesolucion.idlenguaje = '"
						+ idLenguaje + "'");
		sql.LEFT_OUTER_JOIN("scs_tiporesolauto tiporesolauto ON tiporesolauto.idtiporesolauto = ejg.idtiporesolauto");
		sql.LEFT_OUTER_JOIN(
				"gen_recursos_catalogos rectiporesolauto ON rectiporesolauto.idrecurso = tiporesolauto.descripcion AND rectiporesolauto.idlenguaje = '"
						+ idLenguaje + "'");
		sql.LEFT_OUTER_JOIN(
				"SCS_EJGDESIGNA EJGD ON   ejgd.anioejg = ejg.anio   AND   ejgd.numeroejg = ejg.numero AND   ejgd.idtipoejg = ejg.idtipoejg  AND   ejgd.idinstitucion = ejg.idinstitucion");
		sql.LEFT_OUTER_JOIN(joinDesignaLetrado);
		sql.LEFT_OUTER_JOIN(
				"CEN_CLIENTE clientedesigna on clientedesigna.idpersona = designaletrado2.idpersona  and clientedesigna.idinstitucion = designaletrado2.idinstitucion");
		sql.LEFT_OUTER_JOIN("cen_persona personadesigna on clientedesigna.idpersona = personadesigna.idpersona");
		// where
		sql.WHERE("ejg.idinstitucion = " + idInstitucion);
		if (ejgItem.getAnnio() != null && ejgItem.getAnnio() != "")
			sql.WHERE("ejg.anio =" + ejgItem.getAnnio());
		if (ejgItem.getNumEjg() != null && ejgItem.getNumEjg() != "")
			sql.WHERE("EJG.NUMEJG =" + ejgItem.getNumEjg());
//				sql.WHERE ("EJG.NUMEJG ='09039'");

		if (ejgItem.getTipoEJG() != null && ejgItem.getTipoEJG() != "")
			sql.WHERE("ejg.IDTIPOEJG = " + ejgItem.getTipoEJG());

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

	public String getAsuntoTipoEjg(AsuntosClaveJusticiableItem asuntoClave, String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("EJG.IDINSTITUCION");
		sql.SELECT("concat('E' || EJG.anio || '/',lpad(EJG.numejg,5,'0') ) as asunto");
		sql.SELECT("EJG.FECHAAPERTURA AS fecha");
		sql.SELECT("EJG.ANIO");
		sql.SELECT("EJG.NUMERO");
		sql.SELECT("turno.nombre as turnoguardia");
		sql.SELECT("EJG.IDPERSONA AS IDPERSONALETRADO");
		sql.SELECT("EJG.IDPERSONAJG AS IDPERSONAJG");

		sql.SELECT(
				"concat('X' || EXPEDIENTE.anioexpediente || '/', EXPEDIENTE.numeroexpediente) AS EXPEDIENTEINSOSTENIBILIDAD");
		// sql.SELECT("EXPEDIENTE.ASUNTO AS EXPEDIENTEINSOSTENIBILIDAD");
		sql.SELECT("RECDICTAMEN.DESCRIPCION AS DICTAMEN");
		sql.SELECT("RECTIPOFUNDAMENTOCALIF.DESCRIPCION AS FUNDAMENTOCALIFICACION");
		sql.SELECT("RECTIPORESOLUCION.DESCRIPCION AS TIPORESOLUCION");
		sql.SELECT("RECTIPOFUNDAMENTO.DESCRIPCION AS TIPOFUNDAMENTO");
		sql.SELECT("RECTIPORESOLUAUTO.DESCRIPCION AS TIPORESOLUCIONAUTO");
		sql.SELECT("TIPOSENTIDOAUTO.DESCRIPCION AS TIPOSENTIDOAUTO");

		sql.FROM("SCS_EJG EJG");

		sql.LEFT_OUTER_JOIN(
				"SCS_TURNO TURNO ON EJG.GUARDIATURNO_IDTURNO = turno.idturno and EJG.IDINSTITUCION = TURNO.IDINSTITUCION");
		sql.LEFT_OUTER_JOIN(
				"EXP_EXPEDIENTE EXPEDIENTE ON EJG.IDINSTITUCION = EXPEDIENTE.IDINSTITUCION AND EJG.ANIO = EXPEDIENTE.ANIOEJG  AND EJG.NUMERO = EXPEDIENTE.NUMEROEJG AND EJG.IDTIPOEJG = EXPEDIENTE.IDTIPOEJG");
		sql.LEFT_OUTER_JOIN(
				"scs_tipodictamenejg TIPODICTAMEN ON EJG.IDTIPODICTAMENEJG = TIPODICTAMEN.IDTIPODICTAMENEJG AND TIPODICTAMEN.IDINSTITUCION = EJG.IDINSTITUCION");
		sql.LEFT_OUTER_JOIN(
				"GEN_RECURSOS_CATALOGOS RECDICTAMEN ON RECDICTAMEN.IDRECURSO = TIPODICTAMEN.DESCRIPCION AND RECDICTAMEN.IDLENGUAJE = '"
						+ idLenguaje + "'");
		sql.LEFT_OUTER_JOIN(
				"scs_tipofundamentocalif TIPOFUNDAMENTOCALIF ON TIPOFUNDAMENTOCALIF.IDFUNDAMENTOCALIF = EJG.IDFUNDAMENTOCALIF AND TIPOFUNDAMENTOCALIF.IDINSTITUCION = EJG.IDINSTITUCION");
		sql.LEFT_OUTER_JOIN(
				"GEN_RECURSOS_CATALOGOS RECTIPOFUNDAMENTOCALIF ON RECTIPOFUNDAMENTOCALIF.IDRECURSO = TIPOFUNDAMENTOCALIF.DESCRIPCION AND RECTIPOFUNDAMENTOCALIF.IDLENGUAJE = '"
						+ idLenguaje + "'");
		sql.LEFT_OUTER_JOIN(
				"scs_tiporesolucion TIPORESOLUCION ON TIPORESOLUCION.IDTIPORESOLUCION = EJG.IDTIPORATIFICACIONEJG");
		sql.LEFT_OUTER_JOIN(
				"GEN_RECURSOS_CATALOGOS RECTIPORESOLUCION ON RECTIPORESOLUCION.IDRECURSO = TIPORESOLUCION.DESCRIPCION AND RECTIPORESOLUCION.IDLENGUAJE = '"
						+ idLenguaje + "'");
		sql.LEFT_OUTER_JOIN(
				"scs_tipofundamentos TIPOFUNDAMENTO ON TIPOFUNDAMENTO.IDFUNDAMENTO = EJG.IDFUNDAMENTOJURIDICO AND TIPOFUNDAMENTO.IDINSTITUCION = EJG.IDINSTITUCION");
		sql.LEFT_OUTER_JOIN(
				"GEN_RECURSOS_CATALOGOS RECTIPOFUNDAMENTO ON RECTIPOFUNDAMENTO.IDRECURSO = TIPOFUNDAMENTO.DESCRIPCION AND RECTIPOFUNDAMENTO.IDLENGUAJE = '"
						+ idLenguaje + "'");
		sql.LEFT_OUTER_JOIN("scs_tiporesolauto TIPORESOLUAUTO ON TIPORESOLUAUTO.IDTIPORESOLAUTO = EJG.IDTIPORESOLAUTO");
		sql.LEFT_OUTER_JOIN(
				"GEN_RECURSOS_CATALOGOS RECTIPORESOLUAUTO ON RECTIPORESOLUAUTO.IDRECURSO = TIPORESOLUAUTO.DESCRIPCION AND RECTIPORESOLUAUTO.IDLENGUAJE = '"
						+ idLenguaje + "'");
		sql.LEFT_OUTER_JOIN(
				"scs_tiposentidoauto TIPOSENTIDOAUTO ON TIPOSENTIDOAUTO.IDTIPOSENTIDOAUTO = EJG.IDTIPOSENTIDOAUTO");
		sql.LEFT_OUTER_JOIN(
				"GEN_RECURSOS_CATALOGOS RECTIPOSENTIDOAUTO ON RECTIPOSENTIDOAUTO.IDRECURSO = TIPOSENTIDOAUTO.DESCRIPCION AND RECTIPOSENTIDOAUTO.IDLENGUAJE = '"
						+ idLenguaje + "'");

		sql.WHERE("EJG.IDINSTITUCION = '" + asuntoClave.getIdInstitucion() + "'");
		sql.WHERE("EJG.ANIO = '" + asuntoClave.getAnio() + "'");
		sql.WHERE("EJG.NUMERO = '" + asuntoClave.getNumero() + "'");
		sql.WHERE("EJG.IDTIPOEJG = '" + asuntoClave.getClave() + "'");

		return sql.toString();
	}

	public String searchClaveAsuntosEJG(AsuntosJusticiableItem asuntosJusticiableItem, Integer tamMax) {
		SQL sql = new SQL();
		SQL sqlOrder = new SQL();

		sqlOrder.SELECT("*");
		sql.SELECT("EJG.idinstitucion, EJG.anio,EJG.numero,to_char(EJG.IDTIPOEJG) AS clave, '' as rol, 'E' as tipo");
		sql.FROM("SCS_EJG EJG");
		sql.INNER_JOIN(
				"SCS_PERSONAJG PERSONA ON (EJG.IDPERSONAJG = PERSONA.IDPERSONA AND PERSONA.IDINSTITUCION = EJG.IDINSTITUCION)");
		sql.INNER_JOIN(
				"SCS_ESTADOEJG ESTADO ON (ESTADO.IDINSTITUCION = EJG.IDINSTITUCION AND ESTADO.IDTIPOEJG = EJG.IDTIPOEJG AND ESTADO.ANIO = "
						+ "EJG.ANIO AND ESTADO.NUMERO = EJG.NUMERO AND ESTADO.FECHABAJA IS NULL AND ESTADO.FECHAINICIO = (SELECT MAX(FECHAINICIO) FROM SCS_ESTADOEJG ESTADO2 WHERE (ESTADO.IDINSTITUCION = ESTADO2.IDINSTITUCION"
						+ " AND ESTADO.IDTIPOEJG = ESTADO2.IDTIPOEJG AND ESTADO.ANIO = ESTADO2.ANIO AND ESTADO.NUMERO = ESTADO2.NUMERO AND ESTADO2.FECHABAJA IS NULL)))");
		sql.WHERE("EJG.idinstitucion = " + asuntosJusticiableItem.getIdInstitucion());

		if (asuntosJusticiableItem.getAnio() != null && asuntosJusticiableItem.getAnio() != "") {
			sql.WHERE("EJG.ANIO = " + asuntosJusticiableItem.getAnio());
		}
		if (asuntosJusticiableItem.getNumero() != null) {
			sql.WHERE("EJG.NUMERO = " + asuntosJusticiableItem.getNumero());
		}
		if (asuntosJusticiableItem.getIdTurno() != null) {
			sql.WHERE("EJG.GUARDIATURNO_IDTURNO = " + asuntosJusticiableItem.getIdTurno());
		}
		if (asuntosJusticiableItem.getIdGuardia() != null) {
			sql.WHERE("EJG.GUARDIATURNO_IDGUARDIA = " + asuntosJusticiableItem.getIdGuardia());
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

		if (asuntosJusticiableItem.getApellidos() != null && asuntosJusticiableItem.getApellidos() != "")
			sql.WHERE(UtilidadesString.filtroTextoBusquedas("REPLACE(CONCAT(apellido1,apellido2), ' ', '')",
					asuntosJusticiableItem.getApellidos().replaceAll("\\s+", "")));
		if (asuntosJusticiableItem.getNombre() != null && asuntosJusticiableItem.getNombre() != "")
			sql.WHERE(UtilidadesString.filtroTextoBusquedas("NOMBRE", asuntosJusticiableItem.getNombre()));

		if (asuntosJusticiableItem.getIdPersonaColegiado() != null) {
			sql.WHERE("EJG.IDPERSONA = " + asuntosJusticiableItem.getIdPersonaColegiado());
		}
		if (asuntosJusticiableItem.getNif() != null && asuntosJusticiableItem.getNif() != "") {
			sql.WHERE("upper(PERSONA.NIF) like upper('%" + asuntosJusticiableItem.getNif() + "%')");
		}
		if (asuntosJusticiableItem.getIdTipoEjg() != null) {
			sql.WHERE("EJG.IDTIPOEJG = " + asuntosJusticiableItem.getIdTipoEjg());
		}
		if (asuntosJusticiableItem.getIdTipoEjColegio() != null) {
			sql.WHERE("EJG.IDTIPOEJGCOLEGIO = " + asuntosJusticiableItem.getIdTipoEjColegio());
		}
		if (asuntosJusticiableItem.getIdEstadoPorEjg() != null) {
			sql.WHERE("ESTADO.IDESTADOPOREJG = " + asuntosJusticiableItem.getIdEstadoPorEjg());
		}
		sqlOrder.FROM("(" + sql + " )");
		if (tamMax != null) {
			Integer tamMaxNumber = tamMax + 1;
			sqlOrder.WHERE("rownum <= " + tamMaxNumber);
		}

		return sqlOrder.toString();
	}

	public String getDictamen(EjgItem ejgItem, String idInstitucion, String idLenguaje) {
		SQL sql = new SQL();

		sql.SELECT("ejg.fechadictamen," + " ejg.dictamen as observaciones," + " recdictamen.descripcion as dictamen,"
				+ " ejg.idtipodictamenejg as iddictamen," + " recfundamento.descripcion as fundamento,"
				+ " ejg.idfundamentocalif as idfundamento");

		sql.FROM("scs_ejg ejg");
		sql.LEFT_OUTER_JOIN(
				"scs_tipodictamenejg tipodictamen on (tipodictamen.idtipodictamenejg=ejg.idtipodictamenejg and tipodictamen.idinstitucion=ejg.idinstitucion)");
		sql.LEFT_OUTER_JOIN(
				"gen_recursos_catalogos recdictamen on (tipodictamen.descripcion=recdictamen.idrecurso and recdictamen.idlenguaje = '"
						+ idLenguaje + "')");
		sql.LEFT_OUTER_JOIN(
				"scs_tipofundamentocalif tipofundamento on (tipofundamento.idtipodictamenejg=ejg.idtipodictamenejg and tipofundamento.idfundamentocalif=ejg.idfundamentocalif and tipofundamento.idinstitucion=ejg.idinstitucion)");
		sql.LEFT_OUTER_JOIN(
				"gen_recursos_catalogos recfundamento on (tipofundamento.descripcion=recfundamento.idrecurso and recfundamento.idlenguaje = '"
						+ idLenguaje + "')");

		if (ejgItem.getAnnio() != null && ejgItem.getAnnio() != "")
			sql.WHERE("ejg.anio = '" + ejgItem.getAnnio() + "'");
		if (ejgItem.getNumEjg() != null && ejgItem.getNumEjg() != "")
			sql.WHERE("ejg.numero = '" + ejgItem.getNumEjg() + "'");
//		sql.WHERE("ejg.numero = '24604'");

		if (ejgItem.getTipoEJG() != null && ejgItem.getTipoEJG() != "")
			sql.WHERE("ejg.idtipoejg = '" + ejgItem.getTipoEJG() + "'");
		if (idInstitucion != null && idInstitucion != "")
			sql.WHERE("ejg.idinstitucion = '" + idInstitucion + "'");

		sql.ORDER_BY("anio DESC, to_number(numejg) DESC");
		return sql.toString();
	}

	public String getResolucion(EjgItem ejgItem, String idInstitucion, String idLenguaje) {
		SQL sql = new SQL();

		sql.SELECT("ejg.idtiporatificacionejg," + " ejg.idfundamentojuridico," + " ejg.ratificaciondictamen,"
				+ " ejg.idorigencajg," + " ejg.aniocajg," + " ejg.numero_cajg," + " ejg.idponente,"
				+ " ejg.fechapresentacionponente," + " ejg.fecharesolucioncajg," + " ejg.fecharatificacion,"
				+ " ejg.fechanotificacion," + " ejg.refauto," + " ejg.turnadoratificacion,"
				+ " ejg.requierenotificarproc," + " ejg.anioacta," + " ejg.idacta,"
				+ " ejg.idinstitucion||','||ejg.anioacta||','||ejg.idacta as idannioacta,"
				+ " resolucion.notascajg AS notascajg");

		sql.FROM("scs_ejg ejg");
		sql.LEFT_OUTER_JOIN(
				"scs_ejg_resolucion resolucion on (resolucion.idinstitucion = ejg.idinstitucion AND resolucion.idtipoejg = ejg.idtipoejg AND resolucion.anio = ejg.anio AND resolucion.numero = ejg.numero)");

		sql.WHERE("ejg.idinstitucion = " + idInstitucion);
		if (ejgItem.getAnnio() != null && ejgItem.getAnnio() != "")
			sql.WHERE("ejg.anio =" + ejgItem.getAnnio());
		if (ejgItem.getNumEjg() != null && ejgItem.getNumEjg() != "")
			sql.WHERE("ejg.numejg =" + ejgItem.getNumEjg());
		if (ejgItem.getTipoEJG() != null && ejgItem.getTipoEJG() != "")
			sql.WHERE("ejg.idtipoejg = " + ejgItem.getTipoEJG());

		return sql.toString();
	}

	public String busquedaColegiadoEJG(ColegiadosSJCSItem item, String idLenguaje) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();
		
		//SUBQUERY PARA EL INNER JOIN
		sql2.SELECT("MAX(COL2.FECHAESTADO)");
		sql2.FROM("CEN_DATOSCOLEGIALESESTADO COL2");
		sql2.WHERE("COL2.IDPERSONA=PER.IDPERSONA AND COL2.IDINSTITUCION = COL.IDINSTITUCION");

		//QUERY PRINCIPAL
		sql.SELECT("PER.NIFCIF NIF, PER.NOMBRE NOMBRE, CONCAT(CONCAT(PER.APELLIDOS1, ' '), PER.APELLIDOS2) APELLIDOS, COL.NCOLEGIADO NCOLEGIADO, COL.NCOMUNITARIO NCOMUNITARIO,"
			+ "ESTADO.IDESTADO IDESTADO,COL.IDINSTITUCION IDINSTITUCION, INS.ABREVIATURA ABREVIATURA, F_SIGA_GETRECURSO(TIPOESTADO.DESCRIPCION,"+idLenguaje+") ESTADO, COL.SITUACIONRESIDENTE RESIDENTE");
		
		sql.FROM("CEN_PERSONA PER");
		
		sql.INNER_JOIN("CEN_COLEGIADO COL ON (COL.IDPERSONA = PER.IDPERSONA)");
		sql.INNER_JOIN("CEN_DATOSCOLEGIALESESTADO ESTADO ON (PER.IDPERSONA = ESTADO.IDPERSONA AND ESTADO.IDINSTITUCION = COL.IDINSTITUCION AND ESTADO.FECHAESTADO = ("+sql2.toString()+"))");
		sql.INNER_JOIN("SCS_GUARDIASCOLEGIADO GUARDIAS ON (PER.IDPERSONA = GUARDIAS.IDPERSONA AND COL.IDINSTITUCION = GUARDIAS.IDINSTITUCION)");
		sql.INNER_JOIN("CEN_ESTADOCOLEGIAL TIPOESTADO ON (TIPOESTADO.IDESTADO=ESTADO.IDESTADO)");
		sql.INNER_JOIN("CEN_INSTITUCION INS ON (INS.IDINSTITUCION=COL.IDINSTITUCION)");
		
		//CONDICIONES WHERE
		if(item.getIdInstitucion()!=null && !item.getIdInstitucion().isEmpty()) {
			sql.WHERE("COL.IDINSTITUCION = "+item.getIdInstitucion());
		}
		
		if(item.getNombre() !=null && !item.getNombre().trim().isEmpty()) {
			sql.WHERE("UPPER(PER.NOMBRE) LIKE UPPER('%"+item.getNombre().trim()+"%')");
		}
		
		if(item.getApellidos() !=null && !item.getApellidos().trim().isEmpty()) {
			sql.WHERE("CONCAT(CONCAT(UPPER(PER.APELLIDOS1), ' '), UPPER(PER.APELLIDOS2)) LIKE UPPER('%"+item.getApellidos().trim()+"%')");
		}
		
		if(item.getIdEstado() !=null && !item.getIdEstado().isEmpty()) {
			sql.WHERE("ESTADO.IDESTADO = "+item.getIdEstado());
		}
		
		if(item.getnColegiado() !=null && !item.getnColegiado().trim().isEmpty()) {
			sql.WHERE("(COL.NCOLEGIADO = "+item.getnColegiado().trim()+" OR COL.NCOMUNITARIO = "+item.getnColegiado().trim()+")");
		}
		
		if(item.getIdTurno() !=null && !item.getIdTurno().isEmpty()) {
			sql.WHERE("GUARDIAS.IDTURNO = " + item.getIdTurno());
		}
		
		if(item.getIdGuardia()!=null && !item.getIdGuardia().isEmpty()) {
			sql.WHERE("GUARDIAS.IDGUARDIA = " + item.getIdGuardia());
		}
		
		sql.GROUP_BY("PER.NIFCIF, PER.NOMBRE, CONCAT(CONCAT(PER.APELLIDOS1, ' '), PER.APELLIDOS2), COL.NCOLEGIADO, "
				+ "COL.NCOMUNITARIO, COL.IDINSTITUCION, INS.ABREVIATURA, ESTADO.IDESTADO, TIPOESTADO.DESCRIPCION, COL.SITUACIONRESIDENTE");
		sql.ORDER_BY("PER.NOMBRE, CONCAT(CONCAT(PER.APELLIDOS1, ' '), PER.APELLIDOS2)");

		return sql.toString();
	}
	
	public String getNumeroEJG(short idTipoEJG, short anio, short idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("NVL(MAX(NUMERO) +1,1)");
		sql.FROM("SCS_EJG");
		sql.WHERE("IDINSTITUCION = "+idInstitucion);
		sql.WHERE("ANIO = "+anio);
		sql.WHERE("IDTIPOEJG = "+idTipoEJG);
		
		return sql.toString();
	}
}
