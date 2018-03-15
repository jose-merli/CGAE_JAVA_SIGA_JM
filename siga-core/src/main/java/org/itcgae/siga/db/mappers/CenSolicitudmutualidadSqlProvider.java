package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.CenSolicitudmutualidad;
import org.itcgae.siga.db.entities.CenSolicitudmutualidadExample.Criteria;
import org.itcgae.siga.db.entities.CenSolicitudmutualidadExample.Criterion;
import org.itcgae.siga.db.entities.CenSolicitudmutualidadExample;

public class CenSolicitudmutualidadSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_SOLICITUDMUTUALIDAD
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String countByExample(CenSolicitudmutualidadExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("CEN_SOLICITUDMUTUALIDAD");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_SOLICITUDMUTUALIDAD
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String deleteByExample(CenSolicitudmutualidadExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("CEN_SOLICITUDMUTUALIDAD");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_SOLICITUDMUTUALIDAD
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String insertSelective(CenSolicitudmutualidad record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("CEN_SOLICITUDMUTUALIDAD");
		if (record.getIdsolicitud() != null) {
			sql.VALUES("IDSOLICITUD", "#{idsolicitud,jdbcType=DECIMAL}");
		}
		if (record.getIdsolicitudincorporacion() != null) {
			sql.VALUES("IDSOLICITUDINCORPORACION", "#{idsolicitudincorporacion,jdbcType=DECIMAL}");
		}
		if (record.getFechasolicitud() != null) {
			sql.VALUES("FECHASOLICITUD", "#{fechasolicitud,jdbcType=TIMESTAMP}");
		}
		if (record.getIdtratamiento() != null) {
			sql.VALUES("IDTRATAMIENTO", "#{idtratamiento,jdbcType=DECIMAL}");
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
		if (record.getNumeroidentificador() != null) {
			sql.VALUES("NUMEROIDENTIFICADOR", "#{numeroidentificador,jdbcType=VARCHAR}");
		}
		if (record.getDomicilio() != null) {
			sql.VALUES("DOMICILIO", "#{domicilio,jdbcType=VARCHAR}");
		}
		if (record.getCodigopostal() != null) {
			sql.VALUES("CODIGOPOSTAL", "#{codigopostal,jdbcType=VARCHAR}");
		}
		if (record.getTelefono1() != null) {
			sql.VALUES("TELEFONO1", "#{telefono1,jdbcType=VARCHAR}");
		}
		if (record.getCorreoelectronico() != null) {
			sql.VALUES("CORREOELECTRONICO", "#{correoelectronico,jdbcType=VARCHAR}");
		}
		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdestado() != null) {
			sql.VALUES("IDESTADO", "#{idestado,jdbcType=DECIMAL}");
		}
		if (record.getIdtiposolicitud() != null) {
			sql.VALUES("IDTIPOSOLICITUD", "#{idtiposolicitud,jdbcType=CHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getFechanacimiento() != null) {
			sql.VALUES("FECHANACIMIENTO", "#{fechanacimiento,jdbcType=TIMESTAMP}");
		}
		if (record.getIdtipoidentificacion() != null) {
			sql.VALUES("IDTIPOIDENTIFICACION", "#{idtipoidentificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdprovincia() != null) {
			sql.VALUES("IDPROVINCIA", "#{idprovincia,jdbcType=VARCHAR}");
		}
		if (record.getIdpoblacion() != null) {
			sql.VALUES("IDPOBLACION", "#{idpoblacion,jdbcType=VARCHAR}");
		}
		if (record.getFechaestado() != null) {
			sql.VALUES("FECHAESTADO", "#{fechaestado,jdbcType=TIMESTAMP}");
		}
		if (record.getTelefono2() != null) {
			sql.VALUES("TELEFONO2", "#{telefono2,jdbcType=VARCHAR}");
		}
		if (record.getMovil() != null) {
			sql.VALUES("MOVIL", "#{movil,jdbcType=VARCHAR}");
		}
		if (record.getFax1() != null) {
			sql.VALUES("FAX1", "#{fax1,jdbcType=VARCHAR}");
		}
		if (record.getFax2() != null) {
			sql.VALUES("FAX2", "#{fax2,jdbcType=VARCHAR}");
		}
		if (record.getIdestadocivil() != null) {
			sql.VALUES("IDESTADOCIVIL", "#{idestadocivil,jdbcType=DECIMAL}");
		}
		if (record.getIdpais() != null) {
			sql.VALUES("IDPAIS", "#{idpais,jdbcType=VARCHAR}");
		}
		if (record.getNaturalde() != null) {
			sql.VALUES("NATURALDE", "#{naturalde,jdbcType=VARCHAR}");
		}
		if (record.getIdsexo() != null) {
			sql.VALUES("IDSEXO", "#{idsexo,jdbcType=VARCHAR}");
		}
		if (record.getPoblacionextranjera() != null) {
			sql.VALUES("POBLACIONEXTRANJERA", "#{poblacionextranjera,jdbcType=VARCHAR}");
		}
		if (record.getTitular() != null) {
			sql.VALUES("TITULAR", "#{titular,jdbcType=VARCHAR}");
		}
		if (record.getCodigosucursal() != null) {
			sql.VALUES("CODIGOSUCURSAL", "#{codigosucursal,jdbcType=VARCHAR}");
		}
		if (record.getCboCodigo() != null) {
			sql.VALUES("CBO_CODIGO", "#{cboCodigo,jdbcType=VARCHAR}");
		}
		if (record.getDigitocontrol() != null) {
			sql.VALUES("DIGITOCONTROL", "#{digitocontrol,jdbcType=VARCHAR}");
		}
		if (record.getNumerocuenta() != null) {
			sql.VALUES("NUMEROCUENTA", "#{numerocuenta,jdbcType=VARCHAR}");
		}
		if (record.getIban() != null) {
			sql.VALUES("IBAN", "#{iban,jdbcType=VARCHAR}");
		}
		if (record.getIdperiodicidadpago() != null) {
			sql.VALUES("IDPERIODICIDADPAGO", "#{idperiodicidadpago,jdbcType=VARCHAR}");
		}
		if (record.getIdcobertura() != null) {
			sql.VALUES("IDCOBERTURA", "#{idcobertura,jdbcType=VARCHAR}");
		}
		if (record.getIdbeneficiario() != null) {
			sql.VALUES("IDBENEFICIARIO", "#{idbeneficiario,jdbcType=VARCHAR}");
		}
		if (record.getOtrosbeneficiarios() != null) {
			sql.VALUES("OTROSBENEFICIARIOS", "#{otrosbeneficiarios,jdbcType=VARCHAR}");
		}
		if (record.getIdasistenciasanitaria() != null) {
			sql.VALUES("IDASISTENCIASANITARIA", "#{idasistenciasanitaria,jdbcType=VARCHAR}");
		}
		if (record.getFechanacimientoconyuge() != null) {
			sql.VALUES("FECHANACIMIENTOCONYUGE", "#{fechanacimientoconyuge,jdbcType=TIMESTAMP}");
		}
		if (record.getNumerohijos() != null) {
			sql.VALUES("NUMEROHIJOS", "#{numerohijos,jdbcType=DECIMAL}");
		}
		if (record.getEdadhijo1() != null) {
			sql.VALUES("EDADHIJO1", "#{edadhijo1,jdbcType=DECIMAL}");
		}
		if (record.getEdadhijo2() != null) {
			sql.VALUES("EDADHIJO2", "#{edadhijo2,jdbcType=DECIMAL}");
		}
		if (record.getEdadhijo3() != null) {
			sql.VALUES("EDADHIJO3", "#{edadhijo3,jdbcType=DECIMAL}");
		}
		if (record.getEdadhijo4() != null) {
			sql.VALUES("EDADHIJO4", "#{edadhijo4,jdbcType=DECIMAL}");
		}
		if (record.getEdadhijo5() != null) {
			sql.VALUES("EDADHIJO5", "#{edadhijo5,jdbcType=DECIMAL}");
		}
		if (record.getEdadhijo6() != null) {
			sql.VALUES("EDADHIJO6", "#{edadhijo6,jdbcType=DECIMAL}");
		}
		if (record.getEdadhijo7() != null) {
			sql.VALUES("EDADHIJO7", "#{edadhijo7,jdbcType=DECIMAL}");
		}
		if (record.getEdadhijo8() != null) {
			sql.VALUES("EDADHIJO8", "#{edadhijo8,jdbcType=DECIMAL}");
		}
		if (record.getEdadhijo9() != null) {
			sql.VALUES("EDADHIJO9", "#{edadhijo9,jdbcType=DECIMAL}");
		}
		if (record.getEdadhijo10() != null) {
			sql.VALUES("EDADHIJO10", "#{edadhijo10,jdbcType=DECIMAL}");
		}
		if (record.getPeriodicidadpago() != null) {
			sql.VALUES("PERIODICIDADPAGO", "#{periodicidadpago,jdbcType=VARCHAR}");
		}
		if (record.getCobertura() != null) {
			sql.VALUES("COBERTURA", "#{cobertura,jdbcType=VARCHAR}");
		}
		if (record.getBeneficiario() != null) {
			sql.VALUES("BENEFICIARIO", "#{beneficiario,jdbcType=VARCHAR}");
		}
		if (record.getAsistenciasanitaria() != null) {
			sql.VALUES("ASISTENCIASANITARIA", "#{asistenciasanitaria,jdbcType=VARCHAR}");
		}
		if (record.getSwift() != null) {
			sql.VALUES("SWIFT", "#{swift,jdbcType=VARCHAR}");
		}
		if (record.getCuotacobertura() != null) {
			sql.VALUES("CUOTACOBERTURA", "#{cuotacobertura,jdbcType=VARCHAR}");
		}
		if (record.getCapitalcobertura() != null) {
			sql.VALUES("CAPITALCOBERTURA", "#{capitalcobertura,jdbcType=VARCHAR}");
		}
		if (record.getIdsolicitudaceptada() != null) {
			sql.VALUES("IDSOLICITUDACEPTADA", "#{idsolicitudaceptada,jdbcType=DECIMAL}");
		}
		if (record.getProvincia() != null) {
			sql.VALUES("PROVINCIA", "#{provincia,jdbcType=VARCHAR}");
		}
		if (record.getPoblacion() != null) {
			sql.VALUES("POBLACION", "#{poblacion,jdbcType=VARCHAR}");
		}
		if (record.getPais() != null) {
			sql.VALUES("PAIS", "#{pais,jdbcType=VARCHAR}");
		}
		if (record.getEstado() != null) {
			sql.VALUES("ESTADO", "#{estado,jdbcType=VARCHAR}");
		}
		if (record.getEstadomutualista() != null) {
			sql.VALUES("ESTADOMUTUALISTA", "#{estadomutualista,jdbcType=VARCHAR}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_SOLICITUDMUTUALIDAD
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String selectByExample(CenSolicitudmutualidadExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDSOLICITUD");
		} else {
			sql.SELECT("IDSOLICITUD");
		}
		sql.SELECT("IDSOLICITUDINCORPORACION");
		sql.SELECT("FECHASOLICITUD");
		sql.SELECT("IDTRATAMIENTO");
		sql.SELECT("NOMBRE");
		sql.SELECT("APELLIDO1");
		sql.SELECT("APELLIDO2");
		sql.SELECT("NUMEROIDENTIFICADOR");
		sql.SELECT("DOMICILIO");
		sql.SELECT("CODIGOPOSTAL");
		sql.SELECT("TELEFONO1");
		sql.SELECT("CORREOELECTRONICO");
		sql.SELECT("IDINSTITUCION");
		sql.SELECT("IDESTADO");
		sql.SELECT("IDTIPOSOLICITUD");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("FECHANACIMIENTO");
		sql.SELECT("IDTIPOIDENTIFICACION");
		sql.SELECT("IDPROVINCIA");
		sql.SELECT("IDPOBLACION");
		sql.SELECT("FECHAESTADO");
		sql.SELECT("TELEFONO2");
		sql.SELECT("MOVIL");
		sql.SELECT("FAX1");
		sql.SELECT("FAX2");
		sql.SELECT("IDESTADOCIVIL");
		sql.SELECT("IDPAIS");
		sql.SELECT("NATURALDE");
		sql.SELECT("IDSEXO");
		sql.SELECT("POBLACIONEXTRANJERA");
		sql.SELECT("TITULAR");
		sql.SELECT("CODIGOSUCURSAL");
		sql.SELECT("CBO_CODIGO");
		sql.SELECT("DIGITOCONTROL");
		sql.SELECT("NUMEROCUENTA");
		sql.SELECT("IBAN");
		sql.SELECT("IDPERIODICIDADPAGO");
		sql.SELECT("IDCOBERTURA");
		sql.SELECT("IDBENEFICIARIO");
		sql.SELECT("OTROSBENEFICIARIOS");
		sql.SELECT("IDASISTENCIASANITARIA");
		sql.SELECT("FECHANACIMIENTOCONYUGE");
		sql.SELECT("NUMEROHIJOS");
		sql.SELECT("EDADHIJO1");
		sql.SELECT("EDADHIJO2");
		sql.SELECT("EDADHIJO3");
		sql.SELECT("EDADHIJO4");
		sql.SELECT("EDADHIJO5");
		sql.SELECT("EDADHIJO6");
		sql.SELECT("EDADHIJO7");
		sql.SELECT("EDADHIJO8");
		sql.SELECT("EDADHIJO9");
		sql.SELECT("EDADHIJO10");
		sql.SELECT("PERIODICIDADPAGO");
		sql.SELECT("COBERTURA");
		sql.SELECT("BENEFICIARIO");
		sql.SELECT("ASISTENCIASANITARIA");
		sql.SELECT("SWIFT");
		sql.SELECT("CUOTACOBERTURA");
		sql.SELECT("CAPITALCOBERTURA");
		sql.SELECT("IDSOLICITUDACEPTADA");
		sql.SELECT("PROVINCIA");
		sql.SELECT("POBLACION");
		sql.SELECT("PAIS");
		sql.SELECT("ESTADO");
		sql.SELECT("ESTADOMUTUALISTA");
		sql.FROM("CEN_SOLICITUDMUTUALIDAD");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_SOLICITUDMUTUALIDAD
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		CenSolicitudmutualidad record = (CenSolicitudmutualidad) parameter.get("record");
		CenSolicitudmutualidadExample example = (CenSolicitudmutualidadExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("CEN_SOLICITUDMUTUALIDAD");
		if (record.getIdsolicitud() != null) {
			sql.SET("IDSOLICITUD = #{record.idsolicitud,jdbcType=DECIMAL}");
		}
		if (record.getIdsolicitudincorporacion() != null) {
			sql.SET("IDSOLICITUDINCORPORACION = #{record.idsolicitudincorporacion,jdbcType=DECIMAL}");
		}
		if (record.getFechasolicitud() != null) {
			sql.SET("FECHASOLICITUD = #{record.fechasolicitud,jdbcType=TIMESTAMP}");
		}
		if (record.getIdtratamiento() != null) {
			sql.SET("IDTRATAMIENTO = #{record.idtratamiento,jdbcType=DECIMAL}");
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
		if (record.getNumeroidentificador() != null) {
			sql.SET("NUMEROIDENTIFICADOR = #{record.numeroidentificador,jdbcType=VARCHAR}");
		}
		if (record.getDomicilio() != null) {
			sql.SET("DOMICILIO = #{record.domicilio,jdbcType=VARCHAR}");
		}
		if (record.getCodigopostal() != null) {
			sql.SET("CODIGOPOSTAL = #{record.codigopostal,jdbcType=VARCHAR}");
		}
		if (record.getTelefono1() != null) {
			sql.SET("TELEFONO1 = #{record.telefono1,jdbcType=VARCHAR}");
		}
		if (record.getCorreoelectronico() != null) {
			sql.SET("CORREOELECTRONICO = #{record.correoelectronico,jdbcType=VARCHAR}");
		}
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdestado() != null) {
			sql.SET("IDESTADO = #{record.idestado,jdbcType=DECIMAL}");
		}
		if (record.getIdtiposolicitud() != null) {
			sql.SET("IDTIPOSOLICITUD = #{record.idtiposolicitud,jdbcType=CHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getFechanacimiento() != null) {
			sql.SET("FECHANACIMIENTO = #{record.fechanacimiento,jdbcType=TIMESTAMP}");
		}
		if (record.getIdtipoidentificacion() != null) {
			sql.SET("IDTIPOIDENTIFICACION = #{record.idtipoidentificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdprovincia() != null) {
			sql.SET("IDPROVINCIA = #{record.idprovincia,jdbcType=VARCHAR}");
		}
		if (record.getIdpoblacion() != null) {
			sql.SET("IDPOBLACION = #{record.idpoblacion,jdbcType=VARCHAR}");
		}
		if (record.getFechaestado() != null) {
			sql.SET("FECHAESTADO = #{record.fechaestado,jdbcType=TIMESTAMP}");
		}
		if (record.getTelefono2() != null) {
			sql.SET("TELEFONO2 = #{record.telefono2,jdbcType=VARCHAR}");
		}
		if (record.getMovil() != null) {
			sql.SET("MOVIL = #{record.movil,jdbcType=VARCHAR}");
		}
		if (record.getFax1() != null) {
			sql.SET("FAX1 = #{record.fax1,jdbcType=VARCHAR}");
		}
		if (record.getFax2() != null) {
			sql.SET("FAX2 = #{record.fax2,jdbcType=VARCHAR}");
		}
		if (record.getIdestadocivil() != null) {
			sql.SET("IDESTADOCIVIL = #{record.idestadocivil,jdbcType=DECIMAL}");
		}
		if (record.getIdpais() != null) {
			sql.SET("IDPAIS = #{record.idpais,jdbcType=VARCHAR}");
		}
		if (record.getNaturalde() != null) {
			sql.SET("NATURALDE = #{record.naturalde,jdbcType=VARCHAR}");
		}
		if (record.getIdsexo() != null) {
			sql.SET("IDSEXO = #{record.idsexo,jdbcType=VARCHAR}");
		}
		if (record.getPoblacionextranjera() != null) {
			sql.SET("POBLACIONEXTRANJERA = #{record.poblacionextranjera,jdbcType=VARCHAR}");
		}
		if (record.getTitular() != null) {
			sql.SET("TITULAR = #{record.titular,jdbcType=VARCHAR}");
		}
		if (record.getCodigosucursal() != null) {
			sql.SET("CODIGOSUCURSAL = #{record.codigosucursal,jdbcType=VARCHAR}");
		}
		if (record.getCboCodigo() != null) {
			sql.SET("CBO_CODIGO = #{record.cboCodigo,jdbcType=VARCHAR}");
		}
		if (record.getDigitocontrol() != null) {
			sql.SET("DIGITOCONTROL = #{record.digitocontrol,jdbcType=VARCHAR}");
		}
		if (record.getNumerocuenta() != null) {
			sql.SET("NUMEROCUENTA = #{record.numerocuenta,jdbcType=VARCHAR}");
		}
		if (record.getIban() != null) {
			sql.SET("IBAN = #{record.iban,jdbcType=VARCHAR}");
		}
		if (record.getIdperiodicidadpago() != null) {
			sql.SET("IDPERIODICIDADPAGO = #{record.idperiodicidadpago,jdbcType=VARCHAR}");
		}
		if (record.getIdcobertura() != null) {
			sql.SET("IDCOBERTURA = #{record.idcobertura,jdbcType=VARCHAR}");
		}
		if (record.getIdbeneficiario() != null) {
			sql.SET("IDBENEFICIARIO = #{record.idbeneficiario,jdbcType=VARCHAR}");
		}
		if (record.getOtrosbeneficiarios() != null) {
			sql.SET("OTROSBENEFICIARIOS = #{record.otrosbeneficiarios,jdbcType=VARCHAR}");
		}
		if (record.getIdasistenciasanitaria() != null) {
			sql.SET("IDASISTENCIASANITARIA = #{record.idasistenciasanitaria,jdbcType=VARCHAR}");
		}
		if (record.getFechanacimientoconyuge() != null) {
			sql.SET("FECHANACIMIENTOCONYUGE = #{record.fechanacimientoconyuge,jdbcType=TIMESTAMP}");
		}
		if (record.getNumerohijos() != null) {
			sql.SET("NUMEROHIJOS = #{record.numerohijos,jdbcType=DECIMAL}");
		}
		if (record.getEdadhijo1() != null) {
			sql.SET("EDADHIJO1 = #{record.edadhijo1,jdbcType=DECIMAL}");
		}
		if (record.getEdadhijo2() != null) {
			sql.SET("EDADHIJO2 = #{record.edadhijo2,jdbcType=DECIMAL}");
		}
		if (record.getEdadhijo3() != null) {
			sql.SET("EDADHIJO3 = #{record.edadhijo3,jdbcType=DECIMAL}");
		}
		if (record.getEdadhijo4() != null) {
			sql.SET("EDADHIJO4 = #{record.edadhijo4,jdbcType=DECIMAL}");
		}
		if (record.getEdadhijo5() != null) {
			sql.SET("EDADHIJO5 = #{record.edadhijo5,jdbcType=DECIMAL}");
		}
		if (record.getEdadhijo6() != null) {
			sql.SET("EDADHIJO6 = #{record.edadhijo6,jdbcType=DECIMAL}");
		}
		if (record.getEdadhijo7() != null) {
			sql.SET("EDADHIJO7 = #{record.edadhijo7,jdbcType=DECIMAL}");
		}
		if (record.getEdadhijo8() != null) {
			sql.SET("EDADHIJO8 = #{record.edadhijo8,jdbcType=DECIMAL}");
		}
		if (record.getEdadhijo9() != null) {
			sql.SET("EDADHIJO9 = #{record.edadhijo9,jdbcType=DECIMAL}");
		}
		if (record.getEdadhijo10() != null) {
			sql.SET("EDADHIJO10 = #{record.edadhijo10,jdbcType=DECIMAL}");
		}
		if (record.getPeriodicidadpago() != null) {
			sql.SET("PERIODICIDADPAGO = #{record.periodicidadpago,jdbcType=VARCHAR}");
		}
		if (record.getCobertura() != null) {
			sql.SET("COBERTURA = #{record.cobertura,jdbcType=VARCHAR}");
		}
		if (record.getBeneficiario() != null) {
			sql.SET("BENEFICIARIO = #{record.beneficiario,jdbcType=VARCHAR}");
		}
		if (record.getAsistenciasanitaria() != null) {
			sql.SET("ASISTENCIASANITARIA = #{record.asistenciasanitaria,jdbcType=VARCHAR}");
		}
		if (record.getSwift() != null) {
			sql.SET("SWIFT = #{record.swift,jdbcType=VARCHAR}");
		}
		if (record.getCuotacobertura() != null) {
			sql.SET("CUOTACOBERTURA = #{record.cuotacobertura,jdbcType=VARCHAR}");
		}
		if (record.getCapitalcobertura() != null) {
			sql.SET("CAPITALCOBERTURA = #{record.capitalcobertura,jdbcType=VARCHAR}");
		}
		if (record.getIdsolicitudaceptada() != null) {
			sql.SET("IDSOLICITUDACEPTADA = #{record.idsolicitudaceptada,jdbcType=DECIMAL}");
		}
		if (record.getProvincia() != null) {
			sql.SET("PROVINCIA = #{record.provincia,jdbcType=VARCHAR}");
		}
		if (record.getPoblacion() != null) {
			sql.SET("POBLACION = #{record.poblacion,jdbcType=VARCHAR}");
		}
		if (record.getPais() != null) {
			sql.SET("PAIS = #{record.pais,jdbcType=VARCHAR}");
		}
		if (record.getEstado() != null) {
			sql.SET("ESTADO = #{record.estado,jdbcType=VARCHAR}");
		}
		if (record.getEstadomutualista() != null) {
			sql.SET("ESTADOMUTUALISTA = #{record.estadomutualista,jdbcType=VARCHAR}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_SOLICITUDMUTUALIDAD
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_SOLICITUDMUTUALIDAD");
		sql.SET("IDSOLICITUD = #{record.idsolicitud,jdbcType=DECIMAL}");
		sql.SET("IDSOLICITUDINCORPORACION = #{record.idsolicitudincorporacion,jdbcType=DECIMAL}");
		sql.SET("FECHASOLICITUD = #{record.fechasolicitud,jdbcType=TIMESTAMP}");
		sql.SET("IDTRATAMIENTO = #{record.idtratamiento,jdbcType=DECIMAL}");
		sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
		sql.SET("APELLIDO1 = #{record.apellido1,jdbcType=VARCHAR}");
		sql.SET("APELLIDO2 = #{record.apellido2,jdbcType=VARCHAR}");
		sql.SET("NUMEROIDENTIFICADOR = #{record.numeroidentificador,jdbcType=VARCHAR}");
		sql.SET("DOMICILIO = #{record.domicilio,jdbcType=VARCHAR}");
		sql.SET("CODIGOPOSTAL = #{record.codigopostal,jdbcType=VARCHAR}");
		sql.SET("TELEFONO1 = #{record.telefono1,jdbcType=VARCHAR}");
		sql.SET("CORREOELECTRONICO = #{record.correoelectronico,jdbcType=VARCHAR}");
		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		sql.SET("IDESTADO = #{record.idestado,jdbcType=DECIMAL}");
		sql.SET("IDTIPOSOLICITUD = #{record.idtiposolicitud,jdbcType=CHAR}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("FECHANACIMIENTO = #{record.fechanacimiento,jdbcType=TIMESTAMP}");
		sql.SET("IDTIPOIDENTIFICACION = #{record.idtipoidentificacion,jdbcType=DECIMAL}");
		sql.SET("IDPROVINCIA = #{record.idprovincia,jdbcType=VARCHAR}");
		sql.SET("IDPOBLACION = #{record.idpoblacion,jdbcType=VARCHAR}");
		sql.SET("FECHAESTADO = #{record.fechaestado,jdbcType=TIMESTAMP}");
		sql.SET("TELEFONO2 = #{record.telefono2,jdbcType=VARCHAR}");
		sql.SET("MOVIL = #{record.movil,jdbcType=VARCHAR}");
		sql.SET("FAX1 = #{record.fax1,jdbcType=VARCHAR}");
		sql.SET("FAX2 = #{record.fax2,jdbcType=VARCHAR}");
		sql.SET("IDESTADOCIVIL = #{record.idestadocivil,jdbcType=DECIMAL}");
		sql.SET("IDPAIS = #{record.idpais,jdbcType=VARCHAR}");
		sql.SET("NATURALDE = #{record.naturalde,jdbcType=VARCHAR}");
		sql.SET("IDSEXO = #{record.idsexo,jdbcType=VARCHAR}");
		sql.SET("POBLACIONEXTRANJERA = #{record.poblacionextranjera,jdbcType=VARCHAR}");
		sql.SET("TITULAR = #{record.titular,jdbcType=VARCHAR}");
		sql.SET("CODIGOSUCURSAL = #{record.codigosucursal,jdbcType=VARCHAR}");
		sql.SET("CBO_CODIGO = #{record.cboCodigo,jdbcType=VARCHAR}");
		sql.SET("DIGITOCONTROL = #{record.digitocontrol,jdbcType=VARCHAR}");
		sql.SET("NUMEROCUENTA = #{record.numerocuenta,jdbcType=VARCHAR}");
		sql.SET("IBAN = #{record.iban,jdbcType=VARCHAR}");
		sql.SET("IDPERIODICIDADPAGO = #{record.idperiodicidadpago,jdbcType=VARCHAR}");
		sql.SET("IDCOBERTURA = #{record.idcobertura,jdbcType=VARCHAR}");
		sql.SET("IDBENEFICIARIO = #{record.idbeneficiario,jdbcType=VARCHAR}");
		sql.SET("OTROSBENEFICIARIOS = #{record.otrosbeneficiarios,jdbcType=VARCHAR}");
		sql.SET("IDASISTENCIASANITARIA = #{record.idasistenciasanitaria,jdbcType=VARCHAR}");
		sql.SET("FECHANACIMIENTOCONYUGE = #{record.fechanacimientoconyuge,jdbcType=TIMESTAMP}");
		sql.SET("NUMEROHIJOS = #{record.numerohijos,jdbcType=DECIMAL}");
		sql.SET("EDADHIJO1 = #{record.edadhijo1,jdbcType=DECIMAL}");
		sql.SET("EDADHIJO2 = #{record.edadhijo2,jdbcType=DECIMAL}");
		sql.SET("EDADHIJO3 = #{record.edadhijo3,jdbcType=DECIMAL}");
		sql.SET("EDADHIJO4 = #{record.edadhijo4,jdbcType=DECIMAL}");
		sql.SET("EDADHIJO5 = #{record.edadhijo5,jdbcType=DECIMAL}");
		sql.SET("EDADHIJO6 = #{record.edadhijo6,jdbcType=DECIMAL}");
		sql.SET("EDADHIJO7 = #{record.edadhijo7,jdbcType=DECIMAL}");
		sql.SET("EDADHIJO8 = #{record.edadhijo8,jdbcType=DECIMAL}");
		sql.SET("EDADHIJO9 = #{record.edadhijo9,jdbcType=DECIMAL}");
		sql.SET("EDADHIJO10 = #{record.edadhijo10,jdbcType=DECIMAL}");
		sql.SET("PERIODICIDADPAGO = #{record.periodicidadpago,jdbcType=VARCHAR}");
		sql.SET("COBERTURA = #{record.cobertura,jdbcType=VARCHAR}");
		sql.SET("BENEFICIARIO = #{record.beneficiario,jdbcType=VARCHAR}");
		sql.SET("ASISTENCIASANITARIA = #{record.asistenciasanitaria,jdbcType=VARCHAR}");
		sql.SET("SWIFT = #{record.swift,jdbcType=VARCHAR}");
		sql.SET("CUOTACOBERTURA = #{record.cuotacobertura,jdbcType=VARCHAR}");
		sql.SET("CAPITALCOBERTURA = #{record.capitalcobertura,jdbcType=VARCHAR}");
		sql.SET("IDSOLICITUDACEPTADA = #{record.idsolicitudaceptada,jdbcType=DECIMAL}");
		sql.SET("PROVINCIA = #{record.provincia,jdbcType=VARCHAR}");
		sql.SET("POBLACION = #{record.poblacion,jdbcType=VARCHAR}");
		sql.SET("PAIS = #{record.pais,jdbcType=VARCHAR}");
		sql.SET("ESTADO = #{record.estado,jdbcType=VARCHAR}");
		sql.SET("ESTADOMUTUALISTA = #{record.estadomutualista,jdbcType=VARCHAR}");
		CenSolicitudmutualidadExample example = (CenSolicitudmutualidadExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_SOLICITUDMUTUALIDAD
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String updateByPrimaryKeySelective(CenSolicitudmutualidad record) {
		SQL sql = new SQL();
		sql.UPDATE("CEN_SOLICITUDMUTUALIDAD");
		if (record.getIdsolicitudincorporacion() != null) {
			sql.SET("IDSOLICITUDINCORPORACION = #{idsolicitudincorporacion,jdbcType=DECIMAL}");
		}
		if (record.getFechasolicitud() != null) {
			sql.SET("FECHASOLICITUD = #{fechasolicitud,jdbcType=TIMESTAMP}");
		}
		if (record.getIdtratamiento() != null) {
			sql.SET("IDTRATAMIENTO = #{idtratamiento,jdbcType=DECIMAL}");
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
		if (record.getNumeroidentificador() != null) {
			sql.SET("NUMEROIDENTIFICADOR = #{numeroidentificador,jdbcType=VARCHAR}");
		}
		if (record.getDomicilio() != null) {
			sql.SET("DOMICILIO = #{domicilio,jdbcType=VARCHAR}");
		}
		if (record.getCodigopostal() != null) {
			sql.SET("CODIGOPOSTAL = #{codigopostal,jdbcType=VARCHAR}");
		}
		if (record.getTelefono1() != null) {
			sql.SET("TELEFONO1 = #{telefono1,jdbcType=VARCHAR}");
		}
		if (record.getCorreoelectronico() != null) {
			sql.SET("CORREOELECTRONICO = #{correoelectronico,jdbcType=VARCHAR}");
		}
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdestado() != null) {
			sql.SET("IDESTADO = #{idestado,jdbcType=DECIMAL}");
		}
		if (record.getIdtiposolicitud() != null) {
			sql.SET("IDTIPOSOLICITUD = #{idtiposolicitud,jdbcType=CHAR}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getFechanacimiento() != null) {
			sql.SET("FECHANACIMIENTO = #{fechanacimiento,jdbcType=TIMESTAMP}");
		}
		if (record.getIdtipoidentificacion() != null) {
			sql.SET("IDTIPOIDENTIFICACION = #{idtipoidentificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdprovincia() != null) {
			sql.SET("IDPROVINCIA = #{idprovincia,jdbcType=VARCHAR}");
		}
		if (record.getIdpoblacion() != null) {
			sql.SET("IDPOBLACION = #{idpoblacion,jdbcType=VARCHAR}");
		}
		if (record.getFechaestado() != null) {
			sql.SET("FECHAESTADO = #{fechaestado,jdbcType=TIMESTAMP}");
		}
		if (record.getTelefono2() != null) {
			sql.SET("TELEFONO2 = #{telefono2,jdbcType=VARCHAR}");
		}
		if (record.getMovil() != null) {
			sql.SET("MOVIL = #{movil,jdbcType=VARCHAR}");
		}
		if (record.getFax1() != null) {
			sql.SET("FAX1 = #{fax1,jdbcType=VARCHAR}");
		}
		if (record.getFax2() != null) {
			sql.SET("FAX2 = #{fax2,jdbcType=VARCHAR}");
		}
		if (record.getIdestadocivil() != null) {
			sql.SET("IDESTADOCIVIL = #{idestadocivil,jdbcType=DECIMAL}");
		}
		if (record.getIdpais() != null) {
			sql.SET("IDPAIS = #{idpais,jdbcType=VARCHAR}");
		}
		if (record.getNaturalde() != null) {
			sql.SET("NATURALDE = #{naturalde,jdbcType=VARCHAR}");
		}
		if (record.getIdsexo() != null) {
			sql.SET("IDSEXO = #{idsexo,jdbcType=VARCHAR}");
		}
		if (record.getPoblacionextranjera() != null) {
			sql.SET("POBLACIONEXTRANJERA = #{poblacionextranjera,jdbcType=VARCHAR}");
		}
		if (record.getTitular() != null) {
			sql.SET("TITULAR = #{titular,jdbcType=VARCHAR}");
		}
		if (record.getCodigosucursal() != null) {
			sql.SET("CODIGOSUCURSAL = #{codigosucursal,jdbcType=VARCHAR}");
		}
		if (record.getCboCodigo() != null) {
			sql.SET("CBO_CODIGO = #{cboCodigo,jdbcType=VARCHAR}");
		}
		if (record.getDigitocontrol() != null) {
			sql.SET("DIGITOCONTROL = #{digitocontrol,jdbcType=VARCHAR}");
		}
		if (record.getNumerocuenta() != null) {
			sql.SET("NUMEROCUENTA = #{numerocuenta,jdbcType=VARCHAR}");
		}
		if (record.getIban() != null) {
			sql.SET("IBAN = #{iban,jdbcType=VARCHAR}");
		}
		if (record.getIdperiodicidadpago() != null) {
			sql.SET("IDPERIODICIDADPAGO = #{idperiodicidadpago,jdbcType=VARCHAR}");
		}
		if (record.getIdcobertura() != null) {
			sql.SET("IDCOBERTURA = #{idcobertura,jdbcType=VARCHAR}");
		}
		if (record.getIdbeneficiario() != null) {
			sql.SET("IDBENEFICIARIO = #{idbeneficiario,jdbcType=VARCHAR}");
		}
		if (record.getOtrosbeneficiarios() != null) {
			sql.SET("OTROSBENEFICIARIOS = #{otrosbeneficiarios,jdbcType=VARCHAR}");
		}
		if (record.getIdasistenciasanitaria() != null) {
			sql.SET("IDASISTENCIASANITARIA = #{idasistenciasanitaria,jdbcType=VARCHAR}");
		}
		if (record.getFechanacimientoconyuge() != null) {
			sql.SET("FECHANACIMIENTOCONYUGE = #{fechanacimientoconyuge,jdbcType=TIMESTAMP}");
		}
		if (record.getNumerohijos() != null) {
			sql.SET("NUMEROHIJOS = #{numerohijos,jdbcType=DECIMAL}");
		}
		if (record.getEdadhijo1() != null) {
			sql.SET("EDADHIJO1 = #{edadhijo1,jdbcType=DECIMAL}");
		}
		if (record.getEdadhijo2() != null) {
			sql.SET("EDADHIJO2 = #{edadhijo2,jdbcType=DECIMAL}");
		}
		if (record.getEdadhijo3() != null) {
			sql.SET("EDADHIJO3 = #{edadhijo3,jdbcType=DECIMAL}");
		}
		if (record.getEdadhijo4() != null) {
			sql.SET("EDADHIJO4 = #{edadhijo4,jdbcType=DECIMAL}");
		}
		if (record.getEdadhijo5() != null) {
			sql.SET("EDADHIJO5 = #{edadhijo5,jdbcType=DECIMAL}");
		}
		if (record.getEdadhijo6() != null) {
			sql.SET("EDADHIJO6 = #{edadhijo6,jdbcType=DECIMAL}");
		}
		if (record.getEdadhijo7() != null) {
			sql.SET("EDADHIJO7 = #{edadhijo7,jdbcType=DECIMAL}");
		}
		if (record.getEdadhijo8() != null) {
			sql.SET("EDADHIJO8 = #{edadhijo8,jdbcType=DECIMAL}");
		}
		if (record.getEdadhijo9() != null) {
			sql.SET("EDADHIJO9 = #{edadhijo9,jdbcType=DECIMAL}");
		}
		if (record.getEdadhijo10() != null) {
			sql.SET("EDADHIJO10 = #{edadhijo10,jdbcType=DECIMAL}");
		}
		if (record.getPeriodicidadpago() != null) {
			sql.SET("PERIODICIDADPAGO = #{periodicidadpago,jdbcType=VARCHAR}");
		}
		if (record.getCobertura() != null) {
			sql.SET("COBERTURA = #{cobertura,jdbcType=VARCHAR}");
		}
		if (record.getBeneficiario() != null) {
			sql.SET("BENEFICIARIO = #{beneficiario,jdbcType=VARCHAR}");
		}
		if (record.getAsistenciasanitaria() != null) {
			sql.SET("ASISTENCIASANITARIA = #{asistenciasanitaria,jdbcType=VARCHAR}");
		}
		if (record.getSwift() != null) {
			sql.SET("SWIFT = #{swift,jdbcType=VARCHAR}");
		}
		if (record.getCuotacobertura() != null) {
			sql.SET("CUOTACOBERTURA = #{cuotacobertura,jdbcType=VARCHAR}");
		}
		if (record.getCapitalcobertura() != null) {
			sql.SET("CAPITALCOBERTURA = #{capitalcobertura,jdbcType=VARCHAR}");
		}
		if (record.getIdsolicitudaceptada() != null) {
			sql.SET("IDSOLICITUDACEPTADA = #{idsolicitudaceptada,jdbcType=DECIMAL}");
		}
		if (record.getProvincia() != null) {
			sql.SET("PROVINCIA = #{provincia,jdbcType=VARCHAR}");
		}
		if (record.getPoblacion() != null) {
			sql.SET("POBLACION = #{poblacion,jdbcType=VARCHAR}");
		}
		if (record.getPais() != null) {
			sql.SET("PAIS = #{pais,jdbcType=VARCHAR}");
		}
		if (record.getEstado() != null) {
			sql.SET("ESTADO = #{estado,jdbcType=VARCHAR}");
		}
		if (record.getEstadomutualista() != null) {
			sql.SET("ESTADOMUTUALISTA = #{estadomutualista,jdbcType=VARCHAR}");
		}
		sql.WHERE("IDSOLICITUD = #{idsolicitud,jdbcType=DECIMAL}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE_DESA.CEN_SOLICITUDMUTUALIDAD
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	protected void applyWhere(SQL sql, CenSolicitudmutualidadExample example, boolean includeExamplePhrase) {
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