package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.ComboEtiquetasItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.CenGruposclienteCliente;
import org.itcgae.siga.db.entities.CenGruposclienteClienteExample;
import org.itcgae.siga.db.mappers.CenGruposclienteClienteMapper;
import org.itcgae.siga.db.services.cen.providers.CenGruposclienteClienteSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenGruposclienteClienteExtendsMapper extends CenGruposclienteClienteMapper{

	@InsertProvider(type = CenGruposclienteClienteSqlExtendsProvider.class, method = "insertSelectiveForCreateLegalPerson")
	int insertSelectiveForCreateLegalPerson(ComboEtiquetasItem etiqueta, String idInstitucion, String grupo, String idUsuario);
	
	
	
	@SelectProvider(type = CenGruposclienteClienteSqlExtendsProvider.class, method = "selectGruposPersonaJuridica")
	@Results({
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGRUPO", property = "idGrupo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHA_INICIO", property = "fechaInicio", jdbcType = JdbcType.DATE),
		@Result(column = "FECHA_BAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.DATE)
	})

	//List<ComboItem> selectGruposPersonaJuridica(String idPersona, String idInstitucion);
	List<ComboEtiquetasItem> selectGruposPersonaJuridica(String idPersona, String idInstitucion);
	
	@SelectProvider(type = CenGruposclienteClienteSqlExtendsProvider.class, method = "selectGruposPersonaJuridicaLenguaje")
	@Results({
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGRUPO", property = "idGrupo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHA_INICIO", property = "fechaInicio", jdbcType = JdbcType.DATE),
		@Result(column = "FECHA_BAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.DATE)
	})

	//List<ComboItem> selectGruposPersonaJuridica(String idPersona, String idInstitucion);
	List<ComboEtiquetasItem> selectGruposPersonaJuridicaLenguaje(String idPersona, String idInstitucion, String idLenguaje);
	
	@SelectProvider(type = CenGruposclienteClienteSqlExtendsProvider.class, method = "selectGrupos")
	@Results({
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGRUPO", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> selectGruposEtiquetas(String idlenguaje, String idInstitucion);
	

	@InsertProvider(type = CenGruposclienteClienteSqlExtendsProvider.class, method = "insertSelectiveForUpdateLegalPerson")
	int insertSelectiveForUpdateLegalPerson(ComboEtiquetasItem etiqueta, String idPersona, String idInstitucionEtiqueta, String idInstitucion, String idUsuario);
	
	@UpdateProvider(type = CenGruposclienteClienteSqlExtendsProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") CenGruposclienteCliente record,
			@Param("example") CenGruposclienteClienteExample example);

}
