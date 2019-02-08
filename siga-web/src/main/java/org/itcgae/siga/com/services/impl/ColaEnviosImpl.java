package org.itcgae.siga.com.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.itcgae.siga.DTOs.com.DestinatarioItem;
import org.itcgae.siga.com.services.IEnviosService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.entities.CenDireccionesExample;
import org.itcgae.siga.db.entities.CenDireccionesKey;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.CenPersonaExample;
import org.itcgae.siga.db.entities.ConConsulta;
import org.itcgae.siga.db.entities.ConConsultaExample;
import org.itcgae.siga.db.entities.EnvConsultasenvio;
import org.itcgae.siga.db.entities.EnvConsultasenvioExample;
import org.itcgae.siga.db.entities.EnvEnvioprogramado;
import org.itcgae.siga.db.entities.EnvEnvioprogramadoExample;
import org.itcgae.siga.db.entities.EnvEnvios;
import org.itcgae.siga.db.entities.EnvEnviosExample;
import org.itcgae.siga.db.entities.EnvPlantillasenvios;
import org.itcgae.siga.db.entities.EnvPlantillasenviosKey;
import org.itcgae.siga.db.entities.EnvPlantillasenviosWithBLOBs;
import org.itcgae.siga.db.entities.ModPlantillaenvioConsulta;
import org.itcgae.siga.db.entities.ModPlantillaenvioConsultaExample;
import org.itcgae.siga.db.mappers.CenDireccionesMapper;
import org.itcgae.siga.db.mappers.CenPersonaMapper;
import org.itcgae.siga.db.mappers.ConConsultaMapper;
import org.itcgae.siga.db.mappers.EnvConsultasenvioMapper;
import org.itcgae.siga.db.mappers.EnvEnvioprogramadoMapper;
import org.itcgae.siga.db.mappers.EnvEnviosMapper;
import org.itcgae.siga.db.mappers.EnvPlantillasenviosMapper;
import org.itcgae.siga.db.mappers.ModPlantillaenvioConsultaMapper;
import org.itcgae.siga.db.services.com.mappers.ConConsultasExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvPlantillaEnviosExtendsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

public class ColaEnviosImpl implements IColaEnvios{

	
	@Autowired
	private EnvEnvioprogramadoMapper _envEnvioprogramadoMapper;
	
	@Autowired
	private EnvEnviosMapper _envEnviosMapper;
	
	@Autowired
	private EnvPlantillasenviosMapper _envPlantillasenviosMapper;
	
	@Autowired
	private CenDireccionesMapper _cenDireccionesMapper;
	
	@Autowired
	private IEnviosService _enviosService;
	
	@Autowired
	private EnvConsultasenvioMapper _envConsultasenvioMapper;
	
	@Autowired
	private ModPlantillaenvioConsultaMapper _modPlantillaenvioConsultaMapper;
	
	@Autowired
	private ConConsultaMapper _conConsultaMapper;
	
	@Autowired
	private ConConsultasExtendsMapper _conConsultasExtendsMapper;
	
	@Autowired
	private CenPersonaMapper _cenPersonaMapper;
	
	@Transactional
	@Scheduled(cron  = "${cron.pattern.scheduled.Envios}") 
	@Override
	public void execute() {

		EnvEnvioprogramadoExample example = new EnvEnvioprogramadoExample();
		example.createCriteria().andFechaprogramadaLessThanOrEqualTo(new Date());
		List<EnvEnvioprogramado> enviosProgramados = _envEnvioprogramadoMapper.selectByExample(example);
		
		if(enviosProgramados != null && enviosProgramados.size() > 0){
			
			for (EnvEnvioprogramado envEnvioprogramado : enviosProgramados) {
				EnvEnviosExample exampleEnvio = new EnvEnviosExample();
				Short estadoPendienteAutomatico = 4;
				exampleEnvio.createCriteria().andIdenvioEqualTo(envEnvioprogramado.getIdenvio()).andIdestadoEqualTo(estadoPendienteAutomatico);
				List<EnvEnvios> envioSelected = _envEnviosMapper.selectByExample(exampleEnvio);
				if(envioSelected != null && envioSelected.size() > 0){
					EnvEnvios envio = envioSelected.get(0);
					String idSolicitudEcos;
					switch (envio.getIdtipoenvios().toString()) {
					
					case SigaConstants.TIPO_ENVIO_CORREOELECTRONICO:
						_enviosService.envioMail(envio);
						break;
					case SigaConstants.TIPO_ENVIO_CORREO_ORDINARIO:
						_enviosService.envioCorreoOrdinario();
						break;
					case SigaConstants.TIPO_ENVIO_SMS:
						idSolicitudEcos = preparaEnvioSMS(envio, false);
						break;
					case SigaConstants.TIPO_ENVIO_BUROSMS:
						idSolicitudEcos = preparaEnvioSMS(envio, true);
						break;
					}
				}
			}
			
		}
		
	}
	
	private String preparaEnvioSMS(EnvEnvios envio, boolean isBuroSMS){
		
		//Obtenemos la plantilla de envio
		EnvPlantillasenviosKey keyPlantilla = new EnvPlantillasenviosKey();
		keyPlantilla.setIdinstitucion(envio.getIdinstitucion());
		keyPlantilla.setIdplantillaenvios(envio.getIdplantillaenvios());
		keyPlantilla.setIdtipoenvios(envio.getIdtipoenvios());
		EnvPlantillasenviosWithBLOBs plantilla = _envPlantillasenviosMapper.selectByPrimaryKey(keyPlantilla);
		
		//obtenemos la direccion del remitente de la plantilla
		CenDireccionesKey keyDireccion = new CenDireccionesKey();
		keyDireccion.setIddireccion(plantilla.getIddireccion());
		keyDireccion.setIdpersona(plantilla.getIdpersona());
		keyDireccion.setIdinstitucion(envio.getIdinstitucion());
		CenDirecciones remitente = _cenDireccionesMapper.selectByPrimaryKey(keyDireccion);
		
		//obtenemos las consultas de destinatarios asociadas a la plantilla de envio
		ModPlantillaenvioConsultaExample consultaPlantillaExample = new ModPlantillaenvioConsultaExample();
		consultaPlantillaExample.createCriteria().andIdplantillaenviosEqualTo(plantilla.getIdplantillaenvios()).andIdtipoenviosEqualTo(plantilla.getIdtipoenvios());
		List<ModPlantillaenvioConsulta> consultasAsociadas = _modPlantillaenvioConsultaMapper.selectByExample(consultaPlantillaExample);
		
	
		List<DestinatarioItem> destinatarios = new ArrayList<DestinatarioItem>();

		
		for (int i = 0; i < consultasAsociadas.size();i++) {
			ConConsultaExample consultaExample = new ConConsultaExample();
			consultaExample.createCriteria().andIdconsultaEqualTo(consultasAsociadas.get(i).getIdconsulta()).andIdinstitucionEqualTo(consultasAsociadas.get(i).getIdinstitucion())
			.andIdobjetivoEqualTo(Long.valueOf(SigaConstants.ID_OBJETIVO_DESTINATARIOS));
			List<ConConsulta> consultaSelected = _conConsultaMapper.selectByExampleWithBLOBs(consultaExample);
			
			if(consultaSelected != null && consultaSelected.size() > 0){
				ConConsulta consulta = consultaSelected.get(0);
				int inicioSelect = consulta.getSentencia().indexOf("<SELECT>")+8;
				int finSelect = consulta.getSentencia().indexOf("</SELECT>");
				String selectConEtiquetas = consulta.getSentencia().substring(inicioSelect, finSelect);
				String aliasIdPersona = obtenerAliasIdPersona(selectConEtiquetas);
				String aliasIdInstitucion = obtenerAliasIdInstitucion(selectConEtiquetas);
				String aliasCorreo = obtenerAliasCorreoElectronico(selectConEtiquetas);
				String aliasMovil = obtenerAliasMovil(selectConEtiquetas);
				
				String query = obtenerCamposConsulta(consulta.getSentencia());
				List<Map<String, Object>> result = _conConsultasExtendsMapper.ejecutarConsultaString(query);
				
				//Recorremos todos los destinatarios de los resultados de la consulta.
				for (Map<String, Object> map : result) {
					destinatarios.add(obtenerDestinatario(map, aliasIdPersona, aliasIdInstitucion, aliasCorreo, aliasMovil));
				}
			}
		}
		String[] numerosDestinatarios = new String[destinatarios.size()];
		
		//Obtenemos los numeros de los destinatarios.
		for (int i = 0; i < numerosDestinatarios.length; i++) {
			numerosDestinatarios[i] = destinatarios.get(i).getMovil();
		}
		//Realizamos el envio por SMS
		String idSolicitudEcos = _enviosService.envioSMS(remitente, numerosDestinatarios, envio.getIdinstitucion(), plantilla.getAsunto(), plantilla.getCuerpo(), isBuroSMS);
		
		return idSolicitudEcos;
	}
	
	private String obtenerCamposConsulta(String consulta){
		
		consulta = consulta.replace("<SELECT>", "");
		consulta = consulta.replace("</SELECT>", "");
		consulta = consulta.replace("<FROM>", "");
		consulta = consulta.replace("</FROM>", "");
		consulta = consulta.replace("<WHERE>", "");
		consulta = consulta.replace("</WHERE>", "");
		consulta = consulta.replace("<UNION>", "");
		consulta = consulta.replace("</UNION>", "");
		consulta = consulta.replace("<UNIONALL>", "");
		consulta = consulta.replace("</UNIONALL>", "");
		consulta = consulta.replace("<GROUPBY>", "");
		consulta = consulta.replace("</GROUPBY>", "");
		consulta = consulta.replace("<ORDERBY>", "");
		consulta = consulta.replace("</ORDERBY>", "");
		
		return consulta;
	}
	
	private String obtenerAliasIdInstitucion(String select){
		
		String idInstitucion = "";
		
		select = select.toUpperCase();
		String[] selects = select.split(",");
		
		for (String string : selects) {
			if(string.indexOf("CEN_CLIENTE.IDINSTITUCION AS") > 0){
				int inicio = string.indexOf("CEN_CLIENTE.IDINSTITUCION AS") + string.length();
				idInstitucion = string.substring(inicio);
			}
		}
		
		return idInstitucion;
	}
	
	private String obtenerAliasIdPersona(String select){
		
		String idPersona = "";
		
		select = select.toUpperCase();
		String[] selects = select.split(",");
		
		for (String string : selects) {
			if(string.indexOf("CEN_CLIENTE.IDPERSONA AS") > 0){
				int inicio = string.indexOf("CEN_CLIENTE.IDPERSONA AS") + string.length();
				idPersona = string.substring(inicio);
				idPersona = idPersona.replace("\"", "");
				idPersona = idPersona.replace("\"", "");
			}
		}
		
		return idPersona;
	}
	
	private String obtenerAliasCorreoElectronico(String select){
		
		String correo = "";
		
		select = select.toUpperCase();
		String[] selects = select.split(",");
		
		for (String string : selects) {
			if(string.indexOf("CEN_DIRECCIONES.CORREOELECTRONICO AS") > 0){
				int inicio = string.indexOf("CEN_DIRECCIONES.CORREOELECTRONICO AS")+ string.length();
				correo = string.substring(inicio);
				correo = correo.replace("\"", "");
				correo = correo.replace("\"", "");
			}
		}
		
		return correo;
	}
	
	private String obtenerAliasMovil(String select){
		
		String correo = "";
		
		select = select.toUpperCase();
		String[] selects = select.split(",");
		
		for (String string : selects) {
			if(select.indexOf("CEN_DIRECCIONES.MOVIL AS") > 0){
				int inicio = string.indexOf("CEN_DIRECCIONES.CORREOELECTRONICO AS")+ string.length();
				correo = string.substring(inicio);
				correo = correo.replace("\"", "");
				correo = correo.replace("\"", "");
			}
		}
		
		return correo;
	}
	
	private DestinatarioItem obtenerDestinatario(Map<String, Object> registro, String idPersona, String idInstitucion,String correoElectronico, String movil){
		
		DestinatarioItem destinatario = new DestinatarioItem();
		
		//Recojemos los campos
		Object campoIdPersona = registro.get(idPersona.trim());
		Object campoidInstitucion = registro.get(idInstitucion.trim());
		Object campoCorreo = registro.get(correoElectronico.trim());
		
		CenPersona persona = _cenPersonaMapper.selectByPrimaryKey(Long.valueOf(campoIdPersona.toString()));
		destinatario.setNombre(persona.getNombre());
		destinatario.setApellidos1(persona.getApellidos1());
		destinatario.setApellidos2(persona.getApellidos2());
		destinatario.setCorreoElectronico(campoCorreo.toString());
		destinatario.setMovil(movil);
		
		return destinatario;
		
	}

}
