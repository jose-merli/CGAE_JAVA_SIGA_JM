package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.ScsAsistencia;
import org.itcgae.siga.db.entities.ScsAsistenciaExample.Criteria;
import org.itcgae.siga.db.entities.ScsAsistenciaExample.Criterion;
import org.itcgae.siga.db.entities.ScsAsistenciaExample;

public class ScsAsistenciaSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_ASISTENCIA
	 * @mbg.generated  Fri Nov 08 08:36:13 CET 2019
	 */
	public String countByExample(ScsAsistenciaExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("SCS_ASISTENCIA");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_ASISTENCIA
	 * @mbg.generated  Fri Nov 08 08:36:13 CET 2019
	 */
	public String deleteByExample(ScsAsistenciaExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("SCS_ASISTENCIA");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_ASISTENCIA
	 * @mbg.generated  Fri Nov 08 08:36:13 CET 2019
	 */
	public String insertSelective(ScsAsistencia record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("SCS_ASISTENCIA");
		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getAnio() != null) {
			sql.VALUES("ANIO", "#{anio,jdbcType=DECIMAL}");
		}
		if (record.getNumero() != null) {
			sql.VALUES("NUMERO", "#{numero,jdbcType=DECIMAL}");
		}
		if (record.getFechahora() != null) {
			sql.VALUES("FECHAHORA", "#{fechahora,jdbcType=TIMESTAMP}");
		}
		if (record.getIdtipoasistencia() != null) {
			sql.VALUES("IDTIPOASISTENCIA", "#{idtipoasistencia,jdbcType=DECIMAL}");
		}
		if (record.getIdturno() != null) {
			sql.VALUES("IDTURNO", "#{idturno,jdbcType=DECIMAL}");
		}
		if (record.getIdguardia() != null) {
			sql.VALUES("IDGUARDIA", "#{idguardia,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdpersonacolegiado() != null) {
			sql.VALUES("IDPERSONACOLEGIADO", "#{idpersonacolegiado,jdbcType=DECIMAL}");
		}
		if (record.getObservaciones() != null) {
			sql.VALUES("OBSERVACIONES", "#{observaciones,jdbcType=VARCHAR}");
		}
		if (record.getIncidencias() != null) {
			sql.VALUES("INCIDENCIAS", "#{incidencias,jdbcType=VARCHAR}");
		}
		if (record.getFechaanulacion() != null) {
			sql.VALUES("FECHAANULACION", "#{fechaanulacion,jdbcType=TIMESTAMP}");
		}
		if (record.getMotivosanulacion() != null) {
			sql.VALUES("MOTIVOSANULACION", "#{motivosanulacion,jdbcType=VARCHAR}");
		}
		if (record.getDelitosimputados() != null) {
			sql.VALUES("DELITOSIMPUTADOS", "#{delitosimputados,jdbcType=VARCHAR}");
		}
		if (record.getContrarios() != null) {
			sql.VALUES("CONTRARIOS", "#{contrarios,jdbcType=VARCHAR}");
		}
		if (record.getDatosdefensajuridica() != null) {
			sql.VALUES("DATOSDEFENSAJURIDICA", "#{datosdefensajuridica,jdbcType=VARCHAR}");
		}
		if (record.getFechacierre() != null) {
			sql.VALUES("FECHACIERRE", "#{fechacierre,jdbcType=TIMESTAMP}");
		}
		if (record.getIdtipoasistenciacolegio() != null) {
			sql.VALUES("IDTIPOASISTENCIACOLEGIO", "#{idtipoasistenciacolegio,jdbcType=DECIMAL}");
		}
		if (record.getDesignaAnio() != null) {
			sql.VALUES("DESIGNA_ANIO", "#{designaAnio,jdbcType=DECIMAL}");
		}
		if (record.getDesignaNumero() != null) {
			sql.VALUES("DESIGNA_NUMERO", "#{designaNumero,jdbcType=DECIMAL}");
		}
		if (record.getIdpersonajg() != null) {
			sql.VALUES("IDPERSONAJG", "#{idpersonajg,jdbcType=DECIMAL}");
		}
		if (record.getFacturado() != null) {
			sql.VALUES("FACTURADO", "#{facturado,jdbcType=VARCHAR}");
		}
		if (record.getPagado() != null) {
			sql.VALUES("PAGADO", "#{pagado,jdbcType=VARCHAR}");
		}
		if (record.getIdfacturacion() != null) {
			sql.VALUES("IDFACTURACION", "#{idfacturacion,jdbcType=DECIMAL}");
		}
		if (record.getDesignaTurno() != null) {
			sql.VALUES("DESIGNA_TURNO", "#{designaTurno,jdbcType=DECIMAL}");
		}
		if (record.getNumerodiligencia() != null) {
			sql.VALUES("NUMERODILIGENCIA", "#{numerodiligencia,jdbcType=VARCHAR}");
		}
		if (record.getNumeroprocedimiento() != null) {
			sql.VALUES("NUMEROPROCEDIMIENTO", "#{numeroprocedimiento,jdbcType=VARCHAR}");
		}
		if (record.getComisaria() != null) {
			sql.VALUES("COMISARIA", "#{comisaria,jdbcType=DECIMAL}");
		}
		if (record.getComisariaidinstitucion() != null) {
			sql.VALUES("COMISARIAIDINSTITUCION", "#{comisariaidinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getJuzgado() != null) {
			sql.VALUES("JUZGADO", "#{juzgado,jdbcType=DECIMAL}");
		}
		if (record.getJuzgadoidinstitucion() != null) {
			sql.VALUES("JUZGADOIDINSTITUCION", "#{juzgadoidinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdestadoasistencia() != null) {
			sql.VALUES("IDESTADOASISTENCIA", "#{idestadoasistencia,jdbcType=DECIMAL}");
		}
		if (record.getFechaestadoasistencia() != null) {
			sql.VALUES("FECHAESTADOASISTENCIA", "#{fechaestadoasistencia,jdbcType=TIMESTAMP}");
		}
		if (record.getEjgidtipoejg() != null) {
			sql.VALUES("EJGIDTIPOEJG", "#{ejgidtipoejg,jdbcType=DECIMAL}");
		}
		if (record.getEjganio() != null) {
			sql.VALUES("EJGANIO", "#{ejganio,jdbcType=DECIMAL}");
		}
		if (record.getEjgnumero() != null) {
			sql.VALUES("EJGNUMERO", "#{ejgnumero,jdbcType=DECIMAL}");
		}
		if (record.getCodigo() != null) {
			sql.VALUES("CODIGO", "#{codigo,jdbcType=VARCHAR}");
		}
		if (record.getNig() != null) {
			sql.VALUES("NIG", "#{nig,jdbcType=VARCHAR}");
		}
		if (record.getIdpretension() != null) {
			sql.VALUES("IDPRETENSION", "#{idpretension,jdbcType=DECIMAL}");
		}
		if (record.getAsistidoautorizaeejg() != null) {
			sql.VALUES("ASISTIDOAUTORIZAEEJG", "#{asistidoautorizaeejg,jdbcType=VARCHAR}");
		}
		if (record.getIdtipoasistido() != null) {
			sql.VALUES("IDTIPOASISTIDO", "#{idtipoasistido,jdbcType=DECIMAL}");
		}
		if (record.getIdorigenasistencia() != null) {
			sql.VALUES("IDORIGENASISTENCIA", "#{idorigenasistencia,jdbcType=DECIMAL}");
		}
		if (record.getAsistidosolicitajg() != null) {
			sql.VALUES("ASISTIDOSOLICITAJG", "#{asistidosolicitajg,jdbcType=VARCHAR}");
		}
		if (record.getFechasolicitud() != null) {
			sql.VALUES("FECHASOLICITUD", "#{fechasolicitud,jdbcType=TIMESTAMP}");
		}
		if (record.getIdsolicitudcentralita() != null) {
			sql.VALUES("IDSOLICITUDCENTRALITA", "#{idsolicitudcentralita,jdbcType=DECIMAL}");
		}
		if (record.getIdmovimiento() != null) {
			sql.VALUES("IDMOVIMIENTO", "#{idmovimiento,jdbcType=DECIMAL}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_ASISTENCIA
	 * @mbg.generated  Fri Nov 08 08:36:13 CET 2019
	 */
	public String selectByExample(ScsAsistenciaExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDINSTITUCION");
		} else {
			sql.SELECT("IDINSTITUCION");
		}
		sql.SELECT("ANIO");
		sql.SELECT("NUMERO");
		sql.SELECT("FECHAHORA");
		sql.SELECT("IDTIPOASISTENCIA");
		sql.SELECT("IDTURNO");
		sql.SELECT("IDGUARDIA");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("IDPERSONACOLEGIADO");
		sql.SELECT("OBSERVACIONES");
		sql.SELECT("INCIDENCIAS");
		sql.SELECT("FECHAANULACION");
		sql.SELECT("MOTIVOSANULACION");
		sql.SELECT("DELITOSIMPUTADOS");
		sql.SELECT("CONTRARIOS");
		sql.SELECT("DATOSDEFENSAJURIDICA");
		sql.SELECT("FECHACIERRE");
		sql.SELECT("IDTIPOASISTENCIACOLEGIO");
		sql.SELECT("DESIGNA_ANIO");
		sql.SELECT("DESIGNA_NUMERO");
		sql.SELECT("IDPERSONAJG");
		sql.SELECT("FACTURADO");
		sql.SELECT("PAGADO");
		sql.SELECT("IDFACTURACION");
		sql.SELECT("DESIGNA_TURNO");
		sql.SELECT("NUMERODILIGENCIA");
		sql.SELECT("NUMEROPROCEDIMIENTO");
		sql.SELECT("COMISARIA");
		sql.SELECT("COMISARIAIDINSTITUCION");
		sql.SELECT("JUZGADO");
		sql.SELECT("JUZGADOIDINSTITUCION");
		sql.SELECT("IDESTADOASISTENCIA");
		sql.SELECT("FECHAESTADOASISTENCIA");
		sql.SELECT("EJGIDTIPOEJG");
		sql.SELECT("EJGANIO");
		sql.SELECT("EJGNUMERO");
		sql.SELECT("CODIGO");
		sql.SELECT("NIG");
		sql.SELECT("IDPRETENSION");
		sql.SELECT("ASISTIDOAUTORIZAEEJG");
		sql.SELECT("IDTIPOASISTIDO");
		sql.SELECT("IDORIGENASISTENCIA");
		sql.SELECT("ASISTIDOSOLICITAJG");
		sql.SELECT("FECHASOLICITUD");
		sql.SELECT("IDSOLICITUDCENTRALITA");
		sql.SELECT("IDMOVIMIENTO");
		sql.FROM("SCS_ASISTENCIA");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_ASISTENCIA
	 * @mbg.generated  Fri Nov 08 08:36:13 CET 2019
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		ScsAsistencia record = (ScsAsistencia) parameter.get("record");
		ScsAsistenciaExample example = (ScsAsistenciaExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("SCS_ASISTENCIA");
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getAnio() != null) {
			sql.SET("ANIO = #{record.anio,jdbcType=DECIMAL}");
		}
		if (record.getNumero() != null) {
			sql.SET("NUMERO = #{record.numero,jdbcType=DECIMAL}");
		}
		if (record.getFechahora() != null) {
			sql.SET("FECHAHORA = #{record.fechahora,jdbcType=TIMESTAMP}");
		}
		if (record.getIdtipoasistencia() != null) {
			sql.SET("IDTIPOASISTENCIA = #{record.idtipoasistencia,jdbcType=DECIMAL}");
		}
		if (record.getIdturno() != null) {
			sql.SET("IDTURNO = #{record.idturno,jdbcType=DECIMAL}");
		}
		if (record.getIdguardia() != null) {
			sql.SET("IDGUARDIA = #{record.idguardia,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdpersonacolegiado() != null) {
			sql.SET("IDPERSONACOLEGIADO = #{record.idpersonacolegiado,jdbcType=DECIMAL}");
		}
		if (record.getObservaciones() != null) {
			sql.SET("OBSERVACIONES = #{record.observaciones,jdbcType=VARCHAR}");
		}
		if (record.getIncidencias() != null) {
			sql.SET("INCIDENCIAS = #{record.incidencias,jdbcType=VARCHAR}");
		}
		if (record.getFechaanulacion() != null) {
			sql.SET("FECHAANULACION = #{record.fechaanulacion,jdbcType=TIMESTAMP}");
		}
		if (record.getMotivosanulacion() != null) {
			sql.SET("MOTIVOSANULACION = #{record.motivosanulacion,jdbcType=VARCHAR}");
		}
		if (record.getDelitosimputados() != null) {
			sql.SET("DELITOSIMPUTADOS = #{record.delitosimputados,jdbcType=VARCHAR}");
		}
		if (record.getContrarios() != null) {
			sql.SET("CONTRARIOS = #{record.contrarios,jdbcType=VARCHAR}");
		}
		if (record.getDatosdefensajuridica() != null) {
			sql.SET("DATOSDEFENSAJURIDICA = #{record.datosdefensajuridica,jdbcType=VARCHAR}");
		}
		if (record.getFechacierre() != null) {
			sql.SET("FECHACIERRE = #{record.fechacierre,jdbcType=TIMESTAMP}");
		}
		if (record.getIdtipoasistenciacolegio() != null) {
			sql.SET("IDTIPOASISTENCIACOLEGIO = #{record.idtipoasistenciacolegio,jdbcType=DECIMAL}");
		}
		if (record.getDesignaAnio() != null) {
			sql.SET("DESIGNA_ANIO = #{record.designaAnio,jdbcType=DECIMAL}");
		}
		if (record.getDesignaNumero() != null) {
			sql.SET("DESIGNA_NUMERO = #{record.designaNumero,jdbcType=DECIMAL}");
		}
		if (record.getIdpersonajg() != null) {
			sql.SET("IDPERSONAJG = #{record.idpersonajg,jdbcType=DECIMAL}");
		}
		if (record.getFacturado() != null) {
			sql.SET("FACTURADO = #{record.facturado,jdbcType=VARCHAR}");
		}
		if (record.getPagado() != null) {
			sql.SET("PAGADO = #{record.pagado,jdbcType=VARCHAR}");
		}
		if (record.getIdfacturacion() != null) {
			sql.SET("IDFACTURACION = #{record.idfacturacion,jdbcType=DECIMAL}");
		}
		if (record.getDesignaTurno() != null) {
			sql.SET("DESIGNA_TURNO = #{record.designaTurno,jdbcType=DECIMAL}");
		}
		if (record.getNumerodiligencia() != null) {
			sql.SET("NUMERODILIGENCIA = #{record.numerodiligencia,jdbcType=VARCHAR}");
		}
		if (record.getNumeroprocedimiento() != null) {
			sql.SET("NUMEROPROCEDIMIENTO = #{record.numeroprocedimiento,jdbcType=VARCHAR}");
		}
		if (record.getComisaria() != null) {
			sql.SET("COMISARIA = #{record.comisaria,jdbcType=DECIMAL}");
		}
		if (record.getComisariaidinstitucion() != null) {
			sql.SET("COMISARIAIDINSTITUCION = #{record.comisariaidinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getJuzgado() != null) {
			sql.SET("JUZGADO = #{record.juzgado,jdbcType=DECIMAL}");
		}
		if (record.getJuzgadoidinstitucion() != null) {
			sql.SET("JUZGADOIDINSTITUCION = #{record.juzgadoidinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdestadoasistencia() != null) {
			sql.SET("IDESTADOASISTENCIA = #{record.idestadoasistencia,jdbcType=DECIMAL}");
		}
		if (record.getFechaestadoasistencia() != null) {
			sql.SET("FECHAESTADOASISTENCIA = #{record.fechaestadoasistencia,jdbcType=TIMESTAMP}");
		}
		if (record.getEjgidtipoejg() != null) {
			sql.SET("EJGIDTIPOEJG = #{record.ejgidtipoejg,jdbcType=DECIMAL}");
		}
		if (record.getEjganio() != null) {
			sql.SET("EJGANIO = #{record.ejganio,jdbcType=DECIMAL}");
		}
		if (record.getEjgnumero() != null) {
			sql.SET("EJGNUMERO = #{record.ejgnumero,jdbcType=DECIMAL}");
		}
		if (record.getCodigo() != null) {
			sql.SET("CODIGO = #{record.codigo,jdbcType=VARCHAR}");
		}
		if (record.getNig() != null) {
			sql.SET("NIG = #{record.nig,jdbcType=VARCHAR}");
		}
		if (record.getIdpretension() != null) {
			sql.SET("IDPRETENSION = #{record.idpretension,jdbcType=DECIMAL}");
		}
		if (record.getAsistidoautorizaeejg() != null) {
			sql.SET("ASISTIDOAUTORIZAEEJG = #{record.asistidoautorizaeejg,jdbcType=VARCHAR}");
		}
		if (record.getIdtipoasistido() != null) {
			sql.SET("IDTIPOASISTIDO = #{record.idtipoasistido,jdbcType=DECIMAL}");
		}
		if (record.getIdorigenasistencia() != null) {
			sql.SET("IDORIGENASISTENCIA = #{record.idorigenasistencia,jdbcType=DECIMAL}");
		}
		if (record.getAsistidosolicitajg() != null) {
			sql.SET("ASISTIDOSOLICITAJG = #{record.asistidosolicitajg,jdbcType=VARCHAR}");
		}
		if (record.getFechasolicitud() != null) {
			sql.SET("FECHASOLICITUD = #{record.fechasolicitud,jdbcType=TIMESTAMP}");
		}
		if (record.getIdsolicitudcentralita() != null) {
			sql.SET("IDSOLICITUDCENTRALITA = #{record.idsolicitudcentralita,jdbcType=DECIMAL}");
		}
		if (record.getIdmovimiento() != null) {
			sql.SET("IDMOVIMIENTO = #{record.idmovimiento,jdbcType=DECIMAL}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_ASISTENCIA
	 * @mbg.generated  Fri Nov 08 08:36:13 CET 2019
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("SCS_ASISTENCIA");
		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		sql.SET("ANIO = #{record.anio,jdbcType=DECIMAL}");
		sql.SET("NUMERO = #{record.numero,jdbcType=DECIMAL}");
		sql.SET("FECHAHORA = #{record.fechahora,jdbcType=TIMESTAMP}");
		sql.SET("IDTIPOASISTENCIA = #{record.idtipoasistencia,jdbcType=DECIMAL}");
		sql.SET("IDTURNO = #{record.idturno,jdbcType=DECIMAL}");
		sql.SET("IDGUARDIA = #{record.idguardia,jdbcType=DECIMAL}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("IDPERSONACOLEGIADO = #{record.idpersonacolegiado,jdbcType=DECIMAL}");
		sql.SET("OBSERVACIONES = #{record.observaciones,jdbcType=VARCHAR}");
		sql.SET("INCIDENCIAS = #{record.incidencias,jdbcType=VARCHAR}");
		sql.SET("FECHAANULACION = #{record.fechaanulacion,jdbcType=TIMESTAMP}");
		sql.SET("MOTIVOSANULACION = #{record.motivosanulacion,jdbcType=VARCHAR}");
		sql.SET("DELITOSIMPUTADOS = #{record.delitosimputados,jdbcType=VARCHAR}");
		sql.SET("CONTRARIOS = #{record.contrarios,jdbcType=VARCHAR}");
		sql.SET("DATOSDEFENSAJURIDICA = #{record.datosdefensajuridica,jdbcType=VARCHAR}");
		sql.SET("FECHACIERRE = #{record.fechacierre,jdbcType=TIMESTAMP}");
		sql.SET("IDTIPOASISTENCIACOLEGIO = #{record.idtipoasistenciacolegio,jdbcType=DECIMAL}");
		sql.SET("DESIGNA_ANIO = #{record.designaAnio,jdbcType=DECIMAL}");
		sql.SET("DESIGNA_NUMERO = #{record.designaNumero,jdbcType=DECIMAL}");
		sql.SET("IDPERSONAJG = #{record.idpersonajg,jdbcType=DECIMAL}");
		sql.SET("FACTURADO = #{record.facturado,jdbcType=VARCHAR}");
		sql.SET("PAGADO = #{record.pagado,jdbcType=VARCHAR}");
		sql.SET("IDFACTURACION = #{record.idfacturacion,jdbcType=DECIMAL}");
		sql.SET("DESIGNA_TURNO = #{record.designaTurno,jdbcType=DECIMAL}");
		sql.SET("NUMERODILIGENCIA = #{record.numerodiligencia,jdbcType=VARCHAR}");
		sql.SET("NUMEROPROCEDIMIENTO = #{record.numeroprocedimiento,jdbcType=VARCHAR}");
		sql.SET("COMISARIA = #{record.comisaria,jdbcType=DECIMAL}");
		sql.SET("COMISARIAIDINSTITUCION = #{record.comisariaidinstitucion,jdbcType=DECIMAL}");
		sql.SET("JUZGADO = #{record.juzgado,jdbcType=DECIMAL}");
		sql.SET("JUZGADOIDINSTITUCION = #{record.juzgadoidinstitucion,jdbcType=DECIMAL}");
		sql.SET("IDESTADOASISTENCIA = #{record.idestadoasistencia,jdbcType=DECIMAL}");
		sql.SET("FECHAESTADOASISTENCIA = #{record.fechaestadoasistencia,jdbcType=TIMESTAMP}");
		sql.SET("EJGIDTIPOEJG = #{record.ejgidtipoejg,jdbcType=DECIMAL}");
		sql.SET("EJGANIO = #{record.ejganio,jdbcType=DECIMAL}");
		sql.SET("EJGNUMERO = #{record.ejgnumero,jdbcType=DECIMAL}");
		sql.SET("CODIGO = #{record.codigo,jdbcType=VARCHAR}");
		sql.SET("NIG = #{record.nig,jdbcType=VARCHAR}");
		sql.SET("IDPRETENSION = #{record.idpretension,jdbcType=DECIMAL}");
		sql.SET("ASISTIDOAUTORIZAEEJG = #{record.asistidoautorizaeejg,jdbcType=VARCHAR}");
		sql.SET("IDTIPOASISTIDO = #{record.idtipoasistido,jdbcType=DECIMAL}");
		sql.SET("IDORIGENASISTENCIA = #{record.idorigenasistencia,jdbcType=DECIMAL}");
		sql.SET("ASISTIDOSOLICITAJG = #{record.asistidosolicitajg,jdbcType=VARCHAR}");
		sql.SET("FECHASOLICITUD = #{record.fechasolicitud,jdbcType=TIMESTAMP}");
		sql.SET("IDSOLICITUDCENTRALITA = #{record.idsolicitudcentralita,jdbcType=DECIMAL}");
		sql.SET("IDMOVIMIENTO = #{record.idmovimiento,jdbcType=DECIMAL}");
		ScsAsistenciaExample example = (ScsAsistenciaExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_ASISTENCIA
	 * @mbg.generated  Fri Nov 08 08:36:13 CET 2019
	 */
	public String updateByPrimaryKeySelective(ScsAsistencia record) {
		SQL sql = new SQL();
		sql.UPDATE("SCS_ASISTENCIA");
		if (record.getFechahora() != null) {
			sql.SET("FECHAHORA = #{fechahora,jdbcType=TIMESTAMP}");
		}
		if (record.getIdtipoasistencia() != null) {
			sql.SET("IDTIPOASISTENCIA = #{idtipoasistencia,jdbcType=DECIMAL}");
		}
		if (record.getIdturno() != null) {
			sql.SET("IDTURNO = #{idturno,jdbcType=DECIMAL}");
		}
		if (record.getIdguardia() != null) {
			sql.SET("IDGUARDIA = #{idguardia,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getIdpersonacolegiado() != null) {
			sql.SET("IDPERSONACOLEGIADO = #{idpersonacolegiado,jdbcType=DECIMAL}");
		}
		if (record.getObservaciones() != null) {
			sql.SET("OBSERVACIONES = #{observaciones,jdbcType=VARCHAR}");
		}
		if (record.getIncidencias() != null) {
			sql.SET("INCIDENCIAS = #{incidencias,jdbcType=VARCHAR}");
		}
		if (record.getFechaanulacion() != null) {
			sql.SET("FECHAANULACION = #{fechaanulacion,jdbcType=TIMESTAMP}");
		}
		if (record.getMotivosanulacion() != null) {
			sql.SET("MOTIVOSANULACION = #{motivosanulacion,jdbcType=VARCHAR}");
		}
		if (record.getDelitosimputados() != null) {
			sql.SET("DELITOSIMPUTADOS = #{delitosimputados,jdbcType=VARCHAR}");
		}
		if (record.getContrarios() != null) {
			sql.SET("CONTRARIOS = #{contrarios,jdbcType=VARCHAR}");
		}
		if (record.getDatosdefensajuridica() != null) {
			sql.SET("DATOSDEFENSAJURIDICA = #{datosdefensajuridica,jdbcType=VARCHAR}");
		}
		if (record.getFechacierre() != null) {
			sql.SET("FECHACIERRE = #{fechacierre,jdbcType=TIMESTAMP}");
		}
		if (record.getIdtipoasistenciacolegio() != null) {
			sql.SET("IDTIPOASISTENCIACOLEGIO = #{idtipoasistenciacolegio,jdbcType=DECIMAL}");
		}
		if (record.getDesignaAnio() != null) {
			sql.SET("DESIGNA_ANIO = #{designaAnio,jdbcType=DECIMAL}");
		}
		if (record.getDesignaNumero() != null) {
			sql.SET("DESIGNA_NUMERO = #{designaNumero,jdbcType=DECIMAL}");
		}
		if (record.getIdpersonajg() != null) {
			sql.SET("IDPERSONAJG = #{idpersonajg,jdbcType=DECIMAL}");
		}
		if (record.getFacturado() != null) {
			sql.SET("FACTURADO = #{facturado,jdbcType=VARCHAR}");
		}
		if (record.getPagado() != null) {
			sql.SET("PAGADO = #{pagado,jdbcType=VARCHAR}");
		}
		if (record.getIdfacturacion() != null) {
			sql.SET("IDFACTURACION = #{idfacturacion,jdbcType=DECIMAL}");
		}
		if (record.getDesignaTurno() != null) {
			sql.SET("DESIGNA_TURNO = #{designaTurno,jdbcType=DECIMAL}");
		}
		if (record.getNumerodiligencia() != null) {
			sql.SET("NUMERODILIGENCIA = #{numerodiligencia,jdbcType=VARCHAR}");
		}
		if (record.getNumeroprocedimiento() != null) {
			sql.SET("NUMEROPROCEDIMIENTO = #{numeroprocedimiento,jdbcType=VARCHAR}");
		}
		if (record.getComisaria() != null) {
			sql.SET("COMISARIA = #{comisaria,jdbcType=DECIMAL}");
		}
		if (record.getComisariaidinstitucion() != null) {
			sql.SET("COMISARIAIDINSTITUCION = #{comisariaidinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getJuzgado() != null) {
			sql.SET("JUZGADO = #{juzgado,jdbcType=DECIMAL}");
		}
		if (record.getJuzgadoidinstitucion() != null) {
			sql.SET("JUZGADOIDINSTITUCION = #{juzgadoidinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdestadoasistencia() != null) {
			sql.SET("IDESTADOASISTENCIA = #{idestadoasistencia,jdbcType=DECIMAL}");
		}
		if (record.getFechaestadoasistencia() != null) {
			sql.SET("FECHAESTADOASISTENCIA = #{fechaestadoasistencia,jdbcType=TIMESTAMP}");
		}
		if (record.getEjgidtipoejg() != null) {
			sql.SET("EJGIDTIPOEJG = #{ejgidtipoejg,jdbcType=DECIMAL}");
		}
		if (record.getEjganio() != null) {
			sql.SET("EJGANIO = #{ejganio,jdbcType=DECIMAL}");
		}
		if (record.getEjgnumero() != null) {
			sql.SET("EJGNUMERO = #{ejgnumero,jdbcType=DECIMAL}");
		}
		if (record.getCodigo() != null) {
			sql.SET("CODIGO = #{codigo,jdbcType=VARCHAR}");
		}
		if (record.getNig() != null) {
			sql.SET("NIG = #{nig,jdbcType=VARCHAR}");
		}
		if (record.getIdpretension() != null) {
			sql.SET("IDPRETENSION = #{idpretension,jdbcType=DECIMAL}");
		}
		if (record.getAsistidoautorizaeejg() != null) {
			sql.SET("ASISTIDOAUTORIZAEEJG = #{asistidoautorizaeejg,jdbcType=VARCHAR}");
		}
		if (record.getIdtipoasistido() != null) {
			sql.SET("IDTIPOASISTIDO = #{idtipoasistido,jdbcType=DECIMAL}");
		}
		if (record.getIdorigenasistencia() != null) {
			sql.SET("IDORIGENASISTENCIA = #{idorigenasistencia,jdbcType=DECIMAL}");
		}
		if (record.getAsistidosolicitajg() != null) {
			sql.SET("ASISTIDOSOLICITAJG = #{asistidosolicitajg,jdbcType=VARCHAR}");
		}
		if (record.getFechasolicitud() != null) {
			sql.SET("FECHASOLICITUD = #{fechasolicitud,jdbcType=TIMESTAMP}");
		}
		if (record.getIdsolicitudcentralita() != null) {
			sql.SET("IDSOLICITUDCENTRALITA = #{idsolicitudcentralita,jdbcType=DECIMAL}");
		}
		if (record.getIdmovimiento() != null) {
			sql.SET("IDMOVIMIENTO = #{idmovimiento,jdbcType=DECIMAL}");
		}
		sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
		sql.WHERE("ANIO = #{anio,jdbcType=DECIMAL}");
		sql.WHERE("NUMERO = #{numero,jdbcType=DECIMAL}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_ASISTENCIA
	 * @mbg.generated  Fri Nov 08 08:36:13 CET 2019
	 */
	protected void applyWhere(SQL sql, ScsAsistenciaExample example, boolean includeExamplePhrase) {
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