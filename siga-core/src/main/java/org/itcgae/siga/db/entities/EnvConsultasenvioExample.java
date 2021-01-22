package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EnvConsultasenvioExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Tue Feb 11 13:06:53 CET 2020
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Tue Feb 11 13:06:53 CET 2020
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Tue Feb 11 13:06:53 CET 2020
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Tue Feb 11 13:06:53 CET 2020
     */
    public EnvConsultasenvioExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Tue Feb 11 13:06:53 CET 2020
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Tue Feb 11 13:06:53 CET 2020
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Tue Feb 11 13:06:53 CET 2020
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Tue Feb 11 13:06:53 CET 2020
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Tue Feb 11 13:06:53 CET 2020
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Tue Feb 11 13:06:53 CET 2020
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Tue Feb 11 13:06:53 CET 2020
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Tue Feb 11 13:06:53 CET 2020
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
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Tue Feb 11 13:06:53 CET 2020
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Tue Feb 11 13:06:53 CET 2020
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Tue Feb 11 13:06:53 CET 2020
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

        public Criteria andIdconsultaenvioIsNull() {
            addCriterion("IDCONSULTAENVIO is null");
            return (Criteria) this;
        }

        public Criteria andIdconsultaenvioIsNotNull() {
            addCriterion("IDCONSULTAENVIO is not null");
            return (Criteria) this;
        }

        public Criteria andIdconsultaenvioEqualTo(Long value) {
            addCriterion("IDCONSULTAENVIO =", value, "idconsultaenvio");
            return (Criteria) this;
        }

        public Criteria andIdconsultaenvioNotEqualTo(Long value) {
            addCriterion("IDCONSULTAENVIO <>", value, "idconsultaenvio");
            return (Criteria) this;
        }

        public Criteria andIdconsultaenvioGreaterThan(Long value) {
            addCriterion("IDCONSULTAENVIO >", value, "idconsultaenvio");
            return (Criteria) this;
        }

        public Criteria andIdconsultaenvioGreaterThanOrEqualTo(Long value) {
            addCriterion("IDCONSULTAENVIO >=", value, "idconsultaenvio");
            return (Criteria) this;
        }

        public Criteria andIdconsultaenvioLessThan(Long value) {
            addCriterion("IDCONSULTAENVIO <", value, "idconsultaenvio");
            return (Criteria) this;
        }

        public Criteria andIdconsultaenvioLessThanOrEqualTo(Long value) {
            addCriterion("IDCONSULTAENVIO <=", value, "idconsultaenvio");
            return (Criteria) this;
        }

        public Criteria andIdconsultaenvioIn(List<Long> values) {
            addCriterion("IDCONSULTAENVIO in", values, "idconsultaenvio");
            return (Criteria) this;
        }

        public Criteria andIdconsultaenvioNotIn(List<Long> values) {
            addCriterion("IDCONSULTAENVIO not in", values, "idconsultaenvio");
            return (Criteria) this;
        }

        public Criteria andIdconsultaenvioBetween(Long value1, Long value2) {
            addCriterion("IDCONSULTAENVIO between", value1, value2, "idconsultaenvio");
            return (Criteria) this;
        }

        public Criteria andIdconsultaenvioNotBetween(Long value1, Long value2) {
            addCriterion("IDCONSULTAENVIO not between", value1, value2, "idconsultaenvio");
            return (Criteria) this;
        }

        public Criteria andIdenvioIsNull() {
            addCriterion("IDENVIO is null");
            return (Criteria) this;
        }

        public Criteria andIdenvioIsNotNull() {
            addCriterion("IDENVIO is not null");
            return (Criteria) this;
        }

        public Criteria andIdenvioEqualTo(Long value) {
            addCriterion("IDENVIO =", value, "idenvio");
            return (Criteria) this;
        }

        public Criteria andIdenvioNotEqualTo(Long value) {
            addCriterion("IDENVIO <>", value, "idenvio");
            return (Criteria) this;
        }

        public Criteria andIdenvioGreaterThan(Long value) {
            addCriterion("IDENVIO >", value, "idenvio");
            return (Criteria) this;
        }

        public Criteria andIdenvioGreaterThanOrEqualTo(Long value) {
            addCriterion("IDENVIO >=", value, "idenvio");
            return (Criteria) this;
        }

        public Criteria andIdenvioLessThan(Long value) {
            addCriterion("IDENVIO <", value, "idenvio");
            return (Criteria) this;
        }

        public Criteria andIdenvioLessThanOrEqualTo(Long value) {
            addCriterion("IDENVIO <=", value, "idenvio");
            return (Criteria) this;
        }

        public Criteria andIdenvioIn(List<Long> values) {
            addCriterion("IDENVIO in", values, "idenvio");
            return (Criteria) this;
        }

        public Criteria andIdenvioNotIn(List<Long> values) {
            addCriterion("IDENVIO not in", values, "idenvio");
            return (Criteria) this;
        }

        public Criteria andIdenvioBetween(Long value1, Long value2) {
            addCriterion("IDENVIO between", value1, value2, "idenvio");
            return (Criteria) this;
        }

        public Criteria andIdenvioNotBetween(Long value1, Long value2) {
            addCriterion("IDENVIO not between", value1, value2, "idenvio");
            return (Criteria) this;
        }

        public Criteria andIdobjetivoIsNull() {
            addCriterion("IDOBJETIVO is null");
            return (Criteria) this;
        }

        public Criteria andIdobjetivoIsNotNull() {
            addCriterion("IDOBJETIVO is not null");
            return (Criteria) this;
        }

        public Criteria andIdobjetivoEqualTo(Long value) {
            addCriterion("IDOBJETIVO =", value, "idobjetivo");
            return (Criteria) this;
        }

        public Criteria andIdobjetivoNotEqualTo(Long value) {
            addCriterion("IDOBJETIVO <>", value, "idobjetivo");
            return (Criteria) this;
        }

        public Criteria andIdobjetivoGreaterThan(Long value) {
            addCriterion("IDOBJETIVO >", value, "idobjetivo");
            return (Criteria) this;
        }

        public Criteria andIdobjetivoGreaterThanOrEqualTo(Long value) {
            addCriterion("IDOBJETIVO >=", value, "idobjetivo");
            return (Criteria) this;
        }

        public Criteria andIdobjetivoLessThan(Long value) {
            addCriterion("IDOBJETIVO <", value, "idobjetivo");
            return (Criteria) this;
        }

        public Criteria andIdobjetivoLessThanOrEqualTo(Long value) {
            addCriterion("IDOBJETIVO <=", value, "idobjetivo");
            return (Criteria) this;
        }

        public Criteria andIdobjetivoIn(List<Long> values) {
            addCriterion("IDOBJETIVO in", values, "idobjetivo");
            return (Criteria) this;
        }

        public Criteria andIdobjetivoNotIn(List<Long> values) {
            addCriterion("IDOBJETIVO not in", values, "idobjetivo");
            return (Criteria) this;
        }

        public Criteria andIdobjetivoBetween(Long value1, Long value2) {
            addCriterion("IDOBJETIVO between", value1, value2, "idobjetivo");
            return (Criteria) this;
        }

        public Criteria andIdobjetivoNotBetween(Long value1, Long value2) {
            addCriterion("IDOBJETIVO not between", value1, value2, "idobjetivo");
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

        public Criteria andIdconsultaIsNull() {
            addCriterion("IDCONSULTA is null");
            return (Criteria) this;
        }

        public Criteria andIdconsultaIsNotNull() {
            addCriterion("IDCONSULTA is not null");
            return (Criteria) this;
        }

        public Criteria andIdconsultaEqualTo(Long value) {
            addCriterion("IDCONSULTA =", value, "idconsulta");
            return (Criteria) this;
        }

        public Criteria andIdconsultaNotEqualTo(Long value) {
            addCriterion("IDCONSULTA <>", value, "idconsulta");
            return (Criteria) this;
        }

        public Criteria andIdconsultaGreaterThan(Long value) {
            addCriterion("IDCONSULTA >", value, "idconsulta");
            return (Criteria) this;
        }

        public Criteria andIdconsultaGreaterThanOrEqualTo(Long value) {
            addCriterion("IDCONSULTA >=", value, "idconsulta");
            return (Criteria) this;
        }

        public Criteria andIdconsultaLessThan(Long value) {
            addCriterion("IDCONSULTA <", value, "idconsulta");
            return (Criteria) this;
        }

        public Criteria andIdconsultaLessThanOrEqualTo(Long value) {
            addCriterion("IDCONSULTA <=", value, "idconsulta");
            return (Criteria) this;
        }

        public Criteria andIdconsultaIn(List<Long> values) {
            addCriterion("IDCONSULTA in", values, "idconsulta");
            return (Criteria) this;
        }

        public Criteria andIdconsultaNotIn(List<Long> values) {
            addCriterion("IDCONSULTA not in", values, "idconsulta");
            return (Criteria) this;
        }

        public Criteria andIdconsultaBetween(Long value1, Long value2) {
            addCriterion("IDCONSULTA between", value1, value2, "idconsulta");
            return (Criteria) this;
        }

        public Criteria andIdconsultaNotBetween(Long value1, Long value2) {
            addCriterion("IDCONSULTA not between", value1, value2, "idconsulta");
            return (Criteria) this;
        }

        public Criteria andFechabajaIsNull() {
            addCriterion("FECHABAJA is null");
            return (Criteria) this;
        }

        public Criteria andFechabajaIsNotNull() {
            addCriterion("FECHABAJA is not null");
            return (Criteria) this;
        }

        public Criteria andFechabajaEqualTo(Date value) {
            addCriterion("FECHABAJA =", value, "fechabaja");
            return (Criteria) this;
        }

        public Criteria andFechabajaNotEqualTo(Date value) {
            addCriterion("FECHABAJA <>", value, "fechabaja");
            return (Criteria) this;
        }

        public Criteria andFechabajaGreaterThan(Date value) {
            addCriterion("FECHABAJA >", value, "fechabaja");
            return (Criteria) this;
        }

        public Criteria andFechabajaGreaterThanOrEqualTo(Date value) {
            addCriterion("FECHABAJA >=", value, "fechabaja");
            return (Criteria) this;
        }

        public Criteria andFechabajaLessThan(Date value) {
            addCriterion("FECHABAJA <", value, "fechabaja");
            return (Criteria) this;
        }

        public Criteria andFechabajaLessThanOrEqualTo(Date value) {
            addCriterion("FECHABAJA <=", value, "fechabaja");
            return (Criteria) this;
        }

        public Criteria andFechabajaIn(List<Date> values) {
            addCriterion("FECHABAJA in", values, "fechabaja");
            return (Criteria) this;
        }

        public Criteria andFechabajaNotIn(List<Date> values) {
            addCriterion("FECHABAJA not in", values, "fechabaja");
            return (Criteria) this;
        }

        public Criteria andFechabajaBetween(Date value1, Date value2) {
            addCriterion("FECHABAJA between", value1, value2, "fechabaja");
            return (Criteria) this;
        }

        public Criteria andFechabajaNotBetween(Date value1, Date value2) {
            addCriterion("FECHABAJA not between", value1, value2, "fechabaja");
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

        public Criteria andIdplantilladocumentoIsNull() {
            addCriterion("IDPLANTILLADOCUMENTO is null");
            return (Criteria) this;
        }

        public Criteria andIdplantilladocumentoIsNotNull() {
            addCriterion("IDPLANTILLADOCUMENTO is not null");
            return (Criteria) this;
        }

        public Criteria andIdplantilladocumentoEqualTo(Long value) {
            addCriterion("IDPLANTILLADOCUMENTO =", value, "idplantilladocumento");
            return (Criteria) this;
        }

        public Criteria andIdplantilladocumentoNotEqualTo(Long value) {
            addCriterion("IDPLANTILLADOCUMENTO <>", value, "idplantilladocumento");
            return (Criteria) this;
        }

        public Criteria andIdplantilladocumentoGreaterThan(Long value) {
            addCriterion("IDPLANTILLADOCUMENTO >", value, "idplantilladocumento");
            return (Criteria) this;
        }

        public Criteria andIdplantilladocumentoGreaterThanOrEqualTo(Long value) {
            addCriterion("IDPLANTILLADOCUMENTO >=", value, "idplantilladocumento");
            return (Criteria) this;
        }

        public Criteria andIdplantilladocumentoLessThan(Long value) {
            addCriterion("IDPLANTILLADOCUMENTO <", value, "idplantilladocumento");
            return (Criteria) this;
        }

        public Criteria andIdplantilladocumentoLessThanOrEqualTo(Long value) {
            addCriterion("IDPLANTILLADOCUMENTO <=", value, "idplantilladocumento");
            return (Criteria) this;
        }

        public Criteria andIdplantilladocumentoIn(List<Long> values) {
            addCriterion("IDPLANTILLADOCUMENTO in", values, "idplantilladocumento");
            return (Criteria) this;
        }

        public Criteria andIdplantilladocumentoNotIn(List<Long> values) {
            addCriterion("IDPLANTILLADOCUMENTO not in", values, "idplantilladocumento");
            return (Criteria) this;
        }

        public Criteria andIdplantilladocumentoBetween(Long value1, Long value2) {
            addCriterion("IDPLANTILLADOCUMENTO between", value1, value2, "idplantilladocumento");
            return (Criteria) this;
        }

        public Criteria andIdplantilladocumentoNotBetween(Long value1, Long value2) {
            addCriterion("IDPLANTILLADOCUMENTO not between", value1, value2, "idplantilladocumento");
            return (Criteria) this;
        }

        public Criteria andIdmodelocomunicacionIsNull() {
            addCriterion("IDMODELOCOMUNICACION is null");
            return (Criteria) this;
        }

        public Criteria andIdmodelocomunicacionIsNotNull() {
            addCriterion("IDMODELOCOMUNICACION is not null");
            return (Criteria) this;
        }

        public Criteria andIdmodelocomunicacionEqualTo(Long value) {
            addCriterion("IDMODELOCOMUNICACION =", value, "idmodelocomunicacion");
            return (Criteria) this;
        }

        public Criteria andIdmodelocomunicacionNotEqualTo(Long value) {
            addCriterion("IDMODELOCOMUNICACION <>", value, "idmodelocomunicacion");
            return (Criteria) this;
        }

        public Criteria andIdmodelocomunicacionGreaterThan(Long value) {
            addCriterion("IDMODELOCOMUNICACION >", value, "idmodelocomunicacion");
            return (Criteria) this;
        }

        public Criteria andIdmodelocomunicacionGreaterThanOrEqualTo(Long value) {
            addCriterion("IDMODELOCOMUNICACION >=", value, "idmodelocomunicacion");
            return (Criteria) this;
        }

        public Criteria andIdmodelocomunicacionLessThan(Long value) {
            addCriterion("IDMODELOCOMUNICACION <", value, "idmodelocomunicacion");
            return (Criteria) this;
        }

        public Criteria andIdmodelocomunicacionLessThanOrEqualTo(Long value) {
            addCriterion("IDMODELOCOMUNICACION <=", value, "idmodelocomunicacion");
            return (Criteria) this;
        }

        public Criteria andIdmodelocomunicacionIn(List<Long> values) {
            addCriterion("IDMODELOCOMUNICACION in", values, "idmodelocomunicacion");
            return (Criteria) this;
        }

        public Criteria andIdmodelocomunicacionNotIn(List<Long> values) {
            addCriterion("IDMODELOCOMUNICACION not in", values, "idmodelocomunicacion");
            return (Criteria) this;
        }

        public Criteria andIdmodelocomunicacionBetween(Long value1, Long value2) {
            addCriterion("IDMODELOCOMUNICACION between", value1, value2, "idmodelocomunicacion");
            return (Criteria) this;
        }

        public Criteria andIdmodelocomunicacionNotBetween(Long value1, Long value2) {
            addCriterion("IDMODELOCOMUNICACION not between", value1, value2, "idmodelocomunicacion");
            return (Criteria) this;
        }

        public Criteria andIdinformeIsNull() {
            addCriterion("IDINFORME is null");
            return (Criteria) this;
        }

        public Criteria andIdinformeIsNotNull() {
            addCriterion("IDINFORME is not null");
            return (Criteria) this;
        }

        public Criteria andIdinformeEqualTo(Long value) {
            addCriterion("IDINFORME =", value, "idinforme");
            return (Criteria) this;
        }

        public Criteria andIdinformeNotEqualTo(Long value) {
            addCriterion("IDINFORME <>", value, "idinforme");
            return (Criteria) this;
        }

        public Criteria andIdinformeGreaterThan(Long value) {
            addCriterion("IDINFORME >", value, "idinforme");
            return (Criteria) this;
        }

        public Criteria andIdinformeGreaterThanOrEqualTo(Long value) {
            addCriterion("IDINFORME >=", value, "idinforme");
            return (Criteria) this;
        }

        public Criteria andIdinformeLessThan(Long value) {
            addCriterion("IDINFORME <", value, "idinforme");
            return (Criteria) this;
        }

        public Criteria andIdinformeLessThanOrEqualTo(Long value) {
            addCriterion("IDINFORME <=", value, "idinforme");
            return (Criteria) this;
        }

        public Criteria andIdinformeIn(List<Long> values) {
            addCriterion("IDINFORME in", values, "idinforme");
            return (Criteria) this;
        }

        public Criteria andIdinformeNotIn(List<Long> values) {
            addCriterion("IDINFORME not in", values, "idinforme");
            return (Criteria) this;
        }

        public Criteria andIdinformeBetween(Long value1, Long value2) {
            addCriterion("IDINFORME between", value1, value2, "idinforme");
            return (Criteria) this;
        }

        public Criteria andIdinformeNotBetween(Long value1, Long value2) {
            addCriterion("IDINFORME not between", value1, value2, "idinforme");
            return (Criteria) this;
        }

        public Criteria andSufijoIsNull() {
            addCriterion("SUFIJO is null");
            return (Criteria) this;
        }

        public Criteria andSufijoIsNotNull() {
            addCriterion("SUFIJO is not null");
            return (Criteria) this;
        }

        public Criteria andSufijoEqualTo(String value) {
            addCriterion("SUFIJO =", value, "sufijo");
            return (Criteria) this;
        }

        public Criteria andSufijoNotEqualTo(String value) {
            addCriterion("SUFIJO <>", value, "sufijo");
            return (Criteria) this;
        }

        public Criteria andSufijoGreaterThan(String value) {
            addCriterion("SUFIJO >", value, "sufijo");
            return (Criteria) this;
        }

        public Criteria andSufijoGreaterThanOrEqualTo(String value) {
            addCriterion("SUFIJO >=", value, "sufijo");
            return (Criteria) this;
        }

        public Criteria andSufijoLessThan(String value) {
            addCriterion("SUFIJO <", value, "sufijo");
            return (Criteria) this;
        }

        public Criteria andSufijoLessThanOrEqualTo(String value) {
            addCriterion("SUFIJO <=", value, "sufijo");
            return (Criteria) this;
        }

        public Criteria andSufijoLike(String value) {
            addCriterion("SUFIJO like", value, "sufijo");
            return (Criteria) this;
        }

        public Criteria andSufijoNotLike(String value) {
            addCriterion("SUFIJO not like", value, "sufijo");
            return (Criteria) this;
        }

        public Criteria andSufijoIn(List<String> values) {
            addCriterion("SUFIJO in", values, "sufijo");
            return (Criteria) this;
        }

        public Criteria andSufijoNotIn(List<String> values) {
            addCriterion("SUFIJO not in", values, "sufijo");
            return (Criteria) this;
        }

        public Criteria andSufijoBetween(String value1, String value2) {
            addCriterion("SUFIJO between", value1, value2, "sufijo");
            return (Criteria) this;
        }

        public Criteria andSufijoNotBetween(String value1, String value2) {
            addCriterion("SUFIJO not between", value1, value2, "sufijo");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated do_not_delete_during_merge Tue Feb 11 13:06:53 CET 2020
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.ENV_CONSULTASENVIO
     *
     * @mbg.generated Tue Feb 11 13:06:53 CET 2020
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