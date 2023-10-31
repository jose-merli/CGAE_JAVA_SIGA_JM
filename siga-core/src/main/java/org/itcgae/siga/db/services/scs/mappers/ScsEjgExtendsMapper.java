package org.itcgae.siga.db.services.scs.mappers;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.itcgae.siga.DTOs.com.EnviosMasivosItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.AsuntosEjgItem;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableItem;
import org.itcgae.siga.DTOs.scs.ColegiadosSJCSItem;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.ExpInsosItem;
import org.itcgae.siga.DTOs.scs.ProcuradorItem;
import org.itcgae.siga.DTOs.scs.RelacionesItem;
import org.itcgae.siga.DTOs.scs.ResolucionEJGItem;
import org.itcgae.siga.db.mappers.ScsEjgMapper;
import org.itcgae.siga.db.services.scs.providers.ScsEjgSqlExtendsProvider;

public interface ScsEjgExtendsMapper extends ScsEjgMapper {

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "getAsuntoTipoEjg")
	@Results({ @Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ASUNTO", property = "asunto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHA", property = "fecha", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TURNOGUARDIA", property = "turnoGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONALETRADO", property = "idPersonaLetrado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONAJG", property = "idPersonajg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "EXPEDIENTEINSOSTENIBILIDAD", property = "expedienteInsostenibilidad", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DICTAMEN", property = "dictamen", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FUNDAMENTOCALIFICACION", property = "fundamentoCalificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TIPORESOLUCION", property = "tipoResolucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TIPOFUNDAMENTO", property = "tipoFundamento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TIPORESOLUCIONAUTO", property = "tipoResolucionAuto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TIPOSENTIDOAUTO", property = "tipoSentidoAuto", jdbcType = JdbcType.VARCHAR), })
	AsuntosEjgItem getAsuntoTipoEjg(AsuntosClaveJusticiableItem asuntoClave, String idLenguaje);

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "searchClaveAsuntosEJG")
	@Results({ @Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "ASUNTO", property = "asunto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TURNOGUARDIA", property = "turnoGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "INTERESADO", property = "interesado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LETRADO", property = "letrado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAAPERTURA", property = "fecha", jdbcType = JdbcType.DATE),
			@Result(column = "NUMEROPROCEDIMIENTO", property = "numProcedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NIG", property = "nig", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPOEJG", property = "idTipoEjg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TIPOEJG", property = "tipo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "dilnigproc", property = "dilnigproc", jdbcType = JdbcType.VARCHAR)
	})
	List<AsuntosJusticiableItem> searchClaveAsuntosEJG(AsuntosJusticiableItem asuntosJusticiableItem, Integer tamMaximo,
													   String idLenguaje);

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "comboCreadoDesde")
	@Results({ @Result(column = "ORIGENAPERTURA", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR) })
	List<ComboItem> comboCreadoDesde(String idlenguaje, String string);

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "busquedaEJG")
	@Results({

			@Result(column = "anio", property = "annio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numero", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idTipoEjg", property = "tipoEJG", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idInstitucion", property = "idInstitucion", jdbcType = JdbcType.INTEGER),

	})
	List<EjgItem> busquedaEJG(EjgItem ejgItem, String idInstitucion, Integer tamMaximo, String idLenguaje);
	
	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "busquedaEJGFinal")
	@Results({

			@Result(column = "anio", property = "annio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idtipoejg", property = "tipoEJG", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numero", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numejg", property = "numEjg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idInstitucion", property = "idInstitucion", jdbcType = JdbcType.INTEGER),
			@Result(column = "NUMANIO", property = "numAnnioProcedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TURNO", property = "turno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TURNOGUARDIA", property = "turnoDes", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechaapertura", property = "fechaApertura", jdbcType = JdbcType.DATE),
			@Result(column = "fechamodificacion", property = "fechaModificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRESOLICITANTE", property = "nombreApeSolicitante", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREletrado", property = "apellidosYNombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NCOLEGIADO", property = "numColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ESTADOEJG", property = "estadoEJG", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numeroprocedimiento", property = "procedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idpersonajg", property = "idPersona", jdbcType = JdbcType.INTEGER)

	})
	List<EjgItem> busquedaEJGFinal(EjgItem ejgItem, String idInstitucion, Integer tamMaximo, String idLenguaje, String stringListaEjgs);

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "datosEJG")
	@Results({

			@Result(column = "anio", property = "annio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idtipoejg", property = "tipoEJG", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numero", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numejg", property = "numEjg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMANIO", property = "numAnnioProcedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idtipoEJGColegio", property = "tipoEJGColegio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TURNO", property = "turno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TURNOGUARDIA", property = "turnoDes", jdbcType = JdbcType.VARCHAR),
			@Result(column = "GUARDIA", property = "guardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechaapertura", property = "fechaApertura", jdbcType = JdbcType.DATE),
			@Result(column = "fechamodificacion", property = "fechaModificacion", jdbcType = JdbcType.DATE),
			@Result(column = "NOMBRESOLICITANTE", property = "nombreApeSolicitante", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APESOLICITANTE", property = "apellidos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SOLONOMBRESOLIC", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombreletrado", property = "apellidosYNombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NCOLEGIADO", property = "numColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ESTADOEJG", property = "estadoEJG", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numeroprocedimiento", property = "procedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "dictamen", property = "dictamenSing", jdbcType = JdbcType.VARCHAR),
			@Result(column = "resolucion", property = "resolucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "resolauto", property = "impugnacionDesc", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechapresentacion", property = "fechapresentacion", jdbcType = JdbcType.DATE),
			@Result(column = "fechalimitepresentacion", property = "fechalimitepresentacion", jdbcType = JdbcType.DATE),
			@Result(column = "anioexpediente", property = "anioexpInsos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numeroexpediente", property = "numeroexpInsos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idtipoexpediente", property = "idTipoExpInsos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idInstitucion_TipoExpediente", property = "idInstTipoExp", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechaauto", property = "fechaAuto", jdbcType = JdbcType.DATE),
			@Result(column = "idtiporesolauto", property = "autoResolutorio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idtiposentidoauto", property = "sentidoAuto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "observacionimpugnacion", property = "observacionesImpugnacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numeroresolucion", property = "nImpugnacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechapublicacion", property = "fechaPublicacion", jdbcType = JdbcType.DATE),
			@Result(column = "bisresolucion", property = "bis", jdbcType = JdbcType.VARCHAR),
			@Result(column = "turnadoratificacion", property = "requiereTurn", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numerodesigna", property = "numDesigna", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONAjg", property = "idPersonajg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idsituacion", property = "idsituacion", jdbcType = JdbcType.INTEGER),
			@Result(column = "numerodiligencia", property = "numerodiligencia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "comisaria", property = "comisaria", jdbcType = JdbcType.INTEGER),
			@Result(column = "idpreceptivo", property = "perceptivo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "calidad", property = "calidad", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idrenuncia", property = "renuncia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "juzgado", property = "juzgado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nig", property = "nig", jdbcType = JdbcType.VARCHAR),
			@Result(column = "delitos", property = "delitos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idprocurador", property = "idProcurador", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idinstitucion_proc", property = "idInstitucionProc", jdbcType = JdbcType.INTEGER),
			@Result(column = "idPretension", property = "idPretension", jdbcType = JdbcType.INTEGER),
			@Result(column = "fecha_des_proc", property = "fechaDesProc", jdbcType = JdbcType.DATE),
			@Result(column = "numerodesignaproc", property = "numerodesignaproc", jdbcType = JdbcType.VARCHAR),
			@Result(column = "iddictamen", property = "iddictamen", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechaDictamen", property = "fechaDictamen", jdbcType = JdbcType.DATE),
			@Result(column = "idTipoDictamenEJG", property = "idTipoDictamen", jdbcType = JdbcType.INTEGER),
			@Result(column = "IDFUNDAMENTOCALIF", property = "fundamentoCalif", jdbcType = JdbcType.INTEGER),
			@Result(column = "observacionesDictamen", property = "dictamen", jdbcType = JdbcType.CLOB),
			@Result(column = "identificadords", property = "identificadords", jdbcType = JdbcType.VARCHAR),
			@Result(column = "observaciones", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idexpedienteext", property = "idExpedienteExt", jdbcType = JdbcType.INTEGER)})
	List<EjgItem> datosEJG(EjgItem ejgItem, String idInstitucion, String idLenguaje);
	
	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "datosEJGJustificacionExpres")
	@Results({

			@Result(column = "anio", property = "annio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idtipoejg", property = "tipoEJG", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numero", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numejg", property = "numEjg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMANIO", property = "numAnnioProcedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idtipoEJGColegio", property = "tipoEJGColegio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TURNO", property = "turno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TURNOGUARDIA", property = "turnoDes", jdbcType = JdbcType.VARCHAR),
			@Result(column = "GUARDIA", property = "guardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechaapertura", property = "fechaApertura", jdbcType = JdbcType.DATE),
			@Result(column = "fechamodificacion", property = "fechaModificacion", jdbcType = JdbcType.DATE),
			@Result(column = "NOMBRESOLICITANTE", property = "nombreApeSolicitante", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APESOLICITANTE", property = "apellidos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SOLONOMBRESOLIC", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombreletrado", property = "apellidosYNombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NCOLEGIADO", property = "numColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ESTADOEJG", property = "estadoEJG", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numeroprocedimiento", property = "procedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "dictamen", property = "dictamenSing", jdbcType = JdbcType.VARCHAR),
			@Result(column = "resolucion", property = "resolucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "resolauto", property = "impugnacionDesc", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechapresentacion", property = "fechapresentacion", jdbcType = JdbcType.DATE),
			@Result(column = "fechalimitepresentacion", property = "fechalimitepresentacion", jdbcType = JdbcType.DATE),
			@Result(column = "anioexpediente", property = "anioexpInsos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numeroexpediente", property = "numeroexpInsos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idtipoexpediente", property = "idTipoExpInsos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idInstitucion_TipoExpediente", property = "idInstTipoExp", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechaauto", property = "fechaAuto", jdbcType = JdbcType.DATE),
			@Result(column = "idtiporesolauto", property = "autoResolutorio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idtiposentidoauto", property = "sentidoAuto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "observacionimpugnacion", property = "observacionesImpugnacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numeroresolucion", property = "nImpugnacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechapublicacion", property = "fechaPublicacion", jdbcType = JdbcType.DATE),
			@Result(column = "bisresolucion", property = "bis", jdbcType = JdbcType.VARCHAR),
			@Result(column = "turnadoratificacion", property = "requiereTurn", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numerodesigna", property = "numDesigna", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONAjg", property = "idPersonajg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idsituacion", property = "idsituacion", jdbcType = JdbcType.INTEGER),
			@Result(column = "numerodiligencia", property = "numerodiligencia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "comisaria", property = "comisaria", jdbcType = JdbcType.INTEGER),
			@Result(column = "idpreceptivo", property = "perceptivo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "calidad", property = "calidad", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idrenuncia", property = "renuncia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "juzgado", property = "juzgado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nig", property = "nig", jdbcType = JdbcType.VARCHAR),
			@Result(column = "delitos", property = "delitos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idprocurador", property = "idProcurador", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idinstitucion_proc", property = "idInstitucionProc", jdbcType = JdbcType.INTEGER),
			@Result(column = "idPretension", property = "idPretension", jdbcType = JdbcType.INTEGER),
			@Result(column = "fecha_des_proc", property = "fechaDesProc", jdbcType = JdbcType.DATE),
			@Result(column = "numerodesignaproc", property = "numerodesignaproc", jdbcType = JdbcType.VARCHAR),
			@Result(column = "iddictamen", property = "iddictamen", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechaDictamen", property = "fechaDictamen", jdbcType = JdbcType.DATE),
			@Result(column = "idTipoDictamenEJG", property = "idTipoDictamen", jdbcType = JdbcType.INTEGER),
			@Result(column = "IDFUNDAMENTOCALIF", property = "fundamentoCalif", jdbcType = JdbcType.INTEGER),
			@Result(column = "observacionesDictamen", property = "dictamen", jdbcType = JdbcType.CLOB),
			@Result(column = "identificadords", property = "identificadords", jdbcType = JdbcType.VARCHAR),
			@Result(column = "observaciones", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idexpedienteext", property = "idExpedienteExt", jdbcType = JdbcType.INTEGER)})
	List<EjgItem> datosEJGJustificacionExpres(EjgItem ejgItem, String idInstitucion, String idLenguaje);

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "getDictamen")
	@Results({ @Result(column = "fechadictamen", property = "fechaDictamen", jdbcType = JdbcType.DATE),
			@Result(column = "observaciones", property = "observacionesDictamen", jdbcType = JdbcType.VARCHAR),
			@Result(column = "dictamen", property = "dictamenSing", jdbcType = JdbcType.VARCHAR),
			@Result(column = "iddictamen", property = "iddictamen", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fundamento", property = "fundamentoCalifDes", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idfundamento", property = "fundamentoCalif", jdbcType = JdbcType.VARCHAR) })
	EjgItem getDictamen(EjgItem ejgItem, String idInstitucion, String idLenguaje);

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "getResolucion")
	@Results({ @Result(column = "anioacta", property = "annioActa", jdbcType = JdbcType.INTEGER),
			@Result(column = "idacta", property = "idActa", jdbcType = JdbcType.INTEGER),
			@Result(column = "idtiporatificacionejg", property = "idTiporatificacionEJG", jdbcType = JdbcType.INTEGER),
			@Result(column = "idfundamentojuridico", property = "idFundamentoJuridico", jdbcType = JdbcType.INTEGER),
			@Result(column = "ratificaciondictamen", property = "ratificacionDictamen", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idorigencajg", property = "idOrigencajg", jdbcType = JdbcType.INTEGER),
			@Result(column = "aniocajg", property = "anioCAJG", jdbcType = JdbcType.INTEGER),
			@Result(column = "numero_cajg", property = "numeroCAJG", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idponente", property = "idPonente", jdbcType = JdbcType.INTEGER),
			@Result(column = "fechapresentacionponente", property = "fechaPresentacionPonente", jdbcType = JdbcType.DATE),
			@Result(column = "fecharesolucioncajg", property = "fechaResolucionCAJG", jdbcType = JdbcType.DATE),
			@Result(column = "fecharatificacion", property = "fechaRatificacion", jdbcType = JdbcType.DATE),
			@Result(column = "fechanotificacion", property = "fechaNotificacion", jdbcType = JdbcType.DATE),
			@Result(column = "refauto", property = "refAuto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idannioacta", property = "idAnnioActa", jdbcType = JdbcType.VARCHAR),
			@Result(column = "turnadoratificacion", property = "turnadoRatificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "requierenotificarproc", property = "requiereNotificarProc", jdbcType = JdbcType.VARCHAR),
			@Result(column = "notascajg", property = "notasCAJG", jdbcType = JdbcType.VARCHAR),
			@Result(column = "docresolucion", property = "docResolucion", jdbcType = JdbcType.VARCHAR),

	})
	ResolucionEJGItem getResolucion(EjgItem ejgItem, String idInstitucion, String idLenguaje);

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "busquedaColegiadoEJG")
	@Results({ @Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS", property = "apellidos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NCOLEGIADO", property = "nColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NCOMUNITARIO", property = "nComunitario", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDESTADO", property = "idEstado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ESTADO", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "RESIDENTE", property = "residente", jdbcType = JdbcType.BOOLEAN),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ABREVIATURA", property = "abreviatura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idPersona", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "tieneguardias", property = "tieneGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "guardiaspendientes", property = "guardiasPendientes", jdbcType = JdbcType.VARCHAR),
			@Result(column = "tieneturno", property = "tieneTurno", jdbcType = JdbcType.VARCHAR) })
	List<ColegiadosSJCSItem> busquedaColegiadoEJG(ColegiadosSJCSItem item, String idLenguaje, Integer tamMaximo);

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "tieneGuardias")
	@Results({ @Result(column = "idGuardia", property = "tieneGuardia", jdbcType = JdbcType.VARCHAR), })
	List<String> tieneGuardias(String idInstitucion, ColegiadosSJCSItem colegiadosSJCSItem);

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "tieneTurnos")
	@Results({ @Result(column = "idTurno", property = "tieneTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idPersona", property = "idPersona", jdbcType = JdbcType.VARCHAR), })
	List<ColegiadosSJCSItem> tieneTurnos(String idInstitucion, String idPersona);

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "tieneGuardiasPendientes")
	@Results({ @Result(column = "idGuardia", property = "tieneGuardia", jdbcType = JdbcType.VARCHAR), })
	String tieneGuardiasPendientes(String idInstitucion, ColegiadosSJCSItem colegiadosSJCSItem, String idGuardia);

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "getNumeroEJG")
	String getNumeroEJG(short idTipoEJG, short anio, short idInstitucion);

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "getNumero")
	String getNumero(short idTipoEJG, short anio, short idInstitucion);

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "getRelacionesEJG")
	@Results({ @Result(column = "SJCS", property = "sjcs", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDLETRADO", property = "idletrado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRELETRADO", property = "letrado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "INTERESADO", property = "interesado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPO", property = "idtipo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGO", property = "codigo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DES_TURNO", property = "descturno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DES_TIPO", property = "destipo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHA", property = "fechaasunto", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "RESOLUCION", property = "resolucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "dilnigproc", property = "dilnigproc", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDSJCS", property = "idsjcs", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONAJG", property = "idpersonajg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechadesignacion", property = "fechaDesignacion", jdbcType = JdbcType.TIMESTAMP),

	})
	List<RelacionesItem> getRelacionesEJG(EjgItem item);
	
	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "getRelacionesEJGBusqueda")
	@Results({ @Result(column = "SJCS", property = "sjcs", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDLETRADO", property = "idletrado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRELETRADO", property = "letrado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "INTERESADO", property = "interesado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPO", property = "idtipo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGO", property = "codigo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DES_TURNO", property = "descturno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DES_TIPO", property = "destipo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHA", property = "fechaasunto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "RESOLUCION", property = "resolucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "dilnigproc", property = "dilnigproc", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDSJCS", property = "idsjcs", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONAJG", property = "idpersonajg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechadesignacion", property = "fechaDesignacion", jdbcType = JdbcType.TIMESTAMP),

	})
	List<RelacionesItem> getRelacionesEJGBusqueda(EjgItem item);
	
	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "getEjgDesignas")
	@Results({ @Result(column = "ANIODESIGNA", property = "anioDesigna", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NUMERODESIGNA", property = "numeroDesigna", jdbcType = JdbcType.NUMERIC),

	})
	List<EjgItem>  getEjgDesignas(EjgItem item);

	/**
	 * getComunicaciones
	 *
	 * @param
	 * @param idInstitucion
	 * @param idLenguaje
	 * @return
	 */
	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "getComunicaciones")
	@Results({ @Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "IDENVIO", property = "idEnvio", jdbcType = JdbcType.NUMERIC),
			@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHA", property = "fechaCreacion", jdbcType = JdbcType.DATE),
			@Result(column = "IDPLANTILLAENVIOS", property = "idPlantillaEnvios", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDESTADO", property = "idEstado", jdbcType = JdbcType.NUMERIC),
			@Result(column = "IDTIPOENVIOS", property = "idTipoEnvios", jdbcType = JdbcType.NUMERIC),
		    @Result(column = "NOMBRECLASE", property = "claseComunicacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREPLANTILLA", property = "nombrePlantilla", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPLANTILLA", property = "idPlantilla", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAPROGRAMADA", property = "fechaProgramada", jdbcType = JdbcType.DATE),
			@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
			@Result(column = "ASUNTO", property = "asunto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CUERPO", property = "cuerpo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TIPOENVIO", property = "tipoEnvio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESTINATARIO", property = "destinatario", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ESTADOENVIO", property = "estadoEnvio", jdbcType = JdbcType.VARCHAR) })
	List<EnviosMasivosItem> getComunicaciones(String num, String anio, String idTipo, Short idInstitucion,
											  String idLenguaje);

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "busquedaProcuradorEJG")
	@Results({ @Result(column = "NCOLEGIADO", property = "nColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS1", property = "apellido1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS2", property = "apellido2", jdbcType = JdbcType.VARCHAR),
	})
	List<ProcuradorItem> busquedaProcuradorEJG(String idProcurador, String idinstitucion);// String num, String idturno,

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "getNewIdDocumentacionEjg")
	@Results({ @Result(column = "ID", property = "idMax", jdbcType = JdbcType.VARCHAR) })
	MaxIdDto getNewIdDocumentacionEjg(Short idInstitucion);
	
	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "getMaxNumEjg")
	String getMaxNumEjg(Short idInstitucion, String anio, String idTipo);

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "getObservacionEstadoEjgDictamen")
	@Results({ @Result(column = "observaciones", jdbcType = JdbcType.VARCHAR), })
	String getObservacionEstadoEjgDictamen(Short idInstitucion, String idLenguaje, Short idTipoDictamen);

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "getObservacionEstadoEjgPonente")
	@Results({ @Result(column = "observaciones", jdbcType = JdbcType.VARCHAR), })
	String getObservacionEstadoEjgPonente(Short idInstitucion, String idLenguaje, Integer idPonente);

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "getObservacionEstadoEjgResol")
	@Results({ @Result(column = "observaciones", jdbcType = JdbcType.VARCHAR), })
	String getObservacionEstadoEjgResol(String idLenguaje, Short idTiporatificacionEJG);

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "getObservacionEstadoEjgImpug")
	@Results({ @Result(column = "observaciones", jdbcType = JdbcType.VARCHAR), })
	String getObservacionEstadoEjgImpug(String idLenguaje, String idTiporesolauto);

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "getObservacionEstadoIniInsertEjg")
	@Results({ @Result(column = "observaciones", jdbcType = JdbcType.VARCHAR), })
	String getObservacionEstadoIniInsertEjg(String idLenguaje);

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "getObservacionEstadoEjgResolDev")
	@Results({ @Result(column = "observaciones", jdbcType = JdbcType.VARCHAR), })
	String getObservacionEstadoEjgResolDev(String idLenguaje);

	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "getIdEcomCola")
	int getIdEcomCola();

	/**
	 * getDatosExpInsos
	 *
	 * @param
	 * @return
	 */
	@SelectProvider(type = ScsEjgSqlExtendsProvider.class, method = "getDatosExpInsos")
	@Results({ @Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEJG", property = "numEJG", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ANIO", property = "anioEJG", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPOEJG", property = "idTipoEJG", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPROCEDIMIENTO", property = "idProcedimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDJUZGADO", property = "idJuzgado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPRETENSION", property = "idPretension", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONASOLICITANTE", property = "idpersonasolicitante", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NIFSOLICITANTE", property = "nifsolicitante", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRESOLICITANTE", property = "nombresolicitante", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDO1SOLICITANTE", property = "apellido1solicitante", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDO2SOLICITANTE", property = "apellido2solicitante", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMPROCEDIMIENTO", property = "numprocedimiento", jdbcType = JdbcType.VARCHAR),
	})
	List<ExpInsosItem> getDatosExpInsos(EjgItem item);
}