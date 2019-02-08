package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.db.mappers.CenSolmodifacturacionservicioMapper;
import org.itcgae.siga.db.services.cen.providers.CenSolModifFacturacionServicioSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenSolModifFacturacionServicioExtendsMapper extends CenSolmodifacturacionservicioMapper{

	@SelectProvider(type = CenSolModifFacturacionServicioSqlExtendsProvider.class, method = "searchSolModifDatosFacturacion")
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
	List<SolModificacionItem> searchSolModifDatosFacturacion(SolicitudModificacionSearchDTO solicitudModificacionSearchDTO,
			String idLenguage, String idInstitucion, Long idPersona);
}
