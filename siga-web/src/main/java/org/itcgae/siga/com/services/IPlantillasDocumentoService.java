package org.itcgae.siga.com.services;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.com.ComboConsultasDTO;
import org.itcgae.siga.DTOs.com.ComboSufijoDTO;
import org.itcgae.siga.DTOs.com.ConsultasDTO;
import org.itcgae.siga.DTOs.com.DocumentoPlantillaItem;
import org.itcgae.siga.DTOs.com.DocumentosPlantillaDTO;
import org.itcgae.siga.DTOs.com.PlantillaDocumentoBorrarDTO;
import org.itcgae.siga.DTOs.com.ResponseDataDTO;
import org.itcgae.siga.DTOs.com.ResponseDocumentoDTO;
import org.itcgae.siga.DTOs.com.ResponseFileDTO;
import org.itcgae.siga.DTOs.com.TarjetaPlantillaDocumentoDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.Error;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface IPlantillasDocumentoService {

	/**
	 * Obtiene las consultas asociadas a las plantillas de un informe
	 * @param request
	 * @param plantillaDoc
	 * @param historico
	 * @return
	 */
	ConsultasDTO obtenerConsultasPlantilla(HttpServletRequest request, TarjetaPlantillaDocumentoDTO plantillaDoc, boolean historico);

	//ComboDTO obtenerConsultasDisponibles(HttpServletRequest request, TarjetaPlantillaDocumentoDTO plantillaDoc);

	/**
	 * Sube una plantilla de documento al fileSystem
	 * @param request
	 * @return
	 */
	ResponseDocumentoDTO uploadFile(MultipartHttpServletRequest request, String idClaseComunicacion);

	//ComboDTO obtenerConsultasDisponibles(HttpServletRequest request, TarjetaPlantillaDocumentoDTO plantillaDoc);
	
	/**
	 * Guarda las consultas asociadas a las plantillas de un informe
	 * @param request
	 * @param plantillaDoc
	 * @return
	 */
	Error guardarConsultasPlantilla(HttpServletRequest request, TarjetaPlantillaDocumentoDTO plantillaDoc);

	/**
	 * Guarda una plantilla de documento en bdd
	 * @param request
	 * @param documento
	 * @return
	 */
	ResponseDocumentoDTO guardarPlantillaDocumento(HttpServletRequest request, DocumentoPlantillaItem documento);

	/**
	 * Obtiene las plantillas asignadas a un informe
	 * @param request
	 * @param plantillaDoc
	 * @return
	 */
	DocumentosPlantillaDTO obtenerPlantillasInforme(HttpServletRequest request, TarjetaPlantillaDocumentoDTO plantillaDoc);

	/**
	 * Obtiene el combo de sufijos posibles
	 * @param request
	 * @return
	 */
	ComboSufijoDTO obtenerSufijos(HttpServletRequest request);

	/**
	 * Obtiene el combo de formatos de salida
	 * @param request
	 * @return
	 */
	ComboDTO obtenerFormatoSalida(HttpServletRequest request);

	/**
	 * Guarda una plantilla de documento
	 * @param request
	 * @param plantillaDoc
	 * @return
	 */
	ResponseDataDTO guardarModPlantillaDocumento(HttpServletRequest request, TarjetaPlantillaDocumentoDTO plantillaDoc);

	/**
	 * Guarda datos salida de la plantilla documento
	 * @param request
	 * @param plantillaDoc
	 * @return
	 */
	ResponseDataDTO guardarDatosSalida(HttpServletRequest request, TarjetaPlantillaDocumentoDTO plantillaDoc);

	/**
	 * Obtiene los combos de las consultas posibles seperadas por objetivo
	 * @param request
	 * @param plantillaDoc
	 * @return
	 */
	ComboConsultasDTO obtenerConsultasDisponibles(HttpServletRequest request, TarjetaPlantillaDocumentoDTO plantillaDoc);

	/**
	 * Borra las consultas indicadas
	 * @param request
	 * @param plantillaDoc
	 * @return
	 */
	Error borrarConsultasPlantilla(HttpServletRequest request, PlantillaDocumentoBorrarDTO[] plantillaDoc);

	/**
	 * Elimina las plantillas recibidas
	 * @param request
	 * @param plantillaDoc
	 * @return
	 */
	Error borrarPlantillas(HttpServletRequest request, PlantillaDocumentoBorrarDTO[] plantillaDoc);

	/**
	 * Descarga la plantilla de documento
	 * @param request
	 * @param plantillaDoc
	 * @return
	 */
	ResponseFileDTO descargarPlantilla(HttpServletRequest request, DocumentoPlantillaItem plantillaDoc);

	ConsultasDTO obtenerConsultasById(HttpServletRequest request, TarjetaPlantillaDocumentoDTO plantillaDoc);
	
	/**
	 * Obtiene el tamaño de los ficheros permitido
	 * @param request
	 * @return
	 */
	ComboDTO obtenerSizeFichero(HttpServletRequest request);
	
	
}
