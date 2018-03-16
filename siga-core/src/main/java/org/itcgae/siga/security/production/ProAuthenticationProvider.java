package org.itcgae.siga.security.production;

import java.security.cert.X509Certificate;

import org.apache.log4j.Logger;
import org.itcgae.siga.security.UserAuthenticationToken;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@Configurable
public class ProAuthenticationProvider implements AuthenticationProvider {

	Logger LOGGER = Logger.getLogger(ProAuthenticationProvider.class);
	
	@Value("${cert-conf-path}")
	private String certConfPath;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UserAuthenticationToken cgaeAuthenticaton = (UserAuthenticationToken) authentication;
		String username = authentication.getPrincipal() + "";

		LOGGER.info("Intento de validar certificado " + username);
		// TODO: Añadir llamada a OCSPs con el certificado y comprobar
		if (System.getProperty("CERT_CHECK_CONF") == null){
			System.setProperty("CERT_CHECK_CONF", certConfPath);
		}
		Validacion result = validaCertificado(cgaeAuthenticaton.getCertificate());
		
		if (result.equals(Validacion.OK)){
			return new UserAuthenticationToken(cgaeAuthenticaton.getPrincipal(), null,
					cgaeAuthenticaton.getCertificate(), cgaeAuthenticaton.getAuthorities());			
		} else{
			throw new BadCredentialsException("Imposible validar el certificado");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UserAuthenticationToken.class);
	}

	private Validacion validaCertificado(X509Certificate x509Certificate) {

		Validacion valCertificadoRespuesta = null;

		if (valCertificadoRespuesta == null) {

			try {
//				CertificateAuthValidatorDefault certificateAuthValidatorDefault = new CertificateAuthValidatorDefault();
				//TODO: Pdte integrar libreria de validacion con java 1.8 
				// sun.security.provider.certpath java 8 
				// OCSP.check(X509Certificate arg, X509Certificate arg0)
//				boolean isValid = certificateAuthValidatorDefault.validate(x509Certificate);
				boolean isValid = true;
				if (isValid) {
					LOGGER.debug("Certificado Valido");
					valCertificadoRespuesta = Validacion.OK;
				} else {
					LOGGER.error("Imposible validar el certificado");
					valCertificadoRespuesta = Validacion.ERROR_CERT_IS_NOT_VALID;
				}
//			} catch (CertificateNotYetValidException e) {
//				LOGGER.error(e);
//				valCertificadoRespuesta = Validacion.ERROR_CERT_NOT_YET_VALID;
//			} catch (CertificateExpiredException e) {
//				LOGGER.error(e);
//				valCertificadoRespuesta = Validacion.ERROR_CERT_EXPIRED;
//			} catch (OcspRevokedException e) {
//				LOGGER.error(e);
//				valCertificadoRespuesta = Validacion.ERROR_CERT_OCSP_REVOKED;
//			} catch (OcspUnknownException e) {
//				LOGGER.error(e);
//				valCertificadoRespuesta = Validacion.ERROR_CERT_OCSP_UNKNOWN;
//			} catch (AuthenticationFailedException e) {
//				LOGGER.error(e);
//				valCertificadoRespuesta = Validacion.ERROR_CANNOT_VALIDATE_CERTIFICATE;
//			} catch (UnrecognizedCAException e) {
//				LOGGER.error(e);
//				valCertificadoRespuesta = Validacion.ERROR_UNRECOGNIZED_CA;
//			} catch (CrlRevokedException e) {
//				LOGGER.error(e);
//				valCertificadoRespuesta = Validacion.ERROR_CERT_CRL_REVOKED;
//			} catch (CrlCommunicationException e) {
//				LOGGER.error(e);
//				valCertificadoRespuesta = Validacion.ERROR_CRL_NOTAVAILABLE;
//			} catch (CrlOutOfDateException e) {
//				LOGGER.error(e);
//				valCertificadoRespuesta = Validacion.ERROR_CRL_OUT_OF_DATE;
//			} catch (ConfigException e) {
//				LOGGER.error(e);
//				valCertificadoRespuesta = Validacion.ERROR_CONFIG;
//			} catch (DamagedOrModifiedCertificateException e) {
//				LOGGER.error(e);
//				valCertificadoRespuesta = Validacion.ERROR_DAMAGE_OR_MODIFIED_CERTIFICATE;
			} catch (Exception e) {
				LOGGER.error(e);
				valCertificadoRespuesta = Validacion.ERROR_CANNOT_VALIDATE_CERTIFICATE;
			}
		}

		return valCertificadoRespuesta;

	}
	
	private enum Validacion {
		OK
		, ERROR_CERT_IS_NOT_VALID
		, ERROR_CERT_NOT_YET_VALID
		, ERROR_CERT_EXPIRED
		, ERROR_CERT_OCSP_REVOKED
		, ERROR_CERT_OCSP_UNKNOWN
		, ERROR_CANNOT_VALIDATE_CERTIFICATE
		, ERROR_UNRECOGNIZED_CA
		, ERROR_CERT_CRL_REVOKED
		, ERROR_CRL_NOTAVAILABLE
		, ERROR_CRL_OUT_OF_DATE
		, ERROR_CONFIG
		
		, ERROR_DAMAGE_OR_MODIFIED_CERTIFICATE
		
		, ERROR_CERT_ROL_NOT_ALLOWED
		, ERROR_BAR_PROVIDER
	
}

}
