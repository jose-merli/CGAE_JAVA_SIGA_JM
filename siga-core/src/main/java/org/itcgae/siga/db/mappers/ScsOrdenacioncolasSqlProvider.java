package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.ScsOrdenacioncolas;
import org.itcgae.siga.db.entities.ScsOrdenacioncolasExample.Criteria;
import org.itcgae.siga.db.entities.ScsOrdenacioncolasExample.Criterion;
import org.itcgae.siga.db.entities.ScsOrdenacioncolasExample;

public class ScsOrdenacioncolasSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ORDENACIONCOLAS
     *
     * @mbg.generated Mon Nov 11 12:38:34 CET 2019
     */
    public String countByExample(ScsOrdenacioncolasExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("SCS_ORDENACIONCOLAS");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ORDENACIONCOLAS
     *
     * @mbg.generated Mon Nov 11 12:38:34 CET 2019
     */
    public String deleteByExample(ScsOrdenacioncolasExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("SCS_ORDENACIONCOLAS");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ORDENACIONCOLAS
     *
     * @mbg.generated Mon Nov 11 12:38:34 CET 2019
     */
    public String insertSelective(ScsOrdenacioncolas record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("SCS_ORDENACIONCOLAS");
        
        if (record.getIdordenacioncolas() != null) {
            sql.VALUES("IDORDENACIONCOLAS", "#{idordenacioncolas,jdbcType=DECIMAL}");
        }
        
        if (record.getAlfabeticoapellidos() != null) {
            sql.VALUES("ALFABETICOAPELLIDOS", "#{alfabeticoapellidos,jdbcType=DECIMAL}");
        }
        
        if (record.getFechanacimiento() != null) {
            sql.VALUES("FECHANACIMIENTO", "#{fechanacimiento,jdbcType=DECIMAL}");
        }
        
        if (record.getNumerocolegiado() != null) {
            sql.VALUES("NUMEROCOLEGIADO", "#{numerocolegiado,jdbcType=DECIMAL}");
        }
        
        if (record.getAntiguedadcola() != null) {
            sql.VALUES("ANTIGUEDADCOLA", "#{antiguedadcola,jdbcType=DECIMAL}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ORDENACIONCOLAS
     *
     * @mbg.generated Mon Nov 11 12:38:34 CET 2019
     */
    public String selectByExample(ScsOrdenacioncolasExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("IDORDENACIONCOLAS");
        } else {
            sql.SELECT("IDORDENACIONCOLAS");
        }
        sql.SELECT("ALFABETICOAPELLIDOS");
        sql.SELECT("FECHANACIMIENTO");
        sql.SELECT("NUMEROCOLEGIADO");
        sql.SELECT("ANTIGUEDADCOLA");
        sql.SELECT("FECHAMODIFICACION");
        sql.SELECT("USUMODIFICACION");
        sql.FROM("SCS_ORDENACIONCOLAS");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ORDENACIONCOLAS
     *
     * @mbg.generated Mon Nov 11 12:38:34 CET 2019
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        ScsOrdenacioncolas record = (ScsOrdenacioncolas) parameter.get("record");
        ScsOrdenacioncolasExample example = (ScsOrdenacioncolasExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("SCS_ORDENACIONCOLAS");
        
        if (record.getIdordenacioncolas() != null) {
            sql.SET("IDORDENACIONCOLAS = #{record.idordenacioncolas,jdbcType=DECIMAL}");
        }
        
        if (record.getAlfabeticoapellidos() != null) {
            sql.SET("ALFABETICOAPELLIDOS = #{record.alfabeticoapellidos,jdbcType=DECIMAL}");
        }
        
        if (record.getFechanacimiento() != null) {
            sql.SET("FECHANACIMIENTO = #{record.fechanacimiento,jdbcType=DECIMAL}");
        }
        
        if (record.getNumerocolegiado() != null) {
            sql.SET("NUMEROCOLEGIADO = #{record.numerocolegiado,jdbcType=DECIMAL}");
        }
        
        if (record.getAntiguedadcola() != null) {
            sql.SET("ANTIGUEDADCOLA = #{record.antiguedadcola,jdbcType=DECIMAL}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ORDENACIONCOLAS
     *
     * @mbg.generated Mon Nov 11 12:38:34 CET 2019
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("SCS_ORDENACIONCOLAS");
        
        sql.SET("IDORDENACIONCOLAS = #{record.idordenacioncolas,jdbcType=DECIMAL}");
        sql.SET("ALFABETICOAPELLIDOS = #{record.alfabeticoapellidos,jdbcType=DECIMAL}");
        sql.SET("FECHANACIMIENTO = #{record.fechanacimiento,jdbcType=DECIMAL}");
        sql.SET("NUMEROCOLEGIADO = #{record.numerocolegiado,jdbcType=DECIMAL}");
        sql.SET("ANTIGUEDADCOLA = #{record.antiguedadcola,jdbcType=DECIMAL}");
        sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        
        ScsOrdenacioncolasExample example = (ScsOrdenacioncolasExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ORDENACIONCOLAS
     *
     * @mbg.generated Mon Nov 11 12:38:34 CET 2019
     */
    public String updateByPrimaryKeySelective(ScsOrdenacioncolas record) {
        SQL sql = new SQL();
        sql.UPDATE("SCS_ORDENACIONCOLAS");
        
        if (record.getAlfabeticoapellidos() != null) {
            sql.SET("ALFABETICOAPELLIDOS = #{alfabeticoapellidos,jdbcType=DECIMAL}");
        }
        
        if (record.getFechanacimiento() != null) {
            sql.SET("FECHANACIMIENTO = #{fechanacimiento,jdbcType=DECIMAL}");
        }
        
        if (record.getNumerocolegiado() != null) {
            sql.SET("NUMEROCOLEGIADO = #{numerocolegiado,jdbcType=DECIMAL}");
        }
        
        if (record.getAntiguedadcola() != null) {
            sql.SET("ANTIGUEDADCOLA = #{antiguedadcola,jdbcType=DECIMAL}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
        }
        
        sql.WHERE("IDORDENACIONCOLAS = #{idordenacioncolas,jdbcType=DECIMAL}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ORDENACIONCOLAS
     *
     * @mbg.generated Mon Nov 11 12:38:34 CET 2019
     */
    protected void applyWhere(SQL sql, ScsOrdenacioncolasExample example, boolean includeExamplePhrase) {
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
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
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