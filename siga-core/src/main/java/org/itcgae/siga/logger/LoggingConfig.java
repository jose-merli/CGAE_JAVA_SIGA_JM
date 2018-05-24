package org.itcgae.siga.logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.rolling.FixedWindowRollingPolicy;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy;
import ch.qos.logback.core.util.FileSize;

@Component
public class LoggingConfig {

	@Value("${logging.pattern.console:%d{LLL-dd HH:mm:ss.SSS} %-5level [%X{callId}] --- %.40logger : %msg%n}")
	private String loggingPattern;

	@Value("${log4j.siga.web.file:siga-web.log}")
	private String sigaWebFile;
	
	@Value("${log4j.siga.web.bck.number:5}")
	private String sigaWebBckNumber;
	
	@Value("${log4j.siga.web.max.size:50MB}")
	private String sigaWebMaxSize;
	
	@Value("${log4j.siga.web.nivel:DEBUG}")
	private String sigaLevelLog;
	
	@Value("${log4j.root.nivel:ERROR}")
	private String rootLevel;
	
	public void initLogback() {

		LoggerContext logCtx = (LoggerContext) LoggerFactory.getILoggerFactory();

		// Pattern console
		PatternLayoutEncoder logEncoder = new PatternLayoutEncoder();
		logEncoder.setContext(logCtx);
		logEncoder.setPattern(loggingPattern);
		logEncoder.start();

		// Appender console
		ConsoleAppender<ILoggingEvent> logConsoleAppender = new ConsoleAppender<ILoggingEvent>();
		logConsoleAppender.setContext(logCtx);
		logConsoleAppender.setEncoder(logEncoder);
		logConsoleAppender.start();

		// Pattern file appender (inicializar nuevo aunque sea igual que el
		// anterior)
		logEncoder = new PatternLayoutEncoder();
		logEncoder.setContext(logCtx);
		logEncoder.setPattern(loggingPattern);
		logEncoder.start();

		// Appender file
		RollingFileAppender<ILoggingEvent> logFileAppender = new RollingFileAppender<ILoggingEvent>();
		logFileAppender.setContext(logCtx);
		logFileAppender.setEncoder(logEncoder);
		logFileAppender.setAppend(true);
		logFileAppender.setFile(sigaWebFile);
		logFileAppender.start();
		
		// Rolling Policy
		FixedWindowRollingPolicy logFilePolicy = new FixedWindowRollingPolicy();
		logFilePolicy.setContext(logCtx);
		logFilePolicy.setParent(logFileAppender);
		logFilePolicy.setFileNamePattern(sigaWebFile + "%i");
		logFilePolicy.setMinIndex(1);
		logFilePolicy.setMinIndex(Integer.parseInt(sigaWebBckNumber));
		logFilePolicy.start();

		// Triger Policy
		SizeBasedTriggeringPolicy<ILoggingEvent> sizeBasedPolicy = new SizeBasedTriggeringPolicy<ILoggingEvent>();
		sizeBasedPolicy.setMaxFileSize(FileSize.valueOf(sigaWebMaxSize));
		sizeBasedPolicy.setContext(logCtx);
		sizeBasedPolicy.start();

		// Policies appender file
		logFileAppender.setRollingPolicy(logFilePolicy);
		logFileAppender.setTriggeringPolicy(sizeBasedPolicy);
		logFileAppender.start();

		//Logger general aplicacion
		Logger logApplication = logCtx.getLogger("org.itcgae.siga");
		logApplication.setLevel(Level.toLevel(sigaLevelLog));
		logApplication.setAdditive(false);
		logApplication.addAppender(logFileAppender);

		//Logger de springframework
		Logger logSpring = logCtx.getLogger("org.springframework");
		logSpring.setLevel(Level.WARN);
		logApplication.setAdditive(false);
		logSpring.addAppender(logFileAppender);

		//Root logger
		Logger root = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.toLevel(rootLevel));

		return;
	}
}
