package org.itcgae.siga.db.services.scs.mappers;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.GuardiasItem;
import org.itcgae.siga.DTOs.scs.TarjetaAsistenciaResponseItem;
import org.itcgae.siga.db.entities.ScsCabeceraguardias;
import org.itcgae.siga.db.entities.ScsGuardiascolegiado;
import org.itcgae.siga.db.mappers.ScsCabeceraguardiasMapper;
import org.itcgae.siga.db.mappers.ScsCabeceraguardiasSqlProvider;
import org.itcgae.siga.db.services.scs.providers.ScsCabeceraguardiasSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsGuardiascolegiadoSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsGuardiasturnoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsCabeceraguardiasExtendsMapper extends ScsCabeceraguardiasMapper {

	

	
	@SelectProvider(type = ScsCabeceraguardiasSqlExtendsProvider.class, method = "getCabeceraGuardiasDeVariasPersonas")
	@Results({ 
		@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPAGOSJG", property = "idpagosjg", jdbcType = JdbcType.VARCHAR)
	})
	List<ScsCabeceraguardias> getCabeceraGuardiasDeVariasPersonas(String colegio, ArrayList<String> listaIdPersonas);
	
	@SelectProvider(type = ScsCabeceraguardiasSqlExtendsProvider.class, method = "busquedaGuardiasColegiado")
	@Results({ @Result(column = "idinstitucion", property = "idinstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechainicio", property = "fechadesde", jdbcType = JdbcType.DATE),
			@Result(column = "fecha_fin", property = "fechahasta", jdbcType = JdbcType.DATE),
			@Result(column = "validado", property = "validada", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idturno", property = "idTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idguardia", property = "idGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nomturno", property = "tipoTurno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nomguardia", property = "tipoGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "colegiado", property = "letradosGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numerocolegiado", property = "numColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "tipodias", property = "tipoDiasGuardia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ordengrupo", property = "ordenGrupo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "numerogrupo", property = "grupoGuardiaColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idpersona", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechavalidacion", property = "fechaValidacion", jdbcType = JdbcType.VARCHAR),})
	List<org.itcgae.siga.DTOs.scs.GuardiasItem> busquedaGuardiasColegiado(GuardiasItem guardiaItem, String idInstitucion);
	
	@UpdateProvider(type = ScsCabeceraguardiasSqlExtendsProvider.class, method = "validarSolicitudGuardia")
	int validarSolicitudGuardia(ScsCabeceraguardias record);
	
	@UpdateProvider(type = ScsCabeceraguardiasSqlExtendsProvider.class, method = "desvalidarGuardiaColegiado")
	int desvalidarGuardiaColegiado(ScsCabeceraguardias record);
	
}
