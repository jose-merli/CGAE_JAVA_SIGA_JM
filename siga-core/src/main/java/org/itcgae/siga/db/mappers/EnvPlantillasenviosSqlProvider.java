package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.EnvPlantillasenviosExample;
import org.itcgae.siga.db.entities.EnvPlantillasenviosExample.Criteria;
import org.itcgae.siga.db.entities.EnvPlantillasenviosExample.Criterion;
import org.itcgae.siga.db.entities.EnvPlantillasenviosWithBLOBs;

public class EnvPlantillasenviosSqlProvider {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLASENVIOS
     *
     * @mbg.generated Mon Jan 20 14:13:09 CET 2020
     */
    public String countByExample(EnvPlantillasenviosExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("ENV_PLANTILLASENVIOS");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLASENVIOS
     *
     * @mbg.generated Mon Jan 20 14:13:09 CET 2020
     */
    public String deleteByExample(EnvPlantillasenviosExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("ENV_PLANTILLASENVIOS");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLASENVIOS
     *
     * @mbg.generated Mon Jan 20 14:13:09 CET 2020
     */
    public String insertSelective(EnvPlantillasenviosWithBLOBs record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("ENV_PLANTILLASENVIOS");
        
        if (record.getIdinstitucion() != null) {
            sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
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
        
        if (record.getFechamodificacion() != null) {
            sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getAcuserecibo() != null) {
            sql.VALUES("ACUSERECIBO", "#{acuserecibo,jdbcType=VARCHAR}");
        }
        
        if (record.getFechabaja() != null) {
            sql.VALUES("FECHABAJA", "#{fechabaja,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIddireccion() != null) {
            sql.VALUES("IDDIRECCION", "#{iddireccion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdpersona() != null) {
            sql.VALUES("IDPERSONA", "#{idpersona,jdbcType=DECIMAL}");
        }
        
        if (record.getDescripcion() != null) {
            sql.VALUES("DESCRIPCION", "#{descripcion,jdbcType=VARCHAR}");
        }
        
        if (record.getAntigua() != null) {
            sql.VALUES("ANTIGUA", "#{antigua,jdbcType=VARCHAR}");
        }
        
        if (record.getDescripcionRemitente() != null) {
            sql.VALUES("DESCRIPCION_REMITENTE", "#{descripcionRemitente,jdbcType=VARCHAR}");
        }
        
        if (record.getIdclasecomunicacion() != null) {
            sql.VALUES("IDCLASECOMUNICACION", "#{idclasecomunicacion,jdbcType=DECIMAL}");
        }
        
        if (record.getAsunto() != null) {
            sql.VALUES("ASUNTO", "#{asunto,jdbcType=CLOB}");
        }
        
        if (record.getCuerpo() != null) {
            sql.VALUES("CUERPO", "#{cuerpo,jdbcType=CLOB}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLASENVIOS
     *
     * @mbg.generated Mon Jan 20 14:13:09 CET 2020
     */
    public String selectByExampleWithBLOBs(EnvPlantillasenviosExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("IDINSTITUCION");
        } else {
            sql.SELECT("IDINSTITUCION");
        }
        sql.SELECT("IDTIPOENVIOS");
        sql.SELECT("IDPLANTILLAENVIOS");
        sql.SELECT("NOMBRE");
        sql.SELECT("FECHAMODIFICACION");
        sql.SELECT("USUMODIFICACION");
        sql.SELECT("ACUSERECIBO");
        sql.SELECT("FECHABAJA");
        sql.SELECT("IDDIRECCION");
        sql.SELECT("IDPERSONA");
        sql.SELECT("DESCRIPCION");
        sql.SELECT("ANTIGUA");
        sql.SELECT("DESCRIPCION_REMITENTE");
        sql.SELECT("IDCLASECOMUNICACION");
        sql.SELECT("ASUNTO");
        sql.SELECT("CUERPO");
        sql.FROM("ENV_PLANTILLASENVIOS");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLASENVIOS
     *
     * @mbg.generated Mon Jan 20 14:13:09 CET 2020
     */
    public String selectByExample(EnvPlantillasenviosExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("IDINSTITUCION");
        } else {
            sql.SELECT("IDINSTITUCION");
        }
        sql.SELECT("IDTIPOENVIOS");
        sql.SELECT("IDPLANTILLAENVIOS");
        sql.SELECT("NOMBRE");
        sql.SELECT("FECHAMODIFICACION");
        sql.SELECT("USUMODIFICACION");
        sql.SELECT("ACUSERECIBO");
        sql.SELECT("FECHABAJA");
        sql.SELECT("IDDIRECCION");
        sql.SELECT("IDPERSONA");
        sql.SELECT("DESCRIPCION");
        sql.SELECT("ANTIGUA");
        sql.SELECT("DESCRIPCION_REMITENTE");
        sql.SELECT("IDCLASECOMUNICACION");
        sql.FROM("ENV_PLANTILLASENVIOS");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLASENVIOS
     *
     * @mbg.generated Mon Jan 20 14:13:09 CET 2020
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        EnvPlantillasenviosWithBLOBs record = (EnvPlantillasenviosWithBLOBs) parameter.get("record");
        EnvPlantillasenviosExample example = (EnvPlantillasenviosExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("ENV_PLANTILLASENVIOS");
        
        if (record.getIdinstitucion() != null) {
            sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
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
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getAcuserecibo() != null) {
            sql.SET("ACUSERECIBO = #{record.acuserecibo,jdbcType=VARCHAR}");
        }
        
        if (record.getFechabaja() != null) {
            sql.SET("FECHABAJA = #{record.fechabaja,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIddireccion() != null) {
            sql.SET("IDDIRECCION = #{record.iddireccion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdpersona() != null) {
            sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
        }
        
        if (record.getDescripcion() != null) {
            sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
        }
        
        if (record.getAntigua() != null) {
            sql.SET("ANTIGUA = #{record.antigua,jdbcType=VARCHAR}");
        }
        
        if (record.getDescripcionRemitente() != null) {
            sql.SET("DESCRIPCION_REMITENTE = #{record.descripcionRemitente,jdbcType=VARCHAR}");
        }
        
        if (record.getIdclasecomunicacion() != null) {
            sql.SET("IDCLASECOMUNICACION = #{record.idclasecomunicacion,jdbcType=DECIMAL}");
        }
        
        if (record.getAsunto() != null) {
            sql.SET("ASUNTO = #{record.asunto,jdbcType=CLOB}");
        }
        
        if (record.getCuerpo() != null) {
            sql.SET("CUERPO = #{record.cuerpo,jdbcType=CLOB}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLASENVIOS
     *
     * @mbg.generated Mon Jan 20 14:13:09 CET 2020
     */
    public String updateByExampleWithBLOBs(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("ENV_PLANTILLASENVIOS");
        
        sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        sql.SET("IDTIPOENVIOS = #{record.idtipoenvios,jdbcType=DECIMAL}");
        sql.SET("IDPLANTILLAENVIOS = #{record.idplantillaenvios,jdbcType=DECIMAL}");
        sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
        sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        sql.SET("ACUSERECIBO = #{record.acuserecibo,jdbcType=VARCHAR}");
        sql.SET("FECHABAJA = #{record.fechabaja,jdbcType=TIMESTAMP}");
        sql.SET("IDDIRECCION = #{record.iddireccion,jdbcType=DECIMAL}");
        sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
        sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
        sql.SET("ANTIGUA = #{record.antigua,jdbcType=VARCHAR}");
        sql.SET("DESCRIPCION_REMITENTE = #{record.descripcionRemitente,jdbcType=VARCHAR}");
        sql.SET("IDCLASECOMUNICACION = #{record.idclasecomunicacion,jdbcType=DECIMAL}");
        sql.SET("ASUNTO = #{record.asunto,jdbcType=CLOB}");
        sql.SET("CUERPO = #{record.cuerpo,jdbcType=CLOB}");
        
        EnvPlantillasenviosExample example = (EnvPlantillasenviosExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLASENVIOS
     *
     * @mbg.generated Mon Jan 20 14:13:09 CET 2020
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("ENV_PLANTILLASENVIOS");
        
        sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        sql.SET("IDTIPOENVIOS = #{record.idtipoenvios,jdbcType=DECIMAL}");
        sql.SET("IDPLANTILLAENVIOS = #{record.idplantillaenvios,jdbcType=DECIMAL}");
        sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
        sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        sql.SET("ACUSERECIBO = #{record.acuserecibo,jdbcType=VARCHAR}");
        sql.SET("FECHABAJA = #{record.fechabaja,jdbcType=TIMESTAMP}");
        sql.SET("IDDIRECCION = #{record.iddireccion,jdbcType=DECIMAL}");
        sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
        sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
        sql.SET("ANTIGUA = #{record.antigua,jdbcType=VARCHAR}");
        sql.SET("DESCRIPCION_REMITENTE = #{record.descripcionRemitente,jdbcType=VARCHAR}");
        sql.SET("IDCLASECOMUNICACION = #{record.idclasecomunicacion,jdbcType=DECIMAL}");
        
        EnvPlantillasenviosExample example = (EnvPlantillasenviosExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLASENVIOS
     *
     * @mbg.generated Mon Jan 20 14:13:09 CET 2020
     */
    public String updateByPrimaryKeySelective(EnvPlantillasenviosWithBLOBs record) {
        SQL sql = new SQL();
        sql.UPDATE("ENV_PLANTILLASENVIOS");
        
        if (record.getNombre() != null) {
            sql.SET("NOMBRE = #{nombre,jdbcType=VARCHAR}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getAcuserecibo() != null) {
            sql.SET("ACUSERECIBO = #{acuserecibo,jdbcType=VARCHAR}");
        }
        
        if (record.getFechabaja() != null) {
            sql.SET("FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIddireccion() != null) {
            sql.SET("IDDIRECCION = #{iddireccion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdpersona() != null) {
            sql.SET("IDPERSONA = #{idpersona,jdbcType=DECIMAL}");
        }
        
        if (record.getDescripcion() != null) {
            sql.SET("DESCRIPCION = #{descripcion,jdbcType=VARCHAR}");
        }
        
        if (record.getAntigua() != null) {
            sql.SET("ANTIGUA = #{antigua,jdbcType=VARCHAR}");
        }
        
        if (record.getDescripcionRemitente() != null) {
            sql.SET("DESCRIPCION_REMITENTE = #{descripcionRemitente,jdbcType=VARCHAR}");
        }
        
        if (record.getIdclasecomunicacion() != null) {
            sql.SET("IDCLASECOMUNICACION = #{idclasecomunicacion,jdbcType=DECIMAL}");
        }
        
        if (record.getAsunto() != null) {
            sql.SET("ASUNTO = #{asunto,jdbcType=CLOB}");
        }
        
        if (record.getCuerpo() != null) {
            sql.SET("CUERPO = #{cuerpo,jdbcType=CLOB}");
        }
        
        sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
        sql.WHERE("IDTIPOENVIOS = #{idtipoenvios,jdbcType=DECIMAL}");
        sql.WHERE("IDPLANTILLAENVIOS = #{idplantillaenvios,jdbcType=DECIMAL}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_PLANTILLASENVIOS
     *
     * @mbg.generated Mon Jan 20 14:13:09 CET 2020
     */
    protected void applyWhere(SQL sql, EnvPlantillasenviosExample example, boolean includeExamplePhrase) {
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