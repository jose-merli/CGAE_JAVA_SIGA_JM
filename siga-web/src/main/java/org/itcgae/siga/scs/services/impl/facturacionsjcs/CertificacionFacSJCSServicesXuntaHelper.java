package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.constants.SigaConstants.OPERACION;
import org.itcgae.siga.db.entities.EcomCola;
import org.itcgae.siga.db.entities.FcsFactEstadosfacturacion;
import org.itcgae.siga.db.entities.FcsFacturacionjgKey;
import org.itcgae.siga.db.mappers.FcsFactEstadosfacturacionMapper;
import org.itcgae.siga.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Migración de diversos métodos SIGA classic para la Facturación SJCS 
 * @author mmorenomartin
 *
 */

@Service
public class CertificacionFacSJCSServicesXuntaHelper {
	
	private static final String ZIP_PREFIX = "CertXunta";


	private static final String ZIP_EXT = ".zip";


	private static final Logger LOGGER = Logger.getLogger(CertificacionFacSJCSServicesXuntaHelper.class);

	
	@Autowired
	private FacturacionSJCSHelper facHelper;
	private FcsFactEstadosfacturacionMapper fcsFactEstadosfacturacionMapper; 
	
	public File generaFicheroCertificacionesXunta(Short idInstitucion, List<String> lFacturaciones) throws BusinessException{
		File fCert=null;
		Set<String> sFacturaciones = new HashSet<>(lFacturaciones);
		Predicate<Path> predZip = p->Files.isDirectory(p)&&sFacturaciones.contains(p.getFileName().toString());
		Path pInstitucion = facHelper.getRutaAlmacenFichero().resolve(FacturacionSJCSHelper.JE_ABOGADOS).resolve(String.valueOf(idInstitucion));
		
		try {
			fCert = Files.createTempFile(ZIP_PREFIX,ZIP_EXT).toFile();
			LOGGER.info("temporal:" + fCert);
		} catch (IOException e) {
			LOGGER.error(e);
			throw new BusinessException("error en generaFicheroCertificacionesXunta",e);
		}

		try (FileOutputStream fout = new FileOutputStream(fCert); ZipOutputStream zout = new ZipOutputStream(fout);) {
			Stream<Path> pZipear = Files.list(pInstitucion).filter(predZip);
			List<File> fsZipear = pZipear.map(p -> p.toFile()).collect(Collectors.toList());
			for (File file : fsZipear) {
				addDir(file, zout, pInstitucion.toString());
			}
		} catch (Exception e) {
			LOGGER.error(e);
			throw new BusinessException("error en generaFicheroCertificacionesXunta", e);
		}
   		
		return fCert;
	}
    
	    /**
	     * @param directorio a comprimir
	     * @param out objeto donde se realizará la compresión
	     * @param delPrefix prefijo a eliminar del zip (directorios) Ej. Si se quiere comprimir todo bajo C:\prueba\, tenemos C:\prueba\dir1 y C:\prueba\dir2 y lo
	     * 	queremos comprimido con carpetas dir1 y dir2 hay que indicar "C:\prueba\" como este parámetro. Si no, se comprimirán como prueba\dir1 y prueba\dir2 
	     * @throws IOException
	     */
	    static void addDir(File dir, ZipOutputStream out, String delPrefix) throws IOException {
	        File[] files = dir.listFiles();

	        for (int i = 0; i < files.length; i++) {
	         LOGGER.info("procesando files[" + i + "]:" + files[i]);
	          if (files[i].isDirectory()) {
	            addDir(files[i], out, delPrefix);
	            continue;
	          }
	          String addFName = files[i].getAbsolutePath().replace(delPrefix,"");
	          
	          LOGGER.info(" Adding: " + addFName);
	          out.putNextEntry(new ZipEntry(addFName));
	          Files.copy(files[i].toPath(), out);
	          out.closeEntry();
	        }
	      }

		public void envioJustificacion(Short idInstitucion, String idFacturacion)  throws Exception {
			envioGenerico(idInstitucion,idFacturacion,OPERACION.XUNTA_ENVIO_JUSTIFICACION);
		}

		public void envioReintegros(Short idInstitucion, String idFacturacion)  throws Exception {
			envioGenerico(idInstitucion,idFacturacion,OPERACION.XUNTA_ENVIO_REINTEGROS);
		} 
		
		
		
		private void envioGenerico(Short idinstitucion, String idfacturacion, OPERACION operacion) throws Exception {
			if (idinstitucion == null || idfacturacion == null) {
				String error = "Los parámetros idinstitucion e idfacturación deben ser no nulos";
				LOGGER.error(error);
				throw new IllegalArgumentException(error);
			}

			LOGGER.debug(String.format("Se va a insertar un nuevo estado y solicitar e ecom el envío para el colegio %s e idFacturación %s", idinstitucion, idfacturacion));
			FcsFactEstadosfacturacion fcsFactEstadosfacturacion = new FcsFactEstadosfacturacion();
			fcsFactEstadosfacturacion.setIdinstitucion(idinstitucion);
			fcsFactEstadosfacturacion.setIdfacturacion(Integer.valueOf(idfacturacion));	
			fcsFactEstadosfacturacion.setIdestadofacturacion(SigaConstants.ESTADO_FACTURACION.ESTADO_FACTURACION_ENVIO_EN_PROCESO.getCodigo().shortValue());
			fcsFactEstadosfacturacion.setFechaestado(Calendar.getInstance().getTime());
			fcsFactEstadosfacturacion.setFechamodificacion(Calendar.getInstance().getTime());
			fcsFactEstadosfacturacion.setUsumodificacion(facHelper.getUsuarioAuto().getIdusuario());
			 
			if (fcsFactEstadosfacturacionMapper.insert(fcsFactEstadosfacturacion) != 1) {
				String error = String.format("No se ha insertado o se ha insertado más de un estado en FcsFactEstadosfacturacion para el colegio %s e idfacturación %s", idinstitucion, idfacturacion);
				LOGGER.error(error);
				throw new BusinessException(error);
			}
			LOGGER.debug(String.format("Se ha insertado correctamente el nuevo estado para el colegio %s e idfacturacion %s", idinstitucion, idfacturacion));
			
			EcomCola ecomCola = new EcomCola();
			ecomCola.setIdinstitucion(idinstitucion);
			ecomCola.setIdoperacion(operacion.getId());	
				
			Map<String, String> mapa = new HashMap<String, String>();
			mapa.put(FcsFacturacionjgKey.C_IDINSTITUCION, idinstitucion.toString());
			mapa.put(FcsFacturacionjgKey.C_IDFACTURACION, idfacturacion.toString());		
			
			facHelper.insertaColaConParametros(ecomCola, mapa);
		}
 
	
}


