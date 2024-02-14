package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.*;
import org.itcgae.siga.db.entities.ScsActuacionasistencia;
import org.itcgae.siga.db.mappers.ScsActuacionasistenciaSqlProvider;
import org.itcgae.siga.db.mappers.ScsAsistenciaMapper;
import org.itcgae.siga.db.mappers.ScsAsistenciaSqlProvider;
import org.itcgae.siga.db.services.scs.providers.ScsAsistenciaSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsDesignacionesSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsEstadoAsistenciaSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsAsistenciaExtendsMapper extends ScsAsistenciaMapper{

	@SelectProvider(type = ScsAsistenciaSqlExtendsProvider.class, method = "searchClaveAsistencia")
	@Results({ @Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "ASUNTO", property = "asunto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TURNOGUARDIA", property = "turnoGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "INTERESADO", property = "interesado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "JUZGADO", property = "juzgado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "LETRADO", property = "letrado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAHORA", property = "fecha", jdbcType = JdbcType.DATE),
		@Result(column = "NUMEROPROCEDIMIENTO", property = "numeroProcedimiento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOASISTENCIA", property = "idTipoAsistencia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOASISTENCIA", property = "tipo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMERODILIGENCIA", property = "numeroDiligencia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "dilnigproc", property = "dilnigproc", jdbcType = JdbcType.VARCHAR)
	})
	List<AsuntosJusticiableItem> searchClaveAsistencia(AsuntosJusticiableItem asuntosJusticiableItem, Integer tamMaximo, String idLenguaje);
	
	
	@SelectProvider(type = ScsAsistenciaSqlExtendsProvider.class, method = "searchHitoNueveAsistencia")
	int searchHitoNueveAsistencia(String anio, String numero, String idInstitucion);

	@SelectProvider(type = ScsAsistenciaSqlExtendsProvider.class, method = "getAsuntoTipoAsistencia")
	@Results({ 
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ASUNTO", property = "asunto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHA", property = "fecha", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),	
		@Result(column = "TURNOGUARDIA", property = "turnoGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGO", property = "codigo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DATOSINTERES", property = "datosInteres", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONAASISTIDO", property = "idPersonaAsistido", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONACOLEGIADO", property = "idPersonaColegiado", jdbcType = JdbcType.VARCHAR)
	})
	AsuntosAsistenciaItem getAsuntoTipoAsistencia(AsuntosClaveJusticiableItem asuntoClave, String idLenguaje);

	 @UpdateProvider(type=ScsAsistenciaSqlExtendsProvider.class, method="eliminarRelacionAsistencia")
	    int eliminarRelacionAsistencia(String idinstitucion, String anio, String numero);

	 @UpdateProvider(type=ScsAsistenciaSqlExtendsProvider.class, method="eliminarRelacionAsistenciaDes")
	    int eliminarRelacionAsistenciaDes(String idinstitucion, String anio, String numero);


	@SelectProvider(type = ScsAsistenciaSqlExtendsProvider.class, method = "searchAsistenciasExpress")
	@Results({
			@Result(column = "anio", property = "anio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numero", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numeroasunto", property = "numeroAsunto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechaActuacion", property = "fchaActuacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechajustificacion", property = "fchaJustificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "observaciones", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechahora", property = "fechaAsistencia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "lugar", property = "lugar", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ejganio", property = "ejgAnio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ejgnumero", property = "ejgNumero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombre", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "apellido1", property = "apellido1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "apellido2", property = "apellido2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nif", property = "nif", jdbcType = JdbcType.VARCHAR),
			@Result(column = "sexo", property = "sexo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "comisariaJuzgado", property = "comisariaJuzgado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ejgidtipoejg", property = "idTipoEjg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechanacimiento", property = "fechaNacimiento", jdbcType = JdbcType.VARCHAR)

	})
	List<TarjetaAsistenciaItem2> searchAsistenciasExpress(FiltroAsistenciaItem filtroAsistenciaItem, Short idInstitucion);
	@SelectProvider(type = ScsAsistenciaSqlExtendsProvider.class, method = "getDelitosFromAsistencia")
	@Results({
		@Result(column = "iddelito", property = "idDelito", jdbcType = JdbcType.VARCHAR)
	})
	List<String> getDelitosFromAsistencia(String anio, String numero, String institucion);

	@SelectProvider(type = ScsAsistenciaSqlExtendsProvider.class, method = "searchAsistencias")
	@Results({
			@Result(column = "anio", property = "anio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numero", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechahora", property = "fechaAsistencia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idguardia", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idturno", property = "idTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombreGuardia", property = "descripcionGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombreTurno", property = "descripcionTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idpersonajg", property = "idPersonaJg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "asistido", property = "asistido", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idestadoasistencia", property = "estado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "estadoasistencia", property = "descripcionEstado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idpersonacolegiado", property = "idLetradoGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "letrado", property = "nombreColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "actuacionesvalidadas", property = "validada", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPOASISTENCIACOLEGIO", property = "idTipoAsistenciaColegio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechacierre", property = "fechaCierre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechasolicitud", property = "fechaSolicitud", jdbcType = JdbcType.VARCHAR),
			@Result(column = "REQUERIDAVALIDACION", property = "guardiaRequeridaValidacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "JUZGADO", property = "idJuzgado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR)
	})
	List<TarjetaAsistenciaResponseItem> searchAsistencias(FiltroAsistenciaItem filtroAsistenciaItem, Short idInstitucion, Integer idLenguaje, Integer tamMax);

	@SelectProvider(type = ScsAsistenciaSqlExtendsProvider.class, method = "getNextNumeroAsistencia")
	@Results({
		@Result(column = "numero", property = "numero", jdbcType = JdbcType.VARCHAR),

	})
	String getNextNumeroAsistencia(String anio, Short idInstitucion);

    @UpdateProvider(type=ScsAsistenciaSqlExtendsProvider.class, method="updateAsistenciaExpress")
    int updateAsistenciaExpress(ScsActuacionasistencia record);

	@SelectProvider(type = ScsAsistenciaSqlExtendsProvider.class, method = "searchListaContrarios")
	@Results({ @Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOSNOMBRE", property = "apellidosnombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE)})
	List<ListaContrarioJusticiableItem> searchListaContrarios(String anioNumero, Short idInstitucion, boolean mostrarHistorico);

	@SelectProvider(type = ScsAsistenciaSqlExtendsProvider.class, method = "getNumContrarios")
	@Results({ @Result(column = "contrarios", property = "valor", jdbcType = JdbcType.VARCHAR)})
	StringDTO getNumContrarios(String anioNumero, Short idInstitucion);

	@SelectProvider(type = ScsAsistenciaSqlExtendsProvider.class, method = "searchRelaciones")
	@Results({ @Result(column = "SJCS", property = "sjcs", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEJG", property = "numEjg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDLETRADO", property = "idletrado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LETRADO", property = "letrado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNODESIGNA", property = "idturnodesigna", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPO", property = "idtipo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGO", property = "codigo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DES_TURNO", property = "descturno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DES_TIPO", property = "destipo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "INTERESADO", property = "interesado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DICTAMEN", property = "dictamen", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHADICTAMEN", property = "fechadictamen", jdbcType = JdbcType.VARCHAR),
			@Result(column = "RESOLUCION", property = "resolucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHARESOLUCION", property = "fecharesolucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAASUNTO", property = "fechaasunto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DILNIGPROC", property = "dilnigproc", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IMPUGNACION", property = "impugnacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPODICTAMENEJG", property = "idTipoDictamenEJG", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DICTAMENOBS", property = "dictamenObs", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDFUNDAMENTOCALIF", property = "idFundamentoCalif", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR)})
	List<RelacionesItem> searchRelaciones(String anio, String num, Short idInstitucion, int idLenguaje, Integer tamMax);

	@SelectProvider(type= ScsAsistenciaSqlExtendsProvider.class, method="comboTipoDocumentosAsistencia")
	@Results({
			@Result(column="IDTIPODOCUMENTOASI", property="value", jdbcType=JdbcType.VARCHAR),
			@Result(column="NOMBRE", property="label", jdbcType=JdbcType.VARCHAR),
	})
	List<ComboItem> comboTipoDocumentosAsistencia();

	@SelectProvider(type= ScsAsistenciaSqlExtendsProvider.class, method="comboAsociadoAsistencia")
	@Results({
			@Result(column="ID", property="value", jdbcType=JdbcType.VARCHAR),
			@Result(column="DESCRIPCION", property="label", jdbcType=JdbcType.VARCHAR),
	})
	List<ComboItem> comboAsociadoAsistencia(String anio, String numero, Short idInstitucion, Integer idLenguaje);

	@SelectProvider(type= ScsAsistenciaSqlExtendsProvider.class, method="searchDocumentacion")
	@Results({
			@Result(column="iddocumentacionasi", property="idDocumentacion", jdbcType=JdbcType.VARCHAR),
			@Result(column="idtipodocumento", property="idTipoDoc", jdbcType=JdbcType.VARCHAR),
			@Result(column="idfichero", property="idFichero", jdbcType=JdbcType.VARCHAR),
			@Result(column="IDACTUACION", property="asociado", jdbcType=JdbcType.VARCHAR),
			@Result(column="observaciones", property="observaciones", jdbcType=JdbcType.VARCHAR),
			@Result(column="nombrefichero", property="nombreFichero", jdbcType=JdbcType.VARCHAR),
			@Result(column="fechaentrada", property="fechaEntrada", jdbcType=JdbcType.VARCHAR),
			@Result(column="idpersona", property="idPersona", jdbcType=JdbcType.VARCHAR),

	})
	List<DocumentacionAsistenciaItem> searchDocumentacion(String anio , String numero, Short idInstitucion, String idActuacion);

	@SelectProvider(type= ScsAsistenciaSqlExtendsProvider.class, method="comboOrigenContacto")
	@Results({
			@Result(column="ID", property="value", jdbcType=JdbcType.VARCHAR),
			@Result(column="DESCRIPCION", property="label", jdbcType=JdbcType.VARCHAR),
	})
	List<ComboItem> comboOrigenContacto(Short idInstitucion);

	@SelectProvider(type = ScsAsistenciaSqlExtendsProvider.class, method = "searchActuaciones")
	@Results({ @Result(column = "FECHA", property = "fechaActuacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDACTUACION", property = "idActuacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROASUNTO", property = "numeroAsunto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAJUSTIFICACION", property = "fechaJustificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "VALIDADA", property = "validada", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ANULACION", property = "anulada", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDFACTURACION", property = "idFacturacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FACTURADO", property = "facturada", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPOACTUACION", property = "tipoActuacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCIONACTUACION", property = "tipoActuacionDesc", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREFACTURACION", property = "facturacionDesc", jdbcType = JdbcType.VARCHAR)})
	List<ActuacionAsistenciaItem> searchActuaciones(String anio, String num, Short idInstitucion, int idLenguaje, String mostrarHistorico);

}
