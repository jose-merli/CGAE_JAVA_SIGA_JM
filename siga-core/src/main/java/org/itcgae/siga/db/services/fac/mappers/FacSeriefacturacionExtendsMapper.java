package org.itcgae.siga.db.services.fac.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.SerieFacturacionItem;
import org.itcgae.siga.DTO.fac.UsosSufijosItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.entities.FacSeriefacturacion;
import org.itcgae.siga.db.entities.FacSeriefacturacionBanco;
import org.itcgae.siga.db.mappers.FacSeriefacturacionMapper;
import org.itcgae.siga.db.mappers.FacSeriefacturacionSqlProvider;
import org.itcgae.siga.db.services.cen.providers.CenSolimodidireccionesSqlExtendsProvider;
import org.itcgae.siga.db.services.fac.providers.FacSeriefacturacionExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface FacSeriefacturacionExtendsMapper extends FacSeriefacturacionMapper {

	@SelectProvider(type = FacSeriefacturacionExtendsSqlProvider.class, method = "getSeriesFacturacion")
	@Results({
		@Result(column = "idseriefacturacion", property = "idSerieFacturacion", jdbcType = JdbcType.INTEGER),
		@Result(column = "observaciones", property = "observaciones", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechabaja", property = "fechaBaja", jdbcType = JdbcType.DATE),
		@Result(column = "nombreabreviado", property = "abreviatura", jdbcType = JdbcType.VARCHAR),
		@Result(column = "descripcion", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "bancos_codigo", property = "idCuentaBancaria", jdbcType = JdbcType.VARCHAR),
		@Result(column = "iban", property = "cuentaBancaria", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idsufijo", property = "idSufijo", jdbcType = JdbcType.INTEGER),
		@Result(column = "sufijo", property = "sufijo", jdbcType = JdbcType.VARCHAR),
//		@Result(column = "idformapago", property = "idFormaPago", jdbcType = JdbcType.VARCHAR),
		@Result(column = "formapago", property = "formaPago", jdbcType = JdbcType.BOOLEAN),
		@Result(column = "generarpdf", property = "generarPDF", jdbcType = JdbcType.BOOLEAN),
		@Result(column = "idmodelofactura", property = "idModeloFactura", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idmodelorectificativa", property = "idModeloRectificativa", jdbcType = JdbcType.VARCHAR),
		@Result(column = "enviofacturas", property = "envioFacturas", jdbcType = JdbcType.BOOLEAN),
		@Result(column = "idtipoplantillamail", property = "idPlantillaMail", jdbcType = JdbcType.VARCHAR),
		@Result(column = "traspasofacturas", property = "traspasoFacturas", jdbcType = JdbcType.BOOLEAN),
		@Result(column = "traspaso_plantilla", property = "traspasoPlantilla", jdbcType = JdbcType.VARCHAR),
		@Result(column = "traspaso_codauditoria_def", property = "traspasoCodAuditoriaDef", jdbcType = JdbcType.VARCHAR),
		@Result(column = "confdeudor", property = "confDeudor", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ctaclientes", property = "ctaClientes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "confingresos", property = "confIngresos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ctaingresos", property = "ctaIngresos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idSerieFacturacionPrevia", property = "idSerieFacturacionPrevia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "serieGenerica", property = "serieGenerica", jdbcType = JdbcType.BOOLEAN),
		@Result(column = "idcontador", property = "idContadorFacturas", jdbcType = JdbcType.BOOLEAN),
		@Result(column = "idcontador_abonos", property = "idContadorFacturasRectificativas", jdbcType = JdbcType.BOOLEAN)
	})
	List<SerieFacturacionItem> getSeriesFacturacion(SerieFacturacionItem serieFacturacionItem, Short idInstitucion, String idioma);

	@SelectProvider(type = FacSeriefacturacionExtendsSqlProvider.class, method = "getNextIdSerieFacturacion")
	@Results({ @Result(column = "idseriefacturacion", property = "newId", jdbcType = JdbcType.VARCHAR) })
	NewIdDTO getNextIdSerieFacturacion(Short idInstitucion);

	@SelectProvider(type = FacSeriefacturacionExtendsSqlProvider.class, method = "getConsultaUsoSufijo")
	@Results({
		@Result(column = "idseriefacturacion", property = "idSerieFacturacion", jdbcType = JdbcType.INTEGER),
		@Result(column = "tipo", property = "tipo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "cod_banco", property = "codBanco", jdbcType = JdbcType.VARCHAR),
		@Result(column = "abreviatura", property = "abreviatura", jdbcType = JdbcType.VARCHAR),
		@Result(column = "descripcion", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idsufijo", property = "idSufijo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "sufijo", property = "sufijo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "num_pendientes", property = "numPendientes", jdbcType = JdbcType.VARCHAR),
	})
	List<UsosSufijosItem> getUsosSufijos(int idInstitucion, String codigoBanco);

	@SelectProvider(type = FacSeriefacturacionExtendsSqlProvider.class, method = "obtenerSeriesAdecuadas")
	@Results({
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDSERIEFACTURACION", property = "idseriefacturacion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPLANTILLA", property = "idplantilla", jdbcType = JdbcType.DECIMAL),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREABREVIADO", property = "nombreabreviado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "ENVIOFACTURAS", property = "enviofacturas", jdbcType = JdbcType.VARCHAR),
			@Result(column = "GENERARPDF", property = "generarpdf", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDCONTADOR", property = "idcontador", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CONFDEUDOR", property = "confdeudor", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CONFINGRESOS", property = "confingresos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CTAINGRESOS", property = "ctaingresos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CTACLIENTES", property = "ctaclientes", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TIPOSERIE", property = "tiposerie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPOPLANTILLAMAIL", property = "idtipoplantillamail", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDTIPOENVIOS", property = "idtipoenvios", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDSERIEFACTURACIONPREVIA", property = "idseriefacturacionprevia", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "ID_NOMBRE_DESCARGA_FAC", property = "idNombreDescargaFac", jdbcType = JdbcType.DECIMAL),
			@Result(column = "TRASPASO_PLANTILLA", property = "traspasoPlantilla", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TRASPASO_CODAUDITORIA_DEF", property = "traspasoCodauditoriaDef", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDCONTADOR_ABONOS", property = "idcontadorAbonos", jdbcType = JdbcType.VARCHAR)
	})
	List<FacSeriefacturacion> obtenerSeriesAdecuadas(String idInstitucion, int numeroTipos, String listadoProductos);

	@SelectProvider(type = FacSeriefacturacionExtendsSqlProvider.class, method = "obtenerSeriesAdecuadas2")
	@Results({
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDSERIEFACTURACION", property = "idseriefacturacion", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "IDPLANTILLA", property = "idplantilla", jdbcType = JdbcType.DECIMAL),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREABREVIADO", property = "nombreabreviado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
			@Result(column = "ENVIOFACTURAS", property = "enviofacturas", jdbcType = JdbcType.VARCHAR),
			@Result(column = "GENERARPDF", property = "generarpdf", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDCONTADOR", property = "idcontador", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CONFDEUDOR", property = "confdeudor", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CONFINGRESOS", property = "confingresos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CTAINGRESOS", property = "ctaingresos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CTACLIENTES", property = "ctaclientes", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TIPOSERIE", property = "tiposerie", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPOPLANTILLAMAIL", property = "idtipoplantillamail", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDTIPOENVIOS", property = "idtipoenvios", jdbcType = JdbcType.DECIMAL),
			@Result(column = "IDSERIEFACTURACIONPREVIA", property = "idseriefacturacionprevia", jdbcType = JdbcType.DECIMAL),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "ID_NOMBRE_DESCARGA_FAC", property = "idNombreDescargaFac", jdbcType = JdbcType.DECIMAL),
			@Result(column = "TRASPASO_PLANTILLA", property = "traspasoPlantilla", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TRASPASO_CODAUDITORIA_DEF", property = "traspasoCodauditoriaDef", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDCONTADOR_ABONOS", property = "idcontadorAbonos", jdbcType = JdbcType.VARCHAR)
	})
	List<FacSeriefacturacion> obtenerSeriesAdecuadas2(String idInstitucion, int numeroTipos, String listadoProductos, String idPersona);
}
