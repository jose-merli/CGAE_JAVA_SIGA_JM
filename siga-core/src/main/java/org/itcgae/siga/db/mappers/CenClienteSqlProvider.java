package org.itcgae.siga.db.mappers;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteExample.Criteria;
import org.itcgae.siga.db.entities.CenClienteExample.Criterion;
import org.itcgae.siga.db.entities.CenClienteExample;

public class CenClienteSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CLIENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String countByExample(CenClienteExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("CEN_CLIENTE");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CLIENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String deleteByExample(CenClienteExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("CEN_CLIENTE");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CLIENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String insertSelective(CenCliente record) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		SQL sql = new SQL();
		sql.INSERT_INTO("CEN_CLIENTE");
		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdpersona() != null) {
			sql.VALUES("IDPERSONA", "#{idpersona,jdbcType=DECIMAL}");
		}
		if (record.getFechaalta() != null) {
			sql.VALUES("FECHAALTA", "#{fechaalta,jdbcType=TIMESTAMP}");
		}
		if (record.getCaracter() != null) {
			sql.VALUES("CARACTER", "#{caracter,jdbcType=VARCHAR}");
		}
		if (record.getPublicidad() != null) {
			sql.VALUES("PUBLICIDAD", "#{publicidad,jdbcType=VARCHAR}");
		}
		if (record.getGuiajudicial() != null) {
			sql.VALUES("GUIAJUDICIAL", "#{guiajudicial,jdbcType=VARCHAR}");
		}
		if (record.getAbonosbanco() != null) {
			sql.VALUES("ABONOSBANCO", "#{abonosbanco,jdbcType=VARCHAR}");
		}
		if (record.getCargosbanco() != null) {
			sql.VALUES("CARGOSBANCO", "#{cargosbanco,jdbcType=VARCHAR}");
		}
		if (record.getComisiones() != null) {
			sql.VALUES("COMISIONES", "#{comisiones,jdbcType=VARCHAR}");
		}
		if (record.getIdtratamiento() != null) {
			sql.VALUES("IDTRATAMIENTO", "#{idtratamiento,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdlenguaje() != null) {
			sql.VALUES("IDLENGUAJE", "#{idlenguaje,jdbcType=VARCHAR}");
		}
		if (record.getFotografia() != null) {
			sql.VALUES("FOTOGRAFIA", "#{fotografia,jdbcType=VARCHAR}");
		}
		if (record.getAsientocontable() != null) {
			sql.VALUES("ASIENTOCONTABLE", "#{asientocontable,jdbcType=VARCHAR}");
		}
		if (record.getLetrado() != null) {
			sql.VALUES("LETRADO", "#{letrado,jdbcType=VARCHAR}");
		}
		if (record.getFechacarga() != null) {
			sql.VALUES("FECHACARGA", "#{fechacarga,jdbcType=TIMESTAMP}");
		}
		if (record.getFechaactualizacion() != null) {
			sql.VALUES("FECHAACTUALIZACION", "#{fechaactualizacion,jdbcType=TIMESTAMP}");
		}
		if (record.getFechaexportcenso() != null) {
			sql.VALUES("FECHAEXPORTCENSO", "#{fechaexportcenso,jdbcType=TIMESTAMP}");
		}
		if (record.getNoenviarrevista() != null) {
			sql.VALUES("NOENVIARREVISTA", "#{noenviarrevista,jdbcType=VARCHAR}");
		}
		if (record.getNoaparecerredabogacia() != null) {
			sql.VALUES("NOAPARECERREDABOGACIA", "#{noaparecerredabogacia,jdbcType=VARCHAR}");
		}
		if (record.getExportarfoto() != null) {
			sql.VALUES("EXPORTARFOTO", "#{exportarfoto,jdbcType=VARCHAR}");
		}
		
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CLIENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String selectByExample(CenClienteExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDINSTITUCION");
		} else {
			sql.SELECT("IDINSTITUCION");
		}
		sql.SELECT("IDPERSONA");
		sql.SELECT("FECHAALTA");
		sql.SELECT("CARACTER");
		sql.SELECT("PUBLICIDAD");
		sql.SELECT("GUIAJUDICIAL");
		sql.SELECT("ABONOSBANCO");
		sql.SELECT("CARGOSBANCO");
		sql.SELECT("COMISIONES");
		sql.SELECT("IDTRATAMIENTO");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("IDLENGUAJE");
		sql.SELECT("FOTOGRAFIA");
		sql.SELECT("ASIENTOCONTABLE");
		sql.SELECT("LETRADO");
		sql.SELECT("FECHACARGA");
		sql.SELECT("FECHAACTUALIZACION");
		sql.SELECT("FECHAEXPORTCENSO");
		sql.SELECT("NOENVIARREVISTA");
		sql.SELECT("NOAPARECERREDABOGACIA");
		sql.SELECT("EXPORTARFOTO");
		sql.FROM("CEN_CLIENTE");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CLIENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		CenCliente record = (CenCliente) parameter.get("record");
		CenClienteExample example = (CenClienteExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("CEN_CLIENTE");
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdpersona() != null) {
			sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
		}
		if (record.getFechaalta() != null) {
			sql.SET("FECHAALTA = #{record.fechaalta,jdbcType=TIMESTAMP}");
		}
		if (record.getCaracter() != null) {
			sql.SET("CARACTER = #{record.caracter,jdbcType=VARCHAR}");
		}
		if (record.getPublicidad() != null) {
			sql.SET("PUBLICIDAD = #{record.publicidad,jdbcType=VARCHAR}");
		}
		if (record.getGuiajudicial() != null) {
			sql.SET("GUIAJUDICIAL = #{record.guiajudicial,jdbcType=VARCHAR}");
		}
		if (record.getAbonosbanco() != null) {
			sql.SET("ABONOSBANCO = #{record.abonosbanco,jdbcType=VARCHAR}");
		}
		if (record.getCargosbanco() != null) {
			sql.SET("CARGOSBANCO = #{record.cargosbanco,jdbcType=VARCHAR}");
		}
		if (record.getComisiones() != null) {
			sql.SET("COMISIONES = #{record.comisiones,jdbcType=VARCHAR}");
		}
		if (record.getIdtratamiento() != null) {
			sql.SET("IDTRATAMIENTO = #{record.idtratamiento,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdlenguaje() != null) {
			sql.SET("IDLENGUAJE = #{record.idlenguaje,jdbcType=VARCHAR}");
		}
		if (record.getFotografia() != null) {
			sql.SET("FOTOGRAFIA = #{record.fotografia,jdbcType=VARCHAR}");
		}
		if (record.getAsientocontable() != null) {
			sql.SET("ASIENTOCONTABLE = #{record.asientocontable,jdbcType=VARCHAR}");
		}
		if (record.getLetrado() != null) {
			sql.SET("LETRADO = #{record.letrado,jdbcType=VARCHAR}");
		}
		if (record.getFechacarga() != null) {
			sql.SET("FECHACARGA = #{record.fechacarga,jdbcType=TIMESTAMP}");
		}
		if (record.getFechaactualizacion() != null) {
			sql.SET("FECHAACTUALIZACION = #{record.fechaactualizacion,jdbcType=TIMESTAMP}");
		}
		if (record.getFechaexportcenso() != null) {
			sql.SET("FECHAEXPORTCENSO = #{record.fechaexportcenso,jdbcType=TIMESTAMP}");
		}
		if (record.getNoenviarrevista() != null) {
			sql.SET("NOENVIARREVISTA = #{record.noenviarrevista,jdbcType=VARCHAR}");
		}
		if (record.getNoaparecerredabogacia() != null) {
			sql.SET("NOAPARECERREDABOGACIA = #{record.noaparecerredabogacia,jdbcType=VARCHAR}");
		}
		if (record.getExportarfoto() != null) {
			sql.SET("EXPORTARFOTO = #{record.exportarfoto,jdbcType=VARCHAR}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CLIENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_CLIENTE");
		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
		sql.SET("FECHAALTA = #{record.fechaalta,jdbcType=TIMESTAMP}");
		sql.SET("CARACTER = #{record.caracter,jdbcType=VARCHAR}");
		sql.SET("PUBLICIDAD = #{record.publicidad,jdbcType=VARCHAR}");
		sql.SET("GUIAJUDICIAL = #{record.guiajudicial,jdbcType=VARCHAR}");
		sql.SET("ABONOSBANCO = #{record.abonosbanco,jdbcType=VARCHAR}");
		sql.SET("CARGOSBANCO = #{record.cargosbanco,jdbcType=VARCHAR}");
		sql.SET("COMISIONES = #{record.comisiones,jdbcType=VARCHAR}");
		sql.SET("IDTRATAMIENTO = #{record.idtratamiento,jdbcType=DECIMAL}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("IDLENGUAJE = #{record.idlenguaje,jdbcType=VARCHAR}");
		sql.SET("FOTOGRAFIA = #{record.fotografia,jdbcType=VARCHAR}");
		sql.SET("ASIENTOCONTABLE = #{record.asientocontable,jdbcType=VARCHAR}");
		sql.SET("LETRADO = #{record.letrado,jdbcType=VARCHAR}");
		sql.SET("FECHACARGA = #{record.fechacarga,jdbcType=TIMESTAMP}");
		sql.SET("FECHAACTUALIZACION = #{record.fechaactualizacion,jdbcType=TIMESTAMP}");
		sql.SET("FECHAEXPORTCENSO = #{record.fechaexportcenso,jdbcType=TIMESTAMP}");
		sql.SET("NOENVIARREVISTA = #{record.noenviarrevista,jdbcType=VARCHAR}");
		sql.SET("NOAPARECERREDABOGACIA = #{record.noaparecerredabogacia,jdbcType=VARCHAR}");
		sql.SET("EXPORTARFOTO = #{record.exportarfoto,jdbcType=VARCHAR}");
		CenClienteExample example = (CenClienteExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CLIENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByPrimaryKeySelective(CenCliente record) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_CLIENTE");
		if (record.getFechaalta() != null) {
			sql.SET("FECHAALTA = #{fechaalta,jdbcType=TIMESTAMP}");
		}
		if (record.getCaracter() != null) {
			sql.SET("CARACTER = #{caracter,jdbcType=VARCHAR}");
		}
		if (record.getPublicidad() != null) {
			sql.SET("PUBLICIDAD = #{publicidad,jdbcType=VARCHAR}");
		}
		if (record.getGuiajudicial() != null) {
			sql.SET("GUIAJUDICIAL = #{guiajudicial,jdbcType=VARCHAR}");
		}
		if (record.getAbonosbanco() != null) {
			sql.SET("ABONOSBANCO = #{abonosbanco,jdbcType=VARCHAR}");
		}
		if (record.getCargosbanco() != null) {
			sql.SET("CARGOSBANCO = #{cargosbanco,jdbcType=VARCHAR}");
		}
		if (record.getComisiones() != null) {
			sql.SET("COMISIONES = #{comisiones,jdbcType=VARCHAR}");
		}
		if (record.getIdtratamiento() != null) {
			sql.SET("IDTRATAMIENTO = #{idtratamiento,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdlenguaje() != null) {
			sql.SET("IDLENGUAJE = #{idlenguaje,jdbcType=VARCHAR}");
		}
		if (record.getFotografia() != null) {
			sql.SET("FOTOGRAFIA = #{fotografia,jdbcType=VARCHAR}");
		}
		if (record.getAsientocontable() != null) {
			sql.SET("ASIENTOCONTABLE = #{asientocontable,jdbcType=VARCHAR}");
		}
		if (record.getLetrado() != null) {
			sql.SET("LETRADO = #{letrado,jdbcType=VARCHAR}");
		}
		if (record.getFechacarga() != null) {
			sql.SET("FECHACARGA = #{fechacarga,jdbcType=TIMESTAMP}");
		}
		if (record.getFechaactualizacion() != null) {
			sql.SET("FECHAACTUALIZACION = #{fechaactualizacion,jdbcType=TIMESTAMP}");
		}
		if (record.getFechaexportcenso() != null) {
			sql.SET("FECHAEXPORTCENSO = #{fechaexportcenso,jdbcType=TIMESTAMP}");
		}
		if (record.getNoenviarrevista() != null) {
			sql.SET("NOENVIARREVISTA = #{noenviarrevista,jdbcType=VARCHAR}");
		}
		if (record.getNoaparecerredabogacia() != null) {
			sql.SET("NOAPARECERREDABOGACIA = #{noaparecerredabogacia,jdbcType=VARCHAR}");
		}
		if (record.getExportarfoto() != null) {
			sql.SET("EXPORTARFOTO = #{exportarfoto,jdbcType=VARCHAR}");
		}
		sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
		sql.WHERE("IDPERSONA = #{idpersona,jdbcType=DECIMAL}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CLIENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	protected void applyWhere(SQL sql, CenClienteExample example, boolean includeExamplePhrase) {
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