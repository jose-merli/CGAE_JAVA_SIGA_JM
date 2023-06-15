package org.itcgae.siga.DTOs.scs;

import java.util.Date;

import lombok.Data;

@Data
public class GrupoGuardiaRowItem {
	int idGrupoGuardia;
	int idGrupoGuardiaColegiado;
	int orden;
	String idPersona;
	Date fechaSuscripcion;
	
}
