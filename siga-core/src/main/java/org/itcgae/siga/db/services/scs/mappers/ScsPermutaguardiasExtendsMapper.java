package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.GuardiasItem;
import org.itcgae.siga.DTOs.scs.PermutaItem;
import org.itcgae.siga.db.mappers.ScsPermutaguardiasMapper;
import org.itcgae.siga.db.services.scs.providers.ScsPermutaguardiasSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsTurnosSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsPermutaguardiasExtendsMapper extends ScsPermutaguardiasMapper {
	
	@SelectProvider(type = ScsPermutaguardiasSqlExtendsProvider.class, method = "getPermutaColeg")
	@Results({ 
		@Result(column = "FECHACONFIRMACION", property = "fechaconfirmacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHASOLICITUD", property = "fechasolicitud", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TURNOPERMUTA", property = "nombreTurno", jdbcType = JdbcType.VARCHAR),
		@Result(column = "GUARDIADESTINO", property = "nombreGuardia", jdbcType = JdbcType.VARCHAR),
		@Result(column = "MOTIVOS", property = "motivos", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombreColegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.VARCHAR),	
		@Result(column = "IDGUARDIA_CONFIRMADOR", property = "idguardiaConfirmador", jdbcType = JdbcType.VARCHAR)
	})
	List<PermutaItem> getPermutaColeg(PermutaItem permutaItem, Short idInstitucion);
	
	@SelectProvider(type = ScsPermutaguardiasSqlExtendsProvider.class, method = "getTurnoInscrito")
	@Results({ @Result(column = "IDTURNO", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR), })
	List<ComboItem> getTurnoInscrito(Long idpersona, Short idinstitucion);

	
	@SelectProvider(type = ScsPermutaguardiasSqlExtendsProvider.class, method = "getGuardiaDestinoInscrito")
	@Results({ @Result(column = "IDGUARDIA", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR), })
	List<ComboItem> getGuardiaDestinoInscrito(String idTurno, Short idinstitucion);
}
