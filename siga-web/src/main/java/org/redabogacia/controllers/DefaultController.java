package org.redabogacia.controllers;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.redabogacia.db.entities.DemoCustomers;
import org.redabogacia.services.IDbService;
import org.redabogacia.services.ITestHeadersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {
	
	@Autowired
	IDbService dbService;
	
	@Autowired
	ITestHeadersService testHeadersService;
	
	
	@RequestMapping(value="/", produces="text/html")
	String health() {
		return "Status: UP";
	}
	
	@RequestMapping("/db")
	List<DemoCustomers> getAllCustomers() {
		return dbService.findAllCustomer();
	}
	
	@RequestMapping("/db/{name}")
	List<DemoCustomers> getCustomersByName(@PathVariable(name="name") String name) {
		
		return dbService.findCustomersByName(name);
	}
	
	@RequestMapping("/userCertificate")
	String getCustomersByCertificate(HttpServletRequest request) {

		byte[] decodedCertificate = Base64.getDecoder()
				.decode(request.getHeader("ssl_client_cert").replaceAll("-----BEGIN CERTIFICATE-----", "")
						.replaceAll("-----END CERTIFICATE-----", "").replaceAll(" ", ""));

		X509Certificate cert = null;
		try {
			cert = (X509Certificate) CertificateFactory.getInstance("X.509")
					.generateCertificate(new ByteArrayInputStream(decodedCertificate));
		} catch (CertificateException e) {
			e.printStackTrace();
		}

		return cert != null ? cert.toString() : "null";
	}
	
    @RequestMapping(value = "/headers", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public @ResponseBody String getCabeceras(HttpServletRequest httpRequest) {
        
    	String headers = testHeadersService.getHeadersService(httpRequest);

        return headers;
    }
	
}
