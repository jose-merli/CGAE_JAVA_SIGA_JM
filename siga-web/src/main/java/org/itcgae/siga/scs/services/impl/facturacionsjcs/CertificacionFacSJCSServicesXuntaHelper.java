package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;
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
 
	
}


