package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.GenTareaspendientes;
import org.itcgae.siga.db.entities.GenTareaspendientesExample.Criteria;
import org.itcgae.siga.db.entities.GenTareaspendientesExample.Criterion;
import org.itcgae.siga.db.entities.GenTareaspendientesExample;

public class GenTareaspendientesSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_TAREASPENDIENTES
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String countByExample(GenTareaspendientesExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("GEN_TAREASPENDIENTES");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_TAREASPENDIENTES
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String deleteByExample(GenTareaspendientesExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("GEN_TAREASPENDIENTES");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_TAREASPENDIENTES
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String insertSelective(GenTareaspendientes record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("GEN_TAREASPENDIENTES");
        
        if (record.getGenTareaspendientesId() != null) {
            sql.VALUES("GEN_TAREASPENDIENTES_ID", "#{genTareaspendientesId,jdbcType=DECIMAL}");
        }
        
        if (record.getIdtareapendiente() != null) {
            sql.VALUES("IDTAREAPENDIENTE", "#{idtareapendiente,jdbcType=DECIMAL}");
        }
        
        if (record.getCenInstitucionId() != null) {
            sql.VALUES("CEN_INSTITUCION_ID", "#{cenInstitucionId,jdbcType=DECIMAL}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getEstado() != null) {
            sql.VALUES("ESTADO", "#{estado,jdbcType=VARCHAR}");
        }
        
        if (record.getIdproceso() != null) {
            sql.VALUES("IDPROCESO", "#{idproceso,jdbcType=VARCHAR}");
        }
        
        if (record.getIdtarea() != null) {
            sql.VALUES("IDTAREA", "#{idtarea,jdbcType=VARCHAR}");
        }
        
        if (record.getIdusuario() != null) {
            sql.VALUES("IDUSUARIO", "#{idusuario,jdbcType=DECIMAL}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_TAREASPENDIENTES
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String selectByExample(GenTareaspendientesExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("GEN_TAREASPENDIENTES_ID");
        } else {
            sql.SELECT("GEN_TAREASPENDIENTES_ID");
        }
        sql.SELECT("IDTAREAPENDIENTE");
        sql.SELECT("CEN_INSTITUCION_ID");
        sql.SELECT("FECHAMODIFICACION");
        sql.SELECT("USUMODIFICACION");
        sql.SELECT("ESTADO");
        sql.SELECT("IDPROCESO");
        sql.SELECT("IDTAREA");
        sql.SELECT("IDUSUARIO");
        sql.FROM("GEN_TAREASPENDIENTES");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_TAREASPENDIENTES
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        GenTareaspendientes record = (GenTareaspendientes) parameter.get("record");
        GenTareaspendientesExample example = (GenTareaspendientesExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("GEN_TAREASPENDIENTES");
        
        if (record.getGenTareaspendientesId() != null) {
            sql.SET("GEN_TAREASPENDIENTES_ID = #{record.genTareaspendientesId,jdbcType=DECIMAL}");
        }
        
        if (record.getIdtareapendiente() != null) {
            sql.SET("IDTAREAPENDIENTE = #{record.idtareapendiente,jdbcType=DECIMAL}");
        }
        
        if (record.getCenInstitucionId() != null) {
            sql.SET("CEN_INSTITUCION_ID = #{record.cenInstitucionId,jdbcType=DECIMAL}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getEstado() != null) {
            sql.SET("ESTADO = #{record.estado,jdbcType=VARCHAR}");
        }
        
        if (record.getIdproceso() != null) {
            sql.SET("IDPROCESO = #{record.idproceso,jdbcType=VARCHAR}");
        }
        
        if (record.getIdtarea() != null) {
            sql.SET("IDTAREA = #{record.idtarea,jdbcType=VARCHAR}");
        }
        
        if (record.getIdusuario() != null) {
            sql.SET("IDUSUARIO = #{record.idusuario,jdbcType=DECIMAL}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_TAREASPENDIENTES
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("GEN_TAREASPENDIENTES");
        
        sql.SET("GEN_TAREASPENDIENTES_ID = #{record.genTareaspendientesId,jdbcType=DECIMAL}");
        sql.SET("IDTAREAPENDIENTE = #{record.idtareapendiente,jdbcType=DECIMAL}");
        sql.SET("CEN_INSTITUCION_ID = #{record.cenInstitucionId,jdbcType=DECIMAL}");
        sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        sql.SET("ESTADO = #{record.estado,jdbcType=VARCHAR}");
        sql.SET("IDPROCESO = #{record.idproceso,jdbcType=VARCHAR}");
        sql.SET("IDTAREA = #{record.idtarea,jdbcType=VARCHAR}");
        sql.SET("IDUSUARIO = #{record.idusuario,jdbcType=DECIMAL}");
        
        GenTareaspendientesExample example = (GenTareaspendientesExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_TAREASPENDIENTES
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String updateByPrimaryKeySelective(GenTareaspendientes record) {
        SQL sql = new SQL();
        sql.UPDATE("GEN_TAREASPENDIENTES");
        
        if (record.getIdtareapendiente() != null) {
            sql.SET("IDTAREAPENDIENTE = #{idtareapendiente,jdbcType=DECIMAL}");
        }
        
        if (record.getCenInstitucionId() != null) {
            sql.SET("CEN_INSTITUCION_ID = #{cenInstitucionId,jdbcType=DECIMAL}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getEstado() != null) {
            sql.SET("ESTADO = #{estado,jdbcType=VARCHAR}");
        }
        
        if (record.getIdproceso() != null) {
            sql.SET("IDPROCESO = #{idproceso,jdbcType=VARCHAR}");
        }
        
        if (record.getIdtarea() != null) {
            sql.SET("IDTAREA = #{idtarea,jdbcType=VARCHAR}");
        }
        
        if (record.getIdusuario() != null) {
            sql.SET("IDUSUARIO = #{idusuario,jdbcType=DECIMAL}");
        }
        
        sql.WHERE("GEN_TAREASPENDIENTES_ID = #{genTareaspendientesId,jdbcType=DECIMAL}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_TAREASPENDIENTES
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    protected void applyWhere(SQL sql, GenTareaspendientesExample example, boolean includeExamplePhrase) {
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