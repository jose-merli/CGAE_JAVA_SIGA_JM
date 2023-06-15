package org.itcgae.siga.scs.services.maestros;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.scs.DocumentacionEjgDTO;
import org.itcgae.siga.DTOs.scs.DocumentacionEjgItem;

public interface IBusquedaDocumentacionEjgService {
	public DocumentacionEjgDTO searchDocumento(DocumentacionEjgItem documentacionEjgItem, HttpServletRequest request);

	public UpdateResponseDTO deleteTipoDoc(DocumentacionEjgDTO documentacionejgDTO, HttpServletRequest request);

	public DocumentacionEjgDTO searchDocumentos(DocumentacionEjgItem documentacionejgItem, HttpServletRequest request);



	public InsertResponseDTO createTipoDoc(DocumentacionEjgItem documentacionejgItem, HttpServletRequest request);

	public UpdateResponseDTO updateTipoDoc(DocumentacionEjgItem documentacionejgItem, HttpServletRequest request);

	public UpdateResponseDTO deleteDoc(DocumentacionEjgDTO documentacionejgDTO, HttpServletRequest request);

	public UpdateResponseDTO updateDoc(DocumentacionEjgDTO documentacionejgDTO, HttpServletRequest request);

	public InsertResponseDTO createDoc(DocumentacionEjgItem documentacionejgItem, HttpServletRequest request);

	//public UpdateResponseDTO activateCourt(DocumentacionEjgDTO documentacionEjgDTO, HttpServletRequest request);
}
