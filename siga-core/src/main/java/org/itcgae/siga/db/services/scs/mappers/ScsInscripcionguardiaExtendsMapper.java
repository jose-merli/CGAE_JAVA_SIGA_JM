package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.BusquedaInscripcionItem;
import org.itcgae.siga.DTOs.scs.BusquedaInscripcionMod;
import org.itcgae.siga.DTOs.scs.CargaMasivaDatosITItem;
import org.itcgae.siga.DTOs.scs.GestionInscripcion;
import org.itcgae.siga.DTOs.scs.GuardiasTurnosItem;
import org.itcgae.siga.DTOs.scs.InscripcionDatosEntradaDTO;
import org.itcgae.siga.DTOs.scs.InscripcionGuardiaItem;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaItem;
import org.itcgae.siga.DTOs.scs.TrabajosSJCSInsGuardiaItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.ScsInscripcionguardia;
import org.itcgae.siga.db.entities.ScsInscripcionguardiaKey;
import org.itcgae.siga.db.mappers.ScsInscripcionguardiaMapper;
import org.itcgae.siga.db.services.scs.providers.ScsGuardiasturnoSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsInscripcionguardiaSqlExtendsProvider;

public interface ScsInscripcionguardiaExtendsMapper extends ScsInscripcionguardiaMapper{


	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "getColaGuardias")
	@Results({
		@Result(column = "NUMEROGRUPO", property="numeroGrupo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGRUPOGUARDIACOLEGIADO", property = "idGrupoGuardiaColegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ORDENGRUPO", property = "orden", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ORDENINSC", property = "ordenBD", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMEROCOLEGIADO", property = "nColegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAVALIDACION", property = "fechaValidacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "COMPENSACIONES", property = "compensaciones", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SALTOS", property = "saltos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS1", property = "apellido1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS2", property = "apellido2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombre", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "orden_cola", property = "ordenCola", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "GRUPO", property = "idGrupoGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "Fechasuscripcion", property = "fechaSuscripcion", jdbcType = JdbcType.TIMESTAMP),
		@Result(column = "IDGRUPOGUARDIA_ULTIMO", property = "ultimoCola", jdbcType = JdbcType.NUMERIC)
	})
	List<InscripcionGuardiaItem> getColaGuardias(String idGuardia, String idTurno, String fecha,String ultimo,String ordenaciones, String idInstitucion, String idgrupoguardia, Boolean porGrupos);

	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "getColaGuardiasNueva")
	@Results({
		@Result(column = "NUMEROGRUPO", property="numeroGrupo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGRUPOGUARDIACOLEGIADO", property = "idGrupoGuardiaColegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ORDENGRUPO", property = "orden", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ORDENINSC", property = "ordenBD", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMEROCOLEGIADO", property = "nColegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAVALIDACION", property = "fechaValidacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "COMPENSACIONES", property = "compensaciones", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SALTOS", property = "saltos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS1", property = "apellido1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS2", property = "apellido2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombre", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "orden_cola", property = "ordenCola", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "GRUPO", property = "idGrupoGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "Fechasuscripcion", property = "fechaSuscripcion", jdbcType = JdbcType.TIMESTAMP),
		@Result(column = "IDGRUPOGUARDIA_ULTIMO", property = "ultimoCola", jdbcType = JdbcType.NUMERIC)
	})
	List<InscripcionGuardiaItem> getColaGuardiasNueva(String idGuardia, String idTurno, String ordenaciones, String fecha, String posicionColaUltimo, String idInstitucion);
	
	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "getColaGuardiasByNumColegiado")
	@Results({
		@Result(column = "NUMEROGRUPO", property="numeroGrupo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGRUPOGUARDIACOLEGIADO", property = "idGrupoGuardiaColegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ORDENGRUPO", property = "orden", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ORDENINSC", property = "ordenBD", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMEROCOLEGIADO", property = "nColegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAVALIDACION", property = "fechaValidacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "COMPENSACIONES", property = "compensaciones", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SALTOS", property = "saltos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS1", property = "apellido1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS2", property = "apellido2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombre", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "orden_cola", property = "ordenCola", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "GRUPO", property = "idGrupoGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "Fechasuscripcion", property = "fechaSuscripcion", jdbcType = JdbcType.TIMESTAMP),
		@Result(column = "IDGRUPOGUARDIA_ULTIMO", property = "ultimoCola", jdbcType = JdbcType.NUMERIC)
	})
	List<InscripcionGuardiaItem> getColaGuardiasByNumColegiado(String idGuardia, String idTurno, String fechaIni, String fechaFin, String idInstitucion, String numCol);

	
	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "searchGrupo")
	@Results({
		@Result(column = "NUMEROGRUPO", property = "grupo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ORDEN", property = "orden", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR)
	})
	List<CargaMasivaDatosITItem> searchGrupo(String grupo, Short idInstitucion);
	
	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "searchGrupoGuardia")
	@Results({
		@Result(column = "NUMEROGRUPO", property = "grupo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ORDEN", property = "orden", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR)
	})
	List<CargaMasivaDatosITItem> searchGrupoGuardia(Short idInstitucion, String idGuardia, String idPersona);
	
	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "comboGuardiasInscritoLetrado")
	@Results({
		@Result(column = "IDGUARDIA", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> comboGuardiasInscritoLetrado(Short idInstitucion, String idPersona, String idTurno);
	
	@InsertProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "insertarInscripcion")
	int insertarInscripcion(Short idInstitucion, BusquedaInscripcionItem inscripcion, AdmUsuarios admUsuarios);

	/*@Results({
		@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHASUSCRIPCION", property = "fechasolicitud", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.DATE),
		@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.DATE),
		@Result(column = "OBSERVACIONESSUSCRIPCION", property = "observacionessuscripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "OBSERVACIONESBAJA", property = "observacionesbaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHASOLICITUDBAJA", property = "fechasolicitudbaja", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAVALIDACION", property = "fechavalidacion", jdbcType = JdbcType.DATE),
		@Result(column = "OBSERVACIONESVALIDACION", property = "observacionesvalidacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHADENEGACION", property = "fechadenegacion", jdbcType = JdbcType.DATE),
		@Result(column = "OBSERVACIONESDENEGACION", property = "observacionesdenegacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "OBSERVACIONESVALBAJA", property = "observacionesvalbaja", jdbcType = JdbcType.VARCHAR)
	})*/
	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "buscarInscripcion")
	List<InscripcionGuardiaItem> buscarInscripcion(Short idInstitucion, BusquedaInscripcionItem inscripcion,
			AdmUsuarios admUsuarios);

	@Results({
		@Result(column = "idguardia", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idturno", property = "idTurno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "numerogrupo", property = "numeroGrupo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "colegiado", property = "colegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "colegiado_grupo", property = "colegiadoGrupo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "letrado", property = "letrado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombre_turno", property = "nombreTurno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idzona", property = "idZona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombre_zona", property = "nombreZona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idsubzona", property = "idSubzona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombre_subzona", property = "nombreSubzona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idarea", property = "idArea", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombre_area", property = "nombreArea", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idmateria", property = "idMateria", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombre_materia", property = "nombreMateria", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombre_guardia", property = "nombreGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "descripcion_tipo_guardia", property = "descripcionNombreGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "obligatoriedad_inscripcion", property = "obligatoriedadInscripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "descripcion_obligatoriedad", property = "descripcionObligatoriedad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechasuscripcion", property = "fechasolicitud", jdbcType = JdbcType.VARCHAR)
	})
	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "inscripcionesDisponibles")
	List<GestionInscripcion> inscripcionesDisponibles(Short idInstitucion, AdmUsuarios admUsuarios, BusquedaInscripcionItem inscripcion);
	
	
	 @SelectProvider(type=ScsInscripcionguardiaSqlExtendsProvider.class, method="busquedaTarjetaInscripcionesGuardia")
		@Results({
			@Result(column = "numerogrupo", property = "numeroGrupo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "colegiado", property = "colegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "colegiado_grupo", property = "colegiadoGrupo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "letrado", property = "letrado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechasuscripcion", property = "fechasolicitud", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "idguardia", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombre_guardia", property = "nombreGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombre_turno", property = "nombreTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombre_zona", property = "nombreZona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombre_subzona", property = "nombreSubzona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombre_area", property = "nombreArea", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombre_materia", property = "nombreMateria", jdbcType = JdbcType.VARCHAR),
			@Result(column = "obligatoriedad_inscripcion", property = "obligatoriedadInscripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idturno", property = "idTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idzona", property = "idZona", jdbcType = JdbcType.VARCHAR),		
			@Result(column = "idsubzona", property = "idSubzona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idarea", property = "idArea", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idmateria", property = "idMateria", jdbcType = JdbcType.VARCHAR),
			@Result(column = "descripcion_tipo_guardia", property = "descripcionNombreGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "descripcion_obligatoriedad", property = "descripcionObligatoriedad", jdbcType = JdbcType.VARCHAR)
			
		})
	    List<GestionInscripcion> busquedaTarjetaInscripcionesGuardia(Short idInstitucion, AdmUsuarios admUsuarios, BusquedaInscripcionItem inscripcion);
	 
	 @SelectProvider(type=ScsInscripcionguardiaSqlExtendsProvider.class, method="busquedaTarjetaInscripcionesTurnosConGuardia")
		@Results({
			@Result(column = "numerogrupo", property = "numeroGrupo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "colegiado", property = "colegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "colegiado_grupo", property = "colegiadoGrupo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "letrado", property = "letrado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechasuscripcion", property = "fechasolicitud", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "idguardia", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombre_guardia", property = "nombreGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombre_turno", property = "nombreTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombre_zona", property = "nombreZona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombre_subzona", property = "nombreSubzona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombre_area", property = "nombreArea", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nombre_materia", property = "nombreMateria", jdbcType = JdbcType.VARCHAR),
			@Result(column = "obligatoriedad_inscripcion", property = "obligatoriedadInscripcion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idturno", property = "idTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idzona", property = "idZona", jdbcType = JdbcType.VARCHAR),		
			@Result(column = "idsubzona", property = "idSubzona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idarea", property = "idArea", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idmateria", property = "idMateria", jdbcType = JdbcType.VARCHAR),
			@Result(column = "descripcion_tipo_guardia", property = "descripcionNombreGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "descripcion_obligatoriedad", property = "descripcionObligatoriedad", jdbcType = JdbcType.VARCHAR)
			
		})
	    List<GestionInscripcion> busquedaTarjetaInscripcionesTurnosConGuardia(Short idInstitucion, AdmUsuarios admUsuarios, BusquedaInscripcionItem inscripcion);

	@Results({
		@Result(column = "idguardia", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idturno", property = "idTurno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "numerogrupo", property = "numeroGrupo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "colegiado", property = "colegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "colegiado_grupo", property = "colegiadoGrupo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "letrado", property = "letrado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombre_turno", property = "nombreTurno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idzona", property = "idZona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombre_zona", property = "nombreZona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idsubzona", property = "idSubzona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombre_subzona", property = "nombreSubzona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idarea", property = "idArea", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombre_area", property = "nombreArea", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idmateria", property = "idMateria", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombre_materia", property = "nombreMateria", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombre_guardia", property = "nombreGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "descripcion_tipo_guardia", property = "descripcionNombreGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "obligatoriedad_inscripcion", property = "obligatoriedadInscripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "descripcion_obligatoriedad", property = "descripcionObligatoriedad", jdbcType = JdbcType.VARCHAR),
		
	})
	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "inscripcionPorguardia")
	List<GestionInscripcion> inscripcionPorguardia(Short idInstitucion, AdmUsuarios admUsuarios, String guardia, String idpersona);


	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "listadoInscripciones")
	@Results({ 
		@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL),
		@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
		@Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.DECIMAL),
		@Result(column = "NOMBREGUARDIA", property = "nombreGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGUARDIA", property = "idguardia", jdbcType = JdbcType.DECIMAL),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.DATE),
		@Result(column = "OBSERVACIONESBAJA", property = "observacionesbaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHASOLICITUDBAJA", property = "fechasolicitudbaja", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAVALIDACION", property = "fechavalidacion", jdbcType = JdbcType.DATE),
		@Result(column = "OBSERVACIONESVALIDACION", property = "observacionesvalidacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHADENEGACION", property = "fechadenegacion", jdbcType = JdbcType.DATE),
		@Result(column = "OBSERVACIONESDENEGACION", property = "observacionesdenegacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "OBSERVACIONESVALBAJA", property = "observacionesvalbaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHASUSCRIPCION", property = "fechaSol", jdbcType = JdbcType.VARCHAR),
		@Result(column = "estado", property = "estado", jdbcType = JdbcType.VARCHAR),
	})
	List<BusquedaInscripcionItem> getListadoInscripciones(InscripcionDatosEntradaDTO inscripciones, String idInstitucion, Integer tamMax);
	
	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "getLastInscripciones")
	@Results({ 
		@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
		@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL),
		@Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.DECIMAL),
		@Result(column = "IDGUARDIA", property = "idguardia", jdbcType = JdbcType.DECIMAL),
		@Result(column = "FECHASUSCRIPCION", property = "fechasuscripcion", jdbcType = JdbcType.DATE),
		@Result(column = "OBSERVACIONESSUSCRIPCION", property = "observacionessuscripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.DATE),
		@Result(column = "OBSERVACIONESBAJA", property = "observacionesbaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHASOLICITUDBAJA", property = "fechasolicitudbaja", jdbcType = JdbcType.DATE),
		@Result(column = "OBSERVACIONESVALBAJA", property = "observacionesvalbaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAVALIDACION", property = "fechavalidacion", jdbcType = JdbcType.DATE),
		@Result(column = "OBSERVACIONESVALIDACION", property = "observacionesvalidacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHADENEGACION", property = "fechadenegacion", jdbcType = JdbcType.DATE),
		@Result(column = "OBSERVACIONESDENEGACION", property = "observacionesdenegacion", jdbcType = JdbcType.VARCHAR)
	})
    ScsInscripcionguardia getLastInscripciones(String idGuardia, String idTurno, String idPersona, String idInstitucion);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getSaltoCompensacionesActivo")
	@Results({})
	List<SaltoCompGuardiaItem> getBuscarSaltoCompensancion(String idInstitucion, String idturno, String idguardia, String idpersona,String saltocompensacion);

	@DeleteProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "eliminarSaltoCompensacion")
	int getEliminarSaltoCompensancion(String idinstitucion, String idturno, String idguardia, String idpersona, String saltooCompensacion);

	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "busquedaTrabajosGuardias")
	@Results({})
	List<TrabajosSJCSInsGuardiaItem> busquedaTrabajosGuardias(String idpersona,String idturno,String idguardia ,Short idInstitucion,String fechaActual);
	
	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "busquedaTrabajosPendientes")
	@Results({})
	List<TrabajosSJCSInsGuardiaItem> busquedaTrabajosPendientes(String idpersona,String idturno ,Short idInstitucion,String fechaActual);



	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "buscarGuardiasAsocTurnos")
	@Results({ 
	})
	List<GuardiasTurnosItem> getbuscarGuardiasAsocTurnos(String string, String idturno, String idguardia, String idpersona);



	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "validarInscripcionesCampo")
	@Results({ 
	})
	String validarInscripcionesCampo(String string, String idturno, String idguardia, String idpersona);



	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "requeridaValidacionCampo")
	@Results({ 
	})
	String requeridaValidacionCampo(String string, String idturno, String idguardia, String idpersona);
	
	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "getPosicionUltimoColaGuardia")
	@Results({ 
	})
	String getPosicionUltimoColaGuardia(String idTurno, String idGuardia, String idInstitucion, String ordenaciones, String fecha);

	@UpdateProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "UpdateInscripcionTurno")
	int UpdateInscripcionTurno(String string, String idturno, BusquedaInscripcionMod a, String fECHABAJA);



	@UpdateProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "UpdateInscripcionGuardia")
	int UpdateInscripcionGuardia(String string, String idturno, String idguardia,BusquedaInscripcionMod a, String fECHABAJA);
    
    @SelectProvider(type=ScsInscripcionguardiaSqlExtendsProvider.class, method="getInscripcionByTurnoGuardiaNcolegiado")
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPERSONA", property="idpersona", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTURNO", property="idturno", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDGUARDIA", property="idguardia", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="FECHASUSCRIPCION", property="fechasuscripcion", jdbcType=JdbcType.TIMESTAMP, id=true),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="OBSERVACIONESSUSCRIPCION", property="observacionessuscripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="OBSERVACIONESBAJA", property="observacionesbaja", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHASOLICITUDBAJA", property="fechasolicitudbaja", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHAVALIDACION", property="fechavalidacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="OBSERVACIONESVALIDACION", property="observacionesvalidacion", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHADENEGACION", property="fechadenegacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="OBSERVACIONESDENEGACION", property="observacionesdenegacion", jdbcType=JdbcType.VARCHAR),
        @Result(column="OBSERVACIONESVALBAJA", property="observacionesvalbaja", jdbcType=JdbcType.VARCHAR)
    })
    List<ScsInscripcionguardia> getInscripcionByTurnoGuardiaNcolegiado(String usuModif, String idTurno, String idGuardia, String numColegiado);

    @SelectProvider(type=ScsInscripcionguardiaSqlExtendsProvider.class, method="checkInscripcionesRangoFecha")
    @Results({
        @Result(column="IDINSTITUCION", property="idinstitucion", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDPERSONA", property="idpersona", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDTURNO", property="idturno", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="IDGUARDIA", property="idguardia", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="FECHASUSCRIPCION", property="fechasuscripcion", jdbcType=JdbcType.TIMESTAMP, id=true),
        @Result(column="FECHAMODIFICACION", property="fechamodificacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="USUMODIFICACION", property="usumodificacion", jdbcType=JdbcType.DECIMAL),
        @Result(column="FECHABAJA", property="fechabaja", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="OBSERVACIONESSUSCRIPCION", property="observacionessuscripcion", jdbcType=JdbcType.VARCHAR),
        @Result(column="OBSERVACIONESBAJA", property="observacionesbaja", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHASOLICITUDBAJA", property="fechasolicitudbaja", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="FECHAVALIDACION", property="fechavalidacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="OBSERVACIONESVALIDACION", property="observacionesvalidacion", jdbcType=JdbcType.VARCHAR),
        @Result(column="FECHADENEGACION", property="fechadenegacion", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="OBSERVACIONESDENEGACION", property="observacionesdenegacion", jdbcType=JdbcType.VARCHAR),
        @Result(column="OBSERVACIONESVALBAJA", property="observacionesvalbaja", jdbcType=JdbcType.VARCHAR)
    })
    List<ScsInscripcionguardia>  checkInscripcionesRangoFecha(BusquedaInscripcionMod inscripciones, String idInstitucion,String fechaInicio, String fechaFin);
    
    @SelectProvider(type=ScsInscripcionguardiaSqlExtendsProvider.class, method="getColegiadosInscritosGuardia")
    @Results({})
    List<String> getColegiadosInscritosGuardia(ScsInscripcionguardiaKey key);
    
    @UpdateProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "updateOrdenInscripciones")
	int updateOrdenInscripciones(String idTurno, String idGuardia, String idPersona, String idInstitucion, String ordenBD);

}
