package org.itcgae.siga.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.commons.utils.SigaExceptions;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.services.impl.SigaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@Configurable
public class CgaeAuthenticationProvider implements AuthenticationProvider {

	Logger LOGGER = Logger.getLogger(CgaeAuthenticationProvider.class);

	@Value("${cert-conf-path}")
	private String certConfPath;

	private SigaUserDetailsService userDetailsService;

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}
	
	@Autowired
    private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = (SigaUserDetailsService) userDetailsService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UserAuthenticationToken cgaeAuthenticaton = (UserAuthenticationToken) authentication;
		String username = authentication.getPrincipal() + "";

		// LOGGER.info("Intento de validar certificado " + username);
		// TODO: Añadir llamada a OCSPs con el certificado y comprobar
		/*
		 * if (System.getProperty("CERT_CHECK_CONF") == null) {
		 * System.setProperty("CERT_CHECK_CONF", certConfPath); } Validacion result =
		 * validaCertificado(cgaeAuthenticaton.getCertificate()); if (result == null ||
		 * !result.equals(Validacion.OK)) { throw new
		 * BadCredentialsException("Imposible validar el usuario en SIGA"); }
		 */

		UserCgae user;
		try {
			user = (UserCgae) this.userDetailsService.loadUserByUsername(cgaeAuthenticaton.getUser());

			if (user != null) {
				return new UserAuthenticationToken(cgaeAuthenticaton.getPrincipal(), null, user,
						cgaeAuthenticaton.getCertificate(), cgaeAuthenticaton.getAuthorities());
			} else {
				throw new BadCredentialsException("Imposible validar el usuario en SIGA");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BadCredentialsException("Se ha producido un error al cargar el usuario", e);
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UserAuthenticationToken.class);
	}

	public AdmUsuarios checkAuthentication(HttpServletRequest request) throws Exception {
		AdmUsuarios usuario = new AdmUsuarios();
		
		try {
			LOGGER.debug("UserTokenUtils.checkAuthentication > Comprobando token...");

			// obtenemos los datos del token
			String token = request.getHeader("Authorization");
			String dni = UserTokenUtils.getDniFromJWTToken(token);
			Short idInstitucion = UserTokenUtils.getInstitucionFromJWTToken(token);

			LOGGER.debug("UserTokenUtils.checkAuthentication > ok");

			// obtenemos los datos del usuario
			LOGGER.debug("UserTokenUtils.checkAuthentication > obteniendo datos de usuario...");

			if (idInstitucion != null) {
				AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
				exampleUsuarios.createCriteria().andNifEqualTo(dni).andIdinstitucionEqualTo(idInstitucion);

				List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
				
				if (usuarios != null && !usuarios.isEmpty()) {
					usuario = usuarios.get(0);
				}else {
					throw new SigaExceptions("usuario no encontrado");
				}
				
			}

			LOGGER.debug("UserTokenUtils.checkAuthentication > ok");
		} catch (Exception e) {
			LOGGER.error("UserTokenUtils.checkAuthentication > ERROR al comprobar los datos del token. ", e);
			throw e;
		}

		return usuario;
	}

	/*
	 * private Validacion validaCertificado(X509Certificate x509Certificate) {
	 * 
	 * Validacion valCertificadoRespuesta = null; valCertificadoRespuesta =
	 * Validacion.OK;
	 * 
	 * if (valCertificadoRespuesta.equals(Validacion.OK)){
	 * 
	 * return valCertificadoRespuesta; }else{ if
	 * (System.getProperty("CERT_CHECK_CONF") == null) {
	 * System.setProperty("CERT_CHECK_CONF", certConfPath); }
	 * 
	 * try { CertificateAuthValidatorDefault certificateAuthValidatorDefault = new
	 * CertificateAuthValidatorDefault(); boolean isValid =
	 * certificateAuthValidatorDefault.validate(x509Certificate); if (isValid) {
	 * LOGGER.debug("Certificado Valido"); valCertificadoRespuesta = Validacion.OK;
	 * } else { LOGGER.error("Imposible validar el usuario en SIGA");
	 * valCertificadoRespuesta = Validacion.ERROR_CERT_IS_NOT_VALID; } } catch
	 * (CertificateNotYetValidException e) { LOGGER.error(e);
	 * valCertificadoRespuesta = Validacion.ERROR_CERT_NOT_YET_VALID; } catch
	 * (CertificateExpiredException e) { LOGGER.error(e); valCertificadoRespuesta =
	 * Validacion.ERROR_CERT_EXPIRED; } catch (OcspRevokedException e) {
	 * LOGGER.error(e); valCertificadoRespuesta =
	 * Validacion.ERROR_CERT_OCSP_REVOKED; } catch (OcspUnknownException e) {
	 * LOGGER.error(e); valCertificadoRespuesta =
	 * Validacion.ERROR_CERT_OCSP_UNKNOWN; } catch (AuthenticationFailedException e)
	 * { LOGGER.error(e); valCertificadoRespuesta =
	 * Validacion.ERROR_CANNOT_VALIDATE_CERTIFICATE; } catch
	 * (UnrecognizedCAException e) { LOGGER.error(e); valCertificadoRespuesta =
	 * Validacion.ERROR_UNRECOGNIZED_CA; } catch (CrlRevokedException e) {
	 * LOGGER.error(e); valCertificadoRespuesta = Validacion.ERROR_CERT_CRL_REVOKED;
	 * } catch (CrlCommunicationException e) { LOGGER.error(e);
	 * valCertificadoRespuesta = Validacion.ERROR_CRL_NOTAVAILABLE; } catch
	 * (CrlOutOfDateException e) { LOGGER.error(e); valCertificadoRespuesta =
	 * Validacion.ERROR_CRL_OUT_OF_DATE; } catch (ConfigException e) {
	 * LOGGER.error(e); valCertificadoRespuesta = Validacion.ERROR_CONFIG; } catch
	 * (DamagedOrModifiedCertificateException e) { LOGGER.error(e);
	 * valCertificadoRespuesta = Validacion.ERROR_DAMAGE_OR_MODIFIED_CERTIFICATE; }
	 * catch (Exception e) { LOGGER.error(e); valCertificadoRespuesta =
	 * Validacion.ERROR_CANNOT_VALIDATE_CERTIFICATE; }
	 * 
	 * return valCertificadoRespuesta; }
	 * 
	 * }
	 * 
	 * private enum Validacion { OK, ERROR_CERT_IS_NOT_VALID,
	 * ERROR_CERT_NOT_YET_VALID, ERROR_CERT_EXPIRED, ERROR_CERT_OCSP_REVOKED,
	 * ERROR_CERT_OCSP_UNKNOWN, ERROR_CANNOT_VALIDATE_CERTIFICATE,
	 * ERROR_UNRECOGNIZED_CA, ERROR_CERT_CRL_REVOKED, ERROR_CRL_NOTAVAILABLE,
	 * ERROR_CRL_OUT_OF_DATE, ERROR_CONFIG
	 * 
	 * , ERROR_DAMAGE_OR_MODIFIED_CERTIFICATE
	 * 
	 * , ERROR_CERT_ROL_NOT_ALLOWED, ERROR_BAR_PROVIDER
	 * 
	 * }
	 */

}