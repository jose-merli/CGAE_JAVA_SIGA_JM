package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.NoColegiadoDTO;
import org.itcgae.siga.DTOs.cen.NoColegiadoItem;
import org.itcgae.siga.cen.services.IBusquedaNoColegiadosService;
import org.itcgae.siga.db.services.cen.mappers.CenNocolegiadoExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusquedaNoColegiadosServiceImpl implements IBusquedaNoColegiadosService {

	private Logger LOGGER = Logger.getLogger(BusquedaNoColegiadosServiceImpl.class);

	@Autowired
	private CenNocolegiadoExtendsMapper cenNocolegiadoExtendsMapper;

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
}
