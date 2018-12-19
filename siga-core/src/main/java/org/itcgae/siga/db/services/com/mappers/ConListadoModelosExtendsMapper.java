package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.itcgae.siga.DTOs.com.ModelosComunicacionItem;
import org.itcgae.siga.db.services.com.providers.ConListadoModelosExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ConListadoModelosExtendsMapper {

	@SelectProvider(type = ConListadoModelosExtendsSqlProvider.class, method = "selectListadoModelos")
	@Results({		})
	List<ModelosComunicacionItem> selectListadoModelos(Short idInstitucion,String idConsulta);
}
