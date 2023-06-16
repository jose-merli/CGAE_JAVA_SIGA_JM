package org.itcgae.siga.DTOs.com;

import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.EjgItem;

import lombok.Data;

@Data
public class CamposPlantillaEnvio {
	
	private String cuerpo;
	private String asunto;
	private String identificador;
	private DesignaItem designa;
	private EjgItem ejg;
	private String idInstitucion;

}
