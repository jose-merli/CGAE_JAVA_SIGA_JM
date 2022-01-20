package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FacTipocliincluidoenseriefacExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.FAC_TIPOCLIINCLUIDOENSERIEFAC
     *
     * @mbg.generated Tue Oct 26 15:43:20 CEST 2021
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.FAC_TIPOCLIINCLUIDOENSERIEFAC
     *
     * @mbg.generated Tue Oct 26 15:43:20 CEST 2021
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.FAC_TIPOCLIINCLUIDOENSERIEFAC
     *
     * @mbg.generated Tue Oct 26 15:43:20 CEST 2021
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_TIPOCLIINCLUIDOENSERIEFAC
     *
     * @mbg.generated Tue Oct 26 15:43:20 CEST 2021
     */
    public FacTipocliincluidoenseriefacExample() {
        oredCriteria = new ArrayList<>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_TIPOCLIINCLUIDOENSERIEFAC
     *
     * @mbg.generated Tue Oct 26 15:43:20 CEST 2021
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_TIPOCLIINCLUIDOENSERIEFAC
     *
     * @mbg.generated Tue Oct 26 15:43:20 CEST 2021
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_TIPOCLIINCLUIDOENSERIEFAC
     *
     * @mbg.generated Tue Oct 26 15:43:20 CEST 2021
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_TIPOCLIINCLUIDOENSERIEFAC
     *
     * @mbg.generated Tue Oct 26 15:43:20 CEST 2021
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_TIPOCLIINCLUIDOENSERIEFAC
     *
     * @mbg.generated Tue Oct 26 15:43:20 CEST 2021
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_TIPOCLIINCLUIDOENSERIEFAC
     *
     * @mbg.generated Tue Oct 26 15:43:20 CEST 2021
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_TIPOCLIINCLUIDOENSERIEFAC
     *
     * @mbg.generated Tue Oct 26 15:43:20 CEST 2021
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_TIPOCLIINCLUIDOENSERIEFAC
     *
     * @mbg.generated Tue Oct 26 15:43:20 CEST 2021
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
     * This method corresponds to the database table USCGAE.FAC_TIPOCLIINCLUIDOENSERIEFAC
     *
     * @mbg.generated Tue Oct 26 15:43:20 CEST 2021
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.FAC_TIPOCLIINCLUIDOENSERIEFAC
     *
     * @mbg.generated Tue Oct 26 15:43:20 CEST 2021
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.FAC_TIPOCLIINCLUIDOENSERIEFAC
     *
     * @mbg.generated Tue Oct 26 15:43:20 CEST 2021
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

        public Criteria andIdseriefacturacionIsNull() {
            addCriterion("IDSERIEFACTURACION is null");
            return (Criteria) this;
        }

        public Criteria andIdseriefacturacionIsNotNull() {
            addCriterion("IDSERIEFACTURACION is not null");
            return (Criteria) this;
        }

        public Criteria andIdseriefacturacionEqualTo(Long value) {
            addCriterion("IDSERIEFACTURACION =", value, "idseriefacturacion");
            return (Criteria) this;
        }

        public Criteria andIdseriefacturacionNotEqualTo(Long value) {
            addCriterion("IDSERIEFACTURACION <>", value, "idseriefacturacion");
            return (Criteria) this;
        }

        public Criteria andIdseriefacturacionGreaterThan(Long value) {
            addCriterion("IDSERIEFACTURACION >", value, "idseriefacturacion");
            return (Criteria) this;
        }

        public Criteria andIdseriefacturacionGreaterThanOrEqualTo(Long value) {
            addCriterion("IDSERIEFACTURACION >=", value, "idseriefacturacion");
            return (Criteria) this;
        }

        public Criteria andIdseriefacturacionLessThan(Long value) {
            addCriterion("IDSERIEFACTURACION <", value, "idseriefacturacion");
            return (Criteria) this;
        }

        public Criteria andIdseriefacturacionLessThanOrEqualTo(Long value) {
            addCriterion("IDSERIEFACTURACION <=", value, "idseriefacturacion");
            return (Criteria) this;
        }

        public Criteria andIdseriefacturacionIn(List<Long> values) {
            addCriterion("IDSERIEFACTURACION in", values, "idseriefacturacion");
            return (Criteria) this;
        }

        public Criteria andIdseriefacturacionNotIn(List<Long> values) {
            addCriterion("IDSERIEFACTURACION not in", values, "idseriefacturacion");
            return (Criteria) this;
        }

        public Criteria andIdseriefacturacionBetween(Long value1, Long value2) {
            addCriterion("IDSERIEFACTURACION between", value1, value2, "idseriefacturacion");
            return (Criteria) this;
        }

        public Criteria andIdseriefacturacionNotBetween(Long value1, Long value2) {
            addCriterion("IDSERIEFACTURACION not between", value1, value2, "idseriefacturacion");
            return (Criteria) this;
        }

        public Criteria andIdinstitucionGrupoIsNull() {
            addCriterion("IDINSTITUCION_GRUPO is null");
            return (Criteria) this;
        }

        public Criteria andIdinstitucionGrupoIsNotNull() {
            addCriterion("IDINSTITUCION_GRUPO is not null");
            return (Criteria) this;
        }

        public Criteria andIdinstitucionGrupoEqualTo(Short value) {
            addCriterion("IDINSTITUCION_GRUPO =", value, "idinstitucionGrupo");
            return (Criteria) this;
        }

        public Criteria andIdinstitucionGrupoNotEqualTo(Short value) {
            addCriterion("IDINSTITUCION_GRUPO <>", value, "idinstitucionGrupo");
            return (Criteria) this;
        }

        public Criteria andIdinstitucionGrupoGreaterThan(Short value) {
            addCriterion("IDINSTITUCION_GRUPO >", value, "idinstitucionGrupo");
            return (Criteria) this;
        }

        public Criteria andIdinstitucionGrupoGreaterThanOrEqualTo(Short value) {
            addCriterion("IDINSTITUCION_GRUPO >=", value, "idinstitucionGrupo");
            return (Criteria) this;
        }

        public Criteria andIdinstitucionGrupoLessThan(Short value) {
            addCriterion("IDINSTITUCION_GRUPO <", value, "idinstitucionGrupo");
            return (Criteria) this;
        }

        public Criteria andIdinstitucionGrupoLessThanOrEqualTo(Short value) {
            addCriterion("IDINSTITUCION_GRUPO <=", value, "idinstitucionGrupo");
            return (Criteria) this;
        }

        public Criteria andIdinstitucionGrupoIn(List<Short> values) {
            addCriterion("IDINSTITUCION_GRUPO in", values, "idinstitucionGrupo");
            return (Criteria) this;
        }

        public Criteria andIdinstitucionGrupoNotIn(List<Short> values) {
            addCriterion("IDINSTITUCION_GRUPO not in", values, "idinstitucionGrupo");
            return (Criteria) this;
        }

        public Criteria andIdinstitucionGrupoBetween(Short value1, Short value2) {
            addCriterion("IDINSTITUCION_GRUPO between", value1, value2, "idinstitucionGrupo");
            return (Criteria) this;
        }

        public Criteria andIdinstitucionGrupoNotBetween(Short value1, Short value2) {
            addCriterion("IDINSTITUCION_GRUPO not between", value1, value2, "idinstitucionGrupo");
            return (Criteria) this;
        }

        public Criteria andIdgrupoIsNull() {
            addCriterion("IDGRUPO is null");
            return (Criteria) this;
        }

        public Criteria andIdgrupoIsNotNull() {
            addCriterion("IDGRUPO is not null");
            return (Criteria) this;
        }

        public Criteria andIdgrupoEqualTo(Short value) {
            addCriterion("IDGRUPO =", value, "idgrupo");
            return (Criteria) this;
        }

        public Criteria andIdgrupoNotEqualTo(Short value) {
            addCriterion("IDGRUPO <>", value, "idgrupo");
            return (Criteria) this;
        }

        public Criteria andIdgrupoGreaterThan(Short value) {
            addCriterion("IDGRUPO >", value, "idgrupo");
            return (Criteria) this;
        }

        public Criteria andIdgrupoGreaterThanOrEqualTo(Short value) {
            addCriterion("IDGRUPO >=", value, "idgrupo");
            return (Criteria) this;
        }

        public Criteria andIdgrupoLessThan(Short value) {
            addCriterion("IDGRUPO <", value, "idgrupo");
            return (Criteria) this;
        }

        public Criteria andIdgrupoLessThanOrEqualTo(Short value) {
            addCriterion("IDGRUPO <=", value, "idgrupo");
            return (Criteria) this;
        }

        public Criteria andIdgrupoIn(List<Short> values) {
            addCriterion("IDGRUPO in", values, "idgrupo");
            return (Criteria) this;
        }

        public Criteria andIdgrupoNotIn(List<Short> values) {
            addCriterion("IDGRUPO not in", values, "idgrupo");
            return (Criteria) this;
        }

        public Criteria andIdgrupoBetween(Short value1, Short value2) {
            addCriterion("IDGRUPO between", value1, value2, "idgrupo");
            return (Criteria) this;
        }

        public Criteria andIdgrupoNotBetween(Short value1, Short value2) {
            addCriterion("IDGRUPO not between", value1, value2, "idgrupo");
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
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.FAC_TIPOCLIINCLUIDOENSERIEFAC
     *
     * @mbg.generated do_not_delete_during_merge Tue Oct 26 15:43:20 CEST 2021
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.FAC_TIPOCLIINCLUIDOENSERIEFAC
     *
     * @mbg.generated Tue Oct 26 15:43:20 CEST 2021
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