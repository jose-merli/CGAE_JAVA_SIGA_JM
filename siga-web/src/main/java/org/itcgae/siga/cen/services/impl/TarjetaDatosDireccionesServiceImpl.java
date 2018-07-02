package org.itcgae.siga.cen.services.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.InsertResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.BancoBicDTO;
import org.itcgae.siga.DTOs.cen.BancoBicItem;
import org.itcgae.siga.DTOs.cen.DatosBancariosAnexoDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosAnexoItem;
import org.itcgae.siga.DTOs.cen.DatosBancariosDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosDeleteDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosInsertDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosItem;
import org.itcgae.siga.DTOs.cen.DatosBancariosSearchAnexosDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosSearchBancoDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosSearchDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesDTO;
import org.itcgae.siga.DTOs.cen.DatosDireccionesItem;
import org.itcgae.siga.DTOs.cen.DatosDireccionesSearchDTO;
import org.itcgae.siga.DTOs.cen.MandatosDTO;
import org.itcgae.siga.DTOs.cen.MandatosDownloadDTO;
import org.itcgae.siga.DTOs.cen.MandatosItem;
import org.itcgae.siga.DTOs.cen.MandatosUpdateDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.gen.NewIdDTO;
import org.itcgae.siga.cen.services.ITarjetaDatosBancariosService;
import org.itcgae.siga.cen.services.ITarjetaDatosDireccionesService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmConfig;
import org.itcgae.siga.db.entities.AdmConfigExample;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenAnexosCuentasbancarias;
import org.itcgae.siga.db.entities.CenAnexosCuentasbancariasExample;
import org.itcgae.siga.db.entities.CenAnexosCuentasbancariasKey;
import org.itcgae.siga.db.entities.CenCuentasbancarias;
import org.itcgae.siga.db.entities.CenCuentasbancariasExample;
import org.itcgae.siga.db.entities.CenCuentasbancariasKey;
import org.itcgae.siga.db.entities.CenMandatosCuentasbancarias;
import org.itcgae.siga.db.entities.CenMandatosCuentasbancariasExample;
import org.itcgae.siga.db.entities.CenMandatosCuentasbancariasKey;
import org.itcgae.siga.db.entities.GenFichero;
import org.itcgae.siga.db.entities.GenFicheroKey;
import org.itcgae.siga.db.entities.GenRecursos;
import org.itcgae.siga.db.entities.GenRecursosExample;
import org.itcgae.siga.db.mappers.AdmConfigMapper;
import org.itcgae.siga.db.mappers.CenAnexosCuentasbancariasMapper;
import org.itcgae.siga.db.mappers.CenMandatosCuentasbancariasMapper;
import org.itcgae.siga.db.mappers.GenRecursosMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenCuentasbancariasExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenDireccionesExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.GenFicheroExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


@Service
public class TarjetaDatosDireccionesServiceImpl implements ITarjetaDatosDireccionesService{

	private Logger LOGGER = Logger.getLogger(TarjetaDatosDireccionesServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	

	
	@Autowired
	private CenDireccionesExtendsMapper cenDireccionesExtendsMapper;
	@Override
	public DatosDireccionesDTO datosDireccionesSearch(int numPagina, DatosDireccionesSearchDTO datosDireccionesSearchDTO,	HttpServletRequest request) {
		LOGGER.info("searchBanksData() -> Entrada al servicio para la búsqueda por filtros de direcciones");
		
		List<DatosDireccionesItem> datosDireccionesItem = new ArrayList<DatosDireccionesItem>();
		DatosDireccionesDTO datosDireccionesDTO = new DatosDireccionesDTO();

		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"datosDireccionesSearch() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"datosDireccionesSearch() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				LOGGER.info(
						"datosDireccionesSearch() / cenDireccionesExtendsMapper.selectDirecciones() -> Entrada a cenCuentasbancariasExtendsMapper para busqueda de direcciones");
				datosDireccionesItem = cenDireccionesExtendsMapper.selectDirecciones(datosDireccionesSearchDTO, idInstitucion.toString());
				LOGGER.info(
						"datosDireccionesSearch() / cenDireccionesExtendsMapper.selectDirecciones() -> Salida de cenCuentasbancariasExtendsMapper para busqueda de direcciones");

				datosDireccionesDTO.setDatosDireccionesItem(datosDireccionesItem);;
			} 
			else {
				LOGGER.warn("datosDireccionesSearch() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		} 
		else {
			LOGGER.warn("datosDireccionesSearch() -> idInstitucion del token nula");
		}
		
		LOGGER.info("datosDireccionesSearch() -> Salida del servicio para la búsqueda por filtros de direcciones");
		return datosDireccionesDTO;
	}











	





}
