package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModPlantilladocConsultaExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.MOD_PLANTILLADOC_CONSULTA
     *
     * @mbg.generated Thu Dec 20 11:03:46 CET 2018
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.MOD_PLANTILLADOC_CONSULTA
     *
     * @mbg.generated Thu Dec 20 11:03:46 CET 2018
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE.MOD_PLANTILLADOC_CONSULTA
     *
     * @mbg.generated Thu Dec 20 11:03:46 CET 2018
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_CONSULTA
     *
     * @mbg.generated Thu Dec 20 11:03:46 CET 2018
     */
    public ModPlantilladocConsultaExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_CONSULTA
     *
     * @mbg.generated Thu Dec 20 11:03:46 CET 2018
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_CONSULTA
     *
     * @mbg.generated Thu Dec 20 11:03:46 CET 2018
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_CONSULTA
     *
     * @mbg.generated Thu Dec 20 11:03:46 CET 2018
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_CONSULTA
     *
     * @mbg.generated Thu Dec 20 11:03:46 CET 2018
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_CONSULTA
     *
     * @mbg.generated Thu Dec 20 11:03:46 CET 2018
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_CONSULTA
     *
     * @mbg.generated Thu Dec 20 11:03:46 CET 2018
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_CONSULTA
     *
     * @mbg.generated Thu Dec 20 11:03:46 CET 2018
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_CONSULTA
     *
     * @mbg.generated Thu Dec 20 11:03:46 CET 2018
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
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_CONSULTA
     *
     * @mbg.generated Thu Dec 20 11:03:46 CET 2018
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE.MOD_PLANTILLADOC_CONSULTA
     *
     * @mbg.generated Thu Dec 20 11:03:46 CET 2018
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.MOD_PLANTILLADOC_CONSULTA
     *
     * @mbg.generated Thu Dec 20 11:03:46 CET 2018
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
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.MOD_PLANTILLADOC_CONSULTA
     *
     * @mbg.generated do_not_delete_during_merge Thu Dec 20 11:03:46 CET 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE.MOD_PLANTILLADOC_CONSULTA
     *
     * @mbg.generated Thu Dec 20 11:03:46 CET 2018
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