package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScsDefendidosdesignaExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.SCS_DEFENDIDOSDESIGNA
     *
     * @mbg.generated Thu Nov 07 17:11:41 CET 2019
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.SCS_DEFENDIDOSDESIGNA
     *
     * @mbg.generated Thu Nov 07 17:11:41 CET 2019
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.SCS_DEFENDIDOSDESIGNA
     *
     * @mbg.generated Thu Nov 07 17:11:41 CET 2019
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DEFENDIDOSDESIGNA
     *
     * @mbg.generated Thu Nov 07 17:11:41 CET 2019
     */
    public ScsDefendidosdesignaExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DEFENDIDOSDESIGNA
     *
     * @mbg.generated Thu Nov 07 17:11:41 CET 2019
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DEFENDIDOSDESIGNA
     *
     * @mbg.generated Thu Nov 07 17:11:41 CET 2019
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DEFENDIDOSDESIGNA
     *
     * @mbg.generated Thu Nov 07 17:11:41 CET 2019
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DEFENDIDOSDESIGNA
     *
     * @mbg.generated Thu Nov 07 17:11:41 CET 2019
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DEFENDIDOSDESIGNA
     *
     * @mbg.generated Thu Nov 07 17:11:41 CET 2019
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DEFENDIDOSDESIGNA
     *
     * @mbg.generated Thu Nov 07 17:11:41 CET 2019
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DEFENDIDOSDESIGNA
     *
     * @mbg.generated Thu Nov 07 17:11:41 CET 2019
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DEFENDIDOSDESIGNA
     *
     * @mbg.generated Thu Nov 07 17:11:41 CET 2019
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
     * This method corresponds to the database table USCGAE.SCS_DEFENDIDOSDESIGNA
     *
     * @mbg.generated Thu Nov 07 17:11:41 CET 2019
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_DEFENDIDOSDESIGNA
     *
     * @mbg.generated Thu Nov 07 17:11:41 CET 2019
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.SCS_DEFENDIDOSDESIGNA
     *
     * @mbg.generated Thu Nov 07 17:11:41 CET 2019
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

        public Criteria andIdturnoIsNull() {
            addCriterion("IDTURNO is null");
            return (Criteria) this;
        }

        public Criteria andIdturnoIsNotNull() {
            addCriterion("IDTURNO is not null");
            return (Criteria) this;
        }

        public Criteria andIdturnoEqualTo(Integer value) {
            addCriterion("IDTURNO =", value, "idturno");
            return (Criteria) this;
        }

        public Criteria andIdturnoNotEqualTo(Integer value) {
            addCriterion("IDTURNO <>", value, "idturno");
            return (Criteria) this;
        }

        public Criteria andIdturnoGreaterThan(Integer value) {
            addCriterion("IDTURNO >", value, "idturno");
            return (Criteria) this;
        }

        public Criteria andIdturnoGreaterThanOrEqualTo(Integer value) {
            addCriterion("IDTURNO >=", value, "idturno");
            return (Criteria) this;
        }

        public Criteria andIdturnoLessThan(Integer value) {
            addCriterion("IDTURNO <", value, "idturno");
            return (Criteria) this;
        }

        public Criteria andIdturnoLessThanOrEqualTo(Integer value) {
            addCriterion("IDTURNO <=", value, "idturno");
            return (Criteria) this;
        }

        public Criteria andIdturnoIn(List<Integer> values) {
            addCriterion("IDTURNO in", values, "idturno");
            return (Criteria) this;
        }

        public Criteria andIdturnoNotIn(List<Integer> values) {
            addCriterion("IDTURNO not in", values, "idturno");
            return (Criteria) this;
        }

        public Criteria andIdturnoBetween(Integer value1, Integer value2) {
            addCriterion("IDTURNO between", value1, value2, "idturno");
            return (Criteria) this;
        }

        public Criteria andIdturnoNotBetween(Integer value1, Integer value2) {
            addCriterion("IDTURNO not between", value1, value2, "idturno");
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

        public Criteria andIdpersonaIsNull() {
            addCriterion("IDPERSONA is null");
            return (Criteria) this;
        }

        public Criteria andIdpersonaIsNotNull() {
            addCriterion("IDPERSONA is not null");
            return (Criteria) this;
        }

        public Criteria andIdpersonaEqualTo(Long value) {
            addCriterion("IDPERSONA =", value, "idpersona");
            return (Criteria) this;
        }

        public Criteria andIdpersonaNotEqualTo(Long value) {
            addCriterion("IDPERSONA <>", value, "idpersona");
            return (Criteria) this;
        }

        public Criteria andIdpersonaGreaterThan(Long value) {
            addCriterion("IDPERSONA >", value, "idpersona");
            return (Criteria) this;
        }

        public Criteria andIdpersonaGreaterThanOrEqualTo(Long value) {
            addCriterion("IDPERSONA >=", value, "idpersona");
            return (Criteria) this;
        }

        public Criteria andIdpersonaLessThan(Long value) {
            addCriterion("IDPERSONA <", value, "idpersona");
            return (Criteria) this;
        }

        public Criteria andIdpersonaLessThanOrEqualTo(Long value) {
            addCriterion("IDPERSONA <=", value, "idpersona");
            return (Criteria) this;
        }

        public Criteria andIdpersonaIn(List<Long> values) {
            addCriterion("IDPERSONA in", values, "idpersona");
            return (Criteria) this;
        }

        public Criteria andIdpersonaNotIn(List<Long> values) {
            addCriterion("IDPERSONA not in", values, "idpersona");
            return (Criteria) this;
        }

        public Criteria andIdpersonaBetween(Long value1, Long value2) {
            addCriterion("IDPERSONA between", value1, value2, "idpersona");
            return (Criteria) this;
        }

        public Criteria andIdpersonaNotBetween(Long value1, Long value2) {
            addCriterion("IDPERSONA not between", value1, value2, "idpersona");
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

        public Criteria andNombrerepresentanteIsNull() {
            addCriterion("NOMBREREPRESENTANTE is null");
            return (Criteria) this;
        }

        public Criteria andNombrerepresentanteIsNotNull() {
            addCriterion("NOMBREREPRESENTANTE is not null");
            return (Criteria) this;
        }

        public Criteria andNombrerepresentanteEqualTo(String value) {
            addCriterion("NOMBREREPRESENTANTE =", value, "nombrerepresentante");
            return (Criteria) this;
        }

        public Criteria andNombrerepresentanteNotEqualTo(String value) {
            addCriterion("NOMBREREPRESENTANTE <>", value, "nombrerepresentante");
            return (Criteria) this;
        }

        public Criteria andNombrerepresentanteGreaterThan(String value) {
            addCriterion("NOMBREREPRESENTANTE >", value, "nombrerepresentante");
            return (Criteria) this;
        }

        public Criteria andNombrerepresentanteGreaterThanOrEqualTo(String value) {
            addCriterion("NOMBREREPRESENTANTE >=", value, "nombrerepresentante");
            return (Criteria) this;
        }

        public Criteria andNombrerepresentanteLessThan(String value) {
            addCriterion("NOMBREREPRESENTANTE <", value, "nombrerepresentante");
            return (Criteria) this;
        }

        public Criteria andNombrerepresentanteLessThanOrEqualTo(String value) {
            addCriterion("NOMBREREPRESENTANTE <=", value, "nombrerepresentante");
            return (Criteria) this;
        }

        public Criteria andNombrerepresentanteLike(String value) {
            addCriterion("NOMBREREPRESENTANTE like", value, "nombrerepresentante");
            return (Criteria) this;
        }

        public Criteria andNombrerepresentanteNotLike(String value) {
            addCriterion("NOMBREREPRESENTANTE not like", value, "nombrerepresentante");
            return (Criteria) this;
        }

        public Criteria andNombrerepresentanteIn(List<String> values) {
            addCriterion("NOMBREREPRESENTANTE in", values, "nombrerepresentante");
            return (Criteria) this;
        }

        public Criteria andNombrerepresentanteNotIn(List<String> values) {
            addCriterion("NOMBREREPRESENTANTE not in", values, "nombrerepresentante");
            return (Criteria) this;
        }

        public Criteria andNombrerepresentanteBetween(String value1, String value2) {
            addCriterion("NOMBREREPRESENTANTE between", value1, value2, "nombrerepresentante");
            return (Criteria) this;
        }

        public Criteria andNombrerepresentanteNotBetween(String value1, String value2) {
            addCriterion("NOMBREREPRESENTANTE not between", value1, value2, "nombrerepresentante");
            return (Criteria) this;
        }

        public Criteria andCalidadIsNull() {
            addCriterion("CALIDAD is null");
            return (Criteria) this;
        }

        public Criteria andCalidadIsNotNull() {
            addCriterion("CALIDAD is not null");
            return (Criteria) this;
        }

        public Criteria andCalidadEqualTo(String value) {
            addCriterion("CALIDAD =", value, "calidad");
            return (Criteria) this;
        }

        public Criteria andCalidadNotEqualTo(String value) {
            addCriterion("CALIDAD <>", value, "calidad");
            return (Criteria) this;
        }

        public Criteria andCalidadGreaterThan(String value) {
            addCriterion("CALIDAD >", value, "calidad");
            return (Criteria) this;
        }

        public Criteria andCalidadGreaterThanOrEqualTo(String value) {
            addCriterion("CALIDAD >=", value, "calidad");
            return (Criteria) this;
        }

        public Criteria andCalidadLessThan(String value) {
            addCriterion("CALIDAD <", value, "calidad");
            return (Criteria) this;
        }

        public Criteria andCalidadLessThanOrEqualTo(String value) {
            addCriterion("CALIDAD <=", value, "calidad");
            return (Criteria) this;
        }

        public Criteria andCalidadLike(String value) {
            addCriterion("CALIDAD like", value, "calidad");
            return (Criteria) this;
        }

        public Criteria andCalidadNotLike(String value) {
            addCriterion("CALIDAD not like", value, "calidad");
            return (Criteria) this;
        }

        public Criteria andCalidadIn(List<String> values) {
            addCriterion("CALIDAD in", values, "calidad");
            return (Criteria) this;
        }

        public Criteria andCalidadNotIn(List<String> values) {
            addCriterion("CALIDAD not in", values, "calidad");
            return (Criteria) this;
        }

        public Criteria andCalidadBetween(String value1, String value2) {
            addCriterion("CALIDAD between", value1, value2, "calidad");
            return (Criteria) this;
        }

        public Criteria andCalidadNotBetween(String value1, String value2) {
            addCriterion("CALIDAD not between", value1, value2, "calidad");
            return (Criteria) this;
        }

        public Criteria andIdtipoencalidadIsNull() {
            addCriterion("IDTIPOENCALIDAD is null");
            return (Criteria) this;
        }

        public Criteria andIdtipoencalidadIsNotNull() {
            addCriterion("IDTIPOENCALIDAD is not null");
            return (Criteria) this;
        }

        public Criteria andIdtipoencalidadEqualTo(Short value) {
            addCriterion("IDTIPOENCALIDAD =", value, "idtipoencalidad");
            return (Criteria) this;
        }

        public Criteria andIdtipoencalidadNotEqualTo(Short value) {
            addCriterion("IDTIPOENCALIDAD <>", value, "idtipoencalidad");
            return (Criteria) this;
        }

        public Criteria andIdtipoencalidadGreaterThan(Short value) {
            addCriterion("IDTIPOENCALIDAD >", value, "idtipoencalidad");
            return (Criteria) this;
        }

        public Criteria andIdtipoencalidadGreaterThanOrEqualTo(Short value) {
            addCriterion("IDTIPOENCALIDAD >=", value, "idtipoencalidad");
            return (Criteria) this;
        }

        public Criteria andIdtipoencalidadLessThan(Short value) {
            addCriterion("IDTIPOENCALIDAD <", value, "idtipoencalidad");
            return (Criteria) this;
        }

        public Criteria andIdtipoencalidadLessThanOrEqualTo(Short value) {
            addCriterion("IDTIPOENCALIDAD <=", value, "idtipoencalidad");
            return (Criteria) this;
        }

        public Criteria andIdtipoencalidadIn(List<Short> values) {
            addCriterion("IDTIPOENCALIDAD in", values, "idtipoencalidad");
            return (Criteria) this;
        }

        public Criteria andIdtipoencalidadNotIn(List<Short> values) {
            addCriterion("IDTIPOENCALIDAD not in", values, "idtipoencalidad");
            return (Criteria) this;
        }

        public Criteria andIdtipoencalidadBetween(Short value1, Short value2) {
            addCriterion("IDTIPOENCALIDAD between", value1, value2, "idtipoencalidad");
            return (Criteria) this;
        }

        public Criteria andIdtipoencalidadNotBetween(Short value1, Short value2) {
            addCriterion("IDTIPOENCALIDAD not between", value1, value2, "idtipoencalidad");
            return (Criteria) this;
        }

        public Criteria andCalidadidinstitucionIsNull() {
            addCriterion("CALIDADIDINSTITUCION is null");
            return (Criteria) this;
        }

        public Criteria andCalidadidinstitucionIsNotNull() {
            addCriterion("CALIDADIDINSTITUCION is not null");
            return (Criteria) this;
        }

        public Criteria andCalidadidinstitucionEqualTo(Short value) {
            addCriterion("CALIDADIDINSTITUCION =", value, "calidadidinstitucion");
            return (Criteria) this;
        }

        public Criteria andCalidadidinstitucionNotEqualTo(Short value) {
            addCriterion("CALIDADIDINSTITUCION <>", value, "calidadidinstitucion");
            return (Criteria) this;
        }

        public Criteria andCalidadidinstitucionGreaterThan(Short value) {
            addCriterion("CALIDADIDINSTITUCION >", value, "calidadidinstitucion");
            return (Criteria) this;
        }

        public Criteria andCalidadidinstitucionGreaterThanOrEqualTo(Short value) {
            addCriterion("CALIDADIDINSTITUCION >=", value, "calidadidinstitucion");
            return (Criteria) this;
        }

        public Criteria andCalidadidinstitucionLessThan(Short value) {
            addCriterion("CALIDADIDINSTITUCION <", value, "calidadidinstitucion");
            return (Criteria) this;
        }

        public Criteria andCalidadidinstitucionLessThanOrEqualTo(Short value) {
            addCriterion("CALIDADIDINSTITUCION <=", value, "calidadidinstitucion");
            return (Criteria) this;
        }

        public Criteria andCalidadidinstitucionIn(List<Short> values) {
            addCriterion("CALIDADIDINSTITUCION in", values, "calidadidinstitucion");
            return (Criteria) this;
        }

        public Criteria andCalidadidinstitucionNotIn(List<Short> values) {
            addCriterion("CALIDADIDINSTITUCION not in", values, "calidadidinstitucion");
            return (Criteria) this;
        }

        public Criteria andCalidadidinstitucionBetween(Short value1, Short value2) {
            addCriterion("CALIDADIDINSTITUCION between", value1, value2, "calidadidinstitucion");
            return (Criteria) this;
        }

        public Criteria andCalidadidinstitucionNotBetween(Short value1, Short value2) {
            addCriterion("CALIDADIDINSTITUCION not between", value1, value2, "calidadidinstitucion");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.SCS_DEFENDIDOSDESIGNA
     *
     * @mbg.generated do_not_delete_during_merge Thu Nov 07 17:11:41 CET 2019
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.SCS_DEFENDIDOSDESIGNA
     *
     * @mbg.generated Thu Nov 07 17:11:41 CET 2019
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