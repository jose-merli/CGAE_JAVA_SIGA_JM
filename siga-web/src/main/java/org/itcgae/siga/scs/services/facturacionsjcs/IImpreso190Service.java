package org.itcgae.siga.scs.services.facturacionsjcs;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.scs.DocumentoActDesignaItem;
import org.itcgae.siga.DTOs.scs.Impreso190DTO;
import org.itcgae.siga.DTOs.scs.Impreso190Item;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface IImpreso190Service {

	Impreso190DTO impreso190generar(Impreso190Item impreso190Item, HttpServletRequest request) throws Exception;
	
	public ResponseEntity<InputStreamResource> impreso190descargar(Impreso190Item impreso190Item, HttpServletRequest request);
}
