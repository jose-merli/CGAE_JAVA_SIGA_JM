package org.itcgae.siga.scs.services.ejg;

import java.io.File;
import java.util.Map;

import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.db.entities.ScsEejgPeticiones;

public interface IEEJGServices {

	public File getInformeEejg(Map<Integer, Map<String, String>> mapInformes, String idInstitucion) throws Exception;
	public Map<Integer, Map<String, String>> getDatosInformeEejg(EjgItem item, ScsEejgPeticiones peticion) throws Exception;
}
