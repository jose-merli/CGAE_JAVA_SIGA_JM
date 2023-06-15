package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.constants.SigaConstants.OPERACION;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.EcomCola;
import org.itcgae.siga.db.entities.FcsFactEstadosfacturacion;
import org.itcgae.siga.db.entities.FcsFacturacionjgKey;
import org.itcgae.siga.db.services.fcs.mappers.FcsFactEstadosfacturacionExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * Migración de diversos métodos SIGA classic para la Facturación SJCS
 *
 * @author mmorenomartin
 */

@Service
public class CertificacionFacSJCSServicesXuntaHelper {

    private static final String ZIP_PREFIX = "CertXunta";


    private static final String ZIP_EXT = ".zip";


    private static final Logger LOGGER = Logger.getLogger(CertificacionFacSJCSServicesXuntaHelper.class);


    @Autowired
    private FacturacionSJCSHelper facHelper;

    @Autowired
    private FcsFactEstadosfacturacionExtendsMapper fcsFactEstadosfacturacionExtendsMapper;

    public Resource generaFicheroCertificacionesXunta(Short idInstitucion, List<String> lFacturaciones, String idEstadoCertificacion) throws BusinessException {

        ByteArrayOutputStream byteArrayOutputStream = null;

        try {

            Set<String> sFacturaciones = new HashSet<>(lFacturaciones);
            Predicate<Path> predZip = p -> Files.isDirectory(p) && sFacturaciones.contains(p.getFileName().toString());
            Path pInstitucion = null;
            if (SigaConstants.ESTADO_CERTIFICACION.ESTADO_CERTIFICACION_CERRADA.getCodigo().equalsIgnoreCase(idEstadoCertificacion)) {
                pInstitucion = facHelper.getRutaAlmacenFichero().resolve(FacturacionSJCSHelper.JE_ABOGADOS).resolve(String.valueOf(idInstitucion));
            } else if (SigaConstants.ESTADO_CERTIFICACION.ESTADO_CERTIFICACION_NO_VALIDADA.getCodigo().equalsIgnoreCase(idEstadoCertificacion)) {
                pInstitucion = facHelper.getRutaAlmacenFichero().resolve(FacturacionSJCSHelper.INFROME_INCIDENCIAS_WS).resolve(String.valueOf(idInstitucion));
            } else {
                throw new Exception("La certificacion no tiene un estado correcto");
            }
            Stream<Path> pZipear = Files.list(pInstitucion).filter(predZip);
            List<File> fsZipear = pZipear.map(p -> p.toFile()).collect(Collectors.toList());

            byteArrayOutputStream = new ByteArrayOutputStream();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
            ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

            for (File file : fsZipear) {
                addDir(file, zipOutputStream, pInstitucion.toString());
            }

            zipOutputStream.closeEntry();

            if (zipOutputStream != null) {
                zipOutputStream.finish();
                zipOutputStream.flush();
                IOUtils.closeQuietly(zipOutputStream);
            }

            IOUtils.closeQuietly(bufferedOutputStream);
            IOUtils.closeQuietly(byteArrayOutputStream);

        } catch (Exception e) {
            LOGGER.error(e);
            throw new BusinessException("error en generaFicheroCertificacionesXunta", e);
        }

        return new ByteArrayResource(byteArrayOutputStream.toByteArray());
    }

    private void addDir(File dir, ZipOutputStream out, String delPrefix) throws IOException {

        File[] files = dir.listFiles();

        for (int i = 0; i < files.length; i++) {

            if (files[i].isDirectory()) {
                addDir(files[i], out, delPrefix);
                continue;
            }

            String addFName = files[i].getPath().replace(delPrefix, "");
            LOGGER.info(" Adding: " + addFName);
            out.putNextEntry(new ZipEntry(addFName));
            Files.copy(files[i].toPath(), out);

        }

    }

    public void envioJustificacion(Short idInstitucion, String idFacturacion) throws Exception {
        envioGenerico(idInstitucion, idFacturacion, OPERACION.XUNTA_ENVIO_JUSTIFICACION);
    }

    public void envioReintegros(Short idInstitucion, String idFacturacion) throws Exception {
        envioGenerico(idInstitucion, idFacturacion, OPERACION.XUNTA_ENVIO_REINTEGROS);
    }


    private void envioGenerico(Short idinstitucion, String idfacturacion, OPERACION operacion) throws Exception {
        if (idinstitucion == null || idfacturacion == null) {
            String error = "Los parámetros idinstitucion e idfacturación deben ser no nulos";
            LOGGER.error(error);
            throw new IllegalArgumentException(error);
        }

        LOGGER.debug(String.format("Se va a insertar un nuevo estado y solicitar e ecom el envío para el colegio %s e idFacturación %s", idinstitucion, idfacturacion));

        if (actualizarEstadoFacturacion(facHelper.getUsuarioAuto(), idinstitucion, idfacturacion, SigaConstants.ESTADO_FACTURACION.ESTADO_FACTURACION_ENVIO_EN_PROCESO.getCodigo().shortValue()) != 1) {
            String error = String.format("No se ha insertado o se ha insertado más de un estado en FcsFactEstadosfacturacion para el colegio %s e idfacturación %s", idinstitucion, idfacturacion);
            LOGGER.error(error);
            throw new BusinessException(error);
        }
        LOGGER.debug(String.format("Se ha insertado correctamente el nuevo estado para el colegio %s e idfacturacion %s", idinstitucion, idfacturacion));

        EcomCola ecomCola = new EcomCola();
        ecomCola.setIdinstitucion(idinstitucion);
        ecomCola.setIdoperacion(operacion.getId());

        Map<String, String> mapa = new HashMap<String, String>();
        mapa.put(SigaConstants.C_IDINSTITUCION, idinstitucion.toString());
        mapa.put(SigaConstants.C_IDFACTURACION, idfacturacion.toString());

        facHelper.insertaColaConParametros(ecomCola, mapa);
    }

    private int actualizarEstadoFacturacion(AdmUsuarios usuario, Short idInstitucion, String idFacturacion, Short estadoFuturo) {

        int respuesta;

        try {

            String idOrdenEstado = fcsFactEstadosfacturacionExtendsMapper.getIdordenestadoMaximo(idInstitucion, idFacturacion);
            FcsFactEstadosfacturacion fcsFactEstadosfacturacion = new FcsFactEstadosfacturacion();
            fcsFactEstadosfacturacion.setIdinstitucion(idInstitucion);
            fcsFactEstadosfacturacion.setIdfacturacion(Integer.valueOf(idFacturacion));
            fcsFactEstadosfacturacion.setIdestadofacturacion(estadoFuturo);
            fcsFactEstadosfacturacion.setIdordenestado(Short.valueOf(idOrdenEstado));
            fcsFactEstadosfacturacion.setFechaestado(new Date());
            fcsFactEstadosfacturacion.setFechamodificacion(new Date());
            fcsFactEstadosfacturacion.setUsumodificacion(usuario.getIdusuario());
            respuesta = fcsFactEstadosfacturacionExtendsMapper.insert(fcsFactEstadosfacturacion);

        } catch (Exception e) {
            respuesta = 0;
        }

        return respuesta;
    }

}


