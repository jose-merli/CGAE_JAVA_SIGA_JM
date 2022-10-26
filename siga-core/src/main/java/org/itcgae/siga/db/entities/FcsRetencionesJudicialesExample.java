package org.itcgae.siga.db.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FcsRetencionesJudicialesExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE_INT.FCS_RETENCIONES_JUDICIALES
	 * @mbg.generated  Tue Oct 25 21:00:49 CEST 2022
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE_INT.FCS_RETENCIONES_JUDICIALES
	 * @mbg.generated  Tue Oct 25 21:00:49 CEST 2022
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE_INT.FCS_RETENCIONES_JUDICIALES
	 * @mbg.generated  Tue Oct 25 21:00:49 CEST 2022
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.FCS_RETENCIONES_JUDICIALES
	 * @mbg.generated  Tue Oct 25 21:00:49 CEST 2022
	 */
	public FcsRetencionesJudicialesExample() {
		oredCriteria = new ArrayList<>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.FCS_RETENCIONES_JUDICIALES
	 * @mbg.generated  Tue Oct 25 21:00:49 CEST 2022
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.FCS_RETENCIONES_JUDICIALES
	 * @mbg.generated  Tue Oct 25 21:00:49 CEST 2022
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.FCS_RETENCIONES_JUDICIALES
	 * @mbg.generated  Tue Oct 25 21:00:49 CEST 2022
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.FCS_RETENCIONES_JUDICIALES
	 * @mbg.generated  Tue Oct 25 21:00:49 CEST 2022
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.FCS_RETENCIONES_JUDICIALES
	 * @mbg.generated  Tue Oct 25 21:00:49 CEST 2022
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.FCS_RETENCIONES_JUDICIALES
	 * @mbg.generated  Tue Oct 25 21:00:49 CEST 2022
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.FCS_RETENCIONES_JUDICIALES
	 * @mbg.generated  Tue Oct 25 21:00:49 CEST 2022
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.FCS_RETENCIONES_JUDICIALES
	 * @mbg.generated  Tue Oct 25 21:00:49 CEST 2022
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.FCS_RETENCIONES_JUDICIALES
	 * @mbg.generated  Tue Oct 25 21:00:49 CEST 2022
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.FCS_RETENCIONES_JUDICIALES
	 * @mbg.generated  Tue Oct 25 21:00:49 CEST 2022
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE_INT.FCS_RETENCIONES_JUDICIALES
	 * @mbg.generated  Tue Oct 25 21:00:49 CEST 2022
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

		public Criteria andIdretencionIsNull() {
			addCriterion("IDRETENCION is null");
			return (Criteria) this;
		}

		public Criteria andIdretencionIsNotNull() {
			addCriterion("IDRETENCION is not null");
			return (Criteria) this;
		}

		public Criteria andIdretencionEqualTo(Long value) {
			addCriterion("IDRETENCION =", value, "idretencion");
			return (Criteria) this;
		}

		public Criteria andIdretencionNotEqualTo(Long value) {
			addCriterion("IDRETENCION <>", value, "idretencion");
			return (Criteria) this;
		}

		public Criteria andIdretencionGreaterThan(Long value) {
			addCriterion("IDRETENCION >", value, "idretencion");
			return (Criteria) this;
		}

		public Criteria andIdretencionGreaterThanOrEqualTo(Long value) {
			addCriterion("IDRETENCION >=", value, "idretencion");
			return (Criteria) this;
		}

		public Criteria andIdretencionLessThan(Long value) {
			addCriterion("IDRETENCION <", value, "idretencion");
			return (Criteria) this;
		}

		public Criteria andIdretencionLessThanOrEqualTo(Long value) {
			addCriterion("IDRETENCION <=", value, "idretencion");
			return (Criteria) this;
		}

		public Criteria andIdretencionIn(List<Long> values) {
			addCriterion("IDRETENCION in", values, "idretencion");
			return (Criteria) this;
		}

		public Criteria andIdretencionNotIn(List<Long> values) {
			addCriterion("IDRETENCION not in", values, "idretencion");
			return (Criteria) this;
		}

		public Criteria andIdretencionBetween(Long value1, Long value2) {
			addCriterion("IDRETENCION between", value1, value2, "idretencion");
			return (Criteria) this;
		}

		public Criteria andIdretencionNotBetween(Long value1, Long value2) {
			addCriterion("IDRETENCION not between", value1, value2, "idretencion");
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

		public Criteria andIddestinatarioIsNull() {
			addCriterion("IDDESTINATARIO is null");
			return (Criteria) this;
		}

		public Criteria andIddestinatarioIsNotNull() {
			addCriterion("IDDESTINATARIO is not null");
			return (Criteria) this;
		}

		public Criteria andIddestinatarioEqualTo(Integer value) {
			addCriterion("IDDESTINATARIO =", value, "iddestinatario");
			return (Criteria) this;
		}

		public Criteria andIddestinatarioNotEqualTo(Integer value) {
			addCriterion("IDDESTINATARIO <>", value, "iddestinatario");
			return (Criteria) this;
		}

		public Criteria andIddestinatarioGreaterThan(Integer value) {
			addCriterion("IDDESTINATARIO >", value, "iddestinatario");
			return (Criteria) this;
		}

		public Criteria andIddestinatarioGreaterThanOrEqualTo(Integer value) {
			addCriterion("IDDESTINATARIO >=", value, "iddestinatario");
			return (Criteria) this;
		}

		public Criteria andIddestinatarioLessThan(Integer value) {
			addCriterion("IDDESTINATARIO <", value, "iddestinatario");
			return (Criteria) this;
		}

		public Criteria andIddestinatarioLessThanOrEqualTo(Integer value) {
			addCriterion("IDDESTINATARIO <=", value, "iddestinatario");
			return (Criteria) this;
		}

		public Criteria andIddestinatarioIn(List<Integer> values) {
			addCriterion("IDDESTINATARIO in", values, "iddestinatario");
			return (Criteria) this;
		}

		public Criteria andIddestinatarioNotIn(List<Integer> values) {
			addCriterion("IDDESTINATARIO not in", values, "iddestinatario");
			return (Criteria) this;
		}

		public Criteria andIddestinatarioBetween(Integer value1, Integer value2) {
			addCriterion("IDDESTINATARIO between", value1, value2, "iddestinatario");
			return (Criteria) this;
		}

		public Criteria andIddestinatarioNotBetween(Integer value1, Integer value2) {
			addCriterion("IDDESTINATARIO not between", value1, value2, "iddestinatario");
			return (Criteria) this;
		}

		public Criteria andFechaaltaIsNull() {
			addCriterion("FECHAALTA is null");
			return (Criteria) this;
		}

		public Criteria andFechaaltaIsNotNull() {
			addCriterion("FECHAALTA is not null");
			return (Criteria) this;
		}

		public Criteria andFechaaltaEqualTo(Date value) {
			addCriterion("FECHAALTA =", value, "fechaalta");
			return (Criteria) this;
		}

		public Criteria andFechaaltaNotEqualTo(Date value) {
			addCriterion("FECHAALTA <>", value, "fechaalta");
			return (Criteria) this;
		}

		public Criteria andFechaaltaGreaterThan(Date value) {
			addCriterion("FECHAALTA >", value, "fechaalta");
			return (Criteria) this;
		}

		public Criteria andFechaaltaGreaterThanOrEqualTo(Date value) {
			addCriterion("FECHAALTA >=", value, "fechaalta");
			return (Criteria) this;
		}

		public Criteria andFechaaltaLessThan(Date value) {
			addCriterion("FECHAALTA <", value, "fechaalta");
			return (Criteria) this;
		}

		public Criteria andFechaaltaLessThanOrEqualTo(Date value) {
			addCriterion("FECHAALTA <=", value, "fechaalta");
			return (Criteria) this;
		}

		public Criteria andFechaaltaIn(List<Date> values) {
			addCriterion("FECHAALTA in", values, "fechaalta");
			return (Criteria) this;
		}

		public Criteria andFechaaltaNotIn(List<Date> values) {
			addCriterion("FECHAALTA not in", values, "fechaalta");
			return (Criteria) this;
		}

		public Criteria andFechaaltaBetween(Date value1, Date value2) {
			addCriterion("FECHAALTA between", value1, value2, "fechaalta");
			return (Criteria) this;
		}

		public Criteria andFechaaltaNotBetween(Date value1, Date value2) {
			addCriterion("FECHAALTA not between", value1, value2, "fechaalta");
			return (Criteria) this;
		}

		public Criteria andFechainicioIsNull() {
			addCriterion("FECHAINICIO is null");
			return (Criteria) this;
		}

		public Criteria andFechainicioIsNotNull() {
			addCriterion("FECHAINICIO is not null");
			return (Criteria) this;
		}

		public Criteria andFechainicioEqualTo(Date value) {
			addCriterion("FECHAINICIO =", value, "fechainicio");
			return (Criteria) this;
		}

		public Criteria andFechainicioNotEqualTo(Date value) {
			addCriterion("FECHAINICIO <>", value, "fechainicio");
			return (Criteria) this;
		}

		public Criteria andFechainicioGreaterThan(Date value) {
			addCriterion("FECHAINICIO >", value, "fechainicio");
			return (Criteria) this;
		}

		public Criteria andFechainicioGreaterThanOrEqualTo(Date value) {
			addCriterion("FECHAINICIO >=", value, "fechainicio");
			return (Criteria) this;
		}

		public Criteria andFechainicioLessThan(Date value) {
			addCriterion("FECHAINICIO <", value, "fechainicio");
			return (Criteria) this;
		}

		public Criteria andFechainicioLessThanOrEqualTo(Date value) {
			addCriterion("FECHAINICIO <=", value, "fechainicio");
			return (Criteria) this;
		}

		public Criteria andFechainicioIn(List<Date> values) {
			addCriterion("FECHAINICIO in", values, "fechainicio");
			return (Criteria) this;
		}

		public Criteria andFechainicioNotIn(List<Date> values) {
			addCriterion("FECHAINICIO not in", values, "fechainicio");
			return (Criteria) this;
		}

		public Criteria andFechainicioBetween(Date value1, Date value2) {
			addCriterion("FECHAINICIO between", value1, value2, "fechainicio");
			return (Criteria) this;
		}

		public Criteria andFechainicioNotBetween(Date value1, Date value2) {
			addCriterion("FECHAINICIO not between", value1, value2, "fechainicio");
			return (Criteria) this;
		}

		public Criteria andFechafinIsNull() {
			addCriterion("FECHAFIN is null");
			return (Criteria) this;
		}

		public Criteria andFechafinIsNotNull() {
			addCriterion("FECHAFIN is not null");
			return (Criteria) this;
		}

		public Criteria andFechafinEqualTo(Date value) {
			addCriterion("FECHAFIN =", value, "fechafin");
			return (Criteria) this;
		}

		public Criteria andFechafinNotEqualTo(Date value) {
			addCriterion("FECHAFIN <>", value, "fechafin");
			return (Criteria) this;
		}

		public Criteria andFechafinGreaterThan(Date value) {
			addCriterion("FECHAFIN >", value, "fechafin");
			return (Criteria) this;
		}

		public Criteria andFechafinGreaterThanOrEqualTo(Date value) {
			addCriterion("FECHAFIN >=", value, "fechafin");
			return (Criteria) this;
		}

		public Criteria andFechafinLessThan(Date value) {
			addCriterion("FECHAFIN <", value, "fechafin");
			return (Criteria) this;
		}

		public Criteria andFechafinLessThanOrEqualTo(Date value) {
			addCriterion("FECHAFIN <=", value, "fechafin");
			return (Criteria) this;
		}

		public Criteria andFechafinIn(List<Date> values) {
			addCriterion("FECHAFIN in", values, "fechafin");
			return (Criteria) this;
		}

		public Criteria andFechafinNotIn(List<Date> values) {
			addCriterion("FECHAFIN not in", values, "fechafin");
			return (Criteria) this;
		}

		public Criteria andFechafinBetween(Date value1, Date value2) {
			addCriterion("FECHAFIN between", value1, value2, "fechafin");
			return (Criteria) this;
		}

		public Criteria andFechafinNotBetween(Date value1, Date value2) {
			addCriterion("FECHAFIN not between", value1, value2, "fechafin");
			return (Criteria) this;
		}

		public Criteria andTiporetencionIsNull() {
			addCriterion("TIPORETENCION is null");
			return (Criteria) this;
		}

		public Criteria andTiporetencionIsNotNull() {
			addCriterion("TIPORETENCION is not null");
			return (Criteria) this;
		}

		public Criteria andTiporetencionEqualTo(String value) {
			addCriterion("TIPORETENCION =", value, "tiporetencion");
			return (Criteria) this;
		}

		public Criteria andTiporetencionNotEqualTo(String value) {
			addCriterion("TIPORETENCION <>", value, "tiporetencion");
			return (Criteria) this;
		}

		public Criteria andTiporetencionGreaterThan(String value) {
			addCriterion("TIPORETENCION >", value, "tiporetencion");
			return (Criteria) this;
		}

		public Criteria andTiporetencionGreaterThanOrEqualTo(String value) {
			addCriterion("TIPORETENCION >=", value, "tiporetencion");
			return (Criteria) this;
		}

		public Criteria andTiporetencionLessThan(String value) {
			addCriterion("TIPORETENCION <", value, "tiporetencion");
			return (Criteria) this;
		}

		public Criteria andTiporetencionLessThanOrEqualTo(String value) {
			addCriterion("TIPORETENCION <=", value, "tiporetencion");
			return (Criteria) this;
		}

		public Criteria andTiporetencionLike(String value) {
			addCriterion("TIPORETENCION like", value, "tiporetencion");
			return (Criteria) this;
		}

		public Criteria andTiporetencionNotLike(String value) {
			addCriterion("TIPORETENCION not like", value, "tiporetencion");
			return (Criteria) this;
		}

		public Criteria andTiporetencionIn(List<String> values) {
			addCriterion("TIPORETENCION in", values, "tiporetencion");
			return (Criteria) this;
		}

		public Criteria andTiporetencionNotIn(List<String> values) {
			addCriterion("TIPORETENCION not in", values, "tiporetencion");
			return (Criteria) this;
		}

		public Criteria andTiporetencionBetween(String value1, String value2) {
			addCriterion("TIPORETENCION between", value1, value2, "tiporetencion");
			return (Criteria) this;
		}

		public Criteria andTiporetencionNotBetween(String value1, String value2) {
			addCriterion("TIPORETENCION not between", value1, value2, "tiporetencion");
			return (Criteria) this;
		}

		public Criteria andImporteIsNull() {
			addCriterion("IMPORTE is null");
			return (Criteria) this;
		}

		public Criteria andImporteIsNotNull() {
			addCriterion("IMPORTE is not null");
			return (Criteria) this;
		}

		public Criteria andImporteEqualTo(BigDecimal value) {
			addCriterion("IMPORTE =", value, "importe");
			return (Criteria) this;
		}

		public Criteria andImporteNotEqualTo(BigDecimal value) {
			addCriterion("IMPORTE <>", value, "importe");
			return (Criteria) this;
		}

		public Criteria andImporteGreaterThan(BigDecimal value) {
			addCriterion("IMPORTE >", value, "importe");
			return (Criteria) this;
		}

		public Criteria andImporteGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("IMPORTE >=", value, "importe");
			return (Criteria) this;
		}

		public Criteria andImporteLessThan(BigDecimal value) {
			addCriterion("IMPORTE <", value, "importe");
			return (Criteria) this;
		}

		public Criteria andImporteLessThanOrEqualTo(BigDecimal value) {
			addCriterion("IMPORTE <=", value, "importe");
			return (Criteria) this;
		}

		public Criteria andImporteIn(List<BigDecimal> values) {
			addCriterion("IMPORTE in", values, "importe");
			return (Criteria) this;
		}

		public Criteria andImporteNotIn(List<BigDecimal> values) {
			addCriterion("IMPORTE not in", values, "importe");
			return (Criteria) this;
		}

		public Criteria andImporteBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("IMPORTE between", value1, value2, "importe");
			return (Criteria) this;
		}

		public Criteria andImporteNotBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("IMPORTE not between", value1, value2, "importe");
			return (Criteria) this;
		}

		public Criteria andObservacionesIsNull() {
			addCriterion("OBSERVACIONES is null");
			return (Criteria) this;
		}

		public Criteria andObservacionesIsNotNull() {
			addCriterion("OBSERVACIONES is not null");
			return (Criteria) this;
		}

		public Criteria andObservacionesEqualTo(String value) {
			addCriterion("OBSERVACIONES =", value, "observaciones");
			return (Criteria) this;
		}

		public Criteria andObservacionesNotEqualTo(String value) {
			addCriterion("OBSERVACIONES <>", value, "observaciones");
			return (Criteria) this;
		}

		public Criteria andObservacionesGreaterThan(String value) {
			addCriterion("OBSERVACIONES >", value, "observaciones");
			return (Criteria) this;
		}

		public Criteria andObservacionesGreaterThanOrEqualTo(String value) {
			addCriterion("OBSERVACIONES >=", value, "observaciones");
			return (Criteria) this;
		}

		public Criteria andObservacionesLessThan(String value) {
			addCriterion("OBSERVACIONES <", value, "observaciones");
			return (Criteria) this;
		}

		public Criteria andObservacionesLessThanOrEqualTo(String value) {
			addCriterion("OBSERVACIONES <=", value, "observaciones");
			return (Criteria) this;
		}

		public Criteria andObservacionesLike(String value) {
			addCriterion("OBSERVACIONES like", value, "observaciones");
			return (Criteria) this;
		}

		public Criteria andObservacionesNotLike(String value) {
			addCriterion("OBSERVACIONES not like", value, "observaciones");
			return (Criteria) this;
		}

		public Criteria andObservacionesIn(List<String> values) {
			addCriterion("OBSERVACIONES in", values, "observaciones");
			return (Criteria) this;
		}

		public Criteria andObservacionesNotIn(List<String> values) {
			addCriterion("OBSERVACIONES not in", values, "observaciones");
			return (Criteria) this;
		}

		public Criteria andObservacionesBetween(String value1, String value2) {
			addCriterion("OBSERVACIONES between", value1, value2, "observaciones");
			return (Criteria) this;
		}

		public Criteria andObservacionesNotBetween(String value1, String value2) {
			addCriterion("OBSERVACIONES not between", value1, value2, "observaciones");
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

		public Criteria andContabilizadoIsNull() {
			addCriterion("CONTABILIZADO is null");
			return (Criteria) this;
		}

		public Criteria andContabilizadoIsNotNull() {
			addCriterion("CONTABILIZADO is not null");
			return (Criteria) this;
		}

		public Criteria andContabilizadoEqualTo(String value) {
			addCriterion("CONTABILIZADO =", value, "contabilizado");
			return (Criteria) this;
		}

		public Criteria andContabilizadoNotEqualTo(String value) {
			addCriterion("CONTABILIZADO <>", value, "contabilizado");
			return (Criteria) this;
		}

		public Criteria andContabilizadoGreaterThan(String value) {
			addCriterion("CONTABILIZADO >", value, "contabilizado");
			return (Criteria) this;
		}

		public Criteria andContabilizadoGreaterThanOrEqualTo(String value) {
			addCriterion("CONTABILIZADO >=", value, "contabilizado");
			return (Criteria) this;
		}

		public Criteria andContabilizadoLessThan(String value) {
			addCriterion("CONTABILIZADO <", value, "contabilizado");
			return (Criteria) this;
		}

		public Criteria andContabilizadoLessThanOrEqualTo(String value) {
			addCriterion("CONTABILIZADO <=", value, "contabilizado");
			return (Criteria) this;
		}

		public Criteria andContabilizadoLike(String value) {
			addCriterion("CONTABILIZADO like", value, "contabilizado");
			return (Criteria) this;
		}

		public Criteria andContabilizadoNotLike(String value) {
			addCriterion("CONTABILIZADO not like", value, "contabilizado");
			return (Criteria) this;
		}

		public Criteria andContabilizadoIn(List<String> values) {
			addCriterion("CONTABILIZADO in", values, "contabilizado");
			return (Criteria) this;
		}

		public Criteria andContabilizadoNotIn(List<String> values) {
			addCriterion("CONTABILIZADO not in", values, "contabilizado");
			return (Criteria) this;
		}

		public Criteria andContabilizadoBetween(String value1, String value2) {
			addCriterion("CONTABILIZADO between", value1, value2, "contabilizado");
			return (Criteria) this;
		}

		public Criteria andContabilizadoNotBetween(String value1, String value2) {
			addCriterion("CONTABILIZADO not between", value1, value2, "contabilizado");
			return (Criteria) this;
		}

		public Criteria andEsdeturnoIsNull() {
			addCriterion("ESDETURNO is null");
			return (Criteria) this;
		}

		public Criteria andEsdeturnoIsNotNull() {
			addCriterion("ESDETURNO is not null");
			return (Criteria) this;
		}

		public Criteria andEsdeturnoEqualTo(String value) {
			addCriterion("ESDETURNO =", value, "esdeturno");
			return (Criteria) this;
		}

		public Criteria andEsdeturnoNotEqualTo(String value) {
			addCriterion("ESDETURNO <>", value, "esdeturno");
			return (Criteria) this;
		}

		public Criteria andEsdeturnoGreaterThan(String value) {
			addCriterion("ESDETURNO >", value, "esdeturno");
			return (Criteria) this;
		}

		public Criteria andEsdeturnoGreaterThanOrEqualTo(String value) {
			addCriterion("ESDETURNO >=", value, "esdeturno");
			return (Criteria) this;
		}

		public Criteria andEsdeturnoLessThan(String value) {
			addCriterion("ESDETURNO <", value, "esdeturno");
			return (Criteria) this;
		}

		public Criteria andEsdeturnoLessThanOrEqualTo(String value) {
			addCriterion("ESDETURNO <=", value, "esdeturno");
			return (Criteria) this;
		}

		public Criteria andEsdeturnoLike(String value) {
			addCriterion("ESDETURNO like", value, "esdeturno");
			return (Criteria) this;
		}

		public Criteria andEsdeturnoNotLike(String value) {
			addCriterion("ESDETURNO not like", value, "esdeturno");
			return (Criteria) this;
		}

		public Criteria andEsdeturnoIn(List<String> values) {
			addCriterion("ESDETURNO in", values, "esdeturno");
			return (Criteria) this;
		}

		public Criteria andEsdeturnoNotIn(List<String> values) {
			addCriterion("ESDETURNO not in", values, "esdeturno");
			return (Criteria) this;
		}

		public Criteria andEsdeturnoBetween(String value1, String value2) {
			addCriterion("ESDETURNO between", value1, value2, "esdeturno");
			return (Criteria) this;
		}

		public Criteria andEsdeturnoNotBetween(String value1, String value2) {
			addCriterion("ESDETURNO not between", value1, value2, "esdeturno");
			return (Criteria) this;
		}

		public Criteria andDescdestinatarioIsNull() {
			addCriterion("DESCDESTINATARIO is null");
			return (Criteria) this;
		}

		public Criteria andDescdestinatarioIsNotNull() {
			addCriterion("DESCDESTINATARIO is not null");
			return (Criteria) this;
		}

		public Criteria andDescdestinatarioEqualTo(String value) {
			addCriterion("DESCDESTINATARIO =", value, "descdestinatario");
			return (Criteria) this;
		}

		public Criteria andDescdestinatarioNotEqualTo(String value) {
			addCriterion("DESCDESTINATARIO <>", value, "descdestinatario");
			return (Criteria) this;
		}

		public Criteria andDescdestinatarioGreaterThan(String value) {
			addCriterion("DESCDESTINATARIO >", value, "descdestinatario");
			return (Criteria) this;
		}

		public Criteria andDescdestinatarioGreaterThanOrEqualTo(String value) {
			addCriterion("DESCDESTINATARIO >=", value, "descdestinatario");
			return (Criteria) this;
		}

		public Criteria andDescdestinatarioLessThan(String value) {
			addCriterion("DESCDESTINATARIO <", value, "descdestinatario");
			return (Criteria) this;
		}

		public Criteria andDescdestinatarioLessThanOrEqualTo(String value) {
			addCriterion("DESCDESTINATARIO <=", value, "descdestinatario");
			return (Criteria) this;
		}

		public Criteria andDescdestinatarioLike(String value) {
			addCriterion("DESCDESTINATARIO like", value, "descdestinatario");
			return (Criteria) this;
		}

		public Criteria andDescdestinatarioNotLike(String value) {
			addCriterion("DESCDESTINATARIO not like", value, "descdestinatario");
			return (Criteria) this;
		}

		public Criteria andDescdestinatarioIn(List<String> values) {
			addCriterion("DESCDESTINATARIO in", values, "descdestinatario");
			return (Criteria) this;
		}

		public Criteria andDescdestinatarioNotIn(List<String> values) {
			addCriterion("DESCDESTINATARIO not in", values, "descdestinatario");
			return (Criteria) this;
		}

		public Criteria andDescdestinatarioBetween(String value1, String value2) {
			addCriterion("DESCDESTINATARIO between", value1, value2, "descdestinatario");
			return (Criteria) this;
		}

		public Criteria andDescdestinatarioNotBetween(String value1, String value2) {
			addCriterion("DESCDESTINATARIO not between", value1, value2, "descdestinatario");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE_INT.FCS_RETENCIONES_JUDICIALES
	 * @mbg.generated  Tue Oct 25 21:00:49 CEST 2022
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
     * This class corresponds to the database table USCGAE.FCS_RETENCIONES_JUDICIALES
     *
     * @mbg.generated do_not_delete_during_merge Fri Oct 15 12:21:09 CEST 2021
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }
}