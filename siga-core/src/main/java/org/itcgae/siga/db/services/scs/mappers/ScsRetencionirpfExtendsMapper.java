package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.RetencionIRPFItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.ScsMaestroretencionesMapper;
import org.itcgae.siga.db.services.scs.providers.ScsRetencionirpfSqlExtendsProvider;


public interface ScsRetencionirpfExtendsMapper extends ScsMaestroretencionesMapper{
	
	@SelectProvider(type = ScsRetencionirpfSqlExtendsProvider.class, method = "searchRetenciones")
	@Results({
		@Result(column = "IDRETENCION", property = "idRetencion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "RETENCION", property = "retencion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "LETRANIFSOCIEDAD", property = "tipoSociedad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CLAVEM190", property = "claveModelo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDRECURSO", property = "idDescripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCIONsoc", property = "descripcionSociedad", jdbcType = JdbcType.VARCHAR),
	})
	public List<RetencionIRPFItem> searchRetenciones(String idLenguaje, RetencionIRPFItem retencionItem);

	@SelectProvider(type = ScsRetencionirpfSqlExtendsProvider.class, method = "getIdRetencion")
	@Results({
		@Result(column = "IDRETENCION", property = "newId", jdbcType = JdbcType.VARCHAR)
	})
	public NewIdDTO getIdRetencion();
}
