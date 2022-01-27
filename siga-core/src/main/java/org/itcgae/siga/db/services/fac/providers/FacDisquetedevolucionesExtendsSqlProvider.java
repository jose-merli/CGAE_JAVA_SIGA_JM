package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.fac.FicherosDevolucionesItem;
import org.itcgae.siga.db.mappers.FacDisquetedevolucionesSqlProvider;

import java.text.SimpleDateFormat;


public class FacDisquetedevolucionesExtendsSqlProvider extends FacDisquetedevolucionesSqlProvider {

	public String getFicherosDevoluciones(FicherosDevolucionesItem item, String idInstitucion) {
		SQL principal = new SQL();
		SQL sql = new SQL();

	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fecha;

		//query principal
		principal.SELECT("fd.IDINSTITUCION, dd.IDDISQUETEDEVOLUCIONES, dd.FECHAGENERACION, dd.NOMBREFICHERO, b.DESCRIPCION as CUENTA_ENTIDAD, "
				+ "COUNT(*) NUMEROFACTURAS, SUM(fd.IMPORTE) IMPORTETOTAL, dd.BANCOS_CODIGO, fs.SUFIJO");
		principal.FROM("FAC_FACTURAINCLUIDAENDISQUETE fd");
		principal.INNER_JOIN("FAC_LINEADEVOLUDISQBANCO lin ON (fd.IDINSTITUCION = lin.IDINSTITUCION "
				+ "AND lin.IDFACTURAINCLUIDAENDISQUETE = fd.IDFACTURAINCLUIDAENDISQUETE AND lin.IDDISQUETECARGOS = fd.IDDISQUETECARGOS)");
		principal.INNER_JOIN("FAC_DISQUETEDEVOLUCIONES dd  ON (fd.IDINSTITUCION = dd.IDINSTITUCION AND lin.IDDISQUETEDEVOLUCIONES = dd.IDDISQUETEDEVOLUCIONES)");
		principal.INNER_JOIN("FAC_BANCOINSTITUCION b ON (fd.IDINSTITUCION = b.IDINSTITUCION AND dd.BANCOS_CODIGO = b.BANCOS_CODIGO)");
		principal.LEFT_OUTER_JOIN("FAC_SUFIJO fs ON (fd.IDINSTITUCION = fs.IDINSTITUCION AND b.IDSUFIJOSJCS = fs.IDSUFIJO)");
		principal.WHERE("fd.idinstitucion="+idInstitucion);
		principal.GROUP_BY("fd.IDINSTITUCION, dd.IDDISQUETEDEVOLUCIONES, dd.FECHAGENERACION, dd.NOMBREFICHERO, b.COMISIONDESCRIPCION, b.DESCRIPCION, dd.BANCOS_CODIGO, fs.SUFIJO");
		principal.ORDER_BY("dd.IDDISQUETEDEVOLUCIONES DESC");

		//query completa
		sql.SELECT("*");
		sql.FROM("("+principal.toString()+")");
		sql.WHERE("ROWNUM < 201");

		//CUENTA BANCARIA
		if(item.getBancosCodigo()!=null) {
			sql.WHERE("BANCOS_CODIGO LIKE '%"+item.getBancosCodigo()+"%'");
		}

		//fecha creacion desde
		if(item.getFechaCreacionDesde()!=null) {
			fecha = dateFormat.format(item.getFechaCreacionDesde());
			sql.WHERE("FECHAGENERACION >= TO_DATE('"+fecha+"', 'DD/MM/YYYY')");
		}

		//fecha creacion hasta
		if(item.getFechaCreacionHasta()!=null) {
			fecha = dateFormat.format(item.getFechaCreacionHasta());
			sql.WHERE("FECHAGENERACION <= TO_DATE('"+fecha+"', 'DD/MM/YYYY')");
		}

		//importe total desde
		if(item.getImporteTotalDesde()!=null && !item.getImporteTotalDesde().isEmpty()) {
			sql.WHERE("IMPORTETOTAL >= TO_NUMBER("+item.getImporteTotalDesde()+",'99999999999999999.99')");
		}

		//importe total hasta
		if(item.getImporteTotalHasta()!=null && !item.getImporteTotalHasta().isEmpty()) {
			sql.WHERE("IMPORTETOTAL <= TO_NUMBER("+item.getImporteTotalHasta()+",'99999999999999999.99')");
		}

		//numrecibos desde
		if(item.getNumRecibosDesde()!=null && !item.getNumRecibosDesde().isEmpty()) {
			sql.WHERE("NUMEROFACTURAS >= "+item.getNumRecibosDesde());
		}

		//numrecibos hasta
		if(item.getNumRecibosHasta()!=null && !item.getNumRecibosHasta().isEmpty()) {
			sql.WHERE("NUMEROFACTURAS <= "+item.getNumRecibosHasta());
		}

		return sql.toString();
	}

	public String getFacturasIncluidas(String idFichero, String idInstitucion, String idIdioma) {

		SQL sql = new SQL();

		sql.SELECT("gr.DESCRIPCION ESTADO, F_SIGA_GETRECURSO(pf.DESCRIPCION,1) FORMAPAGO, COUNT(*) NUMEROFACTURAS, "
				+"SUM(ff.IMPTOTAL) IMPORTETOTAL, SUM(ff.IMPTOTALPORPAGAR) PENDIENTETOTAL");
		sql.FROM("FAC_FACTURA ff");
		sql.INNER_JOIN("FAC_LINEADEVOLUDISQBANCO ff3 ON (ff.IDINSTITUCION = ff3.IDINSTITUCION)");
		sql.INNER_JOIN("FAC_FACTURAINCLUIDAENDISQUETE ff2 ON (ff.IDINSTITUCION = ff2.IDINSTITUCION AND "
				+"ff.IDFACTURA = ff2.IDFACTURA AND ff3.IDFACTURAINCLUIDAENDISQUETE = ff2.IDFACTURAINCLUIDAENDISQUETE "
				+"AND ff3.IDDISQUETECARGOS = ff2.IDDISQUETECARGOS)");
		sql.LEFT_OUTER_JOIN("FAC_ESTADOFACTURA fe ON (ff.ESTADO = fe.IDESTADO)");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS gr ON (gr.IDLENGUAJE = "+idIdioma+" AND fe.DESCRIPCION = gr.IDRECURSO)");
		sql.LEFT_OUTER_JOIN("PYS_FORMAPAGO pf ON (ff.IDFORMAPAGO = pf.IDFORMAPAGO)");
		sql.WHERE("ff.IDINSTITUCION = "+idInstitucion);
		sql.WHERE("ff3.IDDISQUETEDEVOLUCIONES ="+idFichero);
		sql.GROUP_BY("gr.DESCRIPCION, pf.DESCRIPCION");

		return sql.toString();
	}

	public String getNextIdDisqueteDevoluciones(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("(NVL(MAX(dd.IDDISQUETEDEVOLUCIONES),0) + 1)");
		sql.FROM("FAC_DISQUETEDEVOLUCIONES dd");
		sql.WHERE("dd.idinstitucion = " + idInstitucion);

		return sql.toString();
	}

}