package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.ComboColaOrdenadaItem;
import org.itcgae.siga.db.mappers.ScsOrdenacioncolasMapper;
import org.itcgae.siga.db.services.scs.providers.ScsOrdenacionColasSqlExtendsProvider;
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
	List<ComboColaOrdenadaItem> ordenColas(String idordenacioncolas);
	
	@SelectProvider(type = ScsOrdenacionColasSqlExtendsProvider.class, method = "ordenColas")
	@Results({@Result(column = "NUMERO", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "POR_FILAS", property = "value", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> ordenColasEnvios(String idordenacioncolas);
	
	@SelectProvider(type = ScsOrdenacionColasSqlExtendsProvider.class, method = "getIdOrdenacion")
	@Results({ @Result(column = "IDORDENACIONCOLAS", property = "newId", jdbcType = JdbcType.VARCHAR)
	})
	NewIdDTO getIdOrdenacion();
}
