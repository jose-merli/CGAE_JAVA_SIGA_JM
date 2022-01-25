package org.itcgae.siga.DTOs.scs;

import java.util.Date;

import lombok.Data;

@Data
public class GestionEconomicaCatalunyaItem {
	private Long idIntercambio;
	private Long idJustificacion;
	private Long idDevolucion;
	private Long idCertificacion;
	private Long idCertificacionAnexo;
	private Short idTipoCertificacion;
	private String idTipoIntercambio;
	private String descripcionTipoIntercambio;
	private String descripcion;
	private Short idPeriodo;
	private Short anio;
	private Short idInstitucion;
	private Short idColegio;
	private Short idEstado;
	private String descripcionEstado;
	private String descripcionEstadoIca;
	private String descripcionEstadoConsell;
	private Date fechaDesde;
	private Date fechaHasta;
	private String nombrePeriodo;
	private Integer usuModificacion;
	private String errorValidacion;
	private String abreviaturaInstitucion;
	private String error;
}


//GestionEconomicaCatalunyaVo gestionEconomicaCatalunyaVo = new GestionEconomicaCatalunyaVo();
//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//if(objectForm.getIdTipoIntercambio()!=null)
//	gestionEconomicaCatalunyaVo.setIdTipoIntercambio(objectForm.getIdTipoIntercambio());
//if(objectForm.getIdInstitucion()!=null && !objectForm.getIdInstitucion().equals(""))
//	gestionEconomicaCatalunyaVo.setIdInstitucion(Short.valueOf(objectForm.getIdInstitucion()));
//
//if(objectForm.getFechaDesde()!=null && !objectForm.getFechaDesde().equals(""))
//	try {
//		gestionEconomicaCatalunyaVo.setFechaDesde(sdf.parse(objectForm.getFechaDesde()));
//	} catch (ParseException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//if(objectForm.getFechaHasta()!=null && !objectForm.getFechaHasta().equals(""))
//	try {
//		gestionEconomicaCatalunyaVo.setFechaHasta(sdf.parse(objectForm.getFechaHasta()));
//	} catch (ParseException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//if(objectForm.getIdIntercambio()!=null && !objectForm.getIdIntercambio().equals(""))
//	gestionEconomicaCatalunyaVo.setIdIntercambio(Long.valueOf(objectForm.getIdIntercambio()));
//if(objectForm.getIdJustificacion()!=null && !objectForm.getIdJustificacion().equals(""))
//	gestionEconomicaCatalunyaVo.setIdJustificacion(Long.valueOf(objectForm.getIdJustificacion()));
//
//if(objectForm.getIdColegio()!=null && !objectForm.getIdColegio().equals(""))
//	gestionEconomicaCatalunyaVo.setIdColegio(Short.valueOf(objectForm.getIdColegio()));
//
//if(objectForm.getIdDevolucion()!=null && !objectForm.getIdDevolucion().equals(""))
//	gestionEconomicaCatalunyaVo.setIdDevolucion(Long.valueOf(objectForm.getIdDevolucion()));
//
//if(objectForm.getIdCertificacion()!=null && !objectForm.getIdCertificacion().equals(""))
//	gestionEconomicaCatalunyaVo.setIdCertificacion(Long.valueOf(objectForm.getIdCertificacion()));
//if(objectForm.getIdCertificacionAnexo()!=null && !objectForm.getIdCertificacionAnexo().equals(""))
//	gestionEconomicaCatalunyaVo.setIdCertificacionAnexo(Long.valueOf(objectForm.getIdCertificacionAnexo()));
//if(objectForm.getIdTipoCertificacion()!=null && !objectForm.getIdTipoCertificacion().equals(""))
//	gestionEconomicaCatalunyaVo.setIdTipoCertificacion(Short.valueOf(objectForm.getIdTipoCertificacion()));
//
//
//if(objectForm.getIdEstado()!=null && !objectForm.getIdEstado().equals(""))
//	gestionEconomicaCatalunyaVo.setIdEstado(Short.valueOf(objectForm.getIdEstado()));
//if(objectForm.getIdPeriodo()!=null && !objectForm.getIdPeriodo().equals(""))
//	gestionEconomicaCatalunyaVo.setIdPeriodo(Short.valueOf(objectForm.getIdPeriodo()));
//if(objectForm.getAnio()!=null && !objectForm.getAnio().equals(""))
//	gestionEconomicaCatalunyaVo.setAnio(Short.valueOf(objectForm.getAnio()));
//
//gestionEconomicaCatalunyaVo.setDescripcion(objectForm.getDescripcion());
//gestionEconomicaCatalunyaVo.setDescripcionTipoIntercambio(objectForm.getDescripcionTipoIntercambio());
//
//gestionEconomicaCatalunyaVo.setNombrePeriodo(objectForm.getNombrePeriodo());
//
//gestionEconomicaCatalunyaVo.setUsuModificacion(objectForm.getUsuModificacion());
//gestionEconomicaCatalunyaVo.setAbreviaturaInstitucion(objectForm.getAbreviaturaInstitucion());
//gestionEconomicaCatalunyaVo.setError(objectForm.getError());
//String seleccion = objectForm.getSeleccion();
//if(seleccion!=null) {
//	String[] idLineasJustificacion = seleccion.split("##");
//	gestionEconomicaCatalunyaVo.setIdLineasJustificacion(idLineasJustificacion);
//}
//try {
//	if(getPathFile()!=null && !getPathFile().equals("")) {
//		gestionEconomicaCatalunyaVo.setFileErrorData(SIGAServicesHelper.getBytes(getPathFile()));
//	}
//	if(getTheFile()!=null) {
//		gestionEconomicaCatalunyaVo.setFileErrorData((getTheFile().getFileData()));
//		
//	}
//} catch (Exception e) {
//	e.printStackTrace();
//}
//
//
//
//
//
//return gestionEconomicaCatalunyaVo;