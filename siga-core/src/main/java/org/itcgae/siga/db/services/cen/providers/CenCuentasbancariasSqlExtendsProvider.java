package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.DatosBancariosSearchDTO;
import org.itcgae.siga.db.mappers.CenGruposclienteClienteSqlProvider;

public class CenCuentasbancariasSqlExtendsProvider extends CenGruposclienteClienteSqlProvider{


	
	public String selectCuentasBancarias(DatosBancariosSearchDTO datosBancariosSearchDTO, String idInstitucion) {
		SQL sql = new SQL();
		sql.SELECT("CUENTA.TITULAR");
		sql.SELECT("CUENTA.IDCUENTA");
		sql.SELECT("CUENTA.IBAN");
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
		sql.LEFT_OUTER_JOIN("CEN_MANDATOS_CUENTASBANCARIAS MANDATOSERVICIO ON (PER.IDPERSONA = MANDATOSERVICIO.IDPERSONA AND MANDATOSERVICIO.TIPOMANDATO = '0')");
		sql.LEFT_OUTER_JOIN("CEN_MANDATOS_CUENTASBANCARIAS MANDATOPRODUCTO ON (PER.IDPERSONA = MANDATOPRODUCTO.IDPERSONA AND MANDATOPRODUCTO.TIPOMANDATO = '1')");

		sql.WHERE("PER.IDPERSONA = '"+datosBancariosSearchDTO.getIdPersona()+"'");
		if (!datosBancariosSearchDTO.getHistorico()) {
			sql.WHERE("CUENTA.FECHABAJA is null");
		}
		
		sql.WHERE("CUENTA.IDINSTITUCION = '"+idInstitucion+"'");
		
		return sql.toString();
	}
	
	
	public String selectGeneralCuentasBancarias(DatosBancariosSearchDTO datosBancariosSearchDTO, String idInstitucion) {
		SQL sql = new SQL();
		sql.SELECT("CUENTA.TITULAR");
		sql.SELECT("CUENTA.IDCUENTA");
		sql.SELECT("CUENTA.IBAN");
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
		sql.WHERE("CUENTA.FECHABAJA is null");
		sql.WHERE("CUENTA.IDCUENTA = '"+datosBancariosSearchDTO.getIdCuenta()+"'");
		sql.WHERE("CUENTA.IDINSTITUCION = '"+idInstitucion+"'");
		
		return sql.toString();
	}
	
	public String selectMandatos(DatosBancariosSearchDTO datosBancariosSearchDTO, String idInstitucion) {
		SQL sql = new SQL();
		sql.SELECT("CUENTA.IDCUENTA");
		sql.SELECT("MANDATOSERVICIO.REFMANDATOSEPA AS REFERENCIASERVICIO");
		sql.SELECT("MANDATOSERVICIO.IDMANDATO AS IDMANDATOSERVICIO");
		sql.SELECT("MANDATOSERVICIO.ESQUEMA AS ESQUEMASERVICIO");
		sql.SELECT("MANDATOSERVICIO.TIPOPAGO AS TIPOPAGOSERVICIO");
		sql.SELECT("MANDATOPRODUCTO.REFMANDATOSEPA AS REFERENCIAPRODUCTO");
		sql.SELECT("MANDATOPRODUCTO.ESQUEMA AS ESQUEMAPRODUCTO");
		sql.SELECT("MANDATOPRODUCTO.TIPOPAGO AS TIPOPAGOPRODUCTO");
		sql.SELECT("MANDATOPRODUCTO.IDMANDATO AS IDMANDATOPRODUCTO");
		sql.FROM("CEN_PERSONA PER");
		sql.INNER_JOIN("CEN_CUENTASBANCARIAS CUENTA ON CUENTA.IDPERSONA = PER.IDPERSONA");
		sql.INNER_JOIN("CEN_MANDATOS_CUENTASBANCARIAS MANDATOSERVICIO ON (PER.IDPERSONA = MANDATOSERVICIO.IDPERSONA AND MANDATOSERVICIO.TIPOMANDATO = '0' AND MANDATOSERVICIO.IDCUENTA = '"+datosBancariosSearchDTO.getIdCuenta()+"')");
		sql.INNER_JOIN("CEN_MANDATOS_CUENTASBANCARIAS MANDATOPRODUCTO ON (PER.IDPERSONA = MANDATOPRODUCTO.IDPERSONA AND MANDATOPRODUCTO.TIPOMANDATO = '1' AND MANDATOSERVICIO.IDCUENTA = '"+datosBancariosSearchDTO.getIdCuenta()+"')");

		sql.WHERE("PER.IDPERSONA = '"+datosBancariosSearchDTO.getIdPersona()+"'");
		sql.WHERE("CUENTA.FECHABAJA is null");
		sql.WHERE("CUENTA.IDINSTITUCION = '"+idInstitucion+"'");
		sql.WHERE("CUENTA.IDCUENTA = '"+datosBancariosSearchDTO.getIdCuenta()+"'");
		
		return sql.toString();
	}
	
	public String selectNewIdCuenta(String idPersona) {
		SQL sql = new SQL();
		sql.SELECT("MAX(CUENTA.IDCUENTA) + 1 AS IDCUENTA");
		sql.FROM("CEN_CUENTASBANCARIAS CUENTA");
		sql.WHERE("CUENTA.IDPERSONA = '"+idPersona+"'");
		
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
}


