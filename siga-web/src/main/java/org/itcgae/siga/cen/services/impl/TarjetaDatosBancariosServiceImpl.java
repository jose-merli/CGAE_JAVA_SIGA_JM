package org.itcgae.siga.cen.services.impl;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosDeleteDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosInsertDTO;
import org.itcgae.siga.DTOs.cen.DatosBancariosItem;
import org.itcgae.siga.DTOs.cen.DatosBancariosSearchDTO;
import org.itcgae.siga.DTOs.cen.MandatosDTO;
import org.itcgae.siga.DTOs.cen.MandatosItem;
import org.itcgae.siga.DTOs.cen.MandatosUpdateDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.cen.services.ITarjetaDatosBancariosService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmConfig;
import org.itcgae.siga.db.entities.AdmConfigExample;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenCuentasbancarias;
import org.itcgae.siga.db.entities.CenCuentasbancariasExample;
import org.itcgae.siga.db.entities.CenMandatosCuentasbancarias;
import org.itcgae.siga.db.mappers.AdmConfigMapper;
import org.itcgae.siga.db.mappers.CenMandatosCuentasbancariasMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenCuentasbancariasExtendsMapper;
import org.itcgae.siga.security.UserTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TarjetaDatosBancariosServiceImpl implements ITarjetaDatosBancariosService{

	private Logger LOGGER = Logger.getLogger(TarjetaDatosBancariosServiceImpl.class);
	
	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	
	@Autowired
	private CenCuentasbancariasExtendsMapper cenCuentasbancariasExtendsMapper;
	
	@Autowired
	private AdmConfigMapper admConfigMapper;
	
	@Autowired
	private CenMandatosCuentasbancariasMapper cenMandatosCuentasbancariasMapper;

	@Override
	public DatosBancariosDTO searchBanksData(int numPagina, DatosBancariosSearchDTO datosBancariosSearchDTO,
			HttpServletRequest request) {
		LOGGER.info("searchBanksData() -> Entrada al servicio para la búsqueda por filtros de cuentas bancarias");
		
		List<DatosBancariosItem> datosBancariosItem = new ArrayList<DatosBancariosItem>();
		DatosBancariosDTO datosBancariosDTO = new DatosBancariosDTO();

		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchBanksData() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchBanksData() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				LOGGER.info(
						"searchBanksData() / cenCuentasbancariasExtendsMapper.selectCuentasBancarias() -> Entrada a cenCuentasbancariasExtendsMapper para busqueda de cuentas bancarias");
				datosBancariosItem = cenCuentasbancariasExtendsMapper.selectCuentasBancarias(datosBancariosSearchDTO, idInstitucion.toString());
				LOGGER.info(
						"searchBanksData() / cenNocolegiadoExtendsMapper.searchLegalPersons() -> Salida de cenCuentasbancariasExtendsMapper para busqueda de cuentas bancarias");

				datosBancariosDTO.setDatosBancariosItem(datosBancariosItem);;
			} 
			else {
				LOGGER.warn("searchBanksData() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		} 
		else {
			LOGGER.warn("searchBanksData() -> idInstitucion del token nula");
		}
		
		LOGGER.info("searchBanksData() -> Salida del servicio para la búsqueda por filtros de cuentas bancarias");
		return datosBancariosDTO;
	}




	@Override
	public DeleteResponseDTO deleteBanksData(DatosBancariosDeleteDTO datosBancariosDeleteDTO,
			HttpServletRequest request) {
		
		LOGGER.info("deleteBanksData() -> Entrada al servicio para eliminar cuentas bancarias");
		int response = 0;
		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"deleteBanksData() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"deleteBanksData() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				
				// información a modificar
				CenCuentasbancarias cuentaBancaria = new CenCuentasbancarias();
				cuentaBancaria.setFechabaja(new Date());
				cuentaBancaria.setFechamodificacion(new Date());
				cuentaBancaria.setUsumodificacion(usuario.getIdusuario());
				
				// filtrado para sentencia sql
				List <Short> idCuentasDelete = new ArrayList<Short>();
				for(int i=0; i<datosBancariosDeleteDTO.getIdCuentas().length; i++) {
					idCuentasDelete.add(Short.valueOf(datosBancariosDeleteDTO.getIdCuentas()[i]));
				}
				
				CenCuentasbancariasExample cenCuentasbancariasDelete = new CenCuentasbancariasExample();
				cenCuentasbancariasDelete.createCriteria().andIdinstitucionEqualTo(idInstitucion).andIdpersonaEqualTo(Long.valueOf(datosBancariosDeleteDTO.getIdPersona())).andIdcuentaIn(idCuentasDelete);
			
				LOGGER.info(
						"deleteBanksData() / cenNocolegiadoExtendsMapper.updateByExampleSelective() -> Entrada a cenNocolegiadoExtendsMapper para eliminar cuentas bancarias");
				response = cenCuentasbancariasExtendsMapper.updateByExampleSelective(cuentaBancaria, cenCuentasbancariasDelete);
				LOGGER.info(
						"deleteBanksData() / cenNocolegiadoExtendsMapper.updateByExampleSelective() -> Salida de cenNocolegiadoExtendsMapper para eliminar cuentas bancarias");

			} else {
				LOGGER.warn(
						"deleteBanksData() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		} else {
			LOGGER.warn("deleteBanksData() -> idInstitucion del token nula");
		}
		
		// comprobacion actualización
		if(response >= 1) {
			LOGGER.info("deleteBanksData() -> OK. Delete para cuentas bancarias realizado correctamente");
			deleteResponseDTO.setStatus(SigaConstants.OK);
		}
		else {
			LOGGER.info("deleteBanksData() -> KO. Delete para cuentas bancarias  NO realizado correctamente");
			deleteResponseDTO.setStatus(SigaConstants.KO);
		}
		
		LOGGER.info("deleteBanksData() -> Salida del servicio para eliminar cuentas bancarias ");
		return deleteResponseDTO;
	}




	@Override
	public DatosBancariosDTO searchGeneralData(int numPagina, DatosBancariosSearchDTO datosBancariosSearchDTO,
			HttpServletRequest request) {
		LOGGER.info("searchGeneralData() -> Entrada al servicio para la búsqueda por filtros de cuentas bancarias");
		
		List<DatosBancariosItem> datosBancariosItem = new ArrayList<DatosBancariosItem>();
		DatosBancariosDTO datosBancariosDTO = new DatosBancariosDTO();

		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchGeneralData() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchGeneralData() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				LOGGER.info(
						"searchGeneralData() / cenCuentasbancariasExtendsMapper.selectCuentasBancarias() -> Entrada a cenCuentasbancariasExtendsMapper para busqueda de cuentas bancarias");
				datosBancariosItem = cenCuentasbancariasExtendsMapper.selectGeneralCuentasBancarias(datosBancariosSearchDTO, idInstitucion.toString());
				LOGGER.info(
						"searchGeneralData() / cenNocolegiadoExtendsMapper.searchLegalPersons() -> Salida de cenCuentasbancariasExtendsMapper para busqueda de cuentas bancarias");

				if (null != datosBancariosItem && datosBancariosItem.size() >0 ) {
					for (DatosBancariosItem datosBancarios : datosBancariosItem) {
						if (null != datosBancarios.getUso()) {
							List<String> usos = new ArrayList<String>();
							if (datosBancarios.getUso().contains("ABONO")) {
								usos.add("A");
							}
							if (datosBancarios.getUso().contains("CARGO")) {
								usos.add("C");
							}
							if (datosBancarios.getUso().contains("SJCS")) {
								usos.add("S");
							}
							if (null != usos && usos.size()>0) {
								String[] tiposCuenta = new String[usos.size()];
								int i = 0;
								for (String uso : usos) {
									tiposCuenta[i] = new String();
									tiposCuenta[i] = uso;
									i++;
								}
								datosBancarios.setTipoCuenta(tiposCuenta);
							}
							
						}
					}
				}
				datosBancariosDTO.setDatosBancariosItem(datosBancariosItem);;
			} 
			else {
				LOGGER.warn("searchGeneralData() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		} 
		else {
			LOGGER.warn("searchGeneralData() -> idInstitucion del token nula");
		}
		
		LOGGER.info("searchBanksData() -> Salida del servicio para la búsqueda por filtros de cuentas bancarias");
		return datosBancariosDTO;
	}




	@Override
	public MandatosDTO searchMandatos(int numPagina, DatosBancariosSearchDTO datosBancariosSearchDTO,
			HttpServletRequest request) {
		LOGGER.info("searchMandatos() -> Entrada al servicio para la búsqueda por filtros de mandatos de cuentas bancarias");
		
		List<MandatosItem> mandatosItem = new ArrayList<MandatosItem>();
		MandatosDTO mandatosDTO = new MandatosDTO();

		
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"searchMandatos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"searchMandatos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				LOGGER.info(
						"searchMandatos() / cenCuentasbancariasExtendsMapper.selectCuentasBancarias() -> Entrada a cenCuentasbancariasExtendsMapper para busqueda de mandatos de cuentas bancarias");
				mandatosItem = cenCuentasbancariasExtendsMapper.selectMandatos(datosBancariosSearchDTO, idInstitucion.toString());
				LOGGER.info(
						"searchMandatos() / cenNocolegiadoExtendsMapper.searchLegalPersons() -> Salida de cenCuentasbancariasExtendsMapper para busqueda de mandatos de cuentas bancarias");

				mandatosDTO.setMandatosItem(mandatosItem);;
			} 
			else {
				LOGGER.warn("searchMandatos() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = " + dni + " e idInstitucion = " + idInstitucion);
			}
		} 
		else {
			LOGGER.warn("searchMandatos() -> idInstitucion del token nula");
		}
		
		LOGGER.info("searchMandatos() -> Salida del servicio para la búsqueda por filtros de mandatos de cuentas bancarias");
		return mandatosDTO;
	}




	@Override
	public UpdateResponseDTO insertBanksData(DatosBancariosInsertDTO datosBancariosInsertDTO,
			HttpServletRequest request) throws IOException, NamingException, SQLException {
		
		LOGGER.info("insertBanksData() -> Entrada al servicio para insertar cuentas bancarias");
		int response = 0;
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		Error error = new Error();
		// Conseguimos información del usuario logeado
		String token = request.getHeader("Authorization");
		String dni = UserTokenUtils.getDniFromJWTToken(token);
		Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
		boolean tieneSCSJ = Boolean.FALSE;
		boolean tieneCargo = Boolean.FALSE;
		boolean tieneAbono = Boolean.FALSE;
		if (null != idInstitucion) {
			AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
			exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
			LOGGER.info(
					"insertBanksData() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
			List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
			LOGGER.info(
					"insertBanksData() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

			if (null != usuarios && usuarios.size() > 0) {
				AdmUsuarios usuario = usuarios.get(0);
				
				
				// información a insertar
				//Obtenemos el nuevo idCuenta
				
				List<DatosBancariosItem> newIdCuenta = cenCuentasbancariasExtendsMapper.selectNewIdCuenta(datosBancariosInsertDTO.getIdPersona());
				CenCuentasbancarias cuentaBancaria = new CenCuentasbancarias();
				
				cuentaBancaria.setFechamodificacion(new Date());
				cuentaBancaria.setUsumodificacion(usuario.getIdusuario());
				cuentaBancaria.setCuentacontable(datosBancariosInsertDTO.getCuentaContable());
				cuentaBancaria.setIban(datosBancariosInsertDTO.getIban());
				cuentaBancaria.setIdcuenta(Short.valueOf(newIdCuenta.get(0).getIdCuenta()));
				cuentaBancaria.setIdinstitucion(idInstitucion);
				cuentaBancaria.setIdpersona(Long.valueOf(datosBancariosInsertDTO.getIdPersona()));
				cuentaBancaria.setTitular(datosBancariosInsertDTO.getTitular());

				//Gestionamos los abonos que nos llegan
				if (null != datosBancariosInsertDTO.getUsos() && datosBancariosInsertDTO.getUsos().length>0) {

					for (String uso : datosBancariosInsertDTO.getUsos()) {
						if (uso.equals("S")) {
							cuentaBancaria.setAbonosjcs("1");
							tieneSCSJ = Boolean.TRUE;
						}
						if (uso.equals("C")) {
							tieneCargo = Boolean.TRUE;
						
						}
						if (uso.equals("A")) {
							tieneAbono = Boolean.TRUE;
						
						}
					}
					if (!tieneSCSJ) {
						cuentaBancaria.setAbonosjcs("0");
					}
					if (tieneCargo && tieneAbono) {
						cuentaBancaria.setAbonocargo("T");
						
					}else if(tieneCargo) {
						cuentaBancaria.setAbonocargo("C");
					}else if(tieneAbono) {
						cuentaBancaria.setAbonocargo("A");
					}
				}
				
				
				cuentaBancaria.setCboCodigo(datosBancariosInsertDTO.getIban().substring(4, 8));
				cuentaBancaria.setCodigosucursal(datosBancariosInsertDTO.getIban().substring(8, 12));
				cuentaBancaria.setDigitocontrol(datosBancariosInsertDTO.getIban().substring(12, 14));
				cuentaBancaria.setNumerocuenta(datosBancariosInsertDTO.getIban().substring(14, 24));			

				LOGGER.info(
						"insertBanksData() / cenNocolegiadoExtendsMapper.updateByExampleSelective() -> Entrada a cenNocolegiadoExtendsMapper para insertar cuentas bancarias");
				response = cenCuentasbancariasExtendsMapper.insertSelective(cuentaBancaria);
				LOGGER.info(
						"insertBanksData() / cenNocolegiadoExtendsMapper.updateByExampleSelective() -> Salida de cenNocolegiadoExtendsMapper para insertar cuentas bancarias");
		
				// comprobacion actualización
				if(response >= 1) {
					LOGGER.info("insertBanksData() -> OK. Insert para cuentas bancarias realizado correctamente");
					updateResponseDTO.setStatus(SigaConstants.OK);
				}
				else {
					LOGGER.info("insertBanksData() -> KO. Insert para cuentas bancarias  NO realizado correctamente");
					updateResponseDTO.setStatus(SigaConstants.KO);
					error.setMessage("Error al insertar la cuenta Bancaria");
					updateResponseDTO.setError(error);
					return updateResponseDTO;
				}
				
				//Si se ha generado correctamente el registro, procedemos a generar los mandatos.
				
		
				
				// Se insertan dos mandatos nuevos a la cuenta, uno para productos y otro para servicios
				if (tieneCargo) {
					Object[] paramMandatos = new Object[4];
					paramMandatos[0] = idInstitucion.toString();
					paramMandatos[1] = datosBancariosInsertDTO.getIdPersona();
					paramMandatos[2] = newIdCuenta.get(0).getIdCuenta();
					paramMandatos[3] = usuario.getIdusuario().toString();
					
					String resultado[] = new String[2];
					resultado = callPLProcedure("{call PKG_SIGA_CARGOS.InsertarMandatos(?,?,?,?,?,?)}", 2, paramMandatos);
					if (resultado == null) {
						LOGGER.info("insertBanksData() -> KO. Insert para mandatos cuentas bancarias  NO realizado correctamente");
						updateResponseDTO.setStatus(SigaConstants.KO);
						error.setMessage("Error al insertar los mandatos de las cuentas");
						updateResponseDTO.setError(error);
						return updateResponseDTO;
						
					} else {
						if (resultado[0].equals("1")) {
							LOGGER.info("insertBanksData() -> KO. Insert para mandatos cuentas bancarias  NO realizado correctamente");
							updateResponseDTO.setStatus(SigaConstants.KO);
							error.setMessage("messages.censo.direcciones.facturacion");
							updateResponseDTO.setError(error);
							return updateResponseDTO;
							
						} else if (resultado[0].equals("2")) {
							LOGGER.info("insertBanksData() -> KO. Insert para mandatos cuentas bancarias  NO realizado correctamente");
							updateResponseDTO.setStatus(SigaConstants.KO);
							error.setMessage("messages.censo.direcciones.facturacion");
							updateResponseDTO.setError(error);
							return updateResponseDTO;
							
						} else if (!resultado[0].equals("0")) {
							LOGGER.info("insertBanksData() -> KO. Insert para mandatos cuentas bancarias  NO realizado correctamente");
							updateResponseDTO.setStatus(SigaConstants.KO);
							error.setMessage("Error al insertar los mandatos de las cuentas");
							updateResponseDTO.setError(error);
							return updateResponseDTO;
						}
					}
				}
			} else {
				LOGGER.warn(
						"insertBanksData() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
								+ dni + " e idInstitucion = " + idInstitucion);
			}
		
		} else {
			LOGGER.warn("insertBanksData() -> idInstitucion del token nula");
		}
		
		
		LOGGER.info("insertBanksData() -> Salida del servicio para insertar cuentas bancarias ");
		return updateResponseDTO;
	}



	
	
	/**
	   * Calls a PL Funtion
	   * @author CSD
	   * @param functionDefinition string that defines the function
	   * @param inParameters input parameters
	   * @param outParameters number of output parameters
	   * @return error code, '0' if ok
	 * @throws NamingException 
	 * @throws IOException 
	 * @throws SQLException 
	   * @throws ClsExceptions  type Exception
	   */
	  private  String[] callPLProcedure(String functionDefinition, int outParameters, Object[] inParameters) throws IOException, NamingException, SQLException  {
	    String result[] = null;
	    
	    if (outParameters>0) result= new String[outParameters];
	    DataSource ds = getOracleDataSource();
	    Connection con=ds.getConnection();
	    try{
	      CallableStatement cs=con.prepareCall(functionDefinition);
	      int size=inParameters.length;
	      
	      //input Parameters
	      for(int i=0;i<size;i++){
	    	  

	        cs.setString(i+1,(String)inParameters[i]);
	      }
	      //output Parameters
	      for(int i=0;i<outParameters;i++){
	        cs.registerOutParameter(i+size+1,Types.VARCHAR);
	      }
	      
			for (int intento = 1; intento <= 2; intento++) {
				try {
					cs.execute();
					break;
					
				} catch (SQLTimeoutException tex) {
					throw tex;
		
				} catch (SQLException ex) {
					if (ex.getErrorCode() != 4068 || intento == 2) { // JPT: 4068 es un error de descompilado (la segunda vez deberia funcionar)
						throw ex;
					}
				}

			}      

	      for(int i=0;i<outParameters;i++){
	        result[i]=cs.getString(i+size+1);
	      }
	      cs.close();
	      return result;
	      
	    }catch(SQLTimeoutException ex){
	        return null;
	    }catch(SQLException ex){
	    	return null;
	    }catch(Exception e){
	    	return null;
	    }finally{
	      con.close();
	      con = null;
	    }
	  }
	  
	  
		/**
		 * Recupera el datasource con los datos de conexión sacados del fichero de
		 * configuracion
		 * 
		 * @return
		 * @throws IOException
		 * @throws NamingException
		 */
		private  DataSource getOracleDataSource() throws IOException, NamingException {
			try {
				
				LOGGER.debug("Recuperando datasource {} provisto por el servidor (JNDI)");
				
				AdmConfigExample example = new AdmConfigExample();
				example.createCriteria().andClaveEqualTo("spring.datasource.jndi-name");
				List<AdmConfig> config = admConfigMapper.selectByExample(example );
				Context ctx = new InitialContext();
				return (DataSource) ctx.lookup(config.get(0).getValor());
			} catch (NamingException e) {
				throw e;
			}
		}




		@Override
		public ComboDTO getLabelEsquema(HttpServletRequest request) {
			LOGGER.info("getLabelEsquema() -> Entrada al servicio para la búsqueda de esquemas de mandatos");
			
			ComboDTO combo = new ComboDTO();
			List<ComboItem> comboItems = new ArrayList<ComboItem>();
			// Conseguimos información del usuario logeado
			String token = request.getHeader("Authorization");
			String dni = UserTokenUtils.getDniFromJWTToken(token);
			Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);
			
			if(null != idInstitucion)
			{
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				LOGGER.info(
						"getLabelEsquema() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				if(null != usuarios && usuarios.size() > 0) {
					
					AdmUsuarios usuario = usuarios.get(0);
					LOGGER.info(
							"getLabelEsquema() / cenNocolegiadoExtendsMapper.getProfesionalActivities() -> Entrada a cenNocolegiadoExtendsMapper obtener lista de esquemas de mandatos");
					comboItems = cenCuentasbancariasExtendsMapper.getComboEsquemas(usuario.getIdlenguaje());
					LOGGER.info(
							"getLabelEsquema() / cenNocolegiadoExtendsMapper.getProfesionalActivities() -> Entrada a cenNocolegiadoExtendsMapper obtener lista de esquemas de mandatos");
					if (!comboItems.equals(null) && comboItems.size() > 0) {
						// añade elemento vacio al principio para el dropdown de parte front
						ComboItem comboItem = new ComboItem();
						comboItem.setLabel("");
						comboItem.setValue("");
						comboItems.add(0, comboItem);
						combo.setCombooItems(comboItems);
					}
				}
				
			}
			
			
			LOGGER.info("getLabelEsquema() -> Salida del servicio para la búsqueda de esquemas de mandatos");
			return combo;

		}




		@Override
		public UpdateResponseDTO updateMandatos(MandatosUpdateDTO mandatosUpdateDTO, HttpServletRequest request) {
			
			LOGGER.info("updateMandatos() -> Entrada al servicio para actualizar mandatos");
			int response = 0;
			UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
			
			// Conseguimos información del usuario logeado
			String token = request.getHeader("Authorization");
			String dni = UserTokenUtils.getDniFromJWTToken(token);
			Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

			if (null != idInstitucion) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(Short.valueOf(idInstitucion));
				LOGGER.info(
						"updateMandatos() / admUsuariosExtendsMapper.selectByExample() -> Entrada a admUsuariosExtendsMapper para obtener información del usuario logeado");
				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				LOGGER.info(
						"updateMandatos() / admUsuariosExtendsMapper.selectByExample() -> Salida de admUsuariosExtendsMapper para obtener información del usuario logeado");

				if (null != usuarios && usuarios.size() > 0) {
					AdmUsuarios usuario = usuarios.get(0);
						
					// información a modificar
					CenMandatosCuentasbancarias record = new CenMandatosCuentasbancarias();
					record.setFechamodificacion(new Date());
					record.setUsumodificacion(usuario.getIdusuario());
					record.setIdmandato(Short.valueOf(mandatosUpdateDTO.getIdMandato()));
					record.setIdcuenta(Short.valueOf(mandatosUpdateDTO.getIdCuenta()));
					record.setIdpersona(Long.valueOf(mandatosUpdateDTO.getIdPersona()));
					record.setIdinstitucion(Short.valueOf(idInstitucion));
					record.setEsquema(Short.valueOf(mandatosUpdateDTO.getEsquema()));
					// filtrado para sentencia sql
				
					LOGGER.info(
							"updateMandatos() / cenNocolegiadoExtendsMapper.updateByExampleSelective() -> Entrada a cenNocolegiadoExtendsMapper para actualizar mandatos");
					
					response = cenMandatosCuentasbancariasMapper.updateByPrimaryKeySelective(record);
					
					LOGGER.info(
							"updateMandatos() / cenNocolegiadoExtendsMapper.updateByExampleSelective() -> Salida de cenNocolegiadoExtendsMapper para actualizar mandatos");

				} else {
					LOGGER.warn(
							"updateMandatos() / admUsuariosExtendsMapper.selectByExample() -> No existen usuarios en tabla admUsuarios para dni = "
									+ dni + " e idInstitucion = " + idInstitucion);
				}
			} else {
				LOGGER.warn("updateMandatos() -> idInstitucion del token nula");
			}
			
			// comprobacion actualización
			if(response >= 1) {
				LOGGER.info("updateMandatos() -> OK. Update para mandatos realizado correctamente");
				updateResponseDTO.setStatus(SigaConstants.OK);
			}
			else {
				LOGGER.info("updateMandatos() -> KO. Update para mandatos  NO realizado correctamente");
				updateResponseDTO.setStatus(SigaConstants.KO);
			}
			
			LOGGER.info("deleteBanksData() -> Salida del servicio para actualizar mandatos ");
			return updateResponseDTO;
		}



	

}
