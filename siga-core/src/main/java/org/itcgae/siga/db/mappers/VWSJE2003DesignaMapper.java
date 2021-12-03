package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface VWSJE2003DesignaMapper {
   
    @Select({
        "select",
        "V.*",
        "from V_WS_JE_2003_DESIGNA V ",
        "where V.IDINSTITUCION = #{idInstitucion,jdbcType=DECIMAL} and V.IDFACTURACION= #{idFacturacion,jdbcType=VARCHAR} ",
    })
   
    List<Map<String,Object>> selectByIdInstitucionAndIdFacturacion(
    		@Param("idInstitucion") Short idInstitucion,
    		@Param("idFacturacion") String idFacturacion );

}