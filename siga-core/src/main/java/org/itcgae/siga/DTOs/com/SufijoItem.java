package org.itcgae.siga.DTOs.com;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SufijoItem {
	private String idSufijo;
	private String orden;
	private String nombreSufijo;
	
	public String getIdSufijo() {
		return idSufijo;
	}
	public void setIdSufijo(String idSufijo) {
		this.idSufijo = idSufijo;
	}
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	public String getNombreSufijo() {
		return nombreSufijo;
	}
	public void setNombreSufijo(String nombreSufijo) {
		this.nombreSufijo = nombreSufijo;
	}
	public SufijoItem(String idSufijo, String orden, String nombreSufijo) {
		super();
		this.idSufijo = idSufijo;
		this.orden = orden;
		this.nombreSufijo = nombreSufijo;
	}
	public SufijoItem() {
		super();
	}
	
	public enum SufijosAgrupados {
		I_NASUNTO("I_NASUNTO", "1", "N. Asunto",new Short[] {3}), 
		II_NASUNTO_NCOL_APENOMBRE("II_NASUNTO_NCOL_APENOMBRE", "2", "N. Asunto - N. Col - Apellidos, Nombre", new Short[] {3,2,1}),
		III_NCOL_APENOMBRE("III_NCOL_APENOMBRE", "3", "N. Col - Apellidos, Nombre", new Short[] {2,1}),
		IV_NCOL_APENOMBRE_NASUNTO("IV_NCOL_APENOMBRE_NASUNTO", "4", "N. Col - Apellidos Nombre - N. Asunto", new Short[]{2,1,3});

		String id;
		String orden;
		String nombre;
		private List<Short> idSufijos;

		SufijosAgrupados(String id, String orden, String nombre, Short [] idSufijos) {
			this.id = id;
			this.orden = orden;
			this.nombre = nombre;
			this.idSufijos=Arrays.asList(idSufijos);
		}
		 
		public List<Short> getIdSufijos() {
			return idSufijos;
		}


		public String getId() {
			return id;
		}

		public String getOrden() {
			return orden;
		}

		public String getNombre() {
			return nombre;
		};
		
		public String getStrIdSufijos() {
			return idSufijos.stream().map(s->s.toString()).collect(Collectors.joining(","));
		}
	
		public static SufijosAgrupados getSufijoAgrupadoEquivalente(List<String> listaSufijos) {
			String sSufijos=listaSufijos.stream().collect(Collectors.joining(","));
			for(SufijosAgrupados s: SufijosAgrupados.values()) {
				if(s.getStrIdSufijos().equals(sSufijos)) {
					return s;
				}
			}
			return null;
		}
		
	}

	
}
