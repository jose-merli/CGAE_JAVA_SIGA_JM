package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.FacEstadoconfirmfact;
import org.itcgae.siga.db.entities.FacEstadoconfirmfactExample.Criteria;
import org.itcgae.siga.db.entities.FacEstadoconfirmfactExample.Criterion;
import org.itcgae.siga.db.entities.FacEstadoconfirmfactExample;

public class FacEstadoconfirmfactSqlProvider {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_ESTADOCONFIRMFACT
     *
     * @mbg.generated Tue Nov 16 17:15:00 CET 2021
     */
    public String countByExample(FacEstadoconfirmfactExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("FAC_ESTADOCONFIRMFACT");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_ESTADOCONFIRMFACT
     *
     * @mbg.generated Tue Nov 16 17:15:00 CET 2021
     */
    public String deleteByExample(FacEstadoconfirmfactExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("FAC_ESTADOCONFIRMFACT");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_ESTADOCONFIRMFACT
     *
     * @mbg.generated Tue Nov 16 17:15:00 CET 2021
     */
    public String insertSelective(FacEstadoconfirmfact record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("FAC_ESTADOCONFIRMFACT");
        
        if (record.getIdestado() != null) {
            sql.VALUES("IDESTADO", "#{idestado,jdbcType=DECIMAL}");
        }
        
        if (record.getAlias() != null) {
            sql.VALUES("ALIAS", "#{alias,jdbcType=VARCHAR}");
        }
        
        if (record.getDescripcion() != null) {
            sql.VALUES("DESCRIPCION", "#{descripcion,jdbcType=VARCHAR}");
        }
        
        if (record.getTipo() != null) {
            sql.VALUES("TIPO", "#{tipo,jdbcType=VARCHAR}");
        }
        
        if (record.getLenguaje() != null) {
            sql.VALUES("LENGUAJE", "#{lenguaje,jdbcType=VARCHAR}");
        }
        
        if (record.getOrden() != null) {
            sql.VALUES("ORDEN", "#{orden,jdbcType=DECIMAL}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_ESTADOCONFIRMFACT
     *
     * @mbg.generated Tue Nov 16 17:15:00 CET 2021
     */
    public String selectByExample(FacEstadoconfirmfactExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("IDESTADO");
        } else {
            sql.SELECT("IDESTADO");
        }
        sql.SELECT("ALIAS");
        sql.SELECT("DESCRIPCION");
        sql.SELECT("TIPO");
        sql.SELECT("LENGUAJE");
        sql.SELECT("ORDEN");
        sql.FROM("FAC_ESTADOCONFIRMFACT");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_ESTADOCONFIRMFACT
     *
     * @mbg.generated Tue Nov 16 17:15:00 CET 2021
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        FacEstadoconfirmfact record = (FacEstadoconfirmfact) parameter.get("record");
        FacEstadoconfirmfactExample example = (FacEstadoconfirmfactExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("FAC_ESTADOCONFIRMFACT");
        
        if (record.getIdestado() != null) {
            sql.SET("IDESTADO = #{record.idestado,jdbcType=DECIMAL}");
        }
        
        if (record.getAlias() != null) {
            sql.SET("ALIAS = #{record.alias,jdbcType=VARCHAR}");
        }
        
        if (record.getDescripcion() != null) {
            sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
        }
        
        if (record.getTipo() != null) {
            sql.SET("TIPO = #{record.tipo,jdbcType=VARCHAR}");
        }
        
        if (record.getLenguaje() != null) {
            sql.SET("LENGUAJE = #{record.lenguaje,jdbcType=VARCHAR}");
        }
        
        if (record.getOrden() != null) {
            sql.SET("ORDEN = #{record.orden,jdbcType=DECIMAL}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_ESTADOCONFIRMFACT
     *
     * @mbg.generated Tue Nov 16 17:15:00 CET 2021
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("FAC_ESTADOCONFIRMFACT");
        
        sql.SET("IDESTADO = #{record.idestado,jdbcType=DECIMAL}");
        sql.SET("ALIAS = #{record.alias,jdbcType=VARCHAR}");
        sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
        sql.SET("TIPO = #{record.tipo,jdbcType=VARCHAR}");
        sql.SET("LENGUAJE = #{record.lenguaje,jdbcType=VARCHAR}");
        sql.SET("ORDEN = #{record.orden,jdbcType=DECIMAL}");
        
        FacEstadoconfirmfactExample example = (FacEstadoconfirmfactExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_ESTADOCONFIRMFACT
     *
     * @mbg.generated Tue Nov 16 17:15:00 CET 2021
     */
    public String updateByPrimaryKeySelective(FacEstadoconfirmfact record) {
        SQL sql = new SQL();
        sql.UPDATE("FAC_ESTADOCONFIRMFACT");
        
        if (record.getAlias() != null) {
            sql.SET("ALIAS = #{alias,jdbcType=VARCHAR}");
        }
        
        if (record.getDescripcion() != null) {
            sql.SET("DESCRIPCION = #{descripcion,jdbcType=VARCHAR}");
        }
        
        if (record.getTipo() != null) {
            sql.SET("TIPO = #{tipo,jdbcType=VARCHAR}");
        }
        
        if (record.getLenguaje() != null) {
            sql.SET("LENGUAJE = #{lenguaje,jdbcType=VARCHAR}");
        }
        
        if (record.getOrden() != null) {
            sql.SET("ORDEN = #{orden,jdbcType=DECIMAL}");
        }
        
        sql.WHERE("IDESTADO = #{idestado,jdbcType=DECIMAL}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_ESTADOCONFIRMFACT
     *
     * @mbg.generated Tue Nov 16 17:15:00 CET 2021
     */
    protected void applyWhere(SQL sql, FacEstadoconfirmfactExample example, boolean includeExamplePhrase) {
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