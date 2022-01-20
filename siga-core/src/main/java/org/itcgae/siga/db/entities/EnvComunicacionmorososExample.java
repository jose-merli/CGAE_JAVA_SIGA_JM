package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EnvComunicacionmorososExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE_INT.ENV_COMUNICACIONMOROSOS
     *
     * @mbg.generated Thu Dec 02 10:26:18 CET 2021
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE_INT.ENV_COMUNICACIONMOROSOS
     *
     * @mbg.generated Thu Dec 02 10:26:18 CET 2021
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE_INT.ENV_COMUNICACIONMOROSOS
     *
     * @mbg.generated Thu Dec 02 10:26:18 CET 2021
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.ENV_COMUNICACIONMOROSOS
     *
     * @mbg.generated Thu Dec 02 10:26:18 CET 2021
     */
    public EnvComunicacionmorososExample() {
        oredCriteria = new ArrayList<>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.ENV_COMUNICACIONMOROSOS
     *
     * @mbg.generated Thu Dec 02 10:26:18 CET 2021
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.ENV_COMUNICACIONMOROSOS
     *
     * @mbg.generated Thu Dec 02 10:26:18 CET 2021
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.ENV_COMUNICACIONMOROSOS
     *
     * @mbg.generated Thu Dec 02 10:26:18 CET 2021
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.ENV_COMUNICACIONMOROSOS
     *
     * @mbg.generated Thu Dec 02 10:26:18 CET 2021
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.ENV_COMUNICACIONMOROSOS
     *
     * @mbg.generated Thu Dec 02 10:26:18 CET 2021
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.ENV_COMUNICACIONMOROSOS
     *
     * @mbg.generated Thu Dec 02 10:26:18 CET 2021
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.ENV_COMUNICACIONMOROSOS
     *
     * @mbg.generated Thu Dec 02 10:26:18 CET 2021
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.ENV_COMUNICACIONMOROSOS
     *
     * @mbg.generated Thu Dec 02 10:26:18 CET 2021
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
     * This method corresponds to the database table USCGAE_INT.ENV_COMUNICACIONMOROSOS
     *
     * @mbg.generated Thu Dec 02 10:26:18 CET 2021
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.ENV_COMUNICACIONMOROSOS
     *
     * @mbg.generated Thu Dec 02 10:26:18 CET 2021
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE_INT.ENV_COMUNICACIONMOROSOS
     *
     * @mbg.generated Thu Dec 02 10:26:18 CET 2021
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

        public Criteria andIdfacturaIsNull() {
            addCriterion("IDFACTURA is null");
            return (Criteria) this;
        }

        public Criteria andIdfacturaIsNotNull() {
            addCriterion("IDFACTURA is not null");
            return (Criteria) this;
        }

        public Criteria andIdfacturaEqualTo(String value) {
            addCriterion("IDFACTURA =", value, "idfactura");
            return (Criteria) this;
        }

        public Criteria andIdfacturaNotEqualTo(String value) {
            addCriterion("IDFACTURA <>", value, "idfactura");
            return (Criteria) this;
        }

        public Criteria andIdfacturaGreaterThan(String value) {
            addCriterion("IDFACTURA >", value, "idfactura");
            return (Criteria) this;
        }

        public Criteria andIdfacturaGreaterThanOrEqualTo(String value) {
            addCriterion("IDFACTURA >=", value, "idfactura");
            return (Criteria) this;
        }

        public Criteria andIdfacturaLessThan(String value) {
            addCriterion("IDFACTURA <", value, "idfactura");
            return (Criteria) this;
        }

        public Criteria andIdfacturaLessThanOrEqualTo(String value) {
            addCriterion("IDFACTURA <=", value, "idfactura");
            return (Criteria) this;
        }

        public Criteria andIdfacturaLike(String value) {
            addCriterion("IDFACTURA like", value, "idfactura");
            return (Criteria) this;
        }

        public Criteria andIdfacturaNotLike(String value) {
            addCriterion("IDFACTURA not like", value, "idfactura");
            return (Criteria) this;
        }

        public Criteria andIdfacturaIn(List<String> values) {
            addCriterion("IDFACTURA in", values, "idfactura");
            return (Criteria) this;
        }

        public Criteria andIdfacturaNotIn(List<String> values) {
            addCriterion("IDFACTURA not in", values, "idfactura");
            return (Criteria) this;
        }

        public Criteria andIdfacturaBetween(String value1, String value2) {
            addCriterion("IDFACTURA between", value1, value2, "idfactura");
            return (Criteria) this;
        }

        public Criteria andIdfacturaNotBetween(String value1, String value2) {
            addCriterion("IDFACTURA not between", value1, value2, "idfactura");
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

        public Criteria andFechaEnvioIsNull() {
            addCriterion("FECHA_ENVIO is null");
            return (Criteria) this;
        }

        public Criteria andFechaEnvioIsNotNull() {
            addCriterion("FECHA_ENVIO is not null");
            return (Criteria) this;
        }

        public Criteria andFechaEnvioEqualTo(Date value) {
            addCriterion("FECHA_ENVIO =", value, "fechaEnvio");
            return (Criteria) this;
        }

        public Criteria andFechaEnvioNotEqualTo(Date value) {
            addCriterion("FECHA_ENVIO <>", value, "fechaEnvio");
            return (Criteria) this;
        }

        public Criteria andFechaEnvioGreaterThan(Date value) {
            addCriterion("FECHA_ENVIO >", value, "fechaEnvio");
            return (Criteria) this;
        }

        public Criteria andFechaEnvioGreaterThanOrEqualTo(Date value) {
            addCriterion("FECHA_ENVIO >=", value, "fechaEnvio");
            return (Criteria) this;
        }

        public Criteria andFechaEnvioLessThan(Date value) {
            addCriterion("FECHA_ENVIO <", value, "fechaEnvio");
            return (Criteria) this;
        }

        public Criteria andFechaEnvioLessThanOrEqualTo(Date value) {
            addCriterion("FECHA_ENVIO <=", value, "fechaEnvio");
            return (Criteria) this;
        }

        public Criteria andFechaEnvioIn(List<Date> values) {
            addCriterion("FECHA_ENVIO in", values, "fechaEnvio");
            return (Criteria) this;
        }

        public Criteria andFechaEnvioNotIn(List<Date> values) {
            addCriterion("FECHA_ENVIO not in", values, "fechaEnvio");
            return (Criteria) this;
        }

        public Criteria andFechaEnvioBetween(Date value1, Date value2) {
            addCriterion("FECHA_ENVIO between", value1, value2, "fechaEnvio");
            return (Criteria) this;
        }

        public Criteria andFechaEnvioNotBetween(Date value1, Date value2) {
            addCriterion("FECHA_ENVIO not between", value1, value2, "fechaEnvio");
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

        public Criteria andPathdocumentoIsNull() {
            addCriterion("PATHDOCUMENTO is null");
            return (Criteria) this;
        }

        public Criteria andPathdocumentoIsNotNull() {
            addCriterion("PATHDOCUMENTO is not null");
            return (Criteria) this;
        }

        public Criteria andPathdocumentoEqualTo(String value) {
            addCriterion("PATHDOCUMENTO =", value, "pathdocumento");
            return (Criteria) this;
        }

        public Criteria andPathdocumentoNotEqualTo(String value) {
            addCriterion("PATHDOCUMENTO <>", value, "pathdocumento");
            return (Criteria) this;
        }

        public Criteria andPathdocumentoGreaterThan(String value) {
            addCriterion("PATHDOCUMENTO >", value, "pathdocumento");
            return (Criteria) this;
        }

        public Criteria andPathdocumentoGreaterThanOrEqualTo(String value) {
            addCriterion("PATHDOCUMENTO >=", value, "pathdocumento");
            return (Criteria) this;
        }

        public Criteria andPathdocumentoLessThan(String value) {
            addCriterion("PATHDOCUMENTO <", value, "pathdocumento");
            return (Criteria) this;
        }

        public Criteria andPathdocumentoLessThanOrEqualTo(String value) {
            addCriterion("PATHDOCUMENTO <=", value, "pathdocumento");
            return (Criteria) this;
        }

        public Criteria andPathdocumentoLike(String value) {
            addCriterion("PATHDOCUMENTO like", value, "pathdocumento");
            return (Criteria) this;
        }

        public Criteria andPathdocumentoNotLike(String value) {
            addCriterion("PATHDOCUMENTO not like", value, "pathdocumento");
            return (Criteria) this;
        }

        public Criteria andPathdocumentoIn(List<String> values) {
            addCriterion("PATHDOCUMENTO in", values, "pathdocumento");
            return (Criteria) this;
        }

        public Criteria andPathdocumentoNotIn(List<String> values) {
            addCriterion("PATHDOCUMENTO not in", values, "pathdocumento");
            return (Criteria) this;
        }

        public Criteria andPathdocumentoBetween(String value1, String value2) {
            addCriterion("PATHDOCUMENTO between", value1, value2, "pathdocumento");
            return (Criteria) this;
        }

        public Criteria andPathdocumentoNotBetween(String value1, String value2) {
            addCriterion("PATHDOCUMENTO not between", value1, value2, "pathdocumento");
            return (Criteria) this;
        }

        public Criteria andIdenviodocIsNull() {
            addCriterion("IDENVIODOC is null");
            return (Criteria) this;
        }

        public Criteria andIdenviodocIsNotNull() {
            addCriterion("IDENVIODOC is not null");
            return (Criteria) this;
        }

        public Criteria andIdenviodocEqualTo(Long value) {
            addCriterion("IDENVIODOC =", value, "idenviodoc");
            return (Criteria) this;
        }

        public Criteria andIdenviodocNotEqualTo(Long value) {
            addCriterion("IDENVIODOC <>", value, "idenviodoc");
            return (Criteria) this;
        }

        public Criteria andIdenviodocGreaterThan(Long value) {
            addCriterion("IDENVIODOC >", value, "idenviodoc");
            return (Criteria) this;
        }

        public Criteria andIdenviodocGreaterThanOrEqualTo(Long value) {
            addCriterion("IDENVIODOC >=", value, "idenviodoc");
            return (Criteria) this;
        }

        public Criteria andIdenviodocLessThan(Long value) {
            addCriterion("IDENVIODOC <", value, "idenviodoc");
            return (Criteria) this;
        }

        public Criteria andIdenviodocLessThanOrEqualTo(Long value) {
            addCriterion("IDENVIODOC <=", value, "idenviodoc");
            return (Criteria) this;
        }

        public Criteria andIdenviodocIn(List<Long> values) {
            addCriterion("IDENVIODOC in", values, "idenviodoc");
            return (Criteria) this;
        }

        public Criteria andIdenviodocNotIn(List<Long> values) {
            addCriterion("IDENVIODOC not in", values, "idenviodoc");
            return (Criteria) this;
        }

        public Criteria andIdenviodocBetween(Long value1, Long value2) {
            addCriterion("IDENVIODOC between", value1, value2, "idenviodoc");
            return (Criteria) this;
        }

        public Criteria andIdenviodocNotBetween(Long value1, Long value2) {
            addCriterion("IDENVIODOC not between", value1, value2, "idenviodoc");
            return (Criteria) this;
        }

        public Criteria andIddocumentoIsNull() {
            addCriterion("IDDOCUMENTO is null");
            return (Criteria) this;
        }

        public Criteria andIddocumentoIsNotNull() {
            addCriterion("IDDOCUMENTO is not null");
            return (Criteria) this;
        }

        public Criteria andIddocumentoEqualTo(Integer value) {
            addCriterion("IDDOCUMENTO =", value, "iddocumento");
            return (Criteria) this;
        }

        public Criteria andIddocumentoNotEqualTo(Integer value) {
            addCriterion("IDDOCUMENTO <>", value, "iddocumento");
            return (Criteria) this;
        }

        public Criteria andIddocumentoGreaterThan(Integer value) {
            addCriterion("IDDOCUMENTO >", value, "iddocumento");
            return (Criteria) this;
        }

        public Criteria andIddocumentoGreaterThanOrEqualTo(Integer value) {
            addCriterion("IDDOCUMENTO >=", value, "iddocumento");
            return (Criteria) this;
        }

        public Criteria andIddocumentoLessThan(Integer value) {
            addCriterion("IDDOCUMENTO <", value, "iddocumento");
            return (Criteria) this;
        }

        public Criteria andIddocumentoLessThanOrEqualTo(Integer value) {
            addCriterion("IDDOCUMENTO <=", value, "iddocumento");
            return (Criteria) this;
        }

        public Criteria andIddocumentoIn(List<Integer> values) {
            addCriterion("IDDOCUMENTO in", values, "iddocumento");
            return (Criteria) this;
        }

        public Criteria andIddocumentoNotIn(List<Integer> values) {
            addCriterion("IDDOCUMENTO not in", values, "iddocumento");
            return (Criteria) this;
        }

        public Criteria andIddocumentoBetween(Integer value1, Integer value2) {
            addCriterion("IDDOCUMENTO between", value1, value2, "iddocumento");
            return (Criteria) this;
        }

        public Criteria andIddocumentoNotBetween(Integer value1, Integer value2) {
            addCriterion("IDDOCUMENTO not between", value1, value2, "iddocumento");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE_INT.ENV_COMUNICACIONMOROSOS
     *
     * @mbg.generated do_not_delete_during_merge Thu Dec 02 10:26:18 CET 2021
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE_INT.ENV_COMUNICACIONMOROSOS
     *
     * @mbg.generated Thu Dec 02 10:26:18 CET 2021
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