package org.itcgae.siga.db.services.scs.mappers;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.itcgae.siga.DTOs.com.EnviosMasivosItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.ComboItem2;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaItem;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaRequestDTO;
import org.itcgae.siga.DTOs.scs.ActuacionesJustificacionExpressItem;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.AsuntosDesignaItem;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableItem;
import org.itcgae.siga.DTOs.scs.BajasTemporalesItem;
import org.itcgae.siga.DTOs.scs.DatosCartaAcreditacionItem;
import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.DocumentoActDesignaItem;
import org.itcgae.siga.DTOs.scs.DocumentoDesignaItem;
import org.itcgae.siga.DTOs.scs.InscripcionTurnoItem;
import org.itcgae.siga.DTOs.scs.JustificacionExpressItem;
import org.itcgae.siga.DTOs.scs.LetradoInscripcionItem;
import org.itcgae.siga.DTOs.scs.ListaContrarioJusticiableItem;
import org.itcgae.siga.DTOs.scs.ListaInteresadoJusticiableItem;
import org.itcgae.siga.DTOs.scs.ListaLetradosDesignaItem;
import org.itcgae.siga.DTOs.scs.ProcuradorItem;
import org.itcgae.siga.DTOs.scs.RelacionesItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.ScsDesigna;
import org.itcgae.siga.db.entities.ScsDesignaKey;
import org.itcgae.siga.db.entities.ScsSaltoscompensaciones;
import org.itcgae.siga.db.mappers.ScsDesignaMapper;
import org.itcgae.siga.db.services.scs.providers.ScsDesignacionesSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;



@Service
@Primary
public interface ScsDesignacionesExtendsMapper extends ScsDesignaMapper {

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "searchClaveDesignaciones")
	@Results({@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "ASUNTO", property = "asunto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TURNOGUARDIA", property = "turnoGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "INTERESADO", property = "interesado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "LETRADO", property = "letrado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAENTRADA", property = "fecha", jdbcType = JdbcType.DATE),
		@Result(column = "numeroprocedimiento", property = "numeroProcedimiento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIG", property = "nig", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ESTADO", property = "idEstadoDesigna", jdbcType = JdbcType.VARCHAR),
		@Result(column = "JUZGADO", property = "juzgado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPODESIGNACOLEGIO", property = "idTipoDesigna", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPODESIGNA", property = "tipo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "dilnigproc", property = "dilnigproc", jdbcType = JdbcType.VARCHAR)})
	
	List<AsuntosJusticiableItem> searchClaveDesignaciones(AsuntosJusticiableItem asuntosJusticiableItem,
			Integer tamMaximo, String idLenguaje);

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
			@Result(column = "RESUMENASUNTO", property = "resumenAsunto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAJUICIO", property = "fechaJuicio", jdbcType = JdbcType.TIMESTAMP),
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
			@Result(column = "ART27", property = "art27", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MODULO", property = "modulo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "VALIDADA", property = "validada", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR)})
	List<DesignaItem> busquedaDesignaciones(DesignaItem designaItem, Short idInstitucion, Integer tamMaximo);
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "busquedaNuevaDesigna")
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
			@Result(column = "CODIGO", property = "codigo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPODESIGNACOLEGIO", property = "idTipoDesignaColegio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDMODULO", property = "idModulo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPRETENSION", property = "idPretension", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPROCEDIMIENTO", property = "idProcedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDJUZGADO", property = "idJuzgado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ART27", property = "art27", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MODULO", property = "modulo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "VALIDADA", property = "validada", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR)})
	List<DesignaItem> busquedaNuevaDesigna(DesignaItem designaItem, Short idInstitucion, Integer tamMaximo, boolean isNoColegiado);

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
			@Result(column = "CATEGORIAJUZGADO", property = "categoriaJuzgado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDJUZGADO", property = "idJuzgado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREJUZGADO", property = "nombreJuzgado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION_JUZG", property = "idInstitucionJuzgado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPROCEDIMIENTO", property = "idProcedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PROCEDIMIENTO", property = "procedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CATEGORIA", property = "categoriaProcedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMPROCEDIMIENTO", property = "numProcedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ANIOPROCEDIMIENTNO", property = "anioProcedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NIG", property = "nig", jdbcType = JdbcType.VARCHAR), 
			@Result(column = "VALIDARJUSTIFICACIONES", property = "validarjustificaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LETRADOACTUACIONES", property = "letradoActuaciones", jdbcType = JdbcType.VARCHAR)})
//	List<JustificacionExpressItem> busquedaJustificacionExpresPendientes(JustificacionExpressItem item,
//			String idInstitucion, String longitudCodEJG, String idPersona,  String fechaDesde, String fechaHasta);
	List<JustificacionExpressItem> busquedaJustificacionExpresPendientes(JustificacionExpressItem item,
			String idInstitucion, String longitudCodEJG, String idPersona, String idFavorable, String idDesfavorable, String fechaDesde, String fechaHasta);

	/**
	 * 
	 * @param idInstitucion
	 * @param idTurno
	 * @param anio
	 * @param numero
	 * @return
	 */
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "busquedaActuacionesJustificacionExpres")
	@Results({ @Result(column = "FACTURADO", property = "facturado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMERO", property = "numDesignacion", jdbcType = JdbcType.VARCHAR),
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
			@Result(column = "CATEGORIAJUZGADO", property = "categoriaJuzgado", jdbcType = JdbcType.VARCHAR),
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
			String idTurno, String anio, String numero, JustificacionExpressItem item);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "comboModulos")
	@Results({ @Result(column = "NOMBRE", property = "label2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPROCEDIMIENTO", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGO", property = "label1", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem2> comboModulos(Short idInstitucion, int filtro, String fecha);
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "comboDelitos")
	@Results({ @Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDDELITO", property = "value", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> comboDelitos(DesignaItem designaItem, Short idInstitucion);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "comboProcedimientos")
	@Results({ @Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPRETENSION", property = "value", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> comboProcedimientos(Short idInstitucion,String idLenguaje);

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
			@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
			@Result(column = "idInstitucion_procu", property = "idInstitucionProc", jdbcType = JdbcType.VARCHAR)
			})
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
			@Result(column = "TALON", property = "talon", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDFACTURACION", property = "idFacturacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ID_MOTIVO_CAMBIO", property = "idMotivoCambio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "USUCREACION", property = "usuCreacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHACREACION", property = "fechaCreacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "USUMODIFICACION", property = "usuModificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechaModificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "USUJUSTIFICACION", property = "usuJustificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAUSUJUSTIFICACION", property = "fechaUsuJustificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "USUVALIDACION", property = "usuValidacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "VALIDARJUSTIFICACIONES", property = "validarJustificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAVALIDACION", property = "fechaValidacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPARTIDAPRESUPUESTARIA", property = "idPartidaPresupuestaria", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREPARTIDA", property = "partidaPresupuestaria", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR)
	})
	List<ActuacionDesignaItem> busquedaActDesigna(ActuacionDesignaRequestDTO actuacionDesignaRequestDTO,
			String idInstitucion);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "getNewIdActuDesigna")
	@Results({ @Result(column = "ID", property = "idMax", jdbcType = JdbcType.VARCHAR), })
	MaxIdDto getNewIdActuDesigna(ActuacionDesignaItem actuacionDesignaItem, Short idInstitucion);

	@UpdateProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "anularReactivarActDesigna")
	int anularReactivarActDesigna(ActuacionDesignaItem actuacionDesignaItem, Short idInstitucion, AdmUsuarios usuario, boolean anular);

	@UpdateProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "validarDesvalidarActDesigna")
	int validarDesvalidarActDesigna(ActuacionDesignaItem actuacionDesignaItem, String idInstitucion,
			AdmUsuarios usuario, boolean validar);

	@DeleteProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "eliminarActDesigna")
	int eliminarActDesigna(ActuacionDesignaItem actuacionDesignaItem, Short idInstitucion, AdmUsuarios usuario);

	@InsertProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "guardarActDesigna")
	int guardarActDesigna(ActuacionDesignaItem actuacionDesignaItem, String idInstitucion, AdmUsuarios usuario);
	
	@UpdateProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "actualizarActDesigna")
	int actualizarActDesigna(ActuacionDesignaItem actuacionDesignaItem, String idInstitucion, AdmUsuarios usuario);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "busquedaProcurador")
	@Results({ @Result(column = "NCOLEGIADO", property = "nColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS1", property = "apellido1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS2", property = "apellido2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "anio", property = "anio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMERODESIGNACION", property = "numerodesignacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHADESIGNA", property = "fechaDesigna", jdbcType = JdbcType.DATE),
			@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MOTIVOSRENUNCIA", property = "motivosRenuncia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHARENUNCIASOLICITA", property = "fecharenunciasolicita", jdbcType = JdbcType.DATE),
			@Result(column = "FECHARENUNCIA", property = "fechabaja", jdbcType = JdbcType.DATE),
			@Result(column = "idprocurador", property = "idProcurador", jdbcType = JdbcType.DATE),
			@Result(column = "idinstitucion_proc", property = "idInstitucion", jdbcType = JdbcType.DATE)})
	List<ProcuradorItem> busquedaProcurador(String num, String idinstitucion, String idturno, String anio);
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "compruebaProcurador")
	@Results({
			@Result(column = "NCOLEGIADO", property = "nColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS1", property = "apellido1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS2", property = "apellido2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMERODESIGNACION", property = "numerodesignacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHADESIGNA", property = "fechaDesigna", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MOTIVOSRENUNCIA", property = "motivosRenuncia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHARENUNCIASOLICITA", property = "fecharenunciasolicita", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHARENUNCIA", property = "fechabaja", jdbcType = JdbcType.DATE) })
	List<ProcuradorItem> compruebaProcurador(String num, String anio);
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "compruebaFechaProcurador")
	@Results({
			@Result(column = "NCOLEGIADO", property = "nColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS1", property = "apellido1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS2", property = "apellido2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMERODESIGNACION", property = "numerodesignacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHADESIGNA", property = "fechaDesigna", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MOTIVOSRENUNCIA", property = "motivosRenuncia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHARENUNCIASOLICITA", property = "fecharenunciasolicita", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHARENUNCIA", property = "fechabaja", jdbcType = JdbcType.DATE) })
	List<ProcuradorItem> compruebaFechaProcurador(ProcuradorItem procurador, Short idInstitucion);


	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "comboTipoMotivo")
	@Results({ @Result(column = "IDTIPOMOTIVO", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR), })
	List<ComboItem> comboTipoMotivo(Short idInstitucion, String idLenguaje);

	@UpdateProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "guardarProcurador")
	int guardarProcurador(ProcuradorItem procuradorItem, String fecha);
	
	@UpdateProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "guardarProcuradorEJG")
	int guardarProcuradorEJG(ProcuradorItem procuradorItem);
	
	@UpdateProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "actualizarProcurador")
	int actualizarProcurador(ProcuradorItem procuradorItem);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "obtenerCodigoDesigna")
	@Results({ 
			@Result(column = "CODIGO", property = "codigo", jdbcType = JdbcType.VARCHAR),
	})
	String obtenerCodigoDesigna(String idInstitucion, String anio);
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "obtenerNumeroDesigna")
	@Results({ 
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
	})
	String obtenerNumeroDesigna(String idInstitucion, String anio);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "getProcedimientosJuzgados")
	@Results({ @Result(column = "IDPROCEDIMIENTO", property = "value", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> getProcedimientosJuzgados(Short idInstitucion, String idJuzgado);
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "getProcedimientosJuzgados2")
	@Results({ @Result(column = "IDPROCEDIMIENTO", property = "value", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> getProcedimientosJuzgados2(Short idInstitucion);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "getProcedimientosPretension")
	@Results({ @Result(column = "IDPROCEDIMIENTO", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPRETENSION", property = "label", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> getProcedimientosPretension(Short idInstitucion, List<String> idProcedimientos);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "comboProcedimientosConJuzgado")
	@Results({ @Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPRETENSION", property = "value", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> comboProcedimientosConJuzgado(Short idInstitucion, List<String> idPretensiones);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "comboModulosConJuzgado")
	@Results({ @Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPROCEDIMIENTO", property = "value", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> comboModulosConJuzgado(Short idInstitucion, List<String> procedimientosJuzgados, int filtro, String fecha);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "getProcedimientoPretension")
	@Results({ @Result(column = "IDPROCEDIMIENTO", property = "value", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> getProcedimientoPretension(Short idInstitucion, String idJuzgado);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "comboModulosConProcedimientos")
	@Results({ @Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPROCEDIMIENTO", property = "value", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> comboModulosConProcedimientos(Short idInstitucion, List<String> idPretensiones, int filtro, String fecha);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "getPretensionModulo")
	@Results({ @Result(column = "IDPRETENSION", property = "value", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> getPretensionModulo(Short idInstitucion, String idJuzgado);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "comboProcedimientosConModulos")
	@Results({ @Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPRETENSION", property = "value", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> comboProcedimientosConModulos(Short idInstitucion, List<String> idPretensiones);
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "comboAcreditacionesPorModulo")
	@Results({ @Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ID", property = "value", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> comboAcreditacionesPorModulo(Short idInstitucion, String idModulo);
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "existeDesginaJuzgadoProcedimiento")
	@Results({@Result(column = "NUM", property = "existeDesignaJuzgadoProcedimiento", jdbcType = JdbcType.VARCHAR), })
	DesignaItem existeDesginaJuzgadoProcedimiento(Short idInstitucion, DesignaItem designa);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "getDatosAdicionales")
	@Results({ @Result(column = "FECHAOFICIOJUZGADO", property = "fechaOficioJuzgado", jdbcType = JdbcType.DATE),
			@Result(column = "DELITOS", property = "delitos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHARECEPCIONCOLEGIO", property = "fechaRecepcionColegio", jdbcType = JdbcType.DATE),
			@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAJUICIO", property = "fechaJuicio", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "DEFENSAJURIDICA", property = "defensaJuridica", jdbcType = JdbcType.VARCHAR), })
	List<DesignaItem> getDatosAdicionales(Short idInstitucion, Integer tamMaximo, DesignaItem designa);
	

	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "busquedaRelaciones")
	@Results({ @Result(column = "SJCS", property = "sjcs", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDLETRADO", property = "idletrado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LETRADO", property = "letrado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNODESIGNA", property = "idturnodesigna", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPO", property = "idtipo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGO", property = "codigo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESC_TURNO", property = "descturno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DES_TIPO", property = "destipo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "INTERESADO", property = "interesado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPUGNACION", property = "impugnacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAIMPUGNACION", property = "fechaimpugnacion", jdbcType = JdbcType.DATE),
			@Result(column = "DICTAMEN", property = "dictamen", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHADICTAMEN", property = "fechadictamen", jdbcType = JdbcType.DATE),
			@Result(column = "RESOLUCION", property = "resolucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHARESOLUCION", property = "fecharesolucion", jdbcType = JdbcType.DATE),
			@Result(column = "CENTRODETENCION", property = "centrodetencion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAASUNTO", property = "fechaasunto", jdbcType = JdbcType.DATE),
			@Result(column = "DILNIGPROC", property = "dilnigproc", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NCOL", property = "nColLetrado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NIF", property = "nifInteresado", jdbcType = JdbcType.VARCHAR)})
	List<RelacionesItem> busquedaRelaciones(String designaAnio, String designaNumero, String designaTurno, String idInstitucion);
	

	@DeleteProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "eliminarRelacion")
	int eliminarRelacion(String anioEjg, String numEjg, String idTurno, String idinstitucion, String anioDes, String numDes, String idTipoEjg);
	

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "getPartidaPresupuestariaDesigna")
	@Results({ @Result(column = "NOMBREPARTIDA", property = "label", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPARTIDAPRESUPUESTARIA", property = "value", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> getPartidaPresupuestariaDesigna(Short idInstitucion, DesignaItem designaItem);
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "comboMotivosCambioActDesigna")
	@Results({ @Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDACTDESMOTCAMBIO", property = "value", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> comboMotivosCambioActDesigna(Short idInstitucion,String idLenguaje);
	
	@UpdateProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "updateJustiActDesigna")
	int updateJustiActDesigna(ActuacionDesignaItem actuacionDesignaItem, String idInstitucion, AdmUsuarios usuario);


	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "getListaLetradosDesigna")
	@Results({ @Result(column = "FECHAdesigna", property = "fechaDesignacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "fecharenuncia", property = "fechaEfecRenuncia", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAREnunciasolicita", property = "fechaSolRenuncia", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "motivosrenuncia", property = "motivoRenuncia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ncolegiado", property = "nColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idpersona", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "apellidosnombre", property = "apellidosNombre", jdbcType = JdbcType.VARCHAR), 
			@Result(column = "letradoDelTurno", property = "letradoTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "observaciones", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nifcif", property = "nif", jdbcType = JdbcType.VARCHAR)})
	List<ListaLetradosDesignaItem> getListaLetradosDesigna( ScsDesigna designa, Short idInstitucion);
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "getLetradosDiasBajaTemporalTurno")
	@Results({ @Result(column = "IDPERSONA", property = "idersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHABT", property = "fechabt", jdbcType = JdbcType.DATE),
			@Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHADESDE", property = "fechadesde", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAHASTA", property = "fechahasta", jdbcType = JdbcType.DATE),
			@Result(column = "FECHAALTA", property = "fechaalta", jdbcType = JdbcType.DATE),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "VALIDADO", property = "validado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAESTADO", property = "fechaestado", jdbcType = JdbcType.DATE),
			@Result(column = "ELIMINADO", property = "eliminado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.VARCHAR)
			})
	List<BajasTemporalesItem> getLetradosDiasBajaTemporalTurno(String idInstitucion,String idTurno,String fecha);
	
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "getSaltos")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.NUMERIC),
		    @Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.NUMERIC),
	        @Result(column = "IDSALTOSTURNO", property = "idSaltoCompensacion", jdbcType = JdbcType.NUMERIC),
		    @Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.NUMERIC),
			@Result(column = "SALTOOCOMPENSACION", property = "saltoocompensacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHA", property = "fecha", jdbcType = JdbcType.DATE),
			@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.NUMERIC),
			@Result(column = "MOTIVOS", property = "motivos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHACUMPLIMIENTO", property = "fechacumplimiento", jdbcType = JdbcType.DATE),
			@Result(column = "IDCALENDARIOGUARDIAS", property = "idcalendarioguardias", jdbcType = JdbcType.NUMERIC),
			@Result(column = "IDCALENDARIOGUARDIASCREACION", property = "idcalendarioguardiascreacion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "TIPOMANUAL", property = "tipomanual", jdbcType = JdbcType.NUMERIC),
			@Result(column = "FECHA_ANULACION", property = "fecha_anulacion", jdbcType = JdbcType.DATE)
			
			})
	List<LetradoInscripcionItem> getSaltos(String idInstitucion, String idTurno, String idGuardia);
	
	
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "getCompensaciones")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.NUMERIC),
		    @Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.NUMERIC),
	        @Result(column = "IDSALTOSTURNO", property = "idSaltoCompensacion", jdbcType = JdbcType.NUMERIC),
		    @Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.NUMERIC),
			@Result(column = "SALTOOCOMPENSACION", property = "saltoocompensacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHA", property = "fecha", jdbcType = JdbcType.DATE),
			@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.NUMERIC),
			@Result(column = "MOTIVOS", property = "motivos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHACUMPLIMIENTO", property = "fechacumplimiento", jdbcType = JdbcType.DATE),
			@Result(column = "IDCALENDARIOGUARDIAS", property = "idcalendarioguardias", jdbcType = JdbcType.NUMERIC),
			@Result(column = "IDCALENDARIOGUARDIASCREACION", property = "idcalendarioguardiascreacion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "TIPOMANUAL", property = "tipomanual", jdbcType = JdbcType.NUMERIC),
			@Result(column = "FECHA_ANULACION", property = "fecha_anulacion", jdbcType = JdbcType.DATE)
			
			})
    List<LetradoInscripcionItem> getCompensaciones(String idInstitucion, String idTurno, String fecha);
	
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "getInscripcionTurnoActiva")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.NUMERIC),
		    @Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.NUMERIC),
		    @Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.NUMERIC),
			@Result(column = "FECHASOLICITUD", property = "fechasolicitud", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "FECHAVALIDACION", property = "fechavalidacion", jdbcType = JdbcType.DATE),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.DATE),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS1", property = "apellidos1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS2", property = "apellidos2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ALFABETICOAPELLIDOS", property = "alfabeticoapellidos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROCOLEGIADO", property = "numerocolegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHANACIMIENTO", property = "fechanacimiento", jdbcType = JdbcType.DATE),
			@Result(column = "ANTIGUEDADCOLA", property = "antiguedadcola", jdbcType = JdbcType.DATE)
			
			})
	InscripcionTurnoItem getInscripcionTurnoActiva(String idinstitucion, String idturno, String idpersona, String fecha);
	

	@UpdateProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "cambiarUltimoCola")
	int cambiarUltimoCola(String idInstitucion, String idTurno, String idPersonaUltimo, Date fechaSolicitudUltimo,AdmUsuarios usuario);
	
	@UpdateProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "marcarSaltoCompensacion")
	int marcarSaltoCompensacion(ScsSaltoscompensaciones saltoCompensacion, AdmUsuarios usuario);
	
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "getColaTurnoBBDD")
	@Results({ @Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.NUMERIC),
		    @Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.NUMERIC),
		    @Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.NUMERIC),
			@Result(column = "FECHAVALIDACION", property = "fechavalidacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHASOLICITUD", property = "fechasolicitud", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "NIFCIF", property = "nifcif", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS1", property = "apellidos1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS2", property = "apellidos2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ALFABETICOAPELLIDOS", property = "alfabeticoapellidos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROCOLEGIADO", property = "numerocolegiado", jdbcType = JdbcType.VARCHAR),
		
			@Result(column = "ACTIVO", property = "estado", jdbcType = JdbcType.VARCHAR)
			})
 	 List<InscripcionTurnoItem> getColaTurnoBBDD(String idinstitucion, String idturno, String fecha, String order);
	
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "busquedaComunicaciones")
		  @Results({
			  @Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
              @Result(column = "IDENVIO", property = "idEnvio", jdbcType = JdbcType.NUMERIC),
              @Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
              @Result(column = "FECHA", property = "fechaCreacion", jdbcType = JdbcType.DATE),
              @Result(column = "IDPLANTILLAENVIOS", property = "idPlantillaEnvios", jdbcType = JdbcType.VARCHAR),
              @Result(column = "IDESTADO", property = "idEstado", jdbcType = JdbcType.NUMERIC),
              @Result(column = "IDTIPOENVIOS", property = "idTipoEnvios", jdbcType = JdbcType.NUMERIC),
              @Result(column = "NOMBREPLANTILLA", property = "nombrePlantilla", jdbcType = JdbcType.VARCHAR),
              @Result(column = "IDPLANTILLA", property = "idPlantilla", jdbcType = JdbcType.VARCHAR),
              @Result(column = "FECHAPROGRAMADA", property = "fechaProgramada", jdbcType = JdbcType.DATE),
              @Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
              @Result(column = "ASUNTO", property = "asunto", jdbcType = JdbcType.VARCHAR),
              @Result(column = "CUERPO", property = "cuerpo", jdbcType = JdbcType.VARCHAR),
              @Result(column = "TIPOENVIO", property = "tipoEnvio", jdbcType = JdbcType.VARCHAR),
              @Result(column = "DESTINATARIO", property = "destinatario", jdbcType = JdbcType.VARCHAR),
              @Result(column = "ESTADOENVIO", property = "estadoEnvio", jdbcType = JdbcType.VARCHAR)
			})
	List<EnviosMasivosItem> busquedaComunicaciones(String num, String anio, String idturno, Short idInstitucion, String idLenguaje);


	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "obtenerIdPersonaByNumCol")
	@Results({ 
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.VARCHAR),
	})
	String obtenerIdPersonaByNumCol(String idInstitucion, String numColegiado);
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "obtenerIdPersonaByNumComunitario")
	@Results({ 
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.VARCHAR),
	})
	String obtenerIdPersonaByNumComunitario(String idInstitucion, String numColegiado);
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "obtenerIdPersonaByNumColNColegiado")
	@Results({ 
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.VARCHAR),
	})
	String obtenerIdPersonaByNumColNColegiado(String numColegiado);
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "obtenerNumNoColegiado")
	@Results({ 
			@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.VARCHAR),
	})
	String obtenerNumNoColegiado(String idInstitucion, String idPersona);
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "comboAcreditacionesPorTipo")
	@Results({ @Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ID", property = "value", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> comboAcreditacionesPorTipo(Short idInstitucion, String idProcedimiento);
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "getNewIdDocumentacionAsi")
	@Results({ @Result(column = "ID", property = "idMax", jdbcType = JdbcType.VARCHAR), })
	MaxIdDto getNewIdDocumentacionAsi(Short idInstitucion);
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "getDocumentosPorActDesigna")
	@Results({
			@Result(column = "IDDOCUMENTACIONASI", property = "idDocumentacionasi", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPODOCUMENTO", property = "idTipoDocumento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRETIPODOCUMENTO", property = "nombreTipoDocumento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDFICHERO", property = "idFichero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDACTUACION", property = "idActuacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "USUMODIFICACION", property = "usuModificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechaModificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAENTRADA", property = "fechaEntrada", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREFICHERO", property = "nombreFichero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			})
	List<DocumentoActDesignaItem> getDocumentosPorActDesigna(DocumentoActDesignaItem documentoActDesignaItem, Short idInstitucion);
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "getDocumentosPorDesigna")
	@Results({
			@Result(column = "IDDOCUMENTACIONDES", property = "idDocumentaciondes", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPODOCUMENTO", property = "idTipodocumento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRETIPODOCUMENTO", property = "nombreTipoDocumento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDFICHERO", property = "idFichero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDACTUACION", property = "idActuacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "USUMODIFICACION", property = "usuModificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechaModificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAENTRADA", property = "fechaEntrada", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREFICHERO", property = "nombreFichero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			})
	List<DocumentoDesignaItem> getDocumentosPorDesigna(DocumentoDesignaItem documentoDesignaItem, Short idInstitucion);

	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "comboTipoDocumentacionDesigna")
	@Results({ @Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPODOCUMENTODES", property = "value", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> comboTipoDocumentacionDesigna();
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "getNewIdDocumentacionDes")
	@Results({ @Result(column = "ID", property = "idMax", jdbcType = JdbcType.VARCHAR), })
	MaxIdDto getNewIdDocumentacionDes(Short idInstitucion);
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "comprobarCodigoDesigna")
	@Results({ 
			@Result(column = "CODIGO", property = "codigo", jdbcType = JdbcType.VARCHAR),
	})
	String comprobarCodigoDesigna(String idInstitucion, String anio, String idTurno,  String codigo);
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "busquedaJuzgadoDesignas")
	@Results({ @Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR) })
	String busquedaJuzgadoDesignas(Integer idJuzgado, Short idInstitucion, Integer tamMaximo);
	
	@UpdateProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "actualizarPartidaPresupuestariaActDesigna")
	int actualizarPartidaPresupuestariaActDesigna(ActuacionDesignaItem actuacionDesignaItem, Short idInstitucion,
			AdmUsuarios usuario);

	@UpdateProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "actualizarPartidaPresupuestariaDesigna")
	int actualizarPartidaPresupuestariaDesigna(DesignaItem designaItem, Short idInstitucion,
			AdmUsuarios usuario);
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "getDelitos")
	@Results({ 
			@Result(column = "IDDELITO", property = "idDelito", jdbcType = JdbcType.VARCHAR),
	})
	List<String> getDelitos(Short idInstitucion, DesignaItem designaItem);
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "getDefendidosDesigna")
	@Results({
			@Result(column = "idinstitucion", property = "idinstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idturno", property = "idturno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "anio", property = "anio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numero", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idpersonainteresado", property = "idpersonainteresado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombre_defendido", property = "nombre_defendido", jdbcType = JdbcType.VARCHAR),
			@Result(column = "domicilio_defendido", property = "domicilio_defendido", jdbcType = JdbcType.VARCHAR),
			@Result(column = "cp_defendido", property = "cp_defendido", jdbcType = JdbcType.VARCHAR),
			@Result(column = "poblacion_defendido", property = "poblacion_defendido", jdbcType = JdbcType.VARCHAR),
			@Result(column = "provincia_defendido", property = "provincia_defendido", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombre_pais", property = "nombre_pais", jdbcType = JdbcType.VARCHAR),
			@Result(column = "obs_defendido", property = "obs_defendido", jdbcType = JdbcType.VARCHAR),
			@Result(column = "telefono1_defendido", property = "telefono1_defendido", jdbcType = JdbcType.VARCHAR),
			@Result(column = "lista_telefonos_interesado", property = "lista_telefonos_interesado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nif_defendido", property = "nif_defendido", jdbcType = JdbcType.VARCHAR),
			@Result(column = "sexo_defendido", property = "sexo_defendido", jdbcType = JdbcType.VARCHAR),
			@Result(column = "sexo_defendido_descripcion", property = "sexo_defendido_descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "o_a_defendido", property = "o_a_defendido", jdbcType = JdbcType.VARCHAR),
			@Result(column = "el_la_defendido", property = "el_la_defendido", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idlenguaje_defendido", property = "idlenguaje_defendido", jdbcType = JdbcType.VARCHAR),
			@Result(column = "anio_ejg", property = "anio_ejg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numero_ejg", property = "numero_ejg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fecharesolucioncajg", property = "fecharesolucioncajg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "count_ejg", property = "count_ejg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "calidad_defendido", property = "calidad_defendido", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idtipoencalidad", property = "idtipoencalidad", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idrepresentantejg", property = "idrepresentantejg", jdbcType = JdbcType.VARCHAR)
		})
	List<DatosCartaAcreditacionItem> getDefendidosDesigna (String idInstitucion, String numero, String idTurno, String anio, String idPersonaJG, String idPersona, String longitudNumEjg);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "getDatosEjgResolucionFavorable")
	@Results({
			@Result(column = "Nombre", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "Apellido1", property = "apellido1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "Apellido2", property = "apellido2", jdbcType = JdbcType.VARCHAR)
		})
	List<DatosCartaAcreditacionItem> getDatosEjgResolucionFavorable(String idInstitucion, String idTurno, String anio,
			String numero);
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "busquedaDesignaActual")
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
			@Result(column = "FECHAJUICIO", property = "fechaJuicio", jdbcType = JdbcType.TIMESTAMP),
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
			@Result(column = "ART27", property = "art27", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MODULO", property = "modulo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "VALIDADA", property = "validada", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR)})
	List<DesignaItem> busquedaDesignaActual(ScsDesignaKey key);
}