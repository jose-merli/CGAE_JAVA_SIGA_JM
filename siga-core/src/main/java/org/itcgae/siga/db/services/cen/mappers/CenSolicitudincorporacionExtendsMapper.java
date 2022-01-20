package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.itcgae.siga.DTOs.cen.SolIncorporacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudIncorporacionSearchDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.db.mappers.CenSolicitudincorporacionMapper;
import org.itcgae.siga.db.services.cen.providers.CenSolicitudincorporacionSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenSolicitudincorporacionExtendsMapper extends CenSolicitudincorporacionMapper {

	@SelectProvider(type = CenSolicitudincorporacionSqlExtendsProvider.class, method = "selectSolicitudes")
	@Results({ @Result(column = "IDSOLICITUD", property = "idSolicitud", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROIDENTIFICADOR", property = "numeroIdentificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS", property = "apellidos", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDO1", property = "apellido1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDO2", property = "apellido2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SEXO", property = "sexo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHANACIMIENTO", property = "fechaNacimiento", jdbcType = JdbcType.DATE),
			@Result(column = "NUMCOLEGIADO", property = "numColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHASOLICITUD", property = "fechaSolicitud", jdbcType = JdbcType.DATE),
			@Result(column = "FECHAESTADO", property = "fechaEstado", jdbcType = JdbcType.DATE),
			@Result(column = "ESTADOSOLICITUD", property = "estadoSolicitud", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TIPOSOLICITUD", property = "tipoSolicitud", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MODALIDAD", property = "modalidad", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDMODALIDADDOCUMENTACION", property = "idModalidadDocumentacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDESTADO", property = "idEstado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPO", property = "idTipo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TIPOCOLEGIACION", property = "tipoColegiacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TIPOIDENTIFICACION", property = "tipoIdentificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPOIDENTIFICACION", property = "idTipoIdentificacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "observaciones", property = "observaciones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAINCORPORACION", property = "fechaIncorporacion", jdbcType = JdbcType.DATE),
			@Result(column = "RESIDENTE", property = "residente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NATURALDE", property = "naturalDe", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TRATAMIENTO", property = "tratamiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTRATAMIENTO", property = "idTratamiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPOCOLEGIACION", property = "idTipoColegiacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ESTADOCIVIL", property = "estadoCivil", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDESTADOCIVIL", property = "idEstadoCivil", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PAIS", property = "pais", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPAIS", property = "idPais", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DOMICILIO", property = "domicilio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TELEFONO1", property = "telefono1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TELEFONO2", property = "telefono2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FAX1", property = "fax1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FAX2", property = "fax2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MOVIL", property = "movil", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CORREOELECTRONICO", property = "correoElectronico", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGOPOSTAL", property = "codigoPostal", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPOBLACION", property = "idPoblacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPROVINCIA", property = "idProvincia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREPOBLACION", property = "nombrePoblacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREPROVINCIA", property = "nombreProvincia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TITULAR", property = "titular", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IBAN", property = "iban", jdbcType = JdbcType.VARCHAR),
			@Result(column = "BIC", property = "bic", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBREBANCO", property = "nombreBanco", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ABONOCARGO", property = "abonoCargo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ABONOSJCS", property = "abonoJCS", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CBO_CODIGO", property = "cboCodigo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CODIGOSUCURSAL", property = "codigoSucursal", jdbcType = JdbcType.VARCHAR),
			@Result(column = "DIGITOCONTROL", property = "digitoControl", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROCUENTA", property = "numeroCuenta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "BANCO", property = "banco", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUM_REGISTRO", property = "numRegistro", jdbcType = JdbcType.VARCHAR),
	@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR)
			})
	List<SolIncorporacionItem> getSolicitudes(SolicitudIncorporacionSearchDTO solIncorporacionSearchDTO, String idLenguage);
	
	@SelectProvider(type = CenSolicitudincorporacionSqlExtendsProvider.class, method = "getMaxIdSolicitud")
	@Results({ @Result(column = "IDSOLICITUD", property = "idMax", jdbcType = JdbcType.NUMERIC)})
	MaxIdDto getMaxIdRecurso();
	
	@SelectProvider(type = CenSolicitudincorporacionSqlExtendsProvider.class, method = "getMaxNColegiado")
	@Results({ @Result(column = "NCOLEGIADO", property = "valor", jdbcType = JdbcType.VARCHAR)})
	StringDTO getMaxNColegiado(String idInstitucion); 

	@SelectProvider(type = CenSolicitudincorporacionSqlExtendsProvider.class, method = "getMaxNComunitario")
	@Results({ @Result(column = "NCOMUNITARIO", property = "valor", jdbcType = JdbcType.VARCHAR)})
	StringDTO getMaxNComunitario(String idInstitucion); 
	
	@SelectProvider(type = CenSolicitudincorporacionSqlExtendsProvider.class, method = "getMaxNColegiadoComunitario")
	@Results({ @Result(column = "NCOLEGIADO", property = "valor", jdbcType = JdbcType.VARCHAR)})
	StringDTO getMaxNColegiadoComunitario(String idInstitucion); 
	
}