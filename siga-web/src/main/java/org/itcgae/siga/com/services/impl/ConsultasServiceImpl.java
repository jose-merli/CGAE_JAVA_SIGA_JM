package org.itcgae.siga.com.services.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.com.ConsultaItem;
import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.ConsultasSearch;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.com.services.IConsultasService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConsultasServiceImpl implements IConsultasService{

	private Logger LOGGER = Logger.getLogger(ConsultasServiceImpl.class);

	@Override
	public ComboDTO modulo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ComboDTO objetivo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ComboDTO claseComunicacion(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConsultasDTO consultasSearch(HttpServletRequest request, ConsultasSearch filtros) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Error duplicarConsulta(HttpServletRequest request, ConsultaItem consultaItem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Error borrarConsulta(HttpServletRequest request, ConsultaItem consultaItem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Error guardarDatosGenerales(HttpServletRequest request, ConsultasSearch filtros) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Error obtenerModelosComunicacion(HttpServletRequest request, ConsultasSearch filtros) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Error obtenerPlantillasEnvio(HttpServletRequest request, ConsultasSearch filtros) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Error guardarConsulta(HttpServletRequest request, ConsultasSearch filtros) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Error ejecutarConsulta(HttpServletRequest request, String consulta) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
