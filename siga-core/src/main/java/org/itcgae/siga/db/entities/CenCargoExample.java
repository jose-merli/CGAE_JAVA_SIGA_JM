package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CenCargoExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE_DESA.CEN_CARGO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE_DESA.CEN_CARGO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE_DESA.CEN_CARGO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CARGO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public CenCargoExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CARGO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CARGO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CARGO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CARGO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CARGO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CARGO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CARGO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CARGO
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
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CARGO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CARGO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE_DESA.CEN_CARGO
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

		public Criteria andIdcargoIsNull() {
			addCriterion("IDCARGO is null");
			return (Criteria) this;
		}

		public Criteria andIdcargoIsNotNull() {
			addCriterion("IDCARGO is not null");
			return (Criteria) this;
		}

		public Criteria andIdcargoEqualTo(Short value) {
			addCriterion("IDCARGO =", value, "idcargo");
			return (Criteria) this;
		}

		public Criteria andIdcargoNotEqualTo(Short value) {
			addCriterion("IDCARGO <>", value, "idcargo");
			return (Criteria) this;
		}

		public Criteria andIdcargoGreaterThan(Short value) {
			addCriterion("IDCARGO >", value, "idcargo");
			return (Criteria) this;
		}

		public Criteria andIdcargoGreaterThanOrEqualTo(Short value) {
			addCriterion("IDCARGO >=", value, "idcargo");
			return (Criteria) this;
		}

		public Criteria andIdcargoLessThan(Short value) {
			addCriterion("IDCARGO <", value, "idcargo");
			return (Criteria) this;
		}

		public Criteria andIdcargoLessThanOrEqualTo(Short value) {
			addCriterion("IDCARGO <=", value, "idcargo");
			return (Criteria) this;
		}

		public Criteria andIdcargoIn(List<Short> values) {
			addCriterion("IDCARGO in", values, "idcargo");
			return (Criteria) this;
		}

		public Criteria andIdcargoNotIn(List<Short> values) {
			addCriterion("IDCARGO not in", values, "idcargo");
			return (Criteria) this;
		}

		public Criteria andIdcargoBetween(Short value1, Short value2) {
			addCriterion("IDCARGO between", value1, value2, "idcargo");
			return (Criteria) this;
		}

		public Criteria andIdcargoNotBetween(Short value1, Short value2) {
			addCriterion("IDCARGO not between", value1, value2, "idcargo");
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

		public Criteria andCodigoextIsNull() {
			addCriterion("CODIGOEXT is null");
			return (Criteria) this;
		}

		public Criteria andCodigoextIsNotNull() {
			addCriterion("CODIGOEXT is not null");
			return (Criteria) this;
		}

		public Criteria andCodigoextEqualTo(String value) {
			addCriterion("CODIGOEXT =", value, "codigoext");
			return (Criteria) this;
		}

		public Criteria andCodigoextNotEqualTo(String value) {
			addCriterion("CODIGOEXT <>", value, "codigoext");
			return (Criteria) this;
		}

		public Criteria andCodigoextGreaterThan(String value) {
			addCriterion("CODIGOEXT >", value, "codigoext");
			return (Criteria) this;
		}

		public Criteria andCodigoextGreaterThanOrEqualTo(String value) {
			addCriterion("CODIGOEXT >=", value, "codigoext");
			return (Criteria) this;
		}

		public Criteria andCodigoextLessThan(String value) {
			addCriterion("CODIGOEXT <", value, "codigoext");
			return (Criteria) this;
		}

		public Criteria andCodigoextLessThanOrEqualTo(String value) {
			addCriterion("CODIGOEXT <=", value, "codigoext");
			return (Criteria) this;
		}

		public Criteria andCodigoextLike(String value) {
			addCriterion("CODIGOEXT like", value, "codigoext");
			return (Criteria) this;
		}

		public Criteria andCodigoextNotLike(String value) {
			addCriterion("CODIGOEXT not like", value, "codigoext");
			return (Criteria) this;
		}

		public Criteria andCodigoextIn(List<String> values) {
			addCriterion("CODIGOEXT in", values, "codigoext");
			return (Criteria) this;
		}

		public Criteria andCodigoextNotIn(List<String> values) {
			addCriterion("CODIGOEXT not in", values, "codigoext");
			return (Criteria) this;
		}

		public Criteria andCodigoextBetween(String value1, String value2) {
			addCriterion("CODIGOEXT between", value1, value2, "codigoext");
			return (Criteria) this;
		}

		public Criteria andCodigoextNotBetween(String value1, String value2) {
			addCriterion("CODIGOEXT not between", value1, value2, "codigoext");
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

		public Criteria andBloqueadoIsNull() {
			addCriterion("BLOQUEADO is null");
			return (Criteria) this;
		}

		public Criteria andBloqueadoIsNotNull() {
			addCriterion("BLOQUEADO is not null");
			return (Criteria) this;
		}

		public Criteria andBloqueadoEqualTo(String value) {
			addCriterion("BLOQUEADO =", value, "bloqueado");
			return (Criteria) this;
		}

		public Criteria andBloqueadoNotEqualTo(String value) {
			addCriterion("BLOQUEADO <>", value, "bloqueado");
			return (Criteria) this;
		}

		public Criteria andBloqueadoGreaterThan(String value) {
			addCriterion("BLOQUEADO >", value, "bloqueado");
			return (Criteria) this;
		}

		public Criteria andBloqueadoGreaterThanOrEqualTo(String value) {
			addCriterion("BLOQUEADO >=", value, "bloqueado");
			return (Criteria) this;
		}

		public Criteria andBloqueadoLessThan(String value) {
			addCriterion("BLOQUEADO <", value, "bloqueado");
			return (Criteria) this;
		}

		public Criteria andBloqueadoLessThanOrEqualTo(String value) {
			addCriterion("BLOQUEADO <=", value, "bloqueado");
			return (Criteria) this;
		}

		public Criteria andBloqueadoLike(String value) {
			addCriterion("BLOQUEADO like", value, "bloqueado");
			return (Criteria) this;
		}

		public Criteria andBloqueadoNotLike(String value) {
			addCriterion("BLOQUEADO not like", value, "bloqueado");
			return (Criteria) this;
		}

		public Criteria andBloqueadoIn(List<String> values) {
			addCriterion("BLOQUEADO in", values, "bloqueado");
			return (Criteria) this;
		}

		public Criteria andBloqueadoNotIn(List<String> values) {
			addCriterion("BLOQUEADO not in", values, "bloqueado");
			return (Criteria) this;
		}

		public Criteria andBloqueadoBetween(String value1, String value2) {
			addCriterion("BLOQUEADO between", value1, value2, "bloqueado");
			return (Criteria) this;
		}

		public Criteria andBloqueadoNotBetween(String value1, String value2) {
			addCriterion("BLOQUEADO not between", value1, value2, "bloqueado");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE_DESA.CEN_CARGO
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
     * This class corresponds to the database table USCGAE_DESA.CEN_CARGO
     *
     * @mbg.generated do_not_delete_during_merge Wed Mar 07 17:49:31 CET 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}