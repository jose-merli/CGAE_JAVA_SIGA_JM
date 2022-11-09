package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScsProgCalendariosExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE_INT.SCS_PROG_CALENDARIOS
	 * @mbg.generated  Mon Nov 07 17:59:44 CET 2022
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE_INT.SCS_PROG_CALENDARIOS
	 * @mbg.generated  Mon Nov 07 17:59:44 CET 2022
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE_INT.SCS_PROG_CALENDARIOS
	 * @mbg.generated  Mon Nov 07 17:59:44 CET 2022
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.SCS_PROG_CALENDARIOS
	 * @mbg.generated  Mon Nov 07 17:59:44 CET 2022
	 */
	public ScsProgCalendariosExample() {
		oredCriteria = new ArrayList<>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.SCS_PROG_CALENDARIOS
	 * @mbg.generated  Mon Nov 07 17:59:44 CET 2022
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.SCS_PROG_CALENDARIOS
	 * @mbg.generated  Mon Nov 07 17:59:44 CET 2022
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.SCS_PROG_CALENDARIOS
	 * @mbg.generated  Mon Nov 07 17:59:44 CET 2022
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.SCS_PROG_CALENDARIOS
	 * @mbg.generated  Mon Nov 07 17:59:44 CET 2022
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.SCS_PROG_CALENDARIOS
	 * @mbg.generated  Mon Nov 07 17:59:44 CET 2022
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.SCS_PROG_CALENDARIOS
	 * @mbg.generated  Mon Nov 07 17:59:44 CET 2022
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.SCS_PROG_CALENDARIOS
	 * @mbg.generated  Mon Nov 07 17:59:44 CET 2022
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.SCS_PROG_CALENDARIOS
	 * @mbg.generated  Mon Nov 07 17:59:44 CET 2022
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.SCS_PROG_CALENDARIOS
	 * @mbg.generated  Mon Nov 07 17:59:44 CET 2022
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.SCS_PROG_CALENDARIOS
	 * @mbg.generated  Mon Nov 07 17:59:44 CET 2022
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE_INT.SCS_PROG_CALENDARIOS
	 * @mbg.generated  Mon Nov 07 17:59:44 CET 2022
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

		public Criteria andIdprogcalendarioIsNull() {
			addCriterion("IDPROGCALENDARIO is null");
			return (Criteria) this;
		}

		public Criteria andIdprogcalendarioIsNotNull() {
			addCriterion("IDPROGCALENDARIO is not null");
			return (Criteria) this;
		}

		public Criteria andIdprogcalendarioEqualTo(Long value) {
			addCriterion("IDPROGCALENDARIO =", value, "idprogcalendario");
			return (Criteria) this;
		}

		public Criteria andIdprogcalendarioNotEqualTo(Long value) {
			addCriterion("IDPROGCALENDARIO <>", value, "idprogcalendario");
			return (Criteria) this;
		}

		public Criteria andIdprogcalendarioGreaterThan(Long value) {
			addCriterion("IDPROGCALENDARIO >", value, "idprogcalendario");
			return (Criteria) this;
		}

		public Criteria andIdprogcalendarioGreaterThanOrEqualTo(Long value) {
			addCriterion("IDPROGCALENDARIO >=", value, "idprogcalendario");
			return (Criteria) this;
		}

		public Criteria andIdprogcalendarioLessThan(Long value) {
			addCriterion("IDPROGCALENDARIO <", value, "idprogcalendario");
			return (Criteria) this;
		}

		public Criteria andIdprogcalendarioLessThanOrEqualTo(Long value) {
			addCriterion("IDPROGCALENDARIO <=", value, "idprogcalendario");
			return (Criteria) this;
		}

		public Criteria andIdprogcalendarioIn(List<Long> values) {
			addCriterion("IDPROGCALENDARIO in", values, "idprogcalendario");
			return (Criteria) this;
		}

		public Criteria andIdprogcalendarioNotIn(List<Long> values) {
			addCriterion("IDPROGCALENDARIO not in", values, "idprogcalendario");
			return (Criteria) this;
		}

		public Criteria andIdprogcalendarioBetween(Long value1, Long value2) {
			addCriterion("IDPROGCALENDARIO between", value1, value2, "idprogcalendario");
			return (Criteria) this;
		}

		public Criteria andIdprogcalendarioNotBetween(Long value1, Long value2) {
			addCriterion("IDPROGCALENDARIO not between", value1, value2, "idprogcalendario");
			return (Criteria) this;
		}

		public Criteria andIdconjuntoguardiaIsNull() {
			addCriterion("IDCONJUNTOGUARDIA is null");
			return (Criteria) this;
		}

		public Criteria andIdconjuntoguardiaIsNotNull() {
			addCriterion("IDCONJUNTOGUARDIA is not null");
			return (Criteria) this;
		}

		public Criteria andIdconjuntoguardiaEqualTo(Short value) {
			addCriterion("IDCONJUNTOGUARDIA =", value, "idconjuntoguardia");
			return (Criteria) this;
		}

		public Criteria andIdconjuntoguardiaNotEqualTo(Short value) {
			addCriterion("IDCONJUNTOGUARDIA <>", value, "idconjuntoguardia");
			return (Criteria) this;
		}

		public Criteria andIdconjuntoguardiaGreaterThan(Short value) {
			addCriterion("IDCONJUNTOGUARDIA >", value, "idconjuntoguardia");
			return (Criteria) this;
		}

		public Criteria andIdconjuntoguardiaGreaterThanOrEqualTo(Short value) {
			addCriterion("IDCONJUNTOGUARDIA >=", value, "idconjuntoguardia");
			return (Criteria) this;
		}

		public Criteria andIdconjuntoguardiaLessThan(Short value) {
			addCriterion("IDCONJUNTOGUARDIA <", value, "idconjuntoguardia");
			return (Criteria) this;
		}

		public Criteria andIdconjuntoguardiaLessThanOrEqualTo(Short value) {
			addCriterion("IDCONJUNTOGUARDIA <=", value, "idconjuntoguardia");
			return (Criteria) this;
		}

		public Criteria andIdconjuntoguardiaIn(List<Short> values) {
			addCriterion("IDCONJUNTOGUARDIA in", values, "idconjuntoguardia");
			return (Criteria) this;
		}

		public Criteria andIdconjuntoguardiaNotIn(List<Short> values) {
			addCriterion("IDCONJUNTOGUARDIA not in", values, "idconjuntoguardia");
			return (Criteria) this;
		}

		public Criteria andIdconjuntoguardiaBetween(Short value1, Short value2) {
			addCriterion("IDCONJUNTOGUARDIA between", value1, value2, "idconjuntoguardia");
			return (Criteria) this;
		}

		public Criteria andIdconjuntoguardiaNotBetween(Short value1, Short value2) {
			addCriterion("IDCONJUNTOGUARDIA not between", value1, value2, "idconjuntoguardia");
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

		public Criteria andFechaprogramacionIsNull() {
			addCriterion("FECHAPROGRAMACION is null");
			return (Criteria) this;
		}

		public Criteria andFechaprogramacionIsNotNull() {
			addCriterion("FECHAPROGRAMACION is not null");
			return (Criteria) this;
		}

		public Criteria andFechaprogramacionEqualTo(Date value) {
			addCriterion("FECHAPROGRAMACION =", value, "fechaprogramacion");
			return (Criteria) this;
		}

		public Criteria andFechaprogramacionNotEqualTo(Date value) {
			addCriterion("FECHAPROGRAMACION <>", value, "fechaprogramacion");
			return (Criteria) this;
		}

		public Criteria andFechaprogramacionGreaterThan(Date value) {
			addCriterion("FECHAPROGRAMACION >", value, "fechaprogramacion");
			return (Criteria) this;
		}

		public Criteria andFechaprogramacionGreaterThanOrEqualTo(Date value) {
			addCriterion("FECHAPROGRAMACION >=", value, "fechaprogramacion");
			return (Criteria) this;
		}

		public Criteria andFechaprogramacionLessThan(Date value) {
			addCriterion("FECHAPROGRAMACION <", value, "fechaprogramacion");
			return (Criteria) this;
		}

		public Criteria andFechaprogramacionLessThanOrEqualTo(Date value) {
			addCriterion("FECHAPROGRAMACION <=", value, "fechaprogramacion");
			return (Criteria) this;
		}

		public Criteria andFechaprogramacionIn(List<Date> values) {
			addCriterion("FECHAPROGRAMACION in", values, "fechaprogramacion");
			return (Criteria) this;
		}

		public Criteria andFechaprogramacionNotIn(List<Date> values) {
			addCriterion("FECHAPROGRAMACION not in", values, "fechaprogramacion");
			return (Criteria) this;
		}

		public Criteria andFechaprogramacionBetween(Date value1, Date value2) {
			addCriterion("FECHAPROGRAMACION between", value1, value2, "fechaprogramacion");
			return (Criteria) this;
		}

		public Criteria andFechaprogramacionNotBetween(Date value1, Date value2) {
			addCriterion("FECHAPROGRAMACION not between", value1, value2, "fechaprogramacion");
			return (Criteria) this;
		}

		public Criteria andFechacalinicioIsNull() {
			addCriterion("FECHACALINICIO is null");
			return (Criteria) this;
		}

		public Criteria andFechacalinicioIsNotNull() {
			addCriterion("FECHACALINICIO is not null");
			return (Criteria) this;
		}

		public Criteria andFechacalinicioEqualTo(Date value) {
			addCriterion("FECHACALINICIO =", value, "fechacalinicio");
			return (Criteria) this;
		}

		public Criteria andFechacalinicioNotEqualTo(Date value) {
			addCriterion("FECHACALINICIO <>", value, "fechacalinicio");
			return (Criteria) this;
		}

		public Criteria andFechacalinicioGreaterThan(Date value) {
			addCriterion("FECHACALINICIO >", value, "fechacalinicio");
			return (Criteria) this;
		}

		public Criteria andFechacalinicioGreaterThanOrEqualTo(Date value) {
			addCriterion("FECHACALINICIO >=", value, "fechacalinicio");
			return (Criteria) this;
		}

		public Criteria andFechacalinicioLessThan(Date value) {
			addCriterion("FECHACALINICIO <", value, "fechacalinicio");
			return (Criteria) this;
		}

		public Criteria andFechacalinicioLessThanOrEqualTo(Date value) {
			addCriterion("FECHACALINICIO <=", value, "fechacalinicio");
			return (Criteria) this;
		}

		public Criteria andFechacalinicioIn(List<Date> values) {
			addCriterion("FECHACALINICIO in", values, "fechacalinicio");
			return (Criteria) this;
		}

		public Criteria andFechacalinicioNotIn(List<Date> values) {
			addCriterion("FECHACALINICIO not in", values, "fechacalinicio");
			return (Criteria) this;
		}

		public Criteria andFechacalinicioBetween(Date value1, Date value2) {
			addCriterion("FECHACALINICIO between", value1, value2, "fechacalinicio");
			return (Criteria) this;
		}

		public Criteria andFechacalinicioNotBetween(Date value1, Date value2) {
			addCriterion("FECHACALINICIO not between", value1, value2, "fechacalinicio");
			return (Criteria) this;
		}

		public Criteria andFechacalfinIsNull() {
			addCriterion("FECHACALFIN is null");
			return (Criteria) this;
		}

		public Criteria andFechacalfinIsNotNull() {
			addCriterion("FECHACALFIN is not null");
			return (Criteria) this;
		}

		public Criteria andFechacalfinEqualTo(Date value) {
			addCriterion("FECHACALFIN =", value, "fechacalfin");
			return (Criteria) this;
		}

		public Criteria andFechacalfinNotEqualTo(Date value) {
			addCriterion("FECHACALFIN <>", value, "fechacalfin");
			return (Criteria) this;
		}

		public Criteria andFechacalfinGreaterThan(Date value) {
			addCriterion("FECHACALFIN >", value, "fechacalfin");
			return (Criteria) this;
		}

		public Criteria andFechacalfinGreaterThanOrEqualTo(Date value) {
			addCriterion("FECHACALFIN >=", value, "fechacalfin");
			return (Criteria) this;
		}

		public Criteria andFechacalfinLessThan(Date value) {
			addCriterion("FECHACALFIN <", value, "fechacalfin");
			return (Criteria) this;
		}

		public Criteria andFechacalfinLessThanOrEqualTo(Date value) {
			addCriterion("FECHACALFIN <=", value, "fechacalfin");
			return (Criteria) this;
		}

		public Criteria andFechacalfinIn(List<Date> values) {
			addCriterion("FECHACALFIN in", values, "fechacalfin");
			return (Criteria) this;
		}

		public Criteria andFechacalfinNotIn(List<Date> values) {
			addCriterion("FECHACALFIN not in", values, "fechacalfin");
			return (Criteria) this;
		}

		public Criteria andFechacalfinBetween(Date value1, Date value2) {
			addCriterion("FECHACALFIN between", value1, value2, "fechacalfin");
			return (Criteria) this;
		}

		public Criteria andFechacalfinNotBetween(Date value1, Date value2) {
			addCriterion("FECHACALFIN not between", value1, value2, "fechacalfin");
			return (Criteria) this;
		}

		public Criteria andEstadoIsNull() {
			addCriterion("ESTADO is null");
			return (Criteria) this;
		}

		public Criteria andEstadoIsNotNull() {
			addCriterion("ESTADO is not null");
			return (Criteria) this;
		}

		public Criteria andEstadoEqualTo(Short value) {
			addCriterion("ESTADO =", value, "estado");
			return (Criteria) this;
		}

		public Criteria andEstadoNotEqualTo(Short value) {
			addCriterion("ESTADO <>", value, "estado");
			return (Criteria) this;
		}

		public Criteria andEstadoGreaterThan(Short value) {
			addCriterion("ESTADO >", value, "estado");
			return (Criteria) this;
		}

		public Criteria andEstadoGreaterThanOrEqualTo(Short value) {
			addCriterion("ESTADO >=", value, "estado");
			return (Criteria) this;
		}

		public Criteria andEstadoLessThan(Short value) {
			addCriterion("ESTADO <", value, "estado");
			return (Criteria) this;
		}

		public Criteria andEstadoLessThanOrEqualTo(Short value) {
			addCriterion("ESTADO <=", value, "estado");
			return (Criteria) this;
		}

		public Criteria andEstadoIn(List<Short> values) {
			addCriterion("ESTADO in", values, "estado");
			return (Criteria) this;
		}

		public Criteria andEstadoNotIn(List<Short> values) {
			addCriterion("ESTADO not in", values, "estado");
			return (Criteria) this;
		}

		public Criteria andEstadoBetween(Short value1, Short value2) {
			addCriterion("ESTADO between", value1, value2, "estado");
			return (Criteria) this;
		}

		public Criteria andEstadoNotBetween(Short value1, Short value2) {
			addCriterion("ESTADO not between", value1, value2, "estado");
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

		public Criteria andIdficherocalendarioIsNull() {
			addCriterion("IDFICHEROCALENDARIO is null");
			return (Criteria) this;
		}

		public Criteria andIdficherocalendarioIsNotNull() {
			addCriterion("IDFICHEROCALENDARIO is not null");
			return (Criteria) this;
		}

		public Criteria andIdficherocalendarioEqualTo(Long value) {
			addCriterion("IDFICHEROCALENDARIO =", value, "idficherocalendario");
			return (Criteria) this;
		}

		public Criteria andIdficherocalendarioNotEqualTo(Long value) {
			addCriterion("IDFICHEROCALENDARIO <>", value, "idficherocalendario");
			return (Criteria) this;
		}

		public Criteria andIdficherocalendarioGreaterThan(Long value) {
			addCriterion("IDFICHEROCALENDARIO >", value, "idficherocalendario");
			return (Criteria) this;
		}

		public Criteria andIdficherocalendarioGreaterThanOrEqualTo(Long value) {
			addCriterion("IDFICHEROCALENDARIO >=", value, "idficherocalendario");
			return (Criteria) this;
		}

		public Criteria andIdficherocalendarioLessThan(Long value) {
			addCriterion("IDFICHEROCALENDARIO <", value, "idficherocalendario");
			return (Criteria) this;
		}

		public Criteria andIdficherocalendarioLessThanOrEqualTo(Long value) {
			addCriterion("IDFICHEROCALENDARIO <=", value, "idficherocalendario");
			return (Criteria) this;
		}

		public Criteria andIdficherocalendarioIn(List<Long> values) {
			addCriterion("IDFICHEROCALENDARIO in", values, "idficherocalendario");
			return (Criteria) this;
		}

		public Criteria andIdficherocalendarioNotIn(List<Long> values) {
			addCriterion("IDFICHEROCALENDARIO not in", values, "idficherocalendario");
			return (Criteria) this;
		}

		public Criteria andIdficherocalendarioBetween(Long value1, Long value2) {
			addCriterion("IDFICHEROCALENDARIO between", value1, value2, "idficherocalendario");
			return (Criteria) this;
		}

		public Criteria andIdficherocalendarioNotBetween(Long value1, Long value2) {
			addCriterion("IDFICHEROCALENDARIO not between", value1, value2, "idficherocalendario");
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

		public Criteria andProcesandogeneracionIsNull() {
			addCriterion("PROCESANDOGENERACION is null");
			return (Criteria) this;
		}

		public Criteria andProcesandogeneracionIsNotNull() {
			addCriterion("PROCESANDOGENERACION is not null");
			return (Criteria) this;
		}

		public Criteria andProcesandogeneracionEqualTo(Short value) {
			addCriterion("PROCESANDOGENERACION =", value, "procesandogeneracion");
			return (Criteria) this;
		}

		public Criteria andProcesandogeneracionNotEqualTo(Short value) {
			addCriterion("PROCESANDOGENERACION <>", value, "procesandogeneracion");
			return (Criteria) this;
		}

		public Criteria andProcesandogeneracionGreaterThan(Short value) {
			addCriterion("PROCESANDOGENERACION >", value, "procesandogeneracion");
			return (Criteria) this;
		}

		public Criteria andProcesandogeneracionGreaterThanOrEqualTo(Short value) {
			addCriterion("PROCESANDOGENERACION >=", value, "procesandogeneracion");
			return (Criteria) this;
		}

		public Criteria andProcesandogeneracionLessThan(Short value) {
			addCriterion("PROCESANDOGENERACION <", value, "procesandogeneracion");
			return (Criteria) this;
		}

		public Criteria andProcesandogeneracionLessThanOrEqualTo(Short value) {
			addCriterion("PROCESANDOGENERACION <=", value, "procesandogeneracion");
			return (Criteria) this;
		}

		public Criteria andProcesandogeneracionIn(List<Short> values) {
			addCriterion("PROCESANDOGENERACION in", values, "procesandogeneracion");
			return (Criteria) this;
		}

		public Criteria andProcesandogeneracionNotIn(List<Short> values) {
			addCriterion("PROCESANDOGENERACION not in", values, "procesandogeneracion");
			return (Criteria) this;
		}

		public Criteria andProcesandogeneracionBetween(Short value1, Short value2) {
			addCriterion("PROCESANDOGENERACION between", value1, value2, "procesandogeneracion");
			return (Criteria) this;
		}

		public Criteria andProcesandogeneracionNotBetween(Short value1, Short value2) {
			addCriterion("PROCESANDOGENERACION not between", value1, value2, "procesandogeneracion");
			return (Criteria) this;
		}

		public Criteria andLogProgramacionNameIsNull() {
			addCriterion("LOG_PROGRAMACION_NAME is null");
			return (Criteria) this;
		}

		public Criteria andLogProgramacionNameIsNotNull() {
			addCriterion("LOG_PROGRAMACION_NAME is not null");
			return (Criteria) this;
		}

		public Criteria andLogProgramacionNameEqualTo(String value) {
			addCriterion("LOG_PROGRAMACION_NAME =", value, "logProgramacionName");
			return (Criteria) this;
		}

		public Criteria andLogProgramacionNameNotEqualTo(String value) {
			addCriterion("LOG_PROGRAMACION_NAME <>", value, "logProgramacionName");
			return (Criteria) this;
		}

		public Criteria andLogProgramacionNameGreaterThan(String value) {
			addCriterion("LOG_PROGRAMACION_NAME >", value, "logProgramacionName");
			return (Criteria) this;
		}

		public Criteria andLogProgramacionNameGreaterThanOrEqualTo(String value) {
			addCriterion("LOG_PROGRAMACION_NAME >=", value, "logProgramacionName");
			return (Criteria) this;
		}

		public Criteria andLogProgramacionNameLessThan(String value) {
			addCriterion("LOG_PROGRAMACION_NAME <", value, "logProgramacionName");
			return (Criteria) this;
		}

		public Criteria andLogProgramacionNameLessThanOrEqualTo(String value) {
			addCriterion("LOG_PROGRAMACION_NAME <=", value, "logProgramacionName");
			return (Criteria) this;
		}

		public Criteria andLogProgramacionNameLike(String value) {
			addCriterion("LOG_PROGRAMACION_NAME like", value, "logProgramacionName");
			return (Criteria) this;
		}

		public Criteria andLogProgramacionNameNotLike(String value) {
			addCriterion("LOG_PROGRAMACION_NAME not like", value, "logProgramacionName");
			return (Criteria) this;
		}

		public Criteria andLogProgramacionNameIn(List<String> values) {
			addCriterion("LOG_PROGRAMACION_NAME in", values, "logProgramacionName");
			return (Criteria) this;
		}

		public Criteria andLogProgramacionNameNotIn(List<String> values) {
			addCriterion("LOG_PROGRAMACION_NAME not in", values, "logProgramacionName");
			return (Criteria) this;
		}

		public Criteria andLogProgramacionNameBetween(String value1, String value2) {
			addCriterion("LOG_PROGRAMACION_NAME between", value1, value2, "logProgramacionName");
			return (Criteria) this;
		}

		public Criteria andLogProgramacionNameNotBetween(String value1, String value2) {
			addCriterion("LOG_PROGRAMACION_NAME not between", value1, value2, "logProgramacionName");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE_INT.SCS_PROG_CALENDARIOS
	 * @mbg.generated  Mon Nov 07 17:59:44 CET 2022
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
     * This class corresponds to the database table USCGAE.SCS_PROG_CALENDARIOS
     *
     * @mbg.generated do_not_delete_during_merge Thu Oct 28 17:42:33 CEST 2021
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }
}