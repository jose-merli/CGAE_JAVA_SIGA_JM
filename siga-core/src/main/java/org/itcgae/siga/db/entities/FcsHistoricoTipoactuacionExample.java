package org.itcgae.siga.db.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FcsHistoricoTipoactuacionExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.FCS_HISTORICO_TIPOACTUACION
     *
     * @mbg.generated Thu Dec 19 19:42:15 CET 2019
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.FCS_HISTORICO_TIPOACTUACION
     *
     * @mbg.generated Thu Dec 19 19:42:15 CET 2019
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.FCS_HISTORICO_TIPOACTUACION
     *
     * @mbg.generated Thu Dec 19 19:42:15 CET 2019
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_HISTORICO_TIPOACTUACION
     *
     * @mbg.generated Thu Dec 19 19:42:15 CET 2019
     */
    public FcsHistoricoTipoactuacionExample() {
        oredCriteria = new ArrayList<>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_HISTORICO_TIPOACTUACION
     *
     * @mbg.generated Thu Dec 19 19:42:15 CET 2019
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_HISTORICO_TIPOACTUACION
     *
     * @mbg.generated Thu Dec 19 19:42:15 CET 2019
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_HISTORICO_TIPOACTUACION
     *
     * @mbg.generated Thu Dec 19 19:42:15 CET 2019
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_HISTORICO_TIPOACTUACION
     *
     * @mbg.generated Thu Dec 19 19:42:15 CET 2019
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_HISTORICO_TIPOACTUACION
     *
     * @mbg.generated Thu Dec 19 19:42:15 CET 2019
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_HISTORICO_TIPOACTUACION
     *
     * @mbg.generated Thu Dec 19 19:42:15 CET 2019
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_HISTORICO_TIPOACTUACION
     *
     * @mbg.generated Thu Dec 19 19:42:15 CET 2019
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_HISTORICO_TIPOACTUACION
     *
     * @mbg.generated Thu Dec 19 19:42:15 CET 2019
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_HISTORICO_TIPOACTUACION
     *
     * @mbg.generated Thu Dec 19 19:42:15 CET 2019
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FCS_HISTORICO_TIPOACTUACION
     *
     * @mbg.generated Thu Dec 19 19:42:15 CET 2019
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.FCS_HISTORICO_TIPOACTUACION
     *
     * @mbg.generated Thu Dec 19 19:42:15 CET 2019
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdinstitucionIsNull() {
            addCriterion("IDINSTITUCION is null");
            return (Criteria) this;
        }

        public Criteria andIdinstitucionIsNotNull() {
            addCriterion("IDINSTITUCION is not null");
            return (Criteria) this;
        }

        public Criteria andIdinstitucionEqualTo(Short value) {
            addCriterion("IDINSTITUCION =", value, "idinstitucion");
            return (Criteria) this;
        }

        public Criteria andIdinstitucionNotEqualTo(Short value) {
            addCriterion("IDINSTITUCION <>", value, "idinstitucion");
            return (Criteria) this;
        }

        public Criteria andIdinstitucionGreaterThan(Short value) {
            addCriterion("IDINSTITUCION >", value, "idinstitucion");
            return (Criteria) this;
        }

        public Criteria andIdinstitucionGreaterThanOrEqualTo(Short value) {
            addCriterion("IDINSTITUCION >=", value, "idinstitucion");
            return (Criteria) this;
        }

        public Criteria andIdinstitucionLessThan(Short value) {
            addCriterion("IDINSTITUCION <", value, "idinstitucion");
            return (Criteria) this;
        }

        public Criteria andIdinstitucionLessThanOrEqualTo(Short value) {
            addCriterion("IDINSTITUCION <=", value, "idinstitucion");
            return (Criteria) this;
        }

        public Criteria andIdinstitucionIn(List<Short> values) {
            addCriterion("IDINSTITUCION in", values, "idinstitucion");
            return (Criteria) this;
        }

        public Criteria andIdinstitucionNotIn(List<Short> values) {
            addCriterion("IDINSTITUCION not in", values, "idinstitucion");
            return (Criteria) this;
        }

        public Criteria andIdinstitucionBetween(Short value1, Short value2) {
            addCriterion("IDINSTITUCION between", value1, value2, "idinstitucion");
            return (Criteria) this;
        }

        public Criteria andIdinstitucionNotBetween(Short value1, Short value2) {
            addCriterion("IDINSTITUCION not between", value1, value2, "idinstitucion");
            return (Criteria) this;
        }

        public Criteria andIdtipoasistenciaIsNull() {
            addCriterion("IDTIPOASISTENCIA is null");
            return (Criteria) this;
        }

        public Criteria andIdtipoasistenciaIsNotNull() {
            addCriterion("IDTIPOASISTENCIA is not null");
            return (Criteria) this;
        }

        public Criteria andIdtipoasistenciaEqualTo(Short value) {
            addCriterion("IDTIPOASISTENCIA =", value, "idtipoasistencia");
            return (Criteria) this;
        }

        public Criteria andIdtipoasistenciaNotEqualTo(Short value) {
            addCriterion("IDTIPOASISTENCIA <>", value, "idtipoasistencia");
            return (Criteria) this;
        }

        public Criteria andIdtipoasistenciaGreaterThan(Short value) {
            addCriterion("IDTIPOASISTENCIA >", value, "idtipoasistencia");
            return (Criteria) this;
        }

        public Criteria andIdtipoasistenciaGreaterThanOrEqualTo(Short value) {
            addCriterion("IDTIPOASISTENCIA >=", value, "idtipoasistencia");
            return (Criteria) this;
        }

        public Criteria andIdtipoasistenciaLessThan(Short value) {
            addCriterion("IDTIPOASISTENCIA <", value, "idtipoasistencia");
            return (Criteria) this;
        }

        public Criteria andIdtipoasistenciaLessThanOrEqualTo(Short value) {
            addCriterion("IDTIPOASISTENCIA <=", value, "idtipoasistencia");
            return (Criteria) this;
        }

        public Criteria andIdtipoasistenciaIn(List<Short> values) {
            addCriterion("IDTIPOASISTENCIA in", values, "idtipoasistencia");
            return (Criteria) this;
        }

        public Criteria andIdtipoasistenciaNotIn(List<Short> values) {
            addCriterion("IDTIPOASISTENCIA not in", values, "idtipoasistencia");
            return (Criteria) this;
        }

        public Criteria andIdtipoasistenciaBetween(Short value1, Short value2) {
            addCriterion("IDTIPOASISTENCIA between", value1, value2, "idtipoasistencia");
            return (Criteria) this;
        }

        public Criteria andIdtipoasistenciaNotBetween(Short value1, Short value2) {
            addCriterion("IDTIPOASISTENCIA not between", value1, value2, "idtipoasistencia");
            return (Criteria) this;
        }

        public Criteria andIdtipoactuacionIsNull() {
            addCriterion("IDTIPOACTUACION is null");
            return (Criteria) this;
        }

        public Criteria andIdtipoactuacionIsNotNull() {
            addCriterion("IDTIPOACTUACION is not null");
            return (Criteria) this;
        }

        public Criteria andIdtipoactuacionEqualTo(Short value) {
            addCriterion("IDTIPOACTUACION =", value, "idtipoactuacion");
            return (Criteria) this;
        }

        public Criteria andIdtipoactuacionNotEqualTo(Short value) {
            addCriterion("IDTIPOACTUACION <>", value, "idtipoactuacion");
            return (Criteria) this;
        }

        public Criteria andIdtipoactuacionGreaterThan(Short value) {
            addCriterion("IDTIPOACTUACION >", value, "idtipoactuacion");
            return (Criteria) this;
        }

        public Criteria andIdtipoactuacionGreaterThanOrEqualTo(Short value) {
            addCriterion("IDTIPOACTUACION >=", value, "idtipoactuacion");
            return (Criteria) this;
        }

        public Criteria andIdtipoactuacionLessThan(Short value) {
            addCriterion("IDTIPOACTUACION <", value, "idtipoactuacion");
            return (Criteria) this;
        }

        public Criteria andIdtipoactuacionLessThanOrEqualTo(Short value) {
            addCriterion("IDTIPOACTUACION <=", value, "idtipoactuacion");
            return (Criteria) this;
        }

        public Criteria andIdtipoactuacionIn(List<Short> values) {
            addCriterion("IDTIPOACTUACION in", values, "idtipoactuacion");
            return (Criteria) this;
        }

        public Criteria andIdtipoactuacionNotIn(List<Short> values) {
            addCriterion("IDTIPOACTUACION not in", values, "idtipoactuacion");
            return (Criteria) this;
        }

        public Criteria andIdtipoactuacionBetween(Short value1, Short value2) {
            addCriterion("IDTIPOACTUACION between", value1, value2, "idtipoactuacion");
            return (Criteria) this;
        }

        public Criteria andIdtipoactuacionNotBetween(Short value1, Short value2) {
            addCriterion("IDTIPOACTUACION not between", value1, value2, "idtipoactuacion");
            return (Criteria) this;
        }

        public Criteria andIdfacturacionIsNull() {
            addCriterion("IDFACTURACION is null");
            return (Criteria) this;
        }

        public Criteria andIdfacturacionIsNotNull() {
            addCriterion("IDFACTURACION is not null");
            return (Criteria) this;
        }

        public Criteria andIdfacturacionEqualTo(Integer value) {
            addCriterion("IDFACTURACION =", value, "idfacturacion");
            return (Criteria) this;
        }

        public Criteria andIdfacturacionNotEqualTo(Integer value) {
            addCriterion("IDFACTURACION <>", value, "idfacturacion");
            return (Criteria) this;
        }

        public Criteria andIdfacturacionGreaterThan(Integer value) {
            addCriterion("IDFACTURACION >", value, "idfacturacion");
            return (Criteria) this;
        }

        public Criteria andIdfacturacionGreaterThanOrEqualTo(Integer value) {
            addCriterion("IDFACTURACION >=", value, "idfacturacion");
            return (Criteria) this;
        }

        public Criteria andIdfacturacionLessThan(Integer value) {
            addCriterion("IDFACTURACION <", value, "idfacturacion");
            return (Criteria) this;
        }

        public Criteria andIdfacturacionLessThanOrEqualTo(Integer value) {
            addCriterion("IDFACTURACION <=", value, "idfacturacion");
            return (Criteria) this;
        }

        public Criteria andIdfacturacionIn(List<Integer> values) {
            addCriterion("IDFACTURACION in", values, "idfacturacion");
            return (Criteria) this;
        }

        public Criteria andIdfacturacionNotIn(List<Integer> values) {
            addCriterion("IDFACTURACION not in", values, "idfacturacion");
            return (Criteria) this;
        }

        public Criteria andIdfacturacionBetween(Integer value1, Integer value2) {
            addCriterion("IDFACTURACION between", value1, value2, "idfacturacion");
            return (Criteria) this;
        }

        public Criteria andIdfacturacionNotBetween(Integer value1, Integer value2) {
            addCriterion("IDFACTURACION not between", value1, value2, "idfacturacion");
            return (Criteria) this;
        }

        public Criteria andDescripcionIsNull() {
            addCriterion("DESCRIPCION is null");
            return (Criteria) this;
        }

        public Criteria andDescripcionIsNotNull() {
            addCriterion("DESCRIPCION is not null");
            return (Criteria) this;
        }

        public Criteria andDescripcionEqualTo(String value) {
            addCriterion("DESCRIPCION =", value, "descripcion");
            return (Criteria) this;
        }

        public Criteria andDescripcionNotEqualTo(String value) {
            addCriterion("DESCRIPCION <>", value, "descripcion");
            return (Criteria) this;
        }

        public Criteria andDescripcionGreaterThan(String value) {
            addCriterion("DESCRIPCION >", value, "descripcion");
            return (Criteria) this;
        }

        public Criteria andDescripcionGreaterThanOrEqualTo(String value) {
            addCriterion("DESCRIPCION >=", value, "descripcion");
            return (Criteria) this;
        }

        public Criteria andDescripcionLessThan(String value) {
            addCriterion("DESCRIPCION <", value, "descripcion");
            return (Criteria) this;
        }

        public Criteria andDescripcionLessThanOrEqualTo(String value) {
            addCriterion("DESCRIPCION <=", value, "descripcion");
            return (Criteria) this;
        }

        public Criteria andDescripcionLike(String value) {
            addCriterion("DESCRIPCION like", value, "descripcion");
            return (Criteria) this;
        }

        public Criteria andDescripcionNotLike(String value) {
            addCriterion("DESCRIPCION not like", value, "descripcion");
            return (Criteria) this;
        }

        public Criteria andDescripcionIn(List<String> values) {
            addCriterion("DESCRIPCION in", values, "descripcion");
            return (Criteria) this;
        }

        public Criteria andDescripcionNotIn(List<String> values) {
            addCriterion("DESCRIPCION not in", values, "descripcion");
            return (Criteria) this;
        }

        public Criteria andDescripcionBetween(String value1, String value2) {
            addCriterion("DESCRIPCION between", value1, value2, "descripcion");
            return (Criteria) this;
        }

        public Criteria andDescripcionNotBetween(String value1, String value2) {
            addCriterion("DESCRIPCION not between", value1, value2, "descripcion");
            return (Criteria) this;
        }

        public Criteria andImporteIsNull() {
            addCriterion("IMPORTE is null");
            return (Criteria) this;
        }

        public Criteria andImporteIsNotNull() {
            addCriterion("IMPORTE is not null");
            return (Criteria) this;
        }

        public Criteria andImporteEqualTo(BigDecimal value) {
            addCriterion("IMPORTE =", value, "importe");
            return (Criteria) this;
        }

        public Criteria andImporteNotEqualTo(BigDecimal value) {
            addCriterion("IMPORTE <>", value, "importe");
            return (Criteria) this;
        }

        public Criteria andImporteGreaterThan(BigDecimal value) {
            addCriterion("IMPORTE >", value, "importe");
            return (Criteria) this;
        }

        public Criteria andImporteGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("IMPORTE >=", value, "importe");
            return (Criteria) this;
        }

        public Criteria andImporteLessThan(BigDecimal value) {
            addCriterion("IMPORTE <", value, "importe");
            return (Criteria) this;
        }

        public Criteria andImporteLessThanOrEqualTo(BigDecimal value) {
            addCriterion("IMPORTE <=", value, "importe");
            return (Criteria) this;
        }

        public Criteria andImporteIn(List<BigDecimal> values) {
            addCriterion("IMPORTE in", values, "importe");
            return (Criteria) this;
        }

        public Criteria andImporteNotIn(List<BigDecimal> values) {
            addCriterion("IMPORTE not in", values, "importe");
            return (Criteria) this;
        }

        public Criteria andImporteBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("IMPORTE between", value1, value2, "importe");
            return (Criteria) this;
        }

        public Criteria andImporteNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("IMPORTE not between", value1, value2, "importe");
            return (Criteria) this;
        }

        public Criteria andFechacreacionIsNull() {
            addCriterion("FECHACREACION is null");
            return (Criteria) this;
        }

        public Criteria andFechacreacionIsNotNull() {
            addCriterion("FECHACREACION is not null");
            return (Criteria) this;
        }

        public Criteria andFechacreacionEqualTo(Date value) {
            addCriterion("FECHACREACION =", value, "fechacreacion");
            return (Criteria) this;
        }

        public Criteria andFechacreacionNotEqualTo(Date value) {
            addCriterion("FECHACREACION <>", value, "fechacreacion");
            return (Criteria) this;
        }

        public Criteria andFechacreacionGreaterThan(Date value) {
            addCriterion("FECHACREACION >", value, "fechacreacion");
            return (Criteria) this;
        }

        public Criteria andFechacreacionGreaterThanOrEqualTo(Date value) {
            addCriterion("FECHACREACION >=", value, "fechacreacion");
            return (Criteria) this;
        }

        public Criteria andFechacreacionLessThan(Date value) {
            addCriterion("FECHACREACION <", value, "fechacreacion");
            return (Criteria) this;
        }

        public Criteria andFechacreacionLessThanOrEqualTo(Date value) {
            addCriterion("FECHACREACION <=", value, "fechacreacion");
            return (Criteria) this;
        }

        public Criteria andFechacreacionIn(List<Date> values) {
            addCriterion("FECHACREACION in", values, "fechacreacion");
            return (Criteria) this;
        }

        public Criteria andFechacreacionNotIn(List<Date> values) {
            addCriterion("FECHACREACION not in", values, "fechacreacion");
            return (Criteria) this;
        }

        public Criteria andFechacreacionBetween(Date value1, Date value2) {
            addCriterion("FECHACREACION between", value1, value2, "fechacreacion");
            return (Criteria) this;
        }

        public Criteria andFechacreacionNotBetween(Date value1, Date value2) {
            addCriterion("FECHACREACION not between", value1, value2, "fechacreacion");
            return (Criteria) this;
        }

        public Criteria andFechamodificacionIsNull() {
            addCriterion("FECHAMODIFICACION is null");
            return (Criteria) this;
        }

        public Criteria andFechamodificacionIsNotNull() {
            addCriterion("FECHAMODIFICACION is not null");
            return (Criteria) this;
        }

        public Criteria andFechamodificacionEqualTo(Date value) {
            addCriterion("FECHAMODIFICACION =", value, "fechamodificacion");
            return (Criteria) this;
        }

        public Criteria andFechamodificacionNotEqualTo(Date value) {
            addCriterion("FECHAMODIFICACION <>", value, "fechamodificacion");
            return (Criteria) this;
        }

        public Criteria andFechamodificacionGreaterThan(Date value) {
            addCriterion("FECHAMODIFICACION >", value, "fechamodificacion");
            return (Criteria) this;
        }

        public Criteria andFechamodificacionGreaterThanOrEqualTo(Date value) {
            addCriterion("FECHAMODIFICACION >=", value, "fechamodificacion");
            return (Criteria) this;
        }

        public Criteria andFechamodificacionLessThan(Date value) {
            addCriterion("FECHAMODIFICACION <", value, "fechamodificacion");
            return (Criteria) this;
        }

        public Criteria andFechamodificacionLessThanOrEqualTo(Date value) {
            addCriterion("FECHAMODIFICACION <=", value, "fechamodificacion");
            return (Criteria) this;
        }

        public Criteria andFechamodificacionIn(List<Date> values) {
            addCriterion("FECHAMODIFICACION in", values, "fechamodificacion");
            return (Criteria) this;
        }

        public Criteria andFechamodificacionNotIn(List<Date> values) {
            addCriterion("FECHAMODIFICACION not in", values, "fechamodificacion");
            return (Criteria) this;
        }

        public Criteria andFechamodificacionBetween(Date value1, Date value2) {
            addCriterion("FECHAMODIFICACION between", value1, value2, "fechamodificacion");
            return (Criteria) this;
        }

        public Criteria andFechamodificacionNotBetween(Date value1, Date value2) {
            addCriterion("FECHAMODIFICACION not between", value1, value2, "fechamodificacion");
            return (Criteria) this;
        }

        public Criteria andImportemaximoIsNull() {
            addCriterion("IMPORTEMAXIMO is null");
            return (Criteria) this;
        }

        public Criteria andImportemaximoIsNotNull() {
            addCriterion("IMPORTEMAXIMO is not null");
            return (Criteria) this;
        }

        public Criteria andImportemaximoEqualTo(BigDecimal value) {
            addCriterion("IMPORTEMAXIMO =", value, "importemaximo");
            return (Criteria) this;
        }

        public Criteria andImportemaximoNotEqualTo(BigDecimal value) {
            addCriterion("IMPORTEMAXIMO <>", value, "importemaximo");
            return (Criteria) this;
        }

        public Criteria andImportemaximoGreaterThan(BigDecimal value) {
            addCriterion("IMPORTEMAXIMO >", value, "importemaximo");
            return (Criteria) this;
        }

        public Criteria andImportemaximoGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("IMPORTEMAXIMO >=", value, "importemaximo");
            return (Criteria) this;
        }

        public Criteria andImportemaximoLessThan(BigDecimal value) {
            addCriterion("IMPORTEMAXIMO <", value, "importemaximo");
            return (Criteria) this;
        }

        public Criteria andImportemaximoLessThanOrEqualTo(BigDecimal value) {
            addCriterion("IMPORTEMAXIMO <=", value, "importemaximo");
            return (Criteria) this;
        }

        public Criteria andImportemaximoIn(List<BigDecimal> values) {
            addCriterion("IMPORTEMAXIMO in", values, "importemaximo");
            return (Criteria) this;
        }

        public Criteria andImportemaximoNotIn(List<BigDecimal> values) {
            addCriterion("IMPORTEMAXIMO not in", values, "importemaximo");
            return (Criteria) this;
        }

        public Criteria andImportemaximoBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("IMPORTEMAXIMO between", value1, value2, "importemaximo");
            return (Criteria) this;
        }

        public Criteria andImportemaximoNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("IMPORTEMAXIMO not between", value1, value2, "importemaximo");
            return (Criteria) this;
        }

        public Criteria andUsumodificacionIsNull() {
            addCriterion("USUMODIFICACION is null");
            return (Criteria) this;
        }

        public Criteria andUsumodificacionIsNotNull() {
            addCriterion("USUMODIFICACION is not null");
            return (Criteria) this;
        }

        public Criteria andUsumodificacionEqualTo(Integer value) {
            addCriterion("USUMODIFICACION =", value, "usumodificacion");
            return (Criteria) this;
        }

        public Criteria andUsumodificacionNotEqualTo(Integer value) {
            addCriterion("USUMODIFICACION <>", value, "usumodificacion");
            return (Criteria) this;
        }

        public Criteria andUsumodificacionGreaterThan(Integer value) {
            addCriterion("USUMODIFICACION >", value, "usumodificacion");
            return (Criteria) this;
        }

        public Criteria andUsumodificacionGreaterThanOrEqualTo(Integer value) {
            addCriterion("USUMODIFICACION >=", value, "usumodificacion");
            return (Criteria) this;
        }

        public Criteria andUsumodificacionLessThan(Integer value) {
            addCriterion("USUMODIFICACION <", value, "usumodificacion");
            return (Criteria) this;
        }

        public Criteria andUsumodificacionLessThanOrEqualTo(Integer value) {
            addCriterion("USUMODIFICACION <=", value, "usumodificacion");
            return (Criteria) this;
        }

        public Criteria andUsumodificacionIn(List<Integer> values) {
            addCriterion("USUMODIFICACION in", values, "usumodificacion");
            return (Criteria) this;
        }

        public Criteria andUsumodificacionNotIn(List<Integer> values) {
            addCriterion("USUMODIFICACION not in", values, "usumodificacion");
            return (Criteria) this;
        }

        public Criteria andUsumodificacionBetween(Integer value1, Integer value2) {
            addCriterion("USUMODIFICACION between", value1, value2, "usumodificacion");
            return (Criteria) this;
        }

        public Criteria andUsumodificacionNotBetween(Integer value1, Integer value2) {
            addCriterion("USUMODIFICACION not between", value1, value2, "usumodificacion");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.FCS_HISTORICO_TIPOACTUACION
     *
     * @mbg.generated do_not_delete_during_merge Thu Dec 19 19:42:15 CET 2019
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.FCS_HISTORICO_TIPOACTUACION
     *
     * @mbg.generated Thu Dec 19 19:42:15 CET 2019
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}