package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScsComunicacionesExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE_INT.SCS_COMUNICACIONES
     *
     * @mbg.generated Wed Jun 14 11:34:46 CEST 2023
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE_INT.SCS_COMUNICACIONES
     *
     * @mbg.generated Wed Jun 14 11:34:46 CEST 2023
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE_INT.SCS_COMUNICACIONES
     *
     * @mbg.generated Wed Jun 14 11:34:46 CEST 2023
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.SCS_COMUNICACIONES
     *
     * @mbg.generated Wed Jun 14 11:34:46 CEST 2023
     */
    public ScsComunicacionesExample() {
        oredCriteria = new ArrayList<>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.SCS_COMUNICACIONES
     *
     * @mbg.generated Wed Jun 14 11:34:46 CEST 2023
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.SCS_COMUNICACIONES
     *
     * @mbg.generated Wed Jun 14 11:34:46 CEST 2023
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.SCS_COMUNICACIONES
     *
     * @mbg.generated Wed Jun 14 11:34:46 CEST 2023
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.SCS_COMUNICACIONES
     *
     * @mbg.generated Wed Jun 14 11:34:46 CEST 2023
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.SCS_COMUNICACIONES
     *
     * @mbg.generated Wed Jun 14 11:34:46 CEST 2023
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.SCS_COMUNICACIONES
     *
     * @mbg.generated Wed Jun 14 11:34:46 CEST 2023
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.SCS_COMUNICACIONES
     *
     * @mbg.generated Wed Jun 14 11:34:46 CEST 2023
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.SCS_COMUNICACIONES
     *
     * @mbg.generated Wed Jun 14 11:34:46 CEST 2023
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
     * This method corresponds to the database table USCGAE_INT.SCS_COMUNICACIONES
     *
     * @mbg.generated Wed Jun 14 11:34:46 CEST 2023
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.SCS_COMUNICACIONES
     *
     * @mbg.generated Wed Jun 14 11:34:46 CEST 2023
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE_INT.SCS_COMUNICACIONES
     *
     * @mbg.generated Wed Jun 14 11:34:46 CEST 2023
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

        public Criteria andIdcomunicacionIsNull() {
            addCriterion("IDCOMUNICACION is null");
            return (Criteria) this;
        }

        public Criteria andIdcomunicacionIsNotNull() {
            addCriterion("IDCOMUNICACION is not null");
            return (Criteria) this;
        }

        public Criteria andIdcomunicacionEqualTo(Long value) {
            addCriterion("IDCOMUNICACION =", value, "idcomunicacion");
            return (Criteria) this;
        }

        public Criteria andIdcomunicacionNotEqualTo(Long value) {
            addCriterion("IDCOMUNICACION <>", value, "idcomunicacion");
            return (Criteria) this;
        }

        public Criteria andIdcomunicacionGreaterThan(Long value) {
            addCriterion("IDCOMUNICACION >", value, "idcomunicacion");
            return (Criteria) this;
        }

        public Criteria andIdcomunicacionGreaterThanOrEqualTo(Long value) {
            addCriterion("IDCOMUNICACION >=", value, "idcomunicacion");
            return (Criteria) this;
        }

        public Criteria andIdcomunicacionLessThan(Long value) {
            addCriterion("IDCOMUNICACION <", value, "idcomunicacion");
            return (Criteria) this;
        }

        public Criteria andIdcomunicacionLessThanOrEqualTo(Long value) {
            addCriterion("IDCOMUNICACION <=", value, "idcomunicacion");
            return (Criteria) this;
        }

        public Criteria andIdcomunicacionIn(List<Long> values) {
            addCriterion("IDCOMUNICACION in", values, "idcomunicacion");
            return (Criteria) this;
        }

        public Criteria andIdcomunicacionNotIn(List<Long> values) {
            addCriterion("IDCOMUNICACION not in", values, "idcomunicacion");
            return (Criteria) this;
        }

        public Criteria andIdcomunicacionBetween(Long value1, Long value2) {
            addCriterion("IDCOMUNICACION between", value1, value2, "idcomunicacion");
            return (Criteria) this;
        }

        public Criteria andIdcomunicacionNotBetween(Long value1, Long value2) {
            addCriterion("IDCOMUNICACION not between", value1, value2, "idcomunicacion");
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

        public Criteria andIdenviosalidaIsNull() {
            addCriterion("IDENVIOSALIDA is null");
            return (Criteria) this;
        }

        public Criteria andIdenviosalidaIsNotNull() {
            addCriterion("IDENVIOSALIDA is not null");
            return (Criteria) this;
        }

        public Criteria andIdenviosalidaEqualTo(Long value) {
            addCriterion("IDENVIOSALIDA =", value, "idenviosalida");
            return (Criteria) this;
        }

        public Criteria andIdenviosalidaNotEqualTo(Long value) {
            addCriterion("IDENVIOSALIDA <>", value, "idenviosalida");
            return (Criteria) this;
        }

        public Criteria andIdenviosalidaGreaterThan(Long value) {
            addCriterion("IDENVIOSALIDA >", value, "idenviosalida");
            return (Criteria) this;
        }

        public Criteria andIdenviosalidaGreaterThanOrEqualTo(Long value) {
            addCriterion("IDENVIOSALIDA >=", value, "idenviosalida");
            return (Criteria) this;
        }

        public Criteria andIdenviosalidaLessThan(Long value) {
            addCriterion("IDENVIOSALIDA <", value, "idenviosalida");
            return (Criteria) this;
        }

        public Criteria andIdenviosalidaLessThanOrEqualTo(Long value) {
            addCriterion("IDENVIOSALIDA <=", value, "idenviosalida");
            return (Criteria) this;
        }

        public Criteria andIdenviosalidaIn(List<Long> values) {
            addCriterion("IDENVIOSALIDA in", values, "idenviosalida");
            return (Criteria) this;
        }

        public Criteria andIdenviosalidaNotIn(List<Long> values) {
            addCriterion("IDENVIOSALIDA not in", values, "idenviosalida");
            return (Criteria) this;
        }

        public Criteria andIdenviosalidaBetween(Long value1, Long value2) {
            addCriterion("IDENVIOSALIDA between", value1, value2, "idenviosalida");
            return (Criteria) this;
        }

        public Criteria andIdenviosalidaNotBetween(Long value1, Long value2) {
            addCriterion("IDENVIOSALIDA not between", value1, value2, "idenviosalida");
            return (Criteria) this;
        }

        public Criteria andIdenvioentradaIsNull() {
            addCriterion("IDENVIOENTRADA is null");
            return (Criteria) this;
        }

        public Criteria andIdenvioentradaIsNotNull() {
            addCriterion("IDENVIOENTRADA is not null");
            return (Criteria) this;
        }

        public Criteria andIdenvioentradaEqualTo(Long value) {
            addCriterion("IDENVIOENTRADA =", value, "idenvioentrada");
            return (Criteria) this;
        }

        public Criteria andIdenvioentradaNotEqualTo(Long value) {
            addCriterion("IDENVIOENTRADA <>", value, "idenvioentrada");
            return (Criteria) this;
        }

        public Criteria andIdenvioentradaGreaterThan(Long value) {
            addCriterion("IDENVIOENTRADA >", value, "idenvioentrada");
            return (Criteria) this;
        }

        public Criteria andIdenvioentradaGreaterThanOrEqualTo(Long value) {
            addCriterion("IDENVIOENTRADA >=", value, "idenvioentrada");
            return (Criteria) this;
        }

        public Criteria andIdenvioentradaLessThan(Long value) {
            addCriterion("IDENVIOENTRADA <", value, "idenvioentrada");
            return (Criteria) this;
        }

        public Criteria andIdenvioentradaLessThanOrEqualTo(Long value) {
            addCriterion("IDENVIOENTRADA <=", value, "idenvioentrada");
            return (Criteria) this;
        }

        public Criteria andIdenvioentradaIn(List<Long> values) {
            addCriterion("IDENVIOENTRADA in", values, "idenvioentrada");
            return (Criteria) this;
        }

        public Criteria andIdenvioentradaNotIn(List<Long> values) {
            addCriterion("IDENVIOENTRADA not in", values, "idenvioentrada");
            return (Criteria) this;
        }

        public Criteria andIdenvioentradaBetween(Long value1, Long value2) {
            addCriterion("IDENVIOENTRADA between", value1, value2, "idenvioentrada");
            return (Criteria) this;
        }

        public Criteria andIdenvioentradaNotBetween(Long value1, Long value2) {
            addCriterion("IDENVIOENTRADA not between", value1, value2, "idenvioentrada");
            return (Criteria) this;
        }

        public Criteria andEjganioIsNull() {
            addCriterion("EJGANIO is null");
            return (Criteria) this;
        }

        public Criteria andEjganioIsNotNull() {
            addCriterion("EJGANIO is not null");
            return (Criteria) this;
        }

        public Criteria andEjganioEqualTo(Short value) {
            addCriterion("EJGANIO =", value, "ejganio");
            return (Criteria) this;
        }

        public Criteria andEjganioNotEqualTo(Short value) {
            addCriterion("EJGANIO <>", value, "ejganio");
            return (Criteria) this;
        }

        public Criteria andEjganioGreaterThan(Short value) {
            addCriterion("EJGANIO >", value, "ejganio");
            return (Criteria) this;
        }

        public Criteria andEjganioGreaterThanOrEqualTo(Short value) {
            addCriterion("EJGANIO >=", value, "ejganio");
            return (Criteria) this;
        }

        public Criteria andEjganioLessThan(Short value) {
            addCriterion("EJGANIO <", value, "ejganio");
            return (Criteria) this;
        }

        public Criteria andEjganioLessThanOrEqualTo(Short value) {
            addCriterion("EJGANIO <=", value, "ejganio");
            return (Criteria) this;
        }

        public Criteria andEjganioIn(List<Short> values) {
            addCriterion("EJGANIO in", values, "ejganio");
            return (Criteria) this;
        }

        public Criteria andEjganioNotIn(List<Short> values) {
            addCriterion("EJGANIO not in", values, "ejganio");
            return (Criteria) this;
        }

        public Criteria andEjganioBetween(Short value1, Short value2) {
            addCriterion("EJGANIO between", value1, value2, "ejganio");
            return (Criteria) this;
        }

        public Criteria andEjganioNotBetween(Short value1, Short value2) {
            addCriterion("EJGANIO not between", value1, value2, "ejganio");
            return (Criteria) this;
        }

        public Criteria andEjgnumeroIsNull() {
            addCriterion("EJGNUMERO is null");
            return (Criteria) this;
        }

        public Criteria andEjgnumeroIsNotNull() {
            addCriterion("EJGNUMERO is not null");
            return (Criteria) this;
        }

        public Criteria andEjgnumeroEqualTo(Long value) {
            addCriterion("EJGNUMERO =", value, "ejgnumero");
            return (Criteria) this;
        }

        public Criteria andEjgnumeroNotEqualTo(Long value) {
            addCriterion("EJGNUMERO <>", value, "ejgnumero");
            return (Criteria) this;
        }

        public Criteria andEjgnumeroGreaterThan(Long value) {
            addCriterion("EJGNUMERO >", value, "ejgnumero");
            return (Criteria) this;
        }

        public Criteria andEjgnumeroGreaterThanOrEqualTo(Long value) {
            addCriterion("EJGNUMERO >=", value, "ejgnumero");
            return (Criteria) this;
        }

        public Criteria andEjgnumeroLessThan(Long value) {
            addCriterion("EJGNUMERO <", value, "ejgnumero");
            return (Criteria) this;
        }

        public Criteria andEjgnumeroLessThanOrEqualTo(Long value) {
            addCriterion("EJGNUMERO <=", value, "ejgnumero");
            return (Criteria) this;
        }

        public Criteria andEjgnumeroIn(List<Long> values) {
            addCriterion("EJGNUMERO in", values, "ejgnumero");
            return (Criteria) this;
        }

        public Criteria andEjgnumeroNotIn(List<Long> values) {
            addCriterion("EJGNUMERO not in", values, "ejgnumero");
            return (Criteria) this;
        }

        public Criteria andEjgnumeroBetween(Long value1, Long value2) {
            addCriterion("EJGNUMERO between", value1, value2, "ejgnumero");
            return (Criteria) this;
        }

        public Criteria andEjgnumeroNotBetween(Long value1, Long value2) {
            addCriterion("EJGNUMERO not between", value1, value2, "ejgnumero");
            return (Criteria) this;
        }

        public Criteria andEjgidtipoIsNull() {
            addCriterion("EJGIDTIPO is null");
            return (Criteria) this;
        }

        public Criteria andEjgidtipoIsNotNull() {
            addCriterion("EJGIDTIPO is not null");
            return (Criteria) this;
        }

        public Criteria andEjgidtipoEqualTo(Short value) {
            addCriterion("EJGIDTIPO =", value, "ejgidtipo");
            return (Criteria) this;
        }

        public Criteria andEjgidtipoNotEqualTo(Short value) {
            addCriterion("EJGIDTIPO <>", value, "ejgidtipo");
            return (Criteria) this;
        }

        public Criteria andEjgidtipoGreaterThan(Short value) {
            addCriterion("EJGIDTIPO >", value, "ejgidtipo");
            return (Criteria) this;
        }

        public Criteria andEjgidtipoGreaterThanOrEqualTo(Short value) {
            addCriterion("EJGIDTIPO >=", value, "ejgidtipo");
            return (Criteria) this;
        }

        public Criteria andEjgidtipoLessThan(Short value) {
            addCriterion("EJGIDTIPO <", value, "ejgidtipo");
            return (Criteria) this;
        }

        public Criteria andEjgidtipoLessThanOrEqualTo(Short value) {
            addCriterion("EJGIDTIPO <=", value, "ejgidtipo");
            return (Criteria) this;
        }

        public Criteria andEjgidtipoIn(List<Short> values) {
            addCriterion("EJGIDTIPO in", values, "ejgidtipo");
            return (Criteria) this;
        }

        public Criteria andEjgidtipoNotIn(List<Short> values) {
            addCriterion("EJGIDTIPO not in", values, "ejgidtipo");
            return (Criteria) this;
        }

        public Criteria andEjgidtipoBetween(Short value1, Short value2) {
            addCriterion("EJGIDTIPO between", value1, value2, "ejgidtipo");
            return (Criteria) this;
        }

        public Criteria andEjgidtipoNotBetween(Short value1, Short value2) {
            addCriterion("EJGIDTIPO not between", value1, value2, "ejgidtipo");
            return (Criteria) this;
        }

        public Criteria andDesignaanioIsNull() {
            addCriterion("DESIGNAANIO is null");
            return (Criteria) this;
        }

        public Criteria andDesignaanioIsNotNull() {
            addCriterion("DESIGNAANIO is not null");
            return (Criteria) this;
        }

        public Criteria andDesignaanioEqualTo(Short value) {
            addCriterion("DESIGNAANIO =", value, "designaanio");
            return (Criteria) this;
        }

        public Criteria andDesignaanioNotEqualTo(Short value) {
            addCriterion("DESIGNAANIO <>", value, "designaanio");
            return (Criteria) this;
        }

        public Criteria andDesignaanioGreaterThan(Short value) {
            addCriterion("DESIGNAANIO >", value, "designaanio");
            return (Criteria) this;
        }

        public Criteria andDesignaanioGreaterThanOrEqualTo(Short value) {
            addCriterion("DESIGNAANIO >=", value, "designaanio");
            return (Criteria) this;
        }

        public Criteria andDesignaanioLessThan(Short value) {
            addCriterion("DESIGNAANIO <", value, "designaanio");
            return (Criteria) this;
        }

        public Criteria andDesignaanioLessThanOrEqualTo(Short value) {
            addCriterion("DESIGNAANIO <=", value, "designaanio");
            return (Criteria) this;
        }

        public Criteria andDesignaanioIn(List<Short> values) {
            addCriterion("DESIGNAANIO in", values, "designaanio");
            return (Criteria) this;
        }

        public Criteria andDesignaanioNotIn(List<Short> values) {
            addCriterion("DESIGNAANIO not in", values, "designaanio");
            return (Criteria) this;
        }

        public Criteria andDesignaanioBetween(Short value1, Short value2) {
            addCriterion("DESIGNAANIO between", value1, value2, "designaanio");
            return (Criteria) this;
        }

        public Criteria andDesignaanioNotBetween(Short value1, Short value2) {
            addCriterion("DESIGNAANIO not between", value1, value2, "designaanio");
            return (Criteria) this;
        }

        public Criteria andDesignanumeroIsNull() {
            addCriterion("DESIGNANUMERO is null");
            return (Criteria) this;
        }

        public Criteria andDesignanumeroIsNotNull() {
            addCriterion("DESIGNANUMERO is not null");
            return (Criteria) this;
        }

        public Criteria andDesignanumeroEqualTo(Long value) {
            addCriterion("DESIGNANUMERO =", value, "designanumero");
            return (Criteria) this;
        }

        public Criteria andDesignanumeroNotEqualTo(Long value) {
            addCriterion("DESIGNANUMERO <>", value, "designanumero");
            return (Criteria) this;
        }

        public Criteria andDesignanumeroGreaterThan(Long value) {
            addCriterion("DESIGNANUMERO >", value, "designanumero");
            return (Criteria) this;
        }

        public Criteria andDesignanumeroGreaterThanOrEqualTo(Long value) {
            addCriterion("DESIGNANUMERO >=", value, "designanumero");
            return (Criteria) this;
        }

        public Criteria andDesignanumeroLessThan(Long value) {
            addCriterion("DESIGNANUMERO <", value, "designanumero");
            return (Criteria) this;
        }

        public Criteria andDesignanumeroLessThanOrEqualTo(Long value) {
            addCriterion("DESIGNANUMERO <=", value, "designanumero");
            return (Criteria) this;
        }

        public Criteria andDesignanumeroIn(List<Long> values) {
            addCriterion("DESIGNANUMERO in", values, "designanumero");
            return (Criteria) this;
        }

        public Criteria andDesignanumeroNotIn(List<Long> values) {
            addCriterion("DESIGNANUMERO not in", values, "designanumero");
            return (Criteria) this;
        }

        public Criteria andDesignanumeroBetween(Long value1, Long value2) {
            addCriterion("DESIGNANUMERO between", value1, value2, "designanumero");
            return (Criteria) this;
        }

        public Criteria andDesignanumeroNotBetween(Long value1, Long value2) {
            addCriterion("DESIGNANUMERO not between", value1, value2, "designanumero");
            return (Criteria) this;
        }

        public Criteria andDesignaidturnoIsNull() {
            addCriterion("DESIGNAIDTURNO is null");
            return (Criteria) this;
        }

        public Criteria andDesignaidturnoIsNotNull() {
            addCriterion("DESIGNAIDTURNO is not null");
            return (Criteria) this;
        }

        public Criteria andDesignaidturnoEqualTo(Integer value) {
            addCriterion("DESIGNAIDTURNO =", value, "designaidturno");
            return (Criteria) this;
        }

        public Criteria andDesignaidturnoNotEqualTo(Integer value) {
            addCriterion("DESIGNAIDTURNO <>", value, "designaidturno");
            return (Criteria) this;
        }

        public Criteria andDesignaidturnoGreaterThan(Integer value) {
            addCriterion("DESIGNAIDTURNO >", value, "designaidturno");
            return (Criteria) this;
        }

        public Criteria andDesignaidturnoGreaterThanOrEqualTo(Integer value) {
            addCriterion("DESIGNAIDTURNO >=", value, "designaidturno");
            return (Criteria) this;
        }

        public Criteria andDesignaidturnoLessThan(Integer value) {
            addCriterion("DESIGNAIDTURNO <", value, "designaidturno");
            return (Criteria) this;
        }

        public Criteria andDesignaidturnoLessThanOrEqualTo(Integer value) {
            addCriterion("DESIGNAIDTURNO <=", value, "designaidturno");
            return (Criteria) this;
        }

        public Criteria andDesignaidturnoIn(List<Integer> values) {
            addCriterion("DESIGNAIDTURNO in", values, "designaidturno");
            return (Criteria) this;
        }

        public Criteria andDesignaidturnoNotIn(List<Integer> values) {
            addCriterion("DESIGNAIDTURNO not in", values, "designaidturno");
            return (Criteria) this;
        }

        public Criteria andDesignaidturnoBetween(Integer value1, Integer value2) {
            addCriterion("DESIGNAIDTURNO between", value1, value2, "designaidturno");
            return (Criteria) this;
        }

        public Criteria andDesignaidturnoNotBetween(Integer value1, Integer value2) {
            addCriterion("DESIGNAIDTURNO not between", value1, value2, "designaidturno");
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
     * This class corresponds to the database table USCGAE_INT.SCS_COMUNICACIONES
     *
     * @mbg.generated do_not_delete_during_merge Wed Jun 14 11:34:46 CEST 2023
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE_INT.SCS_COMUNICACIONES
     *
     * @mbg.generated Wed Jun 14 11:34:46 CEST 2023
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