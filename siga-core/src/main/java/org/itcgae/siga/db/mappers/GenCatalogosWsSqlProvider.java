package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.GenCatalogosWs;
import org.itcgae.siga.db.entities.GenCatalogosWsExample;
import org.itcgae.siga.db.entities.GenCatalogosWsExample.Criteria;
import org.itcgae.siga.db.entities.GenCatalogosWsExample.Criterion;

public class GenCatalogosWsSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_CATALOGOS_WS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String countByExample(GenCatalogosWsExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("GEN_CATALOGOS_WS");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_CATALOGOS_WS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String deleteByExample(GenCatalogosWsExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("GEN_CATALOGOS_WS");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_CATALOGOS_WS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String insertSelective(GenCatalogosWs record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("GEN_CATALOGOS_WS");
        
        if (record.getCatalogo() != null) {
            sql.VALUES("CATALOGO", "#{catalogo,jdbcType=VARCHAR}");
        }
        
        if (record.getConjunto() != null) {
            sql.VALUES("CONJUNTO", "#{conjunto,jdbcType=VARCHAR}");
        }
        
        if (record.getValor() != null) {
            sql.VALUES("VALOR", "#{valor,jdbcType=VARCHAR}");
        }
        
        if (record.getIdinterno() != null) {
            sql.VALUES("IDINTERNO", "#{idinterno,jdbcType=VARCHAR}");
        }
        
        if (record.getIdexterno() != null) {
            sql.VALUES("IDEXTERNO", "#{idexterno,jdbcType=VARCHAR}");
        }
        
        if (record.getRefinterna() != null) {
            sql.VALUES("REFINTERNA", "#{refinterna,jdbcType=VARCHAR}");
        }
        
        if (record.getRecurso() != null) {
            sql.VALUES("RECURSO", "#{recurso,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_CATALOGOS_WS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String selectByExample(GenCatalogosWsExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("CATALOGO");
        } else {
            sql.SELECT("CATALOGO");
        }
        sql.SELECT("CONJUNTO");
        sql.SELECT("VALOR");
        sql.SELECT("IDINTERNO");
        sql.SELECT("IDEXTERNO");
        sql.SELECT("REFINTERNA");
        sql.SELECT("RECURSO");
        sql.FROM("GEN_CATALOGOS_WS");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_CATALOGOS_WS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        GenCatalogosWs record = (GenCatalogosWs) parameter.get("record");
        GenCatalogosWsExample example = (GenCatalogosWsExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("GEN_CATALOGOS_WS");
        
        if (record.getCatalogo() != null) {
            sql.SET("CATALOGO = #{record.catalogo,jdbcType=VARCHAR}");
        }
        
        if (record.getConjunto() != null) {
            sql.SET("CONJUNTO = #{record.conjunto,jdbcType=VARCHAR}");
        }
        
        if (record.getValor() != null) {
            sql.SET("VALOR = #{record.valor,jdbcType=VARCHAR}");
        }
        
        if (record.getIdinterno() != null) {
            sql.SET("IDINTERNO = #{record.idinterno,jdbcType=VARCHAR}");
        }
        
        if (record.getIdexterno() != null) {
            sql.SET("IDEXTERNO = #{record.idexterno,jdbcType=VARCHAR}");
        }
        
        if (record.getRefinterna() != null) {
            sql.SET("REFINTERNA = #{record.refinterna,jdbcType=VARCHAR}");
        }
        
        if (record.getRecurso() != null) {
            sql.SET("RECURSO = #{record.recurso,jdbcType=VARCHAR}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_CATALOGOS_WS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("GEN_CATALOGOS_WS");
        
        sql.SET("CATALOGO = #{record.catalogo,jdbcType=VARCHAR}");
        sql.SET("CONJUNTO = #{record.conjunto,jdbcType=VARCHAR}");
        sql.SET("VALOR = #{record.valor,jdbcType=VARCHAR}");
        sql.SET("IDINTERNO = #{record.idinterno,jdbcType=VARCHAR}");
        sql.SET("IDEXTERNO = #{record.idexterno,jdbcType=VARCHAR}");
        sql.SET("REFINTERNA = #{record.refinterna,jdbcType=VARCHAR}");
        sql.SET("RECURSO = #{record.recurso,jdbcType=VARCHAR}");
        
        GenCatalogosWsExample example = (GenCatalogosWsExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_CATALOGOS_WS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    protected void applyWhere(SQL sql, GenCatalogosWsExample example, boolean includeExamplePhrase) {
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