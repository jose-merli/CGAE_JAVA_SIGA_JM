package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.FcsCertificaciones;
import org.itcgae.siga.db.entities.FcsCertificacionesExample.Criteria;
import org.itcgae.siga.db.entities.FcsCertificacionesExample.Criterion;
import org.itcgae.siga.db.entities.FcsCertificacionesExample;

public class FcsCertificacionesSqlProvider {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FCS_CERTIFICACIONES
     *
     * @mbg.generated Mon Dec 13 17:55:56 CET 2021
     */
    public String countByExample(FcsCertificacionesExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("FCS_CERTIFICACIONES");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FCS_CERTIFICACIONES
     *
     * @mbg.generated Mon Dec 13 17:55:56 CET 2021
     */
    public String deleteByExample(FcsCertificacionesExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("FCS_CERTIFICACIONES");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FCS_CERTIFICACIONES
     *
     * @mbg.generated Mon Dec 13 17:55:56 CET 2021
     */
    public String insertSelective(FcsCertificaciones record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("FCS_CERTIFICACIONES");
        
        sql.VALUES("IDCERTIFICACION", "#{idcertificacion,jdbcType=DECIMAL}");
        
        if (record.getIdinstitucion() != null) {
            sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdestadocertificacion() != null) {
            sql.VALUES("IDESTADOCERTIFICACION", "#{idestadocertificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getFechadesde() != null) {
            sql.VALUES("FECHADESDE", "#{fechadesde,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechahasta() != null) {
            sql.VALUES("FECHAHASTA", "#{fechahasta,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIdhitogeneral() != null) {
            sql.VALUES("IDHITOGENERAL", "#{idhitogeneral,jdbcType=DECIMAL}");
        }
        
        if (record.getNombre() != null) {
            sql.VALUES("NOMBRE", "#{nombre,jdbcType=VARCHAR}");
        }
        
        if (record.getIdgrupofacturacion() != null) {
            sql.VALUES("IDGRUPOFACTURACION", "#{idgrupofacturacion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdpartidapresupuestaria() != null) {
            sql.VALUES("IDPARTIDAPRESUPUESTARIA", "#{idpartidapresupuestaria,jdbcType=DECIMAL}");
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
     * This method corresponds to the database table USCGAE_INT.FCS_CERTIFICACIONES
     *
     * @mbg.generated Mon Dec 13 17:55:56 CET 2021
     */
    public String selectByExample(FcsCertificacionesExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("IDCERTIFICACION");
        } else {
            sql.SELECT("IDCERTIFICACION");
        }
        sql.SELECT("IDINSTITUCION");
        sql.SELECT("IDESTADOCERTIFICACION");
        sql.SELECT("FECHADESDE");
        sql.SELECT("FECHAHASTA");
        sql.SELECT("IDHITOGENERAL");
        sql.SELECT("NOMBRE");
        sql.SELECT("IDGRUPOFACTURACION");
        sql.SELECT("IDPARTIDAPRESUPUESTARIA");
        sql.SELECT("FECHAMODIFICACION");
        sql.SELECT("USUMODIFICACION");
        sql.FROM("FCS_CERTIFICACIONES");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FCS_CERTIFICACIONES
     *
     * @mbg.generated Mon Dec 13 17:55:56 CET 2021
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        FcsCertificaciones record = (FcsCertificaciones) parameter.get("record");
        FcsCertificacionesExample example = (FcsCertificacionesExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("FCS_CERTIFICACIONES");
        
        if (record.getIdcertificacion() != null) {
            sql.SET("IDCERTIFICACION = #{record.idcertificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdinstitucion() != null) {
            sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdestadocertificacion() != null) {
            sql.SET("IDESTADOCERTIFICACION = #{record.idestadocertificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getFechadesde() != null) {
            sql.SET("FECHADESDE = #{record.fechadesde,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechahasta() != null) {
            sql.SET("FECHAHASTA = #{record.fechahasta,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIdhitogeneral() != null) {
            sql.SET("IDHITOGENERAL = #{record.idhitogeneral,jdbcType=DECIMAL}");
        }
        
        if (record.getNombre() != null) {
            sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
        }
        
        if (record.getIdgrupofacturacion() != null) {
            sql.SET("IDGRUPOFACTURACION = #{record.idgrupofacturacion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdpartidapresupuestaria() != null) {
            sql.SET("IDPARTIDAPRESUPUESTARIA = #{record.idpartidapresupuestaria,jdbcType=DECIMAL}");
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
     * This method corresponds to the database table USCGAE_INT.FCS_CERTIFICACIONES
     *
     * @mbg.generated Mon Dec 13 17:55:56 CET 2021
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("FCS_CERTIFICACIONES");
        
        sql.SET("IDCERTIFICACION = #{record.idcertificacion,jdbcType=DECIMAL}");
        sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        sql.SET("IDESTADOCERTIFICACION = #{record.idestadocertificacion,jdbcType=DECIMAL}");
        sql.SET("FECHADESDE = #{record.fechadesde,jdbcType=TIMESTAMP}");
        sql.SET("FECHAHASTA = #{record.fechahasta,jdbcType=TIMESTAMP}");
        sql.SET("IDHITOGENERAL = #{record.idhitogeneral,jdbcType=DECIMAL}");
        sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
        sql.SET("IDGRUPOFACTURACION = #{record.idgrupofacturacion,jdbcType=DECIMAL}");
        sql.SET("IDPARTIDAPRESUPUESTARIA = #{record.idpartidapresupuestaria,jdbcType=DECIMAL}");
        sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        
        FcsCertificacionesExample example = (FcsCertificacionesExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FCS_CERTIFICACIONES
     *
     * @mbg.generated Mon Dec 13 17:55:56 CET 2021
     */
    public String updateByPrimaryKeySelective(FcsCertificaciones record) {
        SQL sql = new SQL();
        sql.UPDATE("FCS_CERTIFICACIONES");
        
        if (record.getIdestadocertificacion() != null) {
            sql.SET("IDESTADOCERTIFICACION = #{idestadocertificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getFechadesde() != null) {
            sql.SET("FECHADESDE = #{fechadesde,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechahasta() != null) {
            sql.SET("FECHAHASTA = #{fechahasta,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIdhitogeneral() != null) {
            sql.SET("IDHITOGENERAL = #{idhitogeneral,jdbcType=DECIMAL}");
        }
        
        if (record.getNombre() != null) {
            sql.SET("NOMBRE = #{nombre,jdbcType=VARCHAR}");
        }
        
        if (record.getIdgrupofacturacion() != null) {
            sql.SET("IDGRUPOFACTURACION = #{idgrupofacturacion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdpartidapresupuestaria() != null) {
            sql.SET("IDPARTIDAPRESUPUESTARIA = #{idpartidapresupuestaria,jdbcType=DECIMAL}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
        }
        
        sql.WHERE("IDCERTIFICACION = #{idcertificacion,jdbcType=DECIMAL}");
        sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.FCS_CERTIFICACIONES
     *
     * @mbg.generated Mon Dec 13 17:55:56 CET 2021
     */
    protected void applyWhere(SQL sql, FcsCertificacionesExample example, boolean includeExamplePhrase) {
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