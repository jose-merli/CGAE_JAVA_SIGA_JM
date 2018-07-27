package org.itcgae.siga.gen.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenCuentasbancarias;
import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.entities.CenNocolegiado;
import org.itcgae.siga.db.entities.CenPersona;

public interface IAuditoriaCenHistoricoService {

	public void insertaCenHistorico(Long idPersona, SigaConstants.CEN_TIPOCAMBIO tipoCambio, String descripcion, HttpServletRequest request, String motivo);
	
	public void manageAuditoriaDatosGenerales(List<String> gruposPerJuridicaNuevos, List<String> gruposPerJuridicaAntiguos, CenPersona cenPersonaAnterior, CenPersona cenPersonaPosterior, CenNocolegiado cenNocolegiadoAnterior, 
			CenNocolegiado cenNocolegiadoPosterior, CenCliente cenClienteAnterior, CenCliente cenClientePosterior, String accion, HttpServletRequest request, String motivo);
	
	public void manageAuditoriaDatosDirecciones(CenDirecciones cenDireccionesAnterior, CenDirecciones cenDireccionesPosterior, String accion, HttpServletRequest request, String motivo);
	
	public void manageAuditoriaDatosCuentasBancarias(CenCuentasbancarias cenCuentasbancariasAnterior, CenCuentasbancarias cenCuentasbancariasPosterior, String accion, HttpServletRequest request, String motivo);
}
