package org.itcgae.siga.cen.services.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionDTO;
import org.itcgae.siga.DTOs.cen.SolModificacionItem;
import org.itcgae.siga.DTOs.cen.SolicitudModificacionSearchDTO;
import org.itcgae.siga.cen.services.ISearchSolModifDatosBancariosService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenCuentasbancarias;
import org.itcgae.siga.db.entities.CenSolicitmodifdatosbasicos;
import org.itcgae.siga.db.entities.CenSolicitmodifdatosbasicosExample;
import org.itcgae.siga.db.entities.CenSolicmodicuentas;
import org.itcgae.siga.db.entities.CenSolicmodicuentasExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenCuentasbancariasExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicmodicuentasExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchSolModifDatosBancariosServiceImpl implements ISearchSolModifDatosBancariosService{

	private Logger LOGGER = Logger.getLogger(SearchSolModifDatosBancariosServiceImpl.class);

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenSolicmodicuentasExtendsMapper cenSolicModiCuentasExtendsMapper;
	
	@Autowired
	private CenCuentasbancariasExtendsMapper cenCuentasbancariasExtendsMapper;
	
	@Override
	public SolModificacionDTO searchSolModifDatosBancarios(int numPagina,
			SolicitudModificacionSearchDTO solicitudModificacionSearchDTO, HttpServletRequest request) {
		LOGGER.info(
				"searchSolModifDatosBancarios() -> Entrada al servicio para recuperar los datos de la búsqueda específica de datos bancarios");

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		SolModificacionDTO solModificacionDTO = new SolModificacionDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchSolModifDatosBancarios() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchSolModifDatosBancarios() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"searchSolModifDatosBancarios() / cenSolicModiCuentasExtendsMapper.searchSolModifDatosBancarios() -> Entrada a cenSoliModiDireccionesExtendsMapper para obtener los resultados de la búsqueda");
				List<SolModificacionItem> solModificacionItems = cenSolicModiCuentasExtendsMapper.searchSolModifDatosBancarios(solicitudModificacionSearchDTO, usuario.getIdlenguaje(), String.valueOf(idInstitucion));
				solModificacionDTO.setSolModificacionItems(solModificacionItems);
			}
		}
		LOGGER.info(
				"searchSolModifDatosBancarios() -> Salida del servicio para recuperar los datos de la búsqueda específica de datos bancarios");

		return solModificacionDTO;
	}

	@Override
	public UpdateResponseDTO processSolModifDatosBancarios(
			SolModificacionItem solModificacionItem, HttpServletRequest request) {
		LOGGER.info(
				"processSolModifDatosBancarios() -> Entrada al servicio para actualizar el estado de la solicitud a REALIZADO");
		
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		AdmUsuarios usuario = new AdmUsuarios();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
		LOGGER.info(
				"processSolModifDatosCurriculares() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		if (null != usuarios && usuarios.size() > 0) {
		usuario = usuarios.get(0);

		CenSolicmodicuentas solicitud = new CenSolicmodicuentas();
		CenSolicmodicuentasExample example = new CenSolicmodicuentasExample();
		
		example.createCriteria().andIdpersonaEqualTo(Long.parseLong(solModificacionItem.getIdPersona()))
		.andIdinstitucionEqualTo(idInstitucion).andIdsolicitudEqualTo(Long.valueOf(solModificacionItem.getIdSolicitud()));
		
		List<CenSolicmodicuentas> lista = cenSolicModiCuentasExtendsMapper.selectByExample(example);

		solicitud = lista.get(0);
		
		CenCuentasbancarias modificacion = new CenCuentasbancarias();
		modificacion.setIdinstitucion(idInstitucion);
		modificacion.setIdpersona(solicitud.getIdpersona());
		modificacion.setUsumodificacion(usuario.getIdusuario());
		modificacion.setFechamodificacion(new Date());
		modificacion.setAbonocargo(solicitud.getAbonocargo());
		modificacion.setDigitocontrol(solicitud.getDigitocontrol());
		modificacion.setAbonosjcs(solicitud.getAbonosjcs());
		modificacion.setIban(solicitud.getIban());
		modificacion.setIdcuenta(solicitud.getIdcuenta());
		modificacion.setCboCodigo(solicitud.getCboCodigo());
		modificacion.setCodigosucursal(solicitud.getCodigosucursal());
		modificacion.setNumerocuenta(solicitud.getNumerocuenta());
		modificacion.setTitular(solicitud.getTitular());
		
		int responseUpdate = cenCuentasbancariasExtendsMapper.updateByPrimaryKeySelective(modificacion);
		
		if(responseUpdate >= 1) {
			CenSolicmodicuentas record = new CenSolicmodicuentas();
			record.setIdsolicitud(Long.valueOf(solModificacionItem.getIdSolicitud()));
			record.setIdestadosolic((short) 20);
			int response = cenSolicModiCuentasExtendsMapper.updateByPrimaryKey(record);
			
			if (response == 0) {
				updateResponseDTO.setStatus(SigaConstants.KO);
				LOGGER.warn("processSolModifDatosBancarios() / cenSolicModiCuentasExtendsMapper.updateByPrimaryKey() -> "
						+ updateResponseDTO.getStatus() + " .no se pudo procesar la solicitud");
			} 
			
			updateResponseDTO.setStatus(SigaConstants.OK);
			LOGGER.info(
					"processSolModifDatosBancarios() -> Salida del servicio para actualizar el estado de la solicitud a REALIZADO");
		}else {
			updateResponseDTO.setStatus(SigaConstants.KO);
		}
		
		}else {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("processSolModifDatosGenerales() / No existe el usuario.");
		}
		
		return updateResponseDTO;
		
	}

	@Override
	public UpdateResponseDTO denySolModifDatosBancarios(SolModificacionItem solModificacionItem,
			HttpServletRequest request) {
		LOGGER.info(
				"denySolModifDatosBancarios() -> Entrada al servicio para actualizar el estado de la solicitud a DENEGADO");
		
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		
		CenSolicmodicuentas record = new CenSolicmodicuentas();
		record.setIdsolicitud(Long.valueOf(solModificacionItem.getIdSolicitud()));
		record.setIdestadosolic((short) 30);
		int response = cenSolicModiCuentasExtendsMapper.updateByPrimaryKeySelective(record);
		
		if (response == 0) {
			updateResponseDTO.setStatus(SigaConstants.KO);
			LOGGER.warn("denySolModifDatosBancarios() / cenSolicModiCuentasExtendsMapper.updateByPrimaryKeySelective() -> "
					+ updateResponseDTO.getStatus() + " .no se pudo procesar la solicitud");

		} else {
			updateResponseDTO.setStatus(SigaConstants.OK);
		}
		
		LOGGER.info(
				"denySolModifDatosBancarios() -> Salida del servicio para actualizar el estado de la solicitud a DENEGADO");
		return updateResponseDTO;
	}

}
