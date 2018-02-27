package org.itcgae.siga.logger;

import org.apache.ibatis.logging.LogFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfig {

	public void myBatisConfig(){
		LogFactory.useSlf4jLogging();
	}
}
