package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaItem;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesItem;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesSearchDTO;
import org.itcgae.siga.DTOs.cen.DatosIntegrantesWS;
import org.itcgae.siga.DTOs.cen.TarjetaIntegrantesCreateDTO;
import org.itcgae.siga.DTOs.cen.TarjetaIntegrantesUpdateDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.mappers.CenComponentesMapper;
import org.itcgae.siga.db.services.cen.providers.CenComponentesSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenComponentesExtendsMapper extends CenComponentesMapper {

	@SelectProvider(type = CenComponentesSqlExtendsProvider.class, method = "selectIntegrantesWS")

	@Results({ @Result(column = "idInstitucionSociedad", property = "idInstitucionSociedad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idSociedad", property = "idSociedad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAMODIFICACION", property = "fechaModificacion", jdbcType = JdbcType.DATE),
		@Result(column = "TIPOIDENTIFICACION", property = "tipoIdentificacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIFCIF", property = "nifCif", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS1", property = "apellidos1", jdbcType = JdbcType.VARCHAR),
		@Result(column = "APELLIDOS2", property = "apellidos2", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PERSONAJURIDICA", property = "personaJuridica", jdbcType = JdbcType.VARCHAR),
		@Result(column = "profesionalAbogado", property = "profesionalAbogado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "PROFESIONAL", property = "profesional", jdbcType = JdbcType.VARCHAR),
		@Result(column = "profesion", property = "profesion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "codigocolegio", property = "codigocolegio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "descripcionColegio", property = "descripcionColegio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMCOLEGIADO", property = "numColegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "SOCIO", property = "socio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "cargo", property = "cargo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "descripcionCargo", property = "descripcionCargo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHACARGO", property = "fechaCargo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJACARGO", property = "fechaBajaCargo", jdbcType = JdbcType.DATE),
		@Result(column = "SOCIEDAD", property = "sociedad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCIONCLIENTE", property = "idInstitucionCliente", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CODIGOCOLEGIOCLIENTE", property = "codigoColegioCliente", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCIONCOLEGIOCLIENTE", property = "descripcionColegioCliente", jdbcType = JdbcType.VARCHAR),
		})
	
	List<DatosIntegrantesWS> selectIntegrantesWS(DatosIntegrantesSearchDTO integrantes, String idInstitucion);
	
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
			@Result(column = "ABOGADO", property = "abogado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCIONCARGO", property = "descripcionCargo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "COLEGIO", property = "colegio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRECOLEGIO", property = "nombrecolegio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DESCRIPCIONPROFESION", property = "descripcionProfesion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PERSONAJURIDICA", property = "personaJuridica", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPROVINCIA", property = "idProvincia", jdbcType = JdbcType.VARCHAR) ,
			@Result(column = "FLAG_SOCIO", property = "flagSocio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CEN_CLIENTE_IDINSTITUCION", property = "colegio", jdbcType = JdbcType.VARCHAR)})
	List<DatosIntegrantesItem> selectIntegrantes(DatosIntegrantesSearchDTO integrantes, String idInstitucion);
	
	@UpdateProvider(type = CenComponentesSqlExtendsProvider.class, method = "updateMember")
	int updateMember(TarjetaIntegrantesUpdateDTO tarjetaIntegrantesUpdateDTO, AdmUsuarios usuario, String idInstitucion);
	
	
	@InsertProvider(type = CenComponentesSqlExtendsProvider.class, method = "insertSelectiveForcreateMember")
	int insertSelectiveForcreateMember(TarjetaIntegrantesCreateDTO tarjetaIntegrantesCreateDTO,AdmUsuarios usuario, String idInstitucion);
	
	
	@SelectProvider(type = CenComponentesSqlExtendsProvider.class, method = "selectMaxIDComponente")
	@Results({ @Result(column = "IDCOMPONENTE", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDCOMPONENTE", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	ComboItem selectMaxIDComponente(String idPersonaPadre, String idInstitucion);
	
	@SelectProvider(type = CenComponentesSqlExtendsProvider.class, method = "searchSocieties")
	@Results({
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DENOMINACION", property = "denominacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ABREVIATURA", property = "abreviatura", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHACONSTITUCION", property = "fechaConstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOSOCIEDAD", property = "tipoSociedad", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMEROINTEGRANTES", property = "numeroIntegrantes", jdbcType = JdbcType.VARCHAR)
	})
	List<BusquedaJuridicaItem> searchSocieties(String idPersona, String idLenguaje, String idInstitucion);
}
