package org.itcgae.siga.scs.services.facturacionsjcs;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.scs.DocumentoActDesignaItem;
import org.itcgae.siga.DTOs.scs.Impreso190DTO;
import org.itcgae.siga.DTOs.scs.Impreso190Item;
import org.itcgae.siga.db.entities.FcsConfImpreso190;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface IImpreso190Service {

	Impreso190DTO impreso190generar(Impreso190Item impreso190Item, HttpServletRequest request) throws Exception;
	
	public ResponseEntity<InputStreamResource> impreso190descargar(List<Impreso190Item> impreso190Item, HttpServletRequest request);
	
	Impreso190DTO searchImpreso190(String[] anio, HttpServletRequest request) throws Exception;

	Impreso190DTO deleteImpreso190(List<Impreso190Item> impreso190Item, HttpServletRequest request) throws Exception;
	
	Impreso190DTO getConfImpreso190(HttpServletRequest request) throws Exception;

	ComboDTO getComboAnio(HttpServletRequest request);
}
