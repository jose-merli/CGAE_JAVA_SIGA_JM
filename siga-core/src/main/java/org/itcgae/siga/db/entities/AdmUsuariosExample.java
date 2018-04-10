package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdmUsuariosExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE_DESA.ADM_USUARIOS
	 * @mbg.generated  Mon Apr 09 11:44:41 CEST 2018
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE_DESA.ADM_USUARIOS
	 * @mbg.generated  Mon Apr 09 11:44:41 CEST 2018
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE_DESA.ADM_USUARIOS
	 * @mbg.generated  Mon Apr 09 11:44:41 CEST 2018
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_USUARIOS
	 * @mbg.generated  Mon Apr 09 11:44:41 CEST 2018
	 */
	public AdmUsuariosExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_USUARIOS
	 * @mbg.generated  Mon Apr 09 11:44:41 CEST 2018
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_USUARIOS
	 * @mbg.generated  Mon Apr 09 11:44:41 CEST 2018
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_USUARIOS
	 * @mbg.generated  Mon Apr 09 11:44:41 CEST 2018
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_USUARIOS
	 * @mbg.generated  Mon Apr 09 11:44:41 CEST 2018
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_USUARIOS
	 * @mbg.generated  Mon Apr 09 11:44:41 CEST 2018
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_USUARIOS
	 * @mbg.generated  Mon Apr 09 11:44:41 CEST 2018
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_USUARIOS
	 * @mbg.generated  Mon Apr 09 11:44:41 CEST 2018
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_USUARIOS
	 * @mbg.generated  Mon Apr 09 11:44:41 CEST 2018
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_USUARIOS
	 * @mbg.generated  Mon Apr 09 11:44:41 CEST 2018
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.ADM_USUARIOS
	 * @mbg.generated  Mon Apr 09 11:44:41 CEST 2018
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE_DESA.ADM_USUARIOS
	 * @mbg.generated  Mon Apr 09 11:44:41 CEST 2018
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

		public Criteria andIdusuarioIsNull() {
			addCriterion("IDUSUARIO is null");
			return (Criteria) this;
		}

		public Criteria andIdusuarioIsNotNull() {
			addCriterion("IDUSUARIO is not null");
			return (Criteria) this;
		}

		public Criteria andIdusuarioEqualTo(Integer value) {
			addCriterion("IDUSUARIO =", value, "idusuario");
			return (Criteria) this;
		}

		public Criteria andIdusuarioNotEqualTo(Integer value) {
			addCriterion("IDUSUARIO <>", value, "idusuario");
			return (Criteria) this;
		}

		public Criteria andIdusuarioGreaterThan(Integer value) {
			addCriterion("IDUSUARIO >", value, "idusuario");
			return (Criteria) this;
		}

		public Criteria andIdusuarioGreaterThanOrEqualTo(Integer value) {
			addCriterion("IDUSUARIO >=", value, "idusuario");
			return (Criteria) this;
		}

		public Criteria andIdusuarioLessThan(Integer value) {
			addCriterion("IDUSUARIO <", value, "idusuario");
			return (Criteria) this;
		}

		public Criteria andIdusuarioLessThanOrEqualTo(Integer value) {
			addCriterion("IDUSUARIO <=", value, "idusuario");
			return (Criteria) this;
		}

		public Criteria andIdusuarioIn(List<Integer> values) {
			addCriterion("IDUSUARIO in", values, "idusuario");
			return (Criteria) this;
		}

		public Criteria andIdusuarioNotIn(List<Integer> values) {
			addCriterion("IDUSUARIO not in", values, "idusuario");
			return (Criteria) this;
		}

		public Criteria andIdusuarioBetween(Integer value1, Integer value2) {
			addCriterion("IDUSUARIO between", value1, value2, "idusuario");
			return (Criteria) this;
		}

		public Criteria andIdusuarioNotBetween(Integer value1, Integer value2) {
			addCriterion("IDUSUARIO not between", value1, value2, "idusuario");
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

		public Criteria andNifIsNull() {
			addCriterion("NIF is null");
			return (Criteria) this;
		}

		public Criteria andNifIsNotNull() {
			addCriterion("NIF is not null");
			return (Criteria) this;
		}

		public Criteria andNifEqualTo(String value) {
			addCriterion("NIF =", value, "nif");
			return (Criteria) this;
		}

		public Criteria andNifNotEqualTo(String value) {
			addCriterion("NIF <>", value, "nif");
			return (Criteria) this;
		}

		public Criteria andNifGreaterThan(String value) {
			addCriterion("NIF >", value, "nif");
			return (Criteria) this;
		}

		public Criteria andNifGreaterThanOrEqualTo(String value) {
			addCriterion("NIF >=", value, "nif");
			return (Criteria) this;
		}

		public Criteria andNifLessThan(String value) {
			addCriterion("NIF <", value, "nif");
			return (Criteria) this;
		}

		public Criteria andNifLessThanOrEqualTo(String value) {
			addCriterion("NIF <=", value, "nif");
			return (Criteria) this;
		}

		public Criteria andNifLike(String value) {
			addCriterion("NIF like", value, "nif");
			return (Criteria) this;
		}

		public Criteria andNifNotLike(String value) {
			addCriterion("NIF not like", value, "nif");
			return (Criteria) this;
		}

		public Criteria andNifIn(List<String> values) {
			addCriterion("NIF in", values, "nif");
			return (Criteria) this;
		}

		public Criteria andNifNotIn(List<String> values) {
			addCriterion("NIF not in", values, "nif");
			return (Criteria) this;
		}

		public Criteria andNifBetween(String value1, String value2) {
			addCriterion("NIF between", value1, value2, "nif");
			return (Criteria) this;
		}

		public Criteria andNifNotBetween(String value1, String value2) {
			addCriterion("NIF not between", value1, value2, "nif");
			return (Criteria) this;
		}

		public Criteria andActivoIsNull() {
			addCriterion("ACTIVO is null");
			return (Criteria) this;
		}

		public Criteria andActivoIsNotNull() {
			addCriterion("ACTIVO is not null");
			return (Criteria) this;
		}

		public Criteria andActivoEqualTo(String value) {
			addCriterion("ACTIVO =", value, "activo");
			return (Criteria) this;
		}

		public Criteria andActivoNotEqualTo(String value) {
			addCriterion("ACTIVO <>", value, "activo");
			return (Criteria) this;
		}

		public Criteria andActivoGreaterThan(String value) {
			addCriterion("ACTIVO >", value, "activo");
			return (Criteria) this;
		}

		public Criteria andActivoGreaterThanOrEqualTo(String value) {
			addCriterion("ACTIVO >=", value, "activo");
			return (Criteria) this;
		}

		public Criteria andActivoLessThan(String value) {
			addCriterion("ACTIVO <", value, "activo");
			return (Criteria) this;
		}

		public Criteria andActivoLessThanOrEqualTo(String value) {
			addCriterion("ACTIVO <=", value, "activo");
			return (Criteria) this;
		}

		public Criteria andActivoLike(String value) {
			addCriterion("ACTIVO like", value, "activo");
			return (Criteria) this;
		}

		public Criteria andActivoNotLike(String value) {
			addCriterion("ACTIVO not like", value, "activo");
			return (Criteria) this;
		}

		public Criteria andActivoIn(List<String> values) {
			addCriterion("ACTIVO in", values, "activo");
			return (Criteria) this;
		}

		public Criteria andActivoNotIn(List<String> values) {
			addCriterion("ACTIVO not in", values, "activo");
			return (Criteria) this;
		}

		public Criteria andActivoBetween(String value1, String value2) {
			addCriterion("ACTIVO between", value1, value2, "activo");
			return (Criteria) this;
		}

		public Criteria andActivoNotBetween(String value1, String value2) {
			addCriterion("ACTIVO not between", value1, value2, "activo");
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

		public Criteria andIdlenguajeIsNull() {
			addCriterion("IDLENGUAJE is null");
			return (Criteria) this;
		}

		public Criteria andIdlenguajeIsNotNull() {
			addCriterion("IDLENGUAJE is not null");
			return (Criteria) this;
		}

		public Criteria andIdlenguajeEqualTo(String value) {
			addCriterion("IDLENGUAJE =", value, "idlenguaje");
			return (Criteria) this;
		}

		public Criteria andIdlenguajeNotEqualTo(String value) {
			addCriterion("IDLENGUAJE <>", value, "idlenguaje");
			return (Criteria) this;
		}

		public Criteria andIdlenguajeGreaterThan(String value) {
			addCriterion("IDLENGUAJE >", value, "idlenguaje");
			return (Criteria) this;
		}

		public Criteria andIdlenguajeGreaterThanOrEqualTo(String value) {
			addCriterion("IDLENGUAJE >=", value, "idlenguaje");
			return (Criteria) this;
		}

		public Criteria andIdlenguajeLessThan(String value) {
			addCriterion("IDLENGUAJE <", value, "idlenguaje");
			return (Criteria) this;
		}

		public Criteria andIdlenguajeLessThanOrEqualTo(String value) {
			addCriterion("IDLENGUAJE <=", value, "idlenguaje");
			return (Criteria) this;
		}

		public Criteria andIdlenguajeLike(String value) {
			addCriterion("IDLENGUAJE like", value, "idlenguaje");
			return (Criteria) this;
		}

		public Criteria andIdlenguajeNotLike(String value) {
			addCriterion("IDLENGUAJE not like", value, "idlenguaje");
			return (Criteria) this;
		}

		public Criteria andIdlenguajeIn(List<String> values) {
			addCriterion("IDLENGUAJE in", values, "idlenguaje");
			return (Criteria) this;
		}

		public Criteria andIdlenguajeNotIn(List<String> values) {
			addCriterion("IDLENGUAJE not in", values, "idlenguaje");
			return (Criteria) this;
		}

		public Criteria andIdlenguajeBetween(String value1, String value2) {
			addCriterion("IDLENGUAJE between", value1, value2, "idlenguaje");
			return (Criteria) this;
		}

		public Criteria andIdlenguajeNotBetween(String value1, String value2) {
			addCriterion("IDLENGUAJE not between", value1, value2, "idlenguaje");
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

		public Criteria andFechaBajaIsNull() {
			addCriterion("FECHA_BAJA is null");
			return (Criteria) this;
		}

		public Criteria andFechaBajaIsNotNull() {
			addCriterion("FECHA_BAJA is not null");
			return (Criteria) this;
		}

		public Criteria andFechaBajaEqualTo(Date value) {
			addCriterion("FECHA_BAJA =", value, "fechaBaja");
			return (Criteria) this;
		}

		public Criteria andFechaBajaNotEqualTo(Date value) {
			addCriterion("FECHA_BAJA <>", value, "fechaBaja");
			return (Criteria) this;
		}

		public Criteria andFechaBajaGreaterThan(Date value) {
			addCriterion("FECHA_BAJA >", value, "fechaBaja");
			return (Criteria) this;
		}

		public Criteria andFechaBajaGreaterThanOrEqualTo(Date value) {
			addCriterion("FECHA_BAJA >=", value, "fechaBaja");
			return (Criteria) this;
		}

		public Criteria andFechaBajaLessThan(Date value) {
			addCriterion("FECHA_BAJA <", value, "fechaBaja");
			return (Criteria) this;
		}

		public Criteria andFechaBajaLessThanOrEqualTo(Date value) {
			addCriterion("FECHA_BAJA <=", value, "fechaBaja");
			return (Criteria) this;
		}

		public Criteria andFechaBajaIn(List<Date> values) {
			addCriterion("FECHA_BAJA in", values, "fechaBaja");
			return (Criteria) this;
		}

		public Criteria andFechaBajaNotIn(List<Date> values) {
			addCriterion("FECHA_BAJA not in", values, "fechaBaja");
			return (Criteria) this;
		}

		public Criteria andFechaBajaBetween(Date value1, Date value2) {
			addCriterion("FECHA_BAJA between", value1, value2, "fechaBaja");
			return (Criteria) this;
		}

		public Criteria andFechaBajaNotBetween(Date value1, Date value2) {
			addCriterion("FECHA_BAJA not between", value1, value2, "fechaBaja");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE_DESA.ADM_USUARIOS
	 * @mbg.generated  Mon Apr 09 11:44:41 CEST 2018
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
     * This class corresponds to the database table USCGAE_DESA.ADM_USUARIOS
     *
     * @mbg.generated do_not_delete_during_merge Wed Mar 07 17:49:31 CET 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}