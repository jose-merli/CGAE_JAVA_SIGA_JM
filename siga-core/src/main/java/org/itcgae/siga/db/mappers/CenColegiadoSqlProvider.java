package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.entities.CenColegiadoExample.Criteria;
import org.itcgae.siga.db.entities.CenColegiadoExample.Criterion;
import org.itcgae.siga.db.entities.CenColegiadoExample;

public class CenColegiadoSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLEGIADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String countByExample(CenColegiadoExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("CEN_COLEGIADO");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLEGIADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String deleteByExample(CenColegiadoExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("CEN_COLEGIADO");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLEGIADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String insertSelective(CenColegiado record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("CEN_COLEGIADO");
		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdpersona() != null) {
			sql.VALUES("IDPERSONA", "#{idpersona,jdbcType=DECIMAL}");
		}
		if (record.getFechapresentacion() != null) {
			sql.VALUES("FECHAPRESENTACION", "#{fechapresentacion,jdbcType=TIMESTAMP}");
		}
		if (record.getFechaincorporacion() != null) {
			sql.VALUES("FECHAINCORPORACION", "#{fechaincorporacion,jdbcType=TIMESTAMP}");
		}
		if (record.getIndtitulacion() != null) {
			sql.VALUES("INDTITULACION", "#{indtitulacion,jdbcType=VARCHAR}");
		}
		if (record.getJubilacioncuota() != null) {
			sql.VALUES("JUBILACIONCUOTA", "#{jubilacioncuota,jdbcType=VARCHAR}");
		}
		if (record.getSituacionejercicio() != null) {
			sql.VALUES("SITUACIONEJERCICIO", "#{situacionejercicio,jdbcType=VARCHAR}");
		}
		if (record.getSituacionresidente() != null) {
			sql.VALUES("SITUACIONRESIDENTE", "#{situacionresidente,jdbcType=VARCHAR}");
		}
		if (record.getSituacionempresa() != null) {
			sql.VALUES("SITUACIONEMPRESA", "#{situacionempresa,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getComunitario() != null) {
			sql.VALUES("COMUNITARIO", "#{comunitario,jdbcType=VARCHAR}");
		}
		if (record.getNcolegiado() != null) {
			sql.VALUES("NCOLEGIADO", "#{ncolegiado,jdbcType=VARCHAR}");
		}
		if (record.getFechajura() != null) {
			sql.VALUES("FECHAJURA", "#{fechajura,jdbcType=TIMESTAMP}");
		}
		if (record.getNcomunitario() != null) {
			sql.VALUES("NCOMUNITARIO", "#{ncomunitario,jdbcType=VARCHAR}");
		}
		if (record.getFechatitulacion() != null) {
			sql.VALUES("FECHATITULACION", "#{fechatitulacion,jdbcType=TIMESTAMP}");
		}
		if (record.getOtroscolegios() != null) {
			sql.VALUES("OTROSCOLEGIOS", "#{otroscolegios,jdbcType=VARCHAR}");
		}
		if (record.getFechadeontologia() != null) {
			sql.VALUES("FECHADEONTOLOGIA", "#{fechadeontologia,jdbcType=TIMESTAMP}");
		}
		if (record.getFechamovimiento() != null) {
			sql.VALUES("FECHAMOVIMIENTO", "#{fechamovimiento,jdbcType=TIMESTAMP}");
		}
		if (record.getIdtiposseguro() != null) {
			sql.VALUES("IDTIPOSSEGURO", "#{idtiposseguro,jdbcType=DECIMAL}");
		}
		if (record.getCuentacontablesjcs() != null) {
			sql.VALUES("CUENTACONTABLESJCS", "#{cuentacontablesjcs,jdbcType=VARCHAR}");
		}
		if (record.getIdentificadords() != null) {
			sql.VALUES("IDENTIFICADORDS", "#{identificadords,jdbcType=VARCHAR}");
		}
		if (record.getNmutualista() != null) {
			sql.VALUES("NMUTUALISTA", "#{nmutualista,jdbcType=VARCHAR}");
		}
		if (record.getNumsolicitudcolegiacion() != null) {
			sql.VALUES("NUMSOLICITUDCOLEGIACION", "#{numsolicitudcolegiacion,jdbcType=VARCHAR}");
		}
		if (record.getColumnaPrueba() != null) {
			sql.VALUES("COLUMNA_PRUEBA", "#{columnaPrueba,jdbcType=VARCHAR}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLEGIADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String selectByExample(CenColegiadoExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDINSTITUCION");
		} else {
			sql.SELECT("IDINSTITUCION");
		}
		sql.SELECT("IDPERSONA");
		sql.SELECT("FECHAPRESENTACION");
		sql.SELECT("FECHAINCORPORACION");
		sql.SELECT("INDTITULACION");
		sql.SELECT("JUBILACIONCUOTA");
		sql.SELECT("SITUACIONEJERCICIO");
		sql.SELECT("SITUACIONRESIDENTE");
		sql.SELECT("SITUACIONEMPRESA");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("COMUNITARIO");
		sql.SELECT("NCOLEGIADO");
		sql.SELECT("FECHAJURA");
		sql.SELECT("NCOMUNITARIO");
		sql.SELECT("FECHATITULACION");
		sql.SELECT("OTROSCOLEGIOS");
		sql.SELECT("FECHADEONTOLOGIA");
		sql.SELECT("FECHAMOVIMIENTO");
		sql.SELECT("IDTIPOSSEGURO");
		sql.SELECT("CUENTACONTABLESJCS");
		sql.SELECT("IDENTIFICADORDS");
		sql.SELECT("NMUTUALISTA");
		sql.SELECT("NUMSOLICITUDCOLEGIACION");
		sql.SELECT("COLUMNA_PRUEBA");
		sql.FROM("CEN_COLEGIADO");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLEGIADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		CenColegiado record = (CenColegiado) parameter.get("record");
		CenColegiadoExample example = (CenColegiadoExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("CEN_COLEGIADO");
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdpersona() != null) {
			sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
		}
		if (record.getFechapresentacion() != null) {
			sql.SET("FECHAPRESENTACION = #{record.fechapresentacion,jdbcType=TIMESTAMP}");
		}
		if (record.getFechaincorporacion() != null) {
			sql.SET("FECHAINCORPORACION = #{record.fechaincorporacion,jdbcType=TIMESTAMP}");
		}
		if (record.getIndtitulacion() != null) {
			sql.SET("INDTITULACION = #{record.indtitulacion,jdbcType=VARCHAR}");
		}
		if (record.getJubilacioncuota() != null) {
			sql.SET("JUBILACIONCUOTA = #{record.jubilacioncuota,jdbcType=VARCHAR}");
		}
		if (record.getSituacionejercicio() != null) {
			sql.SET("SITUACIONEJERCICIO = #{record.situacionejercicio,jdbcType=VARCHAR}");
		}
		if (record.getSituacionresidente() != null) {
			sql.SET("SITUACIONRESIDENTE = #{record.situacionresidente,jdbcType=VARCHAR}");
		}
		if (record.getSituacionempresa() != null) {
			sql.SET("SITUACIONEMPRESA = #{record.situacionempresa,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getComunitario() != null) {
			sql.SET("COMUNITARIO = #{record.comunitario,jdbcType=VARCHAR}");
		}
		if (record.getNcolegiado() != null) {
			sql.SET("NCOLEGIADO = #{record.ncolegiado,jdbcType=VARCHAR}");
		}
		if (record.getFechajura() != null) {
			sql.SET("FECHAJURA = #{record.fechajura,jdbcType=TIMESTAMP}");
		}
		if (record.getNcomunitario() != null) {
			sql.SET("NCOMUNITARIO = #{record.ncomunitario,jdbcType=VARCHAR}");
		}
		if (record.getFechatitulacion() != null) {
			sql.SET("FECHATITULACION = #{record.fechatitulacion,jdbcType=TIMESTAMP}");
		}
		if (record.getOtroscolegios() != null) {
			sql.SET("OTROSCOLEGIOS = #{record.otroscolegios,jdbcType=VARCHAR}");
		}
		if (record.getFechadeontologia() != null) {
			sql.SET("FECHADEONTOLOGIA = #{record.fechadeontologia,jdbcType=TIMESTAMP}");
		}
		if (record.getFechamovimiento() != null) {
			sql.SET("FECHAMOVIMIENTO = #{record.fechamovimiento,jdbcType=TIMESTAMP}");
		}
		if (record.getIdtiposseguro() != null) {
			sql.SET("IDTIPOSSEGURO = #{record.idtiposseguro,jdbcType=DECIMAL}");
		}
		if (record.getCuentacontablesjcs() != null) {
			sql.SET("CUENTACONTABLESJCS = #{record.cuentacontablesjcs,jdbcType=VARCHAR}");
		}
		if (record.getIdentificadords() != null) {
			sql.SET("IDENTIFICADORDS = #{record.identificadords,jdbcType=VARCHAR}");
		}
		if (record.getNmutualista() != null) {
			sql.SET("NMUTUALISTA = #{record.nmutualista,jdbcType=VARCHAR}");
		}
		if (record.getNumsolicitudcolegiacion() != null) {
			sql.SET("NUMSOLICITUDCOLEGIACION = #{record.numsolicitudcolegiacion,jdbcType=VARCHAR}");
		}
		if (record.getColumnaPrueba() != null) {
			sql.SET("COLUMNA_PRUEBA = #{record.columnaPrueba,jdbcType=VARCHAR}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLEGIADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_COLEGIADO");
		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
		sql.SET("FECHAPRESENTACION = #{record.fechapresentacion,jdbcType=TIMESTAMP}");
		sql.SET("FECHAINCORPORACION = #{record.fechaincorporacion,jdbcType=TIMESTAMP}");
		sql.SET("INDTITULACION = #{record.indtitulacion,jdbcType=VARCHAR}");
		sql.SET("JUBILACIONCUOTA = #{record.jubilacioncuota,jdbcType=VARCHAR}");
		sql.SET("SITUACIONEJERCICIO = #{record.situacionejercicio,jdbcType=VARCHAR}");
		sql.SET("SITUACIONRESIDENTE = #{record.situacionresidente,jdbcType=VARCHAR}");
		sql.SET("SITUACIONEMPRESA = #{record.situacionempresa,jdbcType=VARCHAR}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("COMUNITARIO = #{record.comunitario,jdbcType=VARCHAR}");
		sql.SET("NCOLEGIADO = #{record.ncolegiado,jdbcType=VARCHAR}");
		sql.SET("FECHAJURA = #{record.fechajura,jdbcType=TIMESTAMP}");
		sql.SET("NCOMUNITARIO = #{record.ncomunitario,jdbcType=VARCHAR}");
		sql.SET("FECHATITULACION = #{record.fechatitulacion,jdbcType=TIMESTAMP}");
		sql.SET("OTROSCOLEGIOS = #{record.otroscolegios,jdbcType=VARCHAR}");
		sql.SET("FECHADEONTOLOGIA = #{record.fechadeontologia,jdbcType=TIMESTAMP}");
		sql.SET("FECHAMOVIMIENTO = #{record.fechamovimiento,jdbcType=TIMESTAMP}");
		sql.SET("IDTIPOSSEGURO = #{record.idtiposseguro,jdbcType=DECIMAL}");
		sql.SET("CUENTACONTABLESJCS = #{record.cuentacontablesjcs,jdbcType=VARCHAR}");
		sql.SET("IDENTIFICADORDS = #{record.identificadords,jdbcType=VARCHAR}");
		sql.SET("NMUTUALISTA = #{record.nmutualista,jdbcType=VARCHAR}");
		sql.SET("NUMSOLICITUDCOLEGIACION = #{record.numsolicitudcolegiacion,jdbcType=VARCHAR}");
		sql.SET("COLUMNA_PRUEBA = #{record.columnaPrueba,jdbcType=VARCHAR}");
		CenColegiadoExample example = (CenColegiadoExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLEGIADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByPrimaryKeySelective(CenColegiado record) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_COLEGIADO");
		if (record.getFechapresentacion() != null) {
			sql.SET("FECHAPRESENTACION = #{fechapresentacion,jdbcType=TIMESTAMP}");
		}
		if (record.getFechaincorporacion() != null) {
			sql.SET("FECHAINCORPORACION = #{fechaincorporacion,jdbcType=TIMESTAMP}");
		}
		if (record.getIndtitulacion() != null) {
			sql.SET("INDTITULACION = #{indtitulacion,jdbcType=VARCHAR}");
		}
		if (record.getJubilacioncuota() != null) {
			sql.SET("JUBILACIONCUOTA = #{jubilacioncuota,jdbcType=VARCHAR}");
		}
		if (record.getSituacionejercicio() != null) {
			sql.SET("SITUACIONEJERCICIO = #{situacionejercicio,jdbcType=VARCHAR}");
		}
		if (record.getSituacionresidente() != null) {
			sql.SET("SITUACIONRESIDENTE = #{situacionresidente,jdbcType=VARCHAR}");
		}
		if (record.getSituacionempresa() != null) {
			sql.SET("SITUACIONEMPRESA = #{situacionempresa,jdbcType=VARCHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getComunitario() != null) {
			sql.SET("COMUNITARIO = #{comunitario,jdbcType=VARCHAR}");
		}
		if (record.getNcolegiado() != null) {
			sql.SET("NCOLEGIADO = #{ncolegiado,jdbcType=VARCHAR}");
		}
		if (record.getFechajura() != null) {
			sql.SET("FECHAJURA = #{fechajura,jdbcType=TIMESTAMP}");
		}
		if (record.getNcomunitario() != null) {
			sql.SET("NCOMUNITARIO = #{ncomunitario,jdbcType=VARCHAR}");
		}
		if (record.getFechatitulacion() != null) {
			sql.SET("FECHATITULACION = #{fechatitulacion,jdbcType=TIMESTAMP}");
		}
		if (record.getOtroscolegios() != null) {
			sql.SET("OTROSCOLEGIOS = #{otroscolegios,jdbcType=VARCHAR}");
		}
		if (record.getFechadeontologia() != null) {
			sql.SET("FECHADEONTOLOGIA = #{fechadeontologia,jdbcType=TIMESTAMP}");
		}
		if (record.getFechamovimiento() != null) {
			sql.SET("FECHAMOVIMIENTO = #{fechamovimiento,jdbcType=TIMESTAMP}");
		}
		if (record.getIdtiposseguro() != null) {
			sql.SET("IDTIPOSSEGURO = #{idtiposseguro,jdbcType=DECIMAL}");
		}
		if (record.getCuentacontablesjcs() != null) {
			sql.SET("CUENTACONTABLESJCS = #{cuentacontablesjcs,jdbcType=VARCHAR}");
		}
		if (record.getIdentificadords() != null) {
			sql.SET("IDENTIFICADORDS = #{identificadords,jdbcType=VARCHAR}");
		}
		if (record.getNmutualista() != null) {
			sql.SET("NMUTUALISTA = #{nmutualista,jdbcType=VARCHAR}");
		}
		if (record.getNumsolicitudcolegiacion() != null) {
			sql.SET("NUMSOLICITUDCOLEGIACION = #{numsolicitudcolegiacion,jdbcType=VARCHAR}");
		}
		if (record.getColumnaPrueba() != null) {
			sql.SET("COLUMNA_PRUEBA = #{columnaPrueba,jdbcType=VARCHAR}");
		}
		sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
		sql.WHERE("IDPERSONA = #{idpersona,jdbcType=DECIMAL}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLEGIADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	protected void applyWhere(SQL sql, CenColegiadoExample example, boolean includeExamplePhrase) {
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