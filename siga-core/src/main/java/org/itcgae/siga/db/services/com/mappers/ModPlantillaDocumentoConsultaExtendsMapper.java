package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.db.services.com.providers.ModPlantillaDocumentoConsultaExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ModPlantillaDocumentoConsultaExtendsMapper {
	
	@SelectProvider(type = ModPlantillaDocumentoConsultaExtendsSqlProvider.class, method = "selectPlantillaDocConsultas")
	@Results({@Result(column = "IDCONSULTA", property = "idConsulta", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDOBJETIVO", property = "objetivo", jdbcType = JdbcType.VARCHAR)
	})
	List<ConsultaItem> selectPlantillaDocConsultas(Short idInstitucion, Long idModeloComunicacion, Long idPlantillaDocumento, boolean historico);

}
