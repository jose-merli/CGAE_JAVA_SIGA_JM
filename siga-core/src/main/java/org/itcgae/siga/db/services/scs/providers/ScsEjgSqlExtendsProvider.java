package org.itcgae.siga.db.services.scs.providers;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;

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

        if (ejgItem.getEstadoEJG() != null && ejgItem.getEstadoEJG() != "" && !ejgItem.isUltimoEstado()) {
            sql.INNER_JOIN("SCS_ESTADOEJG ESTADO " +
                    "ON ESTADO.IDINSTITUCION = EJG.IDINSTITUCION " +
                    "AND ESTADO.IDTIPOEJG = EJG.IDTIPOEJG " +
                    "AND ESTADO.IDTIPOEJG = EJG.IDTIPOEJG " +
                    "AND ESTADO.ANIO = EJG.ANIO " +
                    "AND ESTADO.NUMERO = EJG.NUMERO " +
                    "AND ESTADO.FECHABAJA IS NULL");
        } else {
            sql.INNER_JOIN("SCS_ESTADOEJG ESTADO ON (ESTADO.IDINSTITUCION = EJG.IDINSTITUCION "
                    + "AND ESTADO.IDTIPOEJG = EJG.IDTIPOEJG AND ESTADO.IDTIPOEJG = EJG.IDTIPOEJG "
                    + "AND ESTADO.ANIO = EJG.ANIO " + "AND ESTADO.NUMERO = EJG.NUMERO " + "AND ESTADO.FECHABAJA IS NULL "
                    + "AND ESTADO.idestadoporejg = (SELECT MAX(idestadoporejg) FROM SCS_ESTADOEJG ESTADO2 WHERE (ESTADO.IDINSTITUCION = ESTADO2.IDINSTITUCION "
                    + "AND ESTADO.IDTIPOEJG = ESTADO2.IDTIPOEJG " + "AND ESTADO.ANIO = ESTADO2.ANIO "
                    + "AND ESTADO.NUMERO = ESTADO2.NUMERO " + "AND ESTADO2.FECHABAJA IS NULL) "
                    + "AND ESTADO2.FECHAINICIO = (SELECT MAX(FECHAINICIO) FROM SCS_ESTADOEJG ESTADO3 WHERE (ESTADO3.IDINSTITUCION = ESTADO2.IDINSTITUCION "
                    + "AND ESTADO.IDTIPOEJG = ESTADO3.IDTIPOEJG " + "AND ESTADO.ANIO = ESTADO3.ANIO "
                    + "AND ESTADO.NUMERO = ESTADO3.NUMERO " + "AND ESTADO3.FECHABAJA IS NULL))))");
        }
        sql.INNER_JOIN("SCS_MAESTROESTADOSEJG MAESTROESTADO ON ESTADO.IDESTADOEJG = MAESTROESTADO.IDESTADOEJG");
        sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS REC ON REC.IDRECURSO = MAESTROESTADO.DESCRIPCION AND REC.IDLENGUAJE = '"
                + idLenguaje + "'");

        sql.WHERE("ejg.idinstitucion = " + idInstitucion);
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
            if (ejgItem.isUltimoEstado()) {
                sql.WHERE("estado.idestadoejg IN (" + ejgItem.getEstadoEJG() + ")");
            } else {

                String[] estados = ejgItem.getEstadoEJG().split(",");
                int maximoEstado;

                if (estados.length > 1) {
                    maximoEstado = Arrays.stream(estados).map(Integer::parseInt).max(Comparator.naturalOrder()).get();
                } else {
                    maximoEstado = Integer.parseInt(estados[0]);
                }

                SQL subQuery = new SQL();
                subQuery.SELECT("IDESTADOEJG");
                subQuery.FROM("SCS_MAESTROESTADOSEJG");
                subQuery.WHERE("FECHA_BAJA IS NULL");
                subQuery.WHERE("IDESTADOEJG >= " + maximoEstado);

                sql.WHERE("estado.idestadoejg IN (" + subQuery.toString() + ")");
            }
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
            sql.WHERE("TO_DATE(ESTADO.FECHAINICIO,'DD/MM/RRRR') >= TO_DATE( '" + fechaEstadoDesd + "','DD/MM/RRRR')");
        }
        if (ejgItem.getFechaEstadoHast() != null) {
            fechaEstadoHast = dateFormat.format(ejgItem.getFechaEstadoHast());
            sql.WHERE("TO_DATE(ESTADO.FECHAINICIO,'DD/MM/RRRR') <= TO_DATE( '" + fechaEstadoHast + "','DD/MM/RRRR')");

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
        if (ejgItem.getFundamentoCalif() != null)// && ejgItem.getFundamentoCalif() != ""
            sql.WHERE("EJG.IDFUNDAMENTOCALIF = " + ejgItem.getFundamentoCalif());
        if (ejgItem.getFechaDictamenDesd() != null) {
            fechaDictamenDesd = dateFormat.format(ejgItem.getFechaDictamenDesd());
            sql.WHERE("TO_DATE(EJG.FECHADICTAMEN,'DD/MM/RRRR') >= TO_DATE( '" + fechaDictamenDesd + "','DD/MM/RRRR')");
        }
        if (ejgItem.getFechaDictamenHast() != null) {
            fechaDictamenHast = dateFormat.format(ejgItem.getFechaDictamenHast());
            sql.WHERE("TO_DATE(EJG.FECHADICTAMEN,'DD/MM/RRRR') <= TO_DATE( '" + fechaDictamenHast + "','DD/MM/RRRR')");
        }
        if (ejgItem.getResolucion() != null)
            sql.WHERE("EJG.IDTIPORATIFICACIONEJG = " + ejgItem.getResolucion());
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
                    if (ejgItem.getIdTurno() != null && ejgItem.getIdTurno() != "")
                        sql.WHERE("EJG.GUARDIATURNO_IDTURNO = " + ejgItem.getIdTurno());
                    if (ejgItem.getIdGuardia() != null && ejgItem.getIdGuardia() != "")
                        sql.WHERE("EJG.GUARDIATURNO_IDGUARDIA " + ejgItem.getIdGuardia());
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
                    if (ejgItem.getIdTurno() != null && ejgItem.getIdTurno() != "")
                        sql.WHERE("DESIGNA.IDTURNO = " + ejgItem.getIdTurno());
                } else if (ejgItem.getTipoLetrado().equals("A")) {
                    // letrado asistencias
                    sql.INNER_JOIN(
                            "SCS_ASISTENCIA ASISTENCIA ON ASISTENCIA.IDINSTITUCION = EJG.idinstitucion and ASISTENCIA.EJGIDTIPOEJG = ejg.idtipoejg and ASISTENCIA.EJGANIO = ejg.anio and ASISTENCIA.ejgnumero = ejg.numero");
                    if (ejgItem.getIdPersona() != null && ejgItem.getIdPersona() != "")
                        sql.WHERE("ASISTENCIA.IDPERSONACOLEGIADO = " + ejgItem.getIdPersona());
                    if (ejgItem.getIdTurno() != null && ejgItem.getIdTurno() != "")
                        sql.WHERE("ASISTENCIA.IDTURNO = " + ejgItem.getIdTurno());
                    if (ejgItem.getIdGuardia() != null && ejgItem.getIdGuardia() != "")
                        sql.WHERE("ASISTENCIA.IDGUARDIA = " + ejgItem.getIdGuardia());
                }
            } else {
                if (ejgItem.getTipoLetrado().equals("E")) {
                    // letrado tramitador
                    if (ejgItem.getIdTurno() != null && ejgItem.getIdTurno() != "")
                        sql.WHERE("EJG.GUARDIATURNO_IDTURNO = " + ejgItem.getIdTurno());
                    if (ejgItem.getIdGuardia() != null && ejgItem.getIdGuardia() != "")
                        sql.WHERE("EJG.GUARDIATURNO_IDGUARDIA = " + ejgItem.getIdGuardia());
                } else if (ejgItem.getTipoLetrado().equals("D")) {
                    // letrado designas
                    sql.INNER_JOIN(
                            "SCS_EJGDESIGNA EJGDESIGNA ON EJGDESIGNA.IDINSTITUCION = EJG.idinstitucion and EJGDESIGNA.idtipoejg = ejg.idtipoejg and EJGDESIGNA.ANIOEJG = ejg.anio and EJGDESIGNA.numeroejg = ejg.numero");
                    if (ejgItem.getIdTurno() != null && ejgItem.getIdTurno() != "")
                        sql.WHERE("EJGDESIGNA.IDTURNO = " + ejgItem.getIdTurno());
                } else if (ejgItem.getTipoLetrado().equals("A")) {
                    // letrado asistencias
                    sql.INNER_JOIN(
                            "SCS_ASISTENCIA ASISTENCIA ON ASISTENCIA.IDINSTITUCION = EJG.idinstitucion and ASISTENCIA.EJGIDTIPOEJG = ejg.idtipoejg and ASISTENCIA.EJGANIO = ejg.anio and ASISTENCIA.ejgnumero = ejg.numero");
                    if (ejgItem.getIdTurno() != null && ejgItem.getIdTurno() != "")
                        sql.WHERE("ASISTENCIA.IDTURNO = " + ejgItem.getIdTurno());
                    if (ejgItem.getIdGuardia() != null && ejgItem.getIdGuardia() != "")
                        sql.WHERE("ASISTENCIA.IDGUARDIA = " + ejgItem.getIdGuardia());
                }
            }
        } else {
            if (ejgItem.getIdPersona() != null && ejgItem.getIdPersona() != "")
                sql.WHERE("PER.IDPERSONA = " + ejgItem.getIdPersona());
            if (ejgItem.getIdTurno() != null && ejgItem.getIdTurno() != "")
                sql.WHERE("EJG.GUARDIATURNO_IDTURNO = " + ejgItem.getIdTurno());
            if (ejgItem.getIdGuardia() != null && ejgItem.getIdGuardia() != "")
                sql.WHERE("EJG.GUARDIATURNO_IDGUARDIA = " + ejgItem.getIdGuardia());
        }

        if (ejgItem.getEstadosSolicitudExpEco() != null && ejgItem.getEstadosSolicitudExpEco().length > 0) {
            sql.INNER_JOIN("SCS_EEJG_PETICIONES PET " +
                    "ON PET.ANIO = ejg.ANIO " +
                    "AND PET.NUMERO = ejg.NUMERO " +
                    "AND PET.IDINSTITUCION = ejg.IDINSTITUCION " +
                    "AND PET.IDTIPOEJG = ejg.IDTIPOEJG");
            sql.WHERE("PET.ESTADO IN (" + String.join(",", ejgItem.getEstadosSolicitudExpEco()) + ")");
        }

        if (tamMaximo != null) {
            Integer tamMaxNumber = tamMaximo + 1;
            sql.WHERE("rownum <= " + tamMaxNumber);

        }

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
        sql.SELECT("EJG.IDPERSONAjg");
        sql.SELECT("ejg.anio");
        sql.SELECT("ejg.idinstitucion");
        sql.SELECT("ejg.idtipoejg");
        sql.SELECT("ejg.idtipoejgcolegio");
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
        sql.SELECT(
                "(CASE WHEN per.nombre is  NULL THEN '' ELSE per.apellidos1 || ' ' || per.apellidos2 || ', ' || per.nombre END) as nombreletrado");
        sql.SELECT("REC.DESCRIPCION AS ESTADOEJG");
        sql.SELECT(
                "(CASE WHEN perjg.nombre is  NULL THEN '' ELSE perjg.apellido1 || ' ' || perjg.apellido2 || ', ' || perjg.nombre END) as NOMBRESOLICITANTE");
        sql.SELECT("perjg.apellido1 || ' ' || perjg.apellido2 as APESOLICITANTE");
        sql.SELECT("perjg.nombre as SOLONOMBRESOLIC");
        sql.SELECT("perjg.NIF");
        sql.SELECT("EJG.NUMEROPROCEDIMIENTO");
        sql.SELECT("rectipodictamen.descripcion AS dictamen");
        sql.SELECT("rectiporesolucion.descripcion AS resolucion");
        sql.SELECT("tiporesolauto.descripcion AS resolauto");
        sql.SELECT(
                "(CASE WHEN personadesigna.nombre is  NULL THEN '' ELSE personadesigna.apellidos1 || ' ' || personadesigna.apellidos2 || ', ' || personadesigna.nombre END) AS nombreletradodesigna");
        sql.SELECT("EXPEDIENTE.anioexpediente AS anioexpediente");
        sql.SELECT("EXPEDIENTE.numeroexpediente AS numeroexpediente");
        sql.SELECT("EXPEDIENTE.IDTIPOEXPEDIENTE AS IDTIPOEXPEDIENTE");
        sql.SELECT("EXPEDIENTE.IDINSTITUCION_TIPOEXPEDIENTE");
        sql.SELECT("ejgd.numerodesigna");
        // Defensa juridica
        sql.SELECT("ejg.idsituacion");
        sql.SELECT("ejg.numerodiligencia");
        sql.SELECT("ejg.comisaria");
        sql.SELECT("ejg.idpreceptivo");
        sql.SELECT("ejg.calidad");
        sql.SELECT("ejg.idrenuncia");
        sql.SELECT("ejg.nig");
        sql.SELECT("ejg.juzgado");
        sql.SELECT("ejg.delitos");
        sql.SELECT("ejg.idpretension");
        // Procurador asociado
        sql.SELECT("ejg.idprocurador");
        sql.SELECT("ejg.idinstitucion_proc");
        sql.SELECT("ejg.fecha_des_proc");
        sql.SELECT("ejg.numerodesignaproc");
        // Tarjeta Dictamen
        sql.SELECT("ejg.idDictamen");
        sql.SELECT("ejg.fechaDictamen");
        sql.SELECT("ejg.idTipoDictamenEJG");
        sql.SELECT("ejg.IDFUNDAMENTOCALIF");
        sql.SELECT("ejg.dictamen as observacionesDictamen");
        // REGTEL
        sql.SELECT("ejg.identificadords");
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
        // Impugnacion
        sql.LEFT_OUTER_JOIN("scs_tiporesolauto tiporesolauto ON tiporesolauto.idtiporesolauto = ejg.idtiporesolauto");
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
//		if (ejgItem.getNumEjg() != null && ejgItem.getNumEjg() != "")
//			sql.WHERE("EJG.NUMEJG =" + ejgItem.getNumEjg());
//				sql.WHERE ("EJG.NUMEJG ='09039'");
        if (ejgItem.getNumero() != null && ejgItem.getNumero() != "")
            sql.WHERE("EJG.NUMERO =" + ejgItem.getNumero());

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

    /**
     * @param asuntosJusticiableItem
     * @param tamMax
     * @param idLenguaje
     * @return
     */
    public String searchClaveAsuntosEJG(AsuntosJusticiableItem asuntosJusticiableItem, Integer tamMax,
                                        String idLenguaje) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fecha;

        SQL sql = new SQL();

        sql.SELECT("ejg.idinstitucion, ejg.anio, ejg.numero, ( 'E' || ejg.anio || '/' || ejg.numejg) asunto,"
                + "(nvl(t.abreviatura,'') || '/' || nvl(g.nombre,'')) turnoguardia,ejg.idtipoejg,"
                + "f_siga_getrecurso(nvl(te.descripcion,'')," + idLenguaje + ") tipoejg,"
                + "(nvl(pjg.nombre,'') || ' ' || nvl(pjg.apellido1,'') || ' ' || nvl(pjg.apellido2,'')) interesado,"
                + "(nvl(per.nombre,'') || ' ' || nvl(per.apellidos1, '') || ' ' || nvl(per.apellidos2,'')) letrado,"
                + "ejg.fechaapertura, nvl(ejg.numeroprocedimiento,'') numeroprocedimiento, nvl(ejg.nig,'') nig,"
                + "nvl( ejg.numerodiligencia, 'Sin número' ) || ' / ' || nvl( ejg.numeroprocedimiento,'Sin número' ) dilnigproc");

        sql.FROM("scs_ejg ejg");

        sql.JOIN("scs_tipoejg te ON (te.idtipoejg = ejg.idtipoejg)");
        sql.LEFT_OUTER_JOIN(
                "scs_personajg pjg ON (pjg.idpersona = ejg.idpersonajg AND pjg.idinstitucion = ejg.idinstitucion)");
        sql.LEFT_OUTER_JOIN(
                "scs_turno t ON (t.idturno = ejg.guardiaturno_idturno AND t.idinstitucion = ejg.idinstitucion)");
        sql.LEFT_OUTER_JOIN(
                "scs_guardiasturno g ON (ejg.guardiaturno_idturno = g.idturno AND ejg.idinstitucion = g.idinstitucion "
                        + "AND ejg.guardiaturno_idguardia = g.idguardia)");
        sql.LEFT_OUTER_JOIN("cen_persona per ON (ejg.idpersona = per.idpersona)");

        sql.WHERE("ejg.idinstitucion = " + asuntosJusticiableItem.getIdInstitucion());

        if (asuntosJusticiableItem.getAnio() != null && !asuntosJusticiableItem.getAnio().trim().isEmpty()) {
            sql.WHERE("ejg.anio = " + asuntosJusticiableItem.getAnio().trim());
        }

        if (asuntosJusticiableItem.getNumero() != null && !asuntosJusticiableItem.getNumero().trim().isEmpty()) {
            sql.WHERE("ejg.numejg = " + asuntosJusticiableItem.getNumero().trim());
        }

        if (asuntosJusticiableItem.getIdTipoEjg() != null && !asuntosJusticiableItem.getIdTipoEjg().trim().isEmpty()) {
            sql.WHERE("ejg.idtipoejg = " + asuntosJusticiableItem.getIdTipoEjg().trim());
        }

        if (asuntosJusticiableItem.getFechaAperturaDesde() != null) {
            fecha = dateFormat.format(asuntosJusticiableItem.getFechaAperturaDesde());
            sql.WHERE("ejg.fechaapertura >= TO_DATE('" + fecha + "','DD/MM/RRRR')");
        }

        if (asuntosJusticiableItem.getFechaAperturaHasta() != null) {
            fecha = dateFormat.format(asuntosJusticiableItem.getFechaAperturaHasta());
            sql.WHERE("ejg.fechaapertura <= TO_DATE('" + fecha + "','DD/MM/RRRR')");
        }

        if (asuntosJusticiableItem.getNif() != null && !asuntosJusticiableItem.getNif().trim().isEmpty()) {
            sql.WHERE("pjg.nif = '%" + asuntosJusticiableItem.getNif().trim() + "%'");
        }

        if (asuntosJusticiableItem.getNombre() != null && !asuntosJusticiableItem.getNombre().trim().isEmpty()) {
            sql.WHERE("pjg.nombre LIKE upper('%" + asuntosJusticiableItem.getNombre().trim() + "%')");
        }

        if (asuntosJusticiableItem.getApellidos() != null && !asuntosJusticiableItem.getApellidos().trim().isEmpty()) {
            sql.WHERE("(pjg.apellido1 || ' ' || pjg.apellido2) LIKE upper('%"
                    + asuntosJusticiableItem.getApellidos().trim() + "%')");
        }

        if (asuntosJusticiableItem.getIdTurno() != null) {
            sql.WHERE("ejg.guardiaturno_idturno = " + asuntosJusticiableItem.getIdTurno());
        }
        if (asuntosJusticiableItem.getIdGuardia() != null) {
            sql.WHERE("ejg.guardiaturno_idguardia = " + asuntosJusticiableItem.getIdGuardia());
        }
        if (asuntosJusticiableItem.getIdPersonaColegiado() != null) {
            sql.WHERE("ejg.idpersona = " + asuntosJusticiableItem.getIdPersonaColegiado());
        }

        sql.WHERE("ROWNUM <= " + tamMax);

        sql.ORDER_BY("ejg.anio desc, ejg.numero DESC");

        /*
         * SQL sqlOrder = new SQL();
         *
         * sqlOrder.SELECT("*"); sql.
         * SELECT("EJG.idinstitucion, EJG.anio,EJG.numero,to_char(EJG.IDTIPOEJG) AS clave, '' as rol, 'E' as tipo"
         * ); sql.FROM("SCS_EJG EJG"); sql.INNER_JOIN(
         * "SCS_PERSONAJG PERSONA ON (EJG.IDPERSONAJG = PERSONA.IDPERSONA AND PERSONA.IDINSTITUCION = EJG.IDINSTITUCION)"
         * ); sql.INNER_JOIN(
         * "SCS_ESTADOEJG ESTADO ON (ESTADO.IDINSTITUCION = EJG.IDINSTITUCION AND ESTADO.IDTIPOEJG = EJG.IDTIPOEJG AND ESTADO.ANIO = "
         * +
         * "EJG.ANIO AND ESTADO.NUMERO = EJG.NUMERO AND ESTADO.FECHABAJA IS NULL AND ESTADO.FECHAINICIO = (SELECT MAX(FECHAINICIO) FROM SCS_ESTADOEJG ESTADO2 WHERE (ESTADO.IDINSTITUCION = ESTADO2.IDINSTITUCION"
         * +
         * " AND ESTADO.IDTIPOEJG = ESTADO2.IDTIPOEJG AND ESTADO.ANIO = ESTADO2.ANIO AND ESTADO.NUMERO = ESTADO2.NUMERO AND ESTADO2.FECHABAJA IS NULL)))"
         * ); sql.WHERE("EJG.idinstitucion = " +
         * asuntosJusticiableItem.getIdInstitucion());
         *
         * if (asuntosJusticiableItem.getAnio() != null &&
         * asuntosJusticiableItem.getAnio() != "") { sql.WHERE("EJG.ANIO = " +
         * asuntosJusticiableItem.getAnio()); } if (asuntosJusticiableItem.getNumero()
         * != null) { sql.WHERE("EJG.NUMERO = " + asuntosJusticiableItem.getNumero()); }
         *
         * if (asuntosJusticiableItem.getFechaAperturaDesde() != null) {
         * SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); String
         * fecha = dateFormat.format(asuntosJusticiableItem.getFechaAperturaDesde());
         * sql.WHERE("TO_CHAR(EJG.FECHAAPERTURA,'DD/MM/RRRR') >= TO_DATE('" + fecha +
         * "','DD/MM/RRRR')"); } if (asuntosJusticiableItem.getFechaAperturaHasta() !=
         * null) { SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
         * String fecha =
         * dateFormat.format(asuntosJusticiableItem.getFechaAperturaHasta());
         * sql.WHERE("TO_CHAR(EJG.FECHAAPERTURA,'DD/MM/RRRR') <= TO_DATE('" + fecha +
         * "','DD/MM/RRRR')"); }
         *
         * if (asuntosJusticiableItem.getApellidos() != null &&
         * asuntosJusticiableItem.getApellidos() != "") sql.WHERE(UtilidadesString.
         * filtroTextoBusquedas("REPLACE(CONCAT(apellido1,apellido2), ' ', '')",
         * asuntosJusticiableItem.getApellidos().replaceAll("\\s+", ""))); if
         * (asuntosJusticiableItem.getNombre() != null &&
         * asuntosJusticiableItem.getNombre() != "")
         * sql.WHERE(UtilidadesString.filtroTextoBusquedas("NOMBRE",
         * asuntosJusticiableItem.getNombre()));
         *
         * if (asuntosJusticiableItem.getIdPersonaColegiado() != null) {
         * sql.WHERE("EJG.IDPERSONA = " +
         * asuntosJusticiableItem.getIdPersonaColegiado()); } if
         * (asuntosJusticiableItem.getNif() != null && asuntosJusticiableItem.getNif()
         * != "") { sql.WHERE("upper(PERSONA.NIF) like upper('%" +
         * asuntosJusticiableItem.getNif() + "%')"); } if
         * (asuntosJusticiableItem.getIdTipoEjg() != null) {
         * sql.WHERE("EJG.IDTIPOEJG = " + asuntosJusticiableItem.getIdTipoEjg()); } if
         * (asuntosJusticiableItem.getIdTipoEjColegio() != null) {
         * sql.WHERE("EJG.IDTIPOEJGCOLEGIO = " +
         * asuntosJusticiableItem.getIdTipoEjColegio()); } if
         * (asuntosJusticiableItem.getIdEstadoPorEjg() != null) {
         * sql.WHERE("ESTADO.IDESTADOPOREJG = " +
         * asuntosJusticiableItem.getIdEstadoPorEjg()); } sqlOrder.FROM("(" + sql +
         * " )"); if (tamMax != null) { Integer tamMaxNumber = tamMax + 1;
         * sqlOrder.WHERE("rownum <= " + tamMaxNumber); }
         */

        return sql.toString();
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

        sql.SELECT("resolucion.idtiporatificacionejg," + " resolucion.idfundamentojuridico,"
                + " resolucion.ratificaciondictamen," + " ejg.idorigencajg," + " ejg.aniocajg," + " ejg.numero_cajg,"
                + " ejg.idponente," + " ejg.fechapresentacionponente," + " ejg.fecharesolucioncajg,"
                + " ejg.fecharatificacion," + " ejg.fechanotificacion," + " ejg.refauto," + " ejg.turnadoratificacion,"
                + " ejg.requierenotificarproc," + " ejg.anioacta," + " ejg.idacta,"
//				+ " ejg.idinstitucion||','||ejg.idacta||','||ejg.anioacta as idannioacta,"
                + " (CASE WHEN ejg.idacta is  NULL THEN null ELSE ejg.idacta||','||ejg.anioacta END) as idannioacta,"
                + " resolucion.notascajg AS notascajg, ejg.docresolucion");

        sql.FROM("scs_ejg ejg");
        sql.LEFT_OUTER_JOIN(
                "scs_ejg_resolucion resolucion on (resolucion.idinstitucion = ejg.idinstitucion AND resolucion.idtipoejg = ejg.idtipoejg AND resolucion.anio = ejg.anio AND resolucion.numero = ejg.numero)");

        sql.WHERE("ejg.idinstitucion = " + idInstitucion);
        sql.WHERE("ejg.anio =" + ejgItem.getAnnio());
        sql.WHERE("ejg.numero =" + ejgItem.getNumero());
        sql.WHERE("ejg.idtipoejg = " + ejgItem.getTipoEJG());

        return sql.toString();
    }

    public String busquedaColegiadoEJG(ColegiadosSJCSItem item, String idLenguaje) {
        SQL sql = new SQL();
        SQL sql2 = new SQL();
        SQL sql3 = new SQL();

        // SUBQUERY PARA EL INNER JOIN
        sql2.SELECT("MAX(COL2.FECHAESTADO)");
        sql2.FROM("CEN_DATOSCOLEGIALESESTADO COL2");
        sql2.WHERE("COL2.IDPERSONA=PER.IDPERSONA AND COL2.IDINSTITUCION = COL.IDINSTITUCION");

        // QUERY PRINCIPAL
        sql.SELECT(
                "PER.NIFCIF NIF,PER.IDPERSONA, PER.NOMBRE NOMBRE, CONCAT(CONCAT(PER.APELLIDOS1, ' '), PER.APELLIDOS2) APELLIDOS, NVL(col.ncolegiado, col.ncomunitario) NCOLEGIADO, "
                        + "COL.NCOMUNITARIO NCOMUNITARIO, ESTADO.IDESTADO IDESTADO,COL.IDINSTITUCION IDINSTITUCION, INS.ABREVIATURA ABREVIATURA, "
                        + "F_SIGA_GETRECURSO(TIPOESTADO.DESCRIPCION, " + idLenguaje
                        + ") ESTADO, COL.SITUACIONRESIDENTE RESIDENTE,\r\n"
                        + "            COUNT(tur.idturno) sumaturnos,\r\n"
                        + "            COUNT(guar.idguardia) tieneguardias,\r\n"
                        + "            SUM(nvl(guarpend.pendiente, 0)) AS guardiaspendientes");

        sql.FROM("CEN_PERSONA PER");

        sql.INNER_JOIN("CEN_COLEGIADO COL ON (COL.IDPERSONA = PER.IDPERSONA)");
        sql.INNER_JOIN(
                "CEN_DATOSCOLEGIALESESTADO ESTADO ON (PER.IDPERSONA = ESTADO.IDPERSONA AND ESTADO.IDINSTITUCION = COL.IDINSTITUCION )");
        if ((item.getIdGuardia() != null && item.getIdGuardia().length > 0)
                || (item.getIdTurno() != null && item.getIdTurno().length > 0)) {
            sql.INNER_JOIN(
                    "SCS_GUARDIASCOLEGIADO GUARDIAS ON (PER.IDPERSONA = GUARDIAS.IDPERSONA AND COL.IDINSTITUCION = GUARDIAS.IDINSTITUCION)");
        }

        sql.INNER_JOIN("CEN_ESTADOCOLEGIAL TIPOESTADO ON (TIPOESTADO.IDESTADO=ESTADO.IDESTADO)");
        sql.INNER_JOIN("CEN_INSTITUCION INS ON (INS.IDINSTITUCION=COL.IDINSTITUCION)");
        sql.LEFT_OUTER_JOIN("scs_inscripcionturno        tur ON ( tur.idinstitucion = col.idinstitucion\r\n"
                + "                                                          AND tur.idpersona = col.idpersona )\r\n");
        sql.LEFT_OUTER_JOIN("scs_inscripcionguardia      guar ON ( guar.idinstitucion = tur.idinstitucion\r\n"
                + "                                                             AND guar.idpersona = tur.idpersona\r\n"
                + "                                                             AND guar.idturno = tur.idturno )\r\n");
        sql.LEFT_OUTER_JOIN(
                " (SELECT\r\n" + "                    guardiain.*,\r\n" + "                    1 AS pendiente\r\n"
                        + "                FROM\r\n" + "                    scs_inscripcionguardia guardiain\r\n"
                        + "                WHERE\r\n" + "                    ( fechavalidacion IS NULL )\r\n"
                        + "            ) guarpend ON ( guarpend.idinstitucion = guar.idinstitucion\r\n"
                        + "                            AND guarpend.idpersona = guar.idpersona\r\n"
                        + "                            AND guarpend.idturno = guar.idturno\r\n"
                        + "                            AND guarpend.idguardia = guar.idguardia )");

        // CONDICIONES WHERE
        if (item.getIdInstitucion() != null && !item.getIdInstitucion().isEmpty()) {
            sql.WHERE("COL.IDINSTITUCION = " + item.getIdInstitucion());
        }

        if (item.getNombre() != null && !item.getNombre().trim().isEmpty()) {
            sql.WHERE("UPPER(PER.NOMBRE) LIKE UPPER('%" + item.getNombre().trim() + "%')");
        }

        if (item.getApellidos() != null && !item.getApellidos().trim().isEmpty()) {
            sql.WHERE("CONCAT(CONCAT(UPPER(PER.APELLIDOS1), ' '), UPPER(PER.APELLIDOS2)) LIKE UPPER('%"
                    + item.getApellidos().trim() + "%')");
        }

        if (item.getIdEstado() != null && !item.getIdEstado().isEmpty()) {
            sql.WHERE("ESTADO.IDESTADO = " + item.getIdEstado());
        }

        sql.WHERE(" ESTADO.FECHAESTADO = (" + sql2.toString() + ")");

        if (item.getnColegiado() != null && !item.getnColegiado().trim().isEmpty()) {
            sql.WHERE("(COL.NCOLEGIADO = " + item.getnColegiado().trim() + " OR COL.NCOMUNITARIO = "
                    + item.getnColegiado().trim() + ")");
        }

        if (item.getIdTurno() != null && item.getIdTurno().length > 0) {
            String inSQL = item.getIdTurno()[0];
            for (int i = 1; i < item.getIdTurno().length; i++) {
                inSQL += ", " + item.getIdTurno()[i];
            }
            sql.WHERE("GUARDIAS.IDTURNO IN  (" + inSQL + ")");
        }

        if (item.getIdGuardia() != null && item.getIdGuardia().length > 0) {
            String inSQL = item.getIdGuardia()[0];
            for (int i = 1; i < item.getIdGuardia().length; i++) {
                inSQL += ", " + item.getIdGuardia()[i];
            }
            sql.WHERE("GUARDIAS.IDGUARDIA IN (" + inSQL + ")");
        }

        if (item.getNif() != null && !item.getNif().isEmpty()) {
            sql.WHERE("PER.NIFCIF = '" + item.getNif() + "'");
        }

        sql.GROUP_BY(
                "PER.NIFCIF, PER.IDPERSONA, PER.NOMBRE, CONCAT(CONCAT(PER.APELLIDOS1, ' '), PER.APELLIDOS2), COL.NCOLEGIADO, "
                        + "COL.NCOMUNITARIO, COL.IDINSTITUCION, INS.ABREVIATURA, ESTADO.IDESTADO, TIPOESTADO.DESCRIPCION, COL.SITUACIONRESIDENTE");
        sql.ORDER_BY("PER.NOMBRE, CONCAT(CONCAT(PER.APELLIDOS1, ' '), PER.APELLIDOS2)");

        // Se realiza esta consulta para poder aplicar el filtro del número máximo de
        // registros
        sql3.SELECT("nif,\r\n" + "    idpersona,\r\n" + "    nombre,\r\n" + "    apellidos,\r\n" + "    ncolegiado,\r\n"
                + "    ncomunitario,\r\n" + "    idestado,\r\n" + "    idinstitucion,\r\n" + "    abreviatura,\r\n"
                + "    estado,\r\n" + "    residente,\r\n" + "    tieneguardias,\r\n" + "    guardiaspendientes,\r\n"
                + "    CASE sumaturnos\r\n" + "        WHEN 0 THEN\r\n" + "            'No'\r\n" + "        ELSE\r\n"
                + "            'Sí'\r\n" + "    END AS tieneturno FROM ( " + sql.toString() + ")");

//		if (tamMaximo != null) {
//			Integer tamMaxNumber = tamMaximo + 1;
//			sql3.WHERE("rownum <= " + tamMaxNumber);
//
//		}

        return sql3.toString();
    }

    public String tieneTurnos(String idInstitucion, String idPersona) {
        SQL sql = new SQL();

        // Busca si tiene algun turno
        sql.SELECT("IDPERSONA, IDTURNO");
        sql.FROM("SCS_INSCRIPCIONTURNO");
        sql.WHERE("IDINSTITUCION = " + idInstitucion);
        sql.WHERE("IDPERSONA = " + idPersona);

        return sql.toString();
    }

    public String tieneGuardias(String idInstitucion, ColegiadosSJCSItem tieneTurno) {
        SQL sql = new SQL();

        // Busca si tiene alguna guardia
        sql.SELECT("IDGUARDIA");
        sql.FROM("SCS_INSCRIPCIONGUARDIA");
        sql.WHERE("IDINSTITUCION = " + idInstitucion);
        sql.WHERE("IDPERSONA = " + tieneTurno.getIdPersona());
        sql.WHERE("IDTURNO = " + tieneTurno.getTieneTurno());

        return sql.toString();
    }

    public String tieneGuardiasPendientes(String idInstitucion, ColegiadosSJCSItem tieneTurno, String idGuardia) {
        SQL sql = new SQL();

        // Busca si tiene alguna guardia
        sql.SELECT("DISTINCT IDGUARDIA");
        sql.FROM("SCS_INSCRIPCIONGUARDIA");
        sql.WHERE("IDINSTITUCION = " + idInstitucion);
        sql.WHERE("IDPERSONA = " + tieneTurno.getIdPersona());
        sql.WHERE("IDTURNO = " + tieneTurno.getTieneTurno());
        sql.WHERE("IDGUARDIA = " + idGuardia);
        sql.WHERE("FECHAVALIDACION IS NULL");

        return sql.toString();
    }

    public String getNumero(short idTipoEJG, short anio, short idInstitucion) {
        SQL sql = new SQL();

        sql.SELECT("NVL(MAX(NUMERO) +1 ,1)");
        sql.FROM("SCS_EJG");
        sql.WHERE("IDINSTITUCION = " + idInstitucion);
        sql.WHERE("ANIO = " + anio);
        // sql.WHERE("IDTIPOEJG = " + idTipoEJG);

        return sql.toString();
    }

    public String getNumeroEJG(short idTipoEJG, short anio, short idInstitucion) {
        SQL sql = new SQL();

        sql.SELECT("NVL(MAX(NUMEJG)+1 ,1) ");
        sql.FROM("SCS_EJG");
        sql.WHERE("IDINSTITUCION = " + idInstitucion);
        sql.WHERE("ANIO = " + anio);
        // sql.WHERE("IDTIPOEJG = " + idTipoEJG);

        return sql.toString();
    }

    /**
     * @param num
     * @param anio
     * @param idturno
     * @param idInstitucion
     * @param idLenguaje
     * @return
     */
    public String getComunicaciones(String num, String anio, String idTipo, Short idInstitucion, String idLenguaje) {
        SQL sql = new SQL();
        SQL sql2 = new SQL();
        SQL sql3 = new SQL();
        SQL sqlTipoEnvio = new SQL();
        SQL sqlEstadosEnvio = new SQL();

        // subquery tipo envio
        sqlTipoEnvio.SELECT("cat.descripcion");
        sqlTipoEnvio.FROM("env_tipoenvios");
        sqlTipoEnvio.LEFT_OUTER_JOIN("gen_recursos_catalogos cat ON (cat.idrecurso = env_tipoenvios.nombre)");
        sqlTipoEnvio.WHERE("env_tipoenvios.idtipoenvios = e.idtipoenvios");
        sqlTipoEnvio.WHERE("cat.idlenguaje = '" + idLenguaje + "'");

        // subquery estadosEnvios
        sqlEstadosEnvio.SELECT("cat.descripcion");
        sqlEstadosEnvio.FROM("env_estadoenvio estado");
        sqlEstadosEnvio.LEFT_OUTER_JOIN("gen_recursos_catalogos cat ON (cat.idrecurso = estado.nombre)");
        sqlEstadosEnvio.WHERE("estado.idestado = e.idestado");
        sqlEstadosEnvio.WHERE("cat.idlenguaje = '" + idLenguaje + "'");

        sql3.SELECT("c.idenviosalida");
        sql3.SELECT("c.idinstitucion");
        sql3.FROM("scs_comunicaciones c");
        sql3.WHERE("c.idinstitucion = '" + idInstitucion + "'");
        sql3.WHERE("c.ejganio = " + anio);
        sql3.WHERE("c.ejgidtipo = " + idTipo);
        sql3.WHERE("c.ejgnumero = '" + num + "'");

        sql2.SELECT("e.*");
        sql2.SELECT("(dest.nombre || ' ' || dest.apellidos1 || ' ' || dest.apellidos2) AS destinatario");
        sql2.SELECT("(" + sqlTipoEnvio.toString() + ") as tipoenvio");
        sql2.SELECT("(" + sqlEstadosEnvio.toString() + ") as estadoenvio");
        sql2.SELECT("nvl(camposenviosasunto.valor, plantilla.asunto) AS asunto");
        sql2.SELECT("nvl(camposenvioscuerpo.valor, plantilla.cuerpo) AS cuerpo");
        sql2.FROM("env_envios e");
        sql2.LEFT_OUTER_JOIN(
                "env_destinatarios dest on (dest.idenvio=e.idenvio and dest.idinstitucion =e.idinstitucion)");
        sql2.LEFT_OUTER_JOIN("env_plantillasenvios plantilla ON (plantilla.idinstitucion = '" + idInstitucion
                + "' AND plantilla.idplantillaenvios = e.idplantillaenvios"
                + " AND plantilla.idtipoenvios = e.idtipoenvios)");
        sql2.LEFT_OUTER_JOIN(
                "env_camposenvios camposenviosasunto ON (e.idenvio = camposenviosasunto.idenvio AND camposenviosasunto.idinstitucion = e.idinstitucion"
                        + " AND camposenviosasunto.idcampo = 1)");
        sql2.LEFT_OUTER_JOIN(
                "env_camposenvios camposenvioscuerpo ON (e.idenvio = camposenvioscuerpo.idenvio AND camposenvioscuerpo.idinstitucion = e.idinstitucion"
                        + " AND camposenvioscuerpo.idcampo = 2)");

        sql2.WHERE("e.fechabaja IS NULL");
        sql2.WHERE("(e.idenvio,e.idinstitucion) IN (" + sql3.toString() + ")");

        sql.SELECT("*");
        sql.FROM("(" + sql2.toString() + ")");

        return sql.toString();
    }

    public String getRelacionesEJG(EjgItem item) {
        SQL sqlPrincipal = new SQL();

        SQL sqlAsistencia = new SQL();
        SQL sqlAsistencia_1 = new SQL();
        SQL sqlAsistencia_2 = new SQL();
        SQL sqlAsistencia_3 = new SQL();
        SQL sqlAsistencia_4 = new SQL();

        SQL sqlSOJ = new SQL();
        SQL sqlSOJ_1 = new SQL();
        SQL sqlSOJ_2 = new SQL();
        SQL sqlSOJ_3 = new SQL();
        SQL sqlSOJ_4 = new SQL();

        SQL sqlDesigna = new SQL();
        SQL sqlDesigna_1 = new SQL();
        SQL sqlDesigna_2 = new SQL();
        SQL sqlDesigna_3 = new SQL();
        SQL sqlDesigna_4 = new SQL();
        SQL sqlDesigna_5 = new SQL();
        SQL sqlDesigna_6 = new SQL();
        SQL sqlDesigna_7 = new SQL();

        SQL sqlExpediente = new SQL();

        // consulta para obtener letrado en consulta de asistencia
        sqlAsistencia_1.SELECT("cen_persona.apellidos2  || ' '|| cen_persona.apellidos1 || ',' || cen_persona.nombre");
        sqlAsistencia_1.FROM(
                "scs_asistencia " + "JOIN cen_persona ON cen_persona.idpersona = scs_asistencia.idpersonacolegiado");
        sqlAsistencia_1.WHERE("ejganio =" + item.getAnnio());
        sqlAsistencia_1.WHERE("ejgnumero =" + item.getNumero());
        sqlAsistencia_1.WHERE("ejgidtipoejg =" + item.getTipoEJG());
        sqlAsistencia_1.WHERE("idinstitucion =" + item.getidInstitucion());

        // consulta para obtener la des_turno en consulta asistencia
        sqlAsistencia_2.SELECT("abreviatura");
        sqlAsistencia_2.FROM("scs_turno");
        sqlAsistencia_2.WHERE("idturno = scs_asistencia.idturno");
        sqlAsistencia_2.WHERE("idinstitucion = scs_asistencia.idinstitucion");

        // consulta para obtener la des_tipo en consulta asistencia
        sqlAsistencia_3.SELECT("f_siga_getrecurso( s.descripcion, 1)");
        sqlAsistencia_3.FROM("scs_tipoasistenciacolegio s");
        sqlAsistencia_3.WHERE("scs_asistencia.idinstitucion = s.idinstitucion");
        sqlAsistencia_3.WHERE("scs_asistencia.idtipoasistenciacolegio = s.idtipoasistenciacolegio");

        // consulta para obtener el interesado en consulta asistencia
        sqlAsistencia_4
                .SELECT("scs_personajg.apellido1" + " || CASE WHEN scs_personajg.apellido2 IS NOT NULL THEN ' '  "
                        + "|| scs_personajg.apellido2" + "|| ',' ELSE ',' END || scs_personajg.nombre");
        sqlAsistencia_4.FROM("scs_asistencia"
                + "                    JOIN scs_personajg ON scs_personajg.idpersona = scs_asistencia.idpersonajg"
                + "                    AND scs_personajg.idinstitucion = scs_asistencia.idinstitucion");
        sqlAsistencia_4.WHERE("scs_asistencia.idinstitucion = " + item.getidInstitucion());
        sqlAsistencia_4.WHERE("scs_asistencia.anio = " + item.getAnnio());
        sqlAsistencia_4.WHERE("scs_asistencia.numero =" + item.getNumero());

        // consulta para obtener la relacion de asistencias con los datos de las
        // consultas anteriores.
        sqlAsistencia.SELECT("TRIM('ASISTENCIA') sjcs," + "			TO_CHAR('A'||anio||'/'||numero) idsjcs,"
                + "			fechahora fecha," + "            idinstitucion idinstitucion,"
                + "            anio anio,\r\n" + "            numero numero,\r\n"
                + "            idpersonacolegiado idletrado," + "				TO_CHAR(idturno) idturno,"
                + "            TO_CHAR(idtipoasistencia) idtipo," + "            TO_CHAR(numero) codigo," + "("
                + sqlAsistencia_2.toString() + ") des_turno," + "(" + sqlAsistencia_3.toString() + ") des_tipo," + "("
                + sqlAsistencia_1.toString() + ") nombreletrado," + "(" + sqlAsistencia_4.toString() + ") interesado,"
                + "nvl( numerodiligencia, 'Sin número' ) || ' / ' || nvl( numeroprocedimiento,'Sin número' ) dilnigproc,"
                + "NULL resolucion");
        sqlAsistencia.SELECT("NULL IDPERSONAJG");

        sqlAsistencia.FROM("scs_asistencia");
        sqlAsistencia.WHERE("ejganio = " + item.getAnnio());
        sqlAsistencia.WHERE("ejgnumero = " + item.getNumero());
        sqlAsistencia.WHERE("ejgidtipoejg =  " + item.getTipoEJG());
        sqlAsistencia.WHERE("idinstitucion =" + item.getidInstitucion());

        // consulta para obtener letrado en consulta de SOJ
        sqlSOJ_1.SELECT(
                "cen_persona.apellidos2\r\n" + "             || ' '\r\n" + "             || cen_persona.apellidos1\r\n"
                        + "             || ','\r\n" + "             || cen_persona.nombre ");
        sqlSOJ_1.FROM("scs_soj " + "JOIN cen_persona ON cen_persona.idpersona = scs_soj.idpersona");
        sqlSOJ_1.WHERE("ejganio =" + item.getAnnio());
        sqlSOJ_1.WHERE("ejgnumero =" + item.getNumero());
        sqlSOJ_1.WHERE("ejgidtipoejg =" + item.getTipoEJG());
        sqlSOJ_1.WHERE("idinstitucion =" + item.getidInstitucion());

        // consulta para obtener la des_turno en consulta SOJ
        sqlSOJ_2.SELECT("abreviatura");
        sqlSOJ_2.FROM("scs_turno");
        sqlSOJ_2.WHERE("idturno = scs_soj.idturno");
        sqlSOJ_2.WHERE("idinstitucion = scs_soj.idinstitucion");

        // consulta para obtener la des_tipo en consulta SOJ
        sqlSOJ_3.SELECT(" f_siga_getrecurso( descripcion, 1) AS descripcion");
        sqlSOJ_3.FROM("scs_tiposoj");
        sqlSOJ_3.WHERE("scs_tiposoj.idtiposoj = scs_soj.idtiposoj");

        // consulta para obtener el interesado en consulta SOJ
        sqlSOJ_4.SELECT("scs_personajg.apellido1\r\n" + "                     || CASE\r\n"
                + "                            WHEN scs_personajg.apellido2 IS NOT NULL THEN ' '\r\n"
                + "                             || scs_personajg.apellido2\r\n"
                + "                             || ','\r\n" + "                            ELSE ','\r\n"
                + "                        END\r\n" + "                     || scs_personajg.nombre");
        sqlSOJ_4.FROM("scs_soj\r\n" + "                JOIN scs_personajg ON\r\n"
                + "                        scs_personajg.idpersona = scs_soj.idpersona\r\n"
                + "                    AND\r\n"
                + "                        scs_personajg.idinstitucion = scs_soj.idinstitucion");
        sqlSOJ_4.WHERE("scs_soj.idinstitucion = " + item.getidInstitucion());
        sqlSOJ_4.WHERE("scs_soj.anio = " + item.getAnnio());
        sqlSOJ_4.WHERE("scs_soj.numero =" + item.getNumero());

        // consulta para obtener la relacion de SOJ con los datos de las consultas
        // anteriores.
        sqlSOJ.SELECT("TRIM('SOJ') sjcs," + "TO_CHAR('S'||anio||'/'||numero) idsjcs," + "fechaapertura fecha,"
                + "            idinstitucion idinstitucion," + "            anio anio," + "            numero numero,"
                + "            scs_soj.idpersona idletrado," + "TO_CHAR(idturno) idturno,"
                + "TO_CHAR(idtiposoj) idtipo," + " numsoj codigo," + "(" + sqlSOJ_2.toString() + ") des_turno," + "("
                + sqlSOJ_3.toString() + ") des_tipo," + "(" + sqlSOJ_1.toString() + ") nombreletrado,"

                + "(" + sqlSOJ_4.toString() + ") interesado," + "NULL dilnigproc," + "NULL resolucion");

        sqlSOJ.SELECT("scs_soj.IDPERSONAJG");

        sqlSOJ.FROM("scs_soj");
        sqlSOJ.WHERE("ejganio = " + item.getAnnio());
        sqlSOJ.WHERE("ejgnumero = " + item.getNumero());
        sqlSOJ.WHERE("ejgidtipoejg =  " + item.getTipoEJG());
        sqlSOJ.WHERE("idinstitucion =" + item.getidInstitucion());

        // consulta para obtener letrado en consulta de DESIGNA
        sqlDesigna_1.SELECT(
                "cen_persona.apellidos2\r\n" + "             || ' '\r\n" + "             || cen_persona.apellidos1\r\n"
                        + "             || ','\r\n" + "             || cen_persona.nombre ");
        sqlDesigna_1.FROM("scs_ejg ejg\r\n" + "             JOIN cen_persona ON cen_persona.idpersona = ejg.idpersona");
        sqlDesigna_1.WHERE("ejg.anio =" + item.getAnnio());
        sqlDesigna_1.WHERE("ejg.numero =" + item.getNumero());
        sqlDesigna_1.WHERE("ejg.idtipoejg =" + item.getTipoEJG());
        sqlDesigna_1.WHERE("ejg.idinstitucion =" + item.getidInstitucion());

        // consulta para obtener el idtipo en consulta DESIGNA
        sqlDesigna_2.SELECT("TO_CHAR(idtipodesignacolegio)");
        sqlDesigna_2.FROM("scs_designa");
        sqlDesigna_2.WHERE("anio = ejgd.aniodesigna");
        sqlDesigna_2.WHERE("numero = ejgd.numerodesigna");
        sqlDesigna_2.WHERE("idturno = ejgd.idturno");
        sqlDesigna_2.WHERE("idinstitucion = ejgd.idinstitucion");

        // consulta para obtener el codigo en consulta Designa
        sqlDesigna_3.SELECT(" codigo");
        sqlDesigna_3.FROM("scs_designa");
        sqlDesigna_3.WHERE("anio = ejgd.aniodesigna");
        sqlDesigna_3.WHERE("numero = ejgd.numerodesigna");
        sqlDesigna_3.WHERE("idturno = ejgd.idturno");
        sqlDesigna_3.WHERE("idinstitucion = ejgd.idinstitucion");

        // consulta para obtener el des_turno en consulta Designa
        sqlDesigna_4.SELECT("abreviatura");
        sqlDesigna_4.FROM("scs_turno");
        sqlDesigna_4.WHERE("idturno = ejgd.idturno ");
        sqlDesigna_4.WHERE("idinstitucion = ejg.idinstitucion ");

        // consulta para obtener el des_tipo en consulta Designa
        sqlDesigna_5.SELECT("f_siga_getrecurso(descripcion, 1)");
        sqlDesigna_5.FROM(" scs_designa a, scs_tipodesignacolegio b");
        sqlDesigna_5.WHERE("a.anio = ejgd.aniodesigna ");
        sqlDesigna_5.WHERE("a.numero = ejgd.numerodesigna ");
        sqlDesigna_5.WHERE("a.idturno = ejgd.idturno");
        sqlDesigna_5.WHERE("a.idinstitucion =  " + item.getidInstitucion());
        sqlDesigna_5.WHERE("a.idinstitucion = b.idinstitucion");
        sqlDesigna_5.WHERE("a.idtipodesignacolegio = b.idtipodesignacolegio");

        // consulta para obtener el interesado en consulta asistencia
        sqlDesigna_6.SELECT("scs_personajg.apellido1\r\n" + "                     || CASE\r\n"
                + "                            WHEN scs_personajg.apellido2 IS NOT NULL THEN ' '\r\n"
                + "                             || scs_personajg.apellido2\r\n"
                + "                             || ','\r\n" + "                            ELSE ','\r\n"
                + "                        END\r\n" + "                     || scs_personajg.nombre");
        sqlDesigna_6.FROM("scs_ejg\r\n" + "                JOIN scs_personajg ON\r\n"
                + "                        scs_personajg.idpersona = scs_ejg.idpersona\r\n"
                + "                    AND\r\n"
                + "                        scs_personajg.idinstitucion = scs_ejg.idinstitucion");
        sqlDesigna_6.WHERE("scs_ejg.idinstitucion = " + item.getidInstitucion());
        sqlDesigna_6.WHERE("scs_ejg.anio = " + item.getAnnio());
        sqlDesigna_6.WHERE("scs_ejg.numero =" + item.getNumero());

        // datos de interes de la consulta DESIGNA
        sqlDesigna_7.SELECT("'Juzgado: ' || scs_juzgado.nombre");
        sqlDesigna_7.FROM("scs_ejg\r\n" + "                    JOIN scs_juzgado ON\r\n"
                + "                        scs_juzgado.idjuzgado = scs_ejg.juzgado\r\n" + "                    AND\r\n"
                + "                        scs_juzgado.idinstitucion = scs_ejg.idinstitucion");
        sqlDesigna_7.WHERE("scs_ejg.anio = " + item.getAnnio());
        sqlDesigna_7.WHERE("scs_ejg.numero = " + item.getNumero());
        sqlDesigna_7.WHERE("scs_ejg.idinstitucion = " + item.getidInstitucion());

        // consulta para obtener la relacion de DESIGNA con los datos de las consultas
        // anteriores.
        sqlDesigna.SELECT("TRIM('DESIGNACIÓN') sjcs,"
                + "TO_CHAR('D' || ejgd.aniodesigna || '/' || ejgd.numerodesigna) idsjcs," + "ejg.FECHAAPERTURA fecha,"
                + "            ejg.idinstitucion idinstitucion,\r\n" + "            ejgd.aniodesigna anio,\r\n"
                + "            ejgd.numerodesigna numero,\r\n" + "            ejg.idpersona idletrado,"
                + "TO_CHAR(ejgd.idturno) idturno," + "(" + sqlDesigna_2.toString() + ") idtipo," + "("
                + sqlDesigna_3.toString() + ") codigo," + "(" + sqlDesigna_4.toString() + ") des_turno," + "("
                + sqlDesigna_5.toString() + ") des_tipo," + "(" + sqlDesigna_1.toString() + ") nombreletrado," + "("
                + sqlDesigna_6.toString() + ") interesado,"
                + "nvl( numerodiligencia,'Sin número') || ' / ' || nvl( numeroprocedimiento,'Sin número') dilnigproc,"
                + " f_siga_getrecurso(res.descripcion,1) resolucion");

        sqlDesigna.SELECT("NULL IDPERSONAJG");

        sqlDesigna.FROM("scs_ejg ejg "
                + "LEFT OUTER JOIN scs_tiporesolucion res ON ejg.idtiporatificacionejg = res.idtiporesolucion,"
                + " scs_ejgdesigna ejgd");
        sqlDesigna.WHERE("ejg.anio = " + item.getAnnio());
        sqlDesigna.WHERE("ejg.numero = " + item.getNumero());
        sqlDesigna.WHERE("ejg.idtipoejg =  " + item.getTipoEJG());
        sqlDesigna.WHERE("ejg.idinstitucion =" + item.getidInstitucion());
        sqlDesigna.WHERE("ejgd.anioejg = ejg.anio");
        sqlDesigna.WHERE("ejgd.numeroejg = ejg.numero  ");
        sqlDesigna.WHERE("ejgd.idtipoejg = ejg.idtipoejg ");
        sqlDesigna.WHERE("ejgd.idinstitucion = ejg.idinstitucion");

        // sentencia expediente
        sqlExpediente.SELECT("TRIM('EXPEDIENTE') sjcs,"

                + "    TO_CHAR('X'||exp.anioejg||'/'||exp.numeroexpediente) idsjcs," + "exp.FECHA fecha,"
                + "    exp.idinstitucion idinstitucion," + "    exp.anioexpediente anio,"
                + "    exp.numeroexpediente numero," + "     den.idpersona idletrado, " + "NULL  idturno,"
                + "    TO_CHAR(exp.idtipoexpediente) idtipo," + "    TO_CHAR(exp.numeroexpediente) codigo,"
                + "    NULL des_turno," + "ext.nombre des_tipo,"

                + "( per.apellidos1 || ' ' || per.apellidos2 || ',' || per.nombre) nombreletrado,"

                + "NULL interesado," + "NULL dilnigproc," + "exp.DESCRIPCIONRESOLUCION resolucion");

        sqlExpediente.SELECT("NULL IDPERSONAJG");

        sqlExpediente.FROM("   exp_expediente exp," + "            exp_denunciado den," + "            cen_persona per,"
                + "            exp_tipoexpediente ext");
        sqlExpediente.WHERE("exp.numeroejg = " + item.getNumero());
        sqlExpediente.WHERE("exp.idtipoejg = " + item.getTipoEJG());
        sqlExpediente.WHERE("exp.anioejg = " + item.getAnnio());
        sqlExpediente.WHERE("exp.idinstitucion_tipoexpediente = " + item.getidInstitucion());
        sqlExpediente.WHERE(
                "exp.idinstitucion = den.idinstitucion" + "            AND exp.idtipoexpediente = den.idtipoexpediente"
                        + "            AND exp.idinstitucion_tipoexpediente = den.idinstitucion_tipoexpediente"
                        + "            AND exp.numeroexpediente = den.numeroexpediente"
                        + "            AND exp.anioexpediente = den.anioexpediente"
                        + "            AND den.idpersona = per.idpersona"
                        + "            AND exp.idtipoexpediente = ext.idtipoexpediente"
                        + "            AND exp.idinstitucion_tipoexpediente = ext.idinstitucion");

        // Sentencia principal.
        sqlPrincipal.SELECT("*");
        sqlPrincipal.FROM("(" + sqlAsistencia.toString() + " UNION " + sqlSOJ.toString() + " UNION "
                + sqlDesigna.toString() + " UNION " + sqlExpediente.toString() + ")");
        sqlPrincipal.WHERE("ROWNUM <= 200");
        sqlPrincipal.ORDER_BY("sjcs");
        sqlPrincipal.ORDER_BY("idinstitucion");
        sqlPrincipal.ORDER_BY("anio DESC");
        sqlPrincipal.ORDER_BY("codigo DESC");

        return sqlPrincipal.toString();
    }

    /**
     * @param anio
     * @param numEJG
     * @param idTipoEJG
     * @param idInstitucion
     * @return
     */
    public String getIdEnvio(String anio, String numEJG, Short idInstitucion) {
        SQL sql = new SQL();

        sql.SELECT("cc1.idenvio");

        sql.FROM("env_valorcampoclave cc1");
        sql.JOIN("env_envios cc2 ON (cc1.idinstitucion = cc2.idinstitucion AND cc1.idenvio = cc2.idenvio)");
        sql.JOIN("env_valorcampoclave cc3 ON (cc1.idenvio = cc3.idenvio)");

        sql.WHERE("cc1.idinstitucion = " + idInstitucion);
        sql.WHERE("cc1.idtipoinforme = 'EJG'");
        sql.WHERE("cc1.campo = 'numero'");
        sql.WHERE("cc1.valor = '" + numEJG + "'");
        sql.WHERE("cc3.campo = 'anio'");
        sql.WHERE("cc3.valor = '" + anio + "'");

        sql.GROUP_BY("cc1.idenvio");

        return sql.toString();
    }

    public String busquedaProcuradorEJG(String idProcurador, String idinstitucion) {// String idProcurador,
        SQL sql = new SQL();

        sql.SELECT("p.ncolegiado, p.nombre, p.apellidos1, p.apellidos2, p.fechabaja, p.idprocurador");// dp.numerodesignacion,
        // dp.fechadesigna,
        // dp.observaciones,
        // dp.motivosrenuncia,dp.fecharenuncia,
        // dp.fecharenunciasolicita

        sql.FROM("SCS_PROCURADOR p");// SCS_DESIGNAPROCURADOR dp
        // sql2.INNER_JOIN(" on p.idprocurador = ejg.idprocurador and p.idinstitucion =
        // ejg.idinstitucion ");
        sql.WHERE("p.idinstitucion = " + idinstitucion);
        sql.WHERE("p.idprocurador = " + idProcurador);

        return sql.toString();
    }

    public String getNewIdDocumentacionEjg(Short idInstitucion) {
        SQL sql = new SQL();

        sql.SELECT("NVL(MAX(DOC.IDDOCUMENTACION),0) +1 AS ID");

        sql.FROM("SCS_DOCUMENTACIONEJG DOC");

        sql.WHERE("DOC.IDINSTITUCION = '" + idInstitucion + "'");

        return sql.toString();
    }

    public String getObservacionEstadoEjgDictamen(Short idInstitucion, String idLenguaje, Short idTipoDictamen) {
        SQL sql = new SQL();

        sql.SELECT("f_Siga_Getrecurso_Etiqueta('gratuita.ejg.estado.literal.automatico','" + idLenguaje + "') \r\n"
                + "	|| ' ' || f_Siga_Getrecurso_Etiqueta('gratuita.busquedaEJG.dictamen', '" + idLenguaje
                + "') || ' ' ||\r\n" + "				           (Select f_Siga_Getrecurso(Descripcion, '"
                + idLenguaje + "')\r\n" + "				              From Scs_Tipodictamenejg\r\n"
                + "				             Where Idinstitucion = '" + idInstitucion + "'\r\n"
                + "	                        And Idtipodictamenejg = " + idTipoDictamen + ") as observaciones");

        sql.FROM("Scs_Estadoejg");

        // Para coger solo un resultado
        sql.WHERE("ROWNUM = 1");

        return sql.toString();
    }

    public String getObservacionEstadoEjgPonente(Short idInstitucion, String idLenguaje, Integer idPonente) {
        SQL sql = new SQL();

        sql.SELECT("f_Siga_Getrecurso_Etiqueta('gratuita.ejg.estado.literal.automatico','" + idLenguaje + "') \r\n"
                + "	|| ' ' || f_Siga_Getrecurso_Etiqueta('gratuita.operarRatificacion.literal.ponente', '" + idLenguaje
                + "') || ' ' ||\r\n" + "				           (Select f_Siga_Getrecurso(Nombre, '" + idLenguaje
                + "')\r\n" + "				              From Scs_Ponente\r\n"
                + "				             Where Idinstitucion = '" + idInstitucion + "'\r\n"
                + "	                        And Idtipodictamenejg = " + idPonente + ") as observaciones");

        sql.FROM("Scs_Estadoejg");

        // Para coger solo un resultado
        sql.WHERE("ROWNUM = 1");

        return sql.toString();
    }

    public String getObservacionEstadoEjgResol(String idLenguaje, Short idTiporatificacionEJG) {
        SQL sql = new SQL();

        sql.SELECT("f_Siga_Getrecurso_Etiqueta('gratuita.ejg.estado.literal.automatico','" + idLenguaje + "') \r\n"
                + "	|| ' ' || f_Siga_Getrecurso_Etiqueta('gratuita.operarRatificacion.literal.tipoRatificacion', '"
                + idLenguaje + "') || ' ' ||\r\n" + "				           (Select f_Siga_Getrecurso(Descripcion, '"
                + idLenguaje + "')\r\n" + "				              From Scs_Tiporesolucion\r\n"
                + "				             Where Idtiporesolucion = " + idTiporatificacionEJG + ") as observaciones");

        sql.FROM("Scs_Estadoejg");

        // Para coger solo un resultado
        sql.WHERE("ROWNUM = 1");

        return sql.toString();
    }

    public String getObservacionEstadoEjgImpug(String idLenguaje, String idTiporesolauto) {
        SQL sql = new SQL();

        sql.SELECT("f_Siga_Getrecurso_Etiqueta('gratuita.ejg.estado.literal.automatico','" + idLenguaje + "') \r\n"
                + "	|| ' ' || f_Siga_Getrecurso_Etiqueta('pestana.justiciagratuitaejg.impugnacion', '" + idLenguaje
                + "') || ' ' ||\r\n" + "				           (Select f_Siga_Getrecurso(Descripcion, '"
                + idLenguaje + "')\r\n" + "				              From Scs_Tiporesolauto\r\n"
                + "				             Where Idtiporesolauto = " + idTiporesolauto + ") as observaciones");

        sql.FROM("Scs_Estadoejg");

        // Para coger solo un resultado
        sql.WHERE("ROWNUM = 1");

        return sql.toString();
    }

    public String getObservacionEstadoIniInsertEjg(String idLenguaje) {
        SQL sql = new SQL();

        sql.SELECT("f_Siga_Getrecurso_Etiqueta('gratuita.ejg.estado.literal.automatico','" + idLenguaje + "') \r\n"
                + "	|| ' ' || f_Siga_Getrecurso_Etiqueta('expedientes.nuevoExpediente.cabecera', '" + idLenguaje
                + "') || ' ' as observaciones\r\n");

        sql.FROM("Scs_Estadoejg");

        // Para coger solo un resultado
        sql.WHERE("ROWNUM = 1");

        return sql.toString();
    }

    public String getObservacionEstadoEjgResolDev(String idLenguaje) {
        SQL sql = new SQL();

        sql.SELECT("f_Siga_Getrecurso_Etiqueta('gratuita.ejg.estado.literal.automatico','" + idLenguaje
                + "') as observaciones\r\n");

        sql.FROM("Scs_Estadoejg");

        // Para coger solo un resultado
        sql.WHERE("ROWNUM = 1");

        return sql.toString();
    }
    
    public String getIdEcomCola() {
        SQL sql = new SQL();

        sql.SELECT("SEQ_ECOM_COLA.CURRVAL");
        sql.FROM("DUAL");

        return sql.toString();
    }
    
    public String getDatosExpInsos(EjgItem ejgItem) {
    	SQL sql = new SQL();
    	SQL sqlIdpersona = new SQL();
    	
    	sqlIdpersona.SELECT("p.IDPERSONA");
    	sqlIdpersona.FROM("CEN_PERSONA p, SCS_DESIGNASLETRADO desle");
    	sqlIdpersona.WHERE("desle.IDINSTITUCION = des.idinstitucion and desle.IDTURNO = des.idturno and desle.ANIO = des.anio "
    			+ "and desle.NUMERO = des.numero and p.IDPERSONA = desle.IDPERSONA and desle.IDPERSONA = "
    			+ "F_SIGA_GETIDLETRADO_DESIGNA(desle.idInstitucion,desle.idTurno,desle.anio,desle.NUMERO) and rownum = 1");
    	
    	sql.SELECT("e.numero, e.numejg, e.idtipoejg, e.anio, e.idinstitucion, des.idjuzgado, des.idpretension, d.idturno, NVL(des.idprocedimiento, '') idprocedimiento,"
    			+ "("+sqlIdpersona.toString()+") idpersona, pjg.idpersona idpersonasolicitante, pjg.nif nifsolicitante, pjg.nombre nombresolicitante, pjg.apellido1 apellido1solicitante, "
    					+ "NVL(pjg.apellido2,'') apellido2solicitante, des.numprocedimiento");
    	sql.FROM("SCS_EJG e");
    	sql.INNER_JOIN("SCS_EJGDESIGNA d ON (d.idinstitucion=e.idInstitucion and d.anioejg=e.anio and d.idtipoejg=e.idtipoejg and d.numeroejg=e.numero)");
    	sql.INNER_JOIN("SCS_DESIGNA des ON (d.idinstitucion=des.idInstitucion and d.aniodesigna=des.anio and d.idturno=des.idturno "
    			+ "and d.numerodesigna=des.numero)");
    	sql.LEFT_OUTER_JOIN("SCS_PERSONAJG pjg ON (e.idpersonajg=pjg.idpersona)");
    	sql.WHERE("e.idinstitucion="+ejgItem.getidInstitucion()+" and e.anio="+ejgItem.getAnnio()+" and e.idtipoejg="+ejgItem.getTipoEJG()+
    			" and e.numero="+ejgItem.getNumero());
    	
    	return sql.toString();
    }
}
