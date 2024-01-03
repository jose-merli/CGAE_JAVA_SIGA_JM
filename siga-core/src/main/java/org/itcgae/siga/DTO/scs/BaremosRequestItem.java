package org.itcgae.siga.DTO.scs;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BaremosRequestItem {
	String guardias;
	String nDias;
	String baremo;
	String dias;
	Double disponibilidad;
	Short numMinimoSimple;
	Double simpleOImporteIndividual;
	Short naPartir;
	Double maximo;
	String porDia;
	String idTurno;
	String idGuardia;
	String nomTurno;
	Date fechabaja;
	boolean historico;
	int keyForTabla;
	String idhitoconfiguracion;
	String idHito;
	
	List<GuardiasItem> guardiasObj;
}
