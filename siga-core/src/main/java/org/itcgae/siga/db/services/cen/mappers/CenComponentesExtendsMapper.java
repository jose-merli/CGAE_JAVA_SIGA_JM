package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesItem;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesSearchDTO;
import org.itcgae.siga.DTOs.cen.TarjetaIntegrantesUpdateDTO;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.CenComponentesMapper;
import org.itcgae.siga.db.services.cen.providers.CenComponentesSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenComponentesExtendsMapper extends CenComponentesMapper {

	@SelectProvider(type = CenComponentesSqlExtendsProvider.class, method = "selectIntegrantes")
	@Results({ @Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDCOMPONENTE", property = "idComponente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CARGO", property = "cargo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHACARGO", property = "fechaCargo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHABAJACARGO", property = "fechaBajaCargo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONACOMPONENTE", property = "idPersonaComponente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SOCIEDAD", property = "sociedad", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CAPITALSOCIAL", property = "capitalSocial", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHACARGOINFORME", property = "fechaCargoInforme", jdbcType = JdbcType.VARCHAR),
			@Result(column = "EJERCIENTE", property = "ejerciente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHA_HISTORICO", property = "fechaHistorico", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NIFCIF", property = "nifCif", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS1", property = "apellidos1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS2", property = "apellidos2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS", property = "apellidos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRECOMPLETO", property = "nombreCompleto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPOCOLEGIO", property = "idTipoColegio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMCOLEGIADO", property = "numColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDCARGO", property = "idCargo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPROVINCIA", property = "idProvincia", jdbcType = JdbcType.VARCHAR) })
	List<DatosIntegrantesItem> selectIntegrantes(DatosIntegrantesSearchDTO integrantes);
	
	
	
	@UpdateProvider(type = CenComponentesSqlExtendsProvider.class, method = "updateMember")
	int updateMember(TarjetaIntegrantesUpdateDTO tarjetaIntegrantesUpdateDTO, AdmUsuarios usuario, String idInstitucion);
	
}
