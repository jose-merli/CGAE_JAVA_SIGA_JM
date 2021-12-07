package org.itcgae.siga.DTO.fac;

import lombok.Data;

import java.util.Date;

@Data
public class EstadosPagosItem {

	String orden;
	Date fechaEnvio;
	String documento;
}