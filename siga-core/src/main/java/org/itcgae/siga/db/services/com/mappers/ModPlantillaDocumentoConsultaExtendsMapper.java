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
	
	@SelectProvider(type = ModPlantillaDocumentoConsultaExtendsSqlProvider.class, method = "selectConsultasByInforme")
	@Results({@Result(column = "IDCONSULTA", property = "idConsulta", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "OBJETIVO", property = "objetivo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDOBJETIVO", property = "idObjetivo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPLANTILLACONSULTA", property = "idPlantillaConsulta", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR)
	})
	List<ConsultaItem> selectConsultasByInforme(Short idInstitucion, Long idModeloComunicacion, Long idInforme, String idLenguaje, boolean historico);

	@Results({@Result(column = "IDCONSULTA", property = "idConsulta", jdbcType = JdbcType.VARCHAR),
			  @Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
			  @Result(column = "SENTENCIA", property = "sentencia", jdbcType = JdbcType.VARCHAR)
	})	
	@SelectProvider(type = ModPlantillaDocumentoConsultaExtendsSqlProvider.class, method = "selectConsultaPorObjetivo")
	List<ConsultaItem> selectConsultaPorObjetivo(Short idInstitucion, Long idModeloComunicacion, String idPlantillaDocumento, Long idObjetivo);

	@SelectProvider(type = ModPlantillaDocumentoConsultaExtendsSqlProvider.class, method = "selectCountConsultaPorObjetivo")
	int selectCountConsultaPorObjetivo(Short idInstitucion, Long idModeloComunicacion, Long idPlantillaDocumento, Long idObjetivo);

	
	@SelectProvider(type = ModPlantillaDocumentoConsultaExtendsSqlProvider.class, method = "selectConsultaByIdConsulta")
	@Results({
		@Result(column = "IDPLANTILLACONSULTA", property = "idPlantillaConsulta", jdbcType = JdbcType.VARCHAR),
		@Result(column = "REGION", property = "region", jdbcType = JdbcType.VARCHAR)
	})
	List<ConsultaItem> selectConsultaByIdConsulta(Short idInstitucion, Long idModeloComunicacion, Long idInforme, Long idConsulta, Long idPlantillaDocumento);
	
}
