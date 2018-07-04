package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CargasWsPaginaExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.CARGAS_WS_PAGINA
     *
     * @mbg.generated Thu Jun 28 12:30:00 CEST 2018
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.CARGAS_WS_PAGINA
     *
     * @mbg.generated Thu Jun 28 12:30:00 CEST 2018
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.CARGAS_WS_PAGINA
     *
     * @mbg.generated Thu Jun 28 12:30:00 CEST 2018
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CARGAS_WS_PAGINA
     *
     * @mbg.generated Thu Jun 28 12:30:00 CEST 2018
     */
    public CargasWsPaginaExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CARGAS_WS_PAGINA
     *
     * @mbg.generated Thu Jun 28 12:30:00 CEST 2018
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CARGAS_WS_PAGINA
     *
     * @mbg.generated Thu Jun 28 12:30:00 CEST 2018
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CARGAS_WS_PAGINA
     *
     * @mbg.generated Thu Jun 28 12:30:00 CEST 2018
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CARGAS_WS_PAGINA
     *
     * @mbg.generated Thu Jun 28 12:30:00 CEST 2018
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CARGAS_WS_PAGINA
     *
     * @mbg.generated Thu Jun 28 12:30:00 CEST 2018
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CARGAS_WS_PAGINA
     *
     * @mbg.generated Thu Jun 28 12:30:00 CEST 2018
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CARGAS_WS_PAGINA
     *
     * @mbg.generated Thu Jun 28 12:30:00 CEST 2018
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CARGAS_WS_PAGINA
     *
     * @mbg.generated Thu Jun 28 12:30:00 CEST 2018
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
     * This method corresponds to the database table USCGAE.CARGAS_WS_PAGINA
     *
     * @mbg.generated Thu Jun 28 12:30:00 CEST 2018
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.CARGAS_WS_PAGINA
     *
     * @mbg.generated Thu Jun 28 12:30:00 CEST 2018
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.CARGAS_WS_PAGINA
     *
     * @mbg.generated Thu Jun 28 12:30:00 CEST 2018
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

        public Criteria andIdWsPaginaIsNull() {
            addCriterion("ID_WS_PAGINA is null");
            return (Criteria) this;
        }

        public Criteria andIdWsPaginaIsNotNull() {
            addCriterion("ID_WS_PAGINA is not null");
            return (Criteria) this;
        }

        public Criteria andIdWsPaginaEqualTo(Long value) {
            addCriterion("ID_WS_PAGINA =", value, "idWsPagina");
            return (Criteria) this;
        }

        public Criteria andIdWsPaginaNotEqualTo(Long value) {
            addCriterion("ID_WS_PAGINA <>", value, "idWsPagina");
            return (Criteria) this;
        }

        public Criteria andIdWsPaginaGreaterThan(Long value) {
            addCriterion("ID_WS_PAGINA >", value, "idWsPagina");
            return (Criteria) this;
        }

        public Criteria andIdWsPaginaGreaterThanOrEqualTo(Long value) {
            addCriterion("ID_WS_PAGINA >=", value, "idWsPagina");
            return (Criteria) this;
        }

        public Criteria andIdWsPaginaLessThan(Long value) {
            addCriterion("ID_WS_PAGINA <", value, "idWsPagina");
            return (Criteria) this;
        }

        public Criteria andIdWsPaginaLessThanOrEqualTo(Long value) {
            addCriterion("ID_WS_PAGINA <=", value, "idWsPagina");
            return (Criteria) this;
        }

        public Criteria andIdWsPaginaIn(List<Long> values) {
            addCriterion("ID_WS_PAGINA in", values, "idWsPagina");
            return (Criteria) this;
        }

        public Criteria andIdWsPaginaNotIn(List<Long> values) {
            addCriterion("ID_WS_PAGINA not in", values, "idWsPagina");
            return (Criteria) this;
        }

        public Criteria andIdWsPaginaBetween(Long value1, Long value2) {
            addCriterion("ID_WS_PAGINA between", value1, value2, "idWsPagina");
            return (Criteria) this;
        }

        public Criteria andIdWsPaginaNotBetween(Long value1, Long value2) {
            addCriterion("ID_WS_PAGINA not between", value1, value2, "idWsPagina");
            return (Criteria) this;
        }

        public Criteria andIdCargaIsNull() {
            addCriterion("ID_CARGA is null");
            return (Criteria) this;
        }

        public Criteria andIdCargaIsNotNull() {
            addCriterion("ID_CARGA is not null");
            return (Criteria) this;
        }

        public Criteria andIdCargaEqualTo(Long value) {
            addCriterion("ID_CARGA =", value, "idCarga");
            return (Criteria) this;
        }

        public Criteria andIdCargaNotEqualTo(Long value) {
            addCriterion("ID_CARGA <>", value, "idCarga");
            return (Criteria) this;
        }

        public Criteria andIdCargaGreaterThan(Long value) {
            addCriterion("ID_CARGA >", value, "idCarga");
            return (Criteria) this;
        }

        public Criteria andIdCargaGreaterThanOrEqualTo(Long value) {
            addCriterion("ID_CARGA >=", value, "idCarga");
            return (Criteria) this;
        }

        public Criteria andIdCargaLessThan(Long value) {
            addCriterion("ID_CARGA <", value, "idCarga");
            return (Criteria) this;
        }

        public Criteria andIdCargaLessThanOrEqualTo(Long value) {
            addCriterion("ID_CARGA <=", value, "idCarga");
            return (Criteria) this;
        }

        public Criteria andIdCargaIn(List<Long> values) {
            addCriterion("ID_CARGA in", values, "idCarga");
            return (Criteria) this;
        }

        public Criteria andIdCargaNotIn(List<Long> values) {
            addCriterion("ID_CARGA not in", values, "idCarga");
            return (Criteria) this;
        }

        public Criteria andIdCargaBetween(Long value1, Long value2) {
            addCriterion("ID_CARGA between", value1, value2, "idCarga");
            return (Criteria) this;
        }

        public Criteria andIdCargaNotBetween(Long value1, Long value2) {
            addCriterion("ID_CARGA not between", value1, value2, "idCarga");
            return (Criteria) this;
        }

        public Criteria andIdDatosXmlIsNull() {
            addCriterion("ID_DATOS_XML is null");
            return (Criteria) this;
        }

        public Criteria andIdDatosXmlIsNotNull() {
            addCriterion("ID_DATOS_XML is not null");
            return (Criteria) this;
        }

        public Criteria andIdDatosXmlEqualTo(Long value) {
            addCriterion("ID_DATOS_XML =", value, "idDatosXml");
            return (Criteria) this;
        }

        public Criteria andIdDatosXmlNotEqualTo(Long value) {
            addCriterion("ID_DATOS_XML <>", value, "idDatosXml");
            return (Criteria) this;
        }

        public Criteria andIdDatosXmlGreaterThan(Long value) {
            addCriterion("ID_DATOS_XML >", value, "idDatosXml");
            return (Criteria) this;
        }

        public Criteria andIdDatosXmlGreaterThanOrEqualTo(Long value) {
            addCriterion("ID_DATOS_XML >=", value, "idDatosXml");
            return (Criteria) this;
        }

        public Criteria andIdDatosXmlLessThan(Long value) {
            addCriterion("ID_DATOS_XML <", value, "idDatosXml");
            return (Criteria) this;
        }

        public Criteria andIdDatosXmlLessThanOrEqualTo(Long value) {
            addCriterion("ID_DATOS_XML <=", value, "idDatosXml");
            return (Criteria) this;
        }

        public Criteria andIdDatosXmlIn(List<Long> values) {
            addCriterion("ID_DATOS_XML in", values, "idDatosXml");
            return (Criteria) this;
        }

        public Criteria andIdDatosXmlNotIn(List<Long> values) {
            addCriterion("ID_DATOS_XML not in", values, "idDatosXml");
            return (Criteria) this;
        }

        public Criteria andIdDatosXmlBetween(Long value1, Long value2) {
            addCriterion("ID_DATOS_XML between", value1, value2, "idDatosXml");
            return (Criteria) this;
        }

        public Criteria andIdDatosXmlNotBetween(Long value1, Long value2) {
            addCriterion("ID_DATOS_XML not between", value1, value2, "idDatosXml");
            return (Criteria) this;
        }

        public Criteria andFechaPeticionIsNull() {
            addCriterion("FECHA_PETICION is null");
            return (Criteria) this;
        }

        public Criteria andFechaPeticionIsNotNull() {
            addCriterion("FECHA_PETICION is not null");
            return (Criteria) this;
        }

        public Criteria andFechaPeticionEqualTo(Date value) {
            addCriterion("FECHA_PETICION =", value, "fechaPeticion");
            return (Criteria) this;
        }

        public Criteria andFechaPeticionNotEqualTo(Date value) {
            addCriterion("FECHA_PETICION <>", value, "fechaPeticion");
            return (Criteria) this;
        }

        public Criteria andFechaPeticionGreaterThan(Date value) {
            addCriterion("FECHA_PETICION >", value, "fechaPeticion");
            return (Criteria) this;
        }

        public Criteria andFechaPeticionGreaterThanOrEqualTo(Date value) {
            addCriterion("FECHA_PETICION >=", value, "fechaPeticion");
            return (Criteria) this;
        }

        public Criteria andFechaPeticionLessThan(Date value) {
            addCriterion("FECHA_PETICION <", value, "fechaPeticion");
            return (Criteria) this;
        }

        public Criteria andFechaPeticionLessThanOrEqualTo(Date value) {
            addCriterion("FECHA_PETICION <=", value, "fechaPeticion");
            return (Criteria) this;
        }

        public Criteria andFechaPeticionIn(List<Date> values) {
            addCriterion("FECHA_PETICION in", values, "fechaPeticion");
            return (Criteria) this;
        }

        public Criteria andFechaPeticionNotIn(List<Date> values) {
            addCriterion("FECHA_PETICION not in", values, "fechaPeticion");
            return (Criteria) this;
        }

        public Criteria andFechaPeticionBetween(Date value1, Date value2) {
            addCriterion("FECHA_PETICION between", value1, value2, "fechaPeticion");
            return (Criteria) this;
        }

        public Criteria andFechaPeticionNotBetween(Date value1, Date value2) {
            addCriterion("FECHA_PETICION not between", value1, value2, "fechaPeticion");
            return (Criteria) this;
        }

        public Criteria andFechaCreacionIsNull() {
            addCriterion("FECHA_CREACION is null");
            return (Criteria) this;
        }

        public Criteria andFechaCreacionIsNotNull() {
            addCriterion("FECHA_CREACION is not null");
            return (Criteria) this;
        }

        public Criteria andFechaCreacionEqualTo(Date value) {
            addCriterion("FECHA_CREACION =", value, "fechaCreacion");
            return (Criteria) this;
        }

        public Criteria andFechaCreacionNotEqualTo(Date value) {
            addCriterion("FECHA_CREACION <>", value, "fechaCreacion");
            return (Criteria) this;
        }

        public Criteria andFechaCreacionGreaterThan(Date value) {
            addCriterion("FECHA_CREACION >", value, "fechaCreacion");
            return (Criteria) this;
        }

        public Criteria andFechaCreacionGreaterThanOrEqualTo(Date value) {
            addCriterion("FECHA_CREACION >=", value, "fechaCreacion");
            return (Criteria) this;
        }

        public Criteria andFechaCreacionLessThan(Date value) {
            addCriterion("FECHA_CREACION <", value, "fechaCreacion");
            return (Criteria) this;
        }

        public Criteria andFechaCreacionLessThanOrEqualTo(Date value) {
            addCriterion("FECHA_CREACION <=", value, "fechaCreacion");
            return (Criteria) this;
        }

        public Criteria andFechaCreacionIn(List<Date> values) {
            addCriterion("FECHA_CREACION in", values, "fechaCreacion");
            return (Criteria) this;
        }

        public Criteria andFechaCreacionNotIn(List<Date> values) {
            addCriterion("FECHA_CREACION not in", values, "fechaCreacion");
            return (Criteria) this;
        }

        public Criteria andFechaCreacionBetween(Date value1, Date value2) {
            addCriterion("FECHA_CREACION between", value1, value2, "fechaCreacion");
            return (Criteria) this;
        }

        public Criteria andFechaCreacionNotBetween(Date value1, Date value2) {
            addCriterion("FECHA_CREACION not between", value1, value2, "fechaCreacion");
            return (Criteria) this;
        }

        public Criteria andNumPaginaIsNull() {
            addCriterion("NUM_PAGINA is null");
            return (Criteria) this;
        }

        public Criteria andNumPaginaIsNotNull() {
            addCriterion("NUM_PAGINA is not null");
            return (Criteria) this;
        }

        public Criteria andNumPaginaEqualTo(Short value) {
            addCriterion("NUM_PAGINA =", value, "numPagina");
            return (Criteria) this;
        }

        public Criteria andNumPaginaNotEqualTo(Short value) {
            addCriterion("NUM_PAGINA <>", value, "numPagina");
            return (Criteria) this;
        }

        public Criteria andNumPaginaGreaterThan(Short value) {
            addCriterion("NUM_PAGINA >", value, "numPagina");
            return (Criteria) this;
        }

        public Criteria andNumPaginaGreaterThanOrEqualTo(Short value) {
            addCriterion("NUM_PAGINA >=", value, "numPagina");
            return (Criteria) this;
        }

        public Criteria andNumPaginaLessThan(Short value) {
            addCriterion("NUM_PAGINA <", value, "numPagina");
            return (Criteria) this;
        }

        public Criteria andNumPaginaLessThanOrEqualTo(Short value) {
            addCriterion("NUM_PAGINA <=", value, "numPagina");
            return (Criteria) this;
        }

        public Criteria andNumPaginaIn(List<Short> values) {
            addCriterion("NUM_PAGINA in", values, "numPagina");
            return (Criteria) this;
        }

        public Criteria andNumPaginaNotIn(List<Short> values) {
            addCriterion("NUM_PAGINA not in", values, "numPagina");
            return (Criteria) this;
        }

        public Criteria andNumPaginaBetween(Short value1, Short value2) {
            addCriterion("NUM_PAGINA between", value1, value2, "numPagina");
            return (Criteria) this;
        }

        public Criteria andNumPaginaNotBetween(Short value1, Short value2) {
            addCriterion("NUM_PAGINA not between", value1, value2, "numPagina");
            return (Criteria) this;
        }

        public Criteria andCodErrorIsNull() {
            addCriterion("COD_ERROR is null");
            return (Criteria) this;
        }

        public Criteria andCodErrorIsNotNull() {
            addCriterion("COD_ERROR is not null");
            return (Criteria) this;
        }

        public Criteria andCodErrorEqualTo(String value) {
            addCriterion("COD_ERROR =", value, "codError");
            return (Criteria) this;
        }

        public Criteria andCodErrorNotEqualTo(String value) {
            addCriterion("COD_ERROR <>", value, "codError");
            return (Criteria) this;
        }

        public Criteria andCodErrorGreaterThan(String value) {
            addCriterion("COD_ERROR >", value, "codError");
            return (Criteria) this;
        }

        public Criteria andCodErrorGreaterThanOrEqualTo(String value) {
            addCriterion("COD_ERROR >=", value, "codError");
            return (Criteria) this;
        }

        public Criteria andCodErrorLessThan(String value) {
            addCriterion("COD_ERROR <", value, "codError");
            return (Criteria) this;
        }

        public Criteria andCodErrorLessThanOrEqualTo(String value) {
            addCriterion("COD_ERROR <=", value, "codError");
            return (Criteria) this;
        }

        public Criteria andCodErrorLike(String value) {
            addCriterion("COD_ERROR like", value, "codError");
            return (Criteria) this;
        }

        public Criteria andCodErrorNotLike(String value) {
            addCriterion("COD_ERROR not like", value, "codError");
            return (Criteria) this;
        }

        public Criteria andCodErrorIn(List<String> values) {
            addCriterion("COD_ERROR in", values, "codError");
            return (Criteria) this;
        }

        public Criteria andCodErrorNotIn(List<String> values) {
            addCriterion("COD_ERROR not in", values, "codError");
            return (Criteria) this;
        }

        public Criteria andCodErrorBetween(String value1, String value2) {
            addCriterion("COD_ERROR between", value1, value2, "codError");
            return (Criteria) this;
        }

        public Criteria andCodErrorNotBetween(String value1, String value2) {
            addCriterion("COD_ERROR not between", value1, value2, "codError");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.CARGAS_WS_PAGINA
     *
     * @mbg.generated do_not_delete_during_merge Thu Jun 28 12:30:00 CEST 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.CARGAS_WS_PAGINA
     *
     * @mbg.generated Thu Jun 28 12:30:00 CEST 2018
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