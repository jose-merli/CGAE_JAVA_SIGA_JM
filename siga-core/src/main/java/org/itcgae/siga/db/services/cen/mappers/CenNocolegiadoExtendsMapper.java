package org.itcgae.siga.db.services.cen.mappers;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import org.itcgae.siga.DTOs.cen.*;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.CenNocolegiadoMapper;
import org.itcgae.siga.db.services.cen.providers.CenNocolegiadoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Primary
public interface CenNocolegiadoExtendsMapper extends CenNocolegiadoMapper{
	
	
	@SelectProvider(type = CenNocolegiadoSqlExtendsProvider.class, method = "searchLegalPersons")
	@Results({
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DENOMINACION", property = "denominacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ABREVIATURA", property = "abreviatura", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHACONSTITUCION", property = "fechaConstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOSOCIEDAD", property = "tipoSociedad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMEROINTEGRANTES", property = "numeroIntegrantes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRESINTEGRANTES", property = "nombresIntegrantes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHA_BAJA", property = "fechaBaja", jdbcType = JdbcType.DATE), 
		@Result(column = "SOCIEDADPROFESIONAL", property = "sociedadProfesional", jdbcType = JdbcType.VARCHAR)
	})
	List<BusquedaJuridicaItem> searchLegalPersons(BusquedaJuridicaSearchDTO busquedaJuridicaSearchDTO, String idLenguaje, String idInstitucion);
	
	
	
	@SelectProvider(type = CenNocolegiadoSqlExtendsProvider.class, method = "searchHistoricLegalPersons")
	@Results({
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DENOMINACION", property = "denominacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ABREVIATURA", property = "abreviatura", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHACONSTITUCION", property = "fechaConstitucion", jdbcType = JdbcType.DATE),
		@Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOSOCIEDAD", property = "tipoSociedad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMEROINTEGRANTES", property = "numeroIntegrantes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRESINTEGRANTES", property = "nombresIntegrantes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHA_BAJA", property = "fechaBaja", jdbcType = JdbcType.DATE), 
		@Result(column = "SOCIEDADPROFESIONAL", property = "sociedadProfesional", jdbcType = JdbcType.VARCHAR)
	})
	List<BusquedaJuridicaItem> searchHistoricLegalPersons(BusquedaJuridicaSearchDTO busquedaJuridicaSearchDTO, String idLenguaje, String idInstitucion);
	
	
	@SelectProvider(type = CenNocolegiadoSqlExtendsProvider.class, method = "searchGeneralData")
	@Results({
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DENOMINACION", property = "denominacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ABREVIATURA", property = "abreviatura", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHACONSTITUCION", property = "fechaConstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOSOCIEDAD", property = "tipoSociedad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMEROINTEGRANTES", property = "numeroIntegrantes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRESINTEGRANTES", property = "nombresIntegrantes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHA_BAJA", property = "fechaBaja", jdbcType = JdbcType.VARCHAR), 
		@Result(column = "FECHAALTA", property = "fechaAlta", jdbcType = JdbcType.VARCHAR), 
		@Result(column = "ANOTACIONES", property = "anotaciones", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGRUPO", property = "IDGrupos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SOCIEDADPROFESIONAL", property = "sociedadProfesional", jdbcType = JdbcType.VARCHAR),
		@Result(column = "LENGUAJESOCIEDAD", property = "idLenguajeSociedad", jdbcType = JdbcType.VARCHAR), 
		@Result(column = "ASIENTOCONTABLE", property = "cuentaContable", jdbcType = JdbcType.VARCHAR)
	})
	List<PersonaJuridicaItem> searchGeneralData(PersonaJuridicaSearchDTO personaJuridicaSearchDTO);
	
	
	@InsertProvider(type = CenNocolegiadoSqlExtendsProvider.class, method = "insertSelectiveForCreateLegalPerson")
	int insertSelectiveForCreateLegalPerson(String idInstitucion, AdmUsuarios usuario,EtiquetaUpdateDTO etiquetaUpdateDTO);
	
	
	@SelectProvider(type = CenNocolegiadoSqlExtendsProvider.class, method = "selectProfesionalActivitiesSociety")
	@Results({
		@Result(column = "IDACTIVIDADPROFESIONAL", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> selectProfesionalActivitiesSociety(PersonaJuridicaActividadDTO personaJuridicaActividadDTO, String idLenguaje);
	
	
	@SelectProvider(type = CenNocolegiadoSqlExtendsProvider.class, method = "selectRetenciones")
	@Results({
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAINICIO", property = "fechaInicio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAFIN", property = "fechaFin", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDRETENCION", property = "idRetencion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "RETENCION", property = "porcentajeRetencion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "RECURSO", property = "recursoRetencion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcionRetencion", jdbcType = JdbcType.VARCHAR)
	})
	List<RetencionesItem> selectRetenciones(PersonaSearchDTO personaSearchDto, String idLenguaje, String idInstitucion);
	
	@SelectProvider(type = CenNocolegiadoSqlExtendsProvider.class, method = "selectRetencionesColegial")
	@Results({
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAINICIO", property = "fechaInicio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAFIN", property = "fechaFin", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDRETENCION", property = "idRetencion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "RETENCION", property = "porcentajeRetencion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "RECURSO", property = "recursoRetencion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcionRetencion", jdbcType = JdbcType.VARCHAR)
	})
	List<RetencionesItem> selectRetencionesColegial(PersonaSearchDTO personaSearchDto, String idLenguaje, String idInstitucion);
	
	@UpdateProvider(type = CenNocolegiadoSqlExtendsProvider.class, method = "disassociatePerson")
	int disassociatePerson(AdmUsuarios usuario, DesasociarPersonaDTO desasociarPersona);
	
	
	@UpdateProvider(type = CenNocolegiadoSqlExtendsProvider.class, method = "updateByExampleDataLegalPerson")
	int updateByExampleDataLegalPerson(PerJuridicaDatosRegistralesUpdateDTO perJuridicaDatosRegistralesUpdateDTO, String idInstitucion, AdmUsuarios usuario);
	
	


	@SelectProvider(type = CenNocolegiadoSqlExtendsProvider.class, method = "selectSociedadesEliminadas")
	@Results({
		@Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHA_BAJA", property = "fechaBaja", jdbcType = JdbcType.DATE), 
	})
	List<SociedadesBajaDTO> selectSociedadesEliminadas(Short idInstitucion, Date fechaDesde, Date fechaHasta);

	@SelectProvider(type = CenNocolegiadoSqlExtendsProvider.class, method = "selectSociedadesEditadas")
	@Results({
		@Result(column = "IDPERSONA", property = "newId", jdbcType = JdbcType.VARCHAR),
	})
	List<NewIdDTO> selectSociedadesEditadas(Short idInstitucion, Date fechaDesde, Date fechaHasta);

	

	@SelectProvider(type = CenNocolegiadoSqlExtendsProvider.class, method = "selectSociedadesEditar")
	@Results({
		@Result(column = "SOCIEDADNIF", property = "sociedadNif", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SOCIEDADDENOMINACION", property = "sociedadDenominacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SOCIEDADFORMASOCIAL", property = "sociedadFormaSocial", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SOCIEDADFECHAALTA", property = "sociedadFechaAlta", jdbcType = JdbcType.DATE),
		@Result(column = "SOCIEDADPROFESIONAL", property = "sociedadProfesional", jdbcType = JdbcType.VARCHAR),
		@Result(column = "RESENA", property = "resena", jdbcType = JdbcType.VARCHAR),
		@Result(column = "OBJETOSOCIAL", property = "objetoSocial", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHACONSTITUCION", property = "fechaConstitucion", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAFIN", property = "fechaFin", jdbcType = JdbcType.DATE),
		@Result(column = "DOMICILIO", property = "domicilio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOPOSTAL", property = "codigoPostal", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOPROVINCIA", property = "codigoProvincia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PROVINCIA", property = "provincia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "POBLACION", property = "poblacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOPOBLACION", property = "codigoPoblacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CORREOELECTRONICO", property = "correoElectronico", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FAX1", property = "fax1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FAX2", property = "fax2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "MOVIL", property = "movil", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PAGINAWEB", property = "paginaWeb", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TELEFONO1", property = "telefono1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TELEFONO2", property = "telefono2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDENTIFICACIONNOTARIO", property = "identificacionNotario", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOIDENTIFICACION", property = "tipoIdentificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRENOTARIO", property = "nombreNotario", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDO1NOTARIO", property = "apellido1Notario", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDO2NOTARIO", property = "apellido2Notario", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMEROREGISTRO", property = "numeroRegistro", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDENTIFICACIONREGISTRO", property = "identificacionRegistro", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAINSCRIPCION", property = "fechaInscripcion", jdbcType = JdbcType.DATE),
		@Result(column = "FECHACANCELACION", property = "fechaCancelacion", jdbcType = JdbcType.DATE),
		
	})
	List<SociedadesEditadasDTO> selectSociedadesEditar(Short idInstitucion, Date fechaDesde, Date fechaHasta);

	
	@InsertProvider(type = CenNocolegiadoSqlExtendsProvider.class, method = "insertSelectiveForCreateNewSociety")
	int insertSelectiveForCreateNewSociety(String idInstitucion, AdmUsuarios usuario,SociedadCreateDTO sociedadCreateDTO);
	
	@SelectProvider(type = CenNocolegiadoSqlExtendsProvider.class, method = "selectNoColegiados")
	@Results({
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIFCIF", property = "nif", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SOLONOMBRE", property = "soloNombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS1", property = "apellidos1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS2", property = "apellidos2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDESTADOCIVIL", property = "idEstadoCivil", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPOIDENTIFICACION", property = "idTipoIdentificacion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NATURALDE", property = "naturalDe", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDLENGUAJE", property = "idLenguaje", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ASIENTOCONTABLE", property = "asientoContable", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTRATAMIENTO", property = "idTratamiento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHANACIMIENTO", property = "fechaNacimiento", jdbcType = JdbcType.DATE),
		@Result(column = "CORREO", property = "correo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "COMISIONES", property = "comisiones", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TELEFONO", property = "telefono", jdbcType = JdbcType.VARCHAR),
		@Result(column = "MOVIL", property = "movil", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechaBaja", property = "fechaBaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOAPARECERREDABOGACIA", property = "noAparecerRedAbogacia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "COLEGIORESULTADO", property = "colegioResultado", jdbcType = JdbcType.VARCHAR)
	})
	List<NoColegiadoItem> selectNoColegiados(Short idInstitucion, NoColegiadoItem noColegiadoItem);
	
	
	@SelectProvider(type = CenNocolegiadoSqlExtendsProvider.class, method = "selectNoColegiadosByIdPersona")
	@Results({
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIFCIF", property = "nif", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SOLONOMBRE", property = "soloNombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS1", property = "apellidos1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS2", property = "apellidos2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDESTADOCIVIL", property = "idEstadoCivil", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPOIDENTIFICACION", property = "idTipoIdentificacion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NATURALDE", property = "naturalDe", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDLENGUAJE", property = "idLenguaje", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ASIENTOCONTABLE", property = "asientoContable", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTRATAMIENTO", property = "idTratamiento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHANACIMIENTO", property = "fechaNacimiento", jdbcType = JdbcType.DATE),
		@Result(column = "CORREO", property = "correo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "COMISIONES", property = "comisiones", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TELEFONO", property = "telefono", jdbcType = JdbcType.VARCHAR),
		@Result(column = "MOVIL", property = "movil", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechaBaja", property = "fechaBaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOAPARECERREDABOGACIA", property = "noAparecerRedAbogacia", jdbcType = JdbcType.VARCHAR)
	})
	List<NoColegiadoItem> selectNoColegiadosByIdPersona(Short idInstitucion, String idPersona);
	
	@SelectProvider(type = CenNocolegiadoSqlExtendsProvider.class, method = "searchHistoricNoColegiado")
	@Results({
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIFCIF", property = "nif", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SOLONOMBRE", property = "soloNombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS1", property = "apellidos1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS2", property = "apellidos2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDESTADOCIVIL", property = "estadoCivil", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPOIDENTIFICACION", property = "idTipoIdentificacion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NATURALDE", property = "naturalDe", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDLENGUAJE", property = "idLenguaje", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ASIENTOCONTABLE", property = "asientoContable", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTRATAMIENTO", property = "tratamiento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHANACIMIENTO", property = "fechaNacimiento", jdbcType = JdbcType.DATE),
		@Result(column = "CORREO", property = "correo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TELEFONO1", property = "telefono", jdbcType = JdbcType.VARCHAR),
		@Result(column = "MOVIL", property = "movil", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHA_BAJA", property = "fechaBaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PUBLICIDAD", property = "publicidad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "GUIAJUDICIAL", property = "guiaJudicial", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOAPARECERREDABOGACIA", property = "noAparecerRedAbogacia", jdbcType = JdbcType.VARCHAR)
	})
	List<NoColegiadoItem> searchHistoricNoColegiado(NoColegiadoItem noColegiadoItem, String idLenguaje, String idInstitucion);
	
	@SelectProvider(type = CenNocolegiadoSqlExtendsProvider.class, method = "selectColegiacionesIdPersona")
	@Results({ 
		@Result(column = "IDINSTITUCION", property = "valor", jdbcType = JdbcType.VARCHAR),
	})
	List<StringDTO> selectColegiacionesIdPersona(Long idPersona);
	
	@SelectProvider(type = CenNocolegiadoSqlExtendsProvider.class, method = "selectCliente")
	@Results({
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIFCIF", property = "nif", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SOLONOMBRE", property = "soloNombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS1", property = "apellidos1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS2", property = "apellidos2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDESTADOCIVIL", property = "idEstadoCivil", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPOIDENTIFICACION", property = "idTipoIdentificacion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NATURALDE", property = "naturalDe", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDLENGUAJE", property = "idLenguaje", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ASIENTOCONTABLE", property = "asientoContable", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTRATAMIENTO", property = "idTratamiento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHANACIMIENTO", property = "fechaNacimiento", jdbcType = JdbcType.DATE),
		@Result(column = "CORREO", property = "correo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "COMISIONES", property = "comisiones", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TELEFONO", property = "telefono", jdbcType = JdbcType.VARCHAR),
		@Result(column = "MOVIL", property = "movil", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechaBaja", property = "fechaBaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOAPARECERREDABOGACIA", property = "noAparecerRedAbogacia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "COLEGIORESULTADO", property = "colegioResultado", jdbcType = JdbcType.VARCHAR)
	})
	List<NoColegiadoItem> selectCliente(Short idInstitucion, NoColegiadoItem noColegiadoItem);


	@SelectProvider(type = CenNocolegiadoSqlExtendsProvider.class, method = "selectLiquidacionSJCS")
	@Results({
		@Result(column = "FECHAINICIO", property = "fechaInicio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAFIN", property = "fechaFin", jdbcType = JdbcType.VARCHAR)
	})
	List<LiquidacionSJCSDTO> selectLiquidacionSJCS(PersonaSearchDTO personaSearchDto,String idInstitucion);


	@SelectProvider(type = CenNocolegiadoSqlExtendsProvider.class, method = "selectRetencionesColegialYSociedades")
	@Results({
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAINICIO", property = "fechaInicio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAFIN", property = "fechaFin", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDRETENCION", property = "idRetencion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "RETENCION", property = "porcentajeRetencion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "RECURSO", property = "recursoRetencion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcionRetencion", jdbcType = JdbcType.VARCHAR)
	})
	List<RetencionesItem> selectRetencionesColegialYSociedades(PersonaSearchDTO personaSearchDto, String idLenguaje, String idInstitucion, List<LiquidacionSJCSDTO> listaRetenciones);

	
}
