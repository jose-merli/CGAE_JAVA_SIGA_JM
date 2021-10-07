package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScsConjuntoguardiasExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.SCS_CONJUNTOGUARDIAS
     *
     * @mbg.generated Mon Oct 04 11:51:44 CEST 2021
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.SCS_CONJUNTOGUARDIAS
     *
     * @mbg.generated Mon Oct 04 11:51:44 CEST 2021
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.SCS_CONJUNTOGUARDIAS
     *
     * @mbg.generated Mon Oct 04 11:51:44 CEST 2021
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CONJUNTOGUARDIAS
     *
     * @mbg.generated Mon Oct 04 11:51:44 CEST 2021
     */
    public ScsConjuntoguardiasExample() {
        oredCriteria = new ArrayList<>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CONJUNTOGUARDIAS
     *
     * @mbg.generated Mon Oct 04 11:51:44 CEST 2021
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CONJUNTOGUARDIAS
     *
     * @mbg.generated Mon Oct 04 11:51:44 CEST 2021
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CONJUNTOGUARDIAS
     *
     * @mbg.generated Mon Oct 04 11:51:44 CEST 2021
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CONJUNTOGUARDIAS
     *
     * @mbg.generated Mon Oct 04 11:51:44 CEST 2021
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CONJUNTOGUARDIAS
     *
     * @mbg.generated Mon Oct 04 11:51:44 CEST 2021
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CONJUNTOGUARDIAS
     *
     * @mbg.generated Mon Oct 04 11:51:44 CEST 2021
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CONJUNTOGUARDIAS
     *
     * @mbg.generated Mon Oct 04 11:51:44 CEST 2021
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CONJUNTOGUARDIAS
     *
     * @mbg.generated Mon Oct 04 11:51:44 CEST 2021
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
     * This method corresponds to the database table USCGAE.SCS_CONJUNTOGUARDIAS
     *
     * @mbg.generated Mon Oct 04 11:51:44 CEST 2021
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_CONJUNTOGUARDIAS
     *
     * @mbg.generated Mon Oct 04 11:51:44 CEST 2021
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.SCS_CONJUNTOGUARDIAS
     *
     * @mbg.generated Mon Oct 04 11:51:44 CEST 2021
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

        public Criteria andIdconjuntoguardiaIsNull() {
            addCriterion("IDCONJUNTOGUARDIA is null");
            return (Criteria) this;
        }

        public Criteria andIdconjuntoguardiaIsNotNull() {
            addCriterion("IDCONJUNTOGUARDIA is not null");
            return (Criteria) this;
        }

        public Criteria andIdconjuntoguardiaEqualTo(Short value) {
            addCriterion("IDCONJUNTOGUARDIA =", value, "idconjuntoguardia");
            return (Criteria) this;
        }

        public Criteria andIdconjuntoguardiaNotEqualTo(Short value) {
            addCriterion("IDCONJUNTOGUARDIA <>", value, "idconjuntoguardia");
            return (Criteria) this;
        }

        public Criteria andIdconjuntoguardiaGreaterThan(Short value) {
            addCriterion("IDCONJUNTOGUARDIA >", value, "idconjuntoguardia");
            return (Criteria) this;
        }

        public Criteria andIdconjuntoguardiaGreaterThanOrEqualTo(Short value) {
            addCriterion("IDCONJUNTOGUARDIA >=", value, "idconjuntoguardia");
            return (Criteria) this;
        }

        public Criteria andIdconjuntoguardiaLessThan(Short value) {
            addCriterion("IDCONJUNTOGUARDIA <", value, "idconjuntoguardia");
            return (Criteria) this;
        }

        public Criteria andIdconjuntoguardiaLessThanOrEqualTo(Short value) {
            addCriterion("IDCONJUNTOGUARDIA <=", value, "idconjuntoguardia");
            return (Criteria) this;
        }

        public Criteria andIdconjuntoguardiaIn(List<Short> values) {
            addCriterion("IDCONJUNTOGUARDIA in", values, "idconjuntoguardia");
            return (Criteria) this;
        }

        public Criteria andIdconjuntoguardiaNotIn(List<Short> values) {
            addCriterion("IDCONJUNTOGUARDIA not in", values, "idconjuntoguardia");
            return (Criteria) this;
        }

        public Criteria andIdconjuntoguardiaBetween(Short value1, Short value2) {
            addCriterion("IDCONJUNTOGUARDIA between", value1, value2, "idconjuntoguardia");
            return (Criteria) this;
        }

        public Criteria andIdconjuntoguardiaNotBetween(Short value1, Short value2) {
            addCriterion("IDCONJUNTOGUARDIA not between", value1, value2, "idconjuntoguardia");
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

        public Criteria andLugarIsNull() {
            addCriterion("LUGAR is null");
            return (Criteria) this;
        }

        public Criteria andLugarIsNotNull() {
            addCriterion("LUGAR is not null");
            return (Criteria) this;
        }

        public Criteria andLugarEqualTo(String value) {
            addCriterion("LUGAR =", value, "lugar");
            return (Criteria) this;
        }

        public Criteria andLugarNotEqualTo(String value) {
            addCriterion("LUGAR <>", value, "lugar");
            return (Criteria) this;
        }

        public Criteria andLugarGreaterThan(String value) {
            addCriterion("LUGAR >", value, "lugar");
            return (Criteria) this;
        }

        public Criteria andLugarGreaterThanOrEqualTo(String value) {
            addCriterion("LUGAR >=", value, "lugar");
            return (Criteria) this;
        }

        public Criteria andLugarLessThan(String value) {
            addCriterion("LUGAR <", value, "lugar");
            return (Criteria) this;
        }

        public Criteria andLugarLessThanOrEqualTo(String value) {
            addCriterion("LUGAR <=", value, "lugar");
            return (Criteria) this;
        }

        public Criteria andLugarLike(String value) {
            addCriterion("LUGAR like", value, "lugar");
            return (Criteria) this;
        }

        public Criteria andLugarNotLike(String value) {
            addCriterion("LUGAR not like", value, "lugar");
            return (Criteria) this;
        }

        public Criteria andLugarIn(List<String> values) {
            addCriterion("LUGAR in", values, "lugar");
            return (Criteria) this;
        }

        public Criteria andLugarNotIn(List<String> values) {
            addCriterion("LUGAR not in", values, "lugar");
            return (Criteria) this;
        }

        public Criteria andLugarBetween(String value1, String value2) {
            addCriterion("LUGAR between", value1, value2, "lugar");
            return (Criteria) this;
        }

        public Criteria andLugarNotBetween(String value1, String value2) {
            addCriterion("LUGAR not between", value1, value2, "lugar");
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

        public Criteria andTipoIsNull() {
            addCriterion("TIPO is null");
            return (Criteria) this;
        }

        public Criteria andTipoIsNotNull() {
            addCriterion("TIPO is not null");
            return (Criteria) this;
        }

        public Criteria andTipoEqualTo(Short value) {
            addCriterion("TIPO =", value, "tipo");
            return (Criteria) this;
        }

        public Criteria andTipoNotEqualTo(Short value) {
            addCriterion("TIPO <>", value, "tipo");
            return (Criteria) this;
        }

        public Criteria andTipoGreaterThan(Short value) {
            addCriterion("TIPO >", value, "tipo");
            return (Criteria) this;
        }

        public Criteria andTipoGreaterThanOrEqualTo(Short value) {
            addCriterion("TIPO >=", value, "tipo");
            return (Criteria) this;
        }

        public Criteria andTipoLessThan(Short value) {
            addCriterion("TIPO <", value, "tipo");
            return (Criteria) this;
        }

        public Criteria andTipoLessThanOrEqualTo(Short value) {
            addCriterion("TIPO <=", value, "tipo");
            return (Criteria) this;
        }

        public Criteria andTipoIn(List<Short> values) {
            addCriterion("TIPO in", values, "tipo");
            return (Criteria) this;
        }

        public Criteria andTipoNotIn(List<Short> values) {
            addCriterion("TIPO not in", values, "tipo");
            return (Criteria) this;
        }

        public Criteria andTipoBetween(Short value1, Short value2) {
            addCriterion("TIPO between", value1, value2, "tipo");
            return (Criteria) this;
        }

        public Criteria andTipoNotBetween(Short value1, Short value2) {
            addCriterion("TIPO not between", value1, value2, "tipo");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.SCS_CONJUNTOGUARDIAS
     *
     * @mbg.generated do_not_delete_during_merge Mon Oct 04 11:51:44 CEST 2021
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.SCS_CONJUNTOGUARDIAS
     *
     * @mbg.generated Mon Oct 04 11:51:44 CEST 2021
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