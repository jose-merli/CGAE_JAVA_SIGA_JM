package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.ScsDictamenejg;
import org.itcgae.siga.db.entities.ScsDictamenejgExample.Criteria;
import org.itcgae.siga.db.entities.ScsDictamenejgExample.Criterion;
import org.itcgae.siga.db.entities.ScsDictamenejgExample;

public class ScsDictamenejgSqlProvider {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DICTAMENEJG
     *
     * @mbg.generated Mon Jul 05 19:23:10 CEST 2021
     */
    public String countByExample(ScsDictamenejgExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("SCS_DICTAMENEJG");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DICTAMENEJG
     *
     * @mbg.generated Mon Jul 05 19:23:10 CEST 2021
     */
    public String deleteByExample(ScsDictamenejgExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("SCS_DICTAMENEJG");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DICTAMENEJG
     *
     * @mbg.generated Mon Jul 05 19:23:10 CEST 2021
     */
    public String insertSelective(ScsDictamenejg record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("SCS_DICTAMENEJG");
        
        if (record.getIdinstitucion() != null) {
            sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getIddictamen() != null) {
            sql.VALUES("IDDICTAMEN", "#{iddictamen,jdbcType=DECIMAL}");
        }
        
        if (record.getAbreviatura() != null) {
            sql.VALUES("ABREVIATURA", "#{abreviatura,jdbcType=VARCHAR}");
        }
        
        if (record.getDescripcion() != null) {
            sql.VALUES("DESCRIPCION", "#{descripcion,jdbcType=VARCHAR}");
        }
        
        if (record.getIdtipodictamen() != null) {
            sql.VALUES("IDTIPODICTAMEN", "#{idtipodictamen,jdbcType=DECIMAL}");
        }
        
        if (record.getIdfundamento() != null) {
            sql.VALUES("IDFUNDAMENTO", "#{idfundamento,jdbcType=DECIMAL}");
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
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DICTAMENEJG
     *
     * @mbg.generated Mon Jul 05 19:23:10 CEST 2021
     */
    public String selectByExample(ScsDictamenejgExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("IDINSTITUCION");
        } else {
            sql.SELECT("IDINSTITUCION");
        }
        sql.SELECT("IDDICTAMEN");
        sql.SELECT("ABREVIATURA");
        sql.SELECT("DESCRIPCION");
        sql.SELECT("IDTIPODICTAMEN");
        sql.SELECT("IDFUNDAMENTO");
        sql.SELECT("FECHAMODIFICACION");
        sql.SELECT("USUMODIFICACION");
        sql.SELECT("CODIGOEXT");
        sql.FROM("SCS_DICTAMENEJG");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DICTAMENEJG
     *
     * @mbg.generated Mon Jul 05 19:23:10 CEST 2021
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        ScsDictamenejg record = (ScsDictamenejg) parameter.get("record");
        ScsDictamenejgExample example = (ScsDictamenejgExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("SCS_DICTAMENEJG");
        
        if (record.getIdinstitucion() != null) {
            sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getIddictamen() != null) {
            sql.SET("IDDICTAMEN = #{record.iddictamen,jdbcType=DECIMAL}");
        }
        
        if (record.getAbreviatura() != null) {
            sql.SET("ABREVIATURA = #{record.abreviatura,jdbcType=VARCHAR}");
        }
        
        if (record.getDescripcion() != null) {
            sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
        }
        
        if (record.getIdtipodictamen() != null) {
            sql.SET("IDTIPODICTAMEN = #{record.idtipodictamen,jdbcType=DECIMAL}");
        }
        
        if (record.getIdfundamento() != null) {
            sql.SET("IDFUNDAMENTO = #{record.idfundamento,jdbcType=DECIMAL}");
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
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DICTAMENEJG
     *
     * @mbg.generated Mon Jul 05 19:23:10 CEST 2021
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("SCS_DICTAMENEJG");
        
        sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        sql.SET("IDDICTAMEN = #{record.iddictamen,jdbcType=DECIMAL}");
        sql.SET("ABREVIATURA = #{record.abreviatura,jdbcType=VARCHAR}");
        sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
        sql.SET("IDTIPODICTAMEN = #{record.idtipodictamen,jdbcType=DECIMAL}");
        sql.SET("IDFUNDAMENTO = #{record.idfundamento,jdbcType=DECIMAL}");
        sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        sql.SET("CODIGOEXT = #{record.codigoext,jdbcType=VARCHAR}");
        
        ScsDictamenejgExample example = (ScsDictamenejgExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DICTAMENEJG
     *
     * @mbg.generated Mon Jul 05 19:23:10 CEST 2021
     */
    public String updateByPrimaryKeySelective(ScsDictamenejg record) {
        SQL sql = new SQL();
        sql.UPDATE("SCS_DICTAMENEJG");
        
        if (record.getAbreviatura() != null) {
            sql.SET("ABREVIATURA = #{abreviatura,jdbcType=VARCHAR}");
        }
        
        if (record.getDescripcion() != null) {
            sql.SET("DESCRIPCION = #{descripcion,jdbcType=VARCHAR}");
        }
        
        if (record.getIdtipodictamen() != null) {
            sql.SET("IDTIPODICTAMEN = #{idtipodictamen,jdbcType=DECIMAL}");
        }
        
        if (record.getIdfundamento() != null) {
            sql.SET("IDFUNDAMENTO = #{idfundamento,jdbcType=DECIMAL}");
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
        
        sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
        sql.WHERE("IDDICTAMEN = #{iddictamen,jdbcType=DECIMAL}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DICTAMENEJG
     *
     * @mbg.generated Mon Jul 05 19:23:10 CEST 2021
     */
    protected void applyWhere(SQL sql, ScsDictamenejgExample example, boolean includeExamplePhrase) {
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