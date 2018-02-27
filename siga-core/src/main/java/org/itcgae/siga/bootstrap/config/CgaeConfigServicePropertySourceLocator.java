package org.itcgae.siga.bootstrap.config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.itcgae.siga.db.entities.AdmConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.MapPropertySource;
import org.springframework.util.StringUtils;

/**
 * Carga la configuracion de la aplicacion desde la BBDD. Se puede recargar esta
 * configuración en tiempo de ejecución realizando un POST al endpoint /refresh.
 * 
 * NOTA: Aunque se recargan las propiedades, no todas se aplican en tiempo de
 * ejecución y necesitan redespliegue del aplicativo
 * 
 * @author DTUser
 *
 */
@Configuration
@Order(0)
@PropertySource("classpath:boot.db.properties")
public class CgaeConfigServicePropertySourceLocator implements PropertySourceLocator {

	private static Logger LOGGER = LoggerFactory.getLogger(CgaeConfigServicePropertySourceLocator.class);
	
	@Value("${jndi-name}")
	private String jndiName;
	
	@Value("${table-name}")
	private String tableName;

	@Override
	public org.springframework.core.env.PropertySource<?> locate(org.springframework.core.env.Environment environment) {
		CompositePropertySource composite = new CompositePropertySource("configService");
		LOGGER.info("Recuperando configuracion de la aplicacion desde BBDD");
		List<AdmConfig> propertyList = new ArrayList<AdmConfig>();
		boolean error = false;
		try {
			HashMap<String, Object> map = new HashMap<>();
			propertyList = getAllPropertiesFromDb(getOracleDataSource());

			for (AdmConfig prop : propertyList) {
				LOGGER.debug("Clave: " + prop.getClave() + ", Valor: " + prop.getValor());
				putValue(map, prop.getClave(), prop.getValor());
			}

			composite.addFirstPropertySource(new MapPropertySource("configClient", map));

		} catch (Exception e) {
			error = true;
			LOGGER.error("Error recuperando la configuracion de bbdd", e);
			LOGGER.warn("Se aplica la configuración por defecto");
		}
		if (!error) {
			LOGGER.info(
					"Propiedades cargadas correctamente. Se han recuperado " + propertyList.size() + " propiedades");
		}
		return composite;

	}

	private void putValue(HashMap<String, Object> map, String key, String value) {
		if (StringUtils.hasText(value)) {
			map.put(key, value);
		}
	}

	/**
	 * Carga todas las propiedades
	 * 
	 * @param ds
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	private List<AdmConfig> getAllPropertiesFromDb(DataSource ds) throws SQLException {
		List<AdmConfig> result = new ArrayList<>();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from " + tableName);
			while (rs.next()) {
				AdmConfig prop = new AdmConfig();
				prop.setClave(rs.getString("CLAVE"));
				prop.setValor(rs.getString("VALOR"));

				result.add(prop);
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

	/**
	 * Recupera el datasource con los datos de conexión sacados
	 * 
	 * @return
	 * @throws IOException
	 * @throws NamingException
	 */
	private DataSource getOracleDataSource() throws IOException, NamingException {
		try {
			Context ctx = new InitialContext();
			return (DataSource) ctx.lookup(jndiName);
		} catch (NamingException e) {
			throw e;
		}
	}


}
