package org.itcgae.siga.db.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScsMaestroretencionesExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE_DESA.SCS_MAESTRORETENCIONES
     *
     * @mbg.generated Thu Jun 14 13:33:37 CEST 2018
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE_DESA.SCS_MAESTRORETENCIONES
     *
     * @mbg.generated Thu Jun 14 13:33:37 CEST 2018
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE_DESA.SCS_MAESTRORETENCIONES
     *
     * @mbg.generated Thu Jun 14 13:33:37 CEST 2018
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.SCS_MAESTRORETENCIONES
     *
     * @mbg.generated Thu Jun 14 13:33:37 CEST 2018
     */
    public ScsMaestroretencionesExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.SCS_MAESTRORETENCIONES
     *
     * @mbg.generated Thu Jun 14 13:33:37 CEST 2018
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.SCS_MAESTRORETENCIONES
     *
     * @mbg.generated Thu Jun 14 13:33:37 CEST 2018
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.SCS_MAESTRORETENCIONES
     *
     * @mbg.generated Thu Jun 14 13:33:37 CEST 2018
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.SCS_MAESTRORETENCIONES
     *
     * @mbg.generated Thu Jun 14 13:33:37 CEST 2018
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.SCS_MAESTRORETENCIONES
     *
     * @mbg.generated Thu Jun 14 13:33:37 CEST 2018
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.SCS_MAESTRORETENCIONES
     *
     * @mbg.generated Thu Jun 14 13:33:37 CEST 2018
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.SCS_MAESTRORETENCIONES
     *
     * @mbg.generated Thu Jun 14 13:33:37 CEST 2018
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.SCS_MAESTRORETENCIONES
     *
     * @mbg.generated Thu Jun 14 13:33:37 CEST 2018
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
     * This method corresponds to the database table USCGAE_DESA.SCS_MAESTRORETENCIONES
     *
     * @mbg.generated Thu Jun 14 13:33:37 CEST 2018
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_DESA.SCS_MAESTRORETENCIONES
     *
     * @mbg.generated Thu Jun 14 13:33:37 CEST 2018
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE_DESA.SCS_MAESTRORETENCIONES
     *
     * @mbg.generated Thu Jun 14 13:33:37 CEST 2018
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

        public Criteria andIdretencionIsNull() {
            addCriterion("IDRETENCION is null");
            return (Criteria) this;
        }

        public Criteria andIdretencionIsNotNull() {
            addCriterion("IDRETENCION is not null");
            return (Criteria) this;
        }

        public Criteria andIdretencionEqualTo(Integer value) {
            addCriterion("IDRETENCION =", value, "idretencion");
            return (Criteria) this;
        }

        public Criteria andIdretencionNotEqualTo(Integer value) {
            addCriterion("IDRETENCION <>", value, "idretencion");
            return (Criteria) this;
        }

        public Criteria andIdretencionGreaterThan(Integer value) {
            addCriterion("IDRETENCION >", value, "idretencion");
            return (Criteria) this;
        }

        public Criteria andIdretencionGreaterThanOrEqualTo(Integer value) {
            addCriterion("IDRETENCION >=", value, "idretencion");
            return (Criteria) this;
        }

        public Criteria andIdretencionLessThan(Integer value) {
            addCriterion("IDRETENCION <", value, "idretencion");
            return (Criteria) this;
        }

        public Criteria andIdretencionLessThanOrEqualTo(Integer value) {
            addCriterion("IDRETENCION <=", value, "idretencion");
            return (Criteria) this;
        }

        public Criteria andIdretencionIn(List<Integer> values) {
            addCriterion("IDRETENCION in", values, "idretencion");
            return (Criteria) this;
        }

        public Criteria andIdretencionNotIn(List<Integer> values) {
            addCriterion("IDRETENCION not in", values, "idretencion");
            return (Criteria) this;
        }

        public Criteria andIdretencionBetween(Integer value1, Integer value2) {
            addCriterion("IDRETENCION between", value1, value2, "idretencion");
            return (Criteria) this;
        }

        public Criteria andIdretencionNotBetween(Integer value1, Integer value2) {
            addCriterion("IDRETENCION not between", value1, value2, "idretencion");
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

        public Criteria andRetencionIsNull() {
            addCriterion("RETENCION is null");
            return (Criteria) this;
        }

        public Criteria andRetencionIsNotNull() {
            addCriterion("RETENCION is not null");
            return (Criteria) this;
        }

        public Criteria andRetencionEqualTo(BigDecimal value) {
            addCriterion("RETENCION =", value, "retencion");
            return (Criteria) this;
        }

        public Criteria andRetencionNotEqualTo(BigDecimal value) {
            addCriterion("RETENCION <>", value, "retencion");
            return (Criteria) this;
        }

        public Criteria andRetencionGreaterThan(BigDecimal value) {
            addCriterion("RETENCION >", value, "retencion");
            return (Criteria) this;
        }

        public Criteria andRetencionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RETENCION >=", value, "retencion");
            return (Criteria) this;
        }

        public Criteria andRetencionLessThan(BigDecimal value) {
            addCriterion("RETENCION <", value, "retencion");
            return (Criteria) this;
        }

        public Criteria andRetencionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RETENCION <=", value, "retencion");
            return (Criteria) this;
        }

        public Criteria andRetencionIn(List<BigDecimal> values) {
            addCriterion("RETENCION in", values, "retencion");
            return (Criteria) this;
        }

        public Criteria andRetencionNotIn(List<BigDecimal> values) {
            addCriterion("RETENCION not in", values, "retencion");
            return (Criteria) this;
        }

        public Criteria andRetencionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RETENCION between", value1, value2, "retencion");
            return (Criteria) this;
        }

        public Criteria andRetencionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RETENCION not between", value1, value2, "retencion");
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

        public Criteria andLetranifsociedadIsNull() {
            addCriterion("LETRANIFSOCIEDAD is null");
            return (Criteria) this;
        }

        public Criteria andLetranifsociedadIsNotNull() {
            addCriterion("LETRANIFSOCIEDAD is not null");
            return (Criteria) this;
        }

        public Criteria andLetranifsociedadEqualTo(String value) {
            addCriterion("LETRANIFSOCIEDAD =", value, "letranifsociedad");
            return (Criteria) this;
        }

        public Criteria andLetranifsociedadNotEqualTo(String value) {
            addCriterion("LETRANIFSOCIEDAD <>", value, "letranifsociedad");
            return (Criteria) this;
        }

        public Criteria andLetranifsociedadGreaterThan(String value) {
            addCriterion("LETRANIFSOCIEDAD >", value, "letranifsociedad");
            return (Criteria) this;
        }

        public Criteria andLetranifsociedadGreaterThanOrEqualTo(String value) {
            addCriterion("LETRANIFSOCIEDAD >=", value, "letranifsociedad");
            return (Criteria) this;
        }

        public Criteria andLetranifsociedadLessThan(String value) {
            addCriterion("LETRANIFSOCIEDAD <", value, "letranifsociedad");
            return (Criteria) this;
        }

        public Criteria andLetranifsociedadLessThanOrEqualTo(String value) {
            addCriterion("LETRANIFSOCIEDAD <=", value, "letranifsociedad");
            return (Criteria) this;
        }

        public Criteria andLetranifsociedadLike(String value) {
            addCriterion("LETRANIFSOCIEDAD like", value, "letranifsociedad");
            return (Criteria) this;
        }

        public Criteria andLetranifsociedadNotLike(String value) {
            addCriterion("LETRANIFSOCIEDAD not like", value, "letranifsociedad");
            return (Criteria) this;
        }

        public Criteria andLetranifsociedadIn(List<String> values) {
            addCriterion("LETRANIFSOCIEDAD in", values, "letranifsociedad");
            return (Criteria) this;
        }

        public Criteria andLetranifsociedadNotIn(List<String> values) {
            addCriterion("LETRANIFSOCIEDAD not in", values, "letranifsociedad");
            return (Criteria) this;
        }

        public Criteria andLetranifsociedadBetween(String value1, String value2) {
            addCriterion("LETRANIFSOCIEDAD between", value1, value2, "letranifsociedad");
            return (Criteria) this;
        }

        public Criteria andLetranifsociedadNotBetween(String value1, String value2) {
            addCriterion("LETRANIFSOCIEDAD not between", value1, value2, "letranifsociedad");
            return (Criteria) this;
        }

        public Criteria andPordefectoIsNull() {
            addCriterion("PORDEFECTO is null");
            return (Criteria) this;
        }

        public Criteria andPordefectoIsNotNull() {
            addCriterion("PORDEFECTO is not null");
            return (Criteria) this;
        }

        public Criteria andPordefectoEqualTo(String value) {
            addCriterion("PORDEFECTO =", value, "pordefecto");
            return (Criteria) this;
        }

        public Criteria andPordefectoNotEqualTo(String value) {
            addCriterion("PORDEFECTO <>", value, "pordefecto");
            return (Criteria) this;
        }

        public Criteria andPordefectoGreaterThan(String value) {
            addCriterion("PORDEFECTO >", value, "pordefecto");
            return (Criteria) this;
        }

        public Criteria andPordefectoGreaterThanOrEqualTo(String value) {
            addCriterion("PORDEFECTO >=", value, "pordefecto");
            return (Criteria) this;
        }

        public Criteria andPordefectoLessThan(String value) {
            addCriterion("PORDEFECTO <", value, "pordefecto");
            return (Criteria) this;
        }

        public Criteria andPordefectoLessThanOrEqualTo(String value) {
            addCriterion("PORDEFECTO <=", value, "pordefecto");
            return (Criteria) this;
        }

        public Criteria andPordefectoLike(String value) {
            addCriterion("PORDEFECTO like", value, "pordefecto");
            return (Criteria) this;
        }

        public Criteria andPordefectoNotLike(String value) {
            addCriterion("PORDEFECTO not like", value, "pordefecto");
            return (Criteria) this;
        }

        public Criteria andPordefectoIn(List<String> values) {
            addCriterion("PORDEFECTO in", values, "pordefecto");
            return (Criteria) this;
        }

        public Criteria andPordefectoNotIn(List<String> values) {
            addCriterion("PORDEFECTO not in", values, "pordefecto");
            return (Criteria) this;
        }

        public Criteria andPordefectoBetween(String value1, String value2) {
            addCriterion("PORDEFECTO between", value1, value2, "pordefecto");
            return (Criteria) this;
        }

        public Criteria andPordefectoNotBetween(String value1, String value2) {
            addCriterion("PORDEFECTO not between", value1, value2, "pordefecto");
            return (Criteria) this;
        }

        public Criteria andClavem190IsNull() {
            addCriterion("CLAVEM190 is null");
            return (Criteria) this;
        }

        public Criteria andClavem190IsNotNull() {
            addCriterion("CLAVEM190 is not null");
            return (Criteria) this;
        }

        public Criteria andClavem190EqualTo(String value) {
            addCriterion("CLAVEM190 =", value, "clavem190");
            return (Criteria) this;
        }

        public Criteria andClavem190NotEqualTo(String value) {
            addCriterion("CLAVEM190 <>", value, "clavem190");
            return (Criteria) this;
        }

        public Criteria andClavem190GreaterThan(String value) {
            addCriterion("CLAVEM190 >", value, "clavem190");
            return (Criteria) this;
        }

        public Criteria andClavem190GreaterThanOrEqualTo(String value) {
            addCriterion("CLAVEM190 >=", value, "clavem190");
            return (Criteria) this;
        }

        public Criteria andClavem190LessThan(String value) {
            addCriterion("CLAVEM190 <", value, "clavem190");
            return (Criteria) this;
        }

        public Criteria andClavem190LessThanOrEqualTo(String value) {
            addCriterion("CLAVEM190 <=", value, "clavem190");
            return (Criteria) this;
        }

        public Criteria andClavem190Like(String value) {
            addCriterion("CLAVEM190 like", value, "clavem190");
            return (Criteria) this;
        }

        public Criteria andClavem190NotLike(String value) {
            addCriterion("CLAVEM190 not like", value, "clavem190");
            return (Criteria) this;
        }

        public Criteria andClavem190In(List<String> values) {
            addCriterion("CLAVEM190 in", values, "clavem190");
            return (Criteria) this;
        }

        public Criteria andClavem190NotIn(List<String> values) {
            addCriterion("CLAVEM190 not in", values, "clavem190");
            return (Criteria) this;
        }

        public Criteria andClavem190Between(String value1, String value2) {
            addCriterion("CLAVEM190 between", value1, value2, "clavem190");
            return (Criteria) this;
        }

        public Criteria andClavem190NotBetween(String value1, String value2) {
            addCriterion("CLAVEM190 not between", value1, value2, "clavem190");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE_DESA.SCS_MAESTRORETENCIONES
     *
     * @mbg.generated do_not_delete_during_merge Thu Jun 14 13:33:37 CEST 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE_DESA.SCS_MAESTRORETENCIONES
     *
     * @mbg.generated Thu Jun 14 13:33:37 CEST 2018
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