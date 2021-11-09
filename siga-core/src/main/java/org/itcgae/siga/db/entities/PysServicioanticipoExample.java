package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PysServicioanticipoExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE.PYS_SERVICIOANTICIPO
	 * @mbg.generated  Fri Oct 29 10:57:06 CEST 2021
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE.PYS_SERVICIOANTICIPO
	 * @mbg.generated  Fri Oct 29 10:57:06 CEST 2021
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE.PYS_SERVICIOANTICIPO
	 * @mbg.generated  Fri Oct 29 10:57:06 CEST 2021
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.PYS_SERVICIOANTICIPO
	 * @mbg.generated  Fri Oct 29 10:57:06 CEST 2021
	 */
	public PysServicioanticipoExample() {
		oredCriteria = new ArrayList<>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.PYS_SERVICIOANTICIPO
	 * @mbg.generated  Fri Oct 29 10:57:06 CEST 2021
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.PYS_SERVICIOANTICIPO
	 * @mbg.generated  Fri Oct 29 10:57:06 CEST 2021
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.PYS_SERVICIOANTICIPO
	 * @mbg.generated  Fri Oct 29 10:57:06 CEST 2021
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.PYS_SERVICIOANTICIPO
	 * @mbg.generated  Fri Oct 29 10:57:06 CEST 2021
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.PYS_SERVICIOANTICIPO
	 * @mbg.generated  Fri Oct 29 10:57:06 CEST 2021
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.PYS_SERVICIOANTICIPO
	 * @mbg.generated  Fri Oct 29 10:57:06 CEST 2021
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.PYS_SERVICIOANTICIPO
	 * @mbg.generated  Fri Oct 29 10:57:06 CEST 2021
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.PYS_SERVICIOANTICIPO
	 * @mbg.generated  Fri Oct 29 10:57:06 CEST 2021
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.PYS_SERVICIOANTICIPO
	 * @mbg.generated  Fri Oct 29 10:57:06 CEST 2021
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.PYS_SERVICIOANTICIPO
	 * @mbg.generated  Fri Oct 29 10:57:06 CEST 2021
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE.PYS_SERVICIOANTICIPO
	 * @mbg.generated  Fri Oct 29 10:57:06 CEST 2021
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

		public Criteria andIdanticipoIsNull() {
			addCriterion("IDANTICIPO is null");
			return (Criteria) this;
		}

		public Criteria andIdanticipoIsNotNull() {
			addCriterion("IDANTICIPO is not null");
			return (Criteria) this;
		}

		public Criteria andIdanticipoEqualTo(Short value) {
			addCriterion("IDANTICIPO =", value, "idanticipo");
			return (Criteria) this;
		}

		public Criteria andIdanticipoNotEqualTo(Short value) {
			addCriterion("IDANTICIPO <>", value, "idanticipo");
			return (Criteria) this;
		}

		public Criteria andIdanticipoGreaterThan(Short value) {
			addCriterion("IDANTICIPO >", value, "idanticipo");
			return (Criteria) this;
		}

		public Criteria andIdanticipoGreaterThanOrEqualTo(Short value) {
			addCriterion("IDANTICIPO >=", value, "idanticipo");
			return (Criteria) this;
		}

		public Criteria andIdanticipoLessThan(Short value) {
			addCriterion("IDANTICIPO <", value, "idanticipo");
			return (Criteria) this;
		}

		public Criteria andIdanticipoLessThanOrEqualTo(Short value) {
			addCriterion("IDANTICIPO <=", value, "idanticipo");
			return (Criteria) this;
		}

		public Criteria andIdanticipoIn(List<Short> values) {
			addCriterion("IDANTICIPO in", values, "idanticipo");
			return (Criteria) this;
		}

		public Criteria andIdanticipoNotIn(List<Short> values) {
			addCriterion("IDANTICIPO not in", values, "idanticipo");
			return (Criteria) this;
		}

		public Criteria andIdanticipoBetween(Short value1, Short value2) {
			addCriterion("IDANTICIPO between", value1, value2, "idanticipo");
			return (Criteria) this;
		}

		public Criteria andIdanticipoNotBetween(Short value1, Short value2) {
			addCriterion("IDANTICIPO not between", value1, value2, "idanticipo");
			return (Criteria) this;
		}

		public Criteria andIdtiposerviciosIsNull() {
			addCriterion("IDTIPOSERVICIOS is null");
			return (Criteria) this;
		}

		public Criteria andIdtiposerviciosIsNotNull() {
			addCriterion("IDTIPOSERVICIOS is not null");
			return (Criteria) this;
		}

		public Criteria andIdtiposerviciosEqualTo(Short value) {
			addCriterion("IDTIPOSERVICIOS =", value, "idtiposervicios");
			return (Criteria) this;
		}

		public Criteria andIdtiposerviciosNotEqualTo(Short value) {
			addCriterion("IDTIPOSERVICIOS <>", value, "idtiposervicios");
			return (Criteria) this;
		}

		public Criteria andIdtiposerviciosGreaterThan(Short value) {
			addCriterion("IDTIPOSERVICIOS >", value, "idtiposervicios");
			return (Criteria) this;
		}

		public Criteria andIdtiposerviciosGreaterThanOrEqualTo(Short value) {
			addCriterion("IDTIPOSERVICIOS >=", value, "idtiposervicios");
			return (Criteria) this;
		}

		public Criteria andIdtiposerviciosLessThan(Short value) {
			addCriterion("IDTIPOSERVICIOS <", value, "idtiposervicios");
			return (Criteria) this;
		}

		public Criteria andIdtiposerviciosLessThanOrEqualTo(Short value) {
			addCriterion("IDTIPOSERVICIOS <=", value, "idtiposervicios");
			return (Criteria) this;
		}

		public Criteria andIdtiposerviciosIn(List<Short> values) {
			addCriterion("IDTIPOSERVICIOS in", values, "idtiposervicios");
			return (Criteria) this;
		}

		public Criteria andIdtiposerviciosNotIn(List<Short> values) {
			addCriterion("IDTIPOSERVICIOS not in", values, "idtiposervicios");
			return (Criteria) this;
		}

		public Criteria andIdtiposerviciosBetween(Short value1, Short value2) {
			addCriterion("IDTIPOSERVICIOS between", value1, value2, "idtiposervicios");
			return (Criteria) this;
		}

		public Criteria andIdtiposerviciosNotBetween(Short value1, Short value2) {
			addCriterion("IDTIPOSERVICIOS not between", value1, value2, "idtiposervicios");
			return (Criteria) this;
		}

		public Criteria andIdservicioIsNull() {
			addCriterion("IDSERVICIO is null");
			return (Criteria) this;
		}

		public Criteria andIdservicioIsNotNull() {
			addCriterion("IDSERVICIO is not null");
			return (Criteria) this;
		}

		public Criteria andIdservicioEqualTo(Long value) {
			addCriterion("IDSERVICIO =", value, "idservicio");
			return (Criteria) this;
		}

		public Criteria andIdservicioNotEqualTo(Long value) {
			addCriterion("IDSERVICIO <>", value, "idservicio");
			return (Criteria) this;
		}

		public Criteria andIdservicioGreaterThan(Long value) {
			addCriterion("IDSERVICIO >", value, "idservicio");
			return (Criteria) this;
		}

		public Criteria andIdservicioGreaterThanOrEqualTo(Long value) {
			addCriterion("IDSERVICIO >=", value, "idservicio");
			return (Criteria) this;
		}

		public Criteria andIdservicioLessThan(Long value) {
			addCriterion("IDSERVICIO <", value, "idservicio");
			return (Criteria) this;
		}

		public Criteria andIdservicioLessThanOrEqualTo(Long value) {
			addCriterion("IDSERVICIO <=", value, "idservicio");
			return (Criteria) this;
		}

		public Criteria andIdservicioIn(List<Long> values) {
			addCriterion("IDSERVICIO in", values, "idservicio");
			return (Criteria) this;
		}

		public Criteria andIdservicioNotIn(List<Long> values) {
			addCriterion("IDSERVICIO not in", values, "idservicio");
			return (Criteria) this;
		}

		public Criteria andIdservicioBetween(Long value1, Long value2) {
			addCriterion("IDSERVICIO between", value1, value2, "idservicio");
			return (Criteria) this;
		}

		public Criteria andIdservicioNotBetween(Long value1, Long value2) {
			addCriterion("IDSERVICIO not between", value1, value2, "idservicio");
			return (Criteria) this;
		}

		public Criteria andIdserviciosinstitucionIsNull() {
			addCriterion("IDSERVICIOSINSTITUCION is null");
			return (Criteria) this;
		}

		public Criteria andIdserviciosinstitucionIsNotNull() {
			addCriterion("IDSERVICIOSINSTITUCION is not null");
			return (Criteria) this;
		}

		public Criteria andIdserviciosinstitucionEqualTo(Long value) {
			addCriterion("IDSERVICIOSINSTITUCION =", value, "idserviciosinstitucion");
			return (Criteria) this;
		}

		public Criteria andIdserviciosinstitucionNotEqualTo(Long value) {
			addCriterion("IDSERVICIOSINSTITUCION <>", value, "idserviciosinstitucion");
			return (Criteria) this;
		}

		public Criteria andIdserviciosinstitucionGreaterThan(Long value) {
			addCriterion("IDSERVICIOSINSTITUCION >", value, "idserviciosinstitucion");
			return (Criteria) this;
		}

		public Criteria andIdserviciosinstitucionGreaterThanOrEqualTo(Long value) {
			addCriterion("IDSERVICIOSINSTITUCION >=", value, "idserviciosinstitucion");
			return (Criteria) this;
		}

		public Criteria andIdserviciosinstitucionLessThan(Long value) {
			addCriterion("IDSERVICIOSINSTITUCION <", value, "idserviciosinstitucion");
			return (Criteria) this;
		}

		public Criteria andIdserviciosinstitucionLessThanOrEqualTo(Long value) {
			addCriterion("IDSERVICIOSINSTITUCION <=", value, "idserviciosinstitucion");
			return (Criteria) this;
		}

		public Criteria andIdserviciosinstitucionIn(List<Long> values) {
			addCriterion("IDSERVICIOSINSTITUCION in", values, "idserviciosinstitucion");
			return (Criteria) this;
		}

		public Criteria andIdserviciosinstitucionNotIn(List<Long> values) {
			addCriterion("IDSERVICIOSINSTITUCION not in", values, "idserviciosinstitucion");
			return (Criteria) this;
		}

		public Criteria andIdserviciosinstitucionBetween(Long value1, Long value2) {
			addCriterion("IDSERVICIOSINSTITUCION between", value1, value2, "idserviciosinstitucion");
			return (Criteria) this;
		}

		public Criteria andIdserviciosinstitucionNotBetween(Long value1, Long value2) {
			addCriterion("IDSERVICIOSINSTITUCION not between", value1, value2, "idserviciosinstitucion");
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
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE.PYS_SERVICIOANTICIPO
	 * @mbg.generated  Fri Oct 29 10:57:06 CEST 2021
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
     * This class corresponds to the database table USCGAE.PYS_SERVICIOANTICIPO
     *
     * @mbg.generated do_not_delete_during_merge Fri Oct 29 10:55:36 CEST 2021
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }
}