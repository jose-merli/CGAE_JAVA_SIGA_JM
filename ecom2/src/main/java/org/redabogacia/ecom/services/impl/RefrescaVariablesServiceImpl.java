package org.redabogacia.ecom.services.impl;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.itcgae.siga.commons.constants.SigaConstants.GEN_PROPERTIES_FICHERO;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.redabogacia.ecom.services.IRefrescaVariablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefrescaVariablesServiceImpl implements IRefrescaVariablesService {
	
	private static Logger LOGGER = Logger.getLogger(RefrescaVariablesServiceImpl.class);
	
	@Autowired
	private GenPropertiesMapper genPropertiesMapper;
	
	@Override
	public void iniciaTodo() {
		initLog4j(GEN_PROPERTIES_FICHERO.eCOM2_LOG4J);		
	}

	@Override
	public void initLog4j(GEN_PROPERTIES_FICHERO fichero) {
		LOGGER.info("Refrescando parámetros de LOG4J del fichero: " + fichero.name());
		BasicConfigurator.resetConfiguration();
		Properties properties = new Properties();
		GenPropertiesExample genPropertiesExample = new GenPropertiesExample();
		genPropertiesExample.createCriteria().andFicheroEqualTo(fichero.name());
		List<GenProperties> listProperties = genPropertiesMapper.selectByExample(genPropertiesExample);
		if (listProperties != null) {
			for (GenProperties genProperties : listProperties) {
				properties.put(genProperties.getParametro(), genProperties.getValor());
				LOGGER.info(genProperties.getParametro() + " = " + genProperties.getValor());
			}
		}
		PropertyConfigurator.configure(properties);
		LOGGER.info("LOG4 se ha refrescado correctamente");		
	}

	


}
