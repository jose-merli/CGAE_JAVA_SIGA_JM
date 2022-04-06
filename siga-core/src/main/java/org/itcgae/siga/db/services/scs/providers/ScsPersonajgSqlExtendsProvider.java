package org.itcgae.siga.db.services.scs.providers;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.JusticiableBusquedaItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.ScsPersonajgSqlProvider;

public class ScsPersonajgSqlExtendsProvider extends ScsPersonajgSqlProvider {

	private Logger LOGGER = Logger.getLogger(ScsPersonajgSqlExtendsProvider.class);

	public String searchIdPersonaJusticiables(JusticiableBusquedaItem justiciableBusquedaItem, Short idInstitucion,
			Integer tamMax) {

		LOGGER.info("INICIO MONTAJE SQL IDPERSONAS");

		SQL sql = new SQL();
		SQL sqlOrder = new SQL(); 

		sqlOrder.SELECT("*");
		sql.SELECT("idpersona");
		sql.FROM("SCS_PERSONAJG persona");
		sql.WHERE("idinstitucion = '" + idInstitucion + "'");

		if (justiciableBusquedaItem.getNombre() != null && justiciableBusquedaItem.getNombre() != "") {
			String columna = "nombre";
			String cadena = justiciableBusquedaItem.getNombre();
			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
		
		}

		if (justiciableBusquedaItem.getApellidos() != null && justiciableBusquedaItem.getApellidos() != "") {
			
			
			String columna = "REPLACE(CONCAT(apellido1,apellido2), ' ', '')";
			String cadena = justiciableBusquedaItem.getApellidos().replaceAll("\\s+","");
			
			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
		}

		if (justiciableBusquedaItem.getNif() != null && justiciableBusquedaItem.getNif() != "") {
			sql.WHERE("upper(nif) like upper('%" + justiciableBusquedaItem.getNif() + "%')");
		}

		if (justiciableBusquedaItem.getCodigoPostal() != null && justiciableBusquedaItem.getCodigoPostal() != "") {
			sql.WHERE("codigopostal like '%" + justiciableBusquedaItem.getCodigoPostal() + "%'");
		}

		if (justiciableBusquedaItem.getIdProvincia() != null && justiciableBusquedaItem.getIdProvincia() != "") {
			sql.WHERE("idprovincia = '" + justiciableBusquedaItem.getIdProvincia() + "'");
		}

		if (justiciableBusquedaItem.getIdPoblacion() != null && justiciableBusquedaItem.getIdPoblacion() != "") {
			sql.WHERE("idpoblacion = '" + justiciableBusquedaItem.getIdPoblacion() + "'");
		}

		if (justiciableBusquedaItem.getIdRol() != null && justiciableBusquedaItem.getIdRol() != ""
				&& justiciableBusquedaItem.getIdRol().equals(SigaConstants.JUSTICIABLE_ROL_REPRESENTANTE)) {

			SQL sqlRepresentante = new SQL();
			sqlRepresentante.SELECT("*");
			sqlRepresentante.FROM("scs_personajg");
			sqlRepresentante.WHERE("IDREPRESENTANTEJG = persona.IDPERSONA");

			sql.WHERE("exists (" + sqlRepresentante + ")");
		}

		SQL sqlUnidadFamiliar = new SQL();
		sqlUnidadFamiliar.SELECT("unidadFamiliar.idpersona");
		sqlUnidadFamiliar.FROM("SCS_UNIDADFAMILIAREJG unidadFamiliar");
		sqlUnidadFamiliar.WHERE("unidadFamiliar.idinstitucion = '" + idInstitucion + "'");
		sqlUnidadFamiliar.WHERE("unidadFamiliar.idpersona = persona.idpersona");

		SQL sqlContrarioEjg = new SQL();
		sqlContrarioEjg.SELECT("CONTRARIOEJG.idpersona");
		sqlContrarioEjg.FROM("SCS_CONTRARIOSEJG CONTRARIOEJG");
		sqlContrarioEjg.WHERE("CONTRARIOEJG.idinstitucion = '" + idInstitucion + "'");
		sqlContrarioEjg.WHERE("CONTRARIOEJG.idpersona = persona.idpersona");

		SQL sqlContrariosDesigna = new SQL();
		sqlContrariosDesigna.SELECT("CONTRARIOSDESIGNA.idpersona");
		sqlContrariosDesigna.FROM("SCS_CONTRARIOSDESIGNA CONTRARIOSDESIGNA");
		sqlContrariosDesigna.WHERE("CONTRARIOSDESIGNA.idinstitucion = '" + idInstitucion + "'");
		sqlContrariosDesigna.WHERE("CONTRARIOSDESIGNA.idpersona = persona.idpersona");

		SQL sqlDefendidosDesigna = new SQL();
		sqlDefendidosDesigna.SELECT("DEFENDIDOSDESIGNA.idpersona");
		sqlDefendidosDesigna.FROM("SCS_DEFENDIDOSDESIGNA DEFENDIDOSDESIGNA");
		sqlDefendidosDesigna.WHERE("DEFENDIDOSDESIGNA.idinstitucion = '" + idInstitucion + "'");
		sqlDefendidosDesigna.WHERE("DEFENDIDOSDESIGNA.idpersona = persona.idpersona");

		SQL sqlSoj = new SQL();
		sqlSoj.SELECT("SOJ.idpersona");
		sqlSoj.FROM("SCS_SOJ SOJ");
		sqlSoj.WHERE("SOJ.idinstitucion = '" + idInstitucion + "'");
		sqlSoj.WHERE("SOJ.IDPERSONAJG = persona.idpersona");

		SQL sqlContrariosAsistencia = new SQL();
		sqlContrariosAsistencia.SELECT("CONTRARIOSASISTENCIA.idpersona");
		sqlContrariosAsistencia.FROM("SCS_CONTRARIOSASISTENCIA CONTRARIOSASISTENCIA");
		sqlContrariosAsistencia.WHERE("CONTRARIOSASISTENCIA.idinstitucion = '" + idInstitucion + "'");
		sqlContrariosAsistencia.WHERE("CONTRARIOSASISTENCIA.idpersona = persona.idpersona");

		SQL sqlAsistencia = new SQL();
		sqlAsistencia.SELECT("ASISTENCIA.idpersonaJG");
		sqlAsistencia.FROM("SCS_ASISTENCIA ASISTENCIA");
		sqlAsistencia.WHERE("ASISTENCIA.idinstitucion = '" + idInstitucion + "'");
		sqlAsistencia.WHERE("ASISTENCIA.idpersonaJG = persona.idpersona");
		
		if ((UtilidadesString.esCadenaVacia(justiciableBusquedaItem.getIdRol())) && (!UtilidadesString.esCadenaVacia(justiciableBusquedaItem.getAnioDesde()) || 
				!UtilidadesString.esCadenaVacia(justiciableBusquedaItem.getAnioHasta()))){
			
			if (justiciableBusquedaItem.getAnioDesde() != null && justiciableBusquedaItem.getAnioDesde() != "") {
				sqlUnidadFamiliar.WHERE("unidadFamiliar.anio >= " + justiciableBusquedaItem.getAnioDesde());
				sqlContrarioEjg.WHERE("CONTRARIOEJG.anio >= " + justiciableBusquedaItem.getAnioDesde());
				sqlContrariosDesigna.WHERE("CONTRARIOSDESIGNA.anio >= " + justiciableBusquedaItem.getAnioDesde());
				sqlDefendidosDesigna.WHERE("DEFENDIDOSDESIGNA.anio >= " + justiciableBusquedaItem.getAnioDesde());
				sqlSoj.WHERE("SOJ.anio >= " + justiciableBusquedaItem.getAnioDesde());
				sqlContrariosAsistencia.WHERE("CONTRARIOSASISTENCIA.anio >= " + justiciableBusquedaItem.getAnioDesde());
				sqlAsistencia.WHERE("ASISTENCIA.anio >= " + justiciableBusquedaItem.getAnioDesde());

			}

			if (justiciableBusquedaItem.getAnioHasta() != null && justiciableBusquedaItem.getAnioHasta() != "") {
				sqlUnidadFamiliar.WHERE("unidadFamiliar.anio <= " + justiciableBusquedaItem.getAnioHasta());
				sqlContrarioEjg.WHERE("CONTRARIOEJG.anio <= " + justiciableBusquedaItem.getAnioHasta());
				sqlContrariosDesigna.WHERE("CONTRARIOSDESIGNA.anio <= " + justiciableBusquedaItem.getAnioHasta());
				sqlDefendidosDesigna.WHERE("DEFENDIDOSDESIGNA.anio <= " + justiciableBusquedaItem.getAnioHasta());
				sqlSoj.WHERE("SOJ.anio <= " + justiciableBusquedaItem.getAnioHasta());
				sqlContrariosAsistencia.WHERE("CONTRARIOSASISTENCIA.anio <= " + justiciableBusquedaItem.getAnioHasta());
				sqlAsistencia.WHERE("ASISTENCIA.anio <= " + justiciableBusquedaItem.getAnioHasta());
			}


			sql.WHERE("exists (" + sqlUnidadFamiliar + " union all " + sqlContrarioEjg + " union all "
					+ sqlContrariosDesigna + " union all " + sqlDefendidosDesigna + " union all " + sqlSoj
					+ " union all " + sqlContrariosAsistencia + " union all " + sqlAsistencia + ")");
		}
		
		
		
		

		if (justiciableBusquedaItem.getIdRol() != null && justiciableBusquedaItem.getIdRol() != ""
				&& justiciableBusquedaItem.getIdRol().equals(SigaConstants.JUSTICIABLE_ROL_SOLICITANTE)) {

			sqlUnidadFamiliar.WHERE("unidadFamiliar.solicitante = 1");

			if (justiciableBusquedaItem.getAnioDesde() != null && justiciableBusquedaItem.getAnioDesde() != "") {
				sqlUnidadFamiliar.WHERE("unidadFamiliar.anio >= " + justiciableBusquedaItem.getAnioDesde());
				sqlDefendidosDesigna.WHERE("DEFENDIDOSDESIGNA.anio >= " + justiciableBusquedaItem.getAnioDesde());
				sqlSoj.WHERE("SOJ.anio >= " + justiciableBusquedaItem.getAnioDesde());
				sqlAsistencia.WHERE("ASISTENCIA.anio >= " + justiciableBusquedaItem.getAnioDesde());

			}

			if (justiciableBusquedaItem.getAnioHasta() != null && justiciableBusquedaItem.getAnioHasta() != "") {
				sqlUnidadFamiliar.WHERE("unidadFamiliar.anio <= " + justiciableBusquedaItem.getAnioHasta());
				sqlDefendidosDesigna.WHERE("DEFENDIDOSDESIGNA.anio <= " + justiciableBusquedaItem.getAnioHasta());
				sqlSoj.WHERE("SOJ.anio <= " + justiciableBusquedaItem.getAnioHasta());
				sqlAsistencia.WHERE("ASISTENCIA.anio <= " + justiciableBusquedaItem.getAnioHasta());
			}

			sql.WHERE("exists (" + sqlUnidadFamiliar + " union all " + sqlDefendidosDesigna + " union all " + sqlSoj
					+ " union all " + sqlAsistencia + ")");

		}

		if (justiciableBusquedaItem.getIdRol() != null && justiciableBusquedaItem.getIdRol() != ""
				&& justiciableBusquedaItem.getIdRol().equals(SigaConstants.JUSTICIABLE_ROL_UNIDADFAMILIAR)) {

			if (justiciableBusquedaItem.getAnioDesde() != null && justiciableBusquedaItem.getAnioDesde() != "") {
				sqlUnidadFamiliar.WHERE("unidadFamiliar.anio >= " + justiciableBusquedaItem.getAnioDesde());
			}

			if (justiciableBusquedaItem.getAnioHasta() != null && justiciableBusquedaItem.getAnioHasta() != "") {
				sqlUnidadFamiliar.WHERE("unidadFamiliar.anio <= " + justiciableBusquedaItem.getAnioHasta());
			}

			sqlUnidadFamiliar.WHERE("unidadFamiliar.solicitante <> 1");
			sql.WHERE("exists (" + sqlUnidadFamiliar + ")");

		}

		if (justiciableBusquedaItem.getIdRol() != null && justiciableBusquedaItem.getIdRol() != ""
				&& justiciableBusquedaItem.getIdRol().equals(SigaConstants.JUSTICIABLE_ROL_CONTRARIO)) {

			if (justiciableBusquedaItem.getAnioDesde() != null && justiciableBusquedaItem.getAnioDesde() != "") {
				sqlContrarioEjg.WHERE("CONTRARIOEJG.anio >= " + justiciableBusquedaItem.getAnioDesde());
				sqlContrariosDesigna.WHERE("CONTRARIOSDESIGNA.anio >= " + justiciableBusquedaItem.getAnioDesde());
				sqlContrariosAsistencia.WHERE("CONTRARIOSASISTENCIA.anio >= " + justiciableBusquedaItem.getAnioDesde());
			}

			if (justiciableBusquedaItem.getAnioHasta() != null && justiciableBusquedaItem.getAnioHasta() != "") {
				sqlContrarioEjg.WHERE("CONTRARIOEJG.anio <= " + justiciableBusquedaItem.getAnioHasta());
				sqlContrariosDesigna.WHERE("CONTRARIOSDESIGNA.anio <= " + justiciableBusquedaItem.getAnioHasta());
				sqlContrariosAsistencia.WHERE("CONTRARIOSASISTENCIA.anio <= " + justiciableBusquedaItem.getAnioHasta());
			}

			sql.WHERE("exists (" + sqlContrarioEjg + " union all " + sqlContrariosDesigna + " union all "
					+ sqlContrariosAsistencia + ")");
		}

		if (justiciableBusquedaItem.getIdRol() != null && justiciableBusquedaItem.getIdRol() != ""
				&& justiciableBusquedaItem.getIdRol().equals(SigaConstants.JUSTICIABLE_ROL_REPRESENTANTE)) {

			if (justiciableBusquedaItem.getAnioDesde() != null && justiciableBusquedaItem.getAnioDesde() != "") {
				sqlUnidadFamiliar.WHERE("unidadFamiliar.anio >= " + justiciableBusquedaItem.getAnioDesde());
				sqlContrarioEjg.WHERE("CONTRARIOEJG.anio >= " + justiciableBusquedaItem.getAnioDesde());
				sqlContrariosDesigna.WHERE("CONTRARIOSDESIGNA.anio >= " + justiciableBusquedaItem.getAnioDesde());
				sqlDefendidosDesigna.WHERE("DEFENDIDOSDESIGNA.anio >= " + justiciableBusquedaItem.getAnioDesde());
				sqlSoj.WHERE("SOJ.anio >= " + justiciableBusquedaItem.getAnioDesde());
				sqlContrariosAsistencia.WHERE("CONTRARIOSASISTENCIA.anio >= " + justiciableBusquedaItem.getAnioDesde());
				sqlAsistencia.WHERE("ASISTENCIA.anio >= " + justiciableBusquedaItem.getAnioDesde());

			}

			if (justiciableBusquedaItem.getAnioHasta() != null && justiciableBusquedaItem.getAnioHasta() != "") {
				sqlUnidadFamiliar.WHERE("unidadFamiliar.anio <= " + justiciableBusquedaItem.getAnioHasta());
				sqlContrarioEjg.WHERE("CONTRARIOEJG.anio <= " + justiciableBusquedaItem.getAnioHasta());
				sqlContrariosDesigna.WHERE("CONTRARIOSDESIGNA.anio <= " + justiciableBusquedaItem.getAnioHasta());
				sqlDefendidosDesigna.WHERE("DEFENDIDOSDESIGNA.anio <= " + justiciableBusquedaItem.getAnioHasta());
				sqlSoj.WHERE("SOJ.anio <= " + justiciableBusquedaItem.getAnioHasta());
				sqlContrariosAsistencia.WHERE("CONTRARIOSASISTENCIA.anio <= " + justiciableBusquedaItem.getAnioHasta());
				sqlAsistencia.WHERE("ASISTENCIA.anio <= " + justiciableBusquedaItem.getAnioHasta());
			}

			sql.WHERE("exists (" + sqlUnidadFamiliar + " union all " + sqlContrarioEjg + " union all "
					+ sqlContrariosDesigna + " union all " + sqlDefendidosDesigna + " union all " + sqlSoj
					+ " union all " + sqlContrariosAsistencia + " union all " + sqlAsistencia + ")");

		}

		sql.ORDER_BY("fechamodificacion desc");
		sqlOrder.FROM("(" + sql + " )");
		if (tamMax != null) {
			Integer tamMaxNumber = tamMax + 1;
			sqlOrder.WHERE("rownum <= " + tamMaxNumber);
		}

		LOGGER.info("MONTADA SQL IDPERSONAS");

		return sqlOrder.toString();
	}

	public String searchJusticiables(List<StringDTO> justiciableBusquedaItems, Short idInstitucion) {

		SQL sql = new SQL();

		LOGGER.info("INICIO MONTAJE SQL JUSTICIABLES");

		String idPersonas = "";
		List<String> arrayPersonas = new ArrayList<String>();
		String idPersonasIn = "";
		int count = 1;

		for (int i = 0; i < justiciableBusquedaItems.size(); i++) {

			if (count < 100) {

				if (count == 99 || count == justiciableBusquedaItems.size() || i + 1 == justiciableBusquedaItems.size()) {
					idPersonas += justiciableBusquedaItems.get(i).getValor();
					arrayPersonas.add(idPersonas);

				} else {
					idPersonas += justiciableBusquedaItems.get(i).getValor();
					idPersonas += ",";
				}

				count++;

			} else {
				count = 1;
				idPersonas = "";

				idPersonas += justiciableBusquedaItems.get(i).getValor();
				idPersonas += ",";

			}
		}

		sql.SELECT("idpersona");
		sql.SELECT("consulta.idinstitucion");
		sql.SELECT("consulta.fechamodificacion");
		sql.SELECT("consulta.nif");
		sql.SELECT("concat(concat(apellido1 || ' ', apellido2) ||  decode(apellido1, null, '',', '), nombre ) as nombre");
		sql.SELECT("concat(consulta.apellido1 || ' ', consulta.apellido2) as apellidos");
		sql.SELECT("consulta.nombre as nombresolo");
		sql.SELECT(
				"LISTAGG(consulta.asunto, ', ') WITHIN GROUP (ORDER BY fechamodificacionasunto desc,consulta.idpersona desc) AS ASUNTOS");
		sql.SELECT("count(consulta.asunto) as numeroasuntos");
		sql.SELECT(
				"nvl(SUBSTR(LISTAGG(consulta.asunto, ', ') WITHIN GROUP (ORDER BY fechamodificacionasunto desc,consulta.idpersona desc), 0, INSTR(LISTAGG(consulta.asunto, ', ')"
						+ "		WITHIN GROUP (ORDER BY fechamodificacionasunto desc,consulta.idpersona desc), ',')-1),"
						+ "		LISTAGG(consulta.asunto, ', ') WITHIN GROUP (ORDER BY fechamodificacionasunto desc,consulta.idpersona desc)) as ultimoasunto");

		SQL sqlUnidadFamiliar = new SQL();
		sqlUnidadFamiliar.SELECT("unidadFamiliar.idpersona");
		sqlUnidadFamiliar.SELECT("per.idinstitucion");
		sqlUnidadFamiliar.SELECT("per.fechamodificacion");
		sqlUnidadFamiliar.SELECT("per.nif");
		sqlUnidadFamiliar.SELECT("per.nombre");
		sqlUnidadFamiliar.SELECT("per.apellido1");
		sqlUnidadFamiliar.SELECT("per.apellido2");
		sqlUnidadFamiliar.SELECT("e.fechaapertura as fechamodificacionasunto");
		sqlUnidadFamiliar.SELECT("concat('E' || unidadFamiliar.anio || '/',lpad(e.NumEJG,5,'0') ) as asunto");
		sqlUnidadFamiliar.FROM("SCS_PERSONAJG per");
		sqlUnidadFamiliar.INNER_JOIN(
				"SCS_UNIDADFAMILIAREJG unidadFamiliar on unidadFamiliar.idpersona = per.idpersona and unidadFamiliar.idinstitucion = per.idinstitucion");
		sqlUnidadFamiliar.INNER_JOIN(
				"scs_ejg e on e.idinstitucion = unidadFamiliar.idinstitucion and unidadFamiliar.anio = e.anio and unidadFamiliar.idinstitucion = e.idinstitucion and unidadFamiliar.numero = e.numero and unidadFamiliar.idtipoejg = e.idtipoejg");
		sqlUnidadFamiliar.WHERE("unidadFamiliar.idinstitucion = '" + idInstitucion + "'");

		idPersonasIn = "";
		for (int j = 0; j < arrayPersonas.size(); j++) {

			if (j != (arrayPersonas.size() - 1)) {
				idPersonasIn += "unidadFamiliar.idpersona in(" + arrayPersonas.get(j) + ") OR ";

			} else {
				idPersonasIn += "unidadFamiliar.idpersona in(" + arrayPersonas.get(j) + ")";
			}

		}

		sqlUnidadFamiliar.WHERE("(" + idPersonasIn + ")");

		SQL sqlContrarioEjg = new SQL();
		sqlContrarioEjg.SELECT("CONTRARIOEJG.idpersona");
		sqlContrarioEjg.SELECT("per.idinstitucion");
		sqlContrarioEjg.SELECT("per.fechamodificacion");
		sqlContrarioEjg.SELECT("per.nif");
		sqlContrarioEjg.SELECT("per.nombre");
		sqlContrarioEjg.SELECT("per.apellido1");
		sqlContrarioEjg.SELECT("per.apellido2");
		sqlContrarioEjg.SELECT("e.fechaapertura as fechamodificacionasunto");
		sqlContrarioEjg.SELECT("concat('E' || CONTRARIOEJG.anio || '/',lpad(e.NumEJG,5,'0') ) as asunto");
		sqlContrarioEjg.FROM("SCS_PERSONAJG per");
		sqlContrarioEjg.INNER_JOIN(
				"SCS_CONTRARIOSEJG CONTRARIOEJG  on CONTRARIOEJG.idpersona = per.idpersona and CONTRARIOEJG.idinstitucion = per.idinstitucion");
		sqlContrarioEjg.INNER_JOIN(
				"scs_ejg e on e.idinstitucion = CONTRARIOEJG.idinstitucion and CONTRARIOEJG.anio = e.anio and CONTRARIOEJG.idinstitucion = e.idinstitucion and CONTRARIOEJG.numero = e.numero and CONTRARIOEJG.idtipoejg = e.idtipoejg");
		sqlContrarioEjg.WHERE("CONTRARIOEJG.idinstitucion = '" + idInstitucion + "'");

		idPersonasIn = "";
		for (int j = 0; j < arrayPersonas.size(); j++) {

			if (j != (arrayPersonas.size() - 1)) {
				idPersonasIn += "CONTRARIOEJG.idpersona in(" + arrayPersonas.get(j) + ") OR ";

			} else {
				idPersonasIn += "CONTRARIOEJG.idpersona in(" + arrayPersonas.get(j) + ")";
			}

		}

		sqlContrarioEjg.WHERE("(" + idPersonasIn + ")");

		SQL sqlContrariosDesigna = new SQL();
		sqlContrariosDesigna.SELECT("CONTRARIOSDESIGNA.idpersona");
		sqlContrariosDesigna.SELECT("per.idinstitucion");
		sqlContrariosDesigna.SELECT("per.fechamodificacion");
		sqlContrariosDesigna.SELECT("per.nif");
		sqlContrariosDesigna.SELECT("per.nombre");
		sqlContrariosDesigna.SELECT("per.apellido1");
		sqlContrariosDesigna.SELECT("per.apellido2");
		sqlContrariosDesigna.SELECT("d.FECHAENTRADA  as fechamodificacionasunto");
		sqlContrariosDesigna.SELECT("concat('D' || CONTRARIOSDESIGNA.anio || '/',lpad(d.codigo,5,'0') ) as asunto");
		sqlContrariosDesigna.FROM("SCS_PERSONAJG per");
		sqlContrariosDesigna.INNER_JOIN(
				"SCS_CONTRARIOSDESIGNA CONTRARIOSDESIGNA on CONTRARIOSDESIGNA.idpersona = per.idpersona and CONTRARIOSDESIGNA.idinstitucion = per.idinstitucion");
		sqlContrariosDesigna.INNER_JOIN(
				"scs_designa d on  d.idinstitucion = CONTRARIOSDESIGNA.idinstitucion and CONTRARIOSDESIGNA.anio = d.anio and CONTRARIOSDESIGNA.idinstitucion = d.idinstitucion and CONTRARIOSDESIGNA.numero = d.numero and CONTRARIOSDESIGNA.idturno = d.idturno and CONTRARIOSDESIGNA.idpersona = CONTRARIOSDESIGNA.idpersona");
		sqlContrariosDesigna.WHERE("CONTRARIOSDESIGNA.idinstitucion = '" + idInstitucion + "'");

		idPersonasIn = "";
		for (int j = 0; j < arrayPersonas.size(); j++) {

			if (j != (arrayPersonas.size() - 1)) {
				idPersonasIn += "CONTRARIOSDESIGNA.idpersona in(" + arrayPersonas.get(j) + ") OR ";

			} else {
				idPersonasIn += "CONTRARIOSDESIGNA.idpersona in(" + arrayPersonas.get(j) + ")";
			}

		}

		sqlContrariosDesigna.WHERE("(" + idPersonasIn + ")");

		SQL sqlDefendidosDesigna = new SQL();
		sqlDefendidosDesigna.SELECT("DEFENDIDOSDESIGNA.idpersona");
		sqlDefendidosDesigna.SELECT("per.idinstitucion");
		sqlDefendidosDesigna.SELECT("per.fechamodificacion");
		sqlDefendidosDesigna.SELECT("per.nif");
		sqlDefendidosDesigna.SELECT("per.nombre");
		sqlDefendidosDesigna.SELECT("per.apellido1");
		sqlDefendidosDesigna.SELECT("per.apellido2");
		sqlDefendidosDesigna.SELECT("d.FECHAENTRADA  as fechamodificacionasunto");
		sqlDefendidosDesigna.SELECT("concat('D' || DEFENDIDOSDESIGNA.anio || '/',lpad(d.codigo,5,'0') ) as asunto");
		sqlDefendidosDesigna.FROM("SCS_PERSONAJG per");
		sqlDefendidosDesigna.INNER_JOIN(
				"SCS_DEFENDIDOSDESIGNA DEFENDIDOSDESIGNA on DEFENDIDOSDESIGNA.idpersona = per.idpersona and DEFENDIDOSDESIGNA.idinstitucion = per.idinstitucion");
		sqlDefendidosDesigna.INNER_JOIN(
				"scs_designa d on d.idinstitucion = DEFENDIDOSDESIGNA.idinstitucion and DEFENDIDOSDESIGNA.anio = d.anio and DEFENDIDOSDESIGNA.idinstitucion = d.idinstitucion and DEFENDIDOSDESIGNA.numero = d.numero and DEFENDIDOSDESIGNA.idturno = d.idturno");
		sqlDefendidosDesigna.WHERE("DEFENDIDOSDESIGNA.idinstitucion = '" + idInstitucion + "'");

		idPersonasIn = "";
		for (int j = 0; j < arrayPersonas.size(); j++) {

			if (j != (arrayPersonas.size() - 1)) {
				idPersonasIn += "DEFENDIDOSDESIGNA.idpersona in(" + arrayPersonas.get(j) + ") OR ";

			} else {
				idPersonasIn += "DEFENDIDOSDESIGNA.idpersona in(" + arrayPersonas.get(j) + ")";
			}

		}

		sqlDefendidosDesigna.WHERE("(" + idPersonasIn + ")");

		SQL sqlSoj = new SQL();
		sqlSoj.SELECT("SOJ.idpersonajg");
		sqlSoj.SELECT("per.idinstitucion");
		sqlSoj.SELECT("per.fechamodificacion");
		sqlSoj.SELECT("per.nif");
		sqlSoj.SELECT("per.nombre");
		sqlSoj.SELECT("per.apellido1");
		sqlSoj.SELECT("per.apellido2");
		sqlSoj.SELECT("SOJ.FECHAAPERTURA as fechamodificacionasunto");
		sqlSoj.SELECT("concat('S' || SOJ.anio || '/',lpad(SOJ.numSOJ,5,'0') ) as asunto");
		sqlSoj.FROM("SCS_PERSONAJG per");
		sqlSoj.INNER_JOIN("SCS_SOJ SOJ  on SOJ.idpersonajg = per.idpersona and SOJ.idinstitucion = per.idinstitucion");
		sqlSoj.WHERE("SOJ.idinstitucion = '" + idInstitucion + "'");

		idPersonasIn = "";
		for (int j = 0; j < arrayPersonas.size(); j++) {

			if (j != (arrayPersonas.size() - 1)) {
				idPersonasIn += "SOJ.idpersonajg in(" + arrayPersonas.get(j) + ") OR ";

			} else {
				idPersonasIn += "SOJ.idpersonajg in(" + arrayPersonas.get(j) + ")";
			}

		}

		sqlSoj.WHERE("(" + idPersonasIn + ")");

		SQL sqlContrariosAsistencia = new SQL();
		sqlContrariosAsistencia.SELECT("CONTRARIOSASISTENCIA.idpersona");
		sqlContrariosAsistencia.SELECT("per.idinstitucion");
		sqlContrariosAsistencia.SELECT("per.fechamodificacion");
		sqlContrariosAsistencia.SELECT("per.nif");
		sqlContrariosAsistencia.SELECT("per.nombre");
		sqlContrariosAsistencia.SELECT("per.apellido1");
		sqlContrariosAsistencia.SELECT("per.apellido2");
		sqlContrariosAsistencia.SELECT("A.FECHAHORA as fechamodificacionasunto");
		sqlContrariosAsistencia
				.SELECT("concat('A' || CONTRARIOSASISTENCIA.anio || '/',lpad(a.numero,5,'0') ) as asunto");
		sqlContrariosAsistencia.FROM("SCS_PERSONAJG per");
		sqlContrariosAsistencia.INNER_JOIN(
				"SCS_CONTRARIOSASISTENCIA CONTRARIOSASISTENCIA on CONTRARIOSASISTENCIA.idpersona = per.idpersona and CONTRARIOSASISTENCIA.idinstitucion = per.idinstitucion");
		sqlContrariosAsistencia.INNER_JOIN(
				"scs_asistencia a on a.idinstitucion = CONTRARIOSASISTENCIA.idinstitucion and CONTRARIOSASISTENCIA.anio = a.anio and CONTRARIOSASISTENCIA.idinstitucion = a.idinstitucion and CONTRARIOSASISTENCIA.numero = a.numero");
		sqlContrariosAsistencia.WHERE("CONTRARIOSASISTENCIA.idinstitucion = '" + idInstitucion + "'");

		idPersonasIn = "";
		for (int j = 0; j < arrayPersonas.size(); j++) {

			if (j != (arrayPersonas.size() - 1)) {
				idPersonasIn += "CONTRARIOSASISTENCIA.idpersona in(" + arrayPersonas.get(j) + ") OR ";

			} else {
				idPersonasIn += "CONTRARIOSASISTENCIA.idpersona in(" + arrayPersonas.get(j) + ")";
			}

		}

		sqlContrariosAsistencia.WHERE("(" + idPersonasIn + ")");

		SQL sqlAsistencia = new SQL();
		sqlAsistencia.SELECT("ASISTENCIA.idpersonaJG");
		sqlAsistencia.SELECT("per.idinstitucion");
		sqlAsistencia.SELECT("per.fechamodificacion");
		sqlAsistencia.SELECT("per.nif");
		sqlAsistencia.SELECT("per.nombre");
		sqlAsistencia.SELECT("per.apellido1");
		sqlAsistencia.SELECT("per.apellido2");
		sqlAsistencia.SELECT("ASISTENCIA.FECHAHORA as fechamodificacionasunto");
		sqlAsistencia.SELECT("concat('A' || ASISTENCIA.anio || '/',lpad(ASISTENCIA.numero,5,'0') ) as asunto");
		sqlAsistencia.FROM("SCS_PERSONAJG per");
		sqlAsistencia.INNER_JOIN(
				"SCS_ASISTENCIA ASISTENCIA on ASISTENCIA.idpersonaJG = per.idpersona and ASISTENCIA.idinstitucion = per.idinstitucion");
		sqlAsistencia.WHERE("ASISTENCIA.idinstitucion = '" + idInstitucion + "'");

		idPersonasIn = "";
		for (int j = 0; j < arrayPersonas.size(); j++) {

			if (j != (arrayPersonas.size() - 1)) {
				idPersonasIn += "ASISTENCIA.idpersonaJG in(" + arrayPersonas.get(j) + ") OR ";

			} else {
				idPersonasIn += "ASISTENCIA.idpersonaJG in(" + arrayPersonas.get(j) + ")";
			}

		}

		sqlAsistencia.WHERE("(" + idPersonasIn + ")");

		SQL sqlPersonajg = new SQL();
		sqlPersonajg.SELECT("per.idpersona");
		sqlPersonajg.SELECT("per.idinstitucion");
		sqlPersonajg.SELECT("per.fechamodificacion");
		sqlPersonajg.SELECT("per.nif");
		sqlPersonajg.SELECT("per.nombre");
		sqlPersonajg.SELECT("per.apellido1");
		sqlPersonajg.SELECT("per.apellido2");
		sqlPersonajg.SELECT("null as fechamodificacionasunto");
		sqlPersonajg.SELECT("null as asunto");
		sqlPersonajg.FROM("SCS_PERSONAJG per");
		sqlPersonajg.WHERE("per.idinstitucion = '" + idInstitucion + "'");

		idPersonasIn = "";
		for (int j = 0; j < arrayPersonas.size(); j++) {

			if (j != (arrayPersonas.size() - 1)) {
				idPersonasIn += "per.idpersona in(" + arrayPersonas.get(j) + ") OR ";

			} else {
				idPersonasIn += "per.idpersona in(" + arrayPersonas.get(j) + ")";
			}

		}

		sqlPersonajg.WHERE("(" + idPersonasIn + ")");

		sql.FROM("(" + sqlUnidadFamiliar + " union " + sqlContrarioEjg + " union all " + sqlContrariosDesigna
				+ " union all " + sqlDefendidosDesigna + " union all " + sqlSoj + " union all "
				+ sqlContrariosAsistencia + "union all " + sqlAsistencia + " union all " + sqlPersonajg + ") consulta");

		sql.GROUP_BY(
				"idpersona , consulta.fechamodificacion, consulta.nif, consulta.nombre, consulta.apellido1, consulta.apellido2, consulta.idinstitucion");
		sql.ORDER_BY("consulta.fechamodificacion desc");

		LOGGER.info("MONTADA SQL JUSTICIABLES");

		return sql.toString();
	}

	public String getIdPersonajg(Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("MAX(IDPERSONA) AS IDPERSONA");
		sql.FROM("SCS_PERSONAJG");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}

	public String searchClaveAsuntosJusticiable(String idPersona, Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("idinstitucion");
		sql.SELECT("anio");
		sql.SELECT("numero");
		sql.SELECT("clave");
		sql.SELECT("rol");
		sql.SELECT("tipo");

		SQL sqlUnidadFamiliar = new SQL();
		sqlUnidadFamiliar.SELECT("unidadFamiliar.idinstitucion");
		sqlUnidadFamiliar.SELECT("unidadfamiliar.anio");
		sqlUnidadFamiliar.SELECT("unidadfamiliar.numero");
		sqlUnidadFamiliar.SELECT("to_char(unidadfamiliar.idtipoejg) clave");
		sqlUnidadFamiliar.SELECT(
				"case when unidadFamiliar.solicitante = 1 then 'Solicitante' else 'Unidad Familiar' end as rol");
		sqlUnidadFamiliar.SELECT("'E' as tipo");

		sqlUnidadFamiliar.FROM("SCS_UNIDADFAMILIAREJG unidadFamiliar");
		sqlUnidadFamiliar.WHERE("unidadFamiliar.idinstitucion = '" + idInstitucion + "'");
		sqlUnidadFamiliar.WHERE("unidadFamiliar.idpersona = '" + idPersona + "'");

		SQL sqlContrarioEjg = new SQL();
		sqlContrarioEjg.SELECT("CONTRARIOEJG.idinstitucion");
		sqlContrarioEjg.SELECT("CONTRARIOEJG.anio");
		sqlContrarioEjg.SELECT("CONTRARIOEJG.numero");
		sqlContrarioEjg.SELECT("to_char(CONTRARIOEJG.idtipoejg) clave");
		sqlContrarioEjg.SELECT("'Contrario' as rol");
		sqlContrarioEjg.SELECT("'E' as tipo");
		sqlContrarioEjg.FROM("SCS_CONTRARIOSEJG CONTRARIOEJG");
		sqlContrarioEjg.WHERE("CONTRARIOEJG.idinstitucion = '" + idInstitucion + "'");
		sqlContrarioEjg.WHERE("CONTRARIOEJG.idpersona = '" + idPersona + "'");

		SQL sqlContrariosDesigna = new SQL();
		sqlContrariosDesigna.SELECT("CONTRARIOSDESIGNA.idinstitucion");
		sqlContrariosDesigna.SELECT("CONTRARIOSDESIGNA.anio");
		sqlContrariosDesigna.SELECT("CONTRARIOSDESIGNA.numero");
		sqlContrariosDesigna.SELECT("to_char(CONTRARIOSDESIGNA.idturno) clave");
		sqlContrariosDesigna.SELECT("'Contrario' as rol");
		sqlContrariosDesigna.SELECT("'D' as tipo");
		sqlContrariosDesigna.FROM("SCS_CONTRARIOSDESIGNA CONTRARIOSDESIGNA");
		sqlContrariosDesigna.WHERE("CONTRARIOSDESIGNA.idinstitucion = '" + idInstitucion + "'");
		sqlContrariosDesigna.WHERE("CONTRARIOSDESIGNA.idpersona = '" + idPersona + "'");

		SQL sqlDefendidosDesigna = new SQL();
		sqlDefendidosDesigna.SELECT("DEFENDIDOSDESIGNA.idinstitucion");
		sqlDefendidosDesigna.SELECT("DEFENDIDOSDESIGNA.anio");
		sqlDefendidosDesigna.SELECT("DEFENDIDOSDESIGNA.numero");
		sqlDefendidosDesigna.SELECT("to_char(DEFENDIDOSDESIGNA.idturno) clave");
		sqlDefendidosDesigna.SELECT("'Solicitante' as rol");
		sqlDefendidosDesigna.SELECT("'D' as tipo");
		sqlDefendidosDesigna.FROM("SCS_DEFENDIDOSDESIGNA DEFENDIDOSDESIGNA");
		sqlDefendidosDesigna.WHERE("DEFENDIDOSDESIGNA.idinstitucion = '" + idInstitucion + "'");
		sqlDefendidosDesigna.WHERE("DEFENDIDOSDESIGNA.idpersona = '" + idPersona + "'");

		SQL sqlSoj = new SQL();
		sqlSoj.SELECT("SOJ.idinstitucion");
		sqlSoj.SELECT("SOJ.anio");
		sqlSoj.SELECT("SOJ.numero");
		sqlSoj.SELECT("to_char(SOJ.IDTIPOSOJ) clave");
		sqlSoj.SELECT("'Solicitante' as rol");
		sqlSoj.SELECT("'S' as tipo");
		sqlSoj.FROM("SCS_SOJ SOJ");
		sqlSoj.WHERE("SOJ.idinstitucion = '" + idInstitucion + "'");
		sqlSoj.WHERE("SOJ.idpersonajg = '" + idPersona + "'");

		SQL sqlContrariosAsistencia = new SQL();
		sqlContrariosAsistencia.SELECT("CONTRARIOSASISTENCIA.idinstitucion");
		sqlContrariosAsistencia.SELECT("CONTRARIOSASISTENCIA.anio");
		sqlContrariosAsistencia.SELECT("CONTRARIOSASISTENCIA.numero");
		sqlContrariosAsistencia.SELECT("'' as clave");
		sqlContrariosAsistencia.SELECT("'Contrario' as rol");
		sqlContrariosAsistencia.SELECT("'A' as tipo");
		sqlContrariosAsistencia.FROM("SCS_CONTRARIOSASISTENCIA CONTRARIOSASISTENCIA");
		sqlContrariosAsistencia.WHERE("CONTRARIOSASISTENCIA.idinstitucion = '" + idInstitucion + "'");
		sqlContrariosAsistencia.WHERE("CONTRARIOSASISTENCIA.idpersona = '" + idPersona + "'");

		SQL sqlAsistencia = new SQL();
		sqlAsistencia.SELECT("ASISTENCIA.idinstitucion");
		sqlAsistencia.SELECT("ASISTENCIA.anio");
		sqlAsistencia.SELECT("ASISTENCIA.numero");
		sqlAsistencia.SELECT("'' as clave");
		sqlAsistencia.SELECT("'Solicitante' as rol");
		sqlAsistencia.SELECT("'A' as tipo");
		sqlAsistencia.FROM("SCS_ASISTENCIA ASISTENCIA");
		sqlAsistencia.WHERE("ASISTENCIA.idinstitucion = '" + idInstitucion + "'");
		sqlAsistencia.WHERE("ASISTENCIA.idpersonaJG = '" + idPersona + "'");

		sql.FROM("(" + sqlUnidadFamiliar + " union " + sqlContrarioEjg + " union all " + sqlContrariosDesigna
				+ " union all " + sqlDefendidosDesigna + " union all " + sqlSoj + " union all "
				+ sqlContrariosAsistencia + "union all " + sqlAsistencia + ") consulta");

		return sql.toString();
	}
	
	
	
	public String unidadFamiliarEJG(EjgItem ejgItem, String idInstitucion, Integer tamMaximo, String idLenguaje) {
		SQL sql = new SQL();
		SQL sqlEstado = new SQL();
		SQL sqlFechaSolicitud = new SQL();
		
		sqlEstado.SELECT("eejg_p.estado");
		sqlEstado.FROM("scs_eejg_peticiones eejg_p");
		sqlEstado.WHERE("eejg_p.idpeticion = (select max(eejg_p.IDPETICION) from scs_eejg_peticiones eejg_p where eejg_p.idpersona = uf.idpersona)");
		
		sqlFechaSolicitud.SELECT("eejg_p.fechasolicitud");
		sqlFechaSolicitud.FROM("scs_eejg_peticiones eejg_p");
		sqlFechaSolicitud.WHERE("eejg_p.idpeticion = (select max(eejg_p.IDPETICION) from scs_eejg_peticiones eejg_p where eejg_p.idpersona = uf.idpersona)");
		
		
		sql.SELECT("uf.idinstitucion," + 
					" uf.idtipoejg," + 
					" uf.anio," + 
					" uf.numero," + 
					" uf.idpersona," + 
					" uf.fechabaja," + 
					" uf.solicitante," + 
					" pjg.nif," + 
					"pjg.apellido1 || ' ' || pjg.apellido2 || ', ' || pjg.nombre as nombrecompletopjg," +
					" pjg.nombre," + 
					" pjg.apellido1," + 
					" pjg.apellido2," + 
					" pjg.direccion," + 
					" uf.encalidadde," + 
					" pd.descripcion," + 
					 " uf.BIENESINMUEBLES,\r\n"
					+ "	uf.BIENESMUEBLES,\r\n"
					+ "	uf.CIRCUNSTANCIAS_EXCEPCIONALES,\r\n"
					+ "	uf.DESCRIPCIONINGRESOSANUALES,\r\n"
					+ "	uf.IDPARENTESCO,\r\n"
					+ "	uf.IDTIPOGRUPOLAB,\r\n"
					+ "	uf.IDTIPOINGRESO,\r\n"
					+ "	uf.IMPORTEBIENESINMUEBLES,\r\n"
					+ "	uf.IMPORTEBIENESMUEBLES,\r\n"
					+ "	uf.IMPORTEINGRESOSANUALES,\r\n"
					+ "	uf.IMPORTEOTROSBIENES,\r\n"
					+ "	uf.INCAPACITADO,\r\n"
					+ "	uf.OBSERVACIONES,\r\n"
					+ "	uf.OTROSBIENES");
		sql.SELECT("case when pjg.idrepresentantejg is not null then repre.apellido1 || ' ' || repre.apellido2 || ', ' || repre.nombre\r\n"
				+ "else null end as representante");
		sql.SELECT("case when pjg.idrepresentantejg is not null then repre.direccion\r\n"
				+ "else null end as direccionRepresentante");
		sql.SELECT("case when pjg.idrepresentantejg is not null then repre.nif\r\n"
				+ "else null end as nifRepresentante");
		sql.SELECT("ejg.idpersonajg as solicitanteppal");
		sql.SELECT(" ( " + sqlEstado.toString()+") estado ");
		sql.SELECT(" ( " + sqlFechaSolicitud.toString()+") fechasolicitud ");
		sql.FROM("scs_unidadfamiliarejg uf");
		sql.JOIN("scs_ejg ejg on ejg.anio = uf.anio AND ejg.numero = uf.numero AND ejg.idtipoejg = uf.idtipoejg AND ejg.idinstitucion = uf.idinstitucion");
		
		sql.INNER_JOIN("scs_personajg pjg on (uf.idpersona=pjg.idpersona and uf.idinstitucion=pjg.idinstitucion)");
		sql.LEFT_OUTER_JOIN("scs_personajg repre on (repre.idpersona =  pjg.idrepresentantejg AND repre.idinstitucion=pjg.idinstitucion)");
		sql.LEFT_OUTER_JOIN("(select grc.descripcion, p.idparentesco, p.idinstitucion, grc.idlenguaje from scs_parentesco p inner join gen_recursos_catalogos grc on (grc.idrecurso=p.descripcion) where grc.idlenguaje= '" + idLenguaje + "'" + " ) pd on (pd.idparentesco=uf.idparentesco and pd.idinstitucion=uf.idinstitucion)");
		//sql.LEFT_OUTER_JOIN("scs_eejg_peticiones eejg_p on (eejg_p.numero = uf.numero and eejg_p.anio=uf.anio and eejg_p.idtipoejg = uf.idtipoejg and eejg_p.idinstitucion = uf.idinstitucion and eejg_p.idpersona=uf.idpersona)");

		if(ejgItem.getAnnio() != null && ejgItem.getAnnio() != "")
			sql.WHERE("uf.anio = '" + ejgItem.getAnnio() + "'");
		if(ejgItem.getNumero() != null && ejgItem.getNumero() != "")
			sql.WHERE("uf.numero = '" + ejgItem.getNumero() + "'");
		if(ejgItem.getTipoEJG() != null && ejgItem.getTipoEJG() != "")
			sql.WHERE("uf.idtipoejg = '" + ejgItem.getTipoEJG() + "'");
		if(idInstitucion != null && idInstitucion != "")
			sql.WHERE("uf.idinstitucion = '" + idInstitucion + "'");
//		if(ejgItem.getIdPersona() != null && ejgItem.getIdPersona() != "")
//			sql.WHERE("pjgP.idpersona = '" + ejgItem.getIdPersona() + "'");
//		sql.WHERE("pjgP.idinstitucion = uf.idinstitucion ");
		/*sql.LEFT_OUTER_JOIN("(\r\n"
				+ "SELECT * \r\n"
				+ "        FROM scs_eejg_peticiones eejg_p\r\n"
				+ "        WHERE eejg_p.numero = '" + ejgItem.getNumero() + "' and eejg_p.anio='" + ejgItem.getAnnio() + "' and eejg_p.idtipoejg = '" + ejgItem.getTipoEJG() + "' and eejg_p.idinstitucion = '" + idInstitucion + "'  \r\n"
				+ "    )  eejg_p on eejg_p.idpersona=uf.idpersona");
		*///sql.WHERE("eejg_p.fechaconsulta=(SELECT MAX(p2.FECHACONSULTA) from scs_eejg_peticiones p2 where eejg_p.nif=p2.nif)");
		
		
		if (tamMaximo != null) {
			Integer tamMaxNumber = tamMaximo + 1;
			sql.WHERE("rownum <= " + tamMaxNumber);

		}
		
		return sql.toString();
	}

	public String getPersonaJG(Long idPersonaJG, Integer idInstitucion) {
		SQL sql  = new SQL();
		sql.SELECT("PER.NOMBRE, PER.APELLIDO1,  PER.APELLIDO2");
		sql.SELECT("((SELECT (UPPER(SUBSTR(F_SIGA_GETRECURSO(TV.DESCRIPCION,1), 1, 1))) || (LOWER(SUBSTR(F_SIGA_GETRECURSO(TV.DESCRIPCION, 1), 2))) FROM CEN_TIPOVIA TV WHERE TV.IDTIPOVIA = PER.IDTIPOVIA AND TV.IDINSTITUCION = PER.IDINSTITUCION) || ' ' || PER.DIRECCION || ' ' || PER.NUMERODIR || ' ' || PER.ESCALERADIR || ' ' || PER.PISODIR || ' ' || PER.PUERTADIR) AS DIRECCION");
		sql.SELECT("PER.EXISTEDOMICILIO, PER.CODIGOPOSTAL, PER.IDPAIS, PER.IDPROVINCIA PERIDPROVINCIA");
		sql.SELECT("PER.NIF, PER.IDPOBLACION,PER.FAX,PER.CORREOELECTRONICO");
		sql.SELECT("POB.NOMBRE POBLACION,PRO.IDPROVINCIA PROIDPROVINCIA, PRO.NOMBRE PROVINCIA");
		sql.FROM("SCS_PERSONAJG PER,CEN_POBLACIONES POB,CEN_PROVINCIAS PRO");
		sql.WHERE("PER.IDPOBLACION = POB.IDPOBLACION(+)");
		sql.WHERE("PER.IDPROVINCIA  = PRO.IDPROVINCIA(+)");
		sql.WHERE("IDPERSONA = " + idPersonaJG);
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		return sql.toString();
	}

}
