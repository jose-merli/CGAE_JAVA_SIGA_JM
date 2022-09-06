package org.itcgae.siga.db.services.scs.mappers;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.ExpedienteEconomicoItem;
import org.itcgae.siga.db.entities.ScsEejgPeticiones;
import org.itcgae.siga.db.mappers.ScsEejgPeticionesMapper;
import org.itcgae.siga.db.services.scs.providers.ScsEejgPeticionesSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsEejgPeticionesExtendsMapper extends ScsEejgPeticionesMapper{

	@SelectProvider(type = ScsEejgPeticionesSqlExtendsProvider.class, method = "getPeticionesPorEJG")
	@Results({@Result(column = "IDPETICION", property = "idpeticion", jdbcType = JdbcType.DECIMAL, id = true),
		@Result(column = "IDUSUARIOPETICION", property = "idusuariopeticion", jdbcType = JdbcType.DECIMAL),
		@Result(column = "FECHAPETICION", property = "fechapeticion", jdbcType = JdbcType.TIMESTAMP),
		@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.DECIMAL),
		@Result(column = "IDSOLICITUD", property = "idsolicitud", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idinstitucion", jdbcType = JdbcType.DECIMAL),
		@Result(column = "IDTIPOEJG", property = "idtipoejg", jdbcType = JdbcType.DECIMAL),
		@Result(column = "ANIO", property = "anio", jdbcType = JdbcType.DECIMAL),
		@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.DECIMAL),
		@Result(column = "IDPERSONA", property = "idpersona", jdbcType = JdbcType.DECIMAL),
		@Result(column = "NUMEROINTENTOSSOLICITUD", property = "numerointentossolicitud", jdbcType = JdbcType.DECIMAL),
		@Result(column = "NUMEROINTENTOSCONSULTA", property = "numerointentosconsulta", jdbcType = JdbcType.DECIMAL),
		@Result(column = "IDXML", property = "idxml", jdbcType = JdbcType.DECIMAL),
		@Result(column = "FECHAMODIFICACION", property = "fechamodificacion", jdbcType = JdbcType.TIMESTAMP),
		@Result(column = "USUMODIFICACION", property = "usumodificacion", jdbcType = JdbcType.DECIMAL),
		@Result(column = "FECHASOLICITUD", property = "fechasolicitud", jdbcType = JdbcType.TIMESTAMP),
		@Result(column = "FECHACONSULTA", property = "fechaconsulta", jdbcType = JdbcType.TIMESTAMP),
		@Result(column = "IDIOMA", property = "idioma", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMEROINTENTOSPENDIENTEINFO", property = "numerointentospendienteinfo", jdbcType = JdbcType.DECIMAL),
		@Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDO1", property = "apellido1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDO2", property = "apellido2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR),
		@Result(column = "RUTA_PDF", property = "rutaPdf", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDECOMCOLA", property = "idecomcola", jdbcType = JdbcType.DECIMAL),
		@Result(column = "MSGERROR", property = "msgerror", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CSV", property = "csv", jdbcType = JdbcType.VARCHAR)})
	List<ScsEejgPeticiones> getPeticionesPorEJG(EjgItem ejg);
	
	@SelectProvider(type = ScsEejgPeticionesSqlExtendsProvider.class, method = "getMaxIdpeticion")
	@Results({})
	BigDecimal getMaxIdpeticion();
	
	@SelectProvider(type = ScsEejgPeticionesSqlExtendsProvider.class, method = "getUltimoIdPeticion")
	@Results({})
	String getUltimoIdPeticion();

	@SelectProvider(type = ScsEejgPeticionesSqlExtendsProvider.class, method = "getExpedientesEconomicos")
	@Results({
			@Result(column = "justiciable", property = "justiciable", jdbcType = JdbcType.VARCHAR),
			@Result(column = "solicitadopor", property = "solicitadoPor", jdbcType = JdbcType.VARCHAR),
			@Result(column = "fechasolicitud", property = "f_solicitud", jdbcType = JdbcType.DATE),
			@Result(column = "fechapeticion", property = "f_recepcion", jdbcType = JdbcType.DATE),
			@Result(column = "idEstado", property = "idEstado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "estado", property = "estado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "csv", property = "csv", jdbcType = JdbcType.VARCHAR),
	})
	List<ExpedienteEconomicoItem> getExpedientesEconomicos(EjgItem ejgItem, String idInstitucion, Integer tamMaximo, String idLenguaje);
}
