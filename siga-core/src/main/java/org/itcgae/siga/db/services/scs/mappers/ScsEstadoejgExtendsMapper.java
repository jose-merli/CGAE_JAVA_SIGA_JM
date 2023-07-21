package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.EstadoEjgItem;
import org.itcgae.siga.db.entities.ScsEstadoejg;
import org.itcgae.siga.db.entities.ScsEstadoejgKey;
import org.itcgae.siga.db.mappers.ScsEstadoejgMapper;
import org.itcgae.siga.db.mappers.ScsEstadoejgSqlProvider;
import org.itcgae.siga.db.services.scs.providers.ScsEstadoejgSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsEstadoejgExtendsMapper extends ScsEstadoejgMapper{

          @SelectProvider(type = ScsEstadoejgSqlExtendsProvider.class, method = "getEstadoEjg")
          @Results({ 
                    @Result(column = "IDESTADOEJG", property = "idEstadoejg", jdbcType = JdbcType.VARCHAR),
                    @Result(column = "FECHAINICIO", property = "fechaInicio", jdbcType = JdbcType.VARCHAR),
                    @Result(column = "FECHAMODIFICACION", property = "fechaModificacion", jdbcType = JdbcType.VARCHAR),
                    @Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
          })
          EstadoEjgItem getEstadoEjg(AsuntosClaveJusticiableItem asuntoClave, String idLenguaje);
          
              @SelectProvider(type = ScsEstadoejgSqlExtendsProvider.class, method = "comboEstadoEjg")
              @Results({
                  @Result(column = "IDESTADOEJG", property = "value", jdbcType = JdbcType.VARCHAR),
                  @Result(column = "DESCRIPCION", property = "label", jdbcType = JdbcType.VARCHAR),
              })
              List<ComboItem> comboEstadoEjg(Short idLenguaje);
              
            @SelectProvider(type = ScsEstadoejgSqlExtendsProvider.class, method = "getEstados")
          	@Results({ 
          		@Result(column = "fechainicio", property = "fechaInicio", jdbcType = JdbcType.DATE),
          		@Result(column = "fechamodificacion", property = "fechaModificacion", jdbcType = JdbcType.DATE),
          		@Result(column = "idestadoejg", property = "idEstadoejg", jdbcType = JdbcType.VARCHAR),
          		@Result(column = "descripcion", property = "descripcion", jdbcType = JdbcType.VARCHAR),
          		@Result(column = "observaciones", property = "observaciones", jdbcType = JdbcType.VARCHAR),
          		@Result(column = "automatico", property = "automatico", jdbcType = JdbcType.VARCHAR),
          		@Result(column = "visiblecomision", property = "propietario", jdbcType = JdbcType.VARCHAR),
          		@Result(column = "usuariomod", property = "user", jdbcType = JdbcType.VARCHAR),
          		@Result(column = "idinstitucion", property = "idinstitucion", jdbcType = JdbcType.VARCHAR),
          		@Result(column = "idtipoejg", property = "idtipoejg", jdbcType = JdbcType.VARCHAR),
          		@Result(column = "numero", property = "numero", jdbcType = JdbcType.VARCHAR),
          		@Result(column = "fechabaja", property = "fechabaja", jdbcType = JdbcType.VARCHAR),
          		@Result(column = "anio", property = "anio", jdbcType = JdbcType.VARCHAR),
          		@Result(column = "idestadoporejg", property = "idestadoporejg", jdbcType = JdbcType.VARCHAR),
          	})
          	List<EstadoEjgItem> getEstados(EjgItem ejgItem, String idInstitucion, String idLenguaje);
            
            @SelectProvider(type = ScsEstadoejgSqlExtendsProvider.class, method = "getUltEstadoEjg")
          	@Results({ 
          		@Result(column = "fechainicio", property = "fechaInicio", jdbcType = JdbcType.DATE),
          		@Result(column = "fechamodificacion", property = "fechaModificacion", jdbcType = JdbcType.DATE),
          		@Result(column = "idestadoejg", property = "idEstadoejg", jdbcType = JdbcType.VARCHAR),
          		@Result(column = "descripcion", property = "descripcion", jdbcType = JdbcType.VARCHAR),
          		@Result(column = "observaciones", property = "observaciones", jdbcType = JdbcType.VARCHAR),
          		@Result(column = "automatico", property = "automatico", jdbcType = JdbcType.VARCHAR),
          		@Result(column = "visiblecomision", property = "propietario", jdbcType = JdbcType.VARCHAR),
          		@Result(column = "usuariomod", property = "user", jdbcType = JdbcType.VARCHAR),
          		@Result(column = "idinstitucion", property = "idinstitucion", jdbcType = JdbcType.VARCHAR),
          		@Result(column = "idtipoejg", property = "idtipoejg", jdbcType = JdbcType.VARCHAR),
          		@Result(column = "numero", property = "numero", jdbcType = JdbcType.VARCHAR),
          		@Result(column = "fechabaja", property = "fechabaja", jdbcType = JdbcType.VARCHAR),
          		@Result(column = "anio", property = "anio", jdbcType = JdbcType.VARCHAR),
          		@Result(column = "idestadoporejg", property = "idestadoporejg", jdbcType = JdbcType.VARCHAR),
          	})
            EstadoEjgItem getUltEstadoEjg(EjgItem ejgItem, String idInstitucion);
            
            @SelectProvider(type = ScsEstadoejgSqlExtendsProvider.class, method = "getEditResolEjg")
          	@Results({ 
          		@Result(column = "EDITABLECOMISION",  jdbcType = JdbcType.VARCHAR)
          	})
            String getEditResolEjg(EjgItem ejgItem, String idInstitucion);
            
        	@UpdateProvider(type = ScsEstadoejgSqlExtendsProvider.class, method = "bajaEstadoEjg")
        	int bajaEstadoEjg(ScsEstadoejg estado);
}
