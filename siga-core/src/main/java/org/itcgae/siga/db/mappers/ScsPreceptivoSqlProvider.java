package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.ScsPreceptivo;
import org.itcgae.siga.db.entities.ScsPreceptivoExample.Criteria;
import org.itcgae.siga.db.entities.ScsPreceptivoExample.Criterion;
import org.itcgae.siga.db.entities.ScsPreceptivoExample;

public class ScsPreceptivoSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_PRECEPTIVO
     *
     * @mbg.generated Tue Nov 12 17:35:58 CET 2019
     */
    public String countByExample(ScsPreceptivoExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("SCS_PRECEPTIVO");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_PRECEPTIVO
     *
     * @mbg.generated Tue Nov 12 17:35:58 CET 2019
     */
    public String deleteByExample(ScsPreceptivoExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("SCS_PRECEPTIVO");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_PRECEPTIVO
     *
     * @mbg.generated Tue Nov 12 17:35:58 CET 2019
     */
    public String insertSelective(ScsPreceptivo record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("SCS_PRECEPTIVO");
        
        if (record.getIdpreceptivo() != null) {
            sql.VALUES("IDPRECEPTIVO", "#{idpreceptivo,jdbcType=DECIMAL}");
        }
        
        if (record.getDescripcion() != null) {
            sql.VALUES("DESCRIPCION", "#{descripcion,jdbcType=VARCHAR}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getCodigoext() != null) {
            sql.VALUES("CODIGOEXT", "#{codigoext,jdbcType=VARCHAR}");
        }
        
        if (record.getBloqueado() != null) {
            sql.VALUES("BLOQUEADO", "#{bloqueado,jdbcType=CHAR}");
        }
        
        if (record.getCodigoejis() != null) {
            sql.VALUES("CODIGOEJIS", "#{codigoejis,jdbcType=VARCHAR}");
        }
        
        if (record.getFechaBaja() != null) {
            sql.VALUES("FECHA_BAJA", "#{fechaBaja,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_PRECEPTIVO
     *
     * @mbg.generated Tue Nov 12 17:35:58 CET 2019
     */
    public String selectByExample(ScsPreceptivoExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("IDPRECEPTIVO");
        } else {
            sql.SELECT("IDPRECEPTIVO");
        }
        sql.SELECT("DESCRIPCION");
        sql.SELECT("FECHAMODIFICACION");
        sql.SELECT("USUMODIFICACION");
        sql.SELECT("CODIGOEXT");
        sql.SELECT("BLOQUEADO");
        sql.SELECT("CODIGOEJIS");
        sql.SELECT("FECHA_BAJA");
        sql.FROM("SCS_PRECEPTIVO");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_PRECEPTIVO
     *
     * @mbg.generated Tue Nov 12 17:35:58 CET 2019
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        ScsPreceptivo record = (ScsPreceptivo) parameter.get("record");
        ScsPreceptivoExample example = (ScsPreceptivoExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("SCS_PRECEPTIVO");
        
        if (record.getIdpreceptivo() != null) {
            sql.SET("IDPRECEPTIVO = #{record.idpreceptivo,jdbcType=DECIMAL}");
        }
        
        if (record.getDescripcion() != null) {
            sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getCodigoext() != null) {
            sql.SET("CODIGOEXT = #{record.codigoext,jdbcType=VARCHAR}");
        }
        
        if (record.getBloqueado() != null) {
            sql.SET("BLOQUEADO = #{record.bloqueado,jdbcType=CHAR}");
        }
        
        if (record.getCodigoejis() != null) {
            sql.SET("CODIGOEJIS = #{record.codigoejis,jdbcType=VARCHAR}");
        }
        
        if (record.getFechaBaja() != null) {
            sql.SET("FECHA_BAJA = #{record.fechaBaja,jdbcType=TIMESTAMP}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_PRECEPTIVO
     *
     * @mbg.generated Tue Nov 12 17:35:58 CET 2019
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("SCS_PRECEPTIVO");
        
        sql.SET("IDPRECEPTIVO = #{record.idpreceptivo,jdbcType=DECIMAL}");
        sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
        sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        sql.SET("CODIGOEXT = #{record.codigoext,jdbcType=VARCHAR}");
        sql.SET("BLOQUEADO = #{record.bloqueado,jdbcType=CHAR}");
        sql.SET("CODIGOEJIS = #{record.codigoejis,jdbcType=VARCHAR}");
        sql.SET("FECHA_BAJA = #{record.fechaBaja,jdbcType=TIMESTAMP}");
        
        ScsPreceptivoExample example = (ScsPreceptivoExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_PRECEPTIVO
     *
     * @mbg.generated Tue Nov 12 17:35:58 CET 2019
     */
    public String updateByPrimaryKeySelective(ScsPreceptivo record) {
        SQL sql = new SQL();
        sql.UPDATE("SCS_PRECEPTIVO");
        
        if (record.getDescripcion() != null) {
            sql.SET("DESCRIPCION = #{descripcion,jdbcType=VARCHAR}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getCodigoext() != null) {
            sql.SET("CODIGOEXT = #{codigoext,jdbcType=VARCHAR}");
        }
        
        if (record.getBloqueado() != null) {
            sql.SET("BLOQUEADO = #{bloqueado,jdbcType=CHAR}");
        }
        
        if (record.getCodigoejis() != null) {
            sql.SET("CODIGOEJIS = #{codigoejis,jdbcType=VARCHAR}");
        }
        
        if (record.getFechaBaja() != null) {
            sql.SET("FECHA_BAJA = #{fechaBaja,jdbcType=TIMESTAMP}");
        }
        
        sql.WHERE("IDPRECEPTIVO = #{idpreceptivo,jdbcType=DECIMAL}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_PRECEPTIVO
     *
     * @mbg.generated Tue Nov 12 17:35:58 CET 2019
     */
    protected void applyWhere(SQL sql, ScsPreceptivoExample example, boolean includeExamplePhrase) {
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