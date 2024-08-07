package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScsDatosprocuradoresExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.SCS_DATOSPROCURADORES
     *
     * @mbg.generated Tue Nov 02 09:26:12 CET 2021
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.SCS_DATOSPROCURADORES
     *
     * @mbg.generated Tue Nov 02 09:26:12 CET 2021
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.SCS_DATOSPROCURADORES
     *
     * @mbg.generated Tue Nov 02 09:26:12 CET 2021
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DATOSPROCURADORES
     *
     * @mbg.generated Tue Nov 02 09:26:12 CET 2021
     */
    public ScsDatosprocuradoresExample() {
        oredCriteria = new ArrayList<>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DATOSPROCURADORES
     *
     * @mbg.generated Tue Nov 02 09:26:12 CET 2021
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DATOSPROCURADORES
     *
     * @mbg.generated Tue Nov 02 09:26:12 CET 2021
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DATOSPROCURADORES
     *
     * @mbg.generated Tue Nov 02 09:26:12 CET 2021
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DATOSPROCURADORES
     *
     * @mbg.generated Tue Nov 02 09:26:12 CET 2021
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DATOSPROCURADORES
     *
     * @mbg.generated Tue Nov 02 09:26:12 CET 2021
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DATOSPROCURADORES
     *
     * @mbg.generated Tue Nov 02 09:26:12 CET 2021
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DATOSPROCURADORES
     *
     * @mbg.generated Tue Nov 02 09:26:12 CET 2021
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DATOSPROCURADORES
     *
     * @mbg.generated Tue Nov 02 09:26:12 CET 2021
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
     * This method corresponds to the database table USCGAE.SCS_DATOSPROCURADORES
     *
     * @mbg.generated Tue Nov 02 09:26:12 CET 2021
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DATOSPROCURADORES
     *
     * @mbg.generated Tue Nov 02 09:26:12 CET 2021
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.SCS_DATOSPROCURADORES
     *
     * @mbg.generated Tue Nov 02 09:26:12 CET 2021
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

        public Criteria andIddatosprocuradoresIsNull() {
            addCriterion("IDDATOSPROCURADORES is null");
            return (Criteria) this;
        }

        public Criteria andIddatosprocuradoresIsNotNull() {
            addCriterion("IDDATOSPROCURADORES is not null");
            return (Criteria) this;
        }

        public Criteria andIddatosprocuradoresEqualTo(Integer value) {
            addCriterion("IDDATOSPROCURADORES =", value, "iddatosprocuradores");
            return (Criteria) this;
        }

        public Criteria andIddatosprocuradoresNotEqualTo(Integer value) {
            addCriterion("IDDATOSPROCURADORES <>", value, "iddatosprocuradores");
            return (Criteria) this;
        }

        public Criteria andIddatosprocuradoresGreaterThan(Integer value) {
            addCriterion("IDDATOSPROCURADORES >", value, "iddatosprocuradores");
            return (Criteria) this;
        }

        public Criteria andIddatosprocuradoresGreaterThanOrEqualTo(Integer value) {
            addCriterion("IDDATOSPROCURADORES >=", value, "iddatosprocuradores");
            return (Criteria) this;
        }

        public Criteria andIddatosprocuradoresLessThan(Integer value) {
            addCriterion("IDDATOSPROCURADORES <", value, "iddatosprocuradores");
            return (Criteria) this;
        }

        public Criteria andIddatosprocuradoresLessThanOrEqualTo(Integer value) {
            addCriterion("IDDATOSPROCURADORES <=", value, "iddatosprocuradores");
            return (Criteria) this;
        }

        public Criteria andIddatosprocuradoresIn(List<Integer> values) {
            addCriterion("IDDATOSPROCURADORES in", values, "iddatosprocuradores");
            return (Criteria) this;
        }

        public Criteria andIddatosprocuradoresNotIn(List<Integer> values) {
            addCriterion("IDDATOSPROCURADORES not in", values, "iddatosprocuradores");
            return (Criteria) this;
        }

        public Criteria andIddatosprocuradoresBetween(Integer value1, Integer value2) {
            addCriterion("IDDATOSPROCURADORES between", value1, value2, "iddatosprocuradores");
            return (Criteria) this;
        }

        public Criteria andIddatosprocuradoresNotBetween(Integer value1, Integer value2) {
            addCriterion("IDDATOSPROCURADORES not between", value1, value2, "iddatosprocuradores");
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

        public Criteria andCodigodesignaabogadoIsNull() {
            addCriterion("CODIGODESIGNAABOGADO is null");
            return (Criteria) this;
        }

        public Criteria andCodigodesignaabogadoIsNotNull() {
            addCriterion("CODIGODESIGNAABOGADO is not null");
            return (Criteria) this;
        }

        public Criteria andCodigodesignaabogadoEqualTo(String value) {
            addCriterion("CODIGODESIGNAABOGADO =", value, "codigodesignaabogado");
            return (Criteria) this;
        }

        public Criteria andCodigodesignaabogadoNotEqualTo(String value) {
            addCriterion("CODIGODESIGNAABOGADO <>", value, "codigodesignaabogado");
            return (Criteria) this;
        }

        public Criteria andCodigodesignaabogadoGreaterThan(String value) {
            addCriterion("CODIGODESIGNAABOGADO >", value, "codigodesignaabogado");
            return (Criteria) this;
        }

        public Criteria andCodigodesignaabogadoGreaterThanOrEqualTo(String value) {
            addCriterion("CODIGODESIGNAABOGADO >=", value, "codigodesignaabogado");
            return (Criteria) this;
        }

        public Criteria andCodigodesignaabogadoLessThan(String value) {
            addCriterion("CODIGODESIGNAABOGADO <", value, "codigodesignaabogado");
            return (Criteria) this;
        }

        public Criteria andCodigodesignaabogadoLessThanOrEqualTo(String value) {
            addCriterion("CODIGODESIGNAABOGADO <=", value, "codigodesignaabogado");
            return (Criteria) this;
        }

        public Criteria andCodigodesignaabogadoLike(String value) {
            addCriterion("CODIGODESIGNAABOGADO like", value, "codigodesignaabogado");
            return (Criteria) this;
        }

        public Criteria andCodigodesignaabogadoNotLike(String value) {
            addCriterion("CODIGODESIGNAABOGADO not like", value, "codigodesignaabogado");
            return (Criteria) this;
        }

        public Criteria andCodigodesignaabogadoIn(List<String> values) {
            addCriterion("CODIGODESIGNAABOGADO in", values, "codigodesignaabogado");
            return (Criteria) this;
        }

        public Criteria andCodigodesignaabogadoNotIn(List<String> values) {
            addCriterion("CODIGODESIGNAABOGADO not in", values, "codigodesignaabogado");
            return (Criteria) this;
        }

        public Criteria andCodigodesignaabogadoBetween(String value1, String value2) {
            addCriterion("CODIGODESIGNAABOGADO between", value1, value2, "codigodesignaabogado");
            return (Criteria) this;
        }

        public Criteria andCodigodesignaabogadoNotBetween(String value1, String value2) {
            addCriterion("CODIGODESIGNAABOGADO not between", value1, value2, "codigodesignaabogado");
            return (Criteria) this;
        }

        public Criteria andNumejgIsNull() {
            addCriterion("NUMEJG is null");
            return (Criteria) this;
        }

        public Criteria andNumejgIsNotNull() {
            addCriterion("NUMEJG is not null");
            return (Criteria) this;
        }

        public Criteria andNumejgEqualTo(String value) {
            addCriterion("NUMEJG =", value, "numejg");
            return (Criteria) this;
        }

        public Criteria andNumejgNotEqualTo(String value) {
            addCriterion("NUMEJG <>", value, "numejg");
            return (Criteria) this;
        }

        public Criteria andNumejgGreaterThan(String value) {
            addCriterion("NUMEJG >", value, "numejg");
            return (Criteria) this;
        }

        public Criteria andNumejgGreaterThanOrEqualTo(String value) {
            addCriterion("NUMEJG >=", value, "numejg");
            return (Criteria) this;
        }

        public Criteria andNumejgLessThan(String value) {
            addCriterion("NUMEJG <", value, "numejg");
            return (Criteria) this;
        }

        public Criteria andNumejgLessThanOrEqualTo(String value) {
            addCriterion("NUMEJG <=", value, "numejg");
            return (Criteria) this;
        }

        public Criteria andNumejgLike(String value) {
            addCriterion("NUMEJG like", value, "numejg");
            return (Criteria) this;
        }

        public Criteria andNumejgNotLike(String value) {
            addCriterion("NUMEJG not like", value, "numejg");
            return (Criteria) this;
        }

        public Criteria andNumejgIn(List<String> values) {
            addCriterion("NUMEJG in", values, "numejg");
            return (Criteria) this;
        }

        public Criteria andNumejgNotIn(List<String> values) {
            addCriterion("NUMEJG not in", values, "numejg");
            return (Criteria) this;
        }

        public Criteria andNumejgBetween(String value1, String value2) {
            addCriterion("NUMEJG between", value1, value2, "numejg");
            return (Criteria) this;
        }

        public Criteria andNumejgNotBetween(String value1, String value2) {
            addCriterion("NUMEJG not between", value1, value2, "numejg");
            return (Criteria) this;
        }

        public Criteria andNumcolprocuradorIsNull() {
            addCriterion("NUMCOLPROCURADOR is null");
            return (Criteria) this;
        }

        public Criteria andNumcolprocuradorIsNotNull() {
            addCriterion("NUMCOLPROCURADOR is not null");
            return (Criteria) this;
        }

        public Criteria andNumcolprocuradorEqualTo(String value) {
            addCriterion("NUMCOLPROCURADOR =", value, "numcolprocurador");
            return (Criteria) this;
        }

        public Criteria andNumcolprocuradorNotEqualTo(String value) {
            addCriterion("NUMCOLPROCURADOR <>", value, "numcolprocurador");
            return (Criteria) this;
        }

        public Criteria andNumcolprocuradorGreaterThan(String value) {
            addCriterion("NUMCOLPROCURADOR >", value, "numcolprocurador");
            return (Criteria) this;
        }

        public Criteria andNumcolprocuradorGreaterThanOrEqualTo(String value) {
            addCriterion("NUMCOLPROCURADOR >=", value, "numcolprocurador");
            return (Criteria) this;
        }

        public Criteria andNumcolprocuradorLessThan(String value) {
            addCriterion("NUMCOLPROCURADOR <", value, "numcolprocurador");
            return (Criteria) this;
        }

        public Criteria andNumcolprocuradorLessThanOrEqualTo(String value) {
            addCriterion("NUMCOLPROCURADOR <=", value, "numcolprocurador");
            return (Criteria) this;
        }

        public Criteria andNumcolprocuradorLike(String value) {
            addCriterion("NUMCOLPROCURADOR like", value, "numcolprocurador");
            return (Criteria) this;
        }

        public Criteria andNumcolprocuradorNotLike(String value) {
            addCriterion("NUMCOLPROCURADOR not like", value, "numcolprocurador");
            return (Criteria) this;
        }

        public Criteria andNumcolprocuradorIn(List<String> values) {
            addCriterion("NUMCOLPROCURADOR in", values, "numcolprocurador");
            return (Criteria) this;
        }

        public Criteria andNumcolprocuradorNotIn(List<String> values) {
            addCriterion("NUMCOLPROCURADOR not in", values, "numcolprocurador");
            return (Criteria) this;
        }

        public Criteria andNumcolprocuradorBetween(String value1, String value2) {
            addCriterion("NUMCOLPROCURADOR between", value1, value2, "numcolprocurador");
            return (Criteria) this;
        }

        public Criteria andNumcolprocuradorNotBetween(String value1, String value2) {
            addCriterion("NUMCOLPROCURADOR not between", value1, value2, "numcolprocurador");
            return (Criteria) this;
        }

        public Criteria andFechadesigprocuradorIsNull() {
            addCriterion("FECHADESIGPROCURADOR is null");
            return (Criteria) this;
        }

        public Criteria andFechadesigprocuradorIsNotNull() {
            addCriterion("FECHADESIGPROCURADOR is not null");
            return (Criteria) this;
        }

        public Criteria andFechadesigprocuradorEqualTo(Date value) {
            addCriterion("FECHADESIGPROCURADOR =", value, "fechadesigprocurador");
            return (Criteria) this;
        }

        public Criteria andFechadesigprocuradorNotEqualTo(Date value) {
            addCriterion("FECHADESIGPROCURADOR <>", value, "fechadesigprocurador");
            return (Criteria) this;
        }

        public Criteria andFechadesigprocuradorGreaterThan(Date value) {
            addCriterion("FECHADESIGPROCURADOR >", value, "fechadesigprocurador");
            return (Criteria) this;
        }

        public Criteria andFechadesigprocuradorGreaterThanOrEqualTo(Date value) {
            addCriterion("FECHADESIGPROCURADOR >=", value, "fechadesigprocurador");
            return (Criteria) this;
        }

        public Criteria andFechadesigprocuradorLessThan(Date value) {
            addCriterion("FECHADESIGPROCURADOR <", value, "fechadesigprocurador");
            return (Criteria) this;
        }

        public Criteria andFechadesigprocuradorLessThanOrEqualTo(Date value) {
            addCriterion("FECHADESIGPROCURADOR <=", value, "fechadesigprocurador");
            return (Criteria) this;
        }

        public Criteria andFechadesigprocuradorIn(List<Date> values) {
            addCriterion("FECHADESIGPROCURADOR in", values, "fechadesigprocurador");
            return (Criteria) this;
        }

        public Criteria andFechadesigprocuradorNotIn(List<Date> values) {
            addCriterion("FECHADESIGPROCURADOR not in", values, "fechadesigprocurador");
            return (Criteria) this;
        }

        public Criteria andFechadesigprocuradorBetween(Date value1, Date value2) {
            addCriterion("FECHADESIGPROCURADOR between", value1, value2, "fechadesigprocurador");
            return (Criteria) this;
        }

        public Criteria andFechadesigprocuradorNotBetween(Date value1, Date value2) {
            addCriterion("FECHADESIGPROCURADOR not between", value1, value2, "fechadesigprocurador");
            return (Criteria) this;
        }

        public Criteria andNumdesignaprocuradorIsNull() {
            addCriterion("NUMDESIGNAPROCURADOR is null");
            return (Criteria) this;
        }

        public Criteria andNumdesignaprocuradorIsNotNull() {
            addCriterion("NUMDESIGNAPROCURADOR is not null");
            return (Criteria) this;
        }

        public Criteria andNumdesignaprocuradorEqualTo(String value) {
            addCriterion("NUMDESIGNAPROCURADOR =", value, "numdesignaprocurador");
            return (Criteria) this;
        }

        public Criteria andNumdesignaprocuradorNotEqualTo(String value) {
            addCriterion("NUMDESIGNAPROCURADOR <>", value, "numdesignaprocurador");
            return (Criteria) this;
        }

        public Criteria andNumdesignaprocuradorGreaterThan(String value) {
            addCriterion("NUMDESIGNAPROCURADOR >", value, "numdesignaprocurador");
            return (Criteria) this;
        }

        public Criteria andNumdesignaprocuradorGreaterThanOrEqualTo(String value) {
            addCriterion("NUMDESIGNAPROCURADOR >=", value, "numdesignaprocurador");
            return (Criteria) this;
        }

        public Criteria andNumdesignaprocuradorLessThan(String value) {
            addCriterion("NUMDESIGNAPROCURADOR <", value, "numdesignaprocurador");
            return (Criteria) this;
        }

        public Criteria andNumdesignaprocuradorLessThanOrEqualTo(String value) {
            addCriterion("NUMDESIGNAPROCURADOR <=", value, "numdesignaprocurador");
            return (Criteria) this;
        }

        public Criteria andNumdesignaprocuradorLike(String value) {
            addCriterion("NUMDESIGNAPROCURADOR like", value, "numdesignaprocurador");
            return (Criteria) this;
        }

        public Criteria andNumdesignaprocuradorNotLike(String value) {
            addCriterion("NUMDESIGNAPROCURADOR not like", value, "numdesignaprocurador");
            return (Criteria) this;
        }

        public Criteria andNumdesignaprocuradorIn(List<String> values) {
            addCriterion("NUMDESIGNAPROCURADOR in", values, "numdesignaprocurador");
            return (Criteria) this;
        }

        public Criteria andNumdesignaprocuradorNotIn(List<String> values) {
            addCriterion("NUMDESIGNAPROCURADOR not in", values, "numdesignaprocurador");
            return (Criteria) this;
        }

        public Criteria andNumdesignaprocuradorBetween(String value1, String value2) {
            addCriterion("NUMDESIGNAPROCURADOR between", value1, value2, "numdesignaprocurador");
            return (Criteria) this;
        }

        public Criteria andNumdesignaprocuradorNotBetween(String value1, String value2) {
            addCriterion("NUMDESIGNAPROCURADOR not between", value1, value2, "numdesignaprocurador");
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
     * This class corresponds to the database table USCGAE.SCS_DATOSPROCURADORES
     *
     * @mbg.generated do_not_delete_during_merge Tue Nov 02 09:26:12 CET 2021
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.SCS_DATOSPROCURADORES
     *
     * @mbg.generated Tue Nov 02 09:26:12 CET 2021
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