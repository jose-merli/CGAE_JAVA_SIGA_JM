package org.itcgae.siga.db.services.cen.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesSearchDTO;
import org.itcgae.siga.DTOs.cen.TarjetaIntegrantesCreateDTO;
import org.itcgae.siga.DTOs.cen.TarjetaIntegrantesUpdateDTO;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.CenComponentesSqlProvider;

public class CenComponentesSqlExtendsProvider extends CenComponentesSqlProvider{

	public String selectIntegrantesWS(DatosIntegrantesSearchDTO integrantesSearchDTO, String idInstitucion) {
		
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("COMPONENTE.IDINSTITUCION idInstitucionSociedad");
		sql.SELECT("COMPONENTE.IDPERSONA idSociedad");
		sql.SELECT("COMPONENTE.IDCOMPONENTE");
		sql.SELECT("COMPONENTE.FECHAMODIFICACION FECHAMODIFICACION");
		sql.SELECT("DECODE (PERSONA.IDTIPOIDENTIFICACION, 10, 'NIF', 40, 'NIE', 20, 'CIF', NULL) AS TIPOIDENTIFICACION");
		sql.SELECT("PERSONA.NIFCIF");
		sql.SELECT("PERSONA.NOMBRE");
		sql.SELECT("DECODE(PERSONA.APELLIDOS1,'#NA','',PERSONA.APELLIDOS1) AS APELLIDOS1");
		sql.SELECT("PERSONA.APELLIDOS2");
		sql.SELECT("DECODE(PERSONA.IDTIPOIDENTIFICACION,'20','1','0') AS PERSONAJURIDICA");
		sql.SELECT("DECODE(f_siga_gettipocliente(COMPONENTE.CEN_CLIENTE_IDPERSONA,COMPONENTE.CEN_CLIENTE_IDINSTITUCION,SYSDATE),20,1,0) AS profesionalAbogado");
		sql.SELECT("DECODE(DECODE(f_siga_gettipocliente(COMPONENTE.CEN_CLIENTE_IDPERSONA,COMPONENTE.CEN_CLIENTE_IDINSTITUCION,SYSDATE),20,1,0), 1, 0, DECODE(ACTIVIDAD.descripcion, NULL, 0, 1)) AS PROFESIONAL");
		sql.SELECT("f_siga_getrecurso(ACTIVIDAD.descripcion, 1) AS  profesion");
		sql.SELECT("decode(COMPONENTE.IDTIPOCOLEGIO, 1, INST.CODIGOEXT, null) as codigocolegio");
		sql.SELECT("decode(COMPONENTE.IDTIPOCOLEGIO, 1,  INST.NOMBRE, PROVINCIAS.NOMBRE) as descripcionColegio");
		sql.SELECT("COMPONENTE.NUMCOLEGIADO");
		sql.SELECT("COMPONENTE.FLAG_SOCIO AS SOCIO");
		sql.SELECT("f_siga_getrecurso(CARGO.descripcion, 1) cargo");
		sql.SELECT("COMPONENTE.CARGO descripcionCargo");
		sql.SELECT("TO_CHAR(COMPONENTE.FECHACARGO, 'dd/mm/yyyy') AS FECHACARGO");
		sql.SELECT("COMPONENTE.FECHABAJA AS FECHABAJACARGO");
		sql.SELECT("DECODE(COMPONENTE.SOCIEDAD,1,'SI','NO') AS SOCIEDAD");
		sql.SELECT("PERSONA.IDPERSONA AS IDPERSONA");
		sql.SELECT("COMPONENTE.CEN_CLIENTE_IDINSTITUCION AS IDINSTITUCIONCLIENTE");
		sql.SELECT("INSTCLIENTE.CODIGOEXT AS CODIGOCOLEGIOCLIENTE");
		sql.SELECT("INSTCLIENTE.NOMBRE AS DESCRIPCIONCOLEGIOCLIENTE");
		
		sql.FROM("CEN_COMPONENTES COMPONENTE");
		sql.INNER_JOIN("CEN_PERSONA PERSONA ON PERSONA.IDPERSONA = COMPONENTE.CEN_CLIENTE_IDPERSONA");
		sql.LEFT_OUTER_JOIN("CEN_INSTITUCION INST ON COMPONENTE.IDINSTITUCION = INST.IDINSTITUCION");
		sql.LEFT_OUTER_JOIN("CEN_CARGO CARGO ON CARGO.IDCARGO=COMPONENTE.IDCARGO");
		sql.LEFT_OUTER_JOIN("CEN_ACTIVIDADPROFESIONAL ACTIVIDAD ON (ACTIVIDAD.IDACTIVIDADPROFESIONAL = COMPONENTE.IDTIPOCOLEGIO)");
		sql.LEFT_OUTER_JOIN("CEN_PROVINCIAS PROVINCIAS ON PROVINCIAS.CODIGOEXT = COMPONENTE.IDPROVINCIA");
		sql.LEFT_OUTER_JOIN("CEN_INSTITUCION INSTCLIENTE ON COMPONENTE.CEN_CLIENTE_IDINSTITUCION = INSTCLIENTE.IDINSTITUCION");
		
		sql.WHERE("COMPONENTE.FECHABAJA is null");
		
		if (!UtilidadesString.esCadenaVacia(integrantesSearchDTO.getIdPersona())) {
			sql.WHERE("COMPONENTE.IDPERSONA = '" + integrantesSearchDTO.getIdPersona() + "'");
		}
		if (!UtilidadesString.esCadenaVacia(idInstitucion)) {
			sql.WHERE("COMPONENTE.idinstitucion = '" + idInstitucion + "'");
		}
		
		sql.ORDER_BY("COMPONENTE.IDCOMPONENTE");
		
		return sql.toString();
	}
	public String selectIntegrantes(DatosIntegrantesSearchDTO integrantesSearchDTO, String idInstitucion) {
		
		SQL sql = new SQL();
		SQL sql1 = new SQL();
		SQL sql2 = new SQL();
				
        sql.SELECT_DISTINCT("COMPONENTE.IDINSTITUCION");
        sql.SELECT("COMPONENTE.IDPERSONA");
		sql.SELECT("COMPONENTE.IDCOMPONENTE");
		sql.SELECT("RECURSOCARGO.DESCRIPCION as CARGO");
		sql.SELECT("TO_CHAR(COMPONENTE.FECHACARGO, 'dd/mm/yyyy') AS FECHACARGO");
		sql.SELECT("TO_CHAR(COMPONENTE.FECHABAJA, 'dd/mm/yyyy') AS FECHABAJACARGO");
		sql.SELECT("COMPONENTE.CEN_CLIENTE_IDPERSONA AS IDPERSONACOMPONENTE");
		sql.SELECT("DECODE(COMPONENTE.SOCIEDAD,1,'SI','NO') AS SOCIEDAD");
		sql.SELECT("COMPONENTE.CAPITALSOCIAL");
		sql.SELECT("TO_CHAR(COMPONENTE.FECHACARGO, 'dd/mm/yyyy') AS FECHACARGOINFORME");
		//sql.SELECT("DECODE(f_siga_gettipocliente(COMPONENTE.CEN_CLIENTE_IDPERSONA,COMPONENTE.CEN_CLIENTE_IDINSTITUCION,SYSDATE),20,'Ejerciente','') AS EJERCIENTE");
		sql.SELECT("NVL(CAT.DESCRIPCION, DECODE(PERSONA.IDTIPOIDENTIFICACION,20,'SOCIEDAD','NO COLEGIADO')) AS EJERCIENTE");
		sql.SELECT("DECODE(TO_CHAR(COMPONENTE.FECHABAJA, 'dd/mm/yyyy'), NULL,TO_CHAR(COMPONENTE.FECHACARGO, 'dd/mm/yyyy'),TO_CHAR(COMPONENTE.FECHACARGO, 'dd/mm/yyyy') || ' -(' || TO_CHAR(COMPONENTE.FECHABAJA, 'dd/mm/yyyy') || ') ' ) AS FECHA_HISTORICO");
		sql.SELECT("PERSONA.NIFCIF");
		sql.SELECT("PERSONA.NOMBRE");
		sql.SELECT("DECODE(PERSONA.APELLIDOS1,'#NA','',PERSONA.APELLIDOS1) AS APELLIDOS1");
		sql.SELECT("PERSONA.APELLIDOS2");
		sql.SELECT("CONCAT(PERSONA.APELLIDOS1 || ' ',PERSONA.APELLIDOS2) as APELLIDOS" );
		sql.SELECT("CONCAT(PERSONA.NOMBRE ||' ',CONCAT(DECODE(PERSONA.APELLIDOS1,'#NA','',PERSONA.APELLIDOS1) || ' ',PERSONA.APELLIDOS2)) AS NOMBRECOMPLETO");
		sql.SELECT("COMPONENTE.IDTIPOCOLEGIO");
		sql.SELECT("COMPONENTE.NUMCOLEGIADO");
		sql.SELECT("COMPONENTE.IDCARGO");
		sql.SELECT("COMPONENTE.IDPROVINCIA");
		sql.SELECT("COMPONENTE.FLAG_SOCIO");
		sql.SELECT("COMPONENTE.CEN_CLIENTE_IDINSTITUCION");
		sql.SELECT("COMPONENTE.CARGO as DESCRIPCIONCARGO");
		sql.SELECT("INST.CODIGOEXT AS COLEGIO");
		sql.SELECT("INST.abreviatura AS NOMBRECOLEGIO");
		sql.SELECT("RECURSOACTIVIDAD.DESCRIPCION AS DESCRIPCIONPROFESION");
		sql.SELECT("DECODE(PERSONA.IDTIPOIDENTIFICACION,'20','1','0') AS PERSONAJURIDICA");
		sql.SELECT("CLIENTE.LETRADO AS ABOGADO");
		sql.FROM("CEN_COMPONENTES COMPONENTE");
		sql.INNER_JOIN(" CEN_PERSONA PERSONA ON PERSONA.IDPERSONA = COMPONENTE.CEN_CLIENTE_IDPERSONA ");
		sql.INNER_JOIN(" CEN_CLIENTE CLIENTE ON CLIENTE.IDPERSONA = COMPONENTE.CEN_CLIENTE_IDPERSONA AND COMPONENTE.CEN_CLIENTE_IDINSTITUCION = CLIENTE.IDINSTITUCION");
		sql.INNER_JOIN(" CEN_INSTITUCION INST ON COMPONENTE.IDINSTITUCION = INST.IDINSTITUCION");
		sql.LEFT_OUTER_JOIN("CEN_CARGO CARGO ON CARGO.IDCARGO=COMPONENTE.IDCARGO");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS RECURSOCARGO on (CARGO.descripcion = idrecurso AND RECURSOCARGO.IDLENGUAJE = '1')");
		sql.LEFT_OUTER_JOIN("CEN_ACTIVIDADPROFESIONAL ACTIVIDAD ON (ACTIVIDAD.IDACTIVIDADPROFESIONAL = COMPONENTE.IDTIPOCOLEGIO )");

		sql1.SELECT("A.IDINSTITUCION");
		sql1.SELECT("A.IDPERSONA");
		sql1.SELECT("A.IDESTADO");
		
		sql2.SELECT("IDINSTITUCION");
		sql2.SELECT("IDPERSONA");
		sql2.SELECT("MAX(FECHAESTADO) AS FE");
		sql2.FROM("CEN_DATOSCOLEGIALESESTADO");
		sql2.GROUP_BY("IDINSTITUCION");
		sql2.GROUP_BY("IDPERSONA");
			
		
		sql1.FROM("CEN_DATOSCOLEGIALESESTADO A, (" + sql2 + ") B");
		sql1.WHERE("A.IDINSTITUCION=B.IDINSTITUCION");
		sql1.WHERE("A.IDPERSONA=B.IDPERSONA");
		sql1.WHERE("A.FECHAESTADO=B.FE");
		
		sql.LEFT_OUTER_JOIN("(" + sql1 + ") DATOSCOLEGIALES ON (DATOSCOLEGIALES.IDPERSONA = COMPONENTE.CEN_CLIENTE_IDPERSONA AND DATOSCOLEGIALES.IDINSTITUCION = COMPONENTE.CEN_CLIENTE_IDINSTITUCION)");
		
		sql.LEFT_OUTER_JOIN("CEN_ESTADOCOLEGIAL ESTCOL on (DATOSCOLEGIALES.IDESTADO =  ESTCOL.IDESTADO)");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS RECURSOACTIVIDAD on (ACTIVIDAD.descripcion = RECURSOACTIVIDAD.idrecurso AND RECURSOACTIVIDAD.IDLENGUAJE = '1')");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS CAT on ( ESTCOL.descripcion = CAT.idrecurso AND CAT.IDLENGUAJE = '1')");
	
		
		sql.WHERE("COMPONENTE.IDPERSONA = '"+integrantesSearchDTO.getIdPersona()+"'");
		if (!integrantesSearchDTO.getHistorico()) {
			sql.WHERE("COMPONENTE.FECHABAJA is null");
		}
		if (!UtilidadesString.esCadenaVacia(idInstitucion)) {
			sql.WHERE("COMPONENTE.idinstitucion = '" + idInstitucion + "'");
		}
		
		sql.ORDER_BY("COMPONENTE.IDCOMPONENTE");

		return sql.toString();
	}
	
	public String updateMember(TarjetaIntegrantesUpdateDTO tarjetaIntegrantesUpdateDTO, AdmUsuarios usuario, String idInstitucion) {
		SQL sql = new SQL();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		sql.UPDATE("CEN_COMPONENTES");
		
 		if(null != tarjetaIntegrantesUpdateDTO.getFechaCargo()) {
 			String fechaC = dateFormat.format(tarjetaIntegrantesUpdateDTO.getFechaCargo());
			sql.SET("FECHACARGO = TO_DATE('" + fechaC + "','DD/MM/YYYY hh24:mi:ss')");
		}
 		
 		if(null != tarjetaIntegrantesUpdateDTO.getFechaBajaCargo()) {
 			String fechaB = dateFormat.format(tarjetaIntegrantesUpdateDTO.getFechaBajaCargo());
			sql.SET("FECHABAJA = TO_DATE('" + fechaB + "','DD/MM/YYYY hh24:mi:ss')");
		}
 		
		if(!tarjetaIntegrantesUpdateDTO.getCargo().equals("")) {
			sql.SET("CARGO = '" + tarjetaIntegrantesUpdateDTO.getCargo().replace("'", "''") + "'");
		}
	
		if(!tarjetaIntegrantesUpdateDTO.getIdCargo().equals("")) {
			sql.SET("IDCARGO = '" + tarjetaIntegrantesUpdateDTO.getIdCargo() + "'");
		}
		
		
		if(!UtilidadesString.esCadenaVacia(tarjetaIntegrantesUpdateDTO.getFlagSocio())) {
			sql.SET("FLAG_SOCIO = '" + tarjetaIntegrantesUpdateDTO.getFlagSocio() + "'");
		}

		if(!UtilidadesString.esCadenaVacia(tarjetaIntegrantesUpdateDTO.getIdTipoColegio())) {
			sql.SET("IDTIPOCOLEGIO = '" + tarjetaIntegrantesUpdateDTO.getIdTipoColegio() + "'");
		}
		
		if(!UtilidadesString.esCadenaVacia(tarjetaIntegrantesUpdateDTO.getIdProvincia())) {
			sql.SET("IDPROVINCIA = '" + tarjetaIntegrantesUpdateDTO.getIdProvincia() + "'");
		}
		
		if(!UtilidadesString.esCadenaVacia(tarjetaIntegrantesUpdateDTO.getIdPersonaComponente())) {
			sql.SET("CEN_CLIENTE_IDPERSONA = '" + tarjetaIntegrantesUpdateDTO.getIdPersonaComponente() + "'");
		}
		
		if(!UtilidadesString.esCadenaVacia(tarjetaIntegrantesUpdateDTO.getColegio())) {
			sql.SET("CEN_CLIENTE_IDINSTITUCION = '" + tarjetaIntegrantesUpdateDTO.getColegio() + "'");
		}
		
		if(!UtilidadesString.esCadenaVacia(tarjetaIntegrantesUpdateDTO.getNumColegiado())) {
			sql.SET("NUMCOLEGIADO = '" + tarjetaIntegrantesUpdateDTO.getNumColegiado() + "'");
		}
		
		sql.SET("CAPITALSOCIAL = " + tarjetaIntegrantesUpdateDTO.getCapitalSocial());
		sql.SET("FECHAMODIFICACION = SYSDATE");
		sql.SET("USUMODIFICACION = '" + usuario.getIdusuario()+ "'");
		
		
		
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("IDPERSONA = '" + tarjetaIntegrantesUpdateDTO.getIdPersona() + "'");
		sql.WHERE("IDCOMPONENTE = '" + tarjetaIntegrantesUpdateDTO.getIdComponente() + "'");
		
		
		return sql.toString();
	}
	
	
	
	public String insertSelectiveForcreateMember(TarjetaIntegrantesCreateDTO tarjetaIntegrantesCreateDTO,AdmUsuarios usuario, String idInstitucion) {
		SQL sql = new SQL();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		sql.INSERT_INTO("CEN_COMPONENTES");
		
		sql.VALUES("IDINSTITUCION", "'" + idInstitucion  +"'");
		sql.VALUES("IDPERSONA", "'" + tarjetaIntegrantesCreateDTO.getIdPersonaPadre() + "'");
		sql.VALUES("IDCOMPONENTE", "'" + tarjetaIntegrantesCreateDTO.getIdComponente() + "'");
		
		if(!tarjetaIntegrantesCreateDTO.getCargo().equals("")) {
	        sql.VALUES("CARGO", "'" + tarjetaIntegrantesCreateDTO.getCargo().replace("'", "''") + "'");
		}
		
		if(null != tarjetaIntegrantesCreateDTO.getFechaCargo()) {
			String fechaC = dateFormat.format(tarjetaIntegrantesCreateDTO.getFechaCargo());
			sql.VALUES("FECHACARGO","TO_DATE('" + fechaC + "','DD/MM/YYYY hh24:mi:ss')");
			
		}
		
		if(null != tarjetaIntegrantesCreateDTO.getFechaBajaCargo()) {
			String fechaB = dateFormat.format(tarjetaIntegrantesCreateDTO.getFechaBajaCargo());
			sql.VALUES("FECHABAJA","TO_DATE('" + fechaB + "','DD/MM/YYYY hh24:mi:ss')");
			
		}
		
		sql.VALUES("CEN_CLIENTE_IDPERSONA", "'" + tarjetaIntegrantesCreateDTO.getIdPersonaIntegrante() + "'");
		sql.VALUES("CEN_CLIENTE_IDINSTITUCION", "'" + tarjetaIntegrantesCreateDTO.getColegio() + "'");
		sql.VALUES("SOCIEDAD", "'0'");
		sql.VALUES("FECHAMODIFICACION", "SYSDATE");
		sql.VALUES("USUMODIFICACION", "'" + usuario.getIdusuario()+ "'");
		
		if(!tarjetaIntegrantesCreateDTO.getIdTipoColegio().equals("")) {
			sql.VALUES("IDTIPOCOLEGIO", "'" + tarjetaIntegrantesCreateDTO.getIdTipoColegio() + "'");
		}
		
		if(!UtilidadesString.esCadenaVacia(tarjetaIntegrantesCreateDTO.getIdProvincia())) {
			sql.VALUES("IDPROVINCIA", "'" + tarjetaIntegrantesCreateDTO.getIdProvincia() + "'");
		}
		
		if(!UtilidadesString.esCadenaVacia(tarjetaIntegrantesCreateDTO.getFlagSocio())) {
			sql.SET("FLAG_SOCIO = '" + tarjetaIntegrantesCreateDTO.getFlagSocio() + "'");
		}
		
		if(!UtilidadesString.esCadenaVacia(tarjetaIntegrantesCreateDTO.getNumColegiado())) {
			sql.VALUES("NUMCOLEGIADO", "'" + tarjetaIntegrantesCreateDTO.getNumColegiado() + "'");
		}
		
		if(null != tarjetaIntegrantesCreateDTO.getCapitalSocial()) {
			sql.VALUES("CAPITALSOCIAL", tarjetaIntegrantesCreateDTO.getCapitalSocial().toString());
		}
		
		if(!UtilidadesString.esCadenaVacia(tarjetaIntegrantesCreateDTO.getIdCargo())) {
			sql.VALUES("IDCARGO", "'" + tarjetaIntegrantesCreateDTO.getIdCargo() + "'");
		}
		
		return sql.toString();
	}
	
	
	public String selectMaxIDComponente(String idPersonaPadre, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("MAX(IDCOMPONENTE) AS IDCOMPONENTE");
		sql.FROM("CEN_COMPONENTES");
		sql.WHERE("IDPERSONA = '" + idPersonaPadre + "'");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		return sql.toString();
	}
	
	public String searchSocieties(String idPersona, String idLenguaje, String idInstitucion) {
		SQL sql = new SQL();
		
		
		sql.SELECT("DISTINCT COMP.IDINSTITUCION as IDINSTITUCION");
		sql.SELECT("COMP.IDPERSONA AS IDPERSONA");
		sql.SELECT("PER.NIFCIF AS NIF");
		sql.SELECT("PER.NOMBRE  AS DENOMINACION");
		sql.SELECT("DECODE(PER.APELLIDOS1,'#NA','',PER.APELLIDOS1) AS ABREVIATURA");
		sql.SELECT("TO_CHAR(PER.FECHANACIMIENTO,'DD/MM/YYYY')  AS FECHACONSTITUCION");
		sql.SELECT("CA.DESCRIPCION AS TIPO");
		sql.SELECT("TIPOSOCIEDAD.LETRACIF AS TIPOSOCIEDAD");
		
		sql.SELECT("( SELECT COUNT('"+ idPersona + "') FROM CEN_COMPONENTES c WHERE C.IDINSTITUCION = '" + idInstitucion + "' AND C.IDPERSONA = per.IDPERSONA ) AS NUMEROINTEGRANTES");
		
		sql.FROM("CEN_COMPONENTES comp");

		sql.INNER_JOIN("CEN_PERSONA per ON PER.IDPERSONA = COMP.IDPERSONA");
		sql.INNER_JOIN("CEN_NOCOLEGIADO noCol ON noCol.IDPERSONA = per.IDPERSONA AND comp.IDINSTITUCION = noCol.IDINSTITUCION");
		sql.LEFT_OUTER_JOIN("CEN_TIPOSOCIEDAD  TIPOSOCIEDAD ON  noCol.TIPO = TIPOSOCIEDAD.LETRACIF");
		sql.LEFT_OUTER_JOIN(
				"GEN_RECURSOS_CATALOGOS CA ON (TIPOSOCIEDAD.DESCRIPCION = CA.IDRECURSO  AND CA.IDLENGUAJE = '"
						+ idLenguaje + "')");
		
		sql.WHERE("comp.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("comp.CEN_CLIENTE_IDPERSONA = '" + idPersona + "'");
		sql.ORDER_BY("FECHACONSTITUCION ASC");
		
		return sql.toString();
	}
	
}
