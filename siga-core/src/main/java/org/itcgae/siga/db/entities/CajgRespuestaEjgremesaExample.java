package org.itcgae.siga.db.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CajgRespuestaEjgremesaExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.CAJG_RESPUESTA_EJGREMESA
     *
     * @mbg.generated Mon Oct 04 12:35:50 CEST 2021
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.CAJG_RESPUESTA_EJGREMESA
     *
     * @mbg.generated Mon Oct 04 12:35:50 CEST 2021
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.CAJG_RESPUESTA_EJGREMESA
     *
     * @mbg.generated Mon Oct 04 12:35:50 CEST 2021
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CAJG_RESPUESTA_EJGREMESA
     *
     * @mbg.generated Mon Oct 04 12:35:50 CEST 2021
     */
    public CajgRespuestaEjgremesaExample() {
        oredCriteria = new ArrayList<>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CAJG_RESPUESTA_EJGREMESA
     *
     * @mbg.generated Mon Oct 04 12:35:50 CEST 2021
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CAJG_RESPUESTA_EJGREMESA
     *
     * @mbg.generated Mon Oct 04 12:35:50 CEST 2021
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CAJG_RESPUESTA_EJGREMESA
     *
     * @mbg.generated Mon Oct 04 12:35:50 CEST 2021
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CAJG_RESPUESTA_EJGREMESA
     *
     * @mbg.generated Mon Oct 04 12:35:50 CEST 2021
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CAJG_RESPUESTA_EJGREMESA
     *
     * @mbg.generated Mon Oct 04 12:35:50 CEST 2021
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CAJG_RESPUESTA_EJGREMESA
     *
     * @mbg.generated Mon Oct 04 12:35:50 CEST 2021
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CAJG_RESPUESTA_EJGREMESA
     *
     * @mbg.generated Mon Oct 04 12:35:50 CEST 2021
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CAJG_RESPUESTA_EJGREMESA
     *
     * @mbg.generated Mon Oct 04 12:35:50 CEST 2021
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
     * This method corresponds to the database table USCGAE.CAJG_RESPUESTA_EJGREMESA
     *
     * @mbg.generated Mon Oct 04 12:35:50 CEST 2021
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CAJG_RESPUESTA_EJGREMESA
     *
     * @mbg.generated Mon Oct 04 12:35:50 CEST 2021
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.CAJG_RESPUESTA_EJGREMESA
     *
     * @mbg.generated Mon Oct 04 12:35:50 CEST 2021
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

        public Criteria andIdrespuestaIsNull() {
            addCriterion("IDRESPUESTA is null");
            return (Criteria) this;
        }

        public Criteria andIdrespuestaIsNotNull() {
            addCriterion("IDRESPUESTA is not null");
            return (Criteria) this;
        }

        public Criteria andIdrespuestaEqualTo(BigDecimal value) {
            addCriterion("IDRESPUESTA =", value, "idrespuesta");
            return (Criteria) this;
        }

        public Criteria andIdrespuestaNotEqualTo(BigDecimal value) {
            addCriterion("IDRESPUESTA <>", value, "idrespuesta");
            return (Criteria) this;
        }

        public Criteria andIdrespuestaGreaterThan(BigDecimal value) {
            addCriterion("IDRESPUESTA >", value, "idrespuesta");
            return (Criteria) this;
        }

        public Criteria andIdrespuestaGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("IDRESPUESTA >=", value, "idrespuesta");
            return (Criteria) this;
        }

        public Criteria andIdrespuestaLessThan(BigDecimal value) {
            addCriterion("IDRESPUESTA <", value, "idrespuesta");
            return (Criteria) this;
        }

        public Criteria andIdrespuestaLessThanOrEqualTo(BigDecimal value) {
            addCriterion("IDRESPUESTA <=", value, "idrespuesta");
            return (Criteria) this;
        }

        public Criteria andIdrespuestaIn(List<BigDecimal> values) {
            addCriterion("IDRESPUESTA in", values, "idrespuesta");
            return (Criteria) this;
        }

        public Criteria andIdrespuestaNotIn(List<BigDecimal> values) {
            addCriterion("IDRESPUESTA not in", values, "idrespuesta");
            return (Criteria) this;
        }

        public Criteria andIdrespuestaBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("IDRESPUESTA between", value1, value2, "idrespuesta");
            return (Criteria) this;
        }

        public Criteria andIdrespuestaNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("IDRESPUESTA not between", value1, value2, "idrespuesta");
            return (Criteria) this;
        }

        public Criteria andIdejgremesaIsNull() {
            addCriterion("IDEJGREMESA is null");
            return (Criteria) this;
        }

        public Criteria andIdejgremesaIsNotNull() {
            addCriterion("IDEJGREMESA is not null");
            return (Criteria) this;
        }

        public Criteria andIdejgremesaEqualTo(Long value) {
            addCriterion("IDEJGREMESA =", value, "idejgremesa");
            return (Criteria) this;
        }

        public Criteria andIdejgremesaNotEqualTo(Long value) {
            addCriterion("IDEJGREMESA <>", value, "idejgremesa");
            return (Criteria) this;
        }

        public Criteria andIdejgremesaGreaterThan(Long value) {
            addCriterion("IDEJGREMESA >", value, "idejgremesa");
            return (Criteria) this;
        }

        public Criteria andIdejgremesaGreaterThanOrEqualTo(Long value) {
            addCriterion("IDEJGREMESA >=", value, "idejgremesa");
            return (Criteria) this;
        }

        public Criteria andIdejgremesaLessThan(Long value) {
            addCriterion("IDEJGREMESA <", value, "idejgremesa");
            return (Criteria) this;
        }

        public Criteria andIdejgremesaLessThanOrEqualTo(Long value) {
            addCriterion("IDEJGREMESA <=", value, "idejgremesa");
            return (Criteria) this;
        }

        public Criteria andIdejgremesaIn(List<Long> values) {
            addCriterion("IDEJGREMESA in", values, "idejgremesa");
            return (Criteria) this;
        }

        public Criteria andIdejgremesaNotIn(List<Long> values) {
            addCriterion("IDEJGREMESA not in", values, "idejgremesa");
            return (Criteria) this;
        }

        public Criteria andIdejgremesaBetween(Long value1, Long value2) {
            addCriterion("IDEJGREMESA between", value1, value2, "idejgremesa");
            return (Criteria) this;
        }

        public Criteria andIdejgremesaNotBetween(Long value1, Long value2) {
            addCriterion("IDEJGREMESA not between", value1, value2, "idejgremesa");
            return (Criteria) this;
        }

        public Criteria andCodigoIsNull() {
            addCriterion("CODIGO is null");
            return (Criteria) this;
        }

        public Criteria andCodigoIsNotNull() {
            addCriterion("CODIGO is not null");
            return (Criteria) this;
        }

        public Criteria andCodigoEqualTo(String value) {
            addCriterion("CODIGO =", value, "codigo");
            return (Criteria) this;
        }

        public Criteria andCodigoNotEqualTo(String value) {
            addCriterion("CODIGO <>", value, "codigo");
            return (Criteria) this;
        }

        public Criteria andCodigoGreaterThan(String value) {
            addCriterion("CODIGO >", value, "codigo");
            return (Criteria) this;
        }

        public Criteria andCodigoGreaterThanOrEqualTo(String value) {
            addCriterion("CODIGO >=", value, "codigo");
            return (Criteria) this;
        }

        public Criteria andCodigoLessThan(String value) {
            addCriterion("CODIGO <", value, "codigo");
            return (Criteria) this;
        }

        public Criteria andCodigoLessThanOrEqualTo(String value) {
            addCriterion("CODIGO <=", value, "codigo");
            return (Criteria) this;
        }

        public Criteria andCodigoLike(String value) {
            addCriterion("CODIGO like", value, "codigo");
            return (Criteria) this;
        }

        public Criteria andCodigoNotLike(String value) {
            addCriterion("CODIGO not like", value, "codigo");
            return (Criteria) this;
        }

        public Criteria andCodigoIn(List<String> values) {
            addCriterion("CODIGO in", values, "codigo");
            return (Criteria) this;
        }

        public Criteria andCodigoNotIn(List<String> values) {
            addCriterion("CODIGO not in", values, "codigo");
            return (Criteria) this;
        }

        public Criteria andCodigoBetween(String value1, String value2) {
            addCriterion("CODIGO between", value1, value2, "codigo");
            return (Criteria) this;
        }

        public Criteria andCodigoNotBetween(String value1, String value2) {
            addCriterion("CODIGO not between", value1, value2, "codigo");
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

        public Criteria andAbreviaturaIsNull() {
            addCriterion("ABREVIATURA is null");
            return (Criteria) this;
        }

        public Criteria andAbreviaturaIsNotNull() {
            addCriterion("ABREVIATURA is not null");
            return (Criteria) this;
        }

        public Criteria andAbreviaturaEqualTo(String value) {
            addCriterion("ABREVIATURA =", value, "abreviatura");
            return (Criteria) this;
        }

        public Criteria andAbreviaturaNotEqualTo(String value) {
            addCriterion("ABREVIATURA <>", value, "abreviatura");
            return (Criteria) this;
        }

        public Criteria andAbreviaturaGreaterThan(String value) {
            addCriterion("ABREVIATURA >", value, "abreviatura");
            return (Criteria) this;
        }

        public Criteria andAbreviaturaGreaterThanOrEqualTo(String value) {
            addCriterion("ABREVIATURA >=", value, "abreviatura");
            return (Criteria) this;
        }

        public Criteria andAbreviaturaLessThan(String value) {
            addCriterion("ABREVIATURA <", value, "abreviatura");
            return (Criteria) this;
        }

        public Criteria andAbreviaturaLessThanOrEqualTo(String value) {
            addCriterion("ABREVIATURA <=", value, "abreviatura");
            return (Criteria) this;
        }

        public Criteria andAbreviaturaLike(String value) {
            addCriterion("ABREVIATURA like", value, "abreviatura");
            return (Criteria) this;
        }

        public Criteria andAbreviaturaNotLike(String value) {
            addCriterion("ABREVIATURA not like", value, "abreviatura");
            return (Criteria) this;
        }

        public Criteria andAbreviaturaIn(List<String> values) {
            addCriterion("ABREVIATURA in", values, "abreviatura");
            return (Criteria) this;
        }

        public Criteria andAbreviaturaNotIn(List<String> values) {
            addCriterion("ABREVIATURA not in", values, "abreviatura");
            return (Criteria) this;
        }

        public Criteria andAbreviaturaBetween(String value1, String value2) {
            addCriterion("ABREVIATURA between", value1, value2, "abreviatura");
            return (Criteria) this;
        }

        public Criteria andAbreviaturaNotBetween(String value1, String value2) {
            addCriterion("ABREVIATURA not between", value1, value2, "abreviatura");
            return (Criteria) this;
        }

        public Criteria andFechaIsNull() {
            addCriterion("FECHA is null");
            return (Criteria) this;
        }

        public Criteria andFechaIsNotNull() {
            addCriterion("FECHA is not null");
            return (Criteria) this;
        }

        public Criteria andFechaEqualTo(Date value) {
            addCriterion("FECHA =", value, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaNotEqualTo(Date value) {
            addCriterion("FECHA <>", value, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaGreaterThan(Date value) {
            addCriterion("FECHA >", value, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaGreaterThanOrEqualTo(Date value) {
            addCriterion("FECHA >=", value, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaLessThan(Date value) {
            addCriterion("FECHA <", value, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaLessThanOrEqualTo(Date value) {
            addCriterion("FECHA <=", value, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaIn(List<Date> values) {
            addCriterion("FECHA in", values, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaNotIn(List<Date> values) {
            addCriterion("FECHA not in", values, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaBetween(Date value1, Date value2) {
            addCriterion("FECHA between", value1, value2, "fecha");
            return (Criteria) this;
        }

        public Criteria andFechaNotBetween(Date value1, Date value2) {
            addCriterion("FECHA not between", value1, value2, "fecha");
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

        public Criteria andIdtiporespuestaIsNull() {
            addCriterion("IDTIPORESPUESTA is null");
            return (Criteria) this;
        }

        public Criteria andIdtiporespuestaIsNotNull() {
            addCriterion("IDTIPORESPUESTA is not null");
            return (Criteria) this;
        }

        public Criteria andIdtiporespuestaEqualTo(Long value) {
            addCriterion("IDTIPORESPUESTA =", value, "idtiporespuesta");
            return (Criteria) this;
        }

        public Criteria andIdtiporespuestaNotEqualTo(Long value) {
            addCriterion("IDTIPORESPUESTA <>", value, "idtiporespuesta");
            return (Criteria) this;
        }

        public Criteria andIdtiporespuestaGreaterThan(Long value) {
            addCriterion("IDTIPORESPUESTA >", value, "idtiporespuesta");
            return (Criteria) this;
        }

        public Criteria andIdtiporespuestaGreaterThanOrEqualTo(Long value) {
            addCriterion("IDTIPORESPUESTA >=", value, "idtiporespuesta");
            return (Criteria) this;
        }

        public Criteria andIdtiporespuestaLessThan(Long value) {
            addCriterion("IDTIPORESPUESTA <", value, "idtiporespuesta");
            return (Criteria) this;
        }

        public Criteria andIdtiporespuestaLessThanOrEqualTo(Long value) {
            addCriterion("IDTIPORESPUESTA <=", value, "idtiporespuesta");
            return (Criteria) this;
        }

        public Criteria andIdtiporespuestaIn(List<Long> values) {
            addCriterion("IDTIPORESPUESTA in", values, "idtiporespuesta");
            return (Criteria) this;
        }

        public Criteria andIdtiporespuestaNotIn(List<Long> values) {
            addCriterion("IDTIPORESPUESTA not in", values, "idtiporespuesta");
            return (Criteria) this;
        }

        public Criteria andIdtiporespuestaBetween(Long value1, Long value2) {
            addCriterion("IDTIPORESPUESTA between", value1, value2, "idtiporespuesta");
            return (Criteria) this;
        }

        public Criteria andIdtiporespuestaNotBetween(Long value1, Long value2) {
            addCriterion("IDTIPORESPUESTA not between", value1, value2, "idtiporespuesta");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.CAJG_RESPUESTA_EJGREMESA
     *
     * @mbg.generated do_not_delete_during_merge Mon Oct 04 12:35:50 CEST 2021
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.CAJG_RESPUESTA_EJGREMESA
     *
     * @mbg.generated Mon Oct 04 12:35:50 CEST 2021
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