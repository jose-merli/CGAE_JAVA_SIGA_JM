package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.IFichaColegialRegTelService;
import org.itcgae.siga.cen.services.IFichaDatosColegialesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenColegiado;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposseguroExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTratamientoExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.itcgae.siga.services.impl.DocushareHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FichaColegialRegTelServiceImpl implements IFichaColegialRegTelService{

	private Logger LOGGER = Logger.getLogger(FichaColegialRegTelServiceImpl.class);
	
	@Autowired
	private DocushareHelper docushareHelper;
	
	@Autowired
	private CenTratamientoExtendsMapper cenTratamientoExtendsMapper;
	
	@Autowired
	private CenTiposseguroExtendsMapper cenTiposseguroExtendsMapper;

	@Autowired 
	private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;
	 

	@Override
	public BusquedaJuridicaDTO searchListDoc(int numPagina, String idPersona, HttpServletRequest request) throws Exception {
		docushareHelper.buscaCollectionCenso("");
		return null;
	}	
	
	
}
