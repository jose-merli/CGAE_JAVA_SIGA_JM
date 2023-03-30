package org.itcgae.siga.db.services.cen.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.scs.ColegiadosSJCSItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.mappers.CenColegiadoSqlProvider;

public class CenColegiadoSqlExtendsProvider extends CenColegiadoSqlProvider {

	public String selectColegiados(Short idInstitucion, ColegiadoItem colegiadoItem, Integer tamMaximo) {

		SQL sql = new SQL();
		SQL sql2 = new SQL();
		SQL sql3 = new SQL();
		String situacCad = "";

		// En el caso de que venga de la pantalla de busqueda colegiados/no colegiados, tendremos que preparar el filtro de instituciones
		String instituciones = "";
		if(colegiadoItem.getColegio() != null && colegiadoItem.getColegio().length > 0) {
			if (colegiadoItem.getColegio().length > 1) {
				for (String string : colegiadoItem.getColegio()) {
					instituciones += "'" + string + "'";
					instituciones += ",";
				}
				instituciones = instituciones.substring(0, instituciones.length() - 1);
			} else if (colegiadoItem.getColegio().length == 1) {
				instituciones = "'" + colegiadoItem.getColegio()[0] + "'";
			}
		}
		
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		
			sql.SELECT_DISTINCT("col.idpersona");
			sql.SELECT_DISTINCT("col.idinstitucion");
			sql.SELECT_DISTINCT("per.nifcif");
			sql.SELECT_DISTINCT("col.comunitario");
			sql.SELECT_DISTINCT("concat(concat(per.apellidos1 || ' ', concat(per.apellidos2 , ', ')), per.nombre || ' ') AS nombre");
			if (idInstitucion.equals(Short.parseShort("2000"))){
				sql.SELECT_DISTINCT("cli2.noaparecerredabogacia as noaparecerredabogacia2");
			}else {
				sql.SELECT_DISTINCT("cli.noaparecerredabogacia as noaparecerredabogacia2");				
			}
			sql.SELECT("TO_CHAR(col.fechaincorporacion,'DD/MM/YYYY') AS fechaincorporacion");
			sql.SELECT_DISTINCT("nvl(decode(nvl(col.comunitario,0),0, col.ncolegiado, col.ncomunitario), col.ncolegiado) as numcolegiado");
			sql.SELECT_DISTINCT("colest.idestado as situacion");
			sql.SELECT_DISTINCT("colest.fechaestado as fechaestado");
			sql.SELECT("decode (f_siga_gettipocliente(col.idpersona,col.idinstitucion,sysdate),'10','No Ejerciente','20','Ejerciente','30','Baja Colegial','40','Inhabilitación','50','Suspensión Ejercicio','60','Baja por Deceso','Baja por Deceso') AS estadoColegial");
			sql.SELECT_DISTINCT("col.situacionresidente as situacionresidente");

			sql.SELECT_DISTINCT(
					"concat( decode(col.situacionresidente,0,'No', 'Sí')  || ' / ',decode(col.comunitario,0,'No', 'Sí')) as residenteInscrito");
			sql.SELECT("TO_CHAR(per.fechanacimiento,'DD/MM/YYYY') AS fechanacimiento");
			sql.SELECT("inst.abreviatura as colegioResultado");

				
		sql.FROM("cen_colegiado col");

		sql.INNER_JOIN("cen_persona per on col.idpersona = per.idpersona");
		sql.INNER_JOIN("cen_institucion inst on col.idinstitucion = inst.idinstitucion");

			
		if (idInstitucion != Short.parseShort("2000") && idInstitucion != Short.parseShort("3500")) {
			if (idInstitucion > Short.parseShort("2001") && idInstitucion < Short.parseShort("2100") ) {
				sql.INNER_JOIN("cen_cliente cli on (col.idpersona = cli.idpersona and col.idinstitucion = cli.idinstitucion)");
				//sql.INNER_JOIN("cen_cliente cli on (col.idpersona = cli.idpersona and col.idinstitucion = cli.idinstitucion)");
			}
			else{
				sql.INNER_JOIN("cen_cliente cli on (col.idpersona = cli.idpersona and inst.cen_inst_IDINSTITUCION  =  cli.idinstitucion)");
				//sql.INNER_JOIN("cen_cliente cli on (col.idpersona = cli.idpersona and inst.cen_inst_IDINSTITUCION  =  cli2.idinstitucion)");
			}
			
		}else {
            sql.INNER_JOIN("cen_cliente cli on (col.idpersona = cli.idpersona and cli.idinstitucion =  '"+ idInstitucion + "')");
			sql.INNER_JOIN("cen_cliente cli2 on (col.idpersona = cli2.idpersona and col.idinstitucion = cli2.idinstitucion)");
		}
		sql.INNER_JOIN(
				"CEN_DATOSCOLEGIALESESTADO colest on (col.idpersona = colest.idpersona and col.idinstitucion = colest.idinstitucion  and colest.fechaestado = (\r\n"
						+ "                                            select max(datcol.fechaestado) from CEN_DATOSCOLEGIALESESTADO datcol where datcol.idpersona = colest.idpersona and datcol.idinstitucion = colest.idinstitucion"
						+ " and datcol.fechaestado < sysdate))");
		
		if (colegiadoItem.getIdgrupo() != null && colegiadoItem.getIdgrupo().length > 0) {
		sql.LEFT_OUTER_JOIN("cen_gruposcliente_cliente grucli on \r\n"
				+ "    ((grucli.idinstitucion = inst.idinstitucion or grucli.idinstitucion = '2000') and col.idpersona = grucli.idpersona and ((grucli.fecha_inicio <= SYSDATE OR grucli.fecha_inicio IS NULL ) and \r\n"
				+ "        ( grucli.fecha_baja > SYSDATE OR grucli.fecha_baja IS NULL)))");
		}
		
		
		
	/*	sql.INNER_JOIN("cen_estadocolegial estcol on (colest.idestado = estcol.idestado)");
		sql.INNER_JOIN("gen_recursos_catalogos cat on (estcol.descripcion = cat.idrecurso and cat.idlenguaje = '1')");
		*/
		if(!UtilidadesString.esCadenaVacia(colegiadoItem.getDomicilio()) || !UtilidadesString.esCadenaVacia(colegiadoItem.getIdPoblacion()) 
				|| !UtilidadesString.esCadenaVacia(colegiadoItem.getIdProvincia()) || !UtilidadesString.esCadenaVacia(colegiadoItem.getTelefono())
				|| !UtilidadesString.esCadenaVacia(colegiadoItem.getCorreo()) || !UtilidadesString.esCadenaVacia(colegiadoItem.getTipoDireccion()) 
				|| !UtilidadesString.esCadenaVacia(colegiadoItem.getMovil()) || !UtilidadesString.esCadenaVacia(colegiadoItem.getCodigoPostal())) {
			
			sql.LEFT_OUTER_JOIN(
					"cen_direcciones dir on (cli.idpersona = dir.idpersona and cli.idinstitucion = dir.idinstitucion and inst.idinstitucion = dir.idinstitucion and dir.fechabaja is null)");

			sql.LEFT_OUTER_JOIN("CEN_DIRECCION_TIPODIRECCION TIPODIR ON (CLI.IDPERSONA = TIPODIR.IDPERSONA AND"  
		                + " DIR.IDDIRECCION = TIPODIR.IDDIRECCION AND CLI.IDINSTITUCION = TIPODIR.IDINSTITUCION AND "
		                + " INST.IDINSTITUCION = DIR.IDINSTITUCION)"); 
		}
		
		if ((colegiadoItem.getTipoCV() != null && colegiadoItem.getTipoCV() != "") || (colegiadoItem.getSubTipoCV1() != null && colegiadoItem.getSubTipoCV1() != "") || (colegiadoItem.getSubTipoCV2() != null && colegiadoItem.getSubTipoCV2() != "")) {
			sql.LEFT_OUTER_JOIN(
					"cen_datosCV datosCV ON ( datosCV.idInstitucion = col.idInstitucion and datosCV.idPersona = per.idPersona and datosCV.fechabaja is null)");
			
			sql.LEFT_OUTER_JOIN("cen_tiposcv cenTipoCV ON ( cenTipoCV.idTipoCV = datosCV.idTipoCV and cenTipoCV.fecha_baja is null)");
			sql.LEFT_OUTER_JOIN("cen_tiposcvsubtipo2 subt2 ON ( subt2.idTipoCV = datosCV.idTipoCV and subt2.idInstitucion = col.idInstitucion and subt2.fecha_baja is null)");
			sql.LEFT_OUTER_JOIN("cen_tiposcvsubtipo1 subt1 ON ( subt1.idTipoCV = datosCV.idTipoCV and subt1.idInstitucion = col.idInstitucion and subt1.fecha_baja is null)");
		}
		if(!instituciones.equals("")) {
			sql.WHERE("COL.IDINSTITUCION IN (" + instituciones + ")");
		} else {
			if (idInstitucion != Short.parseShort("2000") && idInstitucion != Short.parseShort("3500")) {
				if (idInstitucion > Short.parseShort("2001") && idInstitucion < Short.parseShort("2100") ) {
					sql.WHERE("COL.IDINSTITUCION = '" + idInstitucion + "'");
				}
				else{
                    sql.WHERE("inst.cen_inst_IDINSTITUCION = '" + idInstitucion + "'");

				}
				
			}
		}
		
		sql.WHERE("per.idtipoidentificacion not in '20'");

		if (colegiadoItem.getNif() != null && colegiadoItem.getNif() != "") {
			sql.WHERE("upper(per.nifcif) like upper('%" + colegiadoItem.getNif() + "%')");
		}

		if (colegiadoItem.getNombre() != null && colegiadoItem.getNombre() != "") {
			String columna = "per.nombre";
			String cadena = colegiadoItem.getNombre();
			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
//			sql.WHERE("upper(per.nombre) like upper('%" + colegiadoItem.getNombre() + "%')");
		}

		if (colegiadoItem.getApellidos() != null && colegiadoItem.getApellidos() != "") {
			
			String columna = "REPLACE(CONCAT(per.apellidos1,per.apellidos2), ' ', '')";
			String cadena = colegiadoItem.getApellidos().replaceAll("\\s+","");
			
			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
			
//			sql.WHERE("UPPER(CONCAT(per.apellidos1,per.apellidos2)) LIKE UPPER('%" +colegiadoItem.getApellidos().replaceAll("\\s+","")
//					+ "%')");
		}
		
		if (colegiadoItem.getIdPersona() != null && colegiadoItem.getIdPersona() != "") {
			sql.WHERE("per.idpersona = " + colegiadoItem.getIdPersona());
		}		
		
		if (colegiadoItem.getNumColegiado() != null && colegiadoItem.getNumColegiado() != "") {
			sql.WHERE("(decode(col.comunitario,1,col.ncomunitario,col.ncolegiado) = '" + colegiadoItem.getNumColegiado() + "')");
		}

		if (colegiadoItem.getSexo() != null && colegiadoItem.getSexo() != "") {
			sql.WHERE("per.sexo = '" + colegiadoItem.getSexo() + "'");
		}

		if (colegiadoItem.getCodigoPostal() != null && colegiadoItem.getCodigoPostal() != "") {
			sql.WHERE("dir.codigopostal ='" + colegiadoItem.getCodigoPostal() + "'");
		}

		if (colegiadoItem.getTipoDireccion() != null && colegiadoItem.getTipoDireccion() != "") {
			sql.WHERE("tipodir.idtipodireccion = "+ colegiadoItem.getTipoDireccion());
		}else {
			/*sql.WHERE("(tipodir.idtipodireccion = 2 OR 2 NOT IN (SELECT idtipodireccion FROM CEN_DIRECCION_TIPODIRECCION TIPODIR2 "
					+ "WHERE TIPODIR.IDPERSONA = TIPODIR2.IDPERSONA  AND TIPODIR.IDINSTITUCION = TIPODIR2.IDINSTITUCION ))");*/
		}

		if (colegiadoItem.getIdEstadoCivil() != null && colegiadoItem.getIdEstadoCivil() != "") {
			sql.WHERE("per.idestadocivil = '" + colegiadoItem.getIdEstadoCivil() + "'");
		}

		if (colegiadoItem.getIdProvincia() != null && colegiadoItem.getIdProvincia() != "") {
			sql.WHERE("dir.idprovincia = '" + colegiadoItem.getIdProvincia() + "'");
		}

		if (colegiadoItem.getIdPoblacion() != null && colegiadoItem.getIdPoblacion() != "") {
			sql.WHERE("dir.idpoblacion = '" + colegiadoItem.getIdPoblacion() + "'");
		}

		if (colegiadoItem.getDomicilio() != null && colegiadoItem.getDomicilio() != "") {
			String columna = "dir.domicilio";
			String cadena = colegiadoItem.getDomicilio();
			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
//			sql.WHERE("(dir.domicilio) like upper('%" + colegiadoItem.getDomicilio() + "%')");
		}

		if (colegiadoItem.getCorreo() != null && colegiadoItem.getCorreo() != "") {
			String columna = "dir.correoelectronico";
			String cadena = colegiadoItem.getCorreo();
			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
			
//			sql.WHERE("upper(dir.correoelectronico) LIKE upper('%" + colegiadoItem.getCorreo() + "%')");
		}

		if (colegiadoItem.getTelefono() != null && colegiadoItem.getTelefono() != "") {
			sql.WHERE("dir.telefono1 like '%" + colegiadoItem.getTelefono() + "%'");
		}

		if (colegiadoItem.getMovil() != null && colegiadoItem.getMovil() != "") {
			sql.WHERE("dir.movil like '%" + colegiadoItem.getMovil() + "%'");
		}

		if (colegiadoItem.getTipoCV() != null && colegiadoItem.getTipoCV() != "") {
			sql.WHERE("datoscv.idtipocv = '" + colegiadoItem.getTipoCV() + "'");
		}

		if (colegiadoItem.getSubTipoCV1() != null && colegiadoItem.getSubTipoCV1() != "") {
			sql.WHERE("datoscv.idtipocvsubtipo1 = '" + colegiadoItem.getSubTipoCV1() + "'");
		}
		
		if (colegiadoItem.getSubTipoCV2() != null && colegiadoItem.getSubTipoCV2() != "") {
			sql.WHERE("datoscv.idtipocvsubtipo2 = '" + colegiadoItem.getSubTipoCV2() + "'");
		}

//		if (colegiadoItem.getSituacion() != null && colegiadoItem.getSituacion() != "") {
//			sql.WHERE("colest.idestado ='" + colegiadoItem.getSituacion() + "'");
//		}
		
		 if(colegiadoItem.getSituaciones() != null) {
	        	for (String situac : colegiadoItem.getSituaciones()) {
	        			situacCad += situac+",";
	    		}
	        	situacCad = situacCad.substring(0, (situacCad.length() -1));
	                sql.WHERE ("colest.idestado IN (" + situacCad + ")");
	     }
		 
		if (colegiadoItem.getResidencia() != null && colegiadoItem.getResidencia() != "") {
			sql.WHERE("col.situacionresidente ='" + colegiadoItem.getResidencia() + "'");
		}

		if (colegiadoItem.getInscrito() != null && colegiadoItem.getInscrito() != "") {
			sql.WHERE("col.comunitario ='" + colegiadoItem.getInscrito() + "'");
		}


		if (colegiadoItem.getIdgrupo() != null && colegiadoItem.getIdgrupo().length > 0) {

			String etiquetas = "";
					
			for (int i = 0; colegiadoItem.getIdgrupo().length > i; i++) {

				if (i == colegiadoItem.getIdgrupo().length - 1) {
					etiquetas += "( grucli.IDGRUPO ='" + colegiadoItem.getIdgrupo()[i].getValue() + "' and grucli.IDINSTITUCION_GRUPO = '" + colegiadoItem.getIdgrupo()[i].getIdInstitucion() + "')";
				} else {
					etiquetas += "( grucli.IDGRUPO ='" + colegiadoItem.getIdgrupo()[i].getValue() + "' and grucli.IDINSTITUCION_GRUPO = '" + colegiadoItem.getIdgrupo()[i].getIdInstitucion() + "') or";

				}
			}

			sql.WHERE("(" + etiquetas + ")");
		}

		if (colegiadoItem.getFechaIncorporacion() != null && colegiadoItem.getFechaIncorporacion().length != 0) {

			if (colegiadoItem.getFechaIncorporacion()[0] != null && colegiadoItem.getFechaIncorporacion()[1] != null) {

				String fechaIncorporacionDesde = dateFormat.format(colegiadoItem.getFechaIncorporacion()[0]);
				String fechaIncorporacionHasta = dateFormat.format(colegiadoItem.getFechaIncorporacion()[1]);

				sql.WHERE("(TO_CHAR(col.fechaincorporacion,'DD/MM/YYYY') >= TO_DATE('" + fechaIncorporacionDesde
						+ "','DD/MM/YYYY') " + " and ( TO_CHAR(col.fechaincorporacion,'DD/MM/YYYY') <= TO_DATE('"
						+ fechaIncorporacionHasta + "','DD/MM/YYYY')))");

			} else if (colegiadoItem.getFechaIncorporacion()[0] != null
					&& colegiadoItem.getFechaIncorporacion()[1] == null) {

				String fechaIncorporacionDesde = dateFormat.format(colegiadoItem.getFechaIncorporacion()[0]);

				sql.WHERE("(TO_CHAR(col.fechaincorporacion,'DD/MM/YYYY') >= TO_DATE('" + fechaIncorporacionDesde
						+ "','DD/MM/YYYY'))");

			} else if (colegiadoItem.getFechaIncorporacion()[0] == null
					&& colegiadoItem.getFechaIncorporacion()[1] != null) {

				String fechaIncorporacionHasta = dateFormat.format(colegiadoItem.getFechaIncorporacion()[1]);

				sql.WHERE("(TO_CHAR(col.fechaincorporacion,'DD/MM/YYYY') <= TO_DATE('" + fechaIncorporacionHasta
						+ "','DD/MM/YYYY'))");
			}
		}


		
		if (colegiadoItem.getFechaNacimientoRango() != null && colegiadoItem.getFechaNacimientoRango().length != 0) {

			if (colegiadoItem.getFechaNacimientoRango()[0] != null && colegiadoItem.getFechaNacimientoRango()[1] != null) {

				String getFechaNacimientoDesde = dateFormat.format(colegiadoItem.getFechaNacimientoRango()[0]);
				String getFechaNacimientoHasta = dateFormat.format(colegiadoItem.getFechaNacimientoRango()[1]);

				sql.WHERE("(TO_CHAR(per.fechanacimiento, 'DD-MM-YYYY') >= TO_DATE('" + getFechaNacimientoDesde
						+ "','DD/MM/YYYY') " + " and ( TO_CHAR(per.fechanacimiento, 'DD-MM-YYYY') <= TO_DATE('"
						+ getFechaNacimientoHasta + "','DD/MM/YYYY')))");

			} else if (colegiadoItem.getFechaNacimientoRango()[0] != null
					&& colegiadoItem.getFechaNacimientoRango()[1] == null) {

				String getFechaNacimientoDesde = dateFormat.format(colegiadoItem.getFechaNacimientoRango()[0]);

				sql.WHERE("(TO_CHAR(per.fechanacimiento, 'DD-MM-YYYY') >= TO_DATE('" + getFechaNacimientoDesde
						+ "','DD/MM/YYYY'))");

			} else if (colegiadoItem.getFechaNacimientoRango()[0] == null
					&& colegiadoItem.getFechaNacimientoRango()[1] != null) {

				String getFechaNacimientoHasta = dateFormat.format(colegiadoItem.getFechaNacimientoRango()[1]);

				sql.WHERE("( TO_CHAR(per.fechanacimiento, 'DD-MM-YYYY') <= TO_DATE('" + getFechaNacimientoHasta
						+ "','DD/MM/YYYY'))");
			}
		}

		
		sql.ORDER_BY("NOMBRE");

		sql2.SELECT("CONSULTA.*, ROW_NUMBER() OVER(PARTITION BY concat(CONSULTA.idpersona,CONSULTA.idinstitucion) ORDER BY CONSULTA.idpersona) AS RN");
		sql2.FROM("(" + sql + ") CONSULTA");
//		sql2.WHERE("rownum < 5000");
	 
		if(null != colegiadoItem.getSearchCount() && colegiadoItem.getSearchCount() == true) {
			sql3.SELECT("count(*) as count");
			sql3.FROM("(" + sql + ")");

		}else {
			sql3.SELECT("*");
			sql3.FROM("(" + sql + ")");

			if (tamMaximo != null) {
				Integer tamMaxNumber = tamMaximo + 1;
				sql3.WHERE("rownum <= " + tamMaxNumber);

			}
		}
		//sql3.WHERE("RN = 1");

		
		return sql3.toString();
		
	}
	
	public String selectColegiado(Short idInstitucion, ColegiadoItem colegiadoItem) {

		SQL sql = new SQL();
		SQL sql1 = new SQL();
		SQL sql2 = new SQL();
		SQL sql3 = new SQL();

		// En el caso de que venga de la pantalla de busqueda colegiados/no colegiados, tendremos que preparar el filtro de instituciones
		String instituciones = "";
		if(colegiadoItem.getColegio() != null) {
			if (colegiadoItem.getColegio().length > 1) {
				for (String string : colegiadoItem.getColegio()) {
					instituciones += "'" + string + "'";
					instituciones += ",";
				}
				instituciones = instituciones.substring(0, instituciones.length() - 1);
			} else if (colegiadoItem.getColegio().length == 1) {
				instituciones = "'" + colegiadoItem.getColegio()[0] + "'";
			}
		}else if(colegiadoItem.getIdInstitucion() != null){
			instituciones = colegiadoItem.getIdInstitucion();
		}
		
		sql.SELECT_DISTINCT("col.idpersona");
		sql.SELECT_DISTINCT("col.idinstitucion");
		sql.SELECT_DISTINCT("col.identificadords");
		sql.SELECT_DISTINCT("per.nifcif");
		sql.SELECT_DISTINCT("concat(concat(per.apellidos1 || ' ', concat(per.apellidos2 , ', ')), per.nombre || ' ') AS nombre");
		sql.SELECT_DISTINCT("per.nombre as solonombre");
		sql.SELECT_DISTINCT("per.apellidos1");
		sql.SELECT_DISTINCT("per.apellidos2");
		sql.SELECT_DISTINCT("per.sexo");
		sql.SELECT_DISTINCT("per.idestadocivil");
		sql.SELECT_DISTINCT("cli.noaparecerredabogacia");
		sql.SELECT_DISTINCT("cli2.noaparecerredabogacia as noaparecerredabogacia2");
		sql.SELECT_DISTINCT("decode(cli2.noaparecerredabogacia, 0, 'NONono' , 1, 'SISisiSísíSÍ') as noAparecerRedAbogaciaFilter");
		sql.SELECT_DISTINCT("decode(col.situacionresidente, 0, 'NONono' , 1, 'SISisiSísíSÍ') as situacionresidenteFilter");
		sql.SELECT_DISTINCT("per.idtipoidentificacion");
		sql.SELECT_DISTINCT("per.naturalde");
		sql.SELECT("TO_CHAR(cli.fechaalta,'DD/MM/YYYY') AS fechaalta");
		sql.SELECT_DISTINCT("cli.idlenguaje");
		sql.SELECT_DISTINCT("cli.asientocontable");
		sql.SELECT_DISTINCT("col.nmutualista");
		sql.SELECT("TO_CHAR(col.fechaincorporacion,'DD/MM/YYYY') AS fechaincorporacion");
		sql.SELECT("TO_CHAR(col.fechajura,'DD/MM/YYYY') AS fechajura");
		sql.SELECT("TO_CHAR(col.fechatitulacion,'DD/MM/YYYY') AS fechatitulacion");
		sql.SELECT("TO_CHAR(col.fechapresentacion,'DD/MM/YYYY') AS fechapresentacion");
		sql.SELECT_DISTINCT("TO_CHAR(col.idtiposseguro) AS idTiposSeguro");

		sql.SELECT_DISTINCT("cli.comisiones");
		sql.SELECT_DISTINCT("cli.idtratamiento");
		sql.SELECT_DISTINCT("nvl(decode(nvl(col.comunitario,0),0, col.ncolegiado, col.ncomunitario), col.ncolegiado) as numcolegiado");
		sql.SELECT_DISTINCT("colest.idestado as situacion");
		sql.SELECT_DISTINCT("cat.descripcion as estadoColegial");
		sql.SELECT_DISTINCT("col.situacionresidente as situacionresidente");
		sql.SELECT_DISTINCT("col.comunitario as comunitario");

		sql.SELECT_DISTINCT(
				"concat( decode(col.situacionresidente,0,'No', 'Sí')  || ' / ',decode(col.comunitario,0,'No', 'Sí')) as residenteInscrito");
		sql.SELECT("TO_CHAR(per.fechanacimiento,'DD/MM/YYYY') AS fechanacimiento");
		sql.SELECT("inst.abreviatura as colegioResultado");
		
		sql1.SELECT("partidojudicial.nombre");		
		sql1.FROM("cen_partidojudicial partidojudicial");
		sql1.INNER_JOIN("cen_poblaciones pob on pob.idpartido = partidojudicial.idpartido");
		sql1.INNER_JOIN("cen_direcciones direcciones on pob.idpoblacion = direcciones.idpoblacion");
		sql1.INNER_JOIN(
				"CEN_DIRECCION_TIPODIRECCION tipodireccion ON (tipodireccion.IDDIRECCION = direcciones.IDDIRECCION AND"
						+ " tipodireccion.IDPERSONA = direcciones.IDPERSONA AND  tipodireccion.IDINSTITUCION = direcciones.IDINSTITUCION)");

		sql1.WHERE("tipodireccion.idtipodireccion = '2'");
		sql1.WHERE("direcciones.idpersona = dir.idpersona");
		sql1.WHERE("direcciones.idinstitucion = dir.idinstitucion");
		sql1.WHERE("direcciones.iddireccion = dir.iddireccion");

		sql.SELECT_DISTINCT("(" + sql1 + ") as partidoJudicial");

		sql.SELECT_DISTINCT("decode(TIPODIR.idtipodireccion ,3,dir.correoelectronico,'') AS correo");
		sql.SELECT_DISTINCT("decode(TIPODIR.idtipodireccion ,3,dir.telefono1,'') AS telefono");
		sql.SELECT_DISTINCT("decode(TIPODIR.idtipodireccion ,3,dir.movil,'') as movil");
		//sql.SELECT_DISTINCT("ROW_NUMBER() OVER(PARTITION BY concat(col.idpersona,col.idinstitucion) ORDER BY col.idpersona) AS RN");
		sql.FROM("cen_colegiado col");

		sql.INNER_JOIN("cen_persona per on col.idpersona = per.idpersona");
		sql.INNER_JOIN("cen_institucion inst on col.idinstitucion = inst.idinstitucion");

			
		if (idInstitucion != Short.parseShort("2000") && idInstitucion != Short.parseShort("3500")) {
			if (idInstitucion > Short.parseShort("2001") && idInstitucion < Short.parseShort("2100") ) {
				sql.INNER_JOIN("cen_cliente cli on (col.idpersona = cli.idpersona and col.idinstitucion = cli.idinstitucion)");
				sql.INNER_JOIN("cen_cliente cli2 on (col.idpersona = cli2.idpersona and col.idinstitucion = cli2.idinstitucion)");
			}
			else{
				sql.INNER_JOIN("cen_cliente cli on (col.idpersona = cli.idpersona and inst.cen_inst_IDINSTITUCION  =  cli.idinstitucion)");
				sql.INNER_JOIN("cen_cliente cli2 on (col.idpersona = cli2.idpersona and inst.cen_inst_IDINSTITUCION  =  cli2.idinstitucion)");
			}
			
		}else {
            sql.INNER_JOIN("cen_cliente cli on (col.idpersona = cli.idpersona and cli.idinstitucion =  '"+ idInstitucion + "')");
			sql.INNER_JOIN("cen_cliente cli2 on (col.idpersona = cli2.idpersona and col.idinstitucion = cli2.idinstitucion)");
		}

		
		if (colegiadoItem.getIdgrupo() != null && colegiadoItem.getIdgrupo().length > 0) {
		sql.LEFT_OUTER_JOIN("cen_gruposcliente_cliente grucli on \r\n"
				+ "    ((grucli.idinstitucion = inst.idinstitucion or grucli.idinstitucion = '2000') and col.idpersona = grucli.idpersona and ((grucli.fecha_inicio <= SYSDATE OR grucli.fecha_inicio IS NULL ) and \r\n"
				+ "        ( grucli.fecha_baja > SYSDATE OR grucli.fecha_baja IS NULL)))");
		}
		sql.INNER_JOIN(
				"CEN_DATOSCOLEGIALESESTADO colest on (col.idpersona = colest.idpersona and col.idinstitucion = colest.idinstitucion  and colest.fechaestado = (\r\n"
						+ "                                            select max(datcol.fechaestado) from CEN_DATOSCOLEGIALESESTADO datcol where datcol.idpersona = colest.idpersona and datcol.idinstitucion = colest.idinstitucion"
						+ " and datcol.fechaestado < sysdate))");
		sql.INNER_JOIN("cen_estadocolegial estcol on (colest.idestado = estcol.idestado)");
		sql.INNER_JOIN("gen_recursos_catalogos cat on (estcol.descripcion = cat.idrecurso and cat.idlenguaje = '1')");
		sql.LEFT_OUTER_JOIN(
				"cen_direcciones dir on (cli.idpersona = dir.idpersona and cli.idinstitucion = dir.idinstitucion and inst.idinstitucion = dir.idinstitucion and dir.fechabaja is null)");

		sql.LEFT_OUTER_JOIN("CEN_DIRECCION_TIPODIRECCION TIPODIR ON (CLI.IDPERSONA = TIPODIR.IDPERSONA AND"  
	                + " DIR.IDDIRECCION = TIPODIR.IDDIRECCION AND CLI.IDINSTITUCION = TIPODIR.IDINSTITUCION AND "
	                + " INST.IDINSTITUCION = DIR.IDINSTITUCION)"); 
		
		
		if ((colegiadoItem.getTipoCV() != null && colegiadoItem.getTipoCV() != "") || (colegiadoItem.getSubTipoCV1() != null && colegiadoItem.getSubTipoCV1() != "") || (colegiadoItem.getSubTipoCV2() != null && colegiadoItem.getSubTipoCV2() != "")) {
			sql.LEFT_OUTER_JOIN(
					"cen_datosCV datosCV ON ( datosCV.idInstitucion = col.idInstitucion and datosCV.idPersona = per.idPersona and datosCV.fechabaja is null)");
			
			sql.LEFT_OUTER_JOIN("cen_tiposcv cenTipoCV ON ( cenTipoCV.idTipoCV = datosCV.idTipoCV and cenTipoCV.fecha_baja is null)");
			sql.LEFT_OUTER_JOIN("cen_tiposcvsubtipo2 subt2 ON ( subt2.idTipoCV = datosCV.idTipoCV and subt2.idInstitucion = col.idInstitucion and subt2.fecha_baja is null)");
			sql.LEFT_OUTER_JOIN("cen_tiposcvsubtipo1 subt1 ON ( subt1.idTipoCV = datosCV.idTipoCV and subt1.idInstitucion = col.idInstitucion and subt1.fecha_baja is null)");
		}
		if(!instituciones.equals("")) {
			sql.WHERE("COL.IDINSTITUCION IN (" + instituciones + ")");
		} else {
			if (idInstitucion != Short.parseShort("2000") && idInstitucion != Short.parseShort("3500")) {
				if (idInstitucion > Short.parseShort("2001") && idInstitucion < Short.parseShort("2100") ) {
					sql.WHERE("COL.IDINSTITUCION = '" + idInstitucion + "'");
				}
				else{
                    sql.WHERE("inst.cen_inst_IDINSTITUCION = '" + idInstitucion + "'");

				}
				
			}
		}
		sql.WHERE("per.idtipoidentificacion not in '20'");

		
		if (colegiadoItem.getIdPersona() != null && colegiadoItem.getIdPersona() != "") {
			sql.WHERE("col.idpersona = " + colegiadoItem.getIdPersona());
		}
		if (colegiadoItem.getNif() != null && colegiadoItem.getNif() != "") {
			sql.WHERE("upper(per.nifcif) like upper('%" + colegiadoItem.getNif() + "%')");
		}

		sql.ORDER_BY("NOMBRE,CORREO,TELEFONO");

		sql2.SELECT("CONSULTA.*, ROW_NUMBER() OVER(PARTITION BY concat(CONSULTA.idpersona,CONSULTA.idinstitucion) ORDER BY CONSULTA.idpersona) AS RN");
		sql2.FROM("(" + sql + ") CONSULTA");
//		sql2.WHERE("rownum < 5000");
		
		sql3.SELECT("*");
		sql3.FROM("(" + sql2 + ")");
		sql3.WHERE("RN = 1");

		return sql3.toString();
		
	}

	public String updateColegiado(CenColegiado record) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		SQL sql = new SQL();
		sql.UPDATE("CEN_COLEGIADO");
		if (record.getFechapresentacion() != null) {
			// sql.SET("FECHAPRESENTACION = "+record.getFechapresentacion()+"");
			String fechaF = dateFormat.format(record.getFechapresentacion());
			sql.SET("FECHAPRESENTACION = TO_DATE('" + fechaF + "','DD/MM/YYYY hh24:mi:ss')");
		}
		
		if (record.getFechaincorporacion() != null) {
			String fechaF = dateFormat.format(record.getFechaincorporacion());
			sql.SET("FECHAINCORPORACION = TO_DATE('" + fechaF + "','DD/MM/YYYY hh24:mi:ss')");
		}

		if (record.getIndtitulacion() != null) {
			sql.SET("INDTITULACION = " + record.getIndtitulacion() + "");
		}
		if (record.getJubilacioncuota() != null) {
			sql.SET("JUBILACIONCUOTA = " + record.getIndtitulacion() + "");
		}
		if (record.getSituacionejercicio() != null) {
			sql.SET("SITUACIONEJERCICIO = " + record.getSituacionejercicio() + "");
		}
		if (record.getSituacionresidente() != null) {
			sql.SET("SITUACIONRESIDENTE = " + record.getSituacionresidente() + "");
		}
		if (record.getSituacionempresa() != null) {
			sql.SET("SITUACIONEMPRESA = " + record.getSituacionempresa() + "");
		}
		if (record.getFechamodificacion() != null) {
			String fechaF = dateFormat.format(record.getFechamodificacion());
			sql.SET("FECHAMODIFICACION = TO_DATE('" + fechaF + "','DD/MM/YYYY hh24:mi:ss')");
		} else {
			sql.SET("FECHAMODIFICACION = " + record.getFechamodificacion() + "");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = " + record.getUsumodificacion() + "");
		}
		if (record.getComunitario() != null) {
			sql.SET("COMUNITARIO = " + record.getComunitario() + "");
		}
		if (record.getNcolegiado() != null) {
			sql.SET("NCOLEGIADO = " + record.getNcolegiado() + "");
		}
		if (record.getFechajura() != null) {
			String fechaF = dateFormat.format(record.getFechajura());
			sql.SET("FECHAJURA = TO_DATE('" + fechaF + "','DD/MM/YYYY hh24:mi:ss')");
		} else {
			sql.SET("FECHAJURA = " + record.getFechajura() + "");
		}
		if (record.getNcomunitario() != null) {
			sql.SET("NCOMUNITARIO = " + record.getNcomunitario() + "");
		}
		if (record.getFechatitulacion() != null) {
			String fechaF = dateFormat.format(record.getFechatitulacion());
			sql.SET("FECHATITULACION = TO_DATE('" + fechaF + "','DD/MM/YYYY hh24:mi:ss')");
		} else {
			sql.SET("FECHATITULACION = " + record.getFechatitulacion() + "");
		}
		if (record.getOtroscolegios() != null) {
			sql.SET("OTROSCOLEGIOS = " + record.getOtroscolegios() + "");
		}
		if (record.getFechadeontologia() != null) {
			String fechaF = dateFormat.format(record.getFechadeontologia());
			sql.SET("FECHADEONTOLOGIA = TO_DATE('" + fechaF + "','DD/MM/YYYY hh24:mi:ss')");
		} else {
			sql.SET("FECHADEONTOLOGIA = " + record.getFechadeontologia() + "");
		}
		if (record.getFechamovimiento() != null) {
			String fechaF = dateFormat.format(record.getFechamovimiento());
			sql.SET("FECHAMOVIMIENTO = TO_DATE('" + fechaF + "','DD/MM/YYYY hh24:mi:ss')");
		} else {
			sql.SET("FECHAMOVIMIENTO = " + record.getFechamovimiento() + "");
		}
		sql.SET("IDTIPOSSEGURO = " + record.getIdtiposseguro() + "");
		if (record.getCuentacontablesjcs() != null) {
			sql.SET("CUENTACONTABLESJCS = " + record.getCuentacontablesjcs() + "");
		}
		if (record.getIdentificadords() != null) {
			sql.SET("IDENTIFICADORDS = " + record.getIdentificadords() + "");
		}
		if (record.getNmutualista() != null) {
			sql.SET("NMUTUALISTA = '" + record.getNmutualista() + "'");
		}
		if (record.getNumsolicitudcolegiacion() != null) {
			sql.SET("NUMSOLICITUDCOLEGIACION = " + record.getNumsolicitudcolegiacion() + "");
		}
		sql.WHERE("IDINSTITUCION = " + record.getIdinstitucion() + "");
		sql.WHERE("IDPERSONA = " + record.getIdpersona() + "");

		return sql.toString();
	}

	public String selectColegiaciones(Short idInstitucion, String idLenguaje, ColegiadoItem colegiadoItem) {

		SQL sql = new SQL();

		sql.SELECT("TO_CHAR(fechaincorporacion,'DD/MM/YYYY') AS fechaincorporacion");
		sql.SELECT("cat.descripcion as estadoColegial");
		sql.SELECT("decode (col.situacionresidente, 1, 'Si', 0, 'No') as residenteInscrito");
		sql.SELECT("decode (colest.situacionresidente, 1, 'Si', 0, 'No') as situacionResidente");
		sql.SELECT("observaciones");
		sql.SELECT("TO_CHAR(fechaestado,'DD/MM/YYYY') AS fechaestado");
		sql.SELECT("fechaestado AS fechaestadodate");
		sql.SELECT("colest.idestado AS idEstado");

		sql.FROM("cen_colegiado col");
		sql.INNER_JOIN(
				"CEN_DATOSCOLEGIALESESTADO colest on (col.idpersona = colest.idpersona and col.idinstitucion = colest.idinstitucion )");
		sql.INNER_JOIN("cen_estadocolegial estcol on (colest.idestado = estcol.idestado)");
		sql.INNER_JOIN("gen_recursos_catalogos cat on (estcol.descripcion = cat.idrecurso and cat.idlenguaje = '"
				+ idLenguaje + "')");

		sql.WHERE("col.idpersona = '" + colegiadoItem.getIdPersona() + "'");

		if (idInstitucion != Short.parseShort("2000")) {
			sql.WHERE("colest.idinstitucion = '" + idInstitucion + "'");
		}
		// sql1.WHERE("dir.fechabaja is null");

		sql.ORDER_BY("fechaestadodate desc");
		// sql.ORDER_BY("per.nombre");

		return sql.toString();
	}

	public String selectColegiacionesIdPersona(Long idPersona) {

		SQL sql = new SQL();

		sql.SELECT("col.idinstitucion as IDINSTITUCION");

		sql.FROM("cen_colegiado col");

		sql.WHERE("col.idpersona = '" + idPersona + "'");

		return sql.toString();
	}

	public String insertSelectiveForCreateNewColegiado(String idInstitucion, AdmUsuarios usuario,
			CenColegiado cenColegiado) {
		SQL sql = new SQL();

		sql.INSERT_INTO("CEN_COLEGIADO");

		sql.VALUES("IDPERSONA", "(Select max(idpersona)  from cen_persona where idpersona like '" + idInstitucion + "' || '%' )");
		sql.VALUES("IDINSTITUCION", "'" + idInstitucion + "'");
		sql.VALUES("FECHAMODIFICACION", "SYSDATE");
		sql.VALUES("USUMODIFICACION", "'" + String.valueOf(usuario.getIdusuario()) + "'");
		sql.VALUES("FECHAPRESENTACION", "'" + cenColegiado.getFechapresentacion() + "'");
		sql.VALUES("FECHAINCORPORACION", "'" + cenColegiado.getFechaincorporacion() + "'");
		sql.VALUES("INDTITULACION", "'" + String.valueOf(cenColegiado.getIndtitulacion()) + "'");
		sql.VALUES("JUBILACIONCUOTA", "'" + String.valueOf(cenColegiado.getJubilacioncuota()) + "'");
		sql.VALUES("SITUACIONEJERCICIO", "'" + String.valueOf(cenColegiado.getSituacionejercicio()) + "'");
		sql.VALUES("SITUACIONRESIDENTE", "'" + String.valueOf(cenColegiado.getSituacionresidente()) + "'");
		sql.VALUES("SITUACIONEMPRESA", "'" + String.valueOf(cenColegiado.getSituacionempresa()) + "'");
		sql.VALUES("FECHAMODIFICACION", "'" + cenColegiado.getFechamodificacion() + "'");
		sql.VALUES("USUMODIFICACION", "'" + String.valueOf(cenColegiado.getUsumodificacion()) + "'");
		sql.VALUES("COMUNITARIO", "'" + String.valueOf(cenColegiado.getComunitario()) + "'");
		sql.VALUES("NCOLEGIADO", "'" + String.valueOf(cenColegiado.getNcolegiado()) + "'");
		sql.VALUES("FECHAJURA", "'" + cenColegiado.getFechajura() + "'");
		sql.VALUES("NCOMUNITARIO", "'" + String.valueOf(cenColegiado.getNcomunitario()) + "'");
		sql.VALUES("FECHATITULACION", "'" + String.valueOf(cenColegiado.getFechatitulacion()) + "'");
		sql.VALUES("OTROSCOLEGIOS", "'" + String.valueOf(cenColegiado.getOtroscolegios()) + "'");
		sql.VALUES("FECHADEONTOLOGIA", "'" + cenColegiado.getFechadeontologia() + "'");
		sql.VALUES("FECHAMOVIMIENTO", "'" + cenColegiado.getFechamovimiento() + "'");
		sql.VALUES("IDTIPOSSEGURO", "'" + String.valueOf(cenColegiado.getIdtiposseguro()) + "'");
		sql.VALUES("CUENTACONTABLESJCS", "'" + String.valueOf(cenColegiado.getCuentacontablesjcs()).replace("'", "''") + "'");
		sql.VALUES("IDENTIFICADORDS", "'" + String.valueOf(cenColegiado.getIdentificadords()) + "'");
		sql.VALUES("NMUTUALISTA", "'" + String.valueOf(cenColegiado.getNmutualista()) + "'");
		sql.VALUES("NUMSOLICITUDCOLEGIACION", "'" + String.valueOf(cenColegiado.getNumsolicitudcolegiacion()) + "'");
		sql.VALUES("FECHA_BAJA", "null");

		return sql.toString();
	}

	public String getLabel(AdmUsuarios usuario) {

		SQL sql = new SQL();
		sql.SELECT("distinct MAX(GRUCLI.IDGRUPO) AS IDGRUPO");
		sql.SELECT("INITCAP(GENR.DESCRIPCION) as DESCRIPCION");
		sql.SELECT("GRUCLI.IDINSTITUCION");
		sql.FROM("cen_gruposcliente GRUCLI");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS GENR on GRUCLI.NOMBRE = GENR.IDRECURSO");
		sql.WHERE("GRUCLI.IDINSTITUCION in ('2000', '" + usuario.getIdinstitucion() + "')");
		sql.WHERE("GENR.IDLENGUAJE = '" + usuario.getIdlenguaje() + "'");
		sql.GROUP_BY("GENR.DESCRIPCION, GRUCLI.IDINSTITUCION");
		sql.ORDER_BY("DESCRIPCION");

		return sql.toString();
	}

	public String searchOtherCollegues(String idPersona, String idLenguaje) {

		SQL sql = new SQL();
		sql.SELECT_DISTINCT("col.idInstitucion AS idInstitucion");
		sql.SELECT("per.nifcif AS nif");
		sql.SELECT("col.ncolegiado AS numeroColegiado");
		sql.SELECT("concat(per.nombre || ' ',concat(per.apellidos1 || ' ', per.apellidos2)) AS nombre");
		sql.SELECT("DECODE (col.situacionresidente,0,'No', 'Sí') AS residenteInscrito");
		sql.SELECT("cat.descripcion AS estadoColegial");
		sql.SELECT("TO_CHAR(per.fechanacimiento,'DD/MM/YYYY') AS fechaNacimiento");
		sql.SELECT("dir.correoelectronico AS correo");
		sql.SELECT("dir.telefono1 AS telefono");
		sql.SELECT("dir.movil AS movil");
		sql.SELECT("inst.abreviatura as institucion");

		sql.FROM("cen_colegiado col");

		sql.INNER_JOIN("cen_persona per ON per.idpersona = col.idpersona");
		sql.INNER_JOIN(
				"cen_direcciones dir ON dir.idpersona = per.idpersona and dir.idInstitucion = col.idInstitucion and dir.fechabaja is null");
		sql.INNER_JOIN(
				"cen_direccion_tipodireccion tipodir ON tipodir.idpersona = per.idpersona and tipodir.idInstitucion = col.idInstitucion and dir.iddireccion = tipodir.iddireccion and tipodir.idtipodireccion = '2'");
		sql.INNER_JOIN(
				"cen_datoscolegialesestado dat ON dat.idPersona = per.idPersona and dat.idInstitucion = dir.idInstitucion and dat.fechaestado = (select max(datcol.fechaestado) from CEN_DATOSCOLEGIALESESTADO datcol where datcol.idpersona = dat.idpersona and datcol.idinstitucion = dat.idinstitucion)");
		sql.INNER_JOIN("cen_estadocolegial est ON est.idEstado = dat.idEstado");
		sql.INNER_JOIN("cen_institucion inst ON inst.idinstitucion = col.idinstitucion");
		sql.INNER_JOIN("gen_recursos_catalogos cat ON cat.idRecurso = est.descripcion and cat.idLenguaje = '"
				+ idLenguaje + "'");

		sql.WHERE("col.IDPERSONA = '" + idPersona + "'");
		return sql.toString();
	}

	public String selectDatosColegiales(String idPersona, String idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("CEN.NCOLEGIADO");
		sql.SELECT("SEG.NOMBRE");
		sql.SELECT("CEN.NMUTUALISTA");
		sql.SELECT("CEN.FECHAINCORPORACION");
		sql.SELECT("CEN.FECHAPRESENTACION");
		sql.SELECT("CEN.FECHAJURA");
		sql.SELECT("CEN.FECHATITULACION");
		sql.SELECT("CASE WHEN CEN.SITUACIONRESIDENTE = 1 THEN 'Si' ELSE 'No' END AS SITUACIONRESIDENTE");
		sql.SELECT("CASE WHEN CEN.COMUNITARIO = 1 THEN 'Si' ELSE 'No' END AS COMUNITARIO");
		sql.SELECT("EST.DESCRIPCION");
		sql.SELECT("DAT.OBSERVACIONES");

		sql.FROM("CEN_COLEGIADO CEN");
		sql.LEFT_OUTER_JOIN("CEN_TIPOSSEGURO SEG ON SEG.IDTIPOSSEGURO = CEN.IDTIPOSSEGURO");
		sql.INNER_JOIN(
				"CEN_DATOSCOLEGIALESESTADO DAT ON (DAT.IDPERSONA = CEN.IDPERSONA AND DAT.IDINSTITUCION = CEN.IDINSTITUCION)");
		sql.INNER_JOIN("CEN_ESTADOCOLEGIAL EST ON EST.IDESTADO = DAT.IDESTADO");

		sql.WHERE("CEN.IDPERSONA = '" + idPersona + "'");
		if (idInstitucion != "2000") {
			sql.WHERE("CEN.IDINSTITUCION = '" + idInstitucion + "'");
		}

		return sql.toString();
	}

	public String getLabelColegios(String idPersona) {
		SQL sql = new SQL();

		sql.SELECT("distinct col.IDINSTITUCION AS idInstitucion");
		sql.SELECT("inst.abreviatura as nombre");
		sql.SELECT("nvl(decode(nvl(col.comunitario,0),0, col.ncolegiado, col.ncomunitario), col.ncolegiado) as nColegiado");
		sql.FROM("CEN_COLEGIADO col");
		sql.INNER_JOIN("CEN_INSTITUCION inst ON inst.idInstitucion = col.idInstitucion");
		sql.WHERE("col.IDPERSONA = '" + idPersona + "'");
		return sql.toString();
	}
	
	public String selectColegiadosByIdPersona(Short idInstitucion, String idPersona) {

		SQL sql = new SQL();
		SQL sql1 = new SQL();

		sql.SELECT_DISTINCT("col.idpersona");
		sql.SELECT_DISTINCT("col.idinstitucion");
		sql.SELECT_DISTINCT("per.nifcif");
		sql.SELECT_DISTINCT("concat(per.nombre || ' ',concat(per.apellidos1 || ' ', per.apellidos2) ) AS nombre");
		sql.SELECT_DISTINCT("per.nombre as solonombre");
		sql.SELECT_DISTINCT("per.apellidos1");
		sql.SELECT_DISTINCT("per.apellidos2");
		sql.SELECT_DISTINCT("per.sexo");
		sql.SELECT_DISTINCT("per.idestadocivil");
		sql.SELECT_DISTINCT("cli.noaparecerredabogacia");

		sql.SELECT_DISTINCT("per.idtipoidentificacion");
		sql.SELECT_DISTINCT("per.naturalde");
		sql.SELECT("TO_CHAR(cli.fechaalta,'DD/MM/YYYY') AS fechaalta");
		sql.SELECT_DISTINCT("cli.idlenguaje");
		sql.SELECT_DISTINCT("cli.asientocontable");
		sql.SELECT_DISTINCT("col.nmutualista");
		sql.SELECT("TO_CHAR(col.fechaincorporacion,'DD/MM/YYYY') AS fechaincorporacion");
		sql.SELECT("TO_CHAR(col.fechajura,'DD/MM/YYYY') AS fechajura");
		sql.SELECT("TO_CHAR(col.fechatitulacion,'DD/MM/YYYY') AS fechatitulacion");
		sql.SELECT("TO_CHAR(col.fechapresentacion,'DD/MM/YYYY') AS fechapresentacion");
		sql.SELECT_DISTINCT("TO_CHAR(col.idtiposseguro) AS idTiposSeguro");

		sql.SELECT_DISTINCT("cli.comisiones");
		sql.SELECT_DISTINCT("cli.idtratamiento");
		sql.SELECT_DISTINCT("decode(col.comunitario,0, col.ncolegiado,col.ncomunitario) as numcolegiado");
		sql.SELECT_DISTINCT("colest.idestado as situacion");
		sql.SELECT_DISTINCT("cat.descripcion as estadoColegial");
		sql.SELECT_DISTINCT("col.situacionresidente as situacionresidente");
		sql.SELECT_DISTINCT("col.comunitario as comunitario");

		sql.SELECT_DISTINCT(
				"concat( decode(col.situacionresidente,0,'No', 'Sí')  || ' / ',decode(col.comunitario,0,'No', 'Sí')) as residenteInscrito");
		sql.SELECT("TO_CHAR(per.fechanacimiento,'DD/MM/YYYY') AS fechanacimiento");
		sql.SELECT_DISTINCT("dir.correoelectronico AS correo");
		sql.SELECT_DISTINCT("dir.telefono1 AS telefono");

		sql.SELECT_DISTINCT("inst.abreviatura as colegioResultado");
		
		
		sql1.SELECT("partidojudicial.nombre");
		sql1.FROM("cen_partidojudicial partidojudicial");
		sql1.INNER_JOIN("cen_poblaciones pob on pob.idpartido = partidojudicial.idpartido");
		sql1.INNER_JOIN("cen_direcciones direcciones on pob.idpoblacion = direcciones.idpoblacion");
		sql1.INNER_JOIN(
				"CEN_DIRECCION_TIPODIRECCION tipodireccion ON (tipodireccion.IDDIRECCION = direcciones.IDDIRECCION AND"
						+ " tipodireccion.IDPERSONA = direcciones.IDPERSONA AND  tipodireccion.IDINSTITUCION = direcciones.IDINSTITUCION)");
		
		sql1.WHERE("tipodireccion.idtipodireccion = '2'");
		sql1.WHERE("direcciones.idpersona = dir.idpersona");
		sql1.WHERE("direcciones.idinstitucion = dir.idinstitucion");
		sql1.WHERE("direcciones.iddireccion = dir.iddireccion");

		sql.SELECT_DISTINCT("(" + sql1 + ") as partidoJudicial");

		sql.SELECT_DISTINCT("dir.movil as movil");
		sql.FROM("cen_colegiado col");

		sql.INNER_JOIN("cen_persona per on col.idpersona = per.idpersona");
		sql.INNER_JOIN("cen_cliente cli on (col.idpersona = cli.idpersona and col.idinstitucion = cli.idinstitucion)");
		sql.INNER_JOIN("cen_institucion inst on col.idinstitucion = inst.idinstitucion");

		sql.LEFT_OUTER_JOIN("cen_gruposcliente_cliente grucli on \r\n"
				+ "    (grucli.idinstitucion = inst.idinstitucion and col.idpersona = grucli.idpersona and ((grucli.fecha_inicio <= SYSDATE OR grucli.fecha_inicio IS NULL ) and \r\n"
				+ "        ( grucli.fecha_baja > SYSDATE OR grucli.fecha_baja IS NULL)))");
		sql.INNER_JOIN(
				"CEN_DATOSCOLEGIALESESTADO colest on (col.idpersona = colest.idpersona and col.idinstitucion = colest.idinstitucion  and colest.fechaestado = (\r\n"
						+ "                                            select max(datcol.fechaestado) from CEN_DATOSCOLEGIALESESTADO datcol where datcol.idpersona = colest.idpersona and datcol.idinstitucion = colest.idinstitucion))");
		sql.INNER_JOIN("cen_estadocolegial estcol on (colest.idestado = estcol.idestado)");
		sql.INNER_JOIN("gen_recursos_catalogos cat on (estcol.descripcion = cat.idrecurso and cat.idlenguaje = '1')");
		sql.LEFT_OUTER_JOIN(
				"cen_direcciones dir on (cli.idpersona = dir.idpersona and cli.idinstitucion = dir.idinstitucion and inst.idinstitucion = dir.idinstitucion and dir.fechabaja is null)");

		sql.LEFT_OUTER_JOIN("CEN_DIRECCION_TIPODIRECCION TIPODIR ON (CLI.IDPERSONA = TIPODIR.IDPERSONA AND"  
	                + " DIR.IDDIRECCION = TIPODIR.IDDIRECCION AND CLI.IDINSTITUCION = TIPODIR.IDINSTITUCION AND "
	                + " INST.IDINSTITUCION = DIR.IDINSTITUCION)"); 
		sql.LEFT_OUTER_JOIN(
				"cen_datosCV datosCV ON ( datosCV.idInstitucion = col.idInstitucion and datosCV.idPersona = per.idPersona )");
		sql.LEFT_OUTER_JOIN("cen_tiposcv cenTipoCV ON ( cenTipoCV.idTipoCV = datosCV.idTipoCV )");
		sql.LEFT_OUTER_JOIN("cen_tiposcvsubtipo2 subt2 ON ( subt2.idTipoCV = datosCV.idTipoCV and subt2.idInstitucion = col.idInstitucion )");
		sql.LEFT_OUTER_JOIN("cen_tiposcvsubtipo1 subt1 ON ( subt1.idTipoCV = datosCV.idTipoCV and subt1.idInstitucion = col.idInstitucion )");

		if (idInstitucion != Short.parseShort("2000")) {
			sql.WHERE("COL.IDINSTITUCION = '" + idInstitucion + "'");
		}

		sql.WHERE("per.idtipoidentificacion not in '20'");

		sql.WHERE("per.idpersona = '" + idPersona + "'");

		return sql.toString();
	}
	
	public String selectColegiadosCensoGeneral(Short idInstitucion, ColegiadoItem colegiadoItem) {

		SQL sql = new SQL();
		SQL sql1 = new SQL();
		SQL sql2 = new SQL();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		sql.SELECT_DISTINCT("col.idpersona");
		sql.SELECT_DISTINCT("col.idinstitucion");
		sql.SELECT_DISTINCT("col.identificadords");
		sql.SELECT_DISTINCT("per.nifcif");
		sql.SELECT_DISTINCT("concat(per.nombre || ' ',concat(per.apellidos1 || ' ', per.apellidos2) ) AS nombre");
		sql.SELECT_DISTINCT("per.nombre as solonombre");
		sql.SELECT_DISTINCT("per.apellidos1");
		sql.SELECT_DISTINCT("per.apellidos2");
		sql.SELECT_DISTINCT("per.sexo");
		sql.SELECT_DISTINCT("per.idestadocivil");
		sql.SELECT_DISTINCT("cli.noaparecerredabogacia");

		sql.SELECT_DISTINCT("per.idtipoidentificacion");
		sql.SELECT_DISTINCT("per.naturalde");
		sql.SELECT("TO_CHAR(cli.fechaalta,'DD/MM/YYYY') AS fechaalta");
		sql.SELECT_DISTINCT("cli.idlenguaje");
		sql.SELECT_DISTINCT("cli.asientocontable");
		sql.SELECT_DISTINCT("col.nmutualista");
		sql.SELECT("TO_CHAR(col.fechaincorporacion,'DD/MM/YYYY') AS fechaincorporacion");
		sql.SELECT("TO_CHAR(col.fechajura,'DD/MM/YYYY') AS fechajura");
		sql.SELECT("TO_CHAR(col.fechatitulacion,'DD/MM/YYYY') AS fechatitulacion");
		sql.SELECT("TO_CHAR(col.fechapresentacion,'DD/MM/YYYY') AS fechapresentacion");
		sql.SELECT_DISTINCT("TO_CHAR(col.idtiposseguro) AS idTiposSeguro");

		sql.SELECT_DISTINCT("cli.comisiones");
		sql.SELECT_DISTINCT("cli.idtratamiento");
		sql.SELECT_DISTINCT("nvl(decode(nvl(col.comunitario,0),0, col.ncolegiado, col.ncomunitario), col.ncolegiado) as numcolegiado");
		sql.SELECT_DISTINCT("colest.idestado as situacion");
		sql.SELECT_DISTINCT("cat.descripcion as estadoColegial");
		sql.SELECT_DISTINCT("col.situacionresidente as situacionresidente");
		sql.SELECT_DISTINCT("col.comunitario as comunitario");

		sql.SELECT_DISTINCT(
				"concat( decode(col.situacionresidente,0,'No', 'Sí')  || ' / ',decode(col.comunitario,0,'No', 'Sí')) as residenteInscrito");
		sql.SELECT("TO_CHAR(per.fechanacimiento,'DD/MM/YYYY') AS fechanacimiento");
		sql.SELECT("inst.abreviatura as colegioResultado");
		
		sql1.SELECT("partidojudicial.nombre");		
		sql1.FROM("cen_partidojudicial partidojudicial");
		sql1.INNER_JOIN("cen_poblaciones pob on pob.idpartido = partidojudicial.idpartido");
		sql1.INNER_JOIN("cen_direcciones direcciones on pob.idpoblacion = direcciones.idpoblacion");
		sql1.INNER_JOIN(
				"CEN_DIRECCION_TIPODIRECCION tipodireccion ON (tipodireccion.IDDIRECCION = direcciones.IDDIRECCION AND"
						+ " tipodireccion.IDPERSONA = direcciones.IDPERSONA AND  tipodireccion.IDINSTITUCION = direcciones.IDINSTITUCION)");

		sql1.WHERE("tipodireccion.idtipodireccion = '2'");
		sql1.WHERE("direcciones.idpersona = dir.idpersona");
		sql1.WHERE("direcciones.idinstitucion = dir.idinstitucion");
		sql1.WHERE("direcciones.iddireccion = dir.iddireccion");

		sql.SELECT_DISTINCT("(" + sql1 + ") as partidoJudicial");

		sql.SELECT_DISTINCT("decode(TIPODIR.idtipodireccion ,3,dir.correoelectronico,'') AS correo");
		sql.SELECT_DISTINCT("decode(TIPODIR.idtipodireccion ,3,dir.telefono1,'') AS telefono");
		sql.SELECT_DISTINCT("decode(TIPODIR.idtipodireccion ,3,dir.movil,'') as movil");
		sql.SELECT_DISTINCT("ROW_NUMBER() OVER(PARTITION BY concat(col.idpersona,col.idinstitucion) ORDER BY col.idpersona) AS RN");
		sql.FROM("cen_colegiado col");

		sql.INNER_JOIN("cen_persona per on col.idpersona = per.idpersona");
		sql.INNER_JOIN("cen_cliente cli on (col.idpersona = cli.idpersona and cli.idinstitucion =  '"+ idInstitucion + "')");
		
		sql.INNER_JOIN("cen_institucion inst on col.idinstitucion = inst.idinstitucion");

		sql.LEFT_OUTER_JOIN("cen_gruposcliente_cliente grucli on \r\n"
				+ "    ((grucli.idinstitucion = inst.idinstitucion or grucli.idinstitucion = '2000') and col.idpersona = grucli.idpersona and ((grucli.fecha_inicio <= SYSDATE OR grucli.fecha_inicio IS NULL ) and \r\n"
				+ "        ( grucli.fecha_baja > SYSDATE OR grucli.fecha_baja IS NULL)))");
		sql.INNER_JOIN(
				"CEN_DATOSCOLEGIALESESTADO colest on (col.idpersona = colest.idpersona and col.idinstitucion = colest.idinstitucion  and colest.fechaestado = (\r\n"
						+ "                                            select max(datcol.fechaestado) from CEN_DATOSCOLEGIALESESTADO datcol where datcol.idpersona = colest.idpersona and datcol.idinstitucion = colest.idinstitucion))");
		sql.INNER_JOIN("cen_estadocolegial estcol on (colest.idestado = estcol.idestado)");
		sql.INNER_JOIN("gen_recursos_catalogos cat on (estcol.descripcion = cat.idrecurso and cat.idlenguaje = '1')");
		sql.LEFT_OUTER_JOIN(
				"cen_direcciones dir on (cli.idpersona = dir.idpersona and cli.idinstitucion = dir.idinstitucion and inst.idinstitucion = dir.idinstitucion and dir.fechabaja is null)");

		sql.LEFT_OUTER_JOIN("CEN_DIRECCION_TIPODIRECCION TIPODIR ON (CLI.IDPERSONA = TIPODIR.IDPERSONA AND"  
	                + " DIR.IDDIRECCION = TIPODIR.IDDIRECCION AND CLI.IDINSTITUCION = TIPODIR.IDINSTITUCION AND "
	                + " INST.IDINSTITUCION = DIR.IDINSTITUCION)"); 
		sql.LEFT_OUTER_JOIN(
				"cen_datosCV datosCV ON ( datosCV.idInstitucion = col.idInstitucion and datosCV.idPersona = per.idPersona )");
		sql.LEFT_OUTER_JOIN("cen_tiposcv cenTipoCV ON ( cenTipoCV.idTipoCV = datosCV.idTipoCV )");
		sql.LEFT_OUTER_JOIN("cen_tiposcvsubtipo2 subt2 ON ( subt2.idTipoCV = datosCV.idTipoCV and subt2.idInstitucion = col.idInstitucion )");
		sql.LEFT_OUTER_JOIN("cen_tiposcvsubtipo1 subt1 ON ( subt1.idTipoCV = datosCV.idTipoCV and subt1.idInstitucion = col.idInstitucion )");

		if(idInstitucion != null && (idInstitucion.equals(SigaConstants.IDINSTITUCION_2000) 
				|| idInstitucion.equals(SigaConstants.IDINSTITUCION_3500))) {
			sql.WHERE("COL.IDINSTITUCION = '" + colegiadoItem.getIdInstitucion() +"'");
		} else {
			//Colegio
			if (idInstitucion > Short.parseShort("2001") && idInstitucion < Short.parseShort("2100")) {
				sql.WHERE("COL.IDINSTITUCION = '" + idInstitucion + "'");
			//Consejo
			} else {
				sql.WHERE("inst.cen_inst_IDINSTITUCION = '" + idInstitucion + "'");

			}
				
		}
		
		sql.WHERE("per.idtipoidentificacion not in '20'");

		if (colegiadoItem.getNif() != null && colegiadoItem.getNif() != "") {
			sql.WHERE("upper(per.nifcif) like upper('%" + colegiadoItem.getNif() + "%')");
		}

		if (colegiadoItem.getNombre() != null && colegiadoItem.getNombre() != "") {
			String columna = "per.nombre";
			String cadena = colegiadoItem.getNombre();
			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
//			sql.WHERE("upper(per.nombre) like upper('%" + colegiadoItem.getNombre() + "%')");
		}

		if (colegiadoItem.getApellidos() != null && colegiadoItem.getApellidos() != "") {
			String columna = "CONCAT(per.apellidos1,per.apellidos2)";
			String cadena = colegiadoItem.getApellidos().replaceAll("\\s+","");
			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
			
//			sql.WHERE("UPPER(CONCAT(per.apellidos1,per.apellidos2)) LIKE UPPER('%" +colegiadoItem.getApellidos().replaceAll("\\s+","")
//					+ "%')");
		}
		
		if (colegiadoItem.getNumColegiado() != null && colegiadoItem.getNumColegiado() != "") {
			sql.WHERE("(decode(col.comunitario,1,col.ncomunitario,col.ncolegiado) = '" + colegiadoItem.getNumColegiado() + "')");
		}

		if (colegiadoItem.getSexo() != null && colegiadoItem.getSexo() != "") {
			sql.WHERE("per.sexo = '" + colegiadoItem.getSexo() + "'");
		}

		if (colegiadoItem.getCodigoPostal() != null && colegiadoItem.getCodigoPostal() != "") {
			sql.WHERE("dir.codigopostal ='" + colegiadoItem.getCodigoPostal() + "'");
		}

		if (colegiadoItem.getTipoDireccion() != null && colegiadoItem.getTipoDireccion() != "") {
			sql.WHERE("tipodir.idtipodireccion = "+ colegiadoItem.getTipoDireccion());
		}else {
			/*sql.WHERE("(tipodir.idtipodireccion = 2 OR 2 NOT IN (SELECT idtipodireccion FROM CEN_DIRECCION_TIPODIRECCION TIPODIR2 "
					+ "WHERE TIPODIR.IDPERSONA = TIPODIR2.IDPERSONA  AND TIPODIR.IDINSTITUCION = TIPODIR2.IDINSTITUCION ))");*/
		}

		if (colegiadoItem.getIdEstadoCivil() != null && colegiadoItem.getIdEstadoCivil() != "") {
			sql.WHERE("per.idestadocivil = '" + colegiadoItem.getIdEstadoCivil() + "'");
		}

		if (colegiadoItem.getIdProvincia() != null && colegiadoItem.getIdProvincia() != "") {
			sql.WHERE("dir.idprovincia = '" + colegiadoItem.getIdProvincia() + "'");
		}

		if (colegiadoItem.getIdPoblacion() != null && colegiadoItem.getIdPoblacion() != "") {
			sql.WHERE("dir.idpoblacion = '" + colegiadoItem.getIdPoblacion() + "'");
		}

		if (colegiadoItem.getDomicilio() != null && colegiadoItem.getDomicilio() != "") {
			sql.WHERE("(dir.domicilio) like upper('" + colegiadoItem.getDomicilio() + "')");
		}

		if (colegiadoItem.getCorreo() != null && colegiadoItem.getCorreo() != "") {
			String columna = "dir.correoelectronico";
			String cadena = colegiadoItem.getCorreo();
			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
			
//			sql.WHERE("upper(dir.correoelectronico) LIKE upper('%" + colegiadoItem.getCorreo() + "%')");
		}

		if (colegiadoItem.getTelefono() != null && colegiadoItem.getTelefono() != "") {
			sql.WHERE("dir.telefono1 like '%" + colegiadoItem.getTelefono() + "%'");
		}

		if (colegiadoItem.getMovil() != null && colegiadoItem.getMovil() != "") {
			sql.WHERE("dir.movil like '%" + colegiadoItem.getMovil() + "%'");
		}

		if (colegiadoItem.getTipoCV() != null && colegiadoItem.getTipoCV() != "") {
			sql.WHERE("datoscv.idtipocv = '" + colegiadoItem.getTipoCV() + "'");
		}

		if (colegiadoItem.getSubTipoCV1() != null && colegiadoItem.getSubTipoCV1() != "") {
			sql.WHERE("datoscv.idtipocvsubtipo1 = '" + colegiadoItem.getSubTipoCV1() + "'");
		}
		
		if (colegiadoItem.getSubTipoCV2() != null && colegiadoItem.getSubTipoCV2() != "") {
			sql.WHERE("datoscv.idtipocvsubtipo2 = '" + colegiadoItem.getSubTipoCV2() + "'");
		}

		if (colegiadoItem.getSituacion() != null && colegiadoItem.getSituacion() != "") {
			sql.WHERE("colest.idestado ='" + colegiadoItem.getSituacion() + "'");
		}

		if (colegiadoItem.getResidencia() != null && colegiadoItem.getResidencia() != "") {
			sql.WHERE("col.situacionresidente ='" + colegiadoItem.getResidencia() + "'");
		}

		if (colegiadoItem.getInscrito() != null && colegiadoItem.getInscrito() != "") {
			sql.WHERE("col.comunitario ='" + colegiadoItem.getInscrito() + "'");
		}

		if (colegiadoItem.getIdgrupo() != null && colegiadoItem.getIdgrupo().length > 0) {

			String etiquetas = "";

			for (int i = 0; colegiadoItem.getIdgrupo().length > i; i++) {

				if (i == colegiadoItem.getIdgrupo().length - 1) {
					etiquetas += "'" + colegiadoItem.getIdgrupo()[i] + "'";
				} else {
					etiquetas += "'" + colegiadoItem.getIdgrupo()[i] + "',";
				}
			}

			sql.WHERE("grucli.IDGRUPO IN (" + etiquetas + ")");
		}

		if (colegiadoItem.getFechaIncorporacion() != null && colegiadoItem.getFechaIncorporacion().length != 0) {

			if (colegiadoItem.getFechaIncorporacion()[0] != null && colegiadoItem.getFechaIncorporacion()[1] != null) {

				String fechaIncorporacionDesde = dateFormat.format(colegiadoItem.getFechaIncorporacion()[0]);
				String fechaIncorporacionHasta = dateFormat.format(colegiadoItem.getFechaIncorporacion()[1]);

				sql.WHERE("(col.fechaincorporacion >= TO_DATE('" + fechaIncorporacionDesde
						+ "','DD/MM/YYYY') " + " and ( col.fechaincorporacion <= TO_DATE('"
						+ fechaIncorporacionHasta + "','DD/MM/YYYY')))");

			} else if (colegiadoItem.getFechaIncorporacion()[0] != null
					&& colegiadoItem.getFechaIncorporacion()[1] == null) {

				String fechaIncorporacionDesde = dateFormat.format(colegiadoItem.getFechaIncorporacion()[0]);

				sql.WHERE("(col.fechaincorporacion >= TO_DATE('" + fechaIncorporacionDesde
						+ "','DD/MM/YYYY'))");

			} else if (colegiadoItem.getFechaIncorporacion()[0] == null
					&& colegiadoItem.getFechaIncorporacion()[1] != null) {

				String fechaIncorporacionHasta = dateFormat.format(colegiadoItem.getFechaIncorporacion()[1]);

				sql.WHERE("( col.fechaincorporacion <= TO_DATE('" + fechaIncorporacionHasta
						+ "','DD/MM/YYYY'))");
			}
		}

		if (colegiadoItem.getEstadoColegial() != null && colegiadoItem.getEstadoColegial() != "") {
			sql.WHERE("cat.descripcion like '" + colegiadoItem.getEstadoColegial() + "'");
		}
		
		if (colegiadoItem.getFechaNacimientoRango() != null && colegiadoItem.getFechaNacimientoRango().length != 0) {

			if (colegiadoItem.getFechaNacimientoRango()[0] != null && colegiadoItem.getFechaNacimientoRango()[1] != null) {

				String getFechaNacimientoDesde = dateFormat.format(colegiadoItem.getFechaNacimientoRango()[0]);
				String getFechaNacimientoHasta = dateFormat.format(colegiadoItem.getFechaNacimientoRango()[1]);

				sql.WHERE("(per.fechanacimiento >= TO_DATE('" + getFechaNacimientoDesde
						+ "','DD/MM/YYYY') " + " and ( per.fechanacimiento <= TO_DATE('"
						+ getFechaNacimientoHasta + "','DD/MM/YYYY')))");

			} else if (colegiadoItem.getFechaNacimientoRango()[0] != null
					&& colegiadoItem.getFechaNacimientoRango()[1] == null) {

				String getFechaNacimientoDesde = dateFormat.format(colegiadoItem.getFechaNacimientoRango()[0]);

				sql.WHERE("(per.fechanacimiento >= TO_DATE('" + getFechaNacimientoDesde
						+ "','DD/MM/YYYY'))");

			} else if (colegiadoItem.getFechaNacimientoRango()[0] == null
					&& colegiadoItem.getFechaNacimientoRango()[1] != null) {

				String getFechaNacimientoHasta = dateFormat.format(colegiadoItem.getFechaNacimientoRango()[1]);

				sql.WHERE("( per.fechanacimiento <= TO_DATE('" + getFechaNacimientoHasta
						+ "','DD/MM/YYYY'))");
			}
		}

		sql.ORDER_BY("NOMBRE,CORREO,TELEFONO");

		sql2.SELECT("*");
		sql2.FROM("(" + sql + ")");
		sql2.WHERE("RN = 1 and rownum < 5000");

		return sql2.toString();
	}
	public String selectColegiacionActual(Short idInstitucion, String idLenguaje, ColegiadoItem colegiadoItem) {

		SQL sql = new SQL();

		sql.SELECT("TO_CHAR(fechaincorporacion,'DD/MM/YYYY') AS fechaincorporacion");
		sql.SELECT("cat.descripcion as estadoColegial");
		sql.SELECT("decode (col.situacionresidente, 1, 'Si', 0, 'No') as residenteInscrito");
		sql.SELECT("decode (colest.situacionresidente, 1, 'Si', 0, 'No') as situacionResidente");
		sql.SELECT("observaciones");
		sql.SELECT("TO_CHAR(fechaestado,'DD/MM/YYYY') AS fechaestado");
		sql.SELECT("fechaestado AS fechaestadodate");
		sql.SELECT("colest.idestado AS idEstado");

		sql.FROM("cen_colegiado col");
//		sql.INNER_JOIN(
//				"CEN_DATOSCOLEGIALESESTADO colest on (col.idpersona = colest.idpersona and col.idinstitucion = colest.idinstitucion )");		
		sql.INNER_JOIN(
				"CEN_DATOSCOLEGIALESESTADO colest on (col.idpersona = colest.idpersona and col.idinstitucion = colest.idinstitucion  and colest.fechaestado = (\r\n"
						+ "                                            select max(datcol.fechaestado) from CEN_DATOSCOLEGIALESESTADO datcol where datcol.idpersona = colest.idpersona and datcol.idinstitucion = colest.idinstitucion"
						+ " and datcol.fechaestado < sysdate))");
		
		sql.INNER_JOIN("cen_estadocolegial estcol on (colest.idestado = estcol.idestado)");
		sql.INNER_JOIN("gen_recursos_catalogos cat on (estcol.descripcion = cat.idrecurso and cat.idlenguaje = '"
				+ idLenguaje + "')");

		sql.WHERE("col.idpersona = '" + colegiadoItem.getIdPersona() + "'");

		if (idInstitucion != Short.parseShort("2000")) {
			sql.WHERE("colest.idinstitucion = '" + idInstitucion + "'");
		}
		// sql1.WHERE("dir.fechabaja is null");

		sql.ORDER_BY("fechaestadodate desc");
		// sql.ORDER_BY("per.nombre");

		return sql.toString();
	}
	public String busquedaColegiadosSJCS(String idInstitucion, ColegiadosSJCSItem colegiadosSJCSItem) {
		SQL sql = new SQL();
		sql.SELECT("COLEGIADO.IDPERSONA,INSTITUCION.ABREVIATURA,COLEGIADO.idinstitucion");
		sql.SELECT("DECODE(COLEGIADO.COMUNITARIO,0,COLEGIADO.NCOLEGIADO,COLEGIADO.NCOMUNITARIO) AS NCOLEGIADO");
		sql.SELECT("PERSONA.NIFCIF");
		sql.SELECT("RECURSO.DESCRIPCION");
		sql.SELECT("(PERSONA.APELLIDOS1  || ' ' || PERSONA.APELLIDOS2 || ' ' || PERSONA.NOMBRE) AS NOMBRE");
		sql.SELECT("DECODE(COUNT(INSCRIPCIONTURNO.IDTURNO),0,'NO','SI') AS INSCRITOTURNO");
		sql.SELECT("DECODE(COUNT(INSCRIPCIONGUARDIA.IDTURNO),0,'NO','SI') AS INSCRITOGUARDIA");
		sql.SELECT("DIRECCION.TELEFONO1 AS TELEFONO");
		sql.SELECT("(Select count(*)      From Scs_Cabeceraguardias Cab Where Cab.Idinstitucion = COLEGIADO.idinstitucion and cab.idpersona = COLEGIADO.idpersona and Cab.Fecha_Fin >= Sysdate) as GUARDIASPENDIENTES");
		sql.FROM("CEN_COLEGIADO COLEGIADO");
		sql.INNER_JOIN("CEN_INSTITUCION INSTITUCION ON COLEGIADO.IDINSTITUCION = INSTITUCION.IDINSTITUCION");
		sql.INNER_JOIN("CEN_CLIENTE CLIENTE ON CLIENTE.IDPERSONA = COLEGIADO.IDPERSONA AND COLEGIADO.IDINSTITUCION = CLIENTE.IDINSTITUCION");
		sql.INNER_JOIN("CEN_PERSONA PERSONA ON PERSONA.IDPERSONA = CLIENTE.IDPERSONA");
		sql.INNER_JOIN("CEN_DATOSCOLEGIALESESTADO ESTADOCOLEGIAL ON ESTADOCOLEGIAL.IDPERSONA = COLEGIADO.IDPERSONA AND COLEGIADO.IDINSTITUCION = ESTADOCOLEGIAL.IDINSTITUCION AND  ESTADOCOLEGIAL.FECHAESTADO = (SELECT MAX(FECHAESTADO) FROM CEN_DATOSCOLEGIALESESTADO ESTADOCOLEGIAL2 WHERE ESTADOCOLEGIAL2.IDPERSONA = COLEGIADO.IDPERSONA AND COLEGIADO.IDINSTITUCION = ESTADOCOLEGIAL2.IDINSTITUCION)");
		sql.INNER_JOIN("CEN_ESTADOCOLEGIAL ESTADO ON ESTADO.IDESTADO = ESTADOCOLEGIAL.IDESTADO");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS RECURSO ON ESTADO.DESCRIPCION = RECURSO.IDRECURSO AND RECURSO.IDLENGUAJE = 1");
		sql.LEFT_OUTER_JOIN("(SELECT DIRECCIONES.IDPERSONA,DIRECCIONES.IDINSTITUCION,DIRECCIONES.TELEFONO1 FROM CEN_DIRECCIONES DIRECCIONES INNER JOIN CEN_DIRECCION_TIPODIRECCION TIPODIRECCION ON  TIPODIRECCION.IDPERSONA = DIRECCIONES.IDPERSONA AND DIRECCIONES.IDINSTITUCION = TIPODIRECCION.IDINSTITUCION AND TIPODIRECCION.IDDIRECCION = DIRECCIONES.IDDIRECCION AND TIPODIRECCION.IDTIPODIRECCION = 6 WHERE DIRECCIONES.FECHABAJA IS NULL AND DIRECCIONES.IDINSTITUCION = 2005) direccion on DIRECCION.IDPERSONA = COLEGIADO.IDPERSONA AND COLEGIADO.IDINSTITUCION = DIRECCION.IDINSTITUCION");
		sql.LEFT_OUTER_JOIN("scs_inscripcionturno INSCRIPCIONTURNO ON INSCRIPCIONTURNO.IDPERSONA = COLEGIADO.IDPERSONA AND COLEGIADO.IDINSTITUCION = INSCRIPCIONTURNO.IDINSTITUCION");
		sql.LEFT_OUTER_JOIN("SCS_INSCRIPCIONGUARDIA INSCRIPCIONGUARDIA ON INSCRIPCIONGUARDIA.IDPERSONA = COLEGIADO.IDPERSONA AND COLEGIADO.IDINSTITUCION = INSCRIPCIONGUARDIA.IDINSTITUCION");
		sql.WHERE("COLEGIADO.IDINSTITUCION = '"+idInstitucion+"'");
		if(colegiadosSJCSItem.getnColegiado() != null && colegiadosSJCSItem.getnColegiado() != "")sql.WHERE("NVL(COLEGIADO.NCOLEGIADO, COLEGIADO.NCOMUNITARIO) = '"+ colegiadosSJCSItem.getnColegiado()+"'");			
		if(colegiadosSJCSItem.getIdTurno() != null) { 
			sql.WHERE("INSCRIPCIONTURNO.IDTURNO = '"+colegiadosSJCSItem.getIdTurno()+"'");
			if(colegiadosSJCSItem.getIdGuardia() != null)sql.WHERE("(INSCRIPCIONGUARDIA.IDTURNO = '"+colegiadosSJCSItem.getIdTurno()+"'  AND INSCRIPCIONGUARDIA.IDGUARDIA = '"+colegiadosSJCSItem.getIdGuardia()+"')");
		}
		if(colegiadosSJCSItem.getIdEstado() != null) sql.WHERE("ESTADOCOLEGIAL.IDESTADO = '"+colegiadosSJCSItem.getIdEstado()+"'");
		if(colegiadosSJCSItem.getFechaestado() != null) sql.WHERE("ESTADOCOLEGIAL.FECHAESTADO = '"+colegiadosSJCSItem.getFechaestado()+"'");
		if(colegiadosSJCSItem.getNif() != null && colegiadosSJCSItem.getNif() != "") sql.WHERE("PERSONA.NIFCIF =  '"+colegiadosSJCSItem.getNif()+"'");
		if (colegiadosSJCSItem.getApellidos() != null && colegiadosSJCSItem.getApellidos() != ""){ 
			String columna = "REPLACE(CONCAT(PERSONA.apellidos1,PERSONA.apellidos2), ' ', '')";
			String cadena = colegiadosSJCSItem.getApellidos();
			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));	
		}
		if (colegiadosSJCSItem.getNombre() != null && colegiadosSJCSItem.getNombre() != "") {
			String columna = "PERSONA.NOMBRE";
			String cadena = colegiadosSJCSItem.getNombre();
			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));	
		}
		sql.GROUP_BY("COLEGIADO.IDPERSONA,INSTITUCION.ABREVIATURA,COLEGIADO.idinstitucion, COLEGIADO.COMUNITARIO,COLEGIADO.NCOLEGIADO,COLEGIADO.NCOMUNITARIO, PERSONA.NIFCIF,RECURSO.DESCRIPCION, PERSONA.APELLIDOS1  ,PERSONA.APELLIDOS2 ,PERSONA.NOMBRE,DIRECCION.TELEFONO1");
		return sql.toString();
	}	
	
	public String selectCuentaContableSJCS(Short idInstitucion, ColegiadoItem colegiadoItem) {

		SQL sql = new SQL();

		sql.SELECT("IDPERSONA");
		sql.SELECT("IDINSTITUCION");
		sql.SELECT("NVL(CUENTACONTABLESJCS,' ') AS cuentaContable");

		sql.FROM("cen_colegiado");

		sql.WHERE("idpersona = '" + colegiadoItem.getIdPersona() + "'");
		sql.WHERE("idinstitucion = '" + idInstitucion + "'");

		return sql.toString();
	}
}
