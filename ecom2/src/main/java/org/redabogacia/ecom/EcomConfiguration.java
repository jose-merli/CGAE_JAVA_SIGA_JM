package org.redabogacia.ecom;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.logger.LoggingConfig;
import org.itcgae.siga.logger.MyBatisLoggerInterceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.redabogacia.ecom.services.IRefrescaVariablesService;
import org.redabogacia.ecom.services.impl.InitEcom2ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import oracle.jdbc.pool.OracleDataSource;

@Configuration
@PropertySource("classpath:bootstrap.properties")
public class EcomConfiguration implements ApplicationListener<ApplicationReadyEvent>{

	private static Logger LOGGER = LoggerFactory.getLogger(EcomConfiguration.class);
	
	@Autowired
	ServletContext servletContext;
	
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent arg0) {
	
		
	}
	
	
	@Configuration
	@EnableAutoConfiguration
	@PropertySource("classpath:bootstrap.properties")
	class MyBatisConfig {

		@Autowired
		private DataSource dataSource;
		
		@Autowired
		private ConfigurationCustomizer configurationCustomizer;
		
		@Value("${jndi-name}")
		private String jndiName;

		@Value("${table-name}")
		private String tableName;
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
            try {
				String parametroMaxFile = getMaxSizeFileFromDb(dataSource);
				Long tamañoTotal = Long.valueOf(parametroMaxFile) *1024 *1024;
				LOGGER.info(
						"Tamañó máximo de ficheros: " +  parametroMaxFile + " MB");
	            factory.setMaxFileSize(tamañoTotal);
	            factory.setMaxRequestSize(tamañoTotal);
	            return factory.createMultipartConfig();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return null;
        }

        /**
    	 * Carga todas las propiedades
    	 * 
    	 * @param ds
    	 * @param tableName
    	 * @return
    	 * @throws SQLException
    	 */
    	private String getMaxSizeFileFromDb(DataSource ds) throws SQLException {
    		String result = "";
    		Connection con = null;
    		Statement stmt = null;
    		ResultSet rs = null;
    		try {
    			con = ds.getConnection();
    			stmt = con.createStatement();
    			String query = "SELECT VALOR FROM GEN_PROPERTIES WHERE PARAMETRO = 'gen.ficheros.maxsize.bytes'" ;
    			rs = stmt.executeQuery(query);
    			LOGGER.info("SQL: {}", query);
    			while (rs.next()) {
    				result = rs.getString("VALOR");
    				
    			}
    			return result;
    		} catch (SQLException e) {
    			throw e;
    		} finally {
    			try {
    				if (rs != null)
    					rs.close();
    				if (stmt != null)
    					stmt.close();
    				if (con != null)
    					con.close();
    			} catch (SQLException e) {
    				e.printStackTrace();
    			}
    		}
    		
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
		

		/**
		 * Recupera el datasource con los datos de conexión sacados del fichero de
		 * configuracion
		 * 
		 * @return
		 * @throws IOException
		 * @throws NamingException
		 */
		private DataSource getOracleDataSource() throws IOException, NamingException {
			try {
				Context ctx = new InitialContext();
				DataSource datasource = (DataSource) ctx.lookup(jndiName);
				
				if (datasource == null) {
					try {
						// Setup the datasource
						OracleDataSource ds = new OracleDataSource();// There is other DataSource offered by Oracle , check the javadoc for more information
						ds.setDriverType("thin");
						ds.setServerName("vmcgaeap004.cloud.es.deloitte.com");
						ds.setPortNumber(1521);
						ds.setDatabaseName("cgae");
						ds.setUser("uscgae");
						ds.setPassword("uscgae");
	
						// Get a JDBC connection
						Connection c = ds.getConnection();
						
//						vmcgaeap004.cloud.es.deloitte.com:1521:cgae
//						uscgae/uscgae 

						
						return ds;
					}  catch (SQLException e) {
						e.printStackTrace();
					}
	
				}
				return (DataSource) ctx.lookup(jndiName);
				
				
								
			} catch (NamingException e) {
				throw e;
			}
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
