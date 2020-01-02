package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FcsFactGrupofactHitoExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE.FCS_FACT_GRUPOFACT_HITO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE.FCS_FACT_GRUPOFACT_HITO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE.FCS_FACT_GRUPOFACT_HITO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACT_GRUPOFACT_HITO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public FcsFactGrupofactHitoExample() {
		oredCriteria = new ArrayList<>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACT_GRUPOFACT_HITO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACT_GRUPOFACT_HITO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACT_GRUPOFACT_HITO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACT_GRUPOFACT_HITO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACT_GRUPOFACT_HITO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACT_GRUPOFACT_HITO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACT_GRUPOFACT_HITO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACT_GRUPOFACT_HITO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACT_GRUPOFACT_HITO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_FACT_GRUPOFACT_HITO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE.FCS_FACT_GRUPOFACT_HITO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
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

		public Criteria andIdgrupofacturacionIsNull() {
			addCriterion("IDGRUPOFACTURACION is null");
			return (Criteria) this;
		}

		public Criteria andIdgrupofacturacionIsNotNull() {
			addCriterion("IDGRUPOFACTURACION is not null");
			return (Criteria) this;
		}

		public Criteria andIdgrupofacturacionEqualTo(Short value) {
			addCriterion("IDGRUPOFACTURACION =", value, "idgrupofacturacion");
			return (Criteria) this;
		}

		public Criteria andIdgrupofacturacionNotEqualTo(Short value) {
			addCriterion("IDGRUPOFACTURACION <>", value, "idgrupofacturacion");
			return (Criteria) this;
		}

		public Criteria andIdgrupofacturacionGreaterThan(Short value) {
			addCriterion("IDGRUPOFACTURACION >", value, "idgrupofacturacion");
			return (Criteria) this;
		}

		public Criteria andIdgrupofacturacionGreaterThanOrEqualTo(Short value) {
			addCriterion("IDGRUPOFACTURACION >=", value, "idgrupofacturacion");
			return (Criteria) this;
		}

		public Criteria andIdgrupofacturacionLessThan(Short value) {
			addCriterion("IDGRUPOFACTURACION <", value, "idgrupofacturacion");
			return (Criteria) this;
		}

		public Criteria andIdgrupofacturacionLessThanOrEqualTo(Short value) {
			addCriterion("IDGRUPOFACTURACION <=", value, "idgrupofacturacion");
			return (Criteria) this;
		}

		public Criteria andIdgrupofacturacionIn(List<Short> values) {
			addCriterion("IDGRUPOFACTURACION in", values, "idgrupofacturacion");
			return (Criteria) this;
		}

		public Criteria andIdgrupofacturacionNotIn(List<Short> values) {
			addCriterion("IDGRUPOFACTURACION not in", values, "idgrupofacturacion");
			return (Criteria) this;
		}

		public Criteria andIdgrupofacturacionBetween(Short value1, Short value2) {
			addCriterion("IDGRUPOFACTURACION between", value1, value2, "idgrupofacturacion");
			return (Criteria) this;
		}

		public Criteria andIdgrupofacturacionNotBetween(Short value1, Short value2) {
			addCriterion("IDGRUPOFACTURACION not between", value1, value2, "idgrupofacturacion");
			return (Criteria) this;
		}

		public Criteria andIdhitogeneralIsNull() {
			addCriterion("IDHITOGENERAL is null");
			return (Criteria) this;
		}

		public Criteria andIdhitogeneralIsNotNull() {
			addCriterion("IDHITOGENERAL is not null");
			return (Criteria) this;
		}

		public Criteria andIdhitogeneralEqualTo(Short value) {
			addCriterion("IDHITOGENERAL =", value, "idhitogeneral");
			return (Criteria) this;
		}

		public Criteria andIdhitogeneralNotEqualTo(Short value) {
			addCriterion("IDHITOGENERAL <>", value, "idhitogeneral");
			return (Criteria) this;
		}

		public Criteria andIdhitogeneralGreaterThan(Short value) {
			addCriterion("IDHITOGENERAL >", value, "idhitogeneral");
			return (Criteria) this;
		}

		public Criteria andIdhitogeneralGreaterThanOrEqualTo(Short value) {
			addCriterion("IDHITOGENERAL >=", value, "idhitogeneral");
			return (Criteria) this;
		}

		public Criteria andIdhitogeneralLessThan(Short value) {
			addCriterion("IDHITOGENERAL <", value, "idhitogeneral");
			return (Criteria) this;
		}

		public Criteria andIdhitogeneralLessThanOrEqualTo(Short value) {
			addCriterion("IDHITOGENERAL <=", value, "idhitogeneral");
			return (Criteria) this;
		}

		public Criteria andIdhitogeneralIn(List<Short> values) {
			addCriterion("IDHITOGENERAL in", values, "idhitogeneral");
			return (Criteria) this;
		}

		public Criteria andIdhitogeneralNotIn(List<Short> values) {
			addCriterion("IDHITOGENERAL not in", values, "idhitogeneral");
			return (Criteria) this;
		}

		public Criteria andIdhitogeneralBetween(Short value1, Short value2) {
			addCriterion("IDHITOGENERAL between", value1, value2, "idhitogeneral");
			return (Criteria) this;
		}

		public Criteria andIdhitogeneralNotBetween(Short value1, Short value2) {
			addCriterion("IDHITOGENERAL not between", value1, value2, "idhitogeneral");
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

		public Criteria andFactconvenioIsNull() {
			addCriterion("FACTCONVENIO is null");
			return (Criteria) this;
		}

		public Criteria andFactconvenioIsNotNull() {
			addCriterion("FACTCONVENIO is not null");
			return (Criteria) this;
		}

		public Criteria andFactconvenioEqualTo(Short value) {
			addCriterion("FACTCONVENIO =", value, "factconvenio");
			return (Criteria) this;
		}

		public Criteria andFactconvenioNotEqualTo(Short value) {
			addCriterion("FACTCONVENIO <>", value, "factconvenio");
			return (Criteria) this;
		}

		public Criteria andFactconvenioGreaterThan(Short value) {
			addCriterion("FACTCONVENIO >", value, "factconvenio");
			return (Criteria) this;
		}

		public Criteria andFactconvenioGreaterThanOrEqualTo(Short value) {
			addCriterion("FACTCONVENIO >=", value, "factconvenio");
			return (Criteria) this;
		}

		public Criteria andFactconvenioLessThan(Short value) {
			addCriterion("FACTCONVENIO <", value, "factconvenio");
			return (Criteria) this;
		}

		public Criteria andFactconvenioLessThanOrEqualTo(Short value) {
			addCriterion("FACTCONVENIO <=", value, "factconvenio");
			return (Criteria) this;
		}

		public Criteria andFactconvenioIn(List<Short> values) {
			addCriterion("FACTCONVENIO in", values, "factconvenio");
			return (Criteria) this;
		}

		public Criteria andFactconvenioNotIn(List<Short> values) {
			addCriterion("FACTCONVENIO not in", values, "factconvenio");
			return (Criteria) this;
		}

		public Criteria andFactconvenioBetween(Short value1, Short value2) {
			addCriterion("FACTCONVENIO between", value1, value2, "factconvenio");
			return (Criteria) this;
		}

		public Criteria andFactconvenioNotBetween(Short value1, Short value2) {
			addCriterion("FACTCONVENIO not between", value1, value2, "factconvenio");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE.FCS_FACT_GRUPOFACT_HITO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
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
     * This class corresponds to the database table USCGAE.FCS_FACT_GRUPOFACT_HITO
     *
     * @mbg.generated do_not_delete_during_merge Fri Dec 13 08:58:03 CET 2019
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}