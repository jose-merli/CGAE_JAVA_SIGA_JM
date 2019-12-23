package org.itcgae.siga.scs.services.ejg;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.EjgDTO;
import org.itcgae.siga.DTOs.scs.EjgItem;
import org.itcgae.siga.DTOs.scs.UnidadFamiliarEJGDTO;

public interface IGestionEJG {

	EjgDTO datosEJG(EjgItem ejgItem, HttpServletRequest request);

	ComboDTO comboPrestaciones(HttpServletRequest request);

	UnidadFamiliarEJGDTO unidadFamiliarEJG(EjgItem ejgItem, HttpServletRequest request);
}
