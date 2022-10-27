package org.itcgae.siga.db.services.scs.mappers;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.FichaPersonaItem;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.LetradoDesignaItem;
import org.itcgae.siga.db.entities.ScsDesignasletrado;
import org.itcgae.siga.db.mappers.ScsDesignasletradoMapper;
import org.itcgae.siga.db.mappers.ScsDesignasletradoSqlProvider;
import org.itcgae.siga.db.services.scs.providers.ScsDesignasLetradoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsDesignasLetradoExtendsMapper extends ScsDesignasletradoMapper{

	@SelectProvider(type = ScsDesignasLetradoSqlExtendsProvider.class, method = "getDesignaLetrado")
	@Results({ 
		@Result(column = "NCOLEGIADO", property = "numeroColegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDO1", property = "apellido1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDO2", property = "apellido2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "COLEGIADO", property = "colegiado", jdbcType = JdbcType.VARCHAR),
	})
	FichaPersonaItem getDesignaLetrado(AsuntosClaveJusticiableItem asuntoClave, String idLenguaje);
	
	@SelectProvider(type = ScsDesignasLetradoSqlExtendsProvider.class, method = "getDesignaLetradoPorFecha")
	@Results({ 
		@Result(column = "NCOLEGIADO", property = "numeroColegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDO1", property = "apellido1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDO2", property = "apellido2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "COLEGIADO", property = "colegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
	})
	List<LetradoDesignaItem> getDesignaLetradoPorFecha(AsuntosClaveJusticiableItem asuntoClave);
	
	@UpdateProvider(type = ScsDesignasLetradoSqlExtendsProvider.class, method = "updateFechaDesignasLetrado")
	int updateFechaDesignasLetrado(ScsDesignasletrado designaUpdate,Date fechaOriginal);
	
	
	
}
