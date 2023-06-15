package org.itcgae.siga.db.services.scs.mappers;


import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.DTOs.scs.DocumentacionEjgItem;
import org.itcgae.siga.DTOs.scs.EjgDocumentacionItem;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.db.entities.ScsDocumentacionejg;
import org.itcgae.siga.db.mappers.ScsTipodocumentoejgMapper;
import org.itcgae.siga.db.services.scs.providers.ScsDocumentacionejgExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTipodocumentoejgSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsDocumentacionEjgExtendsMapper extends ScsTipodocumentoejgMapper{

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

	/**
	 * 
	 * @param idInstitucion
	 * @return
	 */
	@SelectProvider(type = ScsTipodocumentoejgSqlExtendsProvider.class, method = "getIdTipoDocumentoEjg")
	@Results({ @Result(column = "IDTIPODOCUMENTOEJG", property = "newId", jdbcType = JdbcType.VARCHAR)
	})
	NewIdDTO getIdTipoDocumentoEjg(Short idInstitucion);
	
	@SelectProvider(type = ScsDocumentacionejgExtendsProvider.class, method = "getDocumentacion")
	@Results({ 
		@Result(column = "fechalimite", property = "flimite_presentacion", jdbcType = JdbcType.DATE),
		@Result(column = "presentador", property = "presentador", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombrecompleto", property = "presentador_persona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "parentesco", property = "parentesco", jdbcType = JdbcType.VARCHAR),
		@Result(column = "documentacion", property = "descripcionDoc", jdbcType = JdbcType.VARCHAR),
		@Result(column = "regentrada", property = "regEntrada", jdbcType = JdbcType.VARCHAR),
		@Result(column = "regsalida", property = "regSalida", jdbcType = JdbcType.VARCHAR),
		@Result(column = "fechaentrega", property = "f_presentacion", jdbcType = JdbcType.DATE),
		@Result(column = "comisionajg", property = "propietario", jdbcType = JdbcType.VARCHAR),
		@Result(column = "propietarioDes", property = "propietarioDes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idtipodocumento", property = "idTipoDocumento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "iddocumento", property = "idDocumento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "iddocumentacion", property = "idDocumentacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idMaestroPresentador", property = "idMaestroPresentador", jdbcType = JdbcType.INTEGER),
		@Result(column = "idFichero", property = "idFichero", jdbcType = JdbcType.INTEGER),
		@Result(column = "nombreFichero", property = "nombreFichero", jdbcType = JdbcType.INTEGER),
		@Result(column = "labelDocumento", property = "labelDocumento", jdbcType = JdbcType.VARCHAR),
	})
	List<EjgDocumentacionItem> getDocumentacion(EjgItem ejgItem, String idInstitucion, Integer tamMaximo, String idLenguaje);

	@SelectProvider(type = ScsDocumentacionejgExtendsProvider.class, method = "getDocumentacionParaEnviarPericles")
	List<ScsDocumentacionejg> getDocumentacionParaEnviarPericles(Short idInstitucion, Short idTipoEjg, Short anio, Long numero, boolean reenviar);
	
}