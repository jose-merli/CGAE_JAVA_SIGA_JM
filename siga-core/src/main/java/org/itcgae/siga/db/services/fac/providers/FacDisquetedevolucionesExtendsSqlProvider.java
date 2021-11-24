package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.FicherosDevolucionesItem;
import org.itcgae.siga.db.mappers.FacDisquetedevolucionesSqlProvider;

import java.text.SimpleDateFormat;


public class FacDisquetedevolucionesExtendsSqlProvider extends FacDisquetedevolucionesSqlProvider {

	public String getFicherosDevoluciones(FicherosDevolucionesItem item, String idInstitucion) {
		SQL principal = new SQL();
		SQL numRecibos = new SQL();
		SQL sql = new SQL();

	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fecha;

		//sumatorio numero de recibos
		numRecibos.SELECT("COUNT (1)");
		numRecibos.FROM("fac_abonoincluidoendisquete fi");
		numRecibos.WHERE("fi.idinstitucion = c.idinstitucion AND fi.iddisqueteabono = c.iddisquetedevoluciones");

		//query principal
		principal.SELECT("c.idinstitucion,c.iddisquetedevoluciones, c.FECHAGENERACION, b.cod_banco, b.comisiondescripcion || ' (...' || SUBSTR(b.iban, -4) || ')' CUENTA_ENTIDAD, c.nombrefichero,"
				+ "c.fechamodificacion, id.importe, ("+numRecibos.toString()+") AS numfacturas");

		principal.FROM("fac_disquetedevoluciones c");
		principal.INNER_JOIN("fac_lineadevoludisqbanco lin ON (lin.idinstitucion = c.idinstitucion AND lin.iddisquetedevoluciones = c.iddisquetedevoluciones)");
		principal.INNER_JOIN("fac_facturaincluidaendisquete id ON (lin.idinstitucion = id.idinstitucion AND lin.iddisquetecargos = id.iddisquetecargos AND lin.idfacturaincluidaendisquete = id.idfacturaincluidaendisquete)");
		principal.INNER_JOIN("fac_bancoinstitucion b ON (c.idinstitucion=b.idinstitucion AND c.bancos_codigo=b.bancos_codigo)");

		//principal.WHERE("c.idinstitucion="+idInstitucion);

		//CUENTA BANCARIA
		if(item.getBancosCodigo()!=null) {
			principal.WHERE("b.bancos_codigo="+item.getBancosCodigo());
		}

		//fecha creacion desde
		if(item.getFechaCreacionDesde()!=null) {
			fecha = dateFormat.format(item.getFechaCreacionDesde());
			principal.WHERE("c.fechageneracion >= TO_DATE('"+fecha+"', 'DD/MM/YYYY')");
		}

		//fecha creacion hasta
		if(item.getFechaCreacionHasta()!=null) {
			fecha = dateFormat.format(item.getFechaCreacionHasta());
			principal.WHERE("c.fechageneracion <= TO_DATE('"+fecha+"', 'DD/MM/YYYY')");
		}

		//origen  -PENDIENTE POR PREGUNTAR A CLIENTE

		principal.ORDER_BY("c.iddisquetedevoluciones DESC");

		//query completa
		sql.SELECT("*");
		sql.FROM("("+principal.toString()+")");
		sql.WHERE("ROWNUM < 201");

		//importe total desde
		if(item.getImporteTotalDesde()!=null && !item.getImporteTotalDesde().isEmpty()) {
			sql.WHERE("importe>=to_number("+item.getImporteTotalDesde()+",'99999999999999999.99')");
		}

		//importe total hasta
		if(item.getImporteTotalHasta()!=null && !item.getImporteTotalHasta().isEmpty()) {
			sql.WHERE("importe<=to_number("+item.getImporteTotalHasta()+",'99999999999999999.99')");
		}

		//numrecibos desde
		if(item.getNumRecibosDesde()!=null && !item.getNumRecibosDesde().isEmpty()) {
			sql.WHERE("numfacturas >= "+item.getNumRecibosDesde());
		}

		//numrecibos hasta
		if(item.getNumRecibosHasta()!=null && !item.getNumRecibosHasta().isEmpty()) {
			sql.WHERE("numfacturas <= "+item.getNumRecibosHasta());
		}

		return sql.toString();
	}
}