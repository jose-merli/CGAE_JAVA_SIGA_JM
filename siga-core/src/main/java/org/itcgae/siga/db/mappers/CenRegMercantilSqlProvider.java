package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.CenRegMercantil;
import org.itcgae.siga.db.entities.CenRegMercantilExample;
import org.itcgae.siga.db.entities.CenRegMercantilExample.Criteria;
import org.itcgae.siga.db.entities.CenRegMercantilExample.Criterion;

public class CenRegMercantilSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CEN_REG_MERCANTIL
     *
     * @mbg.generated Fri Aug 03 11:55:27 CEST 2018
     */
    public String countByExample(CenRegMercantilExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("CEN_REG_MERCANTIL");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CEN_REG_MERCANTIL
     *
     * @mbg.generated Fri Aug 03 11:55:27 CEST 2018
     */
    public String deleteByExample(CenRegMercantilExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("CEN_REG_MERCANTIL");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CEN_REG_MERCANTIL
     *
     * @mbg.generated Fri Aug 03 11:55:27 CEST 2018
     */
    public String insertSelective(CenRegMercantil record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("CEN_REG_MERCANTIL");
        
        sql.VALUES("ID_DATOS_REG", "#{idDatosReg,jdbcType=DECIMAL}");
        
        if (record.getNumRegistro() != null) {
            sql.VALUES("NUM_REGISTRO", "#{numRegistro,jdbcType=VARCHAR}");
        }
        
        if (record.getIdentificacionReg() != null) {
            sql.VALUES("IDENTIFICACION_REG", "#{identificacionReg,jdbcType=VARCHAR}");
        }
        
        if (record.getFechaInscripcion() != null) {
            sql.VALUES("FECHA_INSCRIPCION", "#{fechaInscripcion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechaCancelacion() != null) {
            sql.VALUES("FECHA_CANCELACION", "#{fechaCancelacion,jdbcType=TIMESTAMP}");
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
     * This method corresponds to the database table USCGAE.CEN_REG_MERCANTIL
     *
     * @mbg.generated Fri Aug 03 11:55:27 CEST 2018
     */
    public String selectByExample(CenRegMercantilExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("ID_DATOS_REG");
        } else {
            sql.SELECT("ID_DATOS_REG");
        }
        sql.SELECT("NUM_REGISTRO");
        sql.SELECT("IDENTIFICACION_REG");
        sql.SELECT("FECHA_INSCRIPCION");
        sql.SELECT("FECHA_CANCELACION");
        sql.SELECT("FECHAMODIFICACION");
        sql.SELECT("USUMODIFICACION");
        sql.FROM("CEN_REG_MERCANTIL");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CEN_REG_MERCANTIL
     *
     * @mbg.generated Fri Aug 03 11:55:27 CEST 2018
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        CenRegMercantil record = (CenRegMercantil) parameter.get("record");
        CenRegMercantilExample example = (CenRegMercantilExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("CEN_REG_MERCANTIL");
        
        if (record.getIdDatosReg() != null) {
            sql.SET("ID_DATOS_REG = #{record.idDatosReg,jdbcType=DECIMAL}");
        }
        
        if (record.getNumRegistro() != null) {
            sql.SET("NUM_REGISTRO = #{record.numRegistro,jdbcType=VARCHAR}");
        }
        
        if (record.getIdentificacionReg() != null) {
            sql.SET("IDENTIFICACION_REG = #{record.identificacionReg,jdbcType=VARCHAR}");
        }
        
        if (record.getFechaInscripcion() != null) {
            sql.SET("FECHA_INSCRIPCION = #{record.fechaInscripcion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechaCancelacion() != null) {
            sql.SET("FECHA_CANCELACION = #{record.fechaCancelacion,jdbcType=TIMESTAMP}");
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
     * This method corresponds to the database table USCGAE.CEN_REG_MERCANTIL
     *
     * @mbg.generated Fri Aug 03 11:55:27 CEST 2018
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("CEN_REG_MERCANTIL");
        
        sql.SET("ID_DATOS_REG = #{record.idDatosReg,jdbcType=DECIMAL}");
        sql.SET("NUM_REGISTRO = #{record.numRegistro,jdbcType=VARCHAR}");
        sql.SET("IDENTIFICACION_REG = #{record.identificacionReg,jdbcType=VARCHAR}");
        sql.SET("FECHA_INSCRIPCION = #{record.fechaInscripcion,jdbcType=TIMESTAMP}");
        sql.SET("FECHA_CANCELACION = #{record.fechaCancelacion,jdbcType=TIMESTAMP}");
        sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        
        CenRegMercantilExample example = (CenRegMercantilExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CEN_REG_MERCANTIL
     *
     * @mbg.generated Fri Aug 03 11:55:27 CEST 2018
     */
    public String updateByPrimaryKeySelective(CenRegMercantil record) {
        SQL sql = new SQL();
        sql.UPDATE("CEN_REG_MERCANTIL");
        
        if (record.getNumRegistro() != null) {
            sql.SET("NUM_REGISTRO = #{numRegistro,jdbcType=VARCHAR}");
        }
        
        if (record.getIdentificacionReg() != null) {
            sql.SET("IDENTIFICACION_REG = #{identificacionReg,jdbcType=VARCHAR}");
        }
        
        if (record.getFechaInscripcion() != null) {
            sql.SET("FECHA_INSCRIPCION = #{fechaInscripcion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechaCancelacion() != null) {
            sql.SET("FECHA_CANCELACION = #{fechaCancelacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
        }
        
        sql.WHERE("ID_DATOS_REG = #{idDatosReg,jdbcType=DECIMAL}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CEN_REG_MERCANTIL
     *
     * @mbg.generated Fri Aug 03 11:55:27 CEST 2018
     */
    protected void applyWhere(SQL sql, CenRegMercantilExample example, boolean includeExamplePhrase) {
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