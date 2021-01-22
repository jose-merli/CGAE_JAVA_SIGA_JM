package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.AgeRepeticionevento;
import org.itcgae.siga.db.entities.AgeRepeticioneventoExample;
import org.itcgae.siga.db.entities.AgeRepeticioneventoExample.Criteria;
import org.itcgae.siga.db.entities.AgeRepeticioneventoExample.Criterion;

public class AgeRepeticioneventoSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_REPETICIONEVENTO
     *
     * @mbg.generated Mon Nov 19 12:40:02 CET 2018
     */
    public String countByExample(AgeRepeticioneventoExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("AGE_REPETICIONEVENTO");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_REPETICIONEVENTO
     *
     * @mbg.generated Mon Nov 19 12:40:02 CET 2018
     */
    public String deleteByExample(AgeRepeticioneventoExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("AGE_REPETICIONEVENTO");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_REPETICIONEVENTO
     *
     * @mbg.generated Mon Nov 19 12:40:02 CET 2018
     */
    public String insertSelective(AgeRepeticionevento record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("AGE_REPETICIONEVENTO");
        
        sql.VALUES("IDREPETICIONEVENTO", "#{idrepeticionevento,jdbcType=DECIMAL}");
        
        if (record.getUsumodificacion() != null) {
            sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getFechainicio() != null) {
            sql.VALUES("FECHAINICIO", "#{fechainicio,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechafin() != null) {
            sql.VALUES("FECHAFIN", "#{fechafin,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechabaja() != null) {
            sql.VALUES("FECHABAJA", "#{fechabaja,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getValoresrepeticion() != null) {
            sql.VALUES("VALORESREPETICION", "#{valoresrepeticion,jdbcType=VARCHAR}");
        }
        
        if (record.getIdinstitucion() != null) {
            sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getTiporepeticion() != null) {
            sql.VALUES("TIPOREPETICION", "#{tiporepeticion,jdbcType=VARCHAR}");
        }
        
        if (record.getTipodiasrepeticion() != null) {
            sql.VALUES("TIPODIASREPETICION", "#{tipodiasrepeticion,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_REPETICIONEVENTO
     *
     * @mbg.generated Mon Nov 19 12:40:02 CET 2018
     */
    public String selectByExample(AgeRepeticioneventoExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("IDREPETICIONEVENTO");
        } else {
            sql.SELECT("IDREPETICIONEVENTO");
        }
        sql.SELECT("USUMODIFICACION");
        sql.SELECT("FECHAINICIO");
        sql.SELECT("FECHAFIN");
        sql.SELECT("FECHABAJA");
        sql.SELECT("FECHAMODIFICACION");
        sql.SELECT("VALORESREPETICION");
        sql.SELECT("IDINSTITUCION");
        sql.SELECT("TIPOREPETICION");
        sql.SELECT("TIPODIASREPETICION");
        sql.FROM("AGE_REPETICIONEVENTO");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_REPETICIONEVENTO
     *
     * @mbg.generated Mon Nov 19 12:40:02 CET 2018
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        AgeRepeticionevento record = (AgeRepeticionevento) parameter.get("record");
        AgeRepeticioneventoExample example = (AgeRepeticioneventoExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("AGE_REPETICIONEVENTO");
        
        if (record.getIdrepeticionevento() != null) {
            sql.SET("IDREPETICIONEVENTO = #{record.idrepeticionevento,jdbcType=DECIMAL}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getFechainicio() != null) {
            sql.SET("FECHAINICIO = #{record.fechainicio,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechafin() != null) {
            sql.SET("FECHAFIN = #{record.fechafin,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechabaja() != null) {
            sql.SET("FECHABAJA = #{record.fechabaja,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getValoresrepeticion() != null) {
            sql.SET("VALORESREPETICION = #{record.valoresrepeticion,jdbcType=VARCHAR}");
        }
        
        if (record.getIdinstitucion() != null) {
            sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getTiporepeticion() != null) {
            sql.SET("TIPOREPETICION = #{record.tiporepeticion,jdbcType=VARCHAR}");
        }
        
        if (record.getTipodiasrepeticion() != null) {
            sql.SET("TIPODIASREPETICION = #{record.tipodiasrepeticion,jdbcType=VARCHAR}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_REPETICIONEVENTO
     *
     * @mbg.generated Mon Nov 19 12:40:02 CET 2018
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("AGE_REPETICIONEVENTO");
        
        sql.SET("IDREPETICIONEVENTO = #{record.idrepeticionevento,jdbcType=DECIMAL}");
        sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        sql.SET("FECHAINICIO = #{record.fechainicio,jdbcType=TIMESTAMP}");
        sql.SET("FECHAFIN = #{record.fechafin,jdbcType=TIMESTAMP}");
        sql.SET("FECHABAJA = #{record.fechabaja,jdbcType=TIMESTAMP}");
        sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        sql.SET("VALORESREPETICION = #{record.valoresrepeticion,jdbcType=VARCHAR}");
        sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        sql.SET("TIPOREPETICION = #{record.tiporepeticion,jdbcType=VARCHAR}");
        sql.SET("TIPODIASREPETICION = #{record.tipodiasrepeticion,jdbcType=VARCHAR}");
        
        AgeRepeticioneventoExample example = (AgeRepeticioneventoExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_REPETICIONEVENTO
     *
     * @mbg.generated Mon Nov 19 12:40:02 CET 2018
     */
    public String updateByPrimaryKeySelective(AgeRepeticionevento record) {
        SQL sql = new SQL();
        sql.UPDATE("AGE_REPETICIONEVENTO");
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getFechainicio() != null) {
            sql.SET("FECHAINICIO = #{fechainicio,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechafin() != null) {
            sql.SET("FECHAFIN = #{fechafin,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechabaja() != null) {
            sql.SET("FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getValoresrepeticion() != null) {
            sql.SET("VALORESREPETICION = #{valoresrepeticion,jdbcType=VARCHAR}");
        }
        
        if (record.getIdinstitucion() != null) {
            sql.SET("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getTiporepeticion() != null) {
            sql.SET("TIPOREPETICION = #{tiporepeticion,jdbcType=VARCHAR}");
        }
        
        if (record.getTipodiasrepeticion() != null) {
            sql.SET("TIPODIASREPETICION = #{tipodiasrepeticion,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("IDREPETICIONEVENTO = #{idrepeticionevento,jdbcType=DECIMAL}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_REPETICIONEVENTO
     *
     * @mbg.generated Mon Nov 19 12:40:02 CET 2018
     */
    protected void applyWhere(SQL sql, AgeRepeticioneventoExample example, boolean includeExamplePhrase) {
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