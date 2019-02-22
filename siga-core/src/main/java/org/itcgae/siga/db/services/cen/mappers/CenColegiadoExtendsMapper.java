package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.ComboColegiadoItem;
import org.itcgae.siga.DTOs.cen.FichaDatosColegialesItem;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.mappers.CenColegiadoMapper;
import org.itcgae.siga.db.services.cen.providers.CenColegiadoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


@Service
@Primary
public interface CenColegiadoExtendsMapper extends CenColegiadoMapper {

	@SelectProvider(type = CenColegiadoSqlExtendsProvider.class, method = "selectColegiados")
	@Results({ @Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.NUMERIC),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NIFCIF", property = "nif", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SOLONOMBRE", property = "soloNombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS1", property = "apellidos1", jdbcType = JdbcType.VARCHAR),
			@Result(column = "APELLIDOS2", property = "apellidos2", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SEXO", property = "sexo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDESTADOCIVIL", property = "idEstadoCivil", jdbcType = JdbcType.NUMERIC),
			@Result(column = "IDTIPOIDENTIFICACION", property = "idTipoIdentificacion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NATURALDE", property = "naturalDe", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDLENGUAJE", property = "idLenguaje", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ASIENTOCONTABLE", property = "asientoContable", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NMUTUALISTA", property = "nMutualista", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SITUACION", property = "situacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAINCORPORACION", property = "incorporacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAJURA", property = "fechaJura", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHATITULACION", property = "fechaTitulacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAALTA", property = "fechaAlta", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTIPOSSEGURO", property = "idTiposSeguro", jdbcType = JdbcType.VARCHAR),
			@Result(column = "COMISIONES", property = "comisiones", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PARTIDOJUDICIAL", property = "partidoJudicial", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMCOLEGIADO", property = "numColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMCOLEGIADO", property = "numberColegiado", jdbcType = JdbcType.NUMERIC),
			@Result(column = "ESTADOCOLEGIAL", property = "estadoColegial", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDTRATAMIENTO", property = "idTratamiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "RESIDENTEINSCRITO", property = "residenteInscrito", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHANACIMIENTO", property = "fechaNacimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CORREO", property = "correo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TELEFONO", property = "telefono", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MOVIL", property = "movil", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHA_BAJA", property = "fechaBaja", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHAPRESENTACION", property = "fechapresentacion", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOAPARECERREDABOGACIA", property = "noAparecerRedAbogacia", jdbcType = JdbcType.VARCHAR),
			@Result(column = "SITUACIONRESIDENTE", property = "situacionResidente", jdbcType = JdbcType.VARCHAR),
			@Result(column = "COMUNITARIO", property = "comunitario", jdbcType = JdbcType.VARCHAR),
			@Result(column = "COLEGIORESULTADO", property = "colegioResultado", jdbcType = JdbcType.VARCHAR)


	})
	List<ColegiadoItem> selectColegiados(Short idInstitucion, ColegiadoItem colegiadoItem);

	
	@SelectProvider(type = CenColegiadoSqlExtendsProvider.class, method = "selectColegiaciones")
	@Results({ 
		@Result(column = "FECHAINCORPORACION", property = "incorporacion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "RESIDENTEINSCRITO", property = "residenteInscrito", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDESTADO", property = "idEstado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAESTADO", property = "fechaEstadoStr", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAESTADODATE", property = "fechaEstado", jdbcType = JdbcType.DATE),
		@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR)
	})
	List<ColegiadoItem> selectColegiaciones(Short idInstitucion, String idLenguaje, ColegiadoItem colegiadoItem);
	
	
	@UpdateProvider(type = CenColegiadoSqlExtendsProvider.class, method = "updateColegiado")
	int updateColegiado(CenColegiado record);
	
	@InsertProvider(type = CenColegiadoSqlExtendsProvider.class, method = "insertSelectiveForCreateNewColegiado")
	int insertSelectiveForCreateNewColegiado(String idInstitucion, AdmUsuarios usuario,CenColegiado cenColegiado);
	
	@SelectProvider(type = CenColegiadoSqlExtendsProvider.class, method = "getLabel")
	@Results({ 
		@Result(column = "IDGRUPO", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR)
		})

	List<ComboItem> getLabel(AdmUsuarios usuario);

	@SelectProvider(type = CenColegiadoSqlExtendsProvider.class, method = "searchOtherCollegues")
	@Results({ @Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NIF", property = "nif", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NUMEROCOLEGIADO", property = "numColegiado", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
			@Result(column = "RESIDENTEINSCRITO", property = "residenteInscrito", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ESTADOCOLEGIAL", property = "estadoColegial", jdbcType = JdbcType.VARCHAR),
			@Result(column = "FECHANACIMIENTO", property = "fechaNacimiento", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CORREOELECTRONICO", property = "correo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CORREO", property = "correo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "TELEFONO", property = "telefono", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MOVIL", property = "movil", jdbcType = JdbcType.VARCHAR),
			@Result(column = "INSTITUCION", property = "institucion", jdbcType = JdbcType.VARCHAR)

	})
	List<ColegiadoItem> searchOtherCollegues(String idPersona, String idLenguaje);

	
	@SelectProvider(type = CenColegiadoSqlExtendsProvider.class, method = "selectDatosColegiales")
	@Results({
		@Result(column = "NCOLEGIADO", property = "nColegiado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHAINCORPORACION", property = "fechaIncorporacion", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAPRESENTACION", property = "fechaPresentacion", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAJURA", property = "fechaJura", jdbcType = JdbcType.DATE),
		@Result(column = "FECHATITULACION", property = "fechaTitulacion", jdbcType = JdbcType.DATE),
		@Result(column = "SITUACIONRESIDENTE", property = "situacionResidente", jdbcType = JdbcType.VARCHAR),
		@Result(column = "COMUNITARIO", property = "comunitario", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR)
	})
	List<FichaDatosColegialesItem> selectDatosColegiales(String idPersona, String idInstitucion);
	
	
	@SelectProvider(type = CenColegiadoSqlExtendsProvider.class, method = "selectColegiacionesIdPersona")
	@Results({ 
		@Result(column = "IDINSTITUCION", property = "valor", jdbcType = JdbcType.VARCHAR),
	})
	List<StringDTO> selectColegiacionesIdPersona(Long idPersona);


	@SelectProvider(type = CenColegiadoSqlExtendsProvider.class, method = "getLabelColegios")
	@Results({ @Result(column = "IDINSTITUCION", property = "value", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NCOLEGIADO", property = "nColegiado", jdbcType = JdbcType.VARCHAR)})
	List<ComboColegiadoItem> getLabelColegios(String idPersona);


}
