package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ForCambioinscripcionExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.FOR_CAMBIOINSCRIPCION
     *
     * @mbg.generated Wed Dec 12 16:02:36 CET 2018
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.FOR_CAMBIOINSCRIPCION
     *
     * @mbg.generated Wed Dec 12 16:02:36 CET 2018
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.FOR_CAMBIOINSCRIPCION
     *
     * @mbg.generated Wed Dec 12 16:02:36 CET 2018
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_CAMBIOINSCRIPCION
     *
     * @mbg.generated Wed Dec 12 16:02:36 CET 2018
     */
    public ForCambioinscripcionExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_CAMBIOINSCRIPCION
     *
     * @mbg.generated Wed Dec 12 16:02:36 CET 2018
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_CAMBIOINSCRIPCION
     *
     * @mbg.generated Wed Dec 12 16:02:36 CET 2018
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_CAMBIOINSCRIPCION
     *
     * @mbg.generated Wed Dec 12 16:02:36 CET 2018
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_CAMBIOINSCRIPCION
     *
     * @mbg.generated Wed Dec 12 16:02:36 CET 2018
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_CAMBIOINSCRIPCION
     *
     * @mbg.generated Wed Dec 12 16:02:36 CET 2018
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_CAMBIOINSCRIPCION
     *
     * @mbg.generated Wed Dec 12 16:02:36 CET 2018
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_CAMBIOINSCRIPCION
     *
     * @mbg.generated Wed Dec 12 16:02:36 CET 2018
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_CAMBIOINSCRIPCION
     *
     * @mbg.generated Wed Dec 12 16:02:36 CET 2018
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
     * This method corresponds to the database table USCGAE.FOR_CAMBIOINSCRIPCION
     *
     * @mbg.generated Wed Dec 12 16:02:36 CET 2018
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FOR_CAMBIOINSCRIPCION
     *
     * @mbg.generated Wed Dec 12 16:02:36 CET 2018
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.FOR_CAMBIOINSCRIPCION
     *
     * @mbg.generated Wed Dec 12 16:02:36 CET 2018
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

        public Criteria andIdinscripcionIsNull() {
            addCriterion("IDINSCRIPCION is null");
            return (Criteria) this;
        }

        public Criteria andIdinscripcionIsNotNull() {
            addCriterion("IDINSCRIPCION is not null");
            return (Criteria) this;
        }

        public Criteria andIdinscripcionEqualTo(Long value) {
            addCriterion("IDINSCRIPCION =", value, "idinscripcion");
            return (Criteria) this;
        }

        public Criteria andIdinscripcionNotEqualTo(Long value) {
            addCriterion("IDINSCRIPCION <>", value, "idinscripcion");
            return (Criteria) this;
        }

        public Criteria andIdinscripcionGreaterThan(Long value) {
            addCriterion("IDINSCRIPCION >", value, "idinscripcion");
            return (Criteria) this;
        }

        public Criteria andIdinscripcionGreaterThanOrEqualTo(Long value) {
            addCriterion("IDINSCRIPCION >=", value, "idinscripcion");
            return (Criteria) this;
        }

        public Criteria andIdinscripcionLessThan(Long value) {
            addCriterion("IDINSCRIPCION <", value, "idinscripcion");
            return (Criteria) this;
        }

        public Criteria andIdinscripcionLessThanOrEqualTo(Long value) {
            addCriterion("IDINSCRIPCION <=", value, "idinscripcion");
            return (Criteria) this;
        }

        public Criteria andIdinscripcionIn(List<Long> values) {
            addCriterion("IDINSCRIPCION in", values, "idinscripcion");
            return (Criteria) this;
        }

        public Criteria andIdinscripcionNotIn(List<Long> values) {
            addCriterion("IDINSCRIPCION not in", values, "idinscripcion");
            return (Criteria) this;
        }

        public Criteria andIdinscripcionBetween(Long value1, Long value2) {
            addCriterion("IDINSCRIPCION between", value1, value2, "idinscripcion");
            return (Criteria) this;
        }

        public Criteria andIdinscripcionNotBetween(Long value1, Long value2) {
            addCriterion("IDINSCRIPCION not between", value1, value2, "idinscripcion");
            return (Criteria) this;
        }

        public Criteria andIdestadoinscripcionIsNull() {
            addCriterion("IDESTADOINSCRIPCION is null");
            return (Criteria) this;
        }

        public Criteria andIdestadoinscripcionIsNotNull() {
            addCriterion("IDESTADOINSCRIPCION is not null");
            return (Criteria) this;
        }

        public Criteria andIdestadoinscripcionEqualTo(Long value) {
            addCriterion("IDESTADOINSCRIPCION =", value, "idestadoinscripcion");
            return (Criteria) this;
        }

        public Criteria andIdestadoinscripcionNotEqualTo(Long value) {
            addCriterion("IDESTADOINSCRIPCION <>", value, "idestadoinscripcion");
            return (Criteria) this;
        }

        public Criteria andIdestadoinscripcionGreaterThan(Long value) {
            addCriterion("IDESTADOINSCRIPCION >", value, "idestadoinscripcion");
            return (Criteria) this;
        }

        public Criteria andIdestadoinscripcionGreaterThanOrEqualTo(Long value) {
            addCriterion("IDESTADOINSCRIPCION >=", value, "idestadoinscripcion");
            return (Criteria) this;
        }

        public Criteria andIdestadoinscripcionLessThan(Long value) {
            addCriterion("IDESTADOINSCRIPCION <", value, "idestadoinscripcion");
            return (Criteria) this;
        }

        public Criteria andIdestadoinscripcionLessThanOrEqualTo(Long value) {
            addCriterion("IDESTADOINSCRIPCION <=", value, "idestadoinscripcion");
            return (Criteria) this;
        }

        public Criteria andIdestadoinscripcionIn(List<Long> values) {
            addCriterion("IDESTADOINSCRIPCION in", values, "idestadoinscripcion");
            return (Criteria) this;
        }

        public Criteria andIdestadoinscripcionNotIn(List<Long> values) {
            addCriterion("IDESTADOINSCRIPCION not in", values, "idestadoinscripcion");
            return (Criteria) this;
        }

        public Criteria andIdestadoinscripcionBetween(Long value1, Long value2) {
            addCriterion("IDESTADOINSCRIPCION between", value1, value2, "idestadoinscripcion");
            return (Criteria) this;
        }

        public Criteria andIdestadoinscripcionNotBetween(Long value1, Long value2) {
            addCriterion("IDESTADOINSCRIPCION not between", value1, value2, "idestadoinscripcion");
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

        public Criteria andMotivoIsNull() {
            addCriterion("MOTIVO is null");
            return (Criteria) this;
        }

        public Criteria andMotivoIsNotNull() {
            addCriterion("MOTIVO is not null");
            return (Criteria) this;
        }

        public Criteria andMotivoEqualTo(String value) {
            addCriterion("MOTIVO =", value, "motivo");
            return (Criteria) this;
        }

        public Criteria andMotivoNotEqualTo(String value) {
            addCriterion("MOTIVO <>", value, "motivo");
            return (Criteria) this;
        }

        public Criteria andMotivoGreaterThan(String value) {
            addCriterion("MOTIVO >", value, "motivo");
            return (Criteria) this;
        }

        public Criteria andMotivoGreaterThanOrEqualTo(String value) {
            addCriterion("MOTIVO >=", value, "motivo");
            return (Criteria) this;
        }

        public Criteria andMotivoLessThan(String value) {
            addCriterion("MOTIVO <", value, "motivo");
            return (Criteria) this;
        }

        public Criteria andMotivoLessThanOrEqualTo(String value) {
            addCriterion("MOTIVO <=", value, "motivo");
            return (Criteria) this;
        }

        public Criteria andMotivoLike(String value) {
            addCriterion("MOTIVO like", value, "motivo");
            return (Criteria) this;
        }

        public Criteria andMotivoNotLike(String value) {
            addCriterion("MOTIVO not like", value, "motivo");
            return (Criteria) this;
        }

        public Criteria andMotivoIn(List<String> values) {
            addCriterion("MOTIVO in", values, "motivo");
            return (Criteria) this;
        }

        public Criteria andMotivoNotIn(List<String> values) {
            addCriterion("MOTIVO not in", values, "motivo");
            return (Criteria) this;
        }

        public Criteria andMotivoBetween(String value1, String value2) {
            addCriterion("MOTIVO between", value1, value2, "motivo");
            return (Criteria) this;
        }

        public Criteria andMotivoNotBetween(String value1, String value2) {
            addCriterion("MOTIVO not between", value1, value2, "motivo");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.FOR_CAMBIOINSCRIPCION
     *
     * @mbg.generated do_not_delete_during_merge Wed Dec 12 16:02:36 CET 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.FOR_CAMBIOINSCRIPCION
     *
     * @mbg.generated Wed Dec 12 16:02:36 CET 2018
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