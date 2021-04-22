package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaItem;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaRequestDTO;
import org.itcgae.siga.DTOs.scs.ActuacionesJustificacionExpressItem;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.AsuntosDesignaItem;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableItem;
import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.JustificacionExpressItem;
import org.itcgae.siga.DTOs.scs.ListaContrarioJusticiableItem;
import org.itcgae.siga.DTOs.scs.ListaInteresadoJusticiableItem;
import org.itcgae.siga.DTOs.scs.ProcuradorItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.ScsDesignaMapper;
import org.itcgae.siga.db.services.scs.providers.ScsDesignacionesSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsDesignacionesExtendsMapper extends ScsDesignaMapper {

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "searchClaveDesignaciones")
	@Results({ @Result(column = "ANIO", property = "anio", jdbcType = JdbcType.DATE),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CLAVE", property = "clave", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ROL", property = "rol", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR) })
	List<AsuntosClaveJusticiableItem> searchClaveDesignaciones(AsuntosJusticiableItem asuntosJusticiableItem,
			Integer tamMaximo);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "busquedaDesignaciones")
	@Results({ @Result(column = "ANIO", property = "ano", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.NUMERIC),
			@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAESTADO", property = "fechaEstado", jdbcType = JdbcType.DATE),
			@Result(column = "FECHAENTRADA", property = "fechaEntradaInicio", jdbcType = JdbcType.DATE),
			@Result(column = "NOMBRE", property = "nombreTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NCOLEGIADO", property = "numColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAALTA", property = "fechaAlta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAFIN", property = "fechaFin", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NIG", property = "nig", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMPROCEDIMIENTO", property = "numProcedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREJUZGADO", property = "nombreJuzgado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PROCEDIMIENTO", property = "nombreProcedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREPERSONA", property = "nombreColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDO1PERSONA", property = "apellido1Colegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDO2PERSONA", property = "apellido2Colegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPODESIGNACOLEGIO", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAOFICIOJUZGADO", property = "fechaOficioJuzgado", jdbcType = JdbcType.DATE),
			@Result(column = "DELITOS", property = "delitos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHARECEPCIONCOLEGIO", property = "fechaRecepcionColegio", jdbcType = JdbcType.DATE),
			@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAJUICIO", property = "fechaJuicio", jdbcType = JdbcType.DATE),
			@Result(column = "DEFENSAJURIDICA", property = "defensaJuridica", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREINTERESADO", property = "nombreInteresado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDO1", property = "apellido1Interesado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDO2", property = "apellido2Interesado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGO", property = "codigo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPODESIGNACOLEGIO", property = "idTipoDesignaColegio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDMODULO", property = "idModulo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPRETENSION", property = "idPretension", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPROCEDIMIENTO", property = "idProcedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDJUZGADO", property = "idJuzgado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MODULO", property = "modulo", jdbcType = JdbcType.VARCHAR) })
	List<DesignaItem> busquedaDesignaciones(DesignaItem designaItem, Short idInstitucion, Integer tamMaximo);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "busquedaProcedimientoDesignas")
	@Results({ @Result(column = "PROCEDIMIENTO", property = "nombreProcedimiento", jdbcType = JdbcType.VARCHAR) })
	List<DesignaItem> busquedaProcedimientoDesignas(DesignaItem designaItem, Short idInstitucion, Integer tamMaximo);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "busquedaModuloDesignas")
	@Results({ @Result(column = "MODULO", property = "modulo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDMODULO", property = "idModulo", jdbcType = JdbcType.VARCHAR) })
	List<DesignaItem> busquedaModuloDesignas(DesignaItem designaItem, Short idInstitucion, Integer tamMaximo);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "getAsuntoTipoDesigna")
	@Results({ @Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ASUNTO", property = "asunto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHA", property = "fecha", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TURNOGUARDIA", property = "turnoGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGO", property = "codigo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DATOSINTERES", property = "datosInteres", jdbcType = JdbcType.VARCHAR), })
	AsuntosDesignaItem getAsuntoTipoDesigna(AsuntosClaveJusticiableItem asuntoClave, String idLenguaje);

	/**
	 * 
	 * @param item
	 * @param idInstitucion
	 * @param longitudCodEJG
	 * @param idPersona
	 * @return
	 */
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "busquedaJustificacionExpres")
	@Results({
			@Result(column = "TIPO_RESOLUCION_DESIGNA", property = "resolucionDesignacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAENTRADA", property = "fechaDesignacion", jdbcType = JdbcType.DATE),
			@Result(column = "ART27", property = "art27", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGODESIGNA", property = "codigoDesignacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "EXPEDIENTES", property = "ejgs", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CLIENTE", property = "cliente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGODESIGNA", property = "codigoDesignacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ANIO", property = "anioDesignacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMERO", property = "numDesignacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDJUZGADO", property = "idJuzgado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREJUZGADO", property = "nombreJuzgado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION_JUZG", property = "idInstitucionJuzgado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPROCEDIMIENTO", property = "idProcedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMPROCEDIMIENTNO", property = "numProcedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ANIOPROCEDIMIENTNO", property = "anioProcedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NIG", property = "nig", jdbcType = JdbcType.VARCHAR), })
	List<JustificacionExpressItem> busquedaJustificacionExpresPendientes(JustificacionExpressItem item,
			String idInstitucion, String longitudCodEJG, String idPersona);

	/**
	 * 
	 * @param idInstitucion
	 * @param idTurno
	 * @param anio
	 * @param numero
	 * @return
	 */
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "busquedaActuacionesJustificacionExpres")
	@Results({ @Result(column = "NUMERO", property = "numActuacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDACREDITACION", property = "idAcreditacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ACREDITACION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPOACREDITACION", property = "idTipoAcreditacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PORCENTAJE", property = "porcentaje", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TIPO", property = "tipoAcreditacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PROCEDIMIENTO", property = "procedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CATEGORIA", property = "categoriaProcedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDJURISDICCION", property = "idJurisdiccion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "COMPLEMENTO", property = "complemento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PERMITIRANIADIRLETRADO", property = "permitirAniadirLetrado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROASUNTO", property = "numAsunto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPROCEDIMIENTO", property = "idProcedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDJUZGADO", property = "idJuzgado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREJUZGADO", property = "nombreJuzgado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAJUSTIFICACION", property = "fechaJustificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "VALIDADA", property = "validada", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDFACTURACION", property = "idFacturacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROPROCEDIMIENTO", property = "numProcedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ANIOPROCEDIMIENTO", property = "anioProcedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCIONFACTURACION", property = "descripcionFacturacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DOCJUSTIFICACION", property = "docJustificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ANULACION", property = "anulacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NIG_NUMPROCEDIMIENTO", property = "nigNumProcedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NIG", property = "nig", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHA", property = "fecha", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR), 
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR), 
			})
	List<ActuacionesJustificacionExpressItem> busquedaActuacionesJustificacionExpres(String idInstitucion,
			String idTurno, String anio, String numero);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "comboModulos")
	@Results({ @Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPROCEDIMIENTO", property = "value", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> comboModulos(Short idInstitucion);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "comboProcedimientos")
	@Results({ @Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPRETENSION", property = "value", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> comboProcedimientos(Short idInstitucion);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "busquedaListaContrarios")
	@Results({ @Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ABOGADO", property = "abogado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idrepresentantelegal", property = "representante", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PROCURADOR", property = "procurador", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOSNOMBRE", property = "apellidosnombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idabogadocontrario", property = "idabogadocontrario", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idprocurador", property = "idprocurador", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE) })
	List<ListaContrarioJusticiableItem> busquedaListaContrarios(DesignaItem item, Short idInstitucion,
			Boolean historico);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "busquedaListaInteresados")
	@Results({ @Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
			@Result(column = "REPRESENTANTE", property = "representante", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOSNOMBRE", property = "apellidosnombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DIRECCION", property = "direccion", jdbcType = JdbcType.VARCHAR) })
	List<ListaInteresadoJusticiableItem> busquedaListaInteresados(DesignaItem item, Short idInstitucion);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "busquedaActDesigna")
	@Results({ @Result(column = "FECHAACTUACION", property = "fechaActuacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROASUNTO", property = "numeroAsunto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FACTURADO", property = "facturado", jdbcType = JdbcType.BOOLEAN),
			@Result(column = "MODULO", property = "modulo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ACREDITACION", property = "acreditacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "JUSTIFICACION", property = "fechaJustificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "VALIDADA", property = "validada", jdbcType = JdbcType.BOOLEAN),
			@Result(column = "ANULADA", property = "anulada", jdbcType = JdbcType.BOOLEAN),
			@Result(column = "FACTURACION", property = "facturacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LETRADO", property = "letrado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMCOLEGIADO", property = "numColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONACOLEGIADO", property = "idPersonaColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NIG", property = "nig", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROPROCEDIMIENTO", property = "numProcedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDJUZGADO", property = "idJuzgado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREJUZGADO", property = "nombreJuzgado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPROCEDIMIENTO", property = "idProcedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPRETENSION", property = "idPretension", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDACREDITACION", property = "idAcreditacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPRISION", property = "idPrision", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OBSERVACIONESJUSTIFICACION", property = "observacionesJusti", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TALONARIO", property = "talonario", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TALON", property = "talon", jdbcType = JdbcType.VARCHAR) })
	List<ActuacionDesignaItem> busquedaActDesigna(ActuacionDesignaRequestDTO actuacionDesignaRequestDTO,
			String idInstitucion);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "getNewIdActuDesigna")
	@Results({ @Result(column = "ID", property = "idMax", jdbcType = JdbcType.VARCHAR), })
	MaxIdDto getNewIdActuDesigna(ActuacionDesignaRequestDTO actuacionDesignaRequestDTO, Short idInstitucion);

	@UpdateProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "anularReactivarActDesigna")
	int anularReactivarActDesigna(ActuacionDesignaItem actuacionDesignaItem, String idInstitucion, AdmUsuarios usuario,
			boolean anular);

	@UpdateProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "validarDesvalidarActDesigna")
	int validarDesvalidarActDesigna(ActuacionDesignaItem actuacionDesignaItem, String idInstitucion,
			AdmUsuarios usuario, boolean validar);

	@DeleteProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "eliminarActDesigna")
	int eliminarActDesigna(ActuacionDesignaItem actuacionDesignaItem, String idInstitucion, AdmUsuarios usuario);

	@InsertProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "guardarActDesigna")
	int guardarActDesigna(ActuacionDesignaItem actuacionDesignaItem, String idInstitucion, AdmUsuarios usuario);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "busquedaProcurador")
	@Results({ @Result(column = "NCOLEGIADO", property = "nColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS1", property = "apellido1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS2", property = "apellido2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMERODESIGNACION", property = "numerodesignacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHADESIGNA", property = "fechaDesigna", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MOTIVOSRENUNCIA", property = "motivosRenuncia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHARENUNCIASOLICITA", property = "fecharenunciasolicita", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHARENUNCIA", property = "fechabaja", jdbcType = JdbcType.DATE) })
	List<ProcuradorItem> busquedaProcurador(String num, String idinstitucion);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "comboTipoMotivo")
	@Results({ @Result(column = "NOMBRE", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR), })
	List<ComboItem> comboTipoMotivo(Short idInstitucion);

	@UpdateProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "guardarProcurador")
	int guardarProcurador(ProcuradorItem procuradorItem);

	@InsertProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "nuevoProcurador")
	int nuevoProcurador(ProcuradorItem procuradorItem, Integer usuario);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "getProcedimientosJuzgados")
	@Results({ @Result(column = "IDPROCEDIMIENTO", property = "value", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> getProcedimientosJuzgados(Short idInstitucion, String idJuzgado);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "getProcedimientosPretension")
	@Results({ @Result(column = "IDPROCEDIMIENTO", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPRETENSION", property = "label", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> getProcedimientosPretension(Short idInstitucion, List<String> idProcedimeintos);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "comboProcedimientosConJuzgado")
	@Results({ @Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPRETENSION", property = "value", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> comboProcedimientosConJuzgado(Short idInstitucion, List<String> idPretensiones);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "comboModulosConJuzgado")
	@Results({ @Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPRETENSION", property = "value", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> comboModulosConJuzgado(Short idInstitucion, List<String> procedimientosJuzgados);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "getProcedimientoPretension")
	@Results({ @Result(column = "IDPROCEDIMIENTO", property = "value", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> getProcedimientoPretension(Short idInstitucion, String idJuzgado);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "comboModulosConProcedimientos")
	@Results({ @Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPROCEDIMIENTO", property = "value", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> comboModulosConProcedimientos(Short idInstitucion, List<String> idPretensiones);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "getPretensionModulo")
	@Results({ @Result(column = "IDPRETENSION", property = "value", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> getPretensionModulo(Short idInstitucion, String idJuzgado);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "comboProcedimientosConModulos")
	@Results({ @Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPRETENSION", property = "value", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> comboProcedimientosConModulos(Short idInstitucion, List<String> idPretensiones);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "getDatosAdicionales")
	@Results({ @Result(column = "FECHAOFICIOJUZGADO", property = "fechaOficioJuzgado", jdbcType = JdbcType.DATE),
			@Result(column = "DELITOS", property = "delitos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHARECEPCIONCOLEGIO", property = "fechaRecepcionColegio", jdbcType = JdbcType.DATE),
			@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAJUICIO", property = "fechaJuicio", jdbcType = JdbcType.DATE),
			@Result(column = "DEFENSAJURIDICA", property = "defensaJuridica", jdbcType = JdbcType.VARCHAR), })
	List<DesignaItem> getDatosAdicionales(Short idInstitucion, Integer tamMaximo, DesignaItem designa);

}