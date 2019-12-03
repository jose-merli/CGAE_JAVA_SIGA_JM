package org.redabogacia.ecom;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication
@EnableScheduling
@MapperScan({"org.itcgae.siga.db.mappers"
	,"org.itcgae.siga.db.services"
	,"org.redabogacia.ecom.db"
	,"org.redabogacia.ecom.services.impl"})
public class Application extends SpringBootServletInitializer implements WebApplicationInitializer {
	
//	@Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(Application.class);
//    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}
