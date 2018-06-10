package org.itcgae.siga.db.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdmUsuariosEfectivosPerfilExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE.ADM_USUARIOS_EFECTIVOS_PERFIL
	 * @mbg.generated  Sat Jun 09 20:51:16 CEST 2018
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE.ADM_USUARIOS_EFECTIVOS_PERFIL
	 * @mbg.generated  Sat Jun 09 20:51:16 CEST 2018
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USCGAE.ADM_USUARIOS_EFECTIVOS_PERFIL
	 * @mbg.generated  Sat Jun 09 20:51:16 CEST 2018
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ADM_USUARIOS_EFECTIVOS_PERFIL
	 * @mbg.generated  Sat Jun 09 20:51:16 CEST 2018
	 */
	public AdmUsuariosEfectivosPerfilExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ADM_USUARIOS_EFECTIVOS_PERFIL
	 * @mbg.generated  Sat Jun 09 20:51:16 CEST 2018
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ADM_USUARIOS_EFECTIVOS_PERFIL
	 * @mbg.generated  Sat Jun 09 20:51:16 CEST 2018
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ADM_USUARIOS_EFECTIVOS_PERFIL
	 * @mbg.generated  Sat Jun 09 20:51:16 CEST 2018
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ADM_USUARIOS_EFECTIVOS_PERFIL
	 * @mbg.generated  Sat Jun 09 20:51:16 CEST 2018
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ADM_USUARIOS_EFECTIVOS_PERFIL
	 * @mbg.generated  Sat Jun 09 20:51:16 CEST 2018
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ADM_USUARIOS_EFECTIVOS_PERFIL
	 * @mbg.generated  Sat Jun 09 20:51:16 CEST 2018
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ADM_USUARIOS_EFECTIVOS_PERFIL
	 * @mbg.generated  Sat Jun 09 20:51:16 CEST 2018
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ADM_USUARIOS_EFECTIVOS_PERFIL
	 * @mbg.generated  Sat Jun 09 20:51:16 CEST 2018
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ADM_USUARIOS_EFECTIVOS_PERFIL
	 * @mbg.generated  Sat Jun 09 20:51:16 CEST 2018
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.ADM_USUARIOS_EFECTIVOS_PERFIL
	 * @mbg.generated  Sat Jun 09 20:51:16 CEST 2018
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE.ADM_USUARIOS_EFECTIVOS_PERFIL
	 * @mbg.generated  Sat Jun 09 20:51:16 CEST 2018
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

		public Criteria andIdrolIsNull() {
			addCriterion("IDROL is null");
			return (Criteria) this;
		}

		public Criteria andIdrolIsNotNull() {
			addCriterion("IDROL is not null");
			return (Criteria) this;
		}

		public Criteria andIdrolEqualTo(String value) {
			addCriterion("IDROL =", value, "idrol");
			return (Criteria) this;
		}

		public Criteria andIdrolNotEqualTo(String value) {
			addCriterion("IDROL <>", value, "idrol");
			return (Criteria) this;
		}

		public Criteria andIdrolGreaterThan(String value) {
			addCriterion("IDROL >", value, "idrol");
			return (Criteria) this;
		}

		public Criteria andIdrolGreaterThanOrEqualTo(String value) {
			addCriterion("IDROL >=", value, "idrol");
			return (Criteria) this;
		}

		public Criteria andIdrolLessThan(String value) {
			addCriterion("IDROL <", value, "idrol");
			return (Criteria) this;
		}

		public Criteria andIdrolLessThanOrEqualTo(String value) {
			addCriterion("IDROL <=", value, "idrol");
			return (Criteria) this;
		}

		public Criteria andIdrolLike(String value) {
			addCriterion("IDROL like", value, "idrol");
			return (Criteria) this;
		}

		public Criteria andIdrolNotLike(String value) {
			addCriterion("IDROL not like", value, "idrol");
			return (Criteria) this;
		}

		public Criteria andIdrolIn(List<String> values) {
			addCriterion("IDROL in", values, "idrol");
			return (Criteria) this;
		}

		public Criteria andIdrolNotIn(List<String> values) {
			addCriterion("IDROL not in", values, "idrol");
			return (Criteria) this;
		}

		public Criteria andIdrolBetween(String value1, String value2) {
			addCriterion("IDROL between", value1, value2, "idrol");
			return (Criteria) this;
		}

		public Criteria andIdrolNotBetween(String value1, String value2) {
			addCriterion("IDROL not between", value1, value2, "idrol");
			return (Criteria) this;
		}

		public Criteria andIdperfilIsNull() {
			addCriterion("IDPERFIL is null");
			return (Criteria) this;
		}

		public Criteria andIdperfilIsNotNull() {
			addCriterion("IDPERFIL is not null");
			return (Criteria) this;
		}

		public Criteria andIdperfilEqualTo(String value) {
			addCriterion("IDPERFIL =", value, "idperfil");
			return (Criteria) this;
		}

		public Criteria andIdperfilNotEqualTo(String value) {
			addCriterion("IDPERFIL <>", value, "idperfil");
			return (Criteria) this;
		}

		public Criteria andIdperfilGreaterThan(String value) {
			addCriterion("IDPERFIL >", value, "idperfil");
			return (Criteria) this;
		}

		public Criteria andIdperfilGreaterThanOrEqualTo(String value) {
			addCriterion("IDPERFIL >=", value, "idperfil");
			return (Criteria) this;
		}

		public Criteria andIdperfilLessThan(String value) {
			addCriterion("IDPERFIL <", value, "idperfil");
			return (Criteria) this;
		}

		public Criteria andIdperfilLessThanOrEqualTo(String value) {
			addCriterion("IDPERFIL <=", value, "idperfil");
			return (Criteria) this;
		}

		public Criteria andIdperfilLike(String value) {
			addCriterion("IDPERFIL like", value, "idperfil");
			return (Criteria) this;
		}

		public Criteria andIdperfilNotLike(String value) {
			addCriterion("IDPERFIL not like", value, "idperfil");
			return (Criteria) this;
		}

		public Criteria andIdperfilIn(List<String> values) {
			addCriterion("IDPERFIL in", values, "idperfil");
			return (Criteria) this;
		}

		public Criteria andIdperfilNotIn(List<String> values) {
			addCriterion("IDPERFIL not in", values, "idperfil");
			return (Criteria) this;
		}

		public Criteria andIdperfilBetween(String value1, String value2) {
			addCriterion("IDPERFIL between", value1, value2, "idperfil");
			return (Criteria) this;
		}

		public Criteria andIdperfilNotBetween(String value1, String value2) {
			addCriterion("IDPERFIL not between", value1, value2, "idperfil");
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
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USCGAE.ADM_USUARIOS_EFECTIVOS_PERFIL
	 * @mbg.generated  Sat Jun 09 20:51:16 CEST 2018
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
     * This class corresponds to the database table USCGAE_DESA.ADM_USUARIOS_EFECTIVOS_PERFIL
     *
     * @mbg.generated do_not_delete_during_merge Wed Mar 07 17:49:31 CET 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}