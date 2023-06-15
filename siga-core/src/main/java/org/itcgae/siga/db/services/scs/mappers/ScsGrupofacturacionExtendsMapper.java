package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ScsGrupofacturacionMapper;
import org.itcgae.siga.db.services.scs.providers.ScsGrupofacturacionSqlExtendsProvider;


public interface ScsGrupofacturacionExtendsMapper extends ScsGrupofacturacionMapper {
	
	@SelectProvider(type= ScsGrupofacturacionSqlExtendsProvider.class, method = "getComboGrupoFacturacion")
	@Results({
		@Result(column = "IDGRUPOFACTURACION", property="value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property="label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getComboGrupoFacturacion(String idInstitucion, String idLenguaje);

	@SelectProvider(type= ScsGrupofacturacionSqlExtendsProvider.class, method = "grupoFacturacionByColegios")
	@Results({
			@Result(column = "IDGRUPOFACTURACION", property="value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCION", property="label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> grupoFacturacionByColegios(List<String> idColegios, String idLenguaje);

}
