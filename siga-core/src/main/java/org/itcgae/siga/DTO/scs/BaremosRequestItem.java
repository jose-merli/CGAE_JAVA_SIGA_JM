package org.itcgae.siga.DTO.scs;

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
	Short simpleOImporteIndividual;
	Short naPartir;
	Short maximo;
	String porDia;
	String idTurno;
	String idGuardia;
	String nomTurno;
	
	List<GuardiasItem> guardiasObj;
}
