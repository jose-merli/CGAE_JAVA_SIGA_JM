package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GenMtiposErrorExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE_DESA.GEN_MTIPOS_ERROR
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE_DESA.GEN_MTIPOS_ERROR
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE_DESA.GEN_MTIPOS_ERROR
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_MTIPOS_ERROR
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public GenMtiposErrorExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_MTIPOS_ERROR
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_MTIPOS_ERROR
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_MTIPOS_ERROR
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_MTIPOS_ERROR
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_MTIPOS_ERROR
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_MTIPOS_ERROR
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_MTIPOS_ERROR
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_MTIPOS_ERROR
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
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
     * This method corresponds to the database table USCGAE_DESA.GEN_MTIPOS_ERROR
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.GEN_MTIPOS_ERROR
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE_DESA.GEN_MTIPOS_ERROR
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
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

        public Criteria andIdcategoriaerrorIsNull() {
            addCriterion("IDCATEGORIAERROR is null");
            return (Criteria) this;
        }

        public Criteria andIdcategoriaerrorIsNotNull() {
            addCriterion("IDCATEGORIAERROR is not null");
            return (Criteria) this;
        }

        public Criteria andIdcategoriaerrorEqualTo(String value) {
            addCriterion("IDCATEGORIAERROR =", value, "idcategoriaerror");
            return (Criteria) this;
        }

        public Criteria andIdcategoriaerrorNotEqualTo(String value) {
            addCriterion("IDCATEGORIAERROR <>", value, "idcategoriaerror");
            return (Criteria) this;
        }

        public Criteria andIdcategoriaerrorGreaterThan(String value) {
            addCriterion("IDCATEGORIAERROR >", value, "idcategoriaerror");
            return (Criteria) this;
        }

        public Criteria andIdcategoriaerrorGreaterThanOrEqualTo(String value) {
            addCriterion("IDCATEGORIAERROR >=", value, "idcategoriaerror");
            return (Criteria) this;
        }

        public Criteria andIdcategoriaerrorLessThan(String value) {
            addCriterion("IDCATEGORIAERROR <", value, "idcategoriaerror");
            return (Criteria) this;
        }

        public Criteria andIdcategoriaerrorLessThanOrEqualTo(String value) {
            addCriterion("IDCATEGORIAERROR <=", value, "idcategoriaerror");
            return (Criteria) this;
        }

        public Criteria andIdcategoriaerrorLike(String value) {
            addCriterion("IDCATEGORIAERROR like", value, "idcategoriaerror");
            return (Criteria) this;
        }

        public Criteria andIdcategoriaerrorNotLike(String value) {
            addCriterion("IDCATEGORIAERROR not like", value, "idcategoriaerror");
            return (Criteria) this;
        }

        public Criteria andIdcategoriaerrorIn(List<String> values) {
            addCriterion("IDCATEGORIAERROR in", values, "idcategoriaerror");
            return (Criteria) this;
        }

        public Criteria andIdcategoriaerrorNotIn(List<String> values) {
            addCriterion("IDCATEGORIAERROR not in", values, "idcategoriaerror");
            return (Criteria) this;
        }

        public Criteria andIdcategoriaerrorBetween(String value1, String value2) {
            addCriterion("IDCATEGORIAERROR between", value1, value2, "idcategoriaerror");
            return (Criteria) this;
        }

        public Criteria andIdcategoriaerrorNotBetween(String value1, String value2) {
            addCriterion("IDCATEGORIAERROR not between", value1, value2, "idcategoriaerror");
            return (Criteria) this;
        }

        public Criteria andIdtipoerrorIsNull() {
            addCriterion("IDTIPOERROR is null");
            return (Criteria) this;
        }

        public Criteria andIdtipoerrorIsNotNull() {
            addCriterion("IDTIPOERROR is not null");
            return (Criteria) this;
        }

        public Criteria andIdtipoerrorEqualTo(String value) {
            addCriterion("IDTIPOERROR =", value, "idtipoerror");
            return (Criteria) this;
        }

        public Criteria andIdtipoerrorNotEqualTo(String value) {
            addCriterion("IDTIPOERROR <>", value, "idtipoerror");
            return (Criteria) this;
        }

        public Criteria andIdtipoerrorGreaterThan(String value) {
            addCriterion("IDTIPOERROR >", value, "idtipoerror");
            return (Criteria) this;
        }

        public Criteria andIdtipoerrorGreaterThanOrEqualTo(String value) {
            addCriterion("IDTIPOERROR >=", value, "idtipoerror");
            return (Criteria) this;
        }

        public Criteria andIdtipoerrorLessThan(String value) {
            addCriterion("IDTIPOERROR <", value, "idtipoerror");
            return (Criteria) this;
        }

        public Criteria andIdtipoerrorLessThanOrEqualTo(String value) {
            addCriterion("IDTIPOERROR <=", value, "idtipoerror");
            return (Criteria) this;
        }

        public Criteria andIdtipoerrorLike(String value) {
            addCriterion("IDTIPOERROR like", value, "idtipoerror");
            return (Criteria) this;
        }

        public Criteria andIdtipoerrorNotLike(String value) {
            addCriterion("IDTIPOERROR not like", value, "idtipoerror");
            return (Criteria) this;
        }

        public Criteria andIdtipoerrorIn(List<String> values) {
            addCriterion("IDTIPOERROR in", values, "idtipoerror");
            return (Criteria) this;
        }

        public Criteria andIdtipoerrorNotIn(List<String> values) {
            addCriterion("IDTIPOERROR not in", values, "idtipoerror");
            return (Criteria) this;
        }

        public Criteria andIdtipoerrorBetween(String value1, String value2) {
            addCriterion("IDTIPOERROR between", value1, value2, "idtipoerror");
            return (Criteria) this;
        }

        public Criteria andIdtipoerrorNotBetween(String value1, String value2) {
            addCriterion("IDTIPOERROR not between", value1, value2, "idtipoerror");
            return (Criteria) this;
        }

        public Criteria andPersistenteIsNull() {
            addCriterion("PERSISTENTE is null");
            return (Criteria) this;
        }

        public Criteria andPersistenteIsNotNull() {
            addCriterion("PERSISTENTE is not null");
            return (Criteria) this;
        }

        public Criteria andPersistenteEqualTo(Long value) {
            addCriterion("PERSISTENTE =", value, "persistente");
            return (Criteria) this;
        }

        public Criteria andPersistenteNotEqualTo(Long value) {
            addCriterion("PERSISTENTE <>", value, "persistente");
            return (Criteria) this;
        }

        public Criteria andPersistenteGreaterThan(Long value) {
            addCriterion("PERSISTENTE >", value, "persistente");
            return (Criteria) this;
        }

        public Criteria andPersistenteGreaterThanOrEqualTo(Long value) {
            addCriterion("PERSISTENTE >=", value, "persistente");
            return (Criteria) this;
        }

        public Criteria andPersistenteLessThan(Long value) {
            addCriterion("PERSISTENTE <", value, "persistente");
            return (Criteria) this;
        }

        public Criteria andPersistenteLessThanOrEqualTo(Long value) {
            addCriterion("PERSISTENTE <=", value, "persistente");
            return (Criteria) this;
        }

        public Criteria andPersistenteIn(List<Long> values) {
            addCriterion("PERSISTENTE in", values, "persistente");
            return (Criteria) this;
        }

        public Criteria andPersistenteNotIn(List<Long> values) {
            addCriterion("PERSISTENTE not in", values, "persistente");
            return (Criteria) this;
        }

        public Criteria andPersistenteBetween(Long value1, Long value2) {
            addCriterion("PERSISTENTE between", value1, value2, "persistente");
            return (Criteria) this;
        }

        public Criteria andPersistenteNotBetween(Long value1, Long value2) {
            addCriterion("PERSISTENTE not between", value1, value2, "persistente");
            return (Criteria) this;
        }

        public Criteria andBorradoIsNull() {
            addCriterion("BORRADO is null");
            return (Criteria) this;
        }

        public Criteria andBorradoIsNotNull() {
            addCriterion("BORRADO is not null");
            return (Criteria) this;
        }

        public Criteria andBorradoEqualTo(String value) {
            addCriterion("BORRADO =", value, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoNotEqualTo(String value) {
            addCriterion("BORRADO <>", value, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoGreaterThan(String value) {
            addCriterion("BORRADO >", value, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoGreaterThanOrEqualTo(String value) {
            addCriterion("BORRADO >=", value, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoLessThan(String value) {
            addCriterion("BORRADO <", value, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoLessThanOrEqualTo(String value) {
            addCriterion("BORRADO <=", value, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoLike(String value) {
            addCriterion("BORRADO like", value, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoNotLike(String value) {
            addCriterion("BORRADO not like", value, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoIn(List<String> values) {
            addCriterion("BORRADO in", values, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoNotIn(List<String> values) {
            addCriterion("BORRADO not in", values, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoBetween(String value1, String value2) {
            addCriterion("BORRADO between", value1, value2, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoNotBetween(String value1, String value2) {
            addCriterion("BORRADO not between", value1, value2, "borrado");
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
     * This class corresponds to the database table USCGAE_DESA.GEN_MTIPOS_ERROR
     *
     * @mbg.generated do_not_delete_during_merge Wed Mar 14 18:23:45 CET 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE_DESA.GEN_MTIPOS_ERROR
     *
     * @mbg.generated Wed Mar 14 18:23:45 CET 2018
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