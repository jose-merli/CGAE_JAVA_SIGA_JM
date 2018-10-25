package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgeNotificacioneseventoExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Tue Oct 23 17:52:28 CEST 2018
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Tue Oct 23 17:52:28 CEST 2018
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Tue Oct 23 17:52:28 CEST 2018
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Tue Oct 23 17:52:28 CEST 2018
	 */
	public AgeNotificacioneseventoExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Tue Oct 23 17:52:28 CEST 2018
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Tue Oct 23 17:52:28 CEST 2018
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Tue Oct 23 17:52:28 CEST 2018
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Tue Oct 23 17:52:28 CEST 2018
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Tue Oct 23 17:52:28 CEST 2018
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Tue Oct 23 17:52:28 CEST 2018
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Tue Oct 23 17:52:28 CEST 2018
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Tue Oct 23 17:52:28 CEST 2018
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Tue Oct 23 17:52:28 CEST 2018
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Tue Oct 23 17:52:28 CEST 2018
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Tue Oct 23 17:52:28 CEST 2018
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

		public Criteria andIdnotificacioneventoIsNull() {
			addCriterion("IDNOTIFICACIONEVENTO is null");
			return (Criteria) this;
		}

		public Criteria andIdnotificacioneventoIsNotNull() {
			addCriterion("IDNOTIFICACIONEVENTO is not null");
			return (Criteria) this;
		}

		public Criteria andIdnotificacioneventoEqualTo(Long value) {
			addCriterion("IDNOTIFICACIONEVENTO =", value, "idnotificacionevento");
			return (Criteria) this;
		}

		public Criteria andIdnotificacioneventoNotEqualTo(Long value) {
			addCriterion("IDNOTIFICACIONEVENTO <>", value, "idnotificacionevento");
			return (Criteria) this;
		}

		public Criteria andIdnotificacioneventoGreaterThan(Long value) {
			addCriterion("IDNOTIFICACIONEVENTO >", value, "idnotificacionevento");
			return (Criteria) this;
		}

		public Criteria andIdnotificacioneventoGreaterThanOrEqualTo(Long value) {
			addCriterion("IDNOTIFICACIONEVENTO >=", value, "idnotificacionevento");
			return (Criteria) this;
		}

		public Criteria andIdnotificacioneventoLessThan(Long value) {
			addCriterion("IDNOTIFICACIONEVENTO <", value, "idnotificacionevento");
			return (Criteria) this;
		}

		public Criteria andIdnotificacioneventoLessThanOrEqualTo(Long value) {
			addCriterion("IDNOTIFICACIONEVENTO <=", value, "idnotificacionevento");
			return (Criteria) this;
		}

		public Criteria andIdnotificacioneventoIn(List<Long> values) {
			addCriterion("IDNOTIFICACIONEVENTO in", values, "idnotificacionevento");
			return (Criteria) this;
		}

		public Criteria andIdnotificacioneventoNotIn(List<Long> values) {
			addCriterion("IDNOTIFICACIONEVENTO not in", values, "idnotificacionevento");
			return (Criteria) this;
		}

		public Criteria andIdnotificacioneventoBetween(Long value1, Long value2) {
			addCriterion("IDNOTIFICACIONEVENTO between", value1, value2, "idnotificacionevento");
			return (Criteria) this;
		}

		public Criteria andIdnotificacioneventoNotBetween(Long value1, Long value2) {
			addCriterion("IDNOTIFICACIONEVENTO not between", value1, value2, "idnotificacionevento");
			return (Criteria) this;
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

		public Criteria andUsumodificacionIsNull() {
			addCriterion("USUMODIFICACION is null");
			return (Criteria) this;
		}

		public Criteria andUsumodificacionIsNotNull() {
			addCriterion("USUMODIFICACION is not null");
			return (Criteria) this;
		}

		public Criteria andUsumodificacionEqualTo(Long value) {
			addCriterion("USUMODIFICACION =", value, "usumodificacion");
			return (Criteria) this;
		}

		public Criteria andUsumodificacionNotEqualTo(Long value) {
			addCriterion("USUMODIFICACION <>", value, "usumodificacion");
			return (Criteria) this;
		}

		public Criteria andUsumodificacionGreaterThan(Long value) {
			addCriterion("USUMODIFICACION >", value, "usumodificacion");
			return (Criteria) this;
		}

		public Criteria andUsumodificacionGreaterThanOrEqualTo(Long value) {
			addCriterion("USUMODIFICACION >=", value, "usumodificacion");
			return (Criteria) this;
		}

		public Criteria andUsumodificacionLessThan(Long value) {
			addCriterion("USUMODIFICACION <", value, "usumodificacion");
			return (Criteria) this;
		}

		public Criteria andUsumodificacionLessThanOrEqualTo(Long value) {
			addCriterion("USUMODIFICACION <=", value, "usumodificacion");
			return (Criteria) this;
		}

		public Criteria andUsumodificacionIn(List<Long> values) {
			addCriterion("USUMODIFICACION in", values, "usumodificacion");
			return (Criteria) this;
		}

		public Criteria andUsumodificacionNotIn(List<Long> values) {
			addCriterion("USUMODIFICACION not in", values, "usumodificacion");
			return (Criteria) this;
		}

		public Criteria andUsumodificacionBetween(Long value1, Long value2) {
			addCriterion("USUMODIFICACION between", value1, value2, "usumodificacion");
			return (Criteria) this;
		}

		public Criteria andUsumodificacionNotBetween(Long value1, Long value2) {
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

		public Criteria andIdtiponotificacioneventoIsNull() {
			addCriterion("IDTIPONOTIFICACIONEVENTO is null");
			return (Criteria) this;
		}

		public Criteria andIdtiponotificacioneventoIsNotNull() {
			addCriterion("IDTIPONOTIFICACIONEVENTO is not null");
			return (Criteria) this;
		}

		public Criteria andIdtiponotificacioneventoEqualTo(Long value) {
			addCriterion("IDTIPONOTIFICACIONEVENTO =", value, "idtiponotificacionevento");
			return (Criteria) this;
		}

		public Criteria andIdtiponotificacioneventoNotEqualTo(Long value) {
			addCriterion("IDTIPONOTIFICACIONEVENTO <>", value, "idtiponotificacionevento");
			return (Criteria) this;
		}

		public Criteria andIdtiponotificacioneventoGreaterThan(Long value) {
			addCriterion("IDTIPONOTIFICACIONEVENTO >", value, "idtiponotificacionevento");
			return (Criteria) this;
		}

		public Criteria andIdtiponotificacioneventoGreaterThanOrEqualTo(Long value) {
			addCriterion("IDTIPONOTIFICACIONEVENTO >=", value, "idtiponotificacionevento");
			return (Criteria) this;
		}

		public Criteria andIdtiponotificacioneventoLessThan(Long value) {
			addCriterion("IDTIPONOTIFICACIONEVENTO <", value, "idtiponotificacionevento");
			return (Criteria) this;
		}

		public Criteria andIdtiponotificacioneventoLessThanOrEqualTo(Long value) {
			addCriterion("IDTIPONOTIFICACIONEVENTO <=", value, "idtiponotificacionevento");
			return (Criteria) this;
		}

		public Criteria andIdtiponotificacioneventoIn(List<Long> values) {
			addCriterion("IDTIPONOTIFICACIONEVENTO in", values, "idtiponotificacionevento");
			return (Criteria) this;
		}

		public Criteria andIdtiponotificacioneventoNotIn(List<Long> values) {
			addCriterion("IDTIPONOTIFICACIONEVENTO not in", values, "idtiponotificacionevento");
			return (Criteria) this;
		}

		public Criteria andIdtiponotificacioneventoBetween(Long value1, Long value2) {
			addCriterion("IDTIPONOTIFICACIONEVENTO between", value1, value2, "idtiponotificacionevento");
			return (Criteria) this;
		}

		public Criteria andIdtiponotificacioneventoNotBetween(Long value1, Long value2) {
			addCriterion("IDTIPONOTIFICACIONEVENTO not between", value1, value2, "idtiponotificacionevento");
			return (Criteria) this;
		}

		public Criteria andCuandoIsNull() {
			addCriterion("CUANDO is null");
			return (Criteria) this;
		}

		public Criteria andCuandoIsNotNull() {
			addCriterion("CUANDO is not null");
			return (Criteria) this;
		}

		public Criteria andCuandoEqualTo(Long value) {
			addCriterion("CUANDO =", value, "cuando");
			return (Criteria) this;
		}

		public Criteria andCuandoNotEqualTo(Long value) {
			addCriterion("CUANDO <>", value, "cuando");
			return (Criteria) this;
		}

		public Criteria andCuandoGreaterThan(Long value) {
			addCriterion("CUANDO >", value, "cuando");
			return (Criteria) this;
		}

		public Criteria andCuandoGreaterThanOrEqualTo(Long value) {
			addCriterion("CUANDO >=", value, "cuando");
			return (Criteria) this;
		}

		public Criteria andCuandoLessThan(Long value) {
			addCriterion("CUANDO <", value, "cuando");
			return (Criteria) this;
		}

		public Criteria andCuandoLessThanOrEqualTo(Long value) {
			addCriterion("CUANDO <=", value, "cuando");
			return (Criteria) this;
		}

		public Criteria andCuandoIn(List<Long> values) {
			addCriterion("CUANDO in", values, "cuando");
			return (Criteria) this;
		}

		public Criteria andCuandoNotIn(List<Long> values) {
			addCriterion("CUANDO not in", values, "cuando");
			return (Criteria) this;
		}

		public Criteria andCuandoBetween(Long value1, Long value2) {
			addCriterion("CUANDO between", value1, value2, "cuando");
			return (Criteria) this;
		}

		public Criteria andCuandoNotBetween(Long value1, Long value2) {
			addCriterion("CUANDO not between", value1, value2, "cuando");
			return (Criteria) this;
		}

		public Criteria andIdtipocuandoIsNull() {
			addCriterion("IDTIPOCUANDO is null");
			return (Criteria) this;
		}

		public Criteria andIdtipocuandoIsNotNull() {
			addCriterion("IDTIPOCUANDO is not null");
			return (Criteria) this;
		}

		public Criteria andIdtipocuandoEqualTo(Long value) {
			addCriterion("IDTIPOCUANDO =", value, "idtipocuando");
			return (Criteria) this;
		}

		public Criteria andIdtipocuandoNotEqualTo(Long value) {
			addCriterion("IDTIPOCUANDO <>", value, "idtipocuando");
			return (Criteria) this;
		}

		public Criteria andIdtipocuandoGreaterThan(Long value) {
			addCriterion("IDTIPOCUANDO >", value, "idtipocuando");
			return (Criteria) this;
		}

		public Criteria andIdtipocuandoGreaterThanOrEqualTo(Long value) {
			addCriterion("IDTIPOCUANDO >=", value, "idtipocuando");
			return (Criteria) this;
		}

		public Criteria andIdtipocuandoLessThan(Long value) {
			addCriterion("IDTIPOCUANDO <", value, "idtipocuando");
			return (Criteria) this;
		}

		public Criteria andIdtipocuandoLessThanOrEqualTo(Long value) {
			addCriterion("IDTIPOCUANDO <=", value, "idtipocuando");
			return (Criteria) this;
		}

		public Criteria andIdtipocuandoIn(List<Long> values) {
			addCriterion("IDTIPOCUANDO in", values, "idtipocuando");
			return (Criteria) this;
		}

		public Criteria andIdtipocuandoNotIn(List<Long> values) {
			addCriterion("IDTIPOCUANDO not in", values, "idtipocuando");
			return (Criteria) this;
		}

		public Criteria andIdtipocuandoBetween(Long value1, Long value2) {
			addCriterion("IDTIPOCUANDO between", value1, value2, "idtipocuando");
			return (Criteria) this;
		}

		public Criteria andIdtipocuandoNotBetween(Long value1, Long value2) {
			addCriterion("IDTIPOCUANDO not between", value1, value2, "idtipocuando");
			return (Criteria) this;
		}

		public Criteria andIdunidadmedidaIsNull() {
			addCriterion("IDUNIDADMEDIDA is null");
			return (Criteria) this;
		}

		public Criteria andIdunidadmedidaIsNotNull() {
			addCriterion("IDUNIDADMEDIDA is not null");
			return (Criteria) this;
		}

		public Criteria andIdunidadmedidaEqualTo(Long value) {
			addCriterion("IDUNIDADMEDIDA =", value, "idunidadmedida");
			return (Criteria) this;
		}

		public Criteria andIdunidadmedidaNotEqualTo(Long value) {
			addCriterion("IDUNIDADMEDIDA <>", value, "idunidadmedida");
			return (Criteria) this;
		}

		public Criteria andIdunidadmedidaGreaterThan(Long value) {
			addCriterion("IDUNIDADMEDIDA >", value, "idunidadmedida");
			return (Criteria) this;
		}

		public Criteria andIdunidadmedidaGreaterThanOrEqualTo(Long value) {
			addCriterion("IDUNIDADMEDIDA >=", value, "idunidadmedida");
			return (Criteria) this;
		}

		public Criteria andIdunidadmedidaLessThan(Long value) {
			addCriterion("IDUNIDADMEDIDA <", value, "idunidadmedida");
			return (Criteria) this;
		}

		public Criteria andIdunidadmedidaLessThanOrEqualTo(Long value) {
			addCriterion("IDUNIDADMEDIDA <=", value, "idunidadmedida");
			return (Criteria) this;
		}

		public Criteria andIdunidadmedidaIn(List<Long> values) {
			addCriterion("IDUNIDADMEDIDA in", values, "idunidadmedida");
			return (Criteria) this;
		}

		public Criteria andIdunidadmedidaNotIn(List<Long> values) {
			addCriterion("IDUNIDADMEDIDA not in", values, "idunidadmedida");
			return (Criteria) this;
		}

		public Criteria andIdunidadmedidaBetween(Long value1, Long value2) {
			addCriterion("IDUNIDADMEDIDA between", value1, value2, "idunidadmedida");
			return (Criteria) this;
		}

		public Criteria andIdunidadmedidaNotBetween(Long value1, Long value2) {
			addCriterion("IDUNIDADMEDIDA not between", value1, value2, "idunidadmedida");
			return (Criteria) this;
		}

		public Criteria andIdcalendarioIsNull() {
			addCriterion("IDCALENDARIO is null");
			return (Criteria) this;
		}

		public Criteria andIdcalendarioIsNotNull() {
			addCriterion("IDCALENDARIO is not null");
			return (Criteria) this;
		}

		public Criteria andIdcalendarioEqualTo(Long value) {
			addCriterion("IDCALENDARIO =", value, "idcalendario");
			return (Criteria) this;
		}

		public Criteria andIdcalendarioNotEqualTo(Long value) {
			addCriterion("IDCALENDARIO <>", value, "idcalendario");
			return (Criteria) this;
		}

		public Criteria andIdcalendarioGreaterThan(Long value) {
			addCriterion("IDCALENDARIO >", value, "idcalendario");
			return (Criteria) this;
		}

		public Criteria andIdcalendarioGreaterThanOrEqualTo(Long value) {
			addCriterion("IDCALENDARIO >=", value, "idcalendario");
			return (Criteria) this;
		}

		public Criteria andIdcalendarioLessThan(Long value) {
			addCriterion("IDCALENDARIO <", value, "idcalendario");
			return (Criteria) this;
		}

		public Criteria andIdcalendarioLessThanOrEqualTo(Long value) {
			addCriterion("IDCALENDARIO <=", value, "idcalendario");
			return (Criteria) this;
		}

		public Criteria andIdcalendarioIn(List<Long> values) {
			addCriterion("IDCALENDARIO in", values, "idcalendario");
			return (Criteria) this;
		}

		public Criteria andIdcalendarioNotIn(List<Long> values) {
			addCriterion("IDCALENDARIO not in", values, "idcalendario");
			return (Criteria) this;
		}

		public Criteria andIdcalendarioBetween(Long value1, Long value2) {
			addCriterion("IDCALENDARIO between", value1, value2, "idcalendario");
			return (Criteria) this;
		}

		public Criteria andIdcalendarioNotBetween(Long value1, Long value2) {
			addCriterion("IDCALENDARIO not between", value1, value2, "idcalendario");
			return (Criteria) this;
		}

		public Criteria andIdeventoIsNull() {
			addCriterion("IDEVENTO is null");
			return (Criteria) this;
		}

		public Criteria andIdeventoIsNotNull() {
			addCriterion("IDEVENTO is not null");
			return (Criteria) this;
		}

		public Criteria andIdeventoEqualTo(Long value) {
			addCriterion("IDEVENTO =", value, "idevento");
			return (Criteria) this;
		}

		public Criteria andIdeventoNotEqualTo(Long value) {
			addCriterion("IDEVENTO <>", value, "idevento");
			return (Criteria) this;
		}

		public Criteria andIdeventoGreaterThan(Long value) {
			addCriterion("IDEVENTO >", value, "idevento");
			return (Criteria) this;
		}

		public Criteria andIdeventoGreaterThanOrEqualTo(Long value) {
			addCriterion("IDEVENTO >=", value, "idevento");
			return (Criteria) this;
		}

		public Criteria andIdeventoLessThan(Long value) {
			addCriterion("IDEVENTO <", value, "idevento");
			return (Criteria) this;
		}

		public Criteria andIdeventoLessThanOrEqualTo(Long value) {
			addCriterion("IDEVENTO <=", value, "idevento");
			return (Criteria) this;
		}

		public Criteria andIdeventoIn(List<Long> values) {
			addCriterion("IDEVENTO in", values, "idevento");
			return (Criteria) this;
		}

		public Criteria andIdeventoNotIn(List<Long> values) {
			addCriterion("IDEVENTO not in", values, "idevento");
			return (Criteria) this;
		}

		public Criteria andIdeventoBetween(Long value1, Long value2) {
			addCriterion("IDEVENTO between", value1, value2, "idevento");
			return (Criteria) this;
		}

		public Criteria andIdeventoNotBetween(Long value1, Long value2) {
			addCriterion("IDEVENTO not between", value1, value2, "idevento");
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

		public Criteria andIdplantillaIsNull() {
			addCriterion("IDPLANTILLA is null");
			return (Criteria) this;
		}

		public Criteria andIdplantillaIsNotNull() {
			addCriterion("IDPLANTILLA is not null");
			return (Criteria) this;
		}

		public Criteria andIdplantillaEqualTo(Long value) {
			addCriterion("IDPLANTILLA =", value, "idplantilla");
			return (Criteria) this;
		}

		public Criteria andIdplantillaNotEqualTo(Long value) {
			addCriterion("IDPLANTILLA <>", value, "idplantilla");
			return (Criteria) this;
		}

		public Criteria andIdplantillaGreaterThan(Long value) {
			addCriterion("IDPLANTILLA >", value, "idplantilla");
			return (Criteria) this;
		}

		public Criteria andIdplantillaGreaterThanOrEqualTo(Long value) {
			addCriterion("IDPLANTILLA >=", value, "idplantilla");
			return (Criteria) this;
		}

		public Criteria andIdplantillaLessThan(Long value) {
			addCriterion("IDPLANTILLA <", value, "idplantilla");
			return (Criteria) this;
		}

		public Criteria andIdplantillaLessThanOrEqualTo(Long value) {
			addCriterion("IDPLANTILLA <=", value, "idplantilla");
			return (Criteria) this;
		}

		public Criteria andIdplantillaIn(List<Long> values) {
			addCriterion("IDPLANTILLA in", values, "idplantilla");
			return (Criteria) this;
		}

		public Criteria andIdplantillaNotIn(List<Long> values) {
			addCriterion("IDPLANTILLA not in", values, "idplantilla");
			return (Criteria) this;
		}

		public Criteria andIdplantillaBetween(Long value1, Long value2) {
			addCriterion("IDPLANTILLA between", value1, value2, "idplantilla");
			return (Criteria) this;
		}

		public Criteria andIdplantillaNotBetween(Long value1, Long value2) {
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

		public Criteria andIdtipoenviosEqualTo(Long value) {
			addCriterion("IDTIPOENVIOS =", value, "idtipoenvios");
			return (Criteria) this;
		}

		public Criteria andIdtipoenviosNotEqualTo(Long value) {
			addCriterion("IDTIPOENVIOS <>", value, "idtipoenvios");
			return (Criteria) this;
		}

		public Criteria andIdtipoenviosGreaterThan(Long value) {
			addCriterion("IDTIPOENVIOS >", value, "idtipoenvios");
			return (Criteria) this;
		}

		public Criteria andIdtipoenviosGreaterThanOrEqualTo(Long value) {
			addCriterion("IDTIPOENVIOS >=", value, "idtipoenvios");
			return (Criteria) this;
		}

		public Criteria andIdtipoenviosLessThan(Long value) {
			addCriterion("IDTIPOENVIOS <", value, "idtipoenvios");
			return (Criteria) this;
		}

		public Criteria andIdtipoenviosLessThanOrEqualTo(Long value) {
			addCriterion("IDTIPOENVIOS <=", value, "idtipoenvios");
			return (Criteria) this;
		}

		public Criteria andIdtipoenviosIn(List<Long> values) {
			addCriterion("IDTIPOENVIOS in", values, "idtipoenvios");
			return (Criteria) this;
		}

		public Criteria andIdtipoenviosNotIn(List<Long> values) {
			addCriterion("IDTIPOENVIOS not in", values, "idtipoenvios");
			return (Criteria) this;
		}

		public Criteria andIdtipoenviosBetween(Long value1, Long value2) {
			addCriterion("IDTIPOENVIOS between", value1, value2, "idtipoenvios");
			return (Criteria) this;
		}

		public Criteria andIdtipoenviosNotBetween(Long value1, Long value2) {
			addCriterion("IDTIPOENVIOS not between", value1, value2, "idtipoenvios");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
	 * @mbg.generated  Tue Oct 23 17:52:28 CEST 2018
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
     * This class corresponds to the database table USCGAE.AGE_NOTIFICACIONESEVENTO
     *
     * @mbg.generated do_not_delete_during_merge Fri Oct 19 13:04:58 CEST 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}