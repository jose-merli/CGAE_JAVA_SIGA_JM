package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.db.mappers.CenColegiadoMapper;
import org.itcgae.siga.db.services.cen.providers.CenColegiadoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenColegiadoExtendsMapper extends CenColegiadoMapper{
		
	@SelectProvider(type = CenColegiadoSqlExtendsProvider.class, method = "selectColegiados")
	@Results({
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NIFCIF", property = "nif", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMCOLEGIADO", property = "numColegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ESTADOCOLEGIAL", property = "estadoColegial", jdbcType = JdbcType.VARCHAR),
		@Result(column = "RESIDENTEINSCRITO", property = "residenteInscrito", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHANACIMIENTO", property = "fechaNacimiento", jdbcType = JdbcType.DATE),
		@Result(column = "CORREO", property = "correo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TELEFONO1", property = "telefono", jdbcType = JdbcType.VARCHAR),
		@Result(column = "MOVIL", property = "movil", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHA_BAJA", property = "fechaBaja", jdbcType = JdbcType.VARCHAR)
	})
	List<ColegiadoItem> selectColegiados(Short idInstitucion, ColegiadoItem colegiadoItem);
	
	}
