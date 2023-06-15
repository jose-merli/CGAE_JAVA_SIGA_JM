package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.GestionEconomicaCatalunyaItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.ExcelHelper;
import org.itcgae.siga.commons.utils.SIGAServicesHelper;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.EcomCola;
import org.itcgae.siga.db.entities.FcsJeCertEstado;
import org.itcgae.siga.db.entities.FcsJeCertEstadoExample;
import org.itcgae.siga.db.entities.FcsJeCertanexoEstado;
import org.itcgae.siga.db.entities.FcsJeCertanexoEstadoExample;
import org.itcgae.siga.db.entities.FcsJeDevEstado;
import org.itcgae.siga.db.entities.FcsJeDevEstadoExample;
import org.itcgae.siga.db.entities.FcsJeIntercambios;
import org.itcgae.siga.db.entities.FcsJeJustEstado;
import org.itcgae.siga.db.entities.FcsJeJustEstadoExample;
import org.itcgae.siga.db.entities.JeCertValerroneo;
import org.itcgae.siga.db.entities.JeCertValerroneoExample;
import org.itcgae.siga.db.entities.JeCertanexoValerroneo;
import org.itcgae.siga.db.entities.JeCertanexoValerroneoExample;
import org.itcgae.siga.db.entities.JeDevValerroneo;
import org.itcgae.siga.db.entities.JeDevValerroneoExample;
import org.itcgae.siga.db.entities.JeJusValerroneo;
import org.itcgae.siga.db.entities.JeJusValerroneoExample;
import org.itcgae.siga.db.mappers.FcsJeCertEstadoMapper;
import org.itcgae.siga.db.mappers.FcsJeCertanexoEstadoMapper;
import org.itcgae.siga.db.mappers.FcsJeDevEstadoMapper;
import org.itcgae.siga.db.mappers.FcsJeIntercambiosMapper;
import org.itcgae.siga.db.mappers.FcsJeJustEstadoMapper;
import org.itcgae.siga.db.mappers.GestionEconomicaCatalunyaMapper;
import org.itcgae.siga.db.mappers.JeCertValerroneoMapper;
import org.itcgae.siga.db.mappers.JeCertanexoValerroneoMapper;
import org.itcgae.siga.db.mappers.JeDevValerroneoMapper;
import org.itcgae.siga.db.mappers.JeJusValerroneoMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.scs.services.impl.facturacionsjcs.GestionEnvioInformacionEconomicaCatalunyaService.ICAS;
import org.itcgae.siga.scs.services.impl.facturacionsjcs.GestionEnvioInformacionEconomicaCatalunyaService.TIPOINTERCAMBIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Migración de diversos métodos SIGA classic para la Facturación SJCS 
 * @author mmorenomartin
 *
 */

@Service
public class CertificacionFacSJCSServicesCatalunyaHelper {
	
	private Logger LOGGER = Logger.getLogger(CertificacionFacSJCSServicesCatalunyaHelper.class);

	
	@Autowired
	private FacturacionSJCSHelper facHelper;
	
	@Autowired
	private FcsJeIntercambiosMapper jeIntercambiosMapper;
	
	@Autowired
	private FcsJeJustEstadoMapper justEstadoMapper;
	
	@Autowired
	private FcsJeDevEstadoMapper devEstadoMapper;
	
	@Autowired
	private FcsJeCertEstadoMapper certEstadoMapper;
	
	@Autowired
	FcsJeCertanexoEstadoMapper certanexoEstadoMapper;
	
	@Autowired
	private JeJusValerroneoMapper jusValerroneoMapper;
	
	@Autowired
	private JeDevValerroneoMapper jusDevValerroneoMapper;
	
	@Autowired
	private JeCertValerroneoMapper certValerroneoMapper;
	
	@Autowired
	private JeCertanexoValerroneoMapper certAnexValerroneoMapper;
	
	@Autowired
	private GestionEconomicaCatalunyaMapper gestionEconomicaCatalunyaMapper;
	
	@Autowired
	private ExcelHelper excelHelper;

	public void valida(GestionEconomicaCatalunyaItem justificacionVo, AdmUsuarios admUsr) throws BusinessException  {
		TIPOINTERCAMBIO tipo = TIPOINTERCAMBIO.getEnum(justificacionVo.getIdTipoIntercambio());
		updateEstadoIntercambioDirect(justificacionVo.getIdIntercambio(), GestionEnvioInformacionEconomicaCatalunyaService.ESTADOS_FCS_JE.VALIDANDO);
		try {
		switch (tipo) {
				case Justificaciones:
					validaJustificacion(justificacionVo);
					break;
				case Devoluciones:
					validaDevolucion(justificacionVo);
					break;
				case Certificaciones:
					validaCertificacionIca(justificacionVo);
					break;
				case Anexos:
					validaCertificacionAnexo(justificacionVo);
					break;
			
				default:
					break;
				}
		} catch(Exception e) {
			throw new BusinessException("Excepcion en método valida", e);
		}
	}
	
	private GestionEconomicaCatalunyaItem getCabeceraIntercambio(Long idIntercambio) throws BusinessException {
		GestionEconomicaCatalunyaItem  intercambio = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
		
			map.put("idIntercambio", idIntercambio);
			
			intercambio = gestionEconomicaCatalunyaMapper.getCabeceraIntercambio(map);

		} catch (Exception e) {
			throw new BusinessException("Se ha producido un error al obtener getCabeceraIntercambio", e);
		}

		return intercambio;
	}

	
	private void validaJustificacion(GestionEconomicaCatalunyaItem justificacionVo) throws Exception {
		Long idJustificacion = justificacionVo.getIdJustificacion();
		//		Priemro hay que borrar el error de validacion que hace referencia al estado
		FcsJeJustEstadoExample justEstadoExample = new FcsJeJustEstadoExample();
		justEstadoExample.createCriteria().andIdestadoEqualTo(GestionEnvioInformacionEconomicaCatalunyaService.ESTADOS_FCS_JE.VALIDADO_ERRONEO.getIdEstado()).andIdjustificacionEqualTo(idJustificacion);
		//Solo hay un estado de validado erroneo
		
		short idEstado = obtenerEstadoValidaJustificacion(justificacionVo);
		
		borraFicherosJustificacion(justificacionVo, idEstado);

		//Insertamos en la cola de ecom para el servicio validacion
		EcomCola ecomColaValidarJustificacion = new EcomCola();
		ecomColaValidarJustificacion.setIdinstitucion(justificacionVo.getIdInstitucion());
		ecomColaValidarJustificacion.setIdoperacion(SigaConstants.ECOM_OPERACION.ECOM2_CAT_VALIDA_JUSTIFICACION.getId());
	
		Map<String, String> mapa = new HashMap<String, String>();
		mapa.put(GestionEnvioInformacionEconomicaCatalunyaService.PARAM_ECOMCOLA_JUSTIFICACION, justificacionVo.getIdJustificacion().toString());
		mapa.put(GestionEnvioInformacionEconomicaCatalunyaService.PARAM_ECOMCOLA_IDINTERCAMBIO, justificacionVo.getIdIntercambio().toString());
	
		facHelper.insertaColaConParametros(ecomColaValidarJustificacion, mapa);
		
	}
	
	private void borraFicherosJustificacion(GestionEconomicaCatalunyaItem justificacionVo, short idEstado) {
		String pathcompleto = getFilePath(TIPOINTERCAMBIO.Justificaciones, justificacionVo,true,false);
		File vistaPreviaFile = new File(pathcompleto.toString());
		vistaPreviaFile.delete();
		pathcompleto = getFilePath(TIPOINTERCAMBIO.Justificaciones, justificacionVo,true,true);
		vistaPreviaFile = new File(pathcompleto.toString());
		vistaPreviaFile.delete();
		
		String pathcompletoFile = getFilePath(TIPOINTERCAMBIO.Justificaciones,idEstado, justificacionVo.getIdJustificacion(),justificacionVo.getIdInstitucion(),false);
		File dile = new File(pathcompletoFile.toString());
		dile.delete();
		
		pathcompletoFile = getFilePath(TIPOINTERCAMBIO.Justificaciones,  justificacionVo,false,true);
		dile = new File(pathcompletoFile.toString());
		dile.delete();
	}

	private short obtenerEstadoValidaJustificacion(GestionEconomicaCatalunyaItem justificacionVo) {
		short idEstado;
		Long idJustificacion = justificacionVo.getIdJustificacion();
		//		Priemro hay que borrar el error de validacion que hace referencia al estado
		FcsJeJustEstadoExample justEstadoExample = new FcsJeJustEstadoExample();
		justEstadoExample.createCriteria().andIdestadoEqualTo(GestionEnvioInformacionEconomicaCatalunyaService.ESTADOS_FCS_JE.VALIDADO_ERRONEO.getIdEstado()).andIdjustificacionEqualTo(idJustificacion);
	
		List<FcsJeJustEstado> estadosList =  justEstadoMapper.selectByExample(justEstadoExample);
		
		if(estadosList!=null&&estadosList.size()>0) {
			FcsJeJustEstado estadoValidadoErroneo = estadosList.get(0);
			JeJusValerroneoExample jeJusValerroneoExample = new JeJusValerroneoExample();
			jeJusValerroneoExample.createCriteria().andIdjustestadoEqualTo(estadoValidadoErroneo.getIdjustestado());
			jusValerroneoMapper.deleteByExample(jeJusValerroneoExample);
//			borramos el estado de error_validacion
			justEstadoMapper.deleteByExample(justEstadoExample);
			idEstado = GestionEnvioInformacionEconomicaCatalunyaService.ESTADOS_FCS_JE.VALIDADO_ERRONEO.getIdEstado();
		}else {
			justEstadoMapper.deleteByExample(justEstadoExample);
			justEstadoExample = new FcsJeJustEstadoExample();
			justEstadoExample.createCriteria().andIdestadoEqualTo(GestionEnvioInformacionEconomicaCatalunyaService.ESTADOS_FCS_JE.VALIDADO_CORRECTO.getIdEstado()).andIdjustificacionEqualTo(idJustificacion);
			justEstadoMapper.deleteByExample(justEstadoExample);
			idEstado = GestionEnvioInformacionEconomicaCatalunyaService.ESTADOS_FCS_JE.VALIDADO_CORRECTO.getIdEstado();
		}
		return idEstado;
	}



	private void validaDevolucion(GestionEconomicaCatalunyaItem devolucionVo) throws Exception {
//	Hay qeu borrar el estado validando y el estado error validacion
	//		Priemro hay que borrar el error de validacion que hace referencia al estado
	//Solo hay un estado de validado erroneo
	
	short idEstado = obtenerEstadoValidaDevolucion(devolucionVo);
	
		
	borraFicherosDevolucion(devolucionVo, idEstado);
	
	
	//Insertamos en la cola de ecom para el servicio validacion
	EcomCola ecomColaValidarJustificacion = new EcomCola();
	ecomColaValidarJustificacion.setIdinstitucion(devolucionVo.getIdInstitucion());
	ecomColaValidarJustificacion.setIdoperacion(SigaConstants.OPERACION.CAT_VALIDA_JUSTIFICACION.getId());

	Map<String, String> mapa = new HashMap<String, String>();
	mapa.put(GestionEnvioInformacionEconomicaCatalunyaService.PARAM_ECOMCOLA_DEVOLUCION, devolucionVo.getIdDevolucion().toString());
	mapa.put(GestionEnvioInformacionEconomicaCatalunyaService.PARAM_ECOMCOLA_IDINTERCAMBIO, devolucionVo.getIdIntercambio().toString());
	facHelper.insertaColaConParametros(ecomColaValidarJustificacion, mapa);

	}

	private void borraFicherosDevolucion(GestionEconomicaCatalunyaItem devolucionVo, short idEstado) {
		String pathcompleto = getFilePath(TIPOINTERCAMBIO.Devoluciones,  devolucionVo,true,false);
		File vistaPreviaFile = new File(pathcompleto.toString());
		vistaPreviaFile.delete();
		
		pathcompleto = getFilePath(TIPOINTERCAMBIO.Devoluciones, devolucionVo,true,true);
		vistaPreviaFile = new File(pathcompleto.toString());
		vistaPreviaFile.delete();
		
		String pathcompletoFile = getFilePath(TIPOINTERCAMBIO.Devoluciones, devolucionVo,false,true);
		File dile = new File(pathcompletoFile.toString());
		dile.delete();
		
		pathcompletoFile = getFilePath(TIPOINTERCAMBIO.Devoluciones,idEstado, devolucionVo.getIdDevolucion(),devolucionVo.getIdInstitucion(),false);
		dile = new File(pathcompletoFile.toString());
		dile.delete();
		
	}

	private short obtenerEstadoValidaDevolucion(GestionEconomicaCatalunyaItem devolucionVo) {
		Long idDevolucion = devolucionVo.getIdDevolucion();
		Short idEstado;
		FcsJeDevEstadoExample justEstadoExample = new FcsJeDevEstadoExample();
		justEstadoExample.createCriteria().andIdestadoEqualTo(GestionEnvioInformacionEconomicaCatalunyaService.ESTADOS_FCS_JE.VALIDADO_ERRONEO.getIdEstado()).andIddevolucionEqualTo(idDevolucion);
		
		List<FcsJeDevEstado> estadosList =  devEstadoMapper.selectByExample(justEstadoExample);
		
		if(estadosList!=null&&estadosList.size()>0) {
			FcsJeDevEstado estadoValidadoErroneo = estadosList.get(0);

			JeDevValerroneoExample jeJusValerroneoExample = new JeDevValerroneoExample();
			jeJusValerroneoExample.createCriteria().andIddevestadoEqualTo(estadoValidadoErroneo.getIddevestado());
			jusDevValerroneoMapper.deleteByExample(jeJusValerroneoExample);
			idEstado = GestionEnvioInformacionEconomicaCatalunyaService.ESTADOS_FCS_JE.VALIDADO_ERRONEO.getIdEstado();
		}else {
			devEstadoMapper.deleteByExample(justEstadoExample);
			justEstadoExample = new FcsJeDevEstadoExample();
			justEstadoExample.createCriteria().andIdestadoEqualTo(GestionEnvioInformacionEconomicaCatalunyaService.ESTADOS_FCS_JE.VALIDADO_CORRECTO.getIdEstado()).andIddevolucionEqualTo(idDevolucion);
			devEstadoMapper.deleteByExample(justEstadoExample);
			idEstado = GestionEnvioInformacionEconomicaCatalunyaService.ESTADOS_FCS_JE.VALIDADO_CORRECTO.getIdEstado();
		}
//		//borramos el estado de error_validacion
		devEstadoMapper.deleteByExample(justEstadoExample);

		return idEstado;
	}
	

	private void validaCertificacionIca(GestionEconomicaCatalunyaItem certificacionVo) throws Exception {
//	Hay que borrar el estado validando y el estado error validacion
	short idEstado = obtenerEstadoValidaCertificacionIca(certificacionVo);
	
	borraFicherosCertificacionIca(certificacionVo,idEstado);
	
	//Insertamos en la cola de ecom para el servicio validacion
	EcomCola ecomColaValidarJustificacion = new EcomCola();
	ecomColaValidarJustificacion.setIdinstitucion(certificacionVo.getIdInstitucion());
	ecomColaValidarJustificacion.setIdoperacion(SigaConstants.OPERACION.CAT_VALIDA_JUSTIFICACION.getId());

	Map<String, String> mapa = new HashMap<String, String>();
	mapa.put(GestionEnvioInformacionEconomicaCatalunyaService.PARAM_ECOMCOLA_CERTIFIFICACIONICA, certificacionVo.getIdCertificacion().toString());
	mapa.put(GestionEnvioInformacionEconomicaCatalunyaService.PARAM_ECOMCOLA_IDINTERCAMBIO, certificacionVo.getIdIntercambio().toString());
	facHelper.insertaColaConParametros(ecomColaValidarJustificacion, mapa);
	}

	private void borraFicherosCertificacionIca(GestionEconomicaCatalunyaItem certificacionVo, short idEstado) {
		Long idCertificacion = certificacionVo.getIdCertificacion();

		String pathcompleto = getFilePath(TIPOINTERCAMBIO.Certificaciones,certificacionVo,true,false);
		File vistaPreviaFile = new File(pathcompleto.toString());
		vistaPreviaFile.delete();
		
		pathcompleto = getFilePath(TIPOINTERCAMBIO.Certificaciones,certificacionVo,true,true);
		vistaPreviaFile = new File(pathcompleto.toString());
		vistaPreviaFile.delete();
		
		String pathcompletoFile = getFilePath(TIPOINTERCAMBIO.Certificaciones,certificacionVo,false,true);
		File dile = new File(pathcompletoFile.toString());
		dile.delete();
		
		pathcompletoFile = getFilePath(TIPOINTERCAMBIO.Certificaciones,idEstado, idCertificacion,certificacionVo.getIdInstitucion(),false);
		dile = new File(pathcompletoFile.toString());
		dile.delete();
		
	}

	private short obtenerEstadoValidaCertificacionIca(GestionEconomicaCatalunyaItem certificacionVo) {
		Long idCertificacion = certificacionVo.getIdCertificacion();
		//		Priemro hay que borrar el error de validacion que hace referencia al estado
		FcsJeCertEstadoExample certEstadoExample = new FcsJeCertEstadoExample();
		certEstadoExample.createCriteria().andIdestadoEqualTo(GestionEnvioInformacionEconomicaCatalunyaService.ESTADOS_FCS_JE.VALIDADO_ERRONEO.getIdEstado()).andIdcertificacionEqualTo(idCertificacion);
		List<FcsJeCertEstado> estadosList =  certEstadoMapper.selectByExample(certEstadoExample);
		//Solo hay un estado de validado erroneo
		short idEstado ;
		if(estadosList!=null&&estadosList.size()>0) {
			FcsJeCertEstado estadoValidadoErroneo = estadosList.get(0);

			JeCertValerroneoExample certValerroneoExample = new JeCertValerroneoExample();
			certValerroneoExample.createCriteria().andIdcertestadoEqualTo(estadoValidadoErroneo.getIdcertestado());
			certValerroneoMapper.deleteByExample(certValerroneoExample);
			idEstado = GestionEnvioInformacionEconomicaCatalunyaService.ESTADOS_FCS_JE.VALIDADO_ERRONEO.getIdEstado();
		}else {
			certEstadoMapper.deleteByExample(certEstadoExample);
			certEstadoExample = new FcsJeCertEstadoExample();
			certEstadoExample.createCriteria().andIdestadoEqualTo(GestionEnvioInformacionEconomicaCatalunyaService.ESTADOS_FCS_JE.VALIDADO_CORRECTO.getIdEstado()).andIdcertificacionEqualTo(idCertificacion);
			certEstadoMapper.deleteByExample(certEstadoExample);
			idEstado = GestionEnvioInformacionEconomicaCatalunyaService.ESTADOS_FCS_JE.VALIDADO_CORRECTO.getIdEstado();
				
		}

//		//borramos el estado de error_validacion
		certEstadoMapper.deleteByExample(certEstadoExample);
		return idEstado;
	}

	private void validaCertificacionAnexo(GestionEconomicaCatalunyaItem certificacionVo) throws Exception {
//	Hay qeu borrar el estado validando y el estado error validacion
	Long idCertificacionAnexo = certificacionVo.getIdCertificacionAnexo();
	//		Priemro hay que borrar el error de validacion que hace referencia al estado
	
	//Solo hay un estado de validado erroneo
	short idEstado = obtenerEstadoValidaCertificacionAnexo(certificacionVo);

//	
	borraFicherosCertificacionAnexo(certificacionVo,idEstado);
//	
//	
//	
//	//Insertamos en la cola de ecom para el servicio validacion
	EcomCola ecomColaValidarJustificacion = new EcomCola();
	ecomColaValidarJustificacion.setIdinstitucion(certificacionVo.getIdInstitucion());
	ecomColaValidarJustificacion.setIdoperacion(SigaConstants.OPERACION.CAT_VALIDA_JUSTIFICACION.getId());

	Map<String, String> mapa = new HashMap<String, String>();
	mapa.put(GestionEnvioInformacionEconomicaCatalunyaService.PARAM_ECOMCOLA_CERTIFIFICACIONANEXO, idCertificacionAnexo.toString());
	mapa.put(GestionEnvioInformacionEconomicaCatalunyaService.PARAM_ECOMCOLA_IDINTERCAMBIO, certificacionVo.getIdIntercambio().toString());
	facHelper.insertaColaConParametros(ecomColaValidarJustificacion, mapa);
//
	}

	
	
	private void borraFicherosCertificacionAnexo(GestionEconomicaCatalunyaItem certificacionVo, short idEstado) {
		String pathcompleto = getFilePath(TIPOINTERCAMBIO.Anexos,  certificacionVo,true,false);
		File vistaPreviaFile = new File(pathcompleto.toString());
		vistaPreviaFile.delete();
		
		pathcompleto = getFilePath(TIPOINTERCAMBIO.Anexos, certificacionVo,true,true);
		vistaPreviaFile = new File(pathcompleto.toString());
		vistaPreviaFile.delete();
		
		String pathcompletoFile = getFilePath(TIPOINTERCAMBIO.Anexos, certificacionVo,false,true);
		File dile = new File(pathcompletoFile.toString());
		dile.delete();
		
		pathcompletoFile = getFilePath(TIPOINTERCAMBIO.Anexos, idEstado, certificacionVo.getIdDevolucion(),certificacionVo.getIdInstitucion(),false);
		dile = new File(pathcompletoFile.toString());
		dile.delete();
	}

	private short obtenerEstadoValidaCertificacionAnexo(GestionEconomicaCatalunyaItem certificacionVo) {
		short idEstado;
		Long idCertificacionAnexo = certificacionVo.getIdCertificacionAnexo();
		FcsJeCertanexoEstadoExample fcsJeCertanexoEstadoExample = new FcsJeCertanexoEstadoExample();
		 fcsJeCertanexoEstadoExample.createCriteria().andIdestadoEqualTo(GestionEnvioInformacionEconomicaCatalunyaService.ESTADOS_FCS_JE.VALIDADO_ERRONEO.getIdEstado()).andIdcertificacionanexoEqualTo(idCertificacionAnexo);
		List<FcsJeCertanexoEstado> estadosList =  certanexoEstadoMapper.selectByExample(fcsJeCertanexoEstadoExample);
		
		if(estadosList!=null&&estadosList.size()>0) {
			FcsJeCertanexoEstado estadoValidadoErroneo = estadosList.get(0);

			JeCertanexoValerroneoExample jeJusValerroneoExample = new JeCertanexoValerroneoExample();
			jeJusValerroneoExample.createCriteria().andIdcertanexoestadoEqualTo(estadoValidadoErroneo.getIdcertificacionanexoestado());
			certAnexValerroneoMapper.deleteByExample(jeJusValerroneoExample);
			idEstado = GestionEnvioInformacionEconomicaCatalunyaService.ESTADOS_FCS_JE.VALIDADO_ERRONEO.getIdEstado();
		}else {
			certanexoEstadoMapper.deleteByExample(fcsJeCertanexoEstadoExample);
			fcsJeCertanexoEstadoExample = new FcsJeCertanexoEstadoExample();
			fcsJeCertanexoEstadoExample.createCriteria().andIdestadoEqualTo(GestionEnvioInformacionEconomicaCatalunyaService.ESTADOS_FCS_JE.VALIDADO_CORRECTO.getIdEstado()).andIdcertificacionanexoEqualTo(idCertificacionAnexo);
			certanexoEstadoMapper.deleteByExample(fcsJeCertanexoEstadoExample);
			idEstado = GestionEnvioInformacionEconomicaCatalunyaService.ESTADOS_FCS_JE.VALIDADO_CORRECTO.getIdEstado();
				
		}

//		//borramos el estado de error_validacion
		certanexoEstadoMapper.deleteByExample(fcsJeCertanexoEstadoExample);
		
		return idEstado;

	}

	public void updateEstadoIntercambioDirect( Long idIntercambio,GestionEnvioInformacionEconomicaCatalunyaService.ESTADOS_FCS_JE estado) throws BusinessException {
		FcsJeIntercambios intercambio = new FcsJeIntercambios();
		intercambio.setIdestado(estado.getIdEstado());
		intercambio.setIdintercambio(idIntercambio);
		intercambio.setFechamodificacion(new Date());
		intercambio.setUsumodificacion(facHelper.getUsuarioAuto().getIdusuario());
		
		try {
			jeIntercambiosMapper.updateByPrimaryKeySelective(intercambio);	
		} catch (Exception e) {
			throw new BusinessException("Excepción en updateEstadoIntercambioDirect",e);
		}
		
		
	}
	
	
	private String getFilePath(TIPOINTERCAMBIO tipo, GestionEconomicaCatalunyaItem vo,boolean isVistaPrevia,boolean isError)throws BusinessException {
		StringBuilder nombrefichero = new StringBuilder();
	
			if(isError) {
				nombrefichero.append(GestionEnvioInformacionEconomicaCatalunyaService.JUSTIFICACION_ERROR);
				nombrefichero.append("_");
				
			}
			if(isVistaPrevia) {
				nombrefichero.append(GestionEnvioInformacionEconomicaCatalunyaService.SUFIJO_ARCHIVO_VISTAPREVIA);
				nombrefichero.append("_");
				
			}
			
			nombrefichero.append(tipo.getId());
			nombrefichero.append("_");
			nombrefichero.append(vo.getIdInstitucion());
			nombrefichero.append("_");
			nombrefichero.append(ICAS.CICAC.getCodSIGA());
			nombrefichero.append("_");
			nombrefichero.append(vo.getAnio());
			nombrefichero.append("_");
			nombrefichero.append(vo.getIdPeriodo());
			nombrefichero.append(".xls");
		String path = getFileDirectory(vo.getIdInstitucion());
		String pathcompleto = path.toString()+File.separatorChar+nombrefichero;
		LOGGER.debug("PATH fichero:"+pathcompleto);
		return pathcompleto;
		
	}
	

private String getFilePath(TIPOINTERCAMBIO tipo, Short idEstado,Long identificador, Short idInstitucion,boolean isConsell)throws BusinessException {
	StringBuilder nombrefichero = new StringBuilder();
		
		String nombre ="";
		switch (tipo) {
		case Justificaciones:
			nombre =  getNombreXmlJustificacion(identificador,idEstado,isConsell);
		break;
		case Devoluciones:
			nombre = getNombreXmlDevolucion(identificador,idEstado,isConsell);			
			break;
		case Certificaciones:
			nombre = getNombreXmlCertificacionIca(identificador,idEstado,isConsell);
			break;
		case Anexos:
			nombre = getNombreXmlAnexo(identificador,idEstado,isConsell);
			break;
		default:
			nombre = "";
			LOGGER.error("Tipo intercambio no encontrado");
			
			throw new BusinessException("Tipo intercambio no encontrado");
		}
		if(nombre!=null) {
			nombre = nombre.replaceAll(".xml", ".xls");
			nombrefichero.append(nombre);
		}else
			nombre = "";
		
	
	String path = getFileDirectory(idInstitucion);
	String pathcompleto = path.toString()+File.separatorChar+nombrefichero;
	LOGGER.debug("PATH fichero:"+pathcompleto);
	return pathcompleto;
	
} 

private String getNombreXmlJustificacion(Long idJustificacion, short idEstado,boolean isConsell) throws BusinessException {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("idJustificacion",idJustificacion);
	if(idEstado==90) {	
		map.put("detalles",0);
	}
	map.put("idEstado",idEstado);
	
	String nombre;
	try {
		nombre = gestionEconomicaCatalunyaMapper.getNombreXmlJustificacion(map);
	} catch (Exception e) {
		throw new BusinessException("Se ha producido un error al obtener el getNombreXmlJustificacion", e);
	}
	return nombre;
}

private String getNombreXmlDevolucion(Long idDevolucion, short idEstado,boolean isConsell) throws BusinessException {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("idDevolucion",idDevolucion);
	if(idEstado==90) {
		
		map.put("detalles",0);
	}
	map.put("idEstado",idEstado);
	String nombre;
	try {
		nombre = gestionEconomicaCatalunyaMapper.getNombreXmlDevolucion(map);
	} catch (Exception e) {
		throw new BusinessException("Se ha producido un error al obtener el getNombreXmlDevolucion", e);
	}
	return nombre;
}
private String getNombreXmlCertificacionIca(Long idCertificacionIca, short idEstado,boolean isConsell) throws BusinessException {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("idCertificacionIca",idCertificacionIca);
	if(idEstado==90) {
		map.put("detalles",0);
	}
	map.put("idEstado",idEstado);
	String nombre;
	try {
		nombre = gestionEconomicaCatalunyaMapper.getNombreXmlCertificacionIca(map);
	} catch (Exception e) {
		throw new BusinessException("Se ha producido un error al obtener el getNombreXmlCertificacionIca", e);
	}
	return nombre;
}
private String getNombreXmlAnexo(Long idCertificacionAnexo,short idEstado,boolean isConsell) throws BusinessException {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("idCertificacionAnexo",idCertificacionAnexo);
	if(idEstado==90) {
		
		map.put("detalles",0);
	}
	map.put("idEstado",idEstado);
	
	String nombre;
	try {
		nombre = gestionEconomicaCatalunyaMapper.getNombreXmlCertificacionAnexo(map);
	} catch (Exception e) {
		throw new BusinessException("Se ha producido un error al obtener el getNombreXmlAnexo", e);
	}
	return nombre;
}


	public String getFileDirectory(Short idInstitucion)  {
		Path pInstitucion = facHelper.getRutaAlmacenFichero(idInstitucion);	
		Path pDescarga = pInstitucion.resolve(GestionEnvioInformacionEconomicaCatalunyaService.JUSTIFICACION);
	
		pDescarga.toFile().mkdirs();
	
		LOGGER.debug("Directorio descarga:" + pDescarga.toString());
		return pDescarga.toString();	
	}
	
	
	public File descargaErrorValidacion (GestionEconomicaCatalunyaItem justificacionVo) throws BusinessException {
		File log = null;
		try {
			GestionEconomicaCatalunyaItem intercambio = getCabeceraIntercambio(justificacionVo.getIdIntercambio());
			justificacionVo.setAnio(intercambio.getAnio());
			justificacionVo.setIdPeriodo(intercambio.getIdPeriodo());
			justificacionVo.setIdInstitucion(intercambio.getIdInstitucion());
			
			log = getFileErroresValidacion(justificacionVo);
//			request.setAttribute("nombreFichero", log.getName());
//			request.setAttribute("rutaFichero", log.getPath());			
//			request.setAttribute("borrarFichero", "false");			



		}catch (BusinessException e){
			throw new BusinessException("messages.general.error", e , new String[] {"modulo.gratuita"});
		}
		catch (Exception e){
			throw new BusinessException("messages.general.error", e , new String[] {"modulo.gratuita"});

		}
		return log;
	}
	
	
public	File getFileErroresValidacion(GestionEconomicaCatalunyaItem justificacionVo) throws BusinessException {
		GestionEnvioInformacionEconomicaCatalunyaService.TIPOINTERCAMBIO tipo = GestionEnvioInformacionEconomicaCatalunyaService.TIPOINTERCAMBIO
				.getEnum(justificacionVo.getIdTipoIntercambio());

		switch (tipo) {
		case Justificaciones:
			return getFileErroresValidacionJustificacion(justificacionVo);
		case Devoluciones:
			return getFileErroresValidacionDevolucion(justificacionVo);
		case Certificaciones:
			return getFileErroresValidacionCertificacionIca(justificacionVo);
		case Anexos:
			return getFileErroresValidacionCertificacionAnexo(justificacionVo);
		default:
			break;
		}
		return null;
	}
	
private File getFileErroresValidacionJustificacion(GestionEconomicaCatalunyaItem itemVo)throws BusinessException{
		String pathcompleto = getFilePath(TIPOINTERCAMBIO.Justificaciones, itemVo,true,true);
		File jsutificaioneFile = new File(pathcompleto.toString());
		if(jsutificaioneFile!=null && jsutificaioneFile.exists())
			return jsutificaioneFile;
		Long idJustificacion = itemVo.getIdJustificacion(); 

		FcsJeJustEstadoExample justEstadoExample = new FcsJeJustEstadoExample();
		justEstadoExample.createCriteria().andIdestadoEqualTo(GestionEnvioInformacionEconomicaCatalunyaService.ESTADOS_FCS_JE.VALIDADO_ERRONEO.getIdEstado()).andIdjustificacionEqualTo(idJustificacion);
		List<FcsJeJustEstado> estadosList =  justEstadoMapper.selectByExample(justEstadoExample);
		//Solo hay un estado de validado erroneo
		FcsJeJustEstado estadoValidadoErroneo = estadosList.get(0);


		JeJusValerroneoExample jeJusValerroneoExample = new JeJusValerroneoExample();
		jeJusValerroneoExample.createCriteria().andIdjustestadoEqualTo(estadoValidadoErroneo.getIdjustestado());
		List<JeJusValerroneo> jusValerroneoList =  jusValerroneoMapper.selectByExampleWithBLOBs(jeJusValerroneoExample);
		//Solo hay un estado de validado erroneo
		JeJusValerroneo jusValerroneo = jusValerroneoList.get(0);
		String errorString = jusValerroneo.getDescerrorgenera();
		
		String[] errores = errorString.split("\n");
		Vector<Hashtable<String, Object>> datos = new Vector<Hashtable<String,Object>>();
		Hashtable<String, Object> datosColumnas = null;
		
		for (String linea : errores) {
			datosColumnas = new Hashtable<String, Object>();
			datosColumnas.put(GestionEnvioInformacionEconomicaCatalunyaService.JUSTIFICACION_ERROR, linea);
			datos.add(datosColumnas);
		}
		
		List<String> camposError = new ArrayList<String>(); 
		camposError.add(GestionEnvioInformacionEconomicaCatalunyaService.JUSTIFICACION_ERROR);
		byte[] bytesJustificaio = this.excelHelper.createExcelBytes(camposError, datos);
		File logFile = SIGAServicesHelper.createFile(bytesJustificaio,jsutificaioneFile.getParent(),jsutificaioneFile.getName());
		LOGGER.debug("Fin getFileErroresValidacionJustificacion");
		
		
		return logFile;
	}


private  File getFileErroresValidacionDevolucion(GestionEconomicaCatalunyaItem itemVo) throws BusinessException {
	String pathcompleto = getFilePath(TIPOINTERCAMBIO.Devoluciones, itemVo,true,true);
	File jsutificaioneFile = new File(pathcompleto.toString());
	if(jsutificaioneFile!=null && jsutificaioneFile.exists())
		return jsutificaioneFile;
	
	Long idDevolucion = itemVo.getIdDevolucion(); 

	FcsJeDevEstadoExample justEstadoExample = new FcsJeDevEstadoExample();
	justEstadoExample.createCriteria().andIdestadoEqualTo(GestionEnvioInformacionEconomicaCatalunyaService.ESTADOS_FCS_JE.VALIDADO_ERRONEO.getIdEstado()).andIddevolucionEqualTo(idDevolucion);
	List<FcsJeDevEstado> estadosList =  devEstadoMapper.selectByExample(justEstadoExample);
	//Solo hay un estado de validado erroneo
	FcsJeDevEstado estadoValidadoErroneo = estadosList.get(0);

	JeDevValerroneoExample jeJusValerroneoExample = new JeDevValerroneoExample();
	jeJusValerroneoExample.createCriteria().andIddevestadoEqualTo(estadoValidadoErroneo.getIddevestado());
	List<JeDevValerroneo> jusValerroneoList =  jusDevValerroneoMapper.selectByExampleWithBLOBs(jeJusValerroneoExample);
	//Solo hay un estado de validado erroneo
	JeDevValerroneo jusValerroneo = jusValerroneoList.get(0);
	String errorString = jusValerroneo.getDescerrorgeneral();
	
	String[] errores = errorString.split("\n");
	Vector<Hashtable<String, Object>> datos = new Vector<Hashtable<String,Object>>();
	Hashtable<String, Object> datosColumnas = null;
	
	for (String linea : errores) {
		datosColumnas = new Hashtable<String, Object>();
		datosColumnas.put(GestionEnvioInformacionEconomicaCatalunyaService.JUSTIFICACION_ERROR, linea);
		datos.add(datosColumnas);
	}
	
	List<String> camposError = new ArrayList<String>(); 
	camposError.add(GestionEnvioInformacionEconomicaCatalunyaService.JUSTIFICACION_ERROR);
	byte[] bytesJustificaio = this.excelHelper.createExcelBytes(camposError, datos);
	
	File logFile = SIGAServicesHelper.createFile(bytesJustificaio,jsutificaioneFile.getParent(),jsutificaioneFile.getName());
	LOGGER.debug("Fin getFileErroresValidacionDevolucion");
	
	
	return logFile;
}

private File getFileErroresValidacionCertificacionIca(GestionEconomicaCatalunyaItem certificacionVo) throws BusinessException {
	String pathcompleto = getFilePath(TIPOINTERCAMBIO.Certificaciones, certificacionVo,true,true);
	File jsutificaioneFile = new File(pathcompleto.toString());
	if(jsutificaioneFile!=null && jsutificaioneFile.exists())
		return jsutificaioneFile;
	
	Long idCertificacion = certificacionVo.getIdCertificacion(); 

	FcsJeCertEstadoExample justEstadoExample = new FcsJeCertEstadoExample();
	 justEstadoExample.createCriteria()
	 .andIdestadoEqualTo(GestionEnvioInformacionEconomicaCatalunyaService.ESTADOS_FCS_JE.VALIDADO_ERRONEO.getIdEstado())
	 .andIdcertificacionEqualTo(idCertificacion);
	List<FcsJeCertEstado> estadosList =  certEstadoMapper.selectByExample(justEstadoExample);
	//Solo hay un estado de validado erroneo
	FcsJeCertEstado estadoValidadoErroneo = estadosList.get(0);

	JeCertValerroneoExample jeJusValerroneoExample = new JeCertValerroneoExample();
	jeJusValerroneoExample.createCriteria()
	.andIdcertestadoEqualTo(estadoValidadoErroneo.getIdcertestado());
	List<JeCertValerroneo> jusValerroneoList =  certValerroneoMapper.selectByExampleWithBLOBs(jeJusValerroneoExample);
	//Solo hay un estado de validado erroneo
	JeCertValerroneo jusValerroneo = jusValerroneoList.get(0);
	String errorString = jusValerroneo.getDescerrorgeneral();
	
	String[] errores = errorString.split("\n");
	Vector<Hashtable<String, Object>> datos = new Vector<Hashtable<String,Object>>();
	Hashtable<String, Object> datosColumnas = null;
	
	for (String linea : errores) {
				
		
		datosColumnas = new Hashtable<String, Object>();
		datosColumnas.put(GestionEnvioInformacionEconomicaCatalunyaService.JUSTIFICACION_ERROR, linea);
		datos.add(datosColumnas);
		
		
	}
	List<String> camposError = new ArrayList<String>(); 
	camposError.add(GestionEnvioInformacionEconomicaCatalunyaService.JUSTIFICACION_ERROR);
	byte[] bytesJustificaio = this.excelHelper.createExcelBytes(camposError, datos);
	
	File logFile = SIGAServicesHelper.createFile(bytesJustificaio,jsutificaioneFile.getParent(),jsutificaioneFile.getName());
	LOGGER.debug("Fin getFileErroresValidacionCertificacionIca");
	
	
	return logFile;
}


private File getFileErroresValidacionCertificacionAnexo(GestionEconomicaCatalunyaItem certificacionVo) throws BusinessException {
	String pathcompleto = getFilePath(TIPOINTERCAMBIO.Anexos,certificacionVo, true,true);
	File jsutificaioneFile = new File(pathcompleto.toString());
	if(jsutificaioneFile!=null && jsutificaioneFile.exists())
		return jsutificaioneFile;
	
	Long idCertificacion = certificacionVo.getIdCertificacionAnexo(); 

	FcsJeCertanexoEstadoExample justEstadoExample = new FcsJeCertanexoEstadoExample();
	justEstadoExample.createCriteria()
	.andIdestadoEqualTo(GestionEnvioInformacionEconomicaCatalunyaService.ESTADOS_FCS_JE.VALIDADO_ERRONEO.getIdEstado())
	.andIdcertificacionanexoEqualTo(idCertificacion);
	List<FcsJeCertanexoEstado> estadosList =  certanexoEstadoMapper.selectByExample(justEstadoExample);
	//Solo hay un estado de validado erroneo
	FcsJeCertanexoEstado estadoValidadoErroneo = estadosList.get(0);

	JeCertanexoValerroneoExample jeJusValerroneoExample = new JeCertanexoValerroneoExample();
	jeJusValerroneoExample.createCriteria()
	.andIdcertanexoestadoEqualTo(estadoValidadoErroneo.getIdcertificacionanexoestado());
	List<JeCertanexoValerroneo> jusValerroneoList =  certAnexValerroneoMapper.selectByExampleWithBLOBs(jeJusValerroneoExample);
	//Solo hay un estado de validado erroneo
	JeCertanexoValerroneo jusValerroneo = jusValerroneoList.get(0);
	String errorString = jusValerroneo.getDescerrorgeneral();
	
	String[] errores = errorString.split("\n");
	Vector<Hashtable<String, Object>> datos = new Vector<Hashtable<String,Object>>();
	Hashtable<String, Object> datosColumnas = null;
	
	for (String linea : errores) {
				
		
		datosColumnas = new Hashtable<String, Object>();
		datosColumnas.put(GestionEnvioInformacionEconomicaCatalunyaService.JUSTIFICACION_ERROR, linea);
		datos.add(datosColumnas);
		
		
	}
	List<String> camposError = new ArrayList<String>(); 
	camposError.add(GestionEnvioInformacionEconomicaCatalunyaService.JUSTIFICACION_ERROR);
	byte[] bytesJustificaio = this.excelHelper.createExcelBytes(camposError, datos);
	
	File logFile = SIGAServicesHelper.createFile(bytesJustificaio,jsutificaioneFile.getParent(),jsutificaioneFile.getName());
	LOGGER.debug("Fin getFileErroresValidacionCertificacionAnexo");
	
	
	return logFile;
}

public void enviaRespuestaCICAC_ICA (GestionEconomicaCatalunyaItem justificacionVo) throws BusinessException {
	try {
		GestionEconomicaCatalunyaItem intercambio = getCabeceraIntercambio(justificacionVo.getIdIntercambio());
		procesaRespuestasPendientes(intercambio);
	}catch (BusinessException e){
		throw new BusinessException("error en enviaRespuestaCICAC_ICA", e);
	}
	catch (Exception e){
		throw new BusinessException("messages.general.error", e);
	}
}

private void procesaRespuestasPendientes(GestionEconomicaCatalunyaItem gestionEconomicaCatalunyaVo)  throws Exception {
	EcomCola ecomColaValidarJustificacion = new EcomCola();
	ecomColaValidarJustificacion.setIdoperacion(SigaConstants.ECOM_OPERACION.CAT_ENVIA_RESP_JUSTIFICACION.getId());
	ecomColaValidarJustificacion.setIdinstitucion(gestionEconomicaCatalunyaVo.getIdInstitucion());
	Map<String, String> mapa = new HashMap<String, String>();
	mapa.put(GestionEnvioInformacionEconomicaCatalunyaService.PARAM_ECOMCOLA_IDINTERCAMBIO, gestionEconomicaCatalunyaVo.getIdIntercambio().toString());
	facHelper.insertaColaConParametros(ecomColaValidarJustificacion, mapa);

}

	
}


