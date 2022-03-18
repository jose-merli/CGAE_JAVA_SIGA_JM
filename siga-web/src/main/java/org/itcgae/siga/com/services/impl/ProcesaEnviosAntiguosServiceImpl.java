package org.itcgae.siga.com.services.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.com.EnviosMasivosItem;
import org.itcgae.siga.com.services.IProcesaEnviosAntiguosService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.EnvEnvios;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.itcgae.siga.db.mappers.EnvEnviosMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.com.mappers.EnvEnviosExtendsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcesaEnviosAntiguosServiceImpl implements IProcesaEnviosAntiguosService {

	private Logger LOGGER = Logger.getLogger(ProcesaEnviosAntiguosServiceImpl.class);
	
	@Autowired
	private EnvEnviosExtendsMapper _envEnviosExtendsMapper;
	
	@Autowired
	private EnvEnviosMapper _envEnviosMapper;
	
	@Autowired
	private GenPropertiesMapper _genPropertiesMapper;
	
	@Override
	public void compruebaEnviosAntiguos() {

		EnvEnvios envio = null;
		
		GenPropertiesKey key = new GenPropertiesKey();
		key.setFichero(SigaConstants.FICHERO_SIGA);
		key.setParametro(SigaConstants.minutosRevisionEnviosProcesando);
		
		// Obtenemos los minutos que han debido pasar para los envíos en estado procesando
		GenProperties mins = _genPropertiesMapper.selectByPrimaryKey(key);
		
		Short minutos = Short.valueOf(mins.getValor().toString());
		
		List<EnviosMasivosItem> enviosIrrecuperables = _envEnviosExtendsMapper.obtenerEnviosIrrecuperables(minutos);
		if (enviosIrrecuperables != null && enviosIrrecuperables.size() > 0) {
			for (EnviosMasivosItem envioBasura : enviosIrrecuperables) {
				envio = new EnvEnvios();
				LOGGER.info("Listener envios => Se ha encontrado envio irrecuperable y se procederá a su cambio de estado a Pendiente automático con ID: " + envioBasura.getIdEnvio());
				envio.setIdinstitucion(envioBasura.getIdInstitucion());
				envio.setIdenvio(envioBasura.getIdEnvio());
				envio.setIdestado(Short.valueOf(SigaConstants.ENVIO_PENDIENTE_AUTOMATICO.toString()));
				_envEnviosMapper.updateByPrimaryKeySelective(envio);
			}
		}

	}

}
