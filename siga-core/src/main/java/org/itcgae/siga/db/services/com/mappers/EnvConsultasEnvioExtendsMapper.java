package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;
import org.itcgae.siga.db.services.com.providers.EnvConsultasEnvioExtendsSqlProvider;

public interface EnvConsultasEnvioExtendsMapper {

	
	@SelectProvider(type = EnvConsultasEnvioExtendsSqlProvider.class, method = "selectPlantillasByEnvio")
	List<String> selectPlantillasByEnvio(String idInstitucion, String idEnvio);
}
