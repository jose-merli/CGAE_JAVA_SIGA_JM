package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.EnvRemitentes;
import org.itcgae.siga.db.entities.EnvRemitentesExample.Criteria;
import org.itcgae.siga.db.entities.EnvRemitentesExample.Criterion;
import org.itcgae.siga.db.entities.EnvRemitentesExample;

public class EnvRemitentesSqlProvider {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.ENV_REMITENTES
     *
     * @mbg.generated Tue Feb 01 13:28:22 CET 2022
     */
    public String countByExample(EnvRemitentesExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("ENV_REMITENTES");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.ENV_REMITENTES
     *
     * @mbg.generated Tue Feb 01 13:28:22 CET 2022
     */
    public String deleteByExample(EnvRemitentesExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("ENV_REMITENTES");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.ENV_REMITENTES
     *
     * @mbg.generated Tue Feb 01 13:28:22 CET 2022
     */
    public String insertSelective(EnvRemitentes record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("ENV_REMITENTES");
        
        if (record.getIdinstitucion() != null) {
            sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdenvio() != null) {
            sql.VALUES("IDENVIO", "#{idenvio,jdbcType=DECIMAL}");
        }
        
        if (record.getIdpersona() != null) {
            sql.VALUES("IDPERSONA", "#{idpersona,jdbcType=DECIMAL}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getCodigopostal() != null) {
            sql.VALUES("CODIGOPOSTAL", "#{codigopostal,jdbcType=VARCHAR}");
        }
        
        if (record.getCorreoelectronico() != null) {
            sql.VALUES("CORREOELECTRONICO", "#{correoelectronico,jdbcType=VARCHAR}");
        }
        
        if (record.getDomicilio() != null) {
            sql.VALUES("DOMICILIO", "#{domicilio,jdbcType=VARCHAR}");
        }
        
        if (record.getFax1() != null) {
            sql.VALUES("FAX1", "#{fax1,jdbcType=VARCHAR}");
        }
        
        if (record.getFax2() != null) {
            sql.VALUES("FAX2", "#{fax2,jdbcType=VARCHAR}");
        }
        
        if (record.getIdpais() != null) {
            sql.VALUES("IDPAIS", "#{idpais,jdbcType=VARCHAR}");
        }
        
        if (record.getIdpoblacion() != null) {
            sql.VALUES("IDPOBLACION", "#{idpoblacion,jdbcType=VARCHAR}");
        }
        
        if (record.getIdprovincia() != null) {
            sql.VALUES("IDPROVINCIA", "#{idprovincia,jdbcType=VARCHAR}");
        }
        
        if (record.getDescripcion() != null) {
            sql.VALUES("DESCRIPCION", "#{descripcion,jdbcType=VARCHAR}");
        }
        
        if (record.getPoblacionextranjera() != null) {
            sql.VALUES("POBLACIONEXTRANJERA", "#{poblacionextranjera,jdbcType=VARCHAR}");
        }
        
        if (record.getMovil() != null) {
            sql.VALUES("MOVIL", "#{movil,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.ENV_REMITENTES
     *
     * @mbg.generated Tue Feb 01 13:28:22 CET 2022
     */
    public String selectByExample(EnvRemitentesExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("IDINSTITUCION");
        } else {
            sql.SELECT("IDINSTITUCION");
        }
        sql.SELECT("IDENVIO");
        sql.SELECT("IDPERSONA");
        sql.SELECT("FECHAMODIFICACION");
        sql.SELECT("USUMODIFICACION");
        sql.SELECT("CODIGOPOSTAL");
        sql.SELECT("CORREOELECTRONICO");
        sql.SELECT("DOMICILIO");
        sql.SELECT("FAX1");
        sql.SELECT("FAX2");
        sql.SELECT("IDPAIS");
        sql.SELECT("IDPOBLACION");
        sql.SELECT("IDPROVINCIA");
        sql.SELECT("DESCRIPCION");
        sql.SELECT("POBLACIONEXTRANJERA");
        sql.SELECT("MOVIL");
        sql.FROM("ENV_REMITENTES");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.ENV_REMITENTES
     *
     * @mbg.generated Tue Feb 01 13:28:22 CET 2022
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        EnvRemitentes record = (EnvRemitentes) parameter.get("record");
        EnvRemitentesExample example = (EnvRemitentesExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("ENV_REMITENTES");
        
        if (record.getIdinstitucion() != null) {
            sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdenvio() != null) {
            sql.SET("IDENVIO = #{record.idenvio,jdbcType=DECIMAL}");
        }
        
        if (record.getIdpersona() != null) {
            sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getCodigopostal() != null) {
            sql.SET("CODIGOPOSTAL = #{record.codigopostal,jdbcType=VARCHAR}");
        }
        
        if (record.getCorreoelectronico() != null) {
            sql.SET("CORREOELECTRONICO = #{record.correoelectronico,jdbcType=VARCHAR}");
        }
        
        if (record.getDomicilio() != null) {
            sql.SET("DOMICILIO = #{record.domicilio,jdbcType=VARCHAR}");
        }
        
        if (record.getFax1() != null) {
            sql.SET("FAX1 = #{record.fax1,jdbcType=VARCHAR}");
        }
        
        if (record.getFax2() != null) {
            sql.SET("FAX2 = #{record.fax2,jdbcType=VARCHAR}");
        }
        
        if (record.getIdpais() != null) {
            sql.SET("IDPAIS = #{record.idpais,jdbcType=VARCHAR}");
        }
        
        if (record.getIdpoblacion() != null) {
            sql.SET("IDPOBLACION = #{record.idpoblacion,jdbcType=VARCHAR}");
        }
        
        if (record.getIdprovincia() != null) {
            sql.SET("IDPROVINCIA = #{record.idprovincia,jdbcType=VARCHAR}");
        }
        
        if (record.getDescripcion() != null) {
            sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
        }
        
        if (record.getPoblacionextranjera() != null) {
            sql.SET("POBLACIONEXTRANJERA = #{record.poblacionextranjera,jdbcType=VARCHAR}");
        }
        
        if (record.getMovil() != null) {
            sql.SET("MOVIL = #{record.movil,jdbcType=VARCHAR}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.ENV_REMITENTES
     *
     * @mbg.generated Tue Feb 01 13:28:22 CET 2022
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("ENV_REMITENTES");
        
        sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        sql.SET("IDENVIO = #{record.idenvio,jdbcType=DECIMAL}");
        sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
        sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        sql.SET("CODIGOPOSTAL = #{record.codigopostal,jdbcType=VARCHAR}");
        sql.SET("CORREOELECTRONICO = #{record.correoelectronico,jdbcType=VARCHAR}");
        sql.SET("DOMICILIO = #{record.domicilio,jdbcType=VARCHAR}");
        sql.SET("FAX1 = #{record.fax1,jdbcType=VARCHAR}");
        sql.SET("FAX2 = #{record.fax2,jdbcType=VARCHAR}");
        sql.SET("IDPAIS = #{record.idpais,jdbcType=VARCHAR}");
        sql.SET("IDPOBLACION = #{record.idpoblacion,jdbcType=VARCHAR}");
        sql.SET("IDPROVINCIA = #{record.idprovincia,jdbcType=VARCHAR}");
        sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
        sql.SET("POBLACIONEXTRANJERA = #{record.poblacionextranjera,jdbcType=VARCHAR}");
        sql.SET("MOVIL = #{record.movil,jdbcType=VARCHAR}");
        
        EnvRemitentesExample example = (EnvRemitentesExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.ENV_REMITENTES
     *
     * @mbg.generated Tue Feb 01 13:28:22 CET 2022
     */
    public String updateByPrimaryKeySelective(EnvRemitentes record) {
        SQL sql = new SQL();
        sql.UPDATE("ENV_REMITENTES");
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getCodigopostal() != null) {
            sql.SET("CODIGOPOSTAL = #{codigopostal,jdbcType=VARCHAR}");
        }
        
        if (record.getCorreoelectronico() != null) {
            sql.SET("CORREOELECTRONICO = #{correoelectronico,jdbcType=VARCHAR}");
        }
        
        if (record.getDomicilio() != null) {
            sql.SET("DOMICILIO = #{domicilio,jdbcType=VARCHAR}");
        }
        
        if (record.getFax1() != null) {
            sql.SET("FAX1 = #{fax1,jdbcType=VARCHAR}");
        }
        
        if (record.getFax2() != null) {
            sql.SET("FAX2 = #{fax2,jdbcType=VARCHAR}");
        }
        
        if (record.getIdpais() != null) {
            sql.SET("IDPAIS = #{idpais,jdbcType=VARCHAR}");
        }
        
        if (record.getIdpoblacion() != null) {
            sql.SET("IDPOBLACION = #{idpoblacion,jdbcType=VARCHAR}");
        }
        
        if (record.getIdprovincia() != null) {
            sql.SET("IDPROVINCIA = #{idprovincia,jdbcType=VARCHAR}");
        }
        
        if (record.getDescripcion() != null) {
            sql.SET("DESCRIPCION = #{descripcion,jdbcType=VARCHAR}");
        }
        
        if (record.getPoblacionextranjera() != null) {
            sql.SET("POBLACIONEXTRANJERA = #{poblacionextranjera,jdbcType=VARCHAR}");
        }
        
        if (record.getMovil() != null) {
            sql.SET("MOVIL = #{movil,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
        sql.WHERE("IDENVIO = #{idenvio,jdbcType=DECIMAL}");
        sql.WHERE("IDPERSONA = #{idpersona,jdbcType=DECIMAL}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.ENV_REMITENTES
     *
     * @mbg.generated Tue Feb 01 13:28:22 CET 2022
     */
    protected void applyWhere(SQL sql, EnvRemitentesExample example, boolean includeExamplePhrase) {
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