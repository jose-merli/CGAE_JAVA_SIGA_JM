package org.itcgae.siga.db.services.scs.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.ScsGrupoguardiacolegiadoMapper;
import org.itcgae.siga.db.services.scs.providers.ScsGrupoguardiacolegiadoSqlExtendsProvider;

public interface ScsGrupoguardiacolegiadoExtendsMapper extends ScsGrupoguardiacolegiadoMapper{

	@SelectProvider(type=ScsGrupoguardiacolegiadoSqlExtendsProvider.class, method ="getLastId")
	@Results({
		@Result(column="IDGRUPOGUARDIACOLEGIADO", property="newId")
	})
	public NewIdDTO getLastId();

}
