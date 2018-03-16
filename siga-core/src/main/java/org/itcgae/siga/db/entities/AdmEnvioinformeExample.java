package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdmEnvioinformeExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE_DESA.ADM_ENVIOINFORME
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE_DESA.ADM_ENVIOINFORME
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE_DESA.ADM_ENVIOINFORME
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_ENVIOINFORME
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public AdmEnvioinformeExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_ENVIOINFORME
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_ENVIOINFORME
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_ENVIOINFORME
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_ENVIOINFORME
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_ENVIOINFORME
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_ENVIOINFORME
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_ENVIOINFORME
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_ENVIOINFORME
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
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_ENVIOINFORME
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_ENVIOINFORME
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE_DESA.ADM_ENVIOINFORME
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

		public Criteria andIdplantillaIsNull() {
			addCriterion("IDPLANTILLA is null");
			return (Criteria) this;
		}

		public Criteria andIdplantillaIsNotNull() {
			addCriterion("IDPLANTILLA is not null");
			return (Criteria) this;
		}

		public Criteria andIdplantillaEqualTo(Integer value) {
			addCriterion("IDPLANTILLA =", value, "idplantilla");
			return (Criteria) this;
		}

		public Criteria andIdplantillaNotEqualTo(Integer value) {
			addCriterion("IDPLANTILLA <>", value, "idplantilla");
			return (Criteria) this;
		}

		public Criteria andIdplantillaGreaterThan(Integer value) {
			addCriterion("IDPLANTILLA >", value, "idplantilla");
			return (Criteria) this;
		}

		public Criteria andIdplantillaGreaterThanOrEqualTo(Integer value) {
			addCriterion("IDPLANTILLA >=", value, "idplantilla");
			return (Criteria) this;
		}

		public Criteria andIdplantillaLessThan(Integer value) {
			addCriterion("IDPLANTILLA <", value, "idplantilla");
			return (Criteria) this;
		}

		public Criteria andIdplantillaLessThanOrEqualTo(Integer value) {
			addCriterion("IDPLANTILLA <=", value, "idplantilla");
			return (Criteria) this;
		}

		public Criteria andIdplantillaIn(List<Integer> values) {
			addCriterion("IDPLANTILLA in", values, "idplantilla");
			return (Criteria) this;
		}

		public Criteria andIdplantillaNotIn(List<Integer> values) {
			addCriterion("IDPLANTILLA not in", values, "idplantilla");
			return (Criteria) this;
		}

		public Criteria andIdplantillaBetween(Integer value1, Integer value2) {
			addCriterion("IDPLANTILLA between", value1, value2, "idplantilla");
			return (Criteria) this;
		}

		public Criteria andIdplantillaNotBetween(Integer value1, Integer value2) {
			addCriterion("IDPLANTILLA not between", value1, value2, "idplantilla");
			return (Criteria) this;
		}

		public Criteria andIdtipoenviosIsNull() {
			addCriterion("IDTIPOENVIOS is null");
			return (Criteria) this;
		}

		public Criteria andIdtipoenviosIsNotNull() {
			addCriterion("IDTIPOENVIOS is not null");
			return (Criteria) this;
		}

		public Criteria andIdtipoenviosEqualTo(Short value) {
			addCriterion("IDTIPOENVIOS =", value, "idtipoenvios");
			return (Criteria) this;
		}

		public Criteria andIdtipoenviosNotEqualTo(Short value) {
			addCriterion("IDTIPOENVIOS <>", value, "idtipoenvios");
			return (Criteria) this;
		}

		public Criteria andIdtipoenviosGreaterThan(Short value) {
			addCriterion("IDTIPOENVIOS >", value, "idtipoenvios");
			return (Criteria) this;
		}

		public Criteria andIdtipoenviosGreaterThanOrEqualTo(Short value) {
			addCriterion("IDTIPOENVIOS >=", value, "idtipoenvios");
			return (Criteria) this;
		}

		public Criteria andIdtipoenviosLessThan(Short value) {
			addCriterion("IDTIPOENVIOS <", value, "idtipoenvios");
			return (Criteria) this;
		}

		public Criteria andIdtipoenviosLessThanOrEqualTo(Short value) {
			addCriterion("IDTIPOENVIOS <=", value, "idtipoenvios");
			return (Criteria) this;
		}

		public Criteria andIdtipoenviosIn(List<Short> values) {
			addCriterion("IDTIPOENVIOS in", values, "idtipoenvios");
			return (Criteria) this;
		}

		public Criteria andIdtipoenviosNotIn(List<Short> values) {
			addCriterion("IDTIPOENVIOS not in", values, "idtipoenvios");
			return (Criteria) this;
		}

		public Criteria andIdtipoenviosBetween(Short value1, Short value2) {
			addCriterion("IDTIPOENVIOS between", value1, value2, "idtipoenvios");
			return (Criteria) this;
		}

		public Criteria andIdtipoenviosNotBetween(Short value1, Short value2) {
			addCriterion("IDTIPOENVIOS not between", value1, value2, "idtipoenvios");
			return (Criteria) this;
		}

		public Criteria andIdplantillaenviodefIsNull() {
			addCriterion("IDPLANTILLAENVIODEF is null");
			return (Criteria) this;
		}

		public Criteria andIdplantillaenviodefIsNotNull() {
			addCriterion("IDPLANTILLAENVIODEF is not null");
			return (Criteria) this;
		}

		public Criteria andIdplantillaenviodefEqualTo(Short value) {
			addCriterion("IDPLANTILLAENVIODEF =", value, "idplantillaenviodef");
			return (Criteria) this;
		}

		public Criteria andIdplantillaenviodefNotEqualTo(Short value) {
			addCriterion("IDPLANTILLAENVIODEF <>", value, "idplantillaenviodef");
			return (Criteria) this;
		}

		public Criteria andIdplantillaenviodefGreaterThan(Short value) {
			addCriterion("IDPLANTILLAENVIODEF >", value, "idplantillaenviodef");
			return (Criteria) this;
		}

		public Criteria andIdplantillaenviodefGreaterThanOrEqualTo(Short value) {
			addCriterion("IDPLANTILLAENVIODEF >=", value, "idplantillaenviodef");
			return (Criteria) this;
		}

		public Criteria andIdplantillaenviodefLessThan(Short value) {
			addCriterion("IDPLANTILLAENVIODEF <", value, "idplantillaenviodef");
			return (Criteria) this;
		}

		public Criteria andIdplantillaenviodefLessThanOrEqualTo(Short value) {
			addCriterion("IDPLANTILLAENVIODEF <=", value, "idplantillaenviodef");
			return (Criteria) this;
		}

		public Criteria andIdplantillaenviodefIn(List<Short> values) {
			addCriterion("IDPLANTILLAENVIODEF in", values, "idplantillaenviodef");
			return (Criteria) this;
		}

		public Criteria andIdplantillaenviodefNotIn(List<Short> values) {
			addCriterion("IDPLANTILLAENVIODEF not in", values, "idplantillaenviodef");
			return (Criteria) this;
		}

		public Criteria andIdplantillaenviodefBetween(Short value1, Short value2) {
			addCriterion("IDPLANTILLAENVIODEF between", value1, value2, "idplantillaenviodef");
			return (Criteria) this;
		}

		public Criteria andIdplantillaenviodefNotBetween(Short value1, Short value2) {
			addCriterion("IDPLANTILLAENVIODEF not between", value1, value2, "idplantillaenviodef");
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

		public Criteria andDefectoIsNull() {
			addCriterion("DEFECTO is null");
			return (Criteria) this;
		}

		public Criteria andDefectoIsNotNull() {
			addCriterion("DEFECTO is not null");
			return (Criteria) this;
		}

		public Criteria andDefectoEqualTo(String value) {
			addCriterion("DEFECTO =", value, "defecto");
			return (Criteria) this;
		}

		public Criteria andDefectoNotEqualTo(String value) {
			addCriterion("DEFECTO <>", value, "defecto");
			return (Criteria) this;
		}

		public Criteria andDefectoGreaterThan(String value) {
			addCriterion("DEFECTO >", value, "defecto");
			return (Criteria) this;
		}

		public Criteria andDefectoGreaterThanOrEqualTo(String value) {
			addCriterion("DEFECTO >=", value, "defecto");
			return (Criteria) this;
		}

		public Criteria andDefectoLessThan(String value) {
			addCriterion("DEFECTO <", value, "defecto");
			return (Criteria) this;
		}

		public Criteria andDefectoLessThanOrEqualTo(String value) {
			addCriterion("DEFECTO <=", value, "defecto");
			return (Criteria) this;
		}

		public Criteria andDefectoLike(String value) {
			addCriterion("DEFECTO like", value, "defecto");
			return (Criteria) this;
		}

		public Criteria andDefectoNotLike(String value) {
			addCriterion("DEFECTO not like", value, "defecto");
			return (Criteria) this;
		}

		public Criteria andDefectoIn(List<String> values) {
			addCriterion("DEFECTO in", values, "defecto");
			return (Criteria) this;
		}

		public Criteria andDefectoNotIn(List<String> values) {
			addCriterion("DEFECTO not in", values, "defecto");
			return (Criteria) this;
		}

		public Criteria andDefectoBetween(String value1, String value2) {
			addCriterion("DEFECTO between", value1, value2, "defecto");
			return (Criteria) this;
		}

		public Criteria andDefectoNotBetween(String value1, String value2) {
			addCriterion("DEFECTO not between", value1, value2, "defecto");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE_DESA.ADM_ENVIOINFORME
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
     * This class corresponds to the database table USCGAE_DESA.ADM_ENVIOINFORME
     *
     * @mbg.generated do_not_delete_during_merge Wed Mar 07 17:49:31 CET 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}