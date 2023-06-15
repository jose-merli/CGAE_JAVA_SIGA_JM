package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PcajgAlcActErrorCamExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE_INT.PCAJG_ALC_ACT_ERROR_CAM
     *
     * @mbg.generated Mon May 16 12:21:15 CEST 2022
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE_INT.PCAJG_ALC_ACT_ERROR_CAM
     *
     * @mbg.generated Mon May 16 12:21:15 CEST 2022
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table USCGAE_INT.PCAJG_ALC_ACT_ERROR_CAM
     *
     * @mbg.generated Mon May 16 12:21:15 CEST 2022
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.PCAJG_ALC_ACT_ERROR_CAM
     *
     * @mbg.generated Mon May 16 12:21:15 CEST 2022
     */
    public PcajgAlcActErrorCamExample() {
        oredCriteria = new ArrayList<>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.PCAJG_ALC_ACT_ERROR_CAM
     *
     * @mbg.generated Mon May 16 12:21:15 CEST 2022
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.PCAJG_ALC_ACT_ERROR_CAM
     *
     * @mbg.generated Mon May 16 12:21:15 CEST 2022
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.PCAJG_ALC_ACT_ERROR_CAM
     *
     * @mbg.generated Mon May 16 12:21:15 CEST 2022
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.PCAJG_ALC_ACT_ERROR_CAM
     *
     * @mbg.generated Mon May 16 12:21:15 CEST 2022
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.PCAJG_ALC_ACT_ERROR_CAM
     *
     * @mbg.generated Mon May 16 12:21:15 CEST 2022
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.PCAJG_ALC_ACT_ERROR_CAM
     *
     * @mbg.generated Mon May 16 12:21:15 CEST 2022
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.PCAJG_ALC_ACT_ERROR_CAM
     *
     * @mbg.generated Mon May 16 12:21:15 CEST 2022
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.PCAJG_ALC_ACT_ERROR_CAM
     *
     * @mbg.generated Mon May 16 12:21:15 CEST 2022
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
     * This method corresponds to the database table USCGAE_INT.PCAJG_ALC_ACT_ERROR_CAM
     *
     * @mbg.generated Mon May 16 12:21:15 CEST 2022
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USCGAE_INT.PCAJG_ALC_ACT_ERROR_CAM
     *
     * @mbg.generated Mon May 16 12:21:15 CEST 2022
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE_INT.PCAJG_ALC_ACT_ERROR_CAM
     *
     * @mbg.generated Mon May 16 12:21:15 CEST 2022
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

        public Criteria andIdentificadorIsNull() {
            addCriterion("IDENTIFICADOR is null");
            return (Criteria) this;
        }

        public Criteria andIdentificadorIsNotNull() {
            addCriterion("IDENTIFICADOR is not null");
            return (Criteria) this;
        }

        public Criteria andIdentificadorEqualTo(Long value) {
            addCriterion("IDENTIFICADOR =", value, "identificador");
            return (Criteria) this;
        }

        public Criteria andIdentificadorNotEqualTo(Long value) {
            addCriterion("IDENTIFICADOR <>", value, "identificador");
            return (Criteria) this;
        }

        public Criteria andIdentificadorGreaterThan(Long value) {
            addCriterion("IDENTIFICADOR >", value, "identificador");
            return (Criteria) this;
        }

        public Criteria andIdentificadorGreaterThanOrEqualTo(Long value) {
            addCriterion("IDENTIFICADOR >=", value, "identificador");
            return (Criteria) this;
        }

        public Criteria andIdentificadorLessThan(Long value) {
            addCriterion("IDENTIFICADOR <", value, "identificador");
            return (Criteria) this;
        }

        public Criteria andIdentificadorLessThanOrEqualTo(Long value) {
            addCriterion("IDENTIFICADOR <=", value, "identificador");
            return (Criteria) this;
        }

        public Criteria andIdentificadorIn(List<Long> values) {
            addCriterion("IDENTIFICADOR in", values, "identificador");
            return (Criteria) this;
        }

        public Criteria andIdentificadorNotIn(List<Long> values) {
            addCriterion("IDENTIFICADOR not in", values, "identificador");
            return (Criteria) this;
        }

        public Criteria andIdentificadorBetween(Long value1, Long value2) {
            addCriterion("IDENTIFICADOR between", value1, value2, "identificador");
            return (Criteria) this;
        }

        public Criteria andIdentificadorNotBetween(Long value1, Long value2) {
            addCriterion("IDENTIFICADOR not between", value1, value2, "identificador");
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

        public Criteria andIdfacturacionIsNull() {
            addCriterion("IDFACTURACION is null");
            return (Criteria) this;
        }

        public Criteria andIdfacturacionIsNotNull() {
            addCriterion("IDFACTURACION is not null");
            return (Criteria) this;
        }

        public Criteria andIdfacturacionEqualTo(Integer value) {
            addCriterion("IDFACTURACION =", value, "idfacturacion");
            return (Criteria) this;
        }

        public Criteria andIdfacturacionNotEqualTo(Integer value) {
            addCriterion("IDFACTURACION <>", value, "idfacturacion");
            return (Criteria) this;
        }

        public Criteria andIdfacturacionGreaterThan(Integer value) {
            addCriterion("IDFACTURACION >", value, "idfacturacion");
            return (Criteria) this;
        }

        public Criteria andIdfacturacionGreaterThanOrEqualTo(Integer value) {
            addCriterion("IDFACTURACION >=", value, "idfacturacion");
            return (Criteria) this;
        }

        public Criteria andIdfacturacionLessThan(Integer value) {
            addCriterion("IDFACTURACION <", value, "idfacturacion");
            return (Criteria) this;
        }

        public Criteria andIdfacturacionLessThanOrEqualTo(Integer value) {
            addCriterion("IDFACTURACION <=", value, "idfacturacion");
            return (Criteria) this;
        }

        public Criteria andIdfacturacionIn(List<Integer> values) {
            addCriterion("IDFACTURACION in", values, "idfacturacion");
            return (Criteria) this;
        }

        public Criteria andIdfacturacionNotIn(List<Integer> values) {
            addCriterion("IDFACTURACION not in", values, "idfacturacion");
            return (Criteria) this;
        }

        public Criteria andIdfacturacionBetween(Integer value1, Integer value2) {
            addCriterion("IDFACTURACION between", value1, value2, "idfacturacion");
            return (Criteria) this;
        }

        public Criteria andIdfacturacionNotBetween(Integer value1, Integer value2) {
            addCriterion("IDFACTURACION not between", value1, value2, "idfacturacion");
            return (Criteria) this;
        }

        public Criteria andRegistroErrorCamIsNull() {
            addCriterion("REGISTRO_ERROR_CAM is null");
            return (Criteria) this;
        }

        public Criteria andRegistroErrorCamIsNotNull() {
            addCriterion("REGISTRO_ERROR_CAM is not null");
            return (Criteria) this;
        }

        public Criteria andRegistroErrorCamEqualTo(String value) {
            addCriterion("REGISTRO_ERROR_CAM =", value, "registroErrorCam");
            return (Criteria) this;
        }

        public Criteria andRegistroErrorCamNotEqualTo(String value) {
            addCriterion("REGISTRO_ERROR_CAM <>", value, "registroErrorCam");
            return (Criteria) this;
        }

        public Criteria andRegistroErrorCamGreaterThan(String value) {
            addCriterion("REGISTRO_ERROR_CAM >", value, "registroErrorCam");
            return (Criteria) this;
        }

        public Criteria andRegistroErrorCamGreaterThanOrEqualTo(String value) {
            addCriterion("REGISTRO_ERROR_CAM >=", value, "registroErrorCam");
            return (Criteria) this;
        }

        public Criteria andRegistroErrorCamLessThan(String value) {
            addCriterion("REGISTRO_ERROR_CAM <", value, "registroErrorCam");
            return (Criteria) this;
        }

        public Criteria andRegistroErrorCamLessThanOrEqualTo(String value) {
            addCriterion("REGISTRO_ERROR_CAM <=", value, "registroErrorCam");
            return (Criteria) this;
        }

        public Criteria andRegistroErrorCamLike(String value) {
            addCriterion("REGISTRO_ERROR_CAM like", value, "registroErrorCam");
            return (Criteria) this;
        }

        public Criteria andRegistroErrorCamNotLike(String value) {
            addCriterion("REGISTRO_ERROR_CAM not like", value, "registroErrorCam");
            return (Criteria) this;
        }

        public Criteria andRegistroErrorCamIn(List<String> values) {
            addCriterion("REGISTRO_ERROR_CAM in", values, "registroErrorCam");
            return (Criteria) this;
        }

        public Criteria andRegistroErrorCamNotIn(List<String> values) {
            addCriterion("REGISTRO_ERROR_CAM not in", values, "registroErrorCam");
            return (Criteria) this;
        }

        public Criteria andRegistroErrorCamBetween(String value1, String value2) {
            addCriterion("REGISTRO_ERROR_CAM between", value1, value2, "registroErrorCam");
            return (Criteria) this;
        }

        public Criteria andRegistroErrorCamNotBetween(String value1, String value2) {
            addCriterion("REGISTRO_ERROR_CAM not between", value1, value2, "registroErrorCam");
            return (Criteria) this;
        }

        public Criteria andCodigoErrorIsNull() {
            addCriterion("CODIGO_ERROR is null");
            return (Criteria) this;
        }

        public Criteria andCodigoErrorIsNotNull() {
            addCriterion("CODIGO_ERROR is not null");
            return (Criteria) this;
        }

        public Criteria andCodigoErrorEqualTo(String value) {
            addCriterion("CODIGO_ERROR =", value, "codigoError");
            return (Criteria) this;
        }

        public Criteria andCodigoErrorNotEqualTo(String value) {
            addCriterion("CODIGO_ERROR <>", value, "codigoError");
            return (Criteria) this;
        }

        public Criteria andCodigoErrorGreaterThan(String value) {
            addCriterion("CODIGO_ERROR >", value, "codigoError");
            return (Criteria) this;
        }

        public Criteria andCodigoErrorGreaterThanOrEqualTo(String value) {
            addCriterion("CODIGO_ERROR >=", value, "codigoError");
            return (Criteria) this;
        }

        public Criteria andCodigoErrorLessThan(String value) {
            addCriterion("CODIGO_ERROR <", value, "codigoError");
            return (Criteria) this;
        }

        public Criteria andCodigoErrorLessThanOrEqualTo(String value) {
            addCriterion("CODIGO_ERROR <=", value, "codigoError");
            return (Criteria) this;
        }

        public Criteria andCodigoErrorLike(String value) {
            addCriterion("CODIGO_ERROR like", value, "codigoError");
            return (Criteria) this;
        }

        public Criteria andCodigoErrorNotLike(String value) {
            addCriterion("CODIGO_ERROR not like", value, "codigoError");
            return (Criteria) this;
        }

        public Criteria andCodigoErrorIn(List<String> values) {
            addCriterion("CODIGO_ERROR in", values, "codigoError");
            return (Criteria) this;
        }

        public Criteria andCodigoErrorNotIn(List<String> values) {
            addCriterion("CODIGO_ERROR not in", values, "codigoError");
            return (Criteria) this;
        }

        public Criteria andCodigoErrorBetween(String value1, String value2) {
            addCriterion("CODIGO_ERROR between", value1, value2, "codigoError");
            return (Criteria) this;
        }

        public Criteria andCodigoErrorNotBetween(String value1, String value2) {
            addCriterion("CODIGO_ERROR not between", value1, value2, "codigoError");
            return (Criteria) this;
        }

        public Criteria andCodigoCampoErrorIsNull() {
            addCriterion("CODIGO_CAMPO_ERROR is null");
            return (Criteria) this;
        }

        public Criteria andCodigoCampoErrorIsNotNull() {
            addCriterion("CODIGO_CAMPO_ERROR is not null");
            return (Criteria) this;
        }

        public Criteria andCodigoCampoErrorEqualTo(String value) {
            addCriterion("CODIGO_CAMPO_ERROR =", value, "codigoCampoError");
            return (Criteria) this;
        }

        public Criteria andCodigoCampoErrorNotEqualTo(String value) {
            addCriterion("CODIGO_CAMPO_ERROR <>", value, "codigoCampoError");
            return (Criteria) this;
        }

        public Criteria andCodigoCampoErrorGreaterThan(String value) {
            addCriterion("CODIGO_CAMPO_ERROR >", value, "codigoCampoError");
            return (Criteria) this;
        }

        public Criteria andCodigoCampoErrorGreaterThanOrEqualTo(String value) {
            addCriterion("CODIGO_CAMPO_ERROR >=", value, "codigoCampoError");
            return (Criteria) this;
        }

        public Criteria andCodigoCampoErrorLessThan(String value) {
            addCriterion("CODIGO_CAMPO_ERROR <", value, "codigoCampoError");
            return (Criteria) this;
        }

        public Criteria andCodigoCampoErrorLessThanOrEqualTo(String value) {
            addCriterion("CODIGO_CAMPO_ERROR <=", value, "codigoCampoError");
            return (Criteria) this;
        }

        public Criteria andCodigoCampoErrorLike(String value) {
            addCriterion("CODIGO_CAMPO_ERROR like", value, "codigoCampoError");
            return (Criteria) this;
        }

        public Criteria andCodigoCampoErrorNotLike(String value) {
            addCriterion("CODIGO_CAMPO_ERROR not like", value, "codigoCampoError");
            return (Criteria) this;
        }

        public Criteria andCodigoCampoErrorIn(List<String> values) {
            addCriterion("CODIGO_CAMPO_ERROR in", values, "codigoCampoError");
            return (Criteria) this;
        }

        public Criteria andCodigoCampoErrorNotIn(List<String> values) {
            addCriterion("CODIGO_CAMPO_ERROR not in", values, "codigoCampoError");
            return (Criteria) this;
        }

        public Criteria andCodigoCampoErrorBetween(String value1, String value2) {
            addCriterion("CODIGO_CAMPO_ERROR between", value1, value2, "codigoCampoError");
            return (Criteria) this;
        }

        public Criteria andCodigoCampoErrorNotBetween(String value1, String value2) {
            addCriterion("CODIGO_CAMPO_ERROR not between", value1, value2, "codigoCampoError");
            return (Criteria) this;
        }

        public Criteria andObservacionesErrorIsNull() {
            addCriterion("OBSERVACIONES_ERROR is null");
            return (Criteria) this;
        }

        public Criteria andObservacionesErrorIsNotNull() {
            addCriterion("OBSERVACIONES_ERROR is not null");
            return (Criteria) this;
        }

        public Criteria andObservacionesErrorEqualTo(String value) {
            addCriterion("OBSERVACIONES_ERROR =", value, "observacionesError");
            return (Criteria) this;
        }

        public Criteria andObservacionesErrorNotEqualTo(String value) {
            addCriterion("OBSERVACIONES_ERROR <>", value, "observacionesError");
            return (Criteria) this;
        }

        public Criteria andObservacionesErrorGreaterThan(String value) {
            addCriterion("OBSERVACIONES_ERROR >", value, "observacionesError");
            return (Criteria) this;
        }

        public Criteria andObservacionesErrorGreaterThanOrEqualTo(String value) {
            addCriterion("OBSERVACIONES_ERROR >=", value, "observacionesError");
            return (Criteria) this;
        }

        public Criteria andObservacionesErrorLessThan(String value) {
            addCriterion("OBSERVACIONES_ERROR <", value, "observacionesError");
            return (Criteria) this;
        }

        public Criteria andObservacionesErrorLessThanOrEqualTo(String value) {
            addCriterion("OBSERVACIONES_ERROR <=", value, "observacionesError");
            return (Criteria) this;
        }

        public Criteria andObservacionesErrorLike(String value) {
            addCriterion("OBSERVACIONES_ERROR like", value, "observacionesError");
            return (Criteria) this;
        }

        public Criteria andObservacionesErrorNotLike(String value) {
            addCriterion("OBSERVACIONES_ERROR not like", value, "observacionesError");
            return (Criteria) this;
        }

        public Criteria andObservacionesErrorIn(List<String> values) {
            addCriterion("OBSERVACIONES_ERROR in", values, "observacionesError");
            return (Criteria) this;
        }

        public Criteria andObservacionesErrorNotIn(List<String> values) {
            addCriterion("OBSERVACIONES_ERROR not in", values, "observacionesError");
            return (Criteria) this;
        }

        public Criteria andObservacionesErrorBetween(String value1, String value2) {
            addCriterion("OBSERVACIONES_ERROR between", value1, value2, "observacionesError");
            return (Criteria) this;
        }

        public Criteria andObservacionesErrorNotBetween(String value1, String value2) {
            addCriterion("OBSERVACIONES_ERROR not between", value1, value2, "observacionesError");
            return (Criteria) this;
        }

        public Criteria andEjgAnioIsNull() {
            addCriterion("EJG_ANIO is null");
            return (Criteria) this;
        }

        public Criteria andEjgAnioIsNotNull() {
            addCriterion("EJG_ANIO is not null");
            return (Criteria) this;
        }

        public Criteria andEjgAnioEqualTo(String value) {
            addCriterion("EJG_ANIO =", value, "ejgAnio");
            return (Criteria) this;
        }

        public Criteria andEjgAnioNotEqualTo(String value) {
            addCriterion("EJG_ANIO <>", value, "ejgAnio");
            return (Criteria) this;
        }

        public Criteria andEjgAnioGreaterThan(String value) {
            addCriterion("EJG_ANIO >", value, "ejgAnio");
            return (Criteria) this;
        }

        public Criteria andEjgAnioGreaterThanOrEqualTo(String value) {
            addCriterion("EJG_ANIO >=", value, "ejgAnio");
            return (Criteria) this;
        }

        public Criteria andEjgAnioLessThan(String value) {
            addCriterion("EJG_ANIO <", value, "ejgAnio");
            return (Criteria) this;
        }

        public Criteria andEjgAnioLessThanOrEqualTo(String value) {
            addCriterion("EJG_ANIO <=", value, "ejgAnio");
            return (Criteria) this;
        }

        public Criteria andEjgAnioLike(String value) {
            addCriterion("EJG_ANIO like", value, "ejgAnio");
            return (Criteria) this;
        }

        public Criteria andEjgAnioNotLike(String value) {
            addCriterion("EJG_ANIO not like", value, "ejgAnio");
            return (Criteria) this;
        }

        public Criteria andEjgAnioIn(List<String> values) {
            addCriterion("EJG_ANIO in", values, "ejgAnio");
            return (Criteria) this;
        }

        public Criteria andEjgAnioNotIn(List<String> values) {
            addCriterion("EJG_ANIO not in", values, "ejgAnio");
            return (Criteria) this;
        }

        public Criteria andEjgAnioBetween(String value1, String value2) {
            addCriterion("EJG_ANIO between", value1, value2, "ejgAnio");
            return (Criteria) this;
        }

        public Criteria andEjgAnioNotBetween(String value1, String value2) {
            addCriterion("EJG_ANIO not between", value1, value2, "ejgAnio");
            return (Criteria) this;
        }

        public Criteria andEjgNumejgIsNull() {
            addCriterion("EJG_NUMEJG is null");
            return (Criteria) this;
        }

        public Criteria andEjgNumejgIsNotNull() {
            addCriterion("EJG_NUMEJG is not null");
            return (Criteria) this;
        }

        public Criteria andEjgNumejgEqualTo(String value) {
            addCriterion("EJG_NUMEJG =", value, "ejgNumejg");
            return (Criteria) this;
        }

        public Criteria andEjgNumejgNotEqualTo(String value) {
            addCriterion("EJG_NUMEJG <>", value, "ejgNumejg");
            return (Criteria) this;
        }

        public Criteria andEjgNumejgGreaterThan(String value) {
            addCriterion("EJG_NUMEJG >", value, "ejgNumejg");
            return (Criteria) this;
        }

        public Criteria andEjgNumejgGreaterThanOrEqualTo(String value) {
            addCriterion("EJG_NUMEJG >=", value, "ejgNumejg");
            return (Criteria) this;
        }

        public Criteria andEjgNumejgLessThan(String value) {
            addCriterion("EJG_NUMEJG <", value, "ejgNumejg");
            return (Criteria) this;
        }

        public Criteria andEjgNumejgLessThanOrEqualTo(String value) {
            addCriterion("EJG_NUMEJG <=", value, "ejgNumejg");
            return (Criteria) this;
        }

        public Criteria andEjgNumejgLike(String value) {
            addCriterion("EJG_NUMEJG like", value, "ejgNumejg");
            return (Criteria) this;
        }

        public Criteria andEjgNumejgNotLike(String value) {
            addCriterion("EJG_NUMEJG not like", value, "ejgNumejg");
            return (Criteria) this;
        }

        public Criteria andEjgNumejgIn(List<String> values) {
            addCriterion("EJG_NUMEJG in", values, "ejgNumejg");
            return (Criteria) this;
        }

        public Criteria andEjgNumejgNotIn(List<String> values) {
            addCriterion("EJG_NUMEJG not in", values, "ejgNumejg");
            return (Criteria) this;
        }

        public Criteria andEjgNumejgBetween(String value1, String value2) {
            addCriterion("EJG_NUMEJG between", value1, value2, "ejgNumejg");
            return (Criteria) this;
        }

        public Criteria andEjgNumejgNotBetween(String value1, String value2) {
            addCriterion("EJG_NUMEJG not between", value1, value2, "ejgNumejg");
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

        public Criteria andBorradoEqualTo(Short value) {
            addCriterion("BORRADO =", value, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoNotEqualTo(Short value) {
            addCriterion("BORRADO <>", value, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoGreaterThan(Short value) {
            addCriterion("BORRADO >", value, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoGreaterThanOrEqualTo(Short value) {
            addCriterion("BORRADO >=", value, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoLessThan(Short value) {
            addCriterion("BORRADO <", value, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoLessThanOrEqualTo(Short value) {
            addCriterion("BORRADO <=", value, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoIn(List<Short> values) {
            addCriterion("BORRADO in", values, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoNotIn(List<Short> values) {
            addCriterion("BORRADO not in", values, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoBetween(Short value1, Short value2) {
            addCriterion("BORRADO between", value1, value2, "borrado");
            return (Criteria) this;
        }

        public Criteria andBorradoNotBetween(Short value1, Short value2) {
            addCriterion("BORRADO not between", value1, value2, "borrado");
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
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE_INT.PCAJG_ALC_ACT_ERROR_CAM
     *
     * @mbg.generated do_not_delete_during_merge Mon May 16 12:21:15 CEST 2022
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE_INT.PCAJG_ALC_ACT_ERROR_CAM
     *
     * @mbg.generated Mon May 16 12:21:15 CEST 2022
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