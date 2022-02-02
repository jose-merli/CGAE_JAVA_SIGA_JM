package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.DireccionesDTO;
import org.itcgae.siga.DTO.fac.EntradaDireccionEspecificaDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionLetradoOficio;
import org.itcgae.siga.DTOs.cen.DatosDireccionesItem;
import org.itcgae.siga.DTOs.cen.DatosDireccionesSearchDTO;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.cen.TarjetaIntegrantesCreateDTO;
import org.itcgae.siga.DTOs.cen.TarjetaIntegrantesUpdateDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.mappers.CenDireccionesMapper;
import org.itcgae.siga.db.services.cen.providers.CenClienteSqlExtendsProvider;
import org.itcgae.siga.db.services.cen.providers.CenComponentesSqlExtendsProvider;
import org.itcgae.siga.db.services.cen.providers.CenDireccionesSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenDireccionesExtendsMapper extends CenDireccionesMapper {

	@SelectProvider(type = CenDireccionesSqlExtendsProvider.class, method = "selectDireccionesWs")
	@Results({ @Result(column = "TIPODIRECCION", property = "tipoDireccion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDDIRECCION", property = "idDireccion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOPOSTAL", property = "codigoPostal", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DOMICILIO ", property = "domicilio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPOBLACION", property = "idPoblacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPROVINCIA", property = "idProvincia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPAIS", property = "idPais", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TELEFONO1", property = "telefono", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FAX1", property = "fax", jdbcType = JdbcType.VARCHAR),
		@Result(column = "MOVIL", property = "movil", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PAGINAWEB", property = "paginaWeb", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CORREOELECTRONICO", property = "correoElectronico", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDEXTERNOPAIS", property = "idExternoPais", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREPAIS", property = "nombrePais", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDEXTERNOPOBLACION", property = "idExternoPoblacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREPOBLACION", property = "nombrePoblacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDEXTERNOPROVINCIA", property = "idExternoProvincia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREPROVINCIA", property = "nombreProvincia", jdbcType = JdbcType.VARCHAR),
        @Result(column = "POBLACIONEXTRANJERA", property = "poblacionExtranjera", jdbcType = JdbcType.VARCHAR),

	})
	List<DatosDireccionesItem> selectDireccionesWs(String idPersona);

	
	@SelectProvider(type = CenDireccionesSqlExtendsProvider.class, method = "selectPartidoJudicial")
	@Results({ @Result(column = "NOMBREPARTIDO", property = "nombrepartido", jdbcType = JdbcType.VARCHAR)
	})
	List<DatosDireccionesItem> selectPartidoJudicial(String idPersona, String idInstitucion);
	
	
	
	@UpdateProvider(type = CenComponentesSqlExtendsProvider.class, method = "updateMember")
	int updateMember(TarjetaIntegrantesUpdateDTO tarjetaIntegrantesUpdateDTO, AdmUsuarios usuario, String idInstitucion);
	
	
	@InsertProvider(type = CenComponentesSqlExtendsProvider.class, method = "insertSelectiveForcreateMember")
	int insertSelectiveForcreateMember(TarjetaIntegrantesCreateDTO tarjetaIntegrantesCreateDTO,AdmUsuarios usuario, String idInstitucion);
	
	
	@SelectProvider(type = CenComponentesSqlExtendsProvider.class, method = "selectMaxIDComponente")
	@Results({ @Result(column = "IDCOMPONENTE", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCOMPONENTE", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	ComboItem selectMaxIDComponente(String idPersonaPadre, String idInstitucion);
	

	@SelectProvider(type = CenDireccionesSqlExtendsProvider.class, method = "selectMaxIdDireccion")
	@Results({ @Result(column = "IDDIRECCION", property = "idMax", jdbcType = JdbcType.NUMERIC)
	})
	MaxIdDto selectMaxID();


	@SelectProvider(type = CenDireccionesSqlExtendsProvider.class, method = "selectDirecciones")
	@Results({ @Result(column = "TIPODIRECCION", property = "tipoDireccion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDDIRECCION", property = "idDireccion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPODIRECCIONLIST", property = "idTipoDireccionList", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOPOSTAL", property = "codigoPostal", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DOMICILIO", property = "domicilio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DOMICILIOLISTA", property = "domicilioLista", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPOBLACION", property = "idPoblacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPROVINCIA", property = "idProvincia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPAIS", property = "idPais", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TELEFONO1", property = "telefono", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FAX1", property = "fax", jdbcType = JdbcType.VARCHAR),
		@Result(column = "MOVIL", property = "movil", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PAGINAWEB", property = "paginaWeb", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CORREOELECTRONICO", property = "correoElectronico", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDEXTERNOPAIS", property = "idExternoPais", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREPAIS", property = "nombrePais", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDEXTERNOPOBLACION", property = "idExternoPoblacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREPOBLACION", property = "nombrePoblacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDEXTERNOPROVINCIA", property = "idExternoProvincia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREPROVINCIA", property = "nombreProvincia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAMODIFICACION", property = "fechaModificacion", jdbcType = JdbcType.VARCHAR),
        @Result(column = "POBLACIONEXTRANJERA", property = "poblacionExtranjera", jdbcType = JdbcType.VARCHAR),
        @Result(column = "PREFERENTE", property = "preferente", jdbcType = JdbcType.VARCHAR),
        @Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR)

	})
	List<DatosDireccionesItem> selectDirecciones(DatosDireccionesSearchDTO datosDireccionesSearchDTO,	String idInstitucion);

	@SelectProvider(type = CenDireccionesSqlExtendsProvider.class, method = "selectNewIdDireccion")
	@Results({
		@Result(column = "IDDIRECCION", property = "idDireccion", jdbcType = JdbcType.VARCHAR),
	})
	List<DatosDireccionesItem> selectNewIdDireccion(String idPersona, String idInstitucion);
	
	@SelectProvider(type = CenDireccionesSqlExtendsProvider.class, method = "selectDireccionesSolEsp")
	@Results({ 
		@Result(column = "CODIGOPOSTAL", property = "codigoPostal", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DOMICILIO", property = "domicilio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TELEFONO1", property = "telefono", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FAX1", property = "fax", jdbcType = JdbcType.VARCHAR),
		@Result(column = "MOVIL", property = "movil", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PAGINAWEB", property = "paginaWeb", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CORREOELECTRONICO", property = "correoElectronico", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREPAIS", property = "nombrePais", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREPOBLACION", property = "nombrePoblacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREPROVINCIA", property = "nombreProvincia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAMODIFICACION", property = "fechaModificacion", jdbcType = JdbcType.VARCHAR)
	})
	List<DatosDireccionesItem> selectDireccionesSolEsp(String idPersona, String idDireccion, String idInstitucion);

	@SelectProvider(type = CenDireccionesSqlExtendsProvider.class, method = "getNumDirecciones")
	@Results({
		@Result(column = "IDDIRECCION", property = "idDireccion", jdbcType = JdbcType.VARCHAR),
	})
	List<DatosDireccionesItem> getNumDirecciones(CenDirecciones beanDir, int tipoDireccionFacturacion);

	@SelectProvider(type = CenDireccionesSqlExtendsProvider.class, method = "getTiposDireccion")
	@Results({
		@Result(column = "DESCRIPCION", property = "valor", jdbcType = JdbcType.VARCHAR),
	})
	List<StringDTO> getTiposDireccion(Short idinstitucion, Long idPersona, Long idDireccion,	String idioma);
	
	
	
	
	
	
	@SelectProvider(type = CenDireccionesSqlExtendsProvider.class, method = "getDireccionLetradoSalidaOficio")
	@Results({ @Result(column = "domicilio_despacho_letrado", property = "domicilio_letrado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "cp_despacho_letrado", property = "cp_letrado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idpoblacion_despacho_letrado", property = "idpoblacion_letrado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "poblacion_despacho_letrado", property = "poblacion_letrado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idprovincia_despacho_letrado", property = "idprovincia_letrado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "telefono1_despacho_letrado", property = "telefono1_letrado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "telefono2_despacho_letrado", property = "telefono2_letrado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fax1_despacho_letrado", property = "fax1_letrado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fax2_despacho_letrado", property = "fax2_letrado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "email_despacho_letrado", property = "email_letrado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "movil_despacho_letrado", property = "movil_letrado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "provincia_despacho_letrado", property = "provincia_letrado", jdbcType = JdbcType.VARCHAR)
	})
	List<DatosDireccionLetradoOficio> getDireccionLetradoSalidaOficio(String idPersona, String idInstitucion);
	
	
	@SelectProvider(type = CenDireccionesSqlExtendsProvider.class, method = "getDireccionPersonalSalidaOficio")
	@Results({ @Result(column = "domicilio_guardia_letrado", property = "domicilio_letrado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "cp_guardia_letrado", property = "cp_letrado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idpoblacion_guardia_letrado", property = "idpoblacion_letrado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "poblacion_guardia_letrado", property = "poblacion_letrado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idprovincia_guardia_letrado", property = "idprovincia_letrado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "telefono1_guardia_letrado", property = "telefono1_letrado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "telefono2_guardia_letrado", property = "telefono2_letrado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fax1_guardia_letrado", property = "fax1_letrado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fax2_guardia_letrado", property = "fax2_letrado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "email_guardia_letrado", property = "email_letrado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "movil_guardia_letrado", property = "movil_letrado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "provincia_guardia_letrado", property = "provincia_letrado", jdbcType = JdbcType.VARCHAR)
	})
	List<DatosDireccionLetradoOficio> getDireccionPersonalSalidaOficio(String idPersona, String idInstitucion);
	
	
	@SelectProvider(type = CenDireccionesSqlExtendsProvider.class, method = "getIdProvinciaImpreso190")
	@Results({
		
	})
	String getIdProvinciaImpreso190(String idPersona, String idinstitucion, int tipoDireccionFac );

	@SelectProvider(type = CenDireccionesSqlExtendsProvider.class, method = "getEntradaDireccionEspecifica")
	@Results({
			@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDDIRECCION", property = "idDireccion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DOMICILIO", property = "domicilio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGOPOSTAL", property = "codigoPostal", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TELEFONO1", property = "telefono1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TELEFONO2", property = "telefono2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MOVIL", property = "movil", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FAX1", property = "fax1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FAX2", property = "fax2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CORREOELECTRONICO", property = "correoElectronico", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PAGINAWEB", property = "paginaWeb", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "PREFERENTE", property = "preferente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPAIS", property = "idPais", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPROVINCIA", property = "idProvincia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPOBLACION", property = "idPoblacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "POBLACIONEXTRANJERA", property = "poblacionExtranjera", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechaModificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usuModificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "POBLACION", property = "poblacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PROVINCIA", property = "provincia", jdbcType = JdbcType.VARCHAR)
	})
	List<EntradaDireccionEspecificaDTO> getEntradaDireccionEspecifica(String idPersona, String idInstitucion, String idDireccion);

	@SelectProvider(type = CenDireccionesSqlExtendsProvider.class, method = "getDirecciones")
	@Results({
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDDIRECCION", property = "idDireccion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DOMICILIO", property = "domicilio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGOPOSTAL", property = "codigoPostal", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TELEFONO1", property = "telefono1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TELEFONO2", property = "telefono2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MOVIL", property = "movil", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FAX1", property = "fax1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FAX2", property = "fax2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CORREOELECTRONICO", property = "correoElectronico", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PAGINAWEB", property = "paginaWeb", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHABAJA", property = "fechBaja", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "PREFERENTE", property = "preferente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPAISDIREC", property = "idPaisDirec", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPROVINCIA", property = "idProvincia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPOBLACION", property = "idPoblacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "POBLACIONEXTRANJERA", property = "poblacionExtranjera", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAMODIFICACION", property = "fechaModificacion", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "USUMODIFICACION", property = "usuModificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "OTRAPROVINCIA", property = "otraProvincia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "POBLACION", property = "poblacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PROVINCIA", property = "provincia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREPAIS", property = "nombrePais", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPAIS", property = "idPais", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCIONTIPODIRECCION", property = "descripcionTipoDireccion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPODIRECCION", property = "idTipoDireccion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "COLEGIOORIGEN", property = "colegioOrigen", jdbcType = JdbcType.VARCHAR)
	})
	List<DireccionesDTO> getDirecciones(Long idPersona, Integer idInstitucion, boolean bIncluirRegistrosConBajaLogica);
}
