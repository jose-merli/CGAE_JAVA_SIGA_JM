package org.itcgae.siga.gen.services.impl;

import org.apache.log4j.Logger;
import org.itcgae.siga.commons.constants.SigaEndPointsProcess;
import org.itcgae.siga.gen.services.IProcesoService;
import org.springframework.stereotype.Service;




@Service
public class ProcesoServiceImpl implements IProcesoService {
	
	Logger LOGGER = Logger.getLogger(ProcesoServiceImpl.class);

	@Override
	public String getIdProceso(String url) {
		String idproceso = null;
		if(SigaEndPointsProcess.PROCESS.get(url) != null) {
			idproceso = SigaEndPointsProcess.PROCESS.get(url);
		}
		return idproceso;
	}

}
