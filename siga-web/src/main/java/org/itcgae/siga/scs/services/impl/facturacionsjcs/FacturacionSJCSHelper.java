package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import org.apache.log4j.Logger;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.constants.SigaConstants.ECOM_ESTADOSCOLA;
import org.itcgae.siga.db.entities.*;
import org.itcgae.siga.db.mappers.EcomColaMapper;
import org.itcgae.siga.db.mappers.EcomColaParametrosMapper;
import org.itcgae.siga.db.mappers.EcomOperacionMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class FacturacionSJCSHelper {


    private static final Integer ID_USU_DEFAULT = 0;

    private static AdmUsuarios USU_DEFAULT = null;

    private Logger LOGGER = Logger.getLogger(FacturacionSJCSHelper.class);

    public static final String JE_ABOGADOS = "JEAbogados";

    public static final String INFROME_INCIDENCIAS_WS = "informeIncidenciasWS";

    @Autowired
    private EcomColaMapper ecomColaMapper;

    @Autowired
    private EcomColaParametrosMapper ecomColaParametrosMapper;

    @Autowired
    private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

    @Autowired
    private GenPropertiesMapper genPropertiesMapper;

    @Autowired
    private EcomOperacionMapper ecomOperacionMapper;


    public void insertaColaConParametros(EcomCola ecomCola, Map<String, String> parametros, AdmUsuarios usuario) throws Exception {

        insertartCola(ecomCola, usuario);

        if (parametros != null) {
            Iterator<String> it = parametros.keySet().iterator();

            while (it.hasNext()) {
                EcomColaParametros ecomColaParametros = new EcomColaParametros();
                String clave = it.next();
                String valor = parametros.get(clave);
                ecomColaParametros.setIdecomcola(ecomCola.getIdecomcola());
                ecomColaParametros.setClave(clave);
                ecomColaParametros.setValor(valor);
                if (ecomColaParametrosMapper.insert(ecomColaParametros) != 1) {
                    throw new Exception("Error al insertar los parámetros de la cola.");
                }
            }
        }
    }

    public void insertaColaConParametros(EcomCola ecomCola, Map<String, String> parametros) throws Exception {
        insertaColaConParametros(ecomCola, parametros, getUsuarioAuto());
    }


    private int insertartCola(EcomCola ecomCola, AdmUsuarios usuario) {

        try {
            ecomCola.setIdestadocola(SigaConstants.ECOM_ESTADOSCOLA.INICIAL.getId());
            ecomCola.setReintento(0);
            ecomCola.setFechacreacion(new Date());
            ecomCola.setFechamodificacion(new Date());
            ecomCola.setUsumodificacion(usuario.getIdusuario());

            return ecomColaMapper.insert(ecomCola);
        } catch (Exception e) {
            LOGGER.error(String.format("Se ha producido un error al insertar en la cola %s", ecomCola));
            throw e;
        }
    }


    public AdmUsuarios getUsuario(Short idInstitucion, String dni) {
        AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
        exampleUsuarios.createCriteria()
                .andNifEqualTo(dni)
                .andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
        List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
        return usuarios.get(0);
    }

    public AdmUsuarios getUsuarioAuto() {
        if (USU_DEFAULT == null) {
            USU_DEFAULT = new AdmUsuarios();
            USU_DEFAULT.setIdusuario(ID_USU_DEFAULT);
        }
        return USU_DEFAULT;
    }


    public Path getRutaAlmacenFichero(Short idInstitucion) {
        return getRutaAlmacenFichero().resolve(String.valueOf(idInstitucion));
    }


    public Path getRutaAlmacenFichero() {
        GenPropertiesKey key = new GenPropertiesKey();
        key.setFichero(SigaConstants.FICHERO_SIGA);
        key.setParametro(SigaConstants.parametroRutaAlmacenFicheros);
        GenProperties rutaFicherosSalida = genPropertiesMapper.selectByPrimaryKey(key);
        String rutaRaiz = rutaFicherosSalida.getValor();
        return Paths.get(rutaRaiz);
    }

    public boolean isEjecutandoOperacionFacturacion(Short idinstitucion, String idFacturacion, int operacionCompruebaEnEjecucion) {
        boolean ejecutandose = false;

        EcomColaParametrosExample ecomColaParametrosExample = new EcomColaParametrosExample();

        ecomColaParametrosExample.createCriteria().andClaveEqualTo(SigaConstants.C_IDFACTURACION).andValorEqualTo(idFacturacion.toString());
        List<EcomColaParametros> listaEcomColaParametros = ecomColaParametrosMapper.selectByExample(ecomColaParametrosExample);

        if (listaEcomColaParametros != null && listaEcomColaParametros.size() > 0) {
            LOGGER.debug("Posibles candidatos para ver si la facturación ha sido ejecutada o se está ejecutando");
            List<Long> ids = new ArrayList<Long>();
            for (EcomColaParametros ecomColaParametros : listaEcomColaParametros) {
                ids.add(ecomColaParametros.getIdecomcola());
            }

            List<Short> listaEstados = new ArrayList<Short>();
            listaEstados.add(ECOM_ESTADOSCOLA.INICIAL.getId());
            listaEstados.add(ECOM_ESTADOSCOLA.EJECUTANDOSE.getId());
            listaEstados.add(ECOM_ESTADOSCOLA.REINTENTANDO.getId());

            EcomColaExample ecomColaExample = new EcomColaExample();
            ecomColaExample.createCriteria().andIdinstitucionEqualTo(idinstitucion).andIdoperacionEqualTo(operacionCompruebaEnEjecucion).andIdestadocolaIn(listaEstados).andIdecomcolaIn(ids);
            long count = ecomColaMapper.countByExample(ecomColaExample);
            if (count > 0) {
                ejecutandose = true;
            } else {
                EcomOperacion operacion = ecomOperacionMapper.selectByPrimaryKey(operacionCompruebaEnEjecucion);
                ecomColaExample = new EcomColaExample();
                ecomColaExample.createCriteria().andIdinstitucionEqualTo(idinstitucion).andIdoperacionEqualTo(operacionCompruebaEnEjecucion).andIdestadocolaEqualTo(ECOM_ESTADOSCOLA.ERROR.getId()).andReintentoLessThan(operacion.getMaxreintentos()).andIdecomcolaIn(ids);
                count = ecomColaMapper.countByExample(ecomColaExample);
                if (count > 0)
                    ejecutandose = true;

            }
        }

        LOGGER.debug("¿La facturación del colegio " + idinstitucion + " con idFacturacion " + idFacturacion + " está en ejecución? = '" + ejecutandose + "'");

        return ejecutandose;
    }


}
