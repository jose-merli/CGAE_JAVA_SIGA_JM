package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.EnvEnvioprogramado;
import org.itcgae.siga.db.entities.EnvEnvioprogramadoExample;
import org.itcgae.siga.db.entities.EnvEnvioprogramadoExample.Criteria;
import org.itcgae.siga.db.entities.EnvEnvioprogramadoExample.Criterion;

public class EnvEnvioprogramadoSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_ENVIOPROGRAMADO
     *
     * @mbg.generated Thu Feb 21 17:54:10 CET 2019
     */
    public String countByExample(EnvEnvioprogramadoExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("ENV_ENVIOPROGRAMADO");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_ENVIOPROGRAMADO
     *
     * @mbg.generated Thu Feb 21 17:54:10 CET 2019
     */
    public String deleteByExample(EnvEnvioprogramadoExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("ENV_ENVIOPROGRAMADO");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_ENVIOPROGRAMADO
     *
     * @mbg.generated Thu Feb 21 17:54:10 CET 2019
     */
    public String insertSelective(EnvEnvioprogramado record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("ENV_ENVIOPROGRAMADO");
        
        if (record.getIdenvio() != null) {
            sql.VALUES("IDENVIO", "#{idenvio,jdbcType=DECIMAL}");
        }
        
        if (record.getIdinstitucion() != null) {
            sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getEstado() != null) {
            sql.VALUES("ESTADO", "#{estado,jdbcType=VARCHAR}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdtipoenvios() != null) {
            sql.VALUES("IDTIPOENVIOS", "#{idtipoenvios,jdbcType=DECIMAL}");
        }
        
        if (record.getIdplantillaenvios() != null) {
            sql.VALUES("IDPLANTILLAENVIOS", "#{idplantillaenvios,jdbcType=DECIMAL}");
        }
        
        if (record.getNombre() != null) {
            sql.VALUES("NOMBRE", "#{nombre,jdbcType=VARCHAR}");
        }
        
        if (record.getFechaprogramada() != null) {
            sql.VALUES("FECHAPROGRAMADA", "#{fechaprogramada,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIdplantilla() != null) {
            sql.VALUES("IDPLANTILLA", "#{idplantilla,jdbcType=DECIMAL}");
        }
        
        if (record.getAcuserecibo() != null) {
            sql.VALUES("ACUSERECIBO", "#{acuserecibo,jdbcType=VARCHAR}");
        }
        
        if (record.getComisionajg() != null) {
            sql.VALUES("COMISIONAJG", "#{comisionajg,jdbcType=DECIMAL}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_ENVIOPROGRAMADO
     *
     * @mbg.generated Thu Feb 21 17:54:10 CET 2019
     */
    public String selectByExample(EnvEnvioprogramadoExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("IDENVIO");
        } else {
            sql.SELECT("IDENVIO");
        }
        sql.SELECT("IDINSTITUCION");
        sql.SELECT("ESTADO");
        sql.SELECT("FECHAMODIFICACION");
        sql.SELECT("USUMODIFICACION");
        sql.SELECT("IDTIPOENVIOS");
        sql.SELECT("IDPLANTILLAENVIOS");
        sql.SELECT("NOMBRE");
        sql.SELECT("FECHAPROGRAMADA");
        sql.SELECT("IDPLANTILLA");
        sql.SELECT("ACUSERECIBO");
        sql.SELECT("COMISIONAJG");
        sql.FROM("ENV_ENVIOPROGRAMADO");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_ENVIOPROGRAMADO
     *
     * @mbg.generated Thu Feb 21 17:54:10 CET 2019
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        EnvEnvioprogramado record = (EnvEnvioprogramado) parameter.get("record");
        EnvEnvioprogramadoExample example = (EnvEnvioprogramadoExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("ENV_ENVIOPROGRAMADO");
        
        if (record.getIdenvio() != null) {
            sql.SET("IDENVIO = #{record.idenvio,jdbcType=DECIMAL}");
        }
        
        if (record.getIdinstitucion() != null) {
            sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getEstado() != null) {
            sql.SET("ESTADO = #{record.estado,jdbcType=VARCHAR}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdtipoenvios() != null) {
            sql.SET("IDTIPOENVIOS = #{record.idtipoenvios,jdbcType=DECIMAL}");
        }
        
        if (record.getIdplantillaenvios() != null) {
            sql.SET("IDPLANTILLAENVIOS = #{record.idplantillaenvios,jdbcType=DECIMAL}");
        }
        
        if (record.getNombre() != null) {
            sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
        }
        
        if (record.getFechaprogramada() != null) {
            sql.SET("FECHAPROGRAMADA = #{record.fechaprogramada,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIdplantilla() != null) {
            sql.SET("IDPLANTILLA = #{record.idplantilla,jdbcType=DECIMAL}");
        }
        
        if (record.getAcuserecibo() != null) {
            sql.SET("ACUSERECIBO = #{record.acuserecibo,jdbcType=VARCHAR}");
        }
        
        if (record.getComisionajg() != null) {
            sql.SET("COMISIONAJG = #{record.comisionajg,jdbcType=DECIMAL}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_ENVIOPROGRAMADO
     *
     * @mbg.generated Thu Feb 21 17:54:10 CET 2019
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("ENV_ENVIOPROGRAMADO");
        
        sql.SET("IDENVIO = #{record.idenvio,jdbcType=DECIMAL}");
        sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        sql.SET("ESTADO = #{record.estado,jdbcType=VARCHAR}");
        sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        sql.SET("IDTIPOENVIOS = #{record.idtipoenvios,jdbcType=DECIMAL}");
        sql.SET("IDPLANTILLAENVIOS = #{record.idplantillaenvios,jdbcType=DECIMAL}");
        sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
        sql.SET("FECHAPROGRAMADA = #{record.fechaprogramada,jdbcType=TIMESTAMP}");
        sql.SET("IDPLANTILLA = #{record.idplantilla,jdbcType=DECIMAL}");
        sql.SET("ACUSERECIBO = #{record.acuserecibo,jdbcType=VARCHAR}");
        sql.SET("COMISIONAJG = #{record.comisionajg,jdbcType=DECIMAL}");
        
        EnvEnvioprogramadoExample example = (EnvEnvioprogramadoExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_ENVIOPROGRAMADO
     *
     * @mbg.generated Thu Feb 21 17:54:10 CET 2019
     */
    public String updateByPrimaryKeySelective(EnvEnvioprogramado record) {
        SQL sql = new SQL();
        sql.UPDATE("ENV_ENVIOPROGRAMADO");
        
        if (record.getEstado() != null) {
            sql.SET("ESTADO = #{estado,jdbcType=VARCHAR}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdtipoenvios() != null) {
            sql.SET("IDTIPOENVIOS = #{idtipoenvios,jdbcType=DECIMAL}");
        }
        
        if (record.getIdplantillaenvios() != null) {
            sql.SET("IDPLANTILLAENVIOS = #{idplantillaenvios,jdbcType=DECIMAL}");
        }
        
        if (record.getNombre() != null) {
            sql.SET("NOMBRE = #{nombre,jdbcType=VARCHAR}");
        }
        
        if (record.getFechaprogramada() != null) {
            sql.SET("FECHAPROGRAMADA = #{fechaprogramada,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIdplantilla() != null) {
            sql.SET("IDPLANTILLA = #{idplantilla,jdbcType=DECIMAL}");
        }
        
        if (record.getAcuserecibo() != null) {
            sql.SET("ACUSERECIBO = #{acuserecibo,jdbcType=VARCHAR}");
        }
        
        if (record.getComisionajg() != null) {
            sql.SET("COMISIONAJG = #{comisionajg,jdbcType=DECIMAL}");
        }
        
        sql.WHERE("IDENVIO = #{idenvio,jdbcType=DECIMAL}");
        sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_ENVIOPROGRAMADO
     *
     * @mbg.generated Thu Feb 21 17:54:10 CET 2019
     */
    protected void applyWhere(SQL sql, EnvEnvioprogramadoExample example, boolean includeExamplePhrase) {
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