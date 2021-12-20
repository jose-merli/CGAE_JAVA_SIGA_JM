package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.EcomCola;
import org.itcgae.siga.db.entities.EcomColaParametros;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.itcgae.siga.db.mappers.EcomColaMapper;
import org.itcgae.siga.db.mappers.EcomColaParametrosMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacturacionSJCSHelper  {


	private static final Integer ID_USU_DEFAULT = 0;

	private static AdmUsuarios USU_DEFAULT = null;

	private Logger LOGGER = Logger.getLogger(FacturacionSJCSHelper.class);

	  @Autowired
	  private EcomColaMapper ecomColaMapper; 
	    
	  @Autowired
	  private EcomColaParametrosMapper ecomColaParametrosMapper;
	  
	  @Autowired
	  private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;
	  
	  @Autowired
	  private GenPropertiesMapper genPropertiesMapper;
	 

	  
	 public void insertaColaConParametros(EcomCola ecomCola, Map<String, String> parametros, AdmUsuarios usuario) throws Exception {

	        insertartCola(ecomCola, usuario);

	        if (parametros != null) {
	            Iterator<String> it = parametros.keySet().iterator();

	            while (it.hasNext()) {
	                EcomColaParametros ecomColaParametros = new EcomColaParametros();
	                String clave = it.next();
	                String valor = parametros.get(clave);
	                ecomColaParametros.setIdecomcola(ecomCola.getIdecomcola());
	                ecomColaParametros.setClave(clave);
	                ecomColaParametros.setValor(valor);
	                if (ecomColaParametrosMapper.insert(ecomColaParametros) != 1) {
	                    throw new Exception("Error al insertar los parámetros de la cola.");
	                }
	            }
	        }
	    }	
	 
	 public void insertaColaConParametros(EcomCola ecomCola, Map<String, String> parametros) throws Exception {
		 insertaColaConParametros(ecomCola,parametros,getUsuarioAuto());
	   }
	 
	 
	  private int insertartCola(EcomCola ecomCola, AdmUsuarios usuario) {

	        try {
	            ecomCola.setIdestadocola(SigaConstants.ECOM_ESTADOSCOLA.INICIAL.getId());
	            ecomCola.setReintento(0);
	            ecomCola.setFechacreacion(new Date());
	            ecomCola.setUsumodificacion(usuario.getIdusuario());

	            return ecomColaMapper.insert(ecomCola);
	        } catch (Exception e) {
	            LOGGER.error(String.format("Se ha producido un error al insertar en la cola %s", ecomCola));
	            throw e;
	        }
	    }


		public AdmUsuarios getUsuario(Short idInstitucion, String dni) {
	        AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
	        exampleUsuarios.createCriteria()
	          	.andNifEqualTo(dni)
	          	.andIdinstitucionEqualTo(Short.valueOf(idInstitucion));	            
	        List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
	        return  usuarios.get(0);    
		}
		
		public AdmUsuarios getUsuarioAuto() {
			if(USU_DEFAULT==null) {
				USU_DEFAULT = new AdmUsuarios();
				USU_DEFAULT.setIdusuario(ID_USU_DEFAULT);
			}
	        return  USU_DEFAULT;    
		}
		
		
	public Path getRutaAlmacenFichero(Short idInstitucion) {
			GenPropertiesKey key = new GenPropertiesKey();
			key.setFichero(SigaConstants.FICHERO_SIGA);
			key.setParametro(SigaConstants.parametroRutaAlmacenFicheros);
			GenProperties rutaFicherosSalida = genPropertiesMapper.selectByPrimaryKey(key);
			String rutaRaiz = rutaFicherosSalida.getValor();
			return Paths.get(rutaRaiz,String.valueOf(idInstitucion));
		}

	
}
