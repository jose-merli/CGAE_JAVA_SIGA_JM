package org.itcgae.siga.db.services.scs.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableItem;
import org.itcgae.siga.DTOs.scs.ColegiadosSJCSItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.ScsEjgSqlProvider;

public class ScsEjgSqlExtendsProvider extends ScsEjgSqlProvider {


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
}
