package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ScsRenunciaMapper;
import org.itcgae.siga.db.services.scs.providers.ScsRenunciaSqlExtendsProvider;

public interface ScsRenunciaExtendsMapper extends ScsRenunciaMapper{

	@SelectProvider(type = ScsRenunciaSqlExtendsProvider.class, method = "comboRenuncia")
	@Results({ 
		@Result(column = "IDRENUNCIA", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> comboRenuncia(String idLenguaje, String idInstitucion);


}
