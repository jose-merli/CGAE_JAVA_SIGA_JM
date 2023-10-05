package org.itcgae.siga.cen.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.ComboInstitucionDTO;
import org.itcgae.siga.DTOs.cen.ComboInstitucionItem;
import org.itcgae.siga.DTOs.com.ResponseFileDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.DTOs.scs.CenPersonaItem;
import org.itcgae.siga.cen.services.IBusquedaColegiadosService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenDiccionario;
import org.itcgae.siga.db.entities.GenDiccionarioKey;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.itcgae.siga.db.mappers.GenDiccionarioMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenEstadocivilExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenEstadocolegialExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposcvExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ConConsultasExtendsMapper;
import org.itcgae.siga.scs.services.impl.oficio.DesignacionesServiceImpl;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BusquedaColegiadosServiceImpl implements IBusquedaColegiadosService {

	private Logger LOGGER = Logger.getLogger(BusquedaColegiadosServiceImpl.class);

	@Autowired
	private CenEstadocivilExtendsMapper cenEstadocivilExtendsMapper;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	@Autowired
	private CenEstadocolegialExtendsMapper cenEstadocolegialExtendsMapper;

	@Autowired
	private GenPropertiesMapper genPropertiesMapper;

	@Autowired
	private CenTiposcvExtendsMapper cenTiposcvExtendsMapper;

	@Autowired
	private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;

	@Autowired
	private ConConsultasExtendsMapper conConsultasExtendsMapper;

	@Autowired
	private GenDiccionarioMapper genDiccionarioMapper;

	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;
	
	@Autowired
	private DesignacionesServiceImpl desigancionesServiceImpl;
	
	private static final int EXCEL_ROW_FLUSH = 1000;
	
	@Override
	public ComboDTO getCivilStatus(HttpServletRequest request) {

		LOGGER.info("getCivilStatus() -> Entrada al servicio para obtener los tipos de estado civil");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getCivilStatus() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getCivilStatus() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getCivilStatus() / cenEstadocivilExtendsMapper.distinctCivilStatus() -> Entrada a cenEstadocivilExtendsMapper para obtener los diferentes tipos de estados civiles");
				comboItems = cenEstadocivilExtendsMapper.distinctCivilStatus(usuario.getIdlenguaje());
				LOGGER.info(
						"getCivilStatus() / cenEstadocivilExtendsMapper.distinctCivilStatus() -> Salida de cenEstadocivilExtendsMapper para obtener los diferentes tipos de estados civiles");
			}
		}

		comboDTO.setCombooItems(comboItems);

		LOGGER.info("getCivilStatus() -> Salida del servicio para obtener los tipos de estado civil");

		return comboDTO;
	}

	@Override
	public ComboDTO getSituacion(HttpServletRequest request) {

		LOGGER.info("getSituacion() -> Entrada al servicio para obtener los tipos situacion de un colegiado");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getSituacion() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getSituacion() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getSituacion() / cenEstadocolegialExtendsMapper.distinctCivilStatus() -> Entrada a cenEstadocolegialExtendsMapper para obtener los diferentes tipos de situacion de un colegiado");
				comboItems = cenEstadocolegialExtendsMapper.distinctSituacionColegial(usuario.getIdlenguaje());
				LOGGER.info(
						"getSituacion() / cenEstadocolegialExtendsMapper.distinctCivilStatus() -> Salida de cenEstadocolegialExtendsMapper para obtener los diferentes tipos de situacion de un colegiado");

//				ComboItem comboItem = new ComboItem();
//				comboItem.setLabel("");
//				comboItem.setValue("");
//				comboItems.add(0, comboItem);

			}
		}

		comboDTO.setCombooItems(comboItems);

		LOGGER.info("getSituacion() -> Salida del servicio para obtener los tipos situacion de un colegiado");

		return comboDTO;
	}
	
	@Override
	public ComboDTO getSituacionGlobalColegiado(String idPersona, HttpServletRequest request) {
		LOGGER.info("getSituacionGlobalColegiado() -> Entrada al servicio para obtener las situaciones de todos los colegios de la persona");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		
		if (idPersona != null || idPersona != "") {
			LOGGER.info(
					"getSituacionGlobalColegiado() / cenEstadocolegialExtendsMapper.getSituacionGlobalColegiado() -> "
					+ "Entrada a cenEstadocivilExtendsMapper para obtener los diferentes tipos de estados civiles");
			comboItems = cenEstadocolegialExtendsMapper.getSituacionGlobalColegiado(idPersona);
			LOGGER.info(
					"getSituacionGlobalColegiado() / cenEstadocolegialExtendsMapper.getSituacionGlobalColegiado() -> "
					+ "Salida de cenEstadocivilExtendsMapper para obtener los diferentes tipos de estados civiles");
			
			try {
				LOGGER.info(
						"getSituacionGlobalColegiado() / desigancionesServiceImpl.getPersonaPorId() -> "
						+ "Entrada a desigancionesServiceImpl para obtener datos generales persona y saber si esta fallecido");
				CenPersonaItem cenPersonaItem = desigancionesServiceImpl.getPersonaPorId(idPersona);
				LOGGER.info(
						"getSituacionGlobalColegiado() / desigancionesServiceImpl.getPersonaPorId() -> "
						+ "Salida de desigancionesServiceImpl para obtener datos generales persona y saber si esta fallecido");
				if(cenPersonaItem != null && cenPersonaItem.getFallecido().equals("1")) {
					ComboItem incluirFallecido = new ComboItem();
					incluirFallecido.setLabel("FFFF");
					incluirFallecido.setValue("60");					
					comboItems.add(incluirFallecido);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		comboDTO.setCombooItems(comboItems);

		LOGGER.info("getSituacionGlobalColegiado() -> Salida del servicio para obtener las situaciones de todos los colegios de la persona");
		
		return comboDTO;
	}

	@Override
	public ComboDTO getCVCategory(HttpServletRequest request) {

		LOGGER.info("getCVCategory() -> Entrada al servicio para obtener los tipos de categorías curriculares");

		ComboDTO comboDTO = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getCVCategory() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getCVCategory() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				LOGGER.info(
						"getCVCategory() / cenTiposcvExtendsMapper.selectCategoriaCV() -> Entrada a cenTiposcvExtendsMapper para obtener los diferentes tipos de categorías curriculares");
				comboItems = cenTiposcvExtendsMapper.selectCategoriaCV(usuario.getIdlenguaje());
				LOGGER.info(
						"getCVCategory() / cenTiposcvExtendsMapper.selectCategoriaCV() -> Salida de cenTiposcvExtendsMapper para obtener los diferentes tipos de categorías curriculares");
			}
		}

		comboDTO.setCombooItems(comboItems);

		LOGGER.info("getCVCategory() -> Salida del servicio para obtener los tipos de categorías curriculares");

		return comboDTO;
	}

	@Override
	public ComboInstitucionDTO getLabel(HttpServletRequest request) {
		LOGGER.info("getLabel() -> Entrada al servicio para obtener los de grupos de clientes");
		List<ComboInstitucionItem> comboItem = new ArrayList<ComboInstitucionItem>();
		ComboInstitucionDTO comboDTO = new ComboInstitucionDTO();

		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"getLabel() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"getLabel() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				// la consulta necesita idinstitucion de token y idlenguaje del usuario logeado
				usuario.setIdinstitucion(idInstitucion);
				LOGGER.info(
						"getLabel() / cenGruposclienteExtendsMapper.getLabel() -> Entrada a cenGruposclienteExtendsMapper para obtener lista de tipos de colegios");
				comboItem = cenColegiadoExtendsMapper.getLabel(usuario);
				LOGGER.info(
						"getLabel() / cenGruposclienteExtendsMapper.getLabel() -> Salida de cenGruposclienteExtendsMapper para obtener lista de tipos de colegios");
				comboDTO.setComboInstitucionItem(comboItem);
			} else {
				LOGGER.warn(
						"getLabel() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("getLabel() -> idInstitucion del token nula");
		}

		LOGGER.info("getLabel() -> Salida del servicio para obtener los de grupos de clientes");
		return comboDTO;
	}

	@Override
	public ColegiadoDTO searchColegiado(ColegiadoItem colegiadoItem, HttpServletRequest request) throws ParseException {

		LOGGER.info("searchColegiado() -> Entrada al servicio para obtener colegiados");
		Error error = new Error();
		ColegiadoDTO colegiadosDTO = new ColegiadoDTO();
		List<GenParametros> tamMax = null;
		Integer tamMaximo = null;
		
		List<ColegiadoItem> colegiadoItemList = new ArrayList<ColegiadoItem>();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			if (null != colegiadoItem.getSearchLoggedUser() && colegiadoItem.getSearchLoggedUser()) {
				colegiadoItem.setNif(dni);
			}
		GenParametrosExample genParametrosExample = new GenParametrosExample();
	    genParametrosExample.createCriteria().andModuloEqualTo("CEN").andParametroEqualTo("TAM_MAX_BUSQUEDA_COLEGIADO").andIdinstitucionIn(Arrays.asList(SigaConstants.IDINSTITUCION_0_SHORT, idInstitucion));
	    genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
	    LOGGER.info("searchColegiado() / genParametrosExtendsMapper.selectByExample() -> Entrada a genParametrosExtendsMapper para obtener tamaño máximo consulta");
	    tamMax = genParametrosExtendsMapper.selectByExample(genParametrosExample);
	    LOGGER.info("searchColegiado() / genParametrosExtendsMapper.selectByExample() -> Salida a genParametrosExtendsMapper para obtener tamaño máximo consulta");
        if (tamMax != null) {
            tamMaximo = Integer.valueOf(tamMax.get(0).getValor());
        } else {
            tamMaximo = null;
        }
	    
        //Recuperamos los colegiados con el ultimo estado con fecha anterior al dia de hoy
		colegiadoItemList = cenColegiadoExtendsMapper.selectColegiados(idInstitucion, colegiadoItem, tamMaximo);
		
		//Recorremos los colegiados, si su ultimo estado es con fecha hacia el futuro le cambiamos el estado a "Sin estado"
		if(colegiadoItemList != null && !colegiadoItemList.isEmpty()) {
			for (int i = 0; i < colegiadoItemList.size(); i++) {
				if(colegiadoItemList.get(i).getFechaEstado() != null && colegiadoItemList.get(i).getFechaEstado().after(new Date())) {
					colegiadoItemList.get(i).setEstadoColegial("Sin estado");
				} else {
					//Formateamos la fecha que viene en string
					Date fechaEst = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(colegiadoItemList.get(i).getFechaEstadoStr());
					
					if(fechaEst.after(new Date())) {
						colegiadoItemList.get(i).setEstadoColegial("Sin estado");
					}
				}
			}
		}
		
		//colegiadosDTO.setColegiadoItem(colegiadoItemList);
		if((colegiadoItemList != null) && tamMaximo != null && (colegiadoItemList.size()) > tamMaximo) {
			error.setCode(200);
			error.setDescription("La consulta devuelve más de " + tamMaximo + " resultados, pero se muestran sólo los " + tamMaximo + " más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
			colegiadosDTO.setError(error);
			colegiadoItemList.remove(colegiadoItemList.size()-1);
			}
		colegiadosDTO.setColegiadoItem(colegiadoItemList);
			if (colegiadoItemList == null || colegiadoItemList.size() == 0) {

				LOGGER.warn(
						"searchColegiado() / cenColegiadoExtendsMapper.searchColegiado() -> No existen colegiados con las condiciones recibidas en la Institucion = "
								+ idInstitucion);
			}

		} else {
			LOGGER.warn("searchColegiado() -> idInstitucion del token nula");
		}

		return colegiadosDTO;
	}

	@Override
	public ColegiadoDTO searchColegiadoFicha(ColegiadoItem colegiadoItem, HttpServletRequest request) {

		LOGGER.info("searchColegiado() -> Entrada al servicio para obtener colegiados");

		ColegiadoDTO colegiadosDTO = new ColegiadoDTO();

		List<ColegiadoItem> colegiadoItemList = new ArrayList<ColegiadoItem>();

		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			if (null != colegiadoItem.getSearchLoggedUser() && colegiadoItem.getSearchLoggedUser()) {
				colegiadoItem.setNif(dni);

			}
			colegiadoItemList = cenColegiadoExtendsMapper.selectColegiado(idInstitucion, colegiadoItem);
			colegiadosDTO.setColegiadoItem(colegiadoItemList);

			if (colegiadoItemList == null || colegiadoItemList.size() == 0) {

				LOGGER.warn(
						"searchColegiado() / cenColegiadoExtendsMapper.searchColegiado() -> No existen colegiados con las condiciones recibidas en la Institucion = "
								+ idInstitucion);
			}

		} else {
			LOGGER.warn("searchColegiado() -> idInstitucion del token nula");
		}

		return colegiadosDTO;
	}

	@Override
	public ResponseFileDTO generateExcel(ColegiadoItem colegiadoItem, HttpServletRequest request) {

		LOGGER.info("generateExcel() -> Entrada del servicio para generar el excel de los colegiados");

		String token = request.getHeader("Authorization");
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		ResponseFileDTO response = new ResponseFileDTO();
		File excel = null;
		List<Map<String, Object>> result = null;

		if (null != idInstitucion) {

			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));

			LOGGER.info(
					"generateExcel() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");

			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);

			LOGGER.info(
					"generateExcel() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {

				AdmUsuarios usuario = usuarios.get(0);

				String sentencia = selectColegiados(idInstitucion, colegiadoItem, usuario.getIdlenguaje());

				LOGGER.info(
						"generateExcel() / conConsultasExtendsMapper.ejecutarConsultaString() -> Entrada a conConsultasExtendsMapper para obtener lista de colegiados");

				result = conConsultasExtendsMapper.ejecutarConsultaString(sentencia);

				LOGGER.info(
						"generateExcel() / conConsultasExtendsMapper.ejecutarConsultaString() -> Salida a conConsultasExtendsMapper para obtener lista de colegiados");

				if (result != null && result.size() > 0) {

					try {
						Workbook workBook = crearExcel(result);
						
						// Obtenemos la ruta temporal
						GenPropertiesKey key = new GenPropertiesKey();
						key.setFichero(SigaConstants.FICHERO_SIGA);
						key.setParametro(SigaConstants.parametroRutaSalidaInformes);

						LOGGER.info(
								"generateExcel() / genPropertiesMapper.selectByPrimaryKey() -> Entrada a genPropertiesMapper para obtener la ruta donde generar el excel");

						GenProperties rutaFicherosSalida = genPropertiesMapper.selectByPrimaryKey(key);

						LOGGER.info(
								"generateExcel() / genPropertiesMapper.selectByPrimaryKey() -> Salida a genPropertiesMapper para obtener la ruta donde generar el excel");

						String rutaTmp = rutaFicherosSalida.getValor() + SigaConstants.pathSeparator + idInstitucion
								+ SigaConstants.pathSeparator + SigaConstants.carpetaTmp;

						File aux = new File(rutaTmp);
						// creo directorio si no existe
						aux.mkdirs();

						GenDiccionarioKey keyDiccionario = new GenDiccionarioKey();
						keyDiccionario.setIdlenguaje(usuario.getIdlenguaje());
						keyDiccionario.setIdrecurso("censo.nombre.fichero.generarexcel");

						LOGGER.info(
								"generateExcel() / genPropertiesMapper.selectByPrimaryKey() -> Entrada a genPropertiesMapper para obtener la ruta donde generar el excel");

						GenDiccionario nombreFicherosSalida = genDiccionarioMapper.selectByPrimaryKey(keyDiccionario);

						LOGGER.info(
								"generateExcel() / genPropertiesMapper.selectByPrimaryKey() -> Salida a genPropertiesMapper para obtener la ruta donde generar el excel");

						String nombreFichero = nombreFicherosSalida.getDescripcion() + new Date().getTime() + ".xlsx";
						excel = new File(rutaTmp, nombreFichero);
						FileOutputStream fileOut;

						fileOut = new FileOutputStream(rutaTmp + SigaConstants.pathSeparator + nombreFichero);
						workBook.write(fileOut);
						fileOut.close();
						workBook.close();

						response.setFile(excel);
						response.setResultados(true);

					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					response.setResultados(false);
				}

			}

		}

		LOGGER.info("generateExcel() -> Salida del servicio para generar el excel de los colegiados");

		return response;

	}

	private Workbook crearExcel(List<Map<String, Object>> result) {

		LOGGER.info("crearExcel() -> Entrada del servicio para crear el excel con los datos de los colegiados");

		// Creamos el libro de excel
		Workbook workbook = new SXSSFWorkbook(EXCEL_ROW_FLUSH);
		Sheet sheet = workbook.createSheet("Query");

		// Le aplicamos estilos a las cabeceras
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		// headerFont.setItalic(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.BLUE.getIndex());
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		Row headerRow = sheet.createRow(0);

		// Recorremos el map y vamos metiendo celdas
		List<String> columnsKey = new ArrayList<String>();
		int rowNum = 1;
		int index = 0;
		Row row = null;

		Map<Integer, CellStyle> mapaEstilos = new HashMap<Integer, CellStyle>();

		CellStyle cellStyleNum = workbook.createCellStyle();
		cellStyleNum.setAlignment(CellStyle.ALIGN_RIGHT);
		
		CellStyle cellStyleString = workbook.createCellStyle();
		cellStyleString.setAlignment(CellStyle.ALIGN_LEFT);
		
		Object campo = null;
		XSSFRichTextString textCell = null;
		
		if (result.size() > 0) {
			for (String value : result.get(0).keySet()) {
				Cell cell = headerRow.createCell(index);
				cell.setCellValue(value);
				cell.setCellStyle(headerCellStyle);
				columnsKey.add(value);
				index++;
			}

			for (Map<String, Object> map : result) {
				
				if (map != null) {
	
					row = sheet.createRow(rowNum++);
					int cell = 0;
	
					
					for (int j = 0; j < columnsKey.size(); j++) {
						campo = map.get(columnsKey.get(j).trim());
						
						if (campo == null || campo.toString().trim() == "") {
							row.createCell(cell).setCellValue("");
						} else {
							Cell celda = row.createCell(cell);
							if (campo instanceof Number) {
								if (!mapaEstilos.containsKey(cell)) {
									mapaEstilos.put(cell, cellStyleNum);
								}
								celda.setCellType(Cell.CELL_TYPE_NUMERIC);
								celda.setCellValue(Double.parseDouble(campo.toString()));
								
							} else if (campo instanceof Date) {
								if (!mapaEstilos.containsKey(cell)) {
									mapaEstilos.put(cell, cellStyleString);
								}
								
								CreationHelper creationHelper = workbook.getCreationHelper();
								
								celda.setCellValue((Date) campo);
								
								CellStyle style1 = workbook.createCellStyle();
								style1.setDataFormat(creationHelper.createDataFormat().getFormat(
										"dd/mm/yyyy hh:mm"));
								celda.setCellStyle(style1);
								
							} else {
								if (!mapaEstilos.containsKey(cell)) {
									mapaEstilos.put(cell, cellStyleString);
								}
								
								celda.setCellType(Cell.CELL_TYPE_STRING);
								textCell = new XSSFRichTextString(campo.toString());
								celda.setCellValue(textCell);
							}
						}
						cell++;
						
					}
				}
			}

			for (int i = 0; i < index; i++) {
				//sheet.autoSizeColumn(j);
				if (mapaEstilos.containsKey(i)) {
					sheet.setDefaultColumnStyle(i, mapaEstilos.get(i));
				}
			}
		}

		LOGGER.info("crearExcel() -> Salida del servicio para crear el excel con los datos de los colegiados");

		return workbook;

	}

	private String selectColegiados(Short idInstitucion, ColegiadoItem colegiadoItem, String idLenguaje) {

		LOGGER.info(
				"selectColegiados() -> Entrada del servicio para obtener la sentencia para obtener la lista de colegiados");

		SQL sql = new SQL();

		// En el caso de que venga de la pantalla de busqueda colegiados/no colegiados,
		// tendremos que preparar el filtro de instituciones
		String instituciones = "";
		if (colegiadoItem.getColegio() != null && colegiadoItem.getColegio().length > 0) {
			if (colegiadoItem.getColegio().length > 1) {
				for (String string : colegiadoItem.getColegio()) {
					instituciones += "'" + string + "'";
					instituciones += ",";
				}
				instituciones = instituciones.substring(0, instituciones.length() - 1);
			} else if (colegiadoItem.getColegio().length == 1) {
				instituciones = "'" + colegiadoItem.getColegio()[0] + "'";
			}
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		sql.SELECT("f_siga_getncol_ncom(col.idinstitucion,col.idpersona) AS NcolNcom");
        sql.SELECT("decode (col.comunitario,'1','SI','0','NO') AS Comunitario");
        sql.SELECT("per.nombre AS Nombre");
        sql.SELECT("per.apellidos1 AS Apellido1");
        sql.SELECT("per.apellidos2 AS Apellido2");
        sql.SELECT("f_siga_getrecurso(tip.descripcion,1) AS TipoIdentificacion");
        sql.SELECT("per.nifcif AS NifCif");
        sql.SELECT("Per.Fechanacimiento AS FechadeNacimiento");
        sql.SELECT("f_siga_getrecurso(est.descripcion,1) AS EstadoCivil");
        sql.SELECT("per.naturalde AS Naturalde");
        sql.SELECT("decode (per.fallecido,'1','SI','NO') AS Fallecido");
        sql.SELECT("decode (per.sexo,'M','MUEJR','HOMBRE') AS Sexo");
        sql.SELECT("Cli.Fechaalta AS Fechaalta");
        sql.SELECT("f_siga_getrecurso(tra.descripcion,1) AS Tratamiento");
        sql.SELECT("cli.caracter AS Caracter");
        sql.SELECT("cli.publicidad AS Publicidad");
        sql.SELECT("cli.comisiones AS Comisiones");
        sql.SELECT("cli.guiajudicial AS GuiaJudicial");
        sql.SELECT("cli.abonosbanco AS AbonosBanco");
        sql.SELECT("cli.cargosbanco AS CargosBanco");
        sql.SELECT("decode (cli.idlenguaje,'1','Castellano','2','Catalá','3','Euskera','4','Galego') AS Lenguaje");
        sql.SELECT("cli.fotografia AS Fotografia");
        sql.SELECT("cli.asientocontable AS AsientoContable");
        sql.SELECT("Cli.Fechacarga AS FechaCarga");
        sql.SELECT("cli.letrado AS Letrado");
        sql.SELECT("Cli.Fechaactualizacion AS FechaActualizacion");
        sql.SELECT("Cli.Fechaexportcenso AS FechaExportCenso");
        sql.SELECT("cli.noenviarrevista AS NoenviaRevista");
        sql.SELECT("cli.noaparecerredabogacia AS NoapareceRedAbogacia");
        sql.SELECT("Col.Fechapresentacion AS FechaPresentacion");
        sql.SELECT("Col.Fechaincorporacion AS FechaIncorporacion");
        sql.SELECT("col.indtitulacion AS IndTitulacion");
        sql.SELECT("col.jubilacioncuota AS JubilacionCuota");
        sql.SELECT("decode (col.situacionejercicio,'1','Alta','0','Baja') AS SituacionEjercicio");
        sql.SELECT("decode (col.situacionresidente,'1','SI','0','NO') AS SituacionResidente");
        sql.SELECT("col.situacionempresa AS SituacionEmpresa");
        sql.SELECT("Col.Fechajura AS FechaJura");
        sql.SELECT("Col.Fechatitulacion AS FechaTitulacion");
        sql.SELECT("decode (col.otroscolegios,'1','SI','0','NO') as OtrosColegios");
        sql.SELECT("Col.Fechadeontologia AS FechaDeontologia");
        sql.SELECT("Col.Fechamovimiento AS FechaMovimiento");
        sql.SELECT("f_siga_getrecurso(ts.nombre,1) AS TipoSeguro");
        sql.SELECT("col.cuentacontablesjcs AS CuentaContableSJCS");
        sql.SELECT("decode (f_siga_gettipocliente(col.idpersona,col.idinstitucion,sysdate),'10','No Ejerciente','20','Ejerciente','30','Baja Colegial','40','Inhabilitacion','50','Suspension Ejercicio','60','Baja por Deceso','Baja por Deceso') AS EstadoColegial");
        sql.SELECT("f_siga_getfechaestadocolegial(col.idpersona,col.idinstitucion,sysdate)   AS FechaEstado");
        sql.SELECT("dir.domicilio AS Domicilio");
        sql.SELECT("dir.codigopostal AS CodigoPostal");
        sql.SELECT("dir.telefono1 AS Telefono1");
        sql.SELECT("dir.telefono2 AS Telefono2");
        sql.SELECT("dir.movil AS Movil");
        sql.SELECT("dir.fax1 AS Fax1");
        sql.SELECT("dir.fax2 AS Fax2");
        sql.SELECT("dir.correoelectronico AS CorreoElectronico");
        sql.SELECT("dir.paginaweb AS PaginaWeb");
        sql.SELECT("CASE WHEN dir.poblacionextranjera IS NULL THEN pob.nombre ELSE dir.poblacionextranjera || '(' || pais.nombre || ')' END AS Poblacion");
        sql.SELECT("CASE WHEN dir.poblacionextranjera IS NULL THEN pro.nombre ELSE NULL END AS Provincia");
        sql.SELECT("f_siga_getrecurso(pais.nombre, 1) AS Pais");


		/*String from = "cen_persona per, cen_colegiado col, cen_tipoidentificacion tip, cen_estadocivil est, cen_cliente cli, cen_tratamiento tra, cen_tiposseguro ts";

		sql.WHERE("col.idpersona=per.idpersona");
		sql.WHERE("per.idtipoidentificacion not in '20'");
		sql.WHERE("per.idtipoidentificacion=tip.idtipoidentificacion");
		sql.WHERE("est.idestadocivil(+)=per.idestadocivil");
		sql.WHERE("cli.idpersona=col.idpersona");
		sql.WHERE("cli.idinstitucion=col.idinstitucion");
		sql.WHERE("tra.idtratamiento(+)=cli.idtratamiento");
		sql.WHERE("col.idtiposseguro=ts.idtiposseguro(+)");
*/
		sql.FROM("cen_colegiado col");
		sql.LEFT_OUTER_JOIN("cen_direcciones dir ON (dir.idinstitucion = col.idinstitucion AND dir.idpersona = col.idpersona AND dir.fechabaja IS NULL AND "
				+ "EXISTS ( SELECT 1 FROM cen_direccion_tipodireccion tipdir WHERE dir.idinstitucion = tipdir.idinstitucion AND dir.idpersona = tipdir.idpersona AND "
				+ "dir.iddireccion = tipdir.iddireccion AND tipdir.idtipodireccion = '3'))");
		sql.LEFT_OUTER_JOIN("cen_poblaciones pob ON pob.idpoblacion = dir.idpoblacion");
		sql.LEFT_OUTER_JOIN("cen_provincias pro ON pro.idprovincia = dir.idprovincia");
		sql.LEFT_OUTER_JOIN("cen_pais pais ON pais.idpais = dir.idpais");
		sql.INNER_JOIN("cen_persona per on col.idpersona = per.idpersona");
		sql.INNER_JOIN("cen_institucion inst on col.idinstitucion = inst.idinstitucion");

			
		if (idInstitucion != Short.parseShort("2000") && idInstitucion != Short.parseShort("3500")) {
			if (idInstitucion > Short.parseShort("2001") && idInstitucion < Short.parseShort("2100") ) {
				sql.INNER_JOIN("cen_cliente cli on (col.idpersona = cli.idpersona and col.idinstitucion = cli.idinstitucion)");
				//sql.INNER_JOIN("cen_cliente cli on (col.idpersona = cli.idpersona and col.idinstitucion = cli.idinstitucion)");
			}
			else{
				sql.INNER_JOIN("cen_cliente cli on (col.idpersona = cli.idpersona and inst.cen_inst_IDINSTITUCION  =  cli.idinstitucion)");
				//sql.INNER_JOIN("cen_cliente cli on (col.idpersona = cli.idpersona and inst.cen_inst_IDINSTITUCION  =  cli2.idinstitucion)");
			}
			
		}else {
            sql.INNER_JOIN("cen_cliente cli on (col.idpersona = cli.idpersona and cli.idinstitucion =  '"+ idInstitucion + "')");
			sql.INNER_JOIN("cen_cliente cli2 on (col.idpersona = cli2.idpersona and col.idinstitucion = cli2.idinstitucion)");
		}
		sql.INNER_JOIN(
				"CEN_DATOSCOLEGIALESESTADO colest on (col.idpersona = colest.idpersona and col.idinstitucion = colest.idinstitucion  and colest.fechaestado = (\r\n"
						+ "                                            select max(datcol.fechaestado) from CEN_DATOSCOLEGIALESESTADO datcol where datcol.idpersona = colest.idpersona and datcol.idinstitucion = colest.idinstitucion"
						+ " and datcol.fechaestado < sysdate))");
		
		sql.INNER_JOIN("cen_tipoidentificacion tip on (per.idtipoidentificacion = tip.idtipoidentificacion)");
		sql.LEFT_OUTER_JOIN("cen_estadocivil est on ( est.idestadocivil = per.idestadocivil)");
		sql.LEFT_OUTER_JOIN(" cen_tratamiento tra on (tra.idtratamiento = cli.idtratamiento)");
		sql.LEFT_OUTER_JOIN(" cen_tiposseguro ts  on ( col.idtiposseguro = ts.idtiposseguro)") ;     
		
		
		
		
		if (colegiadoItem.getIdgrupo() != null && colegiadoItem.getIdgrupo().length > 0) {
		sql.LEFT_OUTER_JOIN("cen_gruposcliente_cliente grucli on \r\n"
				+ "    ((grucli.idinstitucion = inst.idinstitucion or grucli.idinstitucion = '2000') and col.idpersona = grucli.idpersona and ((grucli.fecha_inicio <= SYSDATE OR grucli.fecha_inicio IS NULL ) and \r\n"
				+ "        ( grucli.fecha_baja > SYSDATE OR grucli.fecha_baja IS NULL)))");
		}
		
		
		
	/*	sql.INNER_JOIN("cen_estadocolegial estcol on (colest.idestado = estcol.idestado)");
		sql.INNER_JOIN("gen_recursos_catalogos cat on (estcol.descripcion = cat.idrecurso and cat.idlenguaje = '1')");
		*/
		if(!UtilidadesString.esCadenaVacia(colegiadoItem.getDomicilio()) || !UtilidadesString.esCadenaVacia(colegiadoItem.getIdPoblacion()) 
				|| !UtilidadesString.esCadenaVacia(colegiadoItem.getIdProvincia()) || !UtilidadesString.esCadenaVacia(colegiadoItem.getTelefono())
				|| !UtilidadesString.esCadenaVacia(colegiadoItem.getCorreo()) || !UtilidadesString.esCadenaVacia(colegiadoItem.getTipoDireccion()) 
				|| !UtilidadesString.esCadenaVacia(colegiadoItem.getMovil()) || !UtilidadesString.esCadenaVacia(colegiadoItem.getCodigoPostal())) {
			
			sql.LEFT_OUTER_JOIN(
					"cen_direcciones dir on (cli.idpersona = dir.idpersona and cli.idinstitucion = dir.idinstitucion and inst.idinstitucion = dir.idinstitucion and dir.fechabaja is null)");

			sql.LEFT_OUTER_JOIN("CEN_DIRECCION_TIPODIRECCION TIPODIR ON (CLI.IDPERSONA = TIPODIR.IDPERSONA AND"  
		                + " DIR.IDDIRECCION = TIPODIR.IDDIRECCION AND CLI.IDINSTITUCION = TIPODIR.IDINSTITUCION AND "
		                + " INST.IDINSTITUCION = DIR.IDINSTITUCION)"); 
		}
		
		if ((colegiadoItem.getTipoCV() != null && colegiadoItem.getTipoCV() != "") || (colegiadoItem.getSubTipoCV1() != null && colegiadoItem.getSubTipoCV1() != "") || (colegiadoItem.getSubTipoCV2() != null && colegiadoItem.getSubTipoCV2() != "")) {
			sql.LEFT_OUTER_JOIN(
					"cen_datosCV datosCV ON ( datosCV.idInstitucion = col.idInstitucion and datosCV.idPersona = per.idPersona )");
			
			sql.LEFT_OUTER_JOIN("cen_tiposcv cenTipoCV ON ( cenTipoCV.idTipoCV = datosCV.idTipoCV )");
			sql.LEFT_OUTER_JOIN("cen_tiposcvsubtipo2 subt2 ON ( subt2.idTipoCV = datosCV.idTipoCV and subt2.idInstitucion = col.idInstitucion )");
			sql.LEFT_OUTER_JOIN("cen_tiposcvsubtipo1 subt1 ON ( subt1.idTipoCV = datosCV.idTipoCV and subt1.idInstitucion = col.idInstitucion )");
		}
		if(!instituciones.equals("")) {
			sql.WHERE("COL.IDINSTITUCION IN (" + instituciones + ")");
		} else {
			if (idInstitucion != Short.parseShort("2000") && idInstitucion != Short.parseShort("3500")) {
				if (idInstitucion > Short.parseShort("2001") && idInstitucion < Short.parseShort("2100") ) {
					sql.WHERE("COL.IDINSTITUCION = '" + idInstitucion + "'");
				}
				else{
                    sql.WHERE("inst.cen_inst_IDINSTITUCION = '" + idInstitucion + "'");

				}
				
			}
		}
		
		sql.WHERE("per.idtipoidentificacion not in '20'");

		if (colegiadoItem.getNif() != null && colegiadoItem.getNif() != "") {
			sql.WHERE("upper(per.nifcif) like upper('%" + colegiadoItem.getNif() + "%')");
		}

		if (colegiadoItem.getNombre() != null && colegiadoItem.getNombre() != "") {
			String columna = "per.nombre";
			String cadena = colegiadoItem.getNombre();
			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
//			sql.WHERE("upper(per.nombre) like upper('%" + colegiadoItem.getNombre() + "%')");
		}

		if (colegiadoItem.getApellidos() != null && colegiadoItem.getApellidos() != "") {
			
			String columna = "REPLACE(CONCAT(per.apellidos1,per.apellidos2), ' ', '')";
			String cadena = colegiadoItem.getApellidos().replaceAll("\\s+","%"); 
			
			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
			
//			sql.WHERE("UPPER(CONCAT(per.apellidos1,per.apellidos2)) LIKE UPPER('%" +colegiadoItem.getApellidos().replaceAll("\\s+","")
//					+ "%')");
		}
		
		if (colegiadoItem.getNumColegiado() != null && colegiadoItem.getNumColegiado() != "") {
			sql.WHERE("(decode(col.comunitario,1,col.ncomunitario,col.ncolegiado) = '" + colegiadoItem.getNumColegiado() + "')");
		}

		if (colegiadoItem.getSexo() != null && colegiadoItem.getSexo() != "") {
			sql.WHERE("per.sexo = '" + colegiadoItem.getSexo() + "'");
		}

		if (colegiadoItem.getCodigoPostal() != null && colegiadoItem.getCodigoPostal() != "") {
			sql.WHERE("dir.codigopostal ='" + colegiadoItem.getCodigoPostal() + "'");
		}

		if (colegiadoItem.getTipoDireccion() != null && colegiadoItem.getTipoDireccion() != "") {
			sql.WHERE("tipodir.idtipodireccion = "+ colegiadoItem.getTipoDireccion());
		}else {
			/*sql.WHERE("(tipodir.idtipodireccion = 2 OR 2 NOT IN (SELECT idtipodireccion FROM CEN_DIRECCION_TIPODIRECCION TIPODIR2 "
					+ "WHERE TIPODIR.IDPERSONA = TIPODIR2.IDPERSONA  AND TIPODIR.IDINSTITUCION = TIPODIR2.IDINSTITUCION ))");*/
		}

		if (colegiadoItem.getIdEstadoCivil() != null && colegiadoItem.getIdEstadoCivil() != "") {
			sql.WHERE("per.idestadocivil = '" + colegiadoItem.getIdEstadoCivil() + "'");
		}

		if (colegiadoItem.getIdProvincia() != null && colegiadoItem.getIdProvincia() != "") {
			sql.WHERE("dir.idprovincia = '" + colegiadoItem.getIdProvincia() + "'");
		}

		if (colegiadoItem.getIdPoblacion() != null && colegiadoItem.getIdPoblacion() != "") {
			sql.WHERE("dir.idpoblacion = '" + colegiadoItem.getIdPoblacion() + "'");
		}

		if (colegiadoItem.getDomicilio() != null && colegiadoItem.getDomicilio() != "") {
			sql.WHERE("(dir.domicilio) like upper('" + colegiadoItem.getDomicilio() + "')");
		}

		if (colegiadoItem.getCorreo() != null && colegiadoItem.getCorreo() != "") {
			String columna = "dir.correoelectronico";
			String cadena = colegiadoItem.getCorreo();
			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));
			
//			sql.WHERE("upper(dir.correoelectronico) LIKE upper('%" + colegiadoItem.getCorreo() + "%')");
		}

		if (colegiadoItem.getTelefono() != null && colegiadoItem.getTelefono() != "") {
			sql.WHERE("dir.telefono1 like '%" + colegiadoItem.getTelefono() + "%'");
		}

		if (colegiadoItem.getMovil() != null && colegiadoItem.getMovil() != "") {
			sql.WHERE("dir.movil like '%" + colegiadoItem.getMovil() + "%'");
		}

		if (colegiadoItem.getTipoCV() != null && colegiadoItem.getTipoCV() != "") {
			sql.WHERE("datoscv.idtipocv = '" + colegiadoItem.getTipoCV() + "'");
		}

		if (colegiadoItem.getSubTipoCV1() != null && colegiadoItem.getSubTipoCV1() != "") {
			sql.WHERE("datoscv.idtipocvsubtipo1 = '" + colegiadoItem.getSubTipoCV1() + "'");
		}
		
		if (colegiadoItem.getSubTipoCV2() != null && colegiadoItem.getSubTipoCV2() != "") {
			sql.WHERE("datoscv.idtipocvsubtipo2 = '" + colegiadoItem.getSubTipoCV2() + "'");
		}

		if (colegiadoItem.getSituacion() != null && colegiadoItem.getSituacion() != "") {
			sql.WHERE("colest.idestado ='" + colegiadoItem.getSituacion() + "'");
		}

		if (colegiadoItem.getResidencia() != null && colegiadoItem.getResidencia() != "") {
			sql.WHERE("col.situacionresidente ='" + colegiadoItem.getResidencia() + "'");
		}

		if (colegiadoItem.getInscrito() != null && colegiadoItem.getInscrito() != "") {
			sql.WHERE("col.comunitario ='" + colegiadoItem.getInscrito() + "'");
		}


		if (colegiadoItem.getIdgrupo() != null && colegiadoItem.getIdgrupo().length > 0) {

			String etiquetas = "";
					
			for (int i = 0; colegiadoItem.getIdgrupo().length > i; i++) {

				if (i == colegiadoItem.getIdgrupo().length - 1) {
					etiquetas += "( grucli.IDGRUPO ='" + colegiadoItem.getIdgrupo()[i].getValue() + "' and grucli.IDINSTITUCION_GRUPO = '" + colegiadoItem.getIdgrupo()[i].getIdInstitucion() + "')";
				} else {
					etiquetas += "( grucli.IDGRUPO ='" + colegiadoItem.getIdgrupo()[i].getValue() + "' and grucli.IDINSTITUCION_GRUPO = '" + colegiadoItem.getIdgrupo()[i].getIdInstitucion() + "') or";

				}
			}

			sql.WHERE("(" + etiquetas + ")");
		}

		if (colegiadoItem.getFechaIncorporacion() != null && colegiadoItem.getFechaIncorporacion().length != 0) {

			if (colegiadoItem.getFechaIncorporacion()[0] != null && colegiadoItem.getFechaIncorporacion()[1] != null) {

				String fechaIncorporacionDesde = dateFormat.format(colegiadoItem.getFechaIncorporacion()[0]);
				String fechaIncorporacionHasta = dateFormat.format(colegiadoItem.getFechaIncorporacion()[1]);

				sql.WHERE("(TO_CHAR(col.fechaincorporacion,'DD/MM/YYYY') >= TO_DATE('" + fechaIncorporacionDesde
						+ "','DD/MM/YYYY') " + " and ( TO_CHAR(col.fechaincorporacion,'DD/MM/YYYY') <= TO_DATE('"
						+ fechaIncorporacionHasta + "','DD/MM/YYYY')))");

			} else if (colegiadoItem.getFechaIncorporacion()[0] != null
					&& colegiadoItem.getFechaIncorporacion()[1] == null) {

				String fechaIncorporacionDesde = dateFormat.format(colegiadoItem.getFechaIncorporacion()[0]);

				sql.WHERE("(TO_CHAR(col.fechaincorporacion,'DD/MM/YYYY') >= TO_DATE('" + fechaIncorporacionDesde
						+ "','DD/MM/YYYY'))");

			} else if (colegiadoItem.getFechaIncorporacion()[0] == null
					&& colegiadoItem.getFechaIncorporacion()[1] != null) {

				String fechaIncorporacionHasta = dateFormat.format(colegiadoItem.getFechaIncorporacion()[1]);

				sql.WHERE("(TO_CHAR(col.fechaincorporacion,'DD/MM/YYYY') <= TO_DATE('" + fechaIncorporacionHasta
						+ "','DD/MM/YYYY'))");
			}
		}


		
		if (colegiadoItem.getFechaNacimientoRango() != null && colegiadoItem.getFechaNacimientoRango().length != 0) {

			if (colegiadoItem.getFechaNacimientoRango()[0] != null && colegiadoItem.getFechaNacimientoRango()[1] != null) {

				String getFechaNacimientoDesde = dateFormat.format(colegiadoItem.getFechaNacimientoRango()[0]);
				String getFechaNacimientoHasta = dateFormat.format(colegiadoItem.getFechaNacimientoRango()[1]);

				sql.WHERE("(TO_CHAR(per.fechanacimiento, 'DD-MM-YYYY') >= TO_DATE('" + getFechaNacimientoDesde
						+ "','DD/MM/YYYY') " + " and ( TO_CHAR(per.fechanacimiento, 'DD-MM-YYYY') <= TO_DATE('"
						+ getFechaNacimientoHasta + "','DD/MM/YYYY')))");

			} else if (colegiadoItem.getFechaNacimientoRango()[0] != null
					&& colegiadoItem.getFechaNacimientoRango()[1] == null) {

				String getFechaNacimientoDesde = dateFormat.format(colegiadoItem.getFechaNacimientoRango()[0]);

				sql.WHERE("(TO_CHAR(per.fechanacimiento, 'DD-MM-YYYY') >= TO_DATE('" + getFechaNacimientoDesde
						+ "','DD/MM/YYYY'))");

			} else if (colegiadoItem.getFechaNacimientoRango()[0] == null
					&& colegiadoItem.getFechaNacimientoRango()[1] != null) {

				String getFechaNacimientoHasta = dateFormat.format(colegiadoItem.getFechaNacimientoRango()[1]);

				sql.WHERE("( TO_CHAR(per.fechanacimiento, 'DD-MM-YYYY') <= TO_DATE('" + getFechaNacimientoHasta
						+ "','DD/MM/YYYY'))");
			}
		}




		
		
		GenParametrosExample genParametrosExample = new GenParametrosExample();
		
		List<Short> idInstituciones = new ArrayList<>();
		idInstituciones.add(idInstitucion);
		idInstituciones.add(SigaConstants.IDINSTITUCION_0_SHORT);
		
		genParametrosExample.createCriteria().andIdinstitucionIn(idInstituciones)
		.andParametroEqualTo("EXPORTAR_COLEGIADOS_ACOGIDOS_A_LOPD");
		
		genParametrosExample.setOrderByClause("IDINSTITUCION DESC");
		
		
		List<GenParametros> genParametros = genParametrosExtendsMapper.selectByExample(genParametrosExample);
		
		if(genParametros != null && genParametros.size() > 0) {
			
			GenParametros parametro = genParametros.get(0);
			
			if(parametro.getValor().equals("N")) {
				sql.WHERE("(decode(cli.noaparecerredabogacia,null,0,cli.noaparecerredabogacia) <> 1)");
			}
		}

		LOGGER.info(
				"selectColegiados() -> Salida del servicio para obtener la sentencia para obtener la lista de colegiados");
		return sql.toString();

	}

}
