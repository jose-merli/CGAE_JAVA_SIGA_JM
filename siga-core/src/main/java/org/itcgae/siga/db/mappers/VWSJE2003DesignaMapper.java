package org.itcgae.siga.db.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface VWSJE2003DesignaMapper {
   
    @Select({
    	"<script>",
        "select",
        "V.*",
        "from V_WS_JE_2003_DESIGNA V ",
        "where V.IDINSTITUCION = #{idInstitucion,jdbcType=DECIMAL} and V.IDFACTURACION= #{idFacturacion,jdbcType=VARCHAR} ",
        "<if test='IdTipoFichero !=null and IdTipoFichero.trim()==&quot;24&quot;'> ",
        "  AND V.EJG_ANIO IS NULL ",
        "</if>",
        "<if test='IdTipoFichero !=null and IdTipoFichero.trim()!=&quot;TODOS&quot; and IdTipoFichero.trim()!=&quot;NINGUNO&quot;'> ",
        " AND (V.EJG_ANIO || LPAD(V.EJG_NUMERO, 8, '0')) IN "
        + "					(SELECT E.EJG_ANIO || LPAD(E.EJG_NUMEJG, 8, '0') "
        + "					 FROM PCAJG_ALC_ACT_ERROR_CAM E "
        + "					 WHERE E.IDINSTITUCION = V.IDINSTITUCION "
        + "					 AND E.IDFACTURACION = V.IDFACTURACION "
        + "					 AND E.BORRADO = 0 "
        + "					 AND E.CODIGO_ERROR = #{IdTipoFichero,jdbcType=VARCHAR} ) ",
        "</if>",
        "</script>"
    })
   
    List<Map<String,Object>> selectByIdInstitucionAndIdFacturacionAndIdTipoFichero(
    		@Param("idInstitucion") Short idInstitucion,
    		@Param("idFacturacion") String idFacturacion,
    		@Param("IdTipoFichero") String IdTipoFichero
    		);

}