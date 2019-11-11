package org.itcgae.siga.scs.services.ejg;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;

public interface IBusquedaEJG {

	ComboDTO comboTipoEJG(HttpServletRequest request);

}
