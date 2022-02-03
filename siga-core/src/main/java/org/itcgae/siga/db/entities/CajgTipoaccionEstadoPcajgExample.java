package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CajgTipoaccionEstadoPcajgExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE.CAJG_TIPOACCION_ESTADO_PCAJG
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE.CAJG_TIPOACCION_ESTADO_PCAJG
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE.CAJG_TIPOACCION_ESTADO_PCAJG
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CAJG_TIPOACCION_ESTADO_PCAJG
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	public CajgTipoaccionEstadoPcajgExample() {
		oredCriteria = new ArrayList<>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CAJG_TIPOACCION_ESTADO_PCAJG
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CAJG_TIPOACCION_ESTADO_PCAJG
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CAJG_TIPOACCION_ESTADO_PCAJG
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CAJG_TIPOACCION_ESTADO_PCAJG
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CAJG_TIPOACCION_ESTADO_PCAJG
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CAJG_TIPOACCION_ESTADO_PCAJG
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CAJG_TIPOACCION_ESTADO_PCAJG
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CAJG_TIPOACCION_ESTADO_PCAJG
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CAJG_TIPOACCION_ESTADO_PCAJG
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.CAJG_TIPOACCION_ESTADO_PCAJG
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE.CAJG_TIPOACCION_ESTADO_PCAJG
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
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

		public Criteria andIdtipoaccionestcajgIsNull() {
			addCriterion("IDTIPOACCIONESTCAJG is null");
			return (Criteria) this;
		}

		public Criteria andIdtipoaccionestcajgIsNotNull() {
			addCriterion("IDTIPOACCIONESTCAJG is not null");
			return (Criteria) this;
		}

		public Criteria andIdtipoaccionestcajgEqualTo(Integer value) {
			addCriterion("IDTIPOACCIONESTCAJG =", value, "idtipoaccionestcajg");
			return (Criteria) this;
		}

		public Criteria andIdtipoaccionestcajgNotEqualTo(Integer value) {
			addCriterion("IDTIPOACCIONESTCAJG <>", value, "idtipoaccionestcajg");
			return (Criteria) this;
		}

		public Criteria andIdtipoaccionestcajgGreaterThan(Integer value) {
			addCriterion("IDTIPOACCIONESTCAJG >", value, "idtipoaccionestcajg");
			return (Criteria) this;
		}

		public Criteria andIdtipoaccionestcajgGreaterThanOrEqualTo(Integer value) {
			addCriterion("IDTIPOACCIONESTCAJG >=", value, "idtipoaccionestcajg");
			return (Criteria) this;
		}

		public Criteria andIdtipoaccionestcajgLessThan(Integer value) {
			addCriterion("IDTIPOACCIONESTCAJG <", value, "idtipoaccionestcajg");
			return (Criteria) this;
		}

		public Criteria andIdtipoaccionestcajgLessThanOrEqualTo(Integer value) {
			addCriterion("IDTIPOACCIONESTCAJG <=", value, "idtipoaccionestcajg");
			return (Criteria) this;
		}

		public Criteria andIdtipoaccionestcajgIn(List<Integer> values) {
			addCriterion("IDTIPOACCIONESTCAJG in", values, "idtipoaccionestcajg");
			return (Criteria) this;
		}

		public Criteria andIdtipoaccionestcajgNotIn(List<Integer> values) {
			addCriterion("IDTIPOACCIONESTCAJG not in", values, "idtipoaccionestcajg");
			return (Criteria) this;
		}

		public Criteria andIdtipoaccionestcajgBetween(Integer value1, Integer value2) {
			addCriterion("IDTIPOACCIONESTCAJG between", value1, value2, "idtipoaccionestcajg");
			return (Criteria) this;
		}

		public Criteria andIdtipoaccionestcajgNotBetween(Integer value1, Integer value2) {
			addCriterion("IDTIPOACCIONESTCAJG not between", value1, value2, "idtipoaccionestcajg");
			return (Criteria) this;
		}

		public Criteria andTipoPcajgIsNull() {
			addCriterion("TIPO_PCAJG is null");
			return (Criteria) this;
		}

		public Criteria andTipoPcajgIsNotNull() {
			addCriterion("TIPO_PCAJG is not null");
			return (Criteria) this;
		}

		public Criteria andTipoPcajgEqualTo(String value) {
			addCriterion("TIPO_PCAJG =", value, "tipoPcajg");
			return (Criteria) this;
		}

		public Criteria andTipoPcajgNotEqualTo(String value) {
			addCriterion("TIPO_PCAJG <>", value, "tipoPcajg");
			return (Criteria) this;
		}

		public Criteria andTipoPcajgGreaterThan(String value) {
			addCriterion("TIPO_PCAJG >", value, "tipoPcajg");
			return (Criteria) this;
		}

		public Criteria andTipoPcajgGreaterThanOrEqualTo(String value) {
			addCriterion("TIPO_PCAJG >=", value, "tipoPcajg");
			return (Criteria) this;
		}

		public Criteria andTipoPcajgLessThan(String value) {
			addCriterion("TIPO_PCAJG <", value, "tipoPcajg");
			return (Criteria) this;
		}

		public Criteria andTipoPcajgLessThanOrEqualTo(String value) {
			addCriterion("TIPO_PCAJG <=", value, "tipoPcajg");
			return (Criteria) this;
		}

		public Criteria andTipoPcajgLike(String value) {
			addCriterion("TIPO_PCAJG like", value, "tipoPcajg");
			return (Criteria) this;
		}

		public Criteria andTipoPcajgNotLike(String value) {
			addCriterion("TIPO_PCAJG not like", value, "tipoPcajg");
			return (Criteria) this;
		}

		public Criteria andTipoPcajgIn(List<String> values) {
			addCriterion("TIPO_PCAJG in", values, "tipoPcajg");
			return (Criteria) this;
		}

		public Criteria andTipoPcajgNotIn(List<String> values) {
			addCriterion("TIPO_PCAJG not in", values, "tipoPcajg");
			return (Criteria) this;
		}

		public Criteria andTipoPcajgBetween(String value1, String value2) {
			addCriterion("TIPO_PCAJG between", value1, value2, "tipoPcajg");
			return (Criteria) this;
		}

		public Criteria andTipoPcajgNotBetween(String value1, String value2) {
			addCriterion("TIPO_PCAJG not between", value1, value2, "tipoPcajg");
			return (Criteria) this;
		}

		public Criteria andIdestadoIsNull() {
			addCriterion("IDESTADO is null");
			return (Criteria) this;
		}

		public Criteria andIdestadoIsNotNull() {
			addCriterion("IDESTADO is not null");
			return (Criteria) this;
		}

		public Criteria andIdestadoEqualTo(Short value) {
			addCriterion("IDESTADO =", value, "idestado");
			return (Criteria) this;
		}

		public Criteria andIdestadoNotEqualTo(Short value) {
			addCriterion("IDESTADO <>", value, "idestado");
			return (Criteria) this;
		}

		public Criteria andIdestadoGreaterThan(Short value) {
			addCriterion("IDESTADO >", value, "idestado");
			return (Criteria) this;
		}

		public Criteria andIdestadoGreaterThanOrEqualTo(Short value) {
			addCriterion("IDESTADO >=", value, "idestado");
			return (Criteria) this;
		}

		public Criteria andIdestadoLessThan(Short value) {
			addCriterion("IDESTADO <", value, "idestado");
			return (Criteria) this;
		}

		public Criteria andIdestadoLessThanOrEqualTo(Short value) {
			addCriterion("IDESTADO <=", value, "idestado");
			return (Criteria) this;
		}

		public Criteria andIdestadoIn(List<Short> values) {
			addCriterion("IDESTADO in", values, "idestado");
			return (Criteria) this;
		}

		public Criteria andIdestadoNotIn(List<Short> values) {
			addCriterion("IDESTADO not in", values, "idestado");
			return (Criteria) this;
		}

		public Criteria andIdestadoBetween(Short value1, Short value2) {
			addCriterion("IDESTADO between", value1, value2, "idestado");
			return (Criteria) this;
		}

		public Criteria andIdestadoNotBetween(Short value1, Short value2) {
			addCriterion("IDESTADO not between", value1, value2, "idestado");
			return (Criteria) this;
		}

		public Criteria andIdtipoaccionremesaIsNull() {
			addCriterion("IDTIPOACCIONREMESA is null");
			return (Criteria) this;
		}

		public Criteria andIdtipoaccionremesaIsNotNull() {
			addCriterion("IDTIPOACCIONREMESA is not null");
			return (Criteria) this;
		}

		public Criteria andIdtipoaccionremesaEqualTo(Integer value) {
			addCriterion("IDTIPOACCIONREMESA =", value, "idtipoaccionremesa");
			return (Criteria) this;
		}

		public Criteria andIdtipoaccionremesaNotEqualTo(Integer value) {
			addCriterion("IDTIPOACCIONREMESA <>", value, "idtipoaccionremesa");
			return (Criteria) this;
		}

		public Criteria andIdtipoaccionremesaGreaterThan(Integer value) {
			addCriterion("IDTIPOACCIONREMESA >", value, "idtipoaccionremesa");
			return (Criteria) this;
		}

		public Criteria andIdtipoaccionremesaGreaterThanOrEqualTo(Integer value) {
			addCriterion("IDTIPOACCIONREMESA >=", value, "idtipoaccionremesa");
			return (Criteria) this;
		}

		public Criteria andIdtipoaccionremesaLessThan(Integer value) {
			addCriterion("IDTIPOACCIONREMESA <", value, "idtipoaccionremesa");
			return (Criteria) this;
		}

		public Criteria andIdtipoaccionremesaLessThanOrEqualTo(Integer value) {
			addCriterion("IDTIPOACCIONREMESA <=", value, "idtipoaccionremesa");
			return (Criteria) this;
		}

		public Criteria andIdtipoaccionremesaIn(List<Integer> values) {
			addCriterion("IDTIPOACCIONREMESA in", values, "idtipoaccionremesa");
			return (Criteria) this;
		}

		public Criteria andIdtipoaccionremesaNotIn(List<Integer> values) {
			addCriterion("IDTIPOACCIONREMESA not in", values, "idtipoaccionremesa");
			return (Criteria) this;
		}

		public Criteria andIdtipoaccionremesaBetween(Integer value1, Integer value2) {
			addCriterion("IDTIPOACCIONREMESA between", value1, value2, "idtipoaccionremesa");
			return (Criteria) this;
		}

		public Criteria andIdtipoaccionremesaNotBetween(Integer value1, Integer value2) {
			addCriterion("IDTIPOACCIONREMESA not between", value1, value2, "idtipoaccionremesa");
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
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE.CAJG_TIPOACCION_ESTADO_PCAJG
	 * @mbg.generated  Wed Sep 29 10:42:47 CEST 2021
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
     * This class corresponds to the database table USCGAE.CAJG_TIPOACCION_ESTADO_PCAJG
     *
     * @mbg.generated do_not_delete_during_merge Wed Sep 29 10:00:01 CEST 2021
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }
}