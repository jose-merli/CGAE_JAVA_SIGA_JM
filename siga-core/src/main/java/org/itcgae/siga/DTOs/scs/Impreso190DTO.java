package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

import lombok.Data;

@Data
public class Impreso190DTO {

	private List<Impreso190Item>  impreso190Item = new ArrayList<Impreso190Item>();
	private Error error = null;
}
