package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaDTO;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaItem;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaSearchDTO;
import org.itcgae.siga.DTOs.cen.NoColegiadoDTO;
import org.itcgae.siga.DTOs.cen.NoColegiadoItem;
import org.itcgae.siga.cen.services.IBusquedaNoColegiadosService;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenNocolegiadoExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusquedaNoColegiadosServiceImpl implements IBusquedaNoColegiadosService {

	private Logger LOGGER = Logger.getLogger(BusquedaNoColegiadosServiceImpl.class);

	@Autowired
	private CenNocolegiadoExtendsMapper cenNocolegiadoExtendsMapper;
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Override
	public NoColegiadoDTO searchNoColegiado(NoColegiadoItem noColegiadoItem, HttpServletRequest request) {

		LOGGER.info("searchNoColegiado() -> Entrada al servicio para obtener no colegiados");

		NoColegiadoDTO noColegiadosDTO = new NoColegiadoDTO();
		List<NoColegiadoItem> noColegiadoItemList = new ArrayList<NoColegiadoItem>();

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		if (null != idInstitucion) {

			noColegiadoItemList = cenNocolegiadoExtendsMapper.selectNoColegiados(idInstitucion, noColegiadoItem);
			noColegiadosDTO.setNoColegiadoItem(noColegiadoItemList);

			if (noColegiadoItemList == null || noColegiadoItemList.size() == 0) {

				LOGGER.warn(
						"searchNoColegiado() / cenNocolegiadoExtendsMapper.searchNoColegiado() -> No existen no colegiados con las condiciones recibidas en la Institucion = "
								+ idInstitucion);
			}

		} else {
			LOGGER.warn("searchNoColegiado() -> idInstitucion del token nula");
		}

		return noColegiadosDTO;
	}
	
	@Override
	public NoColegiadoDTO searchHistoricNoColegiado(int numPagina, NoColegiadoItem noColegiadoItem, HttpServletRequest request) {
		
		LOGGER.info("searchHistoricNoColegiado() -> Entrada al servicio para la búsqueda por filtros de personas no colegiadas");
		
		List<NoColegiadoItem> noColegiadoItemList = new ArrayList<NoColegiadoItem>();
		NoColegiadoDTO noColegiadoDTO = new NoColegiadoDTO();
		String idLenguaje = null;
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {

			LOGGER.info(
						"searchHistoricNoColegiado() / cenNocolegiadoExtendsMapper.searchHistoricNoColegiado() -> Entrada a cenNocolegiadoExtendsMapper para histórico de personas no colegiadas");
			noColegiadoItemList = cenNocolegiadoExtendsMapper.searchHistoricNoColegiado(noColegiadoItem, idLenguaje, String.valueOf(idInstitucion));
				LOGGER.info(
						"searchHistoricNoColegiado() / cenNocolegiadoExtendsMapper.searchHistoricNoColegiado() -> Salida de cenNocolegiadoExtendsMapper para histórico de personas colegiadas");

				noColegiadoDTO.setNoColegiadoItem(noColegiadoItemList);
			
		} 
		else {
			LOGGER.warn("searchHistoricNoColegiado() -> idInstitucion del token nula");
		}
		
		LOGGER.info("searchHistoricNoColegiado() -> Salida del servicio para la búsqueda por filtros de personas no colegiadas");
		return noColegiadoDTO;
	}

}
