package org.itcgae.siga;

import java.net.URI;
import java.util.List;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.logger.LoggingConfig;
import org.itcgae.siga.logger.MyBatisLoggerInterceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesExample;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;

import com.aspose.words.License;

@Configuration
public class SigaConfiguration implements ApplicationListener<ApplicationReadyEvent>{

	@Autowired
	private LoggingConfig loggingConfig;
	
	@Autowired
	ServletContext servletContext;
	
	@Autowired
	static
	private GenPropertiesMapper genPropertiesMapper;
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent arg0) {
		loggingConfig.initLogback();
		
		// Configuramos la licencia del aspose
		String rutaLicencia = SigaConstants.rutaLicencia;
		
		try {
			URI ruta = servletContext.getResource(rutaLicencia).toURI();
			License license = new License();
			license.setLicense(ruta.getPath());
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}
	
	
	@Configuration
	@EnableAutoConfiguration
	class MyBatisConfig {

		@Autowired
		private DataSource dataSource;
		
		@Autowired
		private ConfigurationCustomizer configurationCustomizer;

		@Bean
		public SqlSessionFactory sqlSessionFactory() throws Exception {
			SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
			sqlSessionFactory.setDataSource(dataSource);
			org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
			//Para usar mappers de xml
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			sqlSessionFactory.setMapperLocations(resolver.getResources("classpath:org/itcgae/siga/db/services/com/mappers/MapperConsulta.xml"));
			config.setCallSettersOnNulls(true);
			configurationCustomizer.customize(config);
			sqlSessionFactory.setConfiguration(config);
			return sqlSessionFactory.getObject();
		}
		
		/*@Bean
		 public MultipartConfigElement multipartConfigElement() {
		     return new MultipartConfigElement("");
		 }

		 @Bean
		 public MultipartResolver multipartResolver() {
		     org.springframework.web.multipart.commons.CommonsMultipartResolver multipartResolver = new org.springframework.web.multipart.commons.CommonsMultipartResolver();
		     multipartResolver.setMaxUploadSize(1000000);
		     return multipartResolver;
		 }*/
		
        @Bean
        public MultipartConfigElement multipartConfigElement() {
            MultipartConfigFactory factory = new MultipartConfigFactory();
            factory.setMaxFileSize(Long.valueOf(SigaConstants.PARAMETRO_GENERAL.MAX_FILE_SIZE.getValor()));
            factory.setMaxRequestSize(Long.valueOf(SigaConstants.PARAMETRO_GENERAL.MAX_FILE_SIZE.getValor()));
            return factory.createMultipartConfig();
        }

		
		@Bean
		public ConfigurationCustomizer configurationCustomizer() {
		    return new ConfigurationCustomizer() {
				@Override
				public void customize(org.apache.ibatis.session.Configuration configuration) {
					configuration.addInterceptor(new MyBatisLoggerInterceptor());
				}
		    };
		}	
		
	}
	
	@EnableWebMvc
	class WebConfig extends WebMvcConfigurerAdapter{
		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**");
		}		
	}
		
	
}
