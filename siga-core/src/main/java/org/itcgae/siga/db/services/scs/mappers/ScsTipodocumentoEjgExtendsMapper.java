package org.itcgae.siga.db.services.scs.mappers;


import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.DocumentacionEjgItem;
import org.itcgae.siga.db.mappers.ScsTipodocumentoejgMapper;
import org.itcgae.siga.db.services.scs.providers.ScsPresentadorSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTipodocumentoejgSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsTipodocumentoEjgExtendsMapper extends ScsTipodocumentoejgMapper{

	@SelectProvider(type = ScsTipodocumentoejgSqlExtendsProvider.class, method = "searchDocumento")
	@Results({ 
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.DECIMAL),
		//@Result(column = "IDTIPODOCUMENTOEJG", property = "idTipoDocumento", jdbcType = JdbcType.DECIMAL),
		@Result(column = "ABREVIATURADOC", property = "abreviaturaDoc", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ABREVIATURA", property = "abreviaturaTipoDoc", jdbcType = JdbcType.VARCHAR),      //BIEN Y EN SU SITIO		
		@Result(column = "DESCRIPCION", property = "descripcionDoc", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCIONTIPO", property = "descripcionTipoDoc", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGODESCRIPCION", property = "codigodescripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOEXT", property = "codigoExt", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPODOC", property = "idTipoDocumento", jdbcType = JdbcType.VARCHAR),
		
	})
	List<DocumentacionEjgItem> searchDocumento(DocumentacionEjgItem documentacionEjgItem, String idLenguaje);

	@SelectProvider(type = ScsTipodocumentoejgSqlExtendsProvider.class, method = "getIdTipoDocumentoEjg")
	@Results({ @Result(column = "IDTIPODOCUMENTOEJG", property = "newId", jdbcType = JdbcType.VARCHAR)
	})
	NewIdDTO getIdTipoDocumentoEjg(Short idInstitucion);
	
	@SelectProvider(type = ScsTipodocumentoejgSqlExtendsProvider.class, method = "comboTipoDocumentacion")
	@Results({ 
		@Result(column = "IDTIPODOCUMENTOEJG", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ABREVIATURA", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> comboTipoDocumentacion(String idLenguaje, Short idInstitucion);
	
	
	
	
}
