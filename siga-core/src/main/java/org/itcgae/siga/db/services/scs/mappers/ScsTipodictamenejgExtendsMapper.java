package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.ComboItemDictamen;
import org.itcgae.siga.db.entities.ScsTipodictamenejg;
import org.itcgae.siga.db.mappers.ScsTipodictamenejgMapper;
import org.itcgae.siga.db.services.scs.providers.ScsEjgComisionSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTipodictamenejgSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsTipodictamenejgExtendsMapper extends ScsTipodictamenejgMapper {

	
	@SelectProvider(type = ScsTipodictamenejgSqlExtendsProvider.class, method = "comboDictamen")
	@Results({ @Result(column = "IDTIPODICTAMENEJG", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
			@Result(column = "BLOQUEADO", property = "bloqueado", jdbcType = JdbcType.VARCHAR)})
	List<ComboItemDictamen> comboDictamen(String idLenguaje, String idInstitucion);
	
	/**
	 * 
	 * @param idLenguaje
	 * @param idInstitucion
	 * @return
	 */
	@SelectProvider(type = ScsTipodictamenejgSqlExtendsProvider.class, method = "estadosDictamen")
	@Results({ 
		@Result(column = "IDTIPODICTAMENEJG", property = "idtipodictamenejg", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR)
	})
	List<ScsTipodictamenejg> estadosDictamen(String idLenguaje, String idInstitucion);
}