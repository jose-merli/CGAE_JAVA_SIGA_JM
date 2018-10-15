package org.itcgae.siga.db.services.adm.mappers;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.adm.MultiidiomaCatalogoItem;
import org.itcgae.siga.DTOs.adm.MultiidiomaCatalogoSearchDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.GenRecursosCatalogosMapper;
import org.itcgae.siga.db.services.adm.providers.GenRecursosCatalogosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface GenRecursosCatalogosExtendsMapper extends GenRecursosCatalogosMapper{
	
	@SelectProvider(type = GenRecursosCatalogosSqlExtendsProvider.class, method = "getCatalogEntity")
	@Results({
		@Result(column = "NOMBRETABLA", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "LOCAL", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getCatalogEntity(String idInstitucion);
	
	
	@SelectProvider(type = GenRecursosCatalogosSqlExtendsProvider.class, method = "getCatalogSearch")
	@Results({
		@Result(column = "IDRECURSO", property = "idRecurso", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRETABLA", property = "nombreTabla", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDRECURSOALIAS", property = "idRecursoAlias", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCIONBUSCAR", property = "descripcionBusqueda", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCIONTRADUCIR", property = "descripcionTraduccion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDLENGUAJEBUSCAR", property = "idLenguajeBuscar", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDLENGUAJETRADUCIR", property = "idLenguajeTraducir", jdbcType = JdbcType.VARCHAR)
	})
	List<MultiidiomaCatalogoItem> getCatalogSearch(int numPagina, MultiidiomaCatalogoSearchDTO multiidiomaCatalogoSearchDTO,String idInstitucion, String campoTabla);
	
	
	@InsertProvider(type = GenRecursosCatalogosSqlExtendsProvider.class, method = "insertSelectiveForCreateLegalPerson")
	int insertSelectiveForCreateLegalPerson(String idInstitucion, AdmUsuarios usuario, String grupo,String nombreTabla, String campoTabla);
	
	@SelectProvider(type = GenRecursosCatalogosSqlExtendsProvider.class, method = "getMaxIdRecurso")
    String getMaxIdRecurso();
}
