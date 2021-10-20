package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
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
import org.itcgae.siga.DTOs.scs.GrupoGuardiaColegiadoItem;
import org.itcgae.siga.DTOs.scs.GuardiasTurnosItem;
import org.itcgae.siga.DTOs.scs.InscripcionDatosEntradaDTO;
import org.itcgae.siga.DTOs.scs.InscripcionGuardiaItem;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaItem;
import org.itcgae.siga.DTOs.scs.TrabajosSJCSInsGuardiaItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.ScsInscripcionguardia;
import org.itcgae.siga.db.entities.ScsInscripcionguardiaKey;
import org.itcgae.siga.db.mappers.ScsInscripcionguardiaMapper;
import org.itcgae.siga.db.mappers.ScsInscripcionguardiaSqlProvider;
import org.itcgae.siga.db.services.scs.providers.ScsGuardiasturnoSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsInscripcionguardiaSqlExtendsProvider;

public interface ScsInscripcionguardiaExtendsMapper extends ScsInscripcionguardiaMapper{


	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "getColaGuardias")
	@Results({
		@Result(column = "NUMEROGRUPO", property="numeroGrupo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGRUPOGUARDIACOLEGIADO", property = "idGrupoGuardiaColegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ORDENGRUPO", property = "orden", jdbcType = JdbcType.VARCHAR),
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

	})
	List<InscripcionGuardiaItem> getColaGuardias(String idGuardia, String idTurno, String fecha,String ultimo,String ordenaciones, String idInstitucion, String idgrupoguardiacolegiado);


	
	
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
	int insertarInscripcion(Short idInstitucion, InscripcionGuardiaItem inscripcion, AdmUsuarios admUsuarios);

	@Results({
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHASUSCRIPCION", property = "fechaSuscripcion", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.DATE),
		@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
		@Result(column = "OBSERVACIONESSUSCRIPCION", property = "observacionessuscripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "OBSERVACIONESBAJA", property = "observacionesbaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHASOLICITUDBAJA", property = "fechasolicitudbaja", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAVALIDACION", property = "fechavalidacion", jdbcType = JdbcType.DATE),
		@Result(column = "OBSERVACIONESVALIDACION", property = "observacionesvalidacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHADENEGACION", property = "fechadenegacion", jdbcType = JdbcType.DATE),
		@Result(column = "OBSERVACIONESDENEGACION", property = "observacionesdenegacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "OBSERVACIONESVALBAJA", property = "observacionesvalbaja", jdbcType = JdbcType.VARCHAR)
	})
	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "buscarInscripcion")
	List<InscripcionGuardiaItem> buscarInscripcion(Short idInstitucion, InscripcionGuardiaItem inscripcion,
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
		@Result(column = "descripcion_obligatoriedad", property = "descripcionObligatoriedad", jdbcType = JdbcType.VARCHAR)
	})
	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "inscripcionesDisponibles")
	List<GestionInscripcion> inscripcionesDisponibles(Short idInstitucion, AdmUsuarios admUsuarios);

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
		@Result(column = "descripcion_obligatoriedad", property = "descripcionObligatoriedad", jdbcType = JdbcType.VARCHAR)
	})
	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "inscripcionPorguardia")
	List<GestionInscripcion> inscripcionPorguardia(Short idInstitucion, AdmUsuarios admUsuarios, String guardia);


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
		@Result(column = "FECHASUSCRIPCION", property = "fechasolicitud", jdbcType = JdbcType.DATE),
	})
	List<BusquedaInscripcionItem> getListadoInscripciones(InscripcionDatosEntradaDTO inscripciones, String idInstitucion);
	
	
	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "ObjetoFrontValidarInscripcion")
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
		@Result(column = "FECHASUSCRIPCION", property = "fechasolicitud", jdbcType = JdbcType.DATE),
	})
	BusquedaInscripcionMod getObjetoFrontValidarInscripcion(BusquedaInscripcionMod inscripciones, String idInstitucion, String fECHABAJA,
			String fECHADENEGACION, String fECHASOLICITUD, String fECHASOLICITUDBAJA, String fECHAVALIDACION, String fECHAVALORALTA, String fECHAVALORBAJA);
	
	
	@DeleteProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "DeleteFKGrupoColegiado")
	int getDeleteFKGrupoColegiado(BusquedaInscripcionMod inscripcion, String fECHASOLICITUD);
	
	@DeleteProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "DeleteObjetoFrontValidarInscripcion")
	int getDeleteObjetoFrontValidarInscripcion(BusquedaInscripcionMod inscripcion, String fECHABAJA, String fECHADENEGACION, String fECHASOLICITUD, String fECHASOLICITUDBAJA, String fECHAVALIDACION, String fECHAVALORALTA, String fECHAVALORBAJA);
	
	@InsertProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "InsertObjetoValidarInscripcion")	
	@Results({
	})
	int getInsertObjetoValidarInscripcion(BusquedaInscripcionMod inscripcion, int usuario, GrupoGuardiaColegiadoItem objetoFK, String FECHABAJA,String fECHADENEGACIONNUEVA, String fECHASOLICITUDBAJANUEVA, String fECHASOLICITUDNUEVA, String fECHAVALIDACIONNUEVA);

	
	@UpdateProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "validarDenegarSBajaCFechaInscripciones")
	int getValidarDenegarSBajaCFechaInscripciones(BusquedaInscripcionMod inscripciones, String idInstitucion, String fECHABAJA,
			String fECHADENEGACIONNUEVA, String fECHASOLICITUDNUEVA, String fECHASOLICITUDBAJANUEVA, String fECHAVALIDACIONNUEVA, String fECHAVALORALTA,
			String fECHAVALORBAJA, int usuario);



	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "ObjetoFKGrupoColegiado")
	@Results({ 
		@Result(column = "IDGRUPOGUARDIACOLEGIADO", property = "idgrupoguardiacolegiado", jdbcType = JdbcType.DECIMAL),
		@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
		@Result(column = "IDTURNO", property = "idturno", jdbcType = JdbcType.DECIMAL),
		@Result(column = "IDGUARDIA", property = "idguardia", jdbcType = JdbcType.DECIMAL),
		@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL),
		@Result(column = "FECHASUSCRIPCION", property = "fechasuscripcion", jdbcType = JdbcType.DATE),
		@Result(column = "IDGRUPOGUARDIA", property = "idgrupoguardia", jdbcType = JdbcType.DECIMAL),
		@Result(column = "ORDEN", property = "orden", jdbcType = JdbcType.DECIMAL),
		@Result(column = "FECHACREACION", property = "fechacreacion", jdbcType = JdbcType.DATE),
		@Result(column = "USUCREACION", property = "usucreacion", jdbcType = JdbcType.DECIMAL),
		@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.DATE),
		@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),

	})
	GrupoGuardiaColegiadoItem getObjetoFKGrupoColegiado(BusquedaInscripcionMod a, String string, String fECHASOLICITUD);



	@InsertProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "insertarObjetoFKGrupoColegiado")
	int getInsertFKGrupoGuardiaColegiado(GrupoGuardiaColegiadoItem objetoFK, int usuario, String fECHASOLICITUDNUEVA, String fECHACREACIONNUEVA);




	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "getSaltoCompensacionesActivo")
	@Results({ 
	})
	List<SaltoCompGuardiaItem> getBuscarSaltoCompensancion(String idInstitucion, String idturno, String idguardia, String idpersona,
			String saltos);



	@DeleteProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "eliminarSaltoCompensacion")
	int getEliminarSaltoCompensancion(String string, String idturno, String idguardia, String idpersona, String saltos);



	@SelectProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "buscarTrabajosSJCS")
	@Results({ 
	})
	List<TrabajosSJCSInsGuardiaItem> getBuscarTrabajosSJCS(String idInstitucion, String idturno, String idguardia, String idpersona, String fECHADESDE, String fECHAHASTA);



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



	@UpdateProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "UpdateInscripcionTurno")
	int UpdateInscripcionTurno(String string, String idturno, BusquedaInscripcionMod a, String fECHABAJA);



	@UpdateProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "UpdateInscripcionGuardia")
	int UpdateInscripcionGuardia(String string, String idturno, String idguardia,BusquedaInscripcionMod a, String fECHABAJA);



	@UpdateProvider(type = ScsInscripcionguardiaSqlExtendsProvider.class, method = "CFechaInscripciones")

	int getCFechaInscripciones(BusquedaInscripcionMod a, String string, String fECHABAJA, String fECHAVALIDACIONNUEVA,
			int usuario);


    
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

}
