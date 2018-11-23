package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SoliModiDireccionesItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.db.mappers.CenSolimodidireccionesMapper;
import org.itcgae.siga.db.services.cen.providers.CenSoliModiDireccionesSqlExtendsProvider;
import org.itcgae.siga.db.services.cen.providers.CenTiposModificacionesSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenSoliModiDireccionesExtendsMapper extends CenSolimodidireccionesMapper {

//	@SelectProvider(type = CenSoliModiDireccionesSqlExtendsProvider.class, method = "selectSoliModiDirecciones")
//	@Results({ @Result(column = "IDSOLICITUD", property = "idSolicitud", jdbcType = JdbcType.VARCHAR),
//			@Result(column = "IDDIRECCION", property = "idDireccion", jdbcType = JdbcType.VARCHAR),
//			@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
//			@Result(column = "MOTIVO", property = "motivo", jdbcType = JdbcType.VARCHAR),
//			//@Result(column = "FECHAMODIFICACION", property = "fechaModificacion", jdbcType = JdbcType.VARCHAR),
//			@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.VARCHAR),
//			@Result(column = "FECHAALTA", property = "fechaAlta", jdbcType = JdbcType.VARCHAR),
//			//@Result(column = "PREFERENTE", property = "preferente", jdbcType = JdbcType.VARCHAR),
//			@Result(column = "CODIGOPOSTAL", property = "codigoPostal", jdbcType = JdbcType.VARCHAR),
//			@Result(column = "TELEFONO1", property = "telefono", jdbcType = JdbcType.VARCHAR),
//			@Result(column = "DOMICILIO", property = "domicilio", jdbcType = JdbcType.VARCHAR),
//			@Result(column = "FAX1", property = "fax", jdbcType = JdbcType.VARCHAR),
//			@Result(column = "CORREOELECTRONICO", property = "correoElectronico", jdbcType = JdbcType.VARCHAR),
//			@Result(column = "PAGINAWEB", property = "paginaWeb", jdbcType = JdbcType.VARCHAR),
//			@Result(column = "PAIS", property = "pais", jdbcType = JdbcType.VARCHAR),
//			@Result(column = "PROVINCIA", property = "provincia", jdbcType = JdbcType.VARCHAR),
//			@Result(column = "POBLACION", property = "poblacion", jdbcType = JdbcType.VARCHAR),
//	@Result(column = "POBLACIONEXTRANJERA", property = "poblacionExtranjera", jdbcType = JdbcType.VARCHAR) })
//	SoliModiDireccionesItem selectSoliModiDirecciones(String idSolicitud, String idLenguaje);

	
	@SelectProvider(type = CenSoliModiDireccionesSqlExtendsProvider.class, method = "searchSolModifDatosDirecciones")
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
			String idLenguaje, String idInstitucion);
}
