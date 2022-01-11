package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.FacAbono;
import org.itcgae.siga.db.entities.FacAbonoExample.Criteria;
import org.itcgae.siga.db.entities.FacAbonoExample.Criterion;
import org.itcgae.siga.db.entities.FacAbonoExample;

public class FacAbonoSqlProvider {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_ABONO
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public String countByExample(FacAbonoExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("FAC_ABONO");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_ABONO
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public String deleteByExample(FacAbonoExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("FAC_ABONO");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_ABONO
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public String insertSelective(FacAbono record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("FAC_ABONO");
        
        if (record.getIdinstitucion() != null) {
            sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdabono() != null) {
            sql.VALUES("IDABONO", "#{idabono,jdbcType=DECIMAL}");
        }
        
        if (record.getMotivos() != null) {
            sql.VALUES("MOTIVOS", "#{motivos,jdbcType=VARCHAR}");
        }
        
        if (record.getFecha() != null) {
            sql.VALUES("FECHA", "#{fecha,jdbcType=TIMESTAMP}");
        }
        
        if (record.getContabilizada() != null) {
            sql.VALUES("CONTABILIZADA", "#{contabilizada,jdbcType=VARCHAR}");
        }
        
        if (record.getIdpersona() != null) {
            sql.VALUES("IDPERSONA", "#{idpersona,jdbcType=DECIMAL}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIdcuenta() != null) {
            sql.VALUES("IDCUENTA", "#{idcuenta,jdbcType=DECIMAL}");
        }
        
        if (record.getIdfactura() != null) {
            sql.VALUES("IDFACTURA", "#{idfactura,jdbcType=VARCHAR}");
        }
        
        if (record.getIdpagosjg() != null) {
            sql.VALUES("IDPAGOSJG", "#{idpagosjg,jdbcType=DECIMAL}");
        }
        
        if (record.getNumeroabono() != null) {
            sql.VALUES("NUMEROABONO", "#{numeroabono,jdbcType=VARCHAR}");
        }
        
        if (record.getObservaciones() != null) {
            sql.VALUES("OBSERVACIONES", "#{observaciones,jdbcType=VARCHAR}");
        }
        
        if (record.getEstado() != null) {
            sql.VALUES("ESTADO", "#{estado,jdbcType=DECIMAL}");
        }
        
        if (record.getImptotalneto() != null) {
            sql.VALUES("IMPTOTALNETO", "#{imptotalneto,jdbcType=DECIMAL}");
        }
        
        if (record.getImptotaliva() != null) {
            sql.VALUES("IMPTOTALIVA", "#{imptotaliva,jdbcType=DECIMAL}");
        }
        
        if (record.getImptotal() != null) {
            sql.VALUES("IMPTOTAL", "#{imptotal,jdbcType=DECIMAL}");
        }
        
        if (record.getImptotalabonadoefectivo() != null) {
            sql.VALUES("IMPTOTALABONADOEFECTIVO", "#{imptotalabonadoefectivo,jdbcType=DECIMAL}");
        }
        
        if (record.getImptotalabonadoporbanco() != null) {
            sql.VALUES("IMPTOTALABONADOPORBANCO", "#{imptotalabonadoporbanco,jdbcType=DECIMAL}");
        }
        
        if (record.getImptotalabonado() != null) {
            sql.VALUES("IMPTOTALABONADO", "#{imptotalabonado,jdbcType=DECIMAL}");
        }
        
        if (record.getImppendienteporabonar() != null) {
            sql.VALUES("IMPPENDIENTEPORABONAR", "#{imppendienteporabonar,jdbcType=DECIMAL}");
        }
        
        if (record.getIdpersonadeudor() != null) {
            sql.VALUES("IDPERSONADEUDOR", "#{idpersonadeudor,jdbcType=DECIMAL}");
        }
        
        if (record.getIdcuentadeudor() != null) {
            sql.VALUES("IDCUENTADEUDOR", "#{idcuentadeudor,jdbcType=DECIMAL}");
        }
        
        if (record.getIdperorigen() != null) {
            sql.VALUES("IDPERORIGEN", "#{idperorigen,jdbcType=DECIMAL}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_ABONO
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public String selectByExample(FacAbonoExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("IDINSTITUCION");
        } else {
            sql.SELECT("IDINSTITUCION");
        }
        sql.SELECT("IDABONO");
        sql.SELECT("MOTIVOS");
        sql.SELECT("FECHA");
        sql.SELECT("CONTABILIZADA");
        sql.SELECT("IDPERSONA");
        sql.SELECT("USUMODIFICACION");
        sql.SELECT("FECHAMODIFICACION");
        sql.SELECT("IDCUENTA");
        sql.SELECT("IDFACTURA");
        sql.SELECT("IDPAGOSJG");
        sql.SELECT("NUMEROABONO");
        sql.SELECT("OBSERVACIONES");
        sql.SELECT("ESTADO");
        sql.SELECT("IMPTOTALNETO");
        sql.SELECT("IMPTOTALIVA");
        sql.SELECT("IMPTOTAL");
        sql.SELECT("IMPTOTALABONADOEFECTIVO");
        sql.SELECT("IMPTOTALABONADOPORBANCO");
        sql.SELECT("IMPTOTALABONADO");
        sql.SELECT("IMPPENDIENTEPORABONAR");
        sql.SELECT("IDPERSONADEUDOR");
        sql.SELECT("IDCUENTADEUDOR");
        sql.SELECT("IDPERORIGEN");
        sql.FROM("FAC_ABONO");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_ABONO
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        FacAbono record = (FacAbono) parameter.get("record");
        FacAbonoExample example = (FacAbonoExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("FAC_ABONO");
        
        if (record.getIdinstitucion() != null) {
            sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdabono() != null) {
            sql.SET("IDABONO = #{record.idabono,jdbcType=DECIMAL}");
        }
        
        if (record.getMotivos() != null) {
            sql.SET("MOTIVOS = #{record.motivos,jdbcType=VARCHAR}");
        }
        
        if (record.getFecha() != null) {
            sql.SET("FECHA = #{record.fecha,jdbcType=TIMESTAMP}");
        }
        
        if (record.getContabilizada() != null) {
            sql.SET("CONTABILIZADA = #{record.contabilizada,jdbcType=VARCHAR}");
        }
        
        if (record.getIdpersona() != null) {
            sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIdcuenta() != null) {
            sql.SET("IDCUENTA = #{record.idcuenta,jdbcType=DECIMAL}");
        }
        
        if (record.getIdfactura() != null) {
            sql.SET("IDFACTURA = #{record.idfactura,jdbcType=VARCHAR}");
        }
        
        if (record.getIdpagosjg() != null) {
            sql.SET("IDPAGOSJG = #{record.idpagosjg,jdbcType=DECIMAL}");
        }
        
        if (record.getNumeroabono() != null) {
            sql.SET("NUMEROABONO = #{record.numeroabono,jdbcType=VARCHAR}");
        }
        
        if (record.getObservaciones() != null) {
            sql.SET("OBSERVACIONES = #{record.observaciones,jdbcType=VARCHAR}");
        }
        
        if (record.getEstado() != null) {
            sql.SET("ESTADO = #{record.estado,jdbcType=DECIMAL}");
        }
        
        if (record.getImptotalneto() != null) {
            sql.SET("IMPTOTALNETO = #{record.imptotalneto,jdbcType=DECIMAL}");
        }
        
        if (record.getImptotaliva() != null) {
            sql.SET("IMPTOTALIVA = #{record.imptotaliva,jdbcType=DECIMAL}");
        }
        
        if (record.getImptotal() != null) {
            sql.SET("IMPTOTAL = #{record.imptotal,jdbcType=DECIMAL}");
        }
        
        if (record.getImptotalabonadoefectivo() != null) {
            sql.SET("IMPTOTALABONADOEFECTIVO = #{record.imptotalabonadoefectivo,jdbcType=DECIMAL}");
        }
        
        if (record.getImptotalabonadoporbanco() != null) {
            sql.SET("IMPTOTALABONADOPORBANCO = #{record.imptotalabonadoporbanco,jdbcType=DECIMAL}");
        }
        
        if (record.getImptotalabonado() != null) {
            sql.SET("IMPTOTALABONADO = #{record.imptotalabonado,jdbcType=DECIMAL}");
        }
        
        if (record.getImppendienteporabonar() != null) {
            sql.SET("IMPPENDIENTEPORABONAR = #{record.imppendienteporabonar,jdbcType=DECIMAL}");
        }
        
        if (record.getIdpersonadeudor() != null) {
            sql.SET("IDPERSONADEUDOR = #{record.idpersonadeudor,jdbcType=DECIMAL}");
        }
        
        if (record.getIdcuentadeudor() != null) {
            sql.SET("IDCUENTADEUDOR = #{record.idcuentadeudor,jdbcType=DECIMAL}");
        }
        
        if (record.getIdperorigen() != null) {
            sql.SET("IDPERORIGEN = #{record.idperorigen,jdbcType=DECIMAL}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_ABONO
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("FAC_ABONO");
        
        sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        sql.SET("IDABONO = #{record.idabono,jdbcType=DECIMAL}");
        sql.SET("MOTIVOS = #{record.motivos,jdbcType=VARCHAR}");
        sql.SET("FECHA = #{record.fecha,jdbcType=TIMESTAMP}");
        sql.SET("CONTABILIZADA = #{record.contabilizada,jdbcType=VARCHAR}");
        sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
        sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        sql.SET("IDCUENTA = #{record.idcuenta,jdbcType=DECIMAL}");
        sql.SET("IDFACTURA = #{record.idfactura,jdbcType=VARCHAR}");
        sql.SET("IDPAGOSJG = #{record.idpagosjg,jdbcType=DECIMAL}");
        sql.SET("NUMEROABONO = #{record.numeroabono,jdbcType=VARCHAR}");
        sql.SET("OBSERVACIONES = #{record.observaciones,jdbcType=VARCHAR}");
        sql.SET("ESTADO = #{record.estado,jdbcType=DECIMAL}");
        sql.SET("IMPTOTALNETO = #{record.imptotalneto,jdbcType=DECIMAL}");
        sql.SET("IMPTOTALIVA = #{record.imptotaliva,jdbcType=DECIMAL}");
        sql.SET("IMPTOTAL = #{record.imptotal,jdbcType=DECIMAL}");
        sql.SET("IMPTOTALABONADOEFECTIVO = #{record.imptotalabonadoefectivo,jdbcType=DECIMAL}");
        sql.SET("IMPTOTALABONADOPORBANCO = #{record.imptotalabonadoporbanco,jdbcType=DECIMAL}");
        sql.SET("IMPTOTALABONADO = #{record.imptotalabonado,jdbcType=DECIMAL}");
        sql.SET("IMPPENDIENTEPORABONAR = #{record.imppendienteporabonar,jdbcType=DECIMAL}");
        sql.SET("IDPERSONADEUDOR = #{record.idpersonadeudor,jdbcType=DECIMAL}");
        sql.SET("IDCUENTADEUDOR = #{record.idcuentadeudor,jdbcType=DECIMAL}");
        sql.SET("IDPERORIGEN = #{record.idperorigen,jdbcType=DECIMAL}");
        
        FacAbonoExample example = (FacAbonoExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_ABONO
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public String updateByPrimaryKeySelective(FacAbono record) {
        SQL sql = new SQL();
        sql.UPDATE("FAC_ABONO");
        
        if (record.getMotivos() != null) {
            sql.SET("MOTIVOS = #{motivos,jdbcType=VARCHAR}");
        }
        
        if (record.getFecha() != null) {
            sql.SET("FECHA = #{fecha,jdbcType=TIMESTAMP}");
        }
        
        if (record.getContabilizada() != null) {
            sql.SET("CONTABILIZADA = #{contabilizada,jdbcType=VARCHAR}");
        }
        
        if (record.getIdpersona() != null) {
            sql.SET("IDPERSONA = #{idpersona,jdbcType=DECIMAL}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIdcuenta() != null) {
            sql.SET("IDCUENTA = #{idcuenta,jdbcType=DECIMAL}");
        }
        
        if (record.getIdfactura() != null) {
            sql.SET("IDFACTURA = #{idfactura,jdbcType=VARCHAR}");
        }
        
        if (record.getIdpagosjg() != null) {
            sql.SET("IDPAGOSJG = #{idpagosjg,jdbcType=DECIMAL}");
        }
        
        if (record.getNumeroabono() != null) {
            sql.SET("NUMEROABONO = #{numeroabono,jdbcType=VARCHAR}");
        }
        
        if (record.getObservaciones() != null) {
            sql.SET("OBSERVACIONES = #{observaciones,jdbcType=VARCHAR}");
        }
        
        if (record.getEstado() != null) {
            sql.SET("ESTADO = #{estado,jdbcType=DECIMAL}");
        }
        
        if (record.getImptotalneto() != null) {
            sql.SET("IMPTOTALNETO = #{imptotalneto,jdbcType=DECIMAL}");
        }
        
        if (record.getImptotaliva() != null) {
            sql.SET("IMPTOTALIVA = #{imptotaliva,jdbcType=DECIMAL}");
        }
        
        if (record.getImptotal() != null) {
            sql.SET("IMPTOTAL = #{imptotal,jdbcType=DECIMAL}");
        }
        
        if (record.getImptotalabonadoefectivo() != null) {
            sql.SET("IMPTOTALABONADOEFECTIVO = #{imptotalabonadoefectivo,jdbcType=DECIMAL}");
        }
        
        if (record.getImptotalabonadoporbanco() != null) {
            sql.SET("IMPTOTALABONADOPORBANCO = #{imptotalabonadoporbanco,jdbcType=DECIMAL}");
        }
        
        if (record.getImptotalabonado() != null) {
            sql.SET("IMPTOTALABONADO = #{imptotalabonado,jdbcType=DECIMAL}");
        }
        
        if (record.getImppendienteporabonar() != null) {
            sql.SET("IMPPENDIENTEPORABONAR = #{imppendienteporabonar,jdbcType=DECIMAL}");
        }
        
        if (record.getIdpersonadeudor() != null) {
            sql.SET("IDPERSONADEUDOR = #{idpersonadeudor,jdbcType=DECIMAL}");
        }
        
        if (record.getIdcuentadeudor() != null) {
            sql.SET("IDCUENTADEUDOR = #{idcuentadeudor,jdbcType=DECIMAL}");
        }
        
        if (record.getIdperorigen() != null) {
            sql.SET("IDPERORIGEN = #{idperorigen,jdbcType=DECIMAL}");
        }
        
        sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
        sql.WHERE("IDABONO = #{idabono,jdbcType=DECIMAL}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_ABONO
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    protected void applyWhere(SQL sql, FacAbonoExample example, boolean includeExamplePhrase) {
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