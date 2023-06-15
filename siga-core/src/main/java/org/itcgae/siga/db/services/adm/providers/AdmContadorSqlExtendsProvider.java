package org.itcgae.siga.db.services.adm.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.adm.ContadorRequestDTO;
import org.itcgae.siga.DTOs.adm.ParametroRequestDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmContador;
import org.itcgae.siga.db.mappers.AdmContadorSqlProvider;

public class AdmContadorSqlExtendsProvider extends AdmContadorSqlProvider {


    public String getModules() {
        SQL sql = new SQL();

        sql.SELECT("DISTINCT IDMODULO");
        sql.SELECT("INITCAP(NOMBRE) AS NOMBRE");
        sql.FROM("CON_MODULO");
        sql.ORDER_BY("NOMBRE");

        return sql.toString();
    }


    public String getMode(String idLenguaje) {
        SQL sql = new SQL();

        sql.SELECT("DISTINCT MODO.IDMODO");
        sql.SELECT("INITCAP(rec.DESCRIPCION) AS DESCRIPCION");
        sql.FROM("ADM_CONTADOR CON");
        sql.INNER_JOIN("ADM_MODO_CONTADOR MODO ON MODO.IDMODO = CON.MODO");
        sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS REC ON REC.IDRECURSO =  MODO.DESCRIPCION ");
        sql.WHERE("REC.IDLENGUAJE = '" + idLenguaje + "'");
        sql.ORDER_BY("DESCRIPCION");
        return sql.toString();
    }

    public String getContadoresSearch(int numPagina, ContadorRequestDTO contadorRequestDTO) {
        SQL sql = new SQL();

        sql.SELECT_DISTINCT("IDINSTITUCION");
        sql.SELECT("IDCONTADOR");
        sql.SELECT("NOMBRE");
        sql.SELECT("DESCRIPCION");
        sql.SELECT("MODIFICABLECONTADOR");
        sql.SELECT("MODO");
        sql.SELECT("CONTADOR");
        sql.SELECT("PREFIJO");
        sql.SELECT("SUFIJO");
        sql.SELECT("LONGITUDCONTADOR");
        sql.SELECT("FECHARECONFIGURACION");
        sql.SELECT("RECONFIGURACIONCONTADOR");
        sql.SELECT("RECONFIGURACIONPREFIJO");
        sql.SELECT("RECONFIGURACIONSUFIJO");
        sql.SELECT("IDTABLA");
        sql.SELECT("IDCAMPOCONTADOR");
        sql.SELECT("IDCAMPOPREFIJO");
        sql.SELECT("IDCAMPOSUFIJO");
        sql.SELECT("IDMODULO");
        sql.SELECT("GENERAL");
        sql.SELECT("FECHAMODIFICACION");
        sql.SELECT("USUMODIFICACION");
        sql.SELECT("FECHACREACION");
        sql.SELECT("USUCREACION");
        sql.FROM("ADM_CONTADOR");

        sql.WHERE("IDINSTITUCION = ('" + contadorRequestDTO.getIdInstitucion() + "')");

        if (null != contadorRequestDTO.getIdModulo() && contadorRequestDTO.getIdModulo() != "") {
            sql.WHERE("IDMODULO = ('" + contadorRequestDTO.getIdModulo() + "')");
        }
        if (null != contadorRequestDTO.getIdContador() && contadorRequestDTO.getIdContador() != "") {
            sql.WHERE("UPPER(IDCONTADOR) like UPPER('%" + contadorRequestDTO.getIdContador() + "%')");

        }
        if (null != contadorRequestDTO.getDescripcion() && contadorRequestDTO.getDescripcion() != "") {
            sql.WHERE("UPPER(DESCRIPCION) like UPPER('%" + contadorRequestDTO.getDescripcion() + "%')");
        }
        if (null != contadorRequestDTO.getNombre() && contadorRequestDTO.getNombre() != "") {
            sql.WHERE("UPPER(NOMBRE) like UPPER('%" + contadorRequestDTO.getNombre() + "%')");
        }
        sql.ORDER_BY("NOMBRE ASC");
        return sql.toString();
    }

    public String getParametersRecord(int numPagina, ParametroRequestDTO parametroRequestDTO) {
        SQL sql = new SQL();

        sql.SELECT("DISTINCT PARAM.MODULO");
        sql.SELECT("PARAM.PARAMETRO");
        sql.SELECT("DECODE( (select param2.valor from gen_parametros param2 where param.parametro = param2.parametro and idinstitucion = '" + parametroRequestDTO.getIdInstitucion() + "' and param.MODULO =param2.MODULO),"
                + "null,(select param2.valor from gen_parametros param2 where param.parametro = param2.parametro and idinstitucion = '0' and param.MODULO = param2.MODULO),"
                + "(select param2.valor from gen_parametros param2 where param.parametro = param2.parametro and idinstitucion = '" + parametroRequestDTO.getIdInstitucion() + "' and param.MODULO =param2.MODULO)) AS VALOR");

        sql.SELECT("DECODE(COUNT(IDINSTITUCION),1,'0','" + parametroRequestDTO.getIdInstitucion() + "') AS IDINSTITUCION");
        sql.SELECT("FECHA_BAJA");
        sql.FROM("gen_parametros param");
        sql.WHERE("IDINSTITUCION IN ('" + parametroRequestDTO.getIdInstitucion() + "','0')");
        sql.WHERE("MODULO = '" + parametroRequestDTO.getModulo() + "'");
        sql.GROUP_BY("param.modulo, param.parametro");

        return sql.toString();
    }

    public String comprobarUnicidadContadorProdCertif(int contadorSiguiente, AdmContador contador, String idTipoProducto,
                                                      String idProducto, String idProductoInstitucion) {

        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM(contador.getIdtabla());
        sql.WHERE("IDINSTITUCION = " + contador.getIdinstitucion());
        sql.WHERE(contador.getIdtabla() + "." + SigaConstants.C_PPN_IDTIPOPRODUCTO + " = " + idTipoProducto);
        sql.WHERE(contador.getIdtabla() + "." + SigaConstants.C_PPN_IDPRODUCTO + " = " + idProducto);
        sql.WHERE(contador.getIdtabla() + "." + SigaConstants.C_PPN_IDPRODUCTOINSTITUCION + " = " + idProductoInstitucion);
        sql.WHERE("TRIM(NVL(" + contador.getIdcampoprefijo() + ",'1')) = TRIM(NVL('" + contador.getPrefijo() + "','1'))");
        sql.WHERE("TRIM(NVL(" + contador.getIdcamposufijo() + ",'1')) = TRIM(NVL('" + contador.getSufijo() + "','1'))");
        sql.WHERE(contador.getIdcampocontador() + " = " + contadorSiguiente);

        return sql.toString();
    }

    public String getSiguienteNumContador(AdmContador contador) {
        SQL sql = new SQL();

        sql.SELECT("max (" + contador.getIdcampocontador() + ")+1 CONTADOR");

        sql.FROM(contador.getIdtabla());

        sql.WHERE("IDINSTITUCION = " + contador.getIdinstitucion() + " and " +
                contador.getIdcampoprefijo() + "= " + contador.getPrefijo() + " and " +
                contador.getIdcamposufijo() + "= " + contador.getSufijo());

        return sql.toString();
    }

    public String comprobarUnicidadContador(AdmContador contador, int contadorSiguiente) {
        SQL sql = new SQL();

        sql.SELECT(contador.getIdcampocontador() + " CONTADOR");

        sql.FROM(contador.getIdtabla());

        sql.WHERE("IDINSTITUCION = " + contador.getIdinstitucion() + " and " +
                contador.getIdcampocontador() + "= " + contadorSiguiente);

        if (contador.getPrefijo() != null && !contador.getPrefijo().equals("")) {
            sql.WHERE(contador.getIdcampoprefijo() + " = " + contador.getPrefijo());
        } else {
            sql.WHERE(contador.getIdcampoprefijo() + " IS NULL ");
        }

        if (contador.getSufijo() != null && !contador.getSufijo().equals("")) {
            sql.WHERE(contador.getIdcamposufijo() + " = " + contador.getSufijo());
        } else {
            sql.WHERE(contador.getIdcamposufijo() + " IS NULL ");
        }
        return sql.toString();
    }

}