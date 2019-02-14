package org.itcgae.siga.com.services.impl;

import org.itcgae.siga.db.entities.EnvEnvios;

public interface IColaEnvios {
	
	public void execute();
	public void preparaEnvioMail(EnvEnvios envio) throws Exception;
	public void preparaEnvioSMS(EnvEnvios envio, boolean isBuroSMS);

}
