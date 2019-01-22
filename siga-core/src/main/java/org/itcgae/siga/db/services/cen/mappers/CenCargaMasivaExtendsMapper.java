package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTOs.cen.CargaMasivaItem;
import org.itcgae.siga.db.mappers.CenCargamasivaMapper;
import org.itcgae.siga.db.services.cen.providers.CenCargaMasivaSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface CenCargaMasivaExtendsMapper extends CenCargamasivaMapper {

	@SelectProvider(type = CenCargaMasivaSqlExtendsProvider.class, method = "selectEtiquetas")
	@Results({ @Result(column = "NOMBREFICHERO", property = "nombreFichero", jdbcType = JdbcType.VARCHAR),
			@Result(column = "USUARIO", property = "usuario", jdbcType = JdbcType.VARCHAR),
			@Result(column = "REGISTROSCORRECTOS", property = "registrosCorrectos", jdbcType = JdbcType.NUMERIC),
			@Result(column = "REGISTROSERRONEOS", property = "registrosErroneos", jdbcType = JdbcType.NUMERIC),
			@Result(column = "FECHACARGA", property = "fechaCarga", jdbcType = JdbcType.DATE),
			@Result(column = "IDFICHERO", property = "idFichero", jdbcType = JdbcType.NUMERIC),
			@Result(column = "IDFICHEROLOG", property = "idFicheroLog", jdbcType = JdbcType.NUMERIC),
			@Result(column = "TIPOCARGA", property = "tipoCarga", jdbcType = JdbcType.VARCHAR),
	})
	List<CargaMasivaItem> selectEtiquetas(Short idInstitucion, CargaMasivaItem cargaMasivaItem);


}
