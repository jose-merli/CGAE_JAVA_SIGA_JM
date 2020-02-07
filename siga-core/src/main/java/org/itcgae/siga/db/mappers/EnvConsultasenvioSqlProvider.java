package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.EnvConsultasenvio;
import org.itcgae.siga.db.entities.EnvConsultasenvioExample.Criteria;
import org.itcgae.siga.db.entities.EnvConsultasenvioExample.Criterion;
import org.itcgae.siga.db.entities.EnvConsultasenvioExample;

public class EnvConsultasenvioSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Fri Feb 07 14:16:45 CET 2020
     */
    public String countByExample(EnvConsultasenvioExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("ENV_CONSULTASENVIO");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Fri Feb 07 14:16:45 CET 2020
     */
    public String deleteByExample(EnvConsultasenvioExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("ENV_CONSULTASENVIO");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Fri Feb 07 14:16:45 CET 2020
     */
    public String insertSelective(EnvConsultasenvio record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("ENV_CONSULTASENVIO");
        
        sql.VALUES("IDCONSULTAENVIO", "#{idconsultaenvio,jdbcType=DECIMAL}");
        
        if (record.getIdenvio() != null) {
            sql.VALUES("IDENVIO", "#{idenvio,jdbcType=DECIMAL}");
        }
        
        if (record.getIdobjetivo() != null) {
            sql.VALUES("IDOBJETIVO", "#{idobjetivo,jdbcType=DECIMAL}");
        }
        
        if (record.getIdinstitucion() != null) {
            sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdconsulta() != null) {
            sql.VALUES("IDCONSULTA", "#{idconsulta,jdbcType=DECIMAL}");
        }
        
        if (record.getFechabaja() != null) {
            sql.VALUES("FECHABAJA", "#{fechabaja,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdplantilladocumento() != null) {
            sql.VALUES("IDPLANTILLADOCUMENTO", "#{idplantilladocumento,jdbcType=DECIMAL}");
        }
        
        if (record.getIdmodelocomunicacion() != null) {
            sql.VALUES("IDMODELOCOMUNICACION", "#{idmodelocomunicacion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdinforme() != null) {
            sql.VALUES("IDINFORME", "#{idinforme,jdbcType=DECIMAL}");
        }
        
        if (record.getSufijo() != null) {
            sql.VALUES("SUFIJO", "#{sufijo,jdbcType=VARCHAR}");
        }
        
        if (record.getConsulta() != null) {
            sql.VALUES("CONSULTA", "#{consulta,jdbcType=CLOB}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Fri Feb 07 14:16:45 CET 2020
     */
    public String selectByExampleWithBLOBs(EnvConsultasenvioExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("IDCONSULTAENVIO");
        } else {
            sql.SELECT("IDCONSULTAENVIO");
        }
        sql.SELECT("IDENVIO");
        sql.SELECT("IDOBJETIVO");
        sql.SELECT("IDINSTITUCION");
        sql.SELECT("IDCONSULTA");
        sql.SELECT("FECHABAJA");
        sql.SELECT("FECHAMODIFICACION");
        sql.SELECT("USUMODIFICACION");
        sql.SELECT("IDPLANTILLADOCUMENTO");
        sql.SELECT("IDMODELOCOMUNICACION");
        sql.SELECT("IDINFORME");
        sql.SELECT("SUFIJO");
        sql.SELECT("CONSULTA");
        sql.FROM("ENV_CONSULTASENVIO");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Fri Feb 07 14:16:45 CET 2020
     */
    public String selectByExample(EnvConsultasenvioExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("IDCONSULTAENVIO");
        } else {
            sql.SELECT("IDCONSULTAENVIO");
        }
        sql.SELECT("IDENVIO");
        sql.SELECT("IDOBJETIVO");
        sql.SELECT("IDINSTITUCION");
        sql.SELECT("IDCONSULTA");
        sql.SELECT("FECHABAJA");
        sql.SELECT("FECHAMODIFICACION");
        sql.SELECT("USUMODIFICACION");
        sql.SELECT("IDPLANTILLADOCUMENTO");
        sql.SELECT("IDMODELOCOMUNICACION");
        sql.SELECT("IDINFORME");
        sql.SELECT("SUFIJO");
        sql.FROM("ENV_CONSULTASENVIO");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Fri Feb 07 14:16:45 CET 2020
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        EnvConsultasenvio record = (EnvConsultasenvio) parameter.get("record");
        EnvConsultasenvioExample example = (EnvConsultasenvioExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("ENV_CONSULTASENVIO");
        
        if (record.getIdconsultaenvio() != null) {
            sql.SET("IDCONSULTAENVIO = #{record.idconsultaenvio,jdbcType=DECIMAL}");
        }
        
        if (record.getIdenvio() != null) {
            sql.SET("IDENVIO = #{record.idenvio,jdbcType=DECIMAL}");
        }
        
        if (record.getIdobjetivo() != null) {
            sql.SET("IDOBJETIVO = #{record.idobjetivo,jdbcType=DECIMAL}");
        }
        
        if (record.getIdinstitucion() != null) {
            sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdconsulta() != null) {
            sql.SET("IDCONSULTA = #{record.idconsulta,jdbcType=DECIMAL}");
        }
        
        if (record.getFechabaja() != null) {
            sql.SET("FECHABAJA = #{record.fechabaja,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdplantilladocumento() != null) {
            sql.SET("IDPLANTILLADOCUMENTO = #{record.idplantilladocumento,jdbcType=DECIMAL}");
        }
        
        if (record.getIdmodelocomunicacion() != null) {
            sql.SET("IDMODELOCOMUNICACION = #{record.idmodelocomunicacion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdinforme() != null) {
            sql.SET("IDINFORME = #{record.idinforme,jdbcType=DECIMAL}");
        }
        
        if (record.getSufijo() != null) {
            sql.SET("SUFIJO = #{record.sufijo,jdbcType=VARCHAR}");
        }
        
        if (record.getConsulta() != null) {
            sql.SET("CONSULTA = #{record.consulta,jdbcType=CLOB}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Fri Feb 07 14:16:45 CET 2020
     */
    public String updateByExampleWithBLOBs(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("ENV_CONSULTASENVIO");
        
        sql.SET("IDCONSULTAENVIO = #{record.idconsultaenvio,jdbcType=DECIMAL}");
        sql.SET("IDENVIO = #{record.idenvio,jdbcType=DECIMAL}");
        sql.SET("IDOBJETIVO = #{record.idobjetivo,jdbcType=DECIMAL}");
        sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        sql.SET("IDCONSULTA = #{record.idconsulta,jdbcType=DECIMAL}");
        sql.SET("FECHABAJA = #{record.fechabaja,jdbcType=TIMESTAMP}");
        sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        sql.SET("IDPLANTILLADOCUMENTO = #{record.idplantilladocumento,jdbcType=DECIMAL}");
        sql.SET("IDMODELOCOMUNICACION = #{record.idmodelocomunicacion,jdbcType=DECIMAL}");
        sql.SET("IDINFORME = #{record.idinforme,jdbcType=DECIMAL}");
        sql.SET("SUFIJO = #{record.sufijo,jdbcType=VARCHAR}");
        sql.SET("CONSULTA = #{record.consulta,jdbcType=CLOB}");
        
        EnvConsultasenvioExample example = (EnvConsultasenvioExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Fri Feb 07 14:16:45 CET 2020
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("ENV_CONSULTASENVIO");
        
        sql.SET("IDCONSULTAENVIO = #{record.idconsultaenvio,jdbcType=DECIMAL}");
        sql.SET("IDENVIO = #{record.idenvio,jdbcType=DECIMAL}");
        sql.SET("IDOBJETIVO = #{record.idobjetivo,jdbcType=DECIMAL}");
        sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        sql.SET("IDCONSULTA = #{record.idconsulta,jdbcType=DECIMAL}");
        sql.SET("FECHABAJA = #{record.fechabaja,jdbcType=TIMESTAMP}");
        sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        sql.SET("IDPLANTILLADOCUMENTO = #{record.idplantilladocumento,jdbcType=DECIMAL}");
        sql.SET("IDMODELOCOMUNICACION = #{record.idmodelocomunicacion,jdbcType=DECIMAL}");
        sql.SET("IDINFORME = #{record.idinforme,jdbcType=DECIMAL}");
        sql.SET("SUFIJO = #{record.sufijo,jdbcType=VARCHAR}");
        
        EnvConsultasenvioExample example = (EnvConsultasenvioExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Fri Feb 07 14:16:45 CET 2020
     */
    public String updateByPrimaryKeySelective(EnvConsultasenvio record) {
        SQL sql = new SQL();
        sql.UPDATE("ENV_CONSULTASENVIO");
        
        if (record.getIdenvio() != null) {
            sql.SET("IDENVIO = #{idenvio,jdbcType=DECIMAL}");
        }
        
        if (record.getIdobjetivo() != null) {
            sql.SET("IDOBJETIVO = #{idobjetivo,jdbcType=DECIMAL}");
        }
        
        if (record.getIdinstitucion() != null) {
            sql.SET("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdconsulta() != null) {
            sql.SET("IDCONSULTA = #{idconsulta,jdbcType=DECIMAL}");
        }
        
        if (record.getFechabaja() != null) {
            sql.SET("FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdplantilladocumento() != null) {
            sql.SET("IDPLANTILLADOCUMENTO = #{idplantilladocumento,jdbcType=DECIMAL}");
        }
        
        if (record.getIdmodelocomunicacion() != null) {
            sql.SET("IDMODELOCOMUNICACION = #{idmodelocomunicacion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdinforme() != null) {
            sql.SET("IDINFORME = #{idinforme,jdbcType=DECIMAL}");
        }
        
        if (record.getSufijo() != null) {
            sql.SET("SUFIJO = #{sufijo,jdbcType=VARCHAR}");
        }
        
        if (record.getConsulta() != null) {
            sql.SET("CONSULTA = #{consulta,jdbcType=CLOB}");
        }
        
        sql.WHERE("IDCONSULTAENVIO = #{idconsultaenvio,jdbcType=DECIMAL}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Fri Feb 07 14:16:45 CET 2020
     */
    protected void applyWhere(SQL sql, EnvConsultasenvioExample example, boolean includeExamplePhrase) {
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