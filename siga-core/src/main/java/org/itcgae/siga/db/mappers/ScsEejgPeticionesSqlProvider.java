package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.ScsEejgPeticiones;
import org.itcgae.siga.db.entities.ScsEejgPeticionesExample.Criteria;
import org.itcgae.siga.db.entities.ScsEejgPeticionesExample.Criterion;
import org.itcgae.siga.db.entities.ScsEejgPeticionesExample;

public class ScsEejgPeticionesSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_EEJG_PETICIONES
	 * @mbg.generated  Tue May 11 17:13:13 CEST 2021
	 */
	public String countByExample(ScsEejgPeticionesExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("SCS_EEJG_PETICIONES");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_EEJG_PETICIONES
	 * @mbg.generated  Tue May 11 17:13:13 CEST 2021
	 */
	public String deleteByExample(ScsEejgPeticionesExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("SCS_EEJG_PETICIONES");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_EEJG_PETICIONES
	 * @mbg.generated  Tue May 11 17:13:13 CEST 2021
	 */
	public String insertSelective(ScsEejgPeticiones record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("SCS_EEJG_PETICIONES");
		if (record.getIdpeticion() != null) {
			sql.VALUES("IDPETICION", "#{idpeticion,jdbcType=DECIMAL}");
		}
		if (record.getIdusuariopeticion() != null) {
			sql.VALUES("IDUSUARIOPETICION", "#{idusuariopeticion,jdbcType=DECIMAL}");
		}
		if (record.getFechapeticion() != null) {
			sql.VALUES("FECHAPETICION", "#{fechapeticion,jdbcType=TIMESTAMP}");
		}
		if (record.getEstado() != null) {
			sql.VALUES("ESTADO", "#{estado,jdbcType=DECIMAL}");
		}
		if (record.getIdsolicitud() != null) {
			sql.VALUES("IDSOLICITUD", "#{idsolicitud,jdbcType=VARCHAR}");
		}
		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdtipoejg() != null) {
			sql.VALUES("IDTIPOEJG", "#{idtipoejg,jdbcType=DECIMAL}");
		}
		if (record.getAnio() != null) {
			sql.VALUES("ANIO", "#{anio,jdbcType=DECIMAL}");
		}
		if (record.getNumero() != null) {
			sql.VALUES("NUMERO", "#{numero,jdbcType=DECIMAL}");
		}
		if (record.getIdpersona() != null) {
			sql.VALUES("IDPERSONA", "#{idpersona,jdbcType=DECIMAL}");
		}
		if (record.getNumerointentossolicitud() != null) {
			sql.VALUES("NUMEROINTENTOSSOLICITUD", "#{numerointentossolicitud,jdbcType=DECIMAL}");
		}
		if (record.getNumerointentosconsulta() != null) {
			sql.VALUES("NUMEROINTENTOSCONSULTA", "#{numerointentosconsulta,jdbcType=DECIMAL}");
		}
		if (record.getIdxml() != null) {
			sql.VALUES("IDXML", "#{idxml,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getFechasolicitud() != null) {
			sql.VALUES("FECHASOLICITUD", "#{fechasolicitud,jdbcType=TIMESTAMP}");
		}
		if (record.getFechaconsulta() != null) {
			sql.VALUES("FECHACONSULTA", "#{fechaconsulta,jdbcType=TIMESTAMP}");
		}
		if (record.getIdioma() != null) {
			sql.VALUES("IDIOMA", "#{idioma,jdbcType=VARCHAR}");
		}
		if (record.getNumerointentospendienteinfo() != null) {
			sql.VALUES("NUMEROINTENTOSPENDIENTEINFO", "#{numerointentospendienteinfo,jdbcType=DECIMAL}");
		}
		if (record.getNif() != null) {
			sql.VALUES("NIF", "#{nif,jdbcType=VARCHAR}");
		}
		if (record.getNombre() != null) {
			sql.VALUES("NOMBRE", "#{nombre,jdbcType=VARCHAR}");
		}
		if (record.getApellido1() != null) {
			sql.VALUES("APELLIDO1", "#{apellido1,jdbcType=VARCHAR}");
		}
		if (record.getApellido2() != null) {
			sql.VALUES("APELLIDO2", "#{apellido2,jdbcType=VARCHAR}");
		}
		if (record.getObservaciones() != null) {
			sql.VALUES("OBSERVACIONES", "#{observaciones,jdbcType=VARCHAR}");
		}
		if (record.getRutaPdf() != null) {
			sql.VALUES("RUTA_PDF", "#{rutaPdf,jdbcType=VARCHAR}");
		}
		if (record.getIdecomcola() != null) {
			sql.VALUES("IDECOMCOLA", "#{idecomcola,jdbcType=DECIMAL}");
		}
		if (record.getMsgerror() != null) {
			sql.VALUES("MSGERROR", "#{msgerror,jdbcType=VARCHAR}");
		}
		if (record.getCsv() != null) {
			sql.VALUES("CSV", "#{csv,jdbcType=VARCHAR}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_EEJG_PETICIONES
	 * @mbg.generated  Tue May 11 17:13:13 CEST 2021
	 */
	public String selectByExample(ScsEejgPeticionesExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDPETICION");
		} else {
			sql.SELECT("IDPETICION");
		}
		sql.SELECT("IDUSUARIOPETICION");
		sql.SELECT("FECHAPETICION");
		sql.SELECT("ESTADO");
		sql.SELECT("IDSOLICITUD");
		sql.SELECT("IDINSTITUCION");
		sql.SELECT("IDTIPOEJG");
		sql.SELECT("ANIO");
		sql.SELECT("NUMERO");
		sql.SELECT("IDPERSONA");
		sql.SELECT("NUMEROINTENTOSSOLICITUD");
		sql.SELECT("NUMEROINTENTOSCONSULTA");
		sql.SELECT("IDXML");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("FECHASOLICITUD");
		sql.SELECT("FECHACONSULTA");
		sql.SELECT("IDIOMA");
		sql.SELECT("NUMEROINTENTOSPENDIENTEINFO");
		sql.SELECT("NIF");
		sql.SELECT("NOMBRE");
		sql.SELECT("APELLIDO1");
		sql.SELECT("APELLIDO2");
		sql.SELECT("OBSERVACIONES");
		sql.SELECT("RUTA_PDF");
		sql.SELECT("IDECOMCOLA");
		sql.SELECT("MSGERROR");
		sql.SELECT("CSV");
		sql.FROM("SCS_EEJG_PETICIONES");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_EEJG_PETICIONES
	 * @mbg.generated  Tue May 11 17:13:13 CEST 2021
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		ScsEejgPeticiones record = (ScsEejgPeticiones) parameter.get("record");
		ScsEejgPeticionesExample example = (ScsEejgPeticionesExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("SCS_EEJG_PETICIONES");
		if (record.getIdpeticion() != null) {
			sql.SET("IDPETICION = #{record.idpeticion,jdbcType=DECIMAL}");
		}
		if (record.getIdusuariopeticion() != null) {
			sql.SET("IDUSUARIOPETICION = #{record.idusuariopeticion,jdbcType=DECIMAL}");
		}
		if (record.getFechapeticion() != null) {
			sql.SET("FECHAPETICION = #{record.fechapeticion,jdbcType=TIMESTAMP}");
		}
		if (record.getEstado() != null) {
			sql.SET("ESTADO = #{record.estado,jdbcType=DECIMAL}");
		}
		if (record.getIdsolicitud() != null) {
			sql.SET("IDSOLICITUD = #{record.idsolicitud,jdbcType=VARCHAR}");
		}
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdtipoejg() != null) {
			sql.SET("IDTIPOEJG = #{record.idtipoejg,jdbcType=DECIMAL}");
		}
		if (record.getAnio() != null) {
			sql.SET("ANIO = #{record.anio,jdbcType=DECIMAL}");
		}
		if (record.getNumero() != null) {
			sql.SET("NUMERO = #{record.numero,jdbcType=DECIMAL}");
		}
		if (record.getIdpersona() != null) {
			sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
		}
		if (record.getNumerointentossolicitud() != null) {
			sql.SET("NUMEROINTENTOSSOLICITUD = #{record.numerointentossolicitud,jdbcType=DECIMAL}");
		}
		if (record.getNumerointentosconsulta() != null) {
			sql.SET("NUMEROINTENTOSCONSULTA = #{record.numerointentosconsulta,jdbcType=DECIMAL}");
		}
		if (record.getIdxml() != null) {
			sql.SET("IDXML = #{record.idxml,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getFechasolicitud() != null) {
			sql.SET("FECHASOLICITUD = #{record.fechasolicitud,jdbcType=TIMESTAMP}");
		}
		if (record.getFechaconsulta() != null) {
			sql.SET("FECHACONSULTA = #{record.fechaconsulta,jdbcType=TIMESTAMP}");
		}
		if (record.getIdioma() != null) {
			sql.SET("IDIOMA = #{record.idioma,jdbcType=VARCHAR}");
		}
		if (record.getNumerointentospendienteinfo() != null) {
			sql.SET("NUMEROINTENTOSPENDIENTEINFO = #{record.numerointentospendienteinfo,jdbcType=DECIMAL}");
		}
		if (record.getNif() != null) {
			sql.SET("NIF = #{record.nif,jdbcType=VARCHAR}");
		}
		if (record.getNombre() != null) {
			sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
		}
		if (record.getApellido1() != null) {
			sql.SET("APELLIDO1 = #{record.apellido1,jdbcType=VARCHAR}");
		}
		if (record.getApellido2() != null) {
			sql.SET("APELLIDO2 = #{record.apellido2,jdbcType=VARCHAR}");
		}
		if (record.getObservaciones() != null) {
			sql.SET("OBSERVACIONES = #{record.observaciones,jdbcType=VARCHAR}");
		}
		if (record.getRutaPdf() != null) {
			sql.SET("RUTA_PDF = #{record.rutaPdf,jdbcType=VARCHAR}");
		}
		if (record.getIdecomcola() != null) {
			sql.SET("IDECOMCOLA = #{record.idecomcola,jdbcType=DECIMAL}");
		}
		if (record.getMsgerror() != null) {
			sql.SET("MSGERROR = #{record.msgerror,jdbcType=VARCHAR}");
		}
		if (record.getCsv() != null) {
			sql.SET("CSV = #{record.csv,jdbcType=VARCHAR}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_EEJG_PETICIONES
	 * @mbg.generated  Tue May 11 17:13:13 CEST 2021
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("SCS_EEJG_PETICIONES");
		sql.SET("IDPETICION = #{record.idpeticion,jdbcType=DECIMAL}");
		sql.SET("IDUSUARIOPETICION = #{record.idusuariopeticion,jdbcType=DECIMAL}");
		sql.SET("FECHAPETICION = #{record.fechapeticion,jdbcType=TIMESTAMP}");
		sql.SET("ESTADO = #{record.estado,jdbcType=DECIMAL}");
		sql.SET("IDSOLICITUD = #{record.idsolicitud,jdbcType=VARCHAR}");
		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		sql.SET("IDTIPOEJG = #{record.idtipoejg,jdbcType=DECIMAL}");
		sql.SET("ANIO = #{record.anio,jdbcType=DECIMAL}");
		sql.SET("NUMERO = #{record.numero,jdbcType=DECIMAL}");
		sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
		sql.SET("NUMEROINTENTOSSOLICITUD = #{record.numerointentossolicitud,jdbcType=DECIMAL}");
		sql.SET("NUMEROINTENTOSCONSULTA = #{record.numerointentosconsulta,jdbcType=DECIMAL}");
		sql.SET("IDXML = #{record.idxml,jdbcType=DECIMAL}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("FECHASOLICITUD = #{record.fechasolicitud,jdbcType=TIMESTAMP}");
		sql.SET("FECHACONSULTA = #{record.fechaconsulta,jdbcType=TIMESTAMP}");
		sql.SET("IDIOMA = #{record.idioma,jdbcType=VARCHAR}");
		sql.SET("NUMEROINTENTOSPENDIENTEINFO = #{record.numerointentospendienteinfo,jdbcType=DECIMAL}");
		sql.SET("NIF = #{record.nif,jdbcType=VARCHAR}");
		sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
		sql.SET("APELLIDO1 = #{record.apellido1,jdbcType=VARCHAR}");
		sql.SET("APELLIDO2 = #{record.apellido2,jdbcType=VARCHAR}");
		sql.SET("OBSERVACIONES = #{record.observaciones,jdbcType=VARCHAR}");
		sql.SET("RUTA_PDF = #{record.rutaPdf,jdbcType=VARCHAR}");
		sql.SET("IDECOMCOLA = #{record.idecomcola,jdbcType=DECIMAL}");
		sql.SET("MSGERROR = #{record.msgerror,jdbcType=VARCHAR}");
		sql.SET("CSV = #{record.csv,jdbcType=VARCHAR}");
		ScsEejgPeticionesExample example = (ScsEejgPeticionesExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_EEJG_PETICIONES
	 * @mbg.generated  Tue May 11 17:13:13 CEST 2021
	 */
	public String updateByPrimaryKeySelective(ScsEejgPeticiones record) {
		SQL sql = new SQL();
		sql.UPDATE("SCS_EEJG_PETICIONES");
		if (record.getIdusuariopeticion() != null) {
			sql.SET("IDUSUARIOPETICION = #{idusuariopeticion,jdbcType=DECIMAL}");
		}
		if (record.getFechapeticion() != null) {
			sql.SET("FECHAPETICION = #{fechapeticion,jdbcType=TIMESTAMP}");
		}
		if (record.getEstado() != null) {
			sql.SET("ESTADO = #{estado,jdbcType=DECIMAL}");
		}
		if (record.getIdsolicitud() != null) {
			sql.SET("IDSOLICITUD = #{idsolicitud,jdbcType=VARCHAR}");
		}
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdtipoejg() != null) {
			sql.SET("IDTIPOEJG = #{idtipoejg,jdbcType=DECIMAL}");
		}
		if (record.getAnio() != null) {
			sql.SET("ANIO = #{anio,jdbcType=DECIMAL}");
		}
		if (record.getNumero() != null) {
			sql.SET("NUMERO = #{numero,jdbcType=DECIMAL}");
		}
		if (record.getIdpersona() != null) {
			sql.SET("IDPERSONA = #{idpersona,jdbcType=DECIMAL}");
		}
		if (record.getNumerointentossolicitud() != null) {
			sql.SET("NUMEROINTENTOSSOLICITUD = #{numerointentossolicitud,jdbcType=DECIMAL}");
		}
		if (record.getNumerointentosconsulta() != null) {
			sql.SET("NUMEROINTENTOSCONSULTA = #{numerointentosconsulta,jdbcType=DECIMAL}");
		}
		if (record.getIdxml() != null) {
			sql.SET("IDXML = #{idxml,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getFechasolicitud() != null) {
			sql.SET("FECHASOLICITUD = #{fechasolicitud,jdbcType=TIMESTAMP}");
		}
		if (record.getFechaconsulta() != null) {
			sql.SET("FECHACONSULTA = #{fechaconsulta,jdbcType=TIMESTAMP}");
		}
		if (record.getIdioma() != null) {
			sql.SET("IDIOMA = #{idioma,jdbcType=VARCHAR}");
		}
		if (record.getNumerointentospendienteinfo() != null) {
			sql.SET("NUMEROINTENTOSPENDIENTEINFO = #{numerointentospendienteinfo,jdbcType=DECIMAL}");
		}
		if (record.getNif() != null) {
			sql.SET("NIF = #{nif,jdbcType=VARCHAR}");
		}
		if (record.getNombre() != null) {
			sql.SET("NOMBRE = #{nombre,jdbcType=VARCHAR}");
		}
		if (record.getApellido1() != null) {
			sql.SET("APELLIDO1 = #{apellido1,jdbcType=VARCHAR}");
		}
		if (record.getApellido2() != null) {
			sql.SET("APELLIDO2 = #{apellido2,jdbcType=VARCHAR}");
		}
		if (record.getObservaciones() != null) {
			sql.SET("OBSERVACIONES = #{observaciones,jdbcType=VARCHAR}");
		}
		if (record.getRutaPdf() != null) {
			sql.SET("RUTA_PDF = #{rutaPdf,jdbcType=VARCHAR}");
		}
		if (record.getIdecomcola() != null) {
			sql.SET("IDECOMCOLA = #{idecomcola,jdbcType=DECIMAL}");
		}
		if (record.getMsgerror() != null) {
			sql.SET("MSGERROR = #{msgerror,jdbcType=VARCHAR}");
		}
		if (record.getCsv() != null) {
			sql.SET("CSV = #{csv,jdbcType=VARCHAR}");
		}
		sql.WHERE("IDPETICION = #{idpeticion,jdbcType=DECIMAL}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_EEJG_PETICIONES
	 * @mbg.generated  Tue May 11 17:13:13 CEST 2021
	 */
	protected void applyWhere(SQL sql, ScsEejgPeticionesExample example, boolean includeExamplePhrase) {
		if (example == null) {
			return;
		}
		String parmPhrase1;
		String parmPhrase1_th;
		String parmPhrase2;
		String parmPhrase2_th;
		String parmPhrase3;
		String parmPhrase3_th;
		if (includeExamplePhrase) {
			parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
			parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
			parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
			parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
			parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
			parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
		} else {
			parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
			parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
			parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
			parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
			parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
			parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
		}
		StringBuilder sb = new StringBuilder();
		List<Criteria> oredCriteria = example.getOredCriteria();
		boolean firstCriteria = true;
		for (int i = 0; i < oredCriteria.size(); i++) {
			Criteria criteria = oredCriteria.get(i);
			if (criteria.isValid()) {
				if (firstCriteria) {
					firstCriteria = false;
				} else {
					sb.append(" or ");
				}
				sb.append('(');
				List<Criterion> criterions = criteria.getAllCriteria();
				boolean firstCriterion = true;
				for (int j = 0; j < criterions.size(); j++) {
					Criterion criterion = criterions.get(j);
					if (firstCriterion) {
						firstCriterion = false;
					} else {
						sb.append(" and ");
					}
					if (criterion.isNoValue()) {
						sb.append(criterion.getCondition());
					} else if (criterion.isSingleValue()) {
						if (criterion.getTypeHandler() == null) {
							sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
						} else {
							sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,
									criterion.getTypeHandler()));
						}
					} else if (criterion.isBetweenValue()) {
						if (criterion.getTypeHandler() == null) {
							sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
						} else {
							sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j,
									criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
						}
					} else if (criterion.isListValue()) {
						sb.append(criterion.getCondition());
						sb.append(" (");
						List<?> listItems = (List<?>) criterion.getValue();
						boolean comma = false;
						for (int k = 0; k < listItems.size(); k++) {
							if (comma) {
								sb.append(", ");
							} else {
								comma = true;
							}
							if (criterion.getTypeHandler() == null) {
								sb.append(String.format(parmPhrase3, i, j, k));
							} else {
								sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
							}
						}
						sb.append(')');
					}
				}
				sb.append(')');
			}
		}
		if (sb.length() > 0) {
			sql.WHERE(sb.toString());
		}
	}
}