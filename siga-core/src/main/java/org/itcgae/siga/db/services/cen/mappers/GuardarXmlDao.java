package org.itcgae.siga.db.services.cen.mappers;

import org.itcgae.siga.db.entities.CmnDatosXml;
import org.itcgae.siga.exception.BusinessException;

public interface GuardarXmlDao {

	public Long insertaXML(CmnDatosXml datos) throws BusinessException;
}
