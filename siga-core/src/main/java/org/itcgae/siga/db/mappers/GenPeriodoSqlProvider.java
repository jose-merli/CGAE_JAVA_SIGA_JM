package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.GenPeriodo;
import org.itcgae.siga.db.entities.GenPeriodoExample.Criteria;
import org.itcgae.siga.db.entities.GenPeriodoExample.Criterion;
import org.itcgae.siga.db.entities.GenPeriodoExample;

public class GenPeriodoSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_PERIODO
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String countByExample(GenPeriodoExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("GEN_PERIODO");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_PERIODO
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String deleteByExample(GenPeriodoExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("GEN_PERIODO");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_PERIODO
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String insertSelective(GenPeriodo record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("GEN_PERIODO");
        
        if (record.getIdperiodo() != null) {
            sql.VALUES("IDPERIODO", "#{idperiodo,jdbcType=DECIMAL}");
        }
        
        if (record.getNombre() != null) {
            sql.VALUES("NOMBRE", "#{nombre,jdbcType=VARCHAR}");
        }
        
        if (record.getDiainicio() != null) {
            sql.VALUES("DIAINICIO", "#{diainicio,jdbcType=DECIMAL}");
        }
        
        if (record.getMesinicio() != null) {
            sql.VALUES("MESINICIO", "#{mesinicio,jdbcType=DECIMAL}");
        }
        
        if (record.getAnioinicio() != null) {
            sql.VALUES("ANIOINICIO", "#{anioinicio,jdbcType=DECIMAL}");
        }
        
        if (record.getDiafin() != null) {
            sql.VALUES("DIAFIN", "#{diafin,jdbcType=DECIMAL}");
        }
        
        if (record.getMesfin() != null) {
            sql.VALUES("MESFIN", "#{mesfin,jdbcType=DECIMAL}");
        }
        
        if (record.getAniofin() != null) {
            sql.VALUES("ANIOFIN", "#{aniofin,jdbcType=DECIMAL}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_PERIODO
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String selectByExample(GenPeriodoExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("IDPERIODO");
        } else {
            sql.SELECT("IDPERIODO");
        }
        sql.SELECT("NOMBRE");
        sql.SELECT("DIAINICIO");
        sql.SELECT("MESINICIO");
        sql.SELECT("ANIOINICIO");
        sql.SELECT("DIAFIN");
        sql.SELECT("MESFIN");
        sql.SELECT("ANIOFIN");
        sql.FROM("GEN_PERIODO");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_PERIODO
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        GenPeriodo record = (GenPeriodo) parameter.get("record");
        GenPeriodoExample example = (GenPeriodoExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("GEN_PERIODO");
        
        if (record.getIdperiodo() != null) {
            sql.SET("IDPERIODO = #{record.idperiodo,jdbcType=DECIMAL}");
        }
        
        if (record.getNombre() != null) {
            sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
        }
        
        if (record.getDiainicio() != null) {
            sql.SET("DIAINICIO = #{record.diainicio,jdbcType=DECIMAL}");
        }
        
        if (record.getMesinicio() != null) {
            sql.SET("MESINICIO = #{record.mesinicio,jdbcType=DECIMAL}");
        }
        
        if (record.getAnioinicio() != null) {
            sql.SET("ANIOINICIO = #{record.anioinicio,jdbcType=DECIMAL}");
        }
        
        if (record.getDiafin() != null) {
            sql.SET("DIAFIN = #{record.diafin,jdbcType=DECIMAL}");
        }
        
        if (record.getMesfin() != null) {
            sql.SET("MESFIN = #{record.mesfin,jdbcType=DECIMAL}");
        }
        
        if (record.getAniofin() != null) {
            sql.SET("ANIOFIN = #{record.aniofin,jdbcType=DECIMAL}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_PERIODO
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("GEN_PERIODO");
        
        sql.SET("IDPERIODO = #{record.idperiodo,jdbcType=DECIMAL}");
        sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
        sql.SET("DIAINICIO = #{record.diainicio,jdbcType=DECIMAL}");
        sql.SET("MESINICIO = #{record.mesinicio,jdbcType=DECIMAL}");
        sql.SET("ANIOINICIO = #{record.anioinicio,jdbcType=DECIMAL}");
        sql.SET("DIAFIN = #{record.diafin,jdbcType=DECIMAL}");
        sql.SET("MESFIN = #{record.mesfin,jdbcType=DECIMAL}");
        sql.SET("ANIOFIN = #{record.aniofin,jdbcType=DECIMAL}");
        
        GenPeriodoExample example = (GenPeriodoExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_PERIODO
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String updateByPrimaryKeySelective(GenPeriodo record) {
        SQL sql = new SQL();
        sql.UPDATE("GEN_PERIODO");
        
        if (record.getNombre() != null) {
            sql.SET("NOMBRE = #{nombre,jdbcType=VARCHAR}");
        }
        
        if (record.getDiainicio() != null) {
            sql.SET("DIAINICIO = #{diainicio,jdbcType=DECIMAL}");
        }
        
        if (record.getMesinicio() != null) {
            sql.SET("MESINICIO = #{mesinicio,jdbcType=DECIMAL}");
        }
        
        if (record.getAnioinicio() != null) {
            sql.SET("ANIOINICIO = #{anioinicio,jdbcType=DECIMAL}");
        }
        
        if (record.getDiafin() != null) {
            sql.SET("DIAFIN = #{diafin,jdbcType=DECIMAL}");
        }
        
        if (record.getMesfin() != null) {
            sql.SET("MESFIN = #{mesfin,jdbcType=DECIMAL}");
        }
        
        if (record.getAniofin() != null) {
            sql.SET("ANIOFIN = #{aniofin,jdbcType=DECIMAL}");
        }
        
        sql.WHERE("IDPERIODO = #{idperiodo,jdbcType=DECIMAL}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_PERIODO
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    protected void applyWhere(SQL sql, GenPeriodoExample example, boolean includeExamplePhrase) {
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