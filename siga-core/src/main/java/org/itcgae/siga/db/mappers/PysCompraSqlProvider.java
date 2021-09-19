package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.PysCompra;
import org.itcgae.siga.db.entities.PysCompraExample.Criteria;
import org.itcgae.siga.db.entities.PysCompraExample.Criterion;
import org.itcgae.siga.db.entities.PysCompraExample;

public class PysCompraSqlProvider {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_COMPRA
     *
     * @mbg.generated Fri Sep 17 11:13:44 CEST 2021
     */
    public String countByExample(PysCompraExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("PYS_COMPRA");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_COMPRA
     *
     * @mbg.generated Fri Sep 17 11:13:44 CEST 2021
     */
    public String deleteByExample(PysCompraExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("PYS_COMPRA");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_COMPRA
     *
     * @mbg.generated Fri Sep 17 11:13:44 CEST 2021
     */
    public String insertSelective(PysCompra record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("PYS_COMPRA");
        
        if (record.getIdinstitucion() != null) {
            sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdpeticion() != null) {
            sql.VALUES("IDPETICION", "#{idpeticion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdproducto() != null) {
            sql.VALUES("IDPRODUCTO", "#{idproducto,jdbcType=DECIMAL}");
        }
        
        if (record.getIdtipoproducto() != null) {
            sql.VALUES("IDTIPOPRODUCTO", "#{idtipoproducto,jdbcType=DECIMAL}");
        }
        
        if (record.getIdproductoinstitucion() != null) {
            sql.VALUES("IDPRODUCTOINSTITUCION", "#{idproductoinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getFecha() != null) {
            sql.VALUES("FECHA", "#{fecha,jdbcType=DATE}");
        }
        
        if (record.getCantidad() != null) {
            sql.VALUES("CANTIDAD", "#{cantidad,jdbcType=DECIMAL}");
        }
        
        if (record.getImporteunitario() != null) {
            sql.VALUES("IMPORTEUNITARIO", "#{importeunitario,jdbcType=DECIMAL}");
        }
        
        if (record.getIdformapago() != null) {
            sql.VALUES("IDFORMAPAGO", "#{idformapago,jdbcType=DECIMAL}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=DATE}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdpersona() != null) {
            sql.VALUES("IDPERSONA", "#{idpersona,jdbcType=DECIMAL}");
        }
        
        if (record.getDescripcion() != null) {
            sql.VALUES("DESCRIPCION", "#{descripcion,jdbcType=VARCHAR}");
        }
        
        if (record.getImporteanticipado() != null) {
            sql.VALUES("IMPORTEANTICIPADO", "#{importeanticipado,jdbcType=DECIMAL}");
        }
        
        if (record.getAceptado() != null) {
            sql.VALUES("ACEPTADO", "#{aceptado,jdbcType=VARCHAR}");
        }
        
        if (record.getNumerolinea() != null) {
            sql.VALUES("NUMEROLINEA", "#{numerolinea,jdbcType=DECIMAL}");
        }
        
        if (record.getIdfactura() != null) {
            sql.VALUES("IDFACTURA", "#{idfactura,jdbcType=VARCHAR}");
        }
        
        if (record.getFechabaja() != null) {
            sql.VALUES("FECHABAJA", "#{fechabaja,jdbcType=DATE}");
        }
        
        if (record.getIdcuenta() != null) {
            sql.VALUES("IDCUENTA", "#{idcuenta,jdbcType=DECIMAL}");
        }
        
        if (record.getIdpersonadeudor() != null) {
            sql.VALUES("IDPERSONADEUDOR", "#{idpersonadeudor,jdbcType=DECIMAL}");
        }
        
        if (record.getIdcuentadeudor() != null) {
            sql.VALUES("IDCUENTADEUDOR", "#{idcuentadeudor,jdbcType=DECIMAL}");
        }
        
        if (record.getNofacturable() != null) {
            sql.VALUES("NOFACTURABLE", "#{nofacturable,jdbcType=VARCHAR}");
        }
        
        if (record.getIdtipoiva() != null) {
            sql.VALUES("IDTIPOIVA", "#{idtipoiva,jdbcType=DECIMAL}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_COMPRA
     *
     * @mbg.generated Fri Sep 17 11:13:44 CEST 2021
     */
    public String selectByExample(PysCompraExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("IDINSTITUCION");
        } else {
            sql.SELECT("IDINSTITUCION");
        }
        sql.SELECT("IDPETICION");
        sql.SELECT("IDPRODUCTO");
        sql.SELECT("IDTIPOPRODUCTO");
        sql.SELECT("IDPRODUCTOINSTITUCION");
        sql.SELECT("FECHA");
        sql.SELECT("CANTIDAD");
        sql.SELECT("IMPORTEUNITARIO");
        sql.SELECT("IDFORMAPAGO");
        sql.SELECT("FECHAMODIFICACION");
        sql.SELECT("USUMODIFICACION");
        sql.SELECT("IDPERSONA");
        sql.SELECT("DESCRIPCION");
        sql.SELECT("IMPORTEANTICIPADO");
        sql.SELECT("ACEPTADO");
        sql.SELECT("NUMEROLINEA");
        sql.SELECT("IDFACTURA");
        sql.SELECT("FECHABAJA");
        sql.SELECT("IDCUENTA");
        sql.SELECT("IDPERSONADEUDOR");
        sql.SELECT("IDCUENTADEUDOR");
        sql.SELECT("NOFACTURABLE");
        sql.SELECT("IDTIPOIVA");
        sql.FROM("PYS_COMPRA");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_COMPRA
     *
     * @mbg.generated Fri Sep 17 11:13:44 CEST 2021
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        PysCompra record = (PysCompra) parameter.get("record");
        PysCompraExample example = (PysCompraExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("PYS_COMPRA");
        
        if (record.getIdinstitucion() != null) {
            sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdpeticion() != null) {
            sql.SET("IDPETICION = #{record.idpeticion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdproducto() != null) {
            sql.SET("IDPRODUCTO = #{record.idproducto,jdbcType=DECIMAL}");
        }
        
        if (record.getIdtipoproducto() != null) {
            sql.SET("IDTIPOPRODUCTO = #{record.idtipoproducto,jdbcType=DECIMAL}");
        }
        
        if (record.getIdproductoinstitucion() != null) {
            sql.SET("IDPRODUCTOINSTITUCION = #{record.idproductoinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getFecha() != null) {
            sql.SET("FECHA = #{record.fecha,jdbcType=DATE}");
        }
        
        if (record.getCantidad() != null) {
            sql.SET("CANTIDAD = #{record.cantidad,jdbcType=DECIMAL}");
        }
        
        if (record.getImporteunitario() != null) {
            sql.SET("IMPORTEUNITARIO = #{record.importeunitario,jdbcType=DECIMAL}");
        }
        
        if (record.getIdformapago() != null) {
            sql.SET("IDFORMAPAGO = #{record.idformapago,jdbcType=DECIMAL}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=DATE}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdpersona() != null) {
            sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
        }
        
        if (record.getDescripcion() != null) {
            sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
        }
        
        if (record.getImporteanticipado() != null) {
            sql.SET("IMPORTEANTICIPADO = #{record.importeanticipado,jdbcType=DECIMAL}");
        }
        
        if (record.getAceptado() != null) {
            sql.SET("ACEPTADO = #{record.aceptado,jdbcType=VARCHAR}");
        }
        
        if (record.getNumerolinea() != null) {
            sql.SET("NUMEROLINEA = #{record.numerolinea,jdbcType=DECIMAL}");
        }
        
        if (record.getIdfactura() != null) {
            sql.SET("IDFACTURA = #{record.idfactura,jdbcType=VARCHAR}");
        }
        
        if (record.getFechabaja() != null) {
            sql.SET("FECHABAJA = #{record.fechabaja,jdbcType=DATE}");
        }
        
        if (record.getIdcuenta() != null) {
            sql.SET("IDCUENTA = #{record.idcuenta,jdbcType=DECIMAL}");
        }
        
        if (record.getIdpersonadeudor() != null) {
            sql.SET("IDPERSONADEUDOR = #{record.idpersonadeudor,jdbcType=DECIMAL}");
        }
        
        if (record.getIdcuentadeudor() != null) {
            sql.SET("IDCUENTADEUDOR = #{record.idcuentadeudor,jdbcType=DECIMAL}");
        }
        
        if (record.getNofacturable() != null) {
            sql.SET("NOFACTURABLE = #{record.nofacturable,jdbcType=VARCHAR}");
        }
        
        if (record.getIdtipoiva() != null) {
            sql.SET("IDTIPOIVA = #{record.idtipoiva,jdbcType=DECIMAL}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_COMPRA
     *
     * @mbg.generated Fri Sep 17 11:13:44 CEST 2021
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("PYS_COMPRA");
        
        sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        sql.SET("IDPETICION = #{record.idpeticion,jdbcType=DECIMAL}");
        sql.SET("IDPRODUCTO = #{record.idproducto,jdbcType=DECIMAL}");
        sql.SET("IDTIPOPRODUCTO = #{record.idtipoproducto,jdbcType=DECIMAL}");
        sql.SET("IDPRODUCTOINSTITUCION = #{record.idproductoinstitucion,jdbcType=DECIMAL}");
        sql.SET("FECHA = #{record.fecha,jdbcType=DATE}");
        sql.SET("CANTIDAD = #{record.cantidad,jdbcType=DECIMAL}");
        sql.SET("IMPORTEUNITARIO = #{record.importeunitario,jdbcType=DECIMAL}");
        sql.SET("IDFORMAPAGO = #{record.idformapago,jdbcType=DECIMAL}");
        sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=DATE}");
        sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
        sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
        sql.SET("IMPORTEANTICIPADO = #{record.importeanticipado,jdbcType=DECIMAL}");
        sql.SET("ACEPTADO = #{record.aceptado,jdbcType=VARCHAR}");
        sql.SET("NUMEROLINEA = #{record.numerolinea,jdbcType=DECIMAL}");
        sql.SET("IDFACTURA = #{record.idfactura,jdbcType=VARCHAR}");
        sql.SET("FECHABAJA = #{record.fechabaja,jdbcType=DATE}");
        sql.SET("IDCUENTA = #{record.idcuenta,jdbcType=DECIMAL}");
        sql.SET("IDPERSONADEUDOR = #{record.idpersonadeudor,jdbcType=DECIMAL}");
        sql.SET("IDCUENTADEUDOR = #{record.idcuentadeudor,jdbcType=DECIMAL}");
        sql.SET("NOFACTURABLE = #{record.nofacturable,jdbcType=VARCHAR}");
        sql.SET("IDTIPOIVA = #{record.idtipoiva,jdbcType=DECIMAL}");
        
        PysCompraExample example = (PysCompraExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_COMPRA
     *
     * @mbg.generated Fri Sep 17 11:13:44 CEST 2021
     */
    public String updateByPrimaryKeySelective(PysCompra record) {
        SQL sql = new SQL();
        sql.UPDATE("PYS_COMPRA");
        
        if (record.getFecha() != null) {
            sql.SET("FECHA = #{fecha,jdbcType=DATE}");
        }
        
        if (record.getCantidad() != null) {
            sql.SET("CANTIDAD = #{cantidad,jdbcType=DECIMAL}");
        }
        
        if (record.getImporteunitario() != null) {
            sql.SET("IMPORTEUNITARIO = #{importeunitario,jdbcType=DECIMAL}");
        }
        
        if (record.getIdformapago() != null) {
            sql.SET("IDFORMAPAGO = #{idformapago,jdbcType=DECIMAL}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=DATE}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdpersona() != null) {
            sql.SET("IDPERSONA = #{idpersona,jdbcType=DECIMAL}");
        }
        
        if (record.getDescripcion() != null) {
            sql.SET("DESCRIPCION = #{descripcion,jdbcType=VARCHAR}");
        }
        
        if (record.getImporteanticipado() != null) {
            sql.SET("IMPORTEANTICIPADO = #{importeanticipado,jdbcType=DECIMAL}");
        }
        
        if (record.getAceptado() != null) {
            sql.SET("ACEPTADO = #{aceptado,jdbcType=VARCHAR}");
        }
        
        if (record.getNumerolinea() != null) {
            sql.SET("NUMEROLINEA = #{numerolinea,jdbcType=DECIMAL}");
        }
        
        if (record.getIdfactura() != null) {
            sql.SET("IDFACTURA = #{idfactura,jdbcType=VARCHAR}");
        }
        
        if (record.getFechabaja() != null) {
            sql.SET("FECHABAJA = #{fechabaja,jdbcType=DATE}");
        }
        
        if (record.getIdcuenta() != null) {
            sql.SET("IDCUENTA = #{idcuenta,jdbcType=DECIMAL}");
        }
        
        if (record.getIdpersonadeudor() != null) {
            sql.SET("IDPERSONADEUDOR = #{idpersonadeudor,jdbcType=DECIMAL}");
        }
        
        if (record.getIdcuentadeudor() != null) {
            sql.SET("IDCUENTADEUDOR = #{idcuentadeudor,jdbcType=DECIMAL}");
        }
        
        if (record.getNofacturable() != null) {
            sql.SET("NOFACTURABLE = #{nofacturable,jdbcType=VARCHAR}");
        }
        
        if (record.getIdtipoiva() != null) {
            sql.SET("IDTIPOIVA = #{idtipoiva,jdbcType=DECIMAL}");
        }
        
        sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
        sql.WHERE("IDPETICION = #{idpeticion,jdbcType=DECIMAL}");
        sql.WHERE("IDPRODUCTO = #{idproducto,jdbcType=DECIMAL}");
        sql.WHERE("IDTIPOPRODUCTO = #{idtipoproducto,jdbcType=DECIMAL}");
        sql.WHERE("IDPRODUCTOINSTITUCION = #{idproductoinstitucion,jdbcType=DECIMAL}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.PYS_COMPRA
     *
     * @mbg.generated Fri Sep 17 11:13:44 CEST 2021
     */
    protected void applyWhere(SQL sql, PysCompraExample example, boolean includeExamplePhrase) {
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