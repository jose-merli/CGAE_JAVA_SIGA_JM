package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.ScsGuardiascolegiado;
import org.itcgae.siga.db.entities.ScsGuardiascolegiadoExample.Criteria;
import org.itcgae.siga.db.entities.ScsGuardiascolegiadoExample.Criterion;
import org.itcgae.siga.db.entities.ScsGuardiascolegiadoExample;

public class ScsGuardiascolegiadoSqlProvider {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_GUARDIASCOLEGIADO
     *
     * @mbg.generated Thu Jan 30 10:17:22 CET 2020
     */
    public String countByExample(ScsGuardiascolegiadoExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("SCS_GUARDIASCOLEGIADO");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_GUARDIASCOLEGIADO
     *
     * @mbg.generated Thu Jan 30 10:17:22 CET 2020
     */
    public String deleteByExample(ScsGuardiascolegiadoExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("SCS_GUARDIASCOLEGIADO");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_GUARDIASCOLEGIADO
     *
     * @mbg.generated Thu Jan 30 10:17:22 CET 2020
     */
    public String insertSelective(ScsGuardiascolegiado record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("SCS_GUARDIASCOLEGIADO");
        
        if (record.getIdinstitucion() != null) {
            sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdturno() != null) {
            sql.VALUES("IDTURNO", "#{idturno,jdbcType=DECIMAL}");
        }
        
        if (record.getIdguardia() != null) {
            sql.VALUES("IDGUARDIA", "#{idguardia,jdbcType=DECIMAL}");
        }
        
        if (record.getIdpersona() != null) {
            sql.VALUES("IDPERSONA", "#{idpersona,jdbcType=DECIMAL}");
        }
        
        if (record.getFechainicio() != null) {
            sql.VALUES("FECHAINICIO", "#{fechainicio,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechafin() != null) {
            sql.VALUES("FECHAFIN", "#{fechafin,jdbcType=TIMESTAMP}");
        }
        
        if (record.getDiasguardia() != null) {
            sql.VALUES("DIASGUARDIA", "#{diasguardia,jdbcType=DECIMAL}");
        }
        
        if (record.getDiasacobrar() != null) {
            sql.VALUES("DIASACOBRAR", "#{diasacobrar,jdbcType=DECIMAL}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getReserva() != null) {
            sql.VALUES("RESERVA", "#{reserva,jdbcType=VARCHAR}");
        }
        
        if (record.getObservaciones() != null) {
            sql.VALUES("OBSERVACIONES", "#{observaciones,jdbcType=VARCHAR}");
        }
        
        if (record.getFacturado() != null) {
            sql.VALUES("FACTURADO", "#{facturado,jdbcType=VARCHAR}");
        }
        
        if (record.getPagado() != null) {
            sql.VALUES("PAGADO", "#{pagado,jdbcType=VARCHAR}");
        }
        
        if (record.getIdfacturacion() != null) {
            sql.VALUES("IDFACTURACION", "#{idfacturacion,jdbcType=DECIMAL}");
        }
        
        if (record.getGuardiaSeleccionlaborables() != null) {
            sql.VALUES("GUARDIA_SELECCIONLABORABLES", "#{guardiaSeleccionlaborables,jdbcType=VARCHAR}");
        }
        
        if (record.getGuardiaSeleccionfestivos() != null) {
            sql.VALUES("GUARDIA_SELECCIONFESTIVOS", "#{guardiaSeleccionfestivos,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_GUARDIASCOLEGIADO
     *
     * @mbg.generated Thu Jan 30 10:17:22 CET 2020
     */
    public String selectByExample(ScsGuardiascolegiadoExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("IDINSTITUCION");
        } else {
            sql.SELECT("IDINSTITUCION");
        }
        sql.SELECT("IDTURNO");
        sql.SELECT("IDGUARDIA");
        sql.SELECT("IDPERSONA");
        sql.SELECT("FECHAINICIO");
        sql.SELECT("FECHAFIN");
        sql.SELECT("DIASGUARDIA");
        sql.SELECT("DIASACOBRAR");
        sql.SELECT("FECHAMODIFICACION");
        sql.SELECT("USUMODIFICACION");
        sql.SELECT("RESERVA");
        sql.SELECT("OBSERVACIONES");
        sql.SELECT("FACTURADO");
        sql.SELECT("PAGADO");
        sql.SELECT("IDFACTURACION");
        sql.SELECT("GUARDIA_SELECCIONLABORABLES");
        sql.SELECT("GUARDIA_SELECCIONFESTIVOS");
        sql.FROM("SCS_GUARDIASCOLEGIADO");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_GUARDIASCOLEGIADO
     *
     * @mbg.generated Thu Jan 30 10:17:22 CET 2020
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        ScsGuardiascolegiado record = (ScsGuardiascolegiado) parameter.get("record");
        ScsGuardiascolegiadoExample example = (ScsGuardiascolegiadoExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("SCS_GUARDIASCOLEGIADO");
        
        if (record.getIdinstitucion() != null) {
            sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdturno() != null) {
            sql.SET("IDTURNO = #{record.idturno,jdbcType=DECIMAL}");
        }
        
        if (record.getIdguardia() != null) {
            sql.SET("IDGUARDIA = #{record.idguardia,jdbcType=DECIMAL}");
        }
        
        if (record.getIdpersona() != null) {
            sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
        }
        
        if (record.getFechainicio() != null) {
            sql.SET("FECHAINICIO = #{record.fechainicio,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechafin() != null) {
            sql.SET("FECHAFIN = #{record.fechafin,jdbcType=TIMESTAMP}");
        }
        
        if (record.getDiasguardia() != null) {
            sql.SET("DIASGUARDIA = #{record.diasguardia,jdbcType=DECIMAL}");
        }
        
        if (record.getDiasacobrar() != null) {
            sql.SET("DIASACOBRAR = #{record.diasacobrar,jdbcType=DECIMAL}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getReserva() != null) {
            sql.SET("RESERVA = #{record.reserva,jdbcType=VARCHAR}");
        }
        
        if (record.getObservaciones() != null) {
            sql.SET("OBSERVACIONES = #{record.observaciones,jdbcType=VARCHAR}");
        }
        
        if (record.getFacturado() != null) {
            sql.SET("FACTURADO = #{record.facturado,jdbcType=VARCHAR}");
        }
        
        if (record.getPagado() != null) {
            sql.SET("PAGADO = #{record.pagado,jdbcType=VARCHAR}");
        }
        
        if (record.getIdfacturacion() != null) {
            sql.SET("IDFACTURACION = #{record.idfacturacion,jdbcType=DECIMAL}");
        }
        
        if (record.getGuardiaSeleccionlaborables() != null) {
            sql.SET("GUARDIA_SELECCIONLABORABLES = #{record.guardiaSeleccionlaborables,jdbcType=VARCHAR}");
        }
        
        if (record.getGuardiaSeleccionfestivos() != null) {
            sql.SET("GUARDIA_SELECCIONFESTIVOS = #{record.guardiaSeleccionfestivos,jdbcType=VARCHAR}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_GUARDIASCOLEGIADO
     *
     * @mbg.generated Thu Jan 30 10:17:22 CET 2020
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("SCS_GUARDIASCOLEGIADO");
        
        sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        sql.SET("IDTURNO = #{record.idturno,jdbcType=DECIMAL}");
        sql.SET("IDGUARDIA = #{record.idguardia,jdbcType=DECIMAL}");
        sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
        sql.SET("FECHAINICIO = #{record.fechainicio,jdbcType=TIMESTAMP}");
        sql.SET("FECHAFIN = #{record.fechafin,jdbcType=TIMESTAMP}");
        sql.SET("DIASGUARDIA = #{record.diasguardia,jdbcType=DECIMAL}");
        sql.SET("DIASACOBRAR = #{record.diasacobrar,jdbcType=DECIMAL}");
        sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        sql.SET("RESERVA = #{record.reserva,jdbcType=VARCHAR}");
        sql.SET("OBSERVACIONES = #{record.observaciones,jdbcType=VARCHAR}");
        sql.SET("FACTURADO = #{record.facturado,jdbcType=VARCHAR}");
        sql.SET("PAGADO = #{record.pagado,jdbcType=VARCHAR}");
        sql.SET("IDFACTURACION = #{record.idfacturacion,jdbcType=DECIMAL}");
        sql.SET("GUARDIA_SELECCIONLABORABLES = #{record.guardiaSeleccionlaborables,jdbcType=VARCHAR}");
        sql.SET("GUARDIA_SELECCIONFESTIVOS = #{record.guardiaSeleccionfestivos,jdbcType=VARCHAR}");
        
        ScsGuardiascolegiadoExample example = (ScsGuardiascolegiadoExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_GUARDIASCOLEGIADO
     *
     * @mbg.generated Thu Jan 30 10:17:22 CET 2020
     */
    public String updateByPrimaryKeySelective(ScsGuardiascolegiado record) {
        SQL sql = new SQL();
        sql.UPDATE("SCS_GUARDIASCOLEGIADO");
        
        if (record.getDiasguardia() != null) {
            sql.SET("DIASGUARDIA = #{diasguardia,jdbcType=DECIMAL}");
        }
        
        if (record.getDiasacobrar() != null) {
            sql.SET("DIASACOBRAR = #{diasacobrar,jdbcType=DECIMAL}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getReserva() != null) {
            sql.SET("RESERVA = #{reserva,jdbcType=VARCHAR}");
        }
        
        if (record.getObservaciones() != null) {
            sql.SET("OBSERVACIONES = #{observaciones,jdbcType=VARCHAR}");
        }
        
        if (record.getFacturado() != null) {
            sql.SET("FACTURADO = #{facturado,jdbcType=VARCHAR}");
        }
        
        if (record.getPagado() != null) {
            sql.SET("PAGADO = #{pagado,jdbcType=VARCHAR}");
        }
        
        if (record.getIdfacturacion() != null) {
            sql.SET("IDFACTURACION = #{idfacturacion,jdbcType=DECIMAL}");
        }
        
        if (record.getGuardiaSeleccionlaborables() != null) {
            sql.SET("GUARDIA_SELECCIONLABORABLES = #{guardiaSeleccionlaborables,jdbcType=VARCHAR}");
        }
        
        if (record.getGuardiaSeleccionfestivos() != null) {
            sql.SET("GUARDIA_SELECCIONFESTIVOS = #{guardiaSeleccionfestivos,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
        sql.WHERE("IDTURNO = #{idturno,jdbcType=DECIMAL}");
        sql.WHERE("IDGUARDIA = #{idguardia,jdbcType=DECIMAL}");
        sql.WHERE("IDPERSONA = #{idpersona,jdbcType=DECIMAL}");
        sql.WHERE("FECHAINICIO = #{fechainicio,jdbcType=TIMESTAMP}");
        sql.WHERE("FECHAFIN = #{fechafin,jdbcType=TIMESTAMP}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_GUARDIASCOLEGIADO
     *
     * @mbg.generated Thu Jan 30 10:17:22 CET 2020
     */
    protected void applyWhere(SQL sql, ScsGuardiascolegiadoExample example, boolean includeExamplePhrase) {
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