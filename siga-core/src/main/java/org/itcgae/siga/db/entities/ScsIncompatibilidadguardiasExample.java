package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScsIncompatibilidadguardiasExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.SCS_INCOMPATIBILIDADGUARDIAS
     *
     * @mbg.generated Wed Dec 04 13:34:11 CET 2019
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.SCS_INCOMPATIBILIDADGUARDIAS
     *
     * @mbg.generated Wed Dec 04 13:34:11 CET 2019
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.SCS_INCOMPATIBILIDADGUARDIAS
     *
     * @mbg.generated Wed Dec 04 13:34:11 CET 2019
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_INCOMPATIBILIDADGUARDIAS
     *
     * @mbg.generated Wed Dec 04 13:34:11 CET 2019
     */
    public ScsIncompatibilidadguardiasExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_INCOMPATIBILIDADGUARDIAS
     *
     * @mbg.generated Wed Dec 04 13:34:11 CET 2019
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_INCOMPATIBILIDADGUARDIAS
     *
     * @mbg.generated Wed Dec 04 13:34:11 CET 2019
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_INCOMPATIBILIDADGUARDIAS
     *
     * @mbg.generated Wed Dec 04 13:34:11 CET 2019
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_INCOMPATIBILIDADGUARDIAS
     *
     * @mbg.generated Wed Dec 04 13:34:11 CET 2019
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_INCOMPATIBILIDADGUARDIAS
     *
     * @mbg.generated Wed Dec 04 13:34:11 CET 2019
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_INCOMPATIBILIDADGUARDIAS
     *
     * @mbg.generated Wed Dec 04 13:34:11 CET 2019
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_INCOMPATIBILIDADGUARDIAS
     *
     * @mbg.generated Wed Dec 04 13:34:11 CET 2019
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_INCOMPATIBILIDADGUARDIAS
     *
     * @mbg.generated Wed Dec 04 13:34:11 CET 2019
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
     * This method corresponds to the database table USCGAE.SCS_INCOMPATIBILIDADGUARDIAS
     *
     * @mbg.generated Wed Dec 04 13:34:11 CET 2019
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.SCS_INCOMPATIBILIDADGUARDIAS
     *
     * @mbg.generated Wed Dec 04 13:34:11 CET 2019
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.SCS_INCOMPATIBILIDADGUARDIAS
     *
     * @mbg.generated Wed Dec 04 13:34:11 CET 2019
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

        public Criteria andIdguardiaIsNull() {
            addCriterion("IDGUARDIA is null");
            return (Criteria) this;
        }

        public Criteria andIdguardiaIsNotNull() {
            addCriterion("IDGUARDIA is not null");
            return (Criteria) this;
        }

        public Criteria andIdguardiaEqualTo(Integer value) {
            addCriterion("IDGUARDIA =", value, "idguardia");
            return (Criteria) this;
        }

        public Criteria andIdguardiaNotEqualTo(Integer value) {
            addCriterion("IDGUARDIA <>", value, "idguardia");
            return (Criteria) this;
        }

        public Criteria andIdguardiaGreaterThan(Integer value) {
            addCriterion("IDGUARDIA >", value, "idguardia");
            return (Criteria) this;
        }

        public Criteria andIdguardiaGreaterThanOrEqualTo(Integer value) {
            addCriterion("IDGUARDIA >=", value, "idguardia");
            return (Criteria) this;
        }

        public Criteria andIdguardiaLessThan(Integer value) {
            addCriterion("IDGUARDIA <", value, "idguardia");
            return (Criteria) this;
        }

        public Criteria andIdguardiaLessThanOrEqualTo(Integer value) {
            addCriterion("IDGUARDIA <=", value, "idguardia");
            return (Criteria) this;
        }

        public Criteria andIdguardiaIn(List<Integer> values) {
            addCriterion("IDGUARDIA in", values, "idguardia");
            return (Criteria) this;
        }

        public Criteria andIdguardiaNotIn(List<Integer> values) {
            addCriterion("IDGUARDIA not in", values, "idguardia");
            return (Criteria) this;
        }

        public Criteria andIdguardiaBetween(Integer value1, Integer value2) {
            addCriterion("IDGUARDIA between", value1, value2, "idguardia");
            return (Criteria) this;
        }

        public Criteria andIdguardiaNotBetween(Integer value1, Integer value2) {
            addCriterion("IDGUARDIA not between", value1, value2, "idguardia");
            return (Criteria) this;
        }

        public Criteria andIdturnoIncompatibleIsNull() {
            addCriterion("IDTURNO_INCOMPATIBLE is null");
            return (Criteria) this;
        }

        public Criteria andIdturnoIncompatibleIsNotNull() {
            addCriterion("IDTURNO_INCOMPATIBLE is not null");
            return (Criteria) this;
        }

        public Criteria andIdturnoIncompatibleEqualTo(Integer value) {
            addCriterion("IDTURNO_INCOMPATIBLE =", value, "idturnoIncompatible");
            return (Criteria) this;
        }

        public Criteria andIdturnoIncompatibleNotEqualTo(Integer value) {
            addCriterion("IDTURNO_INCOMPATIBLE <>", value, "idturnoIncompatible");
            return (Criteria) this;
        }

        public Criteria andIdturnoIncompatibleGreaterThan(Integer value) {
            addCriterion("IDTURNO_INCOMPATIBLE >", value, "idturnoIncompatible");
            return (Criteria) this;
        }

        public Criteria andIdturnoIncompatibleGreaterThanOrEqualTo(Integer value) {
            addCriterion("IDTURNO_INCOMPATIBLE >=", value, "idturnoIncompatible");
            return (Criteria) this;
        }

        public Criteria andIdturnoIncompatibleLessThan(Integer value) {
            addCriterion("IDTURNO_INCOMPATIBLE <", value, "idturnoIncompatible");
            return (Criteria) this;
        }

        public Criteria andIdturnoIncompatibleLessThanOrEqualTo(Integer value) {
            addCriterion("IDTURNO_INCOMPATIBLE <=", value, "idturnoIncompatible");
            return (Criteria) this;
        }

        public Criteria andIdturnoIncompatibleIn(List<Integer> values) {
            addCriterion("IDTURNO_INCOMPATIBLE in", values, "idturnoIncompatible");
            return (Criteria) this;
        }

        public Criteria andIdturnoIncompatibleNotIn(List<Integer> values) {
            addCriterion("IDTURNO_INCOMPATIBLE not in", values, "idturnoIncompatible");
            return (Criteria) this;
        }

        public Criteria andIdturnoIncompatibleBetween(Integer value1, Integer value2) {
            addCriterion("IDTURNO_INCOMPATIBLE between", value1, value2, "idturnoIncompatible");
            return (Criteria) this;
        }

        public Criteria andIdturnoIncompatibleNotBetween(Integer value1, Integer value2) {
            addCriterion("IDTURNO_INCOMPATIBLE not between", value1, value2, "idturnoIncompatible");
            return (Criteria) this;
        }

        public Criteria andIdguardiaIncompatibleIsNull() {
            addCriterion("IDGUARDIA_INCOMPATIBLE is null");
            return (Criteria) this;
        }

        public Criteria andIdguardiaIncompatibleIsNotNull() {
            addCriterion("IDGUARDIA_INCOMPATIBLE is not null");
            return (Criteria) this;
        }

        public Criteria andIdguardiaIncompatibleEqualTo(Integer value) {
            addCriterion("IDGUARDIA_INCOMPATIBLE =", value, "idguardiaIncompatible");
            return (Criteria) this;
        }

        public Criteria andIdguardiaIncompatibleNotEqualTo(Integer value) {
            addCriterion("IDGUARDIA_INCOMPATIBLE <>", value, "idguardiaIncompatible");
            return (Criteria) this;
        }

        public Criteria andIdguardiaIncompatibleGreaterThan(Integer value) {
            addCriterion("IDGUARDIA_INCOMPATIBLE >", value, "idguardiaIncompatible");
            return (Criteria) this;
        }

        public Criteria andIdguardiaIncompatibleGreaterThanOrEqualTo(Integer value) {
            addCriterion("IDGUARDIA_INCOMPATIBLE >=", value, "idguardiaIncompatible");
            return (Criteria) this;
        }

        public Criteria andIdguardiaIncompatibleLessThan(Integer value) {
            addCriterion("IDGUARDIA_INCOMPATIBLE <", value, "idguardiaIncompatible");
            return (Criteria) this;
        }

        public Criteria andIdguardiaIncompatibleLessThanOrEqualTo(Integer value) {
            addCriterion("IDGUARDIA_INCOMPATIBLE <=", value, "idguardiaIncompatible");
            return (Criteria) this;
        }

        public Criteria andIdguardiaIncompatibleIn(List<Integer> values) {
            addCriterion("IDGUARDIA_INCOMPATIBLE in", values, "idguardiaIncompatible");
            return (Criteria) this;
        }

        public Criteria andIdguardiaIncompatibleNotIn(List<Integer> values) {
            addCriterion("IDGUARDIA_INCOMPATIBLE not in", values, "idguardiaIncompatible");
            return (Criteria) this;
        }

        public Criteria andIdguardiaIncompatibleBetween(Integer value1, Integer value2) {
            addCriterion("IDGUARDIA_INCOMPATIBLE between", value1, value2, "idguardiaIncompatible");
            return (Criteria) this;
        }

        public Criteria andIdguardiaIncompatibleNotBetween(Integer value1, Integer value2) {
            addCriterion("IDGUARDIA_INCOMPATIBLE not between", value1, value2, "idguardiaIncompatible");
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

        public Criteria andMotivosIsNull() {
            addCriterion("MOTIVOS is null");
            return (Criteria) this;
        }

        public Criteria andMotivosIsNotNull() {
            addCriterion("MOTIVOS is not null");
            return (Criteria) this;
        }

        public Criteria andMotivosEqualTo(String value) {
            addCriterion("MOTIVOS =", value, "motivos");
            return (Criteria) this;
        }

        public Criteria andMotivosNotEqualTo(String value) {
            addCriterion("MOTIVOS <>", value, "motivos");
            return (Criteria) this;
        }

        public Criteria andMotivosGreaterThan(String value) {
            addCriterion("MOTIVOS >", value, "motivos");
            return (Criteria) this;
        }

        public Criteria andMotivosGreaterThanOrEqualTo(String value) {
            addCriterion("MOTIVOS >=", value, "motivos");
            return (Criteria) this;
        }

        public Criteria andMotivosLessThan(String value) {
            addCriterion("MOTIVOS <", value, "motivos");
            return (Criteria) this;
        }

        public Criteria andMotivosLessThanOrEqualTo(String value) {
            addCriterion("MOTIVOS <=", value, "motivos");
            return (Criteria) this;
        }

        public Criteria andMotivosLike(String value) {
            addCriterion("MOTIVOS like", value, "motivos");
            return (Criteria) this;
        }

        public Criteria andMotivosNotLike(String value) {
            addCriterion("MOTIVOS not like", value, "motivos");
            return (Criteria) this;
        }

        public Criteria andMotivosIn(List<String> values) {
            addCriterion("MOTIVOS in", values, "motivos");
            return (Criteria) this;
        }

        public Criteria andMotivosNotIn(List<String> values) {
            addCriterion("MOTIVOS not in", values, "motivos");
            return (Criteria) this;
        }

        public Criteria andMotivosBetween(String value1, String value2) {
            addCriterion("MOTIVOS between", value1, value2, "motivos");
            return (Criteria) this;
        }

        public Criteria andMotivosNotBetween(String value1, String value2) {
            addCriterion("MOTIVOS not between", value1, value2, "motivos");
            return (Criteria) this;
        }

        public Criteria andDiasseparacionguardiasIsNull() {
            addCriterion("DIASSEPARACIONGUARDIAS is null");
            return (Criteria) this;
        }

        public Criteria andDiasseparacionguardiasIsNotNull() {
            addCriterion("DIASSEPARACIONGUARDIAS is not null");
            return (Criteria) this;
        }

        public Criteria andDiasseparacionguardiasEqualTo(Short value) {
            addCriterion("DIASSEPARACIONGUARDIAS =", value, "diasseparacionguardias");
            return (Criteria) this;
        }

        public Criteria andDiasseparacionguardiasNotEqualTo(Short value) {
            addCriterion("DIASSEPARACIONGUARDIAS <>", value, "diasseparacionguardias");
            return (Criteria) this;
        }

        public Criteria andDiasseparacionguardiasGreaterThan(Short value) {
            addCriterion("DIASSEPARACIONGUARDIAS >", value, "diasseparacionguardias");
            return (Criteria) this;
        }

        public Criteria andDiasseparacionguardiasGreaterThanOrEqualTo(Short value) {
            addCriterion("DIASSEPARACIONGUARDIAS >=", value, "diasseparacionguardias");
            return (Criteria) this;
        }

        public Criteria andDiasseparacionguardiasLessThan(Short value) {
            addCriterion("DIASSEPARACIONGUARDIAS <", value, "diasseparacionguardias");
            return (Criteria) this;
        }

        public Criteria andDiasseparacionguardiasLessThanOrEqualTo(Short value) {
            addCriterion("DIASSEPARACIONGUARDIAS <=", value, "diasseparacionguardias");
            return (Criteria) this;
        }

        public Criteria andDiasseparacionguardiasIn(List<Short> values) {
            addCriterion("DIASSEPARACIONGUARDIAS in", values, "diasseparacionguardias");
            return (Criteria) this;
        }

        public Criteria andDiasseparacionguardiasNotIn(List<Short> values) {
            addCriterion("DIASSEPARACIONGUARDIAS not in", values, "diasseparacionguardias");
            return (Criteria) this;
        }

        public Criteria andDiasseparacionguardiasBetween(Short value1, Short value2) {
            addCriterion("DIASSEPARACIONGUARDIAS between", value1, value2, "diasseparacionguardias");
            return (Criteria) this;
        }

        public Criteria andDiasseparacionguardiasNotBetween(Short value1, Short value2) {
            addCriterion("DIASSEPARACIONGUARDIAS not between", value1, value2, "diasseparacionguardias");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.SCS_INCOMPATIBILIDADGUARDIAS
     *
     * @mbg.generated do_not_delete_during_merge Wed Dec 04 13:34:11 CET 2019
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.SCS_INCOMPATIBILIDADGUARDIAS
     *
     * @mbg.generated Wed Dec 04 13:34:11 CET 2019
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