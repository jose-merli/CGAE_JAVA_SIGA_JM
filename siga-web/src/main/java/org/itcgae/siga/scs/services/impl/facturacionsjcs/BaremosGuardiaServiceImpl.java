package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTO.scs.BaremosGuardiaDTO;
import org.itcgae.siga.DTO.scs.BaremosGuardiaItem;
import org.itcgae.siga.DTO.scs.BaremosRequestDTO;
import org.itcgae.siga.DTO.scs.BaremosRequestItem;
import org.itcgae.siga.DTOs.cen.StringDTO;
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
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.ScsBaremosGuardiaKey;
import org.itcgae.siga.db.entities.ScsHitofacturable;
import org.itcgae.siga.db.entities.ScsHitofacturableguardia;
import org.itcgae.siga.db.entities.ScsHitofacturableguardiaExample;
import org.itcgae.siga.db.entities.ScsHitofacturableguardiaKey;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsBaremosGuardiaMapper;
import org.itcgae.siga.db.services.scs.mappers.ScsGuardiasturnoExtendsMapper;
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
	private ScsGuardiasturnoExtendsMapper scsGuardiasturnoExtendsMapper;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
    
    @Autowired
    private GenParametrosExtendsMapper genParametrosMapper;

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
			Integer tamMaximo = getTamanoMaximo(idInstitucion);
			List<BaremosRequestItem> lBaremos = baremosGuardiaMapper.searchBaremosGuardia(baremosGuardiaItem,
					idInstitucion,tamMaximo);
			int keyForTabla = 0;
			String hitoActual;
			List<GuardiasItem> guar = new ArrayList<GuardiasItem>();
			List<GuardiasItem> guarAux = new ArrayList<GuardiasItem>();
			int indiceNuevo = 0;
			for(int i = 0; i< lBaremos.size(); i++) {
				hitoActual = lBaremos.get(i).getIdHito();
		//for (BaremosRequestItem baremo : lBaremos) {	
				if(i > 0) {
					if(hitoActual.equals(lBaremos.get(i-1).getIdHito())) {
						//MISMO HITO
						GuardiasItem guardia = new GuardiasItem();
						String[] dias = lBaremos.get(i).getDias().toString().split("\n");
						String[] parts = lBaremos.get(i).getGuardias().split(",");
						lBaremos.get(i).setGuardias(parts[0]);
						guardia.setNombre(lBaremos.get(i).getNomTurno() + "-" + parts[0]);
						guardia.setIdGuardia(lBaremos.get(i).getIdGuardia());
						guardia.setDiasGuardia(dias[0] + dias[1]);
						guardia.setFechabaja(lBaremos.get(i).getFechabaja());
						guardia.setBaremo(lBaremos.get(i).getBaremo());
						guardia.setnDias(lBaremos.get(i).getNDias());
						guardia.setNumMinimoSimple(lBaremos.get(i).getNumMinimoSimple());
						guardia.setSimpleOImporteIndividual(lBaremos.get(i).getSimpleOImporteIndividual());
						guardia.setNaPartir(lBaremos.get(i).getNaPartir());
						guardia.setMaximo(lBaremos.get(i).getMaximo());
						guardia.setDisponibilidad(lBaremos.get(i).getDisponibilidad());
						guardia.setPorDia(lBaremos.get(i).getPorDia());
						guardia.setIdTurno(lBaremos.get(i).getIdTurno());
						//guar.add(guardia);
						//if(i+1 != lBaremos.size()) {
								//&& (!(hitoActual.equals(lBaremos.get(i+1).getIdHito())))  ) {
						guarAux.add(guardia);
						lBaremos.get(indiceNuevo).getGuardiasObj().add(guardia);
						//}
					}else {
						//DIFERENTE HITO
						indiceNuevo = i;
						guar = new ArrayList<GuardiasItem>();
						GuardiasItem guardia = new GuardiasItem();
						String[] dias = lBaremos.get(i).getDias().toString().split("\n");
						String[] parts = lBaremos.get(i).getGuardias().split(",");
						lBaremos.get(i).setGuardias(parts[0]);
						guardia.setNombre(lBaremos.get(i).getNomTurno() + "-" + parts[0]);
						guardia.setIdGuardia(lBaremos.get(i).getIdGuardia());
						guardia.setDiasGuardia(dias[0] + dias[1]);
						guardia.setFechabaja(lBaremos.get(i).getFechabaja());
						guardia.setBaremo(lBaremos.get(i).getBaremo());
						guardia.setnDias(lBaremos.get(i).getNDias());
						guardia.setNumMinimoSimple(lBaremos.get(i).getNumMinimoSimple());
						guardia.setSimpleOImporteIndividual(lBaremos.get(i).getSimpleOImporteIndividual());
						guardia.setNaPartir(lBaremos.get(i).getNaPartir());
						guardia.setMaximo(lBaremos.get(i).getMaximo());
						guardia.setDisponibilidad(lBaremos.get(i).getDisponibilidad());
						guardia.setPorDia(lBaremos.get(i).getPorDia());
						guardia.setIdTurno(lBaremos.get(i).getIdTurno());
						guar.add(guardia);
						guarAux.add(guardia);
						//if((i+1 != lBaremos.size()) && (!(hitoActual.equals(lBaremos.get(i+1).getIdHito())))) {
						lBaremos.get(i).setGuardiasObj(guar);
						lBaremos.get(i).setKeyForTabla(keyForTabla++);
						//}
					}
				}else {
					indiceNuevo = i;
					GuardiasItem guardia = new GuardiasItem();
					String[] dias = lBaremos.get(i).getDias().toString().split("\n");
					String[] parts = lBaremos.get(i).getGuardias().split(",");
					lBaremos.get(i).setGuardias(parts[0]);
					guardia.setNombre(lBaremos.get(i).getNomTurno() + "-" + parts[0]);
					guardia.setIdGuardia(lBaremos.get(i).getIdGuardia());
					guardia.setDiasGuardia(dias[0] + dias[1]);
					guardia.setFechabaja(lBaremos.get(i).getFechabaja());
					guardia.setBaremo(lBaremos.get(i).getBaremo());
					guardia.setnDias(lBaremos.get(i).getNDias());
					guardia.setIdTurno(lBaremos.get(i).getIdTurno());
					guardia.setNumMinimoSimple(lBaremos.get(i).getNumMinimoSimple());
					guardia.setSimpleOImporteIndividual(lBaremos.get(i).getSimpleOImporteIndividual());
					guardia.setNaPartir(lBaremos.get(i).getNaPartir());
					guardia.setMaximo(lBaremos.get(i).getMaximo());
					guardia.setDisponibilidad(lBaremos.get(i).getDisponibilidad());
					guardia.setPorDia(lBaremos.get(i).getPorDia());

					guar.add(guardia);
					guarAux.add(guardia);
					//if((i+1 != lBaremos.size()) && (!(hitoActual.equals(lBaremos.get(i+1).getIdHito())))) {
					lBaremos.get(i).setGuardiasObj(guar);
					lBaremos.get(i).setKeyForTabla(keyForTabla++);
					//}
				}
			}

			lBaremos.removeIf ( s -> s.getGuardiasObj() == null );

			error.setCode(200);
			BaremosRequestItem baremoFinal = new BaremosRequestItem();
			List<BaremosRequestItem> lBaremosFinal =  new ArrayList<>();
			baremoFinal.setGuardiasObj(guarAux);
			lBaremosFinal.add(baremoFinal);
			baremosRequestDTO.setBaremosRequestItems(lBaremosFinal);

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
	public BaremosGuardiaDTO saveBaremo(List<List<BaremosGuardiaItem>> baremosGuardiaItem, HttpServletRequest request) {
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
					for (List<BaremosGuardiaItem> baremoList : baremosGuardiaItem) {
						ScsHitofacturableguardiaExample hfe = new ScsHitofacturableguardiaExample();
						hfe.createCriteria().andIdinstitucionEqualTo(idInstitucion)
								.andIdturnoEqualTo(Integer.parseInt(baremoList.get(0).getIdTurno()))
								.andIdguardiaEqualTo(Integer.parseInt(baremoList.get(0).getIdGuardia()));

						scsHitofacturableguardiaExtendsMapper.deleteByExample(hfe);

						for (BaremosGuardiaItem baremo : baremoList) {

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

	@Override
	public BaremosGuardiaDTO getTurnoGuarConf(String[] configuracion,HttpServletRequest request) {
		BaremosGuardiaDTO baremosGuardiaDTO = new BaremosGuardiaDTO();
		Error error = new Error();

		LOGGER.info("getTurnoGuarConf() -> Entrada del servicio para obtener las guardias segun la configuracion ");

		String token = request.getHeader("Authorization");
//        String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<BaremosGuardiaItem> hitos = new ArrayList<>();

		if (idInstitucion != null) {

			if(configuracion.length != 0){
				List<BaremosGuardiaItem> turnoGuardiaConf = scsHitofacturableguardiaExtendsMapper
						.getTurnoGuarConf(idInstitucion.toString());
				for(BaremosGuardiaItem conf : turnoGuardiaConf){
					String[] h;
					h = conf.getHitos().split("/");
					if(h.length == configuracion.length){
						int contador = 0;
						for(int i=0;i < h.length;i++){
							for(int j=0;j < configuracion.length;j++){
								if(h[i].equals(configuracion[j])){
									contador++;
								}
							}
						}
						if(contador == configuracion.length){
							BaremosGuardiaItem hito = new BaremosGuardiaItem();
							hito.setIdTurno(conf.getIdTurno());
							hito.setIdGuardia(conf.getIdGuardia());
							hito.setNomguardia(conf.getNomguardia());
							hito.setNomturno(conf.getNomturno());
							hitos.add(hito);
						}
					}

				}
				baremosGuardiaDTO.setBaremosGuardiaItems(hitos);
				error.setCode(200);
			}

		} else {
			LOGGER.warn("getTurnoGuarConf() -> idInstitucion del token nula");
			error.setCode(500);
			error.setDescription("general.mensaje.error.bbdd");
		}

		baremosGuardiaDTO.setError(error);

		LOGGER.info("getTurnoGuarConf() -> Salida del servicio para obtener las guardias segun la configuracion ");

		return baremosGuardiaDTO;
	}

	@Override
	@Transactional
	public BaremosRequestDTO searchBaremosFichaGuardia(String idGuardia, HttpServletRequest request) {
		BaremosRequestDTO baremosRequestDTO = new BaremosRequestDTO();
		Error error = new Error();

		LOGGER.info("searchBaremosGuardia() -> Entrada del servicio para obtener baremos guardia ");

		String token = request.getHeader("Authorization");
//        String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (idInstitucion != null) {

			Integer tamMaximo = getTamanoMaximo(idInstitucion);
			List<BaremosRequestItem> lBaremos = baremosGuardiaMapper.searchBaremosFichaGuardia(idGuardia,
					idInstitucion, tamMaximo);
			for(BaremosRequestItem baremo : lBaremos){
				baremo.setGuardias(baremo.getNomTurno() + "-" + baremo.getGuardias());
			}
			int keyForTabla = 0;
			String hitoActual;
			List<GuardiasItem> guar = new ArrayList<GuardiasItem>();
			List<GuardiasItem> guarAux = new ArrayList<GuardiasItem>();
			int indiceNuevo = 0;

			error.setCode(200);
			BaremosRequestItem baremoFinal = new BaremosRequestItem();
			List<BaremosRequestItem> lBaremosFinal =  new ArrayList<>();
			baremoFinal.setGuardiasObj(guarAux);
			lBaremosFinal.add(baremoFinal);
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
	public StringDTO getResumenBaremos(String idGuardia, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		List<String> datos = new ArrayList<String>();
		StringDTO resumenBaremos = new StringDTO();

		if (idInstitucion != null) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"getResumenBaremos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"getResumenBaremos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (usuarios != null && usuarios.size() > 0) {
				LOGGER.info("getResumenBaremos() -> Entrada para obtener el resumen de baremos");

				datos = scsGuardiasturnoExtendsMapper.getResumenBaremos(idGuardia, idInstitucion.toString());

				LOGGER.info("getResumenBaremos() -> Salida del servicio para obtener el resumen de baremos");
			}
		}
		resumenBaremos.setValor(datos.get(0));
		return resumenBaremos;
	}
	
    private Integer getTamanoMaximo(Short idinstitucion) {
        GenParametrosExample genParametrosExample = new GenParametrosExample();
        genParametrosExample.createCriteria().andModuloEqualTo("SCS").andParametroEqualTo("TAM_MAX_CONSULTA_JG")
                .andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idinstitucion));
        genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
        LOGGER.info("genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
        List<GenParametros> tamMax = genParametrosMapper.selectByExample(genParametrosExample);
        LOGGER.info("genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
        Integer tamMaximo = null;
        if (tamMax != null) {
            tamMaximo  = Integer.valueOf(tamMax.get(0).getValor());
        } else {
            tamMaximo = null;
        }
        return tamMaximo;
    }

}