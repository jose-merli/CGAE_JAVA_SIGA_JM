package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.ScsGuardiasturno;
import org.itcgae.siga.db.entities.ScsGuardiasturnoExample.Criteria;
import org.itcgae.siga.db.entities.ScsGuardiasturnoExample.Criterion;
import org.itcgae.siga.db.entities.ScsGuardiasturnoExample;

public class ScsGuardiasturnoSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_GUARDIASTURNO
	 * @mbg.generated  Fri Nov 22 09:48:03 CET 2019
	 */
	public String countByExample(ScsGuardiasturnoExample example) {
		SQL sql = new SQL();
		sql.SELECT("count(*)").FROM("SCS_GUARDIASTURNO");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_GUARDIASTURNO
	 * @mbg.generated  Fri Nov 22 09:48:03 CET 2019
	 */
	public String deleteByExample(ScsGuardiasturnoExample example) {
		SQL sql = new SQL();
		sql.DELETE_FROM("SCS_GUARDIASTURNO");
		applyWhere(sql, example, false);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_GUARDIASTURNO
	 * @mbg.generated  Fri Nov 22 09:48:03 CET 2019
	 */
	public String insertSelective(ScsGuardiasturno record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("SCS_GUARDIASTURNO");
		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", "#{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdturno() != null) {
			sql.VALUES("IDTURNO", "#{idturno,jdbcType=DECIMAL}");
		}
		if (record.getIdguardia() != null) {
			sql.VALUES("IDGUARDIA", "#{idguardia,jdbcType=DECIMAL}");
		}
		if (record.getNombre() != null) {
			sql.VALUES("NOMBRE", "#{nombre,jdbcType=VARCHAR}");
		}
		if (record.getNumeroletradosguardia() != null) {
			sql.VALUES("NUMEROLETRADOSGUARDIA", "#{numeroletradosguardia,jdbcType=DECIMAL}");
		}
		if (record.getNumerosustitutosguardia() != null) {
			sql.VALUES("NUMEROSUSTITUTOSGUARDIA", "#{numerosustitutosguardia,jdbcType=DECIMAL}");
		}
		if (record.getDiasguardia() != null) {
			sql.VALUES("DIASGUARDIA", "#{diasguardia,jdbcType=DECIMAL}");
		}
		if (record.getDiaspagados() != null) {
			sql.VALUES("DIASPAGADOS", "#{diaspagados,jdbcType=DECIMAL}");
		}
		if (record.getValidarjustificaciones() != null) {
			sql.VALUES("VALIDARJUSTIFICACIONES", "#{validarjustificaciones,jdbcType=VARCHAR}");
		}
		if (record.getDiasseparacionguardias() != null) {
			sql.VALUES("DIASSEPARACIONGUARDIAS", "#{diasseparacionguardias,jdbcType=DECIMAL}");
		}
		if (record.getIdordenacioncolas() != null) {
			sql.VALUES("IDORDENACIONCOLAS", "#{idordenacioncolas,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "#{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", "#{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getNumeroasistencias() != null) {
			sql.VALUES("NUMEROASISTENCIAS", "#{numeroasistencias,jdbcType=DECIMAL}");
		}
		if (record.getDescripcion() != null) {
			sql.VALUES("DESCRIPCION", "#{descripcion,jdbcType=VARCHAR}");
		}
		if (record.getDescripcionfacturacion() != null) {
			sql.VALUES("DESCRIPCIONFACTURACION", "#{descripcionfacturacion,jdbcType=VARCHAR}");
		}
		if (record.getDescripcionpago() != null) {
			sql.VALUES("DESCRIPCIONPAGO", "#{descripcionpago,jdbcType=VARCHAR}");
		}
		if (record.getIdpartidapresupuestaria() != null) {
			sql.VALUES("IDPARTIDAPRESUPUESTARIA", "#{idpartidapresupuestaria,jdbcType=DECIMAL}");
		}
		if (record.getNumeroactuaciones() != null) {
			sql.VALUES("NUMEROACTUACIONES", "#{numeroactuaciones,jdbcType=DECIMAL}");
		}
		if (record.getIdpersonaUltimo() != null) {
			sql.VALUES("IDPERSONA_ULTIMO", "#{idpersonaUltimo,jdbcType=DECIMAL}");
		}
		if (record.getTipodiasguardia() != null) {
			sql.VALUES("TIPODIASGUARDIA", "#{tipodiasguardia,jdbcType=VARCHAR}");
		}
		if (record.getDiasperiodo() != null) {
			sql.VALUES("DIASPERIODO", "#{diasperiodo,jdbcType=DECIMAL}");
		}
		if (record.getTipodiasperiodo() != null) {
			sql.VALUES("TIPODIASPERIODO", "#{tipodiasperiodo,jdbcType=VARCHAR}");
		}
		if (record.getFestivos() != null) {
			sql.VALUES("FESTIVOS", "#{festivos,jdbcType=VARCHAR}");
		}
		if (record.getSeleccionlaborables() != null) {
			sql.VALUES("SELECCIONLABORABLES", "#{seleccionlaborables,jdbcType=VARCHAR}");
		}
		if (record.getSeleccionfestivos() != null) {
			sql.VALUES("SELECCIONFESTIVOS", "#{seleccionfestivos,jdbcType=VARCHAR}");
		}
		if (record.getIdturnosustitucion() != null) {
			sql.VALUES("IDTURNOSUSTITUCION", "#{idturnosustitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdguardiasustitucion() != null) {
			sql.VALUES("IDGUARDIASUSTITUCION", "#{idguardiasustitucion,jdbcType=DECIMAL}");
		}
		if (record.getPorgrupos() != null) {
			sql.VALUES("PORGRUPOS", "#{porgrupos,jdbcType=VARCHAR}");
		}
		if (record.getRotarcomponentes() != null) {
			sql.VALUES("ROTARCOMPONENTES", "#{rotarcomponentes,jdbcType=VARCHAR}");
		}
		if (record.getIdinstitucionprincipal() != null) {
			sql.VALUES("IDINSTITUCIONPRINCIPAL", "#{idinstitucionprincipal,jdbcType=DECIMAL}");
		}
		if (record.getIdturnoprincipal() != null) {
			sql.VALUES("IDTURNOPRINCIPAL", "#{idturnoprincipal,jdbcType=DECIMAL}");
		}
		if (record.getIdguardiaprincipal() != null) {
			sql.VALUES("IDGUARDIAPRINCIPAL", "#{idguardiaprincipal,jdbcType=DECIMAL}");
		}
		if (record.getFechasuscripcionUltimo() != null) {
			sql.VALUES("FECHASUSCRIPCION_ULTIMO", "#{fechasuscripcionUltimo,jdbcType=TIMESTAMP}");
		}
		if (record.getIdgrupoguardiaUltimo() != null) {
			sql.VALUES("IDGRUPOGUARDIA_ULTIMO", "#{idgrupoguardiaUltimo,jdbcType=DECIMAL}");
		}
		if (record.getIdtipoguardia() != null) {
			sql.VALUES("IDTIPOGUARDIA", "#{idtipoguardia,jdbcType=DECIMAL}");
		}
		if (record.getEnviocentralita() != null) {
			sql.VALUES("ENVIOCENTRALITA", "#{enviocentralita,jdbcType=DECIMAL}");
		}
		if (record.getFechabaja() != null) {
			sql.VALUES("FECHABAJA", "#{fechabaja,jdbcType=TIMESTAMP}");
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_GUARDIASTURNO
	 * @mbg.generated  Fri Nov 22 09:48:03 CET 2019
	 */
	public String selectByExample(ScsGuardiasturnoExample example) {
		SQL sql = new SQL();
		if (example != null && example.isDistinct()) {
			sql.SELECT_DISTINCT("IDINSTITUCION");
		} else {
			sql.SELECT("IDINSTITUCION");
		}
		sql.SELECT("IDTURNO");
		sql.SELECT("IDGUARDIA");
		sql.SELECT("NOMBRE");
		sql.SELECT("NUMEROLETRADOSGUARDIA");
		sql.SELECT("NUMEROSUSTITUTOSGUARDIA");
		sql.SELECT("DIASGUARDIA");
		sql.SELECT("DIASPAGADOS");
		sql.SELECT("VALIDARJUSTIFICACIONES");
		sql.SELECT("DIASSEPARACIONGUARDIAS");
		sql.SELECT("IDORDENACIONCOLAS");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("USUMODIFICACION");
		sql.SELECT("NUMEROASISTENCIAS");
		sql.SELECT("DESCRIPCION");
		sql.SELECT("DESCRIPCIONFACTURACION");
		sql.SELECT("DESCRIPCIONPAGO");
		sql.SELECT("IDPARTIDAPRESUPUESTARIA");
		sql.SELECT("NUMEROACTUACIONES");
		sql.SELECT("IDPERSONA_ULTIMO");
		sql.SELECT("TIPODIASGUARDIA");
		sql.SELECT("DIASPERIODO");
		sql.SELECT("TIPODIASPERIODO");
		sql.SELECT("FESTIVOS");
		sql.SELECT("SELECCIONLABORABLES");
		sql.SELECT("SELECCIONFESTIVOS");
		sql.SELECT("IDTURNOSUSTITUCION");
		sql.SELECT("IDGUARDIASUSTITUCION");
		sql.SELECT("PORGRUPOS");
		sql.SELECT("ROTARCOMPONENTES");
		sql.SELECT("IDINSTITUCIONPRINCIPAL");
		sql.SELECT("IDTURNOPRINCIPAL");
		sql.SELECT("IDGUARDIAPRINCIPAL");
		sql.SELECT("FECHASUSCRIPCION_ULTIMO");
		sql.SELECT("IDGRUPOGUARDIA_ULTIMO");
		sql.SELECT("IDTIPOGUARDIA");
		sql.SELECT("ENVIOCENTRALITA");
		sql.SELECT("FECHABAJA");
		sql.FROM("SCS_GUARDIASTURNO");
		applyWhere(sql, example, false);
		if (example != null && example.getOrderByClause() != null) {
			sql.ORDER_BY(example.getOrderByClause());
		}
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_GUARDIASTURNO
	 * @mbg.generated  Fri Nov 22 09:48:03 CET 2019
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		ScsGuardiasturno record = (ScsGuardiasturno) parameter.get("record");
		ScsGuardiasturnoExample example = (ScsGuardiasturnoExample) parameter.get("example");
		SQL sql = new SQL();
		sql.UPDATE("SCS_GUARDIASTURNO");
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdturno() != null) {
			sql.SET("IDTURNO = #{record.idturno,jdbcType=DECIMAL}");
		}
		if (record.getIdguardia() != null) {
			sql.SET("IDGUARDIA = #{record.idguardia,jdbcType=DECIMAL}");
		}
		if (record.getNombre() != null) {
			sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
		}
		if (record.getNumeroletradosguardia() != null) {
			sql.SET("NUMEROLETRADOSGUARDIA = #{record.numeroletradosguardia,jdbcType=DECIMAL}");
		}
		if (record.getNumerosustitutosguardia() != null) {
			sql.SET("NUMEROSUSTITUTOSGUARDIA = #{record.numerosustitutosguardia,jdbcType=DECIMAL}");
		}
		if (record.getDiasguardia() != null) {
			sql.SET("DIASGUARDIA = #{record.diasguardia,jdbcType=DECIMAL}");
		}
		if (record.getDiaspagados() != null) {
			sql.SET("DIASPAGADOS = #{record.diaspagados,jdbcType=DECIMAL}");
		}
		if (record.getValidarjustificaciones() != null) {
			sql.SET("VALIDARJUSTIFICACIONES = #{record.validarjustificaciones,jdbcType=VARCHAR}");
		}
		if (record.getDiasseparacionguardias() != null) {
			sql.SET("DIASSEPARACIONGUARDIAS = #{record.diasseparacionguardias,jdbcType=DECIMAL}");
		}
		if (record.getIdordenacioncolas() != null) {
			sql.SET("IDORDENACIONCOLAS = #{record.idordenacioncolas,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getNumeroasistencias() != null) {
			sql.SET("NUMEROASISTENCIAS = #{record.numeroasistencias,jdbcType=DECIMAL}");
		}
		if (record.getDescripcion() != null) {
			sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
		}
		if (record.getDescripcionfacturacion() != null) {
			sql.SET("DESCRIPCIONFACTURACION = #{record.descripcionfacturacion,jdbcType=VARCHAR}");
		}
		if (record.getDescripcionpago() != null) {
			sql.SET("DESCRIPCIONPAGO = #{record.descripcionpago,jdbcType=VARCHAR}");
		}
		if (record.getIdpartidapresupuestaria() != null) {
			sql.SET("IDPARTIDAPRESUPUESTARIA = #{record.idpartidapresupuestaria,jdbcType=DECIMAL}");
		}
		if (record.getNumeroactuaciones() != null) {
			sql.SET("NUMEROACTUACIONES = #{record.numeroactuaciones,jdbcType=DECIMAL}");
		}
		if (record.getIdpersonaUltimo() != null) {
			sql.SET("IDPERSONA_ULTIMO = #{record.idpersonaUltimo,jdbcType=DECIMAL}");
		}
		if (record.getTipodiasguardia() != null) {
			sql.SET("TIPODIASGUARDIA = #{record.tipodiasguardia,jdbcType=VARCHAR}");
		}
		if (record.getDiasperiodo() != null) {
			sql.SET("DIASPERIODO = #{record.diasperiodo,jdbcType=DECIMAL}");
		}
		if (record.getTipodiasperiodo() != null) {
			sql.SET("TIPODIASPERIODO = #{record.tipodiasperiodo,jdbcType=VARCHAR}");
		}
		if (record.getFestivos() != null) {
			sql.SET("FESTIVOS = #{record.festivos,jdbcType=VARCHAR}");
		}
		if (record.getSeleccionlaborables() != null) {
			sql.SET("SELECCIONLABORABLES = #{record.seleccionlaborables,jdbcType=VARCHAR}");
		}
		if (record.getSeleccionfestivos() != null) {
			sql.SET("SELECCIONFESTIVOS = #{record.seleccionfestivos,jdbcType=VARCHAR}");
		}
		if (record.getIdturnosustitucion() != null) {
			sql.SET("IDTURNOSUSTITUCION = #{record.idturnosustitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdguardiasustitucion() != null) {
			sql.SET("IDGUARDIASUSTITUCION = #{record.idguardiasustitucion,jdbcType=DECIMAL}");
		}
		if (record.getPorgrupos() != null) {
			sql.SET("PORGRUPOS = #{record.porgrupos,jdbcType=VARCHAR}");
		}
		if (record.getRotarcomponentes() != null) {
			sql.SET("ROTARCOMPONENTES = #{record.rotarcomponentes,jdbcType=VARCHAR}");
		}
		if (record.getIdinstitucionprincipal() != null) {
			sql.SET("IDINSTITUCIONPRINCIPAL = #{record.idinstitucionprincipal,jdbcType=DECIMAL}");
		}
		if (record.getIdturnoprincipal() != null) {
			sql.SET("IDTURNOPRINCIPAL = #{record.idturnoprincipal,jdbcType=DECIMAL}");
		}
		if (record.getIdguardiaprincipal() != null) {
			sql.SET("IDGUARDIAPRINCIPAL = #{record.idguardiaprincipal,jdbcType=DECIMAL}");
		}
		if (record.getFechasuscripcionUltimo() != null) {
			sql.SET("FECHASUSCRIPCION_ULTIMO = #{record.fechasuscripcionUltimo,jdbcType=TIMESTAMP}");
		}
		if (record.getIdgrupoguardiaUltimo() != null) {
			sql.SET("IDGRUPOGUARDIA_ULTIMO = #{record.idgrupoguardiaUltimo,jdbcType=DECIMAL}");
		}
		if (record.getIdtipoguardia() != null) {
			sql.SET("IDTIPOGUARDIA = #{record.idtipoguardia,jdbcType=DECIMAL}");
		}
		if (record.getEnviocentralita() != null) {
			sql.SET("ENVIOCENTRALITA = #{record.enviocentralita,jdbcType=DECIMAL}");
		}
		if (record.getFechabaja() != null) {
			sql.SET("FECHABAJA = #{record.fechabaja,jdbcType=TIMESTAMP}");
		}
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_GUARDIASTURNO
	 * @mbg.generated  Fri Nov 22 09:48:03 CET 2019
	 */
	public String updateByExample(Map<String, Object> parameter) {
		SQL sql = new SQL();
		sql.UPDATE("SCS_GUARDIASTURNO");
		sql.SET("IDINSTITUCION = #{record.idinstitucion,jdbcType=DECIMAL}");
		sql.SET("IDTURNO = #{record.idturno,jdbcType=DECIMAL}");
		sql.SET("IDGUARDIA = #{record.idguardia,jdbcType=DECIMAL}");
		sql.SET("NOMBRE = #{record.nombre,jdbcType=VARCHAR}");
		sql.SET("NUMEROLETRADOSGUARDIA = #{record.numeroletradosguardia,jdbcType=DECIMAL}");
		sql.SET("NUMEROSUSTITUTOSGUARDIA = #{record.numerosustitutosguardia,jdbcType=DECIMAL}");
		sql.SET("DIASGUARDIA = #{record.diasguardia,jdbcType=DECIMAL}");
		sql.SET("DIASPAGADOS = #{record.diaspagados,jdbcType=DECIMAL}");
		sql.SET("VALIDARJUSTIFICACIONES = #{record.validarjustificaciones,jdbcType=VARCHAR}");
		sql.SET("DIASSEPARACIONGUARDIAS = #{record.diasseparacionguardias,jdbcType=DECIMAL}");
		sql.SET("IDORDENACIONCOLAS = #{record.idordenacioncolas,jdbcType=DECIMAL}");
		sql.SET("FECHAMODIFICACION = #{record.fechamodificacion,jdbcType=TIMESTAMP}");
		sql.SET("USUMODIFICACION = #{record.usumodificacion,jdbcType=DECIMAL}");
		sql.SET("NUMEROASISTENCIAS = #{record.numeroasistencias,jdbcType=DECIMAL}");
		sql.SET("DESCRIPCION = #{record.descripcion,jdbcType=VARCHAR}");
		sql.SET("DESCRIPCIONFACTURACION = #{record.descripcionfacturacion,jdbcType=VARCHAR}");
		sql.SET("DESCRIPCIONPAGO = #{record.descripcionpago,jdbcType=VARCHAR}");
		sql.SET("IDPARTIDAPRESUPUESTARIA = #{record.idpartidapresupuestaria,jdbcType=DECIMAL}");
		sql.SET("NUMEROACTUACIONES = #{record.numeroactuaciones,jdbcType=DECIMAL}");
		sql.SET("IDPERSONA_ULTIMO = #{record.idpersonaUltimo,jdbcType=DECIMAL}");
		sql.SET("TIPODIASGUARDIA = #{record.tipodiasguardia,jdbcType=VARCHAR}");
		sql.SET("DIASPERIODO = #{record.diasperiodo,jdbcType=DECIMAL}");
		sql.SET("TIPODIASPERIODO = #{record.tipodiasperiodo,jdbcType=VARCHAR}");
		sql.SET("FESTIVOS = #{record.festivos,jdbcType=VARCHAR}");
		sql.SET("SELECCIONLABORABLES = #{record.seleccionlaborables,jdbcType=VARCHAR}");
		sql.SET("SELECCIONFESTIVOS = #{record.seleccionfestivos,jdbcType=VARCHAR}");
		sql.SET("IDTURNOSUSTITUCION = #{record.idturnosustitucion,jdbcType=DECIMAL}");
		sql.SET("IDGUARDIASUSTITUCION = #{record.idguardiasustitucion,jdbcType=DECIMAL}");
		sql.SET("PORGRUPOS = #{record.porgrupos,jdbcType=VARCHAR}");
		sql.SET("ROTARCOMPONENTES = #{record.rotarcomponentes,jdbcType=VARCHAR}");
		sql.SET("IDINSTITUCIONPRINCIPAL = #{record.idinstitucionprincipal,jdbcType=DECIMAL}");
		sql.SET("IDTURNOPRINCIPAL = #{record.idturnoprincipal,jdbcType=DECIMAL}");
		sql.SET("IDGUARDIAPRINCIPAL = #{record.idguardiaprincipal,jdbcType=DECIMAL}");
		sql.SET("FECHASUSCRIPCION_ULTIMO = #{record.fechasuscripcionUltimo,jdbcType=TIMESTAMP}");
		sql.SET("IDGRUPOGUARDIA_ULTIMO = #{record.idgrupoguardiaUltimo,jdbcType=DECIMAL}");
		sql.SET("IDTIPOGUARDIA = #{record.idtipoguardia,jdbcType=DECIMAL}");
		sql.SET("ENVIOCENTRALITA = #{record.enviocentralita,jdbcType=DECIMAL}");
		sql.SET("FECHABAJA = #{record.fechabaja,jdbcType=TIMESTAMP}");
		ScsGuardiasturnoExample example = (ScsGuardiasturnoExample) parameter.get("example");
		applyWhere(sql, example, true);
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_GUARDIASTURNO
	 * @mbg.generated  Fri Nov 22 09:48:03 CET 2019
	 */
	public String updateByPrimaryKeySelective(ScsGuardiasturno record) {
		SQL sql = new SQL();
		sql.UPDATE("SCS_GUARDIASTURNO");
		if (record.getNombre() != null) {
			sql.SET("NOMBRE = #{nombre,jdbcType=VARCHAR}");
		}
		if (record.getNumeroletradosguardia() != null) {
			sql.SET("NUMEROLETRADOSGUARDIA = #{numeroletradosguardia,jdbcType=DECIMAL}");
		}
		if (record.getNumerosustitutosguardia() != null) {
			sql.SET("NUMEROSUSTITUTOSGUARDIA = #{numerosustitutosguardia,jdbcType=DECIMAL}");
		}
		if (record.getDiasguardia() != null) {
			sql.SET("DIASGUARDIA = #{diasguardia,jdbcType=DECIMAL}");
		}
		if (record.getDiaspagados() != null) {
			sql.SET("DIASPAGADOS = #{diaspagados,jdbcType=DECIMAL}");
		}
		if (record.getValidarjustificaciones() != null) {
			sql.SET("VALIDARJUSTIFICACIONES = #{validarjustificaciones,jdbcType=VARCHAR}");
		}
		if (record.getDiasseparacionguardias() != null) {
			sql.SET("DIASSEPARACIONGUARDIAS = #{diasseparacionguardias,jdbcType=DECIMAL}");
		}
		if (record.getIdordenacioncolas() != null) {
			sql.SET("IDORDENACIONCOLAS = #{idordenacioncolas,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		if (record.getNumeroasistencias() != null) {
			sql.SET("NUMEROASISTENCIAS = #{numeroasistencias,jdbcType=DECIMAL}");
		}
		if (record.getDescripcion() != null) {
			sql.SET("DESCRIPCION = #{descripcion,jdbcType=VARCHAR}");
		}
		if (record.getDescripcionfacturacion() != null) {
			sql.SET("DESCRIPCIONFACTURACION = #{descripcionfacturacion,jdbcType=VARCHAR}");
		}
		if (record.getDescripcionpago() != null) {
			sql.SET("DESCRIPCIONPAGO = #{descripcionpago,jdbcType=VARCHAR}");
		}
		if (record.getIdpartidapresupuestaria() != null) {
			sql.SET("IDPARTIDAPRESUPUESTARIA = #{idpartidapresupuestaria,jdbcType=DECIMAL}");
		}
		if (record.getNumeroactuaciones() != null) {
			sql.SET("NUMEROACTUACIONES = #{numeroactuaciones,jdbcType=DECIMAL}");
		}
		if (record.getIdpersonaUltimo() != null) {
			sql.SET("IDPERSONA_ULTIMO = #{idpersonaUltimo,jdbcType=DECIMAL}");
		}
		if (record.getTipodiasguardia() != null) {
			sql.SET("TIPODIASGUARDIA = #{tipodiasguardia,jdbcType=VARCHAR}");
		}
		if (record.getDiasperiodo() != null) {
			sql.SET("DIASPERIODO = #{diasperiodo,jdbcType=DECIMAL}");
		}
		if (record.getTipodiasperiodo() != null) {
			sql.SET("TIPODIASPERIODO = #{tipodiasperiodo,jdbcType=VARCHAR}");
		}
		if (record.getFestivos() != null) {
			sql.SET("FESTIVOS = #{festivos,jdbcType=VARCHAR}");
		}
		if (record.getSeleccionlaborables() != null) {
			sql.SET("SELECCIONLABORABLES = #{seleccionlaborables,jdbcType=VARCHAR}");
		}
		if (record.getSeleccionfestivos() != null) {
			sql.SET("SELECCIONFESTIVOS = #{seleccionfestivos,jdbcType=VARCHAR}");
		}
		if (record.getIdturnosustitucion() != null) {
			sql.SET("IDTURNOSUSTITUCION = #{idturnosustitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdguardiasustitucion() != null) {
			sql.SET("IDGUARDIASUSTITUCION = #{idguardiasustitucion,jdbcType=DECIMAL}");
		}
		if (record.getPorgrupos() != null) {
			sql.SET("PORGRUPOS = #{porgrupos,jdbcType=VARCHAR}");
		}
		if (record.getRotarcomponentes() != null) {
			sql.SET("ROTARCOMPONENTES = #{rotarcomponentes,jdbcType=VARCHAR}");
		}
		if (record.getIdinstitucionprincipal() != null) {
			sql.SET("IDINSTITUCIONPRINCIPAL = #{idinstitucionprincipal,jdbcType=DECIMAL}");
		}
		if (record.getIdturnoprincipal() != null) {
			sql.SET("IDTURNOPRINCIPAL = #{idturnoprincipal,jdbcType=DECIMAL}");
		}
		if (record.getIdguardiaprincipal() != null) {
			sql.SET("IDGUARDIAPRINCIPAL = #{idguardiaprincipal,jdbcType=DECIMAL}");
		}
		if (record.getFechasuscripcionUltimo() != null) {
			sql.SET("FECHASUSCRIPCION_ULTIMO = #{fechasuscripcionUltimo,jdbcType=TIMESTAMP}");
		}
		if (record.getIdgrupoguardiaUltimo() != null) {
			sql.SET("IDGRUPOGUARDIA_ULTIMO = #{idgrupoguardiaUltimo,jdbcType=DECIMAL}");
		}
		if (record.getIdtipoguardia() != null) {
			sql.SET("IDTIPOGUARDIA = #{idtipoguardia,jdbcType=DECIMAL}");
		}
		if (record.getEnviocentralita() != null) {
			sql.SET("ENVIOCENTRALITA = #{enviocentralita,jdbcType=DECIMAL}");
		}
		if (record.getFechabaja() != null) {
			sql.SET("FECHABAJA = #{fechabaja,jdbcType=TIMESTAMP}");
		}
		sql.WHERE("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
		sql.WHERE("IDTURNO = #{idturno,jdbcType=DECIMAL}");
		sql.WHERE("IDGUARDIA = #{idguardia,jdbcType=DECIMAL}");
		return sql.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USCGAE.SCS_GUARDIASTURNO
	 * @mbg.generated  Fri Nov 22 09:48:03 CET 2019
	 */
	protected void applyWhere(SQL sql, ScsGuardiasturnoExample example, boolean includeExamplePhrase) {
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