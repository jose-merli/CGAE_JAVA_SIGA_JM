package org.itcgae.siga.db.services.fcs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.CartasFacturacionPagosItem;
import org.itcgae.siga.DTOs.scs.FacturacionItem;
import org.itcgae.siga.DTOs.scs.PagosjgItem;
import org.itcgae.siga.db.entities.FcsFactEstadosfacturacionKey;
import org.itcgae.siga.db.entities.FcsFacturacionjg;
import org.itcgae.siga.db.mappers.FcsFacturacionjgMapper;
import org.itcgae.siga.db.services.fcs.providers.FcsFacturacionJGSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface FcsFacturacionJGExtendsMapper extends FcsFacturacionjgMapper {

	@SelectProvider(type = FcsFacturacionJGSqlExtendsProvider.class, method = "buscarFacturaciones")
	@Results({ 
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ABREVIATURA", property = "abreviatura", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDFACTURACION", property = "idFacturacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHADESDE", property = "fechaDesde", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAHASTA", property = "fechaHasta", jdbcType = JdbcType.DATE),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "REGULARIZACION", property = "regularizacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESESTADO", property = "desEstado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDESTADO", property = "idEstado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAESTADO", property = "fechaEstado", jdbcType = JdbcType.DATE),
		@Result(column = "IMPORTETOTAL", property = "importeTotal", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTEPAGADO", property = "importePagado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPARTIDAPRESUPUESTARIA", property = "idPartidaPresupuestaria", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTEPENDIENTE", property = "importePendiente", jdbcType = JdbcType.VARCHAR)
	})
	List<FacturacionItem> buscarFacturaciones(FacturacionItem facturacionItem, String idInstitucion);
	
	@SelectProvider(type = FcsFacturacionJGSqlExtendsProvider.class, method = "datosFacturacion")
	@Results({ 
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDFACTURACION", property = "idFacturacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHADESDE", property = "fechaDesde", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAHASTA", property = "fechaHasta", jdbcType = JdbcType.DATE),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "REGULARIZACION", property = "regularizacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PREVISION", property = "prevision", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VISIBLE", property = "visible", jdbcType = JdbcType.VARCHAR)
	})
	List<FacturacionItem> datosFacturacion(String idFacturacion, String idInstitucion);
	
	@SelectProvider(type = FcsFacturacionJGSqlExtendsProvider.class, method = "historicoFacturacion")
	@Results({ 
		@Result(column = "idestadofacturacion", property = "idEstado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "descripcion", property = "desEstado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "observaciones", property = "observaciones", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechaestado", property = "fechaEstado", jdbcType = JdbcType.DATE)
	})
	List<FacturacionItem> historicoFacturacion(String idFacturacion, String idLenguaje, String idInstitucion);
	
	@SelectProvider(type = FcsFacturacionJGSqlExtendsProvider.class, method = "numApuntes")
	@Results({ 
		@Result(column = "NUMAPUNTES", property = "valor", jdbcType = JdbcType.VARCHAR),
	})
	String numApuntes(String idFacturacion, String idInstitucion);
	
	@SelectProvider(type = FcsFacturacionJGSqlExtendsProvider.class, method = "getIdFacturacion")
	@Results({ @Result(column = "IDFACTURACION", property = "newId", jdbcType = JdbcType.VARCHAR)
	})
	NewIdDTO getIdFacturacion(Short idInstitucion);
	
	@SelectProvider(type = FcsFacturacionJGSqlExtendsProvider.class, method = "getIdOrdenEstado")
	@Results({ @Result(column = "IDORDENESTADO", property = "newId", jdbcType = JdbcType.VARCHAR)
	})
	NewIdDTO getIdOrdenEstado(Short idInstitucion, String idFacturacion);
	

	@SelectProvider(type = FcsFacturacionJGSqlExtendsProvider.class, method = "conceptosFacturacion")
	@Results({ 
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDFACTURACION", property = "idFacturacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGRUPO", property = "idGrupo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCONCEPTO", property = "idConcepto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCGRUPO", property = "descGrupo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCCONCEPTO", property = "descConcepto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTETOTAL", property = "importeTotal", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTEPENDIENTE", property = "importePendiente", jdbcType = JdbcType.VARCHAR)
	})
	List<FacturacionItem> conceptosFacturacion(String idFacturacion, String idInstitucion, String idLenguaje);

	@SelectProvider(type = FcsFacturacionJGSqlExtendsProvider.class, method = "getComboFactColegio")
	@Results({ 
		@Result(column = "LABEL", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDFACTURACION", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getComboFactColegio(Short idInstitucion);	
	
	@SelectProvider(type = FcsFacturacionJGSqlExtendsProvider.class, method = "buscarCartasfacturacion")
	@Results({ 
		@Result(column = "NOMBRECOL", property = "nombreCol", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDFACTURACION", property = "idFacturacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREFAC", property = "nombreFac", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHADESDE", property = "fechaDesde", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAHASTA", property = "fechaHasta", jdbcType = JdbcType.DATE),
		@Result(column = "NCOLEGIADO", property = "ncolegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTETOTAL", property = "importeTotal", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTEGUARDIA", property = "importeGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTEOFICIO", property = "importeOficio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTEEJG", property = "importeEjg", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTESOJ", property = "importeSoj", jdbcType = JdbcType.VARCHAR)

	})
	List<CartasFacturacionPagosItem> buscarCartasfacturacion(CartasFacturacionPagosItem cartasFacturacionPagosItem, Short idInstitucion, Integer tamMax);
	
	@SelectProvider(type = FcsFacturacionJGSqlExtendsProvider.class, method = "buscarCartaspago")
	@Results({ 
		@Result(column = "IDPERSONASJCS", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRECOL", property = "nombreCol", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREPAGO", property = "nombrePago", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPAGOS", property = "idPago", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREDEST", property = "nombreDest", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TOTALIMPORTESJCS", property = "totalImportesjcs", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTETOTALRETENCIONES", property = "importeTotalRetenciones", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NCOLEGIADO", property = "ncolegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTETOTAL", property = "importeTotal", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTETOTALMOVIMIENTOS", property = "importeTotalMovimientos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TOTALIMPORTEBRUTO", property = "totalImporteBruto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TOTALIMPORTEIRPF", property = "totalImporteIrpf", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FORMADEPAGO", property = "formaDePago", jdbcType = JdbcType.VARCHAR)

	})
	List<CartasFacturacionPagosItem> buscarCartaspago(CartasFacturacionPagosItem cartasFacturacionPagosItem, Short idInstitucion, String idLenguaje, Integer tamMax);

	@SelectProvider(type = FcsFacturacionJGSqlExtendsProvider.class, method = "datosPagos")
	@Results({ 
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPAGOSJG", property = "idPagosjg", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDFACTURACION", property = "idFacturacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTEEJG", property = "importeEJG", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTEGUARDIA", property = "importeGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTEOFICIO", property = "importeOficio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTESOJ", property = "importesOJ", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTEREPARTIR", property = "importeRepartir", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTEPAGADO", property = "importePagado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAESTADO", property = "fechaEstado", jdbcType = JdbcType.DATE),
		@Result(column = "DESESTADO", property = "desEstado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCONCEPTO", property = "desConcepto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PORCENTAJE", property = "porcentaje", jdbcType = JdbcType.VARCHAR)
		
	})
	List<PagosjgItem> datosPagos(String idFacturacion, String idInstitucion, String idLenguaje);
	
	@SelectProvider(type = FcsFacturacionJGSqlExtendsProvider.class, method = "facturacionesPorEstadoEjecucion")
	@Results({ 
		@Result(column = "IDFACTURACION", property = "idfacturacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHADESDE", property = "fechadesde", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAHASTA", property = "fechahasta", jdbcType = JdbcType.DATE),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTETOTAL", property = "importetotal", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTEOFICIO", property = "importeoficio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTEGUARDIA", property = "importeguardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTESOJ", property = "importesoj", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTEEJG", property = "importeejg", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PREVISION", property = "prevision", jdbcType = JdbcType.VARCHAR),
		@Result(column = "REGULARIZACION", property = "regularizacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.DATE),
		@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDFACTURACION_REGULARIZA", property = "idfacturacionRegulariza", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREFISICO", property = "nombrefisico", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDECOMCOLA", property = "idecomcola", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VISIBLE", property = "visible", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPARTIDAPRESUPUESTARIA", property = "idpartidapresupuestaria", jdbcType = JdbcType.VARCHAR)
	})
	List<FcsFacturacionjg> facturacionesPorEstadoEjecucion(String idInstitucion);
	
	@SelectProvider(type = FcsFacturacionJGSqlExtendsProvider.class, method = "facturacionesPorEstadoProgramadas")
	@Results({ 
		@Result(column = "IDFACTURACION", property = "idfacturacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHADESDE", property = "fechadesde", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAHASTA", property = "fechahasta", jdbcType = JdbcType.DATE),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTETOTAL", property = "importetotal", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTEOFICIO", property = "importeoficio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTEGUARDIA", property = "importeguardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTESOJ", property = "importesoj", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPORTEEJG", property = "importeejg", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PREVISION", property = "prevision", jdbcType = JdbcType.VARCHAR),
		@Result(column = "REGULARIZACION", property = "regularizacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.DATE),
		@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDFACTURACION_REGULARIZA", property = "idfacturacionRegulariza", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREFISICO", property = "nombrefisico", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDECOMCOLA", property = "idecomcola", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VISIBLE", property = "visible", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPARTIDAPRESUPUESTARIA", property = "idpartidapresupuestaria", jdbcType = JdbcType.VARCHAR)
	})
	List<FcsFacturacionjg> facturacionesPorEstadoProgramadas(String idInstitucion);
	
	@SelectProvider(type = FcsFacturacionJGSqlExtendsProvider.class, method = "ultimoEstadoFacturacion")
	@Results({ 
		@Result(column = "idestadofacturacion", property = "idestadofacturacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idinstitucion", property = "idinstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idfacturacion", property = "idfacturacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechaestado", property = "fechaestado", jdbcType = JdbcType.DATE)
	})
	FcsFactEstadosfacturacionKey ultimoEstadoFacturacion(String idFacturacion, String idInstitucion);
	
	@SelectProvider(type = FcsFacturacionJGSqlExtendsProvider.class, method = "getParametroInstitucion")
	@Results({ 
		@Result(column = "valor", property = "valor", jdbcType = JdbcType.VARCHAR),
	})
	StringDTO getParametroInstitucion(String idInstitucion, String parametro);

}