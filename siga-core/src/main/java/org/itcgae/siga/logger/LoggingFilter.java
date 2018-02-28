package org.itcgae.siga.logger;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import javax.naming.ldap.LdapName;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.MDC;

public class LoggingFilter implements Filter{

	String callId;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		callId = new RandomString(21).nextString();
		
		String user = getUserFromRequest(request);
		if (user != null && !user.equals("")){
			callId = callId.concat("||").concat(user);
		}
		MDC.put("callId", callId);
		
		chain.doFilter(request, response);
	}


	@Override
	public void destroy() {
		
	}
	
	private String getUserFromRequest(ServletRequest request) {
		String user = "";
		try {
		X509Certificate[] certs = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
		if (null != certs && certs.length > 0) {
			String dn = certs[0].getSubjectX500Principal().getName();
			LdapName ldapDN = new LdapName(dn);
				user = ldapDN.getRdns().stream().filter(a -> a.getType().equals("CN")).findFirst().get().getValue()
						.toString();
			}
			return user;
		} catch (Exception e) {
			return user;
		} 
	}

	
	static class RandomString {

	    /**
	     * Generate a random string.
	     */
	    public String nextString() {
	        for (int idx = 0; idx < buf.length; ++idx)
	            buf[idx] = symbols[random.nextInt(symbols.length)];
	        return new String(buf);
	    }

	    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	    public static final String lower = upper.toLowerCase(Locale.ROOT);

	    public static final String digits = "0123456789";

	    public static final String alphanum = upper + lower + digits;

	    private final Random random;

	    private final char[] symbols;

	    private final char[] buf;

	    public RandomString(int length, Random random, String symbols) {
	        if (length < 1) throw new IllegalArgumentException();
	        if (symbols.length() < 2) throw new IllegalArgumentException();
	        this.random = Objects.requireNonNull(random);
	        this.symbols = symbols.toCharArray();
	        this.buf = new char[length];
	    }

	    /**
	     * Create an alphanumeric string generator.
	     */
	    public RandomString(int length, Random random) {
	        this(length, random, alphanum);
	    }

	    /**
	     * Create an alphanumeric strings from a secure generator.
	     */
	    public RandomString(int length) {
	        this(length, new SecureRandom());
	    }

	    /**
	     * Create session identifiers.
	     */
	    public RandomString() {
	        this(21);
	    }

	}
}
