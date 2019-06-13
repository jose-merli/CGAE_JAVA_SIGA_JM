package org.itcgae.siga.db.services.cen.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.EtiquetaUpdateDTO;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.commons.utils.SolModifSQLUtils;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.CenDatoscv;
import org.itcgae.siga.db.entities.CenSolicitudmodificacioncv;
import org.itcgae.siga.db.mappers.CenDatoscvSqlProvider;
import org.itcgae.siga.db.mappers.CenSolicitudmodificacioncvSqlProvider;

public class CenSolicitudmodificacioncvSqlExtendsProvider extends CenSolicitudmodificacioncvSqlProvider{
	
	public String searchDatosCurriculares(String idPersona, String idInstitucion) {
		SQL sql = new SQL();
		SQL sql1 = new SQL();
		
		sql.SELECT("TO_CHAR(FECHAINICIO,'DD/MM/YYYY') AS FECHADESDE");
		sql.SELECT("TO_CHAR(FECHAFIN,'DD/MM/YYYY') AS FECHAHASTA");
		sql.SELECT("TO_CHAR(FECHAMOVIMIENTO,'DD/MM/YYYY') AS FECHAMOVIMIENTO");
		sql1.SELECT("F_SIGA_GETRECURSO(DESCRIPCION,1) AS LABEL FROM CEN_TIPOSCV E WHERE IDTIPOCV = DATOS.IDTIPOCV");
		sql.SELECT("DATOS.DESCRIPCION, (" + sql1 + ")  AS CATEGORIACURRICULAR");		
		sql.SELECT("DATOS.DESCRIPCION, (" + sql1 + ")  AS DESCRIPCION");		
		sql.SELECT("CONCAT((SELECT F_SIGA_GETRECURSO(DESCRIPCION,1) AS LABEL FROM CEN_TIPOSCVSUBTIPO1 E WHERE IDTIPOCVSUBTIPO1 = DATOS.IDTIPOCVSUBTIPO1 AND TIPOS.IDTIPOCV = IDTIPOCV AND DATOS.IDINSTITUCION = IDINSTITUCION) || ' - ' , ((SELECT F_SIGA_GETRECURSO(DESCRIPCION,1) AS LABEL FROM CEN_TIPOSCVSUBTIPO2 E WHERE IDTIPOCVSUBTIPO2 = DATOS.IDTIPOCVSUBTIPO2 AND TIPOS.IDTIPOCV = IDTIPOCV AND DATOS.IDINSTITUCION = IDINSTITUCION) )) AS TIPOSUBTIPO");
		sql.SELECT("DATOS.IDTIPOCV");
		sql.SELECT("DATOS.IDCV");
		sql.SELECT("DATOS.IDTIPOCVSUBTIPO1");
		sql.SELECT("DATOS.IDTIPOCVSUBTIPO2");
		sql.SELECT("DATOS.IDINSTITUCION");
		sql.SELECT("DATOS.IDPERSONA");
		sql.SELECT("DATOS.CREDITOS");
		sql.SELECT("DATOS.CERTIFICADO");
//		sql.SELECT("DATOS.CERTIFICADO");
		sql.FROM("CEN_DATOSCV DATOS");
		sql.INNER_JOIN("CEN_TIPOSCV TIPOS ON DATOS.IDTIPOCV = TIPOS.IDTIPOCV ");
		sql.LEFT_OUTER_JOIN("CEN_TIPOSCVSUBTIPO1 SUB1 ON (SUB1.IDTIPOCVSUBTIPO1 = DATOS.IDTIPOCVSUBTIPO1 AND TIPOS.IDTIPOCV = SUB1.IDTIPOCV AND DATOS.IDINSTITUCION = SUB1.IDINSTITUCION) ");
		sql.LEFT_OUTER_JOIN("CEN_TIPOSCVSUBTIPO2 SUB2 ON (SUB2.IDTIPOCVSUBTIPO2 = DATOS.IDTIPOCVSUBTIPO2 AND TIPOS.IDTIPOCV = SUB2.IDTIPOCV AND DATOS.IDINSTITUCION = SUB2.IDINSTITUCION) ");
		sql.WHERE("DATOS.IDINSTITUCION = '"+idInstitucion+"'");
		sql.WHERE("DATOS.IDPERSONA = '"+idPersona+"'");
		
		return sql.toString();
	}
	
	
	public String insertSelectiveForCreateLegalPerson(String idInstitucion, AdmUsuarios usuario,
			EtiquetaUpdateDTO etiquetaUpdateDTO) {
		SQL sql = new SQL();

		sql.INSERT_INTO("CEN_NOCOLEGIADO");

		sql.VALUES("IDPERSONA", "(Select max(idpersona)  from cen_persona where idpersona like '" + idInstitucion + "' || '%')");
		sql.VALUES("IDINSTITUCION", "'" + idInstitucion + "'");
		sql.VALUES("FECHAMODIFICACION", "SYSDATE");
		sql.VALUES("USUMODIFICACION", "'" + String.valueOf(usuario.getIdusuario()) + "'");
		sql.VALUES("SOCIEDADSJ", "'0'");
		sql.VALUES("TIPO", "'" + etiquetaUpdateDTO.getTipo() + "'");
		if (null != etiquetaUpdateDTO.getAnotaciones() && !etiquetaUpdateDTO.getAnotaciones().equals("")) {
			sql.VALUES("ANOTACIONES", "'" + etiquetaUpdateDTO.getAnotaciones().replace("'", "''") + "'");
		}	
		
		sql.VALUES("SOCIEDADPROFESIONAL", "'0'");
		sql.VALUES("FECHA_BAJA", "null");

		return sql.toString();
	}
	

	public String solicitudUpdateCurriculo(CenSolicitudmodificacioncv record) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

			SQL sql = new SQL();
			SQL sql1 = new SQL();
			SQL sql2 = new SQL();

			sql.INSERT_INTO("CEN_SOLICITUDMODIFICACIONCV");
			sql1.SELECT("IDINSTITUCION");
			sql2.SELECT("IDINSTITUCION");
	
			sql.VALUES("IDSOLICITUD", ""+record.getIdsolicitud());
			
			sql.VALUES("MOTIVO", "'"+record.getMotivo().replace("'", "''") +"'");
			
			sql.VALUES("IDESTADOSOLIC", "10");
			if (record.getFechainicio() != null) {				
				String fechaF = dateFormat.format(record.getFechainicio());
				sql.VALUES("FECHAINICIO", "TO_DATE('" + fechaF + "','DD/MM/YYYY')");
				sql.VALUES("FECHAALTA", "TO_DATE('" + fechaF + "','DD/MM/YYYY')");
			}
			if (record.getFechafin() != null) {
				String fechaF = dateFormat.format(record.getFechafin());
				sql.VALUES("FECHAFIN", "TO_DATE('" + fechaF + "','DD/MM/YYYY')");
			}else {
				sql.VALUES("FECHAFIN","null");
			}
			if (record.getDescripcion() != null) {
				sql.VALUES("DESCRIPCION"," '"+ record.getDescripcion().replace("'", "''") +"'");
			}else {
				sql.VALUES("DESCRIPCION","null");
			}
			if (record.getIdtipocv() != null) {
				sql.VALUES("IDTIPOCV","'"+record.getIdtipocv() +"'");
			}
			if (record.getFechamodificacion() != null) {
				String fechaF = dateFormat.format(record.getFechamodificacion());
				sql.VALUES("FECHAMODIFICACION ","'" + fechaF + "'");
			}							
			if (record.getUsumodificacion() != null) {
				sql.VALUES("USUMODIFICACION ","'"+record.getUsumodificacion() +"'");
			}

			if (record.getIdtipocvsubtipo1() != null) {
				sql.VALUES("IDTIPOCVSUBTIPO1 "," '"+record.getIdtipocvsubtipo1() + "'");
				sql1.FROM("CEN_TIPOSCVSUBTIPO1");
				sql1.WHERE("IDTIPOCVSUBTIPO1 ='"+ record.getIdtipocvsubtipo1() +"'");
				sql1.WHERE("IDTIPOCV ='"+ record.getIdtipocv() +"'");
				sql1.WHERE("ROWNUM = 1");
				sql.VALUES("IDINSTITUCION_SUBT1 ","("+ record.getIdinstitucionSubt1() + ")");
			}else{
				sql.SET("IDTIPOCVSUBTIPO1 = "+record.getIdtipocvsubtipo1() + "");
				sql.SET("IDINSTITUCION_SUBT1 = "+record.getIdinstitucionSubt1() + "");
			}
			
			if (record.getIdtipocvsubtipo2() != null) {
				sql.VALUES("IDTIPOCVSUBTIPO2 ","'"+ record.getIdtipocvsubtipo2() + "'");
				sql2.FROM("CEN_TIPOSCVSUBTIPO2");
				sql2.WHERE("IDTIPOCVSUBTIPO2 ='"+ record.getIdtipocvsubtipo2() +"'");
				sql2.WHERE("IDTIPOCV ='"+ record.getIdtipocv() +"'");
				sql2.WHERE("ROWNUM = 1");
				sql.VALUES("IDINSTITUCION_SUBT2 "," ("+ record.getIdinstitucionSubt2() + ")");
				
			}else{
				sql.VALUES("IDTIPOCVSUBTIPO2 "," "+ record.getIdtipocvsubtipo2() + "");
				sql.SET("IDINSTITUCION_SUBT2 = "+record.getIdinstitucionSubt2() + "");

		}
			sql.VALUES("IDINSTITUCION ","'"+record.getIdinstitucion()+"'");
			sql.VALUES("IDPERSONA "," '" + record.getIdpersona() +"'");
			sql.VALUES("IDCV ","'" + record.getIdcv() +"'");
			return sql.toString();
	}
	
	public String getMaxIdSolicitud(String idInstitucion, String idPersona) {
		SQL sql = new SQL();

		sql.SELECT("NVL(MAX(IDSOLICITUD),0) AS IDSOLICITUD");
		sql.FROM("CEN_SOLICITUDMODIFICACIONCV");
		
		return sql.toString();
	}
	

	public String searchSolModifDatosCurriculares(SolicitudModificacionSearchDTO solicitudModificacionSearchDTO,
			String idLenguaje, String idInstitucion, Long idPersona) {
		if (null != idPersona) {
			String rdo = "SELECT * FROM ((" + SolModifSQLUtils.getGeneralRequest(solicitudModificacionSearchDTO, idLenguaje, idInstitucion) + " ) UNION ( "
					+ SolModifSQLUtils.getCVRequest(solicitudModificacionSearchDTO, idLenguaje, idInstitucion) + " )) WHERE IDPERSONA = "+ idPersona +"  ORDER BY 6 DESC";
			return rdo;			
		}else{
			String rdo = "SELECT * FROM (" + SolModifSQLUtils.getGeneralRequest(solicitudModificacionSearchDTO, idLenguaje, idInstitucion) + " ) UNION ( "
					+ SolModifSQLUtils.getCVRequest(solicitudModificacionSearchDTO, idLenguaje, idInstitucion) + " ) ORDER BY 6 DESC";
			return rdo;
		}
	}
}
