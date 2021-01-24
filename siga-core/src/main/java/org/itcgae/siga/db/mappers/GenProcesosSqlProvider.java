package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.GenProcesos;
import org.itcgae.siga.db.entities.GenProcesosExample;
import org.itcgae.siga.db.entities.GenProcesosExample.Criteria;
import org.itcgae.siga.db.entities.GenProcesosExample.Criterion;

public class GenProcesosSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_PROCESOS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String countByExample(GenProcesosExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("GEN_PROCESOS");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_PROCESOS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String deleteByExample(GenProcesosExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("GEN_PROCESOS");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_PROCESOS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String insertSelective(GenProcesos record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("GEN_PROCESOS");
        
        if (record.getIdproceso() != null) {
            sql.VALUES("IDPROCESO", "#{idproceso,jdbcType=VARCHAR}");
        }
        
        if (record.getIdmodulo() != null) {
            sql.VALUES("IDMODULO", "#{idmodulo,jdbcType=VARCHAR}");
        }
        
        if (record.getTraza() != null) {
            sql.VALUES("TRAZA", "#{traza,jdbcType=DECIMAL}");
        }
        
        if (record.getTarget() != null) {
            sql.VALUES("TARGET", "#{target,jdbcType=VARCHAR}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getDescripcion() != null) {
            sql.VALUES("DESCRIPCION", "#{descripcion,jdbcType=VARCHAR}");
        }
        
        if (record.getTransaccion() != null) {
            sql.VALUES("TRANSACCION", "#{transaccion,jdbcType=VARCHAR}");
        }
        
        if (record.getIdparent() != null) {
            sql.VALUES("IDPARENT", "#{idparent,jdbcType=VARCHAR}");
        }
        
        if (record.getNivel() != null) {
            sql.VALUES("NIVEL", "#{nivel,jdbcType=DECIMAL}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_PROCESOS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String selectByExample(GenProcesosExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("IDPROCESO");
        } else {
            sql.SELECT("IDPROCESO");
        }
        sql.SELECT("IDMODULO");
        sql.SELECT("TRAZA");
        sql.SELECT("TARGET");
        sql.SELECT("FECHAMODIFICACION");
        sql.SELECT("USUMODIFICACION");
        sql.SELECT("DESCRIPCION");
        sql.SELECT("TRANSACCION");
        sql.SELECT("IDPARENT");
        sql.SELECT("NIVEL");
        sql.FROM("GEN_PROCESOS");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_PROCESOS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        GenProcesos record = (GenProcesos) parameter.get("record");
        GenProcesosExample example = (GenProcesosExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("GEN_PROCESOS");
        
        if (record.getIdproceso() != null) {
            sql.SET("IDPROCESO = #{record.idproceso,jdbcType=VARCHAR}");
        }
        
        if (record.getIdmodulo() != null) {
            sql.SET("IDMODULO = #{record.idmodulo,jdbcType=VARCHAR}");
        }
        
        if (record.getTraza() != null) {
            sql.SET("TRAZA = #{record.traza,jdbcType=DECIMAL}");
        }
        
        if (record.getTarget() != null) {
            sql.SET("TARGET = #{record.target,jdbcType=VARCHAR}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getDescripcion() != null) {
            sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
        }
        
        if (record.getTransaccion() != null) {
            sql.SET("TRANSACCION = #{record.transaccion,jdbcType=VARCHAR}");
        }
        
        if (record.getIdparent() != null) {
            sql.SET("IDPARENT = #{record.idparent,jdbcType=VARCHAR}");
        }
        
        if (record.getNivel() != null) {
            sql.SET("NIVEL = #{record.nivel,jdbcType=DECIMAL}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_PROCESOS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("GEN_PROCESOS");
        
        sql.SET("IDPROCESO = #{record.idproceso,jdbcType=VARCHAR}");
        sql.SET("IDMODULO = #{record.idmodulo,jdbcType=VARCHAR}");
        sql.SET("TRAZA = #{record.traza,jdbcType=DECIMAL}");
        sql.SET("TARGET = #{record.target,jdbcType=VARCHAR}");
        sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
        sql.SET("TRANSACCION = #{record.transaccion,jdbcType=VARCHAR}");
        sql.SET("IDPARENT = #{record.idparent,jdbcType=VARCHAR}");
        sql.SET("NIVEL = #{record.nivel,jdbcType=DECIMAL}");
        
        GenProcesosExample example = (GenProcesosExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_PROCESOS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String updateByPrimaryKeySelective(GenProcesos record) {
        SQL sql = new SQL();
        sql.UPDATE("GEN_PROCESOS");
        
        if (record.getIdmodulo() != null) {
            sql.SET("IDMODULO = #{idmodulo,jdbcType=VARCHAR}");
        }
        
        if (record.getTraza() != null) {
            sql.SET("TRAZA = #{traza,jdbcType=DECIMAL}");
        }
        
        if (record.getTarget() != null) {
            sql.SET("TARGET = #{target,jdbcType=VARCHAR}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getDescripcion() != null) {
            sql.SET("DESCRIPCION = #{descripcion,jdbcType=VARCHAR}");
        }
        
        if (record.getTransaccion() != null) {
            sql.SET("TRANSACCION = #{transaccion,jdbcType=VARCHAR}");
        }
        
        if (record.getIdparent() != null) {
            sql.SET("IDPARENT = #{idparent,jdbcType=VARCHAR}");
        }
        
        if (record.getNivel() != null) {
            sql.SET("NIVEL = #{nivel,jdbcType=DECIMAL}");
        }
        
        sql.WHERE("IDPROCESO = #{idproceso,jdbcType=VARCHAR}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_PROCESOS
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    protected void applyWhere(SQL sql, GenProcesosExample example, boolean includeExamplePhrase) {
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