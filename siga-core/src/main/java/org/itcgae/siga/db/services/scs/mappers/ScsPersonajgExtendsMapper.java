package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.ScsPersonaJGBean;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.JusticiableBusquedaItem;
import org.itcgae.siga.DTOs.scs.UnidadFamiliarEJGItem;
import org.itcgae.siga.db.mappers.ScsPersonajgMapper;
import org.itcgae.siga.db.services.scs.providers.ScsPersonajgSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsPersonajgExtendsMapper extends ScsPersonajgMapper{

	@SelectProvider(type = ScsPersonajgSqlExtendsProvider.class, method = "searchIdPersonaJusticiables")
	@Results({ 
		@Result(column = "IDPERSONA", property = "valor", jdbcType = JdbcType.VARCHAR),
	})
	List<StringDTO> searchIdPersonaJusticiables(JusticiableBusquedaItem justiciableBusquedaItem, Short idInstitucion, Integer tamMax);
	
	@SelectProvider(type = ScsPersonajgSqlExtendsProvider.class, method = "searchJusticiables")
	@Results({ 
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAMODIFICACION", property = "fechaModificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ASUNTOS", property = "asuntos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRESOLO", property = "nombreSolo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS", property = "apellidos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMEROASUNTOS", property = "numeroAsuntos", jdbcType = JdbcType.VARCHAR),	
		@Result(column = "ULTIMOASUNTO", property = "ultimoAsunto", jdbcType = JdbcType.VARCHAR)
	})
	List<JusticiableBusquedaItem> searchJusticiables(List<StringDTO> justiciableBusquedaItems, Short idInstitucion);
	
	@SelectProvider(type = ScsPersonajgSqlExtendsProvider.class, method = "getIdPersonajg")
	@Results({ @Result(column = "IDPERSONA", property = "newId", jdbcType = JdbcType.VARCHAR)
	})
	NewIdDTO getIdPersonajg(Short idInstitucion);
	
	@SelectProvider(type = ScsPersonajgSqlExtendsProvider.class, method = "getIdPersonajgNif")
	@Results({ @Result(column = "IDPERSONA", property = "newId", jdbcType = JdbcType.VARCHAR)
	})
	List<String> getIdPersonajgNif(String nif, Short idInstitucion);
	
	@SelectProvider(type = ScsPersonajgSqlExtendsProvider.class, method = "searchClaveAsuntosJusticiable")
	@Results({ 
		@Result(column = "FECHAMODIFICACION", property = "fechaModificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),	
		@Result(column = "CLAVE", property = "clave", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ROL", property = "rol", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR)
	})
	List<AsuntosClaveJusticiableItem> searchClaveAsuntosJusticiable(String idPersona, Short idInstitucion, String origen);
	
	@SelectProvider(type = ScsPersonajgSqlExtendsProvider.class, method = "searchClaveAsuntosJusticiableRepresentanteJG")
	@Results({ 
		@Result(column = "FECHAMODIFICACION", property = "fechaModificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),	
		@Result(column = "CLAVE", property = "clave", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ROL", property = "rol", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR)
	})
	List<AsuntosClaveJusticiableItem> searchClaveAsuntosJusticiableRepresentanteJG(String idPersona, List<StringDTO> representados, Short idInstitucion, String origen);

	@SelectProvider(type = ScsPersonajgSqlExtendsProvider.class, method = "unidadFamiliarEJG")
	@Results({ 
		@Result(column = "idinstitucion", property = "uf_idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idtipoejg", property = "uf_idTipoejg", jdbcType = JdbcType.VARCHAR),
		@Result(column = "anio", property = "uf_anio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "numero", property = "uf_numero", jdbcType = JdbcType.VARCHAR),	
		@Result(column = "idpersona", property = "uf_idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "solicitante", property = "uf_solicitante", jdbcType = JdbcType.VARCHAR),
		@Result(column = "encalidadde", property = "uf_enCalidad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nif", property = "pjg_nif", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombrecompletopjg", property = "pjg_nombrecompleto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombre", property = "pjg_nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "apellido1", property = "pjg_ape1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "apellido2", property = "pjg_ape2", jdbcType = JdbcType.VARCHAR),	
		@Result(column = "direccion", property = "pjg_direccion", jdbcType = JdbcType.VARCHAR),
//		@Result(column = "nombrePrincipal", property = "nombrePrincipal", jdbcType = JdbcType.VARCHAR),
//		@Result(column = "apellido1Principal", property = "apellido1Principal", jdbcType = JdbcType.VARCHAR),
//		@Result(column = "apellido2Principal", property = "apellido2Principal", jdbcType = JdbcType.VARCHAR),	
		@Result(column = "descripcion", property = "pd_descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "estado", property = "estado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechasolicitud", property = "fechaSolicitud", jdbcType = JdbcType.DATE),
		@Result(column = "fechabaja", property = "fechaBaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "BIENESINMUEBLES", property = "bienesInmu", jdbcType = JdbcType.VARCHAR),
		@Result(column = "BIENESMUEBLES", property = "bienesMu", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CIRCUNSTANCIAS_EXCEPCIONALES", property = "circunsExcep", jdbcType = JdbcType.NUMERIC),
		@Result(column = "DESCRIPCIONINGRESOSANUALES", property = "descrIngrAnuales", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPARENTESCO", property = "idParentesco", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPOGRUPOLAB", property = "idTipoGrupoLab", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPOINGRESO", property = "idTipoIngreso", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IMPORTEBIENESINMUEBLES", property = "impBienesInmu", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IMPORTEBIENESMUEBLES", property = "impBienesMu", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IMPORTEINGRESOSANUALES", property = "impIngrAnuales", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IMPORTEOTROSBIENES", property = "impOtrosBienes", jdbcType = JdbcType.NUMERIC),
		@Result(column = "INCAPACITADO", property = "incapacitado", jdbcType = JdbcType.NUMERIC),
		@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
		@Result(column = "OTROSBIENES", property = "otrosBienes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "representante", property = "representante", jdbcType = JdbcType.VARCHAR),
		@Result(column = "direccionRepresentante", property = "direccionRepresentante", jdbcType = JdbcType.VARCHAR),
		@Result(column = "solicitantePpal", property = "solicitantePpal", jdbcType = JdbcType.VARCHAR),
		

	})
	List<UnidadFamiliarEJGItem> unidadFamiliarEJG(EjgItem ejgItem, String idInstitucion, Integer tamMaximo, String idLenguaje);

	@SelectProvider(type = ScsPersonajgSqlExtendsProvider.class, method = "getPersonaJG")
	@Results({
			@Result(column = "NOMBRE", property = "fechaModificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDO1", property = "apellido1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDO2", property = "apellido2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DIRECCION", property = "direccion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "EXISTEDOMICILIO", property = "existeDomicilio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGOPOSTAL", property = "codigoPostal", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPAIS", property = "idPais", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PERIDPROVINCIA", property = "perIdProvicncia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPOBLACION", property = "idPoblacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FAX", property = "fax", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CORREOELECTRONICO", property = "correoElectronico", jdbcType = JdbcType.VARCHAR),
			@Result(column = "POBLACION", property = "poblacionStr", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PROIDPROVINCIA", property = "proIdProvincia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PROVINCIA", property = "provinciaStr", jdbcType = JdbcType.VARCHAR)
	})
	List<ScsPersonaJGBean> getPersonaJG(Long idPersonaJG, Integer idInstitucion);
	
	@SelectProvider(type = ScsPersonajgSqlExtendsProvider.class, method = "getPersonaRepresentanteJG")
	@Results({
			@Result(column = "IDPERSONA", property = "valor", jdbcType = JdbcType.VARCHAR)
	})
	List<StringDTO> getPersonaRepresentanteJG(String idPersonaJG, Short idInstitucion);
	
}
