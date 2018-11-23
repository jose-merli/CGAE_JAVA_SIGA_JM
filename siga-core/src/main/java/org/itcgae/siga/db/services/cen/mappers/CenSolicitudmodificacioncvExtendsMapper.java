package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.FichaDatosCurricularesItem;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.entities.CenDatoscv;
import org.itcgae.siga.db.entities.CenSolicitudmodificacioncv;
import org.itcgae.siga.db.mappers.CenDatoscvMapper;
import org.itcgae.siga.db.mappers.CenDatoscvSqlProvider;
import org.itcgae.siga.db.mappers.CenSolicitudmodificacioncvMapper;
import org.itcgae.siga.db.mappers.CenSolicitudmodificacioncvSqlProvider;
import org.itcgae.siga.db.services.adm.providers.GenRecursosCatalogosSqlExtendsProvider;
import org.itcgae.siga.db.services.cen.providers.CenDatoscvSqlExtendsProvider;
import org.itcgae.siga.db.services.cen.providers.CenSolicitudmodificacioncvSqlExtendsProvider;
import org.itcgae.siga.db.services.cen.providers.CenTiposModificacionesSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


@Service
@Primary
public interface CenSolicitudmodificacioncvExtendsMapper extends CenSolicitudmodificacioncvMapper {

	
	@SelectProvider(type = CenDatoscvSqlExtendsProvider.class, method = "searchDatosCurriculares")
	@Results({ 
		@Result(column = "FECHADESDE", property = "fechaDesde", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAHASTA", property = "fechaHasta", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAMOVIMIENTO", property = "fechaMovimiento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CATEGORIACURRICULAR", property = "categoriaCurricular", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOSUBTIPO", property = "tipoSubtipo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOCV", property = "idTipoCv", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOCVSUBTIPO1", property = "idTipoCvSubtipo1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOCVSUBTIPO2", property = "idTipoCvSubtipo2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CREDITOS", property = "creditos", jdbcType = JdbcType.VARCHAR)

	})
	List<FichaDatosCurricularesItem> searchDatosCurriculares(String idPersona, String idInstitucion);
	
	@UpdateProvider(type = CenSolicitudmodificacioncvSqlExtendsProvider.class, method = "solicitudUpdateCurriculo")
	int solicitudUpdateCurriculo(CenSolicitudmodificacioncv record);

	//	@InsertProvider(type = CenDatoscvSqlExtendsProvider.class, method = "insertCurriculo")
//	int insertCurriculo(CenDatoscv record);
	
	@SelectProvider(type = CenDatoscvSqlExtendsProvider.class, method = "getMaxIdCv")
	@Results({ @Result(column = "IDCV", property = "newId", jdbcType = JdbcType.VARCHAR)
	
	})
	NewIdDTO getMaxIdSolicitud(String idInstitucion, String idPersona);
	
	@SelectProvider(type = CenSolicitudmodificacioncvSqlExtendsProvider.class, method = "searchSolModifDatosCurriculares")
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
	List<SolModificacionItem> searchSolModifDatosCurriculares(SolicitudModificacionSearchDTO solicitudModificacionSearchDTO,
			String idLenguage, String idInstitucion);
}
