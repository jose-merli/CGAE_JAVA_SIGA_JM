package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.ExpTipoexpediente;
import org.itcgae.siga.db.entities.ExpTipoexpedienteExample.Criteria;
import org.itcgae.siga.db.entities.ExpTipoexpedienteExample.Criterion;
import org.itcgae.siga.db.entities.ExpTipoexpedienteExample;

public class ExpTipoexpedienteSqlProvider {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.EXP_TIPOEXPEDIENTE
     *
     * @mbg.generated Fri Jan 24 12:47:22 CET 2020
     */
    public String countByExample(ExpTipoexpedienteExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("EXP_TIPOEXPEDIENTE");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.EXP_TIPOEXPEDIENTE
     *
     * @mbg.generated Fri Jan 24 12:47:22 CET 2020
     */
    public String deleteByExample(ExpTipoexpedienteExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("EXP_TIPOEXPEDIENTE");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.EXP_TIPOEXPEDIENTE
     *
     * @mbg.generated Fri Jan 24 12:47:22 CET 2020
     */
    public String insertSelective(ExpTipoexpediente record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("EXP_TIPOEXPEDIENTE");
        
        if (record.getIdinstitucion() != null) {
            sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdtipoexpediente() != null) {
            sql.VALUES("IDTIPOEXPEDIENTE", "#{idtipoexpediente,jdbcType=DECIMAL}");
        }
        
        if (record.getNombre() != null) {
            sql.VALUES("NOMBRE", "#{nombre,jdbcType=VARCHAR}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getEsgeneral() != null) {
            sql.VALUES("ESGENERAL", "#{esgeneral,jdbcType=VARCHAR}");
        }
        
        if (record.getTiempocaducidad() != null) {
            sql.VALUES("TIEMPOCADUCIDAD", "#{tiempocaducidad,jdbcType=VARCHAR}");
        }
        
        if (record.getDiasantelacioncad() != null) {
            sql.VALUES("DIASANTELACIONCAD", "#{diasantelacioncad,jdbcType=DECIMAL}");
        }
        
        if (record.getRelacionejg() != null) {
            sql.VALUES("RELACIONEJG", "#{relacionejg,jdbcType=DECIMAL}");
        }
        
        if (record.getEnviaravisos() != null) {
            sql.VALUES("ENVIARAVISOS", "#{enviaravisos,jdbcType=DECIMAL}");
        }
        
        if (record.getIdtipoenvios() != null) {
            sql.VALUES("IDTIPOENVIOS", "#{idtipoenvios,jdbcType=DECIMAL}");
        }
        
        if (record.getIdplantillaenvios() != null) {
            sql.VALUES("IDPLANTILLAENVIOS", "#{idplantillaenvios,jdbcType=DECIMAL}");
        }
        
        if (record.getIdplantilla() != null) {
            sql.VALUES("IDPLANTILLA", "#{idplantilla,jdbcType=DECIMAL}");
        }
        
        if (record.getRelacionexpediente() != null) {
            sql.VALUES("RELACIONEXPEDIENTE", "#{relacionexpediente,jdbcType=DECIMAL}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.EXP_TIPOEXPEDIENTE
     *
     * @mbg.generated Fri Jan 24 12:47:22 CET 2020
     */
    public String selectByExample(ExpTipoexpedienteExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("IDINSTITUCION");
        } else {
            sql.SELECT("IDINSTITUCION");
        }
        sql.SELECT("IDTIPOEXPEDIENTE");
        sql.SELECT("NOMBRE");
        sql.SELECT("FECHAMODIFICACION");
        sql.SELECT("USUMODIFICACION");
        sql.SELECT("ESGENERAL");
        sql.SELECT("TIEMPOCADUCIDAD");
        sql.SELECT("DIASANTELACIONCAD");
        sql.SELECT("RELACIONEJG");
        sql.SELECT("ENVIARAVISOS");
        sql.SELECT("IDTIPOENVIOS");
        sql.SELECT("IDPLANTILLAENVIOS");
        sql.SELECT("IDPLANTILLA");
        sql.SELECT("RELACIONEXPEDIENTE");
        sql.FROM("EXP_TIPOEXPEDIENTE");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.EXP_TIPOEXPEDIENTE
     *
     * @mbg.generated Fri Jan 24 12:47:22 CET 2020
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        ExpTipoexpediente record = (ExpTipoexpediente) parameter.get("record");
        ExpTipoexpedienteExample example = (ExpTipoexpedienteExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("EXP_TIPOEXPEDIENTE");
        
        if (record.getIdinstitucion() != null) {
            sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdtipoexpediente() != null) {
            sql.SET("IDTIPOEXPEDIENTE = #{record.idtipoexpediente,jdbcType=DECIMAL}");
        }
        
        if (record.getNombre() != null) {
            sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getEsgeneral() != null) {
            sql.SET("ESGENERAL = #{record.esgeneral,jdbcType=VARCHAR}");
        }
        
        if (record.getTiempocaducidad() != null) {
            sql.SET("TIEMPOCADUCIDAD = #{record.tiempocaducidad,jdbcType=VARCHAR}");
        }
        
        if (record.getDiasantelacioncad() != null) {
            sql.SET("DIASANTELACIONCAD = #{record.diasantelacioncad,jdbcType=DECIMAL}");
        }
        
        if (record.getRelacionejg() != null) {
            sql.SET("RELACIONEJG = #{record.relacionejg,jdbcType=DECIMAL}");
        }
        
        if (record.getEnviaravisos() != null) {
            sql.SET("ENVIARAVISOS = #{record.enviaravisos,jdbcType=DECIMAL}");
        }
        
        if (record.getIdtipoenvios() != null) {
            sql.SET("IDTIPOENVIOS = #{record.idtipoenvios,jdbcType=DECIMAL}");
        }
        
        if (record.getIdplantillaenvios() != null) {
            sql.SET("IDPLANTILLAENVIOS = #{record.idplantillaenvios,jdbcType=DECIMAL}");
        }
        
        if (record.getIdplantilla() != null) {
            sql.SET("IDPLANTILLA = #{record.idplantilla,jdbcType=DECIMAL}");
        }
        
        if (record.getRelacionexpediente() != null) {
            sql.SET("RELACIONEXPEDIENTE = #{record.relacionexpediente,jdbcType=DECIMAL}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.EXP_TIPOEXPEDIENTE
     *
     * @mbg.generated Fri Jan 24 12:47:22 CET 2020
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("EXP_TIPOEXPEDIENTE");
        
        sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        sql.SET("IDTIPOEXPEDIENTE = #{record.idtipoexpediente,jdbcType=DECIMAL}");
        sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
        sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        sql.SET("ESGENERAL = #{record.esgeneral,jdbcType=VARCHAR}");
        sql.SET("TIEMPOCADUCIDAD = #{record.tiempocaducidad,jdbcType=VARCHAR}");
        sql.SET("DIASANTELACIONCAD = #{record.diasantelacioncad,jdbcType=DECIMAL}");
        sql.SET("RELACIONEJG = #{record.relacionejg,jdbcType=DECIMAL}");
        sql.SET("ENVIARAVISOS = #{record.enviaravisos,jdbcType=DECIMAL}");
        sql.SET("IDTIPOENVIOS = #{record.idtipoenvios,jdbcType=DECIMAL}");
        sql.SET("IDPLANTILLAENVIOS = #{record.idplantillaenvios,jdbcType=DECIMAL}");
        sql.SET("IDPLANTILLA = #{record.idplantilla,jdbcType=DECIMAL}");
        sql.SET("RELACIONEXPEDIENTE = #{record.relacionexpediente,jdbcType=DECIMAL}");
        
        ExpTipoexpedienteExample example = (ExpTipoexpedienteExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.EXP_TIPOEXPEDIENTE
     *
     * @mbg.generated Fri Jan 24 12:47:22 CET 2020
     */
    public String updateByPrimaryKeySelective(ExpTipoexpediente record) {
        SQL sql = new SQL();
        sql.UPDATE("EXP_TIPOEXPEDIENTE");
        
        if (record.getNombre() != null) {
            sql.SET("NOMBRE = #{nombre,jdbcType=VARCHAR}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getEsgeneral() != null) {
            sql.SET("ESGENERAL = #{esgeneral,jdbcType=VARCHAR}");
        }
        
        if (record.getTiempocaducidad() != null) {
            sql.SET("TIEMPOCADUCIDAD = #{tiempocaducidad,jdbcType=VARCHAR}");
        }
        
        if (record.getDiasantelacioncad() != null) {
            sql.SET("DIASANTELACIONCAD = #{diasantelacioncad,jdbcType=DECIMAL}");
        }
        
        if (record.getRelacionejg() != null) {
            sql.SET("RELACIONEJG = #{relacionejg,jdbcType=DECIMAL}");
        }
        
        if (record.getEnviaravisos() != null) {
            sql.SET("ENVIARAVISOS = #{enviaravisos,jdbcType=DECIMAL}");
        }
        
        if (record.getIdtipoenvios() != null) {
            sql.SET("IDTIPOENVIOS = #{idtipoenvios,jdbcType=DECIMAL}");
        }
        
        if (record.getIdplantillaenvios() != null) {
            sql.SET("IDPLANTILLAENVIOS = #{idplantillaenvios,jdbcType=DECIMAL}");
        }
        
        if (record.getIdplantilla() != null) {
            sql.SET("IDPLANTILLA = #{idplantilla,jdbcType=DECIMAL}");
        }
        
        if (record.getRelacionexpediente() != null) {
            sql.SET("RELACIONEXPEDIENTE = #{relacionexpediente,jdbcType=DECIMAL}");
        }
        
        sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
        sql.WHERE("IDTIPOEXPEDIENTE = #{idtipoexpediente,jdbcType=DECIMAL}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.EXP_TIPOEXPEDIENTE
     *
     * @mbg.generated Fri Jan 24 12:47:22 CET 2020
     */
    protected void applyWhere(SQL sql, ExpTipoexpedienteExample example, boolean includeExamplePhrase) {
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