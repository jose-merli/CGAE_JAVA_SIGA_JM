package org.itcgae.siga.db.services.com.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.age.ComboPlantillasItem;
import org.itcgae.siga.DTOs.age.NotificacionEventoItem;
import org.itcgae.siga.DTOs.com.PlantillaEnvioItem;
import org.itcgae.siga.DTOs.com.PlantillaEnvioSearchItem;
import org.itcgae.siga.DTOs.com.TipoEnvioItem;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.db.mappers.EnvPlantillasenviosMapper;
import org.itcgae.siga.db.services.com.providers.EnvPlantillaEnviosExtendsSqlProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface EnvPlantillaEnviosExtendsMapper extends EnvPlantillasenviosMapper {
	
	@SelectProvider(type = EnvPlantillaEnviosExtendsSqlProvider.class, method = "selectPlantillas")
	@Results({@Result(column = "IDPLANTILLAENVIOS", property = "idPlantillaEnvios", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOENVIOS", property = "idTipoEnvios", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ACUSERECIBO", property = "acuseRecibo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
		@Result(column = "ASUNTO", property = "asunto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CUERPO", property = "cuerpo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDDIRECCION", property = "idDireccion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOENVIO", property = "tipoEnvio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CLASECOMUNICACION", property = "claseComunicacion", jdbcType = JdbcType.VARCHAR)
		})
	List<PlantillaEnvioItem> selectPlantillasEnvios(Short idInstitucion, String idLenguaje, PlantillaEnvioSearchItem filtros);
	
	@SelectProvider(type = EnvPlantillaEnviosExtendsSqlProvider.class, method = "selectPlantillaIdPlantilla")
	@Results({@Result(column = "IDPLANTILLAENVIOS", property = "idPlantillaEnvios", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOENVIOS", property = "idTipoEnvios", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "nombre", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ACUSERECIBO", property = "acuseRecibo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
		@Result(column = "ASUNTO", property = "asunto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "CUERPO", property = "cuerpo", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDDIRECCION", property = "idDireccion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR),
		@Result(column = "DESCRIPCION", property = "descripcion", jdbcType = JdbcType.VARCHAR),
		@Result(column = "TIPOENVIO", property = "tipoEnvio", jdbcType = JdbcType.VARCHAR)
		})
	List<PlantillaEnvioItem> selectPlantillaIdPlantilla(Short idInstitucion, PlantillaEnvioSearchItem filtros,String idPlantilla);
	
	@SelectProvider(type = EnvPlantillaEnviosExtendsSqlProvider.class, method = "getPlantillasByIdTipoEnvio")
	@Results({@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPLANTILLAENVIOS", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getPlantillasByIdTipoEnvio(Short idInstitucion, String idTipoEnvio);
	
	@SelectProvider(type = EnvPlantillaEnviosExtendsSqlProvider.class, method = "getPlantillasComunicacion")
	@Results({@Result(column = "LABEL", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "VALUE", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> getPlantillasComunicacion(Short idInstitucion, String idClaseComunicacion);
	
	@SelectProvider(type = EnvPlantillaEnviosExtendsSqlProvider.class, method = "getTipoEnvioPlantilla")
	@Results({@Result(column = "DESCRIPCION", property = "tipoEnvio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "idtipoEnvios", property = "idTipoEnvios", jdbcType = JdbcType.VARCHAR),
			@Result(column = "IDPERSONA", property = "idPersona", jdbcType = JdbcType.VARCHAR)
	})
	List<PlantillaEnvioItem> getTipoEnvioPlantilla(Short idInstitucion, String idPlantilla, String idLenguaje);
	
	@SelectProvider(type = EnvPlantillaEnviosExtendsSqlProvider.class, method = "selectMaxIDPlantillaEnvio")
	@Results({
		@Result(column = "IDMAX", property = "newId", jdbcType = JdbcType.VARCHAR)
	})
	NewIdDTO selectMaxIDPlantillas();
	
	@SelectProvider(type = EnvPlantillaEnviosExtendsSqlProvider.class, method = "selectTipoEnvioPlantilla")
	@Results({@Result(column = "DESCRIPCION", property = "tipoEnvio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "idtipoEnvios", property = "idTipoEnvio", jdbcType = JdbcType.VARCHAR)
	})
	TipoEnvioItem selectTipoEnvioPlantilla(Short idInstitucion, String lenguaje, String idPlantilla);

	@SelectProvider(type = EnvPlantillaEnviosExtendsSqlProvider.class, method = "getTemplates")
	@Results({
		@Result(column = "IDPLANTILLAENVIOS", property = "idPlantilla", jdbcType = JdbcType.NUMERIC),
		@Result(column = "IDTIPOENVIOS", property = "idTipoEnvio", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NOMBRE", property = "nombrePlantilla", jdbcType = JdbcType.VARCHAR)
	})
	List<NotificacionEventoItem> getTemplates(String idInstitucion);
	
	@SelectProvider(type = EnvPlantillaEnviosExtendsSqlProvider.class, method = "getPlantillasByIdInstitucion")
	@Results({
		@Result(column = "IDPLANTILLAENVIOS", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDTIPOENVIOS", property = "subValue", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboPlantillasItem> getPlantillasByIdInstitucion(String idInstitucion);

	@SelectProvider(type = EnvPlantillaEnviosExtendsSqlProvider.class, method = "comboPlantillasEnvio")
	@Results({
		@Result(column = "idplantillaenvios", property = "value", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nombre", property = "label", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> comboPlantillasEnvio(Short idInstitucion);
}
