package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.ScsDelitosasistencia;
import org.itcgae.siga.db.entities.ScsDelitosasistenciaExample.Criteria;
import org.itcgae.siga.db.entities.ScsDelitosasistenciaExample.Criterion;
import org.itcgae.siga.db.entities.ScsDelitosasistenciaExample;

public class ScsDelitosasistenciaSqlProvider {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DELITOSASISTENCIA
     *
     * @mbg.generated Thu Jul 22 14:57:55 CEST 2021
     */
    public String countByExample(ScsDelitosasistenciaExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("SCS_DELITOSASISTENCIA");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DELITOSASISTENCIA
     *
     * @mbg.generated Thu Jul 22 14:57:55 CEST 2021
     */
    public String deleteByExample(ScsDelitosasistenciaExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("SCS_DELITOSASISTENCIA");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DELITOSASISTENCIA
     *
     * @mbg.generated Thu Jul 22 14:57:55 CEST 2021
     */
    public String insertSelective(ScsDelitosasistencia record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("SCS_DELITOSASISTENCIA");
        
        if (record.getIdinstitucion() != null) {
            sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getNumero() != null) {
            sql.VALUES("NUMERO", "#{numero,jdbcType=DECIMAL}");
        }
        
        if (record.getAnio() != null) {
            sql.VALUES("ANIO", "#{anio,jdbcType=DECIMAL}");
        }
        
        if (record.getIddelito() != null) {
            sql.VALUES("IDDELITO", "#{iddelito,jdbcType=DECIMAL}");
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
     * This method corresponds to the database table USCGAE.SCS_DELITOSASISTENCIA
     *
     * @mbg.generated Thu Jul 22 14:57:55 CEST 2021
     */
    public String selectByExample(ScsDelitosasistenciaExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("IDINSTITUCION");
        } else {
            sql.SELECT("IDINSTITUCION");
        }
        sql.SELECT("NUMERO");
        sql.SELECT("ANIO");
        sql.SELECT("IDDELITO");
        sql.SELECT("FECHAMODIFICACION");
        sql.SELECT("USUMODIFICACION");
        sql.FROM("SCS_DELITOSASISTENCIA");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DELITOSASISTENCIA
     *
     * @mbg.generated Thu Jul 22 14:57:55 CEST 2021
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        ScsDelitosasistencia record = (ScsDelitosasistencia) parameter.get("record");
        ScsDelitosasistenciaExample example = (ScsDelitosasistenciaExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("SCS_DELITOSASISTENCIA");
        
        if (record.getIdinstitucion() != null) {
            sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getNumero() != null) {
            sql.SET("NUMERO = #{record.numero,jdbcType=DECIMAL}");
        }
        
        if (record.getAnio() != null) {
            sql.SET("ANIO = #{record.anio,jdbcType=DECIMAL}");
        }
        
        if (record.getIddelito() != null) {
            sql.SET("IDDELITO = #{record.iddelito,jdbcType=DECIMAL}");
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
     * This method corresponds to the database table USCGAE.SCS_DELITOSASISTENCIA
     *
     * @mbg.generated Thu Jul 22 14:57:55 CEST 2021
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("SCS_DELITOSASISTENCIA");
        
        sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        sql.SET("NUMERO = #{record.numero,jdbcType=DECIMAL}");
        sql.SET("ANIO = #{record.anio,jdbcType=DECIMAL}");
        sql.SET("IDDELITO = #{record.iddelito,jdbcType=DECIMAL}");
        sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        
        ScsDelitosasistenciaExample example = (ScsDelitosasistenciaExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DELITOSASISTENCIA
     *
     * @mbg.generated Thu Jul 22 14:57:55 CEST 2021
     */
    public String updateByPrimaryKeySelective(ScsDelitosasistencia record) {
        SQL sql = new SQL();
        sql.UPDATE("SCS_DELITOSASISTENCIA");
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
        }
        
        sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
        sql.WHERE("NUMERO = #{numero,jdbcType=DECIMAL}");
        sql.WHERE("ANIO = #{anio,jdbcType=DECIMAL}");
        sql.WHERE("IDDELITO = #{iddelito,jdbcType=DECIMAL}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DELITOSASISTENCIA
     *
     * @mbg.generated Thu Jul 22 14:57:55 CEST 2021
     */
    protected void applyWhere(SQL sql, ScsDelitosasistenciaExample example, boolean includeExamplePhrase) {
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