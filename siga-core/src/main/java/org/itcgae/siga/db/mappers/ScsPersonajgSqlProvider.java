package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.ScsPersonajg;
import org.itcgae.siga.db.entities.ScsPersonajgExample.Criteria;
import org.itcgae.siga.db.entities.ScsPersonajgExample.Criterion;
import org.itcgae.siga.db.entities.ScsPersonajgExample;

public class ScsPersonajgSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PERSONAJG
	 * @mbg.generated  Tue Nov 19 14:39:46 CET 2019
	 */
	public String countByExample(ScsPersonajgExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("SCS_PERSONAJG");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PERSONAJG
	 * @mbg.generated  Tue Nov 19 14:39:46 CET 2019
	 */
	public String deleteByExample(ScsPersonajgExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("SCS_PERSONAJG");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PERSONAJG
	 * @mbg.generated  Tue Nov 19 14:39:46 CET 2019
	 */
	public String insertSelective(ScsPersonajg record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("SCS_PERSONAJG");
		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdpersona() != null) {
			sql.VALUES("IDPERSONA", "#{idpersona,jdbcType=DECIMAL}");
		}
		if (record.getFechanacimiento() != null) {
			sql.VALUES("FECHANACIMIENTO", "#{fechanacimiento,jdbcType=TIMESTAMP}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdpais() != null) {
			sql.VALUES("IDPAIS", "#{idpais,jdbcType=VARCHAR}");
		}
		if (record.getNif() != null) {
			sql.VALUES("NIF", "#{nif,jdbcType=VARCHAR}");
		}
		if (record.getNombre() != null) {
			sql.VALUES("NOMBRE", "#{nombre,jdbcType=VARCHAR}");
		}
		if (record.getApellido1() != null) {
			sql.VALUES("APELLIDO1", "#{apellido1,jdbcType=VARCHAR}");
		}
		if (record.getApellido2() != null) {
			sql.VALUES("APELLIDO2", "#{apellido2,jdbcType=VARCHAR}");
		}
		if (record.getDireccion() != null) {
			sql.VALUES("DIRECCION", "#{direccion,jdbcType=VARCHAR}");
		}
		if (record.getCodigopostal() != null) {
			sql.VALUES("CODIGOPOSTAL", "#{codigopostal,jdbcType=VARCHAR}");
		}
		if (record.getIdprofesion() != null) {
			sql.VALUES("IDPROFESION", "#{idprofesion,jdbcType=DECIMAL}");
		}
		if (record.getRegimenConyugal() != null) {
			sql.VALUES("REGIMEN_CONYUGAL", "#{regimenConyugal,jdbcType=VARCHAR}");
		}
		if (record.getIdprovincia() != null) {
			sql.VALUES("IDPROVINCIA", "#{idprovincia,jdbcType=VARCHAR}");
		}
		if (record.getIdpoblacion() != null) {
			sql.VALUES("IDPOBLACION", "#{idpoblacion,jdbcType=VARCHAR}");
		}
		if (record.getIdestadocivil() != null) {
			sql.VALUES("IDESTADOCIVIL", "#{idestadocivil,jdbcType=DECIMAL}");
		}
		if (record.getTipopersonajg() != null) {
			sql.VALUES("TIPOPERSONAJG", "#{tipopersonajg,jdbcType=VARCHAR}");
		}
		if (record.getIdtipoidentificacion() != null) {
			sql.VALUES("IDTIPOIDENTIFICACION", "#{idtipoidentificacion,jdbcType=DECIMAL}");
		}
		if (record.getObservaciones() != null) {
			sql.VALUES("OBSERVACIONES", "#{observaciones,jdbcType=VARCHAR}");
		}
		if (record.getIdrepresentantejg() != null) {
			sql.VALUES("IDREPRESENTANTEJG", "#{idrepresentantejg,jdbcType=DECIMAL}");
		}
		if (record.getIdtipoencalidad() != null) {
			sql.VALUES("IDTIPOENCALIDAD", "#{idtipoencalidad,jdbcType=DECIMAL}");
		}
		if (record.getSexo() != null) {
			sql.VALUES("SEXO", "#{sexo,jdbcType=VARCHAR}");
		}
		if (record.getIdlenguaje() != null) {
			sql.VALUES("IDLENGUAJE", "#{idlenguaje,jdbcType=VARCHAR}");
		}
		if (record.getNumerohijos() != null) {
			sql.VALUES("NUMEROHIJOS", "#{numerohijos,jdbcType=DECIMAL}");
		}
		if (record.getFax() != null) {
			sql.VALUES("FAX", "#{fax,jdbcType=VARCHAR}");
		}
		if (record.getCorreoelectronico() != null) {
			sql.VALUES("CORREOELECTRONICO", "#{correoelectronico,jdbcType=VARCHAR}");
		}
		if (record.getEdad() != null) {
			sql.VALUES("EDAD", "#{edad,jdbcType=DECIMAL}");
		}
		if (record.getIdminusvalia() != null) {
			sql.VALUES("IDMINUSVALIA", "#{idminusvalia,jdbcType=DECIMAL}");
		}
		if (record.getExistedomicilio() != null) {
			sql.VALUES("EXISTEDOMICILIO", "#{existedomicilio,jdbcType=VARCHAR}");
		}
		if (record.getIdprovincia2() != null) {
			sql.VALUES("IDPROVINCIA2", "#{idprovincia2,jdbcType=VARCHAR}");
		}
		if (record.getIdpoblacion2() != null) {
			sql.VALUES("IDPOBLACION2", "#{idpoblacion2,jdbcType=VARCHAR}");
		}
		if (record.getDireccion2() != null) {
			sql.VALUES("DIRECCION2", "#{direccion2,jdbcType=VARCHAR}");
		}
		if (record.getCodigopostal2() != null) {
			sql.VALUES("CODIGOPOSTAL2", "#{codigopostal2,jdbcType=VARCHAR}");
		}
		if (record.getIdtipodir() != null) {
			sql.VALUES("IDTIPODIR", "#{idtipodir,jdbcType=VARCHAR}");
		}
		if (record.getIdtipodir2() != null) {
			sql.VALUES("IDTIPODIR2", "#{idtipodir2,jdbcType=VARCHAR}");
		}
		if (record.getCnae() != null) {
			sql.VALUES("CNAE", "#{cnae,jdbcType=VARCHAR}");
		}
		if (record.getIdtipovia() != null) {
			sql.VALUES("IDTIPOVIA", "#{idtipovia,jdbcType=VARCHAR}");
		}
		if (record.getNumerodir() != null) {
			sql.VALUES("NUMERODIR", "#{numerodir,jdbcType=VARCHAR}");
		}
		if (record.getEscaleradir() != null) {
			sql.VALUES("ESCALERADIR", "#{escaleradir,jdbcType=VARCHAR}");
		}
		if (record.getPisodir() != null) {
			sql.VALUES("PISODIR", "#{pisodir,jdbcType=VARCHAR}");
		}
		if (record.getPuertadir() != null) {
			sql.VALUES("PUERTADIR", "#{puertadir,jdbcType=VARCHAR}");
		}
		if (record.getIdtipovia2() != null) {
			sql.VALUES("IDTIPOVIA2", "#{idtipovia2,jdbcType=VARCHAR}");
		}
		if (record.getNumerodir2() != null) {
			sql.VALUES("NUMERODIR2", "#{numerodir2,jdbcType=VARCHAR}");
		}
		if (record.getEscaleradir2() != null) {
			sql.VALUES("ESCALERADIR2", "#{escaleradir2,jdbcType=VARCHAR}");
		}
		if (record.getPisodir2() != null) {
			sql.VALUES("PISODIR2", "#{pisodir2,jdbcType=VARCHAR}");
		}
		if (record.getPuertadir2() != null) {
			sql.VALUES("PUERTADIR2", "#{puertadir2,jdbcType=VARCHAR}");
		}
		if (record.getIdpaisdir1() != null) {
			sql.VALUES("IDPAISDIR1", "#{idpaisdir1,jdbcType=VARCHAR}");
		}
		if (record.getIdpaisdir2() != null) {
			sql.VALUES("IDPAISDIR2", "#{idpaisdir2,jdbcType=VARCHAR}");
		}
		if (record.getDescpaisdir1() != null) {
			sql.VALUES("DESCPAISDIR1", "#{descpaisdir1,jdbcType=VARCHAR}");
		}
		if (record.getDescpaisdir2() != null) {
			sql.VALUES("DESCPAISDIR2", "#{descpaisdir2,jdbcType=VARCHAR}");
		}
		if (record.getIdtipoidentificacionotros() != null) {
			sql.VALUES("IDTIPOIDENTIFICACIONOTROS", "#{idtipoidentificacionotros,jdbcType=VARCHAR}");
		}
		if (record.getAsistidosolicitajg() != null) {
			sql.VALUES("ASISTIDOSOLICITAJG", "#{asistidosolicitajg,jdbcType=VARCHAR}");
		}
		if (record.getAsistidoautorizaeejg() != null) {
			sql.VALUES("ASISTIDOAUTORIZAEEJG", "#{asistidoautorizaeejg,jdbcType=VARCHAR}");
		}
		if (record.getAutorizaavisotelematico() != null) {
			sql.VALUES("AUTORIZAAVISOTELEMATICO", "#{autorizaavisotelematico,jdbcType=VARCHAR}");
		}
		if (record.getFechaalta() != null) {
			sql.VALUES("FECHAALTA", "#{fechaalta,jdbcType=TIMESTAMP}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PERSONAJG
	 * @mbg.generated  Tue Nov 19 14:39:46 CET 2019
	 */
	public String selectByExample(ScsPersonajgExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDINSTITUCION");
		} else {
			sql.SELECT("IDINSTITUCION");
		}
		sql.SELECT("IDPERSONA");
		sql.SELECT("FECHANACIMIENTO");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("IDPAIS");
		sql.SELECT("NIF");
		sql.SELECT("NOMBRE");
		sql.SELECT("APELLIDO1");
		sql.SELECT("APELLIDO2");
		sql.SELECT("DIRECCION");
		sql.SELECT("CODIGOPOSTAL");
		sql.SELECT("IDPROFESION");
		sql.SELECT("REGIMEN_CONYUGAL");
		sql.SELECT("IDPROVINCIA");
		sql.SELECT("IDPOBLACION");
		sql.SELECT("IDESTADOCIVIL");
		sql.SELECT("TIPOPERSONAJG");
		sql.SELECT("IDTIPOIDENTIFICACION");
		sql.SELECT("OBSERVACIONES");
		sql.SELECT("IDREPRESENTANTEJG");
		sql.SELECT("IDTIPOENCALIDAD");
		sql.SELECT("SEXO");
		sql.SELECT("IDLENGUAJE");
		sql.SELECT("NUMEROHIJOS");
		sql.SELECT("FAX");
		sql.SELECT("CORREOELECTRONICO");
		sql.SELECT("EDAD");
		sql.SELECT("IDMINUSVALIA");
		sql.SELECT("EXISTEDOMICILIO");
		sql.SELECT("IDPROVINCIA2");
		sql.SELECT("IDPOBLACION2");
		sql.SELECT("DIRECCION2");
		sql.SELECT("CODIGOPOSTAL2");
		sql.SELECT("IDTIPODIR");
		sql.SELECT("IDTIPODIR2");
		sql.SELECT("CNAE");
		sql.SELECT("IDTIPOVIA");
		sql.SELECT("NUMERODIR");
		sql.SELECT("ESCALERADIR");
		sql.SELECT("PISODIR");
		sql.SELECT("PUERTADIR");
		sql.SELECT("IDTIPOVIA2");
		sql.SELECT("NUMERODIR2");
		sql.SELECT("ESCALERADIR2");
		sql.SELECT("PISODIR2");
		sql.SELECT("PUERTADIR2");
		sql.SELECT("IDPAISDIR1");
		sql.SELECT("IDPAISDIR2");
		sql.SELECT("DESCPAISDIR1");
		sql.SELECT("DESCPAISDIR2");
		sql.SELECT("IDTIPOIDENTIFICACIONOTROS");
		sql.SELECT("ASISTIDOSOLICITAJG");
		sql.SELECT("ASISTIDOAUTORIZAEEJG");
		sql.SELECT("AUTORIZAAVISOTELEMATICO");
		sql.SELECT("FECHAALTA");
		sql.FROM("SCS_PERSONAJG");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PERSONAJG
	 * @mbg.generated  Tue Nov 19 14:39:46 CET 2019
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		ScsPersonajg record = (ScsPersonajg) parameter.get("record");
		ScsPersonajgExample example = (ScsPersonajgExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("SCS_PERSONAJG");
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdpersona() != null) {
			sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
		}
		if (record.getFechanacimiento() != null) {
			sql.SET("FECHANACIMIENTO = #{record.fechanacimiento,jdbcType=TIMESTAMP}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdpais() != null) {
			sql.SET("IDPAIS = #{record.idpais,jdbcType=VARCHAR}");
		}
		if (record.getNif() != null) {
			sql.SET("NIF = #{record.nif,jdbcType=VARCHAR}");
		}
		if (record.getNombre() != null) {
			sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
		}
		if (record.getApellido1() != null) {
			sql.SET("APELLIDO1 = #{record.apellido1,jdbcType=VARCHAR}");
		}
		if (record.getApellido2() != null) {
			sql.SET("APELLIDO2 = #{record.apellido2,jdbcType=VARCHAR}");
		}
		if (record.getDireccion() != null) {
			sql.SET("DIRECCION = #{record.direccion,jdbcType=VARCHAR}");
		}
		if (record.getCodigopostal() != null) {
			sql.SET("CODIGOPOSTAL = #{record.codigopostal,jdbcType=VARCHAR}");
		}
		if (record.getIdprofesion() != null) {
			sql.SET("IDPROFESION = #{record.idprofesion,jdbcType=DECIMAL}");
		}
		if (record.getRegimenConyugal() != null) {
			sql.SET("REGIMEN_CONYUGAL = #{record.regimenConyugal,jdbcType=VARCHAR}");
		}
		if (record.getIdprovincia() != null) {
			sql.SET("IDPROVINCIA = #{record.idprovincia,jdbcType=VARCHAR}");
		}
		if (record.getIdpoblacion() != null) {
			sql.SET("IDPOBLACION = #{record.idpoblacion,jdbcType=VARCHAR}");
		}
		if (record.getIdestadocivil() != null) {
			sql.SET("IDESTADOCIVIL = #{record.idestadocivil,jdbcType=DECIMAL}");
		}
		if (record.getTipopersonajg() != null) {
			sql.SET("TIPOPERSONAJG = #{record.tipopersonajg,jdbcType=VARCHAR}");
		}
		if (record.getIdtipoidentificacion() != null) {
			sql.SET("IDTIPOIDENTIFICACION = #{record.idtipoidentificacion,jdbcType=DECIMAL}");
		}
		if (record.getObservaciones() != null) {
			sql.SET("OBSERVACIONES = #{record.observaciones,jdbcType=VARCHAR}");
		}
		if (record.getIdrepresentantejg() != null) {
			sql.SET("IDREPRESENTANTEJG = #{record.idrepresentantejg,jdbcType=DECIMAL}");
		}
		if (record.getIdtipoencalidad() != null) {
			sql.SET("IDTIPOENCALIDAD = #{record.idtipoencalidad,jdbcType=DECIMAL}");
		}
		if (record.getSexo() != null) {
			sql.SET("SEXO = #{record.sexo,jdbcType=VARCHAR}");
		}
		if (record.getIdlenguaje() != null) {
			sql.SET("IDLENGUAJE = #{record.idlenguaje,jdbcType=VARCHAR}");
		}
		if (record.getNumerohijos() != null) {
			sql.SET("NUMEROHIJOS = #{record.numerohijos,jdbcType=DECIMAL}");
		}
		if (record.getFax() != null) {
			sql.SET("FAX = #{record.fax,jdbcType=VARCHAR}");
		}
		if (record.getCorreoelectronico() != null) {
			sql.SET("CORREOELECTRONICO = #{record.correoelectronico,jdbcType=VARCHAR}");
		}
		if (record.getEdad() != null) {
			sql.SET("EDAD = #{record.edad,jdbcType=DECIMAL}");
		}
		if (record.getIdminusvalia() != null) {
			sql.SET("IDMINUSVALIA = #{record.idminusvalia,jdbcType=DECIMAL}");
		}
		if (record.getExistedomicilio() != null) {
			sql.SET("EXISTEDOMICILIO = #{record.existedomicilio,jdbcType=VARCHAR}");
		}
		if (record.getIdprovincia2() != null) {
			sql.SET("IDPROVINCIA2 = #{record.idprovincia2,jdbcType=VARCHAR}");
		}
		if (record.getIdpoblacion2() != null) {
			sql.SET("IDPOBLACION2 = #{record.idpoblacion2,jdbcType=VARCHAR}");
		}
		if (record.getDireccion2() != null) {
			sql.SET("DIRECCION2 = #{record.direccion2,jdbcType=VARCHAR}");
		}
		if (record.getCodigopostal2() != null) {
			sql.SET("CODIGOPOSTAL2 = #{record.codigopostal2,jdbcType=VARCHAR}");
		}
		if (record.getIdtipodir() != null) {
			sql.SET("IDTIPODIR = #{record.idtipodir,jdbcType=VARCHAR}");
		}
		if (record.getIdtipodir2() != null) {
			sql.SET("IDTIPODIR2 = #{record.idtipodir2,jdbcType=VARCHAR}");
		}
		if (record.getCnae() != null) {
			sql.SET("CNAE = #{record.cnae,jdbcType=VARCHAR}");
		}
		if (record.getIdtipovia() != null) {
			sql.SET("IDTIPOVIA = #{record.idtipovia,jdbcType=VARCHAR}");
		}
		if (record.getNumerodir() != null) {
			sql.SET("NUMERODIR = #{record.numerodir,jdbcType=VARCHAR}");
		}
		if (record.getEscaleradir() != null) {
			sql.SET("ESCALERADIR = #{record.escaleradir,jdbcType=VARCHAR}");
		}
		if (record.getPisodir() != null) {
			sql.SET("PISODIR = #{record.pisodir,jdbcType=VARCHAR}");
		}
		if (record.getPuertadir() != null) {
			sql.SET("PUERTADIR = #{record.puertadir,jdbcType=VARCHAR}");
		}
		if (record.getIdtipovia2() != null) {
			sql.SET("IDTIPOVIA2 = #{record.idtipovia2,jdbcType=VARCHAR}");
		}
		if (record.getNumerodir2() != null) {
			sql.SET("NUMERODIR2 = #{record.numerodir2,jdbcType=VARCHAR}");
		}
		if (record.getEscaleradir2() != null) {
			sql.SET("ESCALERADIR2 = #{record.escaleradir2,jdbcType=VARCHAR}");
		}
		if (record.getPisodir2() != null) {
			sql.SET("PISODIR2 = #{record.pisodir2,jdbcType=VARCHAR}");
		}
		if (record.getPuertadir2() != null) {
			sql.SET("PUERTADIR2 = #{record.puertadir2,jdbcType=VARCHAR}");
		}
		if (record.getIdpaisdir1() != null) {
			sql.SET("IDPAISDIR1 = #{record.idpaisdir1,jdbcType=VARCHAR}");
		}
		if (record.getIdpaisdir2() != null) {
			sql.SET("IDPAISDIR2 = #{record.idpaisdir2,jdbcType=VARCHAR}");
		}
		if (record.getDescpaisdir1() != null) {
			sql.SET("DESCPAISDIR1 = #{record.descpaisdir1,jdbcType=VARCHAR}");
		}
		if (record.getDescpaisdir2() != null) {
			sql.SET("DESCPAISDIR2 = #{record.descpaisdir2,jdbcType=VARCHAR}");
		}
		if (record.getIdtipoidentificacionotros() != null) {
			sql.SET("IDTIPOIDENTIFICACIONOTROS = #{record.idtipoidentificacionotros,jdbcType=VARCHAR}");
		}
		if (record.getAsistidosolicitajg() != null) {
			sql.SET("ASISTIDOSOLICITAJG = #{record.asistidosolicitajg,jdbcType=VARCHAR}");
		}
		if (record.getAsistidoautorizaeejg() != null) {
			sql.SET("ASISTIDOAUTORIZAEEJG = #{record.asistidoautorizaeejg,jdbcType=VARCHAR}");
		}
		if (record.getAutorizaavisotelematico() != null) {
			sql.SET("AUTORIZAAVISOTELEMATICO = #{record.autorizaavisotelematico,jdbcType=VARCHAR}");
		}
		if (record.getFechaalta() != null) {
			sql.SET("FECHAALTA = #{record.fechaalta,jdbcType=TIMESTAMP}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PERSONAJG
	 * @mbg.generated  Tue Nov 19 14:39:46 CET 2019
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("SCS_PERSONAJG");
		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		sql.SET("IDPERSONA = #{record.idpersona,jdbcType=DECIMAL}");
		sql.SET("FECHANACIMIENTO = #{record.fechanacimiento,jdbcType=TIMESTAMP}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("IDPAIS = #{record.idpais,jdbcType=VARCHAR}");
		sql.SET("NIF = #{record.nif,jdbcType=VARCHAR}");
		sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
		sql.SET("APELLIDO1 = #{record.apellido1,jdbcType=VARCHAR}");
		sql.SET("APELLIDO2 = #{record.apellido2,jdbcType=VARCHAR}");
		sql.SET("DIRECCION = #{record.direccion,jdbcType=VARCHAR}");
		sql.SET("CODIGOPOSTAL = #{record.codigopostal,jdbcType=VARCHAR}");
		sql.SET("IDPROFESION = #{record.idprofesion,jdbcType=DECIMAL}");
		sql.SET("REGIMEN_CONYUGAL = #{record.regimenConyugal,jdbcType=VARCHAR}");
		sql.SET("IDPROVINCIA = #{record.idprovincia,jdbcType=VARCHAR}");
		sql.SET("IDPOBLACION = #{record.idpoblacion,jdbcType=VARCHAR}");
		sql.SET("IDESTADOCIVIL = #{record.idestadocivil,jdbcType=DECIMAL}");
		sql.SET("TIPOPERSONAJG = #{record.tipopersonajg,jdbcType=VARCHAR}");
		sql.SET("IDTIPOIDENTIFICACION = #{record.idtipoidentificacion,jdbcType=DECIMAL}");
		sql.SET("OBSERVACIONES = #{record.observaciones,jdbcType=VARCHAR}");
		sql.SET("IDREPRESENTANTEJG = #{record.idrepresentantejg,jdbcType=DECIMAL}");
		sql.SET("IDTIPOENCALIDAD = #{record.idtipoencalidad,jdbcType=DECIMAL}");
		sql.SET("SEXO = #{record.sexo,jdbcType=VARCHAR}");
		sql.SET("IDLENGUAJE = #{record.idlenguaje,jdbcType=VARCHAR}");
		sql.SET("NUMEROHIJOS = #{record.numerohijos,jdbcType=DECIMAL}");
		sql.SET("FAX = #{record.fax,jdbcType=VARCHAR}");
		sql.SET("CORREOELECTRONICO = #{record.correoelectronico,jdbcType=VARCHAR}");
		sql.SET("EDAD = #{record.edad,jdbcType=DECIMAL}");
		sql.SET("IDMINUSVALIA = #{record.idminusvalia,jdbcType=DECIMAL}");
		sql.SET("EXISTEDOMICILIO = #{record.existedomicilio,jdbcType=VARCHAR}");
		sql.SET("IDPROVINCIA2 = #{record.idprovincia2,jdbcType=VARCHAR}");
		sql.SET("IDPOBLACION2 = #{record.idpoblacion2,jdbcType=VARCHAR}");
		sql.SET("DIRECCION2 = #{record.direccion2,jdbcType=VARCHAR}");
		sql.SET("CODIGOPOSTAL2 = #{record.codigopostal2,jdbcType=VARCHAR}");
		sql.SET("IDTIPODIR = #{record.idtipodir,jdbcType=VARCHAR}");
		sql.SET("IDTIPODIR2 = #{record.idtipodir2,jdbcType=VARCHAR}");
		sql.SET("CNAE = #{record.cnae,jdbcType=VARCHAR}");
		sql.SET("IDTIPOVIA = #{record.idtipovia,jdbcType=VARCHAR}");
		sql.SET("NUMERODIR = #{record.numerodir,jdbcType=VARCHAR}");
		sql.SET("ESCALERADIR = #{record.escaleradir,jdbcType=VARCHAR}");
		sql.SET("PISODIR = #{record.pisodir,jdbcType=VARCHAR}");
		sql.SET("PUERTADIR = #{record.puertadir,jdbcType=VARCHAR}");
		sql.SET("IDTIPOVIA2 = #{record.idtipovia2,jdbcType=VARCHAR}");
		sql.SET("NUMERODIR2 = #{record.numerodir2,jdbcType=VARCHAR}");
		sql.SET("ESCALERADIR2 = #{record.escaleradir2,jdbcType=VARCHAR}");
		sql.SET("PISODIR2 = #{record.pisodir2,jdbcType=VARCHAR}");
		sql.SET("PUERTADIR2 = #{record.puertadir2,jdbcType=VARCHAR}");
		sql.SET("IDPAISDIR1 = #{record.idpaisdir1,jdbcType=VARCHAR}");
		sql.SET("IDPAISDIR2 = #{record.idpaisdir2,jdbcType=VARCHAR}");
		sql.SET("DESCPAISDIR1 = #{record.descpaisdir1,jdbcType=VARCHAR}");
		sql.SET("DESCPAISDIR2 = #{record.descpaisdir2,jdbcType=VARCHAR}");
		sql.SET("IDTIPOIDENTIFICACIONOTROS = #{record.idtipoidentificacionotros,jdbcType=VARCHAR}");
		sql.SET("ASISTIDOSOLICITAJG = #{record.asistidosolicitajg,jdbcType=VARCHAR}");
		sql.SET("ASISTIDOAUTORIZAEEJG = #{record.asistidoautorizaeejg,jdbcType=VARCHAR}");
		sql.SET("AUTORIZAAVISOTELEMATICO = #{record.autorizaavisotelematico,jdbcType=VARCHAR}");
		sql.SET("FECHAALTA = #{record.fechaalta,jdbcType=TIMESTAMP}");
		ScsPersonajgExample example = (ScsPersonajgExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PERSONAJG
	 * @mbg.generated  Tue Nov 19 14:39:46 CET 2019
	 */
	public String updateByPrimaryKeySelective(ScsPersonajg record) {
		SQL sql = new SQL();
		sql.UPDATE("SCS_PERSONAJG");
		if (record.getFechanacimiento() != null) {
			sql.SET("FECHANACIMIENTO = #{fechanacimiento,jdbcType=TIMESTAMP}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdpais() != null) {
			sql.SET("IDPAIS = #{idpais,jdbcType=VARCHAR}");
		}
		if (record.getNif() != null) {
			sql.SET("NIF = #{nif,jdbcType=VARCHAR}");
		}
		if (record.getNombre() != null) {
			sql.SET("NOMBRE = #{nombre,jdbcType=VARCHAR}");
		}
		if (record.getApellido1() != null) {
			sql.SET("APELLIDO1 = #{apellido1,jdbcType=VARCHAR}");
		}
		if (record.getApellido2() != null) {
			sql.SET("APELLIDO2 = #{apellido2,jdbcType=VARCHAR}");
		}
		if (record.getDireccion() != null) {
			sql.SET("DIRECCION = #{direccion,jdbcType=VARCHAR}");
		}
		if (record.getCodigopostal() != null) {
			sql.SET("CODIGOPOSTAL = #{codigopostal,jdbcType=VARCHAR}");
		}
		if (record.getIdprofesion() != null) {
			sql.SET("IDPROFESION = #{idprofesion,jdbcType=DECIMAL}");
		}
		if (record.getRegimenConyugal() != null) {
			sql.SET("REGIMEN_CONYUGAL = #{regimenConyugal,jdbcType=VARCHAR}");
		}
		if (record.getIdprovincia() != null) {
			sql.SET("IDPROVINCIA = #{idprovincia,jdbcType=VARCHAR}");
		}
		if (record.getIdpoblacion() != null) {
			sql.SET("IDPOBLACION = #{idpoblacion,jdbcType=VARCHAR}");
		}
		if (record.getIdestadocivil() != null) {
			sql.SET("IDESTADOCIVIL = #{idestadocivil,jdbcType=DECIMAL}");
		}
		if (record.getTipopersonajg() != null) {
			sql.SET("TIPOPERSONAJG = #{tipopersonajg,jdbcType=VARCHAR}");
		}
		if (record.getIdtipoidentificacion() != null) {
			sql.SET("IDTIPOIDENTIFICACION = #{idtipoidentificacion,jdbcType=DECIMAL}");
		}
		if (record.getObservaciones() != null) {
			sql.SET("OBSERVACIONES = #{observaciones,jdbcType=VARCHAR}");
		}
		if (record.getIdrepresentantejg() != null) {
			sql.SET("IDREPRESENTANTEJG = #{idrepresentantejg,jdbcType=DECIMAL}");
		}
		if (record.getIdtipoencalidad() != null) {
			sql.SET("IDTIPOENCALIDAD = #{idtipoencalidad,jdbcType=DECIMAL}");
		}
		if (record.getSexo() != null) {
			sql.SET("SEXO = #{sexo,jdbcType=VARCHAR}");
		}
		if (record.getIdlenguaje() != null) {
			sql.SET("IDLENGUAJE = #{idlenguaje,jdbcType=VARCHAR}");
		}
		if (record.getNumerohijos() != null) {
			sql.SET("NUMEROHIJOS = #{numerohijos,jdbcType=DECIMAL}");
		}
		if (record.getFax() != null) {
			sql.SET("FAX = #{fax,jdbcType=VARCHAR}");
		}
		if (record.getCorreoelectronico() != null) {
			sql.SET("CORREOELECTRONICO = #{correoelectronico,jdbcType=VARCHAR}");
		}
		if (record.getEdad() != null) {
			sql.SET("EDAD = #{edad,jdbcType=DECIMAL}");
		}
		if (record.getIdminusvalia() != null) {
			sql.SET("IDMINUSVALIA = #{idminusvalia,jdbcType=DECIMAL}");
		}
		if (record.getExistedomicilio() != null) {
			sql.SET("EXISTEDOMICILIO = #{existedomicilio,jdbcType=VARCHAR}");
		}
		if (record.getIdprovincia2() != null) {
			sql.SET("IDPROVINCIA2 = #{idprovincia2,jdbcType=VARCHAR}");
		}
		if (record.getIdpoblacion2() != null) {
			sql.SET("IDPOBLACION2 = #{idpoblacion2,jdbcType=VARCHAR}");
		}
		if (record.getDireccion2() != null) {
			sql.SET("DIRECCION2 = #{direccion2,jdbcType=VARCHAR}");
		}
		if (record.getCodigopostal2() != null) {
			sql.SET("CODIGOPOSTAL2 = #{codigopostal2,jdbcType=VARCHAR}");
		}
		if (record.getIdtipodir() != null) {
			sql.SET("IDTIPODIR = #{idtipodir,jdbcType=VARCHAR}");
		}
		if (record.getIdtipodir2() != null) {
			sql.SET("IDTIPODIR2 = #{idtipodir2,jdbcType=VARCHAR}");
		}
		if (record.getCnae() != null) {
			sql.SET("CNAE = #{cnae,jdbcType=VARCHAR}");
		}
		if (record.getIdtipovia() != null) {
			sql.SET("IDTIPOVIA = #{idtipovia,jdbcType=VARCHAR}");
		}
		if (record.getNumerodir() != null) {
			sql.SET("NUMERODIR = #{numerodir,jdbcType=VARCHAR}");
		}
		if (record.getEscaleradir() != null) {
			sql.SET("ESCALERADIR = #{escaleradir,jdbcType=VARCHAR}");
		}
		if (record.getPisodir() != null) {
			sql.SET("PISODIR = #{pisodir,jdbcType=VARCHAR}");
		}
		if (record.getPuertadir() != null) {
			sql.SET("PUERTADIR = #{puertadir,jdbcType=VARCHAR}");
		}
		if (record.getIdtipovia2() != null) {
			sql.SET("IDTIPOVIA2 = #{idtipovia2,jdbcType=VARCHAR}");
		}
		if (record.getNumerodir2() != null) {
			sql.SET("NUMERODIR2 = #{numerodir2,jdbcType=VARCHAR}");
		}
		if (record.getEscaleradir2() != null) {
			sql.SET("ESCALERADIR2 = #{escaleradir2,jdbcType=VARCHAR}");
		}
		if (record.getPisodir2() != null) {
			sql.SET("PISODIR2 = #{pisodir2,jdbcType=VARCHAR}");
		}
		if (record.getPuertadir2() != null) {
			sql.SET("PUERTADIR2 = #{puertadir2,jdbcType=VARCHAR}");
		}
		if (record.getIdpaisdir1() != null) {
			sql.SET("IDPAISDIR1 = #{idpaisdir1,jdbcType=VARCHAR}");
		}
		if (record.getIdpaisdir2() != null) {
			sql.SET("IDPAISDIR2 = #{idpaisdir2,jdbcType=VARCHAR}");
		}
		if (record.getDescpaisdir1() != null) {
			sql.SET("DESCPAISDIR1 = #{descpaisdir1,jdbcType=VARCHAR}");
		}
		if (record.getDescpaisdir2() != null) {
			sql.SET("DESCPAISDIR2 = #{descpaisdir2,jdbcType=VARCHAR}");
		}
		if (record.getIdtipoidentificacionotros() != null) {
			sql.SET("IDTIPOIDENTIFICACIONOTROS = #{idtipoidentificacionotros,jdbcType=VARCHAR}");
		}
		if (record.getAsistidosolicitajg() != null) {
			sql.SET("ASISTIDOSOLICITAJG = #{asistidosolicitajg,jdbcType=VARCHAR}");
		}
		if (record.getAsistidoautorizaeejg() != null) {
			sql.SET("ASISTIDOAUTORIZAEEJG = #{asistidoautorizaeejg,jdbcType=VARCHAR}");
		}
		if (record.getAutorizaavisotelematico() != null) {
			sql.SET("AUTORIZAAVISOTELEMATICO = #{autorizaavisotelematico,jdbcType=VARCHAR}");
		}
		if (record.getFechaalta() != null) {
			sql.SET("FECHAALTA = #{fechaalta,jdbcType=TIMESTAMP}");
		}
		sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
		sql.WHERE("IDPERSONA = #{idpersona,jdbcType=DECIMAL}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_PERSONAJG
	 * @mbg.generated  Tue Nov 19 14:39:46 CET 2019
	 */
	protected void applyWhere(SQL sql, ScsPersonajgExample example, boolean includeExamplePhrase) {
		if (example == null) {
			return;
		}
		String parmPhrase1;
		String parmPhrase1_th;
		String parmPhrase2;
		String parmPhrase2_th;
		String parmPhrase3;
		String parmPhrase3_th;
		if (includeExamplePhrase) {
			parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
			parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
			parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
			parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
			parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
			parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
		} else {
			parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
			parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
			parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
			parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
			parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
			parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
		}
		StringBuilder sb = new StringBuilder();
		List<Criteria> oredCriteria = example.getOredCriteria();
		boolean firstCriteria = true;
		for (int i = 0; i < oredCriteria.size(); i++) {
			Criteria criteria = oredCriteria.get(i);
			if (criteria.isValid()) {
				if (firstCriteria) {
					firstCriteria = false;
				} else {
					sb.append(" or ");
				}
				sb.append('(');
				List<Criterion> criterions = criteria.getAllCriteria();
				boolean firstCriterion = true;
				for (int j = 0; j < criterions.size(); j++) {
					Criterion criterion = criterions.get(j);
					if (firstCriterion) {
						firstCriterion = false;
					} else {
						sb.append(" and ");
					}
					if (criterion.isNoValue()) {
						sb.append(criterion.getCondition());
					} else if (criterion.isSingleValue()) {
						if (criterion.getTypeHandler() == null) {
							sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
						} else {
							sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,
									criterion.getTypeHandler()));
						}
					} else if (criterion.isBetweenValue()) {
						if (criterion.getTypeHandler() == null) {
							sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
						} else {
							sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j,
									criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
						}
					} else if (criterion.isListValue()) {
						sb.append(criterion.getCondition());
						sb.append(" (");
						List<?> listItems = (List<?>) criterion.getValue();
						boolean comma = false;
						for (int k = 0; k < listItems.size(); k++) {
							if (comma) {
								sb.append(", ");
							} else {
								comma = true;
							}
							if (criterion.getTypeHandler() == null) {
								sb.append(String.format(parmPhrase3, i, j, k));
							} else {
								sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
							}
						}
						sb.append(')');
					}
				}
				sb.append(')');
			}
		}
		if (sb.length() > 0) {
			sql.WHERE(sb.toString());
		}
	}
}