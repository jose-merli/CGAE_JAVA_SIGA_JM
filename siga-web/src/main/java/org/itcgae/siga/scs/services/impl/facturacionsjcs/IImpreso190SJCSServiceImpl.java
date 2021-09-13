package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.scs.Impreso190DTO;
import org.itcgae.siga.DTOs.scs.Impreso190Item;
import org.itcgae.siga.scs.services.facturacionsjcs.IImpreso190Service;

public class IImpreso190SJCSServiceImpl implements IImpreso190Service{

	/*@Override
	public Impreso190DTO impreso190generar(Impreso190Item impreso190Item, HttpServletRequest request) {
		String salida = null;
		String sNombreFichero = "";
		try {
			
			UsrBean user = (UsrBean) request.getSession().getAttribute("USRBEAN");

			// borro el formulario en session de Avanzada
			GenerarImpreso190Form miform = (GenerarImpreso190Form)request.getAttribute("generarImpreso190Form");

			// realizo la generaciónv
	    	sNombreFichero = miform.getNombreFicheroOriginal();
	    	String sDirectorio = getPathTemporal(user);
    		
	    	// creo el directorio si no existe
	    	FileHelper.mkdirs(sDirectorio + File.separator + user.getLocation());
    		
	    	String sNombreCompletoFichero=sDirectorio + File.separator + user.getLocation() + File.separator + sNombreFichero;
			File fichero = new File(sNombreCompletoFichero);
			
			FcsFacturacionJGAdm admFac = new FcsFacturacionJGAdm(this.getUserBean(request));
			try {
				request.removeAttribute("mensaje");
				fichero = admFac.generarImpreso190(miform, user.getLocation());
				miform.setNombreFichero(fichero.getName());
				
				//Controlo si se ha generado un zip por tener errores:
				if (fichero.getName().indexOf(".zip")!=-1) {
					request.setAttribute("mensaje", "messages.error.log190");
					request.setAttribute("logError", "SI");
				} else
					request.setAttribute("logError", "NO");
			} catch (SIGAException se) {
				request.setAttribute("mensaje",se.getLiteral(this.getLenguaje(request)));
			}		
			
			salida = "exitoImpreso190";
		}
		catch (Exception e) {
			throwExcp("messages.general.error",new String[] {"modulo.facturacionSJCS"},e,null);
		}
		// COMUN
		return salida;
	}*/

}
