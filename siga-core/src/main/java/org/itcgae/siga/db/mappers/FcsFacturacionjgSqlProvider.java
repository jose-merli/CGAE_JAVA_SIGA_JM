package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.FcsFacturacionjg;
import org.itcgae.siga.db.entities.FcsFacturacionjgExample.Criteria;
import org.itcgae.siga.db.entities.FcsFacturacionjgExample.Criterion;
import org.itcgae.siga.db.entities.FcsFacturacionjgExample;

public class FcsFacturacionjgSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACTURACIONJG
	 * @mbg.generated  Fri Dec 13 09:35:30 CET 2019
	 */
	public String countByExample(FcsFacturacionjgExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("FCS_FACTURACIONJG");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACTURACIONJG
	 * @mbg.generated  Fri Dec 13 09:35:30 CET 2019
	 */
	public String deleteByExample(FcsFacturacionjgExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("FCS_FACTURACIONJG");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACTURACIONJG
	 * @mbg.generated  Fri Dec 13 09:35:30 CET 2019
	 */
	public String insertSelective(FcsFacturacionjg record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("FCS_FACTURACIONJG");
		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdfacturacion() != null) {
			sql.VALUES("IDFACTURACION", "#{idfacturacion,jdbcType=DECIMAL}");
		}
		if (record.getFechadesde() != null) {
			sql.VALUES("FECHADESDE", "#{fechadesde,jdbcType=TIMESTAMP}");
		}
		if (record.getFechahasta() != null) {
			sql.VALUES("FECHAHASTA", "#{fechahasta,jdbcType=TIMESTAMP}");
		}
		if (record.getNombre() != null) {
			sql.VALUES("NOMBRE", "#{nombre,jdbcType=VARCHAR}");
		}
		if (record.getImportetotal() != null) {
			sql.VALUES("IMPORTETOTAL", "#{importetotal,jdbcType=DECIMAL}");
		}
		if (record.getImporteoficio() != null) {
			sql.VALUES("IMPORTEOFICIO", "#{importeoficio,jdbcType=DECIMAL}");
		}
		if (record.getImporteguardia() != null) {
			sql.VALUES("IMPORTEGUARDIA", "#{importeguardia,jdbcType=DECIMAL}");
		}
		if (record.getImportesoj() != null) {
			sql.VALUES("IMPORTESOJ", "#{importesoj,jdbcType=DECIMAL}");
		}
		if (record.getImporteejg() != null) {
			sql.VALUES("IMPORTEEJG", "#{importeejg,jdbcType=DECIMAL}");
		}
		if (record.getPrevision() != null) {
			sql.VALUES("PREVISION", "#{prevision,jdbcType=VARCHAR}");
		}
		if (record.getRegularizacion() != null) {
			sql.VALUES("REGULARIZACION", "#{regularizacion,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdfacturacionRegulariza() != null) {
			sql.VALUES("IDFACTURACION_REGULARIZA", "#{idfacturacionRegulariza,jdbcType=DECIMAL}");
		}
		if (record.getNombrefisico() != null) {
			sql.VALUES("NOMBREFISICO", "#{nombrefisico,jdbcType=VARCHAR}");
		}
		if (record.getIdecomcola() != null) {
			sql.VALUES("IDECOMCOLA", "#{idecomcola,jdbcType=DECIMAL}");
		}
		if (record.getVisible() != null) {
			sql.VALUES("VISIBLE", "#{visible,jdbcType=VARCHAR}");
		}
		if (record.getIdpartidapresupuestaria() != null) {
			sql.VALUES("IDPARTIDAPRESUPUESTARIA", "#{idpartidapresupuestaria,jdbcType=DECIMAL}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACTURACIONJG
	 * @mbg.generated  Fri Dec 13 09:35:30 CET 2019
	 */
	public String selectByExample(FcsFacturacionjgExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDINSTITUCION");
		} else {
			sql.SELECT("IDINSTITUCION");
		}
		sql.SELECT("IDFACTURACION");
		sql.SELECT("FECHADESDE");
		sql.SELECT("FECHAHASTA");
		sql.SELECT("NOMBRE");
		sql.SELECT("IMPORTETOTAL");
		sql.SELECT("IMPORTEOFICIO");
		sql.SELECT("IMPORTEGUARDIA");
		sql.SELECT("IMPORTESOJ");
		sql.SELECT("IMPORTEEJG");
		sql.SELECT("PREVISION");
		sql.SELECT("REGULARIZACION");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("IDFACTURACION_REGULARIZA");
		sql.SELECT("NOMBREFISICO");
		sql.SELECT("IDECOMCOLA");
		sql.SELECT("VISIBLE");
		sql.SELECT("IDPARTIDAPRESUPUESTARIA");
		sql.FROM("FCS_FACTURACIONJG");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACTURACIONJG
	 * @mbg.generated  Fri Dec 13 09:35:30 CET 2019
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		FcsFacturacionjg record = (FcsFacturacionjg) parameter.get("record");
		FcsFacturacionjgExample example = (FcsFacturacionjgExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("FCS_FACTURACIONJG");
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdfacturacion() != null) {
			sql.SET("IDFACTURACION = #{record.idfacturacion,jdbcType=DECIMAL}");
		}
		if (record.getFechadesde() != null) {
			sql.SET("FECHADESDE = #{record.fechadesde,jdbcType=TIMESTAMP}");
		}
		if (record.getFechahasta() != null) {
			sql.SET("FECHAHASTA = #{record.fechahasta,jdbcType=TIMESTAMP}");
		}
		if (record.getNombre() != null) {
			sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
		}
		if (record.getImportetotal() != null) {
			sql.SET("IMPORTETOTAL = #{record.importetotal,jdbcType=DECIMAL}");
		}
		if (record.getImporteoficio() != null) {
			sql.SET("IMPORTEOFICIO = #{record.importeoficio,jdbcType=DECIMAL}");
		}
		if (record.getImporteguardia() != null) {
			sql.SET("IMPORTEGUARDIA = #{record.importeguardia,jdbcType=DECIMAL}");
		}
		if (record.getImportesoj() != null) {
			sql.SET("IMPORTESOJ = #{record.importesoj,jdbcType=DECIMAL}");
		}
		if (record.getImporteejg() != null) {
			sql.SET("IMPORTEEJG = #{record.importeejg,jdbcType=DECIMAL}");
		}
		if (record.getPrevision() != null) {
			sql.SET("PREVISION = #{record.prevision,jdbcType=VARCHAR}");
		}
		if (record.getRegularizacion() != null) {
			sql.SET("REGULARIZACION = #{record.regularizacion,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdfacturacionRegulariza() != null) {
			sql.SET("IDFACTURACION_REGULARIZA = #{record.idfacturacionRegulariza,jdbcType=DECIMAL}");
		}
		if (record.getNombrefisico() != null) {
			sql.SET("NOMBREFISICO = #{record.nombrefisico,jdbcType=VARCHAR}");
		}
		if (record.getIdecomcola() != null) {
			sql.SET("IDECOMCOLA = #{record.idecomcola,jdbcType=DECIMAL}");
		}
		if (record.getVisible() != null) {
			sql.SET("VISIBLE = #{record.visible,jdbcType=VARCHAR}");
		}
		if (record.getIdpartidapresupuestaria() != null) {
			sql.SET("IDPARTIDAPRESUPUESTARIA = #{record.idpartidapresupuestaria,jdbcType=DECIMAL}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACTURACIONJG
	 * @mbg.generated  Fri Dec 13 09:35:30 CET 2019
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("FCS_FACTURACIONJG");
		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		sql.SET("IDFACTURACION = #{record.idfacturacion,jdbcType=DECIMAL}");
		sql.SET("FECHADESDE = #{record.fechadesde,jdbcType=TIMESTAMP}");
		sql.SET("FECHAHASTA = #{record.fechahasta,jdbcType=TIMESTAMP}");
		sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
		sql.SET("IMPORTETOTAL = #{record.importetotal,jdbcType=DECIMAL}");
		sql.SET("IMPORTEOFICIO = #{record.importeoficio,jdbcType=DECIMAL}");
		sql.SET("IMPORTEGUARDIA = #{record.importeguardia,jdbcType=DECIMAL}");
		sql.SET("IMPORTESOJ = #{record.importesoj,jdbcType=DECIMAL}");
		sql.SET("IMPORTEEJG = #{record.importeejg,jdbcType=DECIMAL}");
		sql.SET("PREVISION = #{record.prevision,jdbcType=VARCHAR}");
		sql.SET("REGULARIZACION = #{record.regularizacion,jdbcType=VARCHAR}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("IDFACTURACION_REGULARIZA = #{record.idfacturacionRegulariza,jdbcType=DECIMAL}");
		sql.SET("NOMBREFISICO = #{record.nombrefisico,jdbcType=VARCHAR}");
		sql.SET("IDECOMCOLA = #{record.idecomcola,jdbcType=DECIMAL}");
		sql.SET("VISIBLE = #{record.visible,jdbcType=VARCHAR}");
		sql.SET("IDPARTIDAPRESUPUESTARIA = #{record.idpartidapresupuestaria,jdbcType=DECIMAL}");
		FcsFacturacionjgExample example = (FcsFacturacionjgExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACTURACIONJG
	 * @mbg.generated  Fri Dec 13 09:35:30 CET 2019
	 */
	public String updateByPrimaryKeySelective(FcsFacturacionjg record) {
		SQL sql = new SQL();
		sql.UPDATE("FCS_FACTURACIONJG");
		if (record.getFechadesde() != null) {
			sql.SET("FECHADESDE = #{fechadesde,jdbcType=TIMESTAMP}");
		}
		if (record.getFechahasta() != null) {
			sql.SET("FECHAHASTA = #{fechahasta,jdbcType=TIMESTAMP}");
		}
		if (record.getNombre() != null) {
			sql.SET("NOMBRE = #{nombre,jdbcType=VARCHAR}");
		}
		if (record.getImportetotal() != null) {
			sql.SET("IMPORTETOTAL = #{importetotal,jdbcType=DECIMAL}");
		}
		if (record.getImporteoficio() != null) {
			sql.SET("IMPORTEOFICIO = #{importeoficio,jdbcType=DECIMAL}");
		}
		if (record.getImporteguardia() != null) {
			sql.SET("IMPORTEGUARDIA = #{importeguardia,jdbcType=DECIMAL}");
		}
		if (record.getImportesoj() != null) {
			sql.SET("IMPORTESOJ = #{importesoj,jdbcType=DECIMAL}");
		}
		if (record.getImporteejg() != null) {
			sql.SET("IMPORTEEJG = #{importeejg,jdbcType=DECIMAL}");
		}
		if (record.getPrevision() != null) {
			sql.SET("PREVISION = #{prevision,jdbcType=VARCHAR}");
		}
		if (record.getRegularizacion() != null) {
			sql.SET("REGULARIZACION = #{regularizacion,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdfacturacionRegulariza() != null) {
			sql.SET("IDFACTURACION_REGULARIZA = #{idfacturacionRegulariza,jdbcType=DECIMAL}");
		}
		if (record.getNombrefisico() != null) {
			sql.SET("NOMBREFISICO = #{nombrefisico,jdbcType=VARCHAR}");
		}
		if (record.getIdecomcola() != null) {
			sql.SET("IDECOMCOLA = #{idecomcola,jdbcType=DECIMAL}");
		}
		if (record.getVisible() != null) {
			sql.SET("VISIBLE = #{visible,jdbcType=VARCHAR}");
		}
		if (record.getIdpartidapresupuestaria() != null) {
			sql.SET("IDPARTIDAPRESUPUESTARIA = #{idpartidapresupuestaria,jdbcType=DECIMAL}");
		}
		sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
		sql.WHERE("IDFACTURACION = #{idfacturacion,jdbcType=DECIMAL}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACTURACIONJG
	 * @mbg.generated  Fri Dec 13 09:35:30 CET 2019
	 */
	protected void applyWhere(SQL sql, FcsFacturacionjgExample example, boolean includeExamplePhrase) {
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