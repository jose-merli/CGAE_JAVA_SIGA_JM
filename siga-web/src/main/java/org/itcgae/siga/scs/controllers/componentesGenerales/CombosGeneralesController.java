package org.itcgae.siga.scs.controllers.componentesGenerales;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTO.scs.BaremosGuardiaItem;
import org.itcgae.siga.DTO.scs.BaremosRequestDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.scs.ComboColaOrdenadaDTO;
import org.itcgae.siga.scs.services.componentesGenerales.ComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/combo")
public class CombosGeneralesController {

	@Autowired
	private ComboService comboService;

	@GetMapping("/materias")
	public ResponseEntity<ComboDTO> comboMaterias(String area, String dataFilter, HttpServletRequest request) {
		ComboDTO response = comboService.comboMaterias(request, area, dataFilter);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/areas")
	public ResponseEntity<ComboDTO> comboAreas(HttpServletRequest request) {
		ComboDTO response = comboService.comboAreas(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/tipoGuardia")
	public ResponseEntity<ComboDTO> comboTipoGuardia(HttpServletRequest request) {
		ComboDTO response = comboService.comboTiposGuardia(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/tipoTurno")
	public ResponseEntity<ComboDTO> comboTipoTurno(HttpServletRequest request) {
		ComboDTO response = comboService.comboTiposTurno(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/turnos")
	public ResponseEntity<ComboDTO> comboTurnos(HttpServletRequest request) {
		ComboDTO response = comboService.comboTurnos(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/turnosDesignacion")
	public ResponseEntity<ComboDTO> comboTurnosDesignacion(HttpServletRequest request) {
		ComboDTO response = comboService.comboTurnosDesignacion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/turnosInscritos")
	public ResponseEntity<ComboDTO> comboTurnosInscritoLetrado(HttpServletRequest request,
			@RequestParam String idPersona) {
		ComboDTO response = comboService.comboTurnosInscritoLetrado(request, idPersona);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/grupoFacturacion")
	public ResponseEntity<ComboDTO> comboGrupoFacturacion(HttpServletRequest request) {
		ComboDTO response = comboService.getComboGrupoFacturacion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/partidasPresupuestarias")
	public ResponseEntity<ComboDTO> comboPartidasPresupuestarias(HttpServletRequest request, String importe) {
		ComboDTO response = comboService.getComboPartidasPresupuestarias(request, importe);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/subzonas")
	public ResponseEntity<ComboDTO> comboSubZona(String zona, HttpServletRequest request) {
		ComboDTO response = comboService.getComboSubZonas(request, zona);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/zonas")
	public ResponseEntity<ComboDTO> comboZonas(HttpServletRequest request) {
		ComboDTO response = comboService.getComboZonas(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/jurisdicciones")
	public ResponseEntity<ComboDTO> comboJurisdicciones(HttpServletRequest request) {
		ComboDTO response = comboService.getJurisdicciones(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/ordenCola")
	ResponseEntity<ComboColaOrdenadaDTO> ordenCola(HttpServletRequest request, String idordenacioncolas) {

		ComboColaOrdenadaDTO response = comboService.ordenCola(request, idordenacioncolas);
		if (response.getError() == null)
			return new ResponseEntity<ComboColaOrdenadaDTO>(response, HttpStatus.OK);
		else
			return new ResponseEntity<ComboColaOrdenadaDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/comboTipoEjg")
	public ResponseEntity<ComboDTO> comboTipoEjg(HttpServletRequest request) {
		ComboDTO response = comboService.comboTipoEjg(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/comboTipoEjgColegio")
	public ResponseEntity<ComboDTO> comboTipoEjgColegio(HttpServletRequest request) {
		ComboDTO response = comboService.comboTipoEjgColegio(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/comboEstadoEjg")
	public ResponseEntity<ComboDTO> comboEstadoEjg(HttpServletRequest request) {
		ComboDTO response = comboService.comboEstadoEjg(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/guardias")
	public ResponseEntity<ComboDTO> comboGuardias(HttpServletRequest request, String idTurno) {
		ComboDTO response = comboService.comboGuardias(request, idTurno);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/guardiasNoBaja")
	public ResponseEntity<ComboDTO> comboGuardiasNoBaja(HttpServletRequest request, String idTurno) {
		ComboDTO response = comboService.comboGuardiasNoBaja(request, idTurno);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/guardiasInscritas")
	public ResponseEntity<ComboDTO> comboGuardiasInscritas(HttpServletRequest request, @RequestParam String idTurno,
			@RequestParam String idPersona) {
		ComboDTO response = comboService.comboGuardiasInscritoLetrado(request, idPersona, idTurno);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/guardiasNoGrupo")
	public ResponseEntity<ComboDTO> comboGuardiasNoGrupo(HttpServletRequest request, String idTurno) {
		ComboDTO response = comboService.comboGuardiasNoGrupo(request, idTurno);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/guardiasGrupo")
	public ResponseEntity<ComboDTO> comboGuardiasGrupo(HttpServletRequest request, String idTurno) {
		ComboDTO response = comboService.comboGuardiasGrupo(request, idTurno);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/comboTipoDesignacion")
	public ResponseEntity<ComboDTO> comboTipoDesignacion(HttpServletRequest request) {
		ComboDTO response = comboService.comboTipoDesignacion(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	// Combo Tipo SOJ
	@GetMapping("/comboTipoSOJ")
	public ResponseEntity<ComboDTO> comboTipoSOJ(HttpServletRequest request) {
		ComboDTO response = comboService.comboTipoSOJ(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	// Combo Tipo SOJ Colegio
	@GetMapping("/comboTipoSOJColegio")
	public ResponseEntity<ComboDTO> comboTipoSOJColegio(HttpServletRequest request) {
		ComboDTO response = comboService.comboTipoSOJColegio(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	// Combo Tipo Consulta
	@GetMapping("/comboTipoConsulta")
	public ResponseEntity<ComboDTO> comboTipoConsulta(HttpServletRequest request) {
		ComboDTO response = comboService.comboTipoConsulta(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	// Combo Tipo Resuelta
	@GetMapping("/comboTipoRespuesta")
	public ResponseEntity<ComboDTO> comboTipoRespuesta(HttpServletRequest request) {
		ComboDTO response = comboService.comboTipoRespuesta(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/comboComisaria")
	public ResponseEntity<ComboDTO> comboComisaria(HttpServletRequest request) {
		ComboDTO response = comboService.comboComisaria(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/comboJuzgado")
	public ResponseEntity<ComboDTO> comboJuzgado(HttpServletRequest request) {
		ComboDTO response = comboService.comboJuzgado(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/comboComisariaCdgoExt")
	public ResponseEntity<ComboDTO> comboComisariaCdgoExt(HttpServletRequest request) {
		ComboDTO response = comboService.comboComisariaCdgoExt(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/comboJuzgadoCdgoExt")
	public ResponseEntity<ComboDTO> comboJuzgadoCdgoExt(HttpServletRequest request) {
		ComboDTO response = comboService.comboJuzgadoCdgoExt(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/listasguardias")
	public ResponseEntity<ComboDTO> comboListasGuardias(HttpServletRequest request, String idTurno) {
		ComboDTO response = comboService.comboListasGuardias(request, idTurno);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/conjuntoguardias")
	public ResponseEntity<ComboDTO> comboConjuntoGuardias(HttpServletRequest request) {
		ComboDTO response = comboService.comboConjuntoGuardias(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/estado")
	public ResponseEntity<ComboDTO> comboEstados(HttpServletRequest request) {
		ComboDTO response = comboService.comboEstados(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	/// POST
	@RequestMapping(value = "/comboJuzgadoDesignaciones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ComboDTO> searchBaremosGuardia(@RequestBody String idJuzgado, HttpServletRequest request) {
		ComboDTO response = comboService.comboJuzgadoDesignaciones(request, idJuzgado);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	/*
	 * @GetMapping("/comboJuzgadoDesignaciones") public ResponseEntity<ComboDTO>
	 * comboJuzgadoDesignaciones(HttpServletRequest request) { ComboDTO response =
	 * comboService.comboJuzgadoDesignaciones(request); return new
	 * ResponseEntity<ComboDTO>(response, HttpStatus.OK); }
	 */

	@GetMapping("/comboTipoDocAsistencia")
	public ResponseEntity<ComboDTO> comboTipoDocAsistencia(HttpServletRequest request) {
		ComboDTO response = comboService.comboTipoDocAsistencia(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/comboAsociadoAsistencia")
	public ResponseEntity<ComboDTO> comboAsociadoAsistencia(HttpServletRequest request,
			@RequestParam String anioNumero) {
		ComboDTO response = comboService.comboAsociadoAsistencia(request, anioNumero);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/comboCosteFijoActuacionAsistencia")
	public ResponseEntity<ComboDTO> comboCosteFijoActuacionAsistencia(HttpServletRequest request,
			@RequestParam String anioNumero, @RequestParam String idTipoActuacion) {
		ComboDTO response = comboService.comboCosteFijo(request, anioNumero, idTipoActuacion);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/comboTipoActuacionAsistencia")
	public ResponseEntity<ComboDTO> comboTipoActuacionAsistencia(HttpServletRequest request,
			@RequestParam(required = false) String anioNumero,
			@RequestParam(required = false) String idTipoAsistencia) {
		ComboDTO response = comboService.comboTipoActuacionAsistencia(request, anioNumero, idTipoAsistencia);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping("/comboOrigenContacto")
	public ResponseEntity<ComboDTO> comboOrigenContacto(HttpServletRequest request) {
		ComboDTO response = comboService.comboOrigenContacto(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	/**
	 * 
	 * @param idInstitucion
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/comboJuzgadoPorInstitucion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ComboDTO> comboJuzgadoPorInstitucion(@RequestBody String idInstitucion,
			HttpServletRequest request) {
		ComboDTO response = comboService.comboJuzgadoPorInstitucion(idInstitucion, request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/comboAcreditacionesPorModulo")
	public ResponseEntity<ComboDTO> comboAcreditacionesPorModulo(@RequestParam("idModulo") String idModulo,
			@RequestParam("idTurno") String idTurno, HttpServletRequest request) {
		ComboDTO response = comboService.comboAcreditacionesPorModulo(request, idModulo, idTurno);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/comboTipoDocumentacionDesigna")
	public ResponseEntity<ComboDTO> comboTipoDocumentacionDesigna(HttpServletRequest request) {
		ComboDTO response = comboService.comboTipoDocumentacionDesigna(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/comboEstadosAsistencia")
	public ResponseEntity<ComboDTO> comboEstadosAsistencia(HttpServletRequest request) {
		ComboDTO response = comboService.comboEstadosAsistencia(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/comboProcedimientosEXEA")
	public ResponseEntity<ComboDTO> comboProcedimientosEXEA(HttpServletRequest request) {
		ComboDTO response = comboService.comboProcedimientosEXEA(request);
		return new ResponseEntity<ComboDTO>(response, HttpStatus.OK);
	}

}
