package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CenColegioExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE_DESA.CEN_COLEGIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE_DESA.CEN_COLEGIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE_DESA.CEN_COLEGIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLEGIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public CenColegioExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLEGIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLEGIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLEGIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLEGIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLEGIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLEGIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLEGIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLEGIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLEGIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_COLEGIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE_DESA.CEN_COLEGIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
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

		public Criteria andDecanoIsNull() {
			addCriterion("DECANO is null");
			return (Criteria) this;
		}

		public Criteria andDecanoIsNotNull() {
			addCriterion("DECANO is not null");
			return (Criteria) this;
		}

		public Criteria andDecanoEqualTo(Long value) {
			addCriterion("DECANO =", value, "decano");
			return (Criteria) this;
		}

		public Criteria andDecanoNotEqualTo(Long value) {
			addCriterion("DECANO <>", value, "decano");
			return (Criteria) this;
		}

		public Criteria andDecanoGreaterThan(Long value) {
			addCriterion("DECANO >", value, "decano");
			return (Criteria) this;
		}

		public Criteria andDecanoGreaterThanOrEqualTo(Long value) {
			addCriterion("DECANO >=", value, "decano");
			return (Criteria) this;
		}

		public Criteria andDecanoLessThan(Long value) {
			addCriterion("DECANO <", value, "decano");
			return (Criteria) this;
		}

		public Criteria andDecanoLessThanOrEqualTo(Long value) {
			addCriterion("DECANO <=", value, "decano");
			return (Criteria) this;
		}

		public Criteria andDecanoIn(List<Long> values) {
			addCriterion("DECANO in", values, "decano");
			return (Criteria) this;
		}

		public Criteria andDecanoNotIn(List<Long> values) {
			addCriterion("DECANO not in", values, "decano");
			return (Criteria) this;
		}

		public Criteria andDecanoBetween(Long value1, Long value2) {
			addCriterion("DECANO between", value1, value2, "decano");
			return (Criteria) this;
		}

		public Criteria andDecanoNotBetween(Long value1, Long value2) {
			addCriterion("DECANO not between", value1, value2, "decano");
			return (Criteria) this;
		}

		public Criteria andPeriodomandatoIsNull() {
			addCriterion("PERIODOMANDATO is null");
			return (Criteria) this;
		}

		public Criteria andPeriodomandatoIsNotNull() {
			addCriterion("PERIODOMANDATO is not null");
			return (Criteria) this;
		}

		public Criteria andPeriodomandatoEqualTo(Short value) {
			addCriterion("PERIODOMANDATO =", value, "periodomandato");
			return (Criteria) this;
		}

		public Criteria andPeriodomandatoNotEqualTo(Short value) {
			addCriterion("PERIODOMANDATO <>", value, "periodomandato");
			return (Criteria) this;
		}

		public Criteria andPeriodomandatoGreaterThan(Short value) {
			addCriterion("PERIODOMANDATO >", value, "periodomandato");
			return (Criteria) this;
		}

		public Criteria andPeriodomandatoGreaterThanOrEqualTo(Short value) {
			addCriterion("PERIODOMANDATO >=", value, "periodomandato");
			return (Criteria) this;
		}

		public Criteria andPeriodomandatoLessThan(Short value) {
			addCriterion("PERIODOMANDATO <", value, "periodomandato");
			return (Criteria) this;
		}

		public Criteria andPeriodomandatoLessThanOrEqualTo(Short value) {
			addCriterion("PERIODOMANDATO <=", value, "periodomandato");
			return (Criteria) this;
		}

		public Criteria andPeriodomandatoIn(List<Short> values) {
			addCriterion("PERIODOMANDATO in", values, "periodomandato");
			return (Criteria) this;
		}

		public Criteria andPeriodomandatoNotIn(List<Short> values) {
			addCriterion("PERIODOMANDATO not in", values, "periodomandato");
			return (Criteria) this;
		}

		public Criteria andPeriodomandatoBetween(Short value1, Short value2) {
			addCriterion("PERIODOMANDATO between", value1, value2, "periodomandato");
			return (Criteria) this;
		}

		public Criteria andPeriodomandatoNotBetween(Short value1, Short value2) {
			addCriterion("PERIODOMANDATO not between", value1, value2, "periodomandato");
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

		public Criteria andSecretarioIsNull() {
			addCriterion("SECRETARIO is null");
			return (Criteria) this;
		}

		public Criteria andSecretarioIsNotNull() {
			addCriterion("SECRETARIO is not null");
			return (Criteria) this;
		}

		public Criteria andSecretarioEqualTo(String value) {
			addCriterion("SECRETARIO =", value, "secretario");
			return (Criteria) this;
		}

		public Criteria andSecretarioNotEqualTo(String value) {
			addCriterion("SECRETARIO <>", value, "secretario");
			return (Criteria) this;
		}

		public Criteria andSecretarioGreaterThan(String value) {
			addCriterion("SECRETARIO >", value, "secretario");
			return (Criteria) this;
		}

		public Criteria andSecretarioGreaterThanOrEqualTo(String value) {
			addCriterion("SECRETARIO >=", value, "secretario");
			return (Criteria) this;
		}

		public Criteria andSecretarioLessThan(String value) {
			addCriterion("SECRETARIO <", value, "secretario");
			return (Criteria) this;
		}

		public Criteria andSecretarioLessThanOrEqualTo(String value) {
			addCriterion("SECRETARIO <=", value, "secretario");
			return (Criteria) this;
		}

		public Criteria andSecretarioLike(String value) {
			addCriterion("SECRETARIO like", value, "secretario");
			return (Criteria) this;
		}

		public Criteria andSecretarioNotLike(String value) {
			addCriterion("SECRETARIO not like", value, "secretario");
			return (Criteria) this;
		}

		public Criteria andSecretarioIn(List<String> values) {
			addCriterion("SECRETARIO in", values, "secretario");
			return (Criteria) this;
		}

		public Criteria andSecretarioNotIn(List<String> values) {
			addCriterion("SECRETARIO not in", values, "secretario");
			return (Criteria) this;
		}

		public Criteria andSecretarioBetween(String value1, String value2) {
			addCriterion("SECRETARIO between", value1, value2, "secretario");
			return (Criteria) this;
		}

		public Criteria andSecretarioNotBetween(String value1, String value2) {
			addCriterion("SECRETARIO not between", value1, value2, "secretario");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE_DESA.CEN_COLEGIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
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

	/**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table USCGAE_DESA.CEN_COLEGIO
     *
     * @mbg.generated do_not_delete_during_merge Wed Mar 07 17:49:31 CET 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}