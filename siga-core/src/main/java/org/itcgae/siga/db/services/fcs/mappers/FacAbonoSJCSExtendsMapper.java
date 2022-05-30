package org.itcgae.siga.db.services.fcs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.FacAbonoItem;
import org.itcgae.siga.db.mappers.FacAbonoMapper;
import org.itcgae.siga.db.services.fcs.providers.FacAbonoSqlExtendsProvider;
import org.itcgae.siga.db.services.fcs.providers.FcsHitoGeneralSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface FacAbonoSJCSExtendsMapper extends FacAbonoMapper{
	
	@SelectProvider(type = FacAbonoSqlExtendsProvider.class, method = "buscarAbonosSJCS")
	@Results({
		@Result(column = "NUMEROABONO", property = "numeroAbono", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHA", property = "fechaEmision", jdbcType = JdbcType.DATE),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPTOTALABONADO", property = "importeTotalAbonado", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IMPPENDIENTEPORABONAR", property = "importePendientePorAbonar", jdbcType = JdbcType.NUMERIC),
		@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.NUMERIC),
		@Result(column = "ESTADONOMBRE", property = "estadoNombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPTOTAL", property = "importeTotal", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPTOTALABONADOEFECTIVO", property = "importeTotalAbonadoEfectivo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IMPTOTALABONADOPORBANCO", property = "importeTotalAbonadoBanco", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ncolident", property = "ncolident", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SOCIEDAD", property = "esSociedad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREPAGO", property = "nombrePago", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombreCompleto", property = "nombreCompleto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREGENERAL", property = "nombreGeneral", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOSGENERAL", property = "apellidosGeneral", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDABONO", property = "idAbono", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDFACTURA", property = "idFactura", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDFACTURACION", property = "idFacturacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREFACTURACION", property = "nombreFacturacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPAGOSJG", property = "idPagosjg", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IRPF", property = "importeIRPF", jdbcType = JdbcType.VARCHAR),
	})
	List<FacAbonoItem> buscarAbonosSJCS(FacAbonoItem facAbonoItem, String idInstitucion,String idLenguaje);	
	
	@SelectProvider(type = FacAbonoSqlExtendsProvider.class, method = "comboEstadosAbono")
	@Results({ 
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDESTADO", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> comboEstadosAbono(String idLenguaje);	
	
	@SelectProvider(type = FacAbonoSqlExtendsProvider.class, method = "comboGrupoFacturacion")
	@Results({ 
		@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGRUPOFACTURACION", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> comboGrupoFacturacion(String idLenguaje, String idInstitucion);	
	
	@SelectProvider(type = FacAbonoSqlExtendsProvider.class, method = "comboPago")
	@Results({ 
		@Result(column = "ABREVIATURA", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPAGOSJG", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> comboPago(String idInstitucion);
	
	@SelectProvider(type = FacAbonoSqlExtendsProvider.class, method = "facturacionByGroup")
	@Results()
	String facturacionByGroup (String idGrupo, String idInstitucion);
	
}
