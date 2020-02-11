package org.itcgae.siga.cen.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.itcgae.siga.DTOs.cen.ColegiadoDTO;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.DTOs.cen.ComboInstitucionDTO;
import org.itcgae.siga.DTOs.cen.ComboInstitucionItem;
import org.itcgae.siga.DTOs.com.ResponseFileDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
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
	    
		colegiadoItemList = cenColegiadoExtendsMapper.selectColegiados(idInstitucion, colegiadoItem, tamMaximo);
		colegiadosDTO.setColegiadoItem(colegiadoItemList);
		if((colegiadoItemList != null) && tamMaximo != null && (colegiadoItemList.size()) > tamMaximo) {
			error.setCode(200);
			error.setDescription("La consulta devuelve más de " + tamMaximo + " resultados, pero se muestran sólo los " + tamMaximo + " más recientes. Si lo necesita, refine los criterios de búsqueda para reducir el número de resultados.");
			colegiadosDTO.setError(error);
			}
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
		Workbook workbook = new SXSSFWorkbook(100000);
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

		sql.SELECT_DISTINCT("f_siga_getncol_ncom(col.idinstitucion,col.idpersona) AS NcolNcom");
        sql.SELECT("decode (col.comunitario,'1','SI','0','NO') AS Comunitario");
        sql.SELECT("per.nombre AS Nombre");
        sql.SELECT("per.apellidos1 AS Apellido1");
        sql.SELECT("per.apellidos2 AS Apellido2");
        sql.SELECT("f_siga_getrecurso(tip.descripcion,1) AS TipoIdentificacion");
        sql.SELECT("per.nifcif AS NifCif");
        sql.SELECT("To_Char(Per.Fechanacimiento, 'dd-mm-yyyy') AS FechadeNacimiento");
        sql.SELECT("f_siga_getrecurso(est.descripcion,1) AS EstadoCivil");
        sql.SELECT("per.naturalde AS Naturalde");
        sql.SELECT("decode (per.fallecido,'1','SI','NO') AS Fallecido");
        sql.SELECT("decode (per.sexo,'M','MUEJR','HOMBRE') AS Sexo");
        sql.SELECT("To_Char(Cli.Fechaalta, 'dd-mm-yyyy') AS Fechaalta");
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
        sql.SELECT("To_Char(Cli.Fechacarga, 'dd-mm-yyyy') AS FechaCarga");
        sql.SELECT("cli.letrado AS Letrado");
        sql.SELECT("To_Char(Cli.Fechaactualizacion, 'dd-mm-yyyy') AS FechaActualizacion");
        sql.SELECT("To_Char(Cli.Fechaexportcenso, 'dd-mm-yyyy') AS FechaExportCenso");
        sql.SELECT("cli.noenviarrevista AS NoenviaRevista");
        sql.SELECT("cli.noaparecerredabogacia AS NoapareceRedAbogacia");
        sql.SELECT("To_Char(Col.Fechapresentacion, 'dd-mm-yyyy') AS FechaPresentacion");
        sql.SELECT("To_Char(Col.Fechaincorporacion, 'dd-mm-yyyy') AS FechaIncorporacion");
        sql.SELECT("col.indtitulacion AS IndTitulacion");
        sql.SELECT("col.jubilacioncuota AS JubilacionCuota");
        sql.SELECT("decode (col.situacionejercicio,'1','Alta','0','Baja') AS SituacionEjercicio");
        sql.SELECT("decode (col.situacionresidente,'1','SI','0','NO') AS SituacionResidente");
        sql.SELECT("col.situacionempresa AS SituacionEmpresa");
        sql.SELECT("To_Char(Col.Fechajura, 'dd-mm-yyyy') AS FechaJura");
        sql.SELECT("To_Char(Col.Fechatitulacion, 'dd-mm-yyyy') AS FechaTitulacion");
        sql.SELECT("decode (col.otroscolegios,'1','SI','0','NO') as OtrosColegios");
        sql.SELECT("To_Char(Col.Fechadeontologia, 'dd-mm-yyyy') AS FechaDeontologia");
        sql.SELECT("To_Char(Col.Fechamovimiento, 'dd-mm-yyyy') AS FechaMovimiento");
        sql.SELECT("f_siga_getrecurso(ts.nombre,1) AS TipoSeguro");
        sql.SELECT("col.cuentacontablesjcs AS CuentaContableSJCS");
        sql.SELECT("decode (f_siga_gettipocliente(col.idpersona,col.idinstitucion,sysdate),'10','No Ejerciente','20','Ejerciente','30','Baja Colegial','40','Inhabilitacion','50','Suspension Ejercicio','60','Baja por Deceso') AS EstadoColegial");
        sql.SELECT("To_Char(f_siga_getfechaestadocolegial(col.idpersona,col.idinstitucion,sysdate), 'dd-mm-yyyy')   AS FechaEstado");
        sql.SELECT("f_siga_getdireccioncliente(col.idinstitucion,col.idpersona,3,1) AS Domicilio");
        sql.SELECT("f_siga_getdireccioncliente(col.idinstitucion,col.idpersona,3,2) AS CodigoPostal");
        sql.SELECT("f_siga_getdireccioncliente(col.idinstitucion,col.idpersona,3,11) AS Telefono1");
        sql.SELECT("f_siga_getdireccioncliente(col.idinstitucion,col.idpersona,3,12) AS Telefono2");
        sql.SELECT("f_siga_getdireccioncliente(col.idinstitucion,col.idpersona,3,13) AS Movil");
        sql.SELECT("f_siga_getdireccioncliente(col.idinstitucion,col.idpersona,3,14) AS Fax1");
        sql.SELECT("f_siga_getdireccioncliente(col.idinstitucion,col.idpersona,3,15) AS Fax2");
        sql.SELECT("f_siga_getdireccioncliente(col.idinstitucion,col.idpersona,3,16) AS CorreoElectronico");
        sql.SELECT("f_siga_getdireccioncliente(col.idinstitucion,col.idpersona,3,17) AS PaginaWeb");
        sql.SELECT("f_siga_getdireccioncliente(col.idinstitucion,col.idpersona,3,3) AS Poblacion");
        sql.SELECT("f_siga_getdireccioncliente(col.idinstitucion,col.idpersona,3,4) AS Provincia");
        sql.SELECT("f_siga_getdireccioncliente1(col.idinstitucion,col.idpersona,3,5) AS Pais");


		String from = "cen_persona per, cen_colegiado col, cen_tipoidentificacion tip, cen_estadocivil est, cen_cliente cli, cen_tratamiento tra, cen_tiposseguro ts";

		sql.WHERE("col.idpersona=per.idpersona");
		sql.WHERE("per.idtipoidentificacion not in '20'");
		sql.WHERE("per.idtipoidentificacion=tip.idtipoidentificacion");
		sql.WHERE("est.idestadocivil(+)=per.idestadocivil");
		sql.WHERE("cli.idpersona=col.idpersona");
		sql.WHERE("cli.idinstitucion=col.idinstitucion");
		sql.WHERE("tra.idtratamiento(+)=cli.idtratamiento");
		sql.WHERE("col.idtiposseguro=ts.idtiposseguro(+)");

		if ((colegiadoItem.getTipoCV() != null && colegiadoItem.getTipoCV() != "")
				|| (colegiadoItem.getSubTipoCV1() != null && colegiadoItem.getSubTipoCV1() != "")
				|| (colegiadoItem.getSubTipoCV2() != null && colegiadoItem.getSubTipoCV2() != "")) {

			from += ", cen_datosCV datosCV, cen_tiposcv cenTipoCV, cen_tiposcvsubtipo2 subt2, cen_tiposcvsubtipo1 subt1 ";

			sql.WHERE("datosCV.idInstitucion = col.idInstitucion ");
			sql.WHERE("datosCV.idPersona = per.idPersona");
			sql.WHERE("cenTipoCV.idTipoCV = datosCV.idTipoCV");
			sql.WHERE("subt2.idTipoCV = datosCV.idTipoCV and subt2.idInstitucion = col.idInstitucion");
			sql.WHERE("subt1.idTipoCV = datosCV.idTipoCV and subt1.idInstitucion = col.idInstitucion");

			if (colegiadoItem.getTipoCV() != null && colegiadoItem.getTipoCV() != "") {
				sql.WHERE("datoscv.idtipocv = '" + colegiadoItem.getTipoCV() + "'");
			}

			if (colegiadoItem.getSubTipoCV1() != null && colegiadoItem.getSubTipoCV1() != "") {
				sql.WHERE("datoscv.idtipocvsubtipo1 = '" + colegiadoItem.getSubTipoCV1() + "'");
			}

			if (colegiadoItem.getSubTipoCV2() != null && colegiadoItem.getSubTipoCV2() != "") {
				sql.WHERE("datoscv.idtipocvsubtipo2 = '" + colegiadoItem.getSubTipoCV2() + "'");
			}

		}

		if (colegiadoItem.getSituacion() != null && colegiadoItem.getSituacion() != "") {

			from += ", CEN_DATOSCOLEGIALESESTADO colest ";

			sql.WHERE("col.idpersona = colest.idpersona and col.idinstitucion = colest.idinstitucion  ");
			sql.WHERE(
					"colest.fechaestado = (select max(datcol.fechaestado) from CEN_DATOSCOLEGIALESESTADO datcol where datcol.idpersona = colest.idpersona and datcol.idinstitucion = colest.idinstitucion and datcol.fechaestado < sysdate)");
			sql.WHERE("colest.idestado = 20");
			sql.WHERE("colest.idestado ='" + colegiadoItem.getSituacion() + "'");
		}

		if (colegiadoItem.getIdgrupo() != null && colegiadoItem.getIdgrupo().length > 0) {
			from += ", cen_gruposcliente_cliente grucli ";

			sql.WHERE("(grucli.idinstitucion = col.idinstitucion or grucli.idinstitucion = '2000') ");
			sql.WHERE("col.idpersona = grucli.idpersona ");
			sql.WHERE(
					"((grucli.fecha_inicio <= SYSDATE OR grucli.fecha_inicio IS NULL ) and ( grucli.fecha_baja > SYSDATE OR grucli.fecha_baja IS NULL))");

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

		if (!UtilidadesString.esCadenaVacia(colegiadoItem.getDomicilio())
				|| !UtilidadesString.esCadenaVacia(colegiadoItem.getCodigoPostal())
				|| !UtilidadesString.esCadenaVacia(colegiadoItem.getTelefono())
				|| !UtilidadesString.esCadenaVacia(colegiadoItem.getMovil())
				|| !UtilidadesString.esCadenaVacia(colegiadoItem.getIdPoblacion())
				|| !UtilidadesString.esCadenaVacia(colegiadoItem.getIdProvincia())
				|| !UtilidadesString.esCadenaVacia(colegiadoItem.getTipoDireccion())
				|| !UtilidadesString.esCadenaVacia(colegiadoItem.getCorreo())) {

			from += ", cen_direcciones dir, CEN_DIRECCION_TIPODIRECCION TIPODIR ";

			sql.WHERE("cli.idpersona = dir.idpersona");
			sql.WHERE("cli.idinstitucion = dir.idinstitucion");
			sql.WHERE("dir.fechabaja is null");
			sql.WHERE("Dir.Iddireccion = f_Siga_Getiddireccion_Tipopre2(Dir.Idinstitucion, Dir.Idpersona, 2, 3)");
			sql.WHERE("CLI.IDPERSONA = TIPODIR.IDPERSONA ");
			sql.WHERE("DIR.IDDIRECCION = TIPODIR.IDDIRECCION ");
			sql.WHERE("CLI.IDINSTITUCION = TIPODIR.IDINSTITUCION ");

			if (colegiadoItem.getCodigoPostal() != null && colegiadoItem.getCodigoPostal() != "") {
				sql.WHERE("dir.codigopostal ='" + colegiadoItem.getCodigoPostal() + "'");
			}

			if (colegiadoItem.getTipoDireccion() != null && colegiadoItem.getTipoDireccion() != "") {
				sql.WHERE("tipodir.idtipodireccion = " + colegiadoItem.getTipoDireccion());
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

//				sql.WHERE("upper(dir.correoelectronico) LIKE upper('%" + colegiadoItem.getCorreo() + "%')");
			}

			if (colegiadoItem.getTelefono() != null && colegiadoItem.getTelefono() != "") {
				sql.WHERE("dir.telefono1 like '%" + colegiadoItem.getTelefono() + "%'");
			}

			if (colegiadoItem.getMovil() != null && colegiadoItem.getMovil() != "") {
				sql.WHERE("dir.movil like '%" + colegiadoItem.getMovil() + "%'");
			}
			
			
		}

		sql.FROM(from);

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

		if (colegiadoItem.getNumColegiado() != null && colegiadoItem.getNumColegiado() != "") {
			sql.WHERE("(col.ncolegiado = '" + colegiadoItem.getNumColegiado() + "' OR COL.NCOMUNITARIO = '"
					+ colegiadoItem.getNumColegiado() + "')");
		}

		if (colegiadoItem.getSexo() != null && colegiadoItem.getSexo() != "") {
			sql.WHERE("per.sexo = '" + colegiadoItem.getSexo() + "'");
		}

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

		if (colegiadoItem.getIdEstadoCivil() != null && colegiadoItem.getIdEstadoCivil() != "") {
			sql.WHERE("per.idestadocivil = '" + colegiadoItem.getIdEstadoCivil() + "'");
		}

		if (colegiadoItem.getResidencia() != null && colegiadoItem.getResidencia() != "") {
			sql.WHERE("col.situacionresidente ='" + colegiadoItem.getResidencia() + "'");
		}

		if (colegiadoItem.getInscrito() != null && colegiadoItem.getInscrito() != "") {
			sql.WHERE("col.comunitario ='" + colegiadoItem.getInscrito() + "'");
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
