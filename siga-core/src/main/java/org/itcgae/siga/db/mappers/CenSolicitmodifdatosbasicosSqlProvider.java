package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.CenSolicitmodifdatosbasicos;
import org.itcgae.siga.db.entities.CenSolicitmodifdatosbasicosExample;
import org.itcgae.siga.db.entities.CenSolicitmodifdatosbasicosExample.Criteria;
import org.itcgae.siga.db.entities.CenSolicitmodifdatosbasicosExample.Criterion;

public class CenSolicitmodifdatosbasicosSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_SOLICITMODIFDATOSBASICOS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String countByExample(CenSolicitmodifdatosbasicosExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("CEN_SOLICITMODIFDATOSBASICOS");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_SOLICITMODIFDATOSBASICOS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String deleteByExample(CenSolicitmodifdatosbasicosExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("CEN_SOLICITMODIFDATOSBASICOS");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_SOLICITMODIFDATOSBASICOS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String insertSelective(CenSolicitmodifdatosbasicos record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("CEN_SOLICITMODIFDATOSBASICOS");
		if (record.getIdsolicitud() != null) {
			sql.VALUES("IDSOLICITUD", "#{idsolicitud,jdbcType=DECIMAL}");
		}
		if (record.getMotivo() != null) {
			sql.VALUES("MOTIVO", "#{motivo,jdbcType=VARCHAR}");
		}
		if (record.getPublicidad() != null) {
			sql.VALUES("PUBLICIDAD", "#{publicidad,jdbcType=VARCHAR}");
		}
		if (record.getGuiajudicial() != null) {
			sql.VALUES("GUIAJUDICIAL", "#{guiajudicial,jdbcType=VARCHAR}");
		}
		if (record.getAbonos() != null) {
			sql.VALUES("ABONOS", "#{abonos,jdbcType=VARCHAR}");
		}
		if (record.getCargos() != null) {
			sql.VALUES("CARGOS", "#{cargos,jdbcType=VARCHAR}");
		}
		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdpersona() != null) {
			sql.VALUES("IDPERSONA", "#{idpersona,jdbcType=DECIMAL}");
		}
		if (record.getIdlenguaje() != null) {
			sql.VALUES("IDLENGUAJE", "#{idlenguaje,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdestadosolic() != null) {
			sql.VALUES("IDESTADOSOLIC", "#{idestadosolic,jdbcType=DECIMAL}");
		}
		if (record.getFechaalta() != null) {
			sql.VALUES("FECHAALTA", "#{fechaalta,jdbcType=TIMESTAMP}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_SOLICITMODIFDATOSBASICOS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String selectByExample(CenSolicitmodifdatosbasicosExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDSOLICITUD");
		} else {
			sql.SELECT("IDSOLICITUD");
		}
		sql.SELECT("MOTIVO");
		sql.SELECT("PUBLICIDAD");
		sql.SELECT("GUIAJUDICIAL");
		sql.SELECT("ABONOS");
		sql.SELECT("CARGOS");
		sql.SELECT("IDINSTITUCION");
		sql.SELECT("IDPERSONA");
		sql.SELECT("IDLENGUAJE");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("IDESTADOSOLIC");
		sql.SELECT("FECHAALTA");
		sql.FROM("CEN_SOLICITMODIFDATOSBASICOS");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_SOLICITMODIFDATOSBASICOS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		CenSolicitmodifdatosbasicos record = (CenSolicitmodifdatosbasicos) parameter.get("record");
		CenSolicitmodifdatosbasicosExample example = (CenSolicitmodifdatosbasicosExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("CEN_SOLICITMODIFDATOSBASICOS");
		if (record.getIdsolicitud() != null) {
			sql.SET("IDSOLICITUD = #{record.idsolicitud,jdbcType=DECIMAL}");
		}
		if (record.getMotivo() != null) {
			sql.SET("MOTIVO = #{record.motivo,jdbcType=VARCHAR}");
		}
		if (record.getPublicidad() != null) {
			sql.SET("PUBLICIDAD = #{record.publicidad,jdbcType=VARCHAR}");
		}
		if (record.getGuiajudicial() != null) {
			sql.SET("GUIAJUDICIAL = #{record.guiajudicial,jdbcType=VARCHAR}");
		}
		if (record.getAbonos() != null) {
			sql.SET("ABONOS = #{record.abonos,jdbcType=VARCHAR}");
		}
		if (record.getCargos() != null) {
			sql.SET("CARGOS = #{record.cargos,jdbcType=VARCHAR}");
		}
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdpersona() != null) {
			sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
		}
		if (record.getIdlenguaje() != null) {
			sql.SET("IDLENGUAJE = #{record.idlenguaje,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdestadosolic() != null) {
			sql.SET("IDESTADOSOLIC = #{record.idestadosolic,jdbcType=DECIMAL}");
		}
		if (record.getFechaalta() != null) {
			sql.SET("FECHAALTA = #{record.fechaalta,jdbcType=TIMESTAMP}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_SOLICITMODIFDATOSBASICOS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_SOLICITMODIFDATOSBASICOS");
		sql.SET("IDSOLICITUD = #{record.idsolicitud,jdbcType=DECIMAL}");
		sql.SET("MOTIVO = #{record.motivo,jdbcType=VARCHAR}");
		sql.SET("PUBLICIDAD = #{record.publicidad,jdbcType=VARCHAR}");
		sql.SET("GUIAJUDICIAL = #{record.guiajudicial,jdbcType=VARCHAR}");
		sql.SET("ABONOS = #{record.abonos,jdbcType=VARCHAR}");
		sql.SET("CARGOS = #{record.cargos,jdbcType=VARCHAR}");
		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
		sql.SET("IDLENGUAJE = #{record.idlenguaje,jdbcType=VARCHAR}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("IDESTADOSOLIC = #{record.idestadosolic,jdbcType=DECIMAL}");
		sql.SET("FECHAALTA = #{record.fechaalta,jdbcType=TIMESTAMP}");
		CenSolicitmodifdatosbasicosExample example = (CenSolicitmodifdatosbasicosExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_SOLICITMODIFDATOSBASICOS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByPrimaryKeySelective(CenSolicitmodifdatosbasicos record) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_SOLICITMODIFDATOSBASICOS");
		if (record.getMotivo() != null) {
			sql.SET("MOTIVO = #{motivo,jdbcType=VARCHAR}");
		}
		if (record.getPublicidad() != null) {
			sql.SET("PUBLICIDAD = #{publicidad,jdbcType=VARCHAR}");
		}
		if (record.getGuiajudicial() != null) {
			sql.SET("GUIAJUDICIAL = #{guiajudicial,jdbcType=VARCHAR}");
		}
		if (record.getAbonos() != null) {
			sql.SET("ABONOS = #{abonos,jdbcType=VARCHAR}");
		}
		if (record.getCargos() != null) {
			sql.SET("CARGOS = #{cargos,jdbcType=VARCHAR}");
		}
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdpersona() != null) {
			sql.SET("IDPERSONA = #{idpersona,jdbcType=DECIMAL}");
		}
		if (record.getIdlenguaje() != null) {
			sql.SET("IDLENGUAJE = #{idlenguaje,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdestadosolic() != null) {
			sql.SET("IDESTADOSOLIC = #{idestadosolic,jdbcType=DECIMAL}");
		}
		if (record.getFechaalta() != null) {
			sql.SET("FECHAALTA = #{fechaalta,jdbcType=TIMESTAMP}");
		}
		sql.WHERE("IDSOLICITUD = #{idsolicitud,jdbcType=DECIMAL}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_SOLICITMODIFDATOSBASICOS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	protected void applyWhere(SQL sql, CenSolicitmodifdatosbasicosExample example, boolean includeExamplePhrase) {
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