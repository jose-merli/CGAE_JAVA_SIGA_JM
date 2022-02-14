package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.DatosBancariosSearchAnexosDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosSearchBancoDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosSearchDTO;
import org.itcgae.siga.db.mappers.CenGruposclienteClienteSqlProvider;

public class CenCuentasbancariasSqlExtendsProvider extends CenGruposclienteClienteSqlProvider{


	
	public String selectCuentasBancarias(DatosBancariosSearchDTO datosBancariosSearchDTO, Short idInstitucion) {
		SQL sql = new SQL();
		sql.SELECT("CUENTA.TITULAR");
		sql.SELECT("CUENTA.IDCUENTA");
		sql.SELECT("CUENTA.IBAN");
		sql.SELECT("CUENTA.FECHAMODIFICACION");
		sql.SELECT("F_SIGA_FORMATOIBAN(CUENTA.IBAN) AS IBANFORMATEADO");
		sql.SELECT("DECODE(DECODE(CUENTA.ABONOSJCS,'1',DECODE(CUENTA.ABONOCARGO,'T','ABONO/CARGO', DECODE(CUENTA.ABONOCARGO,'A','ABONO',DECODE(CUENTA.ABONOCARGO,'C','CARGO',''))) || '/SJCS',\r\n" + 
				   "DECODE(CUENTA.ABONOCARGO,'T','ABONO/CARGO', DECODE(CUENTA.ABONOCARGO,'A','ABONO',DECODE(CUENTA.ABONOCARGO,'C','CARGO','')))),'ABONO/CARGO/SJCS', 'PARA TODO',\r\n" + 
				   "DECODE(CUENTA.ABONOSJCS,'1',DECODE(CUENTA.ABONOCARGO,'T','ABONO/CARGO', DECODE(CUENTA.ABONOCARGO,'A','ABONO',DECODE(CUENTA.ABONOCARGO,'C','CARGO',''))) || '/SJCS',\r\n" + 
				   "DECODE(CUENTA.ABONOCARGO,'T','ABONO/CARGO', DECODE(CUENTA.ABONOCARGO,'A','ABONO',DECODE(CUENTA.ABONOCARGO,'C','CARGO','')))))AS USO");
		sql.SELECT("BANCO.BIC");
		sql.SELECT("MANDATOSERVICIO.FIRMA_FECHA AS FECHAFIRMASERVICIOS");
		sql.SELECT("MANDATOPRODUCTO.FIRMA_FECHA AS FECHAFIRMAPRODUCTOS");
		sql.SELECT("CUENTA.IDINSTITUCION");
		sql.SELECT("CUENTA.FECHABAJA");

		sql.FROM("CEN_PERSONA PER");
		sql.INNER_JOIN("CEN_CUENTASBANCARIAS CUENTA ON CUENTA.IDPERSONA = PER.IDPERSONA");
		sql.INNER_JOIN("CEN_BANCOS BANCO ON BANCO.CODIGO = CUENTA.CBO_CODIGO");
		sql.LEFT_OUTER_JOIN("CEN_MANDATOS_CUENTASBANCARIAS MANDATOSERVICIO ON (PER.IDPERSONA = MANDATOSERVICIO.IDPERSONA AND MANDATOSERVICIO.TIPOMANDATO = '0' AND MANDATOSERVICIO.IDCUENTA = CUENTA.IDCUENTA  AND MANDATOSERVICIO.IDINSTITUCION = CUENTA.IDINSTITUCION)");
		sql.LEFT_OUTER_JOIN("CEN_MANDATOS_CUENTASBANCARIAS MANDATOPRODUCTO ON (PER.IDPERSONA = MANDATOPRODUCTO.IDPERSONA AND MANDATOPRODUCTO.TIPOMANDATO = '1' AND MANDATOPRODUCTO.IDCUENTA = CUENTA.IDCUENTA  AND MANDATOPRODUCTO.IDINSTITUCION = CUENTA.IDINSTITUCION)");

		sql.WHERE("PER.IDPERSONA = '"+datosBancariosSearchDTO.getIdPersona()+"'");
		if (!datosBancariosSearchDTO.getHistorico()) {
			sql.WHERE("CUENTA.FECHABAJA is null");
		}
		
		sql.WHERE("CUENTA.IDINSTITUCION = '"+idInstitucion+"'");
		sql.ORDER_BY("CUENTA.FECHABAJA DESC");
		return sql.toString();
	}
	
	
	public String selectGeneralCuentasBancarias(DatosBancariosSearchDTO datosBancariosSearchDTO, String idInstitucion) {
		SQL sql = new SQL();
		sql.SELECT("CUENTA.TITULAR");
		sql.SELECT("CUENTA.IDCUENTA");
		sql.SELECT("CUENTA.FECHAMODIFICACION");
		sql.SELECT("CUENTA.IBAN");
		sql.SELECT("F_SIGA_FORMATOIBAN(CUENTA.IBAN) AS IBANFORMATEADO");
		sql.SELECT("DECODE(CUENTA.ABONOSJCS,'1',DECODE(CUENTA.ABONOCARGO,'T','ABONO/CARGO', DECODE(CUENTA.ABONOCARGO,'A','ABONO',DECODE(CUENTA.ABONOCARGO,'C','CARGO',''))) || '/SJCS',\r\n" + 
				"DECODE(CUENTA.ABONOCARGO,'T','ABONO/CARGO', DECODE(CUENTA.ABONOCARGO,'A','ABONO',DECODE(CUENTA.ABONOCARGO,'C','CARGO',''))))AS USO");
		sql.SELECT("BANCO.BIC");
		sql.SELECT("CUENTA.CUENTACONTABLE AS CUENTACONTABLE");
		sql.SELECT("BANCO.NOMBRE AS BANCO");
		sql.SELECT("PER.NIFCIF AS NIFTITULAR");
		sql.SELECT("CUENTA.IDINSTITUCION");
		sql.SELECT("CUENTA.FECHABAJA");

		sql.FROM("CEN_PERSONA PER");
		sql.INNER_JOIN("CEN_CUENTASBANCARIAS CUENTA ON CUENTA.IDPERSONA = PER.IDPERSONA");
		sql.INNER_JOIN("CEN_BANCOS BANCO ON BANCO.CODIGO = CUENTA.CBO_CODIGO");

		sql.WHERE("PER.IDPERSONA = '"+datosBancariosSearchDTO.getIdPersona()+"'");
		//sql.WHERE("CUENTA.FECHABAJA is null");
		sql.WHERE("CUENTA.IDCUENTA = '"+datosBancariosSearchDTO.getIdCuenta()+"'");
		sql.WHERE("CUENTA.IDINSTITUCION = '"+idInstitucion+"'");
		sql.ORDER_BY("CUENTA.FECHABAJA DESC");

		return sql.toString();
	}
	
	public String selectMandatos(DatosBancariosSearchDTO datosBancariosSearchDTO, String idInstitucion) {
		SQL sql = new SQL();
		sql.SELECT("CUENTA.IDCUENTA");
		sql.SELECT("MANDATOSERVICIO.REFMANDATOSEPA AS REFERENCIASERVICIO");
		sql.SELECT("MANDATOSERVICIO.IDMANDATO AS IDMANDATOSERVICIO");
		sql.SELECT("MANDATOSERVICIO.ESQUEMA AS ESQUEMASERVICIO");
		sql.SELECT("(SELECT DESCRIPCION FROM GEN_RECURSOS   where idrecurso like 'censo.fichaCliente.datosBancarios.tipoPago.recurrente' and idlenguaje = '1') AS TIPOPAGOSERVICIO");
		sql.SELECT("MANDATOPRODUCTO.REFMANDATOSEPA AS REFERENCIAPRODUCTO");
		sql.SELECT("MANDATOPRODUCTO.ESQUEMA AS ESQUEMAPRODUCTO");
		sql.SELECT("(SELECT DESCRIPCION FROM GEN_RECURSOS   where idrecurso like 'censo.fichaCliente.datosBancarios.tipoPago.recurrente' and idlenguaje = '1') AS TIPOPAGOPRODUCTO");
		sql.SELECT("MANDATOPRODUCTO.IDMANDATO AS IDMANDATOPRODUCTO,  MANDATOSERVICIO.DEUDOR_TIPOID as TIPOIDSERVICIO, MANDATOPRODUCTO.DEUDOR_TIPOID as TIPOIDPRODUCTO, MANDATOSERVICIO.DEUDOR_ID as IDENTIFSERVICIO, MANDATOPRODUCTO.DEUDOR_ID as IDENTIFPRODUCTO");
		sql.FROM("CEN_PERSONA PER");
		sql.INNER_JOIN("CEN_CUENTASBANCARIAS CUENTA ON CUENTA.IDPERSONA = PER.IDPERSONA");
		sql.INNER_JOIN("CEN_MANDATOS_CUENTASBANCARIAS MANDATOSERVICIO ON (PER.IDPERSONA = MANDATOSERVICIO.IDPERSONA AND MANDATOSERVICIO.TIPOMANDATO = '0' AND  MANDATOSERVICIO.IDINSTITUCION = CUENTA.IDINSTITUCION AND MANDATOSERVICIO.IDCUENTA = '"+datosBancariosSearchDTO.getIdCuenta()+"')");
		sql.INNER_JOIN("CEN_MANDATOS_CUENTASBANCARIAS MANDATOPRODUCTO ON (PER.IDPERSONA = MANDATOPRODUCTO.IDPERSONA AND MANDATOPRODUCTO.TIPOMANDATO = '1' AND  MANDATOPRODUCTO.IDINSTITUCION = CUENTA.IDINSTITUCION AND MANDATOPRODUCTO.IDCUENTA = '"+datosBancariosSearchDTO.getIdCuenta()+"')");

		sql.WHERE("PER.IDPERSONA = '"+datosBancariosSearchDTO.getIdPersona()+"'");
		sql.WHERE("CUENTA.FECHABAJA is null");
		sql.WHERE("CUENTA.IDINSTITUCION = '"+idInstitucion+"'");
		sql.WHERE("CUENTA.IDCUENTA = '"+datosBancariosSearchDTO.getIdCuenta()+"'");
		
		return sql.toString();
	}
	
	public String selectNewIdCuenta(String idPersona) {
		SQL sql = new SQL();
		sql.SELECT("NVL(MAX(CUENTA.IDCUENTA) + 1,1) AS IDCUENTA");
		sql.FROM("CEN_CUENTASBANCARIAS CUENTA");
		sql.WHERE("CUENTA.IDPERSONA =" + idPersona);
		
		return sql.toString();
	}
	
	public String selectNewIdAnexo(String idPersona,String idCuenta,String idMandato,String institucion) {
		SQL sql = new SQL();
		sql.SELECT("NVL(MAX(CUENTA.IDANEXO) + 1,1) AS IDANEXO");
		sql.FROM("CEN_ANEXOS_CUENTASBANCARIAS CUENTA");
		sql.WHERE("CUENTA.IDPERSONA = '"+idPersona+"'");
		sql.WHERE("CUENTA.IDMANDATO = '"+idMandato+"'");
		sql.WHERE("CUENTA.IDCUENTA = '"+idCuenta+"'");
		sql.WHERE("CUENTA.IDINSTITUCION = '"+institucion+"'");
		return sql.toString();
	}
	
	
	public String getComboEsquemas(String idlenguaje) {
		SQL sql = new SQL();
		sql.SELECT("DECODE(IDRECURSO,'censo.fichaCliente.datosBancarios.esquema.b2b','2',DECODE(IDRECURSO,'censo.fichaCliente.datosBancarios.esquema.core','0','1')) AS VALUE");
		sql.SELECT("DESCRIPCION");
		sql.FROM("GEN_RECURSOS");
		sql.WHERE("IDRECURSO  LIKE 'censo.fichaCliente.datosBancarios.esquema.%'");
		sql.WHERE("IDLENGUAJE = '"+idlenguaje+"'");
		sql.ORDER_BY("IDRECURSO");
		return sql.toString();
	}
	
	public String selectBanks(DatosBancariosSearchBancoDTO datosBancariosSearchBancoDTO) {
		SQL sql = new SQL();
		sql.SELECT("BANCO.NOMBRE AS BANCO");
		sql.SELECT("BANCO.BIC AS BIC");
		sql.FROM("CEN_BANCOS BANCO");

		sql.WHERE("BANCO.CODIGO = '"+datosBancariosSearchBancoDTO.getiban()+"'");
		
		return sql.toString();
	}
	
	public String selectAnexos(DatosBancariosSearchAnexosDTO datosBancariosSearchAnexosDTO) {
		SQL sql = new SQL();

        sql.SELECT("IDMANDATO");
		sql.SELECT("IDANEXO");
		sql.SELECT("IDPERSONA");
		sql.SELECT("IDCUENTA");
		sql.SELECT("DESCRIPCION");
		sql.SELECT("TIPOMANDATO");
        sql.SELECT("IDINSTITUCION");
		sql.SELECT("TIPO");
		sql.SELECT("TO_CHAR(FECHAUSO,'DD/MM/YYYY') AS FECHAUSO") ;
		sql.SELECT("TO_CHAR(FIRMAFECHA,'DD/MM/YYYY')AS FIRMAFECHA");
		sql.SELECT("FIRMALUGAR");
		sql.SELECT("IDFICHEROFIRMA");
		sql.FROM("( SELECT\r\n" + 
				" MANDATO.IDMANDATO\r\n" + 
				" ,NULL AS IDANEXO\r\n" + 
				" ,PER.IDPERSONA\r\n" + 
				" ,MANDATO.IDCUENTA\r\n" + 
				" ,'' AS DESCRIPCION\r\n" + 
				" ,DECODE(MANDATO.TIPOMANDATO,1,'PRODUCTO','SERVICIO') AS TIPOMANDATO\r\n" + 
				" ,'MANDATO' AS TIPO\r\n" + 
				" ,FECHAUSO\r\n" + 
				" ,FIRMA_FECHA AS FIRMAFECHA\r\n" + 
				" ,FIRMA_LUGAR AS FIRMALUGAR\r\n" + 
				" ,IDFICHEROFIRMA\r\n" + 
				" ,MANDATO.IDINSTITUCION\r\n" + 
				" FROM CEN_PERSONA PER\r\n" + 
				"INNER JOIN CEN_MANDATOS_CUENTASBANCARIAS MANDATO ON (PER.IDPERSONA = MANDATO.IDPERSONA)\r\n" + 
				"\r\n" + 
				"UNION ALL\r\n" + 
				"\r\n" + 
				" SELECT\r\n" + 
				" MANDATO.IDMANDATO\r\n" + 
				" ,ANEXO.IDANEXO\r\n" + 
				" ,PER.IDPERSONA\r\n" + 
				" ,MANDATO.IDCUENTA\r\n" + 
				" ,ANEXO.ORIGEN AS DESCRIPCION\r\n" + 
				" ,DECODE(MANDATO.TIPOMANDATO,1,'PRODUCTO','SERVICIO') AS TIPOMANDATO\r\n" + 
				" ,'ANEXO' AS TIPO\r\n" + 
				" ,ANEXO.FECHACREACION AS FECHAUSO\r\n" + 
				" ,ANEXO.FIRMA_FECHA AS FIRMAFECHA\r\n" + 
				" ,ANEXO.FIRMA_LUGAR AS FIRMALUGAR\r\n" + 
				" ,ANEXO.IDFICHEROFIRMA\r\n" + 
				" ,MANDATO.IDINSTITUCION\r\n" + 
				" FROM CEN_PERSONA PER\r\n" + 
				"INNER JOIN CEN_MANDATOS_CUENTASBANCARIAS MANDATO ON (PER.IDPERSONA = MANDATO.IDPERSONA)\r\n" + 
				"INNER JOIN CEN_ANEXOS_CUENTASBANCARIAS ANEXO ON (PER.IDPERSONA = ANEXO.IDPERSONA AND ANEXO.IDCUENTA =  MANDATO.IDCUENTA AND MANDATO.IDMANDATO = ANEXO.IDMANDATO))");

		sql.WHERE("IDPERSONA = '"+datosBancariosSearchAnexosDTO.getIdPersona()+"'");
		sql.WHERE("IDCUENTA = '"+datosBancariosSearchAnexosDTO.getIdCuenta()+"'");
		sql.WHERE("IDINSTITUCION = '"+datosBancariosSearchAnexosDTO.getIdInstitucion()+"'");
		sql.ORDER_BY("TIPO DESC,TIPOMANDATO ");
		
		return sql.toString();
	}
	
	public String selectMaxId(Long idPersona, Short institucion){
		SQL sql = new SQL();
		
		sql.SELECT("NVL(MAX(IDCUENTA) + 1,1) AS IDCUENTA");
		sql.FROM("CEN_CUENTASBANCARIAS");
		sql.WHERE("IDPERSONA = '"+idPersona+"'");
		sql.WHERE("IDINSTITUCION = '"+institucion+"'");
		
		return sql.toString();
	}

	public String getComboCuentas(String idPersona, String idInstitucion) {
		SQL sql = new SQL();
		sql.SELECT("IDCUENTA,NUMEROCUENTA");
		sql.FROM("CEN_CUENTASBANCARIAS");
		sql.WHERE("IDPERSONA = '"+idPersona+"'");
		sql.WHERE("IDINSTITUCION = '"+idInstitucion+"'");
		sql.ORDER_BY("IDCUENTA");
		return sql.toString();
	}

	public String getCuentaActivaServiciosActivos(Short idInstitucion, Long idPersona) {
		SQL countNumServicios = new SQL();
		countNumServicios.SELECT("count(*)");
		countNumServicios.FROM("PYS_SUSCRIPCION SUS");
		countNumServicios.WHERE("SUS.IDINSTITUCION = BANCO.IDINSTITUCION");
		countNumServicios.WHERE("SUS.IDPERSONA = BANCO.IDPERSONA");
		countNumServicios.WHERE("SUS.IDCUENTA = BANCO.IDCUENTA");
		countNumServicios.WHERE("SUS.FECHABAJA IS NULL");

		SQL numServicios = new SQL();
		numServicios.SELECT("BANCO.IDCUENTA");
		numServicios.SELECT("BANCO.NUM_SERVICIOS_ASOCIADOS");
		numServicios.SELECT("( " + countNumServicios.toString() + " ) AS NUM_SERVICIOS_ASOCIADOS");
		numServicios.FROM("CEN_CUENTASBANCARIAS BANCO");
		countNumServicios.WHERE("BANCO.IDINSTITUCION = '" + idInstitucion + "'");
		countNumServicios.WHERE("BANCO.IDPERSONA = '" + idPersona + "'");
		countNumServicios.WHERE("BANCO.FECHABAJA IS NULL");
		countNumServicios.WHERE("BANCO.ABONOCARGO IN ('T', 'C')");

		SQL principal = new SQL();
		principal.SELECT("TABLA.IDCUENTA");
		principal.SELECT("TABLA.NUM_SERVICIOS_ASOCIADOS");
		principal.FROM("( " + numServicios.toString() + " ) TABLA");
		principal.WHERE("TABLA.NUM_SERVICIOS_ASOCIADOS > 0");
		principal.ORDER_BY("TABLA.NUM_SERVICIOS_ASOCIADOS DESC");
		return principal.toString();
	}

}


