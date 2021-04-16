package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaItem;
import org.itcgae.siga.DTOs.scs.ActuacionesJustificacionExpressItem;
import org.itcgae.siga.DTOs.scs.ActuacionDesignaRequestDTO;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.AsuntosDesignaItem;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableItem;
import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.JustificacionExpressItem;
import org.itcgae.siga.DTOs.scs.ListaContrarioJusticiableItem;
import org.itcgae.siga.DTOs.scs.ProcuradorItem;
import org.itcgae.siga.DTOs.scs.ListaInteresadoJusticiableItem;
import org.itcgae.siga.db.entities.ScsTurno;
import org.itcgae.siga.db.entities.ScsTurnoKey;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.ScsDesignaMapper;
import org.itcgae.siga.db.services.scs.providers.ScsBajasTemporalesSqlExtendsProvider;
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
			@Result(column = "ESTADO", property = "art27", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAESTADO", property = "fechaEstado", jdbcType = JdbcType.DATE),
			@Result(column = "NOMBRE", property = "nombreTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NCOLEGIADO", property = "numColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAALTA", property = "fechaAlta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NIG", property = "nig", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMPROCEDIMIENTO", property = "numProcedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREJUZGADO", property = "nombreJuzgado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREPROCEDIMIENTO", property = "nombreProcedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREPERSONA", property = "nombreColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDO1PERSONA", property = "apellido1Colegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDO2PERSONA", property = "apellido2Colegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPODESIGNACOLEGIO", property = "observaciones", jdbcType = JdbcType.VARCHAR) })
	List<DesignaItem> busquedaDesignaciones(DesignaItem designaItem, Short idInstitucion, Integer tamMaximo);

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
		@Result(column = "IDINSTITUCION_JUZG", property = "idInstitucionJuzgado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPROCEDIMIENTO", property = "idProcedimiento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMPROCEDIMIENTNO", property = "numProcedimiento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ANIOPROCEDIMIENTNO", property = "anioProcedimiento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIG", property = "nig", jdbcType = JdbcType.VARCHAR), })
	List<JustificacionExpressItem> busquedaJustificacionExpresPendientes(JustificacionExpressItem item, String idInstitucion,
			String longitudCodEJG, String idPersona);
	
	/**
	 * 
	 * @param idInstitucion
	 * @param idTurno
	 * @param anio
	 * @param numero
	 * @return
	 */
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "busquedaActuacionesJustificacionExpres")
	@Results({
		@Result(column = "NUMERO", property = "numActuacion", jdbcType = JdbcType.VARCHAR),
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
		@Result(column = "PERMITIREDITARLETRADO", property = "permitirLetrado", jdbcType = JdbcType.VARCHAR),
	})
	List<ActuacionesJustificacionExpressItem> busquedaActuacionesJustificacionExpres(String idInstitucion, String idTurno, String anio, String numero);
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "comboModulos")
	@Results({ @Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPROCEDIMIENTO", property = "value", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> comboModulos(Short idInstitucion);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "comboProcedimientos")
	@Results({ @Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPROCEDIMIENTO", property = "value", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> comboProcedimientos(Short idInstitucion);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "busquedaListaContrarios")
	@Results({ @Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ABOGADO", property = "abogado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PROCURADOR", property = "procurador", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOSNOMBRE", property = "apellidosnombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE)
	})
	List<ListaContrarioJusticiableItem> busquedaListaContrarios(DesignaItem item, Short idInstitucion, Boolean historico);
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "busquedaListaInteresados")
	@Results({ @Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
			@Result(column = "REPRESENTANTE", property = "representante", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOSNOMBRE", property = "apellidosnombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DIRECCION", property = "direccion", jdbcType = JdbcType.VARCHAR)
	})
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
			@Result(column = "FACTURACION", property = "facturacion", jdbcType = JdbcType.VARCHAR) })
	List<ActuacionDesignaItem> busquedaActDesigna(ActuacionDesignaRequestDTO actuacionDesignaRequestDTO,
			String idInstitucion);

	@UpdateProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "anularReactivarActDesigna")
	int anularReactivarActDesigna(ActuacionDesignaItem actuacionDesignaItem, String idInstitucion, AdmUsuarios usuario,
			boolean anular);

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "busquedaProcurador")
	@Results({
			@Result(column = "NCOLEGIADO", property = "ncolegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS1", property = "apellidos1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS2", property = "apellidos2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMERODESIGNACION", property = "numerodesignacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHADESIGNA", property = "fechadesigna", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MOTIVOSRENUNCIA", property = "motivosrenuncia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHARENUNCIASOLICITA", property = "fecharenunciasolicita", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHARENUNCIASOLICITA", property = "fecharenunciaefectiva", jdbcType = JdbcType.VARCHAR) })
	List<ProcuradorItem> busquedaProcurador(String num, String idinstitucion);
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "comboTipoMotivo")
	@Results({
		@Result(column = "NOMBRE", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
	})

	List<ComboItem> comboTipoMotivo(short idInstitucion);
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "obtenerNumeroDesigna")
	@Results({ 
			@Result(column = "CODIGO", property = "codigo", jdbcType = JdbcType.VARCHAR),
	})
	String obtenerNumeroDesigna(String idInstitucion, String anio);
}