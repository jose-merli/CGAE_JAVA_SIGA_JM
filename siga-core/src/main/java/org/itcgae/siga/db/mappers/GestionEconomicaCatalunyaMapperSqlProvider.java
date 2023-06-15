package org.itcgae.siga.db.mappers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class GestionEconomicaCatalunyaMapperSqlProvider {

	private static final String PREFIJO_SQL = "SELECT I.TIPO_INTERCAMBIO || '_' || C.COD_ORIGEN_INTERCAMBIO || '_' "
			+ "||  C.COD_DESTINO_INTERCAMBIO || '_' || C.IDENTIFICADOR_INTERCAMBIO || '_' "
			+ "|| TO_CHAR(C.FECHA_INTERCAMBIO, 'YYYYMMDD') || '_' || ";;
			
	public static final String DECODE_ESTADO_ICA = "10, 10,12, 12,14,14,16, 16,20, 20, 30, 30, 60, 60, 95, 95, 30";

	private static final String DECODE_ESTADO_CONSELL = "30,30,40,40,50,50,60,60,62,62,65,65,70,70,87,87,90,90,95,95,70";

	public String getNombreXmlJustificacion(Map<String, Object> params) {
		String sSelect = calculaSSelect(params);
		String sWhere = calculaSWhere(Integer.valueOf(params.get("idEstado").toString()));
		SQL sql = new SQL().SELECT(sSelect)
				.FROM("JE_CABECERA C, JE_INTERCAMBIO I, FCS_JE_JUST_ESTADO EST")
				.WHERE(sWhere, "EST.IDJUSTIFICACION= #{params.idJustificacion}");
		return sql.toString();
	}

	public String getNombreXmlDevolucion(Map<String, Object> params) {
		String sSelect = calculaSSelect(params);
		String sWhere = calculaSWhere(Integer.valueOf(params.get("idEstado").toString()));
		SQL sql = new SQL().SELECT(sSelect)
				.FROM("JE_CABECERA C, JE_INTERCAMBIO I, FCS_JE_DEV_ESTADO EST")
				.WHERE(sWhere, "EST.IDDEVOLUCION =  #{params.idDevolucion}");
		return sql.toString();
	}

	public String getNombreXmlCertificacionIca(Map<String, Object> params) {
		String sSelect = calculaSSelect(params);
		String sWhere = calculaSWhere(Integer.valueOf(params.get("idEstado").toString()));
		SQL sql = new SQL().SELECT(sSelect)
				.FROM("JE_CABECERA C, JE_INTERCAMBIO I, FCS_JE_CERT_ESTADO EST")
				.WHERE(sWhere, "EST.IDCERTIFICACION= #{params.idCertificacionIca}");
		return sql.toString();
	}


	public String getNombreXmlCertificacionAnexo(Map<String, Object> params) {
		String sSelect = calculaSSelect(params);
		String sWhere = calculaSWhere(Integer.valueOf(params.get("idEstado").toString()));
		SQL sql = new SQL().SELECT(sSelect)
				.FROM("JE_CABECERA C, JE_INTERCAMBIO I, FCS_JE_CERTANEXO_ESTADO EST")
				.WHERE(sWhere, "EST.IDCERTIFICACIONANEXO= #{params.idCertificacionAnexo}");
		return sql.toString();
	}
	
	public static void main(String[] args) {
		GestionEconomicaCatalunyaMapperSqlProvider g = new GestionEconomicaCatalunyaMapperSqlProvider();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idEstado", 30);
		params.put("idJustificacion", 30);
		params.put("detalles", "meh");
		
		String so = g.getCabeceraIntercambio(params);
		
		System.out.println(so);
		
	}


	private String calculaSSelect(Map<String, Object> params) {
		String sDetalles = params.containsKey("detalles") && params.get("detalles") != null ? "#{detalles}"
				: "I.NUMERO_DETALLES";
		return PREFIJO_SQL + sDetalles + "||'.xml'";
	}

	private String calculaSWhere(Integer idEstado) {
		String sWhere = "nvl(I.IDJECABECERA_CICAC,I.IDJECABECERA_GEN) = C.IDJECABECERA";
		if (idEstado != null) {
			if (idEstado.equals(30)) {
				sWhere = "I.IDJECABECERA_CICAC = C.IDJECABECERA AND EST.IDESTADO = 30";
			} else if (idEstado.equals(70)) {
				sWhere = "I.IDJECABECERA_GEN = C.IDJECABECERA AND EST.IDESTADO = 70";
			}
		}
		return sWhere + " AND EST.IDJEINTERCAMBIO = I.IDJEINTERCAMBIO ";
	}

	
	public String getCabeceraIntercambio(Map<String, Object> params){
	 SQL sql = new SQL()
			 .SELECT("INTE.IDINTERCAMBIO, INTE.IDINSTITUCION, INTE.DESCRIPCION, INTE.ANIO, INTE.IDESTADO, E.DESCRIPCION DESCRIPCIONESTADO"
					 , descEstadoIca(), descEstadoConsell(),
					 " F_SIGA_GETRECURSO(P.NOMBRE, 1) NOMBREPERIODO, I.ABREVIATURA ABREVIATURAINSTITUCION ",
					 " INTE.IDPERIODO, ROW_NUMBER() OVER(ORDER BY INTE.IDINTERCAMBIO) RN"
					 )
			 .FROM("FCS_JE_INTERCAMBIOS   INTE", "GEN_PERIODO           P","CEN_INSTITUCION       I", "FCS_JE_MAESTROESTADOS E")
			 .WHERE("E.IDESTADO = INTE.IDESTADO" , " INTE.IDPERIODO = P.IDPERIODO" , "INTE.IDINSTITUCION = I.IDINSTITUCION")
			 .WHERE(cabeceraIntercambioOptWhere(params));
	 
	 	if(params.get("idIntercambio")==null) {
	 		sql.ORDER_BY("ANIO DESC", "IDPERIODO DESC", " IDINTERCAMBIO DESC");
	 	}
		return sql.toString();
	}
	
	private String [] cabeceraIntercambioOptWhere(Map<String, Object> params) {
		List<String> condiciones = new ArrayList<String>();
		condiciones.add("1=1");
		if(params.get("descripcion")!=null) {
			condiciones.add("REGEXP_LIKE(UPPER(INTE.DESCRIPCION),#{params.descripcion})");
		}
		if(params.get("anio")!=null) {
			condiciones.add(" INTE.ANIO =  #{params.anio}");
		}
		if(params.get("idPeriodo")!=null) {
			condiciones.add(" INTE.IDPERIODO = #{params.idPeriodo} ");
		}
		if(params.get("idColegio")!=null) {
			condiciones.add(" INTE.IDINSTITUCION = #{params.idColegio}  ");
		}
		if (params.get("idIntercambio") != null) {
			condiciones.add(" INTE.IDINTERCAMBIO = #{params.idIntercambio}  ");
		} else if (params.get("idInstitucion")!=null&&params.get("idInstitucion").equals(3001)) {
			condiciones.add("INTE.IDESTADO >= 30 ");
			if (params.get("idEstado") != null) {
				condiciones.add("INTE.IDESTADO = #{params.idEstado}");
			}
		} else {
			condiciones.add(" INTE.IDINSTITUCION = #{params.idInstitucion}  ");
			if (params.get("idEstado") != null) {
				condiciones.add("INTE.IDESTADO = #{params.idEstado}");
			}
		}

		return condiciones.toArray(new String[0]);
		
	}

	private String descEstadoIca() {
		SQL s = new SQL().SELECT("E2.DESCRIPCION")
				.FROM("FCS_JE_MAESTROESTADOS E2")
				.WHERE("E2.IDESTADO = DECODE(E.IDESTADO, " + DECODE_ESTADO_ICA+ ")" );
		return "("+s.toString()+") DESCRIPCIONESTADOICA";
	}
	
	private String descEstadoConsell() {
		SQL s=new SQL().SELECT("E3.DESCRIPCION")
				.FROM("FCS_JE_MAESTROESTADOS E3")
				.WHERE("E3.IDESTADO = DECODE(E.IDESTADO," + DECODE_ESTADO_CONSELL +  ")");
		return "("+s.toString()+") DESCRIPCIONESTADOCONSELL";	
	}
	
	
	
//	SELECT * FROM (
//		 	SELECT  INTE.IDINTERCAMBIO, INTE.IDINSTITUCION, INTE.DESCRIPCION, INTE.ANIO, INTE.IDESTADO, E.DESCRIPCION DESCRIPCIONESTADO,
//	             
//	                (SELECT E2.DESCRIPCION  FROM FCS_JE_MAESTROESTADOS E2  WHERE E2.IDESTADO = DECODE(E.IDESTADO, decodeEstadoIca) ) DESCRIPCIONESTADOICA,
//	                                            
//	    (SELECT E3.DESCRIPCION  FROM FCS_JE_MAESTROESTADOS E3  WHERE E3.IDESTADO = DECODE(E.IDESTADO,<include refid="decodeEstadoConsell"))  DESCRIPCIONESTADOCONSELL,
//	             
//	             
//	               F_SIGA_GETRECURSO(P.NOMBRE, 1) NOMBREPERIODO, I.ABREVIATURA ABREVIATURAINSTITUCION, INTE.IDPERIODO, ROW_NUMBER() OVER(ORDER BY INTE.IDINTERCAMBIO) RN
//	          FROM FCS_JE_INTERCAMBIOS   INTE,
//	               GEN_PERIODO           P,
//	               CEN_INSTITUCION       I,
//	               FCS_JE_MAESTROESTADOS E
//	         WHERE E.IDESTADO = INTE.IDESTADO
//	           AND INTE.IDPERIODO = P.IDPERIODO
//	   		AND  INTE.IDINSTITUCION = I.IDINSTITUCION
//	    	 
//	           <if test="descripcion != null">
//	            AND REGEXP_LIKE(UPPER(INTE.DESCRIPCION),#{descripcion})
//	       	</if>
//	       	<if test="anio != null">
//	            AND INTE.ANIO =  #{anio}
//	       	</if>
//	       	
//	       	<if test="idPeriodo != null">
//	       		AND INTE.IDPERIODO = #{idPeriodo}       		    
//	       	</if>
//	       	<if test="idColegio != null">
//	       		AND INTE.IDINSTITUCION = #{idColegio}       		    
//	       	</if>
//	       	
//	       	<choose >
//	       	
//	       	 <when test="idIntercambio != null">
//	       		AND INTE.IDINTERCAMBIO = #{idIntercambio}  
//	       		)     		    
//	       	</when>
//	        <when test="idInstitucion==3001" >
//	         			
//	            	   	AND INTE.IDESTADO &gt;= 30
//	 					
//	 			 <if test="idEstado != null">
//	 					 
//	 					 
//	       					AND INTE.IDESTADO = #{idEstado}       		    
//	       				</if>
//	 					ORDER BY  ANIO DESC,  IDPERIODO DESC , IDINTERCAMBIO DESC)
//	         </when>
//	         <otherwise>
//	                	AND INTE.IDINSTITUCION = #{idInstitucion}
//	                	
//	               	<if test="idEstado != null">
//	     					AND INTE.IDESTADO = #{idEstado}       		        
//	     			</if>
//	      			ORDER BY  ANIO DESC,  IDPERIODO DESC,  IDINTERCAMBIO DESC)
//	          </otherwise>
//	      </choose>
//	       	
//	       	
//	      

//	@Select ("<script>"
//			+ "SELECT "
//			+ "       I.TIPO_INTERCAMBIO || '_' || C.COD_ORIGEN_INTERCAMBIO || '_' ||"
//			+ "       C.COD_DESTINO_INTERCAMBIO || '_' || C.IDENTIFICADOR_INTERCAMBIO || '_' ||"
//			+ "       TO_CHAR(C.FECHA_INTERCAMBIO, 'YYYYMMDD') || '_' ||"
//			+ "        <choose >"
//			+ "                <when test=\"params.detalles != null\" >"
//			+ "                 	#{params.detalles}"
//			+ "                </when>"
//			+ "                <otherwise>"
//			+ "                I.NUMERO_DETALLES"
//			+ "                </otherwise>"
//			+ "        </choose>"
//			+ "        ||'.xml'"
//			+ "  FROM JE_CABECERA C, JE_INTERCAMBIO I, FCS_JE_JUST_ESTADO JU"
//			+ " WHERE "
//			+ "	<choose>"
//			+ "          	<when test=\"params.idEstado == 30\" >"
//			+ "				I.IDJECABECERA_CICAC = C.IDJECABECERA"
//			+ "				AND JU.IDESTADO = 30"
//			+ "            </when>"
//			+ "            <when test=\"params.idEstado == 70\" >"
//			+ "				I.IDJECABECERA_GEN = C.IDJECABECERA"
//			+ "				AND JU.IDESTADO = 70"
//			+ "            </when>"
//			+ "         	<otherwise>"
//			+ "           		nvl(I.IDJECABECERA_CICAC,I.IDJECABECERA_GEN) = C.IDJECABECERA"
//			+ "              </otherwise>"
//			+ "        </choose>"
//			+ "   AND JU.IDJEINTERCAMBIO = I.IDJEINTERCAMBIO"
//			+ "   AND JU.IDJUSTIFICACION= #{params.idJustificacion}"
//			+ "</script>")

}
