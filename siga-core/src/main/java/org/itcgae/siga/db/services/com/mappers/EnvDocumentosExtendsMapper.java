package org.itcgae.siga.db.services.com.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.com.DocumentoEnvioItem;
import org.itcgae.siga.db.mappers.EnvDocumentosMapper;
import org.itcgae.siga.db.services.com.providers.EnvDocumentosEnvioExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface EnvDocumentosExtendsMapper extends EnvDocumentosMapper {

	@SelectProvider(type = EnvDocumentosEnvioExtendsSqlProvider.class, method = "selectDocumentosEnvio")
	@Results({@Result(column = "IDENVIO", property = "idEnvio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDDOCUMENTO", property = "idDocumento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NOMBREDOCUMENTO", property = "nombreDocumento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PATHDOCUMENTO", property = "pathDocumento", jdbcType = JdbcType.VARCHAR)
	})
	List<DocumentoEnvioItem> selectDocumentosEnvio(Short idInstitucion, String idEnvio);

	@SelectProvider(type = EnvDocumentosEnvioExtendsSqlProvider.class, method = "getNewIdDocumento")
	Integer getNewIdDocumento(String idInstitucion, String idEnvio);
}
