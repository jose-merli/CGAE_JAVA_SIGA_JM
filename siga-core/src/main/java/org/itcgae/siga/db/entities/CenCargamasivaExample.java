package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CenCargamasivaExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE_DESA.CEN_CARGAMASIVA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE_DESA.CEN_CARGAMASIVA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE_DESA.CEN_CARGAMASIVA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CARGAMASIVA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public CenCargamasivaExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CARGAMASIVA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CARGAMASIVA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CARGAMASIVA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CARGAMASIVA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CARGAMASIVA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CARGAMASIVA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CARGAMASIVA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CARGAMASIVA
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
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CARGAMASIVA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_CARGAMASIVA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE_DESA.CEN_CARGAMASIVA
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

		public Criteria andIdcargamasivaIsNull() {
			addCriterion("IDCARGAMASIVA is null");
			return (Criteria) this;
		}

		public Criteria andIdcargamasivaIsNotNull() {
			addCriterion("IDCARGAMASIVA is not null");
			return (Criteria) this;
		}

		public Criteria andIdcargamasivaEqualTo(Integer value) {
			addCriterion("IDCARGAMASIVA =", value, "idcargamasiva");
			return (Criteria) this;
		}

		public Criteria andIdcargamasivaNotEqualTo(Integer value) {
			addCriterion("IDCARGAMASIVA <>", value, "idcargamasiva");
			return (Criteria) this;
		}

		public Criteria andIdcargamasivaGreaterThan(Integer value) {
			addCriterion("IDCARGAMASIVA >", value, "idcargamasiva");
			return (Criteria) this;
		}

		public Criteria andIdcargamasivaGreaterThanOrEqualTo(Integer value) {
			addCriterion("IDCARGAMASIVA >=", value, "idcargamasiva");
			return (Criteria) this;
		}

		public Criteria andIdcargamasivaLessThan(Integer value) {
			addCriterion("IDCARGAMASIVA <", value, "idcargamasiva");
			return (Criteria) this;
		}

		public Criteria andIdcargamasivaLessThanOrEqualTo(Integer value) {
			addCriterion("IDCARGAMASIVA <=", value, "idcargamasiva");
			return (Criteria) this;
		}

		public Criteria andIdcargamasivaIn(List<Integer> values) {
			addCriterion("IDCARGAMASIVA in", values, "idcargamasiva");
			return (Criteria) this;
		}

		public Criteria andIdcargamasivaNotIn(List<Integer> values) {
			addCriterion("IDCARGAMASIVA not in", values, "idcargamasiva");
			return (Criteria) this;
		}

		public Criteria andIdcargamasivaBetween(Integer value1, Integer value2) {
			addCriterion("IDCARGAMASIVA between", value1, value2, "idcargamasiva");
			return (Criteria) this;
		}

		public Criteria andIdcargamasivaNotBetween(Integer value1, Integer value2) {
			addCriterion("IDCARGAMASIVA not between", value1, value2, "idcargamasiva");
			return (Criteria) this;
		}

		public Criteria andTipocargaIsNull() {
			addCriterion("TIPOCARGA is null");
			return (Criteria) this;
		}

		public Criteria andTipocargaIsNotNull() {
			addCriterion("TIPOCARGA is not null");
			return (Criteria) this;
		}

		public Criteria andTipocargaEqualTo(String value) {
			addCriterion("TIPOCARGA =", value, "tipocarga");
			return (Criteria) this;
		}

		public Criteria andTipocargaNotEqualTo(String value) {
			addCriterion("TIPOCARGA <>", value, "tipocarga");
			return (Criteria) this;
		}

		public Criteria andTipocargaGreaterThan(String value) {
			addCriterion("TIPOCARGA >", value, "tipocarga");
			return (Criteria) this;
		}

		public Criteria andTipocargaGreaterThanOrEqualTo(String value) {
			addCriterion("TIPOCARGA >=", value, "tipocarga");
			return (Criteria) this;
		}

		public Criteria andTipocargaLessThan(String value) {
			addCriterion("TIPOCARGA <", value, "tipocarga");
			return (Criteria) this;
		}

		public Criteria andTipocargaLessThanOrEqualTo(String value) {
			addCriterion("TIPOCARGA <=", value, "tipocarga");
			return (Criteria) this;
		}

		public Criteria andTipocargaLike(String value) {
			addCriterion("TIPOCARGA like", value, "tipocarga");
			return (Criteria) this;
		}

		public Criteria andTipocargaNotLike(String value) {
			addCriterion("TIPOCARGA not like", value, "tipocarga");
			return (Criteria) this;
		}

		public Criteria andTipocargaIn(List<String> values) {
			addCriterion("TIPOCARGA in", values, "tipocarga");
			return (Criteria) this;
		}

		public Criteria andTipocargaNotIn(List<String> values) {
			addCriterion("TIPOCARGA not in", values, "tipocarga");
			return (Criteria) this;
		}

		public Criteria andTipocargaBetween(String value1, String value2) {
			addCriterion("TIPOCARGA between", value1, value2, "tipocarga");
			return (Criteria) this;
		}

		public Criteria andTipocargaNotBetween(String value1, String value2) {
			addCriterion("TIPOCARGA not between", value1, value2, "tipocarga");
			return (Criteria) this;
		}

		public Criteria andFechacargaIsNull() {
			addCriterion("FECHACARGA is null");
			return (Criteria) this;
		}

		public Criteria andFechacargaIsNotNull() {
			addCriterion("FECHACARGA is not null");
			return (Criteria) this;
		}

		public Criteria andFechacargaEqualTo(Date value) {
			addCriterion("FECHACARGA =", value, "fechacarga");
			return (Criteria) this;
		}

		public Criteria andFechacargaNotEqualTo(Date value) {
			addCriterion("FECHACARGA <>", value, "fechacarga");
			return (Criteria) this;
		}

		public Criteria andFechacargaGreaterThan(Date value) {
			addCriterion("FECHACARGA >", value, "fechacarga");
			return (Criteria) this;
		}

		public Criteria andFechacargaGreaterThanOrEqualTo(Date value) {
			addCriterion("FECHACARGA >=", value, "fechacarga");
			return (Criteria) this;
		}

		public Criteria andFechacargaLessThan(Date value) {
			addCriterion("FECHACARGA <", value, "fechacarga");
			return (Criteria) this;
		}

		public Criteria andFechacargaLessThanOrEqualTo(Date value) {
			addCriterion("FECHACARGA <=", value, "fechacarga");
			return (Criteria) this;
		}

		public Criteria andFechacargaIn(List<Date> values) {
			addCriterion("FECHACARGA in", values, "fechacarga");
			return (Criteria) this;
		}

		public Criteria andFechacargaNotIn(List<Date> values) {
			addCriterion("FECHACARGA not in", values, "fechacarga");
			return (Criteria) this;
		}

		public Criteria andFechacargaBetween(Date value1, Date value2) {
			addCriterion("FECHACARGA between", value1, value2, "fechacarga");
			return (Criteria) this;
		}

		public Criteria andFechacargaNotBetween(Date value1, Date value2) {
			addCriterion("FECHACARGA not between", value1, value2, "fechacarga");
			return (Criteria) this;
		}

		public Criteria andIdficheroIsNull() {
			addCriterion("IDFICHERO is null");
			return (Criteria) this;
		}

		public Criteria andIdficheroIsNotNull() {
			addCriterion("IDFICHERO is not null");
			return (Criteria) this;
		}

		public Criteria andIdficheroEqualTo(Long value) {
			addCriterion("IDFICHERO =", value, "idfichero");
			return (Criteria) this;
		}

		public Criteria andIdficheroNotEqualTo(Long value) {
			addCriterion("IDFICHERO <>", value, "idfichero");
			return (Criteria) this;
		}

		public Criteria andIdficheroGreaterThan(Long value) {
			addCriterion("IDFICHERO >", value, "idfichero");
			return (Criteria) this;
		}

		public Criteria andIdficheroGreaterThanOrEqualTo(Long value) {
			addCriterion("IDFICHERO >=", value, "idfichero");
			return (Criteria) this;
		}

		public Criteria andIdficheroLessThan(Long value) {
			addCriterion("IDFICHERO <", value, "idfichero");
			return (Criteria) this;
		}

		public Criteria andIdficheroLessThanOrEqualTo(Long value) {
			addCriterion("IDFICHERO <=", value, "idfichero");
			return (Criteria) this;
		}

		public Criteria andIdficheroIn(List<Long> values) {
			addCriterion("IDFICHERO in", values, "idfichero");
			return (Criteria) this;
		}

		public Criteria andIdficheroNotIn(List<Long> values) {
			addCriterion("IDFICHERO not in", values, "idfichero");
			return (Criteria) this;
		}

		public Criteria andIdficheroBetween(Long value1, Long value2) {
			addCriterion("IDFICHERO between", value1, value2, "idfichero");
			return (Criteria) this;
		}

		public Criteria andIdficheroNotBetween(Long value1, Long value2) {
			addCriterion("IDFICHERO not between", value1, value2, "idfichero");
			return (Criteria) this;
		}

		public Criteria andIdficherologIsNull() {
			addCriterion("IDFICHEROLOG is null");
			return (Criteria) this;
		}

		public Criteria andIdficherologIsNotNull() {
			addCriterion("IDFICHEROLOG is not null");
			return (Criteria) this;
		}

		public Criteria andIdficherologEqualTo(Long value) {
			addCriterion("IDFICHEROLOG =", value, "idficherolog");
			return (Criteria) this;
		}

		public Criteria andIdficherologNotEqualTo(Long value) {
			addCriterion("IDFICHEROLOG <>", value, "idficherolog");
			return (Criteria) this;
		}

		public Criteria andIdficherologGreaterThan(Long value) {
			addCriterion("IDFICHEROLOG >", value, "idficherolog");
			return (Criteria) this;
		}

		public Criteria andIdficherologGreaterThanOrEqualTo(Long value) {
			addCriterion("IDFICHEROLOG >=", value, "idficherolog");
			return (Criteria) this;
		}

		public Criteria andIdficherologLessThan(Long value) {
			addCriterion("IDFICHEROLOG <", value, "idficherolog");
			return (Criteria) this;
		}

		public Criteria andIdficherologLessThanOrEqualTo(Long value) {
			addCriterion("IDFICHEROLOG <=", value, "idficherolog");
			return (Criteria) this;
		}

		public Criteria andIdficherologIn(List<Long> values) {
			addCriterion("IDFICHEROLOG in", values, "idficherolog");
			return (Criteria) this;
		}

		public Criteria andIdficherologNotIn(List<Long> values) {
			addCriterion("IDFICHEROLOG not in", values, "idficherolog");
			return (Criteria) this;
		}

		public Criteria andIdficherologBetween(Long value1, Long value2) {
			addCriterion("IDFICHEROLOG between", value1, value2, "idficherolog");
			return (Criteria) this;
		}

		public Criteria andIdficherologNotBetween(Long value1, Long value2) {
			addCriterion("IDFICHEROLOG not between", value1, value2, "idficherolog");
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

		public Criteria andNumregistrosIsNull() {
			addCriterion("NUMREGISTROS is null");
			return (Criteria) this;
		}

		public Criteria andNumregistrosIsNotNull() {
			addCriterion("NUMREGISTROS is not null");
			return (Criteria) this;
		}

		public Criteria andNumregistrosEqualTo(Integer value) {
			addCriterion("NUMREGISTROS =", value, "numregistros");
			return (Criteria) this;
		}

		public Criteria andNumregistrosNotEqualTo(Integer value) {
			addCriterion("NUMREGISTROS <>", value, "numregistros");
			return (Criteria) this;
		}

		public Criteria andNumregistrosGreaterThan(Integer value) {
			addCriterion("NUMREGISTROS >", value, "numregistros");
			return (Criteria) this;
		}

		public Criteria andNumregistrosGreaterThanOrEqualTo(Integer value) {
			addCriterion("NUMREGISTROS >=", value, "numregistros");
			return (Criteria) this;
		}

		public Criteria andNumregistrosLessThan(Integer value) {
			addCriterion("NUMREGISTROS <", value, "numregistros");
			return (Criteria) this;
		}

		public Criteria andNumregistrosLessThanOrEqualTo(Integer value) {
			addCriterion("NUMREGISTROS <=", value, "numregistros");
			return (Criteria) this;
		}

		public Criteria andNumregistrosIn(List<Integer> values) {
			addCriterion("NUMREGISTROS in", values, "numregistros");
			return (Criteria) this;
		}

		public Criteria andNumregistrosNotIn(List<Integer> values) {
			addCriterion("NUMREGISTROS not in", values, "numregistros");
			return (Criteria) this;
		}

		public Criteria andNumregistrosBetween(Integer value1, Integer value2) {
			addCriterion("NUMREGISTROS between", value1, value2, "numregistros");
			return (Criteria) this;
		}

		public Criteria andNumregistrosNotBetween(Integer value1, Integer value2) {
			addCriterion("NUMREGISTROS not between", value1, value2, "numregistros");
			return (Criteria) this;
		}

		public Criteria andNombreficheroIsNull() {
			addCriterion("NOMBREFICHERO is null");
			return (Criteria) this;
		}

		public Criteria andNombreficheroIsNotNull() {
			addCriterion("NOMBREFICHERO is not null");
			return (Criteria) this;
		}

		public Criteria andNombreficheroEqualTo(String value) {
			addCriterion("NOMBREFICHERO =", value, "nombrefichero");
			return (Criteria) this;
		}

		public Criteria andNombreficheroNotEqualTo(String value) {
			addCriterion("NOMBREFICHERO <>", value, "nombrefichero");
			return (Criteria) this;
		}

		public Criteria andNombreficheroGreaterThan(String value) {
			addCriterion("NOMBREFICHERO >", value, "nombrefichero");
			return (Criteria) this;
		}

		public Criteria andNombreficheroGreaterThanOrEqualTo(String value) {
			addCriterion("NOMBREFICHERO >=", value, "nombrefichero");
			return (Criteria) this;
		}

		public Criteria andNombreficheroLessThan(String value) {
			addCriterion("NOMBREFICHERO <", value, "nombrefichero");
			return (Criteria) this;
		}

		public Criteria andNombreficheroLessThanOrEqualTo(String value) {
			addCriterion("NOMBREFICHERO <=", value, "nombrefichero");
			return (Criteria) this;
		}

		public Criteria andNombreficheroLike(String value) {
			addCriterion("NOMBREFICHERO like", value, "nombrefichero");
			return (Criteria) this;
		}

		public Criteria andNombreficheroNotLike(String value) {
			addCriterion("NOMBREFICHERO not like", value, "nombrefichero");
			return (Criteria) this;
		}

		public Criteria andNombreficheroIn(List<String> values) {
			addCriterion("NOMBREFICHERO in", values, "nombrefichero");
			return (Criteria) this;
		}

		public Criteria andNombreficheroNotIn(List<String> values) {
			addCriterion("NOMBREFICHERO not in", values, "nombrefichero");
			return (Criteria) this;
		}

		public Criteria andNombreficheroBetween(String value1, String value2) {
			addCriterion("NOMBREFICHERO between", value1, value2, "nombrefichero");
			return (Criteria) this;
		}

		public Criteria andNombreficheroNotBetween(String value1, String value2) {
			addCriterion("NOMBREFICHERO not between", value1, value2, "nombrefichero");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE_DESA.CEN_CARGAMASIVA
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
     * This class corresponds to the database table USCGAE_DESA.CEN_CARGAMASIVA
     *
     * @mbg.generated do_not_delete_during_merge Wed Mar 07 17:49:31 CET 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}