package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScsAreaExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE.SCS_AREA
	 * @mbg.generated  Tue Sep 03 13:29:45 CEST 2019
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE.SCS_AREA
	 * @mbg.generated  Tue Sep 03 13:29:45 CEST 2019
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE.SCS_AREA
	 * @mbg.generated  Tue Sep 03 13:29:45 CEST 2019
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_AREA
	 * @mbg.generated  Tue Sep 03 13:29:45 CEST 2019
	 */
	public ScsAreaExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_AREA
	 * @mbg.generated  Tue Sep 03 13:29:45 CEST 2019
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_AREA
	 * @mbg.generated  Tue Sep 03 13:29:45 CEST 2019
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_AREA
	 * @mbg.generated  Tue Sep 03 13:29:45 CEST 2019
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_AREA
	 * @mbg.generated  Tue Sep 03 13:29:45 CEST 2019
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_AREA
	 * @mbg.generated  Tue Sep 03 13:29:45 CEST 2019
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_AREA
	 * @mbg.generated  Tue Sep 03 13:29:45 CEST 2019
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_AREA
	 * @mbg.generated  Tue Sep 03 13:29:45 CEST 2019
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_AREA
	 * @mbg.generated  Tue Sep 03 13:29:45 CEST 2019
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_AREA
	 * @mbg.generated  Tue Sep 03 13:29:45 CEST 2019
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_AREA
	 * @mbg.generated  Tue Sep 03 13:29:45 CEST 2019
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE.SCS_AREA
	 * @mbg.generated  Tue Sep 03 13:29:45 CEST 2019
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

		public Criteria andIdareaIsNull() {
			addCriterion("IDAREA is null");
			return (Criteria) this;
		}

		public Criteria andIdareaIsNotNull() {
			addCriterion("IDAREA is not null");
			return (Criteria) this;
		}

		public Criteria andIdareaEqualTo(Short value) {
			addCriterion("IDAREA =", value, "idarea");
			return (Criteria) this;
		}

		public Criteria andIdareaNotEqualTo(Short value) {
			addCriterion("IDAREA <>", value, "idarea");
			return (Criteria) this;
		}

		public Criteria andIdareaGreaterThan(Short value) {
			addCriterion("IDAREA >", value, "idarea");
			return (Criteria) this;
		}

		public Criteria andIdareaGreaterThanOrEqualTo(Short value) {
			addCriterion("IDAREA >=", value, "idarea");
			return (Criteria) this;
		}

		public Criteria andIdareaLessThan(Short value) {
			addCriterion("IDAREA <", value, "idarea");
			return (Criteria) this;
		}

		public Criteria andIdareaLessThanOrEqualTo(Short value) {
			addCriterion("IDAREA <=", value, "idarea");
			return (Criteria) this;
		}

		public Criteria andIdareaIn(List<Short> values) {
			addCriterion("IDAREA in", values, "idarea");
			return (Criteria) this;
		}

		public Criteria andIdareaNotIn(List<Short> values) {
			addCriterion("IDAREA not in", values, "idarea");
			return (Criteria) this;
		}

		public Criteria andIdareaBetween(Short value1, Short value2) {
			addCriterion("IDAREA between", value1, value2, "idarea");
			return (Criteria) this;
		}

		public Criteria andIdareaNotBetween(Short value1, Short value2) {
			addCriterion("IDAREA not between", value1, value2, "idarea");
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

		public Criteria andNombreIsNull() {
			addCriterion("NOMBRE is null");
			return (Criteria) this;
		}

		public Criteria andNombreIsNotNull() {
			addCriterion("NOMBRE is not null");
			return (Criteria) this;
		}

		public Criteria andNombreEqualTo(String value) {
			addCriterion("NOMBRE =", value, "nombre");
			return (Criteria) this;
		}

		public Criteria andNombreNotEqualTo(String value) {
			addCriterion("NOMBRE <>", value, "nombre");
			return (Criteria) this;
		}

		public Criteria andNombreGreaterThan(String value) {
			addCriterion("NOMBRE >", value, "nombre");
			return (Criteria) this;
		}

		public Criteria andNombreGreaterThanOrEqualTo(String value) {
			addCriterion("NOMBRE >=", value, "nombre");
			return (Criteria) this;
		}

		public Criteria andNombreLessThan(String value) {
			addCriterion("NOMBRE <", value, "nombre");
			return (Criteria) this;
		}

		public Criteria andNombreLessThanOrEqualTo(String value) {
			addCriterion("NOMBRE <=", value, "nombre");
			return (Criteria) this;
		}

		public Criteria andNombreLike(String value) {
			addCriterion("NOMBRE like", value, "nombre");
			return (Criteria) this;
		}

		public Criteria andNombreNotLike(String value) {
			addCriterion("NOMBRE not like", value, "nombre");
			return (Criteria) this;
		}

		public Criteria andNombreIn(List<String> values) {
			addCriterion("NOMBRE in", values, "nombre");
			return (Criteria) this;
		}

		public Criteria andNombreNotIn(List<String> values) {
			addCriterion("NOMBRE not in", values, "nombre");
			return (Criteria) this;
		}

		public Criteria andNombreBetween(String value1, String value2) {
			addCriterion("NOMBRE between", value1, value2, "nombre");
			return (Criteria) this;
		}

		public Criteria andNombreNotBetween(String value1, String value2) {
			addCriterion("NOMBRE not between", value1, value2, "nombre");
			return (Criteria) this;
		}

		public Criteria andContenidoIsNull() {
			addCriterion("CONTENIDO is null");
			return (Criteria) this;
		}

		public Criteria andContenidoIsNotNull() {
			addCriterion("CONTENIDO is not null");
			return (Criteria) this;
		}

		public Criteria andContenidoEqualTo(String value) {
			addCriterion("CONTENIDO =", value, "contenido");
			return (Criteria) this;
		}

		public Criteria andContenidoNotEqualTo(String value) {
			addCriterion("CONTENIDO <>", value, "contenido");
			return (Criteria) this;
		}

		public Criteria andContenidoGreaterThan(String value) {
			addCriterion("CONTENIDO >", value, "contenido");
			return (Criteria) this;
		}

		public Criteria andContenidoGreaterThanOrEqualTo(String value) {
			addCriterion("CONTENIDO >=", value, "contenido");
			return (Criteria) this;
		}

		public Criteria andContenidoLessThan(String value) {
			addCriterion("CONTENIDO <", value, "contenido");
			return (Criteria) this;
		}

		public Criteria andContenidoLessThanOrEqualTo(String value) {
			addCriterion("CONTENIDO <=", value, "contenido");
			return (Criteria) this;
		}

		public Criteria andContenidoLike(String value) {
			addCriterion("CONTENIDO like", value, "contenido");
			return (Criteria) this;
		}

		public Criteria andContenidoNotLike(String value) {
			addCriterion("CONTENIDO not like", value, "contenido");
			return (Criteria) this;
		}

		public Criteria andContenidoIn(List<String> values) {
			addCriterion("CONTENIDO in", values, "contenido");
			return (Criteria) this;
		}

		public Criteria andContenidoNotIn(List<String> values) {
			addCriterion("CONTENIDO not in", values, "contenido");
			return (Criteria) this;
		}

		public Criteria andContenidoBetween(String value1, String value2) {
			addCriterion("CONTENIDO between", value1, value2, "contenido");
			return (Criteria) this;
		}

		public Criteria andContenidoNotBetween(String value1, String value2) {
			addCriterion("CONTENIDO not between", value1, value2, "contenido");
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
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE.SCS_AREA
	 * @mbg.generated  Tue Sep 03 13:29:45 CEST 2019
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
     * This class corresponds to the database table USCGAE.SCS_AREA
     *
     * @mbg.generated do_not_delete_during_merge Wed Aug 28 10:33:57 CEST 2019
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}