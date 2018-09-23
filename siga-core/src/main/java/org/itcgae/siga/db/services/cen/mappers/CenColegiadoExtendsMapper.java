package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.FichaDatosColegialesItem;
import org.itcgae.siga.db.mappers.CenColegiadoMapper;
import org.itcgae.siga.db.services.cen.providers.CenColegiadoSqlExtendsProvider;
import org.springframework.stereotype.Service;


@Service
public interface CenColegiadoExtendsMapper extends CenColegiadoMapper{

	
	@SelectProvider(type = CenColegiadoSqlExtendsProvider.class, method = "selectDatosColegiales")
	@Results({
		@Result(column = "NCOLEGIADO", property = "nColegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAINCORPORACION", property = "fechaIncorporacion", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAPRESENTACION", property = "fechaPresentacion", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAJURA", property = "fechaJura", jdbcType = JdbcType.DATE),
		@Result(column = "FECHATITULACION", property = "fechaTitulacion", jdbcType = JdbcType.DATE),
		@Result(column = "SITUACIONRESIDENTE", property = "situacionResidente", jdbcType = JdbcType.VARCHAR),
		@Result(column = "COMUNITARIO", property = "comunitario", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR)
	})
	List<FichaDatosColegialesItem> selectDatosColegiales(String idPersona, String idInstitucion);
}
