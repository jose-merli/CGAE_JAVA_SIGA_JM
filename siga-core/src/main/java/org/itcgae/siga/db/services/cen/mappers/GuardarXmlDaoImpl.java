package org.itcgae.siga.db.services.cen.mappers;


import org.itcgae.siga.db.entities.CmnDatosXml;
import org.itcgae.siga.db.mappers.CmnDatosXmlMapper;
import org.itcgae.siga.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuardarXmlDaoImpl implements GuardarXmlDao {

	private final Logger log =  LoggerFactory.getLogger(GuardarXmlDaoImpl.class); 
	 
	@Autowired
	CmnDatosXmlMapper cmnDatosXmlMapper;

	@Override
	public Long insertaXML(CmnDatosXml datos) throws BusinessException{

		if (datos.getXml() != null) {

			int resInsert = cmnDatosXmlMapper.insert(datos);

			if (resInsert != 1) {
				throw new BusinessException("No se ha insertado correctamente el xml " + datos.getXml());
			}

		}

		log.info("Se ha insertado" + datos.getIdDatosXml());

		return datos.getIdDatosXml();
	}

}
