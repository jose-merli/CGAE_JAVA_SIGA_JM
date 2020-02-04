package org.itcgae.siga.cen.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.ComboInstitucionDTO;
import org.itcgae.siga.DTOs.cen.ComboInstitucionItem;
import org.itcgae.siga.DTOs.com.ResponseFileDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.IBusquedaColegiadosService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenEstadocivilExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenEstadocolegialExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenTiposcvExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.ConConsultasExtendsMapper;
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
	public ColegiadoDTO searchColegiado(ColegiadoItem colegiadoItem, HttpServletRequest request) {

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
			colegiadoItemList = cenColegiadoExtendsMapper.selectColegiados(idInstitucion, colegiadoItem);
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
		ResponseFileDTO response = new ResponseFileDTO();
		File excel = null;
		List<Map<String, Object>> result = null;

		if (null != idInstitucion) {

			String sentenciaIdPersonas = selectIdPersonas(idInstitucion, colegiadoItem);

			LOGGER.info(
					"generateExcel() / conConsultasExtendsMapper.ejecutarConsultaString() -> Entrada a conConsultasExtendsMapper para obtener lista de colegiados");

			List<Map<String, Object>> idPersonasList = conConsultasExtendsMapper
					.ejecutarConsultaString(sentenciaIdPersonas);

			LOGGER.info(
					"generateExcel() / conConsultasExtendsMapper.ejecutarConsultaString() -> Salida a conConsultasExtendsMapper para obtener lista de colegiados");

			if (idPersonasList != null && idPersonasList.size() > 0) {

				result = new ArrayList<>();

				for (Map<String, Object> map : idPersonasList) {

					String sentenciaColegiado = selectColegiados(idInstitucion, map.get("IDPERSONA").toString());

					LOGGER.info(
							"generateExcel() / conConsultasExtendsMapper.ejecutarConsultaString() -> Entrada a conConsultasExtendsMapper para obtener los datos de cada colegiado");

					List<Map<String, Object>> colegiado = conConsultasExtendsMapper
							.ejecutarConsultaString(sentenciaColegiado);

					LOGGER.info(
							"generateExcel() / conConsultasExtendsMapper.ejecutarConsultaString() -> Salida a conConsultasExtendsMapper para obtener los datos de cada colegiado");


					if(colegiado != null  && colegiado.size() > 0) {
						if(colegiado.get(0) != null ) {
							map.putAll(colegiado.get(0));
						}
					}
					map.remove("IDPERSONA");
					map.remove("IDINSTITUCION");
					result.add(map);

				}

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
						String nombreFichero = SigaConstants.nombreExcelConsulta + new Date().getTime() + ".xlsx";
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

				}

			} else {
				response.setResultados(false);
			}
		}

		LOGGER.info("generateExcel() -> Salida del servicio para generar el excel de los colegiados");

		return response;

	}

	private Workbook crearExcel(List<Map<String, Object>> result) {

		LOGGER.info("crearExcel() -> Entrada del servicio para crear el excel con los datos de los colegiados");

		// Creamos el libro de excel
		Workbook workbook = new XSSFWorkbook();
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
		if (result.size() > 0) {
			for (String value : result.get(0).keySet()) {
				Cell cell = headerRow.createCell(index);
				cell.setCellValue(value);
				cell.setCellStyle(headerCellStyle);
				columnsKey.add(value);
				index++;
			}

			for (Map<String, Object> map : result) {

				Row row = sheet.createRow(rowNum++);
				int cell = 0;

				for (int i = 0; i < columnsKey.size(); i++) {
					Object campo = map.get(columnsKey.get(i).trim());
					if (campo == null || campo.toString().trim() == "") {
						row.createCell(cell).setCellValue("");
					} else {
						row.createCell(cell).setCellValue(campo.toString());
					}
					cell++;
				}
			}

			for (int i = 0; i < index; i++) {
				sheet.autoSizeColumn(i);
			}
		}

		LOGGER.info("crearExcel() -> Salida del servicio para crear el excel con los datos de los colegiados");

		return workbook;

	}

	private String selectIdPersonas(Short idInstitucion, ColegiadoItem colegiadoItem) {

		LOGGER.info(
				"selectIdPersonas() -> Entrada del servicio para obtener la sentencia para obtener la lista de colegiados");

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

		sql.SELECT_DISTINCT("col.idpersona");
		sql.SELECT("col.idinstitucion");
		
		sql.SELECT("per.nombre");
		sql.SELECT("per.apellidos1");
		sql.SELECT("per.apellidos2");
		sql.SELECT("per.nifcif");
		sql.SELECT("per.idtipoidentificacion");
		sql.SELECT("To_Char(Per.Fechanacimiento, 'dd-mm-yyyy') fechanacimiento");
		sql.SELECT("per.idestadocivil");
		sql.SELECT("per.Naturalde");
		sql.SELECT("per.Fallecido");
		sql.SELECT("per.Sexo");
		
		SQL sqlEstadoCivil = new SQL();
		sqlEstadoCivil.SELECT("f_Siga_Getrecurso(Ec.Descripcion, 1)");
		sqlEstadoCivil.FROM("Cen_Estadocivil Ec");
		sqlEstadoCivil.WHERE("Per.Idestadocivil = Ec.Idestadocivil");
		
		sql.SELECT("(" + sqlEstadoCivil + ") Desc_Estadocivil");
		
		SQL sqlTipoIdentificacion = new SQL();
		sqlTipoIdentificacion.SELECT("f_Siga_Getrecurso(Ti.Descripcion, 1)");
		sqlTipoIdentificacion.FROM("Cen_Tipoidentificacion Ti");
		sqlTipoIdentificacion.WHERE("Per.Idtipoidentificacion = Ti.Idtipoidentificacion");
		
		sql.SELECT("(" + sqlTipoIdentificacion + ") Desc_Tipoidentificacion");
		
		sql.SELECT("To_Char(Cli.Fechaalta, 'dd-mm-yyyy') Fechaalta");
		sql.SELECT("Cli.Caracter");
		sql.SELECT("Cli.Publicidad");
		sql.SELECT("Cli.Guiajudicial");
		sql.SELECT("Cli.Cargosbanco");
		sql.SELECT("Cli.Abonosbanco");
		sql.SELECT("Cli.Comisiones");
		sql.SELECT("Cli.Idtratamiento");
		sql.SELECT("Cli.Idlenguaje");
		sql.SELECT("Cli.Fotografia");
		sql.SELECT("Cli.Asientocontable");
		sql.SELECT("To_Char(Cli.Fechacarga, 'dd-mm-yyyy') Fechacarga");
		sql.SELECT("Cli.Letrado");
		sql.SELECT("To_Char(Cli.Fechaactualizacion, 'dd-mm-yyyy') Fechaactualizacion");
		sql.SELECT("To_Char(Cli.Fechaexportcenso, 'dd-mm-yyyy') Fechaexportcenso");
		sql.SELECT("Cli.Noenviarrevista");
		sql.SELECT("Cli.Noaparecerredabogacia");
		
		SQL sqlTratamiento = new SQL();
		sqlTratamiento.SELECT("f_Siga_Getrecurso(Tra.Descripcion, 1)");
		sqlTratamiento.FROM("Cen_Tratamiento Tra");
		sqlTratamiento.WHERE("Cli.Idtratamiento = Tra.Idtratamiento");
		
		sql.SELECT("(" + sqlTratamiento + ") Desc_Tratamiento");
		
		SQL sqlLenguaje = new SQL();
		sqlLenguaje.SELECT("f_Siga_Getrecurso(Len.Descripcion, 1)");
		sqlLenguaje.FROM("Adm_Lenguajes Len");
		sqlLenguaje.WHERE("Cli.Idlenguaje = Len.Idlenguaje");
		
		sql.SELECT("(" + sqlLenguaje + ") Desc_Lenguaje");
		
		sql.SELECT("To_Char(Col.Fechapresentacion, 'dd-mm-yyyy') Fechapresentacion");
		sql.SELECT("To_Char(Col.Fechaincorporacion, 'dd-mm-yyyy') Fechaincorporacion");
		sql.SELECT("Col.Indtitulacion");
		sql.SELECT("Col.Jubilacioncuota");
		sql.SELECT("Col.Situacionejercicio");
		sql.SELECT("Col.Situacionresidente");
		sql.SELECT("Col.Situacionempresa");
		sql.SELECT("Col.Comunitario");
		sql.SELECT("Col.Ncolegiado");
		sql.SELECT("To_Char(Col.Fechajura, 'dd-mm-yyyy') Fechajura");
		sql.SELECT("Col.Ncomunitario");
		sql.SELECT("To_Char(Col.Fechatitulacion, 'dd-mm-yyyy') Fechatitulacion");
		sql.SELECT("Col.Otroscolegios");
		sql.SELECT("To_Char(Col.Fechadeontologia, 'dd-mm-yyyy') Fechadeontologia");
		sql.SELECT("To_Char(Col.Fechamovimiento, 'dd-mm-yyyy') Fechamovimiento");
		sql.SELECT("Col.Idtiposseguro");
		sql.SELECT("Col.Cuentacontablesjcs");
		
		SQL sqlTipoSeguro = new SQL();
		sqlTipoSeguro.SELECT("f_Siga_Getrecurso(Seg.Nombre, 1)");
		sqlTipoSeguro.FROM("Cen_Tiposseguro Seg");
		sqlTipoSeguro.WHERE("Col.Idtiposseguro = Seg.Idtiposseguro");
		
		sql.SELECT("(" + sqlTipoSeguro + ") Desc_Tiposeguro");
		
		SQL sqlEstadoColegial = new SQL();
		sqlEstadoColegial.SELECT("f_Siga_Getrecurso(Estcol.Descripcion, 1)");
		sqlEstadoColegial.FROM("Cen_Estadocolegial Estcol");
		sqlEstadoColegial.WHERE("Colest.Idestado = Estcol.Idestado");
		
		sql.SELECT("(" + sqlEstadoColegial + ") Estado_Colegial");
		
		sql.SELECT("To_Char(Colest.Fechaestado, 'dd-mm-yyyy') Fecha_Estado_Colegial");

		sql.FROM("cen_colegiado col");
		sql.INNER_JOIN("cen_persona per on col.idpersona = per.idpersona");
		sql.INNER_JOIN("cen_institucion inst on col.idinstitucion = inst.idinstitucion");

		if (idInstitucion != Short.parseShort("2000") && idInstitucion != Short.parseShort("3500")) {
			if (idInstitucion > Short.parseShort("2001") && idInstitucion < Short.parseShort("2100")) {
				sql.INNER_JOIN(
						"cen_cliente cli on (col.idpersona = cli.idpersona and col.idinstitucion = cli.idinstitucion)");
				sql.INNER_JOIN(
						"cen_cliente cli2 on (col.idpersona = cli2.idpersona and col.idinstitucion = cli2.idinstitucion)");
			} else {
				sql.INNER_JOIN(
						"cen_cliente cli on (col.idpersona = cli.idpersona and inst.cen_inst_IDINSTITUCION  =  cli.idinstitucion)");
				sql.INNER_JOIN(
						"cen_cliente cli2 on (col.idpersona = cli2.idpersona and inst.cen_inst_IDINSTITUCION  =  cli2.idinstitucion)");
			}

		} else {
			sql.INNER_JOIN("cen_cliente cli on (col.idpersona = cli.idpersona and cli.idinstitucion =  '"
					+ idInstitucion + "')");
			sql.INNER_JOIN(
					"cen_cliente cli2 on (col.idpersona = cli2.idpersona and col.idinstitucion = cli2.idinstitucion)");
		}

		if (colegiadoItem.getIdgrupo() != null && colegiadoItem.getIdgrupo().length > 0) {
			sql.LEFT_OUTER_JOIN("cen_gruposcliente_cliente grucli on \r\n"
					+ "    ((grucli.idinstitucion = inst.idinstitucion or grucli.idinstitucion = '2000') and col.idpersona = grucli.idpersona and ((grucli.fecha_inicio <= SYSDATE OR grucli.fecha_inicio IS NULL ) and \r\n"
					+ "        ( grucli.fecha_baja > SYSDATE OR grucli.fecha_baja IS NULL)))");
		}
		sql.INNER_JOIN(
				"CEN_DATOSCOLEGIALESESTADO colest on (col.idpersona = colest.idpersona and col.idinstitucion = colest.idinstitucion  and colest.fechaestado = (\r\n"
						+ "                                            select max(datcol.fechaestado) from CEN_DATOSCOLEGIALESESTADO datcol where datcol.idpersona = colest.idpersona and datcol.idinstitucion = colest.idinstitucion"
						+ " and datcol.fechaestado < sysdate))");
		
		if (!UtilidadesString.esCadenaVacia(colegiadoItem.getDomicilio()) ||  !UtilidadesString.esCadenaVacia(colegiadoItem.getCodigoPostal())
				|| !UtilidadesString.esCadenaVacia(colegiadoItem.getTelefono()) || !UtilidadesString.esCadenaVacia(colegiadoItem.getMovil()) ||
				!UtilidadesString.esCadenaVacia(colegiadoItem.getIdPoblacion()) || !UtilidadesString.esCadenaVacia(colegiadoItem.getIdProvincia()) || !UtilidadesString.esCadenaVacia(colegiadoItem.getTipoDireccion()) 
				|| !UtilidadesString.esCadenaVacia(colegiadoItem.getCorreo())){
			
			sql.LEFT_OUTER_JOIN(
					"cen_direcciones dir on (cli.idpersona = dir.idpersona and cli.idinstitucion = dir.idinstitucion and inst.idinstitucion = dir.idinstitucion and dir.fechabaja is null)");		
			sql.LEFT_OUTER_JOIN("CEN_DIRECCION_TIPODIRECCION TIPODIR ON (CLI.IDPERSONA = TIPODIR.IDPERSONA AND"
					+ " DIR.IDDIRECCION = TIPODIR.IDDIRECCION AND CLI.IDINSTITUCION = TIPODIR.IDINSTITUCION AND "
					+ " INST.IDINSTITUCION = DIR.IDINSTITUCION)");
		}
		

		if ((colegiadoItem.getTipoCV() != null && colegiadoItem.getTipoCV() != "")
				|| (colegiadoItem.getSubTipoCV1() != null && colegiadoItem.getSubTipoCV1() != "")
				|| (colegiadoItem.getSubTipoCV2() != null && colegiadoItem.getSubTipoCV2() != "")) {
			sql.LEFT_OUTER_JOIN(
					"cen_datosCV datosCV ON ( datosCV.idInstitucion = col.idInstitucion and datosCV.idPersona = per.idPersona )");

			sql.LEFT_OUTER_JOIN("cen_tiposcv cenTipoCV ON ( cenTipoCV.idTipoCV = datosCV.idTipoCV )");
			sql.LEFT_OUTER_JOIN(
					"cen_tiposcvsubtipo2 subt2 ON ( subt2.idTipoCV = datosCV.idTipoCV and subt2.idInstitucion = col.idInstitucion )");
			sql.LEFT_OUTER_JOIN(
					"cen_tiposcvsubtipo1 subt1 ON ( subt1.idTipoCV = datosCV.idTipoCV and subt1.idInstitucion = col.idInstitucion )");
		}
		
		if (!instituciones.equals("")) {
			sql.WHERE("COL.IDINSTITUCION IN (" + instituciones + ")");
		} else {
			if (idInstitucion != Short.parseShort("2000") && idInstitucion != Short.parseShort("3500")) {
				if (idInstitucion > Short.parseShort("2001") && idInstitucion < Short.parseShort("2100")) {
					sql.WHERE("COL.IDINSTITUCION = '" + idInstitucion + "'");
				} else {
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
			String cadena = colegiadoItem.getApellidos().replaceAll("\\s+", "");

			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));

//			sql.WHERE("UPPER(CONCAT(per.apellidos1,per.apellidos2)) LIKE UPPER('%" +colegiadoItem.getApellidos().replaceAll("\\s+","")
//					+ "%')");
		}

		if (colegiadoItem.getNumColegiado() != null && colegiadoItem.getNumColegiado() != "") {
			sql.WHERE("(col.ncolegiado = '" + colegiadoItem.getNumColegiado() + "' OR COL.NCOMUNITARIO = '"
					+ colegiadoItem.getNumColegiado() + "')");
		}

		if (colegiadoItem.getSexo() != null && colegiadoItem.getSexo() != "") {
			sql.WHERE("per.sexo = '" + colegiadoItem.getSexo() + "'");
		}

		if (colegiadoItem.getCodigoPostal() != null && colegiadoItem.getCodigoPostal() != "") {
			sql.WHERE("dir.codigopostal ='" + colegiadoItem.getCodigoPostal() + "'");
		}

		if (colegiadoItem.getTipoDireccion() != null && colegiadoItem.getTipoDireccion() != "") {
			sql.WHERE("tipodir.idtipodireccion = " + colegiadoItem.getTipoDireccion());
		} else {
			/*
			 * sql.
			 * WHERE("(tipodir.idtipodireccion = 2 OR 2 NOT IN (SELECT idtipodireccion FROM CEN_DIRECCION_TIPODIRECCION TIPODIR2 "
			 * +
			 * "WHERE TIPODIR.IDPERSONA = TIPODIR2.IDPERSONA  AND TIPODIR.IDINSTITUCION = TIPODIR2.IDINSTITUCION ))"
			 * );
			 */
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
					etiquetas += "( grucli.IDGRUPO ='" + colegiadoItem.getIdgrupo()[i].getValue()
							+ "' and grucli.IDINSTITUCION_GRUPO = '" + colegiadoItem.getIdgrupo()[i].getIdInstitucion()
							+ "')";
				} else {
					etiquetas += "( grucli.IDGRUPO ='" + colegiadoItem.getIdgrupo()[i].getValue()
							+ "' and grucli.IDINSTITUCION_GRUPO = '" + colegiadoItem.getIdgrupo()[i].getIdInstitucion()
							+ "') or";

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

		if (colegiadoItem.getEstadoColegial() != null && colegiadoItem.getEstadoColegial() != "") {
			sql.WHERE("cat.descripcion like '" + colegiadoItem.getEstadoColegial() + "'");
		}

		if (colegiadoItem.getFechaNacimientoRango() != null && colegiadoItem.getFechaNacimientoRango().length != 0) {

			if (colegiadoItem.getFechaNacimientoRango()[0] != null
					&& colegiadoItem.getFechaNacimientoRango()[1] != null) {

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

		

		LOGGER.info(
				"selectIdPersonas() -> Salida del servicio para obtener la sentencia para obtener la lista de colegiados");

		return sql.toString();

	}

	private String selectColegiados(Short idInstitucion, String idPersona) {

		LOGGER.info(
				"selectColegiados() -> Entrada del servicio para obtener la sentencia para obtener los datos de los colegiados");

		SQL sql = new SQL();
		
		sql.SELECT("Dir.Domicilio");
		sql.SELECT("Dir.Codigopostal");
		sql.SELECT("Dir.Telefono1");
		sql.SELECT("Dir.Telefono2");
		sql.SELECT("Dir.Movil");
		sql.SELECT("Dir.Fax1");
		sql.SELECT("Dir.Fax2");
		sql.SELECT("Dir.Correoelectronico");
		sql.SELECT("Dir.Paginaweb");
		sql.SELECT("Dir.Poblacionextranjera");
		
		SQL sqlPoblacion = new SQL();
		sqlPoblacion.SELECT("f_Siga_Getrecurso(Pob.Nombre, 1)");
		sqlPoblacion.FROM("Cen_Poblaciones Pob");
		sqlPoblacion.WHERE("Pob.idpoblacion = dir.idpoblacion");
		
		sql.SELECT("(" + sqlPoblacion + ") Poblacion");
		
		SQL sqlProvincia = new SQL();
		sqlProvincia.SELECT("f_Siga_Getrecurso(Pro.Nombre, 1)");
		sqlProvincia.FROM("Cen_Provincias Pro");
		sqlProvincia.WHERE("Pro.idprovincia = dir.idprovincia");
		
		sql.SELECT("(" + sqlProvincia + ") Provincia");
		
		SQL sqlPais = new SQL();
		sqlPais.SELECT("f_Siga_Getrecurso(Pa.Nombre, 1)");
		sqlPais.FROM("Cen_Pais Pa");
		sqlPais.WHERE("Pa.idpais = dir.idpais");
		
		sql.SELECT("(" + sqlPais + ") Pais");
		
		sql.FROM("Cen_Direcciones Dir");
		sql.WHERE("(dir.IDINSTITUCION IN ('" + idInstitucion
				+ "') AND dir.idpersona = " + idPersona + ")");
		sql.WHERE("Dir.Iddireccion = (Select Iddireccion From (Select Dir.Iddireccion, Decode(Tip.Idtipodireccion, 2, 1, 3, 2, 3) As Orden From Cen_Direcciones Dir, Cen_Direccion_Tipodireccion Tip Where Dir.Idpersona = Tip.Idpersona"
				+ " And Dir.Idinstitucion = Tip.Idinstitucion And Dir.Iddireccion = Tip.Iddireccion"
				+ " And Dir.Idpersona = " + idPersona + " And Dir.Idinstitucion = " + idInstitucion + " And Dir.Fechabaja Is Null Order By Orden Asc, Dir.Fechamodificacion Desc) Where Rownum = 1)");

		LOGGER.info(
				"selectColegiados() -> Salida del servicio para obtener la sentencia para obtener los datos de los colegiados");

		return sql.toString();

	}
}
