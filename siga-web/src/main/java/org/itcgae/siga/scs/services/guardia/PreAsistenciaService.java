/**
 * 
 */
package org.itcgae.siga.scs.services.guardia;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.PreAsistenciaDTO;
import org.itcgae.siga.DTOs.scs.PreAsistenciaItem;

/**
 * @author pjarana
 *
 */
public interface PreAsistenciaService {
	
	public PreAsistenciaDTO searchPreasistencias(HttpServletRequest request, PreAsistenciaItem filtro);
	
	public UpdateResponseDTO denegarPreasistencias(HttpServletRequest request, List<PreAsistenciaItem> preasistencias);
	
	public UpdateResponseDTO activarPreAsistenciasDenegadas(HttpServletRequest request, List<PreAsistenciaItem> preasistencias);

}
