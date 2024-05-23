package org.itcgae.siga.ws.client;

import org.datacontract.schemas._2004._07.IntegracionCuotaYCapitalObjetivoJubilacion;
import org.datacontract.schemas._2004._07.IntegracionEnumsCombos;
import org.datacontract.schemas._2004._07.IntegracionSolicitudRespuesta;
import org.itcgae.siga.ws.config.MutualidadClient;
import org.itcgae.siga.ws.config.WebServiceClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import samples.servicemodel.microsoft.EstadoMutualistaDocument;
import samples.servicemodel.microsoft.EstadoMutualistaResponseDocument;
import samples.servicemodel.microsoft.EstadoSolicitudDocument;
import samples.servicemodel.microsoft.EstadoSolicitudResponseDocument;
import samples.servicemodel.microsoft.GetEnumsDocument;
import samples.servicemodel.microsoft.GetEnumsResponseDocument;
import samples.servicemodel.microsoft.MGASolicitudPolizaAccuGratuitosDocument;
import samples.servicemodel.microsoft.MGASolicitudPolizaAccuGratuitosResponseDocument;
import samples.servicemodel.microsoft.MGASolicitudPolizaProfesionalDocument;
import samples.servicemodel.microsoft.MGASolicitudPolizaProfesionalResponseDocument;
import samples.servicemodel.microsoft.ObtenerCuotaYCapObjetivoDocument;
import samples.servicemodel.microsoft.ObtenerCuotaYCapObjetivoResponseDocument;


@Component
public class ClientMutualidad extends  WebServiceGatewaySupport  {
	
	//private static Map<Integer, ConfigurationContext> mapa = new HashMap<Integer, ConfigurationContext>();
	
	/**
	 * Bean con la configuración básica del webServiceTemplate
	 */
	@Autowired
	protected WebServiceTemplate webServiceTemplate;
	
	/**
	 * Uri del servicio que se va a consumir
	 */
	protected String uriService;
	

	
	public IntegracionSolicitudRespuesta getEstadoMutualista (EstadoMutualistaDocument request , String uriService)throws Exception{
		
		
		
		try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebServiceClientConfig.class))
		{
		    MutualidadClient client = context.getBean(MutualidadClient.class);
		    EstadoMutualistaResponseDocument responsefinal = client.getEstadoMutualista(uriService,request);
		        
			
			IntegracionSolicitudRespuesta estadoMutualista = responsefinal.getEstadoMutualistaResponse().getEstadoMutualistaResult();
			
			return estadoMutualista;
		}catch(Exception e) {
			logger.error("ClienteMutualidad -> Error al obtener el Estado Mutualista", e);
			return null;
		}
		
	}
	
	public IntegracionSolicitudRespuesta getEstadoSolicitud (EstadoSolicitudDocument request , String uriService)throws Exception{
		
	
		try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebServiceClientConfig.class))
		{
		    MutualidadClient client = context.getBean(MutualidadClient.class);
		    EstadoSolicitudResponseDocument responsefinal = client.getEstadoSolicitud(uriService, request);
		
	
			
			IntegracionSolicitudRespuesta estadoSolicitud = responsefinal.getEstadoSolicitudResponse().getEstadoSolicitudResult();
			
			return estadoSolicitud;
		}catch(Exception e) {
			logger.error("ClienteMutualidad -> Error al obtener el Estado Solicitud", e);
			return null;
		}
		
	}
	
	
	public IntegracionEnumsCombos getEnums (GetEnumsDocument request , String uriService)throws Exception{
		
		try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebServiceClientConfig.class))
		{
			MutualidadClient client = context.getBean(MutualidadClient.class);
		    GetEnumsResponseDocument responsefinal = client.getMutualidad(uriService, request);
		        
			
			IntegracionEnumsCombos enumCombos = responsefinal.getGetEnumsResponse().getGetEnumsResult();
			return enumCombos;
		}catch (Exception e) {
			throw new Exception("Imposible comunicar con la mutualidad en estos momentos. Inténtelo de nuevo en unos minutos", e);
		}
		
	}


	public IntegracionSolicitudRespuesta MGASolicitudPolizaAccuGratuitos (MGASolicitudPolizaAccuGratuitosDocument request , String uriService)throws Exception{
		
		
		
		try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebServiceClientConfig.class))
		{
			MutualidadClient client = context.getBean(MutualidadClient.class);
		    MGASolicitudPolizaAccuGratuitosResponseDocument responsefinal = client.mGASolicitudPolizaAccuGratuitos(uriService, request);
		        
			IntegracionSolicitudRespuesta response = responsefinal.getMGASolicitudPolizaAccuGratuitosResponse().getMGASolicitudPolizaAccuGratuitosResult();
			
			return response;

		}catch (Exception e) {
			throw new Exception("Imposible comunicar con la mutualidad en estos momentos. Inténtelo de nuevo en unos minutos", e);
		}

		

		
	}

	public IntegracionSolicitudRespuesta MGASolicitudPolizaProfesional (MGASolicitudPolizaProfesionalDocument request , String uriService)throws Exception{
		
		try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebServiceClientConfig.class))
		{
			MutualidadClient client = context.getBean(MutualidadClient.class);
		    MGASolicitudPolizaProfesionalResponseDocument responsefinal = client.mGASolicitudPolizaProfesional(uriService, request);
		        
			IntegracionSolicitudRespuesta response = responsefinal.getMGASolicitudPolizaProfesionalResponse().getMGASolicitudPolizaProfesionalResult();
			
			return response;

		}catch (Exception e) {
			throw new Exception("Imposible comunicar con la mutualidad en estos momentos. Inténtelo de nuevo en unos minutos", e);
		}
		
	}
	
	

	
	public IntegracionCuotaYCapitalObjetivoJubilacion ObtenerCuotaYCapObjetivo (ObtenerCuotaYCapObjetivoDocument request , String uriService)throws Exception{
		
		try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebServiceClientConfig.class))
		{
		    MutualidadClient client = context.getBean(MutualidadClient.class);
		    ObtenerCuotaYCapObjetivoResponseDocument responsefinal = client.getCuotaYCapObjetivo(uriService, request);
		        
			
			IntegracionCuotaYCapitalObjetivoJubilacion enumCuotaCapital = responsefinal.getObtenerCuotaYCapObjetivoResponse().getObtenerCuotaYCapObjetivoResult();
			return enumCuotaCapital;
		}catch (Exception e) {
			throw new Exception("Imposible comunicar con la mutualidad en estos momentos. Inténtelo de nuevo en unos minutos", e);
		}
		
	}


}
