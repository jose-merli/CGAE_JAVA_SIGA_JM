package org.itcgae.siga.db.services.fac.mappers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.SerieFacturacionItem;
import org.itcgae.siga.DTO.fac.UsosSufijosItem;
import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.ComboItemConsulta;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.FacGrupcritincluidosenserieMapper;
import org.itcgae.siga.db.mappers.FacSeriefacturacionMapper;
import org.itcgae.siga.db.services.fac.providers.FacGrupcritincluidosenserieExtendsSqlProvider;
import org.itcgae.siga.db.services.fac.providers.FacSeriefacturacionExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public interface FacGrupcritincluidosenserieExtendsMapper extends FacGrupcritincluidosenserieMapper {

	@SelectProvider(type = FacGrupcritincluidosenserieExtendsSqlProvider.class, method = "getConsultasSerie")
	@Results({
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCONSULTA", property = "idConsulta", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCONSULTAANTERIOR", property = "idConsultaAnterior", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOCONSULTA", property = "tipoConsulta", jdbcType = JdbcType.VARCHAR),
		@Result(column = "OBSERVACIONES", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCLASECOMUNICACION", property = "idClaseComunicacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDOBJETIVO", property = "idObjetivo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDMODULO", property = "idModulo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SENTENCIA", property = "sentencia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "GENERAL", property = "generica", jdbcType = JdbcType.VARCHAR),
	})
	List<ConsultaItem> getConsultasSerie(String idSerieFacturacion, Short idInstitucion);

	@SelectProvider(type = FacGrupcritincluidosenserieExtendsSqlProvider.class, method = "comboDestinatarios")
	@Results({
			@Result(column = "nombre", property = "label", jdbcType = JdbcType.VARCHAR),
			@Result(column = "id", property = "value", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> comboDestinatarios(Short idInstitucion);

}
