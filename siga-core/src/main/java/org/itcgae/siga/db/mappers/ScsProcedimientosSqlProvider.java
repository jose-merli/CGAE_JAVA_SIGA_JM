package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.ScsProcedimientos;
import org.itcgae.siga.db.entities.ScsProcedimientosExample.Criteria;
import org.itcgae.siga.db.entities.ScsProcedimientosExample.Criterion;
import org.itcgae.siga.db.entities.ScsProcedimientosExample;

public class ScsProcedimientosSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PROCEDIMIENTOS
	 * @mbg.generated  Wed Sep 18 13:54:36 CEST 2019
	 */
	public String countByExample(ScsProcedimientosExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("SCS_PROCEDIMIENTOS");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PROCEDIMIENTOS
	 * @mbg.generated  Wed Sep 18 13:54:36 CEST 2019
	 */
	public String deleteByExample(ScsProcedimientosExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("SCS_PROCEDIMIENTOS");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PROCEDIMIENTOS
	 * @mbg.generated  Wed Sep 18 13:54:36 CEST 2019
	 */
	public String insertSelective(ScsProcedimientos record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("SCS_PROCEDIMIENTOS");
		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdprocedimiento() != null) {
			sql.VALUES("IDPROCEDIMIENTO", "#{idprocedimiento,jdbcType=VARCHAR}");
		}
		if (record.getNombre() != null) {
			sql.VALUES("NOMBRE", "#{nombre,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getPrecio() != null) {
			sql.VALUES("PRECIO", "#{precio,jdbcType=DECIMAL}");
		}
		if (record.getIdjurisdiccion() != null) {
			sql.VALUES("IDJURISDICCION", "#{idjurisdiccion,jdbcType=DECIMAL}");
		}
		if (record.getCodigo() != null) {
			sql.VALUES("CODIGO", "#{codigo,jdbcType=VARCHAR}");
		}
		if (record.getComplemento() != null) {
			sql.VALUES("COMPLEMENTO", "#{complemento,jdbcType=VARCHAR}");
		}
		if (record.getVigente() != null) {
			sql.VALUES("VIGENTE", "#{vigente,jdbcType=VARCHAR}");
		}
		if (record.getOrden() != null) {
			sql.VALUES("ORDEN", "#{orden,jdbcType=DECIMAL}");
		}
		if (record.getCodigoext() != null) {
			sql.VALUES("CODIGOEXT", "#{codigoext,jdbcType=VARCHAR}");
		}
		if (record.getPermitiraniadirletrado() != null) {
			sql.VALUES("PERMITIRANIADIRLETRADO", "#{permitiraniadirletrado,jdbcType=VARCHAR}");
		}
		if (record.getFechadesdevigor() != null) {
			sql.VALUES("FECHADESDEVIGOR", "#{fechadesdevigor,jdbcType=TIMESTAMP}");
		}
		if (record.getFechahastavigor() != null) {
			sql.VALUES("FECHAHASTAVIGOR", "#{fechahastavigor,jdbcType=TIMESTAMP}");
		}
		if (record.getFechabaja() != null) {
			sql.VALUES("FECHABAJA", "#{fechabaja,jdbcType=TIMESTAMP}");
		}
		if (record.getObservaciones() != null) {
			sql.VALUES("OBSERVACIONES", "#{observaciones,jdbcType=VARCHAR}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PROCEDIMIENTOS
	 * @mbg.generated  Wed Sep 18 13:54:36 CEST 2019
	 */
	public String selectByExample(ScsProcedimientosExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDINSTITUCION");
		} else {
			sql.SELECT("IDINSTITUCION");
		}
		sql.SELECT("IDPROCEDIMIENTO");
		sql.SELECT("NOMBRE");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("PRECIO");
		sql.SELECT("IDJURISDICCION");
		sql.SELECT("CODIGO");
		sql.SELECT("COMPLEMENTO");
		sql.SELECT("VIGENTE");
		sql.SELECT("ORDEN");
		sql.SELECT("CODIGOEXT");
		sql.SELECT("PERMITIRANIADIRLETRADO");
		sql.SELECT("FECHADESDEVIGOR");
		sql.SELECT("FECHAHASTAVIGOR");
		sql.SELECT("FECHABAJA");
		sql.SELECT("OBSERVACIONES");
		sql.FROM("SCS_PROCEDIMIENTOS");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PROCEDIMIENTOS
	 * @mbg.generated  Wed Sep 18 13:54:36 CEST 2019
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		ScsProcedimientos record = (ScsProcedimientos) parameter.get("record");
		ScsProcedimientosExample example = (ScsProcedimientosExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("SCS_PROCEDIMIENTOS");
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdprocedimiento() != null) {
			sql.SET("IDPROCEDIMIENTO = #{record.idprocedimiento,jdbcType=VARCHAR}");
		}
		if (record.getNombre() != null) {
			sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getPrecio() != null) {
			sql.SET("PRECIO = #{record.precio,jdbcType=DECIMAL}");
		}
		if (record.getIdjurisdiccion() != null) {
			sql.SET("IDJURISDICCION = #{record.idjurisdiccion,jdbcType=DECIMAL}");
		}
		if (record.getCodigo() != null) {
			sql.SET("CODIGO = #{record.codigo,jdbcType=VARCHAR}");
		}
		if (record.getComplemento() != null) {
			sql.SET("COMPLEMENTO = #{record.complemento,jdbcType=VARCHAR}");
		}
		if (record.getVigente() != null) {
			sql.SET("VIGENTE = #{record.vigente,jdbcType=VARCHAR}");
		}
		if (record.getOrden() != null) {
			sql.SET("ORDEN = #{record.orden,jdbcType=DECIMAL}");
		}
		if (record.getCodigoext() != null) {
			sql.SET("CODIGOEXT = #{record.codigoext,jdbcType=VARCHAR}");
		}
		if (record.getPermitiraniadirletrado() != null) {
			sql.SET("PERMITIRANIADIRLETRADO = #{record.permitiraniadirletrado,jdbcType=VARCHAR}");
		}
		if (record.getFechadesdevigor() != null) {
			sql.SET("FECHADESDEVIGOR = #{record.fechadesdevigor,jdbcType=TIMESTAMP}");
		}
		if (record.getFechahastavigor() != null) {
			sql.SET("FECHAHASTAVIGOR = #{record.fechahastavigor,jdbcType=TIMESTAMP}");
		}
		if (record.getFechabaja() != null) {
			sql.SET("FECHABAJA = #{record.fechabaja,jdbcType=TIMESTAMP}");
		}
		if (record.getObservaciones() != null) {
			sql.SET("OBSERVACIONES = #{record.observaciones,jdbcType=VARCHAR}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PROCEDIMIENTOS
	 * @mbg.generated  Wed Sep 18 13:54:36 CEST 2019
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("SCS_PROCEDIMIENTOS");
		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		sql.SET("IDPROCEDIMIENTO = #{record.idprocedimiento,jdbcType=VARCHAR}");
		sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("PRECIO = #{record.precio,jdbcType=DECIMAL}");
		sql.SET("IDJURISDICCION = #{record.idjurisdiccion,jdbcType=DECIMAL}");
		sql.SET("CODIGO = #{record.codigo,jdbcType=VARCHAR}");
		sql.SET("COMPLEMENTO = #{record.complemento,jdbcType=VARCHAR}");
		sql.SET("VIGENTE = #{record.vigente,jdbcType=VARCHAR}");
		sql.SET("ORDEN = #{record.orden,jdbcType=DECIMAL}");
		sql.SET("CODIGOEXT = #{record.codigoext,jdbcType=VARCHAR}");
		sql.SET("PERMITIRANIADIRLETRADO = #{record.permitiraniadirletrado,jdbcType=VARCHAR}");
		sql.SET("FECHADESDEVIGOR = #{record.fechadesdevigor,jdbcType=TIMESTAMP}");
		sql.SET("FECHAHASTAVIGOR = #{record.fechahastavigor,jdbcType=TIMESTAMP}");
		sql.SET("FECHABAJA = #{record.fechabaja,jdbcType=TIMESTAMP}");
		sql.SET("OBSERVACIONES = #{record.observaciones,jdbcType=VARCHAR}");
		ScsProcedimientosExample example = (ScsProcedimientosExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PROCEDIMIENTOS
	 * @mbg.generated  Wed Sep 18 13:54:36 CEST 2019
	 */
	public String updateByPrimaryKeySelective(ScsProcedimientos record) {
		SQL sql = new SQL();
		sql.UPDATE("SCS_PROCEDIMIENTOS");
		if (record.getNombre() != null) {
			sql.SET("NOMBRE = #{nombre,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getPrecio() != null) {
			sql.SET("PRECIO = #{precio,jdbcType=DECIMAL}");
		}
		if (record.getIdjurisdiccion() != null) {
			sql.SET("IDJURISDICCION = #{idjurisdiccion,jdbcType=DECIMAL}");
		}
		if (record.getCodigo() != null) {
			sql.SET("CODIGO = #{codigo,jdbcType=VARCHAR}");
		}
		if (record.getComplemento() != null) {
			sql.SET("COMPLEMENTO = #{complemento,jdbcType=VARCHAR}");
		}
		if (record.getVigente() != null) {
			sql.SET("VIGENTE = #{vigente,jdbcType=VARCHAR}");
		}
		if (record.getOrden() != null) {
			sql.SET("ORDEN = #{orden,jdbcType=DECIMAL}");
		}
		if (record.getCodigoext() != null) {
			sql.SET("CODIGOEXT = #{codigoext,jdbcType=VARCHAR}");
		}
		if (record.getPermitiraniadirletrado() != null) {
			sql.SET("PERMITIRANIADIRLETRADO = #{permitiraniadirletrado,jdbcType=VARCHAR}");
		}
		if (record.getFechadesdevigor() != null) {
			sql.SET("FECHADESDEVIGOR = #{fechadesdevigor,jdbcType=TIMESTAMP}");
		}
		if (record.getFechahastavigor() != null) {
			sql.SET("FECHAHASTAVIGOR = #{fechahastavigor,jdbcType=TIMESTAMP}");
		}
		if (record.getFechabaja() != null) {
			sql.SET("FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP}");
		}
		if (record.getObservaciones() != null) {
			sql.SET("OBSERVACIONES = #{observaciones,jdbcType=VARCHAR}");
		}
		sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
		sql.WHERE("IDPROCEDIMIENTO = #{idprocedimiento,jdbcType=VARCHAR}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PROCEDIMIENTOS
	 * @mbg.generated  Wed Sep 18 13:54:36 CEST 2019
	 */
	protected void applyWhere(SQL sql, ScsProcedimientosExample example, boolean includeExamplePhrase) {
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