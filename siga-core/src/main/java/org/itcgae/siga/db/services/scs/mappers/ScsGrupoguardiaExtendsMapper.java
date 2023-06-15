package org.itcgae.siga.db.services.scs.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.ScsGrupoguardiaMapper;
import org.itcgae.siga.db.services.scs.providers.ScsGrupoguardiaSqlExtendsProvider;

public interface ScsGrupoguardiaExtendsMapper extends ScsGrupoguardiaMapper{

	@SelectProvider(type=ScsGrupoguardiaSqlExtendsProvider.class, method ="getLastId")
	@Results({
		@Result(column="IDGRUPOGUARDIA", property="newId")
	})
	NewIdDTO getLastId();

}
