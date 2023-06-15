package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.DocumentacionEjgItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.ScsDocumentoejgMapper;
import org.itcgae.siga.db.services.scs.providers.ScsDocumentoejgSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsPresentadorSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTipodocumentoejgSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsDocumentoejgExtendsMapper extends ScsDocumentoejgMapper{

	@SelectProvider(type = ScsDocumentoejgSqlExtendsProvider.class, method = "searchDocumento")
	@Results({ 
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.DECIMAL),
		@Result(column = "IDTIPODOC", property = "idTipoDocumento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDDOCUMENTO", property = "idDocumento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DOCCODIGOEXT", property = "codigoExt", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcionDoc", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ABREVIATURADOC", property = "abreviaturaDoc", jdbcType = JdbcType.VARCHAR),
		
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGODESCRIPCION", property = "codigodescripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCODIGODESCRIPCION", property = "idCodigoDescripcion", jdbcType = JdbcType.VARCHAR),
	})
	List<DocumentacionEjgItem> searchDocumento(DocumentacionEjgItem documentacionEjgItem, String idLenguaje);
	
	@SelectProvider(type = ScsDocumentoejgSqlExtendsProvider.class, method = "getIdDocumentoEjg")
	@Results({ @Result(column = "IDDOCUMENTOEJG", property = "newId", jdbcType = JdbcType.VARCHAR)
	})
	NewIdDTO getIdDocumentoEjg(Short idInstitucion);
	
	@SelectProvider(type = ScsDocumentoejgSqlExtendsProvider.class, method = "comboDocumentos")
	@Results({ 
		@Result(column = "IDDOCUMENTOEJG", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> comboDocumentos(String idLenguaje, Short idInstitucion, String idTipoDocumentacion);

}
