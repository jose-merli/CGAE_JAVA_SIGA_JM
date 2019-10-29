package org.itcgae.siga.db.services.scs.mappers.guardia;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.scs.guardia.GuardiasItem;
import org.itcgae.siga.db.mappers.ScsGuardiasturnoMapper;
import org.itcgae.siga.db.services.scs.providers.guardia.ScsGuardiasturnoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsGuardiasturnoExtendsMapper extends ScsGuardiasturnoMapper{

	

	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "searchGuardias")
	@Results({ 
		@Result(column = "turno", property = "idTurno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombre", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "tipodeguardia", property = "tipoGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "obligatoriedad", property = "idinstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "duracion", property = "duracion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMEROLETRADOSGUARDIA", property = "letradosGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "numeroletradosinscritos", property = "letradosIns", jdbcType = JdbcType.VARCHAR),
		@Result(column = "diaslaborables", property = "diasLab", jdbcType = JdbcType.VARCHAR),
		@Result(column = "diasfestivos", property = "diasFes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VALIDARJUSTIFICACIONES", property = "validaJustificacion", jdbcType = JdbcType.VARCHAR),
	})
	List<GuardiasItem> searchGuardias(GuardiasItem guardiaItem , String idInstitucion, String idLenguaje);
	
	
	
}
