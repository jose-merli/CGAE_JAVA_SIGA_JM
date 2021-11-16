package org.itcgae.siga.scs.services.facturacionsjcs;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.scs.Impreso190DTO;
import org.itcgae.siga.DTOs.scs.Impreso190Item;
import org.springframework.web.bind.annotation.RequestBody;

public interface IImpreso190Service {

	Impreso190DTO impreso190generar(Impreso190Item impreso190Item, HttpServletRequest request) throws Exception;
}
