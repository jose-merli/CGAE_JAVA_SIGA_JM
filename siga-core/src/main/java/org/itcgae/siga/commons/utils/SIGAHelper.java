package org.itcgae.siga.commons.utils;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

public class SIGAHelper {
	
	private static final Logger LOGGER = Logger.getLogger(SIGAHelper.class);

	
	public static void addPerm777(File file) {
		if (file != null && file.exists()) {
			Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
			// add owners permission
			perms.add(PosixFilePermission.OWNER_READ);
			perms.add(PosixFilePermission.OWNER_WRITE);
			perms.add(PosixFilePermission.OWNER_EXECUTE);
			// add group permissions
			perms.add(PosixFilePermission.GROUP_READ);
			perms.add(PosixFilePermission.GROUP_WRITE);
			perms.add(PosixFilePermission.GROUP_EXECUTE);
			// add others permissions
			perms.add(PosixFilePermission.OTHERS_READ);
			perms.add(PosixFilePermission.OTHERS_WRITE);
			perms.add(PosixFilePermission.OTHERS_EXECUTE);
			
			try {
				File parentFile = file.getParentFile();
				if (parentFile != null && parentFile.exists()) {
					LOGGER.debug("Cambiando los permisos del padre: " + parentFile.getAbsolutePath());
					addPerm777(parentFile);
				}
				boolean isPosix = FileSystems.getDefault().supportedFileAttributeViews().contains("posix");
				if (isPosix) {
					Files.setPosixFilePermissions(file.toPath(), perms);
				} else {
					file.setReadable(true);
					file.setWritable(true);
					file.setExecutable(false);
				}
			} catch (Exception e) {
				LOGGER.warn("Error al cambiar los permisos del fichero " + file.getAbsolutePath() + " - ERROR: " + e.toString() + " - " + e.getMessage());
			}
		}
	}

	public static String quitarEtiquetas(String sentencia) {	
		if (sentencia != null) {
			sentencia = sentencia.toUpperCase();
			sentencia = sentencia.replaceAll("<SELECT>", " ");
			sentencia = sentencia.replaceAll("</SELECT>", " ");
			sentencia = sentencia.replaceAll("<FROM>", " ");
			sentencia = sentencia.replaceAll("</FROM>", " ");
			sentencia = sentencia.replaceAll("<JOIN>", " ");
			sentencia = sentencia.replaceAll("</JOIN>", " ");
			sentencia = sentencia.replaceAll("<OUTERJOIN>", " ");
			sentencia = sentencia.replaceAll("</OUTERJOIN>", " ");
			sentencia = sentencia.replaceAll("<INNERJOIN>", " ");
			sentencia = sentencia.replaceAll("</INNERJOIN>", " ");
			sentencia = sentencia.replaceAll("<LEFTJOIN>", " ");
			sentencia = sentencia.replaceAll("</LEFTJOIN>", " ");
			sentencia = sentencia.replaceAll("<WHERE>", " ");
			sentencia = sentencia.replaceAll("</WHERE>", " ");
			sentencia = sentencia.replaceAll("<ORDERBY>", " ");
			sentencia = sentencia.replaceAll("</ORDERBY>", " ");
			sentencia = sentencia.replaceAll("<ORDER BY>", " ");
			sentencia = sentencia.replaceAll("</ORDER BY>", " ");
			sentencia = sentencia.replaceAll("<GROUPBY>", " ");
			sentencia = sentencia.replaceAll("</GROUPBY>", " ");
			sentencia = sentencia.replaceAll("<GROUP BY>", " ");
			sentencia = sentencia.replaceAll("</GROUP BY>", " ");
			sentencia = sentencia.replaceAll("<HAVING>", " ");
			sentencia = sentencia.replaceAll("</HAVING>", " ");
			sentencia = sentencia.replaceAll("<UNION>", " ");
			sentencia = sentencia.replaceAll("<UNION>", " ");
			sentencia = sentencia.replaceAll("<UNIONALL>", " ");
			sentencia = sentencia.replaceAll("</UNIONALL>", " ");
		}
		
		return sentencia;
	}

	public static void mkdirs(String filePath) {

		if (filePath == null || filePath.trim().equalsIgnoreCase("")) {
			return;
		}

		File fileDir = new File(filePath.toString());
		if (fileDir != null && !fileDir.exists()) {
			fileDir.mkdirs();
			addPerm777(fileDir);
		}
	}

}
