package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.exception.FacturacionSJCSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogErroresFacturacionSJCSHelper {

    private static final String PREFIJO_LOG = "logErrores_";

	private static final String SUFIJO_LOG = ".log";

	private static final String LOG_ERRORES_FACTURACION = "LogErroresFacturacion";

	private static final String JE_ABOGADOS = "JEAbogados";

	@Autowired
    private GenPropertiesMapper genPropertiesMapper;

	private static final String LOG_DATE_FORMAT ="dd/MM/yyyy HH:mm:ss"; 
    private static final DateTimeFormatter formatter  = DateTimeFormatter.ofPattern(LOG_DATE_FORMAT);
	private static final String TEMPLATE_LOG_ERROR = "%s - %s%n";
	private static final String EXITO = "Facturación realizada con éxito";
    
	public class LogErroresFacturacionImpl implements LogErroresFacturacionSJCS {

	
		private Short idInstitucion;
		private String idFacturacion;
		private File fLogErrores;
		private List<Error> lErrores = new ArrayList<Error>();

		private class Error {
			LocalDateTime date;
			String error;

			public Error(LocalDateTime d, String e) {
				this.date = d;
				this.error = e;
			}
		}

		private LogErroresFacturacionImpl(Short idInstitucion, String idFacturacion) {
			this.idInstitucion = idInstitucion;
			this.idFacturacion = idFacturacion;
			this.fLogErrores = getFileLogErroresFacturacion(idInstitucion, idFacturacion).toFile();
		}

		@Override
		public void logError(String error) {
			lErrores.add(new Error(LocalDateTime.now(), error));
		}
		
		@Override
		public void writeExito() throws FacturacionSJCSException {
			writeExito(EXITO);
		}
		
		@Override
		public void writeExito(String msg) throws FacturacionSJCSException {
			try (FileWriter fw = new FileWriter(fLogErrores, true);
					BufferedWriter bw = new BufferedWriter(fw);
					PrintWriter pw = new PrintWriter(bw);) {
					pw.printf(TEMPLATE_LOG_ERROR, formatter.format(LocalDateTime.now()), msg);
			} catch (IOException e) {
				throw new FacturacionSJCSException("error en LogErroresFacturacionSJCSHelper: " + e);
			}
			
		}
		
		@Override
		public void writeAllErrors() throws FacturacionSJCSException {
			if (lErrores.size() > 0) {
				try (FileWriter fw = new FileWriter(fLogErrores, true);
						BufferedWriter bw = new BufferedWriter(fw);
						PrintWriter pw = new PrintWriter(bw);) {
					for (Error e : lErrores) {
						pw.printf(TEMPLATE_LOG_ERROR, formatter.format(e.date), e.error);
					}

				} catch (IOException e) {
					throw new FacturacionSJCSException("error en LogErroresFacturacionSJCSHelper: " + e);
				}
			} 
		}
		
		@Override
		public boolean haveErrors() {
			return lErrores.size()>0;
		}

	}
    
    public LogErroresFacturacionSJCS getLogErroresFacturacion(Short idInstitucion, String idFacturacion) {
    	return new LogErroresFacturacionImpl(idInstitucion, idFacturacion);
    }
    
    public Path getFileLogErroresFacturacion(Short idInstitucion, String idFacturacion) {
		GenPropertiesKey key = new GenPropertiesKey();
		key.setFichero(SigaConstants.FICHERO_SIGA);
		key.setParametro(SigaConstants.PARAMETRO_DIRECTORIO_FISICO_FACTURA_PDF);
		GenProperties rutaFicherosSalida = genPropertiesMapper.selectByPrimaryKey(key);
		String rutaRaiz = rutaFicherosSalida.getValor();
		return Paths.get(rutaRaiz,JE_ABOGADOS,String.valueOf(idInstitucion),LOG_ERRORES_FACTURACION, PREFIJO_LOG + idFacturacion + SUFIJO_LOG);
	}
    

    
}
