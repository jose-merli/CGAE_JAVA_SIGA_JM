package org.itcgae.siga.db.services.adm.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.adm.EtiquetaItem;
import org.itcgae.siga.DTOs.adm.EtiquetaSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.GenDiccionarioMapper;
import org.itcgae.siga.db.services.adm.providers.GenDiccionarioSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface GenDiccionarioExtendsMapper extends GenDiccionarioMapper{

	@SelectProvider(type = GenDiccionarioSqlExtendsProvider.class, method = "getLabelLenguage")
	@Results({
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDLENGUAJE", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getLabelLenguage();
	

	
	@SelectProvider(type = GenDiccionarioSqlExtendsProvider.class, method = "searchLabels")
	@Results({
		@Result(column = "IDRECURSO", property = "idRecurso", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCIONBUSCAR", property = "descripcionBusqueda", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDLENGUAJEBUSCAR", property = "idLenguajeBuscar", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDLENGUAJETRADUCIR", property = "idLenguajeTraducir", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCIONTRADUCIR", property = "descripcionTraduccion", jdbcType = JdbcType.VARCHAR)
	})
	List<EtiquetaItem> searchLabels(int numPagina, EtiquetaSearchDTO etiquetaSearchDTO);
}
