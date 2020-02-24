package org.itcgae.siga.com.services;

import org.itcgae.siga.db.entities.EnvEnvios;

public interface IColaEnvios {
	
	public void execute();
	public void preparaCorreo(EnvEnvios envio) throws Exception;
	public void preparaEnvioSMS(EnvEnvios envio, boolean isBuroSMS) throws Exception;
}
