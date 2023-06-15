package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class ScsDocumentoejgExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE_INT.SCS_DOCUMENTOEJG
	 * @mbg.generated  Fri Feb 18 12:44:50 CET 2022
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE_INT.SCS_DOCUMENTOEJG
	 * @mbg.generated  Fri Feb 18 12:44:50 CET 2022
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE_INT.SCS_DOCUMENTOEJG
	 * @mbg.generated  Fri Feb 18 12:44:50 CET 2022
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.SCS_DOCUMENTOEJG
	 * @mbg.generated  Fri Feb 18 12:44:50 CET 2022
	 */
	public ScsDocumentoejgExample() {
		oredCriteria = new ArrayList<>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.SCS_DOCUMENTOEJG
	 * @mbg.generated  Fri Feb 18 12:44:50 CET 2022
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.SCS_DOCUMENTOEJG
	 * @mbg.generated  Fri Feb 18 12:44:50 CET 2022
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.SCS_DOCUMENTOEJG
	 * @mbg.generated  Fri Feb 18 12:44:50 CET 2022
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.SCS_DOCUMENTOEJG
	 * @mbg.generated  Fri Feb 18 12:44:50 CET 2022
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.SCS_DOCUMENTOEJG
	 * @mbg.generated  Fri Feb 18 12:44:50 CET 2022
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.SCS_DOCUMENTOEJG
	 * @mbg.generated  Fri Feb 18 12:44:50 CET 2022
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.SCS_DOCUMENTOEJG
	 * @mbg.generated  Fri Feb 18 12:44:50 CET 2022
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.SCS_DOCUMENTOEJG
	 * @mbg.generated  Fri Feb 18 12:44:50 CET 2022
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.SCS_DOCUMENTOEJG
	 * @mbg.generated  Fri Feb 18 12:44:50 CET 2022
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_INT.SCS_DOCUMENTOEJG
	 * @mbg.generated  Fri Feb 18 12:44:50 CET 2022
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE_INT.SCS_DOCUMENTOEJG
	 * @mbg.generated  Fri Feb 18 12:44:50 CET 2022
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

		public Criteria andIdtipodocumentoejgIsNull() {
			addCriterion("IDTIPODOCUMENTOEJG is null");
			return (Criteria) this;
		}

		public Criteria andIdtipodocumentoejgIsNotNull() {
			addCriterion("IDTIPODOCUMENTOEJG is not null");
			return (Criteria) this;
		}

		public Criteria andIdtipodocumentoejgEqualTo(Short value) {
			addCriterion("IDTIPODOCUMENTOEJG =", value, "idtipodocumentoejg");
			return (Criteria) this;
		}

		public Criteria andIdtipodocumentoejgNotEqualTo(Short value) {
			addCriterion("IDTIPODOCUMENTOEJG <>", value, "idtipodocumentoejg");
			return (Criteria) this;
		}

		public Criteria andIdtipodocumentoejgGreaterThan(Short value) {
			addCriterion("IDTIPODOCUMENTOEJG >", value, "idtipodocumentoejg");
			return (Criteria) this;
		}

		public Criteria andIdtipodocumentoejgGreaterThanOrEqualTo(Short value) {
			addCriterion("IDTIPODOCUMENTOEJG >=", value, "idtipodocumentoejg");
			return (Criteria) this;
		}

		public Criteria andIdtipodocumentoejgLessThan(Short value) {
			addCriterion("IDTIPODOCUMENTOEJG <", value, "idtipodocumentoejg");
			return (Criteria) this;
		}

		public Criteria andIdtipodocumentoejgLessThanOrEqualTo(Short value) {
			addCriterion("IDTIPODOCUMENTOEJG <=", value, "idtipodocumentoejg");
			return (Criteria) this;
		}

		public Criteria andIdtipodocumentoejgIn(List<Short> values) {
			addCriterion("IDTIPODOCUMENTOEJG in", values, "idtipodocumentoejg");
			return (Criteria) this;
		}

		public Criteria andIdtipodocumentoejgNotIn(List<Short> values) {
			addCriterion("IDTIPODOCUMENTOEJG not in", values, "idtipodocumentoejg");
			return (Criteria) this;
		}

		public Criteria andIdtipodocumentoejgBetween(Short value1, Short value2) {
			addCriterion("IDTIPODOCUMENTOEJG between", value1, value2, "idtipodocumentoejg");
			return (Criteria) this;
		}

		public Criteria andIdtipodocumentoejgNotBetween(Short value1, Short value2) {
			addCriterion("IDTIPODOCUMENTOEJG not between", value1, value2, "idtipodocumentoejg");
			return (Criteria) this;
		}

		public Criteria andIddocumentoejgIsNull() {
			addCriterion("IDDOCUMENTOEJG is null");
			return (Criteria) this;
		}

		public Criteria andIddocumentoejgIsNotNull() {
			addCriterion("IDDOCUMENTOEJG is not null");
			return (Criteria) this;
		}

		public Criteria andIddocumentoejgEqualTo(Short value) {
			addCriterion("IDDOCUMENTOEJG =", value, "iddocumentoejg");
			return (Criteria) this;
		}

		public Criteria andIddocumentoejgNotEqualTo(Short value) {
			addCriterion("IDDOCUMENTOEJG <>", value, "iddocumentoejg");
			return (Criteria) this;
		}

		public Criteria andIddocumentoejgGreaterThan(Short value) {
			addCriterion("IDDOCUMENTOEJG >", value, "iddocumentoejg");
			return (Criteria) this;
		}

		public Criteria andIddocumentoejgGreaterThanOrEqualTo(Short value) {
			addCriterion("IDDOCUMENTOEJG >=", value, "iddocumentoejg");
			return (Criteria) this;
		}

		public Criteria andIddocumentoejgLessThan(Short value) {
			addCriterion("IDDOCUMENTOEJG <", value, "iddocumentoejg");
			return (Criteria) this;
		}

		public Criteria andIddocumentoejgLessThanOrEqualTo(Short value) {
			addCriterion("IDDOCUMENTOEJG <=", value, "iddocumentoejg");
			return (Criteria) this;
		}

		public Criteria andIddocumentoejgIn(List<Short> values) {
			addCriterion("IDDOCUMENTOEJG in", values, "iddocumentoejg");
			return (Criteria) this;
		}

		public Criteria andIddocumentoejgNotIn(List<Short> values) {
			addCriterion("IDDOCUMENTOEJG not in", values, "iddocumentoejg");
			return (Criteria) this;
		}

		public Criteria andIddocumentoejgBetween(Short value1, Short value2) {
			addCriterion("IDDOCUMENTOEJG between", value1, value2, "iddocumentoejg");
			return (Criteria) this;
		}

		public Criteria andIddocumentoejgNotBetween(Short value1, Short value2) {
			addCriterion("IDDOCUMENTOEJG not between", value1, value2, "iddocumentoejg");
			return (Criteria) this;
		}

		public Criteria andAbreviaturaIsNull() {
			addCriterion("ABREVIATURA is null");
			return (Criteria) this;
		}

		public Criteria andAbreviaturaIsNotNull() {
			addCriterion("ABREVIATURA is not null");
			return (Criteria) this;
		}

		public Criteria andAbreviaturaEqualTo(String value) {
			addCriterion("ABREVIATURA =", value, "abreviatura");
			return (Criteria) this;
		}

		public Criteria andAbreviaturaNotEqualTo(String value) {
			addCriterion("ABREVIATURA <>", value, "abreviatura");
			return (Criteria) this;
		}

		public Criteria andAbreviaturaGreaterThan(String value) {
			addCriterion("ABREVIATURA >", value, "abreviatura");
			return (Criteria) this;
		}

		public Criteria andAbreviaturaGreaterThanOrEqualTo(String value) {
			addCriterion("ABREVIATURA >=", value, "abreviatura");
			return (Criteria) this;
		}

		public Criteria andAbreviaturaLessThan(String value) {
			addCriterion("ABREVIATURA <", value, "abreviatura");
			return (Criteria) this;
		}

		public Criteria andAbreviaturaLessThanOrEqualTo(String value) {
			addCriterion("ABREVIATURA <=", value, "abreviatura");
			return (Criteria) this;
		}

		public Criteria andAbreviaturaLike(String value) {
			addCriterion("ABREVIATURA like", value, "abreviatura");
			return (Criteria) this;
		}

		public Criteria andAbreviaturaNotLike(String value) {
			addCriterion("ABREVIATURA not like", value, "abreviatura");
			return (Criteria) this;
		}

		public Criteria andAbreviaturaIn(List<String> values) {
			addCriterion("ABREVIATURA in", values, "abreviatura");
			return (Criteria) this;
		}

		public Criteria andAbreviaturaNotIn(List<String> values) {
			addCriterion("ABREVIATURA not in", values, "abreviatura");
			return (Criteria) this;
		}

		public Criteria andAbreviaturaBetween(String value1, String value2) {
			addCriterion("ABREVIATURA between", value1, value2, "abreviatura");
			return (Criteria) this;
		}

		public Criteria andAbreviaturaNotBetween(String value1, String value2) {
			addCriterion("ABREVIATURA not between", value1, value2, "abreviatura");
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

		public Criteria andCodigoextIsNull() {
			addCriterion("CODIGOEXT is null");
			return (Criteria) this;
		}

		public Criteria andCodigoextIsNotNull() {
			addCriterion("CODIGOEXT is not null");
			return (Criteria) this;
		}

		public Criteria andCodigoextEqualTo(String value) {
			addCriterion("CODIGOEXT =", value, "codigoext");
			return (Criteria) this;
		}

		public Criteria andCodigoextNotEqualTo(String value) {
			addCriterion("CODIGOEXT <>", value, "codigoext");
			return (Criteria) this;
		}

		public Criteria andCodigoextGreaterThan(String value) {
			addCriterion("CODIGOEXT >", value, "codigoext");
			return (Criteria) this;
		}

		public Criteria andCodigoextGreaterThanOrEqualTo(String value) {
			addCriterion("CODIGOEXT >=", value, "codigoext");
			return (Criteria) this;
		}

		public Criteria andCodigoextLessThan(String value) {
			addCriterion("CODIGOEXT <", value, "codigoext");
			return (Criteria) this;
		}

		public Criteria andCodigoextLessThanOrEqualTo(String value) {
			addCriterion("CODIGOEXT <=", value, "codigoext");
			return (Criteria) this;
		}

		public Criteria andCodigoextLike(String value) {
			addCriterion("CODIGOEXT like", value, "codigoext");
			return (Criteria) this;
		}

		public Criteria andCodigoextNotLike(String value) {
			addCriterion("CODIGOEXT not like", value, "codigoext");
			return (Criteria) this;
		}

		public Criteria andCodigoextIn(List<String> values) {
			addCriterion("CODIGOEXT in", values, "codigoext");
			return (Criteria) this;
		}

		public Criteria andCodigoextNotIn(List<String> values) {
			addCriterion("CODIGOEXT not in", values, "codigoext");
			return (Criteria) this;
		}

		public Criteria andCodigoextBetween(String value1, String value2) {
			addCriterion("CODIGOEXT between", value1, value2, "codigoext");
			return (Criteria) this;
		}

		public Criteria andCodigoextNotBetween(String value1, String value2) {
			addCriterion("CODIGOEXT not between", value1, value2, "codigoext");
			return (Criteria) this;
		}

		public Criteria andCodigoejisIsNull() {
			addCriterion("CODIGOEJIS is null");
			return (Criteria) this;
		}

		public Criteria andCodigoejisIsNotNull() {
			addCriterion("CODIGOEJIS is not null");
			return (Criteria) this;
		}

		public Criteria andCodigoejisEqualTo(String value) {
			addCriterion("CODIGOEJIS =", value, "codigoejis");
			return (Criteria) this;
		}

		public Criteria andCodigoejisNotEqualTo(String value) {
			addCriterion("CODIGOEJIS <>", value, "codigoejis");
			return (Criteria) this;
		}

		public Criteria andCodigoejisGreaterThan(String value) {
			addCriterion("CODIGOEJIS >", value, "codigoejis");
			return (Criteria) this;
		}

		public Criteria andCodigoejisGreaterThanOrEqualTo(String value) {
			addCriterion("CODIGOEJIS >=", value, "codigoejis");
			return (Criteria) this;
		}

		public Criteria andCodigoejisLessThan(String value) {
			addCriterion("CODIGOEJIS <", value, "codigoejis");
			return (Criteria) this;
		}

		public Criteria andCodigoejisLessThanOrEqualTo(String value) {
			addCriterion("CODIGOEJIS <=", value, "codigoejis");
			return (Criteria) this;
		}

		public Criteria andCodigoejisLike(String value) {
			addCriterion("CODIGOEJIS like", value, "codigoejis");
			return (Criteria) this;
		}

		public Criteria andCodigoejisNotLike(String value) {
			addCriterion("CODIGOEJIS not like", value, "codigoejis");
			return (Criteria) this;
		}

		public Criteria andCodigoejisIn(List<String> values) {
			addCriterion("CODIGOEJIS in", values, "codigoejis");
			return (Criteria) this;
		}

		public Criteria andCodigoejisNotIn(List<String> values) {
			addCriterion("CODIGOEJIS not in", values, "codigoejis");
			return (Criteria) this;
		}

		public Criteria andCodigoejisBetween(String value1, String value2) {
			addCriterion("CODIGOEJIS between", value1, value2, "codigoejis");
			return (Criteria) this;
		}

		public Criteria andCodigoejisNotBetween(String value1, String value2) {
			addCriterion("CODIGOEJIS not between", value1, value2, "codigoejis");
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
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE_INT.SCS_DOCUMENTOEJG
	 * @mbg.generated  Fri Feb 18 12:44:50 CET 2022
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
     * This class corresponds to the database table USCGAE.SCS_DOCUMENTOEJG
     *
     * @mbg.generated do_not_delete_during_merge Tue Oct 08 14:48:51 CEST 2019
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}