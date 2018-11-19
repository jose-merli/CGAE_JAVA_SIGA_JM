package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgeDiassemanaExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.AGE_DIASSEMANA
     *
     * @mbg.generated Mon Nov 19 09:56:15 CET 2018
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.AGE_DIASSEMANA
     *
     * @mbg.generated Mon Nov 19 09:56:15 CET 2018
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.AGE_DIASSEMANA
     *
     * @mbg.generated Mon Nov 19 09:56:15 CET 2018
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_DIASSEMANA
     *
     * @mbg.generated Mon Nov 19 09:56:15 CET 2018
     */
    public AgeDiassemanaExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_DIASSEMANA
     *
     * @mbg.generated Mon Nov 19 09:56:15 CET 2018
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_DIASSEMANA
     *
     * @mbg.generated Mon Nov 19 09:56:15 CET 2018
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_DIASSEMANA
     *
     * @mbg.generated Mon Nov 19 09:56:15 CET 2018
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_DIASSEMANA
     *
     * @mbg.generated Mon Nov 19 09:56:15 CET 2018
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_DIASSEMANA
     *
     * @mbg.generated Mon Nov 19 09:56:15 CET 2018
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_DIASSEMANA
     *
     * @mbg.generated Mon Nov 19 09:56:15 CET 2018
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_DIASSEMANA
     *
     * @mbg.generated Mon Nov 19 09:56:15 CET 2018
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_DIASSEMANA
     *
     * @mbg.generated Mon Nov 19 09:56:15 CET 2018
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
     * This method corresponds to the database table USCGAE.AGE_DIASSEMANA
     *
     * @mbg.generated Mon Nov 19 09:56:15 CET 2018
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.AGE_DIASSEMANA
     *
     * @mbg.generated Mon Nov 19 09:56:15 CET 2018
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.AGE_DIASSEMANA
     *
     * @mbg.generated Mon Nov 19 09:56:15 CET 2018
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

        public Criteria andIddiassemanaIsNull() {
            addCriterion("IDDIASSEMANA is null");
            return (Criteria) this;
        }

        public Criteria andIddiassemanaIsNotNull() {
            addCriterion("IDDIASSEMANA is not null");
            return (Criteria) this;
        }

        public Criteria andIddiassemanaEqualTo(Long value) {
            addCriterion("IDDIASSEMANA =", value, "iddiassemana");
            return (Criteria) this;
        }

        public Criteria andIddiassemanaNotEqualTo(Long value) {
            addCriterion("IDDIASSEMANA <>", value, "iddiassemana");
            return (Criteria) this;
        }

        public Criteria andIddiassemanaGreaterThan(Long value) {
            addCriterion("IDDIASSEMANA >", value, "iddiassemana");
            return (Criteria) this;
        }

        public Criteria andIddiassemanaGreaterThanOrEqualTo(Long value) {
            addCriterion("IDDIASSEMANA >=", value, "iddiassemana");
            return (Criteria) this;
        }

        public Criteria andIddiassemanaLessThan(Long value) {
            addCriterion("IDDIASSEMANA <", value, "iddiassemana");
            return (Criteria) this;
        }

        public Criteria andIddiassemanaLessThanOrEqualTo(Long value) {
            addCriterion("IDDIASSEMANA <=", value, "iddiassemana");
            return (Criteria) this;
        }

        public Criteria andIddiassemanaIn(List<Long> values) {
            addCriterion("IDDIASSEMANA in", values, "iddiassemana");
            return (Criteria) this;
        }

        public Criteria andIddiassemanaNotIn(List<Long> values) {
            addCriterion("IDDIASSEMANA not in", values, "iddiassemana");
            return (Criteria) this;
        }

        public Criteria andIddiassemanaBetween(Long value1, Long value2) {
            addCriterion("IDDIASSEMANA between", value1, value2, "iddiassemana");
            return (Criteria) this;
        }

        public Criteria andIddiassemanaNotBetween(Long value1, Long value2) {
            addCriterion("IDDIASSEMANA not between", value1, value2, "iddiassemana");
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

        public Criteria andUsumodificacionIsNull() {
            addCriterion("USUMODIFICACION is null");
            return (Criteria) this;
        }

        public Criteria andUsumodificacionIsNotNull() {
            addCriterion("USUMODIFICACION is not null");
            return (Criteria) this;
        }

        public Criteria andUsumodificacionEqualTo(Long value) {
            addCriterion("USUMODIFICACION =", value, "usumodificacion");
            return (Criteria) this;
        }

        public Criteria andUsumodificacionNotEqualTo(Long value) {
            addCriterion("USUMODIFICACION <>", value, "usumodificacion");
            return (Criteria) this;
        }

        public Criteria andUsumodificacionGreaterThan(Long value) {
            addCriterion("USUMODIFICACION >", value, "usumodificacion");
            return (Criteria) this;
        }

        public Criteria andUsumodificacionGreaterThanOrEqualTo(Long value) {
            addCriterion("USUMODIFICACION >=", value, "usumodificacion");
            return (Criteria) this;
        }

        public Criteria andUsumodificacionLessThan(Long value) {
            addCriterion("USUMODIFICACION <", value, "usumodificacion");
            return (Criteria) this;
        }

        public Criteria andUsumodificacionLessThanOrEqualTo(Long value) {
            addCriterion("USUMODIFICACION <=", value, "usumodificacion");
            return (Criteria) this;
        }

        public Criteria andUsumodificacionIn(List<Long> values) {
            addCriterion("USUMODIFICACION in", values, "usumodificacion");
            return (Criteria) this;
        }

        public Criteria andUsumodificacionNotIn(List<Long> values) {
            addCriterion("USUMODIFICACION not in", values, "usumodificacion");
            return (Criteria) this;
        }

        public Criteria andUsumodificacionBetween(Long value1, Long value2) {
            addCriterion("USUMODIFICACION between", value1, value2, "usumodificacion");
            return (Criteria) this;
        }

        public Criteria andUsumodificacionNotBetween(Long value1, Long value2) {
            addCriterion("USUMODIFICACION not between", value1, value2, "usumodificacion");
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
     * This class corresponds to the database table USCGAE.AGE_DIASSEMANA
     *
     * @mbg.generated do_not_delete_during_merge Mon Nov 19 09:56:15 CET 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.AGE_DIASSEMANA
     *
     * @mbg.generated Mon Nov 19 09:56:15 CET 2018
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