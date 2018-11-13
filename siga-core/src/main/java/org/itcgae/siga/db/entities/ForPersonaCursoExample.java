package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ForPersonaCursoExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE.FOR_PERSONA_CURSO
	 * @mbg.generated  Fri Nov 09 11:56:58 CET 2018
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE.FOR_PERSONA_CURSO
	 * @mbg.generated  Fri Nov 09 11:56:58 CET 2018
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE.FOR_PERSONA_CURSO
	 * @mbg.generated  Fri Nov 09 11:56:58 CET 2018
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_PERSONA_CURSO
	 * @mbg.generated  Fri Nov 09 11:56:58 CET 2018
	 */
	public ForPersonaCursoExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_PERSONA_CURSO
	 * @mbg.generated  Fri Nov 09 11:56:58 CET 2018
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_PERSONA_CURSO
	 * @mbg.generated  Fri Nov 09 11:56:58 CET 2018
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_PERSONA_CURSO
	 * @mbg.generated  Fri Nov 09 11:56:58 CET 2018
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_PERSONA_CURSO
	 * @mbg.generated  Fri Nov 09 11:56:58 CET 2018
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_PERSONA_CURSO
	 * @mbg.generated  Fri Nov 09 11:56:58 CET 2018
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_PERSONA_CURSO
	 * @mbg.generated  Fri Nov 09 11:56:58 CET 2018
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_PERSONA_CURSO
	 * @mbg.generated  Fri Nov 09 11:56:58 CET 2018
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_PERSONA_CURSO
	 * @mbg.generated  Fri Nov 09 11:56:58 CET 2018
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_PERSONA_CURSO
	 * @mbg.generated  Fri Nov 09 11:56:58 CET 2018
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.FOR_PERSONA_CURSO
	 * @mbg.generated  Fri Nov 09 11:56:58 CET 2018
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE.FOR_PERSONA_CURSO
	 * @mbg.generated  Fri Nov 09 11:56:58 CET 2018
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

		public Criteria andIdcursoIsNull() {
			addCriterion("IDCURSO is null");
			return (Criteria) this;
		}

		public Criteria andIdcursoIsNotNull() {
			addCriterion("IDCURSO is not null");
			return (Criteria) this;
		}

		public Criteria andIdcursoEqualTo(Long value) {
			addCriterion("IDCURSO =", value, "idcurso");
			return (Criteria) this;
		}

		public Criteria andIdcursoNotEqualTo(Long value) {
			addCriterion("IDCURSO <>", value, "idcurso");
			return (Criteria) this;
		}

		public Criteria andIdcursoGreaterThan(Long value) {
			addCriterion("IDCURSO >", value, "idcurso");
			return (Criteria) this;
		}

		public Criteria andIdcursoGreaterThanOrEqualTo(Long value) {
			addCriterion("IDCURSO >=", value, "idcurso");
			return (Criteria) this;
		}

		public Criteria andIdcursoLessThan(Long value) {
			addCriterion("IDCURSO <", value, "idcurso");
			return (Criteria) this;
		}

		public Criteria andIdcursoLessThanOrEqualTo(Long value) {
			addCriterion("IDCURSO <=", value, "idcurso");
			return (Criteria) this;
		}

		public Criteria andIdcursoIn(List<Long> values) {
			addCriterion("IDCURSO in", values, "idcurso");
			return (Criteria) this;
		}

		public Criteria andIdcursoNotIn(List<Long> values) {
			addCriterion("IDCURSO not in", values, "idcurso");
			return (Criteria) this;
		}

		public Criteria andIdcursoBetween(Long value1, Long value2) {
			addCriterion("IDCURSO between", value1, value2, "idcurso");
			return (Criteria) this;
		}

		public Criteria andIdcursoNotBetween(Long value1, Long value2) {
			addCriterion("IDCURSO not between", value1, value2, "idcurso");
			return (Criteria) this;
		}

		public Criteria andIdrolIsNull() {
			addCriterion("IDROL is null");
			return (Criteria) this;
		}

		public Criteria andIdrolIsNotNull() {
			addCriterion("IDROL is not null");
			return (Criteria) this;
		}

		public Criteria andIdrolEqualTo(Short value) {
			addCriterion("IDROL =", value, "idrol");
			return (Criteria) this;
		}

		public Criteria andIdrolNotEqualTo(Short value) {
			addCriterion("IDROL <>", value, "idrol");
			return (Criteria) this;
		}

		public Criteria andIdrolGreaterThan(Short value) {
			addCriterion("IDROL >", value, "idrol");
			return (Criteria) this;
		}

		public Criteria andIdrolGreaterThanOrEqualTo(Short value) {
			addCriterion("IDROL >=", value, "idrol");
			return (Criteria) this;
		}

		public Criteria andIdrolLessThan(Short value) {
			addCriterion("IDROL <", value, "idrol");
			return (Criteria) this;
		}

		public Criteria andIdrolLessThanOrEqualTo(Short value) {
			addCriterion("IDROL <=", value, "idrol");
			return (Criteria) this;
		}

		public Criteria andIdrolIn(List<Short> values) {
			addCriterion("IDROL in", values, "idrol");
			return (Criteria) this;
		}

		public Criteria andIdrolNotIn(List<Short> values) {
			addCriterion("IDROL not in", values, "idrol");
			return (Criteria) this;
		}

		public Criteria andIdrolBetween(Short value1, Short value2) {
			addCriterion("IDROL between", value1, value2, "idrol");
			return (Criteria) this;
		}

		public Criteria andIdrolNotBetween(Short value1, Short value2) {
			addCriterion("IDROL not between", value1, value2, "idrol");
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

		public Criteria andIdtipocosteIsNull() {
			addCriterion("IDTIPOCOSTE is null");
			return (Criteria) this;
		}

		public Criteria andIdtipocosteIsNotNull() {
			addCriterion("IDTIPOCOSTE is not null");
			return (Criteria) this;
		}

		public Criteria andIdtipocosteEqualTo(Short value) {
			addCriterion("IDTIPOCOSTE =", value, "idtipocoste");
			return (Criteria) this;
		}

		public Criteria andIdtipocosteNotEqualTo(Short value) {
			addCriterion("IDTIPOCOSTE <>", value, "idtipocoste");
			return (Criteria) this;
		}

		public Criteria andIdtipocosteGreaterThan(Short value) {
			addCriterion("IDTIPOCOSTE >", value, "idtipocoste");
			return (Criteria) this;
		}

		public Criteria andIdtipocosteGreaterThanOrEqualTo(Short value) {
			addCriterion("IDTIPOCOSTE >=", value, "idtipocoste");
			return (Criteria) this;
		}

		public Criteria andIdtipocosteLessThan(Short value) {
			addCriterion("IDTIPOCOSTE <", value, "idtipocoste");
			return (Criteria) this;
		}

		public Criteria andIdtipocosteLessThanOrEqualTo(Short value) {
			addCriterion("IDTIPOCOSTE <=", value, "idtipocoste");
			return (Criteria) this;
		}

		public Criteria andIdtipocosteIn(List<Short> values) {
			addCriterion("IDTIPOCOSTE in", values, "idtipocoste");
			return (Criteria) this;
		}

		public Criteria andIdtipocosteNotIn(List<Short> values) {
			addCriterion("IDTIPOCOSTE not in", values, "idtipocoste");
			return (Criteria) this;
		}

		public Criteria andIdtipocosteBetween(Short value1, Short value2) {
			addCriterion("IDTIPOCOSTE between", value1, value2, "idtipocoste");
			return (Criteria) this;
		}

		public Criteria andIdtipocosteNotBetween(Short value1, Short value2) {
			addCriterion("IDTIPOCOSTE not between", value1, value2, "idtipocoste");
			return (Criteria) this;
		}

		public Criteria andTarifaIsNull() {
			addCriterion("TARIFA is null");
			return (Criteria) this;
		}

		public Criteria andTarifaIsNotNull() {
			addCriterion("TARIFA is not null");
			return (Criteria) this;
		}

		public Criteria andTarifaEqualTo(Long value) {
			addCriterion("TARIFA =", value, "tarifa");
			return (Criteria) this;
		}

		public Criteria andTarifaNotEqualTo(Long value) {
			addCriterion("TARIFA <>", value, "tarifa");
			return (Criteria) this;
		}

		public Criteria andTarifaGreaterThan(Long value) {
			addCriterion("TARIFA >", value, "tarifa");
			return (Criteria) this;
		}

		public Criteria andTarifaGreaterThanOrEqualTo(Long value) {
			addCriterion("TARIFA >=", value, "tarifa");
			return (Criteria) this;
		}

		public Criteria andTarifaLessThan(Long value) {
			addCriterion("TARIFA <", value, "tarifa");
			return (Criteria) this;
		}

		public Criteria andTarifaLessThanOrEqualTo(Long value) {
			addCriterion("TARIFA <=", value, "tarifa");
			return (Criteria) this;
		}

		public Criteria andTarifaIn(List<Long> values) {
			addCriterion("TARIFA in", values, "tarifa");
			return (Criteria) this;
		}

		public Criteria andTarifaNotIn(List<Long> values) {
			addCriterion("TARIFA not in", values, "tarifa");
			return (Criteria) this;
		}

		public Criteria andTarifaBetween(Long value1, Long value2) {
			addCriterion("TARIFA between", value1, value2, "tarifa");
			return (Criteria) this;
		}

		public Criteria andTarifaNotBetween(Long value1, Long value2) {
			addCriterion("TARIFA not between", value1, value2, "tarifa");
			return (Criteria) this;
		}

		public Criteria andTutorIsNull() {
			addCriterion("TUTOR is null");
			return (Criteria) this;
		}

		public Criteria andTutorIsNotNull() {
			addCriterion("TUTOR is not null");
			return (Criteria) this;
		}

		public Criteria andTutorEqualTo(Short value) {
			addCriterion("TUTOR =", value, "tutor");
			return (Criteria) this;
		}

		public Criteria andTutorNotEqualTo(Short value) {
			addCriterion("TUTOR <>", value, "tutor");
			return (Criteria) this;
		}

		public Criteria andTutorGreaterThan(Short value) {
			addCriterion("TUTOR >", value, "tutor");
			return (Criteria) this;
		}

		public Criteria andTutorGreaterThanOrEqualTo(Short value) {
			addCriterion("TUTOR >=", value, "tutor");
			return (Criteria) this;
		}

		public Criteria andTutorLessThan(Short value) {
			addCriterion("TUTOR <", value, "tutor");
			return (Criteria) this;
		}

		public Criteria andTutorLessThanOrEqualTo(Short value) {
			addCriterion("TUTOR <=", value, "tutor");
			return (Criteria) this;
		}

		public Criteria andTutorIn(List<Short> values) {
			addCriterion("TUTOR in", values, "tutor");
			return (Criteria) this;
		}

		public Criteria andTutorNotIn(List<Short> values) {
			addCriterion("TUTOR not in", values, "tutor");
			return (Criteria) this;
		}

		public Criteria andTutorBetween(Short value1, Short value2) {
			addCriterion("TUTOR between", value1, value2, "tutor");
			return (Criteria) this;
		}

		public Criteria andTutorNotBetween(Short value1, Short value2) {
			addCriterion("TUTOR not between", value1, value2, "tutor");
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
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE.FOR_PERSONA_CURSO
	 * @mbg.generated  Fri Nov 09 11:56:58 CET 2018
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
     * This class corresponds to the database table USCGAE.FOR_PERSONA_CURSO
     *
     * @mbg.generated do_not_delete_during_merge Tue Oct 30 11:10:01 CET 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}