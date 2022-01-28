package org.itcgae.siga.commons.constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class SigaConstants {

	public static String ACUERDO = "Acuerdo";
	public static String FIN = "Fin";
	public static String FIRMEZA = "Firmeza";
	public static String IMPOSICION = "Imposicion";
	public static String INICIO = "Inicio";
	public static String REHABILITADO = "Rehabilitado";
	public static String RESOLUCION = "Resolucion";
	
	public static String COMBO_INSTITUCIONES = "instituciones";

	public static String COMBO_PERFILES = "perfiles";
	public static String TIPO_PERSONA_NOTARIO = "Notario";
	
	public static Integer IdUsuarioPuertaAtras = 1;
	public static String OK = "OK";
	public static String KO = "KO";

	public static Integer CODE_200 = 200; 
	public static Integer CODE_400 = 400; 
	
	public static String MAX_SIZE_FILE = "10485760";
	public static String InstitucionGeneral = "2000";
	public static String Personal = "Personal";

	public static String DB_FALSE = "0";
	public static String DB_TRUE = "1";
	public static final String IP_ACCESO_SERVICIO_CARGAS = "IP_ACCESO_SERVICIO_CARGAS";
	public static final String ACTIVAR_CLIENTE_SERVICIO_CARGAS = "ACTIVAR_CLIENTE_SERVICIO_CARGAS";
	public static final String DESFASE_PROGRAMACION_ENVIO_MINUTOS = "DESFASE_PROGRAMACION_ENVIO_MINUTOS";
	public static final String NUM_MAXIMO_MODELOS_SELECCIONADOS = "dialogo.modelocomunicacion.seleccionMax";
	public static final String TINY_APIKEY = "comunicaciones.tinyapikey";
	public static final String FICHERO_SIGA = "SIGA";
	public static final String LENGUAJE_DEFECTO = "1";
	
	// DOCUMENTOS
	
	public static String SUFIJO_MODULO_COM_DUPLICADO = "_Copia";
	public static String SUFIJO_CONSULTA_COM_DUPLICADO = "_Copia";
	public static int NOMBRE_MAXLENGTH = 100;
	/*public static String rutaficherosInformesYcomunicaciones = "/Datos/SIGA/ficheros/comunicaciones/";
	public static String rutaExcelConsultaTemp= "/Datos/SIGA/ficheros/temporal/";
	public static String carpetaDocumentosEnvio = "/documentosEnvio/";
	public static String carpetaPlantillasDocumento = "/plantillaDocumentos/";*/
	public static String carpetaDocumentosEnvio = "documentosEnvio";
	public static String carpetaTmp = "temp";
	public static String parametroRutaPlantillas = "informes.directorioFisicoPlantillaInformesJava";
	public static String parametroRutaSalidaInformes = "informes.directorioFisicoSalidaInformesJava";
	public static String rutaPlantillaSinClase = "plantillasSinClase";
	public static String parametroSizePlantillas = "gen.ficheros.maxsize.bytes";
	
	public static String pathAbsolutoFiler = "path.absoluto.origenFiler"; 
	public static String pathRelativoTemp = "path.relativo.temporalesDescarga"; 

	public static String pathSeparator = "/";
	
	public static String nombreExcelConsulta = "ResultadoConsulta";
	public static String nombreZip = "DocumentosComunicacion";
	public static String rutaLicencia = "/WEB-INF/Aspose.Words.lic";
	
	public static String REPLACECHAR_PREFIJO_SUFIJO = "%%";
	public static String CLAVES_QUERY = "CLAVES";
	public static String WHERE_VALUE = "whereValue";
	public static String CAMPO_NOMBRE = "NOMBRE";
	public static String CAMPO_APELLIDOS = "APELLIDOS";
	public static String CAMPO_APELLIDO1 = "APELLIDO1";
	public static String CAMPO_APELLIDO2 = "APELLIDO2";
	public static String CAMPO_NUM_COLEGIADO = "NCOLEGIADO";
	public static String CAMPO_IDENTIFICACION = "INDENTIFICACIONENTIDAD";
	public static String CAMPO_IDINSTITUCION = "IDINSTITUCION";
	public static String CAMPO_CP = "CP";
	public static String IDINSTITUCION_0 = "0";
	public static Short IDINSTITUCION_0_SHORT = Short.valueOf(IDINSTITUCION_0);
	public static String SI = "SI";
	public static String NO = "NO";
	
	public static String minutosRevisionEnviosProcesando = "minutos.revision.envios.procesando"; 
	
	// Para la busqueda de criterios dinamicos de las consultas
	public static final String TIPONUMERO	= "N";
	public static final String TIPOTEXTO	= "A";
	public static final String TIPOFECHA	= "D";
	public static final String TIPOFECHANULA	= "X";
	public static final String TIPOMULTIVALOR		= "MV";
	public static final String TIPOENVIO		= "E";
	
	public static final String ETIQUETATIPONUMERO	= "%%NUMERO%%";
	public static final String ETIQUETATIPOTEXTO	= "%%TEXTO%%";
	public static final String ETIQUETATIPOFECHA	= "%%FECHA%%";
	public static final String ETIQUETATIPOMULTIVALOR		= "%%MULTIVALOR@";
	public static final String ETIQUETATIPOENVIO		= "%%TIPOENVIO%%";
	public static final String ETIQUETAOPERADOR   	= "%%OPERADOR%%";
	
	public static final String NOMBRETIPOENVIO		= "TIPO ENVIO";
	public static final String IS_NULL		= "IS NULL";
	public static final String LIKE		= "LIKE";
	public static final String NOMBRETABLA_CEN_CLIENTE = "CEN_CLIENTE";
	public static final String NOMBRETABLA_CEN_COLEGIADO = "CEN_COLEGIADO";
	
	public static final String ALIASIDPERSONA = "IDPERSONA";
	public static final String ALIASCORREO = "CORREOELECTRONICO";
	public static final String ALIASDOMICILIO = "DOMICILIO";
	public static final String ALIASMOVIL = "MOVIL";
	public static final String ALIASCODIGOPOSTAL = "CODIGOPOSTAL";
	public static final String ALIASIDPAIS = "IDPAIS";
	public static final String ALIASIDPOBLACION = "IDPOBLACION";
	public static final String ALIASIDPROVINCIA = "IDPROVINCIA";
	public static final String ALIASPOBLACIONEXTRANJERA = "POBLACIONEXTRANJERA";
	
	public static final String ECOS_PREFIJO_ESPANA = "(+34)";
	public static final long ID_OBJETIVO_DESTINATARIOS = 1;
	
	// Tipos de Cambio ColaCambioLetrado
	public static final int COLA_CAMBIO_LETRADO_APROBACION_COLEGIACION = 10;
	public static final int COLA_CAMBIO_LETRADO_ACTIVACION_RESIDENCIA  = 20;
	public static final int COLA_CAMBIO_LETRADO_MODIFICACION_DIRECCION = 30;
	public static final int COLA_CAMBIO_LETRADO_BORRADO_DIRECCION = 40;
	public static final int COLA_CAMBIO_LETRADO_LOPD = 50;
	
	//estados envio
	public static final Short ENVIO_PENDIENTE_MANUAL = 1;
	public static final Short ENVIO_PROCESADO = 2;
	public static final Short ENVIO_PROCESADO_CON_ERRORES = 3;
	public static final Short ENVIO_PENDIENTE_AUTOMATICO = 4;
	public static final Short ENVIO_PROCESANDO = 5;
	public static final Short ENVIO_ARCHIVADO = 6;
	
	//tipos envio
	public static final String ID_ENVIO_MAIL = "1";
	public static final String ID_ENVIO_CORREO_ORDINARIO = "2";
	public static final String ID_ENVIO_SMS = "4";
	public static final String ID_ENVIO_BURO_SMS = "5";
	public static final String ID_ENVIO_DOCUMENTACION_LETRADO = "7";
	
	// Tipo Campos envio
	public static final String ID_CAMPO_ASUNTO = "1";
	public static final String ID_CAMPO_CUERPO = "2";
	public static final String ID_CAMPO_TEXTO_SMS = "1";
	public static final String ID_TIPO_CAMPO_EMAIL = "E";
	public static final String ID_TIPO_CAMPO_SMS = "S";
	
	// Etiquetas para envios
	public static final String ETIQUETA_DEST_TRATAMIENTO = "TRATAMIENTO";
	public static final String ETIQUETA_DEST_NOMBRE = "NOMBRE";
	public static final String ETIQUETA_DEST_APELLIDO1 = "APELLIDO1";
	public static final String ETIQUETA_DEST_APELLIDO2 = "APELLIDO2";
	public static final String ETIQUETA_DEST_CIFNIF = "CIFNIF";

	public static final String ETIQUETA_IDENVIO = "IDENVIO";
	public static final String ETIQUETA_FECHAACTUAL = "FECHAACTUAL";

	public static final String MARCAS_ETIQUETAS_REEMPLAZO_TEXTO_ANTIGUO = "%%";
	public static final String MARCAS_ETIQUETAS_REEMPLAZO_TEXTO = "##";
	
	//PFD
	public static final String FIRMA_OK="FIRMA_OK";
	public static final String SOLICITUD_DOCUMENTO_OK = "SOLICITUD_DOCUMENTO_OK";
	public static final String SOLICITUD_DOCUMENTO_KO = "SOLICITUD_DOCUMENTO_KO";
	public static final String URL_HTTP_NO_ENCONTRADA = "URL_HTTP_NO_ENCONTRADA";
	public static final String PFD_URLWS = "PFD_URLWS";
	public static final String PFD_FIRMA_VISIBLE = "PFD_FIRMA_VISIBLE";
	public static final String PFD_FIRMA_RAZON = "PFD_FIRMA_RAZON";
	public static final String PFD_FIRMA_LOCATION = "PFD_FIRMA_LOCATION";
	public static final String PFD_IDCLIENTE = "PFD_IDCLIENTE";
	public static final String SMS_URL_SERVICE = "SMS_URL_SERVICE";
	public static final String SMS_CLIENTE_ECOS = "SMS_CLIENTE_ECOS";
	public static final String NOMBRE_FICHERO_BUROSMS = "Justificante_BUROSMS.pdf";

	public static final String MODULO_SCS = "SCS";
	public static final String MODULO_ADM = "ADM";
	public static final String MODULO_ENV = "ENV";
	public static final String MODULO_GEN = "GEN";
	
	public static final String TIPO_CEN_PERSONA = "CEN_PERSONA";
	
	public static final String SENTENCIA_UPDATE = "UPDATE";
	public static final String SENTENCIA_DELETE = "DELETE";
	public static final String SENTENCIA_DROP = "DROP";
	public static final String SENTENCIA_INSERT = "INSERT";
	public static final String SENTENCIA_CREATE = "CREATE";
	public static final String SENTENCIA_ALTER = "ALTER";
	
	public static final String ID_CLASE_CONSULTA_GENERICA = "5";
	
	public static final String MODULO_CENSO = "CEN";
	public static final String PARAMETRO_CONTADOR_UNICO = "CONTADOR_UNICO_NCOLEGIADO_NCOMUNIT";
	

	public enum ERROR_SERVER {
		XML_NO_VALIDO(null), CLI_NOAUTORIZADO(
				"La IP recibida en la petición no está autorizada."), CLI_IP_NO_ENCONTRADA(
						"La ip no se ha encontrado."), CLI_NOVALIDO(
								"Esquema de petición datos no válido."), CLI_NOACTIVO(
										"El colegio que realiza la petición no tiene activo el servicio."), CLI_CODIGO_NO_EXISTE(
												"El código del colegio no existe."), CLI_VERSION_INCORRECTA(
														"La versión recibida no existe o no es la correspondiente al esquema utilizado."), CLI_ERROR_DATOS_INTERNO(
																"Los datos que se reciben no son válidos para enviar una respuesta correcta en el servicio."), CLI_OTROS_ERRORES_INTERNOS(
																		"Error de causas externas."), CLI_COLEGIO_NULO(
																				"El colegio no puede ser nulo."), CLI_PAGINA_YA_INSERTADA(
																						"La página ya ha sido insertada para esa carga"), CLI_CAMPO_NO_VALIDO(
																								"La longitud es superior a la máxima permitida"), CLI_ORDEN_FECHAS_INCORRECTO(
																										"La fecha hasta no puede ser menor a la fecha desde"), CLI_HORA_EJECUCION_NO_PERMITIDO(
																												"No está permitida la ejecución del servicio a esta hora"), CLI_COLEGIO_NO_EXISTE(
																														"Alguno de los colegios no existe en el sistema"), CLI_INCONCORDANCIA_EN_PAGINAS(
																																"El número de página no puede ser superior al total de páginas.");

		private String mensajeError = null;

		private ERROR_SERVER(String mensajeError) {
			this.mensajeError = mensajeError;
		}

		public String getMensajeError() {
			return mensajeError;
		}
	}
	
	public static enum ECOM_CEN_ROLES {
		ABOGADO("ABO","ABOGADO","Abogado")
		,NOEJERCIENTE("CNE","COLEGIADO_NO_EJERCIENTE","Colegiado No Ejerciente")
		,PERSONAL("PER","PERSONAL","Personal")
		,DECANO("DEC","DECANO","Decano")
		,MIEMBROJUNTA("MJU","MIEMBRO_JUNTA","Miembro de Junta")
		,CONSEJERO("CON","CONSEJERO","Consejero")
		,DIRECTIVO("DIR","DIRECTIVO","Directivo")
		,INSCRITO("INS","ABOGADO_INSCRITO","Abogado inscrito")
		,ABOGADOEUROPEO("CCB","ABOGADO_ADVOCAT_AVOGADO_ABOKATU","Abogado Advocat Avogado Abokatu")
		,ADMINISTRADOR("ADM","ADMINISTRADOR", "Administrador")
		,ADMINISTRADORUNICO("ADMUNI","ADMINISTRADOR_UNICO", "Administrador Único")
		,ADMINISTRADORSOLIDARIO("ADMSOL","ADMINISTRADOR_SOLIDARIO","Administrador Solidario")
		,AUTORIZADO("AUT","AUTORIZADO","Autorizado")
		,REPRESENTANTELEGAL("REP","RESPRESENTANTE_LEGAL","Representante Legal")
		,REPRESENTANTEVOLUNTARIO("REPVOL","RESPRESENTANTE_VOLUNTARIO","Representante Voluntario")
		,SECRETARIO("SEC","SECRETARIO","Secretario")
		,VICEDECANO("VICDEC","VICEDECANO","Vicedecano")
		,SIGAADMIN("SAD","SIGA-Admin","SIGA-Admin");
		
		private String codigo = null;
		private String recurso = null;
		private String descripcion = null;
		
		private ECOM_CEN_ROLES(String codigo, String recurso, String descripcion) {
			this.codigo = codigo;
			this.recurso = recurso;
			this.descripcion = descripcion;
		}
		public String getCodigo() {
			return codigo;
		}
		public String getRecurso() {
			return recurso;
		}
		public String getDescripcion() {
			return descripcion;
		}
		
	}
	
	public static String getTipoUsuario(String rol) {
		if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.PERSONAL.getDescripcion()) ||
				rol.equalsIgnoreCase(ECOM_CEN_ROLES.PERSONAL.getRecurso())) {
			return ECOM_CEN_ROLES.PERSONAL.getCodigo();
		}else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.ABOGADO.getDescripcion()) ||
				rol.equalsIgnoreCase(ECOM_CEN_ROLES.ABOGADO.getRecurso())) {
			return ECOM_CEN_ROLES.ABOGADO.getCodigo();
		}else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.NOEJERCIENTE.getDescripcion()) || 
				rol.equalsIgnoreCase(ECOM_CEN_ROLES.NOEJERCIENTE.getRecurso())) {
			return ECOM_CEN_ROLES.NOEJERCIENTE.getCodigo();
		}else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.CONSEJERO.getDescripcion()) ||
				rol.equalsIgnoreCase(ECOM_CEN_ROLES.CONSEJERO.getRecurso())) {
			return ECOM_CEN_ROLES.CONSEJERO.getCodigo();
		}else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.DECANO.getDescripcion()) ||
				rol.equalsIgnoreCase(ECOM_CEN_ROLES.DECANO.getRecurso())) {
			return ECOM_CEN_ROLES.DECANO.getCodigo();
		}else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.DIRECTIVO.getDescripcion()) ||
				rol.equalsIgnoreCase(ECOM_CEN_ROLES.DIRECTIVO.getRecurso())) {
			return ECOM_CEN_ROLES.DIRECTIVO.getCodigo();
		}else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.MIEMBROJUNTA.getDescripcion()) ||
				rol.equalsIgnoreCase(ECOM_CEN_ROLES.MIEMBROJUNTA.getRecurso())) {
			return ECOM_CEN_ROLES.MIEMBROJUNTA.getCodigo();
		}else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.SECRETARIO.getDescripcion()) ||
				rol.equalsIgnoreCase(ECOM_CEN_ROLES.SECRETARIO.getRecurso())) {
			return ECOM_CEN_ROLES.SECRETARIO.getCodigo();
		}else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.VICEDECANO.getDescripcion()) ||
				rol.equalsIgnoreCase(ECOM_CEN_ROLES.VICEDECANO.getRecurso())) {
			return ECOM_CEN_ROLES.VICEDECANO.getCodigo();
		}else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.ABOGADOEUROPEO.getDescripcion()) ||
				rol.equalsIgnoreCase(ECOM_CEN_ROLES.ABOGADOEUROPEO.getRecurso())) {
			return ECOM_CEN_ROLES.ABOGADOEUROPEO.getCodigo();
		}else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.INSCRITO.getDescripcion()) ||
				rol.equalsIgnoreCase(ECOM_CEN_ROLES.INSCRITO.getRecurso())) {
			return ECOM_CEN_ROLES.INSCRITO.getCodigo();
		}else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.ADMINISTRADOR.getDescripcion()) ||
				rol.equalsIgnoreCase(ECOM_CEN_ROLES.ADMINISTRADOR.getRecurso())) {
			return ECOM_CEN_ROLES.ADMINISTRADOR.getCodigo();
		}else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.ADMINISTRADORSOLIDARIO.getDescripcion()) ||
				rol.equalsIgnoreCase(ECOM_CEN_ROLES.ADMINISTRADORSOLIDARIO.getRecurso())) {
			return ECOM_CEN_ROLES.ADMINISTRADORSOLIDARIO.getCodigo();
		}else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.ADMINISTRADORUNICO.getDescripcion()) ||
				rol.equalsIgnoreCase(ECOM_CEN_ROLES.ADMINISTRADORUNICO.getRecurso())) {
			return ECOM_CEN_ROLES.ADMINISTRADORUNICO.getCodigo();
		}else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.AUTORIZADO.getDescripcion()) ||
				rol.equalsIgnoreCase(ECOM_CEN_ROLES.AUTORIZADO.getRecurso())) {
			return ECOM_CEN_ROLES.AUTORIZADO.getCodigo();
		}else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.REPRESENTANTELEGAL.getDescripcion()) ||
				rol.equalsIgnoreCase(ECOM_CEN_ROLES.REPRESENTANTELEGAL.getRecurso())) {
			return ECOM_CEN_ROLES.REPRESENTANTELEGAL.getCodigo();
		}else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.REPRESENTANTEVOLUNTARIO.getDescripcion()) ||
				rol.equalsIgnoreCase(ECOM_CEN_ROLES.REPRESENTANTEVOLUNTARIO.getRecurso())) {
			return ECOM_CEN_ROLES.REPRESENTANTEVOLUNTARIO.getCodigo();
		}else if (rol.equalsIgnoreCase(ECOM_CEN_ROLES.SIGAADMIN.getDescripcion()) ||
				rol.equalsIgnoreCase(ECOM_CEN_ROLES.SIGAADMIN.getRecurso())) {
			return ECOM_CEN_ROLES.SIGAADMIN.getCodigo();
		}return "";
	}
	
    // Tipo direcciones que tienen logica asociada
	public static final int TIPO_DIRECCION_CENSOWEB		= 3;
	public static final int TIPO_DIRECCION_DESPACHO		= 2;
	public static final int TIPO_DIRECCION_GUIA	    	= 5;
	public static final int TIPO_DIRECCION_GUARDIA  	= 6;
	public static final int TIPO_DIRECCION_FACTURACION  = 8;
	public static final int TIPO_DIRECCION_TRASPASO_OJ  = 9;
	public static final int TIPO_DIRECCION_PUBLICA		= 4;
	public static final int TIPO_DIRECCION_FORMACION    = 14;
	
	// Estados colegiales
	public static final int ESTADO_COLEGIAL_SINEJERCER 		= 10;
	public static final int ESTADO_COLEGIAL_EJERCIENTE		= 20;
	public static final int ESTADO_COLEGIAL_BAJACOLEGIAL	= 30;
	public static final int ESTADO_COLEGIAL_INHABILITACION	= 40;
	public static final int ESTADO_COLEGIAL_SUSPENSION	= 	  50;
	public static final int ESTADO_COLEGIAL_ALTA	= 	  1020;// Correspnde a una combinacion del estado 10 y 20;
	
	//Tipos de Preferente
	public static final String TIPO_PREFERENTE_CORREO = "C";  
	public static final String TIPO_PREFERENTE_CORREOELECTRONICO = "E";  
	public static final String TIPO_PREFERENTE_FAX = "F";  
	public static final String TIPO_PREFERENTE_SMS = "S"; 
	
	// Tipo cambio histórico de censo
		public static final int TIPO_CAMBIO_HISTORICO_ESTADO_ALTA_COLEGIAL	= 1;
		public static final int TIPO_CAMBIO_HISTORICO_ESTADO_BAJA_COLEGIAL	= 2;
		public static final int TIPO_CAMBIO_HISTORICO_ESTADO_ALTA_EJERCICIO	= 3;
		public static final int TIPO_CAMBIO_HISTORICO_ESTADO_BAJA_EJERCICIO	= 4;
		public static final int TIPO_CAMBIO_HISTORICO_ESTADO_INHABILITACION	= 5;
		public static final int TIPO_CAMBIO_HISTORICO_ESTADO_SUSPENSION		= 6;
		public static final int TIPO_CAMBIO_HISTORICO_DATOS_GENERALES		= 10;
		public static final int TIPO_CAMBIO_HISTORICO_DATOS_COLEGIALES		= 20;
		public static final int TIPO_CAMBIO_HISTORICO_DIRECCIONES			= 30;
		public static final int TIPO_CAMBIO_HISTORICO_CUENTAS_BANCARIAS		= 40;
		public static final int TIPO_CAMBIO_HISTORICO_DATOS_CV				= 50;
		public static final int TIPO_CAMBIO_HISTORICO_DATOS_COMPONENTES		= 60;
		public static final int TIPO_CAMBIO_HISTORICO_DATOS_FACTURACION		= 70;
		public static final int TIPO_CAMBIO_HISTORICO_TURNOS				= 80;
		public static final int TIPO_CAMBIO_HISTORICO_EXPEDIENTES			= 90;
		public static final int TIPO_CAMBIO_HISTORICO_DESIGNACIONMODIFICACION		= 101;
		public static final int TIPO_CAMBIO_HISTORICO_DESIGNACIONJUSTIFICACION		= 102;
		public static final int TIPO_CAMBIO_HISTORICO_DESIGNACIONALTAACTUACION		= 103;
		public static final int TIPO_CAMBIO_HISTORICO_DESIGNACIONMODIFICAACTUACION		= 107;
		public static final int TIPO_CAMBIO_HISTORICO_DESIGNACIONDELETEACTUACION		= 108;
		
		public static final int TIPO_CAMBIO_HISTORICO_ASISTENCIAALTA		= 104;
		public static final int TIPO_CAMBIO_HISTORICO_ASISTENCIAMODIFICACION		= 105;
		public static final int TIPO_CAMBIO_HISTORICO_ASISTENCIAALTAACTUACION		= 106;
		
	
	
	
	public enum ERROR_CLIENT {
		XML_NO_VALIDO(null), SERV_NODISPONIBLE("Servicio no disponible."), SERV_CERTNOAUT(
				"En el caso de comunicaciones con certificado de cliente que el certificado presentado no esté autorizado."), CLI_NOVALIDO(
						"Esquema de petición datos no válido."), CLI_NOACTIVO(
								"El colegio que realiza la petición no tiene activo el servicio."), SERV_FECHANOVALIDO(
										"Para el servidor el rango de fechas solicitado no es válido."), SERV_COLEGIONOVALIDO(
												"Para el servidor el código del colegio recibido no es válido."), SERV_NUMPAGINANOVALIDO(
														"Para el servidor el número de página solicitado no existe o no es la esperada.."), SERV_VERSION_INCORRECTA(
																"La versión recibida no existe o no es la correspondiente al esquema utilizado en la petición."), SERV_OTRO_ERROR(
																		"Cualquier otro error no catalogado."), SERV_NOVALIDO(
																				"La respuesta no tiene un formato válido");

		private String mensajeError = null;

		private ERROR_CLIENT(String mensajeError) {
			this.mensajeError = mensajeError;
		}

		public String getMensajeError() {
			return mensajeError;
		}
	}

	public enum ID_TIPO_CARGA {
		SERV_SOC(new Short("1")), CLI_SOC(new Short("2"));

		private Short tipo = null;

		private ID_TIPO_CARGA(Short tipo) {
			this.tipo = tipo;
		}

		public Short getTipo() {
			return tipo;
		}
	}

	public static enum TIPO_XML {
		PETICION_SERVICIO_CARGAS(new Short("1"), "petición servicio"), RESPUESTA_SERVICIO_CARGAS(new Short("2"),
				"respuesta servicio"), PETICION_CLIENTE_CARGAS(new Short("3"),
						"petición cliente"), RESPUESTA_CLIENTE_CARGAS(new Short("4"),
								"respuesta cliente"), PETICION_SERVICIO_ECOS(new Short("5"),
										"petición servicio ECOS"), RESPUESTA_CLIENTE_ECOS(new Short("6"),
												"respuesta cliente ECOS"), PETICION_WS_PUBLICADOR(new Short("7"),
														"petición servicio"), RESPUESTA_WS_PUBLICADOR(new Short("8"),
																"respuesta servicio"),PETICION_SERVICIO_FUSIONADOR(new Short("9"), "petición servicio"),
																	RESPUESTA_SERVICIO_FUSIONADOR(new Short("10"), "respuesta servicio");

		private final Short codigo;
		private final String descripcion;

		TIPO_XML(Short codigo, String descripcion) {
			this.codigo = codigo;
			this.descripcion = descripcion;
		}

		public Short getCodigo() {
			return codigo;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public static TIPO_XML getEnum(Short codigo) {
			for (TIPO_XML sc : values()) {
				if (sc.getCodigo().shortValue() == codigo.shortValue()) {
					return sc;
				}
			}
			return null;
		}
	}

	public static enum MODULO {
		CARGAS(new Short("1"), "CARGAS"), ADMINISTRACION(new Short("2"), "ADMINISTRACION"), PUBLICACION(new Short("3"),
				"PUBLICACION"),FUSIONADOR(new Short("4"), "FUSIONADOR");

		private final Short codigo;
		private final String descripcion;

		MODULO(Short codigo, String descripcion) {
			this.codigo = codigo;
			this.descripcion = descripcion;
		}

		public Short getCodigo() {
			return codigo;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public static MODULO getEnum(Short codigo) {
			for (MODULO sc : values()) {
				if (sc.getCodigo().shortValue() == codigo.shortValue()) {
					return sc;
				}
			}
			return null;
		}
	}

	public static enum ESTADO_CARGAS {
		ESTADO_PENDIENTE(new Short("1"), "Carga pendiente"), ESTADO_PROCESANDO(new Short("2"),
				"Carga procesando"), ESTADO_PROCESADO(new Short("3"), "Carga procesada"), ESTADO_ERROR_FORMATO(
						new Short("4"), "Carga con errores de formato"), ESTADO_ERROR_GENERAL(new Short("5"),
								"Carga finalizada con error");

		private final Short codigo;
		private final String descripcion;

		ESTADO_CARGAS(Short codigo, String descripcion) {
			this.codigo = codigo;
			this.descripcion = descripcion;
		}

		public Short getCodigo() {
			return codigo;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public static ESTADO_CARGAS getEnum(Short codigo) {
			for (ESTADO_CARGAS sc : values()) {
				if (sc.getCodigo().shortValue() == codigo.shortValue()) {
					return sc;
				}
			}
			return null;
		}
	}

	public static enum CEN_TIPOCAMBIO {

		ALTA_COLEGIACION(new Short("1"), "Alta Colegiación"), BAJA_COLEGIACION(new Short("2"),
				"Baja Colegiación"), ALTA_EJERCICIO(new Short("3"), "Alta Ejercicio"), BAJA_EJERCICIO(new Short("4"),
						"Baja Ejercicio"), INHABILITACION(new Short("5"), "Inhabilitación"), SUSPENSION_EJERCICIO(
								new Short("6"), "Suspensión Ejercicio"), MODIFICACION_DATOS_GENERALES(new Short("10"),
										"Modificación Datos Generales"), MODIFICACION_DATOS_COLEGIALES(new Short("20"),
												"Modificación Datos Colegiales"), MODIFICACION_DIRECCIONES(
														new Short("30"),
														"Modificación Direcciones"), MODIFICACION_CUENTAS_BANCARIAS(
																new Short("40"),
																"Modificación Cuentas Bancarias"), MODIFICACION_DATOS_CV(
																		new Short("50"),
																		"Modificación Datos CV"), MODIFICACION_COMPONENTES(
																				new Short("60"),
																				"Modificación Componentes"), MODIFICACION_DATOS_FACTURACIÓN(
																						new Short("70"),
																						"Modificación Datos Facturación"), MODIFICACION_DATOS_TURNO(
																								new Short("80"),
																								"Modificación Datos Turno"), MODIFICACION_DATOS_EXPEDIENTES(
																										new Short("90"),
																										"Modificación Datos Expedientes"), MODIFICACION_OTROS_DATOS(
																												new Short(
																														"99"),
																												"Modificación Otros Datos"), OBSERVACIONES(
																														new Short(
																																"100"),
																														"Observaciones"), DESIGNACION_MODIFICACION(
																																new Short(
																																		"101"),
																																"Designación. Modificación"), DESIGNACION_JUSTIFICACION(
																																		new Short(
																																				"102"),
																																		"Designación. Justificación"), DESIGNACION_ALTA_DE_ACTUACIONES(
																																				new Short(
																																						"103"),
																																				"Designación. Alta de Actuaciones"), ASISTENCIA_ALTA(
																																						new Short(
																																								"104"),
																																						"Asistencia. Alta"), ASISTENCIA_MODIFICACION(
																																								new Short(
																																										"105"),
																																								"Asistencia. Modificación"), ASISTENCIA_ALTA_DE_ACTUACIONES(
																																										new Short(
																																												"106"),
																																										"Asistencia. Alta de Actuaciones"), DESIGNACION_MODIFICACION_DE_ACTUACIONES(
																																												new Short(
																																														"107"),
																																												"Designación. Modificación de Actuaciones"), DESIGNACION_ELIMINACION_DE_ACTUACIONES(
																																														new Short(
																																																"108"),
																																														"Designación. Eliminación de Actuaciones");

		private final Short idTipoCambio;
		private final String descripcionTipoCambio;

		private CEN_TIPOCAMBIO(Short idTipoCambio, String descripcionTipoCambio) {
			this.idTipoCambio = idTipoCambio;
			this.descripcionTipoCambio = descripcionTipoCambio;
		}

		public Short getIdTipoCambio() {
			return idTipoCambio;
		}

		public String getDescripcionTipoCambio() {
			return descripcionTipoCambio;
		}

		public static CEN_TIPOCAMBIO getEnum(Short idTipoCambio) {
			for (CEN_TIPOCAMBIO sc : values()) {
				if (sc.getIdTipoCambio().shortValue() == idTipoCambio.shortValue()) {
					return sc;
				}
			}
			return null;
		}

	}
	
	public static enum OBJETIVO {
		DESTINATARIOS(new Long("1"), "DESTINATARIOS"),
		MULTIDOCUMENTO(new Long("2"), "MULTIDOCUMENTO"),
		CONDICIONAL(new Long("3"), "CONDICIONAL"),
		DATOS(new Long("4"), "DATOS");
		
		private final Long codigo;
		private final String descripcion;

		OBJETIVO(Long codigo, String descripcion) {
			this.codigo = codigo;
			this.descripcion = descripcion;
		}

		public Long getCodigo() {
			return codigo;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public static OBJETIVO getEnum(Short codigo) {
			for (OBJETIVO sc : values()) {
				if (sc.getCodigo().longValue() == codigo.longValue()) {
					return sc;
				}
			}
			return null;
		}
	}
	
	public static enum SUFIJOS {
		NOMBRE_COLEGIADO(new Short("1"), "NOMBRE_COLEGIADO"),
		NUMERO_COLEGIADO(new Short("2"), "NUMERO_COLEGIADO"),
		IDENTIFICACION(new Short("3"), "IDENTIFICACION");
		
		private final Short codigo;
		private final String descripcion;

		SUFIJOS(Short codigo, String descripcion) {
			this.codigo = codigo;
			this.descripcion = descripcion;
		}

		public Short getCodigo() {
			return codigo;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public static SUFIJOS getEnum(Short codigo) {
			for (SUFIJOS sc : values()) {
				if (sc.getCodigo().shortValue() == codigo.shortValue()) {
					return sc;
				}
			}
			return null;
		}
	}
	
	public static enum FORMATO_SALIDA {
		XLS(new Short("1"), "xlsx"),
		DOC(new Short("2"), "docx"),
		PDF(new Short("3"), "pdf"),
		PDF_FIRMADO(new Short("4"), "pdf");
		
		private final Short codigo;
		private final String descripcion;

		FORMATO_SALIDA(Short codigo, String descripcion) {
			this.codigo = codigo;
			this.descripcion = descripcion;
		}

		public Short getCodigo() {
			return codigo;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public static FORMATO_SALIDA getEnum(Short codigo) {
			for (FORMATO_SALIDA sc : values()) {
				if (sc.getCodigo().shortValue() == codigo.shortValue()) {
					return sc;
				}
			}
			return null;
		}
	}

	public static enum CargaMasivaDatosCVVo {

		COLEGIADONUMERO("COLEGIADONUMERO"), PERSONANIF("PERSONANIF"), C_FECHAINICIO("C_FECHAINICIO"), C_FECHAFIN(
				"C_FECHAFIN"), C_CREDITOS("C_CREDITOS"), FECHAVERIFICACION("FECHAVERIFICACION"), TIPOCVCOD(
						"TIPOCVCOD"), SUBTIPOCV1COD("SUBTIPOCV1COD"), SUBTIPOCV2COD("SUBTIPOCV2COD"), PERSONANOMBRE(
								"PERSONANOMBRE"), C_IDPERSONA("C_IDPERSONA"), TIPOCVNOMBRE("TIPOCVNOMBRE"), C_IDTIPOCV(
										"C_IDTIPOCV"), SUBTIPOCV1NOMBRE("SUBTIPOCV1NOMBRE"), C_IDTIPOCVSUBTIPO1(
												"C_IDTIPOCVSUBTIPO1"), SUBTIPOCV2NOMBRE(
														"SUBTIPOCV2NOMBRE"), C_IDTIPOCVSUBTIPO2(
																"C_IDTIPOCVSUBTIPO2"), ERRORES(
																		"ERRORES"), C_DESCRIPCION("C_DESCRIPCION");

		private final String campo;

		private CargaMasivaDatosCVVo(String campo) {
			this.campo = campo;
		}

		public String getCampo() {
			return campo;
		}
	}

	public static enum HISTORICOCAMBIOGF {
		DATOSGENERALES((short) 10), DATOSCARGAMASIVA((short) 50);

		private short id = 0;

		HISTORICOCAMBIOGF(short id) {
			this.id = id;
		}

		public short getId() {
			return this.id;
		}
	}

	public static enum HISTORICOCAMBIOCV {		
		DATOSGENERALES ((short)10),
		DATOSCV ((short)50);
		
		private short id = 0;
		HISTORICOCAMBIOCV(short id) {
			this.id = id;
		}
		public short getId() {
			return this.id;
		}
	} 
	
	public static final String PLAZAS_DISPO_SI = "1";
	
	public static final String PLAZAS_DISPO_NO = "0";
		
	public static final String AUX_TRANS_TILDES_1 = "áéíóúàèìòùãõâêîôôäëïöüçÁÉÍÓÚÀÈÌÒÙÃÕÂÊÎÔÛÄËÏÖÜÇ";
	
	public static final String AUX_TRANS_TILDES_2 = "aeiouaeiouaoaeiooaeioucAEIOUAEIOUAOAEIOOAEIOUC";
	
	public static final String ESTADO_INSCRIPCION_PENDIENTE = "1";
	public static final String ESTADO_INSCRIPCION_RECHAZADA = "2";
	public static final String ESTADO_INSCRIPCION_APROBADA = "3";
	public static final String ESTADO_INSCRIPCION_CANCELADA = "4";
	
	//FICHA CURSO
	
public static final String ESTADO_CURSO_ABIERTO = "0";
	
	public static final String ESTADO_CURSO_ANUNCIADO = "1";
	
	public static final String ESTADO_CURSO_EN_CURSO = "2";
	
	public static final String ESTADO_CURSO_IMPARTIDO = "3";
	
	public static final String ESTADO_CURSO_FINALIZADO = "4";
	
	public static final String ESTADO_CURSO_CANCELADO = "5";
	
	public static final Integer CURSO_SIN_ARCHIVAR = 0;
	
	public static final Integer CURSO_ARCHIVADO = 1;

	public static final Long EMITIR_CERTIFICADO = new Long("1");
	
	public static final Short DESIGNAR_TUTOR = 0; 
	public static final Short ASIGNAR_TUTOR = 1; 
	public static final long ANUNCIADO_CURSO = 1; 
	public static final long ABIERTO_CURSO = 0;
	public static final long FINALIZADO_CURSO = 4; 
	public static final long CANCELADO_CURSO = 5; 
	
	public static final long INSCRIPCION_PENDIENTE = 1; 
	public static final long INSCRIPCION_CANCELADA = 4; 
	public static final long INSCRIPCION_APROBADA = 3; 
	public static final long INSCRIPCION_RECHAZADA = 2; 

	public static final String EVENTO_PLANIFICADO = "1";
	public static final String EVENTO_CUMPLIDO = "2"; 
	public static final String EVENTO_CANCELADO = "3";
	public static final String EVENTO_SESION = "8";
	
	public static final short ID_TIPO_SERVICIOS_FORMACION = 5;
	public static final short PERIOCIDAD_1MES = 1;
	public static final short MODULO_CONTADOR = 11;

	public static final String CODIGO_CURSO = "CÓDIGO CURSO";
	public static final String FORMA_PAGO = "FORMA PAGO";
	public static final String NIF = "NIF";
	public static final String CIF = "CIF";
	public static final String NIE = "NIE";
	public static final String NOMBRE_PERSONA = "NOMBRE_PERSONA";
	public static final String NOMBRE_CURSO = "NOMBRE_CURSO";
	public static final String ESTADO = "ESTADO";
	public static final String ERRORES = "ERRORES";
	
	public static final List<String> CAMPOSPLANTILLACURSO = Arrays.asList(CODIGO_CURSO, FORMA_PAGO, NIF);
	public static final List<String> CAMPOSPLOGCURSO = Arrays.asList(CODIGO_CURSO, NOMBRE_CURSO, FORMA_PAGO, NIF, NOMBRE_PERSONA, ERRORES);
	
	
	//FICHA EVENTOS
	
	public static final String NOMBRE = "NOMBRE";
	public static final String ASISTENCIA = "ASISTENCIA";
	
	public static final long TIPO_EVENTO_INICIO_INSCRIPCION = 4;
	public static final long TIPO_EVENTO_FIN_INSCRIPCION = 5;
	public static final long TIPO_EVENTO_SESION = 8;
	public static final long ESTADO_EVENTO_PLANIFICADO = 1;
	
	public static final List<String> CAMPOSPLANTILLAEVENTOS = Arrays.asList(NIF, NOMBRE, ASISTENCIA);
	
	//CARGA MASIVA GF
	
	public static final String ALTA = "A";
	public static final String BAJA = "B";
	public static final String C_IDGRUPO = "IDGRUPO";
	public static final String ACCION = "ACCION";
	public static final String GENERAL = "GENERAL";
	public static final String NOMBREGRUPO = "NOMBREGRUPO";
	public static final String PERSONANOMBRE = "PERSONANOMBRE";
	public static final String C_IDPERSONA = "IDPERSONA";
	public static final String C_IDINSTITUCION 		= "IDINSTITUCION";
	public static final String PERSONANIF = "PERSONANIFCIF";
	public static final String COLEGIADONUMERO = "COLEGIADONUMERO";
	public static final Short IDINSTITUCION_2000 = 2000;
	public static final Short IDINSTITUCION_3500 = 3500;
	public static final String TIPO_CARGA = "GF";
	public static final String C_FECHAINICIO = "FECHAINICIO";
	public static final String C_FECHAFIN = "FECHAFIN";
	
	public static final List<String> CAMPOSEJEMPLOGF = Arrays.asList(COLEGIADONUMERO, PERSONANIF, 
			C_IDGRUPO, GENERAL, ACCION, C_FECHAINICIO);

	
	public static final List<String> CAMPOSLOGGF = Arrays.asList(COLEGIADONUMERO, PERSONANIF, 
			PERSONANOMBRE, C_IDPERSONA, C_IDGRUPO, 
			GENERAL, NOMBREGRUPO, ACCION, C_FECHAINICIO, ERRORES);

	public static final String tipoExcelXls = "xls";
	public static final String tipoExcelXlsx = "xlsx";
	public static final String nombreFicheroEjemplo = "PlantillaMasivaDatosGF";
	public static final String nombreFicheroError = "LogErrorCargaMasivaGF";
	
	public static final String NOTA_SUSPENSO = "1";
	public static final String NOTA_APROBADO = "2";
	public static final String NOTA_BIEN = "3";
	public static final String NOTA_NOTABLE= "4";
	public static final String NOTA_SOBRESALIENTE = "5";
	
	//CARGA MASIVA CV

	public static final String C_IDTIPOCV = "IDTIPOCV";
    public static final String C_CREDITOS = "CREDITOS";
    public static final String C_IDTIPOCVSUBTIPO1 = "IDTIPOCVSUBTIPO1";
    public static final String C_IDTIPOCVSUBTIPO2 = "IDTIPOCVSUBTIPO2";
	public static final String TIPOCVCOD = "TIPOCVCOD";
	public static final String SUBTIPOCV1COD = "SUBTIPOCV1COD";
	public static final String SUBTIPOCV2COD = "SUBTIPOCV2COD";
	public static final String TIPOCVNOMBRE = "TIPOCVNOMBRE";
	public static final String SUBTIPOCV1NOMBRE = "SUBTIPOCV1NOMBRE";
	public static final String SUBTIPOCV2NOMBRE = "SUBTIPOCV2NOMBRE";
	public static final String FECHAVERIFICACION = "FECHAVERIFICACION";
    public static final String C_DESCRIPCION = "DESCRIPCION";

    public static final String nombreFicheroEjemploCV = "PlantillaMasivaDatosCV";
    
	public static final List<String> CAMPOSEJEMPLOCV = Arrays.asList(COLEGIADONUMERO,PERSONANIF,
			C_FECHAINICIO,C_FECHAFIN,C_CREDITOS,FECHAVERIFICACION,C_DESCRIPCION,TIPOCVCOD,SUBTIPOCV1COD,SUBTIPOCV2COD);
	public static final List<String> CAMPOSLOGCV = Arrays.asList(COLEGIADONUMERO,PERSONANIF,PERSONANOMBRE,C_IDPERSONA,
			C_FECHAINICIO ,C_FECHAFIN,C_CREDITOS,FECHAVERIFICACION,C_DESCRIPCION
			,TIPOCVCOD,TIPOCVNOMBRE,C_IDTIPOCV,SUBTIPOCV1COD,SUBTIPOCV1NOMBRE,C_IDTIPOCVSUBTIPO1,SUBTIPOCV2COD,SUBTIPOCV2NOMBRE,C_IDTIPOCVSUBTIPO2,ERRORES);


	//AGENDA
	public static final long CALENDARIO_GENERAL = 1; 
	public static final long CALENDARIO_LABORAL = 2;
	public static final long CALENDARIO_FORMACION = 3; 


	// AVISOS PARA NOTIFICACIONES EN EVENTOS
	public static final String AVISO_CANCELAR_CURSO = "1";
	public static final String AVISO_MODIFICAR_CURSO = "2";
	public static final String AVISO_PLAZAS_DISPONIBLES = "3";
	public static final String AVISO_CANCELAR_SESION = "4";
	public static final String AVISO_MODIFICAR_SESION = "5";
	
	//NOTIFICACIONES
	public static final long NOTIFICACION_TIPOCUANDO_ANTES = 1;
	public static final long NOTIFICACION_TIPOCUANDO_DESPUES = 2;
	
	public static final long NOTIFICACION_HORAS = 1;
	public static final long NOTIFICACION_MINUTOS = 2;
	public static final long NOTIFICACION_DIAS = 3;
	

	//SOLICITUD INCORPORACION
	public static final String  TIPO_DIR_RESIDENCIA = "1";
	public static final String  TIPO_DIR_DESPACHO = "2";
	public static final String  TIPO_DIR_CENSOWEB = "3";
	public static final String  TIPO_DIR_PUBLICA = "4";
	public static final String  TIPO_DIR_GUIAJUDICIAL = "5";
	public static final String  TIPO_DIR_GUARDIA = "6";
	public static final String  TIPO_DIR_REVISTA = "7";
	public static final String  TIPO_DIR_FACTURACION = "8";
	public static final String  TIPO_DIR_TRASPASO = "9";
	public static final String  TIPO_DIR_PREFERENTE_EMAIL = "10";
	public static final String  TIPO_DIR_PREFERENTE_CORREO = "11";
	public static final String  TIPO_DIR_PREFERENTE_SMS = "12";
	public static final String  TIPO_DIR_PREFERENTE_FAX = "13";
	public static final String  TIPO_DIR_FORMACION = "14";
	
	public static final String  DIR_PREFERENTE_EMAIL = "E";
	public static final String  DIR_PREFERENTE_CORREO = "C";
	public static final String  DIR_PREFERENTE_SMS = "S";
	public static final String  DIR_PREFERENTE_FAX = "F";
	
	public static final short  REINCORPORACION_EJERCIENTE = 10;
	public static final short  REINCORPORACION_NO_EJERCIENTE = 20;
	public static final short  INCORPORACION_EJERCIENTE = 30;
	public static final short  INCORPORACION_NO_EJERCIENTE = 40;
	
	public static final long  TIPO_NOTIFICACION_INICIOINSCRIPCION = 3;
	public static final long  TIPO_NOTIFICACION_FININSCRIPCION = 4;
	public static final long  TIPO_NOTIFICACION_SESION = 7;
	
	public static final String  CENSO_WS_SITUACION_EJERCIENTE = "EJERCIENTE";
	public static final String  CENSO_WS_SITUACION_NOEJERCIENTE = "NOEJERCIENTE";
	public static final String  CENSO_WS_SITUACION_BAJACOLEGIO = "BAJACOLEGIO";
	public static final String  CENSO_WS_SITUACION_INSCRITO = "INSCRITO";
	
	public static enum PARAMETRO_GENERAL { 
		MAX_FILE_SIZE("10485760")
		;
				
				private String valor = null;
				
				private PARAMETRO_GENERAL(String valor) {
					this.valor = valor;
				}
				
				public final String getValor() {
					return this.valor;
				}
				
				public void setValor(String valor){
					this.valor = valor;
				}		
			}
	
	public static enum GEN_PARAMETROS {
		PATH_DOCUMENTOSADJUNTOS
	}
	
	public static String DATEST_FORMAT_MIN = "dd/MM/yyyy HH:mm";
	public static String DATEST_FORMAT_MIN_SEC = "dd/MM/yyyy HH:mm:ss";

	public static DateFormat DATE_FORMAT_MIN = new SimpleDateFormat(DATEST_FORMAT_MIN);
	
	public static String[] columnsExcelLogEnvios = new String[]{"ENVIO", "DESCRIPCION", "FECHA ENVÍO", "REMITENTE", "CORREO REMITENTE", "NIF/CIF", "NOMBRE", "APELLIDO 1", "APELLIDO 2", "MOVIL", "CORREO ELECTRONICO", "MENSAJE", "DOCUMENTOS"};

	public static String ENVIOS_MASIVOS_LOG_NOMBRE_FICHERO = "informeEnvio.log";
	public static enum ENVIOS_MASIVOS_LOG_EXTENSION {
		xls,
		xlsx
	}
	
	public static String EXPRESION_REGULAR_MAIL = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,10}$";
	public static String EXPRESION_REGULAR_MAIL2 = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	
	
	
	public static String EXPRESION_REGULAR_MOVIL = "(\\+34|0034|34|\\(\\+34\\)|\\(0034\\)|34)?[ -]*(6|7)[ -]*([0-9][ -]*){8}";
	
	public static enum ECOM_ESTADOSCOLA {
		INICIAL ((short)1),
		EJECUTANDOSE ((short)2),
		REINTENTANDO ((short)3),
		ERROR ((short)4),
		FINAL ((short)5),
		ERROR_VALIDACION ((short)6);
		
		private short id = -1;
		
		ECOM_ESTADOSCOLA(short id) {
			this.id = id;
		}
		public short getId() {
			return this.id;
		}
		
	}
	
	public static enum ECOM_OPERACION {
		ECOM2_INIT_PARAMETROS_GENERALES(206)
		;

		public static ECOM_OPERACION getEnum(Integer codigo){
			for(ECOM_OPERACION sc : values()){
				if (sc.getId()==codigo){
					return sc;
				}
			}
			return null;
		}
		
		
		private int id = -1;
		
		private ECOM_OPERACION(int id) {			
			this.id = id;
		}
		public int getId() {
			return this.id;
		}
	}
	public static enum GEN_PROPERTIES_FICHERO {
		eCOM2_LOG4J
	}

	
	public static Integer USUMODIFICACION_0 = 0;
	public static int ECOM_COLA_HORAS_EN_EJECUCION_MAXIMAS = 2;
	
	public static String SIGAFRONT_VERSION = "1.0.71_15";
	public static String SIGAWEB_VERSION = "1.0.71_15";
	
}
