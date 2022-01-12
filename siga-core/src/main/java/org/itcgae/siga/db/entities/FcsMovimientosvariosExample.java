package org.itcgae.siga.db.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FcsMovimientosvariosExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE.FCS_MOVIMIENTOSVARIOS
	 * @mbg.generated  Fri Nov 19 11:15:18 CET 2021
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE.FCS_MOVIMIENTOSVARIOS
	 * @mbg.generated  Fri Nov 19 11:15:18 CET 2021
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE.FCS_MOVIMIENTOSVARIOS
	 * @mbg.generated  Fri Nov 19 11:15:18 CET 2021
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_MOVIMIENTOSVARIOS
	 * @mbg.generated  Fri Nov 19 11:15:18 CET 2021
	 */
	public FcsMovimientosvariosExample() {
		oredCriteria = new ArrayList<>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_MOVIMIENTOSVARIOS
	 * @mbg.generated  Fri Nov 19 11:15:18 CET 2021
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_MOVIMIENTOSVARIOS
	 * @mbg.generated  Fri Nov 19 11:15:18 CET 2021
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_MOVIMIENTOSVARIOS
	 * @mbg.generated  Fri Nov 19 11:15:18 CET 2021
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_MOVIMIENTOSVARIOS
	 * @mbg.generated  Fri Nov 19 11:15:18 CET 2021
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_MOVIMIENTOSVARIOS
	 * @mbg.generated  Fri Nov 19 11:15:18 CET 2021
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_MOVIMIENTOSVARIOS
	 * @mbg.generated  Fri Nov 19 11:15:18 CET 2021
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_MOVIMIENTOSVARIOS
	 * @mbg.generated  Fri Nov 19 11:15:18 CET 2021
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_MOVIMIENTOSVARIOS
	 * @mbg.generated  Fri Nov 19 11:15:18 CET 2021
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_MOVIMIENTOSVARIOS
	 * @mbg.generated  Fri Nov 19 11:15:18 CET 2021
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FCS_MOVIMIENTOSVARIOS
	 * @mbg.generated  Fri Nov 19 11:15:18 CET 2021
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE.FCS_MOVIMIENTOSVARIOS
	 * @mbg.generated  Fri Nov 19 11:15:18 CET 2021
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

		public Criteria andIdmovimientoIsNull() {
			addCriterion("IDMOVIMIENTO is null");
			return (Criteria) this;
		}

		public Criteria andIdmovimientoIsNotNull() {
			addCriterion("IDMOVIMIENTO is not null");
			return (Criteria) this;
		}

		public Criteria andIdmovimientoEqualTo(Long value) {
			addCriterion("IDMOVIMIENTO =", value, "idmovimiento");
			return (Criteria) this;
		}

		public Criteria andIdmovimientoNotEqualTo(Long value) {
			addCriterion("IDMOVIMIENTO <>", value, "idmovimiento");
			return (Criteria) this;
		}

		public Criteria andIdmovimientoGreaterThan(Long value) {
			addCriterion("IDMOVIMIENTO >", value, "idmovimiento");
			return (Criteria) this;
		}

		public Criteria andIdmovimientoGreaterThanOrEqualTo(Long value) {
			addCriterion("IDMOVIMIENTO >=", value, "idmovimiento");
			return (Criteria) this;
		}

		public Criteria andIdmovimientoLessThan(Long value) {
			addCriterion("IDMOVIMIENTO <", value, "idmovimiento");
			return (Criteria) this;
		}

		public Criteria andIdmovimientoLessThanOrEqualTo(Long value) {
			addCriterion("IDMOVIMIENTO <=", value, "idmovimiento");
			return (Criteria) this;
		}

		public Criteria andIdmovimientoIn(List<Long> values) {
			addCriterion("IDMOVIMIENTO in", values, "idmovimiento");
			return (Criteria) this;
		}

		public Criteria andIdmovimientoNotIn(List<Long> values) {
			addCriterion("IDMOVIMIENTO not in", values, "idmovimiento");
			return (Criteria) this;
		}

		public Criteria andIdmovimientoBetween(Long value1, Long value2) {
			addCriterion("IDMOVIMIENTO between", value1, value2, "idmovimiento");
			return (Criteria) this;
		}

		public Criteria andIdmovimientoNotBetween(Long value1, Long value2) {
			addCriterion("IDMOVIMIENTO not between", value1, value2, "idmovimiento");
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

		public Criteria andMotivoIsNull() {
			addCriterion("MOTIVO is null");
			return (Criteria) this;
		}

		public Criteria andMotivoIsNotNull() {
			addCriterion("MOTIVO is not null");
			return (Criteria) this;
		}

		public Criteria andMotivoEqualTo(String value) {
			addCriterion("MOTIVO =", value, "motivo");
			return (Criteria) this;
		}

		public Criteria andMotivoNotEqualTo(String value) {
			addCriterion("MOTIVO <>", value, "motivo");
			return (Criteria) this;
		}

		public Criteria andMotivoGreaterThan(String value) {
			addCriterion("MOTIVO >", value, "motivo");
			return (Criteria) this;
		}

		public Criteria andMotivoGreaterThanOrEqualTo(String value) {
			addCriterion("MOTIVO >=", value, "motivo");
			return (Criteria) this;
		}

		public Criteria andMotivoLessThan(String value) {
			addCriterion("MOTIVO <", value, "motivo");
			return (Criteria) this;
		}

		public Criteria andMotivoLessThanOrEqualTo(String value) {
			addCriterion("MOTIVO <=", value, "motivo");
			return (Criteria) this;
		}

		public Criteria andMotivoLike(String value) {
			addCriterion("MOTIVO like", value, "motivo");
			return (Criteria) this;
		}

		public Criteria andMotivoNotLike(String value) {
			addCriterion("MOTIVO not like", value, "motivo");
			return (Criteria) this;
		}

		public Criteria andMotivoIn(List<String> values) {
			addCriterion("MOTIVO in", values, "motivo");
			return (Criteria) this;
		}

		public Criteria andMotivoNotIn(List<String> values) {
			addCriterion("MOTIVO not in", values, "motivo");
			return (Criteria) this;
		}

		public Criteria andMotivoBetween(String value1, String value2) {
			addCriterion("MOTIVO between", value1, value2, "motivo");
			return (Criteria) this;
		}

		public Criteria andMotivoNotBetween(String value1, String value2) {
			addCriterion("MOTIVO not between", value1, value2, "motivo");
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

		public Criteria andCantidadIsNull() {
			addCriterion("CANTIDAD is null");
			return (Criteria) this;
		}

		public Criteria andCantidadIsNotNull() {
			addCriterion("CANTIDAD is not null");
			return (Criteria) this;
		}

		public Criteria andCantidadEqualTo(BigDecimal value) {
			addCriterion("CANTIDAD =", value, "cantidad");
			return (Criteria) this;
		}

		public Criteria andCantidadNotEqualTo(BigDecimal value) {
			addCriterion("CANTIDAD <>", value, "cantidad");
			return (Criteria) this;
		}

		public Criteria andCantidadGreaterThan(BigDecimal value) {
			addCriterion("CANTIDAD >", value, "cantidad");
			return (Criteria) this;
		}

		public Criteria andCantidadGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("CANTIDAD >=", value, "cantidad");
			return (Criteria) this;
		}

		public Criteria andCantidadLessThan(BigDecimal value) {
			addCriterion("CANTIDAD <", value, "cantidad");
			return (Criteria) this;
		}

		public Criteria andCantidadLessThanOrEqualTo(BigDecimal value) {
			addCriterion("CANTIDAD <=", value, "cantidad");
			return (Criteria) this;
		}

		public Criteria andCantidadIn(List<BigDecimal> values) {
			addCriterion("CANTIDAD in", values, "cantidad");
			return (Criteria) this;
		}

		public Criteria andCantidadNotIn(List<BigDecimal> values) {
			addCriterion("CANTIDAD not in", values, "cantidad");
			return (Criteria) this;
		}

		public Criteria andCantidadBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("CANTIDAD between", value1, value2, "cantidad");
			return (Criteria) this;
		}

		public Criteria andCantidadNotBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("CANTIDAD not between", value1, value2, "cantidad");
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

		public Criteria andIdtipomovimientoIsNull() {
			addCriterion("IDTIPOMOVIMIENTO is null");
			return (Criteria) this;
		}

		public Criteria andIdtipomovimientoIsNotNull() {
			addCriterion("IDTIPOMOVIMIENTO is not null");
			return (Criteria) this;
		}

		public Criteria andIdtipomovimientoEqualTo(Short value) {
			addCriterion("IDTIPOMOVIMIENTO =", value, "idtipomovimiento");
			return (Criteria) this;
		}

		public Criteria andIdtipomovimientoNotEqualTo(Short value) {
			addCriterion("IDTIPOMOVIMIENTO <>", value, "idtipomovimiento");
			return (Criteria) this;
		}

		public Criteria andIdtipomovimientoGreaterThan(Short value) {
			addCriterion("IDTIPOMOVIMIENTO >", value, "idtipomovimiento");
			return (Criteria) this;
		}

		public Criteria andIdtipomovimientoGreaterThanOrEqualTo(Short value) {
			addCriterion("IDTIPOMOVIMIENTO >=", value, "idtipomovimiento");
			return (Criteria) this;
		}

		public Criteria andIdtipomovimientoLessThan(Short value) {
			addCriterion("IDTIPOMOVIMIENTO <", value, "idtipomovimiento");
			return (Criteria) this;
		}

		public Criteria andIdtipomovimientoLessThanOrEqualTo(Short value) {
			addCriterion("IDTIPOMOVIMIENTO <=", value, "idtipomovimiento");
			return (Criteria) this;
		}

		public Criteria andIdtipomovimientoIn(List<Short> values) {
			addCriterion("IDTIPOMOVIMIENTO in", values, "idtipomovimiento");
			return (Criteria) this;
		}

		public Criteria andIdtipomovimientoNotIn(List<Short> values) {
			addCriterion("IDTIPOMOVIMIENTO not in", values, "idtipomovimiento");
			return (Criteria) this;
		}

		public Criteria andIdtipomovimientoBetween(Short value1, Short value2) {
			addCriterion("IDTIPOMOVIMIENTO between", value1, value2, "idtipomovimiento");
			return (Criteria) this;
		}

		public Criteria andIdtipomovimientoNotBetween(Short value1, Short value2) {
			addCriterion("IDTIPOMOVIMIENTO not between", value1, value2, "idtipomovimiento");
			return (Criteria) this;
		}

		public Criteria andIdpartidapresupuestariaIsNull() {
			addCriterion("IDPARTIDAPRESUPUESTARIA is null");
			return (Criteria) this;
		}

		public Criteria andIdpartidapresupuestariaIsNotNull() {
			addCriterion("IDPARTIDAPRESUPUESTARIA is not null");
			return (Criteria) this;
		}

		public Criteria andIdpartidapresupuestariaEqualTo(Integer value) {
			addCriterion("IDPARTIDAPRESUPUESTARIA =", value, "idpartidapresupuestaria");
			return (Criteria) this;
		}

		public Criteria andIdpartidapresupuestariaNotEqualTo(Integer value) {
			addCriterion("IDPARTIDAPRESUPUESTARIA <>", value, "idpartidapresupuestaria");
			return (Criteria) this;
		}

		public Criteria andIdpartidapresupuestariaGreaterThan(Integer value) {
			addCriterion("IDPARTIDAPRESUPUESTARIA >", value, "idpartidapresupuestaria");
			return (Criteria) this;
		}

		public Criteria andIdpartidapresupuestariaGreaterThanOrEqualTo(Integer value) {
			addCriterion("IDPARTIDAPRESUPUESTARIA >=", value, "idpartidapresupuestaria");
			return (Criteria) this;
		}

		public Criteria andIdpartidapresupuestariaLessThan(Integer value) {
			addCriterion("IDPARTIDAPRESUPUESTARIA <", value, "idpartidapresupuestaria");
			return (Criteria) this;
		}

		public Criteria andIdpartidapresupuestariaLessThanOrEqualTo(Integer value) {
			addCriterion("IDPARTIDAPRESUPUESTARIA <=", value, "idpartidapresupuestaria");
			return (Criteria) this;
		}

		public Criteria andIdpartidapresupuestariaIn(List<Integer> values) {
			addCriterion("IDPARTIDAPRESUPUESTARIA in", values, "idpartidapresupuestaria");
			return (Criteria) this;
		}

		public Criteria andIdpartidapresupuestariaNotIn(List<Integer> values) {
			addCriterion("IDPARTIDAPRESUPUESTARIA not in", values, "idpartidapresupuestaria");
			return (Criteria) this;
		}

		public Criteria andIdpartidapresupuestariaBetween(Integer value1, Integer value2) {
			addCriterion("IDPARTIDAPRESUPUESTARIA between", value1, value2, "idpartidapresupuestaria");
			return (Criteria) this;
		}

		public Criteria andIdpartidapresupuestariaNotBetween(Integer value1, Integer value2) {
			addCriterion("IDPARTIDAPRESUPUESTARIA not between", value1, value2, "idpartidapresupuestaria");
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
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE.FCS_MOVIMIENTOSVARIOS
	 * @mbg.generated  Fri Nov 19 11:15:18 CET 2021
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
     * This class corresponds to the database table USCGAE.FCS_MOVIMIENTOSVARIOS
     *
     * @mbg.generated do_not_delete_during_merge Thu Dec 26 23:53:13 CET 2019
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }
}