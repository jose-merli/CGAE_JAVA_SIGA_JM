package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.services.com.providers.ModModeloPerfilesExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ModModeloPerfilesExtendsMapper {
	
	@SelectProvider(type = ModModeloPerfilesExtendsSqlProvider.class, method = "selectPerfilesModelo")
	@Results({@Result(column = "IDPERFIL", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> selectPerfilesModelo(Short idInstitucion, Long idModeloComunicacion);
	
}
