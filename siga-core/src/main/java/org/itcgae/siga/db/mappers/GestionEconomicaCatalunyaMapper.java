package org.itcgae.siga.db.mappers;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface GestionEconomicaCatalunyaMapper {

	@Select ("<script>"
			+ "SELECT "
			+ "       I.TIPO_INTERCAMBIO || '_' || C.COD_ORIGEN_INTERCAMBIO || '_' ||"
			+ "       C.COD_DESTINO_INTERCAMBIO || '_' || C.IDENTIFICADOR_INTERCAMBIO || '_' ||"
			+ "       TO_CHAR(C.FECHA_INTERCAMBIO, 'YYYYMMDD') || '_' ||"
			+ "        <choose >"
			+ "                <when test=\"params.detalles != null\" >"
			+ "                 	#{params.detalles}"
			+ "                </when>"
			+ "                <otherwise>"
			+ "                I.NUMERO_DETALLES"
			+ "                </otherwise>"
			+ "        </choose>"
			+ "        ||'.xml'"
			+ "  FROM JE_CABECERA C, JE_INTERCAMBIO I, FCS_JE_JUST_ESTADO JU"
			+ " WHERE "
			+ "	<choose>"
			+ "          	<when test=\"params.idEstado == 30\" >"
			+ "				I.IDJECABECERA_CICAC = C.IDJECABECERA"
			+ "				AND JU.IDESTADO = 30"
			+ "            </when>"
			+ "            <when test=\"params.idEstado == 70\" >"
			+ "				I.IDJECABECERA_GEN = C.IDJECABECERA"
			+ "				AND JU.IDESTADO = 70"
			+ "            </when>"
			+ "         	<otherwise>"
			+ "           		nvl(I.IDJECABECERA_CICAC,I.IDJECABECERA_GEN) = C.IDJECABECERA"
			+ "              </otherwise>"
			+ "        </choose>"
			+ "   AND JU.IDJEINTERCAMBIO = I.IDJEINTERCAMBIO"
			+ "   AND JU.IDJUSTIFICACION= #{params.idJustificacion}"
			+ "</script>")
	String getNombreXmlJustificacion(@Param("params") Map<String, Object> parametrosMap);
    
    
	String getNombreXmlDevolucion(Map<String, Object> parametrosMap);
	String getNombreXmlCertificacionIca(Map<String, Object> parametrosMap);
	String getNombreXmlCertificacionAnexo(Map<String, Object> parametrosMap);
	
}