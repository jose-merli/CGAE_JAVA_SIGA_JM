package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.ScsDatosprocuradores;
import org.itcgae.siga.db.entities.ScsDatosprocuradoresExample.Criteria;
import org.itcgae.siga.db.entities.ScsDatosprocuradoresExample.Criterion;
import org.itcgae.siga.db.entities.ScsDatosprocuradoresExample;

public class ScsDatosprocuradoresSqlProvider {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DATOSPROCURADORES
     *
     * @mbg.generated Tue Nov 02 09:26:12 CET 2021
     */
    public String countByExample(ScsDatosprocuradoresExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("SCS_DATOSPROCURADORES");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DATOSPROCURADORES
     *
     * @mbg.generated Tue Nov 02 09:26:12 CET 2021
     */
    public String deleteByExample(ScsDatosprocuradoresExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("SCS_DATOSPROCURADORES");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DATOSPROCURADORES
     *
     * @mbg.generated Tue Nov 02 09:26:12 CET 2021
     */
    public String insertSelective(ScsDatosprocuradores record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("SCS_DATOSPROCURADORES");
        
        sql.VALUES("IDDATOSPROCURADORES", "#{iddatosprocuradores,jdbcType=DECIMAL}");
        
        if (record.getIdinstitucion() != null) {
            sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getCodigodesignaabogado() != null) {
            sql.VALUES("CODIGODESIGNAABOGADO", "#{codigodesignaabogado,jdbcType=VARCHAR}");
        }
        
        if (record.getNumejg() != null) {
            sql.VALUES("NUMEJG", "#{numejg,jdbcType=VARCHAR}");
        }
        
        if (record.getNumcolprocurador() != null) {
            sql.VALUES("NUMCOLPROCURADOR", "#{numcolprocurador,jdbcType=VARCHAR}");
        }
        
        if (record.getFechadesigprocurador() != null) {
            sql.VALUES("FECHADESIGPROCURADOR", "#{fechadesigprocurador,jdbcType=TIMESTAMP}");
        }
        
        if (record.getNumdesignaprocurador() != null) {
            sql.VALUES("NUMDESIGNAPROCURADOR", "#{numdesignaprocurador,jdbcType=VARCHAR}");
        }
        
        if (record.getObservaciones() != null) {
            sql.VALUES("OBSERVACIONES", "#{observaciones,jdbcType=VARCHAR}");
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
     * This method corresponds to the database table USCGAE.SCS_DATOSPROCURADORES
     *
     * @mbg.generated Tue Nov 02 09:26:12 CET 2021
     */
    public String selectByExample(ScsDatosprocuradoresExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("IDDATOSPROCURADORES");
        } else {
            sql.SELECT("IDDATOSPROCURADORES");
        }
        sql.SELECT("IDINSTITUCION");
        sql.SELECT("CODIGODESIGNAABOGADO");
        sql.SELECT("NUMEJG");
        sql.SELECT("NUMCOLPROCURADOR");
        sql.SELECT("FECHADESIGPROCURADOR");
        sql.SELECT("NUMDESIGNAPROCURADOR");
        sql.SELECT("OBSERVACIONES");
        sql.SELECT("FECHAMODIFICACION");
        sql.SELECT("USUMODIFICACION");
        sql.FROM("SCS_DATOSPROCURADORES");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DATOSPROCURADORES
     *
     * @mbg.generated Tue Nov 02 09:26:12 CET 2021
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        ScsDatosprocuradores record = (ScsDatosprocuradores) parameter.get("record");
        ScsDatosprocuradoresExample example = (ScsDatosprocuradoresExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("SCS_DATOSPROCURADORES");
        
        if (record.getIddatosprocuradores() != null) {
            sql.SET("IDDATOSPROCURADORES = #{record.iddatosprocuradores,jdbcType=DECIMAL}");
        }
        
        if (record.getIdinstitucion() != null) {
            sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getCodigodesignaabogado() != null) {
            sql.SET("CODIGODESIGNAABOGADO = #{record.codigodesignaabogado,jdbcType=VARCHAR}");
        }
        
        if (record.getNumejg() != null) {
            sql.SET("NUMEJG = #{record.numejg,jdbcType=VARCHAR}");
        }
        
        if (record.getNumcolprocurador() != null) {
            sql.SET("NUMCOLPROCURADOR = #{record.numcolprocurador,jdbcType=VARCHAR}");
        }
        
        if (record.getFechadesigprocurador() != null) {
            sql.SET("FECHADESIGPROCURADOR = #{record.fechadesigprocurador,jdbcType=TIMESTAMP}");
        }
        
        if (record.getNumdesignaprocurador() != null) {
            sql.SET("NUMDESIGNAPROCURADOR = #{record.numdesignaprocurador,jdbcType=VARCHAR}");
        }
        
        if (record.getObservaciones() != null) {
            sql.SET("OBSERVACIONES = #{record.observaciones,jdbcType=VARCHAR}");
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
     * This method corresponds to the database table USCGAE.SCS_DATOSPROCURADORES
     *
     * @mbg.generated Tue Nov 02 09:26:12 CET 2021
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("SCS_DATOSPROCURADORES");
        
        sql.SET("IDDATOSPROCURADORES = #{record.iddatosprocuradores,jdbcType=DECIMAL}");
        sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        sql.SET("CODIGODESIGNAABOGADO = #{record.codigodesignaabogado,jdbcType=VARCHAR}");
        sql.SET("NUMEJG = #{record.numejg,jdbcType=VARCHAR}");
        sql.SET("NUMCOLPROCURADOR = #{record.numcolprocurador,jdbcType=VARCHAR}");
        sql.SET("FECHADESIGPROCURADOR = #{record.fechadesigprocurador,jdbcType=TIMESTAMP}");
        sql.SET("NUMDESIGNAPROCURADOR = #{record.numdesignaprocurador,jdbcType=VARCHAR}");
        sql.SET("OBSERVACIONES = #{record.observaciones,jdbcType=VARCHAR}");
        sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        
        ScsDatosprocuradoresExample example = (ScsDatosprocuradoresExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DATOSPROCURADORES
     *
     * @mbg.generated Tue Nov 02 09:26:12 CET 2021
     */
    public String updateByPrimaryKeySelective(ScsDatosprocuradores record) {
        SQL sql = new SQL();
        sql.UPDATE("SCS_DATOSPROCURADORES");
        
        if (record.getIdinstitucion() != null) {
            sql.SET("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getCodigodesignaabogado() != null) {
            sql.SET("CODIGODESIGNAABOGADO = #{codigodesignaabogado,jdbcType=VARCHAR}");
        }
        
        if (record.getNumejg() != null) {
            sql.SET("NUMEJG = #{numejg,jdbcType=VARCHAR}");
        }
        
        if (record.getNumcolprocurador() != null) {
            sql.SET("NUMCOLPROCURADOR = #{numcolprocurador,jdbcType=VARCHAR}");
        }
        
        if (record.getFechadesigprocurador() != null) {
            sql.SET("FECHADESIGPROCURADOR = #{fechadesigprocurador,jdbcType=TIMESTAMP}");
        }
        
        if (record.getNumdesignaprocurador() != null) {
            sql.SET("NUMDESIGNAPROCURADOR = #{numdesignaprocurador,jdbcType=VARCHAR}");
        }
        
        if (record.getObservaciones() != null) {
            sql.SET("OBSERVACIONES = #{observaciones,jdbcType=VARCHAR}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
        }
        
        sql.WHERE("IDDATOSPROCURADORES = #{iddatosprocuradores,jdbcType=DECIMAL}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DATOSPROCURADORES
     *
     * @mbg.generated Tue Nov 02 09:26:12 CET 2021
     */
    protected void applyWhere(SQL sql, ScsDatosprocuradoresExample example, boolean includeExamplePhrase) {
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