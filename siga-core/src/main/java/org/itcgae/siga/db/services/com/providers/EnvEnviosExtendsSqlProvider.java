package org.itcgae.siga.db.services.com.providers;

								  
					  

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.com.EnviosMasivosSearch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EnvEnviosExtendsSqlProvider {

    public String selectEnvios(Short idInstitucion, String idLenguaje, EnviosMasivosSearch filtros) {

        SQL sql = new SQL();
        // Formateo de fecha para sentencia sql
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        sql.SELECT("ENVIO.IDINSTITUCION");
        sql.SELECT("ENVIO.IDENVIO");
        sql.SELECT("ENVIO.DESCRIPCION");
        sql.SELECT("ENVIO.FECHA AS FECHACREACION");
        sql.SELECT("ENVIO.IDPLANTILLAENVIOS");
        sql.SELECT("ENVIO.IDESTADO");
        sql.SELECT("ENVIO.IDTIPOENVIOS");
        sql.SELECT("(SELECT NOMBRE FROM ENV_PLANTILLASENVIOS WHERE IDINSTITUCION = '" + idInstitucion
                + "' AND IDPLANTILLAENVIOS = ENVIO.IDPLANTILLAENVIOS AND IDTIPOENVIOS = ENVIO.IDTIPOENVIOS) AS NOMBREPLANTILLA");
        sql.SELECT("ENVIO.IDPLANTILLA");
        sql.SELECT("ENVIO.FECHAPROGRAMADA");
        sql.SELECT("ENVIO.FECHABAJA");
        sql.SELECT("PLANTILLA.ASUNTO");
        sql.SELECT("PLANTILLA.CUERPO");
        sql.SELECT(
                "(SELECT CAT.DESCRIPCION FROM ENV_TIPOENVIOS LEFT JOIN GEN_RECURSOS_CATALOGOS CAT ON CAT.IDRECURSO = ENV_TIPOENVIOS.NOMBRE WHERE ENV_TIPOENVIOS.IDTIPOENVIOS = ENVIO.IDTIPOENVIOS AND CAT.IDLENGUAJE = '"
                        + idLenguaje + "') AS TIPOENVIO");
        sql.SELECT(
                "(SELECT CAT.DESCRIPCION FROM ENV_ESTADOENVIO ESTADO LEFT JOIN GEN_RECURSOS_CATALOGOS CAT ON CAT.IDRECURSO = ESTADO.NOMBRE WHERE ESTADO.IDESTADO = ENVIO.IDESTADO AND CAT.IDLENGUAJE = '"
                        + idLenguaje + "') AS ESTADOENVIO");
        sql.SELECT(
                "(SELECT count(*) FROM env_dest_consulta_envio dest where dest.idenvio=ENVIO.IDENVIO AND dest.IDINSTITUCION = ENVIO.IDINSTITUCION) + "
                        + "(SELECT count(*) FROM env_destinatarios dest where dest.idenvio=ENVIO.IDENVIO AND dest.IDINSTITUCION = ENVIO.IDINSTITUCION)  + "
                        + "(SELECT count(*) FROM env_enviosgrupocliente dest where dest.idenvio=ENVIO.IDENVIO) AS NUMDEST");

        sql.FROM("ENV_ENVIOS ENVIO");
        sql.JOIN("ENV_PLANTILLASENVIOS PLANTILLA ON PLANTILLA.IDINSTITUCION = '" + idInstitucion
                + "' AND PLANTILLA.IDPLANTILLAENVIOS = ENVIO.IDPLANTILLAENVIOS AND PLANTILLA.IDTIPOENVIOS = ENVIO.IDTIPOENVIOS");

        sql.WHERE("ENVIO.IDINSTITUCION = '" + idInstitucion + "'");
        if (filtros.getIdEnvio() != null) {
            sql.WHERE("ENVIO.IDENVIO = '" + filtros.getIdEnvio() + "'");
        }
        // con este campo controlamos que sea de envios Masivos
        sql.WHERE("ENVIO.ENVIO = 'M'");

        if (filtros.getDescripcion() != null && !filtros.getDescripcion().trim().equals("")) {
            sql.WHERE(filtroTextoBusquedas("ENVIO.DESCRIPCION", filtros.getDescripcion()));
        }
        if (filtros.getIdEstado() != null && !filtros.getIdEstado().trim().equals("")) {
            sql.WHERE("ENVIO.IDESTADO = '" + filtros.getIdEstado() + "'");
            // if(filtros.getidEstado().equals("6")){
            // sql.WHERE("ENVIO.FECHABAJA IS NOT NULL");
            // }else{
            // sql.WHERE("ENVIO.FECHABAJA IS NULL");
            // }
        } // else{
        // sql.WHERE("ENVIO.FECHABAJA IS NULL");
        // }
        if (filtros.getFechaCreacion() != null) {
            String fechaCreacion = dateFormat.format(filtros.getFechaCreacion());
            // String fechaCreacion2 = dateFormat.format(filtros.getFechaCreacion());
            fechaCreacion += " 00:00:00";
            // fechaCreacion2 += " 23:59:59";
            sql.WHERE("(ENVIO.FECHA >= TO_DATE('" + fechaCreacion + "', 'DD/MM/YYYY HH24:MI:SS'))");
        }
        if (filtros.getFechaProgramacion() != null) {
            String fechaProgramacion = dateFormat.format(filtros.getFechaProgramacion());
            String fechaProgramacion2 = dateFormat.format(filtros.getFechaProgramacion());
            fechaProgramacion += " 00:00:00";
            fechaProgramacion2 += " 23:59:59";
            sql.WHERE("(ENVIO.FECHAPROGRAMADA >= TO_DATE('" + fechaProgramacion
                    + "', 'DD/MM/YYYY HH24:MI:SS') AND ENVIO.FECHAPROGRAMADA <= TO_DATE('" + fechaProgramacion2
                    + "', 'DD/MM/YYYY HH24:MI:SS'))");
        }
        if (filtros.getIdTipoEnvios() != null && !filtros.getIdTipoEnvios().trim().equals("")) {
            sql.WHERE("ENVIO.IDTIPOENVIOS = '" + filtros.getIdTipoEnvios() + "'");
        }
        sql.ORDER_BY("ENVIO.FECHA DESC");

        return sql.toString();
    }

    public String busquedaSelectEnvios(Short idInstitucion, String idLenguaje, EnviosMasivosSearch filtros) {

        SQL sql = new SQL();
        // Formateo de fecha para sentencia sql
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        sql.SELECT("ENVIO.IDINSTITUCION");
        sql.SELECT("ENVIO.IDENVIO");
        sql.SELECT("ENVIO.DESCRIPCION");
        sql.SELECT("ENVIO.IDESTADO");
        sql.SELECT("ENVIO.FECHA AS FECHACREACION");
        sql.SELECT("ENVIO.FECHAPROGRAMADA");
        sql.SELECT("ENVIO.IDPLANTILLAENVIOS");
        sql.SELECT("ENVIO.IDTIPOENVIOS");
        sql.SELECT("ENVIO.FECHABAJA");

        sql.SELECT(
                "(SELECT CAT.DESCRIPCION FROM ENV_TIPOENVIOS LEFT JOIN GEN_RECURSOS_CATALOGOS CAT ON CAT.IDRECURSO = ENV_TIPOENVIOS.NOMBRE WHERE ENV_TIPOENVIOS.IDTIPOENVIOS = ENVIO.IDTIPOENVIOS AND CAT.IDLENGUAJE = '"
                        + idLenguaje + "') AS TIPOENVIO");
        sql.SELECT(
                "(SELECT CAT.DESCRIPCION FROM ENV_ESTADOENVIO ESTADO LEFT JOIN GEN_RECURSOS_CATALOGOS CAT ON CAT.IDRECURSO = ESTADO.NOMBRE WHERE ESTADO.IDESTADO = ENVIO.IDESTADO AND CAT.IDLENGUAJE = '"
                        + idLenguaje + "') AS ESTADOENVIO");

//		sql.SELECT("(SELECT count(*) FROM env_dest_consulta_envio dest where dest.idenvio=ENVIO.IDENVIO AND dest.IDINSTITUCION = ENVIO.IDINSTITUCION) + " +
//				"(SELECT count(*) FROM env_destinatarios dest where dest.idenvio=ENVIO.IDENVIO AND dest.IDINSTITUCION = ENVIO.IDINSTITUCION)  + " +
//				"(SELECT count(*) FROM env_enviosgrupocliente dest where dest.idenvio=ENVIO.IDENVIO) AS NUMDEST");

        sql.FROM("ENV_ENVIOS ENVIO");
        sql.WHERE("ENVIO.IDINSTITUCION = '" + idInstitucion + "'");
        // con este campo controlamos que sea de envios Masivos
        sql.WHERE("ENVIO.ENVIO = 'M'");
        if (filtros.getDescripcion() != null && !filtros.getDescripcion().trim().equals("")) {
            sql.WHERE(filtroTextoBusquedas("ENVIO.DESCRIPCION", filtros.getDescripcion()));
        }
        if (filtros.getIdEstado() != null && !filtros.getIdEstado().trim().equals("")) {
            sql.WHERE("ENVIO.IDESTADO = '" + filtros.getIdEstado() + "'");
        }
        if (filtros.getFechaCreacion() != null) {
            String fechaCreacion = dateFormat.format(filtros.getFechaCreacion());
            fechaCreacion += " 00:00:00";
            sql.WHERE("(ENVIO.FECHA >= TO_DATE('" + fechaCreacion + "', 'DD/MM/YYYY HH24:MI:SS'))");
        }
        if (filtros.getFechaProgramacion() != null) {
            String fechaProgramacion = dateFormat.format(filtros.getFechaProgramacion());
            String fechaProgramacion2 = dateFormat.format(filtros.getFechaProgramacion());
            fechaProgramacion += " 00:00:00";
            fechaProgramacion2 += " 23:59:59";
            sql.WHERE("(ENVIO.FECHAPROGRAMADA >= TO_DATE('" + fechaProgramacion
                    + "', 'DD/MM/YYYY HH24:MI:SS') AND ENVIO.FECHAPROGRAMADA <= TO_DATE('" + fechaProgramacion2
                    + "', 'DD/MM/YYYY HH24:MI:SS'))");
        }
        if (filtros.getIdTipoEnvios() != null && !filtros.getIdTipoEnvios().trim().equals("")) {
            sql.WHERE("ENVIO.IDTIPOENVIOS = '" + filtros.getIdTipoEnvios() + "'");
        }
        sql.ORDER_BY("ENVIO.FECHA DESC");

        return sql.toString();
    }

    public String selectMaxIDEnvio(Short idInstitucion) {

        SQL sql = new SQL();
        sql.SELECT("TO_NUMBER(TO_CHAR(SYSDATE, 'YYYY') || SEQ_ENV_ENVIOS.CURRVAL) AS IDMAX");
        sql.FROM("DUAL");
        return sql.toString();
    }
    
    public String selectEnviosComunicacion(Short idInstitucion, String idLenguaje, EnviosMasivosSearch filtros) {

        SQL sql = new SQL();
        // Formateo de fecha para sentencia sql
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        sql.SELECT("ENVIO.IDINSTITUCION");
        sql.SELECT("ENVIO.IDENVIO");
        sql.SELECT("ENVIO.DESCRIPCION");
        sql.SELECT("ENVIO.FECHA AS FECHACREACION");
        sql.SELECT("ENVIO.IDPLANTILLAENVIOS");
        sql.SELECT("ENVIO.IDESTADO");
        sql.SELECT("ENVIO.IDTIPOENVIOS");
        sql.SELECT("(SELECT NOMBRE FROM ENV_PLANTILLASENVIOS WHERE IDINSTITUCION = '" + idInstitucion
                + "' AND IDPLANTILLAENVIOS = ENVIO.IDPLANTILLAENVIOS AND IDTIPOENVIOS = ENVIO.IDTIPOENVIOS) AS NOMBREPLANTILLA");
        sql.SELECT("ENVIO.IDPLANTILLA");
        sql.SELECT("ENVIO.FECHAPROGRAMADA");
        sql.SELECT("ENVIO.FECHABAJA");
        sql.SELECT("ENVIO.IDMODELOCOMUNICACION");
        sql.SELECT("ENVIO.CSV");
        sql.SELECT("NVL(CAMPOSENVIOSASUNTO.VALOR,PLANTILLA.ASUNTO) AS ASUNTO");
        sql.SELECT("NVL(CAMPOSENVIOSCUERPO.VALOR,PLANTILLA.CUERPO) AS CUERPO");
        sql.SELECT(
                "(SELECT CAT.DESCRIPCION FROM ENV_TIPOENVIOS LEFT JOIN GEN_RECURSOS_CATALOGOS CAT ON CAT.IDRECURSO = ENV_TIPOENVIOS.NOMBRE WHERE ENV_TIPOENVIOS.IDTIPOENVIOS = ENVIO.IDTIPOENVIOS AND CAT.IDLENGUAJE = '"
                        + idLenguaje + "') AS TIPOENVIO");
        sql.SELECT(
                "(SELECT CAT.DESCRIPCION FROM ENV_ESTADOENVIO ESTADO LEFT JOIN GEN_RECURSOS_CATALOGOS CAT ON CAT.IDRECURSO = ESTADO.NOMBRE WHERE ESTADO.IDESTADO = ENVIO.IDESTADO AND CAT.IDLENGUAJE = '"
                        + idLenguaje + "') AS ESTADOENVIO");
        sql.SELECT("ENVIO.IDMODELOCOMUNICACION, CLASE.IDCLASECOMUNICACION");
        sql.SELECT(
                "CLASE.NOMBRE AS NOMBRECLASE, (SELECT NOMBRE FROM MOD_MODELOCOMUNICACION WHERE IDMODELOCOMUNICACION = ENVIO.IDMODELOCOMUNICACION) AS NOMBREMODELO");
        sql.SELECT("(DEST.NOMBRE || ' ' ||DEST.APELLIDOS1 || ' ' || DEST.APELLIDOS2) AS DESTINATARIO");

        sql.FROM("ENV_ENVIOS ENVIO");
        // JOIN ENV_PLANTILLASENVIOS PLANTILLA ON PLANTILLA.IDINSTITUCION = '2005' AND
        // PLANTILLA.IDPLANTILLAENVIOS = ENVIO.IDPLANTILLAENVIOS AND
        // PLANTILLA.IDTIPOENVIOS = ENVIO.IDTIPOENVIOS

        sql.LEFT_OUTER_JOIN(
                "ENV_CAMPOSENVIOS CAMPOSENVIOSASUNTO ON (ENVIO.IDENVIO = CAMPOSENVIOSASUNTO.IDENVIO AND CAMPOSENVIOSASUNTO.IDINSTITUCION = ENVIO.IDINSTITUCION AND CAMPOSENVIOSASUNTO.IDCAMPO =1)");
        sql.LEFT_OUTER_JOIN(
                "ENV_CAMPOSENVIOS CAMPOSENVIOSCUERPO ON (ENVIO.IDENVIO = CAMPOSENVIOSCUERPO.IDENVIO AND CAMPOSENVIOSCUERPO.IDINSTITUCION = ENVIO.IDINSTITUCION AND CAMPOSENVIOSCUERPO.IDCAMPO =2)");
        sql.LEFT_OUTER_JOIN("ENV_PLANTILLASENVIOS PLANTILLA ON PLANTILLA.IDINSTITUCION = '" + idInstitucion
                + "' AND PLANTILLA.IDPLANTILLAENVIOS = ENVIO.IDPLANTILLAENVIOS AND PLANTILLA.IDTIPOENVIOS = ENVIO.IDTIPOENVIOS");
																										
        sql.LEFT_OUTER_JOIN("MOD_MODELOCOMUNICACION MODELO ON MODELO.IDMODELOCOMUNICACION = ENVIO.IDMODELOCOMUNICACION");
        sql.LEFT_OUTER_JOIN("MOD_CLASECOMUNICACIONES CLASE ON CLASE.IDCLASECOMUNICACION = MODELO.IDCLASECOMUNICACION");
   
		
																													
																												  
   
  
        sql.LEFT_OUTER_JOIN("ENV_DESTINATARIOS DEST ON DEST.IDENVIO = ENVIO.IDENVIO AND DEST.IDINSTITUCION = ENVIO.IDINSTITUCION");
        sql.LEFT_OUTER_JOIN(
                "CEN_COLEGIADO COLEGIADO ON COLEGIADO.IDPERSONA = DEST.IDPERSONA AND COLEGIADO.IDINSTITUCION = ENVIO.IDINSTITUCION");

        sql.WHERE("ENVIO.IDINSTITUCION = '" + idInstitucion + "'");
        // controlamos con este campo si es 'A' pertenece a comunicaciones
        sql.WHERE("ENVIO.ENVIO = 'A'");

        if (filtros.getDescripcion() != null && !filtros.getDescripcion().trim().equals("")) {
            sql.WHERE(filtroTextoBusquedas("ENVIO.DESCRIPCION", filtros.getDescripcion()));
        }
        if (filtros.getIdEstado() != null && !filtros.getIdEstado().trim().equals("")) {
            sql.WHERE("ENVIO.IDESTADO = '" + filtros.getIdEstado() + "'");
        }
        if (filtros.getIdClaseComunicacion() != null && !filtros.getIdClaseComunicacion().trim().equals("")) {
            sql.WHERE("CLASE.IDCLASECOMUNICACION = '" + filtros.getIdClaseComunicacion() + "'");
        }
        if (filtros.getFechaCreacion() != null) {
            String fechaCreacion = dateFormat.format(filtros.getFechaCreacion());
            // String fechaCreacion2 = dateFormat.format(filtros.getFechaCreacion());
            fechaCreacion += " 00:00:00";
            // fechaCreacion2 += " 23:59:59";
            sql.WHERE("(ENVIO.FECHA >= TO_DATE('" + fechaCreacion + "', 'DD/MM/YYYY HH24:MI:SS'))");
        }
        if (filtros.getFechaProgramacion() != null) {
            String fechaProgramacion = dateFormat.format(filtros.getFechaProgramacion());
            String fechaProgramacion2 = dateFormat.format(filtros.getFechaProgramacion());
            fechaProgramacion += " 00:00:00";
            fechaProgramacion2 += " 23:59:59";
            sql.WHERE("(ENVIO.FECHAPROGRAMADA >= TO_DATE('" + fechaProgramacion
                    + "', 'DD/MM/YYYY HH24:MI:SS') AND ENVIO.FECHAPROGRAMADA <= TO_DATE('" + fechaProgramacion2
                    + "', 'DD/MM/YYYY HH24:MI:SS'))");
        }
        if (filtros.getIdTipoEnvios() != null && !filtros.getIdTipoEnvios().trim().equals("")) {
            sql.WHERE("ENVIO.IDTIPOENVIOS = '" + filtros.getIdTipoEnvios() + "'");
        }
        if (filtros.getNombre() != null && !filtros.getNombre().trim().equals("")) {
            sql.WHERE(filtroTextoBusquedas("DEST.NOMBRE", filtros.getNombre()));
        }

        if (filtros.getApellidos() != null && !filtros.getApellidos().trim().equals("")) {
            String[] apellidos = filtros.getApellidos().split(" ");
            String whereApellidos = "";
            for (int i = 0; i < apellidos.length; i++) {
                String apellidoBuscar = apellidos[i];
                if (i != 0) {
                    whereApellidos = whereApellidos + " OR ";
                }
                whereApellidos = whereApellidos
                        + "TRANSLATE(LOWER(DEST.APELLIDOS1),'ÀÁÂÃÄÅàáâãäåÒÓÔÕÕÖØòóôõöøÈÉÊËèéêëðÇçÐÌÍÎÏìíîïÙÚÛÜùúûüÑñŠšŸÿýŽž','AAAAAAaaaaaaOOOOOOOooooooEEEEeeeeeCcDIIIIiiiiUUUUuuuuNnSsYyyZz') LIKE "
                        + "TRANSLATE(LOWER('%" + apellidoBuscar
                        + "%'),'ÀÁÂÃÄÅàáâãäåÒÓÔÕÕÖØòóôõöøÈÉÊËèéêëðÇçÐÌÍÎÏìíîïÙÚÛÜùúûüÑñŠšŸÿýŽž','AAAAAAaaaaaaOOOOOOOooooooEEEEeeeeeCcDIIIIiiiiUUUUuuuuNnSsYyyZz') OR "
                        + "TRANSLATE(LOWER(DEST.APELLIDOS2),'ÀÁÂÃÄÅàáâãäåÒÓÔÕÕÖØòóôõöøÈÉÊËèéêëðÇçÐÌÍÎÏìíîïÙÚÛÜùúûüÑñŠšŸÿýŽž','AAAAAAaaaaaaOOOOOOOooooooEEEEeeeeeCcDIIIIiiiiUUUUuuuuNnSsYyyZz') LIKE "
                        + "TRANSLATE(LOWER('%" + apellidoBuscar
                        + "%'),'ÀÁÂÃÄÅàáâãäåÒÓÔÕÕÖØòóôõöøÈÉÊËèéêëðÇçÐÌÍÎÏìíîïÙÚÛÜùúûüÑñŠšŸÿýŽž','AAAAAAaaaaaaOOOOOOOooooooEEEEeeeeeCcDIIIIiiiiUUUUuuuuNnSsYyyZz')";
            }

            sql.WHERE("(" + whereApellidos + ")");
        }

        if (filtros.getNif() != null && !filtros.getNif().trim().equals("")) {
            sql.WHERE(filtroTextoBusquedas("DEST.NIFCIF", filtros.getNif()));
        }

        if (filtros.getNumColegiado() != null && !filtros.getNumColegiado().trim().equals("")) {
            sql.WHERE("COLEGIADO.NCOLEGIADO LIKE '%" + filtros.getNumColegiado() + "%'");
        }

        if (filtros.getIdInstitucion() != null && !filtros.getIdInstitucion().trim().equals("")) {
            sql.WHERE("DEST.IDINSTITUCION = '" + filtros.getIdInstitucion() + "'");
        }
        sql.ORDER_BY("ENVIO.FECHA DESC");

        return sql.toString();
    }


    public String obtenerEnviosMalCreados() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechaHoy = dateFormat.format(new Date());
        SQL sql = new SQL();

        sql.SELECT("ENVIO.*");					
		
        sql.FROM("ENV_ENVIOS ENVIO");

		
        sql.WHERE(
                "ENVIO.IDESTADO = 4 AND (ENVIO.ENVIO = 'A' OR ENVIO.ENVIO = 'M') and nvl(ENVIO.fechaprogramada, SYSDATE -2) <= SYSDATE - 1");
        sql.ORDER_BY("ENVIO.FECHA DESC");
        return sql.toString();
    }

    public String selectEnviosProgramados() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechaHoy = dateFormat.format(new Date());
        SQL sql = new SQL();

        sql.SELECT("ENVIO.*");
        sql.FROM("ENV_ENVIOPROGRAMADO PROG");
        sql.JOIN("ENV_ENVIOS ENVIO ON ENVIO.IDENVIO = PROG.IDENVIO AND ENVIO.IDINSTITUCION = PROG.IDINSTITUCION");
												   
																																																			 				

        sql.WHERE(
                "ENVIO.IDESTADO = 4 AND (ENVIO.ENVIO = 'A' OR ENVIO.ENVIO = 'M') AND ENVIO.FECHAPROGRAMADA <= TO_DATE('"
                        + fechaHoy + "', 'DD/MM/YYYY HH24:MI:SS')");
        sql.ORDER_BY("ENVIO.FECHA DESC");
        return sql.toString();
    }

    public static String filtroTextoBusquedas(String columna, String cadena) {

        StringBuilder cadenaWhere = new StringBuilder();
        cadenaWhere.append(" TRANSLATE(LOWER( " + columna
                + "),'ÀÁÂÃÄÅàáâãäåÒÓÔÕÕÖØòóôõöøÈÉÊËèéêëðÇçÐÌÍÎÏìíîïÙÚÛÜùúûüÑñŠšŸÿýŽž','AAAAAAaaaaaaOOOOOOOooooooEEEEeeeeeCcDIIIIiiiiUUUUuuuuNnSsYyyZz') ");
        cadenaWhere.append(" LIKE");
        cadenaWhere.append(" TRANSLATE(LOWER('%" + cadena
                + "%'),'ÀÁÂÃÄÅàáâãäåÒÓÔÕÕÖØòóôõöøÈÉÊËèéêëðÇçÐÌÍÎÏìíîïÙÚÛÜùúûüÑñŠšŸÿýŽž','AAAAAAaaaaaaOOOOOOOooooooEEEEeeeeeCcDIIIIiiiiUUUUuuuuNnSsYyyZz') ");
        return cadenaWhere.toString();

		
    }
    
	public String obtenerEnviosIrrecuperables(Short minutos) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String fecha = dateFormat.format(new Date(System.currentTimeMillis() - (minutos * 60) * 1000));
		SQL sql = new SQL();

		sql.SELECT("ENVIO.IDENVIO");
		sql.SELECT("ENVIO.IDESTADO");
		sql.SELECT("ENVIO.IDINSTITUCION");
		
		sql.FROM("ENV_ENVIOS ENVIO");

		sql.WHERE(
				"ENVIO.IDESTADO IN (5) AND ENVIO.FECHAMODIFICACION <= TO_DATE('"
				+ fecha + "', 'DD/MM/YYYY HH24:MI:SS')");
		sql.ORDER_BY("ENVIO.FECHA DESC");
		return sql.toString();
	}
																						  																													 
    public String selectEnvioById(Short idInstitucion, String idLenguaje, String idEnvio) {
																						  																												
        SQL sql = new SQL();

        sql.SELECT("ENVIO.IDINSTITUCION");
        sql.SELECT("ENVIO.IDENVIO");
        sql.SELECT("ENVIO.DESCRIPCION");
        sql.SELECT("ENVIO.FECHA AS FECHACREACION");
        sql.SELECT("ENVIO.IDPLANTILLAENVIOS");
        sql.SELECT("ENVIO.IDESTADO");
        sql.SELECT("ENVIO.IDTIPOENVIOS");
        sql.SELECT("(SELECT NOMBRE FROM ENV_PLANTILLASENVIOS WHERE IDINSTITUCION = '" + idInstitucion
                + "' AND IDPLANTILLAENVIOS = ENVIO.IDPLANTILLAENVIOS AND IDTIPOENVIOS = ENVIO.IDTIPOENVIOS) AS NOMBREPLANTILLA");
        sql.SELECT("ENVIO.IDPLANTILLA");
        sql.SELECT("ENVIO.FECHAPROGRAMADA");
        sql.SELECT("ENVIO.FECHABAJA");
        sql.SELECT("PLANTILLA.ASUNTO");
        sql.SELECT("PLANTILLA.CUERPO");
        sql.SELECT(
                "(SELECT CAT.DESCRIPCION FROM ENV_TIPOENVIOS LEFT JOIN GEN_RECURSOS_CATALOGOS CAT ON CAT.IDRECURSO = ENV_TIPOENVIOS.NOMBRE WHERE ENV_TIPOENVIOS.IDTIPOENVIOS = ENVIO.IDTIPOENVIOS AND CAT.IDLENGUAJE = '"
                        + idLenguaje + "') AS TIPOENVIO");
        sql.SELECT(
                "(SELECT CAT.DESCRIPCION FROM ENV_ESTADOENVIO ESTADO LEFT JOIN GEN_RECURSOS_CATALOGOS CAT ON CAT.IDRECURSO = ESTADO.NOMBRE WHERE ESTADO.IDESTADO = ENVIO.IDESTADO AND CAT.IDLENGUAJE = '"
                        + idLenguaje + "') AS ESTADOENVIO");

        sql.FROM("ENV_ENVIOS ENVIO");
        sql.JOIN("ENV_PLANTILLASENVIOS PLANTILLA ON PLANTILLA.IDINSTITUCION = '" + idInstitucion
                + "' AND PLANTILLA.IDPLANTILLAENVIOS = ENVIO.IDPLANTILLAENVIOS AND PLANTILLA.IDTIPOENVIOS = ENVIO.IDTIPOENVIOS");

        sql.WHERE("ENVIO.IDINSTITUCION = '" + idInstitucion + "'");
  

        // con este campo controlamos que sea de envios Masivos
        sql.WHERE("ENVIO.ENVIO = 'M'");

        sql.WHERE("ENVIO.IDENVIO = '" + idEnvio + "'");

        return sql.toString();
    }

    public String selectEnviosByIdPlantilla(Short idInstitucion, String idPlantillaEnvio) {

        SQL sql = new SQL();

        sql.SELECT("ENVIO.IDENVIO");
        sql.FROM("ENV_ENVIOS ENVIO");
        sql.WHERE("ENVIO.IDINSTITUCION = '" + idInstitucion + "' AND ENVIO.IDPLANTILLAENVIOS = " + idPlantillaEnvio);

        return sql.toString();
    }
	
    public String obtenerDestinatarios(Short idInstitucion, String idEnvios) {

        SQL sql = new SQL();

        sql.SELECT("ENVIO.IDINSTITUCION");
        sql.SELECT("ENVIO.IDENVIO");
        sql.SELECT("(SELECT count(*) FROM env_dest_consulta_envio dest where dest.idenvio=ENVIO.IDENVIO AND dest.IDINSTITUCION = ENVIO.IDINSTITUCION) + " +
                "(SELECT count(*) FROM env_destinatarios dest where dest.idenvio=ENVIO.IDENVIO AND dest.IDINSTITUCION = ENVIO.IDINSTITUCION)  + " +
                "(SELECT count(*) FROM env_enviosgrupocliente dest where dest.idenvio=ENVIO.IDENVIO) AS NUMDEST");

        sql.FROM("ENV_ENVIOS ENVIO");
        sql.WHERE("ENVIO.IDINSTITUCION = '" + idInstitucion + "'");
        sql.WHERE("ENVIO.IDENVIO in (" + idEnvios + ")");

        return sql.toString();

    }

    public String eliminarEnviosPago(Short idInstitucion, List<String> idPagos) {

        SQL subQuery = new SQL();
        subQuery.SELECT("IDINSTITUCION");
        subQuery.SELECT("IDENVIO");
        subQuery.FROM("ENV_PROGRAMPAGOS");
        subQuery.WHERE("IDINSTITUCION = " + idInstitucion);
        subQuery.WHERE("IDPAGO IN (" + String.join(",", idPagos) + ")");

        SQL query = new SQL();
        query.DELETE_FROM("ENV_ENVIOS");
        query.WHERE("(IDINSTITUCION, IDENVIO) IN (" + subQuery.toString() + ")");

        return query.toString();
    }

    public String getNewIdEnvio() {
        SQL sql = new SQL();
        sql.SELECT("SEQ_ENV_ENVIOS.NEXTVAL");
        sql.FROM("DUAL");
        return sql.toString();
    }

}
