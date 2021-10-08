package org.itcgae.siga.fac.services;


import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.fac.CuentasBancariasDTO;

public interface IFacturacionPySService {
	
	public CuentasBancariasDTO getCuentasBancarias(HttpServletRequest request);

}
 