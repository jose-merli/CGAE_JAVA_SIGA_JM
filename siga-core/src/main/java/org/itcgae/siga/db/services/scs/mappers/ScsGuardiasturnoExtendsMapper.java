package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.scs.GuardiasItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.mappers.ScsGuardiasturnoMapper;
import org.itcgae.siga.db.services.scs.providers.ScsGuardiasturnoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsGuardiasturnoExtendsMapper extends ScsGuardiasturnoMapper{

	

	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "searchGuardias")
	@Results({ 
		@Result(column = "turno", property = "turno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTURNO", property = "idTurno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDGUARDIA", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombre", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "tipodeguardia", property = "tipoGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "obligatoriedad", property = "obligatoriedad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "duracion", property = "duracion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMEROLETRADOSGUARDIA", property = "letradosGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "numeroletradosinscritos", property = "letradosIns", jdbcType = JdbcType.VARCHAR),
		@Result(column = "diaslaborables", property = "diasLab", jdbcType = JdbcType.VARCHAR),
		@Result(column = "diasfestivos", property = "diasFes", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VALIDARJUSTIFICACIONES", property = "validaJustificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechabaja", jdbcType = JdbcType.VARCHAR),
	})
	List<GuardiasItem> searchGuardias(GuardiasItem guardiaItem , String idInstitucion, String idLenguaje);
	
	

	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method = "comboGuardias")
	@Results({
		@Result(column = "IDGUARDIA", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
	})
	List<ComboItem> comboGuardias(String idTurno, String idInstitucion);
	
	@SelectProvider(type = ScsGuardiasturnoSqlExtendsProvider.class, method ="detalleGuardia")
	@Results({
		@Result(column = "SELECCIONLABORABLES", property="diasLab", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SELECCIONFESTIVOS", property="diasFes", jdbcType = JdbcType.VARCHAR),
	})
	List<GuardiasItem> detalleGuardia(String idGuardia, String idInstitucion);
}
