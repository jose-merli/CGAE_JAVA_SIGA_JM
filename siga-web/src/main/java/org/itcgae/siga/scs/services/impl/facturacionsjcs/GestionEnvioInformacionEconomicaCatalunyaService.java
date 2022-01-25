package org.itcgae.siga.scs.services.impl.facturacionsjcs;

public class GestionEnvioInformacionEconomicaCatalunyaService {
	static final String PARAM_ECOMCOLA_JUSTIFICACION = "JUSTIFICACION";
	static final String PARAM_ECOMCOLA_IDINTERCAMBIO = "IDINTERCAMBIO";
	static final String PARAM_ECOMCOLA_DEVOLUCION = "DEVOLUCION";
	static final String PARAM_ECOMCOLA_CERTIFIFICACIONICA = "CERTIFICACIONICA";
	static final String PARAM_ECOMCOLA_CERTIFIFICACIONANEXO = "CERTIFICACIONANEXO";

	
	public static final String SUFIJO_ARCHIVO_VISTAPREVIA= "VP";
	public static final String JUSTIFICACION_ERROR = "ERROR";
	public static final String JUSTIFICACION  ="JUSTIFICACION";



	public enum TIPOINTERCAMBIO {
		Justificaciones ("IJT","Justificació")
		, Devoluciones ("IDT","Devolució")
		, Certificaciones ("ICT","Certificació")
		, Anexos ("IXT","Certificació Anex")
		, CertificacionCICAC ("ICT","Certificació CICAC")
		,IntercambioErroneo("IEE","Intercamio Erróneo");
		
		public static TIPOINTERCAMBIO getEnum(String id){
			for(TIPOINTERCAMBIO sc : values()){
				if (sc.getId().equals(id)){
					return sc;
				}
			}
			return null;
		}
		
		private String id;
		private String descripcio;
		TIPOINTERCAMBIO(String id,String descripcio) {
			this.id = id;
			this.descripcio = descripcio;
		}
		public String getId() {
			return this.id;
		}
		public String getDescripcio() {
			return this.descripcio;
		}
	}
	
	public static enum ESTADOS_FCS_JE {
		INICIAL((short)10,"Inicial"),
		VALIDANDO((short)12,"Validando"),
		VALIDADO_CORRECTO((short)14,"Validado correcto"),
		VALIDADO_ERRONEO((short)16,"Validado err�neo"),
		ENVIANDO_ICA_CICAC((short)20,"Enviando ICA-CICAC..."),
		ENVIADO_CICAC((short)30,"Enviado CICAC"),
		ERROR_CICAC((short)40,"Error CICAC"),
		FIN_CICAC((short)50,"Fin CICAC"),
		DEVUELTO_CICAC_ICA_ERRONEO((short)60,"Devuelto CICAC-ICA erróneo"),
		ENVIANDO_CICAC_GEN_GENERANDO_XML((short)62,"Enviando CICAC-GEN. Generando xml"),
		ENVIANDO_CICAC_GEN_MOVIENDO_XML((short)65,"Enviando CICAC-GEN. Moviendo xml"),
		ENVIADO_GEN((short)70,"Enviado Gen."),
		DEVUELTO_GEN_CICAC_ERRONEO((short)87,"Devuelto GEN-CICAC erróneo"),
		DEVUELTO_GEN_CICAC_CORRECTO((short)90,"Devuelto GEN-CICAC correcto."),
		RESPONDIENDO_CICAC_ICA((short)92,"Respondiendo ICA..."),
		DEVUELTO_CICAC_ICA_CORRECTO((short)95,"Devuelto CICAC-ICA correcto")
		;
		
//		10	Inicial
//		12	Validant...
//		14	Validat correctament
//		16	Validaci� err�nia
//		20	Enviant Consell...
//		30	Enviat Consell
//		40	Error Consell
//		50	Finalitzat Consell
		
//		60	Retornat error Consell a l'ICA.
//		62	Enviant Generalitat. Generant XML....
//		65	Enviant Generalitat. Movent XML...
		
//		70	Enviat Generalitat.
//		75	Processant XML de Generalitat...
		
//		85	Error Generalitat
//		87	Retornat error Generalitat al Consell.
//		90	Verificat correctament Generalitat
		
		public short getIdEstado() {
			return idEstado;
		}
		public String getDescripcion() {
			return descripcion;
		}

		private short idEstado;
		private String descripcion;
		
		ESTADOS_FCS_JE(short idEstado,String descripcion) {
			this.idEstado = idEstado;
			this.descripcion = descripcion;
		}
		
		public static ESTADOS_FCS_JE getEnum(short id){
			for(ESTADOS_FCS_JE ica : values()){
				if (ica.getIdEstado()==id){
					return ica;
				}
			}
			return null;
		}
		
		
	}
	
	public static enum ICAS {
		Barcelona ("2012","IIlustre Col·legi d'Advocats de Barcelona","ICAB"),
		Figueres  ("2026","IIlustre Col·legi d'Advocats de Figueres","FIGU"), 
		Girona  ("2028","IIlustre Col·legi d'Advocats de Girona","GIRO"), 
		Granollers  ("2030","IIlustre Col·legi d'Advocats de Granollers","GRAV"),
		Lleida  ("2041","IIlustre Col·legi d'Advocats de Lleida","LLEI"), 
		Manresa  ("2047","IIlustre Col·legi d'Advocats de Manresa","MANR"), 
		Mataro  ("2048","IIlustre Col·legi d'Advocats de Mataró","MATA"), 
		Reus  ("2057","IIlustre Col·legi d'Advocats de Reus","REUS"), 
		Sabadell  ("2059","IIlustre Col·legi d'Advocats de Sabadell","SABA"), 
		SantFeliu   ("2061","IIlustre Col·legi d'Advocats de Sant Feliu de Llobregat","STFE"), 
		Tarragona  ("2071","IIlustre Col·legi d'Advocats de Tarragona","TARR"), 
		Terrassa  ("2072","IIlustre Col·legi d'Advocats de Terrassa","TERR"), 
		Tortosa  ("2075","IIlustre Col·legi d'Advocats de Tortosa","TORT"), 
		Vic  ("2079","IIlustre Col·legi d'Advocats de Vic","VICC"), 
		CICAC ("3001","Consell dels Il·lustres Col·legis d'Advocats de Catalunya (CICAC)","CICAC"),
		GEN ("GEN","Generalitat","GEN");
		
		
		private String codSIGA;
		private String descripcion;
		private String cogAJG;
		
		ICAS(String codSIGA,String descripcion,String cogAJG) {
			this.codSIGA = codSIGA;
			this.descripcion = descripcion;
			this.cogAJG = cogAJG;
		}
		public String getCodSIGA() {
			return this.codSIGA;
		}
		public String getDescripcion() {
			return this.descripcion;
		}
		public String getCogAJG() {
			return this.cogAJG;
		}
		public static ICAS getEnum(String codSIGA){
			for(ICAS ica : values()){
				if (ica.getCodSIGA().contentEquals(codSIGA)){
					return ica;
				}
			}
			return null;
		}
		
		
		
		
	}
}
