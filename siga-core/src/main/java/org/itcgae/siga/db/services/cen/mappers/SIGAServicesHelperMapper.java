package org.itcgae.siga.db.services.cen.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface SIGAServicesHelperMapper{

	final String PROCESO_REVISION_LETRADO = "{ CALL  PKG_SERVICIOS_AUTOMATICOS.PROCESO_REVISION_LETRADO( #{parameters.idinstitucion, mode=IN, jdbcType=VARCHAR},#{parameters.idpersona, mode=IN, jdbcType=VARCHAR},#{parameters.fecha, mode=IN, jdbcType=VARCHAR},#{parameters.usuario, mode=IN, jdbcType=VARCHAR},#{numregistros, mode=OUT, jdbcType=VARCHAR},#{error, mode=OUT, jdbcType=VARCHAR})}";
	@Select(PROCESO_REVISION_LETRADO)
	@Options(statementType = StatementType.CALLABLE)
	public List<String> getProcesoRevisionLetrado(@Param("parameters") Map<String, String> parameters);
	
}
