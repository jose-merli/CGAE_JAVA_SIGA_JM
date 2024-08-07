package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgeEventoExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE.AGE_EVENTO
	 * @mbg.generated  Thu Nov 29 11:42:59 CET 2018
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE.AGE_EVENTO
	 * @mbg.generated  Thu Nov 29 11:42:59 CET 2018
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE.AGE_EVENTO
	 * @mbg.generated  Thu Nov 29 11:42:59 CET 2018
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_EVENTO
	 * @mbg.generated  Thu Nov 29 11:42:59 CET 2018
	 */
	public AgeEventoExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_EVENTO
	 * @mbg.generated  Thu Nov 29 11:42:59 CET 2018
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_EVENTO
	 * @mbg.generated  Thu Nov 29 11:42:59 CET 2018
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_EVENTO
	 * @mbg.generated  Thu Nov 29 11:42:59 CET 2018
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_EVENTO
	 * @mbg.generated  Thu Nov 29 11:42:59 CET 2018
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_EVENTO
	 * @mbg.generated  Thu Nov 29 11:42:59 CET 2018
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_EVENTO
	 * @mbg.generated  Thu Nov 29 11:42:59 CET 2018
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_EVENTO
	 * @mbg.generated  Thu Nov 29 11:42:59 CET 2018
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_EVENTO
	 * @mbg.generated  Thu Nov 29 11:42:59 CET 2018
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_EVENTO
	 * @mbg.generated  Thu Nov 29 11:42:59 CET 2018
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.AGE_EVENTO
	 * @mbg.generated  Thu Nov 29 11:42:59 CET 2018
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE.AGE_EVENTO
	 * @mbg.generated  Thu Nov 29 11:42:59 CET 2018
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

		public Criteria andTituloIsNull() {
			addCriterion("TITULO is null");
			return (Criteria) this;
		}

		public Criteria andTituloIsNotNull() {
			addCriterion("TITULO is not null");
			return (Criteria) this;
		}

		public Criteria andTituloEqualTo(String value) {
			addCriterion("TITULO =", value, "titulo");
			return (Criteria) this;
		}

		public Criteria andTituloNotEqualTo(String value) {
			addCriterion("TITULO <>", value, "titulo");
			return (Criteria) this;
		}

		public Criteria andTituloGreaterThan(String value) {
			addCriterion("TITULO >", value, "titulo");
			return (Criteria) this;
		}

		public Criteria andTituloGreaterThanOrEqualTo(String value) {
			addCriterion("TITULO >=", value, "titulo");
			return (Criteria) this;
		}

		public Criteria andTituloLessThan(String value) {
			addCriterion("TITULO <", value, "titulo");
			return (Criteria) this;
		}

		public Criteria andTituloLessThanOrEqualTo(String value) {
			addCriterion("TITULO <=", value, "titulo");
			return (Criteria) this;
		}

		public Criteria andTituloLike(String value) {
			addCriterion("TITULO like", value, "titulo");
			return (Criteria) this;
		}

		public Criteria andTituloNotLike(String value) {
			addCriterion("TITULO not like", value, "titulo");
			return (Criteria) this;
		}

		public Criteria andTituloIn(List<String> values) {
			addCriterion("TITULO in", values, "titulo");
			return (Criteria) this;
		}

		public Criteria andTituloNotIn(List<String> values) {
			addCriterion("TITULO not in", values, "titulo");
			return (Criteria) this;
		}

		public Criteria andTituloBetween(String value1, String value2) {
			addCriterion("TITULO between", value1, value2, "titulo");
			return (Criteria) this;
		}

		public Criteria andTituloNotBetween(String value1, String value2) {
			addCriterion("TITULO not between", value1, value2, "titulo");
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

		public Criteria andLugarIsNull() {
			addCriterion("LUGAR is null");
			return (Criteria) this;
		}

		public Criteria andLugarIsNotNull() {
			addCriterion("LUGAR is not null");
			return (Criteria) this;
		}

		public Criteria andLugarEqualTo(String value) {
			addCriterion("LUGAR =", value, "lugar");
			return (Criteria) this;
		}

		public Criteria andLugarNotEqualTo(String value) {
			addCriterion("LUGAR <>", value, "lugar");
			return (Criteria) this;
		}

		public Criteria andLugarGreaterThan(String value) {
			addCriterion("LUGAR >", value, "lugar");
			return (Criteria) this;
		}

		public Criteria andLugarGreaterThanOrEqualTo(String value) {
			addCriterion("LUGAR >=", value, "lugar");
			return (Criteria) this;
		}

		public Criteria andLugarLessThan(String value) {
			addCriterion("LUGAR <", value, "lugar");
			return (Criteria) this;
		}

		public Criteria andLugarLessThanOrEqualTo(String value) {
			addCriterion("LUGAR <=", value, "lugar");
			return (Criteria) this;
		}

		public Criteria andLugarLike(String value) {
			addCriterion("LUGAR like", value, "lugar");
			return (Criteria) this;
		}

		public Criteria andLugarNotLike(String value) {
			addCriterion("LUGAR not like", value, "lugar");
			return (Criteria) this;
		}

		public Criteria andLugarIn(List<String> values) {
			addCriterion("LUGAR in", values, "lugar");
			return (Criteria) this;
		}

		public Criteria andLugarNotIn(List<String> values) {
			addCriterion("LUGAR not in", values, "lugar");
			return (Criteria) this;
		}

		public Criteria andLugarBetween(String value1, String value2) {
			addCriterion("LUGAR between", value1, value2, "lugar");
			return (Criteria) this;
		}

		public Criteria andLugarNotBetween(String value1, String value2) {
			addCriterion("LUGAR not between", value1, value2, "lugar");
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

		public Criteria andRecursosIsNull() {
			addCriterion("RECURSOS is null");
			return (Criteria) this;
		}

		public Criteria andRecursosIsNotNull() {
			addCriterion("RECURSOS is not null");
			return (Criteria) this;
		}

		public Criteria andRecursosEqualTo(String value) {
			addCriterion("RECURSOS =", value, "recursos");
			return (Criteria) this;
		}

		public Criteria andRecursosNotEqualTo(String value) {
			addCriterion("RECURSOS <>", value, "recursos");
			return (Criteria) this;
		}

		public Criteria andRecursosGreaterThan(String value) {
			addCriterion("RECURSOS >", value, "recursos");
			return (Criteria) this;
		}

		public Criteria andRecursosGreaterThanOrEqualTo(String value) {
			addCriterion("RECURSOS >=", value, "recursos");
			return (Criteria) this;
		}

		public Criteria andRecursosLessThan(String value) {
			addCriterion("RECURSOS <", value, "recursos");
			return (Criteria) this;
		}

		public Criteria andRecursosLessThanOrEqualTo(String value) {
			addCriterion("RECURSOS <=", value, "recursos");
			return (Criteria) this;
		}

		public Criteria andRecursosLike(String value) {
			addCriterion("RECURSOS like", value, "recursos");
			return (Criteria) this;
		}

		public Criteria andRecursosNotLike(String value) {
			addCriterion("RECURSOS not like", value, "recursos");
			return (Criteria) this;
		}

		public Criteria andRecursosIn(List<String> values) {
			addCriterion("RECURSOS in", values, "recursos");
			return (Criteria) this;
		}

		public Criteria andRecursosNotIn(List<String> values) {
			addCriterion("RECURSOS not in", values, "recursos");
			return (Criteria) this;
		}

		public Criteria andRecursosBetween(String value1, String value2) {
			addCriterion("RECURSOS between", value1, value2, "recursos");
			return (Criteria) this;
		}

		public Criteria andRecursosNotBetween(String value1, String value2) {
			addCriterion("RECURSOS not between", value1, value2, "recursos");
			return (Criteria) this;
		}

		public Criteria andIdestadoeventoIsNull() {
			addCriterion("IDESTADOEVENTO is null");
			return (Criteria) this;
		}

		public Criteria andIdestadoeventoIsNotNull() {
			addCriterion("IDESTADOEVENTO is not null");
			return (Criteria) this;
		}

		public Criteria andIdestadoeventoEqualTo(Long value) {
			addCriterion("IDESTADOEVENTO =", value, "idestadoevento");
			return (Criteria) this;
		}

		public Criteria andIdestadoeventoNotEqualTo(Long value) {
			addCriterion("IDESTADOEVENTO <>", value, "idestadoevento");
			return (Criteria) this;
		}

		public Criteria andIdestadoeventoGreaterThan(Long value) {
			addCriterion("IDESTADOEVENTO >", value, "idestadoevento");
			return (Criteria) this;
		}

		public Criteria andIdestadoeventoGreaterThanOrEqualTo(Long value) {
			addCriterion("IDESTADOEVENTO >=", value, "idestadoevento");
			return (Criteria) this;
		}

		public Criteria andIdestadoeventoLessThan(Long value) {
			addCriterion("IDESTADOEVENTO <", value, "idestadoevento");
			return (Criteria) this;
		}

		public Criteria andIdestadoeventoLessThanOrEqualTo(Long value) {
			addCriterion("IDESTADOEVENTO <=", value, "idestadoevento");
			return (Criteria) this;
		}

		public Criteria andIdestadoeventoIn(List<Long> values) {
			addCriterion("IDESTADOEVENTO in", values, "idestadoevento");
			return (Criteria) this;
		}

		public Criteria andIdestadoeventoNotIn(List<Long> values) {
			addCriterion("IDESTADOEVENTO not in", values, "idestadoevento");
			return (Criteria) this;
		}

		public Criteria andIdestadoeventoBetween(Long value1, Long value2) {
			addCriterion("IDESTADOEVENTO between", value1, value2, "idestadoevento");
			return (Criteria) this;
		}

		public Criteria andIdestadoeventoNotBetween(Long value1, Long value2) {
			addCriterion("IDESTADOEVENTO not between", value1, value2, "idestadoevento");
			return (Criteria) this;
		}

		public Criteria andIdtipoeventoIsNull() {
			addCriterion("IDTIPOEVENTO is null");
			return (Criteria) this;
		}

		public Criteria andIdtipoeventoIsNotNull() {
			addCriterion("IDTIPOEVENTO is not null");
			return (Criteria) this;
		}

		public Criteria andIdtipoeventoEqualTo(Long value) {
			addCriterion("IDTIPOEVENTO =", value, "idtipoevento");
			return (Criteria) this;
		}

		public Criteria andIdtipoeventoNotEqualTo(Long value) {
			addCriterion("IDTIPOEVENTO <>", value, "idtipoevento");
			return (Criteria) this;
		}

		public Criteria andIdtipoeventoGreaterThan(Long value) {
			addCriterion("IDTIPOEVENTO >", value, "idtipoevento");
			return (Criteria) this;
		}

		public Criteria andIdtipoeventoGreaterThanOrEqualTo(Long value) {
			addCriterion("IDTIPOEVENTO >=", value, "idtipoevento");
			return (Criteria) this;
		}

		public Criteria andIdtipoeventoLessThan(Long value) {
			addCriterion("IDTIPOEVENTO <", value, "idtipoevento");
			return (Criteria) this;
		}

		public Criteria andIdtipoeventoLessThanOrEqualTo(Long value) {
			addCriterion("IDTIPOEVENTO <=", value, "idtipoevento");
			return (Criteria) this;
		}

		public Criteria andIdtipoeventoIn(List<Long> values) {
			addCriterion("IDTIPOEVENTO in", values, "idtipoevento");
			return (Criteria) this;
		}

		public Criteria andIdtipoeventoNotIn(List<Long> values) {
			addCriterion("IDTIPOEVENTO not in", values, "idtipoevento");
			return (Criteria) this;
		}

		public Criteria andIdtipoeventoBetween(Long value1, Long value2) {
			addCriterion("IDTIPOEVENTO between", value1, value2, "idtipoevento");
			return (Criteria) this;
		}

		public Criteria andIdtipoeventoNotBetween(Long value1, Long value2) {
			addCriterion("IDTIPOEVENTO not between", value1, value2, "idtipoevento");
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

		public Criteria andIdrepeticioneventoIsNull() {
			addCriterion("IDREPETICIONEVENTO is null");
			return (Criteria) this;
		}

		public Criteria andIdrepeticioneventoIsNotNull() {
			addCriterion("IDREPETICIONEVENTO is not null");
			return (Criteria) this;
		}

		public Criteria andIdrepeticioneventoEqualTo(Long value) {
			addCriterion("IDREPETICIONEVENTO =", value, "idrepeticionevento");
			return (Criteria) this;
		}

		public Criteria andIdrepeticioneventoNotEqualTo(Long value) {
			addCriterion("IDREPETICIONEVENTO <>", value, "idrepeticionevento");
			return (Criteria) this;
		}

		public Criteria andIdrepeticioneventoGreaterThan(Long value) {
			addCriterion("IDREPETICIONEVENTO >", value, "idrepeticionevento");
			return (Criteria) this;
		}

		public Criteria andIdrepeticioneventoGreaterThanOrEqualTo(Long value) {
			addCriterion("IDREPETICIONEVENTO >=", value, "idrepeticionevento");
			return (Criteria) this;
		}

		public Criteria andIdrepeticioneventoLessThan(Long value) {
			addCriterion("IDREPETICIONEVENTO <", value, "idrepeticionevento");
			return (Criteria) this;
		}

		public Criteria andIdrepeticioneventoLessThanOrEqualTo(Long value) {
			addCriterion("IDREPETICIONEVENTO <=", value, "idrepeticionevento");
			return (Criteria) this;
		}

		public Criteria andIdrepeticioneventoIn(List<Long> values) {
			addCriterion("IDREPETICIONEVENTO in", values, "idrepeticionevento");
			return (Criteria) this;
		}

		public Criteria andIdrepeticioneventoNotIn(List<Long> values) {
			addCriterion("IDREPETICIONEVENTO not in", values, "idrepeticionevento");
			return (Criteria) this;
		}

		public Criteria andIdrepeticioneventoBetween(Long value1, Long value2) {
			addCriterion("IDREPETICIONEVENTO between", value1, value2, "idrepeticionevento");
			return (Criteria) this;
		}

		public Criteria andIdrepeticioneventoNotBetween(Long value1, Long value2) {
			addCriterion("IDREPETICIONEVENTO not between", value1, value2, "idrepeticionevento");
			return (Criteria) this;
		}

		public Criteria andIdeventooriginalIsNull() {
			addCriterion("IDEVENTOORIGINAL is null");
			return (Criteria) this;
		}

		public Criteria andIdeventooriginalIsNotNull() {
			addCriterion("IDEVENTOORIGINAL is not null");
			return (Criteria) this;
		}

		public Criteria andIdeventooriginalEqualTo(Long value) {
			addCriterion("IDEVENTOORIGINAL =", value, "ideventooriginal");
			return (Criteria) this;
		}

		public Criteria andIdeventooriginalNotEqualTo(Long value) {
			addCriterion("IDEVENTOORIGINAL <>", value, "ideventooriginal");
			return (Criteria) this;
		}

		public Criteria andIdeventooriginalGreaterThan(Long value) {
			addCriterion("IDEVENTOORIGINAL >", value, "ideventooriginal");
			return (Criteria) this;
		}

		public Criteria andIdeventooriginalGreaterThanOrEqualTo(Long value) {
			addCriterion("IDEVENTOORIGINAL >=", value, "ideventooriginal");
			return (Criteria) this;
		}

		public Criteria andIdeventooriginalLessThan(Long value) {
			addCriterion("IDEVENTOORIGINAL <", value, "ideventooriginal");
			return (Criteria) this;
		}

		public Criteria andIdeventooriginalLessThanOrEqualTo(Long value) {
			addCriterion("IDEVENTOORIGINAL <=", value, "ideventooriginal");
			return (Criteria) this;
		}

		public Criteria andIdeventooriginalIn(List<Long> values) {
			addCriterion("IDEVENTOORIGINAL in", values, "ideventooriginal");
			return (Criteria) this;
		}

		public Criteria andIdeventooriginalNotIn(List<Long> values) {
			addCriterion("IDEVENTOORIGINAL not in", values, "ideventooriginal");
			return (Criteria) this;
		}

		public Criteria andIdeventooriginalBetween(Long value1, Long value2) {
			addCriterion("IDEVENTOORIGINAL between", value1, value2, "ideventooriginal");
			return (Criteria) this;
		}

		public Criteria andIdeventooriginalNotBetween(Long value1, Long value2) {
			addCriterion("IDEVENTOORIGINAL not between", value1, value2, "ideventooriginal");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE.AGE_EVENTO
	 * @mbg.generated  Thu Nov 29 11:42:59 CET 2018
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
     * This class corresponds to the database table USCGAE.AGE_EVENTO
     *
     * @mbg.generated do_not_delete_during_merge Wed Nov 14 12:45:58 CET 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}