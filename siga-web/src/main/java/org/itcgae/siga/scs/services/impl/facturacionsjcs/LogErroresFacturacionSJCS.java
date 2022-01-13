package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import org.itcgae.siga.exception.FacturacionSJCSException;

public interface LogErroresFacturacionSJCS {
	void logError(String error);
	void writeAllErrors() throws FacturacionSJCSException;
	void writeExito() throws FacturacionSJCSException;
	void writeExito(String msg) throws FacturacionSJCSException;
	boolean haveErrors();
}
