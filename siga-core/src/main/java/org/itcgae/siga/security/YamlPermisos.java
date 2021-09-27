package org.itcgae.siga.security;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application")
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
public class YamlPermisos {

	private Map<String, List<String>> permisos;

	public Map<String, List<String>> getPermisos() {
		return permisos;
	}

	public void setPermisos(Map<String, List<String>> permisos) {
		this.permisos = permisos;
	}

	@Override
	public String toString() {
		return "YamlPermisosProperties [permisos=" + permisos + "]";
	}

}
