package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScsEstadoejgExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.SCS_ESTADOEJG
     *
     * @mbg.generated Thu Nov 07 14:19:49 CET 2019
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.SCS_ESTADOEJG
     *
     * @mbg.generated Thu Nov 07 14:19:49 CET 2019
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.SCS_ESTADOEJG
     *
     * @mbg.generated Thu Nov 07 14:19:49 CET 2019
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ESTADOEJG
     *
     * @mbg.generated Thu Nov 07 14:19:49 CET 2019
     */
    public ScsEstadoejgExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ESTADOEJG
     *
     * @mbg.generated Thu Nov 07 14:19:49 CET 2019
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ESTADOEJG
     *
     * @mbg.generated Thu Nov 07 14:19:49 CET 2019
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ESTADOEJG
     *
     * @mbg.generated Thu Nov 07 14:19:49 CET 2019
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ESTADOEJG
     *
     * @mbg.generated Thu Nov 07 14:19:49 CET 2019
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ESTADOEJG
     *
     * @mbg.generated Thu Nov 07 14:19:49 CET 2019
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ESTADOEJG
     *
     * @mbg.generated Thu Nov 07 14:19:49 CET 2019
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ESTADOEJG
     *
     * @mbg.generated Thu Nov 07 14:19:49 CET 2019
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ESTADOEJG
     *
     * @mbg.generated Thu Nov 07 14:19:49 CET 2019
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
     * This method corresponds to the database table USCGAE.SCS_ESTADOEJG
     *
     * @mbg.generated Thu Nov 07 14:19:49 CET 2019
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_ESTADOEJG
     *
     * @mbg.generated Thu Nov 07 14:19:49 CET 2019
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.SCS_ESTADOEJG
     *
     * @mbg.generated Thu Nov 07 14:19:49 CET 2019
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
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

        public Criteria andIdtipoejgIsNull() {
            addCriterion("IDTIPOEJG is null");
            return (Criteria) this;
        }

        public Criteria andIdtipoejgIsNotNull() {
            addCriterion("IDTIPOEJG is not null");
            return (Criteria) this;
        }

        public Criteria andIdtipoejgEqualTo(Short value) {
            addCriterion("IDTIPOEJG =", value, "idtipoejg");
            return (Criteria) this;
        }

        public Criteria andIdtipoejgNotEqualTo(Short value) {
            addCriterion("IDTIPOEJG <>", value, "idtipoejg");
            return (Criteria) this;
        }

        public Criteria andIdtipoejgGreaterThan(Short value) {
            addCriterion("IDTIPOEJG >", value, "idtipoejg");
            return (Criteria) this;
        }

        public Criteria andIdtipoejgGreaterThanOrEqualTo(Short value) {
            addCriterion("IDTIPOEJG >=", value, "idtipoejg");
            return (Criteria) this;
        }

        public Criteria andIdtipoejgLessThan(Short value) {
            addCriterion("IDTIPOEJG <", value, "idtipoejg");
            return (Criteria) this;
        }

        public Criteria andIdtipoejgLessThanOrEqualTo(Short value) {
            addCriterion("IDTIPOEJG <=", value, "idtipoejg");
            return (Criteria) this;
        }

        public Criteria andIdtipoejgIn(List<Short> values) {
            addCriterion("IDTIPOEJG in", values, "idtipoejg");
            return (Criteria) this;
        }

        public Criteria andIdtipoejgNotIn(List<Short> values) {
            addCriterion("IDTIPOEJG not in", values, "idtipoejg");
            return (Criteria) this;
        }

        public Criteria andIdtipoejgBetween(Short value1, Short value2) {
            addCriterion("IDTIPOEJG between", value1, value2, "idtipoejg");
            return (Criteria) this;
        }

        public Criteria andIdtipoejgNotBetween(Short value1, Short value2) {
            addCriterion("IDTIPOEJG not between", value1, value2, "idtipoejg");
            return (Criteria) this;
        }

        public Criteria andAnioIsNull() {
            addCriterion("ANIO is null");
            return (Criteria) this;
        }

        public Criteria andAnioIsNotNull() {
            addCriterion("ANIO is not null");
            return (Criteria) this;
        }

        public Criteria andAnioEqualTo(Short value) {
            addCriterion("ANIO =", value, "anio");
            return (Criteria) this;
        }

        public Criteria andAnioNotEqualTo(Short value) {
            addCriterion("ANIO <>", value, "anio");
            return (Criteria) this;
        }

        public Criteria andAnioGreaterThan(Short value) {
            addCriterion("ANIO >", value, "anio");
            return (Criteria) this;
        }

        public Criteria andAnioGreaterThanOrEqualTo(Short value) {
            addCriterion("ANIO >=", value, "anio");
            return (Criteria) this;
        }

        public Criteria andAnioLessThan(Short value) {
            addCriterion("ANIO <", value, "anio");
            return (Criteria) this;
        }

        public Criteria andAnioLessThanOrEqualTo(Short value) {
            addCriterion("ANIO <=", value, "anio");
            return (Criteria) this;
        }

        public Criteria andAnioIn(List<Short> values) {
            addCriterion("ANIO in", values, "anio");
            return (Criteria) this;
        }

        public Criteria andAnioNotIn(List<Short> values) {
            addCriterion("ANIO not in", values, "anio");
            return (Criteria) this;
        }

        public Criteria andAnioBetween(Short value1, Short value2) {
            addCriterion("ANIO between", value1, value2, "anio");
            return (Criteria) this;
        }

        public Criteria andAnioNotBetween(Short value1, Short value2) {
            addCriterion("ANIO not between", value1, value2, "anio");
            return (Criteria) this;
        }

        public Criteria andNumeroIsNull() {
            addCriterion("NUMERO is null");
            return (Criteria) this;
        }

        public Criteria andNumeroIsNotNull() {
            addCriterion("NUMERO is not null");
            return (Criteria) this;
        }

        public Criteria andNumeroEqualTo(Long value) {
            addCriterion("NUMERO =", value, "numero");
            return (Criteria) this;
        }

        public Criteria andNumeroNotEqualTo(Long value) {
            addCriterion("NUMERO <>", value, "numero");
            return (Criteria) this;
        }

        public Criteria andNumeroGreaterThan(Long value) {
            addCriterion("NUMERO >", value, "numero");
            return (Criteria) this;
        }

        public Criteria andNumeroGreaterThanOrEqualTo(Long value) {
            addCriterion("NUMERO >=", value, "numero");
            return (Criteria) this;
        }

        public Criteria andNumeroLessThan(Long value) {
            addCriterion("NUMERO <", value, "numero");
            return (Criteria) this;
        }

        public Criteria andNumeroLessThanOrEqualTo(Long value) {
            addCriterion("NUMERO <=", value, "numero");
            return (Criteria) this;
        }

        public Criteria andNumeroIn(List<Long> values) {
            addCriterion("NUMERO in", values, "numero");
            return (Criteria) this;
        }

        public Criteria andNumeroNotIn(List<Long> values) {
            addCriterion("NUMERO not in", values, "numero");
            return (Criteria) this;
        }

        public Criteria andNumeroBetween(Long value1, Long value2) {
            addCriterion("NUMERO between", value1, value2, "numero");
            return (Criteria) this;
        }

        public Criteria andNumeroNotBetween(Long value1, Long value2) {
            addCriterion("NUMERO not between", value1, value2, "numero");
            return (Criteria) this;
        }

        public Criteria andIdestadoporejgIsNull() {
            addCriterion("IDESTADOPOREJG is null");
            return (Criteria) this;
        }

        public Criteria andIdestadoporejgIsNotNull() {
            addCriterion("IDESTADOPOREJG is not null");
            return (Criteria) this;
        }

        public Criteria andIdestadoporejgEqualTo(Long value) {
            addCriterion("IDESTADOPOREJG =", value, "idestadoporejg");
            return (Criteria) this;
        }

        public Criteria andIdestadoporejgNotEqualTo(Long value) {
            addCriterion("IDESTADOPOREJG <>", value, "idestadoporejg");
            return (Criteria) this;
        }

        public Criteria andIdestadoporejgGreaterThan(Long value) {
            addCriterion("IDESTADOPOREJG >", value, "idestadoporejg");
            return (Criteria) this;
        }

        public Criteria andIdestadoporejgGreaterThanOrEqualTo(Long value) {
            addCriterion("IDESTADOPOREJG >=", value, "idestadoporejg");
            return (Criteria) this;
        }

        public Criteria andIdestadoporejgLessThan(Long value) {
            addCriterion("IDESTADOPOREJG <", value, "idestadoporejg");
            return (Criteria) this;
        }

        public Criteria andIdestadoporejgLessThanOrEqualTo(Long value) {
            addCriterion("IDESTADOPOREJG <=", value, "idestadoporejg");
            return (Criteria) this;
        }

        public Criteria andIdestadoporejgIn(List<Long> values) {
            addCriterion("IDESTADOPOREJG in", values, "idestadoporejg");
            return (Criteria) this;
        }

        public Criteria andIdestadoporejgNotIn(List<Long> values) {
            addCriterion("IDESTADOPOREJG not in", values, "idestadoporejg");
            return (Criteria) this;
        }

        public Criteria andIdestadoporejgBetween(Long value1, Long value2) {
            addCriterion("IDESTADOPOREJG between", value1, value2, "idestadoporejg");
            return (Criteria) this;
        }

        public Criteria andIdestadoporejgNotBetween(Long value1, Long value2) {
            addCriterion("IDESTADOPOREJG not between", value1, value2, "idestadoporejg");
            return (Criteria) this;
        }

        public Criteria andIdestadoejgIsNull() {
            addCriterion("IDESTADOEJG is null");
            return (Criteria) this;
        }

        public Criteria andIdestadoejgIsNotNull() {
            addCriterion("IDESTADOEJG is not null");
            return (Criteria) this;
        }

        public Criteria andIdestadoejgEqualTo(Short value) {
            addCriterion("IDESTADOEJG =", value, "idestadoejg");
            return (Criteria) this;
        }

        public Criteria andIdestadoejgNotEqualTo(Short value) {
            addCriterion("IDESTADOEJG <>", value, "idestadoejg");
            return (Criteria) this;
        }

        public Criteria andIdestadoejgGreaterThan(Short value) {
            addCriterion("IDESTADOEJG >", value, "idestadoejg");
            return (Criteria) this;
        }

        public Criteria andIdestadoejgGreaterThanOrEqualTo(Short value) {
            addCriterion("IDESTADOEJG >=", value, "idestadoejg");
            return (Criteria) this;
        }

        public Criteria andIdestadoejgLessThan(Short value) {
            addCriterion("IDESTADOEJG <", value, "idestadoejg");
            return (Criteria) this;
        }

        public Criteria andIdestadoejgLessThanOrEqualTo(Short value) {
            addCriterion("IDESTADOEJG <=", value, "idestadoejg");
            return (Criteria) this;
        }

        public Criteria andIdestadoejgIn(List<Short> values) {
            addCriterion("IDESTADOEJG in", values, "idestadoejg");
            return (Criteria) this;
        }

        public Criteria andIdestadoejgNotIn(List<Short> values) {
            addCriterion("IDESTADOEJG not in", values, "idestadoejg");
            return (Criteria) this;
        }

        public Criteria andIdestadoejgBetween(Short value1, Short value2) {
            addCriterion("IDESTADOEJG between", value1, value2, "idestadoejg");
            return (Criteria) this;
        }

        public Criteria andIdestadoejgNotBetween(Short value1, Short value2) {
            addCriterion("IDESTADOEJG not between", value1, value2, "idestadoejg");
            return (Criteria) this;
        }

        public Criteria andFechainicioIsNull() {
            addCriterion("FECHAINICIO is null");
            return (Criteria) this;
        }

        public Criteria andFechainicioIsNotNull() {
            addCriterion("FECHAINICIO is not null");
            return (Criteria) this;
        }

        public Criteria andFechainicioEqualTo(Date value) {
            addCriterion("FECHAINICIO =", value, "fechainicio");
            return (Criteria) this;
        }

        public Criteria andFechainicioNotEqualTo(Date value) {
            addCriterion("FECHAINICIO <>", value, "fechainicio");
            return (Criteria) this;
        }

        public Criteria andFechainicioGreaterThan(Date value) {
            addCriterion("FECHAINICIO >", value, "fechainicio");
            return (Criteria) this;
        }

        public Criteria andFechainicioGreaterThanOrEqualTo(Date value) {
            addCriterion("FECHAINICIO >=", value, "fechainicio");
            return (Criteria) this;
        }

        public Criteria andFechainicioLessThan(Date value) {
            addCriterion("FECHAINICIO <", value, "fechainicio");
            return (Criteria) this;
        }

        public Criteria andFechainicioLessThanOrEqualTo(Date value) {
            addCriterion("FECHAINICIO <=", value, "fechainicio");
            return (Criteria) this;
        }

        public Criteria andFechainicioIn(List<Date> values) {
            addCriterion("FECHAINICIO in", values, "fechainicio");
            return (Criteria) this;
        }

        public Criteria andFechainicioNotIn(List<Date> values) {
            addCriterion("FECHAINICIO not in", values, "fechainicio");
            return (Criteria) this;
        }

        public Criteria andFechainicioBetween(Date value1, Date value2) {
            addCriterion("FECHAINICIO between", value1, value2, "fechainicio");
            return (Criteria) this;
        }

        public Criteria andFechainicioNotBetween(Date value1, Date value2) {
            addCriterion("FECHAINICIO not between", value1, value2, "fechainicio");
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

        public Criteria andObservacionesIsNull() {
            addCriterion("OBSERVACIONES is null");
            return (Criteria) this;
        }

        public Criteria andObservacionesIsNotNull() {
            addCriterion("OBSERVACIONES is not null");
            return (Criteria) this;
        }

        public Criteria andObservacionesEqualTo(String value) {
            addCriterion("OBSERVACIONES =", value, "observaciones");
            return (Criteria) this;
        }

        public Criteria andObservacionesNotEqualTo(String value) {
            addCriterion("OBSERVACIONES <>", value, "observaciones");
            return (Criteria) this;
        }

        public Criteria andObservacionesGreaterThan(String value) {
            addCriterion("OBSERVACIONES >", value, "observaciones");
            return (Criteria) this;
        }

        public Criteria andObservacionesGreaterThanOrEqualTo(String value) {
            addCriterion("OBSERVACIONES >=", value, "observaciones");
            return (Criteria) this;
        }

        public Criteria andObservacionesLessThan(String value) {
            addCriterion("OBSERVACIONES <", value, "observaciones");
            return (Criteria) this;
        }

        public Criteria andObservacionesLessThanOrEqualTo(String value) {
            addCriterion("OBSERVACIONES <=", value, "observaciones");
            return (Criteria) this;
        }

        public Criteria andObservacionesLike(String value) {
            addCriterion("OBSERVACIONES like", value, "observaciones");
            return (Criteria) this;
        }

        public Criteria andObservacionesNotLike(String value) {
            addCriterion("OBSERVACIONES not like", value, "observaciones");
            return (Criteria) this;
        }

        public Criteria andObservacionesIn(List<String> values) {
            addCriterion("OBSERVACIONES in", values, "observaciones");
            return (Criteria) this;
        }

        public Criteria andObservacionesNotIn(List<String> values) {
            addCriterion("OBSERVACIONES not in", values, "observaciones");
            return (Criteria) this;
        }

        public Criteria andObservacionesBetween(String value1, String value2) {
            addCriterion("OBSERVACIONES between", value1, value2, "observaciones");
            return (Criteria) this;
        }

        public Criteria andObservacionesNotBetween(String value1, String value2) {
            addCriterion("OBSERVACIONES not between", value1, value2, "observaciones");
            return (Criteria) this;
        }

        public Criteria andAutomaticoIsNull() {
            addCriterion("AUTOMATICO is null");
            return (Criteria) this;
        }

        public Criteria andAutomaticoIsNotNull() {
            addCriterion("AUTOMATICO is not null");
            return (Criteria) this;
        }

        public Criteria andAutomaticoEqualTo(String value) {
            addCriterion("AUTOMATICO =", value, "automatico");
            return (Criteria) this;
        }

        public Criteria andAutomaticoNotEqualTo(String value) {
            addCriterion("AUTOMATICO <>", value, "automatico");
            return (Criteria) this;
        }

        public Criteria andAutomaticoGreaterThan(String value) {
            addCriterion("AUTOMATICO >", value, "automatico");
            return (Criteria) this;
        }

        public Criteria andAutomaticoGreaterThanOrEqualTo(String value) {
            addCriterion("AUTOMATICO >=", value, "automatico");
            return (Criteria) this;
        }

        public Criteria andAutomaticoLessThan(String value) {
            addCriterion("AUTOMATICO <", value, "automatico");
            return (Criteria) this;
        }

        public Criteria andAutomaticoLessThanOrEqualTo(String value) {
            addCriterion("AUTOMATICO <=", value, "automatico");
            return (Criteria) this;
        }

        public Criteria andAutomaticoLike(String value) {
            addCriterion("AUTOMATICO like", value, "automatico");
            return (Criteria) this;
        }

        public Criteria andAutomaticoNotLike(String value) {
            addCriterion("AUTOMATICO not like", value, "automatico");
            return (Criteria) this;
        }

        public Criteria andAutomaticoIn(List<String> values) {
            addCriterion("AUTOMATICO in", values, "automatico");
            return (Criteria) this;
        }

        public Criteria andAutomaticoNotIn(List<String> values) {
            addCriterion("AUTOMATICO not in", values, "automatico");
            return (Criteria) this;
        }

        public Criteria andAutomaticoBetween(String value1, String value2) {
            addCriterion("AUTOMATICO between", value1, value2, "automatico");
            return (Criteria) this;
        }

        public Criteria andAutomaticoNotBetween(String value1, String value2) {
            addCriterion("AUTOMATICO not between", value1, value2, "automatico");
            return (Criteria) this;
        }

        public Criteria andPropietariocomisionIsNull() {
            addCriterion("PROPIETARIOCOMISION is null");
            return (Criteria) this;
        }

        public Criteria andPropietariocomisionIsNotNull() {
            addCriterion("PROPIETARIOCOMISION is not null");
            return (Criteria) this;
        }

        public Criteria andPropietariocomisionEqualTo(String value) {
            addCriterion("PROPIETARIOCOMISION =", value, "propietariocomision");
            return (Criteria) this;
        }

        public Criteria andPropietariocomisionNotEqualTo(String value) {
            addCriterion("PROPIETARIOCOMISION <>", value, "propietariocomision");
            return (Criteria) this;
        }

        public Criteria andPropietariocomisionGreaterThan(String value) {
            addCriterion("PROPIETARIOCOMISION >", value, "propietariocomision");
            return (Criteria) this;
        }

        public Criteria andPropietariocomisionGreaterThanOrEqualTo(String value) {
            addCriterion("PROPIETARIOCOMISION >=", value, "propietariocomision");
            return (Criteria) this;
        }

        public Criteria andPropietariocomisionLessThan(String value) {
            addCriterion("PROPIETARIOCOMISION <", value, "propietariocomision");
            return (Criteria) this;
        }

        public Criteria andPropietariocomisionLessThanOrEqualTo(String value) {
            addCriterion("PROPIETARIOCOMISION <=", value, "propietariocomision");
            return (Criteria) this;
        }

        public Criteria andPropietariocomisionLike(String value) {
            addCriterion("PROPIETARIOCOMISION like", value, "propietariocomision");
            return (Criteria) this;
        }

        public Criteria andPropietariocomisionNotLike(String value) {
            addCriterion("PROPIETARIOCOMISION not like", value, "propietariocomision");
            return (Criteria) this;
        }

        public Criteria andPropietariocomisionIn(List<String> values) {
            addCriterion("PROPIETARIOCOMISION in", values, "propietariocomision");
            return (Criteria) this;
        }

        public Criteria andPropietariocomisionNotIn(List<String> values) {
            addCriterion("PROPIETARIOCOMISION not in", values, "propietariocomision");
            return (Criteria) this;
        }

        public Criteria andPropietariocomisionBetween(String value1, String value2) {
            addCriterion("PROPIETARIOCOMISION between", value1, value2, "propietariocomision");
            return (Criteria) this;
        }

        public Criteria andPropietariocomisionNotBetween(String value1, String value2) {
            addCriterion("PROPIETARIOCOMISION not between", value1, value2, "propietariocomision");
            return (Criteria) this;
        }

        public Criteria andFechabajaIsNull() {
            addCriterion("FECHABAJA is null");
            return (Criteria) this;
        }

        public Criteria andFechabajaIsNotNull() {
            addCriterion("FECHABAJA is not null");
            return (Criteria) this;
        }

        public Criteria andFechabajaEqualTo(Date value) {
            addCriterion("FECHABAJA =", value, "fechabaja");
            return (Criteria) this;
        }

        public Criteria andFechabajaNotEqualTo(Date value) {
            addCriterion("FECHABAJA <>", value, "fechabaja");
            return (Criteria) this;
        }

        public Criteria andFechabajaGreaterThan(Date value) {
            addCriterion("FECHABAJA >", value, "fechabaja");
            return (Criteria) this;
        }

        public Criteria andFechabajaGreaterThanOrEqualTo(Date value) {
            addCriterion("FECHABAJA >=", value, "fechabaja");
            return (Criteria) this;
        }

        public Criteria andFechabajaLessThan(Date value) {
            addCriterion("FECHABAJA <", value, "fechabaja");
            return (Criteria) this;
        }

        public Criteria andFechabajaLessThanOrEqualTo(Date value) {
            addCriterion("FECHABAJA <=", value, "fechabaja");
            return (Criteria) this;
        }

        public Criteria andFechabajaIn(List<Date> values) {
            addCriterion("FECHABAJA in", values, "fechabaja");
            return (Criteria) this;
        }

        public Criteria andFechabajaNotIn(List<Date> values) {
            addCriterion("FECHABAJA not in", values, "fechabaja");
            return (Criteria) this;
        }

        public Criteria andFechabajaBetween(Date value1, Date value2) {
            addCriterion("FECHABAJA between", value1, value2, "fechabaja");
            return (Criteria) this;
        }

        public Criteria andFechabajaNotBetween(Date value1, Date value2) {
            addCriterion("FECHABAJA not between", value1, value2, "fechabaja");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.SCS_ESTADOEJG
     *
     * @mbg.generated do_not_delete_during_merge Thu Nov 07 14:19:49 CET 2019
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.SCS_ESTADOEJG
     *
     * @mbg.generated Thu Nov 07 14:19:49 CET 2019
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