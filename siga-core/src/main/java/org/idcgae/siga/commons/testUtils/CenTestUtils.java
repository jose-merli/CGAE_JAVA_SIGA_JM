package org.idcgae.siga.commons.testUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.ComboEtiquetasItem;
import org.itcgae.siga.DTOs.cen.NoColegiadoItem;
import org.itcgae.siga.db.entities.CenCliente;
import org.itcgae.siga.db.entities.CenDatoscolegialesestado;
import org.itcgae.siga.db.entities.CenDatoscv;
import org.itcgae.siga.db.entities.CenGruposcliente;
import org.itcgae.siga.db.entities.CenGruposclienteCliente;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.GenProperties;
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
	
	public ArrayList<CenDatoscolegialesestado> getDatosColegialesList(){
		ArrayList<CenDatoscolegialesestado> listaDatosColegiales = new ArrayList<CenDatoscolegialesestado>();
		listaDatosColegiales.add(getCenDatoscolegialesestado());
		
		return listaDatosColegiales;
	}
	
	
	public CenDatoscolegialesestado getCenDatoscolegialesestado() {
		CenDatoscolegialesestado persona = new CenDatoscolegialesestado();
		persona.setIdpersona(Long.parseLong("1234"));
		persona.setIdestado(Short.parseShort("1234"));
		persona.setIdinstitucion(Short.parseShort("1234"));
		persona.setFechaestado(new Date());
		persona.setFechamodificacion(new Date());
		persona.setUsumodificacion(1);
		return persona;
	}
	
	public List<CenPersona> getListaPersonasSimuladas(Long idpersona,String nifcif){
		List<CenPersona> listaPersonasSimuladas = new ArrayList<CenPersona>();
		listaPersonasSimuladas.add(getCenPersona(idpersona, nifcif));
		
		return listaPersonasSimuladas;
	}
	
	public CenPersona getCenPersona(Long idpersona,String nifcif) {
		CenPersona persona = new CenPersona();
		persona.setIdpersona(idpersona);
		persona.setNombre("Nombre");
		persona.setApellidos1("apellidos1");
		persona.setApellidos2("apellidos2");
		persona.setNifcif(nifcif);
		persona.setFechamodificacion(new Date());
		persona.setUsumodificacion(1);
		persona.setIdtipoidentificacion(Short.parseShort("10"));
		persona.setFechanacimiento(new Date());
		persona.setIdestadocivil(Short.parseShort("10"));
		persona.setNaturalde("naturalde");
		persona.setFallecido("Fallecido");
		persona.setSexo("M");
		return persona;
	}
	
	public ColegiadoItem getColegiadoItem(Boolean isColegiado, String idGrupo) {
		ColegiadoItem colegiado = new ColegiadoItem();
		colegiado.setIdPersona(String.valueOf(1223));
		colegiado.setIdInstitucion("2005");
		colegiado.setNombre("Nombre");
		colegiado.setApellidos1("apellidos1");
		colegiado.setIdTipoIdentificacion("10");
		colegiado.setApellidos2("apellidos2");
		colegiado.setColegiado(isColegiado);
		colegiado.setNaturalDe("");
		colegiado.setIncorporacion(new Date());
		colegiado.setSituacion("1");
		colegiado.setNif("20092000V");
		colegiado.setIdtratamiento("1");
		colegiado.setComisiones("0");
		colegiado.setAsientoContable("");
		colegiado.setSexo("M");
//		CREAR COMBOETIQUETASITEM Y METERLO EN EL ARRAY
		colegiado.setEtiquetas(new ComboEtiquetasItem[] {getComboEtiquetasItem(idGrupo)});
		return colegiado;
	}
	
	public List<ComboEtiquetasItem> getListaEtiquetasSimuladas(String idGrupo){
		List<ComboEtiquetasItem> listaEtiquetasSimuladas = new ArrayList<ComboEtiquetasItem>();
		listaEtiquetasSimuladas.add(getComboEtiquetasItem(idGrupo));
		
		return listaEtiquetasSimuladas;
	}

	
	public List<ComboEtiquetasItem> getComboEtiquetasSimulados(String fInicio, String fBaja, String color){
		List<ComboEtiquetasItem> listaGruposSimulados = new ArrayList<ComboEtiquetasItem>();
		listaGruposSimulados.add(getComboEtiquetasItem(fInicio, fBaja, color));
		
		return listaGruposSimulados;
	}
	
	public ComboEtiquetasItem getComboEtiquetasItem(String fInicio, String fBaja, String color) {
		ComboEtiquetasItem etiquetas = new ComboEtiquetasItem();
		etiquetas.setFechaBaja(fInicio);
		etiquetas.setFechaInicio(fBaja);
		etiquetas.setColor(color);
		return etiquetas;
	}
	
	public List<CenGruposclienteCliente> getListaGruposSimulados(){
		List<CenGruposclienteCliente> listaGruposSimulados = new ArrayList<CenGruposclienteCliente>();
		listaGruposSimulados.add(getGruposCliCliItem());
		
		return listaGruposSimulados;
	}
	
	public CenGruposclienteCliente getGruposCliCliItem() {
		CenGruposclienteCliente gruposCliCli = new CenGruposclienteCliente();
		gruposCliCli.setFechaInicio(new Date());
		gruposCliCli.setFechaBaja(new Date());
		gruposCliCli.setFechamodificacion(new Date());
		gruposCliCli.setIdgrupo(Short.parseShort("1"));
		gruposCliCli.setIdinstitucion((short) 2005);
		gruposCliCli.setIdpersona(Long.parseLong("213"));
		return gruposCliCli;
	}
	
	public ComboEtiquetasItem getComboEtiquetasItem(String idGrupo) {
		ComboEtiquetasItem comboEtiquetas = new ComboEtiquetasItem();
		comboEtiquetas.setIdGrupo(idGrupo);
		comboEtiquetas.setFechaBaja("23/05/2014");
		comboEtiquetas.setFechaInicio("23/05/2014");
		comboEtiquetas.setLabel("Label");
		return comboEtiquetas; 
	}
	
	public int insertSelectiveForUpdateLegalPerson(ComboEtiquetasItem etiqueta, String idPersona, String idInstitucion, String idUsuario) {
		return 1;
	}
	
	public CenCliente getCenCliente() {
		CenCliente cenCliente = new CenCliente();
		cenCliente.setFechamodificacion(new Date());
		cenCliente.setIdinstitucion((short) 2005);
		cenCliente.setIdpersona(Long.parseLong("213"));
		return cenCliente;
	}
	
	public List<CenDatoscv> getListCenDatoscv() {
		List<CenDatoscv> listaCenDatoscv = new ArrayList<CenDatoscv>();
		CenDatoscv cenDatoscv = new CenDatoscv();
		cenDatoscv.setFechamodificacion(new Date());
		cenDatoscv.setIdinstitucion((short) 2005);
		cenDatoscv.setIdpersona(Long.parseLong("213"));
		listaCenDatoscv.add(cenDatoscv);
		return listaCenDatoscv;
	}
	
	public List<GenProperties> getListGenPropertiesSimulados(){
		List<GenProperties> properties = new ArrayList<>();
		GenProperties property = new GenProperties();
		property.setValor("1");
		properties.add(property);
		return properties;
	}
	
//	
//	public CenGruposclienteCliente getGruposCliCliItem() {
//		CenGruposclienteCliente gruposCliCli = new CenGruposclienteCliente();
//		gruposCliCli.setFechaInicio(new Date());
//		gruposCliCli.setFechaBaja(new Date());
//		gruposCliCli.setFechamodificacion(new Date());
//		gruposCliCli.setIdgrupo(Short.parseShort("1"));
//		gruposCliCli.setIdinstitucion((short) 2005);
//		gruposCliCli.setIdpersona(Long.parseLong("213"));
//		return gruposCliCli;
//	}
	
	
	public List<ColegiadoItem> getListaColegiadosSimulada(Boolean isColegiado, String idGrupo){
		List<ColegiadoItem> listaColegiadosSimulada = new ArrayList<ColegiadoItem>();
		listaColegiadosSimulada.add(getColegiadoItem(isColegiado, idGrupo));
		
		return listaColegiadosSimulada;
	}
	
	
	public List<CenGruposcliente> getListaGrupCliSimulados(){
		List<CenGruposcliente> listaGruposSimulados = new ArrayList<CenGruposcliente>();
		listaGruposSimulados.add(getGrupCliItem());
		
		return listaGruposSimulados;
	}
	
	public CenGruposcliente getGrupCliItem() {
		CenGruposcliente gruposCliCli = new CenGruposcliente();
		gruposCliCli.setFechamodificacion(new Date());
		gruposCliCli.setIdgrupo(Short.parseShort("1"));
		gruposCliCli.setIdinstitucion((short) 2005);
		gruposCliCli.setNombre("A");
		gruposCliCli.setUsumodificacion(1234);
		return gruposCliCli;
	}
	
	public NoColegiadoItem getNoColegiadoItem(String idGrupo) {
		NoColegiadoItem noColegiado = new NoColegiadoItem();
		noColegiado.setIdPersona(String.valueOf(1223));
		noColegiado.setIdInstitucion("2005");
		noColegiado.setNombre("Nombre");
		noColegiado.setApellidos1("apellidos1");
		noColegiado.setGuiaJudicial("1");
		noColegiado.setIdLenguaje("1");
		noColegiado.setMotivo("motivo");
		noColegiado.setIdTipoIdentificacion("10");
		noColegiado.setApellidos2("apellidos2");
//		noColegiado.setColegiado(isColegiado);
		noColegiado.setNaturalDe("");
//		noColegiado.setIncorporacion(new Date());
		noColegiado.setSituacion("1");
		noColegiado.setNif("20092000V");
//		noColegiado.setIdtratamiento("1");
		noColegiado.setComisiones("0");
		noColegiado.setAsientoContable("");
		noColegiado.setSexo("M");
//		CREAR COMBOETIQUETASITEM Y METERLO EN EL ARRAY
		noColegiado.setEtiquetas(new ComboEtiquetasItem[] {getComboEtiquetasItem(idGrupo)});
		return noColegiado;
	}
	
}
