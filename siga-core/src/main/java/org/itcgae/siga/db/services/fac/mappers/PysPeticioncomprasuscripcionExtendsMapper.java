package org.itcgae.siga.db.services.fac.mappers;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.CargaMasivaComprasBusquedaItem;
import org.itcgae.siga.DTO.fac.CargaMasivaComprasItem;
import org.itcgae.siga.DTO.fac.FichaCompraSuscripcionItem;
import org.itcgae.siga.DTO.fac.FiltroCargaMasivaCompras;
import org.itcgae.siga.DTO.fac.FiltroProductoItem;
import org.itcgae.siga.DTO.fac.FiltrosCompraProductosItem;
import org.itcgae.siga.DTO.fac.FiltrosSuscripcionesItem;
import org.itcgae.siga.DTO.fac.ListaCompraProductosItem;
import org.itcgae.siga.DTO.fac.ListaProductosCompraItem;
import org.itcgae.siga.DTO.fac.ListaProductosItem;
import org.itcgae.siga.DTO.fac.ListaServiciosItem;
import org.itcgae.siga.DTO.fac.ListaServiciosSuscripcionItem;
import org.itcgae.siga.DTO.fac.ListaSuscripcionesItem;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.entities.PysPeticioncomprasuscripcion;
import org.itcgae.siga.db.entities.PysSuscripcion;
import org.itcgae.siga.db.mappers.PysPeticioncomprasuscripcionMapper;
import org.itcgae.siga.db.services.fac.providers.PySTipoIvaSqlExtendsProvider;
import org.itcgae.siga.db.services.fac.providers.PySTiposProductosSqlExtendsProvider;
import org.itcgae.siga.db.services.fac.providers.PysPeticioncomprasuscripcionSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface PysPeticioncomprasuscripcionExtendsMapper extends PysPeticioncomprasuscripcionMapper{

	@SelectProvider(type = PysPeticioncomprasuscripcionSqlExtendsProvider.class, method = "getFichaCompraSuscripcion")
	@Results({ 
			// TARJETA CLIENTE
			@Result(column = "idInstitucion", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idPersona", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombre", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "apellidos", property = "apellidos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idtipoidentificacion", property = "idtipoidentificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nifcif", property = "nif", jdbcType = JdbcType.VARCHAR), 

			// TARJETA SOLICITUD
			@Result(column = "idpeticion", property = "nSolicitud", jdbcType = JdbcType.VARCHAR),
			@Result(column = "usuModificacion", property = "usuModificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechafechaPendiente", property = "fechafechaPendiente", jdbcType = JdbcType.DATE),
			@Result(column = "fechafechaAceptada", property = "fechafechaAceptada", jdbcType = JdbcType.DATE),
			@Result(column = "fechaDenegada", property = "fechaDenegada", jdbcType = JdbcType.DATE),
			@Result(column = "fechaSolicitadaAnulacion", property = "fechaSolicitadaAnulacion", jdbcType = JdbcType.DATE),
			@Result(column = "fechaAnulada", property = "fechaAnulada", jdbcType = JdbcType.DATE),
			//TARJETA FORMAS DE PAGO
			@Result(column = "idformaspagocomunes", property = "idFormasPagoComunes", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idFormaPagoSeleccionada", property = "idFormaPagoSeleccionada", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idCuentaBancSeleccionada", property = "cuentaBancSelecc", jdbcType = JdbcType.VARCHAR)})
	FichaCompraSuscripcionItem getFichaCompraSuscripcion(FichaCompraSuscripcionItem ficha, boolean esColegiado, Short idInstitucion);
	
	@SelectProvider(type = PysPeticioncomprasuscripcionSqlExtendsProvider.class, method = "getNuevaFichaCompraSuscripcion")
	@Results({ 
			// TARJETA CLIENTE
			@Result(column = "idInstitucion", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idPersona", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombre", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "apellidos", property = "apellidos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idtipoidentificacion", property = "idtipoidentificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nifcif", property = "nif", jdbcType = JdbcType.VARCHAR), 
			//TARJETA SOLICITUD
			@Result(column = "idpeticion", property = "nSolicitud", jdbcType = JdbcType.VARCHAR),
			@Result(column = "usuModificacion", property = "usuModificacion", jdbcType = JdbcType.VARCHAR),
			//TARJETA FORMAS DE PAGO
			@Result(column = "idformaspagocomunes", property = "idFormasPagoComunes", jdbcType = JdbcType.VARCHAR),
			@Result(column = "totalNeto", property = "totalNeto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "totalIVA", property = "totalIVA", jdbcType = JdbcType.VARCHAR),
			@Result(column = "impTotal", property = "impTotal", jdbcType = JdbcType.VARCHAR),
			@Result(column = "pendPago", property = "pendPago", jdbcType = JdbcType.VARCHAR)})
	FichaCompraSuscripcionItem getNuevaFichaCompraSuscripcion(FichaCompraSuscripcionItem ficha, boolean esColegiado);

	@SelectProvider(type = PysPeticioncomprasuscripcionSqlExtendsProvider.class, method = "selectMaxIdPeticion")
	@Results({
		@Result(column = "IDPETICION", property = "newId", jdbcType = JdbcType.VARCHAR),
	})
	NewIdDTO selectMaxIdPeticion(Short idInstitucion);

	@SelectProvider(type = PysPeticioncomprasuscripcionSqlExtendsProvider.class, method = "selectMaxNumOperacion")
	@Results({
		@Result(column = "NUM_OPERACION", property = "newId", jdbcType = JdbcType.VARCHAR),
	})
	NewIdDTO selectMaxNumOperacion(Short idInstitucion);
	
	@SelectProvider(type = PysPeticioncomprasuscripcionSqlExtendsProvider.class, method = "getListaCompras")
	@Results({
			@Result(column = "fechaSolicitud", property = "fechaSolicitud", jdbcType = JdbcType.DATE),
			@Result(column = "idPersona", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechaEfectiva", property = "fechaEfectiva", jdbcType = JdbcType.DATE),
			@Result(column = "fechaDenegada", property = "fechaDenegada", jdbcType = JdbcType.DATE),
			@Result(column = "fechaSolicitadaAnulacion", property = "fechaSolicitadaAnulacion", jdbcType = JdbcType.DATE),
			@Result(column = "fechaAnulada", property = "fechaAnulada", jdbcType = JdbcType.DATE),
			@Result(column = "nSolicitud", property = "nSolicitud", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nIdentificacion", property = "nIdentificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nColegiado", property = "nColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "apellidosNombre", property = "apellidosNombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "concepto", property = "concepto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idFormaPago", property = "idFormaPago", jdbcType = JdbcType.VARCHAR),
			@Result(column = "desFormaPago", property = "desFormaPago", jdbcType = JdbcType.VARCHAR),
			@Result(column = "importe", property = "importe", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idEstadoSolicitud", property = "idEstadoSolicitud", jdbcType = JdbcType.VARCHAR),
			@Result(column = "estadoFactura", property = "estadoFactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "facturas", property = "facturas", jdbcType = JdbcType.VARCHAR),
			@Result(column = "impTotal", property = "importe", jdbcType = JdbcType.VARCHAR),
			@Result(column = "solicitarBaja", property = "solicitarBaja", jdbcType = JdbcType.VARCHAR)})
	List<ListaCompraProductosItem> getListaCompras(FiltrosCompraProductosItem filtro, Short idInstitucion, String idioma, Integer tamMax);
	
	@SelectProvider(type = PysPeticioncomprasuscripcionSqlExtendsProvider.class, method = "comboEstadoFactura")
	@Results({ 
		@Result(column = "ID", property = "value", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
		}) 
	List<ComboItem> comboEstadoFactura(String idioma);
	
	@SelectProvider(type = PysPeticioncomprasuscripcionSqlExtendsProvider.class, method = "getListaProductosCompra")
	@Results({
		@Result(column = "IDPRODUCTO", property = "idproducto", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPOPRODUCTO", property = "idtipoproducto", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDPRODUCTOINSTITUCION", property = "idproductoinstitucion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VALOR", property = "precioUnitario", jdbcType = JdbcType.VARCHAR),
		@Result(column = "orden", property = "orden", jdbcType = JdbcType.VARCHAR),
		@Result(column = "cantidad", property = "cantidad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "total", property = "total", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IVA", property = "iva", jdbcType = JdbcType.VARCHAR),
		@Result(column = "obervaciones", property = "observaciones", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idPeticion", property = "idPeticion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOFACTURABLE", property = "noFacturable", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idtipoiva", property = "idtipoiva", jdbcType = JdbcType.VARCHAR),
		@Result(column = "valorIva", property = "valorIva", jdbcType = JdbcType.VARCHAR),
		@Result(column = "solicitarBaja", property = "solicitarBaja", jdbcType = JdbcType.VARCHAR)
		}) 
	List<ListaProductosCompraItem> getListaProductosCompra(Short idInstitucion, String idPeticion);
	
	@SelectProvider(type = PysPeticioncomprasuscripcionSqlExtendsProvider.class, method = "getListaSuscripciones")
	@Results({
			@Result(column = "fechaSolicitud", property = "fechaSolicitud", jdbcType = JdbcType.DATE),
			@Result(column = "idPersona", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechaEfectiva", property = "fechaEfectiva", jdbcType = JdbcType.DATE),
			@Result(column = "fechaDenegada", property = "fechaDenegada", jdbcType = JdbcType.DATE),
			@Result(column = "fechaSolicitadaAnulacion", property = "fechaSolicitadaAnulacion", jdbcType = JdbcType.DATE),
			@Result(column = "fechaAnulada", property = "fechaAnulada", jdbcType = JdbcType.DATE),
			@Result(column = "nSolicitud", property = "nSolicitud", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nIdentificacion", property = "nIdentificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nColegiado", property = "nColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "apellidosNombre", property = "apellidosNombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "concepto", property = "concepto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idFormaPago", property = "idFormaPago", jdbcType = JdbcType.VARCHAR),
			@Result(column = "desFormaPago", property = "desFormaPago", jdbcType = JdbcType.VARCHAR),
			@Result(column = "importe", property = "importe", jdbcType = JdbcType.VARCHAR),
			@Result(column = "precioPerio", property = "precioPerio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechaSuscripcion", property = "fechaSuscripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechaBaja", property = "fechaBaja", jdbcType = JdbcType.VARCHAR),
			@Result(column = "estadoFactura", property = "idEstadoFactura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "precioServicioDesc", property = "precioServicioDesc", jdbcType = JdbcType.VARCHAR),
			@Result(column = "facturas", property = "facturas", jdbcType = JdbcType.VARCHAR),
			@Result(column = "solicitarBaja", property = "solicitarBaja", jdbcType = JdbcType.VARCHAR),
			@Result(column = "automatico", property = "automatico", jdbcType = JdbcType.VARCHAR)
			
		})
	List<ListaSuscripcionesItem> getListaSuscripciones(FiltrosSuscripcionesItem filtro, Short idInstitucion, String idioma);

	@SelectProvider(type = PysPeticioncomprasuscripcionSqlExtendsProvider.class, method = "getListaServiciosSuscripcion")
	@Results({
		@Result(column = "IDSERVICIO", property = "idServicio", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPOSERVICIOs", property = "idTipoServicios", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDSERVICIOSINSTITUCION", property = "idServiciosInstitucion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "orden", property = "orden", jdbcType = JdbcType.VARCHAR),
		@Result(column = "cantidad", property = "cantidad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "total", property = "total", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IVA", property = "iva", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idPeticion", property = "idPeticion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOFACTURABLE", property = "noFacturable", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idtipoiva", property = "idtipoiva", jdbcType = JdbcType.VARCHAR),
		@Result(column = "valorIva", property = "valorIva", jdbcType = JdbcType.VARCHAR),
		@Result(column = "solicitarBaja", property = "solicitarBaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idPrecio", property = "idPrecioServicio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "precio", property = "precioServicioValor", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idPeriodicidad", property = "idPeriodicidad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "periodicidadValor", property = "periodicidadValor", jdbcType = JdbcType.VARCHAR),
		@Result(column = "periodicidadDesc", property = "periodicidadDesc", jdbcType = JdbcType.VARCHAR),
		@Result(column = "automatico", property = "automatico", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechaBaja", property = "fechaBaja", jdbcType = JdbcType.DATE),
		@Result(column = "fechaAlta", property = "fechaAlta", jdbcType = JdbcType.DATE)
		}) 
	List<ListaServiciosSuscripcionItem> getListaServiciosSuscripcion(Short idInstitucion, String nSolicitud, String idioma, Date aFechaDe);
	
	@SelectProvider(type = PysPeticioncomprasuscripcionSqlExtendsProvider.class, method = "selectNuevoId")
	@Results({
		@Result(column = "ID", property = "idMax", jdbcType = JdbcType.VARCHAR),
	})
	MaxIdDto selectNuevoId(Short idInstitucion);
	
	@SelectProvider(type = PysPeticioncomprasuscripcionSqlExtendsProvider.class, method = "listadoCargaMasivaCompras")
	@Results({
		@Result(column = "IDCARGAMASIVA", property = "idCargaMasiva", jdbcType = JdbcType.NUMERIC),
		@Result(column = "TIPOCARGA", property = "tipoCarga", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "FECHACARGA", property = "fechaCarga", jdbcType = JdbcType.DATE),
		@Result(column = "IDFICHERO", property = "idFichero", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDFICHEROLOG", property = "idFicheroLog", jdbcType = JdbcType.NUMERIC),
		@Result(column = "FECHAMODIFICACION", property = "fechaModificacion", jdbcType = JdbcType.DATE),
		@Result(column = "NUMREGISTROS", property = "numRegistros", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NOMBREFICHERO", property = "nombreFichero", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMREGISTROSERRONEOS", property = "numRegistrosErroneos", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCION", property = "usuario", jdbcType = JdbcType.VARCHAR)
	})
	List<CargaMasivaComprasItem> listadoCargaMasivaCompras(FiltroCargaMasivaCompras cargaMasivaItem, Short idInstitucion);
	
	 @Select({
	        "select",
	        "NVL(max(IDPETICION) +1, 1)",
	        "from PYS_PETICIONCOMPRASUSCRIPCION",
	        "where idinstitucion= #{idinstitucion,jdbcType=DECIMAL}"
	    })
	    int getNewIdPet(PysPeticioncomprasuscripcion idInstitucion);
}
