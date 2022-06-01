package org.itcgae.siga.db.services.scs.mappers;

import org.apache.ibatis.annotations.*;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.ScsGrupoguardiacolegiadoMapper;
import org.itcgae.siga.db.services.scs.providers.ScsGrupoguardiacolegiadoSqlExtendsProvider;

public interface ScsGrupoguardiacolegiadoExtendsMapper extends ScsGrupoguardiacolegiadoMapper{

	@SelectProvider(type=ScsGrupoguardiacolegiadoSqlExtendsProvider.class, method ="getLastId")
	@Results({
		@Result(column="IDGRUPOGUARDIACOLEGIADO", property="newId")
	})
	public NewIdDTO getLastId();

	@UpdateProvider(type=ScsGrupoguardiacolegiadoSqlExtendsProvider.class, method ="updateOrdenGrupoColegiadoPrimero")
	public boolean updateOrdenGrupoColegiadoPrimero(Integer idInstitucion, Integer idCalendarioGuardias, Integer idTurno, Integer idGuardia);

	@UpdateProvider(type=ScsGrupoguardiacolegiadoSqlExtendsProvider.class, method ="updateOrdenGrupoColegiadoSegundo")
	public boolean updateOrdenGrupoColegiadoSegundo(Integer idInstitucion, Integer idCalendarioGuardias, Integer idTurno, Integer idGuardia, Integer usuModificacion);

	@DeleteProvider(type=ScsGrupoguardiacolegiadoSqlExtendsProvider.class, method ="deleteRegistrosGrupoGuardiaCol")
	public boolean deleteRegistrosGrupoGuardiaCol(Integer idCalendarioGuardias);

}
