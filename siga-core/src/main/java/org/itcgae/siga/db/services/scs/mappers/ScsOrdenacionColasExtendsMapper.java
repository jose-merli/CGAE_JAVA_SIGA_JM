package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.scs.ComboColaOrdenadaItem;
import org.itcgae.siga.DTO.scs.TiposActuacionItem;
import org.itcgae.siga.DTO.scs.TurnosItem;
import org.itcgae.siga.DTOs.cen.ComboInstitucionItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.ScsOrdenacioncolasMapper;
import org.itcgae.siga.db.mappers.ScsTurnoMapper;
import org.itcgae.siga.db.services.adm.providers.AdmPerfilSqlProvider;
import org.itcgae.siga.db.services.com.providers.EnvEnviosGrupoClienteExtendsSqlProvider;
import org.itcgae.siga.db.services.scs.providers.ScsOrdenacionColasSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsProcedimientosSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTurnosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsOrdenacionColasExtendsMapper extends ScsOrdenacioncolasMapper{

	@SelectProvider(type = ScsOrdenacionColasSqlExtendsProvider.class, method = "ordenColasFinal")
	@Results({@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
		@Result(column = "POR_FILAS", property = "por_filas", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ORDEN", property = "orden", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboColaOrdenadaItem> ordenColas(TurnosItem turnosItem);
	
	@SelectProvider(type = ScsOrdenacionColasSqlExtendsProvider.class, method = "ordenColas")
	@Results({@Result(column = "NUMERO", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "POR_FILAS", property = "value", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> ordenColasEnvios(String idordenacioncolas);
}
