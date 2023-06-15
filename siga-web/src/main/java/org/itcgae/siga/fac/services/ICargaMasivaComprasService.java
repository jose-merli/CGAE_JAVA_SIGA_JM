package org.itcgae.siga.fac.services;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.EncryptedDocumentException;
import org.itcgae.siga.DTO.fac.CargaMasivaComprasBusquedaItem;
import org.itcgae.siga.DTO.fac.CargaMasivaComprasDTO;
import org.itcgae.siga.DTO.fac.CargaMasivaComprasItem;
import org.itcgae.siga.DTO.fac.FiltroCargaMasivaCompras;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

public interface ICargaMasivaComprasService {

	public InputStreamResource descargarModelo(HttpServletRequest request)
			throws IOException, EncryptedDocumentException, InvalidFormatException;

	public DeleteResponseDTO cargarFichero(MultipartHttpServletRequest request) throws Exception;

	public ResponseEntity<InputStreamResource> descargarFicheros(List<CargaMasivaComprasItem> cargaMasivaComprasItem,
			HttpServletRequest request);

	public CargaMasivaComprasDTO listado(FiltroCargaMasivaCompras cargaMasivaItem, HttpServletRequest request);

}
