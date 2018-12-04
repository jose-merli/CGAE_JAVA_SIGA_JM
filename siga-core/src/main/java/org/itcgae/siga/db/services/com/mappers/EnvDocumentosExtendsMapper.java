package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.com.DocumentoEnvioItem;
import org.itcgae.siga.db.services.com.providers.EnvDocumentosEnvioExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface EnvDocumentosExtendsMapper {
	
	@SelectProvider(type = EnvDocumentosEnvioExtendsSqlProvider.class, method = "selectDocumentosEnvio")
	@Results({@Result(column = "IDENVIO", property = "idEnvio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDDOCUMENTO", property = "idDocumento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBREDOCUMENTO", property = "nombreDocumento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PATHDOCUMENTO", property = "pathDocumento", jdbcType = JdbcType.VARCHAR)
	})
	List<DocumentoEnvioItem> selectDocumentosEnvio(Short idInstitucion, String idEnvio);

}
