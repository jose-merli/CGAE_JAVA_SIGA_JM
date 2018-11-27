package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.CenSolicitudesmodificacionMapper;
import org.itcgae.siga.db.services.cen.providers.CenSolicitmodifdatosbasicosSqlExtendsProvider;
import org.itcgae.siga.db.services.cen.providers.CenSolicitudesmodificacionSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenSolicitudesModificacionExtendsMapper extends CenSolicitudesmodificacionMapper{

	@SelectProvider(type = CenSolicitudesmodificacionSqlExtendsProvider.class, method = "searchSolModif")
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
			@Result(column = "NUMCOLEGIADO", property = "numColegiado", jdbcType = JdbcType.VARCHAR) })
	List<SolModificacionItem> searchSolModif(SolicitudModificacionSearchDTO solicitudModificacionSearchDTO,
			String idLenguage, String idInstitucion);
	
	@SelectProvider(type = CenSolicitudesmodificacionSqlExtendsProvider.class, method = "getMaxIdSolicitud")
	@Results({ @Result(column = "IDSOLICITUD", property = "newId", jdbcType = JdbcType.VARCHAR)
	
	})
	NewIdDTO getMaxIdSolicitud(String idInstitucion, String idPersona);
}
