package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import org.itcgae.siga.DTOs.cen.DatosDireccionesItem;
import org.itcgae.siga.DTOs.cen.DatosDireccionesSearchDTO;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.DTOs.cen.TarjetaIntegrantesCreateDTO;
import org.itcgae.siga.DTOs.cen.TarjetaIntegrantesUpdateDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.CenSolimodidireccionesMapper;
import org.itcgae.siga.db.services.cen.providers.CenComponentesSqlExtendsProvider;
import org.itcgae.siga.db.services.cen.providers.CenDireccionesSqlExtendsProvider;
import org.itcgae.siga.db.services.cen.providers.CenSolimodidireccionesSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenSolimodidireccionesExtendsMapper extends CenSolimodidireccionesMapper {

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
        @Result(column = "POBLACIONEXTRANJERA", property = "poblacionExtranjera", jdbcType = JdbcType.VARCHAR)

	})
	List<DatosDireccionesItem> selectDirecciones(DatosDireccionesSearchDTO datosDireccionesSearchDTO,	String idInstitucion);

	
	
	@SelectProvider(type = CenSolimodidireccionesSqlExtendsProvider.class, method = "getMaxIdSolicitud")
	@Results({ @Result(column = "IDSOLICITUD", property = "newId", jdbcType = JdbcType.VARCHAR)
	
	})NewIdDTO getMaxIdSolicitud(String idInstitucion, String idPersona);
	
	

	@SelectProvider(type = CenDireccionesSqlExtendsProvider.class, method = "selectNewIdDireccion")
	@Results({
		@Result(column = "IDDIRECCION", property = "idDireccion", jdbcType = JdbcType.VARCHAR),
	})
	List<DatosDireccionesItem> selectNewIdDireccion(String idPersona, String idInstitucion);
	
	@SelectProvider(type = CenSolimodidireccionesSqlExtendsProvider.class, method = "searchSolModifDatosDirecciones")
	@Results({ @Result(column = "ESPECIFICA", property = "especifica", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDSOLICITUD", property = "idSolicitud", jdbcType = JdbcType.VARCHAR),
		@Result(column = "MOTIVO", property = "motivo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGO", property = "codigo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAALTA", property = "fechaAlta", jdbcType = JdbcType.DATE),
		@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOMODIFICACION", property = "idTipoModificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOMODIFICACION", property = "tipoModificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMCOLEGIADO", property = "numColegiado", jdbcType = JdbcType.VARCHAR)}) 
	List<SolModificacionItem> searchSolModifDatosDirecciones(SolicitudModificacionSearchDTO solicitudModificacionSearchDTO,
			String idLenguaje, String idInstitucion, Long idPersona);
}
