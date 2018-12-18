package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.cen.NoColegiadoDTO;
import org.itcgae.siga.DTOs.cen.NoColegiadoItem;
import org.itcgae.siga.cen.services.IBusquedaNoColegiadosService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCuentasbancarias;
import org.itcgae.siga.db.entities.CenCuentasbancariasExample;
import org.itcgae.siga.db.entities.CenNocolegiado;
import org.itcgae.siga.db.entities.CenNocolegiadoExample;
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

	@Override
	public DeleteResponseDTO deleteNoColegiado(NoColegiadoItem noColegiadoItem, HttpServletRequest request) {
		LOGGER.info("deleteNoColegiado() -> Entrada al servicio para eliminar personas no colegiadas");
		int response = 0;
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"deleteNoColegiado() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"deleteNoColegiado() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				
				// información a modificar
				CenNocolegiado noColegiadoDelete = new CenNocolegiado();
				noColegiadoDelete.setFechaBaja(new Date());
				noColegiadoDelete.setFechamodificacion(new Date());
				noColegiadoDelete.setUsumodificacion(usuario.getIdusuario());
				
				// filtrado para sentencia sql
				List <Long> idPersonasDelete = new ArrayList<Long>();
				for(int i=0; i<noColegiadoItem.getIdPersonas().length; i++) {
					idPersonasDelete.add(Long.valueOf(noColegiadoItem.getIdPersonas()[i]));
				}
				
				CenNocolegiadoExample noColegiadoFiltroDelete = new CenNocolegiadoExample();
				noColegiadoFiltroDelete.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpersonaIn(idPersonasDelete);
				LOGGER.info(
						"deleteNoColegiado() / cenNocolegiadoExtendsMapper.updateByExampleSelective() -> Entrada a cenNocolegiadoExtendsMapper para eliminar no colegiado");
				response = cenNocolegiadoExtendsMapper.updateByExampleSelective(noColegiadoDelete, noColegiadoFiltroDelete);
				LOGGER.info(
						"deleteNoColegiado() / cenNocolegiadoExtendsMapper.updateByExampleSelective() -> Salida de cenNocolegiadoExtendsMapper para eliminar no colegiado");

			} else {
				LOGGER.warn(
						"deleteNoColegiado() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("deleteNoColegiado() -> idInstitucion del token nula");
		}
		
		// comprobacion actualización
		if(response >= 1) {
			LOGGER.info("deleteNoColegiado() -> OK. Delete para no colegiado realizado correctamente");
			deleteResponseDTO.setStatus(SigaConstants.OK);
		}
		else {
			LOGGER.info("deleteNoColegiado() -> KO. Delete para no colegiado NO realizado correctamente");
			deleteResponseDTO.setStatus(SigaConstants.KO);
		}
		
		LOGGER.info("deleteNoColegiado() -> Salida del servicio para eliminar personas no colegiadas");
		return deleteResponseDTO;
	}
}
