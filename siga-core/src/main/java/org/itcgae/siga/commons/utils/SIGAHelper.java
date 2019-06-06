package org.itcgae.siga.commons.utils;

import java.io.File;
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
				Files.setPosixFilePermissions(file.toPath(), perms);
			} catch (Exception e) {
				LOGGER.warn("Error al cambiar los permisos del fichero " + file.getAbsolutePath(), e);
			}
				
		}
	}
	
	
	public static String quitarEtiquetas(String sentencia) {	
		if (sentencia != null) {
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
}