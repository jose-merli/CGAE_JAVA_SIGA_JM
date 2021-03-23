package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.AsuntosClaveJusticiableItem;
import org.itcgae.siga.DTOs.scs.AsuntosJusticiableItem;
import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.db.mappers.ScsDesignaMapper;
import org.itcgae.siga.db.services.com.providers.ConModulosExtendsSqlProvider;
import org.itcgae.siga.db.services.scs.providers.ScsDesignacionesSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsDesignacionesExtendsMapper extends ScsDesignaMapper{

	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "searchClaveDesignaciones")
	@Results({ @Result(column = "ANIO", property = "anio", jdbcType = JdbcType.DATE),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CLAVE", property = "clave", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ROL", property = "rol", jdbcType = JdbcType.VARCHAR), 
			@Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR) 
	})
	List<AsuntosClaveJusticiableItem> searchClaveDesignaciones(AsuntosJusticiableItem asuntosJusticiableItem, Integer tamMaximo);
	
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "busquedaDesignaciones")
	@Results({ @Result(column = "ANIO", property = "anio", jdbcType = JdbcType.DATE),
			@Result(column = "IDINSTITUCION", property = "idInstitucion", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NUMERO", property = "numero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CLAVE", property = "clave", jdbcType = JdbcType.VARCHAR),
//			@Result(column = "ROL", property = "rol", jdbcType = JdbcType.VARCHAR), 
			@Result(column = "TIPO", property = "tipo", jdbcType = JdbcType.VARCHAR) 
	})
	List<DesignaItem> busquedaDesignaciones(DesignaItem designaItem, Short idInstitucion, Integer tamMaximo);
	
	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "comboModulos")
	@Results({@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPROCEDIMIENTO", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> comboModulos(Short idInstitucion);


	@SelectProvider(type = ScsDesignacionesSqlExtendsProvider.class, method = "comboProcedimientos")
	@Results({@Result(column = "NOMBRE", property = "label", jdbcType = JdbcType.VARCHAR),
		@Result(column = "IDPROCEDIMIENTO", property = "value", jdbcType = JdbcType.VARCHAR)
	})
	List<ComboItem> comboProcedimientos(Short idInstitucion);
}
	

