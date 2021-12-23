package org.itcgae.siga.db.services.fac.providers;


import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.FacRegistroFichConta;
import org.itcgae.siga.db.mappers.FacRegistrofichcontaSqlProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class FacRegistroFichContaExtendsProvider extends FacRegistrofichcontaSqlProvider{

	private Logger LOGGER = Logger.getLogger(this.getClass());
	
	public String search(FacRegistroFichConta facRegistroFichConta, Short idInstitucion, Integer tamMaximo ) {

		SQL sql = new SQL();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		sql.SELECT("IDCONTABILIDAD");
		sql.SELECT("FECHACREACION");
		sql.SELECT("FECHADESDE");
		sql.SELECT("FECHAHASTA");
		sql.SELECT("NUMEROASIENTOS");
		sql.SELECT("NOMBREFICHERO");
		sql.SELECT("ESTADO");
		
		sql.FROM("FAC_REGISTROFICHCONTA");
		
		if(facRegistroFichConta.getIdContabilidadDesde() != 0 && String.valueOf(facRegistroFichConta.getIdContabilidadDesde()) != null && !String.valueOf(facRegistroFichConta.getIdContabilidadDesde()).isEmpty()) {
			sql.WHERE("IDCONTABILIDAD >= " + facRegistroFichConta.getIdContabilidadDesde());
		}
	
		if(facRegistroFichConta.getIdContabilidadHasta() != 0 && String.valueOf(facRegistroFichConta.getIdContabilidadHasta()) != null && !String.valueOf(facRegistroFichConta.getIdContabilidadHasta()).isEmpty()) {
			sql.WHERE("IDCONTABILIDAD <= " + facRegistroFichConta.getIdContabilidadHasta());
		}

		if(facRegistroFichConta.getFechaCreacionDesde() != null) {
			String fechaCreacionDesde = dateFormat.format(facRegistroFichConta.getFechaCreacionDesde());
			sql.WHERE("TRUNC(FECHACREACION) >= TO_DATE('" + fechaCreacionDesde + "', 'DD/MM/RRRR')");
		}
		
		if(facRegistroFichConta.getFechaCreacionHasta() != null) {
			String fechaCreacionHasta = dateFormat.format(facRegistroFichConta.getFechaCreacionHasta());
			sql.WHERE("TRUNC(FECHACREACION) <= TO_DATE('" + fechaCreacionHasta + "', 'DD/MM/RRRR')");
		}
		
		if(facRegistroFichConta.getNumAsientosDesde() != 0 && String.valueOf(facRegistroFichConta.getNumAsientosDesde()) != null && !String.valueOf(facRegistroFichConta.getNumAsientosDesde()).isEmpty()) {
			sql.WHERE("NUMEROASIENTOS >= " + facRegistroFichConta.getNumAsientosDesde());
		}
	
		if(facRegistroFichConta.getNumAsientosHasta() != 0 && String.valueOf(facRegistroFichConta.getNumAsientosHasta()) != null && !String.valueOf(facRegistroFichConta.getNumAsientosHasta()).isEmpty()) {
			sql.WHERE("NUMEROASIENTOS <= " + facRegistroFichConta.getNumAsientosHasta());
		}
		
		if(facRegistroFichConta.getFechaExportacionDesde() != null) {
			String fechaExportacionDesde = dateFormat.format(facRegistroFichConta.getFechaExportacionDesde());
			sql.WHERE("TRUNC(FECHADESDE) >= TO_DATE('" + fechaExportacionDesde + "', 'DD/MM/RRRR')");
		}
		
		if(facRegistroFichConta.getFechaExportacionHasta() != null) {
			String fechaExportacionHasta = dateFormat.format(facRegistroFichConta.getFechaExportacionHasta());
			sql.WHERE("TRUNC(FECHAHASTA) <= TO_DATE('" + fechaExportacionHasta + "', 'DD/MM/RRRR')");
		}
		
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		sql.WHERE("ROWNUM <= " + tamMaximo);
		
		sql.ORDER_BY("IDCONTABILIDAD DESC");
		
		LOGGER.info(sql.toString());
		return sql.toString();
	}
	
	public String getMaxIdFacRegistroFichConta(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDCONTABILIDAD)+1 as IDCONTABILIDAD");
		sql.FROM("FAC_REGISTROFICHCONTA");
		sql.WHERE("IDINSTITUCION = "+ idInstitucion);
		LOGGER.info(sql.toString());
		return sql.toString();
	}
}
