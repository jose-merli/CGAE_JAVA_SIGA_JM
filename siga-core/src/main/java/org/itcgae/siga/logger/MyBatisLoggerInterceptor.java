package org.itcgae.siga.logger;

import java.sql.Statement;
import java.util.Properties;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Intercepts({
		@Signature(type = StatementHandler.class, method = "query", args = { Statement.class, ResultHandler.class }) })
public class MyBatisLoggerInterceptor implements Interceptor {

	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		Logger LOGGER = LoggerFactory.getLogger(MyBatisLoggerInterceptor.class);
		
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		String query = statementHandler.getBoundSql().getSql();
		query = query.replaceAll("\\n|\\r|\\t", " ");
		LOGGER.info("SQL: {}", query);

		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}

}