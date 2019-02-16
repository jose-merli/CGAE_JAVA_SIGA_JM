package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.FichaDatosCurricularesItem;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.entities.CenDatoscv;
import org.itcgae.siga.db.entities.CenSolicitmodifdatosbasicos;
import org.itcgae.siga.db.entities.CenSolicitudmodificacioncv;
import org.itcgae.siga.db.mappers.CenDatoscvMapper;
import org.itcgae.siga.db.mappers.CenDatoscvSqlProvider;
import org.itcgae.siga.db.mappers.CenSolicitmodifdatosbasicosMapper;
import org.itcgae.siga.db.mappers.CenSolicitudmodificacioncvMapper;
import org.itcgae.siga.db.mappers.CenSolicitudmodificacioncvSqlProvider;
import org.itcgae.siga.db.services.adm.providers.GenRecursosCatalogosSqlExtendsProvider;
import org.itcgae.siga.db.services.cen.providers.CenDatoscvSqlExtendsProvider;
import org.itcgae.siga.db.services.cen.providers.CenSolicitmodifdatosbasicosSqlExtendsProvider;
import org.itcgae.siga.db.services.cen.providers.CenSolicitudmodificacioncvSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


@Service
@Primary
public interface CenSolicitmodifdatosbasicosExtendsMapper extends  CenSolicitmodifdatosbasicosMapper {

	//	@InsertProvider(type = CenDatoscvSqlExtendsProvider.class, method = "insertCurriculo")
//	int insertCurriculo(CenDatoscv record);
	
	@SelectProvider(type = CenSolicitmodifdatosbasicosSqlExtendsProvider.class, method = "searchSolModifDatosGenerales")
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
	List<SolModificacionItem> searchSolModifDatosGenerales(SolicitudModificacionSearchDTO solicitudModificacionSearchDTO,
			String idLenguaje, String idInstitucion, Long idPersona);
	
	@SelectProvider(type = CenSolicitmodifdatosbasicosSqlExtendsProvider.class, method = "getMaxIdSolicitud")
	@Results({ @Result(column = "IDSOLICITUD", property = "newId", jdbcType = JdbcType.VARCHAR)
	
	})
	NewIdDTO getMaxIdSolicitud(String idInstitucion, String idPersona);
	
	@SelectProvider(type = CenSolicitmodifdatosbasicosSqlExtendsProvider.class, method = "getAutoAceptar")
		@Results({ @Result(column = "VALOR", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VALOR", property = "value", jdbcType = JdbcType.VARCHAR)})
	List<ComboItem> getAutoAceptar(String idInstitucion);
	
	
	@Select({ "select", "IDSOLICITUD, MOTIVO, PUBLICIDAD, GUIAJUDICIAL, ABONOS, CARGOS, IDINSTITUCION, ",
		"IDPERSONA, IDLENGUAJE, FECHAMODIFICACION, USUMODIFICACION, IDESTADOSOLIC, FECHAALTA",
		"from CEN_SOLICITMODIFDATOSBASICOS", "where IDSOLICITUD = #{idsolicitud,jdbcType=DECIMAL}" })
@Results({ @Result(column = "IDSOLICITUD", property = "idsolicitud", jdbcType = JdbcType.DECIMAL, id = true),
		@Result(column = "MOTIVO", property = "motivo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PUBLICIDAD", property = "publicidad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "GUIAJUDICIAL", property = "guiajudicial", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ABONOS", property = "abonos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CARGOS", property = "cargos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
		@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL),
		@Result(column = "IDLENGUAJE", property = "idlenguaje", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
		@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
		@Result(column = "IDESTADOSOLIC", property = "idestadosolic", jdbcType = JdbcType.DECIMAL),
		@Result(column = "FECHAALTA", property = "fechaalta", jdbcType = JdbcType.TIMESTAMP) })
CenSolicitmodifdatosbasicos selectByPrimaryKeyDTO(Long idsolicitud);
	
}
