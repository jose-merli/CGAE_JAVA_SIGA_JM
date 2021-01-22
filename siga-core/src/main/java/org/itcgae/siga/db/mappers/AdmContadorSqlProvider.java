package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.entities.AdmContadorExample;
import org.itcgae.siga.db.entities.AdmContadorExample.Criteria;
import org.itcgae.siga.db.entities.AdmContadorExample.Criterion;

public class AdmContadorSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_CONTADOR
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String countByExample(AdmContadorExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("ADM_CONTADOR");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_CONTADOR
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String deleteByExample(AdmContadorExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("ADM_CONTADOR");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_CONTADOR
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String insertSelective(AdmContador record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("ADM_CONTADOR");
		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdcontador() != null) {
			sql.VALUES("IDCONTADOR", "#{idcontador,jdbcType=VARCHAR}");
		}
		if (record.getNombre() != null) {
			sql.VALUES("NOMBRE", "#{nombre,jdbcType=VARCHAR}");
		}
		if (record.getDescripcion() != null) {
			sql.VALUES("DESCRIPCION", "#{descripcion,jdbcType=VARCHAR}");
		}
		if (record.getModificablecontador() != null) {
			sql.VALUES("MODIFICABLECONTADOR", "#{modificablecontador,jdbcType=VARCHAR}");
		}
		if (record.getModo() != null) {
			sql.VALUES("MODO", "#{modo,jdbcType=DECIMAL}");
		}
		if (record.getContador() != null) {
			sql.VALUES("CONTADOR", "#{contador,jdbcType=DECIMAL}");
		}
		if (record.getPrefijo() != null) {
			sql.VALUES("PREFIJO", "#{prefijo,jdbcType=VARCHAR}");
		}
		if (record.getSufijo() != null) {
			sql.VALUES("SUFIJO", "#{sufijo,jdbcType=VARCHAR}");
		}
		if (record.getLongitudcontador() != null) {
			sql.VALUES("LONGITUDCONTADOR", "#{longitudcontador,jdbcType=DECIMAL}");
		}
		if (record.getFechareconfiguracion() != null) {
			sql.VALUES("FECHARECONFIGURACION", "#{fechareconfiguracion,jdbcType=TIMESTAMP}");
		}
		if (record.getReconfiguracioncontador() != null) {
			sql.VALUES("RECONFIGURACIONCONTADOR", "#{reconfiguracioncontador,jdbcType=VARCHAR}");
		}
		if (record.getReconfiguracionprefijo() != null) {
			sql.VALUES("RECONFIGURACIONPREFIJO", "#{reconfiguracionprefijo,jdbcType=VARCHAR}");
		}
		if (record.getReconfiguracionsufijo() != null) {
			sql.VALUES("RECONFIGURACIONSUFIJO", "#{reconfiguracionsufijo,jdbcType=VARCHAR}");
		}
		if (record.getIdtabla() != null) {
			sql.VALUES("IDTABLA", "#{idtabla,jdbcType=VARCHAR}");
		}
		if (record.getIdcampocontador() != null) {
			sql.VALUES("IDCAMPOCONTADOR", "#{idcampocontador,jdbcType=VARCHAR}");
		}
		if (record.getIdcampoprefijo() != null) {
			sql.VALUES("IDCAMPOPREFIJO", "#{idcampoprefijo,jdbcType=VARCHAR}");
		}
		if (record.getIdcamposufijo() != null) {
			sql.VALUES("IDCAMPOSUFIJO", "#{idcamposufijo,jdbcType=VARCHAR}");
		}
		if (record.getIdmodulo() != null) {
			sql.VALUES("IDMODULO", "#{idmodulo,jdbcType=DECIMAL}");
		}
		if (record.getGeneral() != null) {
			sql.VALUES("GENERAL", "#{general,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getFechacreacion() != null) {
			sql.VALUES("FECHACREACION", "#{fechacreacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsucreacion() != null) {
			sql.VALUES("USUCREACION", "#{usucreacion,jdbcType=DECIMAL}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_CONTADOR
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String selectByExample(AdmContadorExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDINSTITUCION");
		} else {
			sql.SELECT("IDINSTITUCION");
		}
		sql.SELECT("IDCONTADOR");
		sql.SELECT("NOMBRE");
		sql.SELECT("DESCRIPCION");
		sql.SELECT("MODIFICABLECONTADOR");
		sql.SELECT("MODO");
		sql.SELECT("CONTADOR");
		sql.SELECT("PREFIJO");
		sql.SELECT("SUFIJO");
		sql.SELECT("LONGITUDCONTADOR");
		sql.SELECT("FECHARECONFIGURACION");
		sql.SELECT("RECONFIGURACIONCONTADOR");
		sql.SELECT("RECONFIGURACIONPREFIJO");
		sql.SELECT("RECONFIGURACIONSUFIJO");
		sql.SELECT("IDTABLA");
		sql.SELECT("IDCAMPOCONTADOR");
		sql.SELECT("IDCAMPOPREFIJO");
		sql.SELECT("IDCAMPOSUFIJO");
		sql.SELECT("IDMODULO");
		sql.SELECT("GENERAL");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("FECHACREACION");
		sql.SELECT("USUCREACION");
		sql.FROM("ADM_CONTADOR");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_CONTADOR
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		AdmContador record = (AdmContador) parameter.get("record");
		AdmContadorExample example = (AdmContadorExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("ADM_CONTADOR");
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdcontador() != null) {
			sql.SET("IDCONTADOR = #{record.idcontador,jdbcType=VARCHAR}");
		}
		if (record.getNombre() != null) {
			sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
		}
		if (record.getDescripcion() != null) {
			sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
		}
		if (record.getModificablecontador() != null) {
			sql.SET("MODIFICABLECONTADOR = #{record.modificablecontador,jdbcType=VARCHAR}");
		}
		if (record.getModo() != null) {
			sql.SET("MODO = #{record.modo,jdbcType=DECIMAL}");
		}
		if (record.getContador() != null) {
			sql.SET("CONTADOR = #{record.contador,jdbcType=DECIMAL}");
		}
		if (record.getPrefijo() != null) {
			sql.SET("PREFIJO = #{record.prefijo,jdbcType=VARCHAR}");
		}
		if (record.getSufijo() != null) {
			sql.SET("SUFIJO = #{record.sufijo,jdbcType=VARCHAR}");
		}
		if (record.getLongitudcontador() != null) {
			sql.SET("LONGITUDCONTADOR = #{record.longitudcontador,jdbcType=DECIMAL}");
		}
		if (record.getFechareconfiguracion() != null) {
			sql.SET("FECHARECONFIGURACION = #{record.fechareconfiguracion,jdbcType=TIMESTAMP}");
		}
		if (record.getReconfiguracioncontador() != null) {
			sql.SET("RECONFIGURACIONCONTADOR = #{record.reconfiguracioncontador,jdbcType=VARCHAR}");
		}
		if (record.getReconfiguracionprefijo() != null) {
			sql.SET("RECONFIGURACIONPREFIJO = #{record.reconfiguracionprefijo,jdbcType=VARCHAR}");
		}
		if (record.getReconfiguracionsufijo() != null) {
			sql.SET("RECONFIGURACIONSUFIJO = #{record.reconfiguracionsufijo,jdbcType=VARCHAR}");
		}
		if (record.getIdtabla() != null) {
			sql.SET("IDTABLA = #{record.idtabla,jdbcType=VARCHAR}");
		}
		if (record.getIdcampocontador() != null) {
			sql.SET("IDCAMPOCONTADOR = #{record.idcampocontador,jdbcType=VARCHAR}");
		}
		if (record.getIdcampoprefijo() != null) {
			sql.SET("IDCAMPOPREFIJO = #{record.idcampoprefijo,jdbcType=VARCHAR}");
		}
		if (record.getIdcamposufijo() != null) {
			sql.SET("IDCAMPOSUFIJO = #{record.idcamposufijo,jdbcType=VARCHAR}");
		}
		if (record.getIdmodulo() != null) {
			sql.SET("IDMODULO = #{record.idmodulo,jdbcType=DECIMAL}");
		}
		if (record.getGeneral() != null) {
			sql.SET("GENERAL = #{record.general,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getFechacreacion() != null) {
			sql.SET("FECHACREACION = #{record.fechacreacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsucreacion() != null) {
			sql.SET("USUCREACION = #{record.usucreacion,jdbcType=DECIMAL}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_CONTADOR
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("ADM_CONTADOR");
		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		sql.SET("IDCONTADOR = #{record.idcontador,jdbcType=VARCHAR}");
		sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
		sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
		sql.SET("MODIFICABLECONTADOR = #{record.modificablecontador,jdbcType=VARCHAR}");
		sql.SET("MODO = #{record.modo,jdbcType=DECIMAL}");
		sql.SET("CONTADOR = #{record.contador,jdbcType=DECIMAL}");
		sql.SET("PREFIJO = #{record.prefijo,jdbcType=VARCHAR}");
		sql.SET("SUFIJO = #{record.sufijo,jdbcType=VARCHAR}");
		sql.SET("LONGITUDCONTADOR = #{record.longitudcontador,jdbcType=DECIMAL}");
		sql.SET("FECHARECONFIGURACION = #{record.fechareconfiguracion,jdbcType=TIMESTAMP}");
		sql.SET("RECONFIGURACIONCONTADOR = #{record.reconfiguracioncontador,jdbcType=VARCHAR}");
		sql.SET("RECONFIGURACIONPREFIJO = #{record.reconfiguracionprefijo,jdbcType=VARCHAR}");
		sql.SET("RECONFIGURACIONSUFIJO = #{record.reconfiguracionsufijo,jdbcType=VARCHAR}");
		sql.SET("IDTABLA = #{record.idtabla,jdbcType=VARCHAR}");
		sql.SET("IDCAMPOCONTADOR = #{record.idcampocontador,jdbcType=VARCHAR}");
		sql.SET("IDCAMPOPREFIJO = #{record.idcampoprefijo,jdbcType=VARCHAR}");
		sql.SET("IDCAMPOSUFIJO = #{record.idcamposufijo,jdbcType=VARCHAR}");
		sql.SET("IDMODULO = #{record.idmodulo,jdbcType=DECIMAL}");
		sql.SET("GENERAL = #{record.general,jdbcType=VARCHAR}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("FECHACREACION = #{record.fechacreacion,jdbcType=TIMESTAMP}");
		sql.SET("USUCREACION = #{record.usucreacion,jdbcType=DECIMAL}");
		AdmContadorExample example = (AdmContadorExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_CONTADOR
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByPrimaryKeySelective(AdmContador record) {
		SQL sql = new SQL();
		sql.UPDATE("ADM_CONTADOR");
		if (record.getNombre() != null) {
			sql.SET("NOMBRE = #{nombre,jdbcType=VARCHAR}");
		}
		if (record.getDescripcion() != null) {
			sql.SET("DESCRIPCION = #{descripcion,jdbcType=VARCHAR}");
		}
		if (record.getModificablecontador() != null) {
			sql.SET("MODIFICABLECONTADOR = #{modificablecontador,jdbcType=VARCHAR}");
		}
		if (record.getModo() != null) {
			sql.SET("MODO = #{modo,jdbcType=DECIMAL}");
		}
		if (record.getContador() != null) {
			sql.SET("CONTADOR = #{contador,jdbcType=DECIMAL}");
		}
		if (record.getPrefijo() != null) {
			sql.SET("PREFIJO = #{prefijo,jdbcType=VARCHAR}");
		}
		if (record.getSufijo() != null) {
			sql.SET("SUFIJO = #{sufijo,jdbcType=VARCHAR}");
		}
		if (record.getLongitudcontador() != null) {
			sql.SET("LONGITUDCONTADOR = #{longitudcontador,jdbcType=DECIMAL}");
		}
		if (record.getFechareconfiguracion() != null) {
			sql.SET("FECHARECONFIGURACION = #{fechareconfiguracion,jdbcType=TIMESTAMP}");
		}
		if (record.getReconfiguracioncontador() != null) {
			sql.SET("RECONFIGURACIONCONTADOR = #{reconfiguracioncontador,jdbcType=VARCHAR}");
		}
		if (record.getReconfiguracionprefijo() != null) {
			sql.SET("RECONFIGURACIONPREFIJO = #{reconfiguracionprefijo,jdbcType=VARCHAR}");
		}
		if (record.getReconfiguracionsufijo() != null) {
			sql.SET("RECONFIGURACIONSUFIJO = #{reconfiguracionsufijo,jdbcType=VARCHAR}");
		}
		if (record.getIdtabla() != null) {
			sql.SET("IDTABLA = #{idtabla,jdbcType=VARCHAR}");
		}
		if (record.getIdcampocontador() != null) {
			sql.SET("IDCAMPOCONTADOR = #{idcampocontador,jdbcType=VARCHAR}");
		}
		if (record.getIdcampoprefijo() != null) {
			sql.SET("IDCAMPOPREFIJO = #{idcampoprefijo,jdbcType=VARCHAR}");
		}
		if (record.getIdcamposufijo() != null) {
			sql.SET("IDCAMPOSUFIJO = #{idcamposufijo,jdbcType=VARCHAR}");
		}
		if (record.getIdmodulo() != null) {
			sql.SET("IDMODULO = #{idmodulo,jdbcType=DECIMAL}");
		}
		if (record.getGeneral() != null) {
			sql.SET("GENERAL = #{general,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getFechacreacion() != null) {
			sql.SET("FECHACREACION = #{fechacreacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsucreacion() != null) {
			sql.SET("USUCREACION = #{usucreacion,jdbcType=DECIMAL}");
		}
		sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
		sql.WHERE("IDCONTADOR = #{idcontador,jdbcType=VARCHAR}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_CONTADOR
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	protected void applyWhere(SQL sql, AdmContadorExample example, boolean includeExamplePhrase) {
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