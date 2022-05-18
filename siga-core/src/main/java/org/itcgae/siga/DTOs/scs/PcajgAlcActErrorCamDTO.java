package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.db.entities.PcajgAlcActErrorCam;
import org.itcgae.siga.db.entities.VPcajgAlcActErrorCam;

import lombok.Data;

@Data
public class PcajgAlcActErrorCamDTO {

	private List<VPcajgAlcActErrorCam> listaPcajgAlcActErrorCam = new ArrayList<>();
	private Error error = null;
}
