package org.redabogacia.ecom.services;

import org.itcgae.siga.commons.constants.SigaConstants.GEN_PROPERTIES_FICHERO;

public interface IRefrescaVariablesService {
	public void iniciaTodo();
	public void initLog4j(GEN_PROPERTIES_FICHERO fichero);
}
