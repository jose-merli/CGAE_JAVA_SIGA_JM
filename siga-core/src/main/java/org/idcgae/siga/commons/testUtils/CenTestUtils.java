package org.idcgae.siga.commons.testUtils;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.NoColegiadoItem;
import org.springframework.stereotype.Service;

@Service
public class CenTestUtils {

	public List<ColegiadoItem> getListColegiadoItemSimulados() {

		List<ColegiadoItem> colegiadoItems = new ArrayList<ColegiadoItem>();

		colegiadoItems.add(getColegiadoItem());

		return colegiadoItems;
	}
	
	public ColegiadoItem getColegiadoItem() {
		ColegiadoItem colegiadoItem = new ColegiadoItem();

		colegiadoItem.setIdInstitucion("2000");
		colegiadoItem.setIdPersona("23453212");
		
		return colegiadoItem;
	}
	
	public ColegiadoDTO getColegiadoDTO() {
		ColegiadoDTO colegiadoDTO = new ColegiadoDTO();
		List<ColegiadoItem> listaColegiados = new ArrayList<ColegiadoItem>();
		listaColegiados.add(getColegiadoItem());
		colegiadoDTO.setColegiadoItem(listaColegiados);
	
		return colegiadoDTO;
	}
	
	public List<NoColegiadoItem> getListNoColegiadoItemSimulados() {

		List<NoColegiadoItem> noColegiadoItem = new ArrayList<NoColegiadoItem>();

		noColegiadoItem.add(getNoColegiadoItem());

		return noColegiadoItem;
	}
	
	public NoColegiadoItem getNoColegiadoItem() {
		NoColegiadoItem noColegiadoItem = new NoColegiadoItem();

		noColegiadoItem.setIdInstitucion("2000");
		noColegiadoItem.setIdPersona("23453212");
		
		return noColegiadoItem;
	}
	
	

}
