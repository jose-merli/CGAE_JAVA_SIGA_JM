package org.itcgae.siga.cen.services.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.cen.services.ISolModifDatosUseFotoDetailService;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenClienteKey;
import org.itcgae.siga.db.entities.CenSolicmodifexportarfoto;
import org.itcgae.siga.db.services.cen.mappers.CenClienteExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicmodifexportarfotoExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolModifDatosUseFotoDetailServiceImpl implements ISolModifDatosUseFotoDetailService {

	private Logger LOGGER = Logger.getLogger(SolModifDatosUseFotoDetailServiceImpl.class);

	@Autowired
	private CenClienteExtendsMapper cenClienteExtendsMapper;

	@Autowired
	private CenSolicmodifexportarfotoExtendsMapper  cenSolicModifExportarFotoExtendsMapper;

	@Override
	public StringDTO searchDatosUseFotoDetail(int numPagina, SolModificacionItem solModificacionItem,
			HttpServletRequest request) {

		LOGGER.info("searchDatosUseFotoDetail() -> Entrada al servicio para obtener los datos de la foto");

		StringDTO rdo = new StringDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		LOGGER.info(
				"searchDatosUseFotoDetail() / cenClienteExtendsMapper.selectByPrimaryKey() -> Entrada a cenClienteExtendsMapper para obtener parámetro exportar foto");

		CenClienteKey cenClienteKey = new CenClienteKey();
		cenClienteKey.setIdinstitucion(idInstitucion);
		cenClienteKey.setIdpersona(Long.valueOf(solModificacionItem.getIdPersona()));
		CenCliente cenCliente = cenClienteExtendsMapper.selectByPrimaryKey(cenClienteKey);
		LOGGER.info(
				"searchDatosUseFotoDetail() / cenClienteExtendsMapper.selectByPrimaryKey() -> Salida de cenClienteExtendsMapper para obtener parámetro exportar foto");

		if (cenCliente != null) {

			rdo.setValor(cenCliente.getExportarfoto());
		}

		LOGGER.info("searchDatosUseFotoDetail() -> Salida al servicio para obtener los datos de la foto");

		return rdo;
	}

	@Override
	public StringDTO searchSolModifDatosUseFotoDetail(int numPagina, SolModificacionItem solModificacionItem,
			HttpServletRequest request) {

		LOGGER.info("searchSolModifDatosUseFotoDetail() -> Entrada al servicio para obtener los datos de la foto");

		StringDTO rdo = new StringDTO();

		LOGGER.info(
				"searchSolModifDatosUseFotoDetail() / cenSolicModifExportarFotoExtendsMapper.selectByPrimaryKey() -> Entrada a cenClienteExtendsMapper para obtener parámetro exportar foto");

		CenSolicmodifexportarfoto cenSolicmodifexportarfoto = cenSolicModifExportarFotoExtendsMapper
				.selectByPrimaryKey(Short.valueOf(solModificacionItem.getIdSolicitud()));

		LOGGER.info(
				"searchSolModifDatosUseFotoDetail() / cenSolicModifExportarFotoExtendsMapper.selectByPrimaryKey() -> Salida de cenClienteExtendsMapper para obtener parámetro exportar foto");

		if (cenSolicmodifexportarfoto != null) {
			rdo.setValor(cenSolicmodifexportarfoto.getExportarfoto());
		}

		LOGGER.info("searchSolModifDatosUseFotoDetail() -> Salida al servicio para obtener los datos de la foto");
		return rdo;
	}

}
