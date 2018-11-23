package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.FichaDatosCurricularesItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.entities.CenDatoscv;
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
	
	@SelectProvider(type = CenSolicitmodifdatosbasicosSqlExtendsProvider.class, method = "getMaxIdSolicitud")
	@Results({ @Result(column = "IDSOLICITUD", property = "newId", jdbcType = JdbcType.VARCHAR)
	
	})
	NewIdDTO getMaxIdSolicitud(String idInstitucion, String idPersona);
	
	
}
