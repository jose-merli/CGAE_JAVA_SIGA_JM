package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.ScsComisaria;
import org.itcgae.siga.db.entities.ScsComisariaExample.Criteria;
import org.itcgae.siga.db.entities.ScsComisariaExample.Criterion;
import org.itcgae.siga.db.entities.ScsComisariaExample;

public class ScsComisariaSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_COMISARIA
	 * @mbg.generated  Mon Sep 30 13:03:39 CEST 2019
	 */
	public String countByExample(ScsComisariaExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("SCS_COMISARIA");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_COMISARIA
	 * @mbg.generated  Mon Sep 30 13:03:39 CEST 2019
	 */
	public String deleteByExample(ScsComisariaExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("SCS_COMISARIA");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_COMISARIA
	 * @mbg.generated  Mon Sep 30 13:03:39 CEST 2019
	 */
	public String insertSelective(ScsComisaria record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("SCS_COMISARIA");
		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdcomisaria() != null) {
			sql.VALUES("IDCOMISARIA", "#{idcomisaria,jdbcType=DECIMAL}");
		}
		if (record.getNombre() != null) {
			sql.VALUES("NOMBRE", "#{nombre,jdbcType=VARCHAR}");
		}
		if (record.getDomicilio() != null) {
			sql.VALUES("DOMICILIO", "#{domicilio,jdbcType=VARCHAR}");
		}
		if (record.getCodigopostal() != null) {
			sql.VALUES("CODIGOPOSTAL", "#{codigopostal,jdbcType=VARCHAR}");
		}
		if (record.getIdprovincia() != null) {
			sql.VALUES("IDPROVINCIA", "#{idprovincia,jdbcType=VARCHAR}");
		}
		if (record.getIdpoblacion() != null) {
			sql.VALUES("IDPOBLACION", "#{idpoblacion,jdbcType=VARCHAR}");
		}
		if (record.getTelefono1() != null) {
			sql.VALUES("TELEFONO1", "#{telefono1,jdbcType=VARCHAR}");
		}
		if (record.getTelefono2() != null) {
			sql.VALUES("TELEFONO2", "#{telefono2,jdbcType=VARCHAR}");
		}
		if (record.getFax1() != null) {
			sql.VALUES("FAX1", "#{fax1,jdbcType=VARCHAR}");
		}
		if (record.getFechabaja() != null) {
			sql.VALUES("FECHABAJA", "#{fechabaja,jdbcType=TIMESTAMP}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getCodigoext() != null) {
			sql.VALUES("CODIGOEXT", "#{codigoext,jdbcType=VARCHAR}");
		}
		if (record.getVisiblemovil() != null) {
			sql.VALUES("VISIBLEMOVIL", "#{visiblemovil,jdbcType=DECIMAL}");
		}
		if (record.getEmail() != null) {
			sql.VALUES("EMAIL", "#{email,jdbcType=VARCHAR}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_COMISARIA
	 * @mbg.generated  Mon Sep 30 13:03:39 CEST 2019
	 */
	public String selectByExample(ScsComisariaExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDINSTITUCION");
		} else {
			sql.SELECT("IDINSTITUCION");
		}
		sql.SELECT("IDCOMISARIA");
		sql.SELECT("NOMBRE");
		sql.SELECT("DOMICILIO");
		sql.SELECT("CODIGOPOSTAL");
		sql.SELECT("IDPROVINCIA");
		sql.SELECT("IDPOBLACION");
		sql.SELECT("TELEFONO1");
		sql.SELECT("TELEFONO2");
		sql.SELECT("FAX1");
		sql.SELECT("FECHABAJA");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("CODIGOEXT");
		sql.SELECT("VISIBLEMOVIL");
		sql.SELECT("EMAIL");
		sql.FROM("SCS_COMISARIA");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_COMISARIA
	 * @mbg.generated  Mon Sep 30 13:03:39 CEST 2019
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		ScsComisaria record = (ScsComisaria) parameter.get("record");
		ScsComisariaExample example = (ScsComisariaExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("SCS_COMISARIA");
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdcomisaria() != null) {
			sql.SET("IDCOMISARIA = #{record.idcomisaria,jdbcType=DECIMAL}");
		}
		if (record.getNombre() != null) {
			sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
		}
		if (record.getDomicilio() != null) {
			sql.SET("DOMICILIO = #{record.domicilio,jdbcType=VARCHAR}");
		}
		if (record.getCodigopostal() != null) {
			sql.SET("CODIGOPOSTAL = #{record.codigopostal,jdbcType=VARCHAR}");
		}
		if (record.getIdprovincia() != null) {
			sql.SET("IDPROVINCIA = #{record.idprovincia,jdbcType=VARCHAR}");
		}
		if (record.getIdpoblacion() != null) {
			sql.SET("IDPOBLACION = #{record.idpoblacion,jdbcType=VARCHAR}");
		}
		if (record.getTelefono1() != null) {
			sql.SET("TELEFONO1 = #{record.telefono1,jdbcType=VARCHAR}");
		}
		if (record.getTelefono2() != null) {
			sql.SET("TELEFONO2 = #{record.telefono2,jdbcType=VARCHAR}");
		}
		if (record.getFax1() != null) {
			sql.SET("FAX1 = #{record.fax1,jdbcType=VARCHAR}");
		}
		if (record.getFechabaja() != null) {
			sql.SET("FECHABAJA = #{record.fechabaja,jdbcType=TIMESTAMP}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getCodigoext() != null) {
			sql.SET("CODIGOEXT = #{record.codigoext,jdbcType=VARCHAR}");
		}
		if (record.getVisiblemovil() != null) {
			sql.SET("VISIBLEMOVIL = #{record.visiblemovil,jdbcType=DECIMAL}");
		}
		if (record.getEmail() != null) {
			sql.SET("EMAIL = #{record.email,jdbcType=VARCHAR}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_COMISARIA
	 * @mbg.generated  Mon Sep 30 13:03:40 CEST 2019
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("SCS_COMISARIA");
		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		sql.SET("IDCOMISARIA = #{record.idcomisaria,jdbcType=DECIMAL}");
		sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
		sql.SET("DOMICILIO = #{record.domicilio,jdbcType=VARCHAR}");
		sql.SET("CODIGOPOSTAL = #{record.codigopostal,jdbcType=VARCHAR}");
		sql.SET("IDPROVINCIA = #{record.idprovincia,jdbcType=VARCHAR}");
		sql.SET("IDPOBLACION = #{record.idpoblacion,jdbcType=VARCHAR}");
		sql.SET("TELEFONO1 = #{record.telefono1,jdbcType=VARCHAR}");
		sql.SET("TELEFONO2 = #{record.telefono2,jdbcType=VARCHAR}");
		sql.SET("FAX1 = #{record.fax1,jdbcType=VARCHAR}");
		sql.SET("FECHABAJA = #{record.fechabaja,jdbcType=TIMESTAMP}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("CODIGOEXT = #{record.codigoext,jdbcType=VARCHAR}");
		sql.SET("VISIBLEMOVIL = #{record.visiblemovil,jdbcType=DECIMAL}");
		sql.SET("EMAIL = #{record.email,jdbcType=VARCHAR}");
		ScsComisariaExample example = (ScsComisariaExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_COMISARIA
	 * @mbg.generated  Mon Sep 30 13:03:40 CEST 2019
	 */
	public String updateByPrimaryKeySelective(ScsComisaria record) {
		SQL sql = new SQL();
		sql.UPDATE("SCS_COMISARIA");
		if (record.getNombre() != null) {
			sql.SET("NOMBRE = #{nombre,jdbcType=VARCHAR}");
		}
		if (record.getDomicilio() != null) {
			sql.SET("DOMICILIO = #{domicilio,jdbcType=VARCHAR}");
		}
		if (record.getCodigopostal() != null) {
			sql.SET("CODIGOPOSTAL = #{codigopostal,jdbcType=VARCHAR}");
		}
		if (record.getIdprovincia() != null) {
			sql.SET("IDPROVINCIA = #{idprovincia,jdbcType=VARCHAR}");
		}
		if (record.getIdpoblacion() != null) {
			sql.SET("IDPOBLACION = #{idpoblacion,jdbcType=VARCHAR}");
		}
		if (record.getTelefono1() != null) {
			sql.SET("TELEFONO1 = #{telefono1,jdbcType=VARCHAR}");
		}
		if (record.getTelefono2() != null) {
			sql.SET("TELEFONO2 = #{telefono2,jdbcType=VARCHAR}");
		}
		if (record.getFax1() != null) {
			sql.SET("FAX1 = #{fax1,jdbcType=VARCHAR}");
		}
		if (record.getFechabaja() != null) {
			sql.SET("FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getCodigoext() != null) {
			sql.SET("CODIGOEXT = #{codigoext,jdbcType=VARCHAR}");
		}
		if (record.getVisiblemovil() != null) {
			sql.SET("VISIBLEMOVIL = #{visiblemovil,jdbcType=DECIMAL}");
		}
		if (record.getEmail() != null) {
			sql.SET("EMAIL = #{email,jdbcType=VARCHAR}");
		}
		sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
		sql.WHERE("IDCOMISARIA = #{idcomisaria,jdbcType=DECIMAL}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_COMISARIA
	 * @mbg.generated  Mon Sep 30 13:03:40 CEST 2019
	 */
	protected void applyWhere(SQL sql, ScsComisariaExample example, boolean includeExamplePhrase) {
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