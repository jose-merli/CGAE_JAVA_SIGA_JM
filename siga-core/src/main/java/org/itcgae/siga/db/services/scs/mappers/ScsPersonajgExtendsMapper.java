package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTO.scs.JusticiableBusquedaItem;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.ScsPersonajgMapper;
import org.itcgae.siga.db.services.scs.providers.ScsPersonajgSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsPersonajgExtendsMapper extends ScsPersonajgMapper{

	@SelectProvider(type = ScsPersonajgSqlExtendsProvider.class, method = "searchIdPersonaJusticiables")
	@Results({ 
		@Result(column = "IDPERSONA", property = "valor", jdbcType = JdbcType.VARCHAR),
	})
	List<StringDTO> searchIdPersonaJusticiables(JusticiableBusquedaItem justiciableBusquedaItem, Short idInstitucion, String tamMax);
	
	@SelectProvider(type = ScsPersonajgSqlExtendsProvider.class, method = "searchJusticiables")
	@Results({ 
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAMODIFICACION", property = "fechaModificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ASUNTOS", property = "asuntos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRESOLO", property = "nombreSolo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS", property = "apellidos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMEROASUNTOS", property = "numeroAsuntos", jdbcType = JdbcType.VARCHAR),	
		@Result(column = "ULTIMOASUNTO", property = "ultimoAsunto", jdbcType = JdbcType.VARCHAR)
	})
	List<JusticiableBusquedaItem> searchJusticiables(List<StringDTO> justiciableBusquedaItems, Short idInstitucion);
	
	@SelectProvider(type = ScsPersonajgSqlExtendsProvider.class, method = "getIdPersonajg")
	@Results({ @Result(column = "IDPERSONA", property = "newId", jdbcType = JdbcType.VARCHAR)
	})
	NewIdDTO getIdPersonajg(Short idInstitucion);
	
	@SelectProvider(type = ScsPersonajgSqlExtendsProvider.class, method = "searchClaveAsuntosJusticiable")
	@Results({ 
		@Result(column = "FECHAMODIFICACION", property = "fechaModificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),	
		@Result(column = "CLAVE", property = "clave", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ROL", property = "rol", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR)
	})
	List<AsuntosClaveJusticiableItem> searchClaveAsuntosJusticiable(String idPersona, Short idInstitucion);
}
