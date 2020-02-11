package org.itcgae.siga.commons.constants;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class SigaEndPointsProcess {

	public static final Map<String, String> PROCESS = new HashMap<String, String>() {
		{
			put("busquedaLetrados","11");
			put("busquedaColegiados","1");
			put("busquedaNoColegiados","2");
			put("searchNoColegiados","22");
			put("certificadosAca","130");
			put("mantenimientoDuplicados","18");
			put("comisionesCargos","48");
			put("solicitudesModificacion","1");
			put("solicitudesIncorporacion","3");
			put("mantenimientoSufijos","214");
			put("facturaPlantillas","213");
			put("gestionCuentasBancarias","281");
			put("seriesFactura","200");
			put("previsionesFactura","2A2");
			put("mantenimientoFactura","241");
			put("facturas","220");
			put("ficherosAdeudos","261");
			put("ficherosDevoluciones","2C1");
			put("devolucionManual","2C2");
			put("abonos","2G0");
			put("ficherosTransferencia","2G4");
			put("contabilidad","250");
			put("cobrosRecobros","2F0");
			put("facturasEmitidas","2A1");
			put("categoriasProducto","400");
			put("categoriasServicios","401");
			put("mantenimientoProductos","34");
			put("mantenimientoServicios","35");
			put("gestionarSolicitudes","36");
			put("solicitudCompraSubscripcion","37");
			put("cargaCompras","38");
			put("solicitudAnulacion","40");
			put("tiposExpedientes","41");
			put("gestionarExpedientes","42");
			put("alertas","44");
			put("nuevoExpediente","46");
			put("busquedaSanciones","566");
            put("detalleSancion","566");
			put("fichaColegial","12");
			put("modificacionDatos","13");
			put("cargasPeriodicas","161");
			put("mediadores","164");
			put("comunicacionInterprofesional","64");
			put("solicitarCompra","63");
			put("solicitudCertificados","60");
			put("documentacionSolicitudes","10");
			put("mantenimientoGruposFijos","993");
			put("gestionSubtiposCV","15E");
			put("informacionGestionSubtipoCV","15H");
			put("cargasMasivas","15G");
			put("mantenimientoMandatos","19");
			put("gestionSolicitudes","61");
			put("configurarPerfil","172");
			put("mantenimientoCertificados","62");
			put("catalogos","99B");
			put("usuarios","83");
			put("perfiles","82");
			put("contadores/3/censo","112");
			put("zonasYsubzonas","911");
			put("areasYMaterias","912");
			put("partidas","113");
			put("partidosJudiciales","91C");
			put("retencionesIRPF","92S");
			put("maestrosModulos","91D");
			put("calendarioLaboral","938");
			put("mantenimientoprocuradores","91F");
			put("mantenimientoPrisiones","91G");
			put("mantenimientoComisarias","91H");
			put("mantenimientoJuzgados","91I");
			put("turnos","913");
			put("documentacionEJG","91J");
			put("maestroPJ","992");
			put("guardiasSolicitudesTurnos","74S");
			put("guardiasIncompatibilidades","914");
			put("programacionCalendarios","997");
			put("guardiasBajasTemporales","75S");
			put("guardiasSaltosCompensaciones","76S");
			put("definirListasGuardias","91B");
			put("solicitudesTurnosGuardias","922");
			put("guardiasAsistencias","952");
			put("guardiasAceptadasCentralita","956");
			put("bajasTemporales","9Z5");
			put("soj","941");
			put("ejg","946");
			put("volanteExpres","11A");
			put("gestionActas","947");
			put("saltosYCompensaciones","93N");
			put("inscripciones","96M");
			put("designaciones","959");
			put("facturacionesYPagos","600");
			put("mantenimientoPagos","621");
			put("movimientosVarios","608");
			put("tramosLEC","623");
			put("retencionesJudiciales","611");
			put("destinatariosRetenciones","996");
			put("tiposAsistencia","99J");
			put("costesFijos","915");
			put("fundamentosResolucion","917");
			put("fundamentosCalificacion","921");
			put("calendarioLaboralAgenda","939");
			put("procedimientos","91P");
			put("tiposActuacion","99F");
			put("busquedaRetencionesAplicadas","617");
			put("generarImpreso190","613");
			put("resumenPagos","953");
			put("justificacionLetrado","995");
			put("informeFacturacion","96E");
			put("informeFacturacionMultiple","96G");
			put("informeFacturacionPersonalizado","96H");
			put("fichaFacturacion","96A");
			put("fichaPago","96B");
			put("cartaPagosColegiados","96C");
			put("cartaFacturaColegiado","96I");
			put("certificadosPagos","96D");
			put("certificadosIrpf","96F");
			put("comunicaPreparacion","12B");
			put("comunicaRemesaEnvio","12C");
			put("envioReintegrosXunta","12U");
			put("comunicaRemesaResultado","12Z");
			put("comunicaEnvioActualizacion","12W");
			put("comunicaInfoEconomica","12Y");
			put("comunicaCarga","12X");
			put("comunicaResoluciones","12F");
			put("recuperarConsultas","107");
			put("comunicaDesignaciones","12G");
			put("consultasListasDinamicas","18a");
			put("nuevaConsulta","108");
			put("nuevaConsultaExperta","106");
			put("definirTipoPlantilla","74");
			put("listaCorreos","72");
			put("bandejaSalida","73");
			put("bandejaEntrada","75");
			put("informesGenericos","I00");
			put("buscarCursos","20A");
			put("busquedaCensoGeneral","1c");
			put("buscarInscripciones","20B");
			put("contadores/11/formacion","112");
			put("agenda","21A");
			put("modelosComunicaciones","30A");
			put("plantillasEnvio","30B");
			put("consultas","30C");
			put("comunicaciones","30D");
			put("enviosMasivos","30E");
			put("gestionEntidad","80");
			put("permisos","84");
			put("auditoriaUsuarios","18");
			put("parametrosGenerales","17");
			put("contadores","112");
			put("guardias","916");
			put("catalogosMaestros","78");
			put("justiciables","89");
			put("cartaFacturacionPago","620");
			put("politicaCookies","0");
			put("busquedaGeneral","1c");
			put("fichaPersonaJuridica","22");
			put("mutualidadSeguroAccidentes","225");
			put("edicionCurriculares","12");
			put("editarUsuario","83");
			put("MutualidadAbogaciaPlanUniversal","224");
			put("solicitudesEspecificas","5");
			put("alterMutua","226");
			put("alterMutuaReta","22A");
			put("nuevaIncorporacion","47");
			put("detalleIntegrante","22");
			put("nuevaSolicitudesModificacion","1");
			put("fichaNotario","22");
			put("datosBancarios","123");
			put("consultarDatosBancarios","12K");
			put("datosDirecciones","122");
			put("consultarDatosDirecciones","15F");
			put("datosCv","124");
			put("cargaEtiquetas","15G");
			put("programarFactura","280");
			put("generarFactura","240");
			put("eliminarFactura","242");
			put("facturasSociedad","0");
			put("comunicacionesSociedades","0");
			put("guardiasIncompatibilidades","919");
			put("programacionCalendarios","997");
			put("guardiasBajasTemporales","92D");
			put("guardiasSaltosCompensaciones","76S");
			put("mantenimientoFacturacion","9SH");
			put("previsiones","2A2");
			put("comunicacionesCenso","0");
			put("expedientesCenso","127");
			put("regTel","222");
			put("turnoOficioCenso","0");
			put("auditoria","18");
			put("fichaPlantilla","30B");
			put("fichaModeloComunicaciones","30A");
			put("fichaPlantillaDocumento","30A");
			put("fichaConsulta","30C");
			put("fichaRegistroComunicacion","30A");
			put("fichaRegistroEnvioMasivo","30E");
			put("editarCalendario","21A");
			put("editarNotificacion","21A");
			put("EditarCatalogosMaestros","0");
			put("contadores","112");
			put("gestionContadores","112");
			put("EditarPerfiles","132");
			put("gruposUsuarios","82");
			put("seleccionarIdioma","79");
			put("fichaCurso","20A");
			put("fichaInscripcion","20B");
			put("devolucion","02C");
			put("justificacion","0");
			put("dialogoComunicaciones","0");

		}
	};

}
