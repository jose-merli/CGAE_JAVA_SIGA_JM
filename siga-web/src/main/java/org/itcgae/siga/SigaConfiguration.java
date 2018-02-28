package org.itcgae.siga;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.itcgae.siga.logger.MyBatisLoggerInterceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class SigaConfiguration {

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
			configurationCustomizer.customize(config);
			sqlSessionFactory.setConfiguration(config);
			return sqlSessionFactory.getObject();
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
