package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.scs.BaremosGuardiaDTO;
import org.itcgae.siga.DTO.scs.BaremosGuardiaItem;
import org.itcgae.siga.DTO.scs.BaremosRequestDTO;
import org.itcgae.siga.DTO.scs.BaremosRequestItem;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.Impreso190DTO;
import org.itcgae.siga.DTOs.scs.Impreso190Item;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.DTO.scs.GuardiasItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.FcsFicheroImpreso190;
import org.itcgae.siga.db.entities.FcsFicheroImpreso190Key;
import org.itcgae.siga.db.entities.GenFichero;
import org.itcgae.siga.db.entities.GenFicheroKey;
import org.itcgae.siga.db.entities.ScsBaremosGuardiaKey;
import org.itcgae.siga.db.entities.ScsHitofacturable;
import org.itcgae.siga.db.entities.ScsHitofacturableguardia;
import org.itcgae.siga.db.entities.ScsHitofacturableguardiaExample;
import org.itcgae.siga.db.entities.ScsHitofacturableguardiaKey;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsBaremosGuardiaMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsHitofacturableExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsHitofacturableguardiaExtendsMapper;
import org.itcgae.siga.scs.services.facturacionsjcs.IBaremosGuardiaServices;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BaremosGuardiaServiceImpl implements IBaremosGuardiaServices {

	private Logger LOGGER = Logger.getLogger(BaremosGuardiaServiceImpl.class);

	@Autowired
	private ScsBaremosGuardiaMapper baremosGuardiaMapper;

	@Autowired
	private ScsHitofacturableguardiaExtendsMapper scsHitofacturableguardiaExtendsMapper;

	@Autowired
	private ScsHitofacturableExtendsMapper scsHitofacturableExtendsMapper;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Override
	@Transactional
	public BaremosRequestDTO searchBaremosGuardia(BaremosGuardiaItem baremosGuardiaItem, HttpServletRequest request) {
		BaremosRequestDTO baremosRequestDTO = new BaremosRequestDTO();
		Error error = new Error();

		LOGGER.info("searchBaremosGuardia() -> Entrada del servicio para obtener baremos guardia ");

		String token = request.getHeader("Authorization");
//        String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {
//				ScsBaremosGuardiaKey key = new ScsBaremosGuardiaKey();
//				key.setIdInstitucion(idInstitucion);
//				key.setIdGuardia(idGuardia);
//				key.setIdTurno(idTurno);

			List<BaremosRequestItem> lBaremos = baremosGuardiaMapper.searchBaremosGuardia(baremosGuardiaItem,
					idInstitucion);
			int keyForTabla = 0;
			for (BaremosRequestItem baremo : lBaremos) {
				List<GuardiasItem> guar = new ArrayList<GuardiasItem>();
				GuardiasItem guardiaLaborables = new GuardiasItem();
				GuardiasItem guardiaFestivos = new GuardiasItem();
				String[] dias = baremo.getDias().toString().split("\n");
				guardiaLaborables.setNombre(baremo.getGuardias() + " - LABORABLES");
				guardiaLaborables.setIdGuardia(baremo.getIdGuardia());
				guardiaLaborables.setDiasGuardia(dias[0]);
				guardiaLaborables.setFechabaja(baremo.getFechabaja());

				guardiaFestivos.setNombre(baremo.getGuardias() + " - FESTIVOS");
				guardiaFestivos.setIdGuardia(baremo.getIdGuardia());
				guardiaFestivos.setDiasGuardia(dias[1]);
				guardiaFestivos.setFechabaja(baremo.getFechabaja());

				guar.add(guardiaLaborables);
				guar.add(guardiaFestivos);
				baremo.setGuardiasObj(guar);
				/*
				 * Damos un Key a cada registro para poder crear el desplegable de cada fila en
				 * la tabla. Este valor no se almacena en BBDD. Solo se utiliza para cuando se
				 * haga click se despliegue las guardias correspondientes. Antes se utilizaba el
				 * IdTurno pero este puede ser el mismo para varias guardias con distintos
				 * Baremos. De esta manera se evita que se despliegue otro registros.
				 */
				baremo.setKeyForTabla(keyForTabla++);
			}
			error.setCode(200);
			baremosRequestDTO.setBaremosRequestItems(lBaremos);

		} else {
			LOGGER.warn("searchBaremosGuardia() -> idInstitucion del token nula");
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		baremosRequestDTO.setError(error);

		LOGGER.info("searchBaremosGuardia() -> Salida del servicio para obtener baremos guardia");

		return baremosRequestDTO;
	}

	@Override
	@Transactional
	public BaremosGuardiaDTO getGuardiasByConf(HttpServletRequest request) {
		BaremosGuardiaDTO baremosGuardiaDTO = new BaremosGuardiaDTO();
		Error error = new Error();

		LOGGER.info("searchBaremosGuardia() -> Entrada del servicio para obtener baremos guardia ");

		String token = request.getHeader("Authorization");
//        String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			List<BaremosGuardiaItem> guardias = scsHitofacturableguardiaExtendsMapper
					.getGuardiasByConf(idInstitucion.toString());

			if (guardias.isEmpty()) {
				error.setCode(400);
				error.description("general.message.error.realiza.accion");
			} else {
				error.setCode(200);
				baremosGuardiaDTO.setBaremosGuardiaItems(guardias);
			}

		} else {
			LOGGER.warn("searchBaremosGuardia() -> idInstitucion del token nula");
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		baremosGuardiaDTO.setError(error);

		LOGGER.info("searchBaremosGuardia() -> Salida del servicio para obtener baremos guardia");

		return baremosGuardiaDTO;
	}

	@Override
	public ComboDTO getTurnoForGuardia(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		Error error = new Error();
		ComboDTO comboDTO = new ComboDTO();
		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			if (usuarios != null && usuarios.size() > 0) {

				List<ComboItem> comboItems = scsHitofacturableguardiaExtendsMapper
						.getTurnoForGuardia(idInstitucion.toString());
				error.setCode(200);
				comboDTO.setError(error);
				comboDTO.setCombooItems(comboItems);
			}
		}
		return comboDTO;
	}

	@Override
	@Transactional
	public BaremosGuardiaDTO saveBaremo(List<BaremosGuardiaItem> baremosGuardiaItem, HttpServletRequest request) {
		Error error = new Error();
		BaremosGuardiaDTO baremosGuardiaDTO = new BaremosGuardiaDTO();
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		int response = 0;

		if (idInstitucion != null) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"BaremosGuardiaServiceImpl.insertBaremo() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"BaremosGuardiaServiceImpl.insertBaremo() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {

				if(!baremosGuardiaItem.isEmpty()) {
					ScsHitofacturableguardiaExample hfe = new ScsHitofacturableguardiaExample();
					hfe.createCriteria().andIdinstitucionEqualTo(idInstitucion)
							.andIdturnoEqualTo(Integer.parseInt(baremosGuardiaItem.get(0).getIdTurno()))
							.andIdguardiaEqualTo(Integer.parseInt(baremosGuardiaItem.get(0).getIdGuardia()));
	
					scsHitofacturableguardiaExtendsMapper.deleteByExample(hfe);
	
					for (BaremosGuardiaItem baremo : baremosGuardiaItem) {
	
						ScsHitofacturableguardia insertHito = new ScsHitofacturableguardia();
	
						insertHito.setIdhito(Long.parseLong(baremo.getIdHito()));
						insertHito.setIdinstitucion(idInstitucion);
						insertHito.setIdguardia(Integer.parseInt(baremo.getIdGuardia()));
						insertHito.setIdturno(Integer.parseInt(baremo.getIdTurno()));
						insertHito.setPreciohito(new BigDecimal(baremo.getPrecioHito()));
						insertHito.setAgrupar(baremo.getAgrupar());
						insertHito.setDiasaplicables(baremo.getDias());
						insertHito.setFechamodificacion(new Date());
						insertHito.setUsumodificacion(usuarios.get(0).getIdusuario());
	
						response = scsHitofacturableguardiaExtendsMapper.insertSelective(insertHito);
	
					}
	
					if (response != 0) {
						error.setCode(200);
						error.setDescription("facturacionSJCS.baremos.baremoIsertado");
						baremosGuardiaDTO.setError(error);
					} else {
						error.setCode(400);
						error.setDescription("general.message.error.realiza.accion");
						baremosGuardiaDTO.setError(error);
					}
				}else {
					error.setCode(500);
					error.setDescription("general.message.error.realiza.accion");
					baremosGuardiaDTO.setError(error);
				}
			}
		}

		return baremosGuardiaDTO;
	}

	@Override
	public BaremosGuardiaDTO getBaremo(BaremosGuardiaItem baremosGuardiaItem, HttpServletRequest request) {
		BaremosGuardiaDTO baremosGuardiaDTO = new BaremosGuardiaDTO();
		Error error = new Error();
		List<BaremosGuardiaItem> listHitos = new ArrayList<BaremosGuardiaItem>();
		LOGGER.info("searchBaremosGuardia() -> Entrada del servicio para obtener baremos guardia ");

		String token = request.getHeader("Authorization");
//        String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			ScsHitofacturableguardiaExample hfg = new ScsHitofacturableguardiaExample();
			hfg.createCriteria().andIdinstitucionEqualTo(idInstitucion)
			.andIdturnoEqualTo(Integer.parseInt(baremosGuardiaItem.getIdTurno()))
			.andIdguardiaEqualTo(Integer.parseInt(baremosGuardiaItem.getIdGuardia()));
			
			List<ScsHitofacturableguardia> hitos = scsHitofacturableguardiaExtendsMapper.selectByExample(hfg);
			
			for(ScsHitofacturableguardia hito:hitos) {
				BaremosGuardiaItem hitoGuardia = new BaremosGuardiaItem();
				//para obtener los hitos del baremo de la guardia y el importe, ya se obtiene los dias aplicar y el agrupar.
				hitoGuardia.setIdHito(hito.getIdhito().toString());
				hitoGuardia.setPrecioHito(hito.getPreciohito().toString());
				hitoGuardia.setDias(hito.getDiasaplicables());
				hitoGuardia.setAgrupar((hito.getAgrupar()));
				listHitos.add(hitoGuardia);
				
			}

			if (listHitos.isEmpty()) {
				error.setCode(400);
				error.description("general.message.error.realiza.accion");
			} else {
				error.setCode(200);
				baremosGuardiaDTO.setBaremosGuardiaItems(listHitos);
			}

		} else {
			LOGGER.warn("searchBaremosGuardia() -> idInstitucion del token nula");
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		baremosGuardiaDTO.setError(error);

		LOGGER.info("searchBaremosGuardia() -> Salida del servicio para obtener baremos guardia");

		return baremosGuardiaDTO;
	}

}