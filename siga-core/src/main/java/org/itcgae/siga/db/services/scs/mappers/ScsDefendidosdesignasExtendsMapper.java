package org.itcgae.siga.db.services.scs.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.db.entities.ScsDesigna;
import org.itcgae.siga.db.mappers.ScsDefendidosdesignaMapper;
import org.itcgae.siga.db.services.scs.providers.ScsDefendidosdesignasSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsDefendidosdesignasExtendsMapper extends ScsDefendidosdesignaMapper{

	@SelectProvider(type = ScsDefendidosdesignasSqlExtendsProvider.class, method = "getInteresadosDesigna")
	@Results({ 
		@Result(column = "INTERESADOS", property = "valor", jdbcType = JdbcType.VARCHAR)
	})
	StringDTO getInteresadosDesigna(AsuntosClaveJusticiableItem asuntoClave, String idLenguaje);
	
	@SelectProvider(type = ScsDefendidosdesignasSqlExtendsProvider.class, method = "getIdInteresadoDesigna")
	@Results({ 
		@Result(column = "IDPERSONA", property = "idPersonaInteresada", jdbcType = JdbcType.VARCHAR)
	})
	String getIdInteresadoDesigna(ScsDesigna designa, Short idInstitucion);
}
