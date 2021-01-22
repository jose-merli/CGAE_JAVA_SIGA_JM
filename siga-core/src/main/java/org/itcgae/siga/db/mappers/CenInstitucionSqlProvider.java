package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenInstitucionExample;
import org.itcgae.siga.db.entities.CenInstitucionExample.Criteria;
import org.itcgae.siga.db.entities.CenInstitucionExample.Criterion;

public class CenInstitucionSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.CEN_INSTITUCION
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String countByExample(CenInstitucionExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("CEN_INSTITUCION");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.CEN_INSTITUCION
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String deleteByExample(CenInstitucionExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("CEN_INSTITUCION");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.CEN_INSTITUCION
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String insertSelective(CenInstitucion record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("CEN_INSTITUCION");
        
        if (record.getIdinstitucion() != null) {
            sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getNombre() != null) {
            sql.VALUES("NOMBRE", "#{nombre,jdbcType=VARCHAR}");
        }
        
        if (record.getBbddcpd() != null) {
            sql.VALUES("BBDDCPD", "#{bbddcpd,jdbcType=VARCHAR}");
        }
        
        if (record.getIdlenguaje() != null) {
            sql.VALUES("IDLENGUAJE", "#{idlenguaje,jdbcType=VARCHAR}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdpersona() != null) {
            sql.VALUES("IDPERSONA", "#{idpersona,jdbcType=DECIMAL}");
        }
        
        if (record.getCuentacontablecaja() != null) {
            sql.VALUES("CUENTACONTABLECAJA", "#{cuentacontablecaja,jdbcType=VARCHAR}");
        }
        
        if (record.getCenInstIdinstitucion() != null) {
            sql.VALUES("CEN_INST_IDINSTITUCION", "#{cenInstIdinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getAbreviatura() != null) {
            sql.VALUES("ABREVIATURA", "#{abreviatura,jdbcType=VARCHAR}");
        }
        
        if (record.getFechaenproduccion() != null) {
            sql.VALUES("FECHAENPRODUCCION", "#{fechaenproduccion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCodigoext() != null) {
            sql.VALUES("CODIGOEXT", "#{codigoext,jdbcType=VARCHAR}");
        }
        
        if (record.getIdcomision() != null) {
            sql.VALUES("IDCOMISION", "#{idcomision,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.CEN_INSTITUCION
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String selectByExample(CenInstitucionExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("IDINSTITUCION");
        } else {
            sql.SELECT("IDINSTITUCION");
        }
        sql.SELECT("NOMBRE");
        sql.SELECT("BBDDCPD");
        sql.SELECT("IDLENGUAJE");
        sql.SELECT("FECHAMODIFICACION");
        sql.SELECT("USUMODIFICACION");
        sql.SELECT("IDPERSONA");
        sql.SELECT("CUENTACONTABLECAJA");
        sql.SELECT("CEN_INST_IDINSTITUCION");
        sql.SELECT("ABREVIATURA");
        sql.SELECT("FECHAENPRODUCCION");
        sql.SELECT("CODIGOEXT");
        sql.SELECT("IDCOMISION");
        sql.FROM("CEN_INSTITUCION");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.CEN_INSTITUCION
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        CenInstitucion record = (CenInstitucion) parameter.get("record");
        CenInstitucionExample example = (CenInstitucionExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("CEN_INSTITUCION");
        
        if (record.getIdinstitucion() != null) {
            sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getNombre() != null) {
            sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
        }
        
        if (record.getBbddcpd() != null) {
            sql.SET("BBDDCPD = #{record.bbddcpd,jdbcType=VARCHAR}");
        }
        
        if (record.getIdlenguaje() != null) {
            sql.SET("IDLENGUAJE = #{record.idlenguaje,jdbcType=VARCHAR}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdpersona() != null) {
            sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
        }
        
        if (record.getCuentacontablecaja() != null) {
            sql.SET("CUENTACONTABLECAJA = #{record.cuentacontablecaja,jdbcType=VARCHAR}");
        }
        
        if (record.getCenInstIdinstitucion() != null) {
            sql.SET("CEN_INST_IDINSTITUCION = #{record.cenInstIdinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getAbreviatura() != null) {
            sql.SET("ABREVIATURA = #{record.abreviatura,jdbcType=VARCHAR}");
        }
        
        if (record.getFechaenproduccion() != null) {
            sql.SET("FECHAENPRODUCCION = #{record.fechaenproduccion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCodigoext() != null) {
            sql.SET("CODIGOEXT = #{record.codigoext,jdbcType=VARCHAR}");
        }
        
        if (record.getIdcomision() != null) {
            sql.SET("IDCOMISION = #{record.idcomision,jdbcType=VARCHAR}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.CEN_INSTITUCION
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("CEN_INSTITUCION");
        
        sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
        sql.SET("BBDDCPD = #{record.bbddcpd,jdbcType=VARCHAR}");
        sql.SET("IDLENGUAJE = #{record.idlenguaje,jdbcType=VARCHAR}");
        sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
        sql.SET("CUENTACONTABLECAJA = #{record.cuentacontablecaja,jdbcType=VARCHAR}");
        sql.SET("CEN_INST_IDINSTITUCION = #{record.cenInstIdinstitucion,jdbcType=DECIMAL}");
        sql.SET("ABREVIATURA = #{record.abreviatura,jdbcType=VARCHAR}");
        sql.SET("FECHAENPRODUCCION = #{record.fechaenproduccion,jdbcType=TIMESTAMP}");
        sql.SET("CODIGOEXT = #{record.codigoext,jdbcType=VARCHAR}");
        sql.SET("IDCOMISION = #{record.idcomision,jdbcType=VARCHAR}");
        
        CenInstitucionExample example = (CenInstitucionExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.CEN_INSTITUCION
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String updateByPrimaryKeySelective(CenInstitucion record) {
        SQL sql = new SQL();
        sql.UPDATE("CEN_INSTITUCION");
        
        if (record.getNombre() != null) {
            sql.SET("NOMBRE = #{nombre,jdbcType=VARCHAR}");
        }
        
        if (record.getBbddcpd() != null) {
            sql.SET("BBDDCPD = #{bbddcpd,jdbcType=VARCHAR}");
        }
        
        if (record.getIdlenguaje() != null) {
            sql.SET("IDLENGUAJE = #{idlenguaje,jdbcType=VARCHAR}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdpersona() != null) {
            sql.SET("IDPERSONA = #{idpersona,jdbcType=DECIMAL}");
        }
        
        if (record.getCuentacontablecaja() != null) {
            sql.SET("CUENTACONTABLECAJA = #{cuentacontablecaja,jdbcType=VARCHAR}");
        }
        
        if (record.getCenInstIdinstitucion() != null) {
            sql.SET("CEN_INST_IDINSTITUCION = #{cenInstIdinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getAbreviatura() != null) {
            sql.SET("ABREVIATURA = #{abreviatura,jdbcType=VARCHAR}");
        }
        
        if (record.getFechaenproduccion() != null) {
            sql.SET("FECHAENPRODUCCION = #{fechaenproduccion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCodigoext() != null) {
            sql.SET("CODIGOEXT = #{codigoext,jdbcType=VARCHAR}");
        }
        
        if (record.getIdcomision() != null) {
            sql.SET("IDCOMISION = #{idcomision,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.CEN_INSTITUCION
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    protected void applyWhere(SQL sql, CenInstitucionExample example, boolean includeExamplePhrase) {
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