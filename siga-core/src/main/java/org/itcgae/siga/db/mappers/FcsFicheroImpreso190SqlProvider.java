package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.FcsFicheroImpreso190;
import org.itcgae.siga.db.entities.FcsFicheroImpreso190Example.Criteria;
import org.itcgae.siga.db.entities.FcsFicheroImpreso190Example.Criterion;
import org.itcgae.siga.db.entities.FcsFicheroImpreso190Example;

public class FcsFicheroImpreso190SqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FICHERO_IMPRESO190
	 * @mbg.generated  Thu Nov 18 13:45:46 CET 2021
	 */
	public String countByExample(FcsFicheroImpreso190Example example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("FCS_FICHERO_IMPRESO190");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FICHERO_IMPRESO190
	 * @mbg.generated  Thu Nov 18 13:45:46 CET 2021
	 */
	public String deleteByExample(FcsFicheroImpreso190Example example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("FCS_FICHERO_IMPRESO190");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FICHERO_IMPRESO190
	 * @mbg.generated  Thu Nov 18 13:45:46 CET 2021
	 */
	public String insertSelective(FcsFicheroImpreso190 record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("FCS_FICHERO_IMPRESO190");
		sql.VALUES("IDFICHERO", "#{idfichero,jdbcType=DECIMAL}");
		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getAnio() != null) {
			sql.VALUES("ANIO", "#{anio,jdbcType=DECIMAL}");
		}
		if (record.getNombreFichero() != null) {
			sql.VALUES("NOMBRE_FICHERO", "#{nombreFichero,jdbcType=VARCHAR}");
		}
		if (record.getTelefono() != null) {
			sql.VALUES("TELEFONO", "#{telefono,jdbcType=VARCHAR}");
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
		if (record.getFechagenerarion() != null) {
			sql.VALUES("FECHAGENERARION", "#{fechagenerarion,jdbcType=TIMESTAMP}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdGenFichero() != null) {
			sql.VALUES("ID_GEN_FICHERO", "#{idGenFichero,jdbcType=DECIMAL}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FICHERO_IMPRESO190
	 * @mbg.generated  Thu Nov 18 13:45:46 CET 2021
	 */
	public String selectByExample(FcsFicheroImpreso190Example example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDFICHERO");
		} else {
			sql.SELECT("IDFICHERO");
		}
		sql.SELECT("IDINSTITUCION");
		sql.SELECT("ANIO");
		sql.SELECT("NOMBRE_FICHERO");
		sql.SELECT("TELEFONO");
		sql.SELECT("NOMBRE");
		sql.SELECT("APELLIDO1");
		sql.SELECT("APELLIDO2");
		sql.SELECT("FECHAGENERARION");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("ID_GEN_FICHERO");
		sql.FROM("FCS_FICHERO_IMPRESO190");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FICHERO_IMPRESO190
	 * @mbg.generated  Thu Nov 18 13:45:46 CET 2021
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		FcsFicheroImpreso190 record = (FcsFicheroImpreso190) parameter.get("record");
		FcsFicheroImpreso190Example example = (FcsFicheroImpreso190Example) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("FCS_FICHERO_IMPRESO190");
		if (record.getIdfichero() != null) {
			sql.SET("IDFICHERO = #{record.idfichero,jdbcType=DECIMAL}");
		}
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getAnio() != null) {
			sql.SET("ANIO = #{record.anio,jdbcType=DECIMAL}");
		}
		if (record.getNombreFichero() != null) {
			sql.SET("NOMBRE_FICHERO = #{record.nombreFichero,jdbcType=VARCHAR}");
		}
		if (record.getTelefono() != null) {
			sql.SET("TELEFONO = #{record.telefono,jdbcType=VARCHAR}");
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
		if (record.getFechagenerarion() != null) {
			sql.SET("FECHAGENERARION = #{record.fechagenerarion,jdbcType=TIMESTAMP}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdGenFichero() != null) {
			sql.SET("ID_GEN_FICHERO = #{record.idGenFichero,jdbcType=DECIMAL}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FICHERO_IMPRESO190
	 * @mbg.generated  Thu Nov 18 13:45:46 CET 2021
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("FCS_FICHERO_IMPRESO190");
		sql.SET("IDFICHERO = #{record.idfichero,jdbcType=DECIMAL}");
		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		sql.SET("ANIO = #{record.anio,jdbcType=DECIMAL}");
		sql.SET("NOMBRE_FICHERO = #{record.nombreFichero,jdbcType=VARCHAR}");
		sql.SET("TELEFONO = #{record.telefono,jdbcType=VARCHAR}");
		sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
		sql.SET("APELLIDO1 = #{record.apellido1,jdbcType=VARCHAR}");
		sql.SET("APELLIDO2 = #{record.apellido2,jdbcType=VARCHAR}");
		sql.SET("FECHAGENERARION = #{record.fechagenerarion,jdbcType=TIMESTAMP}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("ID_GEN_FICHERO = #{record.idGenFichero,jdbcType=DECIMAL}");
		FcsFicheroImpreso190Example example = (FcsFicheroImpreso190Example) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FICHERO_IMPRESO190
	 * @mbg.generated  Thu Nov 18 13:45:46 CET 2021
	 */
	public String updateByPrimaryKeySelective(FcsFicheroImpreso190 record) {
		SQL sql = new SQL();
		sql.UPDATE("FCS_FICHERO_IMPRESO190");
		if (record.getAnio() != null) {
			sql.SET("ANIO = #{anio,jdbcType=DECIMAL}");
		}
		if (record.getNombreFichero() != null) {
			sql.SET("NOMBRE_FICHERO = #{nombreFichero,jdbcType=VARCHAR}");
		}
		if (record.getTelefono() != null) {
			sql.SET("TELEFONO = #{telefono,jdbcType=VARCHAR}");
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
		if (record.getFechagenerarion() != null) {
			sql.SET("FECHAGENERARION = #{fechagenerarion,jdbcType=TIMESTAMP}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdGenFichero() != null) {
			sql.SET("ID_GEN_FICHERO = #{idGenFichero,jdbcType=DECIMAL}");
		}
		sql.WHERE("IDFICHERO = #{idfichero,jdbcType=DECIMAL}");
		sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FICHERO_IMPRESO190
	 * @mbg.generated  Thu Nov 18 13:45:46 CET 2021
	 */
	protected void applyWhere(SQL sql, FcsFicheroImpreso190Example example, boolean includeExamplePhrase) {
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