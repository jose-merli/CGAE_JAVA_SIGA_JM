package org.itcgae.siga.db.services.fac.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.FacturaItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.CenGruposcriteriosMapper;
import org.itcgae.siga.db.mappers.FacAbonoMapper;
import org.itcgae.siga.db.services.fac.providers.CenGruposcriteriosExtendsSqlProvider;
import org.itcgae.siga.db.services.fac.providers.FacAbonoExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface CenGruposcriteriosExtendsMapper extends CenGruposcriteriosMapper {

	@SelectProvider(type = CenGruposcriteriosExtendsSqlProvider.class, method = "getNewIdGruposCriterios")
	@Results({ @Result(column = "id", property = "newId", jdbcType = JdbcType.VARCHAR) })
	NewIdDTO getNewIdGruposCriterios(Short idInstitucion);

}
