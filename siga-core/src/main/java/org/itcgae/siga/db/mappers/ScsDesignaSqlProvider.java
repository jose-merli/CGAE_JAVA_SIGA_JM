package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.ScsDesigna;
import org.itcgae.siga.db.entities.ScsDesignaExample.Criteria;
import org.itcgae.siga.db.entities.ScsDesignaExample.Criterion;
import org.itcgae.siga.db.entities.ScsDesignaExample;

public class ScsDesignaSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DESIGNA
     *
     * @mbg.generated Wed Dec 04 16:45:33 CET 2019
     */
    public String countByExample(ScsDesignaExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("SCS_DESIGNA");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DESIGNA
     *
     * @mbg.generated Wed Dec 04 16:45:33 CET 2019
     */
    public String deleteByExample(ScsDesignaExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("SCS_DESIGNA");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DESIGNA
     *
     * @mbg.generated Wed Dec 04 16:45:33 CET 2019
     */
    public String insertSelective(ScsDesigna record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("SCS_DESIGNA");
        
        if (record.getIdinstitucion() != null) {
            sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdturno() != null) {
            sql.VALUES("IDTURNO", "#{idturno,jdbcType=DECIMAL}");
        }
        
        if (record.getAnio() != null) {
            sql.VALUES("ANIO", "#{anio,jdbcType=DECIMAL}");
        }
        
        if (record.getNumero() != null) {
            sql.VALUES("NUMERO", "#{numero,jdbcType=DECIMAL}");
        }
        
        if (record.getFechaentrada() != null) {
            sql.VALUES("FECHAENTRADA", "#{fechaentrada,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getEstado() != null) {
            sql.VALUES("ESTADO", "#{estado,jdbcType=VARCHAR}");
        }
        
        if (record.getFechafin() != null) {
            sql.VALUES("FECHAFIN", "#{fechafin,jdbcType=TIMESTAMP}");
        }
        
        if (record.getResumenasunto() != null) {
            sql.VALUES("RESUMENASUNTO", "#{resumenasunto,jdbcType=VARCHAR}");
        }
        
        if (record.getDelitos() != null) {
            sql.VALUES("DELITOS", "#{delitos,jdbcType=VARCHAR}");
        }
        
        if (record.getProcurador() != null) {
            sql.VALUES("PROCURADOR", "#{procurador,jdbcType=VARCHAR}");
        }
        
        if (record.getIdtipodesignacolegio() != null) {
            sql.VALUES("IDTIPODESIGNACOLEGIO", "#{idtipodesignacolegio,jdbcType=DECIMAL}");
        }
        
        if (record.getObservaciones() != null) {
            sql.VALUES("OBSERVACIONES", "#{observaciones,jdbcType=VARCHAR}");
        }
        
        if (record.getDefensajuridica() != null) {
            sql.VALUES("DEFENSAJURIDICA", "#{defensajuridica,jdbcType=VARCHAR}");
        }
        
        if (record.getIdprocurador() != null) {
            sql.VALUES("IDPROCURADOR", "#{idprocurador,jdbcType=DECIMAL}");
        }
        
        if (record.getIdinstitucionProcur() != null) {
            sql.VALUES("IDINSTITUCION_PROCUR", "#{idinstitucionProcur,jdbcType=DECIMAL}");
        }
        
        if (record.getIdjuzgado() != null) {
            sql.VALUES("IDJUZGADO", "#{idjuzgado,jdbcType=DECIMAL}");
        }
        
        if (record.getIdinstitucionJuzg() != null) {
            sql.VALUES("IDINSTITUCION_JUZG", "#{idinstitucionJuzg,jdbcType=DECIMAL}");
        }
        
        if (record.getFechaanulacion() != null) {
            sql.VALUES("FECHAANULACION", "#{fechaanulacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCodigo() != null) {
            sql.VALUES("CODIGO", "#{codigo,jdbcType=VARCHAR}");
        }
        
        if (record.getNumprocedimiento() != null) {
            sql.VALUES("NUMPROCEDIMIENTO", "#{numprocedimiento,jdbcType=VARCHAR}");
        }
        
        if (record.getFechajuicio() != null) {
            sql.VALUES("FECHAJUICIO", "#{fechajuicio,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIdprocedimiento() != null) {
            sql.VALUES("IDPROCEDIMIENTO", "#{idprocedimiento,jdbcType=VARCHAR}");
        }
        
        if (record.getFechaestado() != null) {
            sql.VALUES("FECHAESTADO", "#{fechaestado,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIdpretension() != null) {
            sql.VALUES("IDPRETENSION", "#{idpretension,jdbcType=DECIMAL}");
        }
        
        if (record.getSufijo() != null) {
            sql.VALUES("SUFIJO", "#{sufijo,jdbcType=VARCHAR}");
        }
        
        if (record.getFechaoficiojuzgado() != null) {
            sql.VALUES("FECHAOFICIOJUZGADO", "#{fechaoficiojuzgado,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFecharecepcioncolegio() != null) {
            sql.VALUES("FECHARECEPCIONCOLEGIO", "#{fecharecepcioncolegio,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechaalta() != null) {
            sql.VALUES("FECHAALTA", "#{fechaalta,jdbcType=TIMESTAMP}");
        }
        
        if (record.getArt27() != null) {
            sql.VALUES("ART27", "#{art27,jdbcType=VARCHAR}");
        }
        
        if (record.getNig() != null) {
            sql.VALUES("NIG", "#{nig,jdbcType=VARCHAR}");
        }
        
        if (record.getAnioprocedimiento() != null) {
            sql.VALUES("ANIOPROCEDIMIENTO", "#{anioprocedimiento,jdbcType=DECIMAL}");
        }
        
        if (record.getFactconvenio() != null) {
            sql.VALUES("FACTCONVENIO", "#{factconvenio,jdbcType=DECIMAL}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DESIGNA
     *
     * @mbg.generated Wed Dec 04 16:45:33 CET 2019
     */
    public String selectByExample(ScsDesignaExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("IDINSTITUCION");
        } else {
            sql.SELECT("IDINSTITUCION");
        }
        sql.SELECT("IDTURNO");
        sql.SELECT("ANIO");
        sql.SELECT("NUMERO");
        sql.SELECT("FECHAENTRADA");
        sql.SELECT("FECHAMODIFICACION");
        sql.SELECT("USUMODIFICACION");
        sql.SELECT("ESTADO");
        sql.SELECT("FECHAFIN");
        sql.SELECT("RESUMENASUNTO");
        sql.SELECT("DELITOS");
        sql.SELECT("PROCURADOR");
        sql.SELECT("IDTIPODESIGNACOLEGIO");
        sql.SELECT("OBSERVACIONES");
        sql.SELECT("DEFENSAJURIDICA");
        sql.SELECT("IDPROCURADOR");
        sql.SELECT("IDINSTITUCION_PROCUR");
        sql.SELECT("IDJUZGADO");
        sql.SELECT("IDINSTITUCION_JUZG");
        sql.SELECT("FECHAANULACION");
        sql.SELECT("CODIGO");
        sql.SELECT("NUMPROCEDIMIENTO");
        sql.SELECT("FECHAJUICIO");
        sql.SELECT("IDPROCEDIMIENTO");
        sql.SELECT("FECHAESTADO");
        sql.SELECT("IDPRETENSION");
        sql.SELECT("SUFIJO");
        sql.SELECT("FECHAOFICIOJUZGADO");
        sql.SELECT("FECHARECEPCIONCOLEGIO");
        sql.SELECT("FECHAALTA");
        sql.SELECT("ART27");
        sql.SELECT("NIG");
        sql.SELECT("ANIOPROCEDIMIENTO");
        sql.SELECT("FACTCONVENIO");
        sql.FROM("SCS_DESIGNA");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DESIGNA
     *
     * @mbg.generated Wed Dec 04 16:45:33 CET 2019
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        ScsDesigna record = (ScsDesigna) parameter.get("record");
        ScsDesignaExample example = (ScsDesignaExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("SCS_DESIGNA");
        
        if (record.getIdinstitucion() != null) {
            sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        }
        
        if (record.getIdturno() != null) {
            sql.SET("IDTURNO = #{record.idturno,jdbcType=DECIMAL}");
        }
        
        if (record.getAnio() != null) {
            sql.SET("ANIO = #{record.anio,jdbcType=DECIMAL}");
        }
        
        if (record.getNumero() != null) {
            sql.SET("NUMERO = #{record.numero,jdbcType=DECIMAL}");
        }
        
        if (record.getFechaentrada() != null) {
            sql.SET("FECHAENTRADA = #{record.fechaentrada,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getEstado() != null) {
            sql.SET("ESTADO = #{record.estado,jdbcType=VARCHAR}");
        }
        
        if (record.getFechafin() != null) {
            sql.SET("FECHAFIN = #{record.fechafin,jdbcType=TIMESTAMP}");
        }
        
        if (record.getResumenasunto() != null) {
            sql.SET("RESUMENASUNTO = #{record.resumenasunto,jdbcType=VARCHAR}");
        }
        
        if (record.getDelitos() != null) {
            sql.SET("DELITOS = #{record.delitos,jdbcType=VARCHAR}");
        }
        
        if (record.getProcurador() != null) {
            sql.SET("PROCURADOR = #{record.procurador,jdbcType=VARCHAR}");
        }
        
        if (record.getIdtipodesignacolegio() != null) {
            sql.SET("IDTIPODESIGNACOLEGIO = #{record.idtipodesignacolegio,jdbcType=DECIMAL}");
        }
        
        if (record.getObservaciones() != null) {
            sql.SET("OBSERVACIONES = #{record.observaciones,jdbcType=VARCHAR}");
        }
        
        if (record.getDefensajuridica() != null) {
            sql.SET("DEFENSAJURIDICA = #{record.defensajuridica,jdbcType=VARCHAR}");
        }
        
        if (record.getIdprocurador() != null) {
            sql.SET("IDPROCURADOR = #{record.idprocurador,jdbcType=DECIMAL}");
        }
        
        if (record.getIdinstitucionProcur() != null) {
            sql.SET("IDINSTITUCION_PROCUR = #{record.idinstitucionProcur,jdbcType=DECIMAL}");
        }
        
        if (record.getIdjuzgado() != null) {
            sql.SET("IDJUZGADO = #{record.idjuzgado,jdbcType=DECIMAL}");
        }
        
        if (record.getIdinstitucionJuzg() != null) {
            sql.SET("IDINSTITUCION_JUZG = #{record.idinstitucionJuzg,jdbcType=DECIMAL}");
        }
        
        if (record.getFechaanulacion() != null) {
            sql.SET("FECHAANULACION = #{record.fechaanulacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCodigo() != null) {
            sql.SET("CODIGO = #{record.codigo,jdbcType=VARCHAR}");
        }
        
        if (record.getNumprocedimiento() != null) {
            sql.SET("NUMPROCEDIMIENTO = #{record.numprocedimiento,jdbcType=VARCHAR}");
        }
        
        if (record.getFechajuicio() != null) {
            sql.SET("FECHAJUICIO = #{record.fechajuicio,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIdprocedimiento() != null) {
            sql.SET("IDPROCEDIMIENTO = #{record.idprocedimiento,jdbcType=VARCHAR}");
        }
        
        if (record.getFechaestado() != null) {
            sql.SET("FECHAESTADO = #{record.fechaestado,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIdpretension() != null) {
            sql.SET("IDPRETENSION = #{record.idpretension,jdbcType=DECIMAL}");
        }
        
        if (record.getSufijo() != null) {
            sql.SET("SUFIJO = #{record.sufijo,jdbcType=VARCHAR}");
        }
        
        if (record.getFechaoficiojuzgado() != null) {
            sql.SET("FECHAOFICIOJUZGADO = #{record.fechaoficiojuzgado,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFecharecepcioncolegio() != null) {
            sql.SET("FECHARECEPCIONCOLEGIO = #{record.fecharecepcioncolegio,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechaalta() != null) {
            sql.SET("FECHAALTA = #{record.fechaalta,jdbcType=TIMESTAMP}");
        }
        
        if (record.getArt27() != null) {
            sql.SET("ART27 = #{record.art27,jdbcType=VARCHAR}");
        }
        
        if (record.getNig() != null) {
            sql.SET("NIG = #{record.nig,jdbcType=VARCHAR}");
        }
        
        if (record.getAnioprocedimiento() != null) {
            sql.SET("ANIOPROCEDIMIENTO = #{record.anioprocedimiento,jdbcType=DECIMAL}");
        }
        
        if (record.getFactconvenio() != null) {
            sql.SET("FACTCONVENIO = #{record.factconvenio,jdbcType=DECIMAL}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DESIGNA
     *
     * @mbg.generated Wed Dec 04 16:45:33 CET 2019
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("SCS_DESIGNA");
        
        sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
        sql.SET("IDTURNO = #{record.idturno,jdbcType=DECIMAL}");
        sql.SET("ANIO = #{record.anio,jdbcType=DECIMAL}");
        sql.SET("NUMERO = #{record.numero,jdbcType=DECIMAL}");
        sql.SET("FECHAENTRADA = #{record.fechaentrada,jdbcType=TIMESTAMP}");
        sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
        sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
        sql.SET("ESTADO = #{record.estado,jdbcType=VARCHAR}");
        sql.SET("FECHAFIN = #{record.fechafin,jdbcType=TIMESTAMP}");
        sql.SET("RESUMENASUNTO = #{record.resumenasunto,jdbcType=VARCHAR}");
        sql.SET("DELITOS = #{record.delitos,jdbcType=VARCHAR}");
        sql.SET("PROCURADOR = #{record.procurador,jdbcType=VARCHAR}");
        sql.SET("IDTIPODESIGNACOLEGIO = #{record.idtipodesignacolegio,jdbcType=DECIMAL}");
        sql.SET("OBSERVACIONES = #{record.observaciones,jdbcType=VARCHAR}");
        sql.SET("DEFENSAJURIDICA = #{record.defensajuridica,jdbcType=VARCHAR}");
        sql.SET("IDPROCURADOR = #{record.idprocurador,jdbcType=DECIMAL}");
        sql.SET("IDINSTITUCION_PROCUR = #{record.idinstitucionProcur,jdbcType=DECIMAL}");
        sql.SET("IDJUZGADO = #{record.idjuzgado,jdbcType=DECIMAL}");
        sql.SET("IDINSTITUCION_JUZG = #{record.idinstitucionJuzg,jdbcType=DECIMAL}");
        sql.SET("FECHAANULACION = #{record.fechaanulacion,jdbcType=TIMESTAMP}");
        sql.SET("CODIGO = #{record.codigo,jdbcType=VARCHAR}");
        sql.SET("NUMPROCEDIMIENTO = #{record.numprocedimiento,jdbcType=VARCHAR}");
        sql.SET("FECHAJUICIO = #{record.fechajuicio,jdbcType=TIMESTAMP}");
        sql.SET("IDPROCEDIMIENTO = #{record.idprocedimiento,jdbcType=VARCHAR}");
        sql.SET("FECHAESTADO = #{record.fechaestado,jdbcType=TIMESTAMP}");
        sql.SET("IDPRETENSION = #{record.idpretension,jdbcType=DECIMAL}");
        sql.SET("SUFIJO = #{record.sufijo,jdbcType=VARCHAR}");
        sql.SET("FECHAOFICIOJUZGADO = #{record.fechaoficiojuzgado,jdbcType=TIMESTAMP}");
        sql.SET("FECHARECEPCIONCOLEGIO = #{record.fecharecepcioncolegio,jdbcType=TIMESTAMP}");
        sql.SET("FECHAALTA = #{record.fechaalta,jdbcType=TIMESTAMP}");
        sql.SET("ART27 = #{record.art27,jdbcType=VARCHAR}");
        sql.SET("NIG = #{record.nig,jdbcType=VARCHAR}");
        sql.SET("ANIOPROCEDIMIENTO = #{record.anioprocedimiento,jdbcType=DECIMAL}");
        sql.SET("FACTCONVENIO = #{record.factconvenio,jdbcType=DECIMAL}");
        
        ScsDesignaExample example = (ScsDesignaExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DESIGNA
     *
     * @mbg.generated Wed Dec 04 16:45:33 CET 2019
     */
    public String updateByPrimaryKeySelective(ScsDesigna record) {
        SQL sql = new SQL();
        sql.UPDATE("SCS_DESIGNA");
        
        if (record.getFechaentrada() != null) {
            sql.SET("FECHAENTRADA = #{fechaentrada,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechamodificacion() != null) {
            sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUsumodificacion() != null) {
            sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
        }
        
        if (record.getEstado() != null) {
            sql.SET("ESTADO = #{estado,jdbcType=VARCHAR}");
        }
        
        if (record.getFechafin() != null) {
            sql.SET("FECHAFIN = #{fechafin,jdbcType=TIMESTAMP}");
        }
        
        if (record.getResumenasunto() != null) {
            sql.SET("RESUMENASUNTO = #{resumenasunto,jdbcType=VARCHAR}");
        }
        
        if (record.getDelitos() != null) {
            sql.SET("DELITOS = #{delitos,jdbcType=VARCHAR}");
        }
        
        if (record.getProcurador() != null) {
            sql.SET("PROCURADOR = #{procurador,jdbcType=VARCHAR}");
        }
        
        if (record.getIdtipodesignacolegio() != null) {
            sql.SET("IDTIPODESIGNACOLEGIO = #{idtipodesignacolegio,jdbcType=DECIMAL}");
        }
        
        if (record.getObservaciones() != null) {
            sql.SET("OBSERVACIONES = #{observaciones,jdbcType=VARCHAR}");
        }
        
        if (record.getDefensajuridica() != null) {
            sql.SET("DEFENSAJURIDICA = #{defensajuridica,jdbcType=VARCHAR}");
        }
        
        if (record.getIdprocurador() != null) {
            sql.SET("IDPROCURADOR = #{idprocurador,jdbcType=DECIMAL}");
        }
        
        if (record.getIdinstitucionProcur() != null) {
            sql.SET("IDINSTITUCION_PROCUR = #{idinstitucionProcur,jdbcType=DECIMAL}");
        }
        
        if (record.getIdjuzgado() != null) {
            sql.SET("IDJUZGADO = #{idjuzgado,jdbcType=DECIMAL}");
        }
        
        if (record.getIdinstitucionJuzg() != null) {
            sql.SET("IDINSTITUCION_JUZG = #{idinstitucionJuzg,jdbcType=DECIMAL}");
        }
        
        if (record.getFechaanulacion() != null) {
            sql.SET("FECHAANULACION = #{fechaanulacion,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCodigo() != null) {
            sql.SET("CODIGO = #{codigo,jdbcType=VARCHAR}");
        }
        
        if (record.getNumprocedimiento() != null) {
            sql.SET("NUMPROCEDIMIENTO = #{numprocedimiento,jdbcType=VARCHAR}");
        }
        
        if (record.getFechajuicio() != null) {
            sql.SET("FECHAJUICIO = #{fechajuicio,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIdprocedimiento() != null) {
            sql.SET("IDPROCEDIMIENTO = #{idprocedimiento,jdbcType=VARCHAR}");
        }
        
        if (record.getFechaestado() != null) {
            sql.SET("FECHAESTADO = #{fechaestado,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIdpretension() != null) {
            sql.SET("IDPRETENSION = #{idpretension,jdbcType=DECIMAL}");
        }
        
        if (record.getSufijo() != null) {
            sql.SET("SUFIJO = #{sufijo,jdbcType=VARCHAR}");
        }
        
        if (record.getFechaoficiojuzgado() != null) {
            sql.SET("FECHAOFICIOJUZGADO = #{fechaoficiojuzgado,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFecharecepcioncolegio() != null) {
            sql.SET("FECHARECEPCIONCOLEGIO = #{fecharecepcioncolegio,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFechaalta() != null) {
            sql.SET("FECHAALTA = #{fechaalta,jdbcType=TIMESTAMP}");
        }
        
        if (record.getArt27() != null) {
            sql.SET("ART27 = #{art27,jdbcType=VARCHAR}");
        }
        
        if (record.getNig() != null) {
            sql.SET("NIG = #{nig,jdbcType=VARCHAR}");
        }
        
        if (record.getAnioprocedimiento() != null) {
            sql.SET("ANIOPROCEDIMIENTO = #{anioprocedimiento,jdbcType=DECIMAL}");
        }
        
        if (record.getFactconvenio() != null) {
            sql.SET("FACTCONVENIO = #{factconvenio,jdbcType=DECIMAL}");
        }
        
        sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
        sql.WHERE("IDTURNO = #{idturno,jdbcType=DECIMAL}");
        sql.WHERE("ANIO = #{anio,jdbcType=DECIMAL}");
        sql.WHERE("NUMERO = #{numero,jdbcType=DECIMAL}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DESIGNA
     *
     * @mbg.generated Wed Dec 04 16:45:33 CET 2019
     */
    protected void applyWhere(SQL sql, ScsDesignaExample example, boolean includeExamplePhrase) {
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