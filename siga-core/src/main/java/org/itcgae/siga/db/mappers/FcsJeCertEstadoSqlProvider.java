package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.FcsJeCertEstado;
import org.itcgae.siga.db.entities.FcsJeCertEstadoExample.Criteria;
import org.itcgae.siga.db.entities.FcsJeCertEstadoExample.Criterion;
import org.itcgae.siga.db.entities.FcsJeCertEstadoExample;

public class FcsJeCertEstadoSqlProvider {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_JE_CERT_ESTADO
     *
     * @mbg.generated Mon Dec 20 08:56:07 CET 2021
     */
    public String countByExample(FcsJeCertEstadoExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("FCS_JE_CERT_ESTADO");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_JE_CERT_ESTADO
     *
     * @mbg.generated Mon Dec 20 08:56:07 CET 2021
     */
    public String deleteByExample(FcsJeCertEstadoExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("FCS_JE_CERT_ESTADO");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_JE_CERT_ESTADO
     *
     * @mbg.generated Mon Dec 20 08:56:07 CET 2021
     */
    public String insertSelective(FcsJeCertEstado record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("FCS_JE_CERT_ESTADO");
        
        sql.VALUES("IDCERTESTADO", "#{idcertestado,jdbcType=DECIMAL}");
        
        if (record.getIdcertificacion() != null) {
            sql.VALUES("IDCERTIFICACION", "#{idcertificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdestado() != null) {
            sql.VALUES("IDESTADO", "#{idestado,jdbcType=DECIMAL}");
        }
        
        if (record.getIdjeintercambio() != null) {
            sql.VALUES("IDJEINTERCAMBIO", "#{idjeintercambio,jdbcType=DECIMAL}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_JE_CERT_ESTADO
     *
     * @mbg.generated Mon Dec 20 08:56:07 CET 2021
     */
    public String selectByExample(FcsJeCertEstadoExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("IDCERTESTADO");
        } else {
            sql.SELECT("IDCERTESTADO");
        }
        sql.SELECT("IDCERTIFICACION");
        sql.SELECT("IDESTADO");
        sql.SELECT("IDJEINTERCAMBIO");
        sql.FROM("FCS_JE_CERT_ESTADO");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_JE_CERT_ESTADO
     *
     * @mbg.generated Mon Dec 20 08:56:07 CET 2021
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        FcsJeCertEstado record = (FcsJeCertEstado) parameter.get("record");
        FcsJeCertEstadoExample example = (FcsJeCertEstadoExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("FCS_JE_CERT_ESTADO");
        
        if (record.getIdcertestado() != null) {
            sql.SET("IDCERTESTADO = #{record.idcertestado,jdbcType=DECIMAL}");
        }
        
        if (record.getIdcertificacion() != null) {
            sql.SET("IDCERTIFICACION = #{record.idcertificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdestado() != null) {
            sql.SET("IDESTADO = #{record.idestado,jdbcType=DECIMAL}");
        }
        
        if (record.getIdjeintercambio() != null) {
            sql.SET("IDJEINTERCAMBIO = #{record.idjeintercambio,jdbcType=DECIMAL}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_JE_CERT_ESTADO
     *
     * @mbg.generated Mon Dec 20 08:56:07 CET 2021
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("FCS_JE_CERT_ESTADO");
        
        sql.SET("IDCERTESTADO = #{record.idcertestado,jdbcType=DECIMAL}");
        sql.SET("IDCERTIFICACION = #{record.idcertificacion,jdbcType=DECIMAL}");
        sql.SET("IDESTADO = #{record.idestado,jdbcType=DECIMAL}");
        sql.SET("IDJEINTERCAMBIO = #{record.idjeintercambio,jdbcType=DECIMAL}");
        
        FcsJeCertEstadoExample example = (FcsJeCertEstadoExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_JE_CERT_ESTADO
     *
     * @mbg.generated Mon Dec 20 08:56:07 CET 2021
     */
    public String updateByPrimaryKeySelective(FcsJeCertEstado record) {
        SQL sql = new SQL();
        sql.UPDATE("FCS_JE_CERT_ESTADO");
        
        if (record.getIdcertificacion() != null) {
            sql.SET("IDCERTIFICACION = #{idcertificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdestado() != null) {
            sql.SET("IDESTADO = #{idestado,jdbcType=DECIMAL}");
        }
        
        if (record.getIdjeintercambio() != null) {
            sql.SET("IDJEINTERCAMBIO = #{idjeintercambio,jdbcType=DECIMAL}");
        }
        
        sql.WHERE("IDCERTESTADO = #{idcertestado,jdbcType=DECIMAL}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_JE_CERT_ESTADO
     *
     * @mbg.generated Mon Dec 20 08:56:07 CET 2021
     */
    protected void applyWhere(SQL sql, FcsJeCertEstadoExample example, boolean includeExamplePhrase) {
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