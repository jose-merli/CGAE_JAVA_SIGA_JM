package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.SociedadCreateDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.mappers.CenColegiadoMapper;
import org.itcgae.siga.db.services.cen.providers.CenColegiadoSqlExtendsProvider;
import org.itcgae.siga.db.services.cen.providers.CenNocolegiadoSqlExtendsProvider;
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
                              @Result(column = "IDTIPOSSERGURO", property = "idTiposSeguro", jdbcType = JdbcType.NUMERIC),
                              @Result(column = "COMISIONES", property = "comisiones", jdbcType = JdbcType.VARCHAR),
                              @Result(column = "PARTIDOJUDICIAL", property = "partidoJudicial", jdbcType = JdbcType.VARCHAR),
                              @Result(column = "NUMCOLEGIADO", property = "numColegiado", jdbcType = JdbcType.VARCHAR),
                              @Result(column = "ESTADOCOLEGIAL", property = "estadoColegial", jdbcType = JdbcType.VARCHAR),
                              @Result(column = "IDTRATAMIENTO", property = "idTratamiento", jdbcType = JdbcType.VARCHAR),
                              @Result(column = "RESIDENTEINSCRITO", property = "residenteInscrito", jdbcType = JdbcType.VARCHAR),
                              @Result(column = "FECHANACIMIENTO", property = "fechaNacimiento", jdbcType = JdbcType.VARCHAR),
                              @Result(column = "CORREO", property = "correo", jdbcType = JdbcType.VARCHAR),
                              @Result(column = "TELEFONO1", property = "telefono", jdbcType = JdbcType.VARCHAR),
                              @Result(column = "MOVIL", property = "movil", jdbcType = JdbcType.VARCHAR),
                              @Result(column = "FECHA_BAJA", property = "fechaBaja", jdbcType = JdbcType.VARCHAR),
                              @Result(column = "FECHAPRESENTACION", property = "fechapresentacion", jdbcType = JdbcType.VARCHAR)

          })
          List<ColegiadoItem> selectColegiados(Short idInstitucion, ColegiadoItem colegiadoItem);

          
          @SelectProvider(type = CenColegiadoSqlExtendsProvider.class, method = "selectColegiaciones")
          @Results({ 
                    @Result(column = "FECHAINCORPORACION", property = "incorporacion", jdbcType = JdbcType.VARCHAR),
                    @Result(column = "RESIDENTEINSCRITO", property = "residenteInscrito", jdbcType = JdbcType.VARCHAR),
                    @Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
                    @Result(column = "OBSERVACIONES", property = "observaciones", jdbcType = JdbcType.VARCHAR)
          })
          List<ColegiadoItem> selectColegiaciones(Short idInstitucion, String idLenguaje, ColegiadoItem colegiadoItem);
          
          
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
                              @Result(column = "TELEFONO", property = "telefono", jdbcType = JdbcType.VARCHAR),
                              @Result(column = "MOVIL", property = "movil", jdbcType = JdbcType.VARCHAR)
          })
          List<ColegiadoItem> searchOtherCollegues(String idPersona, String idLenguaje);
}
