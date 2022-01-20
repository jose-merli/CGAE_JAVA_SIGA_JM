package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.fac.ListaServiciosItem;
import org.itcgae.siga.db.mappers.PysLineaanticipoExtendsSqlProvider;
import org.itcgae.siga.db.mappers.PysPreciosserviciosSqlProvider;

public class PysPreciosserviciosSqlExtendsProvider extends PysPreciosserviciosSqlProvider {

    private Logger LOGGER = Logger.getLogger(PysPreciosserviciosSqlExtendsProvider.class);
    
	public String selectMaxIdPrecioServicio(Short idInstitucion, short idTipoServicio, Long idServicio, Long idServicioInstitucion, Short idPeriocidad) {

		SQL sql = new SQL();

		sql.SELECT("NVL(max(IDPRECIOSSERVICIOS) +1, 1) AS IDPRECIOSERVICIO");
		sql.FROM("PYS_PRECIOSSERVICIOS");
		sql.WHERE("idInstitucion =" + idInstitucion);
		sql.WHERE("idtiposervicios = " + idTipoServicio);
		sql.WHERE("idServicio = " + idServicio);
		sql.WHERE("idserviciosinstitucion = " + idServicioInstitucion);
		sql.WHERE("IDPERIODICIDAD = " + idPeriocidad );
		  
		return sql.toString();
	}
	
	public String selectPricesCourse(Short idInstitucion, Long idServicio, String idLenguaje, String codigoCurso) {

		SQL sql = new SQL();

		sql.SELECT("pys.DESCRIPCION");
		sql.SELECT("pys.VALOR");
		sql.SELECT("DECODE(pys.PORDEFECTO,1,'SI','NO') AS PORDEFECTO");
		sql.SELECT("cat.DESCRIPCION as PERIOCIDAD");
		sql.FROM("PYS_SERVICIOS SERVICIO");
		sql.INNER_JOIN("PYS_SERVICIOSINSTITUCION SERVICIOINST ON SERVICIOINST.IDSERVICIO = SERVICIO.IDSERVICIO "
				+ "AND SERVICIO.IDTIPOSERVICIOS = SERVICIOINST.IDTIPOSERVICIOS  AND SERVICIO.IDINSTITUCION = SERVICIOINST.IDINSTITUCION ");
		sql.INNER_JOIN("PYS_PRECIOSSERVICIOS pys ON PYS.IDSERVICIO = SERVICIO.IDSERVICIO AND SERVICIO.IDTIPOSERVICIOS = PYS.IDTIPOSERVICIOS"
												+ "	 AND PYS.IDSERVICIOSINSTITUCION = SERVICIOINST.IDSERVICIOSINSTITUCION AND PYS.IDINSTITUCION = SERVICIOINST.IDINSTITUCION");
		sql.INNER_JOIN("PYS_PERIODICIDAD per on per.IDPERIODICIDAD = pys.IDPERIODICIDAD");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS cat ON per.DESCRIPCION = cat.IDRECURSO and idLenguaje = '" + idLenguaje + "'");
		sql.WHERE("pys.idInstitucion =" + idInstitucion);
		sql.WHERE("SERVICIOINST.descripcion like '%" + codigoCurso + "%'");
		sql.WHERE("pys.idServicio = " + idServicio);
		
		return sql.toString();
	}
	
	public String detalleTarjetaPrecios(int idTipoServicio, int idServicio, int idServiciosInstitucion, Short idInstitucion, String idioma) {

		SQL sql = new SQL();

		sql.SELECT(" idpreciosservicios"); 
		sql.SELECT(" idserviciosinstitucion");
		sql.SELECT(" idtiposervicios");
		sql.SELECT(" idservicio");
		sql.SELECT(" preciosserv.valor precio");
		sql.SELECT(" preciosserv.idperiodicidad periodicidad");
		sql.SELECT(" f_siga_getrecurso(preciosserv.descripcion," + idioma + ") descripcionprecio");
		sql.SELECT(" preciosserv.idconsulta condicion");
		sql.SELECT(" f_siga_getrecurso(perio.descripcion," + idioma +") descripcionperiodicidad");
		sql.SELECT(" CASE WHEN f_siga_getrecurso(consul.descripcion," + idioma +") IS NOT NULL THEN f_siga_getrecurso(consul.descripcion," + idioma +") ELSE 'No tiene condición' END descripcionconsulta");
		sql.SELECT(" pordefecto");
		sql.SELECT(" perio.periodosmes as periodicidadValor");
		
		sql.FROM(" pys_preciosservicios preciosserv");
		sql.FROM(" pys_periodicidad perio");
		sql.FROM(" con_consulta consul");
		
		sql.WHERE(" preciosserv.idinstitucion = " + idInstitucion);
		sql.WHERE(" preciosserv.idtiposervicios = " + idTipoServicio);
		sql.WHERE(" preciosserv.idserviciosinstitucion = " + idServiciosInstitucion);
		sql.WHERE(" preciosserv.idservicio = " + idServicio);
		sql.WHERE(" perio.idperiodicidad (+) = preciosserv.idperiodicidad");
		sql.WHERE(" consul.idinstitucion (+) = preciosserv.idinstitucion");
		sql.WHERE(" consul.idconsulta (+) = preciosserv.idconsulta");
		
		sql.ORDER_BY(" preciosserv.descripcion");
		
		return sql.toString();
	}
	
	public String comboPeriodicidad(String idioma) {
		SQL sql = new SQL();
		
		sql.SELECT("IDPERIODICIDAD ID");
		sql.SELECT("f_siga_getrecurso (DESCRIPCION,'" + idioma + "') DESCRIPCION");
		
		sql.FROM("PYS_PERIODICIDAD");
		
		return sql.toString();
	}
	
	//Realiza un borrado fisico de los precios de un servicio
	public String borradoFisicoPreciosByServicio(ListaServiciosItem servicio,Short idInstitucion) {
		SQL sql = new SQL();
			
		sql.DELETE_FROM(" PYS_PRECIOSSERVICIOS");
				
		sql.WHERE(" IDTIPOSERVICIOS = " + servicio.getIdtiposervicios());
		sql.WHERE(" IDSERVICIO = " + servicio.getIdservicio());
		sql.WHERE(" IDSERVICIOSINSTITUCION = " +  servicio.getIdserviciosinstitucion());
		sql.WHERE(" IDINSTITUCION = " + idInstitucion);
						
		return sql.toString();
	}

	public String comboPreciosServPers(String idioma, Short idInstitucion, String idPersona, String idServicio, String idTipoServicios, String idServiciosInstitucion) {
		
		SQL sql = new SQL();

		sql.SELECT(" idpreciosservicios"); 
		sql.SELECT(" idserviciosinstitucion");
		sql.SELECT(" idtiposervicios");
		sql.SELECT(" idservicio");
		sql.SELECT(" preciosserv.valor precio");
		sql.SELECT(" preciosserv.idperiodicidad periodicidad");
		sql.SELECT(" f_siga_getrecurso(preciosserv.descripcion," + idioma + ") descripcionprecio");
		sql.SELECT(" preciosserv.idconsulta condicion");
		sql.SELECT(" f_siga_getrecurso(perio.descripcion," + idioma +") descripcionperiodicidad");
		sql.SELECT(" CASE WHEN f_siga_getrecurso(consul.descripcion," + idioma +") IS NOT NULL THEN f_siga_getrecurso(consul.descripcion," + idioma +") ELSE 'No tiene condición' END descripcionconsulta");
		sql.SELECT(" pordefecto");
		sql.SELECT(" perio.periodosmes as periodicidadValor");
		sql.SELECT("REPLACE(REPLACE(preciosserv.CRITERIOS, '@FECHA@', trunc(sysdate)), '@IDPERSONA@', "+idPersona+") as valido");
		
		
		sql.FROM(" pys_preciosservicios preciosserv");
		sql.FROM(" pys_periodicidad perio");
		sql.FROM(" con_consulta consul");
		
		sql.WHERE(" preciosserv.idinstitucion = " + idInstitucion);
		sql.WHERE(" preciosserv.idtiposervicios = " + idTipoServicios);
		sql.WHERE(" preciosserv.idserviciosinstitucion = " + idServiciosInstitucion);
		sql.WHERE(" preciosserv.idservicio = " + idServicio);
		sql.WHERE(" perio.idperiodicidad (+) = preciosserv.idperiodicidad");
		sql.WHERE(" consul.idinstitucion (+) = preciosserv.idinstitucion");
		sql.WHERE(" consul.idconsulta (+) = preciosserv.idconsulta");
		
		sql.ORDER_BY(" preciosserv.descripcion");

        LOGGER.info("CONSULTA PARA EL COMBO DE PRECIOS DE UNA SUSCRIPCION: \r\n" + sql.toString());
						
		return sql.toString();
	}
	
	public String checkCriterioPrecio(String criterio) {
		
		//Se devuelve el criterio del precio para comprobar si la persona lo cumple o no
		return criterio;
		
		
	}
}
