package org.itcgae.siga.db.services.fac.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.DTO.fac.FacRegistroFichConta;
import org.itcgae.siga.db.mappers.FacRegistrofichcontaMapper;
import org.itcgae.siga.db.services.fac.providers.FacRegistroFichContaExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface FacRegistroFichContaExtendsMapper extends FacRegistrofichcontaMapper {
	
	@SelectProvider(type = FacRegistroFichContaExtendsProvider.class, method = "search")
	@Results({
		@Result(column = "IDCONTABILIDAD", property = "idContabilidad", jdbcType = JdbcType.NUMERIC),
		@Result(column = "FECHACREACION", property = "fechaCreacion", jdbcType = JdbcType.DATE),
		@Result(column = "FECHADESDE", property = "fechaExportacionDesde", jdbcType = JdbcType.DATE),
		@Result(column = "FECHAHASTA", property = "fechaExportacionHasta", jdbcType = JdbcType.DATE),
		@Result(column = "NOMBREFICHERO", property = "nombreFichero", jdbcType = JdbcType.VARCHAR),
		@Result(column = "NUMEROASIENTOS", property = "numAsientos", jdbcType = JdbcType.NUMERIC),
		@Result(column = "ESTADO", property = "estado", jdbcType = JdbcType.NUMERIC),
		@Result(column = "NOMBREESTADO", property = "nombreEstado", jdbcType = JdbcType.VARCHAR),
		@Result(column = "FECHABAJA", property = "fechaBaja", jdbcType = JdbcType.DATE),
	})
	List<FacRegistroFichConta> search(FacRegistroFichConta facRegistroFichConta, Short idInstitucion, Integer tamMaximo,String idLenguaje);
	
	@SelectProvider(type = FacRegistroFichContaExtendsProvider.class, method = "getMaxIdFacRegistroFichConta")
	@Results({
		@Result(column = "IDCONTABILIDAD", property = "idContabilidad", jdbcType = JdbcType.VARCHAR)
	})
	FacRegistroFichConta getMaxIdFacRegistroFichConta(Short idInstitucion);
}
