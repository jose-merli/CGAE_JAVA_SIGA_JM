package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.CenPersonaExample.Criteria;
import org.itcgae.siga.db.entities.CenPersonaExample.Criterion;

public class CenPersonaSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PERSONA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String countByExample(CenPersonaExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("CEN_PERSONA");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PERSONA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String deleteByExample(CenPersonaExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("CEN_PERSONA");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PERSONA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String insertSelective(CenPersona record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("CEN_PERSONA");
		if (record.getIdpersona() != null) {
			sql.VALUES("IDPERSONA", "#{idpersona,jdbcType=DECIMAL}");
		}
		if (record.getNombre() != null) {
			sql.VALUES("NOMBRE", "#{nombre,jdbcType=VARCHAR}");
		}
		if (record.getApellidos1() != null) {
			sql.VALUES("APELLIDOS1", "#{apellidos1,jdbcType=VARCHAR}");
		}
		if (record.getApellidos2() != null) {
			sql.VALUES("APELLIDOS2", "#{apellidos2,jdbcType=VARCHAR}");
		}
		if (record.getNifcif() != null) {
			sql.VALUES("NIFCIF", "#{nifcif,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdtipoidentificacion() != null) {
			sql.VALUES("IDTIPOIDENTIFICACION", "#{idtipoidentificacion,jdbcType=DECIMAL}");
		}
		if (record.getFechanacimiento() != null) {
			sql.VALUES("FECHANACIMIENTO", "#{fechanacimiento,jdbcType=TIMESTAMP}");
		}
		if (record.getIdestadocivil() != null) {
			sql.VALUES("IDESTADOCIVIL", "#{idestadocivil,jdbcType=DECIMAL}");
		}
		if (record.getNaturalde() != null) {
			sql.VALUES("NATURALDE", "#{naturalde,jdbcType=VARCHAR}");
		}
		if (record.getFallecido() != null) {
			sql.VALUES("FALLECIDO", "#{fallecido,jdbcType=VARCHAR}");
		}
		if (record.getSexo() != null) {
			sql.VALUES("SEXO", "#{sexo,jdbcType=VARCHAR}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PERSONA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String selectByExample(CenPersonaExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDPERSONA");
		} else {
			sql.SELECT("IDPERSONA");
		}
		sql.SELECT("NOMBRE");
		sql.SELECT("APELLIDOS1");
		sql.SELECT("APELLIDOS2");
		sql.SELECT("NIFCIF");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("IDTIPOIDENTIFICACION");
		sql.SELECT("FECHANACIMIENTO");
		sql.SELECT("IDESTADOCIVIL");
		sql.SELECT("NATURALDE");
		sql.SELECT("FALLECIDO");
		sql.SELECT("SEXO");
		sql.FROM("CEN_PERSONA");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PERSONA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		CenPersona record = (CenPersona) parameter.get("record");
		CenPersonaExample example = (CenPersonaExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("CEN_PERSONA");
		if (record.getIdpersona() != null) {
			sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
		}
		if (record.getNombre() != null) {
			sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
		}
		if (record.getApellidos1() != null) {
			sql.SET("APELLIDOS1 = #{record.apellidos1,jdbcType=VARCHAR}");
		}
		if (record.getApellidos2() != null) {
			sql.SET("APELLIDOS2 = #{record.apellidos2,jdbcType=VARCHAR}");
		}
		if (record.getNifcif() != null) {
			sql.SET("NIFCIF = #{record.nifcif,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdtipoidentificacion() != null) {
			sql.SET("IDTIPOIDENTIFICACION = #{record.idtipoidentificacion,jdbcType=DECIMAL}");
		}
		if (record.getFechanacimiento() != null) {
			sql.SET("FECHANACIMIENTO = #{record.fechanacimiento,jdbcType=TIMESTAMP}");
		}
		if (record.getIdestadocivil() != null) {
			sql.SET("IDESTADOCIVIL = #{record.idestadocivil,jdbcType=DECIMAL}");
		}
		if (record.getNaturalde() != null) {
			sql.SET("NATURALDE = #{record.naturalde,jdbcType=VARCHAR}");
		}
		if (record.getFallecido() != null) {
			sql.SET("FALLECIDO = #{record.fallecido,jdbcType=VARCHAR}");
		}
		if (record.getSexo() != null) {
			sql.SET("SEXO = #{record.sexo,jdbcType=VARCHAR}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PERSONA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_PERSONA");
		sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
		sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
		sql.SET("APELLIDOS1 = #{record.apellidos1,jdbcType=VARCHAR}");
		sql.SET("APELLIDOS2 = #{record.apellidos2,jdbcType=VARCHAR}");
		sql.SET("NIFCIF = #{record.nifcif,jdbcType=VARCHAR}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("IDTIPOIDENTIFICACION = #{record.idtipoidentificacion,jdbcType=DECIMAL}");
		sql.SET("FECHANACIMIENTO = #{record.fechanacimiento,jdbcType=TIMESTAMP}");
		sql.SET("IDESTADOCIVIL = #{record.idestadocivil,jdbcType=DECIMAL}");
		sql.SET("NATURALDE = #{record.naturalde,jdbcType=VARCHAR}");
		sql.SET("FALLECIDO = #{record.fallecido,jdbcType=VARCHAR}");
		sql.SET("SEXO = #{record.sexo,jdbcType=VARCHAR}");
		CenPersonaExample example = (CenPersonaExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PERSONA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByPrimaryKeySelective(CenPersona record) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_PERSONA");
		if (record.getNombre() != null) {
			sql.SET("NOMBRE = #{nombre,jdbcType=VARCHAR}");
		}
		if (record.getApellidos1() != null) {
			sql.SET("APELLIDOS1 = #{apellidos1,jdbcType=VARCHAR}");
		}
		if (record.getApellidos2() != null) {
			sql.SET("APELLIDOS2 = #{apellidos2,jdbcType=VARCHAR}");
		}
		if (record.getNifcif() != null) {
			sql.SET("NIFCIF = #{nifcif,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdtipoidentificacion() != null) {
			sql.SET("IDTIPOIDENTIFICACION = #{idtipoidentificacion,jdbcType=DECIMAL}");
		}
		if (record.getFechanacimiento() != null) {
			sql.SET("FECHANACIMIENTO = #{fechanacimiento,jdbcType=TIMESTAMP}");
		}
		if (record.getIdestadocivil() != null) {
			sql.SET("IDESTADOCIVIL = #{idestadocivil,jdbcType=DECIMAL}");
		}
		if (record.getNaturalde() != null) {
			sql.SET("NATURALDE = #{naturalde,jdbcType=VARCHAR}");
		}
		if (record.getFallecido() != null) {
			sql.SET("FALLECIDO = #{fallecido,jdbcType=VARCHAR}");
		}
		if (record.getSexo() != null) {
			sql.SET("SEXO = #{sexo,jdbcType=VARCHAR}");
		}
		sql.WHERE("IDPERSONA = #{idpersona,jdbcType=DECIMAL}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_PERSONA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	protected void applyWhere(SQL sql, CenPersonaExample example, boolean includeExamplePhrase) {
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