package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModModeloPlantilladocumentoExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE.MOD_MODELO_PLANTILLADOCUMENTO
	 * @mbg.generated  Wed Jan 16 09:26:57 CET 2019
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE.MOD_MODELO_PLANTILLADOCUMENTO
	 * @mbg.generated  Wed Jan 16 09:26:57 CET 2019
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE.MOD_MODELO_PLANTILLADOCUMENTO
	 * @mbg.generated  Wed Jan 16 09:26:57 CET 2019
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_MODELO_PLANTILLADOCUMENTO
	 * @mbg.generated  Wed Jan 16 09:26:57 CET 2019
	 */
	public ModModeloPlantilladocumentoExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_MODELO_PLANTILLADOCUMENTO
	 * @mbg.generated  Wed Jan 16 09:26:57 CET 2019
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_MODELO_PLANTILLADOCUMENTO
	 * @mbg.generated  Wed Jan 16 09:26:57 CET 2019
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_MODELO_PLANTILLADOCUMENTO
	 * @mbg.generated  Wed Jan 16 09:26:57 CET 2019
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_MODELO_PLANTILLADOCUMENTO
	 * @mbg.generated  Wed Jan 16 09:26:57 CET 2019
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_MODELO_PLANTILLADOCUMENTO
	 * @mbg.generated  Wed Jan 16 09:26:57 CET 2019
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_MODELO_PLANTILLADOCUMENTO
	 * @mbg.generated  Wed Jan 16 09:26:57 CET 2019
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_MODELO_PLANTILLADOCUMENTO
	 * @mbg.generated  Wed Jan 16 09:26:57 CET 2019
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_MODELO_PLANTILLADOCUMENTO
	 * @mbg.generated  Wed Jan 16 09:26:57 CET 2019
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_MODELO_PLANTILLADOCUMENTO
	 * @mbg.generated  Wed Jan 16 09:26:57 CET 2019
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.MOD_MODELO_PLANTILLADOCUMENTO
	 * @mbg.generated  Wed Jan 16 09:26:57 CET 2019
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE.MOD_MODELO_PLANTILLADOCUMENTO
	 * @mbg.generated  Wed Jan 16 09:26:57 CET 2019
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

		public Criteria andFechaasociacionIsNull() {
			addCriterion("FECHAASOCIACION is null");
			return (Criteria) this;
		}

		public Criteria andFechaasociacionIsNotNull() {
			addCriterion("FECHAASOCIACION is not null");
			return (Criteria) this;
		}

		public Criteria andFechaasociacionEqualTo(Date value) {
			addCriterion("FECHAASOCIACION =", value, "fechaasociacion");
			return (Criteria) this;
		}

		public Criteria andFechaasociacionNotEqualTo(Date value) {
			addCriterion("FECHAASOCIACION <>", value, "fechaasociacion");
			return (Criteria) this;
		}

		public Criteria andFechaasociacionGreaterThan(Date value) {
			addCriterion("FECHAASOCIACION >", value, "fechaasociacion");
			return (Criteria) this;
		}

		public Criteria andFechaasociacionGreaterThanOrEqualTo(Date value) {
			addCriterion("FECHAASOCIACION >=", value, "fechaasociacion");
			return (Criteria) this;
		}

		public Criteria andFechaasociacionLessThan(Date value) {
			addCriterion("FECHAASOCIACION <", value, "fechaasociacion");
			return (Criteria) this;
		}

		public Criteria andFechaasociacionLessThanOrEqualTo(Date value) {
			addCriterion("FECHAASOCIACION <=", value, "fechaasociacion");
			return (Criteria) this;
		}

		public Criteria andFechaasociacionIn(List<Date> values) {
			addCriterion("FECHAASOCIACION in", values, "fechaasociacion");
			return (Criteria) this;
		}

		public Criteria andFechaasociacionNotIn(List<Date> values) {
			addCriterion("FECHAASOCIACION not in", values, "fechaasociacion");
			return (Criteria) this;
		}

		public Criteria andFechaasociacionBetween(Date value1, Date value2) {
			addCriterion("FECHAASOCIACION between", value1, value2, "fechaasociacion");
			return (Criteria) this;
		}

		public Criteria andFechaasociacionNotBetween(Date value1, Date value2) {
			addCriterion("FECHAASOCIACION not between", value1, value2, "fechaasociacion");
			return (Criteria) this;
		}

		public Criteria andNombreficherosalidaIsNull() {
			addCriterion("NOMBREFICHEROSALIDA is null");
			return (Criteria) this;
		}

		public Criteria andNombreficherosalidaIsNotNull() {
			addCriterion("NOMBREFICHEROSALIDA is not null");
			return (Criteria) this;
		}

		public Criteria andNombreficherosalidaEqualTo(String value) {
			addCriterion("NOMBREFICHEROSALIDA =", value, "nombreficherosalida");
			return (Criteria) this;
		}

		public Criteria andNombreficherosalidaNotEqualTo(String value) {
			addCriterion("NOMBREFICHEROSALIDA <>", value, "nombreficherosalida");
			return (Criteria) this;
		}

		public Criteria andNombreficherosalidaGreaterThan(String value) {
			addCriterion("NOMBREFICHEROSALIDA >", value, "nombreficherosalida");
			return (Criteria) this;
		}

		public Criteria andNombreficherosalidaGreaterThanOrEqualTo(String value) {
			addCriterion("NOMBREFICHEROSALIDA >=", value, "nombreficherosalida");
			return (Criteria) this;
		}

		public Criteria andNombreficherosalidaLessThan(String value) {
			addCriterion("NOMBREFICHEROSALIDA <", value, "nombreficherosalida");
			return (Criteria) this;
		}

		public Criteria andNombreficherosalidaLessThanOrEqualTo(String value) {
			addCriterion("NOMBREFICHEROSALIDA <=", value, "nombreficherosalida");
			return (Criteria) this;
		}

		public Criteria andNombreficherosalidaLike(String value) {
			addCriterion("NOMBREFICHEROSALIDA like", value, "nombreficherosalida");
			return (Criteria) this;
		}

		public Criteria andNombreficherosalidaNotLike(String value) {
			addCriterion("NOMBREFICHEROSALIDA not like", value, "nombreficherosalida");
			return (Criteria) this;
		}

		public Criteria andNombreficherosalidaIn(List<String> values) {
			addCriterion("NOMBREFICHEROSALIDA in", values, "nombreficherosalida");
			return (Criteria) this;
		}

		public Criteria andNombreficherosalidaNotIn(List<String> values) {
			addCriterion("NOMBREFICHEROSALIDA not in", values, "nombreficherosalida");
			return (Criteria) this;
		}

		public Criteria andNombreficherosalidaBetween(String value1, String value2) {
			addCriterion("NOMBREFICHEROSALIDA between", value1, value2, "nombreficherosalida");
			return (Criteria) this;
		}

		public Criteria andNombreficherosalidaNotBetween(String value1, String value2) {
			addCriterion("NOMBREFICHEROSALIDA not between", value1, value2, "nombreficherosalida");
			return (Criteria) this;
		}

		public Criteria andSufijoIsNull() {
			addCriterion("SUFIJO is null");
			return (Criteria) this;
		}

		public Criteria andSufijoIsNotNull() {
			addCriterion("SUFIJO is not null");
			return (Criteria) this;
		}

		public Criteria andSufijoEqualTo(String value) {
			addCriterion("SUFIJO =", value, "sufijo");
			return (Criteria) this;
		}

		public Criteria andSufijoNotEqualTo(String value) {
			addCriterion("SUFIJO <>", value, "sufijo");
			return (Criteria) this;
		}

		public Criteria andSufijoGreaterThan(String value) {
			addCriterion("SUFIJO >", value, "sufijo");
			return (Criteria) this;
		}

		public Criteria andSufijoGreaterThanOrEqualTo(String value) {
			addCriterion("SUFIJO >=", value, "sufijo");
			return (Criteria) this;
		}

		public Criteria andSufijoLessThan(String value) {
			addCriterion("SUFIJO <", value, "sufijo");
			return (Criteria) this;
		}

		public Criteria andSufijoLessThanOrEqualTo(String value) {
			addCriterion("SUFIJO <=", value, "sufijo");
			return (Criteria) this;
		}

		public Criteria andSufijoLike(String value) {
			addCriterion("SUFIJO like", value, "sufijo");
			return (Criteria) this;
		}

		public Criteria andSufijoNotLike(String value) {
			addCriterion("SUFIJO not like", value, "sufijo");
			return (Criteria) this;
		}

		public Criteria andSufijoIn(List<String> values) {
			addCriterion("SUFIJO in", values, "sufijo");
			return (Criteria) this;
		}

		public Criteria andSufijoNotIn(List<String> values) {
			addCriterion("SUFIJO not in", values, "sufijo");
			return (Criteria) this;
		}

		public Criteria andSufijoBetween(String value1, String value2) {
			addCriterion("SUFIJO between", value1, value2, "sufijo");
			return (Criteria) this;
		}

		public Criteria andSufijoNotBetween(String value1, String value2) {
			addCriterion("SUFIJO not between", value1, value2, "sufijo");
			return (Criteria) this;
		}

		public Criteria andFormatosalidaIsNull() {
			addCriterion("FORMATOSALIDA is null");
			return (Criteria) this;
		}

		public Criteria andFormatosalidaIsNotNull() {
			addCriterion("FORMATOSALIDA is not null");
			return (Criteria) this;
		}

		public Criteria andFormatosalidaEqualTo(String value) {
			addCriterion("FORMATOSALIDA =", value, "formatosalida");
			return (Criteria) this;
		}

		public Criteria andFormatosalidaNotEqualTo(String value) {
			addCriterion("FORMATOSALIDA <>", value, "formatosalida");
			return (Criteria) this;
		}

		public Criteria andFormatosalidaGreaterThan(String value) {
			addCriterion("FORMATOSALIDA >", value, "formatosalida");
			return (Criteria) this;
		}

		public Criteria andFormatosalidaGreaterThanOrEqualTo(String value) {
			addCriterion("FORMATOSALIDA >=", value, "formatosalida");
			return (Criteria) this;
		}

		public Criteria andFormatosalidaLessThan(String value) {
			addCriterion("FORMATOSALIDA <", value, "formatosalida");
			return (Criteria) this;
		}

		public Criteria andFormatosalidaLessThanOrEqualTo(String value) {
			addCriterion("FORMATOSALIDA <=", value, "formatosalida");
			return (Criteria) this;
		}

		public Criteria andFormatosalidaLike(String value) {
			addCriterion("FORMATOSALIDA like", value, "formatosalida");
			return (Criteria) this;
		}

		public Criteria andFormatosalidaNotLike(String value) {
			addCriterion("FORMATOSALIDA not like", value, "formatosalida");
			return (Criteria) this;
		}

		public Criteria andFormatosalidaIn(List<String> values) {
			addCriterion("FORMATOSALIDA in", values, "formatosalida");
			return (Criteria) this;
		}

		public Criteria andFormatosalidaNotIn(List<String> values) {
			addCriterion("FORMATOSALIDA not in", values, "formatosalida");
			return (Criteria) this;
		}

		public Criteria andFormatosalidaBetween(String value1, String value2) {
			addCriterion("FORMATOSALIDA between", value1, value2, "formatosalida");
			return (Criteria) this;
		}

		public Criteria andFormatosalidaNotBetween(String value1, String value2) {
			addCriterion("FORMATOSALIDA not between", value1, value2, "formatosalida");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE.MOD_MODELO_PLANTILLADOCUMENTO
	 * @mbg.generated  Wed Jan 16 09:26:57 CET 2019
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
     * This class corresponds to the database table USCGAE.MOD_MODELO_PLANTILLADOCUMENTO
     *
     * @mbg.generated do_not_delete_during_merge Mon Jan 14 11:15:41 CET 2019
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}