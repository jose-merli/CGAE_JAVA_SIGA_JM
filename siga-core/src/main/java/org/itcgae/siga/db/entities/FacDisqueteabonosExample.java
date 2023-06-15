package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FacDisqueteabonosExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE_INT.FAC_DISQUETEABONOS
	 * @mbg.generated  Tue Jan 18 16:13:17 CET 2022
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE_INT.FAC_DISQUETEABONOS
	 * @mbg.generated  Tue Jan 18 16:13:17 CET 2022
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE_INT.FAC_DISQUETEABONOS
	 * @mbg.generated  Tue Jan 18 16:13:17 CET 2022
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.FAC_DISQUETEABONOS
	 * @mbg.generated  Tue Jan 18 16:13:17 CET 2022
	 */
	public FacDisqueteabonosExample() {
		oredCriteria = new ArrayList<>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.FAC_DISQUETEABONOS
	 * @mbg.generated  Tue Jan 18 16:13:17 CET 2022
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.FAC_DISQUETEABONOS
	 * @mbg.generated  Tue Jan 18 16:13:17 CET 2022
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.FAC_DISQUETEABONOS
	 * @mbg.generated  Tue Jan 18 16:13:17 CET 2022
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.FAC_DISQUETEABONOS
	 * @mbg.generated  Tue Jan 18 16:13:17 CET 2022
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.FAC_DISQUETEABONOS
	 * @mbg.generated  Tue Jan 18 16:13:17 CET 2022
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.FAC_DISQUETEABONOS
	 * @mbg.generated  Tue Jan 18 16:13:17 CET 2022
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.FAC_DISQUETEABONOS
	 * @mbg.generated  Tue Jan 18 16:13:17 CET 2022
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.FAC_DISQUETEABONOS
	 * @mbg.generated  Tue Jan 18 16:13:17 CET 2022
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.FAC_DISQUETEABONOS
	 * @mbg.generated  Tue Jan 18 16:13:17 CET 2022
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.FAC_DISQUETEABONOS
	 * @mbg.generated  Tue Jan 18 16:13:17 CET 2022
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE_INT.FAC_DISQUETEABONOS
	 * @mbg.generated  Tue Jan 18 16:13:17 CET 2022
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

		public Criteria andIddisqueteabonoIsNull() {
			addCriterion("IDDISQUETEABONO is null");
			return (Criteria) this;
		}

		public Criteria andIddisqueteabonoIsNotNull() {
			addCriterion("IDDISQUETEABONO is not null");
			return (Criteria) this;
		}

		public Criteria andIddisqueteabonoEqualTo(Long value) {
			addCriterion("IDDISQUETEABONO =", value, "iddisqueteabono");
			return (Criteria) this;
		}

		public Criteria andIddisqueteabonoNotEqualTo(Long value) {
			addCriterion("IDDISQUETEABONO <>", value, "iddisqueteabono");
			return (Criteria) this;
		}

		public Criteria andIddisqueteabonoGreaterThan(Long value) {
			addCriterion("IDDISQUETEABONO >", value, "iddisqueteabono");
			return (Criteria) this;
		}

		public Criteria andIddisqueteabonoGreaterThanOrEqualTo(Long value) {
			addCriterion("IDDISQUETEABONO >=", value, "iddisqueteabono");
			return (Criteria) this;
		}

		public Criteria andIddisqueteabonoLessThan(Long value) {
			addCriterion("IDDISQUETEABONO <", value, "iddisqueteabono");
			return (Criteria) this;
		}

		public Criteria andIddisqueteabonoLessThanOrEqualTo(Long value) {
			addCriterion("IDDISQUETEABONO <=", value, "iddisqueteabono");
			return (Criteria) this;
		}

		public Criteria andIddisqueteabonoIn(List<Long> values) {
			addCriterion("IDDISQUETEABONO in", values, "iddisqueteabono");
			return (Criteria) this;
		}

		public Criteria andIddisqueteabonoNotIn(List<Long> values) {
			addCriterion("IDDISQUETEABONO not in", values, "iddisqueteabono");
			return (Criteria) this;
		}

		public Criteria andIddisqueteabonoBetween(Long value1, Long value2) {
			addCriterion("IDDISQUETEABONO between", value1, value2, "iddisqueteabono");
			return (Criteria) this;
		}

		public Criteria andIddisqueteabonoNotBetween(Long value1, Long value2) {
			addCriterion("IDDISQUETEABONO not between", value1, value2, "iddisqueteabono");
			return (Criteria) this;
		}

		public Criteria andFechaIsNull() {
			addCriterion("FECHA is null");
			return (Criteria) this;
		}

		public Criteria andFechaIsNotNull() {
			addCriterion("FECHA is not null");
			return (Criteria) this;
		}

		public Criteria andFechaEqualTo(Date value) {
			addCriterion("FECHA =", value, "fecha");
			return (Criteria) this;
		}

		public Criteria andFechaNotEqualTo(Date value) {
			addCriterion("FECHA <>", value, "fecha");
			return (Criteria) this;
		}

		public Criteria andFechaGreaterThan(Date value) {
			addCriterion("FECHA >", value, "fecha");
			return (Criteria) this;
		}

		public Criteria andFechaGreaterThanOrEqualTo(Date value) {
			addCriterion("FECHA >=", value, "fecha");
			return (Criteria) this;
		}

		public Criteria andFechaLessThan(Date value) {
			addCriterion("FECHA <", value, "fecha");
			return (Criteria) this;
		}

		public Criteria andFechaLessThanOrEqualTo(Date value) {
			addCriterion("FECHA <=", value, "fecha");
			return (Criteria) this;
		}

		public Criteria andFechaIn(List<Date> values) {
			addCriterion("FECHA in", values, "fecha");
			return (Criteria) this;
		}

		public Criteria andFechaNotIn(List<Date> values) {
			addCriterion("FECHA not in", values, "fecha");
			return (Criteria) this;
		}

		public Criteria andFechaBetween(Date value1, Date value2) {
			addCriterion("FECHA between", value1, value2, "fecha");
			return (Criteria) this;
		}

		public Criteria andFechaNotBetween(Date value1, Date value2) {
			addCriterion("FECHA not between", value1, value2, "fecha");
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

		public Criteria andBancosCodigoIsNull() {
			addCriterion("BANCOS_CODIGO is null");
			return (Criteria) this;
		}

		public Criteria andBancosCodigoIsNotNull() {
			addCriterion("BANCOS_CODIGO is not null");
			return (Criteria) this;
		}

		public Criteria andBancosCodigoEqualTo(String value) {
			addCriterion("BANCOS_CODIGO =", value, "bancosCodigo");
			return (Criteria) this;
		}

		public Criteria andBancosCodigoNotEqualTo(String value) {
			addCriterion("BANCOS_CODIGO <>", value, "bancosCodigo");
			return (Criteria) this;
		}

		public Criteria andBancosCodigoGreaterThan(String value) {
			addCriterion("BANCOS_CODIGO >", value, "bancosCodigo");
			return (Criteria) this;
		}

		public Criteria andBancosCodigoGreaterThanOrEqualTo(String value) {
			addCriterion("BANCOS_CODIGO >=", value, "bancosCodigo");
			return (Criteria) this;
		}

		public Criteria andBancosCodigoLessThan(String value) {
			addCriterion("BANCOS_CODIGO <", value, "bancosCodigo");
			return (Criteria) this;
		}

		public Criteria andBancosCodigoLessThanOrEqualTo(String value) {
			addCriterion("BANCOS_CODIGO <=", value, "bancosCodigo");
			return (Criteria) this;
		}

		public Criteria andBancosCodigoLike(String value) {
			addCriterion("BANCOS_CODIGO like", value, "bancosCodigo");
			return (Criteria) this;
		}

		public Criteria andBancosCodigoNotLike(String value) {
			addCriterion("BANCOS_CODIGO not like", value, "bancosCodigo");
			return (Criteria) this;
		}

		public Criteria andBancosCodigoIn(List<String> values) {
			addCriterion("BANCOS_CODIGO in", values, "bancosCodigo");
			return (Criteria) this;
		}

		public Criteria andBancosCodigoNotIn(List<String> values) {
			addCriterion("BANCOS_CODIGO not in", values, "bancosCodigo");
			return (Criteria) this;
		}

		public Criteria andBancosCodigoBetween(String value1, String value2) {
			addCriterion("BANCOS_CODIGO between", value1, value2, "bancosCodigo");
			return (Criteria) this;
		}

		public Criteria andBancosCodigoNotBetween(String value1, String value2) {
			addCriterion("BANCOS_CODIGO not between", value1, value2, "bancosCodigo");
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

		public Criteria andFcsIsNull() {
			addCriterion("FCS is null");
			return (Criteria) this;
		}

		public Criteria andFcsIsNotNull() {
			addCriterion("FCS is not null");
			return (Criteria) this;
		}

		public Criteria andFcsEqualTo(String value) {
			addCriterion("FCS =", value, "fcs");
			return (Criteria) this;
		}

		public Criteria andFcsNotEqualTo(String value) {
			addCriterion("FCS <>", value, "fcs");
			return (Criteria) this;
		}

		public Criteria andFcsGreaterThan(String value) {
			addCriterion("FCS >", value, "fcs");
			return (Criteria) this;
		}

		public Criteria andFcsGreaterThanOrEqualTo(String value) {
			addCriterion("FCS >=", value, "fcs");
			return (Criteria) this;
		}

		public Criteria andFcsLessThan(String value) {
			addCriterion("FCS <", value, "fcs");
			return (Criteria) this;
		}

		public Criteria andFcsLessThanOrEqualTo(String value) {
			addCriterion("FCS <=", value, "fcs");
			return (Criteria) this;
		}

		public Criteria andFcsLike(String value) {
			addCriterion("FCS like", value, "fcs");
			return (Criteria) this;
		}

		public Criteria andFcsNotLike(String value) {
			addCriterion("FCS not like", value, "fcs");
			return (Criteria) this;
		}

		public Criteria andFcsIn(List<String> values) {
			addCriterion("FCS in", values, "fcs");
			return (Criteria) this;
		}

		public Criteria andFcsNotIn(List<String> values) {
			addCriterion("FCS not in", values, "fcs");
			return (Criteria) this;
		}

		public Criteria andFcsBetween(String value1, String value2) {
			addCriterion("FCS between", value1, value2, "fcs");
			return (Criteria) this;
		}

		public Criteria andFcsNotBetween(String value1, String value2) {
			addCriterion("FCS not between", value1, value2, "fcs");
			return (Criteria) this;
		}

		public Criteria andNumerolineasIsNull() {
			addCriterion("NUMEROLINEAS is null");
			return (Criteria) this;
		}

		public Criteria andNumerolineasIsNotNull() {
			addCriterion("NUMEROLINEAS is not null");
			return (Criteria) this;
		}

		public Criteria andNumerolineasEqualTo(Long value) {
			addCriterion("NUMEROLINEAS =", value, "numerolineas");
			return (Criteria) this;
		}

		public Criteria andNumerolineasNotEqualTo(Long value) {
			addCriterion("NUMEROLINEAS <>", value, "numerolineas");
			return (Criteria) this;
		}

		public Criteria andNumerolineasGreaterThan(Long value) {
			addCriterion("NUMEROLINEAS >", value, "numerolineas");
			return (Criteria) this;
		}

		public Criteria andNumerolineasGreaterThanOrEqualTo(Long value) {
			addCriterion("NUMEROLINEAS >=", value, "numerolineas");
			return (Criteria) this;
		}

		public Criteria andNumerolineasLessThan(Long value) {
			addCriterion("NUMEROLINEAS <", value, "numerolineas");
			return (Criteria) this;
		}

		public Criteria andNumerolineasLessThanOrEqualTo(Long value) {
			addCriterion("NUMEROLINEAS <=", value, "numerolineas");
			return (Criteria) this;
		}

		public Criteria andNumerolineasIn(List<Long> values) {
			addCriterion("NUMEROLINEAS in", values, "numerolineas");
			return (Criteria) this;
		}

		public Criteria andNumerolineasNotIn(List<Long> values) {
			addCriterion("NUMEROLINEAS not in", values, "numerolineas");
			return (Criteria) this;
		}

		public Criteria andNumerolineasBetween(Long value1, Long value2) {
			addCriterion("NUMEROLINEAS between", value1, value2, "numerolineas");
			return (Criteria) this;
		}

		public Criteria andNumerolineasNotBetween(Long value1, Long value2) {
			addCriterion("NUMEROLINEAS not between", value1, value2, "numerolineas");
			return (Criteria) this;
		}

		public Criteria andFechaejecucionIsNull() {
			addCriterion("FECHAEJECUCION is null");
			return (Criteria) this;
		}

		public Criteria andFechaejecucionIsNotNull() {
			addCriterion("FECHAEJECUCION is not null");
			return (Criteria) this;
		}

		public Criteria andFechaejecucionEqualTo(Date value) {
			addCriterion("FECHAEJECUCION =", value, "fechaejecucion");
			return (Criteria) this;
		}

		public Criteria andFechaejecucionNotEqualTo(Date value) {
			addCriterion("FECHAEJECUCION <>", value, "fechaejecucion");
			return (Criteria) this;
		}

		public Criteria andFechaejecucionGreaterThan(Date value) {
			addCriterion("FECHAEJECUCION >", value, "fechaejecucion");
			return (Criteria) this;
		}

		public Criteria andFechaejecucionGreaterThanOrEqualTo(Date value) {
			addCriterion("FECHAEJECUCION >=", value, "fechaejecucion");
			return (Criteria) this;
		}

		public Criteria andFechaejecucionLessThan(Date value) {
			addCriterion("FECHAEJECUCION <", value, "fechaejecucion");
			return (Criteria) this;
		}

		public Criteria andFechaejecucionLessThanOrEqualTo(Date value) {
			addCriterion("FECHAEJECUCION <=", value, "fechaejecucion");
			return (Criteria) this;
		}

		public Criteria andFechaejecucionIn(List<Date> values) {
			addCriterion("FECHAEJECUCION in", values, "fechaejecucion");
			return (Criteria) this;
		}

		public Criteria andFechaejecucionNotIn(List<Date> values) {
			addCriterion("FECHAEJECUCION not in", values, "fechaejecucion");
			return (Criteria) this;
		}

		public Criteria andFechaejecucionBetween(Date value1, Date value2) {
			addCriterion("FECHAEJECUCION between", value1, value2, "fechaejecucion");
			return (Criteria) this;
		}

		public Criteria andFechaejecucionNotBetween(Date value1, Date value2) {
			addCriterion("FECHAEJECUCION not between", value1, value2, "fechaejecucion");
			return (Criteria) this;
		}

		public Criteria andIdsufijoIsNull() {
			addCriterion("IDSUFIJO is null");
			return (Criteria) this;
		}

		public Criteria andIdsufijoIsNotNull() {
			addCriterion("IDSUFIJO is not null");
			return (Criteria) this;
		}

		public Criteria andIdsufijoEqualTo(Short value) {
			addCriterion("IDSUFIJO =", value, "idsufijo");
			return (Criteria) this;
		}

		public Criteria andIdsufijoNotEqualTo(Short value) {
			addCriterion("IDSUFIJO <>", value, "idsufijo");
			return (Criteria) this;
		}

		public Criteria andIdsufijoGreaterThan(Short value) {
			addCriterion("IDSUFIJO >", value, "idsufijo");
			return (Criteria) this;
		}

		public Criteria andIdsufijoGreaterThanOrEqualTo(Short value) {
			addCriterion("IDSUFIJO >=", value, "idsufijo");
			return (Criteria) this;
		}

		public Criteria andIdsufijoLessThan(Short value) {
			addCriterion("IDSUFIJO <", value, "idsufijo");
			return (Criteria) this;
		}

		public Criteria andIdsufijoLessThanOrEqualTo(Short value) {
			addCriterion("IDSUFIJO <=", value, "idsufijo");
			return (Criteria) this;
		}

		public Criteria andIdsufijoIn(List<Short> values) {
			addCriterion("IDSUFIJO in", values, "idsufijo");
			return (Criteria) this;
		}

		public Criteria andIdsufijoNotIn(List<Short> values) {
			addCriterion("IDSUFIJO not in", values, "idsufijo");
			return (Criteria) this;
		}

		public Criteria andIdsufijoBetween(Short value1, Short value2) {
			addCriterion("IDSUFIJO between", value1, value2, "idsufijo");
			return (Criteria) this;
		}

		public Criteria andIdsufijoNotBetween(Short value1, Short value2) {
			addCriterion("IDSUFIJO not between", value1, value2, "idsufijo");
			return (Criteria) this;
		}

		public Criteria andIdpropsepaIsNull() {
			addCriterion("IDPROPSEPA is null");
			return (Criteria) this;
		}

		public Criteria andIdpropsepaIsNotNull() {
			addCriterion("IDPROPSEPA is not null");
			return (Criteria) this;
		}

		public Criteria andIdpropsepaEqualTo(Short value) {
			addCriterion("IDPROPSEPA =", value, "idpropsepa");
			return (Criteria) this;
		}

		public Criteria andIdpropsepaNotEqualTo(Short value) {
			addCriterion("IDPROPSEPA <>", value, "idpropsepa");
			return (Criteria) this;
		}

		public Criteria andIdpropsepaGreaterThan(Short value) {
			addCriterion("IDPROPSEPA >", value, "idpropsepa");
			return (Criteria) this;
		}

		public Criteria andIdpropsepaGreaterThanOrEqualTo(Short value) {
			addCriterion("IDPROPSEPA >=", value, "idpropsepa");
			return (Criteria) this;
		}

		public Criteria andIdpropsepaLessThan(Short value) {
			addCriterion("IDPROPSEPA <", value, "idpropsepa");
			return (Criteria) this;
		}

		public Criteria andIdpropsepaLessThanOrEqualTo(Short value) {
			addCriterion("IDPROPSEPA <=", value, "idpropsepa");
			return (Criteria) this;
		}

		public Criteria andIdpropsepaIn(List<Short> values) {
			addCriterion("IDPROPSEPA in", values, "idpropsepa");
			return (Criteria) this;
		}

		public Criteria andIdpropsepaNotIn(List<Short> values) {
			addCriterion("IDPROPSEPA not in", values, "idpropsepa");
			return (Criteria) this;
		}

		public Criteria andIdpropsepaBetween(Short value1, Short value2) {
			addCriterion("IDPROPSEPA between", value1, value2, "idpropsepa");
			return (Criteria) this;
		}

		public Criteria andIdpropsepaNotBetween(Short value1, Short value2) {
			addCriterion("IDPROPSEPA not between", value1, value2, "idpropsepa");
			return (Criteria) this;
		}

		public Criteria andIdpropotrosIsNull() {
			addCriterion("IDPROPOTROS is null");
			return (Criteria) this;
		}

		public Criteria andIdpropotrosIsNotNull() {
			addCriterion("IDPROPOTROS is not null");
			return (Criteria) this;
		}

		public Criteria andIdpropotrosEqualTo(Short value) {
			addCriterion("IDPROPOTROS =", value, "idpropotros");
			return (Criteria) this;
		}

		public Criteria andIdpropotrosNotEqualTo(Short value) {
			addCriterion("IDPROPOTROS <>", value, "idpropotros");
			return (Criteria) this;
		}

		public Criteria andIdpropotrosGreaterThan(Short value) {
			addCriterion("IDPROPOTROS >", value, "idpropotros");
			return (Criteria) this;
		}

		public Criteria andIdpropotrosGreaterThanOrEqualTo(Short value) {
			addCriterion("IDPROPOTROS >=", value, "idpropotros");
			return (Criteria) this;
		}

		public Criteria andIdpropotrosLessThan(Short value) {
			addCriterion("IDPROPOTROS <", value, "idpropotros");
			return (Criteria) this;
		}

		public Criteria andIdpropotrosLessThanOrEqualTo(Short value) {
			addCriterion("IDPROPOTROS <=", value, "idpropotros");
			return (Criteria) this;
		}

		public Criteria andIdpropotrosIn(List<Short> values) {
			addCriterion("IDPROPOTROS in", values, "idpropotros");
			return (Criteria) this;
		}

		public Criteria andIdpropotrosNotIn(List<Short> values) {
			addCriterion("IDPROPOTROS not in", values, "idpropotros");
			return (Criteria) this;
		}

		public Criteria andIdpropotrosBetween(Short value1, Short value2) {
			addCriterion("IDPROPOTROS between", value1, value2, "idpropotros");
			return (Criteria) this;
		}

		public Criteria andIdpropotrosNotBetween(Short value1, Short value2) {
			addCriterion("IDPROPOTROS not between", value1, value2, "idpropotros");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE_INT.FAC_DISQUETEABONOS
	 * @mbg.generated  Tue Jan 18 16:13:17 CET 2022
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
     * This class corresponds to the database table USCGAE.FAC_DISQUETEABONOS
     *
     * @mbg.generated do_not_delete_during_merge Fri Oct 08 09:03:55 CEST 2021
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }
}