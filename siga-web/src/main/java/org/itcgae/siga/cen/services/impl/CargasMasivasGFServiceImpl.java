package org.itcgae.siga.cen.services.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.itcgae.siga.cen.services.ICargasMasivasGFService;
import org.itcgae.siga.commons.utils.ExcelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CargasMasivasGFServiceImpl implements ICargasMasivasGFService{
	
	@Autowired
	ICargasMasivasGFService cargasMasivasGFService;
	
	private Logger LOGGER = Logger.getLogger(CargasMasivasGFServiceImpl.class);
	
	@Override
	public File createExcelFile(List<String> orderList, Vector<Hashtable<String, Object>> datosVector) {
		if(orderList ==null && datosVector==null)
			LOGGER.info("No hay datos para crear el fichero");
		if(orderList ==null)
			orderList = new ArrayList<String>(datosVector.get(0).keySet());
		File XLSFile = ExcelHelper.createExcelFile(orderList , datosVector, cargasMasivasGFService.nombreFicheroEjemplo);
		return XLSFile;
		
	}

}
