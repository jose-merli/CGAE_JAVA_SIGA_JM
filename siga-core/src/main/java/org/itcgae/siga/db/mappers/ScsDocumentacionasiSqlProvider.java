package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.ScsDocumentacionasi;
import org.itcgae.siga.db.entities.ScsDocumentacionasiExample.Criteria;
import org.itcgae.siga.db.entities.ScsDocumentacionasiExample.Criterion;
import org.itcgae.siga.db.entities.ScsDocumentacionasiExample;

public class ScsDocumentacionasiSqlProvider {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DOCUMENTACIONASI
     *
     * @mbg.generated Tue Apr 27 12:23:55 CEST 2021
     */
    public String countByExample(ScsDocumentacionasiExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("SCS_DOCUMENTACIONASI");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DOCUMENTACIONASI
     *
     * @mbg.generated Tue Apr 27 12:23:55 CEST 2021
     */
    public String deleteByExample(ScsDocumentacionasiExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("SCS_DOCUMENTACIONASI");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DOCUMENTACIONASI
     *
     * @mbg.generated Tue Apr 27 12:23:55 CEST 2021
     */
    public String insertSelective(ScsDocumentacionasi record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("SCS_DOCUMENTACIONASI");
        
        if (record.getIddocumentacionasi() != null) {
            sql.VALUES("IDDOCUMENTACIONASI", "#{iddocumentacionasi,jdbcType=DECIMAL}");
        }
        
        if (record.getIdinstitucion() != null) {
            sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdtipodocumento() != null) {
            sql.VALUES("IDTIPODOCUMENTO", "#{idtipodocumento,jdbcType=DECIMAL}");
        }
        
        if (record.getIdfichero() != null) {
            sql.VALUES("IDFICHERO", "#{idfichero,jdbcType=DECIMAL}");
        }
        
        if (record.getAnio() != null) {
            sql.VALUES("ANIO", "#{anio,jdbcType=DECIMAL}");
        }
        
        if (record.getNumero() != null) {
            sql.VALUES("NUMERO", "#{numero,jdbcType=DECIMAL}");
        }
        
        if (record.getIdactuacion() != null) {
            sql.VALUES("IDACTUACION", "#{idactuacion,jdbcType=DECIMAL}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechaentrada() != null) {
            sql.VALUES("FECHAENTRADA", "#{fechaentrada,jdbcType=TIMESTAMP}");
        }
        
        if (record.getObservaciones() != null) {
            sql.VALUES("OBSERVACIONES", "#{observaciones,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DOCUMENTACIONASI
     *
     * @mbg.generated Tue Apr 27 12:23:55 CEST 2021
     */
    public String selectByExample(ScsDocumentacionasiExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("IDDOCUMENTACIONASI");
        } else {
            sql.SELECT("IDDOCUMENTACIONASI");
        }
        sql.SELECT("IDINSTITUCION");
        sql.SELECT("IDTIPODOCUMENTO");
        sql.SELECT("IDFICHERO");
        sql.SELECT("ANIO");
        sql.SELECT("NUMERO");
        sql.SELECT("IDACTUACION");
        sql.SELECT("USUMODIFICACION");
        sql.SELECT("FECHAMODIFICACION");
        sql.SELECT("FECHAENTRADA");
        sql.SELECT("OBSERVACIONES");
        sql.FROM("SCS_DOCUMENTACIONASI");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DOCUMENTACIONASI
     *
     * @mbg.generated Tue Apr 27 12:23:55 CEST 2021
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        ScsDocumentacionasi record = (ScsDocumentacionasi) parameter.get("record");
        ScsDocumentacionasiExample example = (ScsDocumentacionasiExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("SCS_DOCUMENTACIONASI");
        
        if (record.getIddocumentacionasi() != null) {
            sql.SET("IDDOCUMENTACIONASI = #{record.iddocumentacionasi,jdbcType=DECIMAL}");
        }
        
        if (record.getIdinstitucion() != null) {
            sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdtipodocumento() != null) {
            sql.SET("IDTIPODOCUMENTO = #{record.idtipodocumento,jdbcType=DECIMAL}");
        }
        
        if (record.getIdfichero() != null) {
            sql.SET("IDFICHERO = #{record.idfichero,jdbcType=DECIMAL}");
        }
        
        if (record.getAnio() != null) {
            sql.SET("ANIO = #{record.anio,jdbcType=DECIMAL}");
        }
        
        if (record.getNumero() != null) {
            sql.SET("NUMERO = #{record.numero,jdbcType=DECIMAL}");
        }
        
        if (record.getIdactuacion() != null) {
            sql.SET("IDACTUACION = #{record.idactuacion,jdbcType=DECIMAL}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechaentrada() != null) {
            sql.SET("FECHAENTRADA = #{record.fechaentrada,jdbcType=TIMESTAMP}");
        }
        
        if (record.getObservaciones() != null) {
            sql.SET("OBSERVACIONES = #{record.observaciones,jdbcType=VARCHAR}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DOCUMENTACIONASI
     *
     * @mbg.generated Tue Apr 27 12:23:55 CEST 2021
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("SCS_DOCUMENTACIONASI");
        
        sql.SET("IDDOCUMENTACIONASI = #{record.iddocumentacionasi,jdbcType=DECIMAL}");
        sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        sql.SET("IDTIPODOCUMENTO = #{record.idtipodocumento,jdbcType=DECIMAL}");
        sql.SET("IDFICHERO = #{record.idfichero,jdbcType=DECIMAL}");
        sql.SET("ANIO = #{record.anio,jdbcType=DECIMAL}");
        sql.SET("NUMERO = #{record.numero,jdbcType=DECIMAL}");
        sql.SET("IDACTUACION = #{record.idactuacion,jdbcType=DECIMAL}");
        sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        sql.SET("FECHAENTRADA = #{record.fechaentrada,jdbcType=TIMESTAMP}");
        sql.SET("OBSERVACIONES = #{record.observaciones,jdbcType=VARCHAR}");
        
        ScsDocumentacionasiExample example = (ScsDocumentacionasiExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DOCUMENTACIONASI
     *
     * @mbg.generated Tue Apr 27 12:23:55 CEST 2021
     */
    public String updateByPrimaryKeySelective(ScsDocumentacionasi record) {
        SQL sql = new SQL();
        sql.UPDATE("SCS_DOCUMENTACIONASI");
        
        if (record.getIdtipodocumento() != null) {
            sql.SET("IDTIPODOCUMENTO = #{idtipodocumento,jdbcType=DECIMAL}");
        }
        
        if (record.getIdfichero() != null) {
            sql.SET("IDFICHERO = #{idfichero,jdbcType=DECIMAL}");
        }
        
        if (record.getAnio() != null) {
            sql.SET("ANIO = #{anio,jdbcType=DECIMAL}");
        }
        
        if (record.getNumero() != null) {
            sql.SET("NUMERO = #{numero,jdbcType=DECIMAL}");
        }
        
        if (record.getIdactuacion() != null) {
            sql.SET("IDACTUACION = #{idactuacion,jdbcType=DECIMAL}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechaentrada() != null) {
            sql.SET("FECHAENTRADA = #{fechaentrada,jdbcType=TIMESTAMP}");
        }
        
        if (record.getObservaciones() != null) {
            sql.SET("OBSERVACIONES = #{observaciones,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("IDDOCUMENTACIONASI = #{iddocumentacionasi,jdbcType=DECIMAL}");
        sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DOCUMENTACIONASI
     *
     * @mbg.generated Tue Apr 27 12:23:55 CEST 2021
     */
    protected void applyWhere(SQL sql, ScsDocumentacionasiExample example, boolean includeExamplePhrase) {
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