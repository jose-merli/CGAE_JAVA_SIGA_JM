package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.ScsUnidadfamiliarejg;
import org.itcgae.siga.db.entities.ScsUnidadfamiliarejgExample.Criteria;
import org.itcgae.siga.db.entities.ScsUnidadfamiliarejgExample.Criterion;
import org.itcgae.siga.db.entities.ScsUnidadfamiliarejgExample;

public class ScsUnidadfamiliarejgSqlProvider {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_UNIDADFAMILIAREJG
     *
     * @mbg.generated Tue May 11 13:11:04 CEST 2021
     */
    public String countByExample(ScsUnidadfamiliarejgExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("SCS_UNIDADFAMILIAREJG");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_UNIDADFAMILIAREJG
     *
     * @mbg.generated Tue May 11 13:11:04 CEST 2021
     */
    public String deleteByExample(ScsUnidadfamiliarejgExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("SCS_UNIDADFAMILIAREJG");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_UNIDADFAMILIAREJG
     *
     * @mbg.generated Tue May 11 13:11:04 CEST 2021
     */
    public String insertSelective(ScsUnidadfamiliarejg record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("SCS_UNIDADFAMILIAREJG");
        
        if (record.getIdinstitucion() != null) {
            sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdtipoejg() != null) {
            sql.VALUES("IDTIPOEJG", "#{idtipoejg,jdbcType=DECIMAL}");
        }
        
        if (record.getAnio() != null) {
            sql.VALUES("ANIO", "#{anio,jdbcType=DECIMAL}");
        }
        
        if (record.getNumero() != null) {
            sql.VALUES("NUMERO", "#{numero,jdbcType=DECIMAL}");
        }
        
        if (record.getIdpersona() != null) {
            sql.VALUES("IDPERSONA", "#{idpersona,jdbcType=DECIMAL}");
        }
        
        if (record.getSolicitante() != null) {
            sql.VALUES("SOLICITANTE", "#{solicitante,jdbcType=DECIMAL}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getObservaciones() != null) {
            sql.VALUES("OBSERVACIONES", "#{observaciones,jdbcType=VARCHAR}");
        }
        
        if (record.getEncalidadde() != null) {
            sql.VALUES("ENCALIDADDE", "#{encalidadde,jdbcType=VARCHAR}");
        }
        
        if (record.getBienesinmuebles() != null) {
            sql.VALUES("BIENESINMUEBLES", "#{bienesinmuebles,jdbcType=VARCHAR}");
        }
        
        if (record.getImportebienesinmuebles() != null) {
            sql.VALUES("IMPORTEBIENESINMUEBLES", "#{importebienesinmuebles,jdbcType=DECIMAL}");
        }
        
        if (record.getBienesmuebles() != null) {
            sql.VALUES("BIENESMUEBLES", "#{bienesmuebles,jdbcType=VARCHAR}");
        }
        
        if (record.getImportebienesmuebles() != null) {
            sql.VALUES("IMPORTEBIENESMUEBLES", "#{importebienesmuebles,jdbcType=DECIMAL}");
        }
        
        if (record.getOtrosbienes() != null) {
            sql.VALUES("OTROSBIENES", "#{otrosbienes,jdbcType=VARCHAR}");
        }
        
        if (record.getDescripcioningresosanuales() != null) {
            sql.VALUES("DESCRIPCIONINGRESOSANUALES", "#{descripcioningresosanuales,jdbcType=VARCHAR}");
        }
        
        if (record.getImporteingresosanuales() != null) {
            sql.VALUES("IMPORTEINGRESOSANUALES", "#{importeingresosanuales,jdbcType=DECIMAL}");
        }
        
        if (record.getImporteotrosbienes() != null) {
            sql.VALUES("IMPORTEOTROSBIENES", "#{importeotrosbienes,jdbcType=DECIMAL}");
        }
        
        if (record.getIdtipogrupolab() != null) {
            sql.VALUES("IDTIPOGRUPOLAB", "#{idtipogrupolab,jdbcType=DECIMAL}");
        }
        
        if (record.getIdparentesco() != null) {
            sql.VALUES("IDPARENTESCO", "#{idparentesco,jdbcType=DECIMAL}");
        }
        
        if (record.getIdtipoingreso() != null) {
            sql.VALUES("IDTIPOINGRESO", "#{idtipoingreso,jdbcType=DECIMAL}");
        }
        
        if (record.getIncapacitado() != null) {
            sql.VALUES("INCAPACITADO", "#{incapacitado,jdbcType=DECIMAL}");
        }
        
        if (record.getCircunstanciasExcepcionales() != null) {
            sql.VALUES("CIRCUNSTANCIAS_EXCEPCIONALES", "#{circunstanciasExcepcionales,jdbcType=DECIMAL}");
        }
        
        if (record.getFechabaja() != null) {
            sql.VALUES("FECHABAJA", "#{fechabaja,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_UNIDADFAMILIAREJG
     *
     * @mbg.generated Tue May 11 13:11:04 CEST 2021
     */
    public String selectByExample(ScsUnidadfamiliarejgExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("IDINSTITUCION");
        } else {
            sql.SELECT("IDINSTITUCION");
        }
        sql.SELECT("IDTIPOEJG");
        sql.SELECT("ANIO");
        sql.SELECT("NUMERO");
        sql.SELECT("IDPERSONA");
        sql.SELECT("SOLICITANTE");
        sql.SELECT("FECHAMODIFICACION");
        sql.SELECT("USUMODIFICACION");
        sql.SELECT("OBSERVACIONES");
        sql.SELECT("ENCALIDADDE");
        sql.SELECT("BIENESINMUEBLES");
        sql.SELECT("IMPORTEBIENESINMUEBLES");
        sql.SELECT("BIENESMUEBLES");
        sql.SELECT("IMPORTEBIENESMUEBLES");
        sql.SELECT("OTROSBIENES");
        sql.SELECT("DESCRIPCIONINGRESOSANUALES");
        sql.SELECT("IMPORTEINGRESOSANUALES");
        sql.SELECT("IMPORTEOTROSBIENES");
        sql.SELECT("IDTIPOGRUPOLAB");
        sql.SELECT("IDPARENTESCO");
        sql.SELECT("IDTIPOINGRESO");
        sql.SELECT("INCAPACITADO");
        sql.SELECT("CIRCUNSTANCIAS_EXCEPCIONALES");
        sql.SELECT("FECHABAJA");
        sql.FROM("SCS_UNIDADFAMILIAREJG");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_UNIDADFAMILIAREJG
     *
     * @mbg.generated Tue May 11 13:11:04 CEST 2021
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        ScsUnidadfamiliarejg record = (ScsUnidadfamiliarejg) parameter.get("record");
        ScsUnidadfamiliarejgExample example = (ScsUnidadfamiliarejgExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("SCS_UNIDADFAMILIAREJG");
        
        if (record.getIdinstitucion() != null) {
            sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdtipoejg() != null) {
            sql.SET("IDTIPOEJG = #{record.idtipoejg,jdbcType=DECIMAL}");
        }
        
        if (record.getAnio() != null) {
            sql.SET("ANIO = #{record.anio,jdbcType=DECIMAL}");
        }
        
        if (record.getNumero() != null) {
            sql.SET("NUMERO = #{record.numero,jdbcType=DECIMAL}");
        }
        
        if (record.getIdpersona() != null) {
            sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
        }
        
        if (record.getSolicitante() != null) {
            sql.SET("SOLICITANTE = #{record.solicitante,jdbcType=DECIMAL}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getObservaciones() != null) {
            sql.SET("OBSERVACIONES = #{record.observaciones,jdbcType=VARCHAR}");
        }
        
        if (record.getEncalidadde() != null) {
            sql.SET("ENCALIDADDE = #{record.encalidadde,jdbcType=VARCHAR}");
        }
        
        if (record.getBienesinmuebles() != null) {
            sql.SET("BIENESINMUEBLES = #{record.bienesinmuebles,jdbcType=VARCHAR}");
        }
        
        if (record.getImportebienesinmuebles() != null) {
            sql.SET("IMPORTEBIENESINMUEBLES = #{record.importebienesinmuebles,jdbcType=DECIMAL}");
        }
        
        if (record.getBienesmuebles() != null) {
            sql.SET("BIENESMUEBLES = #{record.bienesmuebles,jdbcType=VARCHAR}");
        }
        
        if (record.getImportebienesmuebles() != null) {
            sql.SET("IMPORTEBIENESMUEBLES = #{record.importebienesmuebles,jdbcType=DECIMAL}");
        }
        
        if (record.getOtrosbienes() != null) {
            sql.SET("OTROSBIENES = #{record.otrosbienes,jdbcType=VARCHAR}");
        }
        
        if (record.getDescripcioningresosanuales() != null) {
            sql.SET("DESCRIPCIONINGRESOSANUALES = #{record.descripcioningresosanuales,jdbcType=VARCHAR}");
        }
        
        if (record.getImporteingresosanuales() != null) {
            sql.SET("IMPORTEINGRESOSANUALES = #{record.importeingresosanuales,jdbcType=DECIMAL}");
        }
        
        if (record.getImporteotrosbienes() != null) {
            sql.SET("IMPORTEOTROSBIENES = #{record.importeotrosbienes,jdbcType=DECIMAL}");
        }
        
        if (record.getIdtipogrupolab() != null) {
            sql.SET("IDTIPOGRUPOLAB = #{record.idtipogrupolab,jdbcType=DECIMAL}");
        }
        
        if (record.getIdparentesco() != null) {
            sql.SET("IDPARENTESCO = #{record.idparentesco,jdbcType=DECIMAL}");
        }
        
        if (record.getIdtipoingreso() != null) {
            sql.SET("IDTIPOINGRESO = #{record.idtipoingreso,jdbcType=DECIMAL}");
        }
        
        if (record.getIncapacitado() != null) {
            sql.SET("INCAPACITADO = #{record.incapacitado,jdbcType=DECIMAL}");
        }
        
        if (record.getCircunstanciasExcepcionales() != null) {
            sql.SET("CIRCUNSTANCIAS_EXCEPCIONALES = #{record.circunstanciasExcepcionales,jdbcType=DECIMAL}");
        }
        
        if (record.getFechabaja() != null) {
            sql.SET("FECHABAJA = #{record.fechabaja,jdbcType=TIMESTAMP}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_UNIDADFAMILIAREJG
     *
     * @mbg.generated Tue May 11 13:11:04 CEST 2021
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("SCS_UNIDADFAMILIAREJG");
        
        sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        sql.SET("IDTIPOEJG = #{record.idtipoejg,jdbcType=DECIMAL}");
        sql.SET("ANIO = #{record.anio,jdbcType=DECIMAL}");
        sql.SET("NUMERO = #{record.numero,jdbcType=DECIMAL}");
        sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
        sql.SET("SOLICITANTE = #{record.solicitante,jdbcType=DECIMAL}");
        sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        sql.SET("OBSERVACIONES = #{record.observaciones,jdbcType=VARCHAR}");
        sql.SET("ENCALIDADDE = #{record.encalidadde,jdbcType=VARCHAR}");
        sql.SET("BIENESINMUEBLES = #{record.bienesinmuebles,jdbcType=VARCHAR}");
        sql.SET("IMPORTEBIENESINMUEBLES = #{record.importebienesinmuebles,jdbcType=DECIMAL}");
        sql.SET("BIENESMUEBLES = #{record.bienesmuebles,jdbcType=VARCHAR}");
        sql.SET("IMPORTEBIENESMUEBLES = #{record.importebienesmuebles,jdbcType=DECIMAL}");
        sql.SET("OTROSBIENES = #{record.otrosbienes,jdbcType=VARCHAR}");
        sql.SET("DESCRIPCIONINGRESOSANUALES = #{record.descripcioningresosanuales,jdbcType=VARCHAR}");
        sql.SET("IMPORTEINGRESOSANUALES = #{record.importeingresosanuales,jdbcType=DECIMAL}");
        sql.SET("IMPORTEOTROSBIENES = #{record.importeotrosbienes,jdbcType=DECIMAL}");
        sql.SET("IDTIPOGRUPOLAB = #{record.idtipogrupolab,jdbcType=DECIMAL}");
        sql.SET("IDPARENTESCO = #{record.idparentesco,jdbcType=DECIMAL}");
        sql.SET("IDTIPOINGRESO = #{record.idtipoingreso,jdbcType=DECIMAL}");
        sql.SET("INCAPACITADO = #{record.incapacitado,jdbcType=DECIMAL}");
        sql.SET("CIRCUNSTANCIAS_EXCEPCIONALES = #{record.circunstanciasExcepcionales,jdbcType=DECIMAL}");
        sql.SET("FECHABAJA = #{record.fechabaja,jdbcType=TIMESTAMP}");
        
        ScsUnidadfamiliarejgExample example = (ScsUnidadfamiliarejgExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_UNIDADFAMILIAREJG
     *
     * @mbg.generated Tue May 11 13:11:04 CEST 2021
     */
    public String updateByPrimaryKeySelective(ScsUnidadfamiliarejg record) {
        SQL sql = new SQL();
        sql.UPDATE("SCS_UNIDADFAMILIAREJG");
        
        if (record.getSolicitante() != null) {
            sql.SET("SOLICITANTE = #{solicitante,jdbcType=DECIMAL}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getObservaciones() != null) {
            sql.SET("OBSERVACIONES = #{observaciones,jdbcType=VARCHAR}");
        }
        
        if (record.getEncalidadde() != null) {
            sql.SET("ENCALIDADDE = #{encalidadde,jdbcType=VARCHAR}");
        }
        
        if (record.getBienesinmuebles() != null) {
            sql.SET("BIENESINMUEBLES = #{bienesinmuebles,jdbcType=VARCHAR}");
        }
        
        if (record.getImportebienesinmuebles() != null) {
            sql.SET("IMPORTEBIENESINMUEBLES = #{importebienesinmuebles,jdbcType=DECIMAL}");
        }
        
        if (record.getBienesmuebles() != null) {
            sql.SET("BIENESMUEBLES = #{bienesmuebles,jdbcType=VARCHAR}");
        }
        
        if (record.getImportebienesmuebles() != null) {
            sql.SET("IMPORTEBIENESMUEBLES = #{importebienesmuebles,jdbcType=DECIMAL}");
        }
        
        if (record.getOtrosbienes() != null) {
            sql.SET("OTROSBIENES = #{otrosbienes,jdbcType=VARCHAR}");
        }
        
        if (record.getDescripcioningresosanuales() != null) {
            sql.SET("DESCRIPCIONINGRESOSANUALES = #{descripcioningresosanuales,jdbcType=VARCHAR}");
        }
        
        if (record.getImporteingresosanuales() != null) {
            sql.SET("IMPORTEINGRESOSANUALES = #{importeingresosanuales,jdbcType=DECIMAL}");
        }
        
        if (record.getImporteotrosbienes() != null) {
            sql.SET("IMPORTEOTROSBIENES = #{importeotrosbienes,jdbcType=DECIMAL}");
        }
        
        if (record.getIdtipogrupolab() != null) {
            sql.SET("IDTIPOGRUPOLAB = #{idtipogrupolab,jdbcType=DECIMAL}");
        }
        
        if (record.getIdparentesco() != null) {
            sql.SET("IDPARENTESCO = #{idparentesco,jdbcType=DECIMAL}");
        }
        
        if (record.getIdtipoingreso() != null) {
            sql.SET("IDTIPOINGRESO = #{idtipoingreso,jdbcType=DECIMAL}");
        }
        
        if (record.getIncapacitado() != null) {
            sql.SET("INCAPACITADO = #{incapacitado,jdbcType=DECIMAL}");
        }
        
        if (record.getCircunstanciasExcepcionales() != null) {
            sql.SET("CIRCUNSTANCIAS_EXCEPCIONALES = #{circunstanciasExcepcionales,jdbcType=DECIMAL}");
        }
        
        if (record.getFechabaja() != null) {
            sql.SET("FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP}");
        }
        
        sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
        sql.WHERE("IDTIPOEJG = #{idtipoejg,jdbcType=DECIMAL}");
        sql.WHERE("ANIO = #{anio,jdbcType=DECIMAL}");
        sql.WHERE("NUMERO = #{numero,jdbcType=DECIMAL}");
        sql.WHERE("IDPERSONA = #{idpersona,jdbcType=DECIMAL}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_UNIDADFAMILIAREJG
     *
     * @mbg.generated Tue May 11 13:11:04 CEST 2021
     */
    protected void applyWhere(SQL sql, ScsUnidadfamiliarejgExample example, boolean includeExamplePhrase) {
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