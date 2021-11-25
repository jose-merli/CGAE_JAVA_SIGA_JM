package org.itcgae.siga.DTO.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

import lombok.Data;

@Data
public class BaremosRequestDTO {
	private List<BaremosRequestItem> baremosRequestItems = new ArrayList<BaremosRequestItem>();
	private Error error = null;
}
